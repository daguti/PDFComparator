/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.Document;
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.io.IndependentRandomAccessSource;
/*     */ import com.itextpdf.text.io.RandomAccessSource;
/*     */ import com.itextpdf.text.io.RandomAccessSourceFactory;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ 
/*     */ public class RandomAccessFileOrArray
/*     */   implements DataInput
/*     */ {
/*     */   private final RandomAccessSource byteSource;
/*     */   private long byteSourcePosition;
/*     */   private byte back;
/*  89 */   private boolean isBack = false;
/*     */ 
/*     */   @Deprecated
/*     */   public RandomAccessFileOrArray(String filename)
/*     */     throws IOException
/*     */   {
/*  98 */     this(new RandomAccessSourceFactory().setForceRead(false).setUsePlainRandomAccess(Document.plainRandomAccess).createBestSource(filename));
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public RandomAccessFileOrArray(RandomAccessFileOrArray source)
/*     */   {
/* 113 */     this(new IndependentRandomAccessSource(source.byteSource));
/*     */   }
/*     */ 
/*     */   public RandomAccessFileOrArray createView()
/*     */   {
/* 122 */     return new RandomAccessFileOrArray(new IndependentRandomAccessSource(this.byteSource));
/*     */   }
/*     */ 
/*     */   public RandomAccessSource createSourceView() {
/* 126 */     return new IndependentRandomAccessSource(this.byteSource);
/*     */   }
/*     */ 
/*     */   public RandomAccessFileOrArray(RandomAccessSource byteSource)
/*     */   {
/* 135 */     this.byteSource = byteSource;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public RandomAccessFileOrArray(String filename, boolean forceRead, boolean plainRandomAccess)
/*     */     throws IOException
/*     */   {
/* 148 */     this(new RandomAccessSourceFactory().setForceRead(forceRead).setUsePlainRandomAccess(plainRandomAccess).createBestSource(filename));
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public RandomAccessFileOrArray(URL url)
/*     */     throws IOException
/*     */   {
/* 161 */     this(new RandomAccessSourceFactory().createSource(url));
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public RandomAccessFileOrArray(InputStream is)
/*     */     throws IOException
/*     */   {
/* 171 */     this(new RandomAccessSourceFactory().createSource(is));
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public RandomAccessFileOrArray(byte[] arrayIn)
/*     */   {
/* 182 */     this(new RandomAccessSourceFactory().createSource(arrayIn));
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected RandomAccessSource getByteSource()
/*     */   {
/* 189 */     return this.byteSource;
/*     */   }
/*     */ 
/*     */   public void pushBack(byte b)
/*     */   {
/* 197 */     this.back = b;
/* 198 */     this.isBack = true;
/*     */   }
/*     */ 
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/* 207 */     if (this.isBack) {
/* 208 */       this.isBack = false;
/* 209 */       return this.back & 0xFF;
/*     */     }
/*     */ 
/* 212 */     return this.byteSource.get(this.byteSourcePosition++);
/*     */   }
/*     */ 
/*     */   public int read(byte[] b, int off, int len) throws IOException {
/* 216 */     if (len == 0)
/* 217 */       return 0;
/* 218 */     int count = 0;
/* 219 */     if ((this.isBack) && (len > 0)) {
/* 220 */       this.isBack = false;
/* 221 */       b[(off++)] = this.back;
/* 222 */       len--;
/* 223 */       count++;
/*     */     }
/* 225 */     if (len > 0) {
/* 226 */       int byteSourceCount = this.byteSource.get(this.byteSourcePosition, b, off, len);
/* 227 */       if (byteSourceCount > 0) {
/* 228 */         count += byteSourceCount;
/* 229 */         this.byteSourcePosition += byteSourceCount;
/*     */       }
/*     */     }
/* 232 */     if (count == 0)
/* 233 */       return -1;
/* 234 */     return count;
/*     */   }
/*     */ 
/*     */   public int read(byte[] b) throws IOException {
/* 238 */     return read(b, 0, b.length);
/*     */   }
/*     */ 
/*     */   public void readFully(byte[] b) throws IOException {
/* 242 */     readFully(b, 0, b.length);
/*     */   }
/*     */ 
/*     */   public void readFully(byte[] b, int off, int len) throws IOException {
/* 246 */     int n = 0;
/*     */     do {
/* 248 */       int count = read(b, off + n, len - n);
/* 249 */       if (count < 0)
/* 250 */         throw new EOFException();
/* 251 */       n += count;
/* 252 */     }while (n < len);
/*     */   }
/*     */ 
/*     */   public long skip(long n) throws IOException {
/* 256 */     if (n <= 0L) {
/* 257 */       return 0L;
/*     */     }
/* 259 */     int adj = 0;
/* 260 */     if (this.isBack) {
/* 261 */       this.isBack = false;
/* 262 */       if (n == 1L) {
/* 263 */         return 1L;
/*     */       }
/*     */ 
/* 266 */       n -= 1L;
/* 267 */       adj = 1;
/*     */     }
/*     */ 
/* 274 */     long pos = getFilePointer();
/* 275 */     long len = length();
/* 276 */     long newpos = pos + n;
/* 277 */     if (newpos > len) {
/* 278 */       newpos = len;
/*     */     }
/* 280 */     seek(newpos);
/*     */ 
/* 283 */     return newpos - pos + adj;
/*     */   }
/*     */ 
/*     */   public int skipBytes(int n) throws IOException {
/* 287 */     return (int)skip(n);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void reOpen() throws IOException
/*     */   {
/* 293 */     seek(0L);
/*     */   }
/*     */ 
/*     */   public void close() throws IOException
/*     */   {
/* 298 */     this.isBack = false;
/*     */ 
/* 300 */     this.byteSource.close();
/*     */   }
/*     */ 
/*     */   public long length() throws IOException {
/* 304 */     return this.byteSource.length();
/*     */   }
/*     */ 
/*     */   public void seek(long pos) throws IOException {
/* 308 */     this.byteSourcePosition = pos;
/* 309 */     this.isBack = false;
/*     */   }
/*     */ 
/*     */   public long getFilePointer() throws IOException
/*     */   {
/* 314 */     return this.byteSourcePosition - (this.isBack ? 1 : 0);
/*     */   }
/*     */ 
/*     */   public boolean readBoolean() throws IOException {
/* 318 */     int ch = read();
/* 319 */     if (ch < 0)
/* 320 */       throw new EOFException();
/* 321 */     return ch != 0;
/*     */   }
/*     */ 
/*     */   public byte readByte() throws IOException {
/* 325 */     int ch = read();
/* 326 */     if (ch < 0)
/* 327 */       throw new EOFException();
/* 328 */     return (byte)ch;
/*     */   }
/*     */ 
/*     */   public int readUnsignedByte() throws IOException {
/* 332 */     int ch = read();
/* 333 */     if (ch < 0)
/* 334 */       throw new EOFException();
/* 335 */     return ch;
/*     */   }
/*     */ 
/*     */   public short readShort() throws IOException {
/* 339 */     int ch1 = read();
/* 340 */     int ch2 = read();
/* 341 */     if ((ch1 | ch2) < 0)
/* 342 */       throw new EOFException();
/* 343 */     return (short)((ch1 << 8) + ch2);
/*     */   }
/*     */ 
/*     */   public final short readShortLE()
/*     */     throws IOException
/*     */   {
/* 368 */     int ch1 = read();
/* 369 */     int ch2 = read();
/* 370 */     if ((ch1 | ch2) < 0)
/* 371 */       throw new EOFException();
/* 372 */     return (short)((ch2 << 8) + (ch1 << 0));
/*     */   }
/*     */ 
/*     */   public int readUnsignedShort() throws IOException {
/* 376 */     int ch1 = read();
/* 377 */     int ch2 = read();
/* 378 */     if ((ch1 | ch2) < 0)
/* 379 */       throw new EOFException();
/* 380 */     return (ch1 << 8) + ch2;
/*     */   }
/*     */ 
/*     */   public final int readUnsignedShortLE()
/*     */     throws IOException
/*     */   {
/* 405 */     int ch1 = read();
/* 406 */     int ch2 = read();
/* 407 */     if ((ch1 | ch2) < 0)
/* 408 */       throw new EOFException();
/* 409 */     return (ch2 << 8) + (ch1 << 0);
/*     */   }
/*     */ 
/*     */   public char readChar() throws IOException {
/* 413 */     int ch1 = read();
/* 414 */     int ch2 = read();
/* 415 */     if ((ch1 | ch2) < 0)
/* 416 */       throw new EOFException();
/* 417 */     return (char)((ch1 << 8) + ch2);
/*     */   }
/*     */ 
/*     */   public final char readCharLE()
/*     */     throws IOException
/*     */   {
/* 441 */     int ch1 = read();
/* 442 */     int ch2 = read();
/* 443 */     if ((ch1 | ch2) < 0)
/* 444 */       throw new EOFException();
/* 445 */     return (char)((ch2 << 8) + (ch1 << 0));
/*     */   }
/*     */ 
/*     */   public int readInt() throws IOException {
/* 449 */     int ch1 = read();
/* 450 */     int ch2 = read();
/* 451 */     int ch3 = read();
/* 452 */     int ch4 = read();
/* 453 */     if ((ch1 | ch2 | ch3 | ch4) < 0)
/* 454 */       throw new EOFException();
/* 455 */     return (ch1 << 24) + (ch2 << 16) + (ch3 << 8) + ch4;
/*     */   }
/*     */ 
/*     */   public final int readIntLE()
/*     */     throws IOException
/*     */   {
/* 480 */     int ch1 = read();
/* 481 */     int ch2 = read();
/* 482 */     int ch3 = read();
/* 483 */     int ch4 = read();
/* 484 */     if ((ch1 | ch2 | ch3 | ch4) < 0)
/* 485 */       throw new EOFException();
/* 486 */     return (ch4 << 24) + (ch3 << 16) + (ch2 << 8) + (ch1 << 0);
/*     */   }
/*     */ 
/*     */   public final long readUnsignedInt()
/*     */     throws IOException
/*     */   {
/* 510 */     long ch1 = read();
/* 511 */     long ch2 = read();
/* 512 */     long ch3 = read();
/* 513 */     long ch4 = read();
/* 514 */     if ((ch1 | ch2 | ch3 | ch4) < 0L)
/* 515 */       throw new EOFException();
/* 516 */     return (ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0);
/*     */   }
/*     */ 
/*     */   public final long readUnsignedIntLE() throws IOException {
/* 520 */     long ch1 = read();
/* 521 */     long ch2 = read();
/* 522 */     long ch3 = read();
/* 523 */     long ch4 = read();
/* 524 */     if ((ch1 | ch2 | ch3 | ch4) < 0L)
/* 525 */       throw new EOFException();
/* 526 */     return (ch4 << 24) + (ch3 << 16) + (ch2 << 8) + (ch1 << 0);
/*     */   }
/*     */ 
/*     */   public long readLong() throws IOException {
/* 530 */     return (readInt() << 32) + (readInt() & 0xFFFFFFFF);
/*     */   }
/*     */ 
/*     */   public final long readLongLE() throws IOException {
/* 534 */     int i1 = readIntLE();
/* 535 */     int i2 = readIntLE();
/* 536 */     return (i2 << 32) + (i1 & 0xFFFFFFFF);
/*     */   }
/*     */ 
/*     */   public float readFloat() throws IOException {
/* 540 */     return Float.intBitsToFloat(readInt());
/*     */   }
/*     */ 
/*     */   public final float readFloatLE() throws IOException {
/* 544 */     return Float.intBitsToFloat(readIntLE());
/*     */   }
/*     */ 
/*     */   public double readDouble() throws IOException {
/* 548 */     return Double.longBitsToDouble(readLong());
/*     */   }
/*     */ 
/*     */   public final double readDoubleLE() throws IOException {
/* 552 */     return Double.longBitsToDouble(readLongLE());
/*     */   }
/*     */ 
/*     */   public String readLine() throws IOException {
/* 556 */     StringBuilder input = new StringBuilder();
/* 557 */     int c = -1;
/* 558 */     boolean eol = false;
/*     */ 
/* 560 */     while (!eol) {
/* 561 */       switch (c = read()) {
/*     */       case -1:
/*     */       case 10:
/* 564 */         eol = true;
/* 565 */         break;
/*     */       case 13:
/* 567 */         eol = true;
/* 568 */         long cur = getFilePointer();
/* 569 */         if (read() != 10)
/* 570 */           seek(cur); break;
/*     */       default:
/* 574 */         input.append((char)c);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 579 */     if ((c == -1) && (input.length() == 0)) {
/* 580 */       return null;
/*     */     }
/* 582 */     return input.toString();
/*     */   }
/*     */ 
/*     */   public String readUTF() throws IOException {
/* 586 */     return DataInputStream.readUTF(this);
/*     */   }
/*     */ 
/*     */   public String readString(int length, String encoding)
/*     */     throws IOException
/*     */   {
/* 597 */     byte[] buf = new byte[length];
/* 598 */     readFully(buf);
/*     */     try {
/* 600 */       return new String(buf, encoding);
/*     */     }
/*     */     catch (Exception e) {
/* 603 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.RandomAccessFileOrArray
 * JD-Core Version:    0.6.2
 */