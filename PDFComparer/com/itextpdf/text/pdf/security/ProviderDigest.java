/*    */ package com.itextpdf.text.pdf.security;
/*    */ 
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.security.MessageDigest;
/*    */ 
/*    */ public class ProviderDigest
/*    */   implements ExternalDigest
/*    */ {
/*    */   private String provider;
/*    */ 
/*    */   public ProviderDigest(String provider)
/*    */   {
/* 58 */     this.provider = provider;
/*    */   }
/*    */ 
/*    */   public MessageDigest getMessageDigest(String hashAlgorithm) throws GeneralSecurityException {
/* 62 */     return DigestAlgorithms.getMessageDigest(hashAlgorithm, this.provider);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.ProviderDigest
 * JD-Core Version:    0.6.2
 */