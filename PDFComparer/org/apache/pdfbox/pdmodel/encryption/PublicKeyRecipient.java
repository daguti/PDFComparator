/*    */ package org.apache.pdfbox.pdmodel.encryption;
/*    */ 
/*    */ import java.security.cert.X509Certificate;
/*    */ 
/*    */ public class PublicKeyRecipient
/*    */ {
/*    */   private X509Certificate x509;
/*    */   private AccessPermission permission;
/*    */ 
/*    */   public X509Certificate getX509()
/*    */   {
/* 43 */     return this.x509;
/*    */   }
/*    */ 
/*    */   public void setX509(X509Certificate aX509)
/*    */   {
/* 53 */     this.x509 = aX509;
/*    */   }
/*    */ 
/*    */   public AccessPermission getPermission()
/*    */   {
/* 63 */     return this.permission;
/*    */   }
/*    */ 
/*    */   public void setPermission(AccessPermission permissions)
/*    */   {
/* 73 */     this.permission = permissions;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.encryption.PublicKeyRecipient
 * JD-Core Version:    0.6.2
 */