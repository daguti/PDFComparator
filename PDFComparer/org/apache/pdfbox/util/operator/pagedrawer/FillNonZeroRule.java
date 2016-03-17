/*    */ package org.apache.pdfbox.util.operator.pagedrawer;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.pdfviewer.PageDrawer;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.operator.OperatorProcessor;
/*    */ 
/*    */ public class FillNonZeroRule extends OperatorProcessor
/*    */ {
/* 42 */   private static final Log log = LogFactory.getLog(FillNonZeroRule.class);
/*    */ 
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/*    */     try
/*    */     {
/* 57 */       PageDrawer drawer = (PageDrawer)this.context;
/* 58 */       drawer.fillPath(1);
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 62 */       log.warn(e, e);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.pagedrawer.FillNonZeroRule
 * JD-Core Version:    0.6.2
 */