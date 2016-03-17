/*     */ package org.antlr.runtime.tree;
/*     */ 
/*     */ import org.antlr.runtime.RecognitionException;
/*     */ import org.antlr.runtime.RecognizerSharedState;
/*     */ import org.antlr.runtime.TokenStream;
/*     */ 
/*     */ public class TreeFilter extends TreeParser
/*     */ {
/*     */   protected TokenStream originalTokenStream;
/*     */   protected TreeAdaptor originalAdaptor;
/* 118 */   fptr topdown_fptr = new fptr() {
/*     */     public void rule() throws RecognitionException {
/* 120 */       TreeFilter.this.topdown();
/*     */     }
/* 118 */   };
/*     */ 
/* 124 */   fptr bottomup_fptr = new fptr() {
/*     */     public void rule() throws RecognitionException {
/* 126 */       TreeFilter.this.bottomup();
/*     */     }
/* 124 */   };
/*     */ 
/*     */   public TreeFilter(TreeNodeStream input)
/*     */   {
/*  87 */     this(input, new RecognizerSharedState());
/*     */   }
/*     */   public TreeFilter(TreeNodeStream input, RecognizerSharedState state) {
/*  90 */     super(input, state);
/*  91 */     this.originalAdaptor = input.getTreeAdaptor();
/*  92 */     this.originalTokenStream = input.getTokenStream();
/*     */   }
/*     */ 
/*     */   public void applyOnce(Object t, fptr whichRule) {
/*  96 */     if (t == null) return;
/*     */     try
/*     */     {
/*  99 */       this.state = new RecognizerSharedState();
/* 100 */       this.input = new CommonTreeNodeStream(this.originalAdaptor, t);
/* 101 */       ((CommonTreeNodeStream)this.input).setTokenStream(this.originalTokenStream);
/* 102 */       setBacktrackingLevel(1);
/* 103 */       whichRule.rule();
/* 104 */       setBacktrackingLevel(0);
/*     */     } catch (RecognitionException e) {
/*     */     }
/*     */   }
/*     */ 
/*     */   public void downup(Object t) {
/* 110 */     TreeVisitor v = new TreeVisitor(new CommonTreeAdaptor());
/* 111 */     TreeVisitorAction actions = new TreeVisitorAction() {
/* 112 */       public Object pre(Object t) { TreeFilter.this.applyOnce(t, TreeFilter.this.topdown_fptr); return t; } 
/* 113 */       public Object post(Object t) { TreeFilter.this.applyOnce(t, TreeFilter.this.bottomup_fptr); return t;
/*     */       }
/*     */     };
/* 115 */     v.visit(t, actions);
/*     */   }
/*     */ 
/*     */   public void topdown()
/*     */     throws RecognitionException
/*     */   {
/*     */   }
/*     */ 
/*     */   public void bottomup()
/*     */     throws RecognitionException
/*     */   {
/*     */   }
/*     */ 
/*     */   public static abstract interface fptr
/*     */   {
/*     */     public abstract void rule()
/*     */       throws RecognitionException;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.tree.TreeFilter
 * JD-Core Version:    0.6.2
 */