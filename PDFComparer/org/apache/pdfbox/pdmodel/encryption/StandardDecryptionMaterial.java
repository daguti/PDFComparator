/*    */ package org.apache.pdfbox.pdmodel.encryption;
/*    */ 
/*    */ public class StandardDecryptionMaterial extends DecryptionMaterial
/*    */ {
/* 45 */   private String password = null;
/*    */ 
/*    */   public StandardDecryptionMaterial(String pwd)
/*    */   {
/* 54 */     this.password = pwd;
/*    */   }
/*    */ 
/*    */   public String getPassword()
/*    */   {
/* 64 */     return this.password;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.encryption.StandardDecryptionMaterial
 * JD-Core Version:    0.6.2
 */