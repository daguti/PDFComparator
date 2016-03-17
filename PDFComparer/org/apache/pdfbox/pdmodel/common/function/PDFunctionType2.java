/*     */ package org.apache.pdfbox.pdmodel.common.function;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ 
/*     */ public class PDFunctionType2 extends PDFunction
/*     */ {
/*     */   private COSArray C0;
/*     */   private COSArray C1;
/*     */   private Float N;
/*     */ 
/*     */   public PDFunctionType2(COSBase function)
/*     */   {
/*  55 */     super(function);
/*     */   }
/*     */ 
/*     */   public int getFunctionType()
/*     */   {
/*  64 */     return 2;
/*     */   }
/*     */ 
/*     */   public float[] eval(float[] input)
/*     */     throws IOException
/*     */   {
/*  77 */     double inputValue = input[0];
/*  78 */     double exponent = getN();
/*  79 */     COSArray c0 = getC0();
/*  80 */     COSArray c1 = getC1();
/*  81 */     int c0Size = c0.size();
/*  82 */     float[] functionResult = new float[c0Size];
/*  83 */     for (int j = 0; j < c0Size; j++)
/*     */     {
/*  86 */       functionResult[j] = (((COSNumber)c0.get(j)).floatValue() + (float)Math.pow(inputValue, exponent) * (((COSNumber)c1.get(j)).floatValue() - ((COSNumber)c0.get(j)).floatValue()));
/*     */     }
/*     */ 
/*  89 */     return clipToRange(functionResult);
/*     */   }
/*     */ 
/*     */   public COSArray getC0()
/*     */   {
/*  98 */     if (this.C0 == null)
/*     */     {
/* 100 */       this.C0 = ((COSArray)getDictionary().getDictionaryObject(COSName.C0));
/* 101 */       if (this.C0 == null)
/*     */       {
/* 104 */         this.C0 = new COSArray();
/* 105 */         this.C0.add(new COSFloat(0.0F));
/*     */       }
/*     */     }
/* 108 */     return this.C0;
/*     */   }
/*     */ 
/*     */   public COSArray getC1()
/*     */   {
/* 117 */     if (this.C1 == null)
/*     */     {
/* 119 */       this.C1 = ((COSArray)getDictionary().getDictionaryObject(COSName.C1));
/* 120 */       if (this.C1 == null)
/*     */       {
/* 123 */         this.C1 = new COSArray();
/* 124 */         this.C1.add(new COSFloat(1.0F));
/*     */       }
/*     */     }
/* 127 */     return this.C1;
/*     */   }
/*     */ 
/*     */   public float getN()
/*     */   {
/* 136 */     if (this.N == null)
/*     */     {
/* 138 */       this.N = Float.valueOf(getDictionary().getFloat(COSName.N));
/*     */     }
/* 140 */     return this.N.floatValue();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.function.PDFunctionType2
 * JD-Core Version:    0.6.2
 */