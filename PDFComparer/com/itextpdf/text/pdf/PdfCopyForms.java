/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.DocWriter;
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.pdf.interfaces.PdfEncryptionSettings;
/*     */ import com.itextpdf.text.pdf.interfaces.PdfViewerPreferences;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.security.cert.Certificate;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ 
/*     */ /** @deprecated */
/*     */ public class PdfCopyForms
/*     */   implements PdfViewerPreferences, PdfEncryptionSettings
/*     */ {
/*     */   private PdfCopyFormsImp fc;
/*     */ 
/*     */   public PdfCopyForms(OutputStream os)
/*     */     throws DocumentException
/*     */   {
/*  77 */     this.fc = new PdfCopyFormsImp(os);
/*     */   }
/*     */ 
/*     */   public void addDocument(PdfReader reader)
/*     */     throws DocumentException, IOException
/*     */   {
/*  86 */     this.fc.addDocument(reader);
/*     */   }
/*     */ 
/*     */   public void addDocument(PdfReader reader, List<Integer> pagesToKeep)
/*     */     throws DocumentException, IOException
/*     */   {
/*  98 */     this.fc.addDocument(reader, pagesToKeep);
/*     */   }
/*     */ 
/*     */   public void addDocument(PdfReader reader, String ranges)
/*     */     throws DocumentException, IOException
/*     */   {
/* 110 */     this.fc.addDocument(reader, SequenceList.expand(ranges, reader.getNumberOfPages()));
/*     */   }
/*     */ 
/*     */   public void copyDocumentFields(PdfReader reader)
/*     */     throws DocumentException
/*     */   {
/* 119 */     this.fc.copyDocumentFields(reader);
/*     */   }
/*     */ 
/*     */   public void setEncryption(byte[] userPassword, byte[] ownerPassword, int permissions, boolean strength128Bits)
/*     */     throws DocumentException
/*     */   {
/* 135 */     this.fc.setEncryption(userPassword, ownerPassword, permissions, strength128Bits ? 1 : 0);
/*     */   }
/*     */ 
/*     */   public void setEncryption(boolean strength, String userPassword, String ownerPassword, int permissions)
/*     */     throws DocumentException
/*     */   {
/* 152 */     setEncryption(DocWriter.getISOBytes(userPassword), DocWriter.getISOBytes(ownerPassword), permissions, strength);
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/* 159 */     this.fc.close();
/*     */   }
/*     */ 
/*     */   public void open()
/*     */   {
/* 167 */     this.fc.openDoc();
/*     */   }
/*     */ 
/*     */   public void addJavaScript(String js)
/*     */   {
/* 175 */     this.fc.addJavaScript(js, !PdfEncodings.isPdfDocEncoding(js));
/*     */   }
/*     */ 
/*     */   public void setOutlines(List<HashMap<String, Object>> outlines)
/*     */   {
/* 184 */     this.fc.setOutlines(outlines);
/*     */   }
/*     */ 
/*     */   public PdfWriter getWriter()
/*     */   {
/* 191 */     return this.fc;
/*     */   }
/*     */ 
/*     */   public boolean isFullCompression()
/*     */   {
/* 199 */     return this.fc.isFullCompression();
/*     */   }
/*     */ 
/*     */   public void setFullCompression()
/*     */     throws DocumentException
/*     */   {
/* 209 */     this.fc.setFullCompression();
/*     */   }
/*     */ 
/*     */   public void setEncryption(byte[] userPassword, byte[] ownerPassword, int permissions, int encryptionType)
/*     */     throws DocumentException
/*     */   {
/* 216 */     this.fc.setEncryption(userPassword, ownerPassword, permissions, encryptionType);
/*     */   }
/*     */ 
/*     */   public void addViewerPreference(PdfName key, PdfObject value)
/*     */   {
/* 223 */     this.fc.addViewerPreference(key, value);
/*     */   }
/*     */ 
/*     */   public void setViewerPreferences(int preferences)
/*     */   {
/* 230 */     this.fc.setViewerPreferences(preferences);
/*     */   }
/*     */ 
/*     */   public void setEncryption(Certificate[] certs, int[] permissions, int encryptionType)
/*     */     throws DocumentException
/*     */   {
/* 237 */     this.fc.setEncryption(certs, permissions, encryptionType);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfCopyForms
 * JD-Core Version:    0.6.2
 */