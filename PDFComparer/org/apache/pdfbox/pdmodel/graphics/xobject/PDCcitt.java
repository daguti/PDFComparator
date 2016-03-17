/*     */ package org.apache.pdfbox.pdmodel.graphics.xobject;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.DataBufferByte;
/*     */ import java.awt.image.IndexColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.io.IOUtils;
/*     */ import org.apache.pdfbox.io.RandomAccess;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceGray;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDIndexed;
/*     */ 
/*     */ public class PDCcitt extends PDXObjectImage
/*     */ {
/*  55 */   private static final List<String> FAX_FILTERS = new ArrayList();
/*     */ 
/*     */   public PDCcitt(PDStream ccitt)
/*     */   {
/*  70 */     super(ccitt, "tiff");
/*     */   }
/*     */ 
/*     */   public PDCcitt(PDDocument doc, RandomAccess raf, int number)
/*     */     throws IOException, IllegalArgumentException
/*     */   {
/*  85 */     super(new PDStream(doc), "tiff");
/*     */ 
/*  87 */     COSDictionary decodeParms = new COSDictionary();
/*     */ 
/*  89 */     COSDictionary dic = getCOSStream();
/*     */ 
/*  91 */     extractFromTiff(raf, getCOSStream().createFilteredStream(), decodeParms, number);
/*     */ 
/*  93 */     dic.setItem(COSName.FILTER, COSName.CCITTFAX_DECODE);
/*  94 */     dic.setItem(COSName.SUBTYPE, COSName.IMAGE);
/*  95 */     dic.setItem(COSName.TYPE, COSName.XOBJECT);
/*  96 */     dic.setItem(COSName.DECODE_PARMS, decodeParms);
/*     */ 
/*  98 */     setBitsPerComponent(1);
/*  99 */     setColorSpace(new PDDeviceGray());
/* 100 */     setWidth(decodeParms.getInt(COSName.COLUMNS));
/* 101 */     setHeight(decodeParms.getInt(COSName.ROWS));
/*     */   }
/*     */ 
/*     */   public PDCcitt(PDDocument doc, RandomAccess raf)
/*     */     throws IOException
/*     */   {
/* 115 */     this(doc, raf, 0);
/*     */   }
/*     */ 
/*     */   public BufferedImage getRGBImage()
/*     */     throws IOException
/*     */   {
/* 125 */     COSStream stream = getCOSStream();
/* 126 */     COSBase decodeP = stream.getDictionaryObject(COSName.DECODE_PARMS);
/* 127 */     COSDictionary decodeParms = null;
/* 128 */     if ((decodeP instanceof COSDictionary))
/*     */     {
/* 130 */       decodeParms = (COSDictionary)decodeP;
/*     */     }
/* 132 */     else if ((decodeP instanceof COSArray))
/*     */     {
/* 134 */       int index = 0;
/*     */ 
/* 136 */       COSBase filters = stream.getFilters();
/* 137 */       if ((filters instanceof COSArray))
/*     */       {
/* 139 */         COSArray filterArray = (COSArray)filters;
/* 140 */         while (index < filterArray.size())
/*     */         {
/* 142 */           COSName filtername = (COSName)filterArray.get(index);
/* 143 */           if (COSName.CCITTFAX_DECODE.equals(filtername))
/*     */           {
/*     */             break;
/*     */           }
/* 147 */           index++;
/*     */         }
/*     */       }
/* 150 */       decodeParms = (COSDictionary)((COSArray)decodeP).getObject(index);
/*     */     }
/* 152 */     int cols = decodeParms.getInt(COSName.COLUMNS, 1728);
/* 153 */     int rows = decodeParms.getInt(COSName.ROWS, 0);
/* 154 */     int height = stream.getInt(COSName.HEIGHT, 0);
/* 155 */     if ((rows > 0) && (height > 0))
/*     */     {
/* 158 */       rows = Math.min(rows, height);
/*     */     }
/*     */     else
/*     */     {
/* 163 */       rows = Math.max(rows, height);
/*     */     }
/* 165 */     byte[] bufferData = null;
/* 166 */     ColorModel colorModel = null;
/* 167 */     PDColorSpace colorspace = getColorSpace();
/*     */ 
/* 170 */     if ((colorspace instanceof PDIndexed))
/*     */     {
/* 172 */       PDIndexed csIndexed = (PDIndexed)colorspace;
/* 173 */       COSBase maskArray = getMask();
/* 174 */       if ((maskArray != null) && ((maskArray instanceof COSArray)))
/*     */       {
/* 176 */         colorModel = csIndexed.createColorModel(1, ((COSArray)maskArray).getInt(0));
/*     */       }
/*     */       else
/*     */       {
/* 180 */         colorModel = csIndexed.createColorModel(1);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 185 */       byte[] map = { 0, -1 };
/* 186 */       colorModel = new IndexColorModel(1, map.length, map, map, map, 1);
/*     */     }
/* 188 */     WritableRaster raster = colorModel.createCompatibleWritableRaster(cols, rows);
/* 189 */     DataBufferByte buffer = (DataBufferByte)raster.getDataBuffer();
/* 190 */     bufferData = buffer.getData();
/* 191 */     InputStream is = stream.getUnfilteredStream();
/* 192 */     IOUtils.populateBuffer(is, bufferData);
/* 193 */     IOUtils.closeQuietly(is);
/* 194 */     BufferedImage image = new BufferedImage(colorModel, raster, false, null);
/*     */ 
/* 197 */     COSArray decode = getDecode();
/* 198 */     if ((!hasMask()) && (decode != null) && (decode.getInt(0) == 1))
/*     */     {
/* 200 */       invertBitmap(bufferData);
/*     */     }
/*     */ 
/* 205 */     if (hasMask())
/*     */     {
/* 207 */       byte[] map = { 0, -1 };
/* 208 */       IndexColorModel cm = new IndexColorModel(1, map.length, map, map, map, 1);
/* 209 */       raster = cm.createCompatibleWritableRaster(cols, rows);
/* 210 */       bufferData = ((DataBufferByte)raster.getDataBuffer()).getData();
/*     */ 
/* 212 */       byte[] array = ((DataBufferByte)image.getData().getDataBuffer()).getData();
/* 213 */       System.arraycopy(array, 0, bufferData, 0, array.length < bufferData.length ? array.length : bufferData.length);
/*     */ 
/* 215 */       BufferedImage indexed = new BufferedImage(cm, raster, false, null);
/* 216 */       image = indexed;
/*     */     }
/* 218 */     return applyMasks(image);
/*     */   }
/*     */ 
/*     */   private void invertBitmap(byte[] bufferData)
/*     */   {
/* 223 */     int i = 0; for (int c = bufferData.length; i < c; i++)
/*     */     {
/* 225 */       bufferData[i] = ((byte)((bufferData[i] ^ 0xFFFFFFFF) & 0xFF));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write2OutputStream(OutputStream out)
/*     */     throws IOException
/*     */   {
/* 237 */     InputStream data = new TiffWrapper(getPDStream().getPartiallyFilteredStream(FAX_FILTERS), getCOSStream(), null);
/* 238 */     IOUtils.copy(data, out);
/* 239 */     IOUtils.closeQuietly(data);
/*     */   }
/*     */ 
/*     */   private void extractFromTiff(RandomAccess raf, OutputStream os, COSDictionary parms, int number)
/*     */     throws IOException, IllegalArgumentException
/*     */   {
/*     */     try
/*     */     {
/* 259 */       raf.seek(0L);
/* 260 */       char endianess = (char)raf.read();
/* 261 */       if ((char)raf.read() != endianess)
/*     */       {
/* 263 */         throw new IOException("Not a valid tiff file");
/*     */       }
/*     */ 
/* 266 */       if ((endianess != 'M') && (endianess != 'I'))
/*     */       {
/* 268 */         throw new IOException("Not a valid tiff file");
/*     */       }
/* 270 */       int magicNumber = readshort(endianess, raf);
/* 271 */       if (magicNumber != 42)
/*     */       {
/* 273 */         throw new IOException("Not a valid tiff file");
/*     */       }
/*     */ 
/* 277 */       int address = readlong(endianess, raf);
/* 278 */       raf.seek(address);
/*     */ 
/* 282 */       for (int i = 0; i < number; i++)
/*     */       {
/* 284 */         int numtags = readshort(endianess, raf);
/* 285 */         if (numtags > 50)
/*     */         {
/* 287 */           throw new IOException("Not a valid tiff file");
/*     */         }
/* 289 */         raf.seek(address + 2 + numtags * 12);
/* 290 */         address = readlong(endianess, raf);
/* 291 */         if (address == 0)
/*     */         {
/* 293 */           throw new IllegalArgumentException("Image number " + number + " does not exist");
/*     */         }
/* 295 */         raf.seek(address);
/*     */       }
/*     */ 
/* 298 */       int numtags = readshort(endianess, raf);
/*     */ 
/* 301 */       if (numtags > 50)
/*     */       {
/* 303 */         throw new IOException("Not a valid tiff file");
/*     */       }
/*     */ 
/* 311 */       int k = -1000;
/* 312 */       int dataoffset = 0;
/* 313 */       int datalength = 0;
/*     */ 
/* 315 */       for (int i = 0; i < numtags; i++)
/*     */       {
/* 317 */         int tag = readshort(endianess, raf);
/* 318 */         int type = readshort(endianess, raf);
/* 319 */         int count = readlong(endianess, raf);
/* 320 */         int val = readlong(endianess, raf);
/*     */ 
/* 325 */         if (endianess == 'M')
/*     */         {
/* 327 */           switch (type)
/*     */           {
/*     */           case 1:
/* 331 */             val >>= 24;
/* 332 */             break;
/*     */           case 3:
/* 336 */             val >>= 16;
/* 337 */             break;
/*     */           case 4:
/* 341 */             break;
/*     */           case 2:
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 349 */         switch (tag)
/*     */         {
/*     */         case 256:
/* 353 */           parms.setInt(COSName.COLUMNS, val);
/* 354 */           break;
/*     */         case 257:
/* 358 */           parms.setInt(COSName.ROWS, val);
/* 359 */           break;
/*     */         case 259:
/* 363 */           if (val == 4)
/*     */           {
/* 365 */             k = -1;
/*     */           }
/* 367 */           if (val == 3)
/*     */           {
/* 369 */             k = 0; } break;
/*     */         case 262:
/* 375 */           if (val == 1)
/*     */           {
/* 377 */             parms.setBoolean(COSName.BLACK_IS_1, true); } break;
/*     */         case 273:
/* 383 */           if (count == 1)
/*     */           {
/* 385 */             dataoffset = val; } break;
/*     */         case 279:
/* 391 */           if (count == 1)
/*     */           {
/* 393 */             datalength = val; } break;
/*     */         case 292:
/* 399 */           if ((val & 0x1) != 0)
/*     */           {
/* 401 */             k = 50;
/*     */           }
/*     */ 
/* 404 */           if ((val & 0x4) != 0)
/*     */           {
/* 406 */             throw new IOException("CCITT Group 3 'uncompressed mode' is not supported");
/*     */           }
/* 408 */           if ((val & 0x2) != 0)
/*     */           {
/* 410 */             throw new IOException("CCITT Group 3 'fill bits before EOL' is not supported");
/*     */           }
/*     */ 
/*     */           break;
/*     */         case 324:
/* 416 */           if (count == 1)
/*     */           {
/* 418 */             dataoffset = val; } break;
/*     */         case 325:
/* 424 */           if (count == 1)
/*     */           {
/* 426 */             datalength = val;
/*     */           }
/*     */ 
/*     */           break;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 437 */       if (k == -1000)
/*     */       {
/* 439 */         throw new IOException("First image in tiff is not CCITT T4 or T6 compressed");
/*     */       }
/* 441 */       if (dataoffset == 0)
/*     */       {
/* 443 */         throw new IOException("First image in tiff is not a single tile/strip");
/*     */       }
/*     */ 
/* 446 */       parms.setInt(COSName.K, k);
/*     */ 
/* 448 */       raf.seek(dataoffset);
/*     */ 
/* 450 */       byte[] buf = new byte[8192];
/* 451 */       int amountRead = -1;
/* 452 */       while ((amountRead = raf.read(buf, 0, Math.min(8192, datalength))) > 0)
/*     */       {
/* 454 */         datalength -= amountRead;
/* 455 */         os.write(buf, 0, amountRead);
/*     */       }
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/* 461 */       os.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   private int readshort(char endianess, RandomAccess raf) throws IOException
/*     */   {
/* 467 */     if (endianess == 'I')
/*     */     {
/* 469 */       return raf.read() | raf.read() << 8;
/*     */     }
/* 471 */     return raf.read() << 8 | raf.read();
/*     */   }
/*     */ 
/*     */   private int readlong(char endianess, RandomAccess raf) throws IOException
/*     */   {
/* 476 */     if (endianess == 'I')
/*     */     {
/* 478 */       return raf.read() | raf.read() << 8 | raf.read() << 16 | raf.read() << 24;
/*     */     }
/* 480 */     return raf.read() << 24 | raf.read() << 16 | raf.read() << 8 | raf.read();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  59 */     FAX_FILTERS.add(COSName.CCITTFAX_DECODE.getName());
/*  60 */     FAX_FILTERS.add(COSName.CCITTFAX_DECODE_ABBREVIATION.getName());
/*     */   }
/*     */ 
/*     */   private class TiffWrapper extends InputStream
/*     */   {
/*     */     private int currentOffset;
/*     */     private byte[] tiffheader;
/*     */     private InputStream datastream;
/* 602 */     private final byte[] basicHeader = { 73, 73, 42, 0, 8, 0, 0, 0, 0, 0 };
/*     */     private int additionalOffset;
/*     */ 
/*     */     private TiffWrapper(InputStream rawstream, COSDictionary options)
/*     */     {
/* 497 */       buildHeader(options);
/* 498 */       this.currentOffset = 0;
/* 499 */       this.datastream = rawstream;
/*     */     }
/*     */ 
/*     */     public boolean markSupported()
/*     */     {
/* 508 */       return false;
/*     */     }
/*     */ 
/*     */     public void reset()
/*     */       throws IOException
/*     */     {
/* 516 */       throw new IOException("reset not supported");
/*     */     }
/*     */ 
/*     */     public int read()
/*     */       throws IOException
/*     */     {
/* 526 */       if (this.currentOffset < this.tiffheader.length)
/*     */       {
/* 528 */         return this.tiffheader[(this.currentOffset++)];
/*     */       }
/* 530 */       return this.datastream.read();
/*     */     }
/*     */ 
/*     */     public int read(byte[] data)
/*     */       throws IOException
/*     */     {
/* 541 */       if (this.currentOffset < this.tiffheader.length)
/*     */       {
/* 543 */         int length = Math.min(this.tiffheader.length - this.currentOffset, data.length);
/* 544 */         if (length > 0)
/*     */         {
/* 546 */           System.arraycopy(this.tiffheader, this.currentOffset, data, 0, length);
/*     */         }
/* 548 */         this.currentOffset += length;
/* 549 */         return length;
/*     */       }
/*     */ 
/* 553 */       return this.datastream.read(data);
/*     */     }
/*     */ 
/*     */     public int read(byte[] data, int off, int len)
/*     */       throws IOException
/*     */     {
/* 565 */       if (this.currentOffset < this.tiffheader.length)
/*     */       {
/* 567 */         int length = Math.min(this.tiffheader.length - this.currentOffset, len);
/* 568 */         if (length > 0)
/*     */         {
/* 570 */           System.arraycopy(this.tiffheader, this.currentOffset, data, off, length);
/*     */         }
/* 572 */         this.currentOffset += length;
/* 573 */         return length;
/*     */       }
/*     */ 
/* 577 */       return this.datastream.read(data, off, len);
/*     */     }
/*     */ 
/*     */     public long skip(long n)
/*     */       throws IOException
/*     */     {
/* 589 */       if (this.currentOffset < this.tiffheader.length)
/*     */       {
/* 591 */         long length = Math.min(this.tiffheader.length - this.currentOffset, n);
/* 592 */         this.currentOffset = ((int)(this.currentOffset + length));
/* 593 */         return length;
/*     */       }
/*     */ 
/* 597 */       return this.datastream.skip(n);
/*     */     }
/*     */ 
/*     */     private void buildHeader(COSDictionary options)
/*     */     {
/* 611 */       int numOfTags = 10;
/* 612 */       int maxAdditionalData = 24;
/*     */ 
/* 619 */       int ifdSize = 134;
/* 620 */       this.tiffheader = new byte[ifdSize + 24];
/* 621 */       Arrays.fill(this.tiffheader, (byte)0);
/* 622 */       System.arraycopy(this.basicHeader, 0, this.tiffheader, 0, this.basicHeader.length);
/*     */ 
/* 625 */       this.additionalOffset = ifdSize;
/*     */ 
/* 629 */       short cols = 1728;
/* 630 */       short rows = 0;
/* 631 */       short blackis1 = 0;
/* 632 */       short comptype = 3;
/* 633 */       long t4options = 0L;
/*     */ 
/* 635 */       COSArray decode = PDCcitt.this.getDecode();
/*     */ 
/* 638 */       if ((decode != null) && (decode.getInt(0) == 1))
/*     */       {
/* 640 */         blackis1 = 1;
/*     */       }
/* 642 */       COSBase dicOrArrayParms = options.getDictionaryObject(COSName.DECODE_PARMS);
/* 643 */       COSDictionary decodeParms = null;
/* 644 */       if ((dicOrArrayParms instanceof COSDictionary))
/*     */       {
/* 646 */         decodeParms = (COSDictionary)dicOrArrayParms;
/*     */       }
/*     */       else
/*     */       {
/* 650 */         COSArray parmsArray = (COSArray)dicOrArrayParms;
/* 651 */         if (parmsArray.size() == 1)
/*     */         {
/* 653 */           decodeParms = (COSDictionary)parmsArray.getObject(0);
/*     */         }
/*     */         else
/*     */         {
/* 658 */           for (int i = 0; (i < parmsArray.size()) && (decodeParms == null); i++)
/*     */           {
/* 660 */             COSDictionary dic = (COSDictionary)parmsArray.getObject(i);
/* 661 */             if ((dic != null) && ((dic.getDictionaryObject(COSName.COLUMNS) != null) || (dic.getDictionaryObject(COSName.ROWS) != null)))
/*     */             {
/* 665 */               decodeParms = dic;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 671 */       if (decodeParms != null)
/*     */       {
/* 673 */         cols = (short)decodeParms.getInt(COSName.COLUMNS, cols);
/* 674 */         rows = (short)decodeParms.getInt(COSName.ROWS, rows);
/* 675 */         if (decodeParms.getBoolean(COSName.BLACK_IS_1, false))
/*     */         {
/* 677 */           blackis1 = 1;
/*     */         }
/* 679 */         int k = decodeParms.getInt(COSName.K, 0);
/* 680 */         if (k < 0)
/*     */         {
/* 683 */           comptype = 4;
/*     */         }
/* 685 */         if (k > 0)
/*     */         {
/* 688 */           comptype = 3;
/* 689 */           t4options = 1L;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 695 */       if (rows == 0)
/*     */       {
/* 697 */         rows = (short)options.getInt(COSName.HEIGHT, rows);
/*     */       }
/*     */ 
/* 704 */       addTag(256, cols);
/* 705 */       addTag(257, rows);
/* 706 */       addTag(259, comptype);
/* 707 */       addTag(262, blackis1);
/* 708 */       addTag(273, this.tiffheader.length);
/* 709 */       addTag(279, options.getInt(COSName.LENGTH));
/* 710 */       addTag(282, 300L, 1L);
/* 711 */       addTag(283, 300L, 1L);
/* 712 */       if (comptype == 3)
/*     */       {
/* 714 */         addTag(292, t4options);
/*     */       }
/* 716 */       addTag(305, "PDFBOX");
/*     */     }
/*     */ 
/*     */     private void addTag(int tag, long value)
/*     */     {
/*     */       byte[] tmp6_1 = this.tiffheader; int count = tmp6_1[8] = (byte)(tmp6_1[8] + 1);
/* 725 */       int offset = (count - 1) * 12 + 10;
/* 726 */       this.tiffheader[offset] = ((byte)(tag & 0xFF));
/* 727 */       this.tiffheader[(offset + 1)] = ((byte)(tag >> 8 & 0xFF));
/* 728 */       this.tiffheader[(offset + 2)] = 4;
/* 729 */       this.tiffheader[(offset + 4)] = 1;
/* 730 */       this.tiffheader[(offset + 8)] = ((byte)(int)(value & 0xFF));
/* 731 */       this.tiffheader[(offset + 9)] = ((byte)(int)(value >> 8 & 0xFF));
/* 732 */       this.tiffheader[(offset + 10)] = ((byte)(int)(value >> 16 & 0xFF));
/* 733 */       this.tiffheader[(offset + 11)] = ((byte)(int)(value >> 24 & 0xFF));
/*     */     }
/*     */ 
/*     */     private void addTag(int tag, short value)
/*     */     {
/*     */       byte[] tmp6_1 = this.tiffheader; int count = tmp6_1[8] = (byte)(tmp6_1[8] + 1);
/* 740 */       int offset = (count - 1) * 12 + 10;
/* 741 */       this.tiffheader[offset] = ((byte)(tag & 0xFF));
/* 742 */       this.tiffheader[(offset + 1)] = ((byte)(tag >> 8 & 0xFF));
/* 743 */       this.tiffheader[(offset + 2)] = 3;
/* 744 */       this.tiffheader[(offset + 4)] = 1;
/* 745 */       this.tiffheader[(offset + 8)] = ((byte)(value & 0xFF));
/* 746 */       this.tiffheader[(offset + 9)] = ((byte)(value >> 8 & 0xFF));
/*     */     }
/*     */ 
/*     */     private void addTag(int tag, String value)
/*     */     {
/*     */       byte[] tmp6_1 = this.tiffheader; int count = tmp6_1[8] = (byte)(tmp6_1[8] + 1);
/* 753 */       int offset = (count - 1) * 12 + 10;
/* 754 */       this.tiffheader[offset] = ((byte)(tag & 0xFF));
/* 755 */       this.tiffheader[(offset + 1)] = ((byte)(tag >> 8 & 0xFF));
/* 756 */       this.tiffheader[(offset + 2)] = 2;
/* 757 */       int len = value.length() + 1;
/* 758 */       this.tiffheader[(offset + 4)] = ((byte)(len & 0xFF));
/* 759 */       this.tiffheader[(offset + 8)] = ((byte)(this.additionalOffset & 0xFF));
/* 760 */       this.tiffheader[(offset + 9)] = ((byte)(this.additionalOffset >> 8 & 0xFF));
/* 761 */       this.tiffheader[(offset + 10)] = ((byte)(this.additionalOffset >> 16 & 0xFF));
/* 762 */       this.tiffheader[(offset + 11)] = ((byte)(this.additionalOffset >> 24 & 0xFF));
/*     */       try
/*     */       {
/* 765 */         System.arraycopy(value.getBytes("US-ASCII"), 0, this.tiffheader, this.additionalOffset, value.length());
/*     */       }
/*     */       catch (UnsupportedEncodingException e)
/*     */       {
/* 769 */         throw new RuntimeException("Incompatible VM without US-ASCII encoding", e);
/*     */       }
/* 771 */       this.additionalOffset += len;
/*     */     }
/*     */ 
/*     */     private void addTag(int tag, long numerator, long denominator)
/*     */     {
/*     */       byte[] tmp6_1 = this.tiffheader; int count = tmp6_1[8] = (byte)(tmp6_1[8] + 1);
/* 778 */       int offset = (count - 1) * 12 + 10;
/* 779 */       this.tiffheader[offset] = ((byte)(tag & 0xFF));
/* 780 */       this.tiffheader[(offset + 1)] = ((byte)(tag >> 8 & 0xFF));
/* 781 */       this.tiffheader[(offset + 2)] = 5;
/* 782 */       this.tiffheader[(offset + 4)] = 1;
/* 783 */       this.tiffheader[(offset + 8)] = ((byte)(this.additionalOffset & 0xFF));
/* 784 */       this.tiffheader[(offset + 9)] = ((byte)(this.additionalOffset >> 8 & 0xFF));
/* 785 */       this.tiffheader[(offset + 10)] = ((byte)(this.additionalOffset >> 16 & 0xFF));
/* 786 */       this.tiffheader[(offset + 11)] = ((byte)(this.additionalOffset >> 24 & 0xFF));
/* 787 */       this.tiffheader[(this.additionalOffset++)] = ((byte)(int)(numerator & 0xFF));
/* 788 */       this.tiffheader[(this.additionalOffset++)] = ((byte)(int)(numerator >> 8 & 0xFF));
/* 789 */       this.tiffheader[(this.additionalOffset++)] = ((byte)(int)(numerator >> 16 & 0xFF));
/* 790 */       this.tiffheader[(this.additionalOffset++)] = ((byte)(int)(numerator >> 24 & 0xFF));
/* 791 */       this.tiffheader[(this.additionalOffset++)] = ((byte)(int)(denominator & 0xFF));
/* 792 */       this.tiffheader[(this.additionalOffset++)] = ((byte)(int)(denominator >> 8 & 0xFF));
/* 793 */       this.tiffheader[(this.additionalOffset++)] = ((byte)(int)(denominator >> 16 & 0xFF));
/* 794 */       this.tiffheader[(this.additionalOffset++)] = ((byte)(int)(denominator >> 24 & 0xFF));
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.xobject.PDCcitt
 * JD-Core Version:    0.6.2
 */