/*    */ package antlr;
/*    */ 
/*    */ class WildcardElement extends GrammarAtom
/*    */ {
/*    */   protected String label;
/*    */ 
/*    */   public WildcardElement(Grammar paramGrammar, Token paramToken, int paramInt)
/*    */   {
/* 14 */     super(paramGrammar, paramToken, paramInt);
/* 15 */     this.line = paramToken.getLine();
/*    */   }
/*    */ 
/*    */   public void generate() {
/* 19 */     this.grammar.generator.gen(this);
/*    */   }
/*    */ 
/*    */   public String getLabel() {
/* 23 */     return this.label;
/*    */   }
/*    */ 
/*    */   public Lookahead look(int paramInt) {
/* 27 */     return this.grammar.theLLkAnalyzer.look(paramInt, this);
/*    */   }
/*    */ 
/*    */   public void setLabel(String paramString) {
/* 31 */     this.label = paramString;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 35 */     String str = " ";
/* 36 */     if (this.label != null) str = str + this.label + ":";
/* 37 */     return str + ".";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.WildcardElement
 * JD-Core Version:    0.6.2
 */