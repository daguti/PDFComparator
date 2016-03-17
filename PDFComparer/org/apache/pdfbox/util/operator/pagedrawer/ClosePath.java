/*    */ package org.apache.pdfbox.util.operator.pagedrawer;
/*    */ 
/*    */ import java.awt.geom.GeneralPath;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.pdfviewer.PageDrawer;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.operator.OperatorProcessor;
/*    */ 
/*    */ public class ClosePath extends OperatorProcessor
/*    */ {
/* 41 */   private static final Log log = LogFactory.getLog(ClosePath.class);
/*    */ 
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 52 */     PageDrawer drawer = (PageDrawer)this.context;
/*    */     try
/*    */     {
/* 55 */       drawer.getLinePath().closePath();
/*    */     }
/*    */     catch (Throwable t)
/*    */     {
/* 59 */       log.warn(t, t);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.pagedrawer.ClosePath
 * JD-Core Version:    0.6.2
 */