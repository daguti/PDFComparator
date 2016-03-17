/*     */ package org.apache.pdfbox.pdmodel.graphics.xobject;
/*     */ 
/*     */ import java.awt.geom.AffineTransform;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdmodel.PDResources;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ import org.apache.pdfbox.util.Matrix;
/*     */ 
/*     */ public class PDXObjectForm extends PDXObject
/*     */ {
/*     */   public static final String SUB_TYPE = "Form";
/*     */ 
/*     */   public PDXObjectForm(PDStream formStream)
/*     */   {
/*  52 */     super(formStream);
/*  53 */     getCOSStream().setName(COSName.SUBTYPE, "Form");
/*     */   }
/*     */ 
/*     */   public PDXObjectForm(COSStream formStream)
/*     */   {
/*  63 */     super(formStream);
/*  64 */     getCOSStream().setName(COSName.SUBTYPE, "Form");
/*     */   }
/*     */ 
/*     */   public int getFormType()
/*     */   {
/*  74 */     return getCOSStream().getInt("FormType", 1);
/*     */   }
/*     */ 
/*     */   public void setFormType(int formType)
/*     */   {
/*  84 */     getCOSStream().setInt("FormType", formType);
/*     */   }
/*     */ 
/*     */   public PDResources getResources()
/*     */   {
/*  95 */     PDResources retval = null;
/*  96 */     COSDictionary resources = (COSDictionary)getCOSStream().getDictionaryObject(COSName.RESOURCES);
/*  97 */     if (resources != null)
/*     */     {
/*  99 */       retval = new PDResources(resources);
/*     */     }
/* 101 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setResources(PDResources resources)
/*     */   {
/* 111 */     getCOSStream().setItem(COSName.RESOURCES, resources);
/*     */   }
/*     */ 
/*     */   public PDRectangle getBBox()
/*     */   {
/* 123 */     PDRectangle retval = null;
/* 124 */     COSArray array = (COSArray)getCOSStream().getDictionaryObject(COSName.BBOX);
/* 125 */     if (array != null)
/*     */     {
/* 127 */       retval = new PDRectangle(array);
/*     */     }
/* 129 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setBBox(PDRectangle bbox)
/*     */   {
/* 139 */     if (bbox == null)
/*     */     {
/* 141 */       getCOSStream().removeItem(COSName.BBOX);
/*     */     }
/*     */     else
/*     */     {
/* 145 */       getCOSStream().setItem(COSName.BBOX, bbox.getCOSArray());
/*     */     }
/*     */   }
/*     */ 
/*     */   public Matrix getMatrix()
/*     */   {
/* 156 */     Matrix retval = null;
/* 157 */     COSArray array = (COSArray)getCOSStream().getDictionaryObject(COSName.MATRIX);
/* 158 */     if (array != null)
/*     */     {
/* 160 */       retval = new Matrix();
/* 161 */       retval.setValue(0, 0, ((COSNumber)array.get(0)).floatValue());
/* 162 */       retval.setValue(0, 1, ((COSNumber)array.get(1)).floatValue());
/* 163 */       retval.setValue(1, 0, ((COSNumber)array.get(2)).floatValue());
/* 164 */       retval.setValue(1, 1, ((COSNumber)array.get(3)).floatValue());
/* 165 */       retval.setValue(2, 0, ((COSNumber)array.get(4)).floatValue());
/* 166 */       retval.setValue(2, 1, ((COSNumber)array.get(5)).floatValue());
/*     */     }
/* 168 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setMatrix(AffineTransform transform)
/*     */   {
/* 178 */     COSArray matrix = new COSArray();
/* 179 */     double[] values = new double[6];
/* 180 */     transform.getMatrix(values);
/* 181 */     for (double v : values)
/*     */     {
/* 183 */       matrix.add(new COSFloat((float)v));
/*     */     }
/* 185 */     getCOSStream().setItem(COSName.MATRIX, matrix);
/*     */   }
/*     */ 
/*     */   public int getStructParents()
/*     */   {
/* 196 */     return getCOSStream().getInt(COSName.STRUCT_PARENTS, 0);
/*     */   }
/*     */ 
/*     */   public void setStructParents(int structParent)
/*     */   {
/* 206 */     getCOSStream().setInt(COSName.STRUCT_PARENTS, structParent);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectForm
 * JD-Core Version:    0.6.2
 */