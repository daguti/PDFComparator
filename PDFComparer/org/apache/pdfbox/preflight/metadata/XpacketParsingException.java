/*    */ package org.apache.pdfbox.preflight.metadata;
/*    */ 
/*    */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*    */ 
/*    */ public class XpacketParsingException extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected ValidationResult.ValidationError error;
/*    */ 
/*    */   public XpacketParsingException(String message, Throwable cause)
/*    */   {
/* 50 */     super(message, cause);
/*    */   }
/*    */ 
/*    */   public XpacketParsingException(String message)
/*    */   {
/* 61 */     super(message);
/*    */   }
/*    */ 
/*    */   public XpacketParsingException(String message, ValidationResult.ValidationError error)
/*    */   {
/* 66 */     super(message);
/* 67 */     this.error = error;
/*    */   }
/*    */ 
/*    */   public ValidationResult.ValidationError getError()
/*    */   {
/* 72 */     return this.error;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.metadata.XpacketParsingException
 * JD-Core Version:    0.6.2
 */