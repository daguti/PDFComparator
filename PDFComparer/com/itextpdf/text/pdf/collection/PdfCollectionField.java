/*     */ package com.itextpdf.text.pdf.collection;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.pdf.PdfBoolean;
/*     */ import com.itextpdf.text.pdf.PdfDate;
/*     */ import com.itextpdf.text.pdf.PdfDictionary;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfNumber;
/*     */ import com.itextpdf.text.pdf.PdfObject;
/*     */ import com.itextpdf.text.pdf.PdfString;
/*     */ 
/*     */ public class PdfCollectionField extends PdfDictionary
/*     */ {
/*     */   public static final int TEXT = 0;
/*     */   public static final int DATE = 1;
/*     */   public static final int NUMBER = 2;
/*     */   public static final int FILENAME = 3;
/*     */   public static final int DESC = 4;
/*     */   public static final int MODDATE = 5;
/*     */   public static final int CREATIONDATE = 6;
/*     */   public static final int SIZE = 7;
/*     */   protected int fieldType;
/*     */ 
/*     */   public PdfCollectionField(String name, int type)
/*     */   {
/*  90 */     super(PdfName.COLLECTIONFIELD);
/*  91 */     put(PdfName.N, new PdfString(name, "UnicodeBig"));
/*  92 */     this.fieldType = type;
/*  93 */     switch (type) {
/*     */     default:
/*  95 */       put(PdfName.SUBTYPE, PdfName.S);
/*  96 */       break;
/*     */     case 1:
/*  98 */       put(PdfName.SUBTYPE, PdfName.D);
/*  99 */       break;
/*     */     case 2:
/* 101 */       put(PdfName.SUBTYPE, PdfName.N);
/* 102 */       break;
/*     */     case 3:
/* 104 */       put(PdfName.SUBTYPE, PdfName.F);
/* 105 */       break;
/*     */     case 4:
/* 107 */       put(PdfName.SUBTYPE, PdfName.DESC);
/* 108 */       break;
/*     */     case 5:
/* 110 */       put(PdfName.SUBTYPE, PdfName.MODDATE);
/* 111 */       break;
/*     */     case 6:
/* 113 */       put(PdfName.SUBTYPE, PdfName.CREATIONDATE);
/* 114 */       break;
/*     */     case 7:
/* 116 */       put(PdfName.SUBTYPE, PdfName.SIZE);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setOrder(int i)
/*     */   {
/* 126 */     put(PdfName.O, new PdfNumber(i));
/*     */   }
/*     */ 
/*     */   public void setVisible(boolean visible)
/*     */   {
/* 134 */     put(PdfName.V, new PdfBoolean(visible));
/*     */   }
/*     */ 
/*     */   public void setEditable(boolean editable)
/*     */   {
/* 142 */     put(PdfName.E, new PdfBoolean(editable));
/*     */   }
/*     */ 
/*     */   public boolean isCollectionItem()
/*     */   {
/* 149 */     switch (this.fieldType) {
/*     */     case 0:
/*     */     case 1:
/*     */     case 2:
/* 153 */       return true;
/*     */     }
/* 155 */     return false;
/*     */   }
/*     */ 
/*     */   public PdfObject getValue(String v)
/*     */   {
/* 164 */     switch (this.fieldType) {
/*     */     case 0:
/* 166 */       return new PdfString(v, "UnicodeBig");
/*     */     case 1:
/* 168 */       return new PdfDate(PdfDate.decode(v));
/*     */     case 2:
/* 170 */       return new PdfNumber(v);
/*     */     }
/* 172 */     throw new IllegalArgumentException(MessageLocalization.getComposedMessage("1.is.not.an.acceptable.value.for.the.field.2", new Object[] { v, get(PdfName.N).toString() }));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.collection.PdfCollectionField
 * JD-Core Version:    0.6.2
 */