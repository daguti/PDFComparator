/*      */ package antlr;
/*      */ 
/*      */ import antlr.collections.impl.BitSet;
/*      */ import antlr.collections.impl.Vector;
/*      */ import java.io.PrintStream;
/*      */ 
/*      */ public class LLkAnalyzer
/*      */   implements LLkGrammarAnalyzer
/*      */ {
/*   23 */   public boolean DEBUG_ANALYZER = false;
/*      */   private AlternativeBlock currentBlock;
/*   25 */   protected Tool tool = null;
/*   26 */   protected Grammar grammar = null;
/*      */ 
/*   28 */   protected boolean lexicalAnalysis = false;
/*      */ 
/*   30 */   CharFormatter charFormatter = new JavaCharFormatter();
/*      */ 
/*      */   public LLkAnalyzer(Tool paramTool)
/*      */   {
/*   34 */     this.tool = paramTool;
/*      */   }
/*      */ 
/*      */   protected boolean altUsesWildcardDefault(Alternative paramAlternative)
/*      */   {
/*   41 */     AlternativeElement localAlternativeElement = paramAlternative.head;
/*      */ 
/*   43 */     if (((localAlternativeElement instanceof TreeElement)) && ((((TreeElement)localAlternativeElement).root instanceof WildcardElement)))
/*      */     {
/*   45 */       return true;
/*      */     }
/*   47 */     if (((localAlternativeElement instanceof WildcardElement)) && ((localAlternativeElement.next instanceof BlockEndElement))) {
/*   48 */       return true;
/*      */     }
/*   50 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean deterministic(AlternativeBlock paramAlternativeBlock)
/*      */   {
/*   58 */     int i = 1;
/*   59 */     if (this.DEBUG_ANALYZER) System.out.println("deterministic(" + paramAlternativeBlock + ")");
/*   60 */     boolean bool = true;
/*   61 */     int j = paramAlternativeBlock.alternatives.size();
/*   62 */     AlternativeBlock localAlternativeBlock = this.currentBlock;
/*   63 */     Object localObject1 = null;
/*   64 */     this.currentBlock = paramAlternativeBlock;
/*      */ 
/*   67 */     if ((!paramAlternativeBlock.greedy) && (!(paramAlternativeBlock instanceof OneOrMoreBlock)) && (!(paramAlternativeBlock instanceof ZeroOrMoreBlock))) {
/*   68 */       this.tool.warning("Being nongreedy only makes sense for (...)+ and (...)*", this.grammar.getFilename(), paramAlternativeBlock.getLine(), paramAlternativeBlock.getColumn());
/*      */     }
/*      */ 
/*   74 */     if (j == 1) {
/*   75 */       AlternativeElement localAlternativeElement = paramAlternativeBlock.getAlternativeAt(0).head;
/*   76 */       this.currentBlock.alti = 0;
/*   77 */       paramAlternativeBlock.getAlternativeAt(0).cache[1] = localAlternativeElement.look(1);
/*   78 */       paramAlternativeBlock.getAlternativeAt(0).lookaheadDepth = 1;
/*   79 */       this.currentBlock = localAlternativeBlock;
/*   80 */       return true;
/*      */     }
/*      */ 
/*   84 */     for (int k = 0; k < j - 1; k++) {
/*   85 */       this.currentBlock.alti = k;
/*   86 */       this.currentBlock.analysisAlt = k;
/*   87 */       this.currentBlock.altj = (k + 1);
/*      */ 
/*   91 */       for (int m = k + 1; m < j; m++) {
/*   92 */         this.currentBlock.altj = m;
/*   93 */         if (this.DEBUG_ANALYZER) System.out.println("comparing " + k + " against alt " + m); this.currentBlock.analysisAlt = m;
/*   95 */         i = 1;
/*      */ 
/*   99 */         Lookahead[] arrayOfLookahead = new Lookahead[this.grammar.maxk + 1];
/*      */         int n;
/*      */         do {
/*  102 */           n = 0;
/*  103 */           if (this.DEBUG_ANALYZER) System.out.println("checking depth " + i + "<=" + this.grammar.maxk);
/*      */ 
/*  105 */           localObject2 = getAltLookahead(paramAlternativeBlock, k, i);
/*  106 */           localObject3 = getAltLookahead(paramAlternativeBlock, m, i);
/*      */ 
/*  110 */           if (this.DEBUG_ANALYZER) System.out.println("p is " + ((Lookahead)localObject2).toString(",", this.charFormatter, this.grammar));
/*  111 */           if (this.DEBUG_ANALYZER) System.out.println("q is " + ((Lookahead)localObject3).toString(",", this.charFormatter, this.grammar));
/*      */ 
/*  113 */           arrayOfLookahead[i] = ((Lookahead)localObject2).intersection((Lookahead)localObject3);
/*  114 */           if (this.DEBUG_ANALYZER) System.out.println("intersection at depth " + i + " is " + arrayOfLookahead[i].toString());
/*  115 */           if (!arrayOfLookahead[i].nil()) {
/*  116 */             n = 1;
/*  117 */             i++;
/*      */           }
/*      */         }
/*  120 */         while ((n != 0) && (i <= this.grammar.maxk));
/*      */ 
/*  122 */         Object localObject2 = paramAlternativeBlock.getAlternativeAt(k);
/*  123 */         Object localObject3 = paramAlternativeBlock.getAlternativeAt(m);
/*  124 */         if (n != 0) {
/*  125 */           bool = false;
/*  126 */           ((Alternative)localObject2).lookaheadDepth = 2147483647;
/*  127 */           ((Alternative)localObject3).lookaheadDepth = 2147483647;
/*      */ 
/*  135 */           if (((Alternative)localObject2).synPred != null) {
/*  136 */             if (this.DEBUG_ANALYZER) {
/*  137 */               System.out.println("alt " + k + " has a syn pred");
/*      */             }
/*      */ 
/*      */           }
/*  150 */           else if (((Alternative)localObject2).semPred != null) {
/*  151 */             if (this.DEBUG_ANALYZER) {
/*  152 */               System.out.println("alt " + k + " has a sem pred");
/*      */             }
/*      */ 
/*      */           }
/*  160 */           else if (altUsesWildcardDefault((Alternative)localObject3))
/*      */           {
/*  163 */             localObject1 = localObject3;
/*      */           }
/*  170 */           else if ((paramAlternativeBlock.warnWhenFollowAmbig) || ((!(((Alternative)localObject2).head instanceof BlockEndElement)) && (!(((Alternative)localObject3).head instanceof BlockEndElement))))
/*      */           {
/*  180 */             if (paramAlternativeBlock.generateAmbigWarnings)
/*      */             {
/*  184 */               if ((!paramAlternativeBlock.greedySet) || (!paramAlternativeBlock.greedy) || (((!(((Alternative)localObject2).head instanceof BlockEndElement)) || ((((Alternative)localObject3).head instanceof BlockEndElement))) && ((!(((Alternative)localObject3).head instanceof BlockEndElement)) || ((((Alternative)localObject2).head instanceof BlockEndElement)))))
/*      */               {
/*  195 */                 this.tool.errorHandler.warnAltAmbiguity(this.grammar, paramAlternativeBlock, this.lexicalAnalysis, this.grammar.maxk, arrayOfLookahead, k, m);
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  208 */           ((Alternative)localObject2).lookaheadDepth = Math.max(((Alternative)localObject2).lookaheadDepth, i);
/*  209 */           ((Alternative)localObject3).lookaheadDepth = Math.max(((Alternative)localObject3).lookaheadDepth, i);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  223 */     this.currentBlock = localAlternativeBlock;
/*  224 */     return bool;
/*      */   }
/*      */ 
/*      */   public boolean deterministic(OneOrMoreBlock paramOneOrMoreBlock)
/*      */   {
/*  231 */     if (this.DEBUG_ANALYZER) System.out.println("deterministic(...)+(" + paramOneOrMoreBlock + ")");
/*  232 */     AlternativeBlock localAlternativeBlock = this.currentBlock;
/*  233 */     this.currentBlock = paramOneOrMoreBlock;
/*  234 */     boolean bool1 = deterministic(paramOneOrMoreBlock);
/*      */ 
/*  237 */     boolean bool2 = deterministicImpliedPath(paramOneOrMoreBlock);
/*  238 */     this.currentBlock = localAlternativeBlock;
/*  239 */     return (bool2) && (bool1);
/*      */   }
/*      */ 
/*      */   public boolean deterministic(ZeroOrMoreBlock paramZeroOrMoreBlock)
/*      */   {
/*  246 */     if (this.DEBUG_ANALYZER) System.out.println("deterministic(...)*(" + paramZeroOrMoreBlock + ")");
/*  247 */     AlternativeBlock localAlternativeBlock = this.currentBlock;
/*  248 */     this.currentBlock = paramZeroOrMoreBlock;
/*  249 */     boolean bool1 = deterministic(paramZeroOrMoreBlock);
/*      */ 
/*  252 */     boolean bool2 = deterministicImpliedPath(paramZeroOrMoreBlock);
/*  253 */     this.currentBlock = localAlternativeBlock;
/*  254 */     return (bool2) && (bool1);
/*      */   }
/*      */ 
/*      */   public boolean deterministicImpliedPath(BlockWithImpliedExitPath paramBlockWithImpliedExitPath)
/*      */   {
/*  263 */     boolean bool = true;
/*  264 */     Vector localVector = paramBlockWithImpliedExitPath.getAlternatives();
/*  265 */     int j = localVector.size();
/*  266 */     this.currentBlock.altj = -1;
/*      */ 
/*  268 */     if (this.DEBUG_ANALYZER) System.out.println("deterministicImpliedPath");
/*  269 */     for (int k = 0; k < j; k++) {
/*  270 */       Alternative localAlternative = paramBlockWithImpliedExitPath.getAlternativeAt(k);
/*      */ 
/*  272 */       if ((localAlternative.head instanceof BlockEndElement)) {
/*  273 */         this.tool.warning("empty alternative makes no sense in (...)* or (...)+", this.grammar.getFilename(), paramBlockWithImpliedExitPath.getLine(), paramBlockWithImpliedExitPath.getColumn()); } 
/*      */ int i = 1;
/*      */ 
/*  279 */       Lookahead[] arrayOfLookahead = new Lookahead[this.grammar.maxk + 1];
/*      */       int m;
/*      */       Object localObject;
/*      */       do { m = 0;
/*  283 */         if (this.DEBUG_ANALYZER) System.out.println("checking depth " + i + "<=" + this.grammar.maxk);
/*      */ 
/*  285 */         Lookahead localLookahead = paramBlockWithImpliedExitPath.next.look(i);
/*  286 */         paramBlockWithImpliedExitPath.exitCache[i] = localLookahead;
/*  287 */         this.currentBlock.alti = k;
/*  288 */         localObject = getAltLookahead(paramBlockWithImpliedExitPath, k, i);
/*      */ 
/*  290 */         if (this.DEBUG_ANALYZER) System.out.println("follow is " + localLookahead.toString(",", this.charFormatter, this.grammar));
/*  291 */         if (this.DEBUG_ANALYZER) System.out.println("p is " + ((Lookahead)localObject).toString(",", this.charFormatter, this.grammar));
/*      */ 
/*  293 */         arrayOfLookahead[i] = localLookahead.intersection((Lookahead)localObject);
/*  294 */         if (this.DEBUG_ANALYZER) System.out.println("intersection at depth " + i + " is " + arrayOfLookahead[i]);
/*  295 */         if (!arrayOfLookahead[i].nil()) {
/*  296 */           m = 1;
/*  297 */           i++;
/*      */         }
/*      */       }
/*  300 */       while ((m != 0) && (i <= this.grammar.maxk));
/*      */ 
/*  302 */       if (m != 0) {
/*  303 */         bool = false;
/*  304 */         localAlternative.lookaheadDepth = 2147483647;
/*  305 */         paramBlockWithImpliedExitPath.exitLookaheadDepth = 2147483647;
/*  306 */         localObject = paramBlockWithImpliedExitPath.getAlternativeAt(this.currentBlock.alti);
/*      */ 
/*  311 */         if (paramBlockWithImpliedExitPath.warnWhenFollowAmbig)
/*      */         {
/*  317 */           if (paramBlockWithImpliedExitPath.generateAmbigWarnings)
/*      */           {
/*  321 */             if ((paramBlockWithImpliedExitPath.greedy == true) && (paramBlockWithImpliedExitPath.greedySet) && (!(((Alternative)localObject).head instanceof BlockEndElement)))
/*      */             {
/*  323 */               if (this.DEBUG_ANALYZER) System.out.println("greedy loop");
/*      */ 
/*      */             }
/*  330 */             else if ((!paramBlockWithImpliedExitPath.greedy) && (!(((Alternative)localObject).head instanceof BlockEndElement)))
/*      */             {
/*  332 */               if (this.DEBUG_ANALYZER) System.out.println("nongreedy loop");
/*      */ 
/*  337 */               if (!lookaheadEquivForApproxAndFullAnalysis(paramBlockWithImpliedExitPath.exitCache, this.grammar.maxk)) {
/*  338 */                 this.tool.warning(new String[] { "nongreedy block may exit incorrectly due", "\tto limitations of linear approximate lookahead (first k-1 sets", "\tin lookahead not singleton)." }, this.grammar.getFilename(), paramBlockWithImpliedExitPath.getLine(), paramBlockWithImpliedExitPath.getColumn());
/*      */               }
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/*  348 */               this.tool.errorHandler.warnAltExitAmbiguity(this.grammar, paramBlockWithImpliedExitPath, this.lexicalAnalysis, this.grammar.maxk, arrayOfLookahead, k);
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  359 */         localAlternative.lookaheadDepth = Math.max(localAlternative.lookaheadDepth, i);
/*  360 */         paramBlockWithImpliedExitPath.exitLookaheadDepth = Math.max(paramBlockWithImpliedExitPath.exitLookaheadDepth, i);
/*      */       }
/*      */     }
/*  363 */     return bool;
/*      */   }
/*      */ 
/*      */   public Lookahead FOLLOW(int paramInt, RuleEndElement paramRuleEndElement)
/*      */   {
/*  371 */     RuleBlock localRuleBlock = (RuleBlock)paramRuleEndElement.block;
/*      */     String str;
/*  374 */     if (this.lexicalAnalysis) {
/*  375 */       str = CodeGenerator.encodeLexerRuleName(localRuleBlock.getRuleName());
/*      */     }
/*      */     else {
/*  378 */       str = localRuleBlock.getRuleName();
/*      */     }
/*      */ 
/*  381 */     if (this.DEBUG_ANALYZER) System.out.println("FOLLOW(" + paramInt + "," + str + ")");
/*      */ 
/*  384 */     if (paramRuleEndElement.lock[paramInt] != 0) {
/*  385 */       if (this.DEBUG_ANALYZER) System.out.println("FOLLOW cycle to " + str);
/*  386 */       return new Lookahead(str);
/*      */     }
/*      */ 
/*  390 */     if (paramRuleEndElement.cache[paramInt] != null) {
/*  391 */       if (this.DEBUG_ANALYZER) {
/*  392 */         System.out.println("cache entry FOLLOW(" + paramInt + ") for " + str + ": " + paramRuleEndElement.cache[paramInt].toString(",", this.charFormatter, this.grammar));
/*      */       }
/*      */ 
/*  395 */       if (paramRuleEndElement.cache[paramInt].cycle == null) {
/*  396 */         return (Lookahead)paramRuleEndElement.cache[paramInt].clone();
/*      */       }
/*      */ 
/*  399 */       localObject1 = (RuleSymbol)this.grammar.getSymbol(paramRuleEndElement.cache[paramInt].cycle);
/*  400 */       localObject2 = ((RuleSymbol)localObject1).getBlock().endNode;
/*      */ 
/*  403 */       if (localObject2.cache[paramInt] == null)
/*      */       {
/*  405 */         return (Lookahead)paramRuleEndElement.cache[paramInt].clone();
/*      */       }
/*      */ 
/*  408 */       if (this.DEBUG_ANALYZER) {
/*  409 */         System.out.println("combining FOLLOW(" + paramInt + ") for " + str + ": from " + paramRuleEndElement.cache[paramInt].toString(",", this.charFormatter, this.grammar) + " with FOLLOW for " + ((RuleBlock)((RuleEndElement)localObject2).block).getRuleName() + ": " + localObject2.cache[paramInt].toString(",", this.charFormatter, this.grammar));
/*      */       }
/*      */ 
/*  412 */       if (localObject2.cache[paramInt].cycle == null)
/*      */       {
/*  416 */         paramRuleEndElement.cache[paramInt].combineWith(localObject2.cache[paramInt]);
/*  417 */         paramRuleEndElement.cache[paramInt].cycle = null;
/*      */       }
/*      */       else
/*      */       {
/*  424 */         Lookahead localLookahead1 = FOLLOW(paramInt, (RuleEndElement)localObject2);
/*  425 */         paramRuleEndElement.cache[paramInt].combineWith(localLookahead1);
/*      */ 
/*  427 */         paramRuleEndElement.cache[paramInt].cycle = localLookahead1.cycle;
/*      */       }
/*  429 */       if (this.DEBUG_ANALYZER) {
/*  430 */         System.out.println("saving FOLLOW(" + paramInt + ") for " + str + ": from " + paramRuleEndElement.cache[paramInt].toString(",", this.charFormatter, this.grammar));
/*      */       }
/*      */ 
/*  434 */       return (Lookahead)paramRuleEndElement.cache[paramInt].clone();
/*      */     }
/*      */ 
/*  438 */     paramRuleEndElement.lock[paramInt] = true;
/*      */ 
/*  440 */     Object localObject1 = new Lookahead();
/*      */ 
/*  442 */     Object localObject2 = (RuleSymbol)this.grammar.getSymbol(str);
/*      */ 
/*  445 */     for (int i = 0; i < ((RuleSymbol)localObject2).numReferences(); i++) {
/*  446 */       RuleRefElement localRuleRefElement = ((RuleSymbol)localObject2).getReference(i);
/*  447 */       if (this.DEBUG_ANALYZER) System.out.println("next[" + str + "] is " + localRuleRefElement.next.toString());
/*  448 */       Lookahead localLookahead2 = localRuleRefElement.next.look(paramInt);
/*  449 */       if (this.DEBUG_ANALYZER) System.out.println("FIRST of next[" + str + "] ptr is " + localLookahead2.toString());
/*      */ 
/*  454 */       if ((localLookahead2.cycle != null) && (localLookahead2.cycle.equals(str))) {
/*  455 */         localLookahead2.cycle = null;
/*      */       }
/*      */ 
/*  458 */       ((Lookahead)localObject1).combineWith(localLookahead2);
/*  459 */       if (this.DEBUG_ANALYZER) System.out.println("combined FOLLOW[" + str + "] is " + ((Lookahead)localObject1).toString());
/*      */     }
/*      */ 
/*  462 */     paramRuleEndElement.lock[paramInt] = false;
/*      */ 
/*  466 */     if ((((Lookahead)localObject1).fset.nil()) && (((Lookahead)localObject1).cycle == null)) {
/*  467 */       if ((this.grammar instanceof TreeWalkerGrammar))
/*      */       {
/*  470 */         ((Lookahead)localObject1).fset.add(3);
/*      */       }
/*  472 */       else if ((this.grammar instanceof LexerGrammar))
/*      */       {
/*  479 */         ((Lookahead)localObject1).setEpsilon();
/*      */       }
/*      */       else {
/*  482 */         ((Lookahead)localObject1).fset.add(1);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  487 */     if (this.DEBUG_ANALYZER) {
/*  488 */       System.out.println("saving FOLLOW(" + paramInt + ") for " + str + ": " + ((Lookahead)localObject1).toString(",", this.charFormatter, this.grammar));
/*      */     }
/*  490 */     paramRuleEndElement.cache[paramInt] = ((Lookahead)((Lookahead)localObject1).clone());
/*      */ 
/*  492 */     return localObject1;
/*      */   }
/*      */ 
/*      */   private Lookahead getAltLookahead(AlternativeBlock paramAlternativeBlock, int paramInt1, int paramInt2)
/*      */   {
/*  497 */     Alternative localAlternative = paramAlternativeBlock.getAlternativeAt(paramInt1);
/*  498 */     AlternativeElement localAlternativeElement = localAlternative.head;
/*      */     Lookahead localLookahead;
/*  500 */     if (localAlternative.cache[paramInt2] == null) {
/*  501 */       localLookahead = localAlternativeElement.look(paramInt2);
/*  502 */       localAlternative.cache[paramInt2] = localLookahead;
/*      */     }
/*      */     else {
/*  505 */       localLookahead = localAlternative.cache[paramInt2];
/*      */     }
/*  507 */     return localLookahead;
/*      */   }
/*      */ 
/*      */   public Lookahead look(int paramInt, ActionElement paramActionElement)
/*      */   {
/*  512 */     if (this.DEBUG_ANALYZER) System.out.println("lookAction(" + paramInt + "," + paramActionElement + ")");
/*  513 */     return paramActionElement.next.look(paramInt);
/*      */   }
/*      */ 
/*      */   public Lookahead look(int paramInt, AlternativeBlock paramAlternativeBlock)
/*      */   {
/*  518 */     if (this.DEBUG_ANALYZER) System.out.println("lookAltBlk(" + paramInt + "," + paramAlternativeBlock + ")");
/*  519 */     AlternativeBlock localAlternativeBlock = this.currentBlock;
/*  520 */     this.currentBlock = paramAlternativeBlock;
/*  521 */     Lookahead localLookahead1 = new Lookahead();
/*      */     Object localObject;
/*  522 */     for (int i = 0; i < paramAlternativeBlock.alternatives.size(); i++) {
/*  523 */       if (this.DEBUG_ANALYZER) System.out.println("alt " + i + " of " + paramAlternativeBlock);
/*      */ 
/*  525 */       this.currentBlock.analysisAlt = i;
/*  526 */       localObject = paramAlternativeBlock.getAlternativeAt(i);
/*  527 */       AlternativeElement localAlternativeElement = ((Alternative)localObject).head;
/*  528 */       if ((this.DEBUG_ANALYZER) && 
/*  529 */         (((Alternative)localObject).head == ((Alternative)localObject).tail)) {
/*  530 */         System.out.println("alt " + i + " is empty");
/*      */       }
/*      */ 
/*  533 */       Lookahead localLookahead2 = localAlternativeElement.look(paramInt);
/*  534 */       localLookahead1.combineWith(localLookahead2);
/*      */     }
/*  536 */     if ((paramInt == 1) && (paramAlternativeBlock.not) && (subruleCanBeInverted(paramAlternativeBlock, this.lexicalAnalysis)))
/*      */     {
/*  538 */       if (this.lexicalAnalysis) {
/*  539 */         BitSet localBitSet = (BitSet)((LexerGrammar)this.grammar).charVocabulary.clone();
/*  540 */         localObject = localLookahead1.fset.toArray();
/*  541 */         for (int j = 0; j < localObject.length; j++) {
/*  542 */           localBitSet.remove(localObject[j]);
/*      */         }
/*  544 */         localLookahead1.fset = localBitSet;
/*      */       }
/*      */       else {
/*  547 */         localLookahead1.fset.notInPlace(4, this.grammar.tokenManager.maxTokenType());
/*      */       }
/*      */     }
/*  550 */     this.currentBlock = localAlternativeBlock;
/*  551 */     return localLookahead1;
/*      */   }
/*      */ 
/*      */   public Lookahead look(int paramInt, BlockEndElement paramBlockEndElement)
/*      */   {
/*  567 */     if (this.DEBUG_ANALYZER) System.out.println("lookBlockEnd(" + paramInt + ", " + paramBlockEndElement.block + "); lock is " + paramBlockEndElement.lock[paramInt]);
/*  568 */     if (paramBlockEndElement.lock[paramInt] != 0)
/*      */     {
/*  573 */       return new Lookahead();
/*      */     }
/*      */     Lookahead localLookahead1;
/*  579 */     if (((paramBlockEndElement.block instanceof ZeroOrMoreBlock)) || ((paramBlockEndElement.block instanceof OneOrMoreBlock)))
/*      */     {
/*  584 */       paramBlockEndElement.lock[paramInt] = true;
/*  585 */       localLookahead1 = look(paramInt, paramBlockEndElement.block);
/*  586 */       paramBlockEndElement.lock[paramInt] = false;
/*      */     }
/*      */     else {
/*  589 */       localLookahead1 = new Lookahead();
/*      */     }
/*      */ 
/*  597 */     if ((paramBlockEndElement.block instanceof TreeElement)) {
/*  598 */       localLookahead1.combineWith(Lookahead.of(3));
/*      */     }
/*  608 */     else if ((paramBlockEndElement.block instanceof SynPredBlock)) {
/*  609 */       localLookahead1.setEpsilon();
/*      */     }
/*      */     else
/*      */     {
/*  614 */       Lookahead localLookahead2 = paramBlockEndElement.block.next.look(paramInt);
/*  615 */       localLookahead1.combineWith(localLookahead2);
/*      */     }
/*      */ 
/*  618 */     return localLookahead1;
/*      */   }
/*      */ 
/*      */   public Lookahead look(int paramInt, CharLiteralElement paramCharLiteralElement)
/*      */   {
/*  641 */     if (this.DEBUG_ANALYZER) System.out.println("lookCharLiteral(" + paramInt + "," + paramCharLiteralElement + ")");
/*      */ 
/*  643 */     if (paramInt > 1) {
/*  644 */       return paramCharLiteralElement.next.look(paramInt - 1);
/*      */     }
/*  646 */     if (this.lexicalAnalysis) {
/*  647 */       if (paramCharLiteralElement.not) {
/*  648 */         BitSet localBitSet = (BitSet)((LexerGrammar)this.grammar).charVocabulary.clone();
/*  649 */         if (this.DEBUG_ANALYZER) System.out.println("charVocab is " + localBitSet.toString());
/*      */ 
/*  651 */         removeCompetingPredictionSets(localBitSet, paramCharLiteralElement);
/*  652 */         if (this.DEBUG_ANALYZER) System.out.println("charVocab after removal of prior alt lookahead " + localBitSet.toString());
/*      */ 
/*  654 */         localBitSet.clear(paramCharLiteralElement.getType());
/*  655 */         return new Lookahead(localBitSet);
/*      */       }
/*      */ 
/*  658 */       return Lookahead.of(paramCharLiteralElement.getType());
/*      */     }
/*      */ 
/*  663 */     this.tool.panic("Character literal reference found in parser");
/*      */ 
/*  665 */     return Lookahead.of(paramCharLiteralElement.getType());
/*      */   }
/*      */ 
/*      */   public Lookahead look(int paramInt, CharRangeElement paramCharRangeElement)
/*      */   {
/*  670 */     if (this.DEBUG_ANALYZER) System.out.println("lookCharRange(" + paramInt + "," + paramCharRangeElement + ")");
/*      */ 
/*  672 */     if (paramInt > 1) {
/*  673 */       return paramCharRangeElement.next.look(paramInt - 1);
/*      */     }
/*  675 */     BitSet localBitSet = BitSet.of(paramCharRangeElement.begin);
/*  676 */     for (int i = paramCharRangeElement.begin + '\001'; i <= paramCharRangeElement.end; i++) {
/*  677 */       localBitSet.add(i);
/*      */     }
/*  679 */     return new Lookahead(localBitSet);
/*      */   }
/*      */ 
/*      */   public Lookahead look(int paramInt, GrammarAtom paramGrammarAtom) {
/*  683 */     if (this.DEBUG_ANALYZER) System.out.println("look(" + paramInt + "," + paramGrammarAtom + "[" + paramGrammarAtom.getType() + "])");
/*      */ 
/*  685 */     if (this.lexicalAnalysis)
/*      */     {
/*  687 */       this.tool.panic("token reference found in lexer");
/*      */     }
/*      */ 
/*  690 */     if (paramInt > 1) {
/*  691 */       return paramGrammarAtom.next.look(paramInt - 1);
/*      */     }
/*  693 */     Lookahead localLookahead = Lookahead.of(paramGrammarAtom.getType());
/*  694 */     if (paramGrammarAtom.not)
/*      */     {
/*  696 */       int i = this.grammar.tokenManager.maxTokenType();
/*  697 */       localLookahead.fset.notInPlace(4, i);
/*      */ 
/*  699 */       removeCompetingPredictionSets(localLookahead.fset, paramGrammarAtom);
/*      */     }
/*  701 */     return localLookahead;
/*      */   }
/*      */ 
/*      */   public Lookahead look(int paramInt, OneOrMoreBlock paramOneOrMoreBlock)
/*      */   {
/*  709 */     if (this.DEBUG_ANALYZER) System.out.println("look+" + paramInt + "," + paramOneOrMoreBlock + ")");
/*  710 */     Lookahead localLookahead = look(paramInt, paramOneOrMoreBlock);
/*  711 */     return localLookahead;
/*      */   }
/*      */ 
/*      */   public Lookahead look(int paramInt, RuleBlock paramRuleBlock)
/*      */   {
/*  720 */     if (this.DEBUG_ANALYZER) System.out.println("lookRuleBlk(" + paramInt + "," + paramRuleBlock + ")");
/*  721 */     Lookahead localLookahead = look(paramInt, paramRuleBlock);
/*  722 */     return localLookahead;
/*      */   }
/*      */ 
/*      */   public Lookahead look(int paramInt, RuleEndElement paramRuleEndElement)
/*      */   {
/*  752 */     if (this.DEBUG_ANALYZER) {
/*  753 */       System.out.println("lookRuleBlockEnd(" + paramInt + "); noFOLLOW=" + paramRuleEndElement.noFOLLOW + "; lock is " + paramRuleEndElement.lock[paramInt]);
/*      */     }
/*  755 */     if (paramRuleEndElement.noFOLLOW) {
/*  756 */       localLookahead = new Lookahead();
/*  757 */       localLookahead.setEpsilon();
/*  758 */       localLookahead.epsilonDepth = BitSet.of(paramInt);
/*  759 */       return localLookahead;
/*      */     }
/*  761 */     Lookahead localLookahead = FOLLOW(paramInt, paramRuleEndElement);
/*  762 */     return localLookahead;
/*      */   }
/*      */ 
/*      */   public Lookahead look(int paramInt, RuleRefElement paramRuleRefElement)
/*      */   {
/*  782 */     if (this.DEBUG_ANALYZER) System.out.println("lookRuleRef(" + paramInt + "," + paramRuleRefElement + ")");
/*  783 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(paramRuleRefElement.targetRule);
/*  784 */     if ((localRuleSymbol == null) || (!localRuleSymbol.defined)) {
/*  785 */       this.tool.error("no definition of rule " + paramRuleRefElement.targetRule, this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*  786 */       return new Lookahead();
/*      */     }
/*  788 */     RuleBlock localRuleBlock = localRuleSymbol.getBlock();
/*  789 */     RuleEndElement localRuleEndElement = localRuleBlock.endNode;
/*  790 */     boolean bool = localRuleEndElement.noFOLLOW;
/*  791 */     localRuleEndElement.noFOLLOW = true;
/*      */ 
/*  793 */     Lookahead localLookahead1 = look(paramInt, paramRuleRefElement.targetRule);
/*  794 */     if (this.DEBUG_ANALYZER) System.out.println("back from rule ref to " + paramRuleRefElement.targetRule);
/*      */ 
/*  796 */     localRuleEndElement.noFOLLOW = bool;
/*      */ 
/*  799 */     if (localLookahead1.cycle != null) {
/*  800 */       this.tool.error("infinite recursion to rule " + localLookahead1.cycle + " from rule " + paramRuleRefElement.enclosingRuleName, this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*      */     }
/*      */ 
/*  805 */     if (localLookahead1.containsEpsilon()) {
/*  806 */       if (this.DEBUG_ANALYZER) {
/*  807 */         System.out.println("rule ref to " + paramRuleRefElement.targetRule + " has eps, depth: " + localLookahead1.epsilonDepth);
/*      */       }
/*      */ 
/*  811 */       localLookahead1.resetEpsilon();
/*      */ 
/*  815 */       int[] arrayOfInt = localLookahead1.epsilonDepth.toArray();
/*  816 */       localLookahead1.epsilonDepth = null;
/*  817 */       for (int i = 0; i < arrayOfInt.length; i++) {
/*  818 */         int j = paramInt - (paramInt - arrayOfInt[i]);
/*  819 */         Lookahead localLookahead2 = paramRuleRefElement.next.look(j);
/*  820 */         localLookahead1.combineWith(localLookahead2);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  826 */     return localLookahead1;
/*      */   }
/*      */ 
/*      */   public Lookahead look(int paramInt, StringLiteralElement paramStringLiteralElement) {
/*  830 */     if (this.DEBUG_ANALYZER) System.out.println("lookStringLiteral(" + paramInt + "," + paramStringLiteralElement + ")");
/*  831 */     if (this.lexicalAnalysis)
/*      */     {
/*  833 */       if (paramInt > paramStringLiteralElement.processedAtomText.length()) {
/*  834 */         return paramStringLiteralElement.next.look(paramInt - paramStringLiteralElement.processedAtomText.length());
/*      */       }
/*      */ 
/*  838 */       return Lookahead.of(paramStringLiteralElement.processedAtomText.charAt(paramInt - 1));
/*      */     }
/*      */ 
/*  843 */     if (paramInt > 1) {
/*  844 */       return paramStringLiteralElement.next.look(paramInt - 1);
/*      */     }
/*  846 */     Lookahead localLookahead = Lookahead.of(paramStringLiteralElement.getType());
/*  847 */     if (paramStringLiteralElement.not)
/*      */     {
/*  849 */       int i = this.grammar.tokenManager.maxTokenType();
/*  850 */       localLookahead.fset.notInPlace(4, i);
/*      */     }
/*  852 */     return localLookahead;
/*      */   }
/*      */ 
/*      */   public Lookahead look(int paramInt, SynPredBlock paramSynPredBlock)
/*      */   {
/*  863 */     if (this.DEBUG_ANALYZER) System.out.println("look=>(" + paramInt + "," + paramSynPredBlock + ")");
/*  864 */     return paramSynPredBlock.next.look(paramInt);
/*      */   }
/*      */ 
/*      */   public Lookahead look(int paramInt, TokenRangeElement paramTokenRangeElement) {
/*  868 */     if (this.DEBUG_ANALYZER) System.out.println("lookTokenRange(" + paramInt + "," + paramTokenRangeElement + ")");
/*      */ 
/*  870 */     if (paramInt > 1) {
/*  871 */       return paramTokenRangeElement.next.look(paramInt - 1);
/*      */     }
/*  873 */     BitSet localBitSet = BitSet.of(paramTokenRangeElement.begin);
/*  874 */     for (int i = paramTokenRangeElement.begin + 1; i <= paramTokenRangeElement.end; i++) {
/*  875 */       localBitSet.add(i);
/*      */     }
/*  877 */     return new Lookahead(localBitSet);
/*      */   }
/*      */ 
/*      */   public Lookahead look(int paramInt, TreeElement paramTreeElement) {
/*  881 */     if (this.DEBUG_ANALYZER)
/*  882 */       System.out.println("look(" + paramInt + "," + paramTreeElement.root + "[" + paramTreeElement.root.getType() + "])");
/*  883 */     if (paramInt > 1) {
/*  884 */       return paramTreeElement.next.look(paramInt - 1);
/*      */     }
/*  886 */     Lookahead localLookahead = null;
/*  887 */     if ((paramTreeElement.root instanceof WildcardElement)) {
/*  888 */       localLookahead = paramTreeElement.root.look(1);
/*      */     }
/*      */     else {
/*  891 */       localLookahead = Lookahead.of(paramTreeElement.root.getType());
/*  892 */       if (paramTreeElement.root.not)
/*      */       {
/*  894 */         int i = this.grammar.tokenManager.maxTokenType();
/*  895 */         localLookahead.fset.notInPlace(4, i);
/*      */       }
/*      */     }
/*  898 */     return localLookahead;
/*      */   }
/*      */ 
/*      */   public Lookahead look(int paramInt, WildcardElement paramWildcardElement) {
/*  902 */     if (this.DEBUG_ANALYZER) System.out.println("look(" + paramInt + "," + paramWildcardElement + ")");
/*      */ 
/*  905 */     if (paramInt > 1)
/*  906 */       return paramWildcardElement.next.look(paramInt - 1);
/*      */     BitSet localBitSet;
/*  910 */     if (this.lexicalAnalysis)
/*      */     {
/*  912 */       localBitSet = (BitSet)((LexerGrammar)this.grammar).charVocabulary.clone();
/*      */     }
/*      */     else {
/*  915 */       localBitSet = new BitSet(1);
/*      */ 
/*  917 */       int i = this.grammar.tokenManager.maxTokenType();
/*  918 */       localBitSet.notInPlace(4, i);
/*  919 */       if (this.DEBUG_ANALYZER) System.out.println("look(" + paramInt + "," + paramWildcardElement + ") after not: " + localBitSet);
/*      */ 
/*      */     }
/*      */ 
/*  925 */     return new Lookahead(localBitSet);
/*      */   }
/*      */ 
/*      */   public Lookahead look(int paramInt, ZeroOrMoreBlock paramZeroOrMoreBlock)
/*      */   {
/*  932 */     if (this.DEBUG_ANALYZER) System.out.println("look*(" + paramInt + "," + paramZeroOrMoreBlock + ")");
/*  933 */     Lookahead localLookahead1 = look(paramInt, paramZeroOrMoreBlock);
/*  934 */     Lookahead localLookahead2 = paramZeroOrMoreBlock.next.look(paramInt);
/*  935 */     localLookahead1.combineWith(localLookahead2);
/*  936 */     return localLookahead1;
/*      */   }
/*      */ 
/*      */   public Lookahead look(int paramInt, String paramString)
/*      */   {
/*  949 */     if (this.DEBUG_ANALYZER) System.out.println("lookRuleName(" + paramInt + "," + paramString + ")");
/*  950 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(paramString);
/*  951 */     RuleBlock localRuleBlock = localRuleSymbol.getBlock();
/*      */ 
/*  953 */     if (localRuleBlock.lock[paramInt] != 0) {
/*  954 */       if (this.DEBUG_ANALYZER)
/*  955 */         System.out.println("infinite recursion to rule " + localRuleBlock.getRuleName());
/*  956 */       return new Lookahead(paramString);
/*      */     }
/*      */ 
/*  960 */     if (localRuleBlock.cache[paramInt] != null) {
/*  961 */       if (this.DEBUG_ANALYZER) {
/*  962 */         System.out.println("found depth " + paramInt + " result in FIRST " + paramString + " cache: " + localRuleBlock.cache[paramInt].toString(",", this.charFormatter, this.grammar));
/*      */       }
/*      */ 
/*  965 */       return (Lookahead)localRuleBlock.cache[paramInt].clone();
/*      */     }
/*      */ 
/*  968 */     localRuleBlock.lock[paramInt] = true;
/*  969 */     Lookahead localLookahead = look(paramInt, localRuleBlock);
/*  970 */     localRuleBlock.lock[paramInt] = false;
/*      */ 
/*  973 */     localRuleBlock.cache[paramInt] = ((Lookahead)localLookahead.clone());
/*  974 */     if (this.DEBUG_ANALYZER) {
/*  975 */       System.out.println("saving depth " + paramInt + " result in FIRST " + paramString + " cache: " + localRuleBlock.cache[paramInt].toString(",", this.charFormatter, this.grammar));
/*      */     }
/*      */ 
/*  978 */     return localLookahead;
/*      */   }
/*      */ 
/*      */   public static boolean lookaheadEquivForApproxAndFullAnalysis(Lookahead[] paramArrayOfLookahead, int paramInt)
/*      */   {
/*  986 */     for (int i = 1; i <= paramInt - 1; i++) {
/*  987 */       BitSet localBitSet = paramArrayOfLookahead[i].fset;
/*  988 */       if (localBitSet.degree() > 1) {
/*  989 */         return false;
/*      */       }
/*      */     }
/*  992 */     return true;
/*      */   }
/*      */ 
/*      */   private void removeCompetingPredictionSets(BitSet paramBitSet, AlternativeElement paramAlternativeElement)
/*      */   {
/* 1005 */     AlternativeElement localAlternativeElement1 = this.currentBlock.getAlternativeAt(this.currentBlock.analysisAlt).head;
/*      */ 
/* 1007 */     if ((localAlternativeElement1 instanceof TreeElement))
/*      */     {
/* 1008 */       if (((TreeElement)localAlternativeElement1).root == paramAlternativeElement);
/*      */     }
/* 1012 */     else if (paramAlternativeElement != localAlternativeElement1) {
/* 1013 */       return;
/*      */     }
/* 1015 */     for (int i = 0; i < this.currentBlock.analysisAlt; i++) {
/* 1016 */       AlternativeElement localAlternativeElement2 = this.currentBlock.getAlternativeAt(i).head;
/* 1017 */       paramBitSet.subtractInPlace(localAlternativeElement2.look(1).fset);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void removeCompetingPredictionSetsFromWildcard(Lookahead[] paramArrayOfLookahead, AlternativeElement paramAlternativeElement, int paramInt)
/*      */   {
/* 1029 */     for (int i = 1; i <= paramInt; i++)
/* 1030 */       for (int j = 0; j < this.currentBlock.analysisAlt; j++) {
/* 1031 */         AlternativeElement localAlternativeElement = this.currentBlock.getAlternativeAt(j).head;
/* 1032 */         paramArrayOfLookahead[i].fset.subtractInPlace(localAlternativeElement.look(i).fset);
/*      */       }
/*      */   }
/*      */ 
/*      */   private void reset()
/*      */   {
/* 1039 */     this.grammar = null;
/* 1040 */     this.DEBUG_ANALYZER = false;
/* 1041 */     this.currentBlock = null;
/* 1042 */     this.lexicalAnalysis = false;
/*      */   }
/*      */ 
/*      */   public void setGrammar(Grammar paramGrammar)
/*      */   {
/* 1047 */     if (this.grammar != null) {
/* 1048 */       reset();
/*      */     }
/* 1050 */     this.grammar = paramGrammar;
/*      */ 
/* 1053 */     this.lexicalAnalysis = (this.grammar instanceof LexerGrammar);
/* 1054 */     this.DEBUG_ANALYZER = this.grammar.analyzerDebug;
/*      */   }
/*      */ 
/*      */   public boolean subruleCanBeInverted(AlternativeBlock paramAlternativeBlock, boolean paramBoolean) {
/* 1058 */     if (((paramAlternativeBlock instanceof ZeroOrMoreBlock)) || ((paramAlternativeBlock instanceof OneOrMoreBlock)) || ((paramAlternativeBlock instanceof SynPredBlock)))
/*      */     {
/* 1063 */       return false;
/*      */     }
/*      */ 
/* 1066 */     if (paramAlternativeBlock.alternatives.size() == 0) {
/* 1067 */       return false;
/*      */     }
/*      */ 
/* 1071 */     for (int i = 0; i < paramAlternativeBlock.alternatives.size(); i++) {
/* 1072 */       Alternative localAlternative = paramAlternativeBlock.getAlternativeAt(i);
/*      */ 
/* 1074 */       if ((localAlternative.synPred != null) || (localAlternative.semPred != null) || (localAlternative.exceptionSpec != null)) {
/* 1075 */         return false;
/*      */       }
/*      */ 
/* 1078 */       AlternativeElement localAlternativeElement = localAlternative.head;
/* 1079 */       if (((!(localAlternativeElement instanceof CharLiteralElement)) && (!(localAlternativeElement instanceof TokenRefElement)) && (!(localAlternativeElement instanceof CharRangeElement)) && (!(localAlternativeElement instanceof TokenRangeElement)) && ((!(localAlternativeElement instanceof StringLiteralElement)) || (paramBoolean))) || (!(localAlternativeElement.next instanceof BlockEndElement)) || (localAlternativeElement.getAutoGenType() != 1))
/*      */       {
/* 1090 */         return false;
/*      */       }
/*      */     }
/* 1093 */     return true;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.LLkAnalyzer
 * JD-Core Version:    0.6.2
 */