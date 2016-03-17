/*    */ package org.stringtemplate.v4.misc;
/*    */ 
/*    */ import java.lang.reflect.Array;
/*    */ import java.util.Iterator;
/*    */ import java.util.NoSuchElementException;
/*    */ 
/*    */ public class ArrayIterator
/*    */   implements Iterator
/*    */ {
/* 39 */   protected int i = -1;
/* 40 */   protected Object array = null;
/*    */   protected int n;
/*    */ 
/*    */   public ArrayIterator(Object array)
/*    */   {
/* 45 */     this.array = array;
/* 46 */     this.n = Array.getLength(array);
/*    */   }
/*    */ 
/*    */   public boolean hasNext() {
/* 50 */     return (this.i + 1 < this.n) && (this.n > 0);
/*    */   }
/*    */ 
/*    */   public Object next() {
/* 54 */     this.i += 1;
/* 55 */     if (this.i >= this.n) {
/* 56 */       throw new NoSuchElementException();
/*    */     }
/* 58 */     return Array.get(this.array, this.i);
/*    */   }
/*    */ 
/*    */   public void remove() {
/* 62 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.misc.ArrayIterator
 * JD-Core Version:    0.6.2
 */