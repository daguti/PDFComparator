/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Stack;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.pdmodel.graphics.PDGraphicsState;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public class GRestore extends OperatorProcessor
/*    */ {
/* 39 */   private static final Log LOG = LogFactory.getLog(GRestore.class);
/*    */ 
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */   {
/* 46 */     if (this.context.getGraphicsStack().size() > 0)
/*    */     {
/* 48 */       this.context.setGraphicsState((PDGraphicsState)this.context.getGraphicsStack().pop());
/*    */     }
/*    */     else
/*    */     {
/* 54 */       LOG.error("GRestore: no graphics state left to be restored.");
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.GRestore
 * JD-Core Version:    0.6.2
 */