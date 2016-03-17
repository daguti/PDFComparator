/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSNumber;
/*    */ import org.apache.pdfbox.pdmodel.graphics.PDGraphicsState;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorState;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceGray;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public class SetNonStrokingGrayColor extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 45 */     PDColorSpace cs = new PDDeviceGray();
/* 46 */     PDColorState colorInstance = this.context.getGraphicsState().getNonStrokingColor();
/* 47 */     colorInstance.setColorSpace(cs);
/* 48 */     float[] values = new float[1];
/* 49 */     if (arguments.size() >= 1)
/*    */     {
/* 51 */       values[0] = ((COSNumber)arguments.get(0)).floatValue();
/*    */     }
/*    */     else
/*    */     {
/* 55 */       throw new IOException("Error: Expected at least one argument when setting non stroking gray color");
/*    */     }
/* 57 */     colorInstance.setColorSpaceValue(values);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.SetNonStrokingGrayColor
 * JD-Core Version:    0.6.2
 */