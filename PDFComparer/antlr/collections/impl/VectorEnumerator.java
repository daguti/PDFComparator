/*    */ package antlr.collections.impl;
/*    */ 
/*    */ import java.util.Enumeration;
/*    */ import java.util.NoSuchElementException;
/*    */ 
/*    */ class VectorEnumerator
/*    */   implements Enumeration
/*    */ {
/*    */   Vector vector;
/*    */   int i;
/*    */ 
/*    */   VectorEnumerator(Vector paramVector)
/*    */   {
/* 23 */     this.vector = paramVector;
/* 24 */     this.i = 0;
/*    */   }
/*    */ 
/*    */   public boolean hasMoreElements() {
/* 28 */     synchronized (this.vector) {
/* 29 */       return this.i <= this.vector.lastElement;
/*    */     }
/*    */   }
/*    */ 
/*    */   public Object nextElement() {
/* 34 */     synchronized (this.vector) {
/* 35 */       if (this.i <= this.vector.lastElement) {
/* 36 */         return this.vector.data[(this.i++)];
/*    */       }
/* 38 */       throw new NoSuchElementException("VectorEnumerator");
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.collections.impl.VectorEnumerator
 * JD-Core Version:    0.6.2
 */