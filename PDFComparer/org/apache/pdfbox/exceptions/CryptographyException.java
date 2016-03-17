/*    */ package org.apache.pdfbox.exceptions;
/*    */ 
/*    */ public class CryptographyException extends Exception
/*    */ {
/*    */   private Exception embedded;
/*    */ 
/*    */   public CryptographyException(String msg)
/*    */   {
/* 37 */     super(msg);
/*    */   }
/*    */ 
/*    */   public CryptographyException(Exception e)
/*    */   {
/* 47 */     super(e.getMessage());
/* 48 */     setEmbedded(e);
/*    */   }
/*    */ 
/*    */   public Exception getEmbedded()
/*    */   {
/* 57 */     return this.embedded;
/*    */   }
/*    */ 
/*    */   private void setEmbedded(Exception e)
/*    */   {
/* 66 */     this.embedded = e;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.exceptions.CryptographyException
 * JD-Core Version:    0.6.2
 */