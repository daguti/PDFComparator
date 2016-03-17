/*    */ package org.apache.pdfbox.util.operator.pagedrawer;
/*    */ 
/*    */ import java.awt.geom.GeneralPath;
/*    */ import java.awt.geom.Point2D;
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSNumber;
/*    */ import org.apache.pdfbox.pdfviewer.PageDrawer;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.operator.OperatorProcessor;
/*    */ 
/*    */ public class AppendRectangleToPath extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */   {
/* 46 */     PageDrawer drawer = (PageDrawer)this.context;
/*    */ 
/* 48 */     COSNumber x = (COSNumber)arguments.get(0);
/* 49 */     COSNumber y = (COSNumber)arguments.get(1);
/* 50 */     COSNumber w = (COSNumber)arguments.get(2);
/* 51 */     COSNumber h = (COSNumber)arguments.get(3);
/*    */ 
/* 53 */     double x1 = x.doubleValue();
/* 54 */     double y1 = y.doubleValue();
/*    */ 
/* 57 */     double x2 = w.doubleValue() + x1;
/* 58 */     double y2 = h.doubleValue() + y1;
/*    */ 
/* 60 */     Point2D p0 = drawer.transformedPoint(x1, y1);
/* 61 */     Point2D p1 = drawer.transformedPoint(x2, y1);
/* 62 */     Point2D p2 = drawer.transformedPoint(x2, y2);
/* 63 */     Point2D p3 = drawer.transformedPoint(x1, y2);
/*    */ 
/* 67 */     GeneralPath path = drawer.getLinePath();
/* 68 */     path.moveTo((float)p0.getX(), (float)p0.getY());
/* 69 */     path.lineTo((float)p1.getX(), (float)p1.getY());
/* 70 */     path.lineTo((float)p2.getX(), (float)p2.getY());
/* 71 */     path.lineTo((float)p3.getX(), (float)p3.getY());
/*    */ 
/* 75 */     path.closePath();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.pagedrawer.AppendRectangleToPath
 * JD-Core Version:    0.6.2
 */