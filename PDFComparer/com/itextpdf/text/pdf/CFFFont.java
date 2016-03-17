/*      */ package com.itextpdf.text.pdf;
/*      */ 
/*      */ import com.itextpdf.text.ExceptionConverter;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ 
/*      */ public class CFFFont
/*      */ {
/*   86 */   static final String[] operatorNames = { "version", "Notice", "FullName", "FamilyName", "Weight", "FontBBox", "BlueValues", "OtherBlues", "FamilyBlues", "FamilyOtherBlues", "StdHW", "StdVW", "UNKNOWN_12", "UniqueID", "XUID", "charset", "Encoding", "CharStrings", "Private", "Subrs", "defaultWidthX", "nominalWidthX", "UNKNOWN_22", "UNKNOWN_23", "UNKNOWN_24", "UNKNOWN_25", "UNKNOWN_26", "UNKNOWN_27", "UNKNOWN_28", "UNKNOWN_29", "UNKNOWN_30", "UNKNOWN_31", "Copyright", "isFixedPitch", "ItalicAngle", "UnderlinePosition", "UnderlineThickness", "PaintType", "CharstringType", "FontMatrix", "StrokeWidth", "BlueScale", "BlueShift", "BlueFuzz", "StemSnapH", "StemSnapV", "ForceBold", "UNKNOWN_12_15", "UNKNOWN_12_16", "LanguageGroup", "ExpansionFactor", "initialRandomSeed", "SyntheticBase", "PostScript", "BaseFontName", "BaseFontBlend", "UNKNOWN_12_24", "UNKNOWN_12_25", "UNKNOWN_12_26", "UNKNOWN_12_27", "UNKNOWN_12_28", "UNKNOWN_12_29", "ROS", "CIDFontVersion", "CIDFontRevision", "CIDFontType", "CIDCount", "UIDBase", "FDArray", "FDSelect", "FontName" };
/*      */ 
/*  107 */   static final String[] standardStrings = { ".notdef", "space", "exclam", "quotedbl", "numbersign", "dollar", "percent", "ampersand", "quoteright", "parenleft", "parenright", "asterisk", "plus", "comma", "hyphen", "period", "slash", "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "colon", "semicolon", "less", "equal", "greater", "question", "at", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "bracketleft", "backslash", "bracketright", "asciicircum", "underscore", "quoteleft", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "braceleft", "bar", "braceright", "asciitilde", "exclamdown", "cent", "sterling", "fraction", "yen", "florin", "section", "currency", "quotesingle", "quotedblleft", "guillemotleft", "guilsinglleft", "guilsinglright", "fi", "fl", "endash", "dagger", "daggerdbl", "periodcentered", "paragraph", "bullet", "quotesinglbase", "quotedblbase", "quotedblright", "guillemotright", "ellipsis", "perthousand", "questiondown", "grave", "acute", "circumflex", "tilde", "macron", "breve", "dotaccent", "dieresis", "ring", "cedilla", "hungarumlaut", "ogonek", "caron", "emdash", "AE", "ordfeminine", "Lslash", "Oslash", "OE", "ordmasculine", "ae", "dotlessi", "lslash", "oslash", "oe", "germandbls", "onesuperior", "logicalnot", "mu", "trademark", "Eth", "onehalf", "plusminus", "Thorn", "onequarter", "divide", "brokenbar", "degree", "thorn", "threequarters", "twosuperior", "registered", "minus", "eth", "multiply", "threesuperior", "copyright", "Aacute", "Acircumflex", "Adieresis", "Agrave", "Aring", "Atilde", "Ccedilla", "Eacute", "Ecircumflex", "Edieresis", "Egrave", "Iacute", "Icircumflex", "Idieresis", "Igrave", "Ntilde", "Oacute", "Ocircumflex", "Odieresis", "Ograve", "Otilde", "Scaron", "Uacute", "Ucircumflex", "Udieresis", "Ugrave", "Yacute", "Ydieresis", "Zcaron", "aacute", "acircumflex", "adieresis", "agrave", "aring", "atilde", "ccedilla", "eacute", "ecircumflex", "edieresis", "egrave", "iacute", "icircumflex", "idieresis", "igrave", "ntilde", "oacute", "ocircumflex", "odieresis", "ograve", "otilde", "scaron", "uacute", "ucircumflex", "udieresis", "ugrave", "yacute", "ydieresis", "zcaron", "exclamsmall", "Hungarumlautsmall", "dollaroldstyle", "dollarsuperior", "ampersandsmall", "Acutesmall", "parenleftsuperior", "parenrightsuperior", "twodotenleader", "onedotenleader", "zerooldstyle", "oneoldstyle", "twooldstyle", "threeoldstyle", "fouroldstyle", "fiveoldstyle", "sixoldstyle", "sevenoldstyle", "eightoldstyle", "nineoldstyle", "commasuperior", "threequartersemdash", "periodsuperior", "questionsmall", "asuperior", "bsuperior", "centsuperior", "dsuperior", "esuperior", "isuperior", "lsuperior", "msuperior", "nsuperior", "osuperior", "rsuperior", "ssuperior", "tsuperior", "ff", "ffi", "ffl", "parenleftinferior", "parenrightinferior", "Circumflexsmall", "hyphensuperior", "Gravesmall", "Asmall", "Bsmall", "Csmall", "Dsmall", "Esmall", "Fsmall", "Gsmall", "Hsmall", "Ismall", "Jsmall", "Ksmall", "Lsmall", "Msmall", "Nsmall", "Osmall", "Psmall", "Qsmall", "Rsmall", "Ssmall", "Tsmall", "Usmall", "Vsmall", "Wsmall", "Xsmall", "Ysmall", "Zsmall", "colonmonetary", "onefitted", "rupiah", "Tildesmall", "exclamdownsmall", "centoldstyle", "Lslashsmall", "Scaronsmall", "Zcaronsmall", "Dieresissmall", "Brevesmall", "Caronsmall", "Dotaccentsmall", "Macronsmall", "figuredash", "hypheninferior", "Ogoneksmall", "Ringsmall", "Cedillasmall", "questiondownsmall", "oneeighth", "threeeighths", "fiveeighths", "seveneighths", "onethird", "twothirds", "zerosuperior", "foursuperior", "fivesuperior", "sixsuperior", "sevensuperior", "eightsuperior", "ninesuperior", "zeroinferior", "oneinferior", "twoinferior", "threeinferior", "fourinferior", "fiveinferior", "sixinferior", "seveninferior", "eightinferior", "nineinferior", "centinferior", "dollarinferior", "periodinferior", "commainferior", "Agravesmall", "Aacutesmall", "Acircumflexsmall", "Atildesmall", "Adieresissmall", "Aringsmall", "AEsmall", "Ccedillasmall", "Egravesmall", "Eacutesmall", "Ecircumflexsmall", "Edieresissmall", "Igravesmall", "Iacutesmall", "Icircumflexsmall", "Idieresissmall", "Ethsmall", "Ntildesmall", "Ogravesmall", "Oacutesmall", "Ocircumflexsmall", "Otildesmall", "Odieresissmall", "OEsmall", "Oslashsmall", "Ugravesmall", "Uacutesmall", "Ucircumflexsmall", "Udieresissmall", "Yacutesmall", "Thornsmall", "Ydieresissmall", "001.000", "001.001", "001.002", "001.003", "Black", "Bold", "Book", "Light", "Medium", "Regular", "Roman", "Semibold" };
/*      */   int nextIndexOffset;
/*      */   protected String key;
/*  295 */   protected Object[] args = new Object[48];
/*  296 */   protected int arg_count = 0;
/*      */   protected RandomAccessFileOrArray buf;
/*      */   private int offSize;
/*      */   protected int nameIndexOffset;
/*      */   protected int topdictIndexOffset;
/*      */   protected int stringIndexOffset;
/*      */   protected int gsubrIndexOffset;
/*      */   protected int[] nameOffsets;
/*      */   protected int[] topdictOffsets;
/*      */   protected int[] stringOffsets;
/*      */   protected int[] gsubrOffsets;
/*      */   protected Font[] fonts;
/*      */ 
/*      */   public String getString(char sid)
/*      */   {
/*  181 */     if (sid < standardStrings.length) return standardStrings[sid];
/*  182 */     if (sid >= standardStrings.length + this.stringOffsets.length - 1) return null;
/*  183 */     int j = sid - standardStrings.length;
/*      */ 
/*  185 */     int p = getPosition();
/*  186 */     seek(this.stringOffsets[j]);
/*  187 */     StringBuffer s = new StringBuffer();
/*  188 */     for (int k = this.stringOffsets[j]; k < this.stringOffsets[(j + 1)]; k++) {
/*  189 */       s.append(getCard8());
/*      */     }
/*  191 */     seek(p);
/*  192 */     return s.toString();
/*      */   }
/*      */ 
/*      */   char getCard8() {
/*      */     try {
/*  197 */       byte i = this.buf.readByte();
/*  198 */       return (char)(i & 0xFF);
/*      */     }
/*      */     catch (Exception e) {
/*  201 */       throw new ExceptionConverter(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   char getCard16() {
/*      */     try {
/*  207 */       return this.buf.readChar();
/*      */     }
/*      */     catch (Exception e) {
/*  210 */       throw new ExceptionConverter(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   int getOffset(int offSize) {
/*  215 */     int offset = 0;
/*  216 */     for (int i = 0; i < offSize; i++) {
/*  217 */       offset *= 256;
/*  218 */       offset += getCard8();
/*      */     }
/*  220 */     return offset;
/*      */   }
/*      */ 
/*      */   void seek(int offset) {
/*      */     try {
/*  225 */       this.buf.seek(offset);
/*      */     }
/*      */     catch (Exception e) {
/*  228 */       throw new ExceptionConverter(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   short getShort() {
/*      */     try {
/*  234 */       return this.buf.readShort();
/*      */     }
/*      */     catch (Exception e) {
/*  237 */       throw new ExceptionConverter(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   int getInt() {
/*      */     try {
/*  243 */       return this.buf.readInt();
/*      */     }
/*      */     catch (Exception e) {
/*  246 */       throw new ExceptionConverter(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   int getPosition() {
/*      */     try {
/*  252 */       return (int)this.buf.getFilePointer();
/*      */     }
/*      */     catch (Exception e) {
/*  255 */       throw new ExceptionConverter(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   int[] getIndex(int nextIndexOffset)
/*      */   {
/*  266 */     seek(nextIndexOffset);
/*  267 */     int count = getCard16();
/*  268 */     int[] offsets = new int[count + 1];
/*      */ 
/*  270 */     if (count == 0) {
/*  271 */       offsets[0] = -1;
/*  272 */       nextIndexOffset += 2;
/*  273 */       return offsets;
/*      */     }
/*      */ 
/*  276 */     int indexOffSize = getCard8();
/*      */ 
/*  278 */     for (int j = 0; j <= count; j++)
/*      */     {
/*  280 */       offsets[j] = (nextIndexOffset + 2 + 1 + (count + 1) * indexOffSize - 1 + getOffset(indexOffSize));
/*      */     }
/*      */ 
/*  291 */     return offsets;
/*      */   }
/*      */ 
/*      */   protected void getDictItem()
/*      */   {
/*  299 */     for (int i = 0; i < this.arg_count; i++) this.args[i] = null;
/*  300 */     this.arg_count = 0;
/*  301 */     this.key = null;
/*  302 */     boolean gotKey = false;
/*      */ 
/*  304 */     while (!gotKey) {
/*  305 */       char b0 = getCard8();
/*  306 */       if (b0 == '\035') {
/*  307 */         int item = getInt();
/*  308 */         this.args[this.arg_count] = Integer.valueOf(item);
/*  309 */         this.arg_count += 1;
/*      */       }
/*  313 */       else if (b0 == '\034') {
/*  314 */         short item = getShort();
/*  315 */         this.args[this.arg_count] = Integer.valueOf(item);
/*  316 */         this.arg_count += 1;
/*      */       }
/*  320 */       else if ((b0 >= ' ') && (b0 <= 'ö')) {
/*  321 */         byte item = (byte)(b0 - '');
/*  322 */         this.args[this.arg_count] = Integer.valueOf(item);
/*  323 */         this.arg_count += 1;
/*      */       }
/*  327 */       else if ((b0 >= '÷') && (b0 <= 'ú')) {
/*  328 */         char b1 = getCard8();
/*  329 */         short item = (short)((b0 - '÷') * 256 + b1 + 108);
/*  330 */         this.args[this.arg_count] = Integer.valueOf(item);
/*  331 */         this.arg_count += 1;
/*      */       }
/*  335 */       else if ((b0 >= 'û') && (b0 <= 'þ')) {
/*  336 */         char b1 = getCard8();
/*  337 */         short item = (short)(-(b0 - 'û') * 256 - b1 - 108);
/*  338 */         this.args[this.arg_count] = Integer.valueOf(item);
/*  339 */         this.arg_count += 1;
/*      */       }
/*  343 */       else if (b0 == '\036') {
/*  344 */         StringBuilder item = new StringBuilder("");
/*  345 */         boolean done = false;
/*  346 */         char buffer = '\000';
/*  347 */         byte avail = 0;
/*  348 */         int nibble = 0;
/*  349 */         while (!done)
/*      */         {
/*  351 */           if (avail == 0) { buffer = getCard8(); avail = 2; }
/*  352 */           if (avail == 1) { nibble = buffer / '\020'; avail = (byte)(avail - 1); }
/*  353 */           if (avail == 2) { nibble = buffer % '\020'; avail = (byte)(avail - 1); }
/*  354 */           switch (nibble) { case 10:
/*  355 */             item.append("."); break;
/*      */           case 11:
/*  356 */             item.append("E"); break;
/*      */           case 12:
/*  357 */             item.append("E-"); break;
/*      */           case 14:
/*  358 */             item.append("-"); break;
/*      */           case 15:
/*  359 */             done = true; break;
/*      */           case 13:
/*      */           default:
/*  361 */             if ((nibble >= 0) && (nibble <= 9)) {
/*  362 */               item.append(String.valueOf(nibble));
/*      */             } else {
/*  364 */               item.append("<NIBBLE ERROR: ").append(nibble).append('>');
/*  365 */               done = true;
/*      */             }
/*      */             break;
/*      */           }
/*      */         }
/*  370 */         this.args[this.arg_count] = item.toString();
/*  371 */         this.arg_count += 1;
/*      */       }
/*  375 */       else if (b0 <= '\025') {
/*  376 */         gotKey = true;
/*  377 */         if (b0 != '\f') this.key = operatorNames[b0]; else
/*  378 */           this.key = operatorNames[(' ' + getCard8())];
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected RangeItem getEntireIndexRange(int indexOffset)
/*      */   {
/*  692 */     seek(indexOffset);
/*  693 */     int count = getCard16();
/*  694 */     if (count == 0) {
/*  695 */       return new RangeItem(this.buf, indexOffset, 2);
/*      */     }
/*  697 */     int indexOffSize = getCard8();
/*  698 */     seek(indexOffset + 2 + 1 + count * indexOffSize);
/*  699 */     int size = getOffset(indexOffSize) - 1;
/*  700 */     return new RangeItem(this.buf, indexOffset, 3 + (count + 1) * indexOffSize + size);
/*      */   }
/*      */ 
/*      */   public byte[] getCID(String fontName)
/*      */   {
/*  721 */     for (int j = 0; (j < this.fonts.length) && 
/*  722 */       (!fontName.equals(this.fonts[j].name)); j++);
/*  723 */     if (j == this.fonts.length) return null;
/*      */ 
/*  725 */     LinkedList l = new LinkedList();
/*      */ 
/*  729 */     seek(0);
/*      */ 
/*  731 */     int major = getCard8();
/*  732 */     int minor = getCard8();
/*  733 */     int hdrSize = getCard8();
/*  734 */     int offSize = getCard8();
/*  735 */     this.nextIndexOffset = hdrSize;
/*      */ 
/*  737 */     l.addLast(new RangeItem(this.buf, 0, hdrSize));
/*      */ 
/*  739 */     int nglyphs = -1; int nstrings = -1;
/*  740 */     if (!this.fonts[j].isCID)
/*      */     {
/*  742 */       seek(this.fonts[j].charstringsOffset);
/*  743 */       nglyphs = getCard16();
/*  744 */       seek(this.stringIndexOffset);
/*  745 */       nstrings = getCard16() + standardStrings.length;
/*      */     }
/*      */ 
/*  751 */     l.addLast(new UInt16Item('\001'));
/*  752 */     l.addLast(new UInt8Item('\001'));
/*  753 */     l.addLast(new UInt8Item('\001'));
/*  754 */     l.addLast(new UInt8Item((char)(1 + this.fonts[j].name.length())));
/*  755 */     l.addLast(new StringItem(this.fonts[j].name));
/*      */ 
/*  760 */     l.addLast(new UInt16Item('\001'));
/*  761 */     l.addLast(new UInt8Item('\002'));
/*  762 */     l.addLast(new UInt16Item('\001'));
/*  763 */     OffsetItem topdictIndex1Ref = new IndexOffsetItem(2);
/*  764 */     l.addLast(topdictIndex1Ref);
/*  765 */     IndexBaseItem topdictBase = new IndexBaseItem();
/*  766 */     l.addLast(topdictBase);
/*      */ 
/*  778 */     OffsetItem charsetRef = new DictOffsetItem();
/*  779 */     OffsetItem charstringsRef = new DictOffsetItem();
/*  780 */     OffsetItem fdarrayRef = new DictOffsetItem();
/*  781 */     OffsetItem fdselectRef = new DictOffsetItem();
/*      */ 
/*  783 */     if (!this.fonts[j].isCID)
/*      */     {
/*  785 */       l.addLast(new DictNumberItem(nstrings));
/*  786 */       l.addLast(new DictNumberItem(nstrings + 1));
/*  787 */       l.addLast(new DictNumberItem(0));
/*  788 */       l.addLast(new UInt8Item('\f'));
/*  789 */       l.addLast(new UInt8Item('\036'));
/*      */ 
/*  791 */       l.addLast(new DictNumberItem(nglyphs));
/*  792 */       l.addLast(new UInt8Item('\f'));
/*  793 */       l.addLast(new UInt8Item('"'));
/*      */     }
/*      */ 
/*  799 */     l.addLast(fdarrayRef);
/*  800 */     l.addLast(new UInt8Item('\f'));
/*  801 */     l.addLast(new UInt8Item('$'));
/*      */ 
/*  803 */     l.addLast(fdselectRef);
/*  804 */     l.addLast(new UInt8Item('\f'));
/*  805 */     l.addLast(new UInt8Item('%'));
/*      */ 
/*  807 */     l.addLast(charsetRef);
/*  808 */     l.addLast(new UInt8Item('\017'));
/*      */ 
/*  810 */     l.addLast(charstringsRef);
/*  811 */     l.addLast(new UInt8Item('\021'));
/*      */ 
/*  813 */     seek(this.topdictOffsets[j]);
/*  814 */     while (getPosition() < this.topdictOffsets[(j + 1)]) {
/*  815 */       int p1 = getPosition();
/*  816 */       getDictItem();
/*  817 */       int p2 = getPosition();
/*  818 */       if ((this.key != "Encoding") && (this.key != "Private") && (this.key != "FDSelect") && (this.key != "FDArray") && (this.key != "charset") && (this.key != "CharStrings"))
/*      */       {
/*  827 */         l.add(new RangeItem(this.buf, p1, p2 - p1));
/*      */       }
/*      */     }
/*      */ 
/*  831 */     l.addLast(new IndexMarkerItem(topdictIndex1Ref, topdictBase));
/*      */ 
/*  837 */     if (this.fonts[j].isCID) {
/*  838 */       l.addLast(getEntireIndexRange(this.stringIndexOffset));
/*      */     } else {
/*  840 */       String fdFontName = this.fonts[j].name + "-OneRange";
/*  841 */       if (fdFontName.length() > 127)
/*  842 */         fdFontName = fdFontName.substring(0, 127);
/*  843 */       String extraStrings = "AdobeIdentity" + fdFontName;
/*      */ 
/*  845 */       int origStringsLen = this.stringOffsets[(this.stringOffsets.length - 1)] - this.stringOffsets[0];
/*      */ 
/*  847 */       int stringsBaseOffset = this.stringOffsets[0] - 1;
/*      */       byte stringsIndexOffSize;
/*      */       byte stringsIndexOffSize;
/*  850 */       if (origStringsLen + extraStrings.length() <= 255) { stringsIndexOffSize = 1; }
/*      */       else
/*      */       {
/*  851 */         byte stringsIndexOffSize;
/*  851 */         if (origStringsLen + extraStrings.length() <= 65535) { stringsIndexOffSize = 2; }
/*      */         else
/*      */         {
/*  852 */           byte stringsIndexOffSize;
/*  852 */           if (origStringsLen + extraStrings.length() <= 16777215) stringsIndexOffSize = 3; else
/*  853 */             stringsIndexOffSize = 4; 
/*      */         }
/*      */       }
/*  855 */       l.addLast(new UInt16Item((char)(this.stringOffsets.length - 1 + 3)));
/*  856 */       l.addLast(new UInt8Item((char)stringsIndexOffSize));
/*  857 */       for (int stringOffset : this.stringOffsets) {
/*  858 */         l.addLast(new IndexOffsetItem(stringsIndexOffSize, stringOffset - stringsBaseOffset));
/*      */       }
/*  860 */       int currentStringsOffset = this.stringOffsets[(this.stringOffsets.length - 1)] - stringsBaseOffset;
/*      */ 
/*  863 */       currentStringsOffset += "Adobe".length();
/*  864 */       l.addLast(new IndexOffsetItem(stringsIndexOffSize, currentStringsOffset));
/*  865 */       currentStringsOffset += "Identity".length();
/*  866 */       l.addLast(new IndexOffsetItem(stringsIndexOffSize, currentStringsOffset));
/*  867 */       currentStringsOffset += fdFontName.length();
/*  868 */       l.addLast(new IndexOffsetItem(stringsIndexOffSize, currentStringsOffset));
/*      */ 
/*  870 */       l.addLast(new RangeItem(this.buf, this.stringOffsets[0], origStringsLen));
/*  871 */       l.addLast(new StringItem(extraStrings));
/*      */     }
/*      */ 
/*  876 */     l.addLast(getEntireIndexRange(this.gsubrIndexOffset));
/*      */ 
/*  880 */     if (!this.fonts[j].isCID)
/*      */     {
/*  884 */       l.addLast(new MarkerItem(fdselectRef));
/*  885 */       l.addLast(new UInt8Item('\003'));
/*  886 */       l.addLast(new UInt16Item('\001'));
/*      */ 
/*  888 */       l.addLast(new UInt16Item('\000'));
/*  889 */       l.addLast(new UInt8Item('\000'));
/*      */ 
/*  891 */       l.addLast(new UInt16Item((char)nglyphs));
/*      */ 
/*  896 */       l.addLast(new MarkerItem(charsetRef));
/*  897 */       l.addLast(new UInt8Item('\002'));
/*      */ 
/*  899 */       l.addLast(new UInt16Item('\001'));
/*  900 */       l.addLast(new UInt16Item((char)(nglyphs - 1)));
/*      */ 
/*  905 */       l.addLast(new MarkerItem(fdarrayRef));
/*  906 */       l.addLast(new UInt16Item('\001'));
/*  907 */       l.addLast(new UInt8Item('\001'));
/*  908 */       l.addLast(new UInt8Item('\001'));
/*      */ 
/*  910 */       OffsetItem privateIndex1Ref = new IndexOffsetItem(1);
/*  911 */       l.addLast(privateIndex1Ref);
/*  912 */       IndexBaseItem privateBase = new IndexBaseItem();
/*  913 */       l.addLast(privateBase);
/*      */ 
/*  922 */       l.addLast(new DictNumberItem(this.fonts[j].privateLength));
/*  923 */       OffsetItem privateRef = new DictOffsetItem();
/*  924 */       l.addLast(privateRef);
/*  925 */       l.addLast(new UInt8Item('\022'));
/*      */ 
/*  927 */       l.addLast(new IndexMarkerItem(privateIndex1Ref, privateBase));
/*      */ 
/*  931 */       l.addLast(new MarkerItem(privateRef));
/*      */ 
/*  935 */       l.addLast(new RangeItem(this.buf, this.fonts[j].privateOffset, this.fonts[j].privateLength));
/*  936 */       if (this.fonts[j].privateSubrs >= 0)
/*      */       {
/*  938 */         l.addLast(getEntireIndexRange(this.fonts[j].privateSubrs));
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  944 */     l.addLast(new MarkerItem(charstringsRef));
/*  945 */     l.addLast(getEntireIndexRange(this.fonts[j].charstringsOffset));
/*      */ 
/*  949 */     int[] currentOffset = new int[1];
/*  950 */     currentOffset[0] = 0;
/*      */ 
/*  952 */     Iterator listIter = l.iterator();
/*  953 */     while (listIter.hasNext()) {
/*  954 */       Item item = (Item)listIter.next();
/*  955 */       item.increment(currentOffset);
/*      */     }
/*      */ 
/*  958 */     listIter = l.iterator();
/*  959 */     while (listIter.hasNext()) {
/*  960 */       Item item = (Item)listIter.next();
/*  961 */       item.xref();
/*      */     }
/*      */ 
/*  964 */     int size = currentOffset[0];
/*  965 */     byte[] b = new byte[size];
/*      */ 
/*  967 */     listIter = l.iterator();
/*  968 */     while (listIter.hasNext()) {
/*  969 */       Item item = (Item)listIter.next();
/*  970 */       item.emit(b);
/*      */     }
/*      */ 
/*  973 */     return b;
/*      */   }
/*      */ 
/*      */   public boolean isCID(String fontName)
/*      */   {
/*  979 */     for (int j = 0; j < this.fonts.length; j++)
/*  980 */       if (fontName.equals(this.fonts[j].name)) return this.fonts[j].isCID;
/*  981 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean exists(String fontName)
/*      */   {
/*  986 */     for (int j = 0; j < this.fonts.length; j++)
/*  987 */       if (fontName.equals(this.fonts[j].name)) return true;
/*  988 */     return false;
/*      */   }
/*      */ 
/*      */   public String[] getNames()
/*      */   {
/*  993 */     String[] names = new String[this.fonts.length];
/*  994 */     for (int i = 0; i < this.fonts.length; i++)
/*  995 */       names[i] = this.fonts[i].name;
/*  996 */     return names;
/*      */   }
/*      */ 
/*      */   public CFFFont(RandomAccessFileOrArray inputbuffer)
/*      */   {
/* 1055 */     this.buf = inputbuffer;
/* 1056 */     seek(0);
/*      */ 
/* 1059 */     int major = getCard8();
/* 1060 */     int minor = getCard8();
/*      */ 
/* 1064 */     int hdrSize = getCard8();
/*      */ 
/* 1066 */     this.offSize = getCard8();
/*      */ 
/* 1072 */     this.nameIndexOffset = hdrSize;
/* 1073 */     this.nameOffsets = getIndex(this.nameIndexOffset);
/* 1074 */     this.topdictIndexOffset = this.nameOffsets[(this.nameOffsets.length - 1)];
/* 1075 */     this.topdictOffsets = getIndex(this.topdictIndexOffset);
/* 1076 */     this.stringIndexOffset = this.topdictOffsets[(this.topdictOffsets.length - 1)];
/* 1077 */     this.stringOffsets = getIndex(this.stringIndexOffset);
/* 1078 */     this.gsubrIndexOffset = this.stringOffsets[(this.stringOffsets.length - 1)];
/* 1079 */     this.gsubrOffsets = getIndex(this.gsubrIndexOffset);
/*      */ 
/* 1081 */     this.fonts = new Font[this.nameOffsets.length - 1];
/*      */ 
/* 1095 */     for (int j = 0; j < this.nameOffsets.length - 1; j++) {
/* 1096 */       this.fonts[j] = new Font();
/* 1097 */       seek(this.nameOffsets[j]);
/* 1098 */       this.fonts[j].name = "";
/* 1099 */       for (int k = this.nameOffsets[j]; k < this.nameOffsets[(j + 1)]; k++) {
/* 1100 */         this.fonts[j].name += getCard8();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1124 */     for (int j = 0; j < this.topdictOffsets.length - 1; j++) {
/* 1125 */       seek(this.topdictOffsets[j]);
/* 1126 */       while (getPosition() < this.topdictOffsets[(j + 1)]) {
/* 1127 */         getDictItem();
/* 1128 */         if (this.key == "FullName")
/*      */         {
/* 1130 */           this.fonts[j].fullName = getString((char)((Integer)this.args[0]).intValue());
/*      */         }
/* 1132 */         else if (this.key == "ROS") {
/* 1133 */           this.fonts[j].isCID = true;
/* 1134 */         } else if (this.key == "Private") {
/* 1135 */           this.fonts[j].privateLength = ((Integer)this.args[0]).intValue();
/* 1136 */           this.fonts[j].privateOffset = ((Integer)this.args[1]).intValue();
/*      */         }
/* 1138 */         else if (this.key == "charset") {
/* 1139 */           this.fonts[j].charsetOffset = ((Integer)this.args[0]).intValue();
/*      */         }
/* 1149 */         else if (this.key == "CharStrings") {
/* 1150 */           this.fonts[j].charstringsOffset = ((Integer)this.args[0]).intValue();
/*      */ 
/* 1153 */           int p = getPosition();
/* 1154 */           this.fonts[j].charstringsOffsets = getIndex(this.fonts[j].charstringsOffset);
/* 1155 */           seek(p);
/* 1156 */         } else if (this.key == "FDArray") {
/* 1157 */           this.fonts[j].fdarrayOffset = ((Integer)this.args[0]).intValue();
/* 1158 */         } else if (this.key == "FDSelect") {
/* 1159 */           this.fonts[j].fdselectOffset = ((Integer)this.args[0]).intValue();
/* 1160 */         } else if (this.key == "CharstringType") {
/* 1161 */           this.fonts[j].CharstringType = ((Integer)this.args[0]).intValue();
/*      */         }
/*      */       }
/*      */ 
/* 1165 */       if (this.fonts[j].privateOffset >= 0)
/*      */       {
/* 1167 */         seek(this.fonts[j].privateOffset);
/* 1168 */         while (getPosition() < this.fonts[j].privateOffset + this.fonts[j].privateLength) {
/* 1169 */           getDictItem();
/* 1170 */           if (this.key == "Subrs")
/*      */           {
/* 1173 */             this.fonts[j].privateSubrs = (((Integer)this.args[0]).intValue() + this.fonts[j].privateOffset);
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/* 1178 */       if (this.fonts[j].fdarrayOffset >= 0) {
/* 1179 */         int[] fdarrayOffsets = getIndex(this.fonts[j].fdarrayOffset);
/*      */ 
/* 1181 */         this.fonts[j].fdprivateOffsets = new int[fdarrayOffsets.length - 1];
/* 1182 */         this.fonts[j].fdprivateLengths = new int[fdarrayOffsets.length - 1];
/*      */ 
/* 1186 */         for (int k = 0; k < fdarrayOffsets.length - 1; k++) {
/* 1187 */           seek(fdarrayOffsets[k]);
/* 1188 */           while (getPosition() < fdarrayOffsets[(k + 1)]) {
/* 1189 */             getDictItem();
/* 1190 */             if (this.key == "Private") {
/* 1191 */               this.fonts[j].fdprivateLengths[k] = ((Integer)this.args[0]).intValue();
/* 1192 */               this.fonts[j].fdprivateOffsets[k] = ((Integer)this.args[1]).intValue();
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   void ReadEncoding(int nextIndexOffset)
/*      */   {
/* 1205 */     seek(nextIndexOffset);
/* 1206 */     int format = getCard8();
/*      */   }
/*      */ 
/*      */   protected final class Font
/*      */   {
/*      */     public String name;
/*      */     public String fullName;
/* 1019 */     public boolean isCID = false;
/* 1020 */     public int privateOffset = -1;
/* 1021 */     public int privateLength = -1;
/* 1022 */     public int privateSubrs = -1;
/* 1023 */     public int charstringsOffset = -1;
/* 1024 */     public int encodingOffset = -1;
/* 1025 */     public int charsetOffset = -1;
/* 1026 */     public int fdarrayOffset = -1;
/* 1027 */     public int fdselectOffset = -1;
/*      */     public int[] fdprivateOffsets;
/*      */     public int[] fdprivateLengths;
/*      */     public int[] fdprivateSubrs;
/*      */     public int nglyphs;
/*      */     public int nstrings;
/*      */     public int CharsetLength;
/*      */     public int[] charstringsOffsets;
/*      */     public int[] charset;
/*      */     public int[] FDSelect;
/*      */     public int FDSelectLength;
/*      */     public int FDSelectFormat;
/* 1041 */     public int CharstringType = 2;
/*      */     public int FDArrayCount;
/*      */     public int FDArrayOffsize;
/*      */     public int[] FDArrayOffsets;
/*      */     public int[] PrivateSubrsOffset;
/*      */     public int[][] PrivateSubrsOffsetsArray;
/*      */     public int[] SubrsOffsets;
/*      */ 
/*      */     protected Font()
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   protected static final class MarkerItem extends CFFFont.Item
/*      */   {
/*      */     CFFFont.OffsetItem p;
/*      */ 
/*      */     public MarkerItem(CFFFont.OffsetItem pointerToMarker)
/*      */     {
/*  678 */       this.p = pointerToMarker;
/*      */     }
/*      */     public void xref() {
/*  681 */       this.p.set(this.myOffset);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected static final class DictNumberItem extends CFFFont.Item
/*      */   {
/*      */     public final int value;
/*  652 */     public int size = 5;
/*      */ 
/*  653 */     public DictNumberItem(int value) { this.value = value; }
/*      */ 
/*      */     public void increment(int[] currentOffset) {
/*  656 */       super.increment(currentOffset);
/*  657 */       currentOffset[0] += this.size;
/*      */     }
/*      */ 
/*      */     public void emit(byte[] buffer)
/*      */     {
/*  662 */       if (this.size == 5) {
/*  663 */         buffer[this.myOffset] = 29;
/*  664 */         buffer[(this.myOffset + 1)] = ((byte)(this.value >>> 24 & 0xFF));
/*  665 */         buffer[(this.myOffset + 2)] = ((byte)(this.value >>> 16 & 0xFF));
/*  666 */         buffer[(this.myOffset + 3)] = ((byte)(this.value >>> 8 & 0xFF));
/*  667 */         buffer[(this.myOffset + 4)] = ((byte)(this.value >>> 0 & 0xFF));
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected static final class StringItem extends CFFFont.Item
/*      */   {
/*      */     public String s;
/*      */ 
/*      */     public StringItem(String s)
/*      */     {
/*  630 */       this.s = s;
/*      */     }
/*      */ 
/*      */     public void increment(int[] currentOffset) {
/*  634 */       super.increment(currentOffset);
/*  635 */       currentOffset[0] += this.s.length();
/*      */     }
/*      */ 
/*      */     public void emit(byte[] buffer) {
/*  639 */       for (int i = 0; i < this.s.length(); i++)
/*  640 */         buffer[(this.myOffset + i)] = ((byte)(this.s.charAt(i) & 0xFF));
/*      */     }
/*      */   }
/*      */ 
/*      */   protected static final class UInt8Item extends CFFFont.Item
/*      */   {
/*      */     public char value;
/*      */ 
/*      */     public UInt8Item(char value)
/*      */     {
/*  614 */       this.value = value;
/*      */     }
/*      */ 
/*      */     public void increment(int[] currentOffset) {
/*  618 */       super.increment(currentOffset);
/*  619 */       currentOffset[0] += 1;
/*      */     }
/*      */ 
/*      */     public void emit(byte[] buffer)
/*      */     {
/*  624 */       buffer[(this.myOffset + 0)] = ((byte)(this.value >>> '\000' & 0xFF));
/*      */     }
/*      */   }
/*      */ 
/*      */   protected static final class UInt16Item extends CFFFont.Item
/*      */   {
/*      */     public char value;
/*      */ 
/*      */     public UInt16Item(char value)
/*      */     {
/*  594 */       this.value = value;
/*      */     }
/*      */ 
/*      */     public void increment(int[] currentOffset) {
/*  598 */       super.increment(currentOffset);
/*  599 */       currentOffset[0] += 2;
/*      */     }
/*      */ 
/*      */     public void emit(byte[] buffer)
/*      */     {
/*  604 */       buffer[(this.myOffset + 0)] = ((byte)(this.value >>> '\b' & 0xFF));
/*  605 */       buffer[(this.myOffset + 1)] = ((byte)(this.value >>> '\000' & 0xFF));
/*      */     }
/*      */   }
/*      */ 
/*      */   protected static final class UInt32Item extends CFFFont.Item
/*      */   {
/*      */     public int value;
/*      */ 
/*      */     public UInt32Item(int value)
/*      */     {
/*  572 */       this.value = value;
/*      */     }
/*      */ 
/*      */     public void increment(int[] currentOffset) {
/*  576 */       super.increment(currentOffset);
/*  577 */       currentOffset[0] += 4;
/*      */     }
/*      */ 
/*      */     public void emit(byte[] buffer)
/*      */     {
/*  582 */       buffer[(this.myOffset + 0)] = ((byte)(this.value >>> 24 & 0xFF));
/*  583 */       buffer[(this.myOffset + 1)] = ((byte)(this.value >>> 16 & 0xFF));
/*  584 */       buffer[(this.myOffset + 2)] = ((byte)(this.value >>> 8 & 0xFF));
/*  585 */       buffer[(this.myOffset + 3)] = ((byte)(this.value >>> 0 & 0xFF));
/*      */     }
/*      */   }
/*      */ 
/*      */   protected static final class UInt24Item extends CFFFont.Item
/*      */   {
/*      */     public int value;
/*      */ 
/*      */     public UInt24Item(int value)
/*      */     {
/*  551 */       this.value = value;
/*      */     }
/*      */ 
/*      */     public void increment(int[] currentOffset) {
/*  555 */       super.increment(currentOffset);
/*  556 */       currentOffset[0] += 3;
/*      */     }
/*      */ 
/*      */     public void emit(byte[] buffer)
/*      */     {
/*  561 */       buffer[(this.myOffset + 0)] = ((byte)(this.value >>> 16 & 0xFF));
/*  562 */       buffer[(this.myOffset + 1)] = ((byte)(this.value >>> 8 & 0xFF));
/*  563 */       buffer[(this.myOffset + 2)] = ((byte)(this.value >>> 0 & 0xFF));
/*      */     }
/*      */   }
/*      */ 
/*      */   protected static final class DictOffsetItem extends CFFFont.OffsetItem
/*      */   {
/*  526 */     public final int size = 5;
/*      */ 
/*      */     public void increment(int[] currentOffset)
/*      */     {
/*  530 */       super.increment(currentOffset);
/*  531 */       currentOffset[0] += this.size;
/*      */     }
/*      */ 
/*      */     public void emit(byte[] buffer)
/*      */     {
/*  536 */       if (this.size == 5) {
/*  537 */         buffer[this.myOffset] = 29;
/*  538 */         buffer[(this.myOffset + 1)] = ((byte)(this.value >>> 24 & 0xFF));
/*  539 */         buffer[(this.myOffset + 2)] = ((byte)(this.value >>> 16 & 0xFF));
/*  540 */         buffer[(this.myOffset + 3)] = ((byte)(this.value >>> 8 & 0xFF));
/*  541 */         buffer[(this.myOffset + 4)] = ((byte)(this.value >>> 0 & 0xFF));
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected static final class SubrMarkerItem extends CFFFont.Item
/*      */   {
/*      */     private CFFFont.OffsetItem offItem;
/*      */     private CFFFont.IndexBaseItem indexBase;
/*      */ 
/*      */     public SubrMarkerItem(CFFFont.OffsetItem offItem, CFFFont.IndexBaseItem indexBase)
/*      */     {
/*  510 */       this.offItem = offItem;
/*  511 */       this.indexBase = indexBase;
/*      */     }
/*      */ 
/*      */     public void xref()
/*      */     {
/*  516 */       this.offItem.set(this.myOffset - this.indexBase.myOffset);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected static final class IndexMarkerItem extends CFFFont.Item
/*      */   {
/*      */     private CFFFont.OffsetItem offItem;
/*      */     private CFFFont.IndexBaseItem indexBase;
/*      */ 
/*      */     public IndexMarkerItem(CFFFont.OffsetItem offItem, CFFFont.IndexBaseItem indexBase)
/*      */     {
/*  493 */       this.offItem = offItem;
/*  494 */       this.indexBase = indexBase;
/*      */     }
/*      */ 
/*      */     public void xref()
/*      */     {
/*  499 */       this.offItem.set(this.myOffset - this.indexBase.myOffset + 1);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected static final class IndexBaseItem extends CFFFont.Item
/*      */   {
/*      */   }
/*      */ 
/*      */   protected static final class IndexOffsetItem extends CFFFont.OffsetItem
/*      */   {
/*      */     public final int size;
/*      */ 
/*      */     public IndexOffsetItem(int size, int value)
/*      */     {
/*  450 */       this.size = size; this.value = value; } 
/*  451 */     public IndexOffsetItem(int size) { this.size = size; }
/*      */ 
/*      */     public void increment(int[] currentOffset)
/*      */     {
/*  455 */       super.increment(currentOffset);
/*  456 */       currentOffset[0] += this.size;
/*      */     }
/*      */ 
/*      */     public void emit(byte[] buffer) {
/*  460 */       int i = 0;
/*  461 */       switch (this.size) {
/*      */       case 4:
/*  463 */         buffer[(this.myOffset + i)] = ((byte)(this.value >>> 24 & 0xFF));
/*  464 */         i++;
/*      */       case 3:
/*  466 */         buffer[(this.myOffset + i)] = ((byte)(this.value >>> 16 & 0xFF));
/*  467 */         i++;
/*      */       case 2:
/*  469 */         buffer[(this.myOffset + i)] = ((byte)(this.value >>> 8 & 0xFF));
/*  470 */         i++;
/*      */       case 1:
/*  472 */         buffer[(this.myOffset + i)] = ((byte)(this.value >>> 0 & 0xFF));
/*  473 */         i++;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected static final class RangeItem extends CFFFont.Item
/*      */   {
/*      */     public int offset;
/*      */     public int length;
/*      */     private RandomAccessFileOrArray buf;
/*      */ 
/*      */     public RangeItem(RandomAccessFileOrArray buf, int offset, int length)
/*      */     {
/*  418 */       this.offset = offset;
/*  419 */       this.length = length;
/*  420 */       this.buf = buf;
/*      */     }
/*      */ 
/*      */     public void increment(int[] currentOffset) {
/*  424 */       super.increment(currentOffset);
/*  425 */       currentOffset[0] += this.length;
/*      */     }
/*      */ 
/*      */     public void emit(byte[] buffer)
/*      */     {
/*      */       try {
/*  431 */         this.buf.seek(this.offset);
/*  432 */         for (int i = this.myOffset; i < this.myOffset + this.length; i++)
/*  433 */           buffer[i] = this.buf.readByte();
/*      */       }
/*      */       catch (Exception e) {
/*  436 */         throw new ExceptionConverter(e);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected static abstract class OffsetItem extends CFFFont.Item
/*      */   {
/*      */     public int value;
/*      */ 
/*      */     public void set(int offset)
/*      */     {
/*  407 */       this.value = offset;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected static abstract class Item
/*      */   {
/*  391 */     protected int myOffset = -1;
/*      */ 
/*      */     public void increment(int[] currentOffset) {
/*  394 */       this.myOffset = currentOffset[0];
/*      */     }
/*      */ 
/*      */     public void emit(byte[] buffer)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void xref()
/*      */     {
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.CFFFont
 * JD-Core Version:    0.6.2
 */