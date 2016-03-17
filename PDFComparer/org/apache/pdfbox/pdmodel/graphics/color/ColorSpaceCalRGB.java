/*     */ package org.apache.pdfbox.pdmodel.graphics.color;
/*     */ 
/*     */ import java.awt.color.ColorSpace;
/*     */ import org.apache.pdfbox.pdmodel.common.PDMatrix;
/*     */ 
/*     */ public class ColorSpaceCalRGB extends ColorSpace
/*     */ {
/*  36 */   private PDGamma gamma = null;
/*  37 */   private PDTristimulus whitepoint = null;
/*  38 */   private PDTristimulus blackpoint = null;
/*  39 */   private PDMatrix matrix = null;
/*     */   private static final long serialVersionUID = -6362864473145799405L;
/*     */ 
/*     */   public ColorSpaceCalRGB()
/*     */   {
/*  51 */     super(13, 3);
/*     */   }
/*     */ 
/*     */   public ColorSpaceCalRGB(PDGamma gammaValue, PDTristimulus whitept, PDTristimulus blackpt, PDMatrix linearMatrix)
/*     */   {
/*  63 */     this();
/*  64 */     this.gamma = gammaValue;
/*  65 */     this.whitepoint = whitept;
/*  66 */     this.blackpoint = blackpt;
/*  67 */     this.matrix = linearMatrix;
/*     */   }
/*     */ 
/*     */   private float[] fromRGBtoCIEXYZ(float[] rgbvalue)
/*     */   {
/*  77 */     ColorSpace colorspaceRGB = ColorSpace.getInstance(1000);
/*  78 */     return colorspaceRGB.toCIEXYZ(rgbvalue);
/*     */   }
/*     */ 
/*     */   private float[] fromCIEXYZtoRGB(float[] xyzvalue)
/*     */   {
/*  88 */     ColorSpace colorspaceXYZ = ColorSpace.getInstance(1001);
/*  89 */     return colorspaceXYZ.toRGB(xyzvalue);
/*     */   }
/*     */ 
/*     */   public float[] fromCIEXYZ(float[] colorvalue)
/*     */   {
/*  97 */     if ((colorvalue != null) && (colorvalue.length == 3))
/*     */     {
/* 100 */       return fromCIEXYZtoRGB(colorvalue);
/*     */     }
/* 102 */     return null;
/*     */   }
/*     */ 
/*     */   public float[] fromRGB(float[] rgbvalue)
/*     */   {
/* 110 */     if ((rgbvalue != null) && (rgbvalue.length == 3))
/*     */     {
/* 112 */       return rgbvalue;
/*     */     }
/* 114 */     return null;
/*     */   }
/*     */ 
/*     */   public float[] toCIEXYZ(float[] colorvalue)
/*     */   {
/* 122 */     if ((colorvalue != null) && (colorvalue.length == 4))
/*     */     {
/* 125 */       return fromRGBtoCIEXYZ(toRGB(colorvalue));
/*     */     }
/* 127 */     return null;
/*     */   }
/*     */ 
/*     */   public float[] toRGB(float[] colorvalue)
/*     */   {
/* 135 */     if ((colorvalue != null) && (colorvalue.length == 3))
/*     */     {
/* 137 */       return colorvalue;
/*     */     }
/* 139 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.color.ColorSpaceCalRGB
 * JD-Core Version:    0.6.2
 */