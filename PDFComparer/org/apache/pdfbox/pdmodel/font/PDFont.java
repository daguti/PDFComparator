/*     */ package org.apache.pdfbox.pdmodel.font;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.fontbox.afm.AFMParser;
/*     */ import org.apache.fontbox.afm.FontMetric;
/*     */ import org.apache.fontbox.cmap.CMap;
/*     */ import org.apache.fontbox.cmap.CMapParser;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ import org.apache.pdfbox.encoding.Encoding;
/*     */ import org.apache.pdfbox.pdmodel.common.COSArrayList;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.common.PDMatrix;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ import org.apache.pdfbox.util.ResourceLoader;
/*     */ 
/*     */ public abstract class PDFont
/*     */   implements COSObjectable
/*     */ {
/*  66 */   private static final Log LOG = LogFactory.getLog(PDFont.class);
/*     */   protected COSDictionary font;
/*  76 */   private Encoding fontEncoding = null;
/*     */ 
/*  81 */   private PDFontDescriptor fontDescriptor = null;
/*     */ 
/*  86 */   protected PDMatrix fontMatrix = null;
/*     */ 
/*  92 */   protected CMap cmap = null;
/*     */ 
/*  97 */   protected CMap toUnicodeCmap = null;
/*     */ 
/*  99 */   private boolean hasToUnicode = false;
/*     */ 
/* 101 */   private boolean widthsAreMissing = false;
/*     */ 
/* 103 */   protected static Map<String, CMap> cmapObjects = Collections.synchronizedMap(new HashMap());
/*     */ 
/* 109 */   private List<Float> widths = null;
/*     */ 
/* 114 */   private static final Map<String, FontMetric> afmObjects = Collections.unmodifiableMap(getAdobeFontMetrics());
/*     */   protected static final String resourceRootCMAP = "org/apache/pdfbox/resources/cmap/";
/*     */   private static final String resourceRootAFM = "org/apache/pdfbox/resources/afm/";
/* 458 */   private FontMetric afm = null;
/*     */ 
/* 460 */   private COSBase encoding = null;
/*     */ 
/* 567 */   private static final String[] SINGLE_CHAR_STRING = new String[256];
/* 568 */   private static final String[][] DOUBLE_CHAR_STRING = new String[256][256];
/*     */ 
/* 672 */   private String subtype = null;
/*     */   private boolean type1Font;
/*     */   private boolean type3Font;
/*     */   private boolean trueTypeFont;
/*     */   private boolean type0Font;
/*     */ 
/*     */   private static Map<String, FontMetric> getAdobeFontMetrics()
/*     */   {
/* 121 */     Map metrics = new HashMap();
/* 122 */     addAdobeFontMetric(metrics, "Courier-Bold");
/* 123 */     addAdobeFontMetric(metrics, "Courier-BoldOblique");
/* 124 */     addAdobeFontMetric(metrics, "Courier");
/* 125 */     addAdobeFontMetric(metrics, "Courier-Oblique");
/* 126 */     addAdobeFontMetric(metrics, "Helvetica");
/* 127 */     addAdobeFontMetric(metrics, "Helvetica-Bold");
/* 128 */     addAdobeFontMetric(metrics, "Helvetica-BoldOblique");
/* 129 */     addAdobeFontMetric(metrics, "Helvetica-Oblique");
/* 130 */     addAdobeFontMetric(metrics, "Symbol");
/* 131 */     addAdobeFontMetric(metrics, "Times-Bold");
/* 132 */     addAdobeFontMetric(metrics, "Times-BoldItalic");
/* 133 */     addAdobeFontMetric(metrics, "Times-Italic");
/* 134 */     addAdobeFontMetric(metrics, "Times-Roman");
/* 135 */     addAdobeFontMetric(metrics, "ZapfDingbats");
/*     */ 
/* 138 */     addAdobeFontMetric(metrics, "Arial", "Helvetica");
/* 139 */     addAdobeFontMetric(metrics, "Arial,Bold", "Helvetica-Bold");
/* 140 */     addAdobeFontMetric(metrics, "Arial,Italic", "Helvetica-Oblique");
/* 141 */     addAdobeFontMetric(metrics, "Arial,BoldItalic", "Helvetica-BoldOblique");
/*     */ 
/* 143 */     return metrics;
/*     */   }
/*     */ 
/*     */   private static void addAdobeFontMetric(Map<String, FontMetric> metrics, String name)
/*     */   {
/* 152 */     addAdobeFontMetric(metrics, name, name);
/*     */   }
/*     */ 
/*     */   private static void addAdobeFontMetric(Map<String, FontMetric> metrics, String name, String filePrefix)
/*     */   {
/*     */     try
/*     */     {
/* 159 */       String resource = "org/apache/pdfbox/resources/afm/" + filePrefix + ".afm";
/* 160 */       InputStream afmStream = ResourceLoader.loadResource(resource);
/* 161 */       if (afmStream != null)
/*     */       {
/*     */         try
/*     */         {
/* 165 */           AFMParser parser = new AFMParser(afmStream);
/* 166 */           parser.parse();
/* 167 */           metrics.put(name, parser.getResult());
/*     */         }
/*     */         finally
/*     */         {
/* 171 */           afmStream.close();
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void clearResources()
/*     */   {
/* 194 */     cmapObjects.clear();
/*     */   }
/*     */ 
/*     */   public PDFont()
/*     */   {
/* 202 */     this.font = new COSDictionary();
/* 203 */     this.font.setItem(COSName.TYPE, COSName.FONT);
/*     */   }
/*     */ 
/*     */   public PDFont(COSDictionary fontDictionary)
/*     */   {
/* 213 */     this.font = fontDictionary;
/* 214 */     determineEncoding();
/*     */   }
/*     */ 
/*     */   public PDFontDescriptor getFontDescriptor()
/*     */   {
/* 225 */     if (this.fontDescriptor == null)
/*     */     {
/* 227 */       COSDictionary fd = (COSDictionary)this.font.getDictionaryObject(COSName.FONT_DESC);
/* 228 */       if (fd != null)
/*     */       {
/* 230 */         this.fontDescriptor = new PDFontDescriptorDictionary(fd);
/*     */       }
/*     */       else
/*     */       {
/* 234 */         getAFM();
/* 235 */         if (this.afm != null)
/*     */         {
/* 237 */           this.fontDescriptor = new PDFontDescriptorAFM(this.afm);
/*     */         }
/*     */       }
/*     */     }
/* 241 */     return this.fontDescriptor;
/*     */   }
/*     */ 
/*     */   public void setFontDescriptor(PDFontDescriptorDictionary fdDictionary)
/*     */   {
/* 251 */     COSDictionary dic = null;
/* 252 */     if (fdDictionary != null)
/*     */     {
/* 254 */       dic = fdDictionary.getCOSDictionary();
/*     */     }
/* 256 */     this.font.setItem(COSName.FONT_DESC, dic);
/* 257 */     this.fontDescriptor = fdDictionary;
/*     */   }
/*     */ 
/*     */   protected abstract void determineEncoding();
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 272 */     return this.font;
/*     */   }
/*     */ 
/*     */   public abstract float getFontWidth(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException;
/*     */ 
/*     */   public abstract float getFontHeight(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException;
/*     */ 
/*     */   public float getStringWidth(String string)
/*     */     throws IOException
/*     */   {
/* 312 */     byte[] data = string.getBytes("ISO-8859-1");
/* 313 */     float totalWidth = 0.0F;
/* 314 */     for (int i = 0; i < data.length; i++)
/*     */     {
/* 316 */       totalWidth += getFontWidth(data, i, 1);
/*     */     }
/* 318 */     return totalWidth;
/*     */   }
/*     */ 
/*     */   public abstract float getAverageFontWidth()
/*     */     throws IOException;
/*     */ 
/*     */   /** @deprecated */
/*     */   public void drawString(String string, Graphics g, float fontSize, AffineTransform at, float x, float y)
/*     */     throws IOException
/*     */   {
/* 346 */     drawString(string, null, g, fontSize, at, x, y);
/*     */   }
/*     */ 
/*     */   public abstract void drawString(String paramString, int[] paramArrayOfInt, Graphics paramGraphics, float paramFloat1, AffineTransform paramAffineTransform, float paramFloat2, float paramFloat3)
/*     */     throws IOException;
/*     */ 
/*     */   public int getCodeFromArray(byte[] data, int offset, int length)
/*     */   {
/* 376 */     int code = 0;
/* 377 */     for (int i = 0; i < length; i++)
/*     */     {
/* 379 */       code <<= 8;
/* 380 */       code |= (data[(offset + i)] + 256) % 256;
/*     */     }
/* 382 */     return code;
/*     */   }
/*     */ 
/*     */   protected float getFontWidthFromAFMFile(int code)
/*     */     throws IOException
/*     */   {
/* 396 */     float retval = 0.0F;
/* 397 */     FontMetric metric = getAFM();
/* 398 */     if (metric != null)
/*     */     {
/* 400 */       String characterName = this.fontEncoding.getName(code);
/* 401 */       retval = metric.getCharacterWidth(characterName);
/*     */     }
/* 403 */     return retval;
/*     */   }
/*     */ 
/*     */   protected float getAverageFontWidthFromAFMFile()
/*     */     throws IOException
/*     */   {
/* 415 */     float retval = 0.0F;
/* 416 */     FontMetric metric = getAFM();
/* 417 */     if (metric != null)
/*     */     {
/* 419 */       retval = metric.getAverageCharacterWidth();
/*     */     }
/* 421 */     return retval;
/*     */   }
/*     */ 
/*     */   protected FontMetric getAFM()
/*     */   {
/* 432 */     if ((isType1Font()) && (this.afm == null))
/*     */     {
/* 434 */       COSBase baseFont = this.font.getDictionaryObject(COSName.BASE_FONT);
/* 435 */       String name = null;
/* 436 */       if ((baseFont instanceof COSName))
/*     */       {
/* 438 */         name = ((COSName)baseFont).getName();
/* 439 */         if (name.indexOf("+") > -1)
/*     */         {
/* 441 */           name = name.substring(name.indexOf("+") + 1);
/*     */         }
/*     */ 
/*     */       }
/* 445 */       else if ((baseFont instanceof COSString))
/*     */       {
/* 447 */         COSString string = (COSString)baseFont;
/* 448 */         name = string.getString();
/*     */       }
/* 450 */       if (name != null)
/*     */       {
/* 452 */         this.afm = ((FontMetric)afmObjects.get(name));
/*     */       }
/*     */     }
/* 455 */     return this.afm;
/*     */   }
/*     */ 
/*     */   protected COSBase getEncoding()
/*     */   {
/* 473 */     if (this.encoding == null)
/*     */     {
/* 475 */       this.encoding = this.font.getDictionaryObject(COSName.ENCODING);
/*     */     }
/* 477 */     return this.encoding;
/*     */   }
/*     */ 
/*     */   protected void setEncoding(COSBase encodingValue)
/*     */   {
/* 486 */     this.font.setItem(COSName.ENCODING, encodingValue);
/* 487 */     this.encoding = encodingValue;
/*     */   }
/*     */ 
/*     */   protected String cmapEncoding(int code, int length, boolean isCIDFont, CMap sourceCmap)
/*     */     throws IOException
/*     */   {
/* 502 */     String retval = null;
/*     */ 
/* 504 */     if (sourceCmap == null)
/*     */     {
/* 506 */       sourceCmap = this.cmap;
/*     */     }
/* 508 */     if (sourceCmap != null)
/*     */     {
/* 510 */       retval = sourceCmap.lookup(code, length);
/* 511 */       if ((retval == null) && (isCIDFont))
/*     */       {
/* 513 */         retval = sourceCmap.lookupCID(code);
/*     */       }
/*     */     }
/* 516 */     return retval;
/*     */   }
/*     */ 
/*     */   public String encode(byte[] c, int offset, int length)
/*     */     throws IOException
/*     */   {
/* 531 */     String retval = null;
/* 532 */     int code = getCodeFromArray(c, offset, length);
/* 533 */     if (this.toUnicodeCmap != null)
/*     */     {
/* 535 */       retval = cmapEncoding(code, length, false, this.toUnicodeCmap);
/*     */     }
/* 537 */     if ((retval == null) && (this.cmap != null))
/*     */     {
/* 539 */       retval = cmapEncoding(code, length, false, this.cmap);
/*     */     }
/*     */ 
/* 543 */     if (retval == null)
/*     */     {
/* 545 */       if (this.fontEncoding != null)
/*     */       {
/* 547 */         retval = this.fontEncoding.getCharacter(code);
/*     */       }
/* 549 */       if ((retval == null) && ((this.cmap == null) || (length == 2)))
/*     */       {
/* 551 */         retval = getStringFromArray(c, offset, length);
/*     */       }
/*     */     }
/* 554 */     return retval;
/*     */   }
/*     */ 
/*     */   public int encodeToCID(byte[] c, int offset, int length) throws IOException
/*     */   {
/* 559 */     int code = -1;
/* 560 */     if (encode(c, offset, length) != null)
/*     */     {
/* 562 */       code = getCodeFromArray(c, offset, length);
/*     */     }
/* 564 */     return code;
/*     */   }
/*     */ 
/*     */   protected static String getStringFromArray(byte[] c, int offset, int length)
/*     */   {
/* 606 */     String retval = null;
/* 607 */     if (length == 1)
/*     */     {
/* 609 */       retval = SINGLE_CHAR_STRING[((c[offset] + 256) % 256)];
/*     */     }
/* 611 */     else if (length == 2)
/*     */     {
/* 613 */       retval = DOUBLE_CHAR_STRING[((c[offset] + 256) % 256)][((c[(offset + 1)] + 256) % 256)];
/*     */     }
/* 615 */     return retval;
/*     */   }
/*     */ 
/*     */   protected CMap parseCmap(String cmapRoot, InputStream cmapStream)
/*     */   {
/* 620 */     CMap targetCmap = null;
/* 621 */     if (cmapStream != null)
/*     */     {
/* 623 */       CMapParser parser = new CMapParser();
/*     */       try
/*     */       {
/* 626 */         targetCmap = parser.parse(cmapRoot, cmapStream);
/*     */ 
/* 628 */         if (cmapRoot != null)
/*     */         {
/* 630 */           cmapObjects.put(targetCmap.getName(), targetCmap);
/*     */         }
/*     */       }
/*     */       catch (IOException exception)
/*     */       {
/* 635 */         LOG.error("An error occurs while reading a CMap", exception);
/*     */       }
/*     */     }
/* 638 */     return targetCmap;
/*     */   }
/*     */ 
/*     */   public void setFontEncoding(Encoding enc)
/*     */   {
/* 648 */     this.fontEncoding = enc;
/*     */   }
/*     */ 
/*     */   public Encoding getFontEncoding()
/*     */   {
/* 658 */     return this.fontEncoding;
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 668 */     return this.font.getNameAsString(COSName.TYPE);
/*     */   }
/*     */ 
/*     */   public String getSubType()
/*     */   {
/* 685 */     if (this.subtype == null)
/*     */     {
/* 687 */       this.subtype = this.font.getNameAsString(COSName.SUBTYPE);
/* 688 */       this.type1Font = "Type1".equals(this.subtype);
/* 689 */       this.trueTypeFont = "TrueType".equals(this.subtype);
/* 690 */       this.type0Font = "Type0".equals(this.subtype);
/* 691 */       this.type3Font = "Type3".equals(this.subtype);
/*     */     }
/* 693 */     return this.subtype;
/*     */   }
/*     */ 
/*     */   protected boolean isType1Font()
/*     */   {
/* 702 */     getSubType();
/* 703 */     return this.type1Font;
/*     */   }
/*     */ 
/*     */   public boolean isType3Font()
/*     */   {
/* 713 */     getSubType();
/* 714 */     return this.type3Font;
/*     */   }
/*     */ 
/*     */   protected boolean isType0Font()
/*     */   {
/* 723 */     getSubType();
/* 724 */     return this.type0Font;
/*     */   }
/*     */ 
/*     */   private boolean isTrueTypeFont()
/*     */   {
/* 729 */     getSubType();
/* 730 */     return this.trueTypeFont;
/*     */   }
/*     */ 
/*     */   public boolean isSymbolicFont()
/*     */   {
/* 740 */     if (getFontDescriptor() != null)
/*     */     {
/* 742 */       return getFontDescriptor().isSymbolic();
/*     */     }
/* 744 */     return false;
/*     */   }
/*     */ 
/*     */   public String getBaseFont()
/*     */   {
/* 754 */     return this.font.getNameAsString(COSName.BASE_FONT);
/*     */   }
/*     */ 
/*     */   public void setBaseFont(String baseFont)
/*     */   {
/* 764 */     this.font.setName(COSName.BASE_FONT, baseFont);
/*     */   }
/*     */ 
/*     */   public int getFirstChar()
/*     */   {
/* 774 */     return this.font.getInt(COSName.FIRST_CHAR, -1);
/*     */   }
/*     */ 
/*     */   public void setFirstChar(int firstChar)
/*     */   {
/* 784 */     this.font.setInt(COSName.FIRST_CHAR, firstChar);
/*     */   }
/*     */ 
/*     */   public int getLastChar()
/*     */   {
/* 794 */     return this.font.getInt(COSName.LAST_CHAR, -1);
/*     */   }
/*     */ 
/*     */   public void setLastChar(int lastChar)
/*     */   {
/* 804 */     this.font.setInt(COSName.LAST_CHAR, lastChar);
/*     */   }
/*     */ 
/*     */   public List<Float> getWidths()
/*     */   {
/* 815 */     if ((this.widths == null) && (!this.widthsAreMissing))
/*     */     {
/* 817 */       COSArray array = (COSArray)this.font.getDictionaryObject(COSName.WIDTHS);
/* 818 */       if (array != null)
/*     */       {
/* 820 */         this.widths = COSArrayList.convertFloatCOSArrayToList(array);
/*     */       }
/*     */       else
/*     */       {
/* 824 */         this.widthsAreMissing = true;
/*     */       }
/*     */     }
/* 827 */     return this.widths;
/*     */   }
/*     */ 
/*     */   public void setWidths(List<Float> widthsList)
/*     */   {
/* 837 */     this.widths = widthsList;
/* 838 */     this.font.setItem(COSName.WIDTHS, COSArrayList.converterToCOSArray(this.widths));
/*     */   }
/*     */ 
/*     */   public PDMatrix getFontMatrix()
/*     */   {
/* 853 */     if (this.fontMatrix == null)
/*     */     {
/* 855 */       COSArray array = (COSArray)this.font.getDictionaryObject(COSName.FONT_MATRIX);
/* 856 */       if (array == null)
/*     */       {
/* 858 */         array = new COSArray();
/* 859 */         array.add(new COSFloat(0.001F));
/* 860 */         array.add(COSInteger.ZERO);
/* 861 */         array.add(COSInteger.ZERO);
/* 862 */         array.add(new COSFloat(0.001F));
/* 863 */         array.add(COSInteger.ZERO);
/* 864 */         array.add(COSInteger.ZERO);
/*     */       }
/* 866 */       this.fontMatrix = new PDMatrix(array);
/*     */     }
/* 868 */     return this.fontMatrix;
/*     */   }
/*     */ 
/*     */   public abstract PDRectangle getFontBoundingBox()
/*     */     throws IOException;
/*     */ 
/*     */   public boolean equals(Object other)
/*     */   {
/* 885 */     return ((other instanceof PDFont)) && (((PDFont)other).getCOSObject() == getCOSObject());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 893 */     return getCOSObject().hashCode();
/*     */   }
/*     */ 
/*     */   public float getFontWidth(int charCode)
/*     */   {
/* 903 */     float width = -1.0F;
/* 904 */     int firstChar = getFirstChar();
/* 905 */     int lastChar = getLastChar();
/* 906 */     if ((charCode >= firstChar) && (charCode <= lastChar))
/*     */     {
/* 909 */       if (!this.widthsAreMissing)
/*     */       {
/* 911 */         getWidths();
/* 912 */         if (this.widths != null)
/*     */         {
/* 914 */           width = ((Float)this.widths.get(charCode - firstChar)).floatValue();
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 920 */       PDFontDescriptor fd = getFontDescriptor();
/* 921 */       if ((fd instanceof PDFontDescriptorDictionary))
/*     */       {
/* 923 */         width = fd.getMissingWidth();
/*     */       }
/*     */     }
/* 926 */     return width;
/*     */   }
/*     */ 
/*     */   protected boolean hasToUnicode()
/*     */   {
/* 935 */     return this.hasToUnicode;
/*     */   }
/*     */ 
/*     */   protected void setHasToUnicode(boolean hasToUnicodeValue)
/*     */   {
/* 944 */     this.hasToUnicode = hasToUnicodeValue;
/*     */   }
/*     */ 
/*     */   public abstract float getSpaceWidth();
/*     */ 
/*     */   public CMap getToUnicodeCMap()
/*     */   {
/* 960 */     return this.toUnicodeCmap;
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 571 */     for (int i = 0; i < 256; i++)
/*     */     {
/*     */       try
/*     */       {
/* 575 */         SINGLE_CHAR_STRING[i] = new String(new byte[] { (byte)i }, "ISO-8859-1");
/*     */       }
/*     */       catch (UnsupportedEncodingException e)
/*     */       {
/* 580 */         LOG.error(e, e);
/*     */       }
/* 582 */       for (int j = 0; j < 256; j++)
/*     */       {
/*     */         try
/*     */         {
/* 586 */           DOUBLE_CHAR_STRING[i][j] = new String(new byte[] { (byte)i, (byte)j }, "UTF-16BE");
/*     */         }
/*     */         catch (UnsupportedEncodingException e)
/*     */         {
/* 591 */           LOG.error(e, e);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.font.PDFont
 * JD-Core Version:    0.6.2
 */