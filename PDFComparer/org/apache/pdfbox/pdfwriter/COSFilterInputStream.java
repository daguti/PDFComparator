/*     */ package org.apache.pdfbox.pdfwriter;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public class COSFilterInputStream extends FilterInputStream
/*     */ {
/*     */   int[] byteRange;
/*  29 */   long position = 0L;
/*     */ 
/*     */   public COSFilterInputStream(InputStream in, int[] byteRange)
/*     */   {
/*  33 */     super(in);
/*  34 */     this.byteRange = byteRange;
/*     */   }
/*     */ 
/*     */   public COSFilterInputStream(byte[] in, int[] byteRange)
/*     */   {
/*  39 */     super(new ByteArrayInputStream(in));
/*  40 */     this.byteRange = byteRange;
/*     */   }
/*     */ 
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/*  46 */     nextAvailable();
/*  47 */     int i = super.read();
/*  48 */     if (i > -1)
/*  49 */       this.position += 1L;
/*  50 */     return i;
/*     */   }
/*     */ 
/*     */   public int read(byte[] b)
/*     */     throws IOException
/*     */   {
/*  56 */     return read(b, 0, b.length);
/*     */   }
/*     */ 
/*     */   public int read(byte[] b, int off, int len)
/*     */     throws IOException
/*     */   {
/*  62 */     if (b == null)
/*  63 */       throw new NullPointerException();
/*  64 */     if ((off < 0) || (len < 0) || (len > b.length - off))
/*  65 */       throw new IndexOutOfBoundsException();
/*  66 */     if (len == 0) {
/*  67 */       return 0;
/*     */     }
/*     */ 
/*  70 */     int c = read();
/*  71 */     if (c == -1) {
/*  72 */       return -1;
/*     */     }
/*  74 */     b[off] = ((byte)c);
/*     */ 
/*  76 */     int i = 1;
/*     */     try {
/*  78 */       for (; i < len; i++) {
/*  79 */         c = read();
/*  80 */         if (c == -1) {
/*     */           break;
/*     */         }
/*  83 */         b[(off + i)] = ((byte)c);
/*     */       }
/*     */     } catch (IOException ee) {
/*     */     }
/*  87 */     return i;
/*     */   }
/*     */ 
/*     */   private boolean inRange() throws IOException {
/*  91 */     long pos = this.position;
/*  92 */     for (int i = 0; i < this.byteRange.length / 2; i++)
/*     */     {
/*  94 */       if ((this.byteRange[(i * 2)] <= pos) && (this.byteRange[(i * 2)] + this.byteRange[(i * 2 + 1)] > pos))
/*     */       {
/*  96 */         return true;
/*     */       }
/*     */     }
/*  99 */     return false;
/*     */   }
/*     */ 
/*     */   private void nextAvailable() throws IOException {
/* 103 */     while (!inRange()) {
/* 104 */       this.position += 1L;
/* 105 */       if (super.read() < 0)
/* 106 */         break;
/*     */     }
/*     */   }
/*     */ 
/*     */   public byte[] toByteArray() throws IOException
/*     */   {
/* 112 */     ByteArrayOutputStream byteOS = new ByteArrayOutputStream();
/* 113 */     byte[] buffer = new byte[1024];
/*     */     int c;
/* 115 */     while ((c = read(buffer)) != -1)
/* 116 */       byteOS.write(buffer, 0, c);
/* 117 */     return byteOS.toByteArray();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdfwriter.COSFilterInputStream
 * JD-Core Version:    0.6.2
 */