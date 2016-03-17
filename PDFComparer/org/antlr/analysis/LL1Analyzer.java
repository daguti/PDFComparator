/*     */ package org.antlr.analysis;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.antlr.misc.IntSet;
/*     */ import org.antlr.misc.IntervalSet;
/*     */ import org.antlr.tool.Grammar;
/*     */ import org.antlr.tool.GrammarAST;
/*     */ import org.antlr.tool.Rule;
/*     */ 
/*     */ public class LL1Analyzer
/*     */ {
/*     */   public static final int DETECT_PRED_EOR = 0;
/*     */   public static final int DETECT_PRED_FOUND = 1;
/*     */   public static final int DETECT_PRED_NOT_FOUND = 2;
/*     */   public Grammar grammar;
/*  59 */   protected Set<NFAState> lookBusy = new HashSet();
/*     */ 
/*  61 */   public Map<NFAState, LookaheadSet> FIRSTCache = new HashMap();
/*  62 */   public Map<Rule, LookaheadSet> FOLLOWCache = new HashMap();
/*     */ 
/*     */   public LL1Analyzer(Grammar grammar) {
/*  65 */     this.grammar = grammar;
/*     */   }
/*     */ 
/*     */   public LookaheadSet FIRST(NFAState s)
/*     */   {
/* 152 */     this.lookBusy.clear();
/* 153 */     LookaheadSet look = _FIRST(s, false);
/*     */ 
/* 155 */     return look;
/*     */   }
/*     */ 
/*     */   public LookaheadSet FOLLOW(Rule r)
/*     */   {
/* 160 */     LookaheadSet f = (LookaheadSet)this.FOLLOWCache.get(r);
/* 161 */     if (f != null) {
/* 162 */       return f;
/*     */     }
/* 164 */     f = _FIRST(r.stopState, true);
/* 165 */     this.FOLLOWCache.put(r, f);
/*     */ 
/* 167 */     return f;
/*     */   }
/*     */ 
/*     */   public LookaheadSet LOOK(NFAState s) {
/* 171 */     if (NFAToDFAConverter.debug) {
/* 172 */       System.out.println("> LOOK(" + s + ")");
/*     */     }
/* 174 */     this.lookBusy.clear();
/* 175 */     LookaheadSet look = _FIRST(s, true);
/*     */ 
/* 177 */     if ((this.grammar.type != 1) && (look.member(1)))
/*     */     {
/* 179 */       LookaheadSet f = FOLLOW(s.enclosingRule);
/* 180 */       f.orInPlace(look);
/* 181 */       f.remove(1);
/* 182 */       look = f;
/*     */     }
/* 185 */     else if ((this.grammar.type == 1) && (look.member(-2)))
/*     */     {
/* 188 */       look = new LookaheadSet(IntervalSet.COMPLETE_SET);
/*     */     }
/* 190 */     if (NFAToDFAConverter.debug) {
/* 191 */       System.out.println("< LOOK(" + s + ")=" + look.toString(this.grammar));
/*     */     }
/* 193 */     return look;
/*     */   }
/*     */ 
/*     */   protected LookaheadSet _FIRST(NFAState s, boolean chaseFollowTransitions)
/*     */   {
/* 203 */     if ((!chaseFollowTransitions) && (s.isAcceptState())) {
/* 204 */       if (this.grammar.type == 1)
/*     */       {
/* 207 */         return new LookaheadSet(IntervalSet.COMPLETE_SET);
/*     */       }
/* 209 */       return new LookaheadSet(1);
/*     */     }
/*     */ 
/* 212 */     if (this.lookBusy.contains(s))
/*     */     {
/* 214 */       return new LookaheadSet();
/*     */     }
/* 216 */     this.lookBusy.add(s);
/*     */ 
/* 218 */     Transition transition0 = s.transition[0];
/* 219 */     if (transition0 == null) {
/* 220 */       return null;
/*     */     }
/*     */ 
/* 223 */     if (transition0.label.isAtom()) {
/* 224 */       int atom = transition0.label.getAtom();
/* 225 */       return new LookaheadSet(atom);
/*     */     }
/* 227 */     if (transition0.label.isSet()) {
/* 228 */       IntSet sl = transition0.label.getSet();
/* 229 */       return new LookaheadSet(sl);
/*     */     }
/*     */ 
/* 233 */     LookaheadSet tset = null;
/*     */ 
/* 235 */     if ((!chaseFollowTransitions) && ((transition0 instanceof RuleClosureTransition))) {
/* 236 */       LookaheadSet prev = (LookaheadSet)this.FIRSTCache.get((NFAState)transition0.target);
/* 237 */       if (prev != null) {
/* 238 */         tset = new LookaheadSet(prev);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 243 */     if (tset == null) {
/* 244 */       tset = _FIRST((NFAState)transition0.target, chaseFollowTransitions);
/*     */ 
/* 246 */       if ((!chaseFollowTransitions) && ((transition0 instanceof RuleClosureTransition))) {
/* 247 */         this.FIRSTCache.put((NFAState)transition0.target, tset);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 252 */     if ((this.grammar.type != 1) && (tset.member(1)) && 
/* 253 */       ((transition0 instanceof RuleClosureTransition)))
/*     */     {
/* 259 */       RuleClosureTransition ruleInvocationTrans = (RuleClosureTransition)transition0;
/*     */ 
/* 263 */       NFAState following = ruleInvocationTrans.followState;
/* 264 */       LookaheadSet fset = _FIRST(following, chaseFollowTransitions);
/* 265 */       fset.orInPlace(tset);
/* 266 */       fset.remove(1);
/* 267 */       tset = fset;
/*     */     }
/*     */ 
/* 271 */     Transition transition1 = s.transition[1];
/* 272 */     if (transition1 != null) {
/* 273 */       LookaheadSet tset1 = _FIRST((NFAState)transition1.target, chaseFollowTransitions);
/*     */ 
/* 275 */       tset1.orInPlace(tset);
/* 276 */       tset = tset1;
/*     */     }
/*     */ 
/* 279 */     return tset;
/*     */   }
/*     */ 
/*     */   public boolean detectConfoundingPredicates(NFAState s)
/*     */   {
/* 289 */     this.lookBusy.clear();
/* 290 */     Rule r = s.enclosingRule;
/* 291 */     return _detectConfoundingPredicates(s, r, false) == 1;
/*     */   }
/*     */ 
/*     */   protected int _detectConfoundingPredicates(NFAState s, Rule enclosingRule, boolean chaseFollowTransitions)
/*     */   {
/* 299 */     if ((!chaseFollowTransitions) && (s.isAcceptState())) {
/* 300 */       if (this.grammar.type == 1)
/*     */       {
/* 303 */         return 2;
/*     */       }
/* 305 */       return 0;
/*     */     }
/*     */ 
/* 308 */     if (this.lookBusy.contains(s))
/*     */     {
/* 310 */       return 2;
/*     */     }
/* 312 */     this.lookBusy.add(s);
/*     */ 
/* 314 */     Transition transition0 = s.transition[0];
/* 315 */     if (transition0 == null) {
/* 316 */       return 2;
/*     */     }
/*     */ 
/* 319 */     if ((!transition0.label.isSemanticPredicate()) && (!transition0.label.isEpsilon()))
/*     */     {
/* 321 */       return 2;
/*     */     }
/*     */ 
/* 324 */     if (transition0.label.isSemanticPredicate())
/*     */     {
/* 326 */       SemanticContext ctx = transition0.label.getSemanticContext();
/* 327 */       SemanticContext.Predicate p = (SemanticContext.Predicate)ctx;
/* 328 */       if (p.predicateAST.getType() != 37) {
/* 329 */         return 1;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 348 */     int result = _detectConfoundingPredicates((NFAState)transition0.target, enclosingRule, chaseFollowTransitions);
/*     */ 
/* 351 */     if (result == 1) {
/* 352 */       return 1;
/*     */     }
/*     */ 
/* 355 */     if ((result == 0) && 
/* 356 */       ((transition0 instanceof RuleClosureTransition)))
/*     */     {
/* 362 */       RuleClosureTransition ruleInvocationTrans = (RuleClosureTransition)transition0;
/*     */ 
/* 364 */       NFAState following = ruleInvocationTrans.followState;
/* 365 */       int afterRuleResult = _detectConfoundingPredicates(following, enclosingRule, chaseFollowTransitions);
/*     */ 
/* 369 */       if (afterRuleResult == 1) {
/* 370 */         return 1;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 375 */     Transition transition1 = s.transition[1];
/* 376 */     if (transition1 != null) {
/* 377 */       int t1Result = _detectConfoundingPredicates((NFAState)transition1.target, enclosingRule, chaseFollowTransitions);
/*     */ 
/* 381 */       if (t1Result == 1) {
/* 382 */         return 1;
/*     */       }
/*     */     }
/*     */ 
/* 386 */     return 2;
/*     */   }
/*     */ 
/*     */   public SemanticContext getPredicates(NFAState altStartState)
/*     */   {
/* 394 */     this.lookBusy.clear();
/* 395 */     return _getPredicates(altStartState, altStartState);
/*     */   }
/*     */ 
/*     */   protected SemanticContext _getPredicates(NFAState s, NFAState altStartState)
/*     */   {
/* 400 */     if (s.isAcceptState()) {
/* 401 */       return null;
/*     */     }
/*     */ 
/* 405 */     if (this.lookBusy.contains(s)) {
/* 406 */       return null;
/*     */     }
/* 408 */     this.lookBusy.add(s);
/*     */ 
/* 410 */     Transition transition0 = s.transition[0];
/*     */ 
/* 412 */     if (transition0 == null) {
/* 413 */       return null;
/*     */     }
/*     */ 
/* 417 */     if ((!transition0.label.isSemanticPredicate()) && (!transition0.label.isEpsilon()))
/*     */     {
/* 419 */       return null;
/*     */     }
/*     */ 
/* 422 */     SemanticContext p = null;
/* 423 */     SemanticContext p0 = null;
/* 424 */     SemanticContext p1 = null;
/* 425 */     if (transition0.label.isSemanticPredicate())
/*     */     {
/* 427 */       p = transition0.label.getSemanticContext();
/*     */ 
/* 429 */       if ((((SemanticContext.Predicate)p).predicateAST.getType() == 37) && (s == altStartState.transition[0].target))
/*     */       {
/* 433 */         p = null;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 438 */     p0 = _getPredicates((NFAState)transition0.target, altStartState);
/*     */ 
/* 441 */     Transition transition1 = s.transition[1];
/* 442 */     if (transition1 != null) {
/* 443 */       p1 = _getPredicates((NFAState)transition1.target, altStartState);
/*     */     }
/*     */ 
/* 447 */     return SemanticContext.and(p, SemanticContext.or(p0, p1));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.analysis.LL1Analyzer
 * JD-Core Version:    0.6.2
 */