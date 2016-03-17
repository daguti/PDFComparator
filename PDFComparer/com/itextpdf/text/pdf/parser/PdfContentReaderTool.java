/*     */ package com.itextpdf.text.pdf.parser;
/*     */ 
/*     */ import com.itextpdf.text.pdf.PdfDictionary;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfObject;
/*     */ import com.itextpdf.text.pdf.PdfReader;
/*     */ import com.itextpdf.text.pdf.PdfStream;
/*     */ import com.itextpdf.text.pdf.RandomAccessFileOrArray;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class PdfContentReaderTool
/*     */ {
/*     */   public static String getDictionaryDetail(PdfDictionary dic)
/*     */   {
/*  76 */     return getDictionaryDetail(dic, 0);
/*     */   }
/*     */ 
/*     */   public static String getDictionaryDetail(PdfDictionary dic, int depth)
/*     */   {
/*  86 */     StringBuffer builder = new StringBuffer();
/*  87 */     builder.append('(');
/*  88 */     List subDictionaries = new ArrayList();
/*  89 */     for (PdfName key : dic.getKeys()) {
/*  90 */       PdfObject val = dic.getDirectObject(key);
/*  91 */       if (val.isDictionary())
/*  92 */         subDictionaries.add(key);
/*  93 */       builder.append(key);
/*  94 */       builder.append('=');
/*  95 */       builder.append(val);
/*  96 */       builder.append(", ");
/*     */     }
/*  98 */     if (builder.length() >= 2)
/*  99 */       builder.setLength(builder.length() - 2);
/* 100 */     builder.append(')');
/* 101 */     for (PdfName pdfSubDictionaryName : subDictionaries) {
/* 102 */       builder.append('\n');
/* 103 */       for (int i = 0; i < depth + 1; i++) {
/* 104 */         builder.append('\t');
/*     */       }
/* 106 */       builder.append("Subdictionary ");
/* 107 */       builder.append(pdfSubDictionaryName);
/* 108 */       builder.append(" = ");
/* 109 */       builder.append(getDictionaryDetail(dic.getAsDict(pdfSubDictionaryName), depth + 1));
/*     */     }
/* 111 */     return builder.toString();
/*     */   }
/*     */ 
/*     */   public static String getXObjectDetail(PdfDictionary resourceDic)
/*     */     throws IOException
/*     */   {
/* 122 */     StringBuilder sb = new StringBuilder();
/*     */ 
/* 124 */     PdfDictionary xobjects = resourceDic.getAsDict(PdfName.XOBJECT);
/* 125 */     if (xobjects == null)
/* 126 */       return "No XObjects";
/* 127 */     for (PdfName entryName : xobjects.getKeys()) {
/* 128 */       PdfStream xobjectStream = xobjects.getAsStream(entryName);
/*     */ 
/* 130 */       sb.append("------ " + entryName + " - subtype = " + xobjectStream.get(PdfName.SUBTYPE) + " = " + xobjectStream.getAsNumber(PdfName.LENGTH) + " bytes ------\n");
/*     */ 
/* 132 */       if (!xobjectStream.get(PdfName.SUBTYPE).equals(PdfName.IMAGE))
/*     */       {
/* 134 */         byte[] contentBytes = ContentByteUtils.getContentBytesFromContentObject(xobjectStream);
/*     */ 
/* 136 */         InputStream is = new ByteArrayInputStream(contentBytes);
/*     */         int ch;
/* 138 */         while ((ch = is.read()) != -1) {
/* 139 */           sb.append((char)ch);
/*     */         }
/*     */ 
/* 142 */         sb.append("------ " + entryName + " - subtype = " + xobjectStream.get(PdfName.SUBTYPE) + "End of Content" + "------\n");
/*     */       }
/*     */     }
/*     */ 
/* 146 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static void listContentStreamForPage(PdfReader reader, int pageNum, PrintWriter out)
/*     */     throws IOException
/*     */   {
/* 158 */     out.println("==============Page " + pageNum + "====================");
/* 159 */     out.println("- - - - - Dictionary - - - - - -");
/* 160 */     PdfDictionary pageDictionary = reader.getPageN(pageNum);
/* 161 */     out.println(getDictionaryDetail(pageDictionary));
/*     */ 
/* 163 */     out.println("- - - - - XObject Summary - - - - - -");
/* 164 */     out.println(getXObjectDetail(pageDictionary.getAsDict(PdfName.RESOURCES)));
/*     */ 
/* 166 */     out.println("- - - - - Content Stream - - - - - -");
/* 167 */     RandomAccessFileOrArray f = reader.getSafeFile();
/*     */ 
/* 169 */     byte[] contentBytes = reader.getPageContent(pageNum, f);
/* 170 */     f.close();
/*     */ 
/* 172 */     out.flush();
/*     */ 
/* 174 */     InputStream is = new ByteArrayInputStream(contentBytes);
/*     */     int ch;
/* 176 */     while ((ch = is.read()) != -1) {
/* 177 */       out.print((char)ch);
/*     */     }
/*     */ 
/* 180 */     out.flush();
/*     */ 
/* 182 */     out.println("- - - - - Text Extraction - - - - - -");
/* 183 */     String extractedText = PdfTextExtractor.getTextFromPage(reader, pageNum, new LocationTextExtractionStrategy());
/* 184 */     if (extractedText.length() != 0)
/* 185 */       out.println(extractedText);
/*     */     else {
/* 187 */       out.println("No text found on page " + pageNum);
/*     */     }
/* 189 */     out.println();
/*     */   }
/*     */ 
/*     */   public static void listContentStream(File pdfFile, PrintWriter out)
/*     */     throws IOException
/*     */   {
/* 201 */     PdfReader reader = new PdfReader(pdfFile.getCanonicalPath());
/*     */ 
/* 203 */     int maxPageNum = reader.getNumberOfPages();
/*     */ 
/* 205 */     for (int pageNum = 1; pageNum <= maxPageNum; pageNum++)
/* 206 */       listContentStreamForPage(reader, pageNum, out);
/*     */   }
/*     */ 
/*     */   public static void listContentStream(File pdfFile, int pageNum, PrintWriter out)
/*     */     throws IOException
/*     */   {
/* 220 */     PdfReader reader = new PdfReader(pdfFile.getCanonicalPath());
/*     */ 
/* 222 */     listContentStreamForPage(reader, pageNum, out);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */     try
/*     */     {
/* 231 */       if ((args.length < 1) || (args.length > 3)) {
/* 232 */         System.out.println("Usage:  PdfContentReaderTool <pdf file> [<output file>|stdout] [<page num>]");
/* 233 */         return;
/*     */       }
/*     */ 
/* 236 */       PrintWriter writer = new PrintWriter(System.out);
/* 237 */       if ((args.length >= 2) && 
/* 238 */         (args[1].compareToIgnoreCase("stdout") != 0)) {
/* 239 */         System.out.println("Writing PDF content to " + args[1]);
/* 240 */         writer = new PrintWriter(new FileOutputStream(new File(args[1])));
/*     */       }
/*     */ 
/* 244 */       int pageNum = -1;
/* 245 */       if (args.length >= 3) {
/* 246 */         pageNum = Integer.parseInt(args[2]);
/*     */       }
/*     */ 
/* 249 */       if (pageNum == -1)
/* 250 */         listContentStream(new File(args[0]), writer);
/*     */       else {
/* 252 */         listContentStream(new File(args[0]), pageNum, writer);
/*     */       }
/* 254 */       writer.flush();
/*     */ 
/* 256 */       if (args.length >= 2) {
/* 257 */         writer.close();
/* 258 */         System.out.println("Finished writing content to " + args[1]);
/*     */       }
/*     */     } catch (Exception e) {
/* 261 */       e.printStackTrace(System.err);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.PdfContentReaderTool
 * JD-Core Version:    0.6.2
 */