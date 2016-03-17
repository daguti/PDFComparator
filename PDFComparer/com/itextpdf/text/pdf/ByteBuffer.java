/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.DocWriter;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.DecimalFormatSymbols;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class ByteBuffer extends OutputStream
/*     */ {
/*     */   protected int count;
/*     */   protected byte[] buf;
/*  69 */   private static int byteCacheSize = 0;
/*     */ 
/*  71 */   private static byte[][] byteCache = new byte[byteCacheSize][];
/*     */   public static final byte ZERO = 48;
/*  73 */   private static final char[] chars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
/*  74 */   private static final byte[] bytes = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
/*     */ 
/*  79 */   public static boolean HIGH_PRECISION = false;
/*  80 */   private static final DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
/*     */ 
/*     */   public ByteBuffer()
/*     */   {
/*  84 */     this(128);
/*     */   }
/*     */ 
/*     */   public ByteBuffer(int size)
/*     */   {
/*  92 */     if (size < 1)
/*  93 */       size = 128;
/*  94 */     this.buf = new byte[size];
/*     */   }
/*     */ 
/*     */   public static void setCacheSize(int size)
/*     */   {
/* 107 */     if (size > 3276700) size = 3276700;
/* 108 */     if (size <= byteCacheSize) return;
/* 109 */     byte[][] tmpCache = new byte[size][];
/* 110 */     System.arraycopy(byteCache, 0, tmpCache, 0, byteCacheSize);
/* 111 */     byteCache = tmpCache;
/* 112 */     byteCacheSize = size;
/*     */   }
/*     */ 
/*     */   public static void fillCache(int decimals)
/*     */   {
/* 122 */     int step = 1;
/* 123 */     switch (decimals) {
/*     */     case 0:
/* 125 */       step = 100;
/* 126 */       break;
/*     */     case 1:
/* 128 */       step = 10;
/*     */     }
/*     */ 
/* 131 */     for (int i = 1; i < byteCacheSize; i += step)
/* 132 */       if (byteCache[i] == null)
/* 133 */         byteCache[i] = convertToBytes(i);
/*     */   }
/*     */ 
/*     */   private static byte[] convertToBytes(int i)
/*     */   {
/* 145 */     int size = (int)Math.floor(Math.log(i) / Math.log(10.0D));
/* 146 */     if (i % 100 != 0) {
/* 147 */       size += 2;
/*     */     }
/* 149 */     if (i % 10 != 0) {
/* 150 */       size++;
/*     */     }
/* 152 */     if (i < 100) {
/* 153 */       size++;
/* 154 */       if (i < 10) {
/* 155 */         size++;
/*     */       }
/*     */     }
/* 158 */     size--;
/* 159 */     byte[] cache = new byte[size];
/* 160 */     size--;
/* 161 */     if (i < 100) {
/* 162 */       cache[0] = 48;
/*     */     }
/* 164 */     if (i % 10 != 0) {
/* 165 */       cache[(size--)] = bytes[(i % 10)];
/*     */     }
/* 167 */     if (i % 100 != 0) {
/* 168 */       cache[(size--)] = bytes[(i / 10 % 10)];
/* 169 */       cache[(size--)] = 46;
/*     */     }
/* 171 */     size = (int)Math.floor(Math.log(i) / Math.log(10.0D)) - 1;
/* 172 */     int add = 0;
/* 173 */     while (add < size) {
/* 174 */       cache[add] = bytes[(i / (int)Math.pow(10.0D, size - add + 1) % 10)];
/* 175 */       add++;
/*     */     }
/* 177 */     return cache;
/*     */   }
/*     */ 
/*     */   public ByteBuffer append_i(int b)
/*     */   {
/* 186 */     int newcount = this.count + 1;
/* 187 */     if (newcount > this.buf.length) {
/* 188 */       byte[] newbuf = new byte[Math.max(this.buf.length << 1, newcount)];
/* 189 */       System.arraycopy(this.buf, 0, newbuf, 0, this.count);
/* 190 */       this.buf = newbuf;
/*     */     }
/* 192 */     this.buf[this.count] = ((byte)b);
/* 193 */     this.count = newcount;
/* 194 */     return this;
/*     */   }
/*     */ 
/*     */   public ByteBuffer append(byte[] b, int off, int len)
/*     */   {
/* 206 */     if ((off < 0) || (off > b.length) || (len < 0) || (off + len > b.length) || (off + len < 0) || (len == 0))
/*     */     {
/* 208 */       return this;
/* 209 */     }int newcount = this.count + len;
/* 210 */     if (newcount > this.buf.length) {
/* 211 */       byte[] newbuf = new byte[Math.max(this.buf.length << 1, newcount)];
/* 212 */       System.arraycopy(this.buf, 0, newbuf, 0, this.count);
/* 213 */       this.buf = newbuf;
/*     */     }
/* 215 */     System.arraycopy(b, off, this.buf, this.count, len);
/* 216 */     this.count = newcount;
/* 217 */     return this;
/*     */   }
/*     */ 
/*     */   public ByteBuffer append(byte[] b)
/*     */   {
/* 226 */     return append(b, 0, b.length);
/*     */   }
/*     */ 
/*     */   public ByteBuffer append(String str)
/*     */   {
/* 236 */     if (str != null)
/* 237 */       return append(DocWriter.getISOBytes(str));
/* 238 */     return this;
/*     */   }
/*     */ 
/*     */   public ByteBuffer append(char c)
/*     */   {
/* 248 */     return append_i(c);
/*     */   }
/*     */ 
/*     */   public ByteBuffer append(ByteBuffer buf)
/*     */   {
/* 257 */     return append(buf.buf, 0, buf.count);
/*     */   }
/*     */ 
/*     */   public ByteBuffer append(int i)
/*     */   {
/* 266 */     return append(i);
/*     */   }
/*     */ 
/*     */   public ByteBuffer append(long i)
/*     */   {
/* 275 */     return append(Long.toString(i));
/*     */   }
/*     */ 
/*     */   public ByteBuffer append(byte b) {
/* 279 */     return append_i(b);
/*     */   }
/*     */ 
/*     */   public ByteBuffer appendHex(byte b) {
/* 283 */     append(bytes[(b >> 4 & 0xF)]);
/* 284 */     return append(bytes[(b & 0xF)]);
/*     */   }
/*     */ 
/*     */   public ByteBuffer append(float i)
/*     */   {
/* 294 */     return append(i);
/*     */   }
/*     */ 
/*     */   public ByteBuffer append(double d)
/*     */   {
/* 304 */     append(formatDouble(d, this));
/* 305 */     return this;
/*     */   }
/*     */ 
/*     */   public static String formatDouble(double d)
/*     */   {
/* 314 */     return formatDouble(d, null);
/*     */   }
/*     */ 
/*     */   public static String formatDouble(double d, ByteBuffer buf)
/*     */   {
/* 326 */     if (HIGH_PRECISION) {
/* 327 */       DecimalFormat dn = new DecimalFormat("0.######", dfs);
/* 328 */       String sform = dn.format(d);
/* 329 */       if (buf == null) {
/* 330 */         return sform;
/*     */       }
/* 332 */       buf.append(sform);
/* 333 */       return null;
/*     */     }
/*     */ 
/* 336 */     boolean negative = false;
/* 337 */     if (Math.abs(d) < 1.5E-005D) {
/* 338 */       if (buf != null) {
/* 339 */         buf.append((byte)48);
/* 340 */         return null;
/*     */       }
/* 342 */       return "0";
/*     */     }
/*     */ 
/* 345 */     if (d < 0.0D) {
/* 346 */       negative = true;
/* 347 */       d = -d;
/*     */     }
/* 349 */     if (d < 1.0D) {
/* 350 */       d += 5.E-006D;
/* 351 */       if (d >= 1.0D) {
/* 352 */         if (negative) {
/* 353 */           if (buf != null) {
/* 354 */             buf.append((byte)45);
/* 355 */             buf.append((byte)49);
/* 356 */             return null;
/*     */           }
/* 358 */           return "-1";
/*     */         }
/*     */ 
/* 361 */         if (buf != null) {
/* 362 */           buf.append((byte)49);
/* 363 */           return null;
/*     */         }
/* 365 */         return "1";
/*     */       }
/*     */ 
/* 369 */       if (buf != null) {
/* 370 */         int v = (int)(d * 100000.0D);
/*     */ 
/* 372 */         if (negative) buf.append((byte)45);
/* 373 */         buf.append((byte)48);
/* 374 */         buf.append((byte)46);
/*     */ 
/* 376 */         buf.append((byte)(v / 10000 + 48));
/* 377 */         if (v % 10000 != 0) {
/* 378 */           buf.append((byte)(v / 1000 % 10 + 48));
/* 379 */           if (v % 1000 != 0) {
/* 380 */             buf.append((byte)(v / 100 % 10 + 48));
/* 381 */             if (v % 100 != 0) {
/* 382 */               buf.append((byte)(v / 10 % 10 + 48));
/* 383 */               if (v % 10 != 0) {
/* 384 */                 buf.append((byte)(v % 10 + 48));
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/* 389 */         return null;
/*     */       }
/* 391 */       int x = 100000;
/* 392 */       int v = (int)(d * x);
/*     */ 
/* 394 */       StringBuilder res = new StringBuilder();
/* 395 */       if (negative) res.append('-');
/* 396 */       res.append("0.");
/*     */ 
/* 398 */       while (v < x / 10) {
/* 399 */         res.append('0');
/* 400 */         x /= 10;
/*     */       }
/* 402 */       res.append(v);
/* 403 */       int cut = res.length() - 1;
/* 404 */       while (res.charAt(cut) == '0') {
/* 405 */         cut--;
/*     */       }
/* 407 */       res.setLength(cut + 1);
/* 408 */       return res.toString();
/*     */     }
/* 410 */     if (d <= 32767.0D) {
/* 411 */       d += 0.005D;
/* 412 */       int v = (int)(d * 100.0D);
/*     */ 
/* 414 */       if ((v < byteCacheSize) && (byteCache[v] != null)) {
/* 415 */         if (buf != null) {
/* 416 */           if (negative) buf.append((byte)45);
/* 417 */           buf.append(byteCache[v]);
/* 418 */           return null;
/*     */         }
/* 420 */         String tmp = PdfEncodings.convertToString(byteCache[v], null);
/* 421 */         if (negative) tmp = "-" + tmp;
/* 422 */         return tmp;
/*     */       }
/*     */ 
/* 425 */       if (buf != null) {
/* 426 */         if (v < byteCacheSize)
/*     */         {
/* 429 */           int size = 0;
/* 430 */           if (v >= 1000000)
/*     */           {
/* 432 */             size += 5;
/* 433 */           } else if (v >= 100000)
/*     */           {
/* 435 */             size += 4;
/* 436 */           } else if (v >= 10000)
/*     */           {
/* 438 */             size += 3;
/* 439 */           } else if (v >= 1000)
/*     */           {
/* 441 */             size += 2;
/* 442 */           } else if (v >= 100)
/*     */           {
/* 444 */             size++;
/*     */           }
/*     */ 
/* 448 */           if (v % 100 != 0)
/*     */           {
/* 450 */             size += 2;
/*     */           }
/* 452 */           if (v % 10 != 0) {
/* 453 */             size++;
/*     */           }
/* 455 */           byte[] cache = new byte[size];
/* 456 */           int add = 0;
/* 457 */           if (v >= 1000000) {
/* 458 */             cache[(add++)] = bytes[(v / 1000000)];
/*     */           }
/* 460 */           if (v >= 100000) {
/* 461 */             cache[(add++)] = bytes[(v / 100000 % 10)];
/*     */           }
/* 463 */           if (v >= 10000) {
/* 464 */             cache[(add++)] = bytes[(v / 10000 % 10)];
/*     */           }
/* 466 */           if (v >= 1000) {
/* 467 */             cache[(add++)] = bytes[(v / 1000 % 10)];
/*     */           }
/* 469 */           if (v >= 100) {
/* 470 */             cache[(add++)] = bytes[(v / 100 % 10)];
/*     */           }
/*     */ 
/* 473 */           if (v % 100 != 0) {
/* 474 */             cache[(add++)] = 46;
/* 475 */             cache[(add++)] = bytes[(v / 10 % 10)];
/* 476 */             if (v % 10 != 0) {
/* 477 */               cache[(add++)] = bytes[(v % 10)];
/*     */             }
/*     */           }
/* 480 */           byteCache[v] = cache;
/*     */         }
/*     */ 
/* 483 */         if (negative) buf.append((byte)45);
/* 484 */         if (v >= 1000000) {
/* 485 */           buf.append(bytes[(v / 1000000)]);
/*     */         }
/* 487 */         if (v >= 100000) {
/* 488 */           buf.append(bytes[(v / 100000 % 10)]);
/*     */         }
/* 490 */         if (v >= 10000) {
/* 491 */           buf.append(bytes[(v / 10000 % 10)]);
/*     */         }
/* 493 */         if (v >= 1000) {
/* 494 */           buf.append(bytes[(v / 1000 % 10)]);
/*     */         }
/* 496 */         if (v >= 100) {
/* 497 */           buf.append(bytes[(v / 100 % 10)]);
/*     */         }
/*     */ 
/* 500 */         if (v % 100 != 0) {
/* 501 */           buf.append((byte)46);
/* 502 */           buf.append(bytes[(v / 10 % 10)]);
/* 503 */           if (v % 10 != 0) {
/* 504 */             buf.append(bytes[(v % 10)]);
/*     */           }
/*     */         }
/* 507 */         return null;
/*     */       }
/* 509 */       StringBuilder res = new StringBuilder();
/* 510 */       if (negative) res.append('-');
/* 511 */       if (v >= 1000000) {
/* 512 */         res.append(chars[(v / 1000000)]);
/*     */       }
/* 514 */       if (v >= 100000) {
/* 515 */         res.append(chars[(v / 100000 % 10)]);
/*     */       }
/* 517 */       if (v >= 10000) {
/* 518 */         res.append(chars[(v / 10000 % 10)]);
/*     */       }
/* 520 */       if (v >= 1000) {
/* 521 */         res.append(chars[(v / 1000 % 10)]);
/*     */       }
/* 523 */       if (v >= 100) {
/* 524 */         res.append(chars[(v / 100 % 10)]);
/*     */       }
/*     */ 
/* 527 */       if (v % 100 != 0) {
/* 528 */         res.append('.');
/* 529 */         res.append(chars[(v / 10 % 10)]);
/* 530 */         if (v % 10 != 0) {
/* 531 */           res.append(chars[(v % 10)]);
/*     */         }
/*     */       }
/* 534 */       return res.toString();
/*     */     }
/*     */ 
/* 537 */     d += 0.5D;
/* 538 */     long v = ()d;
/* 539 */     if (negative) {
/* 540 */       return "-" + Long.toString(v);
/*     */     }
/* 542 */     return Long.toString(v);
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/* 550 */     this.count = 0;
/*     */   }
/*     */ 
/*     */   public byte[] toByteArray()
/*     */   {
/* 561 */     byte[] newbuf = new byte[this.count];
/* 562 */     System.arraycopy(this.buf, 0, newbuf, 0, this.count);
/* 563 */     return newbuf;
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 572 */     return this.count;
/*     */   }
/*     */ 
/*     */   public void setSize(int size) {
/* 576 */     if ((size > this.count) || (size < 0))
/* 577 */       throw new IndexOutOfBoundsException(MessageLocalization.getComposedMessage("the.new.size.must.be.positive.and.lt.eq.of.the.current.size", new Object[0]));
/* 578 */     this.count = size;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 589 */     return new String(this.buf, 0, this.count);
/*     */   }
/*     */ 
/*     */   public String toString(String enc)
/*     */     throws UnsupportedEncodingException
/*     */   {
/* 602 */     return new String(this.buf, 0, this.count, enc);
/*     */   }
/*     */ 
/*     */   public void writeTo(OutputStream out)
/*     */     throws IOException
/*     */   {
/* 614 */     out.write(this.buf, 0, this.count);
/*     */   }
/*     */ 
/*     */   public void write(int b) throws IOException {
/* 618 */     append((byte)b);
/*     */   }
/*     */ 
/*     */   public void write(byte[] b, int off, int len)
/*     */   {
/* 623 */     append(b, off, len);
/*     */   }
/*     */ 
/*     */   public byte[] getBuffer() {
/* 627 */     return this.buf;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.ByteBuffer
 * JD-Core Version:    0.6.2
 */