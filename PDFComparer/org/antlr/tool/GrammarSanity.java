/*     */ package org.antlr.tool;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import org.antlr.analysis.Label;
/*     */ import org.antlr.analysis.NFAState;
/*     */ import org.antlr.analysis.RuleClosureTransition;
/*     */ import org.antlr.analysis.Transition;
/*     */ 
/*     */ public class GrammarSanity
/*     */ {
/*  45 */   protected Set<Rule> visitedDuringRecursionCheck = null;
/*     */   protected Grammar grammar;
/*     */ 
/*     */   public GrammarSanity(Grammar grammar)
/*     */   {
/*  49 */     this.grammar = grammar;
/*     */   }
/*     */ 
/*     */   public List<Set<Rule>> checkAllRulesForLeftRecursion()
/*     */   {
/*  58 */     this.grammar.buildNFA();
/*  59 */     this.grammar.leftRecursiveRules = new HashSet();
/*  60 */     List listOfRecursiveCycles = new ArrayList();
/*  61 */     for (int i = 0; i < this.grammar.composite.ruleIndexToRuleList.size(); i++) {
/*  62 */       Rule r = (Rule)this.grammar.composite.ruleIndexToRuleList.elementAt(i);
/*  63 */       if (r != null) {
/*  64 */         this.visitedDuringRecursionCheck = new HashSet();
/*  65 */         this.visitedDuringRecursionCheck.add(r);
/*  66 */         Set visitedStates = new HashSet();
/*  67 */         traceStatesLookingForLeftRecursion(r.startState, visitedStates, listOfRecursiveCycles);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  72 */     if (listOfRecursiveCycles.size() > 0) {
/*  73 */       ErrorManager.leftRecursionCycles(listOfRecursiveCycles);
/*     */     }
/*  75 */     return listOfRecursiveCycles;
/*     */   }
/*     */ 
/*     */   protected boolean traceStatesLookingForLeftRecursion(NFAState s, Set visitedStates, List<Set<Rule>> listOfRecursiveCycles)
/*     */   {
/*  92 */     if (s.isAcceptState())
/*     */     {
/*  95 */       return true;
/*     */     }
/*  97 */     if (visitedStates.contains(s))
/*     */     {
/*  99 */       return false;
/*     */     }
/* 101 */     visitedStates.add(s);
/* 102 */     boolean stateReachesAcceptState = false;
/* 103 */     Transition t0 = s.transition[0];
/* 104 */     if ((t0 instanceof RuleClosureTransition)) {
/* 105 */       RuleClosureTransition refTrans = (RuleClosureTransition)t0;
/* 106 */       Rule refRuleDef = refTrans.rule;
/*     */ 
/* 108 */       if (this.visitedDuringRecursionCheck.contains(refRuleDef))
/*     */       {
/* 110 */         this.grammar.leftRecursiveRules.add(refRuleDef);
/*     */ 
/* 115 */         addRulesToCycle(refRuleDef, s.enclosingRule, listOfRecursiveCycles);
/*     */       }
/*     */       else
/*     */       {
/* 121 */         this.visitedDuringRecursionCheck.add(refRuleDef);
/* 122 */         boolean callReachedAcceptState = traceStatesLookingForLeftRecursion((NFAState)t0.target, new HashSet(), listOfRecursiveCycles);
/*     */ 
/* 127 */         this.visitedDuringRecursionCheck.remove(refRuleDef);
/*     */ 
/* 129 */         if (callReachedAcceptState) {
/* 130 */           NFAState followingState = ((RuleClosureTransition)t0).followState;
/*     */ 
/* 132 */           stateReachesAcceptState |= traceStatesLookingForLeftRecursion(followingState, visitedStates, listOfRecursiveCycles);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/* 139 */     else if ((t0.label.isEpsilon()) || (t0.label.isSemanticPredicate())) {
/* 140 */       stateReachesAcceptState |= traceStatesLookingForLeftRecursion((NFAState)t0.target, visitedStates, listOfRecursiveCycles);
/*     */     }
/*     */ 
/* 146 */     Transition t1 = s.transition[1];
/* 147 */     if (t1 != null) {
/* 148 */       stateReachesAcceptState |= traceStatesLookingForLeftRecursion((NFAState)t1.target, visitedStates, listOfRecursiveCycles);
/*     */     }
/*     */ 
/* 153 */     return stateReachesAcceptState;
/*     */   }
/*     */ 
/*     */   protected void addRulesToCycle(Rule targetRule, Rule enclosingRule, List<Set<Rule>> listOfRecursiveCycles)
/*     */   {
/* 166 */     boolean foundCycle = false;
/* 167 */     for (int i = 0; i < listOfRecursiveCycles.size(); i++) {
/* 168 */       Set rulesInCycle = (Set)listOfRecursiveCycles.get(i);
/*     */ 
/* 170 */       if (rulesInCycle.contains(targetRule)) {
/* 171 */         rulesInCycle.add(enclosingRule);
/* 172 */         foundCycle = true;
/*     */       }
/* 174 */       if (rulesInCycle.contains(enclosingRule)) {
/* 175 */         rulesInCycle.add(targetRule);
/* 176 */         foundCycle = true;
/*     */       }
/*     */     }
/* 179 */     if (!foundCycle) {
/* 180 */       Set cycle = new HashSet();
/* 181 */       cycle.add(targetRule);
/* 182 */       cycle.add(enclosingRule);
/* 183 */       listOfRecursiveCycles.add(cycle);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void checkRuleReference(GrammarAST scopeAST, GrammarAST refAST, GrammarAST argsAST, String currentRuleName)
/*     */   {
/* 192 */     Rule r = this.grammar.getRule(refAST.getText());
/* 193 */     if (refAST.getType() == 73) {
/* 194 */       if (argsAST != null)
/*     */       {
/* 196 */         if ((r != null) && (r.argActionAST == null))
/*     */         {
/* 198 */           ErrorManager.grammarError(130, this.grammar, argsAST.getToken(), r.name);
/*     */         }
/*     */ 
/*     */       }
/* 207 */       else if ((r != null) && (r.argActionAST != null))
/*     */       {
/* 209 */         ErrorManager.grammarError(129, this.grammar, refAST.getToken(), r.name);
/*     */       }
/*     */ 
/*     */     }
/* 217 */     else if (refAST.getType() == 55) {
/* 218 */       if (this.grammar.type != 1) {
/* 219 */         if (argsAST != null)
/*     */         {
/* 221 */           ErrorManager.grammarError(131, this.grammar, refAST.getToken(), refAST.getText());
/*     */         }
/*     */ 
/* 227 */         return;
/*     */       }
/* 229 */       if (argsAST != null)
/*     */       {
/* 231 */         if ((r != null) && (r.argActionAST == null))
/*     */         {
/* 233 */           ErrorManager.grammarError(130, this.grammar, argsAST.getToken(), r.name);
/*     */         }
/*     */ 
/*     */       }
/* 242 */       else if ((r != null) && (r.argActionAST != null))
/*     */       {
/* 244 */         ErrorManager.grammarError(129, this.grammar, refAST.getToken(), r.name);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void ensureAltIsSimpleNodeOrTree(GrammarAST altAST, GrammarAST elementAST, int outerAltNum)
/*     */   {
/* 268 */     if (isValidSimpleElementNode(elementAST)) {
/* 269 */       GrammarAST next = (GrammarAST)elementAST.getNextSibling();
/* 270 */       if (!isNextNonActionElementEOA(next)) {
/* 271 */         ErrorManager.grammarWarning(153, this.grammar, next.token, new Integer(outerAltNum));
/*     */       }
/*     */ 
/* 276 */       return;
/*     */     }
/* 278 */     switch (elementAST.getType()) {
/*     */     case 49:
/*     */     case 68:
/* 281 */       if (isValidSimpleElementNode(elementAST.getChild(1))) {
/*     */         return;
/*     */       }
/*     */       break;
/*     */     case 35:
/*     */     case 36:
/*     */     case 37:
/*     */     case 40:
/*     */     case 69:
/* 290 */       ensureAltIsSimpleNodeOrTree(altAST, (GrammarAST)elementAST.getNextSibling(), outerAltNum);
/*     */ 
/* 293 */       return;
/*     */     }
/* 295 */     ErrorManager.grammarWarning(153, this.grammar, elementAST.token, new Integer(outerAltNum));
/*     */   }
/*     */ 
/*     */   protected boolean isValidSimpleElementNode(GrammarAST t)
/*     */   {
/* 302 */     switch (t.getType()) {
/*     */     case 50:
/*     */     case 51:
/*     */     case 55:
/*     */     case 72:
/*     */     case 75:
/* 308 */       return true;
/*     */     }
/* 310 */     return false;
/*     */   }
/*     */ 
/*     */   protected boolean isNextNonActionElementEOA(GrammarAST t)
/*     */   {
/* 316 */     while ((t.getType() == 40) || (t.getType() == 69))
/*     */     {
/* 318 */       t = (GrammarAST)t.getNextSibling();
/*     */     }
/* 320 */     if (t.getType() == 20) {
/* 321 */       return true;
/*     */     }
/* 323 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.GrammarSanity
 * JD-Core Version:    0.6.2
 */