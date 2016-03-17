/*    */ package com.itextpdf.text.log;
/*    */ 
/*    */ public class NoOpCounter
/*    */   implements Counter
/*    */ {
/*    */   public Counter getCounter(Class<?> klass)
/*    */   {
/* 54 */     return this;
/*    */   }
/*    */ 
/*    */   public void read(long l)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void written(long l)
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.log.NoOpCounter
 * JD-Core Version:    0.6.2
 */