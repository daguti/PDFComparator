/*     */ package com.itextpdf.text.pdf.security;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.io.StreamUtil;
/*     */ import com.itextpdf.text.log.Level;
/*     */ import com.itextpdf.text.log.Logger;
/*     */ import com.itextpdf.text.log.LoggerFactory;
/*     */ import com.itextpdf.text.pdf.PdfEncryption;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.math.BigInteger;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.Security;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import org.bouncycastle.asn1.DEROctetString;
/*     */ import org.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers;
/*     */ import org.bouncycastle.asn1.x509.Extension;
/*     */ import org.bouncycastle.asn1.x509.Extensions;
/*     */ import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
/*     */ import org.bouncycastle.cert.ocsp.BasicOCSPResp;
/*     */ import org.bouncycastle.cert.ocsp.CertificateID;
/*     */ import org.bouncycastle.cert.ocsp.CertificateStatus;
/*     */ import org.bouncycastle.cert.ocsp.OCSPException;
/*     */ import org.bouncycastle.cert.ocsp.OCSPReq;
/*     */ import org.bouncycastle.cert.ocsp.OCSPReqBuilder;
/*     */ import org.bouncycastle.cert.ocsp.OCSPResp;
/*     */ import org.bouncycastle.cert.ocsp.SingleResp;
/*     */ import org.bouncycastle.jce.provider.BouncyCastleProvider;
/*     */ import org.bouncycastle.ocsp.RevokedStatus;
/*     */ import org.bouncycastle.operator.DigestCalculatorProvider;
/*     */ import org.bouncycastle.operator.OperatorException;
/*     */ import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
/*     */ 
/*     */ public class OcspClientBouncyCastle
/*     */   implements OcspClient
/*     */ {
/*  91 */   private static final Logger LOGGER = LoggerFactory.getLogger(OcspClientBouncyCastle.class);
/*     */ 
/*     */   private static OCSPReq generateOCSPRequest(X509Certificate issuerCert, BigInteger serialNumber)
/*     */     throws OCSPException, IOException, OperatorException, CertificateEncodingException
/*     */   {
/* 104 */     Security.addProvider(new BouncyCastleProvider());
/*     */ 
/* 107 */     CertificateID id = new CertificateID(new JcaDigestCalculatorProviderBuilder().build().get(CertificateID.HASH_SHA1), new JcaX509CertificateHolder(issuerCert), serialNumber);
/*     */ 
/* 112 */     OCSPReqBuilder gen = new OCSPReqBuilder();
/*     */ 
/* 114 */     gen.addRequest(id);
/*     */ 
/* 116 */     Extension ext = new Extension(OCSPObjectIdentifiers.id_pkix_ocsp_nonce, false, new DEROctetString(new DEROctetString(PdfEncryption.createDocumentId()).getEncoded()));
/* 117 */     gen.setRequestExtensions(new Extensions(new Extension[] { ext }));
/*     */ 
/* 119 */     return gen.build();
/*     */   }
/*     */ 
/*     */   private OCSPResp getOcspResponse(X509Certificate checkCert, X509Certificate rootCert, String url) throws GeneralSecurityException, OCSPException, IOException, OperatorException {
/* 123 */     if ((checkCert == null) || (rootCert == null))
/* 124 */       return null;
/* 125 */     if (url == null) {
/* 126 */       url = CertificateUtil.getOCSPURL(checkCert);
/*     */     }
/* 128 */     if (url == null)
/* 129 */       return null;
/* 130 */     LOGGER.info("Getting OCSP from " + url);
/* 131 */     OCSPReq request = generateOCSPRequest(rootCert, checkCert.getSerialNumber());
/* 132 */     byte[] array = request.getEncoded();
/* 133 */     URL urlt = new URL(url);
/* 134 */     HttpURLConnection con = (HttpURLConnection)urlt.openConnection();
/* 135 */     con.setRequestProperty("Content-Type", "application/ocsp-request");
/* 136 */     con.setRequestProperty("Accept", "application/ocsp-response");
/* 137 */     con.setDoOutput(true);
/* 138 */     OutputStream out = con.getOutputStream();
/* 139 */     DataOutputStream dataOut = new DataOutputStream(new BufferedOutputStream(out));
/* 140 */     dataOut.write(array);
/* 141 */     dataOut.flush();
/* 142 */     dataOut.close();
/* 143 */     if (con.getResponseCode() / 100 != 2) {
/* 144 */       throw new IOException(MessageLocalization.getComposedMessage("invalid.http.response.1", con.getResponseCode()));
/*     */     }
/*     */ 
/* 147 */     InputStream in = (InputStream)con.getContent();
/* 148 */     return new OCSPResp(StreamUtil.inputStreamToArray(in));
/*     */   }
/*     */ 
/*     */   public BasicOCSPResp getBasicOCSPResp(X509Certificate checkCert, X509Certificate rootCert, String url) {
/*     */     try {
/* 153 */       OCSPResp ocspResponse = getOcspResponse(checkCert, rootCert, url);
/* 154 */       if (ocspResponse == null)
/* 155 */         return null;
/* 156 */       if (ocspResponse.getStatus() != 0)
/* 157 */         return null;
/* 158 */       return (BasicOCSPResp)ocspResponse.getResponseObject();
/*     */     }
/*     */     catch (Exception ex) {
/* 161 */       if (LOGGER.isLogging(Level.ERROR))
/* 162 */         LOGGER.error(ex.getMessage());
/*     */     }
/* 164 */     return null;
/*     */   }
/*     */ 
/*     */   public byte[] getEncoded(X509Certificate checkCert, X509Certificate rootCert, String url)
/*     */   {
/*     */     try
/*     */     {
/* 177 */       BasicOCSPResp basicResponse = getBasicOCSPResp(checkCert, rootCert, url);
/* 178 */       if (basicResponse != null) {
/* 179 */         SingleResp[] responses = basicResponse.getResponses();
/* 180 */         if (responses.length == 1) {
/* 181 */           SingleResp resp = responses[0];
/* 182 */           Object status = resp.getCertStatus();
/* 183 */           if (status == CertificateStatus.GOOD) {
/* 184 */             return basicResponse.getEncoded();
/*     */           }
/* 186 */           if ((status instanceof RevokedStatus)) {
/* 187 */             throw new IOException(MessageLocalization.getComposedMessage("ocsp.status.is.revoked", new Object[0]));
/*     */           }
/*     */ 
/* 190 */           throw new IOException(MessageLocalization.getComposedMessage("ocsp.status.is.unknown", new Object[0]));
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 196 */       if (LOGGER.isLogging(Level.ERROR))
/* 197 */         LOGGER.error(ex.getMessage());
/*     */     }
/* 199 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.OcspClientBouncyCastle
 * JD-Core Version:    0.6.2
 */