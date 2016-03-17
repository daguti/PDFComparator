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
/*     */ @Deprecated
/*     */ public class PdfCopyFields
/*     */   implements PdfViewerPreferences, PdfEncryptionSettings
/*     */ {
/*     */   private PdfCopyFieldsImp fc;
/*     */ 
/*     */   public PdfCopyFields(OutputStream os)
/*     */     throws DocumentException
/*     */   {
/*  76 */     this.fc = new PdfCopyFieldsImp(os);
/*     */   }
/*     */ 
/*     */   public PdfCopyFields(OutputStream os, char pdfVersion)
/*     */     throws DocumentException
/*     */   {
/*  86 */     this.fc = new PdfCopyFieldsImp(os, pdfVersion);
/*     */   }
/*     */ 
/*     */   public void addDocument(PdfReader reader)
/*     */     throws DocumentException, IOException
/*     */   {
/*  95 */     this.fc.addDocument(reader);
/*     */   }
/*     */ 
/*     */   public void addDocument(PdfReader reader, List<Integer> pagesToKeep)
/*     */     throws DocumentException, IOException
/*     */   {
/* 107 */     this.fc.addDocument(reader, pagesToKeep);
/*     */   }
/*     */ 
/*     */   public void addDocument(PdfReader reader, String ranges)
/*     */     throws DocumentException, IOException
/*     */   {
/* 119 */     this.fc.addDocument(reader, SequenceList.expand(ranges, reader.getNumberOfPages()));
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
/* 207 */     this.fc.setFullCompression();
/*     */   }
/*     */ 
/*     */   public void setEncryption(byte[] userPassword, byte[] ownerPassword, int permissions, int encryptionType)
/*     */     throws DocumentException
/*     */   {
/* 214 */     this.fc.setEncryption(userPassword, ownerPassword, permissions, encryptionType);
/*     */   }
/*     */ 
/*     */   public void addViewerPreference(PdfName key, PdfObject value)
/*     */   {
/* 221 */     this.fc.addViewerPreference(key, value);
/*     */   }
/*     */ 
/*     */   public void setViewerPreferences(int preferences)
/*     */   {
/* 228 */     this.fc.setViewerPreferences(preferences);
/*     */   }
/*     */ 
/*     */   public void setEncryption(Certificate[] certs, int[] permissions, int encryptionType)
/*     */     throws DocumentException
/*     */   {
/* 235 */     this.fc.setEncryption(certs, permissions, encryptionType);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfCopyFields
 * JD-Core Version:    0.6.2
 */