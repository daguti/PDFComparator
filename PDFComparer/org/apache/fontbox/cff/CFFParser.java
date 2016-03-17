/*      */ package org.apache.fontbox.cff;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.apache.fontbox.cff.charset.CFFCharset;
/*      */ import org.apache.fontbox.cff.charset.CFFCharset.Entry;
/*      */ import org.apache.fontbox.cff.charset.CFFExpertCharset;
/*      */ import org.apache.fontbox.cff.charset.CFFExpertSubsetCharset;
/*      */ import org.apache.fontbox.cff.charset.CFFISOAdobeCharset;
/*      */ import org.apache.fontbox.cff.encoding.CFFEncoding;
/*      */ import org.apache.fontbox.cff.encoding.CFFExpertEncoding;
/*      */ import org.apache.fontbox.cff.encoding.CFFStandardEncoding;
/*      */ 
/*      */ public class CFFParser
/*      */ {
/*      */   private static final String TAG_OTTO = "OTTO";
/*      */   private static final String TAG_TTCF = "ttcf";
/*      */   private static final String TAG_TTFONLY = "";
/*      */   private CFFDataInput input;
/*      */   private Header header;
/*      */   private IndexData nameIndex;
/*      */   private IndexData topDictIndex;
/*      */   private IndexData stringIndex;
/*      */ 
/*      */   public CFFParser()
/*      */   {
/*   50 */     this.input = null;
/*   51 */     this.header = null;
/*   52 */     this.nameIndex = null;
/*   53 */     this.topDictIndex = null;
/*   54 */     this.stringIndex = null;
/*      */   }
/*      */ 
/*      */   public List<CFFFont> parse(byte[] bytes)
/*      */     throws IOException
/*      */   {
/*   64 */     this.input = new CFFDataInput(bytes);
/*      */ 
/*   66 */     String firstTag = readTagName(this.input);
/*      */ 
/*   68 */     if ("OTTO".equals(firstTag))
/*      */     {
/*   72 */       short numTables = this.input.readShort();
/*   73 */       short searchRange = this.input.readShort();
/*   74 */       short entrySelector = this.input.readShort();
/*   75 */       short rangeShift = this.input.readShort();
/*      */ 
/*   77 */       boolean cffFound = false;
/*   78 */       for (int q = 0; q < numTables; q++)
/*      */       {
/*   80 */         String tagName = readTagName(this.input);
/*   81 */         long checksum = readLong(this.input);
/*   82 */         long offset = readLong(this.input);
/*   83 */         long length = readLong(this.input);
/*   84 */         if (tagName.equals("CFF "))
/*      */         {
/*   86 */           cffFound = true;
/*   87 */           byte[] bytes2 = new byte[(int)length];
/*   88 */           System.arraycopy(bytes, (int)offset, bytes2, 0, bytes2.length);
/*   89 */           this.input = new CFFDataInput(bytes2);
/*   90 */           break;
/*      */         }
/*      */       }
/*   93 */       if (!cffFound)
/*      */       {
/*   95 */         throw new IOException("CFF tag not found in this OpenType font.");
/*      */       }
/*      */     } else {
/*   98 */       if ("ttcf".equals(firstTag))
/*      */       {
/*  100 */         throw new IOException("True Type Collection fonts are not supported.");
/*      */       }
/*  102 */       if ("".equals(firstTag))
/*      */       {
/*  104 */         throw new IOException("OpenType fonts containing a true type font are not supported.");
/*      */       }
/*      */ 
/*  108 */       this.input.setPosition(0);
/*      */     }
/*      */ 
/*  111 */     this.header = readHeader(this.input);
/*  112 */     this.nameIndex = readIndexData(this.input);
/*  113 */     this.topDictIndex = readIndexData(this.input);
/*  114 */     this.stringIndex = readIndexData(this.input);
/*  115 */     IndexData globalSubrIndex = readIndexData(this.input);
/*      */ 
/*  117 */     List fonts = new ArrayList();
/*  118 */     for (int i = 0; i < this.nameIndex.getCount(); i++)
/*      */     {
/*  120 */       CFFFont font = parseFont(i);
/*  121 */       font.setGlobalSubrIndex(globalSubrIndex);
/*  122 */       fonts.add(font);
/*      */     }
/*  124 */     return fonts;
/*      */   }
/*      */ 
/*      */   private static String readTagName(CFFDataInput input) throws IOException
/*      */   {
/*  129 */     byte[] b = input.readBytes(4);
/*  130 */     return new String(b, "ISO-8859-1");
/*      */   }
/*      */ 
/*      */   private static long readLong(CFFDataInput input) throws IOException
/*      */   {
/*  135 */     return input.readCard16() << 16 | input.readCard16();
/*      */   }
/*      */ 
/*      */   private static Header readHeader(CFFDataInput input) throws IOException
/*      */   {
/*  140 */     Header cffHeader = new Header(null);
/*  141 */     cffHeader.major = input.readCard8();
/*  142 */     cffHeader.minor = input.readCard8();
/*  143 */     cffHeader.hdrSize = input.readCard8();
/*  144 */     cffHeader.offSize = input.readOffSize();
/*  145 */     return cffHeader;
/*      */   }
/*      */ 
/*      */   private static IndexData readIndexData(CFFDataInput input) throws IOException
/*      */   {
/*  150 */     int count = input.readCard16();
/*  151 */     IndexData index = new IndexData(count);
/*  152 */     if (count == 0)
/*      */     {
/*  154 */       return index;
/*      */     }
/*  156 */     int offSize = input.readOffSize();
/*  157 */     for (int i = 0; i <= count; i++)
/*      */     {
/*  159 */       index.setOffset(i, input.readOffset(offSize));
/*      */     }
/*  161 */     int dataSize = index.getOffset(count) - index.getOffset(0);
/*  162 */     index.initData(dataSize);
/*  163 */     for (int i = 0; i < dataSize; i++)
/*      */     {
/*  165 */       index.setData(i, input.readCard8());
/*      */     }
/*  167 */     return index;
/*      */   }
/*      */ 
/*      */   private static DictData readDictData(CFFDataInput input) throws IOException
/*      */   {
/*  172 */     DictData dict = new DictData(null);
/*  173 */     dict.entries = new ArrayList();
/*  174 */     while (input.hasRemaining())
/*      */     {
/*  176 */       CFFParser.DictData.Entry entry = readEntry(input);
/*  177 */       dict.entries.add(entry);
/*      */     }
/*  179 */     return dict;
/*      */   }
/*      */ 
/*      */   private static CFFParser.DictData.Entry readEntry(CFFDataInput input) throws IOException
/*      */   {
/*  184 */     CFFParser.DictData.Entry entry = new CFFParser.DictData.Entry(null);
/*      */     while (true)
/*      */     {
/*  187 */       int b0 = input.readUnsignedByte();
/*      */ 
/*  189 */       if ((b0 >= 0) && (b0 <= 21))
/*      */       {
/*  191 */         entry.operator = readOperator(input, b0);
/*  192 */         break;
/*      */       }
/*  194 */       if ((b0 == 28) || (b0 == 29))
/*      */       {
/*  196 */         entry.operands.add(readIntegerNumber(input, b0));
/*      */       }
/*  198 */       else if (b0 == 30)
/*      */       {
/*  200 */         entry.operands.add(readRealNumber(input, b0));
/*      */       }
/*  202 */       else if ((b0 >= 32) && (b0 <= 254))
/*      */       {
/*  204 */         entry.operands.add(readIntegerNumber(input, b0));
/*      */       }
/*      */       else
/*      */       {
/*  208 */         throw new IllegalArgumentException();
/*      */       }
/*      */     }
/*  211 */     return entry;
/*      */   }
/*      */ 
/*      */   private static CFFOperator readOperator(CFFDataInput input, int b0) throws IOException
/*      */   {
/*  216 */     CFFOperator.Key key = readOperatorKey(input, b0);
/*  217 */     return CFFOperator.getOperator(key);
/*      */   }
/*      */ 
/*      */   private static CFFOperator.Key readOperatorKey(CFFDataInput input, int b0) throws IOException
/*      */   {
/*  222 */     if (b0 == 12)
/*      */     {
/*  224 */       int b1 = input.readUnsignedByte();
/*  225 */       return new CFFOperator.Key(b0, b1);
/*      */     }
/*  227 */     return new CFFOperator.Key(b0);
/*      */   }
/*      */ 
/*      */   private static Integer readIntegerNumber(CFFDataInput input, int b0) throws IOException
/*      */   {
/*  232 */     if (b0 == 28)
/*      */     {
/*  234 */       int b1 = input.readUnsignedByte();
/*  235 */       int b2 = input.readUnsignedByte();
/*  236 */       return Integer.valueOf((short)(b1 << 8 | b2));
/*      */     }
/*  238 */     if (b0 == 29)
/*      */     {
/*  240 */       int b1 = input.readUnsignedByte();
/*  241 */       int b2 = input.readUnsignedByte();
/*  242 */       int b3 = input.readUnsignedByte();
/*  243 */       int b4 = input.readUnsignedByte();
/*  244 */       return Integer.valueOf(b1 << 24 | b2 << 16 | b3 << 8 | b4);
/*      */     }
/*  246 */     if ((b0 >= 32) && (b0 <= 246))
/*      */     {
/*  248 */       return Integer.valueOf(b0 - 139);
/*      */     }
/*  250 */     if ((b0 >= 247) && (b0 <= 250))
/*      */     {
/*  252 */       int b1 = input.readUnsignedByte();
/*  253 */       return Integer.valueOf((b0 - 247) * 256 + b1 + 108);
/*      */     }
/*  255 */     if ((b0 >= 251) && (b0 <= 254))
/*      */     {
/*  257 */       int b1 = input.readUnsignedByte();
/*  258 */       return Integer.valueOf(-(b0 - 251) * 256 - b1 - 108);
/*      */     }
/*      */ 
/*  262 */     throw new IllegalArgumentException();
/*      */   }
/*      */ 
/*      */   private static Double readRealNumber(CFFDataInput input, int b0)
/*      */     throws IOException
/*      */   {
/*  268 */     StringBuffer sb = new StringBuffer();
/*  269 */     boolean done = false;
/*  270 */     boolean exponentMissing = false;
/*  271 */     while (!done)
/*      */     {
/*  273 */       int b = input.readUnsignedByte();
/*  274 */       int[] nibbles = { b / 16, b % 16 };
/*  275 */       for (int nibble : nibbles)
/*      */       {
/*  277 */         switch (nibble)
/*      */         {
/*      */         case 0:
/*      */         case 1:
/*      */         case 2:
/*      */         case 3:
/*      */         case 4:
/*      */         case 5:
/*      */         case 6:
/*      */         case 7:
/*      */         case 8:
/*      */         case 9:
/*  289 */           sb.append(nibble);
/*  290 */           exponentMissing = false;
/*  291 */           break;
/*      */         case 10:
/*  293 */           sb.append(".");
/*  294 */           break;
/*      */         case 11:
/*  296 */           sb.append("E");
/*  297 */           exponentMissing = true;
/*  298 */           break;
/*      */         case 12:
/*  300 */           sb.append("E-");
/*  301 */           exponentMissing = true;
/*  302 */           break;
/*      */         case 13:
/*  304 */           break;
/*      */         case 14:
/*  306 */           sb.append("-");
/*  307 */           break;
/*      */         case 15:
/*  309 */           done = true;
/*  310 */           break;
/*      */         default:
/*  312 */           throw new IllegalArgumentException();
/*      */         }
/*      */       }
/*      */     }
/*  316 */     if (exponentMissing)
/*      */     {
/*  321 */       sb.append("0");
/*      */     }
/*  323 */     return Double.valueOf(sb.toString());
/*      */   }
/*      */ 
/*      */   private CFFFont parseFont(int index) throws IOException
/*      */   {
/*  328 */     CFFFont font = null;
/*  329 */     DataInput nameInput = new DataInput(this.nameIndex.getBytes(index));
/*  330 */     String name = nameInput.getString();
/*      */ 
/*  332 */     CFFDataInput topDictInput = new CFFDataInput(this.topDictIndex.getBytes(index));
/*  333 */     DictData topDict = readDictData(topDictInput);
/*  334 */     CFFParser.DictData.Entry syntheticBaseEntry = topDict.getEntry("SyntheticBase");
/*  335 */     if (syntheticBaseEntry != null)
/*      */     {
/*  337 */       throw new IOException("Synthetic Fonts are not supported");
/*      */     }
/*      */ 
/*  340 */     CFFParser.DictData.Entry rosEntry = topDict.getEntry("ROS");
/*  341 */     if (rosEntry != null)
/*      */     {
/*  343 */       font = new CFFFontROS();
/*  344 */       ((CFFFontROS)font).setRegistry(readString(rosEntry.getNumber(0).intValue()));
/*  345 */       ((CFFFontROS)font).setOrdering(readString(rosEntry.getNumber(1).intValue()));
/*  346 */       ((CFFFontROS)font).setSupplement(rosEntry.getNumber(2).intValue());
/*      */     }
/*      */ 
/*  349 */     if (font == null)
/*      */     {
/*  352 */       font = new CFFFont();
/*      */     }
/*      */ 
/*  355 */     font.setName(name);
/*      */ 
/*  357 */     font.addValueToTopDict("version", getString(topDict, "version"));
/*  358 */     font.addValueToTopDict("Notice", getString(topDict, "Notice"));
/*  359 */     font.addValueToTopDict("Copyright", getString(topDict, "Copyright"));
/*  360 */     font.addValueToTopDict("FullName", getString(topDict, "FullName"));
/*  361 */     font.addValueToTopDict("FamilyName", getString(topDict, "FamilyName"));
/*  362 */     font.addValueToTopDict("Weight", getString(topDict, "Weight"));
/*  363 */     font.addValueToTopDict("isFixedPitch", getBoolean(topDict, "isFixedPitch", false));
/*  364 */     font.addValueToTopDict("ItalicAngle", getNumber(topDict, "ItalicAngle", Integer.valueOf(0)));
/*  365 */     font.addValueToTopDict("UnderlinePosition", getNumber(topDict, "UnderlinePosition", Integer.valueOf(-100)));
/*  366 */     font.addValueToTopDict("UnderlineThickness", getNumber(topDict, "UnderlineThickness", Integer.valueOf(50)));
/*  367 */     font.addValueToTopDict("PaintType", getNumber(topDict, "PaintType", Integer.valueOf(0)));
/*  368 */     font.addValueToTopDict("CharstringType", getNumber(topDict, "CharstringType", Integer.valueOf(2)));
/*  369 */     font.addValueToTopDict("FontMatrix", getArray(topDict, "FontMatrix", Arrays.asList(new Number[] { Double.valueOf(0.001D), Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(0.001D), Double.valueOf(0.0D), Double.valueOf(0.0D) })));
/*      */ 
/*  376 */     font.addValueToTopDict("UniqueID", getNumber(topDict, "UniqueID", null));
/*  377 */     font.addValueToTopDict("FontBBox", getArray(topDict, "FontBBox", Arrays.asList(new Number[] { Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0) })));
/*      */ 
/*  384 */     font.addValueToTopDict("StrokeWidth", getNumber(topDict, "StrokeWidth", Integer.valueOf(0)));
/*  385 */     font.addValueToTopDict("XUID", getArray(topDict, "XUID", null));
/*      */ 
/*  387 */     CFFParser.DictData.Entry charStringsEntry = topDict.getEntry("CharStrings");
/*  388 */     int charStringsOffset = charStringsEntry.getNumber(0).intValue();
/*  389 */     this.input.setPosition(charStringsOffset);
/*  390 */     IndexData charStringsIndex = readIndexData(this.input);
/*  391 */     CFFParser.DictData.Entry charsetEntry = topDict.getEntry("charset");
/*      */ 
/*  393 */     int charsetId = charsetEntry != null ? charsetEntry.getNumber(0).intValue() : 0;
/*      */     CFFCharset charset;
/*      */     CFFCharset charset;
/*  394 */     if (charsetId == 0)
/*      */     {
/*  396 */       charset = CFFISOAdobeCharset.getInstance();
/*      */     }
/*      */     else
/*      */     {
/*      */       CFFCharset charset;
/*  398 */       if (charsetId == 1)
/*      */       {
/*  400 */         charset = CFFExpertCharset.getInstance();
/*      */       }
/*      */       else
/*      */       {
/*      */         CFFCharset charset;
/*  402 */         if (charsetId == 2)
/*      */         {
/*  404 */           charset = CFFExpertSubsetCharset.getInstance();
/*      */         }
/*      */         else
/*      */         {
/*  408 */           this.input.setPosition(charsetId);
/*  409 */           charset = readCharset(this.input, charStringsIndex.getCount());
/*      */         }
/*      */       }
/*      */     }
/*  411 */     font.setCharset(charset);
/*  412 */     font.getCharStringsDict().put(".notdef", charStringsIndex.getBytes(0));
/*  413 */     int[] gids = new int[charStringsIndex.getCount()];
/*  414 */     List glyphEntries = charset.getEntries();
/*  415 */     for (int i = 1; i < charStringsIndex.getCount(); i++)
/*      */     {
/*  417 */       CFFCharset.Entry glyphEntry = (CFFCharset.Entry)glyphEntries.get(i - 1);
/*  418 */       gids[(i - 1)] = glyphEntry.getSID();
/*  419 */       font.getCharStringsDict().put(glyphEntry.getName(), charStringsIndex.getBytes(i));
/*      */     }
/*  421 */     CFFParser.DictData.Entry encodingEntry = topDict.getEntry("Encoding");
/*      */ 
/*  423 */     int encodingId = encodingEntry != null ? encodingEntry.getNumber(0).intValue() : 0;
/*      */     CFFEncoding encoding;
/*      */     CFFEncoding encoding;
/*  424 */     if ((encodingId == 0) || (rosEntry != null))
/*      */     {
/*  426 */       encoding = CFFStandardEncoding.getInstance();
/*      */     }
/*      */     else
/*      */     {
/*      */       CFFEncoding encoding;
/*  428 */       if (encodingId == 1)
/*      */       {
/*  430 */         encoding = CFFExpertEncoding.getInstance();
/*      */       }
/*      */       else
/*      */       {
/*  434 */         this.input.setPosition(encodingId);
/*  435 */         encoding = readEncoding(this.input, gids);
/*      */       }
/*      */     }
/*  437 */     font.setEncoding(encoding);
/*      */ 
/*  439 */     if (rosEntry != null)
/*      */     {
/*  443 */       CFFParser.DictData.Entry fdArrayEntry = topDict.getEntry("FDArray");
/*  444 */       if (fdArrayEntry == null)
/*      */       {
/*  446 */         throw new IOException("FDArray is missing for a CIDKeyed Font.");
/*      */       }
/*      */ 
/*  449 */       int fontDictOffset = fdArrayEntry.getNumber(0).intValue();
/*  450 */       this.input.setPosition(fontDictOffset);
/*  451 */       IndexData fdIndex = readIndexData(this.input);
/*      */ 
/*  453 */       List privateDictionaries = new LinkedList();
/*  454 */       List fontDictionaries = new LinkedList();
/*  455 */       CFFFontROS fontRos = (CFFFontROS)font;
/*      */ 
/*  457 */       for (int i = 0; i < fdIndex.getCount(); i++)
/*      */       {
/*  459 */         byte[] b = fdIndex.getBytes(i);
/*  460 */         CFFDataInput fontDictInput = new CFFDataInput(b);
/*  461 */         DictData fontDictData = readDictData(fontDictInput);
/*      */ 
/*  463 */         Map fontDictMap = new LinkedHashMap();
/*  464 */         fontDictMap.put("FontName", getString(fontDictData, "FontName"));
/*  465 */         fontDictMap.put("FontType", getNumber(fontDictData, "FontType", Integer.valueOf(0)));
/*  466 */         fontDictMap.put("FontBBox", getDelta(fontDictData, "FontBBox", null));
/*  467 */         fontDictMap.put("FontMatrix", getDelta(fontDictData, "FontMatrix", null));
/*      */ 
/*  469 */         fontDictionaries.add(fontDictMap);
/*      */ 
/*  471 */         CFFParser.DictData.Entry privateEntry = fontDictData.getEntry("Private");
/*      */ 
/*  473 */         if (privateEntry == null)
/*      */         {
/*  475 */           throw new IOException("Missing Private Dictionary");
/*      */         }
/*      */ 
/*  478 */         int privateOffset = privateEntry.getNumber(1).intValue();
/*  479 */         this.input.setPosition(privateOffset);
/*  480 */         int privateSize = privateEntry.getNumber(0).intValue();
/*  481 */         CFFDataInput privateDictData = new CFFDataInput(this.input.readBytes(privateSize));
/*  482 */         DictData privateDict = readDictData(privateDictData);
/*      */ 
/*  484 */         Map privDict = new LinkedHashMap();
/*  485 */         privDict.put("BlueValues", getDelta(privateDict, "BlueValues", null));
/*  486 */         privDict.put("OtherBlues", getDelta(privateDict, "OtherBlues", null));
/*  487 */         privDict.put("FamilyBlues", getDelta(privateDict, "FamilyBlues", null));
/*  488 */         privDict.put("FamilyOtherBlues", getDelta(privateDict, "FamilyOtherBlues", null));
/*  489 */         privDict.put("BlueScale", getNumber(privateDict, "BlueScale", Double.valueOf(0.039625D)));
/*  490 */         privDict.put("BlueShift", getNumber(privateDict, "BlueShift", Integer.valueOf(7)));
/*  491 */         privDict.put("BlueFuzz", getNumber(privateDict, "BlueFuzz", Integer.valueOf(1)));
/*  492 */         privDict.put("StdHW", getNumber(privateDict, "StdHW", null));
/*  493 */         privDict.put("StdVW", getNumber(privateDict, "StdVW", null));
/*  494 */         privDict.put("StemSnapH", getDelta(privateDict, "StemSnapH", null));
/*  495 */         privDict.put("StemSnapV", getDelta(privateDict, "StemSnapV", null));
/*  496 */         privDict.put("ForceBold", getBoolean(privateDict, "ForceBold", false));
/*  497 */         privDict.put("LanguageGroup", getNumber(privateDict, "LanguageGroup", Integer.valueOf(0)));
/*  498 */         privDict.put("ExpansionFactor", getNumber(privateDict, "ExpansionFactor", Double.valueOf(0.06D)));
/*  499 */         privDict.put("initialRandomSeed", getNumber(privateDict, "initialRandomSeed", Integer.valueOf(0)));
/*  500 */         privDict.put("defaultWidthX", getNumber(privateDict, "defaultWidthX", Integer.valueOf(0)));
/*  501 */         privDict.put("nominalWidthX", getNumber(privateDict, "nominalWidthX", Integer.valueOf(0)));
/*      */ 
/*  503 */         int localSubrOffset = ((Integer)getNumber(privateDict, "Subrs", Integer.valueOf(0))).intValue();
/*  504 */         if (localSubrOffset == 0)
/*      */         {
/*  506 */           font.setLocalSubrIndex(new IndexData(0));
/*      */         }
/*      */         else
/*      */         {
/*  510 */           this.input.setPosition(privateOffset + localSubrOffset);
/*  511 */           font.setLocalSubrIndex(readIndexData(this.input));
/*      */         }
/*      */ 
/*  514 */         privateDictionaries.add(privDict);
/*      */       }
/*      */ 
/*  517 */       fontRos.setFontDict(fontDictionaries);
/*  518 */       fontRos.setPrivDict(privateDictionaries);
/*      */ 
/*  520 */       CFFParser.DictData.Entry fdSelectEntry = topDict.getEntry("FDSelect");
/*  521 */       int fdSelectPos = fdSelectEntry.getNumber(0).intValue();
/*  522 */       this.input.setPosition(fdSelectPos);
/*  523 */       CIDKeyedFDSelect fdSelect = readFDSelect(this.input, charStringsIndex.getCount(), fontRos);
/*      */ 
/*  525 */       font.addValueToPrivateDict("defaultWidthX", Integer.valueOf(1000));
/*  526 */       font.addValueToPrivateDict("nominalWidthX", Integer.valueOf(0));
/*      */ 
/*  528 */       fontRos.setFdSelect(fdSelect);
/*      */     }
/*      */     else
/*      */     {
/*  533 */       CFFParser.DictData.Entry privateEntry = topDict.getEntry("Private");
/*  534 */       int privateOffset = privateEntry.getNumber(1).intValue();
/*  535 */       this.input.setPosition(privateOffset);
/*  536 */       int privateSize = privateEntry.getNumber(0).intValue();
/*  537 */       CFFDataInput privateDictData = new CFFDataInput(this.input.readBytes(privateSize));
/*  538 */       DictData privateDict = readDictData(privateDictData);
/*  539 */       font.addValueToPrivateDict("BlueValues", getDelta(privateDict, "BlueValues", null));
/*  540 */       font.addValueToPrivateDict("OtherBlues", getDelta(privateDict, "OtherBlues", null));
/*  541 */       font.addValueToPrivateDict("FamilyBlues", getDelta(privateDict, "FamilyBlues", null));
/*  542 */       font.addValueToPrivateDict("FamilyOtherBlues", getDelta(privateDict, "FamilyOtherBlues", null));
/*  543 */       font.addValueToPrivateDict("BlueScale", getNumber(privateDict, "BlueScale", Double.valueOf(0.039625D)));
/*  544 */       font.addValueToPrivateDict("BlueShift", getNumber(privateDict, "BlueShift", Integer.valueOf(7)));
/*  545 */       font.addValueToPrivateDict("BlueFuzz", getNumber(privateDict, "BlueFuzz", Integer.valueOf(1)));
/*  546 */       font.addValueToPrivateDict("StdHW", getNumber(privateDict, "StdHW", null));
/*  547 */       font.addValueToPrivateDict("StdVW", getNumber(privateDict, "StdVW", null));
/*  548 */       font.addValueToPrivateDict("StemSnapH", getDelta(privateDict, "StemSnapH", null));
/*  549 */       font.addValueToPrivateDict("StemSnapV", getDelta(privateDict, "StemSnapV", null));
/*  550 */       font.addValueToPrivateDict("ForceBold", getBoolean(privateDict, "ForceBold", false));
/*  551 */       font.addValueToPrivateDict("LanguageGroup", getNumber(privateDict, "LanguageGroup", Integer.valueOf(0)));
/*  552 */       font.addValueToPrivateDict("ExpansionFactor", getNumber(privateDict, "ExpansionFactor", Double.valueOf(0.06D)));
/*      */ 
/*  554 */       font.addValueToPrivateDict("initialRandomSeed", getNumber(privateDict, "initialRandomSeed", Integer.valueOf(0)));
/*      */ 
/*  556 */       font.addValueToPrivateDict("defaultWidthX", getNumber(privateDict, "defaultWidthX", Integer.valueOf(0)));
/*  557 */       font.addValueToPrivateDict("nominalWidthX", getNumber(privateDict, "nominalWidthX", Integer.valueOf(0)));
/*      */ 
/*  559 */       int localSubrOffset = ((Integer)getNumber(privateDict, "Subrs", Integer.valueOf(0))).intValue();
/*  560 */       if (localSubrOffset == 0)
/*      */       {
/*  562 */         font.setLocalSubrIndex(new IndexData(0));
/*      */       }
/*      */       else
/*      */       {
/*  566 */         this.input.setPosition(privateOffset + localSubrOffset);
/*  567 */         font.setLocalSubrIndex(readIndexData(this.input));
/*      */       }
/*      */     }
/*      */ 
/*  571 */     return font;
/*      */   }
/*      */ 
/*      */   private String readString(int index) throws IOException
/*      */   {
/*  576 */     if ((index >= 0) && (index <= 390))
/*      */     {
/*  578 */       return CFFStandardString.getName(index);
/*      */     }
/*  580 */     if (index - 391 < this.stringIndex.getCount())
/*      */     {
/*  582 */       DataInput dataInput = new DataInput(this.stringIndex.getBytes(index - 391));
/*  583 */       return dataInput.getString();
/*      */     }
/*      */ 
/*  587 */     return CFFStandardString.getName(0);
/*      */   }
/*      */ 
/*      */   private String getString(DictData dict, String name)
/*      */     throws IOException
/*      */   {
/*  593 */     CFFParser.DictData.Entry entry = dict.getEntry(name);
/*  594 */     return entry != null ? readString(entry.getNumber(0).intValue()) : null;
/*      */   }
/*      */ 
/*      */   private Boolean getBoolean(DictData dict, String name, boolean defaultValue) throws IOException
/*      */   {
/*  599 */     CFFParser.DictData.Entry entry = dict.getEntry(name);
/*  600 */     return Boolean.valueOf(entry != null ? entry.getBoolean(0).booleanValue() : defaultValue);
/*      */   }
/*      */ 
/*      */   private Number getNumber(DictData dict, String name, Number defaultValue) throws IOException
/*      */   {
/*  605 */     CFFParser.DictData.Entry entry = dict.getEntry(name);
/*  606 */     return entry != null ? entry.getNumber(0) : defaultValue;
/*      */   }
/*      */ 
/*      */   private List<Number> getArray(DictData dict, String name, List<Number> defaultValue)
/*      */     throws IOException
/*      */   {
/*  612 */     CFFParser.DictData.Entry entry = dict.getEntry(name);
/*  613 */     return entry != null ? entry.getArray() : defaultValue;
/*      */   }
/*      */ 
/*      */   private List<Number> getDelta(DictData dict, String name, List<Number> defaultValue)
/*      */     throws IOException
/*      */   {
/*  619 */     CFFParser.DictData.Entry entry = dict.getEntry(name);
/*  620 */     return entry != null ? entry.getArray() : defaultValue;
/*      */   }
/*      */ 
/*      */   private CFFEncoding readEncoding(CFFDataInput dataInput, int[] gids) throws IOException
/*      */   {
/*  625 */     int format = dataInput.readCard8();
/*  626 */     int baseFormat = format & 0x7F;
/*      */ 
/*  628 */     if (baseFormat == 0)
/*      */     {
/*  630 */       return readFormat0Encoding(dataInput, format, gids);
/*      */     }
/*  632 */     if (baseFormat == 1)
/*      */     {
/*  634 */       return readFormat1Encoding(dataInput, format, gids);
/*      */     }
/*      */ 
/*  638 */     throw new IllegalArgumentException();
/*      */   }
/*      */ 
/*      */   private Format0Encoding readFormat0Encoding(CFFDataInput dataInput, int format, int[] gids)
/*      */     throws IOException
/*      */   {
/*  644 */     Format0Encoding encoding = new Format0Encoding(null);
/*  645 */     encoding.format = format;
/*  646 */     encoding.nCodes = dataInput.readCard8();
/*  647 */     encoding.code = new int[encoding.nCodes];
/*  648 */     for (int i = 0; i < encoding.code.length; i++)
/*      */     {
/*  650 */       encoding.code[i] = dataInput.readCard8();
/*  651 */       encoding.register(encoding.code[i], gids[i]);
/*      */     }
/*  653 */     if ((format & 0x80) != 0)
/*      */     {
/*  655 */       readSupplement(dataInput, encoding);
/*      */     }
/*  657 */     return encoding;
/*      */   }
/*      */ 
/*      */   private Format1Encoding readFormat1Encoding(CFFDataInput dataInput, int format, int[] gids) throws IOException
/*      */   {
/*  662 */     Format1Encoding encoding = new Format1Encoding(null);
/*  663 */     encoding.format = format;
/*  664 */     encoding.nRanges = dataInput.readCard8();
/*  665 */     int count = 0;
/*  666 */     encoding.range = new CFFParser.Format1Encoding.Range1[encoding.nRanges];
/*  667 */     for (int i = 0; i < encoding.range.length; i++)
/*      */     {
/*  669 */       CFFParser.Format1Encoding.Range1 range = new CFFParser.Format1Encoding.Range1(null);
/*  670 */       range.first = dataInput.readCard8();
/*  671 */       range.nLeft = dataInput.readCard8();
/*  672 */       encoding.range[i] = range;
/*  673 */       for (int j = 0; j < 1 + range.nLeft; j++)
/*      */       {
/*  675 */         encoding.register(range.first + j, gids[(count + j)]);
/*      */       }
/*  677 */       count += 1 + range.nLeft;
/*      */     }
/*  679 */     if ((format & 0x80) != 0)
/*      */     {
/*  681 */       readSupplement(dataInput, encoding);
/*      */     }
/*  683 */     return encoding;
/*      */   }
/*      */ 
/*      */   private void readSupplement(CFFDataInput dataInput, EmbeddedEncoding encoding) throws IOException
/*      */   {
/*  688 */     encoding.nSups = dataInput.readCard8();
/*  689 */     encoding.supplement = new CFFParser.EmbeddedEncoding.Supplement[encoding.nSups];
/*  690 */     for (int i = 0; i < encoding.supplement.length; i++)
/*      */     {
/*  692 */       CFFParser.EmbeddedEncoding.Supplement supplement = new CFFParser.EmbeddedEncoding.Supplement();
/*  693 */       supplement.code = dataInput.readCard8();
/*  694 */       supplement.glyph = dataInput.readSID();
/*  695 */       encoding.supplement[i] = supplement;
/*      */     }
/*      */   }
/*      */ 
/*      */   private CIDKeyedFDSelect readFDSelect(CFFDataInput dataInput, int nGlyphs, CFFFontROS ros)
/*      */     throws IOException
/*      */   {
/*  709 */     int format = dataInput.readCard8();
/*  710 */     if (format == 0)
/*      */     {
/*  712 */       return readFormat0FDSelect(dataInput, format, nGlyphs, ros);
/*      */     }
/*  714 */     if (format == 3)
/*      */     {
/*  716 */       return readFormat3FDSelect(dataInput, format, nGlyphs, ros);
/*      */     }
/*      */ 
/*  720 */     throw new IllegalArgumentException();
/*      */   }
/*      */ 
/*      */   private Format0FDSelect readFormat0FDSelect(CFFDataInput dataInput, int format, int nGlyphs, CFFFontROS ros)
/*      */     throws IOException
/*      */   {
/*  736 */     Format0FDSelect fdselect = new Format0FDSelect(ros, null);
/*  737 */     fdselect.format = format;
/*  738 */     fdselect.fds = new int[nGlyphs];
/*  739 */     for (int i = 0; i < fdselect.fds.length; i++)
/*      */     {
/*  741 */       fdselect.fds[i] = dataInput.readCard8();
/*      */     }
/*      */ 
/*  744 */     return fdselect;
/*      */   }
/*      */ 
/*      */   private Format3FDSelect readFormat3FDSelect(CFFDataInput dataInput, int format, int nGlyphs, CFFFontROS ros)
/*      */     throws IOException
/*      */   {
/*  760 */     Format3FDSelect fdselect = new Format3FDSelect(ros, null);
/*  761 */     fdselect.format = format;
/*  762 */     fdselect.nbRanges = dataInput.readCard16();
/*      */ 
/*  764 */     fdselect.range3 = new Range3[fdselect.nbRanges];
/*  765 */     for (int i = 0; i < fdselect.nbRanges; i++)
/*      */     {
/*  767 */       Range3 r3 = new Range3(null);
/*  768 */       r3.first = dataInput.readCard16();
/*  769 */       r3.fd = dataInput.readCard8();
/*  770 */       fdselect.range3[i] = r3;
/*      */     }
/*      */ 
/*  774 */     fdselect.sentinel = dataInput.readCard16();
/*  775 */     return fdselect;
/*      */   }
/*      */ 
/*      */   private CFFCharset readCharset(CFFDataInput dataInput, int nGlyphs)
/*      */     throws IOException
/*      */   {
/*  907 */     int format = dataInput.readCard8();
/*  908 */     if (format == 0)
/*      */     {
/*  910 */       return readFormat0Charset(dataInput, format, nGlyphs);
/*      */     }
/*  912 */     if (format == 1)
/*      */     {
/*  914 */       return readFormat1Charset(dataInput, format, nGlyphs);
/*      */     }
/*  916 */     if (format == 2)
/*      */     {
/*  918 */       return readFormat2Charset(dataInput, format, nGlyphs);
/*      */     }
/*      */ 
/*  922 */     throw new IllegalArgumentException();
/*      */   }
/*      */ 
/*      */   private Format0Charset readFormat0Charset(CFFDataInput dataInput, int format, int nGlyphs)
/*      */     throws IOException
/*      */   {
/*  928 */     Format0Charset charset = new Format0Charset(null);
/*  929 */     charset.format = format;
/*  930 */     charset.glyph = new int[nGlyphs - 1];
/*  931 */     for (int i = 0; i < charset.glyph.length; i++)
/*      */     {
/*  933 */       charset.glyph[i] = dataInput.readSID();
/*  934 */       charset.register(charset.glyph[i], readString(charset.glyph[i]));
/*      */     }
/*  936 */     return charset;
/*      */   }
/*      */ 
/*      */   private Format1Charset readFormat1Charset(CFFDataInput dataInput, int format, int nGlyphs) throws IOException
/*      */   {
/*  941 */     Format1Charset charset = new Format1Charset(null);
/*  942 */     charset.format = format;
/*  943 */     List ranges = new ArrayList();
/*  944 */     for (int i = 0; i < nGlyphs - 1; )
/*      */     {
/*  946 */       CFFParser.Format1Charset.Range1 range = new CFFParser.Format1Charset.Range1(null);
/*  947 */       range.first = dataInput.readSID();
/*  948 */       range.nLeft = dataInput.readCard8();
/*  949 */       ranges.add(range);
/*  950 */       for (int j = 0; j < 1 + range.nLeft; j++)
/*      */       {
/*  952 */         charset.register(range.first + j, readString(range.first + j));
/*      */       }
/*  954 */       i += 1 + range.nLeft;
/*      */     }
/*  956 */     charset.range = ((CFFParser.Format1Charset.Range1[])ranges.toArray(new CFFParser.Format1Charset.Range1[0]));
/*  957 */     return charset;
/*      */   }
/*      */ 
/*      */   private Format2Charset readFormat2Charset(CFFDataInput dataInput, int format, int nGlyphs) throws IOException
/*      */   {
/*  962 */     Format2Charset charset = new Format2Charset(null);
/*  963 */     charset.format = format;
/*  964 */     charset.range = new CFFParser.Format2Charset.Range2[0];
/*  965 */     for (int i = 0; i < nGlyphs - 1; )
/*      */     {
/*  967 */       CFFParser.Format2Charset.Range2[] newRange = new CFFParser.Format2Charset.Range2[charset.range.length + 1];
/*  968 */       System.arraycopy(charset.range, 0, newRange, 0, charset.range.length);
/*  969 */       charset.range = newRange;
/*  970 */       CFFParser.Format2Charset.Range2 range = new CFFParser.Format2Charset.Range2(null);
/*  971 */       range.first = dataInput.readSID();
/*  972 */       range.nLeft = dataInput.readCard16();
/*  973 */       charset.range[(charset.range.length - 1)] = range;
/*  974 */       for (int j = 0; j < 1 + range.nLeft; j++)
/*      */       {
/*  976 */         charset.register(range.first + j, readString(range.first + j));
/*      */       }
/*  978 */       i += 1 + range.nLeft;
/*      */     }
/*  980 */     return charset;
/*      */   }
/*      */ 
/*      */   private static class Format2Charset extends CFFParser.EmbeddedCharset
/*      */   {
/*      */     private int format;
/*      */     private Range2[] range;
/*      */ 
/*      */     public String toString()
/*      */     {
/* 1269 */       return getClass().getName() + "[format=" + this.format + ", range=" + Arrays.toString(this.range) + "]";
/*      */     }
/*      */ 
/*      */     private static class Range2
/*      */     {
/*      */       private int first;
/*      */       private int nLeft;
/*      */ 
/*      */       public String toString()
/*      */       {
/* 1283 */         return getClass().getName() + "[first=" + this.first + ", nLeft=" + this.nLeft + "]";
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class Format1Charset extends CFFParser.EmbeddedCharset
/*      */   {
/*      */     private int format;
/*      */     private Range1[] range;
/*      */ 
/*      */     public String toString()
/*      */     {
/* 1239 */       return getClass().getName() + "[format=" + this.format + ", range=" + Arrays.toString(this.range) + "]";
/*      */     }
/*      */ 
/*      */     private static class Range1
/*      */     {
/*      */       private int first;
/*      */       private int nLeft;
/*      */ 
/*      */       public String toString()
/*      */       {
/* 1253 */         return getClass().getName() + "[first=" + this.first + ", nLeft=" + this.nLeft + "]";
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class Format0Charset extends CFFParser.EmbeddedCharset
/*      */   {
/*      */     private int format;
/*      */     private int[] glyph;
/*      */ 
/*      */     public String toString()
/*      */     {
/* 1224 */       return getClass().getName() + "[format=" + this.format + ", glyph=" + Arrays.toString(this.glyph) + "]";
/*      */     }
/*      */   }
/*      */ 
/*      */   static abstract class EmbeddedCharset extends CFFCharset
/*      */   {
/*      */     public boolean isFontSpecific()
/*      */     {
/* 1209 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class Format1Encoding extends CFFParser.EmbeddedEncoding
/*      */   {
/*      */     private int format;
/*      */     private int nRanges;
/*      */     private Range1[] range;
/*      */ 
/*      */     public String toString()
/*      */     {
/* 1181 */       return getClass().getName() + "[format=" + this.format + ", nRanges=" + this.nRanges + ", range=" + Arrays.toString(this.range) + ", supplement=" + Arrays.toString(CFFParser.EmbeddedEncoding.access$2200(this)) + "]";
/*      */     }
/*      */ 
/*      */     private static class Range1
/*      */     {
/*      */       private int first;
/*      */       private int nLeft;
/*      */ 
/*      */       public String toString()
/*      */       {
/* 1196 */         return getClass().getName() + "[first=" + this.first + ", nLeft=" + this.nLeft + "]";
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class Format0Encoding extends CFFParser.EmbeddedEncoding
/*      */   {
/*      */     private int format;
/*      */     private int nCodes;
/*      */     private int[] code;
/*      */ 
/*      */     public String toString()
/*      */     {
/* 1164 */       return getClass().getName() + "[format=" + this.format + ", nCodes=" + this.nCodes + ", code=" + Arrays.toString(this.code) + ", supplement=" + Arrays.toString(CFFParser.EmbeddedEncoding.access$2200(this)) + "]";
/*      */     }
/*      */   }
/*      */ 
/*      */   static abstract class EmbeddedEncoding extends CFFEncoding
/*      */   {
/*      */     private int nSups;
/*      */     private Supplement[] supplement;
/*      */ 
/*      */     public boolean isFontSpecific()
/*      */     {
/* 1114 */       return true;
/*      */     }
/*      */ 
/*      */     List<Supplement> getSupplements()
/*      */     {
/* 1119 */       if (this.supplement == null)
/*      */       {
/* 1121 */         return Collections.emptyList();
/*      */       }
/* 1123 */       return Arrays.asList(this.supplement);
/*      */     }
/*      */ 
/*      */     static class Supplement
/*      */     {
/*      */       private int code;
/*      */       private int glyph;
/*      */ 
/*      */       int getCode()
/*      */       {
/* 1136 */         return this.code;
/*      */       }
/*      */ 
/*      */       int getGlyph()
/*      */       {
/* 1141 */         return this.glyph;
/*      */       }
/*      */ 
/*      */       public String toString()
/*      */       {
/* 1147 */         return getClass().getName() + "[code=" + this.code + ", glyph=" + this.glyph + "]";
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class DictData
/*      */   {
/*      */     private List<Entry> entries;
/*      */ 
/*      */     private DictData()
/*      */     {
/* 1007 */       this.entries = null;
/*      */     }
/*      */ 
/*      */     public Entry getEntry(CFFOperator.Key key) {
/* 1011 */       return getEntry(CFFOperator.getOperator(key));
/*      */     }
/*      */ 
/*      */     public Entry getEntry(String name)
/*      */     {
/* 1016 */       return getEntry(CFFOperator.getOperator(name));
/*      */     }
/*      */ 
/*      */     private Entry getEntry(CFFOperator operator)
/*      */     {
/* 1021 */       for (Entry entry : this.entries)
/*      */       {
/* 1024 */         if ((entry != null) && (entry.operator != null) && (entry.operator.equals(operator)))
/*      */         {
/* 1026 */           return entry;
/*      */         }
/*      */       }
/* 1029 */       return null;
/*      */     }
/*      */ 
/*      */     public String toString()
/*      */     {
/* 1037 */       return getClass().getName() + "[entries=" + this.entries + "]";
/*      */     }
/*      */ 
/*      */     private static class Entry
/*      */     {
/* 1045 */       private List<Number> operands = new ArrayList();
/* 1046 */       private CFFOperator operator = null;
/*      */ 
/*      */       public Number getNumber(int index)
/*      */       {
/* 1050 */         return (Number)this.operands.get(index);
/*      */       }
/*      */ 
/*      */       public Boolean getBoolean(int index)
/*      */       {
/* 1055 */         Number operand = (Number)this.operands.get(index);
/* 1056 */         if ((operand instanceof Integer))
/*      */         {
/* 1058 */           switch (operand.intValue())
/*      */           {
/*      */           case 0:
/* 1061 */             return Boolean.FALSE;
/*      */           case 1:
/* 1063 */             return Boolean.TRUE;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1068 */         throw new IllegalArgumentException();
/*      */       }
/*      */ 
/*      */       public Integer getSID(int index)
/*      */       {
/* 1074 */         Number operand = (Number)this.operands.get(index);
/* 1075 */         if ((operand instanceof Integer))
/*      */         {
/* 1077 */           return (Integer)operand;
/*      */         }
/* 1079 */         throw new IllegalArgumentException();
/*      */       }
/*      */ 
/*      */       public List<Number> getArray()
/*      */       {
/* 1085 */         return this.operands;
/*      */       }
/*      */ 
/*      */       public List<Number> getDelta()
/*      */       {
/* 1091 */         return this.operands;
/*      */       }
/*      */ 
/*      */       public String toString()
/*      */       {
/* 1097 */         return getClass().getName() + "[operands=" + this.operands + ", operator=" + this.operator + "]";
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class Header
/*      */   {
/*      */     private int major;
/*      */     private int minor;
/*      */     private int hdrSize;
/*      */     private int offSize;
/*      */ 
/*      */     public String toString()
/*      */     {
/*  996 */       return getClass().getName() + "[major=" + this.major + ", minor=" + this.minor + ", hdrSize=" + this.hdrSize + ", offSize=" + this.offSize + "]";
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class Format0FDSelect extends CIDKeyedFDSelect
/*      */   {
/*      */     private int format;
/*      */     private int[] fds;
/*      */ 
/*      */     private Format0FDSelect(CFFFontROS owner)
/*      */     {
/*  866 */       super();
/*      */     }
/*      */ 
/*      */     public int getFd(int glyph)
/*      */     {
/*  877 */       Map charString = this.owner.getCharStringsDict();
/*  878 */       Set keys = charString.keySet();
/*      */ 
/*  880 */       for (Iterator i$ = this.owner.getMappings().iterator(); i$.hasNext(); ) { mapping = (CFFFont.Mapping)i$.next();
/*      */ 
/*  882 */         if ((mapping.getSID() == glyph) && (charString.containsKey(mapping.getName())))
/*      */         {
/*  884 */           index = 0;
/*  885 */           for (String str : keys)
/*      */           {
/*  887 */             if (mapping.getName().equals(str))
/*      */             {
/*  889 */               return this.fds[index];
/*      */             }
/*  891 */             index++;
/*      */           }
/*      */         }
/*      */       }
/*      */       CFFFont.Mapping mapping;
/*      */       int index;
/*  895 */       return -1;
/*      */     }
/*      */ 
/*      */     public String toString()
/*      */     {
/*  901 */       return getClass().getName() + "[format=" + this.format + ", fds=" + Arrays.toString(this.fds) + "]";
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class Range3
/*      */   {
/*      */     private int first;
/*      */     private int fd;
/*      */ 
/*      */     public String toString()
/*      */     {
/*  852 */       return getClass().getName() + "[first=" + this.first + ", fd=" + this.fd + "]";
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class Format3FDSelect extends CIDKeyedFDSelect
/*      */   {
/*      */     private int format;
/*      */     private int nbRanges;
/*      */     private CFFParser.Range3[] range3;
/*      */     private int sentinel;
/*      */ 
/*      */     private Format3FDSelect(CFFFontROS owner)
/*      */     {
/*  790 */       super();
/*      */     }
/*      */ 
/*      */     public int getFd(int glyph)
/*      */     {
/*  801 */       for (int i = 0; i < this.nbRanges; i++)
/*      */       {
/*  803 */         if (this.range3[i].first >= glyph)
/*      */         {
/*  805 */           if (i + 1 < this.nbRanges)
/*      */           {
/*  807 */             if (this.range3[(i + 1)].first > glyph)
/*      */             {
/*  809 */               return this.range3[i].fd;
/*      */             }
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*  819 */             if (this.sentinel > glyph)
/*      */             {
/*  821 */               return this.range3[i].fd;
/*      */             }
/*      */ 
/*  825 */             return -1;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  830 */       return 0;
/*      */     }
/*      */ 
/*      */     public String toString()
/*      */     {
/*  836 */       return getClass().getName() + "[format=" + this.format + " nbRanges=" + this.nbRanges + ", range3=" + Arrays.toString(this.range3) + " sentinel=" + this.sentinel + "]";
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.cff.CFFParser
 * JD-Core Version:    0.6.2
 */