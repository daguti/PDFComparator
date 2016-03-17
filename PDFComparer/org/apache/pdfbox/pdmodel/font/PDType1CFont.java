/*     */ package org.apache.pdfbox.pdmodel.font;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.FontFormatException;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.fontbox.afm.AFMParser;
/*     */ import org.apache.fontbox.afm.FontMetric;
/*     */ import org.apache.fontbox.cff.AFMFormatter;
/*     */ import org.apache.fontbox.cff.CFFFont;
/*     */ import org.apache.fontbox.cff.CFFFont.Mapping;
/*     */ import org.apache.fontbox.cff.CFFParser;
/*     */ import org.apache.fontbox.cff.Type1FontFormatter;
/*     */ import org.apache.fontbox.cff.charset.CFFCharset;
/*     */ import org.apache.fontbox.cff.charset.CFFCharset.Entry;
/*     */ import org.apache.fontbox.cff.encoding.CFFEncoding;
/*     */ import org.apache.fontbox.cff.encoding.CFFEncoding.Entry;
/*     */ import org.apache.fontbox.util.BoundingBox;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.encoding.Encoding;
/*     */ import org.apache.pdfbox.encoding.EncodingManager;
/*     */ import org.apache.pdfbox.exceptions.WrappedIOException;
/*     */ import org.apache.pdfbox.pdmodel.common.PDMatrix;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ 
/*     */ public class PDType1CFont extends PDSimpleFont
/*     */ {
/*  67 */   private CFFFont cffFont = null;
/*     */ 
/*  69 */   private String fontname = null;
/*     */ 
/*  71 */   private Map<Integer, String> sidToName = new HashMap();
/*     */ 
/*  73 */   private Map<Integer, Integer> codeToSID = new HashMap();
/*     */ 
/*  75 */   private Map<Integer, String> sidToCharacter = new HashMap();
/*     */ 
/*  77 */   private Map<String, Integer> characterToSID = new HashMap();
/*     */ 
/*  79 */   private FontMetric fontMetric = null;
/*     */ 
/*  81 */   private Font awtFont = null;
/*     */ 
/*  83 */   private Map<String, Float> glyphWidths = new HashMap();
/*     */ 
/*  85 */   private Map<String, Float> glyphHeights = new HashMap();
/*     */ 
/*  87 */   private Float avgWidth = null;
/*     */ 
/*  89 */   private PDRectangle fontBBox = null;
/*     */ 
/*  91 */   private static final Log log = LogFactory.getLog(PDType1CFont.class);
/*     */ 
/*  93 */   private static final byte[] SPACE_BYTES = { 32 };
/*     */ 
/*     */   public PDType1CFont(COSDictionary fontDictionary)
/*     */     throws IOException
/*     */   {
/* 101 */     super(fontDictionary);
/* 102 */     load();
/*     */   }
/*     */ 
/*     */   public String encode(byte[] bytes, int offset, int length)
/*     */     throws IOException
/*     */   {
/* 111 */     String character = getCharacter(bytes, offset, length);
/* 112 */     if (character == null)
/*     */     {
/* 114 */       log.debug("No character for code " + (bytes[offset] & 0xFF) + " in " + this.fontname);
/* 115 */       return null;
/*     */     }
/* 117 */     return character;
/*     */   }
/*     */ 
/*     */   private String getCharacter(byte[] bytes, int offset, int length)
/*     */   {
/* 122 */     String character = null;
/*     */ 
/* 126 */     if (getFontDescriptor().getCharSet() != null)
/*     */     {
/* 128 */       int code = getCodeFromArray(bytes, offset, length);
/* 129 */       if (this.codeToSID.containsKey(Integer.valueOf(code)))
/*     */       {
/* 131 */         code = ((Integer)this.codeToSID.get(Integer.valueOf(code))).intValue();
/*     */       }
/* 133 */       if (this.sidToCharacter.containsKey(Integer.valueOf(code)))
/*     */       {
/* 135 */         character = (String)this.sidToCharacter.get(Integer.valueOf(code));
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 141 */       character = getStringFromArray(bytes, offset, length);
/*     */     }
/* 143 */     return character;
/*     */   }
/*     */ 
/*     */   public int encodeToCID(byte[] bytes, int offset, int length)
/*     */   {
/* 152 */     if (length > 2)
/*     */     {
/* 154 */       return -1;
/*     */     }
/* 156 */     int code = bytes[offset] & 0xFF;
/* 157 */     if (length == 2)
/*     */     {
/* 159 */       code = code * 256 + bytes[(offset + 1)] & 0xFF;
/*     */     }
/* 161 */     return code;
/*     */   }
/*     */ 
/*     */   public float getFontWidth(byte[] bytes, int offset, int length)
/*     */     throws IOException
/*     */   {
/* 169 */     String name = getName(bytes, offset, length);
/* 170 */     if ((name == null) && (!Arrays.equals(SPACE_BYTES, bytes)))
/*     */     {
/* 172 */       log.debug("No name for code " + (bytes[offset] & 0xFF) + " in " + this.cffFont.getName());
/*     */ 
/* 174 */       return 0.0F;
/*     */     }
/*     */ 
/* 177 */     Float width = (Float)this.glyphWidths.get(name);
/* 178 */     if (width == null)
/*     */     {
/* 180 */       width = Float.valueOf(getFontMetric().getCharacterWidth(name));
/* 181 */       this.glyphWidths.put(name, width);
/*     */     }
/*     */ 
/* 184 */     return width.floatValue();
/*     */   }
/*     */ 
/*     */   public float getFontHeight(byte[] bytes, int offset, int length)
/*     */     throws IOException
/*     */   {
/* 192 */     String name = getName(bytes, offset, length);
/* 193 */     if (name == null)
/*     */     {
/* 195 */       log.debug("No name for code " + (bytes[offset] & 0xFF) + " in " + this.cffFont.getName());
/*     */ 
/* 197 */       return 0.0F;
/*     */     }
/*     */ 
/* 200 */     Float height = (Float)this.glyphHeights.get(name);
/* 201 */     if (height == null)
/*     */     {
/* 203 */       height = Float.valueOf(getFontMetric().getCharacterHeight(name));
/* 204 */       this.glyphHeights.put(name, height);
/*     */     }
/*     */ 
/* 207 */     return height.floatValue();
/*     */   }
/*     */ 
/*     */   private String getName(byte[] bytes, int offset, int length)
/*     */   {
/* 212 */     if (length > 2)
/*     */     {
/* 214 */       return null;
/*     */     }
/*     */ 
/* 217 */     int code = bytes[offset] & 0xFF;
/* 218 */     if (length == 2)
/*     */     {
/* 220 */       code = code * 256 + bytes[(offset + 1)] & 0xFF;
/*     */     }
/*     */ 
/* 223 */     return (String)this.sidToName.get(Integer.valueOf(code));
/*     */   }
/*     */ 
/*     */   public float getStringWidth(String string)
/*     */     throws IOException
/*     */   {
/* 231 */     float width = 0.0F;
/*     */ 
/* 233 */     for (int i = 0; i < string.length(); i++)
/*     */     {
/* 235 */       String character = string.substring(i, i + 1);
/*     */ 
/* 237 */       Integer code = getCode(character);
/* 238 */       if (code == null)
/*     */       {
/* 240 */         log.debug("No code for character " + character);
/*     */ 
/* 242 */         return 0.0F;
/*     */       }
/*     */ 
/* 245 */       width += getFontWidth(new byte[] { (byte)code.intValue() }, 0, 1);
/*     */     }
/*     */ 
/* 248 */     return width;
/*     */   }
/*     */ 
/*     */   private Integer getCode(String character)
/*     */   {
/* 253 */     return (Integer)this.characterToSID.get(character);
/*     */   }
/*     */ 
/*     */   public float getAverageFontWidth()
/*     */     throws IOException
/*     */   {
/* 262 */     if (this.avgWidth == null)
/*     */     {
/* 264 */       this.avgWidth = Float.valueOf(getFontMetric().getAverageCharacterWidth());
/*     */     }
/*     */ 
/* 267 */     return this.avgWidth.floatValue();
/*     */   }
/*     */ 
/*     */   public PDRectangle getFontBoundingBox()
/*     */     throws IOException
/*     */   {
/* 275 */     if (this.fontBBox == null)
/*     */     {
/* 277 */       this.fontBBox = new PDRectangle(getFontMetric().getFontBBox());
/*     */     }
/*     */ 
/* 280 */     return this.fontBBox;
/*     */   }
/*     */ 
/*     */   public PDMatrix getFontMatrix()
/*     */   {
/* 288 */     if (this.fontMatrix == null)
/*     */     {
/* 290 */       List numbers = (List)this.cffFont.getProperty("FontMatrix");
/* 291 */       if ((numbers != null) && (numbers.size() == 6))
/*     */       {
/* 293 */         COSArray array = new COSArray();
/* 294 */         for (Number number : numbers)
/*     */         {
/* 296 */           array.add(new COSFloat(number.floatValue()));
/*     */         }
/* 298 */         this.fontMatrix = new PDMatrix(array);
/*     */       }
/*     */       else
/*     */       {
/* 302 */         super.getFontMatrix();
/*     */       }
/*     */     }
/* 305 */     return this.fontMatrix;
/*     */   }
/*     */ 
/*     */   public Font getawtFont()
/*     */     throws IOException
/*     */   {
/* 313 */     if (this.awtFont == null)
/*     */     {
/* 315 */       this.awtFont = prepareAwtFont(this.cffFont);
/*     */     }
/* 317 */     return this.awtFont;
/*     */   }
/*     */ 
/*     */   private FontMetric getFontMetric()
/*     */   {
/* 322 */     if (this.fontMetric == null)
/*     */     {
/*     */       try
/*     */       {
/* 326 */         this.fontMetric = prepareFontMetric(this.cffFont);
/*     */       }
/*     */       catch (IOException exception)
/*     */       {
/* 330 */         log.error("An error occured while extracting the font metrics!", exception);
/*     */       }
/*     */     }
/* 333 */     return this.fontMetric;
/*     */   }
/*     */ 
/*     */   private void load() throws IOException
/*     */   {
/* 338 */     byte[] cffBytes = loadBytes();
/*     */ 
/* 340 */     CFFParser cffParser = new CFFParser();
/* 341 */     List fonts = cffParser.parse(cffBytes);
/*     */ 
/* 343 */     String baseFontName = getBaseFont();
/* 344 */     if ((fonts.size() > 1) && (baseFontName != null))
/*     */     {
/* 346 */       for (CFFFont font : fonts)
/*     */       {
/* 348 */         if (baseFontName.equals(font.getName()))
/*     */         {
/* 350 */           this.cffFont = font;
/* 351 */           break;
/*     */         }
/*     */       }
/*     */     }
/* 355 */     if (this.cffFont == null)
/*     */     {
/* 357 */       this.cffFont = ((CFFFont)fonts.get(0));
/*     */     }
/*     */ 
/* 361 */     this.fontname = this.cffFont.getName();
/*     */ 
/* 363 */     Number defaultWidthX = (Number)this.cffFont.getProperty("defaultWidthX");
/* 364 */     this.glyphWidths.put(null, Float.valueOf(defaultWidthX.floatValue()));
/*     */ 
/* 366 */     CFFEncoding encoding = this.cffFont.getEncoding();
/* 367 */     PDFEncoding pdfEncoding = new PDFEncoding(encoding, null);
/*     */ 
/* 369 */     CFFCharset charset = this.cffFont.getCharset();
/* 370 */     PDFCharset pdfCharset = new PDFCharset(charset, null);
/*     */ 
/* 372 */     Map charStringsDict = this.cffFont.getCharStringsDict();
/* 373 */     Map pdfCharStringsDict = new LinkedHashMap();
/* 374 */     pdfCharStringsDict.put(".notdef", charStringsDict.get(".notdef"));
/*     */ 
/* 376 */     Map codeToNameMap = new LinkedHashMap();
/*     */ 
/* 378 */     Collection mappings = this.cffFont.getMappings();
/* 379 */     for (Iterator it = mappings.iterator(); it.hasNext(); )
/*     */     {
/* 381 */       CFFFont.Mapping mapping = (CFFFont.Mapping)it.next();
/* 382 */       Integer code = Integer.valueOf(mapping.getCode());
/* 383 */       String name = mapping.getName();
/* 384 */       codeToNameMap.put(code, name);
/*     */     }
/*     */ 
/* 387 */     Set knownNames = new HashSet(codeToNameMap.values());
/*     */ 
/* 389 */     Map codeToNameOverride = loadOverride();
/* 390 */     for (Iterator it = codeToNameOverride.entrySet().iterator(); it.hasNext(); )
/*     */     {
/* 392 */       Map.Entry entry = (Map.Entry)it.next();
/* 393 */       Integer code = (Integer)entry.getKey();
/* 394 */       String name = (String)entry.getValue();
/* 395 */       if (knownNames.contains(name))
/*     */       {
/* 397 */         codeToNameMap.put(code, name);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*     */     Map nameToCharacter;
/*     */     try
/*     */     {
/* 405 */       Field nameToCharacterField = Encoding.class.getDeclaredField("NAME_TO_CHARACTER");
/* 406 */       nameToCharacterField.setAccessible(true);
/* 407 */       nameToCharacter = (Map)nameToCharacterField.get(null);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 411 */       throw new RuntimeException(e);
/*     */     }
/*     */ 
/* 414 */     for (Iterator it = codeToNameMap.entrySet().iterator(); it.hasNext(); )
/*     */     {
/* 416 */       Map.Entry entry = (Map.Entry)it.next();
/* 417 */       Integer code = (Integer)entry.getKey();
/* 418 */       String name = (String)entry.getValue();
/* 419 */       String uniName = "uni";
/* 420 */       String character = (String)nameToCharacter.get(name);
/* 421 */       if (character != null)
/*     */       {
/* 423 */         for (int j = 0; j < character.length(); j++)
/*     */         {
/* 425 */           uniName = uniName + hexString(character.charAt(j), 4);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 430 */         uniName = uniName + hexString(code.intValue(), 4);
/* 431 */         character = String.valueOf((char)code.intValue());
/*     */       }
/* 433 */       pdfEncoding.register(code.intValue(), code.intValue());
/* 434 */       pdfCharset.register(code.intValue(), uniName);
/* 435 */       pdfCharStringsDict.put(uniName, charStringsDict.get(name));
/*     */     }
/*     */ 
/* 438 */     this.cffFont.setEncoding(pdfEncoding);
/* 439 */     this.cffFont.setCharset(pdfCharset);
/* 440 */     charStringsDict.clear();
/* 441 */     charStringsDict.putAll(pdfCharStringsDict);
/*     */ 
/* 444 */     Encoding fontEncoding = getFontEncoding();
/* 445 */     Map nameToCode = fontEncoding != null ? fontEncoding.getNameToCodeMap() : null;
/* 446 */     for (CFFFont.Mapping mapping : mappings)
/*     */     {
/* 448 */       int sid = mapping.getSID();
/* 449 */       String name = mapping.getName();
/* 450 */       String character = null;
/* 451 */       if ((nameToCode != null) && (nameToCode.containsKey(name)))
/*     */       {
/* 453 */         sid = ((Integer)nameToCode.get(name)).intValue();
/* 454 */         character = fontEncoding.getCharacter(name);
/*     */       }
/* 456 */       if (character == null)
/*     */       {
/* 458 */         character = Encoding.getCharacterForName(name);
/*     */       }
/* 460 */       this.sidToName.put(Integer.valueOf(sid), name);
/* 461 */       this.codeToSID.put(Integer.valueOf(mapping.getCode()), Integer.valueOf(sid));
/* 462 */       if (character != null)
/*     */       {
/* 464 */         this.sidToCharacter.put(Integer.valueOf(sid), character);
/* 465 */         this.characterToSID.put(character, Integer.valueOf(sid));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private byte[] loadBytes()
/*     */     throws IOException
/*     */   {
/* 473 */     PDFontDescriptor fd = getFontDescriptor();
/* 474 */     if ((fd != null) && ((fd instanceof PDFontDescriptorDictionary)))
/*     */     {
/* 476 */       PDStream ff3Stream = ((PDFontDescriptorDictionary)fd).getFontFile3();
/* 477 */       if (ff3Stream != null)
/*     */       {
/* 479 */         ByteArrayOutputStream os = new ByteArrayOutputStream();
/*     */ 
/* 481 */         InputStream is = ff3Stream.createInputStream();
/*     */         try
/*     */         {
/* 484 */           byte[] buf = new byte[512];
/*     */           while (true)
/*     */           {
/* 487 */             int count = is.read(buf);
/* 488 */             if (count < 0)
/*     */             {
/*     */               break;
/*     */             }
/* 492 */             os.write(buf, 0, count);
/*     */           }
/*     */         }
/*     */         finally
/*     */         {
/* 497 */           is.close();
/*     */         }
/*     */ 
/* 500 */         return os.toByteArray();
/*     */       }
/*     */     }
/*     */ 
/* 504 */     throw new IOException();
/*     */   }
/*     */ 
/*     */   private static String hexString(int code, int length)
/*     */   {
/* 509 */     String string = Integer.toHexString(code);
/* 510 */     while (string.length() < length)
/*     */     {
/* 512 */       string = "0" + string;
/*     */     }
/*     */ 
/* 515 */     return string;
/*     */   }
/*     */ 
/*     */   private FontMetric prepareFontMetric(CFFFont font) throws IOException
/*     */   {
/* 520 */     byte[] afmBytes = AFMFormatter.format(font);
/*     */ 
/* 522 */     InputStream is = new ByteArrayInputStream(afmBytes);
/*     */     try
/*     */     {
/* 525 */       AFMParser afmParser = new AFMParser(is);
/* 526 */       afmParser.parse();
/*     */ 
/* 528 */       FontMetric result = afmParser.getResult();
/*     */ 
/* 531 */       BoundingBox bounds = result.getFontBBox();
/* 532 */       List numbers = Arrays.asList(new Integer[] { Integer.valueOf((int)bounds.getLowerLeftX()), Integer.valueOf((int)bounds.getLowerLeftY()), Integer.valueOf((int)bounds.getUpperRightX()), Integer.valueOf((int)bounds.getUpperRightY()) });
/*     */ 
/* 538 */       font.addValueToTopDict("FontBBox", numbers);
/*     */ 
/* 540 */       return result;
/*     */     }
/*     */     finally
/*     */     {
/* 544 */       is.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   private Map<Integer, String> loadOverride() throws IOException
/*     */   {
/* 550 */     Map result = new LinkedHashMap();
/* 551 */     COSBase encoding = getEncoding();
/* 552 */     if ((encoding instanceof COSName))
/*     */     {
/* 554 */       COSName name = (COSName)encoding;
/* 555 */       result.putAll(loadEncoding(name));
/*     */     }
/* 557 */     else if ((encoding instanceof COSDictionary))
/*     */     {
/* 559 */       COSDictionary encodingDic = (COSDictionary)encoding;
/* 560 */       COSName baseName = (COSName)encodingDic.getDictionaryObject(COSName.BASE_ENCODING);
/* 561 */       if (baseName != null)
/*     */       {
/* 563 */         result.putAll(loadEncoding(baseName));
/*     */       }
/* 565 */       COSArray differences = (COSArray)encodingDic.getDictionaryObject(COSName.DIFFERENCES);
/* 566 */       if (differences != null)
/*     */       {
/* 568 */         result.putAll(loadDifferences(differences));
/*     */       }
/*     */     }
/*     */ 
/* 572 */     return result;
/*     */   }
/*     */ 
/*     */   private Map<Integer, String> loadEncoding(COSName name) throws IOException
/*     */   {
/* 577 */     Map result = new LinkedHashMap();
/* 578 */     Encoding encoding = EncodingManager.INSTANCE.getEncoding(name);
/* 579 */     Iterator it = encoding.getCodeToNameMap().entrySet().iterator();
/* 580 */     while (it.hasNext())
/*     */     {
/* 582 */       Map.Entry entry = (Map.Entry)it.next();
/* 583 */       result.put(entry.getKey(), entry.getValue());
/*     */     }
/*     */ 
/* 586 */     return result;
/*     */   }
/*     */ 
/*     */   private Map<Integer, String> loadDifferences(COSArray differences)
/*     */   {
/* 591 */     Map result = new LinkedHashMap();
/* 592 */     Integer code = null;
/* 593 */     for (int i = 0; i < differences.size(); i++)
/*     */     {
/* 595 */       COSBase element = differences.get(i);
/* 596 */       if ((element instanceof COSNumber))
/*     */       {
/* 598 */         COSNumber number = (COSNumber)element;
/* 599 */         code = Integer.valueOf(number.intValue());
/*     */       }
/* 603 */       else if ((element instanceof COSName))
/*     */       {
/* 605 */         COSName name = (COSName)element;
/* 606 */         result.put(code, name.getName());
/* 607 */         code = Integer.valueOf(code.intValue() + 1);
/*     */       }
/*     */     }
/*     */ 
/* 611 */     return result;
/*     */   }
/*     */ 
/*     */   private static Font prepareAwtFont(CFFFont font)
/*     */     throws IOException
/*     */   {
/* 617 */     byte[] type1Bytes = Type1FontFormatter.format(font);
/*     */ 
/* 619 */     InputStream is = new ByteArrayInputStream(type1Bytes);
/*     */     try
/*     */     {
/* 622 */       return Font.createFont(1, is);
/*     */     }
/*     */     catch (FontFormatException ffe)
/*     */     {
/* 626 */       throw new WrappedIOException(ffe);
/*     */     }
/*     */     finally
/*     */     {
/* 630 */       is.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 682 */     super.clear();
/* 683 */     this.cffFont = null;
/* 684 */     this.fontMetric = null;
/* 685 */     this.fontBBox = null;
/* 686 */     if (this.characterToSID != null)
/*     */     {
/* 688 */       this.characterToSID.clear();
/* 689 */       this.characterToSID = null;
/*     */     }
/* 691 */     if (this.codeToSID != null)
/*     */     {
/* 693 */       this.codeToSID.clear();
/* 694 */       this.codeToSID = null;
/*     */     }
/* 696 */     if (this.glyphHeights != null)
/*     */     {
/* 698 */       this.glyphHeights.clear();
/* 699 */       this.glyphHeights = null;
/*     */     }
/* 701 */     if (this.glyphWidths != null)
/*     */     {
/* 703 */       this.glyphWidths.clear();
/* 704 */       this.glyphWidths = null;
/*     */     }
/* 706 */     if (this.sidToCharacter != null)
/*     */     {
/* 708 */       this.sidToCharacter.clear();
/* 709 */       this.sidToCharacter = null;
/*     */     }
/* 711 */     if (this.sidToName != null)
/*     */     {
/* 713 */       this.sidToName.clear();
/* 714 */       this.sidToName = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class PDFCharset extends CFFCharset
/*     */   {
/*     */     private PDFCharset(CFFCharset parent)
/*     */     {
/* 665 */       Iterator parentEntries = parent.getEntries().iterator();
/* 666 */       while (parentEntries.hasNext())
/*     */       {
/* 668 */         addEntry((CFFCharset.Entry)parentEntries.next());
/*     */       }
/*     */     }
/*     */ 
/*     */     public boolean isFontSpecific()
/*     */     {
/* 674 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class PDFEncoding extends CFFEncoding
/*     */   {
/*     */     private PDFEncoding(CFFEncoding parent)
/*     */     {
/* 643 */       Iterator parentEntries = parent.getEntries().iterator();
/* 644 */       while (parentEntries.hasNext())
/*     */       {
/* 646 */         addEntry((CFFEncoding.Entry)parentEntries.next());
/*     */       }
/*     */     }
/*     */ 
/*     */     public boolean isFontSpecific()
/*     */     {
/* 652 */       return true;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.font.PDType1CFont
 * JD-Core Version:    0.6.2
 */