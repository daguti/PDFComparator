/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSNumber;
/*    */ import org.apache.pdfbox.util.Matrix;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public class SetMatrix extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */   {
/* 42 */     COSNumber a = (COSNumber)arguments.get(0);
/* 43 */     COSNumber b = (COSNumber)arguments.get(1);
/* 44 */     COSNumber c = (COSNumber)arguments.get(2);
/* 45 */     COSNumber d = (COSNumber)arguments.get(3);
/* 46 */     COSNumber e = (COSNumber)arguments.get(4);
/* 47 */     COSNumber f = (COSNumber)arguments.get(5);
/*    */ 
/* 49 */     Matrix textMatrix = new Matrix();
/* 50 */     textMatrix.setValue(0, 0, a.floatValue());
/* 51 */     textMatrix.setValue(0, 1, b.floatValue());
/* 52 */     textMatrix.setValue(1, 0, c.floatValue());
/* 53 */     textMatrix.setValue(1, 1, d.floatValue());
/* 54 */     textMatrix.setValue(2, 0, e.floatValue());
/* 55 */     textMatrix.setValue(2, 1, f.floatValue());
/* 56 */     this.context.setTextMatrix(textMatrix);
/* 57 */     this.context.setTextLineMatrix(textMatrix.copy());
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.SetMatrix
 * JD-Core Version:    0.6.2
 */