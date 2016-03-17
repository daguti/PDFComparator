/*     */ package org.apache.pdfbox.pdmodel.graphics.color;
/*     */ 
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.IndexColorModel;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNull;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ import org.apache.pdfbox.io.IOUtils;
/*     */ 
/*     */ public class PDIndexed extends PDColorSpace
/*     */ {
/*     */   public static final String NAME = "Indexed";
/*     */   public static final String ABBREVIATED_NAME = "I";
/*     */   private COSArray array;
/*  57 */   private PDColorSpace baseColorspace = null;
/*  58 */   private ColorModel baseColorModel = null;
/*     */   private byte[] lookupData;
/*     */   private byte[] indexedColorValues;
/*     */   private int indexNumOfComponents;
/*     */   private int maxIndex;
/*     */   private static final int INDEXED_BPC = 8;
/*     */ 
/*     */   public PDIndexed()
/*     */   {
/*  79 */     this.array = new COSArray();
/*  80 */     this.array.add(COSName.INDEXED);
/*  81 */     this.array.add(COSName.DEVICERGB);
/*  82 */     this.array.add(COSInteger.get(255L));
/*  83 */     this.array.add(COSNull.NULL);
/*     */   }
/*     */ 
/*     */   public PDIndexed(COSArray indexedArray)
/*     */   {
/*  93 */     this.array = indexedArray;
/*     */   }
/*     */ 
/*     */   public int getNumberOfComponents()
/*     */     throws IOException
/*     */   {
/* 106 */     return getBaseColorSpace().getNumberOfComponents();
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 116 */     return "Indexed";
/*     */   }
/*     */ 
/*     */   protected ColorSpace createColorSpace()
/*     */     throws IOException
/*     */   {
/* 128 */     return getBaseColorSpace().getJavaColorSpace();
/*     */   }
/*     */ 
/*     */   public ColorModel createColorModel(int bpc)
/*     */     throws IOException
/*     */   {
/* 142 */     return createColorModel(bpc, -1);
/*     */   }
/*     */ 
/*     */   public ColorModel createColorModel(int bpc, int mask)
/*     */     throws IOException
/*     */   {
/* 157 */     ColorModel colorModel = getBaseColorModel(8);
/* 158 */     calculateIndexedColorValues(colorModel, bpc);
/* 159 */     if (mask > -1)
/*     */     {
/* 161 */       return new IndexColorModel(bpc, this.maxIndex + 1, this.indexedColorValues, 0, colorModel.hasAlpha(), mask);
/*     */     }
/*     */ 
/* 165 */     return new IndexColorModel(bpc, this.maxIndex + 1, this.indexedColorValues, 0, colorModel.hasAlpha());
/*     */   }
/*     */ 
/*     */   public PDColorSpace getBaseColorSpace()
/*     */     throws IOException
/*     */   {
/* 178 */     if (this.baseColorspace == null)
/*     */     {
/* 180 */       COSBase base = this.array.getObject(1);
/* 181 */       this.baseColorspace = PDColorSpaceFactory.createColorSpace(base);
/*     */     }
/* 183 */     return this.baseColorspace;
/*     */   }
/*     */ 
/*     */   public void setBaseColorSpace(PDColorSpace base)
/*     */   {
/* 193 */     this.array.set(1, base.getCOSObject());
/* 194 */     this.baseColorspace = base;
/*     */   }
/*     */ 
/*     */   public int getHighValue()
/*     */   {
/* 204 */     return ((COSNumber)this.array.getObject(2)).intValue();
/*     */   }
/*     */ 
/*     */   public void setHighValue(int high)
/*     */   {
/* 214 */     this.array.set(2, high);
/*     */   }
/*     */ 
/*     */   public int lookupColor(int lookupIndex, int componentNumber)
/*     */     throws IOException
/*     */   {
/* 229 */     PDColorSpace baseColor = getBaseColorSpace();
/* 230 */     byte[] data = getLookupData();
/* 231 */     int numberOfComponents = baseColor.getNumberOfComponents();
/* 232 */     return (data[(lookupIndex * numberOfComponents + componentNumber)] + 256) % 256;
/*     */   }
/*     */ 
/*     */   public byte[] getLookupData()
/*     */     throws IOException
/*     */   {
/* 243 */     if (this.lookupData == null)
/*     */     {
/* 245 */       COSBase lookupTable = this.array.getObject(3);
/* 246 */       if ((lookupTable instanceof COSString))
/*     */       {
/* 248 */         this.lookupData = ((COSString)lookupTable).getBytes();
/*     */       }
/* 250 */       else if ((lookupTable instanceof COSStream))
/*     */       {
/* 254 */         COSStream lookupStream = (COSStream)lookupTable;
/* 255 */         InputStream input = lookupStream.getUnfilteredStream();
/* 256 */         ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
/* 257 */         byte[] buffer = new byte[1024];
/*     */         int amountRead;
/* 259 */         while ((amountRead = input.read(buffer, 0, buffer.length)) != -1)
/*     */         {
/* 261 */           output.write(buffer, 0, amountRead);
/*     */         }
/* 263 */         this.lookupData = output.toByteArray();
/* 264 */         IOUtils.closeQuietly(input);
/*     */       }
/* 266 */       else if (lookupTable == null)
/*     */       {
/* 268 */         this.lookupData = new byte[0];
/*     */       }
/*     */       else
/*     */       {
/* 272 */         throw new IOException("Error: Unknown type for lookup table " + lookupTable);
/*     */       }
/*     */     }
/* 275 */     return this.lookupData;
/*     */   }
/*     */ 
/*     */   public void setLookupColor(int lookupIndex, int componentNumber, int color)
/*     */     throws IOException
/*     */   {
/* 289 */     PDColorSpace baseColor = getBaseColorSpace();
/* 290 */     int numberOfComponents = baseColor.getNumberOfComponents();
/* 291 */     byte[] data = getLookupData();
/* 292 */     data[(lookupIndex * numberOfComponents + componentNumber)] = ((byte)color);
/* 293 */     COSString string = new COSString(data);
/* 294 */     this.array.set(3, string);
/*     */   }
/*     */ 
/*     */   public float[] calculateColorValues(int index)
/*     */     throws IOException
/*     */   {
/* 307 */     calculateIndexedColorValues(getBaseColorModel(8), 8);
/* 308 */     float[] colorValues = null;
/* 309 */     if (index < this.maxIndex)
/*     */     {
/* 311 */       int bufferIndex = index * this.indexNumOfComponents;
/* 312 */       colorValues = new float[this.indexNumOfComponents];
/* 313 */       for (int i = 0; i < this.indexNumOfComponents; i++)
/*     */       {
/* 315 */         colorValues[i] = this.indexedColorValues[(bufferIndex + i)];
/*     */       }
/*     */     }
/* 318 */     return colorValues;
/*     */   }
/*     */ 
/*     */   private ColorModel getBaseColorModel(int bpc) throws IOException
/*     */   {
/* 323 */     if (this.baseColorModel == null)
/*     */     {
/* 325 */       this.baseColorModel = getBaseColorSpace().createColorModel(bpc);
/* 326 */       if (this.baseColorModel.getTransferType() != 0)
/*     */       {
/* 328 */         throw new IOException("Not implemented");
/*     */       }
/*     */     }
/* 331 */     return this.baseColorModel;
/*     */   }
/*     */ 
/*     */   private void calculateIndexedColorValues(ColorModel colorModel, int bpc) throws IOException
/*     */   {
/* 336 */     if (this.indexedColorValues == null)
/*     */     {
/* 339 */       int numberOfColorValues = 1 << bpc;
/*     */ 
/* 341 */       int highValue = getHighValue();
/*     */ 
/* 344 */       this.maxIndex = Math.min(numberOfColorValues - 1, highValue);
/* 345 */       byte[] index = getLookupData();
/*     */ 
/* 347 */       int numberOfColorValuesFromIndex = index.length / this.baseColorModel.getNumComponents() - 1;
/* 348 */       this.maxIndex = Math.min(this.maxIndex, numberOfColorValuesFromIndex);
/*     */ 
/* 350 */       boolean hasAlpha = this.baseColorModel.hasAlpha();
/* 351 */       this.indexNumOfComponents = (3 + (hasAlpha ? 1 : 0));
/* 352 */       int buffersize = (this.maxIndex + 1) * this.indexNumOfComponents;
/* 353 */       this.indexedColorValues = new byte[buffersize];
/* 354 */       byte[] inData = new byte[this.baseColorModel.getNumComponents()];
/* 355 */       int bufferIndex = 0;
/* 356 */       for (int i = 0; i <= this.maxIndex; i++)
/*     */       {
/* 358 */         System.arraycopy(index, i * inData.length, inData, 0, inData.length);
/*     */ 
/* 360 */         this.indexedColorValues[bufferIndex] = ((byte)colorModel.getRed(inData));
/* 361 */         this.indexedColorValues[(bufferIndex + 1)] = ((byte)colorModel.getGreen(inData));
/* 362 */         this.indexedColorValues[(bufferIndex + 2)] = ((byte)colorModel.getBlue(inData));
/* 363 */         if (hasAlpha)
/*     */         {
/* 365 */           this.indexedColorValues[(bufferIndex + 3)] = ((byte)colorModel.getAlpha(inData));
/*     */         }
/* 367 */         bufferIndex += this.indexNumOfComponents;
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.color.PDIndexed
 * JD-Core Version:    0.6.2
 */