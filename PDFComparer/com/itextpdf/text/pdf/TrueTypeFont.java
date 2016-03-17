/*      */ package com.itextpdf.text.pdf;
/*      */ 
/*      */ import com.itextpdf.text.Document;
/*      */ import com.itextpdf.text.DocumentException;
/*      */ import com.itextpdf.text.error_messages.MessageLocalization;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Map.Entry;
/*      */ 
/*      */ class TrueTypeFont extends BaseFont
/*      */ {
/*   66 */   static final String[] codePages = { "1252 Latin 1", "1250 Latin 2: Eastern Europe", "1251 Cyrillic", "1253 Greek", "1254 Turkish", "1255 Hebrew", "1256 Arabic", "1257 Windows Baltic", "1258 Vietnamese", null, null, null, null, null, null, null, "874 Thai", "932 JIS/Japan", "936 Chinese: Simplified chars--PRC and Singapore", "949 Korean Wansung", "950 Chinese: Traditional chars--Taiwan and Hong Kong", "1361 Korean Johab", null, null, null, null, null, null, null, "Macintosh Character Set (US Roman)", "OEM Character Set", "Symbol Character Set", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "869 IBM Greek", "866 MS-DOS Russian", "865 MS-DOS Nordic", "864 Arabic", "863 MS-DOS Canadian French", "862 Hebrew", "861 MS-DOS Icelandic", "860 MS-DOS Portuguese", "857 IBM Turkish", "855 IBM Cyrillic; primarily Russian", "852 Latin 2", "775 MS-DOS Baltic", "737 Greek; former 437 G", "708 Arabic; ASMO 708", "850 WE/Latin 1", "437 US" };
/*      */ 
/*  132 */   protected boolean justNames = false;
/*      */   protected HashMap<String, int[]> tables;
/*      */   protected RandomAccessFileOrArray rf;
/*      */   protected String fileName;
/*  146 */   protected boolean cff = false;
/*      */   protected int cffOffset;
/*      */   protected int cffLength;
/*      */   protected int directoryOffset;
/*      */   protected String ttcIndex;
/*  161 */   protected String style = "";
/*      */ 
/*  164 */   protected FontHeader head = new FontHeader();
/*      */ 
/*  167 */   protected HorizontalHeader hhea = new HorizontalHeader();
/*      */ 
/*  170 */   protected WindowsMetrics os_2 = new WindowsMetrics();
/*      */   protected int[] glyphWidthsByIndex;
/*      */   protected int[][] bboxes;
/*      */   protected HashMap<Integer, int[]> cmap10;
/*      */   protected HashMap<Integer, int[]> cmap31;
/*      */   protected HashMap<Integer, int[]> cmapExt;
/*  200 */   protected IntHashtable kerning = new IntHashtable();
/*      */   protected String fontName;
/*      */   protected String[][] fullName;
/*      */   protected String[][] allNameEntries;
/*      */   protected String[][] familyName;
/*      */   protected double italicAngle;
/*  229 */   protected boolean isFixedPitch = false;
/*      */   protected int underlinePosition;
/*      */   protected int underlineThickness;
/*      */ 
/*      */   protected TrueTypeFont()
/*      */   {
/*      */   }
/*      */ 
/*      */   TrueTypeFont(String ttFile, String enc, boolean emb, byte[] ttfAfm, boolean justNames, boolean forceRead)
/*      */     throws DocumentException, IOException
/*      */   {
/*  356 */     this.justNames = justNames;
/*  357 */     String nameBase = getBaseName(ttFile);
/*  358 */     String ttcName = getTTCName(nameBase);
/*  359 */     if (nameBase.length() < ttFile.length()) {
/*  360 */       this.style = ttFile.substring(nameBase.length());
/*      */     }
/*  362 */     this.encoding = enc;
/*  363 */     this.embedded = emb;
/*  364 */     this.fileName = ttcName;
/*  365 */     this.fontType = 1;
/*  366 */     this.ttcIndex = "";
/*  367 */     if (ttcName.length() < nameBase.length())
/*  368 */       this.ttcIndex = nameBase.substring(ttcName.length() + 1);
/*  369 */     if ((this.fileName.toLowerCase().endsWith(".ttf")) || (this.fileName.toLowerCase().endsWith(".otf")) || (this.fileName.toLowerCase().endsWith(".ttc"))) {
/*  370 */       process(ttfAfm, forceRead);
/*  371 */       if ((!justNames) && (this.embedded) && (this.os_2.fsType == 2))
/*  372 */         throw new DocumentException(MessageLocalization.getComposedMessage("1.cannot.be.embedded.due.to.licensing.restrictions", new Object[] { this.fileName + this.style }));
/*      */     }
/*      */     else {
/*  375 */       throw new DocumentException(MessageLocalization.getComposedMessage("1.is.not.a.ttf.otf.or.ttc.font.file", new Object[] { this.fileName + this.style }));
/*  376 */     }if (!this.encoding.startsWith("#"))
/*  377 */       PdfEncodings.convertToBytes(" ", enc);
/*  378 */     createEncoding();
/*      */   }
/*      */ 
/*      */   protected static String getTTCName(String name)
/*      */   {
/*  388 */     int idx = name.toLowerCase().indexOf(".ttc,");
/*  389 */     if (idx < 0) {
/*  390 */       return name;
/*      */     }
/*  392 */     return name.substring(0, idx + 4);
/*      */   }
/*      */ 
/*      */   void fillTables()
/*      */     throws DocumentException, IOException
/*      */   {
/*  403 */     int[] table_location = (int[])this.tables.get("head");
/*  404 */     if (table_location == null)
/*  405 */       throw new DocumentException(MessageLocalization.getComposedMessage("table.1.does.not.exist.in.2", new Object[] { "head", this.fileName + this.style }));
/*  406 */     this.rf.seek(table_location[0] + 16);
/*  407 */     this.head.flags = this.rf.readUnsignedShort();
/*  408 */     this.head.unitsPerEm = this.rf.readUnsignedShort();
/*  409 */     this.rf.skipBytes(16);
/*  410 */     this.head.xMin = this.rf.readShort();
/*  411 */     this.head.yMin = this.rf.readShort();
/*  412 */     this.head.xMax = this.rf.readShort();
/*  413 */     this.head.yMax = this.rf.readShort();
/*  414 */     this.head.macStyle = this.rf.readUnsignedShort();
/*      */ 
/*  416 */     table_location = (int[])this.tables.get("hhea");
/*  417 */     if (table_location == null)
/*  418 */       throw new DocumentException(MessageLocalization.getComposedMessage("table.1.does.not.exist.in.2", new Object[] { "hhea", this.fileName + this.style }));
/*  419 */     this.rf.seek(table_location[0] + 4);
/*  420 */     this.hhea.Ascender = this.rf.readShort();
/*  421 */     this.hhea.Descender = this.rf.readShort();
/*  422 */     this.hhea.LineGap = this.rf.readShort();
/*  423 */     this.hhea.advanceWidthMax = this.rf.readUnsignedShort();
/*  424 */     this.hhea.minLeftSideBearing = this.rf.readShort();
/*  425 */     this.hhea.minRightSideBearing = this.rf.readShort();
/*  426 */     this.hhea.xMaxExtent = this.rf.readShort();
/*  427 */     this.hhea.caretSlopeRise = this.rf.readShort();
/*  428 */     this.hhea.caretSlopeRun = this.rf.readShort();
/*  429 */     this.rf.skipBytes(12);
/*  430 */     this.hhea.numberOfHMetrics = this.rf.readUnsignedShort();
/*      */ 
/*  432 */     table_location = (int[])this.tables.get("OS/2");
/*  433 */     if (table_location == null)
/*  434 */       throw new DocumentException(MessageLocalization.getComposedMessage("table.1.does.not.exist.in.2", new Object[] { "OS/2", this.fileName + this.style }));
/*  435 */     this.rf.seek(table_location[0]);
/*  436 */     int version = this.rf.readUnsignedShort();
/*  437 */     this.os_2.xAvgCharWidth = this.rf.readShort();
/*  438 */     this.os_2.usWeightClass = this.rf.readUnsignedShort();
/*  439 */     this.os_2.usWidthClass = this.rf.readUnsignedShort();
/*  440 */     this.os_2.fsType = this.rf.readShort();
/*  441 */     this.os_2.ySubscriptXSize = this.rf.readShort();
/*  442 */     this.os_2.ySubscriptYSize = this.rf.readShort();
/*  443 */     this.os_2.ySubscriptXOffset = this.rf.readShort();
/*  444 */     this.os_2.ySubscriptYOffset = this.rf.readShort();
/*  445 */     this.os_2.ySuperscriptXSize = this.rf.readShort();
/*  446 */     this.os_2.ySuperscriptYSize = this.rf.readShort();
/*  447 */     this.os_2.ySuperscriptXOffset = this.rf.readShort();
/*  448 */     this.os_2.ySuperscriptYOffset = this.rf.readShort();
/*  449 */     this.os_2.yStrikeoutSize = this.rf.readShort();
/*  450 */     this.os_2.yStrikeoutPosition = this.rf.readShort();
/*  451 */     this.os_2.sFamilyClass = this.rf.readShort();
/*  452 */     this.rf.readFully(this.os_2.panose);
/*  453 */     this.rf.skipBytes(16);
/*  454 */     this.rf.readFully(this.os_2.achVendID);
/*  455 */     this.os_2.fsSelection = this.rf.readUnsignedShort();
/*  456 */     this.os_2.usFirstCharIndex = this.rf.readUnsignedShort();
/*  457 */     this.os_2.usLastCharIndex = this.rf.readUnsignedShort();
/*  458 */     this.os_2.sTypoAscender = this.rf.readShort();
/*  459 */     this.os_2.sTypoDescender = this.rf.readShort();
/*  460 */     if (this.os_2.sTypoDescender > 0)
/*  461 */       this.os_2.sTypoDescender = ((short)-this.os_2.sTypoDescender);
/*  462 */     this.os_2.sTypoLineGap = this.rf.readShort();
/*  463 */     this.os_2.usWinAscent = this.rf.readUnsignedShort();
/*  464 */     this.os_2.usWinDescent = this.rf.readUnsignedShort();
/*  465 */     this.os_2.ulCodePageRange1 = 0;
/*  466 */     this.os_2.ulCodePageRange2 = 0;
/*  467 */     if (version > 0) {
/*  468 */       this.os_2.ulCodePageRange1 = this.rf.readInt();
/*  469 */       this.os_2.ulCodePageRange2 = this.rf.readInt();
/*      */     }
/*  471 */     if (version > 1) {
/*  472 */       this.rf.skipBytes(2);
/*  473 */       this.os_2.sCapHeight = this.rf.readShort();
/*      */     }
/*      */     else {
/*  476 */       this.os_2.sCapHeight = ((int)(0.7D * this.head.unitsPerEm));
/*      */     }
/*  478 */     table_location = (int[])this.tables.get("post");
/*  479 */     if (table_location == null) {
/*  480 */       this.italicAngle = (-Math.atan2(this.hhea.caretSlopeRun, this.hhea.caretSlopeRise) * 180.0D / 3.141592653589793D);
/*  481 */       return;
/*      */     }
/*  483 */     this.rf.seek(table_location[0] + 4);
/*  484 */     short mantissa = this.rf.readShort();
/*  485 */     int fraction = this.rf.readUnsignedShort();
/*  486 */     this.italicAngle = (mantissa + fraction / 16384.0D);
/*  487 */     this.underlinePosition = this.rf.readShort();
/*  488 */     this.underlineThickness = this.rf.readShort();
/*  489 */     this.isFixedPitch = (this.rf.readInt() != 0);
/*      */   }
/*      */ 
/*      */   String getBaseFont()
/*      */     throws DocumentException, IOException
/*      */   {
/*  500 */     int[] table_location = (int[])this.tables.get("name");
/*  501 */     if (table_location == null)
/*  502 */       throw new DocumentException(MessageLocalization.getComposedMessage("table.1.does.not.exist.in.2", new Object[] { "name", this.fileName + this.style }));
/*  503 */     this.rf.seek(table_location[0] + 2);
/*  504 */     int numRecords = this.rf.readUnsignedShort();
/*  505 */     int startOfStorage = this.rf.readUnsignedShort();
/*  506 */     for (int k = 0; k < numRecords; k++) {
/*  507 */       int platformID = this.rf.readUnsignedShort();
/*  508 */       int platformEncodingID = this.rf.readUnsignedShort();
/*  509 */       int languageID = this.rf.readUnsignedShort();
/*  510 */       int nameID = this.rf.readUnsignedShort();
/*  511 */       int length = this.rf.readUnsignedShort();
/*  512 */       int offset = this.rf.readUnsignedShort();
/*  513 */       if (nameID == 6) {
/*  514 */         this.rf.seek(table_location[0] + startOfStorage + offset);
/*  515 */         if ((platformID == 0) || (platformID == 3)) {
/*  516 */           return readUnicodeString(length);
/*      */         }
/*  518 */         return readStandardString(length);
/*      */       }
/*      */     }
/*  521 */     File file = new File(this.fileName);
/*  522 */     return file.getName().replace(' ', '-');
/*      */   }
/*      */ 
/*      */   String[][] getNames(int id)
/*      */     throws DocumentException, IOException
/*      */   {
/*  532 */     int[] table_location = (int[])this.tables.get("name");
/*  533 */     if (table_location == null)
/*  534 */       throw new DocumentException(MessageLocalization.getComposedMessage("table.1.does.not.exist.in.2", new Object[] { "name", this.fileName + this.style }));
/*  535 */     this.rf.seek(table_location[0] + 2);
/*  536 */     int numRecords = this.rf.readUnsignedShort();
/*  537 */     int startOfStorage = this.rf.readUnsignedShort();
/*  538 */     ArrayList names = new ArrayList();
/*  539 */     for (int k = 0; k < numRecords; k++) {
/*  540 */       int platformID = this.rf.readUnsignedShort();
/*  541 */       int platformEncodingID = this.rf.readUnsignedShort();
/*  542 */       int languageID = this.rf.readUnsignedShort();
/*  543 */       int nameID = this.rf.readUnsignedShort();
/*  544 */       int length = this.rf.readUnsignedShort();
/*  545 */       int offset = this.rf.readUnsignedShort();
/*  546 */       if (nameID == id) {
/*  547 */         int pos = (int)this.rf.getFilePointer();
/*  548 */         this.rf.seek(table_location[0] + startOfStorage + offset);
/*      */         String name;
/*      */         String name;
/*  550 */         if ((platformID == 0) || (platformID == 3) || ((platformID == 2) && (platformEncodingID == 1))) {
/*  551 */           name = readUnicodeString(length);
/*      */         }
/*      */         else {
/*  554 */           name = readStandardString(length);
/*      */         }
/*  556 */         names.add(new String[] { String.valueOf(platformID), String.valueOf(platformEncodingID), String.valueOf(languageID), name });
/*      */ 
/*  558 */         this.rf.seek(pos);
/*      */       }
/*      */     }
/*  561 */     String[][] thisName = new String[names.size()][];
/*  562 */     for (int k = 0; k < names.size(); k++)
/*  563 */       thisName[k] = ((String[])names.get(k));
/*  564 */     return thisName;
/*      */   }
/*      */ 
/*      */   String[][] getAllNames()
/*      */     throws DocumentException, IOException
/*      */   {
/*  573 */     int[] table_location = (int[])this.tables.get("name");
/*  574 */     if (table_location == null)
/*  575 */       throw new DocumentException(MessageLocalization.getComposedMessage("table.1.does.not.exist.in.2", new Object[] { "name", this.fileName + this.style }));
/*  576 */     this.rf.seek(table_location[0] + 2);
/*  577 */     int numRecords = this.rf.readUnsignedShort();
/*  578 */     int startOfStorage = this.rf.readUnsignedShort();
/*  579 */     ArrayList names = new ArrayList();
/*  580 */     for (int k = 0; k < numRecords; k++) {
/*  581 */       int platformID = this.rf.readUnsignedShort();
/*  582 */       int platformEncodingID = this.rf.readUnsignedShort();
/*  583 */       int languageID = this.rf.readUnsignedShort();
/*  584 */       int nameID = this.rf.readUnsignedShort();
/*  585 */       int length = this.rf.readUnsignedShort();
/*  586 */       int offset = this.rf.readUnsignedShort();
/*  587 */       int pos = (int)this.rf.getFilePointer();
/*  588 */       this.rf.seek(table_location[0] + startOfStorage + offset);
/*      */       String name;
/*      */       String name;
/*  590 */       if ((platformID == 0) || (platformID == 3) || ((platformID == 2) && (platformEncodingID == 1))) {
/*  591 */         name = readUnicodeString(length);
/*      */       }
/*      */       else {
/*  594 */         name = readStandardString(length);
/*      */       }
/*  596 */       names.add(new String[] { String.valueOf(nameID), String.valueOf(platformID), String.valueOf(platformEncodingID), String.valueOf(languageID), name });
/*      */ 
/*  598 */       this.rf.seek(pos);
/*      */     }
/*  600 */     String[][] thisName = new String[names.size()][];
/*  601 */     for (int k = 0; k < names.size(); k++)
/*  602 */       thisName[k] = ((String[])names.get(k));
/*  603 */     return thisName;
/*      */   }
/*      */ 
/*      */   void checkCff()
/*      */   {
/*  608 */     int[] table_location = (int[])this.tables.get("CFF ");
/*  609 */     if (table_location != null) {
/*  610 */       this.cff = true;
/*  611 */       this.cffOffset = table_location[0];
/*  612 */       this.cffLength = table_location[1];
/*      */     }
/*      */   }
/*      */ 
/*      */   void process(byte[] ttfAfm, boolean preload)
/*      */     throws DocumentException, IOException
/*      */   {
/*  623 */     this.tables = new HashMap();
/*      */ 
/*  625 */     if (ttfAfm == null)
/*  626 */       this.rf = new RandomAccessFileOrArray(this.fileName, preload, Document.plainRandomAccess);
/*      */     else
/*  628 */       this.rf = new RandomAccessFileOrArray(ttfAfm);
/*      */     try
/*      */     {
/*  631 */       if (this.ttcIndex.length() > 0) {
/*  632 */         int dirIdx = Integer.parseInt(this.ttcIndex);
/*  633 */         if (dirIdx < 0)
/*  634 */           throw new DocumentException(MessageLocalization.getComposedMessage("the.font.index.for.1.must.be.positive", new Object[] { this.fileName }));
/*  635 */         String mainTag = readStandardString(4);
/*  636 */         if (!mainTag.equals("ttcf"))
/*  637 */           throw new DocumentException(MessageLocalization.getComposedMessage("1.is.not.a.valid.ttc.file", new Object[] { this.fileName }));
/*  638 */         this.rf.skipBytes(4);
/*  639 */         int dirCount = this.rf.readInt();
/*  640 */         if (dirIdx >= dirCount)
/*  641 */           throw new DocumentException(MessageLocalization.getComposedMessage("the.font.index.for.1.must.be.between.0.and.2.it.was.3", new Object[] { this.fileName, String.valueOf(dirCount - 1), String.valueOf(dirIdx) }));
/*  642 */         this.rf.skipBytes(dirIdx * 4);
/*  643 */         this.directoryOffset = this.rf.readInt();
/*      */       }
/*  645 */       this.rf.seek(this.directoryOffset);
/*  646 */       int ttId = this.rf.readInt();
/*  647 */       if ((ttId != 65536) && (ttId != 1330926671))
/*  648 */         throw new DocumentException(MessageLocalization.getComposedMessage("1.is.not.a.valid.ttf.or.otf.file", new Object[] { this.fileName }));
/*  649 */       int num_tables = this.rf.readUnsignedShort();
/*  650 */       this.rf.skipBytes(6);
/*  651 */       for (int k = 0; k < num_tables; k++) {
/*  652 */         String tag = readStandardString(4);
/*  653 */         this.rf.skipBytes(4);
/*  654 */         int[] table_location = new int[2];
/*  655 */         table_location[0] = this.rf.readInt();
/*  656 */         table_location[1] = this.rf.readInt();
/*  657 */         this.tables.put(tag, table_location);
/*      */       }
/*  659 */       checkCff();
/*  660 */       this.fontName = getBaseFont();
/*  661 */       this.fullName = getNames(4);
/*  662 */       this.familyName = getNames(1);
/*  663 */       this.allNameEntries = getAllNames();
/*  664 */       if (!this.justNames) {
/*  665 */         fillTables();
/*  666 */         readGlyphWidths();
/*  667 */         readCMaps();
/*  668 */         readKerning();
/*  669 */         readBbox();
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*  674 */       if (!this.embedded) {
/*  675 */         this.rf.close();
/*  676 */         this.rf = null;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected String readStandardString(int length)
/*      */     throws IOException
/*      */   {
/*  688 */     return this.rf.readString(length, "Cp1252");
/*      */   }
/*      */ 
/*      */   protected String readUnicodeString(int length)
/*      */     throws IOException
/*      */   {
/*  699 */     StringBuffer buf = new StringBuffer();
/*  700 */     length /= 2;
/*  701 */     for (int k = 0; k < length; k++) {
/*  702 */       buf.append(this.rf.readChar());
/*      */     }
/*  704 */     return buf.toString();
/*      */   }
/*      */ 
/*      */   protected void readGlyphWidths()
/*      */     throws DocumentException, IOException
/*      */   {
/*  714 */     int[] table_location = (int[])this.tables.get("hmtx");
/*  715 */     if (table_location == null)
/*  716 */       throw new DocumentException(MessageLocalization.getComposedMessage("table.1.does.not.exist.in.2", new Object[] { "hmtx", this.fileName + this.style }));
/*  717 */     this.rf.seek(table_location[0]);
/*  718 */     this.glyphWidthsByIndex = new int[this.hhea.numberOfHMetrics];
/*      */     int leftSideBearing;
/*  719 */     for (int k = 0; k < this.hhea.numberOfHMetrics; k++) {
/*  720 */       this.glyphWidthsByIndex[k] = (this.rf.readUnsignedShort() * 1000 / this.head.unitsPerEm);
/*      */ 
/*  722 */       leftSideBearing = this.rf.readShort() * 1000 / this.head.unitsPerEm;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected int getGlyphWidth(int glyph)
/*      */   {
/*  731 */     if (glyph >= this.glyphWidthsByIndex.length)
/*  732 */       glyph = this.glyphWidthsByIndex.length - 1;
/*  733 */     return this.glyphWidthsByIndex[glyph];
/*      */   }
/*      */ 
/*      */   private void readBbox() throws DocumentException, IOException
/*      */   {
/*  738 */     int[] tableLocation = (int[])this.tables.get("head");
/*  739 */     if (tableLocation == null)
/*  740 */       throw new DocumentException(MessageLocalization.getComposedMessage("table.1.does.not.exist.in.2", new Object[] { "head", this.fileName + this.style }));
/*  741 */     this.rf.seek(tableLocation[0] + 51);
/*  742 */     boolean locaShortTable = this.rf.readUnsignedShort() == 0;
/*  743 */     tableLocation = (int[])this.tables.get("loca");
/*  744 */     if (tableLocation == null)
/*  745 */       return;
/*  746 */     this.rf.seek(tableLocation[0]);
/*      */     int[] locaTable;
/*  748 */     if (locaShortTable) {
/*  749 */       int entries = tableLocation[1] / 2;
/*  750 */       int[] locaTable = new int[entries];
/*  751 */       for (int k = 0; k < entries; k++)
/*  752 */         locaTable[k] = (this.rf.readUnsignedShort() * 2);
/*      */     }
/*      */     else {
/*  755 */       int entries = tableLocation[1] / 4;
/*  756 */       locaTable = new int[entries];
/*  757 */       for (int k = 0; k < entries; k++)
/*  758 */         locaTable[k] = this.rf.readInt();
/*      */     }
/*  760 */     tableLocation = (int[])this.tables.get("glyf");
/*  761 */     if (tableLocation == null)
/*  762 */       throw new DocumentException(MessageLocalization.getComposedMessage("table.1.does.not.exist.in.2", new Object[] { "glyf", this.fileName + this.style }));
/*  763 */     int tableGlyphOffset = tableLocation[0];
/*  764 */     this.bboxes = new int[locaTable.length - 1][];
/*  765 */     for (int glyph = 0; glyph < locaTable.length - 1; glyph++) {
/*  766 */       int start = locaTable[glyph];
/*  767 */       if (start != locaTable[(glyph + 1)]) {
/*  768 */         this.rf.seek(tableGlyphOffset + start + 2);
/*  769 */         this.bboxes[glyph] = { this.rf.readShort() * 1000 / this.head.unitsPerEm, this.rf.readShort() * 1000 / this.head.unitsPerEm, this.rf.readShort() * 1000 / this.head.unitsPerEm, this.rf.readShort() * 1000 / this.head.unitsPerEm };
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   void readCMaps()
/*      */     throws DocumentException, IOException
/*      */   {
/*  785 */     int[] table_location = (int[])this.tables.get("cmap");
/*  786 */     if (table_location == null)
/*  787 */       throw new DocumentException(MessageLocalization.getComposedMessage("table.1.does.not.exist.in.2", new Object[] { "cmap", this.fileName + this.style }));
/*  788 */     this.rf.seek(table_location[0]);
/*  789 */     this.rf.skipBytes(2);
/*  790 */     int num_tables = this.rf.readUnsignedShort();
/*  791 */     this.fontSpecific = false;
/*  792 */     int map10 = 0;
/*  793 */     int map31 = 0;
/*  794 */     int map30 = 0;
/*  795 */     int mapExt = 0;
/*  796 */     for (int k = 0; k < num_tables; k++) {
/*  797 */       int platId = this.rf.readUnsignedShort();
/*  798 */       int platSpecId = this.rf.readUnsignedShort();
/*  799 */       int offset = this.rf.readInt();
/*  800 */       if ((platId == 3) && (platSpecId == 0)) {
/*  801 */         this.fontSpecific = true;
/*  802 */         map30 = offset;
/*      */       }
/*  804 */       else if ((platId == 3) && (platSpecId == 1)) {
/*  805 */         map31 = offset;
/*      */       }
/*  807 */       else if ((platId == 3) && (platSpecId == 10)) {
/*  808 */         mapExt = offset;
/*      */       }
/*  810 */       if ((platId == 1) && (platSpecId == 0)) {
/*  811 */         map10 = offset;
/*      */       }
/*      */     }
/*  814 */     if (map10 > 0) {
/*  815 */       this.rf.seek(table_location[0] + map10);
/*  816 */       int format = this.rf.readUnsignedShort();
/*  817 */       switch (format) {
/*      */       case 0:
/*  819 */         this.cmap10 = readFormat0();
/*  820 */         break;
/*      */       case 4:
/*  822 */         this.cmap10 = readFormat4();
/*  823 */         break;
/*      */       case 6:
/*  825 */         this.cmap10 = readFormat6();
/*      */       }
/*      */     }
/*      */ 
/*  829 */     if (map31 > 0) {
/*  830 */       this.rf.seek(table_location[0] + map31);
/*  831 */       int format = this.rf.readUnsignedShort();
/*  832 */       if (format == 4) {
/*  833 */         this.cmap31 = readFormat4();
/*      */       }
/*      */     }
/*  836 */     if (map30 > 0) {
/*  837 */       this.rf.seek(table_location[0] + map30);
/*  838 */       int format = this.rf.readUnsignedShort();
/*  839 */       if (format == 4) {
/*  840 */         this.cmap10 = readFormat4();
/*      */       }
/*      */     }
/*  843 */     if (mapExt > 0) {
/*  844 */       this.rf.seek(table_location[0] + mapExt);
/*  845 */       int format = this.rf.readUnsignedShort();
/*  846 */       switch (format) {
/*      */       case 0:
/*  848 */         this.cmapExt = readFormat0();
/*  849 */         break;
/*      */       case 4:
/*  851 */         this.cmapExt = readFormat4();
/*  852 */         break;
/*      */       case 6:
/*  854 */         this.cmapExt = readFormat6();
/*  855 */         break;
/*      */       case 12:
/*  857 */         this.cmapExt = readFormat12();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   HashMap<Integer, int[]> readFormat12() throws IOException
/*      */   {
/*  864 */     HashMap h = new HashMap();
/*  865 */     this.rf.skipBytes(2);
/*  866 */     int table_lenght = this.rf.readInt();
/*  867 */     this.rf.skipBytes(4);
/*  868 */     int nGroups = this.rf.readInt();
/*  869 */     for (int k = 0; k < nGroups; k++) {
/*  870 */       int startCharCode = this.rf.readInt();
/*  871 */       int endCharCode = this.rf.readInt();
/*  872 */       int startGlyphID = this.rf.readInt();
/*  873 */       for (int i = startCharCode; i <= endCharCode; i++) {
/*  874 */         int[] r = new int[2];
/*  875 */         r[0] = startGlyphID;
/*  876 */         r[1] = getGlyphWidth(r[0]);
/*  877 */         h.put(Integer.valueOf(i), r);
/*  878 */         startGlyphID++;
/*      */       }
/*      */     }
/*  881 */     return h;
/*      */   }
/*      */ 
/*      */   HashMap<Integer, int[]> readFormat0()
/*      */     throws IOException
/*      */   {
/*  890 */     HashMap h = new HashMap();
/*  891 */     this.rf.skipBytes(4);
/*  892 */     for (int k = 0; k < 256; k++) {
/*  893 */       int[] r = new int[2];
/*  894 */       r[0] = this.rf.readUnsignedByte();
/*  895 */       r[1] = getGlyphWidth(r[0]);
/*  896 */       h.put(Integer.valueOf(k), r);
/*      */     }
/*  898 */     return h;
/*      */   }
/*      */ 
/*      */   HashMap<Integer, int[]> readFormat4()
/*      */     throws IOException
/*      */   {
/*  907 */     HashMap h = new HashMap();
/*  908 */     int table_lenght = this.rf.readUnsignedShort();
/*  909 */     this.rf.skipBytes(2);
/*  910 */     int segCount = this.rf.readUnsignedShort() / 2;
/*  911 */     this.rf.skipBytes(6);
/*  912 */     int[] endCount = new int[segCount];
/*  913 */     for (int k = 0; k < segCount; k++) {
/*  914 */       endCount[k] = this.rf.readUnsignedShort();
/*      */     }
/*  916 */     this.rf.skipBytes(2);
/*  917 */     int[] startCount = new int[segCount];
/*  918 */     for (int k = 0; k < segCount; k++) {
/*  919 */       startCount[k] = this.rf.readUnsignedShort();
/*      */     }
/*  921 */     int[] idDelta = new int[segCount];
/*  922 */     for (int k = 0; k < segCount; k++) {
/*  923 */       idDelta[k] = this.rf.readUnsignedShort();
/*      */     }
/*  925 */     int[] idRO = new int[segCount];
/*  926 */     for (int k = 0; k < segCount; k++) {
/*  927 */       idRO[k] = this.rf.readUnsignedShort();
/*      */     }
/*  929 */     int[] glyphId = new int[table_lenght / 2 - 8 - segCount * 4];
/*  930 */     for (int k = 0; k < glyphId.length; k++) {
/*  931 */       glyphId[k] = this.rf.readUnsignedShort();
/*      */     }
/*  933 */     for (int k = 0; k < segCount; k++)
/*      */     {
/*  935 */       for (int j = startCount[k]; (j <= endCount[k]) && (j != 65535); j++)
/*      */       {
/*      */         int glyph;
/*      */         int glyph;
/*  936 */         if (idRO[k] == 0) {
/*  937 */           glyph = j + idDelta[k] & 0xFFFF;
/*      */         }
/*      */         else {
/*  940 */           int idx = k + idRO[k] / 2 - segCount + j - startCount[k];
/*  941 */           if (idx >= glyphId.length)
/*      */             continue;
/*  943 */           glyph = glyphId[idx] + idDelta[k] & 0xFFFF;
/*      */         }
/*  945 */         int[] r = new int[2];
/*  946 */         r[0] = glyph;
/*  947 */         r[1] = getGlyphWidth(r[0]);
/*  948 */         h.put(Integer.valueOf(this.fontSpecific ? j : (j & 0xFF00) == 61440 ? j & 0xFF : j), r);
/*      */       }
/*      */     }
/*  951 */     return h;
/*      */   }
/*      */ 
/*      */   HashMap<Integer, int[]> readFormat6()
/*      */     throws IOException
/*      */   {
/*  961 */     HashMap h = new HashMap();
/*  962 */     this.rf.skipBytes(4);
/*  963 */     int start_code = this.rf.readUnsignedShort();
/*  964 */     int code_count = this.rf.readUnsignedShort();
/*  965 */     for (int k = 0; k < code_count; k++) {
/*  966 */       int[] r = new int[2];
/*  967 */       r[0] = this.rf.readUnsignedShort();
/*  968 */       r[1] = getGlyphWidth(r[0]);
/*  969 */       h.put(Integer.valueOf(k + start_code), r);
/*      */     }
/*  971 */     return h;
/*      */   }
/*      */ 
/*      */   void readKerning()
/*      */     throws IOException
/*      */   {
/*  979 */     int[] table_location = (int[])this.tables.get("kern");
/*  980 */     if (table_location == null)
/*  981 */       return;
/*  982 */     this.rf.seek(table_location[0] + 2);
/*  983 */     int nTables = this.rf.readUnsignedShort();
/*  984 */     int checkpoint = table_location[0] + 4;
/*  985 */     int length = 0;
/*  986 */     for (int k = 0; k < nTables; k++) {
/*  987 */       checkpoint += length;
/*  988 */       this.rf.seek(checkpoint);
/*  989 */       this.rf.skipBytes(2);
/*  990 */       length = this.rf.readUnsignedShort();
/*  991 */       int coverage = this.rf.readUnsignedShort();
/*  992 */       if ((coverage & 0xFFF7) == 1) {
/*  993 */         int nPairs = this.rf.readUnsignedShort();
/*  994 */         this.rf.skipBytes(6);
/*  995 */         for (int j = 0; j < nPairs; j++) {
/*  996 */           int pair = this.rf.readInt();
/*  997 */           int value = this.rf.readShort() * 1000 / this.head.unitsPerEm;
/*  998 */           this.kerning.put(pair, value);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getKerning(int char1, int char2)
/*      */   {
/* 1011 */     int[] metrics = getMetricsTT(char1);
/* 1012 */     if (metrics == null)
/* 1013 */       return 0;
/* 1014 */     int c1 = metrics[0];
/* 1015 */     metrics = getMetricsTT(char2);
/* 1016 */     if (metrics == null)
/* 1017 */       return 0;
/* 1018 */     int c2 = metrics[0];
/* 1019 */     return this.kerning.get((c1 << 16) + c2);
/*      */   }
/*      */ 
/*      */   int getRawWidth(int c, String name)
/*      */   {
/* 1030 */     int[] metric = getMetricsTT(c);
/* 1031 */     if (metric == null)
/* 1032 */       return 0;
/* 1033 */     return metric[1];
/*      */   }
/*      */ 
/*      */   protected PdfDictionary getFontDescriptor(PdfIndirectReference fontStream, String subsetPrefix, PdfIndirectReference cidset)
/*      */   {
/* 1042 */     PdfDictionary dic = new PdfDictionary(PdfName.FONTDESCRIPTOR);
/* 1043 */     dic.put(PdfName.ASCENT, new PdfNumber(this.os_2.sTypoAscender * 1000 / this.head.unitsPerEm));
/* 1044 */     dic.put(PdfName.CAPHEIGHT, new PdfNumber(this.os_2.sCapHeight * 1000 / this.head.unitsPerEm));
/* 1045 */     dic.put(PdfName.DESCENT, new PdfNumber(this.os_2.sTypoDescender * 1000 / this.head.unitsPerEm));
/* 1046 */     dic.put(PdfName.FONTBBOX, new PdfRectangle(this.head.xMin * 1000 / this.head.unitsPerEm, this.head.yMin * 1000 / this.head.unitsPerEm, this.head.xMax * 1000 / this.head.unitsPerEm, this.head.yMax * 1000 / this.head.unitsPerEm));
/*      */ 
/* 1051 */     if (cidset != null)
/* 1052 */       dic.put(PdfName.CIDSET, cidset);
/* 1053 */     if (this.cff) {
/* 1054 */       if (this.encoding.startsWith("Identity-"))
/* 1055 */         dic.put(PdfName.FONTNAME, new PdfName(subsetPrefix + this.fontName + "-" + this.encoding));
/*      */       else
/* 1057 */         dic.put(PdfName.FONTNAME, new PdfName(subsetPrefix + this.fontName + this.style));
/*      */     }
/*      */     else
/* 1060 */       dic.put(PdfName.FONTNAME, new PdfName(subsetPrefix + this.fontName + this.style));
/* 1061 */     dic.put(PdfName.ITALICANGLE, new PdfNumber(this.italicAngle));
/* 1062 */     dic.put(PdfName.STEMV, new PdfNumber(80));
/* 1063 */     if (fontStream != null) {
/* 1064 */       if (this.cff)
/* 1065 */         dic.put(PdfName.FONTFILE3, fontStream);
/*      */       else
/* 1067 */         dic.put(PdfName.FONTFILE2, fontStream);
/*      */     }
/* 1069 */     int flags = 0;
/* 1070 */     if (this.isFixedPitch)
/* 1071 */       flags |= 1;
/* 1072 */     flags |= (this.fontSpecific ? 4 : 32);
/* 1073 */     if ((this.head.macStyle & 0x2) != 0)
/* 1074 */       flags |= 64;
/* 1075 */     if ((this.head.macStyle & 0x1) != 0)
/* 1076 */       flags |= 262144;
/* 1077 */     dic.put(PdfName.FLAGS, new PdfNumber(flags));
/*      */ 
/* 1079 */     return dic;
/*      */   }
/*      */ 
/*      */   protected PdfDictionary getFontBaseType(PdfIndirectReference fontDescriptor, String subsetPrefix, int firstChar, int lastChar, byte[] shortTag)
/*      */   {
/* 1091 */     PdfDictionary dic = new PdfDictionary(PdfName.FONT);
/* 1092 */     if (this.cff) {
/* 1093 */       dic.put(PdfName.SUBTYPE, PdfName.TYPE1);
/* 1094 */       dic.put(PdfName.BASEFONT, new PdfName(this.fontName + this.style));
/*      */     }
/*      */     else {
/* 1097 */       dic.put(PdfName.SUBTYPE, PdfName.TRUETYPE);
/* 1098 */       dic.put(PdfName.BASEFONT, new PdfName(subsetPrefix + this.fontName + this.style));
/*      */     }
/* 1100 */     dic.put(PdfName.BASEFONT, new PdfName(subsetPrefix + this.fontName + this.style));
/* 1101 */     if (!this.fontSpecific) {
/* 1102 */       for (int k = firstChar; k <= lastChar; k++) {
/* 1103 */         if (!this.differences[k].equals(".notdef")) {
/* 1104 */           firstChar = k;
/* 1105 */           break;
/*      */         }
/*      */       }
/* 1108 */       if ((this.encoding.equals("Cp1252")) || (this.encoding.equals("MacRoman"))) {
/* 1109 */         dic.put(PdfName.ENCODING, this.encoding.equals("Cp1252") ? PdfName.WIN_ANSI_ENCODING : PdfName.MAC_ROMAN_ENCODING);
/*      */       } else {
/* 1111 */         PdfDictionary enc = new PdfDictionary(PdfName.ENCODING);
/* 1112 */         PdfArray dif = new PdfArray();
/* 1113 */         boolean gap = true;
/* 1114 */         for (int k = firstChar; k <= lastChar; k++)
/* 1115 */           if (shortTag[k] != 0) {
/* 1116 */             if (gap) {
/* 1117 */               dif.add(new PdfNumber(k));
/* 1118 */               gap = false;
/*      */             }
/* 1120 */             dif.add(new PdfName(this.differences[k]));
/*      */           }
/*      */           else {
/* 1123 */             gap = true;
/*      */           }
/* 1125 */         enc.put(PdfName.DIFFERENCES, dif);
/* 1126 */         dic.put(PdfName.ENCODING, enc);
/*      */       }
/*      */     }
/* 1129 */     dic.put(PdfName.FIRSTCHAR, new PdfNumber(firstChar));
/* 1130 */     dic.put(PdfName.LASTCHAR, new PdfNumber(lastChar));
/* 1131 */     PdfArray wd = new PdfArray();
/* 1132 */     for (int k = firstChar; k <= lastChar; k++) {
/* 1133 */       if (shortTag[k] == 0)
/* 1134 */         wd.add(new PdfNumber(0));
/*      */       else
/* 1136 */         wd.add(new PdfNumber(this.widths[k]));
/*      */     }
/* 1138 */     dic.put(PdfName.WIDTHS, wd);
/* 1139 */     if (fontDescriptor != null)
/* 1140 */       dic.put(PdfName.FONTDESCRIPTOR, fontDescriptor);
/* 1141 */     return dic;
/*      */   }
/*      */ 
/*      */   protected byte[] getFullFont() throws IOException {
/* 1145 */     RandomAccessFileOrArray rf2 = null;
/*      */     try {
/* 1147 */       rf2 = new RandomAccessFileOrArray(this.rf);
/* 1148 */       rf2.reOpen();
/* 1149 */       byte[] b = new byte[(int)rf2.length()];
/* 1150 */       rf2.readFully(b);
/* 1151 */       return b;
/*      */     } finally {
/*      */       try {
/* 1154 */         if (rf2 != null) rf2.close();  } catch (Exception e) {
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/* 1159 */   protected synchronized byte[] getSubSet(HashSet glyphs, boolean subsetp) throws IOException, DocumentException { TrueTypeFontSubSet sb = new TrueTypeFontSubSet(this.fileName, new RandomAccessFileOrArray(this.rf), glyphs, this.directoryOffset, true, !subsetp);
/* 1160 */     return sb.process(); }
/*      */ 
/*      */   protected static int[] compactRanges(ArrayList<int[]> ranges)
/*      */   {
/* 1164 */     ArrayList simp = new ArrayList();
/* 1165 */     for (int k = 0; k < ranges.size(); k++) {
/* 1166 */       int[] r = (int[])ranges.get(k);
/* 1167 */       for (int j = 0; j < r.length; j += 2) {
/* 1168 */         simp.add(new int[] { Math.max(0, Math.min(r[j], r[(j + 1)])), Math.min(65535, Math.max(r[j], r[(j + 1)])) });
/*      */       }
/*      */     }
/* 1171 */     for (int k1 = 0; k1 < simp.size() - 1; k1++) {
/* 1172 */       for (int k2 = k1 + 1; k2 < simp.size(); k2++) {
/* 1173 */         int[] r1 = (int[])simp.get(k1);
/* 1174 */         int[] r2 = (int[])simp.get(k2);
/* 1175 */         if (((r1[0] >= r2[0]) && (r1[0] <= r2[1])) || ((r1[1] >= r2[0]) && (r1[0] <= r2[1]))) {
/* 1176 */           r1[0] = Math.min(r1[0], r2[0]);
/* 1177 */           r1[1] = Math.max(r1[1], r2[1]);
/* 1178 */           simp.remove(k2);
/* 1179 */           k2--;
/*      */         }
/*      */       }
/*      */     }
/* 1183 */     int[] s = new int[simp.size() * 2];
/* 1184 */     for (int k = 0; k < simp.size(); k++) {
/* 1185 */       int[] r = (int[])simp.get(k);
/* 1186 */       s[(k * 2)] = r[0];
/* 1187 */       s[(k * 2 + 1)] = r[1];
/*      */     }
/* 1189 */     return s;
/*      */   }
/*      */ 
/*      */   protected void addRangeUni(HashMap<Integer, int[]> longTag, boolean includeMetrics, boolean subsetp)
/*      */   {
/*      */     int[] rg;
/* 1193 */     if ((!subsetp) && ((this.subsetRanges != null) || (this.directoryOffset > 0))) {
/* 1194 */       rg = (this.subsetRanges == null) && (this.directoryOffset > 0) ? new int[] { 0, 65535 } : compactRanges(this.subsetRanges);
/*      */       HashMap usemap;
/*      */       HashMap usemap;
/* 1196 */       if ((!this.fontSpecific) && (this.cmap31 != null)) {
/* 1197 */         usemap = this.cmap31;
/*      */       }
/*      */       else
/*      */       {
/*      */         HashMap usemap;
/* 1198 */         if ((this.fontSpecific) && (this.cmap10 != null)) {
/* 1199 */           usemap = this.cmap10;
/*      */         }
/*      */         else
/*      */         {
/*      */           HashMap usemap;
/* 1200 */           if (this.cmap31 != null)
/* 1201 */             usemap = this.cmap31;
/*      */           else
/* 1203 */             usemap = this.cmap10; 
/*      */         }
/*      */       }
/* 1204 */       for (Map.Entry e : usemap.entrySet()) {
/* 1205 */         int[] v = (int[])e.getValue();
/* 1206 */         Integer gi = Integer.valueOf(v[0]);
/* 1207 */         if (!longTag.containsKey(gi))
/*      */         {
/* 1209 */           int c = ((Integer)e.getKey()).intValue();
/* 1210 */           boolean skip = true;
/* 1211 */           for (int k = 0; k < rg.length; k += 2) {
/* 1212 */             if ((c >= rg[k]) && (c <= rg[(k + 1)])) {
/* 1213 */               skip = false;
/* 1214 */               break;
/*      */             }
/*      */           }
/* 1217 */           if (!skip)
/* 1218 */             longTag.put(gi, includeMetrics ? new int[] { v[0], v[1], c } : null);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void addRangeUni(HashSet<Integer> longTag, boolean subsetp)
/*      */   {
/*      */     int[] rg;
/* 1224 */     if ((!subsetp) && ((this.subsetRanges != null) || (this.directoryOffset > 0))) {
/* 1225 */       rg = (this.subsetRanges == null) && (this.directoryOffset > 0) ? new int[] { 0, 65535 } : compactRanges(this.subsetRanges);
/*      */       HashMap usemap;
/*      */       HashMap usemap;
/* 1227 */       if ((!this.fontSpecific) && (this.cmap31 != null)) {
/* 1228 */         usemap = this.cmap31;
/*      */       }
/*      */       else
/*      */       {
/*      */         HashMap usemap;
/* 1229 */         if ((this.fontSpecific) && (this.cmap10 != null)) {
/* 1230 */           usemap = this.cmap10;
/*      */         }
/*      */         else
/*      */         {
/*      */           HashMap usemap;
/* 1231 */           if (this.cmap31 != null)
/* 1232 */             usemap = this.cmap31;
/*      */           else
/* 1234 */             usemap = this.cmap10; 
/*      */         }
/*      */       }
/* 1235 */       for (Map.Entry e : usemap.entrySet()) {
/* 1236 */         int[] v = (int[])e.getValue();
/* 1237 */         Integer gi = Integer.valueOf(v[0]);
/* 1238 */         if (!longTag.contains(gi))
/*      */         {
/* 1240 */           int c = ((Integer)e.getKey()).intValue();
/* 1241 */           boolean skip = true;
/* 1242 */           for (int k = 0; k < rg.length; k += 2) {
/* 1243 */             if ((c >= rg[k]) && (c <= rg[(k + 1)])) {
/* 1244 */               skip = false;
/* 1245 */               break;
/*      */             }
/*      */           }
/* 1248 */           if (!skip)
/* 1249 */             longTag.add(gi);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   void writeFont(PdfWriter writer, PdfIndirectReference ref, Object[] params)
/*      */     throws DocumentException, IOException
/*      */   {
/* 1263 */     int firstChar = ((Integer)params[0]).intValue();
/* 1264 */     int lastChar = ((Integer)params[1]).intValue();
/* 1265 */     byte[] shortTag = (byte[])params[2];
/* 1266 */     boolean subsetp = (((Boolean)params[3]).booleanValue()) && (this.subset);
/*      */ 
/* 1268 */     if (!subsetp) {
/* 1269 */       firstChar = 0;
/* 1270 */       lastChar = shortTag.length - 1;
/* 1271 */       for (int k = 0; k < shortTag.length; k++)
/* 1272 */         shortTag[k] = 1;
/*      */     }
/* 1274 */     PdfIndirectReference ind_font = null;
/* 1275 */     PdfObject pobj = null;
/* 1276 */     PdfIndirectObject obj = null;
/* 1277 */     String subsetPrefix = "";
/* 1278 */     if (this.embedded) {
/* 1279 */       if (this.cff) {
/* 1280 */         pobj = new BaseFont.StreamFont(readCffFont(), "Type1C", this.compressionLevel);
/* 1281 */         obj = writer.addToBody(pobj);
/* 1282 */         ind_font = obj.getIndirectReference();
/*      */       }
/*      */       else {
/* 1285 */         if (subsetp)
/* 1286 */           subsetPrefix = createSubsetPrefix();
/* 1287 */         HashSet glyphs = new HashSet();
/* 1288 */         for (int k = firstChar; k <= lastChar; k++) {
/* 1289 */           if (shortTag[k] != 0) {
/* 1290 */             int[] metrics = null;
/* 1291 */             if (this.specialMap != null) {
/* 1292 */               int[] cd = GlyphList.nameToUnicode(this.differences[k]);
/* 1293 */               if (cd != null) {
/* 1294 */                 metrics = getMetricsTT(cd[0]);
/*      */               }
/*      */             }
/* 1297 */             else if (this.fontSpecific) {
/* 1298 */               metrics = getMetricsTT(k);
/*      */             } else {
/* 1300 */               metrics = getMetricsTT(this.unicodeDifferences[k]);
/*      */             }
/* 1302 */             if (metrics != null)
/* 1303 */               glyphs.add(Integer.valueOf(metrics[0]));
/*      */           }
/*      */         }
/* 1306 */         addRangeUni(glyphs, subsetp);
/* 1307 */         byte[] b = null;
/* 1308 */         if ((subsetp) || (this.directoryOffset != 0) || (this.subsetRanges != null)) {
/* 1309 */           b = getSubSet(new HashSet(glyphs), subsetp);
/*      */         }
/*      */         else {
/* 1312 */           b = getFullFont();
/*      */         }
/* 1314 */         int[] lengths = { b.length };
/* 1315 */         pobj = new BaseFont.StreamFont(b, lengths, this.compressionLevel);
/* 1316 */         obj = writer.addToBody(pobj);
/* 1317 */         ind_font = obj.getIndirectReference();
/*      */       }
/*      */     }
/* 1320 */     pobj = getFontDescriptor(ind_font, subsetPrefix, null);
/* 1321 */     if (pobj != null) {
/* 1322 */       obj = writer.addToBody(pobj);
/* 1323 */       ind_font = obj.getIndirectReference();
/*      */     }
/* 1325 */     pobj = getFontBaseType(ind_font, subsetPrefix, firstChar, lastChar, shortTag);
/* 1326 */     writer.addToBody(pobj, ref);
/*      */   }
/*      */ 
/*      */   protected byte[] readCffFont()
/*      */     throws IOException
/*      */   {
/* 1337 */     RandomAccessFileOrArray rf2 = new RandomAccessFileOrArray(this.rf);
/* 1338 */     byte[] b = new byte[this.cffLength];
/*      */     try {
/* 1340 */       rf2.reOpen();
/* 1341 */       rf2.seek(this.cffOffset);
/* 1342 */       rf2.readFully(b);
/*      */     }
/*      */     finally {
/*      */       try {
/* 1346 */         rf2.close();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*      */       }
/*      */     }
/* 1352 */     return b;
/*      */   }
/*      */ 
/*      */   public PdfStream getFullFontStream()
/*      */     throws IOException, DocumentException
/*      */   {
/* 1362 */     if (this.cff) {
/* 1363 */       return new BaseFont.StreamFont(readCffFont(), "Type1C", this.compressionLevel);
/*      */     }
/*      */ 
/* 1366 */     byte[] b = getFullFont();
/* 1367 */     int[] lengths = { b.length };
/* 1368 */     return new BaseFont.StreamFont(b, lengths, this.compressionLevel);
/*      */   }
/*      */ 
/*      */   public float getFontDescriptor(int key, float fontSize)
/*      */   {
/* 1381 */     switch (key) {
/*      */     case 1:
/* 1383 */       return this.os_2.sTypoAscender * fontSize / this.head.unitsPerEm;
/*      */     case 2:
/* 1385 */       return this.os_2.sCapHeight * fontSize / this.head.unitsPerEm;
/*      */     case 3:
/* 1387 */       return this.os_2.sTypoDescender * fontSize / this.head.unitsPerEm;
/*      */     case 4:
/* 1389 */       return (float)this.italicAngle;
/*      */     case 5:
/* 1391 */       return fontSize * this.head.xMin / this.head.unitsPerEm;
/*      */     case 6:
/* 1393 */       return fontSize * this.head.yMin / this.head.unitsPerEm;
/*      */     case 7:
/* 1395 */       return fontSize * this.head.xMax / this.head.unitsPerEm;
/*      */     case 8:
/* 1397 */       return fontSize * this.head.yMax / this.head.unitsPerEm;
/*      */     case 9:
/* 1399 */       return fontSize * this.hhea.Ascender / this.head.unitsPerEm;
/*      */     case 10:
/* 1401 */       return fontSize * this.hhea.Descender / this.head.unitsPerEm;
/*      */     case 11:
/* 1403 */       return fontSize * this.hhea.LineGap / this.head.unitsPerEm;
/*      */     case 12:
/* 1405 */       return fontSize * this.hhea.advanceWidthMax / this.head.unitsPerEm;
/*      */     case 13:
/* 1407 */       return (this.underlinePosition - this.underlineThickness / 2) * fontSize / this.head.unitsPerEm;
/*      */     case 14:
/* 1409 */       return this.underlineThickness * fontSize / this.head.unitsPerEm;
/*      */     case 15:
/* 1411 */       return this.os_2.yStrikeoutPosition * fontSize / this.head.unitsPerEm;
/*      */     case 16:
/* 1413 */       return this.os_2.yStrikeoutSize * fontSize / this.head.unitsPerEm;
/*      */     case 17:
/* 1415 */       return this.os_2.ySubscriptYSize * fontSize / this.head.unitsPerEm;
/*      */     case 18:
/* 1417 */       return -this.os_2.ySubscriptYOffset * fontSize / this.head.unitsPerEm;
/*      */     case 19:
/* 1419 */       return this.os_2.ySuperscriptYSize * fontSize / this.head.unitsPerEm;
/*      */     case 20:
/* 1421 */       return this.os_2.ySuperscriptYOffset * fontSize / this.head.unitsPerEm;
/*      */     case 21:
/* 1423 */       return this.os_2.usWeightClass;
/*      */     case 22:
/* 1425 */       return this.os_2.usWidthClass;
/*      */     }
/* 1427 */     return 0.0F;
/*      */   }
/*      */ 
/*      */   public int[] getMetricsTT(int c)
/*      */   {
/* 1435 */     if (this.cmapExt != null)
/* 1436 */       return (int[])this.cmapExt.get(Integer.valueOf(c));
/* 1437 */     if ((!this.fontSpecific) && (this.cmap31 != null))
/* 1438 */       return (int[])this.cmap31.get(Integer.valueOf(c));
/* 1439 */     if ((this.fontSpecific) && (this.cmap10 != null))
/* 1440 */       return (int[])this.cmap10.get(Integer.valueOf(c));
/* 1441 */     if (this.cmap31 != null)
/* 1442 */       return (int[])this.cmap31.get(Integer.valueOf(c));
/* 1443 */     if (this.cmap10 != null)
/* 1444 */       return (int[])this.cmap10.get(Integer.valueOf(c));
/* 1445 */     return null;
/*      */   }
/*      */ 
/*      */   public String getPostscriptFontName()
/*      */   {
/* 1453 */     return this.fontName;
/*      */   }
/*      */ 
/*      */   public String[] getCodePagesSupported()
/*      */   {
/* 1461 */     long cp = (this.os_2.ulCodePageRange2 << 32) + (this.os_2.ulCodePageRange1 & 0xFFFFFFFF);
/* 1462 */     int count = 0;
/* 1463 */     long bit = 1L;
/* 1464 */     for (int k = 0; k < 64; k++) {
/* 1465 */       if (((cp & bit) != 0L) && (codePages[k] != null))
/* 1466 */         count++;
/* 1467 */       bit <<= 1;
/*      */     }
/* 1469 */     String[] ret = new String[count];
/* 1470 */     count = 0;
/* 1471 */     bit = 1L;
/* 1472 */     for (int k = 0; k < 64; k++) {
/* 1473 */       if (((cp & bit) != 0L) && (codePages[k] != null))
/* 1474 */         ret[(count++)] = codePages[k];
/* 1475 */       bit <<= 1;
/*      */     }
/* 1477 */     return ret;
/*      */   }
/*      */ 
/*      */   public String[][] getFullFontName()
/*      */   {
/* 1490 */     return this.fullName;
/*      */   }
/*      */ 
/*      */   public String[][] getAllNameEntries()
/*      */   {
/* 1503 */     return this.allNameEntries;
/*      */   }
/*      */ 
/*      */   public String[][] getFamilyFontName()
/*      */   {
/* 1516 */     return this.familyName;
/*      */   }
/*      */ 
/*      */   public boolean hasKernPairs()
/*      */   {
/* 1524 */     return this.kerning.size() > 0;
/*      */   }
/*      */ 
/*      */   public void setPostscriptFontName(String name)
/*      */   {
/* 1534 */     this.fontName = name;
/*      */   }
/*      */ 
/*      */   public boolean setKerning(int char1, int char2, int kern)
/*      */   {
/* 1546 */     int[] metrics = getMetricsTT(char1);
/* 1547 */     if (metrics == null)
/* 1548 */       return false;
/* 1549 */     int c1 = metrics[0];
/* 1550 */     metrics = getMetricsTT(char2);
/* 1551 */     if (metrics == null)
/* 1552 */       return false;
/* 1553 */     int c2 = metrics[0];
/* 1554 */     this.kerning.put((c1 << 16) + c2, kern);
/* 1555 */     return true;
/*      */   }
/*      */ 
/*      */   protected int[] getRawCharBBox(int c, String name)
/*      */   {
/* 1560 */     HashMap map = null;
/* 1561 */     if ((name == null) || (this.cmap31 == null))
/* 1562 */       map = this.cmap10;
/*      */     else
/* 1564 */       map = this.cmap31;
/* 1565 */     if (map == null)
/* 1566 */       return null;
/* 1567 */     int[] metric = (int[])map.get(Integer.valueOf(c));
/* 1568 */     if ((metric == null) || (this.bboxes == null))
/* 1569 */       return null;
/* 1570 */     return this.bboxes[metric[0]];
/*      */   }
/*      */ 
/*      */   protected static class WindowsMetrics
/*      */   {
/*      */     short xAvgCharWidth;
/*      */     int usWeightClass;
/*      */     int usWidthClass;
/*      */     short fsType;
/*      */     short ySubscriptXSize;
/*      */     short ySubscriptYSize;
/*      */     short ySubscriptXOffset;
/*      */     short ySubscriptYOffset;
/*      */     short ySuperscriptXSize;
/*      */     short ySuperscriptYSize;
/*      */     short ySuperscriptXOffset;
/*      */     short ySuperscriptYOffset;
/*      */     short yStrikeoutSize;
/*      */     short yStrikeoutPosition;
/*      */     short sFamilyClass;
/*  313 */     byte[] panose = new byte[10];
/*      */ 
/*  315 */     byte[] achVendID = new byte[4];
/*      */     int fsSelection;
/*      */     int usFirstCharIndex;
/*      */     int usLastCharIndex;
/*      */     short sTypoAscender;
/*      */     short sTypoDescender;
/*      */     short sTypoLineGap;
/*      */     int usWinAscent;
/*      */     int usWinDescent;
/*      */     int ulCodePageRange1;
/*      */     int ulCodePageRange2;
/*      */     int sCapHeight;
/*      */   }
/*      */ 
/*      */   protected static class HorizontalHeader
/*      */   {
/*      */     short Ascender;
/*      */     short Descender;
/*      */     short LineGap;
/*      */     int advanceWidthMax;
/*      */     short minLeftSideBearing;
/*      */     short minRightSideBearing;
/*      */     short xMaxExtent;
/*      */     short caretSlopeRise;
/*      */     short caretSlopeRun;
/*      */     int numberOfHMetrics;
/*      */   }
/*      */ 
/*      */   protected static class FontHeader
/*      */   {
/*      */     int flags;
/*      */     int unitsPerEm;
/*      */     short xMin;
/*      */     short yMin;
/*      */     short xMax;
/*      */     short yMax;
/*      */     int macStyle;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.TrueTypeFont
 * JD-Core Version:    0.6.2
 */