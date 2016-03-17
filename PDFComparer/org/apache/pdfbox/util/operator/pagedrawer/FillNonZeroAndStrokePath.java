/*    */ package org.apache.pdfbox.util.operator.pagedrawer;
/*    */ 
/*    */ import java.awt.geom.GeneralPath;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.pdfviewer.PageDrawer;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ import org.apache.pdfbox.util.operator.OperatorProcessor;
/*    */ 
/*    */ public class FillNonZeroAndStrokePath extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 46 */     PageDrawer drawer = (PageDrawer)this.context;
/* 47 */     GeneralPath currentPath = (GeneralPath)drawer.getLinePath().clone();
/*    */ 
/* 49 */     this.context.processOperator("f", arguments);
/* 50 */     drawer.setLinePath(currentPath);
/*    */ 
/* 52 */     this.context.processOperator("S", arguments);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.pagedrawer.FillNonZeroAndStrokePath
 * JD-Core Version:    0.6.2
 */