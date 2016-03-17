/*    */ package org.antlr.tool;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.antlr.analysis.DFA;
/*    */ import org.antlr.analysis.DecisionProbe;
/*    */ import org.antlr.analysis.NFA;
/*    */ import org.antlr.analysis.NFAState;
/*    */ import org.antlr.stringtemplate.StringTemplate;
/*    */ 
/*    */ public class GrammarUnreachableAltsMessage extends Message
/*    */ {
/*    */   public DecisionProbe probe;
/*    */   public List alts;
/*    */ 
/*    */   public GrammarUnreachableAltsMessage(DecisionProbe probe, List alts)
/*    */   {
/* 46 */     super(201);
/* 47 */     this.probe = probe;
/* 48 */     this.alts = alts;
/*    */ 
/* 50 */     if (probe.dfa.isTokensRuleDecision())
/* 51 */       setMessageID(208);
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 56 */     GrammarAST decisionASTNode = this.probe.dfa.getDecisionASTNode();
/* 57 */     this.line = decisionASTNode.getLine();
/* 58 */     this.column = decisionASTNode.getColumn();
/* 59 */     String fileName = this.probe.dfa.nfa.grammar.getFileName();
/* 60 */     if (fileName != null) {
/* 61 */       this.file = fileName;
/*    */     }
/*    */ 
/* 64 */     StringTemplate st = getMessageTemplate();
/*    */ 
/* 66 */     if (this.probe.dfa.isTokensRuleDecision())
/*    */     {
/* 68 */       for (int i = 0; i < this.alts.size(); i++) {
/* 69 */         Integer altI = (Integer)this.alts.get(i);
/* 70 */         String tokenName = this.probe.getTokenNameForTokensRuleAlt(altI.intValue());
/*    */ 
/* 73 */         NFAState ruleStart = this.probe.dfa.nfa.grammar.getRuleStartState(tokenName);
/*    */ 
/* 75 */         this.line = ruleStart.associatedASTNode.getLine();
/* 76 */         this.column = ruleStart.associatedASTNode.getColumn();
/* 77 */         st.setAttribute("tokens", tokenName);
/*    */       }
/*    */     }
/*    */     else
/*    */     {
/* 82 */       st.setAttribute("alts", this.alts);
/*    */     }
/*    */ 
/* 85 */     return super.toString(st);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.GrammarUnreachableAltsMessage
 * JD-Core Version:    0.6.2
 */