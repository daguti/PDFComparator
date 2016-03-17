/*    */ package antlr;
/*    */ 
/*    */ import antlr.collections.AST;
/*    */ 
/*    */ public class ASTPair
/*    */ {
/*    */   public AST root;
/*    */   public AST child;
/*    */ 
/*    */   public final void advanceChildToEnd()
/*    */   {
/* 23 */     if (this.child != null)
/* 24 */       while (this.child.getNextSibling() != null)
/* 25 */         this.child = this.child.getNextSibling();
/*    */   }
/*    */ 
/*    */   public ASTPair copy()
/*    */   {
/* 32 */     ASTPair localASTPair = new ASTPair();
/* 33 */     localASTPair.root = this.root;
/* 34 */     localASTPair.child = this.child;
/* 35 */     return localASTPair;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 39 */     String str1 = this.root == null ? "null" : this.root.getText();
/* 40 */     String str2 = this.child == null ? "null" : this.child.getText();
/* 41 */     return "[" + str1 + "," + str2 + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.ASTPair
 * JD-Core Version:    0.6.2
 */