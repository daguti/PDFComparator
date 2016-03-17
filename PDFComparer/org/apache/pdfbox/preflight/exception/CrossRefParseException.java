/*    */ package org.apache.pdfbox.preflight.exception;
/*    */ 
/*    */ import org.apache.pdfbox.preflight.javacc.ParseException;
/*    */ 
/*    */ public class CrossRefParseException extends PdfParseException
/*    */ {
/*    */   public CrossRefParseException(ParseException e)
/*    */   {
/* 42 */     super(e);
/*    */   }
/*    */ 
/*    */   public CrossRefParseException(String message, String code)
/*    */   {
/* 52 */     super(message, code);
/*    */   }
/*    */ 
/*    */   public CrossRefParseException(String message)
/*    */   {
/* 62 */     super(message);
/*    */   }
/*    */ 
/*    */   public String getErrorCode()
/*    */   {
/* 78 */     return "1.3";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.exception.CrossRefParseException
 * JD-Core Version:    0.6.2
 */