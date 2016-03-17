/*     */ package org.apache.pdfbox.pdmodel.graphics.pattern;
/*     */ 
/*     */ import java.awt.Paint;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.pdmodel.PDResources;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ import org.apache.pdfbox.util.Matrix;
/*     */ 
/*     */ public class PDTilingPatternResources extends PDPatternResources
/*     */ {
/*     */   public PDTilingPatternResources()
/*     */   {
/*  48 */     getCOSDictionary().setInt(COSName.PATTERN_TYPE, 1);
/*     */   }
/*     */ 
/*     */   public PDTilingPatternResources(COSDictionary resourceDictionary)
/*     */   {
/*  58 */     super(resourceDictionary);
/*     */   }
/*     */ 
/*     */   public int getPatternType()
/*     */   {
/*  66 */     return 1;
/*     */   }
/*     */ 
/*     */   public void setLength(int length)
/*     */   {
/*  76 */     getCOSDictionary().setInt(COSName.LENGTH, length);
/*     */   }
/*     */ 
/*     */   public int getLength()
/*     */   {
/*  86 */     return getCOSDictionary().getInt(COSName.LENGTH, 0);
/*     */   }
/*     */ 
/*     */   public void setPaintType(int paintType)
/*     */   {
/*  96 */     getCOSDictionary().setInt(COSName.PAINT_TYPE, paintType);
/*     */   }
/*     */ 
/*     */   public int getPaintType()
/*     */   {
/* 106 */     return getCOSDictionary().getInt(COSName.PAINT_TYPE, 0);
/*     */   }
/*     */ 
/*     */   public void setTilingType(int tilingType)
/*     */   {
/* 116 */     getCOSDictionary().setInt(COSName.TILING_TYPE, tilingType);
/*     */   }
/*     */ 
/*     */   public int getTilingType()
/*     */   {
/* 126 */     return getCOSDictionary().getInt(COSName.TILING_TYPE, 0);
/*     */   }
/*     */ 
/*     */   public void setXStep(int xStep)
/*     */   {
/* 136 */     getCOSDictionary().setInt(COSName.X_STEP, xStep);
/*     */   }
/*     */ 
/*     */   public int getXStep()
/*     */   {
/* 146 */     return getCOSDictionary().getInt(COSName.X_STEP, 0);
/*     */   }
/*     */ 
/*     */   public void setYStep(int yStep)
/*     */   {
/* 156 */     getCOSDictionary().setInt(COSName.Y_STEP, yStep);
/*     */   }
/*     */ 
/*     */   public int getYStep()
/*     */   {
/* 166 */     return getCOSDictionary().getInt(COSName.Y_STEP, 0);
/*     */   }
/*     */ 
/*     */   public PDResources getResources()
/*     */   {
/* 177 */     PDResources retval = null;
/* 178 */     COSDictionary resources = (COSDictionary)getCOSDictionary().getDictionaryObject(COSName.RESOURCES);
/* 179 */     if (resources != null)
/*     */     {
/* 181 */       retval = new PDResources(resources);
/*     */     }
/* 183 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setResources(PDResources resources)
/*     */   {
/* 193 */     if (resources != null)
/*     */     {
/* 195 */       getCOSDictionary().setItem(COSName.RESOURCES, resources);
/*     */     }
/*     */     else
/*     */     {
/* 199 */       getCOSDictionary().removeItem(COSName.RESOURCES);
/*     */     }
/*     */   }
/*     */ 
/*     */   public PDRectangle getBBox()
/*     */   {
/* 212 */     PDRectangle retval = null;
/* 213 */     COSArray array = (COSArray)getCOSDictionary().getDictionaryObject(COSName.BBOX);
/* 214 */     if (array != null)
/*     */     {
/* 216 */       retval = new PDRectangle(array);
/*     */     }
/* 218 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setBBox(PDRectangle bbox)
/*     */   {
/* 228 */     if (bbox == null)
/*     */     {
/* 230 */       getCOSDictionary().removeItem(COSName.BBOX);
/*     */     }
/*     */     else
/*     */     {
/* 234 */       getCOSDictionary().setItem(COSName.BBOX, bbox.getCOSArray());
/*     */     }
/*     */   }
/*     */ 
/*     */   public Matrix getMatrix()
/*     */   {
/* 245 */     Matrix retval = null;
/* 246 */     COSArray array = (COSArray)getCOSDictionary().getDictionaryObject(COSName.MATRIX);
/* 247 */     if (array != null)
/*     */     {
/* 249 */       retval = new Matrix();
/* 250 */       retval.setValue(0, 0, ((COSNumber)array.get(0)).floatValue());
/* 251 */       retval.setValue(0, 1, ((COSNumber)array.get(1)).floatValue());
/* 252 */       retval.setValue(1, 0, ((COSNumber)array.get(2)).floatValue());
/* 253 */       retval.setValue(1, 1, ((COSNumber)array.get(3)).floatValue());
/* 254 */       retval.setValue(2, 0, ((COSNumber)array.get(4)).floatValue());
/* 255 */       retval.setValue(2, 1, ((COSNumber)array.get(5)).floatValue());
/*     */     }
/* 257 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setMatrix(AffineTransform transform)
/*     */   {
/* 266 */     COSArray matrix = new COSArray();
/* 267 */     double[] values = new double[6];
/* 268 */     transform.getMatrix(values);
/* 269 */     for (double v : values)
/*     */     {
/* 271 */       matrix.add(new COSFloat((float)v));
/*     */     }
/* 273 */     getCOSDictionary().setItem(COSName.MATRIX, matrix);
/*     */   }
/*     */ 
/*     */   public Paint getPaint(int pageHeight)
/*     */     throws IOException
/*     */   {
/* 283 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.pattern.PDTilingPatternResources
 * JD-Core Version:    0.6.2
 */