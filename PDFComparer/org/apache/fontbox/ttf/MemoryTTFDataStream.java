/*     */ package org.apache.fontbox.ttf;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public class MemoryTTFDataStream extends TTFDataStream
/*     */ {
/*  33 */   private byte[] data = null;
/*  34 */   private int currentPosition = 0;
/*     */ 
/*     */   public MemoryTTFDataStream(InputStream is)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/*  45 */       ByteArrayOutputStream output = new ByteArrayOutputStream(is.available());
/*  46 */       byte[] buffer = new byte[1024];
/*  47 */       int amountRead = 0;
/*  48 */       while ((amountRead = is.read(buffer)) != -1)
/*     */       {
/*  50 */         output.write(buffer, 0, amountRead);
/*     */       }
/*  52 */       this.data = output.toByteArray();
/*     */     }
/*     */     finally
/*     */     {
/*  56 */       if (is != null)
/*     */       {
/*  58 */         is.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public long readLong()
/*     */     throws IOException
/*     */   {
/*  72 */     return (readSignedInt() << 32) + (readSignedInt() & 0xFFFFFFFF);
/*     */   }
/*     */ 
/*     */   public int readSignedInt()
/*     */     throws IOException
/*     */   {
/*  83 */     int ch1 = read();
/*  84 */     int ch2 = read();
/*  85 */     int ch3 = read();
/*  86 */     int ch4 = read();
/*  87 */     if ((ch1 | ch2 | ch3 | ch4) < 0)
/*     */     {
/*  89 */       throw new EOFException();
/*     */     }
/*  91 */     return (ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0);
/*     */   }
/*     */ 
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/* 101 */     if (this.currentPosition >= this.data.length)
/*     */     {
/* 103 */       return -1;
/*     */     }
/* 105 */     int retval = this.data[this.currentPosition];
/* 106 */     this.currentPosition += 1;
/* 107 */     return (retval + 256) % 256;
/*     */   }
/*     */ 
/*     */   public int readUnsignedShort()
/*     */     throws IOException
/*     */   {
/* 118 */     int ch1 = read();
/* 119 */     int ch2 = read();
/* 120 */     if ((ch1 | ch2) < 0)
/*     */     {
/* 122 */       throw new EOFException();
/*     */     }
/* 124 */     return (ch1 << 8) + (ch2 << 0);
/*     */   }
/*     */ 
/*     */   public short readSignedShort()
/*     */     throws IOException
/*     */   {
/* 135 */     int ch1 = read();
/* 136 */     int ch2 = read();
/* 137 */     if ((ch1 | ch2) < 0)
/*     */     {
/* 139 */       throw new EOFException();
/*     */     }
/* 141 */     return (short)((ch1 << 8) + (ch2 << 0));
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 151 */     this.data = null;
/*     */   }
/*     */ 
/*     */   public void seek(long pos)
/*     */     throws IOException
/*     */   {
/* 162 */     this.currentPosition = ((int)pos);
/*     */   }
/*     */ 
/*     */   public int read(byte[] b, int off, int len)
/*     */     throws IOException
/*     */   {
/* 181 */     if (this.currentPosition < this.data.length) {
/* 182 */       int amountRead = Math.min(len, this.data.length - this.currentPosition);
/* 183 */       System.arraycopy(this.data, this.currentPosition, b, off, amountRead);
/* 184 */       this.currentPosition += amountRead;
/* 185 */       return amountRead;
/*     */     }
/* 187 */     return -1;
/*     */   }
/*     */ 
/*     */   public long getCurrentPosition()
/*     */     throws IOException
/*     */   {
/* 198 */     return this.currentPosition;
/*     */   }
/*     */ 
/*     */   public InputStream getOriginalData()
/*     */     throws IOException
/*     */   {
/* 206 */     return new ByteArrayInputStream(this.data);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.ttf.MemoryTTFDataStream
 * JD-Core Version:    0.6.2
 */