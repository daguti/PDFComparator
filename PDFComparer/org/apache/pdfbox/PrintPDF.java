/*     */ package org.apache.pdfbox;
/*     */ 
/*     */ import java.awt.print.PrinterJob;
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import javax.print.PrintService;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDPageable;
/*     */ 
/*     */ public class PrintPDF
/*     */ {
/*     */   private static final String PASSWORD = "-password";
/*     */   private static final String SILENT = "-silentPrint";
/*     */   private static final String PRINTER_NAME = "-printerName";
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/*  58 */     String password = "";
/*  59 */     String pdfFile = null;
/*  60 */     boolean silentPrint = false;
/*  61 */     String printerName = null;
/*  62 */     for (int i = 0; i < args.length; i++)
/*     */     {
/*  64 */       if (args[i].equals("-password"))
/*     */       {
/*  66 */         i++;
/*  67 */         if (i >= args.length)
/*     */         {
/*  69 */           usage();
/*     */         }
/*  71 */         password = args[i];
/*     */       }
/*  73 */       else if (args[i].equals("-printerName"))
/*     */       {
/*  75 */         i++;
/*  76 */         if (i >= args.length)
/*     */         {
/*  78 */           usage();
/*     */         }
/*  80 */         printerName = args[i];
/*     */       }
/*  82 */       else if (args[i].equals("-silentPrint"))
/*     */       {
/*  84 */         silentPrint = true;
/*     */       }
/*     */       else
/*     */       {
/*  88 */         pdfFile = args[i];
/*     */       }
/*     */     }
/*     */ 
/*  92 */     if (pdfFile == null)
/*     */     {
/*  94 */       usage();
/*     */     }
/*     */ 
/*  97 */     PDDocument document = null;
/*     */     try
/*     */     {
/* 100 */       document = PDDocument.load(pdfFile);
/*     */ 
/* 102 */       if (document.isEncrypted())
/*     */       {
/* 104 */         document.decrypt(password);
/*     */       }
/*     */ 
/* 107 */       PrinterJob printJob = PrinterJob.getPrinterJob();
/* 108 */       printJob.setJobName(new File(pdfFile).getName());
/*     */ 
/* 110 */       if (printerName != null)
/*     */       {
/* 112 */         PrintService[] printService = PrinterJob.lookupPrintServices();
/* 113 */         boolean printerFound = false;
/* 114 */         for (int i = 0; (!printerFound) && (i < printService.length); i++)
/*     */         {
/* 116 */           if (printService[i].getName().indexOf(printerName) != -1)
/*     */           {
/* 118 */             printJob.setPrintService(printService[i]);
/* 119 */             printerFound = true;
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 124 */       printJob.setPageable(new PDPageable(document, printJob));
/* 125 */       if ((silentPrint) || (printJob.printDialog()))
/*     */       {
/* 127 */         printJob.print();
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 132 */       if (document != null)
/*     */       {
/* 134 */         document.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void usage()
/*     */   {
/* 144 */     System.err.println("Usage: java -jar pdfbox-app-x.y.z.jar PrintPDF [OPTIONS] <PDF file>\n  -password  <password>        Password to decrypt document\n  -silentPrint                 Print without prompting for printer info\n");
/*     */ 
/* 148 */     System.exit(1);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.PrintPDF
 * JD-Core Version:    0.6.2
 */