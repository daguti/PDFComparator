/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSArray;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.pdmodel.graphics.PDGraphicsState;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorState;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceN;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public class SetStrokingDeviceN extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 44 */     PDColorState colorInstance = this.context.getGraphicsState().getStrokingColor();
/* 45 */     PDColorSpace colorSpace = colorInstance.getColorSpace();
/*    */ 
/* 47 */     if (colorSpace != null)
/*    */     {
/* 49 */       PDDeviceN sep = (PDDeviceN)colorSpace;
/* 50 */       colorSpace = sep.getAlternateColorSpace();
/* 51 */       COSArray colorValues = sep.calculateColorValues(arguments);
/* 52 */       colorInstance.setColorSpaceValue(colorValues.toFloatArray());
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.SetStrokingDeviceN
 * JD-Core Version:    0.6.2
 */