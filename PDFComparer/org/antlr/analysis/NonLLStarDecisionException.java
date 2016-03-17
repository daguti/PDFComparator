/*    */ package org.antlr.analysis;
/*    */ 
/*    */ public class NonLLStarDecisionException extends RuntimeException
/*    */ {
/*    */   public DFA abortedDFA;
/*    */ 
/*    */   public NonLLStarDecisionException(DFA abortedDFA)
/*    */   {
/* 36 */     this.abortedDFA = abortedDFA;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.analysis.NonLLStarDecisionException
 * JD-Core Version:    0.6.2
 */