/*     */ package org.antlr.runtime.tree;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public abstract class RewriteRuleElementStream
/*     */ {
/*  50 */   protected int cursor = 0;
/*     */   protected Object singleElement;
/*     */   protected List elements;
/*  68 */   protected boolean dirty = false;
/*     */   protected String elementDescription;
/*     */   protected TreeAdaptor adaptor;
/*     */ 
/*     */   public RewriteRuleElementStream(TreeAdaptor adaptor, String elementDescription)
/*     */   {
/*  78 */     this.elementDescription = elementDescription;
/*  79 */     this.adaptor = adaptor;
/*     */   }
/*     */ 
/*     */   public RewriteRuleElementStream(TreeAdaptor adaptor, String elementDescription, Object oneElement)
/*     */   {
/*  87 */     this(adaptor, elementDescription);
/*  88 */     add(oneElement);
/*     */   }
/*     */ 
/*     */   public RewriteRuleElementStream(TreeAdaptor adaptor, String elementDescription, List elements)
/*     */   {
/*  96 */     this(adaptor, elementDescription);
/*  97 */     this.singleElement = null;
/*  98 */     this.elements = elements;
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/* 107 */     this.cursor = 0;
/* 108 */     this.dirty = true;
/*     */   }
/*     */ 
/*     */   public void add(Object el)
/*     */   {
/* 113 */     if (el == null) {
/* 114 */       return;
/*     */     }
/* 116 */     if (this.elements != null) {
/* 117 */       this.elements.add(el);
/* 118 */       return;
/*     */     }
/* 120 */     if (this.singleElement == null) {
/* 121 */       this.singleElement = el;
/* 122 */       return;
/*     */     }
/*     */ 
/* 125 */     this.elements = new ArrayList(5);
/* 126 */     this.elements.add(this.singleElement);
/* 127 */     this.singleElement = null;
/* 128 */     this.elements.add(el);
/*     */   }
/*     */ 
/*     */   public Object nextTree()
/*     */   {
/* 137 */     int n = size();
/* 138 */     if ((this.dirty) || ((this.cursor >= n) && (n == 1)))
/*     */     {
/* 140 */       Object el = _next();
/* 141 */       return dup(el);
/*     */     }
/*     */ 
/* 144 */     Object el = _next();
/* 145 */     return el;
/*     */   }
/*     */ 
/*     */   protected Object _next()
/*     */   {
/* 155 */     int n = size();
/* 156 */     if (n == 0) {
/* 157 */       throw new RewriteEmptyStreamException(this.elementDescription);
/*     */     }
/* 159 */     if (this.cursor >= n) {
/* 160 */       if (n == 1) {
/* 161 */         return toTree(this.singleElement);
/*     */       }
/*     */ 
/* 164 */       throw new RewriteCardinalityException(this.elementDescription);
/*     */     }
/*     */ 
/* 167 */     if (this.singleElement != null) {
/* 168 */       this.cursor += 1;
/* 169 */       return toTree(this.singleElement);
/*     */     }
/*     */ 
/* 172 */     Object o = toTree(this.elements.get(this.cursor));
/* 173 */     this.cursor += 1;
/* 174 */     return o;
/*     */   }
/*     */ 
/*     */   protected abstract Object dup(Object paramObject);
/*     */ 
/*     */   protected Object toTree(Object el)
/*     */   {
/* 188 */     return el;
/*     */   }
/*     */ 
/*     */   public boolean hasNext() {
/* 192 */     return ((this.singleElement != null) && (this.cursor < 1)) || ((this.elements != null) && (this.cursor < this.elements.size()));
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 197 */     int n = 0;
/* 198 */     if (this.singleElement != null) {
/* 199 */       n = 1;
/*     */     }
/* 201 */     if (this.elements != null) {
/* 202 */       return this.elements.size();
/*     */     }
/* 204 */     return n;
/*     */   }
/*     */ 
/*     */   public String getDescription() {
/* 208 */     return this.elementDescription;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.tree.RewriteRuleElementStream
 * JD-Core Version:    0.6.2
 */