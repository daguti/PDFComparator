/*    */ package org.apache.pdfbox.preflight.font.util;
/*    */ 
/*    */ public class GlyphDetail
/*    */ {
/* 26 */   private GlyphException invalidGlyphError = null;
/* 27 */   private int charecterIdentifier = 0;
/*    */ 
/*    */   public GlyphDetail(int cid)
/*    */   {
/* 31 */     this.charecterIdentifier = cid;
/*    */   }
/*    */ 
/*    */   public GlyphDetail(int cid, GlyphException error)
/*    */   {
/* 36 */     this.charecterIdentifier = cid;
/* 37 */     this.invalidGlyphError = error;
/*    */   }
/*    */ 
/*    */   public void throwExceptionIfNotValid() throws GlyphException
/*    */   {
/* 42 */     if (this.invalidGlyphError != null)
/*    */     {
/* 44 */       throw this.invalidGlyphError;
/*    */     }
/*    */   }
/*    */ 
/*    */   public int getCID()
/*    */   {
/* 50 */     return this.charecterIdentifier;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.util.GlyphDetail
 * JD-Core Version:    0.6.2
 */