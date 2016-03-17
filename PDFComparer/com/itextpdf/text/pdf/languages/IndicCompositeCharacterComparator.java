/*    */ package com.itextpdf.text.pdf.languages;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ 
/*    */ public class IndicCompositeCharacterComparator
/*    */   implements Comparator<String>
/*    */ {
/*    */   public int compare(String o1, String o2)
/*    */   {
/* 74 */     if (o2.length() > o1.length())
/* 75 */       return 1;
/* 76 */     if (o1.length() > o2.length()) {
/* 77 */       return -1;
/*    */     }
/* 79 */     return o1.compareTo(o2);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.languages.IndicCompositeCharacterComparator
 * JD-Core Version:    0.6.2
 */