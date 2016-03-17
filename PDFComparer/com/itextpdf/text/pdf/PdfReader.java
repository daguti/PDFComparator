/*      */ package com.itextpdf.text.pdf;
/*      */ 
/*      */ import com.itextpdf.text.DocWriter;
/*      */ import com.itextpdf.text.Document;
/*      */ import com.itextpdf.text.ExceptionConverter;
/*      */ import com.itextpdf.text.PageSize;
/*      */ import com.itextpdf.text.Rectangle;
/*      */ import com.itextpdf.text.error_messages.MessageLocalization;
/*      */ import com.itextpdf.text.exceptions.BadPasswordException;
/*      */ import com.itextpdf.text.exceptions.InvalidPdfException;
/*      */ import com.itextpdf.text.exceptions.UnsupportedPdfException;
/*      */ import com.itextpdf.text.io.RandomAccessSource;
/*      */ import com.itextpdf.text.io.RandomAccessSourceFactory;
/*      */ import com.itextpdf.text.io.WindowRandomAccessSource;
/*      */ import com.itextpdf.text.log.Counter;
/*      */ import com.itextpdf.text.log.CounterFactory;
/*      */ import com.itextpdf.text.log.Level;
/*      */ import com.itextpdf.text.log.Logger;
/*      */ import com.itextpdf.text.log.LoggerFactory;
/*      */ import com.itextpdf.text.pdf.interfaces.PdfViewerPreferences;
/*      */ import com.itextpdf.text.pdf.internal.PdfViewerPreferencesImp;
/*      */ import com.itextpdf.text.pdf.security.ExternalDecryptionProcess;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.DataInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.net.URL;
/*      */ import java.security.Key;
/*      */ import java.security.MessageDigest;
/*      */ import java.security.PrivateKey;
/*      */ import java.security.cert.Certificate;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Set;
/*      */ import java.util.Stack;
/*      */ import java.util.zip.InflaterInputStream;
/*      */ import org.bouncycastle.cert.X509CertificateHolder;
/*      */ import org.bouncycastle.cms.CMSEnvelopedData;
/*      */ import org.bouncycastle.cms.RecipientId;
/*      */ import org.bouncycastle.cms.RecipientInformation;
/*      */ import org.bouncycastle.cms.RecipientInformationStore;
/*      */ 
/*      */ public class PdfReader
/*      */   implements PdfViewerPreferences
/*      */ {
/*   88 */   public static boolean unethicalreading = false;
/*      */ 
/*   90 */   public static boolean debugmode = false;
/*   91 */   private static final Logger LOGGER = LoggerFactory.getLogger(PdfReader.class);
/*      */ 
/*   93 */   static final PdfName[] pageInhCandidates = { PdfName.MEDIABOX, PdfName.ROTATE, PdfName.RESOURCES, PdfName.CROPBOX };
/*      */ 
/*   97 */   static final byte[] endstream = PdfEncodings.convertToBytes("endstream", null);
/*   98 */   static final byte[] endobj = PdfEncodings.convertToBytes("endobj", null);
/*      */   protected PRTokeniser tokens;
/*      */   protected long[] xref;
/*      */   protected HashMap<Integer, IntHashtable> objStmMark;
/*      */   protected LongHashtable objStmToOffset;
/*      */   protected boolean newXrefType;
/*      */   protected ArrayList<PdfObject> xrefObj;
/*      */   PdfDictionary rootPages;
/*      */   protected PdfDictionary trailer;
/*      */   protected PdfDictionary catalog;
/*      */   protected PageRefs pageRefs;
/*  113 */   protected PRAcroForm acroForm = null;
/*  114 */   protected boolean acroFormParsed = false;
/*  115 */   protected boolean encrypted = false;
/*  116 */   protected boolean rebuilt = false;
/*      */   protected int freeXref;
/*  118 */   protected boolean tampered = false;
/*      */   protected long lastXref;
/*      */   protected long eofPos;
/*      */   protected char pdfVersion;
/*      */   protected PdfEncryption decrypt;
/*  123 */   protected byte[] password = null;
/*  124 */   protected Key certificateKey = null;
/*  125 */   protected Certificate certificate = null;
/*  126 */   protected String certificateKeyProvider = null;
/*  127 */   protected ExternalDecryptionProcess externalDecryptionProcess = null;
/*      */   private boolean ownerPasswordUsed;
/*  129 */   protected ArrayList<PdfString> strings = new ArrayList();
/*  130 */   protected boolean sharedStreams = true;
/*  131 */   protected boolean consolidateNamedDestinations = false;
/*  132 */   protected boolean remoteToLocalNamedDestinations = false;
/*      */   protected int rValue;
/*      */   protected int pValue;
/*      */   private int objNum;
/*      */   private int objGen;
/*      */   private long fileLength;
/*      */   private boolean hybridXref;
/*  139 */   private int lastXrefPartial = -1;
/*      */   private boolean partial;
/*      */   private PRIndirectReference cryptoRef;
/*  143 */   private final PdfViewerPreferencesImp viewerPreferences = new PdfViewerPreferencesImp();
/*      */   private boolean encryptionError;
/*      */   private boolean appendable;
/*  151 */   protected static Counter COUNTER = CounterFactory.getCounter(PdfReader.class);
/*      */ 
/* 1828 */   private int readDepth = 0;
/*      */ 
/*      */   protected Counter getCounter()
/*      */   {
/*  153 */     return COUNTER;
/*      */   }
/*      */ 
/*      */   private PdfReader(RandomAccessSource byteSource, boolean partialRead, byte[] ownerPassword, Certificate certificate, Key certificateKey, String certificateKeyProvider, ExternalDecryptionProcess externalDecryptionProcess, boolean closeSourceOnConstructorError)
/*      */     throws IOException
/*      */   {
/*  168 */     this.certificate = certificate;
/*  169 */     this.certificateKey = certificateKey;
/*  170 */     this.certificateKeyProvider = certificateKeyProvider;
/*  171 */     this.externalDecryptionProcess = externalDecryptionProcess;
/*  172 */     this.password = ownerPassword;
/*  173 */     this.partial = partialRead;
/*      */     try
/*      */     {
/*  176 */       this.tokens = getOffsetTokeniser(byteSource);
/*      */ 
/*  178 */       if (partialRead)
/*  179 */         readPdfPartial();
/*      */       else
/*  181 */         readPdf();
/*      */     }
/*      */     catch (IOException e) {
/*  184 */       if (closeSourceOnConstructorError)
/*  185 */         byteSource.close();
/*  186 */       throw e;
/*      */     }
/*  188 */     getCounter().read(this.fileLength);
/*      */   }
/*      */ 
/*      */   public PdfReader(String filename)
/*      */     throws IOException
/*      */   {
/*  197 */     this(filename, (byte[])null);
/*      */   }
/*      */ 
/*      */   public PdfReader(String filename, byte[] ownerPassword)
/*      */     throws IOException
/*      */   {
/*  207 */     this(filename, ownerPassword, false);
/*      */   }
/*      */ 
/*      */   public PdfReader(String filename, byte[] ownerPassword, boolean partial)
/*      */     throws IOException
/*      */   {
/*  219 */     this(new RandomAccessSourceFactory().setForceRead(false).setUsePlainRandomAccess(Document.plainRandomAccess).createBestSource(filename), partial, ownerPassword, null, null, null, null, true);
/*      */   }
/*      */ 
/*      */   public PdfReader(byte[] pdfIn)
/*      */     throws IOException
/*      */   {
/*  240 */     this(pdfIn, null);
/*      */   }
/*      */ 
/*      */   public PdfReader(byte[] pdfIn, byte[] ownerPassword)
/*      */     throws IOException
/*      */   {
/*  250 */     this(new RandomAccessSourceFactory().createSource(pdfIn), false, ownerPassword, null, null, null, null, true);
/*      */   }
/*      */ 
/*      */   public PdfReader(String filename, Certificate certificate, Key certificateKey, String certificateKeyProvider)
/*      */     throws IOException
/*      */   {
/*  272 */     this(new RandomAccessSourceFactory().setForceRead(false).setUsePlainRandomAccess(Document.plainRandomAccess).createBestSource(filename), false, null, certificate, certificateKey, certificateKeyProvider, null, true);
/*      */   }
/*      */ 
/*      */   public PdfReader(String filename, ExternalDecryptionProcess externalDecryptionProcess)
/*      */     throws IOException
/*      */   {
/*  297 */     this(new RandomAccessSourceFactory().setForceRead(false).setUsePlainRandomAccess(Document.plainRandomAccess).createBestSource(filename), false, null, null, null, null, externalDecryptionProcess, true);
/*      */   }
/*      */ 
/*      */   public PdfReader(URL url)
/*      */     throws IOException
/*      */   {
/*  319 */     this(url, null);
/*      */   }
/*      */ 
/*      */   public PdfReader(URL url, byte[] ownerPassword)
/*      */     throws IOException
/*      */   {
/*  329 */     this(new RandomAccessSourceFactory().createSource(url), false, ownerPassword, null, null, null, null, true);
/*      */   }
/*      */ 
/*      */   public PdfReader(InputStream is, byte[] ownerPassword)
/*      */     throws IOException
/*      */   {
/*  350 */     this(new RandomAccessSourceFactory().createSource(is), false, ownerPassword, null, null, null, null, false);
/*      */   }
/*      */ 
/*      */   public PdfReader(InputStream is)
/*      */     throws IOException
/*      */   {
/*  370 */     this(is, null);
/*      */   }
/*      */ 
/*      */   public PdfReader(RandomAccessFileOrArray raf, byte[] ownerPassword)
/*      */     throws IOException
/*      */   {
/*  382 */     this(raf.getByteSource(), true, ownerPassword, null, null, null, null, false);
/*      */   }
/*      */ 
/*      */   public PdfReader(PdfReader reader)
/*      */   {
/*  398 */     this.appendable = reader.appendable;
/*  399 */     this.consolidateNamedDestinations = reader.consolidateNamedDestinations;
/*  400 */     this.encrypted = reader.encrypted;
/*  401 */     this.rebuilt = reader.rebuilt;
/*  402 */     this.sharedStreams = reader.sharedStreams;
/*  403 */     this.tampered = reader.tampered;
/*  404 */     this.password = reader.password;
/*  405 */     this.pdfVersion = reader.pdfVersion;
/*  406 */     this.eofPos = reader.eofPos;
/*  407 */     this.freeXref = reader.freeXref;
/*  408 */     this.lastXref = reader.lastXref;
/*  409 */     this.newXrefType = reader.newXrefType;
/*  410 */     this.tokens = new PRTokeniser(reader.tokens.getSafeFile());
/*  411 */     if (reader.decrypt != null)
/*  412 */       this.decrypt = new PdfEncryption(reader.decrypt);
/*  413 */     this.pValue = reader.pValue;
/*  414 */     this.rValue = reader.rValue;
/*  415 */     this.xrefObj = new ArrayList(reader.xrefObj);
/*  416 */     for (int k = 0; k < reader.xrefObj.size(); k++) {
/*  417 */       this.xrefObj.set(k, duplicatePdfObject((PdfObject)reader.xrefObj.get(k), this));
/*      */     }
/*  419 */     this.pageRefs = new PageRefs(reader.pageRefs, this);
/*  420 */     this.trailer = ((PdfDictionary)duplicatePdfObject(reader.trailer, this));
/*  421 */     this.catalog = this.trailer.getAsDict(PdfName.ROOT);
/*  422 */     this.rootPages = this.catalog.getAsDict(PdfName.PAGES);
/*  423 */     this.fileLength = reader.fileLength;
/*  424 */     this.partial = reader.partial;
/*  425 */     this.hybridXref = reader.hybridXref;
/*  426 */     this.objStmToOffset = reader.objStmToOffset;
/*  427 */     this.xref = reader.xref;
/*  428 */     this.cryptoRef = ((PRIndirectReference)duplicatePdfObject(reader.cryptoRef, this));
/*  429 */     this.ownerPasswordUsed = reader.ownerPasswordUsed;
/*      */   }
/*      */ 
/*      */   private static PRTokeniser getOffsetTokeniser(RandomAccessSource byteSource)
/*      */     throws IOException
/*      */   {
/*  440 */     PRTokeniser tok = new PRTokeniser(new RandomAccessFileOrArray(byteSource));
/*  441 */     int offset = tok.getHeaderOffset();
/*  442 */     if (offset != 0) {
/*  443 */       RandomAccessSource offsetSource = new WindowRandomAccessSource(byteSource, offset);
/*  444 */       tok = new PRTokeniser(new RandomAccessFileOrArray(offsetSource));
/*      */     }
/*  446 */     return tok;
/*      */   }
/*      */ 
/*      */   public RandomAccessFileOrArray getSafeFile()
/*      */   {
/*  454 */     return this.tokens.getSafeFile();
/*      */   }
/*      */ 
/*      */   protected PdfReaderInstance getPdfReaderInstance(PdfWriter writer) {
/*  458 */     return new PdfReaderInstance(this, writer);
/*      */   }
/*      */ 
/*      */   public int getNumberOfPages()
/*      */   {
/*  465 */     return this.pageRefs.size();
/*      */   }
/*      */ 
/*      */   public PdfDictionary getCatalog()
/*      */   {
/*  474 */     return this.catalog;
/*      */   }
/*      */ 
/*      */   public PRAcroForm getAcroForm()
/*      */   {
/*  482 */     if (!this.acroFormParsed) {
/*  483 */       this.acroFormParsed = true;
/*  484 */       PdfObject form = this.catalog.get(PdfName.ACROFORM);
/*  485 */       if (form != null) {
/*      */         try {
/*  487 */           this.acroForm = new PRAcroForm(this);
/*  488 */           this.acroForm.readAcroForm((PdfDictionary)getPdfObject(form));
/*      */         }
/*      */         catch (Exception e) {
/*  491 */           this.acroForm = null;
/*      */         }
/*      */       }
/*      */     }
/*  495 */     return this.acroForm;
/*      */   }
/*      */ 
/*      */   public int getPageRotation(int index)
/*      */   {
/*  503 */     return getPageRotation(this.pageRefs.getPageNRelease(index));
/*      */   }
/*      */ 
/*      */   int getPageRotation(PdfDictionary page) {
/*  507 */     PdfNumber rotate = page.getAsNumber(PdfName.ROTATE);
/*  508 */     if (rotate == null) {
/*  509 */       return 0;
/*      */     }
/*  511 */     int n = rotate.intValue();
/*  512 */     n %= 360;
/*  513 */     return n < 0 ? n + 360 : n;
/*      */   }
/*      */ 
/*      */   public Rectangle getPageSizeWithRotation(int index)
/*      */   {
/*  522 */     return getPageSizeWithRotation(this.pageRefs.getPageNRelease(index));
/*      */   }
/*      */ 
/*      */   public Rectangle getPageSizeWithRotation(PdfDictionary page)
/*      */   {
/*  531 */     Rectangle rect = getPageSize(page);
/*  532 */     int rotation = getPageRotation(page);
/*  533 */     while (rotation > 0) {
/*  534 */       rect = rect.rotate();
/*  535 */       rotation -= 90;
/*      */     }
/*  537 */     return rect;
/*      */   }
/*      */ 
/*      */   public Rectangle getPageSize(int index)
/*      */   {
/*  546 */     return getPageSize(this.pageRefs.getPageNRelease(index));
/*      */   }
/*      */ 
/*      */   public Rectangle getPageSize(PdfDictionary page)
/*      */   {
/*  555 */     PdfArray mediaBox = page.getAsArray(PdfName.MEDIABOX);
/*  556 */     return getNormalizedRectangle(mediaBox);
/*      */   }
/*      */ 
/*      */   public Rectangle getCropBox(int index)
/*      */   {
/*  568 */     PdfDictionary page = this.pageRefs.getPageNRelease(index);
/*  569 */     PdfArray cropBox = (PdfArray)getPdfObjectRelease(page.get(PdfName.CROPBOX));
/*  570 */     if (cropBox == null)
/*  571 */       return getPageSize(page);
/*  572 */     return getNormalizedRectangle(cropBox);
/*      */   }
/*      */ 
/*      */   public Rectangle getBoxSize(int index, String boxName)
/*      */   {
/*  581 */     PdfDictionary page = this.pageRefs.getPageNRelease(index);
/*  582 */     PdfArray box = null;
/*  583 */     if (boxName.equals("trim"))
/*  584 */       box = (PdfArray)getPdfObjectRelease(page.get(PdfName.TRIMBOX));
/*  585 */     else if (boxName.equals("art"))
/*  586 */       box = (PdfArray)getPdfObjectRelease(page.get(PdfName.ARTBOX));
/*  587 */     else if (boxName.equals("bleed"))
/*  588 */       box = (PdfArray)getPdfObjectRelease(page.get(PdfName.BLEEDBOX));
/*  589 */     else if (boxName.equals("crop"))
/*  590 */       box = (PdfArray)getPdfObjectRelease(page.get(PdfName.CROPBOX));
/*  591 */     else if (boxName.equals("media"))
/*  592 */       box = (PdfArray)getPdfObjectRelease(page.get(PdfName.MEDIABOX));
/*  593 */     if (box == null)
/*  594 */       return null;
/*  595 */     return getNormalizedRectangle(box);
/*      */   }
/*      */ 
/*      */   public HashMap<String, String> getInfo()
/*      */   {
/*  604 */     HashMap map = new HashMap();
/*  605 */     PdfDictionary info = this.trailer.getAsDict(PdfName.INFO);
/*  606 */     if (info == null)
/*  607 */       return map;
/*  608 */     for (Object element : info.getKeys()) {
/*  609 */       PdfName key = (PdfName)element;
/*  610 */       PdfObject obj = getPdfObject(info.get(key));
/*  611 */       if (obj != null)
/*      */       {
/*  613 */         String value = obj.toString();
/*  614 */         switch (obj.type()) {
/*      */         case 3:
/*  616 */           value = ((PdfString)obj).toUnicodeString();
/*  617 */           break;
/*      */         case 4:
/*  620 */           value = PdfName.decodeName(value);
/*      */         }
/*      */ 
/*  624 */         map.put(PdfName.decodeName(key.toString()), value);
/*      */       }
/*      */     }
/*  626 */     return map;
/*      */   }
/*      */ 
/*      */   public static Rectangle getNormalizedRectangle(PdfArray box)
/*      */   {
/*  634 */     float llx = ((PdfNumber)getPdfObjectRelease(box.getPdfObject(0))).floatValue();
/*  635 */     float lly = ((PdfNumber)getPdfObjectRelease(box.getPdfObject(1))).floatValue();
/*  636 */     float urx = ((PdfNumber)getPdfObjectRelease(box.getPdfObject(2))).floatValue();
/*  637 */     float ury = ((PdfNumber)getPdfObjectRelease(box.getPdfObject(3))).floatValue();
/*  638 */     return new Rectangle(Math.min(llx, urx), Math.min(lly, ury), Math.max(llx, urx), Math.max(lly, ury));
/*      */   }
/*      */ 
/*      */   public boolean isTagged()
/*      */   {
/*  646 */     PdfDictionary markInfo = this.catalog.getAsDict(PdfName.MARKINFO);
/*  647 */     if (markInfo == null)
/*  648 */       return false;
/*  649 */     return PdfBoolean.PDFTRUE.equals(markInfo.getAsBoolean(PdfName.MARKED));
/*      */   }
/*      */ 
/*      */   protected void readPdf()
/*      */     throws IOException
/*      */   {
/*  656 */     this.fileLength = this.tokens.getFile().length();
/*  657 */     this.pdfVersion = this.tokens.checkPdfHeader();
/*      */     try {
/*  659 */       readXref();
/*      */     }
/*      */     catch (Exception e) {
/*      */       try {
/*  663 */         this.rebuilt = true;
/*  664 */         rebuildXref();
/*  665 */         this.lastXref = -1L;
/*      */       }
/*      */       catch (Exception ne) {
/*  668 */         throw new InvalidPdfException(MessageLocalization.getComposedMessage("rebuild.failed.1.original.message.2", new Object[] { ne.getMessage(), e.getMessage() }));
/*      */       }
/*      */     }
/*      */     try {
/*  672 */       readDocObj();
/*      */     }
/*      */     catch (Exception e) {
/*  675 */       if ((e instanceof BadPasswordException))
/*  676 */         throw new BadPasswordException(e.getMessage());
/*  677 */       if ((this.rebuilt) || (this.encryptionError))
/*  678 */         throw new InvalidPdfException(e.getMessage());
/*  679 */       this.rebuilt = true;
/*  680 */       this.encrypted = false;
/*      */       try {
/*  682 */         rebuildXref();
/*  683 */         this.lastXref = -1L;
/*  684 */         readDocObj();
/*      */       } catch (Exception ne) {
/*  686 */         throw new InvalidPdfException(MessageLocalization.getComposedMessage("rebuild.failed.1.original.message.2", new Object[] { ne.getMessage(), e.getMessage() }));
/*      */       }
/*      */     }
/*  689 */     this.strings.clear();
/*  690 */     readPages();
/*      */ 
/*  692 */     removeUnusedObjects();
/*      */   }
/*      */ 
/*      */   protected void readPdfPartial() throws IOException
/*      */   {
/*  697 */     this.fileLength = this.tokens.getFile().length();
/*  698 */     this.pdfVersion = this.tokens.checkPdfHeader();
/*      */     try {
/*  700 */       readXref();
/*      */     }
/*      */     catch (Exception e) {
/*      */       try {
/*  704 */         this.rebuilt = true;
/*  705 */         rebuildXref();
/*  706 */         this.lastXref = -1L;
/*      */       } catch (Exception ne) {
/*  708 */         throw new InvalidPdfException(MessageLocalization.getComposedMessage("rebuild.failed.1.original.message.2", new Object[] { ne.getMessage(), e.getMessage() }), ne);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  714 */     readDocObjPartial();
/*  715 */     readPages();
/*      */   }
/*      */ 
/*      */   private boolean equalsArray(byte[] ar1, byte[] ar2, int size) {
/*  719 */     for (int k = 0; k < size; k++) {
/*  720 */       if (ar1[k] != ar2[k])
/*  721 */         return false;
/*      */     }
/*  723 */     return true;
/*      */   }
/*      */ 
/*      */   private void readDecryptedDocObj()
/*      */     throws IOException
/*      */   {
/*  731 */     if (this.encrypted)
/*  732 */       return;
/*  733 */     PdfObject encDic = this.trailer.get(PdfName.ENCRYPT);
/*  734 */     if ((encDic == null) || (encDic.toString().equals("null")))
/*  735 */       return;
/*  736 */     this.encryptionError = true;
/*  737 */     byte[] encryptionKey = null;
/*  738 */     this.encrypted = true;
/*  739 */     PdfDictionary enc = (PdfDictionary)getPdfObject(encDic);
/*      */ 
/*  744 */     PdfArray documentIDs = this.trailer.getAsArray(PdfName.ID);
/*  745 */     byte[] documentID = null;
/*  746 */     if (documentIDs != null) {
/*  747 */       PdfObject o = documentIDs.getPdfObject(0);
/*  748 */       this.strings.remove(o);
/*  749 */       String s = o.toString();
/*  750 */       documentID = DocWriter.getISOBytes(s);
/*  751 */       if (documentIDs.size() > 1) {
/*  752 */         this.strings.remove(documentIDs.getPdfObject(1));
/*      */       }
/*      */     }
/*  755 */     if (documentID == null)
/*  756 */       documentID = new byte[0];
/*  757 */     byte[] uValue = null;
/*  758 */     byte[] oValue = null;
/*  759 */     int cryptoMode = 0;
/*  760 */     int lengthValue = 0;
/*      */ 
/*  762 */     PdfObject filter = getPdfObjectRelease(enc.get(PdfName.FILTER));
/*      */ 
/*  764 */     if (filter.equals(PdfName.STANDARD)) {
/*  765 */       String s = enc.get(PdfName.U).toString();
/*  766 */       this.strings.remove(enc.get(PdfName.U));
/*  767 */       uValue = DocWriter.getISOBytes(s);
/*  768 */       s = enc.get(PdfName.O).toString();
/*  769 */       this.strings.remove(enc.get(PdfName.O));
/*  770 */       oValue = DocWriter.getISOBytes(s);
/*  771 */       if (enc.contains(PdfName.OE))
/*  772 */         this.strings.remove(enc.get(PdfName.OE));
/*  773 */       if (enc.contains(PdfName.UE))
/*  774 */         this.strings.remove(enc.get(PdfName.UE));
/*  775 */       if (enc.contains(PdfName.PERMS)) {
/*  776 */         this.strings.remove(enc.get(PdfName.PERMS));
/*      */       }
/*  778 */       PdfObject o = enc.get(PdfName.P);
/*  779 */       if (!o.isNumber())
/*  780 */         throw new InvalidPdfException(MessageLocalization.getComposedMessage("illegal.p.value", new Object[0]));
/*  781 */       this.pValue = ((PdfNumber)o).intValue();
/*      */ 
/*  783 */       o = enc.get(PdfName.R);
/*  784 */       if (!o.isNumber())
/*  785 */         throw new InvalidPdfException(MessageLocalization.getComposedMessage("illegal.r.value", new Object[0]));
/*  786 */       this.rValue = ((PdfNumber)o).intValue();
/*      */ 
/*  788 */       switch (this.rValue) {
/*      */       case 2:
/*  790 */         cryptoMode = 0;
/*  791 */         break;
/*      */       case 3:
/*  793 */         o = enc.get(PdfName.LENGTH);
/*  794 */         if (!o.isNumber())
/*  795 */           throw new InvalidPdfException(MessageLocalization.getComposedMessage("illegal.length.value", new Object[0]));
/*  796 */         lengthValue = ((PdfNumber)o).intValue();
/*  797 */         if ((lengthValue > 128) || (lengthValue < 40) || (lengthValue % 8 != 0))
/*  798 */           throw new InvalidPdfException(MessageLocalization.getComposedMessage("illegal.length.value", new Object[0]));
/*  799 */         cryptoMode = 1;
/*  800 */         break;
/*      */       case 4:
/*  802 */         PdfDictionary dic = (PdfDictionary)enc.get(PdfName.CF);
/*  803 */         if (dic == null)
/*  804 */           throw new InvalidPdfException(MessageLocalization.getComposedMessage("cf.not.found.encryption", new Object[0]));
/*  805 */         dic = (PdfDictionary)dic.get(PdfName.STDCF);
/*  806 */         if (dic == null)
/*  807 */           throw new InvalidPdfException(MessageLocalization.getComposedMessage("stdcf.not.found.encryption", new Object[0]));
/*  808 */         if (PdfName.V2.equals(dic.get(PdfName.CFM)))
/*  809 */           cryptoMode = 1;
/*  810 */         else if (PdfName.AESV2.equals(dic.get(PdfName.CFM)))
/*  811 */           cryptoMode = 2;
/*      */         else
/*  813 */           throw new UnsupportedPdfException(MessageLocalization.getComposedMessage("no.compatible.encryption.found", new Object[0]));
/*  814 */         PdfObject em = enc.get(PdfName.ENCRYPTMETADATA);
/*  815 */         if ((em != null) && (em.toString().equals("false")))
/*  816 */           cryptoMode |= 8; break;
/*      */       case 5:
/*  819 */         cryptoMode = 3;
/*  820 */         PdfObject em5 = enc.get(PdfName.ENCRYPTMETADATA);
/*  821 */         if ((em5 != null) && (em5.toString().equals("false")))
/*  822 */           cryptoMode |= 8; break;
/*      */       default:
/*  825 */         throw new UnsupportedPdfException(MessageLocalization.getComposedMessage("unknown.encryption.type.r.eq.1", this.rValue));
/*      */       }
/*      */     }
/*  828 */     else if (filter.equals(PdfName.PUBSEC)) {
/*  829 */       boolean foundRecipient = false;
/*  830 */       byte[] envelopedData = null;
/*  831 */       PdfArray recipients = null;
/*      */ 
/*  833 */       PdfObject o = enc.get(PdfName.V);
/*  834 */       if (!o.isNumber())
/*  835 */         throw new InvalidPdfException(MessageLocalization.getComposedMessage("illegal.v.value", new Object[0]));
/*  836 */       int vValue = ((PdfNumber)o).intValue();
/*  837 */       switch (vValue) {
/*      */       case 1:
/*  839 */         cryptoMode = 0;
/*  840 */         lengthValue = 40;
/*  841 */         recipients = (PdfArray)enc.get(PdfName.RECIPIENTS);
/*  842 */         break;
/*      */       case 2:
/*  844 */         o = enc.get(PdfName.LENGTH);
/*  845 */         if (!o.isNumber())
/*  846 */           throw new InvalidPdfException(MessageLocalization.getComposedMessage("illegal.length.value", new Object[0]));
/*  847 */         lengthValue = ((PdfNumber)o).intValue();
/*  848 */         if ((lengthValue > 128) || (lengthValue < 40) || (lengthValue % 8 != 0))
/*  849 */           throw new InvalidPdfException(MessageLocalization.getComposedMessage("illegal.length.value", new Object[0]));
/*  850 */         cryptoMode = 1;
/*  851 */         recipients = (PdfArray)enc.get(PdfName.RECIPIENTS);
/*  852 */         break;
/*      */       case 4:
/*      */       case 5:
/*  855 */         PdfDictionary dic = (PdfDictionary)enc.get(PdfName.CF);
/*  856 */         if (dic == null)
/*  857 */           throw new InvalidPdfException(MessageLocalization.getComposedMessage("cf.not.found.encryption", new Object[0]));
/*  858 */         dic = (PdfDictionary)dic.get(PdfName.DEFAULTCRYPTFILTER);
/*  859 */         if (dic == null)
/*  860 */           throw new InvalidPdfException(MessageLocalization.getComposedMessage("defaultcryptfilter.not.found.encryption", new Object[0]));
/*  861 */         if (PdfName.V2.equals(dic.get(PdfName.CFM))) {
/*  862 */           cryptoMode = 1;
/*  863 */           lengthValue = 128;
/*      */         }
/*  865 */         else if (PdfName.AESV2.equals(dic.get(PdfName.CFM))) {
/*  866 */           cryptoMode = 2;
/*  867 */           lengthValue = 128;
/*      */         }
/*  869 */         else if (PdfName.AESV3.equals(dic.get(PdfName.CFM))) {
/*  870 */           cryptoMode = 3;
/*  871 */           lengthValue = 256;
/*      */         }
/*      */         else {
/*  874 */           throw new UnsupportedPdfException(MessageLocalization.getComposedMessage("no.compatible.encryption.found", new Object[0]));
/*  875 */         }PdfObject em = dic.get(PdfName.ENCRYPTMETADATA);
/*  876 */         if ((em != null) && (em.toString().equals("false"))) {
/*  877 */           cryptoMode |= 8;
/*      */         }
/*  879 */         recipients = (PdfArray)dic.get(PdfName.RECIPIENTS);
/*  880 */         break;
/*      */       case 3:
/*      */       default:
/*  882 */         throw new UnsupportedPdfException(MessageLocalization.getComposedMessage("unknown.encryption.type.v.eq.1", vValue));
/*      */       }
/*      */       X509CertificateHolder certHolder;
/*      */       try {
/*  886 */         certHolder = new X509CertificateHolder(this.certificate.getEncoded());
/*      */       }
/*      */       catch (Exception f) {
/*  889 */         throw new ExceptionConverter(f);
/*      */       }
/*  891 */       if (this.externalDecryptionProcess == null)
/*  892 */         for (int i = 0; i < recipients.size(); i++) {
/*  893 */           PdfObject recipient = recipients.getPdfObject(i);
/*  894 */           this.strings.remove(recipient);
/*      */ 
/*  896 */           CMSEnvelopedData data = null;
/*      */           try {
/*  898 */             data = new CMSEnvelopedData(recipient.getBytes());
/*      */ 
/*  900 */             Iterator recipientCertificatesIt = data.getRecipientInfos().getRecipients().iterator();
/*      */ 
/*  902 */             while (recipientCertificatesIt.hasNext()) {
/*  903 */               RecipientInformation recipientInfo = (RecipientInformation)recipientCertificatesIt.next();
/*      */ 
/*  905 */               if ((recipientInfo.getRID().match(certHolder)) && (!foundRecipient)) {
/*  906 */                 envelopedData = PdfEncryptor.getContent(recipientInfo, (PrivateKey)this.certificateKey, this.certificateKeyProvider);
/*  907 */                 foundRecipient = true;
/*      */               }
/*      */             }
/*      */           }
/*      */           catch (Exception f) {
/*  912 */             throw new ExceptionConverter(f);
/*      */           }
/*      */         }
/*      */       else {
/*  916 */         for (int i = 0; i < recipients.size(); i++) {
/*  917 */           PdfObject recipient = recipients.getPdfObject(i);
/*  918 */           this.strings.remove(recipient);
/*      */ 
/*  920 */           CMSEnvelopedData data = null;
/*      */           try {
/*  922 */             data = new CMSEnvelopedData(recipient.getBytes());
/*      */ 
/*  924 */             RecipientInformation recipientInfo = data.getRecipientInfos().get(this.externalDecryptionProcess.getCmsRecipientId());
/*      */ 
/*  927 */             if (recipientInfo != null) {
/*  928 */               envelopedData = recipientInfo.getContent(this.externalDecryptionProcess.getCmsRecipient());
/*      */ 
/*  930 */               foundRecipient = true;
/*      */             }
/*      */           } catch (Exception f) {
/*  933 */             throw new ExceptionConverter(f);
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  938 */       if ((!foundRecipient) || (envelopedData == null)) {
/*  939 */         throw new UnsupportedPdfException(MessageLocalization.getComposedMessage("bad.certificate.and.key", new Object[0]));
/*      */       }
/*      */ 
/*  942 */       MessageDigest md = null;
/*      */       try
/*      */       {
/*  945 */         if ((cryptoMode & 0x7) == 3)
/*  946 */           md = MessageDigest.getInstance("SHA-256");
/*      */         else
/*  948 */           md = MessageDigest.getInstance("SHA-1");
/*  949 */         md.update(envelopedData, 0, 20);
/*  950 */         for (int i = 0; i < recipients.size(); i++) {
/*  951 */           byte[] encodedRecipient = recipients.getPdfObject(i).getBytes();
/*  952 */           md.update(encodedRecipient);
/*      */         }
/*  954 */         if ((cryptoMode & 0x8) != 0)
/*  955 */           md.update(new byte[] { -1, -1, -1, -1 });
/*  956 */         encryptionKey = md.digest();
/*      */       }
/*      */       catch (Exception f) {
/*  959 */         throw new ExceptionConverter(f);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  964 */     this.decrypt = new PdfEncryption();
/*  965 */     this.decrypt.setCryptoMode(cryptoMode, lengthValue);
/*      */ 
/*  967 */     if (filter.equals(PdfName.STANDARD)) {
/*  968 */       if (this.rValue == 5) {
/*  969 */         this.ownerPasswordUsed = this.decrypt.readKey(enc, this.password);
/*  970 */         this.pValue = this.decrypt.getPermissions();
/*      */       }
/*      */       else
/*      */       {
/*  974 */         this.decrypt.setupByOwnerPassword(documentID, this.password, uValue, oValue, this.pValue);
/*  975 */         if (!equalsArray(uValue, this.decrypt.userKey, (this.rValue == 3) || (this.rValue == 4) ? 16 : 32))
/*      */         {
/*  977 */           this.decrypt.setupByUserPassword(documentID, this.password, oValue, this.pValue);
/*  978 */           if (!equalsArray(uValue, this.decrypt.userKey, (this.rValue == 3) || (this.rValue == 4) ? 16 : 32))
/*  979 */             throw new BadPasswordException(MessageLocalization.getComposedMessage("bad.user.password", new Object[0]));
/*      */         }
/*      */         else
/*      */         {
/*  983 */           this.ownerPasswordUsed = true;
/*      */         }
/*      */       }
/*  986 */     } else if (filter.equals(PdfName.PUBSEC)) {
/*  987 */       if ((cryptoMode & 0x7) == 3)
/*  988 */         this.decrypt.setKey(encryptionKey);
/*      */       else
/*  990 */         this.decrypt.setupByEncryptionKey(encryptionKey, lengthValue);
/*  991 */       this.ownerPasswordUsed = true;
/*      */     }
/*      */ 
/*  994 */     for (int k = 0; k < this.strings.size(); k++) {
/*  995 */       PdfString str = (PdfString)this.strings.get(k);
/*  996 */       str.decrypt(this);
/*      */     }
/*      */ 
/*  999 */     if (encDic.isIndirect()) {
/* 1000 */       this.cryptoRef = ((PRIndirectReference)encDic);
/* 1001 */       this.xrefObj.set(this.cryptoRef.getNumber(), null);
/*      */     }
/* 1003 */     this.encryptionError = false;
/*      */   }
/*      */ 
/*      */   public static PdfObject getPdfObjectRelease(PdfObject obj)
/*      */   {
/* 1011 */     PdfObject obj2 = getPdfObject(obj);
/* 1012 */     releaseLastXrefPartial(obj);
/* 1013 */     return obj2;
/*      */   }
/*      */ 
/*      */   public static PdfObject getPdfObject(PdfObject obj)
/*      */   {
/* 1024 */     if (obj == null)
/* 1025 */       return null;
/* 1026 */     if (!obj.isIndirect())
/* 1027 */       return obj;
/*      */     try {
/* 1029 */       PRIndirectReference ref = (PRIndirectReference)obj;
/* 1030 */       int idx = ref.getNumber();
/* 1031 */       boolean appendable = ref.getReader().appendable;
/* 1032 */       obj = ref.getReader().getPdfObject(idx);
/* 1033 */       if (obj == null) {
/* 1034 */         return null;
/*      */       }
/*      */ 
/* 1037 */       if (appendable) {
/* 1038 */         switch (obj.type()) {
/*      */         case 8:
/* 1040 */           obj = new PdfNull();
/* 1041 */           break;
/*      */         case 1:
/* 1043 */           obj = new PdfBoolean(((PdfBoolean)obj).booleanValue());
/* 1044 */           break;
/*      */         case 4:
/* 1046 */           obj = new PdfName(obj.getBytes());
/*      */         }
/*      */ 
/* 1049 */         obj.setIndRef(ref);
/*      */       }
/* 1051 */       return obj;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1055 */       throw new ExceptionConverter(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static PdfObject getPdfObjectRelease(PdfObject obj, PdfObject parent)
/*      */   {
/* 1068 */     PdfObject obj2 = getPdfObject(obj, parent);
/* 1069 */     releaseLastXrefPartial(obj);
/* 1070 */     return obj2;
/*      */   }
/*      */ 
/*      */   public static PdfObject getPdfObject(PdfObject obj, PdfObject parent)
/*      */   {
/* 1079 */     if (obj == null)
/* 1080 */       return null;
/* 1081 */     if (!obj.isIndirect()) {
/* 1082 */       PRIndirectReference ref = null;
/* 1083 */       if ((parent != null) && ((ref = parent.getIndRef()) != null) && (ref.getReader().isAppendable())) {
/* 1084 */         switch (obj.type()) {
/*      */         case 8:
/* 1086 */           obj = new PdfNull();
/* 1087 */           break;
/*      */         case 1:
/* 1089 */           obj = new PdfBoolean(((PdfBoolean)obj).booleanValue());
/* 1090 */           break;
/*      */         case 4:
/* 1092 */           obj = new PdfName(obj.getBytes());
/*      */         }
/*      */ 
/* 1095 */         obj.setIndRef(ref);
/*      */       }
/* 1097 */       return obj;
/*      */     }
/* 1099 */     return getPdfObject(obj);
/*      */   }
/*      */ 
/*      */   public PdfObject getPdfObjectRelease(int idx)
/*      */   {
/* 1107 */     PdfObject obj = getPdfObject(idx);
/* 1108 */     releaseLastXrefPartial();
/* 1109 */     return obj;
/*      */   }
/*      */ 
/*      */   public PdfObject getPdfObject(int idx)
/*      */   {
/*      */     try
/*      */     {
/* 1118 */       this.lastXrefPartial = -1;
/* 1119 */       if ((idx < 0) || (idx >= this.xrefObj.size()))
/* 1120 */         return null;
/* 1121 */       PdfObject obj = (PdfObject)this.xrefObj.get(idx);
/* 1122 */       if ((!this.partial) || (obj != null))
/* 1123 */         return obj;
/* 1124 */       if (idx * 2 >= this.xref.length)
/* 1125 */         return null;
/* 1126 */       obj = readSingleObject(idx);
/* 1127 */       this.lastXrefPartial = -1;
/* 1128 */       if (obj != null)
/* 1129 */         this.lastXrefPartial = idx;
/* 1130 */       return obj;
/*      */     }
/*      */     catch (Exception e) {
/* 1133 */       throw new ExceptionConverter(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void resetLastXrefPartial()
/*      */   {
/* 1141 */     this.lastXrefPartial = -1;
/*      */   }
/*      */ 
/*      */   public void releaseLastXrefPartial()
/*      */   {
/* 1148 */     if ((this.partial) && (this.lastXrefPartial != -1)) {
/* 1149 */       this.xrefObj.set(this.lastXrefPartial, null);
/* 1150 */       this.lastXrefPartial = -1;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void releaseLastXrefPartial(PdfObject obj)
/*      */   {
/* 1158 */     if (obj == null)
/* 1159 */       return;
/* 1160 */     if (!obj.isIndirect())
/* 1161 */       return;
/* 1162 */     if (!(obj instanceof PRIndirectReference)) {
/* 1163 */       return;
/*      */     }
/* 1165 */     PRIndirectReference ref = (PRIndirectReference)obj;
/* 1166 */     PdfReader reader = ref.getReader();
/* 1167 */     if ((reader.partial) && (reader.lastXrefPartial != -1) && (reader.lastXrefPartial == ref.getNumber())) {
/* 1168 */       reader.xrefObj.set(reader.lastXrefPartial, null);
/*      */     }
/* 1170 */     reader.lastXrefPartial = -1;
/*      */   }
/*      */ 
/*      */   private void setXrefPartialObject(int idx, PdfObject obj) {
/* 1174 */     if ((!this.partial) || (idx < 0))
/* 1175 */       return;
/* 1176 */     this.xrefObj.set(idx, obj);
/*      */   }
/*      */ 
/*      */   public PRIndirectReference addPdfObject(PdfObject obj)
/*      */   {
/* 1184 */     this.xrefObj.add(obj);
/* 1185 */     return new PRIndirectReference(this, this.xrefObj.size() - 1);
/*      */   }
/*      */ 
/*      */   protected void readPages() throws IOException {
/* 1189 */     this.catalog = this.trailer.getAsDict(PdfName.ROOT);
/* 1190 */     if (this.catalog == null)
/* 1191 */       throw new InvalidPdfException(MessageLocalization.getComposedMessage("the.document.has.no.catalog.object", new Object[0]));
/* 1192 */     this.rootPages = this.catalog.getAsDict(PdfName.PAGES);
/* 1193 */     if (this.rootPages == null)
/* 1194 */       throw new InvalidPdfException(MessageLocalization.getComposedMessage("the.document.has.no.page.root", new Object[0]));
/* 1195 */     this.pageRefs = new PageRefs(this, null);
/*      */   }
/*      */ 
/*      */   protected void readDocObjPartial() throws IOException {
/* 1199 */     this.xrefObj = new ArrayList(this.xref.length / 2);
/* 1200 */     this.xrefObj.addAll(Collections.nCopies(this.xref.length / 2, null));
/* 1201 */     readDecryptedDocObj();
/* 1202 */     if (this.objStmToOffset != null) {
/* 1203 */       long[] keys = this.objStmToOffset.getKeys();
/* 1204 */       for (int k = 0; k < keys.length; k++) {
/* 1205 */         long n = keys[k];
/* 1206 */         this.objStmToOffset.put(n, this.xref[((int)(n * 2L))]);
/* 1207 */         this.xref[((int)(n * 2L))] = -1L;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected PdfObject readSingleObject(int k) throws IOException {
/* 1213 */     this.strings.clear();
/* 1214 */     int k2 = k * 2;
/* 1215 */     long pos = this.xref[k2];
/* 1216 */     if (pos < 0L)
/* 1217 */       return null;
/* 1218 */     if (this.xref[(k2 + 1)] > 0L)
/* 1219 */       pos = this.objStmToOffset.get(this.xref[(k2 + 1)]);
/* 1220 */     if (pos == 0L)
/* 1221 */       return null;
/* 1222 */     this.tokens.seek(pos);
/* 1223 */     this.tokens.nextValidToken();
/* 1224 */     if (this.tokens.getTokenType() != PRTokeniser.TokenType.NUMBER)
/* 1225 */       this.tokens.throwError(MessageLocalization.getComposedMessage("invalid.object.number", new Object[0]));
/* 1226 */     this.objNum = this.tokens.intValue();
/* 1227 */     this.tokens.nextValidToken();
/* 1228 */     if (this.tokens.getTokenType() != PRTokeniser.TokenType.NUMBER)
/* 1229 */       this.tokens.throwError(MessageLocalization.getComposedMessage("invalid.generation.number", new Object[0]));
/* 1230 */     this.objGen = this.tokens.intValue();
/* 1231 */     this.tokens.nextValidToken();
/* 1232 */     if (!this.tokens.getStringValue().equals("obj"))
/* 1233 */       this.tokens.throwError(MessageLocalization.getComposedMessage("token.obj.expected", new Object[0]));
/*      */     try
/*      */     {
/* 1236 */       obj = readPRObject();
/* 1237 */       for (int j = 0; j < this.strings.size(); j++) {
/* 1238 */         PdfString str = (PdfString)this.strings.get(j);
/* 1239 */         str.decrypt(this);
/*      */       }
/* 1241 */       if (obj.isStream())
/* 1242 */         checkPRStreamLength((PRStream)obj);
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/*      */       PdfObject obj;
/* 1246 */       if (debugmode) {
/* 1247 */         if (LOGGER.isLogging(Level.ERROR))
/* 1248 */           LOGGER.error(e.getMessage(), e);
/* 1249 */         obj = null;
/*      */       }
/*      */       else {
/* 1252 */         throw e;
/*      */       }
/*      */     }
/*      */     PdfObject obj;
/* 1254 */     if (this.xref[(k2 + 1)] > 0L) {
/* 1255 */       obj = readOneObjStm((PRStream)obj, (int)this.xref[k2]);
/*      */     }
/* 1257 */     this.xrefObj.set(k, obj);
/* 1258 */     return obj;
/*      */   }
/*      */ 
/*      */   protected PdfObject readOneObjStm(PRStream stream, int idx) throws IOException {
/* 1262 */     int first = stream.getAsNumber(PdfName.FIRST).intValue();
/* 1263 */     byte[] b = getStreamBytes(stream, this.tokens.getFile());
/* 1264 */     PRTokeniser saveTokens = this.tokens;
/* 1265 */     this.tokens = new PRTokeniser(new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(b)));
/*      */     try {
/* 1267 */       int address = 0;
/* 1268 */       boolean ok = true;
/* 1269 */       idx++;
/* 1270 */       for (int k = 0; k < idx; k++) {
/* 1271 */         ok = this.tokens.nextToken();
/* 1272 */         if (!ok)
/*      */           break;
/* 1274 */         if (this.tokens.getTokenType() != PRTokeniser.TokenType.NUMBER) {
/* 1275 */           ok = false;
/* 1276 */           break;
/*      */         }
/* 1278 */         ok = this.tokens.nextToken();
/* 1279 */         if (!ok)
/*      */           break;
/* 1281 */         if (this.tokens.getTokenType() != PRTokeniser.TokenType.NUMBER) {
/* 1282 */           ok = false;
/* 1283 */           break;
/*      */         }
/* 1285 */         address = this.tokens.intValue() + first;
/*      */       }
/* 1287 */       if (!ok)
/* 1288 */         throw new InvalidPdfException(MessageLocalization.getComposedMessage("error.reading.objstm", new Object[0]));
/* 1289 */       this.tokens.seek(address);
/* 1290 */       this.tokens.nextToken();
/*      */       PdfObject obj;
/*      */       PdfObject obj;
/* 1292 */       if (this.tokens.getTokenType() == PRTokeniser.TokenType.NUMBER) {
/* 1293 */         obj = new PdfNumber(this.tokens.getStringValue());
/*      */       }
/*      */       else {
/* 1296 */         this.tokens.seek(address);
/* 1297 */         obj = readPRObject();
/*      */       }
/* 1299 */       return obj;
/*      */     }
/*      */     finally
/*      */     {
/* 1303 */       this.tokens = saveTokens;
/*      */     }
/*      */   }
/*      */ 
/*      */   public double dumpPerc()
/*      */   {
/* 1311 */     int total = 0;
/* 1312 */     for (int k = 0; k < this.xrefObj.size(); k++) {
/* 1313 */       if (this.xrefObj.get(k) != null)
/* 1314 */         total++;
/*      */     }
/* 1316 */     return total * 100.0D / this.xrefObj.size();
/*      */   }
/*      */ 
/*      */   protected void readDocObj() throws IOException {
/* 1320 */     ArrayList streams = new ArrayList();
/* 1321 */     this.xrefObj = new ArrayList(this.xref.length / 2);
/* 1322 */     this.xrefObj.addAll(Collections.nCopies(this.xref.length / 2, null));
/* 1323 */     for (int k = 2; k < this.xref.length; k += 2) {
/* 1324 */       long pos = this.xref[k];
/* 1325 */       if ((pos > 0L) && (this.xref[(k + 1)] <= 0L))
/*      */       {
/* 1327 */         this.tokens.seek(pos);
/* 1328 */         this.tokens.nextValidToken();
/* 1329 */         if (this.tokens.getTokenType() != PRTokeniser.TokenType.NUMBER)
/* 1330 */           this.tokens.throwError(MessageLocalization.getComposedMessage("invalid.object.number", new Object[0]));
/* 1331 */         this.objNum = this.tokens.intValue();
/* 1332 */         this.tokens.nextValidToken();
/* 1333 */         if (this.tokens.getTokenType() != PRTokeniser.TokenType.NUMBER)
/* 1334 */           this.tokens.throwError(MessageLocalization.getComposedMessage("invalid.generation.number", new Object[0]));
/* 1335 */         this.objGen = this.tokens.intValue();
/* 1336 */         this.tokens.nextValidToken();
/* 1337 */         if (!this.tokens.getStringValue().equals("obj"))
/* 1338 */           this.tokens.throwError(MessageLocalization.getComposedMessage("token.obj.expected", new Object[0]));
/*      */         try
/*      */         {
/* 1341 */           obj = readPRObject();
/* 1342 */           if (obj.isStream())
/* 1343 */             streams.add((PRStream)obj);
/*      */         }
/*      */         catch (IOException e)
/*      */         {
/*      */           PdfObject obj;
/* 1347 */           if (debugmode) {
/* 1348 */             if (LOGGER.isLogging(Level.ERROR))
/* 1349 */               LOGGER.error(e.getMessage(), e);
/* 1350 */             obj = null;
/*      */           }
/*      */           else {
/* 1353 */             throw e;
/*      */           }
/*      */         }
/*      */         PdfObject obj;
/* 1355 */         this.xrefObj.set(k / 2, obj);
/*      */       }
/*      */     }
/* 1357 */     for (int k = 0; k < streams.size(); k++) {
/* 1358 */       checkPRStreamLength((PRStream)streams.get(k));
/*      */     }
/* 1360 */     readDecryptedDocObj();
/* 1361 */     if (this.objStmMark != null) {
/* 1362 */       for (Map.Entry entry : this.objStmMark.entrySet()) {
/* 1363 */         int n = ((Integer)entry.getKey()).intValue();
/* 1364 */         IntHashtable h = (IntHashtable)entry.getValue();
/* 1365 */         readObjStm((PRStream)this.xrefObj.get(n), h);
/* 1366 */         this.xrefObj.set(n, null);
/*      */       }
/* 1368 */       this.objStmMark = null;
/*      */     }
/* 1370 */     this.xref = null;
/*      */   }
/*      */ 
/*      */   private void checkPRStreamLength(PRStream stream) throws IOException {
/* 1374 */     long fileLength = this.tokens.length();
/* 1375 */     long start = stream.getOffset();
/* 1376 */     boolean calc = false;
/* 1377 */     long streamLength = 0L;
/* 1378 */     PdfObject obj = getPdfObjectRelease(stream.get(PdfName.LENGTH));
/* 1379 */     if ((obj != null) && (obj.type() == 2)) {
/* 1380 */       streamLength = ((PdfNumber)obj).intValue();
/* 1381 */       if (streamLength + start > fileLength - 20L) {
/* 1382 */         calc = true;
/*      */       } else {
/* 1384 */         this.tokens.seek(start + streamLength);
/* 1385 */         String line = this.tokens.readString(20);
/* 1386 */         if ((!line.startsWith("\nendstream")) && (!line.startsWith("\r\nendstream")) && (!line.startsWith("\rendstream")) && (!line.startsWith("endstream")))
/*      */         {
/* 1390 */           calc = true;
/*      */         }
/*      */       }
/*      */     } else {
/* 1394 */       calc = true;
/* 1395 */     }if (calc) { byte[] tline = new byte[16];
/* 1397 */       this.tokens.seek(start);
/*      */       long pos;
/*      */       do { pos = this.tokens.getFilePointer();
/* 1401 */         if (!this.tokens.readLineSegment(tline, false))
/*      */           break;
/* 1403 */         if (equalsn(tline, endstream)) {
/* 1404 */           streamLength = pos - start;
/* 1405 */           break;
/*      */         } }
/* 1407 */       while (!equalsn(tline, endobj));
/* 1408 */       this.tokens.seek(pos - 16L);
/* 1409 */       String s = this.tokens.readString(16);
/* 1410 */       int index = s.indexOf("endstream");
/* 1411 */       if (index >= 0)
/* 1412 */         pos = pos - 16L + index;
/* 1413 */       streamLength = pos - start;
/*      */ 
/* 1417 */       this.tokens.seek(pos - 2L);
/* 1418 */       if (this.tokens.read() == 13)
/* 1419 */         streamLength -= 1L;
/* 1420 */       this.tokens.seek(pos - 1L);
/* 1421 */       if (this.tokens.read() == 10)
/* 1422 */         streamLength -= 1L;
/*      */     }
/* 1424 */     stream.setLength((int)streamLength);
/*      */   }
/*      */ 
/*      */   protected void readObjStm(PRStream stream, IntHashtable map) throws IOException {
/* 1428 */     if (stream == null) return;
/* 1429 */     int first = stream.getAsNumber(PdfName.FIRST).intValue();
/* 1430 */     int n = stream.getAsNumber(PdfName.N).intValue();
/* 1431 */     byte[] b = getStreamBytes(stream, this.tokens.getFile());
/* 1432 */     PRTokeniser saveTokens = this.tokens;
/* 1433 */     this.tokens = new PRTokeniser(new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(b)));
/*      */     try {
/* 1435 */       int[] address = new int[n];
/* 1436 */       int[] objNumber = new int[n];
/* 1437 */       boolean ok = true;
/* 1438 */       for (int k = 0; k < n; k++) {
/* 1439 */         ok = this.tokens.nextToken();
/* 1440 */         if (!ok)
/*      */           break;
/* 1442 */         if (this.tokens.getTokenType() != PRTokeniser.TokenType.NUMBER) {
/* 1443 */           ok = false;
/* 1444 */           break;
/*      */         }
/* 1446 */         objNumber[k] = this.tokens.intValue();
/* 1447 */         ok = this.tokens.nextToken();
/* 1448 */         if (!ok)
/*      */           break;
/* 1450 */         if (this.tokens.getTokenType() != PRTokeniser.TokenType.NUMBER) {
/* 1451 */           ok = false;
/* 1452 */           break;
/*      */         }
/* 1454 */         address[k] = (this.tokens.intValue() + first);
/*      */       }
/* 1456 */       if (!ok)
/* 1457 */         throw new InvalidPdfException(MessageLocalization.getComposedMessage("error.reading.objstm", new Object[0]));
/* 1458 */       for (int k = 0; k < n; k++)
/* 1459 */         if (map.containsKey(k)) {
/* 1460 */           this.tokens.seek(address[k]);
/* 1461 */           this.tokens.nextToken();
/*      */           PdfObject obj;
/*      */           PdfObject obj;
/* 1463 */           if (this.tokens.getTokenType() == PRTokeniser.TokenType.NUMBER) {
/* 1464 */             obj = new PdfNumber(this.tokens.getStringValue());
/*      */           }
/*      */           else {
/* 1467 */             this.tokens.seek(address[k]);
/* 1468 */             obj = readPRObject();
/*      */           }
/* 1470 */           this.xrefObj.set(objNumber[k], obj);
/*      */         }
/*      */     }
/*      */     finally
/*      */     {
/* 1475 */       this.tokens = saveTokens;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static PdfObject killIndirect(PdfObject obj)
/*      */   {
/* 1486 */     if ((obj == null) || (obj.isNull()))
/* 1487 */       return null;
/* 1488 */     PdfObject ret = getPdfObjectRelease(obj);
/* 1489 */     if (obj.isIndirect()) {
/* 1490 */       PRIndirectReference ref = (PRIndirectReference)obj;
/* 1491 */       PdfReader reader = ref.getReader();
/* 1492 */       int n = ref.getNumber();
/* 1493 */       reader.xrefObj.set(n, null);
/* 1494 */       if (reader.partial)
/* 1495 */         reader.xref[(n * 2)] = -1L;
/*      */     }
/* 1497 */     return ret;
/*      */   }
/*      */ 
/*      */   private void ensureXrefSize(int size) {
/* 1501 */     if (size == 0)
/* 1502 */       return;
/* 1503 */     if (this.xref == null) {
/* 1504 */       this.xref = new long[size];
/*      */     }
/* 1506 */     else if (this.xref.length < size) {
/* 1507 */       long[] xref2 = new long[size];
/* 1508 */       System.arraycopy(this.xref, 0, xref2, 0, this.xref.length);
/* 1509 */       this.xref = xref2;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void readXref() throws IOException
/*      */   {
/* 1515 */     this.hybridXref = false;
/* 1516 */     this.newXrefType = false;
/* 1517 */     this.tokens.seek(this.tokens.getStartxref());
/* 1518 */     this.tokens.nextToken();
/* 1519 */     if (!this.tokens.getStringValue().equals("startxref"))
/* 1520 */       throw new InvalidPdfException(MessageLocalization.getComposedMessage("startxref.not.found", new Object[0]));
/* 1521 */     this.tokens.nextToken();
/* 1522 */     if (this.tokens.getTokenType() != PRTokeniser.TokenType.NUMBER)
/* 1523 */       throw new InvalidPdfException(MessageLocalization.getComposedMessage("startxref.is.not.followed.by.a.number", new Object[0]));
/* 1524 */     long startxref = this.tokens.longValue();
/* 1525 */     this.lastXref = startxref;
/* 1526 */     this.eofPos = this.tokens.getFilePointer();
/*      */     try {
/* 1528 */       if (readXRefStream(startxref)) {
/* 1529 */         this.newXrefType = true;
/* 1530 */         return;
/*      */       }
/*      */     } catch (Exception e) {
/*      */     }
/* 1534 */     this.xref = null;
/* 1535 */     this.tokens.seek(startxref);
/* 1536 */     this.trailer = readXrefSection();
/* 1537 */     PdfDictionary trailer2 = this.trailer;
/*      */     while (true) {
/* 1539 */       PdfNumber prev = (PdfNumber)trailer2.get(PdfName.PREV);
/* 1540 */       if (prev == null)
/*      */         break;
/* 1542 */       this.tokens.seek(prev.longValue());
/* 1543 */       trailer2 = readXrefSection();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected PdfDictionary readXrefSection() throws IOException {
/* 1548 */     this.tokens.nextValidToken();
/* 1549 */     if (!this.tokens.getStringValue().equals("xref"))
/* 1550 */       this.tokens.throwError(MessageLocalization.getComposedMessage("xref.subsection.not.found", new Object[0]));
/* 1551 */     int start = 0;
/* 1552 */     int end = 0;
/* 1553 */     long pos = 0L;
/* 1554 */     int gen = 0;
/*      */     while (true) {
/* 1556 */       this.tokens.nextValidToken();
/* 1557 */       if (this.tokens.getStringValue().equals("trailer"))
/*      */         break;
/* 1559 */       if (this.tokens.getTokenType() != PRTokeniser.TokenType.NUMBER)
/* 1560 */         this.tokens.throwError(MessageLocalization.getComposedMessage("object.number.of.the.first.object.in.this.xref.subsection.not.found", new Object[0]));
/* 1561 */       start = this.tokens.intValue();
/* 1562 */       this.tokens.nextValidToken();
/* 1563 */       if (this.tokens.getTokenType() != PRTokeniser.TokenType.NUMBER)
/* 1564 */         this.tokens.throwError(MessageLocalization.getComposedMessage("number.of.entries.in.this.xref.subsection.not.found", new Object[0]));
/* 1565 */       end = this.tokens.intValue() + start;
/* 1566 */       if (start == 1) {
/* 1567 */         long back = this.tokens.getFilePointer();
/* 1568 */         this.tokens.nextValidToken();
/* 1569 */         pos = this.tokens.longValue();
/* 1570 */         this.tokens.nextValidToken();
/* 1571 */         gen = this.tokens.intValue();
/* 1572 */         if ((pos == 0L) && (gen == 65535)) {
/* 1573 */           start--;
/* 1574 */           end--;
/*      */         }
/* 1576 */         this.tokens.seek(back);
/*      */       }
/* 1578 */       ensureXrefSize(end * 2);
/* 1579 */       for (int k = start; k < end; k++) {
/* 1580 */         this.tokens.nextValidToken();
/* 1581 */         pos = this.tokens.longValue();
/* 1582 */         this.tokens.nextValidToken();
/* 1583 */         gen = this.tokens.intValue();
/* 1584 */         this.tokens.nextValidToken();
/* 1585 */         int p = k * 2;
/* 1586 */         if (this.tokens.getStringValue().equals("n")) {
/* 1587 */           if ((this.xref[p] == 0L) && (this.xref[(p + 1)] == 0L))
/*      */           {
/* 1590 */             this.xref[p] = pos;
/*      */           }
/*      */         }
/* 1593 */         else if (this.tokens.getStringValue().equals("f")) {
/* 1594 */           if ((this.xref[p] == 0L) && (this.xref[(p + 1)] == 0L))
/* 1595 */             this.xref[p] = -1L;
/*      */         }
/*      */         else
/* 1598 */           this.tokens.throwError(MessageLocalization.getComposedMessage("invalid.cross.reference.entry.in.this.xref.subsection", new Object[0]));
/*      */       }
/*      */     }
/* 1601 */     PdfDictionary trailer = (PdfDictionary)readPRObject();
/* 1602 */     PdfNumber xrefSize = (PdfNumber)trailer.get(PdfName.SIZE);
/* 1603 */     ensureXrefSize(xrefSize.intValue() * 2);
/* 1604 */     PdfObject xrs = trailer.get(PdfName.XREFSTM);
/* 1605 */     if ((xrs != null) && (xrs.isNumber())) {
/* 1606 */       int loc = ((PdfNumber)xrs).intValue();
/*      */       try {
/* 1608 */         readXRefStream(loc);
/* 1609 */         this.newXrefType = true;
/* 1610 */         this.hybridXref = true;
/*      */       }
/*      */       catch (IOException e) {
/* 1613 */         this.xref = null;
/* 1614 */         throw e;
/*      */       }
/*      */     }
/* 1617 */     return trailer;
/*      */   }
/*      */ 
/*      */   protected boolean readXRefStream(long ptr) throws IOException {
/* 1621 */     this.tokens.seek(ptr);
/* 1622 */     int thisStream = 0;
/* 1623 */     if (!this.tokens.nextToken())
/* 1624 */       return false;
/* 1625 */     if (this.tokens.getTokenType() != PRTokeniser.TokenType.NUMBER)
/* 1626 */       return false;
/* 1627 */     thisStream = this.tokens.intValue();
/* 1628 */     if ((!this.tokens.nextToken()) || (this.tokens.getTokenType() != PRTokeniser.TokenType.NUMBER))
/* 1629 */       return false;
/* 1630 */     if ((!this.tokens.nextToken()) || (!this.tokens.getStringValue().equals("obj")))
/* 1631 */       return false;
/* 1632 */     PdfObject object = readPRObject();
/* 1633 */     PRStream stm = null;
/* 1634 */     if (object.isStream()) {
/* 1635 */       stm = (PRStream)object;
/* 1636 */       if (!PdfName.XREF.equals(stm.get(PdfName.TYPE)))
/* 1637 */         return false;
/*      */     }
/*      */     else {
/* 1640 */       return false;
/* 1641 */     }if (this.trailer == null) {
/* 1642 */       this.trailer = new PdfDictionary();
/* 1643 */       this.trailer.putAll(stm);
/*      */     }
/* 1645 */     stm.setLength(((PdfNumber)stm.get(PdfName.LENGTH)).intValue());
/* 1646 */     int size = ((PdfNumber)stm.get(PdfName.SIZE)).intValue();
/*      */ 
/* 1648 */     PdfObject obj = stm.get(PdfName.INDEX);
/*      */     PdfArray index;
/* 1649 */     if (obj == null) {
/* 1650 */       PdfArray index = new PdfArray();
/* 1651 */       index.add(new int[] { 0, size });
/*      */     }
/*      */     else {
/* 1654 */       index = (PdfArray)obj;
/* 1655 */     }PdfArray w = (PdfArray)stm.get(PdfName.W);
/* 1656 */     long prev = -1L;
/* 1657 */     obj = stm.get(PdfName.PREV);
/* 1658 */     if (obj != null) {
/* 1659 */       prev = ((PdfNumber)obj).longValue();
/*      */     }
/*      */ 
/* 1664 */     ensureXrefSize(size * 2);
/* 1665 */     if ((this.objStmMark == null) && (!this.partial))
/* 1666 */       this.objStmMark = new HashMap();
/* 1667 */     if ((this.objStmToOffset == null) && (this.partial))
/* 1668 */       this.objStmToOffset = new LongHashtable();
/* 1669 */     byte[] b = getStreamBytes(stm, this.tokens.getFile());
/* 1670 */     int bptr = 0;
/* 1671 */     int[] wc = new int[3];
/* 1672 */     for (int k = 0; k < 3; k++)
/* 1673 */       wc[k] = w.getAsNumber(k).intValue();
/* 1674 */     for (int idx = 0; idx < index.size(); idx += 2) {
/* 1675 */       int start = index.getAsNumber(idx).intValue();
/* 1676 */       int length = index.getAsNumber(idx + 1).intValue();
/* 1677 */       ensureXrefSize((start + length) * 2);
/* 1678 */       while (length-- > 0) {
/* 1679 */         int type = 1;
/* 1680 */         if (wc[0] > 0) {
/* 1681 */           type = 0;
/* 1682 */           for (int k = 0; k < wc[0]; k++)
/* 1683 */             type = (type << 8) + (b[(bptr++)] & 0xFF);
/*      */         }
/* 1685 */         long field2 = 0L;
/* 1686 */         for (int k = 0; k < wc[1]; k++)
/* 1687 */           field2 = (field2 << 8) + (b[(bptr++)] & 0xFF);
/* 1688 */         int field3 = 0;
/* 1689 */         for (int k = 0; k < wc[2]; k++)
/* 1690 */           field3 = (field3 << 8) + (b[(bptr++)] & 0xFF);
/* 1691 */         int base = start * 2;
/* 1692 */         if ((this.xref[base] == 0L) && (this.xref[(base + 1)] == 0L))
/* 1693 */           switch (type) {
/*      */           case 0:
/* 1695 */             this.xref[base] = -1L;
/* 1696 */             break;
/*      */           case 1:
/* 1698 */             this.xref[base] = field2;
/* 1699 */             break;
/*      */           case 2:
/* 1701 */             this.xref[base] = field3;
/* 1702 */             this.xref[(base + 1)] = field2;
/* 1703 */             if (this.partial) {
/* 1704 */               this.objStmToOffset.put(field2, 0L);
/*      */             }
/*      */             else {
/* 1707 */               Integer on = Integer.valueOf((int)field2);
/* 1708 */               IntHashtable seq = (IntHashtable)this.objStmMark.get(on);
/* 1709 */               if (seq == null) {
/* 1710 */                 seq = new IntHashtable();
/* 1711 */                 seq.put(field3, 1);
/* 1712 */                 this.objStmMark.put(on, seq);
/*      */               }
/*      */               else {
/* 1715 */                 seq.put(field3, 1);
/*      */               }
/*      */             }
/*      */             break;
/*      */           }
/* 1720 */         start++;
/*      */       }
/*      */     }
/* 1723 */     thisStream *= 2;
/* 1724 */     if ((thisStream + 1 < this.xref.length) && (this.xref[thisStream] == 0L) && (this.xref[(thisStream + 1)] == 0L)) {
/* 1725 */       this.xref[thisStream] = -1L;
/*      */     }
/* 1727 */     if (prev == -1L)
/* 1728 */       return true;
/* 1729 */     return readXRefStream(prev);
/*      */   }
/*      */ 
/*      */   protected void rebuildXref() throws IOException {
/* 1733 */     this.hybridXref = false;
/* 1734 */     this.newXrefType = false;
/* 1735 */     this.tokens.seek(0L);
/* 1736 */     long[][] xr = new long[1024][];
/* 1737 */     long top = 0L;
/* 1738 */     this.trailer = null;
/* 1739 */     byte[] line = new byte[64];
/*      */     while (true) {
/* 1741 */       long pos = this.tokens.getFilePointer();
/* 1742 */       if (!this.tokens.readLineSegment(line, true))
/*      */         break;
/* 1744 */       if (line[0] == 116) {
/* 1745 */         if (PdfEncodings.convertToString(line, null).startsWith("trailer"))
/*      */         {
/* 1747 */           this.tokens.seek(pos);
/* 1748 */           this.tokens.nextToken();
/* 1749 */           pos = this.tokens.getFilePointer();
/*      */           try {
/* 1751 */             PdfDictionary dic = (PdfDictionary)readPRObject();
/* 1752 */             if (dic.get(PdfName.ROOT) != null)
/* 1753 */               this.trailer = dic;
/*      */             else
/* 1755 */               this.tokens.seek(pos);
/*      */           }
/*      */           catch (Exception e) {
/* 1758 */             this.tokens.seek(pos);
/*      */           }
/*      */         }
/* 1761 */       } else if ((line[0] >= 48) && (line[0] <= 57)) {
/* 1762 */         long[] obj = PRTokeniser.checkObjectStart(line);
/* 1763 */         if (obj != null)
/*      */         {
/* 1765 */           long num = obj[0];
/* 1766 */           long gen = obj[1];
/* 1767 */           if (num >= xr.length) {
/* 1768 */             long newLength = num * 2L;
/* 1769 */             long[][] xr2 = new long[(int)newLength][];
/* 1770 */             System.arraycopy(xr, 0, xr2, 0, (int)top);
/* 1771 */             xr = xr2;
/*      */           }
/* 1773 */           if (num >= top)
/* 1774 */             top = num + 1L;
/* 1775 */           if ((xr[((int)num)] == null) || (gen >= xr[((int)num)][1])) {
/* 1776 */             obj[0] = pos;
/* 1777 */             xr[((int)num)] = obj;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1781 */     if (this.trailer == null)
/* 1782 */       throw new InvalidPdfException(MessageLocalization.getComposedMessage("trailer.not.found", new Object[0]));
/* 1783 */     this.xref = new long[(int)(top * 2L)];
/* 1784 */     for (int k = 0; k < top; k++) {
/* 1785 */       long[] obj = xr[k];
/* 1786 */       if (obj != null)
/* 1787 */         this.xref[(k * 2)] = obj[0];
/*      */     }
/*      */   }
/*      */ 
/*      */   protected PdfDictionary readDictionary() throws IOException {
/* 1792 */     PdfDictionary dic = new PdfDictionary();
/*      */     while (true) {
/* 1794 */       this.tokens.nextValidToken();
/* 1795 */       if (this.tokens.getTokenType() == PRTokeniser.TokenType.END_DIC)
/*      */         break;
/* 1797 */       if (this.tokens.getTokenType() != PRTokeniser.TokenType.NAME)
/* 1798 */         this.tokens.throwError(MessageLocalization.getComposedMessage("dictionary.key.1.is.not.a.name", new Object[] { this.tokens.getStringValue() }));
/* 1799 */       PdfName name = new PdfName(this.tokens.getStringValue(), false);
/* 1800 */       PdfObject obj = readPRObject();
/* 1801 */       int type = obj.type();
/* 1802 */       if (-type == PRTokeniser.TokenType.END_DIC.ordinal())
/* 1803 */         this.tokens.throwError(MessageLocalization.getComposedMessage("unexpected.gt.gt", new Object[0]));
/* 1804 */       if (-type == PRTokeniser.TokenType.END_ARRAY.ordinal())
/* 1805 */         this.tokens.throwError(MessageLocalization.getComposedMessage("unexpected.close.bracket", new Object[0]));
/* 1806 */       dic.put(name, obj);
/*      */     }
/* 1808 */     return dic;
/*      */   }
/*      */ 
/*      */   protected PdfArray readArray() throws IOException {
/* 1812 */     PdfArray array = new PdfArray();
/*      */     while (true) {
/* 1814 */       PdfObject obj = readPRObject();
/* 1815 */       int type = obj.type();
/* 1816 */       if (-type == PRTokeniser.TokenType.END_ARRAY.ordinal())
/*      */         break;
/* 1818 */       if (-type == PRTokeniser.TokenType.END_DIC.ordinal())
/* 1819 */         this.tokens.throwError(MessageLocalization.getComposedMessage("unexpected.gt.gt", new Object[0]));
/* 1820 */       array.add(obj);
/*      */     }
/* 1822 */     return array;
/*      */   }
/*      */ 
/*      */   protected PdfObject readPRObject()
/*      */     throws IOException
/*      */   {
/* 1831 */     this.tokens.nextValidToken();
/* 1832 */     PRTokeniser.TokenType type = this.tokens.getTokenType();
/* 1833 */     switch (1.$SwitchMap$com$itextpdf$text$pdf$PRTokeniser$TokenType[type.ordinal()]) { case 1:
/* 1835 */       this.readDepth += 1;
/* 1836 */       PdfDictionary dic = readDictionary();
/* 1837 */       this.readDepth -= 1;
/* 1838 */       long pos = this.tokens.getFilePointer();
/*      */       boolean hasNext;
/*      */       do hasNext = this.tokens.nextToken();
/* 1843 */       while ((hasNext) && (this.tokens.getTokenType() == PRTokeniser.TokenType.COMMENT));
/*      */ 
/* 1845 */       if ((hasNext) && (this.tokens.getStringValue().equals("stream")))
/*      */       {
/*      */         int ch;
/*      */         do
/* 1849 */           ch = this.tokens.read();
/* 1850 */         while ((ch == 32) || (ch == 9) || (ch == 0) || (ch == 12));
/* 1851 */         if (ch != 10)
/* 1852 */           ch = this.tokens.read();
/* 1853 */         if (ch != 10)
/* 1854 */           this.tokens.backOnePosition(ch);
/* 1855 */         PRStream stream = new PRStream(this, this.tokens.getFilePointer());
/* 1856 */         stream.putAll(dic);
/*      */ 
/* 1858 */         stream.setObjNum(this.objNum, this.objGen);
/*      */ 
/* 1860 */         return stream;
/*      */       }
/*      */ 
/* 1863 */       this.tokens.seek(pos);
/* 1864 */       return dic;
/*      */     case 2:
/* 1868 */       this.readDepth += 1;
/* 1869 */       PdfArray arr = readArray();
/* 1870 */       this.readDepth -= 1;
/* 1871 */       return arr;
/*      */     case 3:
/* 1874 */       return new PdfNumber(this.tokens.getStringValue());
/*      */     case 4:
/* 1876 */       PdfString str = new PdfString(this.tokens.getStringValue(), null).setHexWriting(this.tokens.isHexString());
/*      */ 
/* 1878 */       str.setObjNum(this.objNum, this.objGen);
/* 1879 */       if (this.strings != null) {
/* 1880 */         this.strings.add(str);
/*      */       }
/* 1882 */       return str;
/*      */     case 5:
/* 1884 */       PdfName cachedName = (PdfName)PdfName.staticNames.get(this.tokens.getStringValue());
/* 1885 */       if ((this.readDepth > 0) && (cachedName != null)) {
/* 1886 */         return cachedName;
/*      */       }
/*      */ 
/* 1889 */       return new PdfName(this.tokens.getStringValue(), false);
/*      */     case 6:
/* 1893 */       int num = this.tokens.getReference();
/* 1894 */       PRIndirectReference ref = new PRIndirectReference(this, num, this.tokens.getGeneration());
/* 1895 */       return ref;
/*      */     case 7:
/* 1897 */       throw new IOException(MessageLocalization.getComposedMessage("unexpected.end.of.file", new Object[0]));
/*      */     }
/* 1899 */     String sv = this.tokens.getStringValue();
/* 1900 */     if ("null".equals(sv)) {
/* 1901 */       if (this.readDepth == 0) {
/* 1902 */         return new PdfNull();
/*      */       }
/* 1904 */       return PdfNull.PDFNULL;
/*      */     }
/* 1906 */     if ("true".equals(sv)) {
/* 1907 */       if (this.readDepth == 0) {
/* 1908 */         return new PdfBoolean(true);
/*      */       }
/* 1910 */       return PdfBoolean.PDFTRUE;
/*      */     }
/* 1912 */     if ("false".equals(sv)) {
/* 1913 */       if (this.readDepth == 0) {
/* 1914 */         return new PdfBoolean(false);
/*      */       }
/* 1916 */       return PdfBoolean.PDFFALSE;
/*      */     }
/* 1918 */     return new PdfLiteral(-type.ordinal(), this.tokens.getStringValue());
/*      */   }
/*      */ 
/*      */   public static byte[] FlateDecode(byte[] in)
/*      */   {
/* 1927 */     byte[] b = FlateDecode(in, true);
/* 1928 */     if (b == null)
/* 1929 */       return FlateDecode(in, false);
/* 1930 */     return b;
/*      */   }
/*      */ 
/*      */   public static byte[] decodePredictor(byte[] in, PdfObject dicPar)
/*      */   {
/* 1939 */     if ((dicPar == null) || (!dicPar.isDictionary()))
/* 1940 */       return in;
/* 1941 */     PdfDictionary dic = (PdfDictionary)dicPar;
/* 1942 */     PdfObject obj = getPdfObject(dic.get(PdfName.PREDICTOR));
/* 1943 */     if ((obj == null) || (!obj.isNumber()))
/* 1944 */       return in;
/* 1945 */     int predictor = ((PdfNumber)obj).intValue();
/* 1946 */     if ((predictor < 10) && (predictor != 2))
/* 1947 */       return in;
/* 1948 */     int width = 1;
/* 1949 */     obj = getPdfObject(dic.get(PdfName.COLUMNS));
/* 1950 */     if ((obj != null) && (obj.isNumber()))
/* 1951 */       width = ((PdfNumber)obj).intValue();
/* 1952 */     int colors = 1;
/* 1953 */     obj = getPdfObject(dic.get(PdfName.COLORS));
/* 1954 */     if ((obj != null) && (obj.isNumber()))
/* 1955 */       colors = ((PdfNumber)obj).intValue();
/* 1956 */     int bpc = 8;
/* 1957 */     obj = getPdfObject(dic.get(PdfName.BITSPERCOMPONENT));
/* 1958 */     if ((obj != null) && (obj.isNumber()))
/* 1959 */       bpc = ((PdfNumber)obj).intValue();
/* 1960 */     DataInputStream dataStream = new DataInputStream(new ByteArrayInputStream(in));
/* 1961 */     ByteArrayOutputStream fout = new ByteArrayOutputStream(in.length);
/* 1962 */     int bytesPerPixel = colors * bpc / 8;
/* 1963 */     int bytesPerRow = (colors * width * bpc + 7) / 8;
/* 1964 */     byte[] curr = new byte[bytesPerRow];
/* 1965 */     byte[] prior = new byte[bytesPerRow];
/* 1966 */     if (predictor == 2) {
/* 1967 */       if (bpc == 8) {
/* 1968 */         int numRows = in.length / bytesPerRow;
/* 1969 */         for (int row = 0; row < numRows; row++) {
/* 1970 */           int rowStart = row * bytesPerRow;
/* 1971 */           for (int col = 0 + bytesPerPixel; col < bytesPerRow; col++) {
/* 1972 */             in[(rowStart + col)] = ((byte)(in[(rowStart + col)] + in[(rowStart + col - bytesPerPixel)]));
/*      */           }
/*      */         }
/*      */       }
/* 1976 */       return in;
/*      */     }
/*      */ 
/*      */     while (true)
/*      */     {
/* 1981 */       int filter = 0;
/*      */       try {
/* 1983 */         filter = dataStream.read();
/* 1984 */         if (filter < 0) {
/* 1985 */           return fout.toByteArray();
/*      */         }
/* 1987 */         dataStream.readFully(curr, 0, bytesPerRow);
/*      */       } catch (Exception e) {
/* 1989 */         return fout.toByteArray();
/*      */       }
/*      */ 
/* 1992 */       switch (filter) {
/*      */       case 0:
/* 1994 */         break;
/*      */       case 1:
/* 1996 */         for (int i = bytesPerPixel; i < bytesPerRow; i++)
/*      */         {
/*      */           int tmp422_420 = i;
/*      */           byte[] tmp422_418 = curr; tmp422_418[tmp422_420] = ((byte)(tmp422_418[tmp422_420] + curr[(i - bytesPerPixel)]));
/*      */         }
/* 1999 */         break;
/*      */       case 2:
/* 2001 */         for (int i = 0; i < bytesPerRow; i++)
/*      */         {
/*      */           int tmp458_456 = i;
/*      */           byte[] tmp458_454 = curr; tmp458_454[tmp458_456] = ((byte)(tmp458_454[tmp458_456] + prior[i]));
/*      */         }
/* 2004 */         break;
/*      */       case 3:
/* 2006 */         for (int i = 0; i < bytesPerPixel; i++)
/*      */         {
/*      */           int tmp491_489 = i;
/*      */           byte[] tmp491_487 = curr; tmp491_487[tmp491_489] = ((byte)(tmp491_487[tmp491_489] + prior[i] / 2));
/*      */         }
/* 2009 */         for (int i = bytesPerPixel; i < bytesPerRow; i++)
/*      */         {
/*      */           int tmp524_522 = i;
/*      */           byte[] tmp524_520 = curr; tmp524_520[tmp524_522] = ((byte)(tmp524_520[tmp524_522] + ((curr[(i - bytesPerPixel)] & 0xFF) + (prior[i] & 0xFF)) / 2));
/*      */         }
/* 2012 */         break;
/*      */       case 4:
/* 2014 */         for (int i = 0; i < bytesPerPixel; i++)
/*      */         {
/*      */           int tmp576_574 = i;
/*      */           byte[] tmp576_572 = curr; tmp576_572[tmp576_574] = ((byte)(tmp576_572[tmp576_574] + prior[i]));
/*      */         }
/*      */ 
/* 2018 */         for (int i = bytesPerPixel; i < bytesPerRow; i++) {
/* 2019 */           int a = curr[(i - bytesPerPixel)] & 0xFF;
/* 2020 */           int b = prior[i] & 0xFF;
/* 2021 */           int c = prior[(i - bytesPerPixel)] & 0xFF;
/*      */ 
/* 2023 */           int p = a + b - c;
/* 2024 */           int pa = Math.abs(p - a);
/* 2025 */           int pb = Math.abs(p - b);
/* 2026 */           int pc = Math.abs(p - c);
/*      */           int ret;
/*      */           int ret;
/* 2030 */           if ((pa <= pb) && (pa <= pc)) {
/* 2031 */             ret = a;
/*      */           }
/*      */           else
/*      */           {
/*      */             int ret;
/* 2032 */             if (pb <= pc)
/* 2033 */               ret = b;
/*      */             else
/* 2035 */               ret = c;
/*      */           }
/*      */           int tmp725_723 = i;
/*      */           byte[] tmp725_721 = curr; tmp725_721[tmp725_723] = ((byte)(tmp725_721[tmp725_723] + (byte)ret));
/*      */         }
/* 2039 */         break;
/*      */       default:
/* 2042 */         throw new RuntimeException(MessageLocalization.getComposedMessage("png.filter.unknown", new Object[0]));
/*      */       }
/*      */       try {
/* 2045 */         fout.write(curr);
/*      */       }
/*      */       catch (IOException ioe)
/*      */       {
/*      */       }
/*      */ 
/* 2052 */       byte[] tmp = prior;
/* 2053 */       prior = curr;
/* 2054 */       curr = tmp;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static byte[] FlateDecode(byte[] in, boolean strict)
/*      */   {
/* 2065 */     ByteArrayInputStream stream = new ByteArrayInputStream(in);
/* 2066 */     InflaterInputStream zip = new InflaterInputStream(stream);
/* 2067 */     ByteArrayOutputStream out = new ByteArrayOutputStream();
/* 2068 */     byte[] b = new byte[strict ? 4092 : 1];
/*      */     try
/*      */     {
/*      */       int n;
/* 2071 */       while ((n = zip.read(b)) >= 0) {
/* 2072 */         out.write(b, 0, n);
/*      */       }
/* 2074 */       zip.close();
/* 2075 */       out.close();
/* 2076 */       return out.toByteArray();
/*      */     }
/*      */     catch (Exception e) {
/* 2079 */       if (strict)
/* 2080 */         return null; 
/*      */     }
/* 2081 */     return out.toByteArray();
/*      */   }
/*      */ 
/*      */   public static byte[] ASCIIHexDecode(byte[] in)
/*      */   {
/* 2090 */     ByteArrayOutputStream out = new ByteArrayOutputStream();
/* 2091 */     boolean first = true;
/* 2092 */     int n1 = 0;
/* 2093 */     for (int k = 0; k < in.length; k++) {
/* 2094 */       int ch = in[k] & 0xFF;
/* 2095 */       if (ch == 62)
/*      */         break;
/* 2097 */       if (!PRTokeniser.isWhitespace(ch))
/*      */       {
/* 2099 */         int n = PRTokeniser.getHex(ch);
/* 2100 */         if (n == -1)
/* 2101 */           throw new RuntimeException(MessageLocalization.getComposedMessage("illegal.character.in.asciihexdecode", new Object[0]));
/* 2102 */         if (first)
/* 2103 */           n1 = n;
/*      */         else
/* 2105 */           out.write((byte)((n1 << 4) + n));
/* 2106 */         first = !first;
/*      */       }
/*      */     }
/* 2108 */     if (!first)
/* 2109 */       out.write((byte)(n1 << 4));
/* 2110 */     return out.toByteArray();
/*      */   }
/*      */ 
/*      */   public static byte[] ASCII85Decode(byte[] in)
/*      */   {
/* 2118 */     ByteArrayOutputStream out = new ByteArrayOutputStream();
/* 2119 */     int state = 0;
/* 2120 */     int[] chn = new int[5];
/* 2121 */     for (int k = 0; k < in.length; k++) {
/* 2122 */       int ch = in[k] & 0xFF;
/* 2123 */       if (ch == 126)
/*      */         break;
/* 2125 */       if (!PRTokeniser.isWhitespace(ch))
/*      */       {
/* 2127 */         if ((ch == 122) && (state == 0)) {
/* 2128 */           out.write(0);
/* 2129 */           out.write(0);
/* 2130 */           out.write(0);
/* 2131 */           out.write(0);
/*      */         }
/*      */         else {
/* 2134 */           if ((ch < 33) || (ch > 117))
/* 2135 */             throw new RuntimeException(MessageLocalization.getComposedMessage("illegal.character.in.ascii85decode", new Object[0]));
/* 2136 */           chn[state] = (ch - 33);
/* 2137 */           state++;
/* 2138 */           if (state == 5) {
/* 2139 */             state = 0;
/* 2140 */             int r = 0;
/* 2141 */             for (int j = 0; j < 5; j++)
/* 2142 */               r = r * 85 + chn[j];
/* 2143 */             out.write((byte)(r >> 24));
/* 2144 */             out.write((byte)(r >> 16));
/* 2145 */             out.write((byte)(r >> 8));
/* 2146 */             out.write((byte)r);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 2149 */     int r = 0;
/*      */ 
/* 2153 */     if (state == 2) {
/* 2154 */       r = chn[0] * 85 * 85 * 85 * 85 + chn[1] * 85 * 85 * 85 + 614125 + 7225 + 85;
/* 2155 */       out.write((byte)(r >> 24));
/*      */     }
/* 2157 */     else if (state == 3) {
/* 2158 */       r = chn[0] * 85 * 85 * 85 * 85 + chn[1] * 85 * 85 * 85 + chn[2] * 85 * 85 + 7225 + 85;
/* 2159 */       out.write((byte)(r >> 24));
/* 2160 */       out.write((byte)(r >> 16));
/*      */     }
/* 2162 */     else if (state == 4) {
/* 2163 */       r = chn[0] * 85 * 85 * 85 * 85 + chn[1] * 85 * 85 * 85 + chn[2] * 85 * 85 + chn[3] * 85 + 85;
/* 2164 */       out.write((byte)(r >> 24));
/* 2165 */       out.write((byte)(r >> 16));
/* 2166 */       out.write((byte)(r >> 8));
/*      */     }
/* 2168 */     return out.toByteArray();
/*      */   }
/*      */ 
/*      */   public static byte[] LZWDecode(byte[] in)
/*      */   {
/* 2176 */     ByteArrayOutputStream out = new ByteArrayOutputStream();
/* 2177 */     LZWDecoder lzw = new LZWDecoder();
/* 2178 */     lzw.decode(in, out);
/* 2179 */     return out.toByteArray();
/*      */   }
/*      */ 
/*      */   public boolean isRebuilt()
/*      */   {
/* 2187 */     return this.rebuilt;
/*      */   }
/*      */ 
/*      */   public PdfDictionary getPageN(int pageNum)
/*      */   {
/* 2195 */     PdfDictionary dic = this.pageRefs.getPageN(pageNum);
/* 2196 */     if (dic == null)
/* 2197 */       return null;
/* 2198 */     if (this.appendable)
/* 2199 */       dic.setIndRef(this.pageRefs.getPageOrigRef(pageNum));
/* 2200 */     return dic;
/*      */   }
/*      */ 
/*      */   public PdfDictionary getPageNRelease(int pageNum)
/*      */   {
/* 2208 */     PdfDictionary dic = getPageN(pageNum);
/* 2209 */     this.pageRefs.releasePage(pageNum);
/* 2210 */     return dic;
/*      */   }
/*      */ 
/*      */   public void releasePage(int pageNum)
/*      */   {
/* 2217 */     this.pageRefs.releasePage(pageNum);
/*      */   }
/*      */ 
/*      */   public void resetReleasePage()
/*      */   {
/* 2224 */     this.pageRefs.resetReleasePage();
/*      */   }
/*      */ 
/*      */   public PRIndirectReference getPageOrigRef(int pageNum)
/*      */   {
/* 2232 */     return this.pageRefs.getPageOrigRef(pageNum);
/*      */   }
/*      */ 
/*      */   public byte[] getPageContent(int pageNum, RandomAccessFileOrArray file)
/*      */     throws IOException
/*      */   {
/* 2242 */     PdfDictionary page = getPageNRelease(pageNum);
/* 2243 */     if (page == null)
/* 2244 */       return null;
/* 2245 */     PdfObject contents = getPdfObjectRelease(page.get(PdfName.CONTENTS));
/* 2246 */     if (contents == null)
/* 2247 */       return new byte[0];
/* 2248 */     ByteArrayOutputStream bout = null;
/* 2249 */     if (contents.isStream()) {
/* 2250 */       return getStreamBytes((PRStream)contents, file);
/*      */     }
/* 2252 */     if (contents.isArray()) {
/* 2253 */       PdfArray array = (PdfArray)contents;
/* 2254 */       bout = new ByteArrayOutputStream();
/* 2255 */       for (int k = 0; k < array.size(); k++) {
/* 2256 */         PdfObject item = getPdfObjectRelease(array.getPdfObject(k));
/* 2257 */         if ((item != null) && (item.isStream()))
/*      */         {
/* 2259 */           byte[] b = getStreamBytes((PRStream)item, file);
/* 2260 */           bout.write(b);
/* 2261 */           if (k != array.size() - 1)
/* 2262 */             bout.write(10); 
/*      */         }
/*      */       }
/* 2264 */       return bout.toByteArray();
/*      */     }
/*      */ 
/* 2267 */     return new byte[0];
/*      */   }
/*      */ 
/*      */   public static byte[] getPageContent(PdfDictionary page)
/*      */     throws IOException
/*      */   {
/* 2277 */     if (page == null)
/* 2278 */       return null;
/* 2279 */     RandomAccessFileOrArray rf = null;
/*      */     try {
/* 2281 */       PdfObject contents = getPdfObjectRelease(page.get(PdfName.CONTENTS));
/*      */       byte[] arrayOfByte1;
/* 2282 */       if (contents == null)
/* 2283 */         return new byte[0];
/* 2284 */       if (contents.isStream()) {
/* 2285 */         if (rf == null) {
/* 2286 */           rf = ((PRStream)contents).getReader().getSafeFile();
/* 2287 */           rf.reOpen();
/*      */         }
/* 2289 */         return getStreamBytes((PRStream)contents, rf);
/*      */       }
/*      */       Object array;
/* 2291 */       if (contents.isArray()) {
/* 2292 */         array = (PdfArray)contents;
/* 2293 */         ByteArrayOutputStream bout = new ByteArrayOutputStream();
/* 2294 */         for (int k = 0; k < ((PdfArray)array).size(); k++) {
/* 2295 */           PdfObject item = getPdfObjectRelease(((PdfArray)array).getPdfObject(k));
/* 2296 */           if ((item != null) && (item.isStream()))
/*      */           {
/* 2298 */             if (rf == null) {
/* 2299 */               rf = ((PRStream)item).getReader().getSafeFile();
/* 2300 */               rf.reOpen();
/*      */             }
/* 2302 */             byte[] b = getStreamBytes((PRStream)item, rf);
/* 2303 */             bout.write(b);
/* 2304 */             if (k != ((PdfArray)array).size() - 1)
/* 2305 */               bout.write(10); 
/*      */           }
/*      */         }
/* 2307 */         return bout.toByteArray();
/*      */       }
/*      */ 
/* 2310 */       return new byte[0];
/*      */     }
/*      */     finally {
/*      */       try {
/* 2314 */         if (rf != null)
/* 2315 */           rf.close();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public PdfDictionary getPageResources(int pageNum)
/*      */   {
/* 2327 */     return getPageResources(getPageN(pageNum));
/*      */   }
/*      */ 
/*      */   public PdfDictionary getPageResources(PdfDictionary pageDict)
/*      */   {
/* 2337 */     return pageDict.getAsDict(PdfName.RESOURCES);
/*      */   }
/*      */ 
/*      */   public byte[] getPageContent(int pageNum)
/*      */     throws IOException
/*      */   {
/* 2346 */     RandomAccessFileOrArray rf = getSafeFile();
/*      */     try {
/* 2348 */       rf.reOpen();
/* 2349 */       return getPageContent(pageNum, rf);
/*      */     } finally {
/*      */       try {
/* 2352 */         rf.close(); } catch (Exception e) {
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/* 2357 */   protected void killXref(PdfObject obj) { if (obj == null)
/* 2358 */       return;
/* 2359 */     if (((obj instanceof PdfIndirectReference)) && (!obj.isIndirect()))
/*      */       return;
/*      */     PdfDictionary dic;
/* 2361 */     switch (obj.type()) {
/*      */     case 10:
/* 2363 */       int xr = ((PRIndirectReference)obj).getNumber();
/* 2364 */       obj = (PdfObject)this.xrefObj.get(xr);
/* 2365 */       this.xrefObj.set(xr, null);
/* 2366 */       this.freeXref = xr;
/* 2367 */       killXref(obj);
/* 2368 */       break;
/*      */     case 5:
/* 2371 */       PdfArray t = (PdfArray)obj;
/* 2372 */       for (int i = 0; i < t.size(); i++)
/* 2373 */         killXref(t.getPdfObject(i));
/* 2374 */       break;
/*      */     case 6:
/*      */     case 7:
/* 2378 */       dic = (PdfDictionary)obj;
/* 2379 */       for (Object element : dic.getKeys()) {
/* 2380 */         killXref(dic.get((PdfName)element));
/*      */       }
/* 2382 */       break;
/*      */     case 8:
/*      */     case 9:
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setPageContent(int pageNum, byte[] content)
/*      */   {
/* 2392 */     setPageContent(pageNum, content, -1);
/*      */   }
/*      */ 
/*      */   public void setPageContent(int pageNum, byte[] content, int compressionLevel)
/*      */   {
/* 2401 */     PdfDictionary page = getPageN(pageNum);
/* 2402 */     if (page == null)
/* 2403 */       return;
/* 2404 */     PdfObject contents = page.get(PdfName.CONTENTS);
/* 2405 */     this.freeXref = -1;
/* 2406 */     killXref(contents);
/* 2407 */     if (this.freeXref == -1) {
/* 2408 */       this.xrefObj.add(null);
/* 2409 */       this.freeXref = (this.xrefObj.size() - 1);
/*      */     }
/* 2411 */     page.put(PdfName.CONTENTS, new PRIndirectReference(this, this.freeXref));
/* 2412 */     this.xrefObj.set(this.freeXref, new PRStream(this, content, compressionLevel));
/*      */   }
/*      */ 
/*      */   public static byte[] decodeBytes(byte[] b, PdfDictionary streamDictionary)
/*      */     throws IOException
/*      */   {
/* 2425 */     return decodeBytes(b, streamDictionary, FilterHandlers.getDefaultFilterHandlers());
/*      */   }
/*      */ 
/*      */   public static byte[] decodeBytes(byte[] b, PdfDictionary streamDictionary, Map<PdfName, FilterHandlers.FilterHandler> filterHandlers)
/*      */     throws IOException
/*      */   {
/* 2438 */     PdfObject filter = getPdfObjectRelease(streamDictionary.get(PdfName.FILTER));
/*      */ 
/* 2440 */     ArrayList filters = new ArrayList();
/* 2441 */     if (filter != null) {
/* 2442 */       if (filter.isName())
/* 2443 */         filters.add(filter);
/* 2444 */       else if (filter.isArray())
/* 2445 */         filters = ((PdfArray)filter).getArrayList();
/*      */     }
/* 2447 */     ArrayList dp = new ArrayList();
/* 2448 */     PdfObject dpo = getPdfObjectRelease(streamDictionary.get(PdfName.DECODEPARMS));
/* 2449 */     if ((dpo == null) || ((!dpo.isDictionary()) && (!dpo.isArray())))
/* 2450 */       dpo = getPdfObjectRelease(streamDictionary.get(PdfName.DP));
/* 2451 */     if (dpo != null) {
/* 2452 */       if (dpo.isDictionary())
/* 2453 */         dp.add(dpo);
/* 2454 */       else if (dpo.isArray())
/* 2455 */         dp = ((PdfArray)dpo).getArrayList();
/*      */     }
/* 2457 */     for (int j = 0; j < filters.size(); j++) {
/* 2458 */       PdfName filterName = (PdfName)filters.get(j);
/* 2459 */       FilterHandlers.FilterHandler filterHandler = (FilterHandlers.FilterHandler)filterHandlers.get(filterName);
/* 2460 */       if (filterHandler == null)
/* 2461 */         throw new UnsupportedPdfException(MessageLocalization.getComposedMessage("the.filter.1.is.not.supported", new Object[] { filterName }));
/*      */       PdfDictionary decodeParams;
/*      */       PdfDictionary decodeParams;
/* 2464 */       if (j < dp.size()) {
/* 2465 */         PdfObject dpEntry = getPdfObject((PdfObject)dp.get(j));
/*      */         PdfDictionary decodeParams;
/* 2466 */         if ((dpEntry instanceof PdfDictionary)) {
/* 2467 */           decodeParams = (PdfDictionary)dpEntry;
/*      */         }
/*      */         else
/*      */         {
/*      */           PdfDictionary decodeParams;
/* 2468 */           if ((dpEntry == null) || ((dpEntry instanceof PdfNull)))
/* 2469 */             decodeParams = null;
/*      */           else
/* 2471 */             throw new UnsupportedPdfException(MessageLocalization.getComposedMessage("the.decode.parameter.type.1.is.not.supported", new Object[] { dpEntry.getClass().toString() }));
/*      */         }
/*      */       }
/*      */       else {
/* 2475 */         decodeParams = null;
/*      */       }
/* 2477 */       b = filterHandler.decode(b, filterName, decodeParams, streamDictionary);
/*      */     }
/* 2479 */     return b;
/*      */   }
/*      */ 
/*      */   public static byte[] getStreamBytes(PRStream stream, RandomAccessFileOrArray file)
/*      */     throws IOException
/*      */   {
/* 2489 */     byte[] b = getStreamBytesRaw(stream, file);
/* 2490 */     return decodeBytes(b, stream);
/*      */   }
/*      */ 
/*      */   public static byte[] getStreamBytes(PRStream stream)
/*      */     throws IOException
/*      */   {
/* 2499 */     RandomAccessFileOrArray rf = stream.getReader().getSafeFile();
/*      */     try {
/* 2501 */       rf.reOpen();
/* 2502 */       return getStreamBytes(stream, rf);
/*      */     } finally {
/*      */       try {
/* 2505 */         rf.close();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static byte[] getStreamBytesRaw(PRStream stream, RandomAccessFileOrArray file)
/*      */     throws IOException
/*      */   {
/* 2516 */     PdfReader reader = stream.getReader();
/*      */     byte[] b;
/*      */     byte[] b;
/* 2518 */     if (stream.getOffset() < 0L) {
/* 2519 */       b = stream.getBytes();
/*      */     } else {
/* 2521 */       b = new byte[stream.getLength()];
/* 2522 */       file.seek(stream.getOffset());
/* 2523 */       file.readFully(b);
/* 2524 */       PdfEncryption decrypt = reader.getDecrypt();
/* 2525 */       if (decrypt != null) {
/* 2526 */         PdfObject filter = getPdfObjectRelease(stream.get(PdfName.FILTER));
/* 2527 */         ArrayList filters = new ArrayList();
/* 2528 */         if (filter != null) {
/* 2529 */           if (filter.isName())
/* 2530 */             filters.add(filter);
/* 2531 */           else if (filter.isArray())
/* 2532 */             filters = ((PdfArray)filter).getArrayList();
/*      */         }
/* 2534 */         boolean skip = false;
/* 2535 */         for (int k = 0; k < filters.size(); k++) {
/* 2536 */           PdfObject obj = getPdfObjectRelease((PdfObject)filters.get(k));
/* 2537 */           if ((obj != null) && (obj.toString().equals("/Crypt"))) {
/* 2538 */             skip = true;
/* 2539 */             break;
/*      */           }
/*      */         }
/* 2542 */         if (!skip) {
/* 2543 */           decrypt.setHashKey(stream.getObjNum(), stream.getObjGen());
/* 2544 */           b = decrypt.decryptByteArray(b);
/*      */         }
/*      */       }
/*      */     }
/* 2548 */     return b;
/*      */   }
/*      */ 
/*      */   public static byte[] getStreamBytesRaw(PRStream stream)
/*      */     throws IOException
/*      */   {
/* 2557 */     RandomAccessFileOrArray rf = stream.getReader().getSafeFile();
/*      */     try {
/* 2559 */       rf.reOpen();
/* 2560 */       return getStreamBytesRaw(stream, rf);
/*      */     } finally {
/*      */       try {
/* 2563 */         rf.close(); } catch (Exception e) {
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void eliminateSharedStreams() {
/* 2569 */     if (!this.sharedStreams)
/* 2570 */       return;
/* 2571 */     this.sharedStreams = false;
/* 2572 */     if (this.pageRefs.size() == 1)
/* 2573 */       return;
/* 2574 */     ArrayList newRefs = new ArrayList();
/* 2575 */     ArrayList newStreams = new ArrayList();
/* 2576 */     IntHashtable visited = new IntHashtable();
/* 2577 */     for (int k = 1; k <= this.pageRefs.size(); k++) {
/* 2578 */       PdfDictionary page = this.pageRefs.getPageN(k);
/* 2579 */       if (page != null)
/*      */       {
/* 2581 */         PdfObject contents = getPdfObject(page.get(PdfName.CONTENTS));
/* 2582 */         if (contents != null)
/*      */         {
/* 2584 */           if (contents.isStream()) {
/* 2585 */             PRIndirectReference ref = (PRIndirectReference)page.get(PdfName.CONTENTS);
/* 2586 */             if (visited.containsKey(ref.getNumber()))
/*      */             {
/* 2588 */               newRefs.add(ref);
/* 2589 */               newStreams.add(new PRStream((PRStream)contents, null));
/*      */             }
/*      */             else {
/* 2592 */               visited.put(ref.getNumber(), 1);
/*      */             }
/* 2594 */           } else if (contents.isArray()) {
/* 2595 */             PdfArray array = (PdfArray)contents;
/* 2596 */             for (int j = 0; j < array.size(); j++) {
/* 2597 */               PRIndirectReference ref = (PRIndirectReference)array.getPdfObject(j);
/* 2598 */               if (visited.containsKey(ref.getNumber()))
/*      */               {
/* 2600 */                 newRefs.add(ref);
/* 2601 */                 newStreams.add(new PRStream((PRStream)getPdfObject(ref), null));
/*      */               }
/*      */               else {
/* 2604 */                 visited.put(ref.getNumber(), 1);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 2608 */     if (newStreams.isEmpty())
/* 2609 */       return;
/* 2610 */     for (int k = 0; k < newStreams.size(); k++) {
/* 2611 */       this.xrefObj.add(newStreams.get(k));
/* 2612 */       PRIndirectReference ref = (PRIndirectReference)newRefs.get(k);
/* 2613 */       ref.setNumber(this.xrefObj.size() - 1, 0);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isTampered()
/*      */   {
/* 2622 */     return this.tampered;
/*      */   }
/*      */ 
/*      */   public void setTampered(boolean tampered)
/*      */   {
/* 2630 */     this.tampered = tampered;
/* 2631 */     this.pageRefs.keepPages();
/*      */   }
/*      */ 
/*      */   public byte[] getMetadata()
/*      */     throws IOException
/*      */   {
/* 2639 */     PdfObject obj = getPdfObject(this.catalog.get(PdfName.METADATA));
/* 2640 */     if (!(obj instanceof PRStream))
/* 2641 */       return null;
/* 2642 */     RandomAccessFileOrArray rf = getSafeFile();
/* 2643 */     byte[] b = null;
/*      */     try {
/* 2645 */       rf.reOpen();
/* 2646 */       b = getStreamBytes((PRStream)obj, rf);
/*      */     }
/*      */     finally {
/*      */       try {
/* 2650 */         rf.close();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*      */       }
/*      */     }
/* 2656 */     return b;
/*      */   }
/*      */ 
/*      */   public long getLastXref()
/*      */   {
/* 2664 */     return this.lastXref;
/*      */   }
/*      */ 
/*      */   public int getXrefSize()
/*      */   {
/* 2672 */     return this.xrefObj.size();
/*      */   }
/*      */ 
/*      */   public long getEofPos()
/*      */   {
/* 2680 */     return this.eofPos;
/*      */   }
/*      */ 
/*      */   public char getPdfVersion()
/*      */   {
/* 2689 */     return this.pdfVersion;
/*      */   }
/*      */ 
/*      */   public boolean isEncrypted()
/*      */   {
/* 2697 */     return this.encrypted;
/*      */   }
/*      */ 
/*      */   public int getPermissions()
/*      */   {
/* 2706 */     return this.pValue;
/*      */   }
/*      */ 
/*      */   public boolean is128Key()
/*      */   {
/* 2714 */     return this.rValue == 3;
/*      */   }
/*      */ 
/*      */   public PdfDictionary getTrailer()
/*      */   {
/* 2722 */     return this.trailer;
/*      */   }
/*      */ 
/*      */   PdfEncryption getDecrypt() {
/* 2726 */     return this.decrypt;
/*      */   }
/*      */ 
/*      */   static boolean equalsn(byte[] a1, byte[] a2) {
/* 2730 */     int length = a2.length;
/* 2731 */     for (int k = 0; k < length; k++) {
/* 2732 */       if (a1[k] != a2[k])
/* 2733 */         return false;
/*      */     }
/* 2735 */     return true;
/*      */   }
/*      */ 
/*      */   static boolean existsName(PdfDictionary dic, PdfName key, PdfName value) {
/* 2739 */     PdfObject type = getPdfObjectRelease(dic.get(key));
/* 2740 */     if ((type == null) || (!type.isName()))
/* 2741 */       return false;
/* 2742 */     PdfName name = (PdfName)type;
/* 2743 */     return name.equals(value);
/*      */   }
/*      */ 
/*      */   static String getFontName(PdfDictionary dic) {
/* 2747 */     if (dic == null)
/* 2748 */       return null;
/* 2749 */     PdfObject type = getPdfObjectRelease(dic.get(PdfName.BASEFONT));
/* 2750 */     if ((type == null) || (!type.isName()))
/* 2751 */       return null;
/* 2752 */     return PdfName.decodeName(type.toString());
/*      */   }
/*      */ 
/*      */   static String getSubsetPrefix(PdfDictionary dic) {
/* 2756 */     if (dic == null)
/* 2757 */       return null;
/* 2758 */     String s = getFontName(dic);
/* 2759 */     if (s == null)
/* 2760 */       return null;
/* 2761 */     if ((s.length() < 8) || (s.charAt(6) != '+'))
/* 2762 */       return null;
/* 2763 */     for (int k = 0; k < 6; k++) {
/* 2764 */       char c = s.charAt(k);
/* 2765 */       if ((c < 'A') || (c > 'Z'))
/* 2766 */         return null;
/*      */     }
/* 2768 */     return s;
/*      */   }
/*      */ 
/*      */   public int shuffleSubsetNames()
/*      */   {
/* 2776 */     int total = 0;
/* 2777 */     for (int k = 1; k < this.xrefObj.size(); k++) {
/* 2778 */       PdfObject obj = getPdfObjectRelease(k);
/* 2779 */       if ((obj != null) && (obj.isDictionary()))
/*      */       {
/* 2781 */         PdfDictionary dic = (PdfDictionary)obj;
/* 2782 */         if (existsName(dic, PdfName.TYPE, PdfName.FONT))
/*      */         {
/* 2784 */           if ((existsName(dic, PdfName.SUBTYPE, PdfName.TYPE1)) || (existsName(dic, PdfName.SUBTYPE, PdfName.MMTYPE1)) || (existsName(dic, PdfName.SUBTYPE, PdfName.TRUETYPE)))
/*      */           {
/* 2787 */             String s = getSubsetPrefix(dic);
/* 2788 */             if (s != null)
/*      */             {
/* 2790 */               String ns = BaseFont.createSubsetPrefix() + s.substring(7);
/* 2791 */               PdfName newName = new PdfName(ns);
/* 2792 */               dic.put(PdfName.BASEFONT, newName);
/* 2793 */               setXrefPartialObject(k, dic);
/* 2794 */               total++;
/* 2795 */               PdfDictionary fd = dic.getAsDict(PdfName.FONTDESCRIPTOR);
/* 2796 */               if (fd != null)
/*      */               {
/* 2798 */                 fd.put(PdfName.FONTNAME, newName);
/*      */               }
/*      */             } } else if (existsName(dic, PdfName.SUBTYPE, PdfName.TYPE0)) {
/* 2801 */             String s = getSubsetPrefix(dic);
/* 2802 */             PdfArray arr = dic.getAsArray(PdfName.DESCENDANTFONTS);
/* 2803 */             if (arr != null)
/*      */             {
/* 2805 */               if (!arr.isEmpty())
/*      */               {
/* 2807 */                 PdfDictionary desc = arr.getAsDict(0);
/* 2808 */                 String sde = getSubsetPrefix(desc);
/* 2809 */                 if (sde != null)
/*      */                 {
/* 2811 */                   String ns = BaseFont.createSubsetPrefix();
/* 2812 */                   if (s != null)
/* 2813 */                     dic.put(PdfName.BASEFONT, new PdfName(ns + s.substring(7)));
/* 2814 */                   setXrefPartialObject(k, dic);
/* 2815 */                   PdfName newName = new PdfName(ns + sde.substring(7));
/* 2816 */                   desc.put(PdfName.BASEFONT, newName);
/* 2817 */                   total++;
/* 2818 */                   PdfDictionary fd = desc.getAsDict(PdfName.FONTDESCRIPTOR);
/* 2819 */                   if (fd != null)
/*      */                   {
/* 2821 */                     fd.put(PdfName.FONTNAME, newName);
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 2824 */     return total;
/*      */   }
/*      */ 
/*      */   public int createFakeFontSubsets()
/*      */   {
/* 2831 */     int total = 0;
/* 2832 */     for (int k = 1; k < this.xrefObj.size(); k++) {
/* 2833 */       PdfObject obj = getPdfObjectRelease(k);
/* 2834 */       if ((obj != null) && (obj.isDictionary()))
/*      */       {
/* 2836 */         PdfDictionary dic = (PdfDictionary)obj;
/* 2837 */         if (existsName(dic, PdfName.TYPE, PdfName.FONT))
/*      */         {
/* 2839 */           if ((existsName(dic, PdfName.SUBTYPE, PdfName.TYPE1)) || (existsName(dic, PdfName.SUBTYPE, PdfName.MMTYPE1)) || (existsName(dic, PdfName.SUBTYPE, PdfName.TRUETYPE)))
/*      */           {
/* 2842 */             String s = getSubsetPrefix(dic);
/* 2843 */             if (s == null)
/*      */             {
/* 2845 */               s = getFontName(dic);
/* 2846 */               if (s != null)
/*      */               {
/* 2848 */                 String ns = BaseFont.createSubsetPrefix() + s;
/* 2849 */                 PdfDictionary fd = (PdfDictionary)getPdfObjectRelease(dic.get(PdfName.FONTDESCRIPTOR));
/* 2850 */                 if (fd != null)
/*      */                 {
/* 2852 */                   if ((fd.get(PdfName.FONTFILE) != null) || (fd.get(PdfName.FONTFILE2) != null) || (fd.get(PdfName.FONTFILE3) != null))
/*      */                   {
/* 2855 */                     fd = dic.getAsDict(PdfName.FONTDESCRIPTOR);
/* 2856 */                     PdfName newName = new PdfName(ns);
/* 2857 */                     dic.put(PdfName.BASEFONT, newName);
/* 2858 */                     fd.put(PdfName.FONTNAME, newName);
/* 2859 */                     setXrefPartialObject(k, dic);
/* 2860 */                     total++;
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 2863 */     return total;
/*      */   }
/*      */ 
/*      */   private static PdfArray getNameArray(PdfObject obj) {
/* 2867 */     if (obj == null)
/* 2868 */       return null;
/* 2869 */     obj = getPdfObjectRelease(obj);
/* 2870 */     if (obj == null)
/* 2871 */       return null;
/* 2872 */     if (obj.isArray())
/* 2873 */       return (PdfArray)obj;
/* 2874 */     if (obj.isDictionary()) {
/* 2875 */       PdfObject arr2 = getPdfObjectRelease(((PdfDictionary)obj).get(PdfName.D));
/* 2876 */       if ((arr2 != null) && (arr2.isArray()))
/* 2877 */         return (PdfArray)arr2;
/*      */     }
/* 2879 */     return null;
/*      */   }
/*      */ 
/*      */   public HashMap<Object, PdfObject> getNamedDestination()
/*      */   {
/* 2888 */     return getNamedDestination(false);
/*      */   }
/*      */ 
/*      */   public HashMap<Object, PdfObject> getNamedDestination(boolean keepNames)
/*      */   {
/* 2899 */     HashMap names = getNamedDestinationFromNames(keepNames);
/* 2900 */     names.putAll(getNamedDestinationFromStrings());
/* 2901 */     return names;
/*      */   }
/*      */ 
/*      */   public HashMap<String, PdfObject> getNamedDestinationFromNames()
/*      */   {
/* 2912 */     return new HashMap(getNamedDestinationFromNames(false));
/*      */   }
/*      */ 
/*      */   public HashMap<Object, PdfObject> getNamedDestinationFromNames(boolean keepNames)
/*      */   {
/* 2923 */     HashMap names = new HashMap();
/*      */     PdfDictionary dic;
/* 2924 */     if (this.catalog.get(PdfName.DESTS) != null) {
/* 2925 */       dic = (PdfDictionary)getPdfObjectRelease(this.catalog.get(PdfName.DESTS));
/* 2926 */       if (dic == null)
/* 2927 */         return names;
/* 2928 */       Set keys = dic.getKeys();
/* 2929 */       for (PdfName key : keys) {
/* 2930 */         PdfArray arr = getNameArray(dic.get(key));
/* 2931 */         if (arr != null)
/*      */         {
/* 2933 */           if (keepNames) {
/* 2934 */             names.put(key, arr);
/*      */           }
/*      */           else {
/* 2937 */             String name = PdfName.decodeName(key.toString());
/* 2938 */             names.put(name, arr);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 2942 */     return names;
/*      */   }
/*      */ 
/*      */   public HashMap<String, PdfObject> getNamedDestinationFromStrings()
/*      */   {
/* 2951 */     if (this.catalog.get(PdfName.NAMES) != null) {
/* 2952 */       PdfDictionary dic = (PdfDictionary)getPdfObjectRelease(this.catalog.get(PdfName.NAMES));
/* 2953 */       if (dic != null) {
/* 2954 */         dic = (PdfDictionary)getPdfObjectRelease(dic.get(PdfName.DESTS));
/* 2955 */         if (dic != null) {
/* 2956 */           HashMap names = PdfNameTree.readTree(dic);
/* 2957 */           for (Iterator it = names.entrySet().iterator(); it.hasNext(); ) {
/* 2958 */             Map.Entry entry = (Map.Entry)it.next();
/* 2959 */             PdfArray arr = getNameArray((PdfObject)entry.getValue());
/* 2960 */             if (arr != null)
/* 2961 */               entry.setValue(arr);
/*      */             else
/* 2963 */               it.remove();
/*      */           }
/* 2965 */           return names;
/*      */         }
/*      */       }
/*      */     }
/* 2969 */     return new HashMap();
/*      */   }
/*      */ 
/*      */   public void removeFields()
/*      */   {
/* 2976 */     this.pageRefs.resetReleasePage();
/* 2977 */     for (int k = 1; k <= this.pageRefs.size(); k++) {
/* 2978 */       PdfDictionary page = this.pageRefs.getPageN(k);
/* 2979 */       PdfArray annots = page.getAsArray(PdfName.ANNOTS);
/* 2980 */       if (annots == null) {
/* 2981 */         this.pageRefs.releasePage(k);
/*      */       }
/*      */       else {
/* 2984 */         for (int j = 0; j < annots.size(); j++) {
/* 2985 */           PdfObject obj = getPdfObjectRelease(annots.getPdfObject(j));
/* 2986 */           if ((obj != null) && (obj.isDictionary()))
/*      */           {
/* 2988 */             PdfDictionary annot = (PdfDictionary)obj;
/* 2989 */             if (PdfName.WIDGET.equals(annot.get(PdfName.SUBTYPE)))
/* 2990 */               annots.remove(j--); 
/*      */           }
/*      */         }
/* 2992 */         if (annots.isEmpty())
/* 2993 */           page.remove(PdfName.ANNOTS);
/*      */         else
/* 2995 */           this.pageRefs.releasePage(k); 
/*      */       }
/*      */     }
/* 2997 */     this.catalog.remove(PdfName.ACROFORM);
/* 2998 */     this.pageRefs.resetReleasePage();
/*      */   }
/*      */ 
/*      */   public void removeAnnotations()
/*      */   {
/* 3005 */     this.pageRefs.resetReleasePage();
/* 3006 */     for (int k = 1; k <= this.pageRefs.size(); k++) {
/* 3007 */       PdfDictionary page = this.pageRefs.getPageN(k);
/* 3008 */       if (page.get(PdfName.ANNOTS) == null)
/* 3009 */         this.pageRefs.releasePage(k);
/*      */       else
/* 3011 */         page.remove(PdfName.ANNOTS);
/*      */     }
/* 3013 */     this.catalog.remove(PdfName.ACROFORM);
/* 3014 */     this.pageRefs.resetReleasePage();
/*      */   }
/*      */ 
/*      */   public ArrayList<PdfAnnotation.PdfImportedLink> getLinks(int page)
/*      */   {
/* 3023 */     this.pageRefs.resetReleasePage();
/* 3024 */     ArrayList result = new ArrayList();
/* 3025 */     PdfDictionary pageDic = this.pageRefs.getPageN(page);
/* 3026 */     if (pageDic.get(PdfName.ANNOTS) != null) {
/* 3027 */       PdfArray annots = pageDic.getAsArray(PdfName.ANNOTS);
/* 3028 */       for (int j = 0; j < annots.size(); j++) {
/* 3029 */         PdfDictionary annot = (PdfDictionary)getPdfObjectRelease(annots.getPdfObject(j));
/*      */ 
/* 3031 */         if (PdfName.LINK.equals(annot.get(PdfName.SUBTYPE))) {
/* 3032 */           result.add(new PdfAnnotation.PdfImportedLink(annot));
/*      */         }
/*      */       }
/*      */     }
/* 3036 */     this.pageRefs.releasePage(page);
/* 3037 */     this.pageRefs.resetReleasePage();
/* 3038 */     return result;
/*      */   }
/*      */ 
/*      */   private void iterateBookmarks(PdfObject outlineRef, HashMap<Object, PdfObject> names) {
/* 3042 */     while (outlineRef != null) {
/* 3043 */       replaceNamedDestination(outlineRef, names);
/* 3044 */       PdfDictionary outline = (PdfDictionary)getPdfObjectRelease(outlineRef);
/* 3045 */       PdfObject first = outline.get(PdfName.FIRST);
/* 3046 */       if (first != null) {
/* 3047 */         iterateBookmarks(first, names);
/*      */       }
/* 3049 */       outlineRef = outline.get(PdfName.NEXT);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void makeRemoteNamedDestinationsLocal()
/*      */   {
/* 3058 */     if (this.remoteToLocalNamedDestinations)
/* 3059 */       return;
/* 3060 */     this.remoteToLocalNamedDestinations = true;
/* 3061 */     HashMap names = getNamedDestination(true);
/* 3062 */     if (names.isEmpty())
/* 3063 */       return;
/* 3064 */     for (int k = 1; k <= this.pageRefs.size(); k++) {
/* 3065 */       PdfDictionary page = this.pageRefs.getPageN(k);
/*      */       PdfObject annotsRef;
/* 3067 */       PdfArray annots = (PdfArray)getPdfObject(annotsRef = page.get(PdfName.ANNOTS));
/* 3068 */       int annotIdx = this.lastXrefPartial;
/* 3069 */       releaseLastXrefPartial();
/* 3070 */       if (annots == null) {
/* 3071 */         this.pageRefs.releasePage(k);
/*      */       }
/*      */       else {
/* 3074 */         boolean commitAnnots = false;
/* 3075 */         for (int an = 0; an < annots.size(); an++) {
/* 3076 */           PdfObject objRef = annots.getPdfObject(an);
/* 3077 */           if ((convertNamedDestination(objRef, names)) && (!objRef.isIndirect()))
/* 3078 */             commitAnnots = true;
/*      */         }
/* 3080 */         if (commitAnnots)
/* 3081 */           setXrefPartialObject(annotIdx, annots);
/* 3082 */         if ((!commitAnnots) || (annotsRef.isIndirect()))
/* 3083 */           this.pageRefs.releasePage(k);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean convertNamedDestination(PdfObject obj, HashMap<Object, PdfObject> names)
/*      */   {
/* 3095 */     obj = getPdfObject(obj);
/* 3096 */     int objIdx = this.lastXrefPartial;
/* 3097 */     releaseLastXrefPartial();
/* 3098 */     if ((obj != null) && (obj.isDictionary())) {
/* 3099 */       PdfObject ob2 = getPdfObject(((PdfDictionary)obj).get(PdfName.A));
/* 3100 */       if (ob2 != null) {
/* 3101 */         int obj2Idx = this.lastXrefPartial;
/* 3102 */         releaseLastXrefPartial();
/* 3103 */         PdfDictionary dic = (PdfDictionary)ob2;
/* 3104 */         PdfName type = (PdfName)getPdfObjectRelease(dic.get(PdfName.S));
/* 3105 */         if (PdfName.GOTOR.equals(type)) {
/* 3106 */           PdfObject ob3 = getPdfObjectRelease(dic.get(PdfName.D));
/* 3107 */           Object name = null;
/* 3108 */           if (ob3 != null) {
/* 3109 */             if (ob3.isName())
/* 3110 */               name = ob3;
/* 3111 */             else if (ob3.isString())
/* 3112 */               name = ob3.toString();
/* 3113 */             PdfArray dest = (PdfArray)names.get(name);
/* 3114 */             if (dest != null) {
/* 3115 */               dic.remove(PdfName.F);
/* 3116 */               dic.remove(PdfName.NEWWINDOW);
/* 3117 */               dic.put(PdfName.S, PdfName.GOTO);
/* 3118 */               setXrefPartialObject(obj2Idx, ob2);
/* 3119 */               setXrefPartialObject(objIdx, obj);
/* 3120 */               return true;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 3126 */     return false;
/*      */   }
/*      */ 
/*      */   public void consolidateNamedDestinations()
/*      */   {
/* 3131 */     if (this.consolidateNamedDestinations)
/* 3132 */       return;
/* 3133 */     this.consolidateNamedDestinations = true;
/* 3134 */     HashMap names = getNamedDestination(true);
/* 3135 */     if (names.isEmpty())
/* 3136 */       return;
/* 3137 */     for (int k = 1; k <= this.pageRefs.size(); k++) {
/* 3138 */       PdfDictionary page = this.pageRefs.getPageN(k);
/*      */       PdfObject annotsRef;
/* 3140 */       PdfArray annots = (PdfArray)getPdfObject(annotsRef = page.get(PdfName.ANNOTS));
/* 3141 */       int annotIdx = this.lastXrefPartial;
/* 3142 */       releaseLastXrefPartial();
/* 3143 */       if (annots == null) {
/* 3144 */         this.pageRefs.releasePage(k);
/*      */       }
/*      */       else {
/* 3147 */         boolean commitAnnots = false;
/* 3148 */         for (int an = 0; an < annots.size(); an++) {
/* 3149 */           PdfObject objRef = annots.getPdfObject(an);
/* 3150 */           if ((replaceNamedDestination(objRef, names)) && (!objRef.isIndirect()))
/* 3151 */             commitAnnots = true;
/*      */         }
/* 3153 */         if (commitAnnots)
/* 3154 */           setXrefPartialObject(annotIdx, annots);
/* 3155 */         if ((!commitAnnots) || (annotsRef.isIndirect()))
/* 3156 */           this.pageRefs.releasePage(k); 
/*      */       }
/*      */     }
/* 3158 */     PdfDictionary outlines = (PdfDictionary)getPdfObjectRelease(this.catalog.get(PdfName.OUTLINES));
/* 3159 */     if (outlines == null)
/* 3160 */       return;
/* 3161 */     iterateBookmarks(outlines.get(PdfName.FIRST), names);
/*      */   }
/*      */ 
/*      */   private boolean replaceNamedDestination(PdfObject obj, HashMap<Object, PdfObject> names) {
/* 3165 */     obj = getPdfObject(obj);
/* 3166 */     int objIdx = this.lastXrefPartial;
/* 3167 */     releaseLastXrefPartial();
/* 3168 */     if ((obj != null) && (obj.isDictionary())) {
/* 3169 */       PdfObject ob2 = getPdfObjectRelease(((PdfDictionary)obj).get(PdfName.DEST));
/* 3170 */       Object name = null;
/* 3171 */       if (ob2 != null) {
/* 3172 */         if (ob2.isName())
/* 3173 */           name = ob2;
/* 3174 */         else if (ob2.isString())
/* 3175 */           name = ob2.toString();
/* 3176 */         PdfArray dest = (PdfArray)names.get(name);
/* 3177 */         if (dest != null) {
/* 3178 */           ((PdfDictionary)obj).put(PdfName.DEST, dest);
/* 3179 */           setXrefPartialObject(objIdx, obj);
/* 3180 */           return true;
/*      */         }
/*      */       }
/* 3183 */       else if ((ob2 = getPdfObject(((PdfDictionary)obj).get(PdfName.A))) != null) {
/* 3184 */         int obj2Idx = this.lastXrefPartial;
/* 3185 */         releaseLastXrefPartial();
/* 3186 */         PdfDictionary dic = (PdfDictionary)ob2;
/* 3187 */         PdfName type = (PdfName)getPdfObjectRelease(dic.get(PdfName.S));
/* 3188 */         if (PdfName.GOTO.equals(type)) {
/* 3189 */           PdfObject ob3 = getPdfObjectRelease(dic.get(PdfName.D));
/* 3190 */           if (ob3 != null) {
/* 3191 */             if (ob3.isName())
/* 3192 */               name = ob3;
/* 3193 */             else if (ob3.isString())
/* 3194 */               name = ob3.toString();
/*      */           }
/* 3196 */           PdfArray dest = (PdfArray)names.get(name);
/* 3197 */           if (dest != null) {
/* 3198 */             dic.put(PdfName.D, dest);
/* 3199 */             setXrefPartialObject(obj2Idx, ob2);
/* 3200 */             setXrefPartialObject(objIdx, obj);
/* 3201 */             return true;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 3206 */     return false;
/*      */   }
/*      */ 
/*      */   protected static PdfDictionary duplicatePdfDictionary(PdfDictionary original, PdfDictionary copy, PdfReader newReader) {
/* 3210 */     if (copy == null)
/* 3211 */       copy = new PdfDictionary();
/* 3212 */     for (Object element : original.getKeys()) {
/* 3213 */       PdfName key = (PdfName)element;
/* 3214 */       copy.put(key, duplicatePdfObject(original.get(key), newReader));
/*      */     }
/* 3216 */     return copy;
/*      */   }
/*      */ 
/*      */   protected static PdfObject duplicatePdfObject(PdfObject original, PdfReader newReader) {
/* 3220 */     if (original == null)
/* 3221 */       return null;
/* 3222 */     switch (original.type()) {
/*      */     case 6:
/* 3224 */       return duplicatePdfDictionary((PdfDictionary)original, null, newReader);
/*      */     case 7:
/* 3227 */       PRStream org = (PRStream)original;
/* 3228 */       PRStream stream = new PRStream(org, null, newReader);
/* 3229 */       duplicatePdfDictionary(org, stream, newReader);
/* 3230 */       return stream;
/*      */     case 5:
/* 3233 */       PdfArray arr = new PdfArray();
/* 3234 */       for (Iterator it = ((PdfArray)original).listIterator(); it.hasNext(); ) {
/* 3235 */         arr.add(duplicatePdfObject((PdfObject)it.next(), newReader));
/*      */       }
/* 3237 */       return arr;
/*      */     case 10:
/* 3240 */       PRIndirectReference org = (PRIndirectReference)original;
/* 3241 */       return new PRIndirectReference(newReader, org.getNumber(), org.getGeneration());
/*      */     case 8:
/*      */     case 9:
/* 3244 */     }return original;
/*      */   }
/*      */ 
/*      */   public void close()
/*      */   {
/*      */     try
/*      */     {
/* 3253 */       this.tokens.close();
/*      */     }
/*      */     catch (IOException e) {
/* 3256 */       throw new ExceptionConverter(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void removeUnusedNode(PdfObject obj, boolean[] hits)
/*      */   {
/* 3262 */     Stack state = new Stack();
/* 3263 */     state.push(obj);
/* 3264 */     while (!state.empty()) {
/* 3265 */       Object current = state.pop();
/* 3266 */       if (current != null)
/*      */       {
/* 3268 */         ArrayList ar = null;
/* 3269 */         PdfDictionary dic = null;
/* 3270 */         PdfName[] keys = null;
/* 3271 */         Object[] objs = null;
/* 3272 */         int idx = 0;
/* 3273 */         if ((current instanceof PdfObject)) {
/* 3274 */           obj = (PdfObject)current;
/* 3275 */           switch (obj.type()) {
/*      */           case 6:
/*      */           case 7:
/* 3278 */             dic = (PdfDictionary)obj;
/* 3279 */             keys = new PdfName[dic.size()];
/* 3280 */             dic.getKeys().toArray(keys);
/* 3281 */             break;
/*      */           case 5:
/* 3283 */             ar = ((PdfArray)obj).getArrayList();
/* 3284 */             break;
/*      */           case 10:
/* 3286 */             PRIndirectReference ref = (PRIndirectReference)obj;
/* 3287 */             int num = ref.getNumber();
/* 3288 */             if (hits[num] != 0) continue;
/* 3289 */             hits[num] = true;
/* 3290 */             state.push(getPdfObjectRelease(ref)); break;
/*      */           case 8:
/*      */           case 9:
/*      */           default:
/* 3294 */             continue; break;
/*      */           }
/*      */         }
/*      */         else {
/* 3298 */           objs = (Object[])current;
/* 3299 */           if ((objs[0] instanceof ArrayList)) {
/* 3300 */             ar = (ArrayList)objs[0];
/* 3301 */             idx = ((Integer)objs[1]).intValue();
/*      */           }
/*      */           else {
/* 3304 */             keys = (PdfName[])objs[0];
/* 3305 */             dic = (PdfDictionary)objs[1];
/* 3306 */             idx = ((Integer)objs[2]).intValue();
/*      */           }
/*      */ 
/* 3309 */           if (ar != null) {
/* 3310 */             for (int k = idx; k < ar.size(); k++) {
/* 3311 */               PdfObject v = (PdfObject)ar.get(k);
/* 3312 */               if (v.isIndirect()) {
/* 3313 */                 int num = ((PRIndirectReference)v).getNumber();
/* 3314 */                 if ((num >= this.xrefObj.size()) || ((!this.partial) && (this.xrefObj.get(num) == null))) {
/* 3315 */                   ar.set(k, PdfNull.PDFNULL);
/* 3316 */                   continue;
/*      */                 }
/*      */               }
/* 3319 */               if (objs == null) {
/* 3320 */                 state.push(new Object[] { ar, Integer.valueOf(k + 1) });
/*      */               } else {
/* 3322 */                 objs[1] = Integer.valueOf(k + 1);
/* 3323 */                 state.push(objs);
/*      */               }
/* 3325 */               state.push(v);
/* 3326 */               break;
/*      */             }
/*      */           }
/*      */           else
/* 3330 */             for (int k = idx; k < keys.length; k++) {
/* 3331 */               PdfName key = keys[k];
/* 3332 */               PdfObject v = dic.get(key);
/* 3333 */               if (v.isIndirect()) {
/* 3334 */                 int num = ((PRIndirectReference)v).getNumber();
/* 3335 */                 if ((num < 0) || (num >= this.xrefObj.size()) || ((!this.partial) && (this.xrefObj.get(num) == null))) {
/* 3336 */                   dic.put(key, PdfNull.PDFNULL);
/* 3337 */                   continue;
/*      */                 }
/*      */               }
/* 3340 */               if (objs == null) {
/* 3341 */                 state.push(new Object[] { keys, dic, Integer.valueOf(k + 1) });
/*      */               } else {
/* 3343 */                 objs[2] = Integer.valueOf(k + 1);
/* 3344 */                 state.push(objs);
/*      */               }
/* 3346 */               state.push(v);
/* 3347 */               break;
/*      */             }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public int removeUnusedObjects()
/*      */   {
/* 3358 */     boolean[] hits = new boolean[this.xrefObj.size()];
/* 3359 */     removeUnusedNode(this.trailer, hits);
/* 3360 */     int total = 0;
/* 3361 */     if (this.partial) {
/* 3362 */       for (int k = 1; k < hits.length; k++) {
/* 3363 */         if (hits[k] == 0) {
/* 3364 */           this.xref[(k * 2)] = -1L;
/* 3365 */           this.xref[(k * 2 + 1)] = 0L;
/* 3366 */           this.xrefObj.set(k, null);
/* 3367 */           total++;
/*      */         }
/*      */       }
/*      */     }
/*      */     else {
/* 3372 */       for (int k = 1; k < hits.length; k++) {
/* 3373 */         if (hits[k] == 0) {
/* 3374 */           this.xrefObj.set(k, null);
/* 3375 */           total++;
/*      */         }
/*      */       }
/*      */     }
/* 3379 */     return total;
/*      */   }
/*      */ 
/*      */   public AcroFields getAcroFields()
/*      */   {
/* 3386 */     return new AcroFields(this, null);
/*      */   }
/*      */ 
/*      */   public String getJavaScript(RandomAccessFileOrArray file)
/*      */     throws IOException
/*      */   {
/* 3396 */     PdfDictionary names = (PdfDictionary)getPdfObjectRelease(this.catalog.get(PdfName.NAMES));
/* 3397 */     if (names == null)
/* 3398 */       return null;
/* 3399 */     PdfDictionary js = (PdfDictionary)getPdfObjectRelease(names.get(PdfName.JAVASCRIPT));
/* 3400 */     if (js == null)
/* 3401 */       return null;
/* 3402 */     HashMap jscript = PdfNameTree.readTree(js);
/* 3403 */     String[] sortedNames = new String[jscript.size()];
/* 3404 */     sortedNames = (String[])jscript.keySet().toArray(sortedNames);
/* 3405 */     Arrays.sort(sortedNames);
/* 3406 */     StringBuffer buf = new StringBuffer();
/* 3407 */     for (int k = 0; k < sortedNames.length; k++) {
/* 3408 */       PdfDictionary j = (PdfDictionary)getPdfObjectRelease((PdfObject)jscript.get(sortedNames[k]));
/* 3409 */       if (j != null)
/*      */       {
/* 3411 */         PdfObject obj = getPdfObjectRelease(j.get(PdfName.JS));
/* 3412 */         if (obj != null)
/* 3413 */           if (obj.isString()) {
/* 3414 */             buf.append(((PdfString)obj).toUnicodeString()).append('\n');
/* 3415 */           } else if (obj.isStream()) {
/* 3416 */             byte[] bytes = getStreamBytes((PRStream)obj, file);
/* 3417 */             if ((bytes.length >= 2) && (bytes[0] == -2) && (bytes[1] == -1))
/* 3418 */               buf.append(PdfEncodings.convertToString(bytes, "UnicodeBig"));
/*      */             else
/* 3420 */               buf.append(PdfEncodings.convertToString(bytes, "PDF"));
/* 3421 */             buf.append('\n');
/*      */           }
/*      */       }
/*      */     }
/* 3425 */     return buf.toString();
/*      */   }
/*      */ 
/*      */   public String getJavaScript()
/*      */     throws IOException
/*      */   {
/* 3434 */     RandomAccessFileOrArray rf = getSafeFile();
/*      */     try {
/* 3436 */       rf.reOpen();
/* 3437 */       return getJavaScript(rf);
/*      */     } finally {
/*      */       try {
/* 3440 */         rf.close();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void selectPages(String ranges)
/*      */   {
/* 3451 */     selectPages(SequenceList.expand(ranges, getNumberOfPages()));
/*      */   }
/*      */ 
/*      */   public void selectPages(List<Integer> pagesToKeep)
/*      */   {
/* 3461 */     selectPages(pagesToKeep, true);
/*      */   }
/*      */ 
/*      */   protected void selectPages(List<Integer> pagesToKeep, boolean removeUnused)
/*      */   {
/* 3472 */     this.pageRefs.selectPages(pagesToKeep);
/* 3473 */     if (removeUnused) removeUnusedObjects();
/*      */   }
/*      */ 
/*      */   public void setViewerPreferences(int preferences)
/*      */   {
/* 3481 */     this.viewerPreferences.setViewerPreferences(preferences);
/* 3482 */     setViewerPreferences(this.viewerPreferences);
/*      */   }
/*      */ 
/*      */   public void addViewerPreference(PdfName key, PdfObject value)
/*      */   {
/* 3491 */     this.viewerPreferences.addViewerPreference(key, value);
/* 3492 */     setViewerPreferences(this.viewerPreferences);
/*      */   }
/*      */ 
/*      */   public void setViewerPreferences(PdfViewerPreferencesImp vp) {
/* 3496 */     vp.addToCatalog(this.catalog);
/*      */   }
/*      */ 
/*      */   public int getSimpleViewerPreferences()
/*      */   {
/* 3505 */     return PdfViewerPreferencesImp.getViewerPreferences(this.catalog).getPageLayoutAndMode();
/*      */   }
/*      */ 
/*      */   public boolean isAppendable()
/*      */   {
/* 3513 */     return this.appendable;
/*      */   }
/*      */ 
/*      */   public void setAppendable(boolean appendable)
/*      */   {
/* 3521 */     this.appendable = appendable;
/* 3522 */     if (appendable)
/* 3523 */       getPdfObject(this.trailer.get(PdfName.ROOT));
/*      */   }
/*      */ 
/*      */   public boolean isNewXrefType()
/*      */   {
/* 3531 */     return this.newXrefType;
/*      */   }
/*      */ 
/*      */   public long getFileLength()
/*      */   {
/* 3539 */     return this.fileLength;
/*      */   }
/*      */ 
/*      */   public boolean isHybridXref()
/*      */   {
/* 3547 */     return this.hybridXref;
/*      */   }
/*      */ 
/*      */   PdfIndirectReference getCryptoRef()
/*      */   {
/* 3888 */     if (this.cryptoRef == null)
/* 3889 */       return null;
/* 3890 */     return new PdfIndirectReference(0, this.cryptoRef.getNumber(), this.cryptoRef.getGeneration());
/*      */   }
/*      */ 
/*      */   public boolean hasUsageRights()
/*      */   {
/* 3899 */     PdfDictionary perms = this.catalog.getAsDict(PdfName.PERMS);
/* 3900 */     if (perms == null)
/* 3901 */       return false;
/* 3902 */     return (perms.contains(PdfName.UR)) || (perms.contains(PdfName.UR3));
/*      */   }
/*      */ 
/*      */   public void removeUsageRights()
/*      */   {
/* 3911 */     PdfDictionary perms = this.catalog.getAsDict(PdfName.PERMS);
/* 3912 */     if (perms == null)
/* 3913 */       return;
/* 3914 */     perms.remove(PdfName.UR);
/* 3915 */     perms.remove(PdfName.UR3);
/* 3916 */     if (perms.size() == 0)
/* 3917 */       this.catalog.remove(PdfName.PERMS);
/*      */   }
/*      */ 
/*      */   public int getCertificationLevel()
/*      */   {
/* 3931 */     PdfDictionary dic = this.catalog.getAsDict(PdfName.PERMS);
/* 3932 */     if (dic == null)
/* 3933 */       return 0;
/* 3934 */     dic = dic.getAsDict(PdfName.DOCMDP);
/* 3935 */     if (dic == null)
/* 3936 */       return 0;
/* 3937 */     PdfArray arr = dic.getAsArray(PdfName.REFERENCE);
/* 3938 */     if ((arr == null) || (arr.size() == 0))
/* 3939 */       return 0;
/* 3940 */     dic = arr.getAsDict(0);
/* 3941 */     if (dic == null)
/* 3942 */       return 0;
/* 3943 */     dic = dic.getAsDict(PdfName.TRANSFORMPARAMS);
/* 3944 */     if (dic == null)
/* 3945 */       return 0;
/* 3946 */     PdfNumber p = dic.getAsNumber(PdfName.P);
/* 3947 */     if (p == null)
/* 3948 */       return 0;
/* 3949 */     return p.intValue();
/*      */   }
/*      */ 
/*      */   public final boolean isOpenedWithFullPermissions()
/*      */   {
/* 3960 */     return (!this.encrypted) || (this.ownerPasswordUsed) || (unethicalreading);
/*      */   }
/*      */ 
/*      */   public int getCryptoMode()
/*      */   {
/* 3967 */     if (this.decrypt == null) {
/* 3968 */       return -1;
/*      */     }
/* 3970 */     return this.decrypt.getCryptoMode();
/*      */   }
/*      */ 
/*      */   public boolean isMetadataEncrypted()
/*      */   {
/* 3977 */     if (this.decrypt == null) {
/* 3978 */       return false;
/*      */     }
/* 3980 */     return this.decrypt.isMetadataEncrypted();
/*      */   }
/*      */ 
/*      */   public byte[] computeUserPassword()
/*      */   {
/* 3987 */     if ((!this.encrypted) || (!this.ownerPasswordUsed)) return null;
/* 3988 */     return this.decrypt.computeUserPassword(this.password);
/*      */   }
/*      */ 
/*      */   static class PageRefs
/*      */   {
/*      */     private final PdfReader reader;
/*      */     private ArrayList<PRIndirectReference> refsn;
/*      */     private int sizep;
/*      */     private IntHashtable refsp;
/* 3559 */     private int lastPageRead = -1;
/*      */     private ArrayList<PdfDictionary> pageInh;
/*      */     private boolean keepPages;
/*      */ 
/*      */     private PageRefs(PdfReader reader)
/*      */       throws IOException
/*      */     {
/* 3565 */       this.reader = reader;
/* 3566 */       if (reader.partial) {
/* 3567 */         this.refsp = new IntHashtable();
/* 3568 */         PdfNumber npages = (PdfNumber)PdfReader.getPdfObjectRelease(reader.rootPages.get(PdfName.COUNT));
/* 3569 */         this.sizep = npages.intValue();
/*      */       }
/*      */       else {
/* 3572 */         readPages();
/*      */       }
/*      */     }
/*      */ 
/*      */     PageRefs(PageRefs other, PdfReader reader) {
/* 3577 */       this.reader = reader;
/* 3578 */       this.sizep = other.sizep;
/* 3579 */       if (other.refsn != null) {
/* 3580 */         this.refsn = new ArrayList(other.refsn);
/* 3581 */         for (int k = 0; k < this.refsn.size(); k++)
/* 3582 */           this.refsn.set(k, (PRIndirectReference)PdfReader.duplicatePdfObject((PdfObject)this.refsn.get(k), reader));
/*      */       }
/*      */       else
/*      */       {
/* 3586 */         this.refsp = ((IntHashtable)other.refsp.clone());
/*      */       }
/*      */     }
/*      */ 
/* 3590 */     int size() { if (this.refsn != null) {
/* 3591 */         return this.refsn.size();
/*      */       }
/* 3593 */       return this.sizep; }
/*      */ 
/*      */     void readPages() throws IOException
/*      */     {
/* 3597 */       if (this.refsn != null)
/* 3598 */         return;
/* 3599 */       this.refsp = null;
/* 3600 */       this.refsn = new ArrayList();
/* 3601 */       this.pageInh = new ArrayList();
/* 3602 */       iteratePages((PRIndirectReference)this.reader.catalog.get(PdfName.PAGES));
/* 3603 */       this.pageInh = null;
/* 3604 */       this.reader.rootPages.put(PdfName.COUNT, new PdfNumber(this.refsn.size()));
/*      */     }
/*      */ 
/*      */     void reReadPages() throws IOException {
/* 3608 */       this.refsn = null;
/* 3609 */       readPages();
/*      */     }
/*      */ 
/*      */     public PdfDictionary getPageN(int pageNum)
/*      */     {
/* 3617 */       PRIndirectReference ref = getPageOrigRef(pageNum);
/* 3618 */       return (PdfDictionary)PdfReader.getPdfObject(ref);
/*      */     }
/*      */ 
/*      */     public PdfDictionary getPageNRelease(int pageNum)
/*      */     {
/* 3626 */       PdfDictionary page = getPageN(pageNum);
/* 3627 */       releasePage(pageNum);
/* 3628 */       return page;
/*      */     }
/*      */ 
/*      */     public PRIndirectReference getPageOrigRefRelease(int pageNum)
/*      */     {
/* 3636 */       PRIndirectReference ref = getPageOrigRef(pageNum);
/* 3637 */       releasePage(pageNum);
/* 3638 */       return ref;
/*      */     }
/*      */ 
/*      */     public PRIndirectReference getPageOrigRef(int pageNum)
/*      */     {
/*      */       try
/*      */       {
/* 3648 */         pageNum--;
/* 3649 */         if ((pageNum < 0) || (pageNum >= size()))
/* 3650 */           return null;
/* 3651 */         if (this.refsn != null) {
/* 3652 */           return (PRIndirectReference)this.refsn.get(pageNum);
/*      */         }
/* 3654 */         int n = this.refsp.get(pageNum);
/* 3655 */         if (n == 0) {
/* 3656 */           PRIndirectReference ref = getSinglePage(pageNum);
/* 3657 */           if (this.reader.lastXrefPartial == -1)
/* 3658 */             this.lastPageRead = -1;
/*      */           else
/* 3660 */             this.lastPageRead = pageNum;
/* 3661 */           this.reader.lastXrefPartial = -1;
/* 3662 */           this.refsp.put(pageNum, ref.getNumber());
/* 3663 */           if (this.keepPages)
/* 3664 */             this.lastPageRead = -1;
/* 3665 */           return ref;
/*      */         }
/*      */ 
/* 3668 */         if (this.lastPageRead != pageNum)
/* 3669 */           this.lastPageRead = -1;
/* 3670 */         if (this.keepPages)
/* 3671 */           this.lastPageRead = -1;
/* 3672 */         return new PRIndirectReference(this.reader, n);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 3677 */         throw new ExceptionConverter(e);
/*      */       }
/*      */     }
/*      */ 
/*      */     void keepPages() {
/* 3682 */       if ((this.refsp == null) || (this.keepPages))
/* 3683 */         return;
/* 3684 */       this.keepPages = true;
/* 3685 */       this.refsp.clear();
/*      */     }
/*      */ 
/*      */     public void releasePage(int pageNum)
/*      */     {
/* 3692 */       if (this.refsp == null)
/* 3693 */         return;
/* 3694 */       pageNum--;
/* 3695 */       if ((pageNum < 0) || (pageNum >= size()))
/* 3696 */         return;
/* 3697 */       if (pageNum != this.lastPageRead)
/* 3698 */         return;
/* 3699 */       this.lastPageRead = -1;
/* 3700 */       this.reader.lastXrefPartial = this.refsp.get(pageNum);
/* 3701 */       this.reader.releaseLastXrefPartial();
/* 3702 */       this.refsp.remove(pageNum);
/*      */     }
/*      */ 
/*      */     public void resetReleasePage()
/*      */     {
/* 3709 */       if (this.refsp == null)
/* 3710 */         return;
/* 3711 */       this.lastPageRead = -1;
/*      */     }
/*      */ 
/*      */     void insertPage(int pageNum, PRIndirectReference ref) {
/* 3715 */       pageNum--;
/* 3716 */       if (this.refsn != null) {
/* 3717 */         if (pageNum >= this.refsn.size())
/* 3718 */           this.refsn.add(ref);
/*      */         else
/* 3720 */           this.refsn.add(pageNum, ref);
/*      */       }
/*      */       else {
/* 3723 */         this.sizep += 1;
/* 3724 */         this.lastPageRead = -1;
/* 3725 */         if (pageNum >= size()) {
/* 3726 */           this.refsp.put(size(), ref.getNumber());
/*      */         }
/*      */         else {
/* 3729 */           IntHashtable refs2 = new IntHashtable((this.refsp.size() + 1) * 2);
/* 3730 */           for (Iterator it = this.refsp.getEntryIterator(); it.hasNext(); ) {
/* 3731 */             IntHashtable.Entry entry = (IntHashtable.Entry)it.next();
/* 3732 */             int p = entry.getKey();
/* 3733 */             refs2.put(p >= pageNum ? p + 1 : p, entry.getValue());
/*      */           }
/* 3735 */           refs2.put(pageNum, ref.getNumber());
/* 3736 */           this.refsp = refs2;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     private void pushPageAttributes(PdfDictionary nodePages)
/*      */     {
/* 3746 */       PdfDictionary dic = new PdfDictionary();
/* 3747 */       if (!this.pageInh.isEmpty()) {
/* 3748 */         dic.putAll((PdfDictionary)this.pageInh.get(this.pageInh.size() - 1));
/*      */       }
/* 3750 */       for (int k = 0; k < PdfReader.pageInhCandidates.length; k++) {
/* 3751 */         PdfObject obj = nodePages.get(PdfReader.pageInhCandidates[k]);
/* 3752 */         if (obj != null)
/* 3753 */           dic.put(PdfReader.pageInhCandidates[k], obj);
/*      */       }
/* 3755 */       this.pageInh.add(dic);
/*      */     }
/*      */ 
/*      */     private void popPageAttributes()
/*      */     {
/* 3762 */       this.pageInh.remove(this.pageInh.size() - 1);
/*      */     }
/*      */ 
/*      */     private void iteratePages(PRIndirectReference rpage) throws IOException {
/* 3766 */       PdfDictionary page = (PdfDictionary)PdfReader.getPdfObject(rpage);
/* 3767 */       if (page == null)
/* 3768 */         return;
/* 3769 */       PdfArray kidsPR = page.getAsArray(PdfName.KIDS);
/*      */ 
/* 3771 */       if (kidsPR == null) {
/* 3772 */         page.put(PdfName.TYPE, PdfName.PAGE);
/* 3773 */         PdfDictionary dic = (PdfDictionary)this.pageInh.get(this.pageInh.size() - 1);
/*      */ 
/* 3775 */         for (Object element : dic.getKeys()) {
/* 3776 */           PdfName key = (PdfName)element;
/* 3777 */           if (page.get(key) == null)
/* 3778 */             page.put(key, dic.get(key));
/*      */         }
/* 3780 */         if (page.get(PdfName.MEDIABOX) == null) {
/* 3781 */           PdfArray arr = new PdfArray(new float[] { 0.0F, 0.0F, PageSize.LETTER.getRight(), PageSize.LETTER.getTop() });
/* 3782 */           page.put(PdfName.MEDIABOX, arr);
/*      */         }
/* 3784 */         this.refsn.add(rpage);
/*      */       }
/*      */       else
/*      */       {
/* 3788 */         page.put(PdfName.TYPE, PdfName.PAGES);
/* 3789 */         pushPageAttributes(page);
/* 3790 */         for (int k = 0; k < kidsPR.size(); k++) {
/* 3791 */           PdfObject obj = kidsPR.getPdfObject(k);
/* 3792 */           if (!obj.isIndirect()) {
/* 3793 */             while (k < kidsPR.size()) {
/* 3794 */               kidsPR.remove(k);
/*      */             }
/*      */           }
/* 3797 */           iteratePages((PRIndirectReference)obj);
/*      */         }
/* 3799 */         popPageAttributes(); } 
/*      */     }
/* 3804 */     protected PRIndirectReference getSinglePage(int n) { PdfDictionary acc = new PdfDictionary();
/* 3805 */       PdfDictionary top = this.reader.rootPages;
/* 3806 */       int base = 0;
/*      */       Iterator it;
/*      */       while (true) { for (int k = 0; k < PdfReader.pageInhCandidates.length; k++) {
/* 3809 */           PdfObject obj = top.get(PdfReader.pageInhCandidates[k]);
/* 3810 */           if (obj != null)
/* 3811 */             acc.put(PdfReader.pageInhCandidates[k], obj);
/*      */         }
/* 3813 */         PdfArray kids = (PdfArray)PdfReader.getPdfObjectRelease(top.get(PdfName.KIDS));
/* 3814 */         for (it = kids.listIterator(); it.hasNext(); ) {
/* 3815 */           PRIndirectReference ref = (PRIndirectReference)it.next();
/* 3816 */           PdfDictionary dic = (PdfDictionary)PdfReader.getPdfObject(ref);
/* 3817 */           int last = this.reader.lastXrefPartial;
/* 3818 */           PdfObject count = PdfReader.getPdfObjectRelease(dic.get(PdfName.COUNT));
/* 3819 */           this.reader.lastXrefPartial = last;
/* 3820 */           int acn = 1;
/* 3821 */           if ((count != null) && (count.type() == 2))
/* 3822 */             acn = ((PdfNumber)count).intValue();
/* 3823 */           if (n < base + acn) {
/* 3824 */             if (count == null) {
/* 3825 */               dic.mergeDifferent(acc);
/* 3826 */               return ref;
/*      */             }
/* 3828 */             this.reader.releaseLastXrefPartial();
/* 3829 */             top = dic;
/* 3830 */             break;
/*      */           }
/* 3832 */           this.reader.releaseLastXrefPartial();
/* 3833 */           base += acn;
/*      */         } }
/*      */     }
/*      */ 
/*      */     private void selectPages(List<Integer> pagesToKeep)
/*      */     {
/* 3839 */       IntHashtable pg = new IntHashtable();
/* 3840 */       ArrayList finalPages = new ArrayList();
/* 3841 */       int psize = size();
/* 3842 */       for (Integer pi : pagesToKeep) {
/* 3843 */         int p = pi.intValue();
/* 3844 */         if ((p >= 1) && (p <= psize) && (pg.put(p, 1) == 0))
/* 3845 */           finalPages.add(pi);
/*      */       }
/* 3847 */       if (this.reader.partial) {
/* 3848 */         for (int k = 1; k <= psize; k++) {
/* 3849 */           getPageOrigRef(k);
/* 3850 */           resetReleasePage();
/*      */         }
/*      */       }
/* 3853 */       PRIndirectReference parent = (PRIndirectReference)this.reader.catalog.get(PdfName.PAGES);
/* 3854 */       PdfDictionary topPages = (PdfDictionary)PdfReader.getPdfObject(parent);
/* 3855 */       ArrayList newPageRefs = new ArrayList(finalPages.size());
/* 3856 */       PdfArray kids = new PdfArray();
/* 3857 */       for (int k = 0; k < finalPages.size(); k++) {
/* 3858 */         int p = ((Integer)finalPages.get(k)).intValue();
/* 3859 */         PRIndirectReference pref = getPageOrigRef(p);
/* 3860 */         resetReleasePage();
/* 3861 */         kids.add(pref);
/* 3862 */         newPageRefs.add(pref);
/* 3863 */         getPageN(p).put(PdfName.PARENT, parent);
/*      */       }
/* 3865 */       AcroFields af = this.reader.getAcroFields();
/* 3866 */       boolean removeFields = af.getFields().size() > 0;
/* 3867 */       for (int k = 1; k <= psize; k++) {
/* 3868 */         if (!pg.containsKey(k)) {
/* 3869 */           if (removeFields)
/* 3870 */             af.removeFieldsFromPage(k);
/* 3871 */           PRIndirectReference pref = getPageOrigRef(k);
/* 3872 */           int nref = pref.getNumber();
/* 3873 */           this.reader.xrefObj.set(nref, null);
/* 3874 */           if (this.reader.partial) {
/* 3875 */             this.reader.xref[(nref * 2)] = -1L;
/* 3876 */             this.reader.xref[(nref * 2 + 1)] = 0L;
/*      */           }
/*      */         }
/*      */       }
/* 3880 */       topPages.put(PdfName.COUNT, new PdfNumber(finalPages.size()));
/* 3881 */       topPages.put(PdfName.KIDS, kids);
/* 3882 */       this.refsp = null;
/* 3883 */       this.refsn = newPageRefs;
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfReader
 * JD-Core Version:    0.6.2
 */