/*     */ package com.itextpdf.text.pdf.codec;
/*     */ 
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.Image;
/*     */ import com.itextpdf.text.ImgRaw;
/*     */ import com.itextpdf.text.Utilities;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.pdf.PdfArray;
/*     */ import com.itextpdf.text.pdf.PdfDictionary;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfNumber;
/*     */ import com.itextpdf.text.pdf.PdfString;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class GifImage
/*     */ {
/*     */   protected DataInputStream in;
/*     */   protected int width;
/*     */   protected int height;
/*     */   protected boolean gctFlag;
/*     */   protected int bgIndex;
/*     */   protected int bgColor;
/*     */   protected int pixelAspect;
/*     */   protected boolean lctFlag;
/*     */   protected boolean interlace;
/*     */   protected int lctSize;
/*     */   protected int ix;
/*     */   protected int iy;
/*     */   protected int iw;
/*     */   protected int ih;
/*  79 */   protected byte[] block = new byte[256];
/*  80 */   protected int blockSize = 0;
/*     */ 
/*  83 */   protected int dispose = 0;
/*  84 */   protected boolean transparency = false;
/*  85 */   protected int delay = 0;
/*     */   protected int transIndex;
/*     */   protected static final int MaxStackSize = 4096;
/*     */   protected short[] prefix;
/*     */   protected byte[] suffix;
/*     */   protected byte[] pixelStack;
/*     */   protected byte[] pixels;
/*     */   protected byte[] m_out;
/*     */   protected int m_bpc;
/*     */   protected int m_gbpc;
/*     */   protected byte[] m_global_table;
/*     */   protected byte[] m_local_table;
/*     */   protected byte[] m_curr_table;
/*     */   protected int m_line_stride;
/*     */   protected byte[] fromData;
/*     */   protected URL fromUrl;
/* 107 */   protected ArrayList<GifFrame> frames = new ArrayList();
/*     */ 
/*     */   public GifImage(URL url)
/*     */     throws IOException
/*     */   {
/* 114 */     this.fromUrl = url;
/* 115 */     InputStream is = null;
/*     */     try {
/* 117 */       is = url.openStream();
/*     */ 
/* 119 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 120 */       int read = 0;
/* 121 */       byte[] bytes = new byte[1024];
/*     */ 
/* 123 */       while ((read = is.read(bytes)) != -1) {
/* 124 */         baos.write(bytes, 0, read);
/*     */       }
/* 126 */       is.close();
/*     */ 
/* 128 */       is = new ByteArrayInputStream(baos.toByteArray());
/* 129 */       baos.flush();
/* 130 */       baos.close();
/*     */ 
/* 132 */       process(is);
/*     */     }
/*     */     finally {
/* 135 */       if (is != null)
/* 136 */         is.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public GifImage(String file)
/*     */     throws IOException
/*     */   {
/* 146 */     this(Utilities.toURL(file));
/*     */   }
/*     */ 
/*     */   public GifImage(byte[] data)
/*     */     throws IOException
/*     */   {
/* 154 */     this.fromData = data;
/* 155 */     InputStream is = null;
/*     */     try {
/* 157 */       is = new ByteArrayInputStream(data);
/* 158 */       process(is);
/*     */     }
/*     */     finally {
/* 161 */       if (is != null)
/* 162 */         is.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public GifImage(InputStream is)
/*     */     throws IOException
/*     */   {
/* 172 */     process(is);
/*     */   }
/*     */ 
/*     */   public int getFrameCount()
/*     */   {
/* 179 */     return this.frames.size();
/*     */   }
/*     */ 
/*     */   public Image getImage(int frame)
/*     */   {
/* 187 */     GifFrame gf = (GifFrame)this.frames.get(frame - 1);
/* 188 */     return gf.image;
/*     */   }
/*     */ 
/*     */   public int[] getFramePosition(int frame)
/*     */   {
/* 197 */     GifFrame gf = (GifFrame)this.frames.get(frame - 1);
/* 198 */     return new int[] { gf.ix, gf.iy };
/*     */   }
/*     */ 
/*     */   public int[] getLogicalScreen()
/*     */   {
/* 208 */     return new int[] { this.width, this.height };
/*     */   }
/*     */ 
/*     */   void process(InputStream is) throws IOException {
/* 212 */     this.in = new DataInputStream(new BufferedInputStream(is));
/* 213 */     readHeader();
/* 214 */     readContents();
/* 215 */     if (this.frames.isEmpty())
/* 216 */       throw new IOException(MessageLocalization.getComposedMessage("the.file.does.not.contain.any.valid.image", new Object[0]));
/*     */   }
/*     */ 
/*     */   protected void readHeader()
/*     */     throws IOException
/*     */   {
/* 223 */     StringBuilder id = new StringBuilder("");
/* 224 */     for (int i = 0; i < 6; i++)
/* 225 */       id.append((char)this.in.read());
/* 226 */     if (!id.toString().startsWith("GIF8")) {
/* 227 */       throw new IOException(MessageLocalization.getComposedMessage("gif.signature.nor.found", new Object[0]));
/*     */     }
/*     */ 
/* 230 */     readLSD();
/* 231 */     if (this.gctFlag)
/* 232 */       this.m_global_table = readColorTable(this.m_gbpc);
/*     */   }
/*     */ 
/*     */   protected void readLSD()
/*     */     throws IOException
/*     */   {
/* 242 */     this.width = readShort();
/* 243 */     this.height = readShort();
/*     */ 
/* 246 */     int packed = this.in.read();
/* 247 */     this.gctFlag = ((packed & 0x80) != 0);
/* 248 */     this.m_gbpc = ((packed & 0x7) + 1);
/* 249 */     this.bgIndex = this.in.read();
/* 250 */     this.pixelAspect = this.in.read();
/*     */   }
/*     */ 
/*     */   protected int readShort()
/*     */     throws IOException
/*     */   {
/* 258 */     return this.in.read() | this.in.read() << 8;
/*     */   }
/*     */ 
/*     */   protected int readBlock()
/*     */     throws IOException
/*     */   {
/* 267 */     this.blockSize = this.in.read();
/* 268 */     if (this.blockSize <= 0) {
/* 269 */       return this.blockSize = 0;
/*     */     }
/* 271 */     this.blockSize = this.in.read(this.block, 0, this.blockSize);
/*     */ 
/* 273 */     return this.blockSize;
/*     */   }
/*     */ 
/*     */   protected byte[] readColorTable(int bpc) throws IOException {
/* 277 */     int ncolors = 1 << bpc;
/* 278 */     int nbytes = 3 * ncolors;
/* 279 */     bpc = newBpc(bpc);
/* 280 */     byte[] table = new byte[(1 << bpc) * 3];
/* 281 */     this.in.readFully(table, 0, nbytes);
/* 282 */     return table;
/*     */   }
/*     */ 
/*     */   protected static int newBpc(int bpc)
/*     */   {
/* 287 */     switch (bpc) {
/*     */     case 1:
/*     */     case 2:
/*     */     case 4:
/* 291 */       break;
/*     */     case 3:
/* 293 */       return 4;
/*     */     default:
/* 295 */       return 8;
/*     */     }
/* 297 */     return bpc;
/*     */   }
/*     */ 
/*     */   protected void readContents() throws IOException
/*     */   {
/* 302 */     boolean done = false;
/* 303 */     while (!done) {
/* 304 */       int code = this.in.read();
/* 305 */       switch (code)
/*     */       {
/*     */       case 44:
/* 308 */         readImage();
/* 309 */         break;
/*     */       case 33:
/* 312 */         code = this.in.read();
/* 313 */         switch (code)
/*     */         {
/*     */         case 249:
/* 316 */           readGraphicControlExt();
/* 317 */           break;
/*     */         case 255:
/* 320 */           readBlock();
/* 321 */           skip();
/* 322 */           break;
/*     */         default:
/* 325 */           skip();
/*     */         }
/* 327 */         break;
/*     */       default:
/* 330 */         done = true;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void readImage()
/*     */     throws IOException
/*     */   {
/* 340 */     this.ix = readShort();
/* 341 */     this.iy = readShort();
/* 342 */     this.iw = readShort();
/* 343 */     this.ih = readShort();
/*     */ 
/* 345 */     int packed = this.in.read();
/* 346 */     this.lctFlag = ((packed & 0x80) != 0);
/* 347 */     this.interlace = ((packed & 0x40) != 0);
/*     */ 
/* 350 */     this.lctSize = (2 << (packed & 0x7));
/* 351 */     this.m_bpc = newBpc(this.m_gbpc);
/* 352 */     if (this.lctFlag) {
/* 353 */       this.m_curr_table = readColorTable((packed & 0x7) + 1);
/* 354 */       this.m_bpc = newBpc((packed & 0x7) + 1);
/*     */     }
/*     */     else {
/* 357 */       this.m_curr_table = this.m_global_table;
/*     */     }
/* 359 */     if ((this.transparency) && (this.transIndex >= this.m_curr_table.length / 3))
/* 360 */       this.transparency = false;
/* 361 */     if ((this.transparency) && (this.m_bpc == 1)) {
/* 362 */       byte[] tp = new byte[12];
/* 363 */       System.arraycopy(this.m_curr_table, 0, tp, 0, 6);
/* 364 */       this.m_curr_table = tp;
/* 365 */       this.m_bpc = 2;
/*     */     }
/* 367 */     boolean skipZero = decodeImageData();
/* 368 */     if (!skipZero) {
/* 369 */       skip();
/*     */     }
/* 371 */     Image img = null;
/*     */     try {
/* 373 */       img = new ImgRaw(this.iw, this.ih, 1, this.m_bpc, this.m_out);
/* 374 */       PdfArray colorspace = new PdfArray();
/* 375 */       colorspace.add(PdfName.INDEXED);
/* 376 */       colorspace.add(PdfName.DEVICERGB);
/* 377 */       int len = this.m_curr_table.length;
/* 378 */       colorspace.add(new PdfNumber(len / 3 - 1));
/* 379 */       colorspace.add(new PdfString(this.m_curr_table));
/* 380 */       PdfDictionary ad = new PdfDictionary();
/* 381 */       ad.put(PdfName.COLORSPACE, colorspace);
/* 382 */       img.setAdditional(ad);
/* 383 */       if (this.transparency)
/* 384 */         img.setTransparency(new int[] { this.transIndex, this.transIndex });
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 388 */       throw new ExceptionConverter(e);
/*     */     }
/* 390 */     img.setOriginalType(3);
/* 391 */     img.setOriginalData(this.fromData);
/* 392 */     img.setUrl(this.fromUrl);
/* 393 */     GifFrame gf = new GifFrame();
/* 394 */     gf.image = img;
/* 395 */     gf.ix = this.ix;
/* 396 */     gf.iy = this.iy;
/* 397 */     this.frames.add(gf);
/*     */   }
/*     */ 
/*     */   protected boolean decodeImageData()
/*     */     throws IOException
/*     */   {
/* 404 */     int NullCode = -1;
/* 405 */     int npix = this.iw * this.ih;
/*     */ 
/* 408 */     boolean skipZero = false;
/*     */ 
/* 410 */     if (this.prefix == null)
/* 411 */       this.prefix = new short[4096];
/* 412 */     if (this.suffix == null)
/* 413 */       this.suffix = new byte[4096];
/* 414 */     if (this.pixelStack == null) {
/* 415 */       this.pixelStack = new byte[4097];
/*     */     }
/* 417 */     this.m_line_stride = ((this.iw * this.m_bpc + 7) / 8);
/* 418 */     this.m_out = new byte[this.m_line_stride * this.ih];
/* 419 */     int pass = 1;
/* 420 */     int inc = this.interlace ? 8 : 1;
/* 421 */     int line = 0;
/* 422 */     int xpos = 0;
/*     */ 
/* 426 */     int data_size = this.in.read();
/* 427 */     int clear = 1 << data_size;
/* 428 */     int end_of_information = clear + 1;
/* 429 */     int available = clear + 2;
/* 430 */     int old_code = NullCode;
/* 431 */     int code_size = data_size + 1;
/* 432 */     int code_mask = (1 << code_size) - 1;
/* 433 */     for (int code = 0; code < clear; code++) {
/* 434 */       this.prefix[code] = 0;
/* 435 */       this.suffix[code] = ((byte)code);
/*     */     }
/*     */     int bi;
/*     */     int top;
/*     */     int first;
/*     */     int count;
/*     */     int bits;
/* 440 */     int datum = bits = count = first = top = bi = 0;
/*     */ 
/* 442 */     for (int i = 0; i < npix; ) {
/* 443 */       if (top == 0) {
/* 444 */         if (bits < code_size)
/*     */         {
/* 446 */           if (count == 0)
/*     */           {
/* 448 */             count = readBlock();
/* 449 */             if (count <= 0) {
/* 450 */               skipZero = true;
/* 451 */               break;
/*     */             }
/* 453 */             bi = 0;
/*     */           }
/* 455 */           datum += ((this.block[bi] & 0xFF) << bits);
/* 456 */           bits += 8;
/* 457 */           bi++;
/* 458 */           count--;
/*     */         }
/*     */         else
/*     */         {
/* 464 */           code = datum & code_mask;
/* 465 */           datum >>= code_size;
/* 466 */           bits -= code_size;
/*     */ 
/* 470 */           if ((code > available) || (code == end_of_information))
/*     */             break;
/* 472 */           if (code == clear)
/*     */           {
/* 474 */             code_size = data_size + 1;
/* 475 */             code_mask = (1 << code_size) - 1;
/* 476 */             available = clear + 2;
/* 477 */             old_code = NullCode;
/*     */           }
/* 480 */           else if (old_code == NullCode) {
/* 481 */             this.pixelStack[(top++)] = this.suffix[code];
/* 482 */             old_code = code;
/* 483 */             first = code;
/*     */           }
/*     */           else {
/* 486 */             int in_code = code;
/* 487 */             if (code == available) {
/* 488 */               this.pixelStack[(top++)] = ((byte)first);
/* 489 */               code = old_code;
/*     */             }
/* 491 */             while (code > clear) {
/* 492 */               this.pixelStack[(top++)] = this.suffix[code];
/* 493 */               code = this.prefix[code];
/*     */             }
/* 495 */             first = this.suffix[code] & 0xFF;
/*     */ 
/* 499 */             if (available >= 4096)
/*     */               break;
/* 501 */             this.pixelStack[(top++)] = ((byte)first);
/* 502 */             this.prefix[available] = ((short)old_code);
/* 503 */             this.suffix[available] = ((byte)first);
/* 504 */             available++;
/* 505 */             if (((available & code_mask) == 0) && (available < 4096)) {
/* 506 */               code_size++;
/* 507 */               code_mask += available;
/*     */             }
/* 509 */             old_code = in_code;
/*     */           }
/*     */         }
/*     */       }
/*     */       else {
/* 514 */         top--;
/* 515 */         i++;
/*     */ 
/* 517 */         setPixel(xpos, line, this.pixelStack[top]);
/* 518 */         xpos++;
/* 519 */         if (xpos >= this.iw) {
/* 520 */           xpos = 0;
/* 521 */           line += inc;
/* 522 */           if (line >= this.ih)
/* 523 */             if (this.interlace) {
/*     */               do {
/* 525 */                 pass++;
/* 526 */                 switch (pass) {
/*     */                 case 2:
/* 528 */                   line = 4;
/* 529 */                   break;
/*     */                 case 3:
/* 531 */                   line = 2;
/* 532 */                   inc = 4;
/* 533 */                   break;
/*     */                 case 4:
/* 535 */                   line = 1;
/* 536 */                   inc = 2;
/* 537 */                   break;
/*     */                 default:
/* 539 */                   line = this.ih - 1;
/* 540 */                   inc = 0;
/*     */                 }
/*     */               }
/* 542 */               while (line >= this.ih);
/*     */             }
/*     */             else {
/* 545 */               line = this.ih - 1;
/* 546 */               inc = 0;
/*     */             }
/*     */         }
/*     */       }
/*     */     }
/* 551 */     return skipZero;
/*     */   }
/*     */ 
/*     */   protected void setPixel(int x, int y, int v)
/*     */   {
/* 556 */     if (this.m_bpc == 8) {
/* 557 */       int pos = x + this.iw * y;
/* 558 */       this.m_out[pos] = ((byte)v);
/*     */     }
/*     */     else {
/* 561 */       int pos = this.m_line_stride * y + x / (8 / this.m_bpc);
/* 562 */       int vout = v << 8 - this.m_bpc * (x % (8 / this.m_bpc)) - this.m_bpc;
/*     */       int tmp81_79 = pos;
/*     */       byte[] tmp81_76 = this.m_out; tmp81_76[tmp81_79] = ((byte)(tmp81_76[tmp81_79] | vout));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void resetFrame()
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void readGraphicControlExt()
/*     */     throws IOException
/*     */   {
/* 580 */     this.in.read();
/* 581 */     int packed = this.in.read();
/* 582 */     this.dispose = ((packed & 0x1C) >> 2);
/* 583 */     if (this.dispose == 0)
/* 584 */       this.dispose = 1;
/* 585 */     this.transparency = ((packed & 0x1) != 0);
/* 586 */     this.delay = (readShort() * 10);
/* 587 */     this.transIndex = this.in.read();
/* 588 */     this.in.read();
/*     */   }
/*     */ 
/*     */   protected void skip()
/*     */     throws IOException
/*     */   {
/*     */     do
/* 597 */       readBlock();
/* 598 */     while (this.blockSize > 0);
/*     */   }
/*     */ 
/*     */   static class GifFrame
/*     */   {
/*     */     Image image;
/*     */     int ix;
/*     */     int iy;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.codec.GifImage
 * JD-Core Version:    0.6.2
 */