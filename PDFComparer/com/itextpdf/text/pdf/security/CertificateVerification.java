/*     */ package com.itextpdf.text.pdf.security;
/*     */ 
/*     */ import java.security.KeyStore;
/*     */ import java.security.cert.CRL;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateParsingException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
/*     */ import java.util.Enumeration;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.bouncycastle.cert.ocsp.BasicOCSPResp;
/*     */ import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
/*     */ import org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder;
/*     */ import org.bouncycastle.tsp.TimeStampToken;
/*     */ 
/*     */ public class CertificateVerification
/*     */ {
/*     */   public static String verifyCertificate(X509Certificate cert, Collection<CRL> crls, Calendar calendar)
/*     */   {
/*  78 */     if (calendar == null)
/*  79 */       calendar = new GregorianCalendar();
/*  80 */     if (cert.hasUnsupportedCriticalExtension()) {
/*  81 */       Iterator i$ = cert.getCriticalExtensionOIDs().iterator();
/*     */       while (true) { if (!i$.hasNext()) break label106; String oid = (String)i$.next();
/*     */ 
/*  83 */         if ((!"2.5.29.15".equals(oid)) || (cert.getKeyUsage()[0] == 0))
/*     */         {
/*     */           try
/*     */           {
/*  88 */             if (("2.5.29.37".equals(oid)) && (cert.getExtendedKeyUsage().contains("1.3.6.1.5.5.7.3.8")));
/*     */           }
/*     */           catch (CertificateParsingException e)
/*     */           {
/*     */           }
/*     */         }
/*     */       }
/*  94 */       return "Has unsupported critical extension";
/*     */     }
/*     */     try
/*     */     {
/*  98 */       label106: cert.checkValidity(calendar.getTime());
/*     */     }
/*     */     catch (Exception e) {
/* 101 */       return e.getMessage();
/*     */     }
/* 103 */     if (crls != null) {
/* 104 */       for (CRL crl : crls) {
/* 105 */         if (crl.isRevoked(cert))
/* 106 */           return "Certificate revoked";
/*     */       }
/*     */     }
/* 109 */     return null;
/*     */   }
/*     */ 
/*     */   public static List<VerificationException> verifyCertificates(Certificate[] certs, KeyStore keystore, Collection<CRL> crls, Calendar calendar)
/*     */   {
/* 123 */     List result = new ArrayList();
/* 124 */     if (calendar == null)
/* 125 */       calendar = new GregorianCalendar();
/* 126 */     for (int k = 0; k < certs.length; k++) {
/* 127 */       X509Certificate cert = (X509Certificate)certs[k];
/* 128 */       String err = verifyCertificate(cert, crls, calendar);
/* 129 */       if (err != null)
/* 130 */         result.add(new VerificationException(cert, err)); Enumeration aliases;
/*     */       try {
/* 132 */         for (aliases = keystore.aliases(); aliases.hasMoreElements(); )
/*     */           try {
/* 134 */             String alias = (String)aliases.nextElement();
/* 135 */             if (keystore.isCertificateEntry(alias))
/*     */             {
/* 137 */               X509Certificate certStoreX509 = (X509Certificate)keystore.getCertificate(alias);
/* 138 */               if (verifyCertificate(certStoreX509, crls, calendar) == null)
/*     */                 try
/*     */                 {
/* 141 */                   cert.verify(certStoreX509.getPublicKey());
/* 142 */                   return result;
/*     */                 }
/*     */                 catch (Exception e)
/*     */                 {
/*     */                 }
/*     */             }
/*     */           }
/*     */           catch (Exception ex)
/*     */           {
/*     */           }
/*     */       }
/*     */       catch (Exception e) {
/*     */       }
/* 155 */       for (int j = 0; j < certs.length; j++)
/* 156 */         if (j != k)
/*     */         {
/* 158 */           X509Certificate certNext = (X509Certificate)certs[j];
/*     */           try {
/* 160 */             cert.verify(certNext.getPublicKey());
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/*     */           }
/*     */         }
/* 166 */       if (j == certs.length) {
/* 167 */         result.add(new VerificationException(cert, "Cannot be verified against the KeyStore or the certificate chain"));
/*     */       }
/*     */     }
/* 170 */     if (result.size() == 0)
/* 171 */       result.add(new VerificationException(null, "Invalid state. Possible circular certificate chain"));
/* 172 */     return result;
/*     */   }
/*     */ 
/*     */   public static List<VerificationException> verifyCertificates(Certificate[] certs, KeyStore keystore, Calendar calendar)
/*     */   {
/* 185 */     return verifyCertificates(certs, keystore, null, calendar);
/*     */   }
/*     */ 
/*     */   public static boolean verifyOcspCertificates(BasicOCSPResp ocsp, KeyStore keystore, String provider)
/*     */   {
/* 196 */     if (provider == null)
/* 197 */       provider = "BC"; Enumeration aliases;
/*     */     try {
/* 199 */       for (aliases = keystore.aliases(); aliases.hasMoreElements(); )
/*     */         try {
/* 201 */           String alias = (String)aliases.nextElement();
/* 202 */           if (keystore.isCertificateEntry(alias))
/*     */           {
/* 204 */             X509Certificate certStoreX509 = (X509Certificate)keystore.getCertificate(alias);
/* 205 */             if (ocsp.isSignatureValid(new JcaContentVerifierProviderBuilder().setProvider(provider).build(certStoreX509.getPublicKey())))
/* 206 */               return true;
/*     */           }
/*     */         }
/*     */         catch (Exception ex) {
/*     */         }
/*     */     }
/*     */     catch (Exception e) {
/*     */     }
/* 214 */     return false;
/*     */   }
/*     */ 
/*     */   public static boolean verifyTimestampCertificates(TimeStampToken ts, KeyStore keystore, String provider)
/*     */   {
/* 225 */     if (provider == null)
/* 226 */       provider = "BC"; Enumeration aliases;
/*     */     try {
/* 228 */       for (aliases = keystore.aliases(); aliases.hasMoreElements(); )
/*     */         try {
/* 230 */           String alias = (String)aliases.nextElement();
/* 231 */           if (keystore.isCertificateEntry(alias))
/*     */           {
/* 233 */             X509Certificate certStoreX509 = (X509Certificate)keystore.getCertificate(alias);
/* 234 */             ts.isSignatureValid(new JcaSimpleSignerInfoVerifierBuilder().setProvider(provider).build(certStoreX509));
/* 235 */             return true;
/*     */           }
/*     */         }
/*     */         catch (Exception ex) {
/*     */         }
/*     */     }
/*     */     catch (Exception e) {
/*     */     }
/* 243 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.CertificateVerification
 * JD-Core Version:    0.6.2
 */