/*     */ package org.apache.pdfbox.pdmodel.font;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.FontFormatException;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Properties;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.fontbox.ttf.CMAPEncodingEntry;
/*     */ import org.apache.fontbox.ttf.CMAPTable;
/*     */ import org.apache.fontbox.ttf.GlyphData;
/*     */ import org.apache.fontbox.ttf.GlyphTable;
/*     */ import org.apache.fontbox.ttf.HeaderTable;
/*     */ import org.apache.fontbox.ttf.HorizontalHeaderTable;
/*     */ import org.apache.fontbox.ttf.HorizontalMetricsTable;
/*     */ import org.apache.fontbox.ttf.NameRecord;
/*     */ import org.apache.fontbox.ttf.NamingTable;
/*     */ import org.apache.fontbox.ttf.OS2WindowsMetricsTable;
/*     */ import org.apache.fontbox.ttf.PostScriptTable;
/*     */ import org.apache.fontbox.ttf.TTFParser;
/*     */ import org.apache.fontbox.ttf.TTFSubFont;
/*     */ import org.apache.fontbox.ttf.TrueTypeFont;
/*     */ import org.apache.fontbox.util.BoundingBox;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.encoding.Encoding;
/*     */ import org.apache.pdfbox.encoding.MacOSRomanEncoding;
/*     */ import org.apache.pdfbox.encoding.WinAnsiEncoding;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ import org.apache.pdfbox.util.ResourceLoader;
/*     */ 
/*     */ public class PDTrueTypeFont extends PDSimpleFont
/*     */ {
/*  73 */   private static final Log log = LogFactory.getLog(PDTrueTypeFont.class);
/*     */   private static final int START_RANGE_F000 = 61440;
/*     */   private static final int START_RANGE_F100 = 61696;
/*     */   private static final int START_RANGE_F200 = 61952;
/*  82 */   private CMAPEncodingEntry cmapWinUnicode = null;
/*  83 */   private CMAPEncodingEntry cmapWinSymbol = null;
/*  84 */   private CMAPEncodingEntry cmapMacintoshSymbol = null;
/*  85 */   private boolean cmapInitialized = false;
/*     */ 
/*  87 */   private TrueTypeFont trueTypeFont = null;
/*     */ 
/*  89 */   private HashMap<Integer, Float> advanceWidths = new HashMap();
/*     */   public static final String UNKNOWN_FONT = "UNKNOWN_FONT";
/*  97 */   private Font awtFont = null;
/*     */ 
/*  99 */   private static Properties externalFonts = new Properties();
/* 100 */   private static Map<String, TrueTypeFont> loadedExternalFonts = new HashMap();
/*     */ 
/*     */   public PDTrueTypeFont()
/*     */   {
/* 123 */     this.font.setItem(COSName.SUBTYPE, COSName.TRUE_TYPE);
/*     */   }
/*     */ 
/*     */   public PDTrueTypeFont(COSDictionary fontDictionary)
/*     */     throws IOException
/*     */   {
/* 135 */     super(fontDictionary);
/* 136 */     ensureFontDescriptor();
/*     */   }
/*     */ 
/*     */   public static PDTrueTypeFont loadTTF(PDDocument doc, String file)
/*     */     throws IOException
/*     */   {
/* 149 */     return loadTTF(doc, new File(file));
/*     */   }
/*     */ 
/*     */   public static PDTrueTypeFont loadTTF(PDDocument doc, File file)
/*     */     throws IOException
/*     */   {
/* 162 */     return loadTTF(doc, new FileInputStream(file));
/*     */   }
/*     */ 
/*     */   public static PDTrueTypeFont loadTTF(PDDocument doc, InputStream stream)
/*     */     throws IOException
/*     */   {
/* 175 */     return loadTTF(doc, stream, new WinAnsiEncoding());
/*     */   }
/*     */ 
/*     */   public static PDTrueTypeFont loadTTF(PDDocument doc, InputStream stream, Encoding enc)
/*     */     throws IOException
/*     */   {
/* 189 */     PDStream fontStream = new PDStream(doc, stream, false);
/* 190 */     fontStream.getStream().setInt(COSName.LENGTH1, fontStream.getByteArray().length);
/* 191 */     fontStream.addCompression();
/*     */ 
/* 194 */     return loadTTF(fontStream, enc);
/*     */   }
/*     */ 
/*     */   public static PDTrueTypeFont loadTTF(PDStream fontStream, Encoding enc)
/*     */     throws IOException
/*     */   {
/* 207 */     PDTrueTypeFont retval = new PDTrueTypeFont();
/* 208 */     retval.setFontEncoding(enc);
/* 209 */     retval.setEncoding(enc.getCOSObject());
/*     */ 
/* 211 */     PDFontDescriptorDictionary fd = new PDFontDescriptorDictionary();
/* 212 */     retval.setFontDescriptor(fd);
/* 213 */     fd.setFontFile2(fontStream);
/*     */ 
/* 215 */     InputStream stream = fontStream.createInputStream();
/*     */     try
/*     */     {
/* 218 */       retval.loadDescriptorDictionary(fd, stream);
/*     */     }
/*     */     finally
/*     */     {
/* 222 */       stream.close();
/*     */     }
/* 224 */     return retval;
/*     */   }
/*     */ 
/*     */   private void ensureFontDescriptor() throws IOException
/*     */   {
/* 229 */     if (getFontDescriptor() == null)
/*     */     {
/* 231 */       PDFontDescriptorDictionary fdd = new PDFontDescriptorDictionary();
/* 232 */       setFontDescriptor(fdd);
/* 233 */       InputStream ttfData = getExternalTTFData();
/* 234 */       if (ttfData != null)
/*     */       {
/*     */         try
/*     */         {
/* 238 */           loadDescriptorDictionary(fdd, ttfData);
/*     */         }
/*     */         finally
/*     */         {
/* 242 */           ttfData.close();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void loadDescriptorDictionary(PDFontDescriptorDictionary fd, InputStream ttfData) throws IOException
/*     */   {
/* 250 */     TrueTypeFont ttf = null;
/*     */     try
/*     */     {
/* 253 */       TTFParser parser = new TTFParser();
/* 254 */       ttf = parser.parseTTF(ttfData);
/* 255 */       NamingTable naming = ttf.getNaming();
/* 256 */       List records = naming.getNameRecords();
/* 257 */       for (int i = 0; i < records.size(); i++)
/*     */       {
/* 259 */         NameRecord nr = (NameRecord)records.get(i);
/* 260 */         if (nr.getNameId() == 6)
/*     */         {
/* 262 */           setBaseFont(nr.getString());
/* 263 */           fd.setFontName(nr.getString());
/*     */         }
/* 265 */         else if (nr.getNameId() == 1)
/*     */         {
/* 267 */           fd.setFontFamily(nr.getString());
/*     */         }
/*     */       }
/*     */ 
/* 271 */       OS2WindowsMetricsTable os2 = ttf.getOS2Windows();
/* 272 */       boolean isSymbolic = false;
/* 273 */       switch (os2.getFamilyClass())
/*     */       {
/*     */       case 12:
/* 276 */         isSymbolic = true;
/* 277 */         break;
/*     */       case 10:
/* 279 */         fd.setScript(true);
/* 280 */         break;
/*     */       case 1:
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/*     */       case 7:
/* 286 */         fd.setSerif(true);
/* 287 */         break;
/*     */       case 2:
/*     */       case 6:
/*     */       case 8:
/*     */       case 9:
/* 291 */       case 11: } switch (os2.getWidthClass())
/*     */       {
/*     */       case 1:
/* 294 */         fd.setFontStretch("UltraCondensed");
/* 295 */         break;
/*     */       case 2:
/* 297 */         fd.setFontStretch("ExtraCondensed");
/* 298 */         break;
/*     */       case 3:
/* 300 */         fd.setFontStretch("Condensed");
/* 301 */         break;
/*     */       case 4:
/* 303 */         fd.setFontStretch("SemiCondensed");
/* 304 */         break;
/*     */       case 5:
/* 306 */         fd.setFontStretch("Normal");
/* 307 */         break;
/*     */       case 6:
/* 309 */         fd.setFontStretch("SemiExpanded");
/* 310 */         break;
/*     */       case 7:
/* 312 */         fd.setFontStretch("Expanded");
/* 313 */         break;
/*     */       case 8:
/* 315 */         fd.setFontStretch("ExtraExpanded");
/* 316 */         break;
/*     */       case 9:
/* 318 */         fd.setFontStretch("UltraExpanded");
/* 319 */         break;
/*     */       }
/*     */ 
/* 323 */       fd.setFontWeight(os2.getWeightClass());
/* 324 */       fd.setSymbolic(isSymbolic);
/* 325 */       fd.setNonSymbolic(!isSymbolic);
/*     */ 
/* 332 */       HeaderTable header = ttf.getHeader();
/* 333 */       PDRectangle rect = new PDRectangle();
/* 334 */       float scaling = 1000.0F / header.getUnitsPerEm();
/* 335 */       rect.setLowerLeftX(header.getXMin() * scaling);
/* 336 */       rect.setLowerLeftY(header.getYMin() * scaling);
/* 337 */       rect.setUpperRightX(header.getXMax() * scaling);
/* 338 */       rect.setUpperRightY(header.getYMax() * scaling);
/* 339 */       fd.setFontBoundingBox(rect);
/*     */ 
/* 341 */       HorizontalHeaderTable hHeader = ttf.getHorizontalHeader();
/* 342 */       fd.setAscent(hHeader.getAscender() * scaling);
/* 343 */       fd.setDescent(hHeader.getDescender() * scaling);
/*     */ 
/* 345 */       GlyphTable glyphTable = ttf.getGlyph();
/* 346 */       GlyphData[] glyphs = glyphTable.getGlyphs();
/*     */ 
/* 348 */       PostScriptTable ps = ttf.getPostScript();
/* 349 */       fd.setFixedPitch(ps.getIsFixedPitch() > 0L);
/* 350 */       fd.setItalicAngle(ps.getItalicAngle());
/*     */ 
/* 352 */       String[] names = ps.getGlyphNames();
/*     */ 
/* 354 */       if (names != null)
/*     */       {
/* 356 */         for (int i = 0; i < names.length; i++)
/*     */         {
/* 360 */           if (names[i].equals("H"))
/*     */           {
/* 362 */             fd.setCapHeight(glyphs[i].getBoundingBox().getUpperRightY() / scaling);
/*     */           }
/* 364 */           if (names[i].equals("x"))
/*     */           {
/* 366 */             fd.setXHeight(glyphs[i].getBoundingBox().getUpperRightY() / scaling);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 373 */       fd.setStemV(fd.getFontBoundingBox().getWidth() * 0.13F);
/*     */ 
/* 375 */       CMAPTable cmapTable = ttf.getCMAP();
/* 376 */       CMAPEncodingEntry[] cmaps = cmapTable.getCmaps();
/* 377 */       CMAPEncodingEntry uniMap = null;
/*     */ 
/* 379 */       for (int i = 0; i < cmaps.length; i++)
/*     */       {
/* 381 */         if (cmaps[i].getPlatformId() == 3)
/*     */         {
/* 383 */           int platformEncoding = cmaps[i].getPlatformEncodingId();
/* 384 */           if (1 == platformEncoding)
/*     */           {
/* 386 */             uniMap = cmaps[i];
/* 387 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 392 */       Map codeToName = getFontEncoding().getCodeToNameMap();
/*     */ 
/* 394 */       int firstChar = ((Integer)Collections.min(codeToName.keySet())).intValue();
/* 395 */       int lastChar = ((Integer)Collections.max(codeToName.keySet())).intValue();
/*     */ 
/* 397 */       HorizontalMetricsTable hMet = ttf.getHorizontalMetrics();
/* 398 */       int[] widthValues = hMet.getAdvanceWidth();
/*     */ 
/* 401 */       boolean isMonospaced = (fd.isFixedPitch()) || (widthValues.length == 1);
/* 402 */       int nWidths = lastChar - firstChar + 1;
/* 403 */       List widths = new ArrayList(nWidths);
/*     */ 
/* 407 */       int defaultWidth = Math.round(widthValues[0] * scaling);
/* 408 */       for (int i = 0; i < nWidths; i++)
/*     */       {
/* 410 */         widths.add(Float.valueOf(defaultWidth));
/*     */       }
/*     */ 
/* 414 */       Encoding glyphlist = WinAnsiEncoding.INSTANCE;
/*     */ 
/* 421 */       for (Map.Entry e : codeToName.entrySet())
/*     */       {
/* 423 */         String name = (String)e.getValue();
/*     */ 
/* 425 */         String c = glyphlist.getCharacter(name);
/* 426 */         int charCode = c.codePointAt(0);
/* 427 */         int gid = uniMap.getGlyphId(charCode);
/* 428 */         if (gid != 0)
/*     */         {
/* 430 */           if (isMonospaced)
/*     */           {
/* 432 */             widths.set(((Integer)e.getKey()).intValue() - firstChar, Float.valueOf(defaultWidth));
/*     */           }
/*     */           else
/*     */           {
/* 436 */             widths.set(((Integer)e.getKey()).intValue() - firstChar, Float.valueOf(Math.round(widthValues[gid] * scaling)));
/*     */           }
/*     */         }
/*     */       }
/* 440 */       setWidths(widths);
/* 441 */       setFirstChar(firstChar);
/* 442 */       setLastChar(lastChar);
/*     */     }
/*     */     finally
/*     */     {
/* 446 */       if (ttf != null)
/*     */       {
/* 448 */         ttf.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public Font getawtFont()
/*     */     throws IOException
/*     */   {
/* 458 */     PDFontDescriptorDictionary fd = (PDFontDescriptorDictionary)getFontDescriptor();
/* 459 */     if (this.awtFont == null)
/*     */     {
/* 461 */       PDStream ff2Stream = fd.getFontFile2();
/* 462 */       if (ff2Stream != null)
/*     */       {
/*     */         try
/*     */         {
/* 467 */           this.awtFont = Font.createFont(0, ff2Stream.createInputStream());
/*     */         }
/*     */         catch (FontFormatException f)
/*     */         {
/*     */           try
/*     */           {
/* 474 */             byte[] fontData = rebuildTTF(fd, ff2Stream.createInputStream());
/* 475 */             if (fontData != null)
/*     */             {
/* 477 */               ByteArrayInputStream bais = new ByteArrayInputStream(fontData);
/* 478 */               this.awtFont = Font.createFont(0, bais);
/*     */             }
/*     */           }
/*     */           catch (FontFormatException e)
/*     */           {
/* 483 */             log.info("Can't read the embedded font " + fd.getFontName());
/*     */           }
/*     */         }
/* 486 */         if (this.awtFont == null)
/*     */         {
/* 488 */           if (fd.getFontName() != null)
/*     */           {
/* 490 */             this.awtFont = FontManager.getAwtFont(fd.getFontName());
/*     */           }
/* 492 */           if (this.awtFont != null)
/*     */           {
/* 494 */             log.info("Using font " + this.awtFont.getName() + " instead");
/*     */           }
/* 496 */           setIsFontSubstituted(true);
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 502 */         if (fd.getFontName() != null)
/*     */         {
/* 504 */           this.awtFont = FontManager.getAwtFont(fd.getFontName());
/*     */         }
/* 506 */         if (this.awtFont == null)
/*     */         {
/* 508 */           log.info("Can't find the specified font " + fd.getFontName());
/*     */ 
/* 510 */           TrueTypeFont ttf = getExternalFontFile2(fd);
/* 511 */           if (ttf != null)
/*     */           {
/*     */             try
/*     */             {
/* 515 */               this.awtFont = Font.createFont(0, ttf.getOriginalData());
/*     */             }
/*     */             catch (FontFormatException f)
/*     */             {
/* 519 */               log.info("Can't read the external fontfile " + fd.getFontName());
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 524 */       if (this.awtFont == null)
/*     */       {
/* 527 */         this.awtFont = FontManager.getStandardFont();
/* 528 */         log.info("Using font " + this.awtFont.getName() + " instead");
/* 529 */         setIsFontSubstituted(true);
/*     */       }
/*     */     }
/* 532 */     return this.awtFont;
/*     */   }
/*     */ 
/*     */   private byte[] rebuildTTF(PDFontDescriptorDictionary fd, InputStream inputStream)
/*     */     throws IOException
/*     */   {
/* 538 */     if ((getFontEncoding() instanceof WinAnsiEncoding))
/*     */     {
/* 540 */       TTFParser ttfParser = new TTFParser(true);
/* 541 */       TrueTypeFont ttf = ttfParser.parseTTF(inputStream);
/* 542 */       TTFSubFont ttfSub = new TTFSubFont(ttf, "PDFBox-Rebuild");
/* 543 */       for (int i = getFirstChar(); i <= getLastChar(); i++)
/*     */       {
/* 545 */         ttfSub.addCharCode(i);
/*     */       }
/* 547 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 548 */       ttfSub.writeToStream(baos);
/* 549 */       return baos.toByteArray();
/*     */     }
/* 551 */     return null;
/*     */   }
/*     */ 
/*     */   private InputStream getExternalTTFData() throws IOException
/*     */   {
/* 556 */     String ttfResource = externalFonts.getProperty("UNKNOWN_FONT");
/* 557 */     String baseFont = getBaseFont();
/* 558 */     if ((baseFont != null) && (externalFonts.containsKey(baseFont)))
/*     */     {
/* 560 */       ttfResource = externalFonts.getProperty(baseFont);
/*     */     }
/* 562 */     return ttfResource != null ? ResourceLoader.loadResource(ttfResource) : null;
/*     */   }
/*     */ 
/*     */   private TrueTypeFont getExternalFontFile2(PDFontDescriptorDictionary fd)
/*     */     throws IOException
/*     */   {
/* 578 */     TrueTypeFont retval = null;
/*     */ 
/* 580 */     if (fd != null)
/*     */     {
/* 582 */       String baseFont = getBaseFont();
/*     */ 
/* 584 */       retval = org.apache.fontbox.util.FontManager.findTTFont(baseFont);
/*     */ 
/* 587 */       if (retval == null)
/*     */       {
/* 589 */         String fontResource = externalFonts.getProperty("UNKNOWN_FONT");
/* 590 */         if ((baseFont != null) && (externalFonts.containsKey(baseFont)))
/*     */         {
/* 593 */           fontResource = externalFonts.getProperty(baseFont);
/*     */         }
/* 595 */         if (fontResource != null)
/*     */         {
/* 597 */           retval = (TrueTypeFont)loadedExternalFonts.get(baseFont);
/* 598 */           if (retval == null)
/*     */           {
/* 600 */             TTFParser ttfParser = new TTFParser();
/* 601 */             InputStream fontStream = ResourceLoader.loadResource(fontResource);
/* 602 */             if (fontStream == null)
/*     */             {
/* 604 */               throw new IOException("Error missing font resource '" + externalFonts.get(baseFont) + "'");
/*     */             }
/* 606 */             retval = ttfParser.parseTTF(fontStream);
/* 607 */             loadedExternalFonts.put(baseFont, retval);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 612 */     return retval;
/*     */   }
/*     */ 
/*     */   public TrueTypeFont getTTFFont()
/*     */     throws IOException
/*     */   {
/* 623 */     if (this.trueTypeFont == null)
/*     */     {
/* 625 */       PDFontDescriptorDictionary fd = (PDFontDescriptorDictionary)getFontDescriptor();
/* 626 */       if (fd != null)
/*     */       {
/* 628 */         PDStream ff2Stream = fd.getFontFile2();
/* 629 */         if (ff2Stream != null)
/*     */         {
/* 631 */           TTFParser ttfParser = new TTFParser(true);
/* 632 */           this.trueTypeFont = ttfParser.parseTTF(ff2Stream.createInputStream());
/*     */         }
/*     */       }
/* 635 */       if (this.trueTypeFont == null)
/*     */       {
/* 638 */         this.trueTypeFont = org.apache.fontbox.util.FontManager.findTTFont(getBaseFont());
/*     */       }
/*     */     }
/* 641 */     return this.trueTypeFont;
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 647 */     super.clear();
/* 648 */     this.cmapWinUnicode = null;
/* 649 */     this.cmapWinSymbol = null;
/* 650 */     this.cmapMacintoshSymbol = null;
/* 651 */     this.trueTypeFont = null;
/* 652 */     if (this.advanceWidths != null)
/*     */     {
/* 654 */       this.advanceWidths.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */   public float getFontWidth(int charCode)
/*     */   {
/* 661 */     float width = super.getFontWidth(charCode);
/* 662 */     if (width <= 0.0F)
/*     */     {
/* 664 */       if (this.advanceWidths.containsKey(Integer.valueOf(charCode)))
/*     */       {
/* 666 */         width = ((Float)this.advanceWidths.get(Integer.valueOf(charCode))).floatValue();
/*     */       }
/*     */       else
/*     */       {
/* 670 */         TrueTypeFont ttf = null;
/*     */         try
/*     */         {
/* 673 */           ttf = getTTFFont();
/* 674 */           if (ttf != null)
/*     */           {
/* 676 */             int code = getGlyphcode(charCode);
/* 677 */             width = ttf.getAdvanceWidth(code);
/* 678 */             int unitsPerEM = ttf.getUnitsPerEm();
/*     */ 
/* 680 */             if (unitsPerEM != 1000)
/*     */             {
/* 682 */               width *= 1000.0F / unitsPerEM;
/*     */             }
/*     */           }
/*     */         }
/*     */         catch (IOException exception)
/*     */         {
/* 688 */           width = 250.0F;
/*     */         }
/* 690 */         this.advanceWidths.put(Integer.valueOf(charCode), Float.valueOf(width));
/*     */       }
/*     */     }
/* 693 */     return width;
/*     */   }
/*     */ 
/*     */   private int getGlyphcode(int code)
/*     */   {
/* 698 */     extractCMaps();
/* 699 */     int result = 0;
/* 700 */     if ((getFontEncoding() != null) && (!isSymbolicFont()))
/*     */     {
/*     */       try
/*     */       {
/* 704 */         String charactername = getFontEncoding().getName(code);
/* 705 */         if (charactername != null)
/*     */         {
/* 707 */           if (this.cmapWinUnicode != null)
/*     */           {
/* 709 */             String unicode = Encoding.getCharacterForName(charactername);
/* 710 */             if (unicode != null)
/*     */             {
/* 712 */               result = unicode.codePointAt(0);
/*     */             }
/* 714 */             result = this.cmapWinUnicode.getGlyphId(result);
/*     */           }
/* 716 */           else if ((this.cmapMacintoshSymbol != null) && (MacOSRomanEncoding.INSTANCE.hasCodeForName(charactername)))
/*     */           {
/* 718 */             result = MacOSRomanEncoding.INSTANCE.getCode(charactername);
/* 719 */             result = this.cmapMacintoshSymbol.getGlyphId(result);
/*     */           }
/* 721 */           else if (this.cmapWinSymbol != null)
/*     */           {
/* 726 */             result = this.cmapWinSymbol.getGlyphId(code);
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (IOException exception)
/*     */       {
/* 732 */         log.error("Caught an exception getGlyhcode: " + exception);
/*     */       }
/*     */     }
/* 735 */     else if ((getFontEncoding() == null) || (isSymbolicFont()))
/*     */     {
/* 737 */       if (this.cmapWinSymbol != null)
/*     */       {
/* 739 */         result = this.cmapWinSymbol.getGlyphId(code);
/* 740 */         if ((code >= 0) && (code <= 255))
/*     */         {
/* 745 */           if (result == 0)
/*     */           {
/* 748 */             result = this.cmapWinSymbol.getGlyphId(code + 61440);
/*     */           }
/* 750 */           if (result == 0)
/*     */           {
/* 753 */             result = this.cmapWinSymbol.getGlyphId(code + 61696);
/*     */           }
/* 755 */           if (result == 0)
/*     */           {
/* 758 */             result = this.cmapWinSymbol.getGlyphId(code + 61952);
/*     */           }
/*     */         }
/*     */       }
/* 762 */       else if (this.cmapMacintoshSymbol != null)
/*     */       {
/* 764 */         result = this.cmapMacintoshSymbol.getGlyphId(code);
/*     */       }
/*     */     }
/* 767 */     return result;
/*     */   }
/*     */ 
/*     */   private void extractCMaps()
/*     */   {
/* 775 */     if (!this.cmapInitialized)
/*     */     {
/*     */       try
/*     */       {
/* 779 */         getTTFFont();
/*     */       }
/*     */       catch (IOException exception)
/*     */       {
/* 783 */         log.error("Can't read the true type font", exception);
/*     */       }
/* 785 */       CMAPTable cmapTable = this.trueTypeFont.getCMAP();
/* 786 */       if (cmapTable != null)
/*     */       {
/* 789 */         CMAPEncodingEntry[] cmaps = cmapTable.getCmaps();
/* 790 */         for (int i = 0; i < cmaps.length; i++)
/*     */         {
/* 792 */           if (3 == cmaps[i].getPlatformId())
/*     */           {
/* 794 */             if (1 == cmaps[i].getPlatformEncodingId())
/*     */             {
/* 796 */               this.cmapWinUnicode = cmaps[i];
/*     */             }
/* 798 */             else if (0 == cmaps[i].getPlatformEncodingId())
/*     */             {
/* 800 */               this.cmapWinSymbol = cmaps[i];
/*     */             }
/*     */           }
/* 803 */           else if (1 == cmaps[i].getPlatformId())
/*     */           {
/* 805 */             if (0 == cmaps[i].getPlatformEncodingId())
/*     */             {
/* 807 */               this.cmapMacintoshSymbol = cmaps[i];
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 812 */       this.cmapInitialized = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/* 106 */       ResourceLoader.loadProperties("org/apache/pdfbox/resources/PDFBox_External_Fonts.properties", externalFonts);
/*     */     }
/*     */     catch (IOException io)
/*     */     {
/* 112 */       throw new RuntimeException("Error loading font resources", io);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.font.PDTrueTypeFont
 * JD-Core Version:    0.6.2
 */