/*    */ package com.itextpdf.text.pdf;
/*    */ 
/*    */ public class PdfTransparencyGroup extends PdfDictionary
/*    */ {
/*    */   public PdfTransparencyGroup()
/*    */   {
/* 58 */     put(PdfName.S, PdfName.TRANSPARENCY);
/*    */   }
/*    */ 
/*    */   public void setIsolated(boolean isolated)
/*    */   {
/* 66 */     if (isolated)
/* 67 */       put(PdfName.I, PdfBoolean.PDFTRUE);
/*    */     else
/* 69 */       remove(PdfName.I);
/*    */   }
/*    */ 
/*    */   public void setKnockout(boolean knockout)
/*    */   {
/* 77 */     if (knockout)
/* 78 */       put(PdfName.K, PdfBoolean.PDFTRUE);
/*    */     else
/* 80 */       remove(PdfName.K);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfTransparencyGroup
 * JD-Core Version:    0.6.2
 */