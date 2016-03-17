/*    */ package org.apache.pdfbox.util.operator.pagedrawer;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.pdfviewer.PageDrawer;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.operator.OperatorProcessor;
/*    */ 
/*    */ public class SHFill extends OperatorProcessor
/*    */ {
/* 43 */   private static final Log LOG = LogFactory.getLog(SHFill.class);
/*    */ 
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/*    */     try
/*    */     {
/* 56 */       PageDrawer drawer = (PageDrawer)this.context;
/* 57 */       drawer.shFill((COSName)arguments.get(0));
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 61 */       LOG.warn(e, e);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.pagedrawer.SHFill
 * JD-Core Version:    0.6.2
 */