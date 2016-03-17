/*    */ package antlr.collections.impl;
/*    */ 
/*    */ import java.util.Enumeration;
/*    */ import java.util.Hashtable;
/*    */ 
/*    */ public class IndexedVector
/*    */ {
/*    */   protected Vector elements;
/*    */   protected Hashtable index;
/*    */ 
/*    */   public IndexedVector()
/*    */   {
/* 29 */     this.elements = new Vector(10);
/* 30 */     this.index = new Hashtable(10);
/*    */   }
/*    */ 
/*    */   public IndexedVector(int paramInt)
/*    */   {
/* 38 */     this.elements = new Vector(paramInt);
/* 39 */     this.index = new Hashtable(paramInt);
/*    */   }
/*    */ 
/*    */   public synchronized void appendElement(Object paramObject1, Object paramObject2) {
/* 43 */     this.elements.appendElement(paramObject2);
/* 44 */     this.index.put(paramObject1, paramObject2);
/*    */   }
/*    */ 
/*    */   public Object elementAt(int paramInt)
/*    */   {
/* 54 */     return this.elements.elementAt(paramInt);
/*    */   }
/*    */ 
/*    */   public Enumeration elements() {
/* 58 */     return this.elements.elements();
/*    */   }
/*    */ 
/*    */   public Object getElement(Object paramObject) {
/* 62 */     Object localObject = this.index.get(paramObject);
/* 63 */     return localObject;
/*    */   }
/*    */ 
/*    */   public synchronized boolean removeElement(Object paramObject)
/*    */   {
/* 68 */     Object localObject = this.index.get(paramObject);
/* 69 */     if (localObject == null) {
/* 70 */       return false;
/*    */     }
/* 72 */     this.index.remove(paramObject);
/* 73 */     this.elements.removeElement(localObject);
/* 74 */     return false;
/*    */   }
/*    */ 
/*    */   public int size() {
/* 78 */     return this.elements.size();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.collections.impl.IndexedVector
 * JD-Core Version:    0.6.2
 */