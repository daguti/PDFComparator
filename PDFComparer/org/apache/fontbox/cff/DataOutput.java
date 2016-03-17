/*     */ package org.apache.fontbox.cff;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ 
/*     */ public class DataOutput
/*     */ {
/*  30 */   private ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
/*     */ 
/*  32 */   private String outputEncoding = null;
/*     */ 
/*     */   public DataOutput()
/*     */   {
/*  39 */     this("ISO-8859-1");
/*     */   }
/*     */ 
/*     */   public DataOutput(String encoding)
/*     */   {
/*  48 */     this.outputEncoding = encoding;
/*     */   }
/*     */ 
/*     */   public byte[] getBytes()
/*     */   {
/*  57 */     return this.outputBuffer.toByteArray();
/*     */   }
/*     */ 
/*     */   public void write(int value)
/*     */   {
/*  66 */     this.outputBuffer.write(value);
/*     */   }
/*     */ 
/*     */   public void write(byte[] buffer)
/*     */   {
/*  75 */     this.outputBuffer.write(buffer, 0, buffer.length);
/*     */   }
/*     */ 
/*     */   public void write(byte[] buffer, int offset, int length)
/*     */   {
/*  86 */     this.outputBuffer.write(buffer, offset, length);
/*     */   }
/*     */ 
/*     */   public void print(String string)
/*     */     throws IOException
/*     */   {
/*  96 */     write(string.getBytes(this.outputEncoding));
/*     */   }
/*     */ 
/*     */   public void println(String string)
/*     */     throws IOException
/*     */   {
/* 107 */     write(string.getBytes(this.outputEncoding));
/* 108 */     write(10);
/*     */   }
/*     */ 
/*     */   public void println()
/*     */   {
/* 116 */     write(10);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.cff.DataOutput
 * JD-Core Version:    0.6.2
 */