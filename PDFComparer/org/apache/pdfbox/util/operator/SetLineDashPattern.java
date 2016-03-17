/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ import org.apache.pdfbox.cos.COSArray;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSNumber;
/*    */ import org.apache.pdfbox.pdmodel.graphics.PDGraphicsState;
/*    */ import org.apache.pdfbox.pdmodel.graphics.PDLineDashPattern;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public class SetLineDashPattern extends OperatorProcessor
/*    */ {
/* 43 */   private static final Log LOG = LogFactory.getLog(SetLineDashPattern.class);
/*    */ 
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 55 */     COSArray dashArray = (COSArray)arguments.get(0);
/* 56 */     int dashPhase = ((COSNumber)arguments.get(1)).intValue();
/* 57 */     if (dashPhase < 0)
/*    */     {
/* 59 */       LOG.warn("dash phaseStart has negative value " + dashPhase + ", set to 0");
/* 60 */       dashPhase = 0;
/*    */     }
/* 62 */     PDLineDashPattern lineDash = new PDLineDashPattern(dashArray, dashPhase);
/* 63 */     this.context.getGraphicsState().setLineDashPattern(lineDash);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.SetLineDashPattern
 * JD-Core Version:    0.6.2
 */