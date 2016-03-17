/*     */ package org.apache.fontbox.ttf;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Calendar;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ public abstract class TTFDataStream
/*     */   implements Closeable
/*     */ {
/*     */   public float read32Fixed()
/*     */     throws IOException
/*     */   {
/*  44 */     float retval = 0.0F;
/*  45 */     retval = readSignedShort();
/*  46 */     retval = (float)(retval + readUnsignedShort() / 65536.0D);
/*  47 */     return retval;
/*     */   }
/*     */ 
/*     */   public String readString(int length)
/*     */     throws IOException
/*     */   {
/*  59 */     return readString(length, "ISO-8859-1");
/*     */   }
/*     */ 
/*     */   public String readString(int length, String charset)
/*     */     throws IOException
/*     */   {
/*  72 */     byte[] buffer = read(length);
/*  73 */     return new String(buffer, charset);
/*     */   }
/*     */ 
/*     */   public abstract int read()
/*     */     throws IOException;
/*     */ 
/*     */   public abstract long readLong()
/*     */     throws IOException;
/*     */ 
/*     */   public int readSignedByte()
/*     */     throws IOException
/*     */   {
/* 100 */     int signedByte = read();
/* 101 */     return signedByte < 127 ? signedByte : signedByte - 256;
/*     */   }
/*     */ 
/*     */   public int readUnsignedByte()
/*     */     throws IOException
/*     */   {
/* 112 */     int unsignedByte = read();
/* 113 */     if (unsignedByte == -1)
/*     */     {
/* 115 */       throw new EOFException("premature EOF");
/*     */     }
/* 117 */     return unsignedByte;
/*     */   }
/*     */ 
/*     */   public long readUnsignedInt()
/*     */     throws IOException
/*     */   {
/* 128 */     long byte1 = read();
/* 129 */     long byte2 = read();
/* 130 */     long byte3 = read();
/* 131 */     long byte4 = read();
/* 132 */     if (byte4 < 0L)
/*     */     {
/* 134 */       throw new EOFException();
/*     */     }
/* 136 */     return (byte1 << 24) + (byte2 << 16) + (byte3 << 8) + (byte4 << 0);
/*     */   }
/*     */ 
/*     */   public abstract int readUnsignedShort()
/*     */     throws IOException;
/*     */ 
/*     */   public int[] readUnsignedByteArray(int length)
/*     */     throws IOException
/*     */   {
/* 156 */     int[] array = new int[length];
/* 157 */     for (int i = 0; i < length; i++)
/*     */     {
/* 159 */       array[i] = read();
/*     */     }
/* 161 */     return array;
/*     */   }
/*     */ 
/*     */   public int[] readUnsignedShortArray(int length)
/*     */     throws IOException
/*     */   {
/* 173 */     int[] array = new int[length];
/* 174 */     for (int i = 0; i < length; i++)
/*     */     {
/* 176 */       array[i] = readUnsignedShort();
/*     */     }
/* 178 */     return array;
/*     */   }
/*     */ 
/*     */   public abstract short readSignedShort()
/*     */     throws IOException;
/*     */ 
/*     */   public Calendar readInternationalDate()
/*     */     throws IOException
/*     */   {
/* 197 */     long secondsSince1904 = readLong();
/* 198 */     Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
/* 199 */     cal.set(1904, 0, 1, 0, 0, 0);
/* 200 */     cal.set(14, 0);
/* 201 */     long millisFor1904 = cal.getTimeInMillis();
/* 202 */     millisFor1904 += secondsSince1904 * 1000L;
/* 203 */     cal.setTimeInMillis(millisFor1904);
/* 204 */     return cal;
/*     */   }
/*     */ 
/*     */   public abstract void close()
/*     */     throws IOException;
/*     */ 
/*     */   public abstract void seek(long paramLong)
/*     */     throws IOException;
/*     */ 
/*     */   public byte[] read(int numberOfBytes)
/*     */     throws IOException
/*     */   {
/* 231 */     byte[] data = new byte[numberOfBytes];
/* 232 */     int amountRead = 0;
/* 233 */     int totalAmountRead = 0;
/*     */ 
/* 236 */     while ((totalAmountRead < numberOfBytes) && ((amountRead = read(data, totalAmountRead, numberOfBytes - totalAmountRead)) != -1))
/*     */     {
/* 238 */       totalAmountRead += amountRead;
/*     */     }
/* 240 */     if (totalAmountRead == numberOfBytes)
/*     */     {
/* 242 */       return data;
/*     */     }
/*     */ 
/* 246 */     throw new IOException("Unexpected end of TTF stream reached");
/*     */   }
/*     */ 
/*     */   public abstract int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException;
/*     */ 
/*     */   public abstract long getCurrentPosition()
/*     */     throws IOException;
/*     */ 
/*     */   public abstract InputStream getOriginalData()
/*     */     throws IOException;
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.ttf.TTFDataStream
 * JD-Core Version:    0.6.2
 */