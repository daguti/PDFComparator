/*     */ package org.apache.pdfbox.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.pdmodel.common.COSArrayList;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpaceFactory;
/*     */ 
/*     */ public class ImageParameters
/*     */ {
/*     */   private COSDictionary dictionary;
/*     */ 
/*     */   public ImageParameters()
/*     */   {
/*  49 */     this.dictionary = new COSDictionary();
/*     */   }
/*     */ 
/*     */   public ImageParameters(COSDictionary params)
/*     */   {
/*  59 */     this.dictionary = params;
/*     */   }
/*     */ 
/*     */   public COSDictionary getDictionary()
/*     */   {
/*  69 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   private COSBase getCOSObject(COSName abbreviatedName, COSName name)
/*     */   {
/*  74 */     COSBase retval = this.dictionary.getDictionaryObject(abbreviatedName);
/*  75 */     if (retval == null)
/*     */     {
/*  77 */       retval = this.dictionary.getDictionaryObject(name);
/*     */     }
/*  79 */     return retval;
/*     */   }
/*     */ 
/*     */   private int getNumberOrNegativeOne(COSName abbreviatedName, COSName name)
/*     */   {
/*  84 */     int retval = -1;
/*  85 */     COSNumber number = (COSNumber)getCOSObject(abbreviatedName, name);
/*  86 */     if (number != null)
/*     */     {
/*  88 */       retval = number.intValue();
/*     */     }
/*  90 */     return retval;
/*     */   }
/*     */ 
/*     */   public int getBitsPerComponent()
/*     */   {
/* 101 */     return getNumberOrNegativeOne(COSName.BPC, COSName.BITS_PER_COMPONENT);
/*     */   }
/*     */ 
/*     */   public void setBitsPerComponent(int bpc)
/*     */   {
/* 111 */     this.dictionary.setInt(COSName.BPC, bpc);
/*     */   }
/*     */ 
/*     */   public PDColorSpace getColorSpace()
/*     */     throws IOException
/*     */   {
/* 124 */     return getColorSpace(null);
/*     */   }
/*     */ 
/*     */   public PDColorSpace getColorSpace(Map colorSpaces)
/*     */     throws IOException
/*     */   {
/* 138 */     COSBase cs = getCOSObject(COSName.CS, COSName.COLORSPACE);
/* 139 */     PDColorSpace retval = null;
/* 140 */     if (cs != null)
/*     */     {
/* 142 */       retval = PDColorSpaceFactory.createColorSpace(cs, colorSpaces);
/*     */     }
/* 144 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setColorSpace(PDColorSpace cs)
/*     */   {
/* 154 */     COSBase base = null;
/* 155 */     if (cs != null)
/*     */     {
/* 157 */       base = cs.getCOSObject();
/*     */     }
/* 159 */     this.dictionary.setItem(COSName.CS, base);
/*     */   }
/*     */ 
/*     */   public int getHeight()
/*     */   {
/* 170 */     return getNumberOrNegativeOne(COSName.H, COSName.HEIGHT);
/*     */   }
/*     */ 
/*     */   public void setHeight(int h)
/*     */   {
/* 180 */     this.dictionary.setInt(COSName.H, h);
/*     */   }
/*     */ 
/*     */   public int getWidth()
/*     */   {
/* 191 */     return getNumberOrNegativeOne(COSName.W, COSName.WIDTH);
/*     */   }
/*     */ 
/*     */   public void setWidth(int w)
/*     */   {
/* 201 */     this.dictionary.setInt(COSName.W, w);
/*     */   }
/*     */ 
/*     */   public List getFilters()
/*     */   {
/* 211 */     List retval = null;
/* 212 */     COSBase filters = this.dictionary.getDictionaryObject(new String[] { "Filter", "F" });
/* 213 */     if ((filters instanceof COSName))
/*     */     {
/* 215 */       COSName name = (COSName)filters;
/* 216 */       retval = new COSArrayList(name.getName(), name, this.dictionary, COSName.FILTER);
/*     */     }
/* 218 */     else if ((filters instanceof COSArray))
/*     */     {
/* 220 */       retval = COSArrayList.convertCOSNameCOSArrayToList((COSArray)filters);
/*     */     }
/* 222 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setFilters(List filters)
/*     */   {
/* 232 */     COSBase obj = COSArrayList.convertStringListToCOSNameCOSArray(filters);
/* 233 */     this.dictionary.setItem("Filter", obj);
/*     */   }
/*     */ 
/*     */   public boolean isStencil()
/*     */   {
/* 243 */     return this.dictionary.getBoolean(COSName.IM, COSName.IMAGE_MASK, false);
/*     */   }
/*     */ 
/*     */   public void setStencil(boolean isStencil)
/*     */   {
/* 253 */     this.dictionary.setBoolean(COSName.IM, isStencil);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.ImageParameters
 * JD-Core Version:    0.6.2
 */