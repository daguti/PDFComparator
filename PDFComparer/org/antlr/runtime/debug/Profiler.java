/*     */ package org.antlr.runtime.debug;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.Stack;
/*     */ import org.antlr.runtime.IntStream;
/*     */ import org.antlr.runtime.RecognitionException;
/*     */ import org.antlr.runtime.Token;
/*     */ import org.antlr.runtime.TokenStream;
/*     */ import org.antlr.runtime.misc.DoubleKeyMap;
/*     */ 
/*     */ public class Profiler extends BlankDebugEventListener
/*     */ {
/*     */   public static final String DATA_SEP = "\t";
/*  40 */   public static final String newline = System.getProperty("line.separator");
/*     */ 
/*  42 */   static boolean dump = false;
/*     */   public static final String Version = "3";
/*     */   public static final String RUNTIME_STATS_FILENAME = "runtime.stats";
/* 122 */   public DebugParser parser = null;
/*     */ 
/* 126 */   protected int ruleLevel = 0;
/*     */   protected Token lastRealTokenTouchedInDecision;
/* 129 */   protected Set<String> uniqueRules = new HashSet();
/* 130 */   protected Stack<String> currentGrammarFileName = new Stack();
/* 131 */   protected Stack<String> currentRuleName = new Stack();
/* 132 */   protected Stack<Integer> currentLine = new Stack();
/* 133 */   protected Stack<Integer> currentPos = new Stack();
/*     */ 
/* 137 */   protected DoubleKeyMap<String, Integer, DecisionDescriptor> decisions = new DoubleKeyMap();
/*     */ 
/* 141 */   protected List<DecisionEvent> decisionEvents = new ArrayList();
/* 142 */   protected Stack<DecisionEvent> decisionStack = new Stack();
/*     */   protected int backtrackDepth;
/* 146 */   ProfileStats stats = new ProfileStats();
/*     */ 
/*     */   public Profiler() {
/*     */   }
/*     */ 
/*     */   public Profiler(DebugParser parser) {
/* 152 */     this.parser = parser;
/*     */   }
/*     */ 
/*     */   public void enterRule(String grammarFileName, String ruleName)
/*     */   {
/* 157 */     this.ruleLevel += 1;
/* 158 */     this.stats.numRuleInvocations += 1;
/* 159 */     this.uniqueRules.add(grammarFileName + ":" + ruleName);
/* 160 */     this.stats.maxRuleInvocationDepth = Math.max(this.stats.maxRuleInvocationDepth, this.ruleLevel);
/* 161 */     this.currentGrammarFileName.push(grammarFileName);
/* 162 */     this.currentRuleName.push(ruleName);
/*     */   }
/*     */ 
/*     */   public void exitRule(String grammarFileName, String ruleName) {
/* 166 */     this.ruleLevel -= 1;
/* 167 */     this.currentGrammarFileName.pop();
/* 168 */     this.currentRuleName.pop();
/*     */   }
/*     */ 
/*     */   public void examineRuleMemoization(IntStream input, int ruleIndex, int stopIndex, String ruleName)
/*     */   {
/* 181 */     if (dump) System.out.println("examine memo " + ruleName + " at " + input.index() + ": " + stopIndex);
/* 182 */     if (stopIndex == -1)
/*     */     {
/* 184 */       this.stats.numMemoizationCacheMisses += 1;
/* 185 */       this.stats.numGuessingRuleInvocations += 1;
/* 186 */       currentDecision().numMemoizationCacheMisses += 1;
/*     */     }
/*     */     else
/*     */     {
/* 191 */       this.stats.numMemoizationCacheHits += 1;
/* 192 */       currentDecision().numMemoizationCacheHits += 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void memoize(IntStream input, int ruleIndex, int ruleStartIndex, String ruleName)
/*     */   {
/* 203 */     if (dump) System.out.println("memoize " + ruleName);
/* 204 */     this.stats.numMemoizationCacheEntries += 1;
/*     */   }
/*     */ 
/*     */   public void location(int line, int pos)
/*     */   {
/* 209 */     this.currentLine.push(new Integer(line));
/* 210 */     this.currentPos.push(new Integer(pos));
/*     */   }
/*     */ 
/*     */   public void enterDecision(int decisionNumber, boolean couldBacktrack) {
/* 214 */     this.lastRealTokenTouchedInDecision = null;
/* 215 */     this.stats.numDecisionEvents += 1;
/* 216 */     int startingLookaheadIndex = this.parser.getTokenStream().index();
/* 217 */     TokenStream input = this.parser.getTokenStream();
/* 218 */     if (dump) System.out.println("enterDecision canBacktrack=" + couldBacktrack + " " + decisionNumber + " backtrack depth " + this.backtrackDepth + " @ " + input.get(input.index()) + " rule " + locationDescription());
/*     */ 
/* 222 */     String g = (String)this.currentGrammarFileName.peek();
/* 223 */     DecisionDescriptor descriptor = (DecisionDescriptor)this.decisions.get(g, new Integer(decisionNumber));
/* 224 */     if (descriptor == null) {
/* 225 */       descriptor = new DecisionDescriptor();
/* 226 */       this.decisions.put(g, new Integer(decisionNumber), descriptor);
/* 227 */       descriptor.decision = decisionNumber;
/* 228 */       descriptor.fileName = ((String)this.currentGrammarFileName.peek());
/* 229 */       descriptor.ruleName = ((String)this.currentRuleName.peek());
/* 230 */       descriptor.line = ((Integer)this.currentLine.peek()).intValue();
/* 231 */       descriptor.pos = ((Integer)this.currentPos.peek()).intValue();
/* 232 */       descriptor.couldBacktrack = couldBacktrack;
/*     */     }
/* 234 */     descriptor.n += 1;
/*     */ 
/* 236 */     DecisionEvent d = new DecisionEvent();
/* 237 */     this.decisionStack.push(d);
/* 238 */     d.decision = descriptor;
/* 239 */     d.startTime = System.currentTimeMillis();
/* 240 */     d.startIndex = startingLookaheadIndex;
/*     */   }
/*     */ 
/*     */   public void exitDecision(int decisionNumber) {
/* 244 */     DecisionEvent d = (DecisionEvent)this.decisionStack.pop();
/* 245 */     d.stopTime = System.currentTimeMillis();
/*     */ 
/* 247 */     int lastTokenIndex = this.lastRealTokenTouchedInDecision.getTokenIndex();
/* 248 */     int numHidden = getNumberOfHiddenTokens(d.startIndex, lastTokenIndex);
/* 249 */     int depth = lastTokenIndex - d.startIndex - numHidden + 1;
/* 250 */     d.k = depth;
/* 251 */     d.decision.maxk = Math.max(d.decision.maxk, depth);
/*     */ 
/* 253 */     if (dump) System.out.println("exitDecision " + decisionNumber + " in " + d.decision.ruleName + " lookahead " + d.k + " max token " + this.lastRealTokenTouchedInDecision);
/*     */ 
/* 255 */     this.decisionEvents.add(d);
/*     */   }
/*     */ 
/*     */   public void consumeToken(Token token) {
/* 259 */     if (dump) System.out.println("consume token " + token);
/* 260 */     if (!inDecision()) {
/* 261 */       this.stats.numTokens += 1;
/* 262 */       return;
/*     */     }
/* 264 */     if ((this.lastRealTokenTouchedInDecision == null) || (this.lastRealTokenTouchedInDecision.getTokenIndex() < token.getTokenIndex()))
/*     */     {
/* 267 */       this.lastRealTokenTouchedInDecision = token;
/*     */     }
/* 269 */     DecisionEvent d = currentDecision();
/*     */ 
/* 271 */     int thisRefIndex = token.getTokenIndex();
/* 272 */     int numHidden = getNumberOfHiddenTokens(d.startIndex, thisRefIndex);
/* 273 */     int depth = thisRefIndex - d.startIndex - numHidden + 1;
/*     */ 
/* 275 */     if (dump) System.out.println("consume " + thisRefIndex + " " + depth + " tokens ahead in " + d.decision.ruleName + "-" + d.decision.decision + " start index " + d.startIndex);
/*     */   }
/*     */ 
/*     */   public boolean inDecision()
/*     */   {
/* 283 */     return this.decisionStack.size() > 0;
/*     */   }
/*     */ 
/*     */   public void consumeHiddenToken(Token token)
/*     */   {
/* 288 */     if (!inDecision()) this.stats.numHiddenTokens += 1;
/*     */   }
/*     */ 
/*     */   public void LT(int i, Token t)
/*     */   {
/* 294 */     if ((inDecision()) && (i > 0)) {
/* 295 */       DecisionEvent d = currentDecision();
/* 296 */       if (dump) System.out.println("LT(" + i + ")=" + t + " index " + t.getTokenIndex() + " relative to " + d.decision.ruleName + "-" + d.decision.decision + " start index " + d.startIndex);
/*     */ 
/* 298 */       if ((this.lastRealTokenTouchedInDecision == null) || (this.lastRealTokenTouchedInDecision.getTokenIndex() < t.getTokenIndex()))
/*     */       {
/* 301 */         this.lastRealTokenTouchedInDecision = t;
/* 302 */         if (dump) System.out.println("set last token " + this.lastRealTokenTouchedInDecision);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void beginBacktrack(int level)
/*     */   {
/* 339 */     if (dump) System.out.println("enter backtrack " + level);
/* 340 */     this.backtrackDepth += 1;
/* 341 */     DecisionEvent e = currentDecision();
/* 342 */     if (e.decision.couldBacktrack) {
/* 343 */       this.stats.numBacktrackOccurrences += 1;
/* 344 */       e.decision.numBacktrackOccurrences += 1;
/* 345 */       e.backtracks = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void endBacktrack(int level, boolean successful)
/*     */   {
/* 351 */     if (dump) System.out.println("exit backtrack " + level + ": " + successful);
/* 352 */     this.backtrackDepth -= 1;
/*     */   }
/*     */ 
/*     */   public void mark(int i)
/*     */   {
/* 357 */     if (dump) System.out.println("mark " + i);
/*     */   }
/*     */ 
/*     */   public void rewind(int i)
/*     */   {
/* 362 */     if (dump) System.out.println("rewind " + i);
/*     */   }
/*     */ 
/*     */   public void rewind()
/*     */   {
/* 367 */     if (dump) System.out.println("rewind");
/*     */   }
/*     */ 
/*     */   protected DecisionEvent currentDecision()
/*     */   {
/* 373 */     return (DecisionEvent)this.decisionStack.peek();
/*     */   }
/*     */ 
/*     */   public void recognitionException(RecognitionException e) {
/* 377 */     this.stats.numReportedErrors += 1;
/*     */   }
/*     */ 
/*     */   public void semanticPredicate(boolean result, String predicate) {
/* 381 */     this.stats.numSemanticPredicates += 1;
/* 382 */     if (inDecision()) {
/* 383 */       DecisionEvent d = currentDecision();
/* 384 */       d.evalSemPred = true;
/* 385 */       d.decision.numSemPredEvals += 1;
/* 386 */       if (dump) System.out.println("eval " + predicate + " in " + d.decision.ruleName + "-" + d.decision.decision);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void terminate()
/*     */   {
/* 392 */     for (Iterator i$ = this.decisionEvents.iterator(); i$.hasNext(); ) { DecisionEvent e = (DecisionEvent)i$.next();
/*     */ 
/* 394 */       e.decision.avgk += e.k;
/* 395 */       this.stats.avgkPerDecisionEvent += e.k;
/* 396 */       if (e.backtracks) {
/* 397 */         this.stats.avgkPerBacktrackingDecisionEvent += e.k;
/*     */       }
/*     */     }
/* 400 */     this.stats.averageDecisionPercentBacktracks = 0.0F;
/* 401 */     for (Iterator i$ = this.decisions.values().iterator(); i$.hasNext(); ) { DecisionDescriptor d = (DecisionDescriptor)i$.next();
/* 402 */       this.stats.numDecisionsCovered += 1;
/*     */       DecisionDescriptor tmp144_143 = d; tmp144_143.avgk = ((float)(tmp144_143.avgk / d.n));
/* 404 */       if (d.couldBacktrack) {
/* 405 */         this.stats.numDecisionsThatPotentiallyBacktrack += 1;
/* 406 */         float percentBacktracks = d.numBacktrackOccurrences / d.n;
/*     */ 
/* 408 */         this.stats.averageDecisionPercentBacktracks += percentBacktracks;
/*     */       }
/*     */ 
/* 411 */       if (d.numBacktrackOccurrences > 0) {
/* 412 */         this.stats.numDecisionsThatDoBacktrack += 1;
/*     */       }
/*     */     }
/* 415 */     this.stats.averageDecisionPercentBacktracks /= this.stats.numDecisionsThatPotentiallyBacktrack;
/* 416 */     this.stats.averageDecisionPercentBacktracks *= 100.0F;
/* 417 */     this.stats.avgkPerDecisionEvent /= this.stats.numDecisionEvents;
/*     */     ProfileStats tmp285_282 = this.stats; tmp285_282.avgkPerBacktrackingDecisionEvent = ((float)(tmp285_282.avgkPerBacktrackingDecisionEvent / this.stats.numBacktrackOccurrences));
/*     */ 
/* 420 */     System.err.println(toString());
/* 421 */     System.err.println(getDecisionStatsDump());
/*     */   }
/*     */ 
/*     */   public void setParser(DebugParser parser)
/*     */   {
/* 434 */     this.parser = parser;
/*     */   }
/*     */ 
/*     */   public String toNotifyString()
/*     */   {
/* 440 */     StringBuffer buf = new StringBuffer();
/* 441 */     buf.append("3");
/* 442 */     buf.append('\t');
/* 443 */     buf.append(this.parser.getClass().getName());
/*     */ 
/* 498 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 502 */     return toString(getReport());
/*     */   }
/*     */ 
/*     */   public ProfileStats getReport()
/*     */   {
/* 514 */     this.stats.Version = "3";
/* 515 */     this.stats.name = this.parser.getClass().getName();
/* 516 */     this.stats.numUniqueRulesInvoked = this.uniqueRules.size();
/*     */ 
/* 518 */     return this.stats;
/*     */   }
/*     */ 
/*     */   public DoubleKeyMap getDecisionStats() {
/* 522 */     return this.decisions;
/*     */   }
/*     */ 
/*     */   public List getDecisionEvents() {
/* 526 */     return this.decisionEvents;
/*     */   }
/*     */ 
/*     */   public static String toString(ProfileStats stats) {
/* 530 */     StringBuffer buf = new StringBuffer();
/* 531 */     buf.append("ANTLR Runtime Report; Profile Version ");
/* 532 */     buf.append(stats.Version);
/* 533 */     buf.append(newline);
/* 534 */     buf.append("parser name ");
/* 535 */     buf.append(stats.name);
/* 536 */     buf.append(newline);
/* 537 */     buf.append("Number of rule invocations ");
/* 538 */     buf.append(stats.numRuleInvocations);
/* 539 */     buf.append(newline);
/* 540 */     buf.append("Number of unique rules visited ");
/* 541 */     buf.append(stats.numUniqueRulesInvoked);
/* 542 */     buf.append(newline);
/* 543 */     buf.append("Number of decision events ");
/* 544 */     buf.append(stats.numDecisionEvents);
/* 545 */     buf.append(newline);
/* 546 */     buf.append("Overall average k per decision event ");
/* 547 */     buf.append(stats.avgkPerDecisionEvent);
/* 548 */     buf.append(newline);
/* 549 */     buf.append("Number of backtracking occurrences (can be multiple per decision) ");
/* 550 */     buf.append(stats.numBacktrackOccurrences);
/* 551 */     buf.append(newline);
/* 552 */     buf.append("Overall average k per decision event that backtracks ");
/* 553 */     buf.append(stats.avgkPerBacktrackingDecisionEvent);
/* 554 */     buf.append(newline);
/* 555 */     buf.append("Number of rule invocations while backtracking ");
/* 556 */     buf.append(stats.numGuessingRuleInvocations);
/* 557 */     buf.append(newline);
/* 558 */     buf.append("num decisions that potentially backtrack ");
/* 559 */     buf.append(stats.numDecisionsThatPotentiallyBacktrack);
/* 560 */     buf.append(newline);
/* 561 */     buf.append("num decisions that do backtrack ");
/* 562 */     buf.append(stats.numDecisionsThatDoBacktrack);
/* 563 */     buf.append(newline);
/* 564 */     buf.append("num decisions that potentially backtrack but don't ");
/* 565 */     buf.append(stats.numDecisionsThatPotentiallyBacktrack - stats.numDecisionsThatDoBacktrack);
/* 566 */     buf.append(newline);
/* 567 */     buf.append("average % of time a potentially backtracking decision backtracks ");
/* 568 */     buf.append(stats.averageDecisionPercentBacktracks);
/* 569 */     buf.append(newline);
/* 570 */     buf.append("num unique decisions covered ");
/* 571 */     buf.append(stats.numDecisionsCovered);
/* 572 */     buf.append(newline);
/* 573 */     buf.append("max rule invocation nesting depth ");
/* 574 */     buf.append(stats.maxRuleInvocationDepth);
/* 575 */     buf.append(newline);
/*     */ 
/* 622 */     buf.append("rule memoization cache size ");
/* 623 */     buf.append(stats.numMemoizationCacheEntries);
/* 624 */     buf.append(newline);
/* 625 */     buf.append("number of rule memoization cache hits ");
/* 626 */     buf.append(stats.numMemoizationCacheHits);
/* 627 */     buf.append(newline);
/* 628 */     buf.append("number of rule memoization cache misses ");
/* 629 */     buf.append(stats.numMemoizationCacheMisses);
/* 630 */     buf.append(newline);
/*     */ 
/* 634 */     buf.append("number of tokens ");
/* 635 */     buf.append(stats.numTokens);
/* 636 */     buf.append(newline);
/* 637 */     buf.append("number of hidden tokens ");
/* 638 */     buf.append(stats.numHiddenTokens);
/* 639 */     buf.append(newline);
/* 640 */     buf.append("number of char ");
/* 641 */     buf.append(stats.numCharsMatched);
/* 642 */     buf.append(newline);
/* 643 */     buf.append("number of hidden char ");
/* 644 */     buf.append(stats.numHiddenCharsMatched);
/* 645 */     buf.append(newline);
/* 646 */     buf.append("number of syntax errors ");
/* 647 */     buf.append(stats.numReportedErrors);
/* 648 */     buf.append(newline);
/* 649 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public String getDecisionStatsDump() {
/* 653 */     StringBuffer buf = new StringBuffer();
/* 654 */     buf.append("location");
/* 655 */     buf.append("\t");
/* 656 */     buf.append("n");
/* 657 */     buf.append("\t");
/* 658 */     buf.append("avgk");
/* 659 */     buf.append("\t");
/* 660 */     buf.append("maxk");
/* 661 */     buf.append("\t");
/* 662 */     buf.append("synpred");
/* 663 */     buf.append("\t");
/* 664 */     buf.append("sempred");
/* 665 */     buf.append("\t");
/* 666 */     buf.append("canbacktrack");
/* 667 */     buf.append("\n");
/* 668 */     for (Iterator i$ = this.decisions.keySet().iterator(); i$.hasNext(); ) { fileName = (String)i$.next();
/* 669 */       for (i$ = this.decisions.keySet(fileName).iterator(); i$.hasNext(); ) { int d = ((Integer)i$.next()).intValue();
/* 670 */         DecisionDescriptor s = (DecisionDescriptor)this.decisions.get(fileName, new Integer(d));
/* 671 */         buf.append(s.decision);
/* 672 */         buf.append("@");
/* 673 */         buf.append(locationDescription(s.fileName, s.ruleName, s.line, s.pos));
/* 674 */         buf.append("\t");
/* 675 */         buf.append(s.n);
/* 676 */         buf.append("\t");
/* 677 */         buf.append(String.format("%.2f", new Object[] { new Float(s.avgk) }));
/* 678 */         buf.append("\t");
/* 679 */         buf.append(s.maxk);
/* 680 */         buf.append("\t");
/* 681 */         buf.append(s.numBacktrackOccurrences);
/* 682 */         buf.append("\t");
/* 683 */         buf.append(s.numSemPredEvals);
/* 684 */         buf.append("\t");
/* 685 */         buf.append(s.couldBacktrack ? "1" : "0");
/* 686 */         buf.append(newline);
/*     */       }
/*     */     }
/*     */     String fileName;
/*     */     Iterator i$;
/* 689 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   protected int[] trim(int[] X, int n) {
/* 693 */     if (n < X.length) {
/* 694 */       int[] trimmed = new int[n];
/* 695 */       System.arraycopy(X, 0, trimmed, 0, n);
/* 696 */       X = trimmed;
/*     */     }
/* 698 */     return X;
/*     */   }
/*     */ 
/*     */   protected int[] toArray(List a) {
/* 702 */     int[] x = new int[a.size()];
/* 703 */     for (int i = 0; i < a.size(); i++) {
/* 704 */       Integer I = (Integer)a.get(i);
/* 705 */       x[i] = I.intValue();
/*     */     }
/* 707 */     return x;
/*     */   }
/*     */ 
/*     */   public int getNumberOfHiddenTokens(int i, int j)
/*     */   {
/* 712 */     int n = 0;
/* 713 */     TokenStream input = this.parser.getTokenStream();
/* 714 */     for (int ti = i; (ti < input.size()) && (ti <= j); ti++) {
/* 715 */       Token t = input.get(ti);
/* 716 */       if (t.getChannel() != 0) {
/* 717 */         n++;
/*     */       }
/*     */     }
/* 720 */     return n;
/*     */   }
/*     */ 
/*     */   protected String locationDescription() {
/* 724 */     return locationDescription((String)this.currentGrammarFileName.peek(), (String)this.currentRuleName.peek(), ((Integer)this.currentLine.peek()).intValue(), ((Integer)this.currentPos.peek()).intValue());
/*     */   }
/*     */ 
/*     */   protected String locationDescription(String file, String rule, int line, int pos)
/*     */   {
/* 732 */     return file + ":" + line + ":" + pos + "(" + rule + ")";
/*     */   }
/*     */ 
/*     */   public static class DecisionEvent
/*     */   {
/*     */     public Profiler.DecisionDescriptor decision;
/*     */     public int startIndex;
/*     */     public int k;
/*     */     public boolean backtracks;
/*     */     public boolean evalSemPred;
/*     */     public long startTime;
/*     */     public long stopTime;
/*     */     public int numMemoizationCacheHits;
/*     */     public int numMemoizationCacheMisses;
/*     */   }
/*     */ 
/*     */   public static class DecisionDescriptor
/*     */   {
/*     */     public int decision;
/*     */     public String fileName;
/*     */     public String ruleName;
/*     */     public int line;
/*     */     public int pos;
/*     */     public boolean couldBacktrack;
/*     */     public int n;
/*     */     public float avgk;
/*     */     public int maxk;
/*     */     public int numBacktrackOccurrences;
/*     */     public int numSemPredEvals;
/*     */   }
/*     */ 
/*     */   public static class ProfileStats
/*     */   {
/*     */     public String Version;
/*     */     public String name;
/*     */     public int numRuleInvocations;
/*     */     public int numUniqueRulesInvoked;
/*     */     public int numDecisionEvents;
/*     */     public int numDecisionsCovered;
/*     */     public int numDecisionsThatPotentiallyBacktrack;
/*     */     public int numDecisionsThatDoBacktrack;
/*     */     public int maxRuleInvocationDepth;
/*     */     public float avgkPerDecisionEvent;
/*     */     public float avgkPerBacktrackingDecisionEvent;
/*     */     public float averageDecisionPercentBacktracks;
/*     */     public int numBacktrackOccurrences;
/*     */     public int numFixedDecisions;
/*     */     public int minDecisionMaxFixedLookaheads;
/*     */     public int maxDecisionMaxFixedLookaheads;
/*     */     public int avgDecisionMaxFixedLookaheads;
/*     */     public int stddevDecisionMaxFixedLookaheads;
/*     */     public int numCyclicDecisions;
/*     */     public int minDecisionMaxCyclicLookaheads;
/*     */     public int maxDecisionMaxCyclicLookaheads;
/*     */     public int avgDecisionMaxCyclicLookaheads;
/*     */     public int stddevDecisionMaxCyclicLookaheads;
/*     */     public int numSemanticPredicates;
/*     */     public int numTokens;
/*     */     public int numHiddenTokens;
/*     */     public int numCharsMatched;
/*     */     public int numHiddenCharsMatched;
/*     */     public int numReportedErrors;
/*     */     public int numMemoizationCacheHits;
/*     */     public int numMemoizationCacheMisses;
/*     */     public int numGuessingRuleInvocations;
/*     */     public int numMemoizationCacheEntries;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.debug.Profiler
 * JD-Core Version:    0.6.2
 */