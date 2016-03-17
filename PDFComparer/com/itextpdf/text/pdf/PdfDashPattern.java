/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ public class PdfDashPattern extends PdfArray
/*     */ {
/*  62 */   private float dash = -1.0F;
/*     */ 
/*  65 */   private float gap = -1.0F;
/*     */ 
/*  68 */   private float phase = -1.0F;
/*     */ 
/*     */   public PdfDashPattern()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PdfDashPattern(float dash)
/*     */   {
/*  85 */     super(new PdfNumber(dash));
/*  86 */     this.dash = dash;
/*     */   }
/*     */ 
/*     */   public PdfDashPattern(float dash, float gap)
/*     */   {
/*  94 */     super(new PdfNumber(dash));
/*  95 */     add(new PdfNumber(gap));
/*  96 */     this.dash = dash;
/*  97 */     this.gap = gap;
/*     */   }
/*     */ 
/*     */   public PdfDashPattern(float dash, float gap, float phase)
/*     */   {
/* 105 */     super(new PdfNumber(dash));
/* 106 */     add(new PdfNumber(gap));
/* 107 */     this.dash = dash;
/* 108 */     this.gap = gap;
/* 109 */     this.phase = phase;
/*     */   }
/*     */ 
/*     */   public void add(float n) {
/* 113 */     add(new PdfNumber(n));
/*     */   }
/*     */ 
/*     */   public void toPdf(PdfWriter writer, OutputStream os)
/*     */     throws IOException
/*     */   {
/* 121 */     os.write(91);
/*     */ 
/* 123 */     if (this.dash >= 0.0F) {
/* 124 */       new PdfNumber(this.dash).toPdf(writer, os);
/* 125 */       if (this.gap >= 0.0F) {
/* 126 */         os.write(32);
/* 127 */         new PdfNumber(this.gap).toPdf(writer, os);
/*     */       }
/*     */     }
/* 130 */     os.write(93);
/* 131 */     if (this.phase >= 0.0F) {
/* 132 */       os.write(32);
/* 133 */       new PdfNumber(this.phase).toPdf(writer, os);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfDashPattern
 * JD-Core Version:    0.6.2
 */