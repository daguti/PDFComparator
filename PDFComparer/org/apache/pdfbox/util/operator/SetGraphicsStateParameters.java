/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.pdmodel.graphics.PDExtendedGraphicsState;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public class SetGraphicsStateParameters extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 48 */     COSName graphicsName = (COSName)arguments.get(0);
/* 49 */     PDExtendedGraphicsState gs = (PDExtendedGraphicsState)this.context.getGraphicsStates().get(graphicsName.getName());
/* 50 */     gs.copyIntoGraphicsState(this.context.getGraphicsState());
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.SetGraphicsStateParameters
 * JD-Core Version:    0.6.2
 */