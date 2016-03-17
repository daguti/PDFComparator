/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.lang.reflect.Method;
/*     */ import java.nio.BufferUnderflowException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.MappedByteBuffer;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.channels.FileChannel.MapMode;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ 
/*     */ public class MappedRandomAccessFile
/*     */ {
/*     */   private static final int BUFSIZE = 1073741824;
/*  67 */   private FileChannel channel = null;
/*     */   private MappedByteBuffer[] mappedBuffers;
/*     */   private long size;
/*     */   private long pos;
/*     */ 
/*     */   public MappedRandomAccessFile(String filename, String mode)
/*     */     throws FileNotFoundException, IOException
/*     */   {
/*  82 */     if (mode.equals("rw")) {
/*  83 */       init(new RandomAccessFile(filename, mode).getChannel(), FileChannel.MapMode.READ_WRITE);
/*     */     }
/*     */     else
/*     */     {
/*  87 */       init(new FileInputStream(filename).getChannel(), FileChannel.MapMode.READ_ONLY);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void init(FileChannel channel, FileChannel.MapMode mapMode)
/*     */     throws IOException
/*     */   {
/* 102 */     this.channel = channel;
/*     */ 
/* 105 */     this.size = channel.size();
/* 106 */     this.pos = 0L;
/* 107 */     int requiredBuffers = (int)(this.size / 1073741824L) + (this.size % 1073741824L == 0L ? 0 : 1);
/*     */ 
/* 110 */     this.mappedBuffers = new MappedByteBuffer[requiredBuffers];
/*     */     try {
/* 112 */       int index = 0;
/* 113 */       for (long offset = 0L; offset < this.size; offset += 1073741824L) {
/* 114 */         long size2 = Math.min(this.size - offset, 1073741824L);
/* 115 */         this.mappedBuffers[index] = channel.map(mapMode, offset, size2);
/* 116 */         this.mappedBuffers[index].load();
/* 117 */         index++;
/*     */       }
/* 119 */       if (index != requiredBuffers)
/* 120 */         throw new Error("Should never happen - " + index + " != " + requiredBuffers);
/*     */     }
/*     */     catch (IOException e) {
/* 123 */       close();
/* 124 */       throw e;
/*     */     } catch (RuntimeException e) {
/* 126 */       close();
/* 127 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public FileChannel getChannel()
/*     */   {
/* 136 */     return this.channel;
/*     */   }
/*     */ 
/*     */   public int read()
/*     */   {
/*     */     try
/*     */     {
/* 145 */       int mapN = (int)(this.pos / 1073741824L);
/* 146 */       int offN = (int)(this.pos % 1073741824L);
/*     */ 
/* 148 */       if (mapN >= this.mappedBuffers.length) {
/* 149 */         return -1;
/*     */       }
/* 151 */       if (offN >= this.mappedBuffers[mapN].limit()) {
/* 152 */         return -1;
/*     */       }
/* 154 */       byte b = this.mappedBuffers[mapN].get(offN);
/* 155 */       this.pos += 1L;
/* 156 */       return b & 0xFF;
/*     */     }
/*     */     catch (BufferUnderflowException e) {
/*     */     }
/* 160 */     return -1;
/*     */   }
/*     */ 
/*     */   public int read(byte[] bytes, int off, int len)
/*     */   {
/* 172 */     int mapN = (int)(this.pos / 1073741824L);
/* 173 */     int offN = (int)(this.pos % 1073741824L);
/* 174 */     int totalRead = 0;
/*     */ 
/* 176 */     while ((totalRead < len) && 
/* 177 */       (mapN < this.mappedBuffers.length))
/*     */     {
/* 179 */       MappedByteBuffer currentBuffer = this.mappedBuffers[mapN];
/* 180 */       if (offN > currentBuffer.limit())
/*     */         break;
/* 182 */       currentBuffer.position(offN);
/* 183 */       int bytesFromThisBuffer = Math.min(len - totalRead, currentBuffer.remaining());
/* 184 */       currentBuffer.get(bytes, off, bytesFromThisBuffer);
/* 185 */       off += bytesFromThisBuffer;
/* 186 */       this.pos += bytesFromThisBuffer;
/* 187 */       totalRead += bytesFromThisBuffer;
/*     */ 
/* 189 */       mapN++;
/* 190 */       offN = 0;
/*     */     }
/*     */ 
/* 193 */     return totalRead == 0 ? -1 : totalRead;
/*     */   }
/*     */ 
/*     */   public long getFilePointer()
/*     */   {
/* 201 */     return this.pos;
/*     */   }
/*     */ 
/*     */   public void seek(long pos)
/*     */   {
/* 209 */     this.pos = pos;
/*     */   }
/*     */ 
/*     */   public long length()
/*     */   {
/* 217 */     return this.size;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 225 */     for (int i = 0; i < this.mappedBuffers.length; i++) {
/* 226 */       if (this.mappedBuffers[i] != null) {
/* 227 */         clean(this.mappedBuffers[i]);
/* 228 */         this.mappedBuffers[i] = null;
/*     */       }
/*     */     }
/*     */ 
/* 232 */     if (this.channel != null)
/* 233 */       this.channel.close();
/* 234 */     this.channel = null;
/*     */   }
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/* 243 */     close();
/* 244 */     super.finalize();
/*     */   }
/*     */ 
/*     */   public static boolean clean(ByteBuffer buffer)
/*     */   {
/* 253 */     if ((buffer == null) || (!buffer.isDirect())) {
/* 254 */       return false;
/*     */     }
/* 256 */     Boolean b = (Boolean)AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Boolean run() {
/* 258 */         Boolean success = Boolean.FALSE;
/*     */         try {
/* 260 */           Method getCleanerMethod = this.val$buffer.getClass().getMethod("cleaner", (Class[])null);
/* 261 */           getCleanerMethod.setAccessible(true);
/* 262 */           Object cleaner = getCleanerMethod.invoke(this.val$buffer, (Object[])null);
/* 263 */           Method clean = cleaner.getClass().getMethod("clean", (Class[])null);
/* 264 */           clean.invoke(cleaner, (Object[])null);
/* 265 */           success = Boolean.TRUE;
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/* 270 */         return success;
/*     */       }
/*     */     });
/* 274 */     return b.booleanValue();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.MappedRandomAccessFile
 * JD-Core Version:    0.6.2
 */