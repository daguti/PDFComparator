/*    */ package org.antlr.analysis;
/*    */ 
/*    */ import org.antlr.tool.CompositeGrammar;
/*    */ import org.antlr.tool.Grammar;
/*    */ import org.antlr.tool.NFAFactory;
/*    */ 
/*    */ public class NFA
/*    */ {
/*    */   public static final int INVALID_ALT_NUMBER = -1;
/*    */   public Grammar grammar;
/* 45 */   protected NFAFactory factory = null;
/*    */   public boolean complete;
/*    */ 
/*    */   public NFA(Grammar g)
/*    */   {
/* 50 */     this.grammar = g;
/*    */   }
/*    */ 
/*    */   public int getNewNFAStateNumber() {
/* 54 */     return this.grammar.composite.getNewNFAStateNumber();
/*    */   }
/*    */ 
/*    */   public void addState(NFAState state) {
/* 58 */     this.grammar.composite.addState(state);
/*    */   }
/*    */ 
/*    */   public NFAState getState(int s) {
/* 62 */     return this.grammar.composite.getState(s);
/*    */   }
/*    */ 
/*    */   public NFAFactory getFactory() {
/* 66 */     return this.factory;
/*    */   }
/*    */ 
/*    */   public void setFactory(NFAFactory factory) {
/* 70 */     this.factory = factory;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.analysis.NFA
 * JD-Core Version:    0.6.2
 */