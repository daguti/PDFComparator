/*    */ package antlr.collections.impl;
/*    */ 
/*    */ import java.util.Enumeration;
/*    */ import java.util.NoSuchElementException;
/*    */ 
/*    */ final class LLEnumeration
/*    */   implements Enumeration
/*    */ {
/*    */   LLCell cursor;
/*    */   LList list;
/*    */ 
/*    */   public LLEnumeration(LList paramLList)
/*    */   {
/* 29 */     this.list = paramLList;
/* 30 */     this.cursor = this.list.head;
/*    */   }
/*    */ 
/*    */   public boolean hasMoreElements()
/*    */   {
/* 37 */     if (this.cursor != null) {
/* 38 */       return true;
/*    */     }
/* 40 */     return false;
/*    */   }
/*    */ 
/*    */   public Object nextElement()
/*    */   {
/* 49 */     if (!hasMoreElements()) throw new NoSuchElementException();
/* 50 */     LLCell localLLCell = this.cursor;
/* 51 */     this.cursor = this.cursor.next;
/* 52 */     return localLLCell.data;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.collections.impl.LLEnumeration
 * JD-Core Version:    0.6.2
 */