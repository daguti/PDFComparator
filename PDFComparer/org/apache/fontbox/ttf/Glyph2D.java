/*     */ package org.apache.fontbox.ttf;
/*     */ 
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ public class Glyph2D
/*     */ {
/*  34 */   private short leftSideBearing = 0;
/*  35 */   private int advanceWidth = 0;
/*     */   private Point[] points;
/*     */   private GeneralPath glyphPath;
/*     */ 
/*     */   public Glyph2D(GlyphDescription gd, short lsb, int advance)
/*     */   {
/*  48 */     this.leftSideBearing = lsb;
/*  49 */     this.advanceWidth = advance;
/*  50 */     describe(gd);
/*     */   }
/*     */ 
/*     */   public int getAdvanceWidth()
/*     */   {
/*  60 */     return this.advanceWidth;
/*     */   }
/*     */ 
/*     */   public short getLeftSideBearing()
/*     */   {
/*  70 */     return this.leftSideBearing;
/*     */   }
/*     */ 
/*     */   private void describe(GlyphDescription gd)
/*     */   {
/*  78 */     int endPtIndex = 0;
/*  79 */     this.points = new Point[gd.getPointCount()];
/*  80 */     for (int i = 0; i < gd.getPointCount(); i++)
/*     */     {
/*  82 */       boolean endPt = gd.getEndPtOfContours(endPtIndex) == i;
/*  83 */       if (endPt)
/*     */       {
/*  85 */         endPtIndex++;
/*     */       }
/*  87 */       this.points[i] = new Point(gd.getXCoordinate(i), gd.getYCoordinate(i), (gd.getFlags(i) & 0x1) != 0, endPt);
/*     */     }
/*     */   }
/*     */ 
/*     */   public GeneralPath getPath()
/*     */   {
/* 102 */     if (this.glyphPath == null)
/*     */     {
/* 104 */       this.glyphPath = calculatePath();
/*     */     }
/* 106 */     return this.glyphPath;
/*     */   }
/*     */ 
/*     */   private GeneralPath calculatePath()
/*     */   {
/* 111 */     GeneralPath path = new GeneralPath();
/* 112 */     int numberOfPoints = this.points.length;
/* 113 */     int i = 0;
/* 114 */     boolean endOfContour = true;
/* 115 */     Point startingPoint = null;
/* 116 */     Point lastCtrlPoint = null;
/* 117 */     while (i < numberOfPoints)
/*     */     {
/* 119 */       Point point = this.points[(i % numberOfPoints)];
/* 120 */       Point nextPoint1 = this.points[((i + 1) % numberOfPoints)];
/* 121 */       Point nextPoint2 = this.points[((i + 2) % numberOfPoints)];
/*     */ 
/* 123 */       if (endOfContour)
/*     */       {
/* 126 */         if (point.endOfContour)
/*     */         {
/* 128 */           i++;
/*     */         }
/*     */         else
/*     */         {
/* 132 */           path.moveTo(point.x, point.y);
/* 133 */           endOfContour = false;
/* 134 */           startingPoint = point;
/*     */         }
/*     */       }
/* 137 */       else if ((point.onCurve) && (nextPoint1.onCurve))
/*     */       {
/* 139 */         path.lineTo(nextPoint1.x, nextPoint1.y);
/* 140 */         i++;
/* 141 */         if ((point.endOfContour) || (nextPoint1.endOfContour))
/*     */         {
/* 143 */           endOfContour = true;
/* 144 */           path.closePath();
/*     */         }
/*     */ 
/*     */       }
/* 149 */       else if ((point.onCurve) && (!nextPoint1.onCurve) && (nextPoint2.onCurve))
/*     */       {
/* 151 */         if (nextPoint1.endOfContour)
/*     */         {
/* 154 */           path.quadTo(nextPoint1.x, nextPoint1.y, startingPoint.x, startingPoint.y);
/*     */         }
/*     */         else
/*     */         {
/* 158 */           path.quadTo(nextPoint1.x, nextPoint1.y, nextPoint2.x, nextPoint2.y);
/*     */         }
/* 160 */         if ((nextPoint1.endOfContour) || (nextPoint2.endOfContour))
/*     */         {
/* 162 */           endOfContour = true;
/* 163 */           path.closePath();
/*     */         }
/* 165 */         i += 2;
/* 166 */         lastCtrlPoint = nextPoint1;
/*     */       }
/* 169 */       else if ((point.onCurve) && (!nextPoint1.onCurve) && (!nextPoint2.onCurve))
/*     */       {
/* 172 */         int endPointX = midValue(nextPoint1.x, nextPoint2.x);
/* 173 */         int endPointY = midValue(nextPoint1.y, nextPoint2.y);
/* 174 */         path.quadTo(nextPoint1.x, nextPoint1.y, endPointX, endPointY);
/* 175 */         if ((point.endOfContour) || (nextPoint1.endOfContour) || (nextPoint2.endOfContour))
/*     */         {
/* 177 */           path.quadTo(nextPoint2.x, nextPoint2.y, startingPoint.x, startingPoint.y);
/* 178 */           endOfContour = true;
/* 179 */           path.closePath();
/*     */         }
/* 181 */         i += 2;
/* 182 */         lastCtrlPoint = nextPoint1;
/*     */       }
/* 185 */       else if ((!point.onCurve) && (!nextPoint1.onCurve))
/*     */       {
/* 187 */         Point2D lastEndPoint = path.getCurrentPoint();
/*     */ 
/* 189 */         lastCtrlPoint = new Point(midValue(lastCtrlPoint.x, (int)lastEndPoint.getX()), midValue(lastCtrlPoint.y, (int)lastEndPoint.getY()));
/*     */ 
/* 192 */         int endPointX = midValue((int)lastEndPoint.getX(), nextPoint1.x);
/* 193 */         int endPointY = midValue((int)lastEndPoint.getY(), nextPoint1.y);
/* 194 */         path.quadTo(lastCtrlPoint.x, lastCtrlPoint.y, endPointX, endPointY);
/* 195 */         if ((point.endOfContour) || (nextPoint1.endOfContour))
/*     */         {
/* 197 */           endOfContour = true;
/* 198 */           path.closePath();
/*     */         }
/* 200 */         i++;
/*     */       }
/* 203 */       else if ((!point.onCurve) && (nextPoint1.onCurve))
/*     */       {
/* 205 */         path.quadTo(point.x, point.y, nextPoint1.x, nextPoint1.y);
/* 206 */         if ((point.endOfContour) || (nextPoint1.endOfContour))
/*     */         {
/* 208 */           endOfContour = true;
/* 209 */           path.closePath();
/*     */         }
/* 211 */         i++;
/* 212 */         lastCtrlPoint = point;
/*     */       }
/*     */       else {
/* 215 */         System.err.println("Unknown glyph command!!");
/*     */       }
/*     */     }
/* 218 */     return path;
/*     */   }
/*     */ 
/*     */   private int midValue(int a, int b)
/*     */   {
/* 223 */     return a + (b - a) / 2;
/*     */   }
/*     */ 
/*     */   private class Point
/*     */   {
/* 233 */     public int x = 0;
/* 234 */     public int y = 0;
/* 235 */     public boolean onCurve = true;
/* 236 */     public boolean endOfContour = false;
/*     */ 
/*     */     public Point(int xValue, int yValue, boolean onCurveValue, boolean endOfContourValue)
/*     */     {
/* 240 */       this.x = xValue;
/* 241 */       this.y = yValue;
/* 242 */       this.onCurve = onCurveValue;
/* 243 */       this.endOfContour = endOfContourValue;
/*     */     }
/*     */ 
/*     */     public Point(int xValue, int yValue)
/*     */     {
/* 248 */       this(xValue, yValue, false, false);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.ttf.Glyph2D
 * JD-Core Version:    0.6.2
 */