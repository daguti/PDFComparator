/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class PdfPages
/*     */ {
/*  67 */   private ArrayList<PdfIndirectReference> pages = new ArrayList();
/*  68 */   private ArrayList<PdfIndirectReference> parents = new ArrayList();
/*  69 */   private int leafSize = 10;
/*     */   private PdfWriter writer;
/*     */   private PdfIndirectReference topParent;
/*     */ 
/*     */   PdfPages(PdfWriter writer)
/*     */   {
/*  80 */     this.writer = writer;
/*     */   }
/*     */ 
/*     */   void addPage(PdfDictionary page) {
/*     */     try {
/*  85 */       if (this.pages.size() % this.leafSize == 0)
/*  86 */         this.parents.add(this.writer.getPdfIndirectReference());
/*  87 */       PdfIndirectReference parent = (PdfIndirectReference)this.parents.get(this.parents.size() - 1);
/*  88 */       page.put(PdfName.PARENT, parent);
/*  89 */       PdfIndirectReference current = this.writer.getCurrentPage();
/*  90 */       this.writer.addToBody(page, current);
/*  91 */       this.pages.add(current);
/*     */     }
/*     */     catch (Exception e) {
/*  94 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   PdfIndirectReference addPageRef(PdfIndirectReference pageRef) {
/*     */     try {
/* 100 */       if (this.pages.size() % this.leafSize == 0)
/* 101 */         this.parents.add(this.writer.getPdfIndirectReference());
/* 102 */       this.pages.add(pageRef);
/* 103 */       return (PdfIndirectReference)this.parents.get(this.parents.size() - 1);
/*     */     }
/*     */     catch (Exception e) {
/* 106 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   PdfIndirectReference writePageTree() throws IOException
/*     */   {
/* 112 */     if (this.pages.isEmpty())
/* 113 */       throw new IOException(MessageLocalization.getComposedMessage("the.document.has.no.pages", new Object[0]));
/* 114 */     int leaf = 1;
/* 115 */     ArrayList tParents = this.parents;
/* 116 */     ArrayList tPages = this.pages;
/* 117 */     ArrayList nextParents = new ArrayList();
/*     */     while (true) {
/* 119 */       leaf *= this.leafSize;
/* 120 */       int stdCount = this.leafSize;
/* 121 */       int rightCount = tPages.size() % this.leafSize;
/* 122 */       if (rightCount == 0)
/* 123 */         rightCount = this.leafSize;
/* 124 */       for (int p = 0; p < tParents.size(); p++)
/*     */       {
/* 126 */         int thisLeaf = leaf;
/*     */         int count;
/* 127 */         if (p == tParents.size() - 1) {
/* 128 */           int count = rightCount;
/* 129 */           thisLeaf = this.pages.size() % leaf;
/* 130 */           if (thisLeaf == 0)
/* 131 */             thisLeaf = leaf;
/*     */         }
/*     */         else {
/* 134 */           count = stdCount;
/* 135 */         }PdfDictionary top = new PdfDictionary(PdfName.PAGES);
/* 136 */         top.put(PdfName.COUNT, new PdfNumber(thisLeaf));
/* 137 */         PdfArray kids = new PdfArray();
/* 138 */         ArrayList internal = kids.getArrayList();
/* 139 */         internal.addAll(tPages.subList(p * stdCount, p * stdCount + count));
/* 140 */         top.put(PdfName.KIDS, kids);
/* 141 */         if (tParents.size() > 1) {
/* 142 */           if (p % this.leafSize == 0)
/* 143 */             nextParents.add(this.writer.getPdfIndirectReference());
/* 144 */           top.put(PdfName.PARENT, (PdfObject)nextParents.get(p / this.leafSize));
/*     */         }
/* 146 */         this.writer.addToBody(top, (PdfIndirectReference)tParents.get(p));
/*     */       }
/* 148 */       if (tParents.size() == 1) {
/* 149 */         this.topParent = ((PdfIndirectReference)tParents.get(0));
/* 150 */         return this.topParent;
/*     */       }
/* 152 */       tPages = tParents;
/* 153 */       tParents = nextParents;
/* 154 */       nextParents = new ArrayList();
/*     */     }
/*     */   }
/*     */ 
/*     */   PdfIndirectReference getTopParent() {
/* 159 */     return this.topParent;
/*     */   }
/*     */ 
/*     */   void setLinearMode(PdfIndirectReference topParent) {
/* 163 */     if (this.parents.size() > 1)
/* 164 */       throw new RuntimeException(MessageLocalization.getComposedMessage("linear.page.mode.can.only.be.called.with.a.single.parent", new Object[0]));
/* 165 */     if (topParent != null) {
/* 166 */       this.topParent = topParent;
/* 167 */       this.parents.clear();
/* 168 */       this.parents.add(topParent);
/*     */     }
/* 170 */     this.leafSize = 10000000;
/*     */   }
/*     */ 
/*     */   void addPage(PdfIndirectReference page) {
/* 174 */     this.pages.add(page);
/*     */   }
/*     */ 
/*     */   int reorderPages(int[] order) throws DocumentException {
/* 178 */     if (order == null)
/* 179 */       return this.pages.size();
/* 180 */     if (this.parents.size() > 1)
/* 181 */       throw new DocumentException(MessageLocalization.getComposedMessage("page.reordering.requires.a.single.parent.in.the.page.tree.call.pdfwriter.setlinearmode.after.open", new Object[0]));
/* 182 */     if (order.length != this.pages.size())
/* 183 */       throw new DocumentException(MessageLocalization.getComposedMessage("page.reordering.requires.an.array.with.the.same.size.as.the.number.of.pages", new Object[0]));
/* 184 */     int max = this.pages.size();
/* 185 */     boolean[] temp = new boolean[max];
/* 186 */     for (int k = 0; k < max; k++) {
/* 187 */       int p = order[k];
/* 188 */       if ((p < 1) || (p > max))
/* 189 */         throw new DocumentException(MessageLocalization.getComposedMessage("page.reordering.requires.pages.between.1.and.1.found.2", new Object[] { String.valueOf(max), String.valueOf(p) }));
/* 190 */       if (temp[(p - 1)] != 0)
/* 191 */         throw new DocumentException(MessageLocalization.getComposedMessage("page.reordering.requires.no.page.repetition.page.1.is.repeated", p));
/* 192 */       temp[(p - 1)] = true;
/*     */     }
/* 194 */     PdfIndirectReference[] copy = (PdfIndirectReference[])this.pages.toArray(new PdfIndirectReference[this.pages.size()]);
/* 195 */     for (int k = 0; k < max; k++) {
/* 196 */       this.pages.set(k, copy[(order[k] - 1)]);
/*     */     }
/* 198 */     return max;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfPages
 * JD-Core Version:    0.6.2
 */