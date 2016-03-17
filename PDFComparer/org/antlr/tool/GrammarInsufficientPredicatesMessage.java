/*    */ package org.antlr.tool;
/*    */ 
/*    */ import antlr.Token;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import org.antlr.analysis.DFA;
/*    */ import org.antlr.analysis.DFAState;
/*    */ import org.antlr.analysis.DecisionProbe;
/*    */ import org.antlr.analysis.NFA;
/*    */ import org.antlr.stringtemplate.StringTemplate;
/*    */ 
/*    */ public class GrammarInsufficientPredicatesMessage extends Message
/*    */ {
/*    */   public DecisionProbe probe;
/*    */   public Map<Integer, Set<Token>> altToLocations;
/*    */   public DFAState problemState;
/*    */ 
/*    */   public GrammarInsufficientPredicatesMessage(DecisionProbe probe, DFAState problemState, Map<Integer, Set<Token>> altToLocations)
/*    */   {
/* 47 */     super(203);
/* 48 */     this.probe = probe;
/* 49 */     this.problemState = problemState;
/* 50 */     this.altToLocations = altToLocations;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 54 */     GrammarAST decisionASTNode = this.probe.dfa.getDecisionASTNode();
/* 55 */     this.line = decisionASTNode.getLine();
/* 56 */     this.column = decisionASTNode.getColumn();
/* 57 */     String fileName = this.probe.dfa.nfa.grammar.getFileName();
/* 58 */     if (fileName != null) {
/* 59 */       this.file = fileName;
/*    */     }
/* 61 */     StringTemplate st = getMessageTemplate();
/*    */ 
/* 63 */     Map altToLocationsWithStringKey = new LinkedHashMap();
/* 64 */     List alts = new ArrayList();
/* 65 */     alts.addAll(this.altToLocations.keySet());
/* 66 */     Collections.sort(alts);
/* 67 */     for (Iterator i$ = alts.iterator(); i$.hasNext(); ) { Integer altI = (Integer)i$.next();
/* 68 */       altToLocationsWithStringKey.put(altI.toString(), this.altToLocations.get(altI));
/*    */     }
/*    */ 
/* 78 */     st.setAttribute("altToLocations", altToLocationsWithStringKey);
/*    */ 
/* 80 */     List sampleInputLabels = this.problemState.dfa.probe.getSampleNonDeterministicInputSequence(this.problemState);
/* 81 */     String input = this.problemState.dfa.probe.getInputSequenceDisplay(sampleInputLabels);
/* 82 */     st.setAttribute("upon", input);
/*    */ 
/* 84 */     st.setAttribute("hasPredicateBlockedByAction", new Boolean(this.problemState.dfa.hasPredicateBlockedByAction));
/*    */ 
/* 86 */     return super.toString(st);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.GrammarInsufficientPredicatesMessage
 * JD-Core Version:    0.6.2
 */