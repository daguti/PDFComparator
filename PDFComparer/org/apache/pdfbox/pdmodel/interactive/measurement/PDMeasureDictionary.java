/*     */ package org.apache.pdfbox.pdmodel.interactive.measurement;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ 
/*     */ public class PDMeasureDictionary
/*     */   implements COSObjectable
/*     */ {
/*     */   public static final String TYPE = "Measure";
/*     */   private COSDictionary measureDictionary;
/*     */ 
/*     */   protected PDMeasureDictionary()
/*     */   {
/*  44 */     this.measureDictionary = new COSDictionary();
/*  45 */     getDictionary().setName(COSName.TYPE, "Measure");
/*     */   }
/*     */ 
/*     */   public PDMeasureDictionary(COSDictionary dictionary)
/*     */   {
/*  55 */     this.measureDictionary = dictionary;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  63 */     return this.measureDictionary;
/*     */   }
/*     */ 
/*     */   public COSDictionary getDictionary()
/*     */   {
/*  73 */     return this.measureDictionary;
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  84 */     return "Measure";
/*     */   }
/*     */ 
/*     */   public String getSubtype()
/*     */   {
/*  94 */     return getDictionary().getNameAsString(COSName.SUBTYPE, "RL");
/*     */   }
/*     */ 
/*     */   protected void setSubtype(String subtype)
/*     */   {
/* 104 */     getDictionary().setName(COSName.SUBTYPE, subtype);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.measurement.PDMeasureDictionary
 * JD-Core Version:    0.6.2
 */