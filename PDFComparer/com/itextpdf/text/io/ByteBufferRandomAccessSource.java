/*     */ package com.itextpdf.text.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.nio.BufferUnderflowException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ 
/*     */ class ByteBufferRandomAccessSource
/*     */   implements RandomAccessSource
/*     */ {
/*     */   private final ByteBuffer byteBuffer;
/*     */ 
/*     */   public ByteBufferRandomAccessSource(ByteBuffer byteBuffer)
/*     */   {
/*  69 */     this.byteBuffer = byteBuffer;
/*     */   }
/*     */ 
/*     */   public int get(long position)
/*     */     throws IOException
/*     */   {
/*  79 */     if (position > 2147483647L) {
/*  80 */       throw new IllegalArgumentException("Position must be less than Integer.MAX_VALUE");
/*     */     }
/*     */     try
/*     */     {
/*  84 */       if (position >= this.byteBuffer.limit()) {
/*  85 */         return -1;
/*     */       }
/*  87 */       byte b = this.byteBuffer.get((int)position);
/*     */ 
/*  89 */       return b & 0xFF;
/*     */     }
/*     */     catch (BufferUnderflowException e) {
/*     */     }
/*  93 */     return -1;
/*     */   }
/*     */ 
/*     */   public int get(long position, byte[] bytes, int off, int len)
/*     */     throws IOException
/*     */   {
/* 104 */     if (position > 2147483647L) {
/* 105 */       throw new IllegalArgumentException("Position must be less than Integer.MAX_VALUE");
/*     */     }
/* 107 */     if (position >= this.byteBuffer.limit()) {
/* 108 */       return -1;
/*     */     }
/* 110 */     this.byteBuffer.position((int)position);
/* 111 */     int bytesFromThisBuffer = Math.min(len, this.byteBuffer.remaining());
/* 112 */     this.byteBuffer.get(bytes, off, bytesFromThisBuffer);
/*     */ 
/* 114 */     return bytesFromThisBuffer;
/*     */   }
/*     */ 
/*     */   public long length()
/*     */   {
/* 123 */     return this.byteBuffer.limit();
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 131 */     clean(this.byteBuffer);
/*     */   }
/*     */ 
/*     */   private static boolean clean(ByteBuffer buffer)
/*     */   {
/* 140 */     if ((buffer == null) || (!buffer.isDirect())) {
/* 141 */       return false;
/*     */     }
/* 143 */     Boolean b = (Boolean)AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Boolean run() {
/* 145 */         Boolean success = Boolean.FALSE;
/*     */         try {
/* 147 */           Method getCleanerMethod = this.val$buffer.getClass().getMethod("cleaner", (Class[])null);
/* 148 */           getCleanerMethod.setAccessible(true);
/* 149 */           Object cleaner = getCleanerMethod.invoke(this.val$buffer, (Object[])null);
/* 150 */           Method clean = cleaner.getClass().getMethod("clean", (Class[])null);
/* 151 */           clean.invoke(cleaner, (Object[])null);
/* 152 */           success = Boolean.TRUE;
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/* 157 */         return success;
/*     */       }
/*     */     });
/* 161 */     return b.booleanValue();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.io.ByteBufferRandomAccessSource
 * JD-Core Version:    0.6.2
 */