/*     */ package org.apache.pdfbox.pdmodel.graphics.xobject;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ 
/*     */ public class CompositeImage
/*     */ {
/*     */   private BufferedImage baseImage;
/*     */   private BufferedImage smaskImage;
/*     */ 
/*     */   public CompositeImage(BufferedImage baseImage, BufferedImage smaskImage)
/*     */   {
/*  53 */     this.baseImage = baseImage;
/*  54 */     this.smaskImage = smaskImage;
/*     */   }
/*     */ 
/*     */   public BufferedImage createMaskedImage(COSArray decodeArray)
/*     */     throws IOException
/*     */   {
/*  72 */     boolean isOpaque = false;
/*  73 */     if (decodeArray != null)
/*     */     {
/*  75 */       isOpaque = decodeArray.getInt(0) > decodeArray.getInt(1);
/*     */     }
/*     */ 
/*  78 */     int baseImageWidth = this.baseImage.getWidth();
/*  79 */     int baseImageHeight = this.baseImage.getHeight();
/*     */ 
/*  82 */     int width = Math.min(baseImageWidth, this.smaskImage.getWidth());
/*  83 */     int height = Math.min(baseImageHeight, this.smaskImage.getHeight());
/*     */ 
/*  85 */     BufferedImage result = new BufferedImage(baseImageWidth, baseImageHeight, 2);
/*  86 */     for (int x = 0; x < width; x++)
/*     */     {
/*  88 */       for (int y = 0; y < height; y++)
/*     */       {
/*  90 */         int rgb = this.baseImage.getRGB(x, y);
/*  91 */         int alpha = this.smaskImage.getRGB(x, y);
/*     */ 
/*  98 */         int rgbOnly = 0xFFFFFF & rgb;
/*     */ 
/* 103 */         if (isOpaque)
/*     */         {
/* 105 */           alpha ^= -1;
/*     */         }
/* 107 */         int alphaOnly = alpha << 24;
/*     */ 
/* 109 */         result.setRGB(x, y, rgbOnly | alphaOnly);
/*     */       }
/*     */     }
/* 112 */     return result;
/*     */   }
/*     */ 
/*     */   public BufferedImage createStencilMaskedImage(COSArray decodeArray)
/*     */   {
/* 125 */     int alphaValue = 0;
/* 126 */     if (decodeArray != null)
/*     */     {
/* 129 */       alphaValue = decodeArray.getInt(0) > decodeArray.getInt(1) ? 1 : 0;
/*     */     }
/*     */ 
/* 132 */     int baseImageWidth = this.baseImage.getWidth();
/* 133 */     int baseImageHeight = this.baseImage.getHeight();
/* 134 */     WritableRaster maskRaster = this.smaskImage.getRaster();
/* 135 */     BufferedImage result = new BufferedImage(baseImageWidth, baseImageHeight, 2);
/* 136 */     int[] alpha = new int[1];
/*     */ 
/* 139 */     int width = Math.min(baseImageWidth, this.smaskImage.getWidth());
/* 140 */     int height = Math.min(baseImageHeight, this.smaskImage.getHeight());
/*     */ 
/* 142 */     for (int x = 0; x < width; x++)
/*     */     {
/* 144 */       for (int y = 0; y < height; y++)
/*     */       {
/* 146 */         maskRaster.getPixel(x, y, alpha);
/*     */ 
/* 148 */         int rgbOnly = 0xFFFFFF & this.baseImage.getRGB(x, y);
/* 149 */         int alphaOnly = alpha[0] == alphaValue ? -16777216 : 0;
/* 150 */         result.setRGB(x, y, rgbOnly | alphaOnly);
/*     */       }
/*     */     }
/* 153 */     return result;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.xobject.CompositeImage
 * JD-Core Version:    0.6.2
 */