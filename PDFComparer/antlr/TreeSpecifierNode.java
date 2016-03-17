/*    */ package antlr;
/*    */ 
/*    */ class TreeSpecifierNode
/*    */ {
/* 11 */   private TreeSpecifierNode parent = null;
/* 12 */   private TreeSpecifierNode firstChild = null;
/* 13 */   private TreeSpecifierNode nextSibling = null;
/*    */   private Token tok;
/*    */ 
/*    */   TreeSpecifierNode(Token paramToken)
/*    */   {
/* 18 */     this.tok = paramToken;
/*    */   }
/*    */ 
/*    */   public TreeSpecifierNode getFirstChild() {
/* 22 */     return this.firstChild;
/*    */   }
/*    */ 
/*    */   public TreeSpecifierNode getNextSibling() {
/* 26 */     return this.nextSibling;
/*    */   }
/*    */ 
/*    */   public TreeSpecifierNode getParent()
/*    */   {
/* 31 */     return this.parent;
/*    */   }
/*    */ 
/*    */   public Token getToken() {
/* 35 */     return this.tok;
/*    */   }
/*    */ 
/*    */   public void setFirstChild(TreeSpecifierNode paramTreeSpecifierNode) {
/* 39 */     this.firstChild = paramTreeSpecifierNode;
/* 40 */     paramTreeSpecifierNode.parent = this;
/*    */   }
/*    */ 
/*    */   public void setNextSibling(TreeSpecifierNode paramTreeSpecifierNode)
/*    */   {
/* 45 */     this.nextSibling = paramTreeSpecifierNode;
/* 46 */     paramTreeSpecifierNode.parent = this.parent;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.TreeSpecifierNode
 * JD-Core Version:    0.6.2
 */