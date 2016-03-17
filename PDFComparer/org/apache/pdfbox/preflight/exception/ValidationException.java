/*    */ package org.apache.pdfbox.preflight.exception;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class ValidationException extends IOException
/*    */ {
/*    */   private static final long serialVersionUID = -1616141241190424669L;
/*    */ 
/*    */   public ValidationException(String message, Throwable cause)
/*    */   {
/* 36 */     super(message);
/* 37 */     initCause(cause);
/*    */   }
/*    */ 
/*    */   public ValidationException(String message)
/*    */   {
/* 42 */     super(message);
/*    */   }
/*    */ 
/*    */   public ValidationException(Throwable cause)
/*    */   {
/* 48 */     initCause(cause);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.exception.ValidationException
 * JD-Core Version:    0.6.2
 */