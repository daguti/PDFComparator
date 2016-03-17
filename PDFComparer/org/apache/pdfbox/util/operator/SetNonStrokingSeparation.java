/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSArray;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.pdmodel.graphics.PDGraphicsState;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorState;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDSeparation;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public class SetNonStrokingSeparation extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 45 */     PDColorState colorInstance = this.context.getGraphicsState().getNonStrokingColor();
/* 46 */     PDColorSpace colorSpace = colorInstance.getColorSpace();
/*    */ 
/* 48 */     if (colorSpace != null)
/*    */     {
/* 50 */       PDSeparation sep = (PDSeparation)colorSpace;
/* 51 */       colorSpace = sep.getAlternateColorSpace();
/* 52 */       COSArray values = sep.calculateColorValues((COSBase)arguments.get(0));
/* 53 */       colorInstance.setColorSpaceValue(values.toFloatArray());
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.SetNonStrokingSeparation
 * JD-Core Version:    0.6.2
 */