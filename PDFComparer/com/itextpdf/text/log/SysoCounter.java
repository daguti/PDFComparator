/*    */ package com.itextpdf.text.log;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class SysoCounter
/*    */   implements Counter
/*    */ {
/*    */   protected String name;
/*    */ 
/*    */   public SysoCounter()
/*    */   {
/* 57 */     this.name = "iText";
/*    */   }
/*    */ 
/*    */   protected SysoCounter(Class<?> klass)
/*    */   {
/* 65 */     this.name = klass.getName();
/*    */   }
/*    */ 
/*    */   public Counter getCounter(Class<?> klass)
/*    */   {
/* 72 */     return new SysoCounter(klass);
/*    */   }
/*    */ 
/*    */   public void read(long l)
/*    */   {
/* 79 */     System.out.println(String.format("[%s] %s bytes read", new Object[] { this.name, Long.valueOf(l) }));
/*    */   }
/*    */ 
/*    */   public void written(long l)
/*    */   {
/* 86 */     System.out.println(String.format("[%s] %s bytes written", new Object[] { this.name, Long.valueOf(l) }));
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.log.SysoCounter
 * JD-Core Version:    0.6.2
 */