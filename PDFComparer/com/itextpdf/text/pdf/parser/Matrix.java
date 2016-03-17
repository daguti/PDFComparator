/*     */ package com.itextpdf.text.pdf.parser;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ public class Matrix
/*     */ {
/*     */   public static final int I11 = 0;
/*     */   public static final int I12 = 1;
/*     */   public static final int I13 = 2;
/*     */   public static final int I21 = 3;
/*     */   public static final int I22 = 4;
/*     */   public static final int I23 = 5;
/*     */   public static final int I31 = 6;
/*     */   public static final int I32 = 7;
/*     */   public static final int I33 = 8;
/*  81 */   private final float[] vals = { 1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F };
/*     */ 
/*     */   public Matrix()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Matrix(float tx, float ty)
/*     */   {
/*  99 */     this.vals[6] = tx;
/* 100 */     this.vals[7] = ty;
/*     */   }
/*     */ 
/*     */   public Matrix(float a, float b, float c, float d, float e, float f)
/*     */   {
/* 113 */     this.vals[0] = a;
/* 114 */     this.vals[1] = b;
/* 115 */     this.vals[2] = 0.0F;
/* 116 */     this.vals[3] = c;
/* 117 */     this.vals[4] = d;
/* 118 */     this.vals[5] = 0.0F;
/* 119 */     this.vals[6] = e;
/* 120 */     this.vals[7] = f;
/* 121 */     this.vals[8] = 1.0F;
/*     */   }
/*     */ 
/*     */   public float get(int index)
/*     */   {
/* 136 */     return this.vals[index];
/*     */   }
/*     */ 
/*     */   public Matrix multiply(Matrix by)
/*     */   {
/* 146 */     Matrix rslt = new Matrix();
/*     */ 
/* 148 */     float[] a = this.vals;
/* 149 */     float[] b = by.vals;
/* 150 */     float[] c = rslt.vals;
/*     */ 
/* 152 */     c[0] = (a[0] * b[0] + a[1] * b[3] + a[2] * b[6]);
/* 153 */     c[1] = (a[0] * b[1] + a[1] * b[4] + a[2] * b[7]);
/* 154 */     c[2] = (a[0] * b[2] + a[1] * b[5] + a[2] * b[8]);
/* 155 */     c[3] = (a[3] * b[0] + a[4] * b[3] + a[5] * b[6]);
/* 156 */     c[4] = (a[3] * b[1] + a[4] * b[4] + a[5] * b[7]);
/* 157 */     c[5] = (a[3] * b[2] + a[4] * b[5] + a[5] * b[8]);
/* 158 */     c[6] = (a[6] * b[0] + a[7] * b[3] + a[8] * b[6]);
/* 159 */     c[7] = (a[6] * b[1] + a[7] * b[4] + a[8] * b[7]);
/* 160 */     c[8] = (a[6] * b[2] + a[7] * b[5] + a[8] * b[8]);
/*     */ 
/* 162 */     return rslt;
/*     */   }
/*     */ 
/*     */   public Matrix subtract(Matrix arg)
/*     */   {
/* 171 */     Matrix rslt = new Matrix();
/*     */ 
/* 173 */     float[] a = this.vals;
/* 174 */     float[] b = arg.vals;
/* 175 */     float[] c = rslt.vals;
/*     */ 
/* 177 */     a[0] -= b[0];
/* 178 */     a[1] -= b[1];
/* 179 */     a[2] -= b[2];
/* 180 */     a[3] -= b[3];
/* 181 */     a[4] -= b[4];
/* 182 */     a[5] -= b[5];
/* 183 */     a[6] -= b[6];
/* 184 */     a[7] -= b[7];
/* 185 */     a[8] -= b[8];
/*     */ 
/* 187 */     return rslt;
/*     */   }
/*     */ 
/*     */   public float getDeterminant()
/*     */   {
/* 199 */     return this.vals[0] * this.vals[4] * this.vals[8] + this.vals[1] * this.vals[5] * this.vals[6] + this.vals[2] * this.vals[3] * this.vals[7] - this.vals[0] * this.vals[5] * this.vals[7] - this.vals[1] * this.vals[3] * this.vals[8] - this.vals[2] * this.vals[4] * this.vals[6];
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 214 */     if (!(obj instanceof Matrix)) {
/* 215 */       return false;
/*     */     }
/* 217 */     return Arrays.equals(this.vals, ((Matrix)obj).vals);
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 228 */     int result = 1;
/* 229 */     for (int i = 0; i < this.vals.length; i++) {
/* 230 */       result = 31 * result + Float.floatToIntBits(this.vals[i]);
/*     */     }
/* 232 */     return result;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 241 */     return this.vals[0] + "\t" + this.vals[1] + "\t" + this.vals[2] + "\n" + this.vals[3] + "\t" + this.vals[4] + "\t" + this.vals[2] + "\n" + this.vals[6] + "\t" + this.vals[7] + "\t" + this.vals[8];
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.Matrix
 * JD-Core Version:    0.6.2
 */