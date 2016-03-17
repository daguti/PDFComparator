/*     */ package com.itextpdf.text.pdf.internal;
/*     */ 
/*     */ import com.itextpdf.text.DocWriter;
/*     */ import com.itextpdf.text.pdf.OutputStreamCounter;
/*     */ import com.itextpdf.text.pdf.PdfDeveloperExtension;
/*     */ import com.itextpdf.text.pdf.PdfDictionary;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfNumber;
/*     */ import com.itextpdf.text.pdf.PdfWriter;
/*     */ import com.itextpdf.text.pdf.interfaces.PdfVersion;
/*     */ import java.io.IOException;
/*     */ 
/*     */ public class PdfVersionImp
/*     */   implements PdfVersion
/*     */ {
/*  66 */   public static final byte[][] HEADER = { DocWriter.getISOBytes("\n"), DocWriter.getISOBytes("%PDF-"), DocWriter.getISOBytes("\n%âãÏÓ\n") };
/*     */ 
/*  73 */   protected boolean headerWasWritten = false;
/*     */ 
/*  75 */   protected boolean appendmode = false;
/*     */ 
/*  77 */   protected char header_version = '4';
/*     */ 
/*  79 */   protected PdfName catalog_version = null;
/*     */ 
/*  81 */   protected char version = '4';
/*     */ 
/*  87 */   protected PdfDictionary extensions = null;
/*     */ 
/*     */   public void setPdfVersion(char version)
/*     */   {
/*  93 */     this.version = version;
/*  94 */     if ((this.headerWasWritten) || (this.appendmode)) {
/*  95 */       setPdfVersion(getVersionAsName(version));
/*     */     }
/*     */     else
/*  98 */       this.header_version = version;
/*     */   }
/*     */ 
/*     */   public void setAtLeastPdfVersion(char version)
/*     */   {
/* 106 */     if (version > this.header_version)
/* 107 */       setPdfVersion(version);
/*     */   }
/*     */ 
/*     */   public void setPdfVersion(PdfName version)
/*     */   {
/* 115 */     if ((this.catalog_version == null) || (this.catalog_version.compareTo(version) < 0))
/* 116 */       this.catalog_version = version;
/*     */   }
/*     */ 
/*     */   public void setAppendmode(boolean appendmode)
/*     */   {
/* 124 */     this.appendmode = appendmode;
/*     */   }
/*     */ 
/*     */   public void writeHeader(OutputStreamCounter os)
/*     */     throws IOException
/*     */   {
/* 132 */     if (this.appendmode) {
/* 133 */       os.write(HEADER[0]);
/*     */     }
/*     */     else {
/* 136 */       os.write(HEADER[1]);
/* 137 */       os.write(getVersionAsByteArray(this.header_version));
/* 138 */       os.write(HEADER[2]);
/* 139 */       this.headerWasWritten = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   public PdfName getVersionAsName(char version)
/*     */   {
/* 148 */     switch (version) {
/*     */     case '2':
/* 150 */       return PdfWriter.PDF_VERSION_1_2;
/*     */     case '3':
/* 152 */       return PdfWriter.PDF_VERSION_1_3;
/*     */     case '4':
/* 154 */       return PdfWriter.PDF_VERSION_1_4;
/*     */     case '5':
/* 156 */       return PdfWriter.PDF_VERSION_1_5;
/*     */     case '6':
/* 158 */       return PdfWriter.PDF_VERSION_1_6;
/*     */     case '7':
/* 160 */       return PdfWriter.PDF_VERSION_1_7;
/*     */     }
/* 162 */     return PdfWriter.PDF_VERSION_1_4;
/*     */   }
/*     */ 
/*     */   public byte[] getVersionAsByteArray(char version)
/*     */   {
/* 171 */     return DocWriter.getISOBytes(getVersionAsName(version).toString().substring(1));
/*     */   }
/*     */ 
/*     */   public void addToCatalog(PdfDictionary catalog)
/*     */   {
/* 176 */     if (this.catalog_version != null) {
/* 177 */       catalog.put(PdfName.VERSION, this.catalog_version);
/*     */     }
/* 179 */     if (this.extensions != null)
/* 180 */       catalog.put(PdfName.EXTENSIONS, this.extensions);
/*     */   }
/*     */ 
/*     */   public void addDeveloperExtension(PdfDeveloperExtension de)
/*     */   {
/* 189 */     if (this.extensions == null) {
/* 190 */       this.extensions = new PdfDictionary();
/*     */     }
/*     */     else {
/* 193 */       PdfDictionary extension = this.extensions.getAsDict(de.getPrefix());
/* 194 */       if (extension != null) {
/* 195 */         int diff = de.getBaseversion().compareTo(extension.getAsName(PdfName.BASEVERSION));
/* 196 */         if (diff < 0)
/* 197 */           return;
/* 198 */         diff = de.getExtensionLevel() - extension.getAsNumber(PdfName.EXTENSIONLEVEL).intValue();
/* 199 */         if (diff <= 0)
/* 200 */           return;
/*     */       }
/*     */     }
/* 203 */     this.extensions.put(de.getPrefix(), de.getDeveloperExtensions());
/*     */   }
/*     */ 
/*     */   public char getVersion() {
/* 207 */     return this.version;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.internal.PdfVersionImp
 * JD-Core Version:    0.6.2
 */