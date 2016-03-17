/*     */ package org.apache.pdfbox.util;
/*     */ 
/*     */ public class PositionWrapper
/*     */ {
/*  36 */   private boolean isLineStart = false;
/*  37 */   private boolean isParagraphStart = false;
/*  38 */   private boolean isPageBreak = false;
/*  39 */   private boolean isHangingIndent = false;
/*  40 */   private boolean isArticleStart = false;
/*     */ 
/*  42 */   private TextPosition position = null;
/*     */ 
/*     */   public TextPosition getTextPosition()
/*     */   {
/*  50 */     return this.position;
/*     */   }
/*     */ 
/*     */   public boolean isLineStart()
/*     */   {
/*  56 */     return this.isLineStart;
/*     */   }
/*     */ 
/*     */   public void setLineStart()
/*     */   {
/*  65 */     this.isLineStart = true;
/*     */   }
/*     */ 
/*     */   public boolean isParagraphStart()
/*     */   {
/*  71 */     return this.isParagraphStart;
/*     */   }
/*     */ 
/*     */   public void setParagraphStart()
/*     */   {
/*  80 */     this.isParagraphStart = true;
/*     */   }
/*     */ 
/*     */   public boolean isArticleStart()
/*     */   {
/*  86 */     return this.isArticleStart;
/*     */   }
/*     */ 
/*     */   public void setArticleStart()
/*     */   {
/*  95 */     this.isArticleStart = true;
/*     */   }
/*     */ 
/*     */   public boolean isPageBreak()
/*     */   {
/* 101 */     return this.isPageBreak;
/*     */   }
/*     */ 
/*     */   public void setPageBreak()
/*     */   {
/* 110 */     this.isPageBreak = true;
/*     */   }
/*     */ 
/*     */   public boolean isHangingIndent()
/*     */   {
/* 116 */     return this.isHangingIndent;
/*     */   }
/*     */ 
/*     */   public void setHangingIndent()
/*     */   {
/* 125 */     this.isHangingIndent = true;
/*     */   }
/*     */ 
/*     */   public PositionWrapper(TextPosition position)
/*     */   {
/* 135 */     this.position = position;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.PositionWrapper
 * JD-Core Version:    0.6.2
 */