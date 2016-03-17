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
/*    */ public class ClipEvenOddRule extends OperatorProcessor
/*    */ {
/* 42 */   private static final Log LOG = LogFactory.getLog(ClipEvenOddRule.class);
/*    */ 
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/*    */     try
/*    */     {
/* 56 */       PageDrawer drawer = (PageDrawer)this.context;
/* 57 */       drawer.setClippingWindingRule(0);
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 61 */       LOG.warn(e, e);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.pagedrawer.ClipEvenOddRule
 * JD-Core Version:    0.6.2
 */