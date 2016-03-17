/*      */ package antlr;
/*      */ 
/*      */ import antlr.actions.cpp.ActionLexer;
/*      */ import antlr.collections.impl.BitSet;
/*      */ import antlr.collections.impl.Vector;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ 
/*      */ public class CppCodeGenerator extends CodeGenerator
/*      */ {
/*   25 */   boolean DEBUG_CPP_CODE_GENERATOR = false;
/*      */ 
/*   27 */   protected int syntacticPredLevel = 0;
/*      */ 
/*   30 */   protected boolean genAST = false;
/*      */ 
/*   33 */   protected boolean saveText = false;
/*      */ 
/*   36 */   protected boolean genHashLines = true;
/*      */ 
/*   38 */   protected boolean noConstructors = false;
/*      */   protected int outputLine;
/*      */   protected String outputFile;
/*   46 */   boolean usingCustomAST = false;
/*      */   String labeledElementType;
/*      */   String labeledElementASTType;
/*      */   String labeledElementASTInit;
/*      */   String labeledElementInit;
/*      */   String commonExtraArgs;
/*      */   String commonExtraParams;
/*      */   String commonLocalVars;
/*      */   String lt1Value;
/*      */   String exceptionThrown;
/*      */   String throwNoViable;
/*      */   RuleBlock currentRule;
/*      */   String currentASTResult;
/*   64 */   Hashtable treeVariableMap = new Hashtable();
/*      */ 
/*   69 */   Hashtable declaredASTVariables = new Hashtable();
/*      */ 
/*   72 */   int astVarNumber = 1;
/*      */ 
/*   74 */   protected static final String NONUNIQUE = new String();
/*      */   public static final int caseSizeThreshold = 127;
/*      */   private Vector semPreds;
/*      */   private Vector astTypes;
/*   84 */   private static String namespaceStd = "ANTLR_USE_NAMESPACE(std)";
/*   85 */   private static String namespaceAntlr = "ANTLR_USE_NAMESPACE(antlr)";
/*   86 */   private static NameSpace nameSpace = null;
/*      */   private static final String preIncludeCpp = "pre_include_cpp";
/*      */   private static final String preIncludeHpp = "pre_include_hpp";
/*      */   private static final String postIncludeCpp = "post_include_cpp";
/*      */   private static final String postIncludeHpp = "post_include_hpp";
/*      */ 
/*      */   public CppCodeGenerator()
/*      */   {
/*   99 */     this.charFormatter = new CppCharFormatter();
/*      */   }
/*      */ 
/*      */   protected int addSemPred(String paramString)
/*      */   {
/*  107 */     this.semPreds.appendElement(paramString);
/*  108 */     return this.semPreds.size() - 1;
/*      */   }
/*      */ 
/*      */   public void exitIfError() {
/*  112 */     if (this.antlrTool.hasError())
/*      */     {
/*  114 */       this.antlrTool.fatalError("Exiting due to errors.");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected int countLines(String paramString) {
/*  119 */     int i = 0;
/*  120 */     for (int j = 0; j < paramString.length(); j++)
/*      */     {
/*  122 */       if (paramString.charAt(j) == '\n')
/*  123 */         i++;
/*      */     }
/*  125 */     return i;
/*      */   }
/*      */ 
/*      */   protected void _print(String paramString)
/*      */   {
/*  133 */     if (paramString != null)
/*      */     {
/*  135 */       this.outputLine += countLines(paramString);
/*  136 */       this.currentOutput.print(paramString);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void _printAction(String paramString)
/*      */   {
/*  146 */     if (paramString != null)
/*      */     {
/*  148 */       this.outputLine += countLines(paramString) + 1;
/*  149 */       super._printAction(paramString);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void printAction(Token paramToken)
/*      */   {
/*  155 */     if (paramToken != null)
/*      */     {
/*  157 */       genLineNo(paramToken.getLine());
/*  158 */       printTabs();
/*  159 */       _printAction(processActionForSpecialSymbols(paramToken.getText(), paramToken.getLine(), null, null));
/*      */ 
/*  161 */       genLineNo2();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void printHeaderAction(String paramString)
/*      */   {
/*  169 */     Token localToken = (Token)this.behavior.headerActions.get(paramString);
/*  170 */     if (localToken != null)
/*      */     {
/*  172 */       genLineNo(localToken.getLine());
/*  173 */       println(processActionForSpecialSymbols(localToken.getText(), localToken.getLine(), null, null));
/*      */ 
/*  175 */       genLineNo2();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void _println(String paramString)
/*      */   {
/*  183 */     if (paramString != null) {
/*  184 */       this.outputLine += countLines(paramString) + 1;
/*  185 */       this.currentOutput.println(paramString);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void println(String paramString)
/*      */   {
/*  193 */     if (paramString != null) {
/*  194 */       printTabs();
/*  195 */       this.outputLine += countLines(paramString) + 1;
/*  196 */       this.currentOutput.println(paramString);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void genLineNo(int paramInt)
/*      */   {
/*  202 */     if (paramInt == 0) {
/*  203 */       paramInt++;
/*      */     }
/*  205 */     if (this.genHashLines)
/*  206 */       _println("#line " + paramInt + " \"" + this.antlrTool.fileMinusPath(this.antlrTool.grammarFile) + "\"");
/*      */   }
/*      */ 
/*      */   public void genLineNo(GrammarElement paramGrammarElement)
/*      */   {
/*  212 */     if (paramGrammarElement != null)
/*  213 */       genLineNo(paramGrammarElement.getLine());
/*      */   }
/*      */ 
/*      */   public void genLineNo(Token paramToken)
/*      */   {
/*  218 */     if (paramToken != null)
/*  219 */       genLineNo(paramToken.getLine());
/*      */   }
/*      */ 
/*      */   public void genLineNo2()
/*      */   {
/*  224 */     if (this.genHashLines)
/*      */     {
/*  226 */       _println("#line " + (this.outputLine + 1) + " \"" + this.outputFile + "\"");
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean charIsDigit(String paramString, int paramInt)
/*      */   {
/*  232 */     return (paramInt < paramString.length()) && (Character.isDigit(paramString.charAt(paramInt)));
/*      */   }
/*      */ 
/*      */   private String convertJavaToCppString(String paramString, boolean paramBoolean)
/*      */   {
/*  249 */     String str1 = new String();
/*  250 */     String str2 = paramString;
/*      */ 
/*  252 */     int i = 0;
/*  253 */     int j = 0;
/*      */ 
/*  255 */     if (paramBoolean)
/*      */     {
/*  257 */       if ((!paramString.startsWith("'")) || (!paramString.endsWith("'"))) {
/*  258 */         this.antlrTool.error("Invalid character literal: '" + paramString + "'");
/*      */       }
/*      */ 
/*      */     }
/*  262 */     else if ((!paramString.startsWith("\"")) || (!paramString.endsWith("\""))) {
/*  263 */       this.antlrTool.error("Invalid character string: '" + paramString + "'");
/*      */     }
/*  265 */     str2 = paramString.substring(1, paramString.length() - 1);
/*      */ 
/*  267 */     String str3 = "";
/*  268 */     int k = 255;
/*  269 */     if ((this.grammar instanceof LexerGrammar))
/*      */     {
/*  272 */       k = ((LexerGrammar)this.grammar).charVocabulary.size() - 1;
/*  273 */       if (k > 255) {
/*  274 */         str3 = "L";
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  279 */     while (i < str2.length())
/*      */     {
/*  281 */       if (str2.charAt(i) == '\\')
/*      */       {
/*  283 */         if (str2.length() == i + 1) {
/*  284 */           this.antlrTool.error("Invalid escape in char literal: '" + paramString + "' looking at '" + str2.substring(i) + "'");
/*      */         }
/*      */ 
/*  287 */         switch (str2.charAt(i + 1)) {
/*      */         case 'a':
/*  289 */           j = 7;
/*  290 */           i += 2;
/*  291 */           break;
/*      */         case 'b':
/*  293 */           j = 8;
/*  294 */           i += 2;
/*  295 */           break;
/*      */         case 't':
/*  297 */           j = 9;
/*  298 */           i += 2;
/*  299 */           break;
/*      */         case 'n':
/*  301 */           j = 10;
/*  302 */           i += 2;
/*  303 */           break;
/*      */         case 'f':
/*  305 */           j = 12;
/*  306 */           i += 2;
/*  307 */           break;
/*      */         case 'r':
/*  309 */           j = 13;
/*  310 */           i += 2;
/*  311 */           break;
/*      */         case '"':
/*      */         case '\'':
/*      */         case '\\':
/*  315 */           j = str2.charAt(i + 1);
/*  316 */           i += 2;
/*  317 */           break;
/*      */         case 'u':
/*  321 */           if (i + 5 < str2.length())
/*      */           {
/*  323 */             j = Character.digit(str2.charAt(i + 2), 16) * 16 * 16 * 16 + Character.digit(str2.charAt(i + 3), 16) * 16 * 16 + Character.digit(str2.charAt(i + 4), 16) * 16 + Character.digit(str2.charAt(i + 5), 16);
/*      */ 
/*  327 */             i += 6;
/*      */           }
/*      */           else {
/*  330 */             this.antlrTool.error("Invalid escape in char literal: '" + paramString + "' looking at '" + str2.substring(i) + "'");
/*  331 */           }break;
/*      */         case '0':
/*      */         case '1':
/*      */         case '2':
/*      */         case '3':
/*  337 */           if (charIsDigit(str2, i + 2))
/*      */           {
/*  339 */             if (charIsDigit(str2, i + 3))
/*      */             {
/*  341 */               j = (str2.charAt(i + 1) - '0') * 8 * 8 + (str2.charAt(i + 2) - '0') * 8 + (str2.charAt(i + 3) - '0');
/*      */ 
/*  343 */               i += 4;
/*      */             }
/*      */             else
/*      */             {
/*  347 */               j = (str2.charAt(i + 1) - '0') * 8 + (str2.charAt(i + 2) - '0');
/*  348 */               i += 3;
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  353 */             j = str2.charAt(i + 1) - '0';
/*  354 */             i += 2;
/*      */           }
/*  356 */           break;
/*      */         case '4':
/*      */         case '5':
/*      */         case '6':
/*      */         case '7':
/*  362 */           if (charIsDigit(str2, i + 2))
/*      */           {
/*  364 */             j = (str2.charAt(i + 1) - '0') * 8 + (str2.charAt(i + 2) - '0');
/*  365 */             i += 3;
/*      */           }
/*      */           else
/*      */           {
/*  369 */             j = str2.charAt(i + 1) - '0';
/*  370 */             i += 2;
/*      */           }break;
/*      */         }
/*  373 */         this.antlrTool.error("Unhandled escape in char literal: '" + paramString + "' looking at '" + str2.substring(i) + "'");
/*  374 */         j = 0;
/*      */       }
/*      */       else
/*      */       {
/*  378 */         j = str2.charAt(i++);
/*      */       }
/*  380 */       if ((this.grammar instanceof LexerGrammar))
/*      */       {
/*  382 */         if (j > k)
/*      */         {
/*      */           String str4;
/*  385 */           if ((32 <= j) && (j < 127))
/*  386 */             str4 = this.charFormatter.escapeChar(j, true);
/*      */           else {
/*  388 */             str4 = "0x" + Integer.toString(j, 16);
/*      */           }
/*  390 */           this.antlrTool.error("Character out of range in " + (paramBoolean ? "char literal" : "string constant") + ": '" + str2 + "'");
/*  391 */           this.antlrTool.error("Vocabulary size: " + k + " Character " + str4);
/*      */         }
/*      */       }
/*      */ 
/*  395 */       if (paramBoolean)
/*      */       {
/*  398 */         if (i != str2.length()) {
/*  399 */           this.antlrTool.error("Invalid char literal: '" + paramString + "'");
/*      */         }
/*  401 */         if (k <= 255)
/*      */         {
/*  403 */           if ((j <= 255) && ((j & 0x80) != 0))
/*      */           {
/*  407 */             str1 = "static_cast<unsigned char>('" + this.charFormatter.escapeChar(j, true) + "')";
/*      */           }
/*  409 */           else str1 = "'" + this.charFormatter.escapeChar(j, true) + "'";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  416 */           str1 = "L'" + this.charFormatter.escapeChar(j, true) + "'";
/*      */         }
/*      */       }
/*      */       else {
/*  420 */         str1 = str1 + this.charFormatter.escapeChar(j, true);
/*      */       }
/*      */     }
/*  422 */     if (!paramBoolean)
/*  423 */       str1 = str3 + "\"" + str1 + "\"";
/*  424 */     return str1;
/*      */   }
/*      */ 
/*      */   public void gen()
/*      */   {
/*      */     try
/*      */     {
/*  432 */       Enumeration localEnumeration = this.behavior.grammars.elements();
/*  433 */       while (localEnumeration.hasMoreElements()) {
/*  434 */         localObject = (Grammar)localEnumeration.nextElement();
/*  435 */         if (((Grammar)localObject).debuggingOutput) {
/*  436 */           this.antlrTool.error(((Grammar)localObject).getFilename() + ": C++ mode does not support -debug");
/*      */         }
/*      */ 
/*  439 */         ((Grammar)localObject).setGrammarAnalyzer(this.analyzer);
/*  440 */         ((Grammar)localObject).setCodeGenerator(this);
/*  441 */         this.analyzer.setGrammar((Grammar)localObject);
/*      */ 
/*  443 */         setupGrammarParameters((Grammar)localObject);
/*  444 */         ((Grammar)localObject).generate();
/*  445 */         exitIfError();
/*      */       }
/*      */ 
/*  449 */       Object localObject = this.behavior.tokenManagers.elements();
/*  450 */       while (((Enumeration)localObject).hasMoreElements()) {
/*  451 */         TokenManager localTokenManager = (TokenManager)((Enumeration)localObject).nextElement();
/*  452 */         if (!localTokenManager.isReadOnly())
/*      */         {
/*  456 */           genTokenTypes(localTokenManager);
/*      */ 
/*  458 */           genTokenInterchange(localTokenManager);
/*      */         }
/*  460 */         exitIfError();
/*      */       }
/*      */     }
/*      */     catch (IOException localIOException) {
/*  464 */       this.antlrTool.reportException(localIOException, null);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void gen(ActionElement paramActionElement)
/*      */   {
/*  471 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) System.out.println("genAction(" + paramActionElement + ")");
/*  472 */     if (paramActionElement.isSemPred) {
/*  473 */       genSemPred(paramActionElement.actionText, paramActionElement.line);
/*      */     }
/*      */     else {
/*  476 */       if (this.grammar.hasSyntacticPredicate) {
/*  477 */         println("if ( inputState->guessing==0 ) {");
/*  478 */         this.tabs += 1;
/*      */       }
/*      */ 
/*  481 */       ActionTransInfo localActionTransInfo = new ActionTransInfo();
/*  482 */       String str = processActionForSpecialSymbols(paramActionElement.actionText, paramActionElement.getLine(), this.currentRule, localActionTransInfo);
/*      */ 
/*  486 */       if (localActionTransInfo.refRuleRoot != null)
/*      */       {
/*  491 */         println(localActionTransInfo.refRuleRoot + " = " + this.labeledElementASTType + "(currentAST.root);");
/*      */       }
/*      */ 
/*  495 */       genLineNo(paramActionElement);
/*  496 */       printAction(str);
/*  497 */       genLineNo2();
/*      */ 
/*  499 */       if (localActionTransInfo.assignToRoot)
/*      */       {
/*  501 */         println("currentAST.root = " + localActionTransInfo.refRuleRoot + ";");
/*      */ 
/*  504 */         println("if ( " + localActionTransInfo.refRuleRoot + "!=" + this.labeledElementASTInit + " &&");
/*  505 */         this.tabs += 1;
/*  506 */         println(localActionTransInfo.refRuleRoot + "->getFirstChild() != " + this.labeledElementASTInit + " )");
/*  507 */         println("  currentAST.child = " + localActionTransInfo.refRuleRoot + "->getFirstChild();");
/*  508 */         this.tabs -= 1;
/*  509 */         println("else");
/*  510 */         this.tabs += 1;
/*  511 */         println("currentAST.child = " + localActionTransInfo.refRuleRoot + ";");
/*  512 */         this.tabs -= 1;
/*  513 */         println("currentAST.advanceChildToEnd();");
/*      */       }
/*      */ 
/*  516 */       if (this.grammar.hasSyntacticPredicate) {
/*  517 */         this.tabs -= 1;
/*  518 */         println("}");
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void gen(AlternativeBlock paramAlternativeBlock)
/*      */   {
/*  527 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) System.out.println("gen(" + paramAlternativeBlock + ")");
/*  528 */     println("{");
/*  529 */     genBlockPreamble(paramAlternativeBlock);
/*  530 */     genBlockInitAction(paramAlternativeBlock);
/*      */ 
/*  533 */     String str = this.currentASTResult;
/*  534 */     if (paramAlternativeBlock.getLabel() != null) {
/*  535 */       this.currentASTResult = paramAlternativeBlock.getLabel();
/*      */     }
/*      */ 
/*  538 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramAlternativeBlock);
/*      */ 
/*  540 */     CppBlockFinishingInfo localCppBlockFinishingInfo = genCommonBlock(paramAlternativeBlock, true);
/*  541 */     genBlockFinish(localCppBlockFinishingInfo, this.throwNoViable);
/*      */ 
/*  543 */     println("}");
/*      */ 
/*  546 */     this.currentASTResult = str;
/*      */   }
/*      */ 
/*      */   public void gen(BlockEndElement paramBlockEndElement)
/*      */   {
/*  554 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) System.out.println("genRuleEnd(" + paramBlockEndElement + ")");
/*      */   }
/*      */ 
/*      */   public void gen(CharLiteralElement paramCharLiteralElement)
/*      */   {
/*  561 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) {
/*  562 */       System.out.println("genChar(" + paramCharLiteralElement + ")");
/*      */     }
/*  564 */     if (!(this.grammar instanceof LexerGrammar)) {
/*  565 */       this.antlrTool.error("cannot ref character literals in grammar: " + paramCharLiteralElement);
/*      */     }
/*  567 */     if (paramCharLiteralElement.getLabel() != null) {
/*  568 */       println(paramCharLiteralElement.getLabel() + " = " + this.lt1Value + ";");
/*      */     }
/*      */ 
/*  571 */     boolean bool = this.saveText;
/*  572 */     this.saveText = ((this.saveText) && (paramCharLiteralElement.getAutoGenType() == 1));
/*      */ 
/*  575 */     if ((!this.saveText) || (paramCharLiteralElement.getAutoGenType() == 3)) {
/*  576 */       println("_saveIndex = text.length();");
/*      */     }
/*  578 */     print(paramCharLiteralElement.not ? "matchNot(" : "match(");
/*  579 */     _print(convertJavaToCppString(paramCharLiteralElement.atomText, true));
/*  580 */     _println(" /* charlit */ );");
/*      */ 
/*  582 */     if ((!this.saveText) || (paramCharLiteralElement.getAutoGenType() == 3)) {
/*  583 */       println("text.erase(_saveIndex);");
/*      */     }
/*  585 */     this.saveText = bool;
/*      */   }
/*      */ 
/*      */   public void gen(CharRangeElement paramCharRangeElement)
/*      */   {
/*  592 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) {
/*  593 */       System.out.println("genCharRangeElement(" + paramCharRangeElement.beginText + ".." + paramCharRangeElement.endText + ")");
/*      */     }
/*  595 */     if (!(this.grammar instanceof LexerGrammar)) {
/*  596 */       this.antlrTool.error("cannot ref character range in grammar: " + paramCharRangeElement);
/*      */     }
/*  598 */     if ((paramCharRangeElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  599 */       println(paramCharRangeElement.getLabel() + " = " + this.lt1Value + ";");
/*      */     }
/*      */ 
/*  602 */     int i = ((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramCharRangeElement.getAutoGenType() == 3)) ? 1 : 0;
/*      */ 
/*  606 */     if (i != 0) {
/*  607 */       println("_saveIndex=text.length();");
/*      */     }
/*  609 */     println("matchRange(" + convertJavaToCppString(paramCharRangeElement.beginText, true) + "," + convertJavaToCppString(paramCharRangeElement.endText, true) + ");");
/*      */ 
/*  612 */     if (i != 0)
/*  613 */       println("text.erase(_saveIndex);");
/*      */   }
/*      */ 
/*      */   public void gen(LexerGrammar paramLexerGrammar) throws IOException
/*      */   {
/*  618 */     if (paramLexerGrammar.debuggingOutput) {
/*  619 */       this.semPreds = new Vector();
/*      */     }
/*  621 */     if (paramLexerGrammar.charVocabulary.size() > 256) {
/*  622 */       this.antlrTool.warning(paramLexerGrammar.getFilename() + ": Vocabularies of this size still experimental in C++ mode (vocabulary size now: " + paramLexerGrammar.charVocabulary.size() + ")");
/*      */     }
/*  624 */     setGrammar(paramLexerGrammar);
/*  625 */     if (!(this.grammar instanceof LexerGrammar)) {
/*  626 */       this.antlrTool.panic("Internal error generating lexer");
/*      */     }
/*      */ 
/*  629 */     genBody(paramLexerGrammar);
/*  630 */     genInclude(paramLexerGrammar);
/*      */   }
/*      */ 
/*      */   public void gen(OneOrMoreBlock paramOneOrMoreBlock)
/*      */   {
/*  636 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) System.out.println("gen+(" + paramOneOrMoreBlock + ")");
/*      */ 
/*  639 */     println("{ // ( ... )+");
/*  640 */     genBlockPreamble(paramOneOrMoreBlock);
/*      */     String str2;
/*  641 */     if (paramOneOrMoreBlock.getLabel() != null) {
/*  642 */       str2 = "_cnt_" + paramOneOrMoreBlock.getLabel();
/*      */     }
/*      */     else {
/*  645 */       str2 = "_cnt" + paramOneOrMoreBlock.ID;
/*      */     }
/*  647 */     println("int " + str2 + "=0;");
/*      */     String str1;
/*  648 */     if (paramOneOrMoreBlock.getLabel() != null) {
/*  649 */       str1 = paramOneOrMoreBlock.getLabel();
/*      */     }
/*      */     else {
/*  652 */       str1 = "_loop" + paramOneOrMoreBlock.ID;
/*      */     }
/*      */ 
/*  655 */     println("for (;;) {");
/*  656 */     this.tabs += 1;
/*      */ 
/*  659 */     genBlockInitAction(paramOneOrMoreBlock);
/*      */ 
/*  662 */     String str3 = this.currentASTResult;
/*  663 */     if (paramOneOrMoreBlock.getLabel() != null) {
/*  664 */       this.currentASTResult = paramOneOrMoreBlock.getLabel();
/*      */     }
/*      */ 
/*  667 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramOneOrMoreBlock);
/*      */ 
/*  679 */     int i = 0;
/*  680 */     int j = this.grammar.maxk;
/*      */ 
/*  682 */     if ((!paramOneOrMoreBlock.greedy) && (paramOneOrMoreBlock.exitLookaheadDepth <= this.grammar.maxk) && (paramOneOrMoreBlock.exitCache[paramOneOrMoreBlock.exitLookaheadDepth].containsEpsilon()))
/*      */     {
/*  686 */       i = 1;
/*  687 */       j = paramOneOrMoreBlock.exitLookaheadDepth;
/*      */     }
/*  689 */     else if ((!paramOneOrMoreBlock.greedy) && (paramOneOrMoreBlock.exitLookaheadDepth == 2147483647))
/*      */     {
/*  692 */       i = 1;
/*      */     }
/*      */ 
/*  697 */     if (i != 0) {
/*  698 */       if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) {
/*  699 */         System.out.println("nongreedy (...)+ loop; exit depth is " + paramOneOrMoreBlock.exitLookaheadDepth);
/*      */       }
/*      */ 
/*  702 */       localObject = getLookaheadTestExpression(paramOneOrMoreBlock.exitCache, j);
/*      */ 
/*  705 */       println("// nongreedy exit test");
/*  706 */       println("if ( " + str2 + ">=1 && " + (String)localObject + ") goto " + str1 + ";");
/*      */     }
/*      */ 
/*  709 */     Object localObject = genCommonBlock(paramOneOrMoreBlock, false);
/*  710 */     genBlockFinish((CppBlockFinishingInfo)localObject, "if ( " + str2 + ">=1 ) { goto " + str1 + "; } else {" + this.throwNoViable + "}");
/*      */ 
/*  715 */     println(str2 + "++;");
/*  716 */     this.tabs -= 1;
/*  717 */     println("}");
/*  718 */     println(str1 + ":;");
/*  719 */     println("}  // ( ... )+");
/*      */ 
/*  722 */     this.currentASTResult = str3;
/*      */   }
/*      */ 
/*      */   public void gen(ParserGrammar paramParserGrammar)
/*      */     throws IOException
/*      */   {
/*  729 */     if (paramParserGrammar.debuggingOutput) {
/*  730 */       this.semPreds = new Vector();
/*      */     }
/*  732 */     setGrammar(paramParserGrammar);
/*  733 */     if (!(this.grammar instanceof ParserGrammar)) {
/*  734 */       this.antlrTool.panic("Internal error generating parser");
/*      */     }
/*      */ 
/*  737 */     genBody(paramParserGrammar);
/*  738 */     genInclude(paramParserGrammar);
/*      */   }
/*      */ 
/*      */   public void gen(RuleRefElement paramRuleRefElement)
/*      */   {
/*  745 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) System.out.println("genRR(" + paramRuleRefElement + ")");
/*  746 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(paramRuleRefElement.targetRule);
/*  747 */     if ((localRuleSymbol == null) || (!localRuleSymbol.isDefined()))
/*      */     {
/*  750 */       this.antlrTool.error("Rule '" + paramRuleRefElement.targetRule + "' is not defined", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*  751 */       return;
/*      */     }
/*  753 */     if (!(localRuleSymbol instanceof RuleSymbol))
/*      */     {
/*  756 */       this.antlrTool.error("'" + paramRuleRefElement.targetRule + "' does not name a grammar rule", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*  757 */       return;
/*      */     }
/*      */ 
/*  760 */     genErrorTryForElement(paramRuleRefElement);
/*      */ 
/*  764 */     if (((this.grammar instanceof TreeWalkerGrammar)) && (paramRuleRefElement.getLabel() != null) && (this.syntacticPredLevel == 0))
/*      */     {
/*  768 */       println(paramRuleRefElement.getLabel() + " = (_t == ASTNULL) ? " + this.labeledElementASTInit + " : " + this.lt1Value + ";");
/*      */     }
/*      */ 
/*  773 */     if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramRuleRefElement.getAutoGenType() == 3)))
/*      */     {
/*  775 */       println("_saveIndex = text.length();");
/*      */     }
/*      */ 
/*  779 */     printTabs();
/*  780 */     if (paramRuleRefElement.idAssign != null)
/*      */     {
/*  783 */       if (localRuleSymbol.block.returnAction == null)
/*      */       {
/*  785 */         this.antlrTool.warning("Rule '" + paramRuleRefElement.targetRule + "' has no return type", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*      */       }
/*  787 */       _print(paramRuleRefElement.idAssign + "=");
/*      */     }
/*  790 */     else if ((!(this.grammar instanceof LexerGrammar)) && (this.syntacticPredLevel == 0) && (localRuleSymbol.block.returnAction != null))
/*      */     {
/*  792 */       this.antlrTool.warning("Rule '" + paramRuleRefElement.targetRule + "' returns a value", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*      */     }
/*      */ 
/*  797 */     GenRuleInvocation(paramRuleRefElement);
/*      */ 
/*  800 */     if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramRuleRefElement.getAutoGenType() == 3))) {
/*  801 */       println("text.erase(_saveIndex);");
/*      */     }
/*      */ 
/*  805 */     if (this.syntacticPredLevel == 0)
/*      */     {
/*  807 */       int i = (this.grammar.hasSyntacticPredicate) && (((this.grammar.buildAST) && (paramRuleRefElement.getLabel() != null)) || ((this.genAST) && (paramRuleRefElement.getAutoGenType() == 1))) ? 1 : 0;
/*      */ 
/*  815 */       if (i != 0) {
/*  816 */         println("if (inputState->guessing==0) {");
/*  817 */         this.tabs += 1;
/*      */       }
/*      */ 
/*  820 */       if ((this.grammar.buildAST) && (paramRuleRefElement.getLabel() != null))
/*      */       {
/*  824 */         println(paramRuleRefElement.getLabel() + "_AST = returnAST;");
/*      */       }
/*      */ 
/*  827 */       if (this.genAST)
/*      */       {
/*  829 */         switch (paramRuleRefElement.getAutoGenType())
/*      */         {
/*      */         case 1:
/*  832 */           if (this.usingCustomAST)
/*  833 */             println("astFactory->addASTChild(currentAST, " + namespaceAntlr + "RefAST(returnAST));");
/*      */           else
/*  835 */             println("astFactory->addASTChild( currentAST, returnAST );");
/*  836 */           break;
/*      */         case 2:
/*  840 */           this.antlrTool.error("Internal: encountered ^ after rule reference");
/*  841 */           break;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  848 */       if (((this.grammar instanceof LexerGrammar)) && (paramRuleRefElement.getLabel() != null))
/*      */       {
/*  850 */         println(paramRuleRefElement.getLabel() + "=_returnToken;");
/*      */       }
/*      */ 
/*  853 */       if (i != 0)
/*      */       {
/*  855 */         this.tabs -= 1;
/*  856 */         println("}");
/*      */       }
/*      */     }
/*  859 */     genErrorCatchForElement(paramRuleRefElement);
/*      */   }
/*      */ 
/*      */   public void gen(StringLiteralElement paramStringLiteralElement)
/*      */   {
/*  865 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) System.out.println("genString(" + paramStringLiteralElement + ")");
/*      */ 
/*  868 */     if ((paramStringLiteralElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  869 */       println(paramStringLiteralElement.getLabel() + " = " + this.lt1Value + ";");
/*      */     }
/*      */ 
/*  873 */     genElementAST(paramStringLiteralElement);
/*      */ 
/*  876 */     boolean bool = this.saveText;
/*  877 */     this.saveText = ((this.saveText) && (paramStringLiteralElement.getAutoGenType() == 1));
/*      */ 
/*  880 */     genMatch(paramStringLiteralElement);
/*      */ 
/*  882 */     this.saveText = bool;
/*      */ 
/*  885 */     if ((this.grammar instanceof TreeWalkerGrammar))
/*  886 */       println("_t = _t->getNextSibling();");
/*      */   }
/*      */ 
/*      */   public void gen(TokenRangeElement paramTokenRangeElement)
/*      */   {
/*  893 */     genErrorTryForElement(paramTokenRangeElement);
/*  894 */     if ((paramTokenRangeElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  895 */       println(paramTokenRangeElement.getLabel() + " = " + this.lt1Value + ";");
/*      */     }
/*      */ 
/*  899 */     genElementAST(paramTokenRangeElement);
/*      */ 
/*  902 */     println("matchRange(" + paramTokenRangeElement.beginText + "," + paramTokenRangeElement.endText + ");");
/*  903 */     genErrorCatchForElement(paramTokenRangeElement);
/*      */   }
/*      */ 
/*      */   public void gen(TokenRefElement paramTokenRefElement)
/*      */   {
/*  909 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) System.out.println("genTokenRef(" + paramTokenRefElement + ")");
/*  910 */     if ((this.grammar instanceof LexerGrammar)) {
/*  911 */       this.antlrTool.panic("Token reference found in lexer");
/*      */     }
/*  913 */     genErrorTryForElement(paramTokenRefElement);
/*      */ 
/*  915 */     if ((paramTokenRefElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  916 */       println(paramTokenRefElement.getLabel() + " = " + this.lt1Value + ";");
/*      */     }
/*      */ 
/*  920 */     genElementAST(paramTokenRefElement);
/*      */ 
/*  922 */     genMatch(paramTokenRefElement);
/*  923 */     genErrorCatchForElement(paramTokenRefElement);
/*      */ 
/*  926 */     if ((this.grammar instanceof TreeWalkerGrammar))
/*  927 */       println("_t = _t->getNextSibling();");
/*      */   }
/*      */ 
/*      */   public void gen(TreeElement paramTreeElement)
/*      */   {
/*  932 */     println(this.labeledElementType + " __t" + paramTreeElement.ID + " = _t;");
/*      */ 
/*  935 */     if (paramTreeElement.root.getLabel() != null) {
/*  936 */       println(paramTreeElement.root.getLabel() + " = (_t == " + this.labeledElementType + "(ASTNULL)) ? " + this.labeledElementASTInit + " : _t;");
/*      */     }
/*      */ 
/*  940 */     if (paramTreeElement.root.getAutoGenType() == 3) {
/*  941 */       this.antlrTool.error("Suffixing a root node with '!' is not implemented", this.grammar.getFilename(), paramTreeElement.getLine(), paramTreeElement.getColumn());
/*      */ 
/*  943 */       paramTreeElement.root.setAutoGenType(1);
/*      */     }
/*  945 */     if (paramTreeElement.root.getAutoGenType() == 2) {
/*  946 */       this.antlrTool.warning("Suffixing a root node with '^' is redundant; already a root", this.grammar.getFilename(), paramTreeElement.getLine(), paramTreeElement.getColumn());
/*      */ 
/*  948 */       paramTreeElement.root.setAutoGenType(1);
/*      */     }
/*      */ 
/*  952 */     genElementAST(paramTreeElement.root);
/*  953 */     if (this.grammar.buildAST)
/*      */     {
/*  955 */       println(namespaceAntlr + "ASTPair __currentAST" + paramTreeElement.ID + " = currentAST;");
/*      */ 
/*  957 */       println("currentAST.root = currentAST.child;");
/*  958 */       println("currentAST.child = " + this.labeledElementASTInit + ";");
/*      */     }
/*      */ 
/*  962 */     if ((paramTreeElement.root instanceof WildcardElement)) {
/*  963 */       println("if ( _t == ASTNULL ) throw " + namespaceAntlr + "MismatchedTokenException();");
/*      */     }
/*      */     else {
/*  966 */       genMatch(paramTreeElement.root);
/*      */     }
/*      */ 
/*  969 */     println("_t = _t->getFirstChild();");
/*      */ 
/*  972 */     for (int i = 0; i < paramTreeElement.getAlternatives().size(); i++) {
/*  973 */       Alternative localAlternative = paramTreeElement.getAlternativeAt(i);
/*  974 */       AlternativeElement localAlternativeElement = localAlternative.head;
/*  975 */       while (localAlternativeElement != null) {
/*  976 */         localAlternativeElement.generate();
/*  977 */         localAlternativeElement = localAlternativeElement.next;
/*      */       }
/*      */     }
/*      */ 
/*  981 */     if (this.grammar.buildAST)
/*      */     {
/*  984 */       println("currentAST = __currentAST" + paramTreeElement.ID + ";");
/*      */     }
/*      */ 
/*  987 */     println("_t = __t" + paramTreeElement.ID + ";");
/*      */ 
/*  989 */     println("_t = _t->getNextSibling();");
/*      */   }
/*      */ 
/*      */   public void gen(TreeWalkerGrammar paramTreeWalkerGrammar) throws IOException {
/*  993 */     setGrammar(paramTreeWalkerGrammar);
/*  994 */     if (!(this.grammar instanceof TreeWalkerGrammar)) {
/*  995 */       this.antlrTool.panic("Internal error generating tree-walker");
/*      */     }
/*      */ 
/*  998 */     genBody(paramTreeWalkerGrammar);
/*  999 */     genInclude(paramTreeWalkerGrammar);
/*      */   }
/*      */ 
/*      */   public void gen(WildcardElement paramWildcardElement)
/*      */   {
/* 1006 */     if ((paramWildcardElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/* 1007 */       println(paramWildcardElement.getLabel() + " = " + this.lt1Value + ";");
/*      */     }
/*      */ 
/* 1011 */     genElementAST(paramWildcardElement);
/*      */ 
/* 1013 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1014 */       println("if ( _t == " + this.labeledElementASTInit + " ) throw " + namespaceAntlr + "MismatchedTokenException();");
/*      */     }
/* 1016 */     else if ((this.grammar instanceof LexerGrammar)) {
/* 1017 */       if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramWildcardElement.getAutoGenType() == 3)))
/*      */       {
/* 1019 */         println("_saveIndex = text.length();");
/*      */       }
/* 1021 */       println("matchNot(EOF/*_CHAR*/);");
/* 1022 */       if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramWildcardElement.getAutoGenType() == 3)))
/*      */       {
/* 1024 */         println("text.erase(_saveIndex);");
/*      */       }
/*      */     }
/*      */     else {
/* 1028 */       println("matchNot(" + getValueString(1) + ");");
/*      */     }
/*      */ 
/* 1032 */     if ((this.grammar instanceof TreeWalkerGrammar))
/* 1033 */       println("_t = _t->getNextSibling();");
/*      */   }
/*      */ 
/*      */   public void gen(ZeroOrMoreBlock paramZeroOrMoreBlock)
/*      */   {
/* 1040 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) System.out.println("gen*(" + paramZeroOrMoreBlock + ")");
/* 1041 */     println("{ // ( ... )*");
/* 1042 */     genBlockPreamble(paramZeroOrMoreBlock);
/*      */     String str1;
/* 1044 */     if (paramZeroOrMoreBlock.getLabel() != null) {
/* 1045 */       str1 = paramZeroOrMoreBlock.getLabel();
/*      */     }
/*      */     else {
/* 1048 */       str1 = "_loop" + paramZeroOrMoreBlock.ID;
/*      */     }
/* 1050 */     println("for (;;) {");
/* 1051 */     this.tabs += 1;
/*      */ 
/* 1054 */     genBlockInitAction(paramZeroOrMoreBlock);
/*      */ 
/* 1057 */     String str2 = this.currentASTResult;
/* 1058 */     if (paramZeroOrMoreBlock.getLabel() != null) {
/* 1059 */       this.currentASTResult = paramZeroOrMoreBlock.getLabel();
/*      */     }
/*      */ 
/* 1062 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramZeroOrMoreBlock);
/*      */ 
/* 1074 */     int i = 0;
/* 1075 */     int j = this.grammar.maxk;
/*      */ 
/* 1077 */     if ((!paramZeroOrMoreBlock.greedy) && (paramZeroOrMoreBlock.exitLookaheadDepth <= this.grammar.maxk) && (paramZeroOrMoreBlock.exitCache[paramZeroOrMoreBlock.exitLookaheadDepth].containsEpsilon()))
/*      */     {
/* 1081 */       i = 1;
/* 1082 */       j = paramZeroOrMoreBlock.exitLookaheadDepth;
/*      */     }
/* 1084 */     else if ((!paramZeroOrMoreBlock.greedy) && (paramZeroOrMoreBlock.exitLookaheadDepth == 2147483647))
/*      */     {
/* 1087 */       i = 1;
/*      */     }
/* 1089 */     if (i != 0) {
/* 1090 */       if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) {
/* 1091 */         System.out.println("nongreedy (...)* loop; exit depth is " + paramZeroOrMoreBlock.exitLookaheadDepth);
/*      */       }
/*      */ 
/* 1094 */       localObject = getLookaheadTestExpression(paramZeroOrMoreBlock.exitCache, j);
/*      */ 
/* 1097 */       println("// nongreedy exit test");
/* 1098 */       println("if (" + (String)localObject + ") goto " + str1 + ";");
/*      */     }
/*      */ 
/* 1101 */     Object localObject = genCommonBlock(paramZeroOrMoreBlock, false);
/* 1102 */     genBlockFinish((CppBlockFinishingInfo)localObject, "goto " + str1 + ";");
/*      */ 
/* 1104 */     this.tabs -= 1;
/* 1105 */     println("}");
/* 1106 */     println(str1 + ":;");
/* 1107 */     println("} // ( ... )*");
/*      */ 
/* 1110 */     this.currentASTResult = str2;
/*      */   }
/*      */ 
/*      */   protected void genAlt(Alternative paramAlternative, AlternativeBlock paramAlternativeBlock)
/*      */   {
/* 1119 */     boolean bool1 = this.genAST;
/* 1120 */     this.genAST = ((this.genAST) && (paramAlternative.getAutoGen()));
/*      */ 
/* 1122 */     boolean bool2 = this.saveText;
/* 1123 */     this.saveText = ((this.saveText) && (paramAlternative.getAutoGen()));
/*      */ 
/* 1126 */     Hashtable localHashtable = this.treeVariableMap;
/* 1127 */     this.treeVariableMap = new Hashtable();
/*      */ 
/* 1130 */     if (paramAlternative.exceptionSpec != null) {
/* 1131 */       println("try {      // for error handling");
/* 1132 */       this.tabs += 1;
/*      */     }
/*      */ 
/* 1135 */     AlternativeElement localAlternativeElement = paramAlternative.head;
/* 1136 */     while (!(localAlternativeElement instanceof BlockEndElement)) {
/* 1137 */       localAlternativeElement.generate();
/* 1138 */       localAlternativeElement = localAlternativeElement.next;
/*      */     }
/*      */ 
/* 1141 */     if (this.genAST)
/*      */     {
/* 1143 */       if ((paramAlternativeBlock instanceof RuleBlock))
/*      */       {
/* 1146 */         RuleBlock localRuleBlock = (RuleBlock)paramAlternativeBlock;
/* 1147 */         if (this.usingCustomAST)
/* 1148 */           println(localRuleBlock.getRuleName() + "_AST = " + this.labeledElementASTType + "(currentAST.root);");
/*      */         else
/* 1150 */           println(localRuleBlock.getRuleName() + "_AST = currentAST.root;");
/*      */       }
/* 1152 */       else if (paramAlternativeBlock.getLabel() != null)
/*      */       {
/* 1155 */         this.antlrTool.warning("Labeled subrules are not implemented", this.grammar.getFilename(), paramAlternativeBlock.getLine(), paramAlternativeBlock.getColumn());
/*      */       }
/*      */     }
/*      */ 
/* 1159 */     if (paramAlternative.exceptionSpec != null)
/*      */     {
/* 1162 */       this.tabs -= 1;
/* 1163 */       println("}");
/* 1164 */       genErrorHandler(paramAlternative.exceptionSpec);
/*      */     }
/*      */ 
/* 1167 */     this.genAST = bool1;
/* 1168 */     this.saveText = bool2;
/*      */ 
/* 1170 */     this.treeVariableMap = localHashtable;
/*      */   }
/*      */ 
/*      */   protected void genBitsets(Vector paramVector, int paramInt, String paramString)
/*      */   {
/* 1192 */     TokenManager localTokenManager = this.grammar.tokenManager;
/*      */ 
/* 1194 */     println("");
/*      */ 
/* 1196 */     for (int i = 0; i < paramVector.size(); i++)
/*      */     {
/* 1198 */       BitSet localBitSet = (BitSet)paramVector.elementAt(i);
/*      */ 
/* 1200 */       localBitSet.growToInclude(paramInt);
/*      */ 
/* 1203 */       println("const unsigned long " + paramString + getBitsetName(i) + "_data_" + "[] = { " + localBitSet.toStringOfHalfWords() + " };");
/*      */ 
/* 1210 */       String str = "// ";
/*      */ 
/* 1212 */       for (int j = 0; j < localTokenManager.getVocabulary().size(); j++)
/*      */       {
/* 1214 */         if (localBitSet.member(j))
/*      */         {
/* 1216 */           if ((this.grammar instanceof LexerGrammar))
/*      */           {
/* 1219 */             if ((32 <= j) && (j < 127) && (j != 92))
/* 1220 */               str = str + this.charFormatter.escapeChar(j, true) + " ";
/*      */             else
/* 1222 */               str = str + "0x" + Integer.toString(j, 16) + " ";
/*      */           }
/*      */           else {
/* 1225 */             str = str + localTokenManager.getTokenStringAt(j) + " ";
/*      */           }
/* 1227 */           if (str.length() > 70)
/*      */           {
/* 1229 */             println(str);
/* 1230 */             str = "// ";
/*      */           }
/*      */         }
/*      */       }
/* 1234 */       if (str != "// ") {
/* 1235 */         println(str);
/*      */       }
/*      */ 
/* 1238 */       println("const " + namespaceAntlr + "BitSet " + paramString + getBitsetName(i) + "(" + getBitsetName(i) + "_data_," + localBitSet.size() / 32 + ");");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void genBitsetsHeader(Vector paramVector, int paramInt)
/*      */   {
/* 1249 */     println("");
/* 1250 */     for (int i = 0; i < paramVector.size(); i++)
/*      */     {
/* 1252 */       BitSet localBitSet = (BitSet)paramVector.elementAt(i);
/*      */ 
/* 1254 */       localBitSet.growToInclude(paramInt);
/*      */ 
/* 1256 */       println("static const unsigned long " + getBitsetName(i) + "_data_" + "[];");
/*      */ 
/* 1258 */       println("static const " + namespaceAntlr + "BitSet " + getBitsetName(i) + ";");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void genBlockFinish(CppBlockFinishingInfo paramCppBlockFinishingInfo, String paramString)
/*      */   {
/* 1269 */     if ((paramCppBlockFinishingInfo.needAnErrorClause) && ((paramCppBlockFinishingInfo.generatedAnIf) || (paramCppBlockFinishingInfo.generatedSwitch)))
/*      */     {
/* 1271 */       if (paramCppBlockFinishingInfo.generatedAnIf) {
/* 1272 */         println("else {");
/*      */       }
/*      */       else {
/* 1275 */         println("{");
/*      */       }
/* 1277 */       this.tabs += 1;
/* 1278 */       println(paramString);
/* 1279 */       this.tabs -= 1;
/* 1280 */       println("}");
/*      */     }
/*      */ 
/* 1283 */     if (paramCppBlockFinishingInfo.postscript != null)
/* 1284 */       println(paramCppBlockFinishingInfo.postscript);
/*      */   }
/*      */ 
/*      */   protected void genBlockInitAction(AlternativeBlock paramAlternativeBlock)
/*      */   {
/* 1294 */     if (paramAlternativeBlock.initAction != null) {
/* 1295 */       genLineNo(paramAlternativeBlock);
/* 1296 */       printAction(processActionForSpecialSymbols(paramAlternativeBlock.initAction, paramAlternativeBlock.line, this.currentRule, null));
/*      */ 
/* 1298 */       genLineNo2();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void genBlockPreamble(AlternativeBlock paramAlternativeBlock)
/*      */   {
/* 1308 */     if ((paramAlternativeBlock instanceof RuleBlock)) {
/* 1309 */       RuleBlock localRuleBlock = (RuleBlock)paramAlternativeBlock;
/* 1310 */       if (localRuleBlock.labeledElements != null)
/* 1311 */         for (int i = 0; i < localRuleBlock.labeledElements.size(); i++)
/*      */         {
/* 1313 */           AlternativeElement localAlternativeElement = (AlternativeElement)localRuleBlock.labeledElements.elementAt(i);
/*      */ 
/* 1319 */           if (((localAlternativeElement instanceof RuleRefElement)) || (((localAlternativeElement instanceof AlternativeBlock)) && (!(localAlternativeElement instanceof RuleBlock)) && (!(localAlternativeElement instanceof SynPredBlock))))
/*      */           {
/* 1325 */             if ((!(localAlternativeElement instanceof RuleRefElement)) && (((AlternativeBlock)localAlternativeElement).not) && (this.analyzer.subruleCanBeInverted((AlternativeBlock)localAlternativeElement, this.grammar instanceof LexerGrammar)))
/*      */             {
/* 1332 */               println(this.labeledElementType + " " + localAlternativeElement.getLabel() + " = " + this.labeledElementInit + ";");
/* 1333 */               if (this.grammar.buildAST) {
/* 1334 */                 genASTDeclaration(localAlternativeElement);
/*      */               }
/*      */             }
/*      */             else
/*      */             {
/* 1339 */               if (this.grammar.buildAST)
/*      */               {
/* 1343 */                 genASTDeclaration(localAlternativeElement);
/*      */               }
/* 1345 */               if ((this.grammar instanceof LexerGrammar)) {
/* 1346 */                 println(namespaceAntlr + "RefToken " + localAlternativeElement.getLabel() + ";");
/*      */               }
/* 1348 */               if ((this.grammar instanceof TreeWalkerGrammar))
/*      */               {
/* 1350 */                 println(this.labeledElementType + " " + localAlternativeElement.getLabel() + " = " + this.labeledElementInit + ";");
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 1358 */             println(this.labeledElementType + " " + localAlternativeElement.getLabel() + " = " + this.labeledElementInit + ";");
/*      */ 
/* 1360 */             if (this.grammar.buildAST)
/*      */             {
/* 1362 */               if (((localAlternativeElement instanceof GrammarAtom)) && (((GrammarAtom)localAlternativeElement).getASTNodeType() != null))
/*      */               {
/* 1365 */                 GrammarAtom localGrammarAtom = (GrammarAtom)localAlternativeElement;
/* 1366 */                 genASTDeclaration(localAlternativeElement, "Ref" + localGrammarAtom.getASTNodeType());
/*      */               }
/*      */               else
/*      */               {
/* 1370 */                 genASTDeclaration(localAlternativeElement);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void genBody(LexerGrammar paramLexerGrammar) throws IOException
/*      */   {
/* 1380 */     this.outputFile = (this.grammar.getClassName() + ".cpp");
/* 1381 */     this.outputLine = 1;
/* 1382 */     this.currentOutput = this.antlrTool.openOutputFile(this.outputFile);
/*      */ 
/* 1385 */     this.genAST = false;
/* 1386 */     this.saveText = true;
/*      */ 
/* 1388 */     this.tabs = 0;
/*      */ 
/* 1391 */     genHeader(this.outputFile);
/*      */ 
/* 1393 */     printHeaderAction("pre_include_cpp");
/*      */ 
/* 1395 */     println("#include \"" + this.grammar.getClassName() + ".hpp\"");
/* 1396 */     println("#include <antlr/CharBuffer.hpp>");
/* 1397 */     println("#include <antlr/TokenStreamException.hpp>");
/* 1398 */     println("#include <antlr/TokenStreamIOException.hpp>");
/* 1399 */     println("#include <antlr/TokenStreamRecognitionException.hpp>");
/* 1400 */     println("#include <antlr/CharStreamException.hpp>");
/* 1401 */     println("#include <antlr/CharStreamIOException.hpp>");
/* 1402 */     println("#include <antlr/NoViableAltForCharException.hpp>");
/* 1403 */     if (this.grammar.debuggingOutput)
/* 1404 */       println("#include <antlr/DebuggingInputBuffer.hpp>");
/* 1405 */     println("");
/* 1406 */     printHeaderAction("post_include_cpp");
/*      */ 
/* 1408 */     if (nameSpace != null) {
/* 1409 */       nameSpace.emitDeclarations(this.currentOutput);
/*      */     }
/*      */ 
/* 1412 */     printAction(this.grammar.preambleAction);
/*      */ 
/* 1415 */     String str = null;
/* 1416 */     if (this.grammar.superClass != null) {
/* 1417 */       str = this.grammar.superClass;
/*      */     }
/*      */     else {
/* 1420 */       str = this.grammar.getSuperClass();
/* 1421 */       if (str.lastIndexOf('.') != -1)
/* 1422 */         str = str.substring(str.lastIndexOf('.') + 1);
/* 1423 */       str = namespaceAntlr + str;
/*      */     }
/*      */ 
/* 1426 */     if (this.noConstructors)
/*      */     {
/* 1428 */       println("#if 0");
/* 1429 */       println("// constructor creation turned of with 'noConstructor' option");
/*      */     }
/*      */ 
/* 1434 */     println(this.grammar.getClassName() + "::" + this.grammar.getClassName() + "(" + namespaceStd + "istream& in)");
/* 1435 */     this.tabs += 1;
/*      */ 
/* 1437 */     if (this.grammar.debuggingOutput)
/* 1438 */       println(": " + str + "(new " + namespaceAntlr + "DebuggingInputBuffer(new " + namespaceAntlr + "CharBuffer(in))," + paramLexerGrammar.caseSensitive + ")");
/*      */     else
/* 1440 */       println(": " + str + "(new " + namespaceAntlr + "CharBuffer(in)," + paramLexerGrammar.caseSensitive + ")");
/* 1441 */     this.tabs -= 1;
/* 1442 */     println("{");
/* 1443 */     this.tabs += 1;
/*      */ 
/* 1447 */     if (this.grammar.debuggingOutput) {
/* 1448 */       println("setRuleNames(_ruleNames);");
/* 1449 */       println("setSemPredNames(_semPredNames);");
/* 1450 */       println("setupDebugging();");
/*      */     }
/*      */ 
/* 1454 */     println("initLiterals();");
/* 1455 */     this.tabs -= 1;
/* 1456 */     println("}");
/* 1457 */     println("");
/*      */ 
/* 1460 */     println(this.grammar.getClassName() + "::" + this.grammar.getClassName() + "(" + namespaceAntlr + "InputBuffer& ib)");
/* 1461 */     this.tabs += 1;
/*      */ 
/* 1463 */     if (this.grammar.debuggingOutput)
/* 1464 */       println(": " + str + "(new " + namespaceAntlr + "DebuggingInputBuffer(ib)," + paramLexerGrammar.caseSensitive + ")");
/*      */     else
/* 1466 */       println(": " + str + "(ib," + paramLexerGrammar.caseSensitive + ")");
/* 1467 */     this.tabs -= 1;
/* 1468 */     println("{");
/* 1469 */     this.tabs += 1;
/*      */ 
/* 1473 */     if (this.grammar.debuggingOutput) {
/* 1474 */       println("setRuleNames(_ruleNames);");
/* 1475 */       println("setSemPredNames(_semPredNames);");
/* 1476 */       println("setupDebugging();");
/*      */     }
/*      */ 
/* 1480 */     println("initLiterals();");
/* 1481 */     this.tabs -= 1;
/* 1482 */     println("}");
/* 1483 */     println("");
/*      */ 
/* 1486 */     println(this.grammar.getClassName() + "::" + this.grammar.getClassName() + "(const " + namespaceAntlr + "LexerSharedInputState& state)");
/* 1487 */     this.tabs += 1;
/* 1488 */     println(": " + str + "(state," + paramLexerGrammar.caseSensitive + ")");
/* 1489 */     this.tabs -= 1;
/* 1490 */     println("{");
/* 1491 */     this.tabs += 1;
/*      */ 
/* 1495 */     if (this.grammar.debuggingOutput) {
/* 1496 */       println("setRuleNames(_ruleNames);");
/* 1497 */       println("setSemPredNames(_semPredNames);");
/* 1498 */       println("setupDebugging();");
/*      */     }
/*      */ 
/* 1502 */     println("initLiterals();");
/* 1503 */     this.tabs -= 1;
/* 1504 */     println("}");
/* 1505 */     println("");
/*      */ 
/* 1507 */     if (this.noConstructors)
/*      */     {
/* 1509 */       println("// constructor creation turned of with 'noConstructor' option");
/* 1510 */       println("#endif");
/*      */     }
/*      */ 
/* 1513 */     println("void " + this.grammar.getClassName() + "::initLiterals()");
/* 1514 */     println("{");
/* 1515 */     this.tabs += 1;
/*      */ 
/* 1519 */     Enumeration localEnumeration = this.grammar.tokenManager.getTokenSymbolKeys();
/*      */     Object localObject2;
/* 1520 */     while (localEnumeration.hasMoreElements()) {
/* 1521 */       localObject1 = (String)localEnumeration.nextElement();
/* 1522 */       if (((String)localObject1).charAt(0) == '"')
/*      */       {
/* 1525 */         TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol((String)localObject1);
/* 1526 */         if ((localTokenSymbol instanceof StringLiteralSymbol)) {
/* 1527 */           localObject2 = (StringLiteralSymbol)localTokenSymbol;
/* 1528 */           println("literals[" + ((StringLiteralSymbol)localObject2).getId() + "] = " + ((StringLiteralSymbol)localObject2).getTokenType() + ";");
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1533 */     this.tabs -= 1;
/* 1534 */     println("}");
/*      */ 
/* 1538 */     if (this.grammar.debuggingOutput) {
/* 1539 */       println("const char* " + this.grammar.getClassName() + "::_ruleNames[] = {");
/* 1540 */       this.tabs += 1;
/*      */ 
/* 1542 */       localObject1 = this.grammar.rules.elements();
/* 1543 */       i = 0;
/* 1544 */       while (((Enumeration)localObject1).hasMoreElements()) {
/* 1545 */         localObject2 = (GrammarSymbol)((Enumeration)localObject1).nextElement();
/* 1546 */         if ((localObject2 instanceof RuleSymbol))
/* 1547 */           println("\"" + ((RuleSymbol)localObject2).getId() + "\",");
/*      */       }
/* 1549 */       println("0");
/* 1550 */       this.tabs -= 1;
/* 1551 */       println("};");
/*      */     }
/*      */ 
/* 1557 */     genNextToken();
/*      */ 
/* 1560 */     Object localObject1 = this.grammar.rules.elements();
/* 1561 */     int i = 0;
/* 1562 */     while (((Enumeration)localObject1).hasMoreElements()) {
/* 1563 */       localObject2 = (RuleSymbol)((Enumeration)localObject1).nextElement();
/*      */ 
/* 1565 */       if (!((RuleSymbol)localObject2).getId().equals("mnextToken")) {
/* 1566 */         genRule((RuleSymbol)localObject2, false, i++, this.grammar.getClassName() + "::");
/*      */       }
/* 1568 */       exitIfError();
/*      */     }
/*      */ 
/* 1572 */     if (this.grammar.debuggingOutput) {
/* 1573 */       genSemPredMap(this.grammar.getClassName() + "::");
/*      */     }
/*      */ 
/* 1576 */     genBitsets(this.bitsetsUsed, ((LexerGrammar)this.grammar).charVocabulary.size(), this.grammar.getClassName() + "::");
/*      */ 
/* 1578 */     println("");
/* 1579 */     if (nameSpace != null) {
/* 1580 */       nameSpace.emitClosures(this.currentOutput);
/*      */     }
/*      */ 
/* 1583 */     this.currentOutput.close();
/* 1584 */     this.currentOutput = null;
/*      */   }
/*      */ 
/*      */   public void genInitFactory(Grammar paramGrammar)
/*      */   {
/* 1590 */     String str1 = "factory ";
/* 1591 */     if (!paramGrammar.buildAST) {
/* 1592 */       str1 = "";
/*      */     }
/* 1594 */     println("void " + paramGrammar.getClassName() + "::initializeASTFactory( " + namespaceAntlr + "ASTFactory& " + str1 + ")");
/* 1595 */     println("{");
/* 1596 */     this.tabs += 1;
/*      */ 
/* 1598 */     if (paramGrammar.buildAST)
/*      */     {
/* 1603 */       TokenManager localTokenManager = this.grammar.tokenManager;
/* 1604 */       Enumeration localEnumeration = localTokenManager.getTokenSymbolKeys();
/*      */       Object localObject;
/* 1605 */       while (localEnumeration.hasMoreElements())
/*      */       {
/* 1607 */         String str2 = (String)localEnumeration.nextElement();
/* 1608 */         localObject = localTokenManager.getTokenSymbol(str2);
/*      */ 
/* 1611 */         if (((TokenSymbol)localObject).getASTNodeType() != null)
/*      */         {
/* 1614 */           this.astTypes.ensureCapacity(((TokenSymbol)localObject).getTokenType());
/* 1615 */           String str3 = (String)this.astTypes.elementAt(((TokenSymbol)localObject).getTokenType());
/* 1616 */           if (str3 == null) {
/* 1617 */             this.astTypes.setElementAt(((TokenSymbol)localObject).getASTNodeType(), ((TokenSymbol)localObject).getTokenType());
/*      */           }
/* 1621 */           else if (!((TokenSymbol)localObject).getASTNodeType().equals(str3))
/*      */           {
/* 1623 */             this.antlrTool.warning("Token " + str2 + " taking most specific AST type", this.grammar.getFilename(), 1, 1);
/* 1624 */             this.antlrTool.warning("  using " + str3 + " ignoring " + ((TokenSymbol)localObject).getASTNodeType(), this.grammar.getFilename(), 1, 1);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1631 */       for (int i = 0; i < this.astTypes.size(); i++)
/*      */       {
/* 1633 */         localObject = (String)this.astTypes.elementAt(i);
/* 1634 */         if (localObject != null)
/*      */         {
/* 1636 */           println("factory.registerFactory(" + i + ", \"" + (String)localObject + "\", " + (String)localObject + "::factory);");
/*      */         }
/*      */       }
/*      */ 
/* 1640 */       println("factory.setMaxNodeType(" + this.grammar.tokenManager.maxTokenType() + ");");
/*      */     }
/* 1642 */     this.tabs -= 1;
/* 1643 */     println("}");
/*      */   }
/*      */ 
/*      */   public void genBody(ParserGrammar paramParserGrammar)
/*      */     throws IOException
/*      */   {
/* 1650 */     this.outputFile = (this.grammar.getClassName() + ".cpp");
/* 1651 */     this.outputLine = 1;
/* 1652 */     this.currentOutput = this.antlrTool.openOutputFile(this.outputFile);
/*      */ 
/* 1654 */     this.genAST = this.grammar.buildAST;
/*      */ 
/* 1656 */     this.tabs = 0;
/*      */ 
/* 1659 */     genHeader(this.outputFile);
/*      */ 
/* 1661 */     printHeaderAction("pre_include_cpp");
/*      */ 
/* 1664 */     println("#include \"" + this.grammar.getClassName() + ".hpp\"");
/* 1665 */     println("#include <antlr/NoViableAltException.hpp>");
/* 1666 */     println("#include <antlr/SemanticException.hpp>");
/* 1667 */     println("#include <antlr/ASTFactory.hpp>");
/*      */ 
/* 1669 */     printHeaderAction("post_include_cpp");
/*      */ 
/* 1671 */     if (nameSpace != null) {
/* 1672 */       nameSpace.emitDeclarations(this.currentOutput);
/*      */     }
/*      */ 
/* 1675 */     printAction(this.grammar.preambleAction);
/*      */ 
/* 1677 */     String str = null;
/* 1678 */     if (this.grammar.superClass != null) {
/* 1679 */       str = this.grammar.superClass;
/*      */     } else {
/* 1681 */       str = this.grammar.getSuperClass();
/* 1682 */       if (str.lastIndexOf('.') != -1)
/* 1683 */         str = str.substring(str.lastIndexOf('.') + 1);
/* 1684 */       str = namespaceAntlr + str;
/*      */     }
/*      */     GrammarSymbol localGrammarSymbol;
/* 1689 */     if (this.grammar.debuggingOutput) {
/* 1690 */       println("const char* " + this.grammar.getClassName() + "::_ruleNames[] = {");
/* 1691 */       this.tabs += 1;
/*      */ 
/* 1693 */       localEnumeration = this.grammar.rules.elements();
/* 1694 */       i = 0;
/* 1695 */       while (localEnumeration.hasMoreElements()) {
/* 1696 */         localGrammarSymbol = (GrammarSymbol)localEnumeration.nextElement();
/* 1697 */         if ((localGrammarSymbol instanceof RuleSymbol))
/* 1698 */           println("\"" + ((RuleSymbol)localGrammarSymbol).getId() + "\",");
/*      */       }
/* 1700 */       println("0");
/* 1701 */       this.tabs -= 1;
/* 1702 */       println("};");
/*      */     }
/*      */ 
/* 1721 */     if (this.noConstructors)
/*      */     {
/* 1723 */       println("#if 0");
/* 1724 */       println("// constructor creation turned of with 'noConstructor' option");
/*      */     }
/*      */ 
/* 1728 */     print(this.grammar.getClassName() + "::" + this.grammar.getClassName());
/* 1729 */     println("(" + namespaceAntlr + "TokenBuffer& tokenBuf, int k)");
/* 1730 */     println(": " + str + "(tokenBuf,k)");
/* 1731 */     println("{");
/*      */ 
/* 1735 */     println("}");
/* 1736 */     println("");
/*      */ 
/* 1738 */     print(this.grammar.getClassName() + "::" + this.grammar.getClassName());
/* 1739 */     println("(" + namespaceAntlr + "TokenBuffer& tokenBuf)");
/* 1740 */     println(": " + str + "(tokenBuf," + this.grammar.maxk + ")");
/* 1741 */     println("{");
/*      */ 
/* 1745 */     println("}");
/* 1746 */     println("");
/*      */ 
/* 1749 */     print(this.grammar.getClassName() + "::" + this.grammar.getClassName());
/* 1750 */     println("(" + namespaceAntlr + "TokenStream& lexer, int k)");
/* 1751 */     println(": " + str + "(lexer,k)");
/* 1752 */     println("{");
/*      */ 
/* 1756 */     println("}");
/* 1757 */     println("");
/*      */ 
/* 1759 */     print(this.grammar.getClassName() + "::" + this.grammar.getClassName());
/* 1760 */     println("(" + namespaceAntlr + "TokenStream& lexer)");
/* 1761 */     println(": " + str + "(lexer," + this.grammar.maxk + ")");
/* 1762 */     println("{");
/*      */ 
/* 1766 */     println("}");
/* 1767 */     println("");
/*      */ 
/* 1769 */     print(this.grammar.getClassName() + "::" + this.grammar.getClassName());
/* 1770 */     println("(const " + namespaceAntlr + "ParserSharedInputState& state)");
/* 1771 */     println(": " + str + "(state," + this.grammar.maxk + ")");
/* 1772 */     println("{");
/*      */ 
/* 1776 */     println("}");
/* 1777 */     println("");
/*      */ 
/* 1779 */     if (this.noConstructors)
/*      */     {
/* 1781 */       println("// constructor creation turned of with 'noConstructor' option");
/* 1782 */       println("#endif");
/*      */     }
/*      */ 
/* 1785 */     this.astTypes = new Vector();
/*      */ 
/* 1788 */     Enumeration localEnumeration = this.grammar.rules.elements();
/* 1789 */     int i = 0;
/* 1790 */     while (localEnumeration.hasMoreElements()) {
/* 1791 */       localGrammarSymbol = (GrammarSymbol)localEnumeration.nextElement();
/* 1792 */       if ((localGrammarSymbol instanceof RuleSymbol)) {
/* 1793 */         RuleSymbol localRuleSymbol = (RuleSymbol)localGrammarSymbol;
/* 1794 */         genRule(localRuleSymbol, localRuleSymbol.references.size() == 0, i++, this.grammar.getClassName() + "::");
/*      */       }
/* 1796 */       exitIfError();
/*      */     }
/*      */ 
/* 1799 */     genInitFactory(paramParserGrammar);
/*      */ 
/* 1802 */     genTokenStrings(this.grammar.getClassName() + "::");
/*      */ 
/* 1805 */     genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType(), this.grammar.getClassName() + "::");
/*      */ 
/* 1808 */     if (this.grammar.debuggingOutput) {
/* 1809 */       genSemPredMap(this.grammar.getClassName() + "::");
/*      */     }
/*      */ 
/* 1812 */     println("");
/* 1813 */     println("");
/* 1814 */     if (nameSpace != null) {
/* 1815 */       nameSpace.emitClosures(this.currentOutput);
/*      */     }
/*      */ 
/* 1818 */     this.currentOutput.close();
/* 1819 */     this.currentOutput = null;
/*      */   }
/*      */ 
/*      */   public void genBody(TreeWalkerGrammar paramTreeWalkerGrammar) throws IOException
/*      */   {
/* 1824 */     this.outputFile = (this.grammar.getClassName() + ".cpp");
/* 1825 */     this.outputLine = 1;
/* 1826 */     this.currentOutput = this.antlrTool.openOutputFile(this.outputFile);
/*      */ 
/* 1829 */     this.genAST = this.grammar.buildAST;
/* 1830 */     this.tabs = 0;
/*      */ 
/* 1833 */     genHeader(this.outputFile);
/*      */ 
/* 1835 */     printHeaderAction("pre_include_cpp");
/*      */ 
/* 1838 */     println("#include \"" + this.grammar.getClassName() + ".hpp\"");
/* 1839 */     println("#include <antlr/Token.hpp>");
/* 1840 */     println("#include <antlr/AST.hpp>");
/* 1841 */     println("#include <antlr/NoViableAltException.hpp>");
/* 1842 */     println("#include <antlr/MismatchedTokenException.hpp>");
/* 1843 */     println("#include <antlr/SemanticException.hpp>");
/* 1844 */     println("#include <antlr/BitSet.hpp>");
/*      */ 
/* 1846 */     printHeaderAction("post_include_cpp");
/*      */ 
/* 1848 */     if (nameSpace != null) {
/* 1849 */       nameSpace.emitDeclarations(this.currentOutput);
/*      */     }
/*      */ 
/* 1852 */     printAction(this.grammar.preambleAction);
/*      */ 
/* 1855 */     String str1 = null;
/* 1856 */     if (this.grammar.superClass != null) {
/* 1857 */       str1 = this.grammar.superClass;
/*      */     }
/*      */     else {
/* 1860 */       str1 = this.grammar.getSuperClass();
/* 1861 */       if (str1.lastIndexOf('.') != -1)
/* 1862 */         str1 = str1.substring(str1.lastIndexOf('.') + 1);
/* 1863 */       str1 = namespaceAntlr + str1;
/*      */     }
/* 1865 */     if (this.noConstructors)
/*      */     {
/* 1867 */       println("#if 0");
/* 1868 */       println("// constructor creation turned of with 'noConstructor' option");
/*      */     }
/*      */ 
/* 1872 */     println(this.grammar.getClassName() + "::" + this.grammar.getClassName() + "()");
/* 1873 */     println("\t: " + namespaceAntlr + "TreeParser() {");
/* 1874 */     this.tabs += 1;
/*      */ 
/* 1876 */     this.tabs -= 1;
/* 1877 */     println("}");
/*      */ 
/* 1879 */     if (this.noConstructors)
/*      */     {
/* 1881 */       println("// constructor creation turned of with 'noConstructor' option");
/* 1882 */       println("#endif");
/*      */     }
/* 1884 */     println("");
/*      */ 
/* 1886 */     this.astTypes = new Vector();
/*      */ 
/* 1889 */     Enumeration localEnumeration = this.grammar.rules.elements();
/* 1890 */     int i = 0;
/* 1891 */     String str2 = "";
/* 1892 */     while (localEnumeration.hasMoreElements()) {
/* 1893 */       GrammarSymbol localGrammarSymbol = (GrammarSymbol)localEnumeration.nextElement();
/* 1894 */       if ((localGrammarSymbol instanceof RuleSymbol)) {
/* 1895 */         RuleSymbol localRuleSymbol = (RuleSymbol)localGrammarSymbol;
/* 1896 */         genRule(localRuleSymbol, localRuleSymbol.references.size() == 0, i++, this.grammar.getClassName() + "::");
/*      */       }
/* 1898 */       exitIfError();
/*      */     }
/*      */ 
/* 1902 */     genInitFactory(this.grammar);
/*      */ 
/* 1904 */     genTokenStrings(this.grammar.getClassName() + "::");
/*      */ 
/* 1907 */     genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType(), this.grammar.getClassName() + "::");
/*      */ 
/* 1910 */     println("");
/* 1911 */     println("");
/*      */ 
/* 1913 */     if (nameSpace != null) {
/* 1914 */       nameSpace.emitClosures(this.currentOutput);
/*      */     }
/*      */ 
/* 1917 */     this.currentOutput.close();
/* 1918 */     this.currentOutput = null;
/*      */   }
/*      */ 
/*      */   protected void genCases(BitSet paramBitSet)
/*      */   {
/* 1924 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) System.out.println("genCases(" + paramBitSet + ")");
/*      */ 
/* 1927 */     int[] arrayOfInt = paramBitSet.toArray();
/*      */ 
/* 1929 */     int i = 1;
/* 1930 */     int j = 1;
/* 1931 */     int k = 1;
/* 1932 */     for (int m = 0; m < arrayOfInt.length; m++) {
/* 1933 */       if (j == 1)
/* 1934 */         print("");
/*      */       else {
/* 1936 */         _print("  ");
/*      */       }
/* 1938 */       _print("case " + getValueString(arrayOfInt[m]) + ":");
/*      */ 
/* 1940 */       if (j == i) {
/* 1941 */         _println("");
/* 1942 */         k = 1;
/* 1943 */         j = 1;
/*      */       }
/*      */       else {
/* 1946 */         j++;
/* 1947 */         k = 0;
/*      */       }
/*      */     }
/* 1950 */     if (k == 0)
/* 1951 */       _println("");
/*      */   }
/*      */ 
/*      */   public CppBlockFinishingInfo genCommonBlock(AlternativeBlock paramAlternativeBlock, boolean paramBoolean)
/*      */   {
/* 1968 */     int i = 0;
/* 1969 */     int j = 0;
/* 1970 */     int k = 0;
/* 1971 */     CppBlockFinishingInfo localCppBlockFinishingInfo = new CppBlockFinishingInfo();
/* 1972 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) System.out.println("genCommonBlk(" + paramAlternativeBlock + ")");
/*      */ 
/* 1975 */     boolean bool1 = this.genAST;
/* 1976 */     this.genAST = ((this.genAST) && (paramAlternativeBlock.getAutoGen()));
/*      */ 
/* 1978 */     boolean bool2 = this.saveText;
/* 1979 */     this.saveText = ((this.saveText) && (paramAlternativeBlock.getAutoGen()));
/*      */     Object localObject1;
/* 1982 */     if ((paramAlternativeBlock.not) && (this.analyzer.subruleCanBeInverted(paramAlternativeBlock, this.grammar instanceof LexerGrammar)))
/*      */     {
/* 1985 */       localObject1 = this.analyzer.look(1, paramAlternativeBlock);
/*      */ 
/* 1987 */       if ((paramAlternativeBlock.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/* 1988 */         println(paramAlternativeBlock.getLabel() + " = " + this.lt1Value + ";");
/*      */       }
/*      */ 
/* 1992 */       genElementAST(paramAlternativeBlock);
/*      */ 
/* 1994 */       String str1 = "";
/* 1995 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1996 */         if (this.usingCustomAST)
/* 1997 */           str1 = namespaceAntlr + "RefAST" + "(_t),";
/*      */         else {
/* 1999 */           str1 = "_t,";
/*      */         }
/*      */       }
/*      */ 
/* 2003 */       println("match(" + str1 + getBitsetName(markBitsetForGen(((Lookahead)localObject1).fset)) + ");");
/*      */ 
/* 2006 */       if ((this.grammar instanceof TreeWalkerGrammar))
/*      */       {
/* 2008 */         println("_t = _t->getNextSibling();");
/*      */       }
/* 2010 */       return localCppBlockFinishingInfo;
/*      */     }
/*      */ 
/* 2014 */     if (paramAlternativeBlock.getAlternatives().size() == 1)
/*      */     {
/* 2016 */       localObject1 = paramAlternativeBlock.getAlternativeAt(0);
/*      */ 
/* 2018 */       if (((Alternative)localObject1).synPred != null)
/*      */       {
/* 2020 */         this.antlrTool.warning("Syntactic predicate superfluous for single alternative", this.grammar.getFilename(), paramAlternativeBlock.getAlternativeAt(0).synPred.getLine(), paramAlternativeBlock.getAlternativeAt(0).synPred.getColumn());
/*      */       }
/*      */ 
/* 2027 */       if (paramBoolean)
/*      */       {
/* 2029 */         if (((Alternative)localObject1).semPred != null)
/*      */         {
/* 2032 */           genSemPred(((Alternative)localObject1).semPred, paramAlternativeBlock.line);
/*      */         }
/* 2034 */         genAlt((Alternative)localObject1, paramAlternativeBlock);
/* 2035 */         return localCppBlockFinishingInfo;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2049 */     int m = 0;
/* 2050 */     for (int n = 0; n < paramAlternativeBlock.getAlternatives().size(); n++)
/*      */     {
/* 2052 */       Alternative localAlternative1 = paramAlternativeBlock.getAlternativeAt(n);
/* 2053 */       if (suitableForCaseExpression(localAlternative1))
/* 2054 */         m++;
/*      */     }
/*      */     Object localObject2;
/* 2058 */     if (m >= this.makeSwitchThreshold)
/*      */     {
/* 2061 */       String str2 = lookaheadString(1);
/* 2062 */       j = 1;
/*      */ 
/* 2064 */       if ((this.grammar instanceof TreeWalkerGrammar))
/*      */       {
/* 2066 */         println("if (_t == " + this.labeledElementASTInit + " )");
/* 2067 */         this.tabs += 1;
/* 2068 */         println("_t = ASTNULL;");
/* 2069 */         this.tabs -= 1;
/*      */       }
/* 2071 */       println("switch ( " + str2 + ") {");
/* 2072 */       for (i2 = 0; i2 < paramAlternativeBlock.alternatives.size(); i2++)
/*      */       {
/* 2074 */         Alternative localAlternative2 = paramAlternativeBlock.getAlternativeAt(i2);
/*      */ 
/* 2077 */         if (suitableForCaseExpression(localAlternative2))
/*      */         {
/* 2081 */           localObject2 = localAlternative2.cache[1];
/* 2082 */           if ((((Lookahead)localObject2).fset.degree() == 0) && (!((Lookahead)localObject2).containsEpsilon()))
/*      */           {
/* 2084 */             this.antlrTool.warning("Alternate omitted due to empty prediction set", this.grammar.getFilename(), localAlternative2.head.getLine(), localAlternative2.head.getColumn());
/*      */           }
/*      */           else
/*      */           {
/* 2090 */             genCases(((Lookahead)localObject2).fset);
/* 2091 */             println("{");
/* 2092 */             this.tabs += 1;
/* 2093 */             genAlt(localAlternative2, paramAlternativeBlock);
/* 2094 */             println("break;");
/* 2095 */             this.tabs -= 1;
/* 2096 */             println("}");
/*      */           }
/*      */         }
/*      */       }
/* 2099 */       println("default:");
/* 2100 */       this.tabs += 1;
/*      */     }
/*      */ 
/* 2117 */     int i1 = (this.grammar instanceof LexerGrammar) ? this.grammar.maxk : 0;
/* 2118 */     for (int i2 = i1; i2 >= 0; i2--) {
/* 2119 */       if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) System.out.println("checking depth " + i2);
/* 2120 */       for (i3 = 0; i3 < paramAlternativeBlock.alternatives.size(); i3++) {
/* 2121 */         localObject2 = paramAlternativeBlock.getAlternativeAt(i3);
/* 2122 */         if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) System.out.println("genAlt: " + i3);
/*      */ 
/* 2126 */         if ((j != 0) && (suitableForCaseExpression((Alternative)localObject2)))
/*      */         {
/* 2129 */           if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) {
/* 2130 */             System.out.println("ignoring alt because it was in the switch");
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 2135 */           boolean bool3 = false;
/*      */           String str4;
/* 2137 */           if ((this.grammar instanceof LexerGrammar))
/*      */           {
/* 2140 */             int i4 = ((Alternative)localObject2).lookaheadDepth;
/* 2141 */             if (i4 == 2147483647)
/*      */             {
/* 2144 */               i4 = this.grammar.maxk;
/*      */             }
/* 2146 */             while ((i4 >= 1) && (localObject2.cache[i4].containsEpsilon()))
/*      */             {
/* 2149 */               i4--;
/*      */             }
/*      */ 
/* 2153 */             if (i4 != i2)
/*      */             {
/* 2155 */               if ((!this.DEBUG_CODE_GENERATOR) && (!this.DEBUG_CPP_CODE_GENERATOR)) continue;
/* 2156 */               System.out.println("ignoring alt because effectiveDepth!=altDepth;" + i4 + "!=" + i2); continue;
/*      */             }
/*      */ 
/* 2159 */             bool3 = lookaheadIsEmpty((Alternative)localObject2, i4);
/* 2160 */             str4 = getLookaheadTestExpression((Alternative)localObject2, i4);
/*      */           }
/*      */           else
/*      */           {
/* 2164 */             bool3 = lookaheadIsEmpty((Alternative)localObject2, this.grammar.maxk);
/* 2165 */             str4 = getLookaheadTestExpression((Alternative)localObject2, this.grammar.maxk);
/*      */           }
/*      */ 
/* 2170 */           if ((localObject2.cache[1].fset.degree() > 127) && (suitableForCaseExpression((Alternative)localObject2)))
/*      */           {
/* 2173 */             if (i == 0)
/*      */             {
/* 2177 */               if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2178 */                 println("if (_t == " + this.labeledElementASTInit + " )");
/* 2179 */                 this.tabs += 1;
/* 2180 */                 println("_t = ASTNULL;");
/* 2181 */                 this.tabs -= 1;
/*      */               }
/* 2183 */               println("if " + str4 + " {");
/*      */             }
/*      */             else {
/* 2186 */               println("else if " + str4 + " {");
/*      */             }
/* 2188 */           } else if ((bool3) && (((Alternative)localObject2).semPred == null) && (((Alternative)localObject2).synPred == null))
/*      */           {
/* 2196 */             if (i == 0) {
/* 2197 */               println("{");
/*      */             }
/*      */             else {
/* 2200 */               println("else {");
/*      */             }
/* 2202 */             localCppBlockFinishingInfo.needAnErrorClause = false;
/*      */           }
/*      */           else
/*      */           {
/* 2208 */             if (((Alternative)localObject2).semPred != null)
/*      */             {
/* 2212 */               ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 2213 */               String str5 = processActionForSpecialSymbols(((Alternative)localObject2).semPred, paramAlternativeBlock.line, this.currentRule, localActionTransInfo);
/*      */ 
/* 2221 */               if ((this.grammar.debuggingOutput) && (((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar))))
/*      */               {
/* 2224 */                 str4 = "(" + str4 + "&& fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEvent.PREDICTING," + addSemPred(this.charFormatter.escapeString(str5)) + "," + str5 + "))";
/*      */               }
/*      */               else {
/* 2227 */                 str4 = "(" + str4 + "&&(" + str5 + "))";
/*      */               }
/*      */             }
/*      */ 
/* 2231 */             if (i > 0) {
/* 2232 */               if (((Alternative)localObject2).synPred != null) {
/* 2233 */                 println("else {");
/* 2234 */                 this.tabs += 1;
/* 2235 */                 genSynPred(((Alternative)localObject2).synPred, str4);
/* 2236 */                 k++;
/*      */               }
/*      */               else {
/* 2239 */                 println("else if " + str4 + " {");
/*      */               }
/*      */ 
/*      */             }
/* 2243 */             else if (((Alternative)localObject2).synPred != null) {
/* 2244 */               genSynPred(((Alternative)localObject2).synPred, str4);
/*      */             }
/*      */             else
/*      */             {
/* 2249 */               if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2250 */                 println("if (_t == " + this.labeledElementASTInit + " )");
/* 2251 */                 this.tabs += 1;
/* 2252 */                 println("_t = ASTNULL;");
/* 2253 */                 this.tabs -= 1;
/*      */               }
/* 2255 */               println("if " + str4 + " {");
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 2261 */           i++;
/* 2262 */           this.tabs += 1;
/* 2263 */           genAlt((Alternative)localObject2, paramAlternativeBlock);
/* 2264 */           this.tabs -= 1;
/* 2265 */           println("}");
/*      */         }
/*      */       }
/*      */     }
/* 2268 */     String str3 = "";
/* 2269 */     for (int i3 = 1; i3 <= k; i3++) {
/* 2270 */       this.tabs -= 1;
/* 2271 */       str3 = str3 + "}";
/*      */     }
/*      */ 
/* 2275 */     this.genAST = bool1;
/*      */ 
/* 2278 */     this.saveText = bool2;
/*      */ 
/* 2281 */     if (j != 0) {
/* 2282 */       this.tabs -= 1;
/* 2283 */       localCppBlockFinishingInfo.postscript = (str3 + "}");
/* 2284 */       localCppBlockFinishingInfo.generatedSwitch = true;
/* 2285 */       localCppBlockFinishingInfo.generatedAnIf = (i > 0);
/*      */     }
/*      */     else
/*      */     {
/* 2290 */       localCppBlockFinishingInfo.postscript = str3;
/* 2291 */       localCppBlockFinishingInfo.generatedSwitch = false;
/* 2292 */       localCppBlockFinishingInfo.generatedAnIf = (i > 0);
/*      */     }
/*      */ 
/* 2295 */     return localCppBlockFinishingInfo;
/*      */   }
/*      */ 
/*      */   private static boolean suitableForCaseExpression(Alternative paramAlternative) {
/* 2299 */     return (paramAlternative.lookaheadDepth == 1) && (paramAlternative.semPred == null) && (!paramAlternative.cache[1].containsEpsilon()) && (paramAlternative.cache[1].fset.degree() <= 127);
/*      */   }
/*      */ 
/*      */   private void genElementAST(AlternativeElement paramAlternativeElement)
/*      */   {
/* 2311 */     if (((this.grammar instanceof TreeWalkerGrammar)) && (!this.grammar.buildAST))
/*      */     {
/* 2317 */       if (paramAlternativeElement.getLabel() == null)
/*      */       {
/* 2319 */         String str1 = this.lt1Value;
/*      */ 
/* 2321 */         String str2 = "tmp" + this.astVarNumber + "_AST";
/* 2322 */         this.astVarNumber += 1;
/*      */ 
/* 2324 */         mapTreeVariable(paramAlternativeElement, str2);
/*      */ 
/* 2326 */         println(this.labeledElementASTType + " " + str2 + "_in = " + str1 + ";");
/*      */       }
/* 2328 */       return;
/*      */     }
/*      */ 
/* 2331 */     if ((this.grammar.buildAST) && (this.syntacticPredLevel == 0))
/*      */     {
/* 2333 */       int i = (this.genAST) && ((paramAlternativeElement.getLabel() != null) || (paramAlternativeElement.getAutoGenType() != 3)) ? 1 : 0;
/*      */ 
/* 2341 */       if ((paramAlternativeElement.getAutoGenType() != 3) && ((paramAlternativeElement instanceof TokenRefElement)))
/*      */       {
/* 2343 */         i = 1;
/*      */       }
/* 2345 */       int j = (this.grammar.hasSyntacticPredicate) && (i != 0) ? 1 : 0;
/*      */       String str3;
/*      */       String str4;
/* 2352 */       if (paramAlternativeElement.getLabel() != null)
/*      */       {
/* 2355 */         str3 = paramAlternativeElement.getLabel();
/* 2356 */         str4 = paramAlternativeElement.getLabel();
/*      */       }
/*      */       else
/*      */       {
/* 2361 */         str3 = this.lt1Value;
/*      */ 
/* 2363 */         str4 = "tmp" + this.astVarNumber;
/* 2364 */         this.astVarNumber += 1;
/*      */       }
/*      */ 
/* 2368 */       if (i != 0)
/*      */       {
/* 2370 */         if ((paramAlternativeElement instanceof GrammarAtom))
/*      */         {
/* 2372 */           localObject = (GrammarAtom)paramAlternativeElement;
/* 2373 */           if (((GrammarAtom)localObject).getASTNodeType() != null)
/*      */           {
/* 2375 */             genASTDeclaration(paramAlternativeElement, str4, "Ref" + ((GrammarAtom)localObject).getASTNodeType());
/*      */           }
/*      */           else
/*      */           {
/* 2380 */             genASTDeclaration(paramAlternativeElement, str4, this.labeledElementASTType);
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 2386 */           genASTDeclaration(paramAlternativeElement, str4, this.labeledElementASTType);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2392 */       Object localObject = str4 + "_AST";
/*      */ 
/* 2395 */       mapTreeVariable(paramAlternativeElement, (String)localObject);
/* 2396 */       if ((this.grammar instanceof TreeWalkerGrammar))
/*      */       {
/* 2399 */         println(this.labeledElementASTType + " " + (String)localObject + "_in = " + this.labeledElementASTInit + ";");
/*      */       }
/*      */ 
/* 2403 */       if (j != 0) {
/* 2404 */         println("if ( inputState->guessing == 0 ) {");
/* 2405 */         this.tabs += 1;
/*      */       }
/*      */ 
/* 2410 */       if (paramAlternativeElement.getLabel() != null)
/*      */       {
/* 2412 */         if ((paramAlternativeElement instanceof GrammarAtom))
/*      */         {
/* 2414 */           println((String)localObject + " = " + getASTCreateString((GrammarAtom)paramAlternativeElement, str3) + ";");
/*      */         }
/*      */         else
/*      */         {
/* 2419 */           println((String)localObject + " = " + getASTCreateString(str3) + ";");
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2425 */       if ((paramAlternativeElement.getLabel() == null) && (i != 0))
/*      */       {
/* 2427 */         str3 = this.lt1Value;
/* 2428 */         if ((paramAlternativeElement instanceof GrammarAtom))
/*      */         {
/* 2430 */           println((String)localObject + " = " + getASTCreateString((GrammarAtom)paramAlternativeElement, str3) + ";");
/*      */         }
/*      */         else
/*      */         {
/* 2435 */           println((String)localObject + " = " + getASTCreateString(str3) + ";");
/*      */         }
/*      */ 
/* 2439 */         if ((this.grammar instanceof TreeWalkerGrammar))
/*      */         {
/* 2442 */           println((String)localObject + "_in = " + str3 + ";");
/*      */         }
/*      */       }
/*      */ 
/* 2446 */       if (this.genAST)
/*      */       {
/* 2448 */         switch (paramAlternativeElement.getAutoGenType())
/*      */         {
/*      */         case 1:
/* 2451 */           if ((this.usingCustomAST) || (((paramAlternativeElement instanceof GrammarAtom)) && (((GrammarAtom)paramAlternativeElement).getASTNodeType() != null)))
/*      */           {
/* 2454 */             println("astFactory->addASTChild(currentAST, " + namespaceAntlr + "RefAST(" + (String)localObject + "));");
/*      */           }
/* 2456 */           else println("astFactory->addASTChild(currentAST, " + (String)localObject + ");");
/*      */ 
/* 2458 */           break;
/*      */         case 2:
/* 2460 */           if ((this.usingCustomAST) || (((paramAlternativeElement instanceof GrammarAtom)) && (((GrammarAtom)paramAlternativeElement).getASTNodeType() != null)))
/*      */           {
/* 2463 */             println("astFactory->makeASTRoot(currentAST, " + namespaceAntlr + "RefAST(" + (String)localObject + "));");
/*      */           }
/* 2465 */           else println("astFactory->makeASTRoot(currentAST, " + (String)localObject + ");");
/* 2466 */           break;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2471 */       if (j != 0)
/*      */       {
/* 2473 */         this.tabs -= 1;
/* 2474 */         println("}");
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void genErrorCatchForElement(AlternativeElement paramAlternativeElement)
/*      */   {
/* 2482 */     if (paramAlternativeElement.getLabel() == null) return;
/* 2483 */     String str = paramAlternativeElement.enclosingRuleName;
/* 2484 */     if ((this.grammar instanceof LexerGrammar)) {
/* 2485 */       str = CodeGenerator.encodeLexerRuleName(paramAlternativeElement.enclosingRuleName);
/*      */     }
/* 2487 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(str);
/* 2488 */     if (localRuleSymbol == null) {
/* 2489 */       this.antlrTool.panic("Enclosing rule not found!");
/*      */     }
/* 2491 */     ExceptionSpec localExceptionSpec = localRuleSymbol.block.findExceptionSpec(paramAlternativeElement.getLabel());
/* 2492 */     if (localExceptionSpec != null) {
/* 2493 */       this.tabs -= 1;
/* 2494 */       println("}");
/* 2495 */       genErrorHandler(localExceptionSpec);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void genErrorHandler(ExceptionSpec paramExceptionSpec)
/*      */   {
/* 2502 */     for (int i = 0; i < paramExceptionSpec.handlers.size(); i++)
/*      */     {
/* 2504 */       ExceptionHandler localExceptionHandler = (ExceptionHandler)paramExceptionSpec.handlers.elementAt(i);
/*      */ 
/* 2506 */       println("catch (" + localExceptionHandler.exceptionTypeAndName.getText() + ") {");
/* 2507 */       this.tabs += 1;
/* 2508 */       if (this.grammar.hasSyntacticPredicate) {
/* 2509 */         println("if (inputState->guessing==0) {");
/* 2510 */         this.tabs += 1;
/*      */       }
/*      */ 
/* 2514 */       ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 2515 */       genLineNo(localExceptionHandler.action);
/* 2516 */       printAction(processActionForSpecialSymbols(localExceptionHandler.action.getText(), localExceptionHandler.action.getLine(), this.currentRule, localActionTransInfo));
/*      */ 
/* 2521 */       genLineNo2();
/*      */ 
/* 2523 */       if (this.grammar.hasSyntacticPredicate)
/*      */       {
/* 2525 */         this.tabs -= 1;
/* 2526 */         println("} else {");
/* 2527 */         this.tabs += 1;
/*      */ 
/* 2529 */         println("throw;");
/* 2530 */         this.tabs -= 1;
/* 2531 */         println("}");
/*      */       }
/*      */ 
/* 2534 */       this.tabs -= 1;
/* 2535 */       println("}");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void genErrorTryForElement(AlternativeElement paramAlternativeElement) {
/* 2540 */     if (paramAlternativeElement.getLabel() == null) return;
/* 2541 */     String str = paramAlternativeElement.enclosingRuleName;
/* 2542 */     if ((this.grammar instanceof LexerGrammar)) {
/* 2543 */       str = CodeGenerator.encodeLexerRuleName(paramAlternativeElement.enclosingRuleName);
/*      */     }
/* 2545 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(str);
/* 2546 */     if (localRuleSymbol == null) {
/* 2547 */       this.antlrTool.panic("Enclosing rule not found!");
/*      */     }
/* 2549 */     ExceptionSpec localExceptionSpec = localRuleSymbol.block.findExceptionSpec(paramAlternativeElement.getLabel());
/* 2550 */     if (localExceptionSpec != null) {
/* 2551 */       println("try { // for error handling");
/* 2552 */       this.tabs += 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void genHeader(String paramString)
/*      */   {
/* 2558 */     println("/* $ANTLR " + Tool.version + ": " + "\"" + this.antlrTool.fileMinusPath(this.antlrTool.grammarFile) + "\"" + " -> " + "\"" + paramString + "\"$ */");
/*      */   }
/*      */ 
/*      */   public void genInclude(LexerGrammar paramLexerGrammar)
/*      */     throws IOException
/*      */   {
/* 2567 */     this.outputFile = (this.grammar.getClassName() + ".hpp");
/* 2568 */     this.outputLine = 1;
/* 2569 */     this.currentOutput = this.antlrTool.openOutputFile(this.outputFile);
/*      */ 
/* 2572 */     this.genAST = false;
/* 2573 */     this.saveText = true;
/*      */ 
/* 2575 */     this.tabs = 0;
/*      */ 
/* 2578 */     println("#ifndef INC_" + this.grammar.getClassName() + "_hpp_");
/* 2579 */     println("#define INC_" + this.grammar.getClassName() + "_hpp_");
/* 2580 */     println("");
/*      */ 
/* 2582 */     printHeaderAction("pre_include_hpp");
/*      */ 
/* 2584 */     println("#include <antlr/config.hpp>");
/*      */ 
/* 2587 */     genHeader(this.outputFile);
/*      */ 
/* 2590 */     println("#include <antlr/CommonToken.hpp>");
/* 2591 */     println("#include <antlr/InputBuffer.hpp>");
/* 2592 */     println("#include <antlr/BitSet.hpp>");
/* 2593 */     println("#include \"" + this.grammar.tokenManager.getName() + TokenTypesFileSuffix + ".hpp\"");
/*      */ 
/* 2596 */     String str = null;
/* 2597 */     if (this.grammar.superClass != null) {
/* 2598 */       str = this.grammar.superClass;
/*      */ 
/* 2600 */       println("\n// Include correct superclass header with a header statement for example:");
/* 2601 */       println("// header \"post_include_hpp\" {");
/* 2602 */       println("// #include \"" + str + ".hpp\"");
/* 2603 */       println("// }");
/* 2604 */       println("// Or....");
/* 2605 */       println("// header {");
/* 2606 */       println("// #include \"" + str + ".hpp\"");
/* 2607 */       println("// }\n");
/*      */     }
/*      */     else {
/* 2610 */       str = this.grammar.getSuperClass();
/* 2611 */       if (str.lastIndexOf('.') != -1)
/* 2612 */         str = str.substring(str.lastIndexOf('.') + 1);
/* 2613 */       println("#include <antlr/" + str + ".hpp>");
/* 2614 */       str = namespaceAntlr + str;
/*      */     }
/*      */ 
/* 2618 */     printHeaderAction("post_include_hpp");
/*      */ 
/* 2620 */     if (nameSpace != null) {
/* 2621 */       nameSpace.emitDeclarations(this.currentOutput);
/*      */     }
/* 2623 */     printHeaderAction("");
/*      */ 
/* 2626 */     if (this.grammar.comment != null) {
/* 2627 */       _println(this.grammar.comment);
/*      */     }
/*      */ 
/* 2631 */     print("class CUSTOM_API " + this.grammar.getClassName() + " : public " + str);
/* 2632 */     println(", public " + this.grammar.tokenManager.getName() + TokenTypesFileSuffix);
/*      */ 
/* 2634 */     Token localToken = (Token)this.grammar.options.get("classHeaderSuffix");
/* 2635 */     if (localToken != null) {
/* 2636 */       localObject = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 2637 */       if (localObject != null) {
/* 2638 */         print(", " + (String)localObject);
/*      */       }
/*      */     }
/* 2641 */     println("{");
/*      */ 
/* 2644 */     if (this.grammar.classMemberAction != null) {
/* 2645 */       genLineNo(this.grammar.classMemberAction);
/* 2646 */       print(processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, null));
/*      */ 
/* 2651 */       genLineNo2();
/*      */     }
/*      */ 
/* 2655 */     this.tabs = 0;
/* 2656 */     println("private:");
/* 2657 */     this.tabs = 1;
/* 2658 */     println("void initLiterals();");
/*      */ 
/* 2661 */     this.tabs = 0;
/* 2662 */     println("public:");
/* 2663 */     this.tabs = 1;
/* 2664 */     println("bool getCaseSensitiveLiterals() const");
/* 2665 */     println("{");
/* 2666 */     this.tabs += 1;
/* 2667 */     println("return " + paramLexerGrammar.caseSensitiveLiterals + ";");
/* 2668 */     this.tabs -= 1;
/* 2669 */     println("}");
/*      */ 
/* 2672 */     this.tabs = 0;
/* 2673 */     println("public:");
/* 2674 */     this.tabs = 1;
/*      */ 
/* 2676 */     if (this.noConstructors)
/*      */     {
/* 2678 */       this.tabs = 0;
/* 2679 */       println("#if 0");
/* 2680 */       println("// constructor creation turned of with 'noConstructor' option");
/* 2681 */       this.tabs = 1;
/*      */     }
/*      */ 
/* 2685 */     println(this.grammar.getClassName() + "(" + namespaceStd + "istream& in);");
/*      */ 
/* 2688 */     println(this.grammar.getClassName() + "(" + namespaceAntlr + "InputBuffer& ib);");
/*      */ 
/* 2690 */     println(this.grammar.getClassName() + "(const " + namespaceAntlr + "LexerSharedInputState& state);");
/* 2691 */     if (this.noConstructors)
/*      */     {
/* 2693 */       this.tabs = 0;
/* 2694 */       println("// constructor creation turned of with 'noConstructor' option");
/* 2695 */       println("#endif");
/* 2696 */       this.tabs = 1;
/*      */     }
/*      */ 
/* 2702 */     println(namespaceAntlr + "RefToken nextToken();");
/*      */ 
/* 2705 */     Object localObject = this.grammar.rules.elements();
/* 2706 */     while (((Enumeration)localObject).hasMoreElements()) {
/* 2707 */       RuleSymbol localRuleSymbol = (RuleSymbol)((Enumeration)localObject).nextElement();
/*      */ 
/* 2709 */       if (!localRuleSymbol.getId().equals("mnextToken")) {
/* 2710 */         genRuleHeader(localRuleSymbol, false);
/*      */       }
/* 2712 */       exitIfError();
/*      */     }
/*      */ 
/* 2716 */     this.tabs = 0;
/* 2717 */     println("private:");
/* 2718 */     this.tabs = 1;
/*      */ 
/* 2721 */     if (this.grammar.debuggingOutput) {
/* 2722 */       println("static const char* _ruleNames[];");
/*      */     }
/*      */ 
/* 2726 */     if (this.grammar.debuggingOutput) {
/* 2727 */       println("static const char* _semPredNames[];");
/*      */     }
/*      */ 
/* 2730 */     genBitsetsHeader(this.bitsetsUsed, ((LexerGrammar)this.grammar).charVocabulary.size());
/*      */ 
/* 2732 */     this.tabs = 0;
/* 2733 */     println("};");
/* 2734 */     println("");
/* 2735 */     if (nameSpace != null) {
/* 2736 */       nameSpace.emitClosures(this.currentOutput);
/*      */     }
/*      */ 
/* 2739 */     println("#endif /*INC_" + this.grammar.getClassName() + "_hpp_*/");
/*      */ 
/* 2742 */     this.currentOutput.close();
/* 2743 */     this.currentOutput = null;
/*      */   }
/*      */ 
/*      */   public void genInclude(ParserGrammar paramParserGrammar) throws IOException
/*      */   {
/* 2748 */     this.outputFile = (this.grammar.getClassName() + ".hpp");
/* 2749 */     this.outputLine = 1;
/* 2750 */     this.currentOutput = this.antlrTool.openOutputFile(this.outputFile);
/*      */ 
/* 2753 */     this.genAST = this.grammar.buildAST;
/*      */ 
/* 2755 */     this.tabs = 0;
/*      */ 
/* 2758 */     println("#ifndef INC_" + this.grammar.getClassName() + "_hpp_");
/* 2759 */     println("#define INC_" + this.grammar.getClassName() + "_hpp_");
/* 2760 */     println("");
/* 2761 */     printHeaderAction("pre_include_hpp");
/* 2762 */     println("#include <antlr/config.hpp>");
/*      */ 
/* 2765 */     genHeader(this.outputFile);
/*      */ 
/* 2768 */     println("#include <antlr/TokenStream.hpp>");
/* 2769 */     println("#include <antlr/TokenBuffer.hpp>");
/* 2770 */     println("#include \"" + this.grammar.tokenManager.getName() + TokenTypesFileSuffix + ".hpp\"");
/*      */ 
/* 2773 */     String str = null;
/* 2774 */     if (this.grammar.superClass != null) {
/* 2775 */       str = this.grammar.superClass;
/* 2776 */       println("\n// Include correct superclass header with a header statement for example:");
/* 2777 */       println("// header \"post_include_hpp\" {");
/* 2778 */       println("// #include \"" + str + ".hpp\"");
/* 2779 */       println("// }");
/* 2780 */       println("// Or....");
/* 2781 */       println("// header {");
/* 2782 */       println("// #include \"" + str + ".hpp\"");
/* 2783 */       println("// }\n");
/*      */     }
/*      */     else {
/* 2786 */       str = this.grammar.getSuperClass();
/* 2787 */       if (str.lastIndexOf('.') != -1)
/* 2788 */         str = str.substring(str.lastIndexOf('.') + 1);
/* 2789 */       println("#include <antlr/" + str + ".hpp>");
/* 2790 */       str = namespaceAntlr + str;
/*      */     }
/* 2792 */     println("");
/*      */ 
/* 2795 */     printHeaderAction("post_include_hpp");
/*      */ 
/* 2797 */     if (nameSpace != null) {
/* 2798 */       nameSpace.emitDeclarations(this.currentOutput);
/*      */     }
/* 2800 */     printHeaderAction("");
/*      */ 
/* 2803 */     if (this.grammar.comment != null) {
/* 2804 */       _println(this.grammar.comment);
/*      */     }
/*      */ 
/* 2808 */     print("class CUSTOM_API " + this.grammar.getClassName() + " : public " + str);
/* 2809 */     println(", public " + this.grammar.tokenManager.getName() + TokenTypesFileSuffix);
/*      */ 
/* 2811 */     Token localToken = (Token)this.grammar.options.get("classHeaderSuffix");
/* 2812 */     if (localToken != null) {
/* 2813 */       localObject = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 2814 */       if (localObject != null)
/* 2815 */         print(", " + (String)localObject);
/*      */     }
/* 2817 */     println("{");
/*      */ 
/* 2821 */     if (this.grammar.debuggingOutput) {
/* 2822 */       println("public: static const char* _ruleNames[];");
/*      */     }
/*      */ 
/* 2825 */     if (this.grammar.classMemberAction != null) {
/* 2826 */       genLineNo(this.grammar.classMemberAction.getLine());
/* 2827 */       print(processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, null));
/*      */ 
/* 2832 */       genLineNo2();
/*      */     }
/* 2834 */     println("public:");
/* 2835 */     this.tabs = 1;
/* 2836 */     println("void initializeASTFactory( " + namespaceAntlr + "ASTFactory& factory );");
/*      */ 
/* 2841 */     this.tabs = 0;
/* 2842 */     if (this.noConstructors)
/*      */     {
/* 2844 */       println("#if 0");
/* 2845 */       println("// constructor creation turned of with 'noConstructor' option");
/*      */     }
/* 2847 */     println("protected:");
/* 2848 */     this.tabs = 1;
/* 2849 */     println(this.grammar.getClassName() + "(" + namespaceAntlr + "TokenBuffer& tokenBuf, int k);");
/* 2850 */     this.tabs = 0;
/* 2851 */     println("public:");
/* 2852 */     this.tabs = 1;
/* 2853 */     println(this.grammar.getClassName() + "(" + namespaceAntlr + "TokenBuffer& tokenBuf);");
/*      */ 
/* 2856 */     this.tabs = 0;
/* 2857 */     println("protected:");
/* 2858 */     this.tabs = 1;
/* 2859 */     println(this.grammar.getClassName() + "(" + namespaceAntlr + "TokenStream& lexer, int k);");
/* 2860 */     this.tabs = 0;
/* 2861 */     println("public:");
/* 2862 */     this.tabs = 1;
/* 2863 */     println(this.grammar.getClassName() + "(" + namespaceAntlr + "TokenStream& lexer);");
/*      */ 
/* 2865 */     println(this.grammar.getClassName() + "(const " + namespaceAntlr + "ParserSharedInputState& state);");
/* 2866 */     if (this.noConstructors)
/*      */     {
/* 2868 */       this.tabs = 0;
/* 2869 */       println("// constructor creation turned of with 'noConstructor' option");
/* 2870 */       println("#endif");
/* 2871 */       this.tabs = 1;
/*      */     }
/*      */ 
/* 2874 */     println("int getNumTokens() const");
/* 2875 */     println("{"); this.tabs += 1;
/* 2876 */     println("return " + this.grammar.getClassName() + "::NUM_TOKENS;");
/* 2877 */     this.tabs -= 1; println("}");
/* 2878 */     println("const char* getTokenName( int type ) const");
/* 2879 */     println("{"); this.tabs += 1;
/* 2880 */     println("if( type > getNumTokens() ) return 0;");
/* 2881 */     println("return " + this.grammar.getClassName() + "::tokenNames[type];");
/* 2882 */     this.tabs -= 1; println("}");
/* 2883 */     println("const char* const* getTokenNames() const");
/* 2884 */     println("{"); this.tabs += 1;
/* 2885 */     println("return " + this.grammar.getClassName() + "::tokenNames;");
/* 2886 */     this.tabs -= 1; println("}");
/*      */ 
/* 2889 */     Object localObject = this.grammar.rules.elements();
/* 2890 */     while (((Enumeration)localObject).hasMoreElements()) {
/* 2891 */       GrammarSymbol localGrammarSymbol = (GrammarSymbol)((Enumeration)localObject).nextElement();
/* 2892 */       if ((localGrammarSymbol instanceof RuleSymbol)) {
/* 2893 */         RuleSymbol localRuleSymbol = (RuleSymbol)localGrammarSymbol;
/* 2894 */         genRuleHeader(localRuleSymbol, localRuleSymbol.references.size() == 0);
/*      */       }
/* 2896 */       exitIfError();
/*      */     }
/*      */ 
/* 2904 */     this.tabs = 0; println("public:"); this.tabs = 1;
/* 2905 */     println(namespaceAntlr + "RefAST getAST()");
/* 2906 */     println("{");
/* 2907 */     if (this.usingCustomAST)
/*      */     {
/* 2909 */       this.tabs += 1;
/* 2910 */       println("return " + namespaceAntlr + "RefAST(returnAST);");
/* 2911 */       this.tabs -= 1;
/*      */     }
/*      */     else
/*      */     {
/* 2915 */       this.tabs += 1;
/* 2916 */       println("return returnAST;");
/* 2917 */       this.tabs -= 1;
/*      */     }
/* 2919 */     println("}");
/* 2920 */     println("");
/*      */ 
/* 2922 */     this.tabs = 0; println("protected:"); this.tabs = 1;
/* 2923 */     println(this.labeledElementASTType + " returnAST;");
/*      */ 
/* 2926 */     this.tabs = 0;
/* 2927 */     println("private:");
/* 2928 */     this.tabs = 1;
/*      */ 
/* 2931 */     println("static const char* tokenNames[];");
/*      */ 
/* 2933 */     _println("#ifndef NO_STATIC_CONSTS");
/* 2934 */     println("static const int NUM_TOKENS = " + this.grammar.tokenManager.getVocabulary().size() + ";");
/* 2935 */     _println("#else");
/* 2936 */     println("enum {");
/* 2937 */     println("\tNUM_TOKENS = " + this.grammar.tokenManager.getVocabulary().size());
/* 2938 */     println("};");
/* 2939 */     _println("#endif");
/*      */ 
/* 2942 */     genBitsetsHeader(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
/*      */ 
/* 2945 */     if (this.grammar.debuggingOutput) {
/* 2946 */       println("static const char* _semPredNames[];");
/*      */     }
/*      */ 
/* 2949 */     this.tabs = 0;
/* 2950 */     println("};");
/* 2951 */     println("");
/* 2952 */     if (nameSpace != null) {
/* 2953 */       nameSpace.emitClosures(this.currentOutput);
/*      */     }
/*      */ 
/* 2956 */     println("#endif /*INC_" + this.grammar.getClassName() + "_hpp_*/");
/*      */ 
/* 2959 */     this.currentOutput.close();
/* 2960 */     this.currentOutput = null;
/*      */   }
/*      */ 
/*      */   public void genInclude(TreeWalkerGrammar paramTreeWalkerGrammar) throws IOException
/*      */   {
/* 2965 */     this.outputFile = (this.grammar.getClassName() + ".hpp");
/* 2966 */     this.outputLine = 1;
/* 2967 */     this.currentOutput = this.antlrTool.openOutputFile(this.outputFile);
/*      */ 
/* 2970 */     this.genAST = this.grammar.buildAST;
/* 2971 */     this.tabs = 0;
/*      */ 
/* 2974 */     println("#ifndef INC_" + this.grammar.getClassName() + "_hpp_");
/* 2975 */     println("#define INC_" + this.grammar.getClassName() + "_hpp_");
/* 2976 */     println("");
/* 2977 */     printHeaderAction("pre_include_hpp");
/* 2978 */     println("#include <antlr/config.hpp>");
/* 2979 */     println("#include \"" + this.grammar.tokenManager.getName() + TokenTypesFileSuffix + ".hpp\"");
/*      */ 
/* 2982 */     genHeader(this.outputFile);
/*      */ 
/* 2985 */     String str1 = null;
/* 2986 */     if (this.grammar.superClass != null) {
/* 2987 */       str1 = this.grammar.superClass;
/* 2988 */       println("\n// Include correct superclass header with a header statement for example:");
/* 2989 */       println("// header \"post_include_hpp\" {");
/* 2990 */       println("// #include \"" + str1 + ".hpp\"");
/* 2991 */       println("// }");
/* 2992 */       println("// Or....");
/* 2993 */       println("// header {");
/* 2994 */       println("// #include \"" + str1 + ".hpp\"");
/* 2995 */       println("// }\n");
/*      */     }
/*      */     else {
/* 2998 */       str1 = this.grammar.getSuperClass();
/* 2999 */       if (str1.lastIndexOf('.') != -1)
/* 3000 */         str1 = str1.substring(str1.lastIndexOf('.') + 1);
/* 3001 */       println("#include <antlr/" + str1 + ".hpp>");
/* 3002 */       str1 = namespaceAntlr + str1;
/*      */     }
/* 3004 */     println("");
/*      */ 
/* 3009 */     printHeaderAction("post_include_hpp");
/*      */ 
/* 3011 */     if (nameSpace != null) {
/* 3012 */       nameSpace.emitDeclarations(this.currentOutput);
/*      */     }
/* 3014 */     printHeaderAction("");
/*      */ 
/* 3017 */     if (this.grammar.comment != null) {
/* 3018 */       _println(this.grammar.comment);
/*      */     }
/*      */ 
/* 3022 */     print("class CUSTOM_API " + this.grammar.getClassName() + " : public " + str1);
/* 3023 */     println(", public " + this.grammar.tokenManager.getName() + TokenTypesFileSuffix);
/*      */ 
/* 3025 */     Token localToken = (Token)this.grammar.options.get("classHeaderSuffix");
/* 3026 */     if (localToken != null) {
/* 3027 */       localObject = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 3028 */       if (localObject != null) {
/* 3029 */         print(", " + (String)localObject);
/*      */       }
/*      */     }
/* 3032 */     println("{");
/*      */ 
/* 3035 */     if (this.grammar.classMemberAction != null) {
/* 3036 */       genLineNo(this.grammar.classMemberAction.getLine());
/* 3037 */       print(processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, null));
/*      */ 
/* 3042 */       genLineNo2();
/*      */     }
/*      */ 
/* 3046 */     this.tabs = 0;
/* 3047 */     println("public:");
/*      */ 
/* 3049 */     if (this.noConstructors)
/*      */     {
/* 3051 */       println("#if 0");
/* 3052 */       println("// constructor creation turned of with 'noConstructor' option");
/*      */     }
/* 3054 */     this.tabs = 1;
/* 3055 */     println(this.grammar.getClassName() + "();");
/* 3056 */     if (this.noConstructors)
/*      */     {
/* 3058 */       this.tabs = 0;
/* 3059 */       println("#endif");
/* 3060 */       this.tabs = 1;
/*      */     }
/*      */ 
/* 3064 */     println("static void initializeASTFactory( " + namespaceAntlr + "ASTFactory& factory );");
/*      */ 
/* 3066 */     println("int getNumTokens() const");
/* 3067 */     println("{"); this.tabs += 1;
/* 3068 */     println("return " + this.grammar.getClassName() + "::NUM_TOKENS;");
/* 3069 */     this.tabs -= 1; println("}");
/* 3070 */     println("const char* getTokenName( int type ) const");
/* 3071 */     println("{"); this.tabs += 1;
/* 3072 */     println("if( type > getNumTokens() ) return 0;");
/* 3073 */     println("return " + this.grammar.getClassName() + "::tokenNames[type];");
/* 3074 */     this.tabs -= 1; println("}");
/* 3075 */     println("const char* const* getTokenNames() const");
/* 3076 */     println("{"); this.tabs += 1;
/* 3077 */     println("return " + this.grammar.getClassName() + "::tokenNames;");
/* 3078 */     this.tabs -= 1; println("}");
/*      */ 
/* 3081 */     Object localObject = this.grammar.rules.elements();
/* 3082 */     String str2 = "";
/* 3083 */     while (((Enumeration)localObject).hasMoreElements()) {
/* 3084 */       GrammarSymbol localGrammarSymbol = (GrammarSymbol)((Enumeration)localObject).nextElement();
/* 3085 */       if ((localGrammarSymbol instanceof RuleSymbol)) {
/* 3086 */         RuleSymbol localRuleSymbol = (RuleSymbol)localGrammarSymbol;
/* 3087 */         genRuleHeader(localRuleSymbol, localRuleSymbol.references.size() == 0);
/*      */       }
/* 3089 */       exitIfError();
/*      */     }
/* 3091 */     this.tabs = 0; println("public:"); this.tabs = 1;
/* 3092 */     println(namespaceAntlr + "RefAST getAST()");
/* 3093 */     println("{");
/* 3094 */     if (this.usingCustomAST)
/*      */     {
/* 3096 */       this.tabs += 1;
/* 3097 */       println("return " + namespaceAntlr + "RefAST(returnAST);");
/* 3098 */       this.tabs -= 1;
/*      */     }
/*      */     else
/*      */     {
/* 3102 */       this.tabs += 1;
/* 3103 */       println("return returnAST;");
/* 3104 */       this.tabs -= 1;
/*      */     }
/* 3106 */     println("}");
/* 3107 */     println("");
/*      */ 
/* 3109 */     this.tabs = 0; println("protected:"); this.tabs = 1;
/* 3110 */     println(this.labeledElementASTType + " returnAST;");
/* 3111 */     println(this.labeledElementASTType + " _retTree;");
/*      */ 
/* 3114 */     this.tabs = 0;
/* 3115 */     println("private:");
/* 3116 */     this.tabs = 1;
/*      */ 
/* 3119 */     println("static const char* tokenNames[];");
/*      */ 
/* 3121 */     _println("#ifndef NO_STATIC_CONSTS");
/* 3122 */     println("static const int NUM_TOKENS = " + this.grammar.tokenManager.getVocabulary().size() + ";");
/* 3123 */     _println("#else");
/* 3124 */     println("enum {");
/* 3125 */     println("\tNUM_TOKENS = " + this.grammar.tokenManager.getVocabulary().size());
/* 3126 */     println("};");
/* 3127 */     _println("#endif");
/*      */ 
/* 3130 */     genBitsetsHeader(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
/*      */ 
/* 3133 */     this.tabs = 0;
/* 3134 */     println("};");
/* 3135 */     println("");
/* 3136 */     if (nameSpace != null) {
/* 3137 */       nameSpace.emitClosures(this.currentOutput);
/*      */     }
/*      */ 
/* 3140 */     println("#endif /*INC_" + this.grammar.getClassName() + "_hpp_*/");
/*      */ 
/* 3143 */     this.currentOutput.close();
/* 3144 */     this.currentOutput = null;
/*      */   }
/*      */ 
/*      */   protected void genASTDeclaration(AlternativeElement paramAlternativeElement) {
/* 3148 */     genASTDeclaration(paramAlternativeElement, this.labeledElementASTType);
/*      */   }
/*      */ 
/*      */   protected void genASTDeclaration(AlternativeElement paramAlternativeElement, String paramString) {
/* 3152 */     genASTDeclaration(paramAlternativeElement, paramAlternativeElement.getLabel(), paramString);
/*      */   }
/*      */ 
/*      */   protected void genASTDeclaration(AlternativeElement paramAlternativeElement, String paramString1, String paramString2)
/*      */   {
/* 3157 */     if (this.declaredASTVariables.contains(paramAlternativeElement)) {
/* 3158 */       return;
/*      */     }
/* 3160 */     String str = this.labeledElementASTInit;
/*      */ 
/* 3162 */     if (((paramAlternativeElement instanceof GrammarAtom)) && (((GrammarAtom)paramAlternativeElement).getASTNodeType() != null))
/*      */     {
/* 3164 */       str = "Ref" + ((GrammarAtom)paramAlternativeElement).getASTNodeType() + "(" + this.labeledElementASTInit + ")";
/*      */     }
/*      */ 
/* 3167 */     println(paramString2 + " " + paramString1 + "_AST = " + str + ";");
/*      */ 
/* 3170 */     this.declaredASTVariables.put(paramAlternativeElement, paramAlternativeElement);
/*      */   }
/*      */   private void genLiteralsTest() {
/* 3173 */     println("_ttype = testLiteralsTable(_ttype);");
/*      */   }
/*      */   private void genLiteralsTestForPartialToken() {
/* 3176 */     println("_ttype = testLiteralsTable(text.substr(_begin, text.length()-_begin),_ttype);");
/*      */   }
/*      */   protected void genMatch(BitSet paramBitSet) {
/*      */   }
/*      */   protected void genMatch(GrammarAtom paramGrammarAtom) {
/* 3181 */     if ((paramGrammarAtom instanceof StringLiteralElement)) {
/* 3182 */       if ((this.grammar instanceof LexerGrammar)) {
/* 3183 */         genMatchUsingAtomText(paramGrammarAtom);
/*      */       }
/*      */       else {
/* 3186 */         genMatchUsingAtomTokenType(paramGrammarAtom);
/*      */       }
/*      */     }
/* 3189 */     else if ((paramGrammarAtom instanceof CharLiteralElement))
/*      */     {
/* 3191 */       this.antlrTool.error("cannot ref character literals in grammar: " + paramGrammarAtom);
/*      */     }
/* 3193 */     else if ((paramGrammarAtom instanceof TokenRefElement))
/* 3194 */       genMatchUsingAtomTokenType(paramGrammarAtom);
/* 3195 */     else if ((paramGrammarAtom instanceof WildcardElement))
/* 3196 */       gen((WildcardElement)paramGrammarAtom);
/*      */   }
/*      */ 
/*      */   protected void genMatchUsingAtomText(GrammarAtom paramGrammarAtom)
/*      */   {
/* 3201 */     String str1 = "";
/* 3202 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 3203 */       if (this.usingCustomAST)
/* 3204 */         str1 = namespaceAntlr + "RefAST" + "(_t),";
/*      */       else {
/* 3206 */         str1 = "_t,";
/*      */       }
/*      */     }
/*      */ 
/* 3210 */     if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramGrammarAtom.getAutoGenType() == 3))) {
/* 3211 */       println("_saveIndex = text.length();");
/*      */     }
/*      */ 
/* 3214 */     print(paramGrammarAtom.not ? "matchNot(" : "match(");
/* 3215 */     _print(str1);
/*      */ 
/* 3218 */     if (paramGrammarAtom.atomText.equals("EOF"))
/*      */     {
/* 3220 */       _print(namespaceAntlr + "Token::EOF_TYPE");
/*      */     }
/* 3224 */     else if ((this.grammar instanceof LexerGrammar))
/*      */     {
/* 3226 */       String str2 = convertJavaToCppString(paramGrammarAtom.atomText, false);
/* 3227 */       _print(str2);
/*      */     }
/*      */     else {
/* 3230 */       _print(paramGrammarAtom.atomText);
/*      */     }
/*      */ 
/* 3233 */     _println(");");
/*      */ 
/* 3235 */     if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramGrammarAtom.getAutoGenType() == 3)))
/* 3236 */       println("text.erase(_saveIndex);");
/*      */   }
/*      */ 
/*      */   protected void genMatchUsingAtomTokenType(GrammarAtom paramGrammarAtom)
/*      */   {
/* 3241 */     String str1 = "";
/* 3242 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 3243 */       if (this.usingCustomAST)
/* 3244 */         str1 = namespaceAntlr + "RefAST" + "(_t),";
/*      */       else {
/* 3246 */         str1 = "_t,";
/*      */       }
/*      */     }
/*      */ 
/* 3250 */     String str2 = str1 + getValueString(paramGrammarAtom.getType());
/*      */ 
/* 3253 */     println((paramGrammarAtom.not ? "matchNot(" : "match(") + str2 + ");");
/*      */   }
/*      */ 
/*      */   public void genNextToken()
/*      */   {
/* 3263 */     int i = 0;
/* 3264 */     for (int j = 0; j < this.grammar.rules.size(); j++) {
/* 3265 */       localRuleSymbol1 = (RuleSymbol)this.grammar.rules.elementAt(j);
/* 3266 */       if ((localRuleSymbol1.isDefined()) && (localRuleSymbol1.access.equals("public"))) {
/* 3267 */         i = 1;
/* 3268 */         break;
/*      */       }
/*      */     }
/* 3271 */     if (i == 0) {
/* 3272 */       println("");
/* 3273 */       println(namespaceAntlr + "RefToken " + this.grammar.getClassName() + "::nextToken() { return " + namespaceAntlr + "RefToken(new " + namespaceAntlr + "CommonToken(" + namespaceAntlr + "Token::EOF_TYPE, \"\")); }");
/* 3274 */       println("");
/* 3275 */       return;
/*      */     }
/*      */ 
/* 3279 */     RuleBlock localRuleBlock = MakeGrammar.createNextTokenRule(this.grammar, this.grammar.rules, "nextToken");
/*      */ 
/* 3281 */     RuleSymbol localRuleSymbol1 = new RuleSymbol("mnextToken");
/* 3282 */     localRuleSymbol1.setDefined();
/* 3283 */     localRuleSymbol1.setBlock(localRuleBlock);
/* 3284 */     localRuleSymbol1.access = "private";
/* 3285 */     this.grammar.define(localRuleSymbol1);
/*      */ 
/* 3287 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(localRuleBlock);
/*      */ 
/* 3290 */     String str1 = null;
/* 3291 */     if (((LexerGrammar)this.grammar).filterMode) {
/* 3292 */       str1 = ((LexerGrammar)this.grammar).filterRule;
/*      */     }
/*      */ 
/* 3295 */     println("");
/* 3296 */     println(namespaceAntlr + "RefToken " + this.grammar.getClassName() + "::nextToken()");
/* 3297 */     println("{");
/* 3298 */     this.tabs += 1;
/* 3299 */     println(namespaceAntlr + "RefToken theRetToken;");
/* 3300 */     println("for (;;) {");
/* 3301 */     this.tabs += 1;
/* 3302 */     println(namespaceAntlr + "RefToken theRetToken;");
/* 3303 */     println("int _ttype = " + namespaceAntlr + "Token::INVALID_TYPE;");
/* 3304 */     if (((LexerGrammar)this.grammar).filterMode) {
/* 3305 */       println("setCommitToPath(false);");
/* 3306 */       if (str1 != null)
/*      */       {
/* 3308 */         if (!this.grammar.isDefined(CodeGenerator.encodeLexerRuleName(str1))) {
/* 3309 */           this.grammar.antlrTool.error("Filter rule " + str1 + " does not exist in this lexer");
/*      */         }
/*      */         else {
/* 3312 */           RuleSymbol localRuleSymbol2 = (RuleSymbol)this.grammar.getSymbol(CodeGenerator.encodeLexerRuleName(str1));
/* 3313 */           if (!localRuleSymbol2.isDefined()) {
/* 3314 */             this.grammar.antlrTool.error("Filter rule " + str1 + " does not exist in this lexer");
/*      */           }
/* 3316 */           else if (localRuleSymbol2.access.equals("public")) {
/* 3317 */             this.grammar.antlrTool.error("Filter rule " + str1 + " must be protected");
/*      */           }
/*      */         }
/* 3320 */         println("int _m;");
/* 3321 */         println("_m = mark();");
/*      */       }
/*      */     }
/* 3324 */     println("resetText();");
/*      */ 
/* 3327 */     println("try {   // for lexical and char stream error handling");
/* 3328 */     this.tabs += 1;
/*      */ 
/* 3331 */     for (int k = 0; k < localRuleBlock.getAlternatives().size(); k++) {
/* 3332 */       localObject = localRuleBlock.getAlternativeAt(k);
/* 3333 */       if (localObject.cache[1].containsEpsilon()) {
/* 3334 */         this.antlrTool.warning("found optional path in nextToken()");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3339 */     String str2 = System.getProperty("line.separator");
/* 3340 */     Object localObject = genCommonBlock(localRuleBlock, false);
/* 3341 */     String str3 = "if (LA(1)==EOF_CHAR)" + str2 + "\t\t\t\t{" + str2 + "\t\t\t\t\tuponEOF();" + str2 + "\t\t\t\t\t_returnToken = makeToken(" + namespaceAntlr + "Token::EOF_TYPE);" + str2 + "\t\t\t\t}";
/*      */ 
/* 3345 */     str3 = str3 + str2 + "\t\t\t\t";
/* 3346 */     if (((LexerGrammar)this.grammar).filterMode) {
/* 3347 */       if (str1 == null) {
/* 3348 */         str3 = str3 + "else {consume(); goto tryAgain;}";
/*      */       }
/*      */       else {
/* 3351 */         str3 = str3 + "else {" + str2 + "\t\t\t\t\tcommit();" + str2 + "\t\t\t\t\ttry {m" + str1 + "(false);}" + str2 + "\t\t\t\t\tcatch(" + namespaceAntlr + "RecognitionException& e) {" + str2 + "\t\t\t\t\t\t// catastrophic failure" + str2 + "\t\t\t\t\t\treportError(e);" + str2 + "\t\t\t\t\t\tconsume();" + str2 + "\t\t\t\t\t}" + str2 + "\t\t\t\t\tgoto tryAgain;" + str2 + "\t\t\t\t}";
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 3364 */       str3 = str3 + "else {" + this.throwNoViable + "}";
/*      */     }
/* 3366 */     genBlockFinish((CppBlockFinishingInfo)localObject, str3);
/*      */ 
/* 3369 */     if ((((LexerGrammar)this.grammar).filterMode) && (str1 != null)) {
/* 3370 */       println("commit();");
/*      */     }
/*      */ 
/* 3376 */     println("if ( !_returnToken )" + str2 + "\t\t\t\tgoto tryAgain; // found SKIP token" + str2);
/*      */ 
/* 3378 */     println("_ttype = _returnToken->getType();");
/* 3379 */     if (((LexerGrammar)this.grammar).getTestLiterals()) {
/* 3380 */       genLiteralsTest();
/*      */     }
/*      */ 
/* 3384 */     println("_returnToken->setType(_ttype);");
/* 3385 */     println("return _returnToken;");
/*      */ 
/* 3388 */     this.tabs -= 1;
/* 3389 */     println("}");
/* 3390 */     println("catch (" + namespaceAntlr + "RecognitionException& e) {");
/* 3391 */     this.tabs += 1;
/* 3392 */     if (((LexerGrammar)this.grammar).filterMode) {
/* 3393 */       if (str1 == null) {
/* 3394 */         println("if ( !getCommitToPath() ) {");
/* 3395 */         this.tabs += 1;
/* 3396 */         println("consume();");
/* 3397 */         println("goto tryAgain;");
/* 3398 */         this.tabs -= 1;
/* 3399 */         println("}");
/*      */       }
/*      */       else {
/* 3402 */         println("if ( !getCommitToPath() ) {");
/* 3403 */         this.tabs += 1;
/* 3404 */         println("rewind(_m);");
/* 3405 */         println("resetText();");
/* 3406 */         println("try {m" + str1 + "(false);}");
/* 3407 */         println("catch(" + namespaceAntlr + "RecognitionException& ee) {");
/* 3408 */         println("\t// horrendous failure: error in filter rule");
/* 3409 */         println("\treportError(ee);");
/* 3410 */         println("\tconsume();");
/* 3411 */         println("}");
/*      */ 
/* 3413 */         this.tabs -= 1;
/* 3414 */         println("}");
/* 3415 */         println("else");
/*      */       }
/*      */     }
/* 3418 */     if (localRuleBlock.getDefaultErrorHandler()) {
/* 3419 */       println("{");
/* 3420 */       this.tabs += 1;
/* 3421 */       println("reportError(e);");
/* 3422 */       println("consume();");
/* 3423 */       this.tabs -= 1;
/* 3424 */       println("}");
/*      */     }
/*      */     else
/*      */     {
/* 3428 */       this.tabs += 1;
/* 3429 */       println("throw " + namespaceAntlr + "TokenStreamRecognitionException(e);");
/* 3430 */       this.tabs -= 1;
/*      */     }
/*      */ 
/* 3434 */     this.tabs -= 1;
/* 3435 */     println("}");
/* 3436 */     println("catch (" + namespaceAntlr + "CharStreamIOException& csie) {");
/* 3437 */     println("\tthrow " + namespaceAntlr + "TokenStreamIOException(csie.io);");
/* 3438 */     println("}");
/* 3439 */     println("catch (" + namespaceAntlr + "CharStreamException& cse) {");
/* 3440 */     println("\tthrow " + namespaceAntlr + "TokenStreamException(cse.getMessage());");
/* 3441 */     println("}");
/*      */ 
/* 3444 */     _println("tryAgain:;");
/* 3445 */     this.tabs -= 1;
/* 3446 */     println("}");
/*      */ 
/* 3449 */     this.tabs -= 1;
/* 3450 */     println("}");
/* 3451 */     println("");
/*      */   }
/*      */ 
/*      */   public void genRule(RuleSymbol paramRuleSymbol, boolean paramBoolean, int paramInt, String paramString)
/*      */   {
/* 3471 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) System.out.println("genRule(" + paramRuleSymbol.getId() + ")");
/* 3472 */     if (!paramRuleSymbol.isDefined()) {
/* 3473 */       this.antlrTool.error("undefined rule: " + paramRuleSymbol.getId());
/* 3474 */       return;
/*      */     }
/*      */ 
/* 3478 */     RuleBlock localRuleBlock = paramRuleSymbol.getBlock();
/*      */ 
/* 3480 */     this.currentRule = localRuleBlock;
/* 3481 */     this.currentASTResult = paramRuleSymbol.getId();
/*      */ 
/* 3484 */     this.declaredASTVariables.clear();
/*      */ 
/* 3487 */     boolean bool1 = this.genAST;
/* 3488 */     this.genAST = ((this.genAST) && (localRuleBlock.getAutoGen()));
/*      */ 
/* 3491 */     this.saveText = localRuleBlock.getAutoGen();
/*      */ 
/* 3494 */     if (paramRuleSymbol.comment != null) {
/* 3495 */       _println(paramRuleSymbol.comment);
/*      */     }
/*      */ 
/* 3499 */     if (localRuleBlock.returnAction != null)
/*      */     {
/* 3502 */       _print(extractTypeOfAction(localRuleBlock.returnAction, localRuleBlock.getLine(), localRuleBlock.getColumn()) + " ");
/*      */     }
/*      */     else {
/* 3505 */       _print("void ");
/*      */     }
/*      */ 
/* 3509 */     _print(paramString + paramRuleSymbol.getId() + "(");
/*      */ 
/* 3512 */     _print(this.commonExtraParams);
/* 3513 */     if ((this.commonExtraParams.length() != 0) && (localRuleBlock.argAction != null))
/* 3514 */       _print(",");
/*      */     Object localObject2;
/*      */     Object localObject3;
/* 3518 */     if (localRuleBlock.argAction != null)
/*      */     {
/* 3521 */       _println("");
/*      */ 
/* 3524 */       this.tabs += 1;
/*      */ 
/* 3534 */       localObject1 = localRuleBlock.argAction;
/* 3535 */       localObject2 = "";
/*      */ 
/* 3537 */       localObject3 = "";
/* 3538 */       int i = ((String)localObject1).indexOf('=');
/* 3539 */       if (i != -1)
/*      */       {
/* 3541 */         int j = 0;
/* 3542 */         while ((j != -1) && (i != -1))
/*      */         {
/* 3544 */           localObject2 = (String)localObject2 + (String)localObject3 + ((String)localObject1).substring(0, i).trim();
/* 3545 */           localObject3 = ", ";
/* 3546 */           j = ((String)localObject1).indexOf(',', i);
/* 3547 */           if (j != -1)
/*      */           {
/* 3550 */             localObject1 = ((String)localObject1).substring(j + 1).trim();
/* 3551 */             i = ((String)localObject1).indexOf('=');
/* 3552 */             if (i == -1) {
/* 3553 */               localObject2 = (String)localObject2 + (String)localObject3 + (String)localObject1;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 3558 */       localObject2 = localObject1;
/*      */ 
/* 3560 */       println((String)localObject2);
/*      */ 
/* 3563 */       this.tabs -= 1;
/* 3564 */       print(") ");
/*      */     }
/*      */     else
/*      */     {
/* 3568 */       _print(") ");
/*      */     }
/* 3570 */     _println("{");
/* 3571 */     this.tabs += 1;
/*      */ 
/* 3573 */     if (this.grammar.traceRules) {
/* 3574 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 3575 */         if (this.usingCustomAST)
/* 3576 */           println("Tracer traceInOut(this,\"" + paramRuleSymbol.getId() + "\"," + namespaceAntlr + "RefAST" + "(_t));");
/*      */         else
/* 3578 */           println("Tracer traceInOut(this,\"" + paramRuleSymbol.getId() + "\",_t);");
/*      */       }
/*      */       else {
/* 3581 */         println("Tracer traceInOut(this, \"" + paramRuleSymbol.getId() + "\");");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3586 */     if (localRuleBlock.returnAction != null)
/*      */     {
/* 3588 */       genLineNo(localRuleBlock);
/* 3589 */       println(localRuleBlock.returnAction + ";");
/* 3590 */       genLineNo2();
/*      */     }
/*      */ 
/* 3594 */     if (!this.commonLocalVars.equals("")) {
/* 3595 */       println(this.commonLocalVars);
/*      */     }
/* 3597 */     if ((this.grammar instanceof LexerGrammar))
/*      */     {
/* 3602 */       if (paramRuleSymbol.getId().equals("mEOF"))
/* 3603 */         println("_ttype = " + namespaceAntlr + "Token::EOF_TYPE;");
/*      */       else
/* 3605 */         println("_ttype = " + paramRuleSymbol.getId().substring(1) + ";");
/* 3606 */       println(namespaceStd + "string::size_type _saveIndex;");
/*      */     }
/*      */ 
/* 3616 */     if (this.grammar.debuggingOutput) {
/* 3617 */       if ((this.grammar instanceof ParserGrammar))
/* 3618 */         println("fireEnterRule(" + paramInt + ",0);");
/* 3619 */       else if ((this.grammar instanceof LexerGrammar)) {
/* 3620 */         println("fireEnterRule(" + paramInt + ",_ttype);");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3629 */     if ((this.grammar instanceof TreeWalkerGrammar))
/*      */     {
/* 3632 */       println(this.labeledElementASTType + " " + paramRuleSymbol.getId() + "_AST_in = (_t == " + this.labeledElementASTType + "(ASTNULL)) ? " + this.labeledElementASTInit + " : _t;");
/*      */     }
/* 3634 */     if (this.grammar.buildAST)
/*      */     {
/* 3636 */       println("returnAST = " + this.labeledElementASTInit + ";");
/*      */ 
/* 3638 */       println(namespaceAntlr + "ASTPair currentAST;");
/*      */ 
/* 3640 */       println(this.labeledElementASTType + " " + paramRuleSymbol.getId() + "_AST = " + this.labeledElementASTInit + ";");
/*      */     }
/*      */ 
/* 3643 */     genBlockPreamble(localRuleBlock);
/* 3644 */     genBlockInitAction(localRuleBlock);
/* 3645 */     println("");
/*      */ 
/* 3648 */     Object localObject1 = localRuleBlock.findExceptionSpec("");
/*      */ 
/* 3651 */     if ((localObject1 != null) || (localRuleBlock.getDefaultErrorHandler())) {
/* 3652 */       println("try {      // for error handling");
/* 3653 */       this.tabs += 1;
/*      */     }
/*      */ 
/* 3657 */     if (localRuleBlock.alternatives.size() == 1)
/*      */     {
/* 3660 */       localObject2 = localRuleBlock.getAlternativeAt(0);
/* 3661 */       localObject3 = ((Alternative)localObject2).semPred;
/* 3662 */       if (localObject3 != null)
/* 3663 */         genSemPred((String)localObject3, this.currentRule.line);
/* 3664 */       if (((Alternative)localObject2).synPred != null) {
/* 3665 */         this.antlrTool.warning("Syntactic predicate ignored for single alternative", this.grammar.getFilename(), ((Alternative)localObject2).synPred.getLine(), ((Alternative)localObject2).synPred.getColumn());
/*      */       }
/*      */ 
/* 3672 */       genAlt((Alternative)localObject2, localRuleBlock);
/*      */     }
/*      */     else
/*      */     {
/* 3677 */       boolean bool2 = this.grammar.theLLkAnalyzer.deterministic(localRuleBlock);
/*      */ 
/* 3679 */       localObject3 = genCommonBlock(localRuleBlock, false);
/* 3680 */       genBlockFinish((CppBlockFinishingInfo)localObject3, this.throwNoViable);
/*      */     }
/*      */ 
/* 3684 */     if ((localObject1 != null) || (localRuleBlock.getDefaultErrorHandler()))
/*      */     {
/* 3686 */       this.tabs -= 1;
/* 3687 */       println("}");
/*      */     }
/*      */ 
/* 3691 */     if (localObject1 != null)
/*      */     {
/* 3693 */       genErrorHandler((ExceptionSpec)localObject1);
/*      */     }
/* 3695 */     else if (localRuleBlock.getDefaultErrorHandler())
/*      */     {
/* 3698 */       println("catch (" + this.exceptionThrown + "& ex) {");
/* 3699 */       this.tabs += 1;
/*      */ 
/* 3701 */       if (this.grammar.hasSyntacticPredicate) {
/* 3702 */         println("if( inputState->guessing == 0 ) {");
/* 3703 */         this.tabs += 1;
/*      */       }
/* 3705 */       println("reportError(ex);");
/* 3706 */       if (!(this.grammar instanceof TreeWalkerGrammar))
/*      */       {
/* 3709 */         Lookahead localLookahead = this.grammar.theLLkAnalyzer.FOLLOW(1, localRuleBlock.endNode);
/* 3710 */         localObject3 = getBitsetName(markBitsetForGen(localLookahead.fset));
/* 3711 */         println("recover(ex," + (String)localObject3 + ");");
/*      */       }
/*      */       else
/*      */       {
/* 3716 */         println("if ( _t != " + this.labeledElementASTInit + " )");
/* 3717 */         this.tabs += 1;
/* 3718 */         println("_t = _t->getNextSibling();");
/* 3719 */         this.tabs -= 1;
/*      */       }
/* 3721 */       if (this.grammar.hasSyntacticPredicate)
/*      */       {
/* 3723 */         this.tabs -= 1;
/*      */ 
/* 3725 */         println("} else {");
/* 3726 */         this.tabs += 1;
/* 3727 */         println("throw;");
/* 3728 */         this.tabs -= 1;
/* 3729 */         println("}");
/*      */       }
/*      */ 
/* 3732 */       this.tabs -= 1;
/* 3733 */       println("}");
/*      */     }
/*      */ 
/* 3737 */     if (this.grammar.buildAST) {
/* 3738 */       println("returnAST = " + paramRuleSymbol.getId() + "_AST;");
/*      */     }
/*      */ 
/* 3742 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 3743 */       println("_retTree = _t;");
/*      */     }
/*      */ 
/* 3747 */     if (localRuleBlock.getTestLiterals()) {
/* 3748 */       if (paramRuleSymbol.access.equals("protected")) {
/* 3749 */         genLiteralsTestForPartialToken();
/*      */       }
/*      */       else {
/* 3752 */         genLiteralsTest();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3757 */     if ((this.grammar instanceof LexerGrammar)) {
/* 3758 */       println("if ( _createToken && _token==" + namespaceAntlr + "nullToken && _ttype!=" + namespaceAntlr + "Token::SKIP ) {");
/* 3759 */       println("   _token = makeToken(_ttype);");
/* 3760 */       println("   _token->setText(text.substr(_begin, text.length()-_begin));");
/* 3761 */       println("}");
/* 3762 */       println("_returnToken = _token;");
/*      */ 
/* 3765 */       println("_saveIndex=0;");
/*      */     }
/*      */ 
/* 3769 */     if (localRuleBlock.returnAction != null) {
/* 3770 */       println("return " + extractIdOfAction(localRuleBlock.returnAction, localRuleBlock.getLine(), localRuleBlock.getColumn()) + ";");
/*      */     }
/*      */ 
/* 3798 */     this.tabs -= 1;
/* 3799 */     println("}");
/* 3800 */     println("");
/*      */ 
/* 3803 */     this.genAST = bool1;
/*      */   }
/*      */ 
/*      */   public void genRuleHeader(RuleSymbol paramRuleSymbol, boolean paramBoolean)
/*      */   {
/* 3809 */     this.tabs = 1;
/* 3810 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) System.out.println("genRuleHeader(" + paramRuleSymbol.getId() + ")");
/* 3811 */     if (!paramRuleSymbol.isDefined()) {
/* 3812 */       this.antlrTool.error("undefined rule: " + paramRuleSymbol.getId());
/* 3813 */       return;
/*      */     }
/*      */ 
/* 3817 */     RuleBlock localRuleBlock = paramRuleSymbol.getBlock();
/* 3818 */     this.currentRule = localRuleBlock;
/* 3819 */     this.currentASTResult = paramRuleSymbol.getId();
/*      */ 
/* 3822 */     boolean bool = this.genAST;
/* 3823 */     this.genAST = ((this.genAST) && (localRuleBlock.getAutoGen()));
/*      */ 
/* 3826 */     this.saveText = localRuleBlock.getAutoGen();
/*      */ 
/* 3829 */     print(paramRuleSymbol.access + ": ");
/*      */ 
/* 3832 */     if (localRuleBlock.returnAction != null)
/*      */     {
/* 3835 */       _print(extractTypeOfAction(localRuleBlock.returnAction, localRuleBlock.getLine(), localRuleBlock.getColumn()) + " ");
/*      */     }
/*      */     else {
/* 3838 */       _print("void ");
/*      */     }
/*      */ 
/* 3842 */     _print(paramRuleSymbol.getId() + "(");
/*      */ 
/* 3845 */     _print(this.commonExtraParams);
/* 3846 */     if ((this.commonExtraParams.length() != 0) && (localRuleBlock.argAction != null)) {
/* 3847 */       _print(",");
/*      */     }
/*      */ 
/* 3851 */     if (localRuleBlock.argAction != null)
/*      */     {
/* 3854 */       _println("");
/* 3855 */       this.tabs += 1;
/* 3856 */       println(localRuleBlock.argAction);
/* 3857 */       this.tabs -= 1;
/* 3858 */       print(")");
/*      */     }
/*      */     else {
/* 3861 */       _print(")");
/*      */     }
/* 3863 */     _println(";");
/*      */ 
/* 3865 */     this.tabs -= 1;
/*      */ 
/* 3868 */     this.genAST = bool;
/*      */   }
/*      */ 
/*      */   private void GenRuleInvocation(RuleRefElement paramRuleRefElement)
/*      */   {
/* 3875 */     _print(paramRuleRefElement.targetRule + "(");
/*      */ 
/* 3878 */     if ((this.grammar instanceof LexerGrammar))
/*      */     {
/* 3880 */       if (paramRuleRefElement.getLabel() != null) {
/* 3881 */         _print("true");
/*      */       }
/*      */       else {
/* 3884 */         _print("false");
/*      */       }
/* 3886 */       if ((this.commonExtraArgs.length() != 0) || (paramRuleRefElement.args != null)) {
/* 3887 */         _print(",");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3892 */     _print(this.commonExtraArgs);
/* 3893 */     if ((this.commonExtraArgs.length() != 0) && (paramRuleRefElement.args != null)) {
/* 3894 */       _print(",");
/*      */     }
/*      */ 
/* 3898 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(paramRuleRefElement.targetRule);
/* 3899 */     if (paramRuleRefElement.args != null)
/*      */     {
/* 3902 */       ActionTransInfo localActionTransInfo = new ActionTransInfo();
/*      */ 
/* 3905 */       String str = processActionForSpecialSymbols(paramRuleRefElement.args, paramRuleRefElement.line, this.currentRule, localActionTransInfo);
/*      */ 
/* 3907 */       if ((localActionTransInfo.assignToRoot) || (localActionTransInfo.refRuleRoot != null))
/*      */       {
/* 3909 */         this.antlrTool.error("Arguments of rule reference '" + paramRuleRefElement.targetRule + "' cannot set or ref #" + this.currentRule.getRuleName() + " on line " + paramRuleRefElement.getLine());
/*      */       }
/*      */ 
/* 3912 */       _print(str);
/*      */ 
/* 3915 */       if (localRuleSymbol.block.argAction == null)
/*      */       {
/* 3917 */         this.antlrTool.warning("Rule '" + paramRuleRefElement.targetRule + "' accepts no arguments", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3930 */     _println(");");
/*      */ 
/* 3933 */     if ((this.grammar instanceof TreeWalkerGrammar))
/* 3934 */       println("_t = _retTree;");
/*      */   }
/*      */ 
/*      */   protected void genSemPred(String paramString, int paramInt)
/*      */   {
/* 3939 */     ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 3940 */     paramString = processActionForSpecialSymbols(paramString, paramInt, this.currentRule, localActionTransInfo);
/*      */ 
/* 3942 */     String str = this.charFormatter.escapeString(paramString);
/*      */ 
/* 3946 */     if ((this.grammar.debuggingOutput) && (((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar))))
/*      */     {
/* 3948 */       paramString = "fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEvent.VALIDATING," + addSemPred(str) + "," + paramString + ")";
/*      */     }
/* 3950 */     println("if (!(" + paramString + "))");
/* 3951 */     this.tabs += 1;
/* 3952 */     println("throw " + namespaceAntlr + "SemanticException(\"" + str + "\");");
/* 3953 */     this.tabs -= 1;
/*      */   }
/*      */ 
/*      */   protected void genSemPredMap(String paramString)
/*      */   {
/* 3959 */     Enumeration localEnumeration = this.semPreds.elements();
/* 3960 */     println("const char* " + paramString + "_semPredNames[] = {");
/* 3961 */     this.tabs += 1;
/* 3962 */     while (localEnumeration.hasMoreElements())
/* 3963 */       println("\"" + localEnumeration.nextElement() + "\",");
/* 3964 */     println("0");
/* 3965 */     this.tabs -= 1;
/* 3966 */     println("};");
/*      */   }
/*      */   protected void genSynPred(SynPredBlock paramSynPredBlock, String paramString) {
/* 3969 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) System.out.println("gen=>(" + paramSynPredBlock + ")");
/*      */ 
/* 3972 */     println("bool synPredMatched" + paramSynPredBlock.ID + " = false;");
/*      */ 
/* 3974 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 3975 */       println("if (_t == " + this.labeledElementASTInit + " )");
/* 3976 */       this.tabs += 1;
/* 3977 */       println("_t = ASTNULL;");
/* 3978 */       this.tabs -= 1;
/*      */     }
/*      */ 
/* 3982 */     println("if (" + paramString + ") {");
/* 3983 */     this.tabs += 1;
/*      */ 
/* 3986 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 3987 */       println(this.labeledElementType + " __t" + paramSynPredBlock.ID + " = _t;");
/*      */     }
/*      */     else {
/* 3990 */       println("int _m" + paramSynPredBlock.ID + " = mark();");
/*      */     }
/*      */ 
/* 3994 */     println("synPredMatched" + paramSynPredBlock.ID + " = true;");
/* 3995 */     println("inputState->guessing++;");
/*      */ 
/* 3998 */     if ((this.grammar.debuggingOutput) && (((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar))))
/*      */     {
/* 4000 */       println("fireSyntacticPredicateStarted();");
/*      */     }
/*      */ 
/* 4003 */     this.syntacticPredLevel += 1;
/* 4004 */     println("try {");
/* 4005 */     this.tabs += 1;
/* 4006 */     gen(paramSynPredBlock);
/* 4007 */     this.tabs -= 1;
/*      */ 
/* 4009 */     println("}");
/* 4010 */     println("catch (" + this.exceptionThrown + "& pe) {");
/* 4011 */     this.tabs += 1;
/* 4012 */     println("synPredMatched" + paramSynPredBlock.ID + " = false;");
/*      */ 
/* 4014 */     this.tabs -= 1;
/* 4015 */     println("}");
/*      */ 
/* 4018 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 4019 */       println("_t = __t" + paramSynPredBlock.ID + ";");
/*      */     }
/*      */     else {
/* 4022 */       println("rewind(_m" + paramSynPredBlock.ID + ");");
/*      */     }
/*      */ 
/* 4025 */     println("inputState->guessing--;");
/*      */ 
/* 4028 */     if ((this.grammar.debuggingOutput) && (((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar))))
/*      */     {
/* 4030 */       println("if (synPredMatched" + paramSynPredBlock.ID + ")");
/* 4031 */       println("  fireSyntacticPredicateSucceeded();");
/* 4032 */       println("else");
/* 4033 */       println("  fireSyntacticPredicateFailed();");
/*      */     }
/*      */ 
/* 4036 */     this.syntacticPredLevel -= 1;
/* 4037 */     this.tabs -= 1;
/*      */ 
/* 4040 */     println("}");
/*      */ 
/* 4043 */     println("if ( synPredMatched" + paramSynPredBlock.ID + " ) {");
/*      */   }
/*      */ 
/*      */   public void genTokenStrings(String paramString)
/*      */   {
/* 4057 */     println("const char* " + paramString + "tokenNames[] = {");
/* 4058 */     this.tabs += 1;
/*      */ 
/* 4062 */     Vector localVector = this.grammar.tokenManager.getVocabulary();
/* 4063 */     for (int i = 0; i < localVector.size(); i++)
/*      */     {
/* 4065 */       String str = (String)localVector.elementAt(i);
/* 4066 */       if (str == null)
/*      */       {
/* 4068 */         str = "<" + String.valueOf(i) + ">";
/*      */       }
/* 4070 */       if ((!str.startsWith("\"")) && (!str.startsWith("<"))) {
/* 4071 */         TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol(str);
/* 4072 */         if ((localTokenSymbol != null) && (localTokenSymbol.getParaphrase() != null)) {
/* 4073 */           str = StringUtils.stripFrontBack(localTokenSymbol.getParaphrase(), "\"", "\"");
/*      */         }
/*      */       }
/* 4076 */       print(this.charFormatter.literalString(str));
/* 4077 */       _println(",");
/*      */     }
/* 4079 */     println("0");
/*      */ 
/* 4082 */     this.tabs -= 1;
/* 4083 */     println("};");
/*      */   }
/*      */ 
/*      */   protected void genTokenTypes(TokenManager paramTokenManager) throws IOException
/*      */   {
/* 4088 */     this.outputFile = (paramTokenManager.getName() + TokenTypesFileSuffix + ".hpp");
/* 4089 */     this.outputLine = 1;
/* 4090 */     this.currentOutput = this.antlrTool.openOutputFile(this.outputFile);
/*      */ 
/* 4093 */     this.tabs = 0;
/*      */ 
/* 4096 */     println("#ifndef INC_" + paramTokenManager.getName() + TokenTypesFileSuffix + "_hpp_");
/* 4097 */     println("#define INC_" + paramTokenManager.getName() + TokenTypesFileSuffix + "_hpp_");
/* 4098 */     println("");
/*      */ 
/* 4100 */     if (nameSpace != null) {
/* 4101 */       nameSpace.emitDeclarations(this.currentOutput);
/*      */     }
/*      */ 
/* 4104 */     genHeader(this.outputFile);
/*      */ 
/* 4108 */     println("");
/* 4109 */     println("#ifndef CUSTOM_API");
/* 4110 */     println("# define CUSTOM_API");
/* 4111 */     println("#endif");
/* 4112 */     println("");
/*      */ 
/* 4115 */     println("#ifdef __cplusplus");
/* 4116 */     println("struct CUSTOM_API " + paramTokenManager.getName() + TokenTypesFileSuffix + " {");
/* 4117 */     println("#endif");
/* 4118 */     this.tabs += 1;
/* 4119 */     println("enum {");
/* 4120 */     this.tabs += 1;
/*      */ 
/* 4123 */     Vector localVector = paramTokenManager.getVocabulary();
/*      */ 
/* 4126 */     println("EOF_ = 1,");
/*      */ 
/* 4131 */     for (int i = 4; i < localVector.size(); i++) {
/* 4132 */       String str1 = (String)localVector.elementAt(i);
/* 4133 */       if (str1 != null) {
/* 4134 */         if (str1.startsWith("\""))
/*      */         {
/* 4136 */           StringLiteralSymbol localStringLiteralSymbol = (StringLiteralSymbol)paramTokenManager.getTokenSymbol(str1);
/* 4137 */           if (localStringLiteralSymbol == null) {
/* 4138 */             this.antlrTool.panic("String literal " + str1 + " not in symbol table");
/*      */           }
/* 4140 */           else if (localStringLiteralSymbol.label != null) {
/* 4141 */             println(localStringLiteralSymbol.label + " = " + i + ",");
/*      */           }
/*      */           else {
/* 4144 */             String str2 = mangleLiteral(str1);
/* 4145 */             if (str2 != null)
/*      */             {
/* 4147 */               println(str2 + " = " + i + ",");
/*      */ 
/* 4149 */               localStringLiteralSymbol.label = str2;
/*      */             }
/*      */             else {
/* 4152 */               println("// " + str1 + " = " + i);
/*      */             }
/*      */           }
/*      */         }
/* 4156 */         else if (!str1.startsWith("<")) {
/* 4157 */           println(str1 + " = " + i + ",");
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 4163 */     println("NULL_TREE_LOOKAHEAD = 3");
/*      */ 
/* 4166 */     this.tabs -= 1;
/* 4167 */     println("};");
/*      */ 
/* 4170 */     this.tabs -= 1;
/* 4171 */     println("#ifdef __cplusplus");
/* 4172 */     println("};");
/* 4173 */     println("#endif");
/*      */ 
/* 4175 */     if (nameSpace != null) {
/* 4176 */       nameSpace.emitClosures(this.currentOutput);
/*      */     }
/*      */ 
/* 4179 */     println("#endif /*INC_" + paramTokenManager.getName() + TokenTypesFileSuffix + "_hpp_*/");
/*      */ 
/* 4182 */     this.currentOutput.close();
/* 4183 */     this.currentOutput = null;
/* 4184 */     exitIfError();
/*      */   }
/*      */ 
/*      */   public String processStringForASTConstructor(String paramString)
/*      */   {
/* 4194 */     if ((this.usingCustomAST) && (((this.grammar instanceof TreeWalkerGrammar)) || ((this.grammar instanceof ParserGrammar))) && (!this.grammar.tokenManager.tokenDefined(paramString)))
/*      */     {
/* 4200 */       return namespaceAntlr + "RefAST(" + paramString + ")";
/*      */     }
/*      */ 
/* 4205 */     return paramString;
/*      */   }
/*      */ 
/*      */   public String getASTCreateString(Vector paramVector)
/*      */   {
/* 4213 */     if (paramVector.size() == 0) {
/* 4214 */       return "";
/*      */     }
/* 4216 */     StringBuffer localStringBuffer = new StringBuffer();
/*      */ 
/* 4219 */     localStringBuffer.append(this.labeledElementASTType + "(astFactory->make((new " + namespaceAntlr + "ASTArray(" + paramVector.size() + "))");
/*      */ 
/* 4222 */     for (int i = 0; i < paramVector.size(); i++) {
/* 4223 */       localStringBuffer.append("->add(" + paramVector.elementAt(i) + ")");
/*      */     }
/* 4225 */     localStringBuffer.append("))");
/* 4226 */     return localStringBuffer.toString();
/*      */   }
/*      */ 
/*      */   public String getASTCreateString(GrammarAtom paramGrammarAtom, String paramString)
/*      */   {
/* 4232 */     if ((paramGrammarAtom != null) && (paramGrammarAtom.getASTNodeType() != null))
/*      */     {
/* 4238 */       this.astTypes.ensureCapacity(paramGrammarAtom.getType());
/* 4239 */       String str = (String)this.astTypes.elementAt(paramGrammarAtom.getType());
/* 4240 */       if (str == null) {
/* 4241 */         this.astTypes.setElementAt(paramGrammarAtom.getASTNodeType(), paramGrammarAtom.getType());
/*      */       }
/* 4245 */       else if (!paramGrammarAtom.getASTNodeType().equals(str))
/*      */       {
/* 4247 */         this.antlrTool.warning("Attempt to redefine AST type for " + paramGrammarAtom.getText(), this.grammar.getFilename(), paramGrammarAtom.getLine(), paramGrammarAtom.getColumn());
/* 4248 */         this.antlrTool.warning(" from \"" + str + "\" to \"" + paramGrammarAtom.getASTNodeType() + "\" sticking to \"" + str + "\"", this.grammar.getFilename(), paramGrammarAtom.getLine(), paramGrammarAtom.getColumn());
/*      */       }
/*      */       else {
/* 4251 */         this.astTypes.setElementAt(paramGrammarAtom.getASTNodeType(), paramGrammarAtom.getType());
/*      */       }
/*      */ 
/* 4254 */       return "astFactory->create(" + paramString + ")";
/*      */     }
/*      */ 
/* 4262 */     boolean bool = false;
/* 4263 */     if (paramString.indexOf(',') != -1) {
/* 4264 */       bool = this.grammar.tokenManager.tokenDefined(paramString.substring(0, paramString.indexOf(',')));
/*      */     }
/*      */ 
/* 4267 */     if ((this.usingCustomAST) && ((this.grammar instanceof TreeWalkerGrammar)) && (!this.grammar.tokenManager.tokenDefined(paramString)) && (!bool))
/*      */     {
/* 4271 */       return "astFactory->create(" + namespaceAntlr + "RefAST(" + paramString + "))";
/*      */     }
/* 4273 */     return "astFactory->create(" + paramString + ")";
/*      */   }
/*      */ 
/*      */   public String getASTCreateString(String paramString)
/*      */   {
/* 4282 */     if (this.usingCustomAST) {
/* 4283 */       return this.labeledElementASTType + "(astFactory->create(" + namespaceAntlr + "RefAST(" + paramString + ")))";
/*      */     }
/* 4285 */     return "astFactory->create(" + paramString + ")";
/*      */   }
/*      */ 
/*      */   protected String getLookaheadTestExpression(Lookahead[] paramArrayOfLookahead, int paramInt) {
/* 4289 */     StringBuffer localStringBuffer = new StringBuffer(100);
/* 4290 */     int i = 1;
/*      */ 
/* 4292 */     localStringBuffer.append("(");
/* 4293 */     for (int j = 1; j <= paramInt; j++) {
/* 4294 */       BitSet localBitSet = paramArrayOfLookahead[j].fset;
/* 4295 */       if (i == 0) {
/* 4296 */         localStringBuffer.append(") && (");
/*      */       }
/* 4298 */       i = 0;
/*      */ 
/* 4303 */       if (paramArrayOfLookahead[j].containsEpsilon())
/* 4304 */         localStringBuffer.append("true");
/*      */       else {
/* 4306 */         localStringBuffer.append(getLookaheadTestTerm(j, localBitSet));
/*      */       }
/*      */     }
/* 4309 */     localStringBuffer.append(")");
/*      */ 
/* 4311 */     return localStringBuffer.toString();
/*      */   }
/*      */ 
/*      */   protected String getLookaheadTestExpression(Alternative paramAlternative, int paramInt)
/*      */   {
/* 4318 */     int i = paramAlternative.lookaheadDepth;
/* 4319 */     if (i == 2147483647)
/*      */     {
/* 4322 */       i = this.grammar.maxk;
/*      */     }
/*      */ 
/* 4325 */     if (paramInt == 0)
/*      */     {
/* 4328 */       return "true";
/*      */     }
/*      */ 
/* 4354 */     return "(" + getLookaheadTestExpression(paramAlternative.cache, i) + ")";
/*      */   }
/*      */ 
/*      */   protected String getLookaheadTestTerm(int paramInt, BitSet paramBitSet)
/*      */   {
/* 4366 */     String str1 = lookaheadString(paramInt);
/*      */ 
/* 4369 */     int[] arrayOfInt = paramBitSet.toArray();
/* 4370 */     if (elementsAreRange(arrayOfInt)) {
/* 4371 */       return getRangeExpression(paramInt, arrayOfInt);
/*      */     }
/*      */ 
/* 4376 */     int i = paramBitSet.degree();
/* 4377 */     if (i == 0) {
/* 4378 */       return "true";
/*      */     }
/*      */ 
/* 4381 */     if (i >= this.bitsetTestThreshold) {
/* 4382 */       j = markBitsetForGen(paramBitSet);
/* 4383 */       return getBitsetName(j) + ".member(" + str1 + ")";
/*      */     }
/*      */ 
/* 4387 */     StringBuffer localStringBuffer = new StringBuffer();
/* 4388 */     for (int j = 0; j < arrayOfInt.length; j++)
/*      */     {
/* 4390 */       String str2 = getValueString(arrayOfInt[j]);
/*      */ 
/* 4393 */       if (j > 0) localStringBuffer.append(" || ");
/* 4394 */       localStringBuffer.append(str1);
/* 4395 */       localStringBuffer.append(" == ");
/* 4396 */       localStringBuffer.append(str2);
/*      */     }
/* 4398 */     return localStringBuffer.toString();
/*      */   }
/*      */ 
/*      */   public String getRangeExpression(int paramInt, int[] paramArrayOfInt)
/*      */   {
/* 4406 */     if (!elementsAreRange(paramArrayOfInt)) {
/* 4407 */       this.antlrTool.panic("getRangeExpression called with non-range");
/*      */     }
/* 4409 */     int i = paramArrayOfInt[0];
/* 4410 */     int j = paramArrayOfInt[(paramArrayOfInt.length - 1)];
/* 4411 */     return "(" + lookaheadString(paramInt) + " >= " + getValueString(i) + " && " + lookaheadString(paramInt) + " <= " + getValueString(j) + ")";
/*      */   }
/*      */ 
/*      */   private String getValueString(int paramInt)
/*      */   {
/*      */     Object localObject;
/* 4420 */     if ((this.grammar instanceof LexerGrammar)) {
/* 4421 */       localObject = this.charFormatter.literalChar(paramInt);
/*      */     }
/*      */     else
/*      */     {
/* 4425 */       TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbolAt(paramInt);
/* 4426 */       if (localTokenSymbol == null) {
/* 4427 */         return "" + paramInt;
/*      */       }
/*      */ 
/* 4430 */       String str1 = localTokenSymbol.getId();
/* 4431 */       if ((localTokenSymbol instanceof StringLiteralSymbol))
/*      */       {
/* 4435 */         StringLiteralSymbol localStringLiteralSymbol = (StringLiteralSymbol)localTokenSymbol;
/* 4436 */         String str2 = localStringLiteralSymbol.getLabel();
/* 4437 */         if (str2 != null) {
/* 4438 */           localObject = str2;
/*      */         }
/*      */         else {
/* 4441 */           localObject = mangleLiteral(str1);
/* 4442 */           if (localObject == null) {
/* 4443 */             localObject = String.valueOf(paramInt);
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/* 4448 */       else if (str1.equals("EOF")) {
/* 4449 */         localObject = namespaceAntlr + "Token::EOF_TYPE";
/*      */       } else {
/* 4451 */         localObject = str1;
/*      */       }
/*      */     }
/* 4454 */     return localObject;
/*      */   }
/*      */ 
/*      */   protected boolean lookaheadIsEmpty(Alternative paramAlternative, int paramInt) {
/* 4458 */     int i = paramAlternative.lookaheadDepth;
/* 4459 */     if (i == 2147483647) {
/* 4460 */       i = this.grammar.maxk;
/*      */     }
/* 4462 */     for (int j = 1; (j <= i) && (j <= paramInt); j++) {
/* 4463 */       BitSet localBitSet = paramAlternative.cache[j].fset;
/* 4464 */       if (localBitSet.degree() != 0) {
/* 4465 */         return false;
/*      */       }
/*      */     }
/* 4468 */     return true;
/*      */   }
/*      */   private String lookaheadString(int paramInt) {
/* 4471 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 4472 */       return "_t->getType()";
/*      */     }
/* 4474 */     return "LA(" + paramInt + ")";
/*      */   }
/*      */ 
/*      */   private String mangleLiteral(String paramString)
/*      */   {
/* 4483 */     String str = this.antlrTool.literalsPrefix;
/* 4484 */     for (int i = 1; i < paramString.length() - 1; i++) {
/* 4485 */       if ((!Character.isLetter(paramString.charAt(i))) && (paramString.charAt(i) != '_'))
/*      */       {
/* 4487 */         return null;
/*      */       }
/* 4489 */       str = str + paramString.charAt(i);
/*      */     }
/* 4491 */     if (this.antlrTool.upperCaseMangledLiterals) {
/* 4492 */       str = str.toUpperCase();
/*      */     }
/* 4494 */     return str;
/*      */   }
/*      */ 
/*      */   public String mapTreeId(String paramString, ActionTransInfo paramActionTransInfo)
/*      */   {
/* 4504 */     if (this.currentRule == null) return paramString;
/*      */ 
/* 4507 */     int i = 0;
/* 4508 */     String str1 = paramString;
/* 4509 */     if ((this.grammar instanceof TreeWalkerGrammar))
/*      */     {
/* 4514 */       if (!this.grammar.buildAST)
/*      */       {
/* 4516 */         i = 1;
/*      */       }
/*      */ 
/* 4521 */       if ((str1.length() > 3) && (str1.lastIndexOf("_in") == str1.length() - 3))
/*      */       {
/* 4524 */         str1 = str1.substring(0, str1.length() - 3);
/* 4525 */         i = 1;
/*      */       }
/*      */     }
/*      */     Object localObject;
/* 4533 */     for (int j = 0; j < this.currentRule.labeledElements.size(); j++)
/*      */     {
/* 4535 */       localObject = (AlternativeElement)this.currentRule.labeledElements.elementAt(j);
/* 4536 */       if (((AlternativeElement)localObject).getLabel().equals(str1))
/*      */       {
/* 4540 */         return str1 + "_AST";
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 4547 */     String str2 = (String)this.treeVariableMap.get(str1);
/* 4548 */     if (str2 != null)
/*      */     {
/* 4550 */       if (str2 == NONUNIQUE)
/*      */       {
/* 4555 */         this.antlrTool.error("Ambiguous reference to AST element " + str1 + " in rule " + this.currentRule.getRuleName());
/*      */ 
/* 4557 */         return null;
/*      */       }
/* 4559 */       if (str2.equals(this.currentRule.getRuleName()))
/*      */       {
/* 4565 */         this.antlrTool.error("Ambiguous reference to AST element " + str1 + " in rule " + this.currentRule.getRuleName());
/*      */ 
/* 4567 */         return null;
/*      */       }
/*      */ 
/* 4573 */       return i != 0 ? str2 + "_in" : str2;
/*      */     }
/*      */ 
/* 4580 */     if (str1.equals(this.currentRule.getRuleName()))
/*      */     {
/* 4582 */       localObject = str1 + "_AST";
/* 4583 */       if ((paramActionTransInfo != null) && 
/* 4584 */         (i == 0)) {
/* 4585 */         paramActionTransInfo.refRuleRoot = ((String)localObject);
/*      */       }
/*      */ 
/* 4590 */       return localObject;
/*      */     }
/*      */ 
/* 4597 */     return str1;
/*      */   }
/*      */ 
/*      */   private void mapTreeVariable(AlternativeElement paramAlternativeElement, String paramString)
/*      */   {
/* 4606 */     if ((paramAlternativeElement instanceof TreeElement)) {
/* 4607 */       mapTreeVariable(((TreeElement)paramAlternativeElement).root, paramString);
/* 4608 */       return;
/*      */     }
/*      */ 
/* 4612 */     String str = null;
/*      */ 
/* 4615 */     if (paramAlternativeElement.getLabel() == null) {
/* 4616 */       if ((paramAlternativeElement instanceof TokenRefElement))
/*      */       {
/* 4618 */         str = ((TokenRefElement)paramAlternativeElement).atomText;
/*      */       }
/* 4620 */       else if ((paramAlternativeElement instanceof RuleRefElement))
/*      */       {
/* 4622 */         str = ((RuleRefElement)paramAlternativeElement).targetRule;
/*      */       }
/*      */     }
/*      */ 
/* 4626 */     if (str != null)
/* 4627 */       if (this.treeVariableMap.get(str) != null)
/*      */       {
/* 4629 */         this.treeVariableMap.remove(str);
/* 4630 */         this.treeVariableMap.put(str, NONUNIQUE);
/*      */       }
/*      */       else {
/* 4633 */         this.treeVariableMap.put(str, paramString);
/*      */       }
/*      */   }
/*      */ 
/*      */   protected String processActionForSpecialSymbols(String paramString, int paramInt, RuleBlock paramRuleBlock, ActionTransInfo paramActionTransInfo)
/*      */   {
/* 4647 */     if ((paramString == null) || (paramString.length() == 0)) {
/* 4648 */       return null;
/*      */     }
/*      */ 
/* 4652 */     if (this.grammar == null) {
/* 4653 */       return paramString;
/*      */     }
/* 4655 */     if (((this.grammar.buildAST) && (paramString.indexOf('#') != -1)) || ((this.grammar instanceof TreeWalkerGrammar)) || ((((this.grammar instanceof LexerGrammar)) || ((this.grammar instanceof ParserGrammar))) && (paramString.indexOf('$') != -1)))
/*      */     {
/* 4662 */       ActionLexer localActionLexer = new ActionLexer(paramString, paramRuleBlock, this, paramActionTransInfo);
/*      */ 
/* 4664 */       localActionLexer.setLineOffset(paramInt);
/* 4665 */       localActionLexer.setFilename(this.grammar.getFilename());
/* 4666 */       localActionLexer.setTool(this.antlrTool);
/*      */       try
/*      */       {
/* 4669 */         localActionLexer.mACTION(true);
/* 4670 */         paramString = localActionLexer.getTokenObject().getText();
/*      */       }
/*      */       catch (RecognitionException localRecognitionException)
/*      */       {
/* 4675 */         localActionLexer.reportError(localRecognitionException);
/* 4676 */         return paramString;
/*      */       }
/*      */       catch (TokenStreamException localTokenStreamException) {
/* 4679 */         this.antlrTool.panic("Error reading action:" + paramString);
/* 4680 */         return paramString;
/*      */       }
/*      */       catch (CharStreamException localCharStreamException) {
/* 4683 */         this.antlrTool.panic("Error reading action:" + paramString);
/* 4684 */         return paramString;
/*      */       }
/*      */     }
/* 4687 */     return paramString;
/*      */   }
/*      */ 
/*      */   private String fixNameSpaceOption(String paramString)
/*      */   {
/* 4692 */     paramString = StringUtils.stripFrontBack(paramString, "\"", "\"");
/* 4693 */     if ((paramString.length() > 2) && (!paramString.substring(paramString.length() - 2, paramString.length()).equals("::")))
/*      */     {
/* 4695 */       paramString = paramString + "::";
/* 4696 */     }return paramString;
/*      */   }
/*      */ 
/*      */   private void setupGrammarParameters(Grammar paramGrammar)
/*      */   {
/*      */     Token localToken;
/*      */     String str;
/* 4700 */     if (((paramGrammar instanceof ParserGrammar)) || ((paramGrammar instanceof LexerGrammar)) || ((paramGrammar instanceof TreeWalkerGrammar)))
/*      */     {
/* 4710 */       if (this.antlrTool.nameSpace != null) {
/* 4711 */         nameSpace = this.antlrTool.nameSpace;
/*      */       }
/* 4713 */       if (this.antlrTool.namespaceStd != null) {
/* 4714 */         namespaceStd = fixNameSpaceOption(this.antlrTool.namespaceStd);
/*      */       }
/* 4716 */       if (this.antlrTool.namespaceAntlr != null) {
/* 4717 */         namespaceAntlr = fixNameSpaceOption(this.antlrTool.namespaceAntlr);
/*      */       }
/* 4719 */       this.genHashLines = this.antlrTool.genHashLines;
/*      */ 
/* 4723 */       if (paramGrammar.hasOption("namespace")) {
/* 4724 */         localToken = paramGrammar.getOption("namespace");
/* 4725 */         if (localToken != null) {
/* 4726 */           nameSpace = new NameSpace(localToken.getText());
/*      */         }
/*      */       }
/* 4729 */       if (paramGrammar.hasOption("namespaceAntlr")) {
/* 4730 */         localToken = paramGrammar.getOption("namespaceAntlr");
/* 4731 */         if (localToken != null) {
/* 4732 */           str = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 4733 */           if (str != null) {
/* 4734 */             if ((str.length() > 2) && (!str.substring(str.length() - 2, str.length()).equals("::")))
/*      */             {
/* 4736 */               str = str + "::";
/* 4737 */             }namespaceAntlr = str;
/*      */           }
/*      */         }
/*      */       }
/* 4741 */       if (paramGrammar.hasOption("namespaceStd")) {
/* 4742 */         localToken = paramGrammar.getOption("namespaceStd");
/* 4743 */         if (localToken != null) {
/* 4744 */           str = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 4745 */           if (str != null) {
/* 4746 */             if ((str.length() > 2) && (!str.substring(str.length() - 2, str.length()).equals("::")))
/*      */             {
/* 4748 */               str = str + "::";
/* 4749 */             }namespaceStd = str;
/*      */           }
/*      */         }
/*      */       }
/* 4753 */       if (paramGrammar.hasOption("genHashLines")) {
/* 4754 */         localToken = paramGrammar.getOption("genHashLines");
/* 4755 */         if (localToken != null) {
/* 4756 */           str = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 4757 */           this.genHashLines = str.equals("true");
/*      */         }
/*      */       }
/* 4760 */       this.noConstructors = this.antlrTool.noConstructors;
/* 4761 */       if (paramGrammar.hasOption("noConstructors")) {
/* 4762 */         localToken = paramGrammar.getOption("noConstructors");
/* 4763 */         if ((localToken != null) && (!localToken.getText().equals("true")) && (!localToken.getText().equals("false")))
/* 4764 */           this.antlrTool.error("noConstructors option must be true or false", this.antlrTool.getGrammarFile(), localToken.getLine(), localToken.getColumn());
/* 4765 */         this.noConstructors = localToken.getText().equals("true");
/*      */       }
/*      */     }
/* 4768 */     if ((paramGrammar instanceof ParserGrammar)) {
/* 4769 */       this.labeledElementASTType = (namespaceAntlr + "RefAST");
/* 4770 */       this.labeledElementASTInit = (namespaceAntlr + "nullAST");
/* 4771 */       if (paramGrammar.hasOption("ASTLabelType")) {
/* 4772 */         localToken = paramGrammar.getOption("ASTLabelType");
/* 4773 */         if (localToken != null) {
/* 4774 */           str = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 4775 */           if (str != null) {
/* 4776 */             this.usingCustomAST = true;
/* 4777 */             this.labeledElementASTType = str;
/* 4778 */             this.labeledElementASTInit = (str + "(" + namespaceAntlr + "nullAST)");
/*      */           }
/*      */         }
/*      */       }
/* 4782 */       this.labeledElementType = (namespaceAntlr + "RefToken ");
/* 4783 */       this.labeledElementInit = (namespaceAntlr + "nullToken");
/* 4784 */       this.commonExtraArgs = "";
/* 4785 */       this.commonExtraParams = "";
/* 4786 */       this.commonLocalVars = "";
/* 4787 */       this.lt1Value = "LT(1)";
/* 4788 */       this.exceptionThrown = (namespaceAntlr + "RecognitionException");
/* 4789 */       this.throwNoViable = ("throw " + namespaceAntlr + "NoViableAltException(LT(1), getFilename());");
/*      */     }
/* 4791 */     else if ((paramGrammar instanceof LexerGrammar)) {
/* 4792 */       this.labeledElementType = "char ";
/* 4793 */       this.labeledElementInit = "'\\0'";
/* 4794 */       this.commonExtraArgs = "";
/* 4795 */       this.commonExtraParams = "bool _createToken";
/* 4796 */       this.commonLocalVars = ("int _ttype; " + namespaceAntlr + "RefToken _token; " + namespaceStd + "string::size_type _begin = text.length();");
/* 4797 */       this.lt1Value = "LA(1)";
/* 4798 */       this.exceptionThrown = (namespaceAntlr + "RecognitionException");
/* 4799 */       this.throwNoViable = ("throw " + namespaceAntlr + "NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());");
/*      */     }
/* 4801 */     else if ((paramGrammar instanceof TreeWalkerGrammar)) {
/* 4802 */       this.labeledElementInit = (namespaceAntlr + "nullAST");
/* 4803 */       this.labeledElementASTInit = (namespaceAntlr + "nullAST");
/* 4804 */       this.labeledElementASTType = (namespaceAntlr + "RefAST");
/* 4805 */       this.labeledElementType = (namespaceAntlr + "RefAST");
/* 4806 */       this.commonExtraParams = (namespaceAntlr + "RefAST _t");
/* 4807 */       this.throwNoViable = ("throw " + namespaceAntlr + "NoViableAltException(_t);");
/* 4808 */       this.lt1Value = "_t";
/* 4809 */       if (paramGrammar.hasOption("ASTLabelType")) {
/* 4810 */         localToken = paramGrammar.getOption("ASTLabelType");
/* 4811 */         if (localToken != null) {
/* 4812 */           str = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 4813 */           if (str != null) {
/* 4814 */             this.usingCustomAST = true;
/* 4815 */             this.labeledElementASTType = str;
/* 4816 */             this.labeledElementType = str;
/* 4817 */             this.labeledElementInit = (str + "(" + namespaceAntlr + "nullAST)");
/* 4818 */             this.labeledElementASTInit = this.labeledElementInit;
/* 4819 */             this.commonExtraParams = (str + " _t");
/* 4820 */             this.throwNoViable = ("throw " + namespaceAntlr + "NoViableAltException(" + namespaceAntlr + "RefAST(_t));");
/* 4821 */             this.lt1Value = "_t";
/*      */           }
/*      */         }
/*      */       }
/* 4825 */       if (!paramGrammar.hasOption("ASTLabelType")) {
/* 4826 */         paramGrammar.setOption("ASTLabelType", new Token(6, namespaceAntlr + "RefAST"));
/*      */       }
/* 4828 */       this.commonExtraArgs = "_t";
/* 4829 */       this.commonLocalVars = "";
/* 4830 */       this.exceptionThrown = (namespaceAntlr + "RecognitionException");
/*      */     }
/*      */     else {
/* 4833 */       this.antlrTool.panic("Unknown grammar type");
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.CppCodeGenerator
 * JD-Core Version:    0.6.2
 */