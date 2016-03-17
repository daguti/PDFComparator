/*     */ package com.itextpdf.text.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.FileChannel;
/*     */ 
/*     */ public class FileChannelRandomAccessSource
/*     */   implements RandomAccessSource
/*     */ {
/*     */   private final FileChannel channel;
/*     */   private final MappedChannelRandomAccessSource source;
/*     */ 
/*     */   public FileChannelRandomAccessSource(FileChannel channel)
/*     */     throws IOException
/*     */   {
/*  70 */     this.channel = channel;
/*  71 */     this.source = new MappedChannelRandomAccessSource(channel, 0L, channel.size());
/*  72 */     this.source.open();
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/*  81 */     this.source.close();
/*  82 */     this.channel.close();
/*     */   }
/*     */ 
/*     */   public int get(long position)
/*     */     throws IOException
/*     */   {
/*  90 */     return this.source.get(position);
/*     */   }
/*     */ 
/*     */   public int get(long position, byte[] bytes, int off, int len)
/*     */     throws IOException
/*     */   {
/*  98 */     return this.source.get(position, bytes, off, len);
/*     */   }
/*     */ 
/*     */   public long length()
/*     */   {
/* 106 */     return this.source.length();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.io.FileChannelRandomAccessSource
 * JD-Core Version:    0.6.2
 */