/*     */ package org.apache.pdfbox.io;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ 
/*     */ public class RandomAccessFile
/*     */   implements RandomAccess, Closeable
/*     */ {
/*     */   private java.io.RandomAccessFile ras;
/*     */ 
/*     */   public RandomAccessFile(File file, String mode)
/*     */     throws FileNotFoundException
/*     */   {
/*  44 */     this.ras = new java.io.RandomAccessFile(file, mode);
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/*  52 */     this.ras.close();
/*     */   }
/*     */ 
/*     */   public void seek(long position)
/*     */     throws IOException
/*     */   {
/*  60 */     this.ras.seek(position);
/*     */   }
/*     */ 
/*     */   public long getPosition()
/*     */     throws IOException
/*     */   {
/*  67 */     return this.ras.getFilePointer();
/*     */   }
/*     */ 
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/*  75 */     return this.ras.read();
/*     */   }
/*     */ 
/*     */   public int read(byte[] b, int offset, int length)
/*     */     throws IOException
/*     */   {
/*  83 */     return this.ras.read(b, offset, length);
/*     */   }
/*     */ 
/*     */   public long length()
/*     */     throws IOException
/*     */   {
/*  91 */     return this.ras.length();
/*     */   }
/*     */ 
/*     */   public void write(byte[] b, int offset, int length)
/*     */     throws IOException
/*     */   {
/*  99 */     this.ras.write(b, offset, length);
/*     */   }
/*     */ 
/*     */   public void write(int b)
/*     */     throws IOException
/*     */   {
/* 107 */     this.ras.write(b);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.io.RandomAccessFile
 * JD-Core Version:    0.6.2
 */