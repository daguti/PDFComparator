/*    */ package com.itextpdf.text.exceptions;
/*    */ 
/*    */ public class InvalidImageException extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = -1319471492541702697L;
/*    */   private final Throwable cause;
/*    */ 
/*    */   public InvalidImageException(String message)
/*    */   {
/* 63 */     this(message, null);
/*    */   }
/*    */ 
/*    */   public InvalidImageException(String message, Throwable cause)
/*    */   {
/* 72 */     super(message);
/* 73 */     this.cause = cause;
/*    */   }
/*    */ 
/*    */   public Throwable getCause()
/*    */   {
/* 82 */     return this.cause;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.exceptions.InvalidImageException
 * JD-Core Version:    0.6.2
 */