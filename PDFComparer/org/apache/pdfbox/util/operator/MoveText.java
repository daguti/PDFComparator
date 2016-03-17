/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSNumber;
/*    */ import org.apache.pdfbox.util.Matrix;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public class MoveText extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */   {
/* 41 */     COSNumber x = (COSNumber)arguments.get(0);
/* 42 */     COSNumber y = (COSNumber)arguments.get(1);
/* 43 */     Matrix td = new Matrix();
/* 44 */     td.setValue(2, 0, x.floatValue());
/* 45 */     td.setValue(2, 1, y.floatValue());
/* 46 */     this.context.setTextLineMatrix(td.multiply(this.context.getTextLineMatrix()));
/* 47 */     this.context.setTextMatrix(this.context.getTextLineMatrix().copy());
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.MoveText
 * JD-Core Version:    0.6.2
 */