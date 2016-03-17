/*     */ package org.apache.pdfbox.pdmodel.common.function;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRange;
/*     */ 
/*     */ public class PDFunctionType3 extends PDFunction
/*     */ {
/*  35 */   private COSArray functions = null;
/*  36 */   private COSArray encode = null;
/*  37 */   private COSArray bounds = null;
/*     */ 
/*     */   public PDFunctionType3(COSBase functionStream)
/*     */   {
/*  46 */     super(functionStream);
/*     */   }
/*     */ 
/*     */   public int getFunctionType()
/*     */   {
/*  54 */     return 3;
/*     */   }
/*     */ 
/*     */   public float[] eval(float[] input)
/*     */     throws IOException
/*     */   {
/*  65 */     PDFunction function = null;
/*  66 */     float x = input[0];
/*  67 */     PDRange domain = getDomainForInput(0);
/*     */ 
/*  69 */     x = clipToRange(x, domain.getMin(), domain.getMax());
/*     */ 
/*  71 */     COSArray functionsArray = getFunctions();
/*  72 */     int numberOfFunctions = functionsArray.size();
/*     */ 
/*  74 */     if (numberOfFunctions == 1)
/*     */     {
/*  76 */       function = PDFunction.create(functionsArray.get(0));
/*  77 */       PDRange encRange = getEncodeForParameter(0);
/*  78 */       x = interpolate(x, domain.getMin(), domain.getMax(), encRange.getMin(), encRange.getMax());
/*     */     }
/*     */     else
/*     */     {
/*  82 */       float[] boundsValues = getBounds().toFloatArray();
/*  83 */       int boundsSize = boundsValues.length;
/*     */ 
/*  86 */       float[] partitionValues = new float[boundsSize + 2];
/*  87 */       int partitionValuesSize = partitionValues.length;
/*  88 */       partitionValues[0] = domain.getMin();
/*  89 */       partitionValues[(partitionValuesSize - 1)] = domain.getMax();
/*  90 */       System.arraycopy(boundsValues, 0, partitionValues, 1, boundsSize);
/*     */ 
/*  92 */       for (int i = 0; i < partitionValuesSize - 1; i++)
/*     */       {
/*  94 */         if ((x >= partitionValues[i]) && ((x < partitionValues[(i + 1)]) || ((i == partitionValuesSize - 2) && (x == partitionValues[(i + 1)]))))
/*     */         {
/*  97 */           function = PDFunction.create(functionsArray.get(i));
/*  98 */           PDRange encRange = getEncodeForParameter(i);
/*  99 */           x = interpolate(x, partitionValues[i], partitionValues[(i + 1)], encRange.getMin(), encRange.getMax());
/* 100 */           break;
/*     */         }
/*     */       }
/*     */     }
/* 104 */     float[] functionValues = { x };
/*     */ 
/* 106 */     float[] functionResult = function.eval(functionValues);
/*     */ 
/* 108 */     return clipToRange(functionResult);
/*     */   }
/*     */ 
/*     */   public COSArray getFunctions()
/*     */   {
/* 118 */     if (this.functions == null)
/*     */     {
/* 120 */       this.functions = ((COSArray)getDictionary().getDictionaryObject(COSName.FUNCTIONS));
/*     */     }
/* 122 */     return this.functions;
/*     */   }
/*     */ 
/*     */   public COSArray getBounds()
/*     */   {
/* 132 */     if (this.bounds == null)
/*     */     {
/* 134 */       this.bounds = ((COSArray)getDictionary().getDictionaryObject(COSName.BOUNDS));
/*     */     }
/* 136 */     return this.bounds;
/*     */   }
/*     */ 
/*     */   public COSArray getEncode()
/*     */   {
/* 146 */     if (this.encode == null)
/*     */     {
/* 148 */       this.encode = ((COSArray)getDictionary().getDictionaryObject(COSName.ENCODE));
/*     */     }
/* 150 */     return this.encode;
/*     */   }
/*     */ 
/*     */   private PDRange getEncodeForParameter(int n)
/*     */   {
/* 162 */     COSArray encodeValues = getEncode();
/* 163 */     return new PDRange(encodeValues, n);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.function.PDFunctionType3
 * JD-Core Version:    0.6.2
 */