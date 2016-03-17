/*     */ package com.itextpdf.text.pdf.security;
/*     */ 
/*     */ import com.itextpdf.text.log.Logger;
/*     */ import com.itextpdf.text.log.LoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyStore;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.X509CRL;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ 
/*     */ public class CRLVerifier extends RootStoreVerifier
/*     */ {
/*  68 */   protected static final Logger LOGGER = LoggerFactory.getLogger(CRLVerifier.class);
/*     */   List<X509CRL> crls;
/*     */ 
/*     */   public CRLVerifier(CertificateVerifier verifier, List<X509CRL> crls)
/*     */   {
/*  79 */     super(verifier);
/*  80 */     this.crls = crls;
/*     */   }
/*     */ 
/*     */   public List<VerificationOK> verify(X509Certificate signCert, X509Certificate issuerCert, Date signDate)
/*     */     throws GeneralSecurityException, IOException
/*     */   {
/*  95 */     List result = new ArrayList();
/*  96 */     int validCrlsFound = 0;
/*     */ 
/*  98 */     if (this.crls != null) {
/*  99 */       for (X509CRL crl : this.crls) {
/* 100 */         if (verify(crl, signCert, issuerCert, signDate)) {
/* 101 */           validCrlsFound++;
/*     */         }
/*     */       }
/*     */     }
/* 105 */     boolean online = false;
/* 106 */     if ((this.onlineCheckingAllowed) && (validCrlsFound == 0) && 
/* 107 */       (verify(getCRL(signCert, issuerCert), signCert, issuerCert, signDate))) {
/* 108 */       validCrlsFound++;
/* 109 */       online = true;
/*     */     }
/*     */ 
/* 113 */     LOGGER.info("Valid CRLs found: " + validCrlsFound);
/* 114 */     if (validCrlsFound > 0) {
/* 115 */       result.add(new VerificationOK(signCert, getClass(), "Valid CRLs found: " + validCrlsFound + (online ? " (online)" : "")));
/*     */     }
/* 117 */     if (this.verifier != null) {
/* 118 */       result.addAll(this.verifier.verify(signCert, issuerCert, signDate));
/*     */     }
/* 120 */     return result;
/*     */   }
/*     */ 
/*     */   public boolean verify(X509CRL crl, X509Certificate signCert, X509Certificate issuerCert, Date signDate)
/*     */     throws GeneralSecurityException
/*     */   {
/* 133 */     if ((crl == null) || (signDate == null)) {
/* 134 */       return false;
/*     */     }
/* 136 */     if ((crl.getIssuerX500Principal().equals(signCert.getIssuerX500Principal())) && (signDate.after(crl.getThisUpdate())) && (signDate.before(crl.getNextUpdate())))
/*     */     {
/* 139 */       if ((isSignatureValid(crl, issuerCert)) && (crl.isRevoked(signCert))) {
/* 140 */         throw new VerificationException(signCert, "The certificate has been revoked.");
/*     */       }
/* 142 */       return true;
/*     */     }
/* 144 */     return false;
/*     */   }
/*     */ 
/*     */   public X509CRL getCRL(X509Certificate signCert, X509Certificate issuerCert)
/*     */   {
/* 154 */     if (issuerCert == null)
/* 155 */       issuerCert = signCert;
/*     */     try
/*     */     {
/* 158 */       String crlurl = CertificateUtil.getCRLURL(signCert);
/* 159 */       if (crlurl == null)
/* 160 */         return null;
/* 161 */       LOGGER.info("Getting CRL from " + crlurl);
/* 162 */       CertificateFactory cf = CertificateFactory.getInstance("X.509");
/*     */ 
/* 164 */       return (X509CRL)cf.generateCRL(new URL(crlurl).openStream());
/*     */     }
/*     */     catch (IOException e) {
/* 167 */       return null;
/*     */     } catch (GeneralSecurityException e) {
/*     */     }
/* 170 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean isSignatureValid(X509CRL crl, X509Certificate crlIssuer)
/*     */   {
/* 182 */     if (crlIssuer != null) {
/*     */       try {
/* 184 */         crl.verify(crlIssuer.getPublicKey());
/* 185 */         return true;
/*     */       } catch (GeneralSecurityException e) {
/* 187 */         LOGGER.warn("CRL not issued by the same authority as the certificate that is being checked");
/*     */       }
/*     */     }
/*     */ 
/* 191 */     if (this.rootStore == null)
/* 192 */       return false;
/*     */     try
/*     */     {
/* 195 */       for (aliases = this.rootStore.aliases(); aliases.hasMoreElements(); ) {
/* 196 */         String alias = (String)aliases.nextElement();
/*     */         try {
/* 198 */           if (this.rootStore.isCertificateEntry(alias))
/*     */           {
/* 201 */             X509Certificate anchor = (X509Certificate)this.rootStore.getCertificate(alias);
/* 202 */             crl.verify(anchor.getPublicKey());
/* 203 */             return true;
/*     */           }
/*     */         }
/*     */         catch (GeneralSecurityException e)
/*     */         {
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (GeneralSecurityException e)
/*     */     {
/*     */       Enumeration aliases;
/* 210 */       return false;
/*     */     }
/* 212 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.CRLVerifier
 * JD-Core Version:    0.6.2
 */