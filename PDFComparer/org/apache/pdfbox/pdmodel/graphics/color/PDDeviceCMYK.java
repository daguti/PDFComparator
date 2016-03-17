/*     */ package org.apache.pdfbox.pdmodel.graphics.color;
/*     */ 
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.ComponentColorModel;
/*     */ import java.io.IOException;
/*     */ 
/*     */ public class PDDeviceCMYK extends PDColorSpace
/*     */ {
/*  39 */   public static final PDDeviceCMYK INSTANCE = new PDDeviceCMYK();
/*     */   public static final String NAME = "DeviceCMYK";
/*     */   public static final String ABBREVIATED_NAME = "CMYK";
/*     */ 
/*     */   public String getName()
/*     */   {
/*  62 */     return "DeviceCMYK";
/*     */   }
/*     */ 
/*     */   public int getNumberOfComponents()
/*     */     throws IOException
/*     */   {
/*  74 */     return 4;
/*     */   }
/*     */ 
/*     */   protected ColorSpace createColorSpace()
/*     */   {
/*  84 */     return new ColorSpaceCMYK();
/*     */   }
/*     */ 
/*     */   public ColorModel createColorModel(int bpc)
/*     */     throws IOException
/*     */   {
/*  99 */     int[] nbBits = { bpc, bpc, bpc, bpc };
/* 100 */     ComponentColorModel componentColorModel = new ComponentColorModel(getJavaColorSpace(), nbBits, false, false, 1, 0);
/*     */ 
/* 107 */     return componentColorModel;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.color.PDDeviceCMYK
 * JD-Core Version:    0.6.2
 */