/*     */ package org.antlr.runtime.tree;
/*     */ 
/*     */ import org.antlr.runtime.CommonToken;
/*     */ import org.antlr.runtime.Token;
/*     */ 
/*     */ public class TreePatternParser
/*     */ {
/*     */   protected TreePatternLexer tokenizer;
/*     */   protected int ttype;
/*     */   protected TreeWizard wizard;
/*     */   protected TreeAdaptor adaptor;
/*     */ 
/*     */   public TreePatternParser(TreePatternLexer tokenizer, TreeWizard wizard, TreeAdaptor adaptor)
/*     */   {
/*  40 */     this.tokenizer = tokenizer;
/*  41 */     this.wizard = wizard;
/*  42 */     this.adaptor = adaptor;
/*  43 */     this.ttype = tokenizer.nextToken();
/*     */   }
/*     */ 
/*     */   public Object pattern() {
/*  47 */     if (this.ttype == 1) {
/*  48 */       return parseTree();
/*     */     }
/*  50 */     if (this.ttype == 3) {
/*  51 */       Object node = parseNode();
/*  52 */       if (this.ttype == -1) {
/*  53 */         return node;
/*     */       }
/*  55 */       return null;
/*     */     }
/*  57 */     return null;
/*     */   }
/*     */ 
/*     */   public Object parseTree() {
/*  61 */     if (this.ttype != 1) {
/*  62 */       throw new RuntimeException("no BEGIN");
/*     */     }
/*  64 */     this.ttype = this.tokenizer.nextToken();
/*  65 */     Object root = parseNode();
/*  66 */     if (root == null) {
/*  67 */       return null;
/*     */     }
/*     */ 
/*  72 */     while ((this.ttype == 1) || (this.ttype == 3) || (this.ttype == 5) || (this.ttype == 7))
/*     */     {
/*  74 */       if (this.ttype == 1) {
/*  75 */         Object subtree = parseTree();
/*  76 */         this.adaptor.addChild(root, subtree);
/*     */       }
/*     */       else {
/*  79 */         Object child = parseNode();
/*  80 */         if (child == null) {
/*  81 */           return null;
/*     */         }
/*  83 */         this.adaptor.addChild(root, child);
/*     */       }
/*     */     }
/*  86 */     if (this.ttype != 2) {
/*  87 */       throw new RuntimeException("no END");
/*     */     }
/*  89 */     this.ttype = this.tokenizer.nextToken();
/*  90 */     return root;
/*     */   }
/*     */ 
/*     */   public Object parseNode()
/*     */   {
/*  95 */     String label = null;
/*  96 */     if (this.ttype == 5) {
/*  97 */       this.ttype = this.tokenizer.nextToken();
/*  98 */       if (this.ttype != 3) {
/*  99 */         return null;
/*     */       }
/* 101 */       label = this.tokenizer.sval.toString();
/* 102 */       this.ttype = this.tokenizer.nextToken();
/* 103 */       if (this.ttype != 6) {
/* 104 */         return null;
/*     */       }
/* 106 */       this.ttype = this.tokenizer.nextToken();
/*     */     }
/*     */ 
/* 110 */     if (this.ttype == 7) {
/* 111 */       this.ttype = this.tokenizer.nextToken();
/* 112 */       Token wildcardPayload = new CommonToken(0, ".");
/* 113 */       TreeWizard.TreePattern node = new TreeWizard.WildcardTreePattern(wildcardPayload);
/*     */ 
/* 115 */       if (label != null) {
/* 116 */         node.label = label;
/*     */       }
/* 118 */       return node;
/*     */     }
/*     */ 
/* 122 */     if (this.ttype != 3) {
/* 123 */       return null;
/*     */     }
/* 125 */     String tokenName = this.tokenizer.sval.toString();
/* 126 */     this.ttype = this.tokenizer.nextToken();
/* 127 */     if (tokenName.equals("nil")) {
/* 128 */       return this.adaptor.nil();
/*     */     }
/* 130 */     String text = tokenName;
/*     */ 
/* 132 */     String arg = null;
/* 133 */     if (this.ttype == 4) {
/* 134 */       arg = this.tokenizer.sval.toString();
/* 135 */       text = arg;
/* 136 */       this.ttype = this.tokenizer.nextToken();
/*     */     }
/*     */ 
/* 140 */     int treeNodeType = this.wizard.getTokenType(tokenName);
/* 141 */     if (treeNodeType == 0) {
/* 142 */       return null;
/*     */     }
/*     */ 
/* 145 */     Object node = this.adaptor.create(treeNodeType, text);
/* 146 */     if ((label != null) && (node.getClass() == TreeWizard.TreePattern.class)) {
/* 147 */       ((TreeWizard.TreePattern)node).label = label;
/*     */     }
/* 149 */     if ((arg != null) && (node.getClass() == TreeWizard.TreePattern.class)) {
/* 150 */       ((TreeWizard.TreePattern)node).hasTextArg = true;
/*     */     }
/* 152 */     return node;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.tree.TreePatternParser
 * JD-Core Version:    0.6.2
 */