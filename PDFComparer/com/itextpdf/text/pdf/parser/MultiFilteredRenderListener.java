/*    */ package com.itextpdf.text.pdf.parser;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class MultiFilteredRenderListener
/*    */   implements RenderListener
/*    */ {
/*    */   private final List<RenderListener> delegates;
/*    */   private final List<RenderFilter[]> filters;
/*    */ 
/*    */   public MultiFilteredRenderListener()
/*    */   {
/* 56 */     this.delegates = new ArrayList();
/* 57 */     this.filters = new ArrayList();
/*    */   }
/*    */ 
/*    */   public <E extends RenderListener> E attachRenderListener(E delegate, RenderFilter[] filterSet)
/*    */   {
/* 66 */     this.delegates.add(delegate);
/* 67 */     this.filters.add(filterSet);
/*    */ 
/* 69 */     return delegate;
/*    */   }
/*    */ 
/*    */   public void beginTextBlock() {
/* 73 */     for (RenderListener delegate : this.delegates)
/* 74 */       delegate.beginTextBlock();
/*    */   }
/*    */ 
/*    */   public void renderText(TextRenderInfo renderInfo)
/*    */   {
/* 79 */     for (int i = 0; i < this.delegates.size(); i++) {
/* 80 */       boolean filtersPassed = true;
/* 81 */       for (RenderFilter filter : (RenderFilter[])this.filters.get(i)) {
/* 82 */         if (!filter.allowText(renderInfo)) {
/* 83 */           filtersPassed = false;
/* 84 */           break;
/*    */         }
/*    */       }
/* 87 */       if (filtersPassed)
/* 88 */         ((RenderListener)this.delegates.get(i)).renderText(renderInfo);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void endTextBlock() {
/* 93 */     for (RenderListener delegate : this.delegates)
/* 94 */       delegate.endTextBlock();
/*    */   }
/*    */ 
/*    */   public void renderImage(ImageRenderInfo renderInfo)
/*    */   {
/* 99 */     for (int i = 0; i < this.delegates.size(); i++) {
/* 100 */       boolean filtersPassed = true;
/* 101 */       for (RenderFilter filter : (RenderFilter[])this.filters.get(i)) {
/* 102 */         if (!filter.allowImage(renderInfo)) {
/* 103 */           filtersPassed = false;
/* 104 */           break;
/*    */         }
/*    */       }
/* 107 */       if (filtersPassed)
/* 108 */         ((RenderListener)this.delegates.get(i)).renderImage(renderInfo);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.MultiFilteredRenderListener
 * JD-Core Version:    0.6.2
 */