/*    */ package org.apache.pdfbox.preflight.exception;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import org.apache.pdfbox.preflight.javacc.ParseException;
/*    */ 
/*    */ public class TrailerParseException extends PdfParseException
/*    */ {
/*    */   public TrailerParseException(ParseException e)
/*    */   {
/* 42 */     super(e);
/*    */   }
/*    */ 
/*    */   public TrailerParseException(String message, String code)
/*    */   {
/* 52 */     super(message, code);
/*    */   }
/*    */ 
/*    */   public TrailerParseException(String message)
/*    */   {
/* 62 */     super(message);
/*    */   }
/*    */ 
/*    */   public String getErrorCode()
/*    */   {
/* 73 */     if (!this.isTokenMgrError)
/*    */     {
/* 76 */       System.out.println("## Trailer ParseError");
/*    */     }
/*    */ 
/* 80 */     return "1.4";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.exception.TrailerParseException
 * JD-Core Version:    0.6.2
 */