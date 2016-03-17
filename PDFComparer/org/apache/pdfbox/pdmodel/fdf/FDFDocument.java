/*     */ package org.apache.pdfbox.pdmodel.fdf;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Writer;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.exceptions.COSVisitorException;
/*     */ import org.apache.pdfbox.pdfparser.NonSequentialPDFParser;
/*     */ import org.apache.pdfbox.pdfwriter.COSWriter;
/*     */ import org.apache.pdfbox.util.XMLUtil;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ public class FDFDocument
/*     */   implements Closeable
/*     */ {
/*     */   private COSDocument document;
/*     */ 
/*     */   public FDFDocument()
/*     */     throws IOException
/*     */   {
/*  58 */     this.document = new COSDocument();
/*  59 */     this.document.setHeaderString("%FDF-1.2");
/*     */ 
/*  62 */     this.document.setTrailer(new COSDictionary());
/*     */ 
/*  65 */     FDFCatalog catalog = new FDFCatalog();
/*  66 */     setCatalog(catalog);
/*     */   }
/*     */ 
/*     */   public FDFDocument(COSDocument doc)
/*     */   {
/*  77 */     this.document = doc;
/*     */   }
/*     */ 
/*     */   public FDFDocument(Document doc)
/*     */     throws IOException
/*     */   {
/*  88 */     this();
/*  89 */     Element xfdf = doc.getDocumentElement();
/*  90 */     if (!xfdf.getNodeName().equals("xfdf"))
/*     */     {
/*  92 */       throw new IOException("Error while importing xfdf document, root should be 'xfdf' and not '" + xfdf.getNodeName() + "'");
/*     */     }
/*     */ 
/*  95 */     FDFCatalog cat = new FDFCatalog(xfdf);
/*  96 */     setCatalog(cat);
/*     */   }
/*     */ 
/*     */   public void writeXML(Writer output)
/*     */     throws IOException
/*     */   {
/* 108 */     output.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
/* 109 */     output.write("<xfdf xmlns=\"http://ns.adobe.com/xfdf/\" xml:space=\"preserve\">\n");
/*     */ 
/* 111 */     getCatalog().writeXML(output);
/*     */ 
/* 113 */     output.write("</xfdf>\n");
/*     */   }
/*     */ 
/*     */   public COSDocument getDocument()
/*     */   {
/* 125 */     return this.document;
/*     */   }
/*     */ 
/*     */   public FDFCatalog getCatalog()
/*     */   {
/* 135 */     FDFCatalog retval = null;
/* 136 */     COSDictionary trailer = this.document.getTrailer();
/* 137 */     COSDictionary root = (COSDictionary)trailer.getDictionaryObject(COSName.ROOT);
/* 138 */     if (root == null)
/*     */     {
/* 140 */       retval = new FDFCatalog();
/* 141 */       setCatalog(retval);
/*     */     }
/*     */     else
/*     */     {
/* 145 */       retval = new FDFCatalog(root);
/*     */     }
/* 147 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setCatalog(FDFCatalog cat)
/*     */   {
/* 157 */     COSDictionary trailer = this.document.getTrailer();
/* 158 */     trailer.setItem(COSName.ROOT, cat);
/*     */   }
/*     */ 
/*     */   public static FDFDocument load(String filename)
/*     */     throws IOException
/*     */   {
/* 172 */     return load(new BufferedInputStream(new FileInputStream(filename)));
/*     */   }
/*     */ 
/*     */   public static FDFDocument load(File file)
/*     */     throws IOException
/*     */   {
/* 186 */     return load(new BufferedInputStream(new FileInputStream(file)));
/*     */   }
/*     */ 
/*     */   public static FDFDocument load(InputStream input)
/*     */     throws IOException
/*     */   {
/* 200 */     NonSequentialPDFParser parser = new NonSequentialPDFParser(input);
/* 201 */     parser.parse();
/* 202 */     return parser.getFDFDocument();
/*     */   }
/*     */ 
/*     */   public static FDFDocument loadXFDF(String filename)
/*     */     throws IOException
/*     */   {
/* 216 */     return loadXFDF(new BufferedInputStream(new FileInputStream(filename)));
/*     */   }
/*     */ 
/*     */   public static FDFDocument loadXFDF(File file)
/*     */     throws IOException
/*     */   {
/* 230 */     return loadXFDF(new BufferedInputStream(new FileInputStream(file)));
/*     */   }
/*     */ 
/*     */   public static FDFDocument loadXFDF(InputStream input)
/*     */     throws IOException
/*     */   {
/* 244 */     Document doc = XMLUtil.parse(input);
/* 245 */     return new FDFDocument(doc);
/*     */   }
/*     */ 
/*     */   public void save(File fileName)
/*     */     throws IOException, COSVisitorException
/*     */   {
/* 258 */     save(new FileOutputStream(fileName));
/*     */   }
/*     */ 
/*     */   public void save(String fileName)
/*     */     throws IOException, COSVisitorException
/*     */   {
/* 271 */     save(new FileOutputStream(fileName));
/*     */   }
/*     */ 
/*     */   public void save(OutputStream output)
/*     */     throws IOException, COSVisitorException
/*     */   {
/* 284 */     COSWriter writer = null;
/*     */     try
/*     */     {
/* 287 */       writer = new COSWriter(output);
/* 288 */       writer.write(this.document);
/* 289 */       writer.close();
/*     */     }
/*     */     finally
/*     */     {
/* 293 */       if (writer != null)
/*     */       {
/* 295 */         writer.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void saveXFDF(File fileName)
/*     */     throws IOException, COSVisitorException
/*     */   {
/* 310 */     saveXFDF(new BufferedWriter(new FileWriter(fileName)));
/*     */   }
/*     */ 
/*     */   public void saveXFDF(String fileName)
/*     */     throws IOException, COSVisitorException
/*     */   {
/* 323 */     saveXFDF(new BufferedWriter(new FileWriter(fileName)));
/*     */   }
/*     */ 
/*     */   public void saveXFDF(Writer output)
/*     */     throws IOException, COSVisitorException
/*     */   {
/*     */     try
/*     */     {
/* 338 */       writeXML(output);
/*     */     }
/*     */     finally
/*     */     {
/* 342 */       if (output != null)
/*     */       {
/* 344 */         output.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 356 */     this.document.close();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.fdf.FDFDocument
 * JD-Core Version:    0.6.2
 */