/*     */ package com.itextpdf.text.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ class GroupedRandomAccessSource
/*     */   implements RandomAccessSource
/*     */ {
/*     */   private final SourceEntry[] sources;
/*     */   private SourceEntry currentSourceEntry;
/*     */   private final long size;
/*     */ 
/*     */   public GroupedRandomAccessSource(RandomAccessSource[] sources)
/*     */     throws IOException
/*     */   {
/*  74 */     this.sources = new SourceEntry[sources.length];
/*     */ 
/*  76 */     long totalSize = 0L;
/*  77 */     for (int i = 0; i < sources.length; i++) {
/*  78 */       this.sources[i] = new SourceEntry(i, sources[i], totalSize);
/*  79 */       totalSize += sources[i].length();
/*     */     }
/*  81 */     this.size = totalSize;
/*  82 */     this.currentSourceEntry = this.sources[(sources.length - 1)];
/*  83 */     sourceInUse(this.currentSourceEntry.source);
/*     */   }
/*     */ 
/*     */   protected int getStartingSourceIndex(long offset)
/*     */   {
/*  95 */     if (offset >= this.currentSourceEntry.firstByte) {
/*  96 */       return this.currentSourceEntry.index;
/*     */     }
/*  98 */     return 0;
/*     */   }
/*     */ 
/*     */   private SourceEntry getSourceEntryForOffset(long offset)
/*     */     throws IOException
/*     */   {
/* 109 */     if (offset >= this.size) {
/* 110 */       return null;
/*     */     }
/* 112 */     if ((offset >= this.currentSourceEntry.firstByte) && (offset <= this.currentSourceEntry.lastByte)) {
/* 113 */       return this.currentSourceEntry;
/*     */     }
/*     */ 
/* 116 */     sourceReleased(this.currentSourceEntry.source);
/*     */ 
/* 118 */     int startAt = getStartingSourceIndex(offset);
/*     */ 
/* 120 */     for (int i = startAt; i < this.sources.length; i++) {
/* 121 */       if ((offset >= this.sources[i].firstByte) && (offset <= this.sources[i].lastByte)) {
/* 122 */         this.currentSourceEntry = this.sources[i];
/* 123 */         sourceInUse(this.currentSourceEntry.source);
/* 124 */         return this.currentSourceEntry;
/*     */       }
/*     */     }
/*     */ 
/* 128 */     return null;
/*     */   }
/*     */ 
/*     */   protected void sourceReleased(RandomAccessSource source)
/*     */     throws IOException
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void sourceInUse(RandomAccessSource source)
/*     */     throws IOException
/*     */   {
/*     */   }
/*     */ 
/*     */   public int get(long position)
/*     */     throws IOException
/*     */   {
/* 156 */     SourceEntry entry = getSourceEntryForOffset(position);
/*     */ 
/* 158 */     if (entry == null) {
/* 159 */       return -1;
/*     */     }
/* 161 */     return entry.source.get(entry.offsetN(position));
/*     */   }
/*     */ 
/*     */   public int get(long position, byte[] bytes, int off, int len)
/*     */     throws IOException
/*     */   {
/* 169 */     SourceEntry entry = getSourceEntryForOffset(position);
/*     */ 
/* 171 */     if (entry == null) {
/* 172 */       return -1;
/*     */     }
/* 174 */     long offN = entry.offsetN(position);
/*     */ 
/* 176 */     int remaining = len;
/*     */ 
/* 178 */     while ((remaining > 0) && 
/* 179 */       (entry != null))
/*     */     {
/* 181 */       if (offN > entry.source.length()) {
/*     */         break;
/*     */       }
/* 184 */       int count = entry.source.get(offN, bytes, off, remaining);
/* 185 */       if (count == -1) {
/*     */         break;
/*     */       }
/* 188 */       off += count;
/* 189 */       position += count;
/* 190 */       remaining -= count;
/*     */ 
/* 192 */       offN = 0L;
/* 193 */       entry = getSourceEntryForOffset(position);
/*     */     }
/* 195 */     return remaining == len ? -1 : len - remaining;
/*     */   }
/*     */ 
/*     */   public long length()
/*     */   {
/* 203 */     return this.size;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 211 */     for (SourceEntry entry : this.sources)
/* 212 */       entry.source.close();
/*     */   }
/*     */ 
/*     */   private static class SourceEntry
/*     */   {
/*     */     final RandomAccessSource source;
/*     */     final long firstByte;
/*     */     final long lastByte;
/*     */     final int index;
/*     */ 
/*     */     public SourceEntry(int index, RandomAccessSource source, long offset)
/*     */     {
/* 244 */       this.index = index;
/* 245 */       this.source = source;
/* 246 */       this.firstByte = offset;
/* 247 */       this.lastByte = (offset + source.length() - 1L);
/*     */     }
/*     */ 
/*     */     public long offsetN(long absoluteOffset)
/*     */     {
/* 256 */       return absoluteOffset - this.firstByte;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.io.GroupedRandomAccessSource
 * JD-Core Version:    0.6.2
 */