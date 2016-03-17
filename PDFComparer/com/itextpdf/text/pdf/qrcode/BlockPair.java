/*    */ package com.itextpdf.text.pdf.qrcode;
/*    */ 
/*    */ final class BlockPair
/*    */ {
/*    */   private final ByteArray dataBytes;
/*    */   private final ByteArray errorCorrectionBytes;
/*    */ 
/*    */   BlockPair(ByteArray data, ByteArray errorCorrection)
/*    */   {
/* 27 */     this.dataBytes = data;
/* 28 */     this.errorCorrectionBytes = errorCorrection;
/*    */   }
/*    */ 
/*    */   public ByteArray getDataBytes() {
/* 32 */     return this.dataBytes;
/*    */   }
/*    */ 
/*    */   public ByteArray getErrorCorrectionBytes() {
/* 36 */     return this.errorCorrectionBytes;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.qrcode.BlockPair
 * JD-Core Version:    0.6.2
 */