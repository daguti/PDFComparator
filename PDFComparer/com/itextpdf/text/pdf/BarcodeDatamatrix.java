/*      */ package com.itextpdf.text.pdf;
/*      */ 
/*      */ import com.itextpdf.text.BadElementException;
/*      */ import com.itextpdf.text.pdf.codec.CCITTG4Encoder;
/*      */ import java.awt.Canvas;
/*      */ import java.awt.Color;
/*      */ import java.awt.image.MemoryImageSource;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.util.Arrays;
/*      */ import java.util.Hashtable;
/*      */ 
/*      */ public class BarcodeDatamatrix
/*      */ {
/*      */   public static final int DM_NO_ERROR = 0;
/*      */   public static final int DM_ERROR_TEXT_TOO_BIG = 1;
/*      */   public static final int DM_ERROR_INVALID_SQUARE = 3;
/*      */   public static final int DM_ERROR_EXTENSION = 5;
/*      */   public static final int DM_AUTO = 0;
/*      */   public static final int DM_ASCII = 1;
/*      */   public static final int DM_C40 = 2;
/*      */   public static final int DM_TEXT = 3;
/*      */   public static final int DM_B256 = 4;
/*      */   public static final int DM_X21 = 5;
/*      */   public static final int DM_EDIFACT = 6;
/*      */   public static final int DM_RAW = 7;
/*      */   public static final int DM_EXTENSION = 32;
/*      */   public static final int DM_TEST = 64;
/*  118 */   private static final DmParams[] dmSizes = { new DmParams(10, 10, 10, 10, 3, 3, 5), new DmParams(12, 12, 12, 12, 5, 5, 7), new DmParams(8, 18, 8, 18, 5, 5, 7), new DmParams(14, 14, 14, 14, 8, 8, 10), new DmParams(8, 32, 8, 16, 10, 10, 11), new DmParams(16, 16, 16, 16, 12, 12, 12), new DmParams(12, 26, 12, 26, 16, 16, 14), new DmParams(18, 18, 18, 18, 18, 18, 14), new DmParams(20, 20, 20, 20, 22, 22, 18), new DmParams(12, 36, 12, 18, 22, 22, 18), new DmParams(22, 22, 22, 22, 30, 30, 20), new DmParams(16, 36, 16, 18, 32, 32, 24), new DmParams(24, 24, 24, 24, 36, 36, 24), new DmParams(26, 26, 26, 26, 44, 44, 28), new DmParams(16, 48, 16, 24, 49, 49, 28), new DmParams(32, 32, 16, 16, 62, 62, 36), new DmParams(36, 36, 18, 18, 86, 86, 42), new DmParams(40, 40, 20, 20, 114, 114, 48), new DmParams(44, 44, 22, 22, 144, 144, 56), new DmParams(48, 48, 24, 24, 174, 174, 68), new DmParams(52, 52, 26, 26, 204, 102, 42), new DmParams(64, 64, 16, 16, 280, 140, 56), new DmParams(72, 72, 18, 18, 368, 92, 36), new DmParams(80, 80, 20, 20, 456, 114, 48), new DmParams(88, 88, 22, 22, 576, 144, 56), new DmParams(96, 96, 24, 24, 696, 174, 68), new DmParams(104, 104, 26, 26, 816, 136, 56), new DmParams(120, 120, 20, 20, 1050, 175, 68), new DmParams(132, 132, 22, 22, 1304, 163, 62), new DmParams(144, 144, 24, 24, 1558, 156, 62) };
/*      */   private static final String x12 = "\r*> 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
/*      */   private int extOut;
/*      */   private short[] place;
/*      */   private byte[] image;
/*      */   private int height;
/*      */   private int width;
/*      */   private int ws;
/*      */   private int options;
/*      */ 
/*      */   private void setBit(int x, int y, int xByte)
/*      */   {
/*      */     int tmp12_11 = (y * xByte + x / 8);
/*      */     byte[] tmp12_1 = this.image; tmp12_1[tmp12_11] = ((byte)(tmp12_1[tmp12_11] | (byte)(128 >> (x & 0x7))));
/*      */   }
/*      */ 
/*      */   private void draw(byte[] data, int dataSize, DmParams dm)
/*      */   {
/*  171 */     int xByte = (dm.width + this.ws * 2 + 7) / 8;
/*  172 */     Arrays.fill(this.image, (byte)0);
/*      */ 
/*  175 */     for (int i = this.ws; i < dm.height + this.ws; i += dm.heightSection) {
/*  176 */       for (int j = this.ws; j < dm.width + this.ws; j += 2) {
/*  177 */         setBit(j, i, xByte);
/*      */       }
/*      */     }
/*      */ 
/*  181 */     for (i = dm.heightSection - 1 + this.ws; i < dm.height + this.ws; i += dm.heightSection) {
/*  182 */       for (int j = this.ws; j < dm.width + this.ws; j++) {
/*  183 */         setBit(j, i, xByte);
/*      */       }
/*      */     }
/*      */ 
/*  187 */     for (i = this.ws; i < dm.width + this.ws; i += dm.widthSection) {
/*  188 */       for (int j = this.ws; j < dm.height + this.ws; j++) {
/*  189 */         setBit(i, j, xByte);
/*      */       }
/*      */     }
/*      */ 
/*  193 */     for (i = dm.widthSection - 1 + this.ws; i < dm.width + this.ws; i += dm.widthSection) {
/*  194 */       for (int j = 1 + this.ws; j < dm.height + this.ws; j += 2) {
/*  195 */         setBit(i, j, xByte);
/*      */       }
/*      */     }
/*  198 */     int p = 0;
/*  199 */     for (int ys = 0; ys < dm.height; ys += dm.heightSection)
/*  200 */       for (int y = 1; y < dm.heightSection - 1; y++)
/*  201 */         for (int xs = 0; xs < dm.width; xs += dm.widthSection)
/*  202 */           for (int x = 1; x < dm.widthSection - 1; x++) {
/*  203 */             int z = this.place[(p++)];
/*  204 */             if ((z == 1) || ((z > 1) && ((data[(z / 8 - 1)] & 0xFF & 128 >> z % 8) != 0)))
/*  205 */               setBit(x + xs + this.ws, y + ys + this.ws, xByte);
/*      */           }
/*      */   }
/*      */ 
/*      */   private static void makePadding(byte[] data, int position, int count)
/*      */   {
/*  214 */     if (count <= 0)
/*  215 */       return;
/*  216 */     data[(position++)] = -127;
/*      */     while (true) { count--; if (count <= 0) break;
/*  218 */       int t = 129 + (position + 1) * 149 % 253 + 1;
/*  219 */       if (t > 254)
/*  220 */         t -= 254;
/*  221 */       data[(position++)] = ((byte)t); }
/*      */   }
/*      */ 
/*      */   private static boolean isDigit(int c)
/*      */   {
/*  226 */     return (c >= 48) && (c <= 57);
/*      */   }
/*      */ 
/*      */   private static int asciiEncodation(byte[] text, int textOffset, int textLength, byte[] data, int dataOffset, int dataLength)
/*      */   {
/*  231 */     int ptrIn = textOffset;
/*  232 */     int ptrOut = dataOffset;
/*  233 */     textLength += textOffset;
/*  234 */     dataLength += dataOffset;
/*  235 */     while (ptrIn < textLength) {
/*  236 */       if (ptrOut >= dataLength)
/*  237 */         return -1;
/*  238 */       int c = text[(ptrIn++)] & 0xFF;
/*  239 */       if ((isDigit(c)) && (ptrIn < textLength) && (isDigit(text[ptrIn] & 0xFF))) {
/*  240 */         data[(ptrOut++)] = ((byte)((c - 48) * 10 + (text[(ptrIn++)] & 0xFF) - 48 + 130));
/*      */       }
/*  242 */       else if (c > 127) {
/*  243 */         if (ptrOut + 1 >= dataLength)
/*  244 */           return -1;
/*  245 */         data[(ptrOut++)] = -21;
/*  246 */         data[(ptrOut++)] = ((byte)(c - 128 + 1));
/*      */       }
/*      */       else {
/*  249 */         data[(ptrOut++)] = ((byte)(c + 1));
/*      */       }
/*      */     }
/*  252 */     return ptrOut - dataOffset;
/*      */   }
/*      */ 
/*      */   private static int b256Encodation(byte[] text, int textOffset, int textLength, byte[] data, int dataOffset, int dataLength)
/*      */   {
/*  257 */     if (textLength == 0)
/*  258 */       return 0;
/*  259 */     if ((textLength < 250) && (textLength + 2 > dataLength))
/*  260 */       return -1;
/*  261 */     if ((textLength >= 250) && (textLength + 3 > dataLength))
/*  262 */       return -1;
/*  263 */     data[dataOffset] = -25;
/*      */     int k;
/*      */     int k;
/*  264 */     if (textLength < 250) {
/*  265 */       data[(dataOffset + 1)] = ((byte)textLength);
/*  266 */       k = 2;
/*      */     }
/*      */     else {
/*  269 */       data[(dataOffset + 1)] = ((byte)(textLength / 250 + 249));
/*  270 */       data[(dataOffset + 2)] = ((byte)(textLength % 250));
/*  271 */       k = 3;
/*      */     }
/*  273 */     System.arraycopy(text, textOffset, data, k + dataOffset, textLength);
/*  274 */     k += textLength + dataOffset;
/*  275 */     for (int j = dataOffset + 1; j < k; j++) {
/*  276 */       int c = data[j] & 0xFF;
/*  277 */       int prn = 149 * (j + 1) % 255 + 1;
/*  278 */       int tv = c + prn;
/*  279 */       if (tv > 255)
/*  280 */         tv -= 256;
/*  281 */       data[j] = ((byte)tv);
/*      */     }
/*      */ 
/*  284 */     return k - dataOffset;
/*      */   }
/*      */ 
/*      */   private static int X12Encodation(byte[] text, int textOffset, int textLength, byte[] data, int dataOffset, int dataLength)
/*      */   {
/*  290 */     if (textLength == 0)
/*  291 */       return 0;
/*  292 */     int ptrIn = 0;
/*  293 */     int ptrOut = 0;
/*  294 */     byte[] x = new byte[textLength];
/*  295 */     int count = 0;
/*  296 */     for (; ptrIn < textLength; ptrIn++) {
/*  297 */       int i = "\r*> 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf((char)text[(ptrIn + textOffset)]);
/*  298 */       if (i >= 0) {
/*  299 */         x[ptrIn] = ((byte)i);
/*  300 */         count++;
/*      */       }
/*      */       else {
/*  303 */         x[ptrIn] = 100;
/*  304 */         if (count >= 6)
/*  305 */           count -= count / 3 * 3;
/*  306 */         for (int k = 0; k < count; k++)
/*  307 */           x[(ptrIn - k - 1)] = 100;
/*  308 */         count = 0;
/*      */       }
/*      */     }
/*  311 */     if (count >= 6)
/*  312 */       count -= count / 3 * 3;
/*  313 */     for (int k = 0; k < count; k++)
/*  314 */       x[(ptrIn - k - 1)] = 100;
/*  315 */     ptrIn = 0;
/*  316 */     byte c = 0;
/*  317 */     for (; ptrIn < textLength; ptrIn++) {
/*  318 */       c = x[ptrIn];
/*  319 */       if (ptrOut >= dataLength)
/*      */         break;
/*  321 */       if (c < 40) {
/*  322 */         if ((ptrIn == 0) || ((ptrIn > 0) && (x[(ptrIn - 1)] > 40)))
/*  323 */           data[(dataOffset + ptrOut++)] = -18;
/*  324 */         if (ptrOut + 2 > dataLength)
/*      */           break;
/*  326 */         int n = 1600 * x[ptrIn] + 40 * x[(ptrIn + 1)] + x[(ptrIn + 2)] + 1;
/*  327 */         data[(dataOffset + ptrOut++)] = ((byte)(n / 256));
/*  328 */         data[(dataOffset + ptrOut++)] = ((byte)n);
/*  329 */         ptrIn += 2;
/*      */       }
/*      */       else {
/*  332 */         if ((ptrIn > 0) && (x[(ptrIn - 1)] < 40))
/*  333 */           data[(dataOffset + ptrOut++)] = -2;
/*  334 */         int ci = text[(ptrIn + textOffset)] & 0xFF;
/*  335 */         if (ci > 127) {
/*  336 */           data[(dataOffset + ptrOut++)] = -21;
/*  337 */           ci -= 128;
/*      */         }
/*  339 */         if (ptrOut >= dataLength)
/*      */           break;
/*  341 */         data[(dataOffset + ptrOut++)] = ((byte)(ci + 1));
/*      */       }
/*      */     }
/*  344 */     c = 100;
/*  345 */     if (textLength > 0)
/*  346 */       c = x[(textLength - 1)];
/*  347 */     if ((ptrIn != textLength) || ((c < 40) && (ptrOut >= dataLength)))
/*  348 */       return -1;
/*  349 */     if (c < 40)
/*  350 */       data[(dataOffset + ptrOut++)] = -2;
/*  351 */     return ptrOut;
/*      */   }
/*      */ 
/*      */   private static int EdifactEncodation(byte[] text, int textOffset, int textLength, byte[] data, int dataOffset, int dataLength)
/*      */   {
/*  356 */     if (textLength == 0)
/*  357 */       return 0;
/*  358 */     int ptrIn = 0;
/*  359 */     int ptrOut = 0;
/*  360 */     int edi = 0;
/*  361 */     int pedi = 18;
/*  362 */     boolean ascii = true;
/*  363 */     for (; ptrIn < textLength; ptrIn++) {
/*  364 */       int c = text[(ptrIn + textOffset)] & 0xFF;
/*  365 */       if ((((c & 0xE0) == 64) || ((c & 0xE0) == 32)) && (c != 95)) {
/*  366 */         if (ascii) {
/*  367 */           if (ptrOut + 1 > dataLength)
/*      */             break;
/*  369 */           data[(dataOffset + ptrOut++)] = -16;
/*  370 */           ascii = false;
/*      */         }
/*  372 */         c &= 63;
/*  373 */         edi |= c << pedi;
/*  374 */         if (pedi == 0) {
/*  375 */           if (ptrOut + 3 > dataLength)
/*      */             break;
/*  377 */           data[(dataOffset + ptrOut++)] = ((byte)(edi >> 16));
/*  378 */           data[(dataOffset + ptrOut++)] = ((byte)(edi >> 8));
/*  379 */           data[(dataOffset + ptrOut++)] = ((byte)edi);
/*  380 */           edi = 0;
/*  381 */           pedi = 18;
/*      */         }
/*      */         else {
/*  384 */           pedi -= 6;
/*      */         }
/*      */       } else {
/*  387 */         if (!ascii) {
/*  388 */           edi |= 31 << pedi;
/*  389 */           if (ptrOut + 3 - pedi / 8 > dataLength)
/*      */             break;
/*  391 */           data[(dataOffset + ptrOut++)] = ((byte)(edi >> 16));
/*  392 */           if (pedi <= 12)
/*  393 */             data[(dataOffset + ptrOut++)] = ((byte)(edi >> 8));
/*  394 */           if (pedi <= 6)
/*  395 */             data[(dataOffset + ptrOut++)] = ((byte)edi);
/*  396 */           ascii = true;
/*  397 */           pedi = 18;
/*  398 */           edi = 0;
/*      */         }
/*  400 */         if (c > 127) {
/*  401 */           if (ptrOut >= dataLength)
/*      */             break;
/*  403 */           data[(dataOffset + ptrOut++)] = -21;
/*  404 */           c -= 128;
/*      */         }
/*  406 */         if (ptrOut >= dataLength)
/*      */           break;
/*  408 */         data[(dataOffset + ptrOut++)] = ((byte)(c + 1));
/*      */       }
/*      */     }
/*  411 */     if (ptrIn != textLength)
/*  412 */       return -1;
/*  413 */     if (!ascii) {
/*  414 */       edi |= 31 << pedi;
/*  415 */       if (ptrOut + 3 - pedi / 8 > dataLength)
/*  416 */         return -1;
/*  417 */       data[(dataOffset + ptrOut++)] = ((byte)(edi >> 16));
/*  418 */       if (pedi <= 12)
/*  419 */         data[(dataOffset + ptrOut++)] = ((byte)(edi >> 8));
/*  420 */       if (pedi <= 6)
/*  421 */         data[(dataOffset + ptrOut++)] = ((byte)edi);
/*      */     }
/*  423 */     return ptrOut;
/*      */   }
/*      */ 
/*      */   private static int C40OrTextEncodation(byte[] text, int textOffset, int textLength, byte[] data, int dataOffset, int dataLength, boolean c40)
/*      */   {
/*  429 */     if (textLength == 0)
/*  430 */       return 0;
/*  431 */     int ptrIn = 0;
/*  432 */     int ptrOut = 0;
/*  433 */     if (c40)
/*  434 */       data[(dataOffset + ptrOut++)] = -26;
/*      */     else
/*  436 */       data[(dataOffset + ptrOut++)] = -17;
/*  437 */     String shift2 = "!\"#$%&'()*+,-./:;<=>?@[\\]^_";
/*      */     String shift3;
/*      */     String basic;
/*      */     String shift3;
/*  438 */     if (c40) {
/*  439 */       String basic = " 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
/*  440 */       shift3 = "`abcdefghijklmnopqrstuvwxyz{|}~";
/*      */     }
/*      */     else {
/*  443 */       basic = " 0123456789abcdefghijklmnopqrstuvwxyz";
/*  444 */       shift3 = "`ABCDEFGHIJKLMNOPQRSTUVWXYZ{|}~";
/*      */     }
/*  446 */     int[] enc = new int[textLength * 4 + 10];
/*  447 */     int encPtr = 0;
/*  448 */     int last0 = 0;
/*  449 */     int last1 = 0;
/*  450 */     while (ptrIn < textLength) {
/*  451 */       if (encPtr % 3 == 0) {
/*  452 */         last0 = ptrIn;
/*  453 */         last1 = encPtr;
/*      */       }
/*  455 */       int c = text[(textOffset + ptrIn++)] & 0xFF;
/*  456 */       if (c > 127) {
/*  457 */         c -= 128;
/*  458 */         enc[(encPtr++)] = 1;
/*  459 */         enc[(encPtr++)] = 30;
/*      */       }
/*  461 */       int idx = basic.indexOf((char)c);
/*  462 */       if (idx >= 0) {
/*  463 */         enc[(encPtr++)] = (idx + 3);
/*      */       }
/*  465 */       else if (c < 32) {
/*  466 */         enc[(encPtr++)] = 0;
/*  467 */         enc[(encPtr++)] = c;
/*      */       }
/*  469 */       else if ((idx = shift2.indexOf((char)c)) >= 0) {
/*  470 */         enc[(encPtr++)] = 1;
/*  471 */         enc[(encPtr++)] = idx;
/*      */       }
/*  473 */       else if ((idx = shift3.indexOf((char)c)) >= 0) {
/*  474 */         enc[(encPtr++)] = 2;
/*  475 */         enc[(encPtr++)] = idx;
/*      */       }
/*      */     }
/*  478 */     if (encPtr % 3 != 0) {
/*  479 */       ptrIn = last0;
/*  480 */       encPtr = last1;
/*      */     }
/*  482 */     if (encPtr / 3 * 2 > dataLength - 2) {
/*  483 */       return -1;
/*      */     }
/*  485 */     for (int i = 0; 
/*  486 */       i < encPtr; i += 3) {
/*  487 */       int a = 1600 * enc[i] + 40 * enc[(i + 1)] + enc[(i + 2)] + 1;
/*  488 */       data[(dataOffset + ptrOut++)] = ((byte)(a / 256));
/*  489 */       data[(dataOffset + ptrOut++)] = ((byte)a);
/*      */     }
/*  491 */     data[(ptrOut++)] = -2;
/*  492 */     i = asciiEncodation(text, ptrIn, textLength - ptrIn, data, ptrOut, dataLength - ptrOut);
/*  493 */     if (i < 0)
/*  494 */       return i;
/*  495 */     return ptrOut + i;
/*      */   }
/*      */ 
/*      */   private static int getEncodation(byte[] text, int textOffset, int textSize, byte[] data, int dataOffset, int dataSize, int options, boolean firstMatch)
/*      */   {
/*  500 */     int[] e1 = new int[6];
/*  501 */     if (dataSize < 0)
/*  502 */       return -1;
/*  503 */     int e = -1;
/*  504 */     options &= 7;
/*  505 */     if (options == 0) {
/*  506 */       e1[0] = asciiEncodation(text, textOffset, textSize, data, dataOffset, dataSize);
/*  507 */       if ((firstMatch) && (e1[0] >= 0))
/*  508 */         return e1[0];
/*  509 */       e1[1] = C40OrTextEncodation(text, textOffset, textSize, data, dataOffset, dataSize, false);
/*  510 */       if ((firstMatch) && (e1[1] >= 0))
/*  511 */         return e1[1];
/*  512 */       e1[2] = C40OrTextEncodation(text, textOffset, textSize, data, dataOffset, dataSize, true);
/*  513 */       if ((firstMatch) && (e1[2] >= 0))
/*  514 */         return e1[2];
/*  515 */       e1[3] = b256Encodation(text, textOffset, textSize, data, dataOffset, dataSize);
/*  516 */       if ((firstMatch) && (e1[3] >= 0))
/*  517 */         return e1[3];
/*  518 */       e1[4] = X12Encodation(text, textOffset, textSize, data, dataOffset, dataSize);
/*  519 */       if ((firstMatch) && (e1[4] >= 0))
/*  520 */         return e1[4];
/*  521 */       e1[5] = EdifactEncodation(text, textOffset, textSize, data, dataOffset, dataSize);
/*  522 */       if ((firstMatch) && (e1[5] >= 0))
/*  523 */         return e1[5];
/*  524 */       if ((e1[0] < 0) && (e1[1] < 0) && (e1[2] < 0) && (e1[3] < 0) && (e1[4] < 0) && (e1[5] < 0)) {
/*  525 */         return -1;
/*      */       }
/*  527 */       int j = 0;
/*  528 */       e = 99999;
/*  529 */       for (int k = 0; k < 6; k++) {
/*  530 */         if ((e1[k] >= 0) && (e1[k] < e)) {
/*  531 */           e = e1[k];
/*  532 */           j = k;
/*      */         }
/*      */       }
/*  535 */       if (j == 0)
/*  536 */         e = asciiEncodation(text, textOffset, textSize, data, dataOffset, dataSize);
/*  537 */       else if (j == 1)
/*  538 */         e = C40OrTextEncodation(text, textOffset, textSize, data, dataOffset, dataSize, false);
/*  539 */       else if (j == 2)
/*  540 */         e = C40OrTextEncodation(text, textOffset, textSize, data, dataOffset, dataSize, true);
/*  541 */       else if (j == 3)
/*  542 */         e = b256Encodation(text, textOffset, textSize, data, dataOffset, dataSize);
/*  543 */       else if (j == 4)
/*  544 */         e = X12Encodation(text, textOffset, textSize, data, dataOffset, dataSize);
/*  545 */       return e;
/*      */     }
/*  547 */     switch (options) {
/*      */     case 1:
/*  549 */       return asciiEncodation(text, textOffset, textSize, data, dataOffset, dataSize);
/*      */     case 2:
/*  551 */       return C40OrTextEncodation(text, textOffset, textSize, data, dataOffset, dataSize, true);
/*      */     case 3:
/*  553 */       return C40OrTextEncodation(text, textOffset, textSize, data, dataOffset, dataSize, false);
/*      */     case 4:
/*  555 */       return b256Encodation(text, textOffset, textSize, data, dataOffset, dataSize);
/*      */     case 5:
/*  557 */       return X12Encodation(text, textOffset, textSize, data, dataOffset, dataSize);
/*      */     case 6:
/*  559 */       return EdifactEncodation(text, textOffset, textSize, data, dataOffset, dataSize);
/*      */     case 7:
/*  561 */       if (textSize > dataSize)
/*  562 */         return -1;
/*  563 */       System.arraycopy(text, textOffset, data, dataOffset, textSize);
/*  564 */       return textSize;
/*      */     }
/*  566 */     return -1;
/*      */   }
/*      */ 
/*      */   private static int getNumber(byte[] text, int ptrIn, int n)
/*      */   {
/*  571 */     int v = 0;
/*  572 */     for (int j = 0; j < n; j++) {
/*  573 */       int c = text[(ptrIn++)] & 0xFF;
/*  574 */       if ((c < 48) || (c > 57))
/*  575 */         return -1;
/*  576 */       v = v * 10 + c - 48;
/*      */     }
/*  578 */     return v;
/*      */   }
/*      */ 
/*      */   private int processExtensions(byte[] text, int textOffset, int textSize, byte[] data)
/*      */   {
/*  583 */     if ((this.options & 0x20) == 0)
/*  584 */       return 0;
/*  585 */     int order = 0;
/*  586 */     int ptrIn = 0;
/*  587 */     int ptrOut = 0;
/*  588 */     while (ptrIn < textSize) {
/*  589 */       if (order > 20)
/*  590 */         return -1;
/*  591 */       int c = text[(textOffset + ptrIn++)] & 0xFF;
/*  592 */       order++;
/*  593 */       switch (c) {
/*      */       case 46:
/*  595 */         this.extOut = ptrIn;
/*  596 */         return ptrOut;
/*      */       case 101:
/*  598 */         if (ptrIn + 6 > textSize)
/*  599 */           return -1;
/*  600 */         int eci = getNumber(text, textOffset + ptrIn, 6);
/*  601 */         if (eci < 0)
/*  602 */           return -1;
/*  603 */         ptrIn += 6;
/*  604 */         data[(ptrOut++)] = -15;
/*  605 */         if (eci < 127) {
/*  606 */           data[(ptrOut++)] = ((byte)(eci + 1));
/*  607 */         } else if (eci < 16383) {
/*  608 */           data[(ptrOut++)] = ((byte)((eci - 127) / 254 + 128));
/*  609 */           data[(ptrOut++)] = ((byte)((eci - 127) % 254 + 1));
/*      */         }
/*      */         else {
/*  612 */           data[(ptrOut++)] = ((byte)((eci - 16383) / 64516 + 192));
/*  613 */           data[(ptrOut++)] = ((byte)((eci - 16383) / 254 % 254 + 1));
/*  614 */           data[(ptrOut++)] = ((byte)((eci - 16383) % 254 + 1));
/*      */         }
/*  616 */         break;
/*      */       case 115:
/*  618 */         if (order != 1)
/*  619 */           return -1;
/*  620 */         if (ptrIn + 9 > textSize)
/*  621 */           return -1;
/*  622 */         int fn = getNumber(text, textOffset + ptrIn, 2);
/*  623 */         if ((fn <= 0) || (fn > 16))
/*  624 */           return -1;
/*  625 */         ptrIn += 2;
/*  626 */         int ft = getNumber(text, textOffset + ptrIn, 2);
/*  627 */         if ((ft <= 1) || (ft > 16))
/*  628 */           return -1;
/*  629 */         ptrIn += 2;
/*  630 */         int fi = getNumber(text, textOffset + ptrIn, 5);
/*  631 */         if ((fi < 0) || (fn >= 64516))
/*  632 */           return -1;
/*  633 */         ptrIn += 5;
/*  634 */         data[(ptrOut++)] = -23;
/*  635 */         data[(ptrOut++)] = ((byte)(fn - 1 << 4 | 17 - ft));
/*  636 */         data[(ptrOut++)] = ((byte)(fi / 254 + 1));
/*  637 */         data[(ptrOut++)] = ((byte)(fi % 254 + 1));
/*  638 */         break;
/*      */       case 112:
/*  640 */         if (order != 1)
/*  641 */           return -1;
/*  642 */         data[(ptrOut++)] = -22;
/*  643 */         break;
/*      */       case 109:
/*  645 */         if (order != 1)
/*  646 */           return -1;
/*  647 */         if (ptrIn + 1 > textSize)
/*  648 */           return -1;
/*  649 */         c = text[(textOffset + ptrIn++)] & 0xFF;
/*  650 */         if ((c != 53) && (c != 53))
/*  651 */           return -1;
/*  652 */         data[(ptrOut++)] = -22;
/*  653 */         data[(ptrOut++)] = ((byte)(c == 53 ? 'ì' : 'í'));
/*  654 */         break;
/*      */       case 102:
/*  656 */         if ((order != 1) && ((order != 2) || ((text[textOffset] != 115) && (text[textOffset] != 109))))
/*  657 */           return -1;
/*  658 */         data[(ptrOut++)] = -24;
/*      */       }
/*      */     }
/*  661 */     return -1;
/*      */   }
/*      */ 
/*      */   public int generate(String text)
/*      */     throws UnsupportedEncodingException
/*      */   {
/*  676 */     byte[] t = text.getBytes("iso-8859-1");
/*  677 */     return generate(t, 0, t.length);
/*      */   }
/*      */ 
/*      */   public int generate(byte[] text, int textOffset, int textSize)
/*      */   {
/*  695 */     byte[] data = new byte[2500];
/*  696 */     this.extOut = 0;
/*  697 */     int extCount = processExtensions(text, textOffset, textSize, data);
/*  698 */     if (extCount < 0) {
/*  699 */       return 5;
/*      */     }
/*  701 */     int e = -1;
/*      */     DmParams dm;
/*  702 */     if ((this.height == 0) || (this.width == 0)) {
/*  703 */       DmParams last = dmSizes[(dmSizes.length - 1)];
/*  704 */       e = getEncodation(text, textOffset + this.extOut, textSize - this.extOut, data, extCount, last.dataSize - extCount, this.options, false);
/*  705 */       if (e < 0) {
/*  706 */         return 1;
/*      */       }
/*  708 */       e += extCount;
/*  709 */       for (int k = 0; (k < dmSizes.length) && 
/*  710 */         (dmSizes[k].dataSize < e); k++);
/*  713 */       DmParams dm = dmSizes[k];
/*  714 */       this.height = dm.height;
/*  715 */       this.width = dm.width;
/*      */     }
/*      */     else {
/*  718 */       for (int k = 0; (k < dmSizes.length) && (
/*  719 */         (this.height != dmSizes[k].height) || (this.width != dmSizes[k].width)); k++);
/*  722 */       if (k == dmSizes.length) {
/*  723 */         return 3;
/*      */       }
/*  725 */       dm = dmSizes[k];
/*  726 */       e = getEncodation(text, textOffset + this.extOut, textSize - this.extOut, data, extCount, dm.dataSize - extCount, this.options, true);
/*  727 */       if (e < 0) {
/*  728 */         return 1;
/*      */       }
/*  730 */       e += extCount;
/*      */     }
/*  732 */     if ((this.options & 0x40) != 0) {
/*  733 */       return 0;
/*      */     }
/*  735 */     this.image = new byte[(dm.width + 2 * this.ws + 7) / 8 * (dm.height + 2 * this.ws)];
/*  736 */     makePadding(data, e, dm.dataSize - e);
/*  737 */     this.place = Placement.doPlacement(dm.height - dm.height / dm.heightSection * 2, dm.width - dm.width / dm.widthSection * 2);
/*  738 */     int full = dm.dataSize + (dm.dataSize + 2) / dm.dataBlock * dm.errorBlock;
/*  739 */     ReedSolomon.generateECC(data, dm.dataSize, dm.dataBlock, dm.errorBlock);
/*  740 */     draw(data, full, dm);
/*  741 */     return 0;
/*      */   }
/*      */ 
/*      */   public com.itextpdf.text.Image createImage()
/*      */     throws BadElementException
/*      */   {
/*  750 */     if (this.image == null)
/*  751 */       return null;
/*  752 */     byte[] g4 = CCITTG4Encoder.compress(this.image, this.width + 2 * this.ws, this.height + 2 * this.ws);
/*  753 */     return com.itextpdf.text.Image.getInstance(this.width + 2 * this.ws, this.height + 2 * this.ws, false, 256, 0, g4, null);
/*      */   }
/*      */ 
/*      */   public byte[] getImage()
/*      */   {
/*  784 */     return this.image;
/*      */   }
/*      */ 
/*      */   public int getHeight()
/*      */   {
/*  793 */     return this.height;
/*      */   }
/*      */ 
/*      */   public void setHeight(int height)
/*      */   {
/*  833 */     this.height = height;
/*      */   }
/*      */ 
/*      */   public int getWidth()
/*      */   {
/*  842 */     return this.width;
/*      */   }
/*      */ 
/*      */   public void setWidth(int width)
/*      */   {
/*  882 */     this.width = width;
/*      */   }
/*      */ 
/*      */   public int getWs()
/*      */   {
/*  890 */     return this.ws;
/*      */   }
/*      */ 
/*      */   public void setWs(int ws)
/*      */   {
/*  898 */     this.ws = ws;
/*      */   }
/*      */ 
/*      */   public int getOptions()
/*      */   {
/*  906 */     return this.options;
/*      */   }
/*      */ 
/*      */   public void setOptions(int options)
/*      */   {
/*  937 */     this.options = options;
/*      */   }
/*      */ 
/*      */   public java.awt.Image createAwtImage(Color foreground, Color background)
/*      */   {
/* 1262 */     if (this.image == null)
/* 1263 */       return null;
/* 1264 */     int f = foreground.getRGB();
/* 1265 */     int g = background.getRGB();
/* 1266 */     Canvas canvas = new Canvas();
/*      */ 
/* 1268 */     int w = this.width + 2 * this.ws;
/* 1269 */     int h = this.height + 2 * this.ws;
/* 1270 */     int[] pix = new int[w * h];
/* 1271 */     int stride = (w + 7) / 8;
/* 1272 */     int ptr = 0;
/* 1273 */     for (int k = 0; k < h; k++) {
/* 1274 */       int p = k * stride;
/* 1275 */       for (int j = 0; j < w; j++) {
/* 1276 */         int b = this.image[(p + j / 8)] & 0xFF;
/* 1277 */         b <<= j % 8;
/* 1278 */         pix[(ptr++)] = ((b & 0x80) == 0 ? g : f);
/*      */       }
/*      */     }
/* 1281 */     java.awt.Image img = canvas.createImage(new MemoryImageSource(w, h, pix, 0, w));
/* 1282 */     return img;
/*      */   }
/*      */ 
/*      */   static class ReedSolomon
/*      */   {
/* 1060 */     private static final int[] log = { 0, 255, 1, 240, 2, 225, 241, 53, 3, 38, 226, 133, 242, 43, 54, 210, 4, 195, 39, 114, 227, 106, 134, 28, 243, 140, 44, 23, 55, 118, 211, 234, 5, 219, 196, 96, 40, 222, 115, 103, 228, 78, 107, 125, 135, 8, 29, 162, 244, 186, 141, 180, 45, 99, 24, 49, 56, 13, 119, 153, 212, 199, 235, 91, 6, 76, 220, 217, 197, 11, 97, 184, 41, 36, 223, 253, 116, 138, 104, 193, 229, 86, 79, 171, 108, 165, 126, 145, 136, 34, 9, 74, 30, 32, 163, 84, 245, 173, 187, 204, 142, 81, 181, 190, 46, 88, 100, 159, 25, 231, 50, 207, 57, 147, 14, 67, 120, 128, 154, 248, 213, 167, 200, 63, 236, 110, 92, 176, 7, 161, 77, 124, 221, 102, 218, 95, 198, 90, 12, 152, 98, 48, 185, 179, 42, 209, 37, 132, 224, 52, 254, 239, 117, 233, 139, 22, 105, 27, 194, 113, 230, 206, 87, 158, 80, 189, 172, 203, 109, 175, 166, 62, 127, 247, 146, 66, 137, 192, 35, 252, 10, 183, 75, 216, 31, 83, 33, 73, 164, 144, 85, 170, 246, 65, 174, 61, 188, 202, 205, 157, 143, 169, 82, 72, 182, 215, 191, 251, 47, 178, 89, 151, 101, 94, 160, 123, 26, 112, 232, 21, 51, 238, 208, 131, 58, 69, 148, 18, 15, 16, 68, 17, 121, 149, 129, 19, 155, 59, 249, 70, 214, 250, 168, 71, 201, 156, 64, 60, 237, 130, 111, 20, 93, 122, 177, 150 };
/*      */ 
/* 1079 */     private static final int[] alog = { 1, 2, 4, 8, 16, 32, 64, 128, 45, 90, 180, 69, 138, 57, 114, 228, 229, 231, 227, 235, 251, 219, 155, 27, 54, 108, 216, 157, 23, 46, 92, 184, 93, 186, 89, 178, 73, 146, 9, 18, 36, 72, 144, 13, 26, 52, 104, 208, 141, 55, 110, 220, 149, 7, 14, 28, 56, 112, 224, 237, 247, 195, 171, 123, 246, 193, 175, 115, 230, 225, 239, 243, 203, 187, 91, 182, 65, 130, 41, 82, 164, 101, 202, 185, 95, 190, 81, 162, 105, 210, 137, 63, 126, 252, 213, 135, 35, 70, 140, 53, 106, 212, 133, 39, 78, 156, 21, 42, 84, 168, 125, 250, 217, 159, 19, 38, 76, 152, 29, 58, 116, 232, 253, 215, 131, 43, 86, 172, 117, 234, 249, 223, 147, 11, 22, 44, 88, 176, 77, 154, 25, 50, 100, 200, 189, 87, 174, 113, 226, 233, 255, 211, 139, 59, 118, 236, 245, 199, 163, 107, 214, 129, 47, 94, 188, 85, 170, 121, 242, 201, 191, 83, 166, 97, 194, 169, 127, 254, 209, 143, 51, 102, 204, 181, 71, 142, 49, 98, 196, 165, 103, 206, 177, 79, 158, 17, 34, 68, 136, 61, 122, 244, 197, 167, 99, 198, 161, 111, 222, 145, 15, 30, 60, 120, 240, 205, 183, 67, 134, 33, 66, 132, 37, 74, 148, 5, 10, 20, 40, 80, 160, 109, 218, 153, 31, 62, 124, 248, 221, 151, 3, 6, 12, 24, 48, 96, 192, 173, 119, 238, 241, 207, 179, 75, 150, 1 };
/*      */ 
/* 1098 */     private static final int[] poly5 = { 228, 48, 15, 111, 62 };
/*      */ 
/* 1102 */     private static final int[] poly7 = { 23, 68, 144, 134, 240, 92, 254 };
/*      */ 
/* 1106 */     private static final int[] poly10 = { 28, 24, 185, 166, 223, 248, 116, 255, 110, 61 };
/*      */ 
/* 1110 */     private static final int[] poly11 = { 175, 138, 205, 12, 194, 168, 39, 245, 60, 97, 120 };
/*      */ 
/* 1114 */     private static final int[] poly12 = { 41, 153, 158, 91, 61, 42, 142, 213, 97, 178, 100, 242 };
/*      */ 
/* 1118 */     private static final int[] poly14 = { 156, 97, 192, 252, 95, 9, 157, 119, 138, 45, 18, 186, 83, 185 };
/*      */ 
/* 1122 */     private static final int[] poly18 = { 83, 195, 100, 39, 188, 75, 66, 61, 241, 213, 109, 129, 94, 254, 225, 48, 90, 188 };
/*      */ 
/* 1127 */     private static final int[] poly20 = { 15, 195, 244, 9, 233, 71, 168, 2, 188, 160, 153, 145, 253, 79, 108, 82, 27, 174, 186, 172 };
/*      */ 
/* 1132 */     private static final int[] poly24 = { 52, 190, 88, 205, 109, 39, 176, 21, 155, 197, 251, 223, 155, 21, 5, 172, 254, 124, 12, 181, 184, 96, 50, 193 };
/*      */ 
/* 1137 */     private static final int[] poly28 = { 211, 231, 43, 97, 71, 96, 103, 174, 37, 151, 170, 53, 75, 34, 249, 121, 17, 138, 110, 213, 141, 136, 120, 151, 233, 168, 93, 255 };
/*      */ 
/* 1142 */     private static final int[] poly36 = { 245, 127, 242, 218, 130, 250, 162, 181, 102, 120, 84, 179, 220, 251, 80, 182, 229, 18, 2, 4, 68, 33, 101, 137, 95, 119, 115, 44, 175, 184, 59, 25, 225, 98, 81, 112 };
/*      */ 
/* 1148 */     private static final int[] poly42 = { 77, 193, 137, 31, 19, 38, 22, 153, 247, 105, 122, 2, 245, 133, 242, 8, 175, 95, 100, 9, 167, 105, 214, 111, 57, 121, 21, 1, 253, 57, 54, 101, 248, 202, 69, 50, 150, 177, 226, 5, 9, 5 };
/*      */ 
/* 1154 */     private static final int[] poly48 = { 245, 132, 172, 223, 96, 32, 117, 22, 238, 133, 238, 231, 205, 188, 237, 87, 191, 106, 16, 147, 118, 23, 37, 90, 170, 205, 131, 88, 120, 100, 66, 138, 186, 240, 82, 44, 176, 87, 187, 147, 160, 175, 69, 213, 92, 253, 225, 19 };
/*      */ 
/* 1160 */     private static final int[] poly56 = { 175, 9, 223, 238, 12, 17, 220, 208, 100, 29, 175, 170, 230, 192, 215, 235, 150, 159, 36, 223, 38, 200, 132, 54, 228, 146, 218, 234, 117, 203, 29, 232, 144, 238, 22, 150, 201, 117, 62, 207, 164, 13, 137, 245, 127, 67, 247, 28, 155, 43, 203, 107, 233, 53, 143, 46 };
/*      */ 
/* 1167 */     private static final int[] poly62 = { 242, 93, 169, 50, 144, 210, 39, 118, 202, 188, 201, 189, 143, 108, 196, 37, 185, 112, 134, 230, 245, 63, 197, 190, 250, 106, 185, 221, 175, 64, 114, 71, 161, 44, 147, 6, 27, 218, 51, 63, 87, 10, 40, 130, 188, 17, 163, 31, 176, 170, 4, 107, 232, 7, 94, 166, 224, 124, 86, 47, 11, 204 };
/*      */ 
/* 1174 */     private static final int[] poly68 = { 220, 228, 173, 89, 251, 149, 159, 56, 89, 33, 147, 244, 154, 36, 73, 127, 213, 136, 248, 180, 234, 197, 158, 177, 68, 122, 93, 213, 15, 160, 227, 236, 66, 139, 153, 185, 202, 167, 179, 25, 220, 232, 96, 210, 231, 136, 223, 239, 181, 241, 59, 52, 172, 25, 49, 232, 211, 189, 64, 54, 108, 153, 132, 63, 96, 103, 82, 186 };
/*      */ 
/*      */     private static int[] getPoly(int nc)
/*      */     {
/* 1183 */       switch (nc) {
/*      */       case 5:
/* 1185 */         return poly5;
/*      */       case 7:
/* 1187 */         return poly7;
/*      */       case 10:
/* 1189 */         return poly10;
/*      */       case 11:
/* 1191 */         return poly11;
/*      */       case 12:
/* 1193 */         return poly12;
/*      */       case 14:
/* 1195 */         return poly14;
/*      */       case 18:
/* 1197 */         return poly18;
/*      */       case 20:
/* 1199 */         return poly20;
/*      */       case 24:
/* 1201 */         return poly24;
/*      */       case 28:
/* 1203 */         return poly28;
/*      */       case 36:
/* 1205 */         return poly36;
/*      */       case 42:
/* 1207 */         return poly42;
/*      */       case 48:
/* 1209 */         return poly48;
/*      */       case 56:
/* 1211 */         return poly56;
/*      */       case 62:
/* 1213 */         return poly62;
/*      */       case 68:
/* 1215 */         return poly68;
/*      */       case 6:
/*      */       case 8:
/*      */       case 9:
/*      */       case 13:
/*      */       case 15:
/*      */       case 16:
/*      */       case 17:
/*      */       case 19:
/*      */       case 21:
/*      */       case 22:
/*      */       case 23:
/*      */       case 25:
/*      */       case 26:
/*      */       case 27:
/*      */       case 29:
/*      */       case 30:
/*      */       case 31:
/*      */       case 32:
/*      */       case 33:
/*      */       case 34:
/*      */       case 35:
/*      */       case 37:
/*      */       case 38:
/*      */       case 39:
/*      */       case 40:
/*      */       case 41:
/*      */       case 43:
/*      */       case 44:
/*      */       case 45:
/*      */       case 46:
/*      */       case 47:
/*      */       case 49:
/*      */       case 50:
/*      */       case 51:
/*      */       case 52:
/*      */       case 53:
/*      */       case 54:
/*      */       case 55:
/*      */       case 57:
/*      */       case 58:
/*      */       case 59:
/*      */       case 60:
/*      */       case 61:
/*      */       case 63:
/*      */       case 64:
/*      */       case 65:
/*      */       case 66:
/* 1217 */       case 67: } return null;
/*      */     }
/*      */ 
/*      */     private static void reedSolomonBlock(byte[] wd, int nd, byte[] ncout, int nc, int[] c)
/*      */     {
/* 1223 */       for (int i = 0; i <= nc; i++) ncout[i] = 0;
/* 1224 */       for (i = 0; i < nd; i++) {
/* 1225 */         int k = (ncout[0] ^ wd[i]) & 0xFF;
/* 1226 */         for (int j = 0; j < nc; j++)
/* 1227 */           ncout[j] = ((byte)(ncout[(j + 1)] ^ (k == 0 ? 0 : (byte)alog[((log[k] + log[c[(nc - j - 1)]]) % 255)])));
/*      */       }
/*      */     }
/*      */ 
/*      */     static void generateECC(byte[] wd, int nd, int datablock, int nc)
/*      */     {
/* 1233 */       int blocks = (nd + 2) / datablock;
/*      */ 
/* 1235 */       byte[] buf = new byte[256];
/* 1236 */       byte[] ecc = new byte[256];
/* 1237 */       int[] c = getPoly(nc);
/* 1238 */       for (int b = 0; b < blocks; b++)
/*      */       {
/* 1240 */         int p = 0;
/* 1241 */         for (int n = b; n < nd; n += blocks)
/* 1242 */           buf[(p++)] = wd[n];
/* 1243 */         reedSolomonBlock(buf, p, ecc, nc, c);
/* 1244 */         p = 0;
/* 1245 */         for (n = b; n < nc * blocks; n += blocks)
/* 1246 */           wd[(nd + n)] = ecc[(p++)];
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   static class Placement
/*      */   {
/*      */     private int nrow;
/*      */     private int ncol;
/*      */     private short[] array;
/*  944 */     private static final Hashtable<Integer, short[]> cache = new Hashtable();
/*      */ 
/*      */     static short[] doPlacement(int nrow, int ncol)
/*      */     {
/*  950 */       Integer key = Integer.valueOf(nrow * 1000 + ncol);
/*  951 */       short[] pc = (short[])cache.get(key);
/*  952 */       if (pc != null)
/*  953 */         return pc;
/*  954 */       Placement p = new Placement();
/*  955 */       p.nrow = nrow;
/*  956 */       p.ncol = ncol;
/*  957 */       p.array = new short[nrow * ncol];
/*  958 */       p.ecc200();
/*  959 */       cache.put(key, p.array);
/*  960 */       return p.array;
/*      */     }
/*      */ 
/*      */     private void module(int row, int col, int chr, int bit)
/*      */     {
/*  965 */       if (row < 0) { row += this.nrow; col += 4 - (this.nrow + 4) % 8; }
/*  966 */       if (col < 0) { col += this.ncol; row += 4 - (this.ncol + 4) % 8; }
/*  967 */       this.array[(row * this.ncol + col)] = ((short)(8 * chr + bit));
/*      */     }
/*      */ 
/*      */     private void utah(int row, int col, int chr) {
/*  971 */       module(row - 2, col - 2, chr, 0);
/*  972 */       module(row - 2, col - 1, chr, 1);
/*  973 */       module(row - 1, col - 2, chr, 2);
/*  974 */       module(row - 1, col - 1, chr, 3);
/*  975 */       module(row - 1, col, chr, 4);
/*  976 */       module(row, col - 2, chr, 5);
/*  977 */       module(row, col - 1, chr, 6);
/*  978 */       module(row, col, chr, 7);
/*      */     }
/*      */ 
/*      */     private void corner1(int chr) {
/*  982 */       module(this.nrow - 1, 0, chr, 0);
/*  983 */       module(this.nrow - 1, 1, chr, 1);
/*  984 */       module(this.nrow - 1, 2, chr, 2);
/*  985 */       module(0, this.ncol - 2, chr, 3);
/*  986 */       module(0, this.ncol - 1, chr, 4);
/*  987 */       module(1, this.ncol - 1, chr, 5);
/*  988 */       module(2, this.ncol - 1, chr, 6);
/*  989 */       module(3, this.ncol - 1, chr, 7);
/*      */     }
/*      */     private void corner2(int chr) {
/*  992 */       module(this.nrow - 3, 0, chr, 0);
/*  993 */       module(this.nrow - 2, 0, chr, 1);
/*  994 */       module(this.nrow - 1, 0, chr, 2);
/*  995 */       module(0, this.ncol - 4, chr, 3);
/*  996 */       module(0, this.ncol - 3, chr, 4);
/*  997 */       module(0, this.ncol - 2, chr, 5);
/*  998 */       module(0, this.ncol - 1, chr, 6);
/*  999 */       module(1, this.ncol - 1, chr, 7);
/*      */     }
/*      */     private void corner3(int chr) {
/* 1002 */       module(this.nrow - 3, 0, chr, 0);
/* 1003 */       module(this.nrow - 2, 0, chr, 1);
/* 1004 */       module(this.nrow - 1, 0, chr, 2);
/* 1005 */       module(0, this.ncol - 2, chr, 3);
/* 1006 */       module(0, this.ncol - 1, chr, 4);
/* 1007 */       module(1, this.ncol - 1, chr, 5);
/* 1008 */       module(2, this.ncol - 1, chr, 6);
/* 1009 */       module(3, this.ncol - 1, chr, 7);
/*      */     }
/*      */     private void corner4(int chr) {
/* 1012 */       module(this.nrow - 1, 0, chr, 0);
/* 1013 */       module(this.nrow - 1, this.ncol - 1, chr, 1);
/* 1014 */       module(0, this.ncol - 3, chr, 2);
/* 1015 */       module(0, this.ncol - 2, chr, 3);
/* 1016 */       module(0, this.ncol - 1, chr, 4);
/* 1017 */       module(1, this.ncol - 3, chr, 5);
/* 1018 */       module(1, this.ncol - 2, chr, 6);
/* 1019 */       module(1, this.ncol - 1, chr, 7);
/*      */     }
/*      */ 
/*      */     private void ecc200()
/*      */     {
/* 1025 */       Arrays.fill(this.array, (short)0);
/*      */ 
/* 1027 */       int chr = 1; int row = 4; int col = 0;
/*      */       do
/*      */       {
/* 1030 */         if ((row == this.nrow) && (col == 0)) corner1(chr++);
/* 1031 */         if ((row == this.nrow - 2) && (col == 0) && (this.ncol % 4 != 0)) corner2(chr++);
/* 1032 */         if ((row == this.nrow - 2) && (col == 0) && (this.ncol % 8 == 4)) corner3(chr++);
/* 1033 */         if ((row == this.nrow + 4) && (col == 2) && (this.ncol % 8 == 0)) corner4(chr++);
/*      */         do
/*      */         {
/* 1036 */           if ((row < this.nrow) && (col >= 0) && (this.array[(row * this.ncol + col)] == 0))
/* 1037 */             utah(row, col, chr++);
/* 1038 */           row -= 2; col += 2;
/* 1039 */         }while ((row >= 0) && (col < this.ncol));
/* 1040 */         row++; col += 3;
/*      */         do
/*      */         {
/* 1044 */           if ((row >= 0) && (col < this.ncol) && (this.array[(row * this.ncol + col)] == 0))
/* 1045 */             utah(row, col, chr++);
/* 1046 */           row += 2; col -= 2;
/* 1047 */         }while ((row < this.nrow) && (col >= 0));
/* 1048 */         row += 3; col++;
/*      */       }
/* 1050 */       while ((row < this.nrow) || (col < this.ncol));
/*      */ 
/* 1052 */       if (this.array[(this.nrow * this.ncol - 1)] == 0)
/*      */       {
/*      */         int tmp326_325 = 1; this.array[(this.nrow * this.ncol - this.ncol - 2)] = tmp326_325; this.array[(this.nrow * this.ncol - 1)] = tmp326_325;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class DmParams
/*      */   {
/*      */     int height;
/*      */     int width;
/*      */     int heightSection;
/*      */     int widthSection;
/*      */     int dataSize;
/*      */     int dataBlock;
/*      */     int errorBlock;
/*      */ 
/*      */     DmParams(int height, int width, int heightSection, int widthSection, int dataSize, int dataBlock, int errorBlock)
/*      */     {
/*  758 */       this.height = height;
/*  759 */       this.width = width;
/*  760 */       this.heightSection = heightSection;
/*  761 */       this.widthSection = widthSection;
/*  762 */       this.dataSize = dataSize;
/*  763 */       this.dataBlock = dataBlock;
/*  764 */       this.errorBlock = errorBlock;
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.BarcodeDatamatrix
 * JD-Core Version:    0.6.2
 */