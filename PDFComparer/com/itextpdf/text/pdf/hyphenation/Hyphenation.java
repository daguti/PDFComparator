/*    */ package com.itextpdf.text.pdf.hyphenation;
/*    */ 
/*    */ public class Hyphenation
/*    */ {
/*    */   private int[] hyphenPoints;
/*    */   private String word;
/*    */   private int len;
/*    */ 
/*    */   Hyphenation(String word, int[] points)
/*    */   {
/* 39 */     this.word = word;
/* 40 */     this.hyphenPoints = points;
/* 41 */     this.len = points.length;
/*    */   }
/*    */ 
/*    */   public int length()
/*    */   {
/* 48 */     return this.len;
/*    */   }
/*    */ 
/*    */   public String getPreHyphenText(int index)
/*    */   {
/* 55 */     return this.word.substring(0, this.hyphenPoints[index]);
/*    */   }
/*    */ 
/*    */   public String getPostHyphenText(int index)
/*    */   {
/* 62 */     return this.word.substring(this.hyphenPoints[index]);
/*    */   }
/*    */ 
/*    */   public int[] getHyphenationPoints()
/*    */   {
/* 69 */     return this.hyphenPoints;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 73 */     StringBuffer str = new StringBuffer();
/* 74 */     int start = 0;
/* 75 */     for (int i = 0; i < this.len; i++) {
/* 76 */       str.append(this.word.substring(start, this.hyphenPoints[i])).append('-');
/* 77 */       start = this.hyphenPoints[i];
/*    */     }
/* 79 */     str.append(this.word.substring(start));
/* 80 */     return str.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.hyphenation.Hyphenation
 * JD-Core Version:    0.6.2
 */