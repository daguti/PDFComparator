/*     */ package org.apache.pdfbox.pdmodel.common.function;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Stack;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRange;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ import org.apache.pdfbox.pdmodel.common.function.type4.ExecutionContext;
/*     */ import org.apache.pdfbox.pdmodel.common.function.type4.InstructionSequence;
/*     */ import org.apache.pdfbox.pdmodel.common.function.type4.InstructionSequenceBuilder;
/*     */ import org.apache.pdfbox.pdmodel.common.function.type4.Operators;
/*     */ 
/*     */ public class PDFunctionType4 extends PDFunction
/*     */ {
/*  38 */   private static final Operators OPERATORS = new Operators();
/*     */   private final InstructionSequence instructions;
/*     */ 
/*     */   public PDFunctionType4(COSBase functionStream)
/*     */     throws IOException
/*     */   {
/*  50 */     super(functionStream);
/*  51 */     this.instructions = InstructionSequenceBuilder.parse(getPDStream().getInputStreamAsString());
/*     */   }
/*     */ 
/*     */   public int getFunctionType()
/*     */   {
/*  61 */     return 4;
/*     */   }
/*     */ 
/*     */   public float[] eval(float[] input)
/*     */     throws IOException
/*     */   {
/*  70 */     ExecutionContext context = new ExecutionContext(OPERATORS);
/*  71 */     for (int i = 0; i < input.length; i++)
/*     */     {
/*  73 */       PDRange domain = getDomainForInput(i);
/*  74 */       float value = clipToRange(input[i], domain.getMin(), domain.getMax());
/*  75 */       context.getStack().push(Float.valueOf(value));
/*     */     }
/*     */ 
/*  79 */     this.instructions.execute(context);
/*     */ 
/*  82 */     int numberOfOutputValues = getNumberOfOutputParameters();
/*  83 */     int numberOfActualOutputValues = context.getStack().size();
/*  84 */     if (numberOfActualOutputValues < numberOfOutputValues)
/*     */     {
/*  86 */       throw new IllegalStateException("The type 4 function returned " + numberOfActualOutputValues + " values but the Range entry indicates that " + numberOfOutputValues + " values be returned.");
/*     */     }
/*     */ 
/*  91 */     float[] outputValues = new float[numberOfOutputValues];
/*  92 */     for (int i = numberOfOutputValues - 1; i >= 0; i--)
/*     */     {
/*  94 */       PDRange range = getRangeForOutput(i);
/*  95 */       outputValues[i] = context.popReal();
/*  96 */       outputValues[i] = clipToRange(outputValues[i], range.getMin(), range.getMax());
/*     */     }
/*     */ 
/* 100 */     return outputValues;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.function.PDFunctionType4
 * JD-Core Version:    0.6.2
 */