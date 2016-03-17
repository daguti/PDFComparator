/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.BaseColor;
/*     */ 
/*     */ public abstract class ExtendedColor extends BaseColor
/*     */ {
/*     */   private static final long serialVersionUID = 2722660170712380080L;
/*     */   public static final int TYPE_RGB = 0;
/*     */   public static final int TYPE_GRAY = 1;
/*     */   public static final int TYPE_CMYK = 2;
/*     */   public static final int TYPE_SEPARATION = 3;
/*     */   public static final int TYPE_PATTERN = 4;
/*     */   public static final int TYPE_SHADING = 5;
/*     */   public static final int TYPE_DEVICEN = 6;
/*     */   public static final int TYPE_LAB = 7;
/*     */   protected int type;
/*     */ 
/*     */   public ExtendedColor(int type)
/*     */   {
/*  80 */     super(0, 0, 0);
/*  81 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public ExtendedColor(int type, float red, float green, float blue)
/*     */   {
/*  92 */     super(normalize(red), normalize(green), normalize(blue));
/*  93 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public ExtendedColor(int type, int red, int green, int blue, int alpha)
/*     */   {
/* 104 */     super(normalize(red / 255.0F), normalize(green / 255.0F), normalize(blue / 255.0F), normalize(alpha / 255.0F));
/* 105 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public int getType()
/*     */   {
/* 113 */     return this.type;
/*     */   }
/*     */ 
/*     */   public static int getType(BaseColor color)
/*     */   {
/* 122 */     if ((color instanceof ExtendedColor))
/* 123 */       return ((ExtendedColor)color).getType();
/* 124 */     return 0;
/*     */   }
/*     */ 
/*     */   static final float normalize(float value) {
/* 128 */     if (value < 0.0F)
/* 129 */       return 0.0F;
/* 130 */     if (value > 1.0F)
/* 131 */       return 1.0F;
/* 132 */     return value;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.ExtendedColor
 * JD-Core Version:    0.6.2
 */