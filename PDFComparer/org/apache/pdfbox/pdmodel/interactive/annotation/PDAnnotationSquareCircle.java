/*     */ package org.apache.pdfbox.pdmodel.interactive.annotation;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDGamma;
/*     */ 
/*     */ public class PDAnnotationSquareCircle extends PDAnnotationMarkup
/*     */ {
/*     */   public static final String SUB_TYPE_SQUARE = "Square";
/*     */   public static final String SUB_TYPE_CIRCLE = "Circle";
/*     */ 
/*     */   public PDAnnotationSquareCircle(String subType)
/*     */   {
/*  52 */     setSubtype(subType);
/*     */   }
/*     */ 
/*     */   public PDAnnotationSquareCircle(COSDictionary field)
/*     */   {
/*  64 */     super(field);
/*     */   }
/*     */ 
/*     */   public void setInteriorColour(PDGamma ic)
/*     */   {
/*  78 */     getDictionary().setItem("IC", ic);
/*     */   }
/*     */ 
/*     */   public PDGamma getInteriorColour()
/*     */   {
/*  92 */     COSArray ic = (COSArray)getDictionary().getItem(COSName.getPDFName("IC"));
/*     */ 
/*  94 */     if (ic != null)
/*     */     {
/*  96 */       return new PDGamma(ic);
/*     */     }
/*     */ 
/* 100 */     return null;
/*     */   }
/*     */ 
/*     */   public void setBorderEffect(PDBorderEffectDictionary be)
/*     */   {
/* 114 */     getDictionary().setItem("BE", be);
/*     */   }
/*     */ 
/*     */   public PDBorderEffectDictionary getBorderEffect()
/*     */   {
/* 125 */     COSDictionary be = (COSDictionary)getDictionary().getDictionaryObject("BE");
/* 126 */     if (be != null)
/*     */     {
/* 128 */       return new PDBorderEffectDictionary(be);
/*     */     }
/*     */ 
/* 132 */     return null;
/*     */   }
/*     */ 
/*     */   public void setRectDifference(PDRectangle rd)
/*     */   {
/* 146 */     getDictionary().setItem("RD", rd);
/*     */   }
/*     */ 
/*     */   public PDRectangle getRectDifference()
/*     */   {
/* 158 */     COSArray rd = (COSArray)getDictionary().getDictionaryObject("RD");
/* 159 */     if (rd != null)
/*     */     {
/* 161 */       return new PDRectangle(rd);
/*     */     }
/*     */ 
/* 165 */     return null;
/*     */   }
/*     */ 
/*     */   public void setSubtype(String subType)
/*     */   {
/* 177 */     getDictionary().setName(COSName.SUBTYPE, subType);
/*     */   }
/*     */ 
/*     */   public String getSubtype()
/*     */   {
/* 188 */     return getDictionary().getNameAsString(COSName.SUBTYPE);
/*     */   }
/*     */ 
/*     */   public void setBorderStyle(PDBorderStyleDictionary bs)
/*     */   {
/* 201 */     getDictionary().setItem("BS", bs);
/*     */   }
/*     */ 
/*     */   public PDBorderStyleDictionary getBorderStyle()
/*     */   {
/* 213 */     COSDictionary bs = (COSDictionary)getDictionary().getItem(COSName.getPDFName("BS"));
/*     */ 
/* 215 */     if (bs != null)
/*     */     {
/* 217 */       return new PDBorderStyleDictionary(bs);
/*     */     }
/*     */ 
/* 221 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationSquareCircle
 * JD-Core Version:    0.6.2
 */