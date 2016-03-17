/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ public class PdfLiteral extends PdfObject
/*     */ {
/*     */   private long position;
/*     */ 
/*     */   public PdfLiteral(String text)
/*     */   {
/*  59 */     super(0, text);
/*     */   }
/*     */ 
/*     */   public PdfLiteral(byte[] b) {
/*  63 */     super(0, b);
/*     */   }
/*     */ 
/*     */   public PdfLiteral(int size) {
/*  67 */     super(0, (byte[])null);
/*  68 */     this.bytes = new byte[size];
/*  69 */     Arrays.fill(this.bytes, (byte)32);
/*     */   }
/*     */ 
/*     */   public PdfLiteral(int type, String text) {
/*  73 */     super(type, text);
/*     */   }
/*     */ 
/*     */   public PdfLiteral(int type, byte[] b) {
/*  77 */     super(type, b);
/*     */   }
/*     */ 
/*     */   public void toPdf(PdfWriter writer, OutputStream os) throws IOException {
/*  81 */     if ((os instanceof OutputStreamCounter))
/*  82 */       this.position = ((OutputStreamCounter)os).getCounter();
/*  83 */     super.toPdf(writer, os);
/*     */   }
/*     */ 
/*     */   public long getPosition()
/*     */   {
/*  91 */     return this.position;
/*     */   }
/*     */ 
/*     */   public int getPosLength()
/*     */   {
/*  99 */     if (this.bytes != null) {
/* 100 */       return this.bytes.length;
/*     */     }
/* 102 */     return 0;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfLiteral
 * JD-Core Version:    0.6.2
 */