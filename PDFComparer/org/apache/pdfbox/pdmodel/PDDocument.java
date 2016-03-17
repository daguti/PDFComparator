/*      */ package org.apache.pdfbox.pdmodel;
/*      */ 
/*      */ import java.awt.print.PageFormat;
/*      */ import java.awt.print.Pageable;
/*      */ import java.awt.print.Printable;
/*      */ import java.awt.print.PrinterException;
/*      */ import java.awt.print.PrinterJob;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.Closeable;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.net.URL;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import org.apache.pdfbox.cos.COSArray;
/*      */ import org.apache.pdfbox.cos.COSBase;
/*      */ import org.apache.pdfbox.cos.COSDictionary;
/*      */ import org.apache.pdfbox.cos.COSDocument;
/*      */ import org.apache.pdfbox.cos.COSInteger;
/*      */ import org.apache.pdfbox.cos.COSName;
/*      */ import org.apache.pdfbox.cos.COSObject;
/*      */ import org.apache.pdfbox.cos.COSStream;
/*      */ import org.apache.pdfbox.exceptions.COSVisitorException;
/*      */ import org.apache.pdfbox.exceptions.CryptographyException;
/*      */ import org.apache.pdfbox.exceptions.SignatureException;
/*      */ import org.apache.pdfbox.io.RandomAccess;
/*      */ import org.apache.pdfbox.pdfparser.BaseParser;
/*      */ import org.apache.pdfbox.pdfparser.NonSequentialPDFParser;
/*      */ import org.apache.pdfbox.pdfparser.PDFParser;
/*      */ import org.apache.pdfbox.pdfwriter.COSWriter;
/*      */ import org.apache.pdfbox.pdmodel.common.COSArrayList;
/*      */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*      */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*      */ import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
/*      */ import org.apache.pdfbox.pdmodel.encryption.BadSecurityHandlerException;
/*      */ import org.apache.pdfbox.pdmodel.encryption.DecryptionMaterial;
/*      */ import org.apache.pdfbox.pdmodel.encryption.PDEncryptionDictionary;
/*      */ import org.apache.pdfbox.pdmodel.encryption.ProtectionPolicy;
/*      */ import org.apache.pdfbox.pdmodel.encryption.SecurityHandler;
/*      */ import org.apache.pdfbox.pdmodel.encryption.SecurityHandlersManager;
/*      */ import org.apache.pdfbox.pdmodel.encryption.StandardDecryptionMaterial;
/*      */ import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
/*      */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
/*      */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceDictionary;
/*      */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceStream;
/*      */ import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
/*      */ import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;
/*      */ import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureOptions;
/*      */ import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
/*      */ import org.apache.pdfbox.pdmodel.interactive.form.PDField;
/*      */ import org.apache.pdfbox.pdmodel.interactive.form.PDSignatureField;
/*      */ 
/*      */ public class PDDocument
/*      */   implements Pageable, Closeable
/*      */ {
/*      */   private COSDocument document;
/*      */   private PDDocumentInformation documentInformation;
/*      */   private PDDocumentCatalog documentCatalog;
/*  101 */   private PDEncryptionDictionary encParameters = null;
/*      */ 
/*  106 */   private SecurityHandler securityHandler = null;
/*      */ 
/*  114 */   private Map<String, Integer> pageMap = null;
/*      */ 
/*  120 */   private boolean allSecurityToBeRemoved = false;
/*      */   private Long documentId;
/*      */   private BaseParser parser;
/*      */ 
/*      */   public PDDocument()
/*      */   {
/*  137 */     this.document = new COSDocument();
/*      */ 
/*  140 */     COSDictionary trailer = new COSDictionary();
/*  141 */     this.document.setTrailer(trailer);
/*      */ 
/*  144 */     COSDictionary rootDictionary = new COSDictionary();
/*  145 */     trailer.setItem(COSName.ROOT, rootDictionary);
/*  146 */     rootDictionary.setItem(COSName.TYPE, COSName.CATALOG);
/*  147 */     rootDictionary.setItem(COSName.VERSION, COSName.getPDFName("1.4"));
/*      */ 
/*  150 */     COSDictionary pages = new COSDictionary();
/*  151 */     rootDictionary.setItem(COSName.PAGES, pages);
/*  152 */     pages.setItem(COSName.TYPE, COSName.PAGES);
/*  153 */     COSArray kidsArray = new COSArray();
/*  154 */     pages.setItem(COSName.KIDS, kidsArray);
/*  155 */     pages.setItem(COSName.COUNT, COSInteger.ZERO);
/*      */   }
/*      */ 
/*      */   private void generatePageMap()
/*      */   {
/*  160 */     this.pageMap = new HashMap();
/*      */ 
/*  165 */     processListOfPageReferences(getDocumentCatalog().getPages().getKids());
/*      */   }
/*      */ 
/*      */   private void processListOfPageReferences(List<Object> pageNodes)
/*      */   {
/*  170 */     int numberOfNodes = pageNodes.size();
/*  171 */     for (int i = 0; i < numberOfNodes; i++)
/*      */     {
/*  173 */       Object pageOrArray = pageNodes.get(i);
/*  174 */       if ((pageOrArray instanceof PDPage))
/*      */       {
/*  176 */         COSArray pageArray = ((COSArrayList)((PDPage)pageOrArray).getParent().getKids()).toList();
/*  177 */         parseCatalogObject((COSObject)pageArray.get(i));
/*      */       }
/*  179 */       else if ((pageOrArray instanceof PDPageNode))
/*      */       {
/*  181 */         processListOfPageReferences(((PDPageNode)pageOrArray).getKids());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void parseCatalogObject(COSObject thePageOrArrayObject)
/*      */   {
/*  192 */     COSBase arrayCountBase = thePageOrArrayObject.getItem(COSName.COUNT);
/*  193 */     int arrayCount = -1;
/*  194 */     if ((arrayCountBase instanceof COSInteger))
/*      */     {
/*  196 */       arrayCount = ((COSInteger)arrayCountBase).intValue();
/*      */     }
/*      */ 
/*  199 */     COSBase kidsBase = thePageOrArrayObject.getItem(COSName.KIDS);
/*  200 */     int kidsCount = -1;
/*  201 */     if ((kidsBase instanceof COSArray))
/*      */     {
/*  203 */       kidsCount = ((COSArray)kidsBase).size();
/*      */     }
/*      */ 
/*  206 */     if ((arrayCount == -1) || (kidsCount == -1))
/*      */     {
/*  209 */       String objStr = String.valueOf(thePageOrArrayObject.getObjectNumber().intValue());
/*  210 */       String genStr = String.valueOf(thePageOrArrayObject.getGenerationNumber().intValue());
/*  211 */       getPageMap().put(objStr + "," + genStr, Integer.valueOf(getPageMap().size() + 1));
/*      */     }
/*  216 */     else if (arrayCount == kidsCount)
/*      */     {
/*  219 */       COSArray kidsArray = (COSArray)kidsBase;
/*  220 */       for (int i = 0; i < kidsArray.size(); i++)
/*      */       {
/*  222 */         COSObject thisObject = (COSObject)kidsArray.get(i);
/*  223 */         String objStr = String.valueOf(thisObject.getObjectNumber().intValue());
/*  224 */         String genStr = String.valueOf(thisObject.getGenerationNumber().intValue());
/*  225 */         getPageMap().put(objStr + "," + genStr, Integer.valueOf(getPageMap().size() + 1));
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  231 */       COSArray list = null;
/*  232 */       if ((kidsBase instanceof COSArray))
/*      */       {
/*  234 */         list = (COSArray)kidsBase;
/*      */       }
/*  236 */       if (list != null)
/*      */       {
/*  238 */         for (int arrayCounter = 0; arrayCounter < list.size(); arrayCounter++)
/*      */         {
/*  240 */           parseCatalogObject((COSObject)list.get(arrayCounter));
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public final Map<String, Integer> getPageMap()
/*      */   {
/*  254 */     if (this.pageMap == null)
/*      */     {
/*  256 */       generatePageMap();
/*      */     }
/*  258 */     return this.pageMap;
/*      */   }
/*      */ 
/*      */   public void addPage(PDPage page)
/*      */   {
/*  270 */     PDPageNode rootPages = getDocumentCatalog().getPages();
/*  271 */     rootPages.getKids().add(page);
/*  272 */     page.setParent(rootPages);
/*  273 */     rootPages.updateCount();
/*      */   }
/*      */ 
/*      */   public void addSignature(PDSignature sigObject, SignatureInterface signatureInterface)
/*      */     throws IOException, SignatureException
/*      */   {
/*  287 */     SignatureOptions defaultOptions = new SignatureOptions();
/*  288 */     defaultOptions.setPage(1);
/*  289 */     addSignature(sigObject, signatureInterface, defaultOptions);
/*      */   }
/*      */ 
/*      */   public void addSignature(PDSignature sigObject, SignatureInterface signatureInterface, SignatureOptions options)
/*      */     throws IOException, SignatureException
/*      */   {
/*  307 */     int preferedSignatureSize = options.getPreferedSignatureSize();
/*  308 */     if (preferedSignatureSize > 0)
/*      */     {
/*  310 */       sigObject.setContents(new byte[preferedSignatureSize]);
/*      */     }
/*      */     else
/*      */     {
/*  314 */       sigObject.setContents(new byte[9472]);
/*      */     }
/*      */ 
/*  318 */     sigObject.setByteRange(new int[] { 0, 1000000000, 1000000000, 1000000000 });
/*      */ 
/*  320 */     getDocument().setSignatureInterface(signatureInterface);
/*      */ 
/*  328 */     PDDocumentCatalog root = getDocumentCatalog();
/*  329 */     PDPageNode rootPages = root.getPages();
/*  330 */     List kids = new ArrayList();
/*  331 */     rootPages.getAllKids(kids);
/*      */ 
/*  333 */     int size = (int)rootPages.getCount();
/*      */ 
/*  335 */     if (size == 0)
/*      */     {
/*  337 */       throw new SignatureException(5, "The PDF file has no pages");
/*      */     }
/*      */     PDPage page;
/*      */     PDPage page;
/*  339 */     if (options.getPage() > size)
/*      */     {
/*  341 */       page = (PDPage)kids.get(size - 1);
/*      */     }
/*      */     else
/*      */     {
/*      */       PDPage page;
/*  343 */       if (options.getPage() <= 0)
/*      */       {
/*  345 */         page = (PDPage)kids.get(0);
/*      */       }
/*      */       else
/*      */       {
/*  349 */         page = (PDPage)kids.get(options.getPage() - 1);
/*      */       }
/*      */     }
/*      */ 
/*  353 */     PDAcroForm acroForm = root.getAcroForm();
/*  354 */     root.getCOSObject().setNeedToBeUpdate(true);
/*      */ 
/*  356 */     if (acroForm == null)
/*      */     {
/*  358 */       acroForm = new PDAcroForm(this);
/*  359 */       root.setAcroForm(acroForm);
/*      */     }
/*      */     else
/*      */     {
/*  363 */       acroForm.getCOSObject().setNeedToBeUpdate(true);
/*      */     }
/*      */ 
/*  374 */     List annotations = page.getAnnotations();
/*      */ 
/*  376 */     List fields = acroForm.getFields();
/*  377 */     PDSignatureField signatureField = null;
/*  378 */     if (fields == null)
/*      */     {
/*  380 */       fields = new ArrayList();
/*  381 */       acroForm.setFields(fields);
/*      */     }
/*  383 */     for (PDField pdField : fields)
/*      */     {
/*  385 */       if ((pdField instanceof PDSignatureField))
/*      */       {
/*  387 */         PDSignature signature = ((PDSignatureField)pdField).getSignature();
/*  388 */         if ((signature != null) && (signature.getDictionary().equals(sigObject.getDictionary())))
/*      */         {
/*  390 */           signatureField = (PDSignatureField)pdField;
/*      */         }
/*      */       }
/*      */     }
/*  394 */     if (signatureField == null)
/*      */     {
/*  396 */       signatureField = new PDSignatureField(acroForm);
/*  397 */       signatureField.setSignature(sigObject);
/*  398 */       signatureField.getWidget().setPage(page);
/*      */     }
/*      */ 
/*  402 */     List acroFormFields = acroForm.getFields();
/*  403 */     COSDictionary acroFormDict = acroForm.getDictionary();
/*  404 */     acroFormDict.setDirect(true);
/*  405 */     acroFormDict.setInt(COSName.SIG_FLAGS, 3);
/*      */ 
/*  407 */     boolean checkFields = false;
/*  408 */     for (PDField field : acroFormFields)
/*      */     {
/*      */       COSDocument visualSignature;
/*      */       PDAppearanceDictionary ap;
/*      */       COSStream apsStream;
/*      */       PDAppearanceStream aps;
/*      */       COSDictionary cosObject;
/*      */       List cosObjects;
/*      */       boolean annotNotFound;
/*      */       boolean sigFieldNotFound;
/*  410 */       if ((field instanceof PDSignatureField))
/*      */       {
/*  412 */         if (((PDSignatureField)field).getCOSObject().equals(signatureField.getCOSObject()))
/*      */         {
/*  414 */           checkFields = true;
/*  415 */           signatureField.getCOSObject().setNeedToBeUpdate(true);
/*  416 */           break;
/*      */         }
/*      */       }
/*      */     }
/*  420 */     if (!checkFields)
/*      */     {
/*  422 */       acroFormFields.add(signatureField);
/*      */     }
/*      */ 
/*  426 */     visualSignature = options.getVisualSignature();
/*      */ 
/*  429 */     if (visualSignature == null)
/*      */     {
/*  432 */       signatureField.getWidget().setRectangle(new PDRectangle());
/*      */ 
/*  434 */       acroFormDict.setItem(COSName.DR, null);
/*      */ 
/*  436 */       ap = new PDAppearanceDictionary();
/*  437 */       apsStream = getDocument().createCOSStream();
/*  438 */       apsStream.createUnfilteredStream();
/*  439 */       aps = new PDAppearanceStream(apsStream);
/*  440 */       cosObject = (COSDictionary)aps.getCOSObject();
/*  441 */       cosObject.setItem(COSName.SUBTYPE, COSName.FORM);
/*  442 */       cosObject.setItem(COSName.BBOX, new PDRectangle());
/*      */ 
/*  444 */       ap.setNormalAppearance(aps);
/*  445 */       ap.getDictionary().setDirect(true);
/*  446 */       signatureField.getWidget().setAppearance(ap);
/*      */     }
/*      */     else
/*      */     {
/*  451 */       cosObjects = visualSignature.getObjects();
/*      */ 
/*  453 */       annotNotFound = true;
/*  454 */       sigFieldNotFound = true;
/*      */ 
/*  456 */       for (COSObject cosObject : cosObjects)
/*      */       {
/*  458 */         if ((!annotNotFound) && (!sigFieldNotFound))
/*      */         {
/*      */           break;
/*      */         }
/*      */ 
/*  463 */         COSBase base = cosObject.getObject();
/*  464 */         if ((base != null) && ((base instanceof COSDictionary)))
/*      */         {
/*  466 */           COSBase ft = ((COSDictionary)base).getItem(COSName.FT);
/*  467 */           COSBase type = ((COSDictionary)base).getItem(COSName.TYPE);
/*  468 */           COSBase apDict = ((COSDictionary)base).getItem(COSName.AP);
/*      */ 
/*  471 */           if ((annotNotFound) && (COSName.ANNOT.equals(type)))
/*      */           {
/*  473 */             COSDictionary cosBaseDict = (COSDictionary)base;
/*      */ 
/*  476 */             COSArray rectAry = (COSArray)cosBaseDict.getItem(COSName.RECT);
/*  477 */             PDRectangle rect = new PDRectangle(rectAry);
/*  478 */             signatureField.getWidget().setRectangle(rect);
/*  479 */             annotNotFound = false;
/*      */           }
/*      */ 
/*  483 */           if ((sigFieldNotFound) && (COSName.SIG.equals(ft)) && (apDict != null))
/*      */           {
/*  485 */             COSDictionary cosBaseDict = (COSDictionary)base;
/*      */ 
/*  488 */             PDAppearanceDictionary ap = new PDAppearanceDictionary((COSDictionary)cosBaseDict.getDictionaryObject(COSName.AP));
/*      */ 
/*  490 */             ap.getDictionary().setDirect(true);
/*  491 */             signatureField.getWidget().setAppearance(ap);
/*      */ 
/*  494 */             COSBase dr = cosBaseDict.getItem(COSName.DR);
/*  495 */             if (dr != null)
/*      */             {
/*  497 */               dr.setDirect(true);
/*  498 */               dr.setNeedToBeUpdate(true);
/*  499 */               acroFormDict.setItem(COSName.DR, dr);
/*      */             }
/*  501 */             sigFieldNotFound = false;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  506 */       if ((annotNotFound) || (sigFieldNotFound))
/*      */       {
/*  508 */         throw new SignatureException(6, "Could not read all needed objects from template");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  514 */     if (annotations == null)
/*      */     {
/*  516 */       annotations = new COSArrayList();
/*  517 */       page.setAnnotations(annotations);
/*      */     }
/*      */ 
/*  520 */     if (((!(annotations instanceof COSArrayList)) || (!(acroFormFields instanceof COSArrayList)) || (!((COSArrayList)annotations).toList().equals(((COSArrayList)acroFormFields).toList()))) && (!checkFields))
/*      */     {
/*  525 */       annotations.add(signatureField.getWidget());
/*      */     }
/*  527 */     page.getCOSObject().setNeedToBeUpdate(true);
/*      */   }
/*      */ 
/*      */   public void addSignatureField(List<PDSignatureField> sigFields, SignatureInterface signatureInterface, SignatureOptions options)
/*      */     throws IOException, SignatureException
/*      */   {
/*  542 */     PDDocumentCatalog catalog = getDocumentCatalog();
/*  543 */     catalog.getCOSObject().setNeedToBeUpdate(true);
/*      */ 
/*  545 */     PDAcroForm acroForm = catalog.getAcroForm();
/*  546 */     if (acroForm == null)
/*      */     {
/*  548 */       acroForm = new PDAcroForm(this);
/*  549 */       catalog.setAcroForm(acroForm);
/*      */     }
/*      */     else
/*      */     {
/*  553 */       acroForm.getCOSObject().setNeedToBeUpdate(true);
/*      */     }
/*      */ 
/*  556 */     COSDictionary acroFormDict = acroForm.getDictionary();
/*  557 */     acroFormDict.setDirect(true);
/*  558 */     acroFormDict.setNeedToBeUpdate(true);
/*  559 */     if (acroFormDict.getInt(COSName.SIG_FLAGS) < 1)
/*      */     {
/*  561 */       acroFormDict.setInt(COSName.SIG_FLAGS, 1);
/*      */     }
/*      */ 
/*  564 */     List field = acroForm.getFields();
/*      */ 
/*  566 */     for (PDSignatureField sigField : sigFields)
/*      */     {
/*  568 */       PDSignature sigObject = sigField.getSignature();
/*  569 */       sigField.getCOSObject().setNeedToBeUpdate(true);
/*      */ 
/*  572 */       boolean checkFields = false;
/*  573 */       for (Object obj : field)
/*      */       {
/*  575 */         if ((obj instanceof PDSignatureField))
/*      */         {
/*  577 */           if (((PDSignatureField)obj).getCOSObject().equals(sigField.getCOSObject()))
/*      */           {
/*  579 */             checkFields = true;
/*  580 */             sigField.getCOSObject().setNeedToBeUpdate(true);
/*  581 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  586 */       if (!checkFields)
/*      */       {
/*  588 */         field.add(sigField);
/*      */       }
/*      */ 
/*  592 */       if (sigField.getSignature() != null)
/*      */       {
/*  594 */         sigField.getCOSObject().setNeedToBeUpdate(true);
/*  595 */         if (options == null);
/*  599 */         addSignature(sigField.getSignature(), signatureInterface, options);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean removePage(PDPage page)
/*      */   {
/*  614 */     PDPageNode parent = page.getParent();
/*  615 */     boolean retval = parent.getKids().remove(page);
/*  616 */     if (retval)
/*      */     {
/*  619 */       getDocumentCatalog().getPages().updateCount();
/*      */     }
/*  621 */     return retval;
/*      */   }
/*      */ 
/*      */   public boolean removePage(int pageNumber)
/*      */   {
/*  632 */     boolean removed = false;
/*  633 */     List allPages = getDocumentCatalog().getAllPages();
/*  634 */     if (allPages.size() > pageNumber)
/*      */     {
/*  636 */       PDPage page = (PDPage)allPages.get(pageNumber);
/*  637 */       removed = removePage(page);
/*      */     }
/*  639 */     return removed;
/*      */   }
/*      */ 
/*      */   public PDPage importPage(PDPage page)
/*      */     throws IOException
/*      */   {
/*  656 */     PDPage importedPage = new PDPage(new COSDictionary(page.getCOSDictionary()));
/*  657 */     InputStream is = null;
/*  658 */     OutputStream os = null;
/*      */     try
/*      */     {
/*  661 */       PDStream src = page.getContents();
/*  662 */       if (src != null)
/*      */       {
/*  664 */         PDStream dest = new PDStream(this.document.createCOSStream());
/*  665 */         importedPage.setContents(dest);
/*  666 */         os = dest.createOutputStream();
/*      */ 
/*  668 */         byte[] buf = new byte[10240];
/*      */ 
/*  670 */         is = src.createInputStream();
/*      */         int amountRead;
/*  671 */         while ((amountRead = is.read(buf, 0, 10240)) > -1)
/*      */         {
/*  673 */           os.write(buf, 0, amountRead);
/*      */         }
/*      */       }
/*  676 */       addPage(importedPage);
/*      */     }
/*      */     finally
/*      */     {
/*  680 */       if (is != null)
/*      */       {
/*  682 */         is.close();
/*      */       }
/*  684 */       if (os != null)
/*      */       {
/*  686 */         os.close();
/*      */       }
/*      */     }
/*  689 */     return importedPage;
/*      */   }
/*      */ 
/*      */   public PDDocument(COSDocument doc)
/*      */   {
/*  701 */     this(doc, null);
/*      */   }
/*      */ 
/*      */   public PDDocument(COSDocument doc, BaseParser usedParser)
/*      */   {
/*  712 */     this.document = doc;
/*  713 */     this.parser = usedParser;
/*      */   }
/*      */ 
/*      */   public COSDocument getDocument()
/*      */   {
/*  723 */     return this.document;
/*      */   }
/*      */ 
/*      */   public PDDocumentInformation getDocumentInformation()
/*      */   {
/*  733 */     if (this.documentInformation == null)
/*      */     {
/*  735 */       COSDictionary trailer = this.document.getTrailer();
/*  736 */       COSDictionary infoDic = (COSDictionary)trailer.getDictionaryObject(COSName.INFO);
/*  737 */       if (infoDic == null)
/*      */       {
/*  739 */         infoDic = new COSDictionary();
/*  740 */         trailer.setItem(COSName.INFO, infoDic);
/*      */       }
/*  742 */       this.documentInformation = new PDDocumentInformation(infoDic);
/*      */     }
/*  744 */     return this.documentInformation;
/*      */   }
/*      */ 
/*      */   public void setDocumentInformation(PDDocumentInformation info)
/*      */   {
/*  754 */     this.documentInformation = info;
/*  755 */     this.document.getTrailer().setItem(COSName.INFO, info.getDictionary());
/*      */   }
/*      */ 
/*      */   public PDDocumentCatalog getDocumentCatalog()
/*      */   {
/*  765 */     if (this.documentCatalog == null)
/*      */     {
/*  767 */       COSDictionary trailer = this.document.getTrailer();
/*  768 */       COSBase dictionary = trailer.getDictionaryObject(COSName.ROOT);
/*  769 */       if ((dictionary instanceof COSDictionary))
/*      */       {
/*  771 */         this.documentCatalog = new PDDocumentCatalog(this, (COSDictionary)dictionary);
/*      */       }
/*      */       else
/*      */       {
/*  775 */         this.documentCatalog = new PDDocumentCatalog(this);
/*      */       }
/*      */     }
/*  778 */     return this.documentCatalog;
/*      */   }
/*      */ 
/*      */   public boolean isEncrypted()
/*      */   {
/*  788 */     return this.document.isEncrypted();
/*      */   }
/*      */ 
/*      */   public PDEncryptionDictionary getEncryptionDictionary()
/*      */     throws IOException
/*      */   {
/*  804 */     if (this.encParameters == null)
/*      */     {
/*  806 */       if (isEncrypted())
/*      */       {
/*  808 */         this.encParameters = new PDEncryptionDictionary(this.document.getEncryptionDictionary());
/*      */       }
/*      */     }
/*  811 */     return this.encParameters;
/*      */   }
/*      */ 
/*      */   public void setEncryptionDictionary(PDEncryptionDictionary encDictionary)
/*      */     throws IOException
/*      */   {
/*  823 */     this.encParameters = encDictionary;
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public PDSignature getSignatureDictionary()
/*      */     throws IOException
/*      */   {
/*  836 */     return getLastSignatureDictionary();
/*      */   }
/*      */ 
/*      */   public PDSignature getLastSignatureDictionary()
/*      */     throws IOException
/*      */   {
/*  847 */     List signatureDictionaries = getSignatureDictionaries();
/*  848 */     int size = signatureDictionaries.size();
/*  849 */     if (size > 0)
/*      */     {
/*  851 */       return (PDSignature)signatureDictionaries.get(size - 1);
/*      */     }
/*  853 */     return null;
/*      */   }
/*      */ 
/*      */   public List<PDSignatureField> getSignatureFields()
/*      */     throws IOException
/*      */   {
/*  864 */     List fields = new LinkedList();
/*  865 */     PDAcroForm acroForm = getDocumentCatalog().getAcroForm();
/*  866 */     if (acroForm != null)
/*      */     {
/*  868 */       List signatureDictionary = this.document.getSignatureFields(false);
/*  869 */       for (COSDictionary dict : signatureDictionary)
/*      */       {
/*  871 */         fields.add(new PDSignatureField(acroForm, dict));
/*      */       }
/*      */     }
/*  874 */     return fields;
/*      */   }
/*      */ 
/*      */   public List<PDSignature> getSignatureDictionaries()
/*      */     throws IOException
/*      */   {
/*  885 */     List signatureDictionary = this.document.getSignatureDictionaries();
/*  886 */     List signatures = new LinkedList();
/*  887 */     for (COSDictionary dict : signatureDictionary)
/*      */     {
/*  889 */       signatures.add(new PDSignature(dict));
/*      */     }
/*  891 */     return signatures;
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public boolean isUserPassword(String password)
/*      */     throws IOException, CryptographyException
/*      */   {
/*  910 */     return false;
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public boolean isOwnerPassword(String password)
/*      */     throws IOException, CryptographyException
/*      */   {
/*  929 */     return false;
/*      */   }
/*      */ 
/*      */   public void decrypt(String password)
/*      */     throws CryptographyException, IOException
/*      */   {
/*      */     try
/*      */     {
/*  946 */       StandardDecryptionMaterial m = new StandardDecryptionMaterial(password);
/*  947 */       openProtection(m);
/*      */     }
/*      */     catch (BadSecurityHandlerException e)
/*      */     {
/*  951 */       throw new CryptographyException(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public boolean wasDecryptedWithOwnerPassword()
/*      */   {
/*  966 */     return false;
/*      */   }
/*      */ 
/*      */   public void encrypt(String ownerPassword, String userPassword)
/*      */     throws CryptographyException, IOException
/*      */   {
/*      */     try
/*      */     {
/*  987 */       StandardProtectionPolicy policy = new StandardProtectionPolicy(ownerPassword, userPassword, new AccessPermission());
/*      */ 
/*  989 */       protect(policy);
/*      */     }
/*      */     catch (BadSecurityHandlerException e)
/*      */     {
/*  993 */       throw new CryptographyException(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public String getOwnerPasswordForEncryption()
/*      */   {
/* 1010 */     return null;
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public String getUserPasswordForEncryption()
/*      */   {
/* 1025 */     return null;
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public boolean willEncryptWhenSaving()
/*      */   {
/* 1040 */     return false;
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public void clearWillEncryptWhenSaving()
/*      */   {
/*      */   }
/*      */ 
/*      */   public static PDDocument load(URL url)
/*      */     throws IOException
/*      */   {
/* 1066 */     return load(url.openStream());
/*      */   }
/*      */ 
/*      */   public static PDDocument load(URL url, boolean force)
/*      */     throws IOException
/*      */   {
/* 1082 */     return load(url.openStream(), force);
/*      */   }
/*      */ 
/*      */   public static PDDocument load(URL url, RandomAccess scratchFile)
/*      */     throws IOException
/*      */   {
/* 1097 */     return load(url.openStream(), scratchFile);
/*      */   }
/*      */ 
/*      */   public static PDDocument load(String filename)
/*      */     throws IOException
/*      */   {
/* 1111 */     return load(new FileInputStream(filename));
/*      */   }
/*      */ 
/*      */   public static PDDocument load(String filename, boolean force)
/*      */     throws IOException
/*      */   {
/* 1128 */     return load(new FileInputStream(filename), force);
/*      */   }
/*      */ 
/*      */   public static PDDocument load(String filename, RandomAccess scratchFile)
/*      */     throws IOException
/*      */   {
/* 1143 */     return load(new FileInputStream(filename), scratchFile);
/*      */   }
/*      */ 
/*      */   public static PDDocument load(File file)
/*      */     throws IOException
/*      */   {
/* 1157 */     return load(new FileInputStream(file));
/*      */   }
/*      */ 
/*      */   public static PDDocument load(File file, RandomAccess scratchFile)
/*      */     throws IOException
/*      */   {
/* 1172 */     return load(new FileInputStream(file), scratchFile);
/*      */   }
/*      */ 
/*      */   public static PDDocument load(InputStream input)
/*      */     throws IOException
/*      */   {
/* 1186 */     return load(input, null);
/*      */   }
/*      */ 
/*      */   public static PDDocument load(InputStream input, boolean force)
/*      */     throws IOException
/*      */   {
/* 1203 */     return load(input, null, force);
/*      */   }
/*      */ 
/*      */   public static PDDocument load(InputStream input, RandomAccess scratchFile)
/*      */     throws IOException
/*      */   {
/* 1218 */     PDFParser parser = new PDFParser(input, scratchFile);
/* 1219 */     parser.parse();
/* 1220 */     return parser.getPDDocument();
/*      */   }
/*      */ 
/*      */   public static PDDocument load(InputStream input, RandomAccess scratchFile, boolean force)
/*      */     throws IOException
/*      */   {
/* 1237 */     PDFParser parser = new PDFParser(input, scratchFile, force);
/* 1238 */     parser.parse();
/* 1239 */     return parser.getPDDocument();
/*      */   }
/*      */ 
/*      */   public static PDDocument loadNonSeq(File file, RandomAccess scratchFile)
/*      */     throws IOException
/*      */   {
/* 1255 */     return loadNonSeq(file, scratchFile, "");
/*      */   }
/*      */ 
/*      */   public static PDDocument loadNonSeq(File file, RandomAccess scratchFile, String password)
/*      */     throws IOException
/*      */   {
/* 1271 */     NonSequentialPDFParser parser = new NonSequentialPDFParser(file, scratchFile, password);
/* 1272 */     parser.parse();
/* 1273 */     return parser.getPDDocument();
/*      */   }
/*      */ 
/*      */   public static PDDocument loadNonSeq(InputStream input, RandomAccess scratchFile)
/*      */     throws IOException
/*      */   {
/* 1288 */     return loadNonSeq(input, scratchFile, "");
/*      */   }
/*      */ 
/*      */   public static PDDocument loadNonSeq(InputStream input, RandomAccess scratchFile, String password)
/*      */     throws IOException
/*      */   {
/* 1304 */     NonSequentialPDFParser parser = new NonSequentialPDFParser(input, scratchFile, password);
/* 1305 */     parser.parse();
/* 1306 */     return parser.getPDDocument();
/*      */   }
/*      */ 
/*      */   public void save(String fileName)
/*      */     throws IOException, COSVisitorException
/*      */   {
/* 1319 */     save(new File(fileName));
/*      */   }
/*      */ 
/*      */   public void save(File file)
/*      */     throws IOException, COSVisitorException
/*      */   {
/* 1332 */     save(new FileOutputStream(file));
/*      */   }
/*      */ 
/*      */   public void save(OutputStream output)
/*      */     throws IOException, COSVisitorException
/*      */   {
/* 1346 */     getDocumentCatalog().getPages().updateCount();
/* 1347 */     COSWriter writer = null;
/*      */     try
/*      */     {
/* 1350 */       writer = new COSWriter(output);
/* 1351 */       writer.write(this);
/* 1352 */       writer.close();
/*      */     }
/*      */     finally
/*      */     {
/* 1356 */       if (writer != null)
/*      */       {
/* 1358 */         writer.close();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void saveIncremental(String fileName)
/*      */     throws IOException, COSVisitorException
/*      */   {
/* 1372 */     saveIncremental(new BufferedInputStream(new FileInputStream(fileName)), new BufferedOutputStream(new FileOutputStream(fileName, true)));
/*      */   }
/*      */ 
/*      */   public void saveIncremental(InputStream input, OutputStream output)
/*      */     throws IOException, COSVisitorException
/*      */   {
/* 1387 */     getDocumentCatalog().getPages().updateCount();
/* 1388 */     COSWriter writer = null;
/*      */     try
/*      */     {
/* 1395 */       output.write("\r\n".getBytes());
/* 1396 */       writer = new COSWriter(output, input);
/* 1397 */       writer.write(this);
/* 1398 */       writer.close();
/*      */     }
/*      */     finally
/*      */     {
/* 1402 */       if (writer != null)
/*      */       {
/* 1404 */         writer.close();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public int getPageCount()
/*      */   {
/* 1421 */     return getNumberOfPages();
/*      */   }
/*      */ 
/*      */   public int getNumberOfPages()
/*      */   {
/* 1429 */     PDDocumentCatalog cat = getDocumentCatalog();
/* 1430 */     return (int)cat.getPages().getCount();
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public PageFormat getPageFormat(int pageIndex)
/*      */   {
/*      */     try
/*      */     {
/* 1446 */       PrinterJob printerJob = PrinterJob.getPrinterJob();
/* 1447 */       return new PDPageable(this, printerJob).getPageFormat(pageIndex);
/*      */     }
/*      */     catch (PrinterException e)
/*      */     {
/* 1451 */       throw new RuntimeException(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Printable getPrintable(int pageIndex)
/*      */   {
/* 1460 */     return (Printable)getDocumentCatalog().getAllPages().get(pageIndex);
/*      */   }
/*      */ 
/*      */   public void print(PrinterJob printJob)
/*      */     throws PrinterException
/*      */   {
/* 1473 */     print(printJob, false);
/*      */   }
/*      */ 
/*      */   public void print()
/*      */     throws PrinterException
/*      */   {
/* 1490 */     print(PrinterJob.getPrinterJob());
/*      */   }
/*      */ 
/*      */   public void silentPrint()
/*      */     throws PrinterException
/*      */   {
/* 1503 */     silentPrint(PrinterJob.getPrinterJob());
/*      */   }
/*      */ 
/*      */   public void silentPrint(PrinterJob printJob)
/*      */     throws PrinterException
/*      */   {
/* 1517 */     print(printJob, true);
/*      */   }
/*      */ 
/*      */   private void print(PrinterJob job, boolean silent) throws PrinterException
/*      */   {
/* 1522 */     if (job == null)
/*      */     {
/* 1524 */       throw new PrinterException("The given printer job is null.");
/*      */     }
/*      */ 
/* 1528 */     job.setPageable(new PDPageable(this, job));
/* 1529 */     if ((silent) || (job.printDialog()))
/*      */     {
/* 1531 */       job.print();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void close()
/*      */     throws IOException
/*      */   {
/* 1543 */     this.documentCatalog = null;
/* 1544 */     this.documentInformation = null;
/* 1545 */     this.encParameters = null;
/* 1546 */     if (this.pageMap != null)
/*      */     {
/* 1548 */       this.pageMap.clear();
/* 1549 */       this.pageMap = null;
/*      */     }
/* 1551 */     this.securityHandler = null;
/* 1552 */     if (this.document != null)
/*      */     {
/* 1554 */       this.document.close();
/* 1555 */       this.document = null;
/*      */     }
/* 1557 */     if (this.parser != null)
/*      */     {
/* 1559 */       this.parser.clearResources();
/* 1560 */       this.parser = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void protect(ProtectionPolicy pp)
/*      */     throws BadSecurityHandlerException
/*      */   {
/* 1578 */     SecurityHandler handler = SecurityHandlersManager.getInstance().getSecurityHandler(pp);
/* 1579 */     this.securityHandler = handler;
/*      */   }
/*      */ 
/*      */   public void openProtection(DecryptionMaterial pm)
/*      */     throws BadSecurityHandlerException, IOException, CryptographyException
/*      */   {
/* 1597 */     PDEncryptionDictionary dict = getEncryptionDictionary();
/* 1598 */     if (dict.getFilter() != null)
/*      */     {
/* 1600 */       this.securityHandler = SecurityHandlersManager.getInstance().getSecurityHandler(dict.getFilter());
/* 1601 */       this.securityHandler.decryptDocument(this, pm);
/* 1602 */       this.document.dereferenceObjectStreams();
/* 1603 */       this.document.setEncryptionDictionary(null);
/* 1604 */       getDocumentCatalog();
/*      */     }
/*      */     else
/*      */     {
/* 1608 */       throw new RuntimeException("This document does not need to be decrypted");
/*      */     }
/*      */   }
/*      */ 
/*      */   public AccessPermission getCurrentAccessPermission()
/*      */   {
/* 1625 */     if (this.securityHandler == null)
/*      */     {
/* 1627 */       return AccessPermission.getOwnerAccessPermission();
/*      */     }
/* 1629 */     return this.securityHandler.getCurrentAccessPermission();
/*      */   }
/*      */ 
/*      */   public SecurityHandler getSecurityHandler()
/*      */   {
/* 1639 */     return this.securityHandler;
/*      */   }
/*      */ 
/*      */   public boolean setSecurityHandler(SecurityHandler secHandler)
/*      */   {
/* 1651 */     if (this.securityHandler == null)
/*      */     {
/* 1653 */       this.securityHandler = secHandler;
/* 1654 */       return true;
/*      */     }
/* 1656 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean isAllSecurityToBeRemoved()
/*      */   {
/* 1665 */     return this.allSecurityToBeRemoved;
/*      */   }
/*      */ 
/*      */   public void setAllSecurityToBeRemoved(boolean removeAllSecurity)
/*      */   {
/* 1675 */     this.allSecurityToBeRemoved = removeAllSecurity;
/*      */   }
/*      */ 
/*      */   public Long getDocumentId()
/*      */   {
/* 1680 */     return this.documentId;
/*      */   }
/*      */ 
/*      */   public void setDocumentId(Long docId)
/*      */   {
/* 1685 */     this.documentId = docId;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.PDDocument
 * JD-Core Version:    0.6.2
 */