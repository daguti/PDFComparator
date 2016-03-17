/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.Document;
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ public class PdfConcatenate
/*     */ {
/*     */   protected Document document;
/*     */   protected PdfCopy copy;
/*     */ 
/*     */   public PdfConcatenate(OutputStream os)
/*     */     throws DocumentException
/*     */   {
/*  68 */     this(os, false);
/*     */   }
/*     */ 
/*     */   public PdfConcatenate(OutputStream os, boolean smart)
/*     */     throws DocumentException
/*     */   {
/*  77 */     this.document = new Document();
/*  78 */     if (smart)
/*  79 */       this.copy = new PdfSmartCopy(this.document, os);
/*     */     else
/*  81 */       this.copy = new PdfCopy(this.document, os);
/*     */   }
/*     */ 
/*     */   public int addPages(PdfReader reader)
/*     */     throws DocumentException, IOException
/*     */   {
/*  92 */     open();
/*  93 */     int n = reader.getNumberOfPages();
/*  94 */     for (int i = 1; i <= n; i++) {
/*  95 */       this.copy.addPage(this.copy.getImportedPage(reader, i));
/*     */     }
/*  97 */     this.copy.freeReader(reader);
/*  98 */     reader.close();
/*  99 */     return n;
/*     */   }
/*     */ 
/*     */   public PdfCopy getWriter()
/*     */   {
/* 106 */     return this.copy;
/*     */   }
/*     */ 
/*     */   public void open()
/*     */   {
/* 114 */     if (!this.document.isOpen())
/* 115 */       this.document.open();
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/* 123 */     this.document.close();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfConcatenate
 * JD-Core Version:    0.6.2
 */