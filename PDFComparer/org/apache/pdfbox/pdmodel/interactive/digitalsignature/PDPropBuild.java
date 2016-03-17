/*     */ package org.apache.pdfbox.pdmodel.interactive.digitalsignature;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ 
/*     */ public class PDPropBuild
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary dictionary;
/*     */ 
/*     */   public PDPropBuild()
/*     */   {
/*  45 */     this.dictionary = new COSDictionary();
/*  46 */     this.dictionary.setDirect(true);
/*     */   }
/*     */ 
/*     */   public PDPropBuild(COSDictionary dict)
/*     */   {
/*  56 */     this.dictionary = dict;
/*  57 */     this.dictionary.setDirect(true);
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  67 */     return getDictionary();
/*     */   }
/*     */ 
/*     */   public COSDictionary getDictionary()
/*     */   {
/*  77 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   public PDPropBuildDataDict getFilter()
/*     */   {
/*  88 */     PDPropBuildDataDict filter = null;
/*  89 */     COSDictionary filterDic = (COSDictionary)this.dictionary.getDictionaryObject(COSName.FILTER);
/*  90 */     if (filterDic != null)
/*     */     {
/*  92 */       filter = new PDPropBuildDataDict(filterDic);
/*     */     }
/*  94 */     return filter;
/*     */   }
/*     */ 
/*     */   public void setPDPropBuildFilter(PDPropBuildDataDict filter)
/*     */   {
/* 105 */     this.dictionary.setItem(COSName.FILTER, filter);
/*     */   }
/*     */ 
/*     */   public PDPropBuildDataDict getPubSec()
/*     */   {
/* 116 */     PDPropBuildDataDict pubSec = null;
/* 117 */     COSDictionary pubSecDic = (COSDictionary)this.dictionary.getDictionaryObject(COSName.PUB_SEC);
/* 118 */     if (pubSecDic != null)
/*     */     {
/* 120 */       pubSec = new PDPropBuildDataDict(pubSecDic);
/*     */     }
/* 122 */     return pubSec;
/*     */   }
/*     */ 
/*     */   public void setPDPropBuildPubSec(PDPropBuildDataDict pubSec)
/*     */   {
/* 132 */     this.dictionary.setItem(COSName.PUB_SEC, pubSec);
/*     */   }
/*     */ 
/*     */   public PDPropBuildDataDict getApp()
/*     */   {
/* 143 */     PDPropBuildDataDict app = null;
/* 144 */     COSDictionary appDic = (COSDictionary)this.dictionary.getDictionaryObject(COSName.APP);
/* 145 */     if (appDic != null)
/*     */     {
/* 147 */       app = new PDPropBuildDataDict(appDic);
/*     */     }
/* 149 */     return app;
/*     */   }
/*     */ 
/*     */   public void setPDPropBuildApp(PDPropBuildDataDict app)
/*     */   {
/* 160 */     this.dictionary.setItem(COSName.APP, app);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDPropBuild
 * JD-Core Version:    0.6.2
 */