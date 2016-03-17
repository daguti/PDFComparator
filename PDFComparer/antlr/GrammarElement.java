/*    */ package antlr;
/*    */ 
/*    */ abstract class GrammarElement
/*    */ {
/*    */   public static final int AUTO_GEN_NONE = 1;
/*    */   public static final int AUTO_GEN_CARET = 2;
/*    */   public static final int AUTO_GEN_BANG = 3;
/*    */   protected Grammar grammar;
/*    */   protected int line;
/*    */   protected int column;
/*    */ 
/*    */   public GrammarElement(Grammar paramGrammar)
/*    */   {
/* 35 */     this.grammar = paramGrammar;
/* 36 */     this.line = -1;
/* 37 */     this.column = -1;
/*    */   }
/*    */ 
/*    */   public GrammarElement(Grammar paramGrammar, Token paramToken) {
/* 41 */     this.grammar = paramGrammar;
/* 42 */     this.line = paramToken.getLine();
/* 43 */     this.column = paramToken.getColumn();
/*    */   }
/*    */ 
/*    */   public void generate() {
/*    */   }
/*    */ 
/*    */   public int getLine() {
/* 50 */     return this.line;
/*    */   }
/*    */ 
/*    */   public int getColumn() {
/* 54 */     return this.column;
/*    */   }
/*    */ 
/*    */   public Lookahead look(int paramInt) {
/* 58 */     return null;
/*    */   }
/*    */ 
/*    */   public abstract String toString();
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.GrammarElement
 * JD-Core Version:    0.6.2
 */