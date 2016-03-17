/*    */ package com.itextpdf.text.pdf;
/*    */ 
/*    */ import java.security.cert.Certificate;
/*    */ 
/*    */ public class PdfPublicKeyRecipient
/*    */ {
/* 51 */   private Certificate certificate = null;
/*    */ 
/* 53 */   private int permission = 0;
/*    */ 
/* 55 */   protected byte[] cms = null;
/*    */ 
/*    */   public PdfPublicKeyRecipient(Certificate certificate, int permission)
/*    */   {
/* 59 */     this.certificate = certificate;
/* 60 */     this.permission = permission;
/*    */   }
/*    */ 
/*    */   public Certificate getCertificate() {
/* 64 */     return this.certificate;
/*    */   }
/*    */ 
/*    */   public int getPermission() {
/* 68 */     return this.permission;
/*    */   }
/*    */ 
/*    */   protected void setCms(byte[] cms) {
/* 72 */     this.cms = cms;
/*    */   }
/*    */ 
/*    */   protected byte[] getCms() {
/* 76 */     return this.cms;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfPublicKeyRecipient
 * JD-Core Version:    0.6.2
 */