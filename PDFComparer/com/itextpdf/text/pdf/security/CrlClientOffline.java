/*    */ package com.itextpdf.text.pdf.security;
/*    */ 
/*    */ import com.itextpdf.text.ExceptionConverter;
/*    */ import java.security.cert.CRL;
/*    */ import java.security.cert.X509CRL;
/*    */ import java.security.cert.X509Certificate;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class CrlClientOffline
/*    */   implements CrlClient
/*    */ {
/* 62 */   private ArrayList<byte[]> crls = new ArrayList();
/*    */ 
/*    */   public CrlClientOffline(byte[] crlEncoded)
/*    */   {
/* 70 */     this.crls.add(crlEncoded);
/*    */   }
/*    */ 
/*    */   public CrlClientOffline(CRL crl)
/*    */   {
/*    */     try
/*    */     {
/* 80 */       this.crls.add(((X509CRL)crl).getEncoded());
/*    */     }
/*    */     catch (Exception ex) {
/* 83 */       throw new ExceptionConverter(ex);
/*    */     }
/*    */   }
/*    */ 
/*    */   public Collection<byte[]> getEncoded(X509Certificate checkCert, String url)
/*    */   {
/* 92 */     return this.crls;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.CrlClientOffline
 * JD-Core Version:    0.6.2
 */