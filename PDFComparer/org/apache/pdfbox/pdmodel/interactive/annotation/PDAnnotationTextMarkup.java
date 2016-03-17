/*     */ package org.apache.pdfbox.pdmodel.interactive.annotation;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ 
/*     */ public class PDAnnotationTextMarkup extends PDAnnotationMarkup
/*     */ {
/*     */   public static final String SUB_TYPE_HIGHLIGHT = "Highlight";
/*     */   public static final String SUB_TYPE_UNDERLINE = "Underline";
/*     */   public static final String SUB_TYPE_SQUIGGLY = "Squiggly";
/*     */   public static final String SUB_TYPE_STRIKEOUT = "StrikeOut";
/*     */ 
/*     */   private PDAnnotationTextMarkup()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PDAnnotationTextMarkup(String subType)
/*     */   {
/*  64 */     setSubtype(subType);
/*     */ 
/*  67 */     setQuadPoints(new float[0]);
/*     */   }
/*     */ 
/*     */   public PDAnnotationTextMarkup(COSDictionary field)
/*     */   {
/*  78 */     super(field);
/*     */   }
/*     */ 
/*     */   public void setQuadPoints(float[] quadPoints)
/*     */   {
/*  90 */     COSArray newQuadPoints = new COSArray();
/*  91 */     newQuadPoints.setFloatArray(quadPoints);
/*  92 */     getDictionary().setItem("QuadPoints", newQuadPoints);
/*     */   }
/*     */ 
/*     */   public float[] getQuadPoints()
/*     */   {
/* 103 */     COSArray quadPoints = (COSArray)getDictionary().getDictionaryObject("QuadPoints");
/* 104 */     if (quadPoints != null)
/*     */     {
/* 106 */       return quadPoints.toFloatArray();
/*     */     }
/*     */ 
/* 110 */     return null;
/*     */   }
/*     */ 
/*     */   public void setSubtype(String subType)
/*     */   {
/* 122 */     getDictionary().setName(COSName.SUBTYPE, subType);
/*     */   }
/*     */ 
/*     */   public String getSubtype()
/*     */   {
/* 133 */     return getDictionary().getNameAsString(COSName.SUBTYPE);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationTextMarkup
 * JD-Core Version:    0.6.2
 */