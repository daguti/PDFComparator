/*     */ package org.apache.pdfbox;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
/*     */ import org.apache.pdfbox.pdmodel.fdf.FDFDocument;
/*     */ import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
/*     */ 
/*     */ public class ImportFDF
/*     */ {
/*     */   public void importFDF(PDDocument pdfDocument, FDFDocument fdfDocument)
/*     */     throws IOException
/*     */   {
/*  55 */     PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog();
/*  56 */     PDAcroForm acroForm = docCatalog.getAcroForm();
/*  57 */     acroForm.setCacheFields(true);
/*  58 */     acroForm.importFDF(fdfDocument);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/*  72 */     ImportFDF importer = new ImportFDF();
/*  73 */     importer.importFDF(args);
/*     */   }
/*     */ 
/*     */   private void importFDF(String[] args) throws Exception
/*     */   {
/*  78 */     PDDocument pdf = null;
/*  79 */     FDFDocument fdf = null;
/*     */     try
/*     */     {
/*  83 */       if (args.length != 3)
/*     */       {
/*  85 */         usage();
/*     */       }
/*     */       else
/*     */       {
/*  89 */         ImportFDF importer = new ImportFDF();
/*     */ 
/*  91 */         pdf = PDDocument.load(args[0]);
/*  92 */         fdf = FDFDocument.load(args[1]);
/*  93 */         importer.importFDF(pdf, fdf);
/*     */ 
/*  95 */         pdf.save(args[2]);
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 100 */       close(fdf);
/* 101 */       close(pdf);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void usage()
/*     */   {
/* 110 */     System.err.println("usage: org.apache.pdfbox.ImportFDF <pdf-file> <fdf-file> <output-file>");
/*     */   }
/*     */ 
/*     */   public void close(FDFDocument doc)
/*     */     throws IOException
/*     */   {
/* 122 */     if (doc != null)
/*     */     {
/* 124 */       doc.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void close(PDDocument doc)
/*     */     throws IOException
/*     */   {
/* 137 */     if (doc != null)
/*     */     {
/* 139 */       doc.close();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.ImportFDF
 * JD-Core Version:    0.6.2
 */