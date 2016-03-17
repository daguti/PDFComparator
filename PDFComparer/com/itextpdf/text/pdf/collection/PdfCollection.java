/*     */ package com.itextpdf.text.pdf.collection;
/*     */ 
/*     */ import com.itextpdf.text.pdf.PdfDictionary;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfString;
/*     */ 
/*     */ public class PdfCollection extends PdfDictionary
/*     */ {
/*     */   public static final int DETAILS = 0;
/*     */   public static final int TILE = 1;
/*     */   public static final int HIDDEN = 2;
/*     */   public static final int CUSTOM = 3;
/*     */ 
/*     */   public PdfCollection(int type)
/*     */   {
/*  70 */     super(PdfName.COLLECTION);
/*  71 */     switch (type) {
/*     */     case 1:
/*  73 */       put(PdfName.VIEW, PdfName.T);
/*  74 */       break;
/*     */     case 2:
/*  76 */       put(PdfName.VIEW, PdfName.H);
/*  77 */       break;
/*     */     case 3:
/*  79 */       put(PdfName.VIEW, PdfName.C);
/*  80 */       break;
/*     */     default:
/*  82 */       put(PdfName.VIEW, PdfName.D);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setInitialDocument(String description)
/*     */   {
/*  92 */     put(PdfName.D, new PdfString(description, null));
/*     */   }
/*     */ 
/*     */   public void setSchema(PdfCollectionSchema schema)
/*     */   {
/* 100 */     put(PdfName.SCHEMA, schema);
/*     */   }
/*     */ 
/*     */   public PdfCollectionSchema getSchema()
/*     */   {
/* 108 */     return (PdfCollectionSchema)get(PdfName.SCHEMA);
/*     */   }
/*     */ 
/*     */   public void setSort(PdfCollectionSort sort)
/*     */   {
/* 116 */     put(PdfName.SORT, sort);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.collection.PdfCollection
 * JD-Core Version:    0.6.2
 */