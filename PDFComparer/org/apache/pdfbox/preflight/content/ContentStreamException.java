/*    */ package org.apache.pdfbox.preflight.content;
/*    */ 
/*    */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*    */ 
/*    */ public class ContentStreamException extends ValidationException
/*    */ {
/* 28 */   private String errorCode = "";
/*    */ 
/*    */   public ContentStreamException(String arg0, Throwable arg1)
/*    */   {
/* 32 */     super(arg0);
/*    */   }
/*    */ 
/*    */   public ContentStreamException(String arg0)
/*    */   {
/* 37 */     super(arg0);
/*    */   }
/*    */ 
/*    */   public ContentStreamException(Throwable arg0)
/*    */   {
/* 42 */     super(arg0.getMessage());
/*    */   }
/*    */ 
/*    */   public String getErrorCode()
/*    */   {
/* 47 */     return this.errorCode;
/*    */   }
/*    */ 
/*    */   public void setErrorCode(String errorCode)
/*    */   {
/* 52 */     this.errorCode = errorCode;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.content.ContentStreamException
 * JD-Core Version:    0.6.2
 */