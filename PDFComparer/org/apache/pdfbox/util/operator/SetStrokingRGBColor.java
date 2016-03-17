/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSNumber;
/*    */ import org.apache.pdfbox.pdmodel.graphics.PDGraphicsState;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorState;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public class SetStrokingRGBColor extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 46 */     PDColorState color = this.context.getGraphicsState().getStrokingColor();
/* 47 */     color.setColorSpace(PDDeviceRGB.INSTANCE);
/* 48 */     float[] values = new float[3];
/* 49 */     for (int i = 0; i < arguments.size(); i++)
/*    */     {
/* 51 */       values[i] = ((COSNumber)arguments.get(i)).floatValue();
/*    */     }
/* 53 */     color.setColorSpaceValue(values);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.SetStrokingRGBColor
 * JD-Core Version:    0.6.2
 */