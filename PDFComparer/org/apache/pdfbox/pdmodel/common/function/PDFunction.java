/*     */ package org.apache.pdfbox.pdmodel.common.function;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSObject;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRange;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ 
/*     */ public abstract class PDFunction
/*     */   implements COSObjectable
/*     */ {
/*  40 */   private PDStream functionStream = null;
/*  41 */   private COSDictionary functionDictionary = null;
/*  42 */   private COSArray domain = null;
/*  43 */   private COSArray range = null;
/*  44 */   private int numberOfInputValues = -1;
/*  45 */   private int numberOfOutputValues = -1;
/*     */ 
/*     */   public PDFunction(COSBase function)
/*     */   {
/*  55 */     if ((function instanceof COSStream))
/*     */     {
/*  57 */       this.functionStream = new PDStream((COSStream)function);
/*  58 */       this.functionStream.getStream().setItem(COSName.TYPE, COSName.FUNCTION);
/*     */     }
/*  60 */     else if ((function instanceof COSDictionary))
/*     */     {
/*  62 */       this.functionDictionary = ((COSDictionary)function);
/*     */     }
/*     */   }
/*     */ 
/*     */   public abstract int getFunctionType();
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  87 */     if (this.functionStream != null)
/*     */     {
/*  89 */       return this.functionStream.getCOSObject();
/*     */     }
/*     */ 
/*  93 */     return this.functionDictionary;
/*     */   }
/*     */ 
/*     */   public COSDictionary getDictionary()
/*     */   {
/* 103 */     if (this.functionStream != null)
/*     */     {
/* 105 */       return this.functionStream.getStream();
/*     */     }
/*     */ 
/* 109 */     return this.functionDictionary;
/*     */   }
/*     */ 
/*     */   protected PDStream getPDStream()
/*     */   {
/* 119 */     return this.functionStream;
/*     */   }
/*     */ 
/*     */   public static PDFunction create(COSBase function)
/*     */     throws IOException
/*     */   {
/* 132 */     PDFunction retval = null;
/* 133 */     if ((function instanceof COSObject))
/*     */     {
/* 135 */       function = ((COSObject)function).getObject();
/*     */     }
/* 137 */     COSDictionary functionDictionary = (COSDictionary)function;
/* 138 */     int functionType = functionDictionary.getInt(COSName.FUNCTION_TYPE);
/* 139 */     if (functionType == 0)
/*     */     {
/* 141 */       retval = new PDFunctionType0(functionDictionary);
/*     */     }
/* 143 */     else if (functionType == 2)
/*     */     {
/* 145 */       retval = new PDFunctionType2(functionDictionary);
/*     */     }
/* 147 */     else if (functionType == 3)
/*     */     {
/* 149 */       retval = new PDFunctionType3(functionDictionary);
/*     */     }
/* 151 */     else if (functionType == 4)
/*     */     {
/* 153 */       retval = new PDFunctionType4(functionDictionary);
/*     */     }
/*     */     else
/*     */     {
/* 157 */       throw new IOException("Error: Unknown function type " + functionType);
/*     */     }
/* 159 */     return retval;
/*     */   }
/*     */ 
/*     */   public int getNumberOfOutputParameters()
/*     */   {
/* 174 */     if (this.numberOfOutputValues == -1)
/*     */     {
/* 176 */       COSArray rangeValues = getRangeValues();
/* 177 */       this.numberOfOutputValues = (rangeValues.size() / 2);
/*     */     }
/* 179 */     return this.numberOfOutputValues;
/*     */   }
/*     */ 
/*     */   public PDRange getRangeForOutput(int n)
/*     */   {
/* 193 */     COSArray rangeValues = getRangeValues();
/* 194 */     return new PDRange(rangeValues, n);
/*     */   }
/*     */ 
/*     */   public void setRangeValues(COSArray rangeValues)
/*     */   {
/* 204 */     this.range = rangeValues;
/* 205 */     getDictionary().setItem(COSName.RANGE, rangeValues);
/*     */   }
/*     */ 
/*     */   public int getNumberOfInputParameters()
/*     */   {
/* 217 */     if (this.numberOfInputValues == -1)
/*     */     {
/* 219 */       COSArray array = getDomainValues();
/* 220 */       this.numberOfInputValues = (array.size() / 2);
/*     */     }
/* 222 */     return this.numberOfInputValues;
/*     */   }
/*     */ 
/*     */   public PDRange getDomainForInput(int n)
/*     */   {
/* 236 */     COSArray domainValues = getDomainValues();
/* 237 */     return new PDRange(domainValues, n);
/*     */   }
/*     */ 
/*     */   public void setDomainValues(COSArray domainValues)
/*     */   {
/* 247 */     this.domain = domainValues;
/* 248 */     getDictionary().setItem(COSName.DOMAIN, domainValues);
/*     */   }
/*     */ 
/*     */   public COSArray eval(COSArray input)
/*     */     throws IOException
/*     */   {
/* 268 */     float[] outputValues = eval(input.toFloatArray());
/* 269 */     COSArray array = new COSArray();
/* 270 */     array.setFloatArray(outputValues);
/* 271 */     return array;
/*     */   }
/*     */ 
/*     */   public abstract float[] eval(float[] paramArrayOfFloat)
/*     */     throws IOException;
/*     */ 
/*     */   protected COSArray getRangeValues()
/*     */   {
/* 295 */     if (this.range == null)
/*     */     {
/* 297 */       this.range = ((COSArray)getDictionary().getDictionaryObject(COSName.RANGE));
/*     */     }
/* 299 */     return this.range;
/*     */   }
/*     */ 
/*     */   private COSArray getDomainValues()
/*     */   {
/* 309 */     if (this.domain == null)
/*     */     {
/* 311 */       this.domain = ((COSArray)getDictionary().getDictionaryObject(COSName.DOMAIN));
/*     */     }
/* 313 */     return this.domain;
/*     */   }
/*     */ 
/*     */   protected float[] clipToRange(float[] inputValues)
/*     */   {
/* 324 */     COSArray rangesArray = getRangeValues();
/*     */     float[] result;
/* 326 */     if (rangesArray != null)
/*     */     {
/* 328 */       float[] rangeValues = rangesArray.toFloatArray();
/* 329 */       int numberOfRanges = rangeValues.length / 2;
/* 330 */       float[] result = new float[numberOfRanges];
/* 331 */       for (int i = 0; i < numberOfRanges; i++)
/*     */       {
/* 333 */         int index = i << 1;
/* 334 */         result[i] = clipToRange(inputValues[i], rangeValues[index], rangeValues[(index + 1)]);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 339 */       result = inputValues;
/*     */     }
/* 341 */     return result;
/*     */   }
/*     */ 
/*     */   protected float clipToRange(float x, float rangeMin, float rangeMax)
/*     */   {
/* 355 */     if (x < rangeMin)
/*     */     {
/* 357 */       return rangeMin;
/*     */     }
/* 359 */     if (x > rangeMax)
/*     */     {
/* 361 */       return rangeMax;
/*     */     }
/* 363 */     return x;
/*     */   }
/*     */ 
/*     */   protected float interpolate(float x, float xRangeMin, float xRangeMax, float yRangeMin, float yRangeMax)
/*     */   {
/* 380 */     return yRangeMin + (x - xRangeMin) * (yRangeMax - yRangeMin) / (xRangeMax - xRangeMin);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.function.PDFunction
 * JD-Core Version:    0.6.2
 */