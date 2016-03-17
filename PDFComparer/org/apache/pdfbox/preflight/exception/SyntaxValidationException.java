/*    */ package org.apache.pdfbox.preflight.exception;
/*    */ 
/*    */ import org.apache.pdfbox.preflight.ValidationResult;
/*    */ 
/*    */ public class SyntaxValidationException extends ValidationException
/*    */ {
/*    */   private final ValidationResult result;
/*    */ 
/*    */   public SyntaxValidationException(String message, Throwable cause, ValidationResult result)
/*    */   {
/* 33 */     super(message, cause);
/* 34 */     this.result = result;
/*    */   }
/*    */ 
/*    */   public SyntaxValidationException(String message, ValidationResult result)
/*    */   {
/* 39 */     super(message);
/* 40 */     this.result = result;
/*    */   }
/*    */ 
/*    */   public SyntaxValidationException(Throwable cause, ValidationResult result)
/*    */   {
/* 45 */     super(cause);
/* 46 */     this.result = result;
/*    */   }
/*    */ 
/*    */   public ValidationResult getResult()
/*    */   {
/* 51 */     return this.result;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.exception.SyntaxValidationException
 * JD-Core Version:    0.6.2
 */