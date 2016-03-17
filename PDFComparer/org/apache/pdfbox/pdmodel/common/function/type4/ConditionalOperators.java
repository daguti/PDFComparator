/*    */ package org.apache.pdfbox.pdmodel.common.function.type4;
/*    */ 
/*    */ import java.util.Stack;
/*    */ 
/*    */ class ConditionalOperators
/*    */ {
/*    */   static class IfElse
/*    */     implements Operator
/*    */   {
/*    */     public void execute(ExecutionContext context)
/*    */     {
/* 52 */       Stack stack = context.getStack();
/* 53 */       InstructionSequence proc2 = (InstructionSequence)stack.pop();
/* 54 */       InstructionSequence proc1 = (InstructionSequence)stack.pop();
/* 55 */       Boolean condition = (Boolean)stack.pop();
/* 56 */       if (condition.booleanValue())
/*    */       {
/* 58 */         proc1.execute(context);
/*    */       }
/*    */       else
/*    */       {
/* 62 */         proc2.execute(context);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   static class If
/*    */     implements Operator
/*    */   {
/*    */     public void execute(ExecutionContext context)
/*    */     {
/* 35 */       Stack stack = context.getStack();
/* 36 */       InstructionSequence proc = (InstructionSequence)stack.pop();
/* 37 */       Boolean condition = (Boolean)stack.pop();
/* 38 */       if (condition.booleanValue())
/*    */       {
/* 40 */         proc.execute(context);
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.function.type4.ConditionalOperators
 * JD-Core Version:    0.6.2
 */