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
/*    */ public class LineTo extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */   {
/* 45 */     PageDrawer drawer = (PageDrawer)this.context;
/*    */ 
/* 48 */     COSNumber x = (COSNumber)arguments.get(0);
/* 49 */     COSNumber y = (COSNumber)arguments.get(1);
/*    */ 
/* 51 */     Point2D pos = drawer.transformedPoint(x.doubleValue(), y.doubleValue());
/* 52 */     drawer.getLinePath().lineTo((float)pos.getX(), (float)pos.getY());
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.pagedrawer.LineTo
 * JD-Core Version:    0.6.2
 */