/*    */ package com.itextpdf.text.pdf.security;
/*    */ 
/*    */ import com.itextpdf.text.ExceptionConverter;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.security.KeyStore;
/*    */ 
/*    */ public class KeyStoreUtil
/*    */ {
/*    */   public static KeyStore loadCacertsKeyStore(String provider)
/*    */   {
/* 64 */     File file = new File(System.getProperty("java.home"), "lib");
/* 65 */     file = new File(file, "security");
/* 66 */     file = new File(file, "cacerts");
/* 67 */     FileInputStream fin = null;
/*    */     try {
/* 69 */       fin = new FileInputStream(file);
/*    */       KeyStore k;
/*    */       KeyStore k;
/* 71 */       if (provider == null)
/* 72 */         k = KeyStore.getInstance("JKS");
/*    */       else
/* 74 */         k = KeyStore.getInstance("JKS", provider);
/* 75 */       k.load(fin, null);
/* 76 */       return k;
/*    */     }
/*    */     catch (Exception e) {
/* 79 */       throw new ExceptionConverter(e);
/*    */     } finally {
/*    */       try {
/* 82 */         if (fin != null) fin.close();
/*    */       }
/*    */       catch (Exception ex)
/*    */       {
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public static KeyStore loadCacertsKeyStore()
/*    */   {
/* 92 */     return loadCacertsKeyStore(null);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.KeyStoreUtil
 * JD-Core Version:    0.6.2
 */