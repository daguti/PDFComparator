/*    */ package org.antlr.analysis;
/*    */ 
/*    */ public class StateCluster
/*    */ {
/*    */   public NFAState left;
/*    */   public NFAState right;
/*    */ 
/*    */   public StateCluster(NFAState left, NFAState right)
/*    */   {
/* 38 */     this.left = left;
/* 39 */     this.right = right;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.analysis.StateCluster
 * JD-Core Version:    0.6.2
 */