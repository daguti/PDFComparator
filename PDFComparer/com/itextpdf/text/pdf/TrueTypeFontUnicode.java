/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.Utilities;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.pdf.fonts.otf.GlyphSubstitutionTableReader;
/*     */ import com.itextpdf.text.pdf.fonts.otf.Language;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ class TrueTypeFontUnicode extends TrueTypeFont
/*     */   implements Comparator<int[]>
/*     */ {
/*  68 */   private static final List<Language> SUPPORTED_LANGUAGES_FOR_OTF = Arrays.asList(new Language[] { Language.BENGALI });
/*     */   private Map<String, Glyph> glyphSubstitutionMap;
/*     */   private Language supportedLanguage;
/* 337 */   private static final byte[] rotbits = { -128, 64, 32, 16, 8, 4, 2, 1 };
/*     */ 
/*     */   TrueTypeFontUnicode(String ttFile, String enc, boolean emb, byte[] ttfAfm, boolean forceRead)
/*     */     throws DocumentException, IOException
/*     */   {
/*  85 */     String nameBase = getBaseName(ttFile);
/*  86 */     String ttcName = getTTCName(nameBase);
/*  87 */     if (nameBase.length() < ttFile.length()) {
/*  88 */       this.style = ttFile.substring(nameBase.length());
/*     */     }
/*  90 */     this.encoding = enc;
/*  91 */     this.embedded = emb;
/*  92 */     this.fileName = ttcName;
/*  93 */     this.ttcIndex = "";
/*  94 */     if (ttcName.length() < nameBase.length())
/*  95 */       this.ttcIndex = nameBase.substring(ttcName.length() + 1);
/*  96 */     this.fontType = 3;
/*  97 */     if (((this.fileName.toLowerCase().endsWith(".ttf")) || (this.fileName.toLowerCase().endsWith(".otf")) || (this.fileName.toLowerCase().endsWith(".ttc"))) && ((enc.equals("Identity-H")) || (enc.equals("Identity-V"))) && (emb)) {
/*  98 */       process(ttfAfm, forceRead);
/*  99 */       if (this.os_2.fsType == 2) {
/* 100 */         throw new DocumentException(MessageLocalization.getComposedMessage("1.cannot.be.embedded.due.to.licensing.restrictions", new Object[] { this.fileName + this.style }));
/*     */       }
/* 102 */       if (((this.cmap31 == null) && (!this.fontSpecific)) || ((this.cmap10 == null) && (this.fontSpecific))) {
/* 103 */         this.directTextToByte = true;
/*     */       }
/* 105 */       if (this.fontSpecific) {
/* 106 */         this.fontSpecific = false;
/* 107 */         String tempEncoding = this.encoding;
/* 108 */         this.encoding = "";
/* 109 */         createEncoding();
/* 110 */         this.encoding = tempEncoding;
/* 111 */         this.fontSpecific = true;
/*     */       }
/*     */     }
/*     */     else {
/* 115 */       throw new DocumentException(MessageLocalization.getComposedMessage("1.2.is.not.a.ttf.font.file", new Object[] { this.fileName, this.style }));
/* 116 */     }this.vertical = enc.endsWith("V");
/*     */   }
/*     */ 
/*     */   void process(byte[] ttfAfm, boolean preload) throws DocumentException, IOException
/*     */   {
/* 121 */     super.process(ttfAfm, preload);
/*     */   }
/*     */ 
/*     */   public int getWidth(int char1)
/*     */   {
/* 132 */     if (this.vertical)
/* 133 */       return 1000;
/* 134 */     if (this.fontSpecific) {
/* 135 */       if (((char1 & 0xFF00) == 0) || ((char1 & 0xFF00) == 61440)) {
/* 136 */         return getRawWidth(char1 & 0xFF, null);
/*     */       }
/* 138 */       return 0;
/*     */     }
/*     */ 
/* 141 */     return getRawWidth(char1, this.encoding);
/*     */   }
/*     */ 
/*     */   public int getWidth(String text)
/*     */   {
/* 152 */     if (this.vertical)
/* 153 */       return text.length() * 1000;
/* 154 */     int total = 0;
/* 155 */     if (this.fontSpecific) {
/* 156 */       char[] cc = text.toCharArray();
/* 157 */       int len = cc.length;
/* 158 */       for (int k = 0; k < len; k++) {
/* 159 */         char c = cc[k];
/* 160 */         if (((c & 0xFF00) == 0) || ((c & 0xFF00) == 61440))
/* 161 */           total += getRawWidth(c & 0xFF, null);
/*     */       }
/*     */     }
/*     */     else {
/* 165 */       int len = text.length();
/* 166 */       for (int k = 0; k < len; k++)
/* 167 */         if (Utilities.isSurrogatePair(text, k)) {
/* 168 */           total += getRawWidth(Utilities.convertToUtf32(text, k), this.encoding);
/* 169 */           k++;
/*     */         }
/*     */         else {
/* 172 */           total += getRawWidth(text.charAt(k), this.encoding);
/*     */         }
/*     */     }
/* 175 */     return total;
/*     */   }
/*     */ 
/*     */   public PdfStream getToUnicode(Object[] metrics)
/*     */   {
/* 184 */     if (metrics.length == 0)
/* 185 */       return null;
/* 186 */     StringBuffer buf = new StringBuffer("/CIDInit /ProcSet findresource begin\n12 dict begin\nbegincmap\n/CIDSystemInfo\n<< /Registry (TTX+0)\n/Ordering (T42UV)\n/Supplement 0\n>> def\n/CMapName /TTX+0 def\n/CMapType 2 def\n1 begincodespacerange\n<0000><FFFF>\nendcodespacerange\n");
/*     */ 
/* 200 */     int size = 0;
/* 201 */     for (int k = 0; k < metrics.length; k++) {
/* 202 */       if (size == 0) {
/* 203 */         if (k != 0) {
/* 204 */           buf.append("endbfrange\n");
/*     */         }
/* 206 */         size = Math.min(100, metrics.length - k);
/* 207 */         buf.append(size).append(" beginbfrange\n");
/*     */       }
/* 209 */       size--;
/* 210 */       int[] metric = (int[])metrics[k];
/* 211 */       String fromTo = toHex(metric[0]);
/* 212 */       buf.append(fromTo).append(fromTo).append(toHex(metric[2])).append('\n');
/*     */     }
/* 214 */     buf.append("endbfrange\nendcmap\nCMapName currentdict /CMap defineresource pop\nend end\n");
/*     */ 
/* 219 */     String s = buf.toString();
/* 220 */     PdfStream stream = new PdfStream(PdfEncodings.convertToBytes(s, null));
/* 221 */     stream.flateCompress(this.compressionLevel);
/* 222 */     return stream;
/*     */   }
/*     */ 
/*     */   private static String toHex4(int n) {
/* 226 */     String s = "0000" + Integer.toHexString(n);
/* 227 */     return s.substring(s.length() - 4);
/*     */   }
/*     */ 
/*     */   static String toHex(int n)
/*     */   {
/* 235 */     if (n < 65536)
/* 236 */       return "<" + toHex4(n) + ">";
/* 237 */     n -= 65536;
/* 238 */     int high = n / 1024 + 55296;
/* 239 */     int low = n % 1024 + 56320;
/* 240 */     return "[<" + toHex4(high) + toHex4(low) + ">]";
/*     */   }
/*     */ 
/*     */   public PdfDictionary getCIDFontType2(PdfIndirectReference fontDescriptor, String subsetPrefix, Object[] metrics)
/*     */   {
/* 250 */     PdfDictionary dic = new PdfDictionary(PdfName.FONT);
/*     */ 
/* 252 */     if (this.cff) {
/* 253 */       dic.put(PdfName.SUBTYPE, PdfName.CIDFONTTYPE0);
/* 254 */       dic.put(PdfName.BASEFONT, new PdfName(subsetPrefix + this.fontName + "-" + this.encoding));
/*     */     }
/*     */     else {
/* 257 */       dic.put(PdfName.SUBTYPE, PdfName.CIDFONTTYPE2);
/* 258 */       dic.put(PdfName.BASEFONT, new PdfName(subsetPrefix + this.fontName));
/*     */     }
/* 260 */     dic.put(PdfName.FONTDESCRIPTOR, fontDescriptor);
/* 261 */     if (!this.cff)
/* 262 */       dic.put(PdfName.CIDTOGIDMAP, PdfName.IDENTITY);
/* 263 */     PdfDictionary cdic = new PdfDictionary();
/* 264 */     cdic.put(PdfName.REGISTRY, new PdfString("Adobe"));
/* 265 */     cdic.put(PdfName.ORDERING, new PdfString("Identity"));
/* 266 */     cdic.put(PdfName.SUPPLEMENT, new PdfNumber(0));
/* 267 */     dic.put(PdfName.CIDSYSTEMINFO, cdic);
/* 268 */     if (!this.vertical) {
/* 269 */       dic.put(PdfName.DW, new PdfNumber(1000));
/* 270 */       StringBuffer buf = new StringBuffer("[");
/* 271 */       int lastNumber = -10;
/* 272 */       boolean firstTime = true;
/* 273 */       for (int k = 0; k < metrics.length; k++) {
/* 274 */         int[] metric = (int[])metrics[k];
/* 275 */         if (metric[1] != 1000)
/*     */         {
/* 277 */           int m = metric[0];
/* 278 */           if (m == lastNumber + 1) {
/* 279 */             buf.append(' ').append(metric[1]);
/*     */           }
/*     */           else {
/* 282 */             if (!firstTime) {
/* 283 */               buf.append(']');
/*     */             }
/* 285 */             firstTime = false;
/* 286 */             buf.append(m).append('[').append(metric[1]);
/*     */           }
/* 288 */           lastNumber = m;
/*     */         }
/*     */       }
/* 290 */       if (buf.length() > 1) {
/* 291 */         buf.append("]]");
/* 292 */         dic.put(PdfName.W, new PdfLiteral(buf.toString()));
/*     */       }
/*     */     }
/* 295 */     return dic;
/*     */   }
/*     */ 
/*     */   public PdfDictionary getFontBaseType(PdfIndirectReference descendant, String subsetPrefix, PdfIndirectReference toUnicode)
/*     */   {
/* 305 */     PdfDictionary dic = new PdfDictionary(PdfName.FONT);
/*     */ 
/* 307 */     dic.put(PdfName.SUBTYPE, PdfName.TYPE0);
/*     */ 
/* 309 */     if (this.cff) {
/* 310 */       dic.put(PdfName.BASEFONT, new PdfName(subsetPrefix + this.fontName + "-" + this.encoding));
/*     */     }
/*     */     else {
/* 313 */       dic.put(PdfName.BASEFONT, new PdfName(subsetPrefix + this.fontName));
/*     */     }
/* 315 */     dic.put(PdfName.ENCODING, new PdfName(this.encoding));
/* 316 */     dic.put(PdfName.DESCENDANTFONTS, new PdfArray(descendant));
/* 317 */     if (toUnicode != null)
/* 318 */       dic.put(PdfName.TOUNICODE, toUnicode);
/* 319 */     return dic;
/*     */   }
/*     */ 
/*     */   public int compare(int[] o1, int[] o2)
/*     */   {
/* 328 */     int m1 = o1[0];
/* 329 */     int m2 = o2[0];
/* 330 */     if (m1 < m2)
/* 331 */       return -1;
/* 332 */     if (m1 == m2)
/* 333 */       return 0;
/* 334 */     return 1;
/*     */   }
/*     */ 
/*     */   void writeFont(PdfWriter writer, PdfIndirectReference ref, Object[] params)
/*     */     throws DocumentException, IOException
/*     */   {
/* 348 */     writer.getTtfUnicodeWriter().writeFont(this, ref, params, rotbits);
/*     */   }
/*     */ 
/*     */   public PdfStream getFullFontStream()
/*     */     throws IOException, DocumentException
/*     */   {
/* 358 */     if (this.cff) {
/* 359 */       return new BaseFont.StreamFont(readCffFont(), "CIDFontType0C", this.compressionLevel);
/*     */     }
/* 361 */     return super.getFullFontStream();
/*     */   }
/*     */ 
/*     */   public byte[] convertToBytes(String text)
/*     */   {
/* 370 */     return null;
/*     */   }
/*     */ 
/*     */   byte[] convertToBytes(int char1)
/*     */   {
/* 375 */     return null;
/*     */   }
/*     */ 
/*     */   public int[] getMetricsTT(int c)
/*     */   {
/* 384 */     if (this.cmapExt != null)
/* 385 */       return (int[])this.cmapExt.get(Integer.valueOf(c));
/* 386 */     HashMap map = null;
/* 387 */     if (this.fontSpecific)
/* 388 */       map = this.cmap10;
/*     */     else
/* 390 */       map = this.cmap31;
/* 391 */     if (map == null)
/* 392 */       return null;
/* 393 */     if (this.fontSpecific) {
/* 394 */       if (((c & 0xFFFFFF00) == 0) || ((c & 0xFFFFFF00) == 61440)) {
/* 395 */         return (int[])map.get(Integer.valueOf(c & 0xFF));
/*     */       }
/* 397 */       return null;
/*     */     }
/*     */ 
/* 400 */     return (int[])map.get(Integer.valueOf(c));
/*     */   }
/*     */ 
/*     */   public boolean charExists(int c)
/*     */   {
/* 411 */     return getMetricsTT(c) != null;
/*     */   }
/*     */ 
/*     */   public boolean setCharAdvance(int c, int advance)
/*     */   {
/* 423 */     int[] m = getMetricsTT(c);
/* 424 */     if (m == null)
/* 425 */       return false;
/* 426 */     m[1] = advance;
/* 427 */     return true;
/*     */   }
/*     */ 
/*     */   public int[] getCharBBox(int c)
/*     */   {
/* 432 */     if (this.bboxes == null)
/* 433 */       return null;
/* 434 */     int[] m = getMetricsTT(c);
/* 435 */     if (m == null)
/* 436 */       return null;
/* 437 */     return this.bboxes[m[0]];
/*     */   }
/*     */ 
/*     */   protected Map<String, Glyph> getGlyphSubstitutionMap() {
/* 441 */     return this.glyphSubstitutionMap;
/*     */   }
/*     */ 
/*     */   Language getSupportedLanguage() {
/* 445 */     return this.supportedLanguage;
/*     */   }
/*     */ 
/*     */   private void readGsubTable() throws IOException {
/* 449 */     if (this.tables.get("GSUB") != null)
/*     */     {
/* 451 */       Map glyphToCharacterMap = new HashMap(this.cmap31.size());
/*     */ 
/* 453 */       for (Integer charCode : this.cmap31.keySet()) {
/* 454 */         char c = (char)charCode.intValue();
/* 455 */         int glyphCode = ((int[])this.cmap31.get(charCode))[0];
/* 456 */         glyphToCharacterMap.put(Integer.valueOf(glyphCode), Character.valueOf(c));
/*     */       }
/*     */ 
/* 459 */       GlyphSubstitutionTableReader gsubReader = new GlyphSubstitutionTableReader(this.fileName, ((int[])this.tables.get("GSUB"))[0], glyphToCharacterMap, this.glyphWidthsByIndex);
/*     */       try
/*     */       {
/* 463 */         gsubReader.read();
/* 464 */         this.supportedLanguage = gsubReader.getSupportedLanguage();
/*     */ 
/* 466 */         if (SUPPORTED_LANGUAGES_FOR_OTF.contains(this.supportedLanguage)) {
/* 467 */           this.glyphSubstitutionMap = gsubReader.getGlyphSubstitutionMap();
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 489 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.TrueTypeFontUnicode
 * JD-Core Version:    0.6.2
 */