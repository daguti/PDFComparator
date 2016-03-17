/*      */ package org.apache.pdfbox.pdmodel.documentinterchange.taggedpdf;
/*      */ 
/*      */ import org.apache.pdfbox.cos.COSArray;
/*      */ import org.apache.pdfbox.cos.COSBase;
/*      */ import org.apache.pdfbox.cos.COSDictionary;
/*      */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*      */ import org.apache.pdfbox.pdmodel.graphics.color.PDGamma;
/*      */ 
/*      */ public class PDLayoutAttributeObject extends PDStandardAttributeObject
/*      */ {
/*      */   public static final String OWNER_LAYOUT = "Layout";
/*      */   private static final String PLACEMENT = "Placement";
/*      */   private static final String WRITING_MODE = "WritingMode";
/*      */   private static final String BACKGROUND_COLOR = "BackgroundColor";
/*      */   private static final String BORDER_COLOR = "BorderColor";
/*      */   private static final String BORDER_STYLE = "BorderStyle";
/*      */   private static final String BORDER_THICKNESS = "BorderThickness";
/*      */   private static final String PADDING = "Padding";
/*      */   private static final String COLOR = "Color";
/*      */   private static final String SPACE_BEFORE = "SpaceBefore";
/*      */   private static final String SPACE_AFTER = "SpaceAfter";
/*      */   private static final String START_INDENT = "StartIndent";
/*      */   private static final String END_INDENT = "EndIndent";
/*      */   private static final String TEXT_INDENT = "TextIndent";
/*      */   private static final String TEXT_ALIGN = "TextAlign";
/*      */   private static final String BBOX = "BBox";
/*      */   private static final String WIDTH = "Width";
/*      */   private static final String HEIGHT = "Height";
/*      */   private static final String BLOCK_ALIGN = "BlockAlign";
/*      */   private static final String INLINE_ALIGN = "InlineAlign";
/*      */   private static final String T_BORDER_STYLE = "TBorderStyle";
/*      */   private static final String T_PADDING = "TPadding";
/*      */   private static final String BASELINE_SHIFT = "BaselineShift";
/*      */   private static final String LINE_HEIGHT = "LineHeight";
/*      */   private static final String TEXT_DECORATION_COLOR = "TextDecorationColor";
/*      */   private static final String TEXT_DECORATION_THICKNESS = "TextDecorationThickness";
/*      */   private static final String TEXT_DECORATION_TYPE = "TextDecorationType";
/*      */   private static final String RUBY_ALIGN = "RubyAlign";
/*      */   private static final String RUBY_POSITION = "RubyPosition";
/*      */   private static final String GLYPH_ORIENTATION_VERTICAL = "GlyphOrientationVertical";
/*      */   private static final String COLUMN_COUNT = "ColumnCount";
/*      */   private static final String COLUMN_GAP = "ColumnGap";
/*      */   private static final String COLUMN_WIDTHS = "ColumnWidths";
/*      */   public static final String PLACEMENT_BLOCK = "Block";
/*      */   public static final String PLACEMENT_INLINE = "Inline";
/*      */   public static final String PLACEMENT_BEFORE = "Before";
/*      */   public static final String PLACEMENT_START = "Start";
/*      */   public static final String PLACEMENT_END = "End";
/*      */   public static final String WRITING_MODE_LRTB = "LrTb";
/*      */   public static final String WRITING_MODE_RLTB = "RlTb";
/*      */   public static final String WRITING_MODE_TBRL = "TbRl";
/*      */   public static final String BORDER_STYLE_NONE = "None";
/*      */   public static final String BORDER_STYLE_HIDDEN = "Hidden";
/*      */   public static final String BORDER_STYLE_DOTTED = "Dotted";
/*      */   public static final String BORDER_STYLE_DASHED = "Dashed";
/*      */   public static final String BORDER_STYLE_SOLID = "Solid";
/*      */   public static final String BORDER_STYLE_DOUBLE = "Double";
/*      */   public static final String BORDER_STYLE_GROOVE = "Groove";
/*      */   public static final String BORDER_STYLE_RIDGE = "Ridge";
/*      */   public static final String BORDER_STYLE_INSET = "Inset";
/*      */   public static final String BORDER_STYLE_OUTSET = "Outset";
/*      */   public static final String TEXT_ALIGN_START = "Start";
/*      */   public static final String TEXT_ALIGN_CENTER = "Center";
/*      */   public static final String TEXT_ALIGN_END = "End";
/*      */   public static final String TEXT_ALIGN_JUSTIFY = "Justify";
/*      */   public static final String WIDTH_AUTO = "Auto";
/*      */   public static final String HEIGHT_AUTO = "Auto";
/*      */   public static final String BLOCK_ALIGN_BEFORE = "Before";
/*      */   public static final String BLOCK_ALIGN_MIDDLE = "Middle";
/*      */   public static final String BLOCK_ALIGN_AFTER = "After";
/*      */   public static final String BLOCK_ALIGN_JUSTIFY = "Justify";
/*      */   public static final String INLINE_ALIGN_START = "Start";
/*      */   public static final String INLINE_ALIGN_CENTER = "Center";
/*      */   public static final String INLINE_ALIGN_END = "End";
/*      */   public static final String LINE_HEIGHT_NORMAL = "Normal";
/*      */   public static final String LINE_HEIGHT_AUTO = "Auto";
/*      */   public static final String TEXT_DECORATION_TYPE_NONE = "None";
/*      */   public static final String TEXT_DECORATION_TYPE_UNDERLINE = "Underline";
/*      */   public static final String TEXT_DECORATION_TYPE_OVERLINE = "Overline";
/*      */   public static final String TEXT_DECORATION_TYPE_LINE_THROUGH = "LineThrough";
/*      */   public static final String RUBY_ALIGN_START = "Start";
/*      */   public static final String RUBY_ALIGN_CENTER = "Center";
/*      */   public static final String RUBY_ALIGN_END = "End";
/*      */   public static final String RUBY_ALIGN_JUSTIFY = "Justify";
/*      */   public static final String RUBY_ALIGN_DISTRIBUTE = "Distribute";
/*      */   public static final String RUBY_POSITION_BEFORE = "Before";
/*      */   public static final String RUBY_POSITION_AFTER = "After";
/*      */   public static final String RUBY_POSITION_WARICHU = "Warichu";
/*      */   public static final String RUBY_POSITION_INLINE = "Inline";
/*      */   public static final String GLYPH_ORIENTATION_VERTICAL_AUTO = "Auto";
/*      */   public static final String GLYPH_ORIENTATION_VERTICAL_MINUS_180_DEGREES = "-180";
/*      */   public static final String GLYPH_ORIENTATION_VERTICAL_MINUS_90_DEGREES = "-90";
/*      */   public static final String GLYPH_ORIENTATION_VERTICAL_ZERO_DEGREES = "0";
/*      */   public static final String GLYPH_ORIENTATION_VERTICAL_90_DEGREES = "90";
/*      */   public static final String GLYPH_ORIENTATION_VERTICAL_180_DEGREES = "180";
/*      */   public static final String GLYPH_ORIENTATION_VERTICAL_270_DEGREES = "270";
/*      */   public static final String GLYPH_ORIENTATION_VERTICAL_360_DEGREES = "360";
/*      */ 
/*      */   public PDLayoutAttributeObject()
/*      */   {
/*  358 */     setOwner("Layout");
/*      */   }
/*      */ 
/*      */   public PDLayoutAttributeObject(COSDictionary dictionary)
/*      */   {
/*  368 */     super(dictionary);
/*      */   }
/*      */ 
/*      */   public String getPlacement()
/*      */   {
/*  381 */     return getName("Placement", "Inline");
/*      */   }
/*      */ 
/*      */   public void setPlacement(String placement)
/*      */   {
/*  399 */     setName("Placement", placement);
/*      */   }
/*      */ 
/*      */   public String getWritingMode()
/*      */   {
/*  410 */     return getName("WritingMode", "LrTb");
/*      */   }
/*      */ 
/*      */   public void setWritingMode(String writingMode)
/*      */   {
/*  425 */     setName("WritingMode", writingMode);
/*      */   }
/*      */ 
/*      */   public PDGamma getBackgroundColor()
/*      */   {
/*  435 */     return getColor("BackgroundColor");
/*      */   }
/*      */ 
/*      */   public void setBackgroundColor(PDGamma backgroundColor)
/*      */   {
/*  445 */     setColor("BackgroundColor", backgroundColor);
/*      */   }
/*      */ 
/*      */   public Object getBorderColors()
/*      */   {
/*  456 */     return getColorOrFourColors("BorderColor");
/*      */   }
/*      */ 
/*      */   public void setAllBorderColors(PDGamma borderColor)
/*      */   {
/*  466 */     setColor("BorderColor", borderColor);
/*      */   }
/*      */ 
/*      */   public void setBorderColors(PDFourColours borderColors)
/*      */   {
/*  476 */     setFourColors("BorderColor", borderColors);
/*      */   }
/*      */ 
/*      */   public Object getBorderStyle()
/*      */   {
/*  487 */     return getNameOrArrayOfName("BorderStyle", "None");
/*      */   }
/*      */ 
/*      */   public void setAllBorderStyles(String borderStyle)
/*      */   {
/*  510 */     setName("BorderStyle", borderStyle);
/*      */   }
/*      */ 
/*      */   public void setBorderStyles(String[] borderStyles)
/*      */   {
/*  533 */     setArrayOfName("BorderStyle", borderStyles);
/*      */   }
/*      */ 
/*      */   public Object getBorderThickness()
/*      */   {
/*  543 */     return getNumberOrArrayOfNumber("BorderThickness", -1.0F);
/*      */   }
/*      */ 
/*      */   public void setAllBorderThicknesses(float borderThickness)
/*      */   {
/*  553 */     setNumber("BorderThickness", borderThickness);
/*      */   }
/*      */ 
/*      */   public void setAllBorderThicknesses(int borderThickness)
/*      */   {
/*  563 */     setNumber("BorderThickness", borderThickness);
/*      */   }
/*      */ 
/*      */   public void setBorderThicknesses(float[] borderThicknesses)
/*      */   {
/*  573 */     setArrayOfNumber("BorderThickness", borderThicknesses);
/*      */   }
/*      */ 
/*      */   public Object getPadding()
/*      */   {
/*  583 */     return getNumberOrArrayOfNumber("Padding", 0.0F);
/*      */   }
/*      */ 
/*      */   public void setAllPaddings(float padding)
/*      */   {
/*  593 */     setNumber("Padding", padding);
/*      */   }
/*      */ 
/*      */   public void setAllPaddings(int padding)
/*      */   {
/*  603 */     setNumber("Padding", padding);
/*      */   }
/*      */ 
/*      */   public void setPaddings(float[] paddings)
/*      */   {
/*  613 */     setArrayOfNumber("Padding", paddings);
/*      */   }
/*      */ 
/*      */   public PDGamma getColor()
/*      */   {
/*  624 */     return getColor("Color");
/*      */   }
/*      */ 
/*      */   public void setColor(PDGamma color)
/*      */   {
/*  635 */     setColor("Color", color);
/*      */   }
/*      */ 
/*      */   public float getSpaceBefore()
/*      */   {
/*  646 */     return getNumber("SpaceBefore", 0.0F);
/*      */   }
/*      */ 
/*      */   public void setSpaceBefore(float spaceBefore)
/*      */   {
/*  657 */     setNumber("SpaceBefore", spaceBefore);
/*      */   }
/*      */ 
/*      */   public void setSpaceBefore(int spaceBefore)
/*      */   {
/*  668 */     setNumber("SpaceBefore", spaceBefore);
/*      */   }
/*      */ 
/*      */   public float getSpaceAfter()
/*      */   {
/*  679 */     return getNumber("SpaceAfter", 0.0F);
/*      */   }
/*      */ 
/*      */   public void setSpaceAfter(float spaceAfter)
/*      */   {
/*  690 */     setNumber("SpaceAfter", spaceAfter);
/*      */   }
/*      */ 
/*      */   public void setSpaceAfter(int spaceAfter)
/*      */   {
/*  701 */     setNumber("SpaceAfter", spaceAfter);
/*      */   }
/*      */ 
/*      */   public float getStartIndent()
/*      */   {
/*  712 */     return getNumber("StartIndent", 0.0F);
/*      */   }
/*      */ 
/*      */   public void setStartIndent(float startIndent)
/*      */   {
/*  723 */     setNumber("StartIndent", startIndent);
/*      */   }
/*      */ 
/*      */   public void setStartIndent(int startIndent)
/*      */   {
/*  734 */     setNumber("StartIndent", startIndent);
/*      */   }
/*      */ 
/*      */   public float getEndIndent()
/*      */   {
/*  746 */     return getNumber("EndIndent", 0.0F);
/*      */   }
/*      */ 
/*      */   public void setEndIndent(float endIndent)
/*      */   {
/*  757 */     setNumber("EndIndent", endIndent);
/*      */   }
/*      */ 
/*      */   public void setEndIndent(int endIndent)
/*      */   {
/*  768 */     setNumber("EndIndent", endIndent);
/*      */   }
/*      */ 
/*      */   public float getTextIndent()
/*      */   {
/*  780 */     return getNumber("TextIndent", 0.0F);
/*      */   }
/*      */ 
/*      */   public void setTextIndent(float textIndent)
/*      */   {
/*  792 */     setNumber("TextIndent", textIndent);
/*      */   }
/*      */ 
/*      */   public void setTextIndent(int textIndent)
/*      */   {
/*  804 */     setNumber("TextIndent", textIndent);
/*      */   }
/*      */ 
/*      */   public String getTextAlign()
/*      */   {
/*  816 */     return getName("TextAlign", "Start");
/*      */   }
/*      */ 
/*      */   public void setTextAlign(String textIndent)
/*      */   {
/*  834 */     setName("TextAlign", textIndent);
/*      */   }
/*      */ 
/*      */   public PDRectangle getBBox()
/*      */   {
/*  844 */     COSArray array = (COSArray)getCOSDictionary().getDictionaryObject("BBox");
/*      */ 
/*  846 */     if (array != null)
/*      */     {
/*  848 */       return new PDRectangle(array);
/*      */     }
/*  850 */     return null;
/*      */   }
/*      */ 
/*      */   public void setBBox(PDRectangle bbox)
/*      */   {
/*  860 */     String name = "BBox";
/*  861 */     COSBase oldValue = getCOSDictionary().getDictionaryObject(name);
/*  862 */     getCOSDictionary().setItem(name, bbox);
/*  863 */     COSBase newValue = bbox == null ? null : bbox.getCOSObject();
/*  864 */     potentiallyNotifyChanged(oldValue, newValue);
/*      */   }
/*      */ 
/*      */   public Object getWidth()
/*      */   {
/*  876 */     return getNumberOrName("Width", "Auto");
/*      */   }
/*      */ 
/*      */   public void setWidthAuto()
/*      */   {
/*  885 */     setName("Width", "Auto");
/*      */   }
/*      */ 
/*      */   public void setWidth(float width)
/*      */   {
/*  896 */     setNumber("Width", width);
/*      */   }
/*      */ 
/*      */   public void setWidth(int width)
/*      */   {
/*  907 */     setNumber("Width", width);
/*      */   }
/*      */ 
/*      */   public Object getHeight()
/*      */   {
/*  919 */     return getNumberOrName("Height", "Auto");
/*      */   }
/*      */ 
/*      */   public void setHeightAuto()
/*      */   {
/*  928 */     setName("Height", "Auto");
/*      */   }
/*      */ 
/*      */   public void setHeight(float height)
/*      */   {
/*  939 */     setNumber("Height", height);
/*      */   }
/*      */ 
/*      */   public void setHeight(int height)
/*      */   {
/*  950 */     setNumber("Height", height);
/*      */   }
/*      */ 
/*      */   public String getBlockAlign()
/*      */   {
/*  962 */     return getName("BlockAlign", "Before");
/*      */   }
/*      */ 
/*      */   public void setBlockAlign(String blockAlign)
/*      */   {
/*  979 */     setName("BlockAlign", blockAlign);
/*      */   }
/*      */ 
/*      */   public String getInlineAlign()
/*      */   {
/*  991 */     return getName("InlineAlign", "Start");
/*      */   }
/*      */ 
/*      */   public void setInlineAlign(String inlineAlign)
/*      */   {
/* 1007 */     setName("InlineAlign", inlineAlign);
/*      */   }
/*      */ 
/*      */   public Object getTBorderStyle()
/*      */   {
/* 1018 */     return getNameOrArrayOfName("TBorderStyle", "None");
/*      */   }
/*      */ 
/*      */   public void setAllTBorderStyles(String tBorderStyle)
/*      */   {
/* 1041 */     setName("TBorderStyle", tBorderStyle);
/*      */   }
/*      */ 
/*      */   public void setTBorderStyles(String[] tBorderStyles)
/*      */   {
/* 1064 */     setArrayOfName("TBorderStyle", tBorderStyles);
/*      */   }
/*      */ 
/*      */   public Object getTPadding()
/*      */   {
/* 1076 */     return getNumberOrArrayOfNumber("TPadding", 0.0F);
/*      */   }
/*      */ 
/*      */   public void setAllTPaddings(float tPadding)
/*      */   {
/* 1086 */     setNumber("TPadding", tPadding);
/*      */   }
/*      */ 
/*      */   public void setAllTPaddings(int tPadding)
/*      */   {
/* 1096 */     setNumber("TPadding", tPadding);
/*      */   }
/*      */ 
/*      */   public void setTPaddings(float[] tPaddings)
/*      */   {
/* 1106 */     setArrayOfNumber("TPadding", tPaddings);
/*      */   }
/*      */ 
/*      */   public float getBaselineShift()
/*      */   {
/* 1118 */     return getNumber("BaselineShift", 0.0F);
/*      */   }
/*      */ 
/*      */   public void setBaselineShift(float baselineShift)
/*      */   {
/* 1129 */     setNumber("BaselineShift", baselineShift);
/*      */   }
/*      */ 
/*      */   public void setBaselineShift(int baselineShift)
/*      */   {
/* 1140 */     setNumber("BaselineShift", baselineShift);
/*      */   }
/*      */ 
/*      */   public Object getLineHeight()
/*      */   {
/* 1151 */     return getNumberOrName("LineHeight", "Normal");
/*      */   }
/*      */ 
/*      */   public void setLineHeightNormal()
/*      */   {
/* 1160 */     setName("LineHeight", "Normal");
/*      */   }
/*      */ 
/*      */   public void setLineHeightAuto()
/*      */   {
/* 1169 */     setName("LineHeight", "Auto");
/*      */   }
/*      */ 
/*      */   public void setLineHeight(float lineHeight)
/*      */   {
/* 1180 */     setNumber("LineHeight", lineHeight);
/*      */   }
/*      */ 
/*      */   public void setLineHeight(int lineHeight)
/*      */   {
/* 1191 */     setNumber("LineHeight", lineHeight);
/*      */   }
/*      */ 
/*      */   public PDGamma getTextDecorationColor()
/*      */   {
/* 1202 */     return getColor("TextDecorationColor");
/*      */   }
/*      */ 
/*      */   public void setTextDecorationColor(PDGamma textDecorationColor)
/*      */   {
/* 1213 */     setColor("TextDecorationColor", textDecorationColor);
/*      */   }
/*      */ 
/*      */   public float getTextDecorationThickness()
/*      */   {
/* 1224 */     return getNumber("TextDecorationThickness");
/*      */   }
/*      */ 
/*      */   public void setTextDecorationThickness(float textDecorationThickness)
/*      */   {
/* 1235 */     setNumber("TextDecorationThickness", textDecorationThickness);
/*      */   }
/*      */ 
/*      */   public void setTextDecorationThickness(int textDecorationThickness)
/*      */   {
/* 1246 */     setNumber("TextDecorationThickness", textDecorationThickness);
/*      */   }
/*      */ 
/*      */   public String getTextDecorationType()
/*      */   {
/* 1257 */     return getName("TextDecorationType", "None");
/*      */   }
/*      */ 
/*      */   public void setTextDecorationType(String textDecorationType)
/*      */   {
/* 1274 */     setName("TextDecorationType", textDecorationType);
/*      */   }
/*      */ 
/*      */   public String getRubyAlign()
/*      */   {
/* 1285 */     return getName("RubyAlign", "Distribute");
/*      */   }
/*      */ 
/*      */   public void setRubyAlign(String rubyAlign)
/*      */   {
/* 1303 */     setName("RubyAlign", rubyAlign);
/*      */   }
/*      */ 
/*      */   public String getRubyPosition()
/*      */   {
/* 1315 */     return getName("RubyPosition", "Before");
/*      */   }
/*      */ 
/*      */   public void setRubyPosition(String rubyPosition)
/*      */   {
/* 1332 */     setName("RubyPosition", rubyPosition);
/*      */   }
/*      */ 
/*      */   public String getGlyphOrientationVertical()
/*      */   {
/* 1344 */     return getName("GlyphOrientationVertical", "Auto");
/*      */   }
/*      */ 
/*      */   public void setGlyphOrientationVertical(String glyphOrientationVertical)
/*      */   {
/* 1367 */     setName("GlyphOrientationVertical", glyphOrientationVertical);
/*      */   }
/*      */ 
/*      */   public int getColumnCount()
/*      */   {
/* 1378 */     return getInteger("ColumnCount", 1);
/*      */   }
/*      */ 
/*      */   public void setColumnCount(int columnCount)
/*      */   {
/* 1389 */     setInteger("ColumnCount", columnCount);
/*      */   }
/*      */ 
/*      */   public Object getColumnGap()
/*      */   {
/* 1400 */     return getNumberOrArrayOfNumber("ColumnGap", -1.0F);
/*      */   }
/*      */ 
/*      */   public void setColumnGap(float columnGap)
/*      */   {
/* 1411 */     setNumber("ColumnGap", columnGap);
/*      */   }
/*      */ 
/*      */   public void setColumnGap(int columnGap)
/*      */   {
/* 1422 */     setNumber("ColumnGap", columnGap);
/*      */   }
/*      */ 
/*      */   public void setColumnGaps(float[] columnGaps)
/*      */   {
/* 1435 */     setArrayOfNumber("ColumnGap", columnGaps);
/*      */   }
/*      */ 
/*      */   public Object getColumnWidths()
/*      */   {
/* 1446 */     return getNumberOrArrayOfNumber("ColumnWidths", -1.0F);
/*      */   }
/*      */ 
/*      */   public void setAllColumnWidths(float columnWidth)
/*      */   {
/* 1456 */     setNumber("ColumnWidths", columnWidth);
/*      */   }
/*      */ 
/*      */   public void setAllColumnWidths(int columnWidth)
/*      */   {
/* 1466 */     setNumber("ColumnWidths", columnWidth);
/*      */   }
/*      */ 
/*      */   public void setColumnWidths(float[] columnWidths)
/*      */   {
/* 1476 */     setArrayOfNumber("ColumnWidths", columnWidths);
/*      */   }
/*      */ 
/*      */   public String toString()
/*      */   {
/* 1482 */     StringBuilder sb = new StringBuilder().append(super.toString());
/* 1483 */     if (isSpecified("Placement"))
/*      */     {
/* 1485 */       sb.append(", Placement=").append(getPlacement());
/*      */     }
/* 1487 */     if (isSpecified("WritingMode"))
/*      */     {
/* 1489 */       sb.append(", WritingMode=").append(getWritingMode());
/*      */     }
/* 1491 */     if (isSpecified("BackgroundColor"))
/*      */     {
/* 1493 */       sb.append(", BackgroundColor=").append(getBackgroundColor());
/*      */     }
/* 1495 */     if (isSpecified("BorderColor"))
/*      */     {
/* 1497 */       sb.append(", BorderColor=").append(getBorderColors());
/*      */     }
/* 1499 */     if (isSpecified("BorderStyle"))
/*      */     {
/* 1501 */       Object borderStyle = getBorderStyle();
/* 1502 */       sb.append(", BorderStyle=");
/* 1503 */       if ((borderStyle instanceof String[]))
/*      */       {
/* 1505 */         sb.append(arrayToString((String[])borderStyle));
/*      */       }
/*      */       else
/*      */       {
/* 1509 */         sb.append(borderStyle);
/*      */       }
/*      */     }
/* 1512 */     if (isSpecified("BorderThickness"))
/*      */     {
/* 1514 */       Object borderThickness = getBorderThickness();
/* 1515 */       sb.append(", BorderThickness=");
/* 1516 */       if ((borderThickness instanceof float[]))
/*      */       {
/* 1518 */         sb.append(arrayToString((float[])borderThickness));
/*      */       }
/*      */       else
/*      */       {
/* 1522 */         sb.append(String.valueOf((Float)borderThickness));
/*      */       }
/*      */     }
/* 1525 */     if (isSpecified("Padding"))
/*      */     {
/* 1527 */       Object padding = getPadding();
/* 1528 */       sb.append(", Padding=");
/* 1529 */       if ((padding instanceof float[]))
/*      */       {
/* 1531 */         sb.append(arrayToString((float[])padding));
/*      */       }
/*      */       else
/*      */       {
/* 1535 */         sb.append(String.valueOf((Float)padding));
/*      */       }
/*      */     }
/* 1538 */     if (isSpecified("Color"))
/*      */     {
/* 1540 */       sb.append(", Color=").append(getColor());
/*      */     }
/* 1542 */     if (isSpecified("SpaceBefore"))
/*      */     {
/* 1544 */       sb.append(", SpaceBefore=").append(String.valueOf(getSpaceBefore()));
/*      */     }
/*      */ 
/* 1547 */     if (isSpecified("SpaceAfter"))
/*      */     {
/* 1549 */       sb.append(", SpaceAfter=").append(String.valueOf(getSpaceAfter()));
/*      */     }
/*      */ 
/* 1552 */     if (isSpecified("StartIndent"))
/*      */     {
/* 1554 */       sb.append(", StartIndent=").append(String.valueOf(getStartIndent()));
/*      */     }
/*      */ 
/* 1557 */     if (isSpecified("EndIndent"))
/*      */     {
/* 1559 */       sb.append(", EndIndent=").append(String.valueOf(getEndIndent()));
/*      */     }
/*      */ 
/* 1562 */     if (isSpecified("TextIndent"))
/*      */     {
/* 1564 */       sb.append(", TextIndent=").append(String.valueOf(getTextIndent()));
/*      */     }
/*      */ 
/* 1567 */     if (isSpecified("TextAlign"))
/*      */     {
/* 1569 */       sb.append(", TextAlign=").append(getTextAlign());
/*      */     }
/* 1571 */     if (isSpecified("BBox"))
/*      */     {
/* 1573 */       sb.append(", BBox=").append(getBBox());
/*      */     }
/* 1575 */     if (isSpecified("Width"))
/*      */     {
/* 1577 */       Object width = getWidth();
/* 1578 */       sb.append(", Width=");
/* 1579 */       if ((width instanceof Float))
/*      */       {
/* 1581 */         sb.append(String.valueOf((Float)width));
/*      */       }
/*      */       else
/*      */       {
/* 1585 */         sb.append(width);
/*      */       }
/*      */     }
/* 1588 */     if (isSpecified("Height"))
/*      */     {
/* 1590 */       Object height = getHeight();
/* 1591 */       sb.append(", Height=");
/* 1592 */       if ((height instanceof Float))
/*      */       {
/* 1594 */         sb.append(String.valueOf((Float)height));
/*      */       }
/*      */       else
/*      */       {
/* 1598 */         sb.append(height);
/*      */       }
/*      */     }
/* 1601 */     if (isSpecified("BlockAlign"))
/*      */     {
/* 1603 */       sb.append(", BlockAlign=").append(getBlockAlign());
/*      */     }
/* 1605 */     if (isSpecified("InlineAlign"))
/*      */     {
/* 1607 */       sb.append(", InlineAlign=").append(getInlineAlign());
/*      */     }
/* 1609 */     if (isSpecified("TBorderStyle"))
/*      */     {
/* 1611 */       Object tBorderStyle = getTBorderStyle();
/* 1612 */       sb.append(", TBorderStyle=");
/* 1613 */       if ((tBorderStyle instanceof String[]))
/*      */       {
/* 1615 */         sb.append(arrayToString((String[])tBorderStyle));
/*      */       }
/*      */       else
/*      */       {
/* 1619 */         sb.append(tBorderStyle);
/*      */       }
/*      */     }
/* 1622 */     if (isSpecified("TPadding"))
/*      */     {
/* 1624 */       Object tPadding = getTPadding();
/* 1625 */       sb.append(", TPadding=");
/* 1626 */       if ((tPadding instanceof float[]))
/*      */       {
/* 1628 */         sb.append(arrayToString((float[])tPadding));
/*      */       }
/*      */       else
/*      */       {
/* 1632 */         sb.append(String.valueOf((Float)tPadding));
/*      */       }
/*      */     }
/* 1635 */     if (isSpecified("BaselineShift"))
/*      */     {
/* 1637 */       sb.append(", BaselineShift=").append(String.valueOf(getBaselineShift()));
/*      */     }
/*      */ 
/* 1640 */     if (isSpecified("LineHeight"))
/*      */     {
/* 1642 */       Object lineHeight = getLineHeight();
/* 1643 */       sb.append(", LineHeight=");
/* 1644 */       if ((lineHeight instanceof Float))
/*      */       {
/* 1646 */         sb.append(String.valueOf((Float)lineHeight));
/*      */       }
/*      */       else
/*      */       {
/* 1650 */         sb.append(lineHeight);
/*      */       }
/*      */     }
/* 1653 */     if (isSpecified("TextDecorationColor"))
/*      */     {
/* 1655 */       sb.append(", TextDecorationColor=").append(getTextDecorationColor());
/*      */     }
/*      */ 
/* 1658 */     if (isSpecified("TextDecorationThickness"))
/*      */     {
/* 1660 */       sb.append(", TextDecorationThickness=").append(String.valueOf(getTextDecorationThickness()));
/*      */     }
/*      */ 
/* 1663 */     if (isSpecified("TextDecorationType"))
/*      */     {
/* 1665 */       sb.append(", TextDecorationType=").append(getTextDecorationType());
/*      */     }
/*      */ 
/* 1668 */     if (isSpecified("RubyAlign"))
/*      */     {
/* 1670 */       sb.append(", RubyAlign=").append(getRubyAlign());
/*      */     }
/* 1672 */     if (isSpecified("RubyPosition"))
/*      */     {
/* 1674 */       sb.append(", RubyPosition=").append(getRubyPosition());
/*      */     }
/* 1676 */     if (isSpecified("GlyphOrientationVertical"))
/*      */     {
/* 1678 */       sb.append(", GlyphOrientationVertical=").append(getGlyphOrientationVertical());
/*      */     }
/*      */ 
/* 1681 */     if (isSpecified("ColumnCount"))
/*      */     {
/* 1683 */       sb.append(", ColumnCount=").append(String.valueOf(getColumnCount()));
/*      */     }
/*      */ 
/* 1686 */     if (isSpecified("ColumnGap"))
/*      */     {
/* 1688 */       Object columnGap = getColumnGap();
/* 1689 */       sb.append(", ColumnGap=");
/* 1690 */       if ((columnGap instanceof float[]))
/*      */       {
/* 1692 */         sb.append(arrayToString((float[])columnGap));
/*      */       }
/*      */       else
/*      */       {
/* 1696 */         sb.append(String.valueOf((Float)columnGap));
/*      */       }
/*      */     }
/* 1699 */     if (isSpecified("ColumnWidths"))
/*      */     {
/* 1701 */       Object columnWidth = getColumnWidths();
/* 1702 */       sb.append(", ColumnWidths=");
/* 1703 */       if ((columnWidth instanceof float[]))
/*      */       {
/* 1705 */         sb.append(arrayToString((float[])columnWidth));
/*      */       }
/*      */       else
/*      */       {
/* 1709 */         sb.append(String.valueOf((Float)columnWidth));
/*      */       }
/*      */     }
/* 1712 */     return sb.toString();
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.documentinterchange.taggedpdf.PDLayoutAttributeObject
 * JD-Core Version:    0.6.2
 */