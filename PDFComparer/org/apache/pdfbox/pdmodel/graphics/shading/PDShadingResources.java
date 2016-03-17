/*     */ package org.apache.pdfbox.pdmodel.graphics.shading;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ import org.apache.pdfbox.pdmodel.common.function.PDFunction;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpaceFactory;
/*     */ 
/*     */ public abstract class PDShadingResources
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary dictionary;
/*  39 */   private COSArray background = null;
/*  40 */   private PDRectangle bBox = null;
/*  41 */   private PDColorSpace colorspace = null;
/*  42 */   private PDFunction function = null;
/*  43 */   private PDFunction[] functionArray = null;
/*     */   public static final int SHADING_TYPE1 = 1;
/*     */   public static final int SHADING_TYPE2 = 2;
/*     */   public static final int SHADING_TYPE3 = 3;
/*     */   public static final int SHADING_TYPE4 = 4;
/*     */   public static final int SHADING_TYPE5 = 5;
/*     */   public static final int SHADING_TYPE6 = 6;
/*     */   public static final int SHADING_TYPE7 = 7;
/*     */ 
/*     */   public PDShadingResources()
/*     */   {
/*  79 */     this.dictionary = new COSDictionary();
/*     */   }
/*     */ 
/*     */   public PDShadingResources(COSDictionary shadingDictionary)
/*     */   {
/*  89 */     this.dictionary = shadingDictionary;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/*  99 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 109 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 119 */     return COSName.SHADING.getName();
/*     */   }
/*     */ 
/*     */   public void setShadingType(int shadingType)
/*     */   {
/* 129 */     this.dictionary.setInt(COSName.SHADING_TYPE, shadingType);
/*     */   }
/*     */ 
/*     */   public abstract int getShadingType();
/*     */ 
/*     */   public void setBackground(COSArray newBackground)
/*     */   {
/* 146 */     this.background = newBackground;
/* 147 */     this.dictionary.setItem(COSName.BACKGROUND, newBackground);
/*     */   }
/*     */ 
/*     */   public COSArray getBackground()
/*     */   {
/* 157 */     if (this.background == null)
/*     */     {
/* 159 */       this.background = ((COSArray)this.dictionary.getDictionaryObject(COSName.BACKGROUND));
/*     */     }
/* 161 */     return this.background;
/*     */   }
/*     */ 
/*     */   public PDRectangle getBBox()
/*     */   {
/* 173 */     if (this.bBox == null)
/*     */     {
/* 175 */       COSArray array = (COSArray)this.dictionary.getDictionaryObject(COSName.BBOX);
/* 176 */       if (array != null)
/*     */       {
/* 178 */         this.bBox = new PDRectangle(array);
/*     */       }
/*     */     }
/* 181 */     return this.bBox;
/*     */   }
/*     */ 
/*     */   public void setBBox(PDRectangle newBBox)
/*     */   {
/* 191 */     this.bBox = newBBox;
/* 192 */     if (this.bBox == null)
/*     */     {
/* 194 */       this.dictionary.removeItem(COSName.BBOX);
/*     */     }
/*     */     else
/*     */     {
/* 198 */       this.dictionary.setItem(COSName.BBOX, this.bBox.getCOSArray());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setAntiAlias(boolean antiAlias)
/*     */   {
/* 209 */     this.dictionary.setBoolean(COSName.ANTI_ALIAS, antiAlias);
/*     */   }
/*     */ 
/*     */   public boolean getAntiAlias()
/*     */   {
/* 219 */     return this.dictionary.getBoolean(COSName.ANTI_ALIAS, false);
/*     */   }
/*     */ 
/*     */   public PDColorSpace getColorSpace()
/*     */     throws IOException
/*     */   {
/* 231 */     if (this.colorspace == null)
/*     */     {
/* 233 */       COSBase colorSpaceDictionary = this.dictionary.getDictionaryObject(COSName.CS, COSName.COLORSPACE);
/* 234 */       this.colorspace = PDColorSpaceFactory.createColorSpace(colorSpaceDictionary);
/*     */     }
/* 236 */     return this.colorspace;
/*     */   }
/*     */ 
/*     */   public void setColorSpace(PDColorSpace newColorspace)
/*     */   {
/* 246 */     this.colorspace = newColorspace;
/* 247 */     if (newColorspace != null)
/*     */     {
/* 249 */       this.dictionary.setItem(COSName.COLORSPACE, newColorspace.getCOSObject());
/*     */     }
/*     */     else
/*     */     {
/* 253 */       this.dictionary.removeItem(COSName.COLORSPACE);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static PDShadingResources create(COSDictionary resourceDictionary)
/*     */     throws IOException
/*     */   {
/* 268 */     PDShadingResources shading = null;
/* 269 */     int shadingType = resourceDictionary.getInt(COSName.SHADING_TYPE, 0);
/* 270 */     switch (shadingType)
/*     */     {
/*     */     case 1:
/* 273 */       shading = new PDShadingType1(resourceDictionary);
/* 274 */       break;
/*     */     case 2:
/* 276 */       shading = new PDShadingType2(resourceDictionary);
/* 277 */       break;
/*     */     case 3:
/* 279 */       shading = new PDShadingType3(resourceDictionary);
/* 280 */       break;
/*     */     case 4:
/* 282 */       shading = new PDShadingType4(resourceDictionary);
/* 283 */       break;
/*     */     case 5:
/* 285 */       shading = new PDShadingType5(resourceDictionary);
/* 286 */       break;
/*     */     case 6:
/* 288 */       shading = new PDShadingType6(resourceDictionary);
/* 289 */       break;
/*     */     case 7:
/* 291 */       shading = new PDShadingType7(resourceDictionary);
/* 292 */       break;
/*     */     default:
/* 294 */       throw new IOException("Error: Unknown shading type " + shadingType);
/*     */     }
/* 296 */     return shading;
/*     */   }
/*     */ 
/*     */   public void setFunction(PDFunction newFunction)
/*     */   {
/* 306 */     this.functionArray = null;
/* 307 */     this.function = newFunction;
/* 308 */     if (newFunction == null)
/*     */     {
/* 310 */       getCOSDictionary().removeItem(COSName.FUNCTION);
/*     */     }
/*     */     else
/*     */     {
/* 314 */       getCOSDictionary().setItem(COSName.FUNCTION, newFunction);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setFunction(COSArray newFunctions)
/*     */   {
/* 325 */     this.functionArray = null;
/* 326 */     this.function = null;
/* 327 */     if (newFunctions == null)
/*     */     {
/* 329 */       getCOSDictionary().removeItem(COSName.FUNCTION);
/*     */     }
/*     */     else
/*     */     {
/* 333 */       getCOSDictionary().setItem(COSName.FUNCTION, newFunctions);
/*     */     }
/*     */   }
/*     */ 
/*     */   public PDFunction getFunction()
/*     */     throws IOException
/*     */   {
/* 345 */     if (this.function == null)
/*     */     {
/* 347 */       COSBase dictionaryFunctionObject = getCOSDictionary().getDictionaryObject(COSName.FUNCTION);
/* 348 */       if (dictionaryFunctionObject != null)
/*     */       {
/* 350 */         this.function = PDFunction.create(dictionaryFunctionObject);
/*     */       }
/*     */     }
/* 353 */     return this.function;
/*     */   }
/*     */ 
/*     */   private PDFunction[] getFunctionsArray()
/*     */     throws IOException
/*     */   {
/* 364 */     if (this.functionArray == null)
/*     */     {
/* 366 */       COSBase functionObject = getCOSDictionary().getDictionaryObject(COSName.FUNCTION);
/* 367 */       if ((functionObject instanceof COSDictionary))
/*     */       {
/* 369 */         this.functionArray = new PDFunction[1];
/* 370 */         this.functionArray[0] = PDFunction.create(functionObject);
/*     */       }
/*     */       else
/*     */       {
/* 374 */         COSArray functionCOSArray = (COSArray)functionObject;
/* 375 */         int numberOfFunctions = functionCOSArray.size();
/* 376 */         this.functionArray = new PDFunction[numberOfFunctions];
/* 377 */         for (int i = 0; i < numberOfFunctions; i++)
/*     */         {
/* 379 */           this.functionArray[i] = PDFunction.create(functionCOSArray.get(i));
/*     */         }
/*     */       }
/*     */     }
/* 383 */     return this.functionArray;
/*     */   }
/*     */ 
/*     */   public float[] evalFunction(float inputValue)
/*     */     throws IOException
/*     */   {
/* 395 */     return evalFunction(new float[] { inputValue });
/*     */   }
/*     */ 
/*     */   public float[] evalFunction(float[] input)
/*     */     throws IOException
/*     */   {
/* 410 */     PDFunction[] functions = getFunctionsArray();
/* 411 */     int numberOfFunctions = functions.length;
/* 412 */     float[] returnValues = null;
/* 413 */     if (numberOfFunctions == 1)
/*     */     {
/* 415 */       returnValues = functions[0].eval(input);
/*     */     }
/*     */     else
/*     */     {
/* 419 */       returnValues = new float[numberOfFunctions];
/* 420 */       for (int i = 0; i < numberOfFunctions; i++)
/*     */       {
/* 422 */         float[] newValue = functions[i].eval(input);
/* 423 */         returnValues[i] = newValue[0];
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 429 */     for (int i = 0; i < returnValues.length; i++)
/*     */     {
/* 431 */       if (returnValues[i] < 0.0F)
/*     */       {
/* 433 */         returnValues[i] = 0.0F;
/*     */       }
/* 435 */       else if (returnValues[i] > 1.0F)
/*     */       {
/* 437 */         returnValues[i] = 1.0F;
/*     */       }
/*     */     }
/* 440 */     return returnValues;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.shading.PDShadingResources
 * JD-Core Version:    0.6.2
 */