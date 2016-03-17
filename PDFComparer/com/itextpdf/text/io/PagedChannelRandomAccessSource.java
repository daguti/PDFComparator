/*     */ package com.itextpdf.text.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ 
/*     */ class PagedChannelRandomAccessSource extends GroupedRandomAccessSource
/*     */   implements RandomAccessSource
/*     */ {
/*     */   public static final int DEFAULT_TOTAL_BUFSIZE = 67108864;
/*     */   public static final int DEFAULT_MAX_OPEN_BUFFERS = 16;
/*     */   private final int bufferSize;
/*     */   private final FileChannel channel;
/*     */   private final MRU<RandomAccessSource> mru;
/*     */ 
/*     */   public PagedChannelRandomAccessSource(FileChannel channel)
/*     */     throws IOException
/*     */   {
/*  85 */     this(channel, 67108864, 16);
/*     */   }
/*     */ 
/*     */   public PagedChannelRandomAccessSource(FileChannel channel, int totalBufferSize, int maxOpenBuffers)
/*     */     throws IOException
/*     */   {
/*  95 */     super(buildSources(channel, totalBufferSize / maxOpenBuffers));
/*  96 */     this.channel = channel;
/*  97 */     this.bufferSize = (totalBufferSize / maxOpenBuffers);
/*  98 */     this.mru = new MRU(maxOpenBuffers);
/*     */   }
/*     */ 
/*     */   private static RandomAccessSource[] buildSources(FileChannel channel, int bufferSize)
/*     */     throws IOException
/*     */   {
/* 109 */     long size = channel.size();
/* 110 */     if (size <= 0L) {
/* 111 */       throw new IOException("File size must be greater than zero");
/*     */     }
/* 113 */     int bufferCount = (int)(size / bufferSize) + (size % bufferSize == 0L ? 0 : 1);
/*     */ 
/* 115 */     MappedChannelRandomAccessSource[] sources = new MappedChannelRandomAccessSource[bufferCount];
/* 116 */     for (int i = 0; i < bufferCount; i++) {
/* 117 */       long pageOffset = i * bufferSize;
/* 118 */       long pageLength = Math.min(size - pageOffset, bufferSize);
/* 119 */       sources[i] = new MappedChannelRandomAccessSource(channel, pageOffset, pageLength);
/*     */     }
/* 121 */     return sources;
/*     */   }
/*     */ 
/*     */   protected int getStartingSourceIndex(long offset)
/*     */   {
/* 130 */     return (int)(offset / this.bufferSize);
/*     */   }
/*     */ 
/*     */   protected void sourceReleased(RandomAccessSource source)
/*     */     throws IOException
/*     */   {
/* 139 */     RandomAccessSource old = (RandomAccessSource)this.mru.enqueue(source);
/* 140 */     if (old != null)
/* 141 */       old.close();
/*     */   }
/*     */ 
/*     */   protected void sourceInUse(RandomAccessSource source)
/*     */     throws IOException
/*     */   {
/* 150 */     ((MappedChannelRandomAccessSource)source).open();
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 159 */     super.close();
/* 160 */     this.channel.close();
/*     */   }
/*     */ 
/*     */   private static class MRU<E>
/*     */   {
/*     */     private final int limit;
/* 172 */     private LinkedList<E> queue = new LinkedList();
/*     */ 
/*     */     public MRU(int limit)
/*     */     {
/* 179 */       this.limit = limit;
/*     */     }
/*     */ 
/*     */     public E enqueue(E newElement)
/*     */     {
/* 189 */       if ((this.queue.size() > 0) && (this.queue.getFirst() == newElement)) {
/* 190 */         return null;
/*     */       }
/* 192 */       for (Iterator it = this.queue.iterator(); it.hasNext(); ) {
/* 193 */         Object element = it.next();
/* 194 */         if (newElement == element) {
/* 195 */           it.remove();
/* 196 */           this.queue.addFirst(newElement);
/* 197 */           return null;
/*     */         }
/*     */       }
/* 200 */       this.queue.addFirst(newElement);
/*     */ 
/* 202 */       if (this.queue.size() > this.limit) {
/* 203 */         return this.queue.removeLast();
/*     */       }
/* 205 */       return null;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.io.PagedChannelRandomAccessSource
 * JD-Core Version:    0.6.2
 */