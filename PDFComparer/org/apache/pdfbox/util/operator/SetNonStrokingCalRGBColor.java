/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSNumber;
/*    */ import org.apache.pdfbox.pdmodel.graphics.PDGraphicsState;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorState;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public class SetNonStrokingCalRGBColor extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 43 */     PDColorState colorInstance = this.context.getGraphicsState().getNonStrokingColor();
/* 44 */     float[] values = new float[3];
/* 45 */     for (int i = 0; i < arguments.size(); i++)
/*    */     {
/* 47 */       values[i] = ((COSNumber)arguments.get(i)).floatValue();
/*    */     }
/* 49 */     colorInstance.setColorSpaceValue(values);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.SetNonStrokingCalRGBColor
 * JD-Core Version:    0.6.2
 */