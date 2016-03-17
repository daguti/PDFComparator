/*      */ package com.itextpdf.text.pdf.codec;
/*      */ 
/*      */ import com.itextpdf.text.BadElementException;
/*      */ import com.itextpdf.text.ExceptionConverter;
/*      */ import com.itextpdf.text.Image;
/*      */ import com.itextpdf.text.ImgRaw;
/*      */ import com.itextpdf.text.Utilities;
/*      */ import com.itextpdf.text.error_messages.MessageLocalization;
/*      */ import com.itextpdf.text.pdf.PdfArray;
/*      */ import com.itextpdf.text.pdf.PdfDictionary;
/*      */ import com.itextpdf.text.pdf.PdfName;
/*      */ import com.itextpdf.text.pdf.PdfNumber;
/*      */ import com.itextpdf.text.pdf.PdfString;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.net.URL;
/*      */ import java.util.HashMap;
/*      */ 
/*      */ public class BmpImage
/*      */ {
/*      */   private InputStream inputStream;
/*      */   private long bitmapFileSize;
/*      */   private long bitmapOffset;
/*      */   private long compression;
/*      */   private long imageSize;
/*      */   private byte[] palette;
/*      */   private int imageType;
/*      */   private int numBands;
/*      */   private boolean isBottomUp;
/*      */   private int bitsPerPixel;
/*      */   private int redMask;
/*      */   private int greenMask;
/*      */   private int blueMask;
/*      */   private int alphaMask;
/*  130 */   public HashMap<String, Object> properties = new HashMap();
/*      */   private long xPelsPerMeter;
/*      */   private long yPelsPerMeter;
/*      */   private static final int VERSION_2_1_BIT = 0;
/*      */   private static final int VERSION_2_4_BIT = 1;
/*      */   private static final int VERSION_2_8_BIT = 2;
/*      */   private static final int VERSION_2_24_BIT = 3;
/*      */   private static final int VERSION_3_1_BIT = 4;
/*      */   private static final int VERSION_3_4_BIT = 5;
/*      */   private static final int VERSION_3_8_BIT = 6;
/*      */   private static final int VERSION_3_24_BIT = 7;
/*      */   private static final int VERSION_3_NT_16_BIT = 8;
/*      */   private static final int VERSION_3_NT_32_BIT = 9;
/*      */   private static final int VERSION_4_1_BIT = 10;
/*      */   private static final int VERSION_4_4_BIT = 11;
/*      */   private static final int VERSION_4_8_BIT = 12;
/*      */   private static final int VERSION_4_16_BIT = 13;
/*      */   private static final int VERSION_4_24_BIT = 14;
/*      */   private static final int VERSION_4_32_BIT = 15;
/*      */   private static final int LCS_CALIBRATED_RGB = 0;
/*      */   private static final int LCS_sRGB = 1;
/*      */   private static final int LCS_CMYK = 2;
/*      */   private static final int BI_RGB = 0;
/*      */   private static final int BI_RLE8 = 1;
/*      */   private static final int BI_RLE4 = 2;
/*      */   private static final int BI_BITFIELDS = 3;
/*      */   int width;
/*      */   int height;
/*      */ 
/*      */   BmpImage(InputStream is, boolean noHeader, int size)
/*      */     throws IOException
/*      */   {
/*  169 */     this.bitmapFileSize = size;
/*  170 */     this.bitmapOffset = 0L;
/*  171 */     process(is, noHeader);
/*      */   }
/*      */ 
/*      */   public static Image getImage(URL url)
/*      */     throws IOException
/*      */   {
/*  180 */     InputStream is = null;
/*      */     try {
/*  182 */       is = url.openStream();
/*  183 */       Image img = getImage(is);
/*  184 */       img.setUrl(url);
/*  185 */       return img;
/*      */     }
/*      */     finally {
/*  188 */       if (is != null)
/*  189 */         is.close();
/*      */     }
/*      */   }
/*      */ 
/*      */   public static Image getImage(InputStream is)
/*      */     throws IOException
/*      */   {
/*  200 */     return getImage(is, false, 0);
/*      */   }
/*      */ 
/*      */   public static Image getImage(InputStream is, boolean noHeader, int size)
/*      */     throws IOException
/*      */   {
/*  212 */     BmpImage bmp = new BmpImage(is, noHeader, size);
/*      */     try {
/*  214 */       Image img = bmp.getImage();
/*  215 */       img.setDpi((int)(bmp.xPelsPerMeter * 0.0254D + 0.5D), (int)(bmp.yPelsPerMeter * 0.0254D + 0.5D));
/*  216 */       img.setOriginalType(4);
/*  217 */       return img;
/*      */     }
/*      */     catch (BadElementException be) {
/*  220 */       throw new ExceptionConverter(be);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static Image getImage(String file)
/*      */     throws IOException
/*      */   {
/*  230 */     return getImage(Utilities.toURL(file));
/*      */   }
/*      */ 
/*      */   public static Image getImage(byte[] data)
/*      */     throws IOException
/*      */   {
/*  239 */     ByteArrayInputStream is = new ByteArrayInputStream(data);
/*  240 */     Image img = getImage(is);
/*  241 */     img.setOriginalData(data);
/*  242 */     return img;
/*      */   }
/*      */ 
/*      */   protected void process(InputStream stream, boolean noHeader) throws IOException
/*      */   {
/*  247 */     if ((noHeader) || ((stream instanceof BufferedInputStream)))
/*  248 */       this.inputStream = stream;
/*      */     else {
/*  250 */       this.inputStream = new BufferedInputStream(stream);
/*      */     }
/*  252 */     if (!noHeader)
/*      */     {
/*  254 */       if ((readUnsignedByte(this.inputStream) != 66) || (readUnsignedByte(this.inputStream) != 77))
/*      */       {
/*  256 */         throw new RuntimeException(MessageLocalization.getComposedMessage("invalid.magic.value.for.bmp.file", new Object[0]));
/*      */       }
/*      */ 
/*  260 */       this.bitmapFileSize = readDWord(this.inputStream);
/*      */ 
/*  263 */       readWord(this.inputStream);
/*  264 */       readWord(this.inputStream);
/*      */ 
/*  267 */       this.bitmapOffset = readDWord(this.inputStream);
/*      */     }
/*      */ 
/*  272 */     long size = readDWord(this.inputStream);
/*      */ 
/*  274 */     if (size == 12L) {
/*  275 */       this.width = readWord(this.inputStream);
/*  276 */       this.height = readWord(this.inputStream);
/*      */     } else {
/*  278 */       this.width = readLong(this.inputStream);
/*  279 */       this.height = readLong(this.inputStream);
/*      */     }
/*      */ 
/*  282 */     int planes = readWord(this.inputStream);
/*  283 */     this.bitsPerPixel = readWord(this.inputStream);
/*      */ 
/*  285 */     this.properties.put("color_planes", Integer.valueOf(planes));
/*  286 */     this.properties.put("bits_per_pixel", Integer.valueOf(this.bitsPerPixel));
/*      */ 
/*  290 */     this.numBands = 3;
/*  291 */     if (this.bitmapOffset == 0L)
/*  292 */       this.bitmapOffset = size;
/*  293 */     if (size == 12L)
/*      */     {
/*  295 */       this.properties.put("bmp_version", "BMP v. 2.x");
/*      */ 
/*  298 */       if (this.bitsPerPixel == 1)
/*  299 */         this.imageType = 0;
/*  300 */       else if (this.bitsPerPixel == 4)
/*  301 */         this.imageType = 1;
/*  302 */       else if (this.bitsPerPixel == 8)
/*  303 */         this.imageType = 2;
/*  304 */       else if (this.bitsPerPixel == 24) {
/*  305 */         this.imageType = 3;
/*      */       }
/*      */ 
/*  309 */       int numberOfEntries = (int)((this.bitmapOffset - 14L - size) / 3L);
/*  310 */       int sizeOfPalette = numberOfEntries * 3;
/*  311 */       if (this.bitmapOffset == size) {
/*  312 */         switch (this.imageType) {
/*      */         case 0:
/*  314 */           sizeOfPalette = 6;
/*  315 */           break;
/*      */         case 1:
/*  317 */           sizeOfPalette = 48;
/*  318 */           break;
/*      */         case 2:
/*  320 */           sizeOfPalette = 768;
/*  321 */           break;
/*      */         case 3:
/*  323 */           sizeOfPalette = 0;
/*      */         }
/*      */ 
/*  326 */         this.bitmapOffset = (size + sizeOfPalette);
/*      */       }
/*  328 */       readPalette(sizeOfPalette);
/*      */     }
/*      */     else {
/*  331 */       this.compression = readDWord(this.inputStream);
/*  332 */       this.imageSize = readDWord(this.inputStream);
/*  333 */       this.xPelsPerMeter = readLong(this.inputStream);
/*  334 */       this.yPelsPerMeter = readLong(this.inputStream);
/*  335 */       long colorsUsed = readDWord(this.inputStream);
/*  336 */       long colorsImportant = readDWord(this.inputStream);
/*      */ 
/*  338 */       switch ((int)this.compression) {
/*      */       case 0:
/*  340 */         this.properties.put("compression", "BI_RGB");
/*  341 */         break;
/*      */       case 1:
/*  344 */         this.properties.put("compression", "BI_RLE8");
/*  345 */         break;
/*      */       case 2:
/*  348 */         this.properties.put("compression", "BI_RLE4");
/*  349 */         break;
/*      */       case 3:
/*  352 */         this.properties.put("compression", "BI_BITFIELDS");
/*      */       }
/*      */ 
/*  356 */       this.properties.put("x_pixels_per_meter", Long.valueOf(this.xPelsPerMeter));
/*  357 */       this.properties.put("y_pixels_per_meter", Long.valueOf(this.yPelsPerMeter));
/*  358 */       this.properties.put("colors_used", Long.valueOf(colorsUsed));
/*  359 */       this.properties.put("colors_important", Long.valueOf(colorsImportant));
/*      */ 
/*  361 */       if ((size == 40L) || (size == 52L) || (size == 56L))
/*      */       {
/*      */         int sizeOfPalette;
/*  363 */         switch ((int)this.compression)
/*      */         {
/*      */         case 0:
/*      */         case 1:
/*      */         case 2:
/*  369 */           if (this.bitsPerPixel == 1) {
/*  370 */             this.imageType = 4;
/*  371 */           } else if (this.bitsPerPixel == 4) {
/*  372 */             this.imageType = 5;
/*  373 */           } else if (this.bitsPerPixel == 8) {
/*  374 */             this.imageType = 6;
/*  375 */           } else if (this.bitsPerPixel == 24) {
/*  376 */             this.imageType = 7;
/*  377 */           } else if (this.bitsPerPixel == 16) {
/*  378 */             this.imageType = 8;
/*  379 */             this.redMask = 31744;
/*  380 */             this.greenMask = 992;
/*  381 */             this.blueMask = 31;
/*  382 */             this.properties.put("red_mask", Integer.valueOf(this.redMask));
/*  383 */             this.properties.put("green_mask", Integer.valueOf(this.greenMask));
/*  384 */             this.properties.put("blue_mask", Integer.valueOf(this.blueMask));
/*  385 */           } else if (this.bitsPerPixel == 32) {
/*  386 */             this.imageType = 9;
/*  387 */             this.redMask = 16711680;
/*  388 */             this.greenMask = 65280;
/*  389 */             this.blueMask = 255;
/*  390 */             this.properties.put("red_mask", Integer.valueOf(this.redMask));
/*  391 */             this.properties.put("green_mask", Integer.valueOf(this.greenMask));
/*  392 */             this.properties.put("blue_mask", Integer.valueOf(this.blueMask));
/*      */           }
/*      */ 
/*  396 */           if (size >= 52L) {
/*  397 */             this.redMask = ((int)readDWord(this.inputStream));
/*  398 */             this.greenMask = ((int)readDWord(this.inputStream));
/*  399 */             this.blueMask = ((int)readDWord(this.inputStream));
/*  400 */             this.properties.put("red_mask", Integer.valueOf(this.redMask));
/*  401 */             this.properties.put("green_mask", Integer.valueOf(this.greenMask));
/*  402 */             this.properties.put("blue_mask", Integer.valueOf(this.blueMask));
/*      */           }
/*      */ 
/*  405 */           if (size == 56L) {
/*  406 */             this.alphaMask = ((int)readDWord(this.inputStream));
/*  407 */             this.properties.put("alpha_mask", Integer.valueOf(this.alphaMask));
/*      */           }
/*      */ 
/*  411 */           int numberOfEntries = (int)((this.bitmapOffset - 14L - size) / 4L);
/*  412 */           sizeOfPalette = numberOfEntries * 4;
/*  413 */           if (this.bitmapOffset == size) {
/*  414 */             switch (this.imageType) {
/*      */             case 4:
/*  416 */               sizeOfPalette = (int)(colorsUsed == 0L ? 2L : colorsUsed) * 4;
/*  417 */               break;
/*      */             case 5:
/*  419 */               sizeOfPalette = (int)(colorsUsed == 0L ? 16L : colorsUsed) * 4;
/*  420 */               break;
/*      */             case 6:
/*  422 */               sizeOfPalette = (int)(colorsUsed == 0L ? 256L : colorsUsed) * 4;
/*  423 */               break;
/*      */             default:
/*  425 */               sizeOfPalette = 0;
/*      */             }
/*      */ 
/*  428 */             this.bitmapOffset = (size + sizeOfPalette);
/*      */           }
/*  430 */           readPalette(sizeOfPalette);
/*      */ 
/*  432 */           this.properties.put("bmp_version", "BMP v. 3.x");
/*  433 */           break;
/*      */         case 3:
/*  437 */           if (this.bitsPerPixel == 16)
/*  438 */             this.imageType = 8;
/*  439 */           else if (this.bitsPerPixel == 32) {
/*  440 */             this.imageType = 9;
/*      */           }
/*      */ 
/*  444 */           this.redMask = ((int)readDWord(this.inputStream));
/*  445 */           this.greenMask = ((int)readDWord(this.inputStream));
/*  446 */           this.blueMask = ((int)readDWord(this.inputStream));
/*      */ 
/*  449 */           if (size == 56L) {
/*  450 */             this.alphaMask = ((int)readDWord(this.inputStream));
/*  451 */             this.properties.put("alpha_mask", Integer.valueOf(this.alphaMask));
/*      */           }
/*      */ 
/*  454 */           this.properties.put("red_mask", Integer.valueOf(this.redMask));
/*  455 */           this.properties.put("green_mask", Integer.valueOf(this.greenMask));
/*  456 */           this.properties.put("blue_mask", Integer.valueOf(this.blueMask));
/*      */ 
/*  458 */           if (colorsUsed != 0L)
/*      */           {
/*  460 */             sizeOfPalette = (int)colorsUsed * 4;
/*  461 */             readPalette(sizeOfPalette);
/*      */           }
/*      */ 
/*  464 */           this.properties.put("bmp_version", "BMP v. 3.x NT");
/*  465 */           break;
/*      */         default:
/*  468 */           throw new RuntimeException("Invalid compression specified in BMP file.");
/*      */         }
/*      */       }
/*  471 */       else if (size == 108L)
/*      */       {
/*  474 */         this.properties.put("bmp_version", "BMP v. 4.x");
/*      */ 
/*  477 */         this.redMask = ((int)readDWord(this.inputStream));
/*  478 */         this.greenMask = ((int)readDWord(this.inputStream));
/*  479 */         this.blueMask = ((int)readDWord(this.inputStream));
/*      */ 
/*  481 */         this.alphaMask = ((int)readDWord(this.inputStream));
/*  482 */         long csType = readDWord(this.inputStream);
/*  483 */         int redX = readLong(this.inputStream);
/*  484 */         int redY = readLong(this.inputStream);
/*  485 */         int redZ = readLong(this.inputStream);
/*  486 */         int greenX = readLong(this.inputStream);
/*  487 */         int greenY = readLong(this.inputStream);
/*  488 */         int greenZ = readLong(this.inputStream);
/*  489 */         int blueX = readLong(this.inputStream);
/*  490 */         int blueY = readLong(this.inputStream);
/*  491 */         int blueZ = readLong(this.inputStream);
/*  492 */         long gammaRed = readDWord(this.inputStream);
/*  493 */         long gammaGreen = readDWord(this.inputStream);
/*  494 */         long gammaBlue = readDWord(this.inputStream);
/*      */ 
/*  496 */         if (this.bitsPerPixel == 1) {
/*  497 */           this.imageType = 10;
/*  498 */         } else if (this.bitsPerPixel == 4) {
/*  499 */           this.imageType = 11;
/*  500 */         } else if (this.bitsPerPixel == 8) {
/*  501 */           this.imageType = 12;
/*  502 */         } else if (this.bitsPerPixel == 16) {
/*  503 */           this.imageType = 13;
/*  504 */           if ((int)this.compression == 0) {
/*  505 */             this.redMask = 31744;
/*  506 */             this.greenMask = 992;
/*  507 */             this.blueMask = 31;
/*      */           }
/*  509 */         } else if (this.bitsPerPixel == 24) {
/*  510 */           this.imageType = 14;
/*  511 */         } else if (this.bitsPerPixel == 32) {
/*  512 */           this.imageType = 15;
/*  513 */           if ((int)this.compression == 0) {
/*  514 */             this.redMask = 16711680;
/*  515 */             this.greenMask = 65280;
/*  516 */             this.blueMask = 255;
/*      */           }
/*      */         }
/*      */ 
/*  520 */         this.properties.put("red_mask", Integer.valueOf(this.redMask));
/*  521 */         this.properties.put("green_mask", Integer.valueOf(this.greenMask));
/*  522 */         this.properties.put("blue_mask", Integer.valueOf(this.blueMask));
/*  523 */         this.properties.put("alpha_mask", Integer.valueOf(this.alphaMask));
/*      */ 
/*  526 */         int numberOfEntries = (int)((this.bitmapOffset - 14L - size) / 4L);
/*  527 */         int sizeOfPalette = numberOfEntries * 4;
/*  528 */         if (this.bitmapOffset == size) {
/*  529 */           switch (this.imageType) {
/*      */           case 10:
/*  531 */             sizeOfPalette = (int)(colorsUsed == 0L ? 2L : colorsUsed) * 4;
/*  532 */             break;
/*      */           case 11:
/*  534 */             sizeOfPalette = (int)(colorsUsed == 0L ? 16L : colorsUsed) * 4;
/*  535 */             break;
/*      */           case 12:
/*  537 */             sizeOfPalette = (int)(colorsUsed == 0L ? 256L : colorsUsed) * 4;
/*  538 */             break;
/*      */           default:
/*  540 */             sizeOfPalette = 0;
/*      */           }
/*      */ 
/*  543 */           this.bitmapOffset = (size + sizeOfPalette);
/*      */         }
/*  545 */         readPalette(sizeOfPalette);
/*      */ 
/*  547 */         switch ((int)csType)
/*      */         {
/*      */         case 0:
/*  550 */           this.properties.put("color_space", "LCS_CALIBRATED_RGB");
/*  551 */           this.properties.put("redX", Integer.valueOf(redX));
/*  552 */           this.properties.put("redY", Integer.valueOf(redY));
/*  553 */           this.properties.put("redZ", Integer.valueOf(redZ));
/*  554 */           this.properties.put("greenX", Integer.valueOf(greenX));
/*  555 */           this.properties.put("greenY", Integer.valueOf(greenY));
/*  556 */           this.properties.put("greenZ", Integer.valueOf(greenZ));
/*  557 */           this.properties.put("blueX", Integer.valueOf(blueX));
/*  558 */           this.properties.put("blueY", Integer.valueOf(blueY));
/*  559 */           this.properties.put("blueZ", Integer.valueOf(blueZ));
/*  560 */           this.properties.put("gamma_red", Long.valueOf(gammaRed));
/*  561 */           this.properties.put("gamma_green", Long.valueOf(gammaGreen));
/*  562 */           this.properties.put("gamma_blue", Long.valueOf(gammaBlue));
/*      */ 
/*  565 */           throw new RuntimeException("Not implemented yet.");
/*      */         case 1:
/*  570 */           this.properties.put("color_space", "LCS_sRGB");
/*  571 */           break;
/*      */         case 2:
/*  574 */           this.properties.put("color_space", "LCS_CMYK");
/*      */ 
/*  576 */           throw new RuntimeException("Not implemented yet.");
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  581 */         this.properties.put("bmp_version", "BMP v. 5.x");
/*  582 */         throw new RuntimeException("BMP version 5 not implemented yet.");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  587 */     if (this.height > 0)
/*      */     {
/*  589 */       this.isBottomUp = true;
/*      */     }
/*      */     else {
/*  592 */       this.isBottomUp = false;
/*  593 */       this.height = Math.abs(this.height);
/*      */     }
/*      */ 
/*  596 */     if ((this.bitsPerPixel == 1) || (this.bitsPerPixel == 4) || (this.bitsPerPixel == 8))
/*      */     {
/*  598 */       this.numBands = 1;
/*      */ 
/*  604 */       if ((this.imageType == 0) || (this.imageType == 1) || (this.imageType == 2))
/*      */       {
/*  608 */         int sizep = this.palette.length / 3;
/*      */ 
/*  610 */         if (sizep > 256) {
/*  611 */           sizep = 256;
/*      */         }
/*      */ 
/*  615 */         byte[] r = new byte[sizep];
/*  616 */         byte[] g = new byte[sizep];
/*  617 */         byte[] b = new byte[sizep];
/*  618 */         for (int i = 0; i < sizep; i++) {
/*  619 */           int off = 3 * i;
/*  620 */           b[i] = this.palette[off];
/*  621 */           g[i] = this.palette[(off + 1)];
/*  622 */           r[i] = this.palette[(off + 2)];
/*      */         }
/*      */       } else {
/*  625 */         int sizep = this.palette.length / 4;
/*      */ 
/*  627 */         if (sizep > 256) {
/*  628 */           sizep = 256;
/*      */         }
/*      */ 
/*  632 */         byte[] r = new byte[sizep];
/*  633 */         byte[] g = new byte[sizep];
/*  634 */         byte[] b = new byte[sizep];
/*  635 */         for (int i = 0; i < sizep; i++) {
/*  636 */           int off = 4 * i;
/*  637 */           b[i] = this.palette[off];
/*  638 */           g[i] = this.palette[(off + 1)];
/*  639 */           r[i] = this.palette[(off + 2)];
/*      */         }
/*      */       }
/*      */     }
/*  643 */     else if (this.bitsPerPixel == 16) {
/*  644 */       this.numBands = 3;
/*  645 */     } else if (this.bitsPerPixel == 32) {
/*  646 */       this.numBands = (this.alphaMask == 0 ? 3 : 4);
/*      */     }
/*      */     else
/*      */     {
/*  651 */       this.numBands = 3;
/*      */     }
/*      */   }
/*      */ 
/*      */   private byte[] getPalette(int group) {
/*  656 */     if (this.palette == null)
/*  657 */       return null;
/*  658 */     byte[] np = new byte[this.palette.length / group * 3];
/*  659 */     int e = this.palette.length / group;
/*  660 */     for (int k = 0; k < e; k++) {
/*  661 */       int src = k * group;
/*  662 */       int dest = k * 3;
/*  663 */       np[(dest + 2)] = this.palette[(src++)];
/*  664 */       np[(dest + 1)] = this.palette[(src++)];
/*  665 */       np[dest] = this.palette[src];
/*      */     }
/*  667 */     return np;
/*      */   }
/*      */ 
/*      */   private Image getImage() throws IOException, BadElementException {
/*  671 */     byte[] bdata = null;
/*      */ 
/*  681 */     switch (this.imageType)
/*      */     {
/*      */     case 0:
/*  685 */       return read1Bit(3);
/*      */     case 1:
/*  689 */       return read4Bit(3);
/*      */     case 2:
/*  693 */       return read8Bit(3);
/*      */     case 3:
/*  697 */       bdata = new byte[this.width * this.height * 3];
/*  698 */       read24Bit(bdata);
/*  699 */       return new ImgRaw(this.width, this.height, 3, 8, bdata);
/*      */     case 4:
/*  703 */       return read1Bit(4);
/*      */     case 5:
/*  706 */       switch ((int)this.compression) {
/*      */       case 0:
/*  708 */         return read4Bit(4);
/*      */       case 2:
/*  711 */         return readRLE4();
/*      */       }
/*      */ 
/*  714 */       throw new RuntimeException("Invalid compression specified for BMP file.");
/*      */     case 6:
/*  719 */       switch ((int)this.compression) {
/*      */       case 0:
/*  721 */         return read8Bit(4);
/*      */       case 1:
/*  724 */         return readRLE8();
/*      */       }
/*      */ 
/*  727 */       throw new RuntimeException("Invalid compression specified for BMP file.");
/*      */     case 7:
/*  733 */       bdata = new byte[this.width * this.height * 3];
/*  734 */       read24Bit(bdata);
/*  735 */       return new ImgRaw(this.width, this.height, 3, 8, bdata);
/*      */     case 8:
/*  738 */       return read1632Bit(false);
/*      */     case 9:
/*  741 */       return read1632Bit(true);
/*      */     case 10:
/*  744 */       return read1Bit(4);
/*      */     case 11:
/*  747 */       switch ((int)this.compression)
/*      */       {
/*      */       case 0:
/*  750 */         return read4Bit(4);
/*      */       case 2:
/*  753 */         return readRLE4();
/*      */       }
/*      */ 
/*  756 */       throw new RuntimeException("Invalid compression specified for BMP file.");
/*      */     case 12:
/*  761 */       switch ((int)this.compression)
/*      */       {
/*      */       case 0:
/*  764 */         return read8Bit(4);
/*      */       case 1:
/*  767 */         return readRLE8();
/*      */       }
/*      */ 
/*  770 */       throw new RuntimeException("Invalid compression specified for BMP file.");
/*      */     case 13:
/*  775 */       return read1632Bit(false);
/*      */     case 14:
/*  778 */       bdata = new byte[this.width * this.height * 3];
/*  779 */       read24Bit(bdata);
/*  780 */       return new ImgRaw(this.width, this.height, 3, 8, bdata);
/*      */     case 15:
/*  783 */       return read1632Bit(true);
/*      */     }
/*  785 */     return null;
/*      */   }
/*      */ 
/*      */   private Image indexedModel(byte[] bdata, int bpc, int paletteEntries) throws BadElementException {
/*  789 */     Image img = new ImgRaw(this.width, this.height, 1, bpc, bdata);
/*  790 */     PdfArray colorspace = new PdfArray();
/*  791 */     colorspace.add(PdfName.INDEXED);
/*  792 */     colorspace.add(PdfName.DEVICERGB);
/*  793 */     byte[] np = getPalette(paletteEntries);
/*  794 */     int len = np.length;
/*  795 */     colorspace.add(new PdfNumber(len / 3 - 1));
/*  796 */     colorspace.add(new PdfString(np));
/*  797 */     PdfDictionary ad = new PdfDictionary();
/*  798 */     ad.put(PdfName.COLORSPACE, colorspace);
/*  799 */     img.setAdditional(ad);
/*  800 */     return img;
/*      */   }
/*      */ 
/*      */   private void readPalette(int sizeOfPalette) throws IOException {
/*  804 */     if (sizeOfPalette == 0) {
/*  805 */       return;
/*      */     }
/*      */ 
/*  808 */     this.palette = new byte[sizeOfPalette];
/*  809 */     int bytesRead = 0;
/*  810 */     while (bytesRead < sizeOfPalette) {
/*  811 */       int r = this.inputStream.read(this.palette, bytesRead, sizeOfPalette - bytesRead);
/*  812 */       if (r < 0) {
/*  813 */         throw new RuntimeException(MessageLocalization.getComposedMessage("incomplete.palette", new Object[0]));
/*      */       }
/*  815 */       bytesRead += r;
/*      */     }
/*  817 */     this.properties.put("palette", this.palette);
/*      */   }
/*      */ 
/*      */   private Image read1Bit(int paletteEntries) throws IOException, BadElementException
/*      */   {
/*  822 */     byte[] bdata = new byte[(this.width + 7) / 8 * this.height];
/*  823 */     int padding = 0;
/*  824 */     int bytesPerScanline = (int)Math.ceil(this.width / 8.0D);
/*      */ 
/*  826 */     int remainder = bytesPerScanline % 4;
/*  827 */     if (remainder != 0) {
/*  828 */       padding = 4 - remainder;
/*      */     }
/*      */ 
/*  831 */     int imSize = (bytesPerScanline + padding) * this.height;
/*      */ 
/*  834 */     byte[] values = new byte[imSize];
/*  835 */     int bytesRead = 0;
/*  836 */     while (bytesRead < imSize) {
/*  837 */       bytesRead += this.inputStream.read(values, bytesRead, imSize - bytesRead);
/*      */     }
/*      */ 
/*  841 */     if (this.isBottomUp)
/*      */     {
/*  846 */       for (int i = 0; i < this.height; i++) {
/*  847 */         System.arraycopy(values, imSize - (i + 1) * (bytesPerScanline + padding), bdata, i * bytesPerScanline, bytesPerScanline);
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  854 */       for (int i = 0; i < this.height; i++) {
/*  855 */         System.arraycopy(values, i * (bytesPerScanline + padding), bdata, i * bytesPerScanline, bytesPerScanline);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  862 */     return indexedModel(bdata, 1, paletteEntries);
/*      */   }
/*      */ 
/*      */   private Image read4Bit(int paletteEntries) throws IOException, BadElementException
/*      */   {
/*  867 */     byte[] bdata = new byte[(this.width + 1) / 2 * this.height];
/*      */ 
/*  870 */     int padding = 0;
/*      */ 
/*  872 */     int bytesPerScanline = (int)Math.ceil(this.width / 2.0D);
/*  873 */     int remainder = bytesPerScanline % 4;
/*  874 */     if (remainder != 0) {
/*  875 */       padding = 4 - remainder;
/*      */     }
/*      */ 
/*  878 */     int imSize = (bytesPerScanline + padding) * this.height;
/*      */ 
/*  881 */     byte[] values = new byte[imSize];
/*  882 */     int bytesRead = 0;
/*  883 */     while (bytesRead < imSize) {
/*  884 */       bytesRead += this.inputStream.read(values, bytesRead, imSize - bytesRead);
/*      */     }
/*      */ 
/*  888 */     if (this.isBottomUp)
/*      */     {
/*  892 */       for (int i = 0; i < this.height; i++) {
/*  893 */         System.arraycopy(values, imSize - (i + 1) * (bytesPerScanline + padding), bdata, i * bytesPerScanline, bytesPerScanline);
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  900 */       for (int i = 0; i < this.height; i++) {
/*  901 */         System.arraycopy(values, i * (bytesPerScanline + padding), bdata, i * bytesPerScanline, bytesPerScanline);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  908 */     return indexedModel(bdata, 4, paletteEntries);
/*      */   }
/*      */ 
/*      */   private Image read8Bit(int paletteEntries) throws IOException, BadElementException
/*      */   {
/*  913 */     byte[] bdata = new byte[this.width * this.height];
/*      */ 
/*  915 */     int padding = 0;
/*      */ 
/*  918 */     int bitsPerScanline = this.width * 8;
/*  919 */     if (bitsPerScanline % 32 != 0) {
/*  920 */       padding = (bitsPerScanline / 32 + 1) * 32 - bitsPerScanline;
/*  921 */       padding = (int)Math.ceil(padding / 8.0D);
/*      */     }
/*      */ 
/*  924 */     int imSize = (this.width + padding) * this.height;
/*      */ 
/*  927 */     byte[] values = new byte[imSize];
/*  928 */     int bytesRead = 0;
/*  929 */     while (bytesRead < imSize) {
/*  930 */       bytesRead += this.inputStream.read(values, bytesRead, imSize - bytesRead);
/*      */     }
/*      */ 
/*  933 */     if (this.isBottomUp)
/*      */     {
/*  937 */       for (int i = 0; i < this.height; i++) {
/*  938 */         System.arraycopy(values, imSize - (i + 1) * (this.width + padding), bdata, i * this.width, this.width);
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  945 */       for (int i = 0; i < this.height; i++) {
/*  946 */         System.arraycopy(values, i * (this.width + padding), bdata, i * this.width, this.width);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  953 */     return indexedModel(bdata, 8, paletteEntries);
/*      */   }
/*      */ 
/*      */   private void read24Bit(byte[] bdata)
/*      */   {
/*  959 */     int padding = 0;
/*      */ 
/*  962 */     int bitsPerScanline = this.width * 24;
/*  963 */     if (bitsPerScanline % 32 != 0) {
/*  964 */       padding = (bitsPerScanline / 32 + 1) * 32 - bitsPerScanline;
/*  965 */       padding = (int)Math.ceil(padding / 8.0D);
/*      */     }
/*      */ 
/*  969 */     int imSize = (this.width * 3 + 3) / 4 * 4 * this.height;
/*      */ 
/*  971 */     byte[] values = new byte[imSize];
/*      */     try {
/*  973 */       int bytesRead = 0;
/*  974 */       while (bytesRead < imSize) {
/*  975 */         int r = this.inputStream.read(values, bytesRead, imSize - bytesRead);
/*      */ 
/*  977 */         if (r < 0)
/*      */           break;
/*  979 */         bytesRead += r;
/*      */       }
/*      */     } catch (IOException ioe) {
/*  982 */       throw new ExceptionConverter(ioe);
/*      */     }
/*      */ 
/*  985 */     int l = 0;
/*      */ 
/*  987 */     if (this.isBottomUp) {
/*  988 */       int max = this.width * this.height * 3 - 1;
/*      */ 
/*  990 */       int count = -padding;
/*  991 */       for (int i = 0; i < this.height; i++) {
/*  992 */         l = max - (i + 1) * this.width * 3 + 1;
/*  993 */         count += padding;
/*  994 */         for (int j = 0; j < this.width; j++) {
/*  995 */           bdata[(l + 2)] = values[(count++)];
/*  996 */           bdata[(l + 1)] = values[(count++)];
/*  997 */           bdata[l] = values[(count++)];
/*  998 */           l += 3;
/*      */         }
/*      */       }
/*      */     } else {
/* 1002 */       int count = -padding;
/* 1003 */       for (int i = 0; i < this.height; i++) {
/* 1004 */         count += padding;
/* 1005 */         for (int j = 0; j < this.width; j++) {
/* 1006 */           bdata[(l + 2)] = values[(count++)];
/* 1007 */           bdata[(l + 1)] = values[(count++)];
/* 1008 */           bdata[l] = values[(count++)];
/* 1009 */           l += 3;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private int findMask(int mask) {
/* 1016 */     for (int k = 0; 
/* 1017 */       (k < 32) && 
/* 1018 */       ((mask & 0x1) != 1); k++)
/*      */     {
/* 1020 */       mask >>>= 1;
/*      */     }
/* 1022 */     return mask;
/*      */   }
/*      */ 
/*      */   private int findShift(int mask) {
/* 1026 */     for (int k = 0; 
/* 1027 */       (k < 32) && 
/* 1028 */       ((mask & 0x1) != 1); k++)
/*      */     {
/* 1030 */       mask >>>= 1;
/*      */     }
/* 1032 */     return k;
/*      */   }
/*      */ 
/*      */   private Image read1632Bit(boolean is32) throws IOException, BadElementException
/*      */   {
/* 1037 */     int red_mask = findMask(this.redMask);
/* 1038 */     int red_shift = findShift(this.redMask);
/* 1039 */     int red_factor = red_mask + 1;
/* 1040 */     int green_mask = findMask(this.greenMask);
/* 1041 */     int green_shift = findShift(this.greenMask);
/* 1042 */     int green_factor = green_mask + 1;
/* 1043 */     int blue_mask = findMask(this.blueMask);
/* 1044 */     int blue_shift = findShift(this.blueMask);
/* 1045 */     int blue_factor = blue_mask + 1;
/* 1046 */     byte[] bdata = new byte[this.width * this.height * 3];
/*      */ 
/* 1048 */     int padding = 0;
/*      */ 
/* 1050 */     if (!is32)
/*      */     {
/* 1052 */       int bitsPerScanline = this.width * 16;
/* 1053 */       if (bitsPerScanline % 32 != 0) {
/* 1054 */         padding = (bitsPerScanline / 32 + 1) * 32 - bitsPerScanline;
/* 1055 */         padding = (int)Math.ceil(padding / 8.0D);
/*      */       }
/*      */     }
/*      */ 
/* 1059 */     int imSize = (int)this.imageSize;
/* 1060 */     if (imSize == 0) {
/* 1061 */       imSize = (int)(this.bitmapFileSize - this.bitmapOffset);
/*      */     }
/*      */ 
/* 1064 */     int l = 0;
/*      */ 
/* 1066 */     if (this.isBottomUp) {
/* 1067 */       for (int i = this.height - 1; i >= 0; i--) {
/* 1068 */         l = this.width * 3 * i;
/* 1069 */         for (int j = 0; j < this.width; j++)
/*      */         {
/*      */           int v;
/*      */           int v;
/* 1070 */           if (is32)
/* 1071 */             v = (int)readDWord(this.inputStream);
/*      */           else
/* 1073 */             v = readWord(this.inputStream);
/* 1074 */           bdata[(l++)] = ((byte)((v >>> red_shift & red_mask) * 256 / red_factor));
/* 1075 */           bdata[(l++)] = ((byte)((v >>> green_shift & green_mask) * 256 / green_factor));
/* 1076 */           bdata[(l++)] = ((byte)((v >>> blue_shift & blue_mask) * 256 / blue_factor));
/*      */         }
/* 1078 */         for (int m = 0; m < padding; m++)
/* 1079 */           this.inputStream.read();
/*      */       }
/*      */     }
/*      */     else {
/* 1083 */       for (int i = 0; i < this.height; i++) {
/* 1084 */         for (int j = 0; j < this.width; j++)
/*      */         {
/*      */           int v;
/*      */           int v;
/* 1085 */           if (is32)
/* 1086 */             v = (int)readDWord(this.inputStream);
/*      */           else
/* 1088 */             v = readWord(this.inputStream);
/* 1089 */           bdata[(l++)] = ((byte)((v >>> red_shift & red_mask) * 256 / red_factor));
/* 1090 */           bdata[(l++)] = ((byte)((v >>> green_shift & green_mask) * 256 / green_factor));
/* 1091 */           bdata[(l++)] = ((byte)((v >>> blue_shift & blue_mask) * 256 / blue_factor));
/*      */         }
/* 1093 */         for (int m = 0; m < padding; m++) {
/* 1094 */           this.inputStream.read();
/*      */         }
/*      */       }
/*      */     }
/* 1098 */     return new ImgRaw(this.width, this.height, 3, 8, bdata);
/*      */   }
/*      */ 
/*      */   private Image readRLE8()
/*      */     throws IOException, BadElementException
/*      */   {
/* 1104 */     int imSize = (int)this.imageSize;
/* 1105 */     if (imSize == 0) {
/* 1106 */       imSize = (int)(this.bitmapFileSize - this.bitmapOffset);
/*      */     }
/*      */ 
/* 1110 */     byte[] values = new byte[imSize];
/* 1111 */     int bytesRead = 0;
/* 1112 */     while (bytesRead < imSize) {
/* 1113 */       bytesRead += this.inputStream.read(values, bytesRead, imSize - bytesRead);
/*      */     }
/*      */ 
/* 1118 */     byte[] val = decodeRLE(true, values);
/*      */ 
/* 1121 */     imSize = this.width * this.height;
/*      */ 
/* 1123 */     if (this.isBottomUp)
/*      */     {
/* 1128 */       byte[] temp = new byte[val.length];
/* 1129 */       int bytesPerScanline = this.width;
/* 1130 */       for (int i = 0; i < this.height; i++) {
/* 1131 */         System.arraycopy(val, imSize - (i + 1) * bytesPerScanline, temp, i * bytesPerScanline, bytesPerScanline);
/*      */       }
/*      */ 
/* 1136 */       val = temp;
/*      */     }
/* 1138 */     return indexedModel(val, 8, 4);
/*      */   }
/*      */ 
/*      */   private Image readRLE4()
/*      */     throws IOException, BadElementException
/*      */   {
/* 1144 */     int imSize = (int)this.imageSize;
/* 1145 */     if (imSize == 0) {
/* 1146 */       imSize = (int)(this.bitmapFileSize - this.bitmapOffset);
/*      */     }
/*      */ 
/* 1150 */     byte[] values = new byte[imSize];
/* 1151 */     int bytesRead = 0;
/* 1152 */     while (bytesRead < imSize) {
/* 1153 */       bytesRead += this.inputStream.read(values, bytesRead, imSize - bytesRead);
/*      */     }
/*      */ 
/* 1158 */     byte[] val = decodeRLE(false, values);
/*      */ 
/* 1161 */     if (this.isBottomUp)
/*      */     {
/* 1163 */       byte[] inverted = val;
/* 1164 */       val = new byte[this.width * this.height];
/* 1165 */       int l = 0;
/*      */ 
/* 1167 */       for (int i = this.height - 1; i >= 0; i--) {
/* 1168 */         int index = i * this.width;
/* 1169 */         int lineEnd = l + this.width;
/* 1170 */         while (l != lineEnd) {
/* 1171 */           val[(l++)] = inverted[(index++)];
/*      */         }
/*      */       }
/*      */     }
/* 1175 */     int stride = (this.width + 1) / 2;
/* 1176 */     byte[] bdata = new byte[stride * this.height];
/* 1177 */     int ptr = 0;
/* 1178 */     int sh = 0;
/* 1179 */     for (int h = 0; h < this.height; h++) {
/* 1180 */       for (int w = 0; w < this.width; w++)
/* 1181 */         if ((w & 0x1) == 0) {
/* 1182 */           bdata[(sh + w / 2)] = ((byte)(val[(ptr++)] << 4));
/*      */         }
/*      */         else
/*      */         {
/*      */           int tmp239_238 = (sh + w / 2);
/*      */           byte[] tmp239_230 = bdata; tmp239_230[tmp239_238] = ((byte)(tmp239_230[tmp239_238] | (byte)(val[(ptr++)] & 0xF)));
/*      */         }
/* 1186 */       sh += stride;
/*      */     }
/* 1188 */     return indexedModel(bdata, 4, 4); } 
/* 1192 */   private byte[] decodeRLE(boolean is8, byte[] values) { byte[] val = new byte[this.width * this.height];
/*      */     int ptr;
/*      */     int x;
/*      */     int q;
/*      */     int y;
/*      */     try { ptr = 0;
/* 1195 */       x = 0;
/* 1196 */       q = 0;
/* 1197 */       for (y = 0; (y < this.height) && (ptr < values.length); ) {
/* 1198 */         int count = values[(ptr++)] & 0xFF;
/* 1199 */         if (count != 0)
/*      */         {
/* 1201 */           int bt = values[(ptr++)] & 0xFF;
/* 1202 */           if (is8) {
/* 1203 */             for (int i = count; i != 0; i--) {
/* 1204 */               val[(q++)] = ((byte)bt);
/*      */             }
/*      */           }
/*      */           else {
/* 1208 */             for (int i = 0; i < count; i++) {
/* 1209 */               val[(q++)] = ((byte)((i & 0x1) == 1 ? bt & 0xF : bt >>> 4 & 0xF));
/*      */             }
/*      */           }
/* 1212 */           x += count;
/*      */         }
/*      */         else
/*      */         {
/* 1216 */           count = values[(ptr++)] & 0xFF;
/* 1217 */           if (count == 1)
/*      */             break;
/* 1219 */           switch (count) {
/*      */           case 0:
/* 1221 */             x = 0;
/* 1222 */             y++;
/* 1223 */             q = y * this.width;
/* 1224 */             break;
/*      */           case 2:
/* 1227 */             x += (values[(ptr++)] & 0xFF);
/* 1228 */             y += (values[(ptr++)] & 0xFF);
/* 1229 */             q = y * this.width + x;
/* 1230 */             break;
/*      */           default:
/* 1233 */             if (is8) {
/* 1234 */               for (int i = count; i != 0; i--)
/* 1235 */                 val[(q++)] = ((byte)(values[(ptr++)] & 0xFF));
/*      */             }
/*      */             else {
/* 1238 */               int bt = 0;
/* 1239 */               for (int i = 0; i < count; i++) {
/* 1240 */                 if ((i & 0x1) == 0)
/* 1241 */                   bt = values[(ptr++)] & 0xFF;
/* 1242 */                 val[(q++)] = ((byte)((i & 0x1) == 1 ? bt & 0xF : bt >>> 4 & 0xF));
/*      */               }
/*      */             }
/* 1245 */             x += count;
/*      */ 
/* 1247 */             if (is8) {
/* 1248 */               if ((count & 0x1) == 1) {
/* 1249 */                 ptr++;
/*      */               }
/*      */             }
/* 1252 */             else if (((count & 0x3) == 1) || ((count & 0x3) == 2)) {
/* 1253 */               ptr++;
/*      */             }
/*      */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (RuntimeException e)
/*      */     {
/*      */     }
/*      */ 
/* 1264 */     return val;
/*      */   }
/*      */ 
/*      */   private int readUnsignedByte(InputStream stream)
/*      */     throws IOException
/*      */   {
/* 1271 */     return stream.read() & 0xFF;
/*      */   }
/*      */ 
/*      */   private int readUnsignedShort(InputStream stream) throws IOException
/*      */   {
/* 1276 */     int b1 = readUnsignedByte(stream);
/* 1277 */     int b2 = readUnsignedByte(stream);
/* 1278 */     return (b2 << 8 | b1) & 0xFFFF;
/*      */   }
/*      */ 
/*      */   private int readShort(InputStream stream) throws IOException
/*      */   {
/* 1283 */     int b1 = readUnsignedByte(stream);
/* 1284 */     int b2 = readUnsignedByte(stream);
/* 1285 */     return b2 << 8 | b1;
/*      */   }
/*      */ 
/*      */   private int readWord(InputStream stream) throws IOException
/*      */   {
/* 1290 */     return readUnsignedShort(stream);
/*      */   }
/*      */ 
/*      */   private long readUnsignedInt(InputStream stream) throws IOException
/*      */   {
/* 1295 */     int b1 = readUnsignedByte(stream);
/* 1296 */     int b2 = readUnsignedByte(stream);
/* 1297 */     int b3 = readUnsignedByte(stream);
/* 1298 */     int b4 = readUnsignedByte(stream);
/* 1299 */     long l = b4 << 24 | b3 << 16 | b2 << 8 | b1;
/* 1300 */     return l & 0xFFFFFFFF;
/*      */   }
/*      */ 
/*      */   private int readInt(InputStream stream) throws IOException
/*      */   {
/* 1305 */     int b1 = readUnsignedByte(stream);
/* 1306 */     int b2 = readUnsignedByte(stream);
/* 1307 */     int b3 = readUnsignedByte(stream);
/* 1308 */     int b4 = readUnsignedByte(stream);
/* 1309 */     return b4 << 24 | b3 << 16 | b2 << 8 | b1;
/*      */   }
/*      */ 
/*      */   private long readDWord(InputStream stream) throws IOException
/*      */   {
/* 1314 */     return readUnsignedInt(stream);
/*      */   }
/*      */ 
/*      */   private int readLong(InputStream stream) throws IOException
/*      */   {
/* 1319 */     return readInt(stream);
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.codec.BmpImage
 * JD-Core Version:    0.6.2
 */