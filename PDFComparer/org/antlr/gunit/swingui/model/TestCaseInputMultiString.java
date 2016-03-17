/*    */ package org.antlr.gunit.swingui.model;
/*    */ 
/*    */ public class TestCaseInputMultiString
/*    */   implements ITestCaseInput
/*    */ {
/*    */   private String script;
/*    */ 
/*    */   public TestCaseInputMultiString(String text)
/*    */   {
/* 41 */     this.script = text;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 46 */     return "<<" + TestCase.convertPreservedChars(this.script) + ">>";
/*    */   }
/*    */ 
/*    */   public void setScript(String script) {
/* 50 */     this.script = script;
/*    */   }
/*    */ 
/*    */   public String getScript() {
/* 54 */     return this.script;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.swingui.model.TestCaseInputMultiString
 * JD-Core Version:    0.6.2
 */