/*    */ package org.antlr.gunit.swingui.model;
/*    */ 
/*    */ public class TestCaseOutputReturn
/*    */   implements ITestCaseOutput
/*    */ {
/*    */   private String script;
/*    */ 
/*    */   public TestCaseOutputReturn(String text)
/*    */   {
/* 34 */     this.script = text;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 39 */     return String.format(" returns [%s]", new Object[] { this.script });
/*    */   }
/*    */ 
/*    */   public void setScript(String script) {
/* 43 */     this.script = script;
/*    */   }
/*    */ 
/*    */   public String getScript() {
/* 47 */     return this.script;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.swingui.model.TestCaseOutputReturn
 * JD-Core Version:    0.6.2
 */