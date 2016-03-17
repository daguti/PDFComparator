/*    */ package org.antlr.analysis;
/*    */ 
/*    */ import org.antlr.tool.Grammar;
/*    */ import org.antlr.tool.GrammarAST;
/*    */ 
/*    */ public class PredicateLabel extends Label
/*    */ {
/*    */   protected SemanticContext semanticContext;
/*    */ 
/*    */   public PredicateLabel(GrammarAST predicateASTNode)
/*    */   {
/* 43 */     super(-4);
/* 44 */     this.semanticContext = new SemanticContext.Predicate(predicateASTNode);
/*    */   }
/*    */ 
/*    */   public PredicateLabel(SemanticContext semCtx)
/*    */   {
/* 49 */     super(-4);
/* 50 */     this.semanticContext = semCtx;
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 54 */     return this.semanticContext.hashCode();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object o) {
/* 58 */     if (o == null) {
/* 59 */       return false;
/*    */     }
/* 61 */     if (this == o) {
/* 62 */       return true;
/*    */     }
/* 64 */     if (!(o instanceof PredicateLabel)) {
/* 65 */       return false;
/*    */     }
/* 67 */     return this.semanticContext.equals(((PredicateLabel)o).semanticContext);
/*    */   }
/*    */ 
/*    */   public boolean isSemanticPredicate() {
/* 71 */     return true;
/*    */   }
/*    */ 
/*    */   public SemanticContext getSemanticContext() {
/* 75 */     return this.semanticContext;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 79 */     return "{" + this.semanticContext + "}?";
/*    */   }
/*    */ 
/*    */   public String toString(Grammar g) {
/* 83 */     return toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.analysis.PredicateLabel
 * JD-Core Version:    0.6.2
 */