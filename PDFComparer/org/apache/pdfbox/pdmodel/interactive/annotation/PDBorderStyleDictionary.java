/*     */ package org.apache.pdfbox.pdmodel.interactive.annotation;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.graphics.PDLineDashPattern;
/*     */ 
/*     */ public class PDBorderStyleDictionary
/*     */   implements COSObjectable
/*     */ {
/*     */   public static final String STYLE_SOLID = "S";
/*     */   public static final String STYLE_DASHED = "D";
/*     */   public static final String STYLE_BEVELED = "B";
/*     */   public static final String STYLE_INSET = "I";
/*     */   public static final String STYLE_UNDERLINE = "U";
/*     */   private COSDictionary dictionary;
/*     */ 
/*     */   public PDBorderStyleDictionary()
/*     */   {
/*  73 */     this.dictionary = new COSDictionary();
/*     */   }
/*     */ 
/*     */   public PDBorderStyleDictionary(COSDictionary dict)
/*     */   {
/*  84 */     this.dictionary = dict;
/*     */   }
/*     */ 
/*     */   public COSDictionary getDictionary()
/*     */   {
/*  94 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 104 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   public void setWidth(float w)
/*     */   {
/* 115 */     getDictionary().setFloat("W", w);
/*     */   }
/*     */ 
/*     */   public float getWidth()
/*     */   {
/* 125 */     return getDictionary().getFloat("W", 1.0F);
/*     */   }
/*     */ 
/*     */   public void setStyle(String s)
/*     */   {
/* 136 */     getDictionary().setName("S", s);
/*     */   }
/*     */ 
/*     */   public String getStyle()
/*     */   {
/* 147 */     return getDictionary().getNameAsString("S", "S");
/*     */   }
/*     */ 
/*     */   public void setDashStyle(PDLineDashPattern d)
/*     */   {
/* 158 */     COSArray array = null;
/* 159 */     if (d != null)
/*     */     {
/* 161 */       array = d.getCOSDashPattern();
/*     */     }
/* 163 */     getDictionary().setItem("D", array);
/*     */   }
/*     */ 
/*     */   public PDLineDashPattern getDashStyle()
/*     */   {
/* 173 */     COSArray d = (COSArray)getDictionary().getDictionaryObject("D");
/* 174 */     if (d == null)
/*     */     {
/* 176 */       d = new COSArray();
/* 177 */       d.add(COSInteger.THREE);
/* 178 */       getDictionary().setItem("D", d);
/*     */     }
/* 180 */     return new PDLineDashPattern(d, 0);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.annotation.PDBorderStyleDictionary
 * JD-Core Version:    0.6.2
 */