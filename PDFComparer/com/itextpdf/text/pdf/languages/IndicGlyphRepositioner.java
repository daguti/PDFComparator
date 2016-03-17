/*    */ package com.itextpdf.text.pdf.languages;
/*    */ 
/*    */ import com.itextpdf.text.pdf.Glyph;
/*    */ import java.util.List;
/*    */ 
/*    */ abstract class IndicGlyphRepositioner
/*    */   implements GlyphRepositioner
/*    */ {
/*    */   public void repositionGlyphs(List<Glyph> glyphList)
/*    */   {
/* 59 */     for (int i = 0; i < glyphList.size(); i++) {
/* 60 */       Glyph glyph = (Glyph)glyphList.get(i);
/* 61 */       Glyph nextGlyph = getNextGlyph(glyphList, i);
/*    */ 
/* 63 */       if ((nextGlyph != null) && (getCharactersToBeShiftedLeftByOnePosition().contains(nextGlyph.chars)))
/*    */       {
/* 66 */         glyphList.set(i, nextGlyph);
/* 67 */         glyphList.set(i + 1, glyph);
/* 68 */         i++;
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   abstract List<String> getCharactersToBeShiftedLeftByOnePosition();
/*    */ 
/*    */   private Glyph getNextGlyph(List<Glyph> glyphs, int currentIndex)
/*    */   {
/* 78 */     if (currentIndex + 1 < glyphs.size()) {
/* 79 */       return (Glyph)glyphs.get(currentIndex + 1);
/*    */     }
/* 81 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.languages.IndicGlyphRepositioner
 * JD-Core Version:    0.6.2
 */