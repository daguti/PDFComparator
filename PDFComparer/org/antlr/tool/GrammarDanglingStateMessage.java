/*    */ package org.antlr.tool;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import org.antlr.analysis.DFA;
/*    */ import org.antlr.analysis.DFAState;
/*    */ import org.antlr.analysis.DecisionProbe;
/*    */ import org.antlr.analysis.NFA;
/*    */ import org.antlr.stringtemplate.StringTemplate;
/*    */ 
/*    */ public class GrammarDanglingStateMessage extends Message
/*    */ {
/*    */   public DecisionProbe probe;
/*    */   public DFAState problemState;
/*    */ 
/*    */   public GrammarDanglingStateMessage(DecisionProbe probe, DFAState problemState)
/*    */   {
/* 48 */     super(202);
/* 49 */     this.probe = probe;
/* 50 */     this.problemState = problemState;
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
/* 61 */     List labels = this.probe.getSampleNonDeterministicInputSequence(this.problemState);
/* 62 */     String input = this.probe.getInputSequenceDisplay(labels);
/* 63 */     StringTemplate st = getMessageTemplate();
/* 64 */     List alts = new ArrayList();
/* 65 */     alts.addAll(this.problemState.getAltSet());
/* 66 */     Collections.sort(alts);
/* 67 */     st.setAttribute("danglingAlts", alts);
/* 68 */     st.setAttribute("input", input);
/*    */ 
/* 70 */     return super.toString(st);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.GrammarDanglingStateMessage
 * JD-Core Version:    0.6.2
 */