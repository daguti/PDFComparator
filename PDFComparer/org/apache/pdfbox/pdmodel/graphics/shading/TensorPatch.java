/*     */ package org.apache.pdfbox.pdmodel.graphics.shading;
/*     */ 
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Point2D.Double;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ class TensorPatch extends Patch
/*     */ {
/*     */   protected TensorPatch(Point2D[] tcp, float[][] color)
/*     */   {
/*  37 */     super(tcp, color);
/*  38 */     this.controlPoints = reshapeControlPoints(tcp);
/*  39 */     this.level = calcLevel();
/*  40 */     this.listOfTriangles = getTriangles();
/*     */   }
/*     */ 
/*     */   private Point2D[][] reshapeControlPoints(Point2D[] tcp)
/*     */   {
/*  49 */     Point2D[][] square = new Point2D[4][4];
/*  50 */     for (int i = 0; i <= 3; i++)
/*     */     {
/*  52 */       square[0][i] = tcp[i];
/*  53 */       square[3][i] = tcp[(9 - i)];
/*     */     }
/*  55 */     for (int i = 1; i <= 2; i++)
/*     */     {
/*  57 */       square[i][0] = tcp[(12 - i)];
/*  58 */       square[i][2] = tcp[(12 + i)];
/*  59 */       square[i][3] = tcp[(3 + i)];
/*     */     }
/*  61 */     square[1][1] = tcp[12];
/*  62 */     square[2][1] = tcp[15];
/*  63 */     return square;
/*     */   }
/*     */ 
/*     */   private int[] calcLevel()
/*     */   {
/*  69 */     int[] l = { 4, 4 };
/*     */ 
/*  74 */     Point2D[] ctlC1 = new Point2D[4];
/*  75 */     Point2D[] ctlC2 = new Point2D[4];
/*  76 */     for (int j = 0; j < 4; j++)
/*     */     {
/*  78 */       ctlC1[j] = this.controlPoints[j][0];
/*  79 */       ctlC2[j] = this.controlPoints[j][3];
/*     */     }
/*     */ 
/*  82 */     if ((isEdgeALine(ctlC1) & isEdgeALine(ctlC2)))
/*     */     {
/*  89 */       if (!(isOnSameSideCC(this.controlPoints[1][1]) | isOnSameSideCC(this.controlPoints[1][2]) | isOnSameSideCC(this.controlPoints[2][1]) | isOnSameSideCC(this.controlPoints[2][2])))
/*     */       {
/*  97 */         double lc1 = getLen(ctlC1[0], ctlC1[3]); double lc2 = getLen(ctlC2[0], ctlC2[3]);
/*  98 */         if ((lc1 <= 800.0D) && (lc2 <= 800.0D))
/*     */         {
/* 101 */           if ((lc1 > 400.0D) || (lc2 > 400.0D))
/*     */           {
/* 103 */             l[0] = 3;
/*     */           }
/* 105 */           else if ((lc1 > 200.0D) || (lc2 > 200.0D))
/*     */           {
/* 107 */             l[0] = 2;
/*     */           }
/*     */           else
/*     */           {
/* 111 */             l[0] = 1;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 117 */     if ((isEdgeALine(this.controlPoints[0]) & isEdgeALine(this.controlPoints[3])))
/*     */     {
/* 119 */       if (!(isOnSameSideDD(this.controlPoints[1][1]) | isOnSameSideDD(this.controlPoints[1][2]) | isOnSameSideDD(this.controlPoints[2][1]) | isOnSameSideDD(this.controlPoints[2][2])))
/*     */       {
/* 126 */         double ld1 = getLen(this.controlPoints[0][0], this.controlPoints[0][3]);
/* 127 */         double ld2 = getLen(this.controlPoints[3][0], this.controlPoints[3][3]);
/* 128 */         if ((ld1 <= 800.0D) && (ld2 <= 800.0D))
/*     */         {
/* 131 */           if ((ld1 > 400.0D) || (ld2 > 400.0D))
/*     */           {
/* 133 */             l[1] = 3;
/*     */           }
/* 135 */           else if ((ld1 > 200.0D) || (ld2 > 200.0D))
/*     */           {
/* 137 */             l[1] = 2;
/*     */           }
/*     */           else
/*     */           {
/* 141 */             l[1] = 1;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 145 */     return l;
/*     */   }
/*     */ 
/*     */   private boolean isOnSameSideCC(Point2D p)
/*     */   {
/* 151 */     double cc = edgeEquationValue(p, this.controlPoints[0][0], this.controlPoints[3][0]) * edgeEquationValue(p, this.controlPoints[0][3], this.controlPoints[3][3]);
/*     */ 
/* 153 */     return cc > 0.0D;
/*     */   }
/*     */ 
/*     */   private boolean isOnSameSideDD(Point2D p)
/*     */   {
/* 159 */     double dd = edgeEquationValue(p, this.controlPoints[0][0], this.controlPoints[0][3]) * edgeEquationValue(p, this.controlPoints[3][0], this.controlPoints[3][3]);
/*     */ 
/* 161 */     return dd > 0.0D;
/*     */   }
/*     */ 
/*     */   private ArrayList<ShadedTriangle> getTriangles()
/*     */   {
/* 167 */     CoordinateColorPair[][] patchCC = getPatchCoordinatesColor();
/* 168 */     return getShadedTriangles(patchCC);
/*     */   }
/*     */ 
/*     */   protected Point2D[] getFlag1Edge()
/*     */   {
/* 174 */     Point2D[] implicitEdge = new Point2D[4];
/* 175 */     for (int i = 0; i < 4; i++)
/*     */     {
/* 177 */       implicitEdge[i] = this.controlPoints[i][3];
/*     */     }
/* 179 */     return implicitEdge;
/*     */   }
/*     */ 
/*     */   protected Point2D[] getFlag2Edge()
/*     */   {
/* 185 */     Point2D[] implicitEdge = new Point2D[4];
/* 186 */     for (int i = 0; i < 4; i++)
/*     */     {
/* 188 */       implicitEdge[i] = this.controlPoints[3][(3 - i)];
/*     */     }
/* 190 */     return implicitEdge;
/*     */   }
/*     */ 
/*     */   protected Point2D[] getFlag3Edge()
/*     */   {
/* 196 */     Point2D[] implicitEdge = new Point2D[4];
/* 197 */     for (int i = 0; i < 4; i++)
/*     */     {
/* 199 */       implicitEdge[i] = this.controlPoints[(3 - i)][0];
/*     */     }
/* 201 */     return implicitEdge;
/*     */   }
/*     */ 
/*     */   private CoordinateColorPair[][] getPatchCoordinatesColor()
/*     */   {
/* 212 */     int numberOfColorComponents = this.cornerColor[0].length;
/* 213 */     double[][] bernsteinPolyU = getBernsteinPolynomials(this.level[0]);
/* 214 */     int szU = bernsteinPolyU[0].length;
/* 215 */     double[][] bernsteinPolyV = getBernsteinPolynomials(this.level[1]);
/* 216 */     int szV = bernsteinPolyV[0].length;
/* 217 */     CoordinateColorPair[][] patchCC = new CoordinateColorPair[szV][szU];
/*     */ 
/* 219 */     double stepU = 1.0D / (szU - 1);
/* 220 */     double stepV = 1.0D / (szV - 1);
/* 221 */     double v = -stepV;
/* 222 */     for (int k = 0; k < szV; k++)
/*     */     {
/* 225 */       v += stepV;
/* 226 */       double u = -stepU;
/* 227 */       for (int l = 0; l < szU; l++)
/*     */       {
/* 229 */         double tmpx = 0.0D;
/* 230 */         double tmpy = 0.0D;
/*     */ 
/* 232 */         for (int i = 0; i < 4; i++)
/*     */         {
/* 234 */           for (int j = 0; j < 4; j++)
/*     */           {
/* 236 */             tmpx += this.controlPoints[i][j].getX() * bernsteinPolyU[i][l] * bernsteinPolyV[j][k];
/* 237 */             tmpy += this.controlPoints[i][j].getY() * bernsteinPolyU[i][l] * bernsteinPolyV[j][k];
/*     */           }
/*     */         }
/* 240 */         Point2D tmpC = new Point2D.Double(tmpx, tmpy);
/*     */ 
/* 242 */         u += stepU;
/* 243 */         float[] paramSC = new float[numberOfColorComponents];
/* 244 */         for (int ci = 0; ci < numberOfColorComponents; ci++)
/*     */         {
/* 246 */           paramSC[ci] = ((float)((1.0D - v) * ((1.0D - u) * this.cornerColor[0][ci] + u * this.cornerColor[3][ci]) + v * ((1.0D - u) * this.cornerColor[1][ci] + u * this.cornerColor[2][ci])));
/*     */         }
/*     */ 
/* 249 */         patchCC[k][l] = new CoordinateColorPair(tmpC, paramSC);
/*     */       }
/*     */     }
/* 252 */     return patchCC;
/*     */   }
/*     */ 
/*     */   private double[][] getBernsteinPolynomials(int lvl)
/*     */   {
/* 258 */     int sz = (1 << lvl) + 1;
/* 259 */     double[][] poly = new double[4][sz];
/* 260 */     double step = 1.0D / (sz - 1);
/* 261 */     double t = -step;
/* 262 */     for (int i = 0; i < sz; i++)
/*     */     {
/* 264 */       t += step;
/* 265 */       poly[0][i] = ((1.0D - t) * (1.0D - t) * (1.0D - t));
/* 266 */       poly[1][i] = (3.0D * t * (1.0D - t) * (1.0D - t));
/* 267 */       poly[2][i] = (3.0D * t * t * (1.0D - t));
/* 268 */       poly[3][i] = (t * t * t);
/*     */     }
/* 270 */     return poly;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.shading.TensorPatch
 * JD-Core Version:    0.6.2
 */