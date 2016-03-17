/*    */ package org.apache.xmpbox.xml;
/*    */ 
/*    */ public class XmpParsingException extends Exception
/*    */ {
/*    */   private ErrorType errorType;
/*    */   private static final long serialVersionUID = -8843096358184702908L;
/*    */ 
/*    */   public XmpParsingException(ErrorType error, String message, Throwable cause)
/*    */   {
/* 60 */     super(message, cause);
/* 61 */     this.errorType = error;
/*    */   }
/*    */ 
/*    */   public XmpParsingException(ErrorType error, String message)
/*    */   {
/* 72 */     super(message);
/* 73 */     this.errorType = error;
/*    */   }
/*    */ 
/*    */   public ErrorType getErrorType()
/*    */   {
/* 78 */     return this.errorType;
/*    */   }
/*    */ 
/*    */   public static enum ErrorType
/*    */   {
/* 35 */     Undefined, Configuration, XpacketBadStart, XpacketBadEnd, NoRootElement, NoSchema, 
/*    */ 
/* 37 */     InvalidPdfaSchema, NoType, 
/* 38 */     InvalidType, Format, 
/* 39 */     NoValueType, RequiredProperty, InvalidPrefix;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.xml.XmpParsingException
 * JD-Core Version:    0.6.2
 */