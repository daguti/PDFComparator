/*    */ package org.apache.fontbox.ttf;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class GlyphTable extends TTFTable
/*    */ {
/*    */   public static final String TAG = "glyf";
/*    */   private GlyphData[] glyphs;
/*    */ 
/*    */   public void initData(TrueTypeFont ttf, TTFDataStream data)
/*    */     throws IOException
/*    */   {
/* 45 */     IndexToLocationTable loc = ttf.getIndexToLocation();
/*    */ 
/* 47 */     long[] offsets = loc.getOffsets();
/*    */ 
/* 49 */     int numGlyphs = ttf.getNumberOfGlyphs();
/*    */ 
/* 51 */     long endOfGlyphs = offsets[numGlyphs];
/* 52 */     long offset = getOffset();
/* 53 */     this.glyphs = new GlyphData[numGlyphs];
/* 54 */     for (int i = 0; i < numGlyphs; i++)
/*    */     {
/* 57 */       if (endOfGlyphs == offsets[i])
/*    */       {
/*    */         break;
/*    */       }
/*    */ 
/* 63 */       if (offsets[i] != offsets[(i + 1)])
/*    */       {
/* 67 */         this.glyphs[i] = new GlyphData();
/* 68 */         data.seek(offset + offsets[i]);
/* 69 */         this.glyphs[i].initData(this, data);
/*    */       }
/*    */     }
/* 71 */     for (int i = 0; i < numGlyphs; i++)
/*    */     {
/* 73 */       GlyphData glyph = this.glyphs[i];
/*    */ 
/* 75 */       if ((glyph != null) && (glyph.getDescription().isComposite()))
/*    */       {
/* 77 */         glyph.getDescription().resolve();
/*    */       }
/*    */     }
/* 80 */     this.initialized = true;
/*    */   }
/*    */ 
/*    */   public GlyphData[] getGlyphs()
/*    */   {
/* 88 */     return this.glyphs;
/*    */   }
/*    */ 
/*    */   public void setGlyphs(GlyphData[] glyphsValue)
/*    */   {
/* 96 */     this.glyphs = glyphsValue;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.ttf.GlyphTable
 * JD-Core Version:    0.6.2
 */