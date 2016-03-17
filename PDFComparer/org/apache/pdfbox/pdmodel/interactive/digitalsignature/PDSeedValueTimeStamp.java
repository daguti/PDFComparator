/*     */ package org.apache.pdfbox.pdmodel.interactive.digitalsignature;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ 
/*     */ public class PDSeedValueTimeStamp
/*     */ {
/*     */   private COSDictionary dictionary;
/*     */ 
/*     */   public PDSeedValueTimeStamp()
/*     */   {
/*  40 */     this.dictionary = new COSDictionary();
/*  41 */     this.dictionary.setDirect(true);
/*     */   }
/*     */ 
/*     */   public PDSeedValueTimeStamp(COSDictionary dict)
/*     */   {
/*  51 */     this.dictionary = dict;
/*  52 */     this.dictionary.setDirect(true);
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  63 */     return getDictionary();
/*     */   }
/*     */ 
/*     */   public COSDictionary getDictionary()
/*     */   {
/*  73 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   public String getURL()
/*     */   {
/*  83 */     return this.dictionary.getString(COSName.URL);
/*     */   }
/*     */ 
/*     */   public void setURL(String url)
/*     */   {
/*  92 */     this.dictionary.setString(COSName.URL, url);
/*     */   }
/*     */ 
/*     */   public boolean isTimestampRequired()
/*     */   {
/* 102 */     return this.dictionary.getInt(COSName.FT, 0) != 0;
/*     */   }
/*     */ 
/*     */   public void setTimestampRequired(boolean flag)
/*     */   {
/* 112 */     this.dictionary.setInt(COSName.FT, flag ? 1 : 0);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSeedValueTimeStamp
 * JD-Core Version:    0.6.2
 */