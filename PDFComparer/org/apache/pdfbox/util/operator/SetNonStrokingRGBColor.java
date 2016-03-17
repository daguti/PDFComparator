/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSNumber;
/*    */ import org.apache.pdfbox.pdmodel.graphics.PDGraphicsState;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorState;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public class SetNonStrokingRGBColor extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 45 */     PDColorSpace cs = PDDeviceRGB.INSTANCE;
/* 46 */     PDColorState colorInstance = this.context.getGraphicsState().getNonStrokingColor();
/* 47 */     colorInstance.setColorSpace(cs);
/* 48 */     float[] values = new float[3];
/* 49 */     for (int i = 0; i < arguments.size(); i++)
/*    */     {
/* 51 */       values[i] = ((COSNumber)arguments.get(i)).floatValue();
/*    */     }
/* 53 */     colorInstance.setColorSpaceValue(values);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.SetNonStrokingRGBColor
 * JD-Core Version:    0.6.2
 */