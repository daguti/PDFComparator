/*    */ package org.antlr.gunit.swingui.model;
/*    */ 
/*    */ public class TestCaseOutputResult
/*    */   implements ITestCaseOutput
/*    */ {
/* 37 */   public static String OK = "OK";
/* 38 */   public static String FAIL = "FAIL";
/*    */   private boolean success;
/*    */ 
/*    */   public TestCaseOutputResult(boolean result)
/*    */   {
/* 43 */     this.success = result;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 48 */     return getScript();
/*    */   }
/*    */ 
/*    */   public String getScript() {
/* 52 */     return this.success ? OK : FAIL;
/*    */   }
/*    */ 
/*    */   public void setScript(boolean value) {
/* 56 */     this.success = value;
/*    */   }
/*    */ 
/*    */   public void setScript(String script) {
/* 60 */     this.success = Boolean.parseBoolean(script);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.swingui.model.TestCaseOutputResult
 * JD-Core Version:    0.6.2
 */