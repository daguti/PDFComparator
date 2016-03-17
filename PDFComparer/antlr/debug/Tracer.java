/*    */ package antlr.debug;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class Tracer extends TraceAdapter
/*    */   implements TraceListener
/*    */ {
/*  4 */   String indent = "";
/*    */ 
/*    */   protected void dedent()
/*    */   {
/*  8 */     if (this.indent.length() < 2)
/*  9 */       this.indent = "";
/*    */     else
/* 11 */       this.indent = this.indent.substring(2); 
/*    */   }
/*    */ 
/* 14 */   public void enterRule(TraceEvent paramTraceEvent) { System.out.println(this.indent + paramTraceEvent);
/* 15 */     indent(); }
/*    */ 
/*    */   public void exitRule(TraceEvent paramTraceEvent) {
/* 18 */     dedent();
/* 19 */     System.out.println(this.indent + paramTraceEvent);
/*    */   }
/*    */   protected void indent() {
/* 22 */     this.indent += "  ";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.debug.Tracer
 * JD-Core Version:    0.6.2
 */