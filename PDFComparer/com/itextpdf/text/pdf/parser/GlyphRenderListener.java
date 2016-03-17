/*    */ package com.itextpdf.text.pdf.parser;
/*    */ 
/*    */ public class GlyphRenderListener
/*    */   implements RenderListener
/*    */ {
/*    */   private final RenderListener delegate;
/*    */ 
/*    */   public GlyphRenderListener(RenderListener delegate)
/*    */   {
/* 53 */     this.delegate = delegate;
/*    */   }
/*    */ 
/*    */   public void beginTextBlock() {
/* 57 */     this.delegate.beginTextBlock();
/*    */   }
/*    */ 
/*    */   public void renderText(TextRenderInfo renderInfo) {
/* 61 */     for (TextRenderInfo glyphInfo : renderInfo.getCharacterRenderInfos())
/* 62 */       this.delegate.renderText(glyphInfo);
/*    */   }
/*    */ 
/*    */   public void endTextBlock() {
/* 66 */     this.delegate.endTextBlock();
/*    */   }
/*    */ 
/*    */   public void renderImage(ImageRenderInfo renderInfo) {
/* 70 */     this.delegate.renderImage(renderInfo);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.GlyphRenderListener
 * JD-Core Version:    0.6.2
 */