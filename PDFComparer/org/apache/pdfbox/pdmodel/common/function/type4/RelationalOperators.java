/*     */ package org.apache.pdfbox.pdmodel.common.function.type4;
/*     */ 
/*     */ import java.util.Stack;
/*     */ 
/*     */ class RelationalOperators
/*     */ {
/*     */   static class Ne extends RelationalOperators.Eq
/*     */   {
/*     */     protected boolean isEqual(Object op1, Object op2)
/*     */     {
/* 134 */       boolean result = super.isEqual(op1, op2);
/* 135 */       return !result;
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Lt extends RelationalOperators.AbstractNumberComparisonOperator
/*     */   {
/*     */     Lt()
/*     */     {
/* 116 */       super();
/*     */     }
/*     */ 
/*     */     protected boolean compare(Number num1, Number num2)
/*     */     {
/* 122 */       return num1.floatValue() < num2.floatValue();
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Le extends RelationalOperators.AbstractNumberComparisonOperator
/*     */   {
/*     */     Le()
/*     */     {
/* 104 */       super();
/*     */     }
/*     */ 
/*     */     protected boolean compare(Number num1, Number num2)
/*     */     {
/* 110 */       return num1.floatValue() <= num2.floatValue();
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Gt extends RelationalOperators.AbstractNumberComparisonOperator
/*     */   {
/*     */     Gt()
/*     */     {
/*  92 */       super();
/*     */     }
/*     */ 
/*     */     protected boolean compare(Number num1, Number num2)
/*     */     {
/*  98 */       return num1.floatValue() > num2.floatValue();
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Ge extends RelationalOperators.AbstractNumberComparisonOperator
/*     */   {
/*     */     Ge()
/*     */     {
/*  80 */       super();
/*     */     }
/*     */ 
/*     */     protected boolean compare(Number num1, Number num2)
/*     */     {
/*  86 */       return num1.floatValue() >= num2.floatValue();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static abstract class AbstractNumberComparisonOperator
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/*  66 */       Stack stack = context.getStack();
/*  67 */       Object op2 = stack.pop();
/*  68 */       Object op1 = stack.pop();
/*  69 */       Number num1 = (Number)op1;
/*  70 */       Number num2 = (Number)op2;
/*  71 */       boolean result = compare(num1, num2);
/*  72 */       stack.push(Boolean.valueOf(result));
/*     */     }
/*     */ 
/*     */     protected abstract boolean compare(Number paramNumber1, Number paramNumber2);
/*     */   }
/*     */ 
/*     */   static class Eq
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/*  35 */       Stack stack = context.getStack();
/*  36 */       Object op2 = stack.pop();
/*  37 */       Object op1 = stack.pop();
/*  38 */       boolean result = isEqual(op1, op2);
/*  39 */       stack.push(Boolean.valueOf(result));
/*     */     }
/*     */ 
/*     */     protected boolean isEqual(Object op1, Object op2)
/*     */     {
/*  44 */       boolean result = false;
/*  45 */       if (((op1 instanceof Number)) && ((op2 instanceof Number)))
/*     */       {
/*  47 */         Number num1 = (Number)op1;
/*  48 */         Number num2 = (Number)op2;
/*  49 */         result = num1.floatValue() == num2.floatValue();
/*     */       }
/*     */       else
/*     */       {
/*  53 */         result = op1.equals(op2);
/*     */       }
/*  55 */       return result;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.function.type4.RelationalOperators
 * JD-Core Version:    0.6.2
 */