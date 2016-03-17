/*    */ package org.apache.pdfbox.pdmodel.graphics.predictor;
/*    */ 
/*    */ public class Paeth extends PredictorAlgorithm
/*    */ {
/*    */   public int paethPredictor(int a, int b, int c)
/*    */   {
/* 55 */     int p = a + b - c;
/* 56 */     int pa = Math.abs(p - a);
/* 57 */     int pb = Math.abs(p - b);
/* 58 */     int pc = Math.abs(p - c);
/*    */ 
/* 61 */     if ((pa <= pb) && (pa <= pc))
/*    */     {
/* 63 */       return a;
/*    */     }
/* 65 */     if (pb <= pc)
/*    */     {
/* 67 */       return b;
/*    */     }
/*    */ 
/* 71 */     return c;
/*    */   }
/*    */ 
/*    */   public void encodeLine(byte[] src, byte[] dest, int srcDy, int srcOffset, int destDy, int destOffset)
/*    */   {
/* 81 */     int bpl = getWidth() * getBpp();
/* 82 */     for (int x = 0; x < bpl; x++)
/*    */     {
/* 84 */       dest[(x + destOffset)] = ((byte)(src[(x + srcOffset)] - paethPredictor(leftPixel(src, srcOffset, srcDy, x), abovePixel(src, srcOffset, srcDy, x), aboveLeftPixel(src, srcOffset, srcDy, x))));
/*    */     }
/*    */   }
/*    */ 
/*    */   public void decodeLine(byte[] src, byte[] dest, int srcDy, int srcOffset, int destDy, int destOffset)
/*    */   {
/* 97 */     int bpl = getWidth() * getBpp();
/* 98 */     for (int x = 0; x < bpl; x++)
/*    */     {
/* 100 */       dest[(x + destOffset)] = ((byte)(src[(x + srcOffset)] + paethPredictor(leftPixel(dest, destOffset, destDy, x), abovePixel(dest, destOffset, destDy, x), aboveLeftPixel(dest, destOffset, destDy, x))));
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.predictor.Paeth
 * JD-Core Version:    0.6.2
 */