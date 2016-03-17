/*    */ package antlr;
/*    */ 
/*    */ public class TokenWithIndex extends CommonToken
/*    */ {
/*    */   int index;
/*    */ 
/*    */   public TokenWithIndex()
/*    */   {
/*    */   }
/*    */ 
/*    */   public TokenWithIndex(int paramInt, String paramString)
/*    */   {
/* 20 */     super(paramInt, paramString);
/*    */   }
/*    */ 
/*    */   public void setIndex(int paramInt) {
/* 24 */     this.index = paramInt;
/*    */   }
/*    */ 
/*    */   public int getIndex() {
/* 28 */     return this.index;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 32 */     return "[" + this.index + ":\"" + getText() + "\",<" + getType() + ">,line=" + this.line + ",col=" + this.col + "]\n";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.TokenWithIndex
 * JD-Core Version:    0.6.2
 */