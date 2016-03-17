/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.util.PDFMarkedContentExtractor;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ 
/*    */ public class BeginMarkedContentSequence extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 42 */     COSName tag = null;
/* 43 */     for (COSBase argument : arguments)
/*    */     {
/* 45 */       if ((argument instanceof COSName))
/*    */       {
/* 47 */         tag = (COSName)argument;
/*    */       }
/*    */     }
/* 50 */     if ((this.context instanceof PDFMarkedContentExtractor))
/*    */     {
/* 52 */       ((PDFMarkedContentExtractor)this.context).beginMarkedContentSequence(tag, null);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.BeginMarkedContentSequence
 * JD-Core Version:    0.6.2
 */