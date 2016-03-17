/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSNumber;
/*    */ import org.apache.pdfbox.pdmodel.graphics.PDGraphicsState;
/*    */ import org.apache.pdfbox.util.Matrix;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public class Concatenate extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 46 */     COSNumber a = (COSNumber)arguments.get(0);
/* 47 */     COSNumber b = (COSNumber)arguments.get(1);
/* 48 */     COSNumber c = (COSNumber)arguments.get(2);
/* 49 */     COSNumber d = (COSNumber)arguments.get(3);
/* 50 */     COSNumber e = (COSNumber)arguments.get(4);
/* 51 */     COSNumber f = (COSNumber)arguments.get(5);
/*    */ 
/* 53 */     Matrix newMatrix = new Matrix();
/* 54 */     newMatrix.setValue(0, 0, a.floatValue());
/* 55 */     newMatrix.setValue(0, 1, b.floatValue());
/* 56 */     newMatrix.setValue(1, 0, c.floatValue());
/* 57 */     newMatrix.setValue(1, 1, d.floatValue());
/* 58 */     newMatrix.setValue(2, 0, e.floatValue());
/* 59 */     newMatrix.setValue(2, 1, f.floatValue());
/*    */ 
/* 62 */     this.context.getGraphicsState().setCurrentTransformationMatrix(newMatrix.multiply(this.context.getGraphicsState().getCurrentTransformationMatrix()));
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.Concatenate
 * JD-Core Version:    0.6.2
 */