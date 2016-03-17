/*     */ package com.itextpdf.text.pdf.security;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ 
/*     */ public class CertificateVerifier
/*     */ {
/*     */   protected CertificateVerifier verifier;
/*  66 */   protected boolean onlineCheckingAllowed = true;
/*     */ 
/*     */   public CertificateVerifier(CertificateVerifier verifier)
/*     */   {
/*  73 */     this.verifier = verifier;
/*     */   }
/*     */ 
/*     */   public void setOnlineCheckingAllowed(boolean onlineCheckingAllowed)
/*     */   {
/*  81 */     this.onlineCheckingAllowed = onlineCheckingAllowed;
/*     */   }
/*     */ 
/*     */   public List<VerificationOK> verify(X509Certificate signCert, X509Certificate issuerCert, Date signDate)
/*     */     throws GeneralSecurityException, IOException
/*     */   {
/*  98 */     if (signDate != null) {
/*  99 */       signCert.checkValidity(signDate);
/*     */     }
/* 101 */     if (issuerCert != null) {
/* 102 */       signCert.verify(issuerCert.getPublicKey());
/*     */     }
/*     */     else
/*     */     {
/* 106 */       signCert.verify(signCert.getPublicKey());
/*     */     }
/* 108 */     List result = new ArrayList();
/* 109 */     if (this.verifier != null)
/* 110 */       result.addAll(this.verifier.verify(signCert, issuerCert, signDate));
/* 111 */     return result;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.CertificateVerifier
 * JD-Core Version:    0.6.2
 */