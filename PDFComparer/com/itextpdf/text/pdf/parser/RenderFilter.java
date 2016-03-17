/*    */ package com.itextpdf.text.pdf.parser;
/*    */ 
/*    */ public abstract class RenderFilter
/*    */ {
/*    */   public boolean allowText(TextRenderInfo renderInfo)
/*    */   {
/* 59 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean allowImage(ImageRenderInfo renderInfo)
/*    */   {
/* 68 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.RenderFilter
 * JD-Core Version:    0.6.2
 */