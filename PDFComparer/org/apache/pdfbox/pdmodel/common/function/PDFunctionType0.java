/*     */ package org.apache.pdfbox.pdmodel.common.function;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import javax.imageio.stream.ImageInputStream;
/*     */ import javax.imageio.stream.MemoryCacheImageInputStream;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRange;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ 
/*     */ public class PDFunctionType0 extends PDFunction
/*     */ {
/*  44 */   private static final Log LOG = LogFactory.getLog(PDFunctionType0.class);
/*     */ 
/*  51 */   private COSArray encode = null;
/*     */ 
/*  57 */   private COSArray decode = null;
/*     */ 
/*  62 */   private COSArray size = null;
/*     */ 
/*  66 */   private int[][] samples = (int[][])null;
/*     */ 
/*     */   public PDFunctionType0(COSBase function)
/*     */   {
/*  75 */     super(function);
/*     */   }
/*     */ 
/*     */   public int getFunctionType()
/*     */   {
/*  84 */     return 0;
/*     */   }
/*     */ 
/*     */   public COSArray getSize()
/*     */   {
/*  95 */     if (this.size == null)
/*     */     {
/*  97 */       this.size = ((COSArray)getDictionary().getDictionaryObject(COSName.SIZE));
/*     */     }
/*  99 */     return this.size;
/*     */   }
/*     */ 
/*     */   public int[][] getSamples()
/*     */   {
/* 109 */     if (this.samples == null)
/*     */     {
/* 111 */       int arraySize = 1;
/* 112 */       int numberOfInputValues = getNumberOfInputParameters();
/* 113 */       int numberOfOutputValues = getNumberOfOutputParameters();
/* 114 */       COSArray sizes = getSize();
/* 115 */       for (int i = 0; i < numberOfInputValues; i++)
/*     */       {
/* 117 */         arraySize *= sizes.getInt(i);
/*     */       }
/* 119 */       this.samples = new int[arraySize][numberOfOutputValues];
/* 120 */       int bitsPerSample = getBitsPerSample();
/* 121 */       int index = 0;
/*     */       try
/*     */       {
/* 128 */         ImageInputStream mciis = new MemoryCacheImageInputStream(getPDStream().createInputStream());
/* 129 */         for (int i = 0; i < arraySize; i++)
/*     */         {
/* 131 */           for (int k = 0; k < numberOfOutputValues; k++)
/*     */           {
/* 134 */             this.samples[index][k] = ((int)mciis.readBits(bitsPerSample));
/*     */           }
/* 136 */           index++;
/*     */         }
/* 138 */         mciis.close();
/*     */       }
/*     */       catch (IOException exception)
/*     */       {
/* 142 */         LOG.error("IOException while reading the sample values of this function.", exception);
/*     */       }
/*     */     }
/* 145 */     return this.samples;
/*     */   }
/*     */ 
/*     */   public int getBitsPerSample()
/*     */   {
/* 157 */     return getDictionary().getInt(COSName.BITS_PER_SAMPLE);
/*     */   }
/*     */ 
/*     */   public int getOrder()
/*     */   {
/* 169 */     return getDictionary().getInt(COSName.ORDER, 1);
/*     */   }
/*     */ 
/*     */   public void setBitsPerSample(int bps)
/*     */   {
/* 180 */     getDictionary().setInt(COSName.BITS_PER_SAMPLE, bps);
/*     */   }
/*     */ 
/*     */   private COSArray getEncodeValues()
/*     */   {
/* 190 */     if (this.encode == null)
/*     */     {
/* 192 */       this.encode = ((COSArray)getDictionary().getDictionaryObject(COSName.ENCODE));
/*     */ 
/* 194 */       if (this.encode == null)
/*     */       {
/* 196 */         this.encode = new COSArray();
/* 197 */         COSArray sizeValues = getSize();
/* 198 */         int sizeValuesSize = sizeValues.size();
/* 199 */         for (int i = 0; i < sizeValuesSize; i++)
/*     */         {
/* 201 */           this.encode.add(COSInteger.ZERO);
/* 202 */           this.encode.add(COSInteger.get(sizeValues.getInt(i) - 1));
/*     */         }
/*     */       }
/*     */     }
/* 206 */     return this.encode;
/*     */   }
/*     */ 
/*     */   private COSArray getDecodeValues()
/*     */   {
/* 216 */     if (this.decode == null)
/*     */     {
/* 218 */       this.decode = ((COSArray)getDictionary().getDictionaryObject(COSName.DECODE));
/*     */ 
/* 220 */       if (this.decode == null)
/*     */       {
/* 222 */         this.decode = getRangeValues();
/*     */       }
/*     */     }
/* 225 */     return this.decode;
/*     */   }
/*     */ 
/*     */   public PDRange getEncodeForParameter(int paramNum)
/*     */   {
/* 237 */     PDRange retval = null;
/* 238 */     COSArray encodeValues = getEncodeValues();
/* 239 */     if ((encodeValues != null) && (encodeValues.size() >= paramNum * 2 + 1))
/*     */     {
/* 241 */       retval = new PDRange(encodeValues, paramNum);
/*     */     }
/* 243 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setEncodeValues(COSArray encodeValues)
/*     */   {
/* 253 */     this.encode = encodeValues;
/* 254 */     getDictionary().setItem(COSName.ENCODE, encodeValues);
/*     */   }
/*     */ 
/*     */   public PDRange getDecodeForParameter(int paramNum)
/*     */   {
/* 266 */     PDRange retval = null;
/* 267 */     COSArray decodeValues = getDecodeValues();
/* 268 */     if ((decodeValues != null) && (decodeValues.size() >= paramNum * 2 + 1))
/*     */     {
/* 270 */       retval = new PDRange(decodeValues, paramNum);
/*     */     }
/* 272 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setDecodeValues(COSArray decodeValues)
/*     */   {
/* 282 */     this.decode = decodeValues;
/* 283 */     getDictionary().setItem(COSName.DECODE, decodeValues);
/*     */   }
/*     */ 
/*     */   private int calcSampleIndex(int[] vector)
/*     */   {
/* 297 */     float[] sizeValues = getSize().toFloatArray();
/* 298 */     int index = 0;
/* 299 */     int sizeProduct = 1;
/* 300 */     int dimension = vector.length;
/* 301 */     for (int i = dimension - 2; i >= 0; i--)
/*     */     {
/* 303 */       sizeProduct = (int)(sizeProduct * sizeValues[i]);
/*     */     }
/* 305 */     for (int i = dimension - 1; i >= 0; i--)
/*     */     {
/* 307 */       index += sizeProduct * vector[i];
/* 308 */       if (i - 1 >= 0)
/*     */       {
/* 310 */         sizeProduct = (int)(sizeProduct / sizeValues[(i - 1)]);
/*     */       }
/*     */     }
/* 313 */     return index;
/*     */   }
/*     */ 
/*     */   public float[] eval(float[] input)
/*     */     throws IOException
/*     */   {
/* 428 */     float[] sizeValues = getSize().toFloatArray();
/* 429 */     int bitsPerSample = getBitsPerSample();
/* 430 */     float maxSample = (float)(Math.pow(2.0D, bitsPerSample) - 1.0D);
/* 431 */     int numberOfInputValues = input.length;
/* 432 */     int numberOfOutputValues = getNumberOfOutputParameters();
/*     */ 
/* 434 */     int[] inputPrev = new int[numberOfInputValues];
/* 435 */     int[] inputNext = new int[numberOfInputValues];
/*     */ 
/* 437 */     for (int i = 0; i < numberOfInputValues; i++)
/*     */     {
/* 439 */       PDRange domain = getDomainForInput(i);
/* 440 */       PDRange encodeValues = getEncodeForParameter(i);
/* 441 */       input[i] = clipToRange(input[i], domain.getMin(), domain.getMax());
/* 442 */       input[i] = interpolate(input[i], domain.getMin(), domain.getMax(), encodeValues.getMin(), encodeValues.getMax());
/*     */ 
/* 444 */       input[i] = clipToRange(input[i], 0.0F, sizeValues[i] - 1.0F);
/* 445 */       inputPrev[i] = ((int)Math.floor(input[i]));
/* 446 */       inputNext[i] = ((int)Math.ceil(input[i]));
/*     */     }
/*     */ 
/* 499 */     float[] outputValues = new Rinterpol(input, inputPrev, inputNext).rinterpolate();
/*     */ 
/* 501 */     for (int i = 0; i < numberOfOutputValues; i++)
/*     */     {
/* 503 */       PDRange range = getRangeForOutput(i);
/* 504 */       PDRange decodeValues = getDecodeForParameter(i);
/* 505 */       outputValues[i] = interpolate(outputValues[i], 0.0F, maxSample, decodeValues.getMin(), decodeValues.getMax());
/* 506 */       outputValues[i] = clipToRange(outputValues[i], range.getMin(), range.getMax());
/*     */     }
/*     */ 
/* 509 */     return outputValues;
/*     */   }
/*     */ 
/*     */   class Rinterpol
/*     */   {
/*     */     final float[] in;
/*     */     final int[] inPrev;
/*     */     final int[] inNext;
/*     */     final int numberOfInputValues;
/* 332 */     final int numberOfOutputValues = PDFunctionType0.this.getNumberOfOutputParameters();
/*     */ 
/*     */     public Rinterpol(float[] input, int[] inputPrev, int[] inputNext)
/*     */     {
/* 344 */       this.in = input;
/* 345 */       this.inPrev = inputPrev;
/* 346 */       this.inNext = inputNext;
/* 347 */       this.numberOfInputValues = input.length;
/*     */     }
/*     */ 
/*     */     public float[] rinterpolate()
/*     */     {
/* 357 */       return rinterpol(new int[this.numberOfInputValues], 0);
/*     */     }
/*     */ 
/*     */     private float[] rinterpol(int[] coord, int step)
/*     */     {
/* 372 */       float[] resultSample = new float[this.numberOfOutputValues];
/* 373 */       if (step == this.in.length - 1)
/*     */       {
/* 377 */         if (this.inPrev[step] == this.inNext[step])
/*     */         {
/* 379 */           coord[step] = this.inPrev[step];
/* 380 */           int[] tmpSample = PDFunctionType0.this.getSamples()[PDFunctionType0.this.calcSampleIndex(coord)];
/* 381 */           for (int i = 0; i < this.numberOfOutputValues; i++)
/*     */           {
/* 383 */             resultSample[i] = tmpSample[i];
/*     */           }
/* 385 */           return resultSample;
/*     */         }
/* 387 */         coord[step] = this.inPrev[step];
/* 388 */         int[] sample1 = PDFunctionType0.this.getSamples()[PDFunctionType0.this.calcSampleIndex(coord)];
/* 389 */         coord[step] = this.inNext[step];
/* 390 */         int[] sample2 = PDFunctionType0.this.getSamples()[PDFunctionType0.this.calcSampleIndex(coord)];
/* 391 */         for (int i = 0; i < this.numberOfOutputValues; i++)
/*     */         {
/* 393 */           resultSample[i] = PDFunctionType0.this.interpolate(this.in[step], this.inPrev[step], this.inNext[step], sample1[i], sample2[i]);
/*     */         }
/* 395 */         return resultSample;
/*     */       }
/*     */ 
/* 401 */       if (this.inPrev[step] == this.inNext[step])
/*     */       {
/* 403 */         coord[step] = this.inPrev[step];
/* 404 */         return rinterpol(coord, step + 1);
/*     */       }
/* 406 */       coord[step] = this.inPrev[step];
/* 407 */       float[] sample1 = rinterpol(coord, step + 1);
/* 408 */       coord[step] = this.inNext[step];
/* 409 */       float[] sample2 = rinterpol(coord, step + 1);
/* 410 */       for (int i = 0; i < this.numberOfOutputValues; i++)
/*     */       {
/* 412 */         resultSample[i] = PDFunctionType0.this.interpolate(this.in[step], this.inPrev[step], this.inNext[step], sample1[i], sample2[i]);
/*     */       }
/* 414 */       return resultSample;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.function.PDFunctionType0
 * JD-Core Version:    0.6.2
 */