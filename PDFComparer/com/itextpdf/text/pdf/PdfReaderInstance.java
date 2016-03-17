/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ 
/*     */ class PdfReaderInstance
/*     */ {
/*  58 */   static final PdfLiteral IDENTITYMATRIX = new PdfLiteral("[1 0 0 1 0 0]");
/*  59 */   static final PdfNumber ONE = new PdfNumber(1);
/*     */   int[] myXref;
/*     */   PdfReader reader;
/*     */   RandomAccessFileOrArray file;
/*  63 */   HashMap<Integer, PdfImportedPage> importedPages = new HashMap();
/*     */   PdfWriter writer;
/*  65 */   HashSet<Integer> visited = new HashSet();
/*  66 */   ArrayList<Integer> nextRound = new ArrayList();
/*     */ 
/*     */   PdfReaderInstance(PdfReader reader, PdfWriter writer) {
/*  69 */     this.reader = reader;
/*  70 */     this.writer = writer;
/*  71 */     this.file = reader.getSafeFile();
/*  72 */     this.myXref = new int[reader.getXrefSize()];
/*     */   }
/*     */ 
/*     */   PdfReader getReader() {
/*  76 */     return this.reader;
/*     */   }
/*     */ 
/*     */   PdfImportedPage getImportedPage(int pageNumber) {
/*  80 */     if (!this.reader.isOpenedWithFullPermissions())
/*  81 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("pdfreader.not.opened.with.owner.password", new Object[0]));
/*  82 */     if ((pageNumber < 1) || (pageNumber > this.reader.getNumberOfPages()))
/*  83 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("invalid.page.number.1", pageNumber));
/*  84 */     Integer i = Integer.valueOf(pageNumber);
/*  85 */     PdfImportedPage pageT = (PdfImportedPage)this.importedPages.get(i);
/*  86 */     if (pageT == null) {
/*  87 */       pageT = new PdfImportedPage(this, this.writer, pageNumber);
/*  88 */       this.importedPages.put(i, pageT);
/*     */     }
/*  90 */     return pageT;
/*     */   }
/*     */ 
/*     */   int getNewObjectNumber(int number, int generation) {
/*  94 */     if (this.myXref[number] == 0) {
/*  95 */       this.myXref[number] = this.writer.getIndirectReferenceNumber();
/*  96 */       this.nextRound.add(Integer.valueOf(number));
/*     */     }
/*  98 */     return this.myXref[number];
/*     */   }
/*     */ 
/*     */   RandomAccessFileOrArray getReaderFile() {
/* 102 */     return this.file;
/*     */   }
/*     */ 
/*     */   PdfObject getResources(int pageNumber) {
/* 106 */     PdfObject obj = PdfReader.getPdfObjectRelease(this.reader.getPageNRelease(pageNumber).get(PdfName.RESOURCES));
/* 107 */     return obj;
/*     */   }
/*     */ 
/*     */   PdfStream getFormXObject(int pageNumber, int compressionLevel)
/*     */     throws IOException
/*     */   {
/* 118 */     PdfDictionary page = this.reader.getPageNRelease(pageNumber);
/* 119 */     PdfObject contents = PdfReader.getPdfObjectRelease(page.get(PdfName.CONTENTS));
/* 120 */     PdfDictionary dic = new PdfDictionary();
/* 121 */     byte[] bout = null;
/* 122 */     if (contents != null) {
/* 123 */       if (contents.isStream())
/* 124 */         dic.putAll((PRStream)contents);
/*     */       else
/* 126 */         bout = this.reader.getPageContent(pageNumber, this.file);
/*     */     }
/*     */     else
/* 129 */       bout = new byte[0];
/* 130 */     dic.put(PdfName.RESOURCES, PdfReader.getPdfObjectRelease(page.get(PdfName.RESOURCES)));
/* 131 */     dic.put(PdfName.TYPE, PdfName.XOBJECT);
/* 132 */     dic.put(PdfName.SUBTYPE, PdfName.FORM);
/* 133 */     PdfImportedPage impPage = (PdfImportedPage)this.importedPages.get(Integer.valueOf(pageNumber));
/* 134 */     dic.put(PdfName.BBOX, new PdfRectangle(impPage.getBoundingBox()));
/* 135 */     PdfArray matrix = impPage.getMatrix();
/* 136 */     if (matrix == null)
/* 137 */       dic.put(PdfName.MATRIX, IDENTITYMATRIX);
/*     */     else
/* 139 */       dic.put(PdfName.MATRIX, matrix);
/* 140 */     dic.put(PdfName.FORMTYPE, ONE);
/*     */     PRStream stream;
/*     */     PRStream stream;
/* 142 */     if (bout == null) {
/* 143 */       stream = new PRStream((PRStream)contents, dic);
/*     */     }
/*     */     else {
/* 146 */       stream = new PRStream(this.reader, bout, compressionLevel);
/* 147 */       stream.putAll(dic);
/*     */     }
/* 149 */     return stream;
/*     */   }
/*     */ 
/*     */   void writeAllVisited() throws IOException {
/* 153 */     while (!this.nextRound.isEmpty()) {
/* 154 */       ArrayList vec = this.nextRound;
/* 155 */       this.nextRound = new ArrayList();
/* 156 */       for (int k = 0; k < vec.size(); k++) {
/* 157 */         Integer i = (Integer)vec.get(k);
/* 158 */         if (!this.visited.contains(i)) {
/* 159 */           this.visited.add(i);
/* 160 */           int n = i.intValue();
/* 161 */           this.writer.addToBody(this.reader.getPdfObjectRelease(n), this.myXref[n]);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void writeAllPages() throws IOException {
/*     */     try {
/* 169 */       this.file.reOpen();
/* 170 */       for (Object element : this.importedPages.values()) {
/* 171 */         PdfImportedPage ip = (PdfImportedPage)element;
/* 172 */         if (ip.isToCopy()) {
/* 173 */           this.writer.addToBody(ip.getFormXObject(this.writer.getCompressionLevel()), ip.getIndirectReference());
/* 174 */           ip.setCopied();
/*     */         }
/*     */       }
/* 177 */       writeAllVisited();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 183 */         this.file.close();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfReaderInstance
 * JD-Core Version:    0.6.2
 */