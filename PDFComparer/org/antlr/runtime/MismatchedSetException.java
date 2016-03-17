/*    */ package org.antlr.runtime;
/*    */ 
/*    */ public class MismatchedSetException extends RecognitionException
/*    */ {
/*    */   public BitSet expecting;
/*    */ 
/*    */   public MismatchedSetException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public MismatchedSetException(BitSet expecting, IntStream input)
/*    */   {
/* 37 */     super(input);
/* 38 */     this.expecting = expecting;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 42 */     return "MismatchedSetException(" + getUnexpectedType() + "!=" + this.expecting + ")";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.MismatchedSetException
 * JD-Core Version:    0.6.2
 */