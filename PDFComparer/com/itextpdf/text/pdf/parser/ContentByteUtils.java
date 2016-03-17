/*     */ package com.itextpdf.text.pdf.parser;
/*     */ 
/*     */ import com.itextpdf.text.pdf.PRIndirectReference;
/*     */ import com.itextpdf.text.pdf.PRStream;
/*     */ import com.itextpdf.text.pdf.PdfArray;
/*     */ import com.itextpdf.text.pdf.PdfDictionary;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfObject;
/*     */ import com.itextpdf.text.pdf.PdfReader;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.ListIterator;
/*     */ 
/*     */ public class ContentByteUtils
/*     */ {
/*     */   public static byte[] getContentBytesFromContentObject(PdfObject contentObject)
/*     */     throws IOException
/*     */   {
/*     */     byte[] result;
/*  77 */     switch (contentObject.type())
/*     */     {
/*     */     case 10:
/*  80 */       PRIndirectReference ref = (PRIndirectReference)contentObject;
/*  81 */       PdfObject directObject = PdfReader.getPdfObjectRelease(ref);
/*  82 */       result = getContentBytesFromContentObject(directObject);
/*  83 */       break;
/*     */     case 7:
/*  85 */       PRStream stream = (PRStream)PdfReader.getPdfObjectRelease(contentObject);
/*  86 */       result = PdfReader.getStreamBytes(stream);
/*  87 */       break;
/*     */     case 5:
/*  91 */       ByteArrayOutputStream allBytes = new ByteArrayOutputStream();
/*  92 */       PdfArray contentArray = (PdfArray)contentObject;
/*  93 */       ListIterator iter = contentArray.listIterator();
/*  94 */       while (iter.hasNext())
/*     */       {
/*  96 */         PdfObject element = (PdfObject)iter.next();
/*  97 */         allBytes.write(getContentBytesFromContentObject(element));
/*  98 */         allBytes.write(32);
/*     */       }
/* 100 */       result = allBytes.toByteArray();
/* 101 */       break;
/*     */     default:
/* 103 */       String msg = "Unable to handle Content of type " + contentObject.getClass();
/* 104 */       throw new IllegalStateException(msg);
/*     */     }
/* 106 */     return result;
/*     */   }
/*     */ 
/*     */   public static byte[] getContentBytesForPage(PdfReader reader, int pageNum)
/*     */     throws IOException
/*     */   {
/* 118 */     PdfDictionary pageDictionary = reader.getPageN(pageNum);
/* 119 */     PdfObject contentObject = pageDictionary.get(PdfName.CONTENTS);
/* 120 */     if (contentObject == null) {
/* 121 */       return new byte[0];
/*     */     }
/* 123 */     byte[] contentBytes = getContentBytesFromContentObject(contentObject);
/* 124 */     return contentBytes;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.ContentByteUtils
 * JD-Core Version:    0.6.2
 */