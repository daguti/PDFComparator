/*     */ package org.apache.pdfbox.pdmodel.graphics.shading;
/*     */ 
/*     */ import java.awt.PaintContext;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.NoninvertibleTransformException;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.io.IOException;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.pdmodel.common.function.PDFunction;
/*     */ import org.apache.pdfbox.util.Matrix;
/*     */ 
/*     */ class Type1ShadingContext extends ShadingContext
/*     */   implements PaintContext
/*     */ {
/*  39 */   private static final Log LOG = LogFactory.getLog(Type1ShadingContext.class);
/*     */   private PDShadingType1 type1ShadingType;
/*     */   private AffineTransform rat;
/*     */   private final float[] domain;
/*     */   private Matrix matrix;
/*     */   private float[] background;
/*     */ 
/*     */   public Type1ShadingContext(PDShadingType1 shading, ColorModel colorModel, AffineTransform xform, Matrix ctm, int pageHeight, Rectangle dBounds)
/*     */     throws IOException
/*     */   {
/*  60 */     super(shading, colorModel, xform, ctm, pageHeight, dBounds);
/*  61 */     this.type1ShadingType = shading;
/*     */ 
/*  65 */     xform.scale(1.0D, -1.0D);
/*  66 */     xform.translate(0.0D, -pageHeight);
/*     */ 
/*  72 */     if (shading.getDomain() != null)
/*     */     {
/*  74 */       this.domain = shading.getDomain().toFloatArray();
/*     */     }
/*     */     else
/*     */     {
/*  78 */       this.domain = new float[] { 0.0F, 1.0F, 0.0F, 1.0F };
/*     */     }
/*     */ 
/*  84 */     this.matrix = shading.getMatrix();
/*  85 */     if (this.matrix == null)
/*     */     {
/*  87 */       this.matrix = new Matrix();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  95 */       this.rat = this.matrix.createAffineTransform().createInverse();
/*  96 */       this.rat.concatenate(ctm.createAffineTransform().createInverse());
/*  97 */       this.rat.concatenate(xform.createInverse());
/*     */     }
/*     */     catch (NoninvertibleTransformException ex)
/*     */     {
/* 101 */       LOG.error(ex, ex);
/*     */     }
/*     */ 
/* 105 */     COSArray bg = shading.getBackground();
/* 106 */     if (bg != null)
/*     */     {
/* 108 */       this.background = bg.toFloatArray();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/* 117 */     this.outputColorModel = null;
/* 118 */     this.shadingColorSpace = null;
/* 119 */     this.shadingTinttransform = null;
/* 120 */     this.type1ShadingType = null;
/*     */   }
/*     */ 
/*     */   public ColorModel getColorModel()
/*     */   {
/* 128 */     return this.outputColorModel;
/*     */   }
/*     */ 
/*     */   public Raster getRaster(int x, int y, int w, int h)
/*     */   {
/* 136 */     WritableRaster raster = getColorModel().createCompatibleWritableRaster(w, h);
/* 137 */     int[] data = new int[w * h * 4];
/* 138 */     for (int j = 0; j < h; j++)
/*     */     {
/* 140 */       int currentY = y + j;
/* 141 */       if ((this.bboxRect == null) || (
/* 143 */         (currentY >= this.minBBoxY) && (currentY <= this.maxBBoxY)))
/*     */       {
/* 148 */         for (int i = 0; i < w; i++)
/*     */         {
/* 150 */           int currentX = x + i;
/* 151 */           if ((this.bboxRect == null) || (
/* 153 */             (currentX >= this.minBBoxX) && (currentX <= this.maxBBoxX)))
/*     */           {
/* 158 */             int index = (j * w + i) * 4;
/* 159 */             boolean useBackground = false;
/* 160 */             float[] values = { x + i, y + j };
/*     */ 
/* 164 */             this.rat.transform(values, 0, values, 0, 1);
/* 165 */             if ((values[0] < this.domain[0]) || (values[0] > this.domain[1]) || (values[1] < this.domain[2]) || (values[1] > this.domain[3]))
/*     */             {
/* 167 */               if (this.background != null)
/*     */               {
/* 169 */                 useBackground = true;
/*     */               }
/*     */ 
/*     */             }
/*     */             else
/*     */             {
/*     */               try
/*     */               {
/* 179 */                 if (useBackground)
/*     */                 {
/* 181 */                   values = this.background;
/*     */                 }
/*     */                 else
/*     */                 {
/* 185 */                   values = this.type1ShadingType.evalFunction(values);
/*     */                 }
/*     */ 
/* 188 */                 if (this.shadingColorSpace != null)
/*     */                 {
/* 190 */                   if (this.shadingTinttransform != null)
/*     */                   {
/* 192 */                     values = this.shadingTinttransform.eval(values);
/*     */                   }
/* 194 */                   values = this.shadingColorSpace.toRGB(values);
/*     */                 }
/*     */               }
/*     */               catch (IOException exception)
/*     */               {
/* 199 */                 LOG.error("error while processing a function", exception);
/*     */               }
/* 201 */               data[index] = ((int)(values[0] * 255.0F));
/* 202 */               data[(index + 1)] = ((int)(values[1] * 255.0F));
/* 203 */               data[(index + 2)] = ((int)(values[2] * 255.0F));
/* 204 */               data[(index + 3)] = 255;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 207 */     raster.setPixels(0, 0, w, h, data);
/* 208 */     return raster;
/*     */   }
/*     */ 
/*     */   public float[] getDomain()
/*     */   {
/* 213 */     return this.domain;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.shading.Type1ShadingContext
 * JD-Core Version:    0.6.2
 */