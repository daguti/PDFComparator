/*    */ package com.itextpdf.text.pdf.parser;
/*    */ 
/*    */ public class MarkedContentRenderFilter extends RenderFilter
/*    */ {
/*    */   private int mcid;
/*    */ 
/*    */   public MarkedContentRenderFilter(int mcid)
/*    */   {
/* 61 */     this.mcid = mcid;
/*    */   }
/*    */ 
/*    */   public boolean allowText(TextRenderInfo renderInfo)
/*    */   {
/* 68 */     return renderInfo.hasMcid(this.mcid);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.MarkedContentRenderFilter
 * JD-Core Version:    0.6.2
 */