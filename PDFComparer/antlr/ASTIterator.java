/*    */ package antlr;
/*    */ 
/*    */ import antlr.collections.AST;
/*    */ 
/*    */ public class ASTIterator
/*    */ {
/* 13 */   protected AST cursor = null;
/* 14 */   protected AST original = null;
/*    */ 
/*    */   public ASTIterator(AST paramAST)
/*    */   {
/* 18 */     this.original = (this.cursor = paramAST);
/*    */   }
/*    */ 
/*    */   public boolean isSubtree(AST paramAST1, AST paramAST2)
/*    */   {
/* 26 */     if (paramAST2 == null) {
/* 27 */       return true;
/*    */     }
/*    */ 
/* 31 */     if (paramAST1 == null) {
/* 32 */       if (paramAST2 != null) return false;
/* 33 */       return true;
/*    */     }
/*    */ 
/* 37 */     AST localAST = paramAST1;
/*    */ 
/* 39 */     for (; (localAST != null) && (paramAST2 != null); 
/* 39 */       paramAST2 = paramAST2.getNextSibling())
/*    */     {
/* 41 */       if (localAST.getType() != paramAST2.getType()) return false;
/*    */ 
/* 43 */       if ((localAST.getFirstChild() != null) && 
/* 44 */         (!isSubtree(localAST.getFirstChild(), paramAST2.getFirstChild()))) return false;
/* 39 */       localAST = localAST.getNextSibling();
/*    */     }
/*    */ 
/* 47 */     return true;
/*    */   }
/*    */ 
/*    */   public AST next(AST paramAST)
/*    */   {
/* 54 */     AST localAST = null;
/* 55 */     Object localObject = null;
/*    */ 
/* 57 */     if (this.cursor == null) {
/* 58 */       return null;
/*    */     }
/*    */ 
/* 62 */     for (; this.cursor != null; this.cursor = this.cursor.getNextSibling())
/*    */     {
/* 64 */       if (this.cursor.getType() == paramAST.getType())
/*    */       {
/* 66 */         if ((this.cursor.getFirstChild() != null) && 
/* 67 */           (isSubtree(this.cursor.getFirstChild(), paramAST.getFirstChild()))) {
/* 68 */           return this.cursor;
/*    */         }
/*    */       }
/*    */     }
/*    */ 
/* 73 */     return localAST;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.ASTIterator
 * JD-Core Version:    0.6.2
 */