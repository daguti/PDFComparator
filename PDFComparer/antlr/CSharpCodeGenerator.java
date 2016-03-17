/*      */ package antlr;
/*      */ 
/*      */ import antlr.actions.csharp.ActionLexer;
/*      */ import antlr.collections.impl.BitSet;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.StringTokenizer;
/*      */ 
/*      */ public class CSharpCodeGenerator extends CodeGenerator
/*      */ {
/*   67 */   protected int syntacticPredLevel = 0;
/*      */ 
/*   70 */   protected boolean genAST = false;
/*      */ 
/*   73 */   protected boolean saveText = false;
/*      */ 
/*   77 */   boolean usingCustomAST = false;
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
/*   96 */   Hashtable treeVariableMap = new Hashtable();
/*      */ 
/*  101 */   Hashtable declaredASTVariables = new Hashtable();
/*      */ 
/*  104 */   int astVarNumber = 1;
/*      */ 
/*  107 */   protected static final String NONUNIQUE = new String();
/*      */   public static final int caseSizeThreshold = 127;
/*      */   private antlr.collections.impl.Vector semPreds;
/*      */   private java.util.Vector astTypes;
/*  116 */   private static CSharpNameSpace nameSpace = null;
/*      */   int saveIndexCreateLevel;
/*      */   int blockNestingLevel;
/*      */ 
/*      */   public CSharpCodeGenerator()
/*      */   {
/*  129 */     this.charFormatter = new CSharpCharFormatter();
/*      */   }
/*      */ 
/*      */   protected int addSemPred(String paramString)
/*      */   {
/*  138 */     this.semPreds.appendElement(paramString);
/*  139 */     return this.semPreds.size() - 1;
/*      */   }
/*      */ 
/*      */   public void exitIfError()
/*      */   {
/*  144 */     if (this.antlrTool.hasError())
/*      */     {
/*  146 */       this.antlrTool.fatalError("Exiting due to errors.");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void gen()
/*      */   {
/*      */     try
/*      */     {
/*  155 */       Enumeration localEnumeration = this.behavior.grammars.elements();
/*  156 */       while (localEnumeration.hasMoreElements()) {
/*  157 */         localObject = (Grammar)localEnumeration.nextElement();
/*      */ 
/*  159 */         ((Grammar)localObject).setGrammarAnalyzer(this.analyzer);
/*  160 */         ((Grammar)localObject).setCodeGenerator(this);
/*  161 */         this.analyzer.setGrammar((Grammar)localObject);
/*      */ 
/*  163 */         setupGrammarParameters((Grammar)localObject);
/*  164 */         ((Grammar)localObject).generate();
/*  165 */         exitIfError();
/*      */       }
/*      */ 
/*  169 */       Object localObject = this.behavior.tokenManagers.elements();
/*  170 */       while (((Enumeration)localObject).hasMoreElements()) {
/*  171 */         TokenManager localTokenManager = (TokenManager)((Enumeration)localObject).nextElement();
/*  172 */         if (!localTokenManager.isReadOnly())
/*      */         {
/*  176 */           genTokenTypes(localTokenManager);
/*      */ 
/*  178 */           genTokenInterchange(localTokenManager);
/*      */         }
/*  180 */         exitIfError();
/*      */       }
/*      */     }
/*      */     catch (IOException localIOException) {
/*  184 */       this.antlrTool.reportException(localIOException, null);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void gen(ActionElement paramActionElement)
/*      */   {
/*  192 */     if (this.DEBUG_CODE_GENERATOR) System.out.println("genAction(" + paramActionElement + ")");
/*  193 */     if (paramActionElement.isSemPred) {
/*  194 */       genSemPred(paramActionElement.actionText, paramActionElement.line);
/*      */     }
/*      */     else {
/*  197 */       if (this.grammar.hasSyntacticPredicate) {
/*  198 */         println("if (0==inputState.guessing)");
/*  199 */         println("{");
/*  200 */         this.tabs += 1;
/*      */       }
/*      */ 
/*  203 */       ActionTransInfo localActionTransInfo = new ActionTransInfo();
/*  204 */       String str = processActionForSpecialSymbols(paramActionElement.actionText, paramActionElement.getLine(), this.currentRule, localActionTransInfo);
/*      */ 
/*  208 */       if (localActionTransInfo.refRuleRoot != null)
/*      */       {
/*  213 */         println(localActionTransInfo.refRuleRoot + " = (" + this.labeledElementASTType + ")currentAST.root;");
/*      */       }
/*      */ 
/*  217 */       printAction(str);
/*      */ 
/*  219 */       if (localActionTransInfo.assignToRoot)
/*      */       {
/*  221 */         println("currentAST.root = " + localActionTransInfo.refRuleRoot + ";");
/*      */ 
/*  223 */         println("if ( (null != " + localActionTransInfo.refRuleRoot + ") && (null != " + localActionTransInfo.refRuleRoot + ".getFirstChild()) )");
/*  224 */         this.tabs += 1;
/*  225 */         println("currentAST.child = " + localActionTransInfo.refRuleRoot + ".getFirstChild();");
/*  226 */         this.tabs -= 1;
/*  227 */         println("else");
/*  228 */         this.tabs += 1;
/*  229 */         println("currentAST.child = " + localActionTransInfo.refRuleRoot + ";");
/*  230 */         this.tabs -= 1;
/*  231 */         println("currentAST.advanceChildToEnd();");
/*      */       }
/*      */ 
/*  234 */       if (this.grammar.hasSyntacticPredicate) {
/*  235 */         this.tabs -= 1;
/*  236 */         println("}");
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void gen(AlternativeBlock paramAlternativeBlock)
/*      */   {
/*  245 */     if (this.DEBUG_CODE_GENERATOR) System.out.println("gen(" + paramAlternativeBlock + ")");
/*  246 */     println("{");
/*  247 */     this.tabs += 1;
/*      */ 
/*  249 */     genBlockPreamble(paramAlternativeBlock);
/*  250 */     genBlockInitAction(paramAlternativeBlock);
/*      */ 
/*  253 */     String str = this.currentASTResult;
/*  254 */     if (paramAlternativeBlock.getLabel() != null) {
/*  255 */       this.currentASTResult = paramAlternativeBlock.getLabel();
/*      */     }
/*      */ 
/*  258 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramAlternativeBlock);
/*      */ 
/*  260 */     CSharpBlockFinishingInfo localCSharpBlockFinishingInfo = genCommonBlock(paramAlternativeBlock, true);
/*  261 */     genBlockFinish(localCSharpBlockFinishingInfo, this.throwNoViable);
/*      */ 
/*  263 */     this.tabs -= 1;
/*  264 */     println("}");
/*      */ 
/*  267 */     this.currentASTResult = str;
/*      */   }
/*      */ 
/*      */   public void gen(BlockEndElement paramBlockEndElement)
/*      */   {
/*  275 */     if (this.DEBUG_CODE_GENERATOR) System.out.println("genRuleEnd(" + paramBlockEndElement + ")");
/*      */   }
/*      */ 
/*      */   public void gen(CharLiteralElement paramCharLiteralElement)
/*      */   {
/*  281 */     if (this.DEBUG_CODE_GENERATOR) System.out.println("genChar(" + paramCharLiteralElement + ")");
/*      */ 
/*  283 */     if (paramCharLiteralElement.getLabel() != null) {
/*  284 */       println(paramCharLiteralElement.getLabel() + " = " + this.lt1Value + ";");
/*      */     }
/*      */ 
/*  287 */     boolean bool = this.saveText;
/*  288 */     this.saveText = ((this.saveText) && (paramCharLiteralElement.getAutoGenType() == 1));
/*  289 */     genMatch(paramCharLiteralElement);
/*  290 */     this.saveText = bool;
/*      */   }
/*      */ 
/*      */   public void gen(CharRangeElement paramCharRangeElement)
/*      */   {
/*  296 */     if ((paramCharRangeElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  297 */       println(paramCharRangeElement.getLabel() + " = " + this.lt1Value + ";");
/*      */     }
/*  299 */     int i = ((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramCharRangeElement.getAutoGenType() == 3)) ? 1 : 0;
/*      */ 
/*  301 */     if (i != 0) {
/*  302 */       println("_saveIndex = text.Length;");
/*      */     }
/*  304 */     println("matchRange(" + OctalToUnicode(paramCharRangeElement.beginText) + "," + OctalToUnicode(paramCharRangeElement.endText) + ");");
/*      */ 
/*  306 */     if (i != 0)
/*  307 */       println("text.Length = _saveIndex;");
/*      */   }
/*      */ 
/*      */   public void gen(LexerGrammar paramLexerGrammar) throws IOException
/*      */   {
/*  312 */     if (paramLexerGrammar.debuggingOutput) {
/*  313 */       this.semPreds = new antlr.collections.impl.Vector();
/*      */     }
/*  315 */     setGrammar(paramLexerGrammar);
/*  316 */     if (!(this.grammar instanceof LexerGrammar)) {
/*  317 */       this.antlrTool.panic("Internal error generating lexer");
/*      */     }
/*  319 */     genBody(paramLexerGrammar);
/*      */   }
/*      */ 
/*      */   public void gen(OneOrMoreBlock paramOneOrMoreBlock)
/*      */   {
/*  325 */     if (this.DEBUG_CODE_GENERATOR) System.out.println("gen+(" + paramOneOrMoreBlock + ")");
/*      */ 
/*  328 */     println("{ // ( ... )+");
/*  329 */     this.tabs += 1;
/*  330 */     this.blockNestingLevel += 1;
/*  331 */     genBlockPreamble(paramOneOrMoreBlock);
/*      */     String str2;
/*  332 */     if (paramOneOrMoreBlock.getLabel() != null) {
/*  333 */       str2 = "_cnt_" + paramOneOrMoreBlock.getLabel();
/*      */     }
/*      */     else {
/*  336 */       str2 = "_cnt" + paramOneOrMoreBlock.ID;
/*      */     }
/*  338 */     println("int " + str2 + "=0;");
/*      */     String str1;
/*  339 */     if (paramOneOrMoreBlock.getLabel() != null) {
/*  340 */       str1 = paramOneOrMoreBlock.getLabel();
/*      */     }
/*      */     else {
/*  343 */       str1 = "_loop" + paramOneOrMoreBlock.ID;
/*      */     }
/*      */ 
/*  346 */     println("for (;;)");
/*  347 */     println("{");
/*  348 */     this.tabs += 1;
/*  349 */     this.blockNestingLevel += 1;
/*      */ 
/*  352 */     genBlockInitAction(paramOneOrMoreBlock);
/*      */ 
/*  355 */     String str3 = this.currentASTResult;
/*  356 */     if (paramOneOrMoreBlock.getLabel() != null) {
/*  357 */       this.currentASTResult = paramOneOrMoreBlock.getLabel();
/*      */     }
/*      */ 
/*  360 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramOneOrMoreBlock);
/*      */ 
/*  372 */     int i = 0;
/*  373 */     int j = this.grammar.maxk;
/*      */ 
/*  375 */     if ((!paramOneOrMoreBlock.greedy) && (paramOneOrMoreBlock.exitLookaheadDepth <= this.grammar.maxk) && (paramOneOrMoreBlock.exitCache[paramOneOrMoreBlock.exitLookaheadDepth].containsEpsilon()))
/*      */     {
/*  379 */       i = 1;
/*  380 */       j = paramOneOrMoreBlock.exitLookaheadDepth;
/*      */     }
/*  382 */     else if ((!paramOneOrMoreBlock.greedy) && (paramOneOrMoreBlock.exitLookaheadDepth == 2147483647))
/*      */     {
/*  385 */       i = 1;
/*      */     }
/*      */ 
/*  390 */     if (i != 0) {
/*  391 */       if (this.DEBUG_CODE_GENERATOR) {
/*  392 */         System.out.println("nongreedy (...)+ loop; exit depth is " + paramOneOrMoreBlock.exitLookaheadDepth);
/*      */       }
/*      */ 
/*  395 */       localObject = getLookaheadTestExpression(paramOneOrMoreBlock.exitCache, j);
/*      */ 
/*  398 */       println("// nongreedy exit test");
/*  399 */       println("if ((" + str2 + " >= 1) && " + (String)localObject + ") goto " + str1 + "_breakloop;");
/*      */     }
/*      */ 
/*  402 */     Object localObject = genCommonBlock(paramOneOrMoreBlock, false);
/*  403 */     genBlockFinish((CSharpBlockFinishingInfo)localObject, "if (" + str2 + " >= 1) { goto " + str1 + "_breakloop; } else { " + this.throwNoViable + "; }");
/*      */ 
/*  408 */     println(str2 + "++;");
/*  409 */     this.tabs -= 1;
/*  410 */     if (this.blockNestingLevel-- == this.saveIndexCreateLevel)
/*  411 */       this.saveIndexCreateLevel = 0;
/*  412 */     println("}");
/*  413 */     _print(str1 + "_breakloop:");
/*  414 */     println(";");
/*  415 */     this.tabs -= 1;
/*  416 */     if (this.blockNestingLevel-- == this.saveIndexCreateLevel)
/*  417 */       this.saveIndexCreateLevel = 0;
/*  418 */     println("}    // ( ... )+");
/*      */ 
/*  421 */     this.currentASTResult = str3;
/*      */   }
/*      */ 
/*      */   public void gen(ParserGrammar paramParserGrammar)
/*      */     throws IOException
/*      */   {
/*  428 */     if (paramParserGrammar.debuggingOutput) {
/*  429 */       this.semPreds = new antlr.collections.impl.Vector();
/*      */     }
/*  431 */     setGrammar(paramParserGrammar);
/*  432 */     if (!(this.grammar instanceof ParserGrammar)) {
/*  433 */       this.antlrTool.panic("Internal error generating parser");
/*      */     }
/*  435 */     genBody(paramParserGrammar);
/*      */   }
/*      */ 
/*      */   public void gen(RuleRefElement paramRuleRefElement)
/*      */   {
/*  442 */     if (this.DEBUG_CODE_GENERATOR) System.out.println("genRR(" + paramRuleRefElement + ")");
/*  443 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(paramRuleRefElement.targetRule);
/*  444 */     if ((localRuleSymbol == null) || (!localRuleSymbol.isDefined()))
/*      */     {
/*  447 */       this.antlrTool.error("Rule '" + paramRuleRefElement.targetRule + "' is not defined", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*  448 */       return;
/*      */     }
/*  450 */     if (!(localRuleSymbol instanceof RuleSymbol))
/*      */     {
/*  453 */       this.antlrTool.error("'" + paramRuleRefElement.targetRule + "' does not name a grammar rule", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*  454 */       return;
/*      */     }
/*      */ 
/*  457 */     genErrorTryForElement(paramRuleRefElement);
/*      */ 
/*  461 */     if (((this.grammar instanceof TreeWalkerGrammar)) && (paramRuleRefElement.getLabel() != null) && (this.syntacticPredLevel == 0))
/*      */     {
/*  465 */       println(paramRuleRefElement.getLabel() + " = _t==ASTNULL ? null : " + this.lt1Value + ";");
/*      */     }
/*      */ 
/*  469 */     if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramRuleRefElement.getAutoGenType() == 3)))
/*      */     {
/*  471 */       declareSaveIndexVariableIfNeeded();
/*  472 */       println("_saveIndex = text.Length;");
/*      */     }
/*      */ 
/*  476 */     printTabs();
/*  477 */     if (paramRuleRefElement.idAssign != null)
/*      */     {
/*  480 */       if (localRuleSymbol.block.returnAction == null)
/*      */       {
/*  482 */         this.antlrTool.warning("Rule '" + paramRuleRefElement.targetRule + "' has no return type", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*      */       }
/*  484 */       _print(paramRuleRefElement.idAssign + "=");
/*      */     }
/*  487 */     else if ((!(this.grammar instanceof LexerGrammar)) && (this.syntacticPredLevel == 0) && (localRuleSymbol.block.returnAction != null))
/*      */     {
/*  489 */       this.antlrTool.warning("Rule '" + paramRuleRefElement.targetRule + "' returns a value", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*      */     }
/*      */ 
/*  494 */     GenRuleInvocation(paramRuleRefElement);
/*      */ 
/*  497 */     if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramRuleRefElement.getAutoGenType() == 3))) {
/*  498 */       declareSaveIndexVariableIfNeeded();
/*  499 */       println("text.Length = _saveIndex;");
/*      */     }
/*      */ 
/*  503 */     if (this.syntacticPredLevel == 0)
/*      */     {
/*  505 */       int i = (this.grammar.hasSyntacticPredicate) && (((this.grammar.buildAST) && (paramRuleRefElement.getLabel() != null)) || ((this.genAST) && (paramRuleRefElement.getAutoGenType() == 1))) ? 1 : 0;
/*      */ 
/*  512 */       if (i != 0) {
/*  513 */         println("if (0 == inputState.guessing)");
/*  514 */         println("{");
/*  515 */         this.tabs += 1;
/*      */       }
/*      */ 
/*  518 */       if ((this.grammar.buildAST) && (paramRuleRefElement.getLabel() != null))
/*      */       {
/*  521 */         println(paramRuleRefElement.getLabel() + "_AST = (" + this.labeledElementASTType + ")returnAST;");
/*      */       }
/*  523 */       if (this.genAST)
/*      */       {
/*  525 */         switch (paramRuleRefElement.getAutoGenType())
/*      */         {
/*      */         case 1:
/*  528 */           if (this.usingCustomAST)
/*  529 */             println("astFactory.addASTChild(ref currentAST, (AST)returnAST);");
/*      */           else
/*  531 */             println("astFactory.addASTChild(ref currentAST, returnAST);");
/*  532 */           break;
/*      */         case 2:
/*  534 */           this.antlrTool.error("Internal: encountered ^ after rule reference");
/*  535 */           break;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  542 */       if (((this.grammar instanceof LexerGrammar)) && (paramRuleRefElement.getLabel() != null))
/*      */       {
/*  544 */         println(paramRuleRefElement.getLabel() + " = returnToken_;");
/*      */       }
/*      */ 
/*  547 */       if (i != 0)
/*      */       {
/*  549 */         this.tabs -= 1;
/*  550 */         println("}");
/*      */       }
/*      */     }
/*  553 */     genErrorCatchForElement(paramRuleRefElement);
/*      */   }
/*      */ 
/*      */   public void gen(StringLiteralElement paramStringLiteralElement)
/*      */   {
/*  559 */     if (this.DEBUG_CODE_GENERATOR) System.out.println("genString(" + paramStringLiteralElement + ")");
/*      */ 
/*  562 */     if ((paramStringLiteralElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  563 */       println(paramStringLiteralElement.getLabel() + " = " + this.lt1Value + ";");
/*      */     }
/*      */ 
/*  567 */     genElementAST(paramStringLiteralElement);
/*      */ 
/*  570 */     boolean bool = this.saveText;
/*  571 */     this.saveText = ((this.saveText) && (paramStringLiteralElement.getAutoGenType() == 1));
/*      */ 
/*  574 */     genMatch(paramStringLiteralElement);
/*      */ 
/*  576 */     this.saveText = bool;
/*      */ 
/*  579 */     if ((this.grammar instanceof TreeWalkerGrammar))
/*  580 */       println("_t = _t.getNextSibling();");
/*      */   }
/*      */ 
/*      */   public void gen(TokenRangeElement paramTokenRangeElement)
/*      */   {
/*  588 */     genErrorTryForElement(paramTokenRangeElement);
/*  589 */     if ((paramTokenRangeElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  590 */       println(paramTokenRangeElement.getLabel() + " = " + this.lt1Value + ";");
/*      */     }
/*      */ 
/*  594 */     genElementAST(paramTokenRangeElement);
/*      */ 
/*  597 */     println("matchRange(" + OctalToUnicode(paramTokenRangeElement.beginText) + "," + OctalToUnicode(paramTokenRangeElement.endText) + ");");
/*  598 */     genErrorCatchForElement(paramTokenRangeElement);
/*      */   }
/*      */ 
/*      */   public void gen(TokenRefElement paramTokenRefElement)
/*      */   {
/*  605 */     if (this.DEBUG_CODE_GENERATOR) System.out.println("genTokenRef(" + paramTokenRefElement + ")");
/*  606 */     if ((this.grammar instanceof LexerGrammar)) {
/*  607 */       this.antlrTool.panic("Token reference found in lexer");
/*      */     }
/*  609 */     genErrorTryForElement(paramTokenRefElement);
/*      */ 
/*  611 */     if ((paramTokenRefElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  612 */       println(paramTokenRefElement.getLabel() + " = " + this.lt1Value + ";");
/*      */     }
/*      */ 
/*  616 */     genElementAST(paramTokenRefElement);
/*      */ 
/*  618 */     genMatch(paramTokenRefElement);
/*  619 */     genErrorCatchForElement(paramTokenRefElement);
/*      */ 
/*  622 */     if ((this.grammar instanceof TreeWalkerGrammar))
/*  623 */       println("_t = _t.getNextSibling();");
/*      */   }
/*      */ 
/*      */   public void gen(TreeElement paramTreeElement)
/*      */   {
/*  629 */     println("AST __t" + paramTreeElement.ID + " = _t;");
/*      */ 
/*  632 */     if (paramTreeElement.root.getLabel() != null) {
/*  633 */       println(paramTreeElement.root.getLabel() + " = (ASTNULL == _t) ? null : (" + this.labeledElementASTType + ")_t;");
/*      */     }
/*      */ 
/*  637 */     if (paramTreeElement.root.getAutoGenType() == 3) {
/*  638 */       this.antlrTool.error("Suffixing a root node with '!' is not implemented", this.grammar.getFilename(), paramTreeElement.getLine(), paramTreeElement.getColumn());
/*      */ 
/*  640 */       paramTreeElement.root.setAutoGenType(1);
/*      */     }
/*  642 */     if (paramTreeElement.root.getAutoGenType() == 2) {
/*  643 */       this.antlrTool.warning("Suffixing a root node with '^' is redundant; already a root", this.grammar.getFilename(), paramTreeElement.getLine(), paramTreeElement.getColumn());
/*      */ 
/*  645 */       paramTreeElement.root.setAutoGenType(1);
/*      */     }
/*      */ 
/*  649 */     genElementAST(paramTreeElement.root);
/*  650 */     if (this.grammar.buildAST)
/*      */     {
/*  652 */       println("ASTPair __currentAST" + paramTreeElement.ID + " = currentAST.copy();");
/*      */ 
/*  654 */       println("currentAST.root = currentAST.child;");
/*  655 */       println("currentAST.child = null;");
/*      */     }
/*      */ 
/*  659 */     if ((paramTreeElement.root instanceof WildcardElement)) {
/*  660 */       println("if (null == _t) throw new MismatchedTokenException();");
/*      */     }
/*      */     else {
/*  663 */       genMatch(paramTreeElement.root);
/*      */     }
/*      */ 
/*  666 */     println("_t = _t.getFirstChild();");
/*      */ 
/*  669 */     for (int i = 0; i < paramTreeElement.getAlternatives().size(); i++) {
/*  670 */       Alternative localAlternative = paramTreeElement.getAlternativeAt(i);
/*  671 */       AlternativeElement localAlternativeElement = localAlternative.head;
/*  672 */       while (localAlternativeElement != null) {
/*  673 */         localAlternativeElement.generate();
/*  674 */         localAlternativeElement = localAlternativeElement.next;
/*      */       }
/*      */     }
/*      */ 
/*  678 */     if (this.grammar.buildAST)
/*      */     {
/*  681 */       println("currentAST = __currentAST" + paramTreeElement.ID + ";");
/*      */     }
/*      */ 
/*  684 */     println("_t = __t" + paramTreeElement.ID + ";");
/*      */ 
/*  686 */     println("_t = _t.getNextSibling();");
/*      */   }
/*      */ 
/*      */   public void gen(TreeWalkerGrammar paramTreeWalkerGrammar) throws IOException
/*      */   {
/*  691 */     setGrammar(paramTreeWalkerGrammar);
/*  692 */     if (!(this.grammar instanceof TreeWalkerGrammar)) {
/*  693 */       this.antlrTool.panic("Internal error generating tree-walker");
/*      */     }
/*  695 */     genBody(paramTreeWalkerGrammar);
/*      */   }
/*      */ 
/*      */   public void gen(WildcardElement paramWildcardElement)
/*      */   {
/*  703 */     if ((paramWildcardElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  704 */       println(paramWildcardElement.getLabel() + " = " + this.lt1Value + ";");
/*      */     }
/*      */ 
/*  708 */     genElementAST(paramWildcardElement);
/*      */ 
/*  710 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/*  711 */       println("if (null == _t) throw new MismatchedTokenException();");
/*      */     }
/*  713 */     else if ((this.grammar instanceof LexerGrammar)) {
/*  714 */       if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramWildcardElement.getAutoGenType() == 3)))
/*      */       {
/*  716 */         declareSaveIndexVariableIfNeeded();
/*  717 */         println("_saveIndex = text.Length;");
/*      */       }
/*  719 */       println("matchNot(EOF/*_CHAR*/);");
/*  720 */       if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramWildcardElement.getAutoGenType() == 3)))
/*      */       {
/*  722 */         declareSaveIndexVariableIfNeeded();
/*  723 */         println("text.Length = _saveIndex;");
/*      */       }
/*      */     }
/*      */     else {
/*  727 */       println("matchNot(" + getValueString(1) + ");");
/*      */     }
/*      */ 
/*  731 */     if ((this.grammar instanceof TreeWalkerGrammar))
/*  732 */       println("_t = _t.getNextSibling();");
/*      */   }
/*      */ 
/*      */   public void gen(ZeroOrMoreBlock paramZeroOrMoreBlock)
/*      */   {
/*  740 */     if (this.DEBUG_CODE_GENERATOR) System.out.println("gen*(" + paramZeroOrMoreBlock + ")");
/*  741 */     println("{    // ( ... )*");
/*  742 */     this.tabs += 1;
/*  743 */     this.blockNestingLevel += 1;
/*  744 */     genBlockPreamble(paramZeroOrMoreBlock);
/*      */     String str1;
/*  746 */     if (paramZeroOrMoreBlock.getLabel() != null) {
/*  747 */       str1 = paramZeroOrMoreBlock.getLabel();
/*      */     }
/*      */     else {
/*  750 */       str1 = "_loop" + paramZeroOrMoreBlock.ID;
/*      */     }
/*  752 */     println("for (;;)");
/*  753 */     println("{");
/*  754 */     this.tabs += 1;
/*  755 */     this.blockNestingLevel += 1;
/*      */ 
/*  758 */     genBlockInitAction(paramZeroOrMoreBlock);
/*      */ 
/*  761 */     String str2 = this.currentASTResult;
/*  762 */     if (paramZeroOrMoreBlock.getLabel() != null) {
/*  763 */       this.currentASTResult = paramZeroOrMoreBlock.getLabel();
/*      */     }
/*      */ 
/*  766 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramZeroOrMoreBlock);
/*      */ 
/*  778 */     int i = 0;
/*  779 */     int j = this.grammar.maxk;
/*      */ 
/*  781 */     if ((!paramZeroOrMoreBlock.greedy) && (paramZeroOrMoreBlock.exitLookaheadDepth <= this.grammar.maxk) && (paramZeroOrMoreBlock.exitCache[paramZeroOrMoreBlock.exitLookaheadDepth].containsEpsilon()))
/*      */     {
/*  785 */       i = 1;
/*  786 */       j = paramZeroOrMoreBlock.exitLookaheadDepth;
/*      */     }
/*  788 */     else if ((!paramZeroOrMoreBlock.greedy) && (paramZeroOrMoreBlock.exitLookaheadDepth == 2147483647))
/*      */     {
/*  791 */       i = 1;
/*      */     }
/*  793 */     if (i != 0) {
/*  794 */       if (this.DEBUG_CODE_GENERATOR) {
/*  795 */         System.out.println("nongreedy (...)* loop; exit depth is " + paramZeroOrMoreBlock.exitLookaheadDepth);
/*      */       }
/*      */ 
/*  798 */       localObject = getLookaheadTestExpression(paramZeroOrMoreBlock.exitCache, j);
/*      */ 
/*  801 */       println("// nongreedy exit test");
/*  802 */       println("if (" + (String)localObject + ") goto " + str1 + "_breakloop;");
/*      */     }
/*      */ 
/*  805 */     Object localObject = genCommonBlock(paramZeroOrMoreBlock, false);
/*  806 */     genBlockFinish((CSharpBlockFinishingInfo)localObject, "goto " + str1 + "_breakloop;");
/*      */ 
/*  808 */     this.tabs -= 1;
/*  809 */     if (this.blockNestingLevel-- == this.saveIndexCreateLevel)
/*  810 */       this.saveIndexCreateLevel = 0;
/*  811 */     println("}");
/*  812 */     _print(str1 + "_breakloop:");
/*  813 */     println(";");
/*  814 */     this.tabs -= 1;
/*  815 */     if (this.blockNestingLevel-- == this.saveIndexCreateLevel)
/*  816 */       this.saveIndexCreateLevel = 0;
/*  817 */     println("}    // ( ... )*");
/*      */ 
/*  820 */     this.currentASTResult = str2;
/*      */   }
/*      */ 
/*      */   protected void genAlt(Alternative paramAlternative, AlternativeBlock paramAlternativeBlock)
/*      */   {
/*  830 */     boolean bool1 = this.genAST;
/*  831 */     this.genAST = ((this.genAST) && (paramAlternative.getAutoGen()));
/*      */ 
/*  833 */     boolean bool2 = this.saveText;
/*  834 */     this.saveText = ((this.saveText) && (paramAlternative.getAutoGen()));
/*      */ 
/*  837 */     Hashtable localHashtable = this.treeVariableMap;
/*  838 */     this.treeVariableMap = new Hashtable();
/*      */ 
/*  841 */     if (paramAlternative.exceptionSpec != null) {
/*  842 */       println("try        // for error handling");
/*  843 */       println("{");
/*  844 */       this.tabs += 1;
/*      */     }
/*      */ 
/*  847 */     AlternativeElement localAlternativeElement = paramAlternative.head;
/*  848 */     while (!(localAlternativeElement instanceof BlockEndElement)) {
/*  849 */       localAlternativeElement.generate();
/*  850 */       localAlternativeElement = localAlternativeElement.next;
/*      */     }
/*      */ 
/*  853 */     if (this.genAST)
/*      */     {
/*  855 */       if ((paramAlternativeBlock instanceof RuleBlock))
/*      */       {
/*  858 */         RuleBlock localRuleBlock = (RuleBlock)paramAlternativeBlock;
/*  859 */         if (this.usingCustomAST)
/*      */         {
/*  861 */           println(localRuleBlock.getRuleName() + "_AST = (" + this.labeledElementASTType + ")currentAST.root;");
/*      */         }
/*      */         else
/*      */         {
/*  865 */           println(localRuleBlock.getRuleName() + "_AST = currentAST.root;");
/*      */         }
/*      */       }
/*  868 */       else if (paramAlternativeBlock.getLabel() != null)
/*      */       {
/*  871 */         this.antlrTool.warning("Labeled subrules not yet supported", this.grammar.getFilename(), paramAlternativeBlock.getLine(), paramAlternativeBlock.getColumn());
/*      */       }
/*      */     }
/*      */ 
/*  875 */     if (paramAlternative.exceptionSpec != null)
/*      */     {
/*  878 */       this.tabs -= 1;
/*  879 */       println("}");
/*  880 */       genErrorHandler(paramAlternative.exceptionSpec);
/*      */     }
/*      */ 
/*  883 */     this.genAST = bool1;
/*  884 */     this.saveText = bool2;
/*      */ 
/*  886 */     this.treeVariableMap = localHashtable;
/*      */   }
/*      */ 
/*      */   protected void genBitsets(antlr.collections.impl.Vector paramVector, int paramInt)
/*      */   {
/*  900 */     println("");
/*  901 */     for (int i = 0; i < paramVector.size(); i++)
/*      */     {
/*  903 */       BitSet localBitSet = (BitSet)paramVector.elementAt(i);
/*      */ 
/*  905 */       localBitSet.growToInclude(paramInt);
/*  906 */       genBitSet(localBitSet, i);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void genBitSet(BitSet paramBitSet, int paramInt)
/*      */   {
/*  922 */     println("private static long[] mk_" + getBitsetName(paramInt) + "()");
/*  923 */     println("{");
/*  924 */     this.tabs += 1;
/*  925 */     int i = paramBitSet.lengthInLongWords();
/*      */     long[] arrayOfLong;
/*      */     int j;
/*  926 */     if (i < 8) {
/*  927 */       println("long[] data = { " + paramBitSet.toStringOfWords() + "};");
/*      */     }
/*      */     else
/*      */     {
/*  931 */       println("long[] data = new long[" + i + "];");
/*  932 */       arrayOfLong = paramBitSet.toPackedArray();
/*  933 */       for (j = 0; j < arrayOfLong.length; ) {
/*  934 */         if ((j + 1 == arrayOfLong.length) || (arrayOfLong[j] != arrayOfLong[(j + 1)]))
/*      */         {
/*  936 */           println("data[" + j + "]=" + arrayOfLong[j] + "L;");
/*  937 */           j++;
/*      */         }
/*      */         else
/*      */         {
/*  943 */           for (int k = j + 1; (k < arrayOfLong.length) && (arrayOfLong[k] == arrayOfLong[j]); k++);
/*  948 */           println("for (int i = " + j + "; i<=" + (k - 1) + "; i++) { data[i]=" + arrayOfLong[j] + "L; }");
/*      */ 
/*  950 */           j = k;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  955 */     println("return data;");
/*  956 */     this.tabs -= 1;
/*  957 */     println("}");
/*      */ 
/*  959 */     println("public static readonly BitSet " + getBitsetName(paramInt) + " = new BitSet(" + "mk_" + getBitsetName(paramInt) + "()" + ");");
/*      */   }
/*      */ 
/*      */   protected String getBitsetName(int paramInt)
/*      */   {
/*  969 */     return "tokenSet_" + paramInt + "_";
/*      */   }
/*      */ 
/*      */   private void genBlockFinish(CSharpBlockFinishingInfo paramCSharpBlockFinishingInfo, String paramString)
/*      */   {
/*  981 */     if ((paramCSharpBlockFinishingInfo.needAnErrorClause) && ((paramCSharpBlockFinishingInfo.generatedAnIf) || (paramCSharpBlockFinishingInfo.generatedSwitch)))
/*      */     {
/*  984 */       if (paramCSharpBlockFinishingInfo.generatedAnIf) {
/*  985 */         println("else");
/*  986 */         println("{");
/*      */       }
/*      */       else {
/*  989 */         println("{");
/*      */       }
/*  991 */       this.tabs += 1;
/*  992 */       println(paramString);
/*  993 */       this.tabs -= 1;
/*  994 */       println("}");
/*      */     }
/*      */ 
/*  997 */     if (paramCSharpBlockFinishingInfo.postscript != null)
/*  998 */       if ((paramCSharpBlockFinishingInfo.needAnErrorClause) && (paramCSharpBlockFinishingInfo.generatedSwitch) && (!paramCSharpBlockFinishingInfo.generatedAnIf) && (paramString != null))
/*      */       {
/* 1002 */         if ((paramString.indexOf("throw") == 0) || (paramString.indexOf("goto") == 0))
/*      */         {
/* 1004 */           int i = paramCSharpBlockFinishingInfo.postscript.indexOf("break;") + 6;
/* 1005 */           String str = paramCSharpBlockFinishingInfo.postscript.substring(i);
/* 1006 */           println(str);
/*      */         }
/*      */         else {
/* 1009 */           println(paramCSharpBlockFinishingInfo.postscript);
/*      */         }
/*      */       }
/*      */       else
/* 1013 */         println(paramCSharpBlockFinishingInfo.postscript);
/*      */   }
/*      */ 
/*      */   protected void genBlockInitAction(AlternativeBlock paramAlternativeBlock)
/*      */   {
/* 1025 */     if (paramAlternativeBlock.initAction != null)
/* 1026 */       printAction(processActionForSpecialSymbols(paramAlternativeBlock.initAction, paramAlternativeBlock.getLine(), this.currentRule, null));
/*      */   }
/*      */ 
/*      */   protected void genBlockPreamble(AlternativeBlock paramAlternativeBlock)
/*      */   {
/* 1037 */     if ((paramAlternativeBlock instanceof RuleBlock)) {
/* 1038 */       RuleBlock localRuleBlock = (RuleBlock)paramAlternativeBlock;
/* 1039 */       if (localRuleBlock.labeledElements != null)
/* 1040 */         for (int i = 0; i < localRuleBlock.labeledElements.size(); i++)
/*      */         {
/* 1042 */           AlternativeElement localAlternativeElement = (AlternativeElement)localRuleBlock.labeledElements.elementAt(i);
/*      */ 
/* 1049 */           if (((localAlternativeElement instanceof RuleRefElement)) || (((localAlternativeElement instanceof AlternativeBlock)) && (!(localAlternativeElement instanceof RuleBlock)) && (!(localAlternativeElement instanceof SynPredBlock))))
/*      */           {
/* 1056 */             if ((!(localAlternativeElement instanceof RuleRefElement)) && (((AlternativeBlock)localAlternativeElement).not) && (this.analyzer.subruleCanBeInverted((AlternativeBlock)localAlternativeElement, this.grammar instanceof LexerGrammar)))
/*      */             {
/* 1064 */               println(this.labeledElementType + " " + localAlternativeElement.getLabel() + " = " + this.labeledElementInit + ";");
/* 1065 */               if (this.grammar.buildAST)
/* 1066 */                 genASTDeclaration(localAlternativeElement);
/*      */             }
/*      */             else
/*      */             {
/* 1070 */               if (this.grammar.buildAST)
/*      */               {
/* 1074 */                 genASTDeclaration(localAlternativeElement);
/*      */               }
/* 1076 */               if ((this.grammar instanceof LexerGrammar)) {
/* 1077 */                 println("IToken " + localAlternativeElement.getLabel() + " = null;");
/*      */               }
/* 1079 */               if ((this.grammar instanceof TreeWalkerGrammar))
/*      */               {
/* 1082 */                 println(this.labeledElementType + " " + localAlternativeElement.getLabel() + " = " + this.labeledElementInit + ";");
/*      */               }
/*      */             }
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 1089 */             println(this.labeledElementType + " " + localAlternativeElement.getLabel() + " = " + this.labeledElementInit + ";");
/*      */ 
/* 1091 */             if (this.grammar.buildAST)
/*      */             {
/* 1093 */               if (((localAlternativeElement instanceof GrammarAtom)) && (((GrammarAtom)localAlternativeElement).getASTNodeType() != null))
/*      */               {
/* 1095 */                 GrammarAtom localGrammarAtom = (GrammarAtom)localAlternativeElement;
/* 1096 */                 genASTDeclaration(localAlternativeElement, localGrammarAtom.getASTNodeType());
/*      */               }
/*      */               else {
/* 1099 */                 genASTDeclaration(localAlternativeElement);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void genBody(LexerGrammar paramLexerGrammar)
/*      */     throws IOException
/*      */   {
/* 1112 */     setupOutput(this.grammar.getClassName());
/*      */ 
/* 1114 */     this.genAST = false;
/* 1115 */     this.saveText = true;
/*      */ 
/* 1117 */     this.tabs = 0;
/*      */ 
/* 1120 */     genHeader();
/*      */ 
/* 1122 */     println(this.behavior.getHeaderAction(""));
/*      */ 
/* 1125 */     if (nameSpace != null)
/* 1126 */       nameSpace.emitDeclarations(this.currentOutput);
/* 1127 */     this.tabs += 1;
/*      */ 
/* 1130 */     println("// Generate header specific to lexer CSharp file");
/* 1131 */     println("using System;");
/* 1132 */     println("using Stream                          = System.IO.Stream;");
/* 1133 */     println("using TextReader                      = System.IO.TextReader;");
/* 1134 */     println("using Hashtable                       = System.Collections.Hashtable;");
/* 1135 */     println("using Comparer                        = System.Collections.Comparer;");
/* 1136 */     if (!paramLexerGrammar.caseSensitiveLiterals)
/*      */     {
/* 1138 */       println("using CaseInsensitiveHashCodeProvider = System.Collections.CaseInsensitiveHashCodeProvider;");
/* 1139 */       println("using CaseInsensitiveComparer         = System.Collections.CaseInsensitiveComparer;");
/*      */     }
/* 1141 */     println("");
/* 1142 */     println("using TokenStreamException            = antlr.TokenStreamException;");
/* 1143 */     println("using TokenStreamIOException          = antlr.TokenStreamIOException;");
/* 1144 */     println("using TokenStreamRecognitionException = antlr.TokenStreamRecognitionException;");
/* 1145 */     println("using CharStreamException             = antlr.CharStreamException;");
/* 1146 */     println("using CharStreamIOException           = antlr.CharStreamIOException;");
/* 1147 */     println("using ANTLRException                  = antlr.ANTLRException;");
/* 1148 */     println("using CharScanner                     = antlr.CharScanner;");
/* 1149 */     println("using InputBuffer                     = antlr.InputBuffer;");
/* 1150 */     println("using ByteBuffer                      = antlr.ByteBuffer;");
/* 1151 */     println("using CharBuffer                      = antlr.CharBuffer;");
/* 1152 */     println("using Token                           = antlr.Token;");
/* 1153 */     println("using IToken                          = antlr.IToken;");
/* 1154 */     println("using CommonToken                     = antlr.CommonToken;");
/* 1155 */     println("using SemanticException               = antlr.SemanticException;");
/* 1156 */     println("using RecognitionException            = antlr.RecognitionException;");
/* 1157 */     println("using NoViableAltForCharException     = antlr.NoViableAltForCharException;");
/* 1158 */     println("using MismatchedCharException         = antlr.MismatchedCharException;");
/* 1159 */     println("using TokenStream                     = antlr.TokenStream;");
/* 1160 */     println("using LexerSharedInputState           = antlr.LexerSharedInputState;");
/* 1161 */     println("using BitSet                          = antlr.collections.impl.BitSet;");
/*      */ 
/* 1164 */     println(this.grammar.preambleAction.getText());
/*      */ 
/* 1167 */     String str = null;
/* 1168 */     if (this.grammar.superClass != null) {
/* 1169 */       str = this.grammar.superClass;
/*      */     }
/*      */     else {
/* 1172 */       str = "antlr." + this.grammar.getSuperClass();
/*      */     }
/*      */ 
/* 1176 */     if (this.grammar.comment != null)
/*      */     {
/* 1178 */       _println(this.grammar.comment);
/*      */     }
/*      */ 
/* 1181 */     Token localToken = (Token)this.grammar.options.get("classHeaderPrefix");
/* 1182 */     if (localToken == null) {
/* 1183 */       print("public ");
/*      */     }
/*      */     else {
/* 1186 */       localObject1 = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 1187 */       if (localObject1 == null) {
/* 1188 */         print("public ");
/*      */       }
/*      */       else {
/* 1191 */         print((String)localObject1 + " ");
/*      */       }
/*      */     }
/*      */ 
/* 1195 */     print("class " + this.grammar.getClassName() + " : " + str);
/* 1196 */     println(", TokenStream");
/* 1197 */     Object localObject1 = (Token)this.grammar.options.get("classHeaderSuffix");
/* 1198 */     if (localObject1 != null)
/*      */     {
/* 1200 */       localObject2 = StringUtils.stripFrontBack(((Token)localObject1).getText(), "\"", "\"");
/* 1201 */       if (localObject2 != null)
/*      */       {
/* 1203 */         print(", " + (String)localObject2);
/*      */       }
/*      */     }
/* 1206 */     println(" {");
/* 1207 */     this.tabs += 1;
/*      */ 
/* 1210 */     genTokenDefinitions(this.grammar.tokenManager);
/*      */ 
/* 1213 */     print(processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, null));
/*      */ 
/* 1221 */     println("public " + this.grammar.getClassName() + "(Stream ins) : this(new ByteBuffer(ins))");
/* 1222 */     println("{");
/* 1223 */     println("}");
/* 1224 */     println("");
/*      */ 
/* 1230 */     println("public " + this.grammar.getClassName() + "(TextReader r) : this(new CharBuffer(r))");
/* 1231 */     println("{");
/* 1232 */     println("}");
/* 1233 */     println("");
/*      */ 
/* 1235 */     print("public " + this.grammar.getClassName() + "(InputBuffer ib)");
/*      */ 
/* 1237 */     if (this.grammar.debuggingOutput)
/* 1238 */       println(" : this(new LexerSharedInputState(new antlr.debug.DebuggingInputBuffer(ib)))");
/*      */     else
/* 1240 */       println(" : this(new LexerSharedInputState(ib))");
/* 1241 */     println("{");
/* 1242 */     println("}");
/* 1243 */     println("");
/*      */ 
/* 1248 */     println("public " + this.grammar.getClassName() + "(LexerSharedInputState state) : base(state)");
/* 1249 */     println("{");
/* 1250 */     this.tabs += 1;
/* 1251 */     println("initialize();");
/* 1252 */     this.tabs -= 1;
/* 1253 */     println("}");
/*      */ 
/* 1256 */     println("private void initialize()");
/* 1257 */     println("{");
/* 1258 */     this.tabs += 1;
/*      */ 
/* 1262 */     if (this.grammar.debuggingOutput) {
/* 1263 */       println("ruleNames  = _ruleNames;");
/* 1264 */       println("semPredNames = _semPredNames;");
/* 1265 */       println("setupDebugging();");
/*      */     }
/*      */ 
/* 1271 */     println("caseSensitiveLiterals = " + paramLexerGrammar.caseSensitiveLiterals + ";");
/* 1272 */     println("setCaseSensitive(" + paramLexerGrammar.caseSensitive + ");");
/*      */ 
/* 1277 */     if (paramLexerGrammar.caseSensitiveLiterals)
/* 1278 */       println("literals = new Hashtable(100, (float) 0.4, null, Comparer.Default);");
/*      */     else
/* 1280 */       println("literals = new Hashtable(100, (float) 0.4, CaseInsensitiveHashCodeProvider.Default, CaseInsensitiveComparer.Default);");
/* 1281 */     Object localObject2 = this.grammar.tokenManager.getTokenSymbolKeys();
/*      */     Object localObject4;
/* 1282 */     while (((Enumeration)localObject2).hasMoreElements()) {
/* 1283 */       localObject3 = (String)((Enumeration)localObject2).nextElement();
/* 1284 */       if (((String)localObject3).charAt(0) == '"')
/*      */       {
/* 1287 */         TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol((String)localObject3);
/* 1288 */         if ((localTokenSymbol instanceof StringLiteralSymbol)) {
/* 1289 */           localObject4 = (StringLiteralSymbol)localTokenSymbol;
/* 1290 */           println("literals.Add(" + ((StringLiteralSymbol)localObject4).getId() + ", " + ((StringLiteralSymbol)localObject4).getTokenType() + ");");
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1295 */     this.tabs -= 1;
/* 1296 */     println("}");
/*      */ 
/* 1299 */     if (this.grammar.debuggingOutput) {
/* 1300 */       println("private static readonly string[] _ruleNames = new string[] {");
/*      */ 
/* 1302 */       localObject3 = this.grammar.rules.elements();
/* 1303 */       i = 0;
/* 1304 */       while (((Enumeration)localObject3).hasMoreElements()) {
/* 1305 */         localObject4 = (GrammarSymbol)((Enumeration)localObject3).nextElement();
/* 1306 */         if ((localObject4 instanceof RuleSymbol))
/* 1307 */           println("  \"" + ((RuleSymbol)localObject4).getId() + "\",");
/*      */       }
/* 1309 */       println("};");
/*      */     }
/*      */ 
/* 1315 */     genNextToken();
/*      */ 
/* 1318 */     Object localObject3 = this.grammar.rules.elements();
/* 1319 */     int i = 0;
/* 1320 */     while (((Enumeration)localObject3).hasMoreElements()) {
/* 1321 */       localObject4 = (RuleSymbol)((Enumeration)localObject3).nextElement();
/*      */ 
/* 1323 */       if (!((RuleSymbol)localObject4).getId().equals("mnextToken")) {
/* 1324 */         genRule((RuleSymbol)localObject4, false, i++, this.grammar.tokenManager);
/*      */       }
/* 1326 */       exitIfError();
/*      */     }
/*      */ 
/* 1330 */     if (this.grammar.debuggingOutput) {
/* 1331 */       genSemPredMap();
/*      */     }
/*      */ 
/* 1334 */     genBitsets(this.bitsetsUsed, ((LexerGrammar)this.grammar).charVocabulary.size());
/*      */ 
/* 1336 */     println("");
/* 1337 */     this.tabs -= 1;
/* 1338 */     println("}");
/*      */ 
/* 1340 */     this.tabs -= 1;
/*      */ 
/* 1342 */     if (nameSpace != null) {
/* 1343 */       nameSpace.emitClosures(this.currentOutput);
/*      */     }
/*      */ 
/* 1346 */     this.currentOutput.close();
/* 1347 */     this.currentOutput = null;
/*      */   }
/*      */ 
/*      */   public void genInitFactory(Grammar paramGrammar) {
/* 1351 */     if (paramGrammar.buildAST)
/*      */     {
/* 1355 */       println("static public void initializeASTFactory( ASTFactory factory )");
/* 1356 */       println("{");
/* 1357 */       this.tabs += 1;
/*      */ 
/* 1359 */       println("factory.setMaxNodeType(" + paramGrammar.tokenManager.maxTokenType() + ");");
/*      */ 
/* 1363 */       antlr.collections.impl.Vector localVector = paramGrammar.tokenManager.getVocabulary();
/* 1364 */       for (int i = 0; i < localVector.size(); i++) {
/* 1365 */         String str = (String)localVector.elementAt(i);
/* 1366 */         if (str != null) {
/* 1367 */           TokenSymbol localTokenSymbol = paramGrammar.tokenManager.getTokenSymbol(str);
/* 1368 */           if ((localTokenSymbol != null) && (localTokenSymbol.getASTNodeType() != null)) {
/* 1369 */             println("factory.setTokenTypeASTNodeType(" + str + ", \"" + localTokenSymbol.getASTNodeType() + "\");");
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/* 1374 */       this.tabs -= 1;
/* 1375 */       println("}");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void genBody(ParserGrammar paramParserGrammar)
/*      */     throws IOException
/*      */   {
/* 1383 */     setupOutput(this.grammar.getClassName());
/*      */ 
/* 1385 */     this.genAST = this.grammar.buildAST;
/*      */ 
/* 1387 */     this.tabs = 0;
/*      */ 
/* 1390 */     genHeader();
/*      */ 
/* 1392 */     println(this.behavior.getHeaderAction(""));
/*      */ 
/* 1395 */     if (nameSpace != null)
/* 1396 */       nameSpace.emitDeclarations(this.currentOutput);
/* 1397 */     this.tabs += 1;
/*      */ 
/* 1400 */     println("// Generate the header common to all output files.");
/* 1401 */     println("using System;");
/* 1402 */     println("");
/* 1403 */     println("using TokenBuffer              = antlr.TokenBuffer;");
/* 1404 */     println("using TokenStreamException     = antlr.TokenStreamException;");
/* 1405 */     println("using TokenStreamIOException   = antlr.TokenStreamIOException;");
/* 1406 */     println("using ANTLRException           = antlr.ANTLRException;");
/*      */ 
/* 1408 */     String str1 = this.grammar.getSuperClass();
/* 1409 */     String[] arrayOfString = split(str1, ".");
/* 1410 */     println("using " + arrayOfString[(arrayOfString.length - 1)] + " = antlr." + str1 + ";");
/*      */ 
/* 1412 */     println("using Token                    = antlr.Token;");
/* 1413 */     println("using IToken                   = antlr.IToken;");
/* 1414 */     println("using TokenStream              = antlr.TokenStream;");
/* 1415 */     println("using RecognitionException     = antlr.RecognitionException;");
/* 1416 */     println("using NoViableAltException     = antlr.NoViableAltException;");
/* 1417 */     println("using MismatchedTokenException = antlr.MismatchedTokenException;");
/* 1418 */     println("using SemanticException        = antlr.SemanticException;");
/* 1419 */     println("using ParserSharedInputState   = antlr.ParserSharedInputState;");
/* 1420 */     println("using BitSet                   = antlr.collections.impl.BitSet;");
/* 1421 */     if (this.genAST) {
/* 1422 */       println("using AST                      = antlr.collections.AST;");
/* 1423 */       println("using ASTPair                  = antlr.ASTPair;");
/* 1424 */       println("using ASTFactory               = antlr.ASTFactory;");
/* 1425 */       println("using ASTArray                 = antlr.collections.impl.ASTArray;");
/*      */     }
/*      */ 
/* 1429 */     println(this.grammar.preambleAction.getText());
/*      */ 
/* 1432 */     String str2 = null;
/* 1433 */     if (this.grammar.superClass != null)
/* 1434 */       str2 = this.grammar.superClass;
/*      */     else {
/* 1436 */       str2 = "antlr." + this.grammar.getSuperClass();
/*      */     }
/*      */ 
/* 1439 */     if (this.grammar.comment != null) {
/* 1440 */       _println(this.grammar.comment);
/*      */     }
/*      */ 
/* 1443 */     Token localToken = (Token)this.grammar.options.get("classHeaderPrefix");
/* 1444 */     if (localToken == null) {
/* 1445 */       print("public ");
/*      */     }
/*      */     else {
/* 1448 */       localObject1 = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 1449 */       if (localObject1 == null) {
/* 1450 */         print("public ");
/*      */       }
/*      */       else {
/* 1453 */         print((String)localObject1 + " ");
/*      */       }
/*      */     }
/*      */ 
/* 1457 */     println("class " + this.grammar.getClassName() + " : " + str2);
/*      */ 
/* 1459 */     Object localObject1 = (Token)this.grammar.options.get("classHeaderSuffix");
/* 1460 */     if (localObject1 != null) {
/* 1461 */       localObject2 = StringUtils.stripFrontBack(((Token)localObject1).getText(), "\"", "\"");
/* 1462 */       if (localObject2 != null)
/* 1463 */         print("              , " + (String)localObject2);
/*      */     }
/* 1465 */     println("{");
/* 1466 */     this.tabs += 1;
/*      */ 
/* 1469 */     genTokenDefinitions(this.grammar.tokenManager);
/*      */     GrammarSymbol localGrammarSymbol;
/* 1473 */     if (this.grammar.debuggingOutput) {
/* 1474 */       println("private static readonly string[] _ruleNames = new string[] {");
/* 1475 */       this.tabs += 1;
/*      */ 
/* 1477 */       localObject2 = this.grammar.rules.elements();
/* 1478 */       i = 0;
/* 1479 */       while (((Enumeration)localObject2).hasMoreElements()) {
/* 1480 */         localGrammarSymbol = (GrammarSymbol)((Enumeration)localObject2).nextElement();
/* 1481 */         if ((localGrammarSymbol instanceof RuleSymbol))
/* 1482 */           println("  \"" + ((RuleSymbol)localGrammarSymbol).getId() + "\",");
/*      */       }
/* 1484 */       this.tabs -= 1;
/* 1485 */       println("};");
/*      */     }
/*      */ 
/* 1489 */     print(processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, null));
/*      */ 
/* 1494 */     println("");
/* 1495 */     println("protected void initialize()");
/* 1496 */     println("{");
/* 1497 */     this.tabs += 1;
/* 1498 */     println("tokenNames = tokenNames_;");
/*      */ 
/* 1500 */     if (this.grammar.buildAST) {
/* 1501 */       println("initializeFactory();");
/*      */     }
/*      */ 
/* 1505 */     if (this.grammar.debuggingOutput) {
/* 1506 */       println("ruleNames  = _ruleNames;");
/* 1507 */       println("semPredNames = _semPredNames;");
/* 1508 */       println("setupDebugging(tokenBuf);");
/*      */     }
/* 1510 */     this.tabs -= 1;
/* 1511 */     println("}");
/* 1512 */     println("");
/*      */ 
/* 1514 */     println("");
/* 1515 */     println("protected " + this.grammar.getClassName() + "(TokenBuffer tokenBuf, int k) : base(tokenBuf, k)");
/* 1516 */     println("{");
/* 1517 */     this.tabs += 1;
/* 1518 */     println("initialize();");
/* 1519 */     this.tabs -= 1;
/* 1520 */     println("}");
/* 1521 */     println("");
/*      */ 
/* 1523 */     println("public " + this.grammar.getClassName() + "(TokenBuffer tokenBuf) : this(tokenBuf," + this.grammar.maxk + ")");
/* 1524 */     println("{");
/* 1525 */     println("}");
/* 1526 */     println("");
/*      */ 
/* 1529 */     println("protected " + this.grammar.getClassName() + "(TokenStream lexer, int k) : base(lexer,k)");
/* 1530 */     println("{");
/* 1531 */     this.tabs += 1;
/* 1532 */     println("initialize();");
/* 1533 */     this.tabs -= 1;
/* 1534 */     println("}");
/* 1535 */     println("");
/*      */ 
/* 1537 */     println("public " + this.grammar.getClassName() + "(TokenStream lexer) : this(lexer," + this.grammar.maxk + ")");
/* 1538 */     println("{");
/* 1539 */     println("}");
/* 1540 */     println("");
/*      */ 
/* 1542 */     println("public " + this.grammar.getClassName() + "(ParserSharedInputState state) : base(state," + this.grammar.maxk + ")");
/* 1543 */     println("{");
/* 1544 */     this.tabs += 1;
/* 1545 */     println("initialize();");
/* 1546 */     this.tabs -= 1;
/* 1547 */     println("}");
/* 1548 */     println("");
/*      */ 
/* 1550 */     this.astTypes = new java.util.Vector(100);
/*      */ 
/* 1553 */     Object localObject2 = this.grammar.rules.elements();
/* 1554 */     int i = 0;
/* 1555 */     while (((Enumeration)localObject2).hasMoreElements()) {
/* 1556 */       localGrammarSymbol = (GrammarSymbol)((Enumeration)localObject2).nextElement();
/* 1557 */       if ((localGrammarSymbol instanceof RuleSymbol)) {
/* 1558 */         RuleSymbol localRuleSymbol = (RuleSymbol)localGrammarSymbol;
/* 1559 */         genRule(localRuleSymbol, localRuleSymbol.references.size() == 0, i++, this.grammar.tokenManager);
/*      */       }
/* 1561 */       exitIfError();
/*      */     }
/* 1563 */     if (this.usingCustomAST)
/*      */     {
/* 1567 */       println("public new " + this.labeledElementASTType + " getAST()");
/* 1568 */       println("{");
/* 1569 */       this.tabs += 1;
/* 1570 */       println("return (" + this.labeledElementASTType + ") returnAST;");
/* 1571 */       this.tabs -= 1;
/* 1572 */       println("}");
/* 1573 */       println("");
/*      */     }
/*      */ 
/* 1578 */     println("private void initializeFactory()");
/* 1579 */     println("{");
/* 1580 */     this.tabs += 1;
/* 1581 */     if (this.grammar.buildAST) {
/* 1582 */       println("if (astFactory == null)");
/* 1583 */       println("{");
/* 1584 */       this.tabs += 1;
/* 1585 */       if (this.usingCustomAST)
/*      */       {
/* 1587 */         println("astFactory = new ASTFactory(\"" + this.labeledElementASTType + "\");");
/*      */       }
/*      */       else
/* 1590 */         println("astFactory = new ASTFactory();");
/* 1591 */       this.tabs -= 1;
/* 1592 */       println("}");
/* 1593 */       println("initializeASTFactory( astFactory );");
/*      */     }
/* 1595 */     this.tabs -= 1;
/* 1596 */     println("}");
/* 1597 */     genInitFactory(paramParserGrammar);
/*      */ 
/* 1600 */     genTokenStrings();
/*      */ 
/* 1603 */     genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
/*      */ 
/* 1606 */     if (this.grammar.debuggingOutput) {
/* 1607 */       genSemPredMap();
/*      */     }
/*      */ 
/* 1610 */     println("");
/* 1611 */     this.tabs -= 1;
/* 1612 */     println("}");
/*      */ 
/* 1614 */     this.tabs -= 1;
/*      */ 
/* 1616 */     if (nameSpace != null) {
/* 1617 */       nameSpace.emitClosures(this.currentOutput);
/*      */     }
/*      */ 
/* 1620 */     this.currentOutput.close();
/* 1621 */     this.currentOutput = null;
/*      */   }
/*      */ 
/*      */   public void genBody(TreeWalkerGrammar paramTreeWalkerGrammar)
/*      */     throws IOException
/*      */   {
/* 1628 */     setupOutput(this.grammar.getClassName());
/*      */ 
/* 1630 */     this.genAST = this.grammar.buildAST;
/* 1631 */     this.tabs = 0;
/*      */ 
/* 1634 */     genHeader();
/*      */ 
/* 1636 */     println(this.behavior.getHeaderAction(""));
/*      */ 
/* 1639 */     if (nameSpace != null)
/* 1640 */       nameSpace.emitDeclarations(this.currentOutput);
/* 1641 */     this.tabs += 1;
/*      */ 
/* 1644 */     println("// Generate header specific to the tree-parser CSharp file");
/* 1645 */     println("using System;");
/* 1646 */     println("");
/* 1647 */     println("using " + this.grammar.getSuperClass() + " = antlr." + this.grammar.getSuperClass() + ";");
/* 1648 */     println("using Token                    = antlr.Token;");
/* 1649 */     println("using IToken                   = antlr.IToken;");
/* 1650 */     println("using AST                      = antlr.collections.AST;");
/* 1651 */     println("using RecognitionException     = antlr.RecognitionException;");
/* 1652 */     println("using ANTLRException           = antlr.ANTLRException;");
/* 1653 */     println("using NoViableAltException     = antlr.NoViableAltException;");
/* 1654 */     println("using MismatchedTokenException = antlr.MismatchedTokenException;");
/* 1655 */     println("using SemanticException        = antlr.SemanticException;");
/* 1656 */     println("using BitSet                   = antlr.collections.impl.BitSet;");
/* 1657 */     println("using ASTPair                  = antlr.ASTPair;");
/* 1658 */     println("using ASTFactory               = antlr.ASTFactory;");
/* 1659 */     println("using ASTArray                 = antlr.collections.impl.ASTArray;");
/*      */ 
/* 1662 */     println(this.grammar.preambleAction.getText());
/*      */ 
/* 1665 */     String str1 = null;
/* 1666 */     if (this.grammar.superClass != null) {
/* 1667 */       str1 = this.grammar.superClass;
/*      */     }
/*      */     else {
/* 1670 */       str1 = "antlr." + this.grammar.getSuperClass();
/*      */     }
/* 1672 */     println("");
/*      */ 
/* 1675 */     if (this.grammar.comment != null) {
/* 1676 */       _println(this.grammar.comment);
/*      */     }
/*      */ 
/* 1679 */     Token localToken = (Token)this.grammar.options.get("classHeaderPrefix");
/* 1680 */     if (localToken == null) {
/* 1681 */       print("public ");
/*      */     }
/*      */     else {
/* 1684 */       localObject1 = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 1685 */       if (localObject1 == null) {
/* 1686 */         print("public ");
/*      */       }
/*      */       else {
/* 1689 */         print((String)localObject1 + " ");
/*      */       }
/*      */     }
/*      */ 
/* 1693 */     println("class " + this.grammar.getClassName() + " : " + str1);
/* 1694 */     Object localObject1 = (Token)this.grammar.options.get("classHeaderSuffix");
/* 1695 */     if (localObject1 != null) {
/* 1696 */       localObject2 = StringUtils.stripFrontBack(((Token)localObject1).getText(), "\"", "\"");
/* 1697 */       if (localObject2 != null) {
/* 1698 */         print("              , " + (String)localObject2);
/*      */       }
/*      */     }
/* 1701 */     println("{");
/* 1702 */     this.tabs += 1;
/*      */ 
/* 1705 */     genTokenDefinitions(this.grammar.tokenManager);
/*      */ 
/* 1708 */     print(processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, null));
/*      */ 
/* 1713 */     println("public " + this.grammar.getClassName() + "()");
/* 1714 */     println("{");
/* 1715 */     this.tabs += 1;
/* 1716 */     println("tokenNames = tokenNames_;");
/* 1717 */     this.tabs -= 1;
/* 1718 */     println("}");
/* 1719 */     println("");
/*      */ 
/* 1721 */     this.astTypes = new java.util.Vector();
/*      */ 
/* 1723 */     Object localObject2 = this.grammar.rules.elements();
/* 1724 */     int i = 0;
/* 1725 */     String str2 = "";
/* 1726 */     while (((Enumeration)localObject2).hasMoreElements()) {
/* 1727 */       GrammarSymbol localGrammarSymbol = (GrammarSymbol)((Enumeration)localObject2).nextElement();
/* 1728 */       if ((localGrammarSymbol instanceof RuleSymbol)) {
/* 1729 */         RuleSymbol localRuleSymbol = (RuleSymbol)localGrammarSymbol;
/* 1730 */         genRule(localRuleSymbol, localRuleSymbol.references.size() == 0, i++, this.grammar.tokenManager);
/*      */       }
/* 1732 */       exitIfError();
/*      */     }
/*      */ 
/* 1735 */     if (this.usingCustomAST)
/*      */     {
/* 1739 */       println("public new " + this.labeledElementASTType + " getAST()");
/* 1740 */       println("{");
/* 1741 */       this.tabs += 1;
/* 1742 */       println("return (" + this.labeledElementASTType + ") returnAST;");
/* 1743 */       this.tabs -= 1;
/* 1744 */       println("}");
/* 1745 */       println("");
/*      */     }
/*      */ 
/* 1749 */     genInitFactory(this.grammar);
/*      */ 
/* 1752 */     genTokenStrings();
/*      */ 
/* 1755 */     genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
/*      */ 
/* 1758 */     this.tabs -= 1;
/* 1759 */     println("}");
/* 1760 */     println("");
/*      */ 
/* 1762 */     this.tabs -= 1;
/*      */ 
/* 1764 */     if (nameSpace != null) {
/* 1765 */       nameSpace.emitClosures(this.currentOutput);
/*      */     }
/*      */ 
/* 1768 */     this.currentOutput.close();
/* 1769 */     this.currentOutput = null;
/*      */   }
/*      */ 
/*      */   protected void genCases(BitSet paramBitSet)
/*      */   {
/* 1776 */     if (this.DEBUG_CODE_GENERATOR) System.out.println("genCases(" + paramBitSet + ")");
/*      */ 
/* 1779 */     int[] arrayOfInt = paramBitSet.toArray();
/*      */ 
/* 1781 */     int i = (this.grammar instanceof LexerGrammar) ? 4 : 1;
/* 1782 */     int j = 1;
/* 1783 */     int k = 1;
/* 1784 */     for (int m = 0; m < arrayOfInt.length; m++) {
/* 1785 */       if (j == 1)
/* 1786 */         print("");
/*      */       else {
/* 1788 */         _print("  ");
/*      */       }
/* 1790 */       _print("case " + getValueString(arrayOfInt[m]) + ":");
/* 1791 */       if (j == i) {
/* 1792 */         _println("");
/* 1793 */         k = 1;
/* 1794 */         j = 1;
/*      */       }
/*      */       else {
/* 1797 */         j++;
/* 1798 */         k = 0;
/*      */       }
/*      */     }
/* 1801 */     if (k == 0)
/* 1802 */       _println("");
/*      */   }
/*      */ 
/*      */   public CSharpBlockFinishingInfo genCommonBlock(AlternativeBlock paramAlternativeBlock, boolean paramBoolean)
/*      */   {
/* 1820 */     int i = 0;
/* 1821 */     int j = 0;
/* 1822 */     int k = 0;
/* 1823 */     CSharpBlockFinishingInfo localCSharpBlockFinishingInfo = new CSharpBlockFinishingInfo();
/* 1824 */     if (this.DEBUG_CODE_GENERATOR) System.out.println("genCommonBlock(" + paramAlternativeBlock + ")");
/*      */ 
/* 1827 */     boolean bool1 = this.genAST;
/* 1828 */     this.genAST = ((this.genAST) && (paramAlternativeBlock.getAutoGen()));
/*      */ 
/* 1830 */     boolean bool2 = this.saveText;
/* 1831 */     this.saveText = ((this.saveText) && (paramAlternativeBlock.getAutoGen()));
/*      */     Object localObject1;
/* 1834 */     if ((paramAlternativeBlock.not) && (this.analyzer.subruleCanBeInverted(paramAlternativeBlock, this.grammar instanceof LexerGrammar)))
/*      */     {
/* 1837 */       if (this.DEBUG_CODE_GENERATOR) System.out.println("special case: ~(subrule)");
/* 1838 */       localObject1 = this.analyzer.look(1, paramAlternativeBlock);
/*      */ 
/* 1840 */       if ((paramAlternativeBlock.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/* 1841 */         println(paramAlternativeBlock.getLabel() + " = " + this.lt1Value + ";");
/*      */       }
/*      */ 
/* 1845 */       genElementAST(paramAlternativeBlock);
/*      */ 
/* 1847 */       String str1 = "";
/* 1848 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1849 */         if (this.usingCustomAST)
/* 1850 */           str1 = "(AST)_t,";
/*      */         else {
/* 1852 */           str1 = "_t,";
/*      */         }
/*      */       }
/*      */ 
/* 1856 */       println("match(" + str1 + getBitsetName(markBitsetForGen(((Lookahead)localObject1).fset)) + ");");
/*      */ 
/* 1859 */       if ((this.grammar instanceof TreeWalkerGrammar))
/*      */       {
/* 1861 */         println("_t = _t.getNextSibling();");
/*      */       }
/* 1863 */       return localCSharpBlockFinishingInfo;
/*      */     }
/*      */ 
/* 1867 */     if (paramAlternativeBlock.getAlternatives().size() == 1)
/*      */     {
/* 1869 */       localObject1 = paramAlternativeBlock.getAlternativeAt(0);
/*      */ 
/* 1871 */       if (((Alternative)localObject1).synPred != null)
/*      */       {
/* 1873 */         this.antlrTool.warning("Syntactic predicate superfluous for single alternative", this.grammar.getFilename(), paramAlternativeBlock.getAlternativeAt(0).synPred.getLine(), paramAlternativeBlock.getAlternativeAt(0).synPred.getColumn());
/*      */       }
/*      */ 
/* 1880 */       if (paramBoolean)
/*      */       {
/* 1882 */         if (((Alternative)localObject1).semPred != null)
/*      */         {
/* 1885 */           genSemPred(((Alternative)localObject1).semPred, paramAlternativeBlock.line);
/*      */         }
/* 1887 */         genAlt((Alternative)localObject1, paramAlternativeBlock);
/* 1888 */         return localCSharpBlockFinishingInfo;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1901 */     int m = 0;
/* 1902 */     for (int n = 0; n < paramAlternativeBlock.getAlternatives().size(); n++)
/*      */     {
/* 1904 */       Alternative localAlternative1 = paramAlternativeBlock.getAlternativeAt(n);
/* 1905 */       if (suitableForCaseExpression(localAlternative1))
/* 1906 */         m++;
/*      */     }
/*      */     Object localObject2;
/* 1911 */     if (m >= this.makeSwitchThreshold)
/*      */     {
/* 1914 */       String str2 = lookaheadString(1);
/* 1915 */       j = 1;
/*      */ 
/* 1917 */       if ((this.grammar instanceof TreeWalkerGrammar))
/*      */       {
/* 1919 */         println("if (null == _t)");
/* 1920 */         this.tabs += 1;
/* 1921 */         println("_t = ASTNULL;");
/* 1922 */         this.tabs -= 1;
/*      */       }
/* 1924 */       println("switch ( " + str2 + " )");
/* 1925 */       println("{");
/*      */ 
/* 1928 */       this.blockNestingLevel += 1;
/*      */ 
/* 1930 */       for (i2 = 0; i2 < paramAlternativeBlock.alternatives.size(); i2++)
/*      */       {
/* 1932 */         Alternative localAlternative2 = paramAlternativeBlock.getAlternativeAt(i2);
/*      */ 
/* 1935 */         if (suitableForCaseExpression(localAlternative2))
/*      */         {
/* 1939 */           localObject2 = localAlternative2.cache[1];
/* 1940 */           if ((((Lookahead)localObject2).fset.degree() == 0) && (!((Lookahead)localObject2).containsEpsilon()))
/*      */           {
/* 1942 */             this.antlrTool.warning("Alternate omitted due to empty prediction set", this.grammar.getFilename(), localAlternative2.head.getLine(), localAlternative2.head.getColumn());
/*      */           }
/*      */           else
/*      */           {
/* 1948 */             genCases(((Lookahead)localObject2).fset);
/* 1949 */             println("{");
/* 1950 */             this.tabs += 1;
/* 1951 */             this.blockNestingLevel += 1;
/* 1952 */             genAlt(localAlternative2, paramAlternativeBlock);
/* 1953 */             println("break;");
/* 1954 */             if (this.blockNestingLevel-- == this.saveIndexCreateLevel)
/* 1955 */               this.saveIndexCreateLevel = 0;
/* 1956 */             this.tabs -= 1;
/* 1957 */             println("}");
/*      */           }
/*      */         }
/*      */       }
/* 1960 */       println("default:");
/* 1961 */       this.tabs += 1;
/*      */     }
/*      */ 
/* 1977 */     int i1 = (this.grammar instanceof LexerGrammar) ? this.grammar.maxk : 0;
/* 1978 */     for (int i2 = i1; i2 >= 0; i2--) {
/* 1979 */       if (this.DEBUG_CODE_GENERATOR) System.out.println("checking depth " + i2);
/* 1980 */       for (i3 = 0; i3 < paramAlternativeBlock.alternatives.size(); i3++) {
/* 1981 */         localObject2 = paramAlternativeBlock.getAlternativeAt(i3);
/* 1982 */         if (this.DEBUG_CODE_GENERATOR) System.out.println("genAlt: " + i3);
/*      */ 
/* 1987 */         if ((j != 0) && (suitableForCaseExpression((Alternative)localObject2)))
/*      */         {
/* 1989 */           if (this.DEBUG_CODE_GENERATOR) System.out.println("ignoring alt because it was in the switch");
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1994 */           boolean bool3 = false;
/*      */           String str4;
/* 1996 */           if ((this.grammar instanceof LexerGrammar))
/*      */           {
/* 2000 */             int i4 = ((Alternative)localObject2).lookaheadDepth;
/* 2001 */             if (i4 == 2147483647)
/*      */             {
/* 2004 */               i4 = this.grammar.maxk;
/*      */             }
/* 2006 */             while ((i4 >= 1) && (localObject2.cache[i4].containsEpsilon()))
/*      */             {
/* 2009 */               i4--;
/*      */             }
/*      */ 
/* 2013 */             if (i4 != i2)
/*      */             {
/* 2015 */               if (!this.DEBUG_CODE_GENERATOR) continue;
/* 2016 */               System.out.println("ignoring alt because effectiveDepth!=altDepth;" + i4 + "!=" + i2); continue;
/*      */             }
/*      */ 
/* 2019 */             bool3 = lookaheadIsEmpty((Alternative)localObject2, i4);
/* 2020 */             str4 = getLookaheadTestExpression((Alternative)localObject2, i4);
/*      */           }
/*      */           else
/*      */           {
/* 2024 */             bool3 = lookaheadIsEmpty((Alternative)localObject2, this.grammar.maxk);
/* 2025 */             str4 = getLookaheadTestExpression((Alternative)localObject2, this.grammar.maxk);
/*      */           }
/*      */ 
/* 2030 */           if ((localObject2.cache[1].fset.degree() > 127) && (suitableForCaseExpression((Alternative)localObject2)))
/*      */           {
/* 2033 */             if (i == 0)
/*      */             {
/* 2035 */               println("if " + str4);
/* 2036 */               println("{");
/*      */             }
/*      */             else {
/* 2039 */               println("else if " + str4);
/* 2040 */               println("{");
/*      */             }
/*      */           }
/* 2043 */           else if ((bool3) && (((Alternative)localObject2).semPred == null) && (((Alternative)localObject2).synPred == null))
/*      */           {
/* 2051 */             if (i == 0) {
/* 2052 */               println("{");
/*      */             }
/*      */             else {
/* 2055 */               println("else {");
/*      */             }
/* 2057 */             localCSharpBlockFinishingInfo.needAnErrorClause = false;
/*      */           }
/*      */           else
/*      */           {
/* 2063 */             if (((Alternative)localObject2).semPred != null)
/*      */             {
/* 2067 */               ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 2068 */               String str5 = processActionForSpecialSymbols(((Alternative)localObject2).semPred, paramAlternativeBlock.line, this.currentRule, localActionTransInfo);
/*      */ 
/* 2075 */               if ((((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar))) && (this.grammar.debuggingOutput))
/*      */               {
/* 2077 */                 str4 = "(" + str4 + "&& fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEventArgs.PREDICTING," + addSemPred(this.charFormatter.escapeString(str5)) + "," + str5 + "))";
/*      */               }
/*      */               else
/*      */               {
/* 2081 */                 str4 = "(" + str4 + "&&(" + str5 + "))";
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/* 2086 */             if (i > 0) {
/* 2087 */               if (((Alternative)localObject2).synPred != null) {
/* 2088 */                 println("else {");
/* 2089 */                 this.tabs += 1;
/* 2090 */                 this.blockNestingLevel += 1;
/* 2091 */                 genSynPred(((Alternative)localObject2).synPred, str4);
/* 2092 */                 k++;
/*      */               }
/*      */               else {
/* 2095 */                 println("else if " + str4 + " {");
/*      */               }
/*      */ 
/*      */             }
/* 2099 */             else if (((Alternative)localObject2).synPred != null) {
/* 2100 */               genSynPred(((Alternative)localObject2).synPred, str4);
/*      */             }
/*      */             else
/*      */             {
/* 2105 */               if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2106 */                 println("if (_t == null)");
/* 2107 */                 this.tabs += 1;
/* 2108 */                 println("_t = ASTNULL;");
/* 2109 */                 this.tabs -= 1;
/*      */               }
/* 2111 */               println("if " + str4);
/* 2112 */               println("{");
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 2117 */           this.blockNestingLevel += 1;
/*      */ 
/* 2119 */           i++;
/* 2120 */           this.tabs += 1;
/* 2121 */           genAlt((Alternative)localObject2, paramAlternativeBlock);
/* 2122 */           this.tabs -= 1;
/*      */ 
/* 2124 */           if (this.blockNestingLevel-- == this.saveIndexCreateLevel)
/* 2125 */             this.saveIndexCreateLevel = 0;
/* 2126 */           println("}");
/*      */         }
/*      */       }
/*      */     }
/* 2130 */     String str3 = "";
/* 2131 */     for (int i3 = 1; i3 <= k; i3++) {
/* 2132 */       str3 = str3 + "}";
/* 2133 */       if (this.blockNestingLevel-- == this.saveIndexCreateLevel) {
/* 2134 */         this.saveIndexCreateLevel = 0;
/*      */       }
/*      */     }
/*      */ 
/* 2138 */     this.genAST = bool1;
/*      */ 
/* 2141 */     this.saveText = bool2;
/*      */ 
/* 2144 */     if (j != 0) {
/* 2145 */       this.tabs -= 1;
/* 2146 */       localCSharpBlockFinishingInfo.postscript = (str3 + "break; }");
/* 2147 */       if (this.blockNestingLevel-- == this.saveIndexCreateLevel)
/* 2148 */         this.saveIndexCreateLevel = 0;
/* 2149 */       localCSharpBlockFinishingInfo.generatedSwitch = true;
/* 2150 */       localCSharpBlockFinishingInfo.generatedAnIf = (i > 0);
/*      */     }
/*      */     else
/*      */     {
/* 2155 */       localCSharpBlockFinishingInfo.postscript = str3;
/* 2156 */       localCSharpBlockFinishingInfo.generatedSwitch = false;
/* 2157 */       localCSharpBlockFinishingInfo.generatedAnIf = (i > 0);
/*      */     }
/*      */ 
/* 2160 */     return localCSharpBlockFinishingInfo;
/*      */   }
/*      */ 
/*      */   private static boolean suitableForCaseExpression(Alternative paramAlternative) {
/* 2164 */     return (paramAlternative.lookaheadDepth == 1) && (paramAlternative.semPred == null) && (!paramAlternative.cache[1].containsEpsilon()) && (paramAlternative.cache[1].fset.degree() <= 127);
/*      */   }
/*      */ 
/*      */   private void genElementAST(AlternativeElement paramAlternativeElement)
/*      */   {
/* 2174 */     if (((this.grammar instanceof TreeWalkerGrammar)) && (!this.grammar.buildAST))
/*      */     {
/* 2180 */       if (paramAlternativeElement.getLabel() == null)
/*      */       {
/* 2182 */         String str1 = this.lt1Value;
/*      */ 
/* 2184 */         String str2 = "tmp" + this.astVarNumber + "_AST";
/* 2185 */         this.astVarNumber += 1;
/*      */ 
/* 2187 */         mapTreeVariable(paramAlternativeElement, str2);
/*      */ 
/* 2189 */         println(this.labeledElementASTType + " " + str2 + "_in = " + str1 + ";");
/*      */       }
/* 2191 */       return;
/*      */     }
/*      */ 
/* 2194 */     if ((this.grammar.buildAST) && (this.syntacticPredLevel == 0))
/*      */     {
/* 2196 */       int i = (this.genAST) && ((paramAlternativeElement.getLabel() != null) || (paramAlternativeElement.getAutoGenType() != 3)) ? 1 : 0;
/*      */ 
/* 2204 */       if ((paramAlternativeElement.getAutoGenType() != 3) && ((paramAlternativeElement instanceof TokenRefElement)))
/*      */       {
/* 2206 */         i = 1;
/*      */       }
/* 2208 */       int j = (this.grammar.hasSyntacticPredicate) && (i != 0) ? 1 : 0;
/*      */       String str3;
/*      */       String str4;
/* 2214 */       if (paramAlternativeElement.getLabel() != null)
/*      */       {
/* 2217 */         str3 = paramAlternativeElement.getLabel();
/* 2218 */         str4 = paramAlternativeElement.getLabel();
/*      */       }
/*      */       else
/*      */       {
/* 2223 */         str3 = this.lt1Value;
/*      */ 
/* 2225 */         str4 = "tmp" + this.astVarNumber;
/* 2226 */         this.astVarNumber += 1;
/*      */       }
/*      */ 
/* 2230 */       if (i != 0)
/*      */       {
/* 2233 */         if ((paramAlternativeElement instanceof GrammarAtom))
/*      */         {
/* 2235 */           localObject = (GrammarAtom)paramAlternativeElement;
/* 2236 */           if (((GrammarAtom)localObject).getASTNodeType() != null)
/*      */           {
/* 2238 */             genASTDeclaration(paramAlternativeElement, str4, ((GrammarAtom)localObject).getASTNodeType());
/*      */           }
/*      */           else
/*      */           {
/* 2243 */             genASTDeclaration(paramAlternativeElement, str4, this.labeledElementASTType);
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 2249 */           genASTDeclaration(paramAlternativeElement, str4, this.labeledElementASTType);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2255 */       Object localObject = str4 + "_AST";
/*      */ 
/* 2258 */       mapTreeVariable(paramAlternativeElement, (String)localObject);
/* 2259 */       if ((this.grammar instanceof TreeWalkerGrammar))
/*      */       {
/* 2262 */         println(this.labeledElementASTType + " " + (String)localObject + "_in = null;");
/*      */       }
/*      */ 
/* 2267 */       if ((j == 0) || 
/* 2275 */         (paramAlternativeElement.getLabel() != null))
/*      */       {
/* 2277 */         if ((paramAlternativeElement instanceof GrammarAtom))
/*      */         {
/* 2279 */           println((String)localObject + " = " + getASTCreateString((GrammarAtom)paramAlternativeElement, str3) + ";");
/*      */         }
/*      */         else
/*      */         {
/* 2283 */           println((String)localObject + " = " + getASTCreateString(str3) + ";");
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2288 */       if ((paramAlternativeElement.getLabel() == null) && (i != 0))
/*      */       {
/* 2290 */         str3 = this.lt1Value;
/* 2291 */         if ((paramAlternativeElement instanceof GrammarAtom))
/*      */         {
/* 2293 */           println((String)localObject + " = " + getASTCreateString((GrammarAtom)paramAlternativeElement, str3) + ";");
/*      */         }
/*      */         else
/*      */         {
/* 2297 */           println((String)localObject + " = " + getASTCreateString(str3) + ";");
/*      */         }
/*      */ 
/* 2300 */         if ((this.grammar instanceof TreeWalkerGrammar))
/*      */         {
/* 2303 */           println((String)localObject + "_in = " + str3 + ";");
/*      */         }
/*      */       }
/*      */ 
/* 2307 */       if (this.genAST)
/*      */       {
/* 2309 */         switch (paramAlternativeElement.getAutoGenType())
/*      */         {
/*      */         case 1:
/* 2312 */           if ((this.usingCustomAST) || (((paramAlternativeElement instanceof GrammarAtom)) && (((GrammarAtom)paramAlternativeElement).getASTNodeType() != null)))
/*      */           {
/* 2315 */             println("astFactory.addASTChild(ref currentAST, (AST)" + (String)localObject + ");");
/*      */           }
/* 2317 */           else println("astFactory.addASTChild(ref currentAST, " + (String)localObject + ");");
/* 2318 */           break;
/*      */         case 2:
/* 2320 */           if ((this.usingCustomAST) || (((paramAlternativeElement instanceof GrammarAtom)) && (((GrammarAtom)paramAlternativeElement).getASTNodeType() != null)))
/*      */           {
/* 2323 */             println("astFactory.makeASTRoot(ref currentAST, (AST)" + (String)localObject + ");");
/*      */           }
/* 2325 */           else println("astFactory.makeASTRoot(ref currentAST, " + (String)localObject + ");");
/* 2326 */           break;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2331 */       if (j == 0);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void genErrorCatchForElement(AlternativeElement paramAlternativeElement)
/*      */   {
/* 2344 */     if (paramAlternativeElement.getLabel() == null) return;
/* 2345 */     String str = paramAlternativeElement.enclosingRuleName;
/* 2346 */     if ((this.grammar instanceof LexerGrammar)) {
/* 2347 */       str = CodeGenerator.encodeLexerRuleName(paramAlternativeElement.enclosingRuleName);
/*      */     }
/* 2349 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(str);
/* 2350 */     if (localRuleSymbol == null) {
/* 2351 */       this.antlrTool.panic("Enclosing rule not found!");
/*      */     }
/* 2353 */     ExceptionSpec localExceptionSpec = localRuleSymbol.block.findExceptionSpec(paramAlternativeElement.getLabel());
/* 2354 */     if (localExceptionSpec != null) {
/* 2355 */       this.tabs -= 1;
/* 2356 */       println("}");
/* 2357 */       genErrorHandler(localExceptionSpec);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void genErrorHandler(ExceptionSpec paramExceptionSpec)
/*      */   {
/* 2365 */     for (int i = 0; i < paramExceptionSpec.handlers.size(); i++)
/*      */     {
/* 2367 */       ExceptionHandler localExceptionHandler = (ExceptionHandler)paramExceptionSpec.handlers.elementAt(i);
/*      */ 
/* 2369 */       println("catch (" + localExceptionHandler.exceptionTypeAndName.getText() + ")");
/* 2370 */       println("{");
/* 2371 */       this.tabs += 1;
/* 2372 */       if (this.grammar.hasSyntacticPredicate) {
/* 2373 */         println("if (0 == inputState.guessing)");
/* 2374 */         println("{");
/* 2375 */         this.tabs += 1;
/*      */       }
/*      */ 
/* 2379 */       ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 2380 */       printAction(processActionForSpecialSymbols(localExceptionHandler.action.getText(), localExceptionHandler.action.getLine(), this.currentRule, localActionTransInfo));
/*      */ 
/* 2383 */       if (this.grammar.hasSyntacticPredicate)
/*      */       {
/* 2385 */         this.tabs -= 1;
/* 2386 */         println("}");
/* 2387 */         println("else");
/* 2388 */         println("{");
/* 2389 */         this.tabs += 1;
/*      */ 
/* 2392 */         println("throw;");
/* 2393 */         this.tabs -= 1;
/* 2394 */         println("}");
/*      */       }
/*      */ 
/* 2397 */       this.tabs -= 1;
/* 2398 */       println("}");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void genErrorTryForElement(AlternativeElement paramAlternativeElement) {
/* 2403 */     if (paramAlternativeElement.getLabel() == null) return;
/* 2404 */     String str = paramAlternativeElement.enclosingRuleName;
/* 2405 */     if ((this.grammar instanceof LexerGrammar)) {
/* 2406 */       str = CodeGenerator.encodeLexerRuleName(paramAlternativeElement.enclosingRuleName);
/*      */     }
/* 2408 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(str);
/* 2409 */     if (localRuleSymbol == null) {
/* 2410 */       this.antlrTool.panic("Enclosing rule not found!");
/*      */     }
/* 2412 */     ExceptionSpec localExceptionSpec = localRuleSymbol.block.findExceptionSpec(paramAlternativeElement.getLabel());
/* 2413 */     if (localExceptionSpec != null) {
/* 2414 */       println("try   // for error handling");
/* 2415 */       println("{");
/* 2416 */       this.tabs += 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void genASTDeclaration(AlternativeElement paramAlternativeElement)
/*      */   {
/* 2422 */     genASTDeclaration(paramAlternativeElement, this.labeledElementASTType);
/*      */   }
/*      */ 
/*      */   protected void genASTDeclaration(AlternativeElement paramAlternativeElement, String paramString)
/*      */   {
/* 2427 */     genASTDeclaration(paramAlternativeElement, paramAlternativeElement.getLabel(), paramString);
/*      */   }
/*      */ 
/*      */   protected void genASTDeclaration(AlternativeElement paramAlternativeElement, String paramString1, String paramString2)
/*      */   {
/* 2433 */     if (this.declaredASTVariables.contains(paramAlternativeElement)) {
/* 2434 */       return;
/*      */     }
/*      */ 
/* 2439 */     println(paramString2 + " " + paramString1 + "_AST = null;");
/*      */ 
/* 2442 */     this.declaredASTVariables.put(paramAlternativeElement, paramAlternativeElement);
/*      */   }
/*      */ 
/*      */   protected void genHeader()
/*      */   {
/* 2448 */     println("// $ANTLR " + Tool.version + ": " + "\"" + this.antlrTool.fileMinusPath(this.antlrTool.grammarFile) + "\"" + " -> " + "\"" + this.grammar.getClassName() + ".cs\"$");
/*      */   }
/*      */ 
/*      */   private void genLiteralsTest()
/*      */   {
/* 2455 */     println("_ttype = testLiteralsTable(_ttype);");
/*      */   }
/*      */ 
/*      */   private void genLiteralsTestForPartialToken() {
/* 2459 */     println("_ttype = testLiteralsTable(text.ToString(_begin, text.Length-_begin), _ttype);");
/*      */   }
/*      */ 
/*      */   protected void genMatch(BitSet paramBitSet) {
/*      */   }
/*      */ 
/*      */   protected void genMatch(GrammarAtom paramGrammarAtom) {
/* 2466 */     if ((paramGrammarAtom instanceof StringLiteralElement)) {
/* 2467 */       if ((this.grammar instanceof LexerGrammar)) {
/* 2468 */         genMatchUsingAtomText(paramGrammarAtom);
/*      */       }
/*      */       else {
/* 2471 */         genMatchUsingAtomTokenType(paramGrammarAtom);
/*      */       }
/*      */     }
/* 2474 */     else if ((paramGrammarAtom instanceof CharLiteralElement)) {
/* 2475 */       if ((this.grammar instanceof LexerGrammar)) {
/* 2476 */         genMatchUsingAtomText(paramGrammarAtom);
/*      */       }
/*      */       else {
/* 2479 */         this.antlrTool.error("cannot ref character literals in grammar: " + paramGrammarAtom);
/*      */       }
/*      */     }
/* 2482 */     else if ((paramGrammarAtom instanceof TokenRefElement))
/* 2483 */       genMatchUsingAtomText(paramGrammarAtom);
/* 2484 */     else if ((paramGrammarAtom instanceof WildcardElement))
/* 2485 */       gen((WildcardElement)paramGrammarAtom);
/*      */   }
/*      */ 
/*      */   protected void genMatchUsingAtomText(GrammarAtom paramGrammarAtom)
/*      */   {
/* 2490 */     String str = "";
/* 2491 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2492 */       if (this.usingCustomAST)
/* 2493 */         str = "(AST)_t,";
/*      */       else {
/* 2495 */         str = "_t,";
/*      */       }
/*      */     }
/*      */ 
/* 2499 */     if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramGrammarAtom.getAutoGenType() == 3))) {
/* 2500 */       declareSaveIndexVariableIfNeeded();
/* 2501 */       println("_saveIndex = text.Length;");
/*      */     }
/*      */ 
/* 2504 */     print(paramGrammarAtom.not ? "matchNot(" : "match(");
/* 2505 */     _print(str);
/*      */ 
/* 2508 */     if (paramGrammarAtom.atomText.equals("EOF"))
/*      */     {
/* 2510 */       _print("Token.EOF_TYPE");
/*      */     }
/*      */     else {
/* 2513 */       _print(paramGrammarAtom.atomText);
/*      */     }
/* 2515 */     _println(");");
/*      */ 
/* 2517 */     if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramGrammarAtom.getAutoGenType() == 3))) {
/* 2518 */       declareSaveIndexVariableIfNeeded();
/* 2519 */       println("text.Length = _saveIndex;");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void genMatchUsingAtomTokenType(GrammarAtom paramGrammarAtom)
/*      */   {
/* 2525 */     String str1 = "";
/* 2526 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2527 */       if (this.usingCustomAST)
/* 2528 */         str1 = "(AST)_t,";
/*      */       else {
/* 2530 */         str1 = "_t,";
/*      */       }
/*      */     }
/*      */ 
/* 2534 */     Object localObject = null;
/* 2535 */     String str2 = str1 + getValueString(paramGrammarAtom.getType());
/*      */ 
/* 2538 */     println((paramGrammarAtom.not ? "matchNot(" : "match(") + str2 + ");");
/*      */   }
/*      */ 
/*      */   public void genNextToken()
/*      */   {
/* 2548 */     int i = 0;
/* 2549 */     for (int j = 0; j < this.grammar.rules.size(); j++) {
/* 2550 */       localRuleSymbol1 = (RuleSymbol)this.grammar.rules.elementAt(j);
/* 2551 */       if ((localRuleSymbol1.isDefined()) && (localRuleSymbol1.access.equals("public"))) {
/* 2552 */         i = 1;
/* 2553 */         break;
/*      */       }
/*      */     }
/* 2556 */     if (i == 0) {
/* 2557 */       println("");
/* 2558 */       println("override public IToken nextToken()\t\t\t//throws TokenStreamException");
/* 2559 */       println("{");
/* 2560 */       this.tabs += 1;
/* 2561 */       println("try");
/* 2562 */       println("{");
/* 2563 */       this.tabs += 1;
/* 2564 */       println("uponEOF();");
/* 2565 */       this.tabs -= 1;
/* 2566 */       println("}");
/* 2567 */       println("catch(CharStreamIOException csioe)");
/* 2568 */       println("{");
/* 2569 */       this.tabs += 1;
/* 2570 */       println("throw new TokenStreamIOException(csioe.io);");
/* 2571 */       this.tabs -= 1;
/* 2572 */       println("}");
/* 2573 */       println("catch(CharStreamException cse)");
/* 2574 */       println("{");
/* 2575 */       this.tabs += 1;
/* 2576 */       println("throw new TokenStreamException(cse.Message);");
/* 2577 */       this.tabs -= 1;
/* 2578 */       println("}");
/* 2579 */       println("return new CommonToken(Token.EOF_TYPE, \"\");");
/* 2580 */       this.tabs -= 1;
/* 2581 */       println("}");
/* 2582 */       println("");
/* 2583 */       return;
/*      */     }
/*      */ 
/* 2587 */     RuleBlock localRuleBlock = MakeGrammar.createNextTokenRule(this.grammar, this.grammar.rules, "nextToken");
/*      */ 
/* 2589 */     RuleSymbol localRuleSymbol1 = new RuleSymbol("mnextToken");
/* 2590 */     localRuleSymbol1.setDefined();
/* 2591 */     localRuleSymbol1.setBlock(localRuleBlock);
/* 2592 */     localRuleSymbol1.access = "private";
/* 2593 */     this.grammar.define(localRuleSymbol1);
/*      */ 
/* 2595 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(localRuleBlock);
/*      */ 
/* 2598 */     String str1 = null;
/* 2599 */     if (((LexerGrammar)this.grammar).filterMode) {
/* 2600 */       str1 = ((LexerGrammar)this.grammar).filterRule;
/*      */     }
/*      */ 
/* 2603 */     println("");
/* 2604 */     println("override public IToken nextToken()\t\t\t//throws TokenStreamException");
/* 2605 */     println("{");
/* 2606 */     this.tabs += 1;
/*      */ 
/* 2608 */     this.blockNestingLevel = 1;
/* 2609 */     this.saveIndexCreateLevel = 0;
/* 2610 */     println("IToken theRetToken = null;");
/* 2611 */     _println("tryAgain:");
/* 2612 */     println("for (;;)");
/* 2613 */     println("{");
/* 2614 */     this.tabs += 1;
/* 2615 */     println("IToken _token = null;");
/* 2616 */     println("int _ttype = Token.INVALID_TYPE;");
/* 2617 */     if (((LexerGrammar)this.grammar).filterMode) {
/* 2618 */       println("setCommitToPath(false);");
/* 2619 */       if (str1 != null)
/*      */       {
/* 2621 */         if (!this.grammar.isDefined(CodeGenerator.encodeLexerRuleName(str1))) {
/* 2622 */           this.grammar.antlrTool.error("Filter rule " + str1 + " does not exist in this lexer");
/*      */         }
/*      */         else {
/* 2625 */           RuleSymbol localRuleSymbol2 = (RuleSymbol)this.grammar.getSymbol(CodeGenerator.encodeLexerRuleName(str1));
/* 2626 */           if (!localRuleSymbol2.isDefined()) {
/* 2627 */             this.grammar.antlrTool.error("Filter rule " + str1 + " does not exist in this lexer");
/*      */           }
/* 2629 */           else if (localRuleSymbol2.access.equals("public")) {
/* 2630 */             this.grammar.antlrTool.error("Filter rule " + str1 + " must be protected");
/*      */           }
/*      */         }
/* 2633 */         println("int _m;");
/* 2634 */         println("_m = mark();");
/*      */       }
/*      */     }
/* 2637 */     println("resetText();");
/*      */ 
/* 2639 */     println("try     // for char stream error handling");
/* 2640 */     println("{");
/* 2641 */     this.tabs += 1;
/*      */ 
/* 2644 */     println("try     // for lexical error handling");
/* 2645 */     println("{");
/* 2646 */     this.tabs += 1;
/*      */ 
/* 2649 */     for (int k = 0; k < localRuleBlock.getAlternatives().size(); k++) {
/* 2650 */       localObject1 = localRuleBlock.getAlternativeAt(k);
/* 2651 */       if (localObject1.cache[1].containsEpsilon())
/*      */       {
/* 2653 */         localObject2 = (RuleRefElement)((Alternative)localObject1).head;
/* 2654 */         String str3 = CodeGenerator.decodeLexerRuleName(((RuleRefElement)localObject2).targetRule);
/* 2655 */         this.antlrTool.warning("public lexical rule " + str3 + " is optional (can match \"nothing\")");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2660 */     String str2 = System.getProperty("line.separator");
/* 2661 */     Object localObject1 = genCommonBlock(localRuleBlock, false);
/* 2662 */     Object localObject2 = "if (cached_LA1==EOF_CHAR) { uponEOF(); returnToken_ = makeToken(Token.EOF_TYPE); }";
/*      */ 
/* 2664 */     localObject2 = (String)localObject2 + str2 + "\t\t\t\t";
/* 2665 */     if (((LexerGrammar)this.grammar).filterMode) {
/* 2666 */       if (str1 == null)
/*      */       {
/* 2668 */         localObject2 = (String)localObject2 + "\t\t\t\telse";
/* 2669 */         localObject2 = (String)localObject2 + "\t\t\t\t{";
/* 2670 */         localObject2 = (String)localObject2 + "\t\t\t\t\tconsume();";
/* 2671 */         localObject2 = (String)localObject2 + "\t\t\t\t\tgoto tryAgain;";
/* 2672 */         localObject2 = (String)localObject2 + "\t\t\t\t}";
/*      */       }
/*      */       else {
/* 2675 */         localObject2 = (String)localObject2 + "\t\t\t\t\telse" + str2 + "\t\t\t\t\t{" + str2 + "\t\t\t\t\tcommit();" + str2 + "\t\t\t\t\ttry {m" + str1 + "(false);}" + str2 + "\t\t\t\t\tcatch(RecognitionException e)" + str2 + "\t\t\t\t\t{" + str2 + "\t\t\t\t\t\t// catastrophic failure" + str2 + "\t\t\t\t\t\treportError(e);" + str2 + "\t\t\t\t\t\tconsume();" + str2 + "\t\t\t\t\t}" + str2 + "\t\t\t\t\tgoto tryAgain;" + str2 + "\t\t\t\t}";
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 2690 */       localObject2 = (String)localObject2 + "else {" + this.throwNoViable + "}";
/*      */     }
/* 2692 */     genBlockFinish((CSharpBlockFinishingInfo)localObject1, (String)localObject2);
/*      */ 
/* 2695 */     if ((((LexerGrammar)this.grammar).filterMode) && (str1 != null)) {
/* 2696 */       println("commit();");
/*      */     }
/*      */ 
/* 2702 */     println("if ( null==returnToken_ ) goto tryAgain; // found SKIP token");
/* 2703 */     println("_ttype = returnToken_.Type;");
/* 2704 */     if (((LexerGrammar)this.grammar).getTestLiterals()) {
/* 2705 */       genLiteralsTest();
/*      */     }
/*      */ 
/* 2709 */     println("returnToken_.Type = _ttype;");
/* 2710 */     println("return returnToken_;");
/*      */ 
/* 2713 */     this.tabs -= 1;
/* 2714 */     println("}");
/* 2715 */     println("catch (RecognitionException e) {");
/* 2716 */     this.tabs += 1;
/* 2717 */     if (((LexerGrammar)this.grammar).filterMode) {
/* 2718 */       if (str1 == null) {
/* 2719 */         println("if (!getCommitToPath())");
/* 2720 */         println("{");
/* 2721 */         this.tabs += 1;
/* 2722 */         println("consume();");
/* 2723 */         println("goto tryAgain;");
/* 2724 */         this.tabs -= 1;
/* 2725 */         println("}");
/*      */       }
/*      */       else {
/* 2728 */         println("if (!getCommitToPath())");
/* 2729 */         println("{");
/* 2730 */         this.tabs += 1;
/* 2731 */         println("rewind(_m);");
/* 2732 */         println("resetText();");
/* 2733 */         println("try {m" + str1 + "(false);}");
/* 2734 */         println("catch(RecognitionException ee) {");
/* 2735 */         println("\t// horrendous failure: error in filter rule");
/* 2736 */         println("\treportError(ee);");
/* 2737 */         println("\tconsume();");
/* 2738 */         println("}");
/*      */ 
/* 2740 */         this.tabs -= 1;
/* 2741 */         println("}");
/* 2742 */         println("else");
/*      */       }
/*      */     }
/* 2745 */     if (localRuleBlock.getDefaultErrorHandler()) {
/* 2746 */       println("{");
/* 2747 */       this.tabs += 1;
/* 2748 */       println("reportError(e);");
/* 2749 */       println("consume();");
/* 2750 */       this.tabs -= 1;
/* 2751 */       println("}");
/*      */     }
/*      */     else
/*      */     {
/* 2755 */       this.tabs += 1;
/* 2756 */       println("throw new TokenStreamRecognitionException(e);");
/* 2757 */       this.tabs -= 1;
/*      */     }
/* 2759 */     this.tabs -= 1;
/* 2760 */     println("}");
/*      */ 
/* 2763 */     this.tabs -= 1;
/* 2764 */     println("}");
/* 2765 */     println("catch (CharStreamException cse) {");
/* 2766 */     println("\tif ( cse is CharStreamIOException ) {");
/* 2767 */     println("\t\tthrow new TokenStreamIOException(((CharStreamIOException)cse).io);");
/* 2768 */     println("\t}");
/* 2769 */     println("\telse {");
/* 2770 */     println("\t\tthrow new TokenStreamException(cse.Message);");
/* 2771 */     println("\t}");
/* 2772 */     println("}");
/*      */ 
/* 2775 */     this.tabs -= 1;
/* 2776 */     println("}");
/*      */ 
/* 2779 */     this.tabs -= 1;
/* 2780 */     println("}");
/* 2781 */     println("");
/*      */   }
/*      */ 
/*      */   public void genRule(RuleSymbol paramRuleSymbol, boolean paramBoolean, int paramInt, TokenManager paramTokenManager)
/*      */   {
/* 2800 */     this.tabs = 1;
/* 2801 */     if (this.DEBUG_CODE_GENERATOR) System.out.println("genRule(" + paramRuleSymbol.getId() + ")");
/* 2802 */     if (!paramRuleSymbol.isDefined()) {
/* 2803 */       this.antlrTool.error("undefined rule: " + paramRuleSymbol.getId());
/* 2804 */       return;
/*      */     }
/*      */ 
/* 2808 */     RuleBlock localRuleBlock = paramRuleSymbol.getBlock();
/* 2809 */     this.currentRule = localRuleBlock;
/* 2810 */     this.currentASTResult = paramRuleSymbol.getId();
/*      */ 
/* 2813 */     this.declaredASTVariables.clear();
/*      */ 
/* 2816 */     boolean bool1 = this.genAST;
/* 2817 */     this.genAST = ((this.genAST) && (localRuleBlock.getAutoGen()));
/*      */ 
/* 2820 */     this.saveText = localRuleBlock.getAutoGen();
/*      */ 
/* 2823 */     if (paramRuleSymbol.comment != null) {
/* 2824 */       _println(paramRuleSymbol.comment);
/*      */     }
/*      */ 
/* 2829 */     print(paramRuleSymbol.access + " ");
/*      */ 
/* 2832 */     if (localRuleBlock.returnAction != null)
/*      */     {
/* 2835 */       _print(extractTypeOfAction(localRuleBlock.returnAction, localRuleBlock.getLine(), localRuleBlock.getColumn()) + " ");
/*      */     }
/*      */     else {
/* 2838 */       _print("void ");
/*      */     }
/*      */ 
/* 2842 */     _print(paramRuleSymbol.getId() + "(");
/*      */ 
/* 2845 */     _print(this.commonExtraParams);
/* 2846 */     if ((this.commonExtraParams.length() != 0) && (localRuleBlock.argAction != null)) {
/* 2847 */       _print(",");
/*      */     }
/*      */ 
/* 2851 */     if (localRuleBlock.argAction != null)
/*      */     {
/* 2854 */       _println("");
/* 2855 */       this.tabs += 1;
/* 2856 */       println(localRuleBlock.argAction);
/* 2857 */       this.tabs -= 1;
/* 2858 */       print(")");
/*      */     }
/*      */     else
/*      */     {
/* 2862 */       _print(")");
/*      */     }
/*      */ 
/* 2866 */     _print(" //throws " + this.exceptionThrown);
/* 2867 */     if ((this.grammar instanceof ParserGrammar)) {
/* 2868 */       _print(", TokenStreamException");
/*      */     }
/* 2870 */     else if ((this.grammar instanceof LexerGrammar)) {
/* 2871 */       _print(", CharStreamException, TokenStreamException");
/*      */     }
/*      */ 
/* 2874 */     if (localRuleBlock.throwsSpec != null) {
/* 2875 */       if ((this.grammar instanceof LexerGrammar)) {
/* 2876 */         this.antlrTool.error("user-defined throws spec not allowed (yet) for lexer rule " + localRuleBlock.ruleName);
/*      */       }
/*      */       else {
/* 2879 */         _print(", " + localRuleBlock.throwsSpec);
/*      */       }
/*      */     }
/*      */ 
/* 2883 */     _println("");
/* 2884 */     _println("{");
/* 2885 */     this.tabs += 1;
/*      */ 
/* 2888 */     if (localRuleBlock.returnAction != null) {
/* 2889 */       println(localRuleBlock.returnAction + ";");
/*      */     }
/*      */ 
/* 2892 */     println(this.commonLocalVars);
/*      */ 
/* 2894 */     if (this.grammar.traceRules) {
/* 2895 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2896 */         if (this.usingCustomAST)
/* 2897 */           println("traceIn(\"" + paramRuleSymbol.getId() + "\",(AST)_t);");
/*      */         else
/* 2899 */           println("traceIn(\"" + paramRuleSymbol.getId() + "\",_t);");
/*      */       }
/*      */       else {
/* 2902 */         println("traceIn(\"" + paramRuleSymbol.getId() + "\");");
/*      */       }
/*      */     }
/*      */ 
/* 2906 */     if ((this.grammar instanceof LexerGrammar))
/*      */     {
/* 2909 */       if (paramRuleSymbol.getId().equals("mEOF"))
/* 2910 */         println("_ttype = Token.EOF_TYPE;");
/*      */       else {
/* 2912 */         println("_ttype = " + paramRuleSymbol.getId().substring(1) + ";");
/*      */       }
/*      */ 
/* 2915 */       this.blockNestingLevel = 1;
/* 2916 */       this.saveIndexCreateLevel = 0;
/*      */     }
/*      */ 
/* 2927 */     if (this.grammar.debuggingOutput) {
/* 2928 */       if ((this.grammar instanceof ParserGrammar))
/* 2929 */         println("fireEnterRule(" + paramInt + ",0);");
/* 2930 */       else if ((this.grammar instanceof LexerGrammar)) {
/* 2931 */         println("fireEnterRule(" + paramInt + ",_ttype);");
/*      */       }
/*      */     }
/*      */ 
/* 2935 */     if ((this.grammar.debuggingOutput) || (this.grammar.traceRules)) {
/* 2936 */       println("try { // debugging");
/* 2937 */       this.tabs += 1;
/*      */     }
/*      */ 
/* 2941 */     if ((this.grammar instanceof TreeWalkerGrammar))
/*      */     {
/* 2943 */       println(this.labeledElementASTType + " " + paramRuleSymbol.getId() + "_AST_in = (" + this.labeledElementASTType + ")_t;");
/*      */     }
/* 2945 */     if (this.grammar.buildAST)
/*      */     {
/* 2947 */       println("returnAST = null;");
/*      */ 
/* 2950 */       println("ASTPair currentAST = new ASTPair();");
/*      */ 
/* 2952 */       println(this.labeledElementASTType + " " + paramRuleSymbol.getId() + "_AST = null;");
/*      */     }
/*      */ 
/* 2955 */     genBlockPreamble(localRuleBlock);
/* 2956 */     genBlockInitAction(localRuleBlock);
/* 2957 */     println("");
/*      */ 
/* 2960 */     ExceptionSpec localExceptionSpec = localRuleBlock.findExceptionSpec("");
/*      */ 
/* 2963 */     if ((localExceptionSpec != null) || (localRuleBlock.getDefaultErrorHandler())) {
/* 2964 */       println("try {      // for error handling");
/* 2965 */       this.tabs += 1;
/*      */     }
/*      */     Object localObject;
/* 2969 */     if (localRuleBlock.alternatives.size() == 1)
/*      */     {
/* 2972 */       Alternative localAlternative = localRuleBlock.getAlternativeAt(0);
/* 2973 */       localObject = localAlternative.semPred;
/* 2974 */       if (localObject != null)
/* 2975 */         genSemPred((String)localObject, this.currentRule.line);
/* 2976 */       if (localAlternative.synPred != null) {
/* 2977 */         this.antlrTool.warning("Syntactic predicate ignored for single alternative", this.grammar.getFilename(), localAlternative.synPred.getLine(), localAlternative.synPred.getColumn());
/*      */       }
/*      */ 
/* 2982 */       genAlt(localAlternative, localRuleBlock);
/*      */     }
/*      */     else
/*      */     {
/* 2987 */       boolean bool2 = this.grammar.theLLkAnalyzer.deterministic(localRuleBlock);
/*      */ 
/* 2989 */       localObject = genCommonBlock(localRuleBlock, false);
/* 2990 */       genBlockFinish((CSharpBlockFinishingInfo)localObject, this.throwNoViable);
/*      */     }
/*      */ 
/* 2994 */     if ((localExceptionSpec != null) || (localRuleBlock.getDefaultErrorHandler()))
/*      */     {
/* 2996 */       this.tabs -= 1;
/* 2997 */       println("}");
/*      */     }
/*      */ 
/* 3001 */     if (localExceptionSpec != null)
/*      */     {
/* 3003 */       genErrorHandler(localExceptionSpec);
/*      */     }
/* 3005 */     else if (localRuleBlock.getDefaultErrorHandler())
/*      */     {
/* 3008 */       println("catch (" + this.exceptionThrown + " ex)");
/* 3009 */       println("{");
/* 3010 */       this.tabs += 1;
/*      */ 
/* 3012 */       if (this.grammar.hasSyntacticPredicate) {
/* 3013 */         println("if (0 == inputState.guessing)");
/* 3014 */         println("{");
/* 3015 */         this.tabs += 1;
/*      */       }
/* 3017 */       println("reportError(ex);");
/* 3018 */       if (!(this.grammar instanceof TreeWalkerGrammar))
/*      */       {
/* 3021 */         Lookahead localLookahead = this.grammar.theLLkAnalyzer.FOLLOW(1, localRuleBlock.endNode);
/* 3022 */         localObject = getBitsetName(markBitsetForGen(localLookahead.fset));
/* 3023 */         println("recover(ex," + (String)localObject + ");");
/*      */       }
/*      */       else
/*      */       {
/* 3028 */         println("if (null != _t)");
/* 3029 */         println("{");
/* 3030 */         this.tabs += 1;
/* 3031 */         println("_t = _t.getNextSibling();");
/* 3032 */         this.tabs -= 1;
/* 3033 */         println("}");
/*      */       }
/* 3035 */       if (this.grammar.hasSyntacticPredicate)
/*      */       {
/* 3037 */         this.tabs -= 1;
/*      */ 
/* 3039 */         println("}");
/* 3040 */         println("else");
/* 3041 */         println("{");
/* 3042 */         this.tabs += 1;
/* 3043 */         println("throw ex;");
/* 3044 */         this.tabs -= 1;
/* 3045 */         println("}");
/*      */       }
/*      */ 
/* 3048 */       this.tabs -= 1;
/* 3049 */       println("}");
/*      */     }
/*      */ 
/* 3053 */     if (this.grammar.buildAST) {
/* 3054 */       println("returnAST = " + paramRuleSymbol.getId() + "_AST;");
/*      */     }
/*      */ 
/* 3058 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 3059 */       println("retTree_ = _t;");
/*      */     }
/*      */ 
/* 3063 */     if (localRuleBlock.getTestLiterals()) {
/* 3064 */       if (paramRuleSymbol.access.equals("protected")) {
/* 3065 */         genLiteralsTestForPartialToken();
/*      */       }
/*      */       else {
/* 3068 */         genLiteralsTest();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3073 */     if ((this.grammar instanceof LexerGrammar)) {
/* 3074 */       println("if (_createToken && (null == _token) && (_ttype != Token.SKIP))");
/* 3075 */       println("{");
/* 3076 */       this.tabs += 1;
/* 3077 */       println("_token = makeToken(_ttype);");
/* 3078 */       println("_token.setText(text.ToString(_begin, text.Length-_begin));");
/* 3079 */       this.tabs -= 1;
/* 3080 */       println("}");
/* 3081 */       println("returnToken_ = _token;");
/*      */     }
/*      */ 
/* 3085 */     if (localRuleBlock.returnAction != null) {
/* 3086 */       println("return " + extractIdOfAction(localRuleBlock.returnAction, localRuleBlock.getLine(), localRuleBlock.getColumn()) + ";");
/*      */     }
/*      */ 
/* 3089 */     if ((this.grammar.debuggingOutput) || (this.grammar.traceRules)) {
/* 3090 */       this.tabs -= 1;
/* 3091 */       println("}");
/* 3092 */       println("finally");
/* 3093 */       println("{ // debugging");
/* 3094 */       this.tabs += 1;
/*      */ 
/* 3097 */       if (this.grammar.debuggingOutput) {
/* 3098 */         if ((this.grammar instanceof ParserGrammar))
/* 3099 */           println("fireExitRule(" + paramInt + ",0);");
/* 3100 */         else if ((this.grammar instanceof LexerGrammar))
/* 3101 */           println("fireExitRule(" + paramInt + ",_ttype);");
/*      */       }
/* 3103 */       if (this.grammar.traceRules) {
/* 3104 */         if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 3105 */           println("traceOut(\"" + paramRuleSymbol.getId() + "\",_t);");
/*      */         }
/*      */         else {
/* 3108 */           println("traceOut(\"" + paramRuleSymbol.getId() + "\");");
/*      */         }
/*      */       }
/*      */ 
/* 3112 */       this.tabs -= 1;
/* 3113 */       println("}");
/*      */     }
/*      */ 
/* 3116 */     this.tabs -= 1;
/* 3117 */     println("}");
/* 3118 */     println("");
/*      */ 
/* 3121 */     this.genAST = bool1;
/*      */   }
/*      */ 
/*      */   private void GenRuleInvocation(RuleRefElement paramRuleRefElement)
/*      */   {
/* 3128 */     _print(paramRuleRefElement.targetRule + "(");
/*      */ 
/* 3131 */     if ((this.grammar instanceof LexerGrammar))
/*      */     {
/* 3133 */       if (paramRuleRefElement.getLabel() != null) {
/* 3134 */         _print("true");
/*      */       }
/*      */       else {
/* 3137 */         _print("false");
/*      */       }
/* 3139 */       if ((this.commonExtraArgs.length() != 0) || (paramRuleRefElement.args != null)) {
/* 3140 */         _print(",");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3145 */     _print(this.commonExtraArgs);
/* 3146 */     if ((this.commonExtraArgs.length() != 0) && (paramRuleRefElement.args != null)) {
/* 3147 */       _print(",");
/*      */     }
/*      */ 
/* 3151 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(paramRuleRefElement.targetRule);
/* 3152 */     if (paramRuleRefElement.args != null)
/*      */     {
/* 3155 */       ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 3156 */       String str = processActionForSpecialSymbols(paramRuleRefElement.args, 0, this.currentRule, localActionTransInfo);
/* 3157 */       if ((localActionTransInfo.assignToRoot) || (localActionTransInfo.refRuleRoot != null))
/*      */       {
/* 3159 */         this.antlrTool.error("Arguments of rule reference '" + paramRuleRefElement.targetRule + "' cannot set or ref #" + this.currentRule.getRuleName(), this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*      */       }
/*      */ 
/* 3162 */       _print(str);
/*      */ 
/* 3165 */       if (localRuleSymbol.block.argAction == null)
/*      */       {
/* 3167 */         this.antlrTool.warning("Rule '" + paramRuleRefElement.targetRule + "' accepts no arguments", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*      */       }
/*      */ 
/*      */     }
/* 3174 */     else if (localRuleSymbol.block.argAction != null)
/*      */     {
/* 3176 */       this.antlrTool.warning("Missing parameters on reference to rule " + paramRuleRefElement.targetRule, this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*      */     }
/*      */ 
/* 3179 */     _println(");");
/*      */ 
/* 3182 */     if ((this.grammar instanceof TreeWalkerGrammar))
/* 3183 */       println("_t = retTree_;");
/*      */   }
/*      */ 
/*      */   protected void genSemPred(String paramString, int paramInt)
/*      */   {
/* 3188 */     ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 3189 */     paramString = processActionForSpecialSymbols(paramString, paramInt, this.currentRule, localActionTransInfo);
/*      */ 
/* 3191 */     String str = this.charFormatter.escapeString(paramString);
/*      */ 
/* 3195 */     if ((this.grammar.debuggingOutput) && (((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar)))) {
/* 3196 */       paramString = "fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEvent.VALIDATING," + addSemPred(str) + "," + paramString + ")";
/*      */     }
/* 3198 */     println("if (!(" + paramString + "))");
/* 3199 */     println("  throw new SemanticException(\"" + str + "\");");
/*      */   }
/*      */ 
/*      */   protected void genSemPredMap()
/*      */   {
/* 3205 */     Enumeration localEnumeration = this.semPreds.elements();
/* 3206 */     println("private string[] _semPredNames = {");
/* 3207 */     this.tabs += 1;
/* 3208 */     while (localEnumeration.hasMoreElements())
/* 3209 */       println("\"" + localEnumeration.nextElement() + "\",");
/* 3210 */     this.tabs -= 1;
/* 3211 */     println("};");
/*      */   }
/*      */   protected void genSynPred(SynPredBlock paramSynPredBlock, String paramString) {
/* 3214 */     if (this.DEBUG_CODE_GENERATOR) System.out.println("gen=>(" + paramSynPredBlock + ")");
/*      */ 
/* 3217 */     println("bool synPredMatched" + paramSynPredBlock.ID + " = false;");
/*      */ 
/* 3219 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 3220 */       println("if (_t==null) _t=ASTNULL;");
/*      */     }
/*      */ 
/* 3223 */     println("if (" + paramString + ")");
/* 3224 */     println("{");
/* 3225 */     this.tabs += 1;
/*      */ 
/* 3228 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 3229 */       println("AST __t" + paramSynPredBlock.ID + " = _t;");
/*      */     }
/*      */     else {
/* 3232 */       println("int _m" + paramSynPredBlock.ID + " = mark();");
/*      */     }
/*      */ 
/* 3236 */     println("synPredMatched" + paramSynPredBlock.ID + " = true;");
/* 3237 */     println("inputState.guessing++;");
/*      */ 
/* 3240 */     if ((this.grammar.debuggingOutput) && (((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar))))
/*      */     {
/* 3242 */       println("fireSyntacticPredicateStarted();");
/*      */     }
/*      */ 
/* 3245 */     this.syntacticPredLevel += 1;
/* 3246 */     println("try {");
/* 3247 */     this.tabs += 1;
/* 3248 */     gen(paramSynPredBlock);
/* 3249 */     this.tabs -= 1;
/*      */ 
/* 3251 */     println("}");
/*      */ 
/* 3254 */     println("catch (" + this.exceptionThrown + ")");
/* 3255 */     println("{");
/* 3256 */     this.tabs += 1;
/* 3257 */     println("synPredMatched" + paramSynPredBlock.ID + " = false;");
/*      */ 
/* 3259 */     this.tabs -= 1;
/* 3260 */     println("}");
/*      */ 
/* 3263 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 3264 */       println("_t = __t" + paramSynPredBlock.ID + ";");
/*      */     }
/*      */     else {
/* 3267 */       println("rewind(_m" + paramSynPredBlock.ID + ");");
/*      */     }
/*      */ 
/* 3270 */     println("inputState.guessing--;");
/*      */ 
/* 3273 */     if ((this.grammar.debuggingOutput) && (((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar))))
/*      */     {
/* 3275 */       println("if (synPredMatched" + paramSynPredBlock.ID + ")");
/* 3276 */       println("  fireSyntacticPredicateSucceeded();");
/* 3277 */       println("else");
/* 3278 */       println("  fireSyntacticPredicateFailed();");
/*      */     }
/*      */ 
/* 3281 */     this.syntacticPredLevel -= 1;
/* 3282 */     this.tabs -= 1;
/*      */ 
/* 3285 */     println("}");
/*      */ 
/* 3288 */     println("if ( synPredMatched" + paramSynPredBlock.ID + " )");
/* 3289 */     println("{");
/*      */   }
/*      */ 
/*      */   public void genTokenStrings()
/*      */   {
/* 3302 */     println("");
/* 3303 */     println("public static readonly string[] tokenNames_ = new string[] {");
/* 3304 */     this.tabs += 1;
/*      */ 
/* 3308 */     antlr.collections.impl.Vector localVector = this.grammar.tokenManager.getVocabulary();
/* 3309 */     for (int i = 0; i < localVector.size(); i++)
/*      */     {
/* 3311 */       String str = (String)localVector.elementAt(i);
/* 3312 */       if (str == null)
/*      */       {
/* 3314 */         str = "<" + String.valueOf(i) + ">";
/*      */       }
/* 3316 */       if ((!str.startsWith("\"")) && (!str.startsWith("<"))) {
/* 3317 */         TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol(str);
/* 3318 */         if ((localTokenSymbol != null) && (localTokenSymbol.getParaphrase() != null)) {
/* 3319 */           str = StringUtils.stripFrontBack(localTokenSymbol.getParaphrase(), "\"", "\"");
/*      */         }
/*      */       }
/* 3322 */       else if (str.startsWith("\"")) {
/* 3323 */         str = StringUtils.stripFrontBack(str, "\"", "\"");
/*      */       }
/* 3325 */       print(this.charFormatter.literalString(str));
/* 3326 */       if (i != localVector.size() - 1) {
/* 3327 */         _print(",");
/*      */       }
/* 3329 */       _println("");
/*      */     }
/*      */ 
/* 3333 */     this.tabs -= 1;
/* 3334 */     println("};");
/*      */   }
/*      */ 
/*      */   protected void genTokenTypes(TokenManager paramTokenManager)
/*      */     throws IOException
/*      */   {
/* 3341 */     setupOutput(paramTokenManager.getName() + TokenTypesFileSuffix);
/*      */ 
/* 3343 */     this.tabs = 0;
/*      */ 
/* 3346 */     genHeader();
/*      */ 
/* 3348 */     println(this.behavior.getHeaderAction(""));
/*      */ 
/* 3351 */     if (nameSpace != null)
/* 3352 */       nameSpace.emitDeclarations(this.currentOutput);
/* 3353 */     this.tabs += 1;
/*      */ 
/* 3357 */     println("public class " + paramTokenManager.getName() + TokenTypesFileSuffix);
/*      */ 
/* 3359 */     println("{");
/* 3360 */     this.tabs += 1;
/*      */ 
/* 3362 */     genTokenDefinitions(paramTokenManager);
/*      */ 
/* 3365 */     this.tabs -= 1;
/* 3366 */     println("}");
/*      */ 
/* 3368 */     this.tabs -= 1;
/*      */ 
/* 3370 */     if (nameSpace != null) {
/* 3371 */       nameSpace.emitClosures(this.currentOutput);
/*      */     }
/*      */ 
/* 3374 */     this.currentOutput.close();
/* 3375 */     this.currentOutput = null;
/* 3376 */     exitIfError();
/*      */   }
/*      */ 
/*      */   protected void genTokenDefinitions(TokenManager paramTokenManager) throws IOException {
/* 3380 */     antlr.collections.impl.Vector localVector = paramTokenManager.getVocabulary();
/*      */ 
/* 3383 */     println("public const int EOF = 1;");
/* 3384 */     println("public const int NULL_TREE_LOOKAHEAD = 3;");
/*      */ 
/* 3386 */     for (int i = 4; i < localVector.size(); i++) {
/* 3387 */       String str1 = (String)localVector.elementAt(i);
/* 3388 */       if (str1 != null) {
/* 3389 */         if (str1.startsWith("\""))
/*      */         {
/* 3391 */           StringLiteralSymbol localStringLiteralSymbol = (StringLiteralSymbol)paramTokenManager.getTokenSymbol(str1);
/* 3392 */           if (localStringLiteralSymbol == null) {
/* 3393 */             this.antlrTool.panic("String literal " + str1 + " not in symbol table");
/*      */           }
/* 3395 */           else if (localStringLiteralSymbol.label != null) {
/* 3396 */             println("public const int " + localStringLiteralSymbol.label + " = " + i + ";");
/*      */           }
/*      */           else {
/* 3399 */             String str2 = mangleLiteral(str1);
/* 3400 */             if (str2 != null)
/*      */             {
/* 3402 */               println("public const int " + str2 + " = " + i + ";");
/*      */ 
/* 3404 */               localStringLiteralSymbol.label = str2;
/*      */             }
/*      */             else {
/* 3407 */               println("// " + str1 + " = " + i);
/*      */             }
/*      */           }
/*      */         }
/* 3411 */         else if (!str1.startsWith("<")) {
/* 3412 */           println("public const int " + str1 + " = " + i + ";");
/*      */         }
/*      */       }
/*      */     }
/* 3416 */     println("");
/*      */   }
/*      */ 
/*      */   public String processStringForASTConstructor(String paramString)
/*      */   {
/* 3434 */     if ((this.usingCustomAST) && (((this.grammar instanceof TreeWalkerGrammar)) || ((this.grammar instanceof ParserGrammar))) && (!this.grammar.tokenManager.tokenDefined(paramString)))
/*      */     {
/* 3440 */       return "(AST)" + paramString;
/*      */     }
/*      */ 
/* 3445 */     return paramString;
/*      */   }
/*      */ 
/*      */   public String getASTCreateString(antlr.collections.impl.Vector paramVector)
/*      */   {
/* 3453 */     if (paramVector.size() == 0) {
/* 3454 */       return "";
/*      */     }
/* 3456 */     StringBuffer localStringBuffer = new StringBuffer();
/* 3457 */     localStringBuffer.append("(" + this.labeledElementASTType + ") astFactory.make(");
/*      */ 
/* 3459 */     localStringBuffer.append(paramVector.elementAt(0));
/* 3460 */     for (int i = 1; i < paramVector.size(); i++) {
/* 3461 */       localStringBuffer.append(", " + paramVector.elementAt(i));
/*      */     }
/* 3463 */     localStringBuffer.append(")");
/* 3464 */     return localStringBuffer.toString();
/*      */   }
/*      */ 
/*      */   public String getASTCreateString(GrammarAtom paramGrammarAtom, String paramString)
/*      */   {
/* 3472 */     String str = "astFactory.create(" + paramString + ")";
/*      */ 
/* 3474 */     if (paramGrammarAtom == null) {
/* 3475 */       return getASTCreateString(paramString);
/*      */     }
/* 3477 */     if (paramGrammarAtom.getASTNodeType() != null)
/*      */     {
/* 3485 */       TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol(paramGrammarAtom.getText());
/* 3486 */       if ((localTokenSymbol == null) || (localTokenSymbol.getASTNodeType() != paramGrammarAtom.getASTNodeType()))
/* 3487 */         str = "(" + paramGrammarAtom.getASTNodeType() + ") astFactory.create(" + paramString + ", \"" + paramGrammarAtom.getASTNodeType() + "\")";
/* 3488 */       else if ((localTokenSymbol != null) && (localTokenSymbol.getASTNodeType() != null))
/* 3489 */         str = "(" + localTokenSymbol.getASTNodeType() + ") " + str;
/*      */     }
/* 3491 */     else if (this.usingCustomAST) {
/* 3492 */       str = "(" + this.labeledElementASTType + ") " + str;
/*      */     }
/* 3494 */     return str;
/*      */   }
/*      */ 
/*      */   public String getASTCreateString(String paramString)
/*      */   {
/* 3528 */     if (paramString == null) {
/* 3529 */       paramString = "";
/*      */     }
/* 3531 */     String str1 = "astFactory.create(" + paramString + ")";
/* 3532 */     String str2 = paramString;
/* 3533 */     String str3 = null;
/*      */ 
/* 3535 */     int j = 0;
/*      */ 
/* 3537 */     int i = paramString.indexOf(',');
/* 3538 */     if (i != -1) {
/* 3539 */       str2 = paramString.substring(0, i);
/* 3540 */       str3 = paramString.substring(i + 1, paramString.length());
/* 3541 */       i = str3.indexOf(',');
/* 3542 */       if (i != -1)
/*      */       {
/* 3546 */         j = 1;
/*      */       }
/*      */     }
/* 3549 */     TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol(str2);
/* 3550 */     if ((null != localTokenSymbol) && (null != localTokenSymbol.getASTNodeType()))
/* 3551 */       str1 = "(" + localTokenSymbol.getASTNodeType() + ") " + str1;
/* 3552 */     else if (this.usingCustomAST) {
/* 3553 */       str1 = "(" + this.labeledElementASTType + ") " + str1;
/*      */     }
/* 3555 */     return str1;
/*      */   }
/*      */ 
/*      */   protected String getLookaheadTestExpression(Lookahead[] paramArrayOfLookahead, int paramInt) {
/* 3559 */     StringBuffer localStringBuffer = new StringBuffer(100);
/* 3560 */     int i = 1;
/*      */ 
/* 3562 */     localStringBuffer.append("(");
/* 3563 */     for (int j = 1; j <= paramInt; j++) {
/* 3564 */       BitSet localBitSet = paramArrayOfLookahead[j].fset;
/* 3565 */       if (i == 0) {
/* 3566 */         localStringBuffer.append(") && (");
/*      */       }
/* 3568 */       i = 0;
/*      */ 
/* 3573 */       if (paramArrayOfLookahead[j].containsEpsilon())
/* 3574 */         localStringBuffer.append("true");
/*      */       else {
/* 3576 */         localStringBuffer.append(getLookaheadTestTerm(j, localBitSet));
/*      */       }
/*      */     }
/* 3579 */     localStringBuffer.append(")");
/*      */ 
/* 3581 */     return localStringBuffer.toString();
/*      */   }
/*      */ 
/*      */   protected String getLookaheadTestExpression(Alternative paramAlternative, int paramInt)
/*      */   {
/* 3589 */     int i = paramAlternative.lookaheadDepth;
/* 3590 */     if (i == 2147483647)
/*      */     {
/* 3593 */       i = this.grammar.maxk;
/*      */     }
/*      */ 
/* 3596 */     if (paramInt == 0)
/*      */     {
/* 3599 */       return "( true )";
/*      */     }
/* 3601 */     return "(" + getLookaheadTestExpression(paramAlternative.cache, i) + ")";
/*      */   }
/*      */ 
/*      */   protected String getLookaheadTestTerm(int paramInt, BitSet paramBitSet)
/*      */   {
/* 3614 */     String str1 = lookaheadString(paramInt);
/*      */ 
/* 3617 */     int[] arrayOfInt = paramBitSet.toArray();
/* 3618 */     if (elementsAreRange(arrayOfInt)) {
/* 3619 */       return getRangeExpression(paramInt, arrayOfInt);
/*      */     }
/*      */ 
/* 3624 */     int i = paramBitSet.degree();
/* 3625 */     if (i == 0) {
/* 3626 */       return "true";
/*      */     }
/*      */ 
/* 3629 */     if (i >= this.bitsetTestThreshold) {
/* 3630 */       j = markBitsetForGen(paramBitSet);
/* 3631 */       return getBitsetName(j) + ".member(" + str1 + ")";
/*      */     }
/*      */ 
/* 3635 */     StringBuffer localStringBuffer = new StringBuffer();
/* 3636 */     for (int j = 0; j < arrayOfInt.length; j++)
/*      */     {
/* 3638 */       String str2 = getValueString(arrayOfInt[j]);
/*      */ 
/* 3641 */       if (j > 0) localStringBuffer.append("||");
/* 3642 */       localStringBuffer.append(str1);
/* 3643 */       localStringBuffer.append("==");
/* 3644 */       localStringBuffer.append(str2);
/*      */     }
/* 3646 */     return localStringBuffer.toString();
/*      */   }
/*      */ 
/*      */   public String getRangeExpression(int paramInt, int[] paramArrayOfInt)
/*      */   {
/* 3655 */     if (!elementsAreRange(paramArrayOfInt)) {
/* 3656 */       this.antlrTool.panic("getRangeExpression called with non-range");
/*      */     }
/* 3658 */     int i = paramArrayOfInt[0];
/* 3659 */     int j = paramArrayOfInt[(paramArrayOfInt.length - 1)];
/*      */ 
/* 3661 */     return "(" + lookaheadString(paramInt) + " >= " + getValueString(i) + " && " + lookaheadString(paramInt) + " <= " + getValueString(j) + ")";
/*      */   }
/*      */ 
/*      */   private String getValueString(int paramInt)
/*      */   {
/*      */     Object localObject;
/* 3671 */     if ((this.grammar instanceof LexerGrammar)) {
/* 3672 */       localObject = this.charFormatter.literalChar(paramInt);
/*      */     }
/*      */     else
/*      */     {
/* 3676 */       TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbolAt(paramInt);
/* 3677 */       if (localTokenSymbol == null) {
/* 3678 */         return "" + paramInt;
/*      */       }
/*      */ 
/* 3681 */       String str1 = localTokenSymbol.getId();
/* 3682 */       if ((localTokenSymbol instanceof StringLiteralSymbol))
/*      */       {
/* 3686 */         StringLiteralSymbol localStringLiteralSymbol = (StringLiteralSymbol)localTokenSymbol;
/* 3687 */         String str2 = localStringLiteralSymbol.getLabel();
/* 3688 */         if (str2 != null) {
/* 3689 */           localObject = str2;
/*      */         }
/*      */         else {
/* 3692 */           localObject = mangleLiteral(str1);
/* 3693 */           if (localObject == null)
/* 3694 */             localObject = String.valueOf(paramInt);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 3699 */         localObject = str1;
/*      */       }
/*      */     }
/* 3702 */     return localObject;
/*      */   }
/*      */ 
/*      */   protected boolean lookaheadIsEmpty(Alternative paramAlternative, int paramInt)
/*      */   {
/* 3707 */     int i = paramAlternative.lookaheadDepth;
/* 3708 */     if (i == 2147483647) {
/* 3709 */       i = this.grammar.maxk;
/*      */     }
/* 3711 */     for (int j = 1; (j <= i) && (j <= paramInt); j++) {
/* 3712 */       BitSet localBitSet = paramAlternative.cache[j].fset;
/* 3713 */       if (localBitSet.degree() != 0) {
/* 3714 */         return false;
/*      */       }
/*      */     }
/* 3717 */     return true;
/*      */   }
/*      */ 
/*      */   private String lookaheadString(int paramInt) {
/* 3721 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 3722 */       return "_t.Type";
/*      */     }
/* 3724 */     if ((this.grammar instanceof LexerGrammar)) {
/* 3725 */       if (paramInt == 1) {
/* 3726 */         return "cached_LA1";
/*      */       }
/* 3728 */       if (paramInt == 2) {
/* 3729 */         return "cached_LA2";
/*      */       }
/*      */     }
/* 3732 */     return "LA(" + paramInt + ")";
/*      */   }
/*      */ 
/*      */   private String mangleLiteral(String paramString)
/*      */   {
/* 3742 */     String str = this.antlrTool.literalsPrefix;
/* 3743 */     for (int i = 1; i < paramString.length() - 1; i++) {
/* 3744 */       if ((!Character.isLetter(paramString.charAt(i))) && (paramString.charAt(i) != '_'))
/*      */       {
/* 3746 */         return null;
/*      */       }
/* 3748 */       str = str + paramString.charAt(i);
/*      */     }
/* 3750 */     if (this.antlrTool.upperCaseMangledLiterals) {
/* 3751 */       str = str.toUpperCase();
/*      */     }
/* 3753 */     return str;
/*      */   }
/*      */ 
/*      */   public String mapTreeId(String paramString, ActionTransInfo paramActionTransInfo)
/*      */   {
/* 3764 */     if (this.currentRule == null) return paramString;
/*      */ 
/* 3766 */     int i = 0;
/* 3767 */     String str1 = paramString;
/* 3768 */     if ((this.grammar instanceof TreeWalkerGrammar))
/*      */     {
/* 3770 */       if (!this.grammar.buildAST)
/*      */       {
/* 3772 */         i = 1;
/*      */       }
/* 3775 */       else if ((str1.length() > 3) && (str1.lastIndexOf("_in") == str1.length() - 3))
/*      */       {
/* 3778 */         str1 = str1.substring(0, str1.length() - 3);
/* 3779 */         i = 1;
/*      */       }
/*      */     }
/*      */     Object localObject;
/* 3785 */     for (int j = 0; j < this.currentRule.labeledElements.size(); j++)
/*      */     {
/* 3787 */       localObject = (AlternativeElement)this.currentRule.labeledElements.elementAt(j);
/* 3788 */       if (((AlternativeElement)localObject).getLabel().equals(str1))
/*      */       {
/* 3790 */         return str1 + "_AST";
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3797 */     String str2 = (String)this.treeVariableMap.get(str1);
/* 3798 */     if (str2 != null)
/*      */     {
/* 3800 */       if (str2 == NONUNIQUE)
/*      */       {
/* 3803 */         this.antlrTool.error("Ambiguous reference to AST element " + str1 + " in rule " + this.currentRule.getRuleName());
/*      */ 
/* 3805 */         return null;
/*      */       }
/* 3807 */       if (str2.equals(this.currentRule.getRuleName()))
/*      */       {
/* 3813 */         this.antlrTool.error("Ambiguous reference to AST element " + str1 + " in rule " + this.currentRule.getRuleName());
/*      */ 
/* 3815 */         return null;
/*      */       }
/*      */ 
/* 3819 */       return i != 0 ? str2 + "_in" : str2;
/*      */     }
/*      */ 
/* 3825 */     if (str1.equals(this.currentRule.getRuleName()))
/*      */     {
/* 3827 */       localObject = str1 + "_AST";
/* 3828 */       if ((paramActionTransInfo != null) && 
/* 3829 */         (i == 0)) {
/* 3830 */         paramActionTransInfo.refRuleRoot = ((String)localObject);
/*      */       }
/*      */ 
/* 3833 */       return localObject;
/*      */     }
/*      */ 
/* 3838 */     return str1;
/*      */   }
/*      */ 
/*      */   private void mapTreeVariable(AlternativeElement paramAlternativeElement, String paramString)
/*      */   {
/* 3848 */     if ((paramAlternativeElement instanceof TreeElement)) {
/* 3849 */       mapTreeVariable(((TreeElement)paramAlternativeElement).root, paramString);
/* 3850 */       return;
/*      */     }
/*      */ 
/* 3854 */     String str = null;
/*      */ 
/* 3857 */     if (paramAlternativeElement.getLabel() == null) {
/* 3858 */       if ((paramAlternativeElement instanceof TokenRefElement))
/*      */       {
/* 3860 */         str = ((TokenRefElement)paramAlternativeElement).atomText;
/*      */       }
/* 3862 */       else if ((paramAlternativeElement instanceof RuleRefElement))
/*      */       {
/* 3864 */         str = ((RuleRefElement)paramAlternativeElement).targetRule;
/*      */       }
/*      */     }
/*      */ 
/* 3868 */     if (str != null)
/* 3869 */       if (this.treeVariableMap.get(str) != null)
/*      */       {
/* 3871 */         this.treeVariableMap.remove(str);
/* 3872 */         this.treeVariableMap.put(str, NONUNIQUE);
/*      */       }
/*      */       else {
/* 3875 */         this.treeVariableMap.put(str, paramString);
/*      */       }
/*      */   }
/*      */ 
/*      */   protected String processActionForSpecialSymbols(String paramString, int paramInt, RuleBlock paramRuleBlock, ActionTransInfo paramActionTransInfo)
/*      */   {
/* 3889 */     if ((paramString == null) || (paramString.length() == 0)) {
/* 3890 */       return null;
/*      */     }
/*      */ 
/* 3894 */     if (this.grammar == null) {
/* 3895 */       return paramString;
/*      */     }
/*      */ 
/* 3898 */     if (((this.grammar.buildAST) && (paramString.indexOf('#') != -1)) || ((this.grammar instanceof TreeWalkerGrammar)) || ((((this.grammar instanceof LexerGrammar)) || ((this.grammar instanceof ParserGrammar))) && (paramString.indexOf('$') != -1)))
/*      */     {
/* 3905 */       ActionLexer localActionLexer = new ActionLexer(paramString, paramRuleBlock, this, paramActionTransInfo);
/*      */ 
/* 3907 */       localActionLexer.setLineOffset(paramInt);
/* 3908 */       localActionLexer.setFilename(this.grammar.getFilename());
/* 3909 */       localActionLexer.setTool(this.antlrTool);
/*      */       try
/*      */       {
/* 3912 */         localActionLexer.mACTION(true);
/* 3913 */         paramString = localActionLexer.getTokenObject().getText();
/*      */       }
/*      */       catch (RecognitionException localRecognitionException)
/*      */       {
/* 3918 */         localActionLexer.reportError(localRecognitionException);
/* 3919 */         return paramString;
/*      */       }
/*      */       catch (TokenStreamException localTokenStreamException) {
/* 3922 */         this.antlrTool.panic("Error reading action:" + paramString);
/* 3923 */         return paramString;
/*      */       }
/*      */       catch (CharStreamException localCharStreamException) {
/* 3926 */         this.antlrTool.panic("Error reading action:" + paramString);
/* 3927 */         return paramString;
/*      */       }
/*      */     }
/* 3930 */     return paramString;
/*      */   }
/*      */ 
/*      */   private void setupGrammarParameters(Grammar paramGrammar)
/*      */   {
/*      */     Token localToken;
/* 3934 */     if (((paramGrammar instanceof ParserGrammar)) || ((paramGrammar instanceof LexerGrammar)) || ((paramGrammar instanceof TreeWalkerGrammar)))
/*      */     {
/* 3943 */       if (this.antlrTool.nameSpace != null) {
/* 3944 */         nameSpace = new CSharpNameSpace(this.antlrTool.nameSpace.getName());
/*      */       }
/*      */ 
/* 3949 */       if (paramGrammar.hasOption("namespace")) {
/* 3950 */         localToken = paramGrammar.getOption("namespace");
/* 3951 */         if (localToken != null)
/* 3952 */           nameSpace = new CSharpNameSpace(localToken.getText());
/*      */       }
/*      */     }
/*      */     String str;
/* 3966 */     if ((paramGrammar instanceof ParserGrammar)) {
/* 3967 */       this.labeledElementASTType = "AST";
/* 3968 */       if (paramGrammar.hasOption("ASTLabelType")) {
/* 3969 */         localToken = paramGrammar.getOption("ASTLabelType");
/* 3970 */         if (localToken != null) {
/* 3971 */           str = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 3972 */           if (str != null) {
/* 3973 */             this.usingCustomAST = true;
/* 3974 */             this.labeledElementASTType = str;
/*      */           }
/*      */         }
/*      */       }
/* 3978 */       this.labeledElementType = "IToken ";
/* 3979 */       this.labeledElementInit = "null";
/* 3980 */       this.commonExtraArgs = "";
/* 3981 */       this.commonExtraParams = "";
/* 3982 */       this.commonLocalVars = "";
/* 3983 */       this.lt1Value = "LT(1)";
/* 3984 */       this.exceptionThrown = "RecognitionException";
/* 3985 */       this.throwNoViable = "throw new NoViableAltException(LT(1), getFilename());";
/*      */     }
/* 3987 */     else if ((paramGrammar instanceof LexerGrammar)) {
/* 3988 */       this.labeledElementType = "char ";
/* 3989 */       this.labeledElementInit = "'\\0'";
/* 3990 */       this.commonExtraArgs = "";
/* 3991 */       this.commonExtraParams = "bool _createToken";
/* 3992 */       this.commonLocalVars = "int _ttype; IToken _token=null; int _begin=text.Length;";
/* 3993 */       this.lt1Value = "cached_LA1";
/* 3994 */       this.exceptionThrown = "RecognitionException";
/* 3995 */       this.throwNoViable = "throw new NoViableAltForCharException(cached_LA1, getFilename(), getLine(), getColumn());";
/*      */     }
/* 3997 */     else if ((paramGrammar instanceof TreeWalkerGrammar)) {
/* 3998 */       this.labeledElementASTType = "AST";
/* 3999 */       this.labeledElementType = "AST";
/* 4000 */       if (paramGrammar.hasOption("ASTLabelType")) {
/* 4001 */         localToken = paramGrammar.getOption("ASTLabelType");
/* 4002 */         if (localToken != null) {
/* 4003 */           str = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 4004 */           if (str != null) {
/* 4005 */             this.usingCustomAST = true;
/* 4006 */             this.labeledElementASTType = str;
/* 4007 */             this.labeledElementType = str;
/*      */           }
/*      */         }
/*      */       }
/* 4011 */       if (!paramGrammar.hasOption("ASTLabelType")) {
/* 4012 */         paramGrammar.setOption("ASTLabelType", new Token(6, "AST"));
/*      */       }
/* 4014 */       this.labeledElementInit = "null";
/* 4015 */       this.commonExtraArgs = "_t";
/* 4016 */       this.commonExtraParams = "AST _t";
/* 4017 */       this.commonLocalVars = "";
/* 4018 */       if (this.usingCustomAST)
/* 4019 */         this.lt1Value = ("(_t==ASTNULL) ? null : (" + this.labeledElementASTType + ")_t");
/*      */       else
/* 4021 */         this.lt1Value = "_t";
/* 4022 */       this.exceptionThrown = "RecognitionException";
/* 4023 */       this.throwNoViable = "throw new NoViableAltException(_t);";
/*      */     }
/*      */     else {
/* 4026 */       this.antlrTool.panic("Unknown grammar type");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setupOutput(String paramString)
/*      */     throws IOException
/*      */   {
/* 4036 */     this.currentOutput = this.antlrTool.openOutputFile(paramString + ".cs");
/*      */   }
/*      */ 
/*      */   private static String OctalToUnicode(String paramString)
/*      */   {
/* 4043 */     if ((4 <= paramString.length()) && ('\'' == paramString.charAt(0)) && ('\\' == paramString.charAt(1)) && ('0' <= paramString.charAt(2)) && ('7' >= paramString.charAt(2)) && ('\'' == paramString.charAt(paramString.length() - 1)))
/*      */     {
/* 4050 */       Integer localInteger = Integer.valueOf(paramString.substring(2, paramString.length() - 1), 8);
/*      */ 
/* 4052 */       return "'\\x" + Integer.toHexString(localInteger.intValue()) + "'";
/*      */     }
/*      */ 
/* 4055 */     return paramString;
/*      */   }
/*      */ 
/*      */   public String getTokenTypesClassName()
/*      */   {
/* 4064 */     TokenManager localTokenManager = this.grammar.tokenManager;
/* 4065 */     return new String(localTokenManager.getName() + TokenTypesFileSuffix);
/*      */   }
/*      */ 
/*      */   private void declareSaveIndexVariableIfNeeded()
/*      */   {
/* 4070 */     if (this.saveIndexCreateLevel == 0)
/*      */     {
/* 4072 */       println("int _saveIndex = 0;");
/* 4073 */       this.saveIndexCreateLevel = this.blockNestingLevel;
/*      */     }
/*      */   }
/*      */ 
/*      */   public String[] split(String paramString1, String paramString2)
/*      */   {
/* 4079 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, paramString2);
/* 4080 */     int i = localStringTokenizer.countTokens();
/* 4081 */     String[] arrayOfString = new String[i];
/*      */ 
/* 4083 */     int j = 0;
/* 4084 */     while (localStringTokenizer.hasMoreTokens())
/*      */     {
/* 4086 */       arrayOfString[j] = localStringTokenizer.nextToken();
/* 4087 */       j++;
/*      */     }
/* 4089 */     return arrayOfString;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.CSharpCodeGenerator
 * JD-Core Version:    0.6.2
 */