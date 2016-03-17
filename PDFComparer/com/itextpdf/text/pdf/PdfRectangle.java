/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.awt.geom.AffineTransform;
/*     */ import com.itextpdf.text.Rectangle;
/*     */ 
/*     */ public class PdfRectangle extends NumberArray
/*     */ {
/*  69 */   private float llx = 0.0F;
/*     */ 
/*  72 */   private float lly = 0.0F;
/*     */ 
/*  75 */   private float urx = 0.0F;
/*     */ 
/*  78 */   private float ury = 0.0F;
/*     */ 
/*     */   public PdfRectangle(float llx, float lly, float urx, float ury, int rotation)
/*     */   {
/*  94 */     super(new float[0]);
/*  95 */     if ((rotation == 90) || (rotation == 270)) {
/*  96 */       this.llx = lly;
/*  97 */       this.lly = llx;
/*  98 */       this.urx = ury;
/*  99 */       this.ury = urx;
/*     */     }
/*     */     else {
/* 102 */       this.llx = llx;
/* 103 */       this.lly = lly;
/* 104 */       this.urx = urx;
/* 105 */       this.ury = ury;
/*     */     }
/* 107 */     super.add(new PdfNumber(this.llx));
/* 108 */     super.add(new PdfNumber(this.lly));
/* 109 */     super.add(new PdfNumber(this.urx));
/* 110 */     super.add(new PdfNumber(this.ury));
/*     */   }
/*     */ 
/*     */   public PdfRectangle(float llx, float lly, float urx, float ury) {
/* 114 */     this(llx, lly, urx, ury, 0);
/*     */   }
/*     */ 
/*     */   public PdfRectangle(float urx, float ury, int rotation)
/*     */   {
/* 125 */     this(0.0F, 0.0F, urx, ury, rotation);
/*     */   }
/*     */ 
/*     */   public PdfRectangle(float urx, float ury) {
/* 129 */     this(0.0F, 0.0F, urx, ury, 0);
/*     */   }
/*     */ 
/*     */   public PdfRectangle(Rectangle rectangle, int rotation)
/*     */   {
/* 139 */     this(rectangle.getLeft(), rectangle.getBottom(), rectangle.getRight(), rectangle.getTop(), rotation);
/*     */   }
/*     */ 
/*     */   public PdfRectangle(Rectangle rectangle) {
/* 143 */     this(rectangle.getLeft(), rectangle.getBottom(), rectangle.getRight(), rectangle.getTop(), 0);
/*     */   }
/*     */ 
/*     */   public Rectangle getRectangle()
/*     */   {
/* 152 */     return new Rectangle(left(), bottom(), right(), top());
/*     */   }
/*     */ 
/*     */   public boolean add(PdfObject object)
/*     */   {
/* 163 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean add(float[] values)
/*     */   {
/* 174 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean add(int[] values)
/*     */   {
/* 185 */     return false;
/*     */   }
/*     */ 
/*     */   public void addFirst(PdfObject object)
/*     */   {
/*     */   }
/*     */ 
/*     */   public float left()
/*     */   {
/* 203 */     return this.llx;
/*     */   }
/*     */ 
/*     */   public float right()
/*     */   {
/* 213 */     return this.urx;
/*     */   }
/*     */ 
/*     */   public float top()
/*     */   {
/* 223 */     return this.ury;
/*     */   }
/*     */ 
/*     */   public float bottom()
/*     */   {
/* 233 */     return this.lly;
/*     */   }
/*     */ 
/*     */   public float left(int margin)
/*     */   {
/* 244 */     return this.llx + margin;
/*     */   }
/*     */ 
/*     */   public float right(int margin)
/*     */   {
/* 255 */     return this.urx - margin;
/*     */   }
/*     */ 
/*     */   public float top(int margin)
/*     */   {
/* 266 */     return this.ury - margin;
/*     */   }
/*     */ 
/*     */   public float bottom(int margin)
/*     */   {
/* 277 */     return this.lly + margin;
/*     */   }
/*     */ 
/*     */   public float width()
/*     */   {
/* 287 */     return this.urx - this.llx;
/*     */   }
/*     */ 
/*     */   public float height()
/*     */   {
/* 297 */     return this.ury - this.lly;
/*     */   }
/*     */ 
/*     */   public PdfRectangle rotate()
/*     */   {
/* 307 */     return new PdfRectangle(this.lly, this.llx, this.ury, this.urx, 0);
/*     */   }
/*     */ 
/*     */   public PdfRectangle transform(AffineTransform transform) {
/* 311 */     float[] pts = { this.llx, this.lly, this.urx, this.ury };
/* 312 */     transform.transform(pts, 0, pts, 0, 2);
/* 313 */     float[] dstPts = { pts[0], pts[1], pts[2], pts[3] };
/* 314 */     if (pts[0] > pts[2]) {
/* 315 */       dstPts[0] = pts[2];
/* 316 */       dstPts[2] = pts[0];
/*     */     }
/* 318 */     if (pts[1] > pts[3]) {
/* 319 */       dstPts[1] = pts[3];
/* 320 */       dstPts[3] = pts[1];
/*     */     }
/* 322 */     return new PdfRectangle(dstPts[0], dstPts[1], dstPts[2], dstPts[3]);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfRectangle
 * JD-Core Version:    0.6.2
 */