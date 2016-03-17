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
/*     */ public class BarcodeCodabar extends Barcode
/*     */ {
/*  74 */   private static final byte[][] BARS = { { 0, 0, 0, 0, 0, 1, 1 }, { 0, 0, 0, 0, 1, 1, 0 }, { 0, 0, 0, 1, 0, 0, 1 }, { 1, 1, 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0, 1, 0 }, { 1, 0, 0, 0, 0, 1, 0 }, { 0, 1, 0, 0, 0, 0, 1 }, { 0, 1, 0, 0, 1, 0, 0 }, { 0, 1, 1, 0, 0, 0, 0 }, { 1, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 1, 1, 0, 0 }, { 0, 0, 1, 1, 0, 0, 0 }, { 1, 0, 0, 0, 1, 0, 1 }, { 1, 0, 1, 0, 0, 0, 1 }, { 1, 0, 1, 0, 1, 0, 0 }, { 0, 0, 1, 0, 1, 0, 1 }, { 0, 0, 1, 1, 0, 1, 0 }, { 0, 1, 0, 1, 0, 0, 1 }, { 0, 0, 0, 1, 0, 1, 1 }, { 0, 0, 0, 1, 1, 1, 0 } };
/*     */   private static final String CHARS = "0123456789-$:/.+ABCD";
/*     */   private static final int START_STOP_IDX = 16;
/*     */ 
/*     */   public BarcodeCodabar()
/*     */   {
/*     */     try
/*     */     {
/* 107 */       this.x = 0.8F;
/* 108 */       this.n = 2.0F;
/* 109 */       this.font = BaseFont.createFont("Helvetica", "winansi", false);
/* 110 */       this.size = 8.0F;
/* 111 */       this.baseline = this.size;
/* 112 */       this.barHeight = (this.size * 3.0F);
/* 113 */       this.textAlignment = 1;
/* 114 */       this.generateChecksum = false;
/* 115 */       this.checksumText = false;
/* 116 */       this.startStopText = false;
/* 117 */       this.codeType = 12;
/*     */     }
/*     */     catch (Exception e) {
/* 120 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static byte[] getBarsCodabar(String text)
/*     */   {
/* 129 */     text = text.toUpperCase();
/* 130 */     int len = text.length();
/* 131 */     if (len < 2)
/* 132 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("codabar.must.have.at.least.a.start.and.stop.character", new Object[0]));
/* 133 */     if (("0123456789-$:/.+ABCD".indexOf(text.charAt(0)) < 16) || ("0123456789-$:/.+ABCD".indexOf(text.charAt(len - 1)) < 16))
/* 134 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("codabar.must.have.one.of.abcd.as.start.stop.character", new Object[0]));
/* 135 */     byte[] bars = new byte[text.length() * 8 - 1];
/* 136 */     for (int k = 0; k < len; k++) {
/* 137 */       int idx = "0123456789-$:/.+ABCD".indexOf(text.charAt(k));
/* 138 */       if ((idx >= 16) && (k > 0) && (k < len - 1))
/* 139 */         throw new IllegalArgumentException(MessageLocalization.getComposedMessage("in.codabar.start.stop.characters.are.only.allowed.at.the.extremes", new Object[0]));
/* 140 */       if (idx < 0)
/* 141 */         throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.character.1.is.illegal.in.codabar", text.charAt(k)));
/* 142 */       System.arraycopy(BARS[idx], 0, bars, k * 8, 7);
/*     */     }
/* 144 */     return bars;
/*     */   }
/*     */ 
/*     */   public static String calculateChecksum(String code) {
/* 148 */     if (code.length() < 2)
/* 149 */       return code;
/* 150 */     String text = code.toUpperCase();
/* 151 */     int sum = 0;
/* 152 */     int len = text.length();
/* 153 */     for (int k = 0; k < len; k++)
/* 154 */       sum += "0123456789-$:/.+ABCD".indexOf(text.charAt(k));
/* 155 */     sum = (sum + 15) / 16 * 16 - sum;
/* 156 */     return code.substring(0, len - 1) + "0123456789-$:/.+ABCD".charAt(sum) + code.substring(len - 1);
/*     */   }
/*     */ 
/*     */   public Rectangle getBarcodeSize()
/*     */   {
/* 164 */     float fontX = 0.0F;
/* 165 */     float fontY = 0.0F;
/* 166 */     String text = this.code;
/* 167 */     if ((this.generateChecksum) && (this.checksumText))
/* 168 */       text = calculateChecksum(this.code);
/* 169 */     if (!this.startStopText)
/* 170 */       text = text.substring(1, text.length() - 1);
/* 171 */     if (this.font != null) {
/* 172 */       if (this.baseline > 0.0F)
/* 173 */         fontY = this.baseline - this.font.getFontDescriptor(3, this.size);
/*     */       else
/* 175 */         fontY = -this.baseline + this.size;
/* 176 */       fontX = this.font.getWidthPoint(this.altText != null ? this.altText : text, this.size);
/*     */     }
/* 178 */     text = this.code;
/* 179 */     if (this.generateChecksum)
/* 180 */       text = calculateChecksum(this.code);
/* 181 */     byte[] bars = getBarsCodabar(text);
/* 182 */     int wide = 0;
/* 183 */     for (int k = 0; k < bars.length; k++) {
/* 184 */       wide += bars[k];
/*     */     }
/* 186 */     int narrow = bars.length - wide;
/* 187 */     float fullWidth = this.x * (narrow + wide * this.n);
/* 188 */     fullWidth = Math.max(fullWidth, fontX);
/* 189 */     float fullHeight = this.barHeight + fontY;
/* 190 */     return new Rectangle(fullWidth, fullHeight);
/*     */   }
/*     */ 
/*     */   public Rectangle placeBarcode(PdfContentByte cb, BaseColor barColor, BaseColor textColor)
/*     */   {
/* 230 */     String fullCode = this.code;
/* 231 */     if ((this.generateChecksum) && (this.checksumText))
/* 232 */       fullCode = calculateChecksum(this.code);
/* 233 */     if (!this.startStopText)
/* 234 */       fullCode = fullCode.substring(1, fullCode.length() - 1);
/* 235 */     float fontX = 0.0F;
/* 236 */     if (this.font != null) {
/* 237 */       fontX = this.font.getWidthPoint(fullCode = this.altText != null ? this.altText : fullCode, this.size);
/*     */     }
/* 239 */     byte[] bars = getBarsCodabar(this.generateChecksum ? calculateChecksum(this.code) : this.code);
/* 240 */     int wide = 0;
/* 241 */     for (int k = 0; k < bars.length; k++) {
/* 242 */       wide += bars[k];
/*     */     }
/* 244 */     int narrow = bars.length - wide;
/* 245 */     float fullWidth = this.x * (narrow + wide * this.n);
/* 246 */     float barStartX = 0.0F;
/* 247 */     float textStartX = 0.0F;
/* 248 */     switch (this.textAlignment) {
/*     */     case 0:
/* 250 */       break;
/*     */     case 2:
/* 252 */       if (fontX > fullWidth)
/* 253 */         barStartX = fontX - fullWidth;
/*     */       else
/* 255 */         textStartX = fullWidth - fontX;
/* 256 */       break;
/*     */     default:
/* 258 */       if (fontX > fullWidth)
/* 259 */         barStartX = (fontX - fullWidth) / 2.0F;
/*     */       else
/* 261 */         textStartX = (fullWidth - fontX) / 2.0F;
/*     */       break;
/*     */     }
/* 264 */     float barStartY = 0.0F;
/* 265 */     float textStartY = 0.0F;
/* 266 */     if (this.font != null) {
/* 267 */       if (this.baseline <= 0.0F) {
/* 268 */         textStartY = this.barHeight - this.baseline;
/*     */       } else {
/* 270 */         textStartY = -this.font.getFontDescriptor(3, this.size);
/* 271 */         barStartY = textStartY + this.baseline;
/*     */       }
/*     */     }
/* 274 */     boolean print = true;
/* 275 */     if (barColor != null)
/* 276 */       cb.setColorFill(barColor);
/* 277 */     for (int k = 0; k < bars.length; k++) {
/* 278 */       float w = bars[k] == 0 ? this.x : this.x * this.n;
/* 279 */       if (print)
/* 280 */         cb.rectangle(barStartX, barStartY, w - this.inkSpreading, this.barHeight);
/* 281 */       print = !print;
/* 282 */       barStartX += w;
/*     */     }
/* 284 */     cb.fill();
/* 285 */     if (this.font != null) {
/* 286 */       if (textColor != null)
/* 287 */         cb.setColorFill(textColor);
/* 288 */       cb.beginText();
/* 289 */       cb.setFontAndSize(this.font, this.size);
/* 290 */       cb.setTextMatrix(textStartX, textStartY);
/* 291 */       cb.showText(fullCode);
/* 292 */       cb.endText();
/*     */     }
/* 294 */     return getBarcodeSize();
/*     */   }
/*     */ 
/*     */   public Image createAwtImage(Color foreground, Color background)
/*     */   {
/* 304 */     int f = foreground.getRGB();
/* 305 */     int g = background.getRGB();
/* 306 */     Canvas canvas = new Canvas();
/*     */ 
/* 308 */     String fullCode = this.code;
/* 309 */     if ((this.generateChecksum) && (this.checksumText))
/* 310 */       fullCode = calculateChecksum(this.code);
/* 311 */     if (!this.startStopText)
/* 312 */       fullCode = fullCode.substring(1, fullCode.length() - 1);
/* 313 */     byte[] bars = getBarsCodabar(this.generateChecksum ? calculateChecksum(this.code) : this.code);
/* 314 */     int wide = 0;
/* 315 */     for (int k = 0; k < bars.length; k++) {
/* 316 */       wide += bars[k];
/*     */     }
/* 318 */     int narrow = bars.length - wide;
/* 319 */     int fullWidth = narrow + wide * (int)this.n;
/* 320 */     boolean print = true;
/* 321 */     int ptr = 0;
/* 322 */     int height = (int)this.barHeight;
/* 323 */     int[] pix = new int[fullWidth * height];
/* 324 */     for (int k = 0; k < bars.length; k++) {
/* 325 */       int w = bars[k] == 0 ? 1 : (int)this.n;
/* 326 */       int c = g;
/* 327 */       if (print)
/* 328 */         c = f;
/* 329 */       print = !print;
/* 330 */       for (int j = 0; j < w; j++)
/* 331 */         pix[(ptr++)] = c;
/*     */     }
/* 333 */     for (int k = fullWidth; k < pix.length; k += fullWidth) {
/* 334 */       System.arraycopy(pix, 0, pix, k, fullWidth);
/*     */     }
/* 336 */     Image img = canvas.createImage(new MemoryImageSource(fullWidth, height, pix, 0, fullWidth));
/*     */ 
/* 338 */     return img;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.BarcodeCodabar
 * JD-Core Version:    0.6.2
 */