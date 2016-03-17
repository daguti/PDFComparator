/*    */ package antlr;
/*    */ 
/*    */ import antlr.collections.impl.BitSet;
/*    */ 
/*    */ class StringLiteralElement extends GrammarAtom
/*    */ {
/*    */   protected String processedAtomText;
/*    */ 
/*    */   public StringLiteralElement(Grammar paramGrammar, Token paramToken, int paramInt)
/*    */   {
/* 16 */     super(paramGrammar, paramToken, paramInt);
/* 17 */     if (!(paramGrammar instanceof LexerGrammar))
/*    */     {
/* 19 */       TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol(this.atomText);
/* 20 */       if (localTokenSymbol == null) {
/* 21 */         paramGrammar.antlrTool.error("Undefined literal: " + this.atomText, this.grammar.getFilename(), paramToken.getLine(), paramToken.getColumn());
/*    */       }
/*    */       else {
/* 24 */         this.tokenType = localTokenSymbol.getTokenType();
/*    */       }
/*    */     }
/* 27 */     this.line = paramToken.getLine();
/*    */ 
/* 31 */     this.processedAtomText = new String();
/* 32 */     for (int i = 1; i < this.atomText.length() - 1; i++) {
/* 33 */       char c = this.atomText.charAt(i);
/* 34 */       if ((c == '\\') && 
/* 35 */         (i + 1 < this.atomText.length() - 1)) {
/* 36 */         i++;
/* 37 */         c = this.atomText.charAt(i);
/* 38 */         switch (c) {
/*    */         case 'n':
/* 40 */           c = '\n';
/* 41 */           break;
/*    */         case 'r':
/* 43 */           c = '\r';
/* 44 */           break;
/*    */         case 't':
/* 46 */           c = '\t';
/*    */         }
/*    */ 
/*    */       }
/*    */ 
/* 51 */       if ((paramGrammar instanceof LexerGrammar)) {
/* 52 */         ((LexerGrammar)paramGrammar).charVocabulary.add(c);
/*    */       }
/* 54 */       this.processedAtomText += c;
/*    */     }
/*    */   }
/*    */ 
/*    */   public void generate() {
/* 59 */     this.grammar.generator.gen(this);
/*    */   }
/*    */ 
/*    */   public Lookahead look(int paramInt) {
/* 63 */     return this.grammar.theLLkAnalyzer.look(paramInt, this);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.StringLiteralElement
 * JD-Core Version:    0.6.2
 */