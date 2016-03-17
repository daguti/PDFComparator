/*    */ package antlr.debug;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class InputBufferReporter
/*    */   implements InputBufferListener
/*    */ {
/*    */   public void doneParsing(TraceEvent paramTraceEvent)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void inputBufferChanged(InputBufferEvent paramInputBufferEvent)
/*    */   {
/* 12 */     System.out.println(paramInputBufferEvent);
/*    */   }
/*    */ 
/*    */   public void inputBufferConsume(InputBufferEvent paramInputBufferEvent)
/*    */   {
/* 18 */     System.out.println(paramInputBufferEvent);
/*    */   }
/*    */ 
/*    */   public void inputBufferLA(InputBufferEvent paramInputBufferEvent)
/*    */   {
/* 24 */     System.out.println(paramInputBufferEvent);
/*    */   }
/*    */   public void inputBufferMark(InputBufferEvent paramInputBufferEvent) {
/* 27 */     System.out.println(paramInputBufferEvent);
/*    */   }
/*    */   public void inputBufferRewind(InputBufferEvent paramInputBufferEvent) {
/* 30 */     System.out.println(paramInputBufferEvent);
/*    */   }
/*    */ 
/*    */   public void refresh()
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.debug.InputBufferReporter
 * JD-Core Version:    0.6.2
 */