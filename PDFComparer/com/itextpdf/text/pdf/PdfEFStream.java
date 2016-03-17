/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.zip.Deflater;
/*     */ import java.util.zip.DeflaterOutputStream;
/*     */ 
/*     */ public class PdfEFStream extends PdfStream
/*     */ {
/*     */   public PdfEFStream(InputStream in, PdfWriter writer)
/*     */   {
/*  67 */     super(in, writer);
/*     */   }
/*     */ 
/*     */   public PdfEFStream(byte[] fileStore)
/*     */   {
/*  75 */     super(fileStore);
/*     */   }
/*     */ 
/*     */   public void toPdf(PdfWriter writer, OutputStream os)
/*     */     throws IOException
/*     */   {
/*  82 */     if ((this.inputStream != null) && (this.compressed))
/*  83 */       put(PdfName.FILTER, PdfName.FLATEDECODE);
/*  84 */     PdfEncryption crypto = null;
/*  85 */     if (writer != null)
/*  86 */       crypto = writer.getEncryption();
/*  87 */     if (crypto != null) {
/*  88 */       PdfObject filter = get(PdfName.FILTER);
/*  89 */       if (filter != null) {
/*  90 */         if (PdfName.CRYPT.equals(filter)) {
/*  91 */           crypto = null;
/*  92 */         } else if (filter.isArray()) {
/*  93 */           PdfArray a = (PdfArray)filter;
/*  94 */           if ((!a.isEmpty()) && (PdfName.CRYPT.equals(a.getPdfObject(0))))
/*  95 */             crypto = null;
/*     */         }
/*     */       }
/*     */     }
/*  99 */     if ((crypto != null) && (crypto.isEmbeddedFilesOnly())) {
/* 100 */       PdfArray filter = new PdfArray();
/* 101 */       PdfArray decodeparms = new PdfArray();
/* 102 */       PdfDictionary crypt = new PdfDictionary();
/* 103 */       crypt.put(PdfName.NAME, PdfName.STDCF);
/* 104 */       filter.add(PdfName.CRYPT);
/* 105 */       decodeparms.add(crypt);
/* 106 */       if (this.compressed) {
/* 107 */         filter.add(PdfName.FLATEDECODE);
/* 108 */         decodeparms.add(new PdfNull());
/*     */       }
/* 110 */       put(PdfName.FILTER, filter);
/* 111 */       put(PdfName.DECODEPARMS, decodeparms);
/*     */     }
/* 113 */     PdfObject nn = get(PdfName.LENGTH);
/* 114 */     if ((crypto != null) && (nn != null) && (nn.isNumber())) {
/* 115 */       int sz = ((PdfNumber)nn).intValue();
/* 116 */       put(PdfName.LENGTH, new PdfNumber(crypto.calculateStreamSize(sz)));
/* 117 */       superToPdf(writer, os);
/* 118 */       put(PdfName.LENGTH, nn);
/*     */     }
/*     */     else {
/* 121 */       superToPdf(writer, os);
/*     */     }
/* 123 */     os.write(STARTSTREAM);
/* 124 */     if (this.inputStream != null) {
/* 125 */       this.rawLength = 0;
/* 126 */       DeflaterOutputStream def = null;
/* 127 */       OutputStreamCounter osc = new OutputStreamCounter(os);
/* 128 */       OutputStreamEncryption ose = null;
/* 129 */       OutputStream fout = osc;
/* 130 */       if (crypto != null)
/* 131 */         fout = ose = crypto.getEncryptionStream(fout);
/* 132 */       Deflater deflater = null;
/* 133 */       if (this.compressed) {
/* 134 */         deflater = new Deflater(this.compressionLevel);
/* 135 */         fout = def = new DeflaterOutputStream(fout, deflater, 32768);
/*     */       }
/*     */ 
/* 138 */       byte[] buf = new byte[4192];
/*     */       while (true) {
/* 140 */         int n = this.inputStream.read(buf);
/* 141 */         if (n <= 0)
/*     */           break;
/* 143 */         fout.write(buf, 0, n);
/* 144 */         this.rawLength += n;
/*     */       }
/* 146 */       if (def != null) {
/* 147 */         def.finish();
/* 148 */         deflater.end();
/*     */       }
/* 150 */       if (ose != null)
/* 151 */         ose.finish();
/* 152 */       this.inputStreamLength = ((int)osc.getCounter());
/*     */     }
/* 155 */     else if (crypto == null) {
/* 156 */       if (this.streamBytes != null)
/* 157 */         this.streamBytes.writeTo(os);
/*     */       else
/* 159 */         os.write(this.bytes);
/*     */     }
/*     */     else
/*     */     {
/*     */       byte[] b;
/*     */       byte[] b;
/* 163 */       if (this.streamBytes != null) {
/* 164 */         b = crypto.encryptByteArray(this.streamBytes.toByteArray());
/*     */       }
/*     */       else {
/* 167 */         b = crypto.encryptByteArray(this.bytes);
/*     */       }
/* 169 */       os.write(b);
/*     */     }
/*     */ 
/* 172 */     os.write(ENDSTREAM);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfEFStream
 * JD-Core Version:    0.6.2
 */