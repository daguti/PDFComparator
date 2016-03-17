/*    */ package antlr;
/*    */ 
/*    */ class TokenRefElement extends GrammarAtom
/*    */ {
/*    */   public TokenRefElement(Grammar paramGrammar, Token paramToken, boolean paramBoolean, int paramInt)
/*    */   {
/* 16 */     super(paramGrammar, paramToken, paramInt);
/* 17 */     this.not = paramBoolean;
/* 18 */     TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol(this.atomText);
/* 19 */     if (localTokenSymbol == null) {
/* 20 */       paramGrammar.antlrTool.error("Undefined token symbol: " + this.atomText, this.grammar.getFilename(), paramToken.getLine(), paramToken.getColumn());
/*    */     }
/*    */     else
/*    */     {
/* 24 */       this.tokenType = localTokenSymbol.getTokenType();
/*    */ 
/* 28 */       setASTNodeType(localTokenSymbol.getASTNodeType());
/*    */     }
/* 30 */     this.line = paramToken.getLine();
/*    */   }
/*    */ 
/*    */   public void generate() {
/* 34 */     this.grammar.generator.gen(this);
/*    */   }
/*    */ 
/*    */   public Lookahead look(int paramInt) {
/* 38 */     return this.grammar.theLLkAnalyzer.look(paramInt, this);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.TokenRefElement
 * JD-Core Version:    0.6.2
 */