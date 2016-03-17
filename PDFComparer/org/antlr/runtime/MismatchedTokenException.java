/*    */ package org.antlr.runtime;
/*    */ 
/*    */ public class MismatchedTokenException extends RecognitionException
/*    */ {
/* 32 */   public int expecting = 0;
/*    */ 
/*    */   public MismatchedTokenException() {
/*    */   }
/*    */ 
/*    */   public MismatchedTokenException(int expecting, IntStream input) {
/* 38 */     super(input);
/* 39 */     this.expecting = expecting;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 43 */     return "MismatchedTokenException(" + getUnexpectedType() + "!=" + this.expecting + ")";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.MismatchedTokenException
 * JD-Core Version:    0.6.2
 */