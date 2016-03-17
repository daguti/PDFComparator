/*     */ package org.apache.pdfbox.pdmodel.font;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.font.FontRenderContext;
/*     */ import java.awt.font.GlyphVector;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.fontbox.afm.FontMetric;
/*     */ import org.apache.fontbox.cmap.CMap;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.encoding.DictionaryEncoding;
/*     */ import org.apache.pdfbox.encoding.Encoding;
/*     */ import org.apache.pdfbox.encoding.EncodingManager;
/*     */ import org.apache.pdfbox.io.IOUtils;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ import org.apache.pdfbox.util.ResourceLoader;
/*     */ 
/*     */ public abstract class PDSimpleFont extends PDFont
/*     */ {
/*  59 */   private final HashMap<Integer, Float> mFontSizes = new HashMap(128);
/*     */ 
/*  62 */   private float avgFontWidth = 0.0F;
/*  63 */   private float avgFontHeight = 0.0F;
/*  64 */   private float fontWidthOfSpace = -1.0F;
/*     */ 
/*  66 */   private static final byte[] SPACE_BYTES = { 32 };
/*     */ 
/*  72 */   private static final Log LOG = LogFactory.getLog(PDSimpleFont.class);
/*     */ 
/* 490 */   private boolean isFontSubstituted = false;
/*     */ 
/*     */   public PDSimpleFont()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PDSimpleFont(COSDictionary fontDictionary)
/*     */   {
/*  89 */     super(fontDictionary);
/*     */   }
/*     */ 
/*     */   public Font getawtFont()
/*     */     throws IOException
/*     */   {
/* 100 */     LOG.error("Not yet implemented:" + getClass().getName());
/* 101 */     return null;
/*     */   }
/*     */ 
/*     */   public void drawString(String string, int[] codePoints, Graphics g, float fontSize, AffineTransform at, float x, float y)
/*     */     throws IOException
/*     */   {
/* 110 */     Font awtFont = getawtFont();
/* 111 */     FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
/* 112 */     GlyphVector glyphs = null;
/* 113 */     boolean useCodepoints = (codePoints != null) && (isType0Font());
/* 114 */     PDFont descendantFont = useCodepoints ? ((PDType0Font)this).getDescendantFont() : null;
/*     */ 
/* 116 */     if ((useCodepoints) && (!descendantFont.getFontDescriptor().isSymbolic()))
/*     */     {
/* 118 */       PDCIDFontType2Font cid2Font = null;
/* 119 */       if ((descendantFont instanceof PDCIDFontType2Font))
/*     */       {
/* 121 */         cid2Font = (PDCIDFontType2Font)descendantFont;
/*     */       }
/* 123 */       if (((cid2Font != null) && (cid2Font.hasCIDToGIDMap())) || (this.isFontSubstituted))
/*     */       {
/* 126 */         glyphs = awtFont.createGlyphVector(frc, string);
/*     */       }
/*     */       else
/*     */       {
/* 130 */         glyphs = awtFont.createGlyphVector(frc, codePoints);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 138 */       if ((!isType1Font()) && (awtFont.canDisplayUpTo(string) != -1))
/*     */       {
/* 140 */         LOG.warn("Changing font on <" + string + "> from <" + awtFont.getName() + "> to the default font");
/*     */ 
/* 142 */         awtFont = Font.decode(null).deriveFont(1.0F);
/*     */       }
/* 144 */       glyphs = awtFont.createGlyphVector(frc, string);
/*     */     }
/* 146 */     Graphics2D g2d = (Graphics2D)g;
/* 147 */     g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/* 148 */     writeFont(g2d, at, x, y, glyphs);
/*     */   }
/*     */ 
/*     */   public float getFontHeight(byte[] c, int offset, int length)
/*     */     throws IOException
/*     */   {
/* 165 */     if (this.avgFontHeight > 0.0F)
/*     */     {
/* 167 */       return this.avgFontHeight;
/*     */     }
/* 169 */     float retval = 0.0F;
/* 170 */     FontMetric metric = getAFM();
/* 171 */     if (metric != null)
/*     */     {
/* 173 */       int code = getCodeFromArray(c, offset, length);
/* 174 */       Encoding encoding = getFontEncoding();
/* 175 */       String characterName = encoding.getName(code);
/* 176 */       retval = metric.getCharacterHeight(characterName);
/*     */     }
/*     */     else
/*     */     {
/* 180 */       PDFontDescriptor desc = getFontDescriptor();
/* 181 */       if (desc != null)
/*     */       {
/* 187 */         PDRectangle fontBBox = desc.getFontBoundingBox();
/* 188 */         if (fontBBox != null)
/*     */         {
/* 190 */           retval = fontBBox.getHeight() / 2.0F;
/*     */         }
/* 192 */         if (retval == 0.0F)
/*     */         {
/* 194 */           retval = desc.getCapHeight();
/*     */         }
/* 196 */         if (retval == 0.0F)
/*     */         {
/* 198 */           retval = desc.getAscent();
/*     */         }
/* 200 */         if (retval == 0.0F)
/*     */         {
/* 202 */           retval = desc.getXHeight();
/* 203 */           if (retval > 0.0F)
/*     */           {
/* 205 */             retval -= desc.getDescent();
/*     */           }
/*     */         }
/* 208 */         this.avgFontHeight = retval;
/*     */       }
/*     */     }
/* 211 */     return retval;
/*     */   }
/*     */ 
/*     */   public float getFontWidth(byte[] c, int offset, int length)
/*     */     throws IOException
/*     */   {
/* 227 */     int code = getCodeFromArray(c, offset, length);
/* 228 */     Float fontWidth = (Float)this.mFontSizes.get(Integer.valueOf(code));
/* 229 */     if (fontWidth == null)
/*     */     {
/* 231 */       fontWidth = Float.valueOf(getFontWidth(code));
/* 232 */       if (fontWidth.floatValue() <= 0.0F)
/*     */       {
/* 235 */         fontWidth = Float.valueOf(getFontWidthFromAFMFile(code));
/*     */       }
/* 237 */       this.mFontSizes.put(Integer.valueOf(code), fontWidth);
/*     */     }
/* 239 */     return fontWidth.floatValue();
/*     */   }
/*     */ 
/*     */   public float getAverageFontWidth()
/*     */     throws IOException
/*     */   {
/* 251 */     float average = 0.0F;
/*     */ 
/* 254 */     if (this.avgFontWidth != 0.0F)
/*     */     {
/* 256 */       average = this.avgFontWidth;
/*     */     }
/*     */     else
/*     */     {
/* 260 */       float totalWidth = 0.0F;
/* 261 */       float characterCount = 0.0F;
/* 262 */       COSArray widths = (COSArray)this.font.getDictionaryObject(COSName.WIDTHS);
/* 263 */       if (widths != null)
/*     */       {
/* 265 */         for (int i = 0; i < widths.size(); i++)
/*     */         {
/* 267 */           COSNumber fontWidth = (COSNumber)widths.getObject(i);
/* 268 */           if (fontWidth.floatValue() > 0.0F)
/*     */           {
/* 270 */             totalWidth += fontWidth.floatValue();
/* 271 */             characterCount += 1.0F;
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 276 */       if (totalWidth > 0.0F)
/*     */       {
/* 278 */         average = totalWidth / characterCount;
/*     */       }
/*     */       else
/*     */       {
/* 282 */         average = getAverageFontWidthFromAFMFile();
/*     */       }
/* 284 */       this.avgFontWidth = average;
/*     */     }
/* 286 */     return average;
/*     */   }
/*     */ 
/*     */   public COSBase getToUnicode()
/*     */   {
/* 297 */     return this.font.getDictionaryObject(COSName.TO_UNICODE);
/*     */   }
/*     */ 
/*     */   public void setToUnicode(COSBase unicode)
/*     */   {
/* 307 */     this.font.setItem(COSName.TO_UNICODE, unicode);
/*     */   }
/*     */ 
/*     */   public PDRectangle getFontBoundingBox()
/*     */     throws IOException
/*     */   {
/* 319 */     return getFontDescriptor().getFontBoundingBox();
/*     */   }
/*     */ 
/*     */   protected void writeFont(Graphics2D g2d, AffineTransform at, float x, float y, GlyphVector glyphs)
/*     */   {
/* 336 */     if (!at.isIdentity())
/*     */     {
/* 338 */       for (int i = 0; i < glyphs.getNumGlyphs(); i++)
/*     */       {
/* 340 */         glyphs.setGlyphTransform(i, at);
/*     */       }
/*     */     }
/* 343 */     g2d.drawGlyphVector(glyphs, x, y);
/*     */   }
/*     */ 
/*     */   protected void determineEncoding()
/*     */   {
/* 351 */     String cmapName = null;
/* 352 */     COSName encodingName = null;
/* 353 */     COSBase encoding = getEncoding();
/* 354 */     Encoding fontEncoding = null;
/* 355 */     if (encoding != null)
/*     */     {
/* 357 */       if ((encoding instanceof COSName))
/*     */       {
/* 359 */         if (this.cmap == null)
/*     */         {
/* 361 */           encodingName = (COSName)encoding;
/* 362 */           this.cmap = ((CMap)cmapObjects.get(encodingName.getName()));
/* 363 */           if (this.cmap == null)
/*     */           {
/* 365 */             cmapName = encodingName.getName();
/*     */           }
/*     */         }
/* 368 */         if ((this.cmap == null) && (cmapName != null))
/*     */         {
/*     */           try
/*     */           {
/* 372 */             fontEncoding = EncodingManager.INSTANCE.getEncoding(encodingName);
/*     */           }
/*     */           catch (IOException exception)
/*     */           {
/* 377 */             LOG.debug("Debug: Could not find encoding for " + encodingName);
/*     */           }
/*     */         }
/*     */       }
/* 381 */       else if ((encoding instanceof COSStream))
/*     */       {
/* 383 */         if (this.cmap == null)
/*     */         {
/* 385 */           COSStream encodingStream = (COSStream)encoding;
/*     */           try
/*     */           {
/* 388 */             InputStream is = encodingStream.getUnfilteredStream();
/* 389 */             this.cmap = parseCmap(null, is);
/* 390 */             IOUtils.closeQuietly(is);
/*     */           }
/*     */           catch (IOException exception)
/*     */           {
/* 394 */             LOG.error("Error: Could not parse the embedded CMAP");
/*     */           }
/*     */         }
/*     */       }
/* 398 */       else if ((encoding instanceof COSDictionary))
/*     */       {
/*     */         try
/*     */         {
/* 402 */           fontEncoding = new DictionaryEncoding((COSDictionary)encoding);
/*     */         }
/*     */         catch (IOException exception)
/*     */         {
/* 406 */           LOG.error("Error: Could not create the DictionaryEncoding");
/*     */         }
/*     */       }
/*     */     }
/* 410 */     setFontEncoding(fontEncoding);
/* 411 */     extractToUnicodeEncoding();
/*     */ 
/* 413 */     if ((this.cmap == null) && (cmapName != null))
/*     */     {
/* 415 */       InputStream cmapStream = null;
/*     */       try
/*     */       {
/* 419 */         cmapStream = ResourceLoader.loadResource("org/apache/pdfbox/resources/cmap/" + cmapName);
/* 420 */         if (cmapStream != null)
/*     */         {
/* 422 */           this.cmap = parseCmap("org/apache/pdfbox/resources/cmap/", cmapStream);
/* 423 */           if ((this.cmap == null) && (encodingName == null))
/*     */           {
/* 425 */             LOG.error("Error: Could not parse predefined CMAP file for '" + cmapName + "'");
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 430 */           LOG.debug("Debug: '" + cmapName + "' isn't a predefined map, most likely it's embedded in the pdf itself.");
/*     */         }
/*     */       }
/*     */       catch (IOException exception)
/*     */       {
/* 435 */         LOG.error("Error: Could not find predefined CMAP file for '" + cmapName + "'");
/*     */       }
/*     */       finally
/*     */       {
/* 439 */         IOUtils.closeQuietly(cmapStream);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void extractToUnicodeEncoding()
/*     */   {
/* 446 */     COSName encodingName = null;
/* 447 */     String cmapName = null;
/* 448 */     COSBase toUnicode = getToUnicode();
/* 449 */     if (toUnicode != null)
/*     */     {
/* 451 */       setHasToUnicode(true);
/* 452 */       if ((toUnicode instanceof COSStream))
/*     */       {
/*     */         try
/*     */         {
/* 456 */           InputStream is = ((COSStream)toUnicode).getUnfilteredStream();
/* 457 */           this.toUnicodeCmap = parseCmap("org/apache/pdfbox/resources/cmap/", is);
/* 458 */           IOUtils.closeQuietly(is);
/*     */         }
/*     */         catch (IOException exception)
/*     */         {
/* 462 */           LOG.error("Error: Could not load embedded ToUnicode CMap");
/*     */         }
/*     */       }
/* 465 */       else if ((toUnicode instanceof COSName))
/*     */       {
/* 467 */         encodingName = (COSName)toUnicode;
/* 468 */         this.toUnicodeCmap = ((CMap)cmapObjects.get(encodingName.getName()));
/* 469 */         if (this.toUnicodeCmap == null)
/*     */         {
/* 471 */           cmapName = encodingName.getName();
/* 472 */           String resourceName = "org/apache/pdfbox/resources/cmap/" + cmapName;
/*     */           try
/*     */           {
/* 475 */             this.toUnicodeCmap = parseCmap("org/apache/pdfbox/resources/cmap/", ResourceLoader.loadResource(resourceName));
/*     */           }
/*     */           catch (IOException exception)
/*     */           {
/* 479 */             LOG.error("Error: Could not find predefined ToUnicode CMap file for '" + cmapName + "'");
/*     */           }
/* 481 */           if (this.toUnicodeCmap == null)
/*     */           {
/* 483 */             LOG.error("Error: Could not parse predefined ToUnicode CMap file for '" + cmapName + "'");
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected boolean isFontSubstituted()
/*     */   {
/* 500 */     return this.isFontSubstituted;
/*     */   }
/*     */ 
/*     */   protected void setIsFontSubstituted(boolean isSubstituted)
/*     */   {
/* 510 */     this.isFontSubstituted = isSubstituted;
/*     */   }
/*     */ 
/*     */   public float getSpaceWidth()
/*     */   {
/* 518 */     if (this.fontWidthOfSpace == -1.0F)
/*     */     {
/* 520 */       COSBase toUnicode = getToUnicode();
/*     */       try
/*     */       {
/* 523 */         if (toUnicode != null)
/*     */         {
/* 525 */           int spaceMapping = this.toUnicodeCmap.getSpaceMapping();
/* 526 */           if (spaceMapping > -1)
/*     */           {
/* 528 */             this.fontWidthOfSpace = getFontWidth(spaceMapping);
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 533 */           this.fontWidthOfSpace = getFontWidth(SPACE_BYTES, 0, 1);
/*     */         }
/*     */ 
/* 536 */         if (this.fontWidthOfSpace <= 0.0F)
/*     */         {
/* 538 */           this.fontWidthOfSpace = getAverageFontWidth();
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 543 */         LOG.error("Can't determine the width of the space character using 250 as default", e);
/* 544 */         this.fontWidthOfSpace = 250.0F;
/*     */       }
/*     */     }
/* 547 */     return this.fontWidthOfSpace;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.font.PDSimpleFont
 * JD-Core Version:    0.6.2
 */