/*     */ package org.apache.pdfbox.preflight;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.activation.DataSource;
/*     */ import javax.activation.FileDataSource;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.pdfbox.Version;
/*     */ import org.apache.pdfbox.preflight.exception.SyntaxValidationException;
/*     */ import org.apache.pdfbox.preflight.parser.PreflightParser;
/*     */ import org.apache.pdfbox.preflight.parser.XmlResultParser;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ public class Validator_A1b
/*     */ {
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/*  62 */     if (args.length == 0)
/*     */     {
/*  64 */       usage();
/*  65 */       System.exit(1);
/*     */     }
/*     */ 
/*  69 */     int posFile = 0;
/*  70 */     boolean outputXml = "xml".equals(args[posFile]);
/*  71 */     posFile += (outputXml ? 1 : 0);
/*     */ 
/*  73 */     boolean isGroup = "group".equals(args[posFile]);
/*  74 */     posFile += (isGroup ? 1 : 0);
/*     */ 
/*  76 */     boolean isBatch = "batch".equals(args[posFile]);
/*  77 */     posFile += (isBatch ? 1 : 0);
/*     */     Transformer transformer;
/*     */     XmlResultParser xrp;
/*  79 */     if ((isGroup) || (isBatch))
/*     */     {
/*  81 */       List ftp = listFiles(args[posFile]);
/*  82 */       int status = 0;
/*  83 */       if (!outputXml)
/*     */       {
/*  85 */         for (File file2 : ftp)
/*     */         {
/*  87 */           status |= runSimple(new FileDataSource(file2));
/*     */         }
/*  89 */         System.exit(status);
/*     */       } else {
/*  91 */         transformer = TransformerFactory.newInstance().newTransformer();
/*  92 */         transformer.setOutputProperty("indent", "yes");
/*  93 */         transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
/*  94 */         xrp = new XmlResultParser();
/*  95 */         if (isGroup) {
/*  96 */           Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
/*  97 */           Element root = document.createElement("preflights");
/*  98 */           document.appendChild(root);
/*  99 */           root.setAttribute("count", String.format("%d", new Object[] { Integer.valueOf(ftp.size()) }));
/* 100 */           for (File file : ftp)
/*     */           {
/* 102 */             Element result = xrp.validate(document, new FileDataSource(file));
/* 103 */             root.appendChild(result);
/*     */           }
/* 105 */           transformer.transform(new DOMSource(document), new StreamResult(new File(args[posFile] + ".preflight.xml")));
/*     */         }
/*     */         else {
/* 108 */           for (File file : ftp)
/*     */           {
/* 110 */             Element result = xrp.validate(new FileDataSource(file));
/* 111 */             Document document = result.getOwnerDocument();
/* 112 */             document.appendChild(result);
/* 113 */             transformer.transform(new DOMSource(document), new StreamResult(new File(file.getAbsolutePath() + ".preflight.xml")));
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 122 */       FileDataSource fd = new FileDataSource(args[posFile]);
/* 123 */       if (!outputXml)
/*     */       {
/* 125 */         System.exit(runSimple(fd));
/*     */       }
/*     */       else {
/* 128 */         XmlResultParser xrp = new XmlResultParser();
/* 129 */         Element result = xrp.validate(fd);
/* 130 */         Document document = result.getOwnerDocument();
/* 131 */         document.appendChild(result);
/* 132 */         Transformer transformer = TransformerFactory.newInstance().newTransformer();
/* 133 */         transformer.setOutputProperty("indent", "yes");
/* 134 */         transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
/* 135 */         transformer.transform(new DOMSource(document), new StreamResult(System.out));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void usage()
/*     */   {
/* 142 */     System.out.println("Usage : java org.apache.pdfbox.preflight.Validator_A1b [xml] [mode] <file path>");
/* 143 */     System.out.println();
/* 144 */     System.out.println(" * xml : if set, generate xml output");
/* 145 */     System.out.println(" * mode : if set, <file path> must be a file containing PDF to parse, can have 2 values");
/* 146 */     System.out.println("       batch : for each file of the list and xml file is generated");
/* 147 */     System.out.println("       group : generate an xml result for all the file of the list.");
/* 148 */     System.out.println("Version : " + Version.getVersion());
/*     */   }
/*     */ 
/*     */   private static int runSimple(DataSource fd) throws Exception {
/* 152 */     ValidationResult result = null;
/* 153 */     PreflightParser parser = new PreflightParser(fd);
/*     */     try
/*     */     {
/* 156 */       parser.parse();
/* 157 */       PreflightDocument document = parser.getPreflightDocument();
/* 158 */       document.validate();
/* 159 */       result = document.getResult();
/* 160 */       document.close();
/*     */     }
/*     */     catch (SyntaxValidationException e)
/*     */     {
/* 164 */       result = e.getResult();
/*     */     }
/*     */ 
/* 167 */     if (result.isValid())
/*     */     {
/* 169 */       System.out.println("The file " + fd.getName() + " is a valid PDF/A-1b file");
/* 170 */       System.out.println();
/* 171 */       return 0;
/*     */     }
/*     */ 
/* 175 */     System.out.println("The file" + fd.getName() + " is not valid, error(s) :");
/* 176 */     for (ValidationResult.ValidationError error : result.getErrorsList())
/*     */     {
/* 178 */       System.out.println(error.getErrorCode() + " : " + error.getDetails());
/*     */     }
/* 180 */     System.out.println();
/* 181 */     return -1;
/*     */   }
/*     */ 
/*     */   private static List<File> listFiles(String path)
/*     */     throws IOException
/*     */   {
/* 188 */     List files = new ArrayList();
/* 189 */     File f = new File(path);
/* 190 */     FileReader fr = new FileReader(f);
/* 191 */     BufferedReader buf = new BufferedReader(fr);
/* 192 */     while (buf.ready()) {
/* 193 */       File fn = new File(buf.readLine());
/* 194 */       if (fn.exists()) {
/* 195 */         files.add(fn);
/*     */       }
/*     */     }
/* 198 */     IOUtils.closeQuietly(buf);
/* 199 */     return files;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.Validator_A1b
 * JD-Core Version:    0.6.2
 */