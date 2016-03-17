/*    */ package antlr.collections.impl;
/*    */ 
/*    */ import antlr.collections.AST;
/*    */ 
/*    */ public class ASTArray
/*    */ {
/* 18 */   public int size = 0;
/*    */   public AST[] array;
/*    */ 
/*    */   public ASTArray(int paramInt)
/*    */   {
/* 23 */     this.array = new AST[paramInt];
/*    */   }
/*    */ 
/*    */   public ASTArray add(AST paramAST) {
/* 27 */     this.array[(this.size++)] = paramAST;
/* 28 */     return this;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.collections.impl.ASTArray
 * JD-Core Version:    0.6.2
 */