/*     */ package antlr;
/*     */ 
/*     */ import antlr.collections.Stack;
/*     */ import antlr.collections.impl.LList;
/*     */ import antlr.collections.impl.Vector;
/*     */ 
/*     */ public class MakeGrammar extends DefineGrammarSymbols
/*     */ {
/*  16 */   protected Stack blocks = new LList();
/*     */   protected RuleRefElement lastRuleRef;
/*     */   protected RuleEndElement ruleEnd;
/*     */   protected RuleBlock ruleBlock;
/*  21 */   protected int nested = 0;
/*  22 */   protected boolean grammarError = false;
/*     */ 
/*  24 */   ExceptionSpec currentExceptionSpec = null;
/*     */ 
/*     */   public MakeGrammar(Tool paramTool, String[] paramArrayOfString, LLkAnalyzer paramLLkAnalyzer) {
/*  27 */     super(paramTool, paramArrayOfString, paramLLkAnalyzer);
/*     */   }
/*     */ 
/*     */   public void abortGrammar()
/*     */   {
/*  32 */     String str = "unknown grammar";
/*  33 */     if (this.grammar != null) {
/*  34 */       str = this.grammar.getClassName();
/*     */     }
/*  36 */     this.tool.error("aborting grammar '" + str + "' due to errors");
/*  37 */     super.abortGrammar();
/*     */   }
/*     */ 
/*     */   protected void addElementToCurrentAlt(AlternativeElement paramAlternativeElement) {
/*  41 */     paramAlternativeElement.enclosingRuleName = this.ruleBlock.ruleName;
/*  42 */     context().addAlternativeElement(paramAlternativeElement);
/*     */   }
/*     */ 
/*     */   public void beginAlt(boolean paramBoolean) {
/*  46 */     super.beginAlt(paramBoolean);
/*  47 */     Alternative localAlternative = new Alternative();
/*  48 */     localAlternative.setAutoGen(paramBoolean);
/*  49 */     context().block.addAlternative(localAlternative);
/*     */   }
/*     */ 
/*     */   public void beginChildList() {
/*  53 */     super.beginChildList();
/*  54 */     context().block.addAlternative(new Alternative());
/*     */   }
/*     */ 
/*     */   public void beginExceptionGroup()
/*     */   {
/*  59 */     super.beginExceptionGroup();
/*  60 */     if (!(context().block instanceof RuleBlock))
/*  61 */       this.tool.panic("beginExceptionGroup called outside of rule block");
/*     */   }
/*     */ 
/*     */   public void beginExceptionSpec(Token paramToken)
/*     */   {
/*  68 */     if (paramToken != null) {
/*  69 */       paramToken.setText(StringUtils.stripFront(StringUtils.stripBack(paramToken.getText(), " \n\r\t"), " \n\r\t"));
/*     */     }
/*  71 */     super.beginExceptionSpec(paramToken);
/*     */ 
/*  74 */     this.currentExceptionSpec = new ExceptionSpec(paramToken);
/*     */   }
/*     */ 
/*     */   public void beginSubRule(Token paramToken1, Token paramToken2, boolean paramBoolean) {
/*  78 */     super.beginSubRule(paramToken1, paramToken2, paramBoolean);
/*     */ 
/*  82 */     this.blocks.push(new BlockContext());
/*  83 */     context().block = new AlternativeBlock(this.grammar, paramToken2, paramBoolean);
/*  84 */     context().altNum = 0;
/*  85 */     this.nested += 1;
/*     */ 
/*  88 */     context().blockEnd = new BlockEndElement(this.grammar);
/*     */ 
/*  90 */     context().blockEnd.block = context().block;
/*  91 */     labelElement(context().block, paramToken1);
/*     */   }
/*     */ 
/*     */   public void beginTree(Token paramToken) throws SemanticException {
/*  95 */     if (!(this.grammar instanceof TreeWalkerGrammar)) {
/*  96 */       this.tool.error("Trees only allowed in TreeParser", this.grammar.getFilename(), paramToken.getLine(), paramToken.getColumn());
/*  97 */       throw new SemanticException("Trees only allowed in TreeParser");
/*     */     }
/*  99 */     super.beginTree(paramToken);
/* 100 */     this.blocks.push(new TreeBlockContext());
/* 101 */     context().block = new TreeElement(this.grammar, paramToken);
/* 102 */     context().altNum = 0;
/*     */   }
/*     */ 
/*     */   public BlockContext context() {
/* 106 */     if (this.blocks.height() == 0) {
/* 107 */       return null;
/*     */     }
/*     */ 
/* 110 */     return (BlockContext)this.blocks.top();
/*     */   }
/*     */ 
/*     */   public static RuleBlock createNextTokenRule(Grammar paramGrammar, Vector paramVector, String paramString)
/*     */   {
/* 123 */     RuleBlock localRuleBlock1 = new RuleBlock(paramGrammar, paramString);
/* 124 */     localRuleBlock1.setDefaultErrorHandler(paramGrammar.getDefaultErrorHandler());
/* 125 */     RuleEndElement localRuleEndElement = new RuleEndElement(paramGrammar);
/* 126 */     localRuleBlock1.setEndElement(localRuleEndElement);
/* 127 */     localRuleEndElement.block = localRuleBlock1;
/*     */ 
/* 129 */     for (int i = 0; i < paramVector.size(); i++) {
/* 130 */       RuleSymbol localRuleSymbol = (RuleSymbol)paramVector.elementAt(i);
/* 131 */       if (!localRuleSymbol.isDefined()) {
/* 132 */         paramGrammar.antlrTool.error("Lexer rule " + localRuleSymbol.id.substring(1) + " is not defined");
/*     */       }
/* 135 */       else if (localRuleSymbol.access.equals("public")) {
/* 136 */         Alternative localAlternative = new Alternative();
/* 137 */         RuleBlock localRuleBlock2 = localRuleSymbol.getBlock();
/* 138 */         Vector localVector = localRuleBlock2.getAlternatives();
/*     */ 
/* 141 */         if ((localVector != null) && (localVector.size() == 1)) {
/* 142 */           localObject = (Alternative)localVector.elementAt(0);
/* 143 */           if (((Alternative)localObject).semPred != null)
/*     */           {
/* 145 */             localAlternative.semPred = ((Alternative)localObject).semPred;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 154 */         Object localObject = new RuleRefElement(paramGrammar, new CommonToken(41, localRuleSymbol.getId()), 1);
/*     */ 
/* 158 */         ((RuleRefElement)localObject).setLabel("theRetToken");
/* 159 */         ((RuleRefElement)localObject).enclosingRuleName = "nextToken";
/* 160 */         ((RuleRefElement)localObject).next = localRuleEndElement;
/* 161 */         localAlternative.addElement((AlternativeElement)localObject);
/* 162 */         localAlternative.setAutoGen(true);
/* 163 */         localRuleBlock1.addAlternative(localAlternative);
/* 164 */         localRuleSymbol.addReference((RuleRefElement)localObject);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 169 */     localRuleBlock1.setAutoGen(true);
/* 170 */     localRuleBlock1.prepareForAnalysis();
/*     */ 
/* 172 */     return localRuleBlock1;
/*     */   }
/*     */ 
/*     */   private AlternativeBlock createOptionalRuleRef(String paramString, Token paramToken)
/*     */   {
/* 178 */     AlternativeBlock localAlternativeBlock = new AlternativeBlock(this.grammar, paramToken, false);
/*     */ 
/* 181 */     String str = CodeGenerator.encodeLexerRuleName(paramString);
/* 182 */     if (!this.grammar.isDefined(str)) {
/* 183 */       this.grammar.define(new RuleSymbol(str));
/*     */     }
/*     */ 
/* 188 */     CommonToken localCommonToken = new CommonToken(24, paramString);
/* 189 */     localCommonToken.setLine(paramToken.getLine());
/* 190 */     localCommonToken.setLine(paramToken.getColumn());
/* 191 */     RuleRefElement localRuleRefElement = new RuleRefElement(this.grammar, localCommonToken, 1);
/*     */ 
/* 194 */     localRuleRefElement.enclosingRuleName = this.ruleBlock.ruleName;
/*     */ 
/* 197 */     BlockEndElement localBlockEndElement = new BlockEndElement(this.grammar);
/* 198 */     localBlockEndElement.block = localAlternativeBlock;
/*     */ 
/* 201 */     Alternative localAlternative1 = new Alternative(localRuleRefElement);
/* 202 */     localAlternative1.addElement(localBlockEndElement);
/*     */ 
/* 205 */     localAlternativeBlock.addAlternative(localAlternative1);
/*     */ 
/* 208 */     Alternative localAlternative2 = new Alternative();
/* 209 */     localAlternative2.addElement(localBlockEndElement);
/*     */ 
/* 211 */     localAlternativeBlock.addAlternative(localAlternative2);
/*     */ 
/* 213 */     localAlternativeBlock.prepareForAnalysis();
/* 214 */     return localAlternativeBlock;
/*     */   }
/*     */ 
/*     */   public void defineRuleName(Token paramToken, String paramString1, boolean paramBoolean, String paramString2)
/*     */     throws SemanticException
/*     */   {
/* 223 */     if (paramToken.type == 24) {
/* 224 */       if (!(this.grammar instanceof LexerGrammar)) {
/* 225 */         this.tool.error("Lexical rule " + paramToken.getText() + " defined outside of lexer", this.grammar.getFilename(), paramToken.getLine(), paramToken.getColumn());
/*     */ 
/* 228 */         paramToken.setText(paramToken.getText().toLowerCase());
/*     */       }
/*     */ 
/*     */     }
/* 232 */     else if ((this.grammar instanceof LexerGrammar)) {
/* 233 */       this.tool.error("Lexical rule names must be upper case, '" + paramToken.getText() + "' is not", this.grammar.getFilename(), paramToken.getLine(), paramToken.getColumn());
/*     */ 
/* 236 */       paramToken.setText(paramToken.getText().toUpperCase());
/*     */     }
/*     */ 
/* 240 */     super.defineRuleName(paramToken, paramString1, paramBoolean, paramString2);
/* 241 */     String str = paramToken.getText();
/*     */ 
/* 243 */     if (paramToken.type == 24) {
/* 244 */       str = CodeGenerator.encodeLexerRuleName(str);
/*     */     }
/* 246 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(str);
/* 247 */     RuleBlock localRuleBlock = new RuleBlock(this.grammar, paramToken.getText(), paramToken.getLine(), paramBoolean);
/*     */ 
/* 250 */     localRuleBlock.setDefaultErrorHandler(this.grammar.getDefaultErrorHandler());
/*     */ 
/* 252 */     this.ruleBlock = localRuleBlock;
/* 253 */     this.blocks.push(new BlockContext());
/* 254 */     context().block = localRuleBlock;
/* 255 */     localRuleSymbol.setBlock(localRuleBlock);
/* 256 */     this.ruleEnd = new RuleEndElement(this.grammar);
/* 257 */     localRuleBlock.setEndElement(this.ruleEnd);
/* 258 */     this.nested = 0;
/*     */   }
/*     */ 
/*     */   public void endAlt() {
/* 262 */     super.endAlt();
/* 263 */     if (this.nested == 0) {
/* 264 */       addElementToCurrentAlt(this.ruleEnd);
/*     */     }
/*     */     else {
/* 267 */       addElementToCurrentAlt(context().blockEnd);
/*     */     }
/* 269 */     context().altNum += 1;
/*     */   }
/*     */ 
/*     */   public void endChildList() {
/* 273 */     super.endChildList();
/*     */ 
/* 278 */     BlockEndElement localBlockEndElement = new BlockEndElement(this.grammar);
/* 279 */     localBlockEndElement.block = context().block;
/* 280 */     addElementToCurrentAlt(localBlockEndElement);
/*     */   }
/*     */ 
/*     */   public void endExceptionGroup() {
/* 284 */     super.endExceptionGroup();
/*     */   }
/*     */ 
/*     */   public void endExceptionSpec() {
/* 288 */     super.endExceptionSpec();
/* 289 */     if (this.currentExceptionSpec == null) {
/* 290 */       this.tool.panic("exception processing internal error -- no active exception spec");
/*     */     }
/* 292 */     if ((context().block instanceof RuleBlock))
/*     */     {
/* 294 */       ((RuleBlock)context().block).addExceptionSpec(this.currentExceptionSpec);
/*     */     }
/* 298 */     else if (context().currentAlt().exceptionSpec != null) {
/* 299 */       this.tool.error("Alternative already has an exception specification", this.grammar.getFilename(), context().block.getLine(), context().block.getColumn());
/*     */     }
/*     */     else {
/* 302 */       context().currentAlt().exceptionSpec = this.currentExceptionSpec;
/*     */     }
/*     */ 
/* 305 */     this.currentExceptionSpec = null;
/*     */   }
/*     */ 
/*     */   public void endGrammar()
/*     */   {
/* 310 */     if (this.grammarError) {
/* 311 */       abortGrammar();
/*     */     }
/*     */     else
/* 314 */       super.endGrammar();
/*     */   }
/*     */ 
/*     */   public void endRule(String paramString)
/*     */   {
/* 319 */     super.endRule(paramString);
/* 320 */     BlockContext localBlockContext = (BlockContext)this.blocks.pop();
/*     */ 
/* 322 */     this.ruleEnd.block = localBlockContext.block;
/* 323 */     this.ruleEnd.block.prepareForAnalysis();
/*     */   }
/*     */ 
/*     */   public void endSubRule()
/*     */   {
/* 328 */     super.endSubRule();
/* 329 */     this.nested -= 1;
/*     */ 
/* 331 */     BlockContext localBlockContext = (BlockContext)this.blocks.pop();
/* 332 */     AlternativeBlock localAlternativeBlock = localBlockContext.block;
/*     */     Object localObject;
/* 336 */     if ((localAlternativeBlock.not) && (!(localAlternativeBlock instanceof SynPredBlock)) && (!(localAlternativeBlock instanceof ZeroOrMoreBlock)) && (!(localAlternativeBlock instanceof OneOrMoreBlock)))
/*     */     {
/* 342 */       if (!this.analyzer.subruleCanBeInverted(localAlternativeBlock, this.grammar instanceof LexerGrammar)) {
/* 343 */         localObject = System.getProperty("line.separator");
/* 344 */         this.tool.error("This subrule cannot be inverted.  Only subrules of the form:" + (String)localObject + "    (T1|T2|T3...) or" + (String)localObject + "    ('c1'|'c2'|'c3'...)" + (String)localObject + "may be inverted (ranges are also allowed).", this.grammar.getFilename(), localAlternativeBlock.getLine(), localAlternativeBlock.getColumn());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 356 */     if ((localAlternativeBlock instanceof SynPredBlock))
/*     */     {
/* 359 */       localObject = (SynPredBlock)localAlternativeBlock;
/* 360 */       context().block.hasASynPred = true;
/* 361 */       context().currentAlt().synPred = ((SynPredBlock)localObject);
/* 362 */       this.grammar.hasSyntacticPredicate = true;
/* 363 */       ((SynPredBlock)localObject).removeTrackingOfRuleRefs(this.grammar);
/*     */     }
/*     */     else {
/* 366 */       addElementToCurrentAlt(localAlternativeBlock);
/*     */     }
/* 368 */     localBlockContext.blockEnd.block.prepareForAnalysis();
/*     */   }
/*     */ 
/*     */   public void endTree() {
/* 372 */     super.endTree();
/* 373 */     BlockContext localBlockContext = (BlockContext)this.blocks.pop();
/* 374 */     addElementToCurrentAlt(localBlockContext.block);
/*     */   }
/*     */ 
/*     */   public void hasError()
/*     */   {
/* 379 */     this.grammarError = true;
/*     */   }
/*     */ 
/*     */   private void labelElement(AlternativeElement paramAlternativeElement, Token paramToken) {
/* 383 */     if (paramToken != null)
/*     */     {
/* 385 */       for (int i = 0; i < this.ruleBlock.labeledElements.size(); i++) {
/* 386 */         AlternativeElement localAlternativeElement = (AlternativeElement)this.ruleBlock.labeledElements.elementAt(i);
/* 387 */         String str = localAlternativeElement.getLabel();
/* 388 */         if ((str != null) && (str.equals(paramToken.getText()))) {
/* 389 */           this.tool.error("Label '" + paramToken.getText() + "' has already been defined", this.grammar.getFilename(), paramToken.getLine(), paramToken.getColumn());
/* 390 */           return;
/*     */         }
/*     */       }
/*     */ 
/* 394 */       paramAlternativeElement.setLabel(paramToken.getText());
/* 395 */       this.ruleBlock.labeledElements.appendElement(paramAlternativeElement);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void noAutoGenSubRule() {
/* 400 */     context().block.setAutoGen(false);
/*     */   }
/*     */ 
/*     */   public void oneOrMoreSubRule() {
/* 404 */     if (context().block.not) {
/* 405 */       this.tool.error("'~' cannot be applied to (...)* subrule", this.grammar.getFilename(), context().block.getLine(), context().block.getColumn());
/*     */     }
/*     */ 
/* 410 */     OneOrMoreBlock localOneOrMoreBlock = new OneOrMoreBlock(this.grammar);
/* 411 */     setBlock(localOneOrMoreBlock, context().block);
/* 412 */     BlockContext localBlockContext = (BlockContext)this.blocks.pop();
/* 413 */     this.blocks.push(new BlockContext());
/* 414 */     context().block = localOneOrMoreBlock;
/* 415 */     context().blockEnd = localBlockContext.blockEnd;
/* 416 */     context().blockEnd.block = localOneOrMoreBlock;
/*     */   }
/*     */ 
/*     */   public void optionalSubRule() {
/* 420 */     if (context().block.not) {
/* 421 */       this.tool.error("'~' cannot be applied to (...)? subrule", this.grammar.getFilename(), context().block.getLine(), context().block.getColumn());
/*     */     }
/*     */ 
/* 425 */     beginAlt(false);
/* 426 */     endAlt();
/*     */   }
/*     */ 
/*     */   public void refAction(Token paramToken) {
/* 430 */     super.refAction(paramToken);
/* 431 */     context().block.hasAnAction = true;
/* 432 */     addElementToCurrentAlt(new ActionElement(this.grammar, paramToken));
/*     */   }
/*     */ 
/*     */   public void setUserExceptions(String paramString) {
/* 436 */     ((RuleBlock)context().block).throwsSpec = paramString;
/*     */   }
/*     */ 
/*     */   public void refArgAction(Token paramToken)
/*     */   {
/* 441 */     ((RuleBlock)context().block).argAction = paramToken.getText();
/*     */   }
/*     */ 
/*     */   public void refCharLiteral(Token paramToken1, Token paramToken2, boolean paramBoolean1, int paramInt, boolean paramBoolean2) {
/* 445 */     if (!(this.grammar instanceof LexerGrammar)) {
/* 446 */       this.tool.error("Character literal only valid in lexer", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 447 */       return;
/*     */     }
/* 449 */     super.refCharLiteral(paramToken1, paramToken2, paramBoolean1, paramInt, paramBoolean2);
/* 450 */     CharLiteralElement localCharLiteralElement = new CharLiteralElement((LexerGrammar)this.grammar, paramToken1, paramBoolean1, paramInt);
/*     */ 
/* 453 */     if ((!((LexerGrammar)this.grammar).caseSensitive) && (localCharLiteralElement.getType() < 128) && (Character.toLowerCase((char)localCharLiteralElement.getType()) != (char)localCharLiteralElement.getType()))
/*     */     {
/* 457 */       this.tool.warning("Character literal must be lowercase when caseSensitive=false", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/*     */     }
/*     */ 
/* 460 */     addElementToCurrentAlt(localCharLiteralElement);
/* 461 */     labelElement(localCharLiteralElement, paramToken2);
/*     */ 
/* 464 */     String str = this.ruleBlock.getIgnoreRule();
/* 465 */     if ((!paramBoolean2) && (str != null))
/* 466 */       addElementToCurrentAlt(createOptionalRuleRef(str, paramToken1));
/*     */   }
/*     */ 
/*     */   public void refCharRange(Token paramToken1, Token paramToken2, Token paramToken3, int paramInt, boolean paramBoolean)
/*     */   {
/* 471 */     if (!(this.grammar instanceof LexerGrammar)) {
/* 472 */       this.tool.error("Character range only valid in lexer", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 473 */       return;
/*     */     }
/* 475 */     int i = ANTLRLexer.tokenTypeForCharLiteral(paramToken1.getText());
/* 476 */     int j = ANTLRLexer.tokenTypeForCharLiteral(paramToken2.getText());
/* 477 */     if (j < i) {
/* 478 */       this.tool.error("Malformed range.", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 479 */       return;
/*     */     }
/*     */ 
/* 483 */     if (!((LexerGrammar)this.grammar).caseSensitive) {
/* 484 */       if ((i < 128) && (Character.toLowerCase((char)i) != (char)i)) {
/* 485 */         this.tool.warning("Character literal must be lowercase when caseSensitive=false", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/*     */       }
/* 487 */       if ((j < 128) && (Character.toLowerCase((char)j) != (char)j)) {
/* 488 */         this.tool.warning("Character literal must be lowercase when caseSensitive=false", this.grammar.getFilename(), paramToken2.getLine(), paramToken2.getColumn());
/*     */       }
/*     */     }
/*     */ 
/* 492 */     super.refCharRange(paramToken1, paramToken2, paramToken3, paramInt, paramBoolean);
/* 493 */     CharRangeElement localCharRangeElement = new CharRangeElement((LexerGrammar)this.grammar, paramToken1, paramToken2, paramInt);
/* 494 */     addElementToCurrentAlt(localCharRangeElement);
/* 495 */     labelElement(localCharRangeElement, paramToken3);
/*     */ 
/* 498 */     String str = this.ruleBlock.getIgnoreRule();
/* 499 */     if ((!paramBoolean) && (str != null))
/* 500 */       addElementToCurrentAlt(createOptionalRuleRef(str, paramToken1));
/*     */   }
/*     */ 
/*     */   public void refTokensSpecElementOption(Token paramToken1, Token paramToken2, Token paramToken3)
/*     */   {
/* 511 */     TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol(paramToken1.getText());
/*     */ 
/* 513 */     if (localTokenSymbol == null) {
/* 514 */       this.tool.panic("cannot find " + paramToken1.getText() + "in tokens {...}");
/*     */     }
/* 516 */     if (paramToken2.getText().equals("AST")) {
/* 517 */       localTokenSymbol.setASTNodeType(paramToken3.getText());
/*     */     }
/*     */     else
/* 520 */       this.grammar.antlrTool.error("invalid tokens {...} element option:" + paramToken2.getText(), this.grammar.getFilename(), paramToken2.getLine(), paramToken2.getColumn());
/*     */   }
/*     */ 
/*     */   public void refElementOption(Token paramToken1, Token paramToken2)
/*     */   {
/* 532 */     AlternativeElement localAlternativeElement = context().currentElement();
/* 533 */     if (((localAlternativeElement instanceof StringLiteralElement)) || ((localAlternativeElement instanceof TokenRefElement)) || ((localAlternativeElement instanceof WildcardElement)))
/*     */     {
/* 536 */       ((GrammarAtom)localAlternativeElement).setOption(paramToken1, paramToken2);
/*     */     }
/*     */     else
/* 539 */       this.tool.error("cannot use element option (" + paramToken1.getText() + ") for this kind of element", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/*     */   }
/*     */ 
/*     */   public void refExceptionHandler(Token paramToken1, Token paramToken2)
/*     */   {
/* 547 */     super.refExceptionHandler(paramToken1, paramToken2);
/* 548 */     if (this.currentExceptionSpec == null) {
/* 549 */       this.tool.panic("exception handler processing internal error");
/*     */     }
/* 551 */     this.currentExceptionSpec.addHandler(new ExceptionHandler(paramToken1, paramToken2));
/*     */   }
/*     */ 
/*     */   public void refInitAction(Token paramToken) {
/* 555 */     super.refAction(paramToken);
/* 556 */     context().block.setInitAction(paramToken.getText());
/*     */   }
/*     */ 
/*     */   public void refMemberAction(Token paramToken) {
/* 560 */     this.grammar.classMemberAction = paramToken;
/*     */   }
/*     */ 
/*     */   public void refPreambleAction(Token paramToken) {
/* 564 */     super.refPreambleAction(paramToken);
/*     */   }
/*     */ 
/*     */   public void refReturnAction(Token paramToken)
/*     */   {
/* 569 */     if ((this.grammar instanceof LexerGrammar)) {
/* 570 */       String str = CodeGenerator.encodeLexerRuleName(((RuleBlock)context().block).getRuleName());
/* 571 */       RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(str);
/* 572 */       if (localRuleSymbol.access.equals("public")) {
/* 573 */         this.tool.warning("public Lexical rules cannot specify return type", this.grammar.getFilename(), paramToken.getLine(), paramToken.getColumn());
/* 574 */         return;
/*     */       }
/*     */     }
/* 577 */     ((RuleBlock)context().block).returnAction = paramToken.getText();
/*     */   }
/*     */ 
/*     */   public void refRule(Token paramToken1, Token paramToken2, Token paramToken3, Token paramToken4, int paramInt)
/*     */   {
/* 586 */     if ((this.grammar instanceof LexerGrammar))
/*     */     {
/* 588 */       if (paramToken2.type != 24) {
/* 589 */         this.tool.error("Parser rule " + paramToken2.getText() + " referenced in lexer");
/* 590 */         return;
/*     */       }
/* 592 */       if (paramInt == 2) {
/* 593 */         this.tool.error("AST specification ^ not allowed in lexer", this.grammar.getFilename(), paramToken2.getLine(), paramToken2.getColumn());
/*     */       }
/*     */     }
/*     */ 
/* 597 */     super.refRule(paramToken1, paramToken2, paramToken3, paramToken4, paramInt);
/* 598 */     this.lastRuleRef = new RuleRefElement(this.grammar, paramToken2, paramInt);
/* 599 */     if (paramToken4 != null) {
/* 600 */       this.lastRuleRef.setArgs(paramToken4.getText());
/*     */     }
/* 602 */     if (paramToken1 != null) {
/* 603 */       this.lastRuleRef.setIdAssign(paramToken1.getText());
/*     */     }
/* 605 */     addElementToCurrentAlt(this.lastRuleRef);
/*     */ 
/* 607 */     String str = paramToken2.getText();
/*     */ 
/* 609 */     if (paramToken2.type == 24) {
/* 610 */       str = CodeGenerator.encodeLexerRuleName(str);
/*     */     }
/*     */ 
/* 613 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(str);
/* 614 */     localRuleSymbol.addReference(this.lastRuleRef);
/* 615 */     labelElement(this.lastRuleRef, paramToken3);
/*     */   }
/*     */ 
/*     */   public void refSemPred(Token paramToken)
/*     */   {
/* 620 */     super.refSemPred(paramToken);
/*     */ 
/* 622 */     if (context().currentAlt().atStart()) {
/* 623 */       context().currentAlt().semPred = paramToken.getText();
/*     */     }
/*     */     else {
/* 626 */       ActionElement localActionElement = new ActionElement(this.grammar, paramToken);
/* 627 */       localActionElement.isSemPred = true;
/* 628 */       addElementToCurrentAlt(localActionElement);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void refStringLiteral(Token paramToken1, Token paramToken2, int paramInt, boolean paramBoolean)
/*     */   {
/* 634 */     super.refStringLiteral(paramToken1, paramToken2, paramInt, paramBoolean);
/* 635 */     if (((this.grammar instanceof TreeWalkerGrammar)) && (paramInt == 2)) {
/* 636 */       this.tool.error("^ not allowed in here for tree-walker", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/*     */     }
/* 638 */     StringLiteralElement localStringLiteralElement = new StringLiteralElement(this.grammar, paramToken1, paramInt);
/*     */ 
/* 641 */     if (((this.grammar instanceof LexerGrammar)) && (!((LexerGrammar)this.grammar).caseSensitive)) {
/* 642 */       for (int i = 1; i < paramToken1.getText().length() - 1; i++) {
/* 643 */         char c = paramToken1.getText().charAt(i);
/* 644 */         if ((c < '') && (Character.toLowerCase(c) != c)) {
/* 645 */           this.tool.warning("Characters of string literal must be lowercase when caseSensitive=false", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 646 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 651 */     addElementToCurrentAlt(localStringLiteralElement);
/* 652 */     labelElement(localStringLiteralElement, paramToken2);
/*     */ 
/* 655 */     String str = this.ruleBlock.getIgnoreRule();
/* 656 */     if ((!paramBoolean) && (str != null))
/* 657 */       addElementToCurrentAlt(createOptionalRuleRef(str, paramToken1));
/*     */   }
/*     */ 
/*     */   public void refToken(Token paramToken1, Token paramToken2, Token paramToken3, Token paramToken4, boolean paramBoolean1, int paramInt, boolean paramBoolean2)
/*     */   {
/*     */     Object localObject;
/* 663 */     if ((this.grammar instanceof LexerGrammar))
/*     */     {
/* 665 */       if (paramInt == 2) {
/* 666 */         this.tool.error("AST specification ^ not allowed in lexer", this.grammar.getFilename(), paramToken2.getLine(), paramToken2.getColumn());
/*     */       }
/* 668 */       if (paramBoolean1) {
/* 669 */         this.tool.error("~TOKEN is not allowed in lexer", this.grammar.getFilename(), paramToken2.getLine(), paramToken2.getColumn());
/*     */       }
/* 671 */       refRule(paramToken1, paramToken2, paramToken3, paramToken4, paramInt);
/*     */ 
/* 674 */       localObject = this.ruleBlock.getIgnoreRule();
/* 675 */       if ((!paramBoolean2) && (localObject != null)) {
/* 676 */         addElementToCurrentAlt(createOptionalRuleRef((String)localObject, paramToken2));
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 681 */       if (paramToken1 != null) {
/* 682 */         this.tool.error("Assignment from token reference only allowed in lexer", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/*     */       }
/* 684 */       if (paramToken4 != null) {
/* 685 */         this.tool.error("Token reference arguments only allowed in lexer", this.grammar.getFilename(), paramToken4.getLine(), paramToken4.getColumn());
/*     */       }
/* 687 */       super.refToken(paramToken1, paramToken2, paramToken3, paramToken4, paramBoolean1, paramInt, paramBoolean2);
/* 688 */       localObject = new TokenRefElement(this.grammar, paramToken2, paramBoolean1, paramInt);
/* 689 */       addElementToCurrentAlt((AlternativeElement)localObject);
/* 690 */       labelElement((AlternativeElement)localObject, paramToken3);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void refTokenRange(Token paramToken1, Token paramToken2, Token paramToken3, int paramInt, boolean paramBoolean) {
/* 695 */     if ((this.grammar instanceof LexerGrammar)) {
/* 696 */       this.tool.error("Token range not allowed in lexer", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 697 */       return;
/*     */     }
/* 699 */     super.refTokenRange(paramToken1, paramToken2, paramToken3, paramInt, paramBoolean);
/* 700 */     TokenRangeElement localTokenRangeElement = new TokenRangeElement(this.grammar, paramToken1, paramToken2, paramInt);
/* 701 */     if (localTokenRangeElement.end < localTokenRangeElement.begin) {
/* 702 */       this.tool.error("Malformed range.", this.grammar.getFilename(), paramToken1.getLine(), paramToken1.getColumn());
/* 703 */       return;
/*     */     }
/* 705 */     addElementToCurrentAlt(localTokenRangeElement);
/* 706 */     labelElement(localTokenRangeElement, paramToken3);
/*     */   }
/*     */ 
/*     */   public void refTreeSpecifier(Token paramToken) {
/* 710 */     context().currentAlt().treeSpecifier = paramToken;
/*     */   }
/*     */ 
/*     */   public void refWildcard(Token paramToken1, Token paramToken2, int paramInt) {
/* 714 */     super.refWildcard(paramToken1, paramToken2, paramInt);
/* 715 */     WildcardElement localWildcardElement = new WildcardElement(this.grammar, paramToken1, paramInt);
/* 716 */     addElementToCurrentAlt(localWildcardElement);
/* 717 */     labelElement(localWildcardElement, paramToken2);
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/* 722 */     super.reset();
/* 723 */     this.blocks = new LList();
/* 724 */     this.lastRuleRef = null;
/* 725 */     this.ruleEnd = null;
/* 726 */     this.ruleBlock = null;
/* 727 */     this.nested = 0;
/* 728 */     this.currentExceptionSpec = null;
/* 729 */     this.grammarError = false;
/*     */   }
/*     */ 
/*     */   public void setArgOfRuleRef(Token paramToken) {
/* 733 */     super.setArgOfRuleRef(paramToken);
/* 734 */     this.lastRuleRef.setArgs(paramToken.getText());
/*     */   }
/*     */ 
/*     */   public static void setBlock(AlternativeBlock paramAlternativeBlock1, AlternativeBlock paramAlternativeBlock2) {
/* 738 */     paramAlternativeBlock1.setAlternatives(paramAlternativeBlock2.getAlternatives());
/* 739 */     paramAlternativeBlock1.initAction = paramAlternativeBlock2.initAction;
/*     */ 
/* 741 */     paramAlternativeBlock1.label = paramAlternativeBlock2.label;
/* 742 */     paramAlternativeBlock1.hasASynPred = paramAlternativeBlock2.hasASynPred;
/* 743 */     paramAlternativeBlock1.hasAnAction = paramAlternativeBlock2.hasAnAction;
/* 744 */     paramAlternativeBlock1.warnWhenFollowAmbig = paramAlternativeBlock2.warnWhenFollowAmbig;
/* 745 */     paramAlternativeBlock1.generateAmbigWarnings = paramAlternativeBlock2.generateAmbigWarnings;
/* 746 */     paramAlternativeBlock1.line = paramAlternativeBlock2.line;
/* 747 */     paramAlternativeBlock1.greedy = paramAlternativeBlock2.greedy;
/* 748 */     paramAlternativeBlock1.greedySet = paramAlternativeBlock2.greedySet;
/*     */   }
/*     */ 
/*     */   public void setRuleOption(Token paramToken1, Token paramToken2)
/*     */   {
/* 753 */     this.ruleBlock.setOption(paramToken1, paramToken2);
/*     */   }
/*     */ 
/*     */   public void setSubruleOption(Token paramToken1, Token paramToken2) {
/* 757 */     context().block.setOption(paramToken1, paramToken2);
/*     */   }
/*     */ 
/*     */   public void synPred() {
/* 761 */     if (context().block.not) {
/* 762 */       this.tool.error("'~' cannot be applied to syntactic predicate", this.grammar.getFilename(), context().block.getLine(), context().block.getColumn());
/*     */     }
/*     */ 
/* 767 */     SynPredBlock localSynPredBlock = new SynPredBlock(this.grammar);
/* 768 */     setBlock(localSynPredBlock, context().block);
/* 769 */     BlockContext localBlockContext = (BlockContext)this.blocks.pop();
/* 770 */     this.blocks.push(new BlockContext());
/* 771 */     context().block = localSynPredBlock;
/* 772 */     context().blockEnd = localBlockContext.blockEnd;
/* 773 */     context().blockEnd.block = localSynPredBlock;
/*     */   }
/*     */ 
/*     */   public void zeroOrMoreSubRule() {
/* 777 */     if (context().block.not) {
/* 778 */       this.tool.error("'~' cannot be applied to (...)+ subrule", this.grammar.getFilename(), context().block.getLine(), context().block.getColumn());
/*     */     }
/*     */ 
/* 783 */     ZeroOrMoreBlock localZeroOrMoreBlock = new ZeroOrMoreBlock(this.grammar);
/* 784 */     setBlock(localZeroOrMoreBlock, context().block);
/* 785 */     BlockContext localBlockContext = (BlockContext)this.blocks.pop();
/* 786 */     this.blocks.push(new BlockContext());
/* 787 */     context().block = localZeroOrMoreBlock;
/* 788 */     context().blockEnd = localBlockContext.blockEnd;
/* 789 */     context().blockEnd.block = localZeroOrMoreBlock;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.MakeGrammar
 * JD-Core Version:    0.6.2
 */