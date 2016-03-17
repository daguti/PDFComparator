/*     */ package org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObject;
/*     */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
/*     */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationUnknown;
/*     */ 
/*     */ public class PDObjectReference
/*     */   implements COSObjectable
/*     */ {
/*     */   public static final String TYPE = "OBJR";
/*     */   private COSDictionary dictionary;
/*     */ 
/*     */   protected COSDictionary getCOSDictionary()
/*     */   {
/*  52 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   public PDObjectReference()
/*     */   {
/*  61 */     this.dictionary = new COSDictionary();
/*  62 */     this.dictionary.setName(COSName.TYPE, "OBJR");
/*     */   }
/*     */ 
/*     */   public PDObjectReference(COSDictionary theDictionary)
/*     */   {
/*  72 */     this.dictionary = theDictionary;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  80 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   public COSObjectable getReferencedObject()
/*     */   {
/*  92 */     COSBase obj = getCOSDictionary().getDictionaryObject(COSName.OBJ);
/*  93 */     if (!(obj instanceof COSDictionary))
/*     */     {
/*  95 */       return null;
/*     */     }
/*     */     try
/*     */     {
/*  99 */       PDXObject xobject = PDXObject.createXObject(obj);
/* 100 */       if (xobject != null)
/*     */       {
/* 102 */         return xobject;
/*     */       }
/* 104 */       COSDictionary objDictionary = (COSDictionary)obj;
/* 105 */       PDAnnotation annotation = PDAnnotation.createAnnotation(obj);
/*     */ 
/* 112 */       if ((!(annotation instanceof PDAnnotationUnknown)) || (COSName.ANNOT.equals(objDictionary.getDictionaryObject(COSName.TYPE))))
/*     */       {
/* 115 */         return annotation;
/*     */       }
/*     */     }
/*     */     catch (IOException exception)
/*     */     {
/*     */     }
/*     */ 
/* 122 */     return null;
/*     */   }
/*     */ 
/*     */   public void setReferencedObject(PDAnnotation annotation)
/*     */   {
/* 132 */     getCOSDictionary().setItem(COSName.OBJ, annotation);
/*     */   }
/*     */ 
/*     */   public void setReferencedObject(PDXObject xobject)
/*     */   {
/* 142 */     getCOSDictionary().setItem(COSName.OBJ, xobject);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDObjectReference
 * JD-Core Version:    0.6.2
 */