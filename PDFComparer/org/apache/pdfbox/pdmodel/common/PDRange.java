/*     */ package org.apache.pdfbox.pdmodel.common;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ 
/*     */ public class PDRange
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSArray rangeArray;
/*     */   private int startingIndex;
/*     */ 
/*     */   public PDRange()
/*     */   {
/*  40 */     this.rangeArray = new COSArray();
/*  41 */     this.rangeArray.add(new COSFloat(0.0F));
/*  42 */     this.rangeArray.add(new COSFloat(1.0F));
/*  43 */     this.startingIndex = 0;
/*     */   }
/*     */ 
/*     */   public PDRange(COSArray range)
/*     */   {
/*  53 */     this.rangeArray = range;
/*     */   }
/*     */ 
/*     */   public PDRange(COSArray range, int index)
/*     */   {
/*  67 */     this.rangeArray = range;
/*  68 */     this.startingIndex = index;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  78 */     return this.rangeArray;
/*     */   }
/*     */ 
/*     */   public COSArray getCOSArray()
/*     */   {
/*  88 */     return this.rangeArray;
/*     */   }
/*     */ 
/*     */   public float getMin()
/*     */   {
/*  98 */     COSNumber min = (COSNumber)this.rangeArray.getObject(this.startingIndex * 2);
/*  99 */     return min.floatValue();
/*     */   }
/*     */ 
/*     */   public void setMin(float min)
/*     */   {
/* 109 */     this.rangeArray.set(this.startingIndex * 2, new COSFloat(min));
/*     */   }
/*     */ 
/*     */   public float getMax()
/*     */   {
/* 119 */     COSNumber max = (COSNumber)this.rangeArray.getObject(this.startingIndex * 2 + 1);
/* 120 */     return max.floatValue();
/*     */   }
/*     */ 
/*     */   public void setMax(float max)
/*     */   {
/* 130 */     this.rangeArray.set(this.startingIndex * 2 + 1, new COSFloat(max));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.PDRange
 * JD-Core Version:    0.6.2
 */