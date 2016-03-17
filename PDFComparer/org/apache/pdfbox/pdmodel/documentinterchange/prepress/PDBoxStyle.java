/*     */ package org.apache.pdfbox.pdmodel.documentinterchange.prepress;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.graphics.PDLineDashPattern;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorState;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
/*     */ 
/*     */ public class PDBoxStyle
/*     */   implements COSObjectable
/*     */ {
/*     */   public static final String GUIDELINE_STYLE_SOLID = "S";
/*     */   public static final String GUIDELINE_STYLE_DASHED = "D";
/*     */   private COSDictionary dictionary;
/*     */ 
/*     */   public PDBoxStyle()
/*     */   {
/*  53 */     this.dictionary = new COSDictionary();
/*     */   }
/*     */ 
/*     */   public PDBoxStyle(COSDictionary dic)
/*     */   {
/*  63 */     this.dictionary = dic;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  73 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   public COSDictionary getDictionary()
/*     */   {
/*  83 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   public PDColorState getGuidelineColor()
/*     */   {
/*  95 */     COSArray colorValues = (COSArray)this.dictionary.getDictionaryObject("C");
/*  96 */     if (colorValues == null)
/*     */     {
/*  98 */       colorValues = new COSArray();
/*  99 */       colorValues.add(COSInteger.ZERO);
/* 100 */       colorValues.add(COSInteger.ZERO);
/* 101 */       colorValues.add(COSInteger.ZERO);
/* 102 */       this.dictionary.setItem("C", colorValues);
/*     */     }
/* 104 */     PDColorState instance = new PDColorState(colorValues);
/* 105 */     instance.setColorSpace(PDDeviceRGB.INSTANCE);
/* 106 */     return instance;
/*     */   }
/*     */ 
/*     */   public void setGuideLineColor(PDColorState color)
/*     */   {
/* 117 */     COSArray values = null;
/* 118 */     if (color != null)
/*     */     {
/* 120 */       values = color.getCOSColorSpaceValue();
/*     */     }
/* 122 */     this.dictionary.setItem("C", values);
/*     */   }
/*     */ 
/*     */   public float getGuidelineWidth()
/*     */   {
/* 133 */     return this.dictionary.getFloat("W", 1.0F);
/*     */   }
/*     */ 
/*     */   public void setGuidelineWidth(float width)
/*     */   {
/* 143 */     this.dictionary.setFloat("W", width);
/*     */   }
/*     */ 
/*     */   public String getGuidelineStyle()
/*     */   {
/* 155 */     return this.dictionary.getNameAsString("S", "S");
/*     */   }
/*     */ 
/*     */   public void setGuidelineStyle(String style)
/*     */   {
/* 167 */     this.dictionary.setName("S", style);
/*     */   }
/*     */ 
/*     */   public PDLineDashPattern getLineDashPattern()
/*     */   {
/* 178 */     PDLineDashPattern pattern = null;
/* 179 */     COSArray d = (COSArray)this.dictionary.getDictionaryObject("D");
/* 180 */     if (d == null)
/*     */     {
/* 182 */       d = new COSArray();
/* 183 */       d.add(COSInteger.THREE);
/* 184 */       this.dictionary.setItem("D", d);
/*     */     }
/* 186 */     COSArray lineArray = new COSArray();
/* 187 */     lineArray.add(d);
/*     */ 
/* 189 */     lineArray.add(COSInteger.ZERO);
/* 190 */     pattern = new PDLineDashPattern(lineArray);
/* 191 */     return pattern;
/*     */   }
/*     */ 
/*     */   public void setLineDashPattern(PDLineDashPattern pattern)
/*     */   {
/* 201 */     COSArray array = null;
/* 202 */     if (pattern != null)
/*     */     {
/* 204 */       array = pattern.getCOSDashPattern();
/*     */     }
/* 206 */     this.dictionary.setItem("D", array);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.documentinterchange.prepress.PDBoxStyle
 * JD-Core Version:    0.6.2
 */