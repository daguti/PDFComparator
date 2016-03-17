/*    */ package org.antlr.analysis;
/*    */ 
/*    */ import org.antlr.tool.Grammar;
/*    */ import org.antlr.tool.GrammarAST;
/*    */ 
/*    */ public class ActionLabel extends Label
/*    */ {
/*    */   public GrammarAST actionAST;
/*    */ 
/*    */   public ActionLabel(GrammarAST actionAST)
/*    */   {
/* 37 */     super(-6);
/* 38 */     this.actionAST = actionAST;
/*    */   }
/*    */ 
/*    */   public boolean isEpsilon() {
/* 42 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean isAction() {
/* 46 */     return true;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 50 */     return "{" + this.actionAST + "}";
/*    */   }
/*    */ 
/*    */   public String toString(Grammar g) {
/* 54 */     return toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.analysis.ActionLabel
 * JD-Core Version:    0.6.2
 */