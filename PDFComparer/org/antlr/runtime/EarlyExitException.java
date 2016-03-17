/*    */ package org.antlr.runtime;
/*    */ 
/*    */ public class EarlyExitException extends RecognitionException
/*    */ {
/*    */   public int decisionNumber;
/*    */ 
/*    */   public EarlyExitException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public EarlyExitException(int decisionNumber, IntStream input)
/*    */   {
/* 38 */     super(input);
/* 39 */     this.decisionNumber = decisionNumber;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.EarlyExitException
 * JD-Core Version:    0.6.2
 */