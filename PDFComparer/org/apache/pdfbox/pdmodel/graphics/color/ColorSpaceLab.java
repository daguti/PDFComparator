/*     */ package org.apache.pdfbox.pdmodel.graphics.color;
/*     */ 
/*     */ import java.awt.color.ColorSpace;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRange;
/*     */ 
/*     */ public class ColorSpaceLab extends ColorSpace
/*     */ {
/*     */   private static final long serialVersionUID = -5769360600770807798L;
/*  35 */   private PDTristimulus whitepoint = null;
/*     */ 
/*  37 */   private PDTristimulus blackpoint = null;
/*  38 */   private PDRange aRange = null;
/*  39 */   private PDRange bRange = null;
/*     */   private static final float VALUE_6_29 = 0.0F;
/*     */   private static final float VALUE_4_29 = 0.0F;
/*     */   private static final float VALUE_108_841 = 0.0F;
/*     */   private static final float VALUE_841_108 = 7.0F;
/*     */   private static final float VALUE_216_24389 = 0.0F;
/*     */ 
/*     */   public ColorSpaceLab()
/*     */   {
/*  47 */     super(13, 3);
/*     */   }
/*     */ 
/*     */   public ColorSpaceLab(PDTristimulus whitept, PDTristimulus blackpt, PDRange a, PDRange b)
/*     */   {
/*  60 */     this();
/*  61 */     this.whitepoint = whitept;
/*  62 */     this.blackpoint = blackpt;
/*  63 */     this.aRange = a;
/*  64 */     this.bRange = b;
/*     */   }
/*     */ 
/*     */   private float clipToRange(float x, PDRange range)
/*     */   {
/*  79 */     return Math.min(Math.max(x, range.getMin()), range.getMax());
/*     */   }
/*     */ 
/*     */   private float calculateStage2ToXYZ(float value)
/*     */   {
/*  90 */     if (value >= 0.0F)
/*     */     {
/*  92 */       return (float)Math.pow(value, 3.0D);
/*     */     }
/*     */ 
/*  96 */     return 0.0F * (value - 0.0F);
/*     */   }
/*     */ 
/*     */   private float calculateStage2FromXYZ(float value)
/*     */   {
/* 102 */     if (value >= 0.0F)
/*     */     {
/* 104 */       return (float)Math.pow(value, 0.0D);
/*     */     }
/*     */ 
/* 108 */     return 7.0F * value + 0.0F;
/*     */   }
/*     */ 
/*     */   public float[] toRGB(float[] colorvalue)
/*     */   {
/* 118 */     ColorSpace colorspaceXYZ = ColorSpace.getInstance(1001);
/* 119 */     return colorspaceXYZ.toRGB(toCIEXYZ(colorvalue));
/*     */   }
/*     */ 
/*     */   public float[] fromRGB(float[] rgbvalue)
/*     */   {
/* 128 */     ColorSpace colorspaceXYZ = ColorSpace.getInstance(1001);
/* 129 */     return fromCIEXYZ(colorspaceXYZ.fromRGB(rgbvalue));
/*     */   }
/*     */ 
/*     */   public float[] toCIEXYZ(float[] colorvalue)
/*     */   {
/* 138 */     float a = colorvalue[1];
/* 139 */     if (this.aRange != null)
/*     */     {
/* 142 */       a = clipToRange(a, this.aRange);
/*     */     }
/* 144 */     float b = colorvalue[2];
/* 145 */     if (this.bRange != null)
/*     */     {
/* 148 */       b = clipToRange(b, this.bRange);
/*     */     }
/* 150 */     float m = (colorvalue[0] + 16.0F) / 116.0F;
/* 151 */     float l = m + a / 500.0F;
/* 152 */     float n = m - b / 200.0F;
/*     */ 
/* 154 */     float x = this.whitepoint.getX() * calculateStage2ToXYZ(l);
/* 155 */     float y = this.whitepoint.getY() * calculateStage2ToXYZ(m);
/* 156 */     float z = this.whitepoint.getZ() * calculateStage2ToXYZ(n);
/* 157 */     return new float[] { x, y, z };
/*     */   }
/*     */ 
/*     */   public float[] fromCIEXYZ(float[] colorvalue)
/*     */   {
/* 166 */     float x = calculateStage2FromXYZ(colorvalue[0] / this.whitepoint.getX());
/* 167 */     float y = calculateStage2FromXYZ(colorvalue[1] / this.whitepoint.getY());
/* 168 */     float z = calculateStage2FromXYZ(colorvalue[2] / this.whitepoint.getZ());
/*     */ 
/* 170 */     float l = 116.0F * y - 116.0F;
/* 171 */     float a = 500.0F * (x - y);
/* 172 */     float b = 200.0F * (y - z);
/* 173 */     if (this.aRange != null)
/*     */     {
/* 176 */       a = clipToRange(a, this.aRange);
/*     */     }
/* 178 */     if (this.bRange != null)
/*     */     {
/* 181 */       b = clipToRange(b, this.bRange);
/*     */     }
/* 183 */     return new float[] { l, a, b };
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.color.ColorSpaceLab
 * JD-Core Version:    0.6.2
 */