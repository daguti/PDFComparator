/*     */ package org.apache.pdfbox.pdmodel.common.function.type4;
/*     */ 
/*     */ import java.util.Stack;
/*     */ 
/*     */ class ArithmeticOperators
/*     */ {
/*     */   static class Truncate
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/* 388 */       Number num = context.popNumber();
/* 389 */       if ((num instanceof Integer))
/*     */       {
/* 391 */         context.getStack().push(Integer.valueOf(num.intValue()));
/*     */       }
/*     */       else
/*     */       {
/* 395 */         context.getStack().push(Float.valueOf((int)num.floatValue()));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Sub
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/* 358 */       Stack stack = context.getStack();
/* 359 */       Number num2 = context.popNumber();
/* 360 */       Number num1 = context.popNumber();
/* 361 */       if (((num1 instanceof Integer)) && ((num2 instanceof Integer)))
/*     */       {
/* 363 */         long result = num1.longValue() - num2.longValue();
/* 364 */         if ((result < -2147483648L) || (result > 2147483647L))
/*     */         {
/* 366 */           stack.push(Float.valueOf((float)result));
/*     */         }
/*     */         else
/*     */         {
/* 370 */           stack.push(Integer.valueOf((int)result));
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 375 */         float result = num1.floatValue() - num2.floatValue();
/* 376 */         stack.push(Float.valueOf(result));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Sqrt
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/* 342 */       float num = context.popReal();
/* 343 */       if (num < 0.0F)
/*     */       {
/* 345 */         throw new IllegalArgumentException("argument must be nonnegative");
/*     */       }
/* 347 */       context.getStack().push(Float.valueOf((float)Math.sqrt(num)));
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Sin
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/* 329 */       float angle = context.popReal();
/* 330 */       float sin = (float)Math.sin(Math.toRadians(angle));
/* 331 */       context.getStack().push(Float.valueOf(sin));
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Round
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/* 310 */       Number num = context.popNumber();
/* 311 */       if ((num instanceof Integer))
/*     */       {
/* 313 */         context.getStack().push(Integer.valueOf(num.intValue()));
/*     */       }
/*     */       else
/*     */       {
/* 317 */         context.getStack().push(Float.valueOf((float)Math.round(num.doubleValue())));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Neg
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/* 283 */       Number num = context.popNumber();
/* 284 */       if ((num instanceof Integer))
/*     */       {
/* 286 */         int v = num.intValue();
/* 287 */         if (v == -2147483648)
/*     */         {
/* 289 */           context.getStack().push(Float.valueOf(-num.floatValue()));
/*     */         }
/*     */         else
/*     */         {
/* 293 */           context.getStack().push(Integer.valueOf(-num.intValue()));
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 298 */         context.getStack().push(Float.valueOf(-num.floatValue()));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Mul
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/* 254 */       Number num2 = context.popNumber();
/* 255 */       Number num1 = context.popNumber();
/* 256 */       if (((num1 instanceof Integer)) && ((num2 instanceof Integer)))
/*     */       {
/* 258 */         long result = num1.longValue() * num2.longValue();
/* 259 */         if ((result >= -2147483648L) && (result <= 2147483647L))
/*     */         {
/* 261 */           context.getStack().push(Integer.valueOf((int)result));
/*     */         }
/*     */         else
/*     */         {
/* 265 */           context.getStack().push(Float.valueOf((float)result));
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 270 */         double result = num1.doubleValue() * num2.doubleValue();
/* 271 */         context.getStack().push(Float.valueOf((float)result));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Mod
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/* 241 */       int int2 = context.popInt();
/* 242 */       int int1 = context.popInt();
/* 243 */       context.getStack().push(Integer.valueOf(int1 % int2));
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Log
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/* 229 */       Number num = context.popNumber();
/* 230 */       context.getStack().push(Float.valueOf((float)Math.log10(num.doubleValue())));
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Ln
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/* 217 */       Number num = context.popNumber();
/* 218 */       context.getStack().push(Float.valueOf((float)Math.log(num.doubleValue())));
/*     */     }
/*     */   }
/*     */ 
/*     */   static class IDiv
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/* 204 */       int num2 = context.popInt();
/* 205 */       int num1 = context.popInt();
/* 206 */       context.getStack().push(Integer.valueOf(num1 / num2));
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Floor
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/* 185 */       Number num = context.popNumber();
/* 186 */       if ((num instanceof Integer))
/*     */       {
/* 188 */         context.getStack().push(num);
/*     */       }
/*     */       else
/*     */       {
/* 192 */         context.getStack().push(Float.valueOf((float)Math.floor(num.doubleValue())));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Exp
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/* 171 */       Number exp = context.popNumber();
/* 172 */       Number base = context.popNumber();
/* 173 */       double value = Math.pow(base.doubleValue(), exp.doubleValue());
/* 174 */       context.getStack().push(Float.valueOf((float)value));
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Div
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/* 158 */       Number num2 = context.popNumber();
/* 159 */       Number num1 = context.popNumber();
/* 160 */       context.getStack().push(Float.valueOf(num1.floatValue() / num2.floatValue()));
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Cvr
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/* 146 */       Number num = context.popNumber();
/* 147 */       context.getStack().push(Float.valueOf(num.floatValue()));
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Cvi
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/* 134 */       Number num = context.popNumber();
/* 135 */       context.getStack().push(Integer.valueOf(num.intValue()));
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Cos
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/* 121 */       float angle = context.popReal();
/* 122 */       float cos = (float)Math.cos(Math.toRadians(angle));
/* 123 */       context.getStack().push(Float.valueOf(cos));
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Ceiling
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/* 102 */       Number num = context.popNumber();
/* 103 */       if ((num instanceof Integer))
/*     */       {
/* 105 */         context.getStack().push(num);
/*     */       }
/*     */       else
/*     */       {
/* 109 */         context.getStack().push(Float.valueOf((float)Math.ceil(num.doubleValue())));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Atan
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/*  83 */       float den = context.popReal();
/*  84 */       float num = context.popReal();
/*  85 */       float atan = (float)Math.atan2(num, den);
/*  86 */       atan = (float)Math.toDegrees(atan) % 360.0F;
/*  87 */       if (atan < 0.0F)
/*     */       {
/*  89 */         atan += 360.0F;
/*     */       }
/*  91 */       context.getStack().push(Float.valueOf(atan));
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Add
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/*  54 */       Number num2 = context.popNumber();
/*  55 */       Number num1 = context.popNumber();
/*  56 */       if (((num1 instanceof Integer)) && ((num2 instanceof Integer)))
/*     */       {
/*  58 */         long sum = num1.longValue() + num2.longValue();
/*  59 */         if ((sum < -2147483648L) || (sum > 2147483647L))
/*     */         {
/*  61 */           context.getStack().push(Float.valueOf((float)sum));
/*     */         }
/*     */         else
/*     */         {
/*  65 */           context.getStack().push(Integer.valueOf((int)sum));
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/*  70 */         float sum = num1.floatValue() + num2.floatValue();
/*  71 */         context.getStack().push(Float.valueOf(sum));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Abs
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/*  35 */       Number num = context.popNumber();
/*  36 */       if ((num instanceof Integer))
/*     */       {
/*  38 */         context.getStack().push(Integer.valueOf(Math.abs(num.intValue())));
/*     */       }
/*     */       else
/*     */       {
/*  42 */         context.getStack().push(Float.valueOf(Math.abs(num.floatValue())));
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.function.type4.ArithmeticOperators
 * JD-Core Version:    0.6.2
 */