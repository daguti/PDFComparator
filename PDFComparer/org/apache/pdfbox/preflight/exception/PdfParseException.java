/*    */ package org.apache.pdfbox.preflight.exception;
/*    */ 
/*    */ import org.apache.pdfbox.preflight.javacc.ParseException;
/*    */ 
/*    */ public class PdfParseException extends ParseException
/*    */ {
/* 34 */   protected boolean isTokenMgrError = false;
/* 35 */   protected String errorCode = null;
/* 36 */   protected int line = 0;
/*    */ 
/*    */   public PdfParseException(ParseException e)
/*    */   {
/* 47 */     this.currentToken = e.currentToken;
/* 48 */     this.expectedTokenSequences = e.expectedTokenSequences;
/* 49 */     this.tokenImage = e.tokenImage;
/* 50 */     initCause(e);
/* 51 */     if ((e instanceof PdfParseException))
/*    */     {
/* 53 */       this.errorCode = ((PdfParseException)e).errorCode;
/*    */     }
/*    */   }
/*    */ 
/*    */   public PdfParseException(String message)
/*    */   {
/* 65 */     this(message, null);
/*    */   }
/*    */ 
/*    */   public PdfParseException(String message, String code)
/*    */   {
/* 79 */     super(message);
/* 80 */     this.isTokenMgrError = true;
/* 81 */     int lineIndex = message.indexOf("Lexical error at line ");
/* 82 */     if (lineIndex > -1)
/*    */     {
/* 84 */       String truncMsg = message.replace("Lexical error at line ", "");
/* 85 */       String nbLine = truncMsg.substring(0, truncMsg.indexOf(","));
/* 86 */       this.line = Integer.parseInt(nbLine);
/*    */     }
/* 88 */     this.errorCode = code;
/*    */   }
/*    */ 
/*    */   public String getErrorCode()
/*    */   {
/* 98 */     return this.errorCode;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.exception.PdfParseException
 * JD-Core Version:    0.6.2
 */