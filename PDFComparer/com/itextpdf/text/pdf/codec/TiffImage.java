/*     */ package com.itextpdf.text.pdf.codec;
/*     */ 
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.Image;
/*     */ import com.itextpdf.text.ImgRaw;
/*     */ import com.itextpdf.text.Jpeg;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.exceptions.InvalidImageException;
/*     */ import com.itextpdf.text.pdf.ICC_Profile;
/*     */ import com.itextpdf.text.pdf.PdfArray;
/*     */ import com.itextpdf.text.pdf.PdfDictionary;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfNumber;
/*     */ import com.itextpdf.text.pdf.PdfString;
/*     */ import com.itextpdf.text.pdf.RandomAccessFileOrArray;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.zip.DataFormatException;
/*     */ import java.util.zip.DeflaterOutputStream;
/*     */ import java.util.zip.Inflater;
/*     */ 
/*     */ public class TiffImage
/*     */ {
/*     */   public static int getNumberOfPages(RandomAccessFileOrArray s)
/*     */   {
/*     */     try
/*     */     {
/*  77 */       return TIFFDirectory.getNumDirectories(s);
/*     */     }
/*     */     catch (Exception e) {
/*  80 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   static int getDpi(TIFFField fd, int resolutionUnit) {
/*  85 */     if (fd == null)
/*  86 */       return 0;
/*  87 */     long[] res = fd.getAsRational(0);
/*  88 */     float frac = (float)res[0] / (float)res[1];
/*  89 */     int dpi = 0;
/*  90 */     switch (resolutionUnit) {
/*     */     case 1:
/*     */     case 2:
/*  93 */       dpi = (int)(frac + 0.5D);
/*  94 */       break;
/*     */     case 3:
/*  96 */       dpi = (int)(frac * 2.54D + 0.5D);
/*     */     }
/*     */ 
/*  99 */     return dpi;
/*     */   }
/*     */ 
/*     */   public static Image getTiffImage(RandomAccessFileOrArray s, boolean recoverFromImageError, int page, boolean direct) {
/* 103 */     if (page < 1)
/* 104 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.page.number.must.be.gt.eq.1", new Object[0]));
/*     */     try {
/* 106 */       TIFFDirectory dir = new TIFFDirectory(s, page - 1);
/* 107 */       if (dir.isTagPresent(322))
/* 108 */         throw new IllegalArgumentException(MessageLocalization.getComposedMessage("tiles.are.not.supported", new Object[0]));
/* 109 */       int compression = (int)dir.getFieldAsLong(259);
/* 110 */       switch (compression) {
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 32771:
/* 115 */         break;
/*     */       default:
/* 117 */         return getTiffImageColor(dir, s);
/*     */       }
/* 119 */       float rotation = 0.0F;
/* 120 */       if (dir.isTagPresent(274)) {
/* 121 */         int rot = (int)dir.getFieldAsLong(274);
/* 122 */         if ((rot == 3) || (rot == 4))
/* 123 */           rotation = 3.141593F;
/* 124 */         else if ((rot == 5) || (rot == 8))
/* 125 */           rotation = 1.570796F;
/* 126 */         else if ((rot == 6) || (rot == 7)) {
/* 127 */           rotation = -1.570796F;
/*     */         }
/*     */       }
/* 130 */       Image img = null;
/* 131 */       long tiffT4Options = 0L;
/* 132 */       long tiffT6Options = 0L;
/* 133 */       int fillOrder = 1;
/* 134 */       int h = (int)dir.getFieldAsLong(257);
/* 135 */       int w = (int)dir.getFieldAsLong(256);
/* 136 */       int dpiX = 0;
/* 137 */       int dpiY = 0;
/* 138 */       float XYRatio = 0.0F;
/* 139 */       int resolutionUnit = 2;
/* 140 */       if (dir.isTagPresent(296))
/* 141 */         resolutionUnit = (int)dir.getFieldAsLong(296);
/* 142 */       dpiX = getDpi(dir.getField(282), resolutionUnit);
/* 143 */       dpiY = getDpi(dir.getField(283), resolutionUnit);
/* 144 */       if (resolutionUnit == 1) {
/* 145 */         if (dpiY != 0)
/* 146 */           XYRatio = dpiX / dpiY;
/* 147 */         dpiX = 0;
/* 148 */         dpiY = 0;
/*     */       }
/* 150 */       int rowsStrip = h;
/* 151 */       if (dir.isTagPresent(278))
/* 152 */         rowsStrip = (int)dir.getFieldAsLong(278);
/* 153 */       if ((rowsStrip <= 0) || (rowsStrip > h))
/* 154 */         rowsStrip = h;
/* 155 */       long[] offset = getArrayLongShort(dir, 273);
/* 156 */       long[] size = getArrayLongShort(dir, 279);
/* 157 */       if (((size == null) || ((size.length == 1) && ((size[0] == 0L) || (size[0] + offset[0] > s.length())))) && (h == rowsStrip)) {
/* 158 */         size = new long[] { s.length() - (int)offset[0] };
/*     */       }
/* 160 */       boolean reverse = false;
/* 161 */       TIFFField fillOrderField = dir.getField(266);
/* 162 */       if (fillOrderField != null)
/* 163 */         fillOrder = fillOrderField.getAsInt(0);
/* 164 */       reverse = fillOrder == 2;
/* 165 */       int params = 0;
/* 166 */       if (dir.isTagPresent(262)) {
/* 167 */         long photo = dir.getFieldAsLong(262);
/* 168 */         if (photo == 1L)
/* 169 */           params |= 1;
/*     */       }
/* 171 */       int imagecomp = 0;
/* 172 */       switch (compression) {
/*     */       case 2:
/*     */       case 32771:
/* 175 */         imagecomp = 257;
/* 176 */         params |= 10;
/* 177 */         break;
/*     */       case 3:
/* 179 */         imagecomp = 257;
/* 180 */         params |= 12;
/* 181 */         TIFFField t4OptionsField = dir.getField(292);
/* 182 */         if (t4OptionsField != null) {
/* 183 */           tiffT4Options = t4OptionsField.getAsLong(0);
/* 184 */           if ((tiffT4Options & 1L) != 0L)
/* 185 */             imagecomp = 258;
/* 186 */           if ((tiffT4Options & 0x4) != 0L)
/* 187 */             params |= 2;  } break;
/*     */       case 4:
/* 191 */         imagecomp = 256;
/* 192 */         TIFFField t6OptionsField = dir.getField(293);
/* 193 */         if (t6OptionsField != null)
/* 194 */           tiffT6Options = t6OptionsField.getAsLong(0);
/*     */         break;
/*     */       }
/* 197 */       if ((direct) && (rowsStrip == h)) {
/* 198 */         byte[] im = new byte[(int)size[0]];
/* 199 */         s.seek(offset[0]);
/* 200 */         s.readFully(im);
/* 201 */         img = Image.getInstance(w, h, false, imagecomp, params, im);
/* 202 */         img.setInverted(true);
/*     */       }
/*     */       else {
/* 205 */         int rowsLeft = h;
/* 206 */         CCITTG4Encoder g4 = new CCITTG4Encoder(w);
/* 207 */         for (int k = 0; k < offset.length; k++) {
/* 208 */           byte[] im = new byte[(int)size[k]];
/* 209 */           s.seek(offset[k]);
/* 210 */           s.readFully(im);
/* 211 */           int height = Math.min(rowsStrip, rowsLeft);
/* 212 */           TIFFFaxDecoder decoder = new TIFFFaxDecoder(fillOrder, w, height);
/* 213 */           decoder.setRecoverFromImageError(recoverFromImageError);
/* 214 */           byte[] outBuf = new byte[(w + 7) / 8 * height];
/* 215 */           switch (compression) {
/*     */           case 2:
/*     */           case 32771:
/* 218 */             decoder.decode1D(outBuf, im, 0, height);
/* 219 */             g4.fax4Encode(outBuf, height);
/* 220 */             break;
/*     */           case 3:
/*     */             try {
/* 223 */               decoder.decode2D(outBuf, im, 0, height, tiffT4Options);
/*     */             }
/*     */             catch (RuntimeException e)
/*     */             {
/* 227 */               tiffT4Options ^= 4L;
/*     */               try {
/* 229 */                 decoder.decode2D(outBuf, im, 0, height, tiffT4Options);
/*     */               }
/*     */               catch (RuntimeException e2) {
/* 232 */                 if (!recoverFromImageError)
/* 233 */                   throw e;
/* 234 */                 if (rowsStrip == 1) {
/* 235 */                   throw e;
/*     */                 }
/*     */ 
/* 238 */                 im = new byte[(int)size[0]];
/* 239 */                 s.seek(offset[0]);
/* 240 */                 s.readFully(im);
/* 241 */                 img = Image.getInstance(w, h, false, imagecomp, params, im);
/* 242 */                 img.setInverted(true);
/* 243 */                 img.setDpi(dpiX, dpiY);
/* 244 */                 img.setXYRatio(XYRatio);
/* 245 */                 img.setOriginalType(5);
/* 246 */                 if (rotation != 0.0F)
/* 247 */                   img.setInitialRotation(rotation);
/* 248 */                 return img;
/*     */               }
/*     */             }
/* 251 */             g4.fax4Encode(outBuf, height);
/* 252 */             break;
/*     */           case 4:
/*     */             try {
/* 255 */               decoder.decodeT6(outBuf, im, 0, height, tiffT6Options);
/*     */             } catch (InvalidImageException e) {
/* 257 */               if (!recoverFromImageError) {
/* 258 */                 throw e;
/*     */               }
/*     */             }
/*     */ 
/* 262 */             g4.fax4Encode(outBuf, height);
/*     */           }
/*     */ 
/* 265 */           rowsLeft -= rowsStrip;
/*     */         }
/* 267 */         byte[] g4pic = g4.close();
/* 268 */         img = Image.getInstance(w, h, false, 256, params & 0x1, g4pic);
/*     */       }
/* 270 */       img.setDpi(dpiX, dpiY);
/* 271 */       img.setXYRatio(XYRatio);
/* 272 */       if (dir.isTagPresent(34675)) {
/*     */         try {
/* 274 */           TIFFField fd = dir.getField(34675);
/* 275 */           ICC_Profile icc_prof = ICC_Profile.getInstance(fd.getAsBytes());
/* 276 */           if (icc_prof.getNumComponents() == 1)
/* 277 */             img.tagICC(icc_prof);
/*     */         }
/*     */         catch (RuntimeException e)
/*     */         {
/*     */         }
/*     */       }
/* 283 */       img.setOriginalType(5);
/* 284 */       if (rotation != 0.0F)
/* 285 */         img.setInitialRotation(rotation);
/* 286 */       return img;
/*     */     }
/*     */     catch (Exception e) {
/* 289 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Image getTiffImage(RandomAccessFileOrArray s, boolean recoverFromImageError, int page) {
/* 294 */     return getTiffImage(s, recoverFromImageError, page, false);
/*     */   }
/*     */ 
/*     */   public static Image getTiffImage(RandomAccessFileOrArray s, int page)
/*     */   {
/* 303 */     return getTiffImage(s, page, false);
/*     */   }
/*     */ 
/*     */   public static Image getTiffImage(RandomAccessFileOrArray s, int page, boolean direct)
/*     */   {
/* 315 */     return getTiffImage(s, false, page, direct);
/*     */   }
/*     */ 
/*     */   protected static Image getTiffImageColor(TIFFDirectory dir, RandomAccessFileOrArray s) {
/*     */     try {
/* 320 */       int compression = (int)dir.getFieldAsLong(259);
/* 321 */       int predictor = 1;
/* 322 */       TIFFLZWDecoder lzwDecoder = null;
/* 323 */       switch (compression) {
/*     */       case 1:
/*     */       case 5:
/*     */       case 6:
/*     */       case 7:
/*     */       case 8:
/*     */       case 32773:
/*     */       case 32946:
/* 331 */         break;
/*     */       default:
/* 333 */         throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.compression.1.is.not.supported", compression));
/*     */       }
/* 335 */       int photometric = (int)dir.getFieldAsLong(262);
/* 336 */       switch (photometric) {
/*     */       case 0:
/*     */       case 1:
/*     */       case 2:
/*     */       case 3:
/*     */       case 5:
/* 342 */         break;
/*     */       case 4:
/*     */       default:
/* 344 */         if ((compression != 6) && (compression != 7))
/* 345 */           throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.photometric.1.is.not.supported", photometric)); break;
/*     */       }
/* 347 */       float rotation = 0.0F;
/* 348 */       if (dir.isTagPresent(274)) {
/* 349 */         int rot = (int)dir.getFieldAsLong(274);
/* 350 */         if ((rot == 3) || (rot == 4))
/* 351 */           rotation = 3.141593F;
/* 352 */         else if ((rot == 5) || (rot == 8))
/* 353 */           rotation = 1.570796F;
/* 354 */         else if ((rot == 6) || (rot == 7))
/* 355 */           rotation = -1.570796F;
/*     */       }
/* 357 */       if ((dir.isTagPresent(284)) && (dir.getFieldAsLong(284) == 2L))
/*     */       {
/* 359 */         throw new IllegalArgumentException(MessageLocalization.getComposedMessage("planar.images.are.not.supported", new Object[0]));
/* 360 */       }int extraSamples = 0;
/* 361 */       if (dir.isTagPresent(338))
/* 362 */         extraSamples = 1;
/* 363 */       int samplePerPixel = 1;
/* 364 */       if (dir.isTagPresent(277))
/* 365 */         samplePerPixel = (int)dir.getFieldAsLong(277);
/* 366 */       int bitsPerSample = 1;
/* 367 */       if (dir.isTagPresent(258))
/* 368 */         bitsPerSample = (int)dir.getFieldAsLong(258);
/* 369 */       switch (bitsPerSample) {
/*     */       case 1:
/*     */       case 2:
/*     */       case 4:
/*     */       case 8:
/* 374 */         break;
/*     */       case 3:
/*     */       case 5:
/*     */       case 6:
/*     */       case 7:
/*     */       default:
/* 376 */         throw new IllegalArgumentException(MessageLocalization.getComposedMessage("bits.per.sample.1.is.not.supported", bitsPerSample));
/*     */       }
/* 378 */       Image img = null;
/*     */ 
/* 380 */       int h = (int)dir.getFieldAsLong(257);
/* 381 */       int w = (int)dir.getFieldAsLong(256);
/* 382 */       int dpiX = 0;
/* 383 */       int dpiY = 0;
/* 384 */       int resolutionUnit = 2;
/* 385 */       if (dir.isTagPresent(296))
/* 386 */         resolutionUnit = (int)dir.getFieldAsLong(296);
/* 387 */       dpiX = getDpi(dir.getField(282), resolutionUnit);
/* 388 */       dpiY = getDpi(dir.getField(283), resolutionUnit);
/* 389 */       int fillOrder = 1;
/* 390 */       boolean reverse = false;
/* 391 */       TIFFField fillOrderField = dir.getField(266);
/* 392 */       if (fillOrderField != null)
/* 393 */         fillOrder = fillOrderField.getAsInt(0);
/* 394 */       reverse = fillOrder == 2;
/* 395 */       int rowsStrip = h;
/* 396 */       if (dir.isTagPresent(278))
/* 397 */         rowsStrip = (int)dir.getFieldAsLong(278);
/* 398 */       if ((rowsStrip <= 0) || (rowsStrip > h))
/* 399 */         rowsStrip = h;
/* 400 */       long[] offset = getArrayLongShort(dir, 273);
/* 401 */       long[] size = getArrayLongShort(dir, 279);
/* 402 */       if (((size == null) || ((size.length == 1) && ((size[0] == 0L) || (size[0] + offset[0] > s.length())))) && (h == rowsStrip)) {
/* 403 */         size = new long[] { s.length() - (int)offset[0] };
/*     */       }
/* 405 */       if ((compression == 5) || (compression == 32946) || (compression == 8)) {
/* 406 */         TIFFField predictorField = dir.getField(317);
/* 407 */         if (predictorField != null) {
/* 408 */           predictor = predictorField.getAsInt(0);
/* 409 */           if ((predictor != 1) && (predictor != 2)) {
/* 410 */             throw new RuntimeException(MessageLocalization.getComposedMessage("illegal.value.for.predictor.in.tiff.file", new Object[0]));
/*     */           }
/* 412 */           if ((predictor == 2) && (bitsPerSample != 8)) {
/* 413 */             throw new RuntimeException(MessageLocalization.getComposedMessage("1.bit.samples.are.not.supported.for.horizontal.differencing.predictor", bitsPerSample));
/*     */           }
/*     */         }
/*     */       }
/* 417 */       if (compression == 5) {
/* 418 */         lzwDecoder = new TIFFLZWDecoder(w, predictor, samplePerPixel);
/*     */       }
/* 420 */       int rowsLeft = h;
/* 421 */       ByteArrayOutputStream stream = null;
/* 422 */       ByteArrayOutputStream mstream = null;
/* 423 */       DeflaterOutputStream zip = null;
/* 424 */       DeflaterOutputStream mzip = null;
/* 425 */       if (extraSamples > 0) {
/* 426 */         mstream = new ByteArrayOutputStream();
/* 427 */         mzip = new DeflaterOutputStream(mstream);
/*     */       }
/*     */ 
/* 430 */       CCITTG4Encoder g4 = null;
/* 431 */       if ((bitsPerSample == 1) && (samplePerPixel == 1) && (photometric != 3)) {
/* 432 */         g4 = new CCITTG4Encoder(w);
/*     */       }
/*     */       else {
/* 435 */         stream = new ByteArrayOutputStream();
/* 436 */         if ((compression != 6) && (compression != 7))
/* 437 */           zip = new DeflaterOutputStream(stream);
/*     */       }
/* 439 */       if (compression == 6)
/*     */       {
/* 444 */         if (!dir.isTagPresent(513)) {
/* 445 */           throw new IOException(MessageLocalization.getComposedMessage("missing.tag.s.for.ojpeg.compression", new Object[0]));
/*     */         }
/* 447 */         int jpegOffset = (int)dir.getFieldAsLong(513);
/* 448 */         int jpegLength = (int)s.length() - jpegOffset;
/*     */ 
/* 450 */         if (dir.isTagPresent(514)) {
/* 451 */           jpegLength = (int)dir.getFieldAsLong(514) + (int)size[0];
/*     */         }
/*     */ 
/* 455 */         byte[] jpeg = new byte[Math.min(jpegLength, (int)s.length() - jpegOffset)];
/*     */ 
/* 457 */         int posFilePointer = (int)s.getFilePointer();
/* 458 */         posFilePointer += jpegOffset;
/* 459 */         s.seek(posFilePointer);
/* 460 */         s.readFully(jpeg);
/* 461 */         img = new Jpeg(jpeg);
/*     */       }
/* 463 */       else if (compression == 7) {
/* 464 */         if (size.length > 1)
/* 465 */           throw new IOException(MessageLocalization.getComposedMessage("compression.jpeg.is.only.supported.with.a.single.strip.this.image.has.1.strips", size.length));
/* 466 */         byte[] jpeg = new byte[(int)size[0]];
/* 467 */         s.seek(offset[0]);
/* 468 */         s.readFully(jpeg);
/*     */ 
/* 471 */         TIFFField jpegtables = dir.getField(347);
/* 472 */         if (jpegtables != null) {
/* 473 */           byte[] temp = jpegtables.getAsBytes();
/* 474 */           int tableoffset = 0;
/* 475 */           int tablelength = temp.length;
/*     */ 
/* 477 */           if ((temp[0] == -1) && (temp[1] == -40)) {
/* 478 */             tableoffset = 2;
/* 479 */             tablelength -= 2;
/*     */           }
/*     */ 
/* 482 */           if ((temp[(temp.length - 2)] == -1) && (temp[(temp.length - 1)] == -39))
/* 483 */             tablelength -= 2;
/* 484 */           byte[] tables = new byte[tablelength];
/* 485 */           System.arraycopy(temp, tableoffset, tables, 0, tablelength);
/*     */ 
/* 487 */           byte[] jpegwithtables = new byte[jpeg.length + tables.length];
/* 488 */           System.arraycopy(jpeg, 0, jpegwithtables, 0, 2);
/* 489 */           System.arraycopy(tables, 0, jpegwithtables, 2, tables.length);
/* 490 */           System.arraycopy(jpeg, 2, jpegwithtables, tables.length + 2, jpeg.length - 2);
/* 491 */           jpeg = jpegwithtables;
/*     */         }
/* 493 */         img = new Jpeg(jpeg);
/* 494 */         if (photometric == 2)
/* 495 */           img.setColorTransform(0);
/*     */       }
/*     */       else
/*     */       {
/* 499 */         for (int k = 0; k < offset.length; k++) {
/* 500 */           byte[] im = new byte[(int)size[k]];
/* 501 */           s.seek(offset[k]);
/* 502 */           s.readFully(im);
/* 503 */           int height = Math.min(rowsStrip, rowsLeft);
/* 504 */           byte[] outBuf = null;
/* 505 */           if (compression != 1)
/* 506 */             outBuf = new byte[(w * bitsPerSample * samplePerPixel + 7) / 8 * height];
/* 507 */           if (reverse)
/* 508 */             TIFFFaxDecoder.reverseBits(im);
/* 509 */           switch (compression) {
/*     */           case 8:
/*     */           case 32946:
/* 512 */             inflate(im, outBuf);
/* 513 */             applyPredictor(outBuf, predictor, w, height, samplePerPixel);
/* 514 */             break;
/*     */           case 1:
/* 516 */             outBuf = im;
/* 517 */             break;
/*     */           case 32773:
/* 519 */             decodePackbits(im, outBuf);
/* 520 */             break;
/*     */           case 5:
/* 522 */             lzwDecoder.decode(im, outBuf, height);
/*     */           }
/*     */ 
/* 525 */           if ((bitsPerSample == 1) && (samplePerPixel == 1) && (photometric != 3)) {
/* 526 */             g4.fax4Encode(outBuf, height);
/*     */           }
/* 529 */           else if (extraSamples > 0)
/* 530 */             ProcessExtraSamples(zip, mzip, outBuf, samplePerPixel, bitsPerSample, w, height);
/*     */           else {
/* 532 */             zip.write(outBuf);
/*     */           }
/* 534 */           rowsLeft -= rowsStrip;
/*     */         }
/* 536 */         if ((bitsPerSample == 1) && (samplePerPixel == 1) && (photometric != 3)) {
/* 537 */           img = Image.getInstance(w, h, false, 256, photometric == 1 ? 1 : 0, g4.close());
/*     */         }
/*     */         else
/*     */         {
/* 541 */           zip.close();
/* 542 */           img = new ImgRaw(w, h, samplePerPixel - extraSamples, bitsPerSample, stream.toByteArray());
/* 543 */           img.setDeflated(true);
/*     */         }
/*     */       }
/* 546 */       img.setDpi(dpiX, dpiY);
/* 547 */       if ((compression != 6) && (compression != 7)) {
/* 548 */         if (dir.isTagPresent(34675)) {
/*     */           try {
/* 550 */             TIFFField fd = dir.getField(34675);
/* 551 */             ICC_Profile icc_prof = ICC_Profile.getInstance(fd.getAsBytes());
/* 552 */             if (samplePerPixel - extraSamples == icc_prof.getNumComponents())
/* 553 */               img.tagICC(icc_prof);
/*     */           }
/*     */           catch (RuntimeException e)
/*     */           {
/*     */           }
/*     */         }
/* 559 */         if (dir.isTagPresent(320)) {
/* 560 */           TIFFField fd = dir.getField(320);
/* 561 */           char[] rgb = fd.getAsChars();
/* 562 */           byte[] palette = new byte[rgb.length];
/* 563 */           int gColor = rgb.length / 3;
/* 564 */           int bColor = gColor * 2;
/* 565 */           for (int k = 0; k < gColor; k++) {
/* 566 */             palette[(k * 3)] = ((byte)(rgb[k] >>> '\b'));
/* 567 */             palette[(k * 3 + 1)] = ((byte)(rgb[(k + gColor)] >>> '\b'));
/* 568 */             palette[(k * 3 + 2)] = ((byte)(rgb[(k + bColor)] >>> '\b'));
/*     */           }
/*     */ 
/* 573 */           boolean colormapBroken = true;
/* 574 */           for (int k = 0; k < palette.length; k++) {
/* 575 */             if (palette[k] != 0) {
/* 576 */               colormapBroken = false;
/* 577 */               break;
/*     */             }
/*     */           }
/* 580 */           if (colormapBroken) {
/* 581 */             for (int k = 0; k < gColor; k++) {
/* 582 */               palette[(k * 3)] = ((byte)rgb[k]);
/* 583 */               palette[(k * 3 + 1)] = ((byte)rgb[(k + gColor)]);
/* 584 */               palette[(k * 3 + 2)] = ((byte)rgb[(k + bColor)]);
/*     */             }
/*     */           }
/* 587 */           PdfArray indexed = new PdfArray();
/* 588 */           indexed.add(PdfName.INDEXED);
/* 589 */           indexed.add(PdfName.DEVICERGB);
/* 590 */           indexed.add(new PdfNumber(gColor - 1));
/* 591 */           indexed.add(new PdfString(palette));
/* 592 */           PdfDictionary additional = new PdfDictionary();
/* 593 */           additional.put(PdfName.COLORSPACE, indexed);
/* 594 */           img.setAdditional(additional);
/*     */         }
/* 596 */         img.setOriginalType(5);
/*     */       }
/* 598 */       if (photometric == 0)
/* 599 */         img.setInverted(true);
/* 600 */       if (rotation != 0.0F)
/* 601 */         img.setInitialRotation(rotation);
/* 602 */       if (extraSamples > 0) {
/* 603 */         mzip.close();
/* 604 */         Image mimg = Image.getInstance(w, h, 1, bitsPerSample, mstream.toByteArray());
/* 605 */         mimg.makeMask();
/* 606 */         mimg.setDeflated(true);
/* 607 */         img.setImageMask(mimg);
/*     */       }
/* 609 */       return img;
/*     */     }
/*     */     catch (Exception e) {
/* 612 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   static Image ProcessExtraSamples(DeflaterOutputStream zip, DeflaterOutputStream mzip, byte[] outBuf, int samplePerPixel, int bitsPerSample, int width, int height) throws IOException {
/* 617 */     if (bitsPerSample == 8) {
/* 618 */       byte[] mask = new byte[width * height];
/* 619 */       int mptr = 0;
/* 620 */       int optr = 0;
/* 621 */       int total = width * height * samplePerPixel;
/* 622 */       for (int k = 0; k < total; k += samplePerPixel) {
/* 623 */         for (int s = 0; s < samplePerPixel - 1; s++) {
/* 624 */           outBuf[(optr++)] = outBuf[(k + s)];
/*     */         }
/* 626 */         mask[(mptr++)] = outBuf[(k + samplePerPixel - 1)];
/*     */       }
/* 628 */       zip.write(outBuf, 0, optr);
/* 629 */       mzip.write(mask, 0, mptr);
/*     */     }
/*     */     else {
/* 632 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("extra.samples.are.not.supported", new Object[0]));
/* 633 */     }return null;
/*     */   }
/*     */ 
/*     */   static long[] getArrayLongShort(TIFFDirectory dir, int tag) {
/* 637 */     TIFFField field = dir.getField(tag);
/* 638 */     if (field == null)
/* 639 */       return null;
/*     */     long[] offset;
/*     */     long[] offset;
/* 641 */     if (field.getType() == 4) {
/* 642 */       offset = field.getAsLongs();
/*     */     } else {
/* 644 */       char[] temp = field.getAsChars();
/* 645 */       offset = new long[temp.length];
/* 646 */       for (int k = 0; k < temp.length; k++)
/* 647 */         offset[k] = temp[k];
/*     */     }
/* 649 */     return offset;
/*     */   }
/*     */ 
/*     */   public static void decodePackbits(byte[] data, byte[] dst)
/*     */   {
/* 654 */     int srcCount = 0; int dstCount = 0;
/*     */     try
/*     */     {
/* 658 */       while (dstCount < dst.length) {
/* 659 */         byte b = data[(srcCount++)];
/* 660 */         if ((b >= 0) && (b <= 127))
/*     */         {
/* 662 */           for (int i = 0; i < b + 1; i++) {
/* 663 */             dst[(dstCount++)] = data[(srcCount++)];
/*     */           }
/*     */         }
/* 666 */         else if ((b <= -1) && (b >= -127))
/*     */         {
/* 668 */           byte repeat = data[(srcCount++)];
/* 669 */           for (int i = 0; i < -b + 1; i++)
/* 670 */             dst[(dstCount++)] = repeat;
/*     */         }
/*     */         else
/*     */         {
/* 674 */           srcCount++;
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void inflate(byte[] deflated, byte[] inflated) {
/* 684 */     Inflater inflater = new Inflater();
/* 685 */     inflater.setInput(deflated);
/*     */     try {
/* 687 */       inflater.inflate(inflated);
/*     */     }
/*     */     catch (DataFormatException dfe) {
/* 690 */       throw new ExceptionConverter(dfe);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void applyPredictor(byte[] uncompData, int predictor, int w, int h, int samplesPerPixel) {
/* 695 */     if (predictor != 2) {
/* 696 */       return;
/*     */     }
/* 698 */     for (int j = 0; j < h; j++) {
/* 699 */       int count = samplesPerPixel * (j * w + 1);
/* 700 */       for (int i = samplesPerPixel; i < w * samplesPerPixel; i++)
/*     */       {
/*     */         int tmp42_40 = count; uncompData[tmp42_40] = ((byte)(uncompData[tmp42_40] + uncompData[(count - samplesPerPixel)]));
/* 702 */         count++;
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.codec.TiffImage
 * JD-Core Version:    0.6.2
 */