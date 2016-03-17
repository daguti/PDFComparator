/*    */ package org.antlr.runtime.tree;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.antlr.runtime.Token;
/*    */ 
/*    */ public abstract interface Tree
/*    */ {
/* 45 */   public static final Tree INVALID_NODE = new CommonTree(Token.INVALID_TOKEN);
/*    */ 
/*    */   public abstract Tree getChild(int paramInt);
/*    */ 
/*    */   public abstract int getChildCount();
/*    */ 
/*    */   public abstract Tree getParent();
/*    */ 
/*    */   public abstract void setParent(Tree paramTree);
/*    */ 
/*    */   public abstract boolean hasAncestor(int paramInt);
/*    */ 
/*    */   public abstract Tree getAncestor(int paramInt);
/*    */ 
/*    */   public abstract List getAncestors();
/*    */ 
/*    */   public abstract int getChildIndex();
/*    */ 
/*    */   public abstract void setChildIndex(int paramInt);
/*    */ 
/*    */   public abstract void freshenParentAndChildIndexes();
/*    */ 
/*    */   public abstract void addChild(Tree paramTree);
/*    */ 
/*    */   public abstract void setChild(int paramInt, Tree paramTree);
/*    */ 
/*    */   public abstract Object deleteChild(int paramInt);
/*    */ 
/*    */   public abstract void replaceChildren(int paramInt1, int paramInt2, Object paramObject);
/*    */ 
/*    */   public abstract boolean isNil();
/*    */ 
/*    */   public abstract int getTokenStartIndex();
/*    */ 
/*    */   public abstract void setTokenStartIndex(int paramInt);
/*    */ 
/*    */   public abstract int getTokenStopIndex();
/*    */ 
/*    */   public abstract void setTokenStopIndex(int paramInt);
/*    */ 
/*    */   public abstract Tree dupNode();
/*    */ 
/*    */   public abstract int getType();
/*    */ 
/*    */   public abstract String getText();
/*    */ 
/*    */   public abstract int getLine();
/*    */ 
/*    */   public abstract int getCharPositionInLine();
/*    */ 
/*    */   public abstract String toStringTree();
/*    */ 
/*    */   public abstract String toString();
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.tree.Tree
 * JD-Core Version:    0.6.2
 */