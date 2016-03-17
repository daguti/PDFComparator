/*     */ package org.apache.pdfbox.pdmodel.interactive.annotation;
/*     */ 
/*     */ import java.awt.geom.AffineTransform;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdmodel.PDResources;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ import org.apache.pdfbox.util.Matrix;
/*     */ 
/*     */ public class PDAppearanceStream
/*     */   implements COSObjectable
/*     */ {
/*  44 */   private COSStream stream = null;
/*     */ 
/*     */   public PDAppearanceStream(COSStream s)
/*     */   {
/*  54 */     this.stream = s;
/*     */   }
/*     */ 
/*     */   public COSStream getStream()
/*     */   {
/*  64 */     return this.stream;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  72 */     return this.stream;
/*     */   }
/*     */ 
/*     */   public PDRectangle getBoundingBox()
/*     */   {
/*  83 */     PDRectangle box = null;
/*  84 */     COSArray bbox = (COSArray)this.stream.getDictionaryObject(COSName.BBOX);
/*  85 */     if (bbox != null)
/*     */     {
/*  87 */       box = new PDRectangle(bbox);
/*     */     }
/*  89 */     return box;
/*     */   }
/*     */ 
/*     */   public void setBoundingBox(PDRectangle rectangle)
/*     */   {
/*  99 */     COSArray array = null;
/* 100 */     if (rectangle != null)
/*     */     {
/* 102 */       array = rectangle.getCOSArray();
/*     */     }
/* 104 */     this.stream.setItem(COSName.BBOX, array);
/*     */   }
/*     */ 
/*     */   public PDResources getResources()
/*     */   {
/* 114 */     PDResources retval = null;
/* 115 */     COSDictionary dict = (COSDictionary)this.stream.getDictionaryObject(COSName.RESOURCES);
/* 116 */     if (dict != null)
/*     */     {
/* 118 */       retval = new PDResources(dict);
/*     */     }
/* 120 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setResources(PDResources resources)
/*     */   {
/* 130 */     COSDictionary dict = null;
/* 131 */     if (resources != null)
/*     */     {
/* 133 */       dict = resources.getCOSDictionary();
/*     */     }
/* 135 */     this.stream.setItem(COSName.RESOURCES, dict);
/*     */   }
/*     */ 
/*     */   public Matrix getMatrix()
/*     */   {
/* 145 */     Matrix retval = null;
/* 146 */     COSArray array = (COSArray)this.stream.getDictionaryObject(COSName.MATRIX);
/* 147 */     if (array != null)
/*     */     {
/* 149 */       retval = new Matrix();
/* 150 */       retval.setValue(0, 0, ((COSNumber)array.get(0)).floatValue());
/* 151 */       retval.setValue(0, 1, ((COSNumber)array.get(1)).floatValue());
/* 152 */       retval.setValue(1, 0, ((COSNumber)array.get(2)).floatValue());
/* 153 */       retval.setValue(1, 1, ((COSNumber)array.get(3)).floatValue());
/* 154 */       retval.setValue(2, 0, ((COSNumber)array.get(4)).floatValue());
/* 155 */       retval.setValue(2, 1, ((COSNumber)array.get(5)).floatValue());
/*     */     }
/* 157 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setMatrix(AffineTransform transform)
/*     */   {
/* 166 */     if (transform != null)
/*     */     {
/* 168 */       COSArray matrix = new COSArray();
/* 169 */       double[] values = new double[6];
/* 170 */       transform.getMatrix(values);
/* 171 */       for (double v : values)
/*     */       {
/* 173 */         matrix.add(new COSFloat((float)v));
/*     */       }
/* 175 */       this.stream.setItem(COSName.MATRIX, matrix);
/*     */     }
/*     */     else
/*     */     {
/* 179 */       this.stream.removeItem(COSName.MATRIX);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceStream
 * JD-Core Version:    0.6.2
 */