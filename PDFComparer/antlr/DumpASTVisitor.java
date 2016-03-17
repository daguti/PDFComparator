/*    */ package antlr;
/*    */ 
/*    */ import antlr.collections.AST;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class DumpASTVisitor
/*    */   implements ASTVisitor
/*    */ {
/* 16 */   protected int level = 0;
/*    */ 
/*    */   private void tabs()
/*    */   {
/* 20 */     for (int i = 0; i < this.level; i++)
/* 21 */       System.out.print("   ");
/*    */   }
/*    */ 
/*    */   public void visit(AST paramAST)
/*    */   {
/* 27 */     int i = 0;
/*    */ 
/* 29 */     for (AST localAST = paramAST; localAST != null; localAST = localAST.getNextSibling()) {
/* 30 */       if (localAST.getFirstChild() != null) {
/* 31 */         i = 0;
/* 32 */         break;
/*    */       }
/*    */     }
/*    */ 
/* 36 */     for (localAST = paramAST; localAST != null; localAST = localAST.getNextSibling()) {
/* 37 */       if ((i == 0) || (localAST == paramAST)) {
/* 38 */         tabs();
/*    */       }
/* 40 */       if (localAST.getText() == null) {
/* 41 */         System.out.print("nil");
/*    */       }
/*    */       else {
/* 44 */         System.out.print(localAST.getText());
/*    */       }
/*    */ 
/* 47 */       System.out.print(" [" + localAST.getType() + "] ");
/*    */ 
/* 49 */       if (i != 0) {
/* 50 */         System.out.print(" ");
/*    */       }
/*    */       else {
/* 53 */         System.out.println("");
/*    */       }
/*    */ 
/* 56 */       if (localAST.getFirstChild() != null) {
/* 57 */         this.level += 1;
/* 58 */         visit(localAST.getFirstChild());
/* 59 */         this.level -= 1;
/*    */       }
/*    */     }
/*    */ 
/* 63 */     if (i != 0)
/* 64 */       System.out.println("");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.DumpASTVisitor
 * JD-Core Version:    0.6.2
 */