/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.pdf.crypto.AESCipher;
/*     */ import com.itextpdf.text.pdf.crypto.ARCFOUREncryption;
/*     */ import com.itextpdf.text.pdf.crypto.IVGenerator;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ public class OutputStreamEncryption extends OutputStream
/*     */ {
/*     */   protected OutputStream out;
/*     */   protected ARCFOUREncryption arcfour;
/*     */   protected AESCipher cipher;
/*  58 */   private byte[] sb = new byte[1];
/*     */   private static final int AES_128 = 4;
/*     */   private static final int AES_256 = 5;
/*     */   private boolean aes;
/*     */   private boolean finished;
/*     */ 
/*     */   public OutputStreamEncryption(OutputStream out, byte[] key, int off, int len, int revision)
/*     */   {
/*     */     try
/*     */     {
/*  67 */       this.out = out;
/*  68 */       this.aes = ((revision == 4) || (revision == 5));
/*  69 */       if (this.aes) {
/*  70 */         byte[] iv = IVGenerator.getIV();
/*  71 */         byte[] nkey = new byte[len];
/*  72 */         System.arraycopy(key, off, nkey, 0, len);
/*  73 */         this.cipher = new AESCipher(true, nkey, iv);
/*  74 */         write(iv);
/*     */       }
/*     */       else {
/*  77 */         this.arcfour = new ARCFOUREncryption();
/*  78 */         this.arcfour.prepareARCFOURKey(key, off, len);
/*     */       }
/*     */     } catch (Exception ex) {
/*  81 */       throw new ExceptionConverter(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public OutputStreamEncryption(OutputStream out, byte[] key, int revision) {
/*  86 */     this(out, key, 0, key.length, revision);
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 100 */     finish();
/* 101 */     this.out.close();
/*     */   }
/*     */ 
/*     */   public void flush()
/*     */     throws IOException
/*     */   {
/* 117 */     this.out.flush();
/*     */   }
/*     */ 
/*     */   public void write(byte[] b)
/*     */     throws IOException
/*     */   {
/* 131 */     write(b, 0, b.length);
/*     */   }
/*     */ 
/*     */   public void write(int b)
/*     */     throws IOException
/*     */   {
/* 150 */     this.sb[0] = ((byte)b);
/* 151 */     write(this.sb, 0, 1);
/*     */   }
/*     */ 
/*     */   public void write(byte[] b, int off, int len)
/*     */     throws IOException
/*     */   {
/* 183 */     if (this.aes) {
/* 184 */       byte[] b2 = this.cipher.update(b, off, len);
/* 185 */       if ((b2 == null) || (b2.length == 0))
/* 186 */         return;
/* 187 */       this.out.write(b2, 0, b2.length);
/*     */     }
/*     */     else {
/* 190 */       byte[] b2 = new byte[Math.min(len, 4192)];
/* 191 */       while (len > 0) {
/* 192 */         int sz = Math.min(len, b2.length);
/* 193 */         this.arcfour.encryptARCFOUR(b, off, sz, b2, 0);
/* 194 */         this.out.write(b2, 0, sz);
/* 195 */         len -= sz;
/* 196 */         off += sz;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void finish() throws IOException {
/* 202 */     if (!this.finished) {
/* 203 */       this.finished = true;
/* 204 */       if (this.aes) {
/*     */         byte[] b;
/*     */         try {
/* 207 */           b = this.cipher.doFinal();
/*     */         } catch (Exception ex) {
/* 209 */           throw new ExceptionConverter(ex);
/*     */         }
/* 211 */         this.out.write(b, 0, b.length);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.OutputStreamEncryption
 * JD-Core Version:    0.6.2
 */