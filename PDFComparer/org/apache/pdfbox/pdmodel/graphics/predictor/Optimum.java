/*     */ package org.apache.pdfbox.pdmodel.graphics.predictor;
/*     */ 
/*     */ public class Optimum extends PredictorAlgorithm
/*     */ {
/*  84 */   PredictorAlgorithm[] filter = { new None(), new Sub(), new Up(), new Average(), new Paeth() };
/*     */ 
/*     */   public void checkBufsiz(byte[] filtered, byte[] raw)
/*     */   {
/*  35 */     if (filtered.length != (getWidth() * getBpp() + 1) * getHeight())
/*     */     {
/*  38 */       throw new IllegalArgumentException("filtered.length != (width*bpp + 1) * height, " + filtered.length + " " + (getWidth() * getBpp() + 1) * getHeight() + "w,h,bpp=" + getWidth() + "," + getHeight() + "," + getBpp());
/*     */     }
/*     */ 
/*  45 */     if (raw.length != getWidth() * getHeight() * getBpp())
/*     */     {
/*  47 */       throw new IllegalArgumentException("raw.length != width * height * bpp, raw.length=" + raw.length + " w,h,bpp=" + getWidth() + "," + getHeight() + "," + getBpp());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void encodeLine(byte[] src, byte[] dest, int srcDy, int srcOffset, int destDy, int destOffset)
/*     */   {
/*  60 */     throw new UnsupportedOperationException("encodeLine");
/*     */   }
/*     */ 
/*     */   public void decodeLine(byte[] src, byte[] dest, int srcDy, int srcOffset, int destDy, int destOffset)
/*     */   {
/*  69 */     throw new UnsupportedOperationException("decodeLine");
/*     */   }
/*     */ 
/*     */   public void encode(byte[] src, byte[] dest)
/*     */   {
/*  77 */     checkBufsiz(dest, src);
/*  78 */     throw new UnsupportedOperationException("encode");
/*     */   }
/*     */ 
/*     */   public void setBpp(int bpp)
/*     */   {
/*  92 */     super.setBpp(bpp);
/*  93 */     for (int i = 0; i < this.filter.length; i++)
/*     */     {
/*  95 */       this.filter[i].setBpp(bpp);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setHeight(int height)
/*     */   {
/* 103 */     super.setHeight(height);
/* 104 */     for (int i = 0; i < this.filter.length; i++)
/*     */     {
/* 106 */       this.filter[i].setHeight(height);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setWidth(int width)
/*     */   {
/* 115 */     super.setWidth(width);
/* 116 */     for (int i = 0; i < this.filter.length; i++)
/*     */     {
/* 118 */       this.filter[i].setWidth(width);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void decode(byte[] src, byte[] dest)
/*     */   {
/* 127 */     checkBufsiz(src, dest);
/* 128 */     int bpl = getWidth() * getBpp();
/* 129 */     int srcDy = bpl + 1;
/* 130 */     for (int y = 0; y < getHeight(); y++)
/*     */     {
/* 132 */       PredictorAlgorithm f = this.filter[src[(y * srcDy)]];
/* 133 */       int srcOffset = y * srcDy + 1;
/* 134 */       f.decodeLine(src, dest, srcDy, srcOffset, bpl, y * bpl);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.predictor.Optimum
 * JD-Core Version:    0.6.2
 */