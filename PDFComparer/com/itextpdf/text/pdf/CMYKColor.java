/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ public class CMYKColor extends ExtendedColor
/*     */ {
/*     */   private static final long serialVersionUID = 5940378778276468452L;
/*     */   float cyan;
/*     */   float magenta;
/*     */   float yellow;
/*     */   float black;
/*     */ 
/*     */   public CMYKColor(int intCyan, int intMagenta, int intYellow, int intBlack)
/*     */   {
/*  67 */     this(intCyan / 255.0F, intMagenta / 255.0F, intYellow / 255.0F, intBlack / 255.0F);
/*     */   }
/*     */ 
/*     */   public CMYKColor(float floatCyan, float floatMagenta, float floatYellow, float floatBlack)
/*     */   {
/*  78 */     super(2, 1.0F - floatCyan - floatBlack, 1.0F - floatMagenta - floatBlack, 1.0F - floatYellow - floatBlack);
/*  79 */     this.cyan = normalize(floatCyan);
/*  80 */     this.magenta = normalize(floatMagenta);
/*  81 */     this.yellow = normalize(floatYellow);
/*  82 */     this.black = normalize(floatBlack);
/*     */   }
/*     */ 
/*     */   public float getCyan()
/*     */   {
/*  89 */     return this.cyan;
/*     */   }
/*     */ 
/*     */   public float getMagenta()
/*     */   {
/*  96 */     return this.magenta;
/*     */   }
/*     */ 
/*     */   public float getYellow()
/*     */   {
/* 103 */     return this.yellow;
/*     */   }
/*     */ 
/*     */   public float getBlack()
/*     */   {
/* 110 */     return this.black;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj) {
/* 114 */     if (!(obj instanceof CMYKColor))
/* 115 */       return false;
/* 116 */     CMYKColor c2 = (CMYKColor)obj;
/* 117 */     return (this.cyan == c2.cyan) && (this.magenta == c2.magenta) && (this.yellow == c2.yellow) && (this.black == c2.black);
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/* 121 */     return Float.floatToIntBits(this.cyan) ^ Float.floatToIntBits(this.magenta) ^ Float.floatToIntBits(this.yellow) ^ Float.floatToIntBits(this.black);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.CMYKColor
 * JD-Core Version:    0.6.2
 */