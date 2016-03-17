/*     */ package org.apache.pdfbox.pdmodel.graphics.color;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.color.CMMException;
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.color.ICC_ColorSpace;
/*     */ import java.awt.color.ICC_Profile;
/*     */ import java.awt.color.ProfileDataException;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.ComponentColorModel;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.common.COSArrayList;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRange;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ 
/*     */ public class PDICCBased extends PDColorSpace
/*     */ {
/*  62 */   private static final Log LOG = LogFactory.getLog(PDICCBased.class);
/*     */   public static final String NAME = "ICCBased";
/*     */   private PDStream stream;
/*  75 */   private int numberOfComponents = -1;
/*     */ 
/*     */   public PDICCBased(PDDocument doc)
/*     */   {
/*  84 */     this.array = new COSArray();
/*  85 */     this.array.add(COSName.ICCBASED);
/*  86 */     this.array.add(new PDStream(doc));
/*     */   }
/*     */ 
/*     */   public PDICCBased(COSArray iccArray)
/*     */   {
/*  96 */     this.array = iccArray;
/*  97 */     this.stream = new PDStream((COSStream)iccArray.getObject(1));
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 107 */     return "ICCBased";
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 118 */     return this.array;
/*     */   }
/*     */ 
/*     */   public PDStream getPDStream()
/*     */   {
/* 128 */     return this.stream;
/*     */   }
/*     */ 
/*     */   protected ColorSpace createColorSpace()
/*     */     throws IOException
/*     */   {
/* 140 */     InputStream profile = null;
/* 141 */     ColorSpace cSpace = null;
/*     */     try
/*     */     {
/* 144 */       profile = this.stream.createInputStream();
/* 145 */       ICC_Profile iccProfile = ICC_Profile.getInstance(profile);
/* 146 */       cSpace = new ICC_ColorSpace(iccProfile);
/*     */ 
/* 150 */       new Color(cSpace, new float[getNumberOfComponents()], 1.0F);
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/* 154 */       if (((e instanceof ProfileDataException)) || ((e instanceof CMMException)) || ((e instanceof IllegalArgumentException)))
/*     */       {
/* 159 */         List alternateCSList = getAlternateColorSpaces();
/* 160 */         PDColorSpace alternate = (PDColorSpace)alternateCSList.get(0);
/* 161 */         LOG.error("Can't read ICC-profile, using alternate colorspace instead: " + alternate);
/* 162 */         cSpace = alternate.getJavaColorSpace();
/*     */       }
/*     */       else
/*     */       {
/* 166 */         throw e;
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 171 */       if (profile != null)
/*     */       {
/* 173 */         profile.close();
/*     */       }
/*     */     }
/* 176 */     return cSpace;
/*     */   }
/*     */ 
/*     */   public ColorModel createColorModel(int bpc)
/*     */     throws IOException
/*     */   {
/* 192 */     int numOfComponents = getNumberOfComponents();
/*     */     int[] nbBits;
/* 193 */     switch (numOfComponents)
/*     */     {
/*     */     case 1:
/* 197 */       nbBits = new int[] { bpc };
/* 198 */       break;
/*     */     case 3:
/* 201 */       nbBits = new int[] { bpc, bpc, bpc };
/* 202 */       break;
/*     */     case 4:
/* 205 */       nbBits = new int[] { bpc, bpc, bpc, bpc };
/* 206 */       break;
/*     */     case 2:
/*     */     default:
/* 208 */       throw new IOException("Unknown colorspace number of components:" + numOfComponents);
/*     */     }
/* 210 */     ComponentColorModel componentColorModel = new ComponentColorModel(getJavaColorSpace(), nbBits, false, false, 1, 0);
/*     */ 
/* 217 */     return componentColorModel;
/*     */   }
/*     */ 
/*     */   public int getNumberOfComponents()
/*     */   {
/* 229 */     if (this.numberOfComponents < 0)
/*     */     {
/* 231 */       this.numberOfComponents = this.stream.getStream().getInt(COSName.N);
/*     */     }
/* 233 */     return this.numberOfComponents;
/*     */   }
/*     */ 
/*     */   public void setNumberOfComponents(int n)
/*     */   {
/* 243 */     this.numberOfComponents = n;
/* 244 */     this.stream.getStream().setInt(COSName.N, n);
/*     */   }
/*     */ 
/*     */   public List getAlternateColorSpaces()
/*     */     throws IOException
/*     */   {
/* 257 */     COSBase alternate = this.stream.getStream().getDictionaryObject(COSName.ALTERNATE);
/* 258 */     COSArray alternateArray = null;
/* 259 */     if (alternate == null)
/*     */     {
/* 261 */       alternateArray = new COSArray();
/* 262 */       int numComponents = getNumberOfComponents();
/* 263 */       COSName csName = null;
/* 264 */       if (numComponents == 1)
/*     */       {
/* 266 */         csName = COSName.DEVICEGRAY;
/*     */       }
/* 268 */       else if (numComponents == 3)
/*     */       {
/* 270 */         csName = COSName.DEVICERGB;
/*     */       }
/* 272 */       else if (numComponents == 4)
/*     */       {
/* 274 */         csName = COSName.DEVICECMYK;
/*     */       }
/*     */       else
/*     */       {
/* 278 */         throw new IOException("Unknown colorspace number of components:" + numComponents);
/*     */       }
/* 280 */       alternateArray.add(csName);
/*     */     }
/* 284 */     else if ((alternate instanceof COSArray))
/*     */     {
/* 286 */       alternateArray = (COSArray)alternate;
/*     */     }
/* 288 */     else if ((alternate instanceof COSName))
/*     */     {
/* 290 */       alternateArray = new COSArray();
/* 291 */       alternateArray.add(alternate);
/*     */     }
/*     */     else
/*     */     {
/* 295 */       throw new IOException("Error: expected COSArray or COSName and not " + alternate.getClass().getName());
/*     */     }
/*     */ 
/* 299 */     List retval = new ArrayList();
/* 300 */     retval.add(PDColorSpaceFactory.createColorSpace(alternateArray));
/* 301 */     return new COSArrayList(retval, alternateArray);
/*     */   }
/*     */ 
/*     */   public void setAlternateColorSpaces(List list)
/*     */   {
/* 312 */     COSArray altArray = null;
/* 313 */     if (list != null)
/*     */     {
/* 315 */       altArray = COSArrayList.converterToCOSArray(list);
/*     */     }
/* 317 */     this.stream.getStream().setItem(COSName.ALTERNATE, altArray);
/*     */   }
/*     */ 
/*     */   public PDRange getRangeForComponent(int n)
/*     */   {
/* 329 */     COSArray rangeArray = (COSArray)this.stream.getStream().getDictionaryObject(COSName.RANGE);
/* 330 */     if ((rangeArray == null) || (rangeArray.size() < getNumberOfComponents() * 2))
/*     */     {
/* 332 */       return new PDRange();
/*     */     }
/* 334 */     return new PDRange(rangeArray, n);
/*     */   }
/*     */ 
/*     */   public void setRangeForComponent(PDRange range, int n)
/*     */   {
/* 345 */     COSArray rangeArray = (COSArray)this.stream.getStream().getDictionaryObject(COSName.RANGE);
/* 346 */     if (rangeArray == null)
/*     */     {
/* 348 */       rangeArray = new COSArray();
/* 349 */       this.stream.getStream().setItem(COSName.RANGE, rangeArray);
/*     */     }
/*     */ 
/* 352 */     while (rangeArray.size() < (n + 1) * 2)
/*     */     {
/* 354 */       rangeArray.add(new COSFloat(0.0F));
/* 355 */       rangeArray.add(new COSFloat(1.0F));
/*     */     }
/* 357 */     rangeArray.set(n * 2, new COSFloat(range.getMin()));
/* 358 */     rangeArray.set(n * 2 + 1, new COSFloat(range.getMax()));
/*     */   }
/*     */ 
/*     */   public COSStream getMetadata()
/*     */   {
/* 369 */     return (COSStream)this.stream.getStream().getDictionaryObject(COSName.METADATA);
/*     */   }
/*     */ 
/*     */   public void setMetadata(COSStream metadata)
/*     */   {
/* 379 */     this.stream.getStream().setItem(COSName.METADATA, metadata);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 389 */     return getName() + "{numberOfComponents: " + getNumberOfComponents() + "}";
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.color.PDICCBased
 * JD-Core Version:    0.6.2
 */