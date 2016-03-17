/*     */ package org.apache.pdfbox.pdmodel.graphics.color;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ 
/*     */ public class PDGamma
/*     */   implements COSObjectable
/*     */ {
/*  35 */   private COSArray values = null;
/*     */ 
/*     */   public PDGamma()
/*     */   {
/*  42 */     this.values = new COSArray();
/*  43 */     this.values.add(new COSFloat(0.0F));
/*  44 */     this.values.add(new COSFloat(0.0F));
/*  45 */     this.values.add(new COSFloat(0.0F));
/*     */   }
/*     */ 
/*     */   public PDGamma(COSArray array)
/*     */   {
/*  55 */     this.values = array;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  65 */     return this.values;
/*     */   }
/*     */ 
/*     */   public COSArray getCOSArray()
/*     */   {
/*  75 */     return this.values;
/*     */   }
/*     */ 
/*     */   public float getR()
/*     */   {
/*  85 */     return ((COSNumber)this.values.get(0)).floatValue();
/*     */   }
/*     */ 
/*     */   public void setR(float r)
/*     */   {
/*  95 */     this.values.set(0, new COSFloat(r));
/*     */   }
/*     */ 
/*     */   public float getG()
/*     */   {
/* 105 */     return ((COSNumber)this.values.get(1)).floatValue();
/*     */   }
/*     */ 
/*     */   public void setG(float g)
/*     */   {
/* 115 */     this.values.set(1, new COSFloat(g));
/*     */   }
/*     */ 
/*     */   public float getB()
/*     */   {
/* 125 */     return ((COSNumber)this.values.get(2)).floatValue();
/*     */   }
/*     */ 
/*     */   public void setB(float b)
/*     */   {
/* 135 */     this.values.set(2, new COSFloat(b));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.color.PDGamma
 * JD-Core Version:    0.6.2
 */