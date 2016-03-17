/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSNumber;
/*    */ import org.apache.pdfbox.pdmodel.graphics.PDGraphicsState;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public class SetLineJoinStyle extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 45 */     int lineJoinStyle = ((COSNumber)arguments.get(0)).intValue();
/* 46 */     this.context.getGraphicsState().setLineJoin(lineJoinStyle);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.SetLineJoinStyle
 * JD-Core Version:    0.6.2
 */