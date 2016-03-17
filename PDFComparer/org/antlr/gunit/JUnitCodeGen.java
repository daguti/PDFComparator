/*     */ package org.antlr.gunit;
/*     */ 
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.logging.ConsoleHandler;
/*     */ import java.util.logging.Handler;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.antlr.stringtemplate.CommonGroupLoader;
/*     */ import org.antlr.stringtemplate.StringTemplate;
/*     */ import org.antlr.stringtemplate.StringTemplateGroup;
/*     */ import org.antlr.stringtemplate.StringTemplateGroupLoader;
/*     */ import org.antlr.stringtemplate.language.AngleBracketTemplateLexer;
/*     */ 
/*     */ public class JUnitCodeGen
/*     */ {
/*     */   public GrammarInfo grammarInfo;
/*     */   public Map<String, String> ruleWithReturn;
/*     */   private final String testsuiteDir;
/*  51 */   private String outputDirectoryPath = ".";
/*     */ 
/*  53 */   private static final Handler console = new ConsoleHandler();
/*  54 */   private static final Logger logger = Logger.getLogger(JUnitCodeGen.class.getName());
/*     */ 
/*     */   public JUnitCodeGen(GrammarInfo grammarInfo, String testsuiteDir)
/*     */     throws ClassNotFoundException
/*     */   {
/*  60 */     this(grammarInfo, determineClassLoader(), testsuiteDir);
/*     */   }
/*     */ 
/*     */   private static ClassLoader determineClassLoader() {
/*  64 */     ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*  65 */     if (classLoader == null) {
/*  66 */       classLoader = JUnitCodeGen.class.getClassLoader();
/*     */     }
/*  68 */     return classLoader;
/*     */   }
/*     */ 
/*     */   public JUnitCodeGen(GrammarInfo grammarInfo, ClassLoader classLoader, String testsuiteDir) throws ClassNotFoundException {
/*  72 */     this.grammarInfo = grammarInfo;
/*  73 */     this.testsuiteDir = testsuiteDir;
/*     */ 
/*  75 */     this.ruleWithReturn = new HashMap();
/*  76 */     Class parserClass = locateParserClass(grammarInfo, classLoader);
/*  77 */     Method[] methods = parserClass.getDeclaredMethods();
/*  78 */     Method[] arr$ = methods; int len$ = arr$.length; for (int i$ = 0; i$ < len$; i$++) { Method method = arr$[i$];
/*  79 */       if (!method.getReturnType().getName().equals("void"))
/*  80 */         this.ruleWithReturn.put(method.getName(), method.getReturnType().getName().replace('$', '.')); }
/*     */   }
/*     */ 
/*     */   private Class locateParserClass(GrammarInfo grammarInfo, ClassLoader classLoader)
/*     */     throws ClassNotFoundException
/*     */   {
/*  86 */     String parserClassName = grammarInfo.getGrammarName() + "Parser";
/*  87 */     if (grammarInfo.getGrammarPackage() != null) {
/*  88 */       parserClassName = grammarInfo.getGrammarPackage() + "." + parserClassName;
/*     */     }
/*  90 */     return classLoader.loadClass(parserClassName);
/*     */   }
/*     */ 
/*     */   public String getOutputDirectoryPath() {
/*  94 */     return this.outputDirectoryPath;
/*     */   }
/*     */ 
/*     */   public void setOutputDirectoryPath(String outputDirectoryPath) {
/*  98 */     this.outputDirectoryPath = outputDirectoryPath;
/*     */   }
/*     */ 
/*     */   public void compile()
/*     */     throws IOException
/*     */   {
/*     */     String junitFileName;
/*     */     String junitFileName;
/* 103 */     if (this.grammarInfo.getTreeGrammarName() != null) {
/* 104 */       junitFileName = "Test" + this.grammarInfo.getTreeGrammarName();
/*     */     }
/*     */     else {
/* 107 */       junitFileName = "Test" + this.grammarInfo.getGrammarName();
/*     */     }
/* 109 */     String lexerName = this.grammarInfo.getGrammarName() + "Lexer";
/* 110 */     String parserName = this.grammarInfo.getGrammarName() + "Parser";
/*     */ 
/* 112 */     StringTemplateGroupLoader loader = new CommonGroupLoader("org/antlr/gunit", null);
/* 113 */     StringTemplateGroup.registerGroupLoader(loader);
/* 114 */     StringTemplateGroup.registerDefaultLexer(AngleBracketTemplateLexer.class);
/* 115 */     StringBuffer buf = compileToBuffer(junitFileName, lexerName, parserName);
/* 116 */     writeTestFile(".", junitFileName + ".java", buf.toString());
/*     */   }
/*     */ 
/*     */   public StringBuffer compileToBuffer(String className, String lexerName, String parserName) {
/* 120 */     StringTemplateGroup group = StringTemplateGroup.loadGroup("junit");
/* 121 */     StringBuffer buf = new StringBuffer();
/* 122 */     buf.append(genClassHeader(group, className, lexerName, parserName));
/* 123 */     buf.append(genTestRuleMethods(group));
/* 124 */     buf.append("\n\n}");
/* 125 */     return buf;
/*     */   }
/*     */ 
/*     */   protected String genClassHeader(StringTemplateGroup group, String junitFileName, String lexerName, String parserName) {
/* 129 */     StringTemplate classHeaderST = group.getInstanceOf("classHeader");
/* 130 */     if (this.grammarInfo.getTestPackage() != null) {
/* 131 */       classHeaderST.setAttribute("header", "package " + this.grammarInfo.getTestPackage() + ";");
/*     */     }
/* 133 */     classHeaderST.setAttribute("junitFileName", junitFileName);
/*     */ 
/* 135 */     String lexerPath = null;
/* 136 */     String parserPath = null;
/* 137 */     String treeParserPath = null;
/* 138 */     String packagePath = null;
/* 139 */     boolean isTreeGrammar = false;
/* 140 */     boolean hasPackage = false;
/*     */ 
/* 142 */     if (this.grammarInfo.getGrammarPackage() != null) {
/* 143 */       hasPackage = true;
/* 144 */       packagePath = "./" + this.grammarInfo.getGrammarPackage().replace('.', '/');
/* 145 */       lexerPath = this.grammarInfo.getGrammarPackage() + "." + lexerName;
/* 146 */       parserPath = this.grammarInfo.getGrammarPackage() + "." + parserName;
/* 147 */       if (this.grammarInfo.getTreeGrammarName() != null) {
/* 148 */         treeParserPath = this.grammarInfo.getGrammarPackage() + "." + this.grammarInfo.getTreeGrammarName();
/* 149 */         isTreeGrammar = true;
/*     */       }
/*     */     }
/*     */     else {
/* 153 */       lexerPath = lexerName;
/* 154 */       parserPath = parserName;
/* 155 */       if (this.grammarInfo.getTreeGrammarName() != null) {
/* 156 */         treeParserPath = this.grammarInfo.getTreeGrammarName();
/* 157 */         isTreeGrammar = true;
/*     */       }
/*     */     }
/*     */ 
/* 161 */     String treeAdaptorPath = null;
/* 162 */     boolean hasTreeAdaptor = false;
/* 163 */     if (this.grammarInfo.getAdaptor() != null) {
/* 164 */       hasTreeAdaptor = true;
/* 165 */       treeAdaptorPath = this.grammarInfo.getAdaptor();
/*     */     }
/* 167 */     classHeaderST.setAttribute("hasTreeAdaptor", new Boolean(hasTreeAdaptor));
/* 168 */     classHeaderST.setAttribute("treeAdaptorPath", treeAdaptorPath);
/* 169 */     classHeaderST.setAttribute("hasPackage", new Boolean(hasPackage));
/* 170 */     classHeaderST.setAttribute("packagePath", packagePath);
/* 171 */     classHeaderST.setAttribute("lexerPath", lexerPath);
/* 172 */     classHeaderST.setAttribute("parserPath", parserPath);
/* 173 */     classHeaderST.setAttribute("treeParserPath", treeParserPath);
/* 174 */     classHeaderST.setAttribute("isTreeGrammar", new Boolean(isTreeGrammar));
/* 175 */     return classHeaderST.toString();
/*     */   }
/*     */ 
/*     */   protected String genTestRuleMethods(StringTemplateGroup group) {
/* 179 */     StringBuffer buf = new StringBuffer();
/* 180 */     if (this.grammarInfo.getTreeGrammarName() != null) {
/* 181 */       genTreeMethods(group, buf);
/*     */     }
/*     */     else {
/* 184 */       genParserMethods(group, buf);
/*     */     }
/* 186 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   private void genParserMethods(StringTemplateGroup group, StringBuffer buf) {
/* 190 */     for (Iterator i$ = this.grammarInfo.getRuleTestSuites().iterator(); i$.hasNext(); ) { ts = (gUnitTestSuite)i$.next();
/* 191 */       i = 0;
/* 192 */       for (i$ = ts.testSuites.keySet().iterator(); i$.hasNext(); ) { gUnitTestInput input = (gUnitTestInput)i$.next();
/* 193 */         i++;
/*     */         StringTemplate testRuleMethodST;
/* 196 */         if ((((AbstractTest)ts.testSuites.get(input)).getType() == 9) && (this.ruleWithReturn.containsKey(ts.getRuleName()))) {
/* 197 */           StringTemplate testRuleMethodST = group.getInstanceOf("testRuleMethod2");
/* 198 */           String outputString = ((AbstractTest)ts.testSuites.get(input)).getText();
/* 199 */           testRuleMethodST.setAttribute("methodName", "test" + changeFirstCapital(ts.getRuleName()) + i);
/* 200 */           testRuleMethodST.setAttribute("testRuleName", '"' + ts.getRuleName() + '"');
/* 201 */           testRuleMethodST.setAttribute("test", input);
/* 202 */           testRuleMethodST.setAttribute("returnType", this.ruleWithReturn.get(ts.getRuleName()));
/* 203 */           testRuleMethodST.setAttribute("expecting", outputString);
/*     */         }
/*     */         else
/*     */         {
/* 208 */           String testRuleName;
/*     */           String testRuleName;
/* 208 */           if (ts.isLexicalRule()) testRuleName = ts.getLexicalRuleName(); else
/* 209 */             testRuleName = ts.getRuleName();
/* 210 */           testRuleMethodST = group.getInstanceOf("testRuleMethod");
/* 211 */           String outputString = ((AbstractTest)ts.testSuites.get(input)).getText();
/* 212 */           testRuleMethodST.setAttribute("isLexicalRule", new Boolean(ts.isLexicalRule()));
/* 213 */           testRuleMethodST.setAttribute("methodName", "test" + changeFirstCapital(testRuleName) + i);
/* 214 */           testRuleMethodST.setAttribute("testRuleName", '"' + testRuleName + '"');
/* 215 */           testRuleMethodST.setAttribute("test", input);
/* 216 */           testRuleMethodST.setAttribute("tokenType", getTypeString(((AbstractTest)ts.testSuites.get(input)).getType()));
/*     */ 
/* 219 */           outputString = normalizeTreeSpec(outputString);
/*     */ 
/* 221 */           if (((AbstractTest)ts.testSuites.get(input)).getType() == 9)
/*     */           {
/* 223 */             testRuleMethodST.setAttribute("expecting", outputString);
/*     */           }
/* 225 */           else if (((AbstractTest)ts.testSuites.get(input)).getType() == 14) {
/* 226 */             testRuleMethodST.setAttribute("expecting", outputString);
/*     */           }
/*     */           else
/*     */           {
/* 230 */             outputString = outputString.replaceAll("\n", "");
/* 231 */             testRuleMethodST.setAttribute("expecting", '"' + escapeForJava(outputString) + '"');
/*     */           }
/*     */         }
/* 234 */         buf.append(testRuleMethodST.toString()); }  } gUnitTestSuite ts;
/*     */     int i;
/*     */     Iterator i$;
/*     */   }
/* 240 */   private void genTreeMethods(StringTemplateGroup group, StringBuffer buf) { for (Iterator i$ = this.grammarInfo.getRuleTestSuites().iterator(); i$.hasNext(); ) { ts = (gUnitTestSuite)i$.next();
/* 241 */       i = 0;
/* 242 */       for (i$ = ts.testSuites.keySet().iterator(); i$.hasNext(); ) { gUnitTestInput input = (gUnitTestInput)i$.next();
/* 243 */         i++;
/*     */         StringTemplate testRuleMethodST;
/* 246 */         if ((((AbstractTest)ts.testSuites.get(input)).getType() == 9) && (this.ruleWithReturn.containsKey(ts.getTreeRuleName()))) {
/* 247 */           StringTemplate testRuleMethodST = group.getInstanceOf("testTreeRuleMethod2");
/* 248 */           String outputString = ((AbstractTest)ts.testSuites.get(input)).getText();
/* 249 */           testRuleMethodST.setAttribute("methodName", "test" + changeFirstCapital(ts.getTreeRuleName()) + "_walks_" + changeFirstCapital(ts.getRuleName()) + i);
/*     */ 
/* 251 */           testRuleMethodST.setAttribute("testTreeRuleName", '"' + ts.getTreeRuleName() + '"');
/* 252 */           testRuleMethodST.setAttribute("testRuleName", '"' + ts.getRuleName() + '"');
/* 253 */           testRuleMethodST.setAttribute("test", input);
/* 254 */           testRuleMethodST.setAttribute("returnType", this.ruleWithReturn.get(ts.getTreeRuleName()));
/* 255 */           testRuleMethodST.setAttribute("expecting", outputString);
/*     */         }
/*     */         else {
/* 258 */           testRuleMethodST = group.getInstanceOf("testTreeRuleMethod");
/* 259 */           String outputString = ((AbstractTest)ts.testSuites.get(input)).getText();
/* 260 */           testRuleMethodST.setAttribute("methodName", "test" + changeFirstCapital(ts.getTreeRuleName()) + "_walks_" + changeFirstCapital(ts.getRuleName()) + i);
/*     */ 
/* 262 */           testRuleMethodST.setAttribute("testTreeRuleName", '"' + ts.getTreeRuleName() + '"');
/* 263 */           testRuleMethodST.setAttribute("testRuleName", '"' + ts.getRuleName() + '"');
/* 264 */           testRuleMethodST.setAttribute("test", input);
/* 265 */           testRuleMethodST.setAttribute("tokenType", getTypeString(((AbstractTest)ts.testSuites.get(input)).getType()));
/*     */ 
/* 267 */           if (((AbstractTest)ts.testSuites.get(input)).getType() == 9)
/*     */           {
/* 269 */             testRuleMethodST.setAttribute("expecting", outputString);
/*     */           }
/* 271 */           else if (((AbstractTest)ts.testSuites.get(input)).getType() == 14) {
/* 272 */             testRuleMethodST.setAttribute("expecting", outputString);
/*     */           }
/*     */           else {
/* 275 */             testRuleMethodST.setAttribute("expecting", '"' + escapeForJava(outputString) + '"');
/*     */           }
/*     */         }
/* 278 */         buf.append(testRuleMethodST.toString());
/*     */       }
/*     */     }
/*     */     gUnitTestSuite ts;
/*     */     int i;
/*     */     Iterator i$;
/*     */   }
/*     */ 
/*     */   public String getTypeString(int type)
/*     */   {
/*     */     String typeText;
/* 286 */     switch (type) {
/*     */     case 4:
/* 288 */       typeText = "org.antlr.gunit.gUnitParser.OK";
/* 289 */       break;
/*     */     case 5:
/* 291 */       typeText = "org.antlr.gunit.gUnitParser.FAIL";
/* 292 */       break;
/*     */     case 12:
/* 294 */       typeText = "org.antlr.gunit.gUnitParser.STRING";
/* 295 */       break;
/*     */     case 13:
/* 297 */       typeText = "org.antlr.gunit.gUnitParser.ML_STRING";
/* 298 */       break;
/*     */     case 14:
/* 300 */       typeText = "org.antlr.gunit.gUnitParser.RETVAL";
/* 301 */       break;
/*     */     case 15:
/* 303 */       typeText = "org.antlr.gunit.gUnitParser.AST";
/* 304 */       break;
/*     */     case 6:
/*     */     case 7:
/*     */     case 8:
/*     */     case 9:
/*     */     case 10:
/*     */     case 11:
/*     */     default:
/* 306 */       typeText = "org.antlr.gunit.gUnitParser.EOF";
/*     */     }
/*     */ 
/* 309 */     return typeText;
/*     */   }
/*     */ 
/*     */   protected void writeTestFile(String dir, String fileName, String content) {
/*     */     try {
/* 314 */       File f = new File(dir, fileName);
/* 315 */       FileWriter w = new FileWriter(f);
/* 316 */       BufferedWriter bw = new BufferedWriter(w);
/* 317 */       bw.write(content);
/* 318 */       bw.close();
/* 319 */       w.close();
/*     */     }
/*     */     catch (IOException ioe) {
/* 322 */       logger.log(Level.SEVERE, "can't write file", ioe);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String escapeForJava(String inputString)
/*     */   {
/* 328 */     inputString = inputString.replace("\\", "\\\\");
/*     */ 
/* 330 */     inputString = inputString.replace("\"", "\\\"");
/*     */ 
/* 332 */     inputString = inputString.replace("\n", "\\n").replace("\t", "\\t").replace("\r", "\\r").replace("\b", "\\b").replace("\f", "\\f");
/*     */ 
/* 334 */     return inputString;
/*     */   }
/*     */ 
/*     */   protected String changeFirstCapital(String ruleName) {
/* 338 */     String firstChar = String.valueOf(ruleName.charAt(0));
/* 339 */     return firstChar.toUpperCase() + ruleName.substring(1);
/*     */   }
/*     */ 
/*     */   public static String normalizeTreeSpec(String t) {
/* 343 */     List words = new ArrayList();
/* 344 */     int i = 0;
/* 345 */     StringBuilder word = new StringBuilder();
/* 346 */     while (i < t.length())
/* 347 */       if ((t.charAt(i) == '(') || (t.charAt(i) == ')')) {
/* 348 */         if (word.length() > 0) {
/* 349 */           words.add(word.toString());
/* 350 */           word.setLength(0);
/*     */         }
/* 352 */         words.add(String.valueOf(t.charAt(i)));
/* 353 */         i++;
/*     */       }
/* 356 */       else if (Character.isWhitespace(t.charAt(i)))
/*     */       {
/* 358 */         if (word.length() > 0) {
/* 359 */           words.add(word.toString());
/* 360 */           word.setLength(0);
/*     */         }
/* 362 */         i++;
/*     */       }
/* 367 */       else if ((t.charAt(i) == '"') && (i - 1 >= 0) && ((t.charAt(i - 1) == '(') || (Character.isWhitespace(t.charAt(i - 1)))))
/*     */       {
/* 370 */         i++;
/* 371 */         while ((i < t.length()) && (t.charAt(i) != '"'))
/* 372 */           if ((t.charAt(i) == '\\') && (i + 1 < t.length()) && (t.charAt(i + 1) == '"'))
/*     */           {
/* 375 */             word.append('"');
/* 376 */             i += 2;
/*     */           }
/*     */           else {
/* 379 */             word.append(t.charAt(i));
/* 380 */             i++;
/*     */           }
/* 382 */         i++;
/* 383 */         words.add(word.toString());
/* 384 */         word.setLength(0);
/*     */       }
/*     */       else {
/* 387 */         word.append(t.charAt(i));
/* 388 */         i++;
/*     */       }
/* 390 */     if (word.length() > 0) {
/* 391 */       words.add(word.toString());
/*     */     }
/*     */ 
/* 394 */     StringBuilder buf = new StringBuilder();
/* 395 */     for (int j = 0; j < words.size(); j++) {
/* 396 */       if ((j > 0) && (!((String)words.get(j)).equals(")")) && (!((String)words.get(j - 1)).equals("(")))
/*     */       {
/* 398 */         buf.append(' ');
/*     */       }
/* 400 */       buf.append((String)words.get(j));
/*     */     }
/* 402 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  56 */     logger.addHandler(console);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.JUnitCodeGen
 * JD-Core Version:    0.6.2
 */