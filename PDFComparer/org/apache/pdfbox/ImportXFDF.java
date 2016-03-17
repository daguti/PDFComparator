/*     */ package org.apache.pdfbox;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
/*     */ import org.apache.pdfbox.pdmodel.fdf.FDFDocument;
/*     */ import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
/*     */ 
/*     */ public class ImportXFDF
/*     */ {
/*     */   public void importFDF(PDDocument pdfDocument, FDFDocument fdfDocument)
/*     */     throws IOException
/*     */   {
/*  56 */     PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog();
/*  57 */     PDAcroForm acroForm = docCatalog.getAcroForm();
/*  58 */     acroForm.setCacheFields(true);
/*  59 */     acroForm.importFDF(fdfDocument);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/*  73 */     ImportXFDF importer = new ImportXFDF();
/*  74 */     importer.importXFDF(args);
/*     */   }
/*     */ 
/*     */   private void importXFDF(String[] args) throws Exception
/*     */   {
/*  79 */     PDDocument pdf = null;
/*  80 */     FDFDocument fdf = null;
/*     */     try
/*     */     {
/*  84 */       if (args.length != 3)
/*     */       {
/*  86 */         usage();
/*     */       }
/*     */       else
/*     */       {
/*  90 */         ImportFDF importer = new ImportFDF();
/*  91 */         pdf = PDDocument.load(args[0]);
/*  92 */         fdf = FDFDocument.loadXFDF(args[1]);
/*     */ 
/*  94 */         importer.importFDF(pdf, fdf);
/*  95 */         pdf.save(args[2]);
/*  96 */         fdf.save("tmp/outputXFDFtoPDF.fdf");
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 101 */       close(fdf);
/* 102 */       close(pdf);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void usage()
/*     */   {
/* 111 */     System.err.println("usage: org.apache.pdfbox.ImportXFDF <pdf-file> <fdf-file> <output-file>");
/*     */   }
/*     */ 
/*     */   public void close(FDFDocument doc)
/*     */     throws IOException
/*     */   {
/* 123 */     if (doc != null)
/*     */     {
/* 125 */       doc.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void close(PDDocument doc)
/*     */     throws IOException
/*     */   {
/* 138 */     if (doc != null)
/*     */     {
/* 140 */       doc.close();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.ImportXFDF
 * JD-Core Version:    0.6.2
 */