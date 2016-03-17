/*    */ package com.itextpdf.text.pdf.parser;
/*    */ 
/*    */ import com.itextpdf.awt.geom.Rectangle2D;
/*    */ 
/*    */ public class RegionTextRenderFilter extends RenderFilter
/*    */ {
/*    */   private final Rectangle2D filterRect;
/*    */ 
/*    */   public RegionTextRenderFilter(Rectangle2D filterRect)
/*    */   {
/* 64 */     this.filterRect = filterRect;
/*    */   }
/*    */ 
/*    */   public RegionTextRenderFilter(com.itextpdf.text.Rectangle filterRect)
/*    */   {
/* 72 */     this.filterRect = new com.itextpdf.awt.geom.Rectangle(filterRect);
/*    */   }
/*    */ 
/*    */   public boolean allowText(TextRenderInfo renderInfo)
/*    */   {
/* 78 */     LineSegment segment = renderInfo.getBaseline();
/* 79 */     Vector startPoint = segment.getStartPoint();
/* 80 */     Vector endPoint = segment.getEndPoint();
/*    */ 
/* 82 */     float x1 = startPoint.get(0);
/* 83 */     float y1 = startPoint.get(1);
/* 84 */     float x2 = endPoint.get(0);
/* 85 */     float y2 = endPoint.get(1);
/*    */ 
/* 87 */     return this.filterRect.intersectsLine(x1, y1, x2, y2);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.RegionTextRenderFilter
 * JD-Core Version:    0.6.2
 */