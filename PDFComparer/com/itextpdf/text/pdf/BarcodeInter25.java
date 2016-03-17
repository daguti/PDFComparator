/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.Rectangle;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.awt.Canvas;
/*     */ import java.awt.Color;
/*     */ import java.awt.Image;
/*     */ import java.awt.image.MemoryImageSource;
/*     */ 
/*     */ public class BarcodeInter25 extends Barcode
/*     */ {
/*  75 */   private static final byte[][] BARS = { { 0, 0, 1, 1, 0 }, { 1, 0, 0, 0, 1 }, { 0, 1, 0, 0, 1 }, { 1, 1, 0, 0, 0 }, { 0, 0, 1, 0, 1 }, { 1, 0, 1, 0, 0 }, { 0, 1, 1, 0, 0 }, { 0, 0, 0, 1, 1 }, { 1, 0, 0, 1, 0 }, { 0, 1, 0, 1, 0 } };
/*     */ 
/*     */   public BarcodeInter25()
/*     */   {
/*     */     try
/*     */     {
/*  92 */       this.x = 0.8F;
/*  93 */       this.n = 2.0F;
/*  94 */       this.font = BaseFont.createFont("Helvetica", "winansi", false);
/*  95 */       this.size = 8.0F;
/*  96 */       this.baseline = this.size;
/*  97 */       this.barHeight = (this.size * 3.0F);
/*  98 */       this.textAlignment = 1;
/*  99 */       this.generateChecksum = false;
/* 100 */       this.checksumText = false;
/*     */     }
/*     */     catch (Exception e) {
/* 103 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String keepNumbers(String text)
/*     */   {
/* 112 */     StringBuffer sb = new StringBuffer();
/* 113 */     for (int k = 0; k < text.length(); k++) {
/* 114 */       char c = text.charAt(k);
/* 115 */       if ((c >= '0') && (c <= '9'))
/* 116 */         sb.append(c);
/*     */     }
/* 118 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static char getChecksum(String text)
/*     */   {
/* 126 */     int mul = 3;
/* 127 */     int total = 0;
/* 128 */     for (int k = text.length() - 1; k >= 0; k--) {
/* 129 */       int n = text.charAt(k) - '0';
/* 130 */       total += mul * n;
/* 131 */       mul ^= 2;
/*     */     }
/* 133 */     return (char)((10 - total % 10) % 10 + 48);
/*     */   }
/*     */ 
/*     */   public static byte[] getBarsInter25(String text)
/*     */   {
/* 141 */     text = keepNumbers(text);
/* 142 */     if ((text.length() & 0x1) != 0)
/* 143 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.text.length.must.be.even", new Object[0]));
/* 144 */     byte[] bars = new byte[text.length() * 5 + 7];
/* 145 */     int pb = 0;
/* 146 */     bars[(pb++)] = 0;
/* 147 */     bars[(pb++)] = 0;
/* 148 */     bars[(pb++)] = 0;
/* 149 */     bars[(pb++)] = 0;
/* 150 */     int len = text.length() / 2;
/* 151 */     for (int k = 0; k < len; k++) {
/* 152 */       int c1 = text.charAt(k * 2) - '0';
/* 153 */       int c2 = text.charAt(k * 2 + 1) - '0';
/* 154 */       byte[] b1 = BARS[c1];
/* 155 */       byte[] b2 = BARS[c2];
/* 156 */       for (int j = 0; j < 5; j++) {
/* 157 */         bars[(pb++)] = b1[j];
/* 158 */         bars[(pb++)] = b2[j];
/*     */       }
/*     */     }
/* 161 */     bars[(pb++)] = 1;
/* 162 */     bars[(pb++)] = 0;
/* 163 */     bars[(pb++)] = 0;
/* 164 */     return bars;
/*     */   }
/*     */ 
/*     */   public Rectangle getBarcodeSize()
/*     */   {
/* 172 */     float fontX = 0.0F;
/* 173 */     float fontY = 0.0F;
/* 174 */     if (this.font != null) {
/* 175 */       if (this.baseline > 0.0F)
/* 176 */         fontY = this.baseline - this.font.getFontDescriptor(3, this.size);
/*     */       else
/* 178 */         fontY = -this.baseline + this.size;
/* 179 */       String fullCode = this.code;
/* 180 */       if ((this.generateChecksum) && (this.checksumText))
/* 181 */         fullCode = fullCode + getChecksum(fullCode);
/* 182 */       fontX = this.font.getWidthPoint(this.altText != null ? this.altText : fullCode, this.size);
/*     */     }
/* 184 */     String fullCode = keepNumbers(this.code);
/* 185 */     int len = fullCode.length();
/* 186 */     if (this.generateChecksum)
/* 187 */       len++;
/* 188 */     float fullWidth = len * (3.0F * this.x + 2.0F * this.x * this.n) + (6.0F + this.n) * this.x;
/* 189 */     fullWidth = Math.max(fullWidth, fontX);
/* 190 */     float fullHeight = this.barHeight + fontY;
/* 191 */     return new Rectangle(fullWidth, fullHeight);
/*     */   }
/*     */ 
/*     */   public Rectangle placeBarcode(PdfContentByte cb, BaseColor barColor, BaseColor textColor)
/*     */   {
/* 231 */     String fullCode = this.code;
/* 232 */     float fontX = 0.0F;
/* 233 */     if (this.font != null) {
/* 234 */       if ((this.generateChecksum) && (this.checksumText))
/* 235 */         fullCode = fullCode + getChecksum(fullCode);
/* 236 */       fontX = this.font.getWidthPoint(fullCode = this.altText != null ? this.altText : fullCode, this.size);
/*     */     }
/* 238 */     String bCode = keepNumbers(this.code);
/* 239 */     if (this.generateChecksum)
/* 240 */       bCode = bCode + getChecksum(bCode);
/* 241 */     int len = bCode.length();
/* 242 */     float fullWidth = len * (3.0F * this.x + 2.0F * this.x * this.n) + (6.0F + this.n) * this.x;
/* 243 */     float barStartX = 0.0F;
/* 244 */     float textStartX = 0.0F;
/* 245 */     switch (this.textAlignment) {
/*     */     case 0:
/* 247 */       break;
/*     */     case 2:
/* 249 */       if (fontX > fullWidth)
/* 250 */         barStartX = fontX - fullWidth;
/*     */       else
/* 252 */         textStartX = fullWidth - fontX;
/* 253 */       break;
/*     */     default:
/* 255 */       if (fontX > fullWidth)
/* 256 */         barStartX = (fontX - fullWidth) / 2.0F;
/*     */       else
/* 258 */         textStartX = (fullWidth - fontX) / 2.0F;
/*     */       break;
/*     */     }
/* 261 */     float barStartY = 0.0F;
/* 262 */     float textStartY = 0.0F;
/* 263 */     if (this.font != null) {
/* 264 */       if (this.baseline <= 0.0F) {
/* 265 */         textStartY = this.barHeight - this.baseline;
/*     */       } else {
/* 267 */         textStartY = -this.font.getFontDescriptor(3, this.size);
/* 268 */         barStartY = textStartY + this.baseline;
/*     */       }
/*     */     }
/* 271 */     byte[] bars = getBarsInter25(bCode);
/* 272 */     boolean print = true;
/* 273 */     if (barColor != null)
/* 274 */       cb.setColorFill(barColor);
/* 275 */     for (int k = 0; k < bars.length; k++) {
/* 276 */       float w = bars[k] == 0 ? this.x : this.x * this.n;
/* 277 */       if (print)
/* 278 */         cb.rectangle(barStartX, barStartY, w - this.inkSpreading, this.barHeight);
/* 279 */       print = !print;
/* 280 */       barStartX += w;
/*     */     }
/* 282 */     cb.fill();
/* 283 */     if (this.font != null) {
/* 284 */       if (textColor != null)
/* 285 */         cb.setColorFill(textColor);
/* 286 */       cb.beginText();
/* 287 */       cb.setFontAndSize(this.font, this.size);
/* 288 */       cb.setTextMatrix(textStartX, textStartY);
/* 289 */       cb.showText(fullCode);
/* 290 */       cb.endText();
/*     */     }
/* 292 */     return getBarcodeSize();
/*     */   }
/*     */ 
/*     */   public Image createAwtImage(Color foreground, Color background)
/*     */   {
/* 304 */     int f = foreground.getRGB();
/* 305 */     int g = background.getRGB();
/* 306 */     Canvas canvas = new Canvas();
/*     */ 
/* 308 */     String bCode = keepNumbers(this.code);
/* 309 */     if (this.generateChecksum)
/* 310 */       bCode = bCode + getChecksum(bCode);
/* 311 */     int len = bCode.length();
/* 312 */     int nn = (int)this.n;
/* 313 */     int fullWidth = len * (3 + 2 * nn) + (6 + nn);
/* 314 */     byte[] bars = getBarsInter25(bCode);
/* 315 */     boolean print = true;
/* 316 */     int ptr = 0;
/* 317 */     int height = (int)this.barHeight;
/* 318 */     int[] pix = new int[fullWidth * height];
/* 319 */     for (int k = 0; k < bars.length; k++) {
/* 320 */       int w = bars[k] == 0 ? 1 : nn;
/* 321 */       int c = g;
/* 322 */       if (print)
/* 323 */         c = f;
/* 324 */       print = !print;
/* 325 */       for (int j = 0; j < w; j++)
/* 326 */         pix[(ptr++)] = c;
/*     */     }
/* 328 */     for (int k = fullWidth; k < pix.length; k += fullWidth) {
/* 329 */       System.arraycopy(pix, 0, pix, k, fullWidth);
/*     */     }
/* 331 */     Image img = canvas.createImage(new MemoryImageSource(fullWidth, height, pix, 0, fullWidth));
/*     */ 
/* 333 */     return img;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.BarcodeInter25
 * JD-Core Version:    0.6.2
 */