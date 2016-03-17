/*     */ package org.apache.pdfbox.io;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public class ByteArrayPushBackInputStream extends PushBackInputStream
/*     */ {
/*     */   private byte[] data;
/*     */   private int datapos;
/*     */   private int datalen;
/*     */   private int save;
/*  53 */   private static final InputStream DUMMY = new ByteArrayInputStream("".getBytes());
/*     */ 
/*     */   public ByteArrayPushBackInputStream(byte[] input)
/*     */     throws IOException
/*     */   {
/*  64 */     super(DUMMY, 1);
/*  65 */     this.data = input;
/*  66 */     this.datapos = 0;
/*  67 */     this.save = this.datapos;
/*  68 */     this.datalen = (input != null ? input.length : 0);
/*     */   }
/*     */ 
/*     */   public int peek()
/*     */   {
/*     */     try
/*     */     {
/*  81 */       return this.data[this.datapos] + 256 & 0xFF;
/*     */     }
/*     */     catch (ArrayIndexOutOfBoundsException ex)
/*     */     {
/*     */     }
/*     */ 
/*  88 */     return -1;
/*     */   }
/*     */ 
/*     */   public boolean isEOF()
/*     */   {
/*  99 */     return this.datapos >= this.datalen;
/*     */   }
/*     */ 
/*     */   public void mark(int readlimit)
/*     */   {
/* 113 */     this.save = this.datapos;
/*     */   }
/*     */ 
/*     */   public boolean markSupported()
/*     */   {
/* 123 */     return true;
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/* 132 */     this.datapos = this.save;
/*     */   }
/*     */ 
/*     */   public int available()
/*     */   {
/* 141 */     int av = this.datalen - this.datapos;
/* 142 */     return av > 0 ? av : 0;
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 150 */     return this.datalen;
/*     */   }
/*     */ 
/*     */   public void unread(int by)
/*     */     throws IOException
/*     */   {
/* 162 */     if (this.datapos == 0)
/*     */     {
/* 164 */       throw new IOException("ByteArrayParserInputStream.unread(int): cannot unread 1 byte at buffer position " + this.datapos);
/*     */     }
/*     */ 
/* 167 */     this.datapos -= 1;
/* 168 */     this.data[this.datapos] = ((byte)by);
/*     */   }
/*     */ 
/*     */   public void unread(byte[] buffer, int off, int len)
/*     */     throws IOException
/*     */   {
/* 185 */     if ((len <= 0) || (off >= buffer.length))
/*     */     {
/* 187 */       return;
/*     */     }
/* 189 */     if (off < 0)
/*     */     {
/* 191 */       off = 0;
/*     */     }
/* 193 */     if (len > buffer.length)
/*     */     {
/* 195 */       len = buffer.length;
/*     */     }
/* 197 */     localUnread(buffer, off, len);
/*     */   }
/*     */ 
/*     */   public void unread(byte[] buffer)
/*     */     throws IOException
/*     */   {
/* 212 */     localUnread(buffer, 0, buffer.length);
/*     */   }
/*     */ 
/*     */   private void localUnread(byte[] buffer, int off, int len)
/*     */     throws IOException
/*     */   {
/* 230 */     if (this.datapos < len)
/*     */     {
/* 232 */       throw new IOException("ByteArrayParserInputStream.unread(int): cannot unread " + len + " bytes at buffer position " + this.datapos);
/*     */     }
/*     */ 
/* 236 */     this.datapos -= len;
/* 237 */     System.arraycopy(buffer, off, this.data, this.datapos, len);
/*     */   }
/*     */ 
/*     */   public int read()
/*     */   {
/*     */     try
/*     */     {
/* 250 */       return this.data[(this.datapos++)] + 256 & 0xFF;
/*     */     }
/*     */     catch (ArrayIndexOutOfBoundsException ex)
/*     */     {
/* 257 */       this.datapos = this.datalen;
/* 258 */     }return -1;
/*     */   }
/*     */ 
/*     */   public int read(byte[] buffer)
/*     */   {
/* 271 */     return localRead(buffer, 0, buffer.length);
/*     */   }
/*     */ 
/*     */   public int read(byte[] buffer, int off, int len)
/*     */   {
/* 285 */     if ((len <= 0) || (off >= buffer.length))
/*     */     {
/* 287 */       return 0;
/*     */     }
/* 289 */     if (off < 0)
/*     */     {
/* 291 */       off = 0;
/*     */     }
/* 293 */     if (len > buffer.length)
/*     */     {
/* 295 */       len = buffer.length;
/*     */     }
/* 297 */     return localRead(buffer, off, len);
/*     */   }
/*     */ 
/*     */   public int localRead(byte[] buffer, int off, int len)
/*     */   {
/* 313 */     if (len == 0)
/*     */     {
/* 315 */       return 0;
/*     */     }
/* 317 */     if (this.datapos >= this.datalen)
/*     */     {
/* 319 */       return -1;
/*     */     }
/*     */ 
/* 323 */     int newpos = this.datapos + len;
/* 324 */     if (newpos > this.datalen)
/*     */     {
/* 326 */       newpos = this.datalen;
/* 327 */       len = newpos - this.datapos;
/*     */     }
/* 329 */     System.arraycopy(this.data, this.datapos, buffer, off, len);
/* 330 */     this.datapos = newpos;
/* 331 */     return len;
/*     */   }
/*     */ 
/*     */   public long skip(long num)
/*     */   {
/* 348 */     if (num <= 0L)
/*     */     {
/* 350 */       return 0L;
/*     */     }
/*     */ 
/* 354 */     long newpos = this.datapos + num;
/* 355 */     if (newpos >= this.datalen)
/*     */     {
/* 357 */       num = this.datalen - this.datapos;
/* 358 */       this.datapos = this.datalen;
/*     */     }
/*     */     else
/*     */     {
/* 362 */       this.datapos = ((int)newpos);
/*     */     }
/* 364 */     return num;
/*     */   }
/*     */ 
/*     */   public int seek(int newpos)
/*     */   {
/* 377 */     if (newpos < 0)
/*     */     {
/* 379 */       newpos = 0;
/*     */     }
/* 381 */     else if (newpos > this.datalen)
/*     */     {
/* 383 */       newpos = this.datalen;
/*     */     }
/* 385 */     int oldpos = this.pos;
/* 386 */     this.pos = newpos;
/* 387 */     return oldpos;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.io.ByteArrayPushBackInputStream
 * JD-Core Version:    0.6.2
 */