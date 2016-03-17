/*    */ package org.antlr.gunit;
/*    */ 
/*    */ public class BooleanTest extends AbstractTest
/*    */ {
/*    */   private boolean ok;
/*    */ 
/*    */   public BooleanTest(boolean ok)
/*    */   {
/* 34 */     this.ok = ok;
/*    */   }
/*    */ 
/*    */   public String getText()
/*    */   {
/* 39 */     return this.ok ? "OK" : "FAIL";
/*    */   }
/*    */ 
/*    */   public int getType()
/*    */   {
/* 44 */     return this.ok ? 4 : 5;
/*    */   }
/*    */ 
/*    */   public String getResult(gUnitTestResult testResult)
/*    */   {
/* 49 */     if (testResult.isLexerTest()) {
/* 50 */       if (testResult.isSuccess()) return "OK";
/*    */ 
/* 52 */       this.hasErrorMsg = true;
/* 53 */       return testResult.getError();
/*    */     }
/*    */ 
/* 56 */     return testResult.isSuccess() ? "OK" : "FAIL";
/*    */   }
/*    */ 
/*    */   public String getExpected()
/*    */   {
/* 61 */     return this.ok ? "OK" : "FAIL";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.BooleanTest
 * JD-Core Version:    0.6.2
 */