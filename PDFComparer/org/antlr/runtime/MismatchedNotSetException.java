/*    */ package org.antlr.runtime;
/*    */ 
/*    */ public class MismatchedNotSetException extends MismatchedSetException
/*    */ {
/*    */   public MismatchedNotSetException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public MismatchedNotSetException(BitSet expecting, IntStream input)
/*    */   {
/* 35 */     super(expecting, input);
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 39 */     return "MismatchedNotSetException(" + getUnexpectedType() + "!=" + this.expecting + ")";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.MismatchedNotSetException
 * JD-Core Version:    0.6.2
 */