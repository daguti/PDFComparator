/*     */ package org.apache.pdfbox.pdmodel.graphics.shading;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDSeparation;
/*     */ import org.apache.pdfbox.util.Matrix;
/*     */ 
/*     */ abstract class TriangleBasedShadingContext extends ShadingContext
/*     */ {
/*  41 */   private static final Log LOG = LogFactory.getLog(TriangleBasedShadingContext.class);
/*     */   protected int bitsPerCoordinate;
/*     */   protected int numberOfColorComponents;
/*     */   protected int bitsPerColorComponent;
/*     */   protected final boolean hasFunction;
/*     */ 
/*     */   public TriangleBasedShadingContext(PDShadingResources shading, ColorModel cm, AffineTransform xform, Matrix ctm, int pageHeight, Rectangle dBounds)
/*     */     throws IOException
/*     */   {
/*  64 */     super(shading, cm, xform, ctm, pageHeight, dBounds);
/*  65 */     PDTriangleBasedShadingType triangleBasedShadingType = (PDTriangleBasedShadingType)shading;
/*  66 */     this.hasFunction = (shading.getFunction() != null);
/*  67 */     this.bitsPerCoordinate = triangleBasedShadingType.getBitsPerCoordinate();
/*  68 */     LOG.debug("bitsPerCoordinate: " + (Math.pow(2.0D, this.bitsPerCoordinate) - 1.0D));
/*  69 */     this.bitsPerColorComponent = triangleBasedShadingType.getBitsPerComponent();
/*  70 */     LOG.debug("bitsPerColorComponent: " + this.bitsPerColorComponent);
/*  71 */     this.numberOfColorComponents = (this.hasFunction ? 1 : this.colorSpace.getNumberOfComponents());
/*  72 */     if ((this.colorSpace instanceof PDSeparation))
/*     */     {
/*  76 */       this.numberOfColorComponents = 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void calcPixelTable(ArrayList<ShadedTriangle> triangleList, HashMap<Point, Integer> map)
/*     */   {
/*  84 */     for (ShadedTriangle tri : triangleList)
/*     */     {
/*  86 */       int degree = tri.getDeg();
/*     */       Line line;
/*  87 */       if (degree == 2)
/*     */       {
/*  89 */         line = tri.getLine();
/*  90 */         for (Point p : line.linePoints)
/*     */         {
/*  92 */           map.put(p, Integer.valueOf(convertToRGB(line.calcColor(p))));
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/*  97 */         int[] boundary = tri.getBoundary();
/*  98 */         boundary[0] = Math.max(boundary[0], this.deviceBounds.x);
/*  99 */         boundary[1] = Math.min(boundary[1], this.deviceBounds.x + this.deviceBounds.width);
/* 100 */         boundary[2] = Math.max(boundary[2], this.deviceBounds.y);
/* 101 */         boundary[3] = Math.min(boundary[3], this.deviceBounds.y + this.deviceBounds.height);
/* 102 */         for (int x = boundary[0]; x <= boundary[1]; x++)
/*     */         {
/* 104 */           for (int y = boundary[2]; y <= boundary[3]; y++)
/*     */           {
/* 106 */             Point p = new Point(x, y);
/* 107 */             if (tri.contains(p))
/*     */             {
/* 109 */               map.put(p, Integer.valueOf(convertToRGB(tri.calcColor(p))));
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void transformPoint(Point2D p, Matrix ctm, AffineTransform xform)
/*     */   {
/* 120 */     if (ctm != null)
/*     */     {
/* 122 */       ctm.createAffineTransform().transform(p, p);
/*     */     }
/* 124 */     xform.transform(p, p);
/*     */   }
/*     */ 
/*     */   protected int convertToRGB(float[] values)
/*     */   {
/* 133 */     if (this.hasFunction)
/*     */     {
/*     */       try
/*     */       {
/* 137 */         values = this.shading.evalFunction(values);
/*     */       }
/*     */       catch (IOException exception)
/*     */       {
/* 141 */         LOG.error("error while processing a function", exception);
/*     */       }
/*     */     }
/* 144 */     return super.convertToRGB(values);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.shading.TriangleBasedShadingContext
 * JD-Core Version:    0.6.2
 */