/*     */ package com.itextpdf.text.pdf.fonts.cmaps;
/*     */ 
/*     */ import com.itextpdf.text.pdf.PdfArray;
/*     */ import com.itextpdf.text.pdf.PdfEncodings;
/*     */ import com.itextpdf.text.pdf.PdfNumber;
/*     */ import com.itextpdf.text.pdf.PdfObject;
/*     */ import com.itextpdf.text.pdf.PdfString;
/*     */ 
/*     */ public abstract class AbstractCMap
/*     */ {
/*     */   private String cmapName;
/*     */   private String registry;
/*     */   private String ordering;
/*     */   private int supplement;
/*     */ 
/*     */   public String getName()
/*     */   {
/*  65 */     return this.cmapName;
/*     */   }
/*     */ 
/*     */   void setName(String cmapName) {
/*  69 */     this.cmapName = cmapName;
/*     */   }
/*     */ 
/*     */   public String getOrdering() {
/*  73 */     return this.ordering;
/*     */   }
/*     */ 
/*     */   void setOrdering(String ordering) {
/*  77 */     this.ordering = ordering;
/*     */   }
/*     */ 
/*     */   public String getRegistry() {
/*  81 */     return this.registry;
/*     */   }
/*     */ 
/*     */   void setRegistry(String registry) {
/*  85 */     this.registry = registry;
/*     */   }
/*     */ 
/*     */   public int getSupplement() {
/*  89 */     return this.supplement;
/*     */   }
/*     */ 
/*     */   void setSupplement(int supplement) {
/*  93 */     this.supplement = supplement;
/*     */   }
/*     */ 
/*     */   abstract void addChar(PdfString paramPdfString, PdfObject paramPdfObject);
/*     */ 
/*     */   void addRange(PdfString from, PdfString to, PdfObject code) {
/*  99 */     byte[] a1 = decodeStringToByte(from);
/* 100 */     byte[] a2 = decodeStringToByte(to);
/* 101 */     if ((a1.length != a2.length) || (a1.length == 0))
/* 102 */       throw new IllegalArgumentException("Invalid map.");
/* 103 */     byte[] sout = null;
/* 104 */     if ((code instanceof PdfString))
/* 105 */       sout = decodeStringToByte((PdfString)code);
/* 106 */     int start = a1[(a1.length - 1)] & 0xFF;
/* 107 */     int end = a2[(a2.length - 1)] & 0xFF;
/* 108 */     for (int k = start; k <= end; k++) {
/* 109 */       a1[(a1.length - 1)] = ((byte)k);
/* 110 */       PdfString s = new PdfString(a1);
/* 111 */       s.setHexWriting(true);
/* 112 */       if ((code instanceof PdfArray)) {
/* 113 */         addChar(s, ((PdfArray)code).getPdfObject(k - start));
/*     */       }
/* 115 */       else if ((code instanceof PdfNumber)) {
/* 116 */         int nn = ((PdfNumber)code).intValue() + k - start;
/* 117 */         addChar(s, new PdfNumber(nn));
/*     */       }
/* 119 */       else if ((code instanceof PdfString)) {
/* 120 */         PdfString s1 = new PdfString(sout);
/* 121 */         s1.setHexWriting(true);
/*     */         int tmp224_223 = (sout.length - 1);
/*     */         byte[] tmp224_217 = sout; tmp224_217[tmp224_223] = ((byte)(tmp224_217[tmp224_223] + 1));
/* 123 */         addChar(s, s1);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static byte[] decodeStringToByte(PdfString s) {
/* 129 */     byte[] b = s.getBytes();
/* 130 */     byte[] br = new byte[b.length];
/* 131 */     System.arraycopy(b, 0, br, 0, b.length);
/* 132 */     return br;
/*     */   }
/*     */ 
/*     */   public String decodeStringToUnicode(PdfString ps) {
/* 136 */     if (ps.isHexWriting()) {
/* 137 */       return PdfEncodings.convertToString(ps.getBytes(), "UnicodeBigUnmarked");
/*     */     }
/* 139 */     return ps.toUnicodeString();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.fonts.cmaps.AbstractCMap
 * JD-Core Version:    0.6.2
 */