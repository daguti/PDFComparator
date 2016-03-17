/*     */ package org.antlr.tool;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.List;
/*     */ import java.util.Stack;
/*     */ import org.antlr.analysis.DFA;
/*     */ import org.antlr.analysis.DFAState;
/*     */ import org.antlr.analysis.Label;
/*     */ import org.antlr.analysis.NFA;
/*     */ import org.antlr.analysis.NFAState;
/*     */ import org.antlr.analysis.RuleClosureTransition;
/*     */ import org.antlr.analysis.Transition;
/*     */ import org.antlr.misc.IntervalSet;
/*     */ import org.antlr.runtime.CharStream;
/*     */ import org.antlr.runtime.CommonToken;
/*     */ import org.antlr.runtime.FailedPredicateException;
/*     */ import org.antlr.runtime.IntStream;
/*     */ import org.antlr.runtime.MismatchedSetException;
/*     */ import org.antlr.runtime.MismatchedTokenException;
/*     */ import org.antlr.runtime.NoViableAltException;
/*     */ import org.antlr.runtime.RecognitionException;
/*     */ import org.antlr.runtime.Token;
/*     */ import org.antlr.runtime.TokenSource;
/*     */ import org.antlr.runtime.TokenStream;
/*     */ import org.antlr.runtime.debug.BlankDebugEventListener;
/*     */ import org.antlr.runtime.debug.DebugEventListener;
/*     */ import org.antlr.runtime.debug.ParseTreeBuilder;
/*     */ import org.antlr.runtime.tree.ParseTree;
/*     */ 
/*     */ public class Interpreter
/*     */   implements TokenSource
/*     */ {
/*     */   protected Grammar grammar;
/*     */   protected IntStream input;
/*     */ 
/*     */   public Interpreter(Grammar grammar, IntStream input)
/*     */   {
/*  75 */     this.grammar = grammar;
/*  76 */     this.input = input;
/*     */   }
/*     */ 
/*     */   public Token nextToken() {
/*  80 */     if (this.grammar.type != 1) {
/*  81 */       return null;
/*     */     }
/*  83 */     if (this.input.LA(1) == -1) {
/*  84 */       return new CommonToken((CharStream)this.input, -1, 0, this.input.index(), this.input.index());
/*     */     }
/*  86 */     int start = this.input.index();
/*  87 */     int charPos = ((CharStream)this.input).getCharPositionInLine();
/*  88 */     CommonToken token = null;
/*     */ 
/*  90 */     while (this.input.LA(1) != -1) {
/*     */       try {
/*  92 */         token = scan("Tokens", null);
/*     */       }
/*     */       catch (RecognitionException re)
/*     */       {
/*  97 */         reportScanError(re);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 103 */     int stop = this.input.index() - 1;
/* 104 */     if (token == null) {
/* 105 */       return new CommonToken((CharStream)this.input, -1, 0, start, start);
/*     */     }
/* 107 */     token.setLine(((CharStream)this.input).getLine());
/* 108 */     token.setStartIndex(start);
/* 109 */     token.setStopIndex(stop);
/* 110 */     token.setCharPositionInLine(charPos);
/* 111 */     return token;
/*     */   }
/*     */ 
/*     */   public void scan(String startRule, DebugEventListener actions, List visitedStates)
/*     */     throws RecognitionException
/*     */   {
/* 129 */     if (this.grammar.type != 1) {
/* 130 */       return;
/*     */     }
/* 132 */     CharStream in = (CharStream)this.input;
/*     */ 
/* 135 */     if (this.grammar.getRuleStartState(startRule) == null) {
/* 136 */       this.grammar.buildNFA();
/*     */     }
/*     */ 
/* 139 */     if (!this.grammar.allDecisionDFAHaveBeenCreated())
/*     */     {
/* 141 */       this.grammar.createLookaheadDFAs();
/*     */     }
/*     */ 
/* 145 */     Stack ruleInvocationStack = new Stack();
/* 146 */     NFAState start = this.grammar.getRuleStartState(startRule);
/* 147 */     NFAState stop = this.grammar.getRuleStopState(startRule);
/* 148 */     parseEngine(startRule, start, stop, in, ruleInvocationStack, actions, visitedStates);
/*     */   }
/*     */ 
/*     */   public CommonToken scan(String startRule)
/*     */     throws RecognitionException
/*     */   {
/* 155 */     return scan(startRule, null);
/*     */   }
/*     */ 
/*     */   public CommonToken scan(String startRule, List visitedStates)
/*     */     throws RecognitionException
/*     */   {
/* 162 */     LexerActionGetTokenType actions = new LexerActionGetTokenType(this.grammar);
/* 163 */     scan(startRule, actions, visitedStates);
/* 164 */     return actions.token;
/*     */   }
/*     */ 
/*     */   public void parse(String startRule, DebugEventListener actions, List visitedStates)
/*     */     throws RecognitionException
/*     */   {
/* 174 */     if (this.grammar.getRuleStartState(startRule) == null) {
/* 175 */       this.grammar.buildNFA();
/*     */     }
/* 177 */     if (!this.grammar.allDecisionDFAHaveBeenCreated())
/*     */     {
/* 179 */       this.grammar.createLookaheadDFAs();
/*     */     }
/*     */ 
/* 182 */     Stack ruleInvocationStack = new Stack();
/* 183 */     NFAState start = this.grammar.getRuleStartState(startRule);
/* 184 */     NFAState stop = this.grammar.getRuleStopState(startRule);
/* 185 */     parseEngine(startRule, start, stop, this.input, ruleInvocationStack, actions, visitedStates);
/*     */   }
/*     */ 
/*     */   public ParseTree parse(String startRule)
/*     */     throws RecognitionException
/*     */   {
/* 192 */     return parse(startRule, null);
/*     */   }
/*     */ 
/*     */   public ParseTree parse(String startRule, List visitedStates)
/*     */     throws RecognitionException
/*     */   {
/* 198 */     ParseTreeBuilder actions = new ParseTreeBuilder(this.grammar.name);
/*     */     try {
/* 200 */       parse(startRule, actions, visitedStates);
/*     */     }
/*     */     catch (RecognitionException re)
/*     */     {
/*     */     }
/*     */ 
/* 207 */     return actions.getTree();
/*     */   }
/*     */ 
/*     */   protected void parseEngine(String startRule, NFAState start, NFAState stop, IntStream input, Stack ruleInvocationStack, DebugEventListener actions, List visitedStates)
/*     */     throws RecognitionException
/*     */   {
/* 220 */     NFAState s = start;
/* 221 */     if (actions != null) {
/* 222 */       actions.enterRule(s.nfa.grammar.getFileName(), start.enclosingRule.name);
/*     */     }
/* 224 */     int t = input.LA(1);
/* 225 */     while (s != stop) {
/* 226 */       if (visitedStates != null) {
/* 227 */         visitedStates.add(s);
/*     */       }
/*     */ 
/* 234 */       if ((s.getDecisionNumber() > 0) && (s.nfa.grammar.getNumberOfAltsForDecisionNFA(s) > 1))
/*     */       {
/* 236 */         DFA dfa = s.nfa.grammar.getLookaheadDFA(s.getDecisionNumber());
/*     */ 
/* 244 */         int m = input.mark();
/* 245 */         int predictedAlt = predict(dfa);
/* 246 */         if (predictedAlt == -1) {
/* 247 */           String description = dfa.getNFADecisionStartState().getDescription();
/* 248 */           NoViableAltException nvae = new NoViableAltException(description, dfa.getDecisionNumber(), s.stateNumber, input);
/*     */ 
/* 253 */           if (actions != null) {
/* 254 */             actions.recognitionException(nvae);
/*     */           }
/* 256 */           input.consume();
/* 257 */           throw nvae;
/*     */         }
/* 259 */         input.rewind(m);
/* 260 */         int parseAlt = s.translateDisplayAltToWalkAlt(predictedAlt);
/*     */         NFAState alt;
/*     */         NFAState alt;
/* 269 */         if (parseAlt > s.nfa.grammar.getNumberOfAltsForDecisionNFA(s))
/*     */         {
/* 271 */           alt = s.nfa.grammar.nfa.getState(s.endOfBlockStateNumber);
/*     */         }
/*     */         else {
/* 274 */           alt = s.nfa.grammar.getNFAStateForAltOfDecision(s, parseAlt);
/*     */         }
/* 276 */         s = (NFAState)alt.transition[0].target;
/*     */       }
/* 281 */       else if (s.isAcceptState()) {
/* 282 */         if (actions != null) {
/* 283 */           actions.exitRule(s.nfa.grammar.getFileName(), s.enclosingRule.name);
/*     */         }
/* 285 */         if (ruleInvocationStack.empty())
/*     */         {
/*     */           break;
/*     */         }
/*     */ 
/* 291 */         NFAState invokingState = (NFAState)ruleInvocationStack.pop();
/* 292 */         RuleClosureTransition invokingTransition = (RuleClosureTransition)invokingState.transition[0];
/*     */ 
/* 295 */         s = invokingTransition.followState;
/*     */       }
/*     */       else
/*     */       {
/* 299 */         Transition trans = s.transition[0];
/* 300 */         Label label = trans.label;
/* 301 */         if (label.isSemanticPredicate()) {
/* 302 */           FailedPredicateException fpe = new FailedPredicateException(input, s.enclosingRule.name, "can't deal with predicates yet");
/*     */ 
/* 306 */           if (actions != null) {
/* 307 */             actions.recognitionException(fpe);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 312 */         if (label.isEpsilon())
/*     */         {
/* 314 */           if ((trans instanceof RuleClosureTransition)) {
/* 315 */             ruleInvocationStack.push(s);
/* 316 */             s = (NFAState)trans.target;
/*     */ 
/* 318 */             if (actions != null) {
/* 319 */               actions.enterRule(s.nfa.grammar.getFileName(), s.enclosingRule.name);
/*     */             }
/*     */ 
/* 322 */             if (!s.nfa.grammar.allDecisionDFAHaveBeenCreated()) {
/* 323 */               s.nfa.grammar.createLookaheadDFAs();
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 328 */             s = (NFAState)trans.target;
/*     */           }
/*     */ 
/*     */         }
/* 333 */         else if (label.matches(t)) {
/* 334 */           if ((actions != null) && (
/* 335 */             (s.nfa.grammar.type == 2) || (s.nfa.grammar.type == 4)))
/*     */           {
/* 338 */             actions.consumeToken(((TokenStream)input).LT(1));
/*     */           }
/*     */ 
/* 341 */           s = (NFAState)s.transition[0].target;
/* 342 */           input.consume();
/* 343 */           t = input.LA(1);
/*     */         }
/*     */         else
/*     */         {
/* 348 */           if (label.isAtom()) {
/* 349 */             MismatchedTokenException mte = new MismatchedTokenException(label.getAtom(), input);
/*     */ 
/* 351 */             if (actions != null) {
/* 352 */               actions.recognitionException(mte);
/*     */             }
/* 354 */             input.consume();
/* 355 */             throw mte;
/*     */           }
/* 357 */           if (label.isSet()) {
/* 358 */             MismatchedSetException mse = new MismatchedSetException(((IntervalSet)label.getSet()).toRuntimeBitSet(), input);
/*     */ 
/* 361 */             if (actions != null) {
/* 362 */               actions.recognitionException(mse);
/*     */             }
/* 364 */             input.consume();
/* 365 */             throw mse;
/*     */           }
/* 367 */           if (label.isSemanticPredicate()) {
/* 368 */             FailedPredicateException fpe = new FailedPredicateException(input, s.enclosingRule.name, label.getSemanticContext().toString());
/*     */ 
/* 372 */             if (actions != null) {
/* 373 */               actions.recognitionException(fpe);
/*     */             }
/* 375 */             input.consume();
/* 376 */             throw fpe;
/*     */           }
/*     */ 
/* 379 */           throw new RecognitionException(input);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 384 */     if (actions != null)
/* 385 */       actions.exitRule(s.nfa.grammar.getFileName(), stop.enclosingRule.name);
/*     */   }
/*     */ 
/*     */   public int predict(DFA dfa)
/*     */   {
/* 396 */     DFAState s = dfa.startState;
/* 397 */     int c = this.input.LA(1);
/* 398 */     Transition eotTransition = null;
/*     */ 
/* 400 */     while (!s.isAcceptState())
/*     */     {
/* 406 */       for (int i = 0; ; i++) { if (i >= s.getNumberOfTransitions()) break label113;
/* 407 */         Transition t = s.transition(i);
/*     */ 
/* 409 */         if (t.label.matches(c))
/*     */         {
/* 411 */           s = (DFAState)t.target;
/* 412 */           this.input.consume();
/* 413 */           c = this.input.LA(1);
/* 414 */           break;
/*     */         }
/* 416 */         if (t.label.getAtom() == -2) {
/* 417 */           eotTransition = t;
/*     */         }
/*     */       }
/* 420 */       label113: if (eotTransition != null) {
/* 421 */         s = (DFAState)eotTransition.target;
/*     */       }
/*     */       else
/*     */       {
/* 429 */         return -1;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 437 */     return s.getUniquelyPredictedAlt();
/*     */   }
/*     */ 
/*     */   public void reportScanError(RecognitionException re) {
/* 441 */     CharStream cs = (CharStream)this.input;
/*     */ 
/* 445 */     System.err.println("problem matching token at " + cs.getLine() + ":" + cs.getCharPositionInLine() + " " + re);
/*     */   }
/*     */ 
/*     */   public String getSourceName()
/*     */   {
/* 450 */     return this.input.getSourceName();
/*     */   }
/*     */ 
/*     */   class LexerActionGetTokenType extends BlankDebugEventListener
/*     */   {
/*     */     public CommonToken token;
/*     */     Grammar g;
/*     */ 
/*     */     public LexerActionGetTokenType(Grammar g)
/*     */     {
/*  62 */       this.g = g;
/*     */     }
/*     */ 
/*     */     public void exitRule(String grammarFileName, String ruleName) {
/*  66 */       if (!ruleName.equals("Tokens")) {
/*  67 */         int type = this.g.getTokenType(ruleName);
/*  68 */         int channel = 0;
/*  69 */         this.token = new CommonToken((CharStream)Interpreter.this.input, type, channel, 0, 0);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.Interpreter
 * JD-Core Version:    0.6.2
 */