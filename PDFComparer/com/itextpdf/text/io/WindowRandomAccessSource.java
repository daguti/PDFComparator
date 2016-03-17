/*     */ package com.itextpdf.text.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ public class WindowRandomAccessSource
/*     */   implements RandomAccessSource
/*     */ {
/*     */   private final RandomAccessSource source;
/*     */   private final long offset;
/*     */   private final long length;
/*     */ 
/*     */   public WindowRandomAccessSource(RandomAccessSource source, long offset)
/*     */   {
/*  74 */     this(source, offset, source.length() - offset);
/*     */   }
/*     */ 
/*     */   public WindowRandomAccessSource(RandomAccessSource source, long offset, long length)
/*     */   {
/*  84 */     this.source = source;
/*  85 */     this.offset = offset;
/*  86 */     this.length = length;
/*     */   }
/*     */ 
/*     */   public int get(long position)
/*     */     throws IOException
/*     */   {
/*  94 */     if (position >= this.length) return -1;
/*  95 */     return this.source.get(this.offset + position);
/*     */   }
/*     */ 
/*     */   public int get(long position, byte[] bytes, int off, int len)
/*     */     throws IOException
/*     */   {
/* 103 */     if (position >= this.length) {
/* 104 */       return -1;
/*     */     }
/* 106 */     long toRead = Math.min(len, this.length - position);
/* 107 */     return this.source.get(this.offset + position, bytes, off, (int)toRead);
/*     */   }
/*     */ 
/*     */   public long length()
/*     */   {
/* 115 */     return this.length;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 122 */     this.source.close();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.io.WindowRandomAccessSource
 * JD-Core Version:    0.6.2
 */