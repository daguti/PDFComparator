/*    */ package org.antlr.tool;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import org.antlr.analysis.DFA;
/*    */ import org.antlr.analysis.DecisionProbe;
/*    */ import org.antlr.analysis.NFA;
/*    */ import org.antlr.analysis.NFAState;
/*    */ import org.antlr.stringtemplate.StringTemplate;
/*    */ 
/*    */ public class NonRegularDecisionMessage extends Message
/*    */ {
/*    */   public DecisionProbe probe;
/*    */   public Set<Integer> altsWithRecursion;
/*    */ 
/*    */   public NonRegularDecisionMessage(DecisionProbe probe, Set<Integer> altsWithRecursion)
/*    */   {
/* 44 */     super(211);
/* 45 */     this.probe = probe;
/* 46 */     this.altsWithRecursion = altsWithRecursion;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 50 */     GrammarAST decisionASTNode = this.probe.dfa.getDecisionASTNode();
/* 51 */     this.line = decisionASTNode.getLine();
/* 52 */     this.column = decisionASTNode.getColumn();
/* 53 */     String fileName = this.probe.dfa.nfa.grammar.getFileName();
/* 54 */     if (fileName != null) {
/* 55 */       this.file = fileName;
/*    */     }
/*    */ 
/* 58 */     StringTemplate st = getMessageTemplate();
/* 59 */     String ruleName = this.probe.dfa.getNFADecisionStartState().enclosingRule.name;
/* 60 */     st.setAttribute("ruleName", ruleName);
/* 61 */     List sortedAlts = new ArrayList();
/* 62 */     sortedAlts.addAll(this.altsWithRecursion);
/* 63 */     Collections.sort(sortedAlts);
/* 64 */     st.setAttribute("alts", sortedAlts);
/*    */ 
/* 66 */     return super.toString(st);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.NonRegularDecisionMessage
 * JD-Core Version:    0.6.2
 */