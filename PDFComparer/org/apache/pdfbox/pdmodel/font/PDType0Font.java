/*     */ package org.apache.pdfbox.pdmodel.font;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.io.IOException;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.fontbox.cmap.CMap;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ 
/*     */ public class PDType0Font extends PDSimpleFont
/*     */ {
/*  43 */   private static final Log LOG = LogFactory.getLog(PDType0Font.class);
/*     */   private COSArray descendantFontArray;
/*     */   private PDFont descendantFont;
/*     */   private COSDictionary descendantFontDictionary;
/*     */   private Font awtFont;
/*     */ 
/*     */   public PDType0Font()
/*     */   {
/*  56 */     this.font.setItem(COSName.SUBTYPE, COSName.TYPE0);
/*     */   }
/*     */ 
/*     */   public PDType0Font(COSDictionary fontDictionary)
/*     */   {
/*  67 */     super(fontDictionary);
/*  68 */     this.descendantFontDictionary = ((COSDictionary)getDescendantFonts().getObject(0));
/*  69 */     if (this.descendantFontDictionary != null)
/*     */     {
/*     */       try
/*     */       {
/*  73 */         this.descendantFont = PDFontFactory.createFont(this.descendantFontDictionary);
/*     */       }
/*     */       catch (IOException exception)
/*     */       {
/*  77 */         LOG.error("Error while creating the descendant font!");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public Font getawtFont()
/*     */     throws IOException
/*     */   {
/*  88 */     if (this.awtFont == null)
/*     */     {
/*  90 */       if (this.descendantFont != null)
/*     */       {
/*  92 */         this.awtFont = ((PDSimpleFont)this.descendantFont).getawtFont();
/*  93 */         if (this.awtFont != null)
/*     */         {
/*  95 */           setIsFontSubstituted(((PDSimpleFont)this.descendantFont).isFontSubstituted());
/*     */ 
/* 100 */           this.awtFont.canDisplay(1);
/*     */         }
/*     */       }
/* 103 */       if (this.awtFont == null)
/*     */       {
/* 105 */         this.awtFont = FontManager.getStandardFont();
/* 106 */         LOG.info("Using font " + this.awtFont.getName() + " instead of " + this.descendantFont.getFontDescriptor().getFontName());
/*     */ 
/* 108 */         setIsFontSubstituted(true);
/*     */       }
/*     */     }
/* 111 */     return this.awtFont;
/*     */   }
/*     */ 
/*     */   public PDRectangle getFontBoundingBox()
/*     */     throws IOException
/*     */   {
/* 124 */     throw new RuntimeException("Not yet implemented");
/*     */   }
/*     */ 
/*     */   public float getFontWidth(byte[] c, int offset, int length)
/*     */     throws IOException
/*     */   {
/* 140 */     return this.descendantFont.getFontWidth(c, offset, length);
/*     */   }
/*     */ 
/*     */   public float getFontHeight(byte[] c, int offset, int length)
/*     */     throws IOException
/*     */   {
/* 157 */     return this.descendantFont.getFontHeight(c, offset, length);
/*     */   }
/*     */ 
/*     */   public float getAverageFontWidth()
/*     */     throws IOException
/*     */   {
/* 170 */     return this.descendantFont.getAverageFontWidth();
/*     */   }
/*     */ 
/*     */   private COSArray getDescendantFonts()
/*     */   {
/* 175 */     if (this.descendantFontArray == null)
/*     */     {
/* 177 */       this.descendantFontArray = ((COSArray)this.font.getDictionaryObject(COSName.DESCENDANT_FONTS));
/*     */     }
/* 179 */     return this.descendantFontArray;
/*     */   }
/*     */ 
/*     */   public float getFontWidth(int charCode)
/*     */   {
/* 188 */     return this.descendantFont.getFontWidth(charCode);
/*     */   }
/*     */ 
/*     */   public String encode(byte[] c, int offset, int length)
/*     */     throws IOException
/*     */   {
/* 194 */     String retval = null;
/* 195 */     if (hasToUnicode())
/*     */     {
/* 197 */       retval = super.encode(c, offset, length);
/*     */     }
/*     */ 
/* 200 */     if (retval == null)
/*     */     {
/* 202 */       int result = this.cmap.lookupCID(c, offset, length);
/* 203 */       if (result != -1)
/*     */       {
/* 205 */         retval = this.descendantFont.cmapEncoding(result, 2, true, null);
/*     */       }
/*     */     }
/* 208 */     return retval;
/*     */   }
/*     */ 
/*     */   public PDFont getDescendantFont()
/*     */   {
/* 220 */     return this.descendantFont;
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 225 */     super.clear();
/* 226 */     this.descendantFontArray = null;
/* 227 */     if (this.descendantFont != null)
/*     */     {
/* 229 */       this.descendantFont.clear();
/* 230 */       this.descendantFont = null;
/*     */     }
/* 232 */     this.descendantFontDictionary = null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.font.PDType0Font
 * JD-Core Version:    0.6.2
 */