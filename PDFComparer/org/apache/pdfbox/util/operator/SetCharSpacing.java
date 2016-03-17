/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSNumber;
/*    */ import org.apache.pdfbox.pdmodel.graphics.PDGraphicsState;
/*    */ import org.apache.pdfbox.pdmodel.text.PDTextState;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public class SetCharSpacing extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */   {
/* 40 */     if (arguments.size() > 0)
/*    */     {
/* 45 */       Object charSpacing = arguments.get(arguments.size() - 1);
/* 46 */       if ((charSpacing instanceof COSNumber))
/*    */       {
/* 48 */         COSNumber characterSpacing = (COSNumber)charSpacing;
/* 49 */         this.context.getGraphicsState().getTextState().setCharacterSpacing(characterSpacing.floatValue());
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.SetCharSpacing
 * JD-Core Version:    0.6.2
 */