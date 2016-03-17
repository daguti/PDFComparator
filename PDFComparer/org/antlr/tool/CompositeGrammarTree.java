/*     */ package org.antlr.tool;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class CompositeGrammarTree
/*     */ {
/*     */   protected List<CompositeGrammarTree> children;
/*     */   public Grammar grammar;
/*     */   public CompositeGrammarTree parent;
/*     */ 
/*     */   public CompositeGrammarTree(Grammar g)
/*     */   {
/*  42 */     this.grammar = g;
/*     */   }
/*     */ 
/*     */   public void addChild(CompositeGrammarTree t)
/*     */   {
/*  47 */     if (t == null) {
/*  48 */       return;
/*     */     }
/*  50 */     if (this.children == null) {
/*  51 */       this.children = new ArrayList();
/*     */     }
/*  53 */     this.children.add(t);
/*  54 */     t.parent = this;
/*     */   }
/*     */ 
/*     */   public Rule getRule(String ruleName)
/*     */   {
/*  61 */     Rule r = this.grammar.getLocallyDefinedRule(ruleName);
/*  62 */     for (int i = 0; (r == null) && (this.children != null) && (i < this.children.size()); i++) {
/*  63 */       CompositeGrammarTree child = (CompositeGrammarTree)this.children.get(i);
/*  64 */       r = child.getRule(ruleName);
/*     */     }
/*  66 */     return r;
/*     */   }
/*     */ 
/*     */   public Object getOption(String key)
/*     */   {
/*  71 */     Object o = this.grammar.getLocallyDefinedOption(key);
/*  72 */     if (o != null) {
/*  73 */       return o;
/*     */     }
/*  75 */     if (this.parent != null) {
/*  76 */       return this.parent.getOption(key);
/*     */     }
/*  78 */     return null;
/*     */   }
/*     */ 
/*     */   public CompositeGrammarTree findNode(Grammar g) {
/*  82 */     if (g == null) {
/*  83 */       return null;
/*     */     }
/*  85 */     if (this.grammar == g) {
/*  86 */       return this;
/*     */     }
/*  88 */     CompositeGrammarTree n = null;
/*  89 */     for (int i = 0; (n == null) && (this.children != null) && (i < this.children.size()); i++) {
/*  90 */       CompositeGrammarTree child = (CompositeGrammarTree)this.children.get(i);
/*  91 */       n = child.findNode(g);
/*     */     }
/*  93 */     return n;
/*     */   }
/*     */ 
/*     */   public CompositeGrammarTree findNode(String grammarName) {
/*  97 */     if (grammarName == null) {
/*  98 */       return null;
/*     */     }
/* 100 */     if (grammarName.equals(this.grammar.name)) {
/* 101 */       return this;
/*     */     }
/* 103 */     CompositeGrammarTree n = null;
/* 104 */     for (int i = 0; (n == null) && (this.children != null) && (i < this.children.size()); i++) {
/* 105 */       CompositeGrammarTree child = (CompositeGrammarTree)this.children.get(i);
/* 106 */       n = child.findNode(grammarName);
/*     */     }
/* 108 */     return n;
/*     */   }
/*     */ 
/*     */   public List<Grammar> getPostOrderedGrammarList()
/*     */   {
/* 113 */     List grammars = new ArrayList();
/* 114 */     _getPostOrderedGrammarList(grammars);
/* 115 */     return grammars;
/*     */   }
/*     */ 
/*     */   protected void _getPostOrderedGrammarList(List<Grammar> grammars)
/*     */   {
/* 120 */     for (int i = 0; (this.children != null) && (i < this.children.size()); i++) {
/* 121 */       CompositeGrammarTree child = (CompositeGrammarTree)this.children.get(i);
/* 122 */       child._getPostOrderedGrammarList(grammars);
/*     */     }
/* 124 */     grammars.add(this.grammar);
/*     */   }
/*     */ 
/*     */   public List<Grammar> getPreOrderedGrammarList()
/*     */   {
/* 129 */     List grammars = new ArrayList();
/* 130 */     _getPreOrderedGrammarList(grammars);
/* 131 */     return grammars;
/*     */   }
/*     */ 
/*     */   protected void _getPreOrderedGrammarList(List<Grammar> grammars) {
/* 135 */     grammars.add(this.grammar);
/* 136 */     for (int i = 0; (this.children != null) && (i < this.children.size()); i++) {
/* 137 */       CompositeGrammarTree child = (CompositeGrammarTree)this.children.get(i);
/* 138 */       child._getPreOrderedGrammarList(grammars);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void trimLexerImportsIntoCombined() {
/* 143 */     CompositeGrammarTree p = this;
/* 144 */     if ((p.grammar.type == 1) && (p.parent != null) && (p.parent.grammar.type == 4))
/*     */     {
/* 148 */       p.parent.children.remove(this);
/*     */     }
/* 150 */     for (int i = 0; (this.children != null) && (i < this.children.size()); i++) {
/* 151 */       CompositeGrammarTree child = (CompositeGrammarTree)this.children.get(i);
/* 152 */       child.trimLexerImportsIntoCombined();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.CompositeGrammarTree
 * JD-Core Version:    0.6.2
 */