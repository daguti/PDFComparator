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
/*     */ public class Barcode128 extends Barcode
/*     */ {
/*  78 */   private static final byte[][] BARS = { { 2, 1, 2, 2, 2, 2 }, { 2, 2, 2, 1, 2, 2 }, { 2, 2, 2, 2, 2, 1 }, { 1, 2, 1, 2, 2, 3 }, { 1, 2, 1, 3, 2, 2 }, { 1, 3, 1, 2, 2, 2 }, { 1, 2, 2, 2, 1, 3 }, { 1, 2, 2, 3, 1, 2 }, { 1, 3, 2, 2, 1, 2 }, { 2, 2, 1, 2, 1, 3 }, { 2, 2, 1, 3, 1, 2 }, { 2, 3, 1, 2, 1, 2 }, { 1, 1, 2, 2, 3, 2 }, { 1, 2, 2, 1, 3, 2 }, { 1, 2, 2, 2, 3, 1 }, { 1, 1, 3, 2, 2, 2 }, { 1, 2, 3, 1, 2, 2 }, { 1, 2, 3, 2, 2, 1 }, { 2, 2, 3, 2, 1, 1 }, { 2, 2, 1, 1, 3, 2 }, { 2, 2, 1, 2, 3, 1 }, { 2, 1, 3, 2, 1, 2 }, { 2, 2, 3, 1, 1, 2 }, { 3, 1, 2, 1, 3, 1 }, { 3, 1, 1, 2, 2, 2 }, { 3, 2, 1, 1, 2, 2 }, { 3, 2, 1, 2, 2, 1 }, { 3, 1, 2, 2, 1, 2 }, { 3, 2, 2, 1, 1, 2 }, { 3, 2, 2, 2, 1, 1 }, { 2, 1, 2, 1, 2, 3 }, { 2, 1, 2, 3, 2, 1 }, { 2, 3, 2, 1, 2, 1 }, { 1, 1, 1, 3, 2, 3 }, { 1, 3, 1, 1, 2, 3 }, { 1, 3, 1, 3, 2, 1 }, { 1, 1, 2, 3, 1, 3 }, { 1, 3, 2, 1, 1, 3 }, { 1, 3, 2, 3, 1, 1 }, { 2, 1, 1, 3, 1, 3 }, { 2, 3, 1, 1, 1, 3 }, { 2, 3, 1, 3, 1, 1 }, { 1, 1, 2, 1, 3, 3 }, { 1, 1, 2, 3, 3, 1 }, { 1, 3, 2, 1, 3, 1 }, { 1, 1, 3, 1, 2, 3 }, { 1, 1, 3, 3, 2, 1 }, { 1, 3, 3, 1, 2, 1 }, { 3, 1, 3, 1, 2, 1 }, { 2, 1, 1, 3, 3, 1 }, { 2, 3, 1, 1, 3, 1 }, { 2, 1, 3, 1, 1, 3 }, { 2, 1, 3, 3, 1, 1 }, { 2, 1, 3, 1, 3, 1 }, { 3, 1, 1, 1, 2, 3 }, { 3, 1, 1, 3, 2, 1 }, { 3, 3, 1, 1, 2, 1 }, { 3, 1, 2, 1, 1, 3 }, { 3, 1, 2, 3, 1, 1 }, { 3, 3, 2, 1, 1, 1 }, { 3, 1, 4, 1, 1, 1 }, { 2, 2, 1, 4, 1, 1 }, { 4, 3, 1, 1, 1, 1 }, { 1, 1, 1, 2, 2, 4 }, { 1, 1, 1, 4, 2, 2 }, { 1, 2, 1, 1, 2, 4 }, { 1, 2, 1, 4, 2, 1 }, { 1, 4, 1, 1, 2, 2 }, { 1, 4, 1, 2, 2, 1 }, { 1, 1, 2, 2, 1, 4 }, { 1, 1, 2, 4, 1, 2 }, { 1, 2, 2, 1, 1, 4 }, { 1, 2, 2, 4, 1, 1 }, { 1, 4, 2, 1, 1, 2 }, { 1, 4, 2, 2, 1, 1 }, { 2, 4, 1, 2, 1, 1 }, { 2, 2, 1, 1, 1, 4 }, { 4, 1, 3, 1, 1, 1 }, { 2, 4, 1, 1, 1, 2 }, { 1, 3, 4, 1, 1, 1 }, { 1, 1, 1, 2, 4, 2 }, { 1, 2, 1, 1, 4, 2 }, { 1, 2, 1, 2, 4, 1 }, { 1, 1, 4, 2, 1, 2 }, { 1, 2, 4, 1, 1, 2 }, { 1, 2, 4, 2, 1, 1 }, { 4, 1, 1, 2, 1, 2 }, { 4, 2, 1, 1, 1, 2 }, { 4, 2, 1, 2, 1, 1 }, { 2, 1, 2, 1, 4, 1 }, { 2, 1, 4, 1, 2, 1 }, { 4, 1, 2, 1, 2, 1 }, { 1, 1, 1, 1, 4, 3 }, { 1, 1, 1, 3, 4, 1 }, { 1, 3, 1, 1, 4, 1 }, { 1, 1, 4, 1, 1, 3 }, { 1, 1, 4, 3, 1, 1 }, { 4, 1, 1, 1, 1, 3 }, { 4, 1, 1, 3, 1, 1 }, { 1, 1, 3, 1, 4, 1 }, { 1, 1, 4, 1, 3, 1 }, { 3, 1, 1, 1, 4, 1 }, { 4, 1, 1, 1, 3, 1 }, { 2, 1, 1, 4, 1, 2 }, { 2, 1, 1, 2, 1, 4 }, { 2, 1, 1, 2, 3, 2 } };
/*     */ 
/* 190 */   private static final byte[] BARS_STOP = { 2, 3, 3, 1, 1, 1, 2 };
/*     */   public static final char CODE_AB_TO_C = 'c';
/*     */   public static final char CODE_AC_TO_B = 'd';
/*     */   public static final char CODE_BC_TO_A = 'e';
/*     */   public static final char FNC1_INDEX = 'f';
/*     */   public static final char START_A = 'g';
/*     */   public static final char START_B = 'h';
/*     */   public static final char START_C = 'i';
/*     */   public static final char FNC1 = 'Ê';
/*     */   public static final char DEL = 'Ã';
/*     */   public static final char FNC3 = 'Ä';
/*     */   public static final char FNC2 = 'Å';
/*     */   public static final char SHIFT = 'Æ';
/*     */   public static final char CODE_C = 'Ç';
/*     */   public static final char CODE_A = 'È';
/*     */   public static final char FNC4 = 'È';
/*     */   public static final char STARTA = 'Ë';
/*     */   public static final char STARTB = 'Ì';
/*     */   public static final char STARTC = 'Í';
/* 225 */   private static final IntHashtable ais = new IntHashtable();
/*     */ 
/*     */   public Barcode128() {
/*     */     try {
/* 229 */       this.x = 0.8F;
/* 230 */       this.font = BaseFont.createFont("Helvetica", "winansi", false);
/* 231 */       this.size = 8.0F;
/* 232 */       this.baseline = this.size;
/* 233 */       this.barHeight = (this.size * 3.0F);
/* 234 */       this.textAlignment = 1;
/* 235 */       this.codeType = 9;
/*     */     }
/*     */     catch (Exception e) {
/* 238 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String removeFNC1(String code)
/*     */   {
/* 248 */     int len = code.length();
/* 249 */     StringBuffer buf = new StringBuffer(len);
/* 250 */     for (int k = 0; k < len; k++) {
/* 251 */       char c = code.charAt(k);
/* 252 */       if ((c >= ' ') && (c <= '~'))
/* 253 */         buf.append(c);
/*     */     }
/* 255 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public static String getHumanReadableUCCEAN(String code)
/*     */   {
/* 264 */     StringBuffer buf = new StringBuffer();
/* 265 */     String fnc1 = String.valueOf('Ê');
/*     */     try {
/*     */       while (true)
/* 268 */         if (code.startsWith(fnc1)) {
/* 269 */           code = code.substring(1);
/*     */         }
/*     */         else {
/* 272 */           int n = 0;
/* 273 */           int idlen = 0;
/* 274 */           for (int k = 2; (k < 5) && 
/* 275 */             (code.length() >= k); k++)
/*     */           {
/* 277 */             if ((n = ais.get(Integer.parseInt(code.substring(0, k)))) != 0) {
/* 278 */               idlen = k;
/* 279 */               break;
/*     */             }
/*     */           }
/* 282 */           if (idlen == 0)
/*     */             break;
/* 284 */           buf.append('(').append(code.substring(0, idlen)).append(')');
/* 285 */           code = code.substring(idlen);
/* 286 */           if (n > 0) {
/* 287 */             n -= idlen;
/* 288 */             if (code.length() <= n)
/*     */               break;
/* 290 */             buf.append(removeFNC1(code.substring(0, n)));
/* 291 */             code = code.substring(n);
/*     */           }
/*     */           else {
/* 294 */             int idx = code.indexOf('Ê');
/* 295 */             if (idx < 0)
/*     */               break;
/* 297 */             buf.append(code.substring(0, idx));
/* 298 */             code = code.substring(idx + 1);
/*     */           }
/*     */         }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/* 305 */     buf.append(removeFNC1(code));
/* 306 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   static boolean isNextDigits(String text, int textIndex, int numDigits)
/*     */   {
/* 317 */     int len = text.length();
/* 318 */     while ((textIndex < len) && (numDigits > 0))
/* 319 */       if (text.charAt(textIndex) == 'Ê') {
/* 320 */         textIndex++;
/*     */       }
/*     */       else {
/* 323 */         int n = Math.min(2, numDigits);
/* 324 */         if (textIndex + n > len)
/* 325 */           return false;
/* 326 */         while (n-- > 0) {
/* 327 */           char c = text.charAt(textIndex++);
/* 328 */           if ((c < '0') || (c > '9'))
/* 329 */             return false;
/* 330 */           numDigits--;
/*     */         }
/*     */       }
/* 333 */     return numDigits == 0;
/*     */   }
/*     */ 
/*     */   static String getPackedRawDigits(String text, int textIndex, int numDigits)
/*     */   {
/* 344 */     StringBuilder out = new StringBuilder("");
/* 345 */     int start = textIndex;
/* 346 */     while (numDigits > 0)
/* 347 */       if (text.charAt(textIndex) == 'Ê') {
/* 348 */         out.append('f');
/* 349 */         textIndex++;
/*     */       }
/*     */       else {
/* 352 */         numDigits -= 2;
/* 353 */         int c1 = text.charAt(textIndex++) - '0';
/* 354 */         int c2 = text.charAt(textIndex++) - '0';
/* 355 */         out.append((char)(c1 * 10 + c2));
/*     */       }
/* 357 */     return (char)(textIndex - start) + out.toString();
/*     */   }
/*     */ 
/*     */   public static String getRawText(String text, boolean ucc)
/*     */   {
/* 368 */     String out = "";
/* 369 */     int tLen = text.length();
/* 370 */     if (tLen == 0) {
/* 371 */       out = out + 'h';
/* 372 */       if (ucc)
/* 373 */         out = out + 'f';
/* 374 */       return out;
/*     */     }
/* 376 */     int c = 0;
/* 377 */     for (int k = 0; k < tLen; k++) {
/* 378 */       c = text.charAt(k);
/* 379 */       if ((c > 127) && (c != 202))
/* 380 */         throw new RuntimeException(MessageLocalization.getComposedMessage("there.are.illegal.characters.for.barcode.128.in.1", new Object[] { text }));
/*     */     }
/* 382 */     c = text.charAt(0);
/* 383 */     char currentCode = 'h';
/* 384 */     int index = 0;
/* 385 */     if (isNextDigits(text, index, 2)) {
/* 386 */       currentCode = 'i';
/* 387 */       out = out + currentCode;
/* 388 */       if (ucc)
/* 389 */         out = out + 'f';
/* 390 */       String out2 = getPackedRawDigits(text, index, 2);
/* 391 */       index += out2.charAt(0);
/* 392 */       out = out + out2.substring(1);
/*     */     }
/* 394 */     else if (c < 32) {
/* 395 */       currentCode = 'g';
/* 396 */       out = out + currentCode;
/* 397 */       if (ucc)
/* 398 */         out = out + 'f';
/* 399 */       out = out + (char)(c + 64);
/* 400 */       index++;
/*     */     }
/*     */     else {
/* 403 */       out = out + currentCode;
/* 404 */       if (ucc)
/* 405 */         out = out + 'f';
/* 406 */       if (c == 202)
/* 407 */         out = out + 'f';
/*     */       else
/* 409 */         out = out + (char)(c - 32);
/* 410 */       index++;
/*     */     }
/* 412 */     while (index < tLen) {
/* 413 */       switch (currentCode)
/*     */       {
/*     */       case 'g':
/* 416 */         if (isNextDigits(text, index, 4)) {
/* 417 */           currentCode = 'i';
/* 418 */           out = out + 'c';
/* 419 */           String out2 = getPackedRawDigits(text, index, 4);
/* 420 */           index += out2.charAt(0);
/* 421 */           out = out + out2.substring(1);
/*     */         }
/*     */         else {
/* 424 */           c = text.charAt(index++);
/* 425 */           if (c == 202) {
/* 426 */             out = out + 'f';
/* 427 */           } else if (c > 95) {
/* 428 */             currentCode = 'h';
/* 429 */             out = out + 'd';
/* 430 */             out = out + (char)(c - 32);
/*     */           }
/* 432 */           else if (c < 32) {
/* 433 */             out = out + (char)(c + 64);
/*     */           } else {
/* 435 */             out = out + (char)(c - 32);
/*     */           }
/*     */         }
/* 438 */         break;
/*     */       case 'h':
/* 441 */         if (isNextDigits(text, index, 4)) {
/* 442 */           currentCode = 'i';
/* 443 */           out = out + 'c';
/* 444 */           String out2 = getPackedRawDigits(text, index, 4);
/* 445 */           index += out2.charAt(0);
/* 446 */           out = out + out2.substring(1);
/*     */         }
/*     */         else {
/* 449 */           c = text.charAt(index++);
/* 450 */           if (c == 202) {
/* 451 */             out = out + 'f';
/* 452 */           } else if (c < 32) {
/* 453 */             currentCode = 'g';
/* 454 */             out = out + 'e';
/* 455 */             out = out + (char)(c + 64);
/*     */           }
/*     */           else {
/* 458 */             out = out + (char)(c - 32);
/*     */           }
/*     */         }
/*     */ 
/* 462 */         break;
/*     */       case 'i':
/* 465 */         if (isNextDigits(text, index, 2)) {
/* 466 */           String out2 = getPackedRawDigits(text, index, 2);
/* 467 */           index += out2.charAt(0);
/* 468 */           out = out + out2.substring(1);
/*     */         }
/*     */         else {
/* 471 */           c = text.charAt(index++);
/* 472 */           if (c == 202) {
/* 473 */             out = out + 'f';
/* 474 */           } else if (c < 32) {
/* 475 */             currentCode = 'g';
/* 476 */             out = out + 'e';
/* 477 */             out = out + (char)(c + 64);
/*     */           }
/*     */           else {
/* 480 */             currentCode = 'h';
/* 481 */             out = out + 'd';
/* 482 */             out = out + (char)(c - 32);
/*     */           }
/*     */         }
/*     */         break;
/*     */       }
/*     */     }
/*     */ 
/* 489 */     return out;
/*     */   }
/*     */ 
/*     */   public static byte[] getBarsCode128Raw(String text)
/*     */   {
/* 498 */     int idx = text.indexOf(65535);
/* 499 */     if (idx >= 0)
/* 500 */       text = text.substring(0, idx);
/* 501 */     int chk = text.charAt(0);
/* 502 */     for (int k = 1; k < text.length(); k++)
/* 503 */       chk += k * text.charAt(k);
/* 504 */     chk %= 103;
/* 505 */     text = text + (char)chk;
/* 506 */     byte[] bars = new byte[(text.length() + 1) * 6 + 7];
/*     */ 
/* 508 */     for (int k = 0; k < text.length(); k++)
/* 509 */       System.arraycopy(BARS[text.charAt(k)], 0, bars, k * 6, 6);
/* 510 */     System.arraycopy(BARS_STOP, 0, bars, k * 6, 7);
/* 511 */     return bars;
/*     */   }
/*     */ 
/*     */   public Rectangle getBarcodeSize()
/*     */   {
/* 519 */     float fontX = 0.0F;
/* 520 */     float fontY = 0.0F;
/*     */ 
/* 522 */     if (this.font != null) {
/* 523 */       if (this.baseline > 0.0F)
/* 524 */         fontY = this.baseline - this.font.getFontDescriptor(3, this.size);
/*     */       else
/* 526 */         fontY = -this.baseline + this.size;
/*     */       String fullCode;
/*     */       String fullCode;
/* 527 */       if (this.codeType == 11) {
/* 528 */         int idx = this.code.indexOf(65535);
/*     */         String fullCode;
/* 529 */         if (idx < 0)
/* 530 */           fullCode = "";
/*     */         else
/* 532 */           fullCode = this.code.substring(idx + 1);
/*     */       }
/*     */       else
/*     */       {
/*     */         String fullCode;
/* 534 */         if (this.codeType == 10)
/* 535 */           fullCode = getHumanReadableUCCEAN(this.code);
/*     */         else
/* 537 */           fullCode = removeFNC1(this.code); 
/*     */       }
/* 538 */       fontX = this.font.getWidthPoint(this.altText != null ? this.altText : fullCode, this.size);
/*     */     }
/*     */     String fullCode;
/*     */     String fullCode;
/* 540 */     if (this.codeType == 11) {
/* 541 */       int idx = this.code.indexOf(65535);
/*     */       String fullCode;
/* 542 */       if (idx >= 0)
/* 543 */         fullCode = this.code.substring(0, idx);
/*     */       else
/* 545 */         fullCode = this.code;
/*     */     }
/*     */     else {
/* 548 */       fullCode = getRawText(this.code, this.codeType == 10);
/*     */     }
/* 550 */     int len = fullCode.length();
/* 551 */     float fullWidth = (len + 2) * 11 * this.x + 2.0F * this.x;
/* 552 */     fullWidth = Math.max(fullWidth, fontX);
/* 553 */     float fullHeight = this.barHeight + fontY;
/* 554 */     return new Rectangle(fullWidth, fullHeight);
/*     */   }
/*     */ 
/*     */   public Rectangle placeBarcode(PdfContentByte cb, BaseColor barColor, BaseColor textColor)
/*     */   {
/*     */     String fullCode;
/*     */     String fullCode;
/* 595 */     if (this.codeType == 11) {
/* 596 */       int idx = this.code.indexOf(65535);
/*     */       String fullCode;
/* 597 */       if (idx < 0)
/* 598 */         fullCode = "";
/*     */       else
/* 600 */         fullCode = this.code.substring(idx + 1);
/*     */     }
/*     */     else
/*     */     {
/*     */       String fullCode;
/* 602 */       if (this.codeType == 10)
/* 603 */         fullCode = getHumanReadableUCCEAN(this.code);
/*     */       else
/* 605 */         fullCode = removeFNC1(this.code); 
/*     */     }
/* 606 */     float fontX = 0.0F;
/* 607 */     if (this.font != null)
/* 608 */       fontX = this.font.getWidthPoint(fullCode = this.altText != null ? this.altText : fullCode, this.size);
/*     */     String bCode;
/*     */     String bCode;
/* 611 */     if (this.codeType == 11) {
/* 612 */       int idx = this.code.indexOf(65535);
/*     */       String bCode;
/* 613 */       if (idx >= 0)
/* 614 */         bCode = this.code.substring(0, idx);
/*     */       else
/* 616 */         bCode = this.code;
/*     */     }
/*     */     else {
/* 619 */       bCode = getRawText(this.code, this.codeType == 10);
/*     */     }
/* 621 */     int len = bCode.length();
/* 622 */     float fullWidth = (len + 2) * 11 * this.x + 2.0F * this.x;
/* 623 */     float barStartX = 0.0F;
/* 624 */     float textStartX = 0.0F;
/* 625 */     switch (this.textAlignment) {
/*     */     case 0:
/* 627 */       break;
/*     */     case 2:
/* 629 */       if (fontX > fullWidth)
/* 630 */         barStartX = fontX - fullWidth;
/*     */       else
/* 632 */         textStartX = fullWidth - fontX;
/* 633 */       break;
/*     */     default:
/* 635 */       if (fontX > fullWidth)
/* 636 */         barStartX = (fontX - fullWidth) / 2.0F;
/*     */       else
/* 638 */         textStartX = (fullWidth - fontX) / 2.0F;
/*     */       break;
/*     */     }
/* 641 */     float barStartY = 0.0F;
/* 642 */     float textStartY = 0.0F;
/* 643 */     if (this.font != null) {
/* 644 */       if (this.baseline <= 0.0F) {
/* 645 */         textStartY = this.barHeight - this.baseline;
/*     */       } else {
/* 647 */         textStartY = -this.font.getFontDescriptor(3, this.size);
/* 648 */         barStartY = textStartY + this.baseline;
/*     */       }
/*     */     }
/* 651 */     byte[] bars = getBarsCode128Raw(bCode);
/* 652 */     boolean print = true;
/* 653 */     if (barColor != null)
/* 654 */       cb.setColorFill(barColor);
/* 655 */     for (int k = 0; k < bars.length; k++) {
/* 656 */       float w = bars[k] * this.x;
/* 657 */       if (print)
/* 658 */         cb.rectangle(barStartX, barStartY, w - this.inkSpreading, this.barHeight);
/* 659 */       print = !print;
/* 660 */       barStartX += w;
/*     */     }
/* 662 */     cb.fill();
/* 663 */     if (this.font != null) {
/* 664 */       if (textColor != null)
/* 665 */         cb.setColorFill(textColor);
/* 666 */       cb.beginText();
/* 667 */       cb.setFontAndSize(this.font, this.size);
/* 668 */       cb.setTextMatrix(textStartX, textStartY);
/* 669 */       cb.showText(fullCode);
/* 670 */       cb.endText();
/*     */     }
/* 672 */     return getBarcodeSize();
/*     */   }
/*     */ 
/*     */   public void setCode(String code)
/*     */   {
/* 683 */     if ((getCodeType() == 10) && (code.startsWith("("))) {
/* 684 */       int idx = 0;
/* 685 */       StringBuilder ret = new StringBuilder("");
/* 686 */       while (idx >= 0) {
/* 687 */         int end = code.indexOf(')', idx);
/* 688 */         if (end < 0)
/* 689 */           throw new IllegalArgumentException(MessageLocalization.getComposedMessage("badly.formed.ucc.string.1", new Object[] { code }));
/* 690 */         String sai = code.substring(idx + 1, end);
/* 691 */         if (sai.length() < 2)
/* 692 */           throw new IllegalArgumentException(MessageLocalization.getComposedMessage("ai.too.short.1", new Object[] { sai }));
/* 693 */         int ai = Integer.parseInt(sai);
/* 694 */         int len = ais.get(ai);
/* 695 */         if (len == 0)
/* 696 */           throw new IllegalArgumentException(MessageLocalization.getComposedMessage("ai.not.found.1", new Object[] { sai }));
/* 697 */         sai = String.valueOf(ai);
/* 698 */         if (sai.length() == 1)
/* 699 */           sai = "0" + sai;
/* 700 */         idx = code.indexOf('(', end);
/* 701 */         int next = idx < 0 ? code.length() : idx;
/* 702 */         ret.append(sai).append(code.substring(end + 1, next));
/* 703 */         if (len < 0) {
/* 704 */           if (idx >= 0)
/* 705 */             ret.append('Ê');
/*     */         }
/* 707 */         else if (next - end - 1 + sai.length() != len)
/* 708 */           throw new IllegalArgumentException(MessageLocalization.getComposedMessage("invalid.ai.length.1", new Object[] { sai }));
/*     */       }
/* 710 */       super.setCode(ret.toString());
/*     */     }
/*     */     else {
/* 713 */       super.setCode(code);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Image createAwtImage(Color foreground, Color background)
/*     */   {
/* 784 */     int f = foreground.getRGB();
/* 785 */     int g = background.getRGB();
/* 786 */     Canvas canvas = new Canvas();
/*     */     String bCode;
/*     */     String bCode;
/* 788 */     if (this.codeType == 11) {
/* 789 */       int idx = this.code.indexOf(65535);
/*     */       String bCode;
/* 790 */       if (idx >= 0)
/* 791 */         bCode = this.code.substring(0, idx);
/*     */       else
/* 793 */         bCode = this.code;
/*     */     }
/*     */     else {
/* 796 */       bCode = getRawText(this.code, this.codeType == 10);
/*     */     }
/* 798 */     int len = bCode.length();
/* 799 */     int fullWidth = (len + 2) * 11 + 2;
/* 800 */     byte[] bars = getBarsCode128Raw(bCode);
/*     */ 
/* 802 */     boolean print = true;
/* 803 */     int ptr = 0;
/* 804 */     int height = (int)this.barHeight;
/* 805 */     int[] pix = new int[fullWidth * height];
/* 806 */     for (int k = 0; k < bars.length; k++) {
/* 807 */       int w = bars[k];
/* 808 */       int c = g;
/* 809 */       if (print)
/* 810 */         c = f;
/* 811 */       print = !print;
/* 812 */       for (int j = 0; j < w; j++)
/* 813 */         pix[(ptr++)] = c;
/*     */     }
/* 815 */     for (int k = fullWidth; k < pix.length; k += fullWidth) {
/* 816 */       System.arraycopy(pix, 0, pix, k, fullWidth);
/*     */     }
/* 818 */     Image img = canvas.createImage(new MemoryImageSource(fullWidth, height, pix, 0, fullWidth));
/*     */ 
/* 820 */     return img;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 717 */     ais.put(0, 20);
/* 718 */     ais.put(1, 16);
/* 719 */     ais.put(2, 16);
/* 720 */     ais.put(10, -1);
/* 721 */     ais.put(11, 9);
/* 722 */     ais.put(12, 8);
/* 723 */     ais.put(13, 8);
/* 724 */     ais.put(15, 8);
/* 725 */     ais.put(17, 8);
/* 726 */     ais.put(20, 4);
/* 727 */     ais.put(21, -1);
/* 728 */     ais.put(22, -1);
/* 729 */     ais.put(23, -1);
/* 730 */     ais.put(240, -1);
/* 731 */     ais.put(241, -1);
/* 732 */     ais.put(250, -1);
/* 733 */     ais.put(251, -1);
/* 734 */     ais.put(252, -1);
/* 735 */     ais.put(30, -1);
/* 736 */     for (int k = 3100; k < 3700; k++)
/* 737 */       ais.put(k, 10);
/* 738 */     ais.put(37, -1);
/* 739 */     for (int k = 3900; k < 3940; k++)
/* 740 */       ais.put(k, -1);
/* 741 */     ais.put(400, -1);
/* 742 */     ais.put(401, -1);
/* 743 */     ais.put(402, 20);
/* 744 */     ais.put(403, -1);
/* 745 */     for (int k = 410; k < 416; k++)
/* 746 */       ais.put(k, 16);
/* 747 */     ais.put(420, -1);
/* 748 */     ais.put(421, -1);
/* 749 */     ais.put(422, 6);
/* 750 */     ais.put(423, -1);
/* 751 */     ais.put(424, 6);
/* 752 */     ais.put(425, 6);
/* 753 */     ais.put(426, 6);
/* 754 */     ais.put(7001, 17);
/* 755 */     ais.put(7002, -1);
/* 756 */     for (int k = 7030; k < 7040; k++)
/* 757 */       ais.put(k, -1);
/* 758 */     ais.put(8001, 18);
/* 759 */     ais.put(8002, -1);
/* 760 */     ais.put(8003, -1);
/* 761 */     ais.put(8004, -1);
/* 762 */     ais.put(8005, 10);
/* 763 */     ais.put(8006, 22);
/* 764 */     ais.put(8007, -1);
/* 765 */     ais.put(8008, -1);
/* 766 */     ais.put(8018, 22);
/* 767 */     ais.put(8020, -1);
/* 768 */     ais.put(8100, 10);
/* 769 */     ais.put(8101, 14);
/* 770 */     ais.put(8102, 6);
/* 771 */     for (int k = 90; k < 100; k++)
/* 772 */       ais.put(k, -1);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.Barcode128
 * JD-Core Version:    0.6.2
 */