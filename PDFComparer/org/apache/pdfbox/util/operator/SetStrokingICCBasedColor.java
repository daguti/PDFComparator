/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSNumber;
/*    */ import org.apache.pdfbox.pdmodel.graphics.PDGraphicsState;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorState;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public class SetStrokingICCBasedColor extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 43 */     PDColorState color = this.context.getGraphicsState().getStrokingColor();
/* 44 */     PDColorSpace cs = color.getColorSpace();
/* 45 */     int numberOfComponents = cs.getNumberOfComponents();
/* 46 */     float[] values = new float[numberOfComponents];
/* 47 */     for (int i = 0; i < numberOfComponents; i++)
/*    */     {
/* 49 */       values[i] = ((COSNumber)arguments.get(i)).floatValue();
/*    */     }
/* 51 */     color.setColorSpaceValue(values);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.SetStrokingICCBasedColor
 * JD-Core Version:    0.6.2
 */