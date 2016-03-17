/*    */ package antlr;
/*    */ 
/*    */ public class CommonToken extends Token
/*    */ {
/*    */   protected int line;
/* 13 */   protected String text = null;
/*    */   protected int col;
/*    */ 
/*    */   public CommonToken()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CommonToken(int paramInt, String paramString)
/*    */   {
/* 20 */     this.type = paramInt;
/* 21 */     setText(paramString);
/*    */   }
/*    */ 
/*    */   public CommonToken(String paramString) {
/* 25 */     this.text = paramString;
/*    */   }
/*    */ 
/*    */   public int getLine() {
/* 29 */     return this.line;
/*    */   }
/*    */ 
/*    */   public String getText() {
/* 33 */     return this.text;
/*    */   }
/*    */ 
/*    */   public void setLine(int paramInt) {
/* 37 */     this.line = paramInt;
/*    */   }
/*    */ 
/*    */   public void setText(String paramString) {
/* 41 */     this.text = paramString;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 45 */     return "[\"" + getText() + "\",<" + this.type + ">,line=" + this.line + ",col=" + this.col + "]";
/*    */   }
/*    */ 
/*    */   public int getColumn()
/*    */   {
/* 50 */     return this.col;
/*    */   }
/*    */ 
/*    */   public void setColumn(int paramInt) {
/* 54 */     this.col = paramInt;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.CommonToken
 * JD-Core Version:    0.6.2
 */