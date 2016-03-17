/*     */ package org.apache.pdfbox.pdmodel.fdf;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRange;
/*     */ 
/*     */ public class FDFIconFit
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary fit;
/*     */   public static final String SCALE_OPTION_ALWAYS = "A";
/*     */   public static final String SCALE_OPTION_ONLY_WHEN_ICON_IS_BIGGER = "B";
/*     */   public static final String SCALE_OPTION_ONLY_WHEN_ICON_IS_SMALLER = "S";
/*     */   public static final String SCALE_OPTION_NEVER = "N";
/*     */   public static final String SCALE_TYPE_ANAMORPHIC = "A";
/*     */   public static final String SCALE_TYPE_PROPORTIONAL = "P";
/*     */ 
/*     */   public FDFIconFit()
/*     */   {
/*  69 */     this.fit = new COSDictionary();
/*     */   }
/*     */ 
/*     */   public FDFIconFit(COSDictionary f)
/*     */   {
/*  79 */     this.fit = f;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  89 */     return this.fit;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/*  99 */     return this.fit;
/*     */   }
/*     */ 
/*     */   public String getScaleOption()
/*     */   {
/* 110 */     String retval = this.fit.getNameAsString("SW");
/* 111 */     if (retval == null)
/*     */     {
/* 113 */       retval = "A";
/*     */     }
/* 115 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setScaleOption(String option)
/*     */   {
/* 125 */     this.fit.setName("SW", option);
/*     */   }
/*     */ 
/*     */   public String getScaleType()
/*     */   {
/* 136 */     String retval = this.fit.getNameAsString("S");
/* 137 */     if (retval == null)
/*     */     {
/* 139 */       retval = "P";
/*     */     }
/* 141 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setScaleType(String scale)
/*     */   {
/* 151 */     this.fit.setName("S", scale);
/*     */   }
/*     */ 
/*     */   public PDRange getFractionalSpaceToAllocate()
/*     */   {
/* 168 */     PDRange retval = null;
/* 169 */     COSArray array = (COSArray)this.fit.getDictionaryObject("A");
/* 170 */     if (array == null)
/*     */     {
/* 172 */       retval = new PDRange();
/* 173 */       retval.setMin(0.5F);
/* 174 */       retval.setMax(0.5F);
/* 175 */       setFractionalSpaceToAllocate(retval);
/*     */     }
/*     */     else
/*     */     {
/* 179 */       retval = new PDRange(array);
/*     */     }
/* 181 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setFractionalSpaceToAllocate(PDRange space)
/*     */   {
/* 191 */     this.fit.setItem("A", space);
/*     */   }
/*     */ 
/*     */   public boolean shouldScaleToFitAnnotation()
/*     */   {
/* 201 */     return this.fit.getBoolean("FB", false);
/*     */   }
/*     */ 
/*     */   public void setScaleToFitAnnotation(boolean value)
/*     */   {
/* 211 */     this.fit.setBoolean("FB", value);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.fdf.FDFIconFit
 * JD-Core Version:    0.6.2
 */