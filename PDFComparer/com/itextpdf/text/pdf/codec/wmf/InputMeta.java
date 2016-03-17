/*     */ package com.itextpdf.text.pdf.codec.wmf;
/*     */ 
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import com.itextpdf.text.Utilities;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public class InputMeta
/*     */ {
/*     */   InputStream in;
/*     */   int length;
/*     */ 
/*     */   public InputMeta(InputStream in)
/*     */   {
/*  59 */     this.in = in;
/*     */   }
/*     */ 
/*     */   public int readWord() throws IOException {
/*  63 */     this.length += 2;
/*  64 */     int k1 = this.in.read();
/*  65 */     if (k1 < 0)
/*  66 */       return 0;
/*  67 */     return k1 + (this.in.read() << 8) & 0xFFFF;
/*     */   }
/*     */ 
/*     */   public int readShort() throws IOException {
/*  71 */     int k = readWord();
/*  72 */     if (k > 32767)
/*  73 */       k -= 65536;
/*  74 */     return k;
/*     */   }
/*     */ 
/*     */   public int readInt() throws IOException {
/*  78 */     this.length += 4;
/*  79 */     int k1 = this.in.read();
/*  80 */     if (k1 < 0)
/*  81 */       return 0;
/*  82 */     int k2 = this.in.read() << 8;
/*  83 */     int k3 = this.in.read() << 16;
/*  84 */     return k1 + k2 + k3 + (this.in.read() << 24);
/*     */   }
/*     */ 
/*     */   public int readByte() throws IOException {
/*  88 */     this.length += 1;
/*  89 */     return this.in.read() & 0xFF;
/*     */   }
/*     */ 
/*     */   public void skip(int len) throws IOException {
/*  93 */     this.length += len;
/*  94 */     Utilities.skip(this.in, len);
/*     */   }
/*     */ 
/*     */   public int getLength() {
/*  98 */     return this.length;
/*     */   }
/*     */ 
/*     */   public BaseColor readColor() throws IOException {
/* 102 */     int red = readByte();
/* 103 */     int green = readByte();
/* 104 */     int blue = readByte();
/* 105 */     readByte();
/* 106 */     return new BaseColor(red, green, blue);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.codec.wmf.InputMeta
 * JD-Core Version:    0.6.2
 */