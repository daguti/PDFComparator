/*     */ package com.itextpdf.text.io;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.net.URL;
/*     */ import java.nio.channels.FileChannel;
/*     */ 
/*     */ public final class RandomAccessSourceFactory
/*     */ {
/*  66 */   private boolean forceRead = false;
/*     */ 
/*  71 */   private boolean usePlainRandomAccess = false;
/*     */ 
/*  76 */   private boolean exclusivelyLockFile = false;
/*     */ 
/*     */   public RandomAccessSourceFactory setForceRead(boolean forceRead)
/*     */   {
/*  90 */     this.forceRead = forceRead;
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */   public RandomAccessSourceFactory setUsePlainRandomAccess(boolean usePlainRandomAccess)
/*     */   {
/* 100 */     this.usePlainRandomAccess = usePlainRandomAccess;
/* 101 */     return this;
/*     */   }
/*     */ 
/*     */   public RandomAccessSourceFactory setExclusivelyLockFile(boolean exclusivelyLockFile) {
/* 105 */     this.exclusivelyLockFile = exclusivelyLockFile;
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */   public RandomAccessSource createSource(byte[] data)
/*     */   {
/* 115 */     return new ArrayRandomAccessSource(data);
/*     */   }
/*     */ 
/*     */   public RandomAccessSource createSource(RandomAccessFile raf) throws IOException {
/* 119 */     return new RAFRandomAccessSource(raf);
/*     */   }
/*     */ 
/*     */   public RandomAccessSource createSource(URL url)
/*     */     throws IOException
/*     */   {
/* 129 */     InputStream is = url.openStream();
/*     */     try {
/* 131 */       return createSource(is);
/*     */     } finally {
/*     */       try {
/* 134 */         is.close();
/*     */       }
/*     */       catch (IOException ioe)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public RandomAccessSource createSource(InputStream is) throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 146 */       return createSource(StreamUtil.inputStreamToArray(is));
/*     */     } finally {
/*     */       try {
/* 149 */         is.close();
/*     */       }
/*     */       catch (IOException ioe)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public RandomAccessSource createBestSource(String filename)
/*     */     throws IOException
/*     */   {
/* 162 */     File file = new File(filename);
/* 163 */     if (!file.canRead()) {
/* 164 */       if ((filename.startsWith("file:/")) || (filename.startsWith("http://")) || (filename.startsWith("https://")) || (filename.startsWith("jar:")) || (filename.startsWith("wsjar:")) || (filename.startsWith("wsjar:")) || (filename.startsWith("vfszip:")))
/*     */       {
/* 171 */         return createSource(new URL(filename));
/*     */       }
/* 173 */       return createByReadingToMemory(filename);
/*     */     }
/*     */ 
/* 177 */     if (this.forceRead) {
/* 178 */       return createByReadingToMemory(new FileInputStream(filename));
/*     */     }
/*     */ 
/* 181 */     String openMode = this.exclusivelyLockFile ? "rw" : "r";
/*     */ 
/* 183 */     RandomAccessFile raf = new RandomAccessFile(file, openMode);
/* 184 */     if (this.exclusivelyLockFile) {
/* 185 */       raf.getChannel().lock();
/*     */     }
/*     */ 
/* 188 */     if (this.usePlainRandomAccess) {
/* 189 */       return new RAFRandomAccessSource(raf);
/*     */     }
/*     */     try
/*     */     {
/* 193 */       if (raf.length() <= 0L) {
/* 194 */         return new RAFRandomAccessSource(raf);
/*     */       }
/*     */       try
/*     */       {
/* 198 */         return createBestSource(raf.getChannel());
/*     */       } catch (MapFailedException e) {
/* 200 */         return new RAFRandomAccessSource(raf);
/*     */       }
/*     */     } catch (IOException e) {
/*     */       try {
/* 204 */         raf.close(); } catch (IOException ignore) {
/*     */       }
/* 206 */       throw e;
/*     */     } catch (RuntimeException e) {
/*     */       try {
/* 209 */         raf.close(); } catch (IOException ignore) {
/*     */       }
/* 211 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public RandomAccessSource createBestSource(FileChannel channel)
/*     */     throws IOException
/*     */   {
/* 224 */     if (channel.size() <= 67108864L) {
/* 225 */       return new GetBufferedRandomAccessSource(new FileChannelRandomAccessSource(channel));
/*     */     }
/* 227 */     return new GetBufferedRandomAccessSource(new PagedChannelRandomAccessSource(channel));
/*     */   }
/*     */ 
/*     */   public RandomAccessSource createRanged(RandomAccessSource source, long[] ranges) throws IOException
/*     */   {
/* 232 */     RandomAccessSource[] sources = new RandomAccessSource[ranges.length / 2];
/* 233 */     for (int i = 0; i < ranges.length; i += 2) {
/* 234 */       sources[(i / 2)] = new WindowRandomAccessSource(source, ranges[i], ranges[(i + 1)]);
/*     */     }
/* 236 */     return new GroupedRandomAccessSource(sources);
/*     */   }
/*     */ 
/*     */   private RandomAccessSource createByReadingToMemory(String filename)
/*     */     throws IOException
/*     */   {
/* 246 */     InputStream is = StreamUtil.getResourceStream(filename);
/* 247 */     if (is == null)
/* 248 */       throw new IOException(MessageLocalization.getComposedMessage("1.not.found.as.file.or.resource", new Object[] { filename }));
/* 249 */     return createByReadingToMemory(is);
/*     */   }
/*     */ 
/*     */   private RandomAccessSource createByReadingToMemory(InputStream is)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 260 */       return new ArrayRandomAccessSource(StreamUtil.inputStreamToArray(is));
/*     */     } finally {
/*     */       try {
/* 263 */         is.close();
/*     */       }
/*     */       catch (IOException ioe)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.io.RandomAccessSourceFactory
 * JD-Core Version:    0.6.2
 */