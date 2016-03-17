/*     */ package com.itextpdf.text.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ public class GetBufferedRandomAccessSource
/*     */   implements RandomAccessSource
/*     */ {
/*     */   private final RandomAccessSource source;
/*     */   private final byte[] getBuffer;
/*  57 */   private long getBufferStart = -1L;
/*  58 */   private long getBufferEnd = -1L;
/*     */ 
/*     */   public GetBufferedRandomAccessSource(RandomAccessSource source)
/*     */   {
/*  65 */     this.source = source;
/*     */ 
/*  67 */     this.getBuffer = new byte[(int)Math.min(Math.max(source.length() / 4L, 1L), 4096L)];
/*  68 */     this.getBufferStart = -1L;
/*  69 */     this.getBufferEnd = -1L;
/*     */   }
/*     */ 
/*     */   public int get(long position)
/*     */     throws IOException
/*     */   {
/*  77 */     if ((position < this.getBufferStart) || (position > this.getBufferEnd)) {
/*  78 */       int count = this.source.get(position, this.getBuffer, 0, this.getBuffer.length);
/*  79 */       if (count == -1)
/*  80 */         return -1;
/*  81 */       this.getBufferStart = position;
/*  82 */       this.getBufferEnd = (position + count - 1L);
/*     */     }
/*  84 */     int bufPos = (int)(position - this.getBufferStart);
/*  85 */     return 0xFF & this.getBuffer[bufPos];
/*     */   }
/*     */ 
/*     */   public int get(long position, byte[] bytes, int off, int len)
/*     */     throws IOException
/*     */   {
/*  92 */     return this.source.get(position, bytes, off, len);
/*     */   }
/*     */ 
/*     */   public long length()
/*     */   {
/*  99 */     return this.source.length();
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 106 */     this.source.close();
/* 107 */     this.getBufferStart = -1L;
/* 108 */     this.getBufferEnd = -1L;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.io.GetBufferedRandomAccessSource
 * JD-Core Version:    0.6.2
 */