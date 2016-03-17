/*     */ package com.itextpdf.text.pdf.events;
/*     */ 
/*     */ import com.itextpdf.text.Document;
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.Rectangle;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.pdf.PdfContentByte;
/*     */ import com.itextpdf.text.pdf.PdfFormField;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfPCell;
/*     */ import com.itextpdf.text.pdf.PdfPCellEvent;
/*     */ import com.itextpdf.text.pdf.PdfPageEventHelper;
/*     */ import com.itextpdf.text.pdf.PdfRectangle;
/*     */ import com.itextpdf.text.pdf.PdfWriter;
/*     */ import com.itextpdf.text.pdf.TextField;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class FieldPositioningEvents extends PdfPageEventHelper
/*     */   implements PdfPCellEvent
/*     */ {
/*  73 */   protected HashMap<String, PdfFormField> genericChunkFields = new HashMap();
/*     */ 
/*  78 */   protected PdfFormField cellField = null;
/*     */ 
/*  83 */   protected PdfWriter fieldWriter = null;
/*     */ 
/*  87 */   protected PdfFormField parent = null;
/*     */   public float padding;
/*     */ 
/*     */   public FieldPositioningEvents()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void addField(String text, PdfFormField field)
/*     */   {
/*  99 */     this.genericChunkFields.put(text, field);
/*     */   }
/*     */ 
/*     */   public FieldPositioningEvents(PdfWriter writer, PdfFormField field)
/*     */   {
/* 104 */     this.cellField = field;
/* 105 */     this.fieldWriter = writer;
/*     */   }
/*     */ 
/*     */   public FieldPositioningEvents(PdfFormField parent, PdfFormField field)
/*     */   {
/* 110 */     this.cellField = field;
/* 111 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */   public FieldPositioningEvents(PdfWriter writer, String text)
/*     */     throws IOException, DocumentException
/*     */   {
/* 118 */     this.fieldWriter = writer;
/* 119 */     TextField tf = new TextField(writer, new Rectangle(0.0F, 0.0F), text);
/* 120 */     tf.setFontSize(14.0F);
/* 121 */     this.cellField = tf.getTextField();
/*     */   }
/*     */ 
/*     */   public FieldPositioningEvents(PdfWriter writer, PdfFormField parent, String text)
/*     */     throws IOException, DocumentException
/*     */   {
/* 128 */     this.parent = parent;
/* 129 */     TextField tf = new TextField(writer, new Rectangle(0.0F, 0.0F), text);
/* 130 */     tf.setFontSize(14.0F);
/* 131 */     this.cellField = tf.getTextField();
/*     */   }
/*     */ 
/*     */   public void setPadding(float padding)
/*     */   {
/* 138 */     this.padding = padding;
/*     */   }
/*     */ 
/*     */   public void setParent(PdfFormField parent)
/*     */   {
/* 145 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */   public void onGenericTag(PdfWriter writer, Document document, Rectangle rect, String text)
/*     */   {
/* 153 */     rect.setBottom(rect.getBottom() - 3.0F);
/* 154 */     PdfFormField field = (PdfFormField)this.genericChunkFields.get(text);
/* 155 */     if (field == null) {
/* 156 */       TextField tf = new TextField(writer, new Rectangle(rect.getLeft(this.padding), rect.getBottom(this.padding), rect.getRight(this.padding), rect.getTop(this.padding)), text);
/* 157 */       tf.setFontSize(14.0F);
/*     */       try {
/* 159 */         field = tf.getTextField();
/*     */       } catch (Exception e) {
/* 161 */         throw new ExceptionConverter(e);
/*     */       }
/*     */     }
/*     */     else {
/* 165 */       field.put(PdfName.RECT, new PdfRectangle(rect.getLeft(this.padding), rect.getBottom(this.padding), rect.getRight(this.padding), rect.getTop(this.padding)));
/*     */     }
/* 167 */     if (this.parent == null)
/* 168 */       writer.addAnnotation(field);
/*     */     else
/* 170 */       this.parent.addKid(field);
/*     */   }
/*     */ 
/*     */   public void cellLayout(PdfPCell cell, Rectangle rect, PdfContentByte[] canvases)
/*     */   {
/* 177 */     if ((this.cellField == null) || ((this.fieldWriter == null) && (this.parent == null))) throw new IllegalArgumentException(MessageLocalization.getComposedMessage("you.have.used.the.wrong.constructor.for.this.fieldpositioningevents.class", new Object[0]));
/* 178 */     this.cellField.put(PdfName.RECT, new PdfRectangle(rect.getLeft(this.padding), rect.getBottom(this.padding), rect.getRight(this.padding), rect.getTop(this.padding)));
/* 179 */     if (this.parent == null)
/* 180 */       this.fieldWriter.addAnnotation(this.cellField);
/*     */     else
/* 182 */       this.parent.addKid(this.cellField);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.events.FieldPositioningEvents
 * JD-Core Version:    0.6.2
 */