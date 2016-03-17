/*    */ package org.apache.xmpbox.type;
/*    */ 
/*    */ public enum Cardinality
/*    */ {
/* 27 */   Simple(false), Bag(true), Seq(true), Alt(true);
/*    */ 
/*    */   private boolean array;
/*    */ 
/*    */   private Cardinality(boolean a)
/*    */   {
/* 33 */     this.array = a;
/*    */   }
/*    */ 
/*    */   public boolean isArray()
/*    */   {
/* 38 */     return this.array;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.type.Cardinality
 * JD-Core Version:    0.6.2
 */