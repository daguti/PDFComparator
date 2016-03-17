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
/*     */ import java.util.Arrays;
/*     */ 
/*     */ public class BarcodeEAN extends Barcode
/*     */ {
/*  72 */   private static final int[] GUARD_EMPTY = new int[0];
/*     */ 
/*  74 */   private static final int[] GUARD_UPCA = { 0, 2, 4, 6, 28, 30, 52, 54, 56, 58 };
/*     */ 
/*  76 */   private static final int[] GUARD_EAN13 = { 0, 2, 28, 30, 56, 58 };
/*     */ 
/*  78 */   private static final int[] GUARD_EAN8 = { 0, 2, 20, 22, 40, 42 };
/*     */ 
/*  80 */   private static final int[] GUARD_UPCE = { 0, 2, 28, 30, 32 };
/*     */ 
/*  82 */   private static final float[] TEXTPOS_EAN13 = { 6.5F, 13.5F, 20.5F, 27.5F, 34.5F, 41.5F, 53.5F, 60.5F, 67.5F, 74.5F, 81.5F, 88.5F };
/*     */ 
/*  84 */   private static final float[] TEXTPOS_EAN8 = { 6.5F, 13.5F, 20.5F, 27.5F, 39.5F, 46.5F, 53.5F, 60.5F };
/*     */ 
/*  86 */   private static final byte[][] BARS = { { 3, 2, 1, 1 }, { 2, 2, 2, 1 }, { 2, 1, 2, 2 }, { 1, 4, 1, 1 }, { 1, 1, 3, 2 }, { 1, 2, 3, 1 }, { 1, 1, 1, 4 }, { 1, 3, 1, 2 }, { 1, 2, 1, 3 }, { 3, 1, 1, 2 } };
/*     */   private static final int TOTALBARS_EAN13 = 59;
/*     */   private static final int TOTALBARS_EAN8 = 43;
/*     */   private static final int TOTALBARS_UPCE = 33;
/*     */   private static final int TOTALBARS_SUPP2 = 13;
/*     */   private static final int TOTALBARS_SUPP5 = 31;
/*     */   private static final int ODD = 0;
/*     */   private static final int EVEN = 1;
/* 116 */   private static final byte[][] PARITY13 = { { 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 1, 1 }, { 0, 0, 1, 1, 0, 1 }, { 0, 0, 1, 1, 1, 0 }, { 0, 1, 0, 0, 1, 1 }, { 0, 1, 1, 0, 0, 1 }, { 0, 1, 1, 1, 0, 0 }, { 0, 1, 0, 1, 0, 1 }, { 0, 1, 0, 1, 1, 0 }, { 0, 1, 1, 0, 1, 0 } };
/*     */ 
/* 131 */   private static final byte[][] PARITY2 = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } };
/*     */ 
/* 140 */   private static final byte[][] PARITY5 = { { 1, 1, 0, 0, 0 }, { 1, 0, 1, 0, 0 }, { 1, 0, 0, 1, 0 }, { 1, 0, 0, 0, 1 }, { 0, 1, 1, 0, 0 }, { 0, 0, 1, 1, 0 }, { 0, 0, 0, 1, 1 }, { 0, 1, 0, 1, 0 }, { 0, 1, 0, 0, 1 }, { 0, 0, 1, 0, 1 } };
/*     */ 
/* 155 */   private static final byte[][] PARITYE = { { 1, 1, 1, 0, 0, 0 }, { 1, 1, 0, 1, 0, 0 }, { 1, 1, 0, 0, 1, 0 }, { 1, 1, 0, 0, 0, 1 }, { 1, 0, 1, 1, 0, 0 }, { 1, 0, 0, 1, 1, 0 }, { 1, 0, 0, 0, 1, 1 }, { 1, 0, 1, 0, 1, 0 }, { 1, 0, 1, 0, 0, 1 }, { 1, 0, 0, 1, 0, 1 } };
/*     */ 
/*     */   public BarcodeEAN()
/*     */   {
/*     */     try
/*     */     {
/* 172 */       this.x = 0.8F;
/* 173 */       this.font = BaseFont.createFont("Helvetica", "winansi", false);
/* 174 */       this.size = 8.0F;
/* 175 */       this.baseline = this.size;
/* 176 */       this.barHeight = (this.size * 3.0F);
/* 177 */       this.guardBars = true;
/* 178 */       this.codeType = 1;
/* 179 */       this.code = "";
/*     */     }
/*     */     catch (Exception e) {
/* 182 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static int calculateEANParity(String code)
/*     */   {
/* 191 */     int mul = 3;
/* 192 */     int total = 0;
/* 193 */     for (int k = code.length() - 1; k >= 0; k--) {
/* 194 */       int n = code.charAt(k) - '0';
/* 195 */       total += mul * n;
/* 196 */       mul ^= 2;
/*     */     }
/* 198 */     return (10 - total % 10) % 10;
/*     */   }
/*     */ 
/*     */   public static String convertUPCAtoUPCE(String text)
/*     */   {
/* 208 */     if ((text.length() != 12) || ((!text.startsWith("0")) && (!text.startsWith("1"))))
/* 209 */       return null;
/* 210 */     if ((text.substring(3, 6).equals("000")) || (text.substring(3, 6).equals("100")) || (text.substring(3, 6).equals("200")))
/*     */     {
/* 212 */       if (text.substring(6, 8).equals("00"))
/* 213 */         return text.substring(0, 1) + text.substring(1, 3) + text.substring(8, 11) + text.substring(3, 4) + text.substring(11);
/*     */     }
/* 215 */     else if (text.substring(4, 6).equals("00")) {
/* 216 */       if (text.substring(6, 9).equals("000"))
/* 217 */         return text.substring(0, 1) + text.substring(1, 4) + text.substring(9, 11) + "3" + text.substring(11);
/*     */     }
/* 219 */     else if (text.substring(5, 6).equals("0")) {
/* 220 */       if (text.substring(6, 10).equals("0000"))
/* 221 */         return text.substring(0, 1) + text.substring(1, 5) + text.substring(10, 11) + "4" + text.substring(11);
/*     */     }
/* 223 */     else if ((text.charAt(10) >= '5') && 
/* 224 */       (text.substring(6, 10).equals("0000"))) {
/* 225 */       return text.substring(0, 1) + text.substring(1, 6) + text.substring(10, 11) + text.substring(11);
/*     */     }
/* 227 */     return null;
/*     */   }
/*     */ 
/*     */   public static byte[] getBarsEAN13(String _code)
/*     */   {
/* 235 */     int[] code = new int[_code.length()];
/* 236 */     for (int k = 0; k < code.length; k++)
/* 237 */       code[k] = (_code.charAt(k) - '0');
/* 238 */     byte[] bars = new byte[59];
/* 239 */     int pb = 0;
/* 240 */     bars[(pb++)] = 1;
/* 241 */     bars[(pb++)] = 1;
/* 242 */     bars[(pb++)] = 1;
/* 243 */     byte[] sequence = PARITY13[code[0]];
/* 244 */     for (int k = 0; k < sequence.length; k++) {
/* 245 */       int c = code[(k + 1)];
/* 246 */       byte[] stripes = BARS[c];
/* 247 */       if (sequence[k] == 0) {
/* 248 */         bars[(pb++)] = stripes[0];
/* 249 */         bars[(pb++)] = stripes[1];
/* 250 */         bars[(pb++)] = stripes[2];
/* 251 */         bars[(pb++)] = stripes[3];
/*     */       }
/*     */       else {
/* 254 */         bars[(pb++)] = stripes[3];
/* 255 */         bars[(pb++)] = stripes[2];
/* 256 */         bars[(pb++)] = stripes[1];
/* 257 */         bars[(pb++)] = stripes[0];
/*     */       }
/*     */     }
/* 260 */     bars[(pb++)] = 1;
/* 261 */     bars[(pb++)] = 1;
/* 262 */     bars[(pb++)] = 1;
/* 263 */     bars[(pb++)] = 1;
/* 264 */     bars[(pb++)] = 1;
/* 265 */     for (int k = 7; k < 13; k++) {
/* 266 */       int c = code[k];
/* 267 */       byte[] stripes = BARS[c];
/* 268 */       bars[(pb++)] = stripes[0];
/* 269 */       bars[(pb++)] = stripes[1];
/* 270 */       bars[(pb++)] = stripes[2];
/* 271 */       bars[(pb++)] = stripes[3];
/*     */     }
/* 273 */     bars[(pb++)] = 1;
/* 274 */     bars[(pb++)] = 1;
/* 275 */     bars[(pb++)] = 1;
/* 276 */     return bars;
/*     */   }
/*     */ 
/*     */   public static byte[] getBarsEAN8(String _code)
/*     */   {
/* 284 */     int[] code = new int[_code.length()];
/* 285 */     for (int k = 0; k < code.length; k++)
/* 286 */       code[k] = (_code.charAt(k) - '0');
/* 287 */     byte[] bars = new byte[43];
/* 288 */     int pb = 0;
/* 289 */     bars[(pb++)] = 1;
/* 290 */     bars[(pb++)] = 1;
/* 291 */     bars[(pb++)] = 1;
/* 292 */     for (int k = 0; k < 4; k++) {
/* 293 */       int c = code[k];
/* 294 */       byte[] stripes = BARS[c];
/* 295 */       bars[(pb++)] = stripes[0];
/* 296 */       bars[(pb++)] = stripes[1];
/* 297 */       bars[(pb++)] = stripes[2];
/* 298 */       bars[(pb++)] = stripes[3];
/*     */     }
/* 300 */     bars[(pb++)] = 1;
/* 301 */     bars[(pb++)] = 1;
/* 302 */     bars[(pb++)] = 1;
/* 303 */     bars[(pb++)] = 1;
/* 304 */     bars[(pb++)] = 1;
/* 305 */     for (int k = 4; k < 8; k++) {
/* 306 */       int c = code[k];
/* 307 */       byte[] stripes = BARS[c];
/* 308 */       bars[(pb++)] = stripes[0];
/* 309 */       bars[(pb++)] = stripes[1];
/* 310 */       bars[(pb++)] = stripes[2];
/* 311 */       bars[(pb++)] = stripes[3];
/*     */     }
/* 313 */     bars[(pb++)] = 1;
/* 314 */     bars[(pb++)] = 1;
/* 315 */     bars[(pb++)] = 1;
/* 316 */     return bars;
/*     */   }
/*     */ 
/*     */   public static byte[] getBarsUPCE(String _code)
/*     */   {
/* 324 */     int[] code = new int[_code.length()];
/* 325 */     for (int k = 0; k < code.length; k++)
/* 326 */       code[k] = (_code.charAt(k) - '0');
/* 327 */     byte[] bars = new byte[33];
/* 328 */     boolean flip = code[0] != 0;
/* 329 */     int pb = 0;
/* 330 */     bars[(pb++)] = 1;
/* 331 */     bars[(pb++)] = 1;
/* 332 */     bars[(pb++)] = 1;
/* 333 */     byte[] sequence = PARITYE[code[(code.length - 1)]];
/* 334 */     for (int k = 1; k < code.length - 1; k++) {
/* 335 */       int c = code[k];
/* 336 */       byte[] stripes = BARS[c];
/* 337 */       if (sequence[(k - 1)] == (flip ? 1 : 0)) {
/* 338 */         bars[(pb++)] = stripes[0];
/* 339 */         bars[(pb++)] = stripes[1];
/* 340 */         bars[(pb++)] = stripes[2];
/* 341 */         bars[(pb++)] = stripes[3];
/*     */       }
/*     */       else {
/* 344 */         bars[(pb++)] = stripes[3];
/* 345 */         bars[(pb++)] = stripes[2];
/* 346 */         bars[(pb++)] = stripes[1];
/* 347 */         bars[(pb++)] = stripes[0];
/*     */       }
/*     */     }
/* 350 */     bars[(pb++)] = 1;
/* 351 */     bars[(pb++)] = 1;
/* 352 */     bars[(pb++)] = 1;
/* 353 */     bars[(pb++)] = 1;
/* 354 */     bars[(pb++)] = 1;
/* 355 */     bars[(pb++)] = 1;
/* 356 */     return bars;
/*     */   }
/*     */ 
/*     */   public static byte[] getBarsSupplemental2(String _code)
/*     */   {
/* 364 */     int[] code = new int[2];
/* 365 */     for (int k = 0; k < code.length; k++)
/* 366 */       code[k] = (_code.charAt(k) - '0');
/* 367 */     byte[] bars = new byte[13];
/* 368 */     int pb = 0;
/* 369 */     int parity = (code[0] * 10 + code[1]) % 4;
/* 370 */     bars[(pb++)] = 1;
/* 371 */     bars[(pb++)] = 1;
/* 372 */     bars[(pb++)] = 2;
/* 373 */     byte[] sequence = PARITY2[parity];
/* 374 */     for (int k = 0; k < sequence.length; k++) {
/* 375 */       if (k == 1) {
/* 376 */         bars[(pb++)] = 1;
/* 377 */         bars[(pb++)] = 1;
/*     */       }
/* 379 */       int c = code[k];
/* 380 */       byte[] stripes = BARS[c];
/* 381 */       if (sequence[k] == 0) {
/* 382 */         bars[(pb++)] = stripes[0];
/* 383 */         bars[(pb++)] = stripes[1];
/* 384 */         bars[(pb++)] = stripes[2];
/* 385 */         bars[(pb++)] = stripes[3];
/*     */       }
/*     */       else {
/* 388 */         bars[(pb++)] = stripes[3];
/* 389 */         bars[(pb++)] = stripes[2];
/* 390 */         bars[(pb++)] = stripes[1];
/* 391 */         bars[(pb++)] = stripes[0];
/*     */       }
/*     */     }
/* 394 */     return bars;
/*     */   }
/*     */ 
/*     */   public static byte[] getBarsSupplemental5(String _code)
/*     */   {
/* 402 */     int[] code = new int[5];
/* 403 */     for (int k = 0; k < code.length; k++)
/* 404 */       code[k] = (_code.charAt(k) - '0');
/* 405 */     byte[] bars = new byte[31];
/* 406 */     int pb = 0;
/* 407 */     int parity = ((code[0] + code[2] + code[4]) * 3 + (code[1] + code[3]) * 9) % 10;
/* 408 */     bars[(pb++)] = 1;
/* 409 */     bars[(pb++)] = 1;
/* 410 */     bars[(pb++)] = 2;
/* 411 */     byte[] sequence = PARITY5[parity];
/* 412 */     for (int k = 0; k < sequence.length; k++) {
/* 413 */       if (k != 0) {
/* 414 */         bars[(pb++)] = 1;
/* 415 */         bars[(pb++)] = 1;
/*     */       }
/* 417 */       int c = code[k];
/* 418 */       byte[] stripes = BARS[c];
/* 419 */       if (sequence[k] == 0) {
/* 420 */         bars[(pb++)] = stripes[0];
/* 421 */         bars[(pb++)] = stripes[1];
/* 422 */         bars[(pb++)] = stripes[2];
/* 423 */         bars[(pb++)] = stripes[3];
/*     */       }
/*     */       else {
/* 426 */         bars[(pb++)] = stripes[3];
/* 427 */         bars[(pb++)] = stripes[2];
/* 428 */         bars[(pb++)] = stripes[1];
/* 429 */         bars[(pb++)] = stripes[0];
/*     */       }
/*     */     }
/* 432 */     return bars;
/*     */   }
/*     */ 
/*     */   public Rectangle getBarcodeSize()
/*     */   {
/* 440 */     float width = 0.0F;
/* 441 */     float height = this.barHeight;
/* 442 */     if (this.font != null) {
/* 443 */       if (this.baseline <= 0.0F)
/* 444 */         height += -this.baseline + this.size;
/*     */       else
/* 446 */         height += this.baseline - this.font.getFontDescriptor(3, this.size);
/*     */     }
/* 448 */     switch (this.codeType) {
/*     */     case 1:
/* 450 */       width = this.x * 95.0F;
/* 451 */       if (this.font != null)
/* 452 */         width += this.font.getWidthPoint(this.code.charAt(0), this.size); break;
/*     */     case 2:
/* 456 */       width = this.x * 67.0F;
/* 457 */       break;
/*     */     case 3:
/* 459 */       width = this.x * 95.0F;
/* 460 */       if (this.font != null)
/* 461 */         width += this.font.getWidthPoint(this.code.charAt(0), this.size) + this.font.getWidthPoint(this.code.charAt(11), this.size); break;
/*     */     case 4:
/* 465 */       width = this.x * 51.0F;
/* 466 */       if (this.font != null)
/* 467 */         width += this.font.getWidthPoint(this.code.charAt(0), this.size) + this.font.getWidthPoint(this.code.charAt(7), this.size); break;
/*     */     case 5:
/* 471 */       width = this.x * 20.0F;
/* 472 */       break;
/*     */     case 6:
/* 474 */       width = this.x * 47.0F;
/* 475 */       break;
/*     */     default:
/* 477 */       throw new RuntimeException(MessageLocalization.getComposedMessage("invalid.code.type", new Object[0]));
/*     */     }
/* 479 */     return new Rectangle(width, height);
/*     */   }
/*     */ 
/*     */   public Rectangle placeBarcode(PdfContentByte cb, BaseColor barColor, BaseColor textColor)
/*     */   {
/* 519 */     Rectangle rect = getBarcodeSize();
/* 520 */     float barStartX = 0.0F;
/* 521 */     float barStartY = 0.0F;
/* 522 */     float textStartY = 0.0F;
/* 523 */     if (this.font != null) {
/* 524 */       if (this.baseline <= 0.0F) {
/* 525 */         textStartY = this.barHeight - this.baseline;
/*     */       } else {
/* 527 */         textStartY = -this.font.getFontDescriptor(3, this.size);
/* 528 */         barStartY = textStartY + this.baseline;
/*     */       }
/*     */     }
/* 531 */     switch (this.codeType) {
/*     */     case 1:
/*     */     case 3:
/*     */     case 4:
/* 535 */       if (this.font != null)
/* 536 */         barStartX += this.font.getWidthPoint(this.code.charAt(0), this.size); break;
/*     */     case 2:
/*     */     }
/* 539 */     byte[] bars = null;
/* 540 */     int[] guard = GUARD_EMPTY;
/* 541 */     switch (this.codeType) {
/*     */     case 1:
/* 543 */       bars = getBarsEAN13(this.code);
/* 544 */       guard = GUARD_EAN13;
/* 545 */       break;
/*     */     case 2:
/* 547 */       bars = getBarsEAN8(this.code);
/* 548 */       guard = GUARD_EAN8;
/* 549 */       break;
/*     */     case 3:
/* 551 */       bars = getBarsEAN13("0" + this.code);
/* 552 */       guard = GUARD_UPCA;
/* 553 */       break;
/*     */     case 4:
/* 555 */       bars = getBarsUPCE(this.code);
/* 556 */       guard = GUARD_UPCE;
/* 557 */       break;
/*     */     case 5:
/* 559 */       bars = getBarsSupplemental2(this.code);
/* 560 */       break;
/*     */     case 6:
/* 562 */       bars = getBarsSupplemental5(this.code);
/*     */     }
/*     */ 
/* 565 */     float keepBarX = barStartX;
/* 566 */     boolean print = true;
/* 567 */     float gd = 0.0F;
/* 568 */     if ((this.font != null) && (this.baseline > 0.0F) && (this.guardBars)) {
/* 569 */       gd = this.baseline / 2.0F;
/*     */     }
/* 571 */     if (barColor != null)
/* 572 */       cb.setColorFill(barColor);
/* 573 */     for (int k = 0; k < bars.length; k++) {
/* 574 */       float w = bars[k] * this.x;
/* 575 */       if (print) {
/* 576 */         if (Arrays.binarySearch(guard, k) >= 0)
/* 577 */           cb.rectangle(barStartX, barStartY - gd, w - this.inkSpreading, this.barHeight + gd);
/*     */         else
/* 579 */           cb.rectangle(barStartX, barStartY, w - this.inkSpreading, this.barHeight);
/*     */       }
/* 581 */       print = !print;
/* 582 */       barStartX += w;
/*     */     }
/* 584 */     cb.fill();
/* 585 */     if (this.font != null) {
/* 586 */       if (textColor != null)
/* 587 */         cb.setColorFill(textColor);
/* 588 */       cb.beginText();
/* 589 */       cb.setFontAndSize(this.font, this.size);
/* 590 */       switch (this.codeType) {
/*     */       case 1:
/* 592 */         cb.setTextMatrix(0.0F, textStartY);
/* 593 */         cb.showText(this.code.substring(0, 1));
/* 594 */         for (int k = 1; k < 13; k++) {
/* 595 */           String c = this.code.substring(k, k + 1);
/* 596 */           float len = this.font.getWidthPoint(c, this.size);
/* 597 */           float pX = keepBarX + TEXTPOS_EAN13[(k - 1)] * this.x - len / 2.0F;
/* 598 */           cb.setTextMatrix(pX, textStartY);
/* 599 */           cb.showText(c);
/*     */         }
/* 601 */         break;
/*     */       case 2:
/* 603 */         for (int k = 0; k < 8; k++) {
/* 604 */           String c = this.code.substring(k, k + 1);
/* 605 */           float len = this.font.getWidthPoint(c, this.size);
/* 606 */           float pX = TEXTPOS_EAN8[k] * this.x - len / 2.0F;
/* 607 */           cb.setTextMatrix(pX, textStartY);
/* 608 */           cb.showText(c);
/*     */         }
/* 610 */         break;
/*     */       case 3:
/* 612 */         cb.setTextMatrix(0.0F, textStartY);
/* 613 */         cb.showText(this.code.substring(0, 1));
/* 614 */         for (int k = 1; k < 11; k++) {
/* 615 */           String c = this.code.substring(k, k + 1);
/* 616 */           float len = this.font.getWidthPoint(c, this.size);
/* 617 */           float pX = keepBarX + TEXTPOS_EAN13[k] * this.x - len / 2.0F;
/* 618 */           cb.setTextMatrix(pX, textStartY);
/* 619 */           cb.showText(c);
/*     */         }
/* 621 */         cb.setTextMatrix(keepBarX + this.x * 95.0F, textStartY);
/* 622 */         cb.showText(this.code.substring(11, 12));
/* 623 */         break;
/*     */       case 4:
/* 625 */         cb.setTextMatrix(0.0F, textStartY);
/* 626 */         cb.showText(this.code.substring(0, 1));
/* 627 */         for (int k = 1; k < 7; k++) {
/* 628 */           String c = this.code.substring(k, k + 1);
/* 629 */           float len = this.font.getWidthPoint(c, this.size);
/* 630 */           float pX = keepBarX + TEXTPOS_EAN13[(k - 1)] * this.x - len / 2.0F;
/* 631 */           cb.setTextMatrix(pX, textStartY);
/* 632 */           cb.showText(c);
/*     */         }
/* 634 */         cb.setTextMatrix(keepBarX + this.x * 51.0F, textStartY);
/* 635 */         cb.showText(this.code.substring(7, 8));
/* 636 */         break;
/*     */       case 5:
/*     */       case 6:
/* 639 */         for (int k = 0; k < this.code.length(); k++) {
/* 640 */           String c = this.code.substring(k, k + 1);
/* 641 */           float len = this.font.getWidthPoint(c, this.size);
/* 642 */           float pX = (7.5F + 9 * k) * this.x - len / 2.0F;
/* 643 */           cb.setTextMatrix(pX, textStartY);
/* 644 */           cb.showText(c);
/*     */         }
/*     */       }
/*     */ 
/* 648 */       cb.endText();
/*     */     }
/* 650 */     return rect;
/*     */   }
/*     */ 
/*     */   public Image createAwtImage(Color foreground, Color background)
/*     */   {
/* 662 */     int f = foreground.getRGB();
/* 663 */     int g = background.getRGB();
/* 664 */     Canvas canvas = new Canvas();
/*     */ 
/* 666 */     int width = 0;
/* 667 */     byte[] bars = null;
/* 668 */     switch (this.codeType) {
/*     */     case 1:
/* 670 */       bars = getBarsEAN13(this.code);
/* 671 */       width = 95;
/* 672 */       break;
/*     */     case 2:
/* 674 */       bars = getBarsEAN8(this.code);
/* 675 */       width = 67;
/* 676 */       break;
/*     */     case 3:
/* 678 */       bars = getBarsEAN13("0" + this.code);
/* 679 */       width = 95;
/* 680 */       break;
/*     */     case 4:
/* 682 */       bars = getBarsUPCE(this.code);
/* 683 */       width = 51;
/* 684 */       break;
/*     */     case 5:
/* 686 */       bars = getBarsSupplemental2(this.code);
/* 687 */       width = 20;
/* 688 */       break;
/*     */     case 6:
/* 690 */       bars = getBarsSupplemental5(this.code);
/* 691 */       width = 47;
/* 692 */       break;
/*     */     default:
/* 694 */       throw new RuntimeException(MessageLocalization.getComposedMessage("invalid.code.type", new Object[0]));
/*     */     }
/*     */ 
/* 697 */     boolean print = true;
/* 698 */     int ptr = 0;
/* 699 */     int height = (int)this.barHeight;
/* 700 */     int[] pix = new int[width * height];
/* 701 */     for (int k = 0; k < bars.length; k++) {
/* 702 */       int w = bars[k];
/* 703 */       int c = g;
/* 704 */       if (print)
/* 705 */         c = f;
/* 706 */       print = !print;
/* 707 */       for (int j = 0; j < w; j++)
/* 708 */         pix[(ptr++)] = c;
/*     */     }
/* 710 */     for (int k = width; k < pix.length; k += width) {
/* 711 */       System.arraycopy(pix, 0, pix, k, width);
/*     */     }
/* 713 */     Image img = canvas.createImage(new MemoryImageSource(width, height, pix, 0, width));
/*     */ 
/* 715 */     return img;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.BarcodeEAN
 * JD-Core Version:    0.6.2
 */