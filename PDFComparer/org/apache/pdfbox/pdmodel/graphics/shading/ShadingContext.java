/*     */ package org.apache.pdfbox.pdmodel.graphics.shading;
/*     */ 
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.ComponentColorModel;
/*     */ import java.io.IOException;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ import org.apache.pdfbox.pdmodel.common.function.PDFunction;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceN;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDSeparation;
/*     */ import org.apache.pdfbox.util.Matrix;
/*     */ 
/*     */ public abstract class ShadingContext
/*     */ {
/*  44 */   private static final Log LOG = LogFactory.getLog(ShadingContext.class);
/*     */   protected final PDShadingResources shading;
/*     */   protected final Rectangle deviceBounds;
/*     */   protected ColorSpace shadingColorSpace;
/*     */   protected PDColorSpace colorSpace;
/*     */   protected PDRectangle bboxRect;
/*     */   protected float minBBoxX;
/*     */   protected float minBBoxY;
/*     */   protected float maxBBoxX;
/*     */   protected float maxBBoxY;
/*     */   protected ColorModel outputColorModel;
/*     */   protected PDFunction shadingTinttransform;
/*     */ 
/*     */   public ShadingContext(PDShadingResources shading, ColorModel cm, AffineTransform xform, Matrix ctm, int pageHeight, Rectangle dBounds)
/*     */     throws IOException
/*     */   {
/*  58 */     this.shading = shading;
/*  59 */     this.deviceBounds = dBounds;
/*     */ 
/*  61 */     this.colorSpace = shading.getColorSpace();
/*     */     try
/*     */     {
/*  66 */       if (!(this.colorSpace instanceof PDDeviceRGB))
/*     */       {
/*  69 */         this.shadingColorSpace = this.colorSpace.getJavaColorSpace();
/*     */ 
/*  71 */         if ((this.colorSpace instanceof PDDeviceN))
/*     */         {
/*  73 */           this.shadingTinttransform = ((PDDeviceN)this.colorSpace).getTintTransform();
/*     */         }
/*  75 */         else if ((this.colorSpace instanceof PDSeparation))
/*     */         {
/*  77 */           this.shadingTinttransform = ((PDSeparation)this.colorSpace).getTintTransform();
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (IOException exception)
/*     */     {
/*  83 */       LOG.error("error while creating colorSpace", exception);
/*     */     }
/*     */ 
/*  86 */     ColorSpace outputCS = ColorSpace.getInstance(1000);
/*  87 */     this.outputColorModel = new ComponentColorModel(outputCS, true, false, 3, 0);
/*     */ 
/*  90 */     this.bboxRect = shading.getBBox();
/*  91 */     if (this.bboxRect != null)
/*     */     {
/*  93 */       transformBBox(ctm, xform, pageHeight);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void transformBBox(Matrix ctm, AffineTransform xform, int pageHeight)
/*     */   {
/*  99 */     float[] bboxTab = new float[4];
/* 100 */     bboxTab[0] = this.bboxRect.getLowerLeftX();
/* 101 */     bboxTab[1] = this.bboxRect.getLowerLeftY();
/* 102 */     bboxTab[2] = this.bboxRect.getUpperRightX();
/* 103 */     bboxTab[3] = this.bboxRect.getUpperRightY();
/* 104 */     if (ctm != null)
/*     */     {
/* 108 */       ctm.createAffineTransform().transform(bboxTab, 0, bboxTab, 0, 2);
/*     */ 
/* 110 */       bboxTab[1] = (pageHeight - bboxTab[1]);
/* 111 */       bboxTab[3] = (pageHeight - bboxTab[3]);
/*     */     }
/*     */     else
/*     */     {
/* 117 */       float translateY = (float)xform.getTranslateY();
/*     */ 
/* 119 */       bboxTab[1] = (pageHeight + translateY - bboxTab[1]);
/* 120 */       bboxTab[3] = (pageHeight + translateY - bboxTab[3]);
/*     */     }
/* 122 */     xform.transform(bboxTab, 0, bboxTab, 0, 2);
/* 123 */     this.minBBoxX = Math.min(bboxTab[0], bboxTab[2]);
/* 124 */     this.minBBoxY = Math.min(bboxTab[1], bboxTab[3]);
/* 125 */     this.maxBBoxX = Math.max(bboxTab[0], bboxTab[2]);
/* 126 */     this.maxBBoxY = Math.max(bboxTab[1], bboxTab[3]);
/* 127 */     if ((this.minBBoxX >= this.maxBBoxX) || (this.minBBoxY >= this.maxBBoxY))
/*     */     {
/* 129 */       LOG.warn("empty BBox is ignored");
/* 130 */       this.bboxRect = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected int convertToRGB(float[] values)
/*     */   {
/* 137 */     int normRGBValues = 0;
/*     */ 
/* 139 */     if (this.shadingColorSpace != null)
/*     */     {
/* 141 */       if (this.shadingTinttransform != null)
/*     */       {
/*     */         try
/*     */         {
/* 145 */           values = this.shadingTinttransform.eval(values);
/*     */         }
/*     */         catch (IOException exception)
/*     */         {
/* 149 */           LOG.error("error while processing a function", exception);
/*     */         }
/*     */       }
/* 152 */       values = this.shadingColorSpace.toRGB(values);
/*     */     }
/* 154 */     normRGBValues = (int)(values[0] * 255.0F);
/* 155 */     normRGBValues |= (int)(values[1] * 255.0F) << 8;
/* 156 */     normRGBValues |= (int)(values[2] * 255.0F) << 16;
/* 157 */     return normRGBValues;
/*     */   }
/*     */ 
/*     */   public PDFunction getShadingTintTransform()
/*     */   {
/* 167 */     return this.shadingTinttransform;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.shading.ShadingContext
 * JD-Core Version:    0.6.2
 */