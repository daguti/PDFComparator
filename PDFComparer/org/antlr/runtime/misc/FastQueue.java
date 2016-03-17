/*    */ package org.antlr.runtime.misc;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.NoSuchElementException;
/*    */ 
/*    */ public class FastQueue<T>
/*    */ {
/* 46 */   protected List<T> data = new ArrayList();
/*    */ 
/* 48 */   protected int p = 0;
/* 49 */   protected int range = -1;
/*    */ 
/* 51 */   public void reset() { clear(); } 
/* 52 */   public void clear() { this.p = 0; this.data.clear(); }
/*    */ 
/*    */   public T remove()
/*    */   {
/* 56 */     Object o = elementAt(0);
/* 57 */     this.p += 1;
/*    */ 
/* 59 */     if (this.p == this.data.size())
/*    */     {
/* 61 */       clear();
/*    */     }
/* 63 */     return o;
/*    */   }
/*    */   public void add(T o) {
/* 66 */     this.data.add(o);
/*    */   }
/* 68 */   public int size() { return this.data.size() - this.p; } 
/*    */   public int range() {
/* 70 */     return this.range;
/*    */   }
/* 72 */   public T head() { return elementAt(0); }
/*    */ 
/*    */ 
/*    */   public T elementAt(int i)
/*    */   {
/* 79 */     int absIndex = this.p + i;
/* 80 */     if (absIndex >= this.data.size()) {
/* 81 */       throw new NoSuchElementException("queue index " + absIndex + " > last index " + (this.data.size() - 1));
/*    */     }
/* 83 */     if (absIndex < 0) {
/* 84 */       throw new NoSuchElementException("queue index " + absIndex + " < 0");
/*    */     }
/* 86 */     if (absIndex > this.range) this.range = absIndex;
/* 87 */     return this.data.get(absIndex);
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 92 */     StringBuffer buf = new StringBuffer();
/* 93 */     int n = size();
/* 94 */     for (int i = 0; i < n; i++) {
/* 95 */       buf.append(elementAt(i));
/* 96 */       if (i + 1 < n) buf.append(" ");
/*    */     }
/* 98 */     return buf.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.misc.FastQueue
 * JD-Core Version:    0.6.2
 */