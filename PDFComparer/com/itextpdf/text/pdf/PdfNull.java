/*    */ package com.itextpdf.text.pdf;
/*    */ 
/*    */ public class PdfNull extends PdfObject
/*    */ {
/* 61 */   public static final PdfNull PDFNULL = new PdfNull();
/*    */   private static final String CONTENT = "null";
/*    */ 
/*    */   public PdfNull()
/*    */   {
/* 74 */     super(8, "null");
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 80 */     return "null";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfNull
 * JD-Core Version:    0.6.2
 */