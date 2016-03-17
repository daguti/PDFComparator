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
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import javax.imageio.stream.ImageInputStream;
/*     */ import javax.imageio.stream.MemoryCacheImageInputStream;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRange;
/*     */ import org.apache.pdfbox.util.Matrix;
/*     */ 
/*     */ abstract class PatchMeshesShadingContext extends TriangleBasedShadingContext
/*     */   implements PaintContext
/*     */ {
/*  48 */   private static final Log LOG = LogFactory.getLog(PatchMeshesShadingContext.class);
/*     */   protected float[] background;
/*     */   protected int rgbBackground;
/*     */   protected final PDShadingResources patchMeshesShadingType;
/*     */   protected ArrayList<Patch> patchList;
/*     */   protected int bitsPerFlag;
/*     */   protected HashMap<Point, Integer> pixelTable;
/*     */ 
/*     */   protected PatchMeshesShadingContext(PDShadingResources shading, ColorModel colorModel, AffineTransform xform, Matrix ctm, int pageHeight, Rectangle dBounds)
/*     */     throws IOException
/*     */   {
/*  73 */     super(shading, colorModel, xform, ctm, pageHeight, dBounds);
/*  74 */     this.patchMeshesShadingType = shading;
/*  75 */     this.bitsPerFlag = ((PDShadingType6)shading).getBitsPerFlag();
/*  76 */     this.patchList = new ArrayList();
/*     */ 
/*  78 */     COSArray bg = shading.getBackground();
/*  79 */     if (bg != null)
/*     */     {
/*  81 */       this.background = bg.toFloatArray();
/*  82 */       this.rgbBackground = convertToRGB(this.background);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected ArrayList<Patch> getPatchList(AffineTransform xform, Matrix ctm, COSDictionary cosDictionary, PDRange rangeX, PDRange rangeY, PDRange[] colRange, int numP)
/*     */     throws IOException
/*     */   {
/* 104 */     ArrayList list = new ArrayList();
/* 105 */     long maxSrcCoord = ()Math.pow(2.0D, this.bitsPerCoordinate) - 1L;
/* 106 */     long maxSrcColor = ()Math.pow(2.0D, this.bitsPerColorComponent) - 1L;
/* 107 */     COSStream cosStream = (COSStream)cosDictionary;
/*     */ 
/* 109 */     ImageInputStream mciis = new MemoryCacheImageInputStream(cosStream.getUnfilteredStream());
/*     */ 
/* 111 */     Point2D[] implicitEdge = new Point2D[4];
/* 112 */     float[][] implicitCornerColor = new float[2][this.numberOfColorComponents];
/*     */ 
/* 114 */     byte flag = 0;
/*     */     try
/*     */     {
/* 118 */       flag = (byte)(int)(mciis.readBits(this.bitsPerFlag) & 0x3);
/*     */     }
/*     */     catch (EOFException ex)
/*     */     {
/* 122 */       LOG.error(ex);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*     */       while (true)
/*     */       {
/* 129 */         boolean isFree = flag == 0;
/* 130 */         Patch current = readPatch(mciis, isFree, implicitEdge, implicitCornerColor, maxSrcCoord, maxSrcColor, rangeX, rangeY, colRange, ctm, xform, numP);
/*     */ 
/* 132 */         if (current == null)
/*     */         {
/*     */           break;
/*     */         }
/* 136 */         list.add(current);
/* 137 */         flag = (byte)(int)(mciis.readBits(this.bitsPerFlag) & 0x3);
/* 138 */         switch (flag)
/*     */         {
/*     */         case 0:
/* 141 */           break;
/*     */         case 1:
/* 143 */           implicitEdge = current.getFlag1Edge();
/* 144 */           implicitCornerColor = current.getFlag1Color();
/* 145 */           break;
/*     */         case 2:
/* 147 */           implicitEdge = current.getFlag2Edge();
/* 148 */           implicitCornerColor = current.getFlag2Color();
/* 149 */           break;
/*     */         case 3:
/* 151 */           implicitEdge = current.getFlag3Edge();
/* 152 */           implicitCornerColor = current.getFlag3Color();
/* 153 */           break;
/*     */         default:
/* 155 */           LOG.warn("bad flag: " + flag);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (EOFException ex)
/*     */     {
/* 164 */       mciis.close();
/* 165 */     }return list;
/*     */   }
/*     */ 
/*     */   protected Patch readPatch(ImageInputStream input, boolean isFree, Point2D[] implicitEdge, float[][] implicitCornerColor, long maxSrcCoord, long maxSrcColor, PDRange rangeX, PDRange rangeY, PDRange[] colRange, Matrix ctm, AffineTransform xform, int numP)
/*     */     throws IOException
/*     */   {
/* 196 */     float[][] color = new float[4][this.numberOfColorComponents];
/* 197 */     Point2D[] points = new Point2D[numP];
/* 198 */     int pStart = 4; int cStart = 2;
/* 199 */     if (isFree)
/*     */     {
/* 201 */       pStart = 0;
/* 202 */       cStart = 0;
/*     */     }
/*     */     else
/*     */     {
/* 206 */       points[0] = implicitEdge[0];
/* 207 */       points[1] = implicitEdge[1];
/* 208 */       points[2] = implicitEdge[2];
/* 209 */       points[3] = implicitEdge[3];
/*     */ 
/* 211 */       for (int i = 0; i < this.numberOfColorComponents; i++)
/*     */       {
/* 213 */         color[0][i] = implicitCornerColor[0][i];
/* 214 */         color[1][i] = implicitCornerColor[1][i];
/*     */       }
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 220 */       for (int i = pStart; i < numP; i++)
/*     */       {
/* 222 */         long x = input.readBits(this.bitsPerCoordinate);
/* 223 */         long y = input.readBits(this.bitsPerCoordinate);
/* 224 */         double px = interpolate(x, maxSrcCoord, rangeX.getMin(), rangeX.getMax());
/* 225 */         double py = interpolate(y, maxSrcCoord, rangeY.getMin(), rangeY.getMax());
/* 226 */         Point2D tmp = new Point2D.Double(px, py);
/* 227 */         transformPoint(tmp, ctm, xform);
/* 228 */         points[i] = tmp;
/*     */       }
/* 230 */       for (int i = cStart; i < 4; i++)
/*     */       {
/* 232 */         for (int j = 0; j < this.numberOfColorComponents; j++)
/*     */         {
/* 234 */           long c = input.readBits(this.bitsPerColorComponent);
/* 235 */           color[i][j] = ((float)interpolate(c, maxSrcColor, colRange[j].getMin(), colRange[j].getMax()));
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (EOFException ex)
/*     */     {
/* 241 */       LOG.debug("EOF");
/* 242 */       return null;
/*     */     }
/* 244 */     return generatePatch(points, color);
/*     */   }
/*     */ 
/*     */   abstract Patch generatePatch(Point2D[] paramArrayOfPoint2D, float[][] paramArrayOfFloat);
/*     */ 
/*     */   private double interpolate(double x, long maxValue, float rangeMin, float rangeMax)
/*     */   {
/* 261 */     return rangeMin + x / maxValue * (rangeMax - rangeMin);
/*     */   }
/*     */ 
/*     */   protected HashMap<Point, Integer> calcPixelTable()
/*     */   {
/* 272 */     HashMap map = new HashMap();
/* 273 */     for (Patch it : this.patchList)
/*     */     {
/* 275 */       super.calcPixelTable(it.listOfTriangles, map);
/*     */     }
/* 277 */     return map;
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/* 285 */     this.patchList = null;
/* 286 */     this.outputColorModel = null;
/* 287 */     this.shadingColorSpace = null;
/* 288 */     this.shadingTinttransform = null;
/*     */   }
/*     */ 
/*     */   public final ColorModel getColorModel()
/*     */   {
/* 296 */     return this.outputColorModel;
/*     */   }
/*     */ 
/*     */   public final Raster getRaster(int x, int y, int w, int h)
/*     */   {
/* 304 */     WritableRaster raster = getColorModel().createCompatibleWritableRaster(w, h);
/* 305 */     int[] data = new int[w * h * 4];
/* 306 */     if ((!this.patchList.isEmpty()) || (this.background != null))
/*     */     {
/* 308 */       for (int row = 0; row < h; row++)
/*     */       {
/* 310 */         int currentY = y + row;
/* 311 */         if ((this.bboxRect == null) || (
/* 313 */           (currentY >= this.minBBoxY) && (currentY <= this.maxBBoxY)))
/*     */         {
/* 318 */           for (int col = 0; col < w; col++)
/*     */           {
/* 320 */             int currentX = x + col;
/* 321 */             if ((this.bboxRect == null) || (
/* 323 */               (currentX >= this.minBBoxX) && (currentX <= this.maxBBoxX)))
/*     */             {
/* 328 */               Point p = new Point(x + col, y + row);
/*     */               int value;
/*     */               int value;
/* 330 */               if (this.pixelTable.containsKey(p))
/*     */               {
/* 332 */                 value = ((Integer)this.pixelTable.get(p)).intValue();
/*     */               }
/*     */               else
/*     */               {
/* 336 */                 if (this.background == null)
/*     */                   continue;
/* 338 */                 value = this.rgbBackground;
/*     */               }
/*     */ 
/* 345 */               int index = (row * w + col) * 4;
/* 346 */               data[index] = (value & 0xFF);
/* 347 */               value >>= 8;
/* 348 */               data[(index + 1)] = (value & 0xFF);
/* 349 */               value >>= 8;
/* 350 */               data[(index + 2)] = (value & 0xFF);
/* 351 */               data[(index + 3)] = 255;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 355 */     raster.setPixels(0, 0, w, h, data);
/* 356 */     return raster;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.shading.PatchMeshesShadingContext
 * JD-Core Version:    0.6.2
 */