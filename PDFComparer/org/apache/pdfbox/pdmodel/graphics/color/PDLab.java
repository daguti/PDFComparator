/*     */ package org.apache.pdfbox.pdmodel.graphics.color;
/*     */ 
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.ComponentColorModel;
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRange;
/*     */ 
/*     */ public class PDLab extends PDColorSpace
/*     */ {
/*     */   public static final String NAME = "Lab";
/*     */   private COSArray array;
/*     */   private COSDictionary dictionary;
/*     */ 
/*     */   public PDLab()
/*     */   {
/*  56 */     this.array = new COSArray();
/*  57 */     this.dictionary = new COSDictionary();
/*  58 */     this.array.add(COSName.LAB);
/*  59 */     this.array.add(this.dictionary);
/*     */   }
/*     */ 
/*     */   public PDLab(COSArray lab)
/*     */   {
/*  69 */     this.array = lab;
/*  70 */     this.dictionary = ((COSDictionary)this.array.getObject(1));
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  80 */     return "Lab";
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  90 */     return this.array;
/*     */   }
/*     */ 
/*     */   protected ColorSpace createColorSpace()
/*     */     throws IOException
/*     */   {
/* 102 */     return new ColorSpaceLab(getWhitePoint(), getBlackPoint(), getARange(), getBRange());
/*     */   }
/*     */ 
/*     */   public ColorModel createColorModel(int bpc)
/*     */     throws IOException
/*     */   {
/* 116 */     int[] nBits = { bpc, bpc, bpc };
/* 117 */     return new ComponentColorModel(getJavaColorSpace(), nBits, false, false, 1, 0);
/*     */   }
/*     */ 
/*     */   public int getNumberOfComponents()
/*     */     throws IOException
/*     */   {
/* 134 */     return 3;
/*     */   }
/*     */ 
/*     */   public PDTristimulus getWhitePoint()
/*     */   {
/* 145 */     COSArray wp = (COSArray)this.dictionary.getDictionaryObject(COSName.WHITE_POINT);
/* 146 */     if (wp == null)
/*     */     {
/* 148 */       wp = new COSArray();
/* 149 */       wp.add(new COSFloat(1.0F));
/* 150 */       wp.add(new COSFloat(1.0F));
/* 151 */       wp.add(new COSFloat(1.0F));
/*     */     }
/* 153 */     return new PDTristimulus(wp);
/*     */   }
/*     */ 
/*     */   public void setWhitePoint(PDTristimulus wp)
/*     */   {
/* 164 */     COSBase wpArray = wp.getCOSObject();
/* 165 */     if (wpArray != null)
/*     */     {
/* 167 */       this.dictionary.setItem(COSName.WHITE_POINT, wpArray);
/*     */     }
/*     */   }
/*     */ 
/*     */   public PDTristimulus getBlackPoint()
/*     */   {
/* 179 */     COSArray bp = (COSArray)this.dictionary.getDictionaryObject(COSName.BLACK_POINT);
/* 180 */     if (bp == null)
/*     */     {
/* 182 */       bp = new COSArray();
/* 183 */       bp.add(new COSFloat(0.0F));
/* 184 */       bp.add(new COSFloat(0.0F));
/* 185 */       bp.add(new COSFloat(0.0F));
/*     */     }
/* 187 */     return new PDTristimulus(bp);
/*     */   }
/*     */ 
/*     */   public void setBlackPoint(PDTristimulus bp)
/*     */   {
/* 198 */     COSBase bpArray = null;
/* 199 */     if (bp != null)
/*     */     {
/* 201 */       bpArray = bp.getCOSObject();
/*     */     }
/* 203 */     this.dictionary.setItem(COSName.BLACK_POINT, bpArray);
/*     */   }
/*     */ 
/*     */   private COSArray getDefaultRangeArray()
/*     */   {
/* 212 */     COSArray range = new COSArray();
/* 213 */     range.add(new COSFloat(-100.0F));
/* 214 */     range.add(new COSFloat(100.0F));
/* 215 */     range.add(new COSFloat(-100.0F));
/* 216 */     range.add(new COSFloat(100.0F));
/* 217 */     return range;
/*     */   }
/*     */ 
/*     */   public PDRange getARange()
/*     */   {
/* 227 */     COSArray rangeArray = (COSArray)this.dictionary.getDictionaryObject(COSName.RANGE);
/* 228 */     if (rangeArray == null)
/*     */     {
/* 230 */       rangeArray = getDefaultRangeArray();
/*     */     }
/* 232 */     return new PDRange(rangeArray, 0);
/*     */   }
/*     */ 
/*     */   public void setARange(PDRange range)
/*     */   {
/* 242 */     COSArray rangeArray = (COSArray)this.dictionary.getDictionaryObject(COSName.RANGE);
/* 243 */     if (rangeArray == null)
/*     */     {
/* 245 */       rangeArray = getDefaultRangeArray();
/*     */     }
/*     */ 
/* 248 */     if (range == null)
/*     */     {
/* 250 */       rangeArray.set(0, new COSFloat(-100.0F));
/* 251 */       rangeArray.set(1, new COSFloat(100.0F));
/*     */     }
/*     */     else
/*     */     {
/* 255 */       rangeArray.set(0, new COSFloat(range.getMin()));
/* 256 */       rangeArray.set(1, new COSFloat(range.getMax()));
/*     */     }
/* 258 */     this.dictionary.setItem(COSName.RANGE, rangeArray);
/*     */   }
/*     */ 
/*     */   public PDRange getBRange()
/*     */   {
/* 268 */     COSArray rangeArray = (COSArray)this.dictionary.getDictionaryObject(COSName.RANGE);
/* 269 */     if (rangeArray == null)
/*     */     {
/* 271 */       rangeArray = getDefaultRangeArray();
/*     */     }
/* 273 */     return new PDRange(rangeArray, 1);
/*     */   }
/*     */ 
/*     */   public void setBRange(PDRange range)
/*     */   {
/* 283 */     COSArray rangeArray = (COSArray)this.dictionary.getDictionaryObject(COSName.RANGE);
/* 284 */     if (rangeArray == null)
/*     */     {
/* 286 */       rangeArray = getDefaultRangeArray();
/*     */     }
/*     */ 
/* 289 */     if (range == null)
/*     */     {
/* 291 */       rangeArray.set(2, new COSFloat(-100.0F));
/* 292 */       rangeArray.set(3, new COSFloat(100.0F));
/*     */     }
/*     */     else
/*     */     {
/* 296 */       rangeArray.set(2, new COSFloat(range.getMin()));
/* 297 */       rangeArray.set(3, new COSFloat(range.getMax()));
/*     */     }
/* 299 */     this.dictionary.setItem(COSName.RANGE, rangeArray);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.color.PDLab
 * JD-Core Version:    0.6.2
 */