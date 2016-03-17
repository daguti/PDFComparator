/*    */ package com.itextpdf.text.pdf;
/*    */ 
/*    */ class ColorDetails
/*    */ {
/*    */   PdfIndirectReference indirectReference;
/*    */   PdfName colorSpaceName;
/*    */   ICachedColorSpace colorSpace;
/*    */ 
/*    */   ColorDetails(PdfName colorName, PdfIndirectReference indirectReference, ICachedColorSpace scolor)
/*    */   {
/* 69 */     this.colorSpaceName = colorName;
/* 70 */     this.indirectReference = indirectReference;
/* 71 */     this.colorSpace = scolor;
/*    */   }
/*    */ 
/*    */   public PdfIndirectReference getIndirectReference()
/*    */   {
/* 78 */     return this.indirectReference;
/*    */   }
/*    */ 
/*    */   PdfName getColorSpaceName()
/*    */   {
/* 85 */     return this.colorSpaceName;
/*    */   }
/*    */ 
/*    */   public PdfObject getPdfObject(PdfWriter writer)
/*    */   {
/* 92 */     return this.colorSpace.getPdfObject(writer);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.ColorDetails
 * JD-Core Version:    0.6.2
 */