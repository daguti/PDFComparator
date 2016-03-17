/*    */ package com.itextpdf.text.pdf;
/*    */ 
/*    */ public class PdfPTableHeader extends PdfPTableBody
/*    */ {
/* 49 */   protected PdfName role = PdfName.THEAD;
/*    */ 
/*    */   public PdfName getRole()
/*    */   {
/* 56 */     return this.role;
/*    */   }
/*    */ 
/*    */   public void setRole(PdfName role) {
/* 60 */     this.role = role;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfPTableHeader
 * JD-Core Version:    0.6.2
 */