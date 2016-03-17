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
/*    */ public class CurveTo extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */   {
/* 45 */     PageDrawer drawer = (PageDrawer)this.context;
/*    */ 
/* 47 */     COSNumber x1 = (COSNumber)arguments.get(0);
/* 48 */     COSNumber y1 = (COSNumber)arguments.get(1);
/* 49 */     COSNumber x2 = (COSNumber)arguments.get(2);
/* 50 */     COSNumber y2 = (COSNumber)arguments.get(3);
/* 51 */     COSNumber x3 = (COSNumber)arguments.get(4);
/* 52 */     COSNumber y3 = (COSNumber)arguments.get(5);
/*    */ 
/* 54 */     Point2D point1 = drawer.transformedPoint(x1.doubleValue(), y1.doubleValue());
/* 55 */     Point2D point2 = drawer.transformedPoint(x2.doubleValue(), y2.doubleValue());
/* 56 */     Point2D point3 = drawer.transformedPoint(x3.doubleValue(), y3.doubleValue());
/*    */ 
/* 58 */     drawer.getLinePath().curveTo((float)point1.getX(), (float)point1.getY(), (float)point2.getX(), (float)point2.getY(), (float)point3.getX(), (float)point3.getY());
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.pagedrawer.CurveTo
 * JD-Core Version:    0.6.2
 */