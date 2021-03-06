/*     */ package org.apache.pdfbox;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
/*     */ import org.apache.pdfbox.pdmodel.fdf.FDFDocument;
/*     */ import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
/*     */ 
/*     */ public class ExportFDF
/*     */ {
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/*  54 */     ExportFDF exporter = new ExportFDF();
/*  55 */     exporter.exportFDF(args);
/*     */   }
/*     */ 
/*     */   private void exportFDF(String[] args) throws Exception
/*     */   {
/*  60 */     PDDocument pdf = null;
/*  61 */     FDFDocument fdf = null;
/*     */     try
/*     */     {
/*  65 */       if ((args.length != 1) && (args.length != 2))
/*     */       {
/*  67 */         usage();
/*     */       }
/*     */       else
/*     */       {
/*  71 */         pdf = PDDocument.load(args[0]);
/*  72 */         PDAcroForm form = pdf.getDocumentCatalog().getAcroForm();
/*  73 */         if (form == null)
/*     */         {
/*  75 */           System.err.println("Error: This PDF does not contain a form.");
/*     */         }
/*     */         else
/*     */         {
/*  79 */           String fdfName = null;
/*  80 */           if (args.length == 2)
/*     */           {
/*  82 */             fdfName = args[1];
/*     */           }
/*  86 */           else if (args[0].length() > 4)
/*     */           {
/*  88 */             fdfName = args[0].substring(0, args[0].length() - 4) + ".fdf";
/*     */           }
/*     */ 
/*  91 */           fdf = form.exportFDF();
/*  92 */           fdf.save(fdfName);
/*     */         }
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*  98 */       close(fdf);
/*  99 */       close(pdf);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void usage()
/*     */   {
/* 108 */     System.err.println("usage: org.apache.pdfbox.ExortFDF <pdf-file> [output-fdf-file]");
/* 109 */     System.err.println("    [output-fdf-file] - Default is pdf name, test.pdf->test.fdf");
/*     */   }
/*     */ 
/*     */   public void close(FDFDocument doc)
/*     */     throws IOException
/*     */   {
/* 121 */     if (doc != null)
/*     */     {
/* 123 */       doc.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void close(PDDocument doc)
/*     */     throws IOException
/*     */   {
/* 136 */     if (doc != null)
/*     */     {
/* 138 */       doc.close();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.ExportFDF
 * JD-Core Version:    0.6.2
 */