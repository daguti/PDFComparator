/*      */ package org.antlr.analysis;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import org.antlr.codegen.CodeGenerator;
/*      */ import org.antlr.codegen.Target;
/*      */ import org.antlr.misc.IntSet;
/*      */ import org.antlr.misc.IntervalSet;
/*      */ import org.antlr.misc.Utils;
/*      */ import org.antlr.runtime.IntStream;
/*      */ import org.antlr.stringtemplate.StringTemplate;
/*      */ import org.antlr.tool.ErrorManager;
/*      */ import org.antlr.tool.FASerializer;
/*      */ import org.antlr.tool.Grammar;
/*      */ import org.antlr.tool.GrammarAST;
/*      */ import org.antlr.tool.Interpreter;
/*      */ import org.antlr.tool.Rule;
/*      */ 
/*      */ public class DFA
/*      */ {
/*      */   public static final int REACHABLE_UNKNOWN = -2;
/*      */   public static final int REACHABLE_BUSY = -1;
/*      */   public static final int REACHABLE_NO = 0;
/*      */   public static final int REACHABLE_YES = 1;
/*      */   public static final int CYCLIC_UNKNOWN = -2;
/*      */   public static final int CYCLIC_BUSY = -1;
/*      */   public static final int CYCLIC_DONE = 0;
/*   60 */   public static int MAX_TIME_PER_DFA_CREATION = 1000;
/*      */ 
/*   65 */   public static int MAX_STATE_TRANSITIONS_FOR_TABLE = 65534;
/*      */   public DFAState startState;
/*   71 */   public int decisionNumber = 0;
/*      */   public NFAState decisionNFAStartState;
/*      */   public String description;
/*   86 */   protected Map<DFAState, DFAState> uniqueStates = new HashMap();
/*      */ 
/*   99 */   protected Vector<DFAState> states = new Vector();
/*      */ 
/*  102 */   protected int stateCounter = 0;
/*      */ 
/*  105 */   protected int numberOfStates = 0;
/*      */ 
/*  110 */   protected int user_k = -1;
/*      */ 
/*  113 */   protected int max_k = -1;
/*      */ 
/*  116 */   protected boolean reduced = true;
/*      */ 
/*  121 */   protected boolean cyclic = false;
/*      */ 
/*  128 */   public boolean predicateVisible = false;
/*      */ 
/*  130 */   public boolean hasPredicateBlockedByAction = false;
/*      */   protected List<Integer> unreachableAlts;
/*  143 */   protected int nAlts = 0;
/*      */   protected DFAState[] altToAcceptState;
/*  151 */   public IntSet recursiveAltSet = new IntervalSet();
/*      */   public NFA nfa;
/*      */   protected NFAToDFAConverter nfaConverter;
/*  163 */   public DecisionProbe probe = new DecisionProbe(this);
/*      */ 
/*  186 */   public Map edgeTransitionClassMap = new LinkedHashMap();
/*      */ 
/*  193 */   protected int edgeTransitionClass = 0;
/*      */   public List specialStates;
/*      */   public List specialStateSTs;
/*      */   public Vector accept;
/*      */   public Vector eot;
/*      */   public Vector eof;
/*      */   public Vector min;
/*      */   public Vector max;
/*      */   public Vector special;
/*      */   public Vector transition;
/*      */   public Vector transitionEdgeTables;
/*  219 */   protected int uniqueCompressedSpecialStateNum = 0;
/*      */ 
/*  222 */   protected CodeGenerator generator = null;
/*      */ 
/*      */   protected DFA() {
/*      */   }
/*      */   public DFA(int decisionNumber, NFAState decisionStartState) {
/*  227 */     this.decisionNumber = decisionNumber;
/*  228 */     this.decisionNFAStartState = decisionStartState;
/*  229 */     this.nfa = decisionStartState.nfa;
/*  230 */     this.nAlts = this.nfa.grammar.getNumberOfAltsForDecisionNFA(decisionStartState);
/*      */ 
/*  232 */     initAltRelatedInfo();
/*      */ 
/*  235 */     this.nfaConverter = new NFAToDFAConverter(this);
/*      */     try {
/*  237 */       this.nfaConverter.convert();
/*      */ 
/*  240 */       verify();
/*      */ 
/*  242 */       if ((!this.probe.isDeterministic()) || (this.probe.analysisOverflowed())) {
/*  243 */         this.probe.issueWarnings();
/*      */       }
/*      */ 
/*  249 */       resetStateNumbersToBeContiguous();
/*      */     }
/*      */     catch (NonLLStarDecisionException nonLL)
/*      */     {
/*  261 */       this.probe.reportNonLLStarDecision(this);
/*      */ 
/*  263 */       if (!okToRetryDFAWithK1())
/*  264 */         this.probe.issueWarnings();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void resetStateNumbersToBeContiguous()
/*      */   {
/*  284 */     if (getUserMaxLookahead() > 0)
/*      */     {
/*  286 */       return;
/*      */     }
/*      */ 
/*  291 */     int snum = 0;
/*  292 */     for (int i = 0; i <= getMaxStateNumber(); i++) {
/*  293 */       DFAState s = getState(i);
/*      */ 
/*  296 */       if (s != null)
/*      */       {
/*  305 */         boolean alreadyRenumbered = s.stateNumber < i;
/*  306 */         if (!alreadyRenumbered)
/*      */         {
/*  308 */           s.stateNumber = snum;
/*  309 */           snum++;
/*      */         }
/*      */       }
/*      */     }
/*  312 */     if (snum != getNumberOfStates())
/*  313 */       ErrorManager.internalError("DFA " + this.decisionNumber + ": " + this.decisionNFAStartState.getDescription() + " num unique states " + getNumberOfStates() + "!= num renumbered states " + snum);
/*      */   }
/*      */ 
/*      */   public List getJavaCompressedAccept()
/*      */   {
/*  323 */     return getRunLengthEncoding(this.accept); } 
/*  324 */   public List getJavaCompressedEOT() { return getRunLengthEncoding(this.eot); } 
/*  325 */   public List getJavaCompressedEOF() { return getRunLengthEncoding(this.eof); } 
/*  326 */   public List getJavaCompressedMin() { return getRunLengthEncoding(this.min); } 
/*  327 */   public List getJavaCompressedMax() { return getRunLengthEncoding(this.max); } 
/*  328 */   public List getJavaCompressedSpecial() { return getRunLengthEncoding(this.special); } 
/*      */   public List getJavaCompressedTransition() {
/*  330 */     if ((this.transition == null) || (this.transition.size() == 0)) {
/*  331 */       return null;
/*      */     }
/*  333 */     List encoded = new ArrayList(this.transition.size());
/*      */ 
/*  335 */     for (int i = 0; i < this.transition.size(); i++) {
/*  336 */       Vector transitionsForState = (Vector)this.transition.elementAt(i);
/*  337 */       encoded.add(getRunLengthEncoding(transitionsForState));
/*      */     }
/*  339 */     return encoded;
/*      */   }
/*      */ 
/*      */   public List getRunLengthEncoding(List data)
/*      */   {
/*  353 */     if ((data == null) || (data.size() == 0))
/*      */     {
/*  356 */       List empty = new ArrayList();
/*  357 */       empty.add("");
/*  358 */       return empty;
/*      */     }
/*  360 */     int size = Math.max(2, data.size() / 2);
/*  361 */     List encoded = new ArrayList(size);
/*      */ 
/*  363 */     int i = 0;
/*  364 */     Integer emptyValue = Utils.integer(-1);
/*  365 */     while (i < data.size()) {
/*  366 */       Integer I = (Integer)data.get(i);
/*  367 */       if (I == null) {
/*  368 */         I = emptyValue;
/*      */       }
/*      */ 
/*  371 */       int n = 0;
/*  372 */       for (int j = i; j < data.size(); j++) {
/*  373 */         Integer v = (Integer)data.get(j);
/*  374 */         if (v == null) {
/*  375 */           v = emptyValue;
/*      */         }
/*  377 */         if (!I.equals(v)) break;
/*  378 */         n++;
/*      */       }
/*      */ 
/*  384 */       encoded.add(this.generator.target.encodeIntAsCharEscape((char)n));
/*  385 */       encoded.add(this.generator.target.encodeIntAsCharEscape((char)I.intValue()));
/*  386 */       i += n;
/*      */     }
/*  388 */     return encoded;
/*      */   }
/*      */ 
/*      */   public void createStateTables(CodeGenerator generator)
/*      */   {
/*  393 */     this.generator = generator;
/*  394 */     this.description = getNFADecisionStartState().getDescription();
/*  395 */     this.description = generator.target.getTargetStringLiteralFromString(this.description);
/*      */ 
/*  399 */     this.special = new Vector(getNumberOfStates());
/*  400 */     this.special.setSize(getNumberOfStates());
/*  401 */     this.specialStates = new ArrayList();
/*  402 */     this.specialStateSTs = new ArrayList();
/*  403 */     this.accept = new Vector(getNumberOfStates());
/*  404 */     this.accept.setSize(getNumberOfStates());
/*  405 */     this.eot = new Vector(getNumberOfStates());
/*  406 */     this.eot.setSize(getNumberOfStates());
/*  407 */     this.eof = new Vector(getNumberOfStates());
/*  408 */     this.eof.setSize(getNumberOfStates());
/*  409 */     this.min = new Vector(getNumberOfStates());
/*  410 */     this.min.setSize(getNumberOfStates());
/*  411 */     this.max = new Vector(getNumberOfStates());
/*  412 */     this.max.setSize(getNumberOfStates());
/*  413 */     this.transition = new Vector(getNumberOfStates());
/*  414 */     this.transition.setSize(getNumberOfStates());
/*  415 */     this.transitionEdgeTables = new Vector(getNumberOfStates());
/*  416 */     this.transitionEdgeTables.setSize(getNumberOfStates());
/*      */ 
/*  419 */     Iterator it = null;
/*  420 */     if (getUserMaxLookahead() > 0) {
/*  421 */       it = this.states.iterator();
/*      */     }
/*      */     else {
/*  424 */       it = getUniqueStates().values().iterator();
/*      */     }
/*  426 */     while (it.hasNext()) {
/*  427 */       DFAState s = (DFAState)it.next();
/*  428 */       if (s != null)
/*      */       {
/*  433 */         if (s.isAcceptState())
/*      */         {
/*  435 */           this.accept.set(s.stateNumber, Utils.integer(s.getUniquelyPredictedAlt()));
/*      */         }
/*      */         else
/*      */         {
/*  439 */           createMinMaxTables(s);
/*  440 */           createTransitionTableEntryForState(s);
/*  441 */           createSpecialTable(s);
/*  442 */           createEOTAndEOFTables(s);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  447 */     for (int i = 0; i < this.specialStates.size(); i++) {
/*  448 */       DFAState ss = (DFAState)this.specialStates.get(i);
/*  449 */       StringTemplate stateST = generator.generateSpecialState(ss);
/*      */ 
/*  451 */       this.specialStateSTs.add(stateST);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void createMinMaxTables(DFAState s)
/*      */   {
/*  501 */     int smin = 65536;
/*  502 */     int smax = -3;
/*  503 */     for (int j = 0; j < s.getNumberOfTransitions(); j++) {
/*  504 */       Transition edge = s.transition(j);
/*  505 */       Label label = edge.label;
/*  506 */       if (label.isAtom()) {
/*  507 */         if (label.getAtom() >= 0) {
/*  508 */           if (label.getAtom() < smin) {
/*  509 */             smin = label.getAtom();
/*      */           }
/*  511 */           if (label.getAtom() > smax) {
/*  512 */             smax = label.getAtom();
/*      */           }
/*      */         }
/*      */       }
/*  516 */       else if (label.isSet()) {
/*  517 */         IntervalSet labels = (IntervalSet)label.getSet();
/*  518 */         int lmin = labels.getMinElement();
/*      */ 
/*  520 */         if ((lmin < smin) && (lmin >= 0)) {
/*  521 */           smin = labels.getMinElement();
/*      */         }
/*  523 */         if (labels.getMaxElement() > smax) {
/*  524 */           smax = labels.getMaxElement();
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  529 */     if (smax < 0)
/*      */     {
/*  531 */       smin = 0;
/*  532 */       smax = 0;
/*      */     }
/*      */ 
/*  535 */     this.min.set(s.stateNumber, Utils.integer((char)smin));
/*  536 */     this.max.set(s.stateNumber, Utils.integer((char)smax));
/*      */ 
/*  538 */     if ((smax < 0) || (smin > 65535) || (smin < 0))
/*  539 */       ErrorManager.internalError("messed up: min=" + this.min + ", max=" + this.max);
/*      */   }
/*      */ 
/*      */   protected void createTransitionTableEntryForState(DFAState s)
/*      */   {
/*  548 */     int smax = ((Integer)this.max.get(s.stateNumber)).intValue();
/*  549 */     int smin = ((Integer)this.min.get(s.stateNumber)).intValue();
/*      */ 
/*  551 */     Vector stateTransitions = new Vector(smax - smin + 1);
/*  552 */     stateTransitions.setSize(smax - smin + 1);
/*  553 */     this.transition.set(s.stateNumber, stateTransitions);
/*  554 */     for (int j = 0; j < s.getNumberOfTransitions(); j++) {
/*  555 */       Transition edge = s.transition(j);
/*  556 */       Label label = edge.label;
/*  557 */       if ((label.isAtom()) && (label.getAtom() >= 0)) {
/*  558 */         int labelIndex = label.getAtom() - smin;
/*  559 */         stateTransitions.set(labelIndex, Utils.integer(edge.target.stateNumber));
/*      */       }
/*  562 */       else if (label.isSet()) {
/*  563 */         IntervalSet labels = (IntervalSet)label.getSet();
/*  564 */         int[] atoms = labels.toArray();
/*  565 */         for (int a = 0; a < atoms.length; a++)
/*      */         {
/*  567 */           if (atoms[a] >= 0) {
/*  568 */             int labelIndex = atoms[a] - smin;
/*  569 */             stateTransitions.set(labelIndex, Utils.integer(edge.target.stateNumber));
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  576 */     Integer edgeClass = (Integer)this.edgeTransitionClassMap.get(stateTransitions);
/*  577 */     if (edgeClass != null)
/*      */     {
/*  579 */       this.transitionEdgeTables.set(s.stateNumber, edgeClass);
/*      */     }
/*      */     else {
/*  582 */       edgeClass = Utils.integer(this.edgeTransitionClass);
/*  583 */       this.transitionEdgeTables.set(s.stateNumber, edgeClass);
/*  584 */       this.edgeTransitionClassMap.put(stateTransitions, edgeClass);
/*  585 */       this.edgeTransitionClass += 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void createEOTAndEOFTables(DFAState s)
/*      */   {
/*  593 */     for (int j = 0; j < s.getNumberOfTransitions(); j++) {
/*  594 */       Transition edge = s.transition(j);
/*  595 */       Label label = edge.label;
/*  596 */       if (label.isAtom()) {
/*  597 */         if (label.getAtom() == -2)
/*      */         {
/*  599 */           this.eot.set(s.stateNumber, Utils.integer(edge.target.stateNumber));
/*      */         }
/*  601 */         else if (label.getAtom() == -1)
/*      */         {
/*  603 */           this.eof.set(s.stateNumber, Utils.integer(edge.target.stateNumber));
/*      */         }
/*      */       }
/*  606 */       else if (label.isSet()) {
/*  607 */         IntervalSet labels = (IntervalSet)label.getSet();
/*  608 */         int[] atoms = labels.toArray();
/*  609 */         for (int a = 0; a < atoms.length; a++)
/*  610 */           if (atoms[a] == -2)
/*      */           {
/*  612 */             this.eot.set(s.stateNumber, Utils.integer(edge.target.stateNumber));
/*      */           }
/*  614 */           else if (atoms[a] == -1)
/*  615 */             this.eof.set(s.stateNumber, Utils.integer(edge.target.stateNumber));
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void createSpecialTable(DFAState s)
/*      */   {
/*  624 */     boolean hasSemPred = false;
/*      */ 
/*  627 */     for (int j = 0; j < s.getNumberOfTransitions(); j++) {
/*  628 */       Transition edge = s.transition(j);
/*  629 */       Label label = edge.label;
/*      */ 
/*  632 */       if ((label.isSemanticPredicate()) || (((DFAState)edge.target).getGatedPredicatesInNFAConfigurations() != null))
/*      */       {
/*  635 */         hasSemPred = true;
/*  636 */         break;
/*      */       }
/*      */     }
/*      */ 
/*  640 */     int smax = ((Integer)this.max.get(s.stateNumber)).intValue();
/*  641 */     int smin = ((Integer)this.min.get(s.stateNumber)).intValue();
/*  642 */     if ((hasSemPred) || (smax - smin > MAX_STATE_TRANSITIONS_FOR_TABLE)) {
/*  643 */       this.special.set(s.stateNumber, Utils.integer(this.uniqueCompressedSpecialStateNum));
/*      */ 
/*  645 */       this.uniqueCompressedSpecialStateNum += 1;
/*  646 */       this.specialStates.add(s);
/*      */     }
/*      */     else {
/*  649 */       this.special.set(s.stateNumber, Utils.integer(-1));
/*      */     }
/*      */   }
/*      */ 
/*      */   public int predict(IntStream input) {
/*  654 */     Interpreter interp = new Interpreter(this.nfa.grammar, input);
/*  655 */     return interp.predict(this);
/*      */   }
/*      */ 
/*      */   protected DFAState addState(DFAState d)
/*      */   {
/*  666 */     if (getUserMaxLookahead() > 0) {
/*  667 */       return d;
/*      */     }
/*      */ 
/*  671 */     DFAState existing = (DFAState)this.uniqueStates.get(d);
/*  672 */     if (existing != null)
/*      */     {
/*  678 */       return existing;
/*      */     }
/*      */ 
/*  682 */     this.uniqueStates.put(d, d);
/*  683 */     this.numberOfStates += 1;
/*  684 */     return d;
/*      */   }
/*      */ 
/*      */   public void removeState(DFAState d) {
/*  688 */     DFAState it = (DFAState)this.uniqueStates.remove(d);
/*  689 */     if (it != null)
/*  690 */       this.numberOfStates -= 1;
/*      */   }
/*      */ 
/*      */   public Map<DFAState, DFAState> getUniqueStates()
/*      */   {
/*  695 */     return this.uniqueStates;
/*      */   }
/*      */ 
/*      */   public int getMaxStateNumber()
/*      */   {
/*  702 */     return this.states.size() - 1;
/*      */   }
/*      */ 
/*      */   public DFAState getState(int stateNumber) {
/*  706 */     return (DFAState)this.states.get(stateNumber);
/*      */   }
/*      */ 
/*      */   public void setState(int stateNumber, DFAState d) {
/*  710 */     this.states.set(stateNumber, d);
/*      */   }
/*      */ 
/*      */   public boolean isReduced()
/*      */   {
/*  719 */     return this.reduced;
/*      */   }
/*      */ 
/*      */   public boolean isCyclic()
/*      */   {
/*  729 */     return (this.cyclic) && (getUserMaxLookahead() == 0);
/*      */   }
/*      */ 
/*      */   public boolean isClassicDFA() {
/*  733 */     return (!isCyclic()) && (!this.nfa.grammar.decisionsWhoseDFAsUsesSemPreds.contains(this)) && (!this.nfa.grammar.decisionsWhoseDFAsUsesSynPreds.contains(this));
/*      */   }
/*      */ 
/*      */   public boolean canInlineDecision()
/*      */   {
/*  739 */     return (!isCyclic()) && (!this.probe.isNonLLStarDecision()) && (getNumberOfStates() < CodeGenerator.MAX_ACYCLIC_DFA_STATES_INLINE);
/*      */   }
/*      */ 
/*      */   public boolean isTokensRuleDecision()
/*      */   {
/*  746 */     if (this.nfa.grammar.type != 1) {
/*  747 */       return false;
/*      */     }
/*  749 */     NFAState nfaStart = getNFADecisionStartState();
/*  750 */     Rule r = this.nfa.grammar.getLocallyDefinedRule("Tokens");
/*  751 */     NFAState TokensRuleStart = r.startState;
/*  752 */     NFAState TokensDecisionStart = (NFAState)TokensRuleStart.transition[0].target;
/*      */ 
/*  754 */     return nfaStart == TokensDecisionStart;
/*      */   }
/*      */ 
/*      */   public int getUserMaxLookahead()
/*      */   {
/*  762 */     if (this.user_k >= 0) {
/*  763 */       return this.user_k;
/*      */     }
/*  765 */     this.user_k = this.nfa.grammar.getUserMaxLookahead(this.decisionNumber);
/*  766 */     return this.user_k;
/*      */   }
/*      */ 
/*      */   public boolean getAutoBacktrackMode() {
/*  770 */     return this.nfa.grammar.getAutoBacktrackMode(this.decisionNumber);
/*      */   }
/*      */ 
/*      */   public void setUserMaxLookahead(int k) {
/*  774 */     this.user_k = k;
/*      */   }
/*      */ 
/*      */   public int getMaxLookaheadDepth()
/*      */   {
/*  780 */     if (hasCycle()) return 2147483647;
/*      */ 
/*  782 */     return _getMaxLookaheadDepth(this.startState, 0);
/*      */   }
/*      */ 
/*      */   int _getMaxLookaheadDepth(DFAState d, int depth)
/*      */   {
/*  788 */     int max = depth;
/*  789 */     for (int i = 0; i < d.getNumberOfTransitions(); i++) {
/*  790 */       Transition t = d.transition(i);
/*      */ 
/*  792 */       if (!t.isSemanticPredicate())
/*      */       {
/*  794 */         DFAState edgeTarget = (DFAState)t.target;
/*  795 */         int m = _getMaxLookaheadDepth(edgeTarget, depth + 1);
/*  796 */         max = Math.max(max, m);
/*      */       }
/*      */     }
/*  799 */     return max;
/*      */   }
/*      */ 
/*      */   public boolean hasSynPred()
/*      */   {
/*  817 */     boolean has = _hasSynPred(this.startState, new HashSet());
/*      */ 
/*  824 */     return has;
/*      */   }
/*      */   public boolean getHasSynPred() {
/*  827 */     return hasSynPred();
/*      */   }
/*      */   boolean _hasSynPred(DFAState d, Set<DFAState> busy) {
/*  830 */     busy.add(d);
/*  831 */     for (int i = 0; i < d.getNumberOfTransitions(); i++) {
/*  832 */       Transition t = d.transition(i);
/*  833 */       if (t.isSemanticPredicate()) {
/*  834 */         SemanticContext ctx = t.label.getSemanticContext();
/*      */ 
/*  839 */         if (ctx.isSyntacticPredicate()) return true;
/*      */       }
/*  841 */       DFAState edgeTarget = (DFAState)t.target;
/*  842 */       if ((!busy.contains(edgeTarget)) && (_hasSynPred(edgeTarget, busy))) return true;
/*      */     }
/*      */ 
/*  845 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean hasSemPred() {
/*  849 */     boolean has = _hasSemPred(this.startState, new HashSet());
/*  850 */     return has;
/*      */   }
/*      */ 
/*      */   boolean _hasSemPred(DFAState d, Set<DFAState> busy) {
/*  854 */     busy.add(d);
/*  855 */     for (int i = 0; i < d.getNumberOfTransitions(); i++) {
/*  856 */       Transition t = d.transition(i);
/*  857 */       if (t.isSemanticPredicate()) {
/*  858 */         SemanticContext ctx = t.label.getSemanticContext();
/*  859 */         if (ctx.hasUserSemanticPredicate()) return true;
/*      */       }
/*  861 */       DFAState edgeTarget = (DFAState)t.target;
/*  862 */       if ((!busy.contains(edgeTarget)) && (_hasSemPred(edgeTarget, busy))) return true;
/*      */     }
/*      */ 
/*  865 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean hasCycle()
/*      */   {
/*  870 */     boolean cyclic = _hasCycle(this.startState, new HashMap());
/*  871 */     return cyclic;
/*      */   }
/*      */ 
/*      */   boolean _hasCycle(DFAState d, Map<DFAState, Integer> busy) {
/*  875 */     busy.put(d, new Integer(-1));
/*  876 */     for (int i = 0; i < d.getNumberOfTransitions(); i++) {
/*  877 */       Transition t = d.transition(i);
/*  878 */       DFAState target = (DFAState)t.target;
/*  879 */       int cond = -2;
/*  880 */       if (busy.get(target) != null) cond = ((Integer)busy.get(target)).intValue();
/*  881 */       if (cond == -1) return true;
/*  882 */       if ((cond != 0) && (_hasCycle(target, busy))) return true;
/*      */     }
/*  884 */     busy.put(d, new Integer(0));
/*  885 */     return false;
/*      */   }
/*      */ 
/*      */   public List<Integer> getUnreachableAlts()
/*      */   {
/*  894 */     return this.unreachableAlts;
/*      */   }
/*      */ 
/*      */   public void verify()
/*      */   {
/*  910 */     doesStateReachAcceptState(this.startState);
/*      */   }
/*      */ 
/*      */   protected boolean doesStateReachAcceptState(DFAState d)
/*      */   {
/*  927 */     if (d.isAcceptState())
/*      */     {
/*  929 */       d.setAcceptStateReachable(1);
/*      */ 
/*  931 */       int predicts = d.getUniquelyPredictedAlt();
/*  932 */       this.unreachableAlts.remove(Utils.integer(predicts));
/*  933 */       return true;
/*      */     }
/*      */ 
/*  937 */     d.setAcceptStateReachable(-1);
/*      */ 
/*  939 */     boolean anEdgeReachesAcceptState = false;
/*      */ 
/*  943 */     for (int i = 0; i < d.getNumberOfTransitions(); i++) {
/*  944 */       Transition t = d.transition(i);
/*  945 */       DFAState edgeTarget = (DFAState)t.target;
/*  946 */       int targetStatus = edgeTarget.getAcceptStateReachable();
/*  947 */       if (targetStatus == -1) {
/*  948 */         this.cyclic = true;
/*      */       }
/*  951 */       else if (targetStatus == 1) {
/*  952 */         anEdgeReachesAcceptState = true;
/*      */       }
/*  955 */       else if (targetStatus != 0)
/*      */       {
/*  959 */         if (doesStateReachAcceptState(edgeTarget)) {
/*  960 */           anEdgeReachesAcceptState = true;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  965 */     if (anEdgeReachesAcceptState) {
/*  966 */       d.setAcceptStateReachable(1);
/*      */     }
/*      */     else {
/*  969 */       d.setAcceptStateReachable(0);
/*  970 */       this.reduced = false;
/*      */     }
/*  972 */     return anEdgeReachesAcceptState;
/*      */   }
/*      */ 
/*      */   public void findAllGatedSynPredsUsedInDFAAcceptStates()
/*      */   {
/*  983 */     int nAlts = getNumberOfAlts();
/*      */     Iterator it;
/*  984 */     for (int i = 1; i <= nAlts; i++) {
/*  985 */       DFAState a = getAcceptState(i);
/*      */ 
/*  987 */       if (a != null) {
/*  988 */         Set synpreds = a.getGatedSyntacticPredicatesInNFAConfigurations();
/*  989 */         if (synpreds != null)
/*      */         {
/*  991 */           for (it = synpreds.iterator(); it.hasNext(); ) {
/*  992 */             SemanticContext semctx = (SemanticContext)it.next();
/*      */ 
/*  994 */             this.nfa.grammar.synPredUsedInDFA(this, semctx);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public NFAState getNFADecisionStartState() {
/* 1002 */     return this.decisionNFAStartState;
/*      */   }
/*      */ 
/*      */   public DFAState getAcceptState(int alt) {
/* 1006 */     return this.altToAcceptState[alt];
/*      */   }
/*      */ 
/*      */   public void setAcceptState(int alt, DFAState acceptState) {
/* 1010 */     this.altToAcceptState[alt] = acceptState;
/*      */   }
/*      */ 
/*      */   public String getDescription() {
/* 1014 */     return this.description;
/*      */   }
/*      */ 
/*      */   public int getDecisionNumber() {
/* 1018 */     return this.decisionNFAStartState.getDecisionNumber();
/*      */   }
/*      */ 
/*      */   public boolean okToRetryDFAWithK1()
/*      */   {
/* 1029 */     boolean nonLLStarOrOverflowAndPredicateVisible = ((this.probe.isNonLLStarDecision()) || (this.probe.analysisOverflowed())) && (this.predicateVisible);
/*      */ 
/* 1032 */     return (getUserMaxLookahead() != 1) && (nonLLStarOrOverflowAndPredicateVisible);
/*      */   }
/*      */ 
/*      */   public String getReasonForFailure()
/*      */   {
/* 1037 */     StringBuffer buf = new StringBuffer();
/* 1038 */     if (this.probe.isNonLLStarDecision()) {
/* 1039 */       buf.append("non-LL(*)");
/* 1040 */       if (this.predicateVisible) {
/* 1041 */         buf.append(" && predicate visible");
/*      */       }
/*      */     }
/* 1044 */     if (this.probe.analysisOverflowed()) {
/* 1045 */       buf.append("recursion overflow");
/* 1046 */       if (this.predicateVisible) {
/* 1047 */         buf.append(" && predicate visible");
/*      */       }
/*      */     }
/* 1050 */     buf.append("\n");
/* 1051 */     return buf.toString();
/*      */   }
/*      */ 
/*      */   public GrammarAST getDecisionASTNode()
/*      */   {
/* 1059 */     return this.decisionNFAStartState.associatedASTNode;
/*      */   }
/*      */ 
/*      */   public boolean isGreedy() {
/* 1063 */     GrammarAST blockAST = this.nfa.grammar.getDecisionBlockAST(this.decisionNumber);
/* 1064 */     Object v = this.nfa.grammar.getBlockOption(blockAST, "greedy");
/* 1065 */     if ((v != null) && (v.equals("false"))) {
/* 1066 */       return false;
/*      */     }
/* 1068 */     return true;
/*      */   }
/*      */ 
/*      */   public DFAState newState()
/*      */   {
/* 1073 */     DFAState n = new DFAState(this);
/* 1074 */     n.stateNumber = this.stateCounter;
/* 1075 */     this.stateCounter += 1;
/* 1076 */     this.states.setSize(n.stateNumber + 1);
/* 1077 */     this.states.set(n.stateNumber, n);
/* 1078 */     return n;
/*      */   }
/*      */ 
/*      */   public int getNumberOfStates() {
/* 1082 */     if (getUserMaxLookahead() > 0)
/*      */     {
/* 1084 */       return this.states.size();
/*      */     }
/* 1086 */     return this.numberOfStates;
/*      */   }
/*      */ 
/*      */   public int getNumberOfAlts() {
/* 1090 */     return this.nAlts;
/*      */   }
/*      */ 
/*      */   protected void initAltRelatedInfo()
/*      */   {
/* 1098 */     this.unreachableAlts = new LinkedList();
/* 1099 */     for (int i = 1; i <= this.nAlts; i++) {
/* 1100 */       this.unreachableAlts.add(Utils.integer(i));
/*      */     }
/* 1102 */     this.altToAcceptState = new DFAState[this.nAlts + 1];
/*      */   }
/*      */ 
/*      */   public String toString() {
/* 1106 */     FASerializer serializer = new FASerializer(this.nfa.grammar);
/* 1107 */     if (this.startState == null) {
/* 1108 */       return "";
/*      */     }
/* 1110 */     return serializer.serialize(this.startState, false);
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.analysis.DFA
 * JD-Core Version:    0.6.2
 */