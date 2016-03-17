/*    */ package org.antlr.gunit.swingui.model;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.antlr.runtime.CommonTokenStream;
/*    */ 
/*    */ public class TestSuite
/*    */ {
/*    */   protected List<Rule> rules;
/*    */   protected String grammarName;
/*    */   protected CommonTokenStream tokens;
/*    */   protected File testSuiteFile;
/*    */ 
/*    */   protected TestSuite(String gname, File testFile)
/*    */   {
/* 42 */     this.grammarName = gname;
/* 43 */     this.testSuiteFile = testFile;
/* 44 */     this.rules = new ArrayList();
/*    */   }
/*    */ 
/*    */   public File getTestSuiteFile()
/*    */   {
/* 49 */     return this.testSuiteFile;
/*    */   }
/*    */ 
/*    */   public void addRule(Rule currentRule) {
/* 53 */     if (currentRule == null) throw new IllegalArgumentException("Null rule");
/* 54 */     this.rules.add(currentRule);
/*    */   }
/*    */ 
/*    */   public boolean hasRule(Rule rule)
/*    */   {
/* 59 */     for (Iterator i$ = this.rules.iterator(); i$.hasNext(); ) { Rule r = (Rule)i$.next();
/* 60 */       if (r.getName().equals(rule.getName())) {
/* 61 */         return true;
/*    */       }
/*    */     }
/* 64 */     return false;
/*    */   }
/*    */ 
/*    */   public int getRuleCount() {
/* 68 */     return this.rules.size();
/*    */   }
/*    */ 
/*    */   public void setRules(List<Rule> newRules) {
/* 72 */     this.rules.clear();
/* 73 */     this.rules.addAll(newRules);
/*    */   }
/*    */ 
/*    */   public void setGrammarName(String name)
/*    */   {
/* 78 */     this.grammarName = name;
/*    */   }
/* 80 */   public String getGrammarName() { return this.grammarName; } 
/*    */   public Rule getRule(int index) {
/* 82 */     return (Rule)this.rules.get(index);
/*    */   }
/* 84 */   public CommonTokenStream getTokens() { return this.tokens; } 
/*    */   public void setTokens(CommonTokenStream ts) {
/* 86 */     this.tokens = ts;
/*    */   }
/*    */   public Rule getRule(String name) {
/* 89 */     for (Iterator i$ = this.rules.iterator(); i$.hasNext(); ) { Rule rule = (Rule)i$.next();
/* 90 */       if (rule.getName().equals(name)) {
/* 91 */         return rule;
/*    */       }
/*    */     }
/* 94 */     return null;
/*    */   }
/*    */ 
/*    */   public List getRulesForStringTemplate() {
/* 98 */     return this.rules;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.swingui.model.TestSuite
 * JD-Core Version:    0.6.2
 */