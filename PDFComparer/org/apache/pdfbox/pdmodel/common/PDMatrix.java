/*     */ package org.apache.pdfbox.pdmodel.common;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ 
/*     */ public class PDMatrix
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSArray matrix;
/*  37 */   private int numberOfRowElements = 3;
/*     */ 
/*     */   public PDMatrix()
/*     */   {
/*  44 */     this.matrix = new COSArray();
/*  45 */     this.matrix.add(new COSFloat(1.0F));
/*  46 */     this.matrix.add(new COSFloat(0.0F));
/*  47 */     this.matrix.add(new COSFloat(0.0F));
/*  48 */     this.matrix.add(new COSFloat(0.0F));
/*  49 */     this.matrix.add(new COSFloat(1.0F));
/*  50 */     this.matrix.add(new COSFloat(0.0F));
/*  51 */     this.matrix.add(new COSFloat(0.0F));
/*  52 */     this.matrix.add(new COSFloat(0.0F));
/*  53 */     this.matrix.add(new COSFloat(1.0F));
/*     */   }
/*     */ 
/*     */   public PDMatrix(COSArray array)
/*     */   {
/*  63 */     if (array.size() == 6)
/*     */     {
/*  65 */       this.numberOfRowElements = 2;
/*     */     }
/*  67 */     this.matrix = array;
/*     */   }
/*     */ 
/*     */   public COSArray getCOSArray()
/*     */   {
/*  77 */     return this.matrix;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  87 */     return this.matrix;
/*     */   }
/*     */ 
/*     */   public float getValue(int row, int column)
/*     */   {
/* 101 */     return ((COSNumber)this.matrix.get(row * this.numberOfRowElements + column)).floatValue();
/*     */   }
/*     */ 
/*     */   public void setValue(int row, int column, float value)
/*     */   {
/* 113 */     this.matrix.set(row * this.numberOfRowElements + column, new COSFloat(value));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.PDMatrix
 * JD-Core Version:    0.6.2
 */