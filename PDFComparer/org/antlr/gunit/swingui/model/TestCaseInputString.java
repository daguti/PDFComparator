/*    */ package org.antlr.gunit.swingui.model;
/*    */ 
/*    */ public class TestCaseInputString
/*    */   implements ITestCaseInput
/*    */ {
/*    */   private String script;
/*    */ 
/*    */   public TestCaseInputString(String text)
/*    */   {
/* 40 */     this.script = text;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 45 */     return '"' + TestCase.convertPreservedChars(this.script) + '"';
/*    */   }
/*    */ 
/*    */   public void setScript(String script)
/*    */   {
/* 51 */     this.script = script;
/*    */   }
/*    */ 
/*    */   public String getScript() {
/* 55 */     return this.script;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.swingui.model.TestCaseInputString
 * JD-Core Version:    0.6.2
 */