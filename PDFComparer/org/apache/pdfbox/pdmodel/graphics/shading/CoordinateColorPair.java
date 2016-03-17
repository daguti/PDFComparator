/*    */ package org.apache.pdfbox.pdmodel.graphics.shading;
/*    */ 
/*    */ import java.awt.geom.Point2D;
/*    */ 
/*    */ class CoordinateColorPair
/*    */ {
/*    */   final Point2D coordinate;
/*    */   final float[] color;
/*    */ 
/*    */   CoordinateColorPair(Point2D p, float[] c)
/*    */   {
/* 39 */     this.coordinate = p;
/* 40 */     this.color = ((float[])c.clone());
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.shading.CoordinateColorPair
 * JD-Core Version:    0.6.2
 */