/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import com.itextpdf.text.Rectangle;
/*     */ import java.awt.Canvas;
/*     */ import java.awt.Color;
/*     */ import java.awt.Image;
/*     */ import java.awt.image.MemoryImageSource;
/*     */ 
/*     */ public class BarcodePostnet extends Barcode
/*     */ {
/*  65 */   private static final byte[][] BARS = { { 1, 1, 0, 0, 0 }, { 0, 0, 0, 1, 1 }, { 0, 0, 1, 0, 1 }, { 0, 0, 1, 1, 0 }, { 0, 1, 0, 0, 1 }, { 0, 1, 0, 1, 0 }, { 0, 1, 1, 0, 0 }, { 1, 0, 0, 0, 1 }, { 1, 0, 0, 1, 0 }, { 1, 0, 1, 0, 0 } };
/*     */ 
/*     */   public BarcodePostnet()
/*     */   {
/*  81 */     this.n = 3.272727F;
/*  82 */     this.x = 1.44F;
/*  83 */     this.barHeight = 9.0F;
/*  84 */     this.size = 3.6F;
/*  85 */     this.codeType = 7;
/*     */   }
/*     */ 
/*     */   public static byte[] getBarsPostnet(String text)
/*     */   {
/*  93 */     int total = 0;
/*  94 */     for (int k = text.length() - 1; k >= 0; k--) {
/*  95 */       int n = text.charAt(k) - '0';
/*  96 */       total += n;
/*     */     }
/*  98 */     text = text + (char)((10 - total % 10) % 10 + 48);
/*  99 */     byte[] bars = new byte[text.length() * 5 + 2];
/* 100 */     bars[0] = 1;
/* 101 */     bars[(bars.length - 1)] = 1;
/* 102 */     for (int k = 0; k < text.length(); k++) {
/* 103 */       int c = text.charAt(k) - '0';
/* 104 */       System.arraycopy(BARS[c], 0, bars, k * 5 + 1, 5);
/*     */     }
/* 106 */     return bars;
/*     */   }
/*     */ 
/*     */   public Rectangle getBarcodeSize()
/*     */   {
/* 114 */     float width = ((this.code.length() + 1) * 5 + 1) * this.n + this.x;
/* 115 */     return new Rectangle(width, this.barHeight);
/*     */   }
/*     */ 
/*     */   public Rectangle placeBarcode(PdfContentByte cb, BaseColor barColor, BaseColor textColor)
/*     */   {
/* 155 */     if (barColor != null)
/* 156 */       cb.setColorFill(barColor);
/* 157 */     byte[] bars = getBarsPostnet(this.code);
/* 158 */     byte flip = 1;
/* 159 */     if (this.codeType == 8) {
/* 160 */       flip = 0;
/* 161 */       bars[0] = 0;
/* 162 */       bars[(bars.length - 1)] = 0;
/*     */     }
/* 164 */     float startX = 0.0F;
/* 165 */     for (int k = 0; k < bars.length; k++) {
/* 166 */       cb.rectangle(startX, 0.0F, this.x - this.inkSpreading, bars[k] == flip ? this.barHeight : this.size);
/* 167 */       startX += this.n;
/*     */     }
/* 169 */     cb.fill();
/* 170 */     return getBarcodeSize();
/*     */   }
/*     */ 
/*     */   public Image createAwtImage(Color foreground, Color background)
/*     */   {
/* 183 */     int f = foreground.getRGB();
/* 184 */     int g = background.getRGB();
/* 185 */     Canvas canvas = new Canvas();
/* 186 */     int barWidth = (int)this.x;
/* 187 */     if (barWidth <= 0)
/* 188 */       barWidth = 1;
/* 189 */     int barDistance = (int)this.n;
/* 190 */     if (barDistance <= barWidth)
/* 191 */       barDistance = barWidth + 1;
/* 192 */     int barShort = (int)this.size;
/* 193 */     if (barShort <= 0)
/* 194 */       barShort = 1;
/* 195 */     int barTall = (int)this.barHeight;
/* 196 */     if (barTall <= barShort)
/* 197 */       barTall = barShort + 1;
/* 198 */     int width = ((this.code.length() + 1) * 5 + 1) * barDistance + barWidth;
/* 199 */     int[] pix = new int[width * barTall];
/* 200 */     byte[] bars = getBarsPostnet(this.code);
/* 201 */     byte flip = 1;
/* 202 */     if (this.codeType == 8) {
/* 203 */       flip = 0;
/* 204 */       bars[0] = 0;
/* 205 */       bars[(bars.length - 1)] = 0;
/*     */     }
/* 207 */     int idx = 0;
/* 208 */     for (int k = 0; k < bars.length; k++) {
/* 209 */       boolean dot = bars[k] == flip;
/* 210 */       for (int j = 0; j < barDistance; j++) {
/* 211 */         pix[(idx + j)] = ((dot) && (j < barWidth) ? f : g);
/*     */       }
/* 213 */       idx += barDistance;
/*     */     }
/* 215 */     int limit = width * (barTall - barShort);
/* 216 */     for (int k = width; k < limit; k += width)
/* 217 */       System.arraycopy(pix, 0, pix, k, width);
/* 218 */     idx = limit;
/* 219 */     for (int k = 0; k < bars.length; k++) {
/* 220 */       for (int j = 0; j < barDistance; j++) {
/* 221 */         pix[(idx + j)] = (j < barWidth ? f : g);
/*     */       }
/* 223 */       idx += barDistance;
/*     */     }
/* 225 */     for (int k = limit + width; k < pix.length; k += width)
/* 226 */       System.arraycopy(pix, limit, pix, k, width);
/* 227 */     Image img = canvas.createImage(new MemoryImageSource(width, barTall, pix, 0, width));
/*     */ 
/* 229 */     return img;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.BarcodePostnet
 * JD-Core Version:    0.6.2
 */