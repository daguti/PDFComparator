/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSFloat;
/*    */ import org.apache.pdfbox.cos.COSNumber;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public class MoveTextSetLeading extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 46 */     COSNumber y = (COSNumber)arguments.get(1);
/*    */ 
/* 48 */     ArrayList args = new ArrayList();
/* 49 */     args.add(new COSFloat(-1.0F * y.floatValue()));
/* 50 */     this.context.processOperator("TL", args);
/* 51 */     this.context.processOperator("Td", arguments);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.MoveTextSetLeading
 * JD-Core Version:    0.6.2
 */