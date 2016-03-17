/*     */ package org.antlr.analysis;
/*     */ 
/*     */ import antlr.Token;
/*     */ import antlr.collections.AST;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.antlr.misc.IntSet;
/*     */ import org.antlr.misc.MultiMap;
/*     */ import org.antlr.misc.Utils;
/*     */ import org.antlr.tool.ErrorManager;
/*     */ import org.antlr.tool.Grammar;
/*     */ import org.antlr.tool.GrammarAST;
/*     */ import org.antlr.tool.Rule;
/*     */ 
/*     */ public class DecisionProbe
/*     */ {
/*     */   public DFA dfa;
/*  82 */   protected Set<DFAState> statesWithSyntacticallyAmbiguousAltsSet = new HashSet();
/*     */ 
/*  89 */   protected Map<DFAState, Set<Integer>> stateToSyntacticallyAmbiguousTokensRuleAltsMap = new HashMap();
/*     */ 
/*  96 */   protected Set<DFAState> statesResolvedWithSemanticPredicatesSet = new HashSet();
/*     */ 
/* 103 */   protected Map<DFAState, Map<Integer, SemanticContext>> stateToAltSetWithSemanticPredicatesMap = new HashMap();
/*     */ 
/* 110 */   protected Map<DFAState, Map<Integer, Set<Token>>> stateToIncompletelyCoveredAltsMap = new HashMap();
/*     */ 
/* 114 */   protected Set<DFAState> danglingStates = new HashSet();
/*     */ 
/* 119 */   protected Set<Integer> altsWithProblem = new HashSet();
/*     */ 
/* 125 */   public boolean nonLLStarDecision = false;
/*     */ 
/* 130 */   protected MultiMap<Integer, NFAConfiguration> stateToRecursionOverflowConfigurationsMap = new MultiMap();
/*     */ 
/* 144 */   protected boolean timedOut = false;
/*     */   protected Map<Integer, Integer> stateReachable;
/* 151 */   public static final Integer REACHABLE_BUSY = Utils.integer(-1);
/* 152 */   public static final Integer REACHABLE_NO = Utils.integer(0);
/* 153 */   public static final Integer REACHABLE_YES = Utils.integer(1);
/*     */   protected Set<String> statesVisitedAtInputDepth;
/*     */   protected Set<Integer> statesVisitedDuringSampleSequence;
/* 166 */   public static boolean verbose = false;
/*     */ 
/*     */   public DecisionProbe(DFA dfa) {
/* 169 */     this.dfa = dfa;
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/* 178 */     return this.dfa.getNFADecisionStartState().getDescription();
/*     */   }
/*     */ 
/*     */   public boolean isReduced() {
/* 182 */     return this.dfa.isReduced();
/*     */   }
/*     */ 
/*     */   public boolean isCyclic() {
/* 186 */     return this.dfa.isCyclic();
/*     */   }
/*     */ 
/*     */   public boolean isDeterministic()
/*     */   {
/* 193 */     if ((this.danglingStates.size() == 0) && (this.statesWithSyntacticallyAmbiguousAltsSet.size() == 0) && (this.dfa.getUnreachableAlts().size() == 0))
/*     */     {
/* 197 */       return true;
/*     */     }
/*     */ 
/* 200 */     if (this.statesWithSyntacticallyAmbiguousAltsSet.size() > 0) {
/* 201 */       Iterator it = this.statesWithSyntacticallyAmbiguousAltsSet.iterator();
/*     */ 
/* 203 */       while (it.hasNext()) {
/* 204 */         DFAState d = (DFAState)it.next();
/* 205 */         if (!this.statesResolvedWithSemanticPredicatesSet.contains(d)) {
/* 206 */           return false;
/*     */         }
/*     */       }
/*     */ 
/* 210 */       return true;
/*     */     }
/* 212 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean analysisOverflowed()
/*     */   {
/* 222 */     return this.stateToRecursionOverflowConfigurationsMap.size() > 0;
/*     */   }
/*     */ 
/*     */   public boolean isNonLLStarDecision()
/*     */   {
/* 227 */     return this.nonLLStarDecision;
/*     */   }
/*     */ 
/*     */   public int getNumberOfStates()
/*     */   {
/* 232 */     return this.dfa.getNumberOfStates();
/*     */   }
/*     */ 
/*     */   public List<Integer> getUnreachableAlts()
/*     */   {
/* 241 */     return this.dfa.getUnreachableAlts();
/*     */   }
/*     */ 
/*     */   public Set getDanglingStates()
/*     */   {
/* 250 */     return this.danglingStates;
/*     */   }
/*     */ 
/*     */   public Set getNonDeterministicAlts() {
/* 254 */     return this.altsWithProblem;
/*     */   }
/*     */ 
/*     */   public List getNonDeterministicAltsForState(DFAState targetState)
/*     */   {
/* 261 */     Set nondetAlts = targetState.getNonDeterministicAlts();
/* 262 */     if (nondetAlts == null) {
/* 263 */       return null;
/*     */     }
/* 265 */     List sorted = new LinkedList();
/* 266 */     sorted.addAll(nondetAlts);
/* 267 */     Collections.sort(sorted);
/* 268 */     return sorted;
/*     */   }
/*     */ 
/*     */   public Set getDFAStatesWithSyntacticallyAmbiguousAlts()
/*     */   {
/* 276 */     return this.statesWithSyntacticallyAmbiguousAltsSet;
/*     */   }
/*     */ 
/*     */   public Set getDisabledAlternatives(DFAState d)
/*     */   {
/* 286 */     return d.getDisabledAlternatives();
/*     */   }
/*     */ 
/*     */   public void removeRecursiveOverflowState(DFAState d)
/*     */   {
/* 293 */     Integer stateI = Utils.integer(d.stateNumber);
/* 294 */     this.stateToRecursionOverflowConfigurationsMap.remove(stateI);
/*     */   }
/*     */ 
/*     */   public List<Label> getSampleNonDeterministicInputSequence(DFAState targetState)
/*     */   {
/* 302 */     Set dfaStates = getDFAPathStatesToTarget(targetState);
/* 303 */     this.statesVisitedDuringSampleSequence = new HashSet();
/* 304 */     List labels = new ArrayList();
/* 305 */     if ((this.dfa == null) || (this.dfa.startState == null)) {
/* 306 */       return labels;
/*     */     }
/* 308 */     getSampleInputSequenceUsingStateSet(this.dfa.startState, targetState, dfaStates, labels);
/*     */ 
/* 312 */     return labels;
/*     */   }
/*     */ 
/*     */   public String getInputSequenceDisplay(List labels)
/*     */   {
/* 320 */     Grammar g = this.dfa.nfa.grammar;
/* 321 */     StringBuffer buf = new StringBuffer();
/* 322 */     for (Iterator it = labels.iterator(); it.hasNext(); ) {
/* 323 */       Label label = (Label)it.next();
/* 324 */       buf.append(label.toString(g));
/* 325 */       if ((it.hasNext()) && (g.type != 1)) {
/* 326 */         buf.append(' ');
/*     */       }
/*     */     }
/* 329 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public List getNFAPathStatesForAlt(int firstAlt, int alt, List labels)
/*     */   {
/* 363 */     NFAState nfaStart = this.dfa.getNFADecisionStartState();
/* 364 */     List path = new LinkedList();
/*     */ 
/* 366 */     for (int a = firstAlt; a <= alt; a++) {
/* 367 */       NFAState s = this.dfa.nfa.grammar.getNFAStateForAltOfDecision(nfaStart, a);
/*     */ 
/* 369 */       path.add(s);
/*     */     }
/*     */ 
/* 373 */     NFAState altStart = this.dfa.nfa.grammar.getNFAStateForAltOfDecision(nfaStart, alt);
/* 374 */     NFAState isolatedAltStart = (NFAState)altStart.transition[0].target;
/* 375 */     path.add(isolatedAltStart);
/*     */ 
/* 378 */     this.statesVisitedAtInputDepth = new HashSet();
/* 379 */     getNFAPath(isolatedAltStart, 0, labels, path);
/*     */ 
/* 383 */     return path;
/*     */   }
/*     */ 
/*     */   public SemanticContext getSemanticContextForAlt(DFAState d, int alt)
/*     */   {
/* 391 */     Map altToPredMap = (Map)this.stateToAltSetWithSemanticPredicatesMap.get(d);
/* 392 */     if (altToPredMap == null) {
/* 393 */       return null;
/*     */     }
/* 395 */     return (SemanticContext)altToPredMap.get(Utils.integer(alt));
/*     */   }
/*     */ 
/*     */   public boolean hasPredicate()
/*     */   {
/* 400 */     return this.stateToAltSetWithSemanticPredicatesMap.size() > 0;
/*     */   }
/*     */ 
/*     */   public Set getNondeterministicStatesResolvedWithSemanticPredicate() {
/* 404 */     return this.statesResolvedWithSemanticPredicatesSet;
/*     */   }
/*     */ 
/*     */   public Map<Integer, Set<Token>> getIncompletelyCoveredAlts(DFAState d)
/*     */   {
/* 411 */     return (Map)this.stateToIncompletelyCoveredAltsMap.get(d);
/*     */   }
/*     */ 
/*     */   public void issueWarnings()
/*     */   {
/* 418 */     if ((this.nonLLStarDecision) && (!this.dfa.getAutoBacktrackMode())) {
/* 419 */       ErrorManager.nonLLStarDecision(this);
/*     */     }
/*     */ 
/* 422 */     issueRecursionWarnings();
/*     */ 
/* 425 */     Set resolvedStates = getNondeterministicStatesResolvedWithSemanticPredicate();
/* 426 */     Set problemStates = getDFAStatesWithSyntacticallyAmbiguousAlts();
/* 427 */     if (problemStates.size() > 0) {
/* 428 */       Iterator it = problemStates.iterator();
/*     */ 
/* 430 */       while ((it.hasNext()) && (!this.dfa.nfa.grammar.NFAToDFAConversionExternallyAborted())) {
/* 431 */         DFAState d = (DFAState)it.next();
/* 432 */         Map insufficientAltToLocations = getIncompletelyCoveredAlts(d);
/* 433 */         if ((insufficientAltToLocations != null) && (insufficientAltToLocations.size() > 0)) {
/* 434 */           ErrorManager.insufficientPredicates(this, d, insufficientAltToLocations);
/*     */         }
/*     */ 
/* 437 */         if ((resolvedStates == null) || (!resolvedStates.contains(d)))
/*     */         {
/* 440 */           Set disabledAlts = getDisabledAlternatives(d);
/* 441 */           stripWildCardAlts(disabledAlts);
/* 442 */           if (disabledAlts.size() > 0)
/*     */           {
/* 445 */             boolean explicitlyGreedy = false;
/* 446 */             GrammarAST blockAST = d.dfa.nfa.grammar.getDecisionBlockAST(d.dfa.decisionNumber);
/*     */ 
/* 448 */             if (blockAST != null) {
/* 449 */               String greedyS = (String)blockAST.getBlockOption("greedy");
/* 450 */               if ((greedyS != null) && (greedyS.equals("true"))) explicitlyGreedy = true;
/*     */             }
/* 452 */             if (!explicitlyGreedy) ErrorManager.nondeterminism(this, d);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 458 */     Set danglingStates = getDanglingStates();
/*     */     Iterator it;
/* 459 */     if (danglingStates.size() > 0)
/*     */     {
/* 461 */       for (it = danglingStates.iterator(); it.hasNext(); ) {
/* 462 */         DFAState d = (DFAState)it.next();
/* 463 */         ErrorManager.danglingState(this, d);
/*     */       }
/*     */     }
/*     */ 
/* 467 */     if (!this.nonLLStarDecision) {
/* 468 */       List unreachableAlts = this.dfa.getUnreachableAlts();
/* 469 */       if ((unreachableAlts != null) && (unreachableAlts.size() > 0))
/*     */       {
/* 471 */         boolean isInheritedTokensRule = false;
/*     */         Iterator i$;
/* 472 */         if (this.dfa.isTokensRuleDecision()) {
/* 473 */           for (i$ = unreachableAlts.iterator(); i$.hasNext(); ) { Integer altI = (Integer)i$.next();
/* 474 */             GrammarAST decAST = this.dfa.getDecisionASTNode();
/* 475 */             GrammarAST altAST = decAST.getChild(altI.intValue() - 1);
/* 476 */             GrammarAST delegatedTokensAlt = altAST.getFirstChildWithType(39);
/*     */ 
/* 478 */             if (delegatedTokensAlt != null) {
/* 479 */               isInheritedTokensRule = true;
/* 480 */               ErrorManager.grammarWarning(162, this.dfa.nfa.grammar, null, this.dfa.nfa.grammar.name, delegatedTokensAlt.getFirstChild().getText());
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 488 */         if (!isInheritedTokensRule)
/*     */         {
/* 491 */           ErrorManager.unreachableAlts(this, unreachableAlts);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void stripWildCardAlts(Set disabledAlts)
/*     */   {
/* 502 */     List sortedDisableAlts = new ArrayList(disabledAlts);
/* 503 */     Collections.sort(sortedDisableAlts);
/* 504 */     Integer lastAlt = (Integer)sortedDisableAlts.get(sortedDisableAlts.size() - 1);
/*     */ 
/* 506 */     GrammarAST blockAST = this.dfa.nfa.grammar.getDecisionBlockAST(this.dfa.decisionNumber);
/*     */ 
/* 509 */     GrammarAST lastAltAST = null;
/* 510 */     if (blockAST.getChild(0).getType() == 4)
/*     */     {
/* 512 */       lastAltAST = blockAST.getChild(lastAlt.intValue());
/*     */     }
/*     */     else {
/* 515 */       lastAltAST = blockAST.getChild(lastAlt.intValue() - 1);
/*     */     }
/*     */ 
/* 522 */     if ((lastAltAST.getType() != 19) && (lastAltAST.getChild(0).getType() == 72) && (lastAltAST.getChild(1).getType() == 20))
/*     */     {
/* 527 */       disabledAlts.remove(lastAlt);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void issueRecursionWarnings()
/*     */   {
/* 533 */     Set dfaStatesWithRecursionProblems = this.stateToRecursionOverflowConfigurationsMap.keySet();
/*     */ 
/* 538 */     Map altToTargetToCallSitesMap = new HashMap();
/*     */ 
/* 540 */     Map altToDFAState = new HashMap();
/* 541 */     computeAltToProblemMaps(dfaStatesWithRecursionProblems, this.stateToRecursionOverflowConfigurationsMap, altToTargetToCallSitesMap, altToDFAState);
/*     */ 
/* 547 */     Set alts = altToTargetToCallSitesMap.keySet();
/* 548 */     List sortedAlts = new ArrayList(alts);
/* 549 */     Collections.sort(sortedAlts);
/* 550 */     for (Iterator altsIt = sortedAlts.iterator(); altsIt.hasNext(); ) {
/* 551 */       Integer altI = (Integer)altsIt.next();
/* 552 */       Map targetToCallSiteMap = (Map)altToTargetToCallSitesMap.get(altI);
/*     */ 
/* 554 */       Set targetRules = targetToCallSiteMap.keySet();
/* 555 */       Collection callSiteStates = targetToCallSiteMap.values();
/* 556 */       DFAState sampleBadState = (DFAState)altToDFAState.get(altI);
/* 557 */       ErrorManager.recursionOverflow(this, sampleBadState, altI.intValue(), targetRules, callSiteStates);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void computeAltToProblemMaps(Set dfaStatesUnaliased, Map configurationsMap, Map altToTargetToCallSitesMap, Map altToDFAState)
/*     */   {
/* 570 */     for (Iterator it = dfaStatesUnaliased.iterator(); it.hasNext(); ) {
/* 571 */       Integer stateI = (Integer)it.next();
/*     */ 
/* 573 */       List configs = (List)configurationsMap.get(stateI);
/* 574 */       for (int i = 0; i < configs.size(); i++) {
/* 575 */         NFAConfiguration c = (NFAConfiguration)configs.get(i);
/* 576 */         NFAState ruleInvocationState = this.dfa.nfa.getState(c.state);
/* 577 */         Transition transition0 = ruleInvocationState.transition[0];
/* 578 */         RuleClosureTransition ref = (RuleClosureTransition)transition0;
/* 579 */         String targetRule = ((NFAState)ref.target).enclosingRule.name;
/* 580 */         Integer altI = Utils.integer(c.alt);
/* 581 */         Map targetToCallSiteMap = (Map)altToTargetToCallSitesMap.get(altI);
/*     */ 
/* 583 */         if (targetToCallSiteMap == null) {
/* 584 */           targetToCallSiteMap = new HashMap();
/* 585 */           altToTargetToCallSitesMap.put(altI, targetToCallSiteMap);
/*     */         }
/* 587 */         Set callSites = (HashSet)targetToCallSiteMap.get(targetRule);
/*     */ 
/* 589 */         if (callSites == null) {
/* 590 */           callSites = new HashSet();
/* 591 */           targetToCallSiteMap.put(targetRule, callSites);
/*     */         }
/* 593 */         callSites.add(ruleInvocationState);
/*     */ 
/* 595 */         if (altToDFAState.get(altI) == null) {
/* 596 */           DFAState sampleBadState = this.dfa.getState(stateI.intValue());
/* 597 */           altToDFAState.put(altI, sampleBadState);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private Set getUnaliasedDFAStateSet(Set dfaStatesWithRecursionProblems) {
/* 604 */     Set dfaStatesUnaliased = new HashSet();
/* 605 */     for (Iterator it = dfaStatesWithRecursionProblems.iterator(); it.hasNext(); ) {
/* 606 */       Integer stateI = (Integer)it.next();
/* 607 */       DFAState d = this.dfa.getState(stateI.intValue());
/* 608 */       dfaStatesUnaliased.add(Utils.integer(d.stateNumber));
/*     */     }
/* 610 */     return dfaStatesUnaliased;
/*     */   }
/*     */ 
/*     */   public void reportDanglingState(DFAState d)
/*     */   {
/* 621 */     this.danglingStates.add(d);
/*     */   }
/*     */ 
/*     */   public void reportNonLLStarDecision(DFA dfa)
/*     */   {
/* 637 */     this.nonLLStarDecision = true;
/* 638 */     dfa.nfa.grammar.numNonLLStar += 1;
/* 639 */     this.altsWithProblem.addAll(dfa.recursiveAltSet.toList());
/*     */   }
/*     */ 
/*     */   public void reportRecursionOverflow(DFAState d, NFAConfiguration recursionNFAConfiguration)
/*     */   {
/* 652 */     if (d.stateNumber > 0) {
/* 653 */       Integer stateI = Utils.integer(d.stateNumber);
/* 654 */       this.stateToRecursionOverflowConfigurationsMap.map(stateI, recursionNFAConfiguration);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void reportNondeterminism(DFAState d, Set<Integer> nondeterministicAlts) {
/* 659 */     this.altsWithProblem.addAll(nondeterministicAlts);
/* 660 */     this.statesWithSyntacticallyAmbiguousAltsSet.add(d);
/* 661 */     this.dfa.nfa.grammar.setOfNondeterministicDecisionNumbers.add(Utils.integer(this.dfa.getDecisionNumber()));
/*     */   }
/*     */ 
/*     */   public void reportLexerRuleNondeterminism(DFAState d, Set<Integer> nondeterministicAlts)
/*     */   {
/* 671 */     this.stateToSyntacticallyAmbiguousTokensRuleAltsMap.put(d, nondeterministicAlts);
/*     */   }
/*     */ 
/*     */   public void reportNondeterminismResolvedWithSemanticPredicate(DFAState d)
/*     */   {
/* 677 */     if (d.abortedDueToRecursionOverflow) {
/* 678 */       d.dfa.probe.removeRecursiveOverflowState(d);
/*     */     }
/* 680 */     this.statesResolvedWithSemanticPredicatesSet.add(d);
/*     */ 
/* 682 */     this.dfa.nfa.grammar.setOfNondeterministicDecisionNumbersResolvedWithPredicates.add(Utils.integer(this.dfa.getDecisionNumber()));
/*     */   }
/*     */ 
/*     */   public void reportAltPredicateContext(DFAState d, Map altPredicateContext)
/*     */   {
/* 693 */     Map copy = new HashMap();
/* 694 */     copy.putAll(altPredicateContext);
/* 695 */     this.stateToAltSetWithSemanticPredicatesMap.put(d, copy);
/*     */   }
/*     */ 
/*     */   public void reportIncompletelyCoveredAlts(DFAState d, Map<Integer, Set<Token>> altToLocationsReachableWithoutPredicate)
/*     */   {
/* 701 */     this.stateToIncompletelyCoveredAltsMap.put(d, altToLocationsReachableWithoutPredicate);
/*     */   }
/*     */ 
/*     */   protected boolean reachesState(DFAState startState, DFAState targetState, Set states)
/*     */   {
/* 713 */     if (startState == targetState) {
/* 714 */       states.add(targetState);
/*     */ 
/* 716 */       this.stateReachable.put(new Integer(startState.stateNumber), REACHABLE_YES);
/* 717 */       return true;
/*     */     }
/*     */ 
/* 720 */     DFAState s = startState;
/*     */ 
/* 722 */     this.stateReachable.put(new Integer(s.stateNumber), REACHABLE_BUSY);
/*     */ 
/* 727 */     for (int i = 0; i < s.getNumberOfTransitions(); i++) {
/* 728 */       Transition t = s.transition(i);
/* 729 */       DFAState edgeTarget = (DFAState)t.target;
/* 730 */       Integer targetStatus = (Integer)this.stateReachable.get(new Integer(edgeTarget.stateNumber));
/* 731 */       if (targetStatus != REACHABLE_BUSY)
/*     */       {
/* 734 */         if (targetStatus == REACHABLE_YES) {
/* 735 */           this.stateReachable.put(new Integer(s.stateNumber), REACHABLE_YES);
/* 736 */           return true;
/*     */         }
/* 738 */         if (targetStatus != REACHABLE_NO)
/*     */         {
/* 742 */           if (reachesState(edgeTarget, targetState, states)) {
/* 743 */             states.add(s);
/* 744 */             this.stateReachable.put(new Integer(s.stateNumber), REACHABLE_YES);
/* 745 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 749 */     this.stateReachable.put(new Integer(s.stateNumber), REACHABLE_NO);
/* 750 */     return false;
/*     */   }
/*     */ 
/*     */   protected Set getDFAPathStatesToTarget(DFAState targetState) {
/* 754 */     Set dfaStates = new HashSet();
/* 755 */     this.stateReachable = new HashMap();
/* 756 */     if ((this.dfa == null) || (this.dfa.startState == null)) {
/* 757 */       return dfaStates;
/*     */     }
/* 759 */     boolean reaches = reachesState(this.dfa.startState, targetState, dfaStates);
/* 760 */     return dfaStates;
/*     */   }
/*     */ 
/*     */   protected void getSampleInputSequenceUsingStateSet(State startState, State targetState, Set states, List<Label> labels)
/*     */   {
/* 777 */     this.statesVisitedDuringSampleSequence.add(new Integer(startState.stateNumber));
/*     */ 
/* 780 */     for (int i = 0; i < startState.getNumberOfTransitions(); i++) {
/* 781 */       Transition t = startState.transition(i);
/* 782 */       DFAState edgeTarget = (DFAState)t.target;
/* 783 */       if ((states.contains(edgeTarget)) && (!this.statesVisitedDuringSampleSequence.contains(new Integer(edgeTarget.stateNumber))))
/*     */       {
/* 786 */         labels.add(t.label);
/* 787 */         if (edgeTarget != targetState)
/*     */         {
/* 789 */           getSampleInputSequenceUsingStateSet(edgeTarget, targetState, states, labels);
/*     */         }
/*     */ 
/* 795 */         return;
/*     */       }
/*     */     }
/* 798 */     labels.add(new Label(-5));
/*     */   }
/*     */ 
/*     */   protected boolean getNFAPath(NFAState s, int labelIndex, List labels, List path)
/*     */   {
/* 817 */     String thisStateKey = getStateLabelIndexKey(s.stateNumber, labelIndex);
/* 818 */     if (this.statesVisitedAtInputDepth.contains(thisStateKey))
/*     */     {
/* 823 */       return false;
/*     */     }
/* 825 */     this.statesVisitedAtInputDepth.add(thisStateKey);
/*     */ 
/* 834 */     for (int i = 0; i < s.getNumberOfTransitions(); i++) {
/* 835 */       Transition t = s.transition[i];
/* 836 */       NFAState edgeTarget = (NFAState)t.target;
/* 837 */       Label label = (Label)labels.get(labelIndex);
/*     */ 
/* 844 */       if ((t.label.isEpsilon()) || (t.label.isSemanticPredicate()))
/*     */       {
/* 846 */         path.add(edgeTarget);
/* 847 */         boolean found = getNFAPath(edgeTarget, labelIndex, labels, path);
/*     */ 
/* 849 */         if (found) {
/* 850 */           this.statesVisitedAtInputDepth.remove(thisStateKey);
/* 851 */           return true;
/*     */         }
/* 853 */         path.remove(path.size() - 1);
/*     */       }
/* 856 */       else if (t.label.matches(label)) {
/* 857 */         path.add(edgeTarget);
/*     */ 
/* 863 */         if (labelIndex == labels.size() - 1)
/*     */         {
/* 865 */           this.statesVisitedAtInputDepth.remove(thisStateKey);
/* 866 */           return true;
/*     */         }
/*     */ 
/* 869 */         boolean found = getNFAPath(edgeTarget, labelIndex + 1, labels, path);
/*     */ 
/* 871 */         if (found) {
/* 872 */           this.statesVisitedAtInputDepth.remove(thisStateKey);
/* 873 */           return true;
/*     */         }
/*     */ 
/* 879 */         path.remove(path.size() - 1);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 885 */     this.statesVisitedAtInputDepth.remove(thisStateKey);
/* 886 */     return false;
/*     */   }
/*     */ 
/*     */   protected String getStateLabelIndexKey(int s, int i) {
/* 890 */     StringBuffer buf = new StringBuffer();
/* 891 */     buf.append(s);
/* 892 */     buf.append('_');
/* 893 */     buf.append(i);
/* 894 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public String getTokenNameForTokensRuleAlt(int alt)
/*     */   {
/* 901 */     NFAState decisionState = this.dfa.getNFADecisionStartState();
/* 902 */     NFAState altState = this.dfa.nfa.grammar.getNFAStateForAltOfDecision(decisionState, alt);
/*     */ 
/* 904 */     NFAState decisionLeft = (NFAState)altState.transition[0].target;
/* 905 */     RuleClosureTransition ruleCallEdge = (RuleClosureTransition)decisionLeft.transition[0];
/*     */ 
/* 907 */     NFAState ruleStartState = (NFAState)ruleCallEdge.target;
/*     */ 
/* 909 */     return ruleStartState.enclosingRule.name;
/*     */   }
/*     */ 
/*     */   public void reset() {
/* 913 */     this.stateToRecursionOverflowConfigurationsMap.clear();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.analysis.DecisionProbe
 * JD-Core Version:    0.6.2
 */