/*    */ package antlr;
/*    */ 
/*    */ class RuleEndElement extends BlockEndElement
/*    */ {
/*    */   protected Lookahead[] cache;
/*    */   protected boolean noFOLLOW;
/*    */ 
/*    */   public RuleEndElement(Grammar paramGrammar)
/*    */   {
/* 21 */     super(paramGrammar);
/* 22 */     this.cache = new Lookahead[paramGrammar.maxk + 1];
/*    */   }
/*    */ 
/*    */   public Lookahead look(int paramInt) {
/* 26 */     return this.grammar.theLLkAnalyzer.look(paramInt, this);
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 31 */     return "";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.RuleEndElement
 * JD-Core Version:    0.6.2
 */