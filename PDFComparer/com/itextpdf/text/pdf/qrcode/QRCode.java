/*     */ package com.itextpdf.text.pdf.qrcode;
/*     */ 
/*     */ public final class QRCode
/*     */ {
/*     */   public static final int NUM_MASK_PATTERNS = 8;
/*     */   private Mode mode;
/*     */   private ErrorCorrectionLevel ecLevel;
/*     */   private int version;
/*     */   private int matrixWidth;
/*     */   private int maskPattern;
/*     */   private int numTotalBytes;
/*     */   private int numDataBytes;
/*     */   private int numECBytes;
/*     */   private int numRSBlocks;
/*     */   private ByteMatrix matrix;
/*     */ 
/*     */   public QRCode()
/*     */   {
/*  41 */     this.mode = null;
/*  42 */     this.ecLevel = null;
/*  43 */     this.version = -1;
/*  44 */     this.matrixWidth = -1;
/*  45 */     this.maskPattern = -1;
/*  46 */     this.numTotalBytes = -1;
/*  47 */     this.numDataBytes = -1;
/*  48 */     this.numECBytes = -1;
/*  49 */     this.numRSBlocks = -1;
/*  50 */     this.matrix = null;
/*     */   }
/*     */ 
/*     */   public Mode getMode()
/*     */   {
/*  55 */     return this.mode;
/*     */   }
/*     */ 
/*     */   public ErrorCorrectionLevel getECLevel()
/*     */   {
/*  60 */     return this.ecLevel;
/*     */   }
/*     */ 
/*     */   public int getVersion()
/*     */   {
/*  65 */     return this.version;
/*     */   }
/*     */ 
/*     */   public int getMatrixWidth()
/*     */   {
/*  70 */     return this.matrixWidth;
/*     */   }
/*     */ 
/*     */   public int getMaskPattern()
/*     */   {
/*  75 */     return this.maskPattern;
/*     */   }
/*     */ 
/*     */   public int getNumTotalBytes()
/*     */   {
/*  80 */     return this.numTotalBytes;
/*     */   }
/*     */ 
/*     */   public int getNumDataBytes()
/*     */   {
/*  85 */     return this.numDataBytes;
/*     */   }
/*     */ 
/*     */   public int getNumECBytes()
/*     */   {
/*  90 */     return this.numECBytes;
/*     */   }
/*     */ 
/*     */   public int getNumRSBlocks()
/*     */   {
/*  95 */     return this.numRSBlocks;
/*     */   }
/*     */ 
/*     */   public ByteMatrix getMatrix()
/*     */   {
/* 100 */     return this.matrix;
/*     */   }
/*     */ 
/*     */   public int at(int x, int y)
/*     */   {
/* 108 */     int value = this.matrix.get(x, y);
/* 109 */     if ((value != 0) && (value != 1))
/*     */     {
/* 111 */       throw new RuntimeException("Bad value");
/*     */     }
/* 113 */     return value;
/*     */   }
/*     */ 
/*     */   public boolean isValid()
/*     */   {
/* 119 */     return (this.mode != null) && (this.ecLevel != null) && (this.version != -1) && (this.matrixWidth != -1) && (this.maskPattern != -1) && (this.numTotalBytes != -1) && (this.numDataBytes != -1) && (this.numECBytes != -1) && (this.numRSBlocks != -1) && (isValidMaskPattern(this.maskPattern)) && (this.numTotalBytes == this.numDataBytes + this.numECBytes) && (this.matrix != null) && (this.matrixWidth == this.matrix.getWidth()) && (this.matrix.getWidth() == this.matrix.getHeight());
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 142 */     StringBuffer result = new StringBuffer(200);
/* 143 */     result.append("<<\n");
/* 144 */     result.append(" mode: ");
/* 145 */     result.append(this.mode);
/* 146 */     result.append("\n ecLevel: ");
/* 147 */     result.append(this.ecLevel);
/* 148 */     result.append("\n version: ");
/* 149 */     result.append(this.version);
/* 150 */     result.append("\n matrixWidth: ");
/* 151 */     result.append(this.matrixWidth);
/* 152 */     result.append("\n maskPattern: ");
/* 153 */     result.append(this.maskPattern);
/* 154 */     result.append("\n numTotalBytes: ");
/* 155 */     result.append(this.numTotalBytes);
/* 156 */     result.append("\n numDataBytes: ");
/* 157 */     result.append(this.numDataBytes);
/* 158 */     result.append("\n numECBytes: ");
/* 159 */     result.append(this.numECBytes);
/* 160 */     result.append("\n numRSBlocks: ");
/* 161 */     result.append(this.numRSBlocks);
/* 162 */     if (this.matrix == null) {
/* 163 */       result.append("\n matrix: null\n");
/*     */     } else {
/* 165 */       result.append("\n matrix:\n");
/* 166 */       result.append(this.matrix.toString());
/*     */     }
/* 168 */     result.append(">>\n");
/* 169 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public void setMode(Mode value) {
/* 173 */     this.mode = value;
/*     */   }
/*     */ 
/*     */   public void setECLevel(ErrorCorrectionLevel value) {
/* 177 */     this.ecLevel = value;
/*     */   }
/*     */ 
/*     */   public void setVersion(int value) {
/* 181 */     this.version = value;
/*     */   }
/*     */ 
/*     */   public void setMatrixWidth(int value) {
/* 185 */     this.matrixWidth = value;
/*     */   }
/*     */ 
/*     */   public void setMaskPattern(int value) {
/* 189 */     this.maskPattern = value;
/*     */   }
/*     */ 
/*     */   public void setNumTotalBytes(int value) {
/* 193 */     this.numTotalBytes = value;
/*     */   }
/*     */ 
/*     */   public void setNumDataBytes(int value) {
/* 197 */     this.numDataBytes = value;
/*     */   }
/*     */ 
/*     */   public void setNumECBytes(int value) {
/* 201 */     this.numECBytes = value;
/*     */   }
/*     */ 
/*     */   public void setNumRSBlocks(int value) {
/* 205 */     this.numRSBlocks = value;
/*     */   }
/*     */ 
/*     */   public void setMatrix(ByteMatrix value)
/*     */   {
/* 210 */     this.matrix = value;
/*     */   }
/*     */ 
/*     */   public static boolean isValidMaskPattern(int maskPattern)
/*     */   {
/* 215 */     return (maskPattern >= 0) && (maskPattern < 8);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.qrcode.QRCode
 * JD-Core Version:    0.6.2
 */