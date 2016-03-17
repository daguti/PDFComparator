/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSInteger;
/*    */ import org.apache.pdfbox.pdmodel.graphics.PDGraphicsState;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorState;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDIndexed;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public class SetNonStrokingIndexed extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 44 */     PDColorState colorInstance = this.context.getGraphicsState().getNonStrokingColor();
/* 45 */     PDColorSpace colorSpace = colorInstance.getColorSpace();
/*    */ 
/* 47 */     if (colorSpace != null)
/*    */     {
/* 49 */       PDIndexed indexed = (PDIndexed)colorSpace;
/* 50 */       colorSpace = indexed.getBaseColorSpace();
/* 51 */       COSInteger colorValue = (COSInteger)arguments.get(0);
/* 52 */       colorInstance.setColorSpaceValue(indexed.calculateColorValues(colorValue.intValue()));
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.SetNonStrokingIndexed
 * JD-Core Version:    0.6.2
 */