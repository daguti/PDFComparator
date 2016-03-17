/*    */ package org.antlr.runtime;
/*    */ 
/*    */ import org.antlr.runtime.tree.TreeNodeStream;
/*    */ 
/*    */ public class MismatchedTreeNodeException extends RecognitionException
/*    */ {
/*    */   public int expecting;
/*    */ 
/*    */   public MismatchedTreeNodeException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public MismatchedTreeNodeException(int expecting, TreeNodeStream input)
/*    */   {
/* 42 */     super(input);
/* 43 */     this.expecting = expecting;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 47 */     return "MismatchedTreeNodeException(" + getUnexpectedType() + "!=" + this.expecting + ")";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.MismatchedTreeNodeException
 * JD-Core Version:    0.6.2
 */