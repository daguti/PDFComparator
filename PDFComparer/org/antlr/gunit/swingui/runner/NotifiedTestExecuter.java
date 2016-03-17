/*    */ package org.antlr.gunit.swingui.runner;
/*    */ 
/*    */ import org.antlr.gunit.GrammarInfo;
/*    */ import org.antlr.gunit.ITestCase;
/*    */ import org.antlr.gunit.gUnitExecutor;
/*    */ import org.antlr.gunit.swingui.model.Rule;
/*    */ import org.antlr.gunit.swingui.model.TestCase;
/*    */ import org.antlr.gunit.swingui.model.TestSuite;
/*    */ 
/*    */ public class NotifiedTestExecuter extends gUnitExecutor
/*    */ {
/*    */   private TestSuite testSuite;
/*    */ 
/*    */   public NotifiedTestExecuter(GrammarInfo grammarInfo, ClassLoader loader, String testsuiteDir, TestSuite suite)
/*    */   {
/* 43 */     super(grammarInfo, loader, testsuiteDir);
/*    */ 
/* 45 */     this.testSuite = suite;
/*    */   }
/*    */ 
/*    */   public void onFail(ITestCase failTest)
/*    */   {
/* 50 */     if (failTest == null) throw new IllegalArgumentException("Null fail test");
/*    */ 
/* 52 */     String ruleName = failTest.getTestedRuleName();
/* 53 */     if (ruleName == null) throw new NullPointerException("Null rule name");
/*    */ 
/* 55 */     Rule rule = this.testSuite.getRule(ruleName);
/* 56 */     TestCase failCase = (TestCase)rule.getElementAt(failTest.getTestCaseIndex());
/* 57 */     failCase.setPass(false);
/*    */   }
/*    */ 
/*    */   public void onPass(ITestCase passTest)
/*    */   {
/* 63 */     if (passTest == null) throw new IllegalArgumentException("Null pass test");
/*    */ 
/* 65 */     String ruleName = passTest.getTestedRuleName();
/* 66 */     if (ruleName == null) throw new NullPointerException("Null rule name");
/*    */ 
/* 68 */     Rule rule = this.testSuite.getRule(ruleName);
/* 69 */     TestCase passCase = (TestCase)rule.getElementAt(passTest.getTestCaseIndex());
/* 70 */     passCase.setPass(true);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.swingui.runner.NotifiedTestExecuter
 * JD-Core Version:    0.6.2
 */