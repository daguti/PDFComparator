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
/*    */ public class CurveToReplicateInitialPoint extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */   {
/* 46 */     PageDrawer drawer = (PageDrawer)this.context;
/*    */ 
/* 48 */     COSNumber x2 = (COSNumber)arguments.get(0);
/* 49 */     COSNumber y2 = (COSNumber)arguments.get(1);
/* 50 */     COSNumber x3 = (COSNumber)arguments.get(2);
/* 51 */     COSNumber y3 = (COSNumber)arguments.get(3);
/* 52 */     GeneralPath path = drawer.getLinePath();
/* 53 */     Point2D currentPoint = path.getCurrentPoint();
/*    */ 
/* 55 */     Point2D point2 = drawer.transformedPoint(x2.doubleValue(), y2.doubleValue());
/* 56 */     Point2D point3 = drawer.transformedPoint(x3.doubleValue(), y3.doubleValue());
/*    */ 
/* 58 */     drawer.getLinePath().curveTo((float)currentPoint.getX(), (float)currentPoint.getY(), (float)point2.getX(), (float)point2.getY(), (float)point3.getX(), (float)point3.getY());
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.pagedrawer.CurveToReplicateInitialPoint
 * JD-Core Version:    0.6.2
 */