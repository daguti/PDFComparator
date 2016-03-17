/*     */ package com.itextpdf.text.pdf.languages;
/*     */ 
/*     */ import com.itextpdf.text.pdf.Glyph;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class BanglaGlyphRepositioner extends IndicGlyphRepositioner
/*     */ {
/*  59 */   private static final String[] CHARCTERS_TO_BE_SHIFTED_LEFT_BY_1 = { "ি", "ে", "ৈ" };
/*     */   private final Map<Integer, int[]> cmap31;
/*     */   private final Map<String, Glyph> glyphSubstitutionMap;
/*     */ 
/*     */   public BanglaGlyphRepositioner(Map<Integer, int[]> cmap31, Map<String, Glyph> glyphSubstitutionMap)
/*     */   {
/*  66 */     this.cmap31 = cmap31;
/*  67 */     this.glyphSubstitutionMap = glyphSubstitutionMap;
/*     */   }
/*     */ 
/*     */   public void repositionGlyphs(List<Glyph> glyphList)
/*     */   {
/*  73 */     for (int i = 0; i < glyphList.size(); i++) {
/*  74 */       Glyph glyph = (Glyph)glyphList.get(i);
/*     */ 
/*  76 */       if (glyph.chars.equals("ো"))
/*  77 */         handleOKaarAndOUKaar(i, glyphList, 'ে', 'া');
/*  78 */       else if (glyph.chars.equals("ৌ")) {
/*  79 */         handleOKaarAndOUKaar(i, glyphList, 'ে', 'ৗ');
/*     */       }
/*     */     }
/*     */ 
/*  83 */     super.repositionGlyphs(glyphList);
/*     */   }
/*     */ 
/*     */   public List<String> getCharactersToBeShiftedLeftByOnePosition()
/*     */   {
/*  88 */     return Arrays.asList(CHARCTERS_TO_BE_SHIFTED_LEFT_BY_1);
/*     */   }
/*     */ 
/*     */   private void handleOKaarAndOUKaar(int currentIndex, List<Glyph> glyphList, char first, char second)
/*     */   {
/*  98 */     Glyph g1 = getGlyph(first);
/*  99 */     Glyph g2 = getGlyph(second);
/* 100 */     glyphList.set(currentIndex, g1);
/* 101 */     glyphList.add(currentIndex + 1, g2);
/*     */   }
/*     */ 
/*     */   private Glyph getGlyph(char c)
/*     */   {
/* 106 */     Glyph glyph = (Glyph)this.glyphSubstitutionMap.get(String.valueOf(c));
/*     */ 
/* 108 */     if (glyph != null) {
/* 109 */       return glyph;
/*     */     }
/*     */ 
/* 112 */     int[] metrics = (int[])this.cmap31.get(Integer.valueOf(c));
/* 113 */     int glyphCode = metrics[0];
/* 114 */     int glyphWidth = metrics[1];
/* 115 */     return new Glyph(glyphCode, glyphWidth, String.valueOf(c));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.languages.BanglaGlyphRepositioner
 * JD-Core Version:    0.6.2
 */