/*    */ package antlr;
/*    */ 
/*    */ public class ParseTreeToken extends ParseTree
/*    */ {
/*    */   protected Token token;
/*    */ 
/*    */   public ParseTreeToken(Token paramToken)
/*    */   {
/* 15 */     this.token = paramToken;
/*    */   }
/*    */ 
/*    */   protected int getLeftmostDerivation(StringBuffer paramStringBuffer, int paramInt) {
/* 19 */     paramStringBuffer.append(' ');
/* 20 */     paramStringBuffer.append(toString());
/* 21 */     return paramInt;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 25 */     if (this.token != null) {
/* 26 */       return this.token.getText();
/*    */     }
/* 28 */     return "<missing token>";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.ParseTreeToken
 * JD-Core Version:    0.6.2
 */