/*      */ package com.itextpdf.text.pdf;
/*      */ 
/*      */ import com.itextpdf.text.DocumentException;
/*      */ import com.itextpdf.text.error_messages.MessageLocalization;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.StringTokenizer;
/*      */ 
/*      */ public abstract class BaseFont
/*      */ {
/*      */   public static final String COURIER = "Courier";
/*      */   public static final String COURIER_BOLD = "Courier-Bold";
/*      */   public static final String COURIER_OBLIQUE = "Courier-Oblique";
/*      */   public static final String COURIER_BOLDOBLIQUE = "Courier-BoldOblique";
/*      */   public static final String HELVETICA = "Helvetica";
/*      */   public static final String HELVETICA_BOLD = "Helvetica-Bold";
/*      */   public static final String HELVETICA_OBLIQUE = "Helvetica-Oblique";
/*      */   public static final String HELVETICA_BOLDOBLIQUE = "Helvetica-BoldOblique";
/*      */   public static final String SYMBOL = "Symbol";
/*      */   public static final String TIMES_ROMAN = "Times-Roman";
/*      */   public static final String TIMES_BOLD = "Times-Bold";
/*      */   public static final String TIMES_ITALIC = "Times-Italic";
/*      */   public static final String TIMES_BOLDITALIC = "Times-BoldItalic";
/*      */   public static final String ZAPFDINGBATS = "ZapfDingbats";
/*      */   public static final int ASCENT = 1;
/*      */   public static final int CAPHEIGHT = 2;
/*      */   public static final int DESCENT = 3;
/*      */   public static final int ITALICANGLE = 4;
/*      */   public static final int BBOXLLX = 5;
/*      */   public static final int BBOXLLY = 6;
/*      */   public static final int BBOXURX = 7;
/*      */   public static final int BBOXURY = 8;
/*      */   public static final int AWT_ASCENT = 9;
/*      */   public static final int AWT_DESCENT = 10;
/*      */   public static final int AWT_LEADING = 11;
/*      */   public static final int AWT_MAXADVANCE = 12;
/*      */   public static final int UNDERLINE_POSITION = 13;
/*      */   public static final int UNDERLINE_THICKNESS = 14;
/*      */   public static final int STRIKETHROUGH_POSITION = 15;
/*      */   public static final int STRIKETHROUGH_THICKNESS = 16;
/*      */   public static final int SUBSCRIPT_SIZE = 17;
/*      */   public static final int SUBSCRIPT_OFFSET = 18;
/*      */   public static final int SUPERSCRIPT_SIZE = 19;
/*      */   public static final int SUPERSCRIPT_OFFSET = 20;
/*      */   public static final int WEIGHT_CLASS = 21;
/*      */   public static final int WIDTH_CLASS = 22;
/*      */   public static final int FONT_WEIGHT = 23;
/*      */   public static final int FONT_TYPE_T1 = 0;
/*      */   public static final int FONT_TYPE_TT = 1;
/*      */   public static final int FONT_TYPE_CJK = 2;
/*      */   public static final int FONT_TYPE_TTUNI = 3;
/*      */   public static final int FONT_TYPE_DOCUMENT = 4;
/*      */   public static final int FONT_TYPE_T3 = 5;
/*      */   public static final String IDENTITY_H = "Identity-H";
/*      */   public static final String IDENTITY_V = "Identity-V";
/*      */   public static final String CP1250 = "Cp1250";
/*      */   public static final String CP1252 = "Cp1252";
/*      */   public static final String CP1257 = "Cp1257";
/*      */   public static final String WINANSI = "Cp1252";
/*      */   public static final String MACROMAN = "MacRoman";
/*  231 */   public static final int[] CHAR_RANGE_LATIN = { 0, 383, 8192, 8303, 8352, 8399, 64256, 64262 };
/*  232 */   public static final int[] CHAR_RANGE_ARABIC = { 0, 127, 1536, 1663, 8352, 8399, 64336, 64511, 65136, 65279 };
/*  233 */   public static final int[] CHAR_RANGE_HEBREW = { 0, 127, 1424, 1535, 8352, 8399, 64285, 64335 };
/*  234 */   public static final int[] CHAR_RANGE_CYRILLIC = { 0, 127, 1024, 1327, 8192, 8303, 8352, 8399 };
/*      */   public static final boolean EMBEDDED = true;
/*      */   public static final boolean NOT_EMBEDDED = false;
/*      */   public static final boolean CACHED = true;
/*      */   public static final boolean NOT_CACHED = false;
/*      */   public static final String RESOURCE_PATH = "com/itextpdf/text/pdf/fonts/";
/*      */   public static final char CID_NEWLINE = '翿';
/*      */   public static final char PARAGRAPH_SEPARATOR = ' ';
/*      */   protected ArrayList<int[]> subsetRanges;
/*      */   int fontType;
/*      */   public static final String notdef = ".notdef";
/*  266 */   protected int[] widths = new int[256];
/*      */ 
/*  269 */   protected String[] differences = new String[256];
/*      */ 
/*  271 */   protected char[] unicodeDifferences = new char[256];
/*      */ 
/*  273 */   protected int[][] charBBoxes = new int[256][];
/*      */   protected String encoding;
/*      */   protected boolean embedded;
/*  284 */   protected int compressionLevel = -1;
/*      */ 
/*  291 */   protected boolean fontSpecific = true;
/*      */ 
/*  294 */   protected static HashMap<String, BaseFont> fontCache = new HashMap();
/*      */ 
/*  297 */   protected static final HashMap<String, PdfName> BuiltinFonts14 = new HashMap();
/*      */ 
/*  302 */   protected boolean forceWidthsOutput = false;
/*      */ 
/*  307 */   protected boolean directTextToByte = false;
/*      */ 
/*  312 */   protected boolean subset = true;
/*      */ 
/*  314 */   protected boolean fastWinansi = false;
/*      */   protected IntHashtable specialMap;
/*  322 */   protected boolean vertical = false;
/*      */ 
/*      */   public static BaseFont createFont()
/*      */     throws DocumentException, IOException
/*      */   {
/*  405 */     return createFont("Helvetica", "Cp1252", false);
/*      */   }
/*      */ 
/*      */   public static BaseFont createFont(String name, String encoding, boolean embedded)
/*      */     throws DocumentException, IOException
/*      */   {
/*  457 */     return createFont(name, encoding, embedded, true, null, null, false);
/*      */   }
/*      */ 
/*      */   public static BaseFont createFont(String name, String encoding, boolean embedded, boolean forceRead)
/*      */     throws DocumentException, IOException
/*      */   {
/*  511 */     return createFont(name, encoding, embedded, true, null, null, forceRead);
/*      */   }
/*      */ 
/*      */   public static BaseFont createFont(String name, String encoding, boolean embedded, boolean cached, byte[] ttfAfm, byte[] pfb)
/*      */     throws DocumentException, IOException
/*      */   {
/*  565 */     return createFont(name, encoding, embedded, cached, ttfAfm, pfb, false);
/*      */   }
/*      */ 
/*      */   public static BaseFont createFont(String name, String encoding, boolean embedded, boolean cached, byte[] ttfAfm, byte[] pfb, boolean noThrow)
/*      */     throws DocumentException, IOException
/*      */   {
/*  622 */     return createFont(name, encoding, embedded, cached, ttfAfm, pfb, noThrow, false);
/*      */   }
/*      */ 
/*      */   public static BaseFont createFont(String name, String encoding, boolean embedded, boolean cached, byte[] ttfAfm, byte[] pfb, boolean noThrow, boolean forceRead)
/*      */     throws DocumentException, IOException
/*      */   {
/*  680 */     String nameBase = getBaseName(name);
/*  681 */     encoding = normalizeEncoding(encoding);
/*  682 */     boolean isBuiltinFonts14 = BuiltinFonts14.containsKey(name);
/*  683 */     boolean isCJKFont = isBuiltinFonts14 ? false : CJKFont.isCJKFont(nameBase, encoding);
/*  684 */     if ((isBuiltinFonts14) || (isCJKFont))
/*  685 */       embedded = false;
/*  686 */     else if ((encoding.equals("Identity-H")) || (encoding.equals("Identity-V")))
/*  687 */       embedded = true;
/*  688 */     BaseFont fontFound = null;
/*  689 */     BaseFont fontBuilt = null;
/*  690 */     String key = name + "\n" + encoding + "\n" + embedded;
/*  691 */     if (cached) {
/*  692 */       synchronized (fontCache) {
/*  693 */         fontFound = (BaseFont)fontCache.get(key);
/*      */       }
/*  695 */       if (fontFound != null)
/*  696 */         return fontFound;
/*      */     }
/*  698 */     if ((isBuiltinFonts14) || (name.toLowerCase().endsWith(".afm")) || (name.toLowerCase().endsWith(".pfm"))) {
/*  699 */       fontBuilt = new Type1Font(name, encoding, embedded, ttfAfm, pfb, forceRead);
/*  700 */       fontBuilt.fastWinansi = encoding.equals("Cp1252");
/*      */     }
/*  702 */     else if ((nameBase.toLowerCase().endsWith(".ttf")) || (nameBase.toLowerCase().endsWith(".otf")) || (nameBase.toLowerCase().indexOf(".ttc,") > 0)) {
/*  703 */       if ((encoding.equals("Identity-H")) || (encoding.equals("Identity-V"))) {
/*  704 */         fontBuilt = new TrueTypeFontUnicode(name, encoding, embedded, ttfAfm, forceRead);
/*      */       } else {
/*  706 */         fontBuilt = new TrueTypeFont(name, encoding, embedded, ttfAfm, false, forceRead);
/*  707 */         fontBuilt.fastWinansi = encoding.equals("Cp1252");
/*      */       }
/*      */     }
/*  710 */     else if (isCJKFont) {
/*  711 */       fontBuilt = new CJKFont(name, encoding, embedded); } else {
/*  712 */       if (noThrow) {
/*  713 */         return null;
/*      */       }
/*  715 */       throw new DocumentException(MessageLocalization.getComposedMessage("font.1.with.2.is.not.recognized", new Object[] { name, encoding }));
/*  716 */     }if (cached) {
/*  717 */       synchronized (fontCache) {
/*  718 */         fontFound = (BaseFont)fontCache.get(key);
/*  719 */         if (fontFound != null)
/*  720 */           return fontFound;
/*  721 */         fontCache.put(key, fontBuilt);
/*      */       }
/*      */     }
/*  724 */     return fontBuilt;
/*      */   }
/*      */ 
/*      */   public static BaseFont createFont(PRIndirectReference fontRef)
/*      */   {
/*  734 */     return new DocumentFont(fontRef);
/*      */   }
/*      */ 
/*      */   public boolean isVertical()
/*      */   {
/*  742 */     return this.vertical;
/*      */   }
/*      */ 
/*      */   protected static String getBaseName(String name)
/*      */   {
/*  751 */     if (name.endsWith(",Bold"))
/*  752 */       return name.substring(0, name.length() - 5);
/*  753 */     if (name.endsWith(",Italic"))
/*  754 */       return name.substring(0, name.length() - 7);
/*  755 */     if (name.endsWith(",BoldItalic")) {
/*  756 */       return name.substring(0, name.length() - 11);
/*      */     }
/*  758 */     return name;
/*      */   }
/*      */ 
/*      */   protected static String normalizeEncoding(String enc)
/*      */   {
/*  768 */     if ((enc.equals("winansi")) || (enc.equals("")))
/*  769 */       return "Cp1252";
/*  770 */     if (enc.equals("macroman")) {
/*  771 */       return "MacRoman";
/*      */     }
/*  773 */     return enc;
/*      */   }
/*      */ 
/*      */   protected void createEncoding()
/*      */   {
/*  780 */     if (this.encoding.startsWith("#")) {
/*  781 */       this.specialMap = new IntHashtable();
/*  782 */       StringTokenizer tok = new StringTokenizer(this.encoding.substring(1), " ,\t\n\r\f");
/*  783 */       if (tok.nextToken().equals("full")) {
/*  784 */         while (tok.hasMoreTokens()) {
/*  785 */           String order = tok.nextToken();
/*  786 */           String name = tok.nextToken();
/*  787 */           char uni = (char)Integer.parseInt(tok.nextToken(), 16);
/*      */           int orderK;
/*      */           int orderK;
/*  789 */           if (order.startsWith("'"))
/*  790 */             orderK = order.charAt(1);
/*      */           else
/*  792 */             orderK = Integer.parseInt(order);
/*  793 */           orderK %= 256;
/*  794 */           this.specialMap.put(uni, orderK);
/*  795 */           this.differences[orderK] = name;
/*  796 */           this.unicodeDifferences[orderK] = uni;
/*  797 */           this.widths[orderK] = getRawWidth(uni, name);
/*  798 */           this.charBBoxes[orderK] = getRawCharBBox(uni, name);
/*      */         }
/*      */       }
/*      */ 
/*  802 */       int k = 0;
/*  803 */       if (tok.hasMoreTokens())
/*  804 */         k = Integer.parseInt(tok.nextToken());
/*  805 */       while ((tok.hasMoreTokens()) && (k < 256)) {
/*  806 */         String hex = tok.nextToken();
/*  807 */         int uni = Integer.parseInt(hex, 16) % 65536;
/*  808 */         String name = GlyphList.unicodeToName(uni);
/*  809 */         if (name != null) {
/*  810 */           this.specialMap.put(uni, k);
/*  811 */           this.differences[k] = name;
/*  812 */           this.unicodeDifferences[k] = ((char)uni);
/*  813 */           this.widths[k] = getRawWidth(uni, name);
/*  814 */           this.charBBoxes[k] = getRawCharBBox(uni, name);
/*  815 */           k++;
/*      */         }
/*      */       }
/*      */ 
/*  819 */       for (int k = 0; k < 256; k++) {
/*  820 */         if (this.differences[k] == null) {
/*  821 */           this.differences[k] = ".notdef";
/*      */         }
/*      */       }
/*      */     }
/*  825 */     else if (this.fontSpecific) {
/*  826 */       for (int k = 0; k < 256; k++) {
/*  827 */         this.widths[k] = getRawWidth(k, null);
/*  828 */         this.charBBoxes[k] = getRawCharBBox(k, null);
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  835 */       byte[] b = new byte[1];
/*  836 */       for (int k = 0; k < 256; k++) {
/*  837 */         b[0] = ((byte)k);
/*  838 */         String s = PdfEncodings.convertToString(b, this.encoding);
/*      */         char c;
/*      */         char c;
/*  839 */         if (s.length() > 0) {
/*  840 */           c = s.charAt(0);
/*      */         }
/*      */         else {
/*  843 */           c = '?';
/*      */         }
/*  845 */         String name = GlyphList.unicodeToName(c);
/*  846 */         if (name == null)
/*  847 */           name = ".notdef";
/*  848 */         this.differences[k] = name;
/*  849 */         this.unicodeDifferences[k] = c;
/*  850 */         this.widths[k] = getRawWidth(c, name);
/*  851 */         this.charBBoxes[k] = getRawCharBBox(c, name);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   abstract int getRawWidth(int paramInt, String paramString);
/*      */ 
/*      */   public abstract int getKerning(int paramInt1, int paramInt2);
/*      */ 
/*      */   public abstract boolean setKerning(int paramInt1, int paramInt2, int paramInt3);
/*      */ 
/*      */   public int getWidth(int char1)
/*      */   {
/*  888 */     if (this.fastWinansi) {
/*  889 */       if ((char1 < 128) || ((char1 >= 160) && (char1 <= 255))) {
/*  890 */         return this.widths[char1];
/*      */       }
/*  892 */       return this.widths[PdfEncodings.winansi.get(char1)];
/*      */     }
/*      */ 
/*  895 */     int total = 0;
/*  896 */     byte[] mbytes = convertToBytes((char)char1);
/*  897 */     for (int k = 0; k < mbytes.length; k++)
/*  898 */       total += this.widths[(0xFF & mbytes[k])];
/*  899 */     return total;
/*      */   }
/*      */ 
/*      */   public int getWidth(String text)
/*      */   {
/*  909 */     int total = 0;
/*  910 */     if (this.fastWinansi) {
/*  911 */       int len = text.length();
/*  912 */       for (int k = 0; k < len; k++) {
/*  913 */         char char1 = text.charAt(k);
/*  914 */         if ((char1 < '') || ((char1 >= ' ') && (char1 <= 'ÿ')))
/*  915 */           total += this.widths[char1];
/*      */         else
/*  917 */           total += this.widths[PdfEncodings.winansi.get(char1)];
/*      */       }
/*  919 */       return total;
/*      */     }
/*      */ 
/*  922 */     byte[] mbytes = convertToBytes(text);
/*  923 */     for (int k = 0; k < mbytes.length; k++) {
/*  924 */       total += this.widths[(0xFF & mbytes[k])];
/*      */     }
/*  926 */     return total;
/*      */   }
/*      */ 
/*      */   public int getDescent(String text)
/*      */   {
/*  936 */     int min = 0;
/*  937 */     char[] chars = text.toCharArray();
/*  938 */     for (int k = 0; k < chars.length; k++) {
/*  939 */       int[] bbox = getCharBBox(chars[k]);
/*  940 */       if ((bbox != null) && (bbox[1] < min))
/*  941 */         min = bbox[1];
/*      */     }
/*  943 */     return min;
/*      */   }
/*      */ 
/*      */   public int getAscent(String text)
/*      */   {
/*  953 */     int max = 0;
/*  954 */     char[] chars = text.toCharArray();
/*  955 */     for (int k = 0; k < chars.length; k++) {
/*  956 */       int[] bbox = getCharBBox(chars[k]);
/*  957 */       if ((bbox != null) && (bbox[3] > max))
/*  958 */         max = bbox[3];
/*      */     }
/*  960 */     return max;
/*      */   }
/*      */ 
/*      */   public float getDescentPoint(String text, float fontSize)
/*      */   {
/*  972 */     return getDescent(text) * 0.001F * fontSize;
/*      */   }
/*      */ 
/*      */   public float getAscentPoint(String text, float fontSize)
/*      */   {
/*  984 */     return getAscent(text) * 0.001F * fontSize;
/*      */   }
/*      */ 
/*      */   public float getWidthPointKerned(String text, float fontSize)
/*      */   {
/*  996 */     float size = getWidth(text) * 0.001F * fontSize;
/*  997 */     if (!hasKernPairs())
/*  998 */       return size;
/*  999 */     int len = text.length() - 1;
/* 1000 */     int kern = 0;
/* 1001 */     char[] c = text.toCharArray();
/* 1002 */     for (int k = 0; k < len; k++) {
/* 1003 */       kern += getKerning(c[k], c[(k + 1)]);
/*      */     }
/* 1005 */     return size + kern * 0.001F * fontSize;
/*      */   }
/*      */ 
/*      */   public float getWidthPoint(String text, float fontSize)
/*      */   {
/* 1015 */     return getWidth(text) * 0.001F * fontSize;
/*      */   }
/*      */ 
/*      */   public float getWidthPoint(int char1, float fontSize)
/*      */   {
/* 1025 */     return getWidth(char1) * 0.001F * fontSize;
/*      */   }
/*      */ 
/*      */   public byte[] convertToBytes(String text)
/*      */   {
/* 1035 */     if (this.directTextToByte)
/* 1036 */       return PdfEncodings.convertToBytes(text, null);
/* 1037 */     if (this.specialMap != null) {
/* 1038 */       byte[] b = new byte[text.length()];
/* 1039 */       int ptr = 0;
/* 1040 */       int length = text.length();
/* 1041 */       for (int k = 0; k < length; k++) {
/* 1042 */         char c = text.charAt(k);
/* 1043 */         if (this.specialMap.containsKey(c))
/* 1044 */           b[(ptr++)] = ((byte)this.specialMap.get(c));
/*      */       }
/* 1046 */       if (ptr < length) {
/* 1047 */         byte[] b2 = new byte[ptr];
/* 1048 */         System.arraycopy(b, 0, b2, 0, ptr);
/* 1049 */         return b2;
/*      */       }
/*      */ 
/* 1052 */       return b;
/*      */     }
/* 1054 */     return PdfEncodings.convertToBytes(text, this.encoding);
/*      */   }
/*      */ 
/*      */   byte[] convertToBytes(int char1)
/*      */   {
/* 1064 */     if (this.directTextToByte)
/* 1065 */       return PdfEncodings.convertToBytes((char)char1, null);
/* 1066 */     if (this.specialMap != null) {
/* 1067 */       if (this.specialMap.containsKey(char1)) {
/* 1068 */         return new byte[] { (byte)this.specialMap.get(char1) };
/*      */       }
/* 1070 */       return new byte[0];
/*      */     }
/* 1072 */     return PdfEncodings.convertToBytes((char)char1, this.encoding);
/*      */   }
/*      */ 
/*      */   abstract void writeFont(PdfWriter paramPdfWriter, PdfIndirectReference paramPdfIndirectReference, Object[] paramArrayOfObject)
/*      */     throws DocumentException, IOException;
/*      */ 
/*      */   abstract PdfStream getFullFontStream()
/*      */     throws IOException, DocumentException;
/*      */ 
/*      */   public String getEncoding()
/*      */   {
/* 1097 */     return this.encoding;
/*      */   }
/*      */ 
/*      */   public abstract float getFontDescriptor(int paramInt, float paramFloat);
/*      */ 
/*      */   public void setFontDescriptor(int key, float value)
/*      */   {
/*      */   }
/*      */ 
/*      */   public int getFontType()
/*      */   {
/* 1126 */     return this.fontType;
/*      */   }
/*      */ 
/*      */   public boolean isEmbedded()
/*      */   {
/* 1133 */     return this.embedded;
/*      */   }
/*      */ 
/*      */   public boolean isFontSpecific()
/*      */   {
/* 1140 */     return this.fontSpecific;
/*      */   }
/*      */ 
/*      */   public static String createSubsetPrefix()
/*      */   {
/* 1147 */     StringBuilder s = new StringBuilder("");
/* 1148 */     for (int k = 0; k < 6; k++)
/* 1149 */       s.append((char)(int)(Math.random() * 26.0D + 65.0D));
/* 1150 */     return s + "+";
/*      */   }
/*      */ 
/*      */   char getUnicodeDifferences(int index)
/*      */   {
/* 1158 */     return this.unicodeDifferences[index];
/*      */   }
/*      */ 
/*      */   public abstract String getPostscriptFontName();
/*      */ 
/*      */   public abstract void setPostscriptFontName(String paramString);
/*      */ 
/*      */   public abstract String[][] getFullFontName();
/*      */ 
/*      */   public abstract String[][] getAllNameEntries();
/*      */ 
/*      */   public static String[][] getFullFontName(String name, String encoding, byte[] ttfAfm)
/*      */     throws DocumentException, IOException
/*      */   {
/* 1208 */     String nameBase = getBaseName(name);
/* 1209 */     BaseFont fontBuilt = null;
/* 1210 */     if ((nameBase.toLowerCase().endsWith(".ttf")) || (nameBase.toLowerCase().endsWith(".otf")) || (nameBase.toLowerCase().indexOf(".ttc,") > 0))
/* 1211 */       fontBuilt = new TrueTypeFont(name, "Cp1252", false, ttfAfm, true, false);
/*      */     else
/* 1213 */       fontBuilt = createFont(name, encoding, false, false, ttfAfm, null);
/* 1214 */     return fontBuilt.getFullFontName();
/*      */   }
/*      */ 
/*      */   public static Object[] getAllFontNames(String name, String encoding, byte[] ttfAfm)
/*      */     throws DocumentException, IOException
/*      */   {
/* 1226 */     String nameBase = getBaseName(name);
/* 1227 */     BaseFont fontBuilt = null;
/* 1228 */     if ((nameBase.toLowerCase().endsWith(".ttf")) || (nameBase.toLowerCase().endsWith(".otf")) || (nameBase.toLowerCase().indexOf(".ttc,") > 0))
/* 1229 */       fontBuilt = new TrueTypeFont(name, "Cp1252", false, ttfAfm, true, false);
/*      */     else
/* 1231 */       fontBuilt = createFont(name, encoding, false, false, ttfAfm, null);
/* 1232 */     return new Object[] { fontBuilt.getPostscriptFontName(), fontBuilt.getFamilyFontName(), fontBuilt.getFullFontName() };
/*      */   }
/*      */ 
/*      */   public static String[][] getAllNameEntries(String name, String encoding, byte[] ttfAfm)
/*      */     throws DocumentException, IOException
/*      */   {
/* 1245 */     String nameBase = getBaseName(name);
/* 1246 */     BaseFont fontBuilt = null;
/* 1247 */     if ((nameBase.toLowerCase().endsWith(".ttf")) || (nameBase.toLowerCase().endsWith(".otf")) || (nameBase.toLowerCase().indexOf(".ttc,") > 0))
/* 1248 */       fontBuilt = new TrueTypeFont(name, "Cp1252", false, ttfAfm, true, false);
/*      */     else
/* 1250 */       fontBuilt = createFont(name, encoding, false, false, ttfAfm, null);
/* 1251 */     return fontBuilt.getAllNameEntries();
/*      */   }
/*      */ 
/*      */   public abstract String[][] getFamilyFontName();
/*      */ 
/*      */   public String[] getCodePagesSupported()
/*      */   {
/* 1269 */     return new String[0];
/*      */   }
/*      */ 
/*      */   public static String[] enumerateTTCNames(String ttcFile)
/*      */     throws DocumentException, IOException
/*      */   {
/* 1280 */     return new EnumerateTTC(ttcFile).getNames();
/*      */   }
/*      */ 
/*      */   public static String[] enumerateTTCNames(byte[] ttcArray)
/*      */     throws DocumentException, IOException
/*      */   {
/* 1291 */     return new EnumerateTTC(ttcArray).getNames();
/*      */   }
/*      */ 
/*      */   public int[] getWidths()
/*      */   {
/* 1298 */     return this.widths;
/*      */   }
/*      */ 
/*      */   public String[] getDifferences()
/*      */   {
/* 1305 */     return this.differences;
/*      */   }
/*      */ 
/*      */   public char[] getUnicodeDifferences()
/*      */   {
/* 1312 */     return this.unicodeDifferences;
/*      */   }
/*      */ 
/*      */   public boolean isForceWidthsOutput()
/*      */   {
/* 1319 */     return this.forceWidthsOutput;
/*      */   }
/*      */ 
/*      */   public void setForceWidthsOutput(boolean forceWidthsOutput)
/*      */   {
/* 1328 */     this.forceWidthsOutput = forceWidthsOutput;
/*      */   }
/*      */ 
/*      */   public boolean isDirectTextToByte()
/*      */   {
/* 1336 */     return this.directTextToByte;
/*      */   }
/*      */ 
/*      */   public void setDirectTextToByte(boolean directTextToByte)
/*      */   {
/* 1345 */     this.directTextToByte = directTextToByte;
/*      */   }
/*      */ 
/*      */   public boolean isSubset()
/*      */   {
/* 1353 */     return this.subset;
/*      */   }
/*      */ 
/*      */   public void setSubset(boolean subset)
/*      */   {
/* 1364 */     this.subset = subset;
/*      */   }
/*      */ 
/*      */   public int getUnicodeEquivalent(int c)
/*      */   {
/* 1374 */     return c;
/*      */   }
/*      */ 
/*      */   public int getCidCode(int c)
/*      */   {
/* 1383 */     return c;
/*      */   }
/*      */ 
/*      */   public abstract boolean hasKernPairs();
/*      */ 
/*      */   public boolean charExists(int c)
/*      */   {
/* 1398 */     byte[] b = convertToBytes(c);
/* 1399 */     return b.length > 0;
/*      */   }
/*      */ 
/*      */   public boolean setCharAdvance(int c, int advance)
/*      */   {
/* 1410 */     byte[] b = convertToBytes(c);
/* 1411 */     if (b.length == 0)
/* 1412 */       return false;
/* 1413 */     this.widths[(0xFF & b[0])] = advance;
/* 1414 */     return true;
/*      */   }
/*      */ 
/*      */   private static void addFont(PRIndirectReference fontRef, IntHashtable hits, ArrayList<Object[]> fonts) {
/* 1418 */     PdfObject obj = PdfReader.getPdfObject(fontRef);
/* 1419 */     if ((obj == null) || (!obj.isDictionary()))
/* 1420 */       return;
/* 1421 */     PdfDictionary font = (PdfDictionary)obj;
/* 1422 */     PdfName subtype = font.getAsName(PdfName.SUBTYPE);
/* 1423 */     if ((!PdfName.TYPE1.equals(subtype)) && (!PdfName.TRUETYPE.equals(subtype)) && (!PdfName.TYPE0.equals(subtype)))
/* 1424 */       return;
/* 1425 */     PdfName name = font.getAsName(PdfName.BASEFONT);
/* 1426 */     fonts.add(new Object[] { PdfName.decodeName(name.toString()), fontRef });
/* 1427 */     hits.put(fontRef.getNumber(), 1);
/*      */   }
/*      */ 
/*      */   private static void recourseFonts(PdfDictionary page, IntHashtable hits, ArrayList<Object[]> fonts, int level) {
/* 1431 */     level++;
/* 1432 */     if (level > 50)
/* 1433 */       return;
/* 1434 */     if (page == null)
/* 1435 */       return;
/* 1436 */     PdfDictionary resources = page.getAsDict(PdfName.RESOURCES);
/* 1437 */     if (resources == null)
/* 1438 */       return;
/* 1439 */     PdfDictionary font = resources.getAsDict(PdfName.FONT);
/* 1440 */     if (font != null)
/* 1441 */       for (PdfName key : font.getKeys()) {
/* 1442 */         PdfObject ft = font.get(key);
/* 1443 */         if ((ft != null) && (ft.isIndirect()))
/*      */         {
/* 1445 */           int hit = ((PRIndirectReference)ft).getNumber();
/* 1446 */           if (!hits.containsKey(hit))
/*      */           {
/* 1448 */             addFont((PRIndirectReference)ft, hits, fonts);
/*      */           }
/*      */         }
/*      */       }
/* 1451 */     PdfDictionary xobj = resources.getAsDict(PdfName.XOBJECT);
/* 1452 */     if (xobj != null)
/* 1453 */       for (PdfName key : xobj.getKeys()) {
/* 1454 */         PdfObject po = xobj.getDirectObject(key);
/* 1455 */         if ((po instanceof PdfDictionary))
/* 1456 */           recourseFonts((PdfDictionary)po, hits, fonts, level);
/*      */       }
/*      */   }
/*      */ 
/*      */   public static ArrayList<Object[]> getDocumentFonts(PdfReader reader)
/*      */   {
/* 1469 */     IntHashtable hits = new IntHashtable();
/* 1470 */     ArrayList fonts = new ArrayList();
/* 1471 */     int npages = reader.getNumberOfPages();
/* 1472 */     for (int k = 1; k <= npages; k++)
/* 1473 */       recourseFonts(reader.getPageN(k), hits, fonts, 1);
/* 1474 */     return fonts;
/*      */   }
/*      */ 
/*      */   public static ArrayList<Object[]> getDocumentFonts(PdfReader reader, int page)
/*      */   {
/* 1486 */     IntHashtable hits = new IntHashtable();
/* 1487 */     ArrayList fonts = new ArrayList();
/* 1488 */     recourseFonts(reader.getPageN(page), hits, fonts, 1);
/* 1489 */     return fonts;
/*      */   }
/*      */ 
/*      */   public int[] getCharBBox(int c)
/*      */   {
/* 1502 */     byte[] b = convertToBytes(c);
/* 1503 */     if (b.length == 0) {
/* 1504 */       return null;
/*      */     }
/* 1506 */     return this.charBBoxes[(b[0] & 0xFF)];
/*      */   }
/*      */ 
/*      */   protected abstract int[] getRawCharBBox(int paramInt, String paramString);
/*      */ 
/*      */   public void correctArabicAdvance()
/*      */   {
/* 1519 */     for (char c = 'ً'; c <= '٘'; c = (char)(c + '\001'))
/* 1520 */       setCharAdvance(c, 0);
/* 1521 */     setCharAdvance(1648, 0);
/* 1522 */     for (char c = 'ۖ'; c <= 'ۜ'; c = (char)(c + '\001'))
/* 1523 */       setCharAdvance(c, 0);
/* 1524 */     for (char c = '۟'; c <= 'ۤ'; c = (char)(c + '\001'))
/* 1525 */       setCharAdvance(c, 0);
/* 1526 */     for (char c = 'ۧ'; c <= 'ۨ'; c = (char)(c + '\001'))
/* 1527 */       setCharAdvance(c, 0);
/* 1528 */     for (char c = '۪'; c <= 'ۭ'; c = (char)(c + '\001'))
/* 1529 */       setCharAdvance(c, 0);
/*      */   }
/*      */ 
/*      */   public void addSubsetRange(int[] range)
/*      */   {
/* 1539 */     if (this.subsetRanges == null)
/* 1540 */       this.subsetRanges = new ArrayList();
/* 1541 */     this.subsetRanges.add(range);
/*      */   }
/*      */ 
/*      */   public int getCompressionLevel()
/*      */   {
/* 1550 */     return this.compressionLevel;
/*      */   }
/*      */ 
/*      */   public void setCompressionLevel(int compressionLevel)
/*      */   {
/* 1559 */     if ((compressionLevel < 0) || (compressionLevel > 9))
/* 1560 */       this.compressionLevel = -1;
/*      */     else
/* 1562 */       this.compressionLevel = compressionLevel;
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*  325 */     BuiltinFonts14.put("Courier", PdfName.COURIER);
/*  326 */     BuiltinFonts14.put("Courier-Bold", PdfName.COURIER_BOLD);
/*  327 */     BuiltinFonts14.put("Courier-BoldOblique", PdfName.COURIER_BOLDOBLIQUE);
/*  328 */     BuiltinFonts14.put("Courier-Oblique", PdfName.COURIER_OBLIQUE);
/*  329 */     BuiltinFonts14.put("Helvetica", PdfName.HELVETICA);
/*  330 */     BuiltinFonts14.put("Helvetica-Bold", PdfName.HELVETICA_BOLD);
/*  331 */     BuiltinFonts14.put("Helvetica-BoldOblique", PdfName.HELVETICA_BOLDOBLIQUE);
/*  332 */     BuiltinFonts14.put("Helvetica-Oblique", PdfName.HELVETICA_OBLIQUE);
/*  333 */     BuiltinFonts14.put("Symbol", PdfName.SYMBOL);
/*  334 */     BuiltinFonts14.put("Times-Roman", PdfName.TIMES_ROMAN);
/*  335 */     BuiltinFonts14.put("Times-Bold", PdfName.TIMES_BOLD);
/*  336 */     BuiltinFonts14.put("Times-BoldItalic", PdfName.TIMES_BOLDITALIC);
/*  337 */     BuiltinFonts14.put("Times-Italic", PdfName.TIMES_ITALIC);
/*  338 */     BuiltinFonts14.put("ZapfDingbats", PdfName.ZAPFDINGBATS);
/*      */   }
/*      */ 
/*      */   static class StreamFont extends PdfStream
/*      */   {
/*      */     public StreamFont(byte[] contents, int[] lengths, int compressionLevel)
/*      */       throws DocumentException
/*      */     {
/*      */       try
/*      */       {
/*  356 */         this.bytes = contents;
/*  357 */         put(PdfName.LENGTH, new PdfNumber(this.bytes.length));
/*  358 */         for (int k = 0; k < lengths.length; k++) {
/*  359 */           put(new PdfName("Length" + (k + 1)), new PdfNumber(lengths[k]));
/*      */         }
/*  361 */         flateCompress(compressionLevel);
/*      */       }
/*      */       catch (Exception e) {
/*  364 */         throw new DocumentException(e);
/*      */       }
/*      */     }
/*      */ 
/*      */     public StreamFont(byte[] contents, String subType, int compressionLevel)
/*      */       throws DocumentException
/*      */     {
/*      */       try
/*      */       {
/*  378 */         this.bytes = contents;
/*  379 */         put(PdfName.LENGTH, new PdfNumber(this.bytes.length));
/*  380 */         if (subType != null)
/*  381 */           put(PdfName.SUBTYPE, new PdfName(subType));
/*  382 */         flateCompress(compressionLevel);
/*      */       }
/*      */       catch (Exception e) {
/*  385 */         throw new DocumentException(e);
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.BaseFont
 * JD-Core Version:    0.6.2
 */