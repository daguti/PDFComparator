/*     */ package org.antlr.runtime.debug;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Stack;
/*     */ import org.antlr.runtime.RecognitionException;
/*     */ import org.antlr.runtime.Token;
/*     */ import org.antlr.runtime.tree.ParseTree;
/*     */ 
/*     */ public class ParseTreeBuilder extends BlankDebugEventListener
/*     */ {
/*     */   public static final String EPSILON_PAYLOAD = "<epsilon>";
/*  44 */   Stack callStack = new Stack();
/*  45 */   List hiddenTokens = new ArrayList();
/*  46 */   int backtracking = 0;
/*     */ 
/*     */   public ParseTreeBuilder(String grammarName) {
/*  49 */     ParseTree root = create("<grammar " + grammarName + ">");
/*  50 */     this.callStack.push(root);
/*     */   }
/*     */ 
/*     */   public ParseTree getTree() {
/*  54 */     return (ParseTree)this.callStack.elementAt(0);
/*     */   }
/*     */ 
/*     */   public ParseTree create(Object payload)
/*     */   {
/*  61 */     return new ParseTree(payload);
/*     */   }
/*     */ 
/*     */   public ParseTree epsilonNode() {
/*  65 */     return create("<epsilon>");
/*     */   }
/*     */ 
/*     */   public void enterDecision(int d, boolean couldBacktrack) {
/*  69 */     this.backtracking += 1; } 
/*  70 */   public void exitDecision(int i) { this.backtracking -= 1; }
/*     */ 
/*     */   public void enterRule(String filename, String ruleName) {
/*  73 */     if (this.backtracking > 0) return;
/*  74 */     ParseTree parentRuleNode = (ParseTree)this.callStack.peek();
/*  75 */     ParseTree ruleNode = create(ruleName);
/*  76 */     parentRuleNode.addChild(ruleNode);
/*  77 */     this.callStack.push(ruleNode);
/*     */   }
/*     */ 
/*     */   public void exitRule(String filename, String ruleName) {
/*  81 */     if (this.backtracking > 0) return;
/*  82 */     ParseTree ruleNode = (ParseTree)this.callStack.peek();
/*  83 */     if (ruleNode.getChildCount() == 0) {
/*  84 */       ruleNode.addChild(epsilonNode());
/*     */     }
/*  86 */     this.callStack.pop();
/*     */   }
/*     */ 
/*     */   public void consumeToken(Token token) {
/*  90 */     if (this.backtracking > 0) return;
/*  91 */     ParseTree ruleNode = (ParseTree)this.callStack.peek();
/*  92 */     ParseTree elementNode = create(token);
/*  93 */     elementNode.hiddenTokens = this.hiddenTokens;
/*  94 */     this.hiddenTokens = new ArrayList();
/*  95 */     ruleNode.addChild(elementNode);
/*     */   }
/*     */ 
/*     */   public void consumeHiddenToken(Token token) {
/*  99 */     if (this.backtracking > 0) return;
/* 100 */     this.hiddenTokens.add(token);
/*     */   }
/*     */ 
/*     */   public void recognitionException(RecognitionException e) {
/* 104 */     if (this.backtracking > 0) return;
/* 105 */     ParseTree ruleNode = (ParseTree)this.callStack.peek();
/* 106 */     ParseTree errorNode = create(e);
/* 107 */     ruleNode.addChild(errorNode);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.debug.ParseTreeBuilder
 * JD-Core Version:    0.6.2
 */