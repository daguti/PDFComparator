/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.pdmodel.PDResources;
/*    */ import org.apache.pdfbox.pdmodel.graphics.PDGraphicsState;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpaceFactory;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorState;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceCMYK;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public class SetNonStrokingColorSpace extends OperatorProcessor
/*    */ {
/* 38 */   private static final float[] EMPTY_FLOAT_ARRAY = new float[0];
/*    */ 
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 49 */     COSName name = (COSName)arguments.get(0);
/* 50 */     PDColorSpace cs = PDColorSpaceFactory.createColorSpace(name, this.context.getColorSpaces(), this.context.getResources().getPatterns());
/*    */ 
/* 52 */     PDColorState colorInstance = this.context.getGraphicsState().getNonStrokingColor();
/* 53 */     colorInstance.setColorSpace(cs);
/* 54 */     int numComponents = cs.getNumberOfComponents();
/* 55 */     float[] values = EMPTY_FLOAT_ARRAY;
/* 56 */     if (numComponents >= 0)
/*    */     {
/* 58 */       values = new float[numComponents];
/* 59 */       for (int i = 0; i < numComponents; i++)
/*    */       {
/* 61 */         values[i] = 0.0F;
/*    */       }
/* 63 */       if ((cs instanceof PDDeviceCMYK))
/*    */       {
/* 65 */         values[3] = 1.0F;
/*    */       }
/*    */     }
/* 68 */     colorInstance.setColorSpaceValue(values);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.SetNonStrokingColorSpace
 * JD-Core Version:    0.6.2
 */