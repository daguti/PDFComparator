/*    */ package org.antlr.gunit;
/*    */ 
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class gUnitTestSuite
/*    */ {
/* 35 */   protected String rule = null;
/* 36 */   protected String lexicalRule = null;
/* 37 */   protected String treeRule = null;
/* 38 */   protected boolean isLexicalRule = false;
/*    */ 
/* 44 */   protected Map<gUnitTestInput, AbstractTest> testSuites = new LinkedHashMap();
/*    */ 
/*    */   public gUnitTestSuite() {
/*    */   }
/*    */ 
/*    */   public gUnitTestSuite(String rule) {
/* 50 */     this.rule = rule;
/*    */   }
/*    */ 
/*    */   public gUnitTestSuite(String treeRule, String rule) {
/* 54 */     this.rule = rule;
/* 55 */     this.treeRule = treeRule;
/*    */   }
/*    */   public void setRuleName(String ruleName) {
/* 58 */     this.rule = ruleName; } 
/* 59 */   public void setLexicalRuleName(String lexicalRule) { this.lexicalRule = lexicalRule; this.isLexicalRule = true; } 
/* 60 */   public void setTreeRuleName(String treeRuleName) { this.treeRule = treeRuleName; } 
/*    */   public String getRuleName() {
/* 62 */     return this.rule; } 
/* 63 */   public String getLexicalRuleName() { return this.lexicalRule; } 
/* 64 */   public String getTreeRuleName() { return this.treeRule; } 
/* 65 */   public boolean isLexicalRule() { return this.isLexicalRule; }
/*    */ 
/*    */   public void addTestCase(gUnitTestInput input, AbstractTest expect) {
/* 68 */     if ((input != null) && (expect != null))
/*    */     {
/* 74 */       expect.setTestedRuleName(this.rule == null ? this.lexicalRule : this.rule);
/* 75 */       expect.setTestCaseIndex(this.testSuites.size());
/* 76 */       this.testSuites.put(input, expect);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.gUnitTestSuite
 * JD-Core Version:    0.6.2
 */