/*    */ package org.antlr.runtime;
/*    */ 
/*    */ public class MismatchedRangeException extends RecognitionException
/*    */ {
/*    */   public int a;
/*    */   public int b;
/*    */ 
/*    */   public MismatchedRangeException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public MismatchedRangeException(int a, int b, IntStream input)
/*    */   {
/* 37 */     super(input);
/* 38 */     this.a = a;
/* 39 */     this.b = b;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 43 */     return "MismatchedNotSetException(" + getUnexpectedType() + " not in [" + this.a + "," + this.b + "])";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.MismatchedRangeException
 * JD-Core Version:    0.6.2
 */