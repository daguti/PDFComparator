/*    */ package antlr;
/*    */ 
/*    */ class TokenSymbol extends GrammarSymbol
/*    */ {
/*    */   protected int ttype;
/* 13 */   protected String paraphrase = null;
/*    */   protected String ASTNodeType;
/*    */ 
/*    */   public TokenSymbol(String paramString)
/*    */   {
/* 19 */     super(paramString);
/* 20 */     this.ttype = 0;
/*    */   }
/*    */ 
/*    */   public String getASTNodeType() {
/* 24 */     return this.ASTNodeType;
/*    */   }
/*    */ 
/*    */   public void setASTNodeType(String paramString) {
/* 28 */     this.ASTNodeType = paramString;
/*    */   }
/*    */ 
/*    */   public String getParaphrase() {
/* 32 */     return this.paraphrase;
/*    */   }
/*    */ 
/*    */   public int getTokenType() {
/* 36 */     return this.ttype;
/*    */   }
/*    */ 
/*    */   public void setParaphrase(String paramString) {
/* 40 */     this.paraphrase = paramString;
/*    */   }
/*    */ 
/*    */   public void setTokenType(int paramInt) {
/* 44 */     this.ttype = paramInt;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.TokenSymbol
 * JD-Core Version:    0.6.2
 */