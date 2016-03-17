/*    */ package org.apache.pdfbox.preflight.exception;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import org.apache.pdfbox.preflight.javacc.ParseException;
/*    */ 
/*    */ public class HeaderParseException extends PdfParseException
/*    */ {
/*    */   public HeaderParseException(ParseException e)
/*    */   {
/* 42 */     super(e);
/*    */   }
/*    */ 
/*    */   public HeaderParseException(String message, String code)
/*    */   {
/* 52 */     super(message, code);
/*    */   }
/*    */ 
/*    */   public HeaderParseException(String message)
/*    */   {
/* 62 */     super(message);
/*    */   }
/*    */ 
/*    */   public String getErrorCode()
/*    */   {
/* 73 */     if (!this.isTokenMgrError)
/*    */     {
/* 75 */       System.out.println("## Header ParseError");
/*    */     }
/*    */ 
/* 79 */     return "1.1";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.exception.HeaderParseException
 * JD-Core Version:    0.6.2
 */