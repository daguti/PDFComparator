/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ public class PdfIndirectReference extends PdfObject
/*     */ {
/*     */   protected int number;
/*  69 */   protected int generation = 0;
/*     */ 
/*     */   protected PdfIndirectReference()
/*     */   {
/*  74 */     super(0);
/*     */   }
/*     */ 
/*     */   PdfIndirectReference(int type, int number, int generation)
/*     */   {
/*  86 */     super(0, number + " " + generation + " R");
/*  87 */     this.number = number;
/*  88 */     this.generation = generation;
/*     */   }
/*     */ 
/*     */   protected PdfIndirectReference(int type, int number)
/*     */   {
/*  99 */     this(type, number, 0);
/*     */   }
/*     */ 
/*     */   public int getNumber()
/*     */   {
/* 111 */     return this.number;
/*     */   }
/*     */ 
/*     */   public int getGeneration()
/*     */   {
/* 121 */     return this.generation;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 125 */     return this.number + " " + this.generation + " R";
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfIndirectReference
 * JD-Core Version:    0.6.2
 */