/*     */ package org.antlr.analysis;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.antlr.misc.IntSet;
/*     */ import org.antlr.misc.MultiMap;
/*     */ import org.antlr.misc.OrderedHashSet;
/*     */ import org.antlr.misc.Utils;
/*     */ import org.antlr.tool.Grammar;
/*     */ import org.antlr.tool.Rule;
/*     */ 
/*     */ public class DFAState extends State
/*     */ {
/*     */   public static final int INITIAL_NUM_TRANSITIONS = 4;
/*     */   public static final int PREDICTED_ALT_UNSET = -2;
/*     */   public DFA dfa;
/*  77 */   protected List<Transition> transitions = new ArrayList(4);
/*     */   protected int k;
/*  93 */   protected int acceptStateReachable = -2;
/*     */ 
/* 100 */   protected boolean resolvedWithPredicates = false;
/*     */ 
/* 106 */   public boolean abortedDueToRecursionOverflow = false;
/*     */ 
/* 124 */   protected boolean abortedDueToMultipleRecursiveAlts = false;
/*     */   protected int cachedHashCode;
/* 131 */   protected int cachedUniquelyPredicatedAlt = -2;
/*     */ 
/* 133 */   public int minAltInConfigurations = 2147483647;
/*     */ 
/* 135 */   public boolean atLeastOneConfigurationHasAPredicate = false;
/*     */ 
/* 138 */   public OrderedHashSet<NFAConfiguration> nfaConfigurations = new OrderedHashSet();
/*     */ 
/* 141 */   public List<NFAConfiguration> configurationsWithLabeledEdges = new ArrayList();
/*     */ 
/* 153 */   protected Set<NFAConfiguration> closureBusy = new HashSet();
/*     */   protected OrderedHashSet<Label> reachableLabels;
/*     */ 
/*     */   public DFAState(DFA dfa)
/*     */   {
/* 166 */     this.dfa = dfa;
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/* 171 */     this.configurationsWithLabeledEdges = null;
/* 172 */     this.closureBusy = null;
/* 173 */     this.reachableLabels = null;
/*     */   }
/*     */ 
/*     */   public Transition transition(int i) {
/* 177 */     return (Transition)this.transitions.get(i);
/*     */   }
/*     */ 
/*     */   public int getNumberOfTransitions() {
/* 181 */     return this.transitions.size();
/*     */   }
/*     */ 
/*     */   public void addTransition(Transition t) {
/* 185 */     this.transitions.add(t);
/*     */   }
/*     */ 
/*     */   public int addTransition(DFAState target, Label label)
/*     */   {
/* 192 */     this.transitions.add(new Transition(label, target));
/* 193 */     return this.transitions.size() - 1;
/*     */   }
/*     */ 
/*     */   public Transition getTransition(int trans) {
/* 197 */     return (Transition)this.transitions.get(trans);
/*     */   }
/*     */ 
/*     */   public void removeTransition(int trans) {
/* 201 */     this.transitions.remove(trans);
/*     */   }
/*     */ 
/*     */   public void addNFAConfiguration(NFAState state, NFAConfiguration c)
/*     */   {
/* 222 */     if (this.nfaConfigurations.contains(c)) {
/* 223 */       return;
/*     */     }
/*     */ 
/* 226 */     this.nfaConfigurations.add(c);
/*     */ 
/* 229 */     if (c.alt < this.minAltInConfigurations) {
/* 230 */       this.minAltInConfigurations = c.alt;
/*     */     }
/*     */ 
/* 233 */     if (c.semanticContext != SemanticContext.EMPTY_SEMANTIC_CONTEXT) {
/* 234 */       this.atLeastOneConfigurationHasAPredicate = true;
/*     */     }
/*     */ 
/* 239 */     this.cachedHashCode += c.state + c.alt;
/*     */ 
/* 243 */     if (state.transition[0] != null) {
/* 244 */       Label label = state.transition[0].label;
/* 245 */       if ((!label.isEpsilon()) && (!label.isSemanticPredicate()))
/*     */       {
/* 249 */         this.configurationsWithLabeledEdges.add(c);
/* 250 */         if (state.transition[1] == null)
/*     */         {
/* 252 */           c.singleAtomTransitionEmanating = true;
/*     */         }
/* 254 */         addReachableLabel(label);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public NFAConfiguration addNFAConfiguration(NFAState state, int alt, NFAContext context, SemanticContext semanticContext)
/*     */   {
/* 264 */     NFAConfiguration c = new NFAConfiguration(state.stateNumber, alt, context, semanticContext);
/*     */ 
/* 268 */     addNFAConfiguration(state, c);
/* 269 */     return c;
/*     */   }
/*     */ 
/*     */   protected void addReachableLabel(Label label)
/*     */   {
/* 306 */     if (this.reachableLabels == null) {
/* 307 */       this.reachableLabels = new OrderedHashSet();
/*     */     }
/*     */ 
/* 314 */     if (this.reachableLabels.contains(label)) {
/* 315 */       return;
/*     */     }
/* 317 */     IntSet t = label.getSet();
/* 318 */     IntSet remainder = t;
/* 319 */     int n = this.reachableLabels.size();
/*     */ 
/* 321 */     for (int i = 0; i < n; i++) {
/* 322 */       Label rl = (Label)this.reachableLabels.get(i);
/*     */ 
/* 328 */       if (Label.intersect(label, rl))
/*     */       {
/* 338 */         IntSet s_i = rl.getSet();
/* 339 */         IntSet intersection = s_i.and(t);
/* 340 */         this.reachableLabels.set(i, new Label(intersection));
/*     */ 
/* 343 */         IntSet existingMinusNewElements = s_i.subtract(t);
/*     */ 
/* 345 */         if (!existingMinusNewElements.isNil())
/*     */         {
/* 348 */           Label newLabel = new Label(existingMinusNewElements);
/* 349 */           this.reachableLabels.add(newLabel);
/*     */         }
/*     */ 
/* 358 */         remainder = t.subtract(s_i);
/* 359 */         if (remainder.isNil())
/*     */         {
/*     */           break;
/*     */         }
/* 363 */         t = remainder;
/*     */       }
/*     */     }
/* 365 */     if (!remainder.isNil())
/*     */     {
/* 371 */       Label newLabel = new Label(remainder);
/* 372 */       this.reachableLabels.add(newLabel);
/*     */     }
/*     */   }
/*     */ 
/*     */   public OrderedHashSet getReachableLabels()
/*     */   {
/* 381 */     return this.reachableLabels;
/*     */   }
/*     */ 
/*     */   public void setNFAConfigurations(OrderedHashSet<NFAConfiguration> configs) {
/* 385 */     this.nfaConfigurations = configs;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 393 */     if (this.cachedHashCode == 0)
/*     */     {
/* 396 */       return super.hashCode();
/*     */     }
/* 398 */     return this.cachedHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object o)
/*     */   {
/* 414 */     DFAState other = (DFAState)o;
/* 415 */     return this.nfaConfigurations.equals(other.nfaConfigurations);
/*     */   }
/*     */ 
/*     */   public int getUniquelyPredictedAlt()
/*     */   {
/* 426 */     if (this.cachedUniquelyPredicatedAlt != -2) {
/* 427 */       return this.cachedUniquelyPredicatedAlt;
/*     */     }
/* 429 */     int alt = -1;
/* 430 */     int numConfigs = this.nfaConfigurations.size();
/* 431 */     for (int i = 0; i < numConfigs; i++) {
/* 432 */       NFAConfiguration configuration = (NFAConfiguration)this.nfaConfigurations.get(i);
/*     */ 
/* 436 */       if (!configuration.resolved)
/*     */       {
/* 439 */         if (alt == -1) {
/* 440 */           alt = configuration.alt;
/*     */         }
/* 442 */         else if (configuration.alt != alt)
/* 443 */           return -1;
/*     */       }
/*     */     }
/* 446 */     this.cachedUniquelyPredicatedAlt = alt;
/* 447 */     return alt;
/*     */   }
/*     */ 
/*     */   public int getUniqueAlt()
/*     */   {
/* 455 */     int alt = -1;
/* 456 */     int numConfigs = this.nfaConfigurations.size();
/* 457 */     for (int i = 0; i < numConfigs; i++) {
/* 458 */       NFAConfiguration configuration = (NFAConfiguration)this.nfaConfigurations.get(i);
/* 459 */       if (alt == -1) {
/* 460 */         alt = configuration.alt;
/*     */       }
/* 462 */       else if (configuration.alt != alt) {
/* 463 */         return -1;
/*     */       }
/*     */     }
/* 466 */     return alt;
/*     */   }
/*     */ 
/*     */   public Set getDisabledAlternatives()
/*     */   {
/* 482 */     Set disabled = new LinkedHashSet();
/* 483 */     int numConfigs = this.nfaConfigurations.size();
/* 484 */     for (int i = 0; i < numConfigs; i++) {
/* 485 */       NFAConfiguration configuration = (NFAConfiguration)this.nfaConfigurations.get(i);
/* 486 */       if (configuration.resolved) {
/* 487 */         disabled.add(Utils.integer(configuration.alt));
/*     */       }
/*     */     }
/* 490 */     return disabled;
/*     */   }
/*     */ 
/*     */   protected Set getNonDeterministicAlts() {
/* 494 */     int user_k = this.dfa.getUserMaxLookahead();
/* 495 */     if ((user_k > 0) && (user_k == this.k))
/*     */     {
/* 498 */       return getAltSet();
/*     */     }
/* 500 */     if ((this.abortedDueToMultipleRecursiveAlts) || (this.abortedDueToRecursionOverflow))
/*     */     {
/* 502 */       return getAltSet();
/*     */     }
/*     */ 
/* 505 */     return getConflictingAlts();
/*     */   }
/*     */ 
/*     */   protected Set<Integer> getConflictingAlts()
/*     */   {
/* 528 */     Set nondeterministicAlts = new HashSet();
/*     */ 
/* 534 */     int numConfigs = this.nfaConfigurations.size();
/* 535 */     if (numConfigs <= 1) {
/* 536 */       return null;
/*     */     }
/*     */ 
/* 541 */     MultiMap stateToConfigListMap = new MultiMap();
/*     */ 
/* 543 */     for (int i = 0; i < numConfigs; i++) {
/* 544 */       NFAConfiguration configuration = (NFAConfiguration)this.nfaConfigurations.get(i);
/* 545 */       Integer stateI = Utils.integer(configuration.state);
/* 546 */       stateToConfigListMap.map(stateI, configuration);
/*     */     }
/*     */ 
/* 549 */     Set states = stateToConfigListMap.keySet();
/* 550 */     int numPotentialConflicts = 0;
/* 551 */     for (Iterator it = states.iterator(); it.hasNext(); ) {
/* 552 */       Integer stateI = (Integer)it.next();
/* 553 */       boolean thisStateHasPotentialProblem = false;
/* 554 */       List configsForState = (List)stateToConfigListMap.get(stateI);
/* 555 */       int alt = 0;
/* 556 */       int numConfigsForState = configsForState.size();
/* 557 */       for (int i = 0; (i < numConfigsForState) && (numConfigsForState > 1); i++) {
/* 558 */         NFAConfiguration c = (NFAConfiguration)configsForState.get(i);
/* 559 */         if (alt == 0) {
/* 560 */           alt = c.alt;
/*     */         }
/* 562 */         else if (c.alt != alt)
/*     */         {
/* 577 */           if ((this.dfa.nfa.grammar.type != 1) || (!this.dfa.decisionNFAStartState.enclosingRule.name.equals("Tokens")))
/*     */           {
/* 580 */             numPotentialConflicts++;
/* 581 */             thisStateHasPotentialProblem = true;
/*     */           }
/*     */         }
/*     */       }
/* 585 */       if (!thisStateHasPotentialProblem)
/*     */       {
/* 589 */         stateToConfigListMap.put(stateI, null);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 594 */     if (numPotentialConflicts == 0) {
/* 595 */       return null;
/*     */     }
/*     */ 
/* 611 */     for (Iterator it = states.iterator(); it.hasNext(); ) {
/* 612 */       Integer stateI = (Integer)it.next();
/* 613 */       List configsForState = (List)stateToConfigListMap.get(stateI);
/*     */ 
/* 616 */       int numConfigsForState = 0;
/* 617 */       if (configsForState != null) {
/* 618 */         numConfigsForState = configsForState.size();
/*     */       }
/* 620 */       for (int i = 0; i < numConfigsForState; i++) {
/* 621 */         NFAConfiguration s = (NFAConfiguration)configsForState.get(i);
/* 622 */         for (int j = i + 1; j < numConfigsForState; j++) {
/* 623 */           NFAConfiguration t = (NFAConfiguration)configsForState.get(j);
/*     */ 
/* 627 */           if ((s.alt != t.alt) && (s.context.conflictsWith(t.context))) {
/* 628 */             nondeterministicAlts.add(Utils.integer(s.alt));
/* 629 */             nondeterministicAlts.add(Utils.integer(t.alt));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 635 */     if (nondeterministicAlts.size() == 0) {
/* 636 */       return null;
/*     */     }
/* 638 */     return nondeterministicAlts;
/*     */   }
/*     */ 
/*     */   public Set getAltSet()
/*     */   {
/* 645 */     int numConfigs = this.nfaConfigurations.size();
/* 646 */     Set alts = new HashSet();
/* 647 */     for (int i = 0; i < numConfigs; i++) {
/* 648 */       NFAConfiguration configuration = (NFAConfiguration)this.nfaConfigurations.get(i);
/* 649 */       alts.add(Utils.integer(configuration.alt));
/*     */     }
/* 651 */     if (alts.size() == 0) {
/* 652 */       return null;
/*     */     }
/* 654 */     return alts;
/*     */   }
/*     */ 
/*     */   public Set getGatedSyntacticPredicatesInNFAConfigurations() {
/* 658 */     int numConfigs = this.nfaConfigurations.size();
/* 659 */     Set synpreds = new HashSet();
/* 660 */     for (int i = 0; i < numConfigs; i++) {
/* 661 */       NFAConfiguration configuration = (NFAConfiguration)this.nfaConfigurations.get(i);
/* 662 */       SemanticContext gatedPredExpr = configuration.semanticContext.getGatedPredicateContext();
/*     */ 
/* 665 */       if ((gatedPredExpr != null) && (configuration.semanticContext.isSyntacticPredicate()))
/*     */       {
/* 668 */         synpreds.add(configuration.semanticContext);
/*     */       }
/*     */     }
/* 671 */     if (synpreds.size() == 0) {
/* 672 */       return null;
/*     */     }
/* 674 */     return synpreds;
/*     */   }
/*     */ 
/*     */   public SemanticContext getGatedPredicatesInNFAConfigurations()
/*     */   {
/* 704 */     SemanticContext unionOfPredicatesFromAllAlts = null;
/* 705 */     int numConfigs = this.nfaConfigurations.size();
/* 706 */     for (int i = 0; i < numConfigs; i++) {
/* 707 */       NFAConfiguration configuration = (NFAConfiguration)this.nfaConfigurations.get(i);
/* 708 */       SemanticContext gatedPredExpr = configuration.semanticContext.getGatedPredicateContext();
/*     */ 
/* 710 */       if (gatedPredExpr == null)
/*     */       {
/* 714 */         return null;
/*     */       }
/* 716 */       if ((this.acceptState) || (!configuration.semanticContext.isSyntacticPredicate()))
/*     */       {
/* 722 */         if (unionOfPredicatesFromAllAlts == null) {
/* 723 */           unionOfPredicatesFromAllAlts = gatedPredExpr;
/*     */         }
/*     */         else {
/* 726 */           unionOfPredicatesFromAllAlts = SemanticContext.or(unionOfPredicatesFromAllAlts, gatedPredExpr);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 731 */     if ((unionOfPredicatesFromAllAlts instanceof SemanticContext.TruePredicate)) {
/* 732 */       return null;
/*     */     }
/* 734 */     return unionOfPredicatesFromAllAlts;
/*     */   }
/*     */ 
/*     */   public int getAcceptStateReachable()
/*     */   {
/* 739 */     return this.acceptStateReachable;
/*     */   }
/*     */ 
/*     */   public void setAcceptStateReachable(int acceptStateReachable) {
/* 743 */     this.acceptStateReachable = acceptStateReachable;
/*     */   }
/*     */ 
/*     */   public boolean isResolvedWithPredicates() {
/* 747 */     return this.resolvedWithPredicates;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 752 */     StringBuffer buf = new StringBuffer();
/* 753 */     buf.append(this.stateNumber + ":{");
/* 754 */     for (int i = 0; i < this.nfaConfigurations.size(); i++) {
/* 755 */       NFAConfiguration configuration = (NFAConfiguration)this.nfaConfigurations.get(i);
/* 756 */       if (i > 0) {
/* 757 */         buf.append(", ");
/*     */       }
/* 759 */       buf.append(configuration);
/*     */     }
/* 761 */     buf.append("}");
/* 762 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public int getLookaheadDepth() {
/* 766 */     return this.k;
/*     */   }
/*     */ 
/*     */   public void setLookaheadDepth(int k) {
/* 770 */     this.k = k;
/* 771 */     if (k > this.dfa.max_k)
/* 772 */       this.dfa.max_k = k;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.analysis.DFAState
 * JD-Core Version:    0.6.2
 */