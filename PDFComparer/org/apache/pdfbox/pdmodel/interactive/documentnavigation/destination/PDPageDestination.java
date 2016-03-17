/*     */ package org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.PDPageNode;
/*     */ 
/*     */ public abstract class PDPageDestination extends PDDestination
/*     */ {
/*     */   protected COSArray array;
/*     */ 
/*     */   protected PDPageDestination()
/*     */   {
/*  49 */     this.array = new COSArray();
/*     */   }
/*     */ 
/*     */   protected PDPageDestination(COSArray arr)
/*     */   {
/*  59 */     this.array = arr;
/*     */   }
/*     */ 
/*     */   public PDPage getPage()
/*     */   {
/*  72 */     PDPage retval = null;
/*  73 */     if (this.array.size() > 0)
/*     */     {
/*  75 */       COSBase page = this.array.getObject(0);
/*  76 */       if ((page instanceof COSDictionary))
/*     */       {
/*  78 */         retval = new PDPage((COSDictionary)page);
/*     */       }
/*     */     }
/*  81 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setPage(PDPage page)
/*     */   {
/*  91 */     this.array.set(0, page);
/*     */   }
/*     */ 
/*     */   public int getPageNumber()
/*     */   {
/* 104 */     int retval = -1;
/* 105 */     if (this.array.size() > 0)
/*     */     {
/* 107 */       COSBase page = this.array.getObject(0);
/* 108 */       if ((page instanceof COSNumber))
/*     */       {
/* 110 */         retval = ((COSNumber)page).intValue();
/*     */       }
/*     */     }
/* 113 */     return retval;
/*     */   }
/*     */ 
/*     */   public int findPageNumber()
/*     */   {
/* 125 */     int retval = -1;
/* 126 */     if (this.array.size() > 0)
/*     */     {
/* 128 */       COSBase page = this.array.getObject(0);
/* 129 */       if ((page instanceof COSNumber))
/*     */       {
/* 131 */         retval = ((COSNumber)page).intValue();
/*     */       }
/* 133 */       else if ((page instanceof COSDictionary))
/*     */       {
/* 135 */         COSBase parent = page;
/* 136 */         while (((COSDictionary)parent).getDictionaryObject("Parent", "P") != null) {
/* 137 */           parent = ((COSDictionary)parent).getDictionaryObject("Parent", "P");
/*     */         }
/*     */ 
/* 140 */         PDPageNode pages = new PDPageNode((COSDictionary)parent);
/* 141 */         List allPages = new ArrayList();
/* 142 */         pages.getAllKids(allPages);
/* 143 */         retval = allPages.indexOf(new PDPage((COSDictionary)page)) + 1;
/*     */       }
/*     */     }
/* 146 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setPageNumber(int pageNumber)
/*     */   {
/* 156 */     this.array.set(0, pageNumber);
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 166 */     return this.array;
/*     */   }
/*     */ 
/*     */   public COSArray getCOSArray()
/*     */   {
/* 176 */     return this.array;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination
 * JD-Core Version:    0.6.2
 */