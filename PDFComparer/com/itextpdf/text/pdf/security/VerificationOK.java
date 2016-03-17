/*    */ package com.itextpdf.text.pdf.security;
/*    */ 
/*    */ import java.security.Principal;
/*    */ import java.security.cert.X509Certificate;
/*    */ 
/*    */ public class VerificationOK
/*    */ {
/*    */   protected X509Certificate certificate;
/*    */   protected Class<? extends CertificateVerifier> verifierClass;
/*    */   protected String message;
/*    */ 
/*    */   public VerificationOK(X509Certificate certificate, Class<? extends CertificateVerifier> verifierClass, String message)
/*    */   {
/* 72 */     this.certificate = certificate;
/* 73 */     this.verifierClass = verifierClass;
/* 74 */     this.message = message;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 82 */     StringBuilder sb = new StringBuilder();
/* 83 */     if (this.certificate != null) {
/* 84 */       sb.append(this.certificate.getSubjectDN().getName());
/* 85 */       sb.append(" verified with ");
/*    */     }
/* 87 */     sb.append(this.verifierClass.getName());
/* 88 */     sb.append(": ");
/* 89 */     sb.append(this.message);
/* 90 */     return sb.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.VerificationOK
 * JD-Core Version:    0.6.2
 */