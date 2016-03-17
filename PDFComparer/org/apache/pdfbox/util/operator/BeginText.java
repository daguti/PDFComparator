/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.util.Matrix;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public class BeginText extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */   {
/* 41 */     this.context.setTextMatrix(new Matrix());
/* 42 */     this.context.setTextLineMatrix(new Matrix());
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.BeginText
 * JD-Core Version:    0.6.2
 */