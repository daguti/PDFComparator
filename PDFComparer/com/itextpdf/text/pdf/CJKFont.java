/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.Utilities;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.io.StreamUtil;
/*     */ import com.itextpdf.text.pdf.fonts.cmaps.CMapCache;
/*     */ import com.itextpdf.text.pdf.fonts.cmaps.CMapCidByte;
/*     */ import com.itextpdf.text.pdf.fonts.cmaps.CMapCidUni;
/*     */ import com.itextpdf.text.pdf.fonts.cmaps.CMapUniCid;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ class CJKFont extends BaseFont
/*     */ {
/*     */   static final String CJK_ENCODING = "UnicodeBigUnmarked";
/*     */   private static final int FIRST = 0;
/*     */   private static final int BRACKET = 1;
/*     */   private static final int SERIAL = 2;
/*     */   private static final int V1Y = 880;
/*  83 */   static Properties cjkFonts = new Properties();
/*  84 */   static Properties cjkEncodings = new Properties();
/*  85 */   private static final HashMap<String, HashMap<String, Object>> allFonts = new HashMap();
/*  86 */   private static boolean propertiesLoaded = false;
/*     */   public static final String RESOURCE_PATH_CMAP = "com/itextpdf/text/pdf/fonts/cmaps/";
/*  90 */   private static final HashMap<String, Set<String>> registryNames = new HashMap();
/*     */   private CMapCidByte cidByte;
/*     */   private CMapUniCid uniCid;
/*     */   private CMapCidUni cidUni;
/*     */   private String uniMap;
/*     */   private String fontName;
/*  99 */   private String style = "";
/*     */   private String CMap;
/* 103 */   private boolean cidDirect = false;
/*     */   private IntHashtable vMetrics;
/*     */   private IntHashtable hMetrics;
/*     */   private HashMap<String, Object> fontDesc;
/*     */ 
/*     */   private static void loadProperties()
/*     */   {
/* 111 */     if (propertiesLoaded)
/* 112 */       return;
/* 113 */     synchronized (allFonts) {
/* 114 */       if (propertiesLoaded)
/* 115 */         return;
/*     */       try {
/* 117 */         loadRegistry();
/* 118 */         for (String font : (Set)registryNames.get("fonts"))
/* 119 */           allFonts.put(font, readFontProperties(font));
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */       }
/* 124 */       propertiesLoaded = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void loadRegistry() throws IOException {
/* 129 */     InputStream is = StreamUtil.getResourceStream("com/itextpdf/text/pdf/fonts/cmaps/cjk_registry.properties");
/* 130 */     Properties p = new Properties();
/* 131 */     p.load(is);
/* 132 */     is.close();
/* 133 */     for (Iterator i$ = p.keySet().iterator(); i$.hasNext(); ) { Object key = i$.next();
/* 134 */       String value = p.getProperty((String)key);
/* 135 */       String[] sp = value.split(" ");
/* 136 */       Set hs = new HashSet();
/* 137 */       for (String s : sp) {
/* 138 */         if (s.length() > 0)
/* 139 */           hs.add(s);
/*     */       }
/* 141 */       registryNames.put((String)key, hs);
/*     */     }
/*     */   }
/*     */ 
/*     */   CJKFont(String fontName, String enc, boolean emb)
/*     */     throws DocumentException
/*     */   {
/* 152 */     loadProperties();
/* 153 */     this.fontType = 2;
/* 154 */     String nameBase = getBaseName(fontName);
/* 155 */     if (!isCJKFont(nameBase, enc))
/* 156 */       throw new DocumentException(MessageLocalization.getComposedMessage("font.1.with.2.encoding.is.not.a.cjk.font", new Object[] { fontName, enc }));
/* 157 */     if (nameBase.length() < fontName.length()) {
/* 158 */       this.style = fontName.substring(nameBase.length());
/* 159 */       fontName = nameBase;
/*     */     }
/* 161 */     this.fontName = fontName;
/* 162 */     this.encoding = "UnicodeBigUnmarked";
/* 163 */     this.vertical = enc.endsWith("V");
/* 164 */     this.CMap = enc;
/* 165 */     if ((enc.equals("Identity-H")) || (enc.equals("Identity-V")))
/* 166 */       this.cidDirect = true;
/* 167 */     loadCMaps();
/*     */   }
/*     */ 
/*     */   String getUniMap() {
/* 171 */     return this.uniMap;
/*     */   }
/*     */ 
/*     */   private void loadCMaps() throws DocumentException {
/*     */     try {
/* 176 */       this.fontDesc = ((HashMap)allFonts.get(this.fontName));
/* 177 */       this.hMetrics = ((IntHashtable)this.fontDesc.get("W"));
/* 178 */       this.vMetrics = ((IntHashtable)this.fontDesc.get("W2"));
/* 179 */       String registry = (String)this.fontDesc.get("Registry");
/* 180 */       this.uniMap = "";
/* 181 */       for (String name : (Set)registryNames.get(registry + "_Uni")) {
/* 182 */         this.uniMap = name;
/* 183 */         if ((name.endsWith("V")) && (this.vertical))
/*     */           break;
/* 185 */         if ((!name.endsWith("V")) && (!this.vertical))
/*     */           break;
/*     */       }
/* 188 */       if (this.cidDirect) {
/* 189 */         this.cidUni = CMapCache.getCachedCMapCidUni(this.uniMap);
/*     */       }
/*     */       else {
/* 192 */         this.uniCid = CMapCache.getCachedCMapUniCid(this.uniMap);
/* 193 */         this.cidByte = CMapCache.getCachedCMapCidByte(this.CMap);
/*     */       }
/*     */     }
/*     */     catch (Exception ex) {
/* 197 */       throw new DocumentException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String GetCompatibleFont(String enc)
/*     */   {
/* 207 */     loadProperties();
/* 208 */     String registry = null;
/* 209 */     for (Map.Entry e : registryNames.entrySet()) {
/* 210 */       if (((Set)e.getValue()).contains(enc)) {
/* 211 */         registry = (String)e.getKey();
/* 212 */         break;
/*     */       }
/*     */     }
/* 215 */     if (registry == null)
/* 216 */       return null;
/* 217 */     for (Map.Entry e : allFonts.entrySet()) {
/* 218 */       if (registry.equals(((HashMap)e.getValue()).get("Registry")))
/* 219 */         return (String)e.getKey();
/*     */     }
/* 221 */     return null;
/*     */   }
/*     */ 
/*     */   public static boolean isCJKFont(String fontName, String enc)
/*     */   {
/* 230 */     loadProperties();
/* 231 */     if (!registryNames.containsKey("fonts"))
/* 232 */       return false;
/* 233 */     if (!((Set)registryNames.get("fonts")).contains(fontName))
/* 234 */       return false;
/* 235 */     if ((enc.equals("Identity-H")) || (enc.equals("Identity-V")))
/* 236 */       return true;
/* 237 */     String registry = (String)((HashMap)allFonts.get(fontName)).get("Registry");
/* 238 */     Set encodings = (Set)registryNames.get(registry);
/* 239 */     return (encodings != null) && (encodings.contains(enc));
/*     */   }
/*     */ 
/*     */   public int getWidth(int char1)
/*     */   {
/* 249 */     int c = char1;
/* 250 */     if (!this.cidDirect)
/* 251 */       c = this.uniCid.lookup(char1);
/*     */     int v;
/*     */     int v;
/* 253 */     if (this.vertical)
/* 254 */       v = this.vMetrics.get(c);
/*     */     else
/* 256 */       v = this.hMetrics.get(c);
/* 257 */     if (v > 0) {
/* 258 */       return v;
/*     */     }
/* 260 */     return 1000;
/*     */   }
/*     */ 
/*     */   public int getWidth(String text)
/*     */   {
/* 265 */     int total = 0;
/* 266 */     if (this.cidDirect) {
/* 267 */       for (int k = 0; k < text.length(); k++) {
/* 268 */         total += getWidth(text.charAt(k));
/*     */       }
/*     */     }
/*     */     else {
/* 272 */       for (int k = 0; k < text.length(); k++)
/*     */       {
/*     */         int val;
/* 274 */         if (Utilities.isSurrogatePair(text, k)) {
/* 275 */           int val = Utilities.convertToUtf32(text, k);
/* 276 */           k++;
/*     */         }
/*     */         else {
/* 279 */           val = text.charAt(k);
/*     */         }
/* 281 */         total += getWidth(val);
/*     */       }
/*     */     }
/* 284 */     return total;
/*     */   }
/*     */ 
/*     */   int getRawWidth(int c, String name)
/*     */   {
/* 289 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getKerning(int char1, int char2)
/*     */   {
/* 294 */     return 0;
/*     */   }
/*     */ 
/*     */   private PdfDictionary getFontDescriptor() {
/* 298 */     PdfDictionary dic = new PdfDictionary(PdfName.FONTDESCRIPTOR);
/* 299 */     dic.put(PdfName.ASCENT, new PdfLiteral((String)this.fontDesc.get("Ascent")));
/* 300 */     dic.put(PdfName.CAPHEIGHT, new PdfLiteral((String)this.fontDesc.get("CapHeight")));
/* 301 */     dic.put(PdfName.DESCENT, new PdfLiteral((String)this.fontDesc.get("Descent")));
/* 302 */     dic.put(PdfName.FLAGS, new PdfLiteral((String)this.fontDesc.get("Flags")));
/* 303 */     dic.put(PdfName.FONTBBOX, new PdfLiteral((String)this.fontDesc.get("FontBBox")));
/* 304 */     dic.put(PdfName.FONTNAME, new PdfName(this.fontName + this.style));
/* 305 */     dic.put(PdfName.ITALICANGLE, new PdfLiteral((String)this.fontDesc.get("ItalicAngle")));
/* 306 */     dic.put(PdfName.STEMV, new PdfLiteral((String)this.fontDesc.get("StemV")));
/* 307 */     PdfDictionary pdic = new PdfDictionary();
/* 308 */     pdic.put(PdfName.PANOSE, new PdfString((String)this.fontDesc.get("Panose"), null));
/* 309 */     dic.put(PdfName.STYLE, pdic);
/* 310 */     return dic;
/*     */   }
/*     */ 
/*     */   private PdfDictionary getCIDFont(PdfIndirectReference fontDescriptor, IntHashtable cjkTag) {
/* 314 */     PdfDictionary dic = new PdfDictionary(PdfName.FONT);
/* 315 */     dic.put(PdfName.SUBTYPE, PdfName.CIDFONTTYPE0);
/* 316 */     dic.put(PdfName.BASEFONT, new PdfName(this.fontName + this.style));
/* 317 */     dic.put(PdfName.FONTDESCRIPTOR, fontDescriptor);
/* 318 */     int[] keys = cjkTag.toOrderedKeys();
/* 319 */     String w = convertToHCIDMetrics(keys, this.hMetrics);
/* 320 */     if (w != null)
/* 321 */       dic.put(PdfName.W, new PdfLiteral(w));
/* 322 */     if (this.vertical) {
/* 323 */       w = convertToVCIDMetrics(keys, this.vMetrics, this.hMetrics);
/* 324 */       if (w != null)
/* 325 */         dic.put(PdfName.W2, new PdfLiteral(w));
/*     */     }
/*     */     else {
/* 328 */       dic.put(PdfName.DW, new PdfNumber(1000));
/* 329 */     }PdfDictionary cdic = new PdfDictionary();
/* 330 */     if (this.cidDirect) {
/* 331 */       cdic.put(PdfName.REGISTRY, new PdfString(this.cidUni.getRegistry(), null));
/* 332 */       cdic.put(PdfName.ORDERING, new PdfString(this.cidUni.getOrdering(), null));
/* 333 */       cdic.put(PdfName.SUPPLEMENT, new PdfNumber(this.cidUni.getSupplement()));
/*     */     }
/*     */     else {
/* 336 */       cdic.put(PdfName.REGISTRY, new PdfString(this.cidByte.getRegistry(), null));
/* 337 */       cdic.put(PdfName.ORDERING, new PdfString(this.cidByte.getOrdering(), null));
/* 338 */       cdic.put(PdfName.SUPPLEMENT, new PdfNumber(this.cidByte.getSupplement()));
/*     */     }
/* 340 */     dic.put(PdfName.CIDSYSTEMINFO, cdic);
/* 341 */     return dic;
/*     */   }
/*     */ 
/*     */   private PdfDictionary getFontBaseType(PdfIndirectReference CIDFont) {
/* 345 */     PdfDictionary dic = new PdfDictionary(PdfName.FONT);
/* 346 */     dic.put(PdfName.SUBTYPE, PdfName.TYPE0);
/* 347 */     String name = this.fontName;
/* 348 */     if (this.style.length() > 0)
/* 349 */       name = name + "-" + this.style.substring(1);
/* 350 */     name = name + "-" + this.CMap;
/* 351 */     dic.put(PdfName.BASEFONT, new PdfName(name));
/* 352 */     dic.put(PdfName.ENCODING, new PdfName(this.CMap));
/* 353 */     dic.put(PdfName.DESCENDANTFONTS, new PdfArray(CIDFont));
/* 354 */     return dic;
/*     */   }
/*     */ 
/*     */   void writeFont(PdfWriter writer, PdfIndirectReference ref, Object[] params) throws DocumentException, IOException
/*     */   {
/* 359 */     IntHashtable cjkTag = (IntHashtable)params[0];
/* 360 */     PdfIndirectReference ind_font = null;
/* 361 */     PdfObject pobj = null;
/* 362 */     PdfIndirectObject obj = null;
/* 363 */     pobj = getFontDescriptor();
/* 364 */     if (pobj != null) {
/* 365 */       obj = writer.addToBody(pobj);
/* 366 */       ind_font = obj.getIndirectReference();
/*     */     }
/* 368 */     pobj = getCIDFont(ind_font, cjkTag);
/* 369 */     if (pobj != null) {
/* 370 */       obj = writer.addToBody(pobj);
/* 371 */       ind_font = obj.getIndirectReference();
/*     */     }
/* 373 */     pobj = getFontBaseType(ind_font);
/* 374 */     writer.addToBody(pobj, ref);
/*     */   }
/*     */ 
/*     */   public PdfStream getFullFontStream()
/*     */   {
/* 385 */     return null;
/*     */   }
/*     */ 
/*     */   private float getDescNumber(String name) {
/* 389 */     return Integer.parseInt((String)this.fontDesc.get(name));
/*     */   }
/*     */ 
/*     */   private float getBBox(int idx) {
/* 393 */     String s = (String)this.fontDesc.get("FontBBox");
/* 394 */     StringTokenizer tk = new StringTokenizer(s, " []\r\n\t\f");
/* 395 */     String ret = tk.nextToken();
/* 396 */     for (int k = 0; k < idx; k++)
/* 397 */       ret = tk.nextToken();
/* 398 */     return Integer.parseInt(ret);
/*     */   }
/*     */ 
/*     */   public float getFontDescriptor(int key, float fontSize)
/*     */   {
/* 410 */     switch (key) {
/*     */     case 1:
/*     */     case 9:
/* 413 */       return getDescNumber("Ascent") * fontSize / 1000.0F;
/*     */     case 2:
/* 415 */       return getDescNumber("CapHeight") * fontSize / 1000.0F;
/*     */     case 3:
/*     */     case 10:
/* 418 */       return getDescNumber("Descent") * fontSize / 1000.0F;
/*     */     case 4:
/* 420 */       return getDescNumber("ItalicAngle");
/*     */     case 5:
/* 422 */       return fontSize * getBBox(0) / 1000.0F;
/*     */     case 6:
/* 424 */       return fontSize * getBBox(1) / 1000.0F;
/*     */     case 7:
/* 426 */       return fontSize * getBBox(2) / 1000.0F;
/*     */     case 8:
/* 428 */       return fontSize * getBBox(3) / 1000.0F;
/*     */     case 11:
/* 430 */       return 0.0F;
/*     */     case 12:
/* 432 */       return fontSize * (getBBox(2) - getBBox(0)) / 1000.0F;
/*     */     }
/* 434 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   public String getPostscriptFontName()
/*     */   {
/* 439 */     return this.fontName;
/*     */   }
/*     */ 
/*     */   public String[][] getFullFontName()
/*     */   {
/* 452 */     return new String[][] { { "", "", "", this.fontName } };
/*     */   }
/*     */ 
/*     */   public String[][] getAllNameEntries()
/*     */   {
/* 465 */     return new String[][] { { "4", "", "", "", this.fontName } };
/*     */   }
/*     */ 
/*     */   public String[][] getFamilyFontName()
/*     */   {
/* 478 */     return getFullFontName();
/*     */   }
/*     */ 
/*     */   static IntHashtable createMetric(String s)
/*     */   {
/* 498 */     IntHashtable h = new IntHashtable();
/* 499 */     StringTokenizer tk = new StringTokenizer(s);
/* 500 */     while (tk.hasMoreTokens()) {
/* 501 */       int n1 = Integer.parseInt(tk.nextToken());
/* 502 */       h.put(n1, Integer.parseInt(tk.nextToken()));
/*     */     }
/* 504 */     return h;
/*     */   }
/*     */ 
/*     */   static String convertToHCIDMetrics(int[] keys, IntHashtable h) {
/* 508 */     if (keys.length == 0)
/* 509 */       return null;
/* 510 */     int lastCid = 0;
/* 511 */     int lastValue = 0;
/*     */ 
/* 513 */     for (int start = 0; start < keys.length; start++) {
/* 514 */       lastCid = keys[start];
/* 515 */       lastValue = h.get(lastCid);
/* 516 */       if (lastValue != 0) {
/* 517 */         start++;
/* 518 */         break;
/*     */       }
/*     */     }
/* 521 */     if (lastValue == 0)
/* 522 */       return null;
/* 523 */     StringBuilder buf = new StringBuilder();
/* 524 */     buf.append('[');
/* 525 */     buf.append(lastCid);
/* 526 */     int state = 0;
/* 527 */     for (int k = start; k < keys.length; k++) {
/* 528 */       int cid = keys[k];
/* 529 */       int value = h.get(cid);
/* 530 */       if (value != 0)
/*     */       {
/* 532 */         switch (state) {
/*     */         case 0:
/* 534 */           if ((cid == lastCid + 1) && (value == lastValue)) {
/* 535 */             state = 2;
/*     */           }
/* 537 */           else if (cid == lastCid + 1) {
/* 538 */             state = 1;
/* 539 */             buf.append('[').append(lastValue);
/*     */           }
/*     */           else {
/* 542 */             buf.append('[').append(lastValue).append(']').append(cid);
/*     */           }
/* 544 */           break;
/*     */         case 1:
/* 547 */           if ((cid == lastCid + 1) && (value == lastValue)) {
/* 548 */             state = 2;
/* 549 */             buf.append(']').append(lastCid);
/*     */           }
/* 551 */           else if (cid == lastCid + 1) {
/* 552 */             buf.append(' ').append(lastValue);
/*     */           }
/*     */           else {
/* 555 */             state = 0;
/* 556 */             buf.append(' ').append(lastValue).append(']').append(cid);
/*     */           }
/* 558 */           break;
/*     */         case 2:
/* 561 */           if ((cid != lastCid + 1) || (value != lastValue)) {
/* 562 */             buf.append(' ').append(lastCid).append(' ').append(lastValue).append(' ').append(cid);
/* 563 */             state = 0;
/*     */           }
/*     */           break;
/*     */         }
/*     */ 
/* 568 */         lastValue = value;
/* 569 */         lastCid = cid;
/*     */       }
/*     */     }
/* 571 */     switch (state) {
/*     */     case 0:
/* 573 */       buf.append('[').append(lastValue).append("]]");
/* 574 */       break;
/*     */     case 1:
/* 577 */       buf.append(' ').append(lastValue).append("]]");
/* 578 */       break;
/*     */     case 2:
/* 581 */       buf.append(' ').append(lastCid).append(' ').append(lastValue).append(']');
/*     */     }
/*     */ 
/* 585 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   static String convertToVCIDMetrics(int[] keys, IntHashtable v, IntHashtable h) {
/* 589 */     if (keys.length == 0)
/* 590 */       return null;
/* 591 */     int lastCid = 0;
/* 592 */     int lastValue = 0;
/* 593 */     int lastHValue = 0;
/*     */ 
/* 595 */     for (int start = 0; start < keys.length; start++) {
/* 596 */       lastCid = keys[start];
/* 597 */       lastValue = v.get(lastCid);
/* 598 */       if (lastValue != 0) {
/* 599 */         start++;
/* 600 */         break;
/*     */       }
/*     */ 
/* 603 */       lastHValue = h.get(lastCid);
/*     */     }
/* 605 */     if (lastValue == 0)
/* 606 */       return null;
/* 607 */     if (lastHValue == 0)
/* 608 */       lastHValue = 1000;
/* 609 */     StringBuilder buf = new StringBuilder();
/* 610 */     buf.append('[');
/* 611 */     buf.append(lastCid);
/* 612 */     int state = 0;
/* 613 */     for (int k = start; k < keys.length; k++) {
/* 614 */       int cid = keys[k];
/* 615 */       int value = v.get(cid);
/* 616 */       if (value != 0)
/*     */       {
/* 618 */         int hValue = h.get(lastCid);
/* 619 */         if (hValue == 0)
/* 620 */           hValue = 1000;
/* 621 */         switch (state) {
/*     */         case 0:
/* 623 */           if ((cid == lastCid + 1) && (value == lastValue) && (hValue == lastHValue)) {
/* 624 */             state = 2;
/*     */           }
/*     */           else {
/* 627 */             buf.append(' ').append(lastCid).append(' ').append(-lastValue).append(' ').append(lastHValue / 2).append(' ').append(880).append(' ').append(cid);
/*     */           }
/* 629 */           break;
/*     */         case 2:
/* 632 */           if ((cid != lastCid + 1) || (value != lastValue) || (hValue != lastHValue)) {
/* 633 */             buf.append(' ').append(lastCid).append(' ').append(-lastValue).append(' ').append(lastHValue / 2).append(' ').append(880).append(' ').append(cid);
/* 634 */             state = 0;
/*     */           }
/*     */           break;
/*     */         }
/*     */ 
/* 639 */         lastValue = value;
/* 640 */         lastCid = cid;
/* 641 */         lastHValue = hValue;
/*     */       }
/*     */     }
/* 643 */     buf.append(' ').append(lastCid).append(' ').append(-lastValue).append(' ').append(lastHValue / 2).append(' ').append(880).append(" ]");
/* 644 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   private static HashMap<String, Object> readFontProperties(String name) throws IOException {
/* 648 */     name = name + ".properties";
/* 649 */     InputStream is = StreamUtil.getResourceStream("com/itextpdf/text/pdf/fonts/cmaps/" + name);
/* 650 */     Properties p = new Properties();
/* 651 */     p.load(is);
/* 652 */     is.close();
/* 653 */     IntHashtable W = createMetric(p.getProperty("W"));
/* 654 */     p.remove("W");
/* 655 */     IntHashtable W2 = createMetric(p.getProperty("W2"));
/* 656 */     p.remove("W2");
/* 657 */     HashMap map = new HashMap();
/* 658 */     for (Enumeration e = p.keys(); e.hasMoreElements(); ) {
/* 659 */       Object obj = e.nextElement();
/* 660 */       map.put((String)obj, p.getProperty((String)obj));
/*     */     }
/* 662 */     map.put("W", W);
/* 663 */     map.put("W2", W2);
/* 664 */     return map;
/*     */   }
/*     */ 
/*     */   public int getUnicodeEquivalent(int c)
/*     */   {
/* 669 */     if (this.cidDirect) {
/* 670 */       if (c == 32767)
/* 671 */         return 10;
/* 672 */       return this.cidUni.lookup(c);
/*     */     }
/* 674 */     return c;
/*     */   }
/*     */ 
/*     */   public int getCidCode(int c)
/*     */   {
/* 679 */     if (this.cidDirect)
/* 680 */       return c;
/* 681 */     return this.uniCid.lookup(c);
/*     */   }
/*     */ 
/*     */   public boolean hasKernPairs()
/*     */   {
/* 689 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean charExists(int c)
/*     */   {
/* 700 */     if (this.cidDirect)
/* 701 */       return true;
/* 702 */     return this.cidByte.lookup(this.uniCid.lookup(c)).length > 0;
/*     */   }
/*     */ 
/*     */   public boolean setCharAdvance(int c, int advance)
/*     */   {
/* 714 */     return false;
/*     */   }
/*     */ 
/*     */   public void setPostscriptFontName(String name)
/*     */   {
/* 724 */     this.fontName = name;
/*     */   }
/*     */ 
/*     */   public boolean setKerning(int char1, int char2, int kern)
/*     */   {
/* 729 */     return false;
/*     */   }
/*     */ 
/*     */   public int[] getCharBBox(int c)
/*     */   {
/* 734 */     return null;
/*     */   }
/*     */ 
/*     */   protected int[] getRawCharBBox(int c, String name)
/*     */   {
/* 739 */     return null;
/*     */   }
/*     */ 
/*     */   public byte[] convertToBytes(String text)
/*     */   {
/* 750 */     if (this.cidDirect)
/* 751 */       return super.convertToBytes(text);
/*     */     try {
/* 753 */       if (text.length() == 1)
/* 754 */         return convertToBytes(text.charAt(0));
/* 755 */       ByteArrayOutputStream bout = new ByteArrayOutputStream();
/* 756 */       for (int k = 0; k < text.length(); k++)
/*     */       {
/*     */         int val;
/* 758 */         if (Utilities.isSurrogatePair(text, k)) {
/* 759 */           int val = Utilities.convertToUtf32(text, k);
/* 760 */           k++;
/*     */         }
/*     */         else {
/* 763 */           val = text.charAt(k);
/*     */         }
/* 765 */         bout.write(convertToBytes(val));
/*     */       }
/* 767 */       return bout.toByteArray();
/*     */     }
/*     */     catch (Exception ex) {
/* 770 */       throw new ExceptionConverter(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   byte[] convertToBytes(int char1)
/*     */   {
/* 782 */     if (this.cidDirect)
/* 783 */       return super.convertToBytes(char1);
/* 784 */     return this.cidByte.lookup(this.uniCid.lookup(char1));
/*     */   }
/*     */ 
/*     */   public boolean isIdentity() {
/* 788 */     return this.cidDirect;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.CJKFont
 * JD-Core Version:    0.6.2
 */