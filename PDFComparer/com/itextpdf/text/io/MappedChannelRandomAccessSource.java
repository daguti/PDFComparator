/*     */ package com.itextpdf.text.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.channels.FileChannel.MapMode;
/*     */ 
/*     */ class MappedChannelRandomAccessSource
/*     */   implements RandomAccessSource
/*     */ {
/*     */   private final FileChannel channel;
/*     */   private final long offset;
/*     */   private final long length;
/*     */   private ByteBufferRandomAccessSource source;
/*     */ 
/*     */   public MappedChannelRandomAccessSource(FileChannel channel, long offset, long length)
/*     */   {
/*  82 */     if (offset < 0L)
/*  83 */       throw new IllegalArgumentException(offset + " is negative");
/*  84 */     if (length <= 0L) {
/*  85 */       throw new IllegalArgumentException(length + " is zero or negative");
/*     */     }
/*  87 */     this.channel = channel;
/*  88 */     this.offset = offset;
/*  89 */     this.length = length;
/*  90 */     this.source = null;
/*     */   }
/*     */ 
/*     */   void open()
/*     */     throws IOException
/*     */   {
/*  98 */     if (this.source != null) {
/*  99 */       return;
/*     */     }
/* 101 */     if (!this.channel.isOpen())
/* 102 */       throw new IllegalStateException("Channel is closed");
/*     */     try
/*     */     {
/* 105 */       this.source = new ByteBufferRandomAccessSource(this.channel.map(FileChannel.MapMode.READ_ONLY, this.offset, this.length));
/*     */     } catch (IOException e) {
/* 107 */       if (exceptionIsMapFailureException(e))
/* 108 */         throw new MapFailedException(e);
/* 109 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static boolean exceptionIsMapFailureException(IOException e)
/*     */   {
/* 122 */     if ((e.getMessage() != null) && (e.getMessage().indexOf("Map failed") >= 0)) {
/* 123 */       return true;
/*     */     }
/* 125 */     return false;
/*     */   }
/*     */ 
/*     */   public int get(long position)
/*     */     throws IOException
/*     */   {
/* 132 */     if (this.source == null)
/* 133 */       throw new IOException("RandomAccessSource not opened");
/* 134 */     return this.source.get(position);
/*     */   }
/*     */ 
/*     */   public int get(long position, byte[] bytes, int off, int len)
/*     */     throws IOException
/*     */   {
/* 141 */     if (this.source == null)
/* 142 */       throw new IOException("RandomAccessSource not opened");
/* 143 */     return this.source.get(position, bytes, off, len);
/*     */   }
/*     */ 
/*     */   public long length()
/*     */   {
/* 150 */     return this.length;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 157 */     if (this.source == null)
/* 158 */       return;
/* 159 */     this.source.close();
/* 160 */     this.source = null;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 165 */     return getClass().getName() + " (" + this.offset + ", " + this.length + ")";
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.io.MappedChannelRandomAccessSource
 * JD-Core Version:    0.6.2
 */