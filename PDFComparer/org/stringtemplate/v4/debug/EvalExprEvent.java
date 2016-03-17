/*    */ package org.stringtemplate.v4.debug;
/*    */ 
/*    */ import org.stringtemplate.v4.InstanceScope;
/*    */ import org.stringtemplate.v4.ST;
/*    */ import org.stringtemplate.v4.compiler.CompiledST;
/*    */ 
/*    */ public class EvalExprEvent extends InterpEvent
/*    */ {
/*    */   public final int exprStartChar;
/*    */   public final int exprStopChar;
/*    */   public final String expr;
/*    */ 
/*    */   public EvalExprEvent(InstanceScope scope, int start, int stop, int exprStartChar, int exprStopChar)
/*    */   {
/* 38 */     super(scope, start, stop);
/* 39 */     this.exprStartChar = exprStartChar;
/* 40 */     this.exprStopChar = exprStopChar;
/* 41 */     if ((exprStartChar >= 0) && (exprStopChar >= 0)) {
/* 42 */       this.expr = scope.st.impl.template.substring(exprStartChar, exprStopChar + 1);
/*    */     }
/*    */     else
/* 45 */       this.expr = "";
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 51 */     return getClass().getSimpleName() + "{" + "self=" + this.scope.st + ", expr='" + this.expr + '\'' + ", exprStartChar=" + this.exprStartChar + ", exprStopChar=" + this.exprStopChar + ", start=" + this.outputStartChar + ", stop=" + this.outputStopChar + '}';
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.debug.EvalExprEvent
 * JD-Core Version:    0.6.2
 */