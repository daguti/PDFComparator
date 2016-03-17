/*    */ package antlr;
/*    */ 
/*    */ import antlr.collections.impl.BitSet;
/*    */ 
/*    */ class CharRangeElement extends AlternativeElement
/*    */ {
/*    */   String label;
/* 12 */   protected char begin = '\000';
/* 13 */   protected char end = '\000';
/*    */   protected String beginText;
/*    */   protected String endText;
/*    */ 
/*    */   public CharRangeElement(LexerGrammar paramLexerGrammar, Token paramToken1, Token paramToken2, int paramInt)
/*    */   {
/* 19 */     super(paramLexerGrammar);
/* 20 */     this.begin = ((char)ANTLRLexer.tokenTypeForCharLiteral(paramToken1.getText()));
/* 21 */     this.beginText = paramToken1.getText();
/* 22 */     this.end = ((char)ANTLRLexer.tokenTypeForCharLiteral(paramToken2.getText()));
/* 23 */     this.endText = paramToken2.getText();
/* 24 */     this.line = paramToken1.getLine();
/*    */ 
/* 26 */     for (int i = this.begin; i <= this.end; i++) {
/* 27 */       paramLexerGrammar.charVocabulary.add(i);
/*    */     }
/* 29 */     this.autoGenType = paramInt;
/*    */   }
/*    */ 
/*    */   public void generate() {
/* 33 */     this.grammar.generator.gen(this);
/*    */   }
/*    */ 
/*    */   public String getLabel() {
/* 37 */     return this.label;
/*    */   }
/*    */ 
/*    */   public Lookahead look(int paramInt) {
/* 41 */     return this.grammar.theLLkAnalyzer.look(paramInt, this);
/*    */   }
/*    */ 
/*    */   public void setLabel(String paramString) {
/* 45 */     this.label = paramString;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 49 */     if (this.label != null) {
/* 50 */       return " " + this.label + ":" + this.beginText + ".." + this.endText;
/*    */     }
/* 52 */     return " " + this.beginText + ".." + this.endText;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.CharRangeElement
 * JD-Core Version:    0.6.2
 */