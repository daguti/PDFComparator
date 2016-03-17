/*     */ package org.apache.pdfbox.pdmodel.graphics.pattern;
/*     */ 
/*     */ import java.awt.Paint;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.io.IOException;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.pdmodel.graphics.PDExtendedGraphicsState;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.AxialShadingPaint;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.PDShadingResources;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.PDShadingType1;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.PDShadingType2;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.PDShadingType3;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.PDShadingType4;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.PDShadingType5;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.PDShadingType6;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.PDShadingType7;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.RadialShadingPaint;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.Type1ShadingPaint;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.Type4ShadingPaint;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.Type5ShadingPaint;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.Type6ShadingPaint;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.Type7ShadingPaint;
/*     */ import org.apache.pdfbox.util.Matrix;
/*     */ 
/*     */ public class PDShadingPatternResources extends PDPatternResources
/*     */ {
/*     */   private PDExtendedGraphicsState extendedGraphicsState;
/*     */   private PDShadingResources shading;
/*  57 */   private COSArray matrix = null;
/*     */ 
/*  62 */   private static final Log LOG = LogFactory.getLog(PDShadingPatternResources.class);
/*     */ 
/*     */   public PDShadingPatternResources()
/*     */   {
/*  70 */     getCOSDictionary().setInt(COSName.PATTERN_TYPE, 2);
/*     */   }
/*     */ 
/*     */   public PDShadingPatternResources(COSDictionary resourceDictionary)
/*     */   {
/*  80 */     super(resourceDictionary);
/*     */   }
/*     */ 
/*     */   public int getPatternType()
/*     */   {
/*  88 */     return 2;
/*     */   }
/*     */ 
/*     */   public Matrix getMatrix()
/*     */   {
/*  98 */     Matrix returnMatrix = null;
/*  99 */     if (this.matrix == null)
/*     */     {
/* 101 */       this.matrix = ((COSArray)getCOSDictionary().getDictionaryObject(COSName.MATRIX));
/*     */     }
/* 103 */     if (this.matrix != null)
/*     */     {
/* 105 */       returnMatrix = new Matrix();
/* 106 */       returnMatrix.setValue(0, 0, ((COSNumber)this.matrix.get(0)).floatValue());
/* 107 */       returnMatrix.setValue(0, 1, ((COSNumber)this.matrix.get(1)).floatValue());
/* 108 */       returnMatrix.setValue(1, 0, ((COSNumber)this.matrix.get(2)).floatValue());
/* 109 */       returnMatrix.setValue(1, 1, ((COSNumber)this.matrix.get(3)).floatValue());
/* 110 */       returnMatrix.setValue(2, 0, ((COSNumber)this.matrix.get(4)).floatValue());
/* 111 */       returnMatrix.setValue(2, 1, ((COSNumber)this.matrix.get(5)).floatValue());
/*     */     }
/* 113 */     return returnMatrix;
/*     */   }
/*     */ 
/*     */   public void setMatrix(AffineTransform transform)
/*     */   {
/* 122 */     this.matrix = new COSArray();
/* 123 */     double[] values = new double[6];
/* 124 */     transform.getMatrix(values);
/* 125 */     for (double v : values)
/*     */     {
/* 127 */       this.matrix.add(new COSFloat((float)v));
/*     */     }
/* 129 */     getCOSDictionary().setItem(COSName.MATRIX, this.matrix);
/*     */   }
/*     */ 
/*     */   public PDExtendedGraphicsState getExtendedGraphicsState()
/*     */   {
/* 139 */     if (this.extendedGraphicsState == null)
/*     */     {
/* 141 */       COSDictionary dictionary = (COSDictionary)getCOSDictionary().getDictionaryObject(COSName.EXT_G_STATE);
/* 142 */       if (dictionary != null)
/*     */       {
/* 144 */         this.extendedGraphicsState = new PDExtendedGraphicsState(dictionary);
/*     */       }
/*     */     }
/* 147 */     return this.extendedGraphicsState;
/*     */   }
/*     */ 
/*     */   public void setExtendedGraphicsState(PDExtendedGraphicsState extendedGraphicsState)
/*     */   {
/* 157 */     this.extendedGraphicsState = extendedGraphicsState;
/* 158 */     if (extendedGraphicsState != null)
/*     */     {
/* 160 */       getCOSDictionary().setItem(COSName.EXT_G_STATE, extendedGraphicsState);
/*     */     }
/*     */     else
/*     */     {
/* 164 */       getCOSDictionary().removeItem(COSName.EXT_G_STATE);
/*     */     }
/*     */   }
/*     */ 
/*     */   public PDShadingResources getShading()
/*     */     throws IOException
/*     */   {
/* 177 */     if (this.shading == null)
/*     */     {
/* 179 */       COSDictionary dictionary = (COSDictionary)getCOSDictionary().getDictionaryObject(COSName.SHADING);
/* 180 */       if (dictionary != null)
/*     */       {
/* 182 */         this.shading = PDShadingResources.create(dictionary);
/*     */       }
/*     */     }
/* 185 */     return this.shading;
/*     */   }
/*     */ 
/*     */   public void setShading(PDShadingResources shadingResources)
/*     */   {
/* 195 */     this.shading = shadingResources;
/* 196 */     if (shadingResources != null)
/*     */     {
/* 198 */       getCOSDictionary().setItem(COSName.SHADING, shadingResources);
/*     */     }
/*     */     else
/*     */     {
/* 202 */       getCOSDictionary().removeItem(COSName.SHADING);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Paint getPaint(int pageHeight)
/*     */     throws IOException
/*     */   {
/* 211 */     Paint paint = null;
/* 212 */     PDShadingResources shadingResources = getShading();
/* 213 */     int shadingType = shadingResources != null ? shadingResources.getShadingType() : 0;
/* 214 */     switch (shadingType)
/*     */     {
/*     */     case 1:
/* 217 */       paint = new Type1ShadingPaint((PDShadingType1)getShading(), getMatrix(), pageHeight);
/* 218 */       break;
/*     */     case 2:
/* 220 */       paint = new AxialShadingPaint((PDShadingType2)getShading(), getMatrix(), pageHeight);
/* 221 */       break;
/*     */     case 3:
/* 223 */       paint = new RadialShadingPaint((PDShadingType3)getShading(), getMatrix(), pageHeight);
/* 224 */       break;
/*     */     case 4:
/* 226 */       paint = new Type4ShadingPaint((PDShadingType4)getShading(), getMatrix(), pageHeight);
/* 227 */       break;
/*     */     case 5:
/* 229 */       paint = new Type5ShadingPaint((PDShadingType5)getShading(), getMatrix(), pageHeight);
/* 230 */       break;
/*     */     case 6:
/* 232 */       paint = new Type6ShadingPaint((PDShadingType6)getShading(), getMatrix(), pageHeight);
/* 233 */       break;
/*     */     case 7:
/* 235 */       paint = new Type7ShadingPaint((PDShadingType7)getShading(), getMatrix(), pageHeight);
/* 236 */       break;
/*     */     default:
/* 238 */       throw new IOException("Error: Unknown shading type " + shadingType);
/*     */     }
/* 240 */     return paint;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.pattern.PDShadingPatternResources
 * JD-Core Version:    0.6.2
 */