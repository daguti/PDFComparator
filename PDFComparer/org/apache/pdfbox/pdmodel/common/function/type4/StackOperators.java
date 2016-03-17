/*     */ package org.apache.pdfbox.pdmodel.common.function.type4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Stack;
/*     */ 
/*     */ class StackOperators
/*     */ {
/*     */   static class Roll
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/* 114 */       Stack stack = context.getStack();
/* 115 */       int j = ((Number)stack.pop()).intValue();
/* 116 */       int n = ((Number)stack.pop()).intValue();
/* 117 */       if (j == 0)
/*     */       {
/* 119 */         return;
/*     */       }
/* 121 */       if (n < 0)
/*     */       {
/* 123 */         throw new IllegalArgumentException("rangecheck: " + n);
/*     */       }
/*     */ 
/* 126 */       LinkedList rolled = new LinkedList();
/* 127 */       LinkedList moved = new LinkedList();
/* 128 */       if (j < 0)
/*     */       {
/* 131 */         int n1 = n + j;
/* 132 */         for (int i = 0; i < n1; i++)
/*     */         {
/* 134 */           moved.addFirst(stack.pop());
/*     */         }
/* 136 */         for (int i = j; i < 0; i++)
/*     */         {
/* 138 */           rolled.addFirst(stack.pop());
/*     */         }
/* 140 */         stack.addAll(moved);
/* 141 */         stack.addAll(rolled);
/*     */       }
/*     */       else
/*     */       {
/* 146 */         int n1 = n - j;
/* 147 */         for (int i = j; i > 0; i--)
/*     */         {
/* 149 */           rolled.addFirst(stack.pop());
/*     */         }
/* 151 */         for (int i = 0; i < n1; i++)
/*     */         {
/* 153 */           moved.addFirst(stack.pop());
/*     */         }
/* 155 */         stack.addAll(rolled);
/* 156 */         stack.addAll(moved);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Pop
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/* 102 */       Stack stack = context.getStack();
/* 103 */       stack.pop();
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Index
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/*  84 */       Stack stack = context.getStack();
/*  85 */       int n = ((Number)stack.pop()).intValue();
/*  86 */       if (n < 0)
/*     */       {
/*  88 */         throw new IllegalArgumentException("rangecheck: " + n);
/*     */       }
/*  90 */       int size = stack.size();
/*  91 */       stack.push(stack.get(size - n - 1));
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Exch
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/*  69 */       Stack stack = context.getStack();
/*  70 */       Object any2 = stack.pop();
/*  71 */       Object any1 = stack.pop();
/*  72 */       stack.push(any2);
/*  73 */       stack.push(any1);
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Dup
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/*  57 */       Stack stack = context.getStack();
/*  58 */       stack.push(stack.peek());
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Copy
/*     */     implements Operator
/*     */   {
/*     */     public void execute(ExecutionContext context)
/*     */     {
/*  37 */       Stack stack = context.getStack();
/*  38 */       int n = ((Number)stack.pop()).intValue();
/*  39 */       if (n > 0)
/*     */       {
/*  41 */         int size = stack.size();
/*     */ 
/*  43 */         List copy = new ArrayList(stack.subList(size - n, size));
/*     */ 
/*  45 */         stack.addAll(copy);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.function.type4.StackOperators
 * JD-Core Version:    0.6.2
 */