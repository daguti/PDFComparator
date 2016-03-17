/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ public class PdfString extends PdfObject
/*     */ {
/*  75 */   protected String value = "";
/*     */ 
/*  77 */   protected String originalValue = null;
/*     */ 
/*  80 */   protected String encoding = "PDF";
/*     */ 
/*  82 */   protected int objNum = 0;
/*     */ 
/*  84 */   protected int objGen = 0;
/*     */ 
/*  86 */   protected boolean hexWriting = false;
/*     */ 
/*     */   public PdfString()
/*     */   {
/*  94 */     super(3);
/*     */   }
/*     */ 
/*     */   public PdfString(String value)
/*     */   {
/* 104 */     super(3);
/* 105 */     this.value = value;
/*     */   }
/*     */ 
/*     */   public PdfString(String value, String encoding)
/*     */   {
/* 116 */     super(3);
/* 117 */     this.value = value;
/* 118 */     this.encoding = encoding;
/*     */   }
/*     */ 
/*     */   public PdfString(byte[] bytes)
/*     */   {
/* 127 */     super(3);
/* 128 */     this.value = PdfEncodings.convertToString(bytes, null);
/* 129 */     this.encoding = "";
/*     */   }
/*     */ 
/*     */   public void toPdf(PdfWriter writer, OutputStream os)
/*     */     throws IOException
/*     */   {
/* 142 */     PdfWriter.checkPdfIsoConformance(writer, 11, this);
/* 143 */     byte[] b = getBytes();
/* 144 */     PdfEncryption crypto = null;
/* 145 */     if (writer != null)
/* 146 */       crypto = writer.getEncryption();
/* 147 */     if ((crypto != null) && (!crypto.isEmbeddedFilesOnly()))
/* 148 */       b = crypto.encryptByteArray(b);
/* 149 */     if (this.hexWriting) {
/* 150 */       ByteBuffer buf = new ByteBuffer();
/* 151 */       buf.append('<');
/* 152 */       int len = b.length;
/* 153 */       for (int k = 0; k < len; k++)
/* 154 */         buf.appendHex(b[k]);
/* 155 */       buf.append('>');
/* 156 */       os.write(buf.toByteArray());
/*     */     }
/*     */     else {
/* 159 */       os.write(PdfContentByte.escapeString(b));
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 168 */     return this.value;
/*     */   }
/*     */ 
/*     */   public byte[] getBytes() {
/* 172 */     if (this.bytes == null) {
/* 173 */       if ((this.encoding != null) && (this.encoding.equals("UnicodeBig")) && (PdfEncodings.isPdfDocEncoding(this.value)))
/* 174 */         this.bytes = PdfEncodings.convertToBytes(this.value, "PDF");
/*     */       else
/* 176 */         this.bytes = PdfEncodings.convertToBytes(this.value, this.encoding);
/*     */     }
/* 178 */     return this.bytes;
/*     */   }
/*     */ 
/*     */   public String toUnicodeString()
/*     */   {
/* 190 */     if ((this.encoding != null) && (this.encoding.length() != 0))
/* 191 */       return this.value;
/* 192 */     getBytes();
/* 193 */     if ((this.bytes.length >= 2) && (this.bytes[0] == -2) && (this.bytes[1] == -1)) {
/* 194 */       return PdfEncodings.convertToString(this.bytes, "UnicodeBig");
/*     */     }
/* 196 */     return PdfEncodings.convertToString(this.bytes, "PDF");
/*     */   }
/*     */ 
/*     */   public String getEncoding()
/*     */   {
/* 205 */     return this.encoding;
/*     */   }
/*     */ 
/*     */   void setObjNum(int objNum, int objGen) {
/* 209 */     this.objNum = objNum;
/* 210 */     this.objGen = objGen;
/*     */   }
/*     */ 
/*     */   void decrypt(PdfReader reader)
/*     */   {
/* 217 */     PdfEncryption decrypt = reader.getDecrypt();
/* 218 */     if (decrypt != null) {
/* 219 */       this.originalValue = this.value;
/* 220 */       decrypt.setHashKey(this.objNum, this.objGen);
/* 221 */       this.bytes = PdfEncodings.convertToBytes(this.value, null);
/* 222 */       this.bytes = decrypt.decryptByteArray(this.bytes);
/* 223 */       this.value = PdfEncodings.convertToString(this.bytes, null);
/*     */     }
/*     */   }
/*     */ 
/*     */   public byte[] getOriginalBytes() {
/* 228 */     if (this.originalValue == null)
/* 229 */       return getBytes();
/* 230 */     return PdfEncodings.convertToBytes(this.originalValue, null);
/*     */   }
/*     */ 
/*     */   public PdfString setHexWriting(boolean hexWriting) {
/* 234 */     this.hexWriting = hexWriting;
/* 235 */     return this;
/*     */   }
/*     */ 
/*     */   public boolean isHexWriting() {
/* 239 */     return this.hexWriting;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfString
 * JD-Core Version:    0.6.2
 */