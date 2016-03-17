/*     */ package org.apache.pdfbox.pdmodel.graphics.shading;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.util.HashSet;
/*     */ 
/*     */ class ShadedTriangle
/*     */ {
/*     */   protected final Point2D[] corner;
/*     */   protected final float[][] color;
/*     */   private final double area;
/*     */   private final int degree;
/*     */   private final Line line;
/*     */   private final double v0;
/*     */   private final double v1;
/*     */   private final double v2;
/*     */ 
/*     */   public ShadedTriangle(Point2D[] p, float[][] c)
/*     */   {
/*  59 */     this.corner = ((Point2D[])p.clone());
/*  60 */     this.color = ((float[][])c.clone());
/*  61 */     this.area = getArea(p[0], p[1], p[2]);
/*  62 */     this.degree = calcDeg(p);
/*     */ 
/*  64 */     if (this.degree == 2)
/*     */     {
/*  66 */       if ((overlaps(this.corner[1], this.corner[2])) && (!overlaps(this.corner[0], this.corner[2])))
/*     */       {
/*  68 */         Point p0 = new Point((int)Math.round(this.corner[0].getX()), (int)Math.round(this.corner[0].getY()));
/*     */ 
/*  70 */         Point p1 = new Point((int)Math.round(this.corner[2].getX()), (int)Math.round(this.corner[2].getY()));
/*     */ 
/*  72 */         this.line = new Line(p0, p1, this.color[0], this.color[2]);
/*     */       }
/*     */       else
/*     */       {
/*  76 */         Point p0 = new Point((int)Math.round(this.corner[1].getX()), (int)Math.round(this.corner[1].getY()));
/*     */ 
/*  78 */         Point p1 = new Point((int)Math.round(this.corner[2].getX()), (int)Math.round(this.corner[2].getY()));
/*     */ 
/*  80 */         this.line = new Line(p0, p1, this.color[1], this.color[2]);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*  85 */       this.line = null;
/*     */     }
/*     */ 
/*  88 */     this.v0 = edgeEquationValue(p[0], p[1], p[2]);
/*  89 */     this.v1 = edgeEquationValue(p[1], p[2], p[0]);
/*  90 */     this.v2 = edgeEquationValue(p[2], p[0], p[1]);
/*     */   }
/*     */ 
/*     */   private int calcDeg(Point2D[] p)
/*     */   {
/* 102 */     HashSet set = new HashSet();
/* 103 */     for (Point2D itp : p)
/*     */     {
/* 105 */       Point np = new Point((int)Math.round(itp.getX() * 1000.0D), (int)Math.round(itp.getY() * 1000.0D));
/* 106 */       set.add(np);
/*     */     }
/* 108 */     return set.size();
/*     */   }
/*     */ 
/*     */   public int getDeg()
/*     */   {
/* 113 */     return this.degree;
/*     */   }
/*     */ 
/*     */   public int[] getBoundary()
/*     */   {
/* 123 */     int[] boundary = new int[4];
/* 124 */     int x0 = (int)Math.round(this.corner[0].getX());
/* 125 */     int x1 = (int)Math.round(this.corner[1].getX());
/* 126 */     int x2 = (int)Math.round(this.corner[2].getX());
/* 127 */     int y0 = (int)Math.round(this.corner[0].getY());
/* 128 */     int y1 = (int)Math.round(this.corner[1].getY());
/* 129 */     int y2 = (int)Math.round(this.corner[2].getY());
/*     */ 
/* 131 */     boundary[0] = Math.min(Math.min(x0, x1), x2);
/* 132 */     boundary[1] = Math.max(Math.max(x0, x1), x2);
/* 133 */     boundary[2] = Math.min(Math.min(y0, y1), y2);
/* 134 */     boundary[3] = Math.max(Math.max(y0, y1), y2);
/*     */ 
/* 136 */     return boundary;
/*     */   }
/*     */ 
/*     */   public Line getLine()
/*     */   {
/* 146 */     return this.line;
/*     */   }
/*     */ 
/*     */   public boolean contains(Point2D p)
/*     */   {
/* 157 */     if (this.degree == 1)
/*     */     {
/* 159 */       return overlaps(this.corner[0], p) | overlaps(this.corner[1], p) | overlaps(this.corner[2], p);
/*     */     }
/* 161 */     if (this.degree == 2)
/*     */     {
/* 163 */       Point tp = new Point((int)Math.round(p.getX()), (int)Math.round(p.getY()));
/* 164 */       return this.line.linePoints.contains(tp);
/*     */     }
/*     */ 
/* 171 */     double pv0 = edgeEquationValue(p, this.corner[1], this.corner[2]);
/*     */ 
/* 176 */     if (pv0 * this.v0 < 0.0D)
/*     */     {
/* 178 */       return false;
/*     */     }
/* 180 */     double pv1 = edgeEquationValue(p, this.corner[2], this.corner[0]);
/*     */ 
/* 185 */     if (pv1 * this.v1 < 0.0D)
/*     */     {
/* 187 */       return false;
/*     */     }
/* 189 */     double pv2 = edgeEquationValue(p, this.corner[0], this.corner[1]);
/*     */ 
/* 195 */     return pv2 * this.v2 >= 0.0D;
/*     */   }
/*     */ 
/*     */   private boolean overlaps(Point2D p0, Point2D p1)
/*     */   {
/* 204 */     return (Math.abs(p0.getX() - p1.getX()) < 0.001D) && (Math.abs(p0.getY() - p1.getY()) < 0.001D);
/*     */   }
/*     */ 
/*     */   private double edgeEquationValue(Point2D p, Point2D p1, Point2D p2)
/*     */   {
/* 214 */     return (p2.getY() - p1.getY()) * (p.getX() - p1.getX()) - (p2.getX() - p1.getX()) * (p.getY() - p1.getY());
/*     */   }
/*     */ 
/*     */   private double getArea(Point2D a, Point2D b, Point2D c)
/*     */   {
/* 221 */     return Math.abs((c.getX() - b.getX()) * (c.getY() - a.getY()) - (c.getX() - a.getX()) * (c.getY() - b.getY())) / 2.0D;
/*     */   }
/*     */ 
/*     */   public float[] calcColor(Point2D p)
/*     */   {
/* 233 */     int numberOfColorComponents = this.color[0].length;
/* 234 */     float[] pCol = new float[numberOfColorComponents];
/*     */ 
/* 236 */     if (this.degree == 1)
/*     */     {
/* 238 */       for (int i = 0; i < numberOfColorComponents; i++)
/*     */       {
/* 241 */         pCol[i] = ((this.color[0][i] + this.color[1][i] + this.color[2][i]) / 3.0F);
/*     */       }
/*     */     } else {
/* 244 */       if (this.degree == 2)
/*     */       {
/* 247 */         Point tp = new Point((int)Math.round(p.getX()), (int)Math.round(p.getY()));
/* 248 */         return this.line.calcColor(tp);
/*     */       }
/*     */ 
/* 252 */       float aw = (float)(getArea(p, this.corner[1], this.corner[2]) / this.area);
/* 253 */       float bw = (float)(getArea(p, this.corner[2], this.corner[0]) / this.area);
/* 254 */       float cw = (float)(getArea(p, this.corner[0], this.corner[1]) / this.area);
/* 255 */       for (int i = 0; i < numberOfColorComponents; i++)
/*     */       {
/* 258 */         pCol[i] = (this.color[0][i] * aw + this.color[1][i] * bw + this.color[2][i] * cw);
/*     */       }
/*     */     }
/* 261 */     return pCol;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 267 */     return this.corner[0] + " " + this.corner[1] + " " + this.corner[2];
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.shading.ShadedTriangle
 * JD-Core Version:    0.6.2
 */