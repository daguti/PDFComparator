/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ 
/*     */ public class ChapterAutoNumber extends Chapter
/*     */ {
/*     */   private static final long serialVersionUID = -9217457637987854167L;
/*  62 */   protected boolean numberSet = false;
/*     */ 
/*     */   public ChapterAutoNumber(Paragraph para)
/*     */   {
/*  70 */     super(para, 0);
/*     */   }
/*     */ 
/*     */   public ChapterAutoNumber(String title)
/*     */   {
/*  79 */     super(title, 0);
/*     */   }
/*     */ 
/*     */   public Section addSection(String title)
/*     */   {
/*  90 */     if (isAddedCompletely()) {
/*  91 */       throw new IllegalStateException(MessageLocalization.getComposedMessage("this.largeelement.has.already.been.added.to.the.document", new Object[0]));
/*     */     }
/*  93 */     return addSection(title, 2);
/*     */   }
/*     */ 
/*     */   public Section addSection(Paragraph title)
/*     */   {
/* 104 */     if (isAddedCompletely()) {
/* 105 */       throw new IllegalStateException(MessageLocalization.getComposedMessage("this.largeelement.has.already.been.added.to.the.document", new Object[0]));
/*     */     }
/* 107 */     return addSection(title, 2);
/*     */   }
/*     */ 
/*     */   public int setAutomaticNumber(int number)
/*     */   {
/* 117 */     if (!this.numberSet) {
/* 118 */       number++;
/* 119 */       super.setChapterNumber(number);
/* 120 */       this.numberSet = true;
/*     */     }
/* 122 */     return number;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.ChapterAutoNumber
 * JD-Core Version:    0.6.2
 */