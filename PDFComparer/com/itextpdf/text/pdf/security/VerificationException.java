/*    */ package com.itextpdf.text.pdf.security;
/*    */ 
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.security.Principal;
/*    */ import java.security.cert.Certificate;
/*    */ import java.security.cert.X509Certificate;
/*    */ 
/*    */ public class VerificationException extends GeneralSecurityException
/*    */ {
/*    */   private static final long serialVersionUID = 2978604513926438256L;
/*    */ 
/*    */   public VerificationException(Certificate cert, String message)
/*    */   {
/* 63 */     super(String.format("Certificate %s failed: %s", new Object[] { cert == null ? "Unknown" : ((X509Certificate)cert).getSubjectDN().getName(), message }));
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.VerificationException
 * JD-Core Version:    0.6.2
 */