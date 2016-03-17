/*    */ package com.itextpdf.text.pdf.security;
/*    */ 
/*    */ import com.itextpdf.text.pdf.PdfDictionary;
/*    */ import com.itextpdf.text.pdf.PdfName;
/*    */ 
/*    */ public class PdfSignatureAppDictionary extends PdfDictionary
/*    */ {
/*    */   public void setSignatureCreator(String name)
/*    */   {
/* 67 */     put(PdfName.NAME, new PdfName(name));
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.PdfSignatureAppDictionary
 * JD-Core Version:    0.6.2
 */