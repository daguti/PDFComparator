/*    */ package com.itextpdf.text.pdf;
/*    */ 
/*    */ import com.itextpdf.text.BaseColor;
/*    */ 
/*    */ class PdfColor extends PdfArray
/*    */ {
/*    */   PdfColor(int red, int green, int blue)
/*    */   {
/* 68 */     super(new PdfNumber((red & 0xFF) / 255.0D));
/* 69 */     add(new PdfNumber((green & 0xFF) / 255.0D));
/* 70 */     add(new PdfNumber((blue & 0xFF) / 255.0D));
/*    */   }
/*    */ 
/*    */   PdfColor(BaseColor color) {
/* 74 */     this(color.getRed(), color.getGreen(), color.getBlue());
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfColor
 * JD-Core Version:    0.6.2
 */