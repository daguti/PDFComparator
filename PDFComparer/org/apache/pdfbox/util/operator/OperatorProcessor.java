/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public abstract class OperatorProcessor
/*    */ {
/* 35 */   protected PDFStreamEngine context = null;
/*    */ 
/*    */   protected PDFStreamEngine getContext()
/*    */   {
/* 52 */     return this.context;
/*    */   }
/*    */ 
/*    */   public void setContext(PDFStreamEngine ctx)
/*    */   {
/* 62 */     this.context = ctx;
/*    */   }
/*    */ 
/*    */   public abstract void process(PDFOperator paramPDFOperator, List<COSBase> paramList)
/*    */     throws IOException;
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.OperatorProcessor
 * JD-Core Version:    0.6.2
 */