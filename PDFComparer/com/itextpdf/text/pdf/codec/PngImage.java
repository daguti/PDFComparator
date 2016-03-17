/*     */ package com.itextpdf.text.pdf.codec;
/*     */ 
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.Image;
/*     */ import com.itextpdf.text.ImgRaw;
/*     */ import com.itextpdf.text.Utilities;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.pdf.ByteBuffer;
/*     */ import com.itextpdf.text.pdf.ICC_Profile;
/*     */ import com.itextpdf.text.pdf.PdfArray;
/*     */ import com.itextpdf.text.pdf.PdfDictionary;
/*     */ import com.itextpdf.text.pdf.PdfLiteral;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfNumber;
/*     */ import com.itextpdf.text.pdf.PdfObject;
/*     */ import com.itextpdf.text.pdf.PdfReader;
/*     */ import com.itextpdf.text.pdf.PdfString;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.zip.Inflater;
/*     */ import java.util.zip.InflaterInputStream;
/*     */ 
/*     */ public class PngImage
/*     */ {
/* 125 */   public static final int[] PNGID = { 137, 80, 78, 71, 13, 10, 26, 10 };
/*     */   public static final String IHDR = "IHDR";
/*     */   public static final String PLTE = "PLTE";
/*     */   public static final String IDAT = "IDAT";
/*     */   public static final String IEND = "IEND";
/*     */   public static final String tRNS = "tRNS";
/*     */   public static final String pHYs = "pHYs";
/*     */   public static final String gAMA = "gAMA";
/*     */   public static final String cHRM = "cHRM";
/*     */   public static final String sRGB = "sRGB";
/*     */   public static final String iCCP = "iCCP";
/*     */   private static final int TRANSFERSIZE = 4096;
/*     */   private static final int PNG_FILTER_NONE = 0;
/*     */   private static final int PNG_FILTER_SUB = 1;
/*     */   private static final int PNG_FILTER_UP = 2;
/*     */   private static final int PNG_FILTER_AVERAGE = 3;
/*     */   private static final int PNG_FILTER_PAETH = 4;
/* 163 */   private static final PdfName[] intents = { PdfName.PERCEPTUAL, PdfName.RELATIVECOLORIMETRIC, PdfName.SATURATION, PdfName.ABSOLUTECOLORIMETRIC };
/*     */   InputStream is;
/*     */   DataInputStream dataStream;
/*     */   int width;
/*     */   int height;
/*     */   int bitDepth;
/*     */   int colorType;
/*     */   int compressionMethod;
/*     */   int filterMethod;
/*     */   int interlaceMethod;
/* 175 */   PdfDictionary additional = new PdfDictionary();
/*     */   byte[] image;
/*     */   byte[] smask;
/*     */   byte[] trans;
/* 179 */   NewByteArrayOutputStream idat = new NewByteArrayOutputStream();
/*     */   int dpiX;
/*     */   int dpiY;
/*     */   float XYRatio;
/*     */   boolean genBWMask;
/*     */   boolean palShades;
/* 185 */   int transRedGray = -1;
/* 186 */   int transGreen = -1;
/* 187 */   int transBlue = -1;
/*     */   int inputBands;
/*     */   int bytesPerPixel;
/*     */   byte[] colorTable;
/* 191 */   float gamma = 1.0F;
/* 192 */   boolean hasCHRM = false;
/*     */   float xW;
/*     */   float yW;
/*     */   float xR;
/*     */   float yR;
/*     */   float xG;
/*     */   float yG;
/*     */   float xB;
/*     */   float yB;
/*     */   PdfName intent;
/*     */   ICC_Profile icc_profile;
/*     */ 
/*     */   PngImage(InputStream is)
/*     */   {
/* 201 */     this.is = is;
/*     */   }
/*     */ 
/*     */   public static Image getImage(URL url)
/*     */     throws IOException
/*     */   {
/* 210 */     InputStream is = null;
/*     */     try {
/* 212 */       is = url.openStream();
/* 213 */       Image img = getImage(is);
/* 214 */       img.setUrl(url);
/* 215 */       return img;
/*     */     }
/*     */     finally {
/* 218 */       if (is != null)
/* 219 */         is.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Image getImage(InputStream is)
/*     */     throws IOException
/*     */   {
/* 230 */     PngImage png = new PngImage(is);
/* 231 */     return png.getImage();
/*     */   }
/*     */ 
/*     */   public static Image getImage(String file)
/*     */     throws IOException
/*     */   {
/* 240 */     return getImage(Utilities.toURL(file));
/*     */   }
/*     */ 
/*     */   public static Image getImage(byte[] data)
/*     */     throws IOException
/*     */   {
/* 249 */     ByteArrayInputStream is = new ByteArrayInputStream(data);
/* 250 */     Image img = getImage(is);
/* 251 */     img.setOriginalData(data);
/* 252 */     return img;
/*     */   }
/*     */ 
/*     */   boolean checkMarker(String s) {
/* 256 */     if (s.length() != 4)
/* 257 */       return false;
/* 258 */     for (int k = 0; k < 4; k++) {
/* 259 */       char c = s.charAt(k);
/* 260 */       if (((c < 'a') || (c > 'z')) && ((c < 'A') || (c > 'Z')))
/* 261 */         return false;
/*     */     }
/* 263 */     return true;
/*     */   }
/*     */ 
/*     */   void readPng() throws IOException {
/* 267 */     for (int i = 0; i < PNGID.length; i++) {
/* 268 */       if (PNGID[i] != this.is.read()) {
/* 269 */         throw new IOException(MessageLocalization.getComposedMessage("file.is.not.a.valid.png", new Object[0]));
/*     */       }
/*     */     }
/* 272 */     byte[] buffer = new byte[4096];
/*     */     while (true) {
/* 274 */       int len = getInt(this.is);
/* 275 */       String marker = getString(this.is);
/* 276 */       if ((len < 0) || (!checkMarker(marker)))
/* 277 */         throw new IOException(MessageLocalization.getComposedMessage("corrupted.png.file", new Object[0]));
/* 278 */       if ("IDAT".equals(marker))
/*     */       {
/* 280 */         while (len != 0) {
/* 281 */           int size = this.is.read(buffer, 0, Math.min(len, 4096));
/* 282 */           if (size < 0)
/* 283 */             return;
/* 284 */           this.idat.write(buffer, 0, size);
/* 285 */           len -= size;
/*     */         }
/*     */       }
/* 288 */       else if ("tRNS".equals(marker)) {
/* 289 */         switch (this.colorType) {
/*     */         case 0:
/* 291 */           if (len >= 2) {
/* 292 */             len -= 2;
/* 293 */             int gray = getWord(this.is);
/* 294 */             if (this.bitDepth == 16)
/* 295 */               this.transRedGray = gray;
/*     */             else
/* 297 */               this.additional.put(PdfName.MASK, new PdfLiteral("[" + gray + " " + gray + "]")); 
/*     */           }
/* 298 */           break;
/*     */         case 2:
/* 301 */           if (len >= 6) {
/* 302 */             len -= 6;
/* 303 */             int red = getWord(this.is);
/* 304 */             int green = getWord(this.is);
/* 305 */             int blue = getWord(this.is);
/* 306 */             if (this.bitDepth == 16) {
/* 307 */               this.transRedGray = red;
/* 308 */               this.transGreen = green;
/* 309 */               this.transBlue = blue;
/*     */             }
/*     */             else {
/* 312 */               this.additional.put(PdfName.MASK, new PdfLiteral("[" + red + " " + red + " " + green + " " + green + " " + blue + " " + blue + "]")); } 
/* 313 */           }break;
/*     */         case 3:
/* 316 */           if (len > 0) {
/* 317 */             this.trans = new byte[len];
/* 318 */             for (int k = 0; k < len; k++)
/* 319 */               this.trans[k] = ((byte)this.is.read());
/* 320 */             len = 0;
/*     */           }break;
/*     */         case 1:
/*     */         }
/* 324 */         Utilities.skip(this.is, len);
/*     */       }
/* 326 */       else if ("IHDR".equals(marker)) {
/* 327 */         this.width = getInt(this.is);
/* 328 */         this.height = getInt(this.is);
/*     */ 
/* 330 */         this.bitDepth = this.is.read();
/* 331 */         this.colorType = this.is.read();
/* 332 */         this.compressionMethod = this.is.read();
/* 333 */         this.filterMethod = this.is.read();
/* 334 */         this.interlaceMethod = this.is.read();
/*     */       }
/* 336 */       else if ("PLTE".equals(marker)) {
/* 337 */         if (this.colorType == 3) {
/* 338 */           PdfArray colorspace = new PdfArray();
/* 339 */           colorspace.add(PdfName.INDEXED);
/* 340 */           colorspace.add(getColorspace());
/* 341 */           colorspace.add(new PdfNumber(len / 3 - 1));
/* 342 */           ByteBuffer colortable = new ByteBuffer();
/* 343 */           while (len-- > 0) {
/* 344 */             colortable.append_i(this.is.read());
/*     */           }
/* 346 */           colorspace.add(new PdfString(this.colorTable = colortable.toByteArray()));
/* 347 */           this.additional.put(PdfName.COLORSPACE, colorspace);
/*     */         }
/*     */         else {
/* 350 */           Utilities.skip(this.is, len);
/*     */         }
/*     */       }
/* 353 */       else if ("pHYs".equals(marker)) {
/* 354 */         int dx = getInt(this.is);
/* 355 */         int dy = getInt(this.is);
/* 356 */         int unit = this.is.read();
/* 357 */         if (unit == 1) {
/* 358 */           this.dpiX = ((int)(dx * 0.0254F + 0.5F));
/* 359 */           this.dpiY = ((int)(dy * 0.0254F + 0.5F));
/*     */         }
/* 362 */         else if (dy != 0) {
/* 363 */           this.XYRatio = (dx / dy);
/*     */         }
/*     */       }
/* 366 */       else if ("cHRM".equals(marker)) {
/* 367 */         this.xW = (getInt(this.is) / 100000.0F);
/* 368 */         this.yW = (getInt(this.is) / 100000.0F);
/* 369 */         this.xR = (getInt(this.is) / 100000.0F);
/* 370 */         this.yR = (getInt(this.is) / 100000.0F);
/* 371 */         this.xG = (getInt(this.is) / 100000.0F);
/* 372 */         this.yG = (getInt(this.is) / 100000.0F);
/* 373 */         this.xB = (getInt(this.is) / 100000.0F);
/* 374 */         this.yB = (getInt(this.is) / 100000.0F);
/* 375 */         this.hasCHRM = ((Math.abs(this.xW) >= 1.0E-004F) && (Math.abs(this.yW) >= 1.0E-004F) && (Math.abs(this.xR) >= 1.0E-004F) && (Math.abs(this.yR) >= 1.0E-004F) && (Math.abs(this.xG) >= 1.0E-004F) && (Math.abs(this.yG) >= 1.0E-004F) && (Math.abs(this.xB) >= 1.0E-004F) && (Math.abs(this.yB) >= 1.0E-004F));
/*     */       }
/* 377 */       else if ("sRGB".equals(marker)) {
/* 378 */         int ri = this.is.read();
/* 379 */         this.intent = intents[ri];
/* 380 */         this.gamma = 2.2F;
/* 381 */         this.xW = 0.3127F;
/* 382 */         this.yW = 0.329F;
/* 383 */         this.xR = 0.64F;
/* 384 */         this.yR = 0.33F;
/* 385 */         this.xG = 0.3F;
/* 386 */         this.yG = 0.6F;
/* 387 */         this.xB = 0.15F;
/* 388 */         this.yB = 0.06F;
/* 389 */         this.hasCHRM = true;
/*     */       }
/* 391 */       else if ("gAMA".equals(marker)) {
/* 392 */         int gm = getInt(this.is);
/* 393 */         if (gm != 0) {
/* 394 */           this.gamma = (100000.0F / gm);
/* 395 */           if (!this.hasCHRM) {
/* 396 */             this.xW = 0.3127F;
/* 397 */             this.yW = 0.329F;
/* 398 */             this.xR = 0.64F;
/* 399 */             this.yR = 0.33F;
/* 400 */             this.xG = 0.3F;
/* 401 */             this.yG = 0.6F;
/* 402 */             this.xB = 0.15F;
/* 403 */             this.yB = 0.06F;
/* 404 */             this.hasCHRM = true;
/*     */           }
/*     */         }
/*     */       }
/* 408 */       else if ("iCCP".equals(marker)) {
/*     */         do
/* 410 */           len--;
/* 411 */         while (this.is.read() != 0);
/* 412 */         this.is.read();
/* 413 */         len--;
/* 414 */         byte[] icccom = new byte[len];
/* 415 */         int p = 0;
/* 416 */         while (len > 0) {
/* 417 */           int r = this.is.read(icccom, p, len);
/* 418 */           if (r < 0)
/* 419 */             throw new IOException(MessageLocalization.getComposedMessage("premature.end.of.file", new Object[0]));
/* 420 */           p += r;
/* 421 */           len -= r;
/*     */         }
/* 423 */         byte[] iccp = PdfReader.FlateDecode(icccom, true);
/* 424 */         icccom = null;
/*     */         try {
/* 426 */           this.icc_profile = ICC_Profile.getInstance(iccp);
/*     */         }
/*     */         catch (RuntimeException e) {
/* 429 */           this.icc_profile = null;
/*     */         }
/*     */       } else {
/* 432 */         if ("IEND".equals(marker))
/*     */         {
/*     */           break;
/*     */         }
/* 436 */         Utilities.skip(this.is, len);
/*     */       }
/* 438 */       Utilities.skip(this.is, 4);
/*     */     }
/*     */   }
/*     */ 
/*     */   PdfObject getColorspace() {
/* 443 */     if (this.icc_profile != null) {
/* 444 */       if ((this.colorType & 0x2) == 0) {
/* 445 */         return PdfName.DEVICEGRAY;
/*     */       }
/* 447 */       return PdfName.DEVICERGB;
/*     */     }
/* 449 */     if ((this.gamma == 1.0F) && (!this.hasCHRM)) {
/* 450 */       if ((this.colorType & 0x2) == 0) {
/* 451 */         return PdfName.DEVICEGRAY;
/*     */       }
/* 453 */       return PdfName.DEVICERGB;
/*     */     }
/*     */ 
/* 456 */     PdfArray array = new PdfArray();
/* 457 */     PdfDictionary dic = new PdfDictionary();
/* 458 */     if ((this.colorType & 0x2) == 0) {
/* 459 */       if (this.gamma == 1.0F)
/* 460 */         return PdfName.DEVICEGRAY;
/* 461 */       array.add(PdfName.CALGRAY);
/* 462 */       dic.put(PdfName.GAMMA, new PdfNumber(this.gamma));
/* 463 */       dic.put(PdfName.WHITEPOINT, new PdfLiteral("[1 1 1]"));
/* 464 */       array.add(dic);
/*     */     }
/*     */     else {
/* 467 */       PdfObject wp = new PdfLiteral("[1 1 1]");
/* 468 */       array.add(PdfName.CALRGB);
/* 469 */       if (this.gamma != 1.0F) {
/* 470 */         PdfArray gm = new PdfArray();
/* 471 */         PdfNumber n = new PdfNumber(this.gamma);
/* 472 */         gm.add(n);
/* 473 */         gm.add(n);
/* 474 */         gm.add(n);
/* 475 */         dic.put(PdfName.GAMMA, gm);
/*     */       }
/* 477 */       if (this.hasCHRM) {
/* 478 */         float z = this.yW * ((this.xG - this.xB) * this.yR - (this.xR - this.xB) * this.yG + (this.xR - this.xG) * this.yB);
/* 479 */         float YA = this.yR * ((this.xG - this.xB) * this.yW - (this.xW - this.xB) * this.yG + (this.xW - this.xG) * this.yB) / z;
/* 480 */         float XA = YA * this.xR / this.yR;
/* 481 */         float ZA = YA * ((1.0F - this.xR) / this.yR - 1.0F);
/* 482 */         float YB = -this.yG * ((this.xR - this.xB) * this.yW - (this.xW - this.xB) * this.yR + (this.xW - this.xR) * this.yB) / z;
/* 483 */         float XB = YB * this.xG / this.yG;
/* 484 */         float ZB = YB * ((1.0F - this.xG) / this.yG - 1.0F);
/* 485 */         float YC = this.yB * ((this.xR - this.xG) * this.yW - (this.xW - this.xG) * this.yW + (this.xW - this.xR) * this.yG) / z;
/* 486 */         float XC = YC * this.xB / this.yB;
/* 487 */         float ZC = YC * ((1.0F - this.xB) / this.yB - 1.0F);
/* 488 */         float XW = XA + XB + XC;
/* 489 */         float YW = 1.0F;
/* 490 */         float ZW = ZA + ZB + ZC;
/* 491 */         PdfArray wpa = new PdfArray();
/* 492 */         wpa.add(new PdfNumber(XW));
/* 493 */         wpa.add(new PdfNumber(YW));
/* 494 */         wpa.add(new PdfNumber(ZW));
/* 495 */         wp = wpa;
/* 496 */         PdfArray matrix = new PdfArray();
/* 497 */         matrix.add(new PdfNumber(XA));
/* 498 */         matrix.add(new PdfNumber(YA));
/* 499 */         matrix.add(new PdfNumber(ZA));
/* 500 */         matrix.add(new PdfNumber(XB));
/* 501 */         matrix.add(new PdfNumber(YB));
/* 502 */         matrix.add(new PdfNumber(ZB));
/* 503 */         matrix.add(new PdfNumber(XC));
/* 504 */         matrix.add(new PdfNumber(YC));
/* 505 */         matrix.add(new PdfNumber(ZC));
/* 506 */         dic.put(PdfName.MATRIX, matrix);
/*     */       }
/* 508 */       dic.put(PdfName.WHITEPOINT, wp);
/* 509 */       array.add(dic);
/*     */     }
/* 511 */     return array;
/*     */   }
/*     */ 
/*     */   Image getImage() throws IOException
/*     */   {
/* 516 */     readPng();
/*     */     try {
/* 518 */       int pal0 = 0;
/* 519 */       int palIdx = 0;
/* 520 */       this.palShades = false;
/* 521 */       if (this.trans != null) {
/* 522 */         for (int k = 0; k < this.trans.length; k++) {
/* 523 */           int n = this.trans[k] & 0xFF;
/* 524 */           if (n == 0) {
/* 525 */             pal0++;
/* 526 */             palIdx = k;
/*     */           }
/* 528 */           if ((n != 0) && (n != 255)) {
/* 529 */             this.palShades = true;
/* 530 */             break;
/*     */           }
/*     */         }
/*     */       }
/* 534 */       if ((this.colorType & 0x4) != 0)
/* 535 */         this.palShades = true;
/* 536 */       this.genBWMask = ((!this.palShades) && ((pal0 > 1) || (this.transRedGray >= 0)));
/* 537 */       if ((!this.palShades) && (!this.genBWMask) && (pal0 == 1)) {
/* 538 */         this.additional.put(PdfName.MASK, new PdfLiteral("[" + palIdx + " " + palIdx + "]"));
/*     */       }
/* 540 */       boolean needDecode = (this.interlaceMethod == 1) || (this.bitDepth == 16) || ((this.colorType & 0x4) != 0) || (this.palShades) || (this.genBWMask);
/* 541 */       switch (this.colorType) {
/*     */       case 0:
/* 543 */         this.inputBands = 1;
/* 544 */         break;
/*     */       case 2:
/* 546 */         this.inputBands = 3;
/* 547 */         break;
/*     */       case 3:
/* 549 */         this.inputBands = 1;
/* 550 */         break;
/*     */       case 4:
/* 552 */         this.inputBands = 2;
/* 553 */         break;
/*     */       case 6:
/* 555 */         this.inputBands = 4;
/*     */       case 1:
/*     */       case 5:
/* 558 */       }if (needDecode)
/* 559 */         decodeIdat();
/* 560 */       int components = this.inputBands;
/* 561 */       if ((this.colorType & 0x4) != 0)
/* 562 */         components--;
/* 563 */       int bpc = this.bitDepth;
/* 564 */       if (bpc == 16)
/* 565 */         bpc = 8;
/*     */       Image img;
/*     */       Image img;
/* 567 */       if (this.image != null)
/*     */       {
/*     */         Image img;
/* 568 */         if (this.colorType == 3)
/* 569 */           img = new ImgRaw(this.width, this.height, components, bpc, this.image);
/*     */         else
/* 571 */           img = Image.getInstance(this.width, this.height, components, bpc, this.image);
/*     */       }
/*     */       else {
/* 574 */         img = new ImgRaw(this.width, this.height, components, bpc, this.idat.toByteArray());
/* 575 */         img.setDeflated(true);
/* 576 */         PdfDictionary decodeparms = new PdfDictionary();
/* 577 */         decodeparms.put(PdfName.BITSPERCOMPONENT, new PdfNumber(this.bitDepth));
/* 578 */         decodeparms.put(PdfName.PREDICTOR, new PdfNumber(15));
/* 579 */         decodeparms.put(PdfName.COLUMNS, new PdfNumber(this.width));
/* 580 */         decodeparms.put(PdfName.COLORS, new PdfNumber((this.colorType == 3) || ((this.colorType & 0x2) == 0) ? 1 : 3));
/* 581 */         this.additional.put(PdfName.DECODEPARMS, decodeparms);
/*     */       }
/* 583 */       if (this.additional.get(PdfName.COLORSPACE) == null)
/* 584 */         this.additional.put(PdfName.COLORSPACE, getColorspace());
/* 585 */       if (this.intent != null)
/* 586 */         this.additional.put(PdfName.INTENT, this.intent);
/* 587 */       if (this.additional.size() > 0)
/* 588 */         img.setAdditional(this.additional);
/* 589 */       if (this.icc_profile != null)
/* 590 */         img.tagICC(this.icc_profile);
/* 591 */       if (this.palShades) {
/* 592 */         Image im2 = Image.getInstance(this.width, this.height, 1, 8, this.smask);
/* 593 */         im2.makeMask();
/* 594 */         img.setImageMask(im2);
/*     */       }
/* 596 */       if (this.genBWMask) {
/* 597 */         Image im2 = Image.getInstance(this.width, this.height, 1, 1, this.smask);
/* 598 */         im2.makeMask();
/* 599 */         img.setImageMask(im2);
/*     */       }
/* 601 */       img.setDpi(this.dpiX, this.dpiY);
/* 602 */       img.setXYRatio(this.XYRatio);
/* 603 */       img.setOriginalType(2);
/* 604 */       return img;
/*     */     }
/*     */     catch (Exception e) {
/* 607 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   void decodeIdat() {
/* 612 */     int nbitDepth = this.bitDepth;
/* 613 */     if (nbitDepth == 16)
/* 614 */       nbitDepth = 8;
/* 615 */     int size = -1;
/* 616 */     this.bytesPerPixel = (this.bitDepth == 16 ? 2 : 1);
/* 617 */     switch (this.colorType) {
/*     */     case 0:
/* 619 */       size = (nbitDepth * this.width + 7) / 8 * this.height;
/* 620 */       break;
/*     */     case 2:
/* 622 */       size = this.width * 3 * this.height;
/* 623 */       this.bytesPerPixel *= 3;
/* 624 */       break;
/*     */     case 3:
/* 626 */       if (this.interlaceMethod == 1)
/* 627 */         size = (nbitDepth * this.width + 7) / 8 * this.height;
/* 628 */       this.bytesPerPixel = 1;
/* 629 */       break;
/*     */     case 4:
/* 631 */       size = this.width * this.height;
/* 632 */       this.bytesPerPixel *= 2;
/* 633 */       break;
/*     */     case 6:
/* 635 */       size = this.width * 3 * this.height;
/* 636 */       this.bytesPerPixel *= 4;
/*     */     case 1:
/*     */     case 5:
/* 639 */     }if (size >= 0)
/* 640 */       this.image = new byte[size];
/* 641 */     if (this.palShades)
/* 642 */       this.smask = new byte[this.width * this.height];
/* 643 */     else if (this.genBWMask)
/* 644 */       this.smask = new byte[(this.width + 7) / 8 * this.height];
/* 645 */     ByteArrayInputStream bai = new ByteArrayInputStream(this.idat.getBuf(), 0, this.idat.size());
/* 646 */     InputStream infStream = new InflaterInputStream(bai, new Inflater());
/* 647 */     this.dataStream = new DataInputStream(infStream);
/*     */ 
/* 649 */     if (this.interlaceMethod != 1) {
/* 650 */       decodePass(0, 0, 1, 1, this.width, this.height);
/*     */     }
/*     */     else {
/* 653 */       decodePass(0, 0, 8, 8, (this.width + 7) / 8, (this.height + 7) / 8);
/* 654 */       decodePass(4, 0, 8, 8, (this.width + 3) / 8, (this.height + 7) / 8);
/* 655 */       decodePass(0, 4, 4, 8, (this.width + 3) / 4, (this.height + 3) / 8);
/* 656 */       decodePass(2, 0, 4, 4, (this.width + 1) / 4, (this.height + 3) / 4);
/* 657 */       decodePass(0, 2, 2, 4, (this.width + 1) / 2, (this.height + 1) / 4);
/* 658 */       decodePass(1, 0, 2, 2, this.width / 2, (this.height + 1) / 2);
/* 659 */       decodePass(0, 1, 1, 2, this.width, this.height / 2);
/*     */     }
/*     */   }
/*     */ 
/*     */   void decodePass(int xOffset, int yOffset, int xStep, int yStep, int passWidth, int passHeight)
/*     */   {
/* 667 */     if ((passWidth == 0) || (passHeight == 0)) {
/* 668 */       return;
/*     */     }
/*     */ 
/* 671 */     int bytesPerRow = (this.inputBands * passWidth * this.bitDepth + 7) / 8;
/* 672 */     byte[] curr = new byte[bytesPerRow];
/* 673 */     byte[] prior = new byte[bytesPerRow];
/*     */ 
/* 677 */     int srcY = 0; int dstY = yOffset;
/*     */ 
/* 679 */     for (; srcY < passHeight; 
/* 679 */       dstY += yStep)
/*     */     {
/* 681 */       int filter = 0;
/*     */       try {
/* 683 */         filter = this.dataStream.read();
/* 684 */         this.dataStream.readFully(curr, 0, bytesPerRow);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */       }
/* 689 */       switch (filter) {
/*     */       case 0:
/* 691 */         break;
/*     */       case 1:
/* 693 */         decodeSubFilter(curr, bytesPerRow, this.bytesPerPixel);
/* 694 */         break;
/*     */       case 2:
/* 696 */         decodeUpFilter(curr, prior, bytesPerRow);
/* 697 */         break;
/*     */       case 3:
/* 699 */         decodeAverageFilter(curr, prior, bytesPerRow, this.bytesPerPixel);
/* 700 */         break;
/*     */       case 4:
/* 702 */         decodePaethFilter(curr, prior, bytesPerRow, this.bytesPerPixel);
/* 703 */         break;
/*     */       default:
/* 706 */         throw new RuntimeException(MessageLocalization.getComposedMessage("png.filter.unknown", new Object[0]));
/*     */       }
/*     */ 
/* 709 */       processPixels(curr, xOffset, xStep, dstY, passWidth);
/*     */ 
/* 712 */       byte[] tmp = prior;
/* 713 */       prior = curr;
/* 714 */       curr = tmp;
/*     */ 
/* 679 */       srcY++;
/*     */     }
/*     */   }
/*     */ 
/*     */   void processPixels(byte[] curr, int xOffset, int step, int y, int width)
/*     */   {
/* 721 */     int[] out = getPixel(curr);
/* 722 */     int sizes = 0;
/* 723 */     switch (this.colorType) {
/*     */     case 0:
/*     */     case 3:
/*     */     case 4:
/* 727 */       sizes = 1;
/* 728 */       break;
/*     */     case 2:
/*     */     case 6:
/* 731 */       sizes = 3;
/*     */     case 1:
/*     */     case 5:
/* 734 */     }if (this.image != null) {
/* 735 */       int dstX = xOffset;
/* 736 */       int yStride = (sizes * this.width * (this.bitDepth == 16 ? 8 : this.bitDepth) + 7) / 8;
/* 737 */       for (int srcX = 0; srcX < width; srcX++) {
/* 738 */         setPixel(this.image, out, this.inputBands * srcX, sizes, dstX, y, this.bitDepth, yStride);
/* 739 */         dstX += step;
/*     */       }
/*     */     }
/*     */     int yStride;
/*     */     int[] v;
/*     */     int dstX;
/*     */     int srcX;
/* 742 */     if (this.palShades) {
/* 743 */       if ((this.colorType & 0x4) != 0) {
/* 744 */         if (this.bitDepth == 16) {
/* 745 */           for (int k = 0; k < width; k++)
/* 746 */             out[(k * this.inputBands + sizes)] >>>= 8;
/*     */         }
/* 748 */         int yStride = this.width;
/* 749 */         int dstX = xOffset;
/* 750 */         for (int srcX = 0; srcX < width; srcX++) {
/* 751 */           setPixel(this.smask, out, this.inputBands * srcX + sizes, 1, dstX, y, 8, yStride);
/* 752 */           dstX += step;
/*     */         }
/*     */       }
/*     */       else {
/* 756 */         int yStride = this.width;
/* 757 */         int[] v = new int[1];
/* 758 */         int dstX = xOffset;
/* 759 */         for (int srcX = 0; srcX < width; srcX++) {
/* 760 */           int idx = out[srcX];
/* 761 */           if (idx < this.trans.length)
/* 762 */             v[0] = this.trans[idx];
/*     */           else
/* 764 */             v[0] = 255;
/* 765 */           setPixel(this.smask, v, 0, 1, dstX, y, 8, yStride);
/* 766 */           dstX += step;
/*     */         }
/*     */       }
/*     */     }
/* 770 */     else if (this.genBWMask)
/* 771 */       switch (this.colorType) {
/*     */       case 3:
/* 773 */         yStride = (this.width + 7) / 8;
/* 774 */         v = new int[1];
/* 775 */         dstX = xOffset;
/* 776 */         for (srcX = 0; srcX < width; ) {
/* 777 */           int idx = out[srcX];
/* 778 */           v[0] = ((idx < this.trans.length) && (this.trans[idx] == 0) ? 1 : 0);
/* 779 */           setPixel(this.smask, v, 0, 1, dstX, y, 1, yStride);
/* 780 */           dstX += step;
/*     */ 
/* 776 */           srcX++; continue;
/*     */ 
/* 785 */           yStride = (this.width + 7) / 8;
/* 786 */           v = new int[1];
/* 787 */           dstX = xOffset;
/* 788 */           for (srcX = 0; srcX < width; ) {
/* 789 */             int g = out[srcX];
/* 790 */             v[0] = (g == this.transRedGray ? 1 : 0);
/* 791 */             setPixel(this.smask, v, 0, 1, dstX, y, 1, yStride);
/* 792 */             dstX += step;
/*     */ 
/* 788 */             srcX++; continue;
/*     */ 
/* 797 */             int yStride = (this.width + 7) / 8;
/* 798 */             int[] v = new int[1];
/* 799 */             dstX = xOffset;
/* 800 */             for (srcX = 0; srcX < width; srcX++) {
/* 801 */               int markRed = this.inputBands * srcX;
/* 802 */               v[0] = ((out[markRed] == this.transRedGray) && (out[(markRed + 1)] == this.transGreen) && (out[(markRed + 2)] == this.transBlue) ? 1 : 0);
/*     */ 
/* 804 */               setPixel(this.smask, v, 0, 1, dstX, y, 1, yStride);
/* 805 */               dstX += step; }  } 
/*     */         }case 0:
/*     */       case 2:
/*     */       case 1:
/*     */       } int yStride;
/*     */     int[] v;
/*     */   }
/*     */ 
/* 814 */   static int getPixel(byte[] image, int x, int y, int bitDepth, int bytesPerRow) { if (bitDepth == 8) {
/* 815 */       int pos = bytesPerRow * y + x;
/* 816 */       return image[pos] & 0xFF;
/*     */     }
/*     */ 
/* 819 */     int pos = bytesPerRow * y + x / (8 / bitDepth);
/* 820 */     int v = image[pos] >> 8 - bitDepth * (x % (8 / bitDepth)) - bitDepth;
/* 821 */     return v & (1 << bitDepth) - 1;
/*     */   }
/*     */ 
/*     */   static void setPixel(byte[] image, int[] data, int offset, int size, int x, int y, int bitDepth, int bytesPerRow)
/*     */   {
/* 826 */     if (bitDepth == 8) {
/* 827 */       int pos = bytesPerRow * y + size * x;
/* 828 */       for (int k = 0; k < size; k++)
/* 829 */         image[(pos + k)] = ((byte)data[(k + offset)]);
/*     */     }
/* 831 */     else if (bitDepth == 16) {
/* 832 */       int pos = bytesPerRow * y + size * x;
/* 833 */       for (int k = 0; k < size; k++)
/* 834 */         image[(pos + k)] = ((byte)(data[(k + offset)] >>> 8));
/*     */     }
/*     */     else {
/* 837 */       int pos = bytesPerRow * y + x / (8 / bitDepth);
/* 838 */       int v = data[offset] << 8 - bitDepth * (x % (8 / bitDepth)) - bitDepth;
/*     */       int tmp147_145 = pos; image[tmp147_145] = ((byte)(image[tmp147_145] | v));
/*     */     }
/*     */   }
/*     */ 
/*     */   int[] getPixel(byte[] curr) {
/* 844 */     switch (this.bitDepth) {
/*     */     case 8:
/* 846 */       int[] out = new int[curr.length];
/* 847 */       for (int k = 0; k < out.length; k++)
/* 848 */         curr[k] &= 255;
/* 849 */       return out;
/*     */     case 16:
/* 852 */       int[] out = new int[curr.length / 2];
/* 853 */       for (int k = 0; k < out.length; k++)
/* 854 */         out[k] = (((curr[(k * 2)] & 0xFF) << 8) + (curr[(k * 2 + 1)] & 0xFF));
/* 855 */       return out;
/*     */     }
/*     */ 
/* 858 */     int[] out = new int[curr.length * 8 / this.bitDepth];
/* 859 */     int idx = 0;
/* 860 */     int passes = 8 / this.bitDepth;
/* 861 */     int mask = (1 << this.bitDepth) - 1;
/* 862 */     for (int k = 0; k < curr.length; k++) {
/* 863 */       for (int j = passes - 1; j >= 0; j--) {
/* 864 */         out[(idx++)] = (curr[k] >>> this.bitDepth * j & mask);
/*     */       }
/*     */     }
/* 867 */     return out;
/*     */   }
/*     */ 
/*     */   private static void decodeSubFilter(byte[] curr, int count, int bpp)
/*     */   {
/* 873 */     for (int i = bpp; i < count; i++)
/*     */     {
/* 876 */       int val = curr[i] & 0xFF;
/* 877 */       val += (curr[(i - bpp)] & 0xFF);
/*     */ 
/* 879 */       curr[i] = ((byte)val);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void decodeUpFilter(byte[] curr, byte[] prev, int count)
/*     */   {
/* 885 */     for (int i = 0; i < count; i++) {
/* 886 */       int raw = curr[i] & 0xFF;
/* 887 */       int prior = prev[i] & 0xFF;
/*     */ 
/* 889 */       curr[i] = ((byte)(raw + prior));
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void decodeAverageFilter(byte[] curr, byte[] prev, int count, int bpp)
/*     */   {
/* 897 */     for (int i = 0; i < bpp; i++) {
/* 898 */       int raw = curr[i] & 0xFF;
/* 899 */       int priorRow = prev[i] & 0xFF;
/*     */ 
/* 901 */       curr[i] = ((byte)(raw + priorRow / 2));
/*     */     }
/*     */ 
/* 904 */     for (int i = bpp; i < count; i++) {
/* 905 */       int raw = curr[i] & 0xFF;
/* 906 */       int priorPixel = curr[(i - bpp)] & 0xFF;
/* 907 */       int priorRow = prev[i] & 0xFF;
/*     */ 
/* 909 */       curr[i] = ((byte)(raw + (priorPixel + priorRow) / 2));
/*     */     }
/*     */   }
/*     */ 
/*     */   private static int paethPredictor(int a, int b, int c) {
/* 914 */     int p = a + b - c;
/* 915 */     int pa = Math.abs(p - a);
/* 916 */     int pb = Math.abs(p - b);
/* 917 */     int pc = Math.abs(p - c);
/*     */ 
/* 919 */     if ((pa <= pb) && (pa <= pc))
/* 920 */       return a;
/* 921 */     if (pb <= pc) {
/* 922 */       return b;
/*     */     }
/* 924 */     return c;
/*     */   }
/*     */ 
/*     */   private static void decodePaethFilter(byte[] curr, byte[] prev, int count, int bpp)
/*     */   {
/* 932 */     for (int i = 0; i < bpp; i++) {
/* 933 */       int raw = curr[i] & 0xFF;
/* 934 */       int priorRow = prev[i] & 0xFF;
/*     */ 
/* 936 */       curr[i] = ((byte)(raw + priorRow));
/*     */     }
/*     */ 
/* 939 */     for (int i = bpp; i < count; i++) {
/* 940 */       int raw = curr[i] & 0xFF;
/* 941 */       int priorPixel = curr[(i - bpp)] & 0xFF;
/* 942 */       int priorRow = prev[i] & 0xFF;
/* 943 */       int priorRowPixel = prev[(i - bpp)] & 0xFF;
/*     */ 
/* 945 */       curr[i] = ((byte)(raw + paethPredictor(priorPixel, priorRow, priorRowPixel)));
/*     */     }
/*     */   }
/*     */ 
/*     */   public static final int getInt(InputStream is)
/*     */     throws IOException
/*     */   {
/* 965 */     return (is.read() << 24) + (is.read() << 16) + (is.read() << 8) + is.read();
/*     */   }
/*     */ 
/*     */   public static final int getWord(InputStream is)
/*     */     throws IOException
/*     */   {
/* 976 */     return (is.read() << 8) + is.read();
/*     */   }
/*     */ 
/*     */   public static final String getString(InputStream is)
/*     */     throws IOException
/*     */   {
/* 987 */     StringBuffer buf = new StringBuffer();
/* 988 */     for (int i = 0; i < 4; i++) {
/* 989 */       buf.append((char)is.read());
/*     */     }
/* 991 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   static class NewByteArrayOutputStream extends ByteArrayOutputStream
/*     */   {
/*     */     public byte[] getBuf()
/*     */     {
/* 953 */       return this.buf;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.codec.PngImage
 * JD-Core Version:    0.6.2
 */