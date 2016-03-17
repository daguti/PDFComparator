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
/*     */ public class Barcode39 extends Barcode
/*     */ {
/*  75 */   private static final byte[][] BARS = { { 0, 0, 0, 1, 1, 0, 1, 0, 0 }, { 1, 0, 0, 1, 0, 0, 0, 0, 1 }, { 0, 0, 1, 1, 0, 0, 0, 0, 1 }, { 1, 0, 1, 1, 0, 0, 0, 0, 0 }, { 0, 0, 0, 1, 1, 0, 0, 0, 1 }, { 1, 0, 0, 1, 1, 0, 0, 0, 0 }, { 0, 0, 1, 1, 1, 0, 0, 0, 0 }, { 0, 0, 0, 1, 0, 0, 1, 0, 1 }, { 1, 0, 0, 1, 0, 0, 1, 0, 0 }, { 0, 0, 1, 1, 0, 0, 1, 0, 0 }, { 1, 0, 0, 0, 0, 1, 0, 0, 1 }, { 0, 0, 1, 0, 0, 1, 0, 0, 1 }, { 1, 0, 1, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 0, 1, 1, 0, 0, 1 }, { 1, 0, 0, 0, 1, 1, 0, 0, 0 }, { 0, 0, 1, 0, 1, 1, 0, 0, 0 }, { 0, 0, 0, 0, 0, 1, 1, 0, 1 }, { 1, 0, 0, 0, 0, 1, 1, 0, 0 }, { 0, 0, 1, 0, 0, 1, 1, 0, 0 }, { 0, 0, 0, 0, 1, 1, 1, 0, 0 }, { 1, 0, 0, 0, 0, 0, 0, 1, 1 }, { 0, 0, 1, 0, 0, 0, 0, 1, 1 }, { 1, 0, 1, 0, 0, 0, 0, 1, 0 }, { 0, 0, 0, 0, 1, 0, 0, 1, 1 }, { 1, 0, 0, 0, 1, 0, 0, 1, 0 }, { 0, 0, 1, 0, 1, 0, 0, 1, 0 }, { 0, 0, 0, 0, 0, 0, 1, 1, 1 }, { 1, 0, 0, 0, 0, 0, 1, 1, 0 }, { 0, 0, 1, 0, 0, 0, 1, 1, 0 }, { 0, 0, 0, 0, 1, 0, 1, 1, 0 }, { 1, 1, 0, 0, 0, 0, 0, 0, 1 }, { 0, 1, 1, 0, 0, 0, 0, 0, 1 }, { 1, 1, 1, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 1, 0, 0, 0, 1 }, { 1, 1, 0, 0, 1, 0, 0, 0, 0 }, { 0, 1, 1, 0, 1, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 0, 1, 0, 1 }, { 1, 1, 0, 0, 0, 0, 1, 0, 0 }, { 0, 1, 1, 0, 0, 0, 1, 0, 0 }, { 0, 1, 0, 1, 0, 1, 0, 0, 0 }, { 0, 1, 0, 1, 0, 0, 0, 1, 0 }, { 0, 1, 0, 0, 0, 1, 0, 1, 0 }, { 0, 0, 0, 1, 0, 1, 0, 1, 0 }, { 0, 1, 0, 0, 1, 0, 1, 0, 0 } };
/*     */   private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%*";
/*     */   private static final String EXTENDED = "%U$A$B$C$D$E$F$G$H$I$J$K$L$M$N$O$P$Q$R$S$T$U$V$W$X$Y$Z%A%B%C%D%E  /A/B/C/D/E/F/G/H/I/J/K/L - ./O 0 1 2 3 4 5 6 7 8 9/Z%F%G%H%I%J%V A B C D E F G H I J K L M N O P Q R S T U V W X Y Z%K%L%M%N%O%W+A+B+C+D+E+F+G+H+I+J+K+L+M+N+O+P+Q+R+S+T+U+V+W+X+Y+Z%P%Q%R%S%T";
/*     */ 
/*     */   public Barcode39()
/*     */   {
/*     */     try
/*     */     {
/* 142 */       this.x = 0.8F;
/* 143 */       this.n = 2.0F;
/* 144 */       this.font = BaseFont.createFont("Helvetica", "winansi", false);
/* 145 */       this.size = 8.0F;
/* 146 */       this.baseline = this.size;
/* 147 */       this.barHeight = (this.size * 3.0F);
/* 148 */       this.textAlignment = 1;
/* 149 */       this.generateChecksum = false;
/* 150 */       this.checksumText = false;
/* 151 */       this.startStopText = true;
/* 152 */       this.extended = false;
/*     */     }
/*     */     catch (Exception e) {
/* 155 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static byte[] getBarsCode39(String text)
/*     */   {
/* 165 */     text = "*" + text + "*";
/* 166 */     byte[] bars = new byte[text.length() * 10 - 1];
/* 167 */     for (int k = 0; k < text.length(); k++) {
/* 168 */       int idx = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%*".indexOf(text.charAt(k));
/* 169 */       if (idx < 0)
/* 170 */         throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.character.1.is.illegal.in.code.39", text.charAt(k)));
/* 171 */       System.arraycopy(BARS[idx], 0, bars, k * 10, 9);
/*     */     }
/* 173 */     return bars;
/*     */   }
/*     */ 
/*     */   public static String getCode39Ex(String text)
/*     */   {
/* 182 */     StringBuilder out = new StringBuilder("");
/* 183 */     for (int k = 0; k < text.length(); k++) {
/* 184 */       char c = text.charAt(k);
/* 185 */       if (c > '')
/* 186 */         throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.character.1.is.illegal.in.code.39.extended", c));
/* 187 */       char c1 = "%U$A$B$C$D$E$F$G$H$I$J$K$L$M$N$O$P$Q$R$S$T$U$V$W$X$Y$Z%A%B%C%D%E  /A/B/C/D/E/F/G/H/I/J/K/L - ./O 0 1 2 3 4 5 6 7 8 9/Z%F%G%H%I%J%V A B C D E F G H I J K L M N O P Q R S T U V W X Y Z%K%L%M%N%O%W+A+B+C+D+E+F+G+H+I+J+K+L+M+N+O+P+Q+R+S+T+U+V+W+X+Y+Z%P%Q%R%S%T".charAt(c * '\002');
/* 188 */       char c2 = "%U$A$B$C$D$E$F$G$H$I$J$K$L$M$N$O$P$Q$R$S$T$U$V$W$X$Y$Z%A%B%C%D%E  /A/B/C/D/E/F/G/H/I/J/K/L - ./O 0 1 2 3 4 5 6 7 8 9/Z%F%G%H%I%J%V A B C D E F G H I J K L M N O P Q R S T U V W X Y Z%K%L%M%N%O%W+A+B+C+D+E+F+G+H+I+J+K+L+M+N+O+P+Q+R+S+T+U+V+W+X+Y+Z%P%Q%R%S%T".charAt(c * '\002' + 1);
/* 189 */       if (c1 != ' ')
/* 190 */         out.append(c1);
/* 191 */       out.append(c2);
/*     */     }
/* 193 */     return out.toString();
/*     */   }
/*     */ 
/*     */   static char getChecksum(String text)
/*     */   {
/* 201 */     int chk = 0;
/* 202 */     for (int k = 0; k < text.length(); k++) {
/* 203 */       int idx = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%*".indexOf(text.charAt(k));
/* 204 */       if (idx < 0)
/* 205 */         throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.character.1.is.illegal.in.code.39", text.charAt(k)));
/* 206 */       chk += idx;
/*     */     }
/* 208 */     return "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%*".charAt(chk % 43);
/*     */   }
/*     */ 
/*     */   public Rectangle getBarcodeSize()
/*     */   {
/* 216 */     float fontX = 0.0F;
/* 217 */     float fontY = 0.0F;
/* 218 */     String fCode = this.code;
/* 219 */     if (this.extended)
/* 220 */       fCode = getCode39Ex(this.code);
/* 221 */     if (this.font != null) {
/* 222 */       if (this.baseline > 0.0F)
/* 223 */         fontY = this.baseline - this.font.getFontDescriptor(3, this.size);
/*     */       else
/* 225 */         fontY = -this.baseline + this.size;
/* 226 */       String fullCode = this.code;
/* 227 */       if ((this.generateChecksum) && (this.checksumText))
/* 228 */         fullCode = fullCode + getChecksum(fCode);
/* 229 */       if (this.startStopText)
/* 230 */         fullCode = "*" + fullCode + "*";
/* 231 */       fontX = this.font.getWidthPoint(this.altText != null ? this.altText : fullCode, this.size);
/*     */     }
/* 233 */     int len = fCode.length() + 2;
/* 234 */     if (this.generateChecksum)
/* 235 */       len++;
/* 236 */     float fullWidth = len * (6.0F * this.x + 3.0F * this.x * this.n) + (len - 1) * this.x;
/* 237 */     fullWidth = Math.max(fullWidth, fontX);
/* 238 */     float fullHeight = this.barHeight + fontY;
/* 239 */     return new Rectangle(fullWidth, fullHeight);
/*     */   }
/*     */ 
/*     */   public Rectangle placeBarcode(PdfContentByte cb, BaseColor barColor, BaseColor textColor)
/*     */   {
/* 279 */     String fullCode = this.code;
/* 280 */     float fontX = 0.0F;
/* 281 */     String bCode = this.code;
/* 282 */     if (this.extended)
/* 283 */       bCode = getCode39Ex(this.code);
/* 284 */     if (this.font != null) {
/* 285 */       if ((this.generateChecksum) && (this.checksumText))
/* 286 */         fullCode = fullCode + getChecksum(bCode);
/* 287 */       if (this.startStopText)
/* 288 */         fullCode = "*" + fullCode + "*";
/* 289 */       fontX = this.font.getWidthPoint(fullCode = this.altText != null ? this.altText : fullCode, this.size);
/*     */     }
/* 291 */     if (this.generateChecksum)
/* 292 */       bCode = bCode + getChecksum(bCode);
/* 293 */     int len = bCode.length() + 2;
/* 294 */     float fullWidth = len * (6.0F * this.x + 3.0F * this.x * this.n) + (len - 1) * this.x;
/* 295 */     float barStartX = 0.0F;
/* 296 */     float textStartX = 0.0F;
/* 297 */     switch (this.textAlignment) {
/*     */     case 0:
/* 299 */       break;
/*     */     case 2:
/* 301 */       if (fontX > fullWidth)
/* 302 */         barStartX = fontX - fullWidth;
/*     */       else
/* 304 */         textStartX = fullWidth - fontX;
/* 305 */       break;
/*     */     default:
/* 307 */       if (fontX > fullWidth)
/* 308 */         barStartX = (fontX - fullWidth) / 2.0F;
/*     */       else
/* 310 */         textStartX = (fullWidth - fontX) / 2.0F;
/*     */       break;
/*     */     }
/* 313 */     float barStartY = 0.0F;
/* 314 */     float textStartY = 0.0F;
/* 315 */     if (this.font != null) {
/* 316 */       if (this.baseline <= 0.0F) {
/* 317 */         textStartY = this.barHeight - this.baseline;
/*     */       } else {
/* 319 */         textStartY = -this.font.getFontDescriptor(3, this.size);
/* 320 */         barStartY = textStartY + this.baseline;
/*     */       }
/*     */     }
/* 323 */     byte[] bars = getBarsCode39(bCode);
/* 324 */     boolean print = true;
/* 325 */     if (barColor != null)
/* 326 */       cb.setColorFill(barColor);
/* 327 */     for (int k = 0; k < bars.length; k++) {
/* 328 */       float w = bars[k] == 0 ? this.x : this.x * this.n;
/* 329 */       if (print)
/* 330 */         cb.rectangle(barStartX, barStartY, w - this.inkSpreading, this.barHeight);
/* 331 */       print = !print;
/* 332 */       barStartX += w;
/*     */     }
/* 334 */     cb.fill();
/* 335 */     if (this.font != null) {
/* 336 */       if (textColor != null)
/* 337 */         cb.setColorFill(textColor);
/* 338 */       cb.beginText();
/* 339 */       cb.setFontAndSize(this.font, this.size);
/* 340 */       cb.setTextMatrix(textStartX, textStartY);
/* 341 */       cb.showText(fullCode);
/* 342 */       cb.endText();
/*     */     }
/* 344 */     return getBarcodeSize();
/*     */   }
/*     */ 
/*     */   public Image createAwtImage(Color foreground, Color background)
/*     */   {
/* 356 */     int f = foreground.getRGB();
/* 357 */     int g = background.getRGB();
/* 358 */     Canvas canvas = new Canvas();
/*     */ 
/* 360 */     String bCode = this.code;
/* 361 */     if (this.extended)
/* 362 */       bCode = getCode39Ex(this.code);
/* 363 */     if (this.generateChecksum)
/* 364 */       bCode = bCode + getChecksum(bCode);
/* 365 */     int len = bCode.length() + 2;
/* 366 */     int nn = (int)this.n;
/* 367 */     int fullWidth = len * (6 + 3 * nn) + (len - 1);
/* 368 */     byte[] bars = getBarsCode39(bCode);
/* 369 */     boolean print = true;
/* 370 */     int ptr = 0;
/* 371 */     int height = (int)this.barHeight;
/* 372 */     int[] pix = new int[fullWidth * height];
/* 373 */     for (int k = 0; k < bars.length; k++) {
/* 374 */       int w = bars[k] == 0 ? 1 : nn;
/* 375 */       int c = g;
/* 376 */       if (print)
/* 377 */         c = f;
/* 378 */       print = !print;
/* 379 */       for (int j = 0; j < w; j++)
/* 380 */         pix[(ptr++)] = c;
/*     */     }
/* 382 */     for (int k = fullWidth; k < pix.length; k += fullWidth) {
/* 383 */       System.arraycopy(pix, 0, pix, k, fullWidth);
/*     */     }
/* 385 */     Image img = canvas.createImage(new MemoryImageSource(fullWidth, height, pix, 0, fullWidth));
/*     */ 
/* 387 */     return img;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.Barcode39
 * JD-Core Version:    0.6.2
 */