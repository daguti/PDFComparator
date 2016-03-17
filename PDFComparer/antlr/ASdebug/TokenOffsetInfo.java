/*    */ package antlr.ASdebug;
/*    */ 
/*    */ public class TokenOffsetInfo
/*    */ {
/*    */   public final int beginOffset;
/*    */   public final int length;
/*    */ 
/*    */   public TokenOffsetInfo(int paramInt1, int paramInt2)
/*    */   {
/* 14 */     this.beginOffset = paramInt1;
/* 15 */     this.length = paramInt2;
/*    */   }
/*    */ 
/*    */   public int getEndOffset()
/*    */   {
/* 20 */     return this.beginOffset + this.length - 1;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.ASdebug.TokenOffsetInfo
 * JD-Core Version:    0.6.2
 */