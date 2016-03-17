/*    */ package org.apache.pdfbox.pdmodel.graphics.predictor;
/*    */ 
/*    */ public class Sub extends PredictorAlgorithm
/*    */ {
/*    */   public void encodeLine(byte[] src, byte[] dest, int srcDy, int srcOffset, int destDy, int destOffset)
/*    */   {
/* 37 */     int bpl = getWidth() * getBpp();
/* 38 */     int bpp = getBpp();
/*    */ 
/* 40 */     for (int x = 0; (x < bpl) && (x < bpp); x++)
/*    */     {
/* 42 */       dest[(x + destOffset)] = src[(x + srcOffset)];
/*    */     }
/*    */ 
/* 45 */     for (int x = getBpp(); x < bpl; x++)
/*    */     {
/* 47 */       dest[(x + destOffset)] = ((byte)(src[(x + srcOffset)] - src[(x + srcOffset - bpp)]));
/*    */     }
/*    */   }
/*    */ 
/*    */   public void decodeLine(byte[] src, byte[] dest, int srcDy, int srcOffset, int destDy, int destOffset)
/*    */   {
/* 58 */     int bpl = getWidth() * getBpp();
/* 59 */     int bpp = getBpp();
/*    */ 
/* 61 */     for (int x = 0; (x < bpl) && (x < bpp); x++)
/*    */     {
/* 63 */       dest[(x + destOffset)] = src[(x + srcOffset)];
/*    */     }
/*    */ 
/* 66 */     for (int x = getBpp(); x < bpl; x++)
/*    */     {
/* 68 */       dest[(x + destOffset)] = ((byte)(src[(x + srcOffset)] + dest[(x + destOffset - bpp)]));
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.predictor.Sub
 * JD-Core Version:    0.6.2
 */