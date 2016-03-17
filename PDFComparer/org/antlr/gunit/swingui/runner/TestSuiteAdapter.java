/*     */ package org.antlr.gunit.swingui.runner;
/*     */ 
/*     */ import org.antlr.gunit.swingui.model.ITestCaseInput;
/*     */ import org.antlr.gunit.swingui.model.ITestCaseOutput;
/*     */ import org.antlr.gunit.swingui.model.Rule;
/*     */ import org.antlr.gunit.swingui.model.TestCase;
/*     */ import org.antlr.gunit.swingui.model.TestCaseInputFile;
/*     */ import org.antlr.gunit.swingui.model.TestCaseInputMultiString;
/*     */ import org.antlr.gunit.swingui.model.TestCaseInputString;
/*     */ import org.antlr.gunit.swingui.model.TestCaseOutputAST;
/*     */ import org.antlr.gunit.swingui.model.TestCaseOutputResult;
/*     */ import org.antlr.gunit.swingui.model.TestCaseOutputReturn;
/*     */ import org.antlr.gunit.swingui.model.TestCaseOutputStdOut;
/*     */ import org.antlr.gunit.swingui.model.TestSuite;
/*     */ 
/*     */ public class TestSuiteAdapter
/*     */ {
/*     */   private TestSuite model;
/*     */   private Rule currentRule;
/*     */ 
/*     */   public TestSuiteAdapter(TestSuite testSuite)
/*     */   {
/*  42 */     this.model = testSuite;
/*     */   }
/*     */ 
/*     */   public void setGrammarName(String name) {
/*  46 */     this.model.setGrammarName(name);
/*     */   }
/*     */ 
/*     */   public void startRule(String name) {
/*  50 */     this.currentRule = new Rule(name);
/*     */   }
/*     */ 
/*     */   public void endRule() {
/*  54 */     this.model.addRule(this.currentRule);
/*  55 */     this.currentRule = null;
/*     */   }
/*     */ 
/*     */   public void addTestCase(ITestCaseInput in, ITestCaseOutput out) {
/*  59 */     TestCase testCase = new TestCase(in, out);
/*  60 */     this.currentRule.addTestCase(testCase);
/*     */   }
/*     */ 
/*     */   private static String trimChars(String text, int numOfChars) {
/*  64 */     return text.substring(numOfChars, text.length() - numOfChars);
/*     */   }
/*     */ 
/*     */   public static ITestCaseInput createFileInput(String fileName) {
/*  68 */     if (fileName == null) throw new IllegalArgumentException("null");
/*  69 */     return new TestCaseInputFile(fileName);
/*     */   }
/*     */ 
/*     */   public static ITestCaseInput createStringInput(String line) {
/*  73 */     if (line == null) throw new IllegalArgumentException("null");
/*     */ 
/*  75 */     return new TestCaseInputString(trimChars(line, 1));
/*     */   }
/*     */ 
/*     */   public static ITestCaseInput createMultiInput(String text) {
/*  79 */     if (text == null) throw new IllegalArgumentException("null");
/*     */ 
/*  81 */     return new TestCaseInputMultiString(trimChars(text, 2));
/*     */   }
/*     */ 
/*     */   public static ITestCaseOutput createBoolOutput(boolean bool) {
/*  85 */     return new TestCaseOutputResult(bool);
/*     */   }
/*     */ 
/*     */   public static ITestCaseOutput createAstOutput(String ast) {
/*  89 */     if (ast == null) throw new IllegalArgumentException("null");
/*  90 */     return new TestCaseOutputAST(ast);
/*     */   }
/*     */ 
/*     */   public static ITestCaseOutput createStdOutput(String text) {
/*  94 */     if (text == null) throw new IllegalArgumentException("null");
/*     */ 
/*  96 */     return new TestCaseOutputStdOut(trimChars(text, 1));
/*     */   }
/*     */ 
/*     */   public static ITestCaseOutput createReturnOutput(String text) {
/* 100 */     if (text == null) throw new IllegalArgumentException("null");
/*     */ 
/* 102 */     return new TestCaseOutputReturn(trimChars(text, 1));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.swingui.runner.TestSuiteAdapter
 * JD-Core Version:    0.6.2
 */