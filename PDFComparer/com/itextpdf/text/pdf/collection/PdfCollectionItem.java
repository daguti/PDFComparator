/*     */ package com.itextpdf.text.pdf.collection;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.pdf.PdfDate;
/*     */ import com.itextpdf.text.pdf.PdfDictionary;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfNumber;
/*     */ import com.itextpdf.text.pdf.PdfObject;
/*     */ import com.itextpdf.text.pdf.PdfString;
/*     */ import java.util.Calendar;
/*     */ 
/*     */ public class PdfCollectionItem extends PdfDictionary
/*     */ {
/*     */   PdfCollectionSchema schema;
/*     */ 
/*     */   public PdfCollectionItem(PdfCollectionSchema schema)
/*     */   {
/*  66 */     super(PdfName.COLLECTIONITEM);
/*  67 */     this.schema = schema;
/*     */   }
/*     */ 
/*     */   public void addItem(String key, String value)
/*     */   {
/*  75 */     PdfName fieldname = new PdfName(key);
/*  76 */     PdfCollectionField field = (PdfCollectionField)this.schema.get(fieldname);
/*  77 */     put(fieldname, field.getValue(value));
/*     */   }
/*     */ 
/*     */   public void addItem(String key, PdfString value)
/*     */   {
/*  85 */     PdfName fieldname = new PdfName(key);
/*  86 */     PdfCollectionField field = (PdfCollectionField)this.schema.get(fieldname);
/*  87 */     if (field.fieldType == 0)
/*  88 */       put(fieldname, value);
/*     */   }
/*     */ 
/*     */   public void addItem(String key, PdfDate d)
/*     */   {
/*  97 */     PdfName fieldname = new PdfName(key);
/*  98 */     PdfCollectionField field = (PdfCollectionField)this.schema.get(fieldname);
/*  99 */     if (field.fieldType == 1)
/* 100 */       put(fieldname, d);
/*     */   }
/*     */ 
/*     */   public void addItem(String key, PdfNumber n)
/*     */   {
/* 109 */     PdfName fieldname = new PdfName(key);
/* 110 */     PdfCollectionField field = (PdfCollectionField)this.schema.get(fieldname);
/* 111 */     if (field.fieldType == 2)
/* 112 */       put(fieldname, n);
/*     */   }
/*     */ 
/*     */   public void addItem(String key, Calendar c)
/*     */   {
/* 121 */     addItem(key, new PdfDate(c));
/*     */   }
/*     */ 
/*     */   public void addItem(String key, int i)
/*     */   {
/* 129 */     addItem(key, new PdfNumber(i));
/*     */   }
/*     */ 
/*     */   public void addItem(String key, float f)
/*     */   {
/* 137 */     addItem(key, new PdfNumber(f));
/*     */   }
/*     */ 
/*     */   public void addItem(String key, double d)
/*     */   {
/* 145 */     addItem(key, new PdfNumber(d));
/*     */   }
/*     */ 
/*     */   public void setPrefix(String key, String prefix)
/*     */   {
/* 154 */     PdfName fieldname = new PdfName(key);
/* 155 */     PdfObject o = get(fieldname);
/* 156 */     if (o == null)
/* 157 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("you.must.set.a.value.before.adding.a.prefix", new Object[0]));
/* 158 */     PdfDictionary dict = new PdfDictionary(PdfName.COLLECTIONSUBITEM);
/* 159 */     dict.put(PdfName.D, o);
/* 160 */     dict.put(PdfName.P, new PdfString(prefix, "UnicodeBig"));
/* 161 */     put(fieldname, dict);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.collection.PdfCollectionItem
 * JD-Core Version:    0.6.2
 */