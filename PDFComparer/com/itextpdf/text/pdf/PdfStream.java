/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.DocWriter;
/*     */ import com.itextpdf.text.Document;
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.zip.Deflater;
/*     */ import java.util.zip.DeflaterOutputStream;
/*     */ 
/*     */ public class PdfStream extends PdfDictionary
/*     */ {
/*     */   public static final int DEFAULT_COMPRESSION = -1;
/*     */   public static final int NO_COMPRESSION = 0;
/*     */   public static final int BEST_SPEED = 1;
/*     */   public static final int BEST_COMPRESSION = 9;
/* 108 */   protected boolean compressed = false;
/*     */ 
/* 113 */   protected int compressionLevel = 0;
/*     */ 
/* 115 */   protected ByteArrayOutputStream streamBytes = null;
/*     */   protected InputStream inputStream;
/*     */   protected PdfIndirectReference ref;
/* 118 */   protected int inputStreamLength = -1;
/*     */   protected PdfWriter writer;
/*     */   protected int rawLength;
/* 122 */   static final byte[] STARTSTREAM = DocWriter.getISOBytes("stream\n");
/* 123 */   static final byte[] ENDSTREAM = DocWriter.getISOBytes("\nendstream");
/* 124 */   static final int SIZESTREAM = STARTSTREAM.length + ENDSTREAM.length;
/*     */ 
/*     */   public PdfStream(byte[] bytes)
/*     */   {
/* 136 */     this.type = 7;
/* 137 */     this.bytes = bytes;
/* 138 */     this.rawLength = bytes.length;
/* 139 */     put(PdfName.LENGTH, new PdfNumber(bytes.length));
/*     */   }
/*     */ 
/*     */   public PdfStream(InputStream inputStream, PdfWriter writer)
/*     */   {
/* 159 */     this.type = 7;
/* 160 */     this.inputStream = inputStream;
/* 161 */     this.writer = writer;
/* 162 */     this.ref = writer.getPdfIndirectReference();
/* 163 */     put(PdfName.LENGTH, this.ref);
/*     */   }
/*     */ 
/*     */   protected PdfStream()
/*     */   {
/* 172 */     this.type = 7;
/*     */   }
/*     */ 
/*     */   public void writeLength()
/*     */     throws IOException
/*     */   {
/* 184 */     if (this.inputStream == null)
/* 185 */       throw new UnsupportedOperationException(MessageLocalization.getComposedMessage("writelength.can.only.be.called.in.a.contructed.pdfstream.inputstream.pdfwriter", new Object[0]));
/* 186 */     if (this.inputStreamLength == -1)
/* 187 */       throw new IOException(MessageLocalization.getComposedMessage("writelength.can.only.be.called.after.output.of.the.stream.body", new Object[0]));
/* 188 */     this.writer.addToBody(new PdfNumber(this.inputStreamLength), this.ref, false);
/*     */   }
/*     */ 
/*     */   public int getRawLength()
/*     */   {
/* 196 */     return this.rawLength;
/*     */   }
/*     */ 
/*     */   public void flateCompress()
/*     */   {
/* 203 */     flateCompress(-1);
/*     */   }
/*     */ 
/*     */   public void flateCompress(int compressionLevel)
/*     */   {
/* 212 */     if (!Document.compress) {
/* 213 */       return;
/*     */     }
/* 215 */     if (this.compressed) {
/* 216 */       return;
/*     */     }
/* 218 */     this.compressionLevel = compressionLevel;
/* 219 */     if (this.inputStream != null) {
/* 220 */       this.compressed = true;
/* 221 */       return;
/*     */     }
/*     */ 
/* 224 */     PdfObject filter = PdfReader.getPdfObject(get(PdfName.FILTER));
/* 225 */     if (filter != null) {
/* 226 */       if (filter.isName())
/*     */       {
/* 227 */         if (!PdfName.FLATEDECODE.equals(filter));
/*     */       }
/* 230 */       else if (filter.isArray())
/*     */       {
/* 231 */         if (!((PdfArray)filter).contains(PdfName.FLATEDECODE));
/*     */       }
/*     */       else {
/* 235 */         throw new RuntimeException(MessageLocalization.getComposedMessage("stream.could.not.be.compressed.filter.is.not.a.name.or.array", new Object[0]));
/*     */       }
/*     */     }
/*     */     try
/*     */     {
/* 240 */       ByteArrayOutputStream stream = new ByteArrayOutputStream();
/* 241 */       Deflater deflater = new Deflater(compressionLevel);
/* 242 */       DeflaterOutputStream zip = new DeflaterOutputStream(stream, deflater);
/* 243 */       if (this.streamBytes != null)
/* 244 */         this.streamBytes.writeTo(zip);
/*     */       else
/* 246 */         zip.write(this.bytes);
/* 247 */       zip.close();
/* 248 */       deflater.end();
/*     */ 
/* 250 */       this.streamBytes = stream;
/* 251 */       this.bytes = null;
/* 252 */       put(PdfName.LENGTH, new PdfNumber(this.streamBytes.size()));
/* 253 */       if (filter == null) {
/* 254 */         put(PdfName.FILTER, PdfName.FLATEDECODE);
/*     */       }
/*     */       else {
/* 257 */         PdfArray filters = new PdfArray(filter);
/* 258 */         filters.add(0, PdfName.FLATEDECODE);
/* 259 */         put(PdfName.FILTER, filters);
/*     */       }
/* 261 */       this.compressed = true;
/*     */     }
/*     */     catch (IOException ioe) {
/* 264 */       throw new ExceptionConverter(ioe);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void superToPdf(PdfWriter writer, OutputStream os)
/*     */     throws IOException
/*     */   {
/* 278 */     super.toPdf(writer, os);
/*     */   }
/*     */ 
/*     */   public void toPdf(PdfWriter writer, OutputStream os)
/*     */     throws IOException
/*     */   {
/* 285 */     if ((this.inputStream != null) && (this.compressed))
/* 286 */       put(PdfName.FILTER, PdfName.FLATEDECODE);
/* 287 */     PdfEncryption crypto = null;
/* 288 */     if (writer != null)
/* 289 */       crypto = writer.getEncryption();
/* 290 */     if (crypto != null) {
/* 291 */       PdfObject filter = get(PdfName.FILTER);
/* 292 */       if (filter != null) {
/* 293 */         if (PdfName.CRYPT.equals(filter)) {
/* 294 */           crypto = null;
/* 295 */         } else if (filter.isArray()) {
/* 296 */           PdfArray a = (PdfArray)filter;
/* 297 */           if ((!a.isEmpty()) && (PdfName.CRYPT.equals(a.getPdfObject(0))))
/* 298 */             crypto = null;
/*     */         }
/*     */       }
/*     */     }
/* 302 */     PdfObject nn = get(PdfName.LENGTH);
/* 303 */     if ((crypto != null) && (nn != null) && (nn.isNumber())) {
/* 304 */       int sz = ((PdfNumber)nn).intValue();
/* 305 */       put(PdfName.LENGTH, new PdfNumber(crypto.calculateStreamSize(sz)));
/* 306 */       superToPdf(writer, os);
/* 307 */       put(PdfName.LENGTH, nn);
/*     */     }
/*     */     else {
/* 310 */       superToPdf(writer, os);
/* 311 */     }PdfWriter.checkPdfIsoConformance(writer, 9, this);
/* 312 */     os.write(STARTSTREAM);
/* 313 */     if (this.inputStream != null) {
/* 314 */       this.rawLength = 0;
/* 315 */       DeflaterOutputStream def = null;
/* 316 */       OutputStreamCounter osc = new OutputStreamCounter(os);
/* 317 */       OutputStreamEncryption ose = null;
/* 318 */       OutputStream fout = osc;
/* 319 */       if ((crypto != null) && (!crypto.isEmbeddedFilesOnly()))
/* 320 */         fout = ose = crypto.getEncryptionStream(fout);
/* 321 */       Deflater deflater = null;
/* 322 */       if (this.compressed) {
/* 323 */         deflater = new Deflater(this.compressionLevel);
/* 324 */         fout = def = new DeflaterOutputStream(fout, deflater, 32768);
/*     */       }
/*     */ 
/* 327 */       byte[] buf = new byte[4192];
/*     */       while (true) {
/* 329 */         int n = this.inputStream.read(buf);
/* 330 */         if (n <= 0)
/*     */           break;
/* 332 */         fout.write(buf, 0, n);
/* 333 */         this.rawLength += n;
/*     */       }
/* 335 */       if (def != null) {
/* 336 */         def.finish();
/* 337 */         deflater.end();
/*     */       }
/* 339 */       if (ose != null)
/* 340 */         ose.finish();
/* 341 */       this.inputStreamLength = ((int)osc.getCounter());
/*     */     }
/* 344 */     else if ((crypto != null) && (!crypto.isEmbeddedFilesOnly()))
/*     */     {
/*     */       byte[] b;
/*     */       byte[] b;
/* 346 */       if (this.streamBytes != null) {
/* 347 */         b = crypto.encryptByteArray(this.streamBytes.toByteArray());
/*     */       }
/*     */       else {
/* 350 */         b = crypto.encryptByteArray(this.bytes);
/*     */       }
/* 352 */       os.write(b);
/*     */     }
/* 355 */     else if (this.streamBytes != null) {
/* 356 */       this.streamBytes.writeTo(os);
/*     */     } else {
/* 358 */       os.write(this.bytes);
/*     */     }
/*     */ 
/* 361 */     os.write(ENDSTREAM);
/*     */   }
/*     */ 
/*     */   public void writeContent(OutputStream os)
/*     */     throws IOException
/*     */   {
/* 370 */     if (this.streamBytes != null)
/* 371 */       this.streamBytes.writeTo(os);
/* 372 */     else if (this.bytes != null)
/* 373 */       os.write(this.bytes);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 380 */     if (get(PdfName.TYPE) == null) return "Stream";
/* 381 */     return "Stream of type: " + get(PdfName.TYPE);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfStream
 * JD-Core Version:    0.6.2
 */