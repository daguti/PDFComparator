/*     */ package org.antlr.analysis;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.antlr.misc.Utils;
/*     */ import org.antlr.tool.Grammar;
/*     */ 
/*     */ public class DFAOptimizer
/*     */ {
/* 118 */   public static boolean PRUNE_EBNF_EXIT_BRANCHES = true;
/* 119 */   public static boolean PRUNE_TOKENS_RULE_SUPERFLUOUS_EOT_EDGES = true;
/* 120 */   public static boolean COLLAPSE_ALL_PARALLEL_EDGES = true;
/* 121 */   public static boolean MERGE_STOP_STATES = true;
/*     */ 
/* 128 */   protected Set visited = new HashSet();
/*     */   protected Grammar grammar;
/*     */ 
/*     */   public DFAOptimizer(Grammar grammar)
/*     */   {
/* 133 */     this.grammar = grammar;
/*     */   }
/*     */ 
/*     */   public void optimize()
/*     */   {
/* 138 */     for (int decisionNumber = 1; 
/* 139 */       decisionNumber <= this.grammar.getNumberOfDecisions(); 
/* 140 */       decisionNumber++)
/*     */     {
/* 142 */       DFA dfa = this.grammar.getLookaheadDFA(decisionNumber);
/* 143 */       optimize(dfa);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void optimize(DFA dfa) {
/* 148 */     if (dfa == null) {
/* 149 */       return;
/*     */     }
/*     */ 
/* 156 */     if ((PRUNE_EBNF_EXIT_BRANCHES) && (dfa.canInlineDecision())) {
/* 157 */       this.visited.clear();
/* 158 */       int decisionType = dfa.getNFADecisionStartState().decisionStateType;
/*     */ 
/* 160 */       if ((dfa.isGreedy()) && ((decisionType == 3) || (decisionType == 1)))
/*     */       {
/* 164 */         optimizeExitBranches(dfa.startState);
/*     */       }
/*     */     }
/*     */ 
/* 168 */     if ((PRUNE_TOKENS_RULE_SUPERFLUOUS_EOT_EDGES) && (dfa.isTokensRuleDecision()) && (dfa.probe.stateToSyntacticallyAmbiguousTokensRuleAltsMap.size() > 0))
/*     */     {
/* 172 */       this.visited.clear();
/* 173 */       optimizeEOTBranches(dfa.startState);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void optimizeExitBranches(DFAState d)
/*     */   {
/* 185 */     Integer sI = Utils.integer(d.stateNumber);
/* 186 */     if (this.visited.contains(sI)) {
/* 187 */       return;
/*     */     }
/* 189 */     this.visited.add(sI);
/* 190 */     int nAlts = d.dfa.getNumberOfAlts();
/* 191 */     for (int i = 0; i < d.getNumberOfTransitions(); i++) {
/* 192 */       Transition edge = d.transition(i);
/* 193 */       DFAState edgeTarget = (DFAState)edge.target;
/*     */ 
/* 200 */       if ((edgeTarget.isAcceptState()) && (edgeTarget.getUniquelyPredictedAlt() == nAlts))
/*     */       {
/* 207 */         d.removeTransition(i);
/* 208 */         i--;
/*     */       }
/* 210 */       optimizeExitBranches(edgeTarget);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void optimizeEOTBranches(DFAState d) {
/* 215 */     Integer sI = Utils.integer(d.stateNumber);
/* 216 */     if (this.visited.contains(sI)) {
/* 217 */       return;
/*     */     }
/* 219 */     this.visited.add(sI);
/* 220 */     for (int i = 0; i < d.getNumberOfTransitions(); i++) {
/* 221 */       Transition edge = d.transition(i);
/* 222 */       DFAState edgeTarget = (DFAState)edge.target;
/*     */ 
/* 229 */       if ((PRUNE_TOKENS_RULE_SUPERFLUOUS_EOT_EDGES) && (edgeTarget.isAcceptState()) && (d.getNumberOfTransitions() == 1) && (edge.label.isAtom()) && (edge.label.getAtom() == -2))
/*     */       {
/* 237 */         d.removeTransition(i);
/* 238 */         d.setAcceptState(true);
/*     */ 
/* 240 */         d.cachedUniquelyPredicatedAlt = edgeTarget.getUniquelyPredictedAlt();
/*     */ 
/* 242 */         i--;
/*     */       }
/* 244 */       optimizeEOTBranches(edgeTarget);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.analysis.DFAOptimizer
 * JD-Core Version:    0.6.2
 */