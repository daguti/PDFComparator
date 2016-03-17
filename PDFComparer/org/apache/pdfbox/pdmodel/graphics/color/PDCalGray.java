/*     */ package org.apache.pdfbox.pdmodel.graphics.color;
/*     */ 
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ 
/*     */ public class PDCalGray extends PDColorSpace
/*     */ {
/*     */   public static final String NAME = "CalGray";
/*     */   private COSArray array;
/*     */   private COSDictionary dictionary;
/*     */ 
/*     */   public PDCalGray()
/*     */   {
/*  52 */     this.array = new COSArray();
/*  53 */     this.dictionary = new COSDictionary();
/*  54 */     this.array.add(COSName.CALGRAY);
/*  55 */     this.array.add(this.dictionary);
/*     */   }
/*     */ 
/*     */   public PDCalGray(COSArray gray)
/*     */   {
/*  65 */     this.array = gray;
/*  66 */     this.dictionary = ((COSDictionary)this.array.getObject(1));
/*     */   }
/*     */ 
/*     */   public int getNumberOfComponents()
/*     */     throws IOException
/*     */   {
/*  78 */     return 1;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  88 */     return "CalGray";
/*     */   }
/*     */ 
/*     */   protected ColorSpace createColorSpace()
/*     */     throws IOException
/*     */   {
/* 100 */     throw new IOException("Not implemented");
/*     */   }
/*     */ 
/*     */   public ColorModel createColorModel(int bpc)
/*     */     throws IOException
/*     */   {
/* 114 */     throw new IOException("Not implemented");
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 124 */     return this.array;
/*     */   }
/*     */ 
/*     */   public float getGamma()
/*     */   {
/* 135 */     float retval = 1.0F;
/* 136 */     COSNumber gamma = (COSNumber)this.dictionary.getDictionaryObject(COSName.GAMMA);
/* 137 */     if (gamma != null)
/*     */     {
/* 139 */       retval = gamma.floatValue();
/*     */     }
/* 141 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setGamma(float value)
/*     */   {
/* 151 */     this.dictionary.setItem(COSName.GAMMA, new COSFloat(value));
/*     */   }
/*     */ 
/*     */   public PDTristimulus getWhitepoint()
/*     */   {
/* 163 */     COSArray wp = (COSArray)this.dictionary.getDictionaryObject(COSName.WHITE_POINT);
/* 164 */     if (wp == null)
/*     */     {
/* 166 */       wp = new COSArray();
/* 167 */       wp.add(new COSFloat(1.0F));
/* 168 */       wp.add(new COSFloat(1.0F));
/* 169 */       wp.add(new COSFloat(1.0F));
/* 170 */       this.dictionary.setItem(COSName.WHITE_POINT, wp);
/*     */     }
/* 172 */     return new PDTristimulus(wp);
/*     */   }
/*     */ 
/*     */   public void setWhitepoint(PDTristimulus wp)
/*     */   {
/* 183 */     COSBase wpArray = wp.getCOSObject();
/* 184 */     if (wpArray != null)
/*     */     {
/* 186 */       this.dictionary.setItem(COSName.WHITE_POINT, wpArray);
/*     */     }
/*     */   }
/*     */ 
/*     */   public PDTristimulus getBlackPoint()
/*     */   {
/* 199 */     COSArray bp = (COSArray)this.dictionary.getDictionaryObject(COSName.BLACK_POINT);
/* 200 */     if (bp == null)
/*     */     {
/* 202 */       bp = new COSArray();
/* 203 */       bp.add(new COSFloat(0.0F));
/* 204 */       bp.add(new COSFloat(0.0F));
/* 205 */       bp.add(new COSFloat(0.0F));
/* 206 */       this.dictionary.setItem(COSName.BLACK_POINT, bp);
/*     */     }
/* 208 */     return new PDTristimulus(bp);
/*     */   }
/*     */ 
/*     */   public void setBlackPoint(PDTristimulus bp)
/*     */   {
/* 219 */     COSBase bpArray = null;
/* 220 */     if (bp != null)
/*     */     {
/* 222 */       bpArray = bp.getCOSObject();
/*     */     }
/* 224 */     this.dictionary.setItem(COSName.BLACK_POINT, bpArray);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.color.PDCalGray
 * JD-Core Version:    0.6.2
 */