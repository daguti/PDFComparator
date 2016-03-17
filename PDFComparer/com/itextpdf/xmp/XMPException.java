/*    */ package com.itextpdf.xmp;
/*    */ 
/*    */ public class XMPException extends Exception
/*    */ {
/*    */   private int errorCode;
/*    */ 
/*    */   public XMPException(String message, int errorCode)
/*    */   {
/* 51 */     super(message);
/* 52 */     this.errorCode = errorCode;
/*    */   }
/*    */ 
/*    */   public XMPException(String message, int errorCode, Throwable t)
/*    */   {
/* 64 */     super(message, t);
/* 65 */     this.errorCode = errorCode;
/*    */   }
/*    */ 
/*    */   public int getErrorCode()
/*    */   {
/* 74 */     return this.errorCode;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.xmp.XMPException
 * JD-Core Version:    0.6.2
 */