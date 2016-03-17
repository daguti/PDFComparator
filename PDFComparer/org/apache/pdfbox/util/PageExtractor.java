/*     */ package org.apache.pdfbox.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ 
/*     */ public class PageExtractor
/*     */ {
/*     */   protected PDDocument sourceDocument;
/*  31 */   protected int startPage = 1;
/*  32 */   protected int endPage = 0;
/*     */ 
/*     */   public PageExtractor(PDDocument sourceDocument)
/*     */   {
/*  39 */     this.sourceDocument = sourceDocument;
/*  40 */     this.endPage = sourceDocument.getNumberOfPages();
/*     */   }
/*     */ 
/*     */   public PageExtractor(PDDocument sourceDocument, int startPage, int endPage)
/*     */   {
/*  50 */     this(sourceDocument);
/*  51 */     this.startPage = startPage;
/*  52 */     this.endPage = endPage;
/*     */   }
/*     */ 
/*     */   public PDDocument extract()
/*     */     throws IOException
/*     */   {
/*  68 */     PDDocument extractedDocument = new PDDocument();
/*  69 */     extractedDocument.setDocumentInformation(this.sourceDocument.getDocumentInformation());
/*  70 */     extractedDocument.getDocumentCatalog().setViewerPreferences(this.sourceDocument.getDocumentCatalog().getViewerPreferences());
/*     */ 
/*  73 */     List pages = this.sourceDocument.getDocumentCatalog().getAllPages();
/*  74 */     int pageCounter = 1;
/*  75 */     for (PDPage page : pages) {
/*  76 */       if ((pageCounter >= this.startPage) && (pageCounter <= this.endPage)) {
/*  77 */         PDPage imported = extractedDocument.importPage(page);
/*  78 */         imported.setCropBox(page.findCropBox());
/*  79 */         imported.setMediaBox(page.findMediaBox());
/*  80 */         imported.setResources(page.findResources());
/*  81 */         imported.setRotation(page.findRotation());
/*     */       }
/*  83 */       pageCounter++;
/*     */     }
/*     */ 
/*  86 */     return extractedDocument;
/*     */   }
/*     */ 
/*     */   public int getStartPage()
/*     */   {
/*  94 */     return this.startPage;
/*     */   }
/*     */ 
/*     */   public void setStartPage(int startPage)
/*     */   {
/* 102 */     this.startPage = startPage;
/*     */   }
/*     */ 
/*     */   public int getEndPage()
/*     */   {
/* 110 */     return this.endPage;
/*     */   }
/*     */ 
/*     */   public void setEndPage(int endPage)
/*     */   {
/* 118 */     this.endPage = endPage;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.PageExtractor
 * JD-Core Version:    0.6.2
 */