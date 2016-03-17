/*    */ package org.antlr.runtime;
/*    */ 
/*    */ public class UnwantedTokenException extends MismatchedTokenException
/*    */ {
/*    */   public UnwantedTokenException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public UnwantedTokenException(int expecting, IntStream input)
/*    */   {
/* 36 */     super(expecting, input);
/*    */   }
/*    */ 
/*    */   public Token getUnexpectedToken() {
/* 40 */     return this.token;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 44 */     String exp = ", expected " + this.expecting;
/* 45 */     if (this.expecting == 0) {
/* 46 */       exp = "";
/*    */     }
/* 48 */     if (this.token == null) {
/* 49 */       return "UnwantedTokenException(found=" + null + exp + ")";
/*    */     }
/* 51 */     return "UnwantedTokenException(found=" + this.token.getText() + exp + ")";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.UnwantedTokenException
 * JD-Core Version:    0.6.2
 */