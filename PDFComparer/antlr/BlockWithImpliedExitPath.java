/*    */ package antlr;
/*    */ 
/*    */ abstract class BlockWithImpliedExitPath extends AlternativeBlock
/*    */ {
/*    */   protected int exitLookaheadDepth;
/* 15 */   protected Lookahead[] exitCache = new Lookahead[this.grammar.maxk + 1];
/*    */ 
/*    */   public BlockWithImpliedExitPath(Grammar paramGrammar) {
/* 18 */     super(paramGrammar);
/*    */   }
/*    */ 
/*    */   public BlockWithImpliedExitPath(Grammar paramGrammar, Token paramToken) {
/* 22 */     super(paramGrammar, paramToken, false);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.BlockWithImpliedExitPath
 * JD-Core Version:    0.6.2
 */