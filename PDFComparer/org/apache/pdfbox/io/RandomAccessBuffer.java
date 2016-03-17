/*     */ package org.apache.pdfbox.io;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class RandomAccessBuffer
/*     */   implements RandomAccess, Closeable
/*     */ {
/*     */   private static final int BUFFER_SIZE = 16384;
/*  33 */   private ArrayList<byte[]> bufferList = null;
/*     */   private byte[] currentBuffer;
/*     */   private long pointer;
/*     */   private long currentBufferPointer;
/*     */   private long size;
/*     */   private int bufferListIndex;
/*     */   private int bufferListMaxIndex;
/*     */ 
/*     */   public RandomAccessBuffer()
/*     */   {
/*  53 */     this.bufferList = new ArrayList();
/*  54 */     this.currentBuffer = new byte[16384];
/*  55 */     this.bufferList.add(this.currentBuffer);
/*  56 */     this.pointer = 0L;
/*  57 */     this.currentBufferPointer = 0L;
/*  58 */     this.size = 0L;
/*  59 */     this.bufferListIndex = 0;
/*  60 */     this.bufferListMaxIndex = 0;
/*     */   }
/*     */ 
/*     */   public RandomAccessBuffer clone()
/*     */   {
/*  65 */     RandomAccessBuffer copy = new RandomAccessBuffer();
/*     */ 
/*  67 */     copy.bufferList = new ArrayList(this.bufferList.size());
/*  68 */     for (byte[] buffer : this.bufferList) {
/*  69 */       byte[] newBuffer = new byte[buffer.length];
/*  70 */       System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
/*  71 */       copy.bufferList.add(newBuffer);
/*     */     }
/*  73 */     if (this.currentBuffer != null)
/*  74 */       copy.currentBuffer = ((byte[])copy.bufferList.get(copy.bufferList.size() - 1));
/*     */     else {
/*  76 */       copy.currentBuffer = null;
/*     */     }
/*  78 */     copy.pointer = this.pointer;
/*  79 */     copy.currentBufferPointer = this.currentBufferPointer;
/*  80 */     copy.size = this.size;
/*  81 */     copy.bufferListIndex = this.bufferListIndex;
/*  82 */     copy.bufferListMaxIndex = this.bufferListMaxIndex;
/*     */ 
/*  84 */     return copy;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/*  92 */     this.currentBuffer = null;
/*  93 */     this.bufferList.clear();
/*  94 */     this.pointer = 0L;
/*  95 */     this.currentBufferPointer = 0L;
/*  96 */     this.size = 0L;
/*  97 */     this.bufferListIndex = 0;
/*     */   }
/*     */ 
/*     */   public void seek(long position)
/*     */     throws IOException
/*     */   {
/* 105 */     checkClosed();
/* 106 */     this.pointer = position;
/*     */ 
/* 108 */     this.bufferListIndex = ((int)(position / 16384L));
/* 109 */     this.currentBufferPointer = (position % 16384L);
/* 110 */     this.currentBuffer = ((byte[])this.bufferList.get(this.bufferListIndex));
/*     */   }
/*     */ 
/*     */   public long getPosition()
/*     */     throws IOException
/*     */   {
/* 117 */     checkClosed();
/* 118 */     return this.pointer;
/*     */   }
/*     */ 
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/* 126 */     checkClosed();
/* 127 */     if (this.pointer >= this.size)
/*     */     {
/* 129 */       return -1;
/*     */     }
/* 131 */     if (this.currentBufferPointer >= 16384L)
/*     */     {
/* 133 */       if (this.bufferListIndex >= this.bufferListMaxIndex)
/*     */       {
/* 135 */         return -1;
/*     */       }
/*     */ 
/* 139 */       this.currentBuffer = ((byte[])this.bufferList.get(++this.bufferListIndex));
/* 140 */       this.currentBufferPointer = 0L;
/*     */     }
/*     */ 
/* 143 */     this.pointer += 1L;
/* 144 */     return this.currentBuffer[((int)this.currentBufferPointer++)] & 0xFF;
/*     */   }
/*     */ 
/*     */   public int read(byte[] b, int offset, int length)
/*     */     throws IOException
/*     */   {
/* 152 */     checkClosed();
/* 153 */     if (this.pointer >= this.size)
/*     */     {
/* 155 */       return 0;
/*     */     }
/* 157 */     int maxLength = (int)Math.min(length, this.size - this.pointer);
/* 158 */     long remainingBytes = 16384L - this.currentBufferPointer;
/* 159 */     if (maxLength >= remainingBytes)
/*     */     {
/* 162 */       System.arraycopy(this.currentBuffer, (int)this.currentBufferPointer, b, offset, (int)remainingBytes);
/* 163 */       int newOffset = offset + (int)remainingBytes;
/* 164 */       long remainingBytes2Read = length - remainingBytes;
/*     */ 
/* 166 */       int numberOfArrays = (int)remainingBytes2Read / 16384;
/* 167 */       for (int i = 0; i < numberOfArrays; i++)
/*     */       {
/* 169 */         nextBuffer();
/* 170 */         System.arraycopy(this.currentBuffer, 0, b, newOffset, 16384);
/* 171 */         newOffset += 16384;
/*     */       }
/* 173 */       remainingBytes2Read %= 16384L;
/*     */ 
/* 175 */       if (remainingBytes2Read > 0L)
/*     */       {
/* 177 */         nextBuffer();
/* 178 */         System.arraycopy(this.currentBuffer, 0, b, newOffset, (int)remainingBytes2Read);
/* 179 */         this.currentBufferPointer += remainingBytes2Read;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 184 */       System.arraycopy(this.currentBuffer, (int)this.currentBufferPointer, b, offset, maxLength);
/* 185 */       this.currentBufferPointer += maxLength;
/*     */     }
/* 187 */     this.pointer += maxLength;
/* 188 */     return maxLength;
/*     */   }
/*     */ 
/*     */   public long length()
/*     */     throws IOException
/*     */   {
/* 196 */     checkClosed();
/* 197 */     return this.size;
/*     */   }
/*     */ 
/*     */   public void write(int b)
/*     */     throws IOException
/*     */   {
/* 205 */     checkClosed();
/*     */ 
/* 207 */     if (this.currentBufferPointer >= 16384L)
/*     */     {
/* 209 */       if (this.pointer + 16384L >= 2147483647L)
/*     */       {
/* 211 */         throw new IOException("RandomAccessBuffer overflow");
/*     */       }
/* 213 */       expandBuffer();
/*     */     }
/* 215 */     this.currentBuffer[((int)this.currentBufferPointer++)] = ((byte)b);
/* 216 */     this.pointer += 1L;
/* 217 */     if (this.pointer > this.size)
/*     */     {
/* 219 */       this.size = this.pointer;
/*     */     }
/*     */ 
/* 222 */     if (this.currentBufferPointer >= 16384L)
/*     */     {
/* 224 */       if (this.pointer + 16384L >= 2147483647L)
/*     */       {
/* 226 */         throw new IOException("RandomAccessBuffer overflow");
/*     */       }
/* 228 */       expandBuffer();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(byte[] b, int offset, int length)
/*     */     throws IOException
/*     */   {
/* 237 */     checkClosed();
/* 238 */     long newSize = this.pointer + length;
/* 239 */     long remainingBytes = 16384L - this.currentBufferPointer;
/* 240 */     if (length >= remainingBytes)
/*     */     {
/* 242 */       if (newSize > 2147483647L)
/*     */       {
/* 244 */         throw new IOException("RandomAccessBuffer overflow");
/*     */       }
/*     */ 
/* 247 */       System.arraycopy(b, offset, this.currentBuffer, (int)this.currentBufferPointer, (int)remainingBytes);
/* 248 */       int newOffset = offset + (int)remainingBytes;
/* 249 */       long remainingBytes2Write = length - remainingBytes;
/*     */ 
/* 251 */       int numberOfNewArrays = (int)remainingBytes2Write / 16384;
/* 252 */       for (int i = 0; i < numberOfNewArrays; i++)
/*     */       {
/* 254 */         expandBuffer();
/* 255 */         System.arraycopy(b, newOffset, this.currentBuffer, (int)this.currentBufferPointer, 16384);
/* 256 */         newOffset += 16384;
/*     */       }
/*     */ 
/* 259 */       remainingBytes2Write -= numberOfNewArrays * 16384;
/* 260 */       if (remainingBytes2Write >= 0L)
/*     */       {
/* 262 */         expandBuffer();
/* 263 */         if (remainingBytes2Write > 0L)
/*     */         {
/* 265 */           System.arraycopy(b, newOffset, this.currentBuffer, (int)this.currentBufferPointer, (int)remainingBytes2Write);
/*     */         }
/* 267 */         this.currentBufferPointer = remainingBytes2Write;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 272 */       System.arraycopy(b, offset, this.currentBuffer, (int)this.currentBufferPointer, length);
/* 273 */       this.currentBufferPointer += length;
/*     */     }
/* 275 */     this.pointer += length;
/* 276 */     if (this.pointer > this.size)
/*     */     {
/* 278 */       this.size = this.pointer;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void expandBuffer()
/*     */   {
/* 287 */     if (this.bufferListMaxIndex > this.bufferListIndex)
/*     */     {
/* 290 */       nextBuffer();
/*     */     }
/*     */     else
/*     */     {
/* 295 */       this.currentBuffer = new byte[16384];
/* 296 */       this.bufferList.add(this.currentBuffer);
/* 297 */       this.currentBufferPointer = 0L;
/* 298 */       this.bufferListMaxIndex += 1;
/* 299 */       this.bufferListIndex += 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void nextBuffer()
/*     */   {
/* 308 */     this.currentBufferPointer = 0L;
/* 309 */     this.currentBuffer = ((byte[])this.bufferList.get(++this.bufferListIndex));
/*     */   }
/*     */ 
/*     */   private void checkClosed()
/*     */     throws IOException
/*     */   {
/* 317 */     if (this.currentBuffer == null)
/*     */     {
/* 319 */       throw new IOException("RandomAccessBuffer already closed");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.io.RandomAccessBuffer
 * JD-Core Version:    0.6.2
 */