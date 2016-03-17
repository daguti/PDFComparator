/*    */ package org.apache.pdfbox.exceptions;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class WrappedIOException extends IOException
/*    */ {
/*    */   public WrappedIOException(Throwable e)
/*    */   {
/* 36 */     initCause(e);
/*    */   }
/*    */ 
/*    */   public WrappedIOException(String message, Throwable e)
/*    */   {
/* 47 */     super(message);
/* 48 */     initCause(e);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.exceptions.WrappedIOException
 * JD-Core Version:    0.6.2
 */