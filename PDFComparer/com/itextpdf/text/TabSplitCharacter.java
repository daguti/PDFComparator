/*    */ package com.itextpdf.text;
/*    */ 
/*    */ import com.itextpdf.text.pdf.PdfChunk;
/*    */ 
/*    */ public class TabSplitCharacter
/*    */   implements SplitCharacter
/*    */ {
/* 52 */   public static final SplitCharacter TAB = new TabSplitCharacter();
/*    */ 
/*    */   public boolean isSplitCharacter(int start, int current, int end, char[] cc, PdfChunk[] ck) {
/* 55 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.TabSplitCharacter
 * JD-Core Version:    0.6.2
 */