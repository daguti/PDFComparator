/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ public class PdfDeveloperExtension
/*     */ {
/*  64 */   public static final PdfDeveloperExtension ADOBE_1_7_EXTENSIONLEVEL3 = new PdfDeveloperExtension(PdfName.ADBE, PdfWriter.PDF_VERSION_1_7, 3);
/*     */ 
/*  67 */   public static final PdfDeveloperExtension ESIC_1_7_EXTENSIONLEVEL2 = new PdfDeveloperExtension(PdfName.ESIC, PdfWriter.PDF_VERSION_1_7, 2);
/*     */ 
/*  70 */   public static final PdfDeveloperExtension ESIC_1_7_EXTENSIONLEVEL5 = new PdfDeveloperExtension(PdfName.ESIC, PdfWriter.PDF_VERSION_1_7, 5);
/*     */   protected PdfName prefix;
/*     */   protected PdfName baseversion;
/*     */   protected int extensionLevel;
/*     */ 
/*     */   public PdfDeveloperExtension(PdfName prefix, PdfName baseversion, int extensionLevel)
/*     */   {
/*  87 */     this.prefix = prefix;
/*  88 */     this.baseversion = baseversion;
/*  89 */     this.extensionLevel = extensionLevel;
/*     */   }
/*     */ 
/*     */   public PdfName getPrefix()
/*     */   {
/*  97 */     return this.prefix;
/*     */   }
/*     */ 
/*     */   public PdfName getBaseversion()
/*     */   {
/* 105 */     return this.baseversion;
/*     */   }
/*     */ 
/*     */   public int getExtensionLevel()
/*     */   {
/* 113 */     return this.extensionLevel;
/*     */   }
/*     */ 
/*     */   public PdfDictionary getDeveloperExtensions()
/*     */   {
/* 122 */     PdfDictionary developerextensions = new PdfDictionary();
/* 123 */     developerextensions.put(PdfName.BASEVERSION, this.baseversion);
/* 124 */     developerextensions.put(PdfName.EXTENSIONLEVEL, new PdfNumber(this.extensionLevel));
/* 125 */     return developerextensions;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfDeveloperExtension
 * JD-Core Version:    0.6.2
 */