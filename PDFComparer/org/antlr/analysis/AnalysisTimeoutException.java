/*    */ package org.antlr.analysis;
/*    */ 
/*    */ public class AnalysisTimeoutException extends RuntimeException
/*    */ {
/*    */   public DFA abortedDFA;
/*    */ 
/*    */   public AnalysisTimeoutException(DFA abortedDFA)
/*    */   {
/* 34 */     this.abortedDFA = abortedDFA;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.analysis.AnalysisTimeoutException
 * JD-Core Version:    0.6.2
 */