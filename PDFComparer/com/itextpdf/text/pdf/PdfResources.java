/*    */ package com.itextpdf.text.pdf;
/*    */ 
/*    */ class PdfResources extends PdfDictionary
/*    */ {
/*    */   void add(PdfName key, PdfDictionary resource)
/*    */   {
/* 77 */     if (resource.size() == 0)
/* 78 */       return;
/* 79 */     PdfDictionary dic = getAsDict(key);
/* 80 */     if (dic == null)
/* 81 */       put(key, resource);
/*    */     else
/* 83 */       dic.putAll(resource);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfResources
 * JD-Core Version:    0.6.2
 */