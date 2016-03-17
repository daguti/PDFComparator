/*     */ package com.itextpdf.text.pdf.parser;
/*     */ 
/*     */ public class SimpleTextExtractionStrategy
/*     */   implements TextExtractionStrategy
/*     */ {
/*     */   private Vector lastStart;
/*     */   private Vector lastEnd;
/*  67 */   private final StringBuffer result = new StringBuffer();
/*     */ 
/*     */   public void beginTextBlock()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void endTextBlock()
/*     */   {
/*     */   }
/*     */ 
/*     */   public String getResultantText()
/*     */   {
/*  92 */     return this.result.toString();
/*     */   }
/*     */ 
/*     */   protected final void appendTextChunk(CharSequence text)
/*     */   {
/* 102 */     this.result.append(text);
/*     */   }
/*     */ 
/*     */   public void renderText(TextRenderInfo renderInfo)
/*     */   {
/* 110 */     boolean firstRender = this.result.length() == 0;
/* 111 */     boolean hardReturn = false;
/*     */ 
/* 113 */     LineSegment segment = renderInfo.getBaseline();
/* 114 */     Vector start = segment.getStartPoint();
/* 115 */     Vector end = segment.getEndPoint();
/*     */ 
/* 117 */     if (!firstRender) {
/* 118 */       Vector x0 = start;
/* 119 */       Vector x1 = this.lastStart;
/* 120 */       Vector x2 = this.lastEnd;
/*     */ 
/* 123 */       float dist = x2.subtract(x1).cross(x1.subtract(x0)).lengthSquared() / x2.subtract(x1).lengthSquared();
/*     */ 
/* 125 */       float sameLineThreshold = 1.0F;
/* 126 */       if (dist > sameLineThreshold) {
/* 127 */         hardReturn = true;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 133 */     if (hardReturn)
/*     */     {
/* 135 */       appendTextChunk("\n");
/* 136 */     } else if ((!firstRender) && 
/* 137 */       (this.result.charAt(this.result.length() - 1) != ' ') && (renderInfo.getText().length() > 0) && (renderInfo.getText().charAt(0) != ' ')) {
/* 138 */       float spacing = this.lastEnd.subtract(start).length();
/* 139 */       if (spacing > renderInfo.getSingleSpaceWidth() / 2.0F) {
/* 140 */         appendTextChunk(" ");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 149 */     appendTextChunk(renderInfo.getText());
/*     */ 
/* 151 */     this.lastStart = start;
/* 152 */     this.lastEnd = end;
/*     */   }
/*     */ 
/*     */   public void renderImage(ImageRenderInfo renderInfo)
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy
 * JD-Core Version:    0.6.2
 */