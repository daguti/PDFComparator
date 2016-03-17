/*     */ package org.apache.pdfbox.encryption;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ public class ARCFour
/*     */ {
/*     */   private int[] salt;
/*     */   private int b;
/*     */   private int c;
/*     */ 
/*     */   public ARCFour()
/*     */   {
/*  41 */     this.salt = new int[256];
/*     */   }
/*     */ 
/*     */   public void setKey(byte[] key)
/*     */   {
/*  51 */     this.b = 0;
/*  52 */     this.c = 0;
/*     */ 
/*  54 */     if ((key.length < 1) || (key.length > 32))
/*     */     {
/*  56 */       throw new IllegalArgumentException("number of bytes must be between 1 and 32");
/*     */     }
/*  58 */     for (int i = 0; i < this.salt.length; i++)
/*     */     {
/*  60 */       this.salt[i] = i;
/*     */     }
/*     */ 
/*  63 */     int keyIndex = 0;
/*  64 */     int saltIndex = 0;
/*  65 */     for (int i = 0; i < this.salt.length; i++)
/*     */     {
/*  67 */       saltIndex = (fixByte(key[keyIndex]) + this.salt[i] + saltIndex) % 256;
/*  68 */       swap(this.salt, i, saltIndex);
/*  69 */       keyIndex = (keyIndex + 1) % key.length;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static final int fixByte(byte aByte)
/*     */   {
/*  83 */     return aByte < 0 ? 256 + aByte : aByte;
/*     */   }
/*     */ 
/*     */   private static final void swap(int[] data, int firstIndex, int secondIndex)
/*     */   {
/*  95 */     int tmp = data[firstIndex];
/*  96 */     data[firstIndex] = data[secondIndex];
/*  97 */     data[secondIndex] = tmp;
/*     */   }
/*     */ 
/*     */   public void write(byte aByte, OutputStream output)
/*     */     throws IOException
/*     */   {
/* 110 */     this.b = ((this.b + 1) % 256);
/* 111 */     this.c = ((this.salt[this.b] + this.c) % 256);
/* 112 */     swap(this.salt, this.b, this.c);
/* 113 */     int saltIndex = (this.salt[this.b] + this.salt[this.c]) % 256;
/* 114 */     output.write(aByte ^ (byte)this.salt[saltIndex]);
/*     */   }
/*     */ 
/*     */   public void write(byte[] data, OutputStream output)
/*     */     throws IOException
/*     */   {
/* 127 */     for (int i = 0; i < data.length; i++)
/*     */     {
/* 129 */       write(data[i], output);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(InputStream data, OutputStream output)
/*     */     throws IOException
/*     */   {
/* 143 */     byte[] buffer = new byte[1024];
/* 144 */     int amountRead = 0;
/* 145 */     while ((amountRead = data.read(buffer)) != -1)
/*     */     {
/* 147 */       write(buffer, 0, amountRead, output);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(byte[] data, int offset, int len, OutputStream output)
/*     */     throws IOException
/*     */   {
/* 163 */     for (int i = offset; i < offset + len; i++)
/*     */     {
/* 165 */       write(data[i], output);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.encryption.ARCFour
 * JD-Core Version:    0.6.2
 */