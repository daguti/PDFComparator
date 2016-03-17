/*    */ package com.itextpdf.text.pdf.security;
/*    */ 
/*    */ import com.itextpdf.text.pdf.PdfDictionary;
/*    */ import com.itextpdf.text.pdf.PdfName;
/*    */ 
/*    */ public class PdfSignatureBuildProperties extends PdfDictionary
/*    */ {
/*    */   public void setSignatureCreator(String name)
/*    */   {
/* 68 */     getPdfSignatureAppProperty().setSignatureCreator(name);
/*    */   }
/*    */ 
/*    */   PdfSignatureAppDictionary getPdfSignatureAppProperty()
/*    */   {
/* 79 */     PdfSignatureAppDictionary appPropDic = (PdfSignatureAppDictionary)getAsDict(PdfName.APP);
/* 80 */     if (appPropDic == null) {
/* 81 */       appPropDic = new PdfSignatureAppDictionary();
/* 82 */       put(PdfName.APP, appPropDic);
/*    */     }
/* 84 */     return appPropDic;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.PdfSignatureBuildProperties
 * JD-Core Version:    0.6.2
 */