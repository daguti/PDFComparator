/*     */ package org.apache.pdfbox.pdmodel.graphics.pattern;
/*     */ 
/*     */ import java.awt.Paint;
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ 
/*     */ public abstract class PDPatternResources
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary patternDictionary;
/*     */   public static final int TILING_PATTERN = 1;
/*     */   public static final int SHADING_PATTERN = 2;
/*     */ 
/*     */   public PDPatternResources()
/*     */   {
/*  47 */     this.patternDictionary = new COSDictionary();
/*  48 */     this.patternDictionary.setName(COSName.TYPE, COSName.PATTERN.getName());
/*     */   }
/*     */ 
/*     */   public PDPatternResources(COSDictionary resourceDictionary)
/*     */   {
/*  58 */     this.patternDictionary = resourceDictionary;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/*  68 */     return this.patternDictionary;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  78 */     return this.patternDictionary;
/*     */   }
/*     */ 
/*     */   public void setFilter(String filter)
/*     */   {
/*  88 */     this.patternDictionary.setItem(COSName.FILTER, COSName.getPDFName(filter));
/*     */   }
/*     */ 
/*     */   public String getFilter()
/*     */   {
/*  98 */     return this.patternDictionary.getNameAsString(COSName.FILTER);
/*     */   }
/*     */ 
/*     */   public void setLength(int length)
/*     */   {
/* 108 */     this.patternDictionary.setInt(COSName.LENGTH, length);
/*     */   }
/*     */ 
/*     */   public int getLength()
/*     */   {
/* 118 */     return this.patternDictionary.getInt(COSName.LENGTH, 0);
/*     */   }
/*     */ 
/*     */   public void setPaintType(int paintType)
/*     */   {
/* 128 */     this.patternDictionary.setInt(COSName.PAINT_TYPE, paintType);
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 138 */     return COSName.PATTERN.getName();
/*     */   }
/*     */ 
/*     */   public void setPatternType(int patternType)
/*     */   {
/* 148 */     this.patternDictionary.setInt(COSName.PATTERN_TYPE, patternType);
/*     */   }
/*     */ 
/*     */   public abstract int getPatternType();
/*     */ 
/*     */   public static PDPatternResources create(COSDictionary resourceDictionary)
/*     */     throws IOException
/*     */   {
/* 169 */     PDPatternResources pattern = null;
/* 170 */     int patternType = resourceDictionary.getInt(COSName.PATTERN_TYPE, 0);
/* 171 */     switch (patternType)
/*     */     {
/*     */     case 1:
/* 174 */       pattern = new PDTilingPatternResources(resourceDictionary);
/* 175 */       break;
/*     */     case 2:
/* 177 */       pattern = new PDShadingPatternResources(resourceDictionary);
/* 178 */       break;
/*     */     default:
/* 180 */       throw new IOException("Error: Unknown pattern type " + patternType);
/*     */     }
/* 182 */     return pattern;
/*     */   }
/*     */ 
/*     */   public abstract Paint getPaint(int paramInt)
/*     */     throws IOException;
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.pattern.PDPatternResources
 * JD-Core Version:    0.6.2
 */