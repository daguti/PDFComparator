/*    */ package com.itextpdf.text.pdf.parser;
/*    */ 
/*    */ public class FilteredTextRenderListener extends FilteredRenderListener
/*    */   implements TextExtractionStrategy
/*    */ {
/*    */   private final TextExtractionStrategy delegate;
/*    */ 
/*    */   public FilteredTextRenderListener(TextExtractionStrategy delegate, RenderFilter[] filters)
/*    */   {
/* 63 */     super(delegate, filters);
/* 64 */     this.delegate = delegate;
/*    */   }
/*    */ 
/*    */   public String getResultantText()
/*    */   {
/* 72 */     return this.delegate.getResultantText();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.FilteredTextRenderListener
 * JD-Core Version:    0.6.2
 */