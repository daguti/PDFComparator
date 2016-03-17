/*     */ package org.antlr.runtime.tree;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import org.antlr.runtime.RecognitionException;
/*     */ import org.antlr.runtime.RecognizerSharedState;
/*     */ import org.antlr.runtime.TokenStream;
/*     */ 
/*     */ public class TreeRewriter extends TreeParser
/*     */ {
/*  39 */   protected boolean showTransformations = false;
/*     */   protected TokenStream originalTokenStream;
/*     */   protected TreeAdaptor originalAdaptor;
/* 107 */   fptr topdown_fptr = new fptr() {
/* 108 */     public Object rule() throws RecognitionException { return TreeRewriter.this.topdown(); }
/*     */ 
/* 107 */   };
/*     */ 
/* 111 */   fptr bottomup_ftpr = new fptr() {
/* 112 */     public Object rule() throws RecognitionException { return TreeRewriter.this.bottomup(); }
/*     */ 
/* 111 */   };
/*     */ 
/*     */   public TreeRewriter(TreeNodeStream input)
/*     */   {
/*  45 */     this(input, new RecognizerSharedState());
/*     */   }
/*     */   public TreeRewriter(TreeNodeStream input, RecognizerSharedState state) {
/*  48 */     super(input, state);
/*  49 */     this.originalAdaptor = input.getTreeAdaptor();
/*  50 */     this.originalTokenStream = input.getTokenStream();
/*     */   }
/*     */ 
/*     */   public Object applyOnce(Object t, fptr whichRule) {
/*  54 */     if (t == null) return null;
/*     */     try
/*     */     {
/*  57 */       this.state = new RecognizerSharedState();
/*  58 */       this.input = new CommonTreeNodeStream(this.originalAdaptor, t);
/*  59 */       ((CommonTreeNodeStream)this.input).setTokenStream(this.originalTokenStream);
/*  60 */       setBacktrackingLevel(1);
/*  61 */       TreeRuleReturnScope r = (TreeRuleReturnScope)whichRule.rule();
/*  62 */       setBacktrackingLevel(0);
/*  63 */       if (failed()) return t;
/*  64 */       if ((this.showTransformations) && (r != null) && (!t.equals(r.getTree())) && (r.getTree() != null))
/*     */       {
/*  67 */         reportTransformation(t, r.getTree());
/*     */       }
/*  69 */       if ((r != null) && (r.getTree() != null)) return r.getTree();
/*  70 */       return t;
/*     */     } catch (RecognitionException e) {
/*     */     }
/*  73 */     return t;
/*     */   }
/*     */ 
/*     */   public Object applyRepeatedly(Object t, fptr whichRule) {
/*  77 */     boolean treeChanged = true;
/*  78 */     while (treeChanged) {
/*  79 */       Object u = applyOnce(t, whichRule);
/*  80 */       treeChanged = !t.equals(u);
/*  81 */       t = u;
/*     */     }
/*  83 */     return t;
/*     */   }
/*     */   public Object downup(Object t) {
/*  86 */     return downup(t, false);
/*     */   }
/*     */   public Object downup(Object t, boolean showTransformations) {
/*  89 */     this.showTransformations = showTransformations;
/*  90 */     TreeVisitor v = new TreeVisitor(new CommonTreeAdaptor());
/*  91 */     TreeVisitorAction actions = new TreeVisitorAction() {
/*  92 */       public Object pre(Object t) { return TreeRewriter.this.applyOnce(t, TreeRewriter.this.topdown_fptr); } 
/*  93 */       public Object post(Object t) { return TreeRewriter.this.applyRepeatedly(t, TreeRewriter.this.bottomup_ftpr); }
/*     */ 
/*     */     };
/*  95 */     t = v.visit(t, actions);
/*  96 */     return t;
/*     */   }
/*     */ 
/*     */   public void reportTransformation(Object oldTree, Object newTree)
/*     */   {
/* 103 */     System.out.println(((Tree)oldTree).toStringTree() + " -> " + ((Tree)newTree).toStringTree());
/*     */   }
/*     */ 
/*     */   public Object topdown()
/*     */     throws RecognitionException
/*     */   {
/* 118 */     return null; } 
/* 119 */   public Object bottomup() throws RecognitionException { return null; }
/*     */ 
/*     */ 
/*     */   public static abstract interface fptr
/*     */   {
/*     */     public abstract Object rule()
/*     */       throws RecognitionException;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.tree.TreeRewriter
 * JD-Core Version:    0.6.2
 */