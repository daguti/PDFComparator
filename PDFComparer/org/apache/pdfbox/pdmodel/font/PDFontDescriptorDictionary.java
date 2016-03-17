/*     */ package org.apache.pdfbox.pdmodel.font;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ 
/*     */ public class PDFontDescriptorDictionary extends PDFontDescriptor
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary dic;
/*  40 */   private float xHeight = (1.0F / -1.0F);
/*  41 */   private float capHeight = (1.0F / -1.0F);
/*  42 */   private int flags = -1;
/*     */ 
/*     */   public PDFontDescriptorDictionary()
/*     */   {
/*  49 */     this.dic = new COSDictionary();
/*  50 */     this.dic.setItem(COSName.TYPE, COSName.FONT_DESC);
/*     */   }
/*     */ 
/*     */   public PDFontDescriptorDictionary(COSDictionary desc)
/*     */   {
/*  60 */     this.dic = desc;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/*  70 */     return this.dic;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  80 */     return this.dic;
/*     */   }
/*     */ 
/*     */   public String getFontName()
/*     */   {
/*  90 */     String retval = null;
/*  91 */     COSName name = (COSName)this.dic.getDictionaryObject(COSName.FONT_NAME);
/*  92 */     if (name != null)
/*     */     {
/*  94 */       retval = name.getName();
/*     */     }
/*  96 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setFontName(String fontName)
/*     */   {
/* 106 */     COSName name = null;
/* 107 */     if (fontName != null)
/*     */     {
/* 109 */       name = COSName.getPDFName(fontName);
/*     */     }
/* 111 */     this.dic.setItem(COSName.FONT_NAME, name);
/*     */   }
/*     */ 
/*     */   public String getFontFamily()
/*     */   {
/* 121 */     String retval = null;
/* 122 */     COSString name = (COSString)this.dic.getDictionaryObject(COSName.FONT_FAMILY);
/* 123 */     if (name != null)
/*     */     {
/* 125 */       retval = name.getString();
/*     */     }
/* 127 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setFontFamily(String fontFamily)
/*     */   {
/* 137 */     COSString name = null;
/* 138 */     if (fontFamily != null)
/*     */     {
/* 140 */       name = new COSString(fontFamily);
/*     */     }
/* 142 */     this.dic.setItem(COSName.FONT_FAMILY, name);
/*     */   }
/*     */ 
/*     */   public float getFontWeight()
/*     */   {
/* 154 */     return this.dic.getFloat(COSName.FONT_WEIGHT, 0.0F);
/*     */   }
/*     */ 
/*     */   public void setFontWeight(float fontWeight)
/*     */   {
/* 164 */     this.dic.setFloat(COSName.FONT_WEIGHT, fontWeight);
/*     */   }
/*     */ 
/*     */   public String getFontStretch()
/*     */   {
/* 178 */     String retval = null;
/* 179 */     COSName name = (COSName)this.dic.getDictionaryObject(COSName.FONT_STRETCH);
/* 180 */     if (name != null)
/*     */     {
/* 182 */       retval = name.getName();
/*     */     }
/* 184 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setFontStretch(String fontStretch)
/*     */   {
/* 194 */     COSName name = null;
/* 195 */     if (fontStretch != null)
/*     */     {
/* 197 */       name = COSName.getPDFName(fontStretch);
/*     */     }
/* 199 */     this.dic.setItem(COSName.FONT_STRETCH, name);
/*     */   }
/*     */ 
/*     */   public int getFlags()
/*     */   {
/* 209 */     if (this.flags == -1)
/*     */     {
/* 211 */       this.flags = this.dic.getInt(COSName.FLAGS, 0);
/*     */     }
/* 213 */     return this.flags;
/*     */   }
/*     */ 
/*     */   public void setFlags(int flags)
/*     */   {
/* 223 */     this.dic.setInt(COSName.FLAGS, flags);
/* 224 */     this.flags = flags;
/*     */   }
/*     */ 
/*     */   public PDRectangle getFontBoundingBox()
/*     */   {
/* 234 */     COSArray rect = (COSArray)this.dic.getDictionaryObject(COSName.FONT_BBOX);
/* 235 */     PDRectangle retval = null;
/* 236 */     if (rect != null)
/*     */     {
/* 238 */       retval = new PDRectangle(rect);
/*     */     }
/* 240 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setFontBoundingBox(PDRectangle rect)
/*     */   {
/* 250 */     COSArray array = null;
/* 251 */     if (rect != null)
/*     */     {
/* 253 */       array = rect.getCOSArray();
/*     */     }
/* 255 */     this.dic.setItem(COSName.FONT_BBOX, array);
/*     */   }
/*     */ 
/*     */   public float getItalicAngle()
/*     */   {
/* 265 */     return this.dic.getFloat(COSName.ITALIC_ANGLE, 0.0F);
/*     */   }
/*     */ 
/*     */   public void setItalicAngle(float angle)
/*     */   {
/* 275 */     this.dic.setFloat(COSName.ITALIC_ANGLE, angle);
/*     */   }
/*     */ 
/*     */   public float getAscent()
/*     */   {
/* 285 */     return this.dic.getFloat(COSName.ASCENT, 0.0F);
/*     */   }
/*     */ 
/*     */   public void setAscent(float ascent)
/*     */   {
/* 295 */     this.dic.setFloat(COSName.ASCENT, ascent);
/*     */   }
/*     */ 
/*     */   public float getDescent()
/*     */   {
/* 305 */     return this.dic.getFloat(COSName.DESCENT, 0.0F);
/*     */   }
/*     */ 
/*     */   public void setDescent(float descent)
/*     */   {
/* 315 */     this.dic.setFloat(COSName.DESCENT, descent);
/*     */   }
/*     */ 
/*     */   public float getLeading()
/*     */   {
/* 325 */     return this.dic.getFloat(COSName.LEADING, 0.0F);
/*     */   }
/*     */ 
/*     */   public void setLeading(float leading)
/*     */   {
/* 335 */     this.dic.setFloat(COSName.LEADING, leading);
/*     */   }
/*     */ 
/*     */   public float getCapHeight()
/*     */   {
/* 345 */     if (this.capHeight == (1.0F / -1.0F))
/*     */     {
/* 351 */       this.capHeight = Math.abs(this.dic.getFloat(COSName.CAP_HEIGHT, 0.0F));
/*     */     }
/* 353 */     return this.capHeight;
/*     */   }
/*     */ 
/*     */   public void setCapHeight(float capHeight)
/*     */   {
/* 364 */     this.dic.setFloat(COSName.CAP_HEIGHT, capHeight);
/* 365 */     this.capHeight = capHeight;
/*     */   }
/*     */ 
/*     */   public float getXHeight()
/*     */   {
/* 375 */     if (this.xHeight == (1.0F / -1.0F))
/*     */     {
/* 381 */       this.xHeight = Math.abs(this.dic.getFloat(COSName.XHEIGHT, 0.0F));
/*     */     }
/* 383 */     return this.xHeight;
/*     */   }
/*     */ 
/*     */   public void setXHeight(float xHeight)
/*     */   {
/* 393 */     this.dic.setFloat(COSName.XHEIGHT, xHeight);
/* 394 */     this.xHeight = xHeight;
/*     */   }
/*     */ 
/*     */   public float getStemV()
/*     */   {
/* 404 */     return this.dic.getFloat(COSName.STEM_V, 0.0F);
/*     */   }
/*     */ 
/*     */   public void setStemV(float stemV)
/*     */   {
/* 414 */     this.dic.setFloat(COSName.STEM_V, stemV);
/*     */   }
/*     */ 
/*     */   public float getStemH()
/*     */   {
/* 424 */     return this.dic.getFloat(COSName.STEM_H, 0.0F);
/*     */   }
/*     */ 
/*     */   public void setStemH(float stemH)
/*     */   {
/* 434 */     this.dic.setFloat(COSName.STEM_H, stemH);
/*     */   }
/*     */ 
/*     */   public float getAverageWidth()
/*     */   {
/* 444 */     return this.dic.getFloat(COSName.AVG_WIDTH, 0.0F);
/*     */   }
/*     */ 
/*     */   public void setAverageWidth(float averageWidth)
/*     */   {
/* 454 */     this.dic.setFloat(COSName.AVG_WIDTH, averageWidth);
/*     */   }
/*     */ 
/*     */   public float getMaxWidth()
/*     */   {
/* 464 */     return this.dic.getFloat(COSName.MAX_WIDTH, 0.0F);
/*     */   }
/*     */ 
/*     */   public void setMaxWidth(float maxWidth)
/*     */   {
/* 474 */     this.dic.setFloat(COSName.MAX_WIDTH, maxWidth);
/*     */   }
/*     */ 
/*     */   public float getMissingWidth()
/*     */   {
/* 484 */     return this.dic.getFloat(COSName.MISSING_WIDTH, 0.0F);
/*     */   }
/*     */ 
/*     */   public void setMissingWidth(float missingWidth)
/*     */   {
/* 494 */     this.dic.setFloat(COSName.MISSING_WIDTH, missingWidth);
/*     */   }
/*     */ 
/*     */   public String getCharSet()
/*     */   {
/* 504 */     String retval = null;
/* 505 */     COSString name = (COSString)this.dic.getDictionaryObject(COSName.CHAR_SET);
/* 506 */     if (name != null)
/*     */     {
/* 508 */       retval = name.getString();
/*     */     }
/* 510 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setCharacterSet(String charSet)
/*     */   {
/* 520 */     COSString name = null;
/* 521 */     if (charSet != null)
/*     */     {
/* 523 */       name = new COSString(charSet);
/*     */     }
/* 525 */     this.dic.setItem(COSName.CHAR_SET, name);
/*     */   }
/*     */ 
/*     */   public PDStream getFontFile()
/*     */   {
/* 535 */     PDStream retval = null;
/* 536 */     COSStream stream = (COSStream)this.dic.getDictionaryObject(COSName.FONT_FILE);
/* 537 */     if (stream != null)
/*     */     {
/* 539 */       retval = new PDStream(stream);
/*     */     }
/* 541 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setFontFile(PDStream type1Stream)
/*     */   {
/* 551 */     this.dic.setItem(COSName.FONT_FILE, type1Stream);
/*     */   }
/*     */ 
/*     */   public PDStream getFontFile2()
/*     */   {
/* 561 */     PDStream retval = null;
/* 562 */     COSStream stream = (COSStream)this.dic.getDictionaryObject(COSName.FONT_FILE2);
/* 563 */     if (stream != null)
/*     */     {
/* 565 */       retval = new PDStream(stream);
/*     */     }
/* 567 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setFontFile2(PDStream ttfStream)
/*     */   {
/* 577 */     this.dic.setItem(COSName.FONT_FILE2, ttfStream);
/*     */   }
/*     */ 
/*     */   public PDStream getFontFile3()
/*     */   {
/* 587 */     PDStream retval = null;
/* 588 */     COSStream stream = (COSStream)this.dic.getDictionaryObject(COSName.FONT_FILE3);
/* 589 */     if (stream != null)
/*     */     {
/* 591 */       retval = new PDStream(stream);
/*     */     }
/* 593 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setFontFile3(PDStream stream)
/*     */   {
/* 603 */     this.dic.setItem(COSName.FONT_FILE3, stream);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.font.PDFontDescriptorDictionary
 * JD-Core Version:    0.6.2
 */