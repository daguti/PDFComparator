/*     */ package com.itextpdf.text.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ 
/*     */ class RAFRandomAccessSource
/*     */   implements RandomAccessSource
/*     */ {
/*     */   private final RandomAccessFile raf;
/*     */   private final long length;
/*     */ 
/*     */   public RAFRandomAccessSource(RandomAccessFile raf)
/*     */     throws IOException
/*     */   {
/*  71 */     this.raf = raf;
/*  72 */     this.length = raf.length();
/*     */   }
/*     */ 
/*     */   public int get(long position)
/*     */     throws IOException
/*     */   {
/*  80 */     if (position > this.raf.length()) {
/*  81 */       return -1;
/*     */     }
/*     */ 
/*  84 */     this.raf.seek(position);
/*     */ 
/*  86 */     return this.raf.read();
/*     */   }
/*     */ 
/*     */   public int get(long position, byte[] bytes, int off, int len)
/*     */     throws IOException
/*     */   {
/*  93 */     if (position > this.length) {
/*  94 */       return -1;
/*     */     }
/*     */ 
/*  97 */     this.raf.seek(position);
/*     */ 
/*  99 */     return this.raf.read(bytes, off, len);
/*     */   }
/*     */ 
/*     */   public long length()
/*     */   {
/* 108 */     return this.length;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 115 */     this.raf.close();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.io.RAFRandomAccessSource
 * JD-Core Version:    0.6.2
 */