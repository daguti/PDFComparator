/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSNumber;
/*    */ import org.apache.pdfbox.pdmodel.graphics.PDGraphicsState;
/*    */ import org.apache.pdfbox.pdmodel.text.PDTextState;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public class SetWordSpacing extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */   {
/* 40 */     COSNumber wordSpacing = (COSNumber)arguments.get(0);
/* 41 */     this.context.getGraphicsState().getTextState().setWordSpacing(wordSpacing.floatValue());
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.SetWordSpacing
 * JD-Core Version:    0.6.2
 */