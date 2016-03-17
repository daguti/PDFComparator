/*    */ package com.itextpdf.text.pdf.parser;
/*    */ 
/*    */ public class GlyphTextRenderListener extends GlyphRenderListener
/*    */   implements TextExtractionStrategy
/*    */ {
/*    */   private final TextExtractionStrategy delegate;
/*    */ 
/*    */   public GlyphTextRenderListener(TextExtractionStrategy delegate)
/*    */   {
/* 52 */     super(delegate);
/* 53 */     this.delegate = delegate;
/*    */   }
/*    */ 
/*    */   public String getResultantText() {
/* 57 */     return this.delegate.getResultantText();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.GlyphTextRenderListener
 * JD-Core Version:    0.6.2
 */