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
/*    */ public class SetLineMiterLimit extends org.apache.pdfbox.util.operator.SetLineMiterLimit
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 46 */     super.process(operator, arguments);
/* 47 */     float miterLimit = (float)this.context.getGraphicsState().getMiterLimit();
/* 48 */     PageDrawer drawer = (PageDrawer)this.context;
/* 49 */     BasicStroke stroke = drawer.getStroke();
/* 50 */     if (stroke == null)
/*    */     {
/* 52 */       drawer.setStroke(new BasicStroke(1.0F, 2, 0, miterLimit, null, 0.0F));
/*    */     }
/*    */     else
/*    */     {
/* 57 */       drawer.setStroke(new BasicStroke(stroke.getLineWidth(), stroke.getEndCap(), stroke.getLineJoin(), miterLimit, null, 0.0F));
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.pagedrawer.SetLineMiterLimit
 * JD-Core Version:    0.6.2
 */