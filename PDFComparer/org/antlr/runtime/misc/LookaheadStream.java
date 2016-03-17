/*     */ package org.antlr.runtime.misc;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ public abstract class LookaheadStream<T> extends FastQueue<T>
/*     */ {
/*     */   public static final int UNINITIALIZED_EOF_ELEMENT_INDEX = 2147483647;
/*  45 */   protected int currentElementIndex = 0;
/*     */   protected T prevElement;
/*  52 */   public T eof = null;
/*     */   protected int lastMarker;
/*  58 */   protected int markDepth = 0;
/*     */ 
/*     */   public void reset() {
/*  61 */     super.reset();
/*  62 */     this.currentElementIndex = 0;
/*  63 */     this.p = 0;
/*  64 */     this.prevElement = null;
/*     */   }
/*     */ 
/*     */   public abstract T nextElement();
/*     */ 
/*     */   public abstract boolean isEOF(T paramT);
/*     */ 
/*     */   public T remove()
/*     */   {
/*  78 */     Object o = elementAt(0);
/*  79 */     this.p += 1;
/*     */ 
/*  81 */     if ((this.p == this.data.size()) && (this.markDepth == 0))
/*     */     {
/*  83 */       clear();
/*     */     }
/*  85 */     return o;
/*     */   }
/*     */ 
/*     */   public void consume()
/*     */   {
/*  90 */     syncAhead(1);
/*  91 */     this.prevElement = remove();
/*  92 */     this.currentElementIndex += 1;
/*     */   }
/*     */ 
/*     */   protected void syncAhead(int need)
/*     */   {
/* 100 */     int n = this.p + need - 1 - this.data.size() + 1;
/* 101 */     if (n > 0) fill(n);
/*     */   }
/*     */ 
/*     */   public void fill(int n)
/*     */   {
/* 106 */     for (int i = 1; i <= n; i++) {
/* 107 */       Object o = nextElement();
/* 108 */       if (isEOF(o)) this.eof = o;
/* 109 */       this.data.add(o);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int size() {
/* 114 */     throw new UnsupportedOperationException("streams are of unknown size");
/*     */   }
/*     */   public T LT(int k) {
/* 117 */     if (k == 0) {
/* 118 */       return null;
/*     */     }
/* 120 */     if (k < 0) return LB(-k);
/*     */ 
/* 122 */     syncAhead(k);
/* 123 */     if (this.p + k - 1 > this.data.size()) return this.eof;
/* 124 */     return elementAt(k - 1);
/*     */   }
/*     */   public int index() {
/* 127 */     return this.currentElementIndex;
/*     */   }
/*     */   public int mark() {
/* 130 */     this.markDepth += 1;
/* 131 */     this.lastMarker = this.p;
/* 132 */     return this.lastMarker;
/*     */   }
/*     */ 
/*     */   public void release(int marker)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void rewind(int marker) {
/* 140 */     this.markDepth -= 1;
/* 141 */     seek(marker);
/*     */   }
/*     */ 
/*     */   public void rewind()
/*     */   {
/* 146 */     seek(this.lastMarker);
/*     */   }
/*     */ 
/*     */   public void seek(int index)
/*     */   {
/* 155 */     this.p = index;
/*     */   }
/*     */   protected T LB(int k) {
/* 158 */     if (k == 1) return this.prevElement;
/* 159 */     throw new NoSuchElementException("can't look backwards more than one token in this stream");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.misc.LookaheadStream
 * JD-Core Version:    0.6.2
 */