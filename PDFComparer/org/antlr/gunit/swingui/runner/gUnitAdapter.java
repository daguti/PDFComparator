/*    */ package org.antlr.gunit.swingui.runner;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import org.antlr.gunit.GrammarInfo;
/*    */ import org.antlr.gunit.gUnitExecutor;
/*    */ import org.antlr.gunit.gUnitLexer;
/*    */ import org.antlr.gunit.gUnitParser;
/*    */ import org.antlr.gunit.swingui.model.TestSuite;
/*    */ import org.antlr.runtime.ANTLRFileStream;
/*    */ import org.antlr.runtime.CharStream;
/*    */ import org.antlr.runtime.CommonTokenStream;
/*    */ 
/*    */ public class gUnitAdapter
/*    */ {
/*    */   private ParserLoader loader;
/*    */   private TestSuite testSuite;
/*    */ 
/*    */   public gUnitAdapter(TestSuite suite)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 48 */     int i = 3;
/* 49 */     this.loader = new ParserLoader(suite.getGrammarName(), suite.getTestSuiteFile().getParent());
/*    */ 
/* 51 */     this.testSuite = suite;
/*    */   }
/*    */ 
/*    */   public void run() {
/* 55 */     if (this.testSuite == null) {
/* 56 */       throw new IllegalArgumentException("Null testsuite.");
/*    */     }
/*    */ 
/*    */     try
/*    */     {
/* 62 */       CharStream input = new ANTLRFileStream(this.testSuite.getTestSuiteFile().getCanonicalPath());
/* 63 */       gUnitLexer lexer = new gUnitLexer(input);
/* 64 */       CommonTokenStream tokens = new CommonTokenStream(lexer);
/* 65 */       GrammarInfo grammarInfo = new GrammarInfo();
/* 66 */       gUnitParser parser = new gUnitParser(tokens, grammarInfo);
/* 67 */       parser.gUnitDef();
/*    */ 
/* 70 */       gUnitExecutor executer = new NotifiedTestExecuter(grammarInfo, this.loader, this.testSuite.getTestSuiteFile().getParent(), this.testSuite);
/*    */ 
/* 73 */       executer.execTest();
/*    */     }
/*    */     catch (Exception e) {
/* 76 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.swingui.runner.gUnitAdapter
 * JD-Core Version:    0.6.2
 */