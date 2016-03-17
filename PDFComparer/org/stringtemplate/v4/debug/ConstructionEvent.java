/*    */ package org.stringtemplate.v4.debug;
/*    */ 
/*    */ public class ConstructionEvent
/*    */ {
/* 33 */   public Throwable stack = new Throwable();
/*    */ 
/* 35 */   public String getFileName() { return getSTEntryPoint().getFileName(); } 
/* 36 */   public int getLine() { return getSTEntryPoint().getLineNumber(); }
/*    */ 
/*    */   public StackTraceElement getSTEntryPoint() {
/* 39 */     StackTraceElement[] trace = this.stack.getStackTrace();
/* 40 */     for (StackTraceElement e : trace) {
/* 41 */       String name = e.toString();
/* 42 */       if (!name.startsWith("org.stringtemplate.v4")) return e;
/*    */     }
/* 44 */     return trace[0];
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.debug.ConstructionEvent
 * JD-Core Version:    0.6.2
 */