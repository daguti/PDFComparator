/*    */ package org.apache.pdfbox.exceptions;
/*    */ 
/*    */ public class SignatureException extends Exception
/*    */ {
/*    */   public static final int WRONG_PASSWORD = 1;
/*    */   public static final int UNSUPPORTED_OPERATION = 2;
/*    */   public static final int CERT_PATH_CHECK_INVALID = 3;
/*    */   public static final int NO_SUCH_ALGORITHM = 4;
/*    */   public static final int INVALID_PAGE_FOR_SIGNATURE = 5;
/*    */   public static final int VISUAL_SIGNATURE_INVALID = 6;
/*    */   private int no;
/*    */ 
/*    */   public SignatureException(String msg)
/*    */   {
/* 50 */     super(msg);
/*    */   }
/*    */ 
/*    */   public SignatureException(int errno, String msg)
/*    */   {
/* 61 */     super(msg);
/* 62 */     this.no = errno;
/*    */   }
/*    */ 
/*    */   public SignatureException(Throwable e)
/*    */   {
/* 72 */     super(e);
/*    */   }
/*    */ 
/*    */   public SignatureException(int errno, Throwable e)
/*    */   {
/* 83 */     super(e);
/*    */   }
/*    */ 
/*    */   public int getErrNo()
/*    */   {
/* 93 */     return this.no;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.exceptions.SignatureException
 * JD-Core Version:    0.6.2
 */