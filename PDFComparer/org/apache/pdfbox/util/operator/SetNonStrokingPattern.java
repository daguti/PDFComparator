/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.pdfbox.cos.COSArray;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.pdmodel.PDResources;
/*    */ import org.apache.pdfbox.pdmodel.graphics.PDGraphicsState;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorState;
/*    */ import org.apache.pdfbox.pdmodel.graphics.pattern.PDPatternResources;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public class SetNonStrokingPattern extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 45 */     int numberOfArguments = arguments.size();
/*    */     COSName selectedPattern;
/*    */     COSName selectedPattern;
/* 47 */     if (numberOfArguments == 1)
/*    */     {
/* 49 */       selectedPattern = (COSName)arguments.get(0);
/*    */     }
/*    */     else
/*    */     {
/* 55 */       COSArray colorValues = new COSArray();
/* 56 */       for (int i = 0; i < numberOfArguments - 1; i++)
/*    */       {
/* 58 */         colorValues.add((COSBase)arguments.get(i));
/*    */       }
/* 60 */       selectedPattern = (COSName)arguments.get(numberOfArguments - 1);
/*    */     }
/* 62 */     Map patterns = getContext().getResources().getPatterns();
/* 63 */     PDPatternResources pattern = (PDPatternResources)patterns.get(selectedPattern.getName());
/* 64 */     getContext().getGraphicsState().getNonStrokingColor().setPattern(pattern);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.SetNonStrokingPattern
 * JD-Core Version:    0.6.2
 */