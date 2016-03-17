/*    */ package antlr;
/*    */ 
/*    */ class TokenRangeElement extends AlternativeElement
/*    */ {
/*    */   String label;
/* 12 */   protected int begin = 0;
/* 13 */   protected int end = 0;
/*    */   protected String beginText;
/*    */   protected String endText;
/*    */ 
/*    */   public TokenRangeElement(Grammar paramGrammar, Token paramToken1, Token paramToken2, int paramInt)
/*    */   {
/* 18 */     super(paramGrammar, paramToken1, paramInt);
/* 19 */     this.begin = this.grammar.tokenManager.getTokenSymbol(paramToken1.getText()).getTokenType();
/* 20 */     this.beginText = paramToken1.getText();
/* 21 */     this.end = this.grammar.tokenManager.getTokenSymbol(paramToken2.getText()).getTokenType();
/* 22 */     this.endText = paramToken2.getText();
/* 23 */     this.line = paramToken1.getLine();
/*    */   }
/*    */ 
/*    */   public void generate() {
/* 27 */     this.grammar.generator.gen(this);
/*    */   }
/*    */ 
/*    */   public String getLabel() {
/* 31 */     return this.label;
/*    */   }
/*    */ 
/*    */   public Lookahead look(int paramInt) {
/* 35 */     return this.grammar.theLLkAnalyzer.look(paramInt, this);
/*    */   }
/*    */ 
/*    */   public void setLabel(String paramString) {
/* 39 */     this.label = paramString;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 43 */     if (this.label != null) {
/* 44 */       return " " + this.label + ":" + this.beginText + ".." + this.endText;
/*    */     }
/*    */ 
/* 47 */     return " " + this.beginText + ".." + this.endText;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.TokenRangeElement
 * JD-Core Version:    0.6.2
 */