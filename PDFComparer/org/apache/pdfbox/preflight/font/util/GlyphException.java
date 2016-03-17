/*    */ package org.apache.pdfbox.preflight.font.util;
/*    */ 
/*    */ public class GlyphException extends Exception
/*    */ {
/*    */   private String errorCode;
/*    */   private int invalidCid;
/*    */ 
/*    */   public GlyphException(String code, int cid)
/*    */   {
/* 32 */     this.errorCode = code;
/* 33 */     this.invalidCid = cid;
/*    */   }
/*    */ 
/*    */   public GlyphException(String code, int cid, String message)
/*    */   {
/* 38 */     super(message);
/* 39 */     this.errorCode = code;
/* 40 */     this.invalidCid = cid;
/*    */   }
/*    */ 
/*    */   public String getErrorCode()
/*    */   {
/* 45 */     return this.errorCode;
/*    */   }
/*    */ 
/*    */   public int getInvalidCid()
/*    */   {
/* 50 */     return this.invalidCid;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.util.GlyphException
 * JD-Core Version:    0.6.2
 */