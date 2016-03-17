/*     */ package org.apache.pdfbox;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSObject;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.exceptions.COSVisitorException;
/*     */ import org.apache.pdfbox.exceptions.CryptographyException;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ 
/*     */ public class WriteDecodedDoc
/*     */ {
/*     */   private static final String PASSWORD = "-password";
/*     */   private static final String NONSEQ = "-nonSeq";
/*     */ 
/*     */   /** @deprecated */
/*     */   public void doIt(String in, String out)
/*     */     throws IOException, COSVisitorException
/*     */   {
/*  65 */     doIt(in, out, "", false);
/*     */   }
/*     */ 
/*     */   public void doIt(String in, String out, String password, boolean useNonSeqParser)
/*     */     throws IOException, COSVisitorException
/*     */   {
/*  82 */     PDDocument doc = null;
/*     */     try
/*     */     {
/*  85 */       if (useNonSeqParser)
/*     */       {
/*  87 */         doc = PDDocument.loadNonSeq(new File(in), null, password);
/*  88 */         doc.setAllSecurityToBeRemoved(true);
/*     */       }
/*     */       else
/*     */       {
/*  92 */         doc = PDDocument.load(in);
/*  93 */         if (doc.isEncrypted())
/*     */         {
/*     */           try
/*     */           {
/*  97 */             doc.decrypt(password);
/*  98 */             doc.setAllSecurityToBeRemoved(true);
/*     */           }
/*     */           catch (CryptographyException e)
/*     */           {
/* 102 */             e.printStackTrace();
/* 103 */             return;
/*     */           }
/*     */         }
/*     */       }
/* 107 */       for (Iterator i = doc.getDocument().getObjects().iterator(); i.hasNext(); )
/*     */       {
/* 109 */         COSBase base = ((COSObject)i.next()).getObject();
/* 110 */         if ((base instanceof COSStream))
/*     */         {
/* 113 */           COSStream cosStream = (COSStream)base;
/* 114 */           cosStream.getUnfilteredStream();
/* 115 */           cosStream.setFilters(null);
/*     */         }
/*     */       }
/* 118 */       doc.save(out);
/*     */     }
/*     */     finally
/*     */     {
/* 122 */       if (doc != null)
/*     */       {
/* 124 */         doc.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 138 */     WriteDecodedDoc app = new WriteDecodedDoc();
/* 139 */     String password = "";
/* 140 */     boolean useNonSeqParser = false;
/* 141 */     String pdfFile = null;
/* 142 */     String outputFile = null;
/* 143 */     for (int i = 0; i < args.length; i++)
/*     */     {
/* 145 */       if (args[i].equals("-password"))
/*     */       {
/* 147 */         i++;
/* 148 */         if (i >= args.length)
/*     */         {
/* 150 */           usage();
/*     */         }
/* 152 */         password = args[i];
/*     */       }
/* 155 */       else if (args[i].equals("-nonSeq"))
/*     */       {
/* 157 */         useNonSeqParser = true;
/*     */       }
/* 161 */       else if (pdfFile == null)
/*     */       {
/* 163 */         pdfFile = args[i];
/*     */       }
/*     */       else
/*     */       {
/* 167 */         outputFile = args[i];
/*     */       }
/*     */     }
/*     */ 
/* 171 */     if (pdfFile == null)
/*     */     {
/* 173 */       usage();
/*     */     }
/*     */     else
/*     */     {
/*     */       try
/*     */       {
/* 179 */         if (outputFile == null)
/*     */         {
/* 181 */           outputFile = calculateOutputFilename(pdfFile);
/*     */         }
/* 183 */         app.doIt(pdfFile, outputFile, password, useNonSeqParser);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 187 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String calculateOutputFilename(String filename)
/*     */   {
/*     */     String outputFilename;
/* 195 */     if (filename.toLowerCase().endsWith(".pdf"))
/*     */     {
/* 197 */       outputFilename = filename.substring(0, filename.length() - 4);
/*     */     }
/*     */     else
/*     */     {
/* 201 */       outputFilename = filename;
/*     */     }
/* 203 */     String outputFilename = outputFilename + "_unc.pdf";
/* 204 */     return outputFilename;
/*     */   }
/*     */ 
/*     */   private static void usage()
/*     */   {
/* 212 */     System.err.println("usage: java -jar pdfbox-app-x.y.z.jar WriteDecodedDoc [OPTIONS] <input-file> [output-file]\n  -password <password>      Password to decrypt the document\n  -nonSeq                   Enables the new non-sequential parser\n  <input-file>              The PDF document to be decompressed\n  [output-file]             The filename for the decompressed pdf\n");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.WriteDecodedDoc
 * JD-Core Version:    0.6.2
 */