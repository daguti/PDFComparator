/*    */ package org.apache.pdfbox.preflight.font.container;
/*    */ 
/*    */ import org.apache.fontbox.ttf.GlyphTable;
/*    */ import org.apache.fontbox.ttf.HeaderTable;
/*    */ import org.apache.fontbox.ttf.HorizontalHeaderTable;
/*    */ import org.apache.fontbox.ttf.HorizontalMetricsTable;
/*    */ import org.apache.fontbox.ttf.TrueTypeFont;
/*    */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*    */ import org.apache.pdfbox.preflight.font.util.CIDToGIDMap;
/*    */ 
/*    */ public class CIDType2Container extends FontContainer
/*    */ {
/* 31 */   protected CIDToGIDMap cidToGid = null;
/*    */ 
/* 33 */   protected TrueTypeFont ttf = null;
/*    */ 
/*    */   public CIDType2Container(PDFont font)
/*    */   {
/* 37 */     super(font);
/*    */   }
/*    */ 
/*    */   protected float getFontProgramWidth(int cid)
/*    */   {
/* 43 */     float foundWidth = -1.0F;
/* 44 */     int glyphIndex = getGlyphIndex(cid);
/*    */ 
/* 47 */     if ((this.ttf != null) && (this.ttf.getGlyph().getGlyphs().length > glyphIndex))
/*    */     {
/* 54 */       int numberOfLongHorMetrics = this.ttf.getHorizontalHeader().getNumberOfHMetrics();
/* 55 */       int unitsPerEm = this.ttf.getHeader().getUnitsPerEm();
/* 56 */       int[] advanceGlyphWidths = this.ttf.getHorizontalMetrics().getAdvanceWidth();
/* 57 */       float glypdWidth = advanceGlyphWidths[(numberOfLongHorMetrics - 1)];
/* 58 */       if (glyphIndex < numberOfLongHorMetrics)
/*    */       {
/* 60 */         glypdWidth = advanceGlyphWidths[glyphIndex];
/*    */       }
/* 62 */       foundWidth = glypdWidth * 1000.0F / unitsPerEm;
/*    */     }
/* 64 */     return foundWidth;
/*    */   }
/*    */ 
/*    */   private int getGlyphIndex(int cid)
/*    */   {
/* 75 */     int glyphIndex = cid;
/* 76 */     if (this.cidToGid != null)
/*    */     {
/* 78 */       glyphIndex = this.cidToGid.getGID(cid);
/* 79 */       this.cidToGid.getClass(); if (glyphIndex == 0)
/*    */       {
/* 81 */         glyphIndex = -14;
/*    */       }
/*    */     }
/* 84 */     return glyphIndex;
/*    */   }
/*    */ 
/*    */   public void setCidToGid(CIDToGIDMap cidToGid)
/*    */   {
/* 89 */     this.cidToGid = cidToGid;
/*    */   }
/*    */ 
/*    */   public void setTtf(TrueTypeFont ttf)
/*    */   {
/* 94 */     this.ttf = ttf;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.container.CIDType2Container
 * JD-Core Version:    0.6.2
 */