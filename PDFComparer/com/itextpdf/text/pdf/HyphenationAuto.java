/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.pdf.hyphenation.Hyphenation;
/*     */ import com.itextpdf.text.pdf.hyphenation.Hyphenator;
/*     */ 
/*     */ public class HyphenationAuto
/*     */   implements HyphenationEvent
/*     */ {
/*     */   protected Hyphenator hyphenator;
/*     */   protected String post;
/*     */ 
/*     */   public HyphenationAuto(String lang, String country, int leftMin, int rightMin)
/*     */   {
/*  72 */     this.hyphenator = new Hyphenator(lang, country, leftMin, rightMin);
/*     */   }
/*     */ 
/*     */   public String getHyphenSymbol()
/*     */   {
/*  79 */     return "-";
/*     */   }
/*     */ 
/*     */   public String getHyphenatedWordPre(String word, BaseFont font, float fontSize, float remainingWidth)
/*     */   {
/*  92 */     this.post = word;
/*  93 */     String hyphen = getHyphenSymbol();
/*  94 */     float hyphenWidth = font.getWidthPoint(hyphen, fontSize);
/*  95 */     if (hyphenWidth > remainingWidth)
/*  96 */       return "";
/*  97 */     Hyphenation hyphenation = this.hyphenator.hyphenate(word);
/*  98 */     if (hyphenation == null) {
/*  99 */       return "";
/*     */     }
/* 101 */     int len = hyphenation.length();
/*     */ 
/* 103 */     for (int k = 0; (k < len) && 
/* 104 */       (font.getWidthPoint(hyphenation.getPreHyphenText(k), fontSize) + hyphenWidth <= remainingWidth); k++);
/* 107 */     k--;
/* 108 */     if (k < 0)
/* 109 */       return "";
/* 110 */     this.post = hyphenation.getPostHyphenText(k);
/* 111 */     return hyphenation.getPreHyphenText(k) + hyphen;
/*     */   }
/*     */ 
/*     */   public String getHyphenatedWordPost()
/*     */   {
/* 119 */     return this.post;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.HyphenationAuto
 * JD-Core Version:    0.6.2
 */