/*     */ package com.itextpdf.text.pdf.security;
/*     */ 
/*     */ import com.itextpdf.text.log.Logger;
/*     */ import com.itextpdf.text.log.LoggerFactory;
/*     */ import com.itextpdf.text.pdf.AcroFields;
/*     */ import com.itextpdf.text.pdf.PRStream;
/*     */ import com.itextpdf.text.pdf.PdfArray;
/*     */ import com.itextpdf.text.pdf.PdfDictionary;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfReader;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.Principal;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.X509CRL;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.bouncycastle.cert.ocsp.BasicOCSPResp;
/*     */ import org.bouncycastle.cert.ocsp.OCSPException;
/*     */ import org.bouncycastle.cert.ocsp.OCSPResp;
/*     */ 
/*     */ public class LtvVerifier extends RootStoreVerifier
/*     */ {
/*  78 */   protected static final Logger LOGGER = LoggerFactory.getLogger(LtvVerifier.class);
/*     */ 
/*  81 */   protected LtvVerification.CertificateOption option = LtvVerification.CertificateOption.SIGNING_CERTIFICATE;
/*     */ 
/*  83 */   protected boolean verifyRootCertificate = true;
/*     */   protected PdfReader reader;
/*     */   protected AcroFields fields;
/*     */   protected Date signDate;
/*     */   protected String signatureName;
/*     */   protected PdfPKCS7 pkcs7;
/*  96 */   protected boolean latestRevision = true;
/*     */   protected PdfDictionary dss;
/*     */ 
/*     */   public LtvVerifier(PdfReader reader)
/*     */     throws GeneralSecurityException
/*     */   {
/* 106 */     super(null);
/* 107 */     this.reader = reader;
/* 108 */     this.fields = reader.getAcroFields();
/* 109 */     List names = this.fields.getSignatureNames();
/* 110 */     this.signatureName = ((String)names.get(names.size() - 1));
/* 111 */     this.signDate = new Date();
/* 112 */     this.pkcs7 = coversWholeDocument();
/* 113 */     LOGGER.info(String.format("Checking %ssignature %s", new Object[] { this.pkcs7.isTsp() ? "document-level timestamp " : "", this.signatureName }));
/*     */   }
/*     */ 
/*     */   public void setVerifier(CertificateVerifier verifier)
/*     */   {
/* 121 */     this.verifier = verifier;
/*     */   }
/*     */ 
/*     */   public void setCertificateOption(LtvVerification.CertificateOption option)
/*     */   {
/* 129 */     this.option = option;
/*     */   }
/*     */ 
/*     */   public void setVerifyRootCertificate(boolean verifyRootCertificate)
/*     */   {
/* 136 */     this.verifyRootCertificate = verifyRootCertificate;
/*     */   }
/*     */ 
/*     */   protected PdfPKCS7 coversWholeDocument()
/*     */     throws GeneralSecurityException
/*     */   {
/* 146 */     PdfPKCS7 pkcs7 = this.fields.verifySignature(this.signatureName);
/* 147 */     if (this.fields.signatureCoversWholeDocument(this.signatureName)) {
/* 148 */       LOGGER.info("The timestamp covers whole document.");
/*     */     }
/*     */     else {
/* 151 */       throw new VerificationException(null, "Signature doesn't cover whole document.");
/*     */     }
/* 153 */     if (pkcs7.verify()) {
/* 154 */       LOGGER.info("The signed document has not been modified.");
/* 155 */       return pkcs7;
/*     */     }
/*     */ 
/* 158 */     throw new VerificationException(null, "The document was altered after the final signature was applied.");
/*     */   }
/*     */ 
/*     */   public List<VerificationOK> verify(List<VerificationOK> result)
/*     */     throws IOException, GeneralSecurityException
/*     */   {
/* 168 */     if (result == null)
/* 169 */       result = new ArrayList();
/* 170 */     while (this.pkcs7 != null) {
/* 171 */       result.addAll(verifySignature());
/*     */     }
/* 173 */     return result;
/*     */   }
/*     */ 
/*     */   public List<VerificationOK> verifySignature()
/*     */     throws GeneralSecurityException, IOException
/*     */   {
/* 182 */     LOGGER.info("Verifying signature.");
/* 183 */     List result = new ArrayList();
/*     */ 
/* 185 */     Certificate[] chain = this.pkcs7.getSignCertificateChain();
/* 186 */     verifyChain(chain);
/*     */ 
/* 188 */     int total = 1;
/* 189 */     if (LtvVerification.CertificateOption.WHOLE_CHAIN.equals(this.option)) {
/* 190 */       total = chain.length;
/*     */     }
/*     */ 
/* 195 */     for (int i = 0; i < total; )
/*     */     {
/* 197 */       X509Certificate signCert = (X509Certificate)chain[(i++)];
/*     */ 
/* 199 */       X509Certificate issuerCert = null;
/* 200 */       if (i < chain.length) {
/* 201 */         issuerCert = (X509Certificate)chain[i];
/*     */       }
/* 203 */       LOGGER.info(signCert.getSubjectDN().getName());
/* 204 */       List list = verify(signCert, issuerCert, this.signDate);
/* 205 */       if (list.size() == 0) {
/*     */         try {
/* 207 */           signCert.verify(signCert.getPublicKey());
/* 208 */           if ((this.latestRevision) && (chain.length > 1)) {
/* 209 */             list.add(new VerificationOK(signCert, getClass(), "Root certificate in final revision"));
/*     */           }
/* 211 */           if ((list.size() == 0) && (this.verifyRootCertificate)) {
/* 212 */             throw new GeneralSecurityException();
/*     */           }
/* 214 */           if (chain.length > 1)
/* 215 */             list.add(new VerificationOK(signCert, getClass(), "Root certificate passed without checking"));
/*     */         }
/*     */         catch (GeneralSecurityException e) {
/* 218 */           throw new VerificationException(signCert, "Couldn't verify with CRL or OCSP or trusted anchor");
/*     */         }
/*     */       }
/* 221 */       result.addAll(list);
/*     */     }
/*     */ 
/* 224 */     switchToPreviousRevision();
/* 225 */     return result;
/*     */   }
/*     */ 
/*     */   public void verifyChain(Certificate[] chain)
/*     */     throws GeneralSecurityException
/*     */   {
/* 237 */     for (int i = 0; i < chain.length; i++) {
/* 238 */       X509Certificate cert = (X509Certificate)chain[i];
/*     */ 
/* 240 */       cert.checkValidity(this.signDate);
/*     */ 
/* 242 */       if (i > 0)
/* 243 */         chain[(i - 1)].verify(chain[i].getPublicKey());
/*     */     }
/* 245 */     LOGGER.info("All certificates are valid on " + this.signDate.toString());
/*     */   }
/*     */ 
/*     */   public List<VerificationOK> verify(X509Certificate signCert, X509Certificate issuerCert, Date signDate)
/*     */     throws GeneralSecurityException, IOException
/*     */   {
/* 260 */     RootStoreVerifier rootStoreVerifier = new RootStoreVerifier(this.verifier);
/* 261 */     rootStoreVerifier.setRootStore(this.rootStore);
/*     */ 
/* 263 */     CRLVerifier crlVerifier = new CRLVerifier(rootStoreVerifier, getCRLsFromDSS());
/* 264 */     crlVerifier.setRootStore(this.rootStore);
/* 265 */     crlVerifier.setOnlineCheckingAllowed((this.latestRevision) || (this.onlineCheckingAllowed));
/*     */ 
/* 267 */     OCSPVerifier ocspVerifier = new OCSPVerifier(crlVerifier, getOCSPResponsesFromDSS());
/* 268 */     ocspVerifier.setRootStore(this.rootStore);
/* 269 */     ocspVerifier.setOnlineCheckingAllowed((this.latestRevision) || (this.onlineCheckingAllowed));
/*     */ 
/* 271 */     return ocspVerifier.verify(signCert, issuerCert, signDate);
/*     */   }
/*     */ 
/*     */   public void switchToPreviousRevision()
/*     */     throws IOException, GeneralSecurityException
/*     */   {
/* 280 */     LOGGER.info("Switching to previous revision.");
/* 281 */     this.latestRevision = false;
/* 282 */     this.dss = this.reader.getCatalog().getAsDict(PdfName.DSS);
/* 283 */     Calendar cal = this.pkcs7.getTimeStampDate();
/* 284 */     if (cal == null) {
/* 285 */       cal = this.pkcs7.getSignDate();
/*     */     }
/* 287 */     this.signDate = cal.getTime();
/* 288 */     List names = this.fields.getSignatureNames();
/* 289 */     if (names.size() > 1) {
/* 290 */       this.signatureName = ((String)names.get(names.size() - 2));
/* 291 */       this.reader = new PdfReader(this.fields.extractRevision(this.signatureName));
/* 292 */       this.fields = this.reader.getAcroFields();
/* 293 */       names = this.fields.getSignatureNames();
/* 294 */       this.signatureName = ((String)names.get(names.size() - 1));
/* 295 */       this.pkcs7 = coversWholeDocument();
/* 296 */       LOGGER.info(String.format("Checking %ssignature %s", new Object[] { this.pkcs7.isTsp() ? "document-level timestamp " : "", this.signatureName }));
/*     */     }
/*     */     else {
/* 299 */       LOGGER.info("No signatures in revision");
/* 300 */       this.pkcs7 = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<X509CRL> getCRLsFromDSS()
/*     */     throws GeneralSecurityException, IOException
/*     */   {
/* 311 */     List crls = new ArrayList();
/* 312 */     if (this.dss == null)
/* 313 */       return crls;
/* 314 */     PdfArray crlarray = this.dss.getAsArray(PdfName.CRLS);
/* 315 */     if (crlarray == null)
/* 316 */       return crls;
/* 317 */     CertificateFactory cf = CertificateFactory.getInstance("X.509");
/* 318 */     for (int i = 0; i < crlarray.size(); i++) {
/* 319 */       PRStream stream = (PRStream)crlarray.getAsStream(i);
/* 320 */       X509CRL crl = (X509CRL)cf.generateCRL(new ByteArrayInputStream(PdfReader.getStreamBytes(stream)));
/* 321 */       crls.add(crl);
/*     */     }
/* 323 */     return crls;
/*     */   }
/*     */ 
/*     */   public List<BasicOCSPResp> getOCSPResponsesFromDSS()
/*     */     throws IOException, GeneralSecurityException
/*     */   {
/* 333 */     List ocsps = new ArrayList();
/* 334 */     if (this.dss == null)
/* 335 */       return ocsps;
/* 336 */     PdfArray ocsparray = this.dss.getAsArray(PdfName.OCSPS);
/* 337 */     if (ocsparray == null)
/* 338 */       return ocsps;
/* 339 */     for (int i = 0; i < ocsparray.size(); i++) {
/* 340 */       PRStream stream = (PRStream)ocsparray.getAsStream(i);
/* 341 */       OCSPResp ocspResponse = new OCSPResp(PdfReader.getStreamBytes(stream));
/* 342 */       if (ocspResponse.getStatus() == 0)
/*     */         try {
/* 344 */           ocsps.add((BasicOCSPResp)ocspResponse.getResponseObject());
/*     */         } catch (OCSPException e) {
/* 346 */           throw new GeneralSecurityException(e);
/*     */         }
/*     */     }
/* 349 */     return ocsps;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.LtvVerifier
 * JD-Core Version:    0.6.2
 */