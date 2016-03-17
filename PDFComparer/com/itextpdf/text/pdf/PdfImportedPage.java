/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.Image;
/*     */ import com.itextpdf.text.Rectangle;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.io.IOException;
/*     */ 
/*     */ public class PdfImportedPage extends PdfTemplate
/*     */ {
/*     */   PdfReaderInstance readerInstance;
/*     */   int pageNumber;
/*     */   int rotation;
/*  67 */   protected boolean toCopy = true;
/*     */ 
/*     */   PdfImportedPage(PdfReaderInstance readerInstance, PdfWriter writer, int pageNumber) {
/*  70 */     this.readerInstance = readerInstance;
/*  71 */     this.pageNumber = pageNumber;
/*  72 */     this.writer = writer;
/*  73 */     this.rotation = readerInstance.getReader().getPageRotation(pageNumber);
/*  74 */     this.bBox = readerInstance.getReader().getPageSize(pageNumber);
/*  75 */     setMatrix(1.0F, 0.0F, 0.0F, 1.0F, -this.bBox.getLeft(), -this.bBox.getBottom());
/*  76 */     this.type = 2;
/*     */   }
/*     */ 
/*     */   public PdfImportedPage getFromReader()
/*     */   {
/*  85 */     return this;
/*     */   }
/*     */ 
/*     */   public int getPageNumber() {
/*  89 */     return this.pageNumber;
/*     */   }
/*     */ 
/*     */   public int getRotation() {
/*  93 */     return this.rotation;
/*     */   }
/*     */ 
/*     */   public void addImage(Image image, float a, float b, float c, float d, float e, float f)
/*     */     throws DocumentException
/*     */   {
/* 107 */     throwError();
/*     */   }
/*     */ 
/*     */   public void addTemplate(PdfTemplate template, float a, float b, float c, float d, float e, float f)
/*     */   {
/* 119 */     throwError();
/*     */   }
/*     */ 
/*     */   public PdfContentByte getDuplicate()
/*     */   {
/* 125 */     throwError();
/* 126 */     return null;
/*     */   }
/*     */ 
/*     */   public PdfStream getFormXObject(int compressionLevel)
/*     */     throws IOException
/*     */   {
/* 137 */     return this.readerInstance.getFormXObject(this.pageNumber, compressionLevel);
/*     */   }
/*     */ 
/*     */   public void setColorFill(PdfSpotColor sp, float tint) {
/* 141 */     throwError();
/*     */   }
/*     */ 
/*     */   public void setColorStroke(PdfSpotColor sp, float tint) {
/* 145 */     throwError();
/*     */   }
/*     */ 
/*     */   PdfObject getResources() {
/* 149 */     return this.readerInstance.getResources(this.pageNumber);
/*     */   }
/*     */ 
/*     */   public void setFontAndSize(BaseFont bf, float size)
/*     */   {
/* 156 */     throwError();
/*     */   }
/*     */ 
/*     */   public void setGroup(PdfTransparencyGroup group)
/*     */   {
/* 165 */     throwError();
/*     */   }
/*     */ 
/*     */   void throwError() {
/* 169 */     throw new RuntimeException(MessageLocalization.getComposedMessage("content.can.not.be.added.to.a.pdfimportedpage", new Object[0]));
/*     */   }
/*     */ 
/*     */   PdfReaderInstance getPdfReaderInstance() {
/* 173 */     return this.readerInstance;
/*     */   }
/*     */ 
/*     */   public boolean isToCopy()
/*     */   {
/* 182 */     return this.toCopy;
/*     */   }
/*     */ 
/*     */   public void setCopied()
/*     */   {
/* 190 */     this.toCopy = false;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfImportedPage
 * JD-Core Version:    0.6.2
 */