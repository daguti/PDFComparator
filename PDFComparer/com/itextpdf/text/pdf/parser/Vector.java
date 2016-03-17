/*     */ package com.itextpdf.text.pdf.parser;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ public class Vector
/*     */ {
/*     */   public static final int I1 = 0;
/*     */   public static final int I2 = 1;
/*     */   public static final int I3 = 2;
/*  67 */   private final float[] vals = { 0.0F, 0.0F, 0.0F };
/*     */ 
/*     */   public Vector(float x, float y, float z)
/*     */   {
/*  78 */     this.vals[0] = x;
/*  79 */     this.vals[1] = y;
/*  80 */     this.vals[2] = z;
/*     */   }
/*     */ 
/*     */   public float get(int index)
/*     */   {
/*  89 */     return this.vals[index];
/*     */   }
/*     */ 
/*     */   public Vector cross(Matrix by)
/*     */   {
/*  99 */     float x = this.vals[0] * by.get(0) + this.vals[1] * by.get(3) + this.vals[2] * by.get(6);
/* 100 */     float y = this.vals[0] * by.get(1) + this.vals[1] * by.get(4) + this.vals[2] * by.get(7);
/* 101 */     float z = this.vals[0] * by.get(2) + this.vals[1] * by.get(5) + this.vals[2] * by.get(8);
/*     */ 
/* 103 */     return new Vector(x, y, z);
/*     */   }
/*     */ 
/*     */   public Vector subtract(Vector v)
/*     */   {
/* 112 */     float x = this.vals[0] - v.vals[0];
/* 113 */     float y = this.vals[1] - v.vals[1];
/* 114 */     float z = this.vals[2] - v.vals[2];
/*     */ 
/* 116 */     return new Vector(x, y, z);
/*     */   }
/*     */ 
/*     */   public Vector cross(Vector with)
/*     */   {
/* 125 */     float x = this.vals[1] * with.vals[2] - this.vals[2] * with.vals[1];
/* 126 */     float y = this.vals[2] * with.vals[0] - this.vals[0] * with.vals[2];
/* 127 */     float z = this.vals[0] * with.vals[1] - this.vals[1] * with.vals[0];
/*     */ 
/* 129 */     return new Vector(x, y, z);
/*     */   }
/*     */ 
/*     */   public Vector normalize()
/*     */   {
/* 138 */     float l = length();
/* 139 */     float x = this.vals[0] / l;
/* 140 */     float y = this.vals[1] / l;
/* 141 */     float z = this.vals[2] / l;
/* 142 */     return new Vector(x, y, z);
/*     */   }
/*     */ 
/*     */   public Vector multiply(float by)
/*     */   {
/* 152 */     float x = this.vals[0] * by;
/* 153 */     float y = this.vals[1] * by;
/* 154 */     float z = this.vals[2] * by;
/* 155 */     return new Vector(x, y, z);
/*     */   }
/*     */ 
/*     */   public float dot(Vector with)
/*     */   {
/* 164 */     return this.vals[0] * with.vals[0] + this.vals[1] * with.vals[1] + this.vals[2] * with.vals[2];
/*     */   }
/*     */ 
/*     */   public float length()
/*     */   {
/* 181 */     return (float)Math.sqrt(lengthSquared());
/*     */   }
/*     */ 
/*     */   public float lengthSquared()
/*     */   {
/* 195 */     return this.vals[0] * this.vals[0] + this.vals[1] * this.vals[1] + this.vals[2] * this.vals[2];
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 203 */     return this.vals[0] + "," + this.vals[1] + "," + this.vals[2];
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 211 */     int prime = 31;
/* 212 */     int result = 1;
/* 213 */     result = 31 * result + Arrays.hashCode(this.vals);
/* 214 */     return result;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 222 */     if (this == obj) {
/* 223 */       return true;
/*     */     }
/* 225 */     if (obj == null) {
/* 226 */       return false;
/*     */     }
/* 228 */     if (getClass() != obj.getClass()) {
/* 229 */       return false;
/*     */     }
/* 231 */     Vector other = (Vector)obj;
/* 232 */     if (!Arrays.equals(this.vals, other.vals)) {
/* 233 */       return false;
/*     */     }
/* 235 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.Vector
 * JD-Core Version:    0.6.2
 */