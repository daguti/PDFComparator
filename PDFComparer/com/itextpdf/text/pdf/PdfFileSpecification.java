/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.io.StreamUtil;
/*     */ import com.itextpdf.text.pdf.collection.PdfCollectionItem;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.URL;
/*     */ 
/*     */ public class PdfFileSpecification extends PdfDictionary
/*     */ {
/*     */   protected PdfWriter writer;
/*     */   protected PdfIndirectReference ref;
/*     */ 
/*     */   public PdfFileSpecification()
/*     */   {
/*  64 */     super(PdfName.FILESPEC);
/*     */   }
/*     */ 
/*     */   public static PdfFileSpecification url(PdfWriter writer, String url)
/*     */   {
/*  74 */     PdfFileSpecification fs = new PdfFileSpecification();
/*  75 */     fs.writer = writer;
/*  76 */     fs.put(PdfName.FS, PdfName.URL);
/*  77 */     fs.put(PdfName.F, new PdfString(url));
/*  78 */     return fs;
/*     */   }
/*     */ 
/*     */   public static PdfFileSpecification fileEmbedded(PdfWriter writer, String filePath, String fileDisplay, byte[] fileStore)
/*     */     throws IOException
/*     */   {
/*  93 */     return fileEmbedded(writer, filePath, fileDisplay, fileStore, 9);
/*     */   }
/*     */ 
/*     */   public static PdfFileSpecification fileEmbedded(PdfWriter writer, String filePath, String fileDisplay, byte[] fileStore, int compressionLevel)
/*     */     throws IOException
/*     */   {
/* 111 */     return fileEmbedded(writer, filePath, fileDisplay, fileStore, null, null, compressionLevel);
/*     */   }
/*     */ 
/*     */   public static PdfFileSpecification fileEmbedded(PdfWriter writer, String filePath, String fileDisplay, byte[] fileStore, boolean compress)
/*     */     throws IOException
/*     */   {
/* 129 */     return fileEmbedded(writer, filePath, fileDisplay, fileStore, null, null, compress ? 9 : 0);
/*     */   }
/*     */ 
/*     */   public static PdfFileSpecification fileEmbedded(PdfWriter writer, String filePath, String fileDisplay, byte[] fileStore, boolean compress, String mimeType, PdfDictionary fileParameter)
/*     */     throws IOException
/*     */   {
/* 148 */     return fileEmbedded(writer, filePath, fileDisplay, fileStore, mimeType, fileParameter, compress ? 9 : 0);
/*     */   }
/*     */ 
/*     */   public static PdfFileSpecification fileEmbedded(PdfWriter writer, String filePath, String fileDisplay, byte[] fileStore, String mimeType, PdfDictionary fileParameter, int compressionLevel)
/*     */     throws IOException
/*     */   {
/* 167 */     PdfFileSpecification fs = new PdfFileSpecification();
/* 168 */     fs.writer = writer;
/* 169 */     fs.put(PdfName.F, new PdfString(fileDisplay));
/* 170 */     fs.setUnicodeFileName(fileDisplay, false);
/*     */ 
/* 172 */     InputStream in = null;
/*     */ 
/* 174 */     PdfIndirectReference refFileLength = null;
/*     */     PdfIndirectReference ref;
/*     */     try
/*     */     {
/*     */       PdfEFStream stream;
/*     */       PdfEFStream stream;
/* 176 */       if (fileStore == null) {
/* 177 */         refFileLength = writer.getPdfIndirectReference();
/* 178 */         File file = new File(filePath);
/* 179 */         if (file.canRead()) {
/* 180 */           in = new FileInputStream(filePath);
/*     */         }
/* 183 */         else if ((filePath.startsWith("file:/")) || (filePath.startsWith("http://")) || (filePath.startsWith("https://")) || (filePath.startsWith("jar:"))) {
/* 184 */           in = new URL(filePath).openStream();
/*     */         }
/*     */         else {
/* 187 */           in = StreamUtil.getResourceStream(filePath);
/* 188 */           if (in == null) {
/* 189 */             throw new IOException(MessageLocalization.getComposedMessage("1.not.found.as.file.or.resource", new Object[] { filePath }));
/*     */           }
/*     */         }
/* 192 */         stream = new PdfEFStream(in, writer);
/*     */       }
/*     */       else {
/* 195 */         stream = new PdfEFStream(fileStore);
/*     */       }
/* 197 */       stream.put(PdfName.TYPE, PdfName.EMBEDDEDFILE);
/* 198 */       stream.flateCompress(compressionLevel);
/* 199 */       PdfDictionary param = new PdfDictionary();
/* 200 */       if (fileParameter != null) {
/* 201 */         param.merge(fileParameter);
/*     */       }
/* 203 */       if (!param.contains(PdfName.MODDATE)) {
/* 204 */         param.put(PdfName.MODDATE, new PdfDate());
/*     */       }
/* 206 */       if (fileStore == null) {
/* 207 */         stream.put(PdfName.PARAMS, refFileLength);
/*     */       }
/*     */       else {
/* 210 */         param.put(PdfName.SIZE, new PdfNumber(stream.getRawLength()));
/* 211 */         stream.put(PdfName.PARAMS, param);
/*     */       }
/*     */ 
/* 214 */       if (mimeType != null) {
/* 215 */         stream.put(PdfName.SUBTYPE, new PdfName(mimeType));
/*     */       }
/* 217 */       ref = writer.addToBody(stream).getIndirectReference();
/* 218 */       if (fileStore == null) {
/* 219 */         stream.writeLength();
/* 220 */         param.put(PdfName.SIZE, new PdfNumber(stream.getRawLength()));
/* 221 */         writer.addToBody(param, refFileLength);
/*     */       }
/*     */     }
/*     */     finally {
/* 225 */       if (in != null) try {
/* 226 */           in.close(); } catch (Exception e) {  }
/*     */  
/*     */     }
/* 228 */     PdfDictionary f = new PdfDictionary();
/* 229 */     f.put(PdfName.F, ref);
/* 230 */     f.put(PdfName.UF, ref);
/* 231 */     fs.put(PdfName.EF, f);
/* 232 */     return fs;
/*     */   }
/*     */ 
/*     */   public static PdfFileSpecification fileExtern(PdfWriter writer, String filePath)
/*     */   {
/* 242 */     PdfFileSpecification fs = new PdfFileSpecification();
/* 243 */     fs.writer = writer;
/* 244 */     fs.put(PdfName.F, new PdfString(filePath));
/* 245 */     fs.setUnicodeFileName(filePath, false);
/* 246 */     return fs;
/*     */   }
/*     */ 
/*     */   public PdfIndirectReference getReference()
/*     */     throws IOException
/*     */   {
/* 256 */     if (this.ref != null)
/* 257 */       return this.ref;
/* 258 */     this.ref = this.writer.addToBody(this).getIndirectReference();
/* 259 */     return this.ref;
/*     */   }
/*     */ 
/*     */   public void setMultiByteFileName(byte[] fileName)
/*     */   {
/* 269 */     put(PdfName.F, new PdfString(fileName).setHexWriting(true));
/*     */   }
/*     */ 
/*     */   public void setUnicodeFileName(String filename, boolean unicode)
/*     */   {
/* 280 */     put(PdfName.UF, new PdfString(filename, unicode ? "UnicodeBig" : "PDF"));
/*     */   }
/*     */ 
/*     */   public void setVolatile(boolean volatile_file)
/*     */   {
/* 290 */     put(PdfName.V, new PdfBoolean(volatile_file));
/*     */   }
/*     */ 
/*     */   public void addDescription(String description, boolean unicode)
/*     */   {
/* 299 */     put(PdfName.DESC, new PdfString(description, unicode ? "UnicodeBig" : "PDF"));
/*     */   }
/*     */ 
/*     */   public void addCollectionItem(PdfCollectionItem ci)
/*     */   {
/* 306 */     put(PdfName.CI, ci);
/*     */   }
/*     */ 
/*     */   public void toPdf(PdfWriter writer, OutputStream os) throws IOException
/*     */   {
/* 311 */     PdfWriter.checkPdfIsoConformance(writer, 10, this);
/* 312 */     super.toPdf(writer, os);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfFileSpecification
 * JD-Core Version:    0.6.2
 */