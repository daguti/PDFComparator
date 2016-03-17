/*    */ package com.itextpdf.text.pdf;
/*    */ 
/*    */ public class PdfBorderArray extends PdfArray
/*    */ {
/*    */   public PdfBorderArray(float hRadius, float vRadius, float width)
/*    */   {
/* 62 */     this(hRadius, vRadius, width, null);
/*    */   }
/*    */ 
/*    */   public PdfBorderArray(float hRadius, float vRadius, float width, PdfDashPattern dash)
/*    */   {
/* 70 */     super(new PdfNumber(hRadius));
/* 71 */     add(new PdfNumber(vRadius));
/* 72 */     add(new PdfNumber(width));
/* 73 */     if (dash != null)
/* 74 */       add(dash);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfBorderArray
 * JD-Core Version:    0.6.2
 */