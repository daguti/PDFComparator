/*     */ package org.apache.pdfbox.pdmodel.graphics.color;
/*     */ 
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.ComponentColorModel;
/*     */ import java.awt.image.IndexColorModel;
/*     */ import java.io.IOException;
/*     */ 
/*     */ public class PDDeviceGray extends PDColorSpace
/*     */ {
/*     */   public static final String NAME = "DeviceGray";
/*     */   public static final String ABBREVIATED_NAME = "G";
/*     */ 
/*     */   public String getName()
/*     */   {
/*  52 */     return "DeviceGray";
/*     */   }
/*     */ 
/*     */   public int getNumberOfComponents()
/*     */     throws IOException
/*     */   {
/*  64 */     return 1;
/*     */   }
/*     */ 
/*     */   protected ColorSpace createColorSpace()
/*     */   {
/*  74 */     return ColorSpace.getInstance(1003);
/*     */   }
/*     */ 
/*     */   public ColorModel createColorModel(int bpc)
/*     */     throws IOException
/*     */   {
/*  88 */     ColorModel colorModel = null;
/*  89 */     if (bpc == 8)
/*     */     {
/*  91 */       ColorSpace cs = ColorSpace.getInstance(1003);
/*  92 */       int[] nBits = { bpc };
/*  93 */       colorModel = new ComponentColorModel(cs, nBits, false, false, 1, 0);
/*     */     }
/*     */     else
/*     */     {
/*  97 */       int numEntries = 1 << bpc;
/*     */ 
/*  99 */       byte[] indexedValues = new byte[numEntries];
/* 100 */       for (int i = 0; i < numEntries; i++)
/*     */       {
/* 102 */         indexedValues[i] = ((byte)(i * 255 / (numEntries - 1)));
/*     */       }
/* 104 */       colorModel = new IndexColorModel(bpc, numEntries, indexedValues, indexedValues, indexedValues);
/*     */     }
/* 106 */     return colorModel;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.color.PDDeviceGray
 * JD-Core Version:    0.6.2
 */