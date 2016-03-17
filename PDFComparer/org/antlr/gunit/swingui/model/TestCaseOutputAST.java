/*    */ package org.antlr.gunit.swingui.model;
/*    */ 
/*    */ public class TestCaseOutputAST
/*    */   implements ITestCaseOutput
/*    */ {
/*    */   private String treeString;
/*    */ 
/*    */   public TestCaseOutputAST(String script)
/*    */   {
/* 40 */     this.treeString = script;
/*    */   }
/*    */ 
/*    */   public void setScript(String script) {
/* 44 */     this.treeString = script;
/*    */   }
/*    */ 
/*    */   public String getScript() {
/* 48 */     return this.treeString;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 54 */     return String.format(" -> %s", new Object[] { this.treeString });
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.swingui.model.TestCaseOutputAST
 * JD-Core Version:    0.6.2
 */