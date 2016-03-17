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
/*     */ public class AxialShadingContext extends ShadingContext
/*     */   implements PaintContext
/*     */ {
/*  46 */   private static final Log LOG = LogFactory.getLog(AxialShadingContext.class);
/*     */   private PDShadingType2 axialShadingType;
/*     */   private final float[] coords;
/*     */   private final float[] domain;
/*     */   private float[] background;
/*     */   private int rgbBackground;
/*     */   private final boolean[] extend;
/*     */   private final double x1x0;
/*     */   private final double y1y0;
/*     */   private final float d1d0;
/*     */   private double denom;
/*     */   private final double axialLength;
/*     */   private final int[] colorTable;
/*     */ 
/*     */   public AxialShadingContext(PDShadingType2 shading, ColorModel colorModel, AffineTransform xform, Matrix ctm, int pageHeight, Rectangle dBounds)
/*     */     throws IOException
/*     */   {
/*  76 */     super(shading, colorModel, xform, ctm, pageHeight, dBounds);
/*  77 */     this.axialShadingType = shading;
/*  78 */     this.coords = shading.getCoords().toFloatArray();
/*     */ 
/*  80 */     if (ctm != null)
/*     */     {
/*  84 */       ctm.createAffineTransform().transform(this.coords, 0, this.coords, 0, 2);
/*     */ 
/*  86 */       this.coords[1] = (pageHeight - this.coords[1]);
/*  87 */       this.coords[3] = (pageHeight - this.coords[3]);
/*     */     }
/*     */     else
/*     */     {
/*  93 */       float translateY = (float)xform.getTranslateY();
/*     */ 
/*  95 */       this.coords[1] = (pageHeight + translateY - this.coords[1]);
/*  96 */       this.coords[3] = (pageHeight + translateY - this.coords[3]);
/*     */     }
/*     */ 
/*  99 */     xform.transform(this.coords, 0, this.coords, 0, 2);
/*     */ 
/* 102 */     if (shading.getDomain() != null)
/*     */     {
/* 104 */       this.domain = shading.getDomain().toFloatArray();
/*     */     }
/*     */     else
/*     */     {
/* 109 */       this.domain = new float[] { 0.0F, 1.0F };
/*     */     }
/*     */ 
/* 115 */     COSArray extendValues = shading.getExtend();
/* 116 */     if (shading.getExtend() != null)
/*     */     {
/* 118 */       this.extend = new boolean[2];
/* 119 */       this.extend[0] = ((COSBoolean)extendValues.get(0)).getValue();
/* 120 */       this.extend[1] = ((COSBoolean)extendValues.get(1)).getValue();
/*     */     }
/*     */     else
/*     */     {
/* 125 */       this.extend = new boolean[] { false, false };
/*     */     }
/*     */ 
/* 131 */     this.x1x0 = (this.coords[2] - this.coords[0]);
/* 132 */     this.y1y0 = (this.coords[3] - this.coords[1]);
/* 133 */     this.d1d0 = (this.domain[1] - this.domain[0]);
/* 134 */     this.denom = (Math.pow(this.x1x0, 2.0D) + Math.pow(this.y1y0, 2.0D));
/* 135 */     this.axialLength = Math.sqrt(this.denom);
/*     */ 
/* 138 */     COSArray bg = shading.getBackground();
/* 139 */     if (bg != null)
/*     */     {
/* 141 */       this.background = bg.toFloatArray();
/* 142 */       this.rgbBackground = convertToRGB(this.background);
/*     */     }
/* 144 */     this.colorTable = calcColorTable();
/*     */   }
/*     */ 
/*     */   private int[] calcColorTable()
/*     */   {
/* 155 */     int[] map = new int[(int)this.axialLength + 1];
/* 156 */     if ((this.axialLength == 0.0D) || (this.d1d0 == 0.0F))
/*     */     {
/*     */       try
/*     */       {
/* 160 */         float[] values = this.axialShadingType.evalFunction(this.domain[0]);
/* 161 */         map[0] = convertToRGB(values);
/*     */       }
/*     */       catch (IOException exception)
/*     */       {
/* 165 */         LOG.error("error while processing a function", exception);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 170 */       for (int i = 0; i <= this.axialLength; i++)
/*     */       {
/* 172 */         float t = this.domain[0] + this.d1d0 * i / (float)this.axialLength;
/*     */         try
/*     */         {
/* 175 */           float[] values = this.axialShadingType.evalFunction(t);
/* 176 */           map[i] = convertToRGB(values);
/*     */         }
/*     */         catch (IOException exception)
/*     */         {
/* 180 */           LOG.error("error while processing a function", exception);
/*     */         }
/*     */       }
/*     */     }
/* 184 */     return map;
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/* 192 */     this.outputColorModel = null;
/* 193 */     this.shadingColorSpace = null;
/* 194 */     this.shadingTinttransform = null;
/* 195 */     this.axialShadingType = null;
/*     */   }
/*     */ 
/*     */   public ColorModel getColorModel()
/*     */   {
/* 203 */     return this.outputColorModel;
/*     */   }
/*     */ 
/*     */   public Raster getRaster(int x, int y, int w, int h)
/*     */   {
/* 212 */     WritableRaster raster = getColorModel().createCompatibleWritableRaster(w, h);
/*     */ 
/* 214 */     int[] data = new int[w * h * 4];
/* 215 */     for (int j = 0; j < h; j++)
/*     */     {
/* 217 */       double currentY = y + j;
/* 218 */       if ((this.bboxRect == null) || (
/* 220 */         (currentY >= this.minBBoxY) && (currentY <= this.maxBBoxY)))
/*     */       {
/* 225 */         for (int i = 0; i < w; i++)
/*     */         {
/* 227 */           double currentX = x + i;
/* 228 */           if ((this.bboxRect == null) || (
/* 230 */             (currentX >= this.minBBoxX) && (currentX <= this.maxBBoxX)))
/*     */           {
/* 236 */             boolean useBackground = false;
/* 237 */             double inputValue = this.x1x0 * (currentX - this.coords[0]);
/* 238 */             inputValue += this.y1y0 * (currentY - this.coords[1]);
/*     */ 
/* 240 */             if (this.denom == 0.0D)
/*     */             {
/* 242 */               if (this.background == null)
/*     */                 continue;
/* 244 */               useBackground = true;
/*     */             }
/*     */             else
/*     */             {
/* 253 */               inputValue /= this.denom;
/*     */             }
/*     */ 
/* 256 */             if (inputValue < 0.0D)
/*     */             {
/* 259 */               if (this.extend[0] != 0)
/*     */               {
/* 261 */                 inputValue = 0.0D;
/*     */               }
/*     */               else
/*     */               {
/* 265 */                 if (this.background == null)
/*     */                   continue;
/* 267 */                 useBackground = true;
/*     */               }
/*     */ 
/*     */             }
/* 276 */             else if (inputValue > 1.0D)
/*     */             {
/* 279 */               if (this.extend[1] != 0)
/*     */               {
/* 281 */                 inputValue = 1.0D;
/*     */               }
/*     */               else
/*     */               {
/* 285 */                 if (this.background == null)
/*     */                   continue;
/* 287 */                 useBackground = true;
/*     */               }
/*     */             }
/*     */             int value;
/*     */             int value;
/* 296 */             if (useBackground)
/*     */             {
/* 299 */               value = this.rgbBackground;
/*     */             }
/*     */             else
/*     */             {
/* 303 */               int key = (int)(inputValue * this.axialLength);
/* 304 */               value = this.colorTable[key];
/*     */             }
/* 306 */             int index = (j * w + i) * 4;
/* 307 */             data[index] = (value & 0xFF);
/* 308 */             value >>= 8;
/* 309 */             data[(index + 1)] = (value & 0xFF);
/* 310 */             value >>= 8;
/* 311 */             data[(index + 2)] = (value & 0xFF);
/* 312 */             data[(index + 3)] = 255;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 315 */     raster.setPixels(0, 0, w, h, data);
/* 316 */     return raster;
/*     */   }
/*     */ 
/*     */   public float[] getCoords()
/*     */   {
/* 326 */     return this.coords;
/*     */   }
/*     */ 
/*     */   public float[] getDomain()
/*     */   {
/* 336 */     return this.domain;
/*     */   }
/*     */ 
/*     */   public boolean[] getExtend()
/*     */   {
/* 346 */     return this.extend;
/*     */   }
/*     */ 
/*     */   public PDFunction getFunction()
/*     */     throws IOException
/*     */   {
/* 357 */     return this.axialShadingType.getFunction();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.shading.AxialShadingContext
 * JD-Core Version:    0.6.2
 */