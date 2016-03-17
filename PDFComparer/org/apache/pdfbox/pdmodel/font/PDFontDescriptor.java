/*     */ package org.apache.pdfbox.pdmodel.font;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ 
/*     */ public abstract class PDFontDescriptor
/*     */ {
/*     */   private static final int FLAG_FIXED_PITCH = 1;
/*     */   private static final int FLAG_SERIF = 2;
/*     */   private static final int FLAG_SYMBOLIC = 4;
/*     */   private static final int FLAG_SCRIPT = 8;
/*     */   private static final int FLAG_NON_SYMBOLIC = 32;
/*     */   private static final int FLAG_ITALIC = 64;
/*     */   private static final int FLAG_ALL_CAP = 65536;
/*     */   private static final int FLAG_SMALL_CAP = 131072;
/*     */   private static final int FLAG_FORCE_BOLD = 262144;
/*     */ 
/*     */   public abstract String getFontName();
/*     */ 
/*     */   public abstract void setFontName(String paramString);
/*     */ 
/*     */   public abstract String getFontFamily();
/*     */ 
/*     */   public abstract void setFontFamily(String paramString);
/*     */ 
/*     */   public abstract String getFontStretch();
/*     */ 
/*     */   public abstract void setFontStretch(String paramString);
/*     */ 
/*     */   public abstract float getFontWeight();
/*     */ 
/*     */   public abstract void setFontWeight(float paramFloat);
/*     */ 
/*     */   public abstract int getFlags();
/*     */ 
/*     */   public abstract void setFlags(int paramInt);
/*     */ 
/*     */   public boolean isFixedPitch()
/*     */   {
/* 153 */     return isFlagBitOn(1);
/*     */   }
/*     */ 
/*     */   public void setFixedPitch(boolean flag)
/*     */   {
/* 163 */     setFlagBit(1, flag);
/*     */   }
/*     */ 
/*     */   public boolean isSerif()
/*     */   {
/* 173 */     return isFlagBitOn(2);
/*     */   }
/*     */ 
/*     */   public void setSerif(boolean flag)
/*     */   {
/* 183 */     setFlagBit(2, flag);
/*     */   }
/*     */ 
/*     */   public boolean isSymbolic()
/*     */   {
/* 193 */     return isFlagBitOn(4);
/*     */   }
/*     */ 
/*     */   public void setSymbolic(boolean flag)
/*     */   {
/* 203 */     setFlagBit(4, flag);
/*     */   }
/*     */ 
/*     */   public boolean isScript()
/*     */   {
/* 213 */     return isFlagBitOn(8);
/*     */   }
/*     */ 
/*     */   public void setScript(boolean flag)
/*     */   {
/* 223 */     setFlagBit(8, flag);
/*     */   }
/*     */ 
/*     */   public boolean isNonSymbolic()
/*     */   {
/* 233 */     return isFlagBitOn(32);
/*     */   }
/*     */ 
/*     */   public void setNonSymbolic(boolean flag)
/*     */   {
/* 243 */     setFlagBit(32, flag);
/*     */   }
/*     */ 
/*     */   public boolean isItalic()
/*     */   {
/* 253 */     return isFlagBitOn(64);
/*     */   }
/*     */ 
/*     */   public void setItalic(boolean flag)
/*     */   {
/* 263 */     setFlagBit(64, flag);
/*     */   }
/*     */ 
/*     */   public boolean isAllCap()
/*     */   {
/* 273 */     return isFlagBitOn(65536);
/*     */   }
/*     */ 
/*     */   public void setAllCap(boolean flag)
/*     */   {
/* 283 */     setFlagBit(65536, flag);
/*     */   }
/*     */ 
/*     */   public boolean isSmallCap()
/*     */   {
/* 293 */     return isFlagBitOn(131072);
/*     */   }
/*     */ 
/*     */   public void setSmallCap(boolean flag)
/*     */   {
/* 303 */     setFlagBit(131072, flag);
/*     */   }
/*     */ 
/*     */   public boolean isForceBold()
/*     */   {
/* 313 */     return isFlagBitOn(262144);
/*     */   }
/*     */ 
/*     */   public void setForceBold(boolean flag)
/*     */   {
/* 323 */     setFlagBit(262144, flag);
/*     */   }
/*     */ 
/*     */   private boolean isFlagBitOn(int bit)
/*     */   {
/* 328 */     return (getFlags() & bit) != 0;
/*     */   }
/*     */ 
/*     */   private void setFlagBit(int bit, boolean value)
/*     */   {
/* 333 */     int flags = getFlags();
/* 334 */     if (value)
/*     */     {
/* 336 */       flags |= bit;
/*     */     }
/*     */     else
/*     */     {
/* 340 */       flags &= (0xFFFFFFFF ^ bit);
/*     */     }
/* 342 */     setFlags(flags);
/*     */   }
/*     */ 
/*     */   public abstract PDRectangle getFontBoundingBox();
/*     */ 
/*     */   public abstract void setFontBoundingBox(PDRectangle paramPDRectangle);
/*     */ 
/*     */   public abstract float getItalicAngle();
/*     */ 
/*     */   public abstract void setItalicAngle(float paramFloat);
/*     */ 
/*     */   public abstract float getAscent();
/*     */ 
/*     */   public abstract void setAscent(float paramFloat);
/*     */ 
/*     */   public abstract float getDescent();
/*     */ 
/*     */   public abstract void setDescent(float paramFloat);
/*     */ 
/*     */   public abstract float getLeading();
/*     */ 
/*     */   public abstract void setLeading(float paramFloat);
/*     */ 
/*     */   public abstract float getCapHeight();
/*     */ 
/*     */   public abstract void setCapHeight(float paramFloat);
/*     */ 
/*     */   public abstract float getXHeight();
/*     */ 
/*     */   public abstract void setXHeight(float paramFloat);
/*     */ 
/*     */   public abstract float getStemV();
/*     */ 
/*     */   public abstract void setStemV(float paramFloat);
/*     */ 
/*     */   public abstract float getStemH();
/*     */ 
/*     */   public abstract void setStemH(float paramFloat);
/*     */ 
/*     */   public abstract float getAverageWidth()
/*     */     throws IOException;
/*     */ 
/*     */   public abstract void setAverageWidth(float paramFloat);
/*     */ 
/*     */   public abstract float getMaxWidth();
/*     */ 
/*     */   public abstract void setMaxWidth(float paramFloat);
/*     */ 
/*     */   public abstract String getCharSet();
/*     */ 
/*     */   public abstract void setCharacterSet(String paramString);
/*     */ 
/*     */   public abstract float getMissingWidth();
/*     */ 
/*     */   public abstract void setMissingWidth(float paramFloat);
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.font.PDFontDescriptor
 * JD-Core Version:    0.6.2
 */