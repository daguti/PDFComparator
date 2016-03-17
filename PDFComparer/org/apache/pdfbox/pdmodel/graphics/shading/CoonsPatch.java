/*     */ package org.apache.pdfbox.pdmodel.graphics.shading;
/*     */ 
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Point2D.Double;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ class CoonsPatch extends Patch
/*     */ {
/*     */   protected CoonsPatch(Point2D[] points, float[][] color)
/*     */   {
/*  37 */     super(points, color);
/*  38 */     this.controlPoints = reshapeControlPoints(points);
/*  39 */     this.level = calcLevel();
/*  40 */     this.listOfTriangles = getTriangles();
/*     */   }
/*     */ 
/*     */   private Point2D[][] reshapeControlPoints(Point2D[] points)
/*     */   {
/*  46 */     Point2D[][] fourRows = new Point2D[4][4];
/*  47 */     fourRows[2] = { points[0], points[1], points[2], points[3] };
/*     */ 
/*  51 */     fourRows[1] = { points[3], points[4], points[5], points[6] };
/*     */ 
/*  55 */     fourRows[3] = { points[9], points[8], points[7], points[6] };
/*     */ 
/*  59 */     fourRows[0] = { points[0], points[11], points[10], points[9] };
/*     */ 
/*  63 */     return fourRows;
/*     */   }
/*     */ 
/*     */   private int[] calcLevel()
/*     */   {
/*  69 */     int[] l = { 4, 4 };
/*     */ 
/*  74 */     if ((isEdgeALine(this.controlPoints[0]) & isEdgeALine(this.controlPoints[1])))
/*     */     {
/*  76 */       double lc1 = getLen(this.controlPoints[0][0], this.controlPoints[0][3]);
/*  77 */       double lc2 = getLen(this.controlPoints[1][0], this.controlPoints[1][3]);
/*     */ 
/*  79 */       if ((lc1 <= 800.0D) && (lc2 <= 800.0D))
/*     */       {
/*  82 */         if ((lc1 > 400.0D) || (lc2 > 400.0D))
/*     */         {
/*  84 */           l[0] = 3;
/*     */         }
/*  86 */         else if ((lc1 > 200.0D) || (lc2 > 200.0D))
/*     */         {
/*  88 */           l[0] = 2;
/*     */         }
/*     */         else
/*     */         {
/*  92 */           l[0] = 1;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  97 */     if ((isEdgeALine(this.controlPoints[2]) & isEdgeALine(this.controlPoints[3])))
/*     */     {
/*  99 */       double ld1 = getLen(this.controlPoints[2][0], this.controlPoints[2][3]);
/* 100 */       double ld2 = getLen(this.controlPoints[3][0], this.controlPoints[3][3]);
/* 101 */       if ((ld1 <= 800.0D) && (ld2 <= 800.0D))
/*     */       {
/* 104 */         if ((ld1 > 400.0D) || (ld2 > 400.0D))
/*     */         {
/* 106 */           l[1] = 3;
/*     */         }
/* 108 */         else if ((ld1 > 200.0D) || (ld2 > 200.0D))
/*     */         {
/* 110 */           l[1] = 2;
/*     */         }
/*     */         else
/*     */         {
/* 114 */           l[1] = 1;
/*     */         }
/*     */       }
/*     */     }
/* 117 */     return l;
/*     */   }
/*     */ 
/*     */   private ArrayList<ShadedTriangle> getTriangles()
/*     */   {
/* 124 */     CubicBezierCurve eC1 = new CubicBezierCurve(this.controlPoints[0], this.level[0]);
/* 125 */     CubicBezierCurve eC2 = new CubicBezierCurve(this.controlPoints[1], this.level[0]);
/* 126 */     CubicBezierCurve eD1 = new CubicBezierCurve(this.controlPoints[2], this.level[1]);
/* 127 */     CubicBezierCurve eD2 = new CubicBezierCurve(this.controlPoints[3], this.level[1]);
/* 128 */     CoordinateColorPair[][] patchCC = getPatchCoordinatesColor(eC1, eC2, eD1, eD2);
/* 129 */     return getShadedTriangles(patchCC);
/*     */   }
/*     */ 
/*     */   protected Point2D[] getFlag1Edge()
/*     */   {
/* 135 */     return (Point2D[])this.controlPoints[1].clone();
/*     */   }
/*     */ 
/*     */   protected Point2D[] getFlag2Edge()
/*     */   {
/* 141 */     Point2D[] implicitEdge = new Point2D[4];
/* 142 */     implicitEdge[0] = this.controlPoints[3][3];
/* 143 */     implicitEdge[1] = this.controlPoints[3][2];
/* 144 */     implicitEdge[2] = this.controlPoints[3][1];
/* 145 */     implicitEdge[3] = this.controlPoints[3][0];
/* 146 */     return implicitEdge;
/*     */   }
/*     */ 
/*     */   protected Point2D[] getFlag3Edge()
/*     */   {
/* 152 */     Point2D[] implicitEdge = new Point2D[4];
/* 153 */     implicitEdge[0] = this.controlPoints[0][3];
/* 154 */     implicitEdge[1] = this.controlPoints[0][2];
/* 155 */     implicitEdge[2] = this.controlPoints[0][1];
/* 156 */     implicitEdge[3] = this.controlPoints[0][0];
/* 157 */     return implicitEdge;
/*     */   }
/*     */ 
/*     */   private CoordinateColorPair[][] getPatchCoordinatesColor(CubicBezierCurve C1, CubicBezierCurve C2, CubicBezierCurve D1, CubicBezierCurve D2)
/*     */   {
/* 167 */     Point2D[] curveC1 = C1.getCubicBezierCurve();
/* 168 */     Point2D[] curveC2 = C2.getCubicBezierCurve();
/* 169 */     Point2D[] curveD1 = D1.getCubicBezierCurve();
/* 170 */     Point2D[] curveD2 = D2.getCubicBezierCurve();
/*     */ 
/* 172 */     int numberOfColorComponents = this.cornerColor[0].length;
/* 173 */     int szV = curveD1.length;
/* 174 */     int szU = curveC1.length;
/*     */ 
/* 176 */     CoordinateColorPair[][] patchCC = new CoordinateColorPair[szV][szU];
/*     */ 
/* 178 */     double stepV = 1.0D / (szV - 1);
/* 179 */     double stepU = 1.0D / (szU - 1);
/* 180 */     double v = -stepV;
/* 181 */     for (int i = 0; i < szV; i++)
/*     */     {
/* 184 */       v += stepV;
/* 185 */       double u = -stepU;
/* 186 */       for (int j = 0; j < szU; j++)
/*     */       {
/* 188 */         u += stepU;
/* 189 */         double scx = (1.0D - v) * curveC1[j].getX() + v * curveC2[j].getX();
/* 190 */         double scy = (1.0D - v) * curveC1[j].getY() + v * curveC2[j].getY();
/* 191 */         double sdx = (1.0D - u) * curveD1[i].getX() + u * curveD2[i].getX();
/* 192 */         double sdy = (1.0D - u) * curveD1[i].getY() + u * curveD2[i].getY();
/* 193 */         double sbx = (1.0D - v) * ((1.0D - u) * this.controlPoints[0][0].getX() + u * this.controlPoints[0][3].getX()) + v * ((1.0D - u) * this.controlPoints[1][0].getX() + u * this.controlPoints[1][3].getX());
/*     */ 
/* 195 */         double sby = (1.0D - v) * ((1.0D - u) * this.controlPoints[0][0].getY() + u * this.controlPoints[0][3].getY()) + v * ((1.0D - u) * this.controlPoints[1][0].getY() + u * this.controlPoints[1][3].getY());
/*     */ 
/* 198 */         double sx = scx + sdx - sbx;
/* 199 */         double sy = scy + sdy - sby;
/*     */ 
/* 202 */         Point2D tmpC = new Point2D.Double(sx, sy);
/*     */ 
/* 204 */         float[] paramSC = new float[numberOfColorComponents];
/* 205 */         for (int ci = 0; ci < numberOfColorComponents; ci++)
/*     */         {
/* 207 */           paramSC[ci] = ((float)((1.0D - v) * ((1.0D - u) * this.cornerColor[0][ci] + u * this.cornerColor[3][ci]) + v * ((1.0D - u) * this.cornerColor[1][ci] + u * this.cornerColor[2][ci])));
/*     */         }
/*     */ 
/* 210 */         patchCC[i][j] = new CoordinateColorPair(tmpC, paramSC);
/*     */       }
/*     */     }
/* 213 */     return patchCC;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.shading.CoonsPatch
 * JD-Core Version:    0.6.2
 */