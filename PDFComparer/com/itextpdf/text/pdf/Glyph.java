/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ public class Glyph
/*     */ {
/*     */   public final int code;
/*     */   public final int width;
/*     */   public final String chars;
/*     */ 
/*     */   public Glyph(int code, int width, String chars)
/*     */   {
/*  69 */     this.code = code;
/*  70 */     this.width = width;
/*  71 */     this.chars = chars;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  76 */     int prime = 31;
/*  77 */     int result = 1;
/*  78 */     result = 31 * result + (this.chars == null ? 0 : this.chars.hashCode());
/*  79 */     result = 31 * result + this.code;
/*  80 */     result = 31 * result + this.width;
/*  81 */     return result;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  86 */     if (this == obj)
/*  87 */       return true;
/*  88 */     if (obj == null)
/*  89 */       return false;
/*  90 */     if (getClass() != obj.getClass())
/*  91 */       return false;
/*  92 */     Glyph other = (Glyph)obj;
/*  93 */     if (this.chars == null) {
/*  94 */       if (other.chars != null)
/*  95 */         return false;
/*  96 */     } else if (!this.chars.equals(other.chars))
/*  97 */       return false;
/*  98 */     if (this.code != other.code)
/*  99 */       return false;
/* 100 */     if (this.width != other.width)
/* 101 */       return false;
/* 102 */     return true;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 109 */     return Glyph.class.getSimpleName() + " [id=" + this.code + ", width=" + this.width + ", chars=" + this.chars + "]";
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.Glyph
 * JD-Core Version:    0.6.2
 */