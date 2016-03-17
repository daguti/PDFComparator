/*    */ package org.antlr.tool;
/*    */ 
/*    */ import org.antlr.analysis.DFA;
/*    */ import org.antlr.analysis.DecisionProbe;
/*    */ import org.antlr.analysis.NFA;
/*    */ import org.antlr.analysis.NFAState;
/*    */ import org.antlr.stringtemplate.StringTemplate;
/*    */ 
/*    */ public class GrammarAnalysisAbortedMessage extends Message
/*    */ {
/*    */   public DecisionProbe probe;
/*    */ 
/*    */   public GrammarAnalysisAbortedMessage(DecisionProbe probe)
/*    */   {
/* 40 */     super(205);
/* 41 */     this.probe = probe;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 45 */     GrammarAST decisionASTNode = this.probe.dfa.getDecisionASTNode();
/* 46 */     this.line = decisionASTNode.getLine();
/* 47 */     this.column = decisionASTNode.getColumn();
/* 48 */     String fileName = this.probe.dfa.nfa.grammar.getFileName();
/* 49 */     if (fileName != null) {
/* 50 */       this.file = fileName;
/*    */     }
/* 52 */     StringTemplate st = getMessageTemplate();
/* 53 */     st.setAttribute("enclosingRule", this.probe.dfa.getNFADecisionStartState().enclosingRule.name);
/*    */ 
/* 56 */     return super.toString(st);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.GrammarAnalysisAbortedMessage
 * JD-Core Version:    0.6.2
 */