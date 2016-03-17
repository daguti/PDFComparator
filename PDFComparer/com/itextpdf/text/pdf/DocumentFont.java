/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.Utilities;
/*     */ import com.itextpdf.text.io.RandomAccessSourceFactory;
/*     */ import com.itextpdf.text.pdf.fonts.cmaps.CMapParserEx;
/*     */ import com.itextpdf.text.pdf.fonts.cmaps.CMapToUnicode;
/*     */ import com.itextpdf.text.pdf.fonts.cmaps.CidLocationFromByte;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ 
/*     */ public class DocumentFont extends BaseFont
/*     */ {
/*  63 */   private HashMap<Integer, int[]> metrics = new HashMap();
/*     */   private String fontName;
/*     */   private PRIndirectReference refFont;
/*     */   private PdfDictionary font;
/*  67 */   private IntHashtable uni2byte = new IntHashtable();
/*  68 */   private IntHashtable byte2uni = new IntHashtable();
/*     */   private IntHashtable diffmap;
/*  70 */   private float ascender = 800.0F;
/*  71 */   private float capHeight = 700.0F;
/*  72 */   private float descender = -200.0F;
/*  73 */   private float italicAngle = 0.0F;
/*  74 */   private float fontWeight = 0.0F;
/*  75 */   private float llx = -50.0F;
/*  76 */   private float lly = -200.0F;
/*  77 */   private float urx = 100.0F;
/*  78 */   private float ury = 900.0F;
/*  79 */   protected boolean isType0 = false;
/*  80 */   protected int defaultWidth = 1000;
/*     */   private IntHashtable hMetrics;
/*     */   protected String cjkEncoding;
/*     */   protected String uniMap;
/*     */   private BaseFont cjkMirror;
/*  87 */   private static final int[] stdEnc = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32, 33, 34, 35, 36, 37, 38, 8217, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 8216, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 161, 162, 163, 8260, 165, 402, 167, 164, 39, 8220, 171, 8249, 8250, 64257, 64258, 0, 8211, 8224, 8225, 183, 0, 182, 8226, 8218, 8222, 8221, 187, 8230, 8240, 0, 191, 0, 96, 180, 710, 732, 175, 728, 729, 168, 0, 730, 184, 0, 733, 731, 711, 8212, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 198, 0, 170, 0, 0, 0, 0, 321, 216, 338, 186, 0, 0, 0, 0, 0, 230, 0, 0, 0, 305, 0, 0, 322, 248, 339, 223, 0, 0, 0, 0 };
/*     */ 
/*     */   DocumentFont(PdfDictionary font)
/*     */   {
/* 107 */     this.refFont = null;
/* 108 */     this.font = font;
/* 109 */     init();
/*     */   }
/*     */ 
/*     */   DocumentFont(PRIndirectReference refFont) {
/* 113 */     this.refFont = refFont;
/* 114 */     this.font = ((PdfDictionary)PdfReader.getPdfObject(refFont));
/* 115 */     init();
/*     */   }
/*     */ 
/*     */   public PdfDictionary getFontDictionary() {
/* 119 */     return this.font;
/*     */   }
/*     */ 
/*     */   private void init() {
/* 123 */     this.encoding = "";
/* 124 */     this.fontSpecific = false;
/* 125 */     this.fontType = 4;
/* 126 */     PdfName baseFont = this.font.getAsName(PdfName.BASEFONT);
/* 127 */     this.fontName = (baseFont != null ? PdfName.decodeName(baseFont.toString()) : "Unspecified Font Name");
/* 128 */     PdfName subType = this.font.getAsName(PdfName.SUBTYPE);
/* 129 */     if ((PdfName.TYPE1.equals(subType)) || (PdfName.TRUETYPE.equals(subType))) {
/* 130 */       doType1TT();
/* 131 */     } else if (PdfName.TYPE3.equals(subType))
/*     */     {
/* 137 */       fillEncoding(null);
/*     */     }
/*     */     else {
/* 140 */       PdfName encodingName = this.font.getAsName(PdfName.ENCODING);
/* 141 */       if (encodingName != null) {
/* 142 */         String enc = PdfName.decodeName(encodingName.toString());
/* 143 */         String ffontname = CJKFont.GetCompatibleFont(enc);
/* 144 */         if (ffontname != null) {
/*     */           try {
/* 146 */             this.cjkMirror = BaseFont.createFont(ffontname, enc, false);
/*     */           }
/*     */           catch (Exception e) {
/* 149 */             throw new ExceptionConverter(e);
/*     */           }
/* 151 */           this.cjkEncoding = enc;
/* 152 */           this.uniMap = ((CJKFont)this.cjkMirror).getUniMap();
/*     */         }
/* 154 */         if (PdfName.TYPE0.equals(subType)) {
/* 155 */           this.isType0 = true;
/* 156 */           if ((!enc.equals("Identity-H")) && (this.cjkMirror != null)) {
/* 157 */             PdfArray df = (PdfArray)PdfReader.getPdfObjectRelease(this.font.get(PdfName.DESCENDANTFONTS));
/* 158 */             PdfDictionary cidft = (PdfDictionary)PdfReader.getPdfObjectRelease(df.getPdfObject(0));
/* 159 */             PdfNumber dwo = (PdfNumber)PdfReader.getPdfObjectRelease(cidft.get(PdfName.DW));
/* 160 */             if (dwo != null)
/* 161 */               this.defaultWidth = dwo.intValue();
/* 162 */             this.hMetrics = readWidths((PdfArray)PdfReader.getPdfObjectRelease(cidft.get(PdfName.W)));
/*     */ 
/* 164 */             PdfDictionary fontDesc = (PdfDictionary)PdfReader.getPdfObjectRelease(cidft.get(PdfName.FONTDESCRIPTOR));
/* 165 */             fillFontDesc(fontDesc);
/*     */           } else {
/* 167 */             processType0(this.font);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void processType0(PdfDictionary font) {
/*     */     try {
/* 176 */       PdfObject toUniObject = PdfReader.getPdfObjectRelease(font.get(PdfName.TOUNICODE));
/* 177 */       PdfArray df = (PdfArray)PdfReader.getPdfObjectRelease(font.get(PdfName.DESCENDANTFONTS));
/* 178 */       PdfDictionary cidft = (PdfDictionary)PdfReader.getPdfObjectRelease(df.getPdfObject(0));
/* 179 */       PdfNumber dwo = (PdfNumber)PdfReader.getPdfObjectRelease(cidft.get(PdfName.DW));
/* 180 */       int dw = 1000;
/* 181 */       if (dwo != null)
/* 182 */         dw = dwo.intValue();
/* 183 */       IntHashtable widths = readWidths((PdfArray)PdfReader.getPdfObjectRelease(cidft.get(PdfName.W)));
/* 184 */       PdfDictionary fontDesc = (PdfDictionary)PdfReader.getPdfObjectRelease(cidft.get(PdfName.FONTDESCRIPTOR));
/* 185 */       fillFontDesc(fontDesc);
/* 186 */       if ((toUniObject instanceof PRStream))
/* 187 */         fillMetrics(PdfReader.getStreamBytes((PRStream)toUniObject), widths, dw);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 191 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private IntHashtable readWidths(PdfArray ws) {
/* 196 */     IntHashtable hh = new IntHashtable();
/* 197 */     if (ws == null)
/* 198 */       return hh;
/* 199 */     for (int k = 0; k < ws.size(); k++) {
/* 200 */       int c1 = ((PdfNumber)PdfReader.getPdfObjectRelease(ws.getPdfObject(k))).intValue();
/* 201 */       PdfObject obj = PdfReader.getPdfObjectRelease(ws.getPdfObject(++k));
/* 202 */       if (obj.isArray()) {
/* 203 */         PdfArray a2 = (PdfArray)obj;
/* 204 */         for (int j = 0; j < a2.size(); j++) {
/* 205 */           int c2 = ((PdfNumber)PdfReader.getPdfObjectRelease(a2.getPdfObject(j))).intValue();
/* 206 */           hh.put(c1++, c2);
/*     */         }
/*     */       }
/*     */       else {
/* 210 */         int c2 = ((PdfNumber)obj).intValue();
/* 211 */         int w = ((PdfNumber)PdfReader.getPdfObjectRelease(ws.getPdfObject(++k))).intValue();
/* 212 */         for (; c1 <= c2; c1++)
/* 213 */           hh.put(c1, w);
/*     */       }
/*     */     }
/* 216 */     return hh;
/*     */   }
/*     */ 
/*     */   private String decodeString(PdfString ps) {
/* 220 */     if (ps.isHexWriting()) {
/* 221 */       return PdfEncodings.convertToString(ps.getBytes(), "UnicodeBigUnmarked");
/*     */     }
/* 223 */     return ps.toUnicodeString();
/*     */   }
/*     */ 
/*     */   private void fillMetrics(byte[] touni, IntHashtable widths, int dw) {
/*     */     try {
/* 228 */       PdfContentParser ps = new PdfContentParser(new PRTokeniser(new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(touni))));
/* 229 */       PdfObject ob = null;
/* 230 */       boolean notFound = true;
/* 231 */       int nestLevel = 0;
/* 232 */       int maxExc = 50;
/* 233 */       while ((notFound) || (nestLevel > 0)) {
/*     */         try {
/* 235 */           ob = ps.readPRObject();
/*     */         }
/*     */         catch (Exception ex) {
/* 238 */           maxExc--; if (maxExc >= 0) break label80; 
/* 239 */         }break;
/* 240 */         label80: continue;
/*     */ 
/* 242 */         if (ob != null)
/*     */         {
/* 244 */           if (ob.type() == 200) {
/* 245 */             if (ob.toString().equals("begin")) {
/* 246 */               notFound = false;
/* 247 */               nestLevel++;
/*     */             }
/* 249 */             else if (ob.toString().equals("end")) {
/* 250 */               nestLevel--;
/*     */             } else {
/* 252 */               if (ob.toString().equals("beginbfchar")) {
/*     */                 while (true) {
/* 254 */                   PdfObject nx = ps.readPRObject();
/* 255 */                   if (nx.toString().equals("endbfchar"))
/*     */                     break;
/* 257 */                   String cid = decodeString((PdfString)nx);
/* 258 */                   String uni = decodeString((PdfString)ps.readPRObject());
/* 259 */                   if (uni.length() == 1) {
/* 260 */                     int cidc = cid.charAt(0);
/* 261 */                     int unic = uni.charAt(uni.length() - 1);
/* 262 */                     int w = dw;
/* 263 */                     if (widths.containsKey(cidc))
/* 264 */                       w = widths.get(cidc);
/* 265 */                     this.metrics.put(Integer.valueOf(unic), new int[] { cidc, w });
/*     */                   }
/*     */                 }
/*     */               }
/* 269 */               if (ob.toString().equals("beginbfrange")) {
/*     */                 while (true) {
/* 271 */                   PdfObject nx = ps.readPRObject();
/* 272 */                   if (nx.toString().equals("endbfrange"))
/*     */                     break;
/* 274 */                   String cid1 = decodeString((PdfString)nx);
/* 275 */                   String cid2 = decodeString((PdfString)ps.readPRObject());
/* 276 */                   int cid1c = cid1.charAt(0);
/* 277 */                   int cid2c = cid2.charAt(0);
/* 278 */                   PdfObject ob2 = ps.readPRObject();
/* 279 */                   if (ob2.isString()) {
/* 280 */                     String uni = decodeString((PdfString)ob2);
/* 281 */                     if (uni.length() == 1) {
/* 282 */                       for (int unic = uni.charAt(uni.length() - 1); 
/* 283 */                         cid1c <= cid2c; unic++) {
/* 284 */                         int w = dw;
/* 285 */                         if (widths.containsKey(cid1c))
/* 286 */                           w = widths.get(cid1c);
/* 287 */                         this.metrics.put(Integer.valueOf(unic), new int[] { cid1c, w });
/*     */ 
/* 283 */                         cid1c++;
/*     */                       }
/*     */ 
/*     */                     }
/*     */ 
/*     */                   }
/*     */                   else
/*     */                   {
/* 292 */                     PdfArray a = (PdfArray)ob2;
/* 293 */                     for (int j = 0; j < a.size(); cid1c++) {
/* 294 */                       String uni = decodeString(a.getAsString(j));
/* 295 */                       if (uni.length() == 1) {
/* 296 */                         int unic = uni.charAt(uni.length() - 1);
/* 297 */                         int w = dw;
/* 298 */                         if (widths.containsKey(cid1c))
/* 299 */                           w = widths.get(cid1c);
/* 300 */                         this.metrics.put(Integer.valueOf(unic), new int[] { cid1c, w });
/*     */                       }
/* 293 */                       j++;
/*     */                     }
/*     */                   }
/*     */                 }
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 310 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void doType1TT() {
/* 315 */     CMapToUnicode toUnicode = null;
/* 316 */     PdfObject enc = PdfReader.getPdfObject(this.font.get(PdfName.ENCODING));
/* 317 */     if (enc == null) {
/* 318 */       PdfName baseFont = this.font.getAsName(PdfName.BASEFONT);
/* 319 */       if ((BuiltinFonts14.containsKey(this.fontName)) && ((PdfName.SYMBOL.equals(baseFont)) || (PdfName.ZAPFDINGBATS.equals(baseFont))))
/*     */       {
/* 321 */         fillEncoding(baseFont);
/*     */       }
/* 323 */       else fillEncoding(null); try
/*     */       {
/* 325 */         toUnicode = processToUnicode();
/* 326 */         if (toUnicode != null) {
/* 327 */           Map rm = toUnicode.createReverseMapping();
/* 328 */           for (Map.Entry kv : rm.entrySet()) {
/* 329 */             this.uni2byte.put(((Integer)kv.getKey()).intValue(), ((Integer)kv.getValue()).intValue());
/* 330 */             this.byte2uni.put(((Integer)kv.getValue()).intValue(), ((Integer)kv.getKey()).intValue());
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (Exception ex) {
/* 335 */         throw new ExceptionConverter(ex);
/*     */       }
/*     */ 
/*     */     }
/* 339 */     else if (enc.isName()) {
/* 340 */       fillEncoding((PdfName)enc);
/* 341 */     } else if (enc.isDictionary()) {
/* 342 */       PdfDictionary encDic = (PdfDictionary)enc;
/* 343 */       enc = PdfReader.getPdfObject(encDic.get(PdfName.BASEENCODING));
/* 344 */       if (enc == null)
/* 345 */         fillEncoding(null);
/*     */       else
/* 347 */         fillEncoding((PdfName)enc);
/* 348 */       PdfArray diffs = encDic.getAsArray(PdfName.DIFFERENCES);
/* 349 */       if (diffs != null) {
/* 350 */         this.diffmap = new IntHashtable();
/* 351 */         int currentNumber = 0;
/* 352 */         for (int k = 0; k < diffs.size(); k++) {
/* 353 */           PdfObject obj = diffs.getPdfObject(k);
/* 354 */           if (obj.isNumber()) {
/* 355 */             currentNumber = ((PdfNumber)obj).intValue();
/*     */           } else {
/* 357 */             int[] c = GlyphList.nameToUnicode(PdfName.decodeName(((PdfName)obj).toString()));
/* 358 */             if ((c != null) && (c.length > 0)) {
/* 359 */               this.uni2byte.put(c[0], currentNumber);
/* 360 */               this.byte2uni.put(currentNumber, c[0]);
/* 361 */               this.diffmap.put(c[0], currentNumber);
/*     */             }
/*     */             else {
/* 364 */               if (toUnicode == null) {
/* 365 */                 toUnicode = processToUnicode();
/* 366 */                 if (toUnicode == null) {
/* 367 */                   toUnicode = new CMapToUnicode();
/*     */                 }
/*     */               }
/* 370 */               String unicode = toUnicode.lookup(new byte[] { (byte)currentNumber }, 0, 1);
/* 371 */               if ((unicode != null) && (unicode.length() == 1)) {
/* 372 */                 this.uni2byte.put(unicode.charAt(0), currentNumber);
/* 373 */                 this.byte2uni.put(currentNumber, unicode.charAt(0));
/* 374 */                 this.diffmap.put(unicode.charAt(0), currentNumber);
/*     */               }
/*     */             }
/* 377 */             currentNumber++;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 383 */     PdfArray newWidths = this.font.getAsArray(PdfName.WIDTHS);
/* 384 */     PdfNumber first = this.font.getAsNumber(PdfName.FIRSTCHAR);
/* 385 */     PdfNumber last = this.font.getAsNumber(PdfName.LASTCHAR);
/* 386 */     if (BuiltinFonts14.containsKey(this.fontName)) {
/*     */       BaseFont bf;
/*     */       try {
/* 389 */         bf = BaseFont.createFont(this.fontName, "Cp1252", false);
/*     */       }
/*     */       catch (Exception e) {
/* 392 */         throw new ExceptionConverter(e);
/*     */       }
/* 394 */       int[] e = this.uni2byte.toOrderedKeys();
/* 395 */       for (int k = 0; k < e.length; k++) {
/* 396 */         int n = this.uni2byte.get(e[k]);
/* 397 */         this.widths[n] = bf.getRawWidth(n, GlyphList.unicodeToName(e[k]));
/*     */       }
/* 399 */       if (this.diffmap != null) {
/* 400 */         e = this.diffmap.toOrderedKeys();
/* 401 */         for (int k = 0; k < e.length; k++) {
/* 402 */           int n = this.diffmap.get(e[k]);
/* 403 */           this.widths[n] = bf.getRawWidth(n, GlyphList.unicodeToName(e[k]));
/*     */         }
/* 405 */         this.diffmap = null;
/*     */       }
/* 407 */       this.ascender = bf.getFontDescriptor(1, 1000.0F);
/* 408 */       this.capHeight = bf.getFontDescriptor(2, 1000.0F);
/* 409 */       this.descender = bf.getFontDescriptor(3, 1000.0F);
/* 410 */       this.italicAngle = bf.getFontDescriptor(4, 1000.0F);
/* 411 */       this.fontWeight = bf.getFontDescriptor(23, 1000.0F);
/* 412 */       this.llx = bf.getFontDescriptor(5, 1000.0F);
/* 413 */       this.lly = bf.getFontDescriptor(6, 1000.0F);
/* 414 */       this.urx = bf.getFontDescriptor(7, 1000.0F);
/* 415 */       this.ury = bf.getFontDescriptor(8, 1000.0F);
/*     */     }
/* 417 */     if ((first != null) && (last != null) && (newWidths != null)) {
/* 418 */       int f = first.intValue();
/* 419 */       int nSize = f + newWidths.size();
/* 420 */       if (this.widths.length < nSize) {
/* 421 */         int[] tmp = new int[nSize];
/* 422 */         System.arraycopy(this.widths, 0, tmp, 0, f);
/* 423 */         this.widths = tmp;
/*     */       }
/* 425 */       for (int k = 0; k < newWidths.size(); k++) {
/* 426 */         this.widths[(f + k)] = newWidths.getAsNumber(k).intValue();
/*     */       }
/*     */     }
/* 429 */     fillFontDesc(this.font.getAsDict(PdfName.FONTDESCRIPTOR));
/*     */   }
/*     */ 
/*     */   private CMapToUnicode processToUnicode() {
/* 433 */     CMapToUnicode cmapRet = null;
/* 434 */     PdfObject toUni = PdfReader.getPdfObjectRelease(this.font.get(PdfName.TOUNICODE));
/* 435 */     if ((toUni instanceof PRStream)) {
/*     */       try {
/* 437 */         byte[] touni = PdfReader.getStreamBytes((PRStream)toUni);
/* 438 */         CidLocationFromByte lb = new CidLocationFromByte(touni);
/* 439 */         cmapRet = new CMapToUnicode();
/* 440 */         CMapParserEx.parseCid("", cmapRet, lb);
/*     */       } catch (Exception e) {
/* 442 */         cmapRet = null;
/*     */       }
/*     */     }
/* 445 */     return cmapRet;
/*     */   }
/*     */ 
/*     */   private void fillFontDesc(PdfDictionary fontDesc) {
/* 449 */     if (fontDesc == null)
/* 450 */       return;
/* 451 */     PdfNumber v = fontDesc.getAsNumber(PdfName.ASCENT);
/* 452 */     if (v != null)
/* 453 */       this.ascender = v.floatValue();
/* 454 */     v = fontDesc.getAsNumber(PdfName.CAPHEIGHT);
/* 455 */     if (v != null)
/* 456 */       this.capHeight = v.floatValue();
/* 457 */     v = fontDesc.getAsNumber(PdfName.DESCENT);
/* 458 */     if (v != null)
/* 459 */       this.descender = v.floatValue();
/* 460 */     v = fontDesc.getAsNumber(PdfName.ITALICANGLE);
/* 461 */     if (v != null)
/* 462 */       this.italicAngle = v.floatValue();
/* 463 */     v = fontDesc.getAsNumber(PdfName.FONTWEIGHT);
/* 464 */     if (v != null) {
/* 465 */       this.fontWeight = v.floatValue();
/*     */     }
/* 467 */     PdfArray bbox = fontDesc.getAsArray(PdfName.FONTBBOX);
/* 468 */     if (bbox != null) {
/* 469 */       this.llx = bbox.getAsNumber(0).floatValue();
/* 470 */       this.lly = bbox.getAsNumber(1).floatValue();
/* 471 */       this.urx = bbox.getAsNumber(2).floatValue();
/* 472 */       this.ury = bbox.getAsNumber(3).floatValue();
/* 473 */       if (this.llx > this.urx) {
/* 474 */         float t = this.llx;
/* 475 */         this.llx = this.urx;
/* 476 */         this.urx = t;
/*     */       }
/* 478 */       if (this.lly > this.ury) {
/* 479 */         float t = this.lly;
/* 480 */         this.lly = this.ury;
/* 481 */         this.ury = t;
/*     */       }
/*     */     }
/* 484 */     float maxAscent = Math.max(this.ury, this.ascender);
/* 485 */     float minDescent = Math.min(this.lly, this.descender);
/* 486 */     this.ascender = (maxAscent * 1000.0F / (maxAscent - minDescent));
/* 487 */     this.descender = (minDescent * 1000.0F / (maxAscent - minDescent));
/*     */   }
/*     */ 
/*     */   private void fillEncoding(PdfName encoding) {
/* 491 */     if ((encoding == null) && (isSymbolic())) {
/* 492 */       for (int k = 0; k < 256; k++) {
/* 493 */         this.uni2byte.put(k, k);
/* 494 */         this.byte2uni.put(k, k);
/*     */       }
/* 496 */     } else if ((PdfName.MAC_ROMAN_ENCODING.equals(encoding)) || (PdfName.WIN_ANSI_ENCODING.equals(encoding)) || (PdfName.SYMBOL.equals(encoding)) || (PdfName.ZAPFDINGBATS.equals(encoding)))
/*     */     {
/* 498 */       byte[] b = new byte[256];
/* 499 */       for (int k = 0; k < 256; k++)
/* 500 */         b[k] = ((byte)k);
/* 501 */       String enc = "Cp1252";
/* 502 */       if (PdfName.MAC_ROMAN_ENCODING.equals(encoding))
/* 503 */         enc = "MacRoman";
/* 504 */       else if (PdfName.SYMBOL.equals(encoding))
/* 505 */         enc = "Symbol";
/* 506 */       else if (PdfName.ZAPFDINGBATS.equals(encoding))
/* 507 */         enc = "ZapfDingbats";
/* 508 */       String cv = PdfEncodings.convertToString(b, enc);
/* 509 */       char[] arr = cv.toCharArray();
/* 510 */       for (int k = 0; k < 256; k++) {
/* 511 */         this.uni2byte.put(arr[k], k);
/* 512 */         this.byte2uni.put(k, arr[k]);
/*     */       }
/* 514 */       this.encoding = enc;
/*     */     }
/*     */     else {
/* 517 */       for (int k = 0; k < 256; k++) {
/* 518 */         this.uni2byte.put(stdEnc[k], k);
/* 519 */         this.byte2uni.put(k, stdEnc[k]);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String[][] getFamilyFontName()
/*     */   {
/* 535 */     return getFullFontName();
/*     */   }
/*     */ 
/*     */   public float getFontDescriptor(int key, float fontSize)
/*     */   {
/* 549 */     if (this.cjkMirror != null)
/* 550 */       return this.cjkMirror.getFontDescriptor(key, fontSize);
/* 551 */     switch (key) {
/*     */     case 1:
/*     */     case 9:
/* 554 */       return this.ascender * fontSize / 1000.0F;
/*     */     case 2:
/* 556 */       return this.capHeight * fontSize / 1000.0F;
/*     */     case 3:
/*     */     case 10:
/* 559 */       return this.descender * fontSize / 1000.0F;
/*     */     case 4:
/* 561 */       return this.italicAngle;
/*     */     case 5:
/* 563 */       return this.llx * fontSize / 1000.0F;
/*     */     case 6:
/* 565 */       return this.lly * fontSize / 1000.0F;
/*     */     case 7:
/* 567 */       return this.urx * fontSize / 1000.0F;
/*     */     case 8:
/* 569 */       return this.ury * fontSize / 1000.0F;
/*     */     case 11:
/* 571 */       return 0.0F;
/*     */     case 12:
/* 573 */       return (this.urx - this.llx) * fontSize / 1000.0F;
/*     */     case 23:
/* 575 */       return this.fontWeight * fontSize / 1000.0F;
/*     */     case 13:
/*     */     case 14:
/*     */     case 15:
/*     */     case 16:
/*     */     case 17:
/*     */     case 18:
/*     */     case 19:
/*     */     case 20:
/*     */     case 21:
/* 577 */     case 22: } return 0.0F;
/*     */   }
/*     */ 
/*     */   public String[][] getFullFontName()
/*     */   {
/* 591 */     return new String[][] { { "", "", "", this.fontName } };
/*     */   }
/*     */ 
/*     */   public String[][] getAllNameEntries()
/*     */   {
/* 605 */     return new String[][] { { "4", "", "", "", this.fontName } };
/*     */   }
/*     */ 
/*     */   public int getKerning(int char1, int char2)
/*     */   {
/* 616 */     return 0;
/*     */   }
/*     */ 
/*     */   public String getPostscriptFontName()
/*     */   {
/* 625 */     return this.fontName;
/*     */   }
/*     */ 
/*     */   int getRawWidth(int c, String name)
/*     */   {
/* 637 */     return 0;
/*     */   }
/*     */ 
/*     */   public boolean hasKernPairs()
/*     */   {
/* 646 */     return false;
/*     */   }
/*     */ 
/*     */   void writeFont(PdfWriter writer, PdfIndirectReference ref, Object[] params)
/*     */     throws DocumentException, IOException
/*     */   {
/*     */   }
/*     */ 
/*     */   public PdfStream getFullFontStream()
/*     */   {
/* 668 */     return null;
/*     */   }
/*     */ 
/*     */   public int getWidth(int char1)
/*     */   {
/* 678 */     if (this.isType0) {
/* 679 */       if ((this.hMetrics != null) && (this.cjkMirror != null) && (!this.cjkMirror.isVertical())) {
/* 680 */         int c = this.cjkMirror.getCidCode(char1);
/* 681 */         int v = this.hMetrics.get(c);
/* 682 */         if (v > 0) {
/* 683 */           return v;
/*     */         }
/* 685 */         return this.defaultWidth;
/*     */       }
/* 687 */       int[] ws = (int[])this.metrics.get(Integer.valueOf(char1));
/* 688 */       if (ws != null) {
/* 689 */         return ws[1];
/*     */       }
/* 691 */       return 0;
/*     */     }
/*     */ 
/* 694 */     if (this.cjkMirror != null)
/* 695 */       return this.cjkMirror.getWidth(char1);
/* 696 */     return super.getWidth(char1);
/*     */   }
/*     */ 
/*     */   public int getWidth(String text)
/*     */   {
/* 701 */     if (this.isType0) {
/* 702 */       int total = 0;
/* 703 */       if ((this.hMetrics != null) && (this.cjkMirror != null) && (!this.cjkMirror.isVertical())) {
/* 704 */         if (((CJKFont)this.cjkMirror).isIdentity()) {
/* 705 */           for (int k = 0; k < text.length(); k++) {
/* 706 */             total += getWidth(text.charAt(k));
/*     */           }
/*     */         }
/*     */         else
/* 710 */           for (int k = 0; k < text.length(); k++)
/*     */           {
/*     */             int val;
/* 712 */             if (Utilities.isSurrogatePair(text, k)) {
/* 713 */               int val = Utilities.convertToUtf32(text, k);
/* 714 */               k++;
/*     */             }
/*     */             else {
/* 717 */               val = text.charAt(k);
/*     */             }
/* 719 */             total += getWidth(val);
/*     */           }
/*     */       }
/*     */       else {
/* 723 */         char[] chars = text.toCharArray();
/* 724 */         int len = chars.length;
/* 725 */         for (int k = 0; k < len; k++) {
/* 726 */           int[] ws = (int[])this.metrics.get(Integer.valueOf(chars[k]));
/* 727 */           if (ws != null)
/* 728 */             total += ws[1];
/*     */         }
/*     */       }
/* 731 */       return total;
/*     */     }
/* 733 */     if (this.cjkMirror != null)
/* 734 */       return this.cjkMirror.getWidth(text);
/* 735 */     return super.getWidth(text);
/*     */   }
/*     */ 
/*     */   public byte[] convertToBytes(String text)
/*     */   {
/* 740 */     if (this.cjkMirror != null)
/* 741 */       return this.cjkMirror.convertToBytes(text);
/* 742 */     if (this.isType0) {
/* 743 */       char[] chars = text.toCharArray();
/* 744 */       int len = chars.length;
/* 745 */       byte[] b = new byte[len * 2];
/* 746 */       int bptr = 0;
/* 747 */       for (int k = 0; k < len; k++) {
/* 748 */         int[] ws = (int[])this.metrics.get(Integer.valueOf(chars[k]));
/* 749 */         if (ws != null) {
/* 750 */           int g = ws[0];
/* 751 */           b[(bptr++)] = ((byte)(g / 256));
/* 752 */           b[(bptr++)] = ((byte)g);
/*     */         }
/*     */       }
/* 755 */       if (bptr == b.length) {
/* 756 */         return b;
/*     */       }
/* 758 */       byte[] nb = new byte[bptr];
/* 759 */       System.arraycopy(b, 0, nb, 0, bptr);
/* 760 */       return nb;
/*     */     }
/*     */ 
/* 764 */     char[] cc = text.toCharArray();
/* 765 */     byte[] b = new byte[cc.length];
/* 766 */     int ptr = 0;
/* 767 */     for (int k = 0; k < cc.length; k++) {
/* 768 */       if (this.uni2byte.containsKey(cc[k]))
/* 769 */         b[(ptr++)] = ((byte)this.uni2byte.get(cc[k]));
/*     */     }
/* 771 */     if (ptr == b.length) {
/* 772 */       return b;
/*     */     }
/* 774 */     byte[] b2 = new byte[ptr];
/* 775 */     System.arraycopy(b, 0, b2, 0, ptr);
/* 776 */     return b2;
/*     */   }
/*     */ 
/*     */   byte[] convertToBytes(int char1)
/*     */   {
/* 783 */     if (this.cjkMirror != null)
/* 784 */       return this.cjkMirror.convertToBytes(char1);
/* 785 */     if (this.isType0) {
/* 786 */       int[] ws = (int[])this.metrics.get(Integer.valueOf(char1));
/* 787 */       if (ws != null) {
/* 788 */         int g = ws[0];
/* 789 */         return new byte[] { (byte)(g / 256), (byte)g };
/*     */       }
/*     */ 
/* 792 */       return new byte[0];
/*     */     }
/*     */ 
/* 795 */     if (this.uni2byte.containsKey(char1)) {
/* 796 */       return new byte[] { (byte)this.uni2byte.get(char1) };
/*     */     }
/* 798 */     return new byte[0];
/*     */   }
/*     */ 
/*     */   PdfIndirectReference getIndirectReference()
/*     */   {
/* 803 */     if (this.refFont == null)
/* 804 */       throw new IllegalArgumentException("Font reuse not allowed with direct font objects.");
/* 805 */     return this.refFont;
/*     */   }
/*     */ 
/*     */   public boolean charExists(int c)
/*     */   {
/* 810 */     if (this.cjkMirror != null)
/* 811 */       return this.cjkMirror.charExists(c);
/* 812 */     if (this.isType0) {
/* 813 */       return this.metrics.containsKey(Integer.valueOf(c));
/*     */     }
/*     */ 
/* 816 */     return super.charExists(c);
/*     */   }
/*     */ 
/*     */   public void setPostscriptFontName(String name)
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean setKerning(int char1, int char2, int kern)
/*     */   {
/* 830 */     return false;
/*     */   }
/*     */ 
/*     */   public int[] getCharBBox(int c)
/*     */   {
/* 835 */     return null;
/*     */   }
/*     */ 
/*     */   protected int[] getRawCharBBox(int c, String name)
/*     */   {
/* 840 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean isVertical()
/*     */   {
/* 845 */     if (this.cjkMirror != null) {
/* 846 */       return this.cjkMirror.isVertical();
/*     */     }
/* 848 */     return super.isVertical();
/*     */   }
/*     */ 
/*     */   IntHashtable getUni2Byte()
/*     */   {
/* 857 */     return this.uni2byte;
/*     */   }
/*     */ 
/*     */   IntHashtable getByte2Uni()
/*     */   {
/* 866 */     return this.byte2uni;
/*     */   }
/*     */ 
/*     */   IntHashtable getDiffmap()
/*     */   {
/* 875 */     return this.diffmap;
/*     */   }
/*     */ 
/*     */   boolean isSymbolic() {
/* 879 */     PdfDictionary fontDescriptor = this.font.getAsDict(PdfName.FONTDESCRIPTOR);
/* 880 */     if (fontDescriptor == null)
/* 881 */       return false;
/* 882 */     PdfNumber flags = fontDescriptor.getAsNumber(PdfName.FLAGS);
/* 883 */     if (flags == null)
/* 884 */       return false;
/* 885 */     return (flags.intValue() & 0x4) != 0;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.DocumentFont
 * JD-Core Version:    0.6.2
 */