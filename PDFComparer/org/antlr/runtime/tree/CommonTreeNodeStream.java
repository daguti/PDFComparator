/*     */ package org.antlr.runtime.tree;
/*     */ 
/*     */ import org.antlr.runtime.TokenStream;
/*     */ import org.antlr.runtime.misc.IntArray;
/*     */ import org.antlr.runtime.misc.LookaheadStream;
/*     */ 
/*     */ public class CommonTreeNodeStream extends LookaheadStream<Object>
/*     */   implements TreeNodeStream
/*     */ {
/*     */   public static final int DEFAULT_INITIAL_BUFFER_SIZE = 100;
/*     */   public static final int INITIAL_CALL_STACK_SIZE = 10;
/*     */   protected Object root;
/*     */   protected TokenStream tokens;
/*     */   TreeAdaptor adaptor;
/*     */   protected TreeIterator it;
/*     */   protected IntArray calls;
/*  57 */   protected boolean hasNilRoot = false;
/*     */ 
/*  60 */   protected int level = 0;
/*     */ 
/*     */   public CommonTreeNodeStream(Object tree) {
/*  63 */     this(new CommonTreeAdaptor(), tree);
/*     */   }
/*     */ 
/*     */   public CommonTreeNodeStream(TreeAdaptor adaptor, Object tree) {
/*  67 */     this.root = tree;
/*  68 */     this.adaptor = adaptor;
/*  69 */     this.it = new TreeIterator(adaptor, this.root);
/*     */   }
/*     */ 
/*     */   public void reset() {
/*  73 */     super.reset();
/*  74 */     this.it.reset();
/*  75 */     this.hasNilRoot = false;
/*  76 */     this.level = 0;
/*  77 */     if (this.calls != null) this.calls.clear();
/*     */   }
/*     */ 
/*     */   public Object nextElement()
/*     */   {
/*  84 */     Object t = this.it.next();
/*     */ 
/*  86 */     if (t == this.it.up) {
/*  87 */       this.level -= 1;
/*  88 */       if ((this.level == 0) && (this.hasNilRoot)) return this.it.next();
/*     */     }
/*  90 */     else if (t == this.it.down) { this.level += 1; }
/*  91 */     if ((this.level == 0) && (this.adaptor.isNil(t))) {
/*  92 */       this.hasNilRoot = true;
/*  93 */       t = this.it.next();
/*  94 */       this.level += 1;
/*  95 */       t = this.it.next();
/*     */     }
/*  97 */     return t;
/*     */   }
/*     */   public boolean isEOF(Object o) {
/* 100 */     return this.adaptor.getType(o) == -1;
/*     */   }
/*     */   public void setUniqueNavigationNodes(boolean uniqueNavigationNodes) {
/*     */   }
/* 104 */   public Object getTreeSource() { return this.root; } 
/*     */   public String getSourceName() {
/* 106 */     return getTokenStream().getSourceName();
/*     */   }
/* 108 */   public TokenStream getTokenStream() { return this.tokens; } 
/*     */   public void setTokenStream(TokenStream tokens) {
/* 110 */     this.tokens = tokens;
/*     */   }
/* 112 */   public TreeAdaptor getTreeAdaptor() { return this.adaptor; } 
/*     */   public void setTreeAdaptor(TreeAdaptor adaptor) {
/* 114 */     this.adaptor = adaptor;
/*     */   }
/*     */   public Object get(int i) {
/* 117 */     throw new UnsupportedOperationException("Absolute node indexes are meaningless in an unbuffered stream");
/*     */   }
/*     */   public int LA(int i) {
/* 120 */     return this.adaptor.getType(LT(i));
/*     */   }
/*     */ 
/*     */   public void push(int index)
/*     */   {
/* 126 */     if (this.calls == null) {
/* 127 */       this.calls = new IntArray();
/*     */     }
/* 129 */     this.calls.push(this.p);
/* 130 */     seek(index);
/*     */   }
/*     */ 
/*     */   public int pop()
/*     */   {
/* 137 */     int ret = this.calls.pop();
/* 138 */     seek(ret);
/* 139 */     return ret;
/*     */   }
/*     */ 
/*     */   public void replaceChildren(Object parent, int startChildIndex, int stopChildIndex, Object t)
/*     */   {
/* 145 */     if (parent != null)
/* 146 */       this.adaptor.replaceChildren(parent, startChildIndex, stopChildIndex, t);
/*     */   }
/*     */ 
/*     */   public String toString(Object start, Object stop)
/*     */   {
/* 153 */     return "n/a";
/*     */   }
/*     */ 
/*     */   public String toTokenTypeString()
/*     */   {
/* 158 */     reset();
/* 159 */     StringBuffer buf = new StringBuffer();
/* 160 */     Object o = LT(1);
/* 161 */     int type = this.adaptor.getType(o);
/* 162 */     while (type != -1) {
/* 163 */       buf.append(" ");
/* 164 */       buf.append(type);
/* 165 */       consume();
/* 166 */       o = LT(1);
/* 167 */       type = this.adaptor.getType(o);
/*     */     }
/* 169 */     return buf.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.tree.CommonTreeNodeStream
 * JD-Core Version:    0.6.2
 */