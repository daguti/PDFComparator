/*     */ package org.apache.pdfbox.io;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map.Entry;
/*     */ 
/*     */ public class RandomAccessBufferedFileInputStream extends InputStream
/*     */   implements RandomAccessRead
/*     */ {
/*  41 */   private int pageSizeShift = 12;
/*  42 */   private int pageSize = 1 << this.pageSizeShift;
/*  43 */   private long pageOffsetMask = -1L << this.pageSizeShift;
/*  44 */   private int maxCachedPages = 1000;
/*     */ 
/*  46 */   private byte[] lastRemovedCachePage = null;
/*     */ 
/*  49 */   private final LinkedHashMap<Long, byte[]> pageCache = new LinkedHashMap(this.maxCachedPages, 0.75F, true)
/*     */   {
/*     */     private static final long serialVersionUID = -6302488539257741101L;
/*     */ 
/*     */     protected boolean removeEldestEntry(Map.Entry<Long, byte[]> _eldest)
/*     */     {
/*  57 */       boolean doRemove = size() > RandomAccessBufferedFileInputStream.this.maxCachedPages;
/*  58 */       if (doRemove)
/*     */       {
/*  60 */         RandomAccessBufferedFileInputStream.this.lastRemovedCachePage = ((byte[])_eldest.getValue());
/*     */       }
/*  62 */       return doRemove;
/*     */     }
/*  49 */   };
/*     */ 
/*  66 */   private long curPageOffset = -1L;
/*  67 */   private byte[] curPage = new byte[this.pageSize];
/*  68 */   private int offsetWithinPage = 0;
/*     */   private final RandomAccessFile raFile;
/*     */   private final long fileLength;
/*  72 */   private long fileOffset = 0L;
/*     */ 
/*     */   public RandomAccessBufferedFileInputStream(File _file)
/*     */     throws FileNotFoundException, IOException
/*     */   {
/*  79 */     this.raFile = new RandomAccessFile(_file, "r");
/*  80 */     this.fileLength = _file.length();
/*     */ 
/*  82 */     seek(0L);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public long getFilePointer()
/*     */   {
/*  93 */     return this.fileOffset;
/*     */   }
/*     */ 
/*     */   public long getPosition()
/*     */   {
/* 100 */     return this.fileOffset;
/*     */   }
/*     */ 
/*     */   public void seek(long newOffset)
/*     */     throws IOException
/*     */   {
/* 110 */     long newPageOffset = newOffset & this.pageOffsetMask;
/* 111 */     if (newPageOffset != this.curPageOffset)
/*     */     {
/* 113 */       byte[] newPage = (byte[])this.pageCache.get(Long.valueOf(newPageOffset));
/* 114 */       if (newPage == null)
/*     */       {
/* 116 */         this.raFile.seek(newPageOffset);
/* 117 */         newPage = readPage();
/* 118 */         this.pageCache.put(Long.valueOf(newPageOffset), newPage);
/*     */       }
/* 120 */       this.curPageOffset = newPageOffset;
/* 121 */       this.curPage = newPage;
/*     */     }
/*     */ 
/* 124 */     this.offsetWithinPage = ((int)(newOffset - this.curPageOffset));
/* 125 */     this.fileOffset = newOffset;
/*     */   }
/*     */ 
/*     */   private final byte[] readPage()
/*     */     throws IOException
/*     */   {
/*     */     byte[] page;
/* 138 */     if (this.lastRemovedCachePage != null)
/*     */     {
/* 140 */       byte[] page = this.lastRemovedCachePage;
/* 141 */       this.lastRemovedCachePage = null;
/*     */     }
/*     */     else
/*     */     {
/* 145 */       page = new byte[this.pageSize];
/*     */     }
/*     */ 
/* 148 */     int readBytes = 0;
/* 149 */     while (readBytes < this.pageSize)
/*     */     {
/* 151 */       int curBytesRead = this.raFile.read(page, readBytes, this.pageSize - readBytes);
/* 152 */       if (curBytesRead < 0)
/*     */       {
/*     */         break;
/*     */       }
/*     */ 
/* 157 */       readBytes += curBytesRead;
/*     */     }
/*     */ 
/* 160 */     return page;
/*     */   }
/*     */ 
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/* 167 */     if (this.fileOffset >= this.fileLength)
/*     */     {
/* 169 */       return -1;
/*     */     }
/*     */ 
/* 172 */     if (this.offsetWithinPage == this.pageSize)
/*     */     {
/* 174 */       seek(this.fileOffset);
/*     */     }
/*     */ 
/* 177 */     this.fileOffset += 1L;
/* 178 */     return this.curPage[(this.offsetWithinPage++)] & 0xFF;
/*     */   }
/*     */ 
/*     */   public int read(byte[] b, int off, int len)
/*     */     throws IOException
/*     */   {
/* 185 */     if (this.fileOffset >= this.fileLength)
/*     */     {
/* 187 */       return -1;
/*     */     }
/*     */ 
/* 190 */     if (this.offsetWithinPage == this.pageSize)
/*     */     {
/* 192 */       seek(this.fileOffset);
/*     */     }
/*     */ 
/* 195 */     int commonLen = Math.min(this.pageSize - this.offsetWithinPage, len);
/* 196 */     if (this.fileLength - this.fileOffset < this.pageSize) {
/* 197 */       commonLen = Math.min(commonLen, (int)(this.fileLength - this.fileOffset));
/*     */     }
/* 199 */     System.arraycopy(this.curPage, this.offsetWithinPage, b, off, commonLen);
/*     */ 
/* 201 */     this.offsetWithinPage += commonLen;
/* 202 */     this.fileOffset += commonLen;
/*     */ 
/* 204 */     return commonLen;
/*     */   }
/*     */ 
/*     */   public int available()
/*     */     throws IOException
/*     */   {
/* 211 */     return (int)Math.min(this.fileLength - this.fileOffset, 2147483647L);
/*     */   }
/*     */ 
/*     */   public long skip(long n)
/*     */     throws IOException
/*     */   {
/* 219 */     long toSkip = n;
/*     */ 
/* 221 */     if (this.fileLength - this.fileOffset < toSkip)
/*     */     {
/* 223 */       toSkip = this.fileLength - this.fileOffset;
/*     */     }
/*     */ 
/* 226 */     if ((toSkip < this.pageSize) && (this.offsetWithinPage + toSkip <= this.pageSize))
/*     */     {
/* 229 */       this.offsetWithinPage = ((int)(this.offsetWithinPage + toSkip));
/* 230 */       this.fileOffset += toSkip;
/*     */     }
/*     */     else
/*     */     {
/* 235 */       seek(this.fileOffset + toSkip);
/*     */     }
/*     */ 
/* 238 */     return toSkip;
/*     */   }
/*     */ 
/*     */   public long length()
/*     */     throws IOException
/*     */   {
/* 244 */     return this.fileLength;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 251 */     this.raFile.close();
/* 252 */     this.pageCache.clear();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.io.RandomAccessBufferedFileInputStream
 * JD-Core Version:    0.6.2
 */