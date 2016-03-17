/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.util.PDFMarkedContentExtractor;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ 
/*    */ public class BeginMarkedContentSequenceWithProperties extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 42 */     COSName tag = null;
/* 43 */     COSDictionary properties = null;
/* 44 */     for (COSBase argument : arguments)
/*    */     {
/* 46 */       if ((argument instanceof COSName))
/*    */       {
/* 48 */         tag = (COSName)argument;
/*    */       }
/* 50 */       else if ((argument instanceof COSDictionary))
/*    */       {
/* 52 */         properties = (COSDictionary)argument;
/*    */       }
/*    */     }
/* 55 */     if ((this.context instanceof PDFMarkedContentExtractor))
/*    */     {
/* 57 */       ((PDFMarkedContentExtractor)this.context).beginMarkedContentSequence(tag, properties);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.BeginMarkedContentSequenceWithProperties
 * JD-Core Version:    0.6.2
 */