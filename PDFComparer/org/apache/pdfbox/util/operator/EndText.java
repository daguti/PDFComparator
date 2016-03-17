/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public class EndText extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */   {
/* 38 */     this.context.setTextMatrix(null);
/* 39 */     this.context.setTextLineMatrix(null);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.EndText
 * JD-Core Version:    0.6.2
 */