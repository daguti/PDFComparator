/*     */ package org.antlr.tool;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.antlr.analysis.ActionLabel;
/*     */ import org.antlr.analysis.Label;
/*     */ import org.antlr.analysis.NFA;
/*     */ import org.antlr.analysis.NFAState;
/*     */ import org.antlr.analysis.PredicateLabel;
/*     */ import org.antlr.analysis.RuleClosureTransition;
/*     */ import org.antlr.analysis.State;
/*     */ import org.antlr.analysis.StateCluster;
/*     */ import org.antlr.analysis.Transition;
/*     */ import org.antlr.misc.IntSet;
/*     */ import org.antlr.misc.IntervalSet;
/*     */ 
/*     */ public class NFAFactory
/*     */ {
/*  49 */   NFA nfa = null;
/*     */ 
/*  59 */   Rule currentRule = null;
/*     */ 
/*     */   public Rule getCurrentRule()
/*     */   {
/*  52 */     return this.currentRule;
/*     */   }
/*     */ 
/*     */   public void setCurrentRule(Rule currentRule) {
/*  56 */     this.currentRule = currentRule;
/*     */   }
/*     */ 
/*     */   public NFAFactory(NFA nfa)
/*     */   {
/*  62 */     nfa.setFactory(this);
/*  63 */     this.nfa = nfa;
/*     */   }
/*     */ 
/*     */   public NFAState newState() {
/*  67 */     NFAState n = new NFAState(this.nfa);
/*  68 */     int state = this.nfa.getNewNFAStateNumber();
/*  69 */     n.stateNumber = state;
/*  70 */     this.nfa.addState(n);
/*  71 */     n.enclosingRule = this.currentRule;
/*  72 */     return n;
/*     */   }
/*     */ 
/*     */   public void optimizeAlternative(StateCluster alt)
/*     */   {
/*  84 */     NFAState s = alt.left;
/*  85 */     while (s != alt.right)
/*     */     {
/*  87 */       if (s.endOfBlockStateNumber != -1) {
/*  88 */         s = this.nfa.getState(s.endOfBlockStateNumber);
/*     */       }
/*     */       else {
/*  91 */         Transition t = s.transition[0];
/*  92 */         if ((t instanceof RuleClosureTransition)) {
/*  93 */           s = ((RuleClosureTransition)t).followState;
/*     */         }
/*     */         else {
/*  96 */           if ((t.label.isEpsilon()) && (!t.label.isAction()) && (s.getNumberOfTransitions() == 1))
/*     */           {
/* 101 */             NFAState epsilonTarget = (NFAState)t.target;
/* 102 */             if ((epsilonTarget.endOfBlockStateNumber == -1) && (epsilonTarget.transition[0] != null))
/*     */             {
/* 105 */               s.setTransition0(epsilonTarget.transition[0]);
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 112 */           s = (NFAState)t.target;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/* 118 */   public StateCluster build_Atom(int label, GrammarAST associatedAST) { NFAState left = newState();
/* 119 */     NFAState right = newState();
/* 120 */     left.associatedASTNode = associatedAST;
/* 121 */     right.associatedASTNode = associatedAST;
/* 122 */     transitionBetweenStates(left, right, label);
/* 123 */     StateCluster g = new StateCluster(left, right);
/* 124 */     return g; }
/*     */ 
/*     */   public StateCluster build_Atom(GrammarAST atomAST)
/*     */   {
/* 128 */     int tokenType = this.nfa.grammar.getTokenType(atomAST.getText());
/* 129 */     return build_Atom(tokenType, atomAST);
/*     */   }
/*     */ 
/*     */   public StateCluster build_Set(IntSet set, GrammarAST associatedAST)
/*     */   {
/* 136 */     NFAState left = newState();
/* 137 */     NFAState right = newState();
/* 138 */     left.associatedASTNode = associatedAST;
/* 139 */     right.associatedASTNode = associatedAST;
/* 140 */     Label label = new Label(set);
/* 141 */     Transition e = new Transition(label, right);
/* 142 */     left.addTransition(e);
/* 143 */     StateCluster g = new StateCluster(left, right);
/* 144 */     return g;
/*     */   }
/*     */ 
/*     */   public StateCluster build_Range(int a, int b)
/*     */   {
/* 163 */     NFAState left = newState();
/* 164 */     NFAState right = newState();
/* 165 */     Label label = new Label(IntervalSet.of(a, b));
/* 166 */     Transition e = new Transition(label, right);
/* 167 */     left.addTransition(e);
/* 168 */     StateCluster g = new StateCluster(left, right);
/* 169 */     return g;
/*     */   }
/*     */ 
/*     */   public StateCluster build_CharLiteralAtom(GrammarAST charLiteralAST)
/*     */   {
/* 175 */     int c = Grammar.getCharValueFromGrammarCharLiteral(charLiteralAST.getText());
/* 176 */     return build_Atom(c, charLiteralAST);
/*     */   }
/*     */ 
/*     */   public StateCluster build_CharRange(String a, String b)
/*     */   {
/* 185 */     int from = Grammar.getCharValueFromGrammarCharLiteral(a);
/* 186 */     int to = Grammar.getCharValueFromGrammarCharLiteral(b);
/* 187 */     return build_Range(from, to);
/*     */   }
/*     */ 
/*     */   public StateCluster build_StringLiteralAtom(GrammarAST stringLiteralAST)
/*     */   {
/* 197 */     if (this.nfa.grammar.type == 1) {
/* 198 */       StringBuffer chars = Grammar.getUnescapedStringFromGrammarStringLiteral(stringLiteralAST.getText());
/*     */ 
/* 200 */       NFAState first = newState();
/* 201 */       NFAState last = null;
/* 202 */       NFAState prev = first;
/* 203 */       for (int i = 0; i < chars.length(); i++) {
/* 204 */         int c = chars.charAt(i);
/* 205 */         NFAState next = newState();
/* 206 */         transitionBetweenStates(prev, next, c);
/* 207 */         prev = last = next;
/*     */       }
/* 209 */       return new StateCluster(first, last);
/*     */     }
/*     */ 
/* 213 */     int tokenType = this.nfa.grammar.getTokenType(stringLiteralAST.getText());
/* 214 */     return build_Atom(tokenType, stringLiteralAST);
/*     */   }
/*     */ 
/*     */   public StateCluster build_RuleRef(Rule refDef, NFAState ruleStart)
/*     */   {
/* 234 */     NFAState left = newState();
/*     */ 
/* 236 */     NFAState right = newState();
/*     */ 
/* 238 */     Transition e = new RuleClosureTransition(refDef, ruleStart, right);
/* 239 */     left.addTransition(e);
/* 240 */     StateCluster g = new StateCluster(left, right);
/* 241 */     return g;
/*     */   }
/*     */ 
/*     */   public StateCluster build_Epsilon()
/*     */   {
/* 246 */     NFAState left = newState();
/* 247 */     NFAState right = newState();
/* 248 */     transitionBetweenStates(left, right, -5);
/* 249 */     StateCluster g = new StateCluster(left, right);
/* 250 */     return g;
/*     */   }
/*     */ 
/*     */   public StateCluster build_SemanticPredicate(GrammarAST pred)
/*     */   {
/* 259 */     if (!pred.getText().toUpperCase().startsWith("synpred".toUpperCase()))
/*     */     {
/* 262 */       this.nfa.grammar.numberOfSemanticPredicates += 1;
/*     */     }
/* 264 */     NFAState left = newState();
/* 265 */     NFAState right = newState();
/* 266 */     Transition e = new Transition(new PredicateLabel(pred), right);
/* 267 */     left.addTransition(e);
/* 268 */     StateCluster g = new StateCluster(left, right);
/* 269 */     return g;
/*     */   }
/*     */ 
/*     */   public StateCluster build_Action(GrammarAST action)
/*     */   {
/* 278 */     NFAState left = newState();
/* 279 */     NFAState right = newState();
/* 280 */     Transition e = new Transition(new ActionLabel(action), right);
/* 281 */     left.addTransition(e);
/* 282 */     return new StateCluster(left, right);
/*     */   }
/*     */ 
/*     */   public int build_EOFStates(List rules)
/*     */   {
/* 294 */     int numberUnInvokedRules = 0;
/* 295 */     for (Iterator iterator = rules.iterator(); iterator.hasNext(); ) {
/* 296 */       Rule r = (Rule)iterator.next();
/* 297 */       NFAState endNFAState = r.stopState;
/*     */ 
/* 299 */       if (endNFAState.transition[0] == null)
/*     */       {
/* 302 */         build_EOFState(endNFAState);
/*     */ 
/* 304 */         numberUnInvokedRules++;
/*     */       }
/*     */     }
/* 307 */     return numberUnInvokedRules;
/*     */   }
/*     */ 
/*     */   private void build_EOFState(NFAState endNFAState)
/*     */   {
/* 315 */     NFAState end = newState();
/* 316 */     int label = -1;
/* 317 */     if (this.nfa.grammar.type == 1) {
/* 318 */       label = -2;
/* 319 */       end.setEOTTargetState(true);
/*     */     }
/*     */ 
/* 326 */     Transition toEnd = new Transition(label, end);
/* 327 */     endNFAState.addTransition(toEnd);
/*     */   }
/*     */ 
/*     */   public StateCluster build_AB(StateCluster A, StateCluster B)
/*     */   {
/* 336 */     if (A == null) {
/* 337 */       return B;
/*     */     }
/* 339 */     if (B == null) {
/* 340 */       return A;
/*     */     }
/* 342 */     transitionBetweenStates(A.right, B.left, -5);
/* 343 */     StateCluster g = new StateCluster(A.left, B.right);
/* 344 */     return g;
/*     */   }
/*     */ 
/*     */   public StateCluster build_AlternativeBlockFromSet(StateCluster set)
/*     */   {
/* 352 */     if (set == null) {
/* 353 */       return null;
/*     */     }
/*     */ 
/* 357 */     NFAState startOfAlt = newState();
/* 358 */     transitionBetweenStates(startOfAlt, set.left, -5);
/*     */ 
/* 360 */     return new StateCluster(startOfAlt, set.right);
/*     */   }
/*     */ 
/*     */   public StateCluster build_AlternativeBlock(List alternativeStateClusters)
/*     */   {
/* 388 */     StateCluster result = null;
/* 389 */     if ((alternativeStateClusters == null) || (alternativeStateClusters.size() == 0)) {
/* 390 */       return null;
/*     */     }
/*     */ 
/* 394 */     if (alternativeStateClusters.size() == 1)
/*     */     {
/* 396 */       StateCluster g = (StateCluster)alternativeStateClusters.get(0);
/* 397 */       NFAState startOfAlt = newState();
/* 398 */       transitionBetweenStates(startOfAlt, g.left, -5);
/*     */ 
/* 401 */       return new StateCluster(startOfAlt, g.right);
/*     */     }
/*     */ 
/* 408 */     NFAState prevAlternative = null;
/* 409 */     NFAState firstAlt = null;
/* 410 */     NFAState blockEndNFAState = newState();
/* 411 */     blockEndNFAState.setDescription("end block");
/* 412 */     int altNum = 1;
/* 413 */     for (Iterator iter = alternativeStateClusters.iterator(); iter.hasNext(); ) {
/* 414 */       StateCluster g = (StateCluster)iter.next();
/*     */ 
/* 416 */       NFAState left = newState();
/* 417 */       left.setDescription("alt " + altNum + " of ()");
/* 418 */       transitionBetweenStates(left, g.left, -5);
/* 419 */       transitionBetweenStates(g.right, blockEndNFAState, -5);
/*     */ 
/* 421 */       if (firstAlt == null) {
/* 422 */         firstAlt = left;
/*     */       }
/*     */       else
/*     */       {
/* 426 */         transitionBetweenStates(prevAlternative, left, -5);
/*     */       }
/* 428 */       prevAlternative = left;
/* 429 */       altNum++;
/*     */     }
/*     */ 
/* 434 */     result = new StateCluster(firstAlt, blockEndNFAState);
/*     */ 
/* 436 */     firstAlt.decisionStateType = 2;
/*     */ 
/* 439 */     firstAlt.endOfBlockStateNumber = blockEndNFAState.stateNumber;
/*     */ 
/* 441 */     return result;
/*     */   }
/*     */ 
/*     */   public StateCluster build_Aoptional(StateCluster A)
/*     */   {
/* 453 */     StateCluster g = null;
/* 454 */     int n = this.nfa.grammar.getNumberOfAltsForDecisionNFA(A.left);
/* 455 */     if (n == 1)
/*     */     {
/* 458 */       NFAState decisionState = A.left;
/* 459 */       decisionState.setDescription("only alt of ()? block");
/* 460 */       NFAState emptyAlt = newState();
/* 461 */       emptyAlt.setDescription("epsilon path of ()? block");
/* 462 */       NFAState blockEndNFAState = null;
/* 463 */       blockEndNFAState = newState();
/* 464 */       transitionBetweenStates(A.right, blockEndNFAState, -5);
/* 465 */       blockEndNFAState.setDescription("end ()? block");
/*     */ 
/* 467 */       transitionBetweenStates(decisionState, emptyAlt, -5);
/* 468 */       transitionBetweenStates(emptyAlt, blockEndNFAState, -5);
/*     */ 
/* 471 */       decisionState.endOfBlockStateNumber = blockEndNFAState.stateNumber;
/* 472 */       blockEndNFAState.decisionStateType = 5;
/*     */ 
/* 474 */       g = new StateCluster(decisionState, blockEndNFAState);
/*     */     }
/*     */     else
/*     */     {
/* 478 */       NFAState lastRealAlt = this.nfa.grammar.getNFAStateForAltOfDecision(A.left, n);
/*     */ 
/* 480 */       NFAState emptyAlt = newState();
/* 481 */       emptyAlt.setDescription("epsilon path of ()? block");
/* 482 */       transitionBetweenStates(lastRealAlt, emptyAlt, -5);
/* 483 */       transitionBetweenStates(emptyAlt, A.right, -5);
/*     */ 
/* 486 */       A.left.endOfBlockStateNumber = A.right.stateNumber;
/* 487 */       A.right.decisionStateType = 5;
/*     */ 
/* 489 */       g = A;
/*     */     }
/* 491 */     g.left.decisionStateType = 3;
/*     */ 
/* 493 */     return g;
/*     */   }
/*     */ 
/*     */   public StateCluster build_Aplus(StateCluster A)
/*     */   {
/* 510 */     NFAState left = newState();
/* 511 */     NFAState blockEndNFAState = newState();
/* 512 */     blockEndNFAState.decisionStateType = 5;
/*     */ 
/* 515 */     if (A.right.decisionStateType == 5)
/*     */     {
/* 518 */       NFAState extraRightEdge = newState();
/* 519 */       transitionBetweenStates(A.right, extraRightEdge, -5);
/* 520 */       A.right = extraRightEdge;
/*     */     }
/*     */ 
/* 523 */     transitionBetweenStates(A.right, blockEndNFAState, -5);
/*     */ 
/* 525 */     transitionBetweenStates(A.right, A.left, -5);
/* 526 */     transitionBetweenStates(left, A.left, -5);
/*     */ 
/* 528 */     A.right.decisionStateType = 1;
/* 529 */     A.left.decisionStateType = 2;
/*     */ 
/* 532 */     A.left.endOfBlockStateNumber = A.right.stateNumber;
/*     */ 
/* 534 */     StateCluster g = new StateCluster(left, blockEndNFAState);
/* 535 */     return g;
/*     */   }
/*     */ 
/*     */   public StateCluster build_Astar(StateCluster A)
/*     */   {
/* 569 */     NFAState bypassDecisionState = newState();
/* 570 */     bypassDecisionState.setDescription("enter loop path of ()* block");
/* 571 */     NFAState optionalAlt = newState();
/* 572 */     optionalAlt.setDescription("epsilon path of ()* block");
/* 573 */     NFAState blockEndNFAState = newState();
/* 574 */     blockEndNFAState.decisionStateType = 5;
/*     */ 
/* 577 */     if (A.right.decisionStateType == 5)
/*     */     {
/* 580 */       NFAState extraRightEdge = newState();
/* 581 */       transitionBetweenStates(A.right, extraRightEdge, -5);
/* 582 */       A.right = extraRightEdge;
/*     */     }
/*     */ 
/* 586 */     A.right.setDescription("()* loopback");
/*     */ 
/* 588 */     transitionBetweenStates(bypassDecisionState, A.left, -5);
/*     */ 
/* 590 */     transitionBetweenStates(bypassDecisionState, optionalAlt, -5);
/* 591 */     transitionBetweenStates(optionalAlt, blockEndNFAState, -5);
/*     */ 
/* 593 */     transitionBetweenStates(A.right, blockEndNFAState, -5);
/*     */ 
/* 595 */     transitionBetweenStates(A.right, A.left, -5);
/*     */ 
/* 597 */     bypassDecisionState.decisionStateType = 4;
/* 598 */     A.left.decisionStateType = 2;
/* 599 */     A.right.decisionStateType = 1;
/*     */ 
/* 602 */     A.left.endOfBlockStateNumber = A.right.stateNumber;
/* 603 */     bypassDecisionState.endOfBlockStateNumber = blockEndNFAState.stateNumber;
/*     */ 
/* 605 */     StateCluster g = new StateCluster(bypassDecisionState, blockEndNFAState);
/* 606 */     return g;
/*     */   }
/*     */ 
/*     */   public StateCluster build_Wildcard(GrammarAST associatedAST)
/*     */   {
/* 671 */     NFAState left = newState();
/* 672 */     NFAState right = newState();
/* 673 */     left.associatedASTNode = associatedAST;
/* 674 */     right.associatedASTNode = associatedAST;
/* 675 */     Label label = new Label(this.nfa.grammar.getTokenTypes());
/* 676 */     Transition e = new Transition(label, right);
/* 677 */     left.addTransition(e);
/* 678 */     StateCluster g = new StateCluster(left, right);
/* 679 */     return g;
/*     */   }
/*     */ 
/*     */   public StateCluster build_WildcardTree(GrammarAST associatedAST)
/*     */   {
/* 686 */     StateCluster wildRoot = build_Wildcard(associatedAST);
/*     */ 
/* 688 */     StateCluster down = build_Atom(2, associatedAST);
/* 689 */     wildRoot = build_AB(wildRoot, down);
/*     */ 
/* 692 */     StateCluster wildChildren = build_Wildcard(associatedAST);
/* 693 */     wildChildren = build_Aplus(wildChildren);
/* 694 */     wildRoot = build_AB(wildRoot, wildChildren);
/*     */ 
/* 696 */     StateCluster up = build_Atom(3, associatedAST);
/* 697 */     wildRoot = build_AB(wildRoot, up);
/*     */ 
/* 700 */     StateCluster optionalNodeAlt = build_Wildcard(associatedAST);
/*     */ 
/* 702 */     List alts = new ArrayList();
/* 703 */     alts.add(wildRoot);
/* 704 */     alts.add(optionalNodeAlt);
/* 705 */     StateCluster blk = build_AlternativeBlock(alts);
/*     */ 
/* 707 */     return blk;
/*     */   }
/*     */ 
/*     */   protected IntSet getCollapsedBlockAsSet(State blk)
/*     */   {
/* 714 */     State s0 = blk;
/* 715 */     if ((s0 != null) && (s0.transition(0) != null)) {
/* 716 */       State s1 = s0.transition(0).target;
/* 717 */       if ((s1 != null) && (s1.transition(0) != null)) {
/* 718 */         Label label = s1.transition(0).label;
/* 719 */         if (label.isSet()) {
/* 720 */           return label.getSet();
/*     */         }
/*     */       }
/*     */     }
/* 724 */     return null;
/*     */   }
/*     */ 
/*     */   private void transitionBetweenStates(NFAState a, NFAState b, int label) {
/* 728 */     Transition e = new Transition(label, b);
/* 729 */     a.addTransition(e);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.NFAFactory
 * JD-Core Version:    0.6.2
 */