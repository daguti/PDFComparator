/*    */ package com.itextpdf.text.pdf.fonts.cmaps;
/*    */ 
/*    */ import com.itextpdf.text.Utilities;
/*    */ import com.itextpdf.text.pdf.IntHashtable;
/*    */ import com.itextpdf.text.pdf.PdfNumber;
/*    */ import com.itextpdf.text.pdf.PdfObject;
/*    */ import com.itextpdf.text.pdf.PdfString;
/*    */ 
/*    */ public class CMapCidUni extends AbstractCMap
/*    */ {
/* 58 */   private IntHashtable map = new IntHashtable(65537);
/*    */ 
/*    */   void addChar(PdfString mark, PdfObject code)
/*    */   {
/* 62 */     if (!(code instanceof PdfNumber)) {
/* 63 */       return;
/*    */     }
/* 65 */     String s = decodeStringToUnicode(mark);
/*    */     int codepoint;
/*    */     int codepoint;
/* 66 */     if (Utilities.isSurrogatePair(s, 0))
/* 67 */       codepoint = Utilities.convertToUtf32(s, 0);
/*    */     else
/* 69 */       codepoint = s.charAt(0);
/* 70 */     this.map.put(((PdfNumber)code).intValue(), codepoint);
/*    */   }
/*    */ 
/*    */   public int lookup(int character) {
/* 74 */     return this.map.get(character);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.fonts.cmaps.CMapCidUni
 * JD-Core Version:    0.6.2
 */