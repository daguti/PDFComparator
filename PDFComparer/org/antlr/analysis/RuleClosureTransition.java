/*    */ package org.antlr.analysis;
/*    */ 
/*    */ import org.antlr.tool.Rule;
/*    */ 
/*    */ public class RuleClosureTransition extends Transition
/*    */ {
/*    */   public Rule rule;
/*    */   public NFAState followState;
/*    */ 
/*    */   public RuleClosureTransition(Rule rule, NFAState ruleStart, NFAState followState)
/*    */   {
/* 49 */     super(-5, ruleStart);
/* 50 */     this.rule = rule;
/* 51 */     this.followState = followState;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.analysis.RuleClosureTransition
 * JD-Core Version:    0.6.2
 */