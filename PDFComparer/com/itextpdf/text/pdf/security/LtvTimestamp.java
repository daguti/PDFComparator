/*     */ package com.itextpdf.text.pdf.security;
/*     */ 
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.Rectangle;
/*     */ import com.itextpdf.text.pdf.PdfDeveloperExtension;
/*     */ import com.itextpdf.text.pdf.PdfDictionary;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfSignature;
/*     */ import com.itextpdf.text.pdf.PdfSignatureAppearance;
/*     */ import com.itextpdf.text.pdf.PdfString;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.MessageDigest;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class LtvTimestamp
/*     */ {
/*     */   public static void timestamp(PdfSignatureAppearance sap, TSAClient tsa, String signatureName)
/*     */     throws IOException, DocumentException, GeneralSecurityException
/*     */   {
/*  78 */     int contentEstimated = tsa.getTokenSizeEstimate();
/*  79 */     sap.addDeveloperExtension(PdfDeveloperExtension.ESIC_1_7_EXTENSIONLEVEL5);
/*  80 */     sap.setVisibleSignature(new Rectangle(0.0F, 0.0F, 0.0F, 0.0F), 1, signatureName);
/*     */ 
/*  82 */     PdfSignature dic = new PdfSignature(PdfName.ADOBE_PPKLITE, PdfName.ETSI_RFC3161);
/*  83 */     dic.put(PdfName.TYPE, PdfName.DOCTIMESTAMP);
/*  84 */     sap.setCryptoDictionary(dic);
/*     */ 
/*  86 */     HashMap exc = new HashMap();
/*  87 */     exc.put(PdfName.CONTENTS, new Integer(contentEstimated * 2 + 2));
/*  88 */     sap.preClose(exc);
/*  89 */     InputStream data = sap.getRangeStream();
/*  90 */     MessageDigest messageDigest = tsa.getMessageDigest();
/*  91 */     byte[] buf = new byte[4096];
/*     */     int n;
/*  93 */     while ((n = data.read(buf)) > 0) {
/*  94 */       messageDigest.update(buf, 0, n);
/*  96 */     }byte[] tsImprint = messageDigest.digest();
/*     */     byte[] tsToken;
/*     */     try {
/*  99 */       tsToken = tsa.getTimeStampToken(tsImprint);
/*     */     }
/*     */     catch (Exception e) {
/* 102 */       throw new GeneralSecurityException(e);
/*     */     }
/*     */ 
/* 105 */     if (contentEstimated + 2 < tsToken.length) {
/* 106 */       throw new IOException("Not enough space");
/*     */     }
/* 108 */     byte[] paddedSig = new byte[contentEstimated];
/* 109 */     System.arraycopy(tsToken, 0, paddedSig, 0, tsToken.length);
/*     */ 
/* 111 */     PdfDictionary dic2 = new PdfDictionary();
/* 112 */     dic2.put(PdfName.CONTENTS, new PdfString(paddedSig).setHexWriting(true));
/* 113 */     sap.close(dic2);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.LtvTimestamp
 * JD-Core Version:    0.6.2
 */