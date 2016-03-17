/*     */ package org.apache.fontbox.util;
/*     */ 
/*     */ import java.awt.Point;
/*     */ 
/*     */ public class BoundingBox
/*     */ {
/*     */   private float lowerLeftX;
/*     */   private float lowerLeftY;
/*     */   private float upperRightX;
/*     */   private float upperRightY;
/*     */ 
/*     */   public BoundingBox()
/*     */   {
/*     */   }
/*     */ 
/*     */   public BoundingBox(float minX, float minY, float maxX, float maxY)
/*     */   {
/*  52 */     this.lowerLeftX = minX;
/*  53 */     this.lowerLeftY = minY;
/*  54 */     this.upperRightX = maxX;
/*  55 */     this.upperRightY = maxY;
/*     */   }
/*     */ 
/*     */   public float getLowerLeftX()
/*     */   {
/*  64 */     return this.lowerLeftX;
/*     */   }
/*     */ 
/*     */   public void setLowerLeftX(float lowerLeftXValue)
/*     */   {
/*  74 */     this.lowerLeftX = lowerLeftXValue;
/*     */   }
/*     */ 
/*     */   public float getLowerLeftY()
/*     */   {
/*  84 */     return this.lowerLeftY;
/*     */   }
/*     */ 
/*     */   public void setLowerLeftY(float lowerLeftYValue)
/*     */   {
/*  94 */     this.lowerLeftY = lowerLeftYValue;
/*     */   }
/*     */ 
/*     */   public float getUpperRightX()
/*     */   {
/* 104 */     return this.upperRightX;
/*     */   }
/*     */ 
/*     */   public void setUpperRightX(float upperRightXValue)
/*     */   {
/* 114 */     this.upperRightX = upperRightXValue;
/*     */   }
/*     */ 
/*     */   public float getUpperRightY()
/*     */   {
/* 124 */     return this.upperRightY;
/*     */   }
/*     */ 
/*     */   public void setUpperRightY(float upperRightYValue)
/*     */   {
/* 134 */     this.upperRightY = upperRightYValue;
/*     */   }
/*     */ 
/*     */   public float getWidth()
/*     */   {
/* 145 */     return getUpperRightX() - getLowerLeftX();
/*     */   }
/*     */ 
/*     */   public float getHeight()
/*     */   {
/* 156 */     return getUpperRightY() - getLowerLeftY();
/*     */   }
/*     */ 
/*     */   public boolean contains(float x, float y)
/*     */   {
/* 169 */     return (x >= this.lowerLeftX) && (x <= this.upperRightX) && (y >= this.lowerLeftY) && (y <= this.upperRightY);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public boolean contains(Point point)
/*     */   {
/* 184 */     return contains((float)point.getX(), (float)point.getY());
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 194 */     return "[" + getLowerLeftX() + "," + getLowerLeftY() + "," + getUpperRightX() + "," + getUpperRightY() + "]";
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.util.BoundingBox
 * JD-Core Version:    0.6.2
 */