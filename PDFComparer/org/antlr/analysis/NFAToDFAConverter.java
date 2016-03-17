/*      */ package org.antlr.analysis;
/*      */ 
/*      */ import java.io.PrintStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.antlr.misc.BitSet;
/*      */ import org.antlr.misc.IntSet;
/*      */ import org.antlr.misc.OrderedHashSet;
/*      */ import org.antlr.misc.Utils;
/*      */ import org.antlr.tool.CompositeGrammar;
/*      */ import org.antlr.tool.ErrorManager;
/*      */ import org.antlr.tool.Grammar;
/*      */ import org.antlr.tool.GrammarAST;
/*      */ import org.antlr.tool.Rule;
/*      */ 
/*      */ public class NFAToDFAConverter
/*      */ {
/*   43 */   protected List work = new LinkedList();
/*      */   protected NFAContext[] contextTrees;
/*      */   protected DFA dfa;
/*   57 */   public static boolean debug = false;
/*      */ 
/*   66 */   public static boolean SINGLE_THREADED_NFA_CONVERSION = true;
/*      */ 
/*   68 */   protected boolean computingStartState = false;
/*      */ 
/*      */   public NFAToDFAConverter(DFA dfa) {
/*   71 */     this.dfa = dfa;
/*   72 */     int nAlts = dfa.getNumberOfAlts();
/*   73 */     initContextTrees(nAlts);
/*      */   }
/*      */ 
/*      */   public void convert()
/*      */   {
/*   80 */     this.dfa.startState = computeStartState();
/*      */ 
/*   83 */     while ((this.work.size() > 0) && (!this.dfa.nfa.grammar.NFAToDFAConversionExternallyAborted()))
/*      */     {
/*   86 */       DFAState d = (DFAState)this.work.get(0);
/*   87 */       if (this.dfa.nfa.grammar.composite.watchNFAConversion) {
/*   88 */         System.out.println("convert DFA state " + d.stateNumber + " (" + d.nfaConfigurations.size() + " nfa states)");
/*      */       }
/*      */ 
/*   91 */       int k = this.dfa.getUserMaxLookahead();
/*   92 */       if ((k > 0) && (k == d.getLookaheadDepth()))
/*      */       {
/*  100 */         resolveNonDeterminisms(d);
/*      */ 
/*  102 */         if (d.isResolvedWithPredicates()) {
/*  103 */           addPredicateTransitions(d);
/*      */         }
/*      */         else
/*  106 */           d.setAcceptState(true);
/*      */       }
/*      */       else
/*      */       {
/*  110 */         findNewDFAStatesAndAddDFATransitions(d);
/*      */       }
/*  112 */       this.work.remove(0);
/*      */     }
/*      */ 
/*  118 */     this.dfa.findAllGatedSynPredsUsedInDFAAcceptStates();
/*      */   }
/*      */ 
/*      */   protected DFAState computeStartState()
/*      */   {
/*  140 */     NFAState alt = this.dfa.decisionNFAStartState;
/*  141 */     DFAState startState = this.dfa.newState();
/*  142 */     this.computingStartState = true;
/*  143 */     int i = 0;
/*  144 */     int altNum = 1;
/*  145 */     while (alt != null)
/*      */     {
/*  150 */       NFAContext initialContext = this.contextTrees[i];
/*      */ 
/*  153 */       if ((i == 0) && (this.dfa.getNFADecisionStartState().decisionStateType == 1))
/*      */       {
/*  156 */         int numAltsIncludingExitBranch = this.dfa.nfa.grammar.getNumberOfAltsForDecisionNFA(this.dfa.decisionNFAStartState);
/*      */ 
/*  158 */         altNum = numAltsIncludingExitBranch;
/*  159 */         closure((NFAState)alt.transition[0].target, altNum, initialContext, SemanticContext.EMPTY_SEMANTIC_CONTEXT, startState, true);
/*      */ 
/*  166 */         altNum = 1;
/*      */       }
/*      */       else {
/*  169 */         closure((NFAState)alt.transition[0].target, altNum, initialContext, SemanticContext.EMPTY_SEMANTIC_CONTEXT, startState, true);
/*      */ 
/*  176 */         altNum++;
/*      */       }
/*  178 */       i++;
/*      */ 
/*  181 */       if (alt.transition[1] == null) {
/*      */         break;
/*      */       }
/*  184 */       alt = (NFAState)alt.transition[1].target;
/*      */     }
/*      */ 
/*  190 */     this.dfa.addState(startState);
/*  191 */     this.work.add(startState);
/*  192 */     this.computingStartState = false;
/*  193 */     return startState;
/*      */   }
/*      */ 
/*      */   protected void findNewDFAStatesAndAddDFATransitions(DFAState d)
/*      */   {
/*  203 */     OrderedHashSet labels = d.getReachableLabels();
/*      */ 
/*  236 */     Label EOTLabel = new Label(-2);
/*  237 */     boolean containsEOT = (labels != null) && (labels.contains(EOTLabel));
/*  238 */     if ((!this.dfa.isGreedy()) && (containsEOT)) {
/*  239 */       convertToEOTAcceptState(d);
/*  240 */       return;
/*      */     }
/*      */ 
/*  266 */     int numberOfEdgesEmanating = 0;
/*  267 */     Map targetToLabelMap = new HashMap();
/*      */ 
/*  269 */     int numLabels = 0;
/*  270 */     if (labels != null) {
/*  271 */       numLabels = labels.size();
/*      */     }
/*  273 */     for (int i = 0; i < numLabels; i++) {
/*  274 */       Label label = (Label)labels.get(i);
/*  275 */       DFAState t = reach(d, label);
/*  276 */       if (debug) {
/*  277 */         System.out.println("DFA state after reach " + label + " " + d + "-" + label.toString(this.dfa.nfa.grammar) + "->" + t);
/*      */       }
/*      */ 
/*  280 */       if (t != null)
/*      */       {
/*  289 */         if (t.getUniqueAlt() == -1)
/*      */         {
/*  296 */           closure(t);
/*      */         }
/*      */ 
/*  306 */         DFAState targetState = addDFAStateToWorkList(t);
/*      */ 
/*  308 */         numberOfEdgesEmanating += addTransition(d, label, targetState, targetToLabelMap);
/*      */ 
/*  315 */         targetState.setLookaheadDepth(d.getLookaheadDepth() + 1);
/*      */       }
/*      */     }
/*      */ 
/*  319 */     if ((!d.isResolvedWithPredicates()) && (numberOfEdgesEmanating == 0))
/*      */     {
/*  324 */       this.dfa.probe.reportDanglingState(d);
/*      */ 
/*  328 */       int minAlt = resolveByPickingMinAlt(d, null);
/*      */ 
/*  332 */       d.setAcceptState(true);
/*  333 */       this.dfa.setAcceptState(minAlt, d);
/*      */     }
/*      */ 
/*  339 */     if (d.isResolvedWithPredicates())
/*  340 */       addPredicateTransitions(d);
/*      */   }
/*      */ 
/*      */   protected static int addTransition(DFAState d, Label label, DFAState targetState, Map targetToLabelMap)
/*      */   {
/*  414 */     int n = 0;
/*  415 */     if (DFAOptimizer.COLLAPSE_ALL_PARALLEL_EDGES)
/*      */     {
/*  417 */       Integer tI = Utils.integer(targetState.stateNumber);
/*  418 */       Transition oldTransition = (Transition)targetToLabelMap.get(tI);
/*  419 */       if (oldTransition != null)
/*      */       {
/*  423 */         if (label.getAtom() == -2)
/*      */         {
/*  425 */           oldTransition.label = new Label(-2);
/*      */         }
/*  429 */         else if (oldTransition.label.getAtom() != -2)
/*      */         {
/*  431 */           oldTransition.label.add(label);
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  438 */         n = 1;
/*  439 */         label = (Label)label.clone();
/*  440 */         int transitionIndex = d.addTransition(targetState, label);
/*  441 */         Transition trans = d.getTransition(transitionIndex);
/*      */ 
/*  443 */         targetToLabelMap.put(tI, trans);
/*      */       }
/*      */     }
/*      */     else {
/*  447 */       n = 1;
/*  448 */       d.addTransition(targetState, label);
/*      */     }
/*  450 */     return n;
/*      */   }
/*      */ 
/*      */   public void closure(DFAState d)
/*      */   {
/*  458 */     if (debug) {
/*  459 */       System.out.println("closure(" + d + ")");
/*      */     }
/*      */ 
/*  462 */     List configs = new ArrayList();
/*      */ 
/*  465 */     configs.addAll(d.nfaConfigurations);
/*      */ 
/*  467 */     int numConfigs = configs.size();
/*  468 */     for (int i = 0; i < numConfigs; i++) {
/*  469 */       NFAConfiguration c = (NFAConfiguration)configs.get(i);
/*  470 */       if (!c.singleAtomTransitionEmanating)
/*      */       {
/*  477 */         closure(this.dfa.nfa.getState(c.state), c.alt, c.context, c.semanticContext, d, false);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  485 */     d.closureBusy = null;
/*      */   }
/*      */ 
/*      */   public void closure(NFAState p, int alt, NFAContext context, SemanticContext semanticContext, DFAState d, boolean collectPredicates)
/*      */   {
/*  598 */     if (debug) {
/*  599 */       System.out.println("closure at " + p.enclosingRule.name + " state " + p.stateNumber + "|" + alt + " filling DFA state " + d.stateNumber + " with context " + context);
/*      */     }
/*      */ 
/*  612 */     NFAConfiguration proposedNFAConfiguration = new NFAConfiguration(p.stateNumber, alt, context, semanticContext);
/*      */ 
/*  619 */     if (closureIsBusy(d, proposedNFAConfiguration)) {
/*  620 */       if (debug) {
/*  621 */         System.out.println("avoid visiting exact closure computation NFA config: " + proposedNFAConfiguration + " in " + p.enclosingRule.name);
/*      */ 
/*  623 */         System.out.println("state is " + d.dfa.decisionNumber + "." + d.stateNumber);
/*      */       }
/*  625 */       return;
/*      */     }
/*      */ 
/*  629 */     d.closureBusy.add(proposedNFAConfiguration);
/*      */ 
/*  632 */     d.addNFAConfiguration(p, proposedNFAConfiguration);
/*      */ 
/*  635 */     Transition transition0 = p.transition[0];
/*  636 */     if ((transition0 instanceof RuleClosureTransition)) {
/*  637 */       int depth = context.recursionDepthEmanatingFromState(p.stateNumber);
/*      */ 
/*  640 */       if ((depth == 1) && (d.dfa.getUserMaxLookahead() == 0)) {
/*  641 */         d.dfa.recursiveAltSet.add(alt);
/*  642 */         if (d.dfa.recursiveAltSet.size() > 1)
/*      */         {
/*  644 */           d.abortedDueToMultipleRecursiveAlts = true;
/*  645 */           throw new NonLLStarDecisionException(d.dfa);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  656 */       if (depth >= NFAContext.MAX_SAME_RULE_INVOCATIONS_PER_NFA_CONFIG_STACK)
/*      */       {
/*  661 */         d.abortedDueToRecursionOverflow = true;
/*  662 */         d.dfa.probe.reportRecursionOverflow(d, proposedNFAConfiguration);
/*  663 */         if (debug) {
/*  664 */           System.out.println("analysis overflow in closure(" + d.stateNumber + ")");
/*      */         }
/*  666 */         return;
/*      */       }
/*      */ 
/*  670 */       RuleClosureTransition ref = (RuleClosureTransition)transition0;
/*      */ 
/*  677 */       NFAContext newContext = new NFAContext(context, p);
/*      */ 
/*  681 */       NFAState ruleTarget = (NFAState)ref.target;
/*  682 */       closure(ruleTarget, alt, newContext, semanticContext, d, collectPredicates);
/*      */     }
/*  685 */     else if ((p.isAcceptState()) && (context.parent != null)) {
/*  686 */       NFAState whichStateInvokedRule = context.invokingState;
/*  687 */       RuleClosureTransition edgeToRule = (RuleClosureTransition)whichStateInvokedRule.transition[0];
/*      */ 
/*  689 */       NFAState continueState = edgeToRule.followState;
/*  690 */       NFAContext newContext = context.parent;
/*  691 */       closure(continueState, alt, newContext, semanticContext, d, collectPredicates);
/*      */     }
/*      */     else
/*      */     {
/*  698 */       if ((transition0 != null) && (transition0.isEpsilon())) {
/*  699 */         boolean collectPredicatesAfterAction = collectPredicates;
/*  700 */         if ((transition0.isAction()) && (collectPredicates)) {
/*  701 */           collectPredicatesAfterAction = false;
/*      */         }
/*      */ 
/*  708 */         closure((NFAState)transition0.target, alt, context, semanticContext, d, collectPredicatesAfterAction);
/*      */       }
/*  716 */       else if ((transition0 != null) && (transition0.isSemanticPredicate())) {
/*  717 */         SemanticContext labelContext = transition0.label.getSemanticContext();
/*  718 */         if (this.computingStartState) {
/*  719 */           if (collectPredicates)
/*      */           {
/*  722 */             this.dfa.predicateVisible = true;
/*      */           }
/*      */           else
/*      */           {
/*  726 */             this.dfa.hasPredicateBlockedByAction = true;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  731 */         SemanticContext newSemanticContext = semanticContext;
/*  732 */         if (collectPredicates)
/*      */         {
/*  736 */           int walkAlt = this.dfa.decisionNFAStartState.translateDisplayAltToWalkAlt(alt);
/*      */ 
/*  738 */           NFAState altLeftEdge = this.dfa.nfa.grammar.getNFAStateForAltOfDecision(this.dfa.decisionNFAStartState, walkAlt);
/*      */ 
/*  747 */           if ((!labelContext.isSyntacticPredicate()) || (p == altLeftEdge.transition[0].target))
/*      */           {
/*  751 */             newSemanticContext = SemanticContext.and(semanticContext, labelContext);
/*      */           }
/*      */         }
/*      */ 
/*  755 */         closure((NFAState)transition0.target, alt, context, newSemanticContext, d, collectPredicates);
/*      */       }
/*      */ 
/*  762 */       Transition transition1 = p.transition[1];
/*  763 */       if ((transition1 != null) && (transition1.isEpsilon()))
/*  764 */         closure((NFAState)transition1.target, alt, context, semanticContext, d, collectPredicates);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static boolean closureIsBusy(DFAState d, NFAConfiguration proposedNFAConfiguration)
/*      */   {
/*  812 */     return d.closureBusy.contains(proposedNFAConfiguration);
/*      */   }
/*      */ 
/*      */   public DFAState reach(DFAState d, Label label)
/*      */   {
/*  850 */     DFAState labelDFATarget = this.dfa.newState();
/*      */ 
/*  856 */     List configs = d.configurationsWithLabeledEdges;
/*  857 */     int numConfigs = configs.size();
/*      */     NFAConfiguration newC;
/*  858 */     for (int i = 0; i < numConfigs; i++) {
/*  859 */       NFAConfiguration c = (NFAConfiguration)configs.get(i);
/*  860 */       if ((!c.resolved) && (!c.resolveWithPredicate))
/*      */       {
/*  863 */         NFAState p = this.dfa.nfa.getState(c.state);
/*      */ 
/*  866 */         Transition edge = p.transition[0];
/*  867 */         if ((edge != null) && (c.singleAtomTransitionEmanating))
/*      */         {
/*  870 */           Label edgeLabel = edge.label;
/*      */ 
/*  879 */           if ((c.context.parent == null) || (edgeLabel.label != -2))
/*      */           {
/*  886 */             if (Label.intersect(label, edgeLabel))
/*      */             {
/*  889 */               newC = labelDFATarget.addNFAConfiguration((NFAState)edge.target, c.alt, c.context, c.semanticContext);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  896 */     if (labelDFATarget.nfaConfigurations.size() == 0)
/*      */     {
/*  898 */       this.dfa.setState(labelDFATarget.stateNumber, null);
/*  899 */       labelDFATarget = null;
/*      */     }
/*  901 */     return labelDFATarget;
/*      */   }
/*      */ 
/*      */   protected void convertToEOTAcceptState(DFAState d)
/*      */   {
/*  916 */     Label eot = new Label(-2);
/*  917 */     int numConfigs = d.nfaConfigurations.size();
/*  918 */     for (int i = 0; i < numConfigs; i++) {
/*  919 */       NFAConfiguration c = (NFAConfiguration)d.nfaConfigurations.get(i);
/*  920 */       if ((!c.resolved) && (!c.resolveWithPredicate))
/*      */       {
/*  923 */         NFAState p = this.dfa.nfa.getState(c.state);
/*  924 */         Transition edge = p.transition[0];
/*  925 */         Label edgeLabel = edge.label;
/*  926 */         if (edgeLabel.equals(eot))
/*      */         {
/*  928 */           d.setAcceptState(true);
/*      */ 
/*  930 */           d.nfaConfigurations.clear();
/*  931 */           d.addNFAConfiguration(p, c.alt, c.context, c.semanticContext);
/*      */ 
/*  933 */           return;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected DFAState addDFAStateToWorkList(DFAState d)
/*      */   {
/*  946 */     DFAState existingState = this.dfa.addState(d);
/*  947 */     if (d != existingState)
/*      */     {
/*  958 */       this.dfa.setState(d.stateNumber, existingState);
/*  959 */       return existingState;
/*      */     }
/*      */ 
/*  966 */     resolveNonDeterminisms(d);
/*      */ 
/*  970 */     int alt = d.getUniquelyPredictedAlt();
/*  971 */     if (alt != -1) {
/*  972 */       d = convertToAcceptState(d, alt);
/*      */     }
/*      */     else
/*      */     {
/*  980 */       this.work.add(d);
/*      */     }
/*  982 */     return d;
/*      */   }
/*      */ 
/*      */   protected DFAState convertToAcceptState(DFAState d, int alt)
/*      */   {
/*  991 */     if ((DFAOptimizer.MERGE_STOP_STATES) && (d.getNonDeterministicAlts() == null) && (!d.abortedDueToRecursionOverflow) && (!d.abortedDueToMultipleRecursiveAlts))
/*      */     {
/*  998 */       DFAState acceptStateForAlt = this.dfa.getAcceptState(alt);
/*  999 */       if (acceptStateForAlt != null)
/*      */       {
/* 1006 */         SemanticContext gatedPreds = d.getGatedPredicatesInNFAConfigurations();
/* 1007 */         SemanticContext existingStateGatedPreds = acceptStateForAlt.getGatedPredicatesInNFAConfigurations();
/*      */ 
/* 1009 */         if (((gatedPreds == null) && (existingStateGatedPreds == null)) || ((gatedPreds != null) && (existingStateGatedPreds != null) && (gatedPreds.equals(existingStateGatedPreds))))
/*      */         {
/* 1014 */           this.dfa.setState(d.stateNumber, acceptStateForAlt);
/* 1015 */           this.dfa.removeState(d);
/* 1016 */           d = acceptStateForAlt;
/* 1017 */           return d;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1022 */     d.setAcceptState(true);
/* 1023 */     this.dfa.setAcceptState(alt, d);
/* 1024 */     return d;
/*      */   }
/*      */ 
/*      */   public void resolveNonDeterminisms(DFAState d)
/*      */   {
/* 1169 */     if (debug) {
/* 1170 */       System.out.println("resolveNonDeterminisms " + d.toString());
/*      */     }
/* 1172 */     boolean conflictingLexerRules = false;
/* 1173 */     Set nondeterministicAlts = d.getNonDeterministicAlts();
/* 1174 */     if ((debug) && (nondeterministicAlts != null)) {
/* 1175 */       System.out.println("nondet alts=" + nondeterministicAlts);
/*      */     }
/*      */ 
/* 1183 */     NFAConfiguration anyConfig = (NFAConfiguration)d.nfaConfigurations.get(0);
/* 1184 */     NFAState anyState = this.dfa.nfa.getState(anyConfig.state);
/*      */ 
/* 1189 */     if (anyState.isEOTTargetState()) {
/* 1190 */       Set allAlts = d.getAltSet();
/*      */ 
/* 1192 */       if ((allAlts != null) && (allAlts.size() > 1)) {
/* 1193 */         nondeterministicAlts = allAlts;
/*      */ 
/* 1195 */         if (d.dfa.isTokensRuleDecision()) {
/* 1196 */           this.dfa.probe.reportLexerRuleNondeterminism(d, allAlts);
/*      */ 
/* 1198 */           conflictingLexerRules = true;
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1204 */     if ((!d.abortedDueToRecursionOverflow) && (nondeterministicAlts == null)) {
/* 1205 */       return;
/*      */     }
/*      */ 
/* 1210 */     if ((!d.abortedDueToRecursionOverflow) && (!conflictingLexerRules))
/*      */     {
/* 1212 */       this.dfa.probe.reportNondeterminism(d, nondeterministicAlts);
/*      */     }
/*      */ 
/* 1219 */     boolean resolved = tryToResolveWithSemanticPredicates(d, nondeterministicAlts);
/*      */ 
/* 1221 */     if (resolved) {
/* 1222 */       if (debug) {
/* 1223 */         System.out.println("resolved DFA state " + d.stateNumber + " with pred");
/*      */       }
/* 1225 */       d.resolvedWithPredicates = true;
/* 1226 */       this.dfa.probe.reportNondeterminismResolvedWithSemanticPredicate(d);
/* 1227 */       return;
/*      */     }
/*      */ 
/* 1231 */     resolveByChoosingFirstAlt(d, nondeterministicAlts);
/*      */   }
/*      */ 
/*      */   protected int resolveByChoosingFirstAlt(DFAState d, Set nondeterministicAlts)
/*      */   {
/* 1237 */     int winningAlt = 0;
/* 1238 */     if (this.dfa.isGreedy()) {
/* 1239 */       winningAlt = resolveByPickingMinAlt(d, nondeterministicAlts);
/*      */     }
/*      */     else
/*      */     {
/* 1250 */       int exitAlt = this.dfa.getNumberOfAlts();
/* 1251 */       if (nondeterministicAlts.contains(Utils.integer(exitAlt)))
/*      */       {
/* 1254 */         winningAlt = resolveByPickingExitAlt(d, nondeterministicAlts);
/*      */       }
/*      */       else {
/* 1257 */         winningAlt = resolveByPickingMinAlt(d, nondeterministicAlts);
/*      */       }
/*      */     }
/* 1260 */     return winningAlt;
/*      */   }
/*      */ 
/*      */   protected int resolveByPickingMinAlt(DFAState d, Set nondeterministicAlts)
/*      */   {
/* 1274 */     int min = 2147483647;
/* 1275 */     if (nondeterministicAlts != null) {
/* 1276 */       min = getMinAlt(nondeterministicAlts);
/*      */     }
/*      */     else {
/* 1279 */       min = d.minAltInConfigurations;
/*      */     }
/*      */ 
/* 1282 */     turnOffOtherAlts(d, min, nondeterministicAlts);
/*      */ 
/* 1284 */     return min;
/*      */   }
/*      */ 
/*      */   protected int resolveByPickingExitAlt(DFAState d, Set nondeterministicAlts)
/*      */   {
/* 1291 */     int exitAlt = this.dfa.getNumberOfAlts();
/* 1292 */     turnOffOtherAlts(d, exitAlt, nondeterministicAlts);
/* 1293 */     return exitAlt;
/*      */   }
/*      */ 
/*      */   protected static void turnOffOtherAlts(DFAState d, int min, Set<Integer> nondeterministicAlts)
/*      */   {
/* 1300 */     int numConfigs = d.nfaConfigurations.size();
/* 1301 */     for (int i = 0; i < numConfigs; i++) {
/* 1302 */       NFAConfiguration configuration = (NFAConfiguration)d.nfaConfigurations.get(i);
/* 1303 */       if ((configuration.alt != min) && (
/* 1304 */         (nondeterministicAlts == null) || (nondeterministicAlts.contains(Utils.integer(configuration.alt)))))
/*      */       {
/* 1307 */         configuration.resolved = true;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected static int getMinAlt(Set<Integer> nondeterministicAlts)
/*      */   {
/* 1314 */     int min = 2147483647;
/* 1315 */     for (Iterator i$ = nondeterministicAlts.iterator(); i$.hasNext(); ) { Integer altI = (Integer)i$.next();
/* 1316 */       int alt = altI.intValue();
/* 1317 */       if (alt < min) {
/* 1318 */         min = alt;
/*      */       }
/*      */     }
/* 1321 */     return min;
/*      */   }
/*      */ 
/*      */   protected boolean tryToResolveWithSemanticPredicates(DFAState d, Set nondeterministicAlts)
/*      */   {
/* 1355 */     Map altToPredMap = getPredicatesPerNonDeterministicAlt(d, nondeterministicAlts);
/*      */ 
/* 1358 */     if (altToPredMap.size() == 0) {
/* 1359 */       return false;
/*      */     }
/*      */ 
/* 1363 */     this.dfa.probe.reportAltPredicateContext(d, altToPredMap);
/*      */ 
/* 1365 */     if (nondeterministicAlts.size() - altToPredMap.size() > 1)
/*      */     {
/* 1367 */       return false;
/*      */     }
/*      */ 
/* 1382 */     if (altToPredMap.size() == nondeterministicAlts.size() - 1)
/*      */     {
/* 1384 */       BitSet ndSet = BitSet.of(nondeterministicAlts);
/* 1385 */       BitSet predSet = BitSet.of(altToPredMap);
/* 1386 */       int nakedAlt = ndSet.subtract(predSet).getSingleElement();
/* 1387 */       SemanticContext nakedAltPred = null;
/* 1388 */       if (nakedAlt == max(nondeterministicAlts))
/*      */       {
/* 1390 */         nakedAltPred = new SemanticContext.TruePredicate();
/*      */       }
/*      */       else
/*      */       {
/* 1401 */         SemanticContext unionOfPredicatesFromAllAlts = getUnionOfPredicates(altToPredMap);
/*      */ 
/* 1404 */         if (unionOfPredicatesFromAllAlts.isSyntacticPredicate()) {
/* 1405 */           nakedAltPred = new SemanticContext.TruePredicate();
/*      */         }
/*      */         else {
/* 1408 */           nakedAltPred = SemanticContext.not(unionOfPredicatesFromAllAlts);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1415 */       altToPredMap.put(Utils.integer(nakedAlt), nakedAltPred);
/*      */ 
/* 1417 */       int numConfigs = d.nfaConfigurations.size();
/* 1418 */       for (int i = 0; i < numConfigs; i++)
/*      */       {
/* 1420 */         NFAConfiguration configuration = (NFAConfiguration)d.nfaConfigurations.get(i);
/* 1421 */         if (configuration.alt == nakedAlt) {
/* 1422 */           configuration.semanticContext = nakedAltPred;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1427 */     if (altToPredMap.size() == nondeterministicAlts.size())
/*      */     {
/* 1432 */       if (d.abortedDueToRecursionOverflow) {
/* 1433 */         d.dfa.probe.removeRecursiveOverflowState(d);
/*      */       }
/* 1435 */       int numConfigs = d.nfaConfigurations.size();
/*      */ 
/* 1437 */       for (int i = 0; i < numConfigs; i++) {
/* 1438 */         NFAConfiguration configuration = (NFAConfiguration)d.nfaConfigurations.get(i);
/* 1439 */         SemanticContext semCtx = (SemanticContext)altToPredMap.get(Utils.integer(configuration.alt));
/*      */ 
/* 1441 */         if (semCtx != null)
/*      */         {
/* 1445 */           configuration.resolveWithPredicate = true;
/*      */ 
/* 1447 */           configuration.semanticContext = semCtx;
/* 1448 */           altToPredMap.remove(Utils.integer(configuration.alt));
/*      */ 
/* 1451 */           if (semCtx.isSyntacticPredicate()) {
/* 1452 */             this.dfa.nfa.grammar.synPredUsedInDFA(this.dfa, semCtx);
/*      */           }
/*      */         }
/* 1455 */         else if (nondeterministicAlts.contains(Utils.integer(configuration.alt)))
/*      */         {
/* 1458 */           configuration.resolved = true;
/*      */         }
/*      */       }
/* 1461 */       return true;
/*      */     }
/*      */ 
/* 1464 */     return false;
/*      */   }
/*      */ 
/*      */   protected Map<Integer, SemanticContext> getPredicatesPerNonDeterministicAlt(DFAState d, Set nondeterministicAlts)
/*      */   {
/* 1490 */     Map altToPredicateContextMap = new HashMap();
/*      */ 
/* 1493 */     Map altToSetOfContextsMap = new HashMap();
/*      */ 
/* 1495 */     for (Iterator it = nondeterministicAlts.iterator(); it.hasNext(); ) {
/* 1496 */       Integer altI = (Integer)it.next();
/* 1497 */       altToSetOfContextsMap.put(altI, new OrderedHashSet());
/*      */     }
/*      */ 
/* 1509 */     Map altToLocationsReachableWithoutPredicate = new HashMap();
/* 1510 */     Set nondetAltsWithUncoveredConfiguration = new HashSet();
/*      */ 
/* 1514 */     int numConfigs = d.nfaConfigurations.size();
/* 1515 */     for (int i = 0; i < numConfigs; i++) {
/* 1516 */       NFAConfiguration configuration = (NFAConfiguration)d.nfaConfigurations.get(i);
/* 1517 */       Integer altI = Utils.integer(configuration.alt);
/*      */ 
/* 1519 */       if (nondeterministicAlts.contains(altI))
/*      */       {
/* 1521 */         if (configuration.semanticContext != SemanticContext.EMPTY_SEMANTIC_CONTEXT)
/*      */         {
/* 1524 */           Set predSet = (Set)altToSetOfContextsMap.get(altI);
/* 1525 */           predSet.add(configuration.semanticContext);
/*      */         }
/*      */         else
/*      */         {
/* 1531 */           nondetAltsWithUncoveredConfiguration.add(altI);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1560 */     List incompletelyCoveredAlts = new ArrayList();
/* 1561 */     for (Iterator it = nondeterministicAlts.iterator(); it.hasNext(); ) {
/* 1562 */       Integer altI = (Integer)it.next();
/* 1563 */       Set contextsForThisAlt = (Set)altToSetOfContextsMap.get(altI);
/* 1564 */       if (nondetAltsWithUncoveredConfiguration.contains(altI)) {
/* 1565 */         if (contextsForThisAlt.size() > 0)
/* 1566 */           incompletelyCoveredAlts.add(altI);
/*      */       }
/*      */       else
/*      */       {
/* 1570 */         SemanticContext combinedContext = null;
/* 1571 */         for (Iterator itrSet = contextsForThisAlt.iterator(); itrSet.hasNext(); ) {
/* 1572 */           SemanticContext ctx = (SemanticContext)itrSet.next();
/* 1573 */           combinedContext = SemanticContext.or(combinedContext, ctx);
/*      */         }
/*      */ 
/* 1576 */         altToPredicateContextMap.put(altI, combinedContext);
/*      */       }
/*      */     }
/* 1579 */     if (incompletelyCoveredAlts.size() > 0)
/*      */     {
/* 1591 */       for (int i = 0; i < numConfigs; i++) {
/* 1592 */         NFAConfiguration configuration = (NFAConfiguration)d.nfaConfigurations.get(i);
/* 1593 */         Integer altI = Utils.integer(configuration.alt);
/* 1594 */         if ((incompletelyCoveredAlts.contains(altI)) && (configuration.semanticContext == SemanticContext.EMPTY_SEMANTIC_CONTEXT))
/*      */         {
/* 1597 */           NFAState s = this.dfa.nfa.getState(configuration.state);
/*      */ 
/* 1608 */           if ((s.incidentEdgeLabel != null) && (s.incidentEdgeLabel.label != -1)) {
/* 1609 */             if ((s.associatedASTNode == null) || (s.associatedASTNode.token == null)) {
/* 1610 */               ErrorManager.internalError("no AST/token for nonepsilon target w/o predicate");
/*      */             }
/*      */             else {
/* 1613 */               Set locations = (Set)altToLocationsReachableWithoutPredicate.get(altI);
/* 1614 */               if (locations == null) {
/* 1615 */                 locations = new HashSet();
/* 1616 */                 altToLocationsReachableWithoutPredicate.put(altI, locations);
/*      */               }
/* 1618 */               locations.add(s.associatedASTNode.token);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 1623 */       this.dfa.probe.reportIncompletelyCoveredAlts(d, altToLocationsReachableWithoutPredicate);
/*      */     }
/*      */ 
/* 1627 */     return altToPredicateContextMap;
/*      */   }
/*      */ 
/*      */   protected static SemanticContext getUnionOfPredicates(Map altToPredMap)
/*      */   {
/* 1635 */     SemanticContext unionOfPredicatesFromAllAlts = null;
/* 1636 */     Iterator iter = altToPredMap.values().iterator();
/* 1637 */     while (iter.hasNext()) {
/* 1638 */       SemanticContext semCtx = (SemanticContext)iter.next();
/* 1639 */       if (unionOfPredicatesFromAllAlts == null) {
/* 1640 */         unionOfPredicatesFromAllAlts = semCtx;
/*      */       }
/*      */       else {
/* 1643 */         unionOfPredicatesFromAllAlts = SemanticContext.or(unionOfPredicatesFromAllAlts, semCtx);
/*      */       }
/*      */     }
/*      */ 
/* 1647 */     return unionOfPredicatesFromAllAlts;
/*      */   }
/*      */ 
/*      */   protected void addPredicateTransitions(DFAState d)
/*      */   {
/* 1660 */     List configsWithPreds = new ArrayList();
/*      */ 
/* 1662 */     int numConfigs = d.nfaConfigurations.size();
/* 1663 */     for (int i = 0; i < numConfigs; i++) {
/* 1664 */       NFAConfiguration c = (NFAConfiguration)d.nfaConfigurations.get(i);
/* 1665 */       if (c.resolveWithPredicate) {
/* 1666 */         configsWithPreds.add(c);
/*      */       }
/*      */     }
/*      */ 
/* 1670 */     Collections.sort(configsWithPreds, new Comparator()
/*      */     {
/*      */       public int compare(Object a, Object b) {
/* 1673 */         NFAConfiguration ca = (NFAConfiguration)a;
/* 1674 */         NFAConfiguration cb = (NFAConfiguration)b;
/* 1675 */         if (ca.alt < cb.alt) return -1;
/* 1676 */         if (ca.alt > cb.alt) return 1;
/* 1677 */         return 0;
/*      */       }
/*      */     });
/* 1680 */     List predConfigsSortedByAlt = configsWithPreds;
/*      */ 
/* 1682 */     for (int i = 0; i < predConfigsSortedByAlt.size(); i++) {
/* 1683 */       NFAConfiguration c = (NFAConfiguration)predConfigsSortedByAlt.get(i);
/* 1684 */       DFAState predDFATarget = d.dfa.getAcceptState(c.alt);
/* 1685 */       if (predDFATarget == null) {
/* 1686 */         predDFATarget = this.dfa.newState();
/*      */ 
/* 1688 */         predDFATarget.addNFAConfiguration(this.dfa.nfa.getState(c.state), c.alt, c.context, c.semanticContext);
/*      */ 
/* 1692 */         predDFATarget.setAcceptState(true);
/* 1693 */         this.dfa.setAcceptState(c.alt, predDFATarget);
/* 1694 */         DFAState existingState = this.dfa.addState(predDFATarget);
/* 1695 */         if (predDFATarget != existingState)
/*      */         {
/* 1699 */           this.dfa.setState(predDFATarget.stateNumber, existingState);
/* 1700 */           predDFATarget = existingState;
/*      */         }
/*      */       }
/*      */ 
/* 1704 */       d.addTransition(predDFATarget, new PredicateLabel(c.semanticContext));
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void initContextTrees(int numberOfAlts) {
/* 1709 */     this.contextTrees = new NFAContext[numberOfAlts];
/* 1710 */     for (int i = 0; i < this.contextTrees.length; i++) {
/* 1711 */       int alt = i + 1;
/*      */ 
/* 1716 */       this.contextTrees[i] = new NFAContext(null, null);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static int max(Set s) {
/* 1721 */     if (s == null) {
/* 1722 */       return -2147483648;
/*      */     }
/* 1724 */     int i = 0;
/* 1725 */     int m = 0;
/* 1726 */     for (Iterator it = s.iterator(); it.hasNext(); ) {
/* 1727 */       i++;
/* 1728 */       Integer I = (Integer)it.next();
/* 1729 */       if (i == 1) {
/* 1730 */         m = I.intValue();
/*      */       }
/* 1733 */       else if (I.intValue() > m) {
/* 1734 */         m = I.intValue();
/*      */       }
/*      */     }
/* 1737 */     return m;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.analysis.NFAToDFAConverter
 * JD-Core Version:    0.6.2
 */