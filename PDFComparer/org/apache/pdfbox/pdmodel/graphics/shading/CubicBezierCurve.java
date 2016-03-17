/*     */ package org.apache.pdfbox.pdmodel.graphics.shading;
/*     */ 
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Point2D.Double;
/*     */ 
/*     */ class CubicBezierCurve
/*     */ {
/*     */   protected final Point2D[] controlPoints;
/*     */   private final int level;
/*     */   private final Point2D[] curve;
/*     */ 
/*     */   public CubicBezierCurve(Point2D[] ctrlPnts, int l)
/*     */   {
/*  43 */     this.controlPoints = ((Point2D[])ctrlPnts.clone());
/*  44 */     this.level = l;
/*  45 */     this.curve = getPoints(this.level);
/*     */   }
/*     */ 
/*     */   public int getLevel()
/*     */   {
/*  55 */     return this.level;
/*     */   }
/*     */ 
/*     */   private Point2D[] getPoints(int l)
/*     */   {
/*  61 */     if (l < 0)
/*     */     {
/*  63 */       l = 0;
/*     */     }
/*  65 */     int sz = (1 << l) + 1;
/*  66 */     Point2D[] res = new Point2D[sz];
/*  67 */     double step = 1.0D / (sz - 1);
/*  68 */     double t = -step;
/*  69 */     for (int i = 0; i < sz; i++)
/*     */     {
/*  71 */       t += step;
/*  72 */       double tmpX = (1.0D - t) * (1.0D - t) * (1.0D - t) * this.controlPoints[0].getX() + 3.0D * t * (1.0D - t) * (1.0D - t) * this.controlPoints[1].getX() + 3.0D * t * t * (1.0D - t) * this.controlPoints[2].getX() + t * t * t * this.controlPoints[3].getX();
/*     */ 
/*  76 */       double tmpY = (1.0D - t) * (1.0D - t) * (1.0D - t) * this.controlPoints[0].getY() + 3.0D * t * (1.0D - t) * (1.0D - t) * this.controlPoints[1].getY() + 3.0D * t * t * (1.0D - t) * this.controlPoints[2].getY() + t * t * t * this.controlPoints[3].getY();
/*     */ 
/*  80 */       res[i] = new Point2D.Double(tmpX, tmpY);
/*     */     }
/*  82 */     return res;
/*     */   }
/*     */ 
/*     */   public Point2D[] getCubicBezierCurve()
/*     */   {
/*  92 */     return this.curve;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  98 */     String pointStr = "";
/*  99 */     for (Point2D p : this.controlPoints)
/*     */     {
/* 101 */       if (pointStr.length() > 0)
/*     */       {
/* 103 */         pointStr = pointStr + " ";
/*     */       }
/* 105 */       pointStr = pointStr + p;
/*     */     }
/* 107 */     return "Cubic Bezier curve{control points p0, p1, p2, p3: " + pointStr + "}";
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.shading.CubicBezierCurve
 * JD-Core Version:    0.6.2
 */