/*     */ package com.itextpdf.text.pdf.security;
/*     */ 
/*     */ import com.itextpdf.text.Utilities;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.log.Logger;
/*     */ import com.itextpdf.text.log.LoggerFactory;
/*     */ import com.itextpdf.text.pdf.AcroFields;
/*     */ import com.itextpdf.text.pdf.PRIndirectReference;
/*     */ import com.itextpdf.text.pdf.PdfArray;
/*     */ import com.itextpdf.text.pdf.PdfDeveloperExtension;
/*     */ import com.itextpdf.text.pdf.PdfDictionary;
/*     */ import com.itextpdf.text.pdf.PdfIndirectObject;
/*     */ import com.itextpdf.text.pdf.PdfIndirectReference;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfObject;
/*     */ import com.itextpdf.text.pdf.PdfReader;
/*     */ import com.itextpdf.text.pdf.PdfStamper;
/*     */ import com.itextpdf.text.pdf.PdfStream;
/*     */ import com.itextpdf.text.pdf.PdfString;
/*     */ import com.itextpdf.text.pdf.PdfWriter;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.bouncycastle.asn1.ASN1EncodableVector;
/*     */ import org.bouncycastle.asn1.ASN1Enumerated;
/*     */ import org.bouncycastle.asn1.ASN1InputStream;
/*     */ import org.bouncycastle.asn1.ASN1Primitive;
/*     */ import org.bouncycastle.asn1.DEROctetString;
/*     */ import org.bouncycastle.asn1.DERSequence;
/*     */ import org.bouncycastle.asn1.DERTaggedObject;
/*     */ import org.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers;
/*     */ 
/*     */ public class LtvVerification
/*     */ {
/*  94 */   private Logger LOGGER = LoggerFactory.getLogger(LtvVerification.class);
/*     */   private PdfStamper stp;
/*     */   private PdfWriter writer;
/*     */   private PdfReader reader;
/*     */   private AcroFields acroFields;
/* 100 */   private Map<PdfName, ValidationData> validated = new HashMap();
/* 101 */   private boolean used = false;
/*     */ 
/*     */   public LtvVerification(PdfStamper stp)
/*     */   {
/* 159 */     this.stp = stp;
/* 160 */     this.writer = stp.getWriter();
/* 161 */     this.reader = stp.getReader();
/* 162 */     this.acroFields = stp.getAcroFields();
/*     */   }
/*     */ 
/*     */   public boolean addVerification(String signatureName, OcspClient ocsp, CrlClient crl, CertificateOption certOption, Level level, CertificateInclusion certInclude)
/*     */     throws IOException, GeneralSecurityException
/*     */   {
/* 178 */     if (this.used)
/* 179 */       throw new IllegalStateException(MessageLocalization.getComposedMessage("verification.already.output", new Object[0]));
/* 180 */     PdfPKCS7 pk = this.acroFields.verifySignature(signatureName);
/* 181 */     this.LOGGER.info("Adding verification for " + signatureName);
/* 182 */     Certificate[] xc = pk.getCertificates();
/*     */ 
/* 184 */     X509Certificate signingCert = pk.getSigningCertificate();
/* 185 */     ValidationData vd = new ValidationData(null);
/* 186 */     for (int k = 0; k < xc.length; k++) {
/* 187 */       X509Certificate cert = (X509Certificate)xc[k];
/* 188 */       this.LOGGER.info("Certificate: " + cert.getSubjectDN());
/* 189 */       if ((certOption != CertificateOption.SIGNING_CERTIFICATE) || (cert.equals(signingCert)))
/*     */       {
/* 193 */         byte[] ocspEnc = null;
/* 194 */         if ((ocsp != null) && (level != Level.CRL)) {
/* 195 */           ocspEnc = ocsp.getEncoded(cert, getParent(cert, xc), null);
/* 196 */           if (ocspEnc != null) {
/* 197 */             vd.ocsps.add(buildOCSPResponse(ocspEnc));
/* 198 */             this.LOGGER.info("OCSP added");
/*     */           }
/*     */         }
/* 201 */         if ((crl != null) && ((level == Level.CRL) || (level == Level.OCSP_CRL) || ((level == Level.OCSP_OPTIONAL_CRL) && (ocspEnc == null)))) {
/* 202 */           Collection cims = crl.getEncoded(cert, null);
/* 203 */           if (cims != null) {
/* 204 */             for (byte[] cim : cims) {
/* 205 */               boolean dup = false;
/* 206 */               for (byte[] b : vd.crls) {
/* 207 */                 if (Arrays.equals(b, cim)) {
/* 208 */                   dup = true;
/* 209 */                   break;
/*     */                 }
/*     */               }
/* 212 */               if (!dup) {
/* 213 */                 vd.crls.add(cim);
/* 214 */                 this.LOGGER.info("CRL added");
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/* 219 */         if (certInclude == CertificateInclusion.YES)
/* 220 */           vd.certs.add(cert.getEncoded());
/*     */       }
/*     */     }
/* 223 */     if ((vd.crls.isEmpty()) && (vd.ocsps.isEmpty()))
/* 224 */       return false;
/* 225 */     this.validated.put(getSignatureHashKey(signatureName), vd);
/* 226 */     return true;
/*     */   }
/*     */ 
/*     */   private X509Certificate getParent(X509Certificate cert, Certificate[] certs)
/*     */   {
/* 237 */     for (int i = 0; i < certs.length; i++) {
/* 238 */       X509Certificate parent = (X509Certificate)certs[i];
/* 239 */       if (cert.getIssuerDN().equals(parent.getSubjectDN()))
/*     */         try
/*     */         {
/* 242 */           cert.verify(parent.getPublicKey());
/* 243 */           return parent;
/*     */         }
/*     */         catch (Exception e) {
/*     */         }
/*     */     }
/* 248 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean addVerification(String signatureName, Collection<byte[]> ocsps, Collection<byte[]> crls, Collection<byte[]> certs)
/*     */     throws IOException, GeneralSecurityException
/*     */   {
/* 261 */     if (this.used)
/* 262 */       throw new IllegalStateException(MessageLocalization.getComposedMessage("verification.already.output", new Object[0]));
/* 263 */     ValidationData vd = new ValidationData(null);
/* 264 */     if (ocsps != null) {
/* 265 */       for (byte[] ocsp : ocsps) {
/* 266 */         vd.ocsps.add(buildOCSPResponse(ocsp));
/*     */       }
/*     */     }
/* 269 */     if (crls != null) {
/* 270 */       for (byte[] crl : crls) {
/* 271 */         vd.crls.add(crl);
/*     */       }
/*     */     }
/* 274 */     if (certs != null) {
/* 275 */       for (byte[] cert : certs) {
/* 276 */         vd.certs.add(cert);
/*     */       }
/*     */     }
/* 279 */     this.validated.put(getSignatureHashKey(signatureName), vd);
/* 280 */     return true;
/*     */   }
/*     */ 
/*     */   private static byte[] buildOCSPResponse(byte[] BasicOCSPResponse) throws IOException {
/* 284 */     DEROctetString doctet = new DEROctetString(BasicOCSPResponse);
/* 285 */     ASN1EncodableVector v2 = new ASN1EncodableVector();
/* 286 */     v2.add(OCSPObjectIdentifiers.id_pkix_ocsp_basic);
/* 287 */     v2.add(doctet);
/* 288 */     ASN1Enumerated den = new ASN1Enumerated(0);
/* 289 */     ASN1EncodableVector v3 = new ASN1EncodableVector();
/* 290 */     v3.add(den);
/* 291 */     v3.add(new DERTaggedObject(true, 0, new DERSequence(v2)));
/* 292 */     DERSequence seq = new DERSequence(v3);
/* 293 */     return seq.getEncoded();
/*     */   }
/*     */ 
/*     */   private PdfName getSignatureHashKey(String signatureName) throws NoSuchAlgorithmException, IOException {
/* 297 */     PdfDictionary dic = this.acroFields.getSignatureDictionary(signatureName);
/* 298 */     PdfString contents = dic.getAsString(PdfName.CONTENTS);
/* 299 */     byte[] bc = contents.getOriginalBytes();
/* 300 */     byte[] bt = null;
/* 301 */     if (PdfName.ETSI_RFC3161.equals(PdfReader.getPdfObject(dic.get(PdfName.SUBFILTER)))) {
/* 302 */       ASN1InputStream din = new ASN1InputStream(new ByteArrayInputStream(bc));
/* 303 */       ASN1Primitive pkcs = din.readObject();
/* 304 */       bc = pkcs.getEncoded();
/*     */     }
/* 306 */     bt = hashBytesSha1(bc);
/* 307 */     return new PdfName(Utilities.convertToHex(bt));
/*     */   }
/*     */ 
/*     */   private static byte[] hashBytesSha1(byte[] b) throws NoSuchAlgorithmException {
/* 311 */     MessageDigest sh = MessageDigest.getInstance("SHA1");
/* 312 */     return sh.digest(b);
/*     */   }
/*     */ 
/*     */   public void merge()
/*     */     throws IOException
/*     */   {
/* 321 */     if ((this.used) || (this.validated.isEmpty()))
/* 322 */       return;
/* 323 */     this.used = true;
/* 324 */     PdfDictionary catalog = this.reader.getCatalog();
/* 325 */     PdfObject dss = catalog.get(PdfName.DSS);
/* 326 */     if (dss == null)
/* 327 */       createDss();
/*     */     else
/* 329 */       updateDss();
/*     */   }
/*     */ 
/*     */   private void updateDss() throws IOException {
/* 333 */     PdfDictionary catalog = this.reader.getCatalog();
/* 334 */     this.stp.markUsed(catalog);
/* 335 */     PdfDictionary dss = catalog.getAsDict(PdfName.DSS);
/* 336 */     PdfArray ocsps = dss.getAsArray(PdfName.OCSPS);
/* 337 */     PdfArray crls = dss.getAsArray(PdfName.CRLS);
/* 338 */     PdfArray certs = dss.getAsArray(PdfName.CERTS);
/* 339 */     dss.remove(PdfName.OCSPS);
/* 340 */     dss.remove(PdfName.CRLS);
/* 341 */     dss.remove(PdfName.CERTS);
/* 342 */     PdfDictionary vrim = dss.getAsDict(PdfName.VRI);
/*     */ 
/* 344 */     if (vrim != null) {
/* 345 */       for (PdfName n : vrim.getKeys()) {
/* 346 */         if (this.validated.containsKey(n)) {
/* 347 */           PdfDictionary vri = vrim.getAsDict(n);
/* 348 */           if (vri != null) {
/* 349 */             deleteOldReferences(ocsps, vri.getAsArray(PdfName.OCSP));
/* 350 */             deleteOldReferences(crls, vri.getAsArray(PdfName.CRL));
/* 351 */             deleteOldReferences(certs, vri.getAsArray(PdfName.CERT));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 356 */     if (ocsps == null)
/* 357 */       ocsps = new PdfArray();
/* 358 */     if (crls == null)
/* 359 */       crls = new PdfArray();
/* 360 */     if (certs == null)
/* 361 */       certs = new PdfArray();
/* 362 */     outputDss(dss, vrim, ocsps, crls, certs);
/*     */   }
/*     */ 
/*     */   private static void deleteOldReferences(PdfArray all, PdfArray toDelete) {
/* 366 */     if ((all == null) || (toDelete == null))
/* 367 */       return;
/* 368 */     for (PdfObject pi : toDelete)
/* 369 */       if (pi.isIndirect())
/*     */       {
/* 371 */         PRIndirectReference pir = (PRIndirectReference)pi;
/* 372 */         for (int k = 0; k < all.size(); k++) {
/* 373 */           PdfObject po = all.getPdfObject(k);
/* 374 */           if (po.isIndirect())
/*     */           {
/* 376 */             PRIndirectReference pod = (PRIndirectReference)po;
/* 377 */             if (pir.getNumber() == pod.getNumber()) {
/* 378 */               all.remove(k);
/* 379 */               k--;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/* 386 */   private void createDss() throws IOException { outputDss(new PdfDictionary(), new PdfDictionary(), new PdfArray(), new PdfArray(), new PdfArray()); }
/*     */ 
/*     */   private void outputDss(PdfDictionary dss, PdfDictionary vrim, PdfArray ocsps, PdfArray crls, PdfArray certs) throws IOException
/*     */   {
/* 390 */     this.writer.addDeveloperExtension(PdfDeveloperExtension.ESIC_1_7_EXTENSIONLEVEL5);
/* 391 */     PdfDictionary catalog = this.reader.getCatalog();
/* 392 */     this.stp.markUsed(catalog);
/* 393 */     for (PdfName vkey : this.validated.keySet()) {
/* 394 */       PdfArray ocsp = new PdfArray();
/* 395 */       PdfArray crl = new PdfArray();
/* 396 */       PdfArray cert = new PdfArray();
/* 397 */       PdfDictionary vri = new PdfDictionary();
/* 398 */       for (byte[] b : ((ValidationData)this.validated.get(vkey)).crls) {
/* 399 */         PdfStream ps = new PdfStream(b);
/* 400 */         ps.flateCompress();
/* 401 */         PdfIndirectReference iref = this.writer.addToBody(ps, false).getIndirectReference();
/* 402 */         crl.add(iref);
/* 403 */         crls.add(iref);
/*     */       }
/* 405 */       for (byte[] b : ((ValidationData)this.validated.get(vkey)).ocsps) {
/* 406 */         PdfStream ps = new PdfStream(b);
/* 407 */         ps.flateCompress();
/* 408 */         PdfIndirectReference iref = this.writer.addToBody(ps, false).getIndirectReference();
/* 409 */         ocsp.add(iref);
/* 410 */         ocsps.add(iref);
/*     */       }
/* 412 */       for (byte[] b : ((ValidationData)this.validated.get(vkey)).certs) {
/* 413 */         PdfStream ps = new PdfStream(b);
/* 414 */         ps.flateCompress();
/* 415 */         PdfIndirectReference iref = this.writer.addToBody(ps, false).getIndirectReference();
/* 416 */         cert.add(iref);
/* 417 */         certs.add(iref);
/*     */       }
/* 419 */       if (ocsp.size() > 0)
/* 420 */         vri.put(PdfName.OCSP, this.writer.addToBody(ocsp, false).getIndirectReference());
/* 421 */       if (crl.size() > 0)
/* 422 */         vri.put(PdfName.CRL, this.writer.addToBody(crl, false).getIndirectReference());
/* 423 */       if (cert.size() > 0)
/* 424 */         vri.put(PdfName.CERT, this.writer.addToBody(cert, false).getIndirectReference());
/* 425 */       vrim.put(vkey, this.writer.addToBody(vri, false).getIndirectReference());
/*     */     }
/* 427 */     dss.put(PdfName.VRI, this.writer.addToBody(vrim, false).getIndirectReference());
/* 428 */     if (ocsps.size() > 0)
/* 429 */       dss.put(PdfName.OCSPS, this.writer.addToBody(ocsps, false).getIndirectReference());
/* 430 */     if (crls.size() > 0)
/* 431 */       dss.put(PdfName.CRLS, this.writer.addToBody(crls, false).getIndirectReference());
/* 432 */     if (certs.size() > 0)
/* 433 */       dss.put(PdfName.CERTS, this.writer.addToBody(certs, false).getIndirectReference());
/* 434 */     catalog.put(PdfName.DSS, this.writer.addToBody(dss, false).getIndirectReference());
/*     */   }
/*     */ 
/*     */   private static class ValidationData {
/* 438 */     public List<byte[]> crls = new ArrayList();
/* 439 */     public List<byte[]> ocsps = new ArrayList();
/* 440 */     public List<byte[]> certs = new ArrayList();
/*     */   }
/*     */ 
/*     */   public static enum CertificateInclusion
/*     */   {
/* 146 */     YES, 
/*     */ 
/* 150 */     NO;
/*     */   }
/*     */ 
/*     */   public static enum CertificateOption
/*     */   {
/* 131 */     SIGNING_CERTIFICATE, 
/*     */ 
/* 135 */     WHOLE_CHAIN;
/*     */   }
/*     */ 
/*     */   public static enum Level
/*     */   {
/* 109 */     OCSP, 
/*     */ 
/* 113 */     CRL, 
/*     */ 
/* 117 */     OCSP_CRL, 
/*     */ 
/* 121 */     OCSP_OPTIONAL_CRL;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.LtvVerification
 * JD-Core Version:    0.6.2
 */