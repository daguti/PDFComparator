/*     */ package org.apache.pdfbox.pdmodel.graphics.color;
/*     */ 
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.ComponentColorModel;
/*     */ import java.io.IOException;
/*     */ 
/*     */ public class PDDeviceRGB extends PDColorSpace
/*     */ {
/*     */   public static final String NAME = "DeviceRGB";
/*     */   public static final String ABBREVIATED_NAME = "RGB";
/*  50 */   public static final PDDeviceRGB INSTANCE = new PDDeviceRGB();
/*     */ 
/*     */   public String getName()
/*     */   {
/*  67 */     return "DeviceRGB";
/*     */   }
/*     */ 
/*     */   public int getNumberOfComponents()
/*     */     throws IOException
/*     */   {
/*  79 */     return 3;
/*     */   }
/*     */ 
/*     */   protected ColorSpace createColorSpace()
/*     */   {
/*  89 */     return ColorSpace.getInstance(1000);
/*     */   }
/*     */ 
/*     */   public ColorModel createColorModel(int bpc)
/*     */     throws IOException
/*     */   {
/* 103 */     int[] nbBits = { bpc, bpc, bpc };
/* 104 */     ComponentColorModel componentColorModel = new ComponentColorModel(getJavaColorSpace(), nbBits, false, false, 1, 0);
/*     */ 
/* 112 */     return componentColorModel;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB
 * JD-Core Version:    0.6.2
 */