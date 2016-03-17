/*     */ package com.itextpdf.text.pdf.parser;
/*     */ 
/*     */ import com.itextpdf.awt.geom.Rectangle2D.Float;
/*     */ 
/*     */ public class LineSegment
/*     */ {
/*     */   private final Vector startPoint;
/*     */   private final Vector endPoint;
/*     */ 
/*     */   public LineSegment(Vector startPoint, Vector endPoint)
/*     */   {
/*  66 */     this.startPoint = startPoint;
/*  67 */     this.endPoint = endPoint;
/*     */   }
/*     */ 
/*     */   public Vector getStartPoint()
/*     */   {
/*  74 */     return this.startPoint;
/*     */   }
/*     */ 
/*     */   public Vector getEndPoint()
/*     */   {
/*  81 */     return this.endPoint;
/*     */   }
/*     */ 
/*     */   public float getLength()
/*     */   {
/*  89 */     return this.endPoint.subtract(this.startPoint).length();
/*     */   }
/*     */ 
/*     */   public Rectangle2D.Float getBoundingRectange()
/*     */   {
/* 101 */     float x1 = getStartPoint().get(0);
/* 102 */     float y1 = getStartPoint().get(1);
/* 103 */     float x2 = getEndPoint().get(0);
/* 104 */     float y2 = getEndPoint().get(1);
/* 105 */     return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
/*     */   }
/*     */ 
/*     */   public LineSegment transformBy(Matrix m)
/*     */   {
/* 115 */     Vector newStart = this.startPoint.cross(m);
/* 116 */     Vector newEnd = this.endPoint.cross(m);
/* 117 */     return new LineSegment(newStart, newEnd);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.LineSegment
 * JD-Core Version:    0.6.2
 */