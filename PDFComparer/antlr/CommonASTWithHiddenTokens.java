/*    */ package antlr;
/*    */ 
/*    */ import antlr.collections.AST;
/*    */ 
/*    */ public class CommonASTWithHiddenTokens extends CommonAST
/*    */ {
/*    */   protected CommonHiddenStreamToken hiddenBefore;
/*    */   protected CommonHiddenStreamToken hiddenAfter;
/*    */ 
/*    */   public CommonASTWithHiddenTokens()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CommonASTWithHiddenTokens(Token paramToken)
/*    */   {
/* 23 */     super(paramToken);
/*    */   }
/*    */ 
/*    */   public CommonHiddenStreamToken getHiddenAfter() {
/* 27 */     return this.hiddenAfter;
/*    */   }
/*    */ 
/*    */   public CommonHiddenStreamToken getHiddenBefore() {
/* 31 */     return this.hiddenBefore;
/*    */   }
/*    */ 
/*    */   public void initialize(AST paramAST)
/*    */   {
/* 36 */     this.hiddenBefore = ((CommonASTWithHiddenTokens)paramAST).getHiddenBefore();
/* 37 */     this.hiddenAfter = ((CommonASTWithHiddenTokens)paramAST).getHiddenAfter();
/* 38 */     super.initialize(paramAST);
/*    */   }
/*    */ 
/*    */   public void initialize(Token paramToken) {
/* 42 */     CommonHiddenStreamToken localCommonHiddenStreamToken = (CommonHiddenStreamToken)paramToken;
/* 43 */     super.initialize(localCommonHiddenStreamToken);
/* 44 */     this.hiddenBefore = localCommonHiddenStreamToken.getHiddenBefore();
/* 45 */     this.hiddenAfter = localCommonHiddenStreamToken.getHiddenAfter();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.CommonASTWithHiddenTokens
 * JD-Core Version:    0.6.2
 */