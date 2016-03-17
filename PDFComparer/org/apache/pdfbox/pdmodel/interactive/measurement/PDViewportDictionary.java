/*     */ package org.apache.pdfbox.pdmodel.interactive.measurement;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ 
/*     */ public class PDViewportDictionary
/*     */   implements COSObjectable
/*     */ {
/*     */   public static final String TYPE = "Viewport";
/*     */   private COSDictionary viewportDictionary;
/*     */ 
/*     */   public PDViewportDictionary()
/*     */   {
/*  47 */     this.viewportDictionary = new COSDictionary();
/*     */   }
/*     */ 
/*     */   public PDViewportDictionary(COSDictionary dictionary)
/*     */   {
/*  57 */     this.viewportDictionary = dictionary;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  65 */     return this.viewportDictionary;
/*     */   }
/*     */ 
/*     */   public COSDictionary getDictionary()
/*     */   {
/*  75 */     return this.viewportDictionary;
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  86 */     return "Viewport";
/*     */   }
/*     */ 
/*     */   public PDRectangle getBBox()
/*     */   {
/*  96 */     COSArray bbox = (COSArray)getDictionary().getDictionaryObject("BBox");
/*  97 */     if (bbox != null)
/*     */     {
/*  99 */       return new PDRectangle(bbox);
/*     */     }
/* 101 */     return null;
/*     */   }
/*     */ 
/*     */   public void setBBox(PDRectangle rectangle)
/*     */   {
/* 111 */     getDictionary().setItem("BBox", rectangle);
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 121 */     return getDictionary().getNameAsString(COSName.NAME);
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 131 */     getDictionary().setName(COSName.NAME, name);
/*     */   }
/*     */ 
/*     */   public PDMeasureDictionary getMeasure()
/*     */   {
/* 141 */     COSDictionary measure = (COSDictionary)getDictionary().getDictionaryObject("Measure");
/* 142 */     if (measure != null)
/*     */     {
/* 144 */       return new PDMeasureDictionary(measure);
/*     */     }
/* 146 */     return null;
/*     */   }
/*     */ 
/*     */   public void setMeasure(PDMeasureDictionary measure)
/*     */   {
/* 156 */     getDictionary().setItem("Measure", measure);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.measurement.PDViewportDictionary
 * JD-Core Version:    0.6.2
 */