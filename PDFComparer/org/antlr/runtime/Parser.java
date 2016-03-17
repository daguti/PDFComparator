/*    */ package org.antlr.runtime;
/*    */ 
/*    */ public class Parser extends BaseRecognizer
/*    */ {
/*    */   public TokenStream input;
/*    */ 
/*    */   public Parser(TokenStream input)
/*    */   {
/* 38 */     setTokenStream(input);
/*    */   }
/*    */ 
/*    */   public Parser(TokenStream input, RecognizerSharedState state) {
/* 42 */     super(state);
/* 43 */     this.input = input;
/*    */   }
/*    */ 
/*    */   public void reset() {
/* 47 */     super.reset();
/* 48 */     if (this.input != null)
/* 49 */       this.input.seek(0);
/*    */   }
/*    */ 
/*    */   protected Object getCurrentInputSymbol(IntStream input)
/*    */   {
/* 54 */     return ((TokenStream)input).LT(1);
/*    */   }
/*    */ 
/*    */   protected Object getMissingSymbol(IntStream input, RecognitionException e, int expectedTokenType, BitSet follow)
/*    */   {
/* 62 */     String tokenText = null;
/* 63 */     if (expectedTokenType == -1) tokenText = "<missing EOF>"; else
/* 64 */       tokenText = "<missing " + getTokenNames()[expectedTokenType] + ">";
/* 65 */     CommonToken t = new CommonToken(expectedTokenType, tokenText);
/* 66 */     Token current = ((TokenStream)input).LT(1);
/* 67 */     if (current.getType() == -1) {
/* 68 */       current = ((TokenStream)input).LT(-1);
/*    */     }
/* 70 */     t.line = current.getLine();
/* 71 */     t.charPositionInLine = current.getCharPositionInLine();
/* 72 */     t.channel = 0;
/* 73 */     return t;
/*    */   }
/*    */ 
/*    */   public void setTokenStream(TokenStream input)
/*    */   {
/* 78 */     this.input = null;
/* 79 */     reset();
/* 80 */     this.input = input;
/*    */   }
/*    */ 
/*    */   public TokenStream getTokenStream() {
/* 84 */     return this.input;
/*    */   }
/*    */ 
/*    */   public String getSourceName() {
/* 88 */     return this.input.getSourceName();
/*    */   }
/*    */ 
/*    */   public void traceIn(String ruleName, int ruleIndex) {
/* 92 */     super.traceIn(ruleName, ruleIndex, this.input.LT(1));
/*    */   }
/*    */ 
/*    */   public void traceOut(String ruleName, int ruleIndex) {
/* 96 */     super.traceOut(ruleName, ruleIndex, this.input.LT(1));
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.Parser
 * JD-Core Version:    0.6.2
 */