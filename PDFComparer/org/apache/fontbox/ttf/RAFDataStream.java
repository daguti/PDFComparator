/*     */ package org.apache.fontbox.ttf;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.RandomAccessFile;
/*     */ 
/*     */ public class RAFDataStream extends TTFDataStream
/*     */ {
/*  34 */   private RandomAccessFile raf = null;
/*  35 */   private File ttfFile = null;
/*     */ 
/*     */   public RAFDataStream(String name, String mode)
/*     */     throws FileNotFoundException
/*     */   {
/*  49 */     this(new File(name), mode);
/*     */   }
/*     */ 
/*     */   public RAFDataStream(File file, String mode)
/*     */     throws FileNotFoundException
/*     */   {
/*  64 */     this.raf = new RandomAccessFile(file, mode);
/*  65 */     this.ttfFile = file;
/*     */   }
/*     */ 
/*     */   public short readSignedShort()
/*     */     throws IOException
/*     */   {
/*  76 */     return this.raf.readShort();
/*     */   }
/*     */ 
/*     */   public long getCurrentPosition()
/*     */     throws IOException
/*     */   {
/*  86 */     return this.raf.getFilePointer();
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/*  96 */     this.raf.close();
/*  97 */     this.raf = null;
/*     */   }
/*     */ 
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/* 107 */     return this.raf.read();
/*     */   }
/*     */ 
/*     */   public int readUnsignedShort()
/*     */     throws IOException
/*     */   {
/* 118 */     return this.raf.readUnsignedShort();
/*     */   }
/*     */ 
/*     */   public long readLong()
/*     */     throws IOException
/*     */   {
/* 128 */     return this.raf.readLong();
/*     */   }
/*     */ 
/*     */   public void seek(long pos)
/*     */     throws IOException
/*     */   {
/* 139 */     this.raf.seek(pos);
/*     */   }
/*     */ 
/*     */   public int read(byte[] b, int off, int len)
/*     */     throws IOException
/*     */   {
/* 158 */     return this.raf.read(b, off, len);
/*     */   }
/*     */ 
/*     */   public InputStream getOriginalData()
/*     */     throws IOException
/*     */   {
/* 166 */     return new FileInputStream(this.ttfFile);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.ttf.RAFDataStream
 * JD-Core Version:    0.6.2
 */