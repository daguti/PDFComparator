/*    */ package com.itextpdf.text.pdf.languages;
/*    */ 
/*    */ import com.itextpdf.text.pdf.BidiLine;
/*    */ 
/*    */ public class HebrewProcessor
/*    */   implements LanguageProcessor
/*    */ {
/* 51 */   protected int runDirection = 3;
/*    */ 
/*    */   public HebrewProcessor() {
/*    */   }
/*    */ 
/*    */   public HebrewProcessor(int runDirection) {
/* 57 */     this.runDirection = runDirection;
/*    */   }
/*    */ 
/*    */   public String process(String s) {
/* 61 */     return BidiLine.processLTR(s, this.runDirection, 0);
/*    */   }
/*    */ 
/*    */   public boolean isRTL()
/*    */   {
/* 70 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.languages.HebrewProcessor
 * JD-Core Version:    0.6.2
 */