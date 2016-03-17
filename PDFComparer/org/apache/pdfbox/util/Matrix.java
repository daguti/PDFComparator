/*     */ package org.apache.pdfbox.util;
/*     */ 
/*     */ import java.awt.geom.AffineTransform;
/*     */ 
/*     */ public class Matrix
/*     */   implements Cloneable
/*     */ {
/*  29 */   static final float[] DEFAULT_SINGLE = { 1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F };
/*     */   private float[] single;
/*     */ 
/*     */   public Matrix()
/*     */   {
/*  43 */     this.single = new float[DEFAULT_SINGLE.length];
/*  44 */     reset();
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/*  53 */     System.arraycopy(DEFAULT_SINGLE, 0, this.single, 0, DEFAULT_SINGLE.length);
/*     */   }
/*     */ 
/*     */   public AffineTransform createAffineTransform()
/*     */   {
/*  63 */     AffineTransform retval = new AffineTransform(this.single[0], this.single[1], this.single[3], this.single[4], this.single[6], this.single[7]);
/*     */ 
/*  67 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setFromAffineTransform(AffineTransform af)
/*     */   {
/*  77 */     this.single[0] = ((float)af.getScaleX());
/*  78 */     this.single[1] = ((float)af.getShearY());
/*  79 */     this.single[3] = ((float)af.getShearX());
/*  80 */     this.single[4] = ((float)af.getScaleY());
/*  81 */     this.single[6] = ((float)af.getTranslateX());
/*  82 */     this.single[7] = ((float)af.getTranslateY());
/*     */   }
/*     */ 
/*     */   public float getValue(int row, int column)
/*     */   {
/*  95 */     return this.single[(row * 3 + column)];
/*     */   }
/*     */ 
/*     */   public void setValue(int row, int column, float value)
/*     */   {
/* 107 */     this.single[(row * 3 + column)] = value;
/*     */   }
/*     */ 
/*     */   public float[][] getValues()
/*     */   {
/* 117 */     float[][] retval = new float[3][3];
/* 118 */     retval[0][0] = this.single[0];
/* 119 */     retval[0][1] = this.single[1];
/* 120 */     retval[0][2] = this.single[2];
/* 121 */     retval[1][0] = this.single[3];
/* 122 */     retval[1][1] = this.single[4];
/* 123 */     retval[1][2] = this.single[5];
/* 124 */     retval[2][0] = this.single[6];
/* 125 */     retval[2][1] = this.single[7];
/* 126 */     retval[2][2] = this.single[8];
/* 127 */     return retval;
/*     */   }
/*     */ 
/*     */   public double[][] getValuesAsDouble()
/*     */   {
/* 137 */     double[][] retval = new double[3][3];
/* 138 */     retval[0][0] = this.single[0];
/* 139 */     retval[0][1] = this.single[1];
/* 140 */     retval[0][2] = this.single[2];
/* 141 */     retval[1][0] = this.single[3];
/* 142 */     retval[1][1] = this.single[4];
/* 143 */     retval[1][2] = this.single[5];
/* 144 */     retval[2][0] = this.single[6];
/* 145 */     retval[2][1] = this.single[7];
/* 146 */     retval[2][2] = this.single[8];
/* 147 */     return retval;
/*     */   }
/*     */ 
/*     */   public Matrix multiply(Matrix b)
/*     */   {
/* 159 */     return multiply(b, new Matrix());
/*     */   }
/*     */ 
/*     */   public Matrix multiply(Matrix other, Matrix result)
/*     */   {
/* 177 */     if (result == null)
/*     */     {
/* 179 */       result = new Matrix();
/*     */     }
/*     */ 
/* 182 */     if ((other != null) && (other.single != null))
/*     */     {
/* 185 */       float[] thisOperand = this.single;
/* 186 */       float[] otherOperand = other.single;
/*     */ 
/* 196 */       if (this == result)
/*     */       {
/* 198 */         float[] thisOrigVals = new float[this.single.length];
/* 199 */         System.arraycopy(this.single, 0, thisOrigVals, 0, this.single.length);
/*     */ 
/* 201 */         thisOperand = thisOrigVals;
/*     */       }
/* 203 */       if (other == result)
/*     */       {
/* 205 */         float[] otherOrigVals = new float[other.single.length];
/* 206 */         System.arraycopy(other.single, 0, otherOrigVals, 0, other.single.length);
/*     */ 
/* 208 */         otherOperand = otherOrigVals;
/*     */       }
/*     */ 
/* 211 */       result.single[0] = (thisOperand[0] * otherOperand[0] + thisOperand[1] * otherOperand[3] + thisOperand[2] * otherOperand[6]);
/*     */ 
/* 214 */       result.single[1] = (thisOperand[0] * otherOperand[1] + thisOperand[1] * otherOperand[4] + thisOperand[2] * otherOperand[7]);
/*     */ 
/* 217 */       result.single[2] = (thisOperand[0] * otherOperand[2] + thisOperand[1] * otherOperand[5] + thisOperand[2] * otherOperand[8]);
/*     */ 
/* 220 */       result.single[3] = (thisOperand[3] * otherOperand[0] + thisOperand[4] * otherOperand[3] + thisOperand[5] * otherOperand[6]);
/*     */ 
/* 223 */       result.single[4] = (thisOperand[3] * otherOperand[1] + thisOperand[4] * otherOperand[4] + thisOperand[5] * otherOperand[7]);
/*     */ 
/* 226 */       result.single[5] = (thisOperand[3] * otherOperand[2] + thisOperand[4] * otherOperand[5] + thisOperand[5] * otherOperand[8]);
/*     */ 
/* 229 */       result.single[6] = (thisOperand[6] * otherOperand[0] + thisOperand[7] * otherOperand[3] + thisOperand[8] * otherOperand[6]);
/*     */ 
/* 232 */       result.single[7] = (thisOperand[6] * otherOperand[1] + thisOperand[7] * otherOperand[4] + thisOperand[8] * otherOperand[7]);
/*     */ 
/* 235 */       result.single[8] = (thisOperand[6] * otherOperand[2] + thisOperand[7] * otherOperand[5] + thisOperand[8] * otherOperand[8]);
/*     */     }
/*     */ 
/* 240 */     return result;
/*     */   }
/*     */ 
/*     */   public Matrix extractScaling()
/*     */   {
/* 250 */     Matrix retval = new Matrix();
/*     */ 
/* 252 */     retval.single[0] = this.single[0];
/* 253 */     retval.single[4] = this.single[4];
/*     */ 
/* 255 */     return retval;
/*     */   }
/*     */ 
/*     */   public static Matrix getScaleInstance(float x, float y)
/*     */   {
/* 267 */     Matrix retval = new Matrix();
/*     */ 
/* 269 */     retval.single[0] = x;
/* 270 */     retval.single[4] = y;
/*     */ 
/* 272 */     return retval;
/*     */   }
/*     */ 
/*     */   public Matrix extractTranslating()
/*     */   {
/* 282 */     Matrix retval = new Matrix();
/*     */ 
/* 284 */     retval.single[6] = this.single[6];
/* 285 */     retval.single[7] = this.single[7];
/*     */ 
/* 287 */     return retval;
/*     */   }
/*     */ 
/*     */   public static Matrix getTranslatingInstance(float x, float y)
/*     */   {
/* 299 */     Matrix retval = new Matrix();
/*     */ 
/* 301 */     retval.single[6] = x;
/* 302 */     retval.single[7] = y;
/*     */ 
/* 304 */     return retval;
/*     */   }
/*     */ 
/*     */   public Object clone()
/*     */   {
/* 313 */     Matrix clone = new Matrix();
/* 314 */     System.arraycopy(this.single, 0, clone.single, 0, 9);
/* 315 */     return clone;
/*     */   }
/*     */ 
/*     */   public Matrix copy()
/*     */   {
/* 325 */     return (Matrix)clone();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 335 */     StringBuffer result = new StringBuffer("");
/* 336 */     result.append("[[");
/* 337 */     result.append(this.single[0] + ",");
/* 338 */     result.append(this.single[1] + ",");
/* 339 */     result.append(this.single[2] + "][");
/* 340 */     result.append(this.single[3] + ",");
/* 341 */     result.append(this.single[4] + ",");
/* 342 */     result.append(this.single[5] + "][");
/* 343 */     result.append(this.single[6] + ",");
/* 344 */     result.append(this.single[7] + ",");
/* 345 */     result.append(this.single[8] + "]]");
/*     */ 
/* 347 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public float getXScale()
/*     */   {
/* 356 */     float xScale = this.single[0];
/*     */ 
/* 375 */     if ((this.single[1] != 0.0F) || (this.single[3] != 0.0F))
/*     */     {
/* 377 */       xScale = (float)Math.sqrt(Math.pow(this.single[0], 2.0D) + Math.pow(this.single[1], 2.0D));
/*     */     }
/*     */ 
/* 380 */     return xScale;
/*     */   }
/*     */ 
/*     */   public float getYScale()
/*     */   {
/* 389 */     float yScale = this.single[4];
/* 390 */     if ((this.single[1] != 0.0F) || (this.single[3] != 0.0F))
/*     */     {
/* 392 */       yScale = (float)Math.sqrt(Math.pow(this.single[3], 2.0D) + Math.pow(this.single[4], 2.0D));
/*     */     }
/*     */ 
/* 395 */     return yScale;
/*     */   }
/*     */ 
/*     */   public float getXPosition()
/*     */   {
/* 404 */     return this.single[6];
/*     */   }
/*     */ 
/*     */   public float getYPosition()
/*     */   {
/* 413 */     return this.single[7];
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.Matrix
 * JD-Core Version:    0.6.2
 */