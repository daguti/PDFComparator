/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.DocWriter;
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.Image;
/*     */ import com.itextpdf.text.Rectangle;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.pdf.collection.PdfCollection;
/*     */ import com.itextpdf.text.pdf.interfaces.PdfEncryptionSettings;
/*     */ import com.itextpdf.text.pdf.interfaces.PdfViewerPreferences;
/*     */ import com.itextpdf.text.pdf.security.LtvVerification;
/*     */ import com.itextpdf.text.xml.xmp.XmpWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.security.cert.Certificate;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PdfStamper
/*     */   implements PdfViewerPreferences, PdfEncryptionSettings
/*     */ {
/*     */   protected PdfStamperImp stamper;
/*     */   private Map<String, String> moreInfo;
/*     */   protected boolean hasSignature;
/*     */   protected PdfSignatureAppearance sigApp;
/*     */   protected XmlSignatureAppearance sigXmlApp;
/*     */   private LtvVerification verification;
/*     */ 
/*     */   public PdfStamper(PdfReader reader, OutputStream os)
/*     */     throws DocumentException, IOException
/*     */   {
/*  98 */     this.stamper = new PdfStamperImp(reader, os, '\000', false);
/*     */   }
/*     */ 
/*     */   public PdfStamper(PdfReader reader, OutputStream os, char pdfVersion)
/*     */     throws DocumentException, IOException
/*     */   {
/* 114 */     this.stamper = new PdfStamperImp(reader, os, pdfVersion, false);
/*     */   }
/*     */ 
/*     */   public PdfStamper(PdfReader reader, OutputStream os, char pdfVersion, boolean append)
/*     */     throws DocumentException, IOException
/*     */   {
/* 132 */     this.stamper = new PdfStamperImp(reader, os, pdfVersion, append);
/*     */   }
/*     */ 
/*     */   public Map<String, String> getMoreInfo()
/*     */   {
/* 141 */     return this.moreInfo;
/*     */   }
/*     */ 
/*     */   public void setMoreInfo(Map<String, String> moreInfo)
/*     */   {
/* 151 */     this.moreInfo = moreInfo;
/*     */   }
/*     */ 
/*     */   public void replacePage(PdfReader r, int pageImported, int pageReplaced)
/*     */   {
/* 164 */     this.stamper.replacePage(r, pageImported, pageReplaced);
/*     */   }
/*     */ 
/*     */   public void insertPage(int pageNumber, Rectangle mediabox)
/*     */   {
/* 175 */     this.stamper.insertPage(pageNumber, mediabox);
/*     */   }
/*     */ 
/*     */   public PdfSignatureAppearance getSignatureAppearance()
/*     */   {
/* 183 */     return this.sigApp;
/*     */   }
/*     */ 
/*     */   public XmlSignatureAppearance getXmlSignatureAppearance()
/*     */   {
/* 191 */     return this.sigXmlApp;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws DocumentException, IOException
/*     */   {
/* 204 */     if (this.stamper.closed)
/* 205 */       return;
/* 206 */     if (!this.hasSignature) {
/* 207 */       mergeVerification();
/* 208 */       this.stamper.close(this.moreInfo);
/*     */     }
/*     */     else {
/* 211 */       throw new DocumentException("Signature defined. Must be closed in PdfSignatureAppearance.");
/*     */     }
/*     */   }
/*     */ 
/*     */   public PdfContentByte getUnderContent(int pageNum)
/*     */   {
/* 222 */     return this.stamper.getUnderContent(pageNum);
/*     */   }
/*     */ 
/*     */   public PdfContentByte getOverContent(int pageNum)
/*     */   {
/* 232 */     return this.stamper.getOverContent(pageNum);
/*     */   }
/*     */ 
/*     */   public boolean isRotateContents()
/*     */   {
/* 240 */     return this.stamper.isRotateContents();
/*     */   }
/*     */ 
/*     */   public void setRotateContents(boolean rotateContents)
/*     */   {
/* 249 */     this.stamper.setRotateContents(rotateContents);
/*     */   }
/*     */ 
/*     */   public void setEncryption(byte[] userPassword, byte[] ownerPassword, int permissions, boolean strength128Bits)
/*     */     throws DocumentException
/*     */   {
/* 265 */     if (this.stamper.isAppend())
/* 266 */       throw new DocumentException(MessageLocalization.getComposedMessage("append.mode.does.not.support.changing.the.encryption.status", new Object[0]));
/* 267 */     if (this.stamper.isContentWritten())
/* 268 */       throw new DocumentException(MessageLocalization.getComposedMessage("content.was.already.written.to.the.output", new Object[0]));
/* 269 */     this.stamper.setEncryption(userPassword, ownerPassword, permissions, strength128Bits ? 1 : 0);
/*     */   }
/*     */ 
/*     */   public void setEncryption(byte[] userPassword, byte[] ownerPassword, int permissions, int encryptionType)
/*     */     throws DocumentException
/*     */   {
/* 286 */     if (this.stamper.isAppend())
/* 287 */       throw new DocumentException(MessageLocalization.getComposedMessage("append.mode.does.not.support.changing.the.encryption.status", new Object[0]));
/* 288 */     if (this.stamper.isContentWritten())
/* 289 */       throw new DocumentException(MessageLocalization.getComposedMessage("content.was.already.written.to.the.output", new Object[0]));
/* 290 */     this.stamper.setEncryption(userPassword, ownerPassword, permissions, encryptionType);
/*     */   }
/*     */ 
/*     */   public void setEncryption(boolean strength, String userPassword, String ownerPassword, int permissions)
/*     */     throws DocumentException
/*     */   {
/* 307 */     setEncryption(DocWriter.getISOBytes(userPassword), DocWriter.getISOBytes(ownerPassword), permissions, strength);
/*     */   }
/*     */ 
/*     */   public void setEncryption(int encryptionType, String userPassword, String ownerPassword, int permissions)
/*     */     throws DocumentException
/*     */   {
/* 325 */     setEncryption(DocWriter.getISOBytes(userPassword), DocWriter.getISOBytes(ownerPassword), permissions, encryptionType);
/*     */   }
/*     */ 
/*     */   public void setEncryption(Certificate[] certs, int[] permissions, int encryptionType)
/*     */     throws DocumentException
/*     */   {
/* 342 */     if (this.stamper.isAppend())
/* 343 */       throw new DocumentException(MessageLocalization.getComposedMessage("append.mode.does.not.support.changing.the.encryption.status", new Object[0]));
/* 344 */     if (this.stamper.isContentWritten())
/* 345 */       throw new DocumentException(MessageLocalization.getComposedMessage("content.was.already.written.to.the.output", new Object[0]));
/* 346 */     this.stamper.setEncryption(certs, permissions, encryptionType);
/*     */   }
/*     */ 
/*     */   public PdfImportedPage getImportedPage(PdfReader reader, int pageNumber)
/*     */   {
/* 356 */     return this.stamper.getImportedPage(reader, pageNumber);
/*     */   }
/*     */ 
/*     */   public PdfWriter getWriter()
/*     */   {
/* 363 */     return this.stamper;
/*     */   }
/*     */ 
/*     */   public PdfReader getReader()
/*     */   {
/* 370 */     return this.stamper.reader;
/*     */   }
/*     */ 
/*     */   public AcroFields getAcroFields()
/*     */   {
/* 378 */     return this.stamper.getAcroFields();
/*     */   }
/*     */ 
/*     */   public void setFormFlattening(boolean flat)
/*     */   {
/* 387 */     this.stamper.setFormFlattening(flat);
/*     */   }
/*     */ 
/*     */   public void setFreeTextFlattening(boolean flat)
/*     */   {
/* 395 */     this.stamper.setFreeTextFlattening(flat);
/*     */   }
/*     */ 
/*     */   public void addAnnotation(PdfAnnotation annot, int page)
/*     */   {
/* 405 */     this.stamper.addAnnotation(annot, page);
/*     */   }
/*     */ 
/*     */   public PdfFormField addSignature(String name, int page, float llx, float lly, float urx, float ury)
/*     */   {
/* 420 */     PdfAcroForm acroForm = this.stamper.getAcroForm();
/* 421 */     PdfFormField signature = PdfFormField.createSignature(this.stamper);
/* 422 */     acroForm.setSignatureParams(signature, name, llx, lly, urx, ury);
/* 423 */     acroForm.drawSignatureAppearences(signature, llx, lly, urx, ury);
/* 424 */     addAnnotation(signature, page);
/* 425 */     return signature;
/*     */   }
/*     */ 
/*     */   public void addComments(FdfReader fdf)
/*     */     throws IOException
/*     */   {
/* 434 */     this.stamper.addComments(fdf);
/*     */   }
/*     */ 
/*     */   public void setOutlines(List<HashMap<String, Object>> outlines)
/*     */   {
/* 443 */     this.stamper.setOutlines(outlines);
/*     */   }
/*     */ 
/*     */   public void setThumbnail(Image image, int page)
/*     */     throws PdfException, DocumentException
/*     */   {
/* 454 */     this.stamper.setThumbnail(image, page);
/*     */   }
/*     */ 
/*     */   public boolean partialFormFlattening(String name)
/*     */   {
/* 468 */     return this.stamper.partialFormFlattening(name);
/*     */   }
/*     */ 
/*     */   public void addJavaScript(String js)
/*     */   {
/* 476 */     this.stamper.addJavaScript(js, !PdfEncodings.isPdfDocEncoding(js));
/*     */   }
/*     */ 
/*     */   public void addJavaScript(String name, String js)
/*     */   {
/* 485 */     this.stamper.addJavaScript(name, PdfAction.javaScript(js, this.stamper, !PdfEncodings.isPdfDocEncoding(js)));
/*     */   }
/*     */ 
/*     */   public void addFileAttachment(String description, byte[] fileStore, String file, String fileDisplay)
/*     */     throws IOException
/*     */   {
/* 498 */     addFileAttachment(description, PdfFileSpecification.fileEmbedded(this.stamper, file, fileDisplay, fileStore));
/*     */   }
/*     */ 
/*     */   public void addFileAttachment(String description, PdfFileSpecification fs)
/*     */     throws IOException
/*     */   {
/* 507 */     this.stamper.addFileAttachment(description, fs);
/*     */   }
/*     */ 
/*     */   public void makePackage(PdfName initialView)
/*     */   {
/* 524 */     PdfCollection collection = new PdfCollection(0);
/* 525 */     collection.put(PdfName.VIEW, initialView);
/* 526 */     this.stamper.makePackage(collection);
/*     */   }
/*     */ 
/*     */   public void makePackage(PdfCollection collection)
/*     */   {
/* 534 */     this.stamper.makePackage(collection);
/*     */   }
/*     */ 
/*     */   public void setViewerPreferences(int preferences)
/*     */   {
/* 543 */     this.stamper.setViewerPreferences(preferences);
/*     */   }
/*     */ 
/*     */   public void addViewerPreference(PdfName key, PdfObject value)
/*     */   {
/* 553 */     this.stamper.addViewerPreference(key, value);
/*     */   }
/*     */ 
/*     */   public void setXmpMetadata(byte[] xmp)
/*     */   {
/* 562 */     this.stamper.setXmpMetadata(xmp);
/*     */   }
/*     */ 
/*     */   public void createXmpMetadata() {
/* 566 */     this.stamper.createXmpMetadata();
/*     */   }
/*     */ 
/*     */   public XmpWriter getXmpWriter() {
/* 570 */     return this.stamper.getXmpWriter();
/*     */   }
/*     */ 
/*     */   public boolean isFullCompression()
/*     */   {
/* 578 */     return this.stamper.isFullCompression();
/*     */   }
/*     */ 
/*     */   public void setFullCompression()
/*     */     throws DocumentException
/*     */   {
/* 586 */     if (this.stamper.isAppend())
/* 587 */       return;
/* 588 */     this.stamper.fullCompression = true;
/* 589 */     this.stamper.setAtLeastPdfVersion('5');
/*     */   }
/*     */ 
/*     */   public void setPageAction(PdfName actionType, PdfAction action, int page)
/*     */     throws PdfException
/*     */   {
/* 601 */     this.stamper.setPageAction(actionType, action, page);
/*     */   }
/*     */ 
/*     */   public void setDuration(int seconds, int page)
/*     */   {
/* 610 */     this.stamper.setDuration(seconds, page);
/*     */   }
/*     */ 
/*     */   public void setTransition(PdfTransition transition, int page)
/*     */   {
/* 619 */     this.stamper.setTransition(transition, page);
/*     */   }
/*     */ 
/*     */   public static PdfStamper createSignature(PdfReader reader, OutputStream os, char pdfVersion, File tempFile, boolean append)
/*     */     throws DocumentException, IOException
/*     */   {
/*     */     PdfStamper stp;
/* 663 */     if (tempFile == null) {
/* 664 */       ByteBuffer bout = new ByteBuffer();
/* 665 */       PdfStamper stp = new PdfStamper(reader, bout, pdfVersion, append);
/* 666 */       stp.sigApp = new PdfSignatureAppearance(stp.stamper);
/* 667 */       stp.sigApp.setSigout(bout);
/*     */     }
/*     */     else {
/* 670 */       if (tempFile.isDirectory())
/* 671 */         tempFile = File.createTempFile("pdf", null, tempFile);
/* 672 */       FileOutputStream fout = new FileOutputStream(tempFile);
/* 673 */       stp = new PdfStamper(reader, fout, pdfVersion, append);
/* 674 */       stp.sigApp = new PdfSignatureAppearance(stp.stamper);
/* 675 */       stp.sigApp.setTempFile(tempFile);
/*     */     }
/* 677 */     stp.sigApp.setOriginalout(os);
/* 678 */     stp.sigApp.setStamper(stp);
/* 679 */     stp.hasSignature = true;
/* 680 */     PdfDictionary catalog = reader.getCatalog();
/* 681 */     PdfDictionary acroForm = (PdfDictionary)PdfReader.getPdfObject(catalog.get(PdfName.ACROFORM), catalog);
/* 682 */     if (acroForm != null) {
/* 683 */       acroForm.remove(PdfName.NEEDAPPEARANCES);
/* 684 */       stp.stamper.markUsed(acroForm);
/*     */     }
/* 686 */     return stp;
/*     */   }
/*     */ 
/*     */   public static PdfStamper createSignature(PdfReader reader, OutputStream os, char pdfVersion)
/*     */     throws DocumentException, IOException
/*     */   {
/* 723 */     return createSignature(reader, os, pdfVersion, null, false);
/*     */   }
/*     */ 
/*     */   public static PdfStamper createSignature(PdfReader reader, OutputStream os, char pdfVersion, File tempFile)
/*     */     throws DocumentException, IOException
/*     */   {
/* 762 */     return createSignature(reader, os, pdfVersion, tempFile, false);
/*     */   }
/*     */ 
/*     */   public static PdfStamper createXmlSignature(PdfReader reader, OutputStream os) throws IOException, DocumentException {
/* 766 */     PdfStamper stp = new PdfStamper(reader, os);
/* 767 */     stp.sigXmlApp = new XmlSignatureAppearance(stp.stamper);
/*     */ 
/* 770 */     stp.sigXmlApp.setStamper(stp);
/*     */ 
/* 772 */     return stp;
/*     */   }
/*     */ 
/*     */   public Map<String, PdfLayer> getPdfLayers()
/*     */   {
/* 782 */     return this.stamper.getPdfLayers();
/*     */   }
/*     */ 
/*     */   public void markUsed(PdfObject obj) {
/* 786 */     this.stamper.markUsed(obj);
/*     */   }
/*     */ 
/*     */   public LtvVerification getLtvVerification() {
/* 790 */     if (this.verification == null)
/* 791 */       this.verification = new LtvVerification(this);
/* 792 */     return this.verification;
/*     */   }
/*     */ 
/*     */   void mergeVerification() throws IOException {
/* 796 */     if (this.verification == null)
/* 797 */       return;
/* 798 */     this.verification.merge();
/*     */   }
/*     */ 
/*     */   protected PdfStamper()
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfStamper
 * JD-Core Version:    0.6.2
 */