/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.BadElementException;
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.pdf.codec.CCITTG4Encoder;
/*     */ import com.itextpdf.text.pdf.qrcode.ByteMatrix;
/*     */ import com.itextpdf.text.pdf.qrcode.EncodeHintType;
/*     */ import com.itextpdf.text.pdf.qrcode.QRCodeWriter;
/*     */ import com.itextpdf.text.pdf.qrcode.WriterException;
/*     */ import java.awt.Canvas;
/*     */ import java.awt.Color;
/*     */ import java.awt.image.MemoryImageSource;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class BarcodeQRCode
/*     */ {
/*     */   ByteMatrix bm;
/*     */ 
/*     */   public BarcodeQRCode(String content, int width, int height, Map<EncodeHintType, Object> hints)
/*     */   {
/*     */     try
/*     */     {
/*  79 */       QRCodeWriter qc = new QRCodeWriter();
/*  80 */       this.bm = qc.encode(content, width, height, hints);
/*     */     }
/*     */     catch (WriterException ex) {
/*  83 */       throw new ExceptionConverter(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   private byte[] getBitMatrix() {
/*  88 */     int width = this.bm.getWidth();
/*  89 */     int height = this.bm.getHeight();
/*  90 */     int stride = (width + 7) / 8;
/*  91 */     byte[] b = new byte[stride * height];
/*  92 */     byte[][] mt = this.bm.getArray();
/*  93 */     for (int y = 0; y < height; y++) {
/*  94 */       byte[] line = mt[y];
/*  95 */       for (int x = 0; x < width; x++) {
/*  96 */         if (line[x] != 0) {
/*  97 */           int offset = stride * y + x / 8;
/*     */           int tmp89_87 = offset;
/*     */           byte[] tmp89_85 = b; tmp89_85[tmp89_87] = ((byte)(tmp89_85[tmp89_87] | (byte)(128 >> x % 8)));
/*     */         }
/*     */       }
/*     */     }
/* 102 */     return b;
/*     */   }
/*     */ 
/*     */   public com.itextpdf.text.Image getImage()
/*     */     throws BadElementException
/*     */   {
/* 110 */     byte[] b = getBitMatrix();
/* 111 */     byte[] g4 = CCITTG4Encoder.compress(b, this.bm.getWidth(), this.bm.getHeight());
/* 112 */     return com.itextpdf.text.Image.getInstance(this.bm.getWidth(), this.bm.getHeight(), false, 256, 1, g4, null);
/*     */   }
/*     */ 
/*     */   public java.awt.Image createAwtImage(Color foreground, Color background)
/*     */   {
/* 123 */     int f = foreground.getRGB();
/* 124 */     int g = background.getRGB();
/* 125 */     Canvas canvas = new Canvas();
/*     */ 
/* 127 */     int width = this.bm.getWidth();
/* 128 */     int height = this.bm.getHeight();
/* 129 */     int[] pix = new int[width * height];
/* 130 */     byte[][] mt = this.bm.getArray();
/* 131 */     for (int y = 0; y < height; y++) {
/* 132 */       byte[] line = mt[y];
/* 133 */       for (int x = 0; x < width; x++) {
/* 134 */         pix[(y * width + x)] = (line[x] == 0 ? f : g);
/*     */       }
/*     */     }
/*     */ 
/* 138 */     java.awt.Image img = canvas.createImage(new MemoryImageSource(width, height, pix, 0, width));
/* 139 */     return img;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.BarcodeQRCode
 * JD-Core Version:    0.6.2
 */