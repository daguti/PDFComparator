/*    */ package org.antlr.stringtemplate.language;
/*    */ 
/*    */ import java.lang.reflect.Array;
/*    */ import java.util.Iterator;
/*    */ import java.util.NoSuchElementException;
/*    */ 
/*    */ public class ArrayIterator
/*    */   implements Iterator
/*    */ {
/* 12 */   protected int i = -1;
/* 13 */   protected Object array = null;
/*    */   protected int n;
/*    */ 
/*    */   public ArrayIterator(Object array)
/*    */   {
/* 18 */     this.array = array;
/* 19 */     this.n = Array.getLength(array);
/*    */   }
/*    */ 
/*    */   public boolean hasNext() {
/* 23 */     return (this.i + 1 < this.n) && (this.n > 0);
/*    */   }
/*    */ 
/*    */   public Object next() {
/* 27 */     this.i += 1;
/* 28 */     if (this.i >= this.n) {
/* 29 */       throw new NoSuchElementException();
/*    */     }
/* 31 */     return Array.get(this.array, this.i);
/*    */   }
/*    */ 
/*    */   public void remove() {
/* 35 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.language.ArrayIterator
 * JD-Core Version:    0.6.2
 */