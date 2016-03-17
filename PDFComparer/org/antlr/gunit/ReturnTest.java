/*    */ package org.antlr.gunit;
/*    */ 
/*    */ import org.antlr.runtime.Token;
/*    */ 
/*    */ public class ReturnTest extends AbstractTest
/*    */ {
/*    */   private final Token retval;
/*    */ 
/*    */   public ReturnTest(Token retval)
/*    */   {
/* 36 */     this.retval = retval;
/*    */   }
/*    */ 
/*    */   public String getText()
/*    */   {
/* 41 */     return this.retval.getText();
/*    */   }
/*    */ 
/*    */   public int getType()
/*    */   {
/* 46 */     return this.retval.getType();
/*    */   }
/*    */ 
/*    */   public String getResult(gUnitTestResult testResult)
/*    */   {
/* 52 */     if (testResult.isSuccess()) return testResult.getReturned();
/*    */ 
/* 54 */     this.hasErrorMsg = true;
/* 55 */     return testResult.getError();
/*    */   }
/*    */ 
/*    */   public String getExpected()
/*    */   {
/* 61 */     String expect = this.retval.getText();
/*    */ 
/* 63 */     if ((expect.charAt(0) == '"') && (expect.charAt(expect.length() - 1) == '"')) {
/* 64 */       expect = expect.substring(1, expect.length() - 1);
/*    */     }
/*    */ 
/* 67 */     return expect;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.ReturnTest
 * JD-Core Version:    0.6.2
 */