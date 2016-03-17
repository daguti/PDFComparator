/*    */ package org.antlr.gunit.swingui.model;
/*    */ 
/*    */ public class TestCaseOutputStdOut
/*    */   implements ITestCaseOutput
/*    */ {
/*    */   private String script;
/*    */ 
/*    */   public TestCaseOutputStdOut(String text)
/*    */   {
/* 38 */     this.script = text;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 43 */     return String.format(" -> \"%s\"", new Object[] { this.script });
/*    */   }
/*    */ 
/*    */   public void setScript(String script) {
/* 47 */     this.script = script;
/*    */   }
/*    */ 
/*    */   public String getScript() {
/* 51 */     return this.script;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.swingui.model.TestCaseOutputStdOut
 * JD-Core Version:    0.6.2
 */