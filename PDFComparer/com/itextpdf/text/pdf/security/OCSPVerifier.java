/*     */ package com.itextpdf.text.pdf.security;
/*     */ 
/*     */ import com.itextpdf.text.log.Logger;
/*     */ import com.itextpdf.text.log.LoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyStore;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ import org.bouncycastle.cert.X509CertificateHolder;
/*     */ import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
/*     */ import org.bouncycastle.cert.ocsp.BasicOCSPResp;
/*     */ import org.bouncycastle.cert.ocsp.CertificateID;
/*     */ import org.bouncycastle.cert.ocsp.CertificateStatus;
/*     */ import org.bouncycastle.cert.ocsp.OCSPException;
/*     */ import org.bouncycastle.cert.ocsp.SingleResp;
/*     */ import org.bouncycastle.operator.ContentVerifierProvider;
/*     */ import org.bouncycastle.operator.OperatorCreationException;
/*     */ import org.bouncycastle.operator.bc.BcDigestCalculatorProvider;
/*     */ import org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder;
/*     */ 
/*     */ public class OCSPVerifier extends RootStoreVerifier
/*     */ {
/*  77 */   protected static final Logger LOGGER = LoggerFactory.getLogger(OCSPVerifier.class);
/*     */   protected List<BasicOCSPResp> ocsps;
/*     */ 
/*     */   public OCSPVerifier(CertificateVerifier verifier, List<BasicOCSPResp> ocsps)
/*     */   {
/*  88 */     super(verifier);
/*  89 */     this.ocsps = ocsps;
/*     */   }
/*     */ 
/*     */   public List<VerificationOK> verify(X509Certificate signCert, X509Certificate issuerCert, Date signDate)
/*     */     throws GeneralSecurityException, IOException
/*     */   {
/* 105 */     List result = new ArrayList();
/* 106 */     int validOCSPsFound = 0;
/*     */ 
/* 108 */     if (this.ocsps != null) {
/* 109 */       for (BasicOCSPResp ocspResp : this.ocsps) {
/* 110 */         if (verify(ocspResp, signCert, issuerCert, signDate)) {
/* 111 */           validOCSPsFound++;
/*     */         }
/*     */       }
/*     */     }
/* 115 */     boolean online = false;
/* 116 */     if ((this.onlineCheckingAllowed) && (validOCSPsFound == 0) && 
/* 117 */       (verify(getOcspResponse(signCert, issuerCert), signCert, issuerCert, signDate))) {
/* 118 */       validOCSPsFound++;
/* 119 */       online = true;
/*     */     }
/*     */ 
/* 123 */     LOGGER.info("Valid OCSPs found: " + validOCSPsFound);
/* 124 */     if (validOCSPsFound > 0)
/* 125 */       result.add(new VerificationOK(signCert, getClass(), "Valid OCSPs Found: " + validOCSPsFound + (online ? " (online)" : "")));
/* 126 */     if (this.verifier != null) {
/* 127 */       result.addAll(this.verifier.verify(signCert, issuerCert, signDate));
/*     */     }
/* 129 */     return result;
/*     */   }
/*     */ 
/*     */   public boolean verify(BasicOCSPResp ocspResp, X509Certificate signCert, X509Certificate issuerCert, Date signDate)
/*     */     throws GeneralSecurityException, IOException
/*     */   {
/* 144 */     if (ocspResp == null) {
/* 145 */       return false;
/*     */     }
/* 147 */     SingleResp[] resp = ocspResp.getResponses();
/* 148 */     for (int i = 0; i < resp.length; i++)
/*     */     {
/* 150 */       if (signCert.getSerialNumber().equals(resp[i].getCertID().getSerialNumber()))
/*     */       {
/*     */         try
/*     */         {
/* 155 */           if (issuerCert == null) issuerCert = signCert;
/* 156 */           if (!resp[i].getCertID().matchesIssuer(new X509CertificateHolder(issuerCert.getEncoded()), new BcDigestCalculatorProvider())) {
/* 157 */             LOGGER.info("OCSP: Issuers doesn't match.");
/* 158 */             continue;
/*     */           }
/*     */         } catch (OCSPException e) {
/* 161 */           continue;
/*     */         }
/*     */ 
/* 164 */         Date nextUpdate = resp[i].getNextUpdate();
/* 165 */         if (nextUpdate == null) {
/* 166 */           nextUpdate = new Date(resp[i].getThisUpdate().getTime() + 180000L);
/* 167 */           LOGGER.info(String.format("No 'next update' for OCSP Response; assuming %s", new Object[] { nextUpdate }));
/*     */         }
/* 169 */         if (signDate.after(nextUpdate)) {
/* 170 */           LOGGER.info(String.format("OCSP no longer valid: %s after %s", new Object[] { signDate, nextUpdate }));
/*     */         }
/*     */         else
/*     */         {
/* 174 */           Object status = resp[i].getCertStatus();
/* 175 */           if (status == CertificateStatus.GOOD)
/*     */           {
/* 177 */             isValidResponse(ocspResp, issuerCert);
/* 178 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 181 */     return false;
/*     */   }
/*     */ 
/*     */   public void isValidResponse(BasicOCSPResp ocspResp, X509Certificate issuerCert)
/*     */     throws GeneralSecurityException, IOException
/*     */   {
/* 193 */     X509Certificate responderCert = issuerCert;
/*     */ 
/* 195 */     X509CertificateHolder[] certHolders = ocspResp.getCerts();
/* 196 */     if (certHolders.length > 0) {
/* 197 */       responderCert = new JcaX509CertificateConverter().setProvider("BC").getCertificate(certHolders[0]);
/*     */       try {
/* 199 */         responderCert.verify(issuerCert.getPublicKey());
/*     */       }
/*     */       catch (GeneralSecurityException e) {
/* 202 */         if (super.verify(responderCert, issuerCert, null).size() == 0) {
/* 203 */           throw new VerificationException(responderCert, "Responder certificate couldn't be verified");
/*     */         }
/*     */       }
/*     */     }
/* 207 */     if (!verifyResponse(ocspResp, responderCert))
/* 208 */       throw new VerificationException(responderCert, "OCSP response could not be verified");
/*     */   }
/*     */ 
/*     */   public boolean verifyResponse(BasicOCSPResp ocspResp, X509Certificate responderCert)
/*     */   {
/* 221 */     if (isSignatureValid(ocspResp, responderCert)) {
/* 222 */       return true;
/*     */     }
/* 224 */     if (this.rootStore == null)
/* 225 */       return false;
/*     */     try
/*     */     {
/* 228 */       for (aliases = this.rootStore.aliases(); aliases.hasMoreElements(); ) {
/* 229 */         String alias = (String)aliases.nextElement();
/*     */         try {
/* 231 */           if (!this.rootStore.isCertificateEntry(alias))
/*     */             continue;
/* 233 */           X509Certificate anchor = (X509Certificate)this.rootStore.getCertificate(alias);
/* 234 */           if (isSignatureValid(ocspResp, anchor))
/* 235 */             return true;
/*     */         }
/*     */         catch (GeneralSecurityException e)
/*     */         {
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (GeneralSecurityException e)
/*     */     {
/*     */       Enumeration aliases;
/* 242 */       return false;
/*     */     }
/* 244 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isSignatureValid(BasicOCSPResp ocspResp, Certificate responderCert)
/*     */   {
/*     */     try
/*     */     {
/* 255 */       ContentVerifierProvider verifierProvider = new JcaContentVerifierProviderBuilder().setProvider("BC").build(responderCert.getPublicKey());
/* 256 */       return ocspResp.isSignatureValid(verifierProvider);
/*     */     } catch (OperatorCreationException e) {
/* 258 */       return false; } catch (OCSPException e) {
/*     */     }
/* 260 */     return false;
/*     */   }
/*     */ 
/*     */   public BasicOCSPResp getOcspResponse(X509Certificate signCert, X509Certificate issuerCert)
/*     */   {
/* 272 */     if ((signCert == null) && (issuerCert == null)) {
/* 273 */       return null;
/*     */     }
/* 275 */     OcspClientBouncyCastle ocsp = new OcspClientBouncyCastle();
/* 276 */     BasicOCSPResp ocspResp = ocsp.getBasicOCSPResp(signCert, issuerCert, null);
/* 277 */     if (ocspResp == null) {
/* 278 */       return null;
/*     */     }
/* 280 */     SingleResp[] resp = ocspResp.getResponses();
/* 281 */     for (int i = 0; i < resp.length; i++) {
/* 282 */       Object status = resp[i].getCertStatus();
/* 283 */       if (status == CertificateStatus.GOOD) {
/* 284 */         return ocspResp;
/*     */       }
/*     */     }
/* 287 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.OCSPVerifier
 * JD-Core Version:    0.6.2
 */