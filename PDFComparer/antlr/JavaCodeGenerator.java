/*      */ package antlr;
/*      */ 
/*      */ import antlr.actions.java.ActionLexer;
/*      */ import antlr.collections.impl.BitSet;
/*      */ import antlr.collections.impl.Vector;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ 
/*      */ public class JavaCodeGenerator extends CodeGenerator
/*      */ {
/*      */   public static final int NO_MAPPING = -999;
/*      */   public static final int CONTINUE_LAST_MAPPING = -888;
/*      */   private JavaCodeGeneratorPrintWriterManager printWriterManager;
/*   30 */   private int defaultLine = -999;
/*      */ 
/*   32 */   protected int syntacticPredLevel = 0;
/*      */ 
/*   35 */   protected boolean genAST = false;
/*      */ 
/*   38 */   protected boolean saveText = false;
/*      */   String labeledElementType;
/*      */   String labeledElementASTType;
/*      */   String labeledElementInit;
/*      */   String commonExtraArgs;
/*      */   String commonExtraParams;
/*      */   String commonLocalVars;
/*      */   String lt1Value;
/*      */   String exceptionThrown;
/*      */   String throwNoViable;
/*      */   RuleBlock currentRule;
/*      */   String currentASTResult;
/*   62 */   Hashtable treeVariableMap = new Hashtable();
/*      */ 
/*   67 */   Hashtable declaredASTVariables = new Hashtable();
/*      */ 
/*   70 */   int astVarNumber = 1;
/*      */ 
/*   73 */   protected static final String NONUNIQUE = new String();
/*      */   public static final int caseSizeThreshold = 127;
/*      */   private Vector semPreds;
/*      */ 
/*      */   public JavaCodeGenerator()
/*      */   {
/*   85 */     this.charFormatter = new JavaCharFormatter();
/*      */   }
/*      */ 
/*      */   protected void printAction(String paramString) {
/*   89 */     printAction(paramString, this.defaultLine);
/*      */   }
/*      */   protected void printAction(String paramString, int paramInt) {
/*   92 */     getPrintWriterManager().startMapping(paramInt);
/*   93 */     super.printAction(paramString);
/*   94 */     getPrintWriterManager().endMapping();
/*      */   }
/*      */ 
/*      */   public void println(String paramString) {
/*   98 */     println(paramString, this.defaultLine);
/*      */   }
/*      */   public void println(String paramString, int paramInt) {
/*  101 */     if ((paramInt > 0) || (paramInt == -888))
/*  102 */       getPrintWriterManager().startSingleSourceLineMapping(paramInt);
/*  103 */     super.println(paramString);
/*  104 */     if ((paramInt > 0) || (paramInt == -888))
/*  105 */       getPrintWriterManager().endMapping();
/*      */   }
/*      */ 
/*      */   protected void print(String paramString) {
/*  109 */     print(paramString, this.defaultLine);
/*      */   }
/*      */   protected void print(String paramString, int paramInt) {
/*  112 */     if ((paramInt > 0) || (paramInt == -888))
/*  113 */       getPrintWriterManager().startMapping(paramInt);
/*  114 */     super.print(paramString);
/*  115 */     if ((paramInt > 0) || (paramInt == -888))
/*  116 */       getPrintWriterManager().endMapping();
/*      */   }
/*      */ 
/*      */   protected void _print(String paramString) {
/*  120 */     _print(paramString, this.defaultLine);
/*      */   }
/*      */   protected void _print(String paramString, int paramInt) {
/*  123 */     if ((paramInt > 0) || (paramInt == -888))
/*  124 */       getPrintWriterManager().startMapping(paramInt);
/*  125 */     super._print(paramString);
/*  126 */     if ((paramInt > 0) || (paramInt == -888))
/*  127 */       getPrintWriterManager().endMapping();
/*      */   }
/*      */ 
/*      */   protected void _println(String paramString) {
/*  131 */     _println(paramString, this.defaultLine);
/*      */   }
/*      */   protected void _println(String paramString, int paramInt) {
/*  134 */     if ((paramInt > 0) || (paramInt == -888))
/*  135 */       getPrintWriterManager().startMapping(paramInt);
/*  136 */     super._println(paramString);
/*  137 */     if ((paramInt > 0) || (paramInt == -888))
/*  138 */       getPrintWriterManager().endMapping();
/*      */   }
/*      */ 
/*      */   protected int addSemPred(String paramString)
/*      */   {
/*  147 */     this.semPreds.appendElement(paramString);
/*  148 */     return this.semPreds.size() - 1;
/*      */   }
/*      */ 
/*      */   public void exitIfError() {
/*  152 */     if (this.antlrTool.hasError())
/*  153 */       this.antlrTool.fatalError("Exiting due to errors.");
/*      */   }
/*      */ 
/*      */   public void gen()
/*      */   {
/*      */     try
/*      */     {
/*  162 */       Enumeration localEnumeration = this.behavior.grammars.elements();
/*  163 */       while (localEnumeration.hasMoreElements()) {
/*  164 */         localObject = (Grammar)localEnumeration.nextElement();
/*      */ 
/*  166 */         ((Grammar)localObject).setGrammarAnalyzer(this.analyzer);
/*  167 */         ((Grammar)localObject).setCodeGenerator(this);
/*  168 */         this.analyzer.setGrammar((Grammar)localObject);
/*      */ 
/*  170 */         setupGrammarParameters((Grammar)localObject);
/*  171 */         ((Grammar)localObject).generate();
/*      */ 
/*  174 */         exitIfError();
/*      */       }
/*      */ 
/*  178 */       Object localObject = this.behavior.tokenManagers.elements();
/*  179 */       while (((Enumeration)localObject).hasMoreElements()) {
/*  180 */         TokenManager localTokenManager = (TokenManager)((Enumeration)localObject).nextElement();
/*  181 */         if (!localTokenManager.isReadOnly())
/*      */         {
/*  185 */           genTokenTypes(localTokenManager);
/*      */ 
/*  187 */           genTokenInterchange(localTokenManager);
/*      */         }
/*  189 */         exitIfError();
/*      */       }
/*      */     }
/*      */     catch (IOException localIOException) {
/*  193 */       this.antlrTool.reportException(localIOException, null);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void gen(ActionElement paramActionElement)
/*      */   {
/*  201 */     int i = this.defaultLine;
/*      */     try {
/*  203 */       this.defaultLine = paramActionElement.getLine();
/*  204 */       if (this.DEBUG_CODE_GENERATOR) System.out.println("genAction(" + paramActionElement + ")");
/*  205 */       if (paramActionElement.isSemPred) {
/*  206 */         genSemPred(paramActionElement.actionText, paramActionElement.line);
/*      */       }
/*      */       else {
/*  209 */         if (this.grammar.hasSyntacticPredicate) {
/*  210 */           println("if ( inputState.guessing==0 ) {");
/*  211 */           this.tabs += 1;
/*      */         }
/*      */ 
/*  216 */         ActionTransInfo localActionTransInfo = new ActionTransInfo();
/*  217 */         String str = processActionForSpecialSymbols(paramActionElement.actionText, paramActionElement.getLine(), this.currentRule, localActionTransInfo);
/*      */ 
/*  222 */         if (localActionTransInfo.refRuleRoot != null)
/*      */         {
/*  227 */           println(localActionTransInfo.refRuleRoot + " = (" + this.labeledElementASTType + ")currentAST.root;");
/*      */         }
/*      */ 
/*  231 */         printAction(str);
/*      */ 
/*  233 */         if (localActionTransInfo.assignToRoot)
/*      */         {
/*  235 */           println("currentAST.root = " + localActionTransInfo.refRuleRoot + ";");
/*      */ 
/*  237 */           println("currentAST.child = " + localActionTransInfo.refRuleRoot + "!=null &&" + localActionTransInfo.refRuleRoot + ".getFirstChild()!=null ?", -999);
/*  238 */           this.tabs += 1;
/*  239 */           println(localActionTransInfo.refRuleRoot + ".getFirstChild() : " + localActionTransInfo.refRuleRoot + ";");
/*  240 */           this.tabs -= 1;
/*  241 */           println("currentAST.advanceChildToEnd();");
/*      */         }
/*      */ 
/*  244 */         if (this.grammar.hasSyntacticPredicate) {
/*  245 */           this.tabs -= 1;
/*  246 */           println("}", -999);
/*      */         }
/*      */       }
/*      */     } finally {
/*  250 */       this.defaultLine = i;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void gen(AlternativeBlock paramAlternativeBlock)
/*      */   {
/*  258 */     if (this.DEBUG_CODE_GENERATOR) System.out.println("gen(" + paramAlternativeBlock + ")");
/*  259 */     println("{", -999);
/*  260 */     genBlockPreamble(paramAlternativeBlock);
/*  261 */     genBlockInitAction(paramAlternativeBlock);
/*      */ 
/*  264 */     String str = this.currentASTResult;
/*  265 */     if (paramAlternativeBlock.getLabel() != null) {
/*  266 */       this.currentASTResult = paramAlternativeBlock.getLabel();
/*      */     }
/*      */ 
/*  269 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramAlternativeBlock);
/*      */ 
/*  271 */     JavaBlockFinishingInfo localJavaBlockFinishingInfo = genCommonBlock(paramAlternativeBlock, true);
/*  272 */     genBlockFinish(localJavaBlockFinishingInfo, this.throwNoViable, paramAlternativeBlock.getLine());
/*      */ 
/*  274 */     println("}", -999);
/*      */ 
/*  277 */     this.currentASTResult = str;
/*      */   }
/*      */ 
/*      */   public void gen(BlockEndElement paramBlockEndElement)
/*      */   {
/*  286 */     if (this.DEBUG_CODE_GENERATOR) System.out.println("genRuleEnd(" + paramBlockEndElement + ")");
/*      */   }
/*      */ 
/*      */   public void gen(CharLiteralElement paramCharLiteralElement)
/*      */   {
/*  293 */     if (this.DEBUG_CODE_GENERATOR) System.out.println("genChar(" + paramCharLiteralElement + ")");
/*      */ 
/*  295 */     if (paramCharLiteralElement.getLabel() != null) {
/*  296 */       println(paramCharLiteralElement.getLabel() + " = " + this.lt1Value + ";", paramCharLiteralElement.getLine());
/*      */     }
/*      */ 
/*  299 */     boolean bool = this.saveText;
/*  300 */     this.saveText = ((this.saveText) && (paramCharLiteralElement.getAutoGenType() == 1));
/*  301 */     genMatch(paramCharLiteralElement);
/*  302 */     this.saveText = bool;
/*      */   }
/*      */ 
/*      */   public void gen(CharRangeElement paramCharRangeElement)
/*      */   {
/*  309 */     int i = this.defaultLine;
/*      */     try {
/*  311 */       this.defaultLine = paramCharRangeElement.getLine();
/*  312 */       if ((paramCharRangeElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  313 */         println(paramCharRangeElement.getLabel() + " = " + this.lt1Value + ";");
/*      */       }
/*  315 */       int j = ((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramCharRangeElement.getAutoGenType() == 3)) ? 1 : 0;
/*      */ 
/*  319 */       if (j != 0) {
/*  320 */         println("_saveIndex=text.length();");
/*      */       }
/*      */ 
/*  323 */       println("matchRange(" + paramCharRangeElement.beginText + "," + paramCharRangeElement.endText + ");");
/*      */ 
/*  325 */       if (j != 0)
/*  326 */         println("text.setLength(_saveIndex);");
/*      */     }
/*      */     finally {
/*  329 */       this.defaultLine = i;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void gen(LexerGrammar paramLexerGrammar) throws IOException
/*      */   {
/*  335 */     int i = this.defaultLine;
/*      */     try {
/*  337 */       this.defaultLine = -999;
/*      */ 
/*  339 */       if (paramLexerGrammar.debuggingOutput) {
/*  340 */         this.semPreds = new Vector();
/*      */       }
/*  342 */       setGrammar(paramLexerGrammar);
/*  343 */       if (!(this.grammar instanceof LexerGrammar)) {
/*  344 */         this.antlrTool.panic("Internal error generating lexer");
/*      */       }
/*      */ 
/*  349 */       this.currentOutput = getPrintWriterManager().setupOutput(this.antlrTool, this.grammar);
/*      */ 
/*  351 */       this.genAST = false;
/*  352 */       this.saveText = true;
/*      */ 
/*  354 */       this.tabs = 0;
/*      */ 
/*  357 */       genHeader();
/*      */       try
/*      */       {
/*  361 */         this.defaultLine = this.behavior.getHeaderActionLine("");
/*  362 */         println(this.behavior.getHeaderAction(""));
/*      */       } finally {
/*  364 */         this.defaultLine = -999;
/*      */       }
/*      */ 
/*  369 */       println("import java.io.InputStream;");
/*  370 */       println("import antlr.TokenStreamException;");
/*  371 */       println("import antlr.TokenStreamIOException;");
/*  372 */       println("import antlr.TokenStreamRecognitionException;");
/*  373 */       println("import antlr.CharStreamException;");
/*  374 */       println("import antlr.CharStreamIOException;");
/*  375 */       println("import antlr.ANTLRException;");
/*  376 */       println("import java.io.Reader;");
/*  377 */       println("import java.util.Hashtable;");
/*  378 */       println("import antlr." + this.grammar.getSuperClass() + ";");
/*  379 */       println("import antlr.InputBuffer;");
/*  380 */       println("import antlr.ByteBuffer;");
/*  381 */       println("import antlr.CharBuffer;");
/*  382 */       println("import antlr.Token;");
/*  383 */       println("import antlr.CommonToken;");
/*  384 */       println("import antlr.RecognitionException;");
/*  385 */       println("import antlr.NoViableAltForCharException;");
/*  386 */       println("import antlr.MismatchedCharException;");
/*  387 */       println("import antlr.TokenStream;");
/*  388 */       println("import antlr.ANTLRHashString;");
/*  389 */       println("import antlr.LexerSharedInputState;");
/*  390 */       println("import antlr.collections.impl.BitSet;");
/*  391 */       println("import antlr.SemanticException;");
/*      */ 
/*  394 */       println(this.grammar.preambleAction.getText());
/*      */ 
/*  397 */       String str = null;
/*  398 */       if (this.grammar.superClass != null) {
/*  399 */         str = this.grammar.superClass;
/*      */       }
/*      */       else {
/*  402 */         str = "antlr." + this.grammar.getSuperClass();
/*      */       }
/*      */ 
/*  406 */       if (this.grammar.comment != null) {
/*  407 */         _println(this.grammar.comment);
/*      */       }
/*      */ 
/*  411 */       Object localObject2 = "public";
/*  412 */       Token localToken = (Token)this.grammar.options.get("classHeaderPrefix");
/*  413 */       if (localToken != null) {
/*  414 */         localObject3 = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/*  415 */         if (localObject3 != null) {
/*  416 */           localObject2 = localObject3;
/*      */         }
/*      */       }
/*      */ 
/*  420 */       print((String)localObject2 + " ");
/*  421 */       print("class " + this.grammar.getClassName() + " extends " + str);
/*  422 */       println(" implements " + this.grammar.tokenManager.getName() + TokenTypesFileSuffix + ", TokenStream");
/*  423 */       Object localObject3 = (Token)this.grammar.options.get("classHeaderSuffix");
/*  424 */       if (localObject3 != null) {
/*  425 */         localObject4 = StringUtils.stripFrontBack(((Token)localObject3).getText(), "\"", "\"");
/*  426 */         if (localObject4 != null) {
/*  427 */           print(", " + (String)localObject4);
/*      */         }
/*      */       }
/*  430 */       println(" {");
/*      */ 
/*  433 */       print(processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, null), this.grammar.classMemberAction.getLine());
/*      */ 
/*  442 */       println("public " + this.grammar.getClassName() + "(InputStream in) {");
/*  443 */       this.tabs += 1;
/*  444 */       println("this(new ByteBuffer(in));");
/*  445 */       this.tabs -= 1;
/*  446 */       println("}");
/*      */ 
/*  452 */       println("public " + this.grammar.getClassName() + "(Reader in) {");
/*  453 */       this.tabs += 1;
/*  454 */       println("this(new CharBuffer(in));");
/*  455 */       this.tabs -= 1;
/*  456 */       println("}");
/*      */ 
/*  458 */       println("public " + this.grammar.getClassName() + "(InputBuffer ib) {");
/*  459 */       this.tabs += 1;
/*      */ 
/*  461 */       if (this.grammar.debuggingOutput)
/*  462 */         println("this(new LexerSharedInputState(new antlr.debug.DebuggingInputBuffer(ib)));");
/*      */       else
/*  464 */         println("this(new LexerSharedInputState(ib));");
/*  465 */       this.tabs -= 1;
/*  466 */       println("}");
/*      */ 
/*  471 */       println("public " + this.grammar.getClassName() + "(LexerSharedInputState state) {");
/*  472 */       this.tabs += 1;
/*      */ 
/*  474 */       println("super(state);");
/*      */ 
/*  477 */       if (this.grammar.debuggingOutput) {
/*  478 */         println("  ruleNames  = _ruleNames;");
/*  479 */         println("  semPredNames = _semPredNames;");
/*  480 */         println("  setupDebugging();");
/*      */       }
/*      */ 
/*  486 */       println("caseSensitiveLiterals = " + paramLexerGrammar.caseSensitiveLiterals + ";");
/*  487 */       println("setCaseSensitive(" + paramLexerGrammar.caseSensitive + ");");
/*      */ 
/*  492 */       println("literals = new Hashtable();");
/*  493 */       Object localObject4 = this.grammar.tokenManager.getTokenSymbolKeys();
/*      */       Object localObject6;
/*  494 */       while (((Enumeration)localObject4).hasMoreElements()) {
/*  495 */         localObject5 = (String)((Enumeration)localObject4).nextElement();
/*  496 */         if (((String)localObject5).charAt(0) == '"')
/*      */         {
/*  499 */           TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol((String)localObject5);
/*  500 */           if ((localTokenSymbol instanceof StringLiteralSymbol)) {
/*  501 */             localObject6 = (StringLiteralSymbol)localTokenSymbol;
/*  502 */             println("literals.put(new ANTLRHashString(" + ((StringLiteralSymbol)localObject6).getId() + ", this), new Integer(" + ((StringLiteralSymbol)localObject6).getTokenType() + "));");
/*      */           }
/*      */         }
/*      */       }
/*  505 */       this.tabs -= 1;
/*      */ 
/*  508 */       println("}");
/*      */ 
/*  511 */       if (this.grammar.debuggingOutput) {
/*  512 */         println("private static final String _ruleNames[] = {");
/*      */ 
/*  514 */         localObject5 = this.grammar.rules.elements();
/*  515 */         j = 0;
/*  516 */         while (((Enumeration)localObject5).hasMoreElements()) {
/*  517 */           localObject6 = (GrammarSymbol)((Enumeration)localObject5).nextElement();
/*  518 */           if ((localObject6 instanceof RuleSymbol))
/*  519 */             println("  \"" + ((RuleSymbol)localObject6).getId() + "\",");
/*      */         }
/*  521 */         println("};");
/*      */       }
/*      */ 
/*  527 */       genNextToken();
/*      */ 
/*  530 */       Object localObject5 = this.grammar.rules.elements();
/*  531 */       int j = 0;
/*  532 */       while (((Enumeration)localObject5).hasMoreElements()) {
/*  533 */         localObject6 = (RuleSymbol)((Enumeration)localObject5).nextElement();
/*      */ 
/*  535 */         if (!((RuleSymbol)localObject6).getId().equals("mnextToken")) {
/*  536 */           genRule((RuleSymbol)localObject6, false, j++);
/*      */         }
/*  538 */         exitIfError();
/*      */       }
/*      */ 
/*  542 */       if (this.grammar.debuggingOutput) {
/*  543 */         genSemPredMap();
/*      */       }
/*      */ 
/*  546 */       genBitsets(this.bitsetsUsed, ((LexerGrammar)this.grammar).charVocabulary.size());
/*      */ 
/*  548 */       println("");
/*  549 */       println("}");
/*      */ 
/*  552 */       getPrintWriterManager().finishOutput();
/*      */     } finally {
/*  554 */       this.defaultLine = i;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void gen(OneOrMoreBlock paramOneOrMoreBlock)
/*      */   {
/*  562 */     int i = this.defaultLine;
/*      */     try {
/*  564 */       this.defaultLine = paramOneOrMoreBlock.getLine();
/*  565 */       if (this.DEBUG_CODE_GENERATOR) System.out.println("gen+(" + paramOneOrMoreBlock + ")");
/*      */ 
/*  568 */       println("{", -999);
/*  569 */       genBlockPreamble(paramOneOrMoreBlock);
/*      */       String str2;
/*  570 */       if (paramOneOrMoreBlock.getLabel() != null) {
/*  571 */         str2 = "_cnt_" + paramOneOrMoreBlock.getLabel();
/*      */       }
/*      */       else {
/*  574 */         str2 = "_cnt" + paramOneOrMoreBlock.ID;
/*      */       }
/*  576 */       println("int " + str2 + "=0;");
/*      */       String str1;
/*  577 */       if (paramOneOrMoreBlock.getLabel() != null) {
/*  578 */         str1 = paramOneOrMoreBlock.getLabel();
/*      */       }
/*      */       else {
/*  581 */         str1 = "_loop" + paramOneOrMoreBlock.ID;
/*      */       }
/*  583 */       println(str1 + ":");
/*  584 */       println("do {");
/*  585 */       this.tabs += 1;
/*      */ 
/*  588 */       genBlockInitAction(paramOneOrMoreBlock);
/*      */ 
/*  591 */       String str3 = this.currentASTResult;
/*  592 */       if (paramOneOrMoreBlock.getLabel() != null) {
/*  593 */         this.currentASTResult = paramOneOrMoreBlock.getLabel();
/*      */       }
/*      */ 
/*  596 */       boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramOneOrMoreBlock);
/*      */ 
/*  608 */       int j = 0;
/*  609 */       int k = this.grammar.maxk;
/*      */ 
/*  611 */       if ((!paramOneOrMoreBlock.greedy) && (paramOneOrMoreBlock.exitLookaheadDepth <= this.grammar.maxk) && (paramOneOrMoreBlock.exitCache[paramOneOrMoreBlock.exitLookaheadDepth].containsEpsilon()))
/*      */       {
/*  614 */         j = 1;
/*  615 */         k = paramOneOrMoreBlock.exitLookaheadDepth;
/*      */       }
/*  617 */       else if ((!paramOneOrMoreBlock.greedy) && (paramOneOrMoreBlock.exitLookaheadDepth == 2147483647))
/*      */       {
/*  619 */         j = 1;
/*      */       }
/*      */ 
/*  624 */       if (j != 0) {
/*  625 */         if (this.DEBUG_CODE_GENERATOR) {
/*  626 */           System.out.println("nongreedy (...)+ loop; exit depth is " + paramOneOrMoreBlock.exitLookaheadDepth);
/*      */         }
/*      */ 
/*  629 */         localObject1 = getLookaheadTestExpression(paramOneOrMoreBlock.exitCache, k);
/*      */ 
/*  632 */         println("// nongreedy exit test", -999);
/*  633 */         println("if ( " + str2 + ">=1 && " + (String)localObject1 + ") break " + str1 + ";", -888);
/*      */       }
/*      */ 
/*  636 */       Object localObject1 = genCommonBlock(paramOneOrMoreBlock, false);
/*  637 */       genBlockFinish((JavaBlockFinishingInfo)localObject1, "if ( " + str2 + ">=1 ) { break " + str1 + "; } else {" + this.throwNoViable + "}", paramOneOrMoreBlock.getLine());
/*      */ 
/*  643 */       println(str2 + "++;");
/*  644 */       this.tabs -= 1;
/*  645 */       println("} while (true);");
/*  646 */       println("}");
/*      */ 
/*  649 */       this.currentASTResult = str3;
/*      */     } finally {
/*  651 */       this.defaultLine = i;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void gen(ParserGrammar paramParserGrammar) throws IOException
/*      */   {
/*  657 */     int i = this.defaultLine;
/*      */     try {
/*  659 */       this.defaultLine = -999;
/*      */ 
/*  662 */       if (paramParserGrammar.debuggingOutput) {
/*  663 */         this.semPreds = new Vector();
/*      */       }
/*  665 */       setGrammar(paramParserGrammar);
/*  666 */       if (!(this.grammar instanceof ParserGrammar)) {
/*  667 */         this.antlrTool.panic("Internal error generating parser");
/*      */       }
/*      */ 
/*  672 */       this.currentOutput = getPrintWriterManager().setupOutput(this.antlrTool, this.grammar);
/*      */ 
/*  674 */       this.genAST = this.grammar.buildAST;
/*      */ 
/*  676 */       this.tabs = 0;
/*      */ 
/*  679 */       genHeader();
/*      */       try
/*      */       {
/*  682 */         this.defaultLine = this.behavior.getHeaderActionLine("");
/*  683 */         println(this.behavior.getHeaderAction(""));
/*      */       } finally {
/*  685 */         this.defaultLine = -999;
/*      */       }
/*      */ 
/*  689 */       println("import antlr.TokenBuffer;");
/*  690 */       println("import antlr.TokenStreamException;");
/*  691 */       println("import antlr.TokenStreamIOException;");
/*  692 */       println("import antlr.ANTLRException;");
/*  693 */       println("import antlr." + this.grammar.getSuperClass() + ";");
/*  694 */       println("import antlr.Token;");
/*  695 */       println("import antlr.TokenStream;");
/*  696 */       println("import antlr.RecognitionException;");
/*  697 */       println("import antlr.NoViableAltException;");
/*  698 */       println("import antlr.MismatchedTokenException;");
/*  699 */       println("import antlr.SemanticException;");
/*  700 */       println("import antlr.ParserSharedInputState;");
/*  701 */       println("import antlr.collections.impl.BitSet;");
/*  702 */       if (this.genAST) {
/*  703 */         println("import antlr.collections.AST;");
/*  704 */         println("import java.util.Hashtable;");
/*  705 */         println("import antlr.ASTFactory;");
/*  706 */         println("import antlr.ASTPair;");
/*  707 */         println("import antlr.collections.impl.ASTArray;");
/*      */       }
/*      */ 
/*  711 */       println(this.grammar.preambleAction.getText());
/*      */ 
/*  714 */       String str = null;
/*  715 */       if (this.grammar.superClass != null)
/*  716 */         str = this.grammar.superClass;
/*      */       else {
/*  718 */         str = "antlr." + this.grammar.getSuperClass();
/*      */       }
/*      */ 
/*  721 */       if (this.grammar.comment != null) {
/*  722 */         _println(this.grammar.comment);
/*      */       }
/*      */ 
/*  726 */       Object localObject2 = "public";
/*  727 */       Token localToken = (Token)this.grammar.options.get("classHeaderPrefix");
/*  728 */       if (localToken != null) {
/*  729 */         localObject3 = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/*  730 */         if (localObject3 != null) {
/*  731 */           localObject2 = localObject3;
/*      */         }
/*      */       }
/*      */ 
/*  735 */       print((String)localObject2 + " ");
/*  736 */       print("class " + this.grammar.getClassName() + " extends " + str);
/*  737 */       println("       implements " + this.grammar.tokenManager.getName() + TokenTypesFileSuffix);
/*      */ 
/*  739 */       Object localObject3 = (Token)this.grammar.options.get("classHeaderSuffix");
/*  740 */       if (localObject3 != null) {
/*  741 */         localObject4 = StringUtils.stripFrontBack(((Token)localObject3).getText(), "\"", "\"");
/*  742 */         if (localObject4 != null)
/*  743 */           print(", " + (String)localObject4);
/*      */       }
/*  745 */       println(" {");
/*      */       GrammarSymbol localGrammarSymbol;
/*  749 */       if (this.grammar.debuggingOutput) {
/*  750 */         println("private static final String _ruleNames[] = {");
/*      */ 
/*  752 */         localObject4 = this.grammar.rules.elements();
/*  753 */         j = 0;
/*  754 */         while (((Enumeration)localObject4).hasMoreElements()) {
/*  755 */           localGrammarSymbol = (GrammarSymbol)((Enumeration)localObject4).nextElement();
/*  756 */           if ((localGrammarSymbol instanceof RuleSymbol))
/*  757 */             println("  \"" + ((RuleSymbol)localGrammarSymbol).getId() + "\",");
/*      */         }
/*  759 */         println("};");
/*      */       }
/*      */ 
/*  763 */       print(processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, null), this.grammar.classMemberAction.getLine());
/*      */ 
/*  769 */       println("");
/*  770 */       println("protected " + this.grammar.getClassName() + "(TokenBuffer tokenBuf, int k) {");
/*  771 */       println("  super(tokenBuf,k);");
/*  772 */       println("  tokenNames = _tokenNames;");
/*      */ 
/*  775 */       if (this.grammar.debuggingOutput) {
/*  776 */         println("  ruleNames  = _ruleNames;");
/*  777 */         println("  semPredNames = _semPredNames;");
/*  778 */         println("  setupDebugging(tokenBuf);");
/*      */       }
/*  780 */       if (this.grammar.buildAST) {
/*  781 */         println("  buildTokenTypeASTClassMap();");
/*  782 */         println("  astFactory = new ASTFactory(getTokenTypeToASTClassMap());");
/*      */       }
/*  784 */       println("}");
/*  785 */       println("");
/*      */ 
/*  787 */       println("public " + this.grammar.getClassName() + "(TokenBuffer tokenBuf) {");
/*  788 */       println("  this(tokenBuf," + this.grammar.maxk + ");");
/*  789 */       println("}");
/*  790 */       println("");
/*      */ 
/*  793 */       println("protected " + this.grammar.getClassName() + "(TokenStream lexer, int k) {");
/*  794 */       println("  super(lexer,k);");
/*  795 */       println("  tokenNames = _tokenNames;");
/*      */ 
/*  799 */       if (this.grammar.debuggingOutput) {
/*  800 */         println("  ruleNames  = _ruleNames;");
/*  801 */         println("  semPredNames = _semPredNames;");
/*  802 */         println("  setupDebugging(lexer);");
/*      */       }
/*  804 */       if (this.grammar.buildAST) {
/*  805 */         println("  buildTokenTypeASTClassMap();");
/*  806 */         println("  astFactory = new ASTFactory(getTokenTypeToASTClassMap());");
/*      */       }
/*  808 */       println("}");
/*  809 */       println("");
/*      */ 
/*  811 */       println("public " + this.grammar.getClassName() + "(TokenStream lexer) {");
/*  812 */       println("  this(lexer," + this.grammar.maxk + ");");
/*  813 */       println("}");
/*  814 */       println("");
/*      */ 
/*  816 */       println("public " + this.grammar.getClassName() + "(ParserSharedInputState state) {");
/*  817 */       println("  super(state," + this.grammar.maxk + ");");
/*  818 */       println("  tokenNames = _tokenNames;");
/*  819 */       if (this.grammar.buildAST) {
/*  820 */         println("  buildTokenTypeASTClassMap();");
/*  821 */         println("  astFactory = new ASTFactory(getTokenTypeToASTClassMap());");
/*      */       }
/*  823 */       println("}");
/*  824 */       println("");
/*      */ 
/*  827 */       Object localObject4 = this.grammar.rules.elements();
/*  828 */       int j = 0;
/*  829 */       while (((Enumeration)localObject4).hasMoreElements()) {
/*  830 */         localGrammarSymbol = (GrammarSymbol)((Enumeration)localObject4).nextElement();
/*  831 */         if ((localGrammarSymbol instanceof RuleSymbol)) {
/*  832 */           RuleSymbol localRuleSymbol = (RuleSymbol)localGrammarSymbol;
/*  833 */           genRule(localRuleSymbol, localRuleSymbol.references.size() == 0, j++);
/*      */         }
/*  835 */         exitIfError();
/*      */       }
/*      */ 
/*  839 */       genTokenStrings();
/*      */ 
/*  841 */       if (this.grammar.buildAST) {
/*  842 */         genTokenASTNodeMap();
/*      */       }
/*      */ 
/*  846 */       genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
/*      */ 
/*  849 */       if (this.grammar.debuggingOutput) {
/*  850 */         genSemPredMap();
/*      */       }
/*      */ 
/*  853 */       println("");
/*  854 */       println("}");
/*      */ 
/*  857 */       getPrintWriterManager().finishOutput();
/*      */     } finally {
/*  859 */       this.defaultLine = i;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void gen(RuleRefElement paramRuleRefElement)
/*      */   {
/*  867 */     int i = this.defaultLine;
/*      */     try {
/*  869 */       this.defaultLine = paramRuleRefElement.getLine();
/*  870 */       if (this.DEBUG_CODE_GENERATOR) System.out.println("genRR(" + paramRuleRefElement + ")");
/*  871 */       RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(paramRuleRefElement.targetRule);
/*  872 */       if ((localRuleSymbol == null) || (!localRuleSymbol.isDefined()))
/*      */       {
/*  874 */         this.antlrTool.error("Rule '" + paramRuleRefElement.targetRule + "' is not defined", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*      */       }
/*  877 */       else if (!(localRuleSymbol instanceof RuleSymbol))
/*      */       {
/*  879 */         this.antlrTool.error("'" + paramRuleRefElement.targetRule + "' does not name a grammar rule", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*      */       }
/*      */       else
/*      */       {
/*  883 */         genErrorTryForElement(paramRuleRefElement);
/*      */ 
/*  887 */         if (((this.grammar instanceof TreeWalkerGrammar)) && (paramRuleRefElement.getLabel() != null) && (this.syntacticPredLevel == 0))
/*      */         {
/*  890 */           println(paramRuleRefElement.getLabel() + " = _t==ASTNULL ? null : " + this.lt1Value + ";");
/*      */         }
/*      */ 
/*  894 */         if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramRuleRefElement.getAutoGenType() == 3))) {
/*  895 */           println("_saveIndex=text.length();");
/*      */         }
/*      */ 
/*  899 */         printTabs();
/*  900 */         if (paramRuleRefElement.idAssign != null)
/*      */         {
/*  902 */           if (localRuleSymbol.block.returnAction == null) {
/*  903 */             this.antlrTool.warning("Rule '" + paramRuleRefElement.targetRule + "' has no return type", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*      */           }
/*  905 */           _print(paramRuleRefElement.idAssign + "=");
/*      */         }
/*  909 */         else if ((!(this.grammar instanceof LexerGrammar)) && (this.syntacticPredLevel == 0) && (localRuleSymbol.block.returnAction != null)) {
/*  910 */           this.antlrTool.warning("Rule '" + paramRuleRefElement.targetRule + "' returns a value", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*      */         }
/*      */ 
/*  915 */         GenRuleInvocation(paramRuleRefElement);
/*      */ 
/*  918 */         if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramRuleRefElement.getAutoGenType() == 3))) {
/*  919 */           println("text.setLength(_saveIndex);");
/*      */         }
/*      */ 
/*  923 */         if (this.syntacticPredLevel == 0) {
/*  924 */           int j = (this.grammar.hasSyntacticPredicate) && (((this.grammar.buildAST) && (paramRuleRefElement.getLabel() != null)) || ((this.genAST) && (paramRuleRefElement.getAutoGenType() == 1))) ? 1 : 0;
/*      */ 
/*  931 */           if ((j == 0) || (
/*  936 */             (this.grammar.buildAST) && (paramRuleRefElement.getLabel() != null)))
/*      */           {
/*  938 */             println(paramRuleRefElement.getLabel() + "_AST = (" + this.labeledElementASTType + ")returnAST;");
/*      */           }
/*  940 */           if (this.genAST) {
/*  941 */             switch (paramRuleRefElement.getAutoGenType())
/*      */             {
/*      */             case 1:
/*  944 */               println("astFactory.addASTChild(currentAST, returnAST);");
/*  945 */               break;
/*      */             case 2:
/*  947 */               this.antlrTool.error("Internal: encountered ^ after rule reference");
/*  948 */               break;
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  955 */           if (((this.grammar instanceof LexerGrammar)) && (paramRuleRefElement.getLabel() != null)) {
/*  956 */             println(paramRuleRefElement.getLabel() + "=_returnToken;");
/*      */           }
/*      */ 
/*  959 */           if (j == 0);
/*      */         }
/*      */ 
/*  964 */         genErrorCatchForElement(paramRuleRefElement);
/*      */       }
/*      */     } finally { this.defaultLine = i; }
/*      */ 
/*      */   }
/*      */ 
/*      */   public void gen(StringLiteralElement paramStringLiteralElement)
/*      */   {
/*  974 */     if (this.DEBUG_CODE_GENERATOR) System.out.println("genString(" + paramStringLiteralElement + ")");
/*      */ 
/*  977 */     if ((paramStringLiteralElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  978 */       println(paramStringLiteralElement.getLabel() + " = " + this.lt1Value + ";", paramStringLiteralElement.getLine());
/*      */     }
/*      */ 
/*  982 */     genElementAST(paramStringLiteralElement);
/*      */ 
/*  985 */     boolean bool = this.saveText;
/*  986 */     this.saveText = ((this.saveText) && (paramStringLiteralElement.getAutoGenType() == 1));
/*      */ 
/*  989 */     genMatch(paramStringLiteralElement);
/*      */ 
/*  991 */     this.saveText = bool;
/*      */ 
/*  994 */     if ((this.grammar instanceof TreeWalkerGrammar))
/*  995 */       println("_t = _t.getNextSibling();", paramStringLiteralElement.getLine());
/*      */   }
/*      */ 
/*      */   public void gen(TokenRangeElement paramTokenRangeElement)
/*      */   {
/* 1003 */     genErrorTryForElement(paramTokenRangeElement);
/* 1004 */     if ((paramTokenRangeElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/* 1005 */       println(paramTokenRangeElement.getLabel() + " = " + this.lt1Value + ";", paramTokenRangeElement.getLine());
/*      */     }
/*      */ 
/* 1009 */     genElementAST(paramTokenRangeElement);
/*      */ 
/* 1012 */     println("matchRange(" + paramTokenRangeElement.beginText + "," + paramTokenRangeElement.endText + ");", paramTokenRangeElement.getLine());
/* 1013 */     genErrorCatchForElement(paramTokenRangeElement);
/*      */   }
/*      */ 
/*      */   public void gen(TokenRefElement paramTokenRefElement)
/*      */   {
/* 1020 */     if (this.DEBUG_CODE_GENERATOR) System.out.println("genTokenRef(" + paramTokenRefElement + ")");
/* 1021 */     if ((this.grammar instanceof LexerGrammar)) {
/* 1022 */       this.antlrTool.panic("Token reference found in lexer");
/*      */     }
/* 1024 */     genErrorTryForElement(paramTokenRefElement);
/*      */ 
/* 1026 */     if ((paramTokenRefElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/* 1027 */       println(paramTokenRefElement.getLabel() + " = " + this.lt1Value + ";", paramTokenRefElement.getLine());
/*      */     }
/*      */ 
/* 1031 */     genElementAST(paramTokenRefElement);
/*      */ 
/* 1033 */     genMatch(paramTokenRefElement);
/* 1034 */     genErrorCatchForElement(paramTokenRefElement);
/*      */ 
/* 1037 */     if ((this.grammar instanceof TreeWalkerGrammar))
/* 1038 */       println("_t = _t.getNextSibling();", paramTokenRefElement.getLine());
/*      */   }
/*      */ 
/*      */   public void gen(TreeElement paramTreeElement)
/*      */   {
/* 1043 */     int i = this.defaultLine;
/*      */     try {
/* 1045 */       this.defaultLine = paramTreeElement.getLine();
/*      */ 
/* 1047 */       println("AST __t" + paramTreeElement.ID + " = _t;");
/*      */ 
/* 1050 */       if (paramTreeElement.root.getLabel() != null) {
/* 1051 */         println(paramTreeElement.root.getLabel() + " = _t==ASTNULL ? null :(" + this.labeledElementASTType + ")_t;", paramTreeElement.root.getLine());
/*      */       }
/*      */ 
/* 1055 */       if (paramTreeElement.root.getAutoGenType() == 3) {
/* 1056 */         this.antlrTool.error("Suffixing a root node with '!' is not implemented", this.grammar.getFilename(), paramTreeElement.getLine(), paramTreeElement.getColumn());
/*      */ 
/* 1058 */         paramTreeElement.root.setAutoGenType(1);
/*      */       }
/* 1060 */       if (paramTreeElement.root.getAutoGenType() == 2) {
/* 1061 */         this.antlrTool.warning("Suffixing a root node with '^' is redundant; already a root", this.grammar.getFilename(), paramTreeElement.getLine(), paramTreeElement.getColumn());
/*      */ 
/* 1063 */         paramTreeElement.root.setAutoGenType(1);
/*      */       }
/*      */ 
/* 1067 */       genElementAST(paramTreeElement.root);
/* 1068 */       if (this.grammar.buildAST)
/*      */       {
/* 1070 */         println("ASTPair __currentAST" + paramTreeElement.ID + " = currentAST.copy();");
/*      */ 
/* 1072 */         println("currentAST.root = currentAST.child;");
/* 1073 */         println("currentAST.child = null;");
/*      */       }
/*      */ 
/* 1077 */       if ((paramTreeElement.root instanceof WildcardElement)) {
/* 1078 */         println("if ( _t==null ) throw new MismatchedTokenException();", paramTreeElement.root.getLine());
/*      */       }
/*      */       else {
/* 1081 */         genMatch(paramTreeElement.root);
/*      */       }
/*      */ 
/* 1084 */       println("_t = _t.getFirstChild();");
/*      */ 
/* 1087 */       for (int j = 0; j < paramTreeElement.getAlternatives().size(); j++) {
/* 1088 */         Alternative localAlternative = paramTreeElement.getAlternativeAt(j);
/* 1089 */         AlternativeElement localAlternativeElement = localAlternative.head;
/* 1090 */         while (localAlternativeElement != null) {
/* 1091 */           localAlternativeElement.generate();
/* 1092 */           localAlternativeElement = localAlternativeElement.next;
/*      */         }
/*      */       }
/*      */ 
/* 1096 */       if (this.grammar.buildAST)
/*      */       {
/* 1099 */         println("currentAST = __currentAST" + paramTreeElement.ID + ";");
/*      */       }
/*      */ 
/* 1102 */       println("_t = __t" + paramTreeElement.ID + ";");
/*      */ 
/* 1104 */       println("_t = _t.getNextSibling();");
/*      */     } finally {
/* 1106 */       this.defaultLine = i;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void gen(TreeWalkerGrammar paramTreeWalkerGrammar) throws IOException
/*      */   {
/* 1112 */     int i = this.defaultLine;
/*      */     try {
/* 1114 */       this.defaultLine = -999;
/*      */ 
/* 1116 */       setGrammar(paramTreeWalkerGrammar);
/* 1117 */       if (!(this.grammar instanceof TreeWalkerGrammar)) {
/* 1118 */         this.antlrTool.panic("Internal error generating tree-walker");
/*      */       }
/*      */ 
/* 1123 */       this.currentOutput = getPrintWriterManager().setupOutput(this.antlrTool, this.grammar);
/*      */ 
/* 1125 */       this.genAST = this.grammar.buildAST;
/* 1126 */       this.tabs = 0;
/*      */ 
/* 1129 */       genHeader();
/*      */       try
/*      */       {
/* 1132 */         this.defaultLine = this.behavior.getHeaderActionLine("");
/* 1133 */         println(this.behavior.getHeaderAction(""));
/*      */       } finally {
/* 1135 */         this.defaultLine = -999;
/*      */       }
/*      */ 
/* 1139 */       println("import antlr." + this.grammar.getSuperClass() + ";");
/* 1140 */       println("import antlr.Token;");
/* 1141 */       println("import antlr.collections.AST;");
/* 1142 */       println("import antlr.RecognitionException;");
/* 1143 */       println("import antlr.ANTLRException;");
/* 1144 */       println("import antlr.NoViableAltException;");
/* 1145 */       println("import antlr.MismatchedTokenException;");
/* 1146 */       println("import antlr.SemanticException;");
/* 1147 */       println("import antlr.collections.impl.BitSet;");
/* 1148 */       println("import antlr.ASTPair;");
/* 1149 */       println("import antlr.collections.impl.ASTArray;");
/*      */ 
/* 1152 */       println(this.grammar.preambleAction.getText());
/*      */ 
/* 1155 */       String str1 = null;
/* 1156 */       if (this.grammar.superClass != null) {
/* 1157 */         str1 = this.grammar.superClass;
/*      */       }
/*      */       else {
/* 1160 */         str1 = "antlr." + this.grammar.getSuperClass();
/*      */       }
/* 1162 */       println("");
/*      */ 
/* 1165 */       if (this.grammar.comment != null) {
/* 1166 */         _println(this.grammar.comment);
/*      */       }
/*      */ 
/* 1170 */       Object localObject2 = "public";
/* 1171 */       Token localToken = (Token)this.grammar.options.get("classHeaderPrefix");
/* 1172 */       if (localToken != null) {
/* 1173 */         localObject3 = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 1174 */         if (localObject3 != null) {
/* 1175 */           localObject2 = localObject3;
/*      */         }
/*      */       }
/*      */ 
/* 1179 */       print((String)localObject2 + " ");
/* 1180 */       print("class " + this.grammar.getClassName() + " extends " + str1);
/* 1181 */       println("       implements " + this.grammar.tokenManager.getName() + TokenTypesFileSuffix);
/* 1182 */       Object localObject3 = (Token)this.grammar.options.get("classHeaderSuffix");
/* 1183 */       if (localObject3 != null) {
/* 1184 */         localObject4 = StringUtils.stripFrontBack(((Token)localObject3).getText(), "\"", "\"");
/* 1185 */         if (localObject4 != null) {
/* 1186 */           print(", " + (String)localObject4);
/*      */         }
/*      */       }
/* 1189 */       println(" {");
/*      */ 
/* 1192 */       print(processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, null), this.grammar.classMemberAction.getLine());
/*      */ 
/* 1198 */       println("public " + this.grammar.getClassName() + "() {");
/* 1199 */       this.tabs += 1;
/* 1200 */       println("tokenNames = _tokenNames;");
/* 1201 */       this.tabs -= 1;
/* 1202 */       println("}");
/* 1203 */       println("");
/*      */ 
/* 1206 */       Object localObject4 = this.grammar.rules.elements();
/* 1207 */       int j = 0;
/* 1208 */       String str2 = "";
/* 1209 */       while (((Enumeration)localObject4).hasMoreElements()) {
/* 1210 */         GrammarSymbol localGrammarSymbol = (GrammarSymbol)((Enumeration)localObject4).nextElement();
/* 1211 */         if ((localGrammarSymbol instanceof RuleSymbol)) {
/* 1212 */           RuleSymbol localRuleSymbol = (RuleSymbol)localGrammarSymbol;
/* 1213 */           genRule(localRuleSymbol, localRuleSymbol.references.size() == 0, j++);
/*      */         }
/* 1215 */         exitIfError();
/*      */       }
/*      */ 
/* 1219 */       genTokenStrings();
/*      */ 
/* 1222 */       genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
/*      */ 
/* 1225 */       println("}");
/* 1226 */       println("");
/*      */ 
/* 1229 */       getPrintWriterManager().finishOutput();
/*      */     } finally {
/* 1231 */       this.defaultLine = i;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void gen(WildcardElement paramWildcardElement)
/*      */   {
/* 1239 */     int i = this.defaultLine;
/*      */     try {
/* 1241 */       this.defaultLine = paramWildcardElement.getLine();
/*      */ 
/* 1243 */       if ((paramWildcardElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/* 1244 */         println(paramWildcardElement.getLabel() + " = " + this.lt1Value + ";");
/*      */       }
/*      */ 
/* 1248 */       genElementAST(paramWildcardElement);
/*      */ 
/* 1250 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1251 */         println("if ( _t==null ) throw new MismatchedTokenException();");
/*      */       }
/* 1253 */       else if ((this.grammar instanceof LexerGrammar)) {
/* 1254 */         if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramWildcardElement.getAutoGenType() == 3)))
/*      */         {
/* 1256 */           println("_saveIndex=text.length();");
/*      */         }
/* 1258 */         println("matchNot(EOF_CHAR);");
/* 1259 */         if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramWildcardElement.getAutoGenType() == 3)))
/*      */         {
/* 1261 */           println("text.setLength(_saveIndex);");
/*      */         }
/*      */       }
/*      */       else {
/* 1265 */         println("matchNot(" + getValueString(1) + ");");
/*      */       }
/*      */ 
/* 1269 */       if ((this.grammar instanceof TreeWalkerGrammar))
/* 1270 */         println("_t = _t.getNextSibling();");
/*      */     }
/*      */     finally {
/* 1273 */       this.defaultLine = i;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void gen(ZeroOrMoreBlock paramZeroOrMoreBlock)
/*      */   {
/* 1281 */     int i = this.defaultLine;
/*      */     try {
/* 1283 */       this.defaultLine = paramZeroOrMoreBlock.getLine();
/* 1284 */       if (this.DEBUG_CODE_GENERATOR) System.out.println("gen*(" + paramZeroOrMoreBlock + ")");
/* 1285 */       println("{");
/* 1286 */       genBlockPreamble(paramZeroOrMoreBlock);
/*      */       String str1;
/* 1288 */       if (paramZeroOrMoreBlock.getLabel() != null) {
/* 1289 */         str1 = paramZeroOrMoreBlock.getLabel();
/*      */       }
/*      */       else {
/* 1292 */         str1 = "_loop" + paramZeroOrMoreBlock.ID;
/*      */       }
/* 1294 */       println(str1 + ":");
/* 1295 */       println("do {");
/* 1296 */       this.tabs += 1;
/*      */ 
/* 1299 */       genBlockInitAction(paramZeroOrMoreBlock);
/*      */ 
/* 1302 */       String str2 = this.currentASTResult;
/* 1303 */       if (paramZeroOrMoreBlock.getLabel() != null) {
/* 1304 */         this.currentASTResult = paramZeroOrMoreBlock.getLabel();
/*      */       }
/*      */ 
/* 1307 */       boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramZeroOrMoreBlock);
/*      */ 
/* 1319 */       int j = 0;
/* 1320 */       int k = this.grammar.maxk;
/*      */ 
/* 1322 */       if ((!paramZeroOrMoreBlock.greedy) && (paramZeroOrMoreBlock.exitLookaheadDepth <= this.grammar.maxk) && (paramZeroOrMoreBlock.exitCache[paramZeroOrMoreBlock.exitLookaheadDepth].containsEpsilon()))
/*      */       {
/* 1325 */         j = 1;
/* 1326 */         k = paramZeroOrMoreBlock.exitLookaheadDepth;
/*      */       }
/* 1328 */       else if ((!paramZeroOrMoreBlock.greedy) && (paramZeroOrMoreBlock.exitLookaheadDepth == 2147483647))
/*      */       {
/* 1330 */         j = 1;
/*      */       }
/* 1332 */       if (j != 0) {
/* 1333 */         if (this.DEBUG_CODE_GENERATOR) {
/* 1334 */           System.out.println("nongreedy (...)* loop; exit depth is " + paramZeroOrMoreBlock.exitLookaheadDepth);
/*      */         }
/*      */ 
/* 1337 */         localObject1 = getLookaheadTestExpression(paramZeroOrMoreBlock.exitCache, k);
/*      */ 
/* 1340 */         println("// nongreedy exit test");
/* 1341 */         println("if (" + (String)localObject1 + ") break " + str1 + ";");
/*      */       }
/*      */ 
/* 1344 */       Object localObject1 = genCommonBlock(paramZeroOrMoreBlock, false);
/* 1345 */       genBlockFinish((JavaBlockFinishingInfo)localObject1, "break " + str1 + ";", paramZeroOrMoreBlock.getLine());
/*      */ 
/* 1347 */       this.tabs -= 1;
/* 1348 */       println("} while (true);");
/* 1349 */       println("}");
/*      */ 
/* 1352 */       this.currentASTResult = str2;
/*      */     } finally {
/* 1354 */       this.defaultLine = i;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void genAlt(Alternative paramAlternative, AlternativeBlock paramAlternativeBlock)
/*      */   {
/* 1364 */     boolean bool1 = this.genAST;
/* 1365 */     this.genAST = ((this.genAST) && (paramAlternative.getAutoGen()));
/*      */ 
/* 1367 */     boolean bool2 = this.saveText;
/* 1368 */     this.saveText = ((this.saveText) && (paramAlternative.getAutoGen()));
/*      */ 
/* 1371 */     Hashtable localHashtable = this.treeVariableMap;
/* 1372 */     this.treeVariableMap = new Hashtable();
/*      */ 
/* 1375 */     if (paramAlternative.exceptionSpec != null) {
/* 1376 */       println("try {      // for error handling", paramAlternative.head.getLine());
/* 1377 */       this.tabs += 1;
/*      */     }
/*      */ 
/* 1380 */     AlternativeElement localAlternativeElement = paramAlternative.head;
/* 1381 */     while (!(localAlternativeElement instanceof BlockEndElement)) {
/* 1382 */       localAlternativeElement.generate();
/* 1383 */       localAlternativeElement = localAlternativeElement.next;
/*      */     }
/*      */ 
/* 1386 */     if (this.genAST) {
/* 1387 */       if ((paramAlternativeBlock instanceof RuleBlock))
/*      */       {
/* 1389 */         RuleBlock localRuleBlock = (RuleBlock)paramAlternativeBlock;
/* 1390 */         if (this.grammar.hasSyntacticPredicate);
/* 1394 */         println(localRuleBlock.getRuleName() + "_AST = (" + this.labeledElementASTType + ")currentAST.root;", -888);
/* 1395 */         if (!this.grammar.hasSyntacticPredicate);
/*      */       }
/* 1400 */       else if (paramAlternativeBlock.getLabel() != null)
/*      */       {
/* 1403 */         this.antlrTool.warning("Labeled subrules not yet supported", this.grammar.getFilename(), paramAlternativeBlock.getLine(), paramAlternativeBlock.getColumn());
/*      */       }
/*      */     }
/*      */ 
/* 1407 */     if (paramAlternative.exceptionSpec != null)
/*      */     {
/* 1409 */       this.tabs -= 1;
/* 1410 */       println("}", -999);
/* 1411 */       genErrorHandler(paramAlternative.exceptionSpec);
/*      */     }
/*      */ 
/* 1414 */     this.genAST = bool1;
/* 1415 */     this.saveText = bool2;
/*      */ 
/* 1417 */     this.treeVariableMap = localHashtable;
/*      */   }
/*      */ 
/*      */   protected void genBitsets(Vector paramVector, int paramInt)
/*      */   {
/* 1433 */     println("", -999);
/* 1434 */     for (int i = 0; i < paramVector.size(); i++) {
/* 1435 */       BitSet localBitSet = (BitSet)paramVector.elementAt(i);
/*      */ 
/* 1437 */       localBitSet.growToInclude(paramInt);
/* 1438 */       genBitSet(localBitSet, i);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void genBitSet(BitSet paramBitSet, int paramInt)
/*      */   {
/* 1453 */     int i = this.defaultLine;
/*      */     try {
/* 1455 */       this.defaultLine = -999;
/*      */ 
/* 1457 */       println("private static final long[] mk" + getBitsetName(paramInt) + "() {");
/*      */ 
/* 1460 */       int j = paramBitSet.lengthInLongWords();
/*      */       long[] arrayOfLong;
/*      */       int k;
/* 1461 */       if (j < 8) {
/* 1462 */         println("\tlong[] data = { " + paramBitSet.toStringOfWords() + "};");
/*      */       }
/*      */       else
/*      */       {
/* 1466 */         println("\tlong[] data = new long[" + j + "];");
/* 1467 */         arrayOfLong = paramBitSet.toPackedArray();
/* 1468 */         for (k = 0; k < arrayOfLong.length; ) {
/* 1469 */           if (arrayOfLong[k] == 0L)
/*      */           {
/* 1471 */             k++;
/*      */           }
/* 1474 */           else if ((k + 1 == arrayOfLong.length) || (arrayOfLong[k] != arrayOfLong[(k + 1)]))
/*      */           {
/* 1476 */             println("\tdata[" + k + "]=" + arrayOfLong[k] + "L;");
/* 1477 */             k++;
/*      */           }
/*      */           else
/*      */           {
/* 1482 */             int m = k + 1;
/* 1483 */             while ((m < arrayOfLong.length) && (arrayOfLong[m] == arrayOfLong[k])) {
/* 1484 */               m++;
/*      */             }
/*      */ 
/* 1488 */             println("\tfor (int i = " + k + "; i<=" + (m - 1) + "; i++) { data[i]=" + arrayOfLong[k] + "L; }");
/*      */ 
/* 1490 */             k = m;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/* 1495 */       println("\treturn data;");
/* 1496 */       println("}");
/*      */ 
/* 1498 */       println("public static final BitSet " + getBitsetName(paramInt) + " = new BitSet(" + "mk" + getBitsetName(paramInt) + "()" + ");");
/*      */     }
/*      */     finally
/*      */     {
/* 1504 */       this.defaultLine = i;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void genBlockFinish(JavaBlockFinishingInfo paramJavaBlockFinishingInfo, String paramString, int paramInt)
/*      */   {
/* 1515 */     int i = this.defaultLine;
/*      */     try {
/* 1517 */       this.defaultLine = paramInt;
/* 1518 */       if ((paramJavaBlockFinishingInfo.needAnErrorClause) && ((paramJavaBlockFinishingInfo.generatedAnIf) || (paramJavaBlockFinishingInfo.generatedSwitch)))
/*      */       {
/* 1520 */         if (paramJavaBlockFinishingInfo.generatedAnIf) {
/* 1521 */           println("else {");
/*      */         }
/*      */         else {
/* 1524 */           println("{");
/*      */         }
/* 1526 */         this.tabs += 1;
/* 1527 */         println(paramString);
/* 1528 */         this.tabs -= 1;
/* 1529 */         println("}");
/*      */       }
/*      */ 
/* 1532 */       if (paramJavaBlockFinishingInfo.postscript != null)
/* 1533 */         println(paramJavaBlockFinishingInfo.postscript);
/*      */     }
/*      */     finally {
/* 1536 */       this.defaultLine = i;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void genBlockInitAction(AlternativeBlock paramAlternativeBlock)
/*      */   {
/* 1546 */     if (paramAlternativeBlock.initAction != null)
/* 1547 */       printAction(processActionForSpecialSymbols(paramAlternativeBlock.initAction, paramAlternativeBlock.getLine(), this.currentRule, null), paramAlternativeBlock.getLine());
/*      */   }
/*      */ 
/*      */   protected void genBlockPreamble(AlternativeBlock paramAlternativeBlock)
/*      */   {
/* 1558 */     if ((paramAlternativeBlock instanceof RuleBlock)) {
/* 1559 */       RuleBlock localRuleBlock = (RuleBlock)paramAlternativeBlock;
/* 1560 */       if (localRuleBlock.labeledElements != null)
/* 1561 */         for (int i = 0; i < localRuleBlock.labeledElements.size(); i++) {
/* 1562 */           AlternativeElement localAlternativeElement = (AlternativeElement)localRuleBlock.labeledElements.elementAt(i);
/* 1563 */           int j = this.defaultLine;
/*      */           try {
/* 1565 */             this.defaultLine = localAlternativeElement.getLine();
/*      */ 
/* 1572 */             if (((localAlternativeElement instanceof RuleRefElement)) || (((localAlternativeElement instanceof AlternativeBlock)) && (!(localAlternativeElement instanceof RuleBlock)) && (!(localAlternativeElement instanceof SynPredBlock))))
/*      */             {
/* 1579 */               if ((!(localAlternativeElement instanceof RuleRefElement)) && (((AlternativeBlock)localAlternativeElement).not) && (this.analyzer.subruleCanBeInverted((AlternativeBlock)localAlternativeElement, this.grammar instanceof LexerGrammar)))
/*      */               {
/* 1587 */                 println(this.labeledElementType + " " + localAlternativeElement.getLabel() + " = " + this.labeledElementInit + ";");
/* 1588 */                 if (this.grammar.buildAST)
/* 1589 */                   genASTDeclaration(localAlternativeElement);
/*      */               }
/*      */               else
/*      */               {
/* 1593 */                 if (this.grammar.buildAST)
/*      */                 {
/* 1597 */                   genASTDeclaration(localAlternativeElement);
/*      */                 }
/* 1599 */                 if ((this.grammar instanceof LexerGrammar)) {
/* 1600 */                   println("Token " + localAlternativeElement.getLabel() + "=null;");
/*      */                 }
/* 1602 */                 if ((this.grammar instanceof TreeWalkerGrammar))
/*      */                 {
/* 1605 */                   println(this.labeledElementType + " " + localAlternativeElement.getLabel() + " = " + this.labeledElementInit + ";");
/*      */                 }
/*      */               }
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/* 1612 */               println(this.labeledElementType + " " + localAlternativeElement.getLabel() + " = " + this.labeledElementInit + ";");
/*      */ 
/* 1616 */               if (this.grammar.buildAST)
/* 1617 */                 if (((localAlternativeElement instanceof GrammarAtom)) && (((GrammarAtom)localAlternativeElement).getASTNodeType() != null))
/*      */                 {
/* 1619 */                   GrammarAtom localGrammarAtom = (GrammarAtom)localAlternativeElement;
/* 1620 */                   genASTDeclaration(localAlternativeElement, localGrammarAtom.getASTNodeType());
/*      */                 }
/*      */                 else {
/* 1623 */                   genASTDeclaration(localAlternativeElement);
/*      */                 }
/*      */             }
/*      */           }
/*      */           finally {
/* 1628 */             this.defaultLine = j;
/*      */           }
/*      */         }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void genCases(BitSet paramBitSet, int paramInt)
/*      */   {
/* 1639 */     int i = this.defaultLine;
/*      */     try {
/* 1641 */       this.defaultLine = paramInt;
/* 1642 */       if (this.DEBUG_CODE_GENERATOR) System.out.println("genCases(" + paramBitSet + ")");
/*      */ 
/* 1645 */       int[] arrayOfInt = paramBitSet.toArray();
/*      */ 
/* 1647 */       int j = (this.grammar instanceof LexerGrammar) ? 4 : 1;
/* 1648 */       int k = 1;
/* 1649 */       int m = 1;
/* 1650 */       for (int n = 0; n < arrayOfInt.length; n++) {
/* 1651 */         if (k == 1) {
/* 1652 */           print("");
/*      */         }
/*      */         else {
/* 1655 */           _print("  ");
/*      */         }
/* 1657 */         _print("case " + getValueString(arrayOfInt[n]) + ":");
/*      */ 
/* 1659 */         if (k == j) {
/* 1660 */           _println("");
/* 1661 */           m = 1;
/* 1662 */           k = 1;
/*      */         }
/*      */         else {
/* 1665 */           k++;
/* 1666 */           m = 0;
/*      */         }
/*      */       }
/* 1669 */       if (m == 0)
/* 1670 */         _println("");
/*      */     }
/*      */     finally {
/* 1673 */       this.defaultLine = i;
/*      */     }
/*      */   }
/*      */ 
/*      */   public JavaBlockFinishingInfo genCommonBlock(AlternativeBlock paramAlternativeBlock, boolean paramBoolean)
/*      */   {
/* 1690 */     int i = this.defaultLine;
/*      */     try {
/* 1692 */       this.defaultLine = paramAlternativeBlock.getLine();
/* 1693 */       int j = 0;
/* 1694 */       int k = 0;
/* 1695 */       int m = 0;
/* 1696 */       JavaBlockFinishingInfo localJavaBlockFinishingInfo1 = new JavaBlockFinishingInfo();
/* 1697 */       if (this.DEBUG_CODE_GENERATOR) System.out.println("genCommonBlock(" + paramAlternativeBlock + ")");
/*      */ 
/* 1700 */       boolean bool1 = this.genAST;
/* 1701 */       this.genAST = ((this.genAST) && (paramAlternativeBlock.getAutoGen()));
/*      */ 
/* 1703 */       boolean bool2 = this.saveText;
/* 1704 */       this.saveText = ((this.saveText) && (paramAlternativeBlock.getAutoGen()));
/*      */       Object localObject1;
/*      */       Object localObject2;
/*      */       Object localObject3;
/* 1707 */       if ((paramAlternativeBlock.not) && (this.analyzer.subruleCanBeInverted(paramAlternativeBlock, this.grammar instanceof LexerGrammar)))
/*      */       {
/* 1711 */         if (this.DEBUG_CODE_GENERATOR) System.out.println("special case: ~(subrule)");
/* 1712 */         localObject1 = this.analyzer.look(1, paramAlternativeBlock);
/*      */ 
/* 1714 */         if ((paramAlternativeBlock.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/* 1715 */           println(paramAlternativeBlock.getLabel() + " = " + this.lt1Value + ";");
/*      */         }
/*      */ 
/* 1719 */         genElementAST(paramAlternativeBlock);
/*      */ 
/* 1721 */         localObject2 = "";
/* 1722 */         if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1723 */           localObject2 = "_t,";
/*      */         }
/*      */ 
/* 1727 */         println("match(" + (String)localObject2 + getBitsetName(markBitsetForGen(((Lookahead)localObject1).fset)) + ");");
/*      */ 
/* 1730 */         if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1731 */           println("_t = _t.getNextSibling();");
/*      */         }
/* 1733 */         return localJavaBlockFinishingInfo1;
/*      */       }
/*      */ 
/* 1737 */       if (paramAlternativeBlock.getAlternatives().size() == 1) {
/* 1738 */         localObject1 = paramAlternativeBlock.getAlternativeAt(0);
/*      */ 
/* 1740 */         if (((Alternative)localObject1).synPred != null) {
/* 1741 */           this.antlrTool.warning("Syntactic predicate superfluous for single alternative", this.grammar.getFilename(), paramAlternativeBlock.getAlternativeAt(0).synPred.getLine(), paramAlternativeBlock.getAlternativeAt(0).synPred.getColumn());
/*      */         }
/*      */ 
/* 1748 */         if (paramBoolean) {
/* 1749 */           if (((Alternative)localObject1).semPred != null)
/*      */           {
/* 1751 */             genSemPred(((Alternative)localObject1).semPred, paramAlternativeBlock.line);
/*      */           }
/* 1753 */           genAlt((Alternative)localObject1, paramAlternativeBlock);
/* 1754 */           return localJavaBlockFinishingInfo1;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1767 */       int n = 0;
/* 1768 */       for (int i1 = 0; i1 < paramAlternativeBlock.getAlternatives().size(); i1++) {
/* 1769 */         localObject3 = paramAlternativeBlock.getAlternativeAt(i1);
/* 1770 */         if (suitableForCaseExpression((Alternative)localObject3))
/* 1771 */           n++;
/*      */       }
/*      */       Object localObject4;
/* 1776 */       if (n >= this.makeSwitchThreshold)
/*      */       {
/* 1778 */         String str1 = lookaheadString(1);
/* 1779 */         k = 1;
/*      */ 
/* 1781 */         if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1782 */           println("if (_t==null) _t=ASTNULL;");
/*      */         }
/* 1784 */         println("switch ( " + str1 + ") {");
/* 1785 */         for (i3 = 0; i3 < paramAlternativeBlock.alternatives.size(); i3++) {
/* 1786 */           Alternative localAlternative = paramAlternativeBlock.getAlternativeAt(i3);
/*      */ 
/* 1789 */           if (suitableForCaseExpression(localAlternative))
/*      */           {
/* 1792 */             localObject4 = localAlternative.cache[1];
/* 1793 */             if ((((Lookahead)localObject4).fset.degree() == 0) && (!((Lookahead)localObject4).containsEpsilon())) {
/* 1794 */               this.antlrTool.warning("Alternate omitted due to empty prediction set", this.grammar.getFilename(), localAlternative.head.getLine(), localAlternative.head.getColumn());
/*      */             }
/*      */             else
/*      */             {
/* 1799 */               genCases(((Lookahead)localObject4).fset, localAlternative.head.getLine());
/* 1800 */               println("{", localAlternative.head.getLine());
/* 1801 */               this.tabs += 1;
/* 1802 */               genAlt(localAlternative, paramAlternativeBlock);
/* 1803 */               println("break;", -999);
/* 1804 */               this.tabs -= 1;
/* 1805 */               println("}", -999);
/*      */             }
/*      */           }
/*      */         }
/* 1808 */         println("default:");
/* 1809 */         this.tabs += 1;
/*      */       }
/*      */ 
/* 1825 */       int i2 = (this.grammar instanceof LexerGrammar) ? this.grammar.maxk : 0;
/* 1826 */       for (int i3 = i2; i3 >= 0; i3--) {
/* 1827 */         if (this.DEBUG_CODE_GENERATOR) System.out.println("checking depth " + i3);
/* 1828 */         for (i4 = 0; i4 < paramAlternativeBlock.alternatives.size(); i4++) {
/* 1829 */           localObject4 = paramAlternativeBlock.getAlternativeAt(i4);
/* 1830 */           if (this.DEBUG_CODE_GENERATOR) System.out.println("genAlt: " + i4);
/*      */ 
/* 1835 */           if ((k != 0) && (suitableForCaseExpression((Alternative)localObject4))) {
/* 1836 */             if (this.DEBUG_CODE_GENERATOR) System.out.println("ignoring alt because it was in the switch");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 1841 */             boolean bool3 = false;
/*      */             String str3;
/* 1843 */             if ((this.grammar instanceof LexerGrammar))
/*      */             {
/* 1847 */               i5 = ((Alternative)localObject4).lookaheadDepth;
/* 1848 */               if (i5 == 2147483647)
/*      */               {
/* 1850 */                 i5 = this.grammar.maxk;
/*      */               }
/* 1852 */               while ((i5 >= 1) && (localObject4.cache[i5].containsEpsilon()))
/*      */               {
/* 1854 */                 i5--;
/*      */               }
/*      */ 
/* 1858 */               if (i5 != i3) {
/* 1859 */                 if (!this.DEBUG_CODE_GENERATOR) continue;
/* 1860 */                 System.out.println("ignoring alt because effectiveDepth!=altDepth;" + i5 + "!=" + i3); continue;
/*      */               }
/*      */ 
/* 1863 */               bool3 = lookaheadIsEmpty((Alternative)localObject4, i5);
/* 1864 */               str3 = getLookaheadTestExpression((Alternative)localObject4, i5);
/*      */             }
/*      */             else {
/* 1867 */               bool3 = lookaheadIsEmpty((Alternative)localObject4, this.grammar.maxk);
/* 1868 */               str3 = getLookaheadTestExpression((Alternative)localObject4, this.grammar.maxk);
/*      */             }
/*      */ 
/* 1871 */             int i5 = this.defaultLine;
/*      */             try {
/* 1873 */               this.defaultLine = ((Alternative)localObject4).head.getLine();
/*      */ 
/* 1876 */               if ((localObject4.cache[1].fset.degree() > 127) && (suitableForCaseExpression((Alternative)localObject4)))
/*      */               {
/* 1878 */                 if (j == 0) {
/* 1879 */                   println("if " + str3 + " {");
/*      */                 }
/*      */                 else {
/* 1882 */                   println("else if " + str3 + " {");
/*      */                 }
/*      */               }
/* 1885 */               else if ((bool3) && (((Alternative)localObject4).semPred == null) && (((Alternative)localObject4).synPred == null))
/*      */               {
/* 1892 */                 if (j == 0) {
/* 1893 */                   println("{");
/*      */                 }
/*      */                 else {
/* 1896 */                   println("else {");
/*      */                 }
/* 1898 */                 localJavaBlockFinishingInfo1.needAnErrorClause = false;
/*      */               }
/*      */               else
/*      */               {
/* 1904 */                 if (((Alternative)localObject4).semPred != null)
/*      */                 {
/* 1908 */                   ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 1909 */                   String str4 = processActionForSpecialSymbols(((Alternative)localObject4).semPred, paramAlternativeBlock.line, this.currentRule, localActionTransInfo);
/*      */ 
/* 1917 */                   if ((((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar))) && (this.grammar.debuggingOutput))
/*      */                   {
/* 1920 */                     str3 = "(" + str3 + "&& fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEvent.PREDICTING," + addSemPred(this.charFormatter.escapeString(str4)) + "," + str4 + "))";
/*      */                   }
/*      */                   else
/*      */                   {
/* 1924 */                     str3 = "(" + str3 + "&&(" + str4 + "))";
/*      */                   }
/*      */ 
/*      */                 }
/*      */ 
/* 1929 */                 if (j > 0) {
/* 1930 */                   if (((Alternative)localObject4).synPred != null) {
/* 1931 */                     println("else {", ((Alternative)localObject4).synPred.getLine());
/* 1932 */                     this.tabs += 1;
/* 1933 */                     genSynPred(((Alternative)localObject4).synPred, str3);
/* 1934 */                     m++;
/*      */                   }
/*      */                   else {
/* 1937 */                     println("else if " + str3 + " {");
/*      */                   }
/*      */ 
/*      */                 }
/* 1941 */                 else if (((Alternative)localObject4).synPred != null) {
/* 1942 */                   genSynPred(((Alternative)localObject4).synPred, str3);
/*      */                 }
/*      */                 else
/*      */                 {
/* 1947 */                   if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1948 */                     println("if (_t==null) _t=ASTNULL;");
/*      */                   }
/* 1950 */                   println("if " + str3 + " {");
/*      */                 }
/*      */               }
/*      */ 
/*      */             }
/*      */             finally
/*      */             {
/*      */             }
/*      */ 
/* 1959 */             j++;
/* 1960 */             this.tabs += 1;
/* 1961 */             genAlt((Alternative)localObject4, paramAlternativeBlock);
/* 1962 */             this.tabs -= 1;
/* 1963 */             println("}");
/*      */           }
/*      */         }
/*      */       }
/* 1966 */       String str2 = "";
/* 1967 */       for (int i4 = 1; i4 <= m; i4++) {
/* 1968 */         str2 = str2 + "}";
/*      */       }
/*      */ 
/* 1972 */       this.genAST = bool1;
/*      */ 
/* 1975 */       this.saveText = bool2;
/*      */ 
/* 1978 */       if (k != 0) {
/* 1979 */         this.tabs -= 1;
/* 1980 */         localJavaBlockFinishingInfo1.postscript = (str2 + "}");
/* 1981 */         localJavaBlockFinishingInfo1.generatedSwitch = true;
/* 1982 */         localJavaBlockFinishingInfo1.generatedAnIf = (j > 0);
/*      */       }
/*      */       else
/*      */       {
/* 1987 */         localJavaBlockFinishingInfo1.postscript = str2;
/* 1988 */         localJavaBlockFinishingInfo1.generatedSwitch = false;
/* 1989 */         localJavaBlockFinishingInfo1.generatedAnIf = (j > 0);
/*      */       }
/*      */ 
/* 1992 */       return localJavaBlockFinishingInfo1;
/*      */     } finally {
/* 1994 */       this.defaultLine = i;
/*      */     }
/*      */   }
/*      */ 
/*      */   private static boolean suitableForCaseExpression(Alternative paramAlternative) {
/* 1999 */     return (paramAlternative.lookaheadDepth == 1) && (paramAlternative.semPred == null) && (!paramAlternative.cache[1].containsEpsilon()) && (paramAlternative.cache[1].fset.degree() <= 127);
/*      */   }
/*      */ 
/*      */   private void genElementAST(AlternativeElement paramAlternativeElement)
/*      */   {
/* 2008 */     int i = this.defaultLine;
/*      */     try {
/* 2010 */       this.defaultLine = paramAlternativeElement.getLine();
/*      */ 
/* 2013 */       if (((this.grammar instanceof TreeWalkerGrammar)) && (!this.grammar.buildAST))
/*      */       {
/* 2018 */         if (paramAlternativeElement.getLabel() == null) {
/* 2019 */           String str1 = this.lt1Value;
/*      */ 
/* 2021 */           String str2 = "tmp" + this.astVarNumber + "_AST";
/* 2022 */           this.astVarNumber += 1;
/*      */ 
/* 2024 */           mapTreeVariable(paramAlternativeElement, str2);
/*      */ 
/* 2026 */           println(this.labeledElementASTType + " " + str2 + "_in = " + str1 + ";");
/*      */         }
/*      */ 
/*      */       }
/* 2031 */       else if ((this.grammar.buildAST) && (this.syntacticPredLevel == 0)) {
/* 2032 */         int j = (this.genAST) && ((paramAlternativeElement.getLabel() != null) || (paramAlternativeElement.getAutoGenType() != 3)) ? 1 : 0;
/*      */ 
/* 2043 */         if ((paramAlternativeElement.getAutoGenType() != 3) && ((paramAlternativeElement instanceof TokenRefElement)))
/*      */         {
/* 2046 */           j = 1;
/*      */         }
/*      */ 
/* 2049 */         int k = (this.grammar.hasSyntacticPredicate) && (j != 0) ? 1 : 0;
/*      */         String str3;
/*      */         String str4;
/* 2056 */         if (paramAlternativeElement.getLabel() != null) {
/* 2057 */           str3 = paramAlternativeElement.getLabel();
/* 2058 */           str4 = paramAlternativeElement.getLabel();
/*      */         }
/*      */         else {
/* 2061 */           str3 = this.lt1Value;
/*      */ 
/* 2063 */           str4 = "tmp" + this.astVarNumber;
/*      */ 
/* 2065 */           this.astVarNumber += 1;
/*      */         }
/*      */ 
/* 2069 */         if (j != 0)
/*      */         {
/* 2071 */           if ((paramAlternativeElement instanceof GrammarAtom)) {
/* 2072 */             localObject1 = (GrammarAtom)paramAlternativeElement;
/* 2073 */             if (((GrammarAtom)localObject1).getASTNodeType() != null) {
/* 2074 */               genASTDeclaration(paramAlternativeElement, str4, ((GrammarAtom)localObject1).getASTNodeType());
/*      */             }
/*      */             else
/*      */             {
/* 2078 */               genASTDeclaration(paramAlternativeElement, str4, this.labeledElementASTType);
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/* 2083 */             genASTDeclaration(paramAlternativeElement, str4, this.labeledElementASTType);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2089 */         Object localObject1 = str4 + "_AST";
/*      */ 
/* 2092 */         mapTreeVariable(paramAlternativeElement, (String)localObject1);
/* 2093 */         if ((this.grammar instanceof TreeWalkerGrammar))
/*      */         {
/* 2095 */           println(this.labeledElementASTType + " " + (String)localObject1 + "_in = null;");
/*      */         }
/*      */ 
/* 2099 */         if ((k == 0) || 
/* 2106 */           (paramAlternativeElement.getLabel() != null)) {
/* 2107 */           if ((paramAlternativeElement instanceof GrammarAtom)) {
/* 2108 */             println((String)localObject1 + " = " + getASTCreateString((GrammarAtom)paramAlternativeElement, str3) + ";");
/*      */           }
/*      */           else {
/* 2111 */             println((String)localObject1 + " = " + getASTCreateString(str3) + ";");
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2116 */         if ((paramAlternativeElement.getLabel() == null) && (j != 0)) {
/* 2117 */           str3 = this.lt1Value;
/* 2118 */           if ((paramAlternativeElement instanceof GrammarAtom)) {
/* 2119 */             println((String)localObject1 + " = " + getASTCreateString((GrammarAtom)paramAlternativeElement, str3) + ";");
/*      */           }
/*      */           else {
/* 2122 */             println((String)localObject1 + " = " + getASTCreateString(str3) + ";");
/*      */           }
/*      */ 
/* 2125 */           if ((this.grammar instanceof TreeWalkerGrammar))
/*      */           {
/* 2127 */             println((String)localObject1 + "_in = " + str3 + ";");
/*      */           }
/*      */         }
/*      */ 
/* 2131 */         if (this.genAST) {
/* 2132 */           switch (paramAlternativeElement.getAutoGenType()) {
/*      */           case 1:
/* 2134 */             println("astFactory.addASTChild(currentAST, " + (String)localObject1 + ");");
/* 2135 */             break;
/*      */           case 2:
/* 2137 */             println("astFactory.makeASTRoot(currentAST, " + (String)localObject1 + ");");
/* 2138 */             break;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2143 */         if (k == 0);
/*      */       }
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/* 2149 */       this.defaultLine = i;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void genErrorCatchForElement(AlternativeElement paramAlternativeElement)
/*      */   {
/* 2157 */     if (paramAlternativeElement.getLabel() == null) return;
/* 2158 */     String str = paramAlternativeElement.enclosingRuleName;
/* 2159 */     if ((this.grammar instanceof LexerGrammar)) {
/* 2160 */       str = CodeGenerator.encodeLexerRuleName(paramAlternativeElement.enclosingRuleName);
/*      */     }
/* 2162 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(str);
/* 2163 */     if (localRuleSymbol == null) {
/* 2164 */       this.antlrTool.panic("Enclosing rule not found!");
/*      */     }
/* 2166 */     ExceptionSpec localExceptionSpec = localRuleSymbol.block.findExceptionSpec(paramAlternativeElement.getLabel());
/* 2167 */     if (localExceptionSpec != null) {
/* 2168 */       this.tabs -= 1;
/* 2169 */       println("}", paramAlternativeElement.getLine());
/* 2170 */       genErrorHandler(localExceptionSpec);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void genErrorHandler(ExceptionSpec paramExceptionSpec)
/*      */   {
/* 2177 */     for (int i = 0; i < paramExceptionSpec.handlers.size(); i++) {
/* 2178 */       ExceptionHandler localExceptionHandler = (ExceptionHandler)paramExceptionSpec.handlers.elementAt(i);
/* 2179 */       int j = this.defaultLine;
/*      */       try {
/* 2181 */         this.defaultLine = localExceptionHandler.action.getLine();
/*      */ 
/* 2183 */         println("catch (" + localExceptionHandler.exceptionTypeAndName.getText() + ") {", localExceptionHandler.exceptionTypeAndName.getLine());
/* 2184 */         this.tabs += 1;
/* 2185 */         if (this.grammar.hasSyntacticPredicate) {
/* 2186 */           println("if (inputState.guessing==0) {");
/* 2187 */           this.tabs += 1;
/*      */         }
/*      */ 
/* 2191 */         ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 2192 */         printAction(processActionForSpecialSymbols(localExceptionHandler.action.getText(), localExceptionHandler.action.getLine(), this.currentRule, localActionTransInfo));
/*      */ 
/* 2198 */         if (this.grammar.hasSyntacticPredicate) {
/* 2199 */           this.tabs -= 1;
/* 2200 */           println("} else {");
/* 2201 */           this.tabs += 1;
/*      */ 
/* 2203 */           println("throw " + extractIdOfAction(localExceptionHandler.exceptionTypeAndName) + ";");
/*      */ 
/* 2208 */           this.tabs -= 1;
/* 2209 */           println("}");
/*      */         }
/*      */ 
/* 2212 */         this.tabs -= 1;
/* 2213 */         println("}");
/*      */       } finally {
/* 2215 */         this.defaultLine = j;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void genErrorTryForElement(AlternativeElement paramAlternativeElement)
/*      */   {
/* 2222 */     if (paramAlternativeElement.getLabel() == null) return;
/* 2223 */     String str = paramAlternativeElement.enclosingRuleName;
/* 2224 */     if ((this.grammar instanceof LexerGrammar)) {
/* 2225 */       str = CodeGenerator.encodeLexerRuleName(paramAlternativeElement.enclosingRuleName);
/*      */     }
/* 2227 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(str);
/* 2228 */     if (localRuleSymbol == null) {
/* 2229 */       this.antlrTool.panic("Enclosing rule not found!");
/*      */     }
/* 2231 */     ExceptionSpec localExceptionSpec = localRuleSymbol.block.findExceptionSpec(paramAlternativeElement.getLabel());
/* 2232 */     if (localExceptionSpec != null) {
/* 2233 */       println("try { // for error handling", paramAlternativeElement.getLine());
/* 2234 */       this.tabs += 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void genASTDeclaration(AlternativeElement paramAlternativeElement) {
/* 2239 */     genASTDeclaration(paramAlternativeElement, this.labeledElementASTType);
/*      */   }
/*      */ 
/*      */   protected void genASTDeclaration(AlternativeElement paramAlternativeElement, String paramString) {
/* 2243 */     genASTDeclaration(paramAlternativeElement, paramAlternativeElement.getLabel(), paramString);
/*      */   }
/*      */ 
/*      */   protected void genASTDeclaration(AlternativeElement paramAlternativeElement, String paramString1, String paramString2)
/*      */   {
/* 2248 */     if (this.declaredASTVariables.contains(paramAlternativeElement)) {
/* 2249 */       return;
/*      */     }
/*      */ 
/* 2252 */     println(paramString2 + " " + paramString1 + "_AST = null;");
/*      */ 
/* 2255 */     this.declaredASTVariables.put(paramAlternativeElement, paramAlternativeElement);
/*      */   }
/*      */ 
/*      */   protected void genHeader()
/*      */   {
/* 2260 */     println("// $ANTLR " + Tool.version + ": " + "\"" + this.antlrTool.fileMinusPath(this.antlrTool.grammarFile) + "\"" + " -> " + "\"" + this.grammar.getClassName() + ".java\"$", -999);
/*      */   }
/*      */ 
/*      */   private void genLiteralsTest()
/*      */   {
/* 2267 */     println("_ttype = testLiteralsTable(_ttype);");
/*      */   }
/*      */ 
/*      */   private void genLiteralsTestForPartialToken() {
/* 2271 */     println("_ttype = testLiteralsTable(new String(text.getBuffer(),_begin,text.length()-_begin),_ttype);");
/*      */   }
/*      */ 
/*      */   protected void genMatch(BitSet paramBitSet) {
/*      */   }
/*      */ 
/*      */   protected void genMatch(GrammarAtom paramGrammarAtom) {
/* 2278 */     if ((paramGrammarAtom instanceof StringLiteralElement)) {
/* 2279 */       if ((this.grammar instanceof LexerGrammar)) {
/* 2280 */         genMatchUsingAtomText(paramGrammarAtom);
/*      */       }
/*      */       else {
/* 2283 */         genMatchUsingAtomTokenType(paramGrammarAtom);
/*      */       }
/*      */     }
/* 2286 */     else if ((paramGrammarAtom instanceof CharLiteralElement)) {
/* 2287 */       if ((this.grammar instanceof LexerGrammar)) {
/* 2288 */         genMatchUsingAtomText(paramGrammarAtom);
/*      */       }
/*      */       else {
/* 2291 */         this.antlrTool.error("cannot ref character literals in grammar: " + paramGrammarAtom);
/*      */       }
/*      */     }
/* 2294 */     else if ((paramGrammarAtom instanceof TokenRefElement)) {
/* 2295 */       genMatchUsingAtomText(paramGrammarAtom);
/*      */     }
/* 2297 */     else if ((paramGrammarAtom instanceof WildcardElement))
/* 2298 */       gen((WildcardElement)paramGrammarAtom);
/*      */   }
/*      */ 
/*      */   protected void genMatchUsingAtomText(GrammarAtom paramGrammarAtom)
/*      */   {
/* 2303 */     int i = this.defaultLine;
/*      */     try {
/* 2305 */       this.defaultLine = paramGrammarAtom.getLine();
/*      */ 
/* 2307 */       String str = "";
/* 2308 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2309 */         str = "_t,";
/*      */       }
/*      */ 
/* 2313 */       if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramGrammarAtom.getAutoGenType() == 3))) {
/* 2314 */         println("_saveIndex=text.length();");
/*      */       }
/*      */ 
/* 2317 */       print(paramGrammarAtom.not ? "matchNot(" : "match(");
/* 2318 */       _print(str, -999);
/*      */ 
/* 2321 */       if (paramGrammarAtom.atomText.equals("EOF"))
/*      */       {
/* 2323 */         _print("Token.EOF_TYPE");
/*      */       }
/*      */       else {
/* 2326 */         _print(paramGrammarAtom.atomText);
/*      */       }
/* 2328 */       _println(");");
/*      */ 
/* 2330 */       if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramGrammarAtom.getAutoGenType() == 3)))
/* 2331 */         println("text.setLength(_saveIndex);");
/*      */     }
/*      */     finally {
/* 2334 */       this.defaultLine = i;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void genMatchUsingAtomTokenType(GrammarAtom paramGrammarAtom)
/*      */   {
/* 2340 */     String str1 = "";
/* 2341 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2342 */       str1 = "_t,";
/*      */     }
/*      */ 
/* 2346 */     Object localObject = null;
/* 2347 */     String str2 = str1 + getValueString(paramGrammarAtom.getType());
/*      */ 
/* 2350 */     println((paramGrammarAtom.not ? "matchNot(" : "match(") + str2 + ");", paramGrammarAtom.getLine());
/*      */   }
/*      */ 
/*      */   public void genNextToken()
/*      */   {
/* 2358 */     int i = this.defaultLine;
/*      */     try {
/* 2360 */       this.defaultLine = -999;
/*      */ 
/* 2363 */       int j = 0;
/*      */       RuleSymbol localRuleSymbol1;
/* 2364 */       for (int k = 0; k < this.grammar.rules.size(); k++) {
/* 2365 */         localRuleSymbol1 = (RuleSymbol)this.grammar.rules.elementAt(k);
/* 2366 */         if ((localRuleSymbol1.isDefined()) && (localRuleSymbol1.access.equals("public"))) {
/* 2367 */           j = 1;
/* 2368 */           break;
/*      */         }
/*      */       }
/* 2371 */       if (j == 0) {
/* 2372 */         println("");
/* 2373 */         println("public Token nextToken() throws TokenStreamException {");
/* 2374 */         println("\ttry {uponEOF();}");
/* 2375 */         println("\tcatch(CharStreamIOException csioe) {");
/* 2376 */         println("\t\tthrow new TokenStreamIOException(csioe.io);");
/* 2377 */         println("\t}");
/* 2378 */         println("\tcatch(CharStreamException cse) {");
/* 2379 */         println("\t\tthrow new TokenStreamException(cse.getMessage());");
/* 2380 */         println("\t}");
/* 2381 */         println("\treturn new CommonToken(Token.EOF_TYPE, \"\");");
/* 2382 */         println("}");
/* 2383 */         println("");
/*      */       }
/*      */       else
/*      */       {
/* 2388 */         RuleBlock localRuleBlock = MakeGrammar.createNextTokenRule(this.grammar, this.grammar.rules, "nextToken");
/*      */ 
/* 2390 */         localRuleSymbol1 = new RuleSymbol("mnextToken");
/* 2391 */         localRuleSymbol1.setDefined();
/* 2392 */         localRuleSymbol1.setBlock(localRuleBlock);
/* 2393 */         localRuleSymbol1.access = "private";
/* 2394 */         this.grammar.define(localRuleSymbol1);
/*      */ 
/* 2396 */         boolean bool = this.grammar.theLLkAnalyzer.deterministic(localRuleBlock);
/*      */ 
/* 2399 */         String str1 = null;
/* 2400 */         if (((LexerGrammar)this.grammar).filterMode) {
/* 2401 */           str1 = ((LexerGrammar)this.grammar).filterRule;
/*      */         }
/*      */ 
/* 2404 */         println("");
/* 2405 */         println("public Token nextToken() throws TokenStreamException {");
/* 2406 */         this.tabs += 1;
/* 2407 */         println("Token theRetToken=null;");
/* 2408 */         _println("tryAgain:");
/* 2409 */         println("for (;;) {");
/* 2410 */         this.tabs += 1;
/* 2411 */         println("Token _token = null;");
/* 2412 */         println("int _ttype = Token.INVALID_TYPE;");
/* 2413 */         if (((LexerGrammar)this.grammar).filterMode) {
/* 2414 */           println("setCommitToPath(false);");
/* 2415 */           if (str1 != null)
/*      */           {
/* 2417 */             if (!this.grammar.isDefined(CodeGenerator.encodeLexerRuleName(str1))) {
/* 2418 */               this.grammar.antlrTool.error("Filter rule " + str1 + " does not exist in this lexer");
/*      */             }
/*      */             else {
/* 2421 */               RuleSymbol localRuleSymbol2 = (RuleSymbol)this.grammar.getSymbol(CodeGenerator.encodeLexerRuleName(str1));
/* 2422 */               if (!localRuleSymbol2.isDefined()) {
/* 2423 */                 this.grammar.antlrTool.error("Filter rule " + str1 + " does not exist in this lexer");
/*      */               }
/* 2425 */               else if (localRuleSymbol2.access.equals("public")) {
/* 2426 */                 this.grammar.antlrTool.error("Filter rule " + str1 + " must be protected");
/*      */               }
/*      */             }
/* 2429 */             println("int _m;");
/* 2430 */             println("_m = mark();");
/*      */           }
/*      */         }
/* 2433 */         println("resetText();");
/*      */ 
/* 2435 */         println("try {   // for char stream error handling");
/* 2436 */         this.tabs += 1;
/*      */ 
/* 2439 */         println("try {   // for lexical error handling");
/* 2440 */         this.tabs += 1;
/*      */ 
/* 2443 */         for (int m = 0; m < localRuleBlock.getAlternatives().size(); m++) {
/* 2444 */           localObject1 = localRuleBlock.getAlternativeAt(m);
/* 2445 */           if (localObject1.cache[1].containsEpsilon())
/*      */           {
/* 2447 */             localObject2 = (RuleRefElement)((Alternative)localObject1).head;
/* 2448 */             String str3 = CodeGenerator.decodeLexerRuleName(((RuleRefElement)localObject2).targetRule);
/* 2449 */             this.antlrTool.warning("public lexical rule " + str3 + " is optional (can match \"nothing\")");
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2454 */         String str2 = System.getProperty("line.separator");
/* 2455 */         Object localObject1 = genCommonBlock(localRuleBlock, false);
/* 2456 */         Object localObject2 = "if (LA(1)==EOF_CHAR) {uponEOF(); _returnToken = makeToken(Token.EOF_TYPE);}";
/* 2457 */         localObject2 = (String)localObject2 + str2 + "\t\t\t\t";
/* 2458 */         if (((LexerGrammar)this.grammar).filterMode) {
/* 2459 */           if (str1 == null) {
/* 2460 */             localObject2 = (String)localObject2 + "else {consume(); continue tryAgain;}";
/*      */           }
/*      */           else {
/* 2463 */             localObject2 = (String)localObject2 + "else {" + str2 + "\t\t\t\t\tcommit();" + str2 + "\t\t\t\t\ttry {m" + str1 + "(false);}" + str2 + "\t\t\t\t\tcatch(RecognitionException e) {" + str2 + "\t\t\t\t\t\t// catastrophic failure" + str2 + "\t\t\t\t\t\treportError(e);" + str2 + "\t\t\t\t\t\tconsume();" + str2 + "\t\t\t\t\t}" + str2 + "\t\t\t\t\tcontinue tryAgain;" + str2 + "\t\t\t\t}";
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 2476 */           localObject2 = (String)localObject2 + "else {" + this.throwNoViable + "}";
/*      */         }
/* 2478 */         genBlockFinish((JavaBlockFinishingInfo)localObject1, (String)localObject2, localRuleBlock.getLine());
/*      */ 
/* 2481 */         if ((((LexerGrammar)this.grammar).filterMode) && (str1 != null)) {
/* 2482 */           println("commit();");
/*      */         }
/*      */ 
/* 2488 */         println("if ( _returnToken==null ) continue tryAgain; // found SKIP token");
/* 2489 */         println("_ttype = _returnToken.getType();");
/* 2490 */         if (((LexerGrammar)this.grammar).getTestLiterals()) {
/* 2491 */           genLiteralsTest();
/*      */         }
/*      */ 
/* 2495 */         println("_returnToken.setType(_ttype);");
/* 2496 */         println("return _returnToken;");
/*      */ 
/* 2499 */         this.tabs -= 1;
/* 2500 */         println("}");
/* 2501 */         println("catch (RecognitionException e) {");
/* 2502 */         this.tabs += 1;
/* 2503 */         if (((LexerGrammar)this.grammar).filterMode) {
/* 2504 */           if (str1 == null) {
/* 2505 */             println("if ( !getCommitToPath() ) {consume(); continue tryAgain;}");
/*      */           }
/*      */           else {
/* 2508 */             println("if ( !getCommitToPath() ) {");
/* 2509 */             this.tabs += 1;
/* 2510 */             println("rewind(_m);");
/* 2511 */             println("resetText();");
/* 2512 */             println("try {m" + str1 + "(false);}");
/* 2513 */             println("catch(RecognitionException ee) {");
/* 2514 */             println("\t// horrendous failure: error in filter rule");
/* 2515 */             println("\treportError(ee);");
/* 2516 */             println("\tconsume();");
/* 2517 */             println("}");
/* 2518 */             println("continue tryAgain;");
/* 2519 */             this.tabs -= 1;
/* 2520 */             println("}");
/*      */           }
/*      */         }
/* 2523 */         if (localRuleBlock.getDefaultErrorHandler()) {
/* 2524 */           println("reportError(e);");
/* 2525 */           println("consume();");
/*      */         }
/*      */         else
/*      */         {
/* 2529 */           println("throw new TokenStreamRecognitionException(e);");
/*      */         }
/* 2531 */         this.tabs -= 1;
/* 2532 */         println("}");
/*      */ 
/* 2535 */         this.tabs -= 1;
/* 2536 */         println("}");
/* 2537 */         println("catch (CharStreamException cse) {");
/* 2538 */         println("\tif ( cse instanceof CharStreamIOException ) {");
/* 2539 */         println("\t\tthrow new TokenStreamIOException(((CharStreamIOException)cse).io);");
/* 2540 */         println("\t}");
/* 2541 */         println("\telse {");
/* 2542 */         println("\t\tthrow new TokenStreamException(cse.getMessage());");
/* 2543 */         println("\t}");
/* 2544 */         println("}");
/*      */ 
/* 2547 */         this.tabs -= 1;
/* 2548 */         println("}");
/*      */ 
/* 2551 */         this.tabs -= 1;
/* 2552 */         println("}");
/* 2553 */         println("");
/*      */       }
/*      */     } finally { this.defaultLine = i; }
/*      */ 
/*      */   }
/*      */ 
/*      */   public void genRule(RuleSymbol paramRuleSymbol, boolean paramBoolean, int paramInt)
/*      */   {
/* 2576 */     this.tabs = 1;
/*      */ 
/* 2578 */     if (this.DEBUG_CODE_GENERATOR) System.out.println("genRule(" + paramRuleSymbol.getId() + ")");
/* 2579 */     if (!paramRuleSymbol.isDefined()) {
/* 2580 */       this.antlrTool.error("undefined rule: " + paramRuleSymbol.getId());
/* 2581 */       return;
/*      */     }
/*      */ 
/* 2585 */     RuleBlock localRuleBlock = paramRuleSymbol.getBlock();
/*      */ 
/* 2587 */     int i = this.defaultLine;
/*      */     try {
/* 2589 */       this.defaultLine = localRuleBlock.getLine();
/* 2590 */       this.currentRule = localRuleBlock;
/* 2591 */       this.currentASTResult = paramRuleSymbol.getId();
/*      */ 
/* 2594 */       this.declaredASTVariables.clear();
/*      */ 
/* 2597 */       boolean bool1 = this.genAST;
/* 2598 */       this.genAST = ((this.genAST) && (localRuleBlock.getAutoGen()));
/*      */ 
/* 2601 */       this.saveText = localRuleBlock.getAutoGen();
/*      */ 
/* 2604 */       if (paramRuleSymbol.comment != null) {
/* 2605 */         _println(paramRuleSymbol.comment);
/*      */       }
/*      */ 
/* 2609 */       print(paramRuleSymbol.access + " final ");
/*      */ 
/* 2612 */       if (localRuleBlock.returnAction != null)
/*      */       {
/* 2614 */         _print(extractTypeOfAction(localRuleBlock.returnAction, localRuleBlock.getLine(), localRuleBlock.getColumn()) + " ");
/*      */       }
/*      */       else
/*      */       {
/* 2618 */         _print("void ");
/*      */       }
/*      */ 
/* 2622 */       _print(paramRuleSymbol.getId() + "(");
/*      */ 
/* 2625 */       _print(this.commonExtraParams);
/* 2626 */       if ((this.commonExtraParams.length() != 0) && (localRuleBlock.argAction != null)) {
/* 2627 */         _print(",");
/*      */       }
/*      */ 
/* 2631 */       if (localRuleBlock.argAction != null)
/*      */       {
/* 2633 */         _println("");
/* 2634 */         this.tabs += 1;
/* 2635 */         println(localRuleBlock.argAction);
/* 2636 */         this.tabs -= 1;
/* 2637 */         print(")");
/*      */       }
/*      */       else
/*      */       {
/* 2641 */         _print(")");
/*      */       }
/*      */ 
/* 2645 */       _print(" throws " + this.exceptionThrown);
/* 2646 */       if ((this.grammar instanceof ParserGrammar)) {
/* 2647 */         _print(", TokenStreamException");
/*      */       }
/* 2649 */       else if ((this.grammar instanceof LexerGrammar)) {
/* 2650 */         _print(", CharStreamException, TokenStreamException");
/*      */       }
/*      */ 
/* 2653 */       if (localRuleBlock.throwsSpec != null) {
/* 2654 */         if ((this.grammar instanceof LexerGrammar)) {
/* 2655 */           this.antlrTool.error("user-defined throws spec not allowed (yet) for lexer rule " + localRuleBlock.ruleName);
/*      */         }
/*      */         else {
/* 2658 */           _print(", " + localRuleBlock.throwsSpec);
/*      */         }
/*      */       }
/*      */ 
/* 2662 */       _println(" {");
/* 2663 */       this.tabs += 1;
/*      */ 
/* 2666 */       if (localRuleBlock.returnAction != null) {
/* 2667 */         println(localRuleBlock.returnAction + ";");
/*      */       }
/*      */ 
/* 2670 */       println(this.commonLocalVars);
/*      */ 
/* 2672 */       if (this.grammar.traceRules) {
/* 2673 */         if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2674 */           println("traceIn(\"" + paramRuleSymbol.getId() + "\",_t);");
/*      */         }
/*      */         else {
/* 2677 */           println("traceIn(\"" + paramRuleSymbol.getId() + "\");");
/*      */         }
/*      */       }
/*      */ 
/* 2681 */       if ((this.grammar instanceof LexerGrammar))
/*      */       {
/* 2684 */         if (paramRuleSymbol.getId().equals("mEOF"))
/* 2685 */           println("_ttype = Token.EOF_TYPE;");
/*      */         else
/* 2687 */           println("_ttype = " + paramRuleSymbol.getId().substring(1) + ";");
/* 2688 */         println("int _saveIndex;");
/*      */       }
/*      */ 
/* 2698 */       if (this.grammar.debuggingOutput) {
/* 2699 */         if ((this.grammar instanceof ParserGrammar))
/* 2700 */           println("fireEnterRule(" + paramInt + ",0);");
/* 2701 */         else if ((this.grammar instanceof LexerGrammar)) {
/* 2702 */           println("fireEnterRule(" + paramInt + ",_ttype);");
/*      */         }
/*      */       }
/* 2705 */       if ((this.grammar.debuggingOutput) || (this.grammar.traceRules)) {
/* 2706 */         println("try { // debugging");
/* 2707 */         this.tabs += 1;
/*      */       }
/*      */ 
/* 2711 */       if ((this.grammar instanceof TreeWalkerGrammar))
/*      */       {
/* 2713 */         println(this.labeledElementASTType + " " + paramRuleSymbol.getId() + "_AST_in = (_t == ASTNULL) ? null : (" + this.labeledElementASTType + ")_t;", -999);
/*      */       }
/* 2715 */       if (this.grammar.buildAST)
/*      */       {
/* 2717 */         println("returnAST = null;");
/*      */ 
/* 2720 */         println("ASTPair currentAST = new ASTPair();");
/*      */ 
/* 2722 */         println(this.labeledElementASTType + " " + paramRuleSymbol.getId() + "_AST = null;");
/*      */       }
/*      */ 
/* 2725 */       genBlockPreamble(localRuleBlock);
/* 2726 */       genBlockInitAction(localRuleBlock);
/* 2727 */       println("");
/*      */ 
/* 2730 */       ExceptionSpec localExceptionSpec = localRuleBlock.findExceptionSpec("");
/*      */ 
/* 2733 */       if ((localExceptionSpec != null) || (localRuleBlock.getDefaultErrorHandler())) {
/* 2734 */         println("try {      // for error handling");
/* 2735 */         this.tabs += 1;
/*      */       }
/*      */       Object localObject1;
/* 2739 */       if (localRuleBlock.alternatives.size() == 1)
/*      */       {
/* 2741 */         Alternative localAlternative = localRuleBlock.getAlternativeAt(0);
/* 2742 */         localObject1 = localAlternative.semPred;
/* 2743 */         if (localObject1 != null)
/* 2744 */           genSemPred((String)localObject1, this.currentRule.line);
/* 2745 */         if (localAlternative.synPred != null) {
/* 2746 */           this.antlrTool.warning("Syntactic predicate ignored for single alternative", this.grammar.getFilename(), localAlternative.synPred.getLine(), localAlternative.synPred.getColumn());
/*      */         }
/*      */ 
/* 2753 */         genAlt(localAlternative, localRuleBlock);
/*      */       }
/*      */       else
/*      */       {
/* 2757 */         boolean bool2 = this.grammar.theLLkAnalyzer.deterministic(localRuleBlock);
/*      */ 
/* 2759 */         localObject1 = genCommonBlock(localRuleBlock, false);
/* 2760 */         genBlockFinish((JavaBlockFinishingInfo)localObject1, this.throwNoViable, localRuleBlock.getLine());
/*      */       }
/*      */ 
/* 2764 */       if ((localExceptionSpec != null) || (localRuleBlock.getDefaultErrorHandler()))
/*      */       {
/* 2766 */         this.tabs -= 1;
/* 2767 */         println("}");
/*      */       }
/*      */ 
/* 2771 */       if (localExceptionSpec != null) {
/* 2772 */         genErrorHandler(localExceptionSpec);
/*      */       }
/* 2774 */       else if (localRuleBlock.getDefaultErrorHandler())
/*      */       {
/* 2776 */         println("catch (" + this.exceptionThrown + " ex) {");
/* 2777 */         this.tabs += 1;
/*      */ 
/* 2779 */         if (this.grammar.hasSyntacticPredicate) {
/* 2780 */           println("if (inputState.guessing==0) {");
/* 2781 */           this.tabs += 1;
/*      */         }
/* 2783 */         println("reportError(ex);");
/* 2784 */         if (!(this.grammar instanceof TreeWalkerGrammar))
/*      */         {
/* 2786 */           Lookahead localLookahead = this.grammar.theLLkAnalyzer.FOLLOW(1, localRuleBlock.endNode);
/* 2787 */           localObject1 = getBitsetName(markBitsetForGen(localLookahead.fset));
/* 2788 */           println("recover(ex," + (String)localObject1 + ");");
/*      */         }
/*      */         else
/*      */         {
/* 2792 */           println("if (_t!=null) {_t = _t.getNextSibling();}");
/*      */         }
/* 2794 */         if (this.grammar.hasSyntacticPredicate) {
/* 2795 */           this.tabs -= 1;
/*      */ 
/* 2797 */           println("} else {");
/* 2798 */           println("  throw ex;");
/* 2799 */           println("}");
/*      */         }
/*      */ 
/* 2802 */         this.tabs -= 1;
/* 2803 */         println("}");
/*      */       }
/*      */ 
/* 2807 */       if (this.grammar.buildAST) {
/* 2808 */         println("returnAST = " + paramRuleSymbol.getId() + "_AST;");
/*      */       }
/*      */ 
/* 2812 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2813 */         println("_retTree = _t;");
/*      */       }
/*      */ 
/* 2817 */       if (localRuleBlock.getTestLiterals()) {
/* 2818 */         if (paramRuleSymbol.access.equals("protected")) {
/* 2819 */           genLiteralsTestForPartialToken();
/*      */         }
/*      */         else {
/* 2822 */           genLiteralsTest();
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2827 */       if ((this.grammar instanceof LexerGrammar)) {
/* 2828 */         println("if ( _createToken && _token==null && _ttype!=Token.SKIP ) {");
/* 2829 */         println("\t_token = makeToken(_ttype);");
/* 2830 */         println("\t_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));");
/* 2831 */         println("}");
/* 2832 */         println("_returnToken = _token;");
/*      */       }
/*      */ 
/* 2836 */       if (localRuleBlock.returnAction != null) {
/* 2837 */         println("return " + extractIdOfAction(localRuleBlock.returnAction, localRuleBlock.getLine(), localRuleBlock.getColumn()) + ";");
/*      */       }
/*      */ 
/* 2840 */       if ((this.grammar.debuggingOutput) || (this.grammar.traceRules)) {
/* 2841 */         this.tabs -= 1;
/* 2842 */         println("} finally { // debugging");
/* 2843 */         this.tabs += 1;
/*      */ 
/* 2846 */         if (this.grammar.debuggingOutput) {
/* 2847 */           if ((this.grammar instanceof ParserGrammar))
/* 2848 */             println("fireExitRule(" + paramInt + ",0);");
/* 2849 */           else if ((this.grammar instanceof LexerGrammar))
/* 2850 */             println("fireExitRule(" + paramInt + ",_ttype);");
/*      */         }
/* 2852 */         if (this.grammar.traceRules) {
/* 2853 */           if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2854 */             println("traceOut(\"" + paramRuleSymbol.getId() + "\",_t);");
/*      */           }
/*      */           else {
/* 2857 */             println("traceOut(\"" + paramRuleSymbol.getId() + "\");");
/*      */           }
/*      */         }
/*      */ 
/* 2861 */         this.tabs -= 1;
/* 2862 */         println("}");
/*      */       }
/*      */ 
/* 2865 */       this.tabs -= 1;
/* 2866 */       println("}");
/* 2867 */       println("");
/*      */ 
/* 2870 */       this.genAST = bool1;
/*      */     }
/*      */     finally
/*      */     {
/* 2875 */       this.defaultLine = i;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void GenRuleInvocation(RuleRefElement paramRuleRefElement) {
/* 2880 */     int i = this.defaultLine;
/*      */     try {
/* 2882 */       this.defaultLine = paramRuleRefElement.getLine();
/*      */ 
/* 2884 */       getPrintWriterManager().startSingleSourceLineMapping(paramRuleRefElement.getLine());
/* 2885 */       _print(paramRuleRefElement.targetRule + "(");
/* 2886 */       getPrintWriterManager().endMapping();
/*      */ 
/* 2889 */       if ((this.grammar instanceof LexerGrammar))
/*      */       {
/* 2891 */         if (paramRuleRefElement.getLabel() != null) {
/* 2892 */           _print("true");
/*      */         }
/*      */         else {
/* 2895 */           _print("false");
/*      */         }
/* 2897 */         if ((this.commonExtraArgs.length() != 0) || (paramRuleRefElement.args != null)) {
/* 2898 */           _print(",");
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2903 */       _print(this.commonExtraArgs);
/* 2904 */       if ((this.commonExtraArgs.length() != 0) && (paramRuleRefElement.args != null)) {
/* 2905 */         _print(",");
/*      */       }
/*      */ 
/* 2909 */       RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(paramRuleRefElement.targetRule);
/* 2910 */       if (paramRuleRefElement.args != null)
/*      */       {
/* 2912 */         ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 2913 */         String str = processActionForSpecialSymbols(paramRuleRefElement.args, 0, this.currentRule, localActionTransInfo);
/* 2914 */         if ((localActionTransInfo.assignToRoot) || (localActionTransInfo.refRuleRoot != null)) {
/* 2915 */           this.antlrTool.error("Arguments of rule reference '" + paramRuleRefElement.targetRule + "' cannot set or ref #" + this.currentRule.getRuleName(), this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*      */         }
/*      */ 
/* 2918 */         _print(str);
/*      */ 
/* 2921 */         if (localRuleSymbol.block.argAction == null) {
/* 2922 */           this.antlrTool.warning("Rule '" + paramRuleRefElement.targetRule + "' accepts no arguments", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*      */         }
/*      */ 
/*      */       }
/* 2928 */       else if (localRuleSymbol.block.argAction != null) {
/* 2929 */         this.antlrTool.warning("Missing parameters on reference to rule " + paramRuleRefElement.targetRule, this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*      */       }
/*      */ 
/* 2932 */       _println(");");
/*      */ 
/* 2935 */       if ((this.grammar instanceof TreeWalkerGrammar))
/* 2936 */         println("_t = _retTree;");
/*      */     }
/*      */     finally {
/* 2939 */       this.defaultLine = i;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void genSemPred(String paramString, int paramInt)
/*      */   {
/* 2945 */     ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 2946 */     paramString = processActionForSpecialSymbols(paramString, paramInt, this.currentRule, localActionTransInfo);
/*      */ 
/* 2948 */     String str = this.charFormatter.escapeString(paramString);
/*      */ 
/* 2952 */     if ((this.grammar.debuggingOutput) && (((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar)))) {
/* 2953 */       paramString = "fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEvent.VALIDATING," + addSemPred(str) + "," + paramString + ")";
/*      */     }
/* 2955 */     println("if (!(" + paramString + "))", paramInt);
/* 2956 */     println("  throw new SemanticException(\"" + str + "\");", paramInt);
/*      */   }
/*      */ 
/*      */   protected void genSemPredMap()
/*      */   {
/* 2963 */     Enumeration localEnumeration = this.semPreds.elements();
/* 2964 */     println("private String _semPredNames[] = {", -999);
/* 2965 */     while (localEnumeration.hasMoreElements())
/* 2966 */       println("\"" + localEnumeration.nextElement() + "\",", -999);
/* 2967 */     println("};", -999);
/*      */   }
/*      */ 
/*      */   protected void genSynPred(SynPredBlock paramSynPredBlock, String paramString) {
/* 2971 */     int i = this.defaultLine;
/*      */     try {
/* 2973 */       this.defaultLine = paramSynPredBlock.getLine();
/* 2974 */       if (this.DEBUG_CODE_GENERATOR) System.out.println("gen=>(" + paramSynPredBlock + ")");
/*      */ 
/* 2977 */       println("boolean synPredMatched" + paramSynPredBlock.ID + " = false;");
/*      */ 
/* 2980 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2981 */         println("if (_t==null) _t=ASTNULL;");
/*      */       }
/*      */ 
/* 2985 */       println("if (" + paramString + ") {");
/* 2986 */       this.tabs += 1;
/*      */ 
/* 2989 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2990 */         println("AST __t" + paramSynPredBlock.ID + " = _t;");
/*      */       }
/*      */       else {
/* 2993 */         println("int _m" + paramSynPredBlock.ID + " = mark();");
/*      */       }
/*      */ 
/* 2997 */       println("synPredMatched" + paramSynPredBlock.ID + " = true;");
/* 2998 */       println("inputState.guessing++;");
/*      */ 
/* 3001 */       if ((this.grammar.debuggingOutput) && (((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar))))
/*      */       {
/* 3003 */         println("fireSyntacticPredicateStarted();");
/*      */       }
/*      */ 
/* 3006 */       this.syntacticPredLevel += 1;
/* 3007 */       println("try {");
/* 3008 */       this.tabs += 1;
/* 3009 */       gen(paramSynPredBlock);
/* 3010 */       this.tabs -= 1;
/*      */ 
/* 3012 */       println("}");
/* 3013 */       println("catch (" + this.exceptionThrown + " pe) {");
/* 3014 */       this.tabs += 1;
/* 3015 */       println("synPredMatched" + paramSynPredBlock.ID + " = false;");
/*      */ 
/* 3017 */       this.tabs -= 1;
/* 3018 */       println("}");
/*      */ 
/* 3021 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 3022 */         println("_t = __t" + paramSynPredBlock.ID + ";");
/*      */       }
/*      */       else {
/* 3025 */         println("rewind(_m" + paramSynPredBlock.ID + ");");
/*      */       }
/*      */ 
/* 3028 */       _println("inputState.guessing--;");
/*      */ 
/* 3031 */       if ((this.grammar.debuggingOutput) && (((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar))))
/*      */       {
/* 3033 */         println("if (synPredMatched" + paramSynPredBlock.ID + ")");
/* 3034 */         println("  fireSyntacticPredicateSucceeded();");
/* 3035 */         println("else");
/* 3036 */         println("  fireSyntacticPredicateFailed();");
/*      */       }
/*      */ 
/* 3039 */       this.syntacticPredLevel -= 1;
/* 3040 */       this.tabs -= 1;
/*      */ 
/* 3043 */       println("}");
/*      */ 
/* 3046 */       println("if ( synPredMatched" + paramSynPredBlock.ID + " ) {");
/*      */     } finally {
/* 3048 */       this.defaultLine = i;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void genTokenStrings()
/*      */   {
/* 3061 */     int i = this.defaultLine;
/*      */     try {
/* 3063 */       this.defaultLine = -999;
/*      */ 
/* 3066 */       println("");
/* 3067 */       println("public static final String[] _tokenNames = {");
/* 3068 */       this.tabs += 1;
/*      */ 
/* 3072 */       Vector localVector = this.grammar.tokenManager.getVocabulary();
/* 3073 */       for (int j = 0; j < localVector.size(); j++) {
/* 3074 */         String str = (String)localVector.elementAt(j);
/* 3075 */         if (str == null) {
/* 3076 */           str = "<" + String.valueOf(j) + ">";
/*      */         }
/* 3078 */         if ((!str.startsWith("\"")) && (!str.startsWith("<"))) {
/* 3079 */           TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol(str);
/* 3080 */           if ((localTokenSymbol != null) && (localTokenSymbol.getParaphrase() != null)) {
/* 3081 */             str = StringUtils.stripFrontBack(localTokenSymbol.getParaphrase(), "\"", "\"");
/*      */           }
/*      */         }
/* 3084 */         print(this.charFormatter.literalString(str));
/* 3085 */         if (j != localVector.size() - 1) {
/* 3086 */           _print(",");
/*      */         }
/* 3088 */         _println("");
/*      */       }
/*      */ 
/* 3092 */       this.tabs -= 1;
/* 3093 */       println("};");
/*      */     } finally {
/* 3095 */       this.defaultLine = i;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void genTokenASTNodeMap()
/*      */   {
/* 3103 */     int i = this.defaultLine;
/*      */     try {
/* 3105 */       this.defaultLine = -999;
/* 3106 */       println("");
/* 3107 */       println("protected void buildTokenTypeASTClassMap() {");
/*      */ 
/* 3110 */       this.tabs += 1;
/* 3111 */       int j = 0;
/* 3112 */       int k = 0;
/*      */ 
/* 3114 */       Vector localVector = this.grammar.tokenManager.getVocabulary();
/* 3115 */       for (int m = 0; m < localVector.size(); m++) {
/* 3116 */         String str = (String)localVector.elementAt(m);
/* 3117 */         if (str != null) {
/* 3118 */           TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol(str);
/* 3119 */           if ((localTokenSymbol != null) && (localTokenSymbol.getASTNodeType() != null)) {
/* 3120 */             k++;
/* 3121 */             if (j == 0)
/*      */             {
/* 3123 */               println("tokenTypeToASTClassMap = new Hashtable();");
/* 3124 */               j = 1;
/*      */             }
/* 3126 */             println("tokenTypeToASTClassMap.put(new Integer(" + localTokenSymbol.getTokenType() + "), " + localTokenSymbol.getASTNodeType() + ".class);");
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 3132 */       if (k == 0) {
/* 3133 */         println("tokenTypeToASTClassMap=null;");
/*      */       }
/* 3135 */       this.tabs -= 1;
/* 3136 */       println("};");
/*      */     } finally {
/* 3138 */       this.defaultLine = i;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void genTokenTypes(TokenManager paramTokenManager) throws IOException
/*      */   {
/* 3144 */     int i = this.defaultLine;
/*      */     try {
/* 3146 */       this.defaultLine = -999;
/*      */ 
/* 3150 */       this.currentOutput = getPrintWriterManager().setupOutput(this.antlrTool, paramTokenManager.getName() + TokenTypesFileSuffix);
/*      */ 
/* 3152 */       this.tabs = 0;
/*      */ 
/* 3155 */       genHeader();
/*      */       try
/*      */       {
/* 3158 */         this.defaultLine = this.behavior.getHeaderActionLine("");
/* 3159 */         println(this.behavior.getHeaderAction(""));
/*      */       } finally {
/* 3161 */         this.defaultLine = -999;
/*      */       }
/*      */ 
/* 3166 */       println("public interface " + paramTokenManager.getName() + TokenTypesFileSuffix + " {");
/* 3167 */       this.tabs += 1;
/*      */ 
/* 3170 */       Vector localVector = paramTokenManager.getVocabulary();
/*      */ 
/* 3173 */       println("int EOF = 1;");
/* 3174 */       println("int NULL_TREE_LOOKAHEAD = 3;");
/*      */ 
/* 3176 */       for (int j = 4; j < localVector.size(); j++) {
/* 3177 */         String str1 = (String)localVector.elementAt(j);
/* 3178 */         if (str1 != null) {
/* 3179 */           if (str1.startsWith("\""))
/*      */           {
/* 3181 */             StringLiteralSymbol localStringLiteralSymbol = (StringLiteralSymbol)paramTokenManager.getTokenSymbol(str1);
/* 3182 */             if (localStringLiteralSymbol == null) {
/* 3183 */               this.antlrTool.panic("String literal " + str1 + " not in symbol table");
/*      */             }
/* 3185 */             else if (localStringLiteralSymbol.label != null) {
/* 3186 */               println("int " + localStringLiteralSymbol.label + " = " + j + ";");
/*      */             }
/*      */             else {
/* 3189 */               String str2 = mangleLiteral(str1);
/* 3190 */               if (str2 != null)
/*      */               {
/* 3192 */                 println("int " + str2 + " = " + j + ";");
/*      */ 
/* 3194 */                 localStringLiteralSymbol.label = str2;
/*      */               }
/*      */               else {
/* 3197 */                 println("// " + str1 + " = " + j);
/*      */               }
/*      */             }
/*      */           }
/* 3201 */           else if (!str1.startsWith("<")) {
/* 3202 */             println("int " + str1 + " = " + j + ";");
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 3208 */       this.tabs -= 1;
/* 3209 */       println("}");
/*      */ 
/* 3212 */       getPrintWriterManager().finishOutput();
/* 3213 */       exitIfError();
/*      */     } finally {
/* 3215 */       this.defaultLine = i;
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getASTCreateString(Vector paramVector)
/*      */   {
/* 3223 */     if (paramVector.size() == 0) {
/* 3224 */       return "";
/*      */     }
/* 3226 */     StringBuffer localStringBuffer = new StringBuffer();
/* 3227 */     localStringBuffer.append("(" + this.labeledElementASTType + ")astFactory.make( (new ASTArray(" + paramVector.size() + "))");
/*      */ 
/* 3230 */     for (int i = 0; i < paramVector.size(); i++) {
/* 3231 */       localStringBuffer.append(".add(" + paramVector.elementAt(i) + ")");
/*      */     }
/* 3233 */     localStringBuffer.append(")");
/* 3234 */     return localStringBuffer.toString();
/*      */   }
/*      */ 
/*      */   public String getASTCreateString(GrammarAtom paramGrammarAtom, String paramString)
/*      */   {
/* 3243 */     if ((paramGrammarAtom != null) && (paramGrammarAtom.getASTNodeType() != null))
/*      */     {
/* 3245 */       return "(" + paramGrammarAtom.getASTNodeType() + ")" + "astFactory.create(" + paramString + ",\"" + paramGrammarAtom.getASTNodeType() + "\")";
/*      */     }
/*      */ 
/* 3250 */     return getASTCreateString(paramString);
/*      */   }
/*      */ 
/*      */   public String getASTCreateString(String paramString)
/*      */   {
/* 3264 */     if (paramString == null) {
/* 3265 */       paramString = "";
/*      */     }
/* 3267 */     int i = 0;
/* 3268 */     for (int j = 0; j < paramString.length(); j++) {
/* 3269 */       if (paramString.charAt(j) == ',') {
/* 3270 */         i++;
/*      */       }
/*      */     }
/*      */ 
/* 3274 */     if (i < 2) {
/* 3275 */       j = paramString.indexOf(',');
/* 3276 */       int k = paramString.lastIndexOf(',');
/* 3277 */       String str1 = paramString;
/* 3278 */       if (i > 0) {
/* 3279 */         str1 = paramString.substring(0, j);
/*      */       }
/*      */ 
/* 3282 */       TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol(str1);
/* 3283 */       if (localTokenSymbol != null) {
/* 3284 */         String str2 = localTokenSymbol.getASTNodeType();
/*      */ 
/* 3286 */         String str3 = "";
/* 3287 */         if (i == 0)
/*      */         {
/* 3289 */           str3 = ",\"\"";
/*      */         }
/* 3291 */         if (str2 != null) {
/* 3292 */           return "(" + str2 + ")" + "astFactory.create(" + paramString + str3 + ",\"" + str2 + "\")";
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 3298 */       if (this.labeledElementASTType.equals("AST")) {
/* 3299 */         return "astFactory.create(" + paramString + ")";
/*      */       }
/* 3301 */       return "(" + this.labeledElementASTType + ")" + "astFactory.create(" + paramString + ")";
/*      */     }
/*      */ 
/* 3305 */     return "(" + this.labeledElementASTType + ")astFactory.create(" + paramString + ")";
/*      */   }
/*      */ 
/*      */   protected String getLookaheadTestExpression(Lookahead[] paramArrayOfLookahead, int paramInt) {
/* 3309 */     StringBuffer localStringBuffer = new StringBuffer(100);
/* 3310 */     int i = 1;
/*      */ 
/* 3312 */     localStringBuffer.append("(");
/* 3313 */     for (int j = 1; j <= paramInt; j++) {
/* 3314 */       BitSet localBitSet = paramArrayOfLookahead[j].fset;
/* 3315 */       if (i == 0) {
/* 3316 */         localStringBuffer.append(") && (");
/*      */       }
/* 3318 */       i = 0;
/*      */ 
/* 3323 */       if (paramArrayOfLookahead[j].containsEpsilon()) {
/* 3324 */         localStringBuffer.append("true");
/*      */       }
/*      */       else {
/* 3327 */         localStringBuffer.append(getLookaheadTestTerm(j, localBitSet));
/*      */       }
/*      */     }
/* 3330 */     localStringBuffer.append(")");
/*      */ 
/* 3332 */     return localStringBuffer.toString();
/*      */   }
/*      */ 
/*      */   protected String getLookaheadTestExpression(Alternative paramAlternative, int paramInt)
/*      */   {
/* 3340 */     int i = paramAlternative.lookaheadDepth;
/* 3341 */     if (i == 2147483647)
/*      */     {
/* 3344 */       i = this.grammar.maxk;
/*      */     }
/*      */ 
/* 3347 */     if (paramInt == 0)
/*      */     {
/* 3350 */       return "( true )";
/*      */     }
/*      */ 
/* 3353 */     return "(" + getLookaheadTestExpression(paramAlternative.cache, i) + ")";
/*      */   }
/*      */ 
/*      */   protected String getLookaheadTestTerm(int paramInt, BitSet paramBitSet)
/*      */   {
/* 3366 */     String str1 = lookaheadString(paramInt);
/*      */ 
/* 3369 */     int[] arrayOfInt = paramBitSet.toArray();
/* 3370 */     if (elementsAreRange(arrayOfInt)) {
/* 3371 */       return getRangeExpression(paramInt, arrayOfInt);
/*      */     }
/*      */ 
/* 3376 */     int i = paramBitSet.degree();
/* 3377 */     if (i == 0) {
/* 3378 */       return "true";
/*      */     }
/*      */ 
/* 3381 */     if (i >= this.bitsetTestThreshold) {
/* 3382 */       j = markBitsetForGen(paramBitSet);
/* 3383 */       return getBitsetName(j) + ".member(" + str1 + ")";
/*      */     }
/*      */ 
/* 3387 */     StringBuffer localStringBuffer = new StringBuffer();
/* 3388 */     for (int j = 0; j < arrayOfInt.length; j++)
/*      */     {
/* 3390 */       String str2 = getValueString(arrayOfInt[j]);
/*      */ 
/* 3393 */       if (j > 0) localStringBuffer.append("||");
/* 3394 */       localStringBuffer.append(str1);
/* 3395 */       localStringBuffer.append("==");
/* 3396 */       localStringBuffer.append(str2);
/*      */     }
/* 3398 */     return localStringBuffer.toString();
/*      */   }
/*      */ 
/*      */   public String getRangeExpression(int paramInt, int[] paramArrayOfInt)
/*      */   {
/* 3407 */     if (!elementsAreRange(paramArrayOfInt)) {
/* 3408 */       this.antlrTool.panic("getRangeExpression called with non-range");
/*      */     }
/* 3410 */     int i = paramArrayOfInt[0];
/* 3411 */     int j = paramArrayOfInt[(paramArrayOfInt.length - 1)];
/* 3412 */     return "(" + lookaheadString(paramInt) + " >= " + getValueString(i) + " && " + lookaheadString(paramInt) + " <= " + getValueString(j) + ")";
/*      */   }
/*      */ 
/*      */   private String getValueString(int paramInt)
/*      */   {
/*      */     Object localObject;
/* 3422 */     if ((this.grammar instanceof LexerGrammar)) {
/* 3423 */       localObject = this.charFormatter.literalChar(paramInt);
/*      */     }
/*      */     else {
/* 3426 */       TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbolAt(paramInt);
/* 3427 */       if (localTokenSymbol == null) {
/* 3428 */         return "" + paramInt;
/*      */       }
/*      */ 
/* 3431 */       String str1 = localTokenSymbol.getId();
/* 3432 */       if ((localTokenSymbol instanceof StringLiteralSymbol))
/*      */       {
/* 3436 */         StringLiteralSymbol localStringLiteralSymbol = (StringLiteralSymbol)localTokenSymbol;
/* 3437 */         String str2 = localStringLiteralSymbol.getLabel();
/* 3438 */         if (str2 != null) {
/* 3439 */           localObject = str2;
/*      */         }
/*      */         else {
/* 3442 */           localObject = mangleLiteral(str1);
/* 3443 */           if (localObject == null)
/* 3444 */             localObject = String.valueOf(paramInt);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 3449 */         localObject = str1;
/*      */       }
/*      */     }
/* 3452 */     return localObject;
/*      */   }
/*      */ 
/*      */   protected boolean lookaheadIsEmpty(Alternative paramAlternative, int paramInt)
/*      */   {
/* 3457 */     int i = paramAlternative.lookaheadDepth;
/* 3458 */     if (i == 2147483647) {
/* 3459 */       i = this.grammar.maxk;
/*      */     }
/* 3461 */     for (int j = 1; (j <= i) && (j <= paramInt); j++) {
/* 3462 */       BitSet localBitSet = paramAlternative.cache[j].fset;
/* 3463 */       if (localBitSet.degree() != 0) {
/* 3464 */         return false;
/*      */       }
/*      */     }
/* 3467 */     return true;
/*      */   }
/*      */ 
/*      */   private String lookaheadString(int paramInt) {
/* 3471 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 3472 */       return "_t.getType()";
/*      */     }
/* 3474 */     return "LA(" + paramInt + ")";
/*      */   }
/*      */ 
/*      */   private String mangleLiteral(String paramString)
/*      */   {
/* 3484 */     String str = this.antlrTool.literalsPrefix;
/* 3485 */     for (int i = 1; i < paramString.length() - 1; i++) {
/* 3486 */       if ((!Character.isLetter(paramString.charAt(i))) && (paramString.charAt(i) != '_'))
/*      */       {
/* 3488 */         return null;
/*      */       }
/* 3490 */       str = str + paramString.charAt(i);
/*      */     }
/* 3492 */     if (this.antlrTool.upperCaseMangledLiterals) {
/* 3493 */       str = str.toUpperCase();
/*      */     }
/* 3495 */     return str;
/*      */   }
/*      */ 
/*      */   public String mapTreeId(String paramString, ActionTransInfo paramActionTransInfo)
/*      */   {
/* 3506 */     if (this.currentRule == null) return paramString;
/*      */ 
/* 3508 */     int i = 0;
/* 3509 */     String str1 = paramString;
/* 3510 */     if ((this.grammar instanceof TreeWalkerGrammar))
/* 3511 */       if (!this.grammar.buildAST) {
/* 3512 */         i = 1;
/*      */       }
/* 3515 */       else if ((str1.length() > 3) && (str1.lastIndexOf("_in") == str1.length() - 3))
/*      */       {
/* 3517 */         str1 = str1.substring(0, str1.length() - 3);
/* 3518 */         i = 1;
/*      */       }
/*      */     Object localObject;
/* 3524 */     for (int j = 0; j < this.currentRule.labeledElements.size(); j++) {
/* 3525 */       localObject = (AlternativeElement)this.currentRule.labeledElements.elementAt(j);
/* 3526 */       if (((AlternativeElement)localObject).getLabel().equals(str1)) {
/* 3527 */         return str1 + "_AST";
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3534 */     String str2 = (String)this.treeVariableMap.get(str1);
/* 3535 */     if (str2 != null) {
/* 3536 */       if (str2 == NONUNIQUE)
/*      */       {
/* 3538 */         this.antlrTool.error("Ambiguous reference to AST element " + str1 + " in rule " + this.currentRule.getRuleName());
/*      */ 
/* 3541 */         return null;
/*      */       }
/* 3543 */       if (str2.equals(this.currentRule.getRuleName()))
/*      */       {
/* 3546 */         this.antlrTool.error("Ambiguous reference to AST element " + str1 + " in rule " + this.currentRule.getRuleName());
/*      */ 
/* 3548 */         return null;
/*      */       }
/*      */ 
/* 3551 */       return i != 0 ? str2 + "_in" : str2;
/*      */     }
/*      */ 
/* 3557 */     if (str1.equals(this.currentRule.getRuleName())) {
/* 3558 */       localObject = str1 + "_AST";
/* 3559 */       if ((paramActionTransInfo != null) && 
/* 3560 */         (i == 0)) {
/* 3561 */         paramActionTransInfo.refRuleRoot = ((String)localObject);
/*      */       }
/*      */ 
/* 3564 */       return localObject;
/*      */     }
/*      */ 
/* 3568 */     return str1;
/*      */   }
/*      */ 
/*      */   private void mapTreeVariable(AlternativeElement paramAlternativeElement, String paramString)
/*      */   {
/* 3577 */     if ((paramAlternativeElement instanceof TreeElement)) {
/* 3578 */       mapTreeVariable(((TreeElement)paramAlternativeElement).root, paramString);
/* 3579 */       return;
/*      */     }
/*      */ 
/* 3583 */     String str = null;
/*      */ 
/* 3586 */     if (paramAlternativeElement.getLabel() == null) {
/* 3587 */       if ((paramAlternativeElement instanceof TokenRefElement))
/*      */       {
/* 3589 */         str = ((TokenRefElement)paramAlternativeElement).atomText;
/*      */       }
/* 3591 */       else if ((paramAlternativeElement instanceof RuleRefElement))
/*      */       {
/* 3593 */         str = ((RuleRefElement)paramAlternativeElement).targetRule;
/*      */       }
/*      */     }
/*      */ 
/* 3597 */     if (str != null)
/* 3598 */       if (this.treeVariableMap.get(str) != null)
/*      */       {
/* 3600 */         this.treeVariableMap.remove(str);
/* 3601 */         this.treeVariableMap.put(str, NONUNIQUE);
/*      */       }
/*      */       else {
/* 3604 */         this.treeVariableMap.put(str, paramString);
/*      */       }
/*      */   }
/*      */ 
/*      */   protected String processActionForSpecialSymbols(String paramString, int paramInt, RuleBlock paramRuleBlock, ActionTransInfo paramActionTransInfo)
/*      */   {
/* 3617 */     if ((paramString == null) || (paramString.length() == 0)) return null;
/*      */ 
/* 3621 */     if (this.grammar == null) {
/* 3622 */       return paramString;
/*      */     }
/*      */ 
/* 3625 */     if (((this.grammar.buildAST) && (paramString.indexOf('#') != -1)) || ((this.grammar instanceof TreeWalkerGrammar)) || ((((this.grammar instanceof LexerGrammar)) || ((this.grammar instanceof ParserGrammar))) && (paramString.indexOf('$') != -1)))
/*      */     {
/* 3631 */       ActionLexer localActionLexer = new ActionLexer(paramString, paramRuleBlock, this, paramActionTransInfo);
/*      */ 
/* 3637 */       localActionLexer.setLineOffset(paramInt);
/* 3638 */       localActionLexer.setFilename(this.grammar.getFilename());
/* 3639 */       localActionLexer.setTool(this.antlrTool);
/*      */       try
/*      */       {
/* 3642 */         localActionLexer.mACTION(true);
/* 3643 */         paramString = localActionLexer.getTokenObject().getText();
/*      */       }
/*      */       catch (RecognitionException localRecognitionException)
/*      */       {
/* 3648 */         localActionLexer.reportError(localRecognitionException);
/* 3649 */         return paramString;
/*      */       }
/*      */       catch (TokenStreamException localTokenStreamException) {
/* 3652 */         this.antlrTool.panic("Error reading action:" + paramString);
/* 3653 */         return paramString;
/*      */       }
/*      */       catch (CharStreamException localCharStreamException) {
/* 3656 */         this.antlrTool.panic("Error reading action:" + paramString);
/* 3657 */         return paramString;
/*      */       }
/*      */     }
/* 3660 */     return paramString;
/*      */   }
/*      */ 
/*      */   private void setupGrammarParameters(Grammar paramGrammar)
/*      */   {
/*      */     Token localToken;
/*      */     String str;
/* 3664 */     if ((paramGrammar instanceof ParserGrammar)) {
/* 3665 */       this.labeledElementASTType = "AST";
/* 3666 */       if (paramGrammar.hasOption("ASTLabelType")) {
/* 3667 */         localToken = paramGrammar.getOption("ASTLabelType");
/* 3668 */         if (localToken != null) {
/* 3669 */           str = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 3670 */           if (str != null) {
/* 3671 */             this.labeledElementASTType = str;
/*      */           }
/*      */         }
/*      */       }
/* 3675 */       this.labeledElementType = "Token ";
/* 3676 */       this.labeledElementInit = "null";
/* 3677 */       this.commonExtraArgs = "";
/* 3678 */       this.commonExtraParams = "";
/* 3679 */       this.commonLocalVars = "";
/* 3680 */       this.lt1Value = "LT(1)";
/* 3681 */       this.exceptionThrown = "RecognitionException";
/* 3682 */       this.throwNoViable = "throw new NoViableAltException(LT(1), getFilename());";
/*      */     }
/* 3684 */     else if ((paramGrammar instanceof LexerGrammar)) {
/* 3685 */       this.labeledElementType = "char ";
/* 3686 */       this.labeledElementInit = "'\\0'";
/* 3687 */       this.commonExtraArgs = "";
/* 3688 */       this.commonExtraParams = "boolean _createToken";
/* 3689 */       this.commonLocalVars = "int _ttype; Token _token=null; int _begin=text.length();";
/* 3690 */       this.lt1Value = "LA(1)";
/* 3691 */       this.exceptionThrown = "RecognitionException";
/* 3692 */       this.throwNoViable = "throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());";
/*      */     }
/* 3694 */     else if ((paramGrammar instanceof TreeWalkerGrammar)) {
/* 3695 */       this.labeledElementASTType = "AST";
/* 3696 */       this.labeledElementType = "AST";
/* 3697 */       if (paramGrammar.hasOption("ASTLabelType")) {
/* 3698 */         localToken = paramGrammar.getOption("ASTLabelType");
/* 3699 */         if (localToken != null) {
/* 3700 */           str = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 3701 */           if (str != null) {
/* 3702 */             this.labeledElementASTType = str;
/* 3703 */             this.labeledElementType = str;
/*      */           }
/*      */         }
/*      */       }
/* 3707 */       if (!paramGrammar.hasOption("ASTLabelType")) {
/* 3708 */         paramGrammar.setOption("ASTLabelType", new Token(6, "AST"));
/*      */       }
/* 3710 */       this.labeledElementInit = "null";
/* 3711 */       this.commonExtraArgs = "_t";
/* 3712 */       this.commonExtraParams = "AST _t";
/* 3713 */       this.commonLocalVars = "";
/* 3714 */       this.lt1Value = ("(" + this.labeledElementASTType + ")_t");
/* 3715 */       this.exceptionThrown = "RecognitionException";
/* 3716 */       this.throwNoViable = "throw new NoViableAltException(_t);";
/*      */     }
/*      */     else {
/* 3719 */       this.antlrTool.panic("Unknown grammar type");
/*      */     }
/*      */   }
/*      */ 
/*      */   public JavaCodeGeneratorPrintWriterManager getPrintWriterManager()
/*      */   {
/* 3728 */     if (this.printWriterManager == null)
/* 3729 */       this.printWriterManager = new DefaultJavaCodeGeneratorPrintWriterManager();
/* 3730 */     return this.printWriterManager;
/*      */   }
/*      */ 
/*      */   public void setPrintWriterManager(JavaCodeGeneratorPrintWriterManager paramJavaCodeGeneratorPrintWriterManager)
/*      */   {
/* 3738 */     this.printWriterManager = paramJavaCodeGeneratorPrintWriterManager;
/*      */   }
/*      */ 
/*      */   public void setTool(Tool paramTool)
/*      */   {
/* 3743 */     super.setTool(paramTool);
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.JavaCodeGenerator
 * JD-Core Version:    0.6.2
 */