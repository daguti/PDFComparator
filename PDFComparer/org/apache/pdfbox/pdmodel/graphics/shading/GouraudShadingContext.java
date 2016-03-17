/*     */ package org.apache.pdfbox.pdmodel.graphics.shading;
/*     */ 
/*     */ import java.awt.PaintContext;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Point2D.Double;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import javax.imageio.stream.ImageInputStream;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRange;
/*     */ import org.apache.pdfbox.util.Matrix;
/*     */ 
/*     */ abstract class GouraudShadingContext extends TriangleBasedShadingContext
/*     */   implements PaintContext
/*     */ {
/*  46 */   private static final Log LOG = LogFactory.getLog(GouraudShadingContext.class);
/*     */   protected ArrayList<ShadedTriangle> triangleList;
/*     */   protected float[] background;
/*     */   protected int rgbBackground;
/*     */   protected HashMap<Point, Integer> pixelTable;
/*     */ 
/*     */   protected GouraudShadingContext(PDShadingResources shading, ColorModel colorModel, AffineTransform xform, Matrix ctm, int pageHeight, Rectangle dBounds)
/*     */     throws IOException
/*     */   {
/*  74 */     super(shading, colorModel, xform, ctm, pageHeight, dBounds);
/*  75 */     this.triangleList = new ArrayList();
/*  76 */     LOG.debug("Background: " + shading.getBackground());
/*  77 */     COSArray bg = shading.getBackground();
/*  78 */     if (bg != null)
/*     */     {
/*  80 */       this.background = bg.toFloatArray();
/*  81 */       this.rgbBackground = convertToRGB(this.background);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Vertex readVertex(ImageInputStream input, long maxSrcCoord, long maxSrcColor, PDRange rangeX, PDRange rangeY, PDRange[] colRangeTab, Matrix ctm, AffineTransform xform)
/*     */     throws IOException
/*     */   {
/* 102 */     float[] colorComponentTab = new float[this.numberOfColorComponents];
/* 103 */     long x = input.readBits(this.bitsPerCoordinate);
/* 104 */     long y = input.readBits(this.bitsPerCoordinate);
/* 105 */     double dstX = interpolate((float)x, maxSrcCoord, rangeX.getMin(), rangeX.getMax());
/* 106 */     double dstY = interpolate((float)y, maxSrcCoord, rangeY.getMin(), rangeY.getMax());
/* 107 */     LOG.debug("coord: " + String.format("[%06X,%06X] -> [%f,%f]", new Object[] { Long.valueOf(x), Long.valueOf(y), Double.valueOf(dstX), Double.valueOf(dstY) }));
/* 108 */     Point2D tmp = new Point2D.Double(dstX, dstY);
/* 109 */     transformPoint(tmp, ctm, xform);
/*     */ 
/* 111 */     for (int n = 0; n < this.numberOfColorComponents; n++)
/*     */     {
/* 113 */       int color = (int)input.readBits(this.bitsPerColorComponent);
/* 114 */       colorComponentTab[n] = interpolate(color, maxSrcColor, colRangeTab[n].getMin(), colRangeTab[n].getMax());
/* 115 */       LOG.debug("color[" + n + "]: " + color + "/" + String.format("%02x", new Object[] { Integer.valueOf(color) }) + "-> color[" + n + "]: " + colorComponentTab[n]);
/*     */     }
/*     */ 
/* 118 */     return new Vertex(tmp, colorComponentTab);
/*     */   }
/*     */ 
/*     */   protected HashMap<Point, Integer> calcPixelTable()
/*     */   {
/* 123 */     HashMap map = new HashMap();
/* 124 */     super.calcPixelTable(this.triangleList, map);
/* 125 */     return map;
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/* 133 */     this.triangleList = null;
/* 134 */     this.outputColorModel = null;
/* 135 */     this.shadingColorSpace = null;
/* 136 */     this.shadingTinttransform = null;
/*     */   }
/*     */ 
/*     */   public final ColorModel getColorModel()
/*     */   {
/* 144 */     return this.outputColorModel;
/*     */   }
/*     */ 
/*     */   private float interpolate(float src, long srcMax, float dstMin, float dstMax)
/*     */   {
/* 158 */     return dstMin + src * (dstMax - dstMin) / (float)srcMax;
/*     */   }
/*     */ 
/*     */   public final Raster getRaster(int x, int y, int w, int h)
/*     */   {
/* 166 */     WritableRaster raster = getColorModel().createCompatibleWritableRaster(w, h);
/* 167 */     int[] data = new int[w * h * 4];
/* 168 */     if ((!this.triangleList.isEmpty()) || (this.background != null))
/*     */     {
/* 170 */       for (int row = 0; row < h; row++)
/*     */       {
/* 172 */         int currentY = y + row;
/* 173 */         if ((this.bboxRect == null) || (
/* 175 */           (currentY >= this.minBBoxY) && (currentY <= this.maxBBoxY)))
/*     */         {
/* 180 */           for (int col = 0; col < w; col++)
/*     */           {
/* 182 */             int currentX = x + col;
/* 183 */             if ((this.bboxRect == null) || (
/* 185 */               (currentX >= this.minBBoxX) && (currentX <= this.maxBBoxX)))
/*     */             {
/* 190 */               Point p = new Point(currentX, currentY);
/*     */               int value;
/*     */               int value;
/* 192 */               if (this.pixelTable.containsKey(p))
/*     */               {
/* 194 */                 value = ((Integer)this.pixelTable.get(p)).intValue();
/*     */               }
/*     */               else
/*     */               {
/* 198 */                 if (this.background == null)
/*     */                   continue;
/* 200 */                 value = this.rgbBackground;
/*     */               }
/*     */ 
/* 207 */               int index = (row * w + col) * 4;
/* 208 */               data[index] = (value & 0xFF);
/* 209 */               value >>= 8;
/* 210 */               data[(index + 1)] = (value & 0xFF);
/* 211 */               value >>= 8;
/* 212 */               data[(index + 2)] = (value & 0xFF);
/* 213 */               data[(index + 3)] = 255;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 217 */     raster.setPixels(0, 0, w, h, data);
/* 218 */     return raster;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.shading.GouraudShadingContext
 * JD-Core Version:    0.6.2
 */