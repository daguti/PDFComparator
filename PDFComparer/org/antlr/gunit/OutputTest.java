/*    */ package org.antlr.gunit;
/*    */ 
/*    */ import org.antlr.runtime.Token;
/*    */ 
/*    */ public class OutputTest extends AbstractTest
/*    */ {
/*    */   private final Token token;
/*    */ 
/*    */   public OutputTest(Token token)
/*    */   {
/* 39 */     this.token = token;
/*    */   }
/*    */ 
/*    */   public String getText()
/*    */   {
/* 44 */     return this.token.getText();
/*    */   }
/*    */ 
/*    */   public int getType()
/*    */   {
/* 49 */     return this.token.getType();
/*    */   }
/*    */ 
/*    */   public String getResult(gUnitTestResult testResult)
/*    */   {
/* 56 */     if (testResult.isSuccess()) return testResult.getReturned();
/*    */ 
/* 58 */     this.hasErrorMsg = true;
/* 59 */     return testResult.getError();
/*    */   }
/*    */ 
/*    */   public String getExpected()
/*    */   {
/* 65 */     return this.token.getText();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.OutputTest
 * JD-Core Version:    0.6.2
 */