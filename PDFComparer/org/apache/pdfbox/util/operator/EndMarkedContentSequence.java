/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.util.PDFMarkedContentExtractor;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ 
/*    */ public class EndMarkedContentSequence extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 40 */     if ((this.context instanceof PDFMarkedContentExtractor))
/*    */     {
/* 42 */       ((PDFMarkedContentExtractor)this.context).endMarkedContentSequence();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.EndMarkedContentSequence
 * JD-Core Version:    0.6.2
 */