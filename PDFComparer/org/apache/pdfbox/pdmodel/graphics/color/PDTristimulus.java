/*     */ package org.apache.pdfbox.pdmodel.graphics.color;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ 
/*     */ public class PDTristimulus
/*     */   implements COSObjectable
/*     */ {
/*  35 */   private COSArray values = null;
/*     */ 
/*     */   public PDTristimulus()
/*     */   {
/*  42 */     this.values = new COSArray();
/*  43 */     this.values.add(new COSFloat(0.0F));
/*  44 */     this.values.add(new COSFloat(0.0F));
/*  45 */     this.values.add(new COSFloat(0.0F));
/*     */   }
/*     */ 
/*     */   public PDTristimulus(COSArray array)
/*     */   {
/*  55 */     this.values = array;
/*     */   }
/*     */ 
/*     */   public PDTristimulus(float[] array)
/*     */   {
/*  65 */     this.values = new COSArray();
/*  66 */     for (int i = 0; (i < array.length) && (i < 3); i++)
/*     */     {
/*  68 */       this.values.add(new COSFloat(array[i]));
/*     */     }
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  79 */     return this.values;
/*     */   }
/*     */ 
/*     */   public float getX()
/*     */   {
/*  89 */     return ((COSNumber)this.values.get(0)).floatValue();
/*     */   }
/*     */ 
/*     */   public void setX(float x)
/*     */   {
/*  99 */     this.values.set(0, new COSFloat(x));
/*     */   }
/*     */ 
/*     */   public float getY()
/*     */   {
/* 109 */     return ((COSNumber)this.values.get(1)).floatValue();
/*     */   }
/*     */ 
/*     */   public void setY(float y)
/*     */   {
/* 119 */     this.values.set(1, new COSFloat(y));
/*     */   }
/*     */ 
/*     */   public float getZ()
/*     */   {
/* 129 */     return ((COSNumber)this.values.get(2)).floatValue();
/*     */   }
/*     */ 
/*     */   public void setZ(float z)
/*     */   {
/* 139 */     this.values.set(2, new COSFloat(z));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.color.PDTristimulus
 * JD-Core Version:    0.6.2
 */