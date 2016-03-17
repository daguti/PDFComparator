/*     */ package antlr;
/*     */ 
/*     */ import antlr.collections.impl.BitSet;
/*     */ import antlr.collections.impl.Vector;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ public class DiagnosticCodeGenerator extends CodeGenerator
/*     */ {
/*  22 */   protected int syntacticPredLevel = 0;
/*     */ 
/*  25 */   protected boolean doingLexRules = false;
/*     */ 
/*     */   public DiagnosticCodeGenerator()
/*     */   {
/*  33 */     this.charFormatter = new JavaCharFormatter();
/*     */   }
/*     */ 
/*     */   public void gen()
/*     */   {
/*     */     try
/*     */     {
/*  42 */       Enumeration localEnumeration = this.behavior.grammars.elements();
/*  43 */       while (localEnumeration.hasMoreElements()) {
/*  44 */         localObject = (Grammar)localEnumeration.nextElement();
/*     */ 
/*  47 */         ((Grammar)localObject).setGrammarAnalyzer(this.analyzer);
/*  48 */         ((Grammar)localObject).setCodeGenerator(this);
/*  49 */         this.analyzer.setGrammar((Grammar)localObject);
/*     */ 
/*  52 */         ((Grammar)localObject).generate();
/*     */ 
/*  54 */         if (this.antlrTool.hasError()) {
/*  55 */           this.antlrTool.panic("Exiting due to errors.");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*  61 */       Object localObject = this.behavior.tokenManagers.elements();
/*  62 */       while (((Enumeration)localObject).hasMoreElements()) {
/*  63 */         TokenManager localTokenManager = (TokenManager)((Enumeration)localObject).nextElement();
/*  64 */         if (!localTokenManager.isReadOnly())
/*     */         {
/*  66 */           genTokenTypes(localTokenManager);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException) {
/*  71 */       this.antlrTool.reportException(localIOException, null);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void gen(ActionElement paramActionElement)
/*     */   {
/*  79 */     if (!paramActionElement.isSemPred)
/*     */     {
/*  83 */       print("ACTION: ");
/*  84 */       _printAction(paramActionElement.actionText);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void gen(AlternativeBlock paramAlternativeBlock)
/*     */   {
/*  92 */     println("Start of alternative block.");
/*  93 */     this.tabs += 1;
/*  94 */     genBlockPreamble(paramAlternativeBlock);
/*     */ 
/*  96 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramAlternativeBlock);
/*  97 */     if (!bool) {
/*  98 */       println("Warning: This alternative block is non-deterministic");
/*     */     }
/* 100 */     genCommonBlock(paramAlternativeBlock);
/* 101 */     this.tabs -= 1;
/*     */   }
/*     */ 
/*     */   public void gen(BlockEndElement paramBlockEndElement)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void gen(CharLiteralElement paramCharLiteralElement)
/*     */   {
/* 117 */     print("Match character ");
/* 118 */     if (paramCharLiteralElement.not) {
/* 119 */       _print("NOT ");
/*     */     }
/* 121 */     _print(paramCharLiteralElement.atomText);
/* 122 */     if (paramCharLiteralElement.label != null) {
/* 123 */       _print(", label=" + paramCharLiteralElement.label);
/*     */     }
/* 125 */     _println("");
/*     */   }
/*     */ 
/*     */   public void gen(CharRangeElement paramCharRangeElement)
/*     */   {
/* 132 */     print("Match character range: " + paramCharRangeElement.beginText + ".." + paramCharRangeElement.endText);
/* 133 */     if (paramCharRangeElement.label != null) {
/* 134 */       _print(", label = " + paramCharRangeElement.label);
/*     */     }
/* 136 */     _println("");
/*     */   }
/*     */ 
/*     */   public void gen(LexerGrammar paramLexerGrammar) throws IOException
/*     */   {
/* 141 */     setGrammar(paramLexerGrammar);
/* 142 */     this.antlrTool.reportProgress("Generating " + this.grammar.getClassName() + TokenTypesFileExt);
/* 143 */     this.currentOutput = this.antlrTool.openOutputFile(this.grammar.getClassName() + TokenTypesFileExt);
/*     */ 
/* 146 */     this.tabs = 0;
/* 147 */     this.doingLexRules = true;
/*     */ 
/* 150 */     genHeader();
/*     */ 
/* 153 */     println("");
/* 154 */     println("*** Lexer Preamble Action.");
/* 155 */     println("This action will appear before the declaration of your lexer class:");
/* 156 */     this.tabs += 1;
/* 157 */     println(this.grammar.preambleAction.getText());
/* 158 */     this.tabs -= 1;
/* 159 */     println("*** End of Lexer Preamble Action");
/*     */ 
/* 162 */     println("");
/* 163 */     println("*** Your lexer class is called '" + this.grammar.getClassName() + "' and is a subclass of '" + this.grammar.getSuperClass() + "'.");
/*     */ 
/* 166 */     println("");
/* 167 */     println("*** User-defined lexer  class members:");
/* 168 */     println("These are the member declarations that you defined for your class:");
/* 169 */     this.tabs += 1;
/* 170 */     printAction(this.grammar.classMemberAction.getText());
/* 171 */     this.tabs -= 1;
/* 172 */     println("*** End of user-defined lexer class members");
/*     */ 
/* 175 */     println("");
/* 176 */     println("*** String literals used in the parser");
/* 177 */     println("The following string literals were used in the parser.");
/* 178 */     println("An actual code generator would arrange to place these literals");
/* 179 */     println("into a table in the generated lexer, so that actions in the");
/* 180 */     println("generated lexer could match token text against the literals.");
/* 181 */     println("String literals used in the lexer are not listed here, as they");
/* 182 */     println("are incorporated into the mainstream lexer processing.");
/* 183 */     this.tabs += 1;
/*     */ 
/* 185 */     Enumeration localEnumeration = this.grammar.getSymbols();
/*     */     Object localObject;
/* 186 */     while (localEnumeration.hasMoreElements()) {
/* 187 */       localObject = (GrammarSymbol)localEnumeration.nextElement();
/*     */ 
/* 189 */       if ((localObject instanceof StringLiteralSymbol)) {
/* 190 */         StringLiteralSymbol localStringLiteralSymbol = (StringLiteralSymbol)localObject;
/* 191 */         println(localStringLiteralSymbol.getId() + " = " + localStringLiteralSymbol.getTokenType());
/*     */       }
/*     */     }
/* 194 */     this.tabs -= 1;
/* 195 */     println("*** End of string literals used by the parser");
/*     */ 
/* 200 */     genNextToken();
/*     */ 
/* 203 */     println("");
/* 204 */     println("*** User-defined Lexer rules:");
/* 205 */     this.tabs += 1;
/*     */ 
/* 207 */     localEnumeration = this.grammar.rules.elements();
/* 208 */     while (localEnumeration.hasMoreElements()) {
/* 209 */       localObject = (RuleSymbol)localEnumeration.nextElement();
/* 210 */       if (!((RuleSymbol)localObject).id.equals("mnextToken")) {
/* 211 */         genRule((RuleSymbol)localObject);
/*     */       }
/*     */     }
/*     */ 
/* 215 */     this.tabs -= 1;
/* 216 */     println("");
/* 217 */     println("*** End User-defined Lexer rules:");
/*     */ 
/* 220 */     this.currentOutput.close();
/* 221 */     this.currentOutput = null;
/* 222 */     this.doingLexRules = false;
/*     */   }
/*     */ 
/*     */   public void gen(OneOrMoreBlock paramOneOrMoreBlock)
/*     */   {
/* 229 */     println("Start ONE-OR-MORE (...)+ block:");
/* 230 */     this.tabs += 1;
/* 231 */     genBlockPreamble(paramOneOrMoreBlock);
/* 232 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramOneOrMoreBlock);
/* 233 */     if (!bool) {
/* 234 */       println("Warning: This one-or-more block is non-deterministic");
/*     */     }
/* 236 */     genCommonBlock(paramOneOrMoreBlock);
/* 237 */     this.tabs -= 1;
/* 238 */     println("End ONE-OR-MORE block.");
/*     */   }
/*     */ 
/*     */   public void gen(ParserGrammar paramParserGrammar) throws IOException
/*     */   {
/* 243 */     setGrammar(paramParserGrammar);
/*     */ 
/* 245 */     this.antlrTool.reportProgress("Generating " + this.grammar.getClassName() + TokenTypesFileExt);
/* 246 */     this.currentOutput = this.antlrTool.openOutputFile(this.grammar.getClassName() + TokenTypesFileExt);
/*     */ 
/* 249 */     this.tabs = 0;
/*     */ 
/* 252 */     genHeader();
/*     */ 
/* 255 */     println("");
/* 256 */     println("*** Parser Preamble Action.");
/* 257 */     println("This action will appear before the declaration of your parser class:");
/* 258 */     this.tabs += 1;
/* 259 */     println(this.grammar.preambleAction.getText());
/* 260 */     this.tabs -= 1;
/* 261 */     println("*** End of Parser Preamble Action");
/*     */ 
/* 264 */     println("");
/* 265 */     println("*** Your parser class is called '" + this.grammar.getClassName() + "' and is a subclass of '" + this.grammar.getSuperClass() + "'.");
/*     */ 
/* 268 */     println("");
/* 269 */     println("*** User-defined parser class members:");
/* 270 */     println("These are the member declarations that you defined for your class:");
/* 271 */     this.tabs += 1;
/* 272 */     printAction(this.grammar.classMemberAction.getText());
/* 273 */     this.tabs -= 1;
/* 274 */     println("*** End of user-defined parser class members");
/*     */ 
/* 277 */     println("");
/* 278 */     println("*** Parser rules:");
/* 279 */     this.tabs += 1;
/*     */ 
/* 282 */     Enumeration localEnumeration = this.grammar.rules.elements();
/* 283 */     while (localEnumeration.hasMoreElements()) {
/* 284 */       println("");
/*     */ 
/* 286 */       GrammarSymbol localGrammarSymbol = (GrammarSymbol)localEnumeration.nextElement();
/*     */ 
/* 288 */       if ((localGrammarSymbol instanceof RuleSymbol)) {
/* 289 */         genRule((RuleSymbol)localGrammarSymbol);
/*     */       }
/*     */     }
/* 292 */     this.tabs -= 1;
/* 293 */     println("");
/* 294 */     println("*** End of parser rules");
/*     */ 
/* 296 */     println("");
/* 297 */     println("*** End of parser");
/*     */ 
/* 300 */     this.currentOutput.close();
/* 301 */     this.currentOutput = null;
/*     */   }
/*     */ 
/*     */   public void gen(RuleRefElement paramRuleRefElement)
/*     */   {
/* 308 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(paramRuleRefElement.targetRule);
/*     */ 
/* 311 */     print("Rule Reference: " + paramRuleRefElement.targetRule);
/* 312 */     if (paramRuleRefElement.idAssign != null) {
/* 313 */       _print(", assigned to '" + paramRuleRefElement.idAssign + "'");
/*     */     }
/* 315 */     if (paramRuleRefElement.args != null) {
/* 316 */       _print(", arguments = " + paramRuleRefElement.args);
/*     */     }
/* 318 */     _println("");
/*     */ 
/* 321 */     if ((localRuleSymbol == null) || (!localRuleSymbol.isDefined())) {
/* 322 */       println("Rule '" + paramRuleRefElement.targetRule + "' is referenced, but that rule is not defined.");
/* 323 */       println("\tPerhaps the rule is misspelled, or you forgot to define it.");
/* 324 */       return;
/*     */     }
/* 326 */     if (!(localRuleSymbol instanceof RuleSymbol))
/*     */     {
/* 328 */       println("Rule '" + paramRuleRefElement.targetRule + "' is referenced, but that is not a grammar rule.");
/* 329 */       return;
/*     */     }
/* 331 */     if (paramRuleRefElement.idAssign != null)
/*     */     {
/* 333 */       if (localRuleSymbol.block.returnAction == null) {
/* 334 */         println("Error: You assigned from Rule '" + paramRuleRefElement.targetRule + "', but that rule has no return type.");
/*     */       }
/*     */ 
/*     */     }
/* 339 */     else if ((!(this.grammar instanceof LexerGrammar)) && (this.syntacticPredLevel == 0) && (localRuleSymbol.block.returnAction != null)) {
/* 340 */       println("Warning: Rule '" + paramRuleRefElement.targetRule + "' returns a value");
/*     */     }
/*     */ 
/* 343 */     if ((paramRuleRefElement.args != null) && (localRuleSymbol.block.argAction == null))
/* 344 */       println("Error: Rule '" + paramRuleRefElement.targetRule + "' accepts no arguments.");
/*     */   }
/*     */ 
/*     */   public void gen(StringLiteralElement paramStringLiteralElement)
/*     */   {
/* 352 */     print("Match string literal ");
/* 353 */     _print(paramStringLiteralElement.atomText);
/* 354 */     if (paramStringLiteralElement.label != null) {
/* 355 */       _print(", label=" + paramStringLiteralElement.label);
/*     */     }
/* 357 */     _println("");
/*     */   }
/*     */ 
/*     */   public void gen(TokenRangeElement paramTokenRangeElement)
/*     */   {
/* 364 */     print("Match token range: " + paramTokenRangeElement.beginText + ".." + paramTokenRangeElement.endText);
/* 365 */     if (paramTokenRangeElement.label != null) {
/* 366 */       _print(", label = " + paramTokenRangeElement.label);
/*     */     }
/* 368 */     _println("");
/*     */   }
/*     */ 
/*     */   public void gen(TokenRefElement paramTokenRefElement)
/*     */   {
/* 375 */     print("Match token ");
/* 376 */     if (paramTokenRefElement.not) {
/* 377 */       _print("NOT ");
/*     */     }
/* 379 */     _print(paramTokenRefElement.atomText);
/* 380 */     if (paramTokenRefElement.label != null) {
/* 381 */       _print(", label=" + paramTokenRefElement.label);
/*     */     }
/* 383 */     _println("");
/*     */   }
/*     */ 
/*     */   public void gen(TreeElement paramTreeElement) {
/* 387 */     print("Tree reference: " + paramTreeElement);
/*     */   }
/*     */ 
/*     */   public void gen(TreeWalkerGrammar paramTreeWalkerGrammar) throws IOException
/*     */   {
/* 392 */     setGrammar(paramTreeWalkerGrammar);
/*     */ 
/* 394 */     this.antlrTool.reportProgress("Generating " + this.grammar.getClassName() + TokenTypesFileExt);
/* 395 */     this.currentOutput = this.antlrTool.openOutputFile(this.grammar.getClassName() + TokenTypesFileExt);
/*     */ 
/* 398 */     this.tabs = 0;
/*     */ 
/* 401 */     genHeader();
/*     */ 
/* 404 */     println("");
/* 405 */     println("*** Tree-walker Preamble Action.");
/* 406 */     println("This action will appear before the declaration of your tree-walker class:");
/* 407 */     this.tabs += 1;
/* 408 */     println(this.grammar.preambleAction.getText());
/* 409 */     this.tabs -= 1;
/* 410 */     println("*** End of tree-walker Preamble Action");
/*     */ 
/* 413 */     println("");
/* 414 */     println("*** Your tree-walker class is called '" + this.grammar.getClassName() + "' and is a subclass of '" + this.grammar.getSuperClass() + "'.");
/*     */ 
/* 417 */     println("");
/* 418 */     println("*** User-defined tree-walker class members:");
/* 419 */     println("These are the member declarations that you defined for your class:");
/* 420 */     this.tabs += 1;
/* 421 */     printAction(this.grammar.classMemberAction.getText());
/* 422 */     this.tabs -= 1;
/* 423 */     println("*** End of user-defined tree-walker class members");
/*     */ 
/* 426 */     println("");
/* 427 */     println("*** tree-walker rules:");
/* 428 */     this.tabs += 1;
/*     */ 
/* 431 */     Enumeration localEnumeration = this.grammar.rules.elements();
/* 432 */     while (localEnumeration.hasMoreElements()) {
/* 433 */       println("");
/*     */ 
/* 435 */       GrammarSymbol localGrammarSymbol = (GrammarSymbol)localEnumeration.nextElement();
/*     */ 
/* 437 */       if ((localGrammarSymbol instanceof RuleSymbol)) {
/* 438 */         genRule((RuleSymbol)localGrammarSymbol);
/*     */       }
/*     */     }
/* 441 */     this.tabs -= 1;
/* 442 */     println("");
/* 443 */     println("*** End of tree-walker rules");
/*     */ 
/* 445 */     println("");
/* 446 */     println("*** End of tree-walker");
/*     */ 
/* 449 */     this.currentOutput.close();
/* 450 */     this.currentOutput = null;
/*     */   }
/*     */ 
/*     */   public void gen(WildcardElement paramWildcardElement)
/*     */   {
/* 455 */     print("Match wildcard");
/* 456 */     if (paramWildcardElement.getLabel() != null) {
/* 457 */       _print(", label = " + paramWildcardElement.getLabel());
/*     */     }
/* 459 */     _println("");
/*     */   }
/*     */ 
/*     */   public void gen(ZeroOrMoreBlock paramZeroOrMoreBlock)
/*     */   {
/* 466 */     println("Start ZERO-OR-MORE (...)+ block:");
/* 467 */     this.tabs += 1;
/* 468 */     genBlockPreamble(paramZeroOrMoreBlock);
/* 469 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramZeroOrMoreBlock);
/* 470 */     if (!bool) {
/* 471 */       println("Warning: This zero-or-more block is non-deterministic");
/*     */     }
/* 473 */     genCommonBlock(paramZeroOrMoreBlock);
/* 474 */     this.tabs -= 1;
/* 475 */     println("End ZERO-OR-MORE block.");
/*     */   }
/*     */ 
/*     */   protected void genAlt(Alternative paramAlternative)
/*     */   {
/* 480 */     for (AlternativeElement localAlternativeElement = paramAlternative.head; 
/* 481 */       !(localAlternativeElement instanceof BlockEndElement); 
/* 482 */       localAlternativeElement = localAlternativeElement.next)
/*     */     {
/* 484 */       localAlternativeElement.generate();
/*     */     }
/* 486 */     if (paramAlternative.getTreeSpecifier() != null)
/* 487 */       println("AST will be built as: " + paramAlternative.getTreeSpecifier().getText());
/*     */   }
/*     */ 
/*     */   protected void genBlockPreamble(AlternativeBlock paramAlternativeBlock)
/*     */   {
/* 498 */     if (paramAlternativeBlock.initAction != null)
/* 499 */       printAction("Init action: " + paramAlternativeBlock.initAction);
/*     */   }
/*     */ 
/*     */   public void genCommonBlock(AlternativeBlock paramAlternativeBlock)
/*     */   {
/* 509 */     int i = paramAlternativeBlock.alternatives.size() == 1 ? 1 : 0;
/*     */ 
/* 511 */     println("Start of an alternative block.");
/* 512 */     this.tabs += 1;
/* 513 */     println("The lookahead set for this block is:");
/* 514 */     this.tabs += 1;
/* 515 */     genLookaheadSetForBlock(paramAlternativeBlock);
/* 516 */     this.tabs -= 1;
/*     */ 
/* 518 */     if (i != 0) {
/* 519 */       println("This block has a single alternative");
/* 520 */       if (paramAlternativeBlock.getAlternativeAt(0).synPred != null)
/*     */       {
/* 522 */         println("Warning: you specified a syntactic predicate for this alternative,");
/* 523 */         println("and it is the only alternative of a block and will be ignored.");
/*     */       }
/*     */     }
/*     */     else {
/* 527 */       println("This block has multiple alternatives:");
/* 528 */       this.tabs += 1;
/*     */     }
/*     */ 
/* 531 */     for (int j = 0; j < paramAlternativeBlock.alternatives.size(); j++) {
/* 532 */       Alternative localAlternative = paramAlternativeBlock.getAlternativeAt(j);
/* 533 */       AlternativeElement localAlternativeElement = localAlternative.head;
/*     */ 
/* 536 */       println("");
/* 537 */       if (j != 0) {
/* 538 */         print("Otherwise, ");
/*     */       }
/*     */       else {
/* 541 */         print("");
/*     */       }
/* 543 */       _println("Alternate(" + (j + 1) + ") will be taken IF:");
/* 544 */       println("The lookahead set: ");
/* 545 */       this.tabs += 1;
/* 546 */       genLookaheadSetForAlt(localAlternative);
/* 547 */       this.tabs -= 1;
/* 548 */       if ((localAlternative.semPred != null) || (localAlternative.synPred != null)) {
/* 549 */         print("is matched, AND ");
/*     */       }
/*     */       else {
/* 552 */         println("is matched.");
/*     */       }
/*     */ 
/* 556 */       if (localAlternative.semPred != null) {
/* 557 */         _println("the semantic predicate:");
/* 558 */         this.tabs += 1;
/* 559 */         println(localAlternative.semPred);
/* 560 */         if (localAlternative.synPred != null) {
/* 561 */           print("is true, AND ");
/*     */         }
/*     */         else {
/* 564 */           println("is true.");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 569 */       if (localAlternative.synPred != null) {
/* 570 */         _println("the syntactic predicate:");
/* 571 */         this.tabs += 1;
/* 572 */         genSynPred(localAlternative.synPred);
/* 573 */         this.tabs -= 1;
/* 574 */         println("is matched.");
/*     */       }
/*     */ 
/* 578 */       genAlt(localAlternative);
/*     */     }
/* 580 */     println("");
/* 581 */     println("OTHERWISE, a NoViableAlt exception will be thrown");
/* 582 */     println("");
/*     */ 
/* 584 */     if (i == 0) {
/* 585 */       this.tabs -= 1;
/* 586 */       println("End of alternatives");
/*     */     }
/* 588 */     this.tabs -= 1;
/* 589 */     println("End of alternative block.");
/*     */   }
/*     */ 
/*     */   public void genFollowSetForRuleBlock(RuleBlock paramRuleBlock)
/*     */   {
/* 597 */     Lookahead localLookahead = this.grammar.theLLkAnalyzer.FOLLOW(1, paramRuleBlock.endNode);
/* 598 */     printSet(this.grammar.maxk, 1, localLookahead);
/*     */   }
/*     */ 
/*     */   protected void genHeader()
/*     */   {
/* 603 */     println("ANTLR-generated file resulting from grammar " + this.antlrTool.grammarFile);
/* 604 */     println("Diagnostic output");
/* 605 */     println("");
/* 606 */     println("Terence Parr, MageLang Institute");
/* 607 */     println("with John Lilley, Empathy Software");
/* 608 */     println("ANTLR Version " + Tool.version + "; 1989-2005");
/* 609 */     println("");
/* 610 */     println("*** Header Action.");
/* 611 */     println("This action will appear at the top of all generated files.");
/* 612 */     this.tabs += 1;
/* 613 */     printAction(this.behavior.getHeaderAction(""));
/* 614 */     this.tabs -= 1;
/* 615 */     println("*** End of Header Action");
/* 616 */     println("");
/*     */   }
/*     */ 
/*     */   protected void genLookaheadSetForAlt(Alternative paramAlternative)
/*     */   {
/* 621 */     if ((this.doingLexRules) && (paramAlternative.cache[1].containsEpsilon())) {
/* 622 */       println("MATCHES ALL");
/* 623 */       return;
/*     */     }
/* 625 */     int i = paramAlternative.lookaheadDepth;
/* 626 */     if (i == 2147483647)
/*     */     {
/* 629 */       i = this.grammar.maxk;
/*     */     }
/* 631 */     for (int j = 1; j <= i; j++) {
/* 632 */       Lookahead localLookahead = paramAlternative.cache[j];
/* 633 */       printSet(i, j, localLookahead);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void genLookaheadSetForBlock(AlternativeBlock paramAlternativeBlock)
/*     */   {
/* 643 */     int i = 0;
/*     */     Object localObject;
/* 644 */     for (int j = 0; j < paramAlternativeBlock.alternatives.size(); j++) {
/* 645 */       localObject = paramAlternativeBlock.getAlternativeAt(j);
/* 646 */       if (((Alternative)localObject).lookaheadDepth == 2147483647) {
/* 647 */         i = this.grammar.maxk;
/* 648 */         break;
/*     */       }
/* 650 */       if (i < ((Alternative)localObject).lookaheadDepth) {
/* 651 */         i = ((Alternative)localObject).lookaheadDepth;
/*     */       }
/*     */     }
/*     */ 
/* 655 */     for (j = 1; j <= i; j++) {
/* 656 */       localObject = this.grammar.theLLkAnalyzer.look(j, paramAlternativeBlock);
/* 657 */       printSet(i, j, (Lookahead)localObject);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void genNextToken()
/*     */   {
/* 666 */     println("");
/* 667 */     println("*** Lexer nextToken rule:");
/* 668 */     println("The lexer nextToken rule is synthesized from all of the user-defined");
/* 669 */     println("lexer rules.  It logically consists of one big alternative block with");
/* 670 */     println("each user-defined rule being an alternative.");
/* 671 */     println("");
/*     */ 
/* 675 */     RuleBlock localRuleBlock = MakeGrammar.createNextTokenRule(this.grammar, this.grammar.rules, "nextToken");
/*     */ 
/* 678 */     RuleSymbol localRuleSymbol = new RuleSymbol("mnextToken");
/* 679 */     localRuleSymbol.setDefined();
/* 680 */     localRuleSymbol.setBlock(localRuleBlock);
/* 681 */     localRuleSymbol.access = "private";
/* 682 */     this.grammar.define(localRuleSymbol);
/*     */ 
/* 685 */     if (!this.grammar.theLLkAnalyzer.deterministic(localRuleBlock)) {
/* 686 */       println("The grammar analyzer has determined that the synthesized");
/* 687 */       println("nextToken rule is non-deterministic (i.e., it has ambiguities)");
/* 688 */       println("This means that there is some overlap of the character");
/* 689 */       println("lookahead for two or more of your lexer rules.");
/*     */     }
/*     */ 
/* 692 */     genCommonBlock(localRuleBlock);
/*     */ 
/* 694 */     println("*** End of nextToken lexer rule.");
/*     */   }
/*     */ 
/*     */   public void genRule(RuleSymbol paramRuleSymbol)
/*     */   {
/* 701 */     println("");
/* 702 */     String str = this.doingLexRules ? "Lexer" : "Parser";
/* 703 */     println("*** " + str + " Rule: " + paramRuleSymbol.getId());
/* 704 */     if (!paramRuleSymbol.isDefined()) {
/* 705 */       println("This rule is undefined.");
/* 706 */       println("This means that the rule was referenced somewhere in the grammar,");
/* 707 */       println("but a definition for the rule was not encountered.");
/* 708 */       println("It is also possible that syntax errors during the parse of");
/* 709 */       println("your grammar file prevented correct processing of the rule.");
/* 710 */       println("*** End " + str + " Rule: " + paramRuleSymbol.getId());
/* 711 */       return;
/*     */     }
/* 713 */     this.tabs += 1;
/*     */ 
/* 715 */     if (paramRuleSymbol.access.length() != 0) {
/* 716 */       println("Access: " + paramRuleSymbol.access);
/*     */     }
/*     */ 
/* 720 */     RuleBlock localRuleBlock = paramRuleSymbol.getBlock();
/*     */ 
/* 723 */     if (localRuleBlock.returnAction != null) {
/* 724 */       println("Return value(s): " + localRuleBlock.returnAction);
/* 725 */       if (this.doingLexRules) {
/* 726 */         println("Error: you specified return value(s) for a lexical rule.");
/* 727 */         println("\tLexical rules have an implicit return type of 'int'.");
/*     */       }
/*     */ 
/*     */     }
/* 731 */     else if (this.doingLexRules) {
/* 732 */       println("Return value: lexical rule returns an implicit token type");
/*     */     }
/*     */     else {
/* 735 */       println("Return value: none");
/*     */     }
/*     */ 
/* 740 */     if (localRuleBlock.argAction != null) {
/* 741 */       println("Arguments: " + localRuleBlock.argAction);
/*     */     }
/*     */ 
/* 745 */     genBlockPreamble(localRuleBlock);
/*     */ 
/* 748 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(localRuleBlock);
/* 749 */     if (!bool) {
/* 750 */       println("Error: This rule is non-deterministic");
/*     */     }
/*     */ 
/* 754 */     genCommonBlock(localRuleBlock);
/*     */ 
/* 757 */     ExceptionSpec localExceptionSpec = localRuleBlock.findExceptionSpec("");
/*     */ 
/* 760 */     if (localExceptionSpec != null) {
/* 761 */       println("You specified error-handler(s) for this rule:");
/* 762 */       this.tabs += 1;
/* 763 */       for (int i = 0; i < localExceptionSpec.handlers.size(); i++) {
/* 764 */         if (i != 0) {
/* 765 */           println("");
/*     */         }
/*     */ 
/* 768 */         ExceptionHandler localExceptionHandler = (ExceptionHandler)localExceptionSpec.handlers.elementAt(i);
/* 769 */         println("Error-handler(" + (i + 1) + ") catches [" + localExceptionHandler.exceptionTypeAndName.getText() + "] and executes:");
/* 770 */         printAction(localExceptionHandler.action.getText());
/*     */       }
/* 772 */       this.tabs -= 1;
/* 773 */       println("End error-handlers.");
/*     */     }
/* 775 */     else if (!this.doingLexRules) {
/* 776 */       println("Default error-handling will be generated, which catches all");
/* 777 */       println("parser exceptions and consumes tokens until the follow-set is seen.");
/*     */     }
/*     */ 
/* 782 */     if (!this.doingLexRules) {
/* 783 */       println("The follow set for this rule is:");
/* 784 */       this.tabs += 1;
/* 785 */       genFollowSetForRuleBlock(localRuleBlock);
/* 786 */       this.tabs -= 1;
/*     */     }
/*     */ 
/* 789 */     this.tabs -= 1;
/* 790 */     println("*** End " + str + " Rule: " + paramRuleSymbol.getId());
/*     */   }
/*     */ 
/*     */   protected void genSynPred(SynPredBlock paramSynPredBlock)
/*     */   {
/* 798 */     this.syntacticPredLevel += 1;
/* 799 */     gen(paramSynPredBlock);
/* 800 */     this.syntacticPredLevel -= 1;
/*     */   }
/*     */ 
/*     */   protected void genTokenTypes(TokenManager paramTokenManager)
/*     */     throws IOException
/*     */   {
/* 806 */     this.antlrTool.reportProgress("Generating " + paramTokenManager.getName() + TokenTypesFileSuffix + TokenTypesFileExt);
/* 807 */     this.currentOutput = this.antlrTool.openOutputFile(paramTokenManager.getName() + TokenTypesFileSuffix + TokenTypesFileExt);
/*     */ 
/* 809 */     this.tabs = 0;
/*     */ 
/* 812 */     genHeader();
/*     */ 
/* 816 */     println("");
/* 817 */     println("*** Tokens used by the parser");
/* 818 */     println("This is a list of the token numeric values and the corresponding");
/* 819 */     println("token identifiers.  Some tokens are literals, and because of that");
/* 820 */     println("they have no identifiers.  Literals are double-quoted.");
/* 821 */     this.tabs += 1;
/*     */ 
/* 824 */     Vector localVector = paramTokenManager.getVocabulary();
/* 825 */     for (int i = 4; i < localVector.size(); i++) {
/* 826 */       String str = (String)localVector.elementAt(i);
/* 827 */       if (str != null) {
/* 828 */         println(str + " = " + i);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 833 */     this.tabs -= 1;
/* 834 */     println("*** End of tokens used by the parser");
/*     */ 
/* 837 */     this.currentOutput.close();
/* 838 */     this.currentOutput = null;
/*     */   }
/*     */ 
/*     */   public String getASTCreateString(Vector paramVector)
/*     */   {
/* 845 */     return "***Create an AST from a vector here***" + System.getProperty("line.separator");
/*     */   }
/*     */ 
/*     */   public String getASTCreateString(GrammarAtom paramGrammarAtom, String paramString)
/*     */   {
/* 852 */     return "[" + paramString + "]";
/*     */   }
/*     */ 
/*     */   protected String processActionForSpecialSymbols(String paramString, int paramInt, RuleBlock paramRuleBlock, ActionTransInfo paramActionTransInfo)
/*     */   {
/* 860 */     return paramString;
/*     */   }
/*     */ 
/*     */   public String mapTreeId(String paramString, ActionTransInfo paramActionTransInfo)
/*     */   {
/* 870 */     return paramString;
/*     */   }
/*     */ 
/*     */   public void printSet(int paramInt1, int paramInt2, Lookahead paramLookahead)
/*     */   {
/* 879 */     int i = 5;
/*     */ 
/* 881 */     int[] arrayOfInt = paramLookahead.fset.toArray();
/*     */ 
/* 883 */     if (paramInt1 != 1) {
/* 884 */       print("k==" + paramInt2 + ": {");
/*     */     }
/*     */     else {
/* 887 */       print("{ ");
/*     */     }
/* 889 */     if (arrayOfInt.length > i) {
/* 890 */       _println("");
/* 891 */       this.tabs += 1;
/* 892 */       print("");
/*     */     }
/*     */ 
/* 895 */     int j = 0;
/* 896 */     for (int k = 0; k < arrayOfInt.length; k++) {
/* 897 */       j++;
/* 898 */       if (j > i) {
/* 899 */         _println("");
/* 900 */         print("");
/* 901 */         j = 0;
/*     */       }
/* 903 */       if (this.doingLexRules) {
/* 904 */         _print(this.charFormatter.literalChar(arrayOfInt[k]));
/*     */       }
/*     */       else {
/* 907 */         _print((String)this.grammar.tokenManager.getVocabulary().elementAt(arrayOfInt[k]));
/*     */       }
/* 909 */       if (k != arrayOfInt.length - 1) {
/* 910 */         _print(", ");
/*     */       }
/*     */     }
/*     */ 
/* 914 */     if (arrayOfInt.length > i) {
/* 915 */       _println("");
/* 916 */       this.tabs -= 1;
/* 917 */       print("");
/*     */     }
/* 919 */     _println(" }");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.DiagnosticCodeGenerator
 * JD-Core Version:    0.6.2
 */