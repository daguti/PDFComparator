/*     */ package org.apache.pdfbox.pdmodel.graphics.shading;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ 
/*     */ public class PDShadingType2 extends PDShadingResources
/*     */ {
/*  30 */   private COSArray coords = null;
/*  31 */   private COSArray domain = null;
/*  32 */   private COSArray extend = null;
/*     */ 
/*     */   public PDShadingType2(COSDictionary shadingDictionary)
/*     */   {
/*  41 */     super(shadingDictionary);
/*     */   }
/*     */ 
/*     */   public int getShadingType()
/*     */   {
/*  50 */     return 2;
/*     */   }
/*     */ 
/*     */   public COSArray getExtend()
/*     */   {
/*  60 */     if (this.extend == null)
/*     */     {
/*  62 */       this.extend = ((COSArray)getCOSDictionary().getDictionaryObject(COSName.EXTEND));
/*     */     }
/*  64 */     return this.extend;
/*     */   }
/*     */ 
/*     */   public void setExtend(COSArray newExtend)
/*     */   {
/*  74 */     this.extend = newExtend;
/*  75 */     if (newExtend == null)
/*     */     {
/*  77 */       getCOSDictionary().removeItem(COSName.EXTEND);
/*     */     }
/*     */     else
/*     */     {
/*  81 */       getCOSDictionary().setItem(COSName.EXTEND, newExtend);
/*     */     }
/*     */   }
/*     */ 
/*     */   public COSArray getDomain()
/*     */   {
/*  92 */     if (this.domain == null)
/*     */     {
/*  94 */       this.domain = ((COSArray)getCOSDictionary().getDictionaryObject(COSName.DOMAIN));
/*     */     }
/*  96 */     return this.domain;
/*     */   }
/*     */ 
/*     */   public void setDomain(COSArray newDomain)
/*     */   {
/* 106 */     this.domain = newDomain;
/* 107 */     if (newDomain == null)
/*     */     {
/* 109 */       getCOSDictionary().removeItem(COSName.DOMAIN);
/*     */     }
/*     */     else
/*     */     {
/* 113 */       getCOSDictionary().setItem(COSName.DOMAIN, newDomain);
/*     */     }
/*     */   }
/*     */ 
/*     */   public COSArray getCoords()
/*     */   {
/* 124 */     if (this.coords == null)
/*     */     {
/* 126 */       this.coords = ((COSArray)getCOSDictionary().getDictionaryObject(COSName.COORDS));
/*     */     }
/* 128 */     return this.coords;
/*     */   }
/*     */ 
/*     */   public void setCoords(COSArray newCoords)
/*     */   {
/* 138 */     this.coords = newCoords;
/* 139 */     if (newCoords == null)
/*     */     {
/* 141 */       getCOSDictionary().removeItem(COSName.COORDS);
/*     */     }
/*     */     else
/*     */     {
/* 145 */       getCOSDictionary().setItem(COSName.COORDS, newCoords);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.shading.PDShadingType2
 * JD-Core Version:    0.6.2
 */