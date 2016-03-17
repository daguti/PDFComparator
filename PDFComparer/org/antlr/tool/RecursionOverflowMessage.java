/*    */ package org.antlr.tool;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import org.antlr.analysis.DFA;
/*    */ import org.antlr.analysis.DFAState;
/*    */ import org.antlr.analysis.DecisionProbe;
/*    */ import org.antlr.analysis.NFA;
/*    */ import org.antlr.stringtemplate.StringTemplate;
/*    */ 
/*    */ public class RecursionOverflowMessage extends Message
/*    */ {
/*    */   public DecisionProbe probe;
/*    */   public DFAState sampleBadState;
/*    */   public int alt;
/*    */   public Collection targetRules;
/*    */   public Collection callSiteStates;
/*    */ 
/*    */   public RecursionOverflowMessage(DecisionProbe probe, DFAState sampleBadState, int alt, Collection targetRules, Collection callSiteStates)
/*    */   {
/* 53 */     super(206);
/* 54 */     this.probe = probe;
/* 55 */     this.sampleBadState = sampleBadState;
/* 56 */     this.alt = alt;
/* 57 */     this.targetRules = targetRules;
/* 58 */     this.callSiteStates = callSiteStates;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 62 */     GrammarAST decisionASTNode = this.probe.dfa.getDecisionASTNode();
/* 63 */     this.line = decisionASTNode.getLine();
/* 64 */     this.column = decisionASTNode.getColumn();
/* 65 */     String fileName = this.probe.dfa.nfa.grammar.getFileName();
/* 66 */     if (fileName != null) {
/* 67 */       this.file = fileName;
/*    */     }
/*    */ 
/* 70 */     StringTemplate st = getMessageTemplate();
/* 71 */     st.setAttribute("targetRules", this.targetRules);
/* 72 */     st.setAttribute("alt", this.alt);
/* 73 */     st.setAttribute("callSiteStates", this.callSiteStates);
/*    */ 
/* 75 */     List labels = this.probe.getSampleNonDeterministicInputSequence(this.sampleBadState);
/*    */ 
/* 77 */     String input = this.probe.getInputSequenceDisplay(labels);
/* 78 */     st.setAttribute("input", input);
/*    */ 
/* 80 */     return super.toString(st);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.RecursionOverflowMessage
 * JD-Core Version:    0.6.2
 */