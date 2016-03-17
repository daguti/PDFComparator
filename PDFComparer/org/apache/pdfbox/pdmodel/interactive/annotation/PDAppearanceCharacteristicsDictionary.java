/*     */ package org.apache.pdfbox.pdmodel.interactive.annotation;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDGamma;
/*     */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectForm;
/*     */ 
/*     */ public class PDAppearanceCharacteristicsDictionary
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary dictionary;
/*     */ 
/*     */   public PDAppearanceCharacteristicsDictionary(COSDictionary dict)
/*     */   {
/*  46 */     this.dictionary = dict;
/*     */   }
/*     */ 
/*     */   public COSDictionary getDictionary()
/*     */   {
/*  56 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  65 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   public int getRotation()
/*     */   {
/*  75 */     return getDictionary().getInt(COSName.R, 0);
/*     */   }
/*     */ 
/*     */   public void setRotation(int rotation)
/*     */   {
/*  85 */     getDictionary().setInt(COSName.R, rotation);
/*     */   }
/*     */ 
/*     */   public PDGamma getBorderColour()
/*     */   {
/*  95 */     COSBase c = getDictionary().getItem(COSName.getPDFName("BC"));
/*  96 */     if ((c instanceof COSArray))
/*     */     {
/*  98 */       return new PDGamma((COSArray)c);
/*     */     }
/* 100 */     return null;
/*     */   }
/*     */ 
/*     */   public void setBorderColour(PDGamma c)
/*     */   {
/* 110 */     getDictionary().setItem("BC", c);
/*     */   }
/*     */ 
/*     */   public PDGamma getBackground()
/*     */   {
/* 120 */     COSBase c = getDictionary().getItem(COSName.getPDFName("BG"));
/* 121 */     if ((c instanceof COSArray))
/*     */     {
/* 123 */       return new PDGamma((COSArray)c);
/*     */     }
/* 125 */     return null;
/*     */   }
/*     */ 
/*     */   public void setBackground(PDGamma c)
/*     */   {
/* 135 */     getDictionary().setItem("BG", c);
/*     */   }
/*     */ 
/*     */   public String getNormalCaption()
/*     */   {
/* 145 */     return getDictionary().getString("CA");
/*     */   }
/*     */ 
/*     */   public void setNormalCaption(String caption)
/*     */   {
/* 155 */     getDictionary().setString("CA", caption);
/*     */   }
/*     */ 
/*     */   public String getRolloverCaption()
/*     */   {
/* 165 */     return getDictionary().getString("RC");
/*     */   }
/*     */ 
/*     */   public void setRolloverCaption(String caption)
/*     */   {
/* 175 */     getDictionary().setString("RC", caption);
/*     */   }
/*     */ 
/*     */   public String getAlternateCaption()
/*     */   {
/* 185 */     return getDictionary().getString("AC");
/*     */   }
/*     */ 
/*     */   public void setAlternateCaption(String caption)
/*     */   {
/* 195 */     getDictionary().setString("AC", caption);
/*     */   }
/*     */ 
/*     */   public PDXObjectForm getNormalIcon()
/*     */   {
/* 205 */     COSBase i = getDictionary().getDictionaryObject("I");
/* 206 */     if ((i instanceof COSStream))
/*     */     {
/* 208 */       return new PDXObjectForm((COSStream)i);
/*     */     }
/* 210 */     return null;
/*     */   }
/*     */ 
/*     */   public PDXObjectForm getRolloverIcon()
/*     */   {
/* 220 */     COSBase i = getDictionary().getDictionaryObject("RI");
/* 221 */     if ((i instanceof COSStream))
/*     */     {
/* 223 */       return new PDXObjectForm((COSStream)i);
/*     */     }
/* 225 */     return null;
/*     */   }
/*     */ 
/*     */   public PDXObjectForm getAlternateIcon()
/*     */   {
/* 235 */     COSBase i = getDictionary().getDictionaryObject("IX");
/* 236 */     if ((i instanceof COSStream))
/*     */     {
/* 238 */       return new PDXObjectForm((COSStream)i);
/*     */     }
/* 240 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceCharacteristicsDictionary
 * JD-Core Version:    0.6.2
 */