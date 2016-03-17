/*    */ package com.itextpdf.text.pdf;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class NumberArray extends PdfArray
/*    */ {
/*    */   public NumberArray(float[] numbers)
/*    */   {
/* 62 */     for (float f : numbers)
/* 63 */       add(new PdfNumber(f));
/*    */   }
/*    */ 
/*    */   public NumberArray(List<PdfNumber> numbers)
/*    */   {
/* 73 */     for (PdfNumber n : numbers)
/* 74 */       add(n);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.NumberArray
 * JD-Core Version:    0.6.2
 */