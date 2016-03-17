/*     */ package org.apache.pdfbox.io;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.Writer;
/*     */ 
/*     */ public class IOUtils
/*     */ {
/*     */   public static byte[] toByteArray(InputStream in)
/*     */     throws IOException
/*     */   {
/*  51 */     ByteArrayOutputStream baout = new ByteArrayOutputStream();
/*  52 */     copy(in, baout);
/*  53 */     return baout.toByteArray();
/*     */   }
/*     */ 
/*     */   public static long copy(InputStream input, OutputStream output)
/*     */     throws IOException
/*     */   {
/*  65 */     byte[] buffer = new byte[4096];
/*  66 */     long count = 0L;
/*  67 */     int n = 0;
/*  68 */     while (-1 != (n = input.read(buffer)))
/*     */     {
/*  70 */       output.write(buffer, 0, n);
/*  71 */       count += n;
/*     */     }
/*  73 */     return count;
/*     */   }
/*     */ 
/*     */   public static long populateBuffer(InputStream in, byte[] buffer)
/*     */     throws IOException
/*     */   {
/*  87 */     int remaining = buffer.length;
/*  88 */     while (remaining > 0)
/*     */     {
/*  90 */       int bufferWritePos = buffer.length - remaining;
/*  91 */       int bytesRead = in.read(buffer, bufferWritePos, remaining);
/*  92 */       if (bytesRead < 0)
/*     */       {
/*     */         break;
/*     */       }
/*  96 */       remaining -= bytesRead;
/*     */     }
/*  98 */     return buffer.length - remaining;
/*     */   }
/*     */ 
/*     */   public static void closeQuietly(InputStream input)
/*     */   {
/*     */     try
/*     */     {
/* 112 */       if (input != null)
/* 113 */         input.close();
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void closeQuietly(Reader input)
/*     */   {
/*     */     try
/*     */     {
/* 130 */       if (input != null)
/* 131 */         input.close();
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void closeQuietly(Writer output)
/*     */   {
/*     */     try
/*     */     {
/* 148 */       if (output != null)
/* 149 */         output.close();
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void closeQuietly(OutputStream output)
/*     */   {
/*     */     try
/*     */     {
/* 166 */       if (output != null)
/* 167 */         output.close();
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.io.IOUtils
 * JD-Core Version:    0.6.2
 */