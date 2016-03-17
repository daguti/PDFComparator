/*    */ package org.antlr.gunit.swingui.model;
/*    */ 
/*    */ public class TestCaseInputFile
/*    */   implements ITestCaseInput
/*    */ {
/*    */   private String fileName;
/*    */ 
/*    */   public TestCaseInputFile(String file)
/*    */   {
/* 43 */     this.fileName = file;
/*    */   }
/*    */ 
/*    */   public String getLabel() {
/* 47 */     return "FILE:" + this.fileName;
/*    */   }
/*    */ 
/*    */   public void setScript(String script) {
/* 51 */     this.fileName = script;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 56 */     return this.fileName;
/*    */   }
/*    */ 
/*    */   public String getScript() {
/* 60 */     return this.fileName;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.swingui.model.TestCaseInputFile
 * JD-Core Version:    0.6.2
 */