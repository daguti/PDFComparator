/*     */ package org.apache.pdfbox.pdmodel.encryption;
/*     */ 
/*     */ import java.security.Key;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.UnrecoverableKeyException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Enumeration;
/*     */ 
/*     */ public class PublicKeyDecryptionMaterial extends DecryptionMaterial
/*     */ {
/*  62 */   private String password = null;
/*  63 */   private KeyStore keyStore = null;
/*  64 */   private String alias = null;
/*     */ 
/*     */   public PublicKeyDecryptionMaterial(KeyStore keystore, String a, String pwd)
/*     */   {
/*  77 */     this.keyStore = keystore;
/*  78 */     this.alias = a;
/*  79 */     this.password = pwd;
/*     */   }
/*     */ 
/*     */   public X509Certificate getCertificate()
/*     */     throws KeyStoreException
/*     */   {
/*  93 */     if (this.keyStore.size() == 1)
/*     */     {
/*  95 */       Enumeration aliases = this.keyStore.aliases();
/*  96 */       String keyStoreAlias = (String)aliases.nextElement();
/*  97 */       return (X509Certificate)this.keyStore.getCertificate(keyStoreAlias);
/*     */     }
/*     */ 
/* 101 */     if (this.keyStore.containsAlias(this.alias))
/*     */     {
/* 103 */       return (X509Certificate)this.keyStore.getCertificate(this.alias);
/*     */     }
/* 105 */     throw new KeyStoreException("the keystore does not contain the given alias");
/*     */   }
/*     */ 
/*     */   public String getPassword()
/*     */   {
/* 117 */     return this.password;
/*     */   }
/*     */ 
/*     */   public Key getPrivateKey()
/*     */     throws KeyStoreException
/*     */   {
/*     */     try
/*     */     {
/* 129 */       if (this.keyStore.size() == 1)
/*     */       {
/* 131 */         Enumeration aliases = this.keyStore.aliases();
/* 132 */         String keyStoreAlias = (String)aliases.nextElement();
/* 133 */         return this.keyStore.getKey(keyStoreAlias, this.password.toCharArray());
/*     */       }
/*     */ 
/* 137 */       if (this.keyStore.containsAlias(this.alias))
/*     */       {
/* 139 */         return this.keyStore.getKey(this.alias, this.password.toCharArray());
/*     */       }
/* 141 */       throw new KeyStoreException("the keystore does not contain the given alias");
/*     */     }
/*     */     catch (UnrecoverableKeyException ex)
/*     */     {
/* 146 */       throw new KeyStoreException("the private key is not recoverable");
/*     */     }
/*     */     catch (NoSuchAlgorithmException ex) {
/*     */     }
/* 150 */     throw new KeyStoreException("the algorithm necessary to recover the key is not available");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.encryption.PublicKeyDecryptionMaterial
 * JD-Core Version:    0.6.2
 */