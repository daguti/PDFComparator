/*     */ package org.apache.pdfbox.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public class RandomAccessFileInputStream extends InputStream
/*     */ {
/*     */   private RandomAccess file;
/*     */   private long currentPosition;
/*     */   private long endPosition;
/*     */ 
/*     */   public RandomAccessFileInputStream(RandomAccess raFile, long startPosition, long length)
/*     */   {
/*  44 */     this.file = raFile;
/*  45 */     this.currentPosition = startPosition;
/*  46 */     this.endPosition = (this.currentPosition + length);
/*     */   }
/*     */ 
/*     */   public int available()
/*     */   {
/*  53 */     return (int)(this.endPosition - this.currentPosition);
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/*     */   }
/*     */ 
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/*  67 */     synchronized (this.file)
/*     */     {
/*  69 */       int retval = -1;
/*  70 */       if (this.currentPosition < this.endPosition)
/*     */       {
/*  72 */         this.file.seek(this.currentPosition);
/*  73 */         this.currentPosition += 1L;
/*  74 */         retval = this.file.read();
/*     */       }
/*  76 */       return retval;
/*     */     }
/*     */   }
/*     */ 
/*     */   public int read(byte[] b, int offset, int length)
/*     */     throws IOException
/*     */   {
/*  85 */     if (length > available())
/*     */     {
/*  87 */       length = available();
/*     */     }
/*  89 */     int amountRead = -1;
/*     */ 
/*  92 */     if (available() > 0)
/*     */     {
/*  94 */       synchronized (this.file)
/*     */       {
/*  96 */         this.file.seek(this.currentPosition);
/*  97 */         amountRead = this.file.read(b, offset, length);
/*     */       }
/*     */     }
/*     */ 
/* 101 */     if (amountRead > 0)
/*     */     {
/* 103 */       this.currentPosition += amountRead;
/*     */     }
/* 105 */     return amountRead;
/*     */   }
/*     */ 
/*     */   public long skip(long amountToSkip)
/*     */   {
/* 113 */     long amountSkipped = Math.min(amountToSkip, available());
/* 114 */     this.currentPosition += amountSkipped;
/* 115 */     return amountSkipped;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.io.RandomAccessFileInputStream
 * JD-Core Version:    0.6.2
 */