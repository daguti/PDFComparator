/*    */ package com.itextpdf.text.pdf.crypto;
/*    */ 
/*    */ public final class IVGenerator
/*    */ {
/* 56 */   private static ARCFOUREncryption arcfour = new ARCFOUREncryption();
/*    */ 
/*    */   public static byte[] getIV()
/*    */   {
/* 72 */     return getIV(16);
/*    */   }
/*    */ 
/*    */   public static byte[] getIV(int len)
/*    */   {
/* 81 */     byte[] b = new byte[len];
/* 82 */     synchronized (arcfour) {
/* 83 */       arcfour.encryptARCFOUR(b);
/*    */     }
/* 85 */     return b;
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 57 */     long time = System.currentTimeMillis();
/* 58 */     long mem = Runtime.getRuntime().freeMemory();
/* 59 */     String s = time + "+" + mem;
/* 60 */     arcfour.prepareARCFOURKey(s.getBytes());
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.crypto.IVGenerator
 * JD-Core Version:    0.6.2
 */