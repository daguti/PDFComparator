/*    */ package antlr;
/*    */ 
/*    */ import antlr.collections.AST;
/*    */ 
/*    */ public class NoViableAltException extends RecognitionException
/*    */ {
/*    */   public Token token;
/*    */   public AST node;
/*    */ 
/*    */   public NoViableAltException(AST paramAST)
/*    */   {
/* 17 */     super("NoViableAlt", "<AST>", paramAST.getLine(), paramAST.getColumn());
/* 18 */     this.node = paramAST;
/*    */   }
/*    */ 
/*    */   public NoViableAltException(Token paramToken, String paramString) {
/* 22 */     super("NoViableAlt", paramString, paramToken.getLine(), paramToken.getColumn());
/* 23 */     this.token = paramToken;
/*    */   }
/*    */ 
/*    */   public String getMessage()
/*    */   {
/* 30 */     if (this.token != null) {
/* 31 */       return "unexpected token: " + this.token.getText();
/*    */     }
/*    */ 
/* 35 */     if (this.node == TreeParser.ASTNULL) {
/* 36 */       return "unexpected end of subtree";
/*    */     }
/* 38 */     return "unexpected AST node: " + this.node.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.NoViableAltException
 * JD-Core Version:    0.6.2
 */