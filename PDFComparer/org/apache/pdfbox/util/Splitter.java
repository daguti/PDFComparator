/*     */ package org.apache.pdfbox.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ 
/*     */ public class Splitter
/*     */ {
/*     */   protected PDDocument pdfDocument;
/*  46 */   protected PDDocument currentDocument = null;
/*     */ 
/*  48 */   private int splitAtPage = 1;
/*  49 */   private int startPage = -2147483648;
/*  50 */   private int endPage = 2147483647;
/*  51 */   private List<PDDocument> newDocuments = null;
/*     */ 
/*  56 */   protected int pageNumber = 0;
/*     */ 
/*     */   public List<PDDocument> split(PDDocument document)
/*     */     throws IOException
/*     */   {
/*  69 */     this.newDocuments = new ArrayList();
/*  70 */     this.pdfDocument = document;
/*     */ 
/*  72 */     List pages = this.pdfDocument.getDocumentCatalog().getAllPages();
/*  73 */     processPages(pages);
/*  74 */     return this.newDocuments;
/*     */   }
/*     */ 
/*     */   public void setSplitAtPage(int split)
/*     */   {
/*  88 */     if (split <= 0)
/*     */     {
/*  90 */       throw new RuntimeException("Error split must be at least one page.");
/*     */     }
/*  92 */     this.splitAtPage = split;
/*     */   }
/*     */ 
/*     */   public int getSplitAtPage()
/*     */   {
/* 102 */     return this.splitAtPage;
/*     */   }
/*     */ 
/*     */   public void setStartPage(int start)
/*     */   {
/* 112 */     if (start <= 0)
/*     */     {
/* 114 */       throw new RuntimeException("Error split must be at least one page.");
/*     */     }
/* 116 */     this.startPage = start;
/*     */   }
/*     */ 
/*     */   public int getStartPage()
/*     */   {
/* 125 */     return this.startPage;
/*     */   }
/*     */ 
/*     */   public void setEndPage(int end)
/*     */   {
/* 135 */     if (end <= 0)
/*     */     {
/* 137 */       throw new RuntimeException("Error split must be at least one page.");
/*     */     }
/* 139 */     this.endPage = end;
/*     */   }
/*     */ 
/*     */   public int getEndPage()
/*     */   {
/* 149 */     return this.endPage;
/*     */   }
/*     */ 
/*     */   protected void processPages(List pages)
/*     */     throws IOException
/*     */   {
/* 161 */     Iterator iter = pages.iterator();
/* 162 */     while (iter.hasNext())
/*     */     {
/* 164 */       PDPage page = (PDPage)iter.next();
/* 165 */       if ((this.pageNumber + 1 >= this.startPage) && (this.pageNumber + 1 <= this.endPage))
/*     */       {
/* 167 */         processNextPage(page);
/*     */       }
/*     */       else
/*     */       {
/* 171 */         if (this.pageNumber > this.endPage)
/*     */         {
/*     */           break;
/*     */         }
/*     */ 
/* 177 */         this.pageNumber += 1;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void createNewDocumentIfNecessary()
/*     */     throws IOException
/*     */   {
/* 201 */     if (isNewDocNecessary())
/*     */     {
/* 203 */       createNewDocument();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected boolean isNewDocNecessary()
/*     */   {
/* 214 */     return (this.pageNumber % this.splitAtPage == 0) || (this.currentDocument == null);
/*     */   }
/*     */ 
/*     */   protected void createNewDocument()
/*     */     throws IOException
/*     */   {
/* 224 */     this.currentDocument = new PDDocument();
/* 225 */     this.currentDocument.setDocumentInformation(this.pdfDocument.getDocumentInformation());
/* 226 */     this.currentDocument.getDocumentCatalog().setViewerPreferences(this.pdfDocument.getDocumentCatalog().getViewerPreferences());
/*     */ 
/* 228 */     this.newDocuments.add(this.currentDocument);
/*     */   }
/*     */ 
/*     */   protected void processNextPage(PDPage page)
/*     */     throws IOException
/*     */   {
/* 242 */     createNewDocumentIfNecessary();
/* 243 */     PDPage imported = this.currentDocument.importPage(page);
/* 244 */     imported.setCropBox(page.findCropBox());
/* 245 */     imported.setMediaBox(page.findMediaBox());
/*     */ 
/* 247 */     imported.setResources(page.getResources());
/* 248 */     imported.setRotation(page.findRotation());
/* 249 */     this.pageNumber += 1;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.Splitter
 * JD-Core Version:    0.6.2
 */