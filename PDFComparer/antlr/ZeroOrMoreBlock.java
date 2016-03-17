/*    */ package antlr;
/*    */ 
/*    */ class ZeroOrMoreBlock extends BlockWithImpliedExitPath
/*    */ {
/*    */   public ZeroOrMoreBlock(Grammar paramGrammar)
/*    */   {
/* 13 */     super(paramGrammar);
/*    */   }
/*    */ 
/*    */   public ZeroOrMoreBlock(Grammar paramGrammar, Token paramToken) {
/* 17 */     super(paramGrammar, paramToken);
/*    */   }
/*    */ 
/*    */   public void generate() {
/* 21 */     this.grammar.generator.gen(this);
/*    */   }
/*    */ 
/*    */   public Lookahead look(int paramInt) {
/* 25 */     return this.grammar.theLLkAnalyzer.look(paramInt, this);
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 29 */     return super.toString() + "*";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.ZeroOrMoreBlock
 * JD-Core Version:    0.6.2
 */