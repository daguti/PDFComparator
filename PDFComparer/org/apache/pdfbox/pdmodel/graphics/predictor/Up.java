/*    */ package org.apache.pdfbox.pdmodel.graphics.predictor;
/*    */ 
/*    */ public class Up extends PredictorAlgorithm
/*    */ {
/*    */   public void encodeLine(byte[] src, byte[] dest, int srcDy, int srcOffset, int destDy, int destOffset)
/*    */   {
/* 37 */     int bpl = getWidth() * getBpp();
/*    */ 
/* 39 */     if (srcOffset - srcDy < 0)
/*    */     {
/* 41 */       if (0 < getHeight())
/*    */       {
/* 43 */         for (int x = 0; x < bpl; x++)
/*    */         {
/* 45 */           dest[(destOffset + x)] = src[(srcOffset + x)];
/*    */         }
/*    */       }
/*    */     }
/*    */     else
/*    */     {
/* 51 */       for (int x = 0; x < bpl; x++)
/*    */       {
/* 53 */         dest[(destOffset + x)] = ((byte)(src[(srcOffset + x)] - src[(srcOffset + x - srcDy)]));
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public void decodeLine(byte[] src, byte[] dest, int srcDy, int srcOffset, int destDy, int destOffset)
/*    */   {
/* 66 */     int bpl = getWidth() * getBpp();
/* 67 */     if (destOffset - destDy < 0)
/*    */     {
/* 69 */       if (0 < getHeight())
/*    */       {
/* 71 */         for (int x = 0; x < bpl; x++)
/*    */         {
/* 73 */           dest[(destOffset + x)] = src[(srcOffset + x)];
/*    */         }
/*    */       }
/*    */     }
/*    */     else
/*    */     {
/* 79 */       for (int x = 0; x < bpl; x++)
/*    */       {
/* 81 */         dest[(destOffset + x)] = ((byte)(src[(srcOffset + x)] + dest[(destOffset + x - destDy)]));
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.predictor.Up
 * JD-Core Version:    0.6.2
 */