/*    */ package org.apache.pdfbox.pdmodel.graphics.shading;
/*    */ 
/*    */ import java.awt.geom.Point2D;
/*    */ 
/*    */ class Vertex
/*    */ {
/*    */   public Point2D point;
/*    */   public float[] color;
/*    */ 
/*    */   public Vertex(Point2D p, float[] c)
/*    */   {
/* 33 */     this.point = p;
/* 34 */     this.color = ((float[])c.clone());
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 40 */     String colorStr = "";
/* 41 */     for (float f : this.color)
/*    */     {
/* 43 */       if (colorStr.length() > 0)
/*    */       {
/* 45 */         colorStr = colorStr + " ";
/*    */       }
/* 47 */       colorStr = colorStr + String.format("%3.2f", new Object[] { Float.valueOf(f) });
/*    */     }
/* 49 */     return "Vertex{ " + this.point + ", colors=[" + colorStr + "] }";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.shading.Vertex
 * JD-Core Version:    0.6.2
 */