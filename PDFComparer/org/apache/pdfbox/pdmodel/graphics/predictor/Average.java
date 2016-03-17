/*    */ package org.apache.pdfbox.pdmodel.graphics.predictor;
/*    */ 
/*    */ public class Average extends PredictorAlgorithm
/*    */ {
/*    */   public void encodeLine(byte[] src, byte[] dest, int srcDy, int srcOffset, int destDy, int destOffset)
/*    */   {
/* 42 */     int bpl = getWidth() * getBpp();
/* 43 */     for (int x = 0; x < bpl; x++)
/*    */     {
/* 45 */       dest[(x + destOffset)] = ((byte)(src[(x + srcOffset)] - (leftPixel(src, srcOffset, srcDy, x) + abovePixel(src, srcOffset, srcDy, x) >>> 2)));
/*    */     }
/*    */   }
/*    */ 
/*    */   public void decodeLine(byte[] src, byte[] dest, int srcDy, int srcOffset, int destDy, int destOffset)
/*    */   {
/* 57 */     int bpl = getWidth() * getBpp();
/* 58 */     for (int x = 0; x < bpl; x++)
/*    */     {
/* 60 */       dest[(x + destOffset)] = ((byte)(src[(x + srcOffset)] + (leftPixel(dest, destOffset, destDy, x) + abovePixel(dest, destOffset, destDy, x) >>> 2)));
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.predictor.Average
 * JD-Core Version:    0.6.2
 */