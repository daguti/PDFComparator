/*     */ package com.itextpdf.text.pdf.security;
/*     */ 
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.Signature;
/*     */ 
/*     */ public class PrivateKeySignature
/*     */   implements ExternalSignature
/*     */ {
/*     */   private PrivateKey pk;
/*     */   private String hashAlgorithm;
/*     */   private String encryptionAlgorithm;
/*     */   private String provider;
/*     */ 
/*     */   public PrivateKeySignature(PrivateKey pk, String hashAlgorithm, String provider)
/*     */   {
/*  74 */     this.pk = pk;
/*  75 */     this.provider = provider;
/*  76 */     this.hashAlgorithm = DigestAlgorithms.getDigest(DigestAlgorithms.getAllowedDigests(hashAlgorithm));
/*  77 */     this.encryptionAlgorithm = pk.getAlgorithm();
/*  78 */     if (this.encryptionAlgorithm.startsWith("EC"))
/*  79 */       this.encryptionAlgorithm = "ECDSA";
/*     */   }
/*     */ 
/*     */   public String getHashAlgorithm()
/*     */   {
/*  89 */     return this.hashAlgorithm;
/*     */   }
/*     */ 
/*     */   public String getEncryptionAlgorithm()
/*     */   {
/*  98 */     return this.encryptionAlgorithm;
/*     */   }
/*     */ 
/*     */   public byte[] sign(byte[] b)
/*     */     throws GeneralSecurityException
/*     */   {
/* 109 */     String signMode = this.hashAlgorithm + "with" + this.encryptionAlgorithm;
/*     */     Signature sig;
/*     */     Signature sig;
/* 111 */     if (this.provider == null)
/* 112 */       sig = Signature.getInstance(signMode);
/*     */     else
/* 114 */       sig = Signature.getInstance(signMode, this.provider);
/* 115 */     sig.initSign(this.pk);
/* 116 */     sig.update(b);
/* 117 */     return sig.sign();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.PrivateKeySignature
 * JD-Core Version:    0.6.2
 */