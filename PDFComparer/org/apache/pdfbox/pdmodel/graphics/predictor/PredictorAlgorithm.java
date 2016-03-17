/*     */ package org.apache.pdfbox.pdmodel.graphics.predictor;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.Random;
/*     */ 
/*     */ public abstract class PredictorAlgorithm
/*     */ {
/*     */   private int width;
/*     */   private int height;
/*     */   private int bpp;
/*     */ 
/*     */   public void checkBufsiz(byte[] src, byte[] dest)
/*     */   {
/*  45 */     if (src.length != dest.length)
/*     */     {
/*  47 */       throw new IllegalArgumentException("src.length != dest.length");
/*     */     }
/*  49 */     if (src.length != getWidth() * getHeight() * getBpp())
/*     */     {
/*  51 */       throw new IllegalArgumentException("src.length != width * height * bpp");
/*     */     }
/*     */   }
/*     */ 
/*     */   public abstract void encodeLine(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */ 
/*     */   public abstract void decodeLine(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 103 */     Random rnd = new Random();
/* 104 */     int width = 5;
/* 105 */     int height = 5;
/* 106 */     int bpp = 3;
/* 107 */     byte[] raw = new byte[width * height * bpp];
/* 108 */     rnd.nextBytes(raw);
/* 109 */     System.out.println("raw:   ");
/* 110 */     dump(raw);
/* 111 */     for (int i = 10; i < 15; i++)
/*     */     {
/* 113 */       byte[] decoded = new byte[width * height * bpp];
/* 114 */       byte[] encoded = new byte[width * height * bpp];
/*     */ 
/* 116 */       PredictorAlgorithm filter = getFilter(i);
/* 117 */       filter.setWidth(width);
/* 118 */       filter.setHeight(height);
/* 119 */       filter.setBpp(bpp);
/* 120 */       filter.encode(raw, encoded);
/* 121 */       filter.decode(encoded, decoded);
/* 122 */       System.out.println(filter.getClass().getName());
/* 123 */       dump(decoded);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int leftPixel(byte[] buf, int offset, int dy, int x)
/*     */   {
/* 139 */     return x >= getBpp() ? buf[(offset + x - getBpp())] : 0;
/*     */   }
/*     */ 
/*     */   public int abovePixel(byte[] buf, int offset, int dy, int x)
/*     */   {
/* 154 */     return offset >= dy ? buf[(offset + x - dy)] : 0;
/*     */   }
/*     */ 
/*     */   public int aboveLeftPixel(byte[] buf, int offset, int dy, int x)
/*     */   {
/* 169 */     return (offset >= dy) && (x >= getBpp()) ? buf[(offset + x - dy - getBpp())] : 0;
/*     */   }
/*     */ 
/*     */   private static void dump(byte[] raw)
/*     */   {
/* 180 */     for (int i = 0; i < raw.length; i++)
/*     */     {
/* 182 */       System.out.print(raw[i] + " ");
/*     */     }
/* 184 */     System.out.println();
/*     */   }
/*     */ 
/*     */   public int getBpp()
/*     */   {
/* 192 */     return this.bpp;
/*     */   }
/*     */ 
/*     */   public void setBpp(int newBpp)
/*     */   {
/* 201 */     this.bpp = newBpp;
/*     */   }
/*     */ 
/*     */   public int getHeight()
/*     */   {
/* 209 */     return this.height;
/*     */   }
/*     */ 
/*     */   public void setHeight(int newHeight)
/*     */   {
/* 218 */     this.height = newHeight;
/*     */   }
/*     */ 
/*     */   public int getWidth()
/*     */   {
/* 226 */     return this.width;
/*     */   }
/*     */ 
/*     */   public void setWidth(int newWidth)
/*     */   {
/* 235 */     this.width = newWidth;
/*     */   }
/*     */ 
/*     */   public void encode(byte[] src, byte[] dest)
/*     */   {
/* 250 */     checkBufsiz(dest, src);
/* 251 */     int dy = getWidth() * getBpp();
/* 252 */     for (int y = 0; y < this.height; y++)
/*     */     {
/* 254 */       int yoffset = y * dy;
/* 255 */       encodeLine(src, dest, dy, yoffset, dy, yoffset);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void decode(byte[] src, byte[] dest)
/*     */   {
/* 270 */     checkBufsiz(src, dest);
/* 271 */     int dy = this.width * this.bpp;
/* 272 */     for (int y = 0; y < this.height; y++)
/*     */     {
/* 274 */       int yoffset = y * dy;
/* 275 */       decodeLine(src, dest, dy, yoffset, dy, yoffset);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static PredictorAlgorithm getFilter(int predictor)
/*     */   {
/*     */     PredictorAlgorithm filter;
/* 297 */     switch (predictor)
/*     */     {
/*     */     case 10:
/* 300 */       filter = new None();
/* 301 */       break;
/*     */     case 11:
/* 303 */       filter = new Sub();
/* 304 */       break;
/*     */     case 12:
/* 306 */       filter = new Up();
/* 307 */       break;
/*     */     case 13:
/* 309 */       filter = new Average();
/* 310 */       break;
/*     */     case 14:
/* 312 */       filter = new Paeth();
/* 313 */       break;
/*     */     case 15:
/* 315 */       filter = new Optimum();
/* 316 */       break;
/*     */     default:
/* 318 */       filter = new None();
/*     */     }
/* 320 */     return filter;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.predictor.PredictorAlgorithm
 * JD-Core Version:    0.6.2
 */