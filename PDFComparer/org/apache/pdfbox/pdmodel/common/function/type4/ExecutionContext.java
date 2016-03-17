/*    */ package org.apache.pdfbox.pdmodel.common.function.type4;
/*    */ 
/*    */ import java.util.Stack;
/*    */ 
/*    */ public class ExecutionContext
/*    */ {
/*    */   private Operators operators;
/* 30 */   private Stack<Object> stack = new Stack();
/*    */ 
/*    */   public ExecutionContext(Operators operatorSet)
/*    */   {
/* 38 */     this.operators = operatorSet;
/*    */   }
/*    */ 
/*    */   public Stack<Object> getStack()
/*    */   {
/* 47 */     return this.stack;
/*    */   }
/*    */ 
/*    */   public Operators getOperators()
/*    */   {
/* 56 */     return this.operators;
/*    */   }
/*    */ 
/*    */   public Number popNumber()
/*    */   {
/* 66 */     return (Number)this.stack.pop();
/*    */   }
/*    */ 
/*    */   public int popInt()
/*    */   {
/* 76 */     return ((Integer)this.stack.pop()).intValue();
/*    */   }
/*    */ 
/*    */   public float popReal()
/*    */   {
/* 86 */     return ((Number)this.stack.pop()).floatValue();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.function.type4.ExecutionContext
 * JD-Core Version:    0.6.2
 */