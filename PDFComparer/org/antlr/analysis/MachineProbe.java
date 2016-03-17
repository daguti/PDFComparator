/*     */ package org.antlr.analysis;
/*     */ 
/*     */ import antlr.CommonToken;
/*     */ import antlr.Token;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.antlr.misc.IntSet;
/*     */ import org.antlr.tool.Grammar;
/*     */ import org.antlr.tool.GrammarAST;
/*     */ 
/*     */ public class MachineProbe
/*     */ {
/*     */   DFA dfa;
/*     */ 
/*     */   public MachineProbe(DFA dfa)
/*     */   {
/*  45 */     this.dfa = dfa;
/*     */   }
/*     */ 
/*     */   List<DFAState> getAnyDFAPathToTarget(DFAState targetState) {
/*  49 */     Set visited = new HashSet();
/*  50 */     return getAnyDFAPathToTarget(this.dfa.startState, targetState, visited);
/*     */   }
/*     */ 
/*     */   public List<DFAState> getAnyDFAPathToTarget(DFAState startState, DFAState targetState, Set<DFAState> visited)
/*     */   {
/*  55 */     List dfaStates = new ArrayList();
/*  56 */     visited.add(startState);
/*  57 */     if (startState.equals(targetState)) {
/*  58 */       dfaStates.add(targetState);
/*  59 */       return dfaStates;
/*     */     }
/*     */ 
/*  63 */     for (int i = 0; i < startState.getNumberOfTransitions(); i++) {
/*  64 */       Transition e = startState.getTransition(i);
/*  65 */       if (!visited.contains(e.target)) {
/*  66 */         List path = getAnyDFAPathToTarget((DFAState)e.target, targetState, visited);
/*     */ 
/*  68 */         if (path != null) {
/*  69 */           dfaStates.add(startState);
/*  70 */           dfaStates.addAll(path);
/*  71 */           return dfaStates;
/*     */         }
/*     */       }
/*     */     }
/*  75 */     return null;
/*     */   }
/*     */ 
/*     */   public List<IntSet> getEdgeLabels(DFAState targetState)
/*     */   {
/*  80 */     List dfaStates = getAnyDFAPathToTarget(targetState);
/*  81 */     List labels = new ArrayList();
/*  82 */     for (int i = 0; i < dfaStates.size() - 1; i++) {
/*  83 */       DFAState d = (DFAState)dfaStates.get(i);
/*  84 */       DFAState nextState = (DFAState)dfaStates.get(i + 1);
/*     */ 
/*  86 */       for (int j = 0; j < d.getNumberOfTransitions(); j++) {
/*  87 */         Transition e = d.getTransition(j);
/*  88 */         if (e.target.stateNumber == nextState.stateNumber) {
/*  89 */           labels.add(e.label.getSet());
/*     */         }
/*     */       }
/*     */     }
/*  93 */     return labels;
/*     */   }
/*     */ 
/*     */   public String getInputSequenceDisplay(Grammar g, List<IntSet> labels)
/*     */   {
/* 102 */     List tokens = new ArrayList();
/* 103 */     for (Iterator i$ = labels.iterator(); i$.hasNext(); ) { IntSet label = (IntSet)i$.next();
/* 104 */       tokens.add(label.toString(g)); }
/* 105 */     return tokens.toString();
/*     */   }
/*     */ 
/*     */   public List<Token> getGrammarLocationsForInputSequence(List<Set<NFAState>> nfaStates, List<IntSet> labels)
/*     */   {
/* 117 */     List tokens = new ArrayList();
/*     */     Set next;
/*     */     IntSet label;
/*     */     Iterator i$;
/* 118 */     label242: for (int i = 0; i < nfaStates.size() - 1; i++) {
/* 119 */       Set cur = (Set)nfaStates.get(i);
/* 120 */       next = (Set)nfaStates.get(i + 1);
/* 121 */       label = (IntSet)labels.get(i);
/*     */ 
/* 125 */       for (i$ = cur.iterator(); i$.hasNext(); ) { NFAState p = (NFAState)i$.next();
/*     */ 
/* 127 */         for (int j = 0; j < p.getNumberOfTransitions(); j++) {
/* 128 */           Transition t = p.transition(j);
/* 129 */           if ((!t.isEpsilon()) && (!t.label.getSet().and(label).isNil()) && (next.contains(t.target)))
/*     */           {
/* 131 */             if (p.associatedASTNode != null) {
/* 132 */               Token oldtoken = p.associatedASTNode.token;
/* 133 */               CommonToken token = new CommonToken(oldtoken.getType(), oldtoken.getText());
/*     */ 
/* 135 */               token.setLine(oldtoken.getLine());
/* 136 */               token.setColumn(oldtoken.getColumn());
/* 137 */               tokens.add(token);
/* 138 */               break label242;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 145 */     return tokens;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.analysis.MachineProbe
 * JD-Core Version:    0.6.2
 */