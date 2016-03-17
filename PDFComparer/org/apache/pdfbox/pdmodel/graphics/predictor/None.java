/*    */ package org.apache.pdfbox.pdmodel.graphics.predictor;
/*    */ 
/*    */ public class None extends PredictorAlgorithm
/*    */ {
/*    */   public void encode(byte[] src, byte[] dest)
/*    */   {
/* 42 */     checkBufsiz(dest, src);
/* 43 */     System.arraycopy(src, 0, dest, 0, src.length);
/*    */   }
/*    */ 
/*    */   public void decode(byte[] src, byte[] dest)
/*    */   {
/* 57 */     System.arraycopy(src, 0, dest, 0, src.length);
/*    */   }
/*    */ 
/*    */   public void encodeLine(byte[] src, byte[] dest, int srcDy, int srcOffset, int destDy, int destOffset)
/*    */   {
/* 68 */     int bpl = getWidth() * getBpp();
/* 69 */     for (int x = 0; x < bpl; x++)
/*    */     {
/* 71 */       dest[(destOffset + x)] = src[(srcOffset + x)];
/*    */     }
/*    */   }
/*    */ 
/*    */   public void decodeLine(byte[] src, byte[] dest, int srcDy, int srcOffset, int destDy, int destOffset)
/*    */   {
/* 81 */     int bpl = getWidth() * getBpp();
/* 82 */     for (int x = 0; x < bpl; x++)
/*    */     {
/* 84 */       dest[(destOffset + x)] = src[(srcOffset + x)];
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.predictor.None
 * JD-Core Version:    0.6.2
 */