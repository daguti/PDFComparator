/*    */ package com.itextpdf.text.exceptions;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class InvalidPdfException extends IOException
/*    */ {
/*    */   private static final long serialVersionUID = -2319614911517026938L;
/*    */   private final Throwable cause;
/*    */ 
/*    */   public InvalidPdfException(String message)
/*    */   {
/* 65 */     this(message, null);
/*    */   }
/*    */ 
/*    */   public InvalidPdfException(String message, Throwable cause)
/*    */   {
/* 74 */     super(message);
/* 75 */     this.cause = cause;
/*    */   }
/*    */ 
/*    */   public Throwable getCause()
/*    */   {
/* 84 */     return this.cause;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.exceptions.InvalidPdfException
 * JD-Core Version:    0.6.2
 */