/*    */ package org.apache.fontbox.cmap;
/*    */ 
/*    */ class CIDRange
/*    */ {
/*    */   private char from;
/*    */   private char to;
/*    */   private int cid;
/*    */ 
/*    */   public CIDRange(char from, char to, int cid)
/*    */   {
/* 32 */     this.from = from;
/* 33 */     this.to = to;
/* 34 */     this.cid = cid;
/*    */   }
/*    */ 
/*    */   public int map(char ch)
/*    */   {
/* 44 */     if ((this.from <= ch) && (ch <= this.to)) {
/* 45 */       return this.cid + (ch - this.from);
/*    */     }
/* 47 */     return -1;
/*    */   }
/*    */ 
/*    */   public int unmap(int code)
/*    */   {
/* 58 */     if ((this.cid <= code) && (code <= this.cid + (this.to - this.from))) {
/* 59 */       return this.from + (code - this.cid);
/*    */     }
/* 61 */     return -1;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.cmap.CIDRange
 * JD-Core Version:    0.6.2
 */