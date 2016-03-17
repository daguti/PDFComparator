/*    */ package antlr;
/*    */ 
/*    */ class RuleRefElement extends AlternativeElement
/*    */ {
/*    */   protected String targetRule;
/* 12 */   protected String args = null;
/* 13 */   protected String idAssign = null;
/*    */   protected String label;
/*    */ 
/*    */   public RuleRefElement(Grammar paramGrammar, Token paramToken, int paramInt)
/*    */   {
/* 18 */     super(paramGrammar, paramToken, paramInt);
/* 19 */     this.targetRule = paramToken.getText();
/*    */ 
/* 21 */     if (paramToken.type == 24)
/* 22 */       this.targetRule = CodeGenerator.encodeLexerRuleName(this.targetRule);
/*    */   }
/*    */ 
/*    */   public void generate()
/*    */   {
/* 36 */     this.grammar.generator.gen(this);
/*    */   }
/*    */ 
/*    */   public String getArgs() {
/* 40 */     return this.args;
/*    */   }
/*    */ 
/*    */   public String getIdAssign() {
/* 44 */     return this.idAssign;
/*    */   }
/*    */ 
/*    */   public String getLabel() {
/* 48 */     return this.label;
/*    */   }
/*    */ 
/*    */   public Lookahead look(int paramInt) {
/* 52 */     return this.grammar.theLLkAnalyzer.look(paramInt, this);
/*    */   }
/*    */ 
/*    */   public void setArgs(String paramString) {
/* 56 */     this.args = paramString;
/*    */   }
/*    */ 
/*    */   public void setIdAssign(String paramString) {
/* 60 */     this.idAssign = paramString;
/*    */   }
/*    */ 
/*    */   public void setLabel(String paramString) {
/* 64 */     this.label = paramString;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 68 */     if (this.args != null) {
/* 69 */       return " " + this.targetRule + this.args;
/*    */     }
/* 71 */     return " " + this.targetRule;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.RuleRefElement
 * JD-Core Version:    0.6.2
 */