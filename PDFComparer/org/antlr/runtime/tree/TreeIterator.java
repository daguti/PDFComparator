/*     */ package org.antlr.runtime.tree;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import org.antlr.runtime.misc.FastQueue;
/*     */ 
/*     */ public class TreeIterator
/*     */   implements Iterator
/*     */ {
/*     */   protected TreeAdaptor adaptor;
/*     */   protected Object root;
/*     */   protected Object tree;
/*  45 */   protected boolean firstTime = true;
/*     */   public Object up;
/*     */   public Object down;
/*     */   public Object eof;
/*     */   protected FastQueue nodes;
/*     */ 
/*     */   public TreeIterator(Object tree)
/*     */   {
/*  58 */     this(new CommonTreeAdaptor(), tree);
/*     */   }
/*     */ 
/*     */   public TreeIterator(TreeAdaptor adaptor, Object tree) {
/*  62 */     this.adaptor = adaptor;
/*  63 */     this.tree = tree;
/*  64 */     this.root = tree;
/*  65 */     this.nodes = new FastQueue();
/*  66 */     this.down = adaptor.create(2, "DOWN");
/*  67 */     this.up = adaptor.create(3, "UP");
/*  68 */     this.eof = adaptor.create(-1, "EOF");
/*     */   }
/*     */ 
/*     */   public void reset() {
/*  72 */     this.firstTime = true;
/*  73 */     this.tree = this.root;
/*  74 */     this.nodes.clear();
/*     */   }
/*     */ 
/*     */   public boolean hasNext() {
/*  78 */     if (this.firstTime) return this.root != null;
/*  79 */     if ((this.nodes != null) && (this.nodes.size() > 0)) return true;
/*  80 */     if (this.tree == null) return false;
/*  81 */     if (this.adaptor.getChildCount(this.tree) > 0) return true;
/*  82 */     return this.adaptor.getParent(this.tree) != null;
/*     */   }
/*     */ 
/*     */   public Object next() {
/*  86 */     if (this.firstTime) {
/*  87 */       this.firstTime = false;
/*  88 */       if (this.adaptor.getChildCount(this.tree) == 0) {
/*  89 */         this.nodes.add(this.eof);
/*  90 */         return this.tree;
/*     */       }
/*  92 */       return this.tree;
/*     */     }
/*     */ 
/*  95 */     if ((this.nodes != null) && (this.nodes.size() > 0)) return this.nodes.remove();
/*     */ 
/*  98 */     if (this.tree == null) return this.eof;
/*     */ 
/* 101 */     if (this.adaptor.getChildCount(this.tree) > 0) {
/* 102 */       this.tree = this.adaptor.getChild(this.tree, 0);
/* 103 */       this.nodes.add(this.tree);
/* 104 */       return this.down;
/*     */     }
/*     */ 
/* 107 */     Object parent = this.adaptor.getParent(this.tree);
/*     */ 
/* 110 */     while ((parent != null) && (this.adaptor.getChildIndex(this.tree) + 1 >= this.adaptor.getChildCount(parent)))
/*     */     {
/* 112 */       this.nodes.add(this.up);
/* 113 */       this.tree = parent;
/* 114 */       parent = this.adaptor.getParent(this.tree);
/*     */     }
/*     */ 
/* 117 */     if (parent == null) {
/* 118 */       this.tree = null;
/* 119 */       this.nodes.add(this.eof);
/* 120 */       return this.nodes.remove();
/*     */     }
/*     */ 
/* 125 */     int nextSiblingIndex = this.adaptor.getChildIndex(this.tree) + 1;
/* 126 */     this.tree = this.adaptor.getChild(parent, nextSiblingIndex);
/* 127 */     this.nodes.add(this.tree);
/* 128 */     return this.nodes.remove();
/*     */   }
/*     */   public void remove() {
/* 131 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.tree.TreeIterator
 * JD-Core Version:    0.6.2
 */