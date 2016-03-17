/*     */ package com.itextpdf.text.pdf.codec;
/*     */ 
/*     */ import com.itextpdf.text.DocWriter;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.zip.DeflaterOutputStream;
/*     */ 
/*     */ public class PngWriter
/*     */ {
/*  61 */   private static final byte[] PNG_SIGNTURE = { -119, 80, 78, 71, 13, 10, 26, 10 };
/*     */ 
/*  63 */   private static final byte[] IHDR = DocWriter.getISOBytes("IHDR");
/*  64 */   private static final byte[] PLTE = DocWriter.getISOBytes("PLTE");
/*  65 */   private static final byte[] IDAT = DocWriter.getISOBytes("IDAT");
/*  66 */   private static final byte[] IEND = DocWriter.getISOBytes("IEND");
/*  67 */   private static final byte[] iCCP = DocWriter.getISOBytes("iCCP");
/*     */   private static int[] crc_table;
/*     */   private OutputStream outp;
/*     */ 
/*     */   public PngWriter(OutputStream outp)
/*     */     throws IOException
/*     */   {
/*  74 */     this.outp = outp;
/*  75 */     outp.write(PNG_SIGNTURE);
/*     */   }
/*     */ 
/*     */   public void writeHeader(int width, int height, int bitDepth, int colorType) throws IOException {
/*  79 */     ByteArrayOutputStream ms = new ByteArrayOutputStream();
/*  80 */     outputInt(width, ms);
/*  81 */     outputInt(height, ms);
/*  82 */     ms.write(bitDepth);
/*  83 */     ms.write(colorType);
/*  84 */     ms.write(0);
/*  85 */     ms.write(0);
/*  86 */     ms.write(0);
/*  87 */     writeChunk(IHDR, ms.toByteArray());
/*     */   }
/*     */ 
/*     */   public void writeEnd() throws IOException {
/*  91 */     writeChunk(IEND, new byte[0]);
/*     */   }
/*     */ 
/*     */   public void writeData(byte[] data, int stride) throws IOException {
/*  95 */     ByteArrayOutputStream stream = new ByteArrayOutputStream();
/*  96 */     DeflaterOutputStream zip = new DeflaterOutputStream(stream);
/*     */ 
/*  98 */     for (int k = 0; k < data.length - stride; k += stride) {
/*  99 */       zip.write(0);
/* 100 */       zip.write(data, k, stride);
/*     */     }
/* 102 */     int remaining = data.length - k;
/* 103 */     if (remaining > 0) {
/* 104 */       zip.write(0);
/* 105 */       zip.write(data, k, remaining);
/*     */     }
/* 107 */     zip.close();
/* 108 */     writeChunk(IDAT, stream.toByteArray());
/*     */   }
/*     */ 
/*     */   public void writePalette(byte[] data) throws IOException {
/* 112 */     writeChunk(PLTE, data);
/*     */   }
/*     */ 
/*     */   public void writeIccProfile(byte[] data) throws IOException {
/* 116 */     ByteArrayOutputStream stream = new ByteArrayOutputStream();
/* 117 */     stream.write(73);
/* 118 */     stream.write(67);
/* 119 */     stream.write(67);
/* 120 */     stream.write(0);
/* 121 */     stream.write(0);
/* 122 */     DeflaterOutputStream zip = new DeflaterOutputStream(stream);
/* 123 */     zip.write(data);
/* 124 */     zip.close();
/* 125 */     writeChunk(iCCP, stream.toByteArray());
/*     */   }
/*     */ 
/*     */   private static void make_crc_table() {
/* 129 */     if (crc_table != null)
/* 130 */       return;
/* 131 */     int[] crc2 = new int[256];
/* 132 */     for (int n = 0; n < 256; n++) {
/* 133 */       int c = n;
/* 134 */       for (int k = 0; k < 8; k++) {
/* 135 */         if ((c & 0x1) != 0)
/* 136 */           c = 0xEDB88320 ^ c >>> 1;
/*     */         else
/* 138 */           c >>>= 1;
/*     */       }
/* 140 */       crc2[n] = c;
/*     */     }
/* 142 */     crc_table = crc2;
/*     */   }
/*     */ 
/*     */   private static int update_crc(int crc, byte[] buf, int offset, int len) {
/* 146 */     int c = crc;
/*     */ 
/* 148 */     if (crc_table == null)
/* 149 */       make_crc_table();
/* 150 */     for (int n = 0; n < len; n++) {
/* 151 */       c = crc_table[((c ^ buf[(n + offset)]) & 0xFF)] ^ c >>> 8;
/*     */     }
/* 153 */     return c;
/*     */   }
/*     */ 
/*     */   private static int crc(byte[] buf, int offset, int len) {
/* 157 */     return update_crc(-1, buf, offset, len) ^ 0xFFFFFFFF;
/*     */   }
/*     */ 
/*     */   private static int crc(byte[] buf) {
/* 161 */     return update_crc(-1, buf, 0, buf.length) ^ 0xFFFFFFFF;
/*     */   }
/*     */ 
/*     */   public void outputInt(int n) throws IOException {
/* 165 */     outputInt(n, this.outp);
/*     */   }
/*     */ 
/*     */   public static void outputInt(int n, OutputStream s) throws IOException {
/* 169 */     s.write((byte)(n >> 24));
/* 170 */     s.write((byte)(n >> 16));
/* 171 */     s.write((byte)(n >> 8));
/* 172 */     s.write((byte)n);
/*     */   }
/*     */ 
/*     */   public void writeChunk(byte[] chunkType, byte[] data) throws IOException {
/* 176 */     outputInt(data.length);
/* 177 */     this.outp.write(chunkType, 0, 4);
/* 178 */     this.outp.write(data);
/* 179 */     int c = update_crc(-1, chunkType, 0, chunkType.length);
/* 180 */     c = update_crc(c, data, 0, data.length) ^ 0xFFFFFFFF;
/* 181 */     outputInt(c);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.codec.PngWriter
 * JD-Core Version:    0.6.2
 */