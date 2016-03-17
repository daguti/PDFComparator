/*    */ package org.antlr.analysis;
/*    */ 
/*    */ import org.antlr.misc.Barrier;
/*    */ import org.antlr.tool.ErrorManager;
/*    */ import org.antlr.tool.Grammar;
/*    */ 
/*    */ public class NFAConversionThread
/*    */   implements Runnable
/*    */ {
/*    */   Grammar grammar;
/*    */   int i;
/*    */   int j;
/*    */   Barrier barrier;
/*    */ 
/*    */   public NFAConversionThread(Grammar grammar, Barrier barrier, int i, int j)
/*    */   {
/* 44 */     this.grammar = grammar;
/* 45 */     this.barrier = barrier;
/* 46 */     this.i = i;
/* 47 */     this.j = j;
/*    */   }
/*    */   public void run() {
/* 50 */     for (int decision = this.i; decision <= this.j; decision++) {
/* 51 */       NFAState decisionStartState = this.grammar.getDecisionNFAStartState(decision);
/* 52 */       if (decisionStartState.getNumberOfTransitions() > 1) {
/* 53 */         this.grammar.createLookaheadDFA(decision, true);
/*    */       }
/*    */     }
/*    */     try
/*    */     {
/* 58 */       this.barrier.waitForRelease();
/*    */     }
/*    */     catch (InterruptedException e) {
/* 61 */       ErrorManager.internalError("what the hell? DFA interruptus", e);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.analysis.NFAConversionThread
 * JD-Core Version:    0.6.2
 */