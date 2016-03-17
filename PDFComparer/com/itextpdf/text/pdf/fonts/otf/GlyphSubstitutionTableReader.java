/*     */ package com.itextpdf.text.pdf.fonts.otf;
/*     */ 
/*     */ import com.itextpdf.text.log.Logger;
/*     */ import com.itextpdf.text.pdf.Glyph;
/*     */ import com.itextpdf.text.pdf.RandomAccessFileOrArray;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class GlyphSubstitutionTableReader extends OpenTypeFontTableReader
/*     */ {
/*     */   private final int[] glyphWidthsByIndex;
/*     */   private final Map<Integer, Character> glyphToCharacterMap;
/*     */   private Map<Integer, List<Integer>> rawLigatureSubstitutionMap;
/*     */ 
/*     */   public GlyphSubstitutionTableReader(String fontFilePath, int gsubTableLocation, Map<Integer, Character> glyphToCharacterMap, int[] glyphWidthsByIndex)
/*     */     throws IOException
/*     */   {
/*  76 */     super(fontFilePath, gsubTableLocation);
/*  77 */     this.glyphWidthsByIndex = glyphWidthsByIndex;
/*  78 */     this.glyphToCharacterMap = glyphToCharacterMap;
/*     */   }
/*     */ 
/*     */   public void read() throws FontReadingException {
/*  82 */     this.rawLigatureSubstitutionMap = new LinkedHashMap();
/*  83 */     startReadingTable();
/*     */   }
/*     */ 
/*     */   public Map<String, Glyph> getGlyphSubstitutionMap() throws FontReadingException {
/*  87 */     Map glyphSubstitutionMap = new LinkedHashMap();
/*     */ 
/*  89 */     for (Integer glyphIdToReplace : this.rawLigatureSubstitutionMap.keySet()) {
/*  90 */       List constituentGlyphs = (List)this.rawLigatureSubstitutionMap.get(glyphIdToReplace);
/*  91 */       StringBuilder chars = new StringBuilder(constituentGlyphs.size());
/*     */ 
/*  93 */       for (Integer constituentGlyphId : constituentGlyphs) {
/*  94 */         chars.append(getTextFromGlyph(constituentGlyphId.intValue(), this.glyphToCharacterMap));
/*     */       }
/*     */ 
/*  97 */       Glyph glyph = new Glyph(glyphIdToReplace.intValue(), this.glyphWidthsByIndex[glyphIdToReplace.intValue()], chars.toString());
/*     */ 
/*  99 */       glyphSubstitutionMap.put(glyph.chars, glyph);
/*     */     }
/*     */ 
/* 102 */     return Collections.unmodifiableMap(glyphSubstitutionMap);
/*     */   }
/*     */ 
/*     */   private String getTextFromGlyph(int glyphId, Map<Integer, Character> glyphToCharacterMap) throws FontReadingException
/*     */   {
/* 107 */     StringBuilder chars = new StringBuilder(1);
/*     */ 
/* 109 */     Character c = (Character)glyphToCharacterMap.get(Integer.valueOf(glyphId));
/*     */     Iterator i$;
/* 111 */     if (c == null)
/*     */     {
/* 113 */       List constituentGlyphs = (List)this.rawLigatureSubstitutionMap.get(Integer.valueOf(glyphId));
/*     */ 
/* 115 */       if ((constituentGlyphs == null) || (constituentGlyphs.isEmpty())) {
/* 116 */         throw new FontReadingException("No corresponding character or simple glyphs found for GlyphID=" + glyphId);
/*     */       }
/*     */ 
/* 119 */       for (i$ = constituentGlyphs.iterator(); i$.hasNext(); ) { int constituentGlyphId = ((Integer)i$.next()).intValue();
/* 120 */         chars.append(getTextFromGlyph(constituentGlyphId, glyphToCharacterMap)); }
/*     */     }
/*     */     else
/*     */     {
/* 124 */       chars.append(c.charValue());
/*     */     }
/*     */ 
/* 127 */     return chars.toString();
/*     */   }
/*     */ 
/*     */   protected void readSubTable(int lookupType, int subTableLocation)
/*     */     throws IOException
/*     */   {
/* 133 */     if (lookupType == 1)
/* 134 */       readSingleSubstitutionSubtable(subTableLocation);
/* 135 */     else if (lookupType == 4)
/* 136 */       readLigatureSubstitutionSubtable(subTableLocation);
/*     */     else
/* 138 */       System.err.println("LookupType " + lookupType + " is not yet handled for " + GlyphSubstitutionTableReader.class.getSimpleName());
/*     */   }
/*     */ 
/*     */   private void readSingleSubstitutionSubtable(int subTableLocation)
/*     */     throws IOException
/*     */   {
/* 147 */     this.rf.seek(subTableLocation);
/*     */ 
/* 149 */     int substFormat = this.rf.readShort();
/* 150 */     LOG.debug("substFormat=" + substFormat);
/*     */     int deltaGlyphID;
/*     */     Iterator i$;
/* 152 */     if (substFormat == 1) {
/* 153 */       int coverage = this.rf.readShort();
/* 154 */       LOG.debug("coverage=" + coverage);
/*     */ 
/* 156 */       deltaGlyphID = this.rf.readShort();
/* 157 */       LOG.debug("deltaGlyphID=" + deltaGlyphID);
/*     */ 
/* 159 */       List coverageGlyphIds = readCoverageFormat(subTableLocation + coverage);
/*     */ 
/* 161 */       for (i$ = coverageGlyphIds.iterator(); i$.hasNext(); ) { int coverageGlyphId = ((Integer)i$.next()).intValue();
/* 162 */         int substituteGlyphId = coverageGlyphId + deltaGlyphID;
/* 163 */         this.rawLigatureSubstitutionMap.put(Integer.valueOf(substituteGlyphId), Arrays.asList(new Integer[] { Integer.valueOf(coverageGlyphId) })); }
/*     */     }
/* 165 */     else if (substFormat == 2) {
/* 166 */       System.err.println("LookupType 1 :: substFormat 2 is not yet handled by " + GlyphSubstitutionTableReader.class.getSimpleName());
/*     */     } else {
/* 168 */       throw new IllegalArgumentException("Bad substFormat: " + substFormat);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readLigatureSubstitutionSubtable(int ligatureSubstitutionSubtableLocation)
/*     */     throws IOException
/*     */   {
/* 176 */     this.rf.seek(ligatureSubstitutionSubtableLocation);
/* 177 */     int substFormat = this.rf.readShort();
/* 178 */     LOG.debug("substFormat=" + substFormat);
/*     */ 
/* 180 */     if (substFormat != 1) {
/* 181 */       throw new IllegalArgumentException("The expected SubstFormat is 1");
/*     */     }
/*     */ 
/* 184 */     int coverage = this.rf.readShort();
/* 185 */     LOG.debug("coverage=" + coverage);
/*     */ 
/* 187 */     int ligSetCount = this.rf.readShort();
/*     */ 
/* 189 */     List ligatureOffsets = new ArrayList(ligSetCount);
/*     */ 
/* 191 */     for (int i = 0; i < ligSetCount; i++) {
/* 192 */       int ligatureOffset = this.rf.readShort();
/* 193 */       ligatureOffsets.add(Integer.valueOf(ligatureOffset));
/*     */     }
/*     */ 
/* 196 */     List coverageGlyphIds = readCoverageFormat(ligatureSubstitutionSubtableLocation + coverage);
/*     */ 
/* 198 */     if (ligSetCount != coverageGlyphIds.size()) {
/* 199 */       throw new IllegalArgumentException("According to the OpenTypeFont specifications, the coverage count should be equal to the no. of LigatureSetTables");
/*     */     }
/*     */ 
/* 202 */     for (int i = 0; i < ligSetCount; i++)
/*     */     {
/* 204 */       int coverageGlyphId = ((Integer)coverageGlyphIds.get(i)).intValue();
/* 205 */       int ligatureOffset = ((Integer)ligatureOffsets.get(i)).intValue();
/* 206 */       LOG.debug("ligatureOffset=" + ligatureOffset);
/* 207 */       readLigatureSetTable(ligatureSubstitutionSubtableLocation + ligatureOffset, coverageGlyphId);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readLigatureSetTable(int ligatureSetTableLocation, int coverageGlyphId) throws IOException
/*     */   {
/* 213 */     this.rf.seek(ligatureSetTableLocation);
/* 214 */     int ligatureCount = this.rf.readShort();
/* 215 */     LOG.debug("ligatureCount=" + ligatureCount);
/*     */ 
/* 217 */     List ligatureOffsets = new ArrayList(ligatureCount);
/*     */ 
/* 219 */     for (int i = 0; i < ligatureCount; i++) {
/* 220 */       int ligatureOffset = this.rf.readShort();
/* 221 */       ligatureOffsets.add(Integer.valueOf(ligatureOffset));
/*     */     }
/*     */ 
/* 224 */     for (Iterator i$ = ligatureOffsets.iterator(); i$.hasNext(); ) { int ligatureOffset = ((Integer)i$.next()).intValue();
/* 225 */       readLigatureTable(ligatureSetTableLocation + ligatureOffset, coverageGlyphId); }
/*     */   }
/*     */ 
/*     */   private void readLigatureTable(int ligatureTableLocation, int coverageGlyphId) throws IOException
/*     */   {
/* 230 */     this.rf.seek(ligatureTableLocation);
/* 231 */     int ligGlyph = this.rf.readShort();
/* 232 */     LOG.debug("ligGlyph=" + ligGlyph);
/*     */ 
/* 234 */     int compCount = this.rf.readShort();
/*     */ 
/* 236 */     List glyphIdList = new ArrayList();
/*     */ 
/* 238 */     glyphIdList.add(Integer.valueOf(coverageGlyphId));
/*     */ 
/* 240 */     for (int i = 0; i < compCount - 1; i++) {
/* 241 */       int glyphId = this.rf.readShort();
/* 242 */       glyphIdList.add(Integer.valueOf(glyphId));
/*     */     }
/*     */ 
/* 245 */     LOG.debug("glyphIdList=" + glyphIdList);
/*     */ 
/* 247 */     List previousValue = (List)this.rawLigatureSubstitutionMap.put(Integer.valueOf(ligGlyph), glyphIdList);
/*     */ 
/* 249 */     if (previousValue != null)
/* 250 */       LOG.warn("!!!!!!!!!!glyphId=" + ligGlyph + ",\npreviousValue=" + previousValue + ",\ncurrentVal=" + glyphIdList);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.fonts.otf.GlyphSubstitutionTableReader
 * JD-Core Version:    0.6.2
 */