/*    */ package antlr.collections.impl;
/*    */ 
/*    */ public class IntRange
/*    */ {
/*    */   int begin;
/*    */   int end;
/*    */ 
/*    */   public IntRange(int paramInt1, int paramInt2)
/*    */   {
/* 15 */     this.begin = paramInt1;
/* 16 */     this.end = paramInt2;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 20 */     return this.begin + ".." + this.end;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.collections.impl.IntRange
 * JD-Core Version:    0.6.2
 */