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
/*     */ import org.apache.pdfbox.pdmodel.common.PDMatrix;
/*     */ 
/*     */ public class PDCalRGB extends PDColorSpace
/*     */ {
/*     */   public static final String NAME = "CalRGB";
/*     */   private COSArray array;
/*     */   private COSDictionary dictionary;
/*     */ 
/*     */   public PDCalRGB()
/*     */   {
/*  55 */     this.array = new COSArray();
/*  56 */     this.dictionary = new COSDictionary();
/*  57 */     this.array.add(COSName.CALRGB);
/*  58 */     this.array.add(this.dictionary);
/*     */   }
/*     */ 
/*     */   public PDCalRGB(COSArray rgb)
/*     */   {
/*  68 */     this.array = rgb;
/*  69 */     this.dictionary = ((COSDictionary)this.array.getObject(1));
/*     */   }
/*     */ 
/*     */   public int getNumberOfComponents()
/*     */     throws IOException
/*     */   {
/*  81 */     return 3;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  91 */     return "CalRGB";
/*     */   }
/*     */ 
/*     */   protected ColorSpace createColorSpace()
/*     */   {
/* 101 */     return new ColorSpaceCalRGB(getGamma(), getWhitepoint(), getBlackPoint(), getLinearInterpretation());
/*     */   }
/*     */ 
/*     */   public ColorModel createColorModel(int bpc)
/*     */     throws IOException
/*     */   {
/* 115 */     int[] nBits = { bpc, bpc, bpc };
/* 116 */     return new ComponentColorModel(getJavaColorSpace(), nBits, false, false, 1, 0);
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 131 */     return this.array;
/*     */   }
/*     */ 
/*     */   public PDTristimulus getWhitepoint()
/*     */   {
/* 143 */     COSArray wp = (COSArray)this.dictionary.getDictionaryObject(COSName.WHITE_POINT);
/* 144 */     if (wp == null)
/*     */     {
/* 146 */       wp = new COSArray();
/* 147 */       wp.add(new COSFloat(1.0F));
/* 148 */       wp.add(new COSFloat(1.0F));
/* 149 */       wp.add(new COSFloat(1.0F));
/* 150 */       this.dictionary.setItem(COSName.WHITE_POINT, wp);
/*     */     }
/* 152 */     return new PDTristimulus(wp);
/*     */   }
/*     */ 
/*     */   public void setWhitepoint(PDTristimulus wp)
/*     */   {
/* 163 */     COSBase wpArray = wp.getCOSObject();
/* 164 */     if (wpArray != null)
/*     */     {
/* 166 */       this.dictionary.setItem(COSName.WHITE_POINT, wpArray);
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
/* 186 */       this.dictionary.setItem(COSName.BLACK_POINT, bp);
/*     */     }
/* 188 */     return new PDTristimulus(bp);
/*     */   }
/*     */ 
/*     */   public void setBlackPoint(PDTristimulus bp)
/*     */   {
/* 200 */     COSBase bpArray = null;
/* 201 */     if (bp != null)
/*     */     {
/* 203 */       bpArray = bp.getCOSObject();
/*     */     }
/* 205 */     this.dictionary.setItem(COSName.BLACK_POINT, bpArray);
/*     */   }
/*     */ 
/*     */   public PDGamma getGamma()
/*     */   {
/* 216 */     COSArray gamma = (COSArray)this.dictionary.getDictionaryObject(COSName.GAMMA);
/* 217 */     if (gamma == null)
/*     */     {
/* 219 */       gamma = new COSArray();
/* 220 */       gamma.add(new COSFloat(1.0F));
/* 221 */       gamma.add(new COSFloat(1.0F));
/* 222 */       gamma.add(new COSFloat(1.0F));
/* 223 */       this.dictionary.setItem(COSName.GAMMA, gamma);
/*     */     }
/* 225 */     return new PDGamma(gamma);
/*     */   }
/*     */ 
/*     */   public void setGamma(PDGamma value)
/*     */   {
/* 235 */     COSArray gamma = null;
/* 236 */     if (value != null)
/*     */     {
/* 238 */       gamma = value.getCOSArray();
/*     */     }
/* 240 */     this.dictionary.setItem(COSName.GAMMA, gamma);
/*     */   }
/*     */ 
/*     */   public PDMatrix getLinearInterpretation()
/*     */   {
/* 252 */     PDMatrix retval = null;
/* 253 */     COSArray matrix = (COSArray)this.dictionary.getDictionaryObject(COSName.MATRIX);
/* 254 */     if (matrix == null)
/*     */     {
/* 256 */       retval = new PDMatrix();
/* 257 */       setLinearInterpretation(retval);
/*     */     }
/*     */     else
/*     */     {
/* 261 */       retval = new PDMatrix(matrix);
/*     */     }
/* 263 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setLinearInterpretation(PDMatrix matrix)
/*     */   {
/* 274 */     COSArray matrixArray = null;
/* 275 */     if (matrix != null)
/*     */     {
/* 277 */       matrixArray = matrix.getCOSArray();
/*     */     }
/* 279 */     this.dictionary.setItem(COSName.MATRIX, matrixArray);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.color.PDCalRGB
 * JD-Core Version:    0.6.2
 */