/*     */ package org.apache.pdfbox.pdmodel.graphics.shading;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.util.HashSet;
/*     */ 
/*     */ class Line
/*     */ {
/*     */   private final Point point0;
/*     */   private final Point point1;
/*     */   private final float[] color0;
/*     */   private final float[] color1;
/*     */   protected final HashSet<Point> linePoints;
/*     */ 
/*     */   public Line(Point p0, Point p1, float[] c0, float[] c1)
/*     */   {
/*  46 */     this.point0 = p0;
/*  47 */     this.point1 = p1;
/*  48 */     this.color0 = ((float[])c0.clone());
/*  49 */     this.color1 = ((float[])c1.clone());
/*  50 */     this.linePoints = calcLine(this.point0.x, this.point0.y, this.point1.x, this.point1.y);
/*     */   }
/*     */ 
/*     */   private HashSet<Point> calcLine(int x0, int y0, int x1, int y1)
/*     */   {
/*  67 */     HashSet points = new HashSet(3);
/*  68 */     int dx = Math.round(Math.abs(x1 - x0));
/*  69 */     int dy = Math.round(Math.abs(y1 - y0));
/*  70 */     int sx = x0 < x1 ? 1 : -1;
/*  71 */     int sy = y0 < y1 ? 1 : -1;
/*  72 */     int err = dx - dy;
/*     */     while (true)
/*     */     {
/*  75 */       points.add(new Point(x0, y0));
/*  76 */       if ((x0 == x1) && (y0 == y1))
/*     */       {
/*     */         break;
/*     */       }
/*  80 */       int e2 = 2 * err;
/*  81 */       if (e2 > -dy)
/*     */       {
/*  83 */         err -= dy;
/*  84 */         x0 += sx;
/*     */       }
/*  86 */       if (e2 < dx)
/*     */       {
/*  88 */         err += dx;
/*  89 */         y0 += sy;
/*     */       }
/*     */     }
/*  92 */     return points;
/*     */   }
/*     */ 
/*     */   protected float[] calcColor(Point p)
/*     */   {
/* 104 */     int numberOfColorComponents = this.color0.length;
/* 105 */     float[] pc = new float[numberOfColorComponents];
/* 106 */     if ((this.point0.x == this.point1.x) && (this.point0.y == this.point1.y))
/*     */     {
/* 108 */       return this.color0;
/*     */     }
/* 110 */     if (this.point0.x == this.point1.x)
/*     */     {
/* 112 */       float l = this.point1.y - this.point0.y;
/* 113 */       for (int i = 0; i < numberOfColorComponents; i++)
/*     */       {
/* 115 */         pc[i] = (this.color0[i] * (this.point1.y - p.y) / l + this.color1[i] * (p.y - this.point0.y) / l);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 121 */       float l = this.point1.x - this.point0.x;
/* 122 */       for (int i = 0; i < numberOfColorComponents; i++)
/*     */       {
/* 124 */         pc[i] = (this.color0[i] * (this.point1.x - p.x) / l + this.color1[i] * (p.x - this.point0.x) / l);
/*     */       }
/*     */     }
/*     */ 
/* 128 */     return pc;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.shading.Line
 * JD-Core Version:    0.6.2
 */