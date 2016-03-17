/*    */ package org.apache.pdfbox.pdmodel.encryption;
/*    */ 
/*    */ public abstract class ProtectionPolicy
/*    */ {
/*    */   private static final int DEFAULT_KEY_LENGTH = 40;
/* 36 */   private int encryptionKeyLength = 40;
/*    */ 
/*    */   public void setEncryptionKeyLength(int l)
/*    */   {
/* 48 */     if ((l != 40) && (l != 128))
/*    */     {
/* 50 */       throw new RuntimeException("Invalid key length '" + l + "' value must be 40 or 128!");
/*    */     }
/* 52 */     this.encryptionKeyLength = l;
/*    */   }
/*    */ 
/*    */   public int getEncryptionKeyLength()
/*    */   {
/* 63 */     return this.encryptionKeyLength;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.encryption.ProtectionPolicy
 * JD-Core Version:    0.6.2
 */