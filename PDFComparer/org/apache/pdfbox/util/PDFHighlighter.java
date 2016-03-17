/*     */ package org.apache.pdfbox.util;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Writer;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ 
/*     */ public class PDFHighlighter extends PDFTextStripper
/*     */ {
/*  42 */   private Writer highlighterOutput = null;
/*     */   private String[] searchedWords;
/*  46 */   private ByteArrayOutputStream textOS = null;
/*  47 */   private Writer textWriter = null;
/*     */   private static final String ENCODING = "UTF-16";
/*     */ 
/*     */   public PDFHighlighter()
/*     */     throws IOException
/*     */   {
/*  57 */     super("UTF-16");
/*  58 */     super.setLineSeparator("");
/*  59 */     super.setPageSeparator("");
/*  60 */     super.setWordSeparator("");
/*  61 */     super.setShouldSeparateByBeads(false);
/*  62 */     super.setSuppressDuplicateOverlappingText(false);
/*     */   }
/*     */ 
/*     */   public void generateXMLHighlight(PDDocument pdDocument, String highlightWord, Writer xmlOutput)
/*     */     throws IOException
/*     */   {
/*  76 */     generateXMLHighlight(pdDocument, new String[] { highlightWord }, xmlOutput);
/*     */   }
/*     */ 
/*     */   public void generateXMLHighlight(PDDocument pdDocument, String[] sWords, Writer xmlOutput)
/*     */     throws IOException
/*     */   {
/*  90 */     this.highlighterOutput = xmlOutput;
/*  91 */     this.searchedWords = sWords;
/*  92 */     this.highlighterOutput.write("<XML>\n<Body units=characters  version=2>\n<Highlight>\n");
/*     */ 
/*  98 */     this.textOS = new ByteArrayOutputStream();
/*  99 */     this.textWriter = new OutputStreamWriter(this.textOS, "UTF-16");
/* 100 */     writeText(pdDocument, this.textWriter);
/* 101 */     this.highlighterOutput.write("</Highlight>\n</Body>\n</XML>");
/* 102 */     this.highlighterOutput.flush();
/*     */   }
/*     */ 
/*     */   protected void endPage(PDPage pdPage)
/*     */     throws IOException
/*     */   {
/* 110 */     this.textWriter.flush();
/*     */ 
/* 112 */     String page = new String(this.textOS.toByteArray(), "UTF-16");
/* 113 */     this.textOS.reset();
/*     */ 
/* 120 */     if (page.indexOf("a") != -1)
/*     */     {
/* 122 */       page = page.replaceAll("a[0-9]{1,3}", ".");
/*     */     }
/*     */ 
/* 125 */     for (int i = 0; i < this.searchedWords.length; i++)
/*     */     {
/* 127 */       Pattern pattern = Pattern.compile(this.searchedWords[i], 2);
/* 128 */       Matcher matcher = pattern.matcher(page);
/* 129 */       while (matcher.find())
/*     */       {
/* 131 */         int begin = matcher.start();
/* 132 */         int end = matcher.end();
/* 133 */         this.highlighterOutput.write("    <loc pg=" + (getCurrentPageNo() - 1) + " pos=" + begin + " len=" + (end - begin) + ">\n");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws IOException
/*     */   {
/* 151 */     PDFHighlighter xmlExtractor = new PDFHighlighter();
/* 152 */     PDDocument doc = null;
/*     */     try
/*     */     {
/* 155 */       if (args.length < 2)
/*     */       {
/* 157 */         usage();
/*     */       }
/* 159 */       String[] highlightStrings = new String[args.length - 1];
/* 160 */       System.arraycopy(args, 1, highlightStrings, 0, highlightStrings.length);
/* 161 */       doc = PDDocument.load(args[0]);
/*     */ 
/* 163 */       xmlExtractor.generateXMLHighlight(doc, highlightStrings, new OutputStreamWriter(System.out));
/*     */     }
/*     */     finally
/*     */     {
/* 170 */       if (doc != null)
/*     */       {
/* 172 */         doc.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void usage()
/*     */   {
/* 179 */     System.err.println("usage: java " + PDFHighlighter.class.getName() + " <pdf file> word1 word2 word3 ...");
/* 180 */     System.exit(1);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.PDFHighlighter
 * JD-Core Version:    0.6.2
 */