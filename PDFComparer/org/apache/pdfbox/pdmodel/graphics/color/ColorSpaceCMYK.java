/*     */ package org.apache.pdfbox.pdmodel.graphics.color;
/*     */ 
/*     */ import java.awt.color.ColorSpace;
/*     */ 
/*     */ public class ColorSpaceCMYK extends ColorSpace
/*     */ {
/*     */   private static final long serialVersionUID = -6362864473145799405L;
/*     */ 
/*     */   public ColorSpaceCMYK()
/*     */   {
/*  39 */     super(9, 4);
/*     */   }
/*     */ 
/*     */   private float[] fromRGBtoCIEXYZ(float[] rgbvalue)
/*     */   {
/*  49 */     ColorSpace colorspaceRGB = ColorSpace.getInstance(1000);
/*  50 */     return colorspaceRGB.toCIEXYZ(rgbvalue);
/*     */   }
/*     */ 
/*     */   private float[] fromCIEXYZtoRGB(float[] xyzvalue)
/*     */   {
/*  60 */     ColorSpace colorspaceXYZ = ColorSpace.getInstance(1001);
/*  61 */     return colorspaceXYZ.toRGB(xyzvalue);
/*     */   }
/*     */ 
/*     */   public float[] fromCIEXYZ(float[] colorvalue)
/*     */   {
/*  69 */     if ((colorvalue != null) && (colorvalue.length == 3))
/*     */     {
/*  72 */       return fromRGB(fromCIEXYZtoRGB(colorvalue));
/*     */     }
/*  74 */     return null;
/*     */   }
/*     */ 
/*     */   public float[] fromRGB(float[] rgbvalue)
/*     */   {
/*  82 */     if ((rgbvalue != null) && (rgbvalue.length == 3))
/*     */     {
/*  85 */       float c = 1.0F - rgbvalue[0];
/*  86 */       float m = 1.0F - rgbvalue[1];
/*  87 */       float y = 1.0F - rgbvalue[2];
/*     */ 
/*  89 */       float varK = 1.0F;
/*  90 */       float[] cmyk = new float[4];
/*  91 */       if (c < varK)
/*     */       {
/*  93 */         varK = c;
/*     */       }
/*  95 */       if (m < varK)
/*     */       {
/*  97 */         varK = m;
/*     */       }
/*  99 */       if (y < varK)
/*     */       {
/* 101 */         varK = y;
/*     */       }
/* 103 */       if (varK == 1.0F)
/*     */       {
/*     */         float tmp88_87 = (cmyk[2] = 0.0F); cmyk[1] = tmp88_87; cmyk[0] = tmp88_87;
/*     */       }
/*     */       else
/*     */       {
/* 109 */         cmyk[0] = ((c - varK) / (1.0F - varK));
/* 110 */         cmyk[1] = ((m - varK) / (1.0F - varK));
/* 111 */         cmyk[2] = ((y - varK) / (1.0F - varK));
/*     */       }
/* 113 */       cmyk[3] = varK;
/* 114 */       return cmyk;
/*     */     }
/* 116 */     return null;
/*     */   }
/*     */ 
/*     */   public float[] toCIEXYZ(float[] colorvalue)
/*     */   {
/* 124 */     if ((colorvalue != null) && (colorvalue.length == 4))
/*     */     {
/* 127 */       return fromRGBtoCIEXYZ(toRGB(colorvalue));
/*     */     }
/* 129 */     return null;
/*     */   }
/*     */ 
/*     */   public float[] toRGB(float[] colorvalue)
/*     */   {
/* 137 */     if ((colorvalue != null) && (colorvalue.length == 4))
/*     */     {
/* 140 */       float k = colorvalue[3];
/* 141 */       float c = colorvalue[0] * (1.0F - k) + k;
/* 142 */       float m = colorvalue[1] * (1.0F - k) + k;
/* 143 */       float y = colorvalue[2] * (1.0F - k) + k;
/*     */ 
/* 145 */       return new float[] { 1.0F - c, 1.0F - m, 1.0F - y };
/*     */     }
/* 147 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.color.ColorSpaceCMYK
 * JD-Core Version:    0.6.2
 */