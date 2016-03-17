/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.Utilities;
/*     */ import com.itextpdf.text.pdf.fonts.otf.Language;
/*     */ import com.itextpdf.text.pdf.languages.BanglaGlyphRepositioner;
/*     */ import com.itextpdf.text.pdf.languages.GlyphRepositioner;
/*     */ import com.itextpdf.text.pdf.languages.IndicCompositeCharacterComparator;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ 
/*     */ class FontDetails
/*     */ {
/*     */   PdfIndirectReference indirectReference;
/*     */   PdfName fontName;
/*     */   BaseFont baseFont;
/*     */   TrueTypeFontUnicode ttu;
/*     */   CJKFont cjkFont;
/*     */   byte[] shortTag;
/*     */   HashMap<Integer, int[]> longTag;
/*     */   IntHashtable cjkTag;
/*     */   int fontType;
/*     */   boolean symbolic;
/* 116 */   protected boolean subset = true;
/*     */ 
/*     */   FontDetails(PdfName fontName, PdfIndirectReference indirectReference, BaseFont baseFont)
/*     */   {
/* 127 */     this.fontName = fontName;
/* 128 */     this.indirectReference = indirectReference;
/* 129 */     this.baseFont = baseFont;
/* 130 */     this.fontType = baseFont.getFontType();
/* 131 */     switch (this.fontType) {
/*     */     case 0:
/*     */     case 1:
/* 134 */       this.shortTag = new byte[256];
/* 135 */       break;
/*     */     case 2:
/* 137 */       this.cjkTag = new IntHashtable();
/* 138 */       this.cjkFont = ((CJKFont)baseFont);
/* 139 */       break;
/*     */     case 3:
/* 141 */       this.longTag = new HashMap();
/* 142 */       this.ttu = ((TrueTypeFontUnicode)baseFont);
/* 143 */       this.symbolic = baseFont.isFontSpecific();
/*     */     }
/*     */   }
/*     */ 
/*     */   PdfIndirectReference getIndirectReference()
/*     */   {
/* 153 */     return this.indirectReference;
/*     */   }
/*     */ 
/*     */   PdfName getFontName()
/*     */   {
/* 161 */     return this.fontName;
/*     */   }
/*     */ 
/*     */   BaseFont getBaseFont()
/*     */   {
/* 169 */     return this.baseFont;
/*     */   }
/*     */ 
/*     */   byte[] convertToBytes(String text)
/*     */   {
/* 180 */     byte[] b = null;
/* 181 */     switch (this.fontType) {
/*     */     case 5:
/* 183 */       return this.baseFont.convertToBytes(text);
/*     */     case 0:
/*     */     case 1:
/* 186 */       b = this.baseFont.convertToBytes(text);
/* 187 */       int len = b.length;
/* 188 */       for (int k = 0; k < len; k++)
/* 189 */         this.shortTag[(b[k] & 0xFF)] = 1;
/* 190 */       break;
/*     */     case 2:
/* 193 */       int len = text.length();
/* 194 */       if (this.cjkFont.isIdentity()) {
/* 195 */         for (int k = 0; k < len; k++) {
/* 196 */           this.cjkTag.put(text.charAt(k), 0);
/*     */         }
/*     */       }
/*     */       else {
/* 200 */         for (int k = 0; k < len; k++)
/*     */         {
/*     */           int val;
/* 202 */           if (Utilities.isSurrogatePair(text, k)) {
/* 203 */             int val = Utilities.convertToUtf32(text, k);
/* 204 */             k++;
/*     */           }
/*     */           else {
/* 207 */             val = text.charAt(k);
/*     */           }
/* 209 */           this.cjkTag.put(this.cjkFont.getCidCode(val), 0);
/*     */         }
/*     */       }
/* 212 */       b = this.cjkFont.convertToBytes(text);
/* 213 */       break;
/*     */     case 4:
/* 216 */       b = this.baseFont.convertToBytes(text);
/* 217 */       break;
/*     */     case 3:
/*     */       try
/*     */       {
/* 221 */         int len = text.length();
/* 222 */         int[] metrics = null;
/* 223 */         char[] glyph = new char[len];
/* 224 */         int i = 0;
/* 225 */         if (this.symbolic) {
/* 226 */           b = PdfEncodings.convertToBytes(text, "symboltt");
/* 227 */           len = b.length;
/* 228 */           for (int k = 0; k < len; k++) {
/* 229 */             metrics = this.ttu.getMetricsTT(b[k] & 0xFF);
/* 230 */             if (metrics != null)
/*     */             {
/* 232 */               this.longTag.put(Integer.valueOf(metrics[0]), new int[] { metrics[0], metrics[1], this.ttu.getUnicodeDifferences(b[k] & 0xFF) });
/* 233 */               glyph[(i++)] = ((char)metrics[0]);
/*     */             }
/*     */           } } else { if (canApplyGlyphSubstitution()) {
/* 236 */             return convertToBytesAfterGlyphSubstitution(text);
/*     */           }
/* 238 */           for (int k = 0; k < len; k++)
/*     */           {
/*     */             int val;
/* 240 */             if (Utilities.isSurrogatePair(text, k)) {
/* 241 */               int val = Utilities.convertToUtf32(text, k);
/* 242 */               k++;
/*     */             }
/*     */             else {
/* 245 */               val = text.charAt(k);
/*     */             }
/* 247 */             metrics = this.ttu.getMetricsTT(val);
/* 248 */             if (metrics != null)
/*     */             {
/* 250 */               int m0 = metrics[0];
/* 251 */               Integer gl = Integer.valueOf(m0);
/* 252 */               if (!this.longTag.containsKey(gl))
/* 253 */                 this.longTag.put(gl, new int[] { m0, metrics[1], val });
/* 254 */               glyph[(i++)] = ((char)m0);
/*     */             }
/*     */           } }
/* 257 */         String s = new String(glyph, 0, i);
/* 258 */         b = s.getBytes("UnicodeBigUnmarked");
/*     */       }
/*     */       catch (UnsupportedEncodingException e) {
/* 261 */         throw new ExceptionConverter(e);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 266 */     return b;
/*     */   }
/*     */ 
/*     */   private boolean canApplyGlyphSubstitution() {
/* 270 */     return (this.fontType == 3) && (this.ttu.getGlyphSubstitutionMap() != null);
/*     */   }
/*     */ 
/*     */   private byte[] convertToBytesAfterGlyphSubstitution(String text) throws UnsupportedEncodingException
/*     */   {
/* 275 */     if (!canApplyGlyphSubstitution()) {
/* 276 */       throw new IllegalArgumentException("Make sure the font type if TTF Unicode and a valid GlyphSubstitutionTable exists!");
/*     */     }
/*     */ 
/* 279 */     Map glyphSubstitutionMap = this.ttu.getGlyphSubstitutionMap();
/*     */ 
/* 284 */     Set compositeCharacters = new TreeSet(new IndicCompositeCharacterComparator());
/* 285 */     compositeCharacters.addAll(glyphSubstitutionMap.keySet());
/*     */ 
/* 288 */     ArrayBasedStringTokenizer tokenizer = new ArrayBasedStringTokenizer((String[])compositeCharacters.toArray(new String[0]));
/* 289 */     String[] tokens = tokenizer.tokenize(text);
/*     */ 
/* 291 */     List glyphList = new ArrayList(50);
/*     */ 
/* 293 */     for (String token : tokens)
/*     */     {
/* 296 */       Glyph subsGlyph = (Glyph)glyphSubstitutionMap.get(token);
/*     */ 
/* 298 */       if (subsGlyph != null) {
/* 299 */         glyphList.add(subsGlyph);
/*     */       }
/*     */       else {
/* 302 */         for (char c : token.toCharArray()) {
/* 303 */           int[] metrics = this.ttu.getMetricsTT(c);
/* 304 */           int glyphCode = metrics[0];
/* 305 */           int glyphWidth = metrics[1];
/* 306 */           glyphList.add(new Glyph(glyphCode, glyphWidth, String.valueOf(c)));
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 312 */     GlyphRepositioner glyphRepositioner = getGlyphRepositioner();
/*     */ 
/* 314 */     if (glyphRepositioner != null) {
/* 315 */       glyphRepositioner.repositionGlyphs(glyphList);
/*     */     }
/*     */ 
/* 318 */     char[] charEncodedGlyphCodes = new char[glyphList.size()];
/*     */ 
/* 321 */     for (int i = 0; i < glyphList.size(); i++) {
/* 322 */       Glyph glyph = (Glyph)glyphList.get(i);
/* 323 */       charEncodedGlyphCodes[i] = ((char)glyph.code);
/* 324 */       Integer glyphCode = Integer.valueOf(glyph.code);
/*     */ 
/* 326 */       if (!this.longTag.containsKey(glyphCode))
/*     */       {
/* 328 */         this.longTag.put(glyphCode, new int[] { glyph.code, glyph.width, glyph.chars.charAt(0) });
/*     */       }
/*     */     }
/*     */ 
/* 332 */     return new String(charEncodedGlyphCodes).getBytes("UnicodeBigUnmarked");
/*     */   }
/*     */ 
/*     */   private GlyphRepositioner getGlyphRepositioner() {
/* 336 */     Language language = this.ttu.getSupportedLanguage();
/*     */ 
/* 338 */     if (language == null) {
/* 339 */       throw new IllegalArgumentException("The supported language field cannot be null in " + this.ttu.getClass().getName());
/*     */     }
/*     */ 
/* 342 */     switch (1.$SwitchMap$com$itextpdf$text$pdf$fonts$otf$Language[language.ordinal()]) {
/*     */     case 1:
/* 344 */       return new BanglaGlyphRepositioner(Collections.unmodifiableMap(this.ttu.cmap31), this.ttu.getGlyphSubstitutionMap());
/*     */     }
/* 346 */     return null;
/*     */   }
/*     */ 
/*     */   public void writeFont(PdfWriter writer)
/*     */   {
/*     */     try
/*     */     {
/* 356 */       switch (this.fontType) {
/*     */       case 5:
/* 358 */         this.baseFont.writeFont(writer, this.indirectReference, null);
/* 359 */         break;
/*     */       case 0:
/*     */       case 1:
/* 364 */         for (int firstChar = 0; (firstChar < 256) && 
/* 365 */           (this.shortTag[firstChar] == 0); firstChar++);
/* 368 */         for (int lastChar = 255; (lastChar >= firstChar) && 
/* 369 */           (this.shortTag[lastChar] == 0); lastChar--);
/* 372 */         if (firstChar > 255) {
/* 373 */           firstChar = 255;
/* 374 */           lastChar = 255;
/*     */         }
/* 376 */         this.baseFont.writeFont(writer, this.indirectReference, new Object[] { Integer.valueOf(firstChar), Integer.valueOf(lastChar), this.shortTag, Boolean.valueOf(this.subset) });
/* 377 */         break;
/*     */       case 2:
/* 380 */         this.baseFont.writeFont(writer, this.indirectReference, new Object[] { this.cjkTag });
/* 381 */         break;
/*     */       case 3:
/* 383 */         this.baseFont.writeFont(writer, this.indirectReference, new Object[] { this.longTag, Boolean.valueOf(this.subset) });
/*     */       case 4:
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 388 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isSubset()
/*     */   {
/* 398 */     return this.subset;
/*     */   }
/*     */ 
/*     */   public void setSubset(boolean subset)
/*     */   {
/* 408 */     this.subset = subset;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.FontDetails
 * JD-Core Version:    0.6.2
 */