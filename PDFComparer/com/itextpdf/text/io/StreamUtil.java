/*     */ package com.itextpdf.text.io;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ public final class StreamUtil
/*     */ {
/*     */   public static byte[] inputStreamToArray(InputStream is)
/*     */     throws IOException
/*     */   {
/*  68 */     byte[] b = new byte[8192];
/*  69 */     ByteArrayOutputStream out = new ByteArrayOutputStream();
/*     */     while (true) {
/*  71 */       int read = is.read(b);
/*  72 */       if (read < 1)
/*     */         break;
/*  74 */       out.write(b, 0, read);
/*     */     }
/*  76 */     out.close();
/*  77 */     return out.toByteArray();
/*     */   }
/*     */ 
/*     */   public static void CopyBytes(RandomAccessSource source, long start, long length, OutputStream outs) throws IOException {
/*  81 */     if (length <= 0L)
/*  82 */       return;
/*  83 */     long idx = start;
/*  84 */     byte[] buf = new byte[8192];
/*  85 */     while (length > 0L) {
/*  86 */       long n = source.get(idx, buf, 0, (int)Math.min(buf.length, length));
/*  87 */       if (n <= 0L)
/*  88 */         throw new EOFException();
/*  89 */       outs.write(buf, 0, (int)n);
/*  90 */       idx += n;
/*  91 */       length -= n;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static InputStream getResourceStream(String key)
/*     */   {
/* 102 */     return getResourceStream(key, null);
/*     */   }
/*     */ 
/*     */   public static InputStream getResourceStream(String key, ClassLoader loader)
/*     */   {
/* 114 */     if (key.startsWith("/"))
/* 115 */       key = key.substring(1);
/* 116 */     InputStream is = null;
/* 117 */     if (loader != null) {
/* 118 */       is = loader.getResourceAsStream(key);
/* 119 */       if (is != null)
/* 120 */         return is;
/*     */     }
/*     */     try
/*     */     {
/* 124 */       ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
/* 125 */       if (contextClassLoader != null) {
/* 126 */         is = contextClassLoader.getResourceAsStream(key);
/*     */       }
/*     */     }
/*     */     catch (Throwable e)
/*     */     {
/*     */     }
/* 132 */     if (is == null) {
/* 133 */       is = StreamUtil.class.getResourceAsStream("/" + key);
/*     */     }
/* 135 */     if (is == null) {
/* 136 */       is = ClassLoader.getSystemResourceAsStream(key);
/*     */     }
/* 138 */     return is;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.io.StreamUtil
 * JD-Core Version:    0.6.2
 */