/*     */ package org.apache.pdfbox.pdmodel.encryption;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ 
/*     */ public class PDCryptFilterDictionary
/*     */ {
/*  39 */   protected COSDictionary cryptFilterDictionary = null;
/*     */ 
/*     */   public PDCryptFilterDictionary()
/*     */   {
/*  46 */     this.cryptFilterDictionary = new COSDictionary();
/*     */   }
/*     */ 
/*     */   public PDCryptFilterDictionary(COSDictionary d)
/*     */   {
/*  55 */     this.cryptFilterDictionary = d;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/*  65 */     return this.cryptFilterDictionary;
/*     */   }
/*     */ 
/*     */   public void setLength(int length)
/*     */   {
/*  75 */     this.cryptFilterDictionary.setInt(COSName.LENGTH, length);
/*     */   }
/*     */ 
/*     */   public int getLength()
/*     */   {
/*  86 */     return this.cryptFilterDictionary.getInt(COSName.LENGTH, 40);
/*     */   }
/*     */ 
/*     */   public void setCryptFilterMethod(COSName cfm)
/*     */     throws IOException
/*     */   {
/*  99 */     this.cryptFilterDictionary.setItem(COSName.CFM, cfm);
/*     */   }
/*     */ 
/*     */   public COSName getCryptFilterMethod()
/*     */     throws IOException
/*     */   {
/* 112 */     return (COSName)this.cryptFilterDictionary.getDictionaryObject(COSName.CFM);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.encryption.PDCryptFilterDictionary
 * JD-Core Version:    0.6.2
 */