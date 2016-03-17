/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSFloat;
/*    */ import org.apache.pdfbox.pdmodel.graphics.PDGraphicsState;
/*    */ import org.apache.pdfbox.pdmodel.text.PDTextState;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public class NextLine extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 44 */     ArrayList args = new ArrayList();
/* 45 */     args.add(new COSFloat(0.0F));
/*    */ 
/* 48 */     args.add(new COSFloat(-1.0F * this.context.getGraphicsState().getTextState().getLeading()));
/*    */ 
/* 50 */     this.context.processOperator("Td", args);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.NextLine
 * JD-Core Version:    0.6.2
 */