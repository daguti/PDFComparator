/*      */ package antlr;
/*      */ 
/*      */ import antlr.actions.python.ActionLexer;
/*      */ import antlr.actions.python.CodeLexer;
/*      */ import antlr.collections.impl.BitSet;
/*      */ import antlr.collections.impl.Vector;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ 
/*      */ public class PythonCodeGenerator extends CodeGenerator
/*      */ {
/*   22 */   protected int syntacticPredLevel = 0;
/*      */ 
/*   25 */   protected boolean genAST = false;
/*      */ 
/*   28 */   protected boolean saveText = false;
/*      */   String labeledElementType;
/*      */   String labeledElementASTType;
/*      */   String labeledElementInit;
/*      */   String commonExtraArgs;
/*      */   String commonExtraParams;
/*      */   String commonLocalVars;
/*      */   String lt1Value;
/*      */   String exceptionThrown;
/*      */   String throwNoViable;
/*      */   public static final String initHeaderAction = "__init__";
/*      */   public static final String mainHeaderAction = "__main__";
/*      */   String lexerClassName;
/*      */   String parserClassName;
/*      */   String treeWalkerClassName;
/*      */   RuleBlock currentRule;
/*      */   String currentASTResult;
/*   59 */   Hashtable treeVariableMap = new Hashtable();
/*      */ 
/*   64 */   Hashtable declaredASTVariables = new Hashtable();
/*      */ 
/*   67 */   int astVarNumber = 1;
/*      */ 
/*   70 */   protected static final String NONUNIQUE = new String();
/*      */   public static final int caseSizeThreshold = 127;
/*      */   private Vector semPreds;
/*      */ 
/*      */   protected void printTabs()
/*      */   {
/*   83 */     for (int i = 0; i < this.tabs; i++)
/*      */     {
/*   85 */       this.currentOutput.print("    ");
/*      */     }
/*      */   }
/*      */ 
/*      */   public PythonCodeGenerator()
/*      */   {
/*   91 */     this.charFormatter = new PythonCharFormatter();
/*   92 */     this.DEBUG_CODE_GENERATOR = true;
/*      */   }
/*      */ 
/*      */   protected int addSemPred(String paramString)
/*      */   {
/*  101 */     this.semPreds.appendElement(paramString);
/*  102 */     return this.semPreds.size() - 1;
/*      */   }
/*      */ 
/*      */   public void exitIfError() {
/*  106 */     if (this.antlrTool.hasError())
/*  107 */       this.antlrTool.fatalError("Exiting due to errors.");
/*      */   }
/*      */ 
/*      */   protected void checkCurrentOutputStream()
/*      */   {
/*      */     try
/*      */     {
/*  114 */       if (this.currentOutput == null)
/*  115 */         throw new NullPointerException();
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*  119 */       Utils.error("current output is not set");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected String extractIdOfAction(String paramString, int paramInt1, int paramInt2)
/*      */   {
/*  132 */     paramString = removeAssignmentFromDeclaration(paramString);
/*      */ 
/*  135 */     paramString = paramString.trim();
/*      */ 
/*  137 */     return paramString;
/*      */   }
/*      */ 
/*      */   protected String extractTypeOfAction(String paramString, int paramInt1, int paramInt2)
/*      */   {
/*  149 */     return "";
/*      */   }
/*      */ 
/*      */   protected void flushTokens()
/*      */   {
/*      */     try {
/*  155 */       int i = 0;
/*      */ 
/*  157 */       checkCurrentOutputStream();
/*      */ 
/*  159 */       println("");
/*  160 */       println("### import antlr.Token ");
/*  161 */       println("from antlr import Token");
/*  162 */       println("### >>>The Known Token Types <<<");
/*      */ 
/*  166 */       PrintWriter localPrintWriter = this.currentOutput;
/*      */ 
/*  169 */       Enumeration localEnumeration = this.behavior.tokenManagers.elements();
/*      */ 
/*  172 */       while (localEnumeration.hasMoreElements())
/*      */       {
/*  174 */         TokenManager localTokenManager = (TokenManager)localEnumeration.nextElement();
/*      */ 
/*  177 */         if (!localTokenManager.isReadOnly())
/*      */         {
/*  182 */           if (i == 0) {
/*  183 */             genTokenTypes(localTokenManager);
/*  184 */             i = 1;
/*      */           }
/*      */ 
/*  188 */           this.currentOutput = localPrintWriter;
/*      */ 
/*  191 */           genTokenInterchange(localTokenManager);
/*  192 */           this.currentOutput = localPrintWriter;
/*      */         }
/*      */ 
/*  195 */         exitIfError();
/*      */       }
/*      */     }
/*      */     catch (Exception localException) {
/*  199 */       exitIfError();
/*      */     }
/*  201 */     checkCurrentOutputStream();
/*  202 */     println("");
/*      */   }
/*      */ 
/*      */   public void gen()
/*      */   {
/*      */     try
/*      */     {
/*  211 */       Enumeration localEnumeration = this.behavior.grammars.elements();
/*  212 */       while (localEnumeration.hasMoreElements()) {
/*  213 */         Grammar localGrammar = (Grammar)localEnumeration.nextElement();
/*      */ 
/*  215 */         localGrammar.setGrammarAnalyzer(this.analyzer);
/*  216 */         localGrammar.setCodeGenerator(this);
/*  217 */         this.analyzer.setGrammar(localGrammar);
/*      */ 
/*  219 */         setupGrammarParameters(localGrammar);
/*  220 */         localGrammar.generate();
/*      */ 
/*  223 */         exitIfError();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (IOException localIOException)
/*      */     {
/*  229 */       this.antlrTool.reportException(localIOException, null);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void gen(ActionElement paramActionElement)
/*      */   {
/*  237 */     if (paramActionElement.isSemPred) {
/*  238 */       genSemPred(paramActionElement.actionText, paramActionElement.line);
/*      */     }
/*      */     else
/*      */     {
/*  242 */       if (this.grammar.hasSyntacticPredicate) {
/*  243 */         println("if not self.inputState.guessing:");
/*  244 */         this.tabs += 1;
/*      */       }
/*      */ 
/*  249 */       ActionTransInfo localActionTransInfo = new ActionTransInfo();
/*  250 */       String str = processActionForSpecialSymbols(paramActionElement.actionText, paramActionElement.getLine(), this.currentRule, localActionTransInfo);
/*      */ 
/*  255 */       if (localActionTransInfo.refRuleRoot != null)
/*      */       {
/*  260 */         println(localActionTransInfo.refRuleRoot + " = currentAST.root");
/*      */       }
/*      */ 
/*  264 */       printAction(str);
/*      */ 
/*  266 */       if (localActionTransInfo.assignToRoot)
/*      */       {
/*  268 */         println("currentAST.root = " + localActionTransInfo.refRuleRoot + "");
/*      */ 
/*  270 */         println("if (" + localActionTransInfo.refRuleRoot + " != None) and (" + localActionTransInfo.refRuleRoot + ".getFirstChild() != None):");
/*  271 */         this.tabs += 1;
/*  272 */         println("currentAST.child = " + localActionTransInfo.refRuleRoot + ".getFirstChild()");
/*  273 */         this.tabs -= 1;
/*  274 */         println("else:");
/*  275 */         this.tabs += 1;
/*  276 */         println("currentAST.child = " + localActionTransInfo.refRuleRoot);
/*  277 */         this.tabs -= 1;
/*  278 */         println("currentAST.advanceChildToEnd()");
/*      */       }
/*      */ 
/*  281 */       if (this.grammar.hasSyntacticPredicate)
/*  282 */         this.tabs -= 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void gen(AlternativeBlock paramAlternativeBlock)
/*      */   {
/*  291 */     if (this.DEBUG_CODE_GENERATOR) System.out.println("gen(" + paramAlternativeBlock + ")");
/*  292 */     genBlockPreamble(paramAlternativeBlock);
/*  293 */     genBlockInitAction(paramAlternativeBlock);
/*      */ 
/*  296 */     String str = this.currentASTResult;
/*  297 */     if (paramAlternativeBlock.getLabel() != null) {
/*  298 */       this.currentASTResult = paramAlternativeBlock.getLabel();
/*      */     }
/*      */ 
/*  301 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramAlternativeBlock);
/*      */ 
/*  304 */     int i = this.tabs;
/*  305 */     PythonBlockFinishingInfo localPythonBlockFinishingInfo = genCommonBlock(paramAlternativeBlock, true);
/*  306 */     genBlockFinish(localPythonBlockFinishingInfo, this.throwNoViable);
/*  307 */     this.tabs = i;
/*      */ 
/*  311 */     this.currentASTResult = str;
/*      */   }
/*      */ 
/*      */   public void gen(BlockEndElement paramBlockEndElement)
/*      */   {
/*  320 */     if (this.DEBUG_CODE_GENERATOR) System.out.println("genRuleEnd(" + paramBlockEndElement + ")");
/*      */   }
/*      */ 
/*      */   public void gen(CharLiteralElement paramCharLiteralElement)
/*      */   {
/*  327 */     if (this.DEBUG_CODE_GENERATOR) System.out.println("genChar(" + paramCharLiteralElement + ")");
/*      */ 
/*  329 */     if (paramCharLiteralElement.getLabel() != null) {
/*  330 */       println(paramCharLiteralElement.getLabel() + " = " + this.lt1Value);
/*      */     }
/*      */ 
/*  333 */     boolean bool = this.saveText;
/*  334 */     this.saveText = ((this.saveText) && (paramCharLiteralElement.getAutoGenType() == 1));
/*  335 */     genMatch(paramCharLiteralElement);
/*  336 */     this.saveText = bool;
/*      */   }
/*      */ 
/*      */   String toString(boolean paramBoolean)
/*      */   {
/*      */     String str;
/*  342 */     if (paramBoolean)
/*  343 */       str = "True";
/*      */     else
/*  345 */       str = "False";
/*  346 */     return str;
/*      */   }
/*      */ 
/*      */   public void gen(CharRangeElement paramCharRangeElement)
/*      */   {
/*  354 */     if ((paramCharRangeElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  355 */       println(paramCharRangeElement.getLabel() + " = " + this.lt1Value);
/*      */     }
/*  357 */     int i = ((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramCharRangeElement.getAutoGenType() == 3)) ? 1 : 0;
/*      */ 
/*  361 */     if (i != 0) {
/*  362 */       println("_saveIndex = self.text.length()");
/*      */     }
/*      */ 
/*  365 */     println("self.matchRange(u" + paramCharRangeElement.beginText + ", u" + paramCharRangeElement.endText + ")");
/*      */ 
/*  367 */     if (i != 0)
/*  368 */       println("self.text.setLength(_saveIndex)");
/*      */   }
/*      */ 
/*      */   public void gen(LexerGrammar paramLexerGrammar)
/*      */     throws IOException
/*      */   {
/*  376 */     if (paramLexerGrammar.debuggingOutput) {
/*  377 */       this.semPreds = new Vector();
/*      */     }
/*  379 */     setGrammar(paramLexerGrammar);
/*  380 */     if (!(this.grammar instanceof LexerGrammar)) {
/*  381 */       this.antlrTool.panic("Internal error generating lexer");
/*      */     }
/*      */ 
/*  386 */     setupOutput(this.grammar.getClassName());
/*      */ 
/*  388 */     this.genAST = false;
/*  389 */     this.saveText = true;
/*      */ 
/*  391 */     this.tabs = 0;
/*      */ 
/*  394 */     genHeader();
/*      */ 
/*  397 */     println("### import antlr and other modules ..");
/*  398 */     println("import sys");
/*  399 */     println("import antlr");
/*  400 */     println("");
/*  401 */     println("version = sys.version.split()[0]");
/*  402 */     println("if version < '2.2.1':");
/*  403 */     this.tabs += 1;
/*  404 */     println("False = 0");
/*  405 */     this.tabs -= 1;
/*  406 */     println("if version < '2.3':");
/*  407 */     this.tabs += 1;
/*  408 */     println("True = not False");
/*  409 */     this.tabs -= 1;
/*      */ 
/*  411 */     println("### header action >>> ");
/*  412 */     printActionCode(this.behavior.getHeaderAction(""), 0);
/*  413 */     println("### header action <<< ");
/*      */ 
/*  416 */     println("### preamble action >>> ");
/*  417 */     printActionCode(this.grammar.preambleAction.getText(), 0);
/*  418 */     println("### preamble action <<< ");
/*      */ 
/*  421 */     String str = null;
/*  422 */     if (this.grammar.superClass != null) {
/*  423 */       str = this.grammar.superClass;
/*      */     }
/*      */     else {
/*  426 */       str = "antlr." + this.grammar.getSuperClass();
/*      */     }
/*      */ 
/*  430 */     Object localObject1 = "";
/*  431 */     Token localToken = (Token)this.grammar.options.get("classHeaderPrefix");
/*  432 */     if (localToken != null) {
/*  433 */       localObject2 = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/*  434 */       if (localObject2 != null) {
/*  435 */         localObject1 = localObject2;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  440 */     println("### >>>The Literals<<<");
/*  441 */     println("literals = {}");
/*  442 */     Object localObject2 = this.grammar.tokenManager.getTokenSymbolKeys();
/*      */     Object localObject4;
/*  443 */     while (((Enumeration)localObject2).hasMoreElements()) {
/*  444 */       localObject3 = (String)((Enumeration)localObject2).nextElement();
/*  445 */       if (((String)localObject3).charAt(0) == '"')
/*      */       {
/*  448 */         TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol((String)localObject3);
/*  449 */         if ((localTokenSymbol instanceof StringLiteralSymbol)) {
/*  450 */           localObject4 = (StringLiteralSymbol)localTokenSymbol;
/*  451 */           println("literals[u" + ((StringLiteralSymbol)localObject4).getId() + "] = " + ((StringLiteralSymbol)localObject4).getTokenType());
/*      */         }
/*      */       }
/*      */     }
/*  454 */     println("");
/*  455 */     flushTokens();
/*      */ 
/*  458 */     genJavadocComment(this.grammar);
/*      */ 
/*  461 */     println("class " + this.lexerClassName + "(" + str + ") :");
/*  462 */     this.tabs += 1;
/*      */ 
/*  464 */     printGrammarAction(this.grammar);
/*      */ 
/*  470 */     println("def __init__(self, *argv, **kwargs) :");
/*  471 */     this.tabs += 1;
/*  472 */     println(str + ".__init__(self, *argv, **kwargs)");
/*      */ 
/*  477 */     println("self.caseSensitiveLiterals = " + toString(paramLexerGrammar.caseSensitiveLiterals));
/*  478 */     println("self.setCaseSensitive(" + toString(paramLexerGrammar.caseSensitive) + ")");
/*  479 */     println("self.literals = literals");
/*      */ 
/*  483 */     if (this.grammar.debuggingOutput) {
/*  484 */       println("ruleNames[] = [");
/*  485 */       localObject3 = this.grammar.rules.elements();
/*  486 */       i = 0;
/*  487 */       this.tabs += 1;
/*  488 */       while (((Enumeration)localObject3).hasMoreElements()) {
/*  489 */         localObject4 = (GrammarSymbol)((Enumeration)localObject3).nextElement();
/*  490 */         if ((localObject4 instanceof RuleSymbol))
/*  491 */           println("\"" + ((RuleSymbol)localObject4).getId() + "\",");
/*      */       }
/*  493 */       this.tabs -= 1;
/*  494 */       println("]");
/*      */     }
/*      */ 
/*  497 */     genHeaderInit(this.grammar);
/*      */ 
/*  499 */     this.tabs -= 1;
/*      */ 
/*  508 */     genNextToken();
/*  509 */     println("");
/*      */ 
/*  512 */     Object localObject3 = this.grammar.rules.elements();
/*  513 */     int i = 0;
/*  514 */     while (((Enumeration)localObject3).hasMoreElements()) {
/*  515 */       localObject4 = (RuleSymbol)((Enumeration)localObject3).nextElement();
/*      */ 
/*  517 */       if (!((RuleSymbol)localObject4).getId().equals("mnextToken")) {
/*  518 */         genRule((RuleSymbol)localObject4, false, i++);
/*      */       }
/*  520 */       exitIfError();
/*      */     }
/*      */ 
/*  524 */     if (this.grammar.debuggingOutput) {
/*  525 */       genSemPredMap();
/*      */     }
/*      */ 
/*  528 */     genBitsets(this.bitsetsUsed, ((LexerGrammar)this.grammar).charVocabulary.size());
/*  529 */     println("");
/*      */ 
/*  531 */     genHeaderMain(this.grammar);
/*      */ 
/*  534 */     this.currentOutput.close();
/*  535 */     this.currentOutput = null;
/*      */   }
/*      */ 
/*      */   protected void genHeaderMain(Grammar paramGrammar)
/*      */   {
/*  540 */     String str1 = paramGrammar.getClassName() + "." + "__main__";
/*  541 */     String str2 = this.behavior.getHeaderAction(str1);
/*      */ 
/*  543 */     if (isEmpty(str2))
/*  544 */       str2 = this.behavior.getHeaderAction("__main__");
/*      */     int i;
/*  546 */     if (isEmpty(str2)) {
/*  547 */       if ((paramGrammar instanceof LexerGrammar)) {
/*  548 */         i = this.tabs;
/*  549 */         this.tabs = 0;
/*  550 */         println("### __main__ header action >>> ");
/*  551 */         genLexerTest();
/*  552 */         this.tabs = 0;
/*  553 */         println("### __main__ header action <<< ");
/*  554 */         this.tabs = i;
/*      */       }
/*      */     } else {
/*  557 */       i = this.tabs;
/*  558 */       this.tabs = 0;
/*  559 */       println("");
/*  560 */       println("### __main__ header action >>> ");
/*  561 */       printMainFunc(str2);
/*  562 */       this.tabs = 0;
/*  563 */       println("### __main__ header action <<< ");
/*  564 */       this.tabs = i;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void genHeaderInit(Grammar paramGrammar)
/*      */   {
/*  570 */     String str1 = paramGrammar.getClassName() + "." + "__init__";
/*  571 */     String str2 = this.behavior.getHeaderAction(str1);
/*      */ 
/*  573 */     if (isEmpty(str2)) {
/*  574 */       str2 = this.behavior.getHeaderAction("__init__");
/*      */     }
/*  576 */     if (!isEmpty(str2))
/*      */     {
/*  579 */       int i = this.tabs;
/*  580 */       println("### __init__ header action >>> ");
/*  581 */       printActionCode(str2, 0);
/*  582 */       this.tabs = i;
/*  583 */       println("### __init__ header action <<< ");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void printMainFunc(String paramString) {
/*  588 */     int i = this.tabs;
/*  589 */     this.tabs = 0;
/*  590 */     println("if __name__ == '__main__':");
/*  591 */     this.tabs += 1;
/*  592 */     printActionCode(paramString, 0);
/*  593 */     this.tabs -= 1;
/*  594 */     this.tabs = i;
/*      */   }
/*      */ 
/*      */   public void gen(OneOrMoreBlock paramOneOrMoreBlock)
/*      */   {
/*  604 */     int i = this.tabs;
/*      */ 
/*  606 */     genBlockPreamble(paramOneOrMoreBlock);
/*      */     String str1;
/*  608 */     if (paramOneOrMoreBlock.getLabel() != null)
/*      */     {
/*  610 */       str1 = "_cnt_" + paramOneOrMoreBlock.getLabel();
/*      */     }
/*      */     else {
/*  613 */       str1 = "_cnt" + paramOneOrMoreBlock.ID;
/*      */     }
/*  615 */     println("" + str1 + "= 0");
/*  616 */     println("while True:");
/*  617 */     this.tabs += 1;
/*  618 */     i = this.tabs;
/*      */ 
/*  621 */     genBlockInitAction(paramOneOrMoreBlock);
/*      */ 
/*  624 */     String str2 = this.currentASTResult;
/*  625 */     if (paramOneOrMoreBlock.getLabel() != null) {
/*  626 */       this.currentASTResult = paramOneOrMoreBlock.getLabel();
/*      */     }
/*      */ 
/*  629 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramOneOrMoreBlock);
/*      */ 
/*  641 */     int j = 0;
/*  642 */     int k = this.grammar.maxk;
/*      */ 
/*  644 */     if ((!paramOneOrMoreBlock.greedy) && (paramOneOrMoreBlock.exitLookaheadDepth <= this.grammar.maxk) && (paramOneOrMoreBlock.exitCache[paramOneOrMoreBlock.exitLookaheadDepth].containsEpsilon()))
/*      */     {
/*  648 */       j = 1;
/*  649 */       k = paramOneOrMoreBlock.exitLookaheadDepth;
/*      */     }
/*  653 */     else if ((!paramOneOrMoreBlock.greedy) && (paramOneOrMoreBlock.exitLookaheadDepth == 2147483647))
/*      */     {
/*  655 */       j = 1;
/*      */     }
/*      */ 
/*  661 */     if (j != 0)
/*      */     {
/*  663 */       println("### nongreedy (...)+ loop; exit depth is " + paramOneOrMoreBlock.exitLookaheadDepth);
/*      */ 
/*  665 */       String str3 = getLookaheadTestExpression(paramOneOrMoreBlock.exitCache, k);
/*      */ 
/*  670 */       println("### nongreedy exit test");
/*  671 */       println("if " + str1 + " >= 1 and " + str3 + ":");
/*  672 */       this.tabs += 1;
/*  673 */       println("break");
/*  674 */       this.tabs -= 1;
/*      */     }
/*      */ 
/*  678 */     int m = this.tabs;
/*  679 */     PythonBlockFinishingInfo localPythonBlockFinishingInfo = genCommonBlock(paramOneOrMoreBlock, false);
/*  680 */     genBlockFinish(localPythonBlockFinishingInfo, "break");
/*  681 */     this.tabs = m;
/*      */ 
/*  685 */     this.tabs = i;
/*  686 */     println(str1 + " += 1");
/*  687 */     this.tabs = i;
/*  688 */     this.tabs -= 1;
/*  689 */     println("if " + str1 + " < 1:");
/*  690 */     this.tabs += 1;
/*  691 */     println(this.throwNoViable);
/*  692 */     this.tabs -= 1;
/*      */ 
/*  694 */     this.currentASTResult = str2;
/*      */   }
/*      */ 
/*      */   public void gen(ParserGrammar paramParserGrammar)
/*      */     throws IOException
/*      */   {
/*  703 */     if (paramParserGrammar.debuggingOutput) {
/*  704 */       this.semPreds = new Vector();
/*      */     }
/*  706 */     setGrammar(paramParserGrammar);
/*  707 */     if (!(this.grammar instanceof ParserGrammar)) {
/*  708 */       this.antlrTool.panic("Internal error generating parser");
/*      */     }
/*      */ 
/*  713 */     setupOutput(this.grammar.getClassName());
/*      */ 
/*  715 */     this.genAST = this.grammar.buildAST;
/*      */ 
/*  717 */     this.tabs = 0;
/*      */ 
/*  720 */     genHeader();
/*      */ 
/*  723 */     println("### import antlr and other modules ..");
/*  724 */     println("import sys");
/*  725 */     println("import antlr");
/*  726 */     println("");
/*  727 */     println("version = sys.version.split()[0]");
/*  728 */     println("if version < '2.2.1':");
/*  729 */     this.tabs += 1;
/*  730 */     println("False = 0");
/*  731 */     this.tabs -= 1;
/*  732 */     println("if version < '2.3':");
/*  733 */     this.tabs += 1;
/*  734 */     println("True = not False");
/*  735 */     this.tabs -= 1;
/*      */ 
/*  737 */     println("### header action >>> ");
/*  738 */     printActionCode(this.behavior.getHeaderAction(""), 0);
/*  739 */     println("### header action <<< ");
/*      */ 
/*  741 */     println("### preamble action>>>");
/*      */ 
/*  743 */     printActionCode(this.grammar.preambleAction.getText(), 0);
/*  744 */     println("### preamble action <<<");
/*      */ 
/*  746 */     flushTokens();
/*      */ 
/*  749 */     String str = null;
/*  750 */     if (this.grammar.superClass != null)
/*  751 */       str = this.grammar.superClass;
/*      */     else {
/*  753 */       str = "antlr." + this.grammar.getSuperClass();
/*      */     }
/*      */ 
/*  756 */     genJavadocComment(this.grammar);
/*      */ 
/*  759 */     Object localObject1 = "";
/*  760 */     Token localToken = (Token)this.grammar.options.get("classHeaderPrefix");
/*  761 */     if (localToken != null) {
/*  762 */       localObject2 = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/*  763 */       if (localObject2 != null) {
/*  764 */         localObject1 = localObject2;
/*      */       }
/*      */     }
/*      */ 
/*  768 */     print("class " + this.parserClassName + "(" + str);
/*  769 */     println("):");
/*  770 */     this.tabs += 1;
/*      */     GrammarSymbol localGrammarSymbol;
/*  774 */     if (this.grammar.debuggingOutput) {
/*  775 */       println("_ruleNames = [");
/*      */ 
/*  777 */       localObject2 = this.grammar.rules.elements();
/*  778 */       i = 0;
/*  779 */       this.tabs += 1;
/*  780 */       while (((Enumeration)localObject2).hasMoreElements()) {
/*  781 */         localGrammarSymbol = (GrammarSymbol)((Enumeration)localObject2).nextElement();
/*  782 */         if ((localGrammarSymbol instanceof RuleSymbol))
/*  783 */           println("\"" + ((RuleSymbol)localGrammarSymbol).getId() + "\",");
/*      */       }
/*  785 */       this.tabs -= 1;
/*  786 */       println("]");
/*      */     }
/*      */ 
/*  790 */     printGrammarAction(this.grammar);
/*      */ 
/*  793 */     println("");
/*  794 */     println("def __init__(self, *args, **kwargs):");
/*  795 */     this.tabs += 1;
/*  796 */     println(str + ".__init__(self, *args, **kwargs)");
/*  797 */     println("self.tokenNames = _tokenNames");
/*      */ 
/*  800 */     if (this.grammar.debuggingOutput) {
/*  801 */       println("self.ruleNames  = _ruleNames");
/*  802 */       println("self.semPredNames = _semPredNames");
/*  803 */       println("self.setupDebugging(self.tokenBuf)");
/*      */     }
/*  805 */     if (this.grammar.buildAST) {
/*  806 */       println("self.buildTokenTypeASTClassMap()");
/*  807 */       println("self.astFactory = antlr.ASTFactory(self.getTokenTypeToASTClassMap())");
/*  808 */       if (this.labeledElementASTType != null)
/*      */       {
/*  810 */         println("self.astFactory.setASTNodeClass(" + this.labeledElementASTType + ")");
/*      */       }
/*      */     }
/*      */ 
/*  814 */     genHeaderInit(this.grammar);
/*  815 */     println("");
/*      */ 
/*  818 */     Object localObject2 = this.grammar.rules.elements();
/*  819 */     int i = 0;
/*  820 */     while (((Enumeration)localObject2).hasMoreElements()) {
/*  821 */       localGrammarSymbol = (GrammarSymbol)((Enumeration)localObject2).nextElement();
/*  822 */       if ((localGrammarSymbol instanceof RuleSymbol)) {
/*  823 */         RuleSymbol localRuleSymbol = (RuleSymbol)localGrammarSymbol;
/*  824 */         genRule(localRuleSymbol, localRuleSymbol.references.size() == 0, i++);
/*      */       }
/*  826 */       exitIfError();
/*      */     }
/*      */ 
/*  830 */     if (this.grammar.buildAST) {
/*  831 */       genTokenASTNodeMap();
/*      */     }
/*      */ 
/*  835 */     genTokenStrings();
/*      */ 
/*  838 */     genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
/*      */ 
/*  841 */     if (this.grammar.debuggingOutput) {
/*  842 */       genSemPredMap();
/*      */     }
/*      */ 
/*  845 */     println("");
/*      */ 
/*  847 */     this.tabs = 0;
/*  848 */     genHeaderMain(this.grammar);
/*      */ 
/*  850 */     this.currentOutput.close();
/*  851 */     this.currentOutput = null;
/*      */   }
/*      */ 
/*      */   public void gen(RuleRefElement paramRuleRefElement)
/*      */   {
/*  858 */     if (this.DEBUG_CODE_GENERATOR) System.out.println("genRR(" + paramRuleRefElement + ")");
/*  859 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(paramRuleRefElement.targetRule);
/*  860 */     if ((localRuleSymbol == null) || (!localRuleSymbol.isDefined()))
/*      */     {
/*  862 */       this.antlrTool.error("Rule '" + paramRuleRefElement.targetRule + "' is not defined", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*  863 */       return;
/*      */     }
/*  865 */     if (!(localRuleSymbol instanceof RuleSymbol))
/*      */     {
/*  867 */       this.antlrTool.error("'" + paramRuleRefElement.targetRule + "' does not name a grammar rule", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*  868 */       return;
/*      */     }
/*      */ 
/*  871 */     genErrorTryForElement(paramRuleRefElement);
/*      */ 
/*  875 */     if (((this.grammar instanceof TreeWalkerGrammar)) && (paramRuleRefElement.getLabel() != null) && (this.syntacticPredLevel == 0))
/*      */     {
/*  878 */       println(paramRuleRefElement.getLabel() + " = antlr.ifelse(_t == antlr.ASTNULL, None, " + this.lt1Value + ")");
/*      */     }
/*      */ 
/*  882 */     if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramRuleRefElement.getAutoGenType() == 3))) {
/*  883 */       println("_saveIndex = self.text.length()");
/*      */     }
/*      */ 
/*  887 */     printTabs();
/*  888 */     if (paramRuleRefElement.idAssign != null)
/*      */     {
/*  890 */       if (localRuleSymbol.block.returnAction == null) {
/*  891 */         this.antlrTool.warning("Rule '" + paramRuleRefElement.targetRule + "' has no return type", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*      */       }
/*  893 */       _print(paramRuleRefElement.idAssign + "=");
/*      */     }
/*  897 */     else if ((!(this.grammar instanceof LexerGrammar)) && (this.syntacticPredLevel == 0) && (localRuleSymbol.block.returnAction != null)) {
/*  898 */       this.antlrTool.warning("Rule '" + paramRuleRefElement.targetRule + "' returns a value", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*      */     }
/*      */ 
/*  903 */     GenRuleInvocation(paramRuleRefElement);
/*      */ 
/*  906 */     if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramRuleRefElement.getAutoGenType() == 3))) {
/*  907 */       println("self.text.setLength(_saveIndex)");
/*      */     }
/*      */ 
/*  911 */     if (this.syntacticPredLevel == 0) {
/*  912 */       int i = (this.grammar.hasSyntacticPredicate) && (((this.grammar.buildAST) && (paramRuleRefElement.getLabel() != null)) || ((this.genAST) && (paramRuleRefElement.getAutoGenType() == 1))) ? 1 : 0;
/*      */ 
/*  918 */       if ((i == 0) || (
/*  923 */         (this.grammar.buildAST) && (paramRuleRefElement.getLabel() != null)))
/*      */       {
/*  925 */         println(paramRuleRefElement.getLabel() + "_AST = self.returnAST");
/*      */       }
/*  927 */       if (this.genAST) {
/*  928 */         switch (paramRuleRefElement.getAutoGenType()) {
/*      */         case 1:
/*  930 */           println("self.addASTChild(currentAST, self.returnAST)");
/*  931 */           break;
/*      */         case 2:
/*  933 */           this.antlrTool.error("Internal: encountered ^ after rule reference");
/*  934 */           break;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  941 */       if (((this.grammar instanceof LexerGrammar)) && (paramRuleRefElement.getLabel() != null)) {
/*  942 */         println(paramRuleRefElement.getLabel() + " = self._returnToken");
/*      */       }
/*      */ 
/*  945 */       if (i == 0);
/*      */     }
/*      */ 
/*  948 */     genErrorCatchForElement(paramRuleRefElement);
/*      */   }
/*      */ 
/*      */   public void gen(StringLiteralElement paramStringLiteralElement)
/*      */   {
/*  955 */     if (this.DEBUG_CODE_GENERATOR) System.out.println("genString(" + paramStringLiteralElement + ")");
/*      */ 
/*  958 */     if ((paramStringLiteralElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  959 */       println(paramStringLiteralElement.getLabel() + " = " + this.lt1Value + "");
/*      */     }
/*      */ 
/*  963 */     genElementAST(paramStringLiteralElement);
/*      */ 
/*  966 */     boolean bool = this.saveText;
/*  967 */     this.saveText = ((this.saveText) && (paramStringLiteralElement.getAutoGenType() == 1));
/*      */ 
/*  970 */     genMatch(paramStringLiteralElement);
/*      */ 
/*  972 */     this.saveText = bool;
/*      */ 
/*  975 */     if ((this.grammar instanceof TreeWalkerGrammar))
/*  976 */       println("_t = _t.getNextSibling()");
/*      */   }
/*      */ 
/*      */   public void gen(TokenRangeElement paramTokenRangeElement)
/*      */   {
/*  984 */     genErrorTryForElement(paramTokenRangeElement);
/*  985 */     if ((paramTokenRangeElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  986 */       println(paramTokenRangeElement.getLabel() + " = " + this.lt1Value);
/*      */     }
/*      */ 
/*  990 */     genElementAST(paramTokenRangeElement);
/*      */ 
/*  993 */     println("self.matchRange(u" + paramTokenRangeElement.beginText + ", u" + paramTokenRangeElement.endText + ")");
/*  994 */     genErrorCatchForElement(paramTokenRangeElement);
/*      */   }
/*      */ 
/*      */   public void gen(TokenRefElement paramTokenRefElement)
/*      */   {
/* 1001 */     if (this.DEBUG_CODE_GENERATOR) System.out.println("genTokenRef(" + paramTokenRefElement + ")");
/* 1002 */     if ((this.grammar instanceof LexerGrammar)) {
/* 1003 */       this.antlrTool.panic("Token reference found in lexer");
/*      */     }
/* 1005 */     genErrorTryForElement(paramTokenRefElement);
/*      */ 
/* 1007 */     if ((paramTokenRefElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/* 1008 */       println(paramTokenRefElement.getLabel() + " = " + this.lt1Value + "");
/*      */     }
/*      */ 
/* 1012 */     genElementAST(paramTokenRefElement);
/*      */ 
/* 1014 */     genMatch(paramTokenRefElement);
/* 1015 */     genErrorCatchForElement(paramTokenRefElement);
/*      */ 
/* 1018 */     if ((this.grammar instanceof TreeWalkerGrammar))
/* 1019 */       println("_t = _t.getNextSibling()");
/*      */   }
/*      */ 
/*      */   public void gen(TreeElement paramTreeElement)
/*      */   {
/* 1025 */     println("_t" + paramTreeElement.ID + " = _t");
/*      */ 
/* 1028 */     if (paramTreeElement.root.getLabel() != null) {
/* 1029 */       println(paramTreeElement.root.getLabel() + " = antlr.ifelse(_t == antlr.ASTNULL, None, _t)");
/*      */     }
/*      */ 
/* 1033 */     if (paramTreeElement.root.getAutoGenType() == 3) {
/* 1034 */       this.antlrTool.error("Suffixing a root node with '!' is not implemented", this.grammar.getFilename(), paramTreeElement.getLine(), paramTreeElement.getColumn());
/*      */ 
/* 1036 */       paramTreeElement.root.setAutoGenType(1);
/*      */     }
/* 1038 */     if (paramTreeElement.root.getAutoGenType() == 2) {
/* 1039 */       this.antlrTool.warning("Suffixing a root node with '^' is redundant; already a root", this.grammar.getFilename(), paramTreeElement.getLine(), paramTreeElement.getColumn());
/*      */ 
/* 1041 */       paramTreeElement.root.setAutoGenType(1);
/*      */     }
/*      */ 
/* 1045 */     genElementAST(paramTreeElement.root);
/* 1046 */     if (this.grammar.buildAST)
/*      */     {
/* 1048 */       println("_currentAST" + paramTreeElement.ID + " = currentAST.copy()");
/*      */ 
/* 1050 */       println("currentAST.root = currentAST.child");
/* 1051 */       println("currentAST.child = None");
/*      */     }
/*      */ 
/* 1055 */     if ((paramTreeElement.root instanceof WildcardElement)) {
/* 1056 */       println("if not _t: raise antlr.MismatchedTokenException()");
/*      */     }
/*      */     else {
/* 1059 */       genMatch(paramTreeElement.root);
/*      */     }
/*      */ 
/* 1062 */     println("_t = _t.getFirstChild()");
/*      */ 
/* 1065 */     for (int i = 0; i < paramTreeElement.getAlternatives().size(); i++) {
/* 1066 */       Alternative localAlternative = paramTreeElement.getAlternativeAt(i);
/* 1067 */       AlternativeElement localAlternativeElement = localAlternative.head;
/* 1068 */       while (localAlternativeElement != null) {
/* 1069 */         localAlternativeElement.generate();
/* 1070 */         localAlternativeElement = localAlternativeElement.next;
/*      */       }
/*      */     }
/*      */ 
/* 1074 */     if (this.grammar.buildAST)
/*      */     {
/* 1077 */       println("currentAST = _currentAST" + paramTreeElement.ID + "");
/*      */     }
/*      */ 
/* 1080 */     println("_t = _t" + paramTreeElement.ID + "");
/*      */ 
/* 1082 */     println("_t = _t.getNextSibling()");
/*      */   }
/*      */ 
/*      */   public void gen(TreeWalkerGrammar paramTreeWalkerGrammar)
/*      */     throws IOException
/*      */   {
/* 1090 */     setGrammar(paramTreeWalkerGrammar);
/* 1091 */     if (!(this.grammar instanceof TreeWalkerGrammar)) {
/* 1092 */       this.antlrTool.panic("Internal error generating tree-walker");
/*      */     }
/*      */ 
/* 1097 */     setupOutput(this.grammar.getClassName());
/*      */ 
/* 1099 */     this.genAST = this.grammar.buildAST;
/* 1100 */     this.tabs = 0;
/*      */ 
/* 1103 */     genHeader();
/*      */ 
/* 1106 */     println("### import antlr and other modules ..");
/* 1107 */     println("import sys");
/* 1108 */     println("import antlr");
/* 1109 */     println("");
/* 1110 */     println("version = sys.version.split()[0]");
/* 1111 */     println("if version < '2.2.1':");
/* 1112 */     this.tabs += 1;
/* 1113 */     println("False = 0");
/* 1114 */     this.tabs -= 1;
/* 1115 */     println("if version < '2.3':");
/* 1116 */     this.tabs += 1;
/* 1117 */     println("True = not False");
/* 1118 */     this.tabs -= 1;
/*      */ 
/* 1120 */     println("### header action >>> ");
/* 1121 */     printActionCode(this.behavior.getHeaderAction(""), 0);
/* 1122 */     println("### header action <<< ");
/*      */ 
/* 1124 */     flushTokens();
/*      */ 
/* 1126 */     println("### user code>>>");
/*      */ 
/* 1128 */     printActionCode(this.grammar.preambleAction.getText(), 0);
/* 1129 */     println("### user code<<<");
/*      */ 
/* 1132 */     String str1 = null;
/* 1133 */     if (this.grammar.superClass != null) {
/* 1134 */       str1 = this.grammar.superClass;
/*      */     }
/*      */     else {
/* 1137 */       str1 = "antlr." + this.grammar.getSuperClass();
/*      */     }
/* 1139 */     println("");
/*      */ 
/* 1142 */     Object localObject1 = "";
/* 1143 */     Token localToken = (Token)this.grammar.options.get("classHeaderPrefix");
/* 1144 */     if (localToken != null) {
/* 1145 */       localObject2 = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 1146 */       if (localObject2 != null) {
/* 1147 */         localObject1 = localObject2;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1152 */     genJavadocComment(this.grammar);
/*      */ 
/* 1154 */     println("class " + this.treeWalkerClassName + "(" + str1 + "):");
/* 1155 */     this.tabs += 1;
/*      */ 
/* 1158 */     println("");
/* 1159 */     println("# ctor ..");
/* 1160 */     println("def __init__(self, *args, **kwargs):");
/* 1161 */     this.tabs += 1;
/* 1162 */     println(str1 + ".__init__(self, *args, **kwargs)");
/* 1163 */     println("self.tokenNames = _tokenNames");
/* 1164 */     genHeaderInit(this.grammar);
/* 1165 */     this.tabs -= 1;
/* 1166 */     println("");
/*      */ 
/* 1169 */     printGrammarAction(this.grammar);
/*      */ 
/* 1172 */     Object localObject2 = this.grammar.rules.elements();
/* 1173 */     int i = 0;
/* 1174 */     String str2 = "";
/* 1175 */     while (((Enumeration)localObject2).hasMoreElements()) {
/* 1176 */       GrammarSymbol localGrammarSymbol = (GrammarSymbol)((Enumeration)localObject2).nextElement();
/* 1177 */       if ((localGrammarSymbol instanceof RuleSymbol)) {
/* 1178 */         RuleSymbol localRuleSymbol = (RuleSymbol)localGrammarSymbol;
/* 1179 */         genRule(localRuleSymbol, localRuleSymbol.references.size() == 0, i++);
/*      */       }
/* 1181 */       exitIfError();
/*      */     }
/*      */ 
/* 1185 */     genTokenStrings();
/*      */ 
/* 1188 */     genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
/*      */ 
/* 1190 */     this.tabs = 0;
/* 1191 */     genHeaderMain(this.grammar);
/*      */ 
/* 1193 */     this.currentOutput.close();
/* 1194 */     this.currentOutput = null;
/*      */   }
/*      */ 
/*      */   public void gen(WildcardElement paramWildcardElement)
/*      */   {
/* 1202 */     if ((paramWildcardElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/* 1203 */       println(paramWildcardElement.getLabel() + " = " + this.lt1Value + "");
/*      */     }
/*      */ 
/* 1207 */     genElementAST(paramWildcardElement);
/*      */ 
/* 1209 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1210 */       println("if not _t:");
/* 1211 */       this.tabs += 1;
/* 1212 */       println("raise antlr.MismatchedTokenException()");
/* 1213 */       this.tabs -= 1;
/*      */     }
/* 1215 */     else if ((this.grammar instanceof LexerGrammar)) {
/* 1216 */       if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramWildcardElement.getAutoGenType() == 3)))
/*      */       {
/* 1218 */         println("_saveIndex = self.text.length()");
/*      */       }
/* 1220 */       println("self.matchNot(antlr.EOF_CHAR)");
/* 1221 */       if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramWildcardElement.getAutoGenType() == 3)))
/*      */       {
/* 1223 */         println("self.text.setLength(_saveIndex)");
/*      */       }
/*      */     }
/*      */     else {
/* 1227 */       println("self.matchNot(" + getValueString(1, false) + ")");
/*      */     }
/*      */ 
/* 1231 */     if ((this.grammar instanceof TreeWalkerGrammar))
/* 1232 */       println("_t = _t.getNextSibling()");
/*      */   }
/*      */ 
/*      */   public void gen(ZeroOrMoreBlock paramZeroOrMoreBlock)
/*      */   {
/* 1241 */     int i = this.tabs;
/* 1242 */     genBlockPreamble(paramZeroOrMoreBlock);
/*      */ 
/* 1244 */     println("while True:");
/* 1245 */     this.tabs += 1;
/* 1246 */     i = this.tabs;
/*      */ 
/* 1249 */     genBlockInitAction(paramZeroOrMoreBlock);
/*      */ 
/* 1252 */     String str1 = this.currentASTResult;
/* 1253 */     if (paramZeroOrMoreBlock.getLabel() != null) {
/* 1254 */       this.currentASTResult = paramZeroOrMoreBlock.getLabel();
/*      */     }
/*      */ 
/* 1257 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramZeroOrMoreBlock);
/*      */ 
/* 1269 */     int j = 0;
/* 1270 */     int k = this.grammar.maxk;
/*      */ 
/* 1272 */     if ((!paramZeroOrMoreBlock.greedy) && (paramZeroOrMoreBlock.exitLookaheadDepth <= this.grammar.maxk) && (paramZeroOrMoreBlock.exitCache[paramZeroOrMoreBlock.exitLookaheadDepth].containsEpsilon()))
/*      */     {
/* 1275 */       j = 1;
/* 1276 */       k = paramZeroOrMoreBlock.exitLookaheadDepth;
/*      */     }
/* 1278 */     else if ((!paramZeroOrMoreBlock.greedy) && (paramZeroOrMoreBlock.exitLookaheadDepth == 2147483647))
/*      */     {
/* 1280 */       j = 1;
/*      */     }
/* 1282 */     if (j != 0) {
/* 1283 */       if (this.DEBUG_CODE_GENERATOR) {
/* 1284 */         System.out.println("nongreedy (...)* loop; exit depth is " + paramZeroOrMoreBlock.exitLookaheadDepth);
/*      */       }
/*      */ 
/* 1287 */       String str2 = getLookaheadTestExpression(paramZeroOrMoreBlock.exitCache, k);
/*      */ 
/* 1290 */       println("###  nongreedy exit test");
/* 1291 */       println("if (" + str2 + "):");
/* 1292 */       this.tabs += 1;
/* 1293 */       println("break");
/* 1294 */       this.tabs -= 1;
/*      */     }
/*      */ 
/* 1298 */     int m = this.tabs;
/* 1299 */     PythonBlockFinishingInfo localPythonBlockFinishingInfo = genCommonBlock(paramZeroOrMoreBlock, false);
/* 1300 */     genBlockFinish(localPythonBlockFinishingInfo, "break");
/* 1301 */     this.tabs = m;
/*      */ 
/* 1303 */     this.tabs = i;
/* 1304 */     this.tabs -= 1;
/*      */ 
/* 1307 */     this.currentASTResult = str1;
/*      */   }
/*      */ 
/*      */   protected void genAlt(Alternative paramAlternative, AlternativeBlock paramAlternativeBlock)
/*      */   {
/* 1316 */     boolean bool1 = this.genAST;
/* 1317 */     this.genAST = ((this.genAST) && (paramAlternative.getAutoGen()));
/*      */ 
/* 1319 */     boolean bool2 = this.saveText;
/* 1320 */     this.saveText = ((this.saveText) && (paramAlternative.getAutoGen()));
/*      */ 
/* 1323 */     Hashtable localHashtable = this.treeVariableMap;
/* 1324 */     this.treeVariableMap = new Hashtable();
/*      */ 
/* 1327 */     if (paramAlternative.exceptionSpec != null) {
/* 1328 */       println("try:");
/* 1329 */       this.tabs += 1;
/*      */     }
/*      */ 
/* 1332 */     println("pass");
/* 1333 */     AlternativeElement localAlternativeElement = paramAlternative.head;
/* 1334 */     while (!(localAlternativeElement instanceof BlockEndElement)) {
/* 1335 */       localAlternativeElement.generate();
/* 1336 */       localAlternativeElement = localAlternativeElement.next;
/*      */     }
/*      */ 
/* 1339 */     if (this.genAST) {
/* 1340 */       if ((paramAlternativeBlock instanceof RuleBlock)) {
/* 1342 */         RuleBlock localRuleBlock = (RuleBlock)paramAlternativeBlock;
/* 1343 */         if (this.grammar.hasSyntacticPredicate);
/* 1345 */         println(localRuleBlock.getRuleName() + "_AST = currentAST.root");
/* 1346 */         if (!this.grammar.hasSyntacticPredicate);
/*      */       }
/* 1349 */       else if (paramAlternativeBlock.getLabel() != null) {
/* 1350 */         this.antlrTool.warning("Labeled subrules not yet supported", this.grammar.getFilename(), paramAlternativeBlock.getLine(), paramAlternativeBlock.getColumn());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1356 */     if (paramAlternative.exceptionSpec != null) {
/* 1357 */       this.tabs -= 1;
/* 1358 */       genErrorHandler(paramAlternative.exceptionSpec);
/*      */     }
/*      */ 
/* 1361 */     this.genAST = bool1;
/* 1362 */     this.saveText = bool2;
/*      */ 
/* 1364 */     this.treeVariableMap = localHashtable;
/*      */   }
/*      */ 
/*      */   protected void genBitsets(Vector paramVector, int paramInt)
/*      */   {
/* 1380 */     println("");
/* 1381 */     for (int i = 0; i < paramVector.size(); i++) {
/* 1382 */       BitSet localBitSet = (BitSet)paramVector.elementAt(i);
/*      */ 
/* 1384 */       localBitSet.growToInclude(paramInt);
/* 1385 */       genBitSet(localBitSet, i);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void genBitSet(BitSet paramBitSet, int paramInt)
/*      */   {
/* 1400 */     int i = this.tabs;
/*      */ 
/* 1403 */     this.tabs = 0;
/*      */ 
/* 1405 */     println("");
/* 1406 */     println("### generate bit set");
/* 1407 */     println("def mk" + getBitsetName(paramInt) + "(): ");
/*      */ 
/* 1409 */     this.tabs += 1;
/* 1410 */     int j = paramBitSet.lengthInLongWords();
/*      */     long[] arrayOfLong;
/*      */     int k;
/* 1411 */     if (j < 8)
/*      */     {
/* 1413 */       println("### var1");
/* 1414 */       println("data = [ " + paramBitSet.toStringOfWords() + "]");
/*      */     }
/*      */     else
/*      */     {
/* 1419 */       println("data = [0L] * " + j + " ### init list");
/*      */ 
/* 1421 */       arrayOfLong = paramBitSet.toPackedArray();
/*      */ 
/* 1423 */       for (k = 0; k < arrayOfLong.length; )
/*      */       {
/* 1425 */         if (arrayOfLong[k] == 0L)
/*      */         {
/* 1428 */           k++;
/*      */         }
/* 1432 */         else if ((k + 1 == arrayOfLong.length) || (arrayOfLong[k] != arrayOfLong[(k + 1)]))
/*      */         {
/* 1435 */           println("data[" + k + "] =" + arrayOfLong[k] + "L");
/* 1436 */           k++;
/*      */         }
/*      */         else
/*      */         {
/* 1442 */           for (int m = k + 1; (m < arrayOfLong.length) && (arrayOfLong[m] == arrayOfLong[k]); m++);
/* 1445 */           long l = arrayOfLong[k];
/*      */ 
/* 1447 */           println("for x in xrange(" + k + ", " + m + "):");
/* 1448 */           this.tabs += 1;
/* 1449 */           println("data[x] = " + l + "L");
/* 1450 */           this.tabs -= 1;
/* 1451 */           k = m;
/*      */         }
/*      */       }
/*      */     }
/* 1455 */     println("return data");
/* 1456 */     this.tabs -= 1;
/*      */ 
/* 1459 */     println(getBitsetName(paramInt) + " = antlr.BitSet(mk" + getBitsetName(paramInt) + "())");
/*      */ 
/* 1463 */     this.tabs = i;
/*      */   }
/*      */ 
/*      */   private void genBlockFinish(PythonBlockFinishingInfo paramPythonBlockFinishingInfo, String paramString)
/*      */   {
/* 1468 */     if ((paramPythonBlockFinishingInfo.needAnErrorClause) && ((paramPythonBlockFinishingInfo.generatedAnIf) || (paramPythonBlockFinishingInfo.generatedSwitch)))
/*      */     {
/* 1470 */       if (paramPythonBlockFinishingInfo.generatedAnIf)
/*      */       {
/* 1472 */         println("else:");
/*      */       }
/* 1474 */       this.tabs += 1;
/* 1475 */       println(paramString);
/* 1476 */       this.tabs -= 1;
/*      */     }
/*      */ 
/* 1479 */     if (paramPythonBlockFinishingInfo.postscript != null)
/* 1480 */       println(paramPythonBlockFinishingInfo.postscript);
/*      */   }
/*      */ 
/*      */   private void genBlockFinish1(PythonBlockFinishingInfo paramPythonBlockFinishingInfo, String paramString)
/*      */   {
/* 1487 */     if ((paramPythonBlockFinishingInfo.needAnErrorClause) && ((paramPythonBlockFinishingInfo.generatedAnIf) || (paramPythonBlockFinishingInfo.generatedSwitch)))
/*      */     {
/* 1490 */       if (paramPythonBlockFinishingInfo.generatedAnIf)
/*      */       {
/* 1493 */         println("else:");
/*      */       }
/*      */ 
/* 1495 */       this.tabs += 1;
/* 1496 */       println(paramString);
/* 1497 */       this.tabs -= 1;
/* 1498 */       if (!paramPythonBlockFinishingInfo.generatedAnIf);
/*      */     }
/*      */ 
/* 1505 */     if (paramPythonBlockFinishingInfo.postscript != null)
/* 1506 */       println(paramPythonBlockFinishingInfo.postscript);
/*      */   }
/*      */ 
/*      */   protected void genBlockInitAction(AlternativeBlock paramAlternativeBlock)
/*      */   {
/* 1516 */     if (paramAlternativeBlock.initAction != null)
/* 1517 */       printAction(processActionForSpecialSymbols(paramAlternativeBlock.initAction, paramAlternativeBlock.getLine(), this.currentRule, null));
/*      */   }
/*      */ 
/*      */   protected void genBlockPreamble(AlternativeBlock paramAlternativeBlock)
/*      */   {
/* 1528 */     if ((paramAlternativeBlock instanceof RuleBlock)) {
/* 1529 */       RuleBlock localRuleBlock = (RuleBlock)paramAlternativeBlock;
/* 1530 */       if (localRuleBlock.labeledElements != null)
/* 1531 */         for (int i = 0; i < localRuleBlock.labeledElements.size(); i++) {
/* 1532 */           AlternativeElement localAlternativeElement = (AlternativeElement)localRuleBlock.labeledElements.elementAt(i);
/*      */ 
/* 1539 */           if (((localAlternativeElement instanceof RuleRefElement)) || (((localAlternativeElement instanceof AlternativeBlock)) && (!(localAlternativeElement instanceof RuleBlock)) && (!(localAlternativeElement instanceof SynPredBlock))))
/*      */           {
/* 1546 */             if ((!(localAlternativeElement instanceof RuleRefElement)) && (((AlternativeBlock)localAlternativeElement).not) && (this.analyzer.subruleCanBeInverted((AlternativeBlock)localAlternativeElement, this.grammar instanceof LexerGrammar)))
/*      */             {
/* 1554 */               println(localAlternativeElement.getLabel() + " = " + this.labeledElementInit);
/* 1555 */               if (this.grammar.buildAST)
/* 1556 */                 genASTDeclaration(localAlternativeElement);
/*      */             }
/*      */             else
/*      */             {
/* 1560 */               if (this.grammar.buildAST)
/*      */               {
/* 1564 */                 genASTDeclaration(localAlternativeElement);
/*      */               }
/* 1566 */               if ((this.grammar instanceof LexerGrammar)) {
/* 1567 */                 println(localAlternativeElement.getLabel() + " = None");
/*      */               }
/* 1569 */               if ((this.grammar instanceof TreeWalkerGrammar))
/*      */               {
/* 1572 */                 println(localAlternativeElement.getLabel() + " = " + this.labeledElementInit);
/*      */               }
/*      */             }
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 1579 */             println(localAlternativeElement.getLabel() + " = " + this.labeledElementInit);
/*      */ 
/* 1583 */             if (this.grammar.buildAST)
/* 1584 */               if (((localAlternativeElement instanceof GrammarAtom)) && (((GrammarAtom)localAlternativeElement).getASTNodeType() != null))
/*      */               {
/* 1586 */                 GrammarAtom localGrammarAtom = (GrammarAtom)localAlternativeElement;
/* 1587 */                 genASTDeclaration(localAlternativeElement, localGrammarAtom.getASTNodeType());
/*      */               }
/*      */               else {
/* 1590 */                 genASTDeclaration(localAlternativeElement);
/*      */               }
/*      */           }
/*      */         }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void genCases(BitSet paramBitSet)
/*      */   {
/* 1603 */     if (this.DEBUG_CODE_GENERATOR) System.out.println("genCases(" + paramBitSet + ")");
/*      */ 
/* 1606 */     int[] arrayOfInt = paramBitSet.toArray();
/*      */ 
/* 1608 */     int i = (this.grammar instanceof LexerGrammar) ? 4 : 1;
/* 1609 */     int j = 1;
/* 1610 */     int k = 1;
/* 1611 */     print("elif la1 and la1 in ");
/*      */ 
/* 1613 */     if ((this.grammar instanceof LexerGrammar))
/*      */     {
/* 1615 */       _print("u'");
/* 1616 */       for (m = 0; m < arrayOfInt.length; m++) {
/* 1617 */         _print(getValueString(arrayOfInt[m], false));
/*      */       }
/* 1619 */       _print("':\n");
/* 1620 */       return;
/*      */     }
/*      */ 
/* 1624 */     _print("[");
/* 1625 */     for (int m = 0; m < arrayOfInt.length; m++) {
/* 1626 */       _print(getValueString(arrayOfInt[m], false));
/* 1627 */       if (m + 1 < arrayOfInt.length)
/* 1628 */         _print(",");
/*      */     }
/* 1630 */     _print("]:\n");
/*      */   }
/*      */ 
/*      */   public PythonBlockFinishingInfo genCommonBlock(AlternativeBlock paramAlternativeBlock, boolean paramBoolean)
/*      */   {
/* 1646 */     int i = this.tabs;
/* 1647 */     int j = 0;
/* 1648 */     int k = 0;
/* 1649 */     int m = 0;
/*      */ 
/* 1651 */     PythonBlockFinishingInfo localPythonBlockFinishingInfo = new PythonBlockFinishingInfo();
/*      */ 
/* 1655 */     boolean bool1 = this.genAST;
/* 1656 */     this.genAST = ((this.genAST) && (paramAlternativeBlock.getAutoGen()));
/*      */ 
/* 1658 */     boolean bool2 = this.saveText;
/* 1659 */     this.saveText = ((this.saveText) && (paramAlternativeBlock.getAutoGen()));
/*      */     Object localObject1;
/* 1662 */     if ((paramAlternativeBlock.not) && (this.analyzer.subruleCanBeInverted(paramAlternativeBlock, this.grammar instanceof LexerGrammar)))
/*      */     {
/* 1667 */       if (this.DEBUG_CODE_GENERATOR) System.out.println("special case: ~(subrule)");
/* 1668 */       localObject1 = this.analyzer.look(1, paramAlternativeBlock);
/*      */ 
/* 1670 */       if ((paramAlternativeBlock.getLabel() != null) && (this.syntacticPredLevel == 0))
/*      */       {
/* 1672 */         println(paramAlternativeBlock.getLabel() + " = " + this.lt1Value);
/*      */       }
/*      */ 
/* 1676 */       genElementAST(paramAlternativeBlock);
/*      */ 
/* 1678 */       String str1 = "";
/* 1679 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1680 */         str1 = "_t, ";
/*      */       }
/*      */ 
/* 1684 */       println("self.match(" + str1 + getBitsetName(markBitsetForGen(((Lookahead)localObject1).fset)) + ")");
/*      */ 
/* 1687 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1688 */         println("_t = _t.getNextSibling()");
/*      */       }
/* 1690 */       return localPythonBlockFinishingInfo;
/*      */     }
/*      */ 
/* 1695 */     if (paramAlternativeBlock.getAlternatives().size() == 1)
/*      */     {
/* 1697 */       localObject1 = paramAlternativeBlock.getAlternativeAt(0);
/*      */ 
/* 1699 */       if (((Alternative)localObject1).synPred != null) {
/* 1700 */         this.antlrTool.warning("Syntactic predicate superfluous for single alternative", this.grammar.getFilename(), paramAlternativeBlock.getAlternativeAt(0).synPred.getLine(), paramAlternativeBlock.getAlternativeAt(0).synPred.getColumn());
/*      */       }
/*      */ 
/* 1707 */       if (paramBoolean)
/*      */       {
/* 1709 */         if (((Alternative)localObject1).semPred != null)
/*      */         {
/* 1711 */           genSemPred(((Alternative)localObject1).semPred, paramAlternativeBlock.line);
/*      */         }
/* 1713 */         genAlt((Alternative)localObject1, paramAlternativeBlock);
/* 1714 */         return localPythonBlockFinishingInfo;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1727 */     int n = 0;
/* 1728 */     for (int i1 = 0; i1 < paramAlternativeBlock.getAlternatives().size(); i1++) {
/* 1729 */       Alternative localAlternative1 = paramAlternativeBlock.getAlternativeAt(i1);
/* 1730 */       if (suitableForCaseExpression(localAlternative1))
/* 1731 */         n++;
/*      */     }
/*      */     Object localObject2;
/* 1736 */     if (n >= this.makeSwitchThreshold)
/*      */     {
/* 1739 */       String str2 = lookaheadString(1);
/* 1740 */       k = 1;
/*      */ 
/* 1742 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1743 */         println("if not _t:");
/* 1744 */         this.tabs += 1;
/* 1745 */         println("_t = antlr.ASTNULL");
/* 1746 */         this.tabs -= 1;
/*      */       }
/*      */ 
/* 1749 */       println("la1 = " + str2);
/*      */ 
/* 1751 */       println("if False:");
/* 1752 */       this.tabs += 1;
/* 1753 */       println("pass");
/*      */ 
/* 1755 */       this.tabs -= 1;
/*      */ 
/* 1757 */       for (i3 = 0; i3 < paramAlternativeBlock.alternatives.size(); i3++)
/*      */       {
/* 1759 */         Alternative localAlternative2 = paramAlternativeBlock.getAlternativeAt(i3);
/*      */ 
/* 1762 */         if (suitableForCaseExpression(localAlternative2))
/*      */         {
/* 1765 */           localObject2 = localAlternative2.cache[1];
/* 1766 */           if ((((Lookahead)localObject2).fset.degree() == 0) && (!((Lookahead)localObject2).containsEpsilon()))
/*      */           {
/* 1768 */             this.antlrTool.warning("Alternate omitted due to empty prediction set", this.grammar.getFilename(), localAlternative2.head.getLine(), localAlternative2.head.getColumn());
/*      */           }
/*      */           else
/*      */           {
/* 1776 */             genCases(((Lookahead)localObject2).fset);
/* 1777 */             this.tabs += 1;
/* 1778 */             genAlt(localAlternative2, paramAlternativeBlock);
/* 1779 */             this.tabs -= 1;
/*      */           }
/*      */         }
/*      */       }
/* 1783 */       println("else:");
/* 1784 */       this.tabs += 1;
/*      */     }
/*      */ 
/* 1800 */     int i2 = (this.grammar instanceof LexerGrammar) ? this.grammar.maxk : 0;
/* 1801 */     for (int i3 = i2; i3 >= 0; i3--)
/*      */     {
/* 1803 */       for (int i4 = 0; i4 < paramAlternativeBlock.alternatives.size(); i4++)
/*      */       {
/* 1805 */         localObject2 = paramAlternativeBlock.getAlternativeAt(i4);
/* 1806 */         if (this.DEBUG_CODE_GENERATOR) System.out.println("genAlt: " + i4);
/*      */ 
/* 1811 */         if ((k != 0) && (suitableForCaseExpression((Alternative)localObject2))) {
/* 1812 */           if (this.DEBUG_CODE_GENERATOR) {
/* 1813 */             System.out.println("ignoring alt because it was in the switch");
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 1818 */           boolean bool3 = false;
/*      */           String str4;
/* 1820 */           if ((this.grammar instanceof LexerGrammar))
/*      */           {
/* 1825 */             int i5 = ((Alternative)localObject2).lookaheadDepth;
/* 1826 */             if (i5 == 2147483647)
/*      */             {
/* 1828 */               i5 = this.grammar.maxk;
/*      */             }
/* 1830 */             while ((i5 >= 1) && (localObject2.cache[i5].containsEpsilon()))
/*      */             {
/* 1832 */               i5--;
/*      */             }
/*      */ 
/* 1836 */             if (i5 != i3) {
/* 1837 */               if (!this.DEBUG_CODE_GENERATOR) continue;
/* 1838 */               System.out.println("ignoring alt because effectiveDepth!=altDepth" + i5 + "!=" + i3); continue;
/*      */             }
/*      */ 
/* 1844 */             bool3 = lookaheadIsEmpty((Alternative)localObject2, i5);
/* 1845 */             str4 = getLookaheadTestExpression((Alternative)localObject2, i5);
/*      */           }
/*      */           else
/*      */           {
/* 1850 */             bool3 = lookaheadIsEmpty((Alternative)localObject2, this.grammar.maxk);
/* 1851 */             str4 = getLookaheadTestExpression((Alternative)localObject2, this.grammar.maxk);
/*      */           }
/*      */ 
/* 1856 */           if ((localObject2.cache[1].fset.degree() > 127) && (suitableForCaseExpression((Alternative)localObject2)))
/*      */           {
/* 1858 */             if (j == 0) {
/* 1859 */               println("<m1> if " + str4 + ":");
/*      */             }
/*      */             else {
/* 1862 */               println("<m2> elif " + str4 + ":");
/*      */             }
/*      */ 
/*      */           }
/* 1867 */           else if ((bool3) && (((Alternative)localObject2).semPred == null) && (((Alternative)localObject2).synPred == null))
/*      */           {
/* 1875 */             if (j == 0) {
/* 1876 */               println("##<m3> <closing");
/*      */             }
/*      */             else
/*      */             {
/* 1880 */               println("else: ## <m4>");
/* 1881 */               this.tabs += 1;
/*      */             }
/*      */ 
/* 1885 */             localPythonBlockFinishingInfo.needAnErrorClause = false;
/*      */           }
/*      */           else
/*      */           {
/* 1893 */             if (((Alternative)localObject2).semPred != null)
/*      */             {
/* 1898 */               ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 1899 */               String str5 = processActionForSpecialSymbols(((Alternative)localObject2).semPred, paramAlternativeBlock.line, this.currentRule, localActionTransInfo);
/*      */ 
/* 1908 */               if ((((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar))) && (this.grammar.debuggingOutput))
/*      */               {
/* 1912 */                 str4 = "(" + str4 + " and fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEvent.PREDICTING, " + addSemPred(this.charFormatter.escapeString(str5)) + ", " + str5 + "))";
/*      */               }
/*      */               else
/*      */               {
/* 1918 */                 str4 = "(" + str4 + " and (" + str5 + "))";
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/* 1923 */             if (j > 0)
/*      */             {
/* 1925 */               if (((Alternative)localObject2).synPred != null)
/*      */               {
/* 1927 */                 println("else:");
/* 1928 */                 this.tabs += 1;
/* 1929 */                 genSynPred(((Alternative)localObject2).synPred, str4);
/* 1930 */                 m++;
/*      */               }
/*      */               else
/*      */               {
/* 1934 */                 println("elif " + str4 + ":");
/*      */               }
/*      */ 
/*      */             }
/* 1939 */             else if (((Alternative)localObject2).synPred != null)
/*      */             {
/* 1941 */               genSynPred(((Alternative)localObject2).synPred, str4);
/*      */             }
/*      */             else
/*      */             {
/* 1947 */               if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1948 */                 println("if not _t:");
/* 1949 */                 this.tabs += 1;
/* 1950 */                 println("_t = antlr.ASTNULL");
/* 1951 */                 this.tabs -= 1;
/*      */               }
/* 1953 */               println("if " + str4 + ":");
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 1959 */           j++;
/* 1960 */           this.tabs += 1;
/* 1961 */           genAlt((Alternative)localObject2, paramAlternativeBlock);
/*      */ 
/* 1963 */           this.tabs -= 1;
/*      */         }
/*      */       }
/*      */     }
/* 1966 */     String str3 = "";
/*      */ 
/* 1972 */     this.genAST = bool1;
/*      */ 
/* 1975 */     this.saveText = bool2;
/*      */ 
/* 1978 */     if (k != 0) {
/* 1979 */       localPythonBlockFinishingInfo.postscript = str3;
/* 1980 */       localPythonBlockFinishingInfo.generatedSwitch = true;
/* 1981 */       localPythonBlockFinishingInfo.generatedAnIf = (j > 0);
/*      */     }
/*      */     else {
/* 1984 */       localPythonBlockFinishingInfo.postscript = str3;
/* 1985 */       localPythonBlockFinishingInfo.generatedSwitch = false;
/* 1986 */       localPythonBlockFinishingInfo.generatedAnIf = (j > 0);
/*      */     }
/*      */ 
/* 1989 */     return localPythonBlockFinishingInfo;
/*      */   }
/*      */ 
/*      */   private static boolean suitableForCaseExpression(Alternative paramAlternative) {
/* 1993 */     return (paramAlternative.lookaheadDepth == 1) && (paramAlternative.semPred == null) && (!paramAlternative.cache[1].containsEpsilon()) && (paramAlternative.cache[1].fset.degree() <= 127);
/*      */   }
/*      */ 
/*      */   private void genElementAST(AlternativeElement paramAlternativeElement)
/*      */   {
/* 2004 */     if (((this.grammar instanceof TreeWalkerGrammar)) && (!this.grammar.buildAST))
/*      */     {
/* 2009 */       if (paramAlternativeElement.getLabel() == null) {
/* 2010 */         String str1 = this.lt1Value;
/*      */ 
/* 2012 */         String str2 = "tmp" + this.astVarNumber + "_AST";
/* 2013 */         this.astVarNumber += 1;
/*      */ 
/* 2015 */         mapTreeVariable(paramAlternativeElement, str2);
/*      */ 
/* 2017 */         println(str2 + "_in = " + str1);
/*      */       }
/* 2019 */       return;
/*      */     }
/*      */ 
/* 2022 */     if ((this.grammar.buildAST) && (this.syntacticPredLevel == 0)) {
/* 2023 */       int i = (this.genAST) && ((paramAlternativeElement.getLabel() != null) || (paramAlternativeElement.getAutoGenType() != 3)) ? 1 : 0;
/*      */ 
/* 2034 */       if ((paramAlternativeElement.getAutoGenType() != 3) && ((paramAlternativeElement instanceof TokenRefElement)))
/*      */       {
/* 2037 */         i = 1;
/*      */       }
/*      */ 
/* 2040 */       int j = (this.grammar.hasSyntacticPredicate) && (i != 0) ? 1 : 0;
/*      */       String str3;
/*      */       String str4;
/* 2047 */       if (paramAlternativeElement.getLabel() != null) {
/* 2048 */         str3 = paramAlternativeElement.getLabel();
/* 2049 */         str4 = paramAlternativeElement.getLabel();
/*      */       }
/*      */       else {
/* 2052 */         str3 = this.lt1Value;
/*      */ 
/* 2054 */         str4 = "tmp" + this.astVarNumber;
/*      */ 
/* 2056 */         this.astVarNumber += 1;
/*      */       }
/*      */ 
/* 2060 */       if (i != 0)
/*      */       {
/* 2062 */         if ((paramAlternativeElement instanceof GrammarAtom)) {
/* 2063 */           localObject = (GrammarAtom)paramAlternativeElement;
/* 2064 */           if (((GrammarAtom)localObject).getASTNodeType() != null) {
/* 2065 */             genASTDeclaration(paramAlternativeElement, str4, ((GrammarAtom)localObject).getASTNodeType());
/*      */           }
/*      */           else
/* 2068 */             genASTDeclaration(paramAlternativeElement, str4, this.labeledElementASTType);
/*      */         }
/*      */         else
/*      */         {
/* 2072 */           genASTDeclaration(paramAlternativeElement, str4, this.labeledElementASTType);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2077 */       Object localObject = str4 + "_AST";
/*      */ 
/* 2080 */       mapTreeVariable(paramAlternativeElement, (String)localObject);
/* 2081 */       if ((this.grammar instanceof TreeWalkerGrammar))
/*      */       {
/* 2083 */         println((String)localObject + "_in = None");
/*      */       }
/*      */ 
/* 2087 */       if ((j == 0) || 
/* 2094 */         (paramAlternativeElement.getLabel() != null)) {
/* 2095 */         if ((paramAlternativeElement instanceof GrammarAtom)) {
/* 2096 */           println((String)localObject + " = " + getASTCreateString((GrammarAtom)paramAlternativeElement, str3) + "");
/*      */         }
/*      */         else {
/* 2099 */           println((String)localObject + " = " + getASTCreateString(str3) + "");
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2104 */       if ((paramAlternativeElement.getLabel() == null) && (i != 0)) {
/* 2105 */         str3 = this.lt1Value;
/* 2106 */         if ((paramAlternativeElement instanceof GrammarAtom)) {
/* 2107 */           println((String)localObject + " = " + getASTCreateString((GrammarAtom)paramAlternativeElement, str3) + "");
/*      */         }
/*      */         else {
/* 2110 */           println((String)localObject + " = " + getASTCreateString(str3) + "");
/*      */         }
/*      */ 
/* 2113 */         if ((this.grammar instanceof TreeWalkerGrammar))
/*      */         {
/* 2115 */           println((String)localObject + "_in = " + str3 + "");
/*      */         }
/*      */       }
/*      */ 
/* 2119 */       if (this.genAST) {
/* 2120 */         switch (paramAlternativeElement.getAutoGenType()) {
/*      */         case 1:
/* 2122 */           println("self.addASTChild(currentAST, " + (String)localObject + ")");
/* 2123 */           break;
/*      */         case 2:
/* 2125 */           println("self.makeASTRoot(currentAST, " + (String)localObject + ")");
/* 2126 */           break;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2131 */       if (j == 0);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void genErrorCatchForElement(AlternativeElement paramAlternativeElement)
/*      */   {
/* 2141 */     if (paramAlternativeElement.getLabel() == null) return;
/* 2142 */     String str = paramAlternativeElement.enclosingRuleName;
/* 2143 */     if ((this.grammar instanceof LexerGrammar)) {
/* 2144 */       str = CodeGenerator.encodeLexerRuleName(paramAlternativeElement.enclosingRuleName);
/*      */     }
/* 2146 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(str);
/* 2147 */     if (localRuleSymbol == null) {
/* 2148 */       this.antlrTool.panic("Enclosing rule not found!");
/*      */     }
/* 2150 */     ExceptionSpec localExceptionSpec = localRuleSymbol.block.findExceptionSpec(paramAlternativeElement.getLabel());
/* 2151 */     if (localExceptionSpec != null) {
/* 2152 */       this.tabs -= 1;
/* 2153 */       genErrorHandler(localExceptionSpec);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void genErrorHandler(ExceptionSpec paramExceptionSpec)
/*      */   {
/* 2160 */     for (int i = 0; i < paramExceptionSpec.handlers.size(); i++) {
/* 2161 */       ExceptionHandler localExceptionHandler = (ExceptionHandler)paramExceptionSpec.handlers.elementAt(i);
/*      */ 
/* 2163 */       String str1 = ""; String str2 = "";
/* 2164 */       String str3 = localExceptionHandler.exceptionTypeAndName.getText();
/* 2165 */       str3 = removeAssignmentFromDeclaration(str3);
/* 2166 */       str3 = str3.trim();
/*      */ 
/* 2169 */       for (int j = str3.length() - 1; j >= 0; j--)
/*      */       {
/* 2171 */         if ((!Character.isLetterOrDigit(str3.charAt(j))) && (str3.charAt(j) != '_'))
/*      */         {
/* 2174 */           str1 = str3.substring(0, j);
/* 2175 */           str2 = str3.substring(j + 1);
/* 2176 */           break;
/*      */         }
/*      */       }
/*      */ 
/* 2180 */       println("except " + str1 + ", " + str2 + ":");
/* 2181 */       this.tabs += 1;
/* 2182 */       if (this.grammar.hasSyntacticPredicate) {
/* 2183 */         println("if not self.inputState.guessing:");
/* 2184 */         this.tabs += 1;
/*      */       }
/*      */ 
/* 2188 */       ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 2189 */       printAction(processActionForSpecialSymbols(localExceptionHandler.action.getText(), localExceptionHandler.action.getLine(), this.currentRule, localActionTransInfo));
/*      */ 
/* 2195 */       if (this.grammar.hasSyntacticPredicate) {
/* 2196 */         this.tabs -= 1;
/* 2197 */         println("else:");
/* 2198 */         this.tabs += 1;
/*      */ 
/* 2200 */         println("raise " + str2);
/* 2201 */         this.tabs -= 1;
/*      */       }
/*      */ 
/* 2204 */       this.tabs -= 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void genErrorTryForElement(AlternativeElement paramAlternativeElement)
/*      */   {
/* 2210 */     if (paramAlternativeElement.getLabel() == null) return;
/* 2211 */     String str = paramAlternativeElement.enclosingRuleName;
/* 2212 */     if ((this.grammar instanceof LexerGrammar)) {
/* 2213 */       str = CodeGenerator.encodeLexerRuleName(paramAlternativeElement.enclosingRuleName);
/*      */     }
/* 2215 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(str);
/* 2216 */     if (localRuleSymbol == null) {
/* 2217 */       this.antlrTool.panic("Enclosing rule not found!");
/*      */     }
/* 2219 */     ExceptionSpec localExceptionSpec = localRuleSymbol.block.findExceptionSpec(paramAlternativeElement.getLabel());
/* 2220 */     if (localExceptionSpec != null) {
/* 2221 */       println("try: # for error handling");
/* 2222 */       this.tabs += 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void genASTDeclaration(AlternativeElement paramAlternativeElement) {
/* 2227 */     genASTDeclaration(paramAlternativeElement, this.labeledElementASTType);
/*      */   }
/*      */ 
/*      */   protected void genASTDeclaration(AlternativeElement paramAlternativeElement, String paramString) {
/* 2231 */     genASTDeclaration(paramAlternativeElement, paramAlternativeElement.getLabel(), paramString);
/*      */   }
/*      */ 
/*      */   protected void genASTDeclaration(AlternativeElement paramAlternativeElement, String paramString1, String paramString2)
/*      */   {
/* 2236 */     if (this.declaredASTVariables.contains(paramAlternativeElement)) {
/* 2237 */       return;
/*      */     }
/*      */ 
/* 2240 */     println(paramString1 + "_AST = None");
/*      */ 
/* 2243 */     this.declaredASTVariables.put(paramAlternativeElement, paramAlternativeElement);
/*      */   }
/*      */ 
/*      */   protected void genHeader()
/*      */   {
/* 2248 */     println("### $ANTLR " + Tool.version + ": " + "\"" + this.antlrTool.fileMinusPath(this.antlrTool.grammarFile) + "\"" + " -> " + "\"" + this.grammar.getClassName() + ".py\"$");
/*      */   }
/*      */ 
/*      */   protected void genLexerTest()
/*      */   {
/* 2264 */     String str = this.grammar.getClassName();
/* 2265 */     println("if __name__ == '__main__' :");
/* 2266 */     this.tabs += 1;
/* 2267 */     println("import sys");
/* 2268 */     println("import antlr");
/* 2269 */     println("import " + str);
/* 2270 */     println("");
/* 2271 */     println("### create lexer - shall read from stdin");
/* 2272 */     println("try:");
/* 2273 */     this.tabs += 1;
/* 2274 */     println("for token in " + str + ".Lexer():");
/* 2275 */     this.tabs += 1;
/* 2276 */     println("print token");
/* 2277 */     println("");
/* 2278 */     this.tabs -= 1;
/* 2279 */     this.tabs -= 1;
/* 2280 */     println("except antlr.TokenStreamException, e:");
/* 2281 */     this.tabs += 1;
/* 2282 */     println("print \"error: exception caught while lexing: \", e");
/* 2283 */     this.tabs -= 1;
/* 2284 */     this.tabs -= 1;
/*      */   }
/*      */ 
/*      */   private void genLiteralsTest()
/*      */   {
/* 2289 */     println("### option { testLiterals=true } ");
/* 2290 */     println("_ttype = self.testLiteralsTable(_ttype)");
/*      */   }
/*      */ 
/*      */   private void genLiteralsTestForPartialToken() {
/* 2294 */     println("_ttype = self.testLiteralsTable(self.text.getString(), _begin, self.text.length()-_begin, _ttype)");
/*      */   }
/*      */ 
/*      */   protected void genMatch(BitSet paramBitSet) {
/*      */   }
/*      */ 
/*      */   protected void genMatch(GrammarAtom paramGrammarAtom) {
/* 2301 */     if ((paramGrammarAtom instanceof StringLiteralElement)) {
/* 2302 */       if ((this.grammar instanceof LexerGrammar)) {
/* 2303 */         genMatchUsingAtomText(paramGrammarAtom);
/*      */       }
/*      */       else {
/* 2306 */         genMatchUsingAtomTokenType(paramGrammarAtom);
/*      */       }
/*      */     }
/* 2309 */     else if ((paramGrammarAtom instanceof CharLiteralElement)) {
/* 2310 */       if ((this.grammar instanceof LexerGrammar)) {
/* 2311 */         genMatchUsingAtomText(paramGrammarAtom);
/*      */       }
/*      */       else {
/* 2314 */         this.antlrTool.error("cannot ref character literals in grammar: " + paramGrammarAtom);
/*      */       }
/*      */     }
/* 2317 */     else if ((paramGrammarAtom instanceof TokenRefElement)) {
/* 2318 */       genMatchUsingAtomText(paramGrammarAtom);
/*      */     }
/* 2320 */     else if ((paramGrammarAtom instanceof WildcardElement))
/* 2321 */       gen((WildcardElement)paramGrammarAtom);
/*      */   }
/*      */ 
/*      */   protected void genMatchUsingAtomText(GrammarAtom paramGrammarAtom)
/*      */   {
/* 2327 */     String str = "";
/* 2328 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2329 */       str = "_t,";
/*      */     }
/*      */ 
/* 2333 */     if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramGrammarAtom.getAutoGenType() == 3)))
/*      */     {
/* 2336 */       println("_saveIndex = self.text.length()");
/*      */     }
/*      */ 
/* 2340 */     print(paramGrammarAtom.not ? "self.matchNot(" : "self.match(");
/* 2341 */     _print(str);
/*      */ 
/* 2344 */     if (paramGrammarAtom.atomText.equals("EOF"))
/*      */     {
/* 2346 */       _print("EOF_TYPE");
/*      */     }
/*      */     else {
/* 2349 */       _print(paramGrammarAtom.atomText);
/*      */     }
/* 2351 */     _println(")");
/*      */ 
/* 2353 */     if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramGrammarAtom.getAutoGenType() == 3)))
/* 2354 */       println("self.text.setLength(_saveIndex)");
/*      */   }
/*      */ 
/*      */   protected void genMatchUsingAtomTokenType(GrammarAtom paramGrammarAtom)
/*      */   {
/* 2360 */     String str1 = "";
/* 2361 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2362 */       str1 = "_t,";
/*      */     }
/*      */ 
/* 2366 */     Object localObject = null;
/* 2367 */     String str2 = str1 + getValueString(paramGrammarAtom.getType(), true);
/*      */ 
/* 2370 */     println((paramGrammarAtom.not ? "self.matchNot(" : "self.match(") + str2 + ")");
/*      */   }
/*      */ 
/*      */   public void genNextToken()
/*      */   {
/* 2381 */     int i = 0;
/* 2382 */     for (int j = 0; j < this.grammar.rules.size(); j++) {
/* 2383 */       localRuleSymbol1 = (RuleSymbol)this.grammar.rules.elementAt(j);
/* 2384 */       if ((localRuleSymbol1.isDefined()) && (localRuleSymbol1.access.equals("public"))) {
/* 2385 */         i = 1;
/* 2386 */         break;
/*      */       }
/*      */     }
/* 2389 */     if (i == 0) {
/* 2390 */       println("");
/* 2391 */       println("def nextToken(self): ");
/* 2392 */       this.tabs += 1;
/* 2393 */       println("try:");
/* 2394 */       this.tabs += 1;
/* 2395 */       println("self.uponEOF()");
/* 2396 */       this.tabs -= 1;
/* 2397 */       println("except antlr.CharStreamIOException, csioe:");
/* 2398 */       this.tabs += 1;
/* 2399 */       println("raise antlr.TokenStreamIOException(csioe.io)");
/* 2400 */       this.tabs -= 1;
/* 2401 */       println("except antlr.CharStreamException, cse:");
/* 2402 */       this.tabs += 1;
/* 2403 */       println("raise antlr.TokenStreamException(str(cse))");
/* 2404 */       this.tabs -= 1;
/* 2405 */       println("return antlr.CommonToken(type=EOF_TYPE, text=\"\")");
/* 2406 */       this.tabs -= 1;
/* 2407 */       return;
/*      */     }
/*      */ 
/* 2411 */     RuleBlock localRuleBlock = MakeGrammar.createNextTokenRule(this.grammar, this.grammar.rules, "nextToken");
/*      */ 
/* 2415 */     RuleSymbol localRuleSymbol1 = new RuleSymbol("mnextToken");
/* 2416 */     localRuleSymbol1.setDefined();
/* 2417 */     localRuleSymbol1.setBlock(localRuleBlock);
/* 2418 */     localRuleSymbol1.access = "private";
/* 2419 */     this.grammar.define(localRuleSymbol1);
/*      */ 
/* 2421 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(localRuleBlock);
/*      */ 
/* 2424 */     String str1 = null;
/* 2425 */     if (((LexerGrammar)this.grammar).filterMode) {
/* 2426 */       str1 = ((LexerGrammar)this.grammar).filterRule;
/*      */     }
/*      */ 
/* 2429 */     println("");
/* 2430 */     println("def nextToken(self):");
/* 2431 */     this.tabs += 1;
/* 2432 */     println("while True:");
/* 2433 */     this.tabs += 1;
/* 2434 */     println("try: ### try again ..");
/* 2435 */     this.tabs += 1;
/* 2436 */     println("while True:");
/* 2437 */     this.tabs += 1;
/* 2438 */     int k = this.tabs;
/* 2439 */     println("_token = None");
/* 2440 */     println("_ttype = INVALID_TYPE");
/* 2441 */     if (((LexerGrammar)this.grammar).filterMode)
/*      */     {
/* 2443 */       println("self.setCommitToPath(False)");
/* 2444 */       if (str1 != null)
/*      */       {
/* 2447 */         if (!this.grammar.isDefined(CodeGenerator.encodeLexerRuleName(str1))) {
/* 2448 */           this.grammar.antlrTool.error("Filter rule " + str1 + " does not exist in this lexer");
/*      */         }
/*      */         else
/*      */         {
/* 2453 */           RuleSymbol localRuleSymbol2 = (RuleSymbol)this.grammar.getSymbol(CodeGenerator.encodeLexerRuleName(str1));
/*      */ 
/* 2455 */           if (!localRuleSymbol2.isDefined()) {
/* 2456 */             this.grammar.antlrTool.error("Filter rule " + str1 + " does not exist in this lexer");
/*      */           }
/* 2459 */           else if (localRuleSymbol2.access.equals("public")) {
/* 2460 */             this.grammar.antlrTool.error("Filter rule " + str1 + " must be protected");
/*      */           }
/*      */         }
/*      */ 
/* 2464 */         println("_m = self.mark()");
/*      */       }
/*      */     }
/* 2467 */     println("self.resetText()");
/*      */ 
/* 2469 */     println("try: ## for char stream error handling");
/* 2470 */     this.tabs += 1;
/* 2471 */     k = this.tabs;
/*      */ 
/* 2474 */     println("try: ##for lexical error handling");
/* 2475 */     this.tabs += 1;
/* 2476 */     k = this.tabs;
/*      */ 
/* 2480 */     for (int m = 0; m < localRuleBlock.getAlternatives().size(); m++)
/*      */     {
/* 2482 */       localObject1 = localRuleBlock.getAlternativeAt(m);
/* 2483 */       if (localObject1.cache[1].containsEpsilon())
/*      */       {
/* 2486 */         localObject2 = (RuleRefElement)((Alternative)localObject1).head;
/* 2487 */         String str3 = CodeGenerator.decodeLexerRuleName(((RuleRefElement)localObject2).targetRule);
/* 2488 */         this.antlrTool.warning("public lexical rule " + str3 + " is optional (can match \"nothing\")");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2493 */     String str2 = System.getProperty("line.separator");
/*      */ 
/* 2496 */     Object localObject1 = genCommonBlock(localRuleBlock, false);
/*      */ 
/* 2500 */     Object localObject2 = "";
/*      */ 
/* 2506 */     if (((LexerGrammar)this.grammar).filterMode)
/*      */     {
/* 2509 */       if (str1 == null)
/*      */       {
/* 2512 */         localObject2 = (String)localObject2 + "self.filterdefault(self.LA(1))";
/*      */       }
/*      */       else
/*      */       {
/* 2516 */         localObject2 = (String)localObject2 + "self.filterdefault(self.LA(1), self.m" + str1 + ", False)";
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 2528 */       localObject2 = "self.default(self.LA(1))";
/*      */     }
/*      */ 
/* 2533 */     genBlockFinish1((PythonBlockFinishingInfo)localObject1, (String)localObject2);
/*      */ 
/* 2536 */     this.tabs = k;
/*      */ 
/* 2539 */     if ((((LexerGrammar)this.grammar).filterMode) && (str1 != null)) {
/* 2540 */       println("self.commit()");
/*      */     }
/*      */ 
/* 2546 */     println("if not self._returnToken:");
/* 2547 */     this.tabs += 1;
/* 2548 */     println("raise antlr.TryAgain ### found SKIP token");
/* 2549 */     this.tabs -= 1;
/*      */ 
/* 2553 */     if (((LexerGrammar)this.grammar).getTestLiterals())
/*      */     {
/* 2555 */       println("### option { testLiterals=true } ");
/*      */ 
/* 2557 */       println("self.testForLiteral(self._returnToken)");
/*      */     }
/*      */ 
/* 2561 */     println("### return token to caller");
/* 2562 */     println("return self._returnToken");
/*      */ 
/* 2565 */     this.tabs -= 1;
/* 2566 */     println("### handle lexical errors ....");
/* 2567 */     println("except antlr.RecognitionException, e:");
/* 2568 */     this.tabs += 1;
/* 2569 */     if (((LexerGrammar)this.grammar).filterMode)
/*      */     {
/* 2571 */       if (str1 == null)
/*      */       {
/* 2573 */         println("if not self.getCommitToPath():");
/* 2574 */         this.tabs += 1;
/* 2575 */         println("self.consume()");
/* 2576 */         println("raise antlr.TryAgain()");
/* 2577 */         this.tabs -= 1;
/*      */       }
/*      */       else
/*      */       {
/* 2581 */         println("if not self.getCommitToPath(): ");
/* 2582 */         this.tabs += 1;
/* 2583 */         println("self.rewind(_m)");
/* 2584 */         println("self.resetText()");
/* 2585 */         println("try:");
/* 2586 */         this.tabs += 1;
/* 2587 */         println("self.m" + str1 + "(False)");
/* 2588 */         this.tabs -= 1;
/* 2589 */         println("except antlr.RecognitionException, ee:");
/* 2590 */         this.tabs += 1;
/* 2591 */         println("### horrendous failure: error in filter rule");
/* 2592 */         println("self.reportError(ee)");
/* 2593 */         println("self.consume()");
/* 2594 */         this.tabs -= 1;
/* 2595 */         println("raise antlr.TryAgain()");
/* 2596 */         this.tabs -= 1;
/*      */       }
/*      */     }
/* 2599 */     if (localRuleBlock.getDefaultErrorHandler()) {
/* 2600 */       println("self.reportError(e)");
/* 2601 */       println("self.consume()");
/*      */     }
/*      */     else
/*      */     {
/* 2605 */       println("raise antlr.TokenStreamRecognitionException(e)");
/*      */     }
/* 2607 */     this.tabs -= 1;
/*      */ 
/* 2612 */     this.tabs -= 1;
/* 2613 */     println("### handle char stream errors ...");
/* 2614 */     println("except antlr.CharStreamException,cse:");
/* 2615 */     this.tabs += 1;
/* 2616 */     println("if isinstance(cse, antlr.CharStreamIOException):");
/* 2617 */     this.tabs += 1;
/* 2618 */     println("raise antlr.TokenStreamIOException(cse.io)");
/* 2619 */     this.tabs -= 1;
/* 2620 */     println("else:");
/* 2621 */     this.tabs += 1;
/* 2622 */     println("raise antlr.TokenStreamException(str(cse))");
/* 2623 */     this.tabs -= 1;
/* 2624 */     this.tabs -= 1;
/*      */ 
/* 2628 */     this.tabs -= 1;
/*      */ 
/* 2632 */     this.tabs -= 1;
/*      */ 
/* 2634 */     println("except antlr.TryAgain:");
/* 2635 */     this.tabs += 1;
/* 2636 */     println("pass");
/* 2637 */     this.tabs -= 1;
/*      */ 
/* 2639 */     this.tabs -= 1;
/*      */   }
/*      */ 
/*      */   public void genRule(RuleSymbol paramRuleSymbol, boolean paramBoolean, int paramInt)
/*      */   {
/* 2664 */     this.tabs = 1;
/* 2665 */     if (!paramRuleSymbol.isDefined()) {
/* 2666 */       this.antlrTool.error("undefined rule: " + paramRuleSymbol.getId());
/* 2667 */       return;
/*      */     }
/*      */ 
/* 2671 */     RuleBlock localRuleBlock = paramRuleSymbol.getBlock();
/*      */ 
/* 2673 */     this.currentRule = localRuleBlock;
/* 2674 */     this.currentASTResult = paramRuleSymbol.getId();
/*      */ 
/* 2677 */     this.declaredASTVariables.clear();
/*      */ 
/* 2680 */     boolean bool1 = this.genAST;
/* 2681 */     this.genAST = ((this.genAST) && (localRuleBlock.getAutoGen()));
/*      */ 
/* 2684 */     this.saveText = localRuleBlock.getAutoGen();
/*      */ 
/* 2687 */     genJavadocComment(paramRuleSymbol);
/*      */ 
/* 2690 */     print("def " + paramRuleSymbol.getId() + "(");
/*      */ 
/* 2693 */     _print(this.commonExtraParams);
/* 2694 */     if ((this.commonExtraParams.length() != 0) && (localRuleBlock.argAction != null)) {
/* 2695 */       _print(",");
/*      */     }
/*      */ 
/* 2700 */     if (localRuleBlock.argAction != null)
/*      */     {
/* 2702 */       _println("");
/* 2703 */       this.tabs += 1;
/* 2704 */       println(localRuleBlock.argAction);
/* 2705 */       this.tabs -= 1;
/* 2706 */       print("):");
/*      */     }
/*      */     else
/*      */     {
/* 2710 */       _print("):");
/*      */     }
/*      */ 
/* 2713 */     println("");
/* 2714 */     this.tabs += 1;
/*      */ 
/* 2717 */     if (localRuleBlock.returnAction != null) {
/* 2718 */       if (localRuleBlock.returnAction.indexOf('=') >= 0) {
/* 2719 */         println(localRuleBlock.returnAction);
/*      */       }
/*      */       else {
/* 2722 */         println(extractIdOfAction(localRuleBlock.returnAction, localRuleBlock.getLine(), localRuleBlock.getColumn()) + " = None");
/*      */       }
/*      */     }
/*      */ 
/* 2726 */     println(this.commonLocalVars);
/*      */ 
/* 2728 */     if (this.grammar.traceRules) {
/* 2729 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2730 */         println("self.traceIn(\"" + paramRuleSymbol.getId() + "\",_t)");
/*      */       }
/*      */       else {
/* 2733 */         println("self.traceIn(\"" + paramRuleSymbol.getId() + "\")");
/*      */       }
/*      */     }
/*      */ 
/* 2737 */     if ((this.grammar instanceof LexerGrammar))
/*      */     {
/* 2740 */       if (paramRuleSymbol.getId().equals("mEOF"))
/* 2741 */         println("_ttype = EOF_TYPE");
/*      */       else
/* 2743 */         println("_ttype = " + paramRuleSymbol.getId().substring(1));
/* 2744 */       println("_saveIndex = 0");
/*      */     }
/*      */ 
/* 2748 */     if (this.grammar.debuggingOutput) {
/* 2749 */       if ((this.grammar instanceof ParserGrammar))
/* 2750 */         println("self.fireEnterRule(" + paramInt + ", 0)");
/* 2751 */       else if ((this.grammar instanceof LexerGrammar)) {
/* 2752 */         println("self.fireEnterRule(" + paramInt + ", _ttype)");
/*      */       }
/*      */     }
/* 2755 */     if ((this.grammar.debuggingOutput) || (this.grammar.traceRules)) {
/* 2756 */       println("try: ### debugging");
/* 2757 */       this.tabs += 1;
/*      */     }
/*      */ 
/* 2761 */     if ((this.grammar instanceof TreeWalkerGrammar))
/*      */     {
/* 2763 */       println(paramRuleSymbol.getId() + "_AST_in = None");
/* 2764 */       println("if _t != antlr.ASTNULL:");
/* 2765 */       this.tabs += 1;
/* 2766 */       println(paramRuleSymbol.getId() + "_AST_in = _t");
/* 2767 */       this.tabs -= 1;
/*      */     }
/* 2769 */     if (this.grammar.buildAST)
/*      */     {
/* 2772 */       println("self.returnAST = None");
/* 2773 */       println("currentAST = antlr.ASTPair()");
/*      */ 
/* 2775 */       println(paramRuleSymbol.getId() + "_AST = None");
/*      */     }
/*      */ 
/* 2778 */     genBlockPreamble(localRuleBlock);
/* 2779 */     genBlockInitAction(localRuleBlock);
/*      */ 
/* 2782 */     ExceptionSpec localExceptionSpec = localRuleBlock.findExceptionSpec("");
/*      */ 
/* 2785 */     if ((localExceptionSpec != null) || (localRuleBlock.getDefaultErrorHandler())) {
/* 2786 */       println("try:      ## for error handling");
/* 2787 */       this.tabs += 1;
/*      */     }
/* 2789 */     int i = this.tabs;
/*      */     Object localObject;
/* 2791 */     if (localRuleBlock.alternatives.size() == 1)
/*      */     {
/* 2794 */       Alternative localAlternative = localRuleBlock.getAlternativeAt(0);
/* 2795 */       localObject = localAlternative.semPred;
/* 2796 */       if (localObject != null)
/* 2797 */         genSemPred((String)localObject, this.currentRule.line);
/* 2798 */       if (localAlternative.synPred != null)
/*      */       {
/* 2800 */         this.antlrTool.warning("Syntactic predicate ignored for single alternative", this.grammar.getFilename(), localAlternative.synPred.getLine(), localAlternative.synPred.getColumn());
/*      */       }
/*      */ 
/* 2807 */       genAlt(localAlternative, localRuleBlock);
/*      */     }
/*      */     else
/*      */     {
/* 2812 */       boolean bool2 = this.grammar.theLLkAnalyzer.deterministic(localRuleBlock);
/*      */ 
/* 2814 */       localObject = genCommonBlock(localRuleBlock, false);
/* 2815 */       genBlockFinish((PythonBlockFinishingInfo)localObject, this.throwNoViable);
/*      */     }
/* 2817 */     this.tabs = i;
/*      */ 
/* 2820 */     if ((localExceptionSpec != null) || (localRuleBlock.getDefaultErrorHandler()))
/*      */     {
/* 2822 */       this.tabs -= 1;
/* 2823 */       println("");
/*      */     }
/*      */ 
/* 2827 */     if (localExceptionSpec != null) {
/* 2828 */       genErrorHandler(localExceptionSpec);
/*      */     }
/* 2830 */     else if (localRuleBlock.getDefaultErrorHandler())
/*      */     {
/* 2832 */       println("except " + this.exceptionThrown + ", ex:");
/* 2833 */       this.tabs += 1;
/*      */ 
/* 2835 */       if (this.grammar.hasSyntacticPredicate) {
/* 2836 */         println("if not self.inputState.guessing:");
/* 2837 */         this.tabs += 1;
/*      */       }
/* 2839 */       println("self.reportError(ex)");
/* 2840 */       if (!(this.grammar instanceof TreeWalkerGrammar))
/*      */       {
/* 2842 */         Lookahead localLookahead = this.grammar.theLLkAnalyzer.FOLLOW(1, localRuleBlock.endNode);
/* 2843 */         localObject = getBitsetName(markBitsetForGen(localLookahead.fset));
/* 2844 */         println("self.consume()");
/* 2845 */         println("self.consumeUntil(" + (String)localObject + ")");
/*      */       }
/*      */       else
/*      */       {
/* 2849 */         println("if _t:");
/* 2850 */         this.tabs += 1;
/* 2851 */         println("_t = _t.getNextSibling()");
/* 2852 */         this.tabs -= 1;
/*      */       }
/* 2854 */       if (this.grammar.hasSyntacticPredicate) {
/* 2855 */         this.tabs -= 1;
/*      */ 
/* 2857 */         println("else:");
/* 2858 */         this.tabs += 1;
/* 2859 */         println("raise ex");
/* 2860 */         this.tabs -= 1;
/*      */       }
/*      */ 
/* 2863 */       this.tabs -= 1;
/* 2864 */       println("");
/*      */     }
/*      */ 
/* 2868 */     if (this.grammar.buildAST) {
/* 2869 */       println("self.returnAST = " + paramRuleSymbol.getId() + "_AST");
/*      */     }
/*      */ 
/* 2873 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2874 */       println("self._retTree = _t");
/*      */     }
/*      */ 
/* 2878 */     if (localRuleBlock.getTestLiterals()) {
/* 2879 */       if (paramRuleSymbol.access.equals("protected")) {
/* 2880 */         genLiteralsTestForPartialToken();
/*      */       }
/*      */       else {
/* 2883 */         genLiteralsTest();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2888 */     if ((this.grammar instanceof LexerGrammar))
/*      */     {
/* 2890 */       println("self.set_return_token(_createToken, _token, _ttype, _begin)");
/*      */     }
/*      */ 
/* 2893 */     if (localRuleBlock.returnAction != null)
/*      */     {
/* 2897 */       println("return " + extractIdOfAction(localRuleBlock.returnAction, localRuleBlock.getLine(), localRuleBlock.getColumn()) + "");
/*      */     }
/*      */ 
/* 2908 */     if ((this.grammar.debuggingOutput) || (this.grammar.traceRules)) {
/* 2909 */       this.tabs -= 1;
/* 2910 */       println("finally:  ### debugging");
/* 2911 */       this.tabs += 1;
/*      */ 
/* 2914 */       if (this.grammar.debuggingOutput) {
/* 2915 */         if ((this.grammar instanceof ParserGrammar))
/* 2916 */           println("self.fireExitRule(" + paramInt + ", 0)");
/* 2917 */         else if ((this.grammar instanceof LexerGrammar))
/* 2918 */           println("self.fireExitRule(" + paramInt + ", _ttype)");
/*      */       }
/* 2920 */       if (this.grammar.traceRules) {
/* 2921 */         if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2922 */           println("self.traceOut(\"" + paramRuleSymbol.getId() + "\", _t)");
/*      */         }
/*      */         else {
/* 2925 */           println("self.traceOut(\"" + paramRuleSymbol.getId() + "\")");
/*      */         }
/*      */       }
/* 2928 */       this.tabs -= 1;
/*      */     }
/* 2930 */     this.tabs -= 1;
/* 2931 */     println("");
/*      */ 
/* 2934 */     this.genAST = bool1;
/*      */   }
/*      */ 
/*      */   private void GenRuleInvocation(RuleRefElement paramRuleRefElement)
/*      */   {
/* 2942 */     _print("self." + paramRuleRefElement.targetRule + "(");
/*      */ 
/* 2945 */     if ((this.grammar instanceof LexerGrammar))
/*      */     {
/* 2947 */       if (paramRuleRefElement.getLabel() != null) {
/* 2948 */         _print("True");
/*      */       }
/*      */       else {
/* 2951 */         _print("False");
/*      */       }
/* 2953 */       if ((this.commonExtraArgs.length() != 0) || (paramRuleRefElement.args != null)) {
/* 2954 */         _print(", ");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2959 */     _print(this.commonExtraArgs);
/* 2960 */     if ((this.commonExtraArgs.length() != 0) && (paramRuleRefElement.args != null)) {
/* 2961 */       _print(", ");
/*      */     }
/*      */ 
/* 2965 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(paramRuleRefElement.targetRule);
/* 2966 */     if (paramRuleRefElement.args != null)
/*      */     {
/* 2968 */       ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 2969 */       String str = processActionForSpecialSymbols(paramRuleRefElement.args, 0, this.currentRule, localActionTransInfo);
/* 2970 */       if ((localActionTransInfo.assignToRoot) || (localActionTransInfo.refRuleRoot != null)) {
/* 2971 */         this.antlrTool.error("Arguments of rule reference '" + paramRuleRefElement.targetRule + "' cannot set or ref #" + this.currentRule.getRuleName(), this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*      */       }
/*      */ 
/* 2974 */       _print(str);
/*      */ 
/* 2977 */       if (localRuleSymbol.block.argAction == null) {
/* 2978 */         this.antlrTool.warning("Rule '" + paramRuleRefElement.targetRule + "' accepts no arguments", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*      */       }
/*      */ 
/*      */     }
/* 2984 */     else if (localRuleSymbol.block.argAction != null) {
/* 2985 */       this.antlrTool.warning("Missing parameters on reference to rule " + paramRuleRefElement.targetRule, this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*      */     }
/*      */ 
/* 2988 */     _println(")");
/*      */ 
/* 2991 */     if ((this.grammar instanceof TreeWalkerGrammar))
/* 2992 */       println("_t = self._retTree");
/*      */   }
/*      */ 
/*      */   protected void genSemPred(String paramString, int paramInt)
/*      */   {
/* 2998 */     ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 2999 */     paramString = processActionForSpecialSymbols(paramString, paramInt, this.currentRule, localActionTransInfo);
/*      */ 
/* 3002 */     String str = this.charFormatter.escapeString(paramString);
/*      */ 
/* 3006 */     if ((this.grammar.debuggingOutput) && (((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar))))
/*      */     {
/* 3008 */       paramString = "fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEvent.VALIDATING," + addSemPred(str) + ", " + paramString + ")";
/*      */     }
/*      */ 
/* 3012 */     println("if not " + paramString + ":");
/* 3013 */     this.tabs += 1;
/* 3014 */     println("raise antlr.SemanticException(\"" + str + "\")");
/* 3015 */     this.tabs -= 1;
/*      */   }
/*      */ 
/*      */   protected void genSemPredMap()
/*      */   {
/* 3022 */     Enumeration localEnumeration = this.semPreds.elements();
/* 3023 */     println("_semPredNames = [");
/* 3024 */     this.tabs += 1;
/* 3025 */     while (localEnumeration.hasMoreElements()) {
/* 3026 */       println("\"" + localEnumeration.nextElement() + "\",");
/*      */     }
/* 3028 */     this.tabs -= 1;
/* 3029 */     println("]");
/*      */   }
/*      */ 
/*      */   protected void genSynPred(SynPredBlock paramSynPredBlock, String paramString) {
/* 3033 */     if (this.DEBUG_CODE_GENERATOR) System.out.println("gen=>(" + paramSynPredBlock + ")");
/*      */ 
/* 3036 */     println("synPredMatched" + paramSynPredBlock.ID + " = False");
/*      */ 
/* 3038 */     println("if " + paramString + ":");
/* 3039 */     this.tabs += 1;
/*      */ 
/* 3042 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 3043 */       println("_t" + paramSynPredBlock.ID + " = _t");
/*      */     }
/*      */     else {
/* 3046 */       println("_m" + paramSynPredBlock.ID + " = self.mark()");
/*      */     }
/*      */ 
/* 3050 */     println("synPredMatched" + paramSynPredBlock.ID + " = True");
/* 3051 */     println("self.inputState.guessing += 1");
/*      */ 
/* 3054 */     if ((this.grammar.debuggingOutput) && (((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar))))
/*      */     {
/* 3056 */       println("self.fireSyntacticPredicateStarted()");
/*      */     }
/*      */ 
/* 3059 */     this.syntacticPredLevel += 1;
/* 3060 */     println("try:");
/* 3061 */     this.tabs += 1;
/* 3062 */     gen(paramSynPredBlock);
/* 3063 */     this.tabs -= 1;
/* 3064 */     println("except " + this.exceptionThrown + ", pe:");
/* 3065 */     this.tabs += 1;
/* 3066 */     println("synPredMatched" + paramSynPredBlock.ID + " = False");
/* 3067 */     this.tabs -= 1;
/*      */ 
/* 3070 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 3071 */       println("_t = _t" + paramSynPredBlock.ID + "");
/*      */     }
/*      */     else {
/* 3074 */       println("self.rewind(_m" + paramSynPredBlock.ID + ")");
/*      */     }
/*      */ 
/* 3077 */     println("self.inputState.guessing -= 1");
/*      */ 
/* 3080 */     if ((this.grammar.debuggingOutput) && (((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar))))
/*      */     {
/* 3082 */       println("if synPredMatched" + paramSynPredBlock.ID + ":");
/* 3083 */       this.tabs += 1;
/* 3084 */       println("self.fireSyntacticPredicateSucceeded()");
/* 3085 */       this.tabs -= 1;
/* 3086 */       println("else:");
/* 3087 */       this.tabs += 1;
/* 3088 */       println("self.fireSyntacticPredicateFailed()");
/* 3089 */       this.tabs -= 1;
/*      */     }
/*      */ 
/* 3092 */     this.syntacticPredLevel -= 1;
/* 3093 */     this.tabs -= 1;
/*      */ 
/* 3098 */     println("if synPredMatched" + paramSynPredBlock.ID + ":");
/*      */   }
/*      */ 
/*      */   public void genTokenStrings()
/*      */   {
/* 3112 */     int i = this.tabs;
/* 3113 */     this.tabs = 0;
/*      */ 
/* 3115 */     println("");
/* 3116 */     println("_tokenNames = [");
/* 3117 */     this.tabs += 1;
/*      */ 
/* 3121 */     Vector localVector = this.grammar.tokenManager.getVocabulary();
/* 3122 */     for (int j = 0; j < localVector.size(); j++) {
/* 3123 */       String str = (String)localVector.elementAt(j);
/* 3124 */       if (str == null) {
/* 3125 */         str = "<" + String.valueOf(j) + ">";
/*      */       }
/* 3127 */       if ((!str.startsWith("\"")) && (!str.startsWith("<"))) {
/* 3128 */         TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol(str);
/* 3129 */         if ((localTokenSymbol != null) && (localTokenSymbol.getParaphrase() != null)) {
/* 3130 */           str = StringUtils.stripFrontBack(localTokenSymbol.getParaphrase(), "\"", "\"");
/*      */         }
/*      */       }
/* 3133 */       print(this.charFormatter.literalString(str));
/* 3134 */       if (j != localVector.size() - 1) {
/* 3135 */         _print(", ");
/*      */       }
/* 3137 */       _println("");
/*      */     }
/*      */ 
/* 3141 */     this.tabs -= 1;
/* 3142 */     println("]");
/* 3143 */     this.tabs = i;
/*      */   }
/*      */ 
/*      */   protected void genTokenASTNodeMap()
/*      */   {
/* 3150 */     println("");
/* 3151 */     println("def buildTokenTypeASTClassMap(self):");
/*      */ 
/* 3154 */     this.tabs += 1;
/* 3155 */     int i = 0;
/* 3156 */     int j = 0;
/*      */ 
/* 3158 */     Vector localVector = this.grammar.tokenManager.getVocabulary();
/* 3159 */     for (int k = 0; k < localVector.size(); k++) {
/* 3160 */       String str = (String)localVector.elementAt(k);
/* 3161 */       if (str != null) {
/* 3162 */         TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol(str);
/* 3163 */         if ((localTokenSymbol != null) && (localTokenSymbol.getASTNodeType() != null)) {
/* 3164 */           j++;
/* 3165 */           if (i == 0)
/*      */           {
/* 3167 */             println("self.tokenTypeToASTClassMap = {}");
/* 3168 */             i = 1;
/*      */           }
/* 3170 */           println("self.tokenTypeToASTClassMap[" + localTokenSymbol.getTokenType() + "] = " + localTokenSymbol.getASTNodeType());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3179 */     if (j == 0) {
/* 3180 */       println("self.tokenTypeToASTClassMap = None");
/*      */     }
/* 3182 */     this.tabs -= 1;
/*      */   }
/*      */ 
/*      */   protected void genTokenTypes(TokenManager paramTokenManager)
/*      */     throws IOException
/*      */   {
/* 3193 */     this.tabs = 0;
/*      */ 
/* 3201 */     Vector localVector = paramTokenManager.getVocabulary();
/*      */ 
/* 3204 */     println("SKIP                = antlr.SKIP");
/* 3205 */     println("INVALID_TYPE        = antlr.INVALID_TYPE");
/* 3206 */     println("EOF_TYPE            = antlr.EOF_TYPE");
/* 3207 */     println("EOF                 = antlr.EOF");
/* 3208 */     println("NULL_TREE_LOOKAHEAD = antlr.NULL_TREE_LOOKAHEAD");
/* 3209 */     println("MIN_USER_TYPE       = antlr.MIN_USER_TYPE");
/*      */ 
/* 3211 */     for (int i = 4; i < localVector.size(); i++)
/*      */     {
/* 3213 */       String str1 = (String)localVector.elementAt(i);
/* 3214 */       if (str1 != null)
/*      */       {
/* 3216 */         if (str1.startsWith("\""))
/*      */         {
/* 3219 */           StringLiteralSymbol localStringLiteralSymbol = (StringLiteralSymbol)paramTokenManager.getTokenSymbol(str1);
/* 3220 */           if (localStringLiteralSymbol == null) {
/* 3221 */             this.antlrTool.panic("String literal " + str1 + " not in symbol table");
/*      */           }
/* 3223 */           if (localStringLiteralSymbol.label != null)
/*      */           {
/* 3225 */             println(localStringLiteralSymbol.label + " = " + i);
/*      */           }
/*      */           else
/*      */           {
/* 3229 */             String str2 = mangleLiteral(str1);
/* 3230 */             if (str2 != null)
/*      */             {
/* 3232 */               println(str2 + " = " + i);
/*      */ 
/* 3234 */               localStringLiteralSymbol.label = str2;
/*      */             }
/*      */             else
/*      */             {
/* 3238 */               println("### " + str1 + " = " + i);
/*      */             }
/*      */           }
/*      */         }
/* 3242 */         else if (!str1.startsWith("<")) {
/* 3243 */           println(str1 + " = " + i);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3249 */     this.tabs -= 1;
/*      */ 
/* 3251 */     exitIfError();
/*      */   }
/*      */ 
/*      */   public String getASTCreateString(Vector paramVector)
/*      */   {
/* 3258 */     if (paramVector.size() == 0) {
/* 3259 */       return "";
/*      */     }
/* 3261 */     StringBuffer localStringBuffer = new StringBuffer();
/* 3262 */     localStringBuffer.append("antlr.make(");
/* 3263 */     for (int i = 0; i < paramVector.size(); i++) {
/* 3264 */       localStringBuffer.append(paramVector.elementAt(i));
/* 3265 */       if (i + 1 < paramVector.size()) {
/* 3266 */         localStringBuffer.append(", ");
/*      */       }
/*      */     }
/* 3269 */     localStringBuffer.append(")");
/* 3270 */     return localStringBuffer.toString();
/*      */   }
/*      */ 
/*      */   public String getASTCreateString(GrammarAtom paramGrammarAtom, String paramString)
/*      */   {
/* 3279 */     if ((paramGrammarAtom != null) && (paramGrammarAtom.getASTNodeType() != null))
/*      */     {
/* 3282 */       return "self.astFactory.create(" + paramString + ", " + paramGrammarAtom.getASTNodeType() + ")";
/*      */     }
/*      */ 
/* 3287 */     return getASTCreateString(paramString);
/*      */   }
/*      */ 
/*      */   public String getASTCreateString(String paramString)
/*      */   {
/* 3300 */     if (paramString == null) {
/* 3301 */       paramString = "";
/*      */     }
/* 3303 */     int i = 0;
/* 3304 */     for (int j = 0; j < paramString.length(); j++) {
/* 3305 */       if (paramString.charAt(j) == ',') {
/* 3306 */         i++;
/*      */       }
/*      */     }
/* 3309 */     if (i < 2) {
/* 3310 */       j = paramString.indexOf(',');
/* 3311 */       int k = paramString.lastIndexOf(',');
/* 3312 */       String str1 = paramString;
/* 3313 */       if (i > 0) {
/* 3314 */         str1 = paramString.substring(0, j);
/*      */       }
/* 3316 */       TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol(str1);
/* 3317 */       if (localTokenSymbol != null) {
/* 3318 */         String str2 = localTokenSymbol.getASTNodeType();
/* 3319 */         String str3 = "";
/* 3320 */         if (i == 0)
/*      */         {
/* 3322 */           str3 = ", \"\"";
/*      */         }
/* 3324 */         if (str2 != null) {
/* 3325 */           return "self.astFactory.create(" + paramString + str3 + ", " + str2 + ")";
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 3330 */       if (this.labeledElementASTType.equals("AST")) {
/* 3331 */         return "self.astFactory.create(" + paramString + ")";
/*      */       }
/* 3333 */       return "self.astFactory.create(" + paramString + ")";
/*      */     }
/*      */ 
/* 3337 */     return "self.astFactory.create(" + paramString + ")";
/*      */   }
/*      */ 
/*      */   protected String getLookaheadTestExpression(Lookahead[] paramArrayOfLookahead, int paramInt) {
/* 3341 */     StringBuffer localStringBuffer = new StringBuffer(100);
/* 3342 */     int i = 1;
/*      */ 
/* 3344 */     localStringBuffer.append("(");
/* 3345 */     for (int j = 1; j <= paramInt; j++) {
/* 3346 */       BitSet localBitSet = paramArrayOfLookahead[j].fset;
/* 3347 */       if (i == 0) {
/* 3348 */         localStringBuffer.append(") and (");
/*      */       }
/* 3350 */       i = 0;
/*      */ 
/* 3355 */       if (paramArrayOfLookahead[j].containsEpsilon()) {
/* 3356 */         localStringBuffer.append("True");
/*      */       }
/*      */       else {
/* 3359 */         localStringBuffer.append(getLookaheadTestTerm(j, localBitSet));
/*      */       }
/*      */     }
/*      */ 
/* 3363 */     localStringBuffer.append(")");
/* 3364 */     String str = localStringBuffer.toString();
/* 3365 */     return str;
/*      */   }
/*      */ 
/*      */   protected String getLookaheadTestExpression(Alternative paramAlternative, int paramInt)
/*      */   {
/* 3373 */     int i = paramAlternative.lookaheadDepth;
/* 3374 */     if (i == 2147483647)
/*      */     {
/* 3378 */       i = this.grammar.maxk;
/*      */     }
/*      */ 
/* 3381 */     if (paramInt == 0)
/*      */     {
/* 3385 */       return "True";
/*      */     }
/*      */ 
/* 3388 */     return getLookaheadTestExpression(paramAlternative.cache, i);
/*      */   }
/*      */ 
/*      */   protected String getLookaheadTestTerm(int paramInt, BitSet paramBitSet)
/*      */   {
/* 3401 */     String str1 = lookaheadString(paramInt);
/*      */ 
/* 3404 */     int[] arrayOfInt = paramBitSet.toArray();
/* 3405 */     if (elementsAreRange(arrayOfInt)) {
/* 3406 */       localObject = getRangeExpression(paramInt, arrayOfInt);
/* 3407 */       return localObject;
/*      */     }
/*      */ 
/* 3412 */     int i = paramBitSet.degree();
/* 3413 */     if (i == 0) {
/* 3414 */       return "True";
/*      */     }
/*      */ 
/* 3417 */     if (i >= this.bitsetTestThreshold) {
/* 3418 */       j = markBitsetForGen(paramBitSet);
/* 3419 */       return getBitsetName(j) + ".member(" + str1 + ")";
/*      */     }
/*      */ 
/* 3423 */     Object localObject = new StringBuffer();
/* 3424 */     for (int j = 0; j < arrayOfInt.length; j++)
/*      */     {
/* 3426 */       String str3 = getValueString(arrayOfInt[j], true);
/*      */ 
/* 3429 */       if (j > 0) ((StringBuffer)localObject).append(" or ");
/* 3430 */       ((StringBuffer)localObject).append(str1);
/* 3431 */       ((StringBuffer)localObject).append("==");
/* 3432 */       ((StringBuffer)localObject).append(str3);
/*      */     }
/* 3434 */     String str2 = ((StringBuffer)localObject).toString();
/* 3435 */     return ((StringBuffer)localObject).toString();
/*      */   }
/*      */ 
/*      */   public String getRangeExpression(int paramInt, int[] paramArrayOfInt)
/*      */   {
/* 3444 */     if (!elementsAreRange(paramArrayOfInt)) {
/* 3445 */       this.antlrTool.panic("getRangeExpression called with non-range");
/*      */     }
/* 3447 */     int i = paramArrayOfInt[0];
/* 3448 */     int j = paramArrayOfInt[(paramArrayOfInt.length - 1)];
/* 3449 */     return "(" + lookaheadString(paramInt) + " >= " + getValueString(i, true) + " and " + lookaheadString(paramInt) + " <= " + getValueString(j, true) + ")";
/*      */   }
/*      */ 
/*      */   private String getValueString(int paramInt, boolean paramBoolean)
/*      */   {
/*      */     Object localObject;
/* 3459 */     if ((this.grammar instanceof LexerGrammar)) {
/* 3460 */       localObject = this.charFormatter.literalChar(paramInt);
/* 3461 */       if (paramBoolean)
/* 3462 */         localObject = "u'" + (String)localObject + "'";
/* 3463 */       return localObject;
/*      */     }
/*      */ 
/* 3467 */     TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbolAt(paramInt);
/*      */ 
/* 3471 */     if (localTokenSymbol == null) {
/* 3472 */       localObject = "" + paramInt;
/* 3473 */       return localObject;
/*      */     }
/*      */ 
/* 3476 */     String str1 = localTokenSymbol.getId();
/* 3477 */     if (!(localTokenSymbol instanceof StringLiteralSymbol))
/*      */     {
/* 3479 */       localObject = str1;
/* 3480 */       return localObject;
/*      */     }
/*      */ 
/* 3486 */     StringLiteralSymbol localStringLiteralSymbol = (StringLiteralSymbol)localTokenSymbol;
/* 3487 */     String str2 = localStringLiteralSymbol.getLabel();
/* 3488 */     if (str2 != null) {
/* 3489 */       localObject = str2;
/*      */     }
/*      */     else
/*      */     {
/* 3493 */       localObject = mangleLiteral(str1);
/* 3494 */       if (localObject == null) {
/* 3495 */         localObject = String.valueOf(paramInt);
/*      */       }
/*      */     }
/* 3498 */     return localObject;
/*      */   }
/*      */ 
/*      */   protected boolean lookaheadIsEmpty(Alternative paramAlternative, int paramInt)
/*      */   {
/* 3503 */     int i = paramAlternative.lookaheadDepth;
/* 3504 */     if (i == 2147483647) {
/* 3505 */       i = this.grammar.maxk;
/*      */     }
/* 3507 */     for (int j = 1; (j <= i) && (j <= paramInt); j++) {
/* 3508 */       BitSet localBitSet = paramAlternative.cache[j].fset;
/* 3509 */       if (localBitSet.degree() != 0) {
/* 3510 */         return false;
/*      */       }
/*      */     }
/* 3513 */     return true;
/*      */   }
/*      */ 
/*      */   private String lookaheadString(int paramInt)
/*      */   {
/* 3518 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 3519 */       return "_t.getType()";
/*      */     }
/* 3521 */     return "self.LA(" + paramInt + ")";
/*      */   }
/*      */ 
/*      */   private String mangleLiteral(String paramString)
/*      */   {
/* 3531 */     String str = this.antlrTool.literalsPrefix;
/* 3532 */     for (int i = 1; i < paramString.length() - 1; i++) {
/* 3533 */       if ((!Character.isLetter(paramString.charAt(i))) && (paramString.charAt(i) != '_'))
/*      */       {
/* 3535 */         return null;
/*      */       }
/* 3537 */       str = str + paramString.charAt(i);
/*      */     }
/* 3539 */     if (this.antlrTool.upperCaseMangledLiterals) {
/* 3540 */       str = str.toUpperCase();
/*      */     }
/* 3542 */     return str;
/*      */   }
/*      */ 
/*      */   public String mapTreeId(String paramString, ActionTransInfo paramActionTransInfo)
/*      */   {
/* 3554 */     if (this.currentRule == null) return paramString;
/*      */ 
/* 3556 */     int i = 0;
/* 3557 */     String str1 = paramString;
/* 3558 */     if ((this.grammar instanceof TreeWalkerGrammar))
/* 3559 */       if (!this.grammar.buildAST) {
/* 3560 */         i = 1;
/*      */       }
/* 3563 */       else if ((str1.length() > 3) && (str1.lastIndexOf("_in") == str1.length() - 3))
/*      */       {
/* 3565 */         str1 = str1.substring(0, str1.length() - 3);
/* 3566 */         i = 1;
/*      */       }
/*      */     Object localObject;
/* 3572 */     for (int j = 0; j < this.currentRule.labeledElements.size(); j++) {
/* 3573 */       localObject = (AlternativeElement)this.currentRule.labeledElements.elementAt(j);
/* 3574 */       if (((AlternativeElement)localObject).getLabel().equals(str1)) {
/* 3575 */         return str1 + "_AST";
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3582 */     String str2 = (String)this.treeVariableMap.get(str1);
/* 3583 */     if (str2 != null) {
/* 3584 */       if (str2 == NONUNIQUE)
/*      */       {
/* 3586 */         this.antlrTool.error("Ambiguous reference to AST element " + str1 + " in rule " + this.currentRule.getRuleName());
/*      */ 
/* 3589 */         return null;
/*      */       }
/* 3591 */       if (str2.equals(this.currentRule.getRuleName()))
/*      */       {
/* 3594 */         this.antlrTool.error("Ambiguous reference to AST element " + str1 + " in rule " + this.currentRule.getRuleName());
/*      */ 
/* 3596 */         return null;
/*      */       }
/*      */ 
/* 3599 */       return i != 0 ? str2 + "_in" : str2;
/*      */     }
/*      */ 
/* 3605 */     if (str1.equals(this.currentRule.getRuleName())) {
/* 3606 */       localObject = str1 + "_AST";
/* 3607 */       if ((paramActionTransInfo != null) && 
/* 3608 */         (i == 0)) {
/* 3609 */         paramActionTransInfo.refRuleRoot = ((String)localObject);
/*      */       }
/*      */ 
/* 3612 */       return localObject;
/*      */     }
/*      */ 
/* 3616 */     return str1;
/*      */   }
/*      */ 
/*      */   private void mapTreeVariable(AlternativeElement paramAlternativeElement, String paramString)
/*      */   {
/* 3625 */     if ((paramAlternativeElement instanceof TreeElement)) {
/* 3626 */       mapTreeVariable(((TreeElement)paramAlternativeElement).root, paramString);
/* 3627 */       return;
/*      */     }
/*      */ 
/* 3631 */     String str = null;
/*      */ 
/* 3634 */     if (paramAlternativeElement.getLabel() == null) {
/* 3635 */       if ((paramAlternativeElement instanceof TokenRefElement))
/*      */       {
/* 3637 */         str = ((TokenRefElement)paramAlternativeElement).atomText;
/*      */       }
/* 3639 */       else if ((paramAlternativeElement instanceof RuleRefElement))
/*      */       {
/* 3641 */         str = ((RuleRefElement)paramAlternativeElement).targetRule;
/*      */       }
/*      */     }
/*      */ 
/* 3645 */     if (str != null)
/* 3646 */       if (this.treeVariableMap.get(str) != null)
/*      */       {
/* 3648 */         this.treeVariableMap.remove(str);
/* 3649 */         this.treeVariableMap.put(str, NONUNIQUE);
/*      */       }
/*      */       else {
/* 3652 */         this.treeVariableMap.put(str, paramString);
/*      */       }
/*      */   }
/*      */ 
/*      */   protected String processActionForSpecialSymbols(String paramString, int paramInt, RuleBlock paramRuleBlock, ActionTransInfo paramActionTransInfo)
/*      */   {
/* 3664 */     if ((paramString == null) || (paramString.length() == 0)) {
/* 3665 */       return null;
/*      */     }
/* 3667 */     if (isEmpty(paramString)) {
/* 3668 */       return "";
/*      */     }
/*      */ 
/* 3672 */     if (this.grammar == null)
/*      */     {
/* 3674 */       return paramString;
/*      */     }
/*      */ 
/* 3678 */     ActionLexer localActionLexer = new ActionLexer(paramString, paramRuleBlock, this, paramActionTransInfo);
/*      */ 
/* 3685 */     localActionLexer.setLineOffset(paramInt);
/* 3686 */     localActionLexer.setFilename(this.grammar.getFilename());
/* 3687 */     localActionLexer.setTool(this.antlrTool);
/*      */     try
/*      */     {
/* 3690 */       localActionLexer.mACTION(true);
/* 3691 */       paramString = localActionLexer.getTokenObject().getText();
/*      */     }
/*      */     catch (RecognitionException localRecognitionException) {
/* 3694 */       localActionLexer.reportError(localRecognitionException);
/*      */     }
/*      */     catch (TokenStreamException localTokenStreamException) {
/* 3697 */       this.antlrTool.panic("Error reading action:" + paramString);
/*      */     }
/*      */     catch (CharStreamException localCharStreamException) {
/* 3700 */       this.antlrTool.panic("Error reading action:" + paramString);
/*      */     }
/* 3702 */     return paramString;
/*      */   }
/*      */ 
/*      */   static boolean isEmpty(String paramString)
/*      */   {
/* 3707 */     boolean bool = true;
/*      */ 
/* 3710 */     for (int j = 0; (bool) && (j < paramString.length()); j++) {
/* 3711 */       int i = paramString.charAt(j);
/* 3712 */       switch (i)
/*      */       {
/*      */       case 9:
/*      */       case 10:
/*      */       case 12:
/*      */       case 13:
/*      */       case 32:
/* 3719 */         break;
/*      */       default:
/* 3722 */         bool = false;
/*      */       }
/*      */     }
/*      */ 
/* 3726 */     return bool;
/*      */   }
/*      */ 
/*      */   protected String processActionCode(String paramString, int paramInt)
/*      */   {
/* 3731 */     if ((paramString == null) || (isEmpty(paramString))) {
/* 3732 */       return "";
/*      */     }
/* 3734 */     CodeLexer localCodeLexer = new CodeLexer(paramString, this.grammar.getFilename(), paramInt, this.antlrTool);
/*      */     try
/*      */     {
/* 3743 */       localCodeLexer.mACTION(true);
/* 3744 */       paramString = localCodeLexer.getTokenObject().getText();
/*      */     }
/*      */     catch (RecognitionException localRecognitionException) {
/* 3747 */       localCodeLexer.reportError(localRecognitionException);
/*      */     }
/*      */     catch (TokenStreamException localTokenStreamException) {
/* 3750 */       this.antlrTool.panic("Error reading action:" + paramString);
/*      */     }
/*      */     catch (CharStreamException localCharStreamException) {
/* 3753 */       this.antlrTool.panic("Error reading action:" + paramString);
/*      */     }
/* 3755 */     return paramString;
/*      */   }
/*      */ 
/*      */   protected void printActionCode(String paramString, int paramInt) {
/* 3759 */     paramString = processActionCode(paramString, paramInt);
/* 3760 */     printAction(paramString);
/*      */   }
/*      */ 
/*      */   private void setupGrammarParameters(Grammar paramGrammar)
/*      */   {
/*      */     Token localToken;
/*      */     String str;
/* 3764 */     if ((paramGrammar instanceof ParserGrammar))
/*      */     {
/* 3766 */       this.labeledElementASTType = "";
/* 3767 */       if (paramGrammar.hasOption("ASTLabelType"))
/*      */       {
/* 3769 */         localToken = paramGrammar.getOption("ASTLabelType");
/* 3770 */         if (localToken != null) {
/* 3771 */           str = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 3772 */           if (str != null) {
/* 3773 */             this.labeledElementASTType = str;
/*      */           }
/*      */         }
/*      */       }
/* 3777 */       this.labeledElementType = "";
/* 3778 */       this.labeledElementInit = "None";
/* 3779 */       this.commonExtraArgs = "";
/* 3780 */       this.commonExtraParams = "self";
/* 3781 */       this.commonLocalVars = "";
/* 3782 */       this.lt1Value = "self.LT(1)";
/* 3783 */       this.exceptionThrown = "antlr.RecognitionException";
/* 3784 */       this.throwNoViable = "raise antlr.NoViableAltException(self.LT(1), self.getFilename())";
/* 3785 */       this.parserClassName = "Parser";
/* 3786 */       if (paramGrammar.hasOption("className"))
/*      */       {
/* 3788 */         localToken = paramGrammar.getOption("className");
/* 3789 */         if (localToken != null) {
/* 3790 */           str = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 3791 */           if (str != null) {
/* 3792 */             this.parserClassName = str;
/*      */           }
/*      */         }
/*      */       }
/* 3796 */       return;
/*      */     }
/*      */ 
/* 3799 */     if ((paramGrammar instanceof LexerGrammar))
/*      */     {
/* 3801 */       this.labeledElementType = "char ";
/* 3802 */       this.labeledElementInit = "'\\0'";
/* 3803 */       this.commonExtraArgs = "";
/* 3804 */       this.commonExtraParams = "self, _createToken";
/* 3805 */       this.commonLocalVars = "_ttype = 0\n        _token = None\n        _begin = self.text.length()";
/* 3806 */       this.lt1Value = "self.LA(1)";
/* 3807 */       this.exceptionThrown = "antlr.RecognitionException";
/* 3808 */       this.throwNoViable = "self.raise_NoViableAlt(self.LA(1))";
/* 3809 */       this.lexerClassName = "Lexer";
/* 3810 */       if (paramGrammar.hasOption("className"))
/*      */       {
/* 3812 */         localToken = paramGrammar.getOption("className");
/* 3813 */         if (localToken != null) {
/* 3814 */           str = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 3815 */           if (str != null) {
/* 3816 */             this.lexerClassName = str;
/*      */           }
/*      */         }
/*      */       }
/* 3820 */       return;
/*      */     }
/*      */ 
/* 3823 */     if ((paramGrammar instanceof TreeWalkerGrammar))
/*      */     {
/* 3825 */       this.labeledElementASTType = "";
/* 3826 */       this.labeledElementType = "";
/* 3827 */       if (paramGrammar.hasOption("ASTLabelType")) {
/* 3828 */         localToken = paramGrammar.getOption("ASTLabelType");
/* 3829 */         if (localToken != null) {
/* 3830 */           str = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 3831 */           if (str != null) {
/* 3832 */             this.labeledElementASTType = str;
/* 3833 */             this.labeledElementType = str;
/*      */           }
/*      */         }
/*      */       }
/* 3837 */       if (!paramGrammar.hasOption("ASTLabelType")) {
/* 3838 */         paramGrammar.setOption("ASTLabelType", new Token(6, "<4>AST"));
/*      */       }
/* 3840 */       this.labeledElementInit = "None";
/* 3841 */       this.commonExtraArgs = "_t";
/* 3842 */       this.commonExtraParams = "self, _t";
/* 3843 */       this.commonLocalVars = "";
/* 3844 */       this.lt1Value = "_t";
/* 3845 */       this.exceptionThrown = "antlr.RecognitionException";
/* 3846 */       this.throwNoViable = "raise antlr.NoViableAltException(_t)";
/* 3847 */       this.treeWalkerClassName = "Walker";
/* 3848 */       if (paramGrammar.hasOption("className"))
/*      */       {
/* 3850 */         localToken = paramGrammar.getOption("className");
/* 3851 */         if (localToken != null) {
/* 3852 */           str = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 3853 */           if (str != null) {
/* 3854 */             this.treeWalkerClassName = str;
/*      */           }
/*      */         }
/*      */       }
/* 3858 */       return;
/*      */     }
/*      */ 
/* 3862 */     this.antlrTool.panic("Unknown grammar type");
/*      */   }
/*      */ 
/*      */   public void setupOutput(String paramString)
/*      */     throws IOException
/*      */   {
/* 3870 */     this.currentOutput = this.antlrTool.openOutputFile(paramString + ".py");
/*      */   }
/*      */ 
/*      */   protected boolean isspace(char paramChar) {
/* 3874 */     boolean bool = true;
/* 3875 */     switch (paramChar) {
/*      */     case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/* 3880 */       break;
/*      */     default:
/* 3882 */       bool = false;
/*      */     }
/*      */ 
/* 3885 */     return bool;
/*      */   }
/*      */ 
/*      */   protected void _printAction(String paramString) {
/* 3889 */     if (paramString == null) {
/* 3890 */       return;
/*      */     }
/*      */ 
/* 3903 */     int j = 0;
/* 3904 */     int k = paramString.length();
/*      */ 
/* 3907 */     int i = 0;
/* 3908 */     int m = 1;
/*      */     char c;
/* 3910 */     while ((j < k) && (m != 0))
/*      */     {
/* 3912 */       c = paramString.charAt(j++);
/* 3913 */       switch (c) {
/*      */       case '\n':
/* 3915 */         i = j;
/* 3916 */         break;
/*      */       case '\r':
/* 3918 */         if ((j <= k) && (paramString.charAt(j) == '\n'))
/* 3919 */           j++;
/* 3920 */         i = j;
/* 3921 */         break;
/*      */       case ' ':
/* 3923 */         break;
/*      */       case '\t':
/*      */       default:
/* 3926 */         m = 0;
/*      */       }
/*      */     }
/*      */ 
/* 3930 */     if (m == 0) {
/* 3931 */       j--;
/*      */     }
/* 3933 */     i = j - i;
/*      */ 
/* 3936 */     k -= 1;
/* 3937 */     while ((k > j) && (isspace(paramString.charAt(k)))) {
/* 3938 */       k--;
/*      */     }
/*      */ 
/* 3941 */     int n = 0;
/*      */ 
/* 3944 */     for (int i2 = j; i2 <= k; i2++)
/*      */     {
/* 3946 */       c = paramString.charAt(i2);
/* 3947 */       switch (c) {
/*      */       case '\n':
/* 3949 */         n = 1;
/* 3950 */         break;
/*      */       case '\r':
/* 3952 */         n = 1;
/* 3953 */         if ((i2 + 1 <= k) && (paramString.charAt(i2 + 1) == '\n'))
/* 3954 */           i2++; break;
/*      */       case '\t':
/* 3958 */         System.err.println("warning: tab characters used in Python action");
/* 3959 */         this.currentOutput.print("        ");
/* 3960 */         break;
/*      */       case ' ':
/* 3962 */         this.currentOutput.print(" ");
/* 3963 */         break;
/*      */       default:
/* 3965 */         this.currentOutput.print(c);
/*      */       }
/*      */ 
/* 3969 */       if (n != 0)
/*      */       {
/* 3971 */         this.currentOutput.print("\n");
/* 3972 */         printTabs();
/* 3973 */         int i1 = 0;
/* 3974 */         n = 0;
/*      */ 
/* 3976 */         for (i2 += 1; i2 <= k; i2++)
/*      */         {
/* 3978 */           c = paramString.charAt(i2);
/* 3979 */           if (!isspace(c)) {
/* 3980 */             i2--;
/* 3981 */             break;
/*      */           }
/* 3983 */           switch (c) {
/*      */           case '\n':
/* 3985 */             n = 1;
/* 3986 */             break;
/*      */           case '\r':
/* 3988 */             if ((i2 + 1 <= k) && (paramString.charAt(i2 + 1) == '\n')) {
/* 3989 */               i2++;
/*      */             }
/* 3991 */             n = 1;
/*      */           }
/*      */ 
/* 3995 */           if (n != 0)
/*      */           {
/* 3997 */             this.currentOutput.print("\n");
/* 3998 */             printTabs();
/* 3999 */             i1 = 0;
/* 4000 */             n = 0;
/*      */           }
/*      */           else
/*      */           {
/* 4004 */             if (i1 >= i) break;
/* 4005 */             i1++;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 4015 */     this.currentOutput.println();
/*      */   }
/*      */ 
/*      */   protected void od(String paramString1, int paramInt1, int paramInt2, String paramString2) {
/* 4019 */     System.out.println(paramString2);
/*      */ 
/* 4021 */     for (int i = paramInt1; i <= paramInt2; i++)
/*      */     {
/* 4023 */       char c = paramString1.charAt(i);
/* 4024 */       switch (c) {
/*      */       case '\n':
/* 4026 */         System.out.print(" nl ");
/* 4027 */         break;
/*      */       case '\t':
/* 4029 */         System.out.print(" ht ");
/* 4030 */         break;
/*      */       case ' ':
/* 4032 */         System.out.print(" sp ");
/* 4033 */         break;
/*      */       default:
/* 4035 */         System.out.print(" " + c + " ");
/*      */       }
/*      */     }
/* 4038 */     System.out.println("");
/*      */   }
/*      */ 
/*      */   protected void printAction(String paramString) {
/* 4042 */     if (paramString != null) {
/* 4043 */       printTabs();
/* 4044 */       _printAction(paramString);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void printGrammarAction(Grammar paramGrammar) {
/* 4049 */     println("### user action >>>");
/* 4050 */     printAction(processActionForSpecialSymbols(paramGrammar.classMemberAction.getText(), paramGrammar.classMemberAction.getLine(), this.currentRule, null));
/*      */ 
/* 4057 */     println("### user action <<<");
/*      */   }
/*      */ 
/*      */   protected void _printJavadoc(String paramString)
/*      */   {
/* 4062 */     int i = paramString.length();
/* 4063 */     int j = 0;
/* 4064 */     int k = 0;
/*      */ 
/* 4066 */     this.currentOutput.print("\n");
/* 4067 */     printTabs();
/* 4068 */     this.currentOutput.print("###");
/*      */ 
/* 4070 */     for (int m = j; m < i; m++)
/*      */     {
/* 4072 */       char c = paramString.charAt(m);
/* 4073 */       switch (c) {
/*      */       case '\n':
/* 4075 */         k = 1;
/* 4076 */         break;
/*      */       case '\r':
/* 4078 */         k = 1;
/* 4079 */         if ((m + 1 <= i) && (paramString.charAt(m + 1) == '\n'))
/* 4080 */           m++; break;
/*      */       case '\t':
/* 4084 */         this.currentOutput.print("\t");
/* 4085 */         break;
/*      */       case ' ':
/* 4087 */         this.currentOutput.print(" ");
/* 4088 */         break;
/*      */       default:
/* 4090 */         this.currentOutput.print(c);
/*      */       }
/*      */ 
/* 4094 */       if (k != 0)
/*      */       {
/* 4096 */         this.currentOutput.print("\n");
/* 4097 */         printTabs();
/* 4098 */         this.currentOutput.print("###");
/* 4099 */         k = 0;
/*      */       }
/*      */     }
/*      */ 
/* 4103 */     this.currentOutput.println();
/*      */   }
/*      */ 
/*      */   protected void genJavadocComment(Grammar paramGrammar)
/*      */   {
/* 4108 */     if (paramGrammar.comment != null)
/* 4109 */       _printJavadoc(paramGrammar.comment);
/*      */   }
/*      */ 
/*      */   protected void genJavadocComment(RuleSymbol paramRuleSymbol)
/*      */   {
/* 4115 */     if (paramRuleSymbol.comment != null)
/* 4116 */       _printJavadoc(paramRuleSymbol.comment);
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.PythonCodeGenerator
 * JD-Core Version:    0.6.2
 */