/*     */ package org.apache.pdfbox;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Writer;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
/*     */ import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
/*     */ import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
/*     */ import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
/*     */ import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
/*     */ import org.apache.pdfbox.pdmodel.encryption.StandardDecryptionMaterial;
/*     */ import org.apache.pdfbox.util.PDFText2HTML;
/*     */ import org.apache.pdfbox.util.PDFTextStripper;
/*     */ 
/*     */ public class ExtractText
/*     */ {
/*     */   private static final String PASSWORD = "-password";
/*     */   private static final String ENCODING = "-encoding";
/*     */   private static final String CONSOLE = "-console";
/*     */   private static final String START_PAGE = "-startPage";
/*     */   private static final String END_PAGE = "-endPage";
/*     */   private static final String SORT = "-sort";
/*     */   private static final String IGNORE_BEADS = "-ignoreBeads";
/*     */   private static final String DEBUG = "-debug";
/*     */   private static final String HTML = "-html";
/*     */   private static final String FORCE = "-force";
/*     */   private static final String NONSEQ = "-nonSeq";
/*  65 */   private boolean debug = false;
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/*  84 */     ExtractText extractor = new ExtractText();
/*  85 */     extractor.startExtraction(args);
/*     */   }
/*     */ 
/*     */   public void startExtraction(String[] args)
/*     */     throws Exception
/*     */   {
/*  96 */     boolean toConsole = false;
/*  97 */     boolean toHTML = false;
/*  98 */     boolean force = false;
/*  99 */     boolean sort = false;
/* 100 */     boolean separateBeads = true;
/* 101 */     boolean useNonSeqParser = false;
/* 102 */     String password = "";
/* 103 */     String encoding = null;
/* 104 */     String pdfFile = null;
/* 105 */     String outputFile = null;
/*     */ 
/* 107 */     String ext = ".txt";
/* 108 */     int startPage = 1;
/* 109 */     int endPage = 2147483647;
/* 110 */     for (int i = 0; i < args.length; i++)
/*     */     {
/* 112 */       if (args[i].equals("-password"))
/*     */       {
/* 114 */         i++;
/* 115 */         if (i >= args.length)
/*     */         {
/* 117 */           usage();
/*     */         }
/* 119 */         password = args[i];
/*     */       }
/* 121 */       else if (args[i].equals("-encoding"))
/*     */       {
/* 123 */         i++;
/* 124 */         if (i >= args.length)
/*     */         {
/* 126 */           usage();
/*     */         }
/* 128 */         encoding = args[i];
/*     */       }
/* 130 */       else if (args[i].equals("-startPage"))
/*     */       {
/* 132 */         i++;
/* 133 */         if (i >= args.length)
/*     */         {
/* 135 */           usage();
/*     */         }
/* 137 */         startPage = Integer.parseInt(args[i]);
/*     */       }
/* 139 */       else if (args[i].equals("-html"))
/*     */       {
/* 141 */         toHTML = true;
/* 142 */         ext = ".html";
/*     */       }
/* 144 */       else if (args[i].equals("-sort"))
/*     */       {
/* 146 */         sort = true;
/*     */       }
/* 148 */       else if (args[i].equals("-ignoreBeads"))
/*     */       {
/* 150 */         separateBeads = false;
/*     */       }
/* 152 */       else if (args[i].equals("-debug"))
/*     */       {
/* 154 */         this.debug = true;
/*     */       }
/* 156 */       else if (args[i].equals("-endPage"))
/*     */       {
/* 158 */         i++;
/* 159 */         if (i >= args.length)
/*     */         {
/* 161 */           usage();
/*     */         }
/* 163 */         endPage = Integer.parseInt(args[i]);
/*     */       }
/* 165 */       else if (args[i].equals("-console"))
/*     */       {
/* 167 */         toConsole = true;
/*     */       }
/* 169 */       else if (args[i].equals("-force"))
/*     */       {
/* 171 */         force = true;
/*     */       }
/* 173 */       else if (args[i].equals("-nonSeq"))
/*     */       {
/* 175 */         useNonSeqParser = true;
/*     */       }
/* 179 */       else if (pdfFile == null)
/*     */       {
/* 181 */         pdfFile = args[i];
/*     */       }
/*     */       else
/*     */       {
/* 185 */         outputFile = args[i];
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 190 */     if (pdfFile == null)
/*     */     {
/* 192 */       usage();
/*     */     }
/*     */     else
/*     */     {
/* 197 */       Writer output = null;
/* 198 */       PDDocument document = null;
/*     */       try
/*     */       {
/* 201 */         long startTime = startProcessing("Loading PDF " + pdfFile);
/* 202 */         if ((outputFile == null) && (pdfFile.length() > 4))
/*     */         {
/* 204 */           outputFile = new File(pdfFile.substring(0, pdfFile.length() - 4) + ext).getAbsolutePath();
/*     */         }
/* 206 */         if (useNonSeqParser)
/*     */         {
/* 208 */           document = PDDocument.loadNonSeq(new File(pdfFile), null, password);
/*     */         }
/*     */         else
/*     */         {
/* 212 */           document = PDDocument.load(pdfFile, force);
/* 213 */           if (document.isEncrypted())
/*     */           {
/* 215 */             StandardDecryptionMaterial sdm = new StandardDecryptionMaterial(password);
/* 216 */             document.openProtection(sdm);
/*     */           }
/*     */         }
/*     */ 
/* 220 */         AccessPermission ap = document.getCurrentAccessPermission();
/* 221 */         if (!ap.canExtractContent())
/*     */         {
/* 223 */           throw new IOException("You do not have permission to extract text");
/*     */         }
/*     */ 
/* 226 */         stopProcessing("Time for loading: ", startTime);
/*     */ 
/* 229 */         if ((encoding == null) && (toHTML))
/*     */         {
/* 231 */           encoding = "UTF-8";
/*     */         }
/*     */ 
/* 234 */         if (toConsole)
/*     */         {
/* 236 */           output = new OutputStreamWriter(System.out);
/*     */         }
/* 240 */         else if (encoding != null)
/*     */         {
/* 242 */           output = new OutputStreamWriter(new FileOutputStream(outputFile), encoding);
/*     */         }
/*     */         else
/*     */         {
/* 248 */           output = new OutputStreamWriter(new FileOutputStream(outputFile));
/*     */         }
/*     */ 
/* 253 */         PDFTextStripper stripper = null;
/* 254 */         if (toHTML)
/*     */         {
/* 256 */           stripper = new PDFText2HTML(encoding);
/*     */         }
/*     */         else
/*     */         {
/* 260 */           stripper = new PDFTextStripper(encoding);
/*     */         }
/* 262 */         stripper.setForceParsing(force);
/* 263 */         stripper.setSortByPosition(sort);
/* 264 */         stripper.setShouldSeparateByBeads(separateBeads);
/* 265 */         stripper.setStartPage(startPage);
/* 266 */         stripper.setEndPage(endPage);
/*     */ 
/* 268 */         startTime = startProcessing("Starting text extraction");
/* 269 */         if (this.debug)
/*     */         {
/* 271 */           System.err.println("Writing to " + outputFile);
/*     */         }
/*     */ 
/* 275 */         stripper.writeText(document, output);
/*     */ 
/* 278 */         PDDocumentCatalog catalog = document.getDocumentCatalog();
/* 279 */         PDDocumentNameDictionary names = catalog.getNames();
/* 280 */         if (names != null)
/*     */         {
/* 282 */           PDEmbeddedFilesNameTreeNode embeddedFiles = names.getEmbeddedFiles();
/* 283 */           if (embeddedFiles != null)
/*     */           {
/* 285 */             Map embeddedFileNames = embeddedFiles.getNames();
/* 286 */             if (embeddedFileNames != null) {
/* 287 */               for (Map.Entry ent : embeddedFileNames.entrySet())
/*     */               {
/* 289 */                 if (this.debug)
/*     */                 {
/* 291 */                   System.err.println("Processing embedded file " + (String)ent.getKey() + ":");
/*     */                 }
/* 293 */                 PDComplexFileSpecification spec = (PDComplexFileSpecification)ent.getValue();
/* 294 */                 PDEmbeddedFile file = spec.getEmbeddedFile();
/* 295 */                 if ((file != null) && (file.getSubtype().equals("application/pdf")))
/*     */                 {
/* 297 */                   if (this.debug)
/*     */                   {
/* 299 */                     System.err.println("  is PDF (size=" + file.getSize() + ")");
/*     */                   }
/* 301 */                   InputStream fis = file.createInputStream();
/* 302 */                   PDDocument subDoc = null;
/*     */                   try
/*     */                   {
/* 305 */                     subDoc = PDDocument.load(fis);
/*     */                   }
/*     */                   finally
/*     */                   {
/* 309 */                     fis.close();
/*     */                   }
/*     */                   try
/*     */                   {
/* 313 */                     stripper.writeText(subDoc, output);
/*     */                   }
/*     */                   finally
/*     */                   {
/* 317 */                     subDoc.close();
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/* 324 */         stopProcessing("Time for extraction: ", startTime);
/*     */       }
/*     */       finally
/*     */       {
/* 328 */         if (output != null)
/*     */         {
/* 330 */           output.close();
/*     */         }
/* 332 */         if (document != null)
/*     */         {
/* 334 */           document.close();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private long startProcessing(String message)
/*     */   {
/* 342 */     if (this.debug)
/*     */     {
/* 344 */       System.err.println(message);
/*     */     }
/* 346 */     return System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */   private void stopProcessing(String message, long startTime)
/*     */   {
/* 351 */     if (this.debug)
/*     */     {
/* 353 */       long stopTime = System.currentTimeMillis();
/* 354 */       float elapsedTime = (float)(stopTime - startTime) / 1000.0F;
/* 355 */       System.err.println(message + elapsedTime + " seconds");
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void usage()
/*     */   {
/* 364 */     System.err.println("Usage: java -jar pdfbox-app-x.y.z.jar ExtractText [OPTIONS] <PDF file> [Text File]\n  -password  <password>        Password to decrypt document\n  -encoding  <output encoding> (ISO-8859-1,UTF-16BE,UTF-16LE,...)\n  -console                     Send text to console instead of file\n  -html                        Output in HTML format instead of raw text\n  -sort                        Sort the text before writing\n  -ignoreBeads                 Disables the separation by beads\n  -force                       Enables pdfbox to ignore corrupt objects\n  -debug                       Enables debug output about the time consumption of every stage\n  -startPage <number>          The first page to start extraction(1 based)\n  -endPage <number>            The last page to extract(inclusive)\n  -nonSeq                      Enables the new non-sequential parser\n  <PDF file>                   The PDF document to use\n  [Text File]                  The file to write the text to\n");
/*     */ 
/* 379 */     System.exit(1);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.ExtractText
 * JD-Core Version:    0.6.2
 */