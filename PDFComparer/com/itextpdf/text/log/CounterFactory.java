/*    */ package com.itextpdf.text.log;
/*    */ 
/*    */ public class CounterFactory
/*    */ {
/* 59 */   private static CounterFactory myself = new CounterFactory();
/*    */ 
/* 63 */   private Counter counter = new NoOpCounter();
/*    */ 
/*    */   public static CounterFactory getInstance()
/*    */   {
/* 70 */     return myself;
/*    */   }
/*    */ 
/*    */   public static Counter getCounter(Class<?> klass)
/*    */   {
/* 75 */     return myself.counter.getCounter(klass);
/*    */   }
/*    */ 
/*    */   public Counter getCounter()
/*    */   {
/* 82 */     return this.counter;
/*    */   }
/*    */ 
/*    */   public void setCounter(Counter counter)
/*    */   {
/* 89 */     this.counter = counter;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.log.CounterFactory
 * JD-Core Version:    0.6.2
 */