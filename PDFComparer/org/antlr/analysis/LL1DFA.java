/*     */ package org.antlr.analysis;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.antlr.misc.IntervalSet;
/*     */ import org.antlr.misc.MultiMap;
/*     */ import org.antlr.tool.Grammar;
/*     */ import org.antlr.tool.GrammarAST;
/*     */ 
/*     */ public class LL1DFA extends DFA
/*     */ {
/*     */   public LL1DFA(int decisionNumber, NFAState decisionStartState, LookaheadSet[] altLook)
/*     */   {
/*  52 */     DFAState s0 = newState();
/*  53 */     this.startState = s0;
/*  54 */     this.nfa = decisionStartState.nfa;
/*  55 */     this.nAlts = this.nfa.grammar.getNumberOfAltsForDecisionNFA(decisionStartState);
/*  56 */     this.decisionNumber = decisionNumber;
/*  57 */     this.decisionNFAStartState = decisionStartState;
/*  58 */     initAltRelatedInfo();
/*  59 */     this.unreachableAlts = null;
/*  60 */     for (int alt = 1; alt < altLook.length; alt++) {
/*  61 */       DFAState acceptAltState = newState();
/*  62 */       acceptAltState.acceptState = true;
/*  63 */       setAcceptState(alt, acceptAltState);
/*  64 */       acceptAltState.k = 1;
/*  65 */       acceptAltState.cachedUniquelyPredicatedAlt = alt;
/*  66 */       Label e = getLabelForSet(altLook[alt].tokenTypeSet);
/*  67 */       s0.addTransition(acceptAltState, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public LL1DFA(int decisionNumber, NFAState decisionStartState, MultiMap<IntervalSet, Integer> edgeMap)
/*     */   {
/*  78 */     DFAState s0 = newState();
/*  79 */     this.startState = s0;
/*  80 */     this.nfa = decisionStartState.nfa;
/*  81 */     this.nAlts = this.nfa.grammar.getNumberOfAltsForDecisionNFA(decisionStartState);
/*  82 */     this.decisionNumber = decisionNumber;
/*  83 */     this.decisionNFAStartState = decisionStartState;
/*  84 */     initAltRelatedInfo();
/*  85 */     this.unreachableAlts = null;
/*  86 */     for (Iterator it = edgeMap.keySet().iterator(); it.hasNext(); ) {
/*  87 */       IntervalSet edge = (IntervalSet)it.next();
/*  88 */       List alts = (List)edgeMap.get(edge);
/*  89 */       Collections.sort(alts);
/*     */ 
/*  91 */       DFAState s = newState();
/*  92 */       s.k = 1;
/*  93 */       Label e = getLabelForSet(edge);
/*  94 */       s0.addTransition(s, e);
/*  95 */       if (alts.size() == 1) {
/*  96 */         s.acceptState = true;
/*  97 */         int alt = ((Integer)alts.get(0)).intValue();
/*  98 */         setAcceptState(alt, s);
/*  99 */         s.cachedUniquelyPredicatedAlt = alt;
/*     */       }
/*     */       else
/*     */       {
/* 104 */         s.resolvedWithPredicates = true;
/* 105 */         for (int i = 0; i < alts.size(); i++) {
/* 106 */           int alt = ((Integer)alts.get(i)).intValue();
/* 107 */           s.cachedUniquelyPredicatedAlt = -1;
/* 108 */           DFAState predDFATarget = getAcceptState(alt);
/* 109 */           if (predDFATarget == null) {
/* 110 */             predDFATarget = newState();
/* 111 */             predDFATarget.acceptState = true;
/* 112 */             predDFATarget.cachedUniquelyPredicatedAlt = alt;
/* 113 */             setAcceptState(alt, predDFATarget);
/*     */           }
/*     */ 
/* 128 */           SemanticContext.Predicate synpred = getSynPredForAlt(decisionStartState, alt);
/*     */ 
/* 130 */           if (synpred == null) {
/* 131 */             synpred = new SemanticContext.TruePredicate();
/*     */           }
/* 133 */           s.addTransition(predDFATarget, new PredicateLabel(synpred));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Label getLabelForSet(IntervalSet edgeSet)
/*     */   {
/* 141 */     Label e = null;
/* 142 */     int atom = edgeSet.getSingleElement();
/* 143 */     if (atom != -7) {
/* 144 */       e = new Label(atom);
/*     */     }
/*     */     else {
/* 147 */       e = new Label(edgeSet);
/*     */     }
/* 149 */     return e;
/*     */   }
/*     */ 
/*     */   protected SemanticContext.Predicate getSynPredForAlt(NFAState decisionStartState, int alt)
/*     */   {
/* 155 */     int walkAlt = decisionStartState.translateDisplayAltToWalkAlt(alt);
/*     */ 
/* 157 */     NFAState altLeftEdge = this.nfa.grammar.getNFAStateForAltOfDecision(decisionStartState, walkAlt);
/*     */ 
/* 159 */     NFAState altStartState = (NFAState)altLeftEdge.transition[0].target;
/*     */ 
/* 161 */     if (altStartState.transition[0].isSemanticPredicate()) {
/* 162 */       SemanticContext ctx = altStartState.transition[0].label.getSemanticContext();
/* 163 */       if (ctx.isSyntacticPredicate()) {
/* 164 */         SemanticContext.Predicate p = (SemanticContext.Predicate)ctx;
/* 165 */         if (p.predicateAST.getType() == 37)
/*     */         {
/* 170 */           if (ctx.isSyntacticPredicate()) {
/* 171 */             this.nfa.grammar.synPredUsedInDFA(this, ctx);
/*     */           }
/* 173 */           return (SemanticContext.Predicate)altStartState.transition[0].label.getSemanticContext();
/*     */         }
/*     */       }
/*     */     }
/* 177 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.analysis.LL1DFA
 * JD-Core Version:    0.6.2
 */