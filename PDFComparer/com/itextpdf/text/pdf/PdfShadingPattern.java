/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.io.IOException;
/*     */ 
/*     */ public class PdfShadingPattern extends PdfDictionary
/*     */ {
/*     */   protected PdfShading shading;
/*     */   protected PdfWriter writer;
/*  59 */   protected float[] matrix = { 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F };
/*     */   protected PdfName patternName;
/*     */   protected PdfIndirectReference patternReference;
/*     */ 
/*     */   public PdfShadingPattern(PdfShading shading)
/*     */   {
/*  67 */     this.writer = shading.getWriter();
/*  68 */     put(PdfName.PATTERNTYPE, new PdfNumber(2));
/*  69 */     this.shading = shading;
/*     */   }
/*     */ 
/*     */   PdfName getPatternName() {
/*  73 */     return this.patternName;
/*     */   }
/*     */ 
/*     */   PdfName getShadingName() {
/*  77 */     return this.shading.getShadingName();
/*     */   }
/*     */ 
/*     */   PdfIndirectReference getPatternReference() {
/*  81 */     if (this.patternReference == null)
/*  82 */       this.patternReference = this.writer.getPdfIndirectReference();
/*  83 */     return this.patternReference;
/*     */   }
/*     */ 
/*     */   PdfIndirectReference getShadingReference() {
/*  87 */     return this.shading.getShadingReference();
/*     */   }
/*     */ 
/*     */   void setName(int number) {
/*  91 */     this.patternName = new PdfName("P" + number);
/*     */   }
/*     */ 
/*     */   public void addToBody() throws IOException {
/*  95 */     put(PdfName.SHADING, getShadingReference());
/*  96 */     put(PdfName.MATRIX, new PdfArray(this.matrix));
/*  97 */     this.writer.addToBody(this, getPatternReference());
/*     */   }
/*     */ 
/*     */   public void setMatrix(float[] matrix) {
/* 101 */     if (matrix.length != 6)
/* 102 */       throw new RuntimeException(MessageLocalization.getComposedMessage("the.matrix.size.must.be.6", new Object[0]));
/* 103 */     this.matrix = matrix;
/*     */   }
/*     */ 
/*     */   public float[] getMatrix() {
/* 107 */     return this.matrix;
/*     */   }
/*     */ 
/*     */   public PdfShading getShading() {
/* 111 */     return this.shading;
/*     */   }
/*     */ 
/*     */   ColorDetails getColorDetails() {
/* 115 */     return this.shading.getColorDetails();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfShadingPattern
 * JD-Core Version:    0.6.2
 */