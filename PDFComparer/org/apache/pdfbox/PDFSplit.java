/*     */ package org.apache.pdfbox;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.exceptions.COSVisitorException;
/*     */ import org.apache.pdfbox.pdfwriter.COSWriter;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.util.Splitter;
/*     */ 
/*     */ public class PDFSplit
/*     */ {
/*     */   private static final String PASSWORD = "-password";
/*     */   private static final String SPLIT = "-split";
/*     */   private static final String START_PAGE = "-startPage";
/*     */   private static final String END_PAGE = "-endPage";
/*     */   private static final String NONSEQ = "-nonSeq";
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/*  60 */     PDFSplit split = new PDFSplit();
/*  61 */     split.split(args);
/*     */   }
/*     */ 
/*     */   private void split(String[] args) throws Exception
/*     */   {
/*  66 */     String password = "";
/*  67 */     String split = null;
/*  68 */     String startPage = null;
/*  69 */     String endPage = null;
/*  70 */     boolean useNonSeqParser = false;
/*  71 */     Splitter splitter = new Splitter();
/*  72 */     String pdfFile = null;
/*  73 */     for (int i = 0; i < args.length; i++)
/*     */     {
/*  75 */       if (args[i].equals("-password"))
/*     */       {
/*  77 */         i++;
/*  78 */         if (i >= args.length)
/*     */         {
/*  80 */           usage();
/*     */         }
/*  82 */         password = args[i];
/*     */       }
/*  84 */       else if (args[i].equals("-split"))
/*     */       {
/*  86 */         i++;
/*  87 */         if (i >= args.length)
/*     */         {
/*  89 */           usage();
/*     */         }
/*  91 */         split = args[i];
/*     */       }
/*  93 */       else if (args[i].equals("-startPage"))
/*     */       {
/*  95 */         i++;
/*  96 */         if (i >= args.length)
/*     */         {
/*  98 */           usage();
/*     */         }
/* 100 */         startPage = args[i];
/*     */       }
/* 102 */       else if (args[i].equals("-endPage"))
/*     */       {
/* 104 */         i++;
/* 105 */         if (i >= args.length)
/*     */         {
/* 107 */           usage();
/*     */         }
/* 109 */         endPage = args[i];
/*     */       }
/* 111 */       else if (args[i].equals("-nonSeq"))
/*     */       {
/* 113 */         useNonSeqParser = true;
/*     */       }
/* 117 */       else if (pdfFile == null)
/*     */       {
/* 119 */         pdfFile = args[i];
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 124 */     if (pdfFile == null)
/*     */     {
/* 126 */       usage();
/*     */     }
/*     */     else
/*     */     {
/* 130 */       PDDocument document = null;
/* 131 */       List documents = null;
/*     */       try
/*     */       {
/* 134 */         if (useNonSeqParser)
/*     */         {
/* 136 */           document = PDDocument.loadNonSeq(new File(pdfFile), null, password);
/*     */         }
/*     */         else
/*     */         {
/* 140 */           document = PDDocument.load(pdfFile);
/* 141 */           if (document.isEncrypted())
/*     */           {
/* 143 */             document.decrypt(password);
/*     */           }
/*     */         }
/*     */ 
/* 147 */         int numberOfPages = document.getNumberOfPages();
/* 148 */         boolean startEndPageSet = false;
/* 149 */         if (startPage != null)
/*     */         {
/* 151 */           splitter.setStartPage(Integer.parseInt(startPage));
/* 152 */           startEndPageSet = true;
/* 153 */           if (split == null)
/*     */           {
/* 155 */             splitter.setSplitAtPage(numberOfPages);
/*     */           }
/*     */         }
/* 158 */         if (endPage != null)
/*     */         {
/* 160 */           splitter.setEndPage(Integer.parseInt(endPage));
/* 161 */           startEndPageSet = true;
/* 162 */           if (split == null)
/*     */           {
/* 164 */             splitter.setSplitAtPage(Integer.parseInt(endPage));
/*     */           }
/*     */         }
/* 167 */         if (split != null)
/*     */         {
/* 169 */           splitter.setSplitAtPage(Integer.parseInt(split));
/*     */         }
/* 173 */         else if (!startEndPageSet)
/*     */         {
/* 175 */           splitter.setSplitAtPage(1);
/*     */         }
/*     */ 
/* 179 */         documents = splitter.split(document);
/* 180 */         for (int i = 0; i < documents.size(); i++)
/*     */         {
/* 182 */           PDDocument doc = (PDDocument)documents.get(i);
/* 183 */           String fileName = pdfFile.substring(0, pdfFile.length() - 4) + "-" + i + ".pdf";
/* 184 */           writeDocument(doc, fileName);
/* 185 */           doc.close();
/*     */         }
/*     */ 
/*     */       }
/*     */       finally
/*     */       {
/* 191 */         if (document != null)
/*     */         {
/* 193 */           document.close();
/*     */         }
/* 195 */         for (int i = 0; (documents != null) && (i < documents.size()); i++)
/*     */         {
/* 197 */           PDDocument doc = (PDDocument)documents.get(i);
/* 198 */           doc.close();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static final void writeDocument(PDDocument doc, String fileName) throws IOException, COSVisitorException
/*     */   {
/* 206 */     FileOutputStream output = null;
/* 207 */     COSWriter writer = null;
/*     */     try
/*     */     {
/* 210 */       output = new FileOutputStream(fileName);
/* 211 */       writer = new COSWriter(output);
/* 212 */       writer.write(doc);
/*     */     }
/*     */     finally
/*     */     {
/* 216 */       if (output != null)
/*     */       {
/* 218 */         output.close();
/*     */       }
/* 220 */       if (writer != null)
/*     */       {
/* 222 */         writer.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void usage()
/*     */   {
/* 232 */     System.err.println("Usage: java -jar pdfbox-app-x.y.z.jar PDFSplit [OPTIONS] <PDF file>\n  -password  <password>  Password to decrypt document\n  -split     <integer>   split after this many pages (default 1, if startPage and endPage are unset)\n  -startPage <integer>   start page\n  -endPage   <integer>   end page\n  -nonSeq                Enables the new non-sequential parser\n  <PDF file>             The PDF document to use\n");
/*     */ 
/* 240 */     System.exit(1);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.PDFSplit
 * JD-Core Version:    0.6.2
 */