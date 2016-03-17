/*     */ package org.apache.pdfbox.pdmodel.common.function.type4;
/*     */ 
/*     */ import java.util.Stack;
/*     */ 
/*     */ class BitwiseOperators
/*     */ {
/*     */   static class Xor extends BitwiseOperators.AbstractLogicalOperator
/*     */   {
/*     */     Xor()
/*     */     {
/* 176 */       super();
/*     */     }
/*     */ 
/*     */     protected boolean applyForBoolean(boolean bool1, boolean bool2)
/*     */     {
/* 182 */       return bool1 ^ bool2;
/*     */     }
/*     */ 
/*     */     protected int applyforInteger(int int1, int int2)
/*     */     {
/* 188 */       return int1 ^ int2;
/*     */     }
/*     */   }
/*     */ 
/*     */   static class True
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/* 169 */       Stack stack = context.getStack();
/* 170 */       stack.push(Boolean.TRUE);
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Or extends BitwiseOperators.AbstractLogicalOperator
/*     */   {
/*     */     Or()
/*     */     {
/* 146 */       super();
/*     */     }
/*     */ 
/*     */     protected boolean applyForBoolean(boolean bool1, boolean bool2)
/*     */     {
/* 152 */       return bool1 | bool2;
/*     */     }
/*     */ 
/*     */     protected int applyforInteger(int int1, int int2)
/*     */     {
/* 158 */       return int1 | int2;
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Not
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/* 123 */       Stack stack = context.getStack();
/* 124 */       Object op1 = stack.pop();
/* 125 */       if ((op1 instanceof Boolean))
/*     */       {
/* 127 */         boolean bool1 = ((Boolean)op1).booleanValue();
/* 128 */         boolean result = !bool1;
/* 129 */         stack.push(Boolean.valueOf(result));
/*     */       }
/* 131 */       else if ((op1 instanceof Integer))
/*     */       {
/* 133 */         int int1 = ((Integer)op1).intValue();
/* 134 */         int result = -int1;
/* 135 */         stack.push(Integer.valueOf(result));
/*     */       }
/*     */       else
/*     */       {
/* 139 */         throw new ClassCastException("Operand must be bool or int");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class False
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/* 111 */       Stack stack = context.getStack();
/* 112 */       stack.push(Boolean.FALSE);
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Bitshift
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/*  88 */       Stack stack = context.getStack();
/*  89 */       int shift = ((Integer)stack.pop()).intValue();
/*  90 */       int int1 = ((Integer)stack.pop()).intValue();
/*  91 */       if (shift < 0)
/*     */       {
/*  93 */         int result = int1 >> Math.abs(shift);
/*  94 */         stack.push(Integer.valueOf(result));
/*     */       }
/*     */       else
/*     */       {
/*  98 */         int result = int1 << shift;
/*  99 */         stack.push(Integer.valueOf(result));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class And extends BitwiseOperators.AbstractLogicalOperator
/*     */   {
/*     */     And()
/*     */     {
/*  66 */       super();
/*     */     }
/*     */ 
/*     */     protected boolean applyForBoolean(boolean bool1, boolean bool2)
/*     */     {
/*  72 */       return bool1 & bool2;
/*     */     }
/*     */ 
/*     */     protected int applyforInteger(int int1, int int2)
/*     */     {
/*  78 */       return int1 & int2;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static abstract class AbstractLogicalOperator
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/*  35 */       Stack stack = context.getStack();
/*  36 */       Object op2 = stack.pop();
/*  37 */       Object op1 = stack.pop();
/*  38 */       if (((op1 instanceof Boolean)) && ((op2 instanceof Boolean)))
/*     */       {
/*  40 */         boolean bool1 = ((Boolean)op1).booleanValue();
/*  41 */         boolean bool2 = ((Boolean)op2).booleanValue();
/*  42 */         boolean result = applyForBoolean(bool1, bool2);
/*  43 */         stack.push(Boolean.valueOf(result));
/*     */       }
/*  45 */       else if (((op1 instanceof Integer)) && ((op2 instanceof Integer)))
/*     */       {
/*  47 */         int int1 = ((Integer)op1).intValue();
/*  48 */         int int2 = ((Integer)op2).intValue();
/*  49 */         int result = applyforInteger(int1, int2);
/*  50 */         stack.push(Integer.valueOf(result));
/*     */       }
/*     */       else
/*     */       {
/*  54 */         throw new ClassCastException("Operands must be bool/bool or int/int");
/*     */       }
/*     */     }
/*     */ 
/*     */     protected abstract boolean applyForBoolean(boolean paramBoolean1, boolean paramBoolean2);
/*     */ 
/*     */     protected abstract int applyforInteger(int paramInt1, int paramInt2);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.function.type4.BitwiseOperators
 * JD-Core Version:    0.6.2
 */