/*    */ package antlr;
/*    */ 
/*    */ class SynPredBlock extends AlternativeBlock
/*    */ {
/*    */   public SynPredBlock(Grammar paramGrammar)
/*    */   {
/* 13 */     super(paramGrammar);
/*    */   }
/*    */ 
/*    */   public SynPredBlock(Grammar paramGrammar, Token paramToken) {
/* 17 */     super(paramGrammar, paramToken, false);
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
/* 29 */     return super.toString() + "=>";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.SynPredBlock
 * JD-Core Version:    0.6.2
 */