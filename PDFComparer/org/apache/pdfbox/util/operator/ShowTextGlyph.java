/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSArray;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSNumber;
/*    */ import org.apache.pdfbox.cos.COSString;
/*    */ import org.apache.pdfbox.pdmodel.graphics.PDGraphicsState;
/*    */ import org.apache.pdfbox.pdmodel.text.PDTextState;
/*    */ import org.apache.pdfbox.util.Matrix;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public class ShowTextGlyph extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 44 */     COSArray array = (COSArray)arguments.get(0);
/* 45 */     int arraySize = array.size();
/* 46 */     float fontsize = this.context.getGraphicsState().getTextState().getFontSize();
/* 47 */     float horizontalScaling = this.context.getGraphicsState().getTextState().getHorizontalScalingPercent() / 100.0F;
/* 48 */     for (int i = 0; i < arraySize; i++)
/*    */     {
/* 50 */       COSBase next = array.get(i);
/* 51 */       if ((next instanceof COSNumber))
/*    */       {
/* 53 */         float adjustment = ((COSNumber)next).floatValue();
/* 54 */         Matrix adjMatrix = new Matrix();
/* 55 */         adjustment = -(adjustment / 1000.0F) * horizontalScaling * fontsize;
/*    */ 
/* 57 */         adjMatrix.setValue(2, 0, adjustment);
/* 58 */         this.context.setTextMatrix(adjMatrix.multiply(this.context.getTextMatrix(), adjMatrix));
/*    */       }
/* 60 */       else if ((next instanceof COSString))
/*    */       {
/* 62 */         this.context.processEncodedText(((COSString)next).getBytes());
/*    */       }
/*    */       else
/*    */       {
/* 66 */         throw new IOException("Unknown type in array for TJ operation:" + next);
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.ShowTextGlyph
 * JD-Core Version:    0.6.2
 */