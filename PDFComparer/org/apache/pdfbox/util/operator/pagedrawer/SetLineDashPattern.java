/*    */ package org.apache.pdfbox.util.operator.pagedrawer;
/*    */ 
/*    */ import java.awt.BasicStroke;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSArray;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.pdfviewer.PageDrawer;
/*    */ import org.apache.pdfbox.pdmodel.graphics.PDGraphicsState;
/*    */ import org.apache.pdfbox.pdmodel.graphics.PDLineDashPattern;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public class SetLineDashPattern extends org.apache.pdfbox.util.operator.SetLineDashPattern
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 48 */     super.process(operator, arguments);
/* 49 */     PDLineDashPattern lineDashPattern = this.context.getGraphicsState().getLineDashPattern();
/* 50 */     PageDrawer drawer = (PageDrawer)this.context;
/* 51 */     BasicStroke stroke = drawer.getStroke();
/* 52 */     if (stroke == null)
/*    */     {
/* 54 */       if (lineDashPattern.isDashPatternEmpty())
/*    */       {
/* 56 */         drawer.setStroke(new BasicStroke(1.0F, 2, 0, 10.0F));
/*    */       }
/*    */       else
/*    */       {
/* 60 */         drawer.setStroke(new BasicStroke(1.0F, 2, 0, 10.0F, lineDashPattern.getCOSDashPattern().toFloatArray(), lineDashPattern.getPhaseStart()));
/*    */       }
/*    */ 
/*    */     }
/* 66 */     else if (lineDashPattern.isDashPatternEmpty())
/*    */     {
/* 68 */       drawer.setStroke(new BasicStroke(stroke.getLineWidth(), stroke.getEndCap(), stroke.getLineJoin(), stroke.getMiterLimit()));
/*    */     }
/*    */     else
/*    */     {
/* 73 */       drawer.setStroke(new BasicStroke(stroke.getLineWidth(), stroke.getEndCap(), stroke.getLineJoin(), stroke.getMiterLimit(), lineDashPattern.getCOSDashPattern().toFloatArray(), lineDashPattern.getPhaseStart()));
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.pagedrawer.SetLineDashPattern
 * JD-Core Version:    0.6.2
 */