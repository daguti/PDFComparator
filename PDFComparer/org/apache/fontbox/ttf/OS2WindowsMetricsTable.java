/*     */ package org.apache.fontbox.ttf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ public class OS2WindowsMetricsTable extends TTFTable
/*     */ {
/*     */   public static final int WEIGHT_CLASS_THIN = 100;
/*     */   public static final int WEIGHT_CLASS_ULTRA_LIGHT = 200;
/*     */   public static final int WEIGHT_CLASS_LIGHT = 300;
/*     */   public static final int WEIGHT_CLASS_NORMAL = 400;
/*     */   public static final int WEIGHT_CLASS_MEDIUM = 500;
/*     */   public static final int WEIGHT_CLASS_SEMI_BOLD = 600;
/*     */   public static final int WEIGHT_CLASS_BOLD = 700;
/*     */   public static final int WEIGHT_CLASS_EXTRA_BOLD = 800;
/*     */   public static final int WEIGHT_CLASS_BLACK = 900;
/*     */   public static final int WIDTH_CLASS_ULTRA_CONDENSED = 1;
/*     */   public static final int WIDTH_CLASS_EXTRA_CONDENSED = 2;
/*     */   public static final int WIDTH_CLASS_CONDENSED = 3;
/*     */   public static final int WIDTH_CLASS_SEMI_CONDENSED = 4;
/*     */   public static final int WIDTH_CLASS_MEDIUM = 5;
/*     */   public static final int WIDTH_CLASS_SEMI_EXPANDED = 6;
/*     */   public static final int WIDTH_CLASS_EXPANDED = 7;
/*     */   public static final int WIDTH_CLASS_EXTRA_EXPANDED = 8;
/*     */   public static final int WIDTH_CLASS_ULTRA_EXPANDED = 9;
/*     */   public static final int FAMILY_CLASS_NO_CLASSIFICATION = 0;
/*     */   public static final int FAMILY_CLASS_OLDSTYLE_SERIFS = 1;
/*     */   public static final int FAMILY_CLASS_TRANSITIONAL_SERIFS = 2;
/*     */   public static final int FAMILY_CLASS_MODERN_SERIFS = 3;
/*     */   public static final int FAMILY_CLASS_CLAREDON_SERIFS = 4;
/*     */   public static final int FAMILY_CLASS_SLAB_SERIFS = 5;
/*     */   public static final int FAMILY_CLASS_FREEFORM_SERIFS = 7;
/*     */   public static final int FAMILY_CLASS_SANS_SERIF = 8;
/*     */   public static final int FAMILY_CLASS_ORNAMENTALS = 9;
/*     */   public static final int FAMILY_CLASS_SCRIPTS = 10;
/*     */   public static final int FAMILY_CLASS_SYMBOLIC = 12;
/*     */   private int version;
/*     */   private short averageCharWidth;
/*     */   private int weightClass;
/*     */   private int widthClass;
/*     */   private short fsType;
/*     */   private short subscriptXSize;
/*     */   private short subscriptYSize;
/*     */   private short subscriptXOffset;
/*     */   private short subscriptYOffset;
/*     */   private short superscriptXSize;
/*     */   private short superscriptYSize;
/*     */   private short superscriptXOffset;
/*     */   private short superscriptYOffset;
/*     */   private short strikeoutSize;
/*     */   private short strikeoutPosition;
/*     */   private int familyClass;
/*     */   private int familySubClass;
/* 694 */   private byte[] panose = new byte[10];
/*     */   private long unicodeRange1;
/*     */   private long unicodeRange2;
/*     */   private long unicodeRange3;
/*     */   private long unicodeRange4;
/* 699 */   private String achVendId = "XXXX";
/*     */   private int fsSelection;
/*     */   private int firstCharIndex;
/*     */   private int lastCharIndex;
/*     */   private int typoAscender;
/*     */   private int typoDescender;
/*     */   private int typeLineGap;
/*     */   private int winAscent;
/*     */   private int winDescent;
/* 708 */   private long codePageRange1 = -1L;
/* 709 */   private long codePageRange2 = -1L;
/*     */   public static final String TAG = "OS/2";
/*     */ 
/*     */   public String getAchVendId()
/*     */   {
/* 154 */     return this.achVendId;
/*     */   }
/*     */ 
/*     */   public void setAchVendId(String achVendIdValue)
/*     */   {
/* 162 */     this.achVendId = achVendIdValue;
/*     */   }
/*     */ 
/*     */   public short getAverageCharWidth()
/*     */   {
/* 170 */     return this.averageCharWidth;
/*     */   }
/*     */ 
/*     */   public void setAverageCharWidth(short averageCharWidthValue)
/*     */   {
/* 178 */     this.averageCharWidth = averageCharWidthValue;
/*     */   }
/*     */ 
/*     */   public long getCodePageRange1()
/*     */   {
/* 186 */     return this.codePageRange1;
/*     */   }
/*     */ 
/*     */   public void setCodePageRange1(long codePageRange1Value)
/*     */   {
/* 194 */     this.codePageRange1 = codePageRange1Value;
/*     */   }
/*     */ 
/*     */   public long getCodePageRange2()
/*     */   {
/* 202 */     return this.codePageRange2;
/*     */   }
/*     */ 
/*     */   public void setCodePageRange2(long codePageRange2Value)
/*     */   {
/* 210 */     this.codePageRange2 = codePageRange2Value;
/*     */   }
/*     */ 
/*     */   public int getFamilyClass()
/*     */   {
/* 218 */     return this.familyClass;
/*     */   }
/*     */ 
/*     */   public void setFamilyClass(int familyClassValue)
/*     */   {
/* 226 */     this.familyClass = familyClassValue;
/*     */   }
/*     */ 
/*     */   public int getFamilySubClass()
/*     */   {
/* 234 */     return this.familySubClass;
/*     */   }
/*     */ 
/*     */   public void setFamilySubClass(int familySubClassValue)
/*     */   {
/* 242 */     this.familySubClass = familySubClassValue;
/*     */   }
/*     */ 
/*     */   public int getFirstCharIndex()
/*     */   {
/* 250 */     return this.firstCharIndex;
/*     */   }
/*     */ 
/*     */   public void setFirstCharIndex(int firstCharIndexValue)
/*     */   {
/* 258 */     this.firstCharIndex = firstCharIndexValue;
/*     */   }
/*     */ 
/*     */   public int getFsSelection()
/*     */   {
/* 266 */     return this.fsSelection;
/*     */   }
/*     */ 
/*     */   public void setFsSelection(int fsSelectionValue)
/*     */   {
/* 274 */     this.fsSelection = fsSelectionValue;
/*     */   }
/*     */ 
/*     */   public short getFsType()
/*     */   {
/* 282 */     return this.fsType;
/*     */   }
/*     */ 
/*     */   public void setFsType(short fsTypeValue)
/*     */   {
/* 290 */     this.fsType = fsTypeValue;
/*     */   }
/*     */ 
/*     */   public int getLastCharIndex()
/*     */   {
/* 298 */     return this.lastCharIndex;
/*     */   }
/*     */ 
/*     */   public void setLastCharIndex(int lastCharIndexValue)
/*     */   {
/* 306 */     this.lastCharIndex = lastCharIndexValue;
/*     */   }
/*     */ 
/*     */   public byte[] getPanose()
/*     */   {
/* 314 */     return this.panose;
/*     */   }
/*     */ 
/*     */   public void setPanose(byte[] panoseValue)
/*     */   {
/* 322 */     this.panose = panoseValue;
/*     */   }
/*     */ 
/*     */   public short getStrikeoutPosition()
/*     */   {
/* 330 */     return this.strikeoutPosition;
/*     */   }
/*     */ 
/*     */   public void setStrikeoutPosition(short strikeoutPositionValue)
/*     */   {
/* 338 */     this.strikeoutPosition = strikeoutPositionValue;
/*     */   }
/*     */ 
/*     */   public short getStrikeoutSize()
/*     */   {
/* 346 */     return this.strikeoutSize;
/*     */   }
/*     */ 
/*     */   public void setStrikeoutSize(short strikeoutSizeValue)
/*     */   {
/* 354 */     this.strikeoutSize = strikeoutSizeValue;
/*     */   }
/*     */ 
/*     */   public short getSubscriptXOffset()
/*     */   {
/* 362 */     return this.subscriptXOffset;
/*     */   }
/*     */ 
/*     */   public void setSubscriptXOffset(short subscriptXOffsetValue)
/*     */   {
/* 370 */     this.subscriptXOffset = subscriptXOffsetValue;
/*     */   }
/*     */ 
/*     */   public short getSubscriptXSize()
/*     */   {
/* 378 */     return this.subscriptXSize;
/*     */   }
/*     */ 
/*     */   public void setSubscriptXSize(short subscriptXSizeValue)
/*     */   {
/* 386 */     this.subscriptXSize = subscriptXSizeValue;
/*     */   }
/*     */ 
/*     */   public short getSubscriptYOffset()
/*     */   {
/* 394 */     return this.subscriptYOffset;
/*     */   }
/*     */ 
/*     */   public void setSubscriptYOffset(short subscriptYOffsetValue)
/*     */   {
/* 402 */     this.subscriptYOffset = subscriptYOffsetValue;
/*     */   }
/*     */ 
/*     */   public short getSubscriptYSize()
/*     */   {
/* 410 */     return this.subscriptYSize;
/*     */   }
/*     */ 
/*     */   public void setSubscriptYSize(short subscriptYSizeValue)
/*     */   {
/* 418 */     this.subscriptYSize = subscriptYSizeValue;
/*     */   }
/*     */ 
/*     */   public short getSuperscriptXOffset()
/*     */   {
/* 426 */     return this.superscriptXOffset;
/*     */   }
/*     */ 
/*     */   public void setSuperscriptXOffset(short superscriptXOffsetValue)
/*     */   {
/* 434 */     this.superscriptXOffset = superscriptXOffsetValue;
/*     */   }
/*     */ 
/*     */   public short getSuperscriptXSize()
/*     */   {
/* 442 */     return this.superscriptXSize;
/*     */   }
/*     */ 
/*     */   public void setSuperscriptXSize(short superscriptXSizeValue)
/*     */   {
/* 450 */     this.superscriptXSize = superscriptXSizeValue;
/*     */   }
/*     */ 
/*     */   public short getSuperscriptYOffset()
/*     */   {
/* 458 */     return this.superscriptYOffset;
/*     */   }
/*     */ 
/*     */   public void setSuperscriptYOffset(short superscriptYOffsetValue)
/*     */   {
/* 466 */     this.superscriptYOffset = superscriptYOffsetValue;
/*     */   }
/*     */ 
/*     */   public short getSuperscriptYSize()
/*     */   {
/* 474 */     return this.superscriptYSize;
/*     */   }
/*     */ 
/*     */   public void setSuperscriptYSize(short superscriptYSizeValue)
/*     */   {
/* 482 */     this.superscriptYSize = superscriptYSizeValue;
/*     */   }
/*     */ 
/*     */   public int getTypeLineGap()
/*     */   {
/* 490 */     return this.typeLineGap;
/*     */   }
/*     */ 
/*     */   public void setTypeLineGap(int typeLineGapValue)
/*     */   {
/* 498 */     this.typeLineGap = typeLineGapValue;
/*     */   }
/*     */ 
/*     */   public int getTypoAscender()
/*     */   {
/* 506 */     return this.typoAscender;
/*     */   }
/*     */ 
/*     */   public void setTypoAscender(int typoAscenderValue)
/*     */   {
/* 514 */     this.typoAscender = typoAscenderValue;
/*     */   }
/*     */ 
/*     */   public int getTypoDescender()
/*     */   {
/* 522 */     return this.typoDescender;
/*     */   }
/*     */ 
/*     */   public void setTypoDescender(int typoDescenderValue)
/*     */   {
/* 530 */     this.typoDescender = typoDescenderValue;
/*     */   }
/*     */ 
/*     */   public long getUnicodeRange1()
/*     */   {
/* 538 */     return this.unicodeRange1;
/*     */   }
/*     */ 
/*     */   public void setUnicodeRange1(long unicodeRange1Value)
/*     */   {
/* 546 */     this.unicodeRange1 = unicodeRange1Value;
/*     */   }
/*     */ 
/*     */   public long getUnicodeRange2()
/*     */   {
/* 554 */     return this.unicodeRange2;
/*     */   }
/*     */ 
/*     */   public void setUnicodeRange2(long unicodeRange2Value)
/*     */   {
/* 562 */     this.unicodeRange2 = unicodeRange2Value;
/*     */   }
/*     */ 
/*     */   public long getUnicodeRange3()
/*     */   {
/* 570 */     return this.unicodeRange3;
/*     */   }
/*     */ 
/*     */   public void setUnicodeRange3(long unicodeRange3Value)
/*     */   {
/* 578 */     this.unicodeRange3 = unicodeRange3Value;
/*     */   }
/*     */ 
/*     */   public long getUnicodeRange4()
/*     */   {
/* 586 */     return this.unicodeRange4;
/*     */   }
/*     */ 
/*     */   public void setUnicodeRange4(long unicodeRange4Value)
/*     */   {
/* 594 */     this.unicodeRange4 = unicodeRange4Value;
/*     */   }
/*     */ 
/*     */   public int getVersion()
/*     */   {
/* 602 */     return this.version;
/*     */   }
/*     */ 
/*     */   public void setVersion(int versionValue)
/*     */   {
/* 610 */     this.version = versionValue;
/*     */   }
/*     */ 
/*     */   public int getWeightClass()
/*     */   {
/* 618 */     return this.weightClass;
/*     */   }
/*     */ 
/*     */   public void setWeightClass(int weightClassValue)
/*     */   {
/* 626 */     this.weightClass = weightClassValue;
/*     */   }
/*     */ 
/*     */   public int getWidthClass()
/*     */   {
/* 634 */     return this.widthClass;
/*     */   }
/*     */ 
/*     */   public void setWidthClass(int widthClassValue)
/*     */   {
/* 642 */     this.widthClass = widthClassValue;
/*     */   }
/*     */ 
/*     */   public int getWinAscent()
/*     */   {
/* 650 */     return this.winAscent;
/*     */   }
/*     */ 
/*     */   public void setWinAscent(int winAscentValue)
/*     */   {
/* 658 */     this.winAscent = winAscentValue;
/*     */   }
/*     */ 
/*     */   public int getWinDescent()
/*     */   {
/* 666 */     return this.winDescent;
/*     */   }
/*     */ 
/*     */   public void setWinDescent(int winDescentValue)
/*     */   {
/* 674 */     this.winDescent = winDescentValue;
/*     */   }
/*     */ 
/*     */   public void initData(TrueTypeFont ttf, TTFDataStream data)
/*     */     throws IOException
/*     */   {
/* 725 */     this.version = data.readUnsignedShort();
/* 726 */     this.averageCharWidth = data.readSignedShort();
/* 727 */     this.weightClass = data.readUnsignedShort();
/* 728 */     this.widthClass = data.readUnsignedShort();
/* 729 */     this.fsType = data.readSignedShort();
/* 730 */     this.subscriptXSize = data.readSignedShort();
/* 731 */     this.subscriptYSize = data.readSignedShort();
/* 732 */     this.subscriptXOffset = data.readSignedShort();
/* 733 */     this.subscriptYOffset = data.readSignedShort();
/* 734 */     this.superscriptXSize = data.readSignedShort();
/* 735 */     this.superscriptYSize = data.readSignedShort();
/* 736 */     this.superscriptXOffset = data.readSignedShort();
/* 737 */     this.superscriptYOffset = data.readSignedShort();
/* 738 */     this.strikeoutSize = data.readSignedShort();
/* 739 */     this.strikeoutPosition = data.readSignedShort();
/* 740 */     this.familyClass = data.readUnsignedByte();
/* 741 */     this.familySubClass = data.readUnsignedByte();
/* 742 */     this.panose = data.read(10);
/* 743 */     this.unicodeRange1 = data.readUnsignedInt();
/* 744 */     this.unicodeRange2 = data.readUnsignedInt();
/* 745 */     this.unicodeRange3 = data.readUnsignedInt();
/* 746 */     this.unicodeRange4 = data.readUnsignedInt();
/* 747 */     this.achVendId = data.readString(4);
/* 748 */     this.fsSelection = data.readUnsignedShort();
/* 749 */     this.firstCharIndex = data.readUnsignedShort();
/* 750 */     this.lastCharIndex = data.readUnsignedShort();
/* 751 */     this.typoAscender = data.readSignedShort();
/* 752 */     this.typoDescender = data.readSignedShort();
/* 753 */     this.typeLineGap = data.readSignedShort();
/* 754 */     this.winAscent = data.readUnsignedShort();
/* 755 */     this.winDescent = data.readUnsignedShort();
/* 756 */     if (this.version >= 1)
/*     */     {
/* 758 */       this.codePageRange1 = data.readUnsignedInt();
/* 759 */       this.codePageRange2 = data.readUnsignedInt();
/*     */     }
/* 761 */     this.initialized = true;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.ttf.OS2WindowsMetricsTable
 * JD-Core Version:    0.6.2
 */