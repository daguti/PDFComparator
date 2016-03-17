/*    */ package antlr;
/*    */ 
/*    */ import antlr.collections.AST;
/*    */ 
/*    */ public abstract class ParseTree extends BaseAST
/*    */ {
/*    */   public String getLeftmostDerivationStep(int paramInt)
/*    */   {
/* 18 */     if (paramInt <= 0) {
/* 19 */       return toString();
/*    */     }
/* 21 */     StringBuffer localStringBuffer = new StringBuffer(2000);
/* 22 */     getLeftmostDerivation(localStringBuffer, paramInt);
/* 23 */     return localStringBuffer.toString();
/*    */   }
/*    */ 
/*    */   public String getLeftmostDerivation(int paramInt) {
/* 27 */     StringBuffer localStringBuffer = new StringBuffer(2000);
/* 28 */     localStringBuffer.append("    " + toString());
/* 29 */     localStringBuffer.append("\n");
/* 30 */     for (int i = 1; i < paramInt; i++) {
/* 31 */       localStringBuffer.append(" =>");
/* 32 */       localStringBuffer.append(getLeftmostDerivationStep(i));
/* 33 */       localStringBuffer.append("\n");
/*    */     }
/* 35 */     return localStringBuffer.toString();
/*    */   }
/*    */ 
/*    */   protected abstract int getLeftmostDerivation(StringBuffer paramStringBuffer, int paramInt);
/*    */ 
/*    */   public void initialize(int paramInt, String paramString)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void initialize(AST paramAST)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void initialize(Token paramToken)
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.ParseTree
 * JD-Core Version:    0.6.2
 */