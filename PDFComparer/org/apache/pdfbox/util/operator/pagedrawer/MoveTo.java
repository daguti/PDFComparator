/*    */ package org.apache.pdfbox.util.operator.pagedrawer;
/*    */ 
/*    */ import java.awt.geom.GeneralPath;
/*    */ import java.awt.geom.Point2D;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSNumber;
/*    */ import org.apache.pdfbox.pdfviewer.PageDrawer;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.operator.OperatorProcessor;
/*    */ 
/*    */ public class MoveTo extends OperatorProcessor
/*    */ {
/* 43 */   private static final Log log = LogFactory.getLog(MoveTo.class);
/*    */ 
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/*    */     try
/*    */     {
/* 55 */       PageDrawer drawer = (PageDrawer)this.context;
/* 56 */       COSNumber x = (COSNumber)arguments.get(0);
/* 57 */       COSNumber y = (COSNumber)arguments.get(1);
/* 58 */       Point2D pos = drawer.transformedPoint(x.doubleValue(), y.doubleValue());
/* 59 */       drawer.getLinePath().moveTo((float)pos.getX(), (float)pos.getY());
/*    */     }
/*    */     catch (Exception exception)
/*    */     {
/* 63 */       log.warn(exception, exception);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.pagedrawer.MoveTo
 * JD-Core Version:    0.6.2
 */