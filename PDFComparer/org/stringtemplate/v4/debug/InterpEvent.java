/*    */ package org.stringtemplate.v4.debug;
/*    */ 
/*    */ import org.stringtemplate.v4.InstanceScope;
/*    */ 
/*    */ public class InterpEvent
/*    */ {
/*    */   public InstanceScope scope;
/*    */   public final int outputStartChar;
/*    */   public final int outputStopChar;
/*    */ 
/*    */   public InterpEvent(InstanceScope scope, int outputStartChar, int outputStopChar)
/*    */   {
/* 36 */     this.scope = scope;
/* 37 */     this.outputStartChar = outputStartChar;
/* 38 */     this.outputStopChar = outputStopChar;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 43 */     return getClass().getSimpleName() + "{" + "self=" + this.scope.st + ", start=" + this.outputStartChar + ", stop=" + this.outputStopChar + '}';
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.debug.InterpEvent
 * JD-Core Version:    0.6.2
 */