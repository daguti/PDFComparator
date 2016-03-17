/*    */ package org.antlr.gunit.swingui.model;
/*    */ 
/*    */ public class TestCase
/*    */ {
/*    */   private ITestCaseInput input;
/*    */   private ITestCaseOutput output;
/*    */   private boolean pass;
/*    */ 
/*    */   public boolean isPass()
/*    */   {
/* 37 */     return this.pass;
/*    */   }
/*    */ 
/*    */   public void setPass(boolean value) {
/* 41 */     this.pass = value;
/*    */   }
/*    */ 
/*    */   public ITestCaseInput getInput() {
/* 45 */     return this.input;
/*    */   }
/*    */ 
/*    */   public ITestCaseOutput getOutput() {
/* 49 */     return this.output;
/*    */   }
/*    */ 
/*    */   public TestCase(ITestCaseInput input, ITestCaseOutput output) {
/* 53 */     this.input = input;
/* 54 */     this.output = output;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 59 */     return String.format("[%s]->[%s]", new Object[] { this.input.getScript(), this.output.getScript() });
/*    */   }
/*    */ 
/*    */   public void setInput(ITestCaseInput in) {
/* 63 */     this.input = in;
/*    */   }
/*    */ 
/*    */   public void setOutput(ITestCaseOutput out) {
/* 67 */     this.output = out;
/*    */   }
/*    */ 
/*    */   public static String convertPreservedChars(String input)
/*    */   {
/* 72 */     return input;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.swingui.model.TestCase
 * JD-Core Version:    0.6.2
 */