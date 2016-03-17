/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public class PdfLister
/*     */ {
/*     */   PrintStream out;
/*     */ 
/*     */   public PdfLister(PrintStream out)
/*     */   {
/*  65 */     this.out = out;
/*     */   }
/*     */ 
/*     */   public void listAnyObject(PdfObject object)
/*     */   {
/*  74 */     switch (object.type()) {
/*     */     case 5:
/*  76 */       listArray((PdfArray)object);
/*  77 */       break;
/*     */     case 6:
/*  79 */       listDict((PdfDictionary)object);
/*  80 */       break;
/*     */     case 3:
/*  82 */       this.out.println("(" + object.toString() + ")");
/*  83 */       break;
/*     */     case 4:
/*     */     default:
/*  85 */       this.out.println(object.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void listDict(PdfDictionary dictionary)
/*     */   {
/*  95 */     this.out.println("<<");
/*     */ 
/*  97 */     for (PdfName key : dictionary.getKeys()) {
/*  98 */       PdfObject value = dictionary.get(key);
/*  99 */       this.out.print(key.toString());
/* 100 */       this.out.print(' ');
/* 101 */       listAnyObject(value);
/*     */     }
/* 103 */     this.out.println(">>");
/*     */   }
/*     */ 
/*     */   public void listArray(PdfArray array)
/*     */   {
/* 112 */     this.out.println('[');
/* 113 */     for (Iterator i = array.listIterator(); i.hasNext(); ) {
/* 114 */       PdfObject item = (PdfObject)i.next();
/* 115 */       listAnyObject(item);
/*     */     }
/* 117 */     this.out.println(']');
/*     */   }
/*     */ 
/*     */   public void listStream(PRStream stream, PdfReaderInstance reader)
/*     */   {
/*     */     try
/*     */     {
/* 127 */       listDict(stream);
/* 128 */       this.out.println("startstream");
/* 129 */       byte[] b = PdfReader.getStreamBytes(stream);
/*     */ 
/* 139 */       int len = b.length - 1;
/* 140 */       for (int k = 0; k < len; k++) {
/* 141 */         if ((b[k] == 13) && (b[(k + 1)] != 10))
/* 142 */           b[k] = 10;
/*     */       }
/* 144 */       this.out.println(new String(b));
/* 145 */       this.out.println("endstream");
/*     */     } catch (IOException e) {
/* 147 */       System.err.println("I/O exception: " + e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void listPage(PdfImportedPage iPage)
/*     */   {
/* 158 */     int pageNum = iPage.getPageNumber();
/* 159 */     PdfReaderInstance readerInst = iPage.getPdfReaderInstance();
/* 160 */     PdfReader reader = readerInst.getReader();
/*     */ 
/* 162 */     PdfDictionary page = reader.getPageN(pageNum);
/* 163 */     listDict(page);
/* 164 */     PdfObject obj = PdfReader.getPdfObject(page.get(PdfName.CONTENTS));
/* 165 */     if (obj == null)
/*     */       return;
/*     */     Iterator i;
/* 167 */     switch (obj.type) {
/*     */     case 7:
/* 169 */       listStream((PRStream)obj, readerInst);
/* 170 */       break;
/*     */     case 5:
/* 172 */       for (i = ((PdfArray)obj).listIterator(); i.hasNext(); ) {
/* 173 */         PdfObject o = PdfReader.getPdfObject((PdfObject)i.next());
/* 174 */         listStream((PRStream)o, readerInst);
/* 175 */         this.out.println("-----------");
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfLister
 * JD-Core Version:    0.6.2
 */