/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ 
/*     */ class PdfFont
/*     */   implements Comparable<PdfFont>
/*     */ {
/*     */   private BaseFont font;
/*     */   private float size;
/*  74 */   protected float hScale = 1.0F;
/*     */ 
/*     */   PdfFont(BaseFont bf, float size)
/*     */   {
/*  79 */     this.size = size;
/*  80 */     this.font = bf;
/*     */   }
/*     */ 
/*     */   public int compareTo(PdfFont pdfFont)
/*     */   {
/*  93 */     if (pdfFont == null)
/*  94 */       return -1;
/*     */     try
/*     */     {
/*  97 */       if (this.font != pdfFont.font) {
/*  98 */         return 1;
/*     */       }
/* 100 */       if (size() != pdfFont.size()) {
/* 101 */         return 2;
/*     */       }
/* 103 */       return 0;
/*     */     } catch (ClassCastException cce) {
/*     */     }
/* 106 */     return -2;
/*     */   }
/*     */ 
/*     */   float size()
/*     */   {
/* 117 */     return this.size;
/*     */   }
/*     */ 
/*     */   float width()
/*     */   {
/* 127 */     return width(32);
/*     */   }
/*     */ 
/*     */   float width(int character)
/*     */   {
/* 138 */     return this.font.getWidthPoint(character, this.size) * this.hScale;
/*     */   }
/*     */ 
/*     */   float width(String s) {
/* 142 */     return this.font.getWidthPoint(s, this.size) * this.hScale;
/*     */   }
/*     */ 
/*     */   BaseFont getFont() {
/* 146 */     return this.font;
/*     */   }
/*     */ 
/*     */   static PdfFont getDefaultFont() {
/*     */     try {
/* 151 */       BaseFont bf = BaseFont.createFont("Helvetica", "Cp1252", false);
/* 152 */       return new PdfFont(bf, 12.0F);
/*     */     }
/*     */     catch (Exception ee) {
/* 155 */       throw new ExceptionConverter(ee);
/*     */     }
/*     */   }
/*     */ 
/* 159 */   void setHorizontalScaling(float hScale) { this.hScale = hScale; }
/*     */ 
/*     */ 
/*     */   float getHorizontalScaling()
/*     */   {
/* 166 */     return this.hScale;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfFont
 * JD-Core Version:    0.6.2
 */