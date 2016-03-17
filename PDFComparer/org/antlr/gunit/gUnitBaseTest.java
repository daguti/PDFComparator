/*     */ package org.antlr.gunit;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import junit.framework.TestCase;
/*     */ import org.antlr.runtime.ANTLRFileStream;
/*     */ import org.antlr.runtime.ANTLRStringStream;
/*     */ import org.antlr.runtime.CharStream;
/*     */ import org.antlr.runtime.CommonTokenStream;
/*     */ import org.antlr.runtime.Lexer;
/*     */ import org.antlr.runtime.Parser;
/*     */ import org.antlr.runtime.TokenStream;
/*     */ import org.antlr.runtime.tree.CommonTree;
/*     */ import org.antlr.runtime.tree.CommonTreeNodeStream;
/*     */ import org.antlr.runtime.tree.TreeAdaptor;
/*     */ import org.antlr.runtime.tree.TreeNodeStream;
/*     */ import org.antlr.stringtemplate.StringTemplate;
/*     */ 
/*     */ public abstract class gUnitBaseTest extends TestCase
/*     */ {
/*     */   public String treeAdaptorPath;
/*     */   public String packagePath;
/*     */   public String lexerPath;
/*     */   public String parserPath;
/*     */   public String treeParserPath;
/*     */   protected String stdout;
/*     */   protected String stderr;
/*  66 */   private PrintStream console = System.out;
/*  67 */   private PrintStream consoleErr = System.err;
/*     */ 
/*     */   public String execLexer(String testRuleName, int line, String testInput, boolean isFile)
/*     */     throws Exception
/*     */   {
/*     */     CharStream input;
/*     */     CharStream input;
/*  73 */     if (isFile) {
/*  74 */       String filePath = testInput;
/*  75 */       File testInputFile = new File(filePath);
/*     */ 
/*  77 */       if ((!testInputFile.exists()) && (this.packagePath != null)) {
/*  78 */         testInputFile = new File(this.packagePath, filePath);
/*  79 */         if (testInputFile.exists()) filePath = testInputFile.getCanonicalPath();
/*     */       }
/*  81 */       input = new ANTLRFileStream(filePath);
/*     */     }
/*     */     else {
/*  84 */       input = new ANTLRStringStream(testInput);
/*     */     }
/*  86 */     Class lexer = null;
/*  87 */     PrintStream ps = null;
/*  88 */     PrintStream ps2 = null;
/*     */     try
/*     */     {
/*  91 */       lexer = Class.forName(this.lexerPath);
/*  92 */       Class[] lexArgTypes = { CharStream.class };
/*  93 */       lexConstructor = lexer.getConstructor(lexArgTypes);
/*  94 */       Object[] lexArgs = { input };
/*  95 */       Lexer lexObj = (Lexer)lexConstructor.newInstance(lexArgs);
/*  96 */       input.setLine(line);
/*     */ 
/*  98 */       Method ruleName = lexer.getMethod("m" + testRuleName, new Class[0]);
/*     */ 
/* 101 */       ByteArrayOutputStream out = new ByteArrayOutputStream();
/* 102 */       ByteArrayOutputStream err = new ByteArrayOutputStream();
/* 103 */       ps = new PrintStream(out);
/* 104 */       ps2 = new PrintStream(err);
/* 105 */       System.setOut(ps);
/* 106 */       System.setErr(ps2);
/*     */ 
/* 110 */       ruleName.invoke(lexObj, new Object[0]);
/* 111 */       Method ruleName2 = lexer.getMethod("getCharIndex", new Class[0]);
/* 112 */       int currentIndex = ((Integer)ruleName2.invoke(lexObj, new Object[0])).intValue();
/* 113 */       if (currentIndex != input.size()) {
/* 114 */         ps2.println("extra text found, '" + input.substring(currentIndex, input.size() - 1) + "'");
/*     */       }
/*     */ 
/* 117 */       this.stdout = null;
/* 118 */       this.stderr = null;
/*     */       String str1;
/* 120 */       if (err.toString().length() > 0) {
/* 121 */         this.stderr = err.toString();
/* 122 */         return this.stderr;
/*     */       }
/* 124 */       if (out.toString().length() > 0) {
/* 125 */         this.stdout = out.toString();
/*     */       }
/* 127 */       if ((err.toString().length() == 0) && (out.toString().length() == 0))
/* 128 */         return null;
/*     */     }
/*     */     catch (ClassNotFoundException e) {
/* 131 */       e.printStackTrace(); System.exit(1);
/*     */     } catch (SecurityException e) {
/* 133 */       e.printStackTrace(); System.exit(1);
/*     */     } catch (NoSuchMethodException e) {
/* 135 */       e.printStackTrace(); System.exit(1);
/*     */     } catch (IllegalArgumentException e) {
/* 137 */       e.printStackTrace(); System.exit(1);
/*     */     } catch (InstantiationException e) {
/* 139 */       e.printStackTrace(); System.exit(1);
/*     */     } catch (IllegalAccessException e) {
/* 141 */       e.printStackTrace(); System.exit(1);
/*     */     }
/*     */     catch (InvocationTargetException e)
/*     */     {
/* 143 */       Constructor lexConstructor;
/* 143 */       if (e.getCause() != null) this.stderr = e.getCause().toString(); else
/* 144 */         this.stderr = e.toString();
/* 145 */       return this.stderr;
/*     */     } finally {
/*     */       try {
/* 148 */         if (ps != null) ps.close();
/* 149 */         if (ps2 != null) ps2.close();
/* 150 */         System.setOut(this.console);
/* 151 */         System.setErr(this.consoleErr);
/*     */       } catch (Exception e) {
/* 153 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 156 */     return this.stdout;
/*     */   }
/*     */ 
/*     */   public Object execParser(String testRuleName, int line, String testInput, boolean isFile)
/*     */     throws Exception
/*     */   {
/*     */     CharStream input;
/*     */     CharStream input;
/* 163 */     if (isFile) {
/* 164 */       String filePath = testInput;
/* 165 */       File testInputFile = new File(filePath);
/*     */ 
/* 167 */       if ((!testInputFile.exists()) && (this.packagePath != null)) {
/* 168 */         testInputFile = new File(this.packagePath, filePath);
/* 169 */         if (testInputFile.exists()) filePath = testInputFile.getCanonicalPath();
/*     */       }
/* 171 */       input = new ANTLRFileStream(filePath);
/*     */     }
/*     */     else {
/* 174 */       input = new ANTLRStringStream(testInput);
/*     */     }
/* 176 */     Class lexer = null;
/* 177 */     Class parser = null;
/* 178 */     PrintStream ps = null;
/* 179 */     PrintStream ps2 = null;
/* 180 */     ByteArrayOutputStream out = null;
/* 181 */     ByteArrayOutputStream err = null;
/*     */     try
/*     */     {
/* 184 */       lexer = Class.forName(this.lexerPath);
/* 185 */       Class[] lexArgTypes = { CharStream.class };
/* 186 */       lexConstructor = lexer.getConstructor(lexArgTypes);
/* 187 */       Object[] lexArgs = { input };
/* 188 */       Lexer lexObj = (Lexer)lexConstructor.newInstance(lexArgs);
/* 189 */       input.setLine(line);
/*     */ 
/* 191 */       CommonTokenStream tokens = new CommonTokenStream(lexObj);
/* 192 */       parser = Class.forName(this.parserPath);
/* 193 */       Class[] parArgTypes = { TokenStream.class };
/* 194 */       Constructor parConstructor = parser.getConstructor(parArgTypes);
/* 195 */       Object[] parArgs = { tokens };
/* 196 */       Parser parObj = (Parser)parConstructor.newInstance(parArgs);
/*     */ 
/* 199 */       if (this.treeAdaptorPath != null) {
/* 200 */         parArgTypes = new Class[] { TreeAdaptor.class };
/* 201 */         Method _setTreeAdaptor = parser.getMethod("setTreeAdaptor", parArgTypes);
/* 202 */         Class _treeAdaptor = Class.forName(this.treeAdaptorPath);
/* 203 */         _setTreeAdaptor.invoke(parObj, new Object[] { _treeAdaptor.newInstance() });
/*     */       }
/*     */ 
/* 206 */       Method ruleName = parser.getMethod(testRuleName, new Class[0]);
/*     */ 
/* 209 */       out = new ByteArrayOutputStream();
/* 210 */       err = new ByteArrayOutputStream();
/* 211 */       ps = new PrintStream(out);
/* 212 */       ps2 = new PrintStream(err);
/* 213 */       System.setOut(ps);
/* 214 */       System.setErr(ps2);
/*     */ 
/* 218 */       Object ruleReturn = ruleName.invoke(parObj, new Object[0]);
/* 219 */       String astString = null;
/* 220 */       String stString = null;
/*     */ 
/* 222 */       if ((ruleReturn != null) && 
/* 223 */         (ruleReturn.getClass().toString().indexOf(testRuleName + "_return") > 0)) {
/*     */         try {
/* 225 */           Class _return = Class.forName(this.parserPath + "$" + testRuleName + "_return");
/* 226 */           Method[] methods = _return.getDeclaredMethods();
/* 227 */           Method[] arr$ = methods; int len$ = arr$.length; for (int i$ = 0; i$ < len$; i$++) { Method method = arr$[i$];
/* 228 */             if (method.getName().equals("getTree")) {
/* 229 */               Method returnName = _return.getMethod("getTree", new Class[0]);
/* 230 */               CommonTree tree = (CommonTree)returnName.invoke(ruleReturn, new Object[0]);
/* 231 */               astString = tree.toStringTree();
/*     */             }
/* 233 */             else if (method.getName().equals("getTemplate")) {
/* 234 */               Method returnName = _return.getMethod("getTemplate", new Class[0]);
/* 235 */               StringTemplate st = (StringTemplate)returnName.invoke(ruleReturn, new Object[0]);
/* 236 */               stString = st.toString();
/*     */             } }
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 241 */           System.err.println(e);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 246 */       this.stdout = "";
/* 247 */       this.stderr = "";
/*     */ 
/* 250 */       if (tokens.index() != tokens.size())
/*     */       {
/* 252 */         this.stderr = (this.stderr + "Stopped parsing at token index " + tokens.index() + ": ");
/*     */       }
/*     */ 
/* 256 */       this.stdout += out.toString();
/* 257 */       this.stderr += err.toString();
/*     */ 
/* 259 */       if (err.toString().length() > 0) return this.stderr;
/* 260 */       if (out.toString().length() > 0) return this.stdout;
/* 261 */       if (astString != null) {
/* 262 */         return astString;
/*     */       }
/* 264 */       if (stString != null) {
/* 265 */         return stString;
/*     */       }
/* 267 */       if (ruleReturn != null) {
/* 268 */         return ruleReturn;
/*     */       }
/* 270 */       if ((err.toString().length() == 0) && (out.toString().length() == 0))
/* 271 */         return null;
/*     */     }
/*     */     catch (ClassNotFoundException e)
/*     */     {
/* 275 */       e.printStackTrace(); System.exit(1);
/*     */     }
/*     */     catch (SecurityException e) {
/* 278 */       e.printStackTrace(); System.exit(1);
/*     */     }
/*     */     catch (NoSuchMethodException e) {
/* 281 */       e.printStackTrace(); System.exit(1);
/*     */     }
/*     */     catch (IllegalAccessException e) {
/* 284 */       e.printStackTrace(); System.exit(1);
/*     */     }
/*     */     catch (InvocationTargetException e)
/*     */     {
/* 290 */       Constructor lexConstructor;
/* 287 */       this.stdout = out.toString();
/* 288 */       this.stderr = err.toString();
/*     */ 
/* 290 */       if (e.getCause() != null) this.stderr += e.getCause().toString(); else
/* 291 */         this.stderr += e.toString();
/* 292 */       return this.stderr;
/*     */     } finally {
/*     */       try {
/* 295 */         if (ps != null) ps.close();
/* 296 */         if (ps2 != null) ps2.close();
/* 297 */         System.setOut(this.console);
/* 298 */         System.setErr(this.consoleErr);
/*     */       } catch (Exception e) {
/* 300 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 303 */     return this.stdout;
/*     */   }
/*     */ 
/*     */   public Object execTreeParser(String testTreeRuleName, String testRuleName, String testInput, boolean isFile)
/*     */     throws Exception
/*     */   {
/*     */     CharStream input;
/*     */     CharStream input;
/* 309 */     if (isFile) {
/* 310 */       String filePath = testInput;
/* 311 */       File testInputFile = new File(filePath);
/*     */ 
/* 313 */       if ((!testInputFile.exists()) && (this.packagePath != null)) {
/* 314 */         testInputFile = new File(this.packagePath, filePath);
/* 315 */         if (testInputFile.exists()) filePath = testInputFile.getCanonicalPath();
/*     */       }
/* 317 */       input = new ANTLRFileStream(filePath);
/*     */     }
/*     */     else {
/* 320 */       input = new ANTLRStringStream(testInput);
/*     */     }
/* 322 */     Class lexer = null;
/* 323 */     Class parser = null;
/* 324 */     Class treeParser = null;
/* 325 */     PrintStream ps = null;
/* 326 */     PrintStream ps2 = null;
/*     */     try
/*     */     {
/* 329 */       lexer = Class.forName(this.lexerPath);
/* 330 */       Class[] lexArgTypes = { CharStream.class };
/* 331 */       lexConstructor = lexer.getConstructor(lexArgTypes);
/* 332 */       Object[] lexArgs = { input };
/* 333 */       Object lexObj = lexConstructor.newInstance(lexArgs);
/*     */ 
/* 335 */       CommonTokenStream tokens = new CommonTokenStream((Lexer)lexObj);
/*     */ 
/* 337 */       parser = Class.forName(this.parserPath);
/* 338 */       Class[] parArgTypes = { TokenStream.class };
/* 339 */       Constructor parConstructor = parser.getConstructor(parArgTypes);
/* 340 */       Object[] parArgs = { tokens };
/* 341 */       Object parObj = parConstructor.newInstance(parArgs);
/*     */ 
/* 344 */       TreeAdaptor customTreeAdaptor = null;
/* 345 */       if (this.treeAdaptorPath != null) {
/* 346 */         parArgTypes = new Class[] { TreeAdaptor.class };
/* 347 */         Method _setTreeAdaptor = parser.getMethod("setTreeAdaptor", parArgTypes);
/* 348 */         Class _treeAdaptor = Class.forName(this.treeAdaptorPath);
/* 349 */         customTreeAdaptor = (TreeAdaptor)_treeAdaptor.newInstance();
/* 350 */         _setTreeAdaptor.invoke(parObj, new Object[] { customTreeAdaptor });
/*     */       }
/*     */ 
/* 353 */       Method ruleName = parser.getMethod(testRuleName, new Class[0]);
/*     */ 
/* 356 */       ByteArrayOutputStream out = new ByteArrayOutputStream();
/* 357 */       ByteArrayOutputStream err = new ByteArrayOutputStream();
/* 358 */       ps = new PrintStream(out);
/* 359 */       ps2 = new PrintStream(err);
/* 360 */       System.setOut(ps);
/* 361 */       System.setErr(ps2);
/*     */ 
/* 365 */       Object ruleReturn = ruleName.invoke(parObj, new Object[0]);
/*     */ 
/* 367 */       Class _return = Class.forName(this.parserPath + "$" + testRuleName + "_return");
/* 368 */       Method returnName = _return.getMethod("getTree", new Class[0]);
/* 369 */       CommonTree tree = (CommonTree)returnName.invoke(ruleReturn, new Object[0]);
/*     */       CommonTreeNodeStream nodes;
/*     */       CommonTreeNodeStream nodes;
/* 373 */       if (customTreeAdaptor != null) {
/* 374 */         nodes = new CommonTreeNodeStream(customTreeAdaptor, tree);
/*     */       }
/*     */       else {
/* 377 */         nodes = new CommonTreeNodeStream(tree);
/*     */       }
/*     */ 
/* 380 */       nodes.setTokenStream(tokens);
/*     */ 
/* 382 */       treeParser = Class.forName(this.treeParserPath);
/* 383 */       Class[] treeParArgTypes = { TreeNodeStream.class };
/* 384 */       Constructor treeParConstructor = treeParser.getConstructor(treeParArgTypes);
/* 385 */       Object[] treeParArgs = { nodes };
/* 386 */       Object treeParObj = treeParConstructor.newInstance(treeParArgs);
/*     */ 
/* 388 */       Method treeRuleName = treeParser.getMethod(testTreeRuleName, new Class[0]);
/* 389 */       Object treeRuleReturn = treeRuleName.invoke(treeParObj, new Object[0]);
/*     */ 
/* 391 */       String astString = null;
/* 392 */       String stString = null;
/*     */ 
/* 394 */       if ((treeRuleReturn != null) && 
/* 395 */         (treeRuleReturn.getClass().toString().indexOf(testTreeRuleName + "_return") > 0)) {
/*     */         try {
/* 397 */           Class _treeReturn = Class.forName(this.treeParserPath + "$" + testTreeRuleName + "_return");
/* 398 */           Method[] methods = _treeReturn.getDeclaredMethods();
/* 399 */           Method[] arr$ = methods; int len$ = arr$.length; for (int i$ = 0; i$ < len$; i$++) { Method method = arr$[i$];
/* 400 */             if (method.getName().equals("getTree")) {
/* 401 */               Method treeReturnName = _treeReturn.getMethod("getTree", new Class[0]);
/* 402 */               CommonTree returnTree = (CommonTree)treeReturnName.invoke(treeRuleReturn, new Object[0]);
/* 403 */               astString = returnTree.toStringTree();
/*     */             }
/* 405 */             else if (method.getName().equals("getTemplate")) {
/* 406 */               Method treeReturnName = _return.getMethod("getTemplate", new Class[0]);
/* 407 */               StringTemplate st = (StringTemplate)treeReturnName.invoke(treeRuleReturn, new Object[0]);
/* 408 */               stString = st.toString();
/*     */             } }
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 413 */           System.err.println(e);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 418 */       this.stdout = null;
/* 419 */       this.stderr = null;
/*     */ 
/* 422 */       if (tokens.index() != tokens.size()) {
/* 423 */         throw new InvalidInputException();
/*     */       }
/*     */ 
/* 427 */       if (err.toString().length() > 0) {
/* 428 */         this.stderr = err.toString();
/* 429 */         return this.stderr;
/*     */       }
/* 431 */       if (out.toString().length() > 0) {
/* 432 */         this.stdout = out.toString();
/*     */       }
/* 434 */       if (astString != null) {
/* 435 */         return astString;
/*     */       }
/* 437 */       if (stString != null) {
/* 438 */         return stString;
/*     */       }
/* 440 */       if (treeRuleReturn != null) {
/* 441 */         return treeRuleReturn;
/*     */       }
/* 443 */       if ((err.toString().length() == 0) && (out.toString().length() == 0))
/* 444 */         return null;
/*     */     }
/*     */     catch (ClassNotFoundException e) {
/* 447 */       e.printStackTrace(); System.exit(1);
/*     */     } catch (SecurityException e) {
/* 449 */       e.printStackTrace(); System.exit(1);
/*     */     } catch (NoSuchMethodException e) {
/* 451 */       e.printStackTrace(); System.exit(1);
/*     */     } catch (IllegalAccessException e) {
/* 453 */       e.printStackTrace(); System.exit(1);
/*     */     }
/*     */     catch (InvocationTargetException e)
/*     */     {
/* 455 */       Constructor lexConstructor;
/* 455 */       if (e.getCause() != null) this.stderr = e.getCause().toString(); else
/* 456 */         this.stderr = e.toString();
/* 457 */       return this.stderr;
/*     */     } finally {
/*     */       try {
/* 460 */         if (ps != null) ps.close();
/* 461 */         if (ps2 != null) ps2.close();
/* 462 */         System.setOut(this.console);
/* 463 */         System.setErr(this.consoleErr);
/*     */       } catch (Exception e) {
/* 465 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 468 */     return this.stdout;
/*     */   }
/*     */ 
/*     */   public Object examineExecResult(int tokenType, Object retVal)
/*     */   {
/* 473 */     if (tokenType == 4) {
/* 474 */       if (this.stderr == null) {
/* 475 */         return "OK";
/*     */       }
/*     */ 
/* 478 */       return "FAIL, " + this.stderr;
/*     */     }
/*     */ 
/* 481 */     if (tokenType == 5) {
/* 482 */       if (this.stderr != null) {
/* 483 */         return "FAIL";
/*     */       }
/*     */ 
/* 486 */       return "OK";
/*     */     }
/*     */ 
/* 490 */     return retVal;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.gUnitBaseTest
 * JD-Core Version:    0.6.2
 */