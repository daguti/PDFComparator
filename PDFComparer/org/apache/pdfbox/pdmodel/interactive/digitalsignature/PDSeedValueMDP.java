/*     */ package org.apache.pdfbox.pdmodel.interactive.digitalsignature;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ 
/*     */ public class PDSeedValueMDP
/*     */ {
/*     */   private COSDictionary dictionary;
/*     */ 
/*     */   public PDSeedValueMDP()
/*     */   {
/*  43 */     this.dictionary = new COSDictionary();
/*  44 */     this.dictionary.setDirect(true);
/*     */   }
/*     */ 
/*     */   public PDSeedValueMDP(COSDictionary dict)
/*     */   {
/*  54 */     this.dictionary = dict;
/*  55 */     this.dictionary.setDirect(true);
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  66 */     return getDictionary();
/*     */   }
/*     */ 
/*     */   public COSDictionary getDictionary()
/*     */   {
/*  76 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   public int getP()
/*     */   {
/*  86 */     return this.dictionary.getInt(COSName.P);
/*     */   }
/*     */ 
/*     */   public void setP(int p)
/*     */   {
/*  96 */     if ((p < 0) || (p > 3))
/*     */     {
/*  98 */       throw new IllegalArgumentException("Only values between 0 and 3 nare allowed.");
/*     */     }
/* 100 */     this.dictionary.setInt(COSName.P, p);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSeedValueMDP
 * JD-Core Version:    0.6.2
 */