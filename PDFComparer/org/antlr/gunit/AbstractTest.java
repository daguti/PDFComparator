/*    */ package org.antlr.gunit;
/*    */ 
/*    */ public abstract class AbstractTest
/*    */   implements ITestCase
/*    */ {
/*    */   protected String header;
/*    */   protected String actual;
/*    */   protected boolean hasErrorMsg;
/*    */   private String testedRuleName;
/*    */   private int testCaseIndex;
/*    */ 
/*    */   public abstract int getType();
/*    */ 
/*    */   public abstract String getText();
/*    */ 
/*    */   public abstract String getExpected();
/*    */ 
/*    */   public String getExpectedResult()
/*    */   {
/* 48 */     String expected = getExpected();
/* 49 */     if (expected != null) expected = JUnitCodeGen.escapeForJava(expected);
/* 50 */     return expected; } 
/*    */   public abstract String getResult(gUnitTestResult paramgUnitTestResult);
/*    */ 
/* 53 */   public String getHeader() { return this.header; } 
/* 54 */   public String getActual() { return this.actual; }
/*    */ 
/*    */   public String getActualResult() {
/* 57 */     String actual = getActual();
/*    */ 
/* 59 */     if ((actual != null) && (!this.hasErrorMsg)) actual = JUnitCodeGen.escapeForJava(actual);
/* 60 */     return actual;
/*    */   }
/*    */   public String getTestedRuleName() {
/* 63 */     return this.testedRuleName; } 
/* 64 */   public int getTestCaseIndex() { return this.testCaseIndex; }
/*    */ 
/*    */   public void setHeader(String rule, String lexicalRule, String treeRule, int numOfTest, int line) {
/* 67 */     StringBuffer buf = new StringBuffer();
/* 68 */     buf.append("test" + numOfTest + " (");
/* 69 */     if (treeRule != null) {
/* 70 */       buf.append(treeRule + " walks ");
/*    */     }
/* 72 */     if (lexicalRule != null)
/* 73 */       buf.append(lexicalRule + ", line" + line + ")" + " - ");
/*    */     else
/* 75 */       buf.append(rule + ", line" + line + ")" + " - ");
/* 76 */     this.header = buf.toString();
/*    */   }
/* 78 */   public void setActual(String actual) { this.actual = actual; } 
/*    */   public void setTestedRuleName(String testedRuleName) {
/* 80 */     this.testedRuleName = testedRuleName; } 
/* 81 */   public void setTestCaseIndex(int testCaseIndex) { this.testCaseIndex = testCaseIndex; }
/*    */ 
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.AbstractTest
 * JD-Core Version:    0.6.2
 */