/*    */ package antlr;
/*    */ 
/*    */ import antlr.collections.impl.BitSet;
/*    */ 
/*    */ class CharLiteralElement extends GrammarAtom
/*    */ {
/*    */   public CharLiteralElement(LexerGrammar paramLexerGrammar, Token paramToken, boolean paramBoolean, int paramInt)
/*    */   {
/* 14 */     super(paramLexerGrammar, paramToken, 1);
/* 15 */     this.tokenType = ANTLRLexer.tokenTypeForCharLiteral(paramToken.getText());
/* 16 */     paramLexerGrammar.charVocabulary.add(this.tokenType);
/* 17 */     this.line = paramToken.getLine();
/* 18 */     this.not = paramBoolean;
/* 19 */     this.autoGenType = paramInt;
/*    */   }
/*    */ 
/*    */   public void generate() {
/* 23 */     this.grammar.generator.gen(this);
/*    */   }
/*    */ 
/*    */   public Lookahead look(int paramInt) {
/* 27 */     return this.grammar.theLLkAnalyzer.look(paramInt, this);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.CharLiteralElement
 * JD-Core Version:    0.6.2
 */