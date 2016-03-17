/*     */ package com.itextpdf.text.pdf.parser;
/*     */ 
/*     */ public class FilteredRenderListener
/*     */   implements RenderListener
/*     */ {
/*     */   private final RenderListener delegate;
/*     */   private final RenderFilter[] filters;
/*     */ 
/*     */   public FilteredRenderListener(RenderListener delegate, RenderFilter[] filters)
/*     */   {
/*  65 */     this.delegate = delegate;
/*  66 */     this.filters = filters;
/*     */   }
/*     */ 
/*     */   public void renderText(TextRenderInfo renderInfo)
/*     */   {
/*  75 */     for (RenderFilter filter : this.filters) {
/*  76 */       if (!filter.allowText(renderInfo))
/*  77 */         return;
/*     */     }
/*  79 */     this.delegate.renderText(renderInfo);
/*     */   }
/*     */ 
/*     */   public void beginTextBlock()
/*     */   {
/*  87 */     this.delegate.beginTextBlock();
/*     */   }
/*     */ 
/*     */   public void endTextBlock()
/*     */   {
/*  95 */     this.delegate.endTextBlock();
/*     */   }
/*     */ 
/*     */   public void renderImage(ImageRenderInfo renderInfo)
/*     */   {
/* 104 */     for (RenderFilter filter : this.filters) {
/* 105 */       if (!filter.allowImage(renderInfo))
/* 106 */         return;
/*     */     }
/* 108 */     this.delegate.renderImage(renderInfo);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.FilteredRenderListener
 * JD-Core Version:    0.6.2
 */