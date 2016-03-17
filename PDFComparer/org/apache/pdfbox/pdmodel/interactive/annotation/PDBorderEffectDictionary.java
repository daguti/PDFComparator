/*     */ package org.apache.pdfbox.pdmodel.interactive.annotation;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ 
/*     */ public class PDBorderEffectDictionary
/*     */   implements COSObjectable
/*     */ {
/*     */   public static final String STYLE_SOLID = "S";
/*     */   public static final String STYLE_CLOUDY = "C";
/*     */   private COSDictionary dictionary;
/*     */ 
/*     */   public PDBorderEffectDictionary()
/*     */   {
/*  55 */     this.dictionary = new COSDictionary();
/*     */   }
/*     */ 
/*     */   public PDBorderEffectDictionary(COSDictionary dict)
/*     */   {
/*  66 */     this.dictionary = dict;
/*     */   }
/*     */ 
/*     */   public COSDictionary getDictionary()
/*     */   {
/*  76 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  86 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   public void setIntensity(float i)
/*     */   {
/*  97 */     getDictionary().setFloat("I", i);
/*     */   }
/*     */ 
/*     */   public float getIntensity()
/*     */   {
/* 107 */     return getDictionary().getFloat("I", 0.0F);
/*     */   }
/*     */ 
/*     */   public void setStyle(String s)
/*     */   {
/* 118 */     getDictionary().setName("S", s);
/*     */   }
/*     */ 
/*     */   public String getStyle()
/*     */   {
/* 129 */     return getDictionary().getNameAsString("S", "S");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.annotation.PDBorderEffectDictionary
 * JD-Core Version:    0.6.2
 */