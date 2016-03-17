/*     */ package org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ 
/*     */ public class PDMarkInfo
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary dictionary;
/*     */ 
/*     */   public PDMarkInfo()
/*     */   {
/*  40 */     this.dictionary = new COSDictionary();
/*     */   }
/*     */ 
/*     */   public PDMarkInfo(COSDictionary dic)
/*     */   {
/*  50 */     this.dictionary = dic;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  60 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   public COSDictionary getDictionary()
/*     */   {
/*  70 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   public boolean isMarked()
/*     */   {
/*  80 */     return this.dictionary.getBoolean("Marked", false);
/*     */   }
/*     */ 
/*     */   public void setMarked(boolean value)
/*     */   {
/*  90 */     this.dictionary.setBoolean("Marked", value);
/*     */   }
/*     */ 
/*     */   public boolean usesUserProperties()
/*     */   {
/* 100 */     return this.dictionary.getBoolean("UserProperties", false);
/*     */   }
/*     */ 
/*     */   public void setUserProperties(boolean userProps)
/*     */   {
/* 110 */     this.dictionary.setBoolean("UserProperties", userProps);
/*     */   }
/*     */ 
/*     */   public boolean isSuspect()
/*     */   {
/* 121 */     return this.dictionary.getBoolean("Suspects", false);
/*     */   }
/*     */ 
/*     */   public void setSuspect(boolean suspect)
/*     */   {
/* 133 */     this.dictionary.setBoolean("Suspects", false);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDMarkInfo
 * JD-Core Version:    0.6.2
 */