/*     */ package com.itextpdf.awt.geom;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.PathIterator;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ public class PolylineShapeIterator
/*     */   implements PathIterator
/*     */ {
/*     */   protected PolylineShape poly;
/*     */   protected AffineTransform affine;
/*     */   protected int index;
/*     */ 
/*     */   PolylineShapeIterator(PolylineShape l, AffineTransform at)
/*     */   {
/*  66 */     this.poly = l;
/*  67 */     this.affine = at;
/*     */   }
/*     */ 
/*     */   public int currentSegment(double[] coords)
/*     */   {
/*  89 */     if (isDone()) {
/*  90 */       throw new NoSuchElementException(MessageLocalization.getComposedMessage("line.iterator.out.of.bounds", new Object[0]));
/*     */     }
/*  92 */     int type = this.index == 0 ? 0 : 1;
/*  93 */     coords[0] = this.poly.x[this.index];
/*  94 */     coords[1] = this.poly.y[this.index];
/*  95 */     if (this.affine != null) {
/*  96 */       this.affine.transform(coords, 0, coords, 0, 1);
/*     */     }
/*  98 */     return type;
/*     */   }
/*     */ 
/*     */   public int currentSegment(float[] coords)
/*     */   {
/* 120 */     if (isDone()) {
/* 121 */       throw new NoSuchElementException(MessageLocalization.getComposedMessage("line.iterator.out.of.bounds", new Object[0]));
/*     */     }
/* 123 */     int type = this.index == 0 ? 0 : 1;
/* 124 */     coords[0] = this.poly.x[this.index];
/* 125 */     coords[1] = this.poly.y[this.index];
/* 126 */     if (this.affine != null) {
/* 127 */       this.affine.transform(coords, 0, coords, 0, 1);
/*     */     }
/* 129 */     return type;
/*     */   }
/*     */ 
/*     */   public int getWindingRule()
/*     */   {
/* 140 */     return 1;
/*     */   }
/*     */ 
/*     */   public boolean isDone()
/*     */   {
/* 149 */     return this.index >= this.poly.np;
/*     */   }
/*     */ 
/*     */   public void next()
/*     */   {
/* 159 */     this.index += 1;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.awt.geom.PolylineShapeIterator
 * JD-Core Version:    0.6.2
 */