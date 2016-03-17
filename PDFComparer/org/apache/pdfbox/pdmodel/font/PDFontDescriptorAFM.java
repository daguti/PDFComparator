/*     */ package org.apache.pdfbox.pdmodel.font;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.fontbox.afm.FontMetric;
/*     */ import org.apache.fontbox.util.BoundingBox;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ 
/*     */ public class PDFontDescriptorAFM extends PDFontDescriptor
/*     */ {
/*     */   private FontMetric afm;
/*     */ 
/*     */   public PDFontDescriptorAFM(FontMetric afmFile)
/*     */   {
/*  45 */     this.afm = afmFile;
/*     */   }
/*     */ 
/*     */   public String getFontName()
/*     */   {
/*  55 */     return this.afm.getFontName();
/*     */   }
/*     */ 
/*     */   public void setFontName(String fontName)
/*     */   {
/*  65 */     throw new UnsupportedOperationException("The AFM Font descriptor is immutable");
/*     */   }
/*     */ 
/*     */   public String getFontFamily()
/*     */   {
/*  75 */     return this.afm.getFamilyName();
/*     */   }
/*     */ 
/*     */   public void setFontFamily(String fontFamily)
/*     */   {
/*  85 */     throw new UnsupportedOperationException("The AFM Font descriptor is immutable");
/*     */   }
/*     */ 
/*     */   public float getFontWeight()
/*     */   {
/*  97 */     String weight = this.afm.getWeight();
/*  98 */     float retval = 500.0F;
/*  99 */     if ((weight != null) && (weight.equalsIgnoreCase("bold")))
/*     */     {
/* 101 */       retval = 900.0F;
/*     */     }
/* 103 */     else if ((weight != null) && (weight.equalsIgnoreCase("light")))
/*     */     {
/* 105 */       retval = 100.0F;
/*     */     }
/* 107 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setFontWeight(float fontWeight)
/*     */   {
/* 117 */     throw new UnsupportedOperationException("The AFM Font descriptor is immutable");
/*     */   }
/*     */ 
/*     */   public String getFontStretch()
/*     */   {
/* 127 */     return null;
/*     */   }
/*     */ 
/*     */   public void setFontStretch(String fontStretch)
/*     */   {
/* 137 */     throw new UnsupportedOperationException("The AFM Font descriptor is immutable");
/*     */   }
/*     */ 
/*     */   public int getFlags()
/*     */   {
/* 148 */     return this.afm.isFixedPitch() ? 1 : 0;
/*     */   }
/*     */ 
/*     */   public void setFlags(int flags)
/*     */   {
/* 158 */     throw new UnsupportedOperationException("The AFM Font descriptor is immutable");
/*     */   }
/*     */ 
/*     */   public PDRectangle getFontBoundingBox()
/*     */   {
/* 168 */     BoundingBox box = this.afm.getFontBBox();
/* 169 */     PDRectangle retval = null;
/* 170 */     if (box != null)
/*     */     {
/* 172 */       retval = new PDRectangle(box);
/*     */     }
/* 174 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setFontBoundingBox(PDRectangle rect)
/*     */   {
/* 184 */     throw new UnsupportedOperationException("The AFM Font descriptor is immutable");
/*     */   }
/*     */ 
/*     */   public float getItalicAngle()
/*     */   {
/* 194 */     return this.afm.getItalicAngle();
/*     */   }
/*     */ 
/*     */   public void setItalicAngle(float angle)
/*     */   {
/* 204 */     throw new UnsupportedOperationException("The AFM Font descriptor is immutable");
/*     */   }
/*     */ 
/*     */   public float getAscent()
/*     */   {
/* 214 */     return this.afm.getAscender();
/*     */   }
/*     */ 
/*     */   public void setAscent(float ascent)
/*     */   {
/* 224 */     throw new UnsupportedOperationException("The AFM Font descriptor is immutable");
/*     */   }
/*     */ 
/*     */   public float getDescent()
/*     */   {
/* 234 */     return this.afm.getDescender();
/*     */   }
/*     */ 
/*     */   public void setDescent(float descent)
/*     */   {
/* 244 */     throw new UnsupportedOperationException("The AFM Font descriptor is immutable");
/*     */   }
/*     */ 
/*     */   public float getLeading()
/*     */   {
/* 255 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   public void setLeading(float leading)
/*     */   {
/* 265 */     throw new UnsupportedOperationException("The AFM Font descriptor is immutable");
/*     */   }
/*     */ 
/*     */   public float getCapHeight()
/*     */   {
/* 275 */     return this.afm.getCapHeight();
/*     */   }
/*     */ 
/*     */   public void setCapHeight(float capHeight)
/*     */   {
/* 285 */     throw new UnsupportedOperationException("The AFM Font descriptor is immutable");
/*     */   }
/*     */ 
/*     */   public float getXHeight()
/*     */   {
/* 295 */     return this.afm.getXHeight();
/*     */   }
/*     */ 
/*     */   public void setXHeight(float xHeight)
/*     */   {
/* 305 */     throw new UnsupportedOperationException("The AFM Font descriptor is immutable");
/*     */   }
/*     */ 
/*     */   public float getStemV()
/*     */   {
/* 316 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   public void setStemV(float stemV)
/*     */   {
/* 326 */     throw new UnsupportedOperationException("The AFM Font descriptor is immutable");
/*     */   }
/*     */ 
/*     */   public float getStemH()
/*     */   {
/* 337 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   public void setStemH(float stemH)
/*     */   {
/* 347 */     throw new UnsupportedOperationException("The AFM Font descriptor is immutable");
/*     */   }
/*     */ 
/*     */   public float getAverageWidth()
/*     */     throws IOException
/*     */   {
/* 359 */     return this.afm.getAverageCharacterWidth();
/*     */   }
/*     */ 
/*     */   public void setAverageWidth(float averageWidth)
/*     */   {
/* 369 */     throw new UnsupportedOperationException("The AFM Font descriptor is immutable");
/*     */   }
/*     */ 
/*     */   public float getMaxWidth()
/*     */   {
/* 380 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   public void setMaxWidth(float maxWidth)
/*     */   {
/* 390 */     throw new UnsupportedOperationException("The AFM Font descriptor is immutable");
/*     */   }
/*     */ 
/*     */   public float getMissingWidth()
/*     */   {
/* 400 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   public void setMissingWidth(float missingWidth)
/*     */   {
/* 410 */     throw new UnsupportedOperationException("The AFM Font descriptor is immutable");
/*     */   }
/*     */ 
/*     */   public String getCharSet()
/*     */   {
/* 420 */     return this.afm.getCharacterSet();
/*     */   }
/*     */ 
/*     */   public void setCharacterSet(String charSet)
/*     */   {
/* 430 */     throw new UnsupportedOperationException("The AFM Font descriptor is immutable");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.font.PDFontDescriptorAFM
 * JD-Core Version:    0.6.2
 */