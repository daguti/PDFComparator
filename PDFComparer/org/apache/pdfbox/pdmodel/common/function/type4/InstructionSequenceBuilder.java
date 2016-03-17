/*     */ package org.apache.pdfbox.pdmodel.common.function.type4;
/*     */ 
/*     */ import java.util.Stack;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class InstructionSequenceBuilder extends Parser.AbstractSyntaxHandler
/*     */ {
/*  31 */   private InstructionSequence mainSequence = new InstructionSequence();
/*  32 */   private Stack<InstructionSequence> seqStack = new Stack();
/*     */ 
/*  66 */   private static final Pattern INTEGER_PATTERN = Pattern.compile("[\\+\\-]?\\d+");
/*  67 */   private static final Pattern REAL_PATTERN = Pattern.compile("[\\-]?\\d*\\.\\d*([Ee]\\-?\\d+)?");
/*     */ 
/*     */   private InstructionSequenceBuilder()
/*     */   {
/*  36 */     this.seqStack.push(this.mainSequence);
/*     */   }
/*     */ 
/*     */   public InstructionSequence getInstructionSequence()
/*     */   {
/*  45 */     return this.mainSequence;
/*     */   }
/*     */ 
/*     */   public static InstructionSequence parse(CharSequence text)
/*     */   {
/*  56 */     InstructionSequenceBuilder builder = new InstructionSequenceBuilder();
/*  57 */     Parser.parse(text, builder);
/*  58 */     return builder.getInstructionSequence();
/*     */   }
/*     */ 
/*     */   private InstructionSequence getCurrentSequence()
/*     */   {
/*  63 */     return (InstructionSequence)this.seqStack.peek();
/*     */   }
/*     */ 
/*     */   public void token(CharSequence text)
/*     */   {
/*  72 */     String token = text.toString();
/*  73 */     token(token);
/*     */   }
/*     */ 
/*     */   private void token(String token)
/*     */   {
/*  78 */     if ("{".equals(token))
/*     */     {
/*  80 */       InstructionSequence child = new InstructionSequence();
/*  81 */       getCurrentSequence().addProc(child);
/*  82 */       this.seqStack.push(child);
/*     */     }
/*  84 */     else if ("}".equals(token))
/*     */     {
/*  86 */       this.seqStack.pop();
/*     */     }
/*     */     else
/*     */     {
/*  90 */       Matcher m = INTEGER_PATTERN.matcher(token);
/*  91 */       if (m.matches())
/*     */       {
/*  93 */         getCurrentSequence().addInteger(parseInt(token.toString()));
/*  94 */         return;
/*     */       }
/*     */ 
/*  97 */       m = REAL_PATTERN.matcher(token);
/*  98 */       if (m.matches())
/*     */       {
/* 100 */         getCurrentSequence().addReal(parseReal(token));
/* 101 */         return;
/*     */       }
/*     */ 
/* 106 */       getCurrentSequence().addName(token.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   public static int parseInt(String token)
/*     */   {
/* 117 */     if (token.startsWith("+"))
/*     */     {
/* 119 */       token = token.substring(1);
/*     */     }
/* 121 */     return Integer.parseInt(token);
/*     */   }
/*     */ 
/*     */   public static float parseReal(String token)
/*     */   {
/* 131 */     return Float.parseFloat(token);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.function.type4.InstructionSequenceBuilder
 * JD-Core Version:    0.6.2
 */