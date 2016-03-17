/*     */ package org.apache.fontbox.afm;
/*     */ 
/*     */ public class TrackKern
/*     */ {
/*     */   private int degree;
/*     */   private float minPointSize;
/*     */   private float minKern;
/*     */   private float maxPointSize;
/*     */   private float maxKern;
/*     */ 
/*     */   public int getDegree()
/*     */   {
/*  38 */     return this.degree;
/*     */   }
/*     */ 
/*     */   public void setDegree(int degreeValue)
/*     */   {
/*  46 */     this.degree = degreeValue;
/*     */   }
/*     */ 
/*     */   public float getMaxKern()
/*     */   {
/*  54 */     return this.maxKern;
/*     */   }
/*     */ 
/*     */   public void setMaxKern(float maxKernValue)
/*     */   {
/*  62 */     this.maxKern = maxKernValue;
/*     */   }
/*     */ 
/*     */   public float getMaxPointSize()
/*     */   {
/*  70 */     return this.maxPointSize;
/*     */   }
/*     */ 
/*     */   public void setMaxPointSize(float maxPointSizeValue)
/*     */   {
/*  78 */     this.maxPointSize = maxPointSizeValue;
/*     */   }
/*     */ 
/*     */   public float getMinKern()
/*     */   {
/*  86 */     return this.minKern;
/*     */   }
/*     */ 
/*     */   public void setMinKern(float minKernValue)
/*     */   {
/*  94 */     this.minKern = minKernValue;
/*     */   }
/*     */ 
/*     */   public float getMinPointSize()
/*     */   {
/* 102 */     return this.minPointSize;
/*     */   }
/*     */ 
/*     */   public void setMinPointSize(float minPointSizeValue)
/*     */   {
/* 110 */     this.minPointSize = minPointSizeValue;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.afm.TrackKern
 * JD-Core Version:    0.6.2
 */