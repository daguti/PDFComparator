/*     */ package com.itextpdf.text.pdf.security;
/*     */ 
/*     */ import com.itextpdf.text.log.Logger;
/*     */ import com.itextpdf.text.log.LoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyStore;
/*     */ import java.security.Principal;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ 
/*     */ public class RootStoreVerifier extends CertificateVerifier
/*     */ {
/*  66 */   protected static final Logger LOGGER = LoggerFactory.getLogger(RootStoreVerifier.class);
/*     */ 
/*  69 */   protected KeyStore rootStore = null;
/*     */ 
/*     */   public RootStoreVerifier(CertificateVerifier verifier)
/*     */   {
/*  78 */     super(verifier);
/*     */   }
/*     */ 
/*     */   public void setRootStore(KeyStore keyStore)
/*     */   {
/*  88 */     this.rootStore = keyStore;
/*     */   }
/*     */ 
/*     */   public List<VerificationOK> verify(X509Certificate signCert, X509Certificate issuerCert, Date signDate)
/*     */     throws GeneralSecurityException, IOException
/*     */   {
/* 105 */     LOGGER.info("Root store verification: " + signCert.getSubjectDN().getName());
/*     */ 
/* 107 */     if (this.rootStore == null)
/* 108 */       return super.verify(signCert, issuerCert, signDate);
/*     */     try {
/* 110 */       List result = new ArrayList();
/*     */ 
/* 112 */       for (Enumeration aliases = this.rootStore.aliases(); aliases.hasMoreElements(); ) {
/* 113 */         String alias = (String)aliases.nextElement();
/*     */         try {
/* 115 */           if (this.rootStore.isCertificateEntry(alias))
/*     */           {
/* 117 */             X509Certificate anchor = (X509Certificate)this.rootStore.getCertificate(alias);
/*     */ 
/* 119 */             signCert.verify(anchor.getPublicKey());
/* 120 */             LOGGER.info("Certificate verified against root store");
/* 121 */             result.add(new VerificationOK(signCert, getClass(), "Certificate verified against root store."));
/* 122 */             result.addAll(super.verify(signCert, issuerCert, signDate));
/* 123 */             return result;
/*     */           }
/*     */         } catch (GeneralSecurityException e) {
/*     */         }
/*     */       }
/* 128 */       result.addAll(super.verify(signCert, issuerCert, signDate));
/* 129 */       return result; } catch (GeneralSecurityException e) {
/*     */     }
/* 131 */     return super.verify(signCert, issuerCert, signDate);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.RootStoreVerifier
 * JD-Core Version:    0.6.2
 */