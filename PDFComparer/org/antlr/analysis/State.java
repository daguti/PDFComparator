/*    */ package org.antlr.analysis;
/*    */ 
/*    */ public abstract class State
/*    */ {
/*    */   public static final int INVALID_STATE_NUMBER = -1;
/* 34 */   public int stateNumber = -1;
/*    */ 
/* 39 */   protected boolean acceptState = false;
/*    */ 
/*    */   public abstract int getNumberOfTransitions();
/*    */ 
/*    */   public abstract void addTransition(Transition paramTransition);
/*    */ 
/*    */   public abstract Transition transition(int paramInt);
/*    */ 
/*    */   public boolean isAcceptState() {
/* 48 */     return this.acceptState;
/*    */   }
/*    */ 
/*    */   public void setAcceptState(boolean acceptState) {
/* 52 */     this.acceptState = acceptState;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.analysis.State
 * JD-Core Version:    0.6.2
 */