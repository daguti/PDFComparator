/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.pdf.crypto.AESCipher;
/*     */ import com.itextpdf.text.pdf.crypto.ARCFOUREncryption;
/*     */ 
/*     */ public class StandardDecryption
/*     */ {
/*     */   protected ARCFOUREncryption arcfour;
/*     */   protected AESCipher cipher;
/*     */   private byte[] key;
/*     */   private static final int AES_128 = 4;
/*     */   private static final int AES_256 = 5;
/*     */   private boolean aes;
/*     */   private boolean initiated;
/*  58 */   private byte[] iv = new byte[16];
/*     */   private int ivptr;
/*     */ 
/*     */   public StandardDecryption(byte[] key, int off, int len, int revision)
/*     */   {
/*  63 */     this.aes = ((revision == 4) || (revision == 5));
/*  64 */     if (this.aes) {
/*  65 */       this.key = new byte[len];
/*  66 */       System.arraycopy(key, off, this.key, 0, len);
/*     */     }
/*     */     else {
/*  69 */       this.arcfour = new ARCFOUREncryption();
/*  70 */       this.arcfour.prepareARCFOURKey(key, off, len);
/*     */     }
/*     */   }
/*     */ 
/*     */   public byte[] update(byte[] b, int off, int len) {
/*  75 */     if (this.aes) {
/*  76 */       if (this.initiated) {
/*  77 */         return this.cipher.update(b, off, len);
/*     */       }
/*  79 */       int left = Math.min(this.iv.length - this.ivptr, len);
/*  80 */       System.arraycopy(b, off, this.iv, this.ivptr, left);
/*  81 */       off += left;
/*  82 */       len -= left;
/*  83 */       this.ivptr += left;
/*  84 */       if (this.ivptr == this.iv.length) {
/*  85 */         this.cipher = new AESCipher(false, this.key, this.iv);
/*  86 */         this.initiated = true;
/*  87 */         if (len > 0)
/*  88 */           return this.cipher.update(b, off, len);
/*     */       }
/*  90 */       return null;
/*     */     }
/*     */ 
/*  94 */     byte[] b2 = new byte[len];
/*  95 */     this.arcfour.encryptARCFOUR(b, off, len, b2, 0);
/*  96 */     return b2;
/*     */   }
/*     */ 
/*     */   public byte[] finish()
/*     */   {
/* 101 */     if (this.aes) {
/* 102 */       return this.cipher.doFinal();
/*     */     }
/*     */ 
/* 105 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.StandardDecryption
 * JD-Core Version:    0.6.2
 */