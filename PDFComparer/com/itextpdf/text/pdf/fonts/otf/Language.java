/*    */ package com.itextpdf.text.pdf.fonts.otf;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ 
/*    */ public enum Language
/*    */ {
/* 56 */   BENGALI(new String[] { "beng", "bng2" });
/*    */ 
/*    */   private final List<String> codes;
/*    */ 
/*    */   private Language(String[] codes) {
/* 61 */     this.codes = Arrays.asList(codes);
/*    */   }
/*    */ 
/*    */   public boolean isSupported(String languageCode) {
/* 65 */     return this.codes.contains(languageCode);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.fonts.otf.Language
 * JD-Core Version:    0.6.2
 */