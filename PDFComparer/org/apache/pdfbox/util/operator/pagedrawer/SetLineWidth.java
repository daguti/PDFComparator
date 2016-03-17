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
/*    */ public class SetLineWidth extends org.apache.pdfbox.util.operator.SetLineWidth
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 46 */     super.process(operator, arguments);
/* 47 */     float lineWidth = (float)this.context.getGraphicsState().getLineWidth();
/* 48 */     PageDrawer drawer = (PageDrawer)this.context;
/* 49 */     BasicStroke stroke = drawer.getStroke();
/* 50 */     if (stroke == null)
/*    */     {
/* 52 */       drawer.setStroke(new BasicStroke(lineWidth, 0, 0));
/*    */     }
/*    */     else
/*    */     {
/* 56 */       drawer.setStroke(new BasicStroke(lineWidth, stroke.getEndCap(), stroke.getLineJoin(), stroke.getMiterLimit(), stroke.getDashArray(), stroke.getDashPhase()));
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.pagedrawer.SetLineWidth
 * JD-Core Version:    0.6.2
 */