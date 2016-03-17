/*     */ package org.antlr.gunit.swingui.model;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.antlr.gunit.swingui.parsers.ANTLRv3Lexer;
/*     */ import org.antlr.gunit.swingui.parsers.ANTLRv3Parser;
/*     */ import org.antlr.gunit.swingui.parsers.StGUnitLexer;
/*     */ import org.antlr.gunit.swingui.parsers.StGUnitParser;
/*     */ import org.antlr.gunit.swingui.runner.TestSuiteAdapter;
/*     */ import org.antlr.runtime.ANTLRReaderStream;
/*     */ import org.antlr.runtime.CommonTokenStream;
/*     */ import org.antlr.stringtemplate.StringTemplate;
/*     */ import org.antlr.stringtemplate.StringTemplateGroup;
/*     */ 
/*     */ public class TestSuiteFactory
/*     */ {
/*  46 */   private static String TEMPLATE_FILE = "org/antlr/gunit/swingui/gunit.stg";
/*     */ 
/*  58 */   private static StringTemplateGroup templates = new StringTemplateGroup(rd);
/*     */   public static final String TEST_SUITE_EXT = ".gunit";
/*     */   public static final String GRAMMAR_EXT = ".g";
/*     */ 
/*     */   public static TestSuite createTestSuite(File grammarFile)
/*     */   {
/*  68 */     if ((grammarFile != null) && (grammarFile.exists()) && (grammarFile.isFile()))
/*     */     {
/*  70 */       String fileName = grammarFile.getName();
/*  71 */       String grammarName = fileName.substring(0, fileName.lastIndexOf('.'));
/*  72 */       String grammarDir = grammarFile.getParent();
/*  73 */       File testFile = new File(grammarDir + File.separator + grammarName + ".gunit");
/*     */ 
/*  75 */       TestSuite result = new TestSuite(grammarName, testFile);
/*  76 */       result.rules = loadRulesFromGrammar(grammarFile);
/*     */ 
/*  78 */       if (saveTestSuite(result)) {
/*  79 */         return result;
/*     */       }
/*  81 */       throw new RuntimeException("Can't save test suite file.");
/*     */     }
/*     */ 
/*  84 */     throw new RuntimeException("Invalid grammar file.");
/*     */   }
/*     */ 
/*     */   private static List<Rule> loadRulesFromGrammar(File grammarFile)
/*     */   {
/*  93 */     List ruleNames = new ArrayList();
/*     */     try {
/*  95 */       Reader reader = new BufferedReader(new FileReader(grammarFile));
/*  96 */       ANTLRv3Lexer lexer = new ANTLRv3Lexer(new ANTLRReaderStream(reader));
/*  97 */       CommonTokenStream tokens = new CommonTokenStream(lexer);
/*  98 */       ANTLRv3Parser parser = new ANTLRv3Parser(tokens);
/*  99 */       parser.rules = ruleNames;
/* 100 */       parser.grammarDef();
/* 101 */       reader.close();
/*     */     } catch (Exception e) {
/* 103 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 107 */     List ruleList = new ArrayList();
/* 108 */     for (Iterator i$ = ruleNames.iterator(); i$.hasNext(); ) { String str = (String)i$.next();
/* 109 */       ruleList.add(new Rule(str));
/*     */     }
/*     */ 
/* 112 */     return ruleList;
/*     */   }
/*     */ 
/*     */   public static boolean saveTestSuite(TestSuite testSuite)
/*     */   {
/* 117 */     String data = getScript(testSuite);
/*     */     try {
/* 119 */       FileWriter fw = new FileWriter(testSuite.getTestSuiteFile());
/* 120 */       fw.write(data);
/* 121 */       fw.flush();
/* 122 */       fw.close();
/*     */     } catch (IOException e) {
/* 124 */       e.printStackTrace();
/* 125 */       return false;
/*     */     }
/* 127 */     return true;
/*     */   }
/*     */ 
/*     */   public static String getScript(TestSuite testSuite)
/*     */   {
/* 136 */     if (testSuite == null) return null;
/* 137 */     StringTemplate gUnitScript = templates.getInstanceOf("gUnitFile");
/* 138 */     gUnitScript.setAttribute("testSuite", testSuite);
/*     */ 
/* 140 */     return gUnitScript.toString();
/*     */   }
/*     */ 
/*     */   public static TestSuite loadTestSuite(File file)
/*     */   {
/* 149 */     if (file.getName().endsWith(".g")) {
/* 150 */       throw new RuntimeException(file.getName() + " is a grammar file not a gunit file");
/*     */     }
/*     */ 
/* 153 */     File grammarFile = getGrammarFile(file);
/* 154 */     if (grammarFile == null) {
/* 155 */       throw new RuntimeException("Can't find grammar file associated with gunit file: " + file.getAbsoluteFile());
/*     */     }
/* 157 */     TestSuite result = new TestSuite("", file);
/*     */     try
/*     */     {
/* 161 */       Reader reader = new BufferedReader(new FileReader(file));
/* 162 */       StGUnitLexer lexer = new StGUnitLexer(new ANTLRReaderStream(reader));
/* 163 */       CommonTokenStream tokens = new CommonTokenStream(lexer);
/* 164 */       StGUnitParser parser = new StGUnitParser(tokens);
/* 165 */       TestSuiteAdapter adapter = new TestSuiteAdapter(result);
/* 166 */       parser.adapter = adapter;
/* 167 */       parser.gUnitDef();
/* 168 */       result.setTokens(tokens);
/* 169 */       reader.close();
/*     */     } catch (Exception ex) {
/* 171 */       throw new RuntimeException("Error reading test suite file.\n" + ex.getMessage());
/*     */     }
/*     */ 
/* 175 */     List completeRuleList = loadRulesFromGrammar(grammarFile);
/* 176 */     for (Iterator i$ = completeRuleList.iterator(); i$.hasNext(); ) { Rule rule = (Rule)i$.next();
/* 177 */       if (!result.hasRule(rule)) {
/* 178 */         result.addRule(rule);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 183 */     return result;
/*     */   }
/*     */ 
/*     */   private static File getGrammarFile(File testsuiteFile)
/*     */   {
/*     */     String sTestFile;
/*     */     try
/*     */     {
/* 194 */       sTestFile = testsuiteFile.getCanonicalPath();
/*     */     }
/*     */     catch (IOException e) {
/* 197 */       return null;
/*     */     }
/*     */ 
/* 200 */     String fname = sTestFile.substring(0, sTestFile.lastIndexOf('.')) + ".g";
/*     */ 
/* 202 */     File fileGrammar = new File(fname);
/* 203 */     if ((fileGrammar.exists()) && (fileGrammar.isFile())) return fileGrammar;
/*     */ 
/* 205 */     fname = sTestFile.substring(0, sTestFile.lastIndexOf('.')) + "Parser" + ".g";
/* 206 */     if ((fileGrammar.exists()) && (fileGrammar.isFile())) return fileGrammar;
/* 207 */     return fileGrammar;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  52 */     ClassLoader loader = TestSuiteFactory.class.getClassLoader();
/*  53 */     InputStream in = loader.getResourceAsStream(TEMPLATE_FILE);
/*  54 */     if (in == null) {
/*  55 */       throw new RuntimeException("internal error: Can't find templates " + TEMPLATE_FILE);
/*     */     }
/*  57 */     Reader rd = new InputStreamReader(in);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.swingui.model.TestSuiteFactory
 * JD-Core Version:    0.6.2
 */