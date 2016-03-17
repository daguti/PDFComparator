/*     */ package org.antlr.misc;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ 
/*     */ public class OrderedHashSet<T> extends LinkedHashSet
/*     */ {
/*  42 */   protected List<T> elements = new ArrayList();
/*     */ 
/*     */   public T get(int i) {
/*  45 */     return this.elements.get(i);
/*     */   }
/*     */ 
/*     */   public T set(int i, T value)
/*     */   {
/*  52 */     Object oldElement = this.elements.get(i);
/*  53 */     this.elements.set(i, value);
/*  54 */     super.remove(oldElement);
/*  55 */     super.add(value);
/*  56 */     return oldElement;
/*     */   }
/*     */ 
/*     */   public boolean add(Object value)
/*     */   {
/*  64 */     boolean result = super.add(value);
/*  65 */     if (result) {
/*  66 */       this.elements.add(value);
/*     */     }
/*  68 */     return result;
/*     */   }
/*     */ 
/*     */   public boolean remove(Object o) {
/*  72 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/*  80 */     this.elements.clear();
/*  81 */     super.clear();
/*     */   }
/*     */ 
/*     */   public List<T> elements()
/*     */   {
/*  88 */     return this.elements;
/*     */   }
/*     */ 
/*     */   public Iterator<T> iterator() {
/*  92 */     return this.elements.iterator();
/*     */   }
/*     */ 
/*     */   public Object[] toArray() {
/*  96 */     return this.elements.toArray();
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 106 */     return this.elements.size();
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 110 */     return this.elements.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.misc.OrderedHashSet
 * JD-Core Version:    0.6.2
 */