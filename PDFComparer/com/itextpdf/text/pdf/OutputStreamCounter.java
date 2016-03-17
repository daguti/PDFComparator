/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ public class OutputStreamCounter extends OutputStream
/*     */ {
/*     */   protected OutputStream out;
/*  56 */   protected long counter = 0L;
/*     */ 
/*     */   public OutputStreamCounter(OutputStream out)
/*     */   {
/*  60 */     this.out = out;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/*  74 */     this.out.close();
/*     */   }
/*     */ 
/*     */   public void flush()
/*     */     throws IOException
/*     */   {
/*  90 */     this.out.flush();
/*     */   }
/*     */ 
/*     */   public void write(byte[] b)
/*     */     throws IOException
/*     */   {
/* 104 */     this.counter += b.length;
/* 105 */     this.out.write(b);
/*     */   }
/*     */ 
/*     */   public void write(int b)
/*     */     throws IOException
/*     */   {
/* 124 */     this.counter += 1L;
/* 125 */     this.out.write(b);
/*     */   }
/*     */ 
/*     */   public void write(byte[] b, int off, int len)
/*     */     throws IOException
/*     */   {
/* 157 */     this.counter += len;
/* 158 */     this.out.write(b, off, len);
/*     */   }
/*     */ 
/*     */   public long getCounter() {
/* 162 */     return this.counter;
/*     */   }
/*     */ 
/*     */   public void resetCounter() {
/* 166 */     this.counter = 0L;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.OutputStreamCounter
 * JD-Core Version:    0.6.2
 */