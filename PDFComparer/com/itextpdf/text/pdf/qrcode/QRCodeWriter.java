/*     */ package com.itextpdf.text.pdf.qrcode;
/*     */ 
/*     */ import java.util.Map;
/*     */ 
/*     */ public final class QRCodeWriter
/*     */ {
/*     */   private static final int QUIET_ZONE_SIZE = 4;
/*     */ 
/*     */   public ByteMatrix encode(String contents, int width, int height)
/*     */     throws WriterException
/*     */   {
/*  35 */     return encode(contents, width, height, null);
/*     */   }
/*     */ 
/*     */   public ByteMatrix encode(String contents, int width, int height, Map<EncodeHintType, Object> hints)
/*     */     throws WriterException
/*     */   {
/*  41 */     if ((contents == null) || (contents.length() == 0)) {
/*  42 */       throw new IllegalArgumentException("Found empty contents");
/*     */     }
/*     */ 
/*  45 */     if ((width < 0) || (height < 0)) {
/*  46 */       throw new IllegalArgumentException("Requested dimensions are too small: " + width + 'x' + height);
/*     */     }
/*     */ 
/*  50 */     ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.L;
/*  51 */     if (hints != null) {
/*  52 */       ErrorCorrectionLevel requestedECLevel = (ErrorCorrectionLevel)hints.get(EncodeHintType.ERROR_CORRECTION);
/*  53 */       if (requestedECLevel != null) {
/*  54 */         errorCorrectionLevel = requestedECLevel;
/*     */       }
/*     */     }
/*     */ 
/*  58 */     QRCode code = new QRCode();
/*  59 */     Encoder.encode(contents, errorCorrectionLevel, hints, code);
/*  60 */     return renderResult(code, width, height);
/*     */   }
/*     */ 
/*     */   private static ByteMatrix renderResult(QRCode code, int width, int height)
/*     */   {
/*  66 */     ByteMatrix input = code.getMatrix();
/*  67 */     int inputWidth = input.getWidth();
/*  68 */     int inputHeight = input.getHeight();
/*  69 */     int qrWidth = inputWidth + 8;
/*  70 */     int qrHeight = inputHeight + 8;
/*  71 */     int outputWidth = Math.max(width, qrWidth);
/*  72 */     int outputHeight = Math.max(height, qrHeight);
/*     */ 
/*  74 */     int multiple = Math.min(outputWidth / qrWidth, outputHeight / qrHeight);
/*     */ 
/*  79 */     int leftPadding = (outputWidth - inputWidth * multiple) / 2;
/*  80 */     int topPadding = (outputHeight - inputHeight * multiple) / 2;
/*     */ 
/*  82 */     ByteMatrix output = new ByteMatrix(outputWidth, outputHeight);
/*  83 */     byte[][] outputArray = output.getArray();
/*     */ 
/*  87 */     byte[] row = new byte[outputWidth];
/*     */ 
/*  90 */     for (int y = 0; y < topPadding; y++) {
/*  91 */       setRowColor(outputArray[y], (byte)-1);
/*     */     }
/*     */ 
/*  95 */     byte[][] inputArray = input.getArray();
/*  96 */     for (int y = 0; y < inputHeight; y++)
/*     */     {
/*  98 */       for (int x = 0; x < leftPadding; x++) {
/*  99 */         row[x] = -1;
/*     */       }
/*     */ 
/* 103 */       int offset = leftPadding;
/* 104 */       for (int x = 0; x < inputWidth; x++) {
/* 105 */         byte value = inputArray[y][x] == 1 ? 0 : -1;
/* 106 */         for (int z = 0; z < multiple; z++) {
/* 107 */           row[(offset + z)] = value;
/*     */         }
/* 109 */         offset += multiple;
/*     */       }
/*     */ 
/* 113 */       offset = leftPadding + inputWidth * multiple;
/* 114 */       for (int x = offset; x < outputWidth; x++) {
/* 115 */         row[x] = -1;
/*     */       }
/*     */ 
/* 119 */       offset = topPadding + y * multiple;
/* 120 */       for (int z = 0; z < multiple; z++) {
/* 121 */         System.arraycopy(row, 0, outputArray[(offset + z)], 0, outputWidth);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 126 */     int offset = topPadding + inputHeight * multiple;
/* 127 */     for (int y = offset; y < outputHeight; y++) {
/* 128 */       setRowColor(outputArray[y], (byte)-1);
/*     */     }
/*     */ 
/* 131 */     return output;
/*     */   }
/*     */ 
/*     */   private static void setRowColor(byte[] row, byte value) {
/* 135 */     for (int x = 0; x < row.length; x++)
/* 136 */       row[x] = value;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.qrcode.QRCodeWriter
 * JD-Core Version:    0.6.2
 */