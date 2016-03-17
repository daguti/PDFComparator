/*     */ package org.antlr.misc;
/*     */ 
/*     */ import java.util.AbstractList;
/*     */ 
/*     */ public class IntArrayList extends AbstractList
/*     */   implements Cloneable
/*     */ {
/*     */   private static final int DEFAULT_CAPACITY = 10;
/*  38 */   protected int n = 0;
/*  39 */   protected int[] elements = null;
/*     */ 
/*     */   public IntArrayList() {
/*  42 */     this(10);
/*     */   }
/*     */ 
/*     */   public IntArrayList(int initialCapacity) {
/*  46 */     this.elements = new int[initialCapacity];
/*     */   }
/*     */ 
/*     */   public int set(int i, int newValue)
/*     */   {
/*  51 */     if (i >= this.n) {
/*  52 */       setSize(i);
/*     */     }
/*  54 */     int v = this.elements[i];
/*  55 */     this.elements[i] = newValue;
/*  56 */     return v;
/*     */   }
/*     */ 
/*     */   public boolean add(int o) {
/*  60 */     if (this.n >= this.elements.length) {
/*  61 */       grow();
/*     */     }
/*  63 */     this.elements[this.n] = o;
/*  64 */     this.n += 1;
/*  65 */     return true;
/*     */   }
/*     */ 
/*     */   public void setSize(int newSize) {
/*  69 */     if (newSize >= this.elements.length) {
/*  70 */       ensureCapacity(newSize);
/*     */     }
/*  72 */     this.n = newSize;
/*     */   }
/*     */ 
/*     */   protected void grow() {
/*  76 */     ensureCapacity(this.elements.length * 3 / 2 + 1);
/*     */   }
/*     */ 
/*     */   public boolean contains(int v) {
/*  80 */     for (int i = 0; i < this.n; i++) {
/*  81 */       int element = this.elements[i];
/*  82 */       if (element == v) {
/*  83 */         return true;
/*     */       }
/*     */     }
/*  86 */     return false;
/*     */   }
/*     */ 
/*     */   public void ensureCapacity(int newCapacity) {
/*  90 */     int oldCapacity = this.elements.length;
/*  91 */     if (this.n >= oldCapacity) {
/*  92 */       int[] oldData = this.elements;
/*  93 */       this.elements = new int[newCapacity];
/*  94 */       System.arraycopy(oldData, 0, this.elements, 0, this.n);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object get(int i) {
/*  99 */     return Utils.integer(element(i));
/*     */   }
/*     */ 
/*     */   public int element(int i) {
/* 103 */     return this.elements[i];
/*     */   }
/*     */ 
/*     */   public int[] elements() {
/* 107 */     int[] a = new int[this.n];
/* 108 */     System.arraycopy(this.elements, 0, a, 0, this.n);
/* 109 */     return a;
/*     */   }
/*     */ 
/*     */   public int size() {
/* 113 */     return this.n;
/*     */   }
/*     */ 
/*     */   public int capacity() {
/* 117 */     return this.elements.length;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object o) {
/* 121 */     if (o == null) {
/* 122 */       return false;
/*     */     }
/* 124 */     IntArrayList other = (IntArrayList)o;
/* 125 */     if (size() != other.size()) {
/* 126 */       return false;
/*     */     }
/* 128 */     for (int i = 0; i < this.n; i++) {
/* 129 */       if (this.elements[i] != other.elements[i]) {
/* 130 */         return false;
/*     */       }
/*     */     }
/* 133 */     return true;
/*     */   }
/*     */ 
/*     */   public Object clone() throws CloneNotSupportedException {
/* 137 */     IntArrayList a = (IntArrayList)super.clone();
/* 138 */     a.n = this.n;
/* 139 */     System.arraycopy(this.elements, 0, a.elements, 0, this.elements.length);
/* 140 */     return a;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 144 */     StringBuffer buf = new StringBuffer();
/* 145 */     for (int i = 0; i < this.n; i++) {
/* 146 */       if (i > 0) {
/* 147 */         buf.append(", ");
/*     */       }
/* 149 */       buf.append(this.elements[i]);
/*     */     }
/* 151 */     return buf.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.misc.IntArrayList
 * JD-Core Version:    0.6.2
 */