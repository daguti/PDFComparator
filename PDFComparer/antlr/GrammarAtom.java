/*    */ package antlr;
/*    */ 
/*    */ abstract class GrammarAtom extends AlternativeElement
/*    */ {
/*    */   protected String label;
/*    */   protected String atomText;
/* 16 */   protected int tokenType = 0;
/* 17 */   protected boolean not = false;
/*    */ 
/* 21 */   protected String ASTNodeType = null;
/*    */ 
/*    */   public GrammarAtom(Grammar paramGrammar, Token paramToken, int paramInt) {
/* 24 */     super(paramGrammar, paramToken, paramInt);
/* 25 */     this.atomText = paramToken.getText();
/*    */   }
/*    */ 
/*    */   public String getLabel() {
/* 29 */     return this.label;
/*    */   }
/*    */ 
/*    */   public String getText() {
/* 33 */     return this.atomText;
/*    */   }
/*    */ 
/*    */   public int getType() {
/* 37 */     return this.tokenType;
/*    */   }
/*    */ 
/*    */   public void setLabel(String paramString) {
/* 41 */     this.label = paramString;
/*    */   }
/*    */ 
/*    */   public String getASTNodeType() {
/* 45 */     return this.ASTNodeType;
/*    */   }
/*    */ 
/*    */   public void setASTNodeType(String paramString) {
/* 49 */     this.ASTNodeType = paramString;
/*    */   }
/*    */ 
/*    */   public void setOption(Token paramToken1, Token paramToken2) {
/* 53 */     if (paramToken1.getText().equals("AST")) {
/* 54 */       setASTNodeType(paramToken2.getText());
/*    */     }
/*    */     else
/* 57 */       this.grammar.antlrTool.error("Invalid element option:" + paramToken1.getText(), this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 63 */     String str = " ";
/* 64 */     if (this.label != null) str = str + this.label + ":";
/* 65 */     if (this.not) str = str + "~";
/* 66 */     return str + this.atomText;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.GrammarAtom
 * JD-Core Version:    0.6.2
 */