/*    */ package org.antlr.analysis;
/*    */ 
/*    */ public class AnalysisRecursionOverflowException extends RuntimeException
/*    */ {
/*    */   public DFAState ovfState;
/*    */   public NFAConfiguration proposedNFAConfiguration;
/*    */ 
/*    */   public AnalysisRecursionOverflowException(DFAState ovfState, NFAConfiguration proposedNFAConfiguration)
/*    */   {
/* 37 */     this.ovfState = ovfState;
/* 38 */     this.proposedNFAConfiguration = proposedNFAConfiguration;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.analysis.AnalysisRecursionOverflowException
 * JD-Core Version:    0.6.2
 */