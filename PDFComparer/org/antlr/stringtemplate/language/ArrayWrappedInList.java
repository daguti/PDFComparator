/*    */ package org.antlr.stringtemplate.language;
/*    */ 
/*    */ import java.lang.reflect.Array;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class ArrayWrappedInList extends ArrayList
/*    */ {
/* 12 */   protected Object array = null;
/*    */   protected int n;
/*    */ 
/*    */   public ArrayWrappedInList(Object array)
/*    */   {
/* 17 */     this.array = array;
/* 18 */     this.n = Array.getLength(array);
/*    */   }
/*    */ 
/*    */   public Object get(int i) {
/* 22 */     return Array.get(this.array, i);
/*    */   }
/*    */ 
/*    */   public int size() {
/* 26 */     return this.n;
/*    */   }
/*    */ 
/*    */   public boolean isEmpty() {
/* 30 */     return this.n == 0;
/*    */   }
/*    */ 
/*    */   public Object[] toArray() {
/* 34 */     return (Object[])this.array;
/*    */   }
/*    */ 
/*    */   public Iterator iterator() {
/* 38 */     return new ArrayIterator(this.array);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.language.ArrayWrappedInList
 * JD-Core Version:    0.6.2
 */