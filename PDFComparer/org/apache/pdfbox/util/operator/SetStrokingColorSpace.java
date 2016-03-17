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
/*    */ public class SetStrokingColorSpace extends OperatorProcessor
/*    */ {
/* 41 */   private static final float[] EMPTY_FLOAT_ARRAY = new float[0];
/*    */ 
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 52 */     COSName name = (COSName)arguments.get(0);
/* 53 */     PDColorSpace cs = PDColorSpaceFactory.createColorSpace(name, this.context.getColorSpaces(), this.context.getResources().getPatterns());
/*    */ 
/* 55 */     PDColorState color = this.context.getGraphicsState().getStrokingColor();
/* 56 */     color.setColorSpace(cs);
/* 57 */     int numComponents = cs.getNumberOfComponents();
/* 58 */     float[] values = EMPTY_FLOAT_ARRAY;
/* 59 */     if (numComponents >= 0)
/*    */     {
/* 61 */       values = new float[numComponents];
/* 62 */       for (int i = 0; i < numComponents; i++)
/*    */       {
/* 64 */         values[i] = 0.0F;
/*    */       }
/* 66 */       if ((cs instanceof PDDeviceCMYK))
/*    */       {
/* 68 */         values[3] = 1.0F;
/*    */       }
/*    */     }
/* 71 */     color.setColorSpaceValue(values);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.SetStrokingColorSpace
 * JD-Core Version:    0.6.2
 */