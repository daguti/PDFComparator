/*     */ package org.apache.pdfbox.pdmodel.graphics.shading;
/*     */ 
/*     */ import java.awt.geom.Point2D;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ abstract class Patch
/*     */ {
/*     */   protected Point2D[][] controlPoints;
/*     */   protected float[][] cornerColor;
/*     */   protected int[] level;
/*     */   protected ArrayList<ShadedTriangle> listOfTriangles;
/*     */ 
/*     */   public Patch(Point2D[] ctl, float[][] color)
/*     */   {
/*  49 */     this.cornerColor = ((float[][])color.clone());
/*     */   }
/*     */ 
/*     */   protected abstract Point2D[] getFlag1Edge();
/*     */ 
/*     */   protected abstract Point2D[] getFlag2Edge();
/*     */ 
/*     */   protected abstract Point2D[] getFlag3Edge();
/*     */ 
/*     */   protected float[][] getFlag1Color()
/*     */   {
/*  80 */     int numberOfColorComponents = this.cornerColor[0].length;
/*  81 */     float[][] implicitCornerColor = new float[2][numberOfColorComponents];
/*  82 */     for (int i = 0; i < numberOfColorComponents; i++)
/*     */     {
/*  84 */       implicitCornerColor[0][i] = this.cornerColor[1][i];
/*  85 */       implicitCornerColor[1][i] = this.cornerColor[2][i];
/*     */     }
/*  87 */     return implicitCornerColor;
/*     */   }
/*     */ 
/*     */   protected float[][] getFlag2Color()
/*     */   {
/*  97 */     int numberOfColorComponents = this.cornerColor[0].length;
/*  98 */     float[][] implicitCornerColor = new float[2][numberOfColorComponents];
/*  99 */     for (int i = 0; i < numberOfColorComponents; i++)
/*     */     {
/* 101 */       implicitCornerColor[0][i] = this.cornerColor[2][i];
/* 102 */       implicitCornerColor[1][i] = this.cornerColor[3][i];
/*     */     }
/* 104 */     return implicitCornerColor;
/*     */   }
/*     */ 
/*     */   protected float[][] getFlag3Color()
/*     */   {
/* 114 */     int numberOfColorComponents = this.cornerColor[0].length;
/* 115 */     float[][] implicitCornerColor = new float[2][numberOfColorComponents];
/* 116 */     for (int i = 0; i < numberOfColorComponents; i++)
/*     */     {
/* 118 */       implicitCornerColor[0][i] = this.cornerColor[3][i];
/* 119 */       implicitCornerColor[1][i] = this.cornerColor[0][i];
/*     */     }
/* 121 */     return implicitCornerColor;
/*     */   }
/*     */ 
/*     */   protected double getLen(Point2D ps, Point2D pe)
/*     */   {
/* 133 */     double x = pe.getX() - ps.getX();
/* 134 */     double y = pe.getY() - ps.getY();
/* 135 */     return Math.sqrt(x * x + y * y);
/*     */   }
/*     */ 
/*     */   protected boolean isEdgeALine(Point2D[] ctl)
/*     */   {
/* 146 */     double ctl1 = Math.abs(edgeEquationValue(ctl[1], ctl[0], ctl[3]));
/* 147 */     double ctl2 = Math.abs(edgeEquationValue(ctl[2], ctl[0], ctl[3]));
/* 148 */     double x = Math.abs(ctl[0].getX() - ctl[3].getX());
/* 149 */     double y = Math.abs(ctl[0].getY() - ctl[3].getY());
/* 150 */     return ((ctl1 <= x) && (ctl2 <= x)) || ((ctl1 <= y) && (ctl2 <= y));
/*     */   }
/*     */ 
/*     */   protected double edgeEquationValue(Point2D p, Point2D p1, Point2D p2)
/*     */   {
/* 165 */     return (p2.getY() - p1.getY()) * (p.getX() - p1.getX()) - (p2.getX() - p1.getX()) * (p.getY() - p1.getY());
/*     */   }
/*     */ 
/*     */   protected ArrayList<ShadedTriangle> getShadedTriangles(CoordinateColorPair[][] patchCC)
/*     */   {
/* 176 */     ArrayList list = new ArrayList();
/* 177 */     int szV = patchCC.length;
/* 178 */     int szU = patchCC[0].length;
/* 179 */     for (int i = 1; i < szV; i++)
/*     */     {
/* 181 */       for (int j = 1; j < szU; j++)
/*     */       {
/* 183 */         Point2D p0 = patchCC[(i - 1)][(j - 1)].coordinate; Point2D p1 = patchCC[(i - 1)][j].coordinate; Point2D p2 = patchCC[i][j].coordinate;
/* 184 */         Point2D p3 = patchCC[i][(j - 1)].coordinate;
/* 185 */         boolean ll = true;
/* 186 */         if ((overlaps(p0, p1)) || (overlaps(p0, p3)))
/*     */         {
/* 188 */           ll = false;
/*     */         }
/*     */         else
/*     */         {
/* 193 */           Point2D[] llCorner = { p0, p1, p3 };
/*     */ 
/* 197 */           float[][] llColor = { patchCC[(i - 1)][(j - 1)].color, patchCC[(i - 1)][j].color, patchCC[i][(j - 1)].color };
/*     */ 
/* 201 */           ShadedTriangle tmpll = new ShadedTriangle(llCorner, llColor);
/* 202 */           list.add(tmpll);
/*     */         }
/* 204 */         if ((!ll) || ((!overlaps(p2, p1)) && (!overlaps(p2, p3))))
/*     */         {
/* 210 */           Point2D[] urCorner = { p3, p1, p2 };
/*     */ 
/* 214 */           float[][] urColor = { patchCC[i][(j - 1)].color, patchCC[(i - 1)][j].color, patchCC[i][j].color };
/*     */ 
/* 218 */           ShadedTriangle tmpur = new ShadedTriangle(urCorner, urColor);
/* 219 */           list.add(tmpur);
/*     */         }
/*     */       }
/*     */     }
/* 223 */     return list;
/*     */   }
/*     */ 
/*     */   private boolean overlaps(Point2D p0, Point2D p1)
/*     */   {
/* 229 */     return (Math.abs(p0.getX() - p1.getX()) < 0.001D) && (Math.abs(p0.getY() - p1.getY()) < 0.001D);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.shading.Patch
 * JD-Core Version:    0.6.2
 */