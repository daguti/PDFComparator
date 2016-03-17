/*    */ package antlr;
/*    */ 
/*    */ class ActionElement extends AlternativeElement
/*    */ {
/*    */   protected String actionText;
/* 12 */   protected boolean isSemPred = false;
/*    */ 
/*    */   public ActionElement(Grammar paramGrammar, Token paramToken)
/*    */   {
/* 16 */     super(paramGrammar);
/* 17 */     this.actionText = paramToken.getText();
/* 18 */     this.line = paramToken.getLine();
/* 19 */     this.column = paramToken.getColumn();
/*    */   }
/*    */ 
/*    */   public void generate() {
/* 23 */     this.grammar.generator.gen(this);
/*    */   }
/*    */ 
/*    */   public Lookahead look(int paramInt) {
/* 27 */     return this.grammar.theLLkAnalyzer.look(paramInt, this);
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 31 */     return " " + this.actionText + (this.isSemPred ? "?" : "");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.ActionElement
 * JD-Core Version:    0.6.2
 */