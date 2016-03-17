/*     */ package com.itextpdf.awt.geom;
/*     */ 
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Line2D.Double;
/*     */ import java.awt.geom.PathIterator;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.geom.Rectangle2D.Double;
/*     */ 
/*     */ public class PolylineShape
/*     */   implements Shape
/*     */ {
/*     */   protected int[] x;
/*     */   protected int[] y;
/*     */   protected int np;
/*     */ 
/*     */   public PolylineShape(int[] x, int[] y, int nPoints)
/*     */   {
/*  71 */     this.np = nPoints;
/*     */ 
/*  73 */     this.x = new int[this.np];
/*  74 */     this.y = new int[this.np];
/*  75 */     System.arraycopy(x, 0, this.x, 0, this.np);
/*  76 */     System.arraycopy(y, 0, this.y, 0, this.np);
/*     */   }
/*     */ 
/*     */   public Rectangle2D getBounds2D()
/*     */   {
/*  87 */     int[] r = rect();
/*  88 */     return r == null ? null : new Rectangle2D.Double(r[0], r[1], r[2], r[3]);
/*     */   }
/*     */ 
/*     */   public Rectangle getBounds()
/*     */   {
/*  96 */     return getBounds2D().getBounds();
/*     */   }
/*     */ 
/*     */   private int[] rect()
/*     */   {
/* 105 */     if (this.np == 0) return null;
/* 106 */     int xMin = this.x[0]; int yMin = this.y[0]; int xMax = this.x[0]; int yMax = this.y[0];
/*     */ 
/* 108 */     for (int i = 1; i < this.np; i++) {
/* 109 */       if (this.x[i] < xMin) xMin = this.x[i];
/* 110 */       else if (this.x[i] > xMax) xMax = this.x[i];
/* 111 */       if (this.y[i] < yMin) yMin = this.y[i];
/* 112 */       else if (this.y[i] > yMax) yMax = this.y[i];
/*     */     }
/*     */ 
/* 115 */     return new int[] { xMin, yMin, xMax - xMin, yMax - yMin };
/*     */   }
/*     */ 
/*     */   public boolean contains(double x, double y)
/*     */   {
/* 122 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean contains(Point2D p)
/*     */   {
/* 128 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean contains(double x, double y, double w, double h)
/*     */   {
/* 134 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean contains(Rectangle2D r)
/*     */   {
/* 140 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean intersects(double x, double y, double w, double h)
/*     */   {
/* 148 */     return intersects(new Rectangle2D.Double(x, y, w, h));
/*     */   }
/*     */ 
/*     */   public boolean intersects(Rectangle2D r)
/*     */   {
/* 157 */     if (this.np == 0) return false;
/* 158 */     Line2D line = new Line2D.Double(this.x[0], this.y[0], this.x[0], this.y[0]);
/* 159 */     for (int i = 1; i < this.np; i++) {
/* 160 */       line.setLine(this.x[(i - 1)], this.y[(i - 1)], this.x[i], this.y[i]);
/* 161 */       if (line.intersects(r)) return true;
/*     */     }
/* 163 */     return false;
/*     */   }
/*     */ 
/*     */   public PathIterator getPathIterator(AffineTransform at)
/*     */   {
/* 173 */     return new PolylineShapeIterator(this, at);
/*     */   }
/*     */ 
/*     */   public PathIterator getPathIterator(AffineTransform at, double flatness)
/*     */   {
/* 181 */     return new PolylineShapeIterator(this, at);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.awt.geom.PolylineShape
 * JD-Core Version:    0.6.2
 */