/*     */ package org.apache.pdfbox.pdmodel.graphics.color;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.COSDictionaryMap;
/*     */ 
/*     */ public class PDDeviceNAttributes
/*     */ {
/*     */   private COSDictionary dictionary;
/*     */ 
/*     */   public PDDeviceNAttributes()
/*     */   {
/*  46 */     this.dictionary = new COSDictionary();
/*     */   }
/*     */ 
/*     */   public PDDeviceNAttributes(COSDictionary attributes)
/*     */   {
/*  56 */     this.dictionary = attributes;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/*  66 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   public Map getColorants()
/*     */     throws IOException
/*     */   {
/*  80 */     Map actuals = new HashMap();
/*  81 */     COSDictionary colorants = (COSDictionary)this.dictionary.getDictionaryObject(COSName.COLORANTS);
/*  82 */     if (colorants == null)
/*     */     {
/*  84 */       colorants = new COSDictionary();
/*  85 */       this.dictionary.setItem(COSName.COLORANTS, colorants);
/*     */     }
/*  87 */     for (COSName name : colorants.keySet())
/*     */     {
/*  89 */       COSBase value = colorants.getDictionaryObject(name);
/*  90 */       actuals.put(name.getName(), PDColorSpaceFactory.createColorSpace(value));
/*     */     }
/*  92 */     return new COSDictionaryMap(actuals, colorants);
/*     */   }
/*     */ 
/*     */   public void setColorants(Map colorants)
/*     */   {
/* 103 */     COSDictionary colorantDict = null;
/* 104 */     if (colorants != null)
/*     */     {
/* 106 */       colorantDict = COSDictionaryMap.convert(colorants);
/*     */     }
/* 108 */     this.dictionary.setItem(COSName.COLORANTS, colorantDict);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.color.PDDeviceNAttributes
 * JD-Core Version:    0.6.2
 */