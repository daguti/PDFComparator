/*     */ package org.apache.pdfbox.pdmodel.common.function.type4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Stack;
/*     */ 
/*     */ public class InstructionSequence
/*     */ {
/*  30 */   private List<Object> instructions = new ArrayList();
/*     */ 
/*     */   public void addName(String name)
/*     */   {
/*  38 */     this.instructions.add(name);
/*     */   }
/*     */ 
/*     */   public void addInteger(int value)
/*     */   {
/*  47 */     this.instructions.add(Integer.valueOf(value));
/*     */   }
/*     */ 
/*     */   public void addReal(float value)
/*     */   {
/*  56 */     this.instructions.add(Float.valueOf(value));
/*     */   }
/*     */ 
/*     */   public void addBoolean(boolean value)
/*     */   {
/*  65 */     this.instructions.add(Boolean.valueOf(value));
/*     */   }
/*     */ 
/*     */   public void addProc(InstructionSequence child)
/*     */   {
/*  74 */     this.instructions.add(child);
/*     */   }
/*     */ 
/*     */   public void execute(ExecutionContext context)
/*     */   {
/*  83 */     Stack stack = context.getStack();
/*  84 */     for (Iterator i$ = this.instructions.iterator(); i$.hasNext(); ) { Object o = i$.next();
/*     */ 
/*  86 */       if ((o instanceof String))
/*     */       {
/*  88 */         String name = (String)o;
/*  89 */         Operator cmd = context.getOperators().getOperator(name);
/*  90 */         if (cmd != null)
/*     */         {
/*  92 */           cmd.execute(context);
/*     */         }
/*     */         else
/*     */         {
/*  96 */           throw new UnsupportedOperationException("Unknown operator or name: " + name);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 101 */         stack.push(o);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 106 */     while ((!stack.isEmpty()) && ((stack.peek() instanceof InstructionSequence)))
/*     */     {
/* 108 */       InstructionSequence nested = (InstructionSequence)stack.pop();
/* 109 */       nested.execute(context);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.function.type4.InstructionSequence
 * JD-Core Version:    0.6.2
 */