/*    */ package org.apache.pdfbox.util.operator.pagedrawer;
/*    */ 
/*    */ import java.awt.BasicStroke;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.pdfviewer.PageDrawer;
/*    */ import org.apache.pdfbox.pdmodel.graphics.PDGraphicsState;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public class SetLineCapStyle extends org.apache.pdfbox.util.operator.SetLineCapStyle
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 47 */     super.process(operator, arguments);
/* 48 */     int lineCapStyle = this.context.getGraphicsState().getLineCap();
/* 49 */     PageDrawer drawer = (PageDrawer)this.context;
/* 50 */     BasicStroke stroke = drawer.getStroke();
/* 51 */     if (stroke == null)
/*    */     {
/* 53 */       drawer.setStroke(new BasicStroke(1.0F, lineCapStyle, 0));
/*    */     }
/*    */     else
/*    */     {
/* 57 */       drawer.setStroke(new BasicStroke(stroke.getLineWidth(), lineCapStyle, stroke.getLineJoin(), stroke.getMiterLimit(), stroke.getDashArray(), stroke.getDashPhase()));
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.pagedrawer.SetLineCapStyle
 * JD-Core Version:    0.6.2
 */