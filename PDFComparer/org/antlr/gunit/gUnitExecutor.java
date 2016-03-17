/*     */ package org.antlr.gunit;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.antlr.runtime.ANTLRFileStream;
/*     */ import org.antlr.runtime.ANTLRStringStream;
/*     */ import org.antlr.runtime.CharStream;
/*     */ import org.antlr.runtime.CommonTokenStream;
/*     */ import org.antlr.runtime.Lexer;
/*     */ import org.antlr.runtime.TokenStream;
/*     */ import org.antlr.runtime.tree.CommonTree;
/*     */ import org.antlr.runtime.tree.CommonTreeNodeStream;
/*     */ import org.antlr.runtime.tree.TreeAdaptor;
/*     */ import org.antlr.runtime.tree.TreeNodeStream;
/*     */ import org.antlr.stringtemplate.CommonGroupLoader;
/*     */ import org.antlr.stringtemplate.StringTemplate;
/*     */ import org.antlr.stringtemplate.StringTemplateGroup;
/*     */ import org.antlr.stringtemplate.StringTemplateGroupLoader;
/*     */ import org.antlr.stringtemplate.language.AngleBracketTemplateLexer;
/*     */ 
/*     */ public class gUnitExecutor
/*     */   implements ITestSuite
/*     */ {
/*     */   public GrammarInfo grammarInfo;
/*     */   private final ClassLoader grammarClassLoader;
/*     */   private final String testsuiteDir;
/*     */   public int numOfTest;
/*     */   public int numOfSuccess;
/*     */   public int numOfFailure;
/*     */   private String title;
/*     */   public int numOfInvalidInput;
/*     */   private String parserName;
/*     */   private String lexerName;
/*     */   public List<AbstractTest> failures;
/*     */   public List<AbstractTest> invalids;
/*  66 */   private PrintStream console = System.out;
/*  67 */   private PrintStream consoleErr = System.err;
/*     */ 
/*     */   public gUnitExecutor(GrammarInfo grammarInfo, String testsuiteDir) {
/*  70 */     this(grammarInfo, determineClassLoader(), testsuiteDir);
/*     */   }
/*     */ 
/*     */   private static ClassLoader determineClassLoader() {
/*  74 */     ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*  75 */     if (classLoader == null) {
/*  76 */       classLoader = gUnitExecutor.class.getClassLoader();
/*     */     }
/*  78 */     return classLoader;
/*     */   }
/*     */ 
/*     */   public gUnitExecutor(GrammarInfo grammarInfo, ClassLoader grammarClassLoader, String testsuiteDir) {
/*  82 */     this.grammarInfo = grammarInfo;
/*  83 */     this.grammarClassLoader = grammarClassLoader;
/*  84 */     this.testsuiteDir = testsuiteDir;
/*  85 */     this.numOfTest = 0;
/*  86 */     this.numOfSuccess = 0;
/*  87 */     this.numOfFailure = 0;
/*  88 */     this.numOfInvalidInput = 0;
/*  89 */     this.failures = new ArrayList();
/*  90 */     this.invalids = new ArrayList();
/*     */   }
/*     */ 
/*     */   protected ClassLoader getGrammarClassLoader() {
/*  94 */     return this.grammarClassLoader;
/*     */   }
/*     */ 
/*     */   protected final Class classForName(String name) throws ClassNotFoundException {
/*  98 */     return getGrammarClassLoader().loadClass(name);
/*     */   }
/*     */ 
/*     */   public String execTest() throws IOException
/*     */   {
/* 103 */     StringTemplate testResultST = getTemplateGroup().getInstanceOf("testResult");
/*     */     try
/*     */     {
/* 106 */       if (this.grammarInfo.getGrammarPackage() != null) {
/* 107 */         this.parserName = (this.grammarInfo.getGrammarPackage() + "." + this.grammarInfo.getGrammarName() + "Parser");
/* 108 */         this.lexerName = (this.grammarInfo.getGrammarPackage() + "." + this.grammarInfo.getGrammarName() + "Lexer");
/*     */       }
/*     */       else {
/* 111 */         this.parserName = (this.grammarInfo.getGrammarName() + "Parser");
/* 112 */         this.lexerName = (this.grammarInfo.getGrammarName() + "Lexer");
/*     */       }
/*     */ 
/* 117 */       if (this.grammarInfo.getTreeGrammarName() != null) {
/* 118 */         this.title = ("executing testsuite for tree grammar:" + this.grammarInfo.getTreeGrammarName() + " walks " + this.parserName);
/*     */       }
/*     */       else {
/* 121 */         this.title = ("executing testsuite for grammar:" + this.grammarInfo.getGrammarName());
/*     */       }
/* 123 */       executeTests();
/*     */ 
/* 127 */       testResultST.setAttribute("title", this.title);
/* 128 */       testResultST.setAttribute("num_of_test", this.numOfTest);
/* 129 */       testResultST.setAttribute("num_of_failure", this.numOfFailure);
/* 130 */       if (this.numOfFailure > 0) {
/* 131 */         testResultST.setAttribute("failure", this.failures);
/*     */       }
/* 133 */       if (this.numOfInvalidInput > 0) {
/* 134 */         testResultST.setAttribute("has_invalid", new Boolean(true));
/* 135 */         testResultST.setAttribute("num_of_invalid", this.numOfInvalidInput);
/* 136 */         testResultST.setAttribute("invalid", this.invalids);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 140 */       e.printStackTrace();
/* 141 */       System.exit(1);
/*     */     }
/* 143 */     return testResultST.toString();
/*     */   }
/*     */ 
/*     */   private StringTemplateGroup getTemplateGroup() {
/* 147 */     StringTemplateGroupLoader loader = new CommonGroupLoader("org/antlr/gunit", null);
/* 148 */     StringTemplateGroup.registerGroupLoader(loader);
/* 149 */     StringTemplateGroup.registerDefaultLexer(AngleBracketTemplateLexer.class);
/* 150 */     StringTemplateGroup group = StringTemplateGroup.loadGroup("gUnitTestResult");
/* 151 */     return group;
/*     */   }
/*     */ 
/*     */   private gUnitTestResult runCorrectParser(String parserName, String lexerName, String rule, String lexicalRule, String treeRule, gUnitTestInput input)
/*     */     throws Exception
/*     */   {
/* 157 */     if (lexicalRule != null) return runLexer(lexerName, lexicalRule, input);
/* 158 */     if (treeRule != null) return runTreeParser(parserName, lexerName, rule, treeRule, input);
/* 159 */     return runParser(parserName, lexerName, rule, input);
/*     */   }
/*     */ 
/*     */   private void executeTests() throws Exception {
/* 163 */     for (Iterator i$ = this.grammarInfo.getRuleTestSuites().iterator(); i$.hasNext(); ) { ts = (gUnitTestSuite)i$.next();
/* 164 */       rule = ts.getRuleName();
/* 165 */       lexicalRule = ts.getLexicalRuleName();
/* 166 */       treeRule = ts.getTreeRuleName();
/* 167 */       for (i$ = ts.testSuites.keySet().iterator(); i$.hasNext(); ) { gUnitTestInput input = (gUnitTestInput)i$.next();
/* 168 */         this.numOfTest += 1;
/*     */ 
/* 170 */         gUnitTestResult result = null;
/* 171 */         AbstractTest test = (AbstractTest)ts.testSuites.get(input);
/*     */         try
/*     */         {
/* 175 */           result = runCorrectParser(this.parserName, this.lexerName, rule, lexicalRule, treeRule, input);
/*     */         }
/*     */         catch (InvalidInputException e)
/*     */         {
/* 179 */           this.numOfInvalidInput += 1;
/* 180 */           test.setHeader(rule, lexicalRule, treeRule, this.numOfTest, input.line);
/* 181 */           test.setActual(input.input);
/* 182 */           this.invalids.add(test);
/* 183 */         }continue;
/*     */ 
/* 186 */         String expected = test.getExpected();
/* 187 */         String actual = test.getResult(result);
/* 188 */         test.setActual(actual);
/*     */ 
/* 190 */         if (actual == null) {
/* 191 */           this.numOfFailure += 1;
/* 192 */           test.setHeader(rule, lexicalRule, treeRule, this.numOfTest, input.line);
/* 193 */           test.setActual("null");
/* 194 */           this.failures.add(test);
/* 195 */           onFail(test);
/*     */         }
/* 198 */         else if ((expected.equals(actual)) || ((expected.equals("FAIL")) && (!actual.equals("OK")))) {
/* 199 */           this.numOfSuccess += 1;
/* 200 */           onPass(test);
/*     */         }
/* 203 */         else if (((AbstractTest)ts.testSuites.get(input)).getType() == 9) {
/* 204 */           this.numOfFailure += 1;
/* 205 */           test.setHeader(rule, lexicalRule, treeRule, this.numOfTest, input.line);
/* 206 */           test.setActual("\t{ACTION} is not supported in the grammarInfo yet...");
/* 207 */           this.failures.add(test);
/* 208 */           onFail(test);
/*     */         }
/*     */         else {
/* 211 */           this.numOfFailure += 1;
/* 212 */           test.setHeader(rule, lexicalRule, treeRule, this.numOfTest, input.line);
/* 213 */           this.failures.add(test);
/* 214 */           onFail(test); }  }  } gUnitTestSuite ts;
/*     */     String rule;
/*     */     String lexicalRule;
/*     */     String treeRule;
/*     */     Iterator i$; } 
/* 223 */   protected gUnitTestResult runLexer(String lexerName, String testRuleName, gUnitTestInput testInput) throws Exception { Class lexer = null;
/* 224 */     PrintStream ps = null;
/* 225 */     PrintStream ps2 = null;
/*     */     try
/*     */     {
/* 228 */       CharStream input = getANTLRInputStream(testInput);
/*     */ 
/* 231 */       lexer = classForName(lexerName);
/* 232 */       Class[] lexArgTypes = { CharStream.class };
/* 233 */       lexConstructor = lexer.getConstructor(lexArgTypes);
/* 234 */       Object[] lexArgs = { input };
/* 235 */       Object lexObj = lexConstructor.newInstance(lexArgs);
/*     */ 
/* 237 */       Method ruleName = lexer.getMethod("m" + testRuleName, new Class[0]);
/*     */ 
/* 240 */       ByteArrayOutputStream out = new ByteArrayOutputStream();
/* 241 */       ByteArrayOutputStream err = new ByteArrayOutputStream();
/* 242 */       ps = new PrintStream(out);
/* 243 */       ps2 = new PrintStream(err);
/* 244 */       System.setOut(ps);
/* 245 */       System.setErr(ps2);
/*     */ 
/* 249 */       ruleName.invoke(lexObj, new Object[0]);
/* 250 */       Method ruleName2 = lexer.getMethod("getCharIndex", new Class[0]);
/* 251 */       int currentIndex = ((Integer)ruleName2.invoke(lexObj, new Object[0])).intValue();
/* 252 */       if (currentIndex != input.size())
/* 253 */         ps2.print("extra text found, '" + input.substring(currentIndex, input.size() - 1) + "'");
/*     */       gUnitTestResult localgUnitTestResult1;
/* 256 */       if (err.toString().length() > 0) {
/* 257 */         gUnitTestResult testResult = new gUnitTestResult(false, err.toString(), true);
/* 258 */         testResult.setError(err.toString());
/* 259 */         return testResult;
/*     */       }
/* 261 */       String stdout = null;
/* 262 */       if (out.toString().length() > 0) {
/* 263 */         stdout = out.toString();
/*     */       }
/* 265 */       return new gUnitTestResult(true, stdout, true);
/*     */     } catch (IOException e) {
/* 267 */       return getTestExceptionResult(e);
/*     */     } catch (ClassNotFoundException e) {
/* 269 */       e.printStackTrace(); System.exit(1);
/*     */     } catch (SecurityException e) {
/* 271 */       e.printStackTrace(); System.exit(1);
/*     */     } catch (NoSuchMethodException e) {
/* 273 */       e.printStackTrace(); System.exit(1);
/*     */     } catch (IllegalArgumentException e) {
/* 275 */       e.printStackTrace(); System.exit(1);
/*     */     } catch (InstantiationException e) {
/* 277 */       e.printStackTrace(); System.exit(1);
/*     */     } catch (IllegalAccessException e) {
/* 279 */       e.printStackTrace(); System.exit(1);
/*     */     }
/*     */     catch (InvocationTargetException e)
/*     */     {
/*     */       Constructor lexConstructor;
/* 281 */       return getTestExceptionResult(e);
/*     */     } finally {
/*     */       try {
/* 284 */         if (ps != null) ps.close();
/* 285 */         if (ps2 != null) ps2.close();
/* 286 */         System.setOut(this.console);
/* 287 */         System.setErr(this.consoleErr);
/*     */       } catch (Exception e) {
/* 289 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */ 
/* 293 */     throw new Exception("This should be unreachable?");
/*     */   }
/*     */ 
/*     */   protected gUnitTestResult runParser(String parserName, String lexerName, String testRuleName, gUnitTestInput testInput)
/*     */     throws Exception
/*     */   {
/* 299 */     Class lexer = null;
/* 300 */     Class parser = null;
/* 301 */     PrintStream ps = null;
/* 302 */     PrintStream ps2 = null;
/*     */     try
/*     */     {
/* 305 */       CharStream input = getANTLRInputStream(testInput);
/*     */ 
/* 308 */       lexer = classForName(lexerName);
/* 309 */       Class[] lexArgTypes = { CharStream.class };
/* 310 */       lexConstructor = lexer.getConstructor(lexArgTypes);
/* 311 */       Object[] lexArgs = { input };
/* 312 */       Object lexObj = lexConstructor.newInstance(lexArgs);
/*     */ 
/* 314 */       CommonTokenStream tokens = new CommonTokenStream((Lexer)lexObj);
/*     */ 
/* 316 */       parser = classForName(parserName);
/* 317 */       Class[] parArgTypes = { TokenStream.class };
/* 318 */       Constructor parConstructor = parser.getConstructor(parArgTypes);
/* 319 */       Object[] parArgs = { tokens };
/* 320 */       Object parObj = parConstructor.newInstance(parArgs);
/*     */ 
/* 323 */       if (this.grammarInfo.getAdaptor() != null) {
/* 324 */         parArgTypes = new Class[] { TreeAdaptor.class };
/* 325 */         Method _setTreeAdaptor = parser.getMethod("setTreeAdaptor", parArgTypes);
/* 326 */         Class _treeAdaptor = classForName(this.grammarInfo.getAdaptor());
/* 327 */         _setTreeAdaptor.invoke(parObj, new Object[] { _treeAdaptor.newInstance() });
/*     */       }
/*     */ 
/* 330 */       Method ruleName = parser.getMethod(testRuleName, new Class[0]);
/*     */ 
/* 333 */       ByteArrayOutputStream out = new ByteArrayOutputStream();
/* 334 */       ByteArrayOutputStream err = new ByteArrayOutputStream();
/* 335 */       ps = new PrintStream(out);
/* 336 */       ps2 = new PrintStream(err);
/* 337 */       System.setOut(ps);
/* 338 */       System.setErr(ps2);
/*     */ 
/* 342 */       Object ruleReturn = ruleName.invoke(parObj, new Object[0]);
/* 343 */       String astString = null;
/* 344 */       String stString = null;
/*     */       Method[] methods;
/* 346 */       if ((ruleReturn != null) && 
/* 347 */         (ruleReturn.getClass().toString().indexOf(testRuleName + "_return") > 0)) {
/*     */         try {
/* 349 */           Class _return = classForName(parserName + "$" + testRuleName + "_return");
/* 350 */           methods = _return.getDeclaredMethods();
/* 351 */           Method[] arr$ = methods; int len$ = arr$.length; for (int i$ = 0; i$ < len$; i$++) { Method method = arr$[i$];
/* 352 */             if (method.getName().equals("getTree")) {
/* 353 */               Method returnName = _return.getMethod("getTree", new Class[0]);
/* 354 */               CommonTree tree = (CommonTree)returnName.invoke(ruleReturn, new Object[0]);
/* 355 */               astString = tree.toStringTree();
/*     */             }
/* 357 */             else if (method.getName().equals("getTemplate")) {
/* 358 */               Method returnName = _return.getMethod("getTemplate", new Class[0]);
/* 359 */               StringTemplate st = (StringTemplate)returnName.invoke(ruleReturn, new Object[0]);
/* 360 */               stString = st.toString();
/*     */             } }
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 365 */           System.err.println(e);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 371 */       if (tokens.index() != tokens.size())
/*     */       {
/* 373 */         ps2.print("Invalid input");
/*     */       }
/*     */ 
/* 376 */       if (err.toString().length() > 0) {
/* 377 */         gUnitTestResult testResult = new gUnitTestResult(false, err.toString());
/* 378 */         testResult.setError(err.toString());
/* 379 */         return testResult;
/*     */       }
/* 381 */       String stdout = null;
/*     */ 
/* 383 */       if (out.toString().length() > 0) {
/* 384 */         stdout = out.toString();
/*     */       }
/* 386 */       if (astString != null) {
/* 387 */         return new gUnitTestResult(true, stdout, astString);
/*     */       }
/* 389 */       if (stString != null) {
/* 390 */         return new gUnitTestResult(true, stdout, stString);
/*     */       }
/*     */ 
/* 393 */       if (ruleReturn != null)
/*     */       {
/* 395 */         return new gUnitTestResult(true, stdout, String.valueOf(ruleReturn));
/*     */       }
/* 397 */       return new gUnitTestResult(true, stdout, stdout);
/*     */     } catch (IOException e) {
/* 399 */       return getTestExceptionResult(e);
/*     */     } catch (ClassNotFoundException e) {
/* 401 */       e.printStackTrace(); System.exit(1);
/*     */     } catch (SecurityException e) {
/* 403 */       e.printStackTrace(); System.exit(1);
/*     */     } catch (NoSuchMethodException e) {
/* 405 */       e.printStackTrace(); System.exit(1);
/*     */     } catch (IllegalArgumentException e) {
/* 407 */       e.printStackTrace(); System.exit(1);
/*     */     } catch (InstantiationException e) {
/* 409 */       e.printStackTrace(); System.exit(1);
/*     */     } catch (IllegalAccessException e) {
/* 411 */       e.printStackTrace(); System.exit(1);
/*     */     }
/*     */     catch (InvocationTargetException e)
/*     */     {
/*     */       Constructor lexConstructor;
/* 413 */       return getTestExceptionResult(e);
/*     */     } finally {
/*     */       try {
/* 416 */         if (ps != null) ps.close();
/* 417 */         if (ps2 != null) ps2.close();
/* 418 */         System.setOut(this.console);
/* 419 */         System.setErr(this.consoleErr);
/*     */       } catch (Exception e) {
/* 421 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */ 
/* 425 */     throw new Exception("This should be unreachable?");
/*     */   }
/*     */ 
/*     */   protected gUnitTestResult runTreeParser(String parserName, String lexerName, String testRuleName, String testTreeRuleName, gUnitTestInput testInput)
/*     */     throws Exception
/*     */   {
/* 431 */     Class lexer = null;
/* 432 */     Class parser = null;
/* 433 */     Class treeParser = null;
/* 434 */     PrintStream ps = null;
/* 435 */     PrintStream ps2 = null;
/*     */     try
/*     */     {
/* 438 */       CharStream input = getANTLRInputStream(testInput);
/*     */       String treeParserPath;
/*     */       String treeParserPath;
/* 441 */       if (this.grammarInfo.getGrammarPackage() != null) {
/* 442 */         treeParserPath = this.grammarInfo.getGrammarPackage() + "." + this.grammarInfo.getTreeGrammarName();
/*     */       }
/*     */       else {
/* 445 */         treeParserPath = this.grammarInfo.getTreeGrammarName();
/*     */       }
/*     */ 
/* 449 */       lexer = classForName(lexerName);
/* 450 */       Class[] lexArgTypes = { CharStream.class };
/* 451 */       lexConstructor = lexer.getConstructor(lexArgTypes);
/* 452 */       Object[] lexArgs = { input };
/* 453 */       Object lexObj = lexConstructor.newInstance(lexArgs);
/*     */ 
/* 455 */       CommonTokenStream tokens = new CommonTokenStream((Lexer)lexObj);
/*     */ 
/* 457 */       parser = classForName(parserName);
/* 458 */       Class[] parArgTypes = { TokenStream.class };
/* 459 */       Constructor parConstructor = parser.getConstructor(parArgTypes);
/* 460 */       Object[] parArgs = { tokens };
/* 461 */       Object parObj = parConstructor.newInstance(parArgs);
/*     */ 
/* 464 */       TreeAdaptor customTreeAdaptor = null;
/* 465 */       if (this.grammarInfo.getAdaptor() != null) {
/* 466 */         parArgTypes = new Class[] { TreeAdaptor.class };
/* 467 */         Method _setTreeAdaptor = parser.getMethod("setTreeAdaptor", parArgTypes);
/* 468 */         Class _treeAdaptor = classForName(this.grammarInfo.getAdaptor());
/* 469 */         customTreeAdaptor = (TreeAdaptor)_treeAdaptor.newInstance();
/* 470 */         _setTreeAdaptor.invoke(parObj, new Object[] { customTreeAdaptor });
/*     */       }
/*     */ 
/* 473 */       Method ruleName = parser.getMethod(testRuleName, new Class[0]);
/*     */ 
/* 476 */       ByteArrayOutputStream out = new ByteArrayOutputStream();
/* 477 */       ByteArrayOutputStream err = new ByteArrayOutputStream();
/* 478 */       ps = new PrintStream(out);
/* 479 */       ps2 = new PrintStream(err);
/* 480 */       System.setOut(ps);
/* 481 */       System.setErr(ps2);
/*     */ 
/* 485 */       Object ruleReturn = ruleName.invoke(parObj, new Object[0]);
/*     */ 
/* 487 */       Class _return = classForName(parserName + "$" + testRuleName + "_return");
/* 488 */       Method returnName = _return.getMethod("getTree", new Class[0]);
/* 489 */       CommonTree tree = (CommonTree)returnName.invoke(ruleReturn, new Object[0]);
/*     */       CommonTreeNodeStream nodes;
/*     */       CommonTreeNodeStream nodes;
/* 493 */       if (customTreeAdaptor != null) {
/* 494 */         nodes = new CommonTreeNodeStream(customTreeAdaptor, tree);
/*     */       }
/*     */       else {
/* 497 */         nodes = new CommonTreeNodeStream(tree);
/*     */       }
/*     */ 
/* 500 */       nodes.setTokenStream(tokens);
/*     */ 
/* 502 */       treeParser = classForName(treeParserPath);
/* 503 */       Class[] treeParArgTypes = { TreeNodeStream.class };
/* 504 */       Constructor treeParConstructor = treeParser.getConstructor(treeParArgTypes);
/* 505 */       Object[] treeParArgs = { nodes };
/* 506 */       Object treeParObj = treeParConstructor.newInstance(treeParArgs);
/*     */ 
/* 508 */       Method treeRuleName = treeParser.getMethod(testTreeRuleName, new Class[0]);
/* 509 */       Object treeRuleReturn = treeRuleName.invoke(treeParObj, new Object[0]);
/*     */ 
/* 511 */       String astString = null;
/* 512 */       String stString = null;
/*     */       Method[] methods;
/* 514 */       if ((treeRuleReturn != null) && 
/* 515 */         (treeRuleReturn.getClass().toString().indexOf(testTreeRuleName + "_return") > 0)) {
/*     */         try {
/* 517 */           Class _treeReturn = classForName(treeParserPath + "$" + testTreeRuleName + "_return");
/* 518 */           methods = _treeReturn.getDeclaredMethods();
/* 519 */           Method[] arr$ = methods; int len$ = arr$.length; for (int i$ = 0; i$ < len$; i$++) { Method method = arr$[i$];
/* 520 */             if (method.getName().equals("getTree")) {
/* 521 */               Method treeReturnName = _treeReturn.getMethod("getTree", new Class[0]);
/* 522 */               CommonTree returnTree = (CommonTree)treeReturnName.invoke(treeRuleReturn, new Object[0]);
/* 523 */               astString = returnTree.toStringTree();
/*     */             }
/* 525 */             else if (method.getName().equals("getTemplate")) {
/* 526 */               Method treeReturnName = _return.getMethod("getTemplate", new Class[0]);
/* 527 */               StringTemplate st = (StringTemplate)treeReturnName.invoke(treeRuleReturn, new Object[0]);
/* 528 */               stString = st.toString();
/*     */             } }
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 533 */           System.err.println(e);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 539 */       if (tokens.index() != tokens.size())
/*     */       {
/* 541 */         ps2.print("Invalid input");
/*     */       }
/*     */ 
/* 544 */       if (err.toString().length() > 0) {
/* 545 */         gUnitTestResult testResult = new gUnitTestResult(false, err.toString());
/* 546 */         testResult.setError(err.toString());
/* 547 */         return testResult;
/*     */       }
/*     */ 
/* 550 */       String stdout = null;
/*     */ 
/* 552 */       if (out.toString().length() > 0) {
/* 553 */         stdout = out.toString();
/*     */       }
/* 555 */       if (astString != null) {
/* 556 */         return new gUnitTestResult(true, stdout, astString);
/*     */       }
/* 558 */       if (stString != null) {
/* 559 */         return new gUnitTestResult(true, stdout, stString);
/*     */       }
/*     */ 
/* 562 */       if (treeRuleReturn != null)
/*     */       {
/* 564 */         return new gUnitTestResult(true, stdout, String.valueOf(treeRuleReturn));
/*     */       }
/* 566 */       return new gUnitTestResult(true, stdout, stdout);
/*     */     } catch (IOException e) {
/* 568 */       return getTestExceptionResult(e);
/*     */     } catch (ClassNotFoundException e) {
/* 570 */       e.printStackTrace(); System.exit(1);
/*     */     } catch (SecurityException e) {
/* 572 */       e.printStackTrace(); System.exit(1);
/*     */     } catch (NoSuchMethodException e) {
/* 574 */       e.printStackTrace(); System.exit(1);
/*     */     } catch (IllegalArgumentException e) {
/* 576 */       e.printStackTrace(); System.exit(1);
/*     */     } catch (InstantiationException e) {
/* 578 */       e.printStackTrace(); System.exit(1);
/*     */     } catch (IllegalAccessException e) {
/* 580 */       e.printStackTrace(); System.exit(1);
/*     */     }
/*     */     catch (InvocationTargetException e)
/*     */     {
/*     */       Constructor lexConstructor;
/* 582 */       return getTestExceptionResult(e);
/*     */     } finally {
/*     */       try {
/* 585 */         if (ps != null) ps.close();
/* 586 */         if (ps2 != null) ps2.close();
/* 587 */         System.setOut(this.console);
/* 588 */         System.setErr(this.consoleErr);
/*     */       } catch (Exception e) {
/* 590 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */ 
/* 594 */     throw new Exception("Should not be reachable?");
/*     */   }
/*     */ 
/*     */   private CharStream getANTLRInputStream(gUnitTestInput testInput)
/*     */     throws IOException
/*     */   {
/*     */     CharStream input;
/*     */     CharStream input;
/* 600 */     if (testInput.isFile) {
/* 601 */       String filePath = testInput.input;
/* 602 */       File testInputFile = new File(filePath);
/*     */ 
/* 604 */       if (!testInputFile.exists()) {
/* 605 */         testInputFile = new File(this.testsuiteDir, filePath);
/* 606 */         if (testInputFile.exists()) { filePath = testInputFile.getCanonicalPath(); }
/* 608 */         else if (this.grammarInfo.getGrammarPackage() != null) {
/* 609 */           testInputFile = new File("." + File.separator + this.grammarInfo.getGrammarPackage().replace(".", File.separator), filePath);
/* 610 */           if (testInputFile.exists()) filePath = testInputFile.getCanonicalPath();
/*     */         }
/*     */       }
/* 613 */       input = new ANTLRFileStream(filePath);
/*     */     }
/*     */     else {
/* 616 */       input = new ANTLRStringStream(testInput.input);
/*     */     }
/* 618 */     return input;
/*     */   }
/*     */ 
/*     */   private gUnitTestResult getTestExceptionResult(Exception e)
/*     */   {
/*     */     gUnitTestResult testResult;
/* 624 */     if (e.getCause() != null) {
/* 625 */       gUnitTestResult testResult = new gUnitTestResult(false, e.getCause().toString(), true);
/* 626 */       testResult.setError(e.getCause().toString());
/*     */     }
/*     */     else {
/* 629 */       testResult = new gUnitTestResult(false, e.toString(), true);
/* 630 */       testResult.setError(e.toString());
/*     */     }
/* 632 */     return testResult;
/*     */   }
/*     */ 
/*     */   public void onPass(ITestCase passTest)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void onFail(ITestCase failTest)
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.gUnitExecutor
 * JD-Core Version:    0.6.2
 */