/*    */ package com.itextpdf.text.pdf.fonts.cmaps;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class IdentityToUnicode
/*    */ {
/*    */   private static CMapToUnicode identityCNS;
/*    */   private static CMapToUnicode identityJapan;
/*    */   private static CMapToUnicode identityKorea;
/*    */   private static CMapToUnicode identityGB;
/*    */ 
/*    */   public static CMapToUnicode GetMapFromOrdering(String ordering)
/*    */     throws IOException
/*    */   {
/* 61 */     if (ordering.equals("CNS1")) {
/* 62 */       if (identityCNS == null) {
/* 63 */         CMapUniCid uni = CMapCache.getCachedCMapUniCid("UniCNS-UTF16-H");
/* 64 */         if (uni == null)
/* 65 */           return null;
/* 66 */         identityCNS = uni.exportToUnicode();
/*    */       }
/* 68 */       return identityCNS;
/*    */     }
/* 70 */     if (ordering.equals("Japan1")) {
/* 71 */       if (identityJapan == null) {
/* 72 */         CMapUniCid uni = CMapCache.getCachedCMapUniCid("UniJIS-UTF16-H");
/* 73 */         if (uni == null)
/* 74 */           return null;
/* 75 */         identityJapan = uni.exportToUnicode();
/*    */       }
/* 77 */       return identityJapan;
/*    */     }
/* 79 */     if (ordering.equals("Korea1")) {
/* 80 */       if (identityKorea == null) {
/* 81 */         CMapUniCid uni = CMapCache.getCachedCMapUniCid("UniKS-UTF16-H");
/* 82 */         if (uni == null)
/* 83 */           return null;
/* 84 */         identityKorea = uni.exportToUnicode();
/*    */       }
/* 86 */       return identityKorea;
/*    */     }
/* 88 */     if (ordering.equals("GB1")) {
/* 89 */       if (identityGB == null) {
/* 90 */         CMapUniCid uni = CMapCache.getCachedCMapUniCid("UniGB-UTF16-H");
/* 91 */         if (uni == null)
/* 92 */           return null;
/* 93 */         identityGB = uni.exportToUnicode();
/*    */       }
/* 95 */       return identityGB;
/*    */     }
/* 97 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.fonts.cmaps.IdentityToUnicode
 * JD-Core Version:    0.6.2
 */