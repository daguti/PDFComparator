/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.Document;
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.zip.Deflater;
/*     */ import java.util.zip.DeflaterOutputStream;
/*     */ 
/*     */ public class PRStream extends PdfStream
/*     */ {
/*     */   protected PdfReader reader;
/*     */   protected long offset;
/*     */   protected int length;
/*  63 */   protected int objNum = 0;
/*  64 */   protected int objGen = 0;
/*     */ 
/*     */   public PRStream(PRStream stream, PdfDictionary newDic) {
/*  67 */     this.reader = stream.reader;
/*  68 */     this.offset = stream.offset;
/*  69 */     this.length = stream.length;
/*  70 */     this.compressed = stream.compressed;
/*  71 */     this.compressionLevel = stream.compressionLevel;
/*  72 */     this.streamBytes = stream.streamBytes;
/*  73 */     this.bytes = stream.bytes;
/*  74 */     this.objNum = stream.objNum;
/*  75 */     this.objGen = stream.objGen;
/*  76 */     if (newDic != null)
/*  77 */       putAll(newDic);
/*     */     else
/*  79 */       this.hashMap.putAll(stream.hashMap);
/*     */   }
/*     */ 
/*     */   public PRStream(PRStream stream, PdfDictionary newDic, PdfReader reader) {
/*  83 */     this(stream, newDic);
/*  84 */     this.reader = reader;
/*     */   }
/*     */ 
/*     */   public PRStream(PdfReader reader, long offset) {
/*  88 */     this.reader = reader;
/*  89 */     this.offset = offset;
/*     */   }
/*     */ 
/*     */   public PRStream(PdfReader reader, byte[] conts) {
/*  93 */     this(reader, conts, -1);
/*     */   }
/*     */ 
/*     */   public PRStream(PdfReader reader, byte[] conts, int compressionLevel)
/*     */   {
/* 105 */     this.reader = reader;
/* 106 */     this.offset = -1L;
/* 107 */     if (Document.compress) {
/*     */       try {
/* 109 */         ByteArrayOutputStream stream = new ByteArrayOutputStream();
/* 110 */         Deflater deflater = new Deflater(compressionLevel);
/* 111 */         DeflaterOutputStream zip = new DeflaterOutputStream(stream, deflater);
/* 112 */         zip.write(conts);
/* 113 */         zip.close();
/* 114 */         deflater.end();
/* 115 */         this.bytes = stream.toByteArray();
/*     */       }
/*     */       catch (IOException ioe) {
/* 118 */         throw new ExceptionConverter(ioe);
/*     */       }
/* 120 */       put(PdfName.FILTER, PdfName.FLATEDECODE);
/*     */     }
/*     */     else {
/* 123 */       this.bytes = conts;
/* 124 */     }setLength(this.bytes.length);
/*     */   }
/*     */ 
/*     */   public void setData(byte[] data, boolean compress)
/*     */   {
/* 137 */     setData(data, compress, -1);
/*     */   }
/*     */ 
/*     */   public void setData(byte[] data, boolean compress, int compressionLevel)
/*     */   {
/* 151 */     remove(PdfName.FILTER);
/* 152 */     this.offset = -1L;
/* 153 */     if ((Document.compress) && (compress)) {
/*     */       try {
/* 155 */         ByteArrayOutputStream stream = new ByteArrayOutputStream();
/* 156 */         Deflater deflater = new Deflater(compressionLevel);
/* 157 */         DeflaterOutputStream zip = new DeflaterOutputStream(stream, deflater);
/* 158 */         zip.write(data);
/* 159 */         zip.close();
/* 160 */         deflater.end();
/* 161 */         this.bytes = stream.toByteArray();
/* 162 */         this.compressionLevel = compressionLevel;
/*     */       }
/*     */       catch (IOException ioe) {
/* 165 */         throw new ExceptionConverter(ioe);
/*     */       }
/* 167 */       put(PdfName.FILTER, PdfName.FLATEDECODE);
/*     */     }
/*     */     else {
/* 170 */       this.bytes = data;
/* 171 */     }setLength(this.bytes.length);
/*     */   }
/*     */ 
/*     */   public void setDataRaw(byte[] data)
/*     */   {
/* 183 */     this.offset = -1L;
/* 184 */     this.bytes = data;
/* 185 */     setLength(this.bytes.length);
/*     */   }
/*     */ 
/*     */   public void setData(byte[] data)
/*     */   {
/* 192 */     setData(data, true);
/*     */   }
/*     */ 
/*     */   public void setLength(int length) {
/* 196 */     this.length = length;
/* 197 */     put(PdfName.LENGTH, new PdfNumber(length));
/*     */   }
/*     */ 
/*     */   public long getOffset() {
/* 201 */     return this.offset;
/*     */   }
/*     */ 
/*     */   public int getLength() {
/* 205 */     return this.length;
/*     */   }
/*     */ 
/*     */   public PdfReader getReader() {
/* 209 */     return this.reader;
/*     */   }
/*     */ 
/*     */   public byte[] getBytes() {
/* 213 */     return this.bytes;
/*     */   }
/*     */ 
/*     */   public void setObjNum(int objNum, int objGen) {
/* 217 */     this.objNum = objNum;
/* 218 */     this.objGen = objGen;
/*     */   }
/*     */ 
/*     */   int getObjNum() {
/* 222 */     return this.objNum;
/*     */   }
/*     */ 
/*     */   int getObjGen() {
/* 226 */     return this.objGen;
/*     */   }
/*     */ 
/*     */   public void toPdf(PdfWriter writer, OutputStream os) throws IOException {
/* 230 */     byte[] b = PdfReader.getStreamBytesRaw(this);
/* 231 */     PdfEncryption crypto = null;
/* 232 */     if (writer != null)
/* 233 */       crypto = writer.getEncryption();
/* 234 */     PdfObject objLen = get(PdfName.LENGTH);
/* 235 */     int nn = b.length;
/* 236 */     if (crypto != null)
/* 237 */       nn = crypto.calculateStreamSize(nn);
/* 238 */     put(PdfName.LENGTH, new PdfNumber(nn));
/* 239 */     superToPdf(writer, os);
/* 240 */     put(PdfName.LENGTH, objLen);
/* 241 */     os.write(STARTSTREAM);
/* 242 */     if (this.length > 0) {
/* 243 */       if ((crypto != null) && (!crypto.isEmbeddedFilesOnly()))
/* 244 */         b = crypto.encryptByteArray(b);
/* 245 */       os.write(b);
/*     */     }
/* 247 */     os.write(ENDSTREAM);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PRStream
 * JD-Core Version:    0.6.2
 */