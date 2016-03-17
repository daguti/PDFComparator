/*    */ package org.antlr.runtime;
/*    */ 
/*    */ public class FailedPredicateException extends RecognitionException
/*    */ {
/*    */   public String ruleName;
/*    */   public String predicateText;
/*    */ 
/*    */   public FailedPredicateException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public FailedPredicateException(IntStream input, String ruleName, String predicateText)
/*    */   {
/* 46 */     super(input);
/* 47 */     this.ruleName = ruleName;
/* 48 */     this.predicateText = predicateText;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 52 */     return "FailedPredicateException(" + this.ruleName + ",{" + this.predicateText + "}?)";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.FailedPredicateException
 * JD-Core Version:    0.6.2
 */