/*    */ package org.antlr.misc;
/*    */ 
/*    */ public class Barrier
/*    */ {
/*    */   protected int threshold;
/* 37 */   protected int count = 0;
/*    */ 
/*    */   public Barrier(int t) {
/* 40 */     this.threshold = t;
/*    */   }
/*    */ 
/*    */   public synchronized void waitForRelease()
/*    */     throws InterruptedException
/*    */   {
/* 46 */     this.count += 1;
/*    */ 
/* 49 */     if (this.count == this.threshold)
/*    */     {
/* 51 */       action();
/* 52 */       notifyAll();
/*    */     } else {
/* 54 */       while (this.count < this.threshold)
/* 55 */         wait();
/*    */     }
/*    */   }
/*    */ 
/*    */   public void action()
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.misc.Barrier
 * JD-Core Version:    0.6.2
 */