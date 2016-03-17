/*     */ package org.apache.pdfbox.pdmodel.graphics.shading;
/*     */ 
/*     */ import java.awt.PaintContext;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.io.IOException;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBoolean;
/*     */ import org.apache.pdfbox.pdmodel.common.function.PDFunction;
/*     */ import org.apache.pdfbox.util.Matrix;
/*     */ 
/*     */ public class RadialShadingContext extends ShadingContext
/*     */   implements PaintContext
/*     */ {
/*  45 */   private static final Log LOG = LogFactory.getLog(RadialShadingContext.class);
/*     */   private PDShadingType3 radialShadingType;
/*     */   private final float[] coords;
/*     */   private final float[] domain;
/*     */   private float[] background;
/*     */   private int rgbBackground;
/*     */   private final boolean[] extend;
/*     */   private final double x1x0;
/*     */   private final double y1y0;
/*     */   private final double r1r0;
/*     */   private final double x1x0pow2;
/*     */   private final double y1y0pow2;
/*     */   private final double r0pow2;
/*     */   private final float d1d0;
/*     */   private final double denom;
/*     */   private final double longestDistance;
/*     */   private final int[] colorTable;
/*     */ 
/*     */   public RadialShadingContext(PDShadingType3 shading, ColorModel colorModel, AffineTransform xform, Matrix ctm, int pageHeight, Rectangle dBounds)
/*     */     throws IOException
/*     */   {
/*  79 */     super(shading, colorModel, xform, ctm, pageHeight, dBounds);
/*  80 */     this.radialShadingType = shading;
/*  81 */     this.coords = shading.getCoords().toFloatArray();
/*     */ 
/*  83 */     if (ctm != null)
/*     */     {
/*  87 */       ctm.createAffineTransform().transform(this.coords, 0, this.coords, 0, 1);
/*  88 */       ctm.createAffineTransform().transform(this.coords, 3, this.coords, 3, 1);
/*     */ 
/*  90 */       this.coords[2] *= ctm.getXScale();
/*  91 */       this.coords[5] *= ctm.getXScale();
/*     */ 
/*  94 */       this.coords[1] = (pageHeight - this.coords[1]);
/*  95 */       this.coords[4] = (pageHeight - this.coords[4]);
/*     */     }
/*     */     else
/*     */     {
/* 101 */       float translateY = (float)xform.getTranslateY();
/*     */ 
/* 103 */       this.coords[1] = (pageHeight + translateY - this.coords[1]);
/* 104 */       this.coords[4] = (pageHeight + translateY - this.coords[4]);
/*     */     }
/*     */ 
/* 108 */     xform.transform(this.coords, 0, this.coords, 0, 1);
/* 109 */     xform.transform(this.coords, 3, this.coords, 3, 1);
/*     */     int tmp215_214 = 2;
/*     */     float[] tmp215_211 = this.coords; tmp215_211[tmp215_214] = ((float)(tmp215_211[tmp215_214] * xform.getScaleX()));
/*     */     int tmp230_229 = 5;
/*     */     float[] tmp230_226 = this.coords; tmp230_226[tmp230_229] = ((float)(tmp230_226[tmp230_229] * xform.getScaleX()));
/*     */ 
/* 116 */     this.coords[2] = Math.abs(this.coords[2]);
/* 117 */     this.coords[5] = Math.abs(this.coords[5]);
/*     */ 
/* 120 */     if (this.radialShadingType.getDomain() != null)
/*     */     {
/* 122 */       this.domain = shading.getDomain().toFloatArray();
/*     */     }
/*     */     else
/*     */     {
/* 127 */       this.domain = new float[] { 0.0F, 1.0F };
/*     */     }
/*     */ 
/* 134 */     COSArray extendValues = shading.getExtend();
/* 135 */     if (shading.getExtend() != null)
/*     */     {
/* 137 */       this.extend = new boolean[2];
/* 138 */       this.extend[0] = ((COSBoolean)extendValues.get(0)).getValue();
/* 139 */       this.extend[1] = ((COSBoolean)extendValues.get(1)).getValue();
/*     */     }
/*     */     else
/*     */     {
/* 144 */       this.extend = new boolean[] { false, false };
/*     */     }
/*     */ 
/* 150 */     this.x1x0 = (this.coords[3] - this.coords[0]);
/* 151 */     this.y1y0 = (this.coords[4] - this.coords[1]);
/* 152 */     this.r1r0 = (this.coords[5] - this.coords[2]);
/* 153 */     this.x1x0pow2 = Math.pow(this.x1x0, 2.0D);
/* 154 */     this.y1y0pow2 = Math.pow(this.y1y0, 2.0D);
/* 155 */     this.r0pow2 = Math.pow(this.coords[2], 2.0D);
/* 156 */     this.denom = (this.x1x0pow2 + this.y1y0pow2 - Math.pow(this.r1r0, 2.0D));
/* 157 */     this.d1d0 = (this.domain[1] - this.domain[0]);
/*     */ 
/* 160 */     COSArray bg = shading.getBackground();
/* 161 */     if (bg != null)
/*     */     {
/* 163 */       this.background = bg.toFloatArray();
/* 164 */       this.rgbBackground = convertToRGB(this.background);
/*     */     }
/* 166 */     this.longestDistance = getLongestDis();
/* 167 */     this.colorTable = calcColorTable();
/*     */   }
/*     */ 
/*     */   private double getLongestDis()
/*     */   {
/* 173 */     double centerToCenter = Math.sqrt(this.x1x0pow2 + this.y1y0pow2);
/*     */     double rmax;
/*     */     double rmin;
/*     */     double rmax;
/* 175 */     if (this.coords[2] < this.coords[5])
/*     */     {
/* 177 */       double rmin = this.coords[2];
/* 178 */       rmax = this.coords[5];
/*     */     }
/*     */     else
/*     */     {
/* 182 */       rmin = this.coords[5];
/* 183 */       rmax = this.coords[2];
/*     */     }
/* 185 */     if (centerToCenter + rmin <= rmax)
/*     */     {
/* 187 */       return 2.0D * rmax;
/*     */     }
/*     */ 
/* 191 */     return rmin + centerToCenter + this.coords[5];
/*     */   }
/*     */ 
/*     */   private int[] calcColorTable()
/*     */   {
/* 204 */     int[] map = new int[(int)this.longestDistance + 1];
/* 205 */     if ((this.longestDistance == 0.0D) || (this.d1d0 == 0.0F))
/*     */     {
/*     */       try
/*     */       {
/* 209 */         float[] values = this.radialShadingType.evalFunction(this.domain[0]);
/* 210 */         map[0] = convertToRGB(values);
/*     */       }
/*     */       catch (IOException exception)
/*     */       {
/* 214 */         LOG.error("error while processing a function", exception);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 219 */       for (int i = 0; i <= this.longestDistance; i++)
/*     */       {
/* 221 */         float t = this.domain[0] + this.d1d0 * i / (float)this.longestDistance;
/*     */         try
/*     */         {
/* 224 */           float[] values = this.radialShadingType.evalFunction(t);
/* 225 */           map[i] = convertToRGB(values);
/*     */         }
/*     */         catch (IOException exception)
/*     */         {
/* 229 */           LOG.error("error while processing a function", exception);
/*     */         }
/*     */       }
/*     */     }
/* 233 */     return map;
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/* 241 */     this.outputColorModel = null;
/* 242 */     this.radialShadingType = null;
/* 243 */     this.shadingColorSpace = null;
/* 244 */     this.shadingTinttransform = null;
/*     */   }
/*     */ 
/*     */   public ColorModel getColorModel()
/*     */   {
/* 252 */     return this.outputColorModel;
/*     */   }
/*     */ 
/*     */   public Raster getRaster(int x, int y, int w, int h)
/*     */   {
/* 261 */     WritableRaster raster = getColorModel().createCompatibleWritableRaster(w, h);
/* 262 */     float inputValue = -1.0F;
/*     */ 
/* 264 */     int[] data = new int[w * h * 4];
/* 265 */     for (int j = 0; j < h; j++)
/*     */     {
/* 267 */       double currentY = y + j;
/* 268 */       if ((this.bboxRect == null) || (
/* 270 */         (currentY >= this.minBBoxY) && (currentY <= this.maxBBoxY)))
/*     */       {
/* 275 */         for (int i = 0; i < w; i++)
/*     */         {
/* 277 */           double currentX = x + i;
/* 278 */           if ((this.bboxRect == null) || (
/* 280 */             (currentX >= this.minBBoxX) && (currentX <= this.maxBBoxX)))
/*     */           {
/* 285 */             boolean useBackground = false;
/* 286 */             float[] inputValues = calculateInputValues(x + i, y + j);
/* 287 */             if ((Float.isNaN(inputValues[0])) && (Float.isNaN(inputValues[1])))
/*     */             {
/* 289 */               if (this.background == null)
/*     */                 continue;
/* 291 */               useBackground = true;
/*     */             }
/*     */             else
/*     */             {
/* 301 */               if ((inputValues[0] >= 0.0F) && (inputValues[0] <= 1.0F))
/*     */               {
/* 304 */                 if ((inputValues[1] >= 0.0F) && (inputValues[1] <= 1.0F))
/*     */                 {
/* 306 */                   inputValue = Math.max(inputValues[0], inputValues[1]);
/*     */                 }
/*     */                 else
/*     */                 {
/* 311 */                   inputValue = inputValues[0];
/*     */                 }
/*     */ 
/*     */               }
/* 318 */               else if ((inputValues[1] >= 0.0F) && (inputValues[1] <= 1.0F))
/*     */               {
/* 320 */                 inputValue = inputValues[1];
/*     */               }
/* 325 */               else if ((this.extend[0] != 0) && (this.extend[1] != 0))
/*     */               {
/* 327 */                 inputValue = Math.max(inputValues[0], inputValues[1]);
/*     */               }
/* 329 */               else if (this.extend[0] != 0)
/*     */               {
/* 331 */                 inputValue = inputValues[0];
/*     */               }
/* 333 */               else if (this.extend[1] != 0)
/*     */               {
/* 335 */                 inputValue = inputValues[1];
/*     */               } else {
/* 337 */                 if (this.background == null)
/*     */                   continue;
/* 339 */                 useBackground = true;
/*     */               }
/*     */ 
/* 348 */               if (inputValue > 1.0F)
/*     */               {
/* 351 */                 if ((this.extend[1] != 0) && (this.coords[5] > 0.0F))
/*     */                 {
/* 353 */                   inputValue = 1.0F;
/*     */                 }
/*     */                 else
/*     */                 {
/* 357 */                   if (this.background == null)
/*     */                     continue;
/* 359 */                   useBackground = true;
/*     */                 }
/*     */ 
/*     */               }
/* 368 */               else if (inputValue < 0.0F)
/*     */               {
/* 371 */                 if ((this.extend[0] != 0) && (this.coords[2] > 0.0F))
/*     */                 {
/* 373 */                   inputValue = 0.0F;
/*     */                 }
/*     */                 else
/*     */                 {
/* 377 */                   if (this.background == null)
/*     */                     continue;
/* 379 */                   useBackground = true;
/*     */                 }
/*     */               }
/*     */             }
/*     */             int value;
/*     */             int value;
/* 389 */             if (useBackground)
/*     */             {
/* 392 */               value = this.rgbBackground;
/*     */             }
/*     */             else
/*     */             {
/* 396 */               int key = (int)(inputValue * this.longestDistance);
/* 397 */               value = this.colorTable[key];
/*     */             }
/* 399 */             int index = (j * w + i) * 4;
/* 400 */             data[index] = (value & 0xFF);
/* 401 */             value >>= 8;
/* 402 */             data[(index + 1)] = (value & 0xFF);
/* 403 */             value >>= 8;
/* 404 */             data[(index + 2)] = (value & 0xFF);
/* 405 */             data[(index + 3)] = 255;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 408 */     raster.setPixels(0, 0, w, h, data);
/* 409 */     return raster;
/*     */   }
/*     */ 
/*     */   private float[] calculateInputValues(int x, int y)
/*     */   {
/* 430 */     double p = -(x - this.coords[0]) * this.x1x0 - (y - this.coords[1]) * this.y1y0 - this.coords[2] * this.r1r0;
/* 431 */     double q = Math.pow(x - this.coords[0], 2.0D) + Math.pow(y - this.coords[1], 2.0D) - this.r0pow2;
/* 432 */     double root = Math.sqrt(p * p - this.denom * q);
/* 433 */     float root1 = (float)((-p + root) / this.denom);
/* 434 */     float root2 = (float)((-p - root) / this.denom);
/* 435 */     if (this.denom < 0.0D)
/*     */     {
/* 437 */       return new float[] { root1, root2 };
/*     */     }
/*     */ 
/* 444 */     return new float[] { root2, root1 };
/*     */   }
/*     */ 
/*     */   public float[] getCoords()
/*     */   {
/* 458 */     return this.coords;
/*     */   }
/*     */ 
/*     */   public float[] getDomain()
/*     */   {
/* 468 */     return this.domain;
/*     */   }
/*     */ 
/*     */   public boolean[] getExtend()
/*     */   {
/* 478 */     return this.extend;
/*     */   }
/*     */ 
/*     */   public PDFunction getFunction()
/*     */     throws IOException
/*     */   {
/* 489 */     return this.radialShadingType.getFunction();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.shading.RadialShadingContext
 * JD-Core Version:    0.6.2
 */