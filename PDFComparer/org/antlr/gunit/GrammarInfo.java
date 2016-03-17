/*     */ package org.antlr.gunit;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ public class GrammarInfo
/*     */ {
/*     */   private String grammarName;
/*  37 */   private String treeGrammarName = null;
/*  38 */   private String grammarPackage = null;
/*  39 */   private String testPackage = null;
/*  40 */   private String adaptor = null;
/*  41 */   private List<gUnitTestSuite> ruleTestSuites = new ArrayList();
/*  42 */   private StringBuffer unitTestResult = new StringBuffer();
/*     */ 
/*     */   public String getGrammarName() {
/*  45 */     return this.grammarName;
/*     */   }
/*     */ 
/*     */   public void setGrammarName(String grammarName) {
/*  49 */     this.grammarName = grammarName;
/*     */   }
/*     */ 
/*     */   public String getTreeGrammarName() {
/*  53 */     return this.treeGrammarName;
/*     */   }
/*     */ 
/*     */   public void setTreeGrammarName(String treeGrammarName) {
/*  57 */     this.treeGrammarName = treeGrammarName;
/*     */   }
/*     */ 
/*     */   public String getTestPackage() {
/*  61 */     return this.testPackage;
/*     */   }
/*     */ 
/*     */   public void setTestPackage(String testPackage) {
/*  65 */     this.testPackage = testPackage;
/*     */   }
/*     */ 
/*     */   public String getGrammarPackage() {
/*  69 */     return this.grammarPackage;
/*     */   }
/*     */ 
/*     */   public void setGrammarPackage(String grammarPackage) {
/*  73 */     this.grammarPackage = grammarPackage;
/*     */   }
/*     */ 
/*     */   public String getAdaptor() {
/*  77 */     return this.adaptor;
/*     */   }
/*     */ 
/*     */   public void setAdaptor(String adaptor) {
/*  81 */     this.adaptor = adaptor;
/*     */   }
/*     */ 
/*     */   public List<gUnitTestSuite> getRuleTestSuites()
/*     */   {
/*  86 */     return Collections.unmodifiableList(this.ruleTestSuites);
/*     */   }
/*     */ 
/*     */   public void addRuleTestSuite(gUnitTestSuite testSuite) {
/*  90 */     this.ruleTestSuites.add(testSuite);
/*     */   }
/*     */ 
/*     */   public void appendUnitTestResult(String result) {
/*  94 */     this.unitTestResult.append(result);
/*     */   }
/*     */ 
/*     */   public String getUnitTestResult()
/*     */   {
/*  99 */     return this.unitTestResult.toString();
/*     */   }
/*     */ 
/*     */   public void setUnitTestResult(StringBuffer unitTestResult) {
/* 103 */     this.unitTestResult = unitTestResult;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.GrammarInfo
 * JD-Core Version:    0.6.2
 */