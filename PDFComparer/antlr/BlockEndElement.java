/*    */ package antlr;
/*    */ 
/*    */ class BlockEndElement extends AlternativeElement
/*    */ {
/*    */   protected boolean[] lock;
/*    */   protected AlternativeBlock block;
/*    */ 
/*    */   public BlockEndElement(Grammar paramGrammar)
/*    */   {
/* 19 */     super(paramGrammar);
/* 20 */     this.lock = new boolean[paramGrammar.maxk + 1];
/*    */   }
/*    */ 
/*    */   public Lookahead look(int paramInt) {
/* 24 */     return this.grammar.theLLkAnalyzer.look(paramInt, this);
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 29 */     return "";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.BlockEndElement
 * JD-Core Version:    0.6.2
 */