/*    */ package antlr;
/*    */ 
/*    */ import antlr.collections.impl.Vector;
/*    */ 
/*    */ class TreeElement extends AlternativeBlock
/*    */ {
/*    */   GrammarAtom root;
/*    */ 
/*    */   public TreeElement(Grammar paramGrammar, Token paramToken)
/*    */   {
/* 15 */     super(paramGrammar, paramToken, false);
/*    */   }
/*    */ 
/*    */   public void generate() {
/* 19 */     this.grammar.generator.gen(this);
/*    */   }
/*    */ 
/*    */   public Lookahead look(int paramInt) {
/* 23 */     return this.grammar.theLLkAnalyzer.look(paramInt, this);
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 27 */     String str = " #(" + this.root;
/* 28 */     Alternative localAlternative = (Alternative)this.alternatives.elementAt(0);
/* 29 */     AlternativeElement localAlternativeElement = localAlternative.head;
/* 30 */     while (localAlternativeElement != null) {
/* 31 */       str = str + localAlternativeElement;
/* 32 */       localAlternativeElement = localAlternativeElement.next;
/*    */     }
/* 34 */     return str + " )";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.TreeElement
 * JD-Core Version:    0.6.2
 */