/*    */ package org.antlr.tool;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.antlr.analysis.DFA;
/*    */ import org.antlr.analysis.NFAState;
/*    */ 
/*    */ public class GrammarReport2
/*    */ {
/* 38 */   public static final String newline = System.getProperty("line.separator");
/*    */   public Grammar root;
/*    */ 
/*    */   public GrammarReport2(Grammar rootGrammar)
/*    */   {
/* 43 */     this.root = rootGrammar;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 47 */     StringBuilder buf = new StringBuilder();
/* 48 */     stats(this.root, buf);
/* 49 */     CompositeGrammar composite = this.root.composite;
/* 50 */     for (Iterator i$ = composite.getDelegates(this.root).iterator(); i$.hasNext(); ) { Grammar g = (Grammar)i$.next();
/* 51 */       stats(g, buf);
/*    */     }
/* 53 */     return buf.toString();
/*    */   }
/*    */ 
/*    */   void stats(Grammar g, StringBuilder buf) {
/* 57 */     int numDec = g.getNumberOfDecisions();
/* 58 */     for (int decision = 1; decision <= numDec; decision++) {
/* 59 */       Grammar.Decision d = g.getDecision(decision);
/* 60 */       if (d.dfa != null)
/*    */       {
/* 64 */         int k = d.dfa.getMaxLookaheadDepth();
/* 65 */         Rule enclosingRule = d.dfa.decisionNFAStartState.enclosingRule;
/* 66 */         if (!enclosingRule.isSynPred) {
/* 67 */           buf.append(g.name + "." + enclosingRule.name + ":" + "");
/*    */ 
/* 69 */           GrammarAST decisionAST = d.dfa.decisionNFAStartState.associatedASTNode;
/*    */ 
/* 71 */           buf.append(decisionAST.getLine());
/* 72 */           buf.append(":");
/* 73 */           buf.append(decisionAST.getColumn());
/* 74 */           buf.append(" decision " + decision + ":");
/*    */ 
/* 76 */           if (d.dfa.isCyclic()) buf.append(" cyclic");
/* 77 */           if (k != 2147483647) buf.append(" k=" + k);
/* 78 */           if (d.dfa.hasSynPred()) buf.append(" backtracks");
/* 79 */           if (d.dfa.hasSemPred()) buf.append(" sempred");
/*    */ 
/* 86 */           nl(buf); } 
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/* 91 */   void nl(StringBuilder buf) { buf.append(newline); }
/*    */ 
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.GrammarReport2
 * JD-Core Version:    0.6.2
 */