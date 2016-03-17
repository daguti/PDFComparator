/*     */ package org.apache.pdfbox.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ import org.apache.pdfbox.exceptions.COSVisitorException;
/*     */ import org.apache.pdfbox.io.RandomAccess;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentInformation;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.PDResources;
/*     */ import org.apache.pdfbox.pdmodel.common.COSArrayList;
/*     */ import org.apache.pdfbox.pdmodel.common.PDNumberTreeNode;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDMarkInfo;
/*     */ import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDStructureTreeRoot;
/*     */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
/*     */ import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
/*     */ import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
/*     */ import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
/*     */ import org.apache.pdfbox.pdmodel.interactive.form.PDField;
/*     */ import org.apache.pdfbox.pdmodel.interactive.form.PDFieldFactory;
/*     */ 
/*     */ public class PDFMergerUtility
/*     */ {
/*  72 */   private static final Log LOG = LogFactory.getLog(PDFMergerUtility.class);
/*     */   private static final String STRUCTURETYPE_DOCUMENT = "Document";
/*     */   private final List<InputStream> sources;
/*     */   private String destinationFileName;
/*     */   private OutputStream destinationStream;
/*  79 */   private boolean ignoreAcroFormErrors = false;
/*     */ 
/* 550 */   private int nextFieldNum = 1;
/*     */ 
/*     */   public PDFMergerUtility()
/*     */   {
/*  86 */     this.sources = new ArrayList();
/*     */   }
/*     */ 
/*     */   public String getDestinationFileName()
/*     */   {
/*  96 */     return this.destinationFileName;
/*     */   }
/*     */ 
/*     */   public void setDestinationFileName(String destination)
/*     */   {
/* 106 */     this.destinationFileName = destination;
/*     */   }
/*     */ 
/*     */   public OutputStream getDestinationStream()
/*     */   {
/* 116 */     return this.destinationStream;
/*     */   }
/*     */ 
/*     */   public void setDestinationStream(OutputStream destStream)
/*     */   {
/* 126 */     this.destinationStream = destStream;
/*     */   }
/*     */ 
/*     */   public void addSource(String source)
/*     */   {
/*     */     try
/*     */     {
/* 138 */       this.sources.add(new FileInputStream(new File(source)));
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 142 */       throw new RuntimeException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addSource(File source)
/*     */   {
/*     */     try
/*     */     {
/* 155 */       this.sources.add(new FileInputStream(source));
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 159 */       throw new RuntimeException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addSource(InputStream source)
/*     */   {
/* 170 */     this.sources.add(source);
/*     */   }
/*     */ 
/*     */   public void addSources(List<InputStream> sourcesList)
/*     */   {
/* 181 */     this.sources.addAll(sourcesList);
/*     */   }
/*     */ 
/*     */   public void mergeDocuments()
/*     */     throws IOException, COSVisitorException
/*     */   {
/* 194 */     mergeDocuments(false, null);
/*     */   }
/*     */ 
/*     */   public void mergeDocumentsNonSeq(RandomAccess scratchFile)
/*     */     throws IOException, COSVisitorException
/*     */   {
/* 210 */     mergeDocuments(true, scratchFile);
/*     */   }
/*     */ 
/*     */   private void mergeDocuments(boolean isNonSeq, RandomAccess scratchFile)
/*     */     throws IOException, COSVisitorException
/*     */   {
/* 216 */     PDDocument destination = null;
/*     */ 
/* 219 */     if ((this.sources != null) && (this.sources.size() > 0))
/*     */     {
/* 221 */       ArrayList tobeclosed = new ArrayList();
/*     */       try
/*     */       {
/* 225 */         Iterator sit = this.sources.iterator();
/* 226 */         destination = new PDDocument();
/*     */ 
/* 228 */         while (sit.hasNext())
/*     */         {
/* 230 */           InputStream sourceFile = (InputStream)sit.next();
/*     */           PDDocument source;
/*     */           PDDocument source;
/* 231 */           if (isNonSeq)
/*     */           {
/* 233 */             source = PDDocument.loadNonSeq(sourceFile, null);
/*     */           }
/*     */           else
/*     */           {
/* 237 */             source = PDDocument.load(sourceFile);
/*     */           }
/*     */ 
/* 240 */           tobeclosed.add(source);
/* 241 */           appendDocument(destination, source);
/*     */         }
/* 243 */         if (this.destinationStream == null)
/*     */         {
/* 245 */           destination.save(this.destinationFileName);
/*     */         }
/*     */         else
/*     */         {
/* 249 */           destination.save(this.destinationStream);
/*     */         }
/*     */       }
/*     */       finally
/*     */       {
/* 254 */         if (destination != null)
/*     */         {
/* 256 */           destination.close();
/*     */         }
/* 258 */         for (PDDocument doc : tobeclosed)
/*     */         {
/* 260 */           doc.close();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void appendDocument(PDDocument destination, PDDocument source)
/*     */     throws IOException
/*     */   {
/* 278 */     if (destination.isEncrypted())
/*     */     {
/* 280 */       throw new IOException("Error: destination PDF is encrypted, can't append encrypted PDF documents.");
/*     */     }
/* 282 */     if (source.isEncrypted())
/*     */     {
/* 284 */       throw new IOException("Error: source PDF is encrypted, can't append encrypted PDF documents.");
/*     */     }
/* 286 */     PDDocumentInformation destInfo = destination.getDocumentInformation();
/* 287 */     PDDocumentInformation srcInfo = source.getDocumentInformation();
/* 288 */     destInfo.getDictionary().mergeInto(srcInfo.getDictionary());
/*     */ 
/* 290 */     PDDocumentCatalog destCatalog = destination.getDocumentCatalog();
/* 291 */     PDDocumentCatalog srcCatalog = source.getDocumentCatalog();
/*     */ 
/* 294 */     float destVersion = destination.getDocument().getVersion();
/* 295 */     float srcVersion = source.getDocument().getVersion();
/*     */ 
/* 297 */     if (destVersion < srcVersion)
/*     */     {
/* 299 */       destination.getDocument().setVersion(srcVersion);
/*     */     }
/*     */ 
/* 302 */     if (destCatalog.getOpenAction() == null)
/*     */     {
/* 304 */       destCatalog.setOpenAction(srcCatalog.getOpenAction());
/*     */     }
/*     */ 
/* 307 */     PDFCloneUtility cloner = new PDFCloneUtility(destination);
/*     */     try
/*     */     {
/* 311 */       PDAcroForm destAcroForm = destCatalog.getAcroForm();
/* 312 */       PDAcroForm srcAcroForm = srcCatalog.getAcroForm();
/* 313 */       if (destAcroForm == null)
/*     */       {
/* 315 */         cloner.cloneForNewDocument(srcAcroForm);
/* 316 */         destCatalog.setAcroForm(srcAcroForm);
/*     */       }
/* 320 */       else if (srcAcroForm != null)
/*     */       {
/* 322 */         mergeAcroForm(cloner, destAcroForm, srcAcroForm);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 329 */       if (!this.ignoreAcroFormErrors)
/*     */       {
/* 331 */         LOG.error(e, e);
/* 332 */         throw new IOException(e.getMessage());
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 337 */       throw new RuntimeException(e);
/*     */     }
/*     */ 
/* 340 */     COSArray destThreads = (COSArray)destCatalog.getCOSDictionary().getDictionaryObject(COSName.THREADS);
/* 341 */     COSArray srcThreads = (COSArray)cloner.cloneForNewDocument(destCatalog.getCOSDictionary().getDictionaryObject(COSName.THREADS));
/*     */ 
/* 343 */     if (destThreads == null)
/*     */     {
/* 345 */       destCatalog.getCOSDictionary().setItem(COSName.THREADS, srcThreads);
/*     */     }
/*     */     else
/*     */     {
/* 349 */       destThreads.addAll(srcThreads);
/*     */     }
/*     */ 
/* 352 */     PDDocumentNameDictionary destNames = destCatalog.getNames();
/* 353 */     PDDocumentNameDictionary srcNames = srcCatalog.getNames();
/* 354 */     if (srcNames != null)
/*     */     {
/* 356 */       if (destNames == null)
/*     */       {
/* 358 */         destCatalog.getCOSDictionary().setItem(COSName.NAMES, cloner.cloneForNewDocument(srcNames));
/*     */       }
/*     */       else
/*     */       {
/* 362 */         cloner.cloneMerge(srcNames, destNames);
/*     */       }
/*     */     }
/*     */ 
/* 366 */     PDDocumentOutline destOutline = destCatalog.getDocumentOutline();
/* 367 */     PDDocumentOutline srcOutline = srcCatalog.getDocumentOutline();
/* 368 */     if (srcOutline != null)
/*     */     {
/* 370 */       if (destOutline == null)
/*     */       {
/* 372 */         PDDocumentOutline cloned = new PDDocumentOutline((COSDictionary)cloner.cloneForNewDocument(srcOutline));
/* 373 */         destCatalog.setDocumentOutline(cloned);
/*     */       }
/*     */       else
/*     */       {
/* 377 */         PDOutlineItem first = srcOutline.getFirstChild();
/* 378 */         if (first != null)
/*     */         {
/* 380 */           PDOutlineItem clonedFirst = new PDOutlineItem((COSDictionary)cloner.cloneForNewDocument(first));
/* 381 */           destOutline.appendChild(clonedFirst);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 386 */     String destPageMode = destCatalog.getPageMode();
/* 387 */     String srcPageMode = srcCatalog.getPageMode();
/* 388 */     if (destPageMode == null)
/*     */     {
/* 390 */       destCatalog.setPageMode(srcPageMode);
/*     */     }
/*     */ 
/* 393 */     COSDictionary destLabels = (COSDictionary)destCatalog.getCOSDictionary().getDictionaryObject(COSName.PAGE_LABELS);
/*     */ 
/* 395 */     COSDictionary srcLabels = (COSDictionary)srcCatalog.getCOSDictionary().getDictionaryObject(COSName.PAGE_LABELS);
/*     */ 
/* 397 */     if (srcLabels != null)
/*     */     {
/* 399 */       int destPageCount = destination.getNumberOfPages();
/*     */       COSArray destNums;
/* 401 */       if (destLabels == null)
/*     */       {
/* 403 */         destLabels = new COSDictionary();
/* 404 */         COSArray destNums = new COSArray();
/* 405 */         destLabels.setItem(COSName.NUMS, destNums);
/* 406 */         destCatalog.getCOSDictionary().setItem(COSName.PAGE_LABELS, destLabels);
/*     */       }
/*     */       else
/*     */       {
/* 410 */         destNums = (COSArray)destLabels.getDictionaryObject(COSName.NUMS);
/*     */       }
/* 412 */       COSArray srcNums = (COSArray)srcLabels.getDictionaryObject(COSName.NUMS);
/* 413 */       if (srcNums != null)
/*     */       {
/* 415 */         for (int i = 0; i < srcNums.size(); i += 2)
/*     */         {
/* 417 */           COSNumber labelIndex = (COSNumber)srcNums.getObject(i);
/* 418 */           long labelIndexValue = labelIndex.intValue();
/* 419 */           destNums.add(COSInteger.get(labelIndexValue + destPageCount));
/* 420 */           destNums.add(cloner.cloneForNewDocument(srcNums.getObject(i + 1)));
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 425 */     COSStream destMetadata = (COSStream)destCatalog.getCOSDictionary().getDictionaryObject(COSName.METADATA);
/* 426 */     COSStream srcMetadata = (COSStream)srcCatalog.getCOSDictionary().getDictionaryObject(COSName.METADATA);
/* 427 */     if ((destMetadata == null) && (srcMetadata != null))
/*     */     {
/* 429 */       PDStream newStream = new PDStream(destination, srcMetadata.getUnfilteredStream(), false);
/* 430 */       newStream.getStream().mergeInto(srcMetadata);
/* 431 */       newStream.addCompression();
/* 432 */       destCatalog.getCOSDictionary().setItem(COSName.METADATA, newStream);
/*     */     }
/*     */ 
/* 437 */     boolean mergeStructTree = false;
/* 438 */     int destParentTreeNextKey = -1;
/* 439 */     COSDictionary destParentTreeDict = null;
/* 440 */     COSDictionary srcParentTreeDict = null;
/* 441 */     COSArray destNumbersArray = null;
/* 442 */     COSArray srcNumbersArray = null;
/* 443 */     PDMarkInfo destMark = destCatalog.getMarkInfo();
/* 444 */     PDStructureTreeRoot destStructTree = destCatalog.getStructureTreeRoot();
/* 445 */     PDMarkInfo srcMark = srcCatalog.getMarkInfo();
/* 446 */     PDStructureTreeRoot srcStructTree = srcCatalog.getStructureTreeRoot();
/* 447 */     if (destStructTree != null)
/*     */     {
/* 449 */       PDNumberTreeNode destParentTree = destStructTree.getParentTree();
/* 450 */       destParentTreeNextKey = destStructTree.getParentTreeNextKey();
/* 451 */       if (destParentTree != null)
/*     */       {
/* 453 */         destParentTreeDict = destParentTree.getCOSDictionary();
/* 454 */         destNumbersArray = (COSArray)destParentTreeDict.getDictionaryObject(COSName.NUMS);
/* 455 */         if (destNumbersArray != null)
/*     */         {
/* 457 */           if (destParentTreeNextKey < 0)
/*     */           {
/* 459 */             destParentTreeNextKey = destNumbersArray.size() / 2;
/*     */           }
/* 461 */           if (destParentTreeNextKey > 0)
/*     */           {
/* 463 */             if (srcStructTree != null)
/*     */             {
/* 465 */               PDNumberTreeNode srcParentTree = srcStructTree.getParentTree();
/* 466 */               if (srcParentTree != null)
/*     */               {
/* 468 */                 srcParentTreeDict = srcParentTree.getCOSDictionary();
/* 469 */                 srcNumbersArray = (COSArray)srcParentTreeDict.getDictionaryObject(COSName.NUMS);
/* 470 */                 if (srcNumbersArray != null)
/*     */                 {
/* 472 */                   mergeStructTree = true;
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 479 */       if ((destMark != null) && (destMark.isMarked()) && (!mergeStructTree))
/*     */       {
/* 481 */         destMark.setMarked(false);
/*     */       }
/* 483 */       if (!mergeStructTree)
/*     */       {
/* 485 */         destCatalog.setStructureTreeRoot(null);
/*     */       }
/*     */     }
/*     */ 
/* 489 */     List pages = srcCatalog.getAllPages();
/* 490 */     Iterator pageIter = pages.iterator();
/* 491 */     HashMap objMapping = new HashMap();
/* 492 */     while (pageIter.hasNext())
/*     */     {
/* 494 */       PDPage page = (PDPage)pageIter.next();
/* 495 */       PDPage newPage = new PDPage((COSDictionary)cloner.cloneForNewDocument(page.getCOSDictionary()));
/* 496 */       newPage.setCropBox(page.findCropBox());
/* 497 */       newPage.setMediaBox(page.findMediaBox());
/* 498 */       newPage.setRotation(page.findRotation());
/*     */ 
/* 500 */       newPage.setResources(new PDResources((COSDictionary)cloner.cloneForNewDocument(page.findResources())));
/* 501 */       if (mergeStructTree)
/*     */       {
/* 503 */         updateStructParentEntries(newPage, destParentTreeNextKey);
/* 504 */         objMapping.put(page.getCOSDictionary(), newPage.getCOSDictionary());
/* 505 */         List oldAnnots = page.getAnnotations();
/* 506 */         List newAnnots = newPage.getAnnotations();
/* 507 */         for (int i = 0; i < oldAnnots.size(); i++)
/*     */         {
/* 509 */           objMapping.put(((PDAnnotation)oldAnnots.get(i)).getDictionary(), ((PDAnnotation)newAnnots.get(i)).getDictionary());
/*     */         }
/*     */       }
/*     */ 
/* 513 */       destination.addPage(newPage);
/*     */     }
/* 515 */     if (mergeStructTree)
/*     */     {
/* 517 */       updatePageReferences(srcNumbersArray, objMapping);
/* 518 */       for (int i = 0; i < srcNumbersArray.size() / 2; i++)
/*     */       {
/* 520 */         destNumbersArray.add(COSInteger.get(destParentTreeNextKey + i));
/* 521 */         destNumbersArray.add(srcNumbersArray.getObject(i * 2 + 1));
/*     */       }
/* 523 */       destParentTreeNextKey += srcNumbersArray.size() / 2;
/* 524 */       destParentTreeDict.setItem(COSName.NUMS, destNumbersArray);
/* 525 */       PDNumberTreeNode newParentTreeNode = new PDNumberTreeNode(destParentTreeDict, COSBase.class);
/* 526 */       destStructTree.setParentTree(newParentTreeNode);
/* 527 */       destStructTree.setParentTreeNextKey(destParentTreeNextKey);
/*     */ 
/* 529 */       COSDictionary kDictLevel0 = new COSDictionary();
/* 530 */       COSArray newKArray = new COSArray();
/* 531 */       COSArray destKArray = destStructTree.getKArray();
/* 532 */       COSArray srcKArray = srcStructTree.getKArray();
/* 533 */       if ((destKArray != null) && (srcKArray != null))
/*     */       {
/* 535 */         updateParentEntry(destKArray, kDictLevel0);
/* 536 */         newKArray.addAll(destKArray);
/* 537 */         if (mergeStructTree)
/*     */         {
/* 539 */           updateParentEntry(srcKArray, kDictLevel0);
/*     */         }
/* 541 */         newKArray.addAll(srcKArray);
/*     */       }
/* 543 */       kDictLevel0.setItem(COSName.K, newKArray);
/* 544 */       kDictLevel0.setItem(COSName.P, destStructTree);
/* 545 */       kDictLevel0.setItem(COSName.S, new COSString("Document"));
/* 546 */       destStructTree.setK(kDictLevel0);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void mergeAcroForm(PDFCloneUtility cloner, PDAcroForm destAcroForm, PDAcroForm srcAcroForm)
/*     */     throws IOException
/*     */   {
/* 564 */     List destFields = destAcroForm.getFields();
/* 565 */     List srcFields = srcAcroForm.getFields();
/* 566 */     if (srcFields != null)
/*     */     {
/* 568 */       if (destFields == null)
/*     */       {
/* 570 */         destFields = new COSArrayList();
/* 571 */         destAcroForm.setFields(destFields);
/*     */       }
/* 573 */       Iterator srcFieldsIterator = srcFields.iterator();
/* 574 */       while (srcFieldsIterator.hasNext())
/*     */       {
/* 576 */         PDField srcField = (PDField)srcFieldsIterator.next();
/* 577 */         PDField destField = PDFieldFactory.createField(destAcroForm, (COSDictionary)cloner.cloneForNewDocument(srcField.getDictionary()));
/*     */ 
/* 581 */         if (destAcroForm.getField(destField.getFullyQualifiedName()) != null)
/*     */         {
/* 583 */           destField.setPartialName("dummyFieldName" + this.nextFieldNum++);
/*     */         }
/* 585 */         destFields.add(destField);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isIgnoreAcroFormErrors()
/*     */   {
/* 597 */     return this.ignoreAcroFormErrors;
/*     */   }
/*     */ 
/*     */   public void setIgnoreAcroFormErrors(boolean ignoreAcroFormErrorsValue)
/*     */   {
/* 608 */     this.ignoreAcroFormErrors = ignoreAcroFormErrorsValue;
/*     */   }
/*     */ 
/*     */   private void updatePageReferences(COSDictionary parentTreeEntry, HashMap<COSDictionary, COSDictionary> objMapping)
/*     */   {
/* 619 */     COSBase page = parentTreeEntry.getDictionaryObject(COSName.PG);
/* 620 */     if ((page instanceof COSDictionary))
/*     */     {
/* 622 */       if (objMapping.containsKey(page))
/*     */       {
/* 624 */         parentTreeEntry.setItem(COSName.PG, (COSBase)objMapping.get(page));
/*     */       }
/*     */     }
/* 627 */     COSBase obj = parentTreeEntry.getDictionaryObject(COSName.OBJ);
/* 628 */     if ((obj instanceof COSDictionary))
/*     */     {
/* 630 */       if (objMapping.containsKey(obj))
/*     */       {
/* 632 */         parentTreeEntry.setItem(COSName.OBJ, (COSBase)objMapping.get(obj));
/*     */       }
/*     */     }
/* 635 */     COSBase kSubEntry = parentTreeEntry.getDictionaryObject(COSName.K);
/* 636 */     if ((kSubEntry instanceof COSArray))
/*     */     {
/* 638 */       updatePageReferences((COSArray)kSubEntry, objMapping);
/*     */     }
/* 640 */     else if ((kSubEntry instanceof COSDictionary))
/*     */     {
/* 642 */       updatePageReferences((COSDictionary)kSubEntry, objMapping);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void updatePageReferences(COSArray parentTreeEntry, HashMap<COSDictionary, COSDictionary> objMapping)
/*     */   {
/* 648 */     for (int i = 0; i < parentTreeEntry.size(); i++)
/*     */     {
/* 650 */       COSBase subEntry = parentTreeEntry.getObject(i);
/* 651 */       if ((subEntry instanceof COSArray))
/*     */       {
/* 653 */         updatePageReferences((COSArray)subEntry, objMapping);
/*     */       }
/* 655 */       else if ((subEntry instanceof COSDictionary))
/*     */       {
/* 657 */         updatePageReferences((COSDictionary)subEntry, objMapping);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void updateParentEntry(COSArray kArray, COSDictionary newParent)
/*     */   {
/* 670 */     for (int i = 0; i < kArray.size(); i++)
/*     */     {
/* 672 */       COSBase subEntry = kArray.getObject(i);
/* 673 */       if ((subEntry instanceof COSDictionary))
/*     */       {
/* 675 */         COSDictionary dictEntry = (COSDictionary)subEntry;
/* 676 */         if (dictEntry.getDictionaryObject(COSName.P) != null)
/*     */         {
/* 678 */           dictEntry.setItem(COSName.P, newParent);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void updateStructParentEntries(PDPage page, int structParentOffset)
/*     */     throws IOException
/*     */   {
/* 692 */     page.setStructParents(page.getStructParents() + structParentOffset);
/* 693 */     List annots = page.getAnnotations();
/* 694 */     List newannots = new ArrayList();
/* 695 */     for (PDAnnotation annot : annots)
/*     */     {
/* 697 */       annot.setStructParent(annot.getStructParent() + structParentOffset);
/* 698 */       newannots.add(annot);
/*     */     }
/* 700 */     page.setAnnotations(newannots);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.PDFMergerUtility
 * JD-Core Version:    0.6.2
 */