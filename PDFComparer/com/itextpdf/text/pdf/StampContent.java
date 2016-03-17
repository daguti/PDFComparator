/*    */ package com.itextpdf.text.pdf;
/*    */ 
/*    */ public class StampContent extends PdfContentByte
/*    */ {
/*    */   PdfStamperImp.PageStamp ps;
/*    */   PageResources pageResources;
/*    */ 
/*    */   StampContent(PdfStamperImp stamper, PdfStamperImp.PageStamp ps)
/*    */   {
/* 53 */     super(stamper);
/* 54 */     this.ps = ps;
/* 55 */     this.pageResources = ps.pageResources;
/*    */   }
/*    */ 
/*    */   public void setAction(PdfAction action, float llx, float lly, float urx, float ury) {
/* 59 */     ((PdfStamperImp)this.writer).addAnnotation(new PdfAnnotation(this.writer, llx, lly, urx, ury, action), this.ps.pageN);
/*    */   }
/*    */ 
/*    */   public PdfContentByte getDuplicate()
/*    */   {
/* 69 */     return new StampContent((PdfStamperImp)this.writer, this.ps);
/*    */   }
/*    */ 
/*    */   PageResources getPageResources() {
/* 73 */     return this.pageResources;
/*    */   }
/*    */ 
/*    */   void addAnnotation(PdfAnnotation annot) {
/* 77 */     ((PdfStamperImp)this.writer).addAnnotation(annot, this.ps.pageN);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.StampContent
 * JD-Core Version:    0.6.2
 */