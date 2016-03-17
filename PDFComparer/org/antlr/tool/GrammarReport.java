/*     */ package org.antlr.tool;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import org.antlr.analysis.DFA;
/*     */ import org.antlr.analysis.NFAState;
/*     */ import org.antlr.misc.IntSet;
/*     */ import org.antlr.misc.Utils;
/*     */ import org.antlr.runtime.misc.Stats;
/*     */ 
/*     */ public class GrammarReport
/*     */ {
/*     */   public static final String Version = "5";
/*     */   public static final String GRAMMAR_STATS_FILENAME = "grammar.stats";
/*  80 */   public static final String newline = System.getProperty("line.separator");
/*     */   public Grammar grammar;
/*     */ 
/*     */   public GrammarReport(Grammar grammar)
/*     */   {
/*  85 */     this.grammar = grammar;
/*     */   }
/*     */ 
/*     */   public static ReportData getReportData(Grammar g) {
/*  89 */     ReportData data = new ReportData();
/*  90 */     data.version = "5";
/*  91 */     data.gname = g.name;
/*     */ 
/*  93 */     data.gtype = g.getGrammarTypeString();
/*     */ 
/*  95 */     data.language = ((String)g.getOption("language"));
/*  96 */     data.output = ((String)g.getOption("output"));
/*  97 */     if (data.output == null) {
/*  98 */       data.output = "none";
/*     */     }
/*     */ 
/* 101 */     String k = (String)g.getOption("k");
/* 102 */     if (k == null) {
/* 103 */       k = "none";
/*     */     }
/* 105 */     data.grammarLevelk = k;
/*     */ 
/* 107 */     String backtrack = (String)g.getOption("backtrack");
/* 108 */     if (backtrack == null) {
/* 109 */       backtrack = "false";
/*     */     }
/* 111 */     data.grammarLevelBacktrack = backtrack;
/*     */ 
/* 113 */     int totalNonSynPredProductions = 0;
/* 114 */     int totalNonSynPredRules = 0;
/* 115 */     Collection rules = g.getRules();
/* 116 */     for (Iterator it = rules.iterator(); it.hasNext(); ) {
/* 117 */       Rule r = (Rule)it.next();
/* 118 */       if (!r.name.toUpperCase().startsWith("synpred".toUpperCase()))
/*     */       {
/* 121 */         totalNonSynPredProductions += r.numberOfAlts;
/* 122 */         totalNonSynPredRules++;
/*     */       }
/*     */     }
/*     */ 
/* 126 */     data.numRules = totalNonSynPredRules;
/* 127 */     data.numOuterProductions = totalNonSynPredProductions;
/*     */ 
/* 129 */     int numACyclicDecisions = g.getNumberOfDecisions() - g.getNumberOfCyclicDecisions();
/*     */ 
/* 131 */     List depths = new ArrayList();
/* 132 */     int[] acyclicDFAStates = new int[numACyclicDecisions];
/* 133 */     int[] cyclicDFAStates = new int[g.getNumberOfCyclicDecisions()];
/* 134 */     int acyclicIndex = 0;
/* 135 */     int cyclicIndex = 0;
/* 136 */     int numLL1 = 0;
/* 137 */     int blocksWithSynPreds = 0;
/* 138 */     int dfaWithSynPred = 0;
/* 139 */     int numDecisions = 0;
/* 140 */     int numCyclicDecisions = 0;
/* 141 */     for (int i = 1; i <= g.getNumberOfDecisions(); i++) {
/* 142 */       Grammar.Decision d = g.getDecision(i);
/* 143 */       if (d.dfa != null)
/*     */       {
/* 147 */         Rule r = d.dfa.decisionNFAStartState.enclosingRule;
/* 148 */         if (!r.name.toUpperCase().startsWith("synpred".toUpperCase()))
/*     */         {
/* 155 */           numDecisions++;
/* 156 */           if (blockHasSynPred(d.blockAST)) blocksWithSynPreds++;
/*     */ 
/* 158 */           if (d.dfa.hasSynPred()) dfaWithSynPred++;
/*     */ 
/* 172 */           if (!d.dfa.isCyclic()) {
/* 173 */             if (d.dfa.isClassicDFA()) {
/* 174 */               int maxk = d.dfa.getMaxLookaheadDepth();
/*     */ 
/* 176 */               if (maxk == 1) numLL1++;
/* 177 */               depths.add(new Integer(maxk));
/*     */             }
/*     */             else {
/* 180 */               acyclicDFAStates[acyclicIndex] = d.dfa.getNumberOfStates();
/* 181 */               acyclicIndex++;
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 186 */             numCyclicDecisions++;
/* 187 */             cyclicDFAStates[cyclicIndex] = d.dfa.getNumberOfStates();
/* 188 */             cyclicIndex++;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 192 */     data.numLL1 = numLL1;
/* 193 */     data.numberOfFixedKDecisions = depths.size();
/* 194 */     data.mink = Stats.min(depths);
/* 195 */     data.maxk = Stats.max(depths);
/* 196 */     data.avgk = Stats.avg(depths);
/*     */ 
/* 198 */     data.numberOfDecisionsInRealRules = numDecisions;
/* 199 */     data.numberOfDecisions = g.getNumberOfDecisions();
/* 200 */     data.numberOfCyclicDecisions = numCyclicDecisions;
/*     */ 
/* 205 */     data.blocksWithSynPreds = blocksWithSynPreds;
/* 206 */     data.decisionsWhoseDFAsUsesSynPreds = dfaWithSynPred;
/*     */ 
/* 231 */     data.numTokens = g.getTokenTypes().size();
/*     */ 
/* 233 */     data.DFACreationWallClockTimeInMS = g.DFACreationWallClockTimeInMS;
/*     */ 
/* 236 */     data.numberOfSemanticPredicates = g.numberOfSemanticPredicates;
/*     */ 
/* 238 */     data.numberOfManualLookaheadOptions = g.numberOfManualLookaheadOptions;
/*     */ 
/* 240 */     data.numNonLLStarDecisions = g.numNonLLStar;
/* 241 */     data.numNondeterministicDecisions = g.setOfNondeterministicDecisionNumbers.size();
/* 242 */     data.numNondeterministicDecisionNumbersResolvedWithPredicates = g.setOfNondeterministicDecisionNumbersResolvedWithPredicates.size();
/*     */ 
/* 245 */     data.errors = ErrorManager.getErrorState().errors;
/* 246 */     data.warnings = ErrorManager.getErrorState().warnings;
/* 247 */     data.infos = ErrorManager.getErrorState().infos;
/*     */ 
/* 249 */     data.blocksWithSemPreds = g.blocksWithSemPreds.size();
/*     */ 
/* 251 */     data.decisionsWhoseDFAsUsesSemPreds = g.decisionsWhoseDFAsUsesSemPreds.size();
/*     */ 
/* 253 */     return data;
/*     */   }
/*     */ 
/*     */   public String toNotifyString()
/*     */   {
/* 260 */     StringBuffer buf = new StringBuffer();
/* 261 */     ReportData data = getReportData(this.grammar);
/* 262 */     Field[] fields = ReportData.class.getDeclaredFields();
/* 263 */     int i = 0;
/* 264 */     Field[] arr$ = fields; int len$ = arr$.length; for (int i$ = 0; i$ < len$; i$++) { Field f = arr$[i$];
/*     */       try {
/* 266 */         Object v = f.get(data);
/* 267 */         String s = v != null ? v.toString() : "null";
/* 268 */         if (i > 0) buf.append('\t');
/* 269 */         buf.append(s);
/*     */       }
/*     */       catch (Exception e) {
/* 272 */         ErrorManager.internalError("Can't get data", e);
/*     */       }
/* 274 */       i++;
/*     */     }
/* 276 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public String getBacktrackingReport() {
/* 280 */     StringBuffer buf = new StringBuffer();
/* 281 */     buf.append("Backtracking report:");
/* 282 */     buf.append(newline);
/* 283 */     buf.append("Number of decisions that backtrack: ");
/* 284 */     buf.append(this.grammar.decisionsWhoseDFAsUsesSynPreds.size());
/* 285 */     buf.append(newline);
/* 286 */     buf.append(getDFALocations(this.grammar.decisionsWhoseDFAsUsesSynPreds));
/* 287 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   protected String getDFALocations(Set dfas) {
/* 291 */     Set decisions = new HashSet();
/* 292 */     StringBuffer buf = new StringBuffer();
/* 293 */     Iterator it = dfas.iterator();
/* 294 */     while (it.hasNext()) {
/* 295 */       DFA dfa = (DFA)it.next();
/*     */ 
/* 297 */       if (!decisions.contains(Utils.integer(dfa.decisionNumber)))
/*     */       {
/* 300 */         decisions.add(Utils.integer(dfa.decisionNumber));
/* 301 */         buf.append("Rule ");
/* 302 */         buf.append(dfa.decisionNFAStartState.enclosingRule.name);
/* 303 */         buf.append(" decision ");
/* 304 */         buf.append(dfa.decisionNumber);
/* 305 */         buf.append(" location ");
/* 306 */         GrammarAST decisionAST = dfa.decisionNFAStartState.associatedASTNode;
/*     */ 
/* 308 */         buf.append(decisionAST.getLine());
/* 309 */         buf.append(":");
/* 310 */         buf.append(decisionAST.getColumn());
/* 311 */         buf.append(newline);
/*     */       }
/*     */     }
/* 313 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 321 */     return toString(toNotifyString());
/*     */   }
/*     */ 
/*     */   protected static ReportData decodeReportData(String dataS) {
/* 325 */     ReportData data = new ReportData();
/* 326 */     StringTokenizer st = new StringTokenizer(dataS, "\t");
/* 327 */     Field[] fields = ReportData.class.getDeclaredFields();
/* 328 */     Field[] arr$ = fields; int len$ = arr$.length; for (int i$ = 0; i$ < len$; i$++) { Field f = arr$[i$];
/* 329 */       String v = st.nextToken();
/*     */       try {
/* 331 */         if (f.getType() == String.class) {
/* 332 */           f.set(data, v);
/*     */         }
/* 334 */         else if (f.getType() == Double.TYPE) {
/* 335 */           f.set(data, Double.valueOf(v));
/*     */         }
/*     */         else
/* 338 */           f.set(data, Integer.valueOf(v));
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 342 */         ErrorManager.internalError("Can't get data", e);
/*     */       }
/*     */     }
/* 345 */     return data;
/*     */   }
/*     */ 
/*     */   public static String toString(String notifyDataLine) {
/* 349 */     ReportData data = decodeReportData(notifyDataLine);
/* 350 */     if (data == null) {
/* 351 */       return null;
/*     */     }
/* 353 */     StringBuffer buf = new StringBuffer();
/* 354 */     buf.append("ANTLR Grammar Report; Stats Version ");
/* 355 */     buf.append(data.version);
/* 356 */     buf.append('\n');
/* 357 */     buf.append("Grammar: ");
/* 358 */     buf.append(data.gname);
/* 359 */     buf.append('\n');
/* 360 */     buf.append("Type: ");
/* 361 */     buf.append(data.gtype);
/* 362 */     buf.append('\n');
/* 363 */     buf.append("Target language: ");
/* 364 */     buf.append(data.language);
/* 365 */     buf.append('\n');
/* 366 */     buf.append("Output: ");
/* 367 */     buf.append(data.output);
/* 368 */     buf.append('\n');
/* 369 */     buf.append("Grammar option k: ");
/* 370 */     buf.append(data.grammarLevelk);
/* 371 */     buf.append('\n');
/* 372 */     buf.append("Grammar option backtrack: ");
/* 373 */     buf.append(data.grammarLevelBacktrack);
/* 374 */     buf.append('\n');
/* 375 */     buf.append("Rules: ");
/* 376 */     buf.append(data.numRules);
/* 377 */     buf.append('\n');
/* 378 */     buf.append("Outer productions: ");
/* 379 */     buf.append(data.numOuterProductions);
/* 380 */     buf.append('\n');
/* 381 */     buf.append("Decisions: ");
/* 382 */     buf.append(data.numberOfDecisions);
/* 383 */     buf.append('\n');
/* 384 */     buf.append("Decisions (ignoring decisions in synpreds): ");
/* 385 */     buf.append(data.numberOfDecisionsInRealRules);
/* 386 */     buf.append('\n');
/* 387 */     buf.append("Fixed k DFA decisions: ");
/* 388 */     buf.append(data.numberOfFixedKDecisions);
/* 389 */     buf.append('\n');
/* 390 */     buf.append("Cyclic DFA decisions: ");
/* 391 */     buf.append(data.numberOfCyclicDecisions);
/* 392 */     buf.append('\n');
/* 393 */     buf.append("LL(1) decisions: "); buf.append(data.numLL1);
/* 394 */     buf.append('\n');
/* 395 */     buf.append("Min fixed k: "); buf.append(data.mink);
/* 396 */     buf.append('\n');
/* 397 */     buf.append("Max fixed k: "); buf.append(data.maxk);
/* 398 */     buf.append('\n');
/* 399 */     buf.append("Average fixed k: "); buf.append(data.avgk);
/* 400 */     buf.append('\n');
/*     */ 
/* 423 */     buf.append("DFA creation time in ms: ");
/* 424 */     buf.append(data.DFACreationWallClockTimeInMS);
/* 425 */     buf.append('\n');
/*     */ 
/* 430 */     buf.append("Decisions with available syntactic predicates (ignoring synpred rules): ");
/* 431 */     buf.append(data.blocksWithSynPreds);
/* 432 */     buf.append('\n');
/* 433 */     buf.append("Decision DFAs using syntactic predicates (ignoring synpred rules): ");
/* 434 */     buf.append(data.decisionsWhoseDFAsUsesSynPreds);
/* 435 */     buf.append('\n');
/*     */ 
/* 437 */     buf.append("Number of semantic predicates found: ");
/* 438 */     buf.append(data.numberOfSemanticPredicates);
/* 439 */     buf.append('\n');
/* 440 */     buf.append("Decisions with semantic predicates: ");
/* 441 */     buf.append(data.blocksWithSemPreds);
/* 442 */     buf.append('\n');
/* 443 */     buf.append("Decision DFAs using semantic predicates: ");
/* 444 */     buf.append(data.decisionsWhoseDFAsUsesSemPreds);
/* 445 */     buf.append('\n');
/*     */ 
/* 447 */     buf.append("Number of (likely) non-LL(*) decisions: ");
/* 448 */     buf.append(data.numNonLLStarDecisions);
/* 449 */     buf.append('\n');
/* 450 */     buf.append("Number of nondeterministic decisions: ");
/* 451 */     buf.append(data.numNondeterministicDecisions);
/* 452 */     buf.append('\n');
/* 453 */     buf.append("Number of nondeterministic decisions resolved with predicates: ");
/* 454 */     buf.append(data.numNondeterministicDecisionNumbersResolvedWithPredicates);
/* 455 */     buf.append('\n');
/*     */ 
/* 457 */     buf.append("Number of manual or forced fixed lookahead k=value options: ");
/* 458 */     buf.append(data.numberOfManualLookaheadOptions);
/* 459 */     buf.append('\n');
/*     */ 
/* 461 */     buf.append("Vocabulary size: ");
/* 462 */     buf.append(data.numTokens);
/* 463 */     buf.append('\n');
/* 464 */     buf.append("Number of errors: ");
/* 465 */     buf.append(data.errors);
/* 466 */     buf.append('\n');
/* 467 */     buf.append("Number of warnings: ");
/* 468 */     buf.append(data.warnings);
/* 469 */     buf.append('\n');
/* 470 */     buf.append("Number of infos: ");
/* 471 */     buf.append(data.infos);
/* 472 */     buf.append('\n');
/* 473 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public static boolean blockHasSynPred(GrammarAST blockAST) {
/* 477 */     GrammarAST c1 = blockAST.findFirstType(36);
/* 478 */     GrammarAST c2 = blockAST.findFirstType(37);
/* 479 */     if ((c1 != null) || (c2 != null)) return true;
/*     */ 
/* 482 */     return false;
/*     */   }
/*     */ 
/*     */   public static class ReportData
/*     */   {
/*     */     String version;
/*     */     String gname;
/*     */     String gtype;
/*     */     String language;
/*     */     int numRules;
/*     */     int numOuterProductions;
/*     */     int numberOfDecisionsInRealRules;
/*     */     int numberOfDecisions;
/*     */     int numberOfCyclicDecisions;
/*     */     int numberOfFixedKDecisions;
/*     */     int numLL1;
/*     */     int mink;
/*     */     int maxk;
/*     */     double avgk;
/*     */     int numTokens;
/*     */     long DFACreationWallClockTimeInMS;
/*     */     int numberOfSemanticPredicates;
/*     */     int numberOfManualLookaheadOptions;
/*     */     int numNonLLStarDecisions;
/*     */     int numNondeterministicDecisions;
/*     */     int numNondeterministicDecisionNumbersResolvedWithPredicates;
/*     */     int errors;
/*     */     int warnings;
/*     */     int infos;
/*     */     int blocksWithSynPreds;
/*     */     int decisionsWhoseDFAsUsesSynPreds;
/*     */     int blocksWithSemPreds;
/*     */     int decisionsWhoseDFAsUsesSemPreds;
/*     */     String output;
/*     */     String grammarLevelk;
/*     */     String grammarLevelBacktrack;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.GrammarReport
 * JD-Core Version:    0.6.2
 */