/*     */ package org.apache.pdfbox.pdmodel.graphics.shading;
/*     */ 
/*     */ import java.awt.geom.AffineTransform;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.util.Matrix;
/*     */ 
/*     */ public class PDShadingType1 extends PDShadingResources
/*     */ {
/*  35 */   private COSArray domain = null;
/*     */ 
/*     */   public PDShadingType1(COSDictionary shadingDictionary)
/*     */   {
/*  44 */     super(shadingDictionary);
/*     */   }
/*     */ 
/*     */   public int getShadingType()
/*     */   {
/*  53 */     return 1;
/*     */   }
/*     */ 
/*     */   public Matrix getMatrix()
/*     */   {
/*  63 */     Matrix retval = null;
/*  64 */     COSArray array = (COSArray)getCOSDictionary().getDictionaryObject(COSName.MATRIX);
/*  65 */     if (array != null)
/*     */     {
/*  67 */       retval = new Matrix();
/*  68 */       retval.setValue(0, 0, ((COSNumber)array.get(0)).floatValue());
/*  69 */       retval.setValue(0, 1, ((COSNumber)array.get(1)).floatValue());
/*  70 */       retval.setValue(1, 0, ((COSNumber)array.get(2)).floatValue());
/*  71 */       retval.setValue(1, 1, ((COSNumber)array.get(3)).floatValue());
/*  72 */       retval.setValue(2, 0, ((COSNumber)array.get(4)).floatValue());
/*  73 */       retval.setValue(2, 1, ((COSNumber)array.get(5)).floatValue());
/*     */     }
/*  75 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setMatrix(AffineTransform transform)
/*     */   {
/*  85 */     COSArray matrix = new COSArray();
/*  86 */     double[] values = new double[6];
/*  87 */     transform.getMatrix(values);
/*  88 */     for (double v : values)
/*     */     {
/*  90 */       matrix.add(new COSFloat((float)v));
/*     */     }
/*  92 */     getCOSDictionary().setItem(COSName.MATRIX, matrix);
/*     */   }
/*     */ 
/*     */   public COSArray getDomain()
/*     */   {
/* 102 */     if (this.domain == null)
/*     */     {
/* 104 */       this.domain = ((COSArray)getCOSDictionary().getDictionaryObject(COSName.DOMAIN));
/*     */     }
/* 106 */     return this.domain;
/*     */   }
/*     */ 
/*     */   public void setDomain(COSArray newDomain)
/*     */   {
/* 116 */     this.domain = newDomain;
/* 117 */     getCOSDictionary().setItem(COSName.DOMAIN, newDomain);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.shading.PDShadingType1
 * JD-Core Version:    0.6.2
 */