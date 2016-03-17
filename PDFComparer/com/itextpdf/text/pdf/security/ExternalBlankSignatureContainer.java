/*    */ package com.itextpdf.text.pdf.security;
/*    */ 
/*    */ import com.itextpdf.text.pdf.PdfDictionary;
/*    */ import com.itextpdf.text.pdf.PdfName;
/*    */ import java.io.InputStream;
/*    */ import java.security.GeneralSecurityException;
/*    */ 
/*    */ public class ExternalBlankSignatureContainer
/*    */   implements ExternalSignatureContainer
/*    */ {
/*    */   private PdfDictionary sigDic;
/*    */ 
/*    */   public ExternalBlankSignatureContainer(PdfDictionary sigDic)
/*    */   {
/* 61 */     this.sigDic = sigDic;
/*    */   }
/*    */ 
/*    */   public ExternalBlankSignatureContainer(PdfName filter, PdfName subFilter) {
/* 65 */     this.sigDic = new PdfDictionary();
/* 66 */     this.sigDic.put(PdfName.FILTER, filter);
/* 67 */     this.sigDic.put(PdfName.SUBFILTER, subFilter);
/*    */   }
/*    */ 
/*    */   public byte[] sign(InputStream data) throws GeneralSecurityException {
/* 71 */     return new byte[0];
/*    */   }
/*    */ 
/*    */   public void modifySigningDictionary(PdfDictionary signDic) {
/* 75 */     signDic.putAll(this.sigDic);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.ExternalBlankSignatureContainer
 * JD-Core Version:    0.6.2
 */