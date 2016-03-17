/*     */ package org.antlr.tool;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.antlr.analysis.DFA;
/*     */ import org.antlr.analysis.DFAState;
/*     */ import org.antlr.analysis.DecisionProbe;
/*     */ import org.antlr.analysis.NFA;
/*     */ import org.antlr.analysis.NFAState;
/*     */ import org.antlr.stringtemplate.StringTemplate;
/*     */ 
/*     */ public class GrammarNonDeterminismMessage extends Message
/*     */ {
/*     */   public DecisionProbe probe;
/*     */   public DFAState problemState;
/*     */ 
/*     */   public GrammarNonDeterminismMessage(DecisionProbe probe, DFAState problemState)
/*     */   {
/*  49 */     super(200);
/*  50 */     this.probe = probe;
/*  51 */     this.problemState = problemState;
/*     */ 
/*  53 */     if (probe.dfa.isTokensRuleDecision())
/*  54 */       setMessageID(209);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  59 */     GrammarAST decisionASTNode = this.probe.dfa.getDecisionASTNode();
/*  60 */     this.line = decisionASTNode.getLine();
/*  61 */     this.column = decisionASTNode.getColumn();
/*  62 */     String fileName = this.probe.dfa.nfa.grammar.getFileName();
/*  63 */     if (fileName != null) {
/*  64 */       this.file = fileName;
/*     */     }
/*     */ 
/*  67 */     StringTemplate st = getMessageTemplate();
/*     */ 
/*  69 */     List labels = this.probe.getSampleNonDeterministicInputSequence(this.problemState);
/*  70 */     String input = this.probe.getInputSequenceDisplay(labels);
/*  71 */     st.setAttribute("input", input);
/*     */     Iterator it;
/*  73 */     if (this.probe.dfa.isTokensRuleDecision()) {
/*  74 */       Set disabledAlts = this.probe.getDisabledAlternatives(this.problemState);
/*  75 */       for (it = disabledAlts.iterator(); it.hasNext(); ) {
/*  76 */         Integer altI = (Integer)it.next();
/*  77 */         String tokenName = this.probe.getTokenNameForTokensRuleAlt(altI.intValue());
/*     */ 
/*  80 */         NFAState ruleStart = this.probe.dfa.nfa.grammar.getRuleStartState(tokenName);
/*     */ 
/*  82 */         this.line = ruleStart.associatedASTNode.getLine();
/*  83 */         this.column = ruleStart.associatedASTNode.getColumn();
/*  84 */         st.setAttribute("disabled", tokenName);
/*     */       }
/*     */     }
/*     */     else {
/*  88 */       st.setAttribute("disabled", this.probe.getDisabledAlternatives(this.problemState));
/*     */     }
/*     */ 
/*  91 */     List nondetAlts = this.probe.getNonDeterministicAltsForState(this.problemState);
/*  92 */     NFAState nfaStart = this.probe.dfa.getNFADecisionStartState();
/*     */ 
/*  94 */     int firstAlt = 0;
/*     */     Iterator iter;
/*  95 */     if (nondetAlts != null) {
/*  96 */       for (iter = nondetAlts.iterator(); iter.hasNext(); ) {
/*  97 */         Integer displayAltI = (Integer)iter.next();
/*  98 */         if (DecisionProbe.verbose) {
/*  99 */           int tracePathAlt = nfaStart.translateDisplayAltToWalkAlt(displayAltI.intValue());
/*     */ 
/* 101 */           if (firstAlt == 0) {
/* 102 */             firstAlt = tracePathAlt;
/*     */           }
/* 104 */           List path = this.probe.getNFAPathStatesForAlt(firstAlt, tracePathAlt, labels);
/*     */ 
/* 108 */           st.setAttribute("paths.{alt,states}", displayAltI, path);
/*     */         }
/* 112 */         else if (this.probe.dfa.isTokensRuleDecision())
/*     */         {
/* 114 */           String tokenName = this.probe.getTokenNameForTokensRuleAlt(displayAltI.intValue());
/*     */ 
/* 116 */           st.setAttribute("conflictingTokens", tokenName);
/*     */         }
/*     */         else {
/* 119 */           st.setAttribute("conflictingAlts", displayAltI);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 124 */     st.setAttribute("hasPredicateBlockedByAction", new Boolean(this.problemState.dfa.hasPredicateBlockedByAction));
/* 125 */     return super.toString(st);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.GrammarNonDeterminismMessage
 * JD-Core Version:    0.6.2
 */