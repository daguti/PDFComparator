/*      */ package com.itextpdf.text.pdf.security;
/*      */ 
/*      */ import com.itextpdf.text.ExceptionConverter;
/*      */ import com.itextpdf.text.error_messages.MessageLocalization;
/*      */ import com.itextpdf.text.pdf.PdfName;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.math.BigInteger;
/*      */ import java.security.GeneralSecurityException;
/*      */ import java.security.InvalidKeyException;
/*      */ import java.security.MessageDigest;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import java.security.NoSuchProviderException;
/*      */ import java.security.PrivateKey;
/*      */ import java.security.PublicKey;
/*      */ import java.security.Signature;
/*      */ import java.security.SignatureException;
/*      */ import java.security.cert.CRL;
/*      */ import java.security.cert.Certificate;
/*      */ import java.security.cert.CertificateFactory;
/*      */ import java.security.cert.X509CRL;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Set;
/*      */ import org.bouncycastle.asn1.ASN1Encodable;
/*      */ import org.bouncycastle.asn1.ASN1EncodableVector;
/*      */ import org.bouncycastle.asn1.ASN1Enumerated;
/*      */ import org.bouncycastle.asn1.ASN1InputStream;
/*      */ import org.bouncycastle.asn1.ASN1Integer;
/*      */ import org.bouncycastle.asn1.ASN1ObjectIdentifier;
/*      */ import org.bouncycastle.asn1.ASN1OctetString;
/*      */ import org.bouncycastle.asn1.ASN1OutputStream;
/*      */ import org.bouncycastle.asn1.ASN1Primitive;
/*      */ import org.bouncycastle.asn1.ASN1Sequence;
/*      */ import org.bouncycastle.asn1.ASN1Set;
/*      */ import org.bouncycastle.asn1.ASN1TaggedObject;
/*      */ import org.bouncycastle.asn1.DERNull;
/*      */ import org.bouncycastle.asn1.DEROctetString;
/*      */ import org.bouncycastle.asn1.DERSequence;
/*      */ import org.bouncycastle.asn1.DERSet;
/*      */ import org.bouncycastle.asn1.DERTaggedObject;
/*      */ import org.bouncycastle.asn1.DERUTCTime;
/*      */ import org.bouncycastle.asn1.cms.Attribute;
/*      */ import org.bouncycastle.asn1.cms.AttributeTable;
/*      */ import org.bouncycastle.asn1.cms.ContentInfo;
/*      */ import org.bouncycastle.asn1.ess.ESSCertID;
/*      */ import org.bouncycastle.asn1.ess.ESSCertIDv2;
/*      */ import org.bouncycastle.asn1.ess.SigningCertificate;
/*      */ import org.bouncycastle.asn1.ess.SigningCertificateV2;
/*      */ import org.bouncycastle.asn1.ocsp.BasicOCSPResponse;
/*      */ import org.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers;
/*      */ import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
/*      */ import org.bouncycastle.asn1.tsp.MessageImprint;
/*      */ import org.bouncycastle.asn1.tsp.TSTInfo;
/*      */ import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
/*      */ import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
/*      */ import org.bouncycastle.cert.ocsp.BasicOCSPResp;
/*      */ import org.bouncycastle.cert.ocsp.CertificateID;
/*      */ import org.bouncycastle.cert.ocsp.SingleResp;
/*      */ import org.bouncycastle.jce.X509Principal;
/*      */ import org.bouncycastle.jce.provider.X509CertParser;
/*      */ import org.bouncycastle.operator.DigestCalculator;
/*      */ import org.bouncycastle.operator.DigestCalculatorProvider;
/*      */ import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
/*      */ import org.bouncycastle.tsp.TimeStampToken;
/*      */ import org.bouncycastle.tsp.TimeStampTokenInfo;
/*      */ 
/*      */ public class PdfPKCS7
/*      */ {
/*      */   private String provider;
/*      */   private String signName;
/*      */   private String reason;
/*      */   private String location;
/*      */   private Calendar signDate;
/*  550 */   private int version = 1;
/*      */ 
/*  553 */   private int signerversion = 1;
/*      */   private String digestAlgorithmOid;
/*      */   private MessageDigest messageDigest;
/*      */   private Set<String> digestalgos;
/*      */   private byte[] digestAttr;
/*      */   private PdfName filterSubtype;
/*      */   private String digestEncryptionAlgorithmOid;
/*      */   private ExternalDigest interfaceDigest;
/*      */   private byte[] externalDigest;
/*      */   private byte[] externalRSAdata;
/*      */   private Signature sig;
/*      */   private byte[] digest;
/*      */   private byte[] RSAdata;
/*      */   private byte[] sigAttr;
/*      */   private byte[] sigAttrDer;
/*      */   private MessageDigest encContDigest;
/*      */   private boolean verified;
/*      */   private boolean verifyResult;
/*      */   private Collection<Certificate> certs;
/*      */   private Collection<Certificate> signCerts;
/*      */   private X509Certificate signCert;
/*      */   private Collection<CRL> crls;
/*      */   private BasicOCSPResp basicResp;
/*      */   private boolean isTsp;
/*      */   private boolean isCades;
/*      */   private TimeStampToken timeStampToken;
/*      */ 
/*      */   public PdfPKCS7(PrivateKey privKey, Certificate[] certChain, String hashAlgorithm, String provider, ExternalDigest interfaceDigest, boolean hasRSAdata)
/*      */     throws InvalidKeyException, NoSuchProviderException, NoSuchAlgorithmException
/*      */   {
/*  144 */     this.provider = provider;
/*  145 */     this.interfaceDigest = interfaceDigest;
/*      */ 
/*  147 */     this.digestAlgorithmOid = DigestAlgorithms.getAllowedDigests(hashAlgorithm);
/*  148 */     if (this.digestAlgorithmOid == null) {
/*  149 */       throw new NoSuchAlgorithmException(MessageLocalization.getComposedMessage("unknown.hash.algorithm.1", new Object[] { hashAlgorithm }));
/*      */     }
/*      */ 
/*  152 */     this.signCert = ((X509Certificate)certChain[0]);
/*  153 */     this.certs = new ArrayList();
/*  154 */     for (Certificate element : certChain) {
/*  155 */       this.certs.add(element);
/*      */     }
/*      */ 
/*  160 */     this.digestalgos = new HashSet();
/*  161 */     this.digestalgos.add(this.digestAlgorithmOid);
/*      */ 
/*  164 */     if (privKey != null) {
/*  165 */       this.digestEncryptionAlgorithmOid = privKey.getAlgorithm();
/*  166 */       if (this.digestEncryptionAlgorithmOid.equals("RSA")) {
/*  167 */         this.digestEncryptionAlgorithmOid = "1.2.840.113549.1.1.1";
/*      */       }
/*  169 */       else if (this.digestEncryptionAlgorithmOid.equals("DSA")) {
/*  170 */         this.digestEncryptionAlgorithmOid = "1.2.840.10040.4.1";
/*      */       }
/*      */       else {
/*  173 */         throw new NoSuchAlgorithmException(MessageLocalization.getComposedMessage("unknown.key.algorithm.1", new Object[] { this.digestEncryptionAlgorithmOid }));
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  178 */     if (hasRSAdata) {
/*  179 */       this.RSAdata = new byte[0];
/*  180 */       this.messageDigest = DigestAlgorithms.getMessageDigest(getHashAlgorithm(), provider);
/*      */     }
/*      */ 
/*  184 */     if (privKey != null)
/*  185 */       this.sig = initSignature(privKey);
/*      */   }
/*      */ 
/*      */   public PdfPKCS7(byte[] contentsKey, byte[] certsKey, String provider)
/*      */   {
/*      */     try
/*      */     {
/*  200 */       this.provider = provider;
/*  201 */       X509CertParser cr = new X509CertParser();
/*  202 */       cr.engineInit(new ByteArrayInputStream(certsKey));
/*  203 */       this.certs = cr.engineReadAll();
/*  204 */       this.signCerts = this.certs;
/*  205 */       this.signCert = ((X509Certificate)this.certs.iterator().next());
/*  206 */       this.crls = new ArrayList();
/*  207 */       ASN1InputStream in = new ASN1InputStream(new ByteArrayInputStream(contentsKey));
/*  208 */       this.digest = ((ASN1OctetString)in.readObject()).getOctets();
/*  209 */       if (provider == null)
/*  210 */         this.sig = Signature.getInstance("SHA1withRSA");
/*      */       else
/*  212 */         this.sig = Signature.getInstance("SHA1withRSA", provider);
/*  213 */       this.sig.initVerify(this.signCert.getPublicKey());
/*      */     }
/*      */     catch (Exception e) {
/*  216 */       throw new ExceptionConverter(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public PdfPKCS7(byte[] contentsKey, PdfName filterSubtype, String provider)
/*      */   {
/*  228 */     this.filterSubtype = filterSubtype;
/*  229 */     this.isTsp = PdfName.ETSI_RFC3161.equals(filterSubtype);
/*  230 */     this.isCades = PdfName.ETSI_CADES_DETACHED.equals(filterSubtype);
/*      */     try
/*      */     {
/*  232 */       this.provider = provider;
/*  233 */       ASN1InputStream din = new ASN1InputStream(new ByteArrayInputStream(contentsKey));
/*      */       ASN1Primitive pkcs;
/*      */       try
/*      */       {
/*  241 */         pkcs = din.readObject();
/*      */       }
/*      */       catch (IOException e) {
/*  244 */         throw new IllegalArgumentException(MessageLocalization.getComposedMessage("can.t.decode.pkcs7signeddata.object", new Object[0]));
/*      */       }
/*  246 */       if (!(pkcs instanceof ASN1Sequence)) {
/*  247 */         throw new IllegalArgumentException(MessageLocalization.getComposedMessage("not.a.valid.pkcs.7.object.not.a.sequence", new Object[0]));
/*      */       }
/*  249 */       ASN1Sequence signedData = (ASN1Sequence)pkcs;
/*  250 */       ASN1ObjectIdentifier objId = (ASN1ObjectIdentifier)signedData.getObjectAt(0);
/*  251 */       if (!objId.getId().equals("1.2.840.113549.1.7.2"))
/*  252 */         throw new IllegalArgumentException(MessageLocalization.getComposedMessage("not.a.valid.pkcs.7.object.not.signed.data", new Object[0]));
/*  253 */       ASN1Sequence content = (ASN1Sequence)((ASN1TaggedObject)signedData.getObjectAt(1)).getObject();
/*      */ 
/*  262 */       this.version = ((ASN1Integer)content.getObjectAt(0)).getValue().intValue();
/*      */ 
/*  265 */       this.digestalgos = new HashSet();
/*  266 */       Enumeration e = ((ASN1Set)content.getObjectAt(1)).getObjects();
/*  267 */       while (e.hasMoreElements()) {
/*  268 */         ASN1Sequence s = (ASN1Sequence)e.nextElement();
/*  269 */         ASN1ObjectIdentifier o = (ASN1ObjectIdentifier)s.getObjectAt(0);
/*  270 */         this.digestalgos.add(o.getId());
/*      */       }
/*      */ 
/*  274 */       ASN1Sequence rsaData = (ASN1Sequence)content.getObjectAt(2);
/*  275 */       if (rsaData.size() > 1) {
/*  276 */         ASN1OctetString rsaDataContent = (ASN1OctetString)((ASN1TaggedObject)rsaData.getObjectAt(1)).getObject();
/*  277 */         this.RSAdata = rsaDataContent.getOctets();
/*      */       }
/*      */ 
/*  280 */       int next = 3;
/*  281 */       while ((content.getObjectAt(next) instanceof ASN1TaggedObject)) {
/*  282 */         next++;
/*      */       }
/*      */ 
/*  289 */       X509CertParser cr = new X509CertParser();
/*  290 */       cr.engineInit(new ByteArrayInputStream(contentsKey));
/*  291 */       this.certs = cr.engineReadAll();
/*      */ 
/*  326 */       ASN1Set signerInfos = (ASN1Set)content.getObjectAt(next);
/*  327 */       if (signerInfos.size() != 1)
/*  328 */         throw new IllegalArgumentException(MessageLocalization.getComposedMessage("this.pkcs.7.object.has.multiple.signerinfos.only.one.is.supported.at.this.time", new Object[0]));
/*  329 */       ASN1Sequence signerInfo = (ASN1Sequence)signerInfos.getObjectAt(0);
/*      */ 
/*  336 */       this.signerversion = ((ASN1Integer)signerInfo.getObjectAt(0)).getValue().intValue();
/*      */ 
/*  338 */       ASN1Sequence issuerAndSerialNumber = (ASN1Sequence)signerInfo.getObjectAt(1);
/*  339 */       X509Principal issuer = new X509Principal(issuerAndSerialNumber.getObjectAt(0).toASN1Primitive().getEncoded());
/*  340 */       BigInteger serialNumber = ((ASN1Integer)issuerAndSerialNumber.getObjectAt(1)).getValue();
/*  341 */       for (Object element : this.certs) {
/*  342 */         X509Certificate cert = (X509Certificate)element;
/*  343 */         if ((cert.getIssuerDN().equals(issuer)) && (serialNumber.equals(cert.getSerialNumber()))) {
/*  344 */           this.signCert = cert;
/*  345 */           break;
/*      */         }
/*      */       }
/*  348 */       if (this.signCert == null) {
/*  349 */         throw new IllegalArgumentException(MessageLocalization.getComposedMessage("can.t.find.signing.certificate.with.serial.1", new Object[] { issuer.getName() + " / " + serialNumber.toString(16) }));
/*      */       }
/*      */ 
/*  352 */       signCertificateChain();
/*  353 */       this.digestAlgorithmOid = ((ASN1ObjectIdentifier)((ASN1Sequence)signerInfo.getObjectAt(2)).getObjectAt(0)).getId();
/*  354 */       next = 3;
/*  355 */       boolean foundCades = false;
/*  356 */       if ((signerInfo.getObjectAt(next) instanceof ASN1TaggedObject)) {
/*  357 */         ASN1TaggedObject tagsig = (ASN1TaggedObject)signerInfo.getObjectAt(next);
/*  358 */         ASN1Set sseq = ASN1Set.getInstance(tagsig, false);
/*  359 */         this.sigAttr = sseq.getEncoded();
/*      */ 
/*  361 */         this.sigAttrDer = sseq.getEncoded("DER");
/*      */ 
/*  363 */         for (int k = 0; k < sseq.size(); k++) {
/*  364 */           ASN1Sequence seq2 = (ASN1Sequence)sseq.getObjectAt(k);
/*  365 */           String idSeq2 = ((ASN1ObjectIdentifier)seq2.getObjectAt(0)).getId();
/*  366 */           if (idSeq2.equals("1.2.840.113549.1.9.4")) {
/*  367 */             ASN1Set set = (ASN1Set)seq2.getObjectAt(1);
/*  368 */             this.digestAttr = ((ASN1OctetString)set.getObjectAt(0)).getOctets();
/*      */           }
/*  370 */           else if (idSeq2.equals("1.2.840.113583.1.1.8")) {
/*  371 */             ASN1Set setout = (ASN1Set)seq2.getObjectAt(1);
/*  372 */             ASN1Sequence seqout = (ASN1Sequence)setout.getObjectAt(0);
/*  373 */             for (int j = 0; j < seqout.size(); j++) {
/*  374 */               ASN1TaggedObject tg = (ASN1TaggedObject)seqout.getObjectAt(j);
/*  375 */               if (tg.getTagNo() == 0) {
/*  376 */                 ASN1Sequence seqin = (ASN1Sequence)tg.getObject();
/*  377 */                 findCRL(seqin);
/*      */               }
/*  379 */               if (tg.getTagNo() == 1) {
/*  380 */                 ASN1Sequence seqin = (ASN1Sequence)tg.getObject();
/*  381 */                 findOcsp(seqin);
/*      */               }
/*      */             }
/*      */           }
/*  385 */           else if ((this.isCades) && (idSeq2.equals("1.2.840.113549.1.9.16.2.12"))) {
/*  386 */             ASN1Set setout = (ASN1Set)seq2.getObjectAt(1);
/*  387 */             ASN1Sequence seqout = (ASN1Sequence)setout.getObjectAt(0);
/*  388 */             SigningCertificate sv2 = SigningCertificate.getInstance(seqout);
/*  389 */             ESSCertID[] cerv2m = sv2.getCerts();
/*  390 */             ESSCertID cerv2 = cerv2m[0];
/*  391 */             byte[] enc2 = this.signCert.getEncoded();
/*  392 */             MessageDigest m2 = new BouncyCastleDigest().getMessageDigest("SHA-1");
/*  393 */             byte[] signCertHash = m2.digest(enc2);
/*  394 */             byte[] hs2 = cerv2.getCertHash();
/*  395 */             if (!Arrays.equals(signCertHash, hs2))
/*  396 */               throw new IllegalArgumentException("Signing certificate doesn't match the ESS information.");
/*  397 */             foundCades = true;
/*      */           }
/*  399 */           else if ((this.isCades) && (idSeq2.equals("1.2.840.113549.1.9.16.2.47"))) {
/*  400 */             ASN1Set setout = (ASN1Set)seq2.getObjectAt(1);
/*  401 */             ASN1Sequence seqout = (ASN1Sequence)setout.getObjectAt(0);
/*  402 */             SigningCertificateV2 sv2 = SigningCertificateV2.getInstance(seqout);
/*  403 */             ESSCertIDv2[] cerv2m = sv2.getCerts();
/*  404 */             ESSCertIDv2 cerv2 = cerv2m[0];
/*  405 */             AlgorithmIdentifier ai2 = cerv2.getHashAlgorithm();
/*  406 */             byte[] enc2 = this.signCert.getEncoded();
/*  407 */             MessageDigest m2 = new BouncyCastleDigest().getMessageDigest(DigestAlgorithms.getDigest(ai2.getAlgorithm().getId()));
/*  408 */             byte[] signCertHash = m2.digest(enc2);
/*  409 */             byte[] hs2 = cerv2.getCertHash();
/*  410 */             if (!Arrays.equals(signCertHash, hs2))
/*  411 */               throw new IllegalArgumentException("Signing certificate doesn't match the ESS information.");
/*  412 */             foundCades = true;
/*      */           }
/*      */         }
/*  415 */         if (this.digestAttr == null)
/*  416 */           throw new IllegalArgumentException(MessageLocalization.getComposedMessage("authenticated.attribute.is.missing.the.digest", new Object[0]));
/*  417 */         next++;
/*      */       }
/*  419 */       if ((this.isCades) && (!foundCades))
/*  420 */         throw new IllegalArgumentException("CAdES ESS information missing.");
/*  421 */       this.digestEncryptionAlgorithmOid = ((ASN1ObjectIdentifier)((ASN1Sequence)signerInfo.getObjectAt(next++)).getObjectAt(0)).getId();
/*  422 */       this.digest = ((ASN1OctetString)signerInfo.getObjectAt(next++)).getOctets();
/*  423 */       if ((next < signerInfo.size()) && ((signerInfo.getObjectAt(next) instanceof ASN1TaggedObject))) {
/*  424 */         ASN1TaggedObject taggedObject = (ASN1TaggedObject)signerInfo.getObjectAt(next);
/*  425 */         ASN1Set unat = ASN1Set.getInstance(taggedObject, false);
/*  426 */         AttributeTable attble = new AttributeTable(unat);
/*  427 */         Attribute ts = attble.get(PKCSObjectIdentifiers.id_aa_signatureTimeStampToken);
/*  428 */         if ((ts != null) && (ts.getAttrValues().size() > 0)) {
/*  429 */           ASN1Set attributeValues = ts.getAttrValues();
/*  430 */           ASN1Sequence tokenSequence = ASN1Sequence.getInstance(attributeValues.getObjectAt(0));
/*  431 */           ContentInfo contentInfo = new ContentInfo(tokenSequence);
/*  432 */           this.timeStampToken = new TimeStampToken(contentInfo);
/*      */         }
/*      */       }
/*  435 */       if (this.isTsp) {
/*  436 */         ContentInfo contentInfoTsp = new ContentInfo(signedData);
/*  437 */         this.timeStampToken = new TimeStampToken(contentInfoTsp);
/*  438 */         TimeStampTokenInfo info = this.timeStampToken.getTimeStampInfo();
/*  439 */         String algOID = info.getMessageImprintAlgOID().getId();
/*  440 */         this.messageDigest = DigestAlgorithms.getMessageDigestFromOid(algOID, null);
/*      */       }
/*      */       else {
/*  443 */         if ((this.RSAdata != null) || (this.digestAttr != null)) {
/*  444 */           if (PdfName.ADBE_PKCS7_SHA1.equals(getFilterSubtype())) {
/*  445 */             this.messageDigest = DigestAlgorithms.getMessageDigest("SHA1", provider);
/*      */           }
/*      */           else {
/*  448 */             this.messageDigest = DigestAlgorithms.getMessageDigest(getHashAlgorithm(), provider);
/*      */           }
/*  450 */           this.encContDigest = DigestAlgorithms.getMessageDigest(getHashAlgorithm(), provider);
/*      */         }
/*  452 */         this.sig = initSignature(this.signCert.getPublicKey());
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*  456 */       throw new ExceptionConverter(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getSignName()
/*      */   {
/*  484 */     return this.signName;
/*      */   }
/*      */ 
/*      */   public void setSignName(String signName)
/*      */   {
/*  492 */     this.signName = signName;
/*      */   }
/*      */ 
/*      */   public String getReason()
/*      */   {
/*  500 */     return this.reason;
/*      */   }
/*      */ 
/*      */   public void setReason(String reason)
/*      */   {
/*  508 */     this.reason = reason;
/*      */   }
/*      */ 
/*      */   public String getLocation()
/*      */   {
/*  516 */     return this.location;
/*      */   }
/*      */ 
/*      */   public void setLocation(String location)
/*      */   {
/*  524 */     this.location = location;
/*      */   }
/*      */ 
/*      */   public Calendar getSignDate()
/*      */   {
/*  532 */     Calendar dt = getTimeStampDate();
/*  533 */     if (dt == null) {
/*  534 */       return this.signDate;
/*      */     }
/*  536 */     return dt;
/*      */   }
/*      */ 
/*      */   public void setSignDate(Calendar signDate)
/*      */   {
/*  544 */     this.signDate = signDate;
/*      */   }
/*      */ 
/*      */   public int getVersion()
/*      */   {
/*  560 */     return this.version;
/*      */   }
/*      */ 
/*      */   public int getSigningInfoVersion()
/*      */   {
/*  568 */     return this.signerversion;
/*      */   }
/*      */ 
/*      */   public String getDigestAlgorithmOid()
/*      */   {
/*  591 */     return this.digestAlgorithmOid;
/*      */   }
/*      */ 
/*      */   public String getHashAlgorithm()
/*      */   {
/*  599 */     return DigestAlgorithms.getDigest(this.digestAlgorithmOid);
/*      */   }
/*      */ 
/*      */   public String getDigestEncryptionAlgorithmOid()
/*      */   {
/*  611 */     return this.digestEncryptionAlgorithmOid;
/*      */   }
/*      */ 
/*      */   public String getDigestAlgorithm()
/*      */   {
/*  619 */     return getHashAlgorithm() + "with" + getEncryptionAlgorithm();
/*      */   }
/*      */ 
/*      */   public void setExternalDigest(byte[] digest, byte[] RSAdata, String digestEncryptionAlgorithm)
/*      */   {
/*  644 */     this.externalDigest = digest;
/*  645 */     this.externalRSAdata = RSAdata;
/*  646 */     if (digestEncryptionAlgorithm != null)
/*  647 */       if (digestEncryptionAlgorithm.equals("RSA")) {
/*  648 */         this.digestEncryptionAlgorithmOid = "1.2.840.113549.1.1.1";
/*      */       }
/*  650 */       else if (digestEncryptionAlgorithm.equals("DSA")) {
/*  651 */         this.digestEncryptionAlgorithmOid = "1.2.840.10040.4.1";
/*      */       }
/*  653 */       else if (digestEncryptionAlgorithm.equals("ECDSA")) {
/*  654 */         this.digestEncryptionAlgorithmOid = "1.2.840.10045.2.1";
/*      */       }
/*      */       else
/*  657 */         throw new ExceptionConverter(new NoSuchAlgorithmException(MessageLocalization.getComposedMessage("unknown.key.algorithm.1", new Object[] { digestEncryptionAlgorithm })));
/*      */   }
/*      */ 
/*      */   private Signature initSignature(PrivateKey key)
/*      */     throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException
/*      */   {
/*      */     Signature signature;
/*      */     Signature signature;
/*  676 */     if (this.provider == null)
/*  677 */       signature = Signature.getInstance(getDigestAlgorithm());
/*      */     else
/*  679 */       signature = Signature.getInstance(getDigestAlgorithm(), this.provider);
/*  680 */     signature.initSign(key);
/*  681 */     return signature;
/*      */   }
/*      */ 
/*      */   private Signature initSignature(PublicKey key) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
/*  685 */     String digestAlgorithm = getDigestAlgorithm();
/*  686 */     if (PdfName.ADBE_X509_RSA_SHA1.equals(getFilterSubtype()))
/*  687 */       digestAlgorithm = "SHA1withRSA";
/*      */     Signature signature;
/*      */     Signature signature;
/*  689 */     if (this.provider == null)
/*  690 */       signature = Signature.getInstance(digestAlgorithm);
/*      */     else {
/*  692 */       signature = Signature.getInstance(digestAlgorithm, this.provider);
/*      */     }
/*  694 */     signature.initVerify(key);
/*  695 */     return signature;
/*      */   }
/*      */ 
/*      */   public void update(byte[] buf, int off, int len)
/*      */     throws SignatureException
/*      */   {
/*  707 */     if ((this.RSAdata != null) || (this.digestAttr != null) || (this.isTsp))
/*  708 */       this.messageDigest.update(buf, off, len);
/*      */     else
/*  710 */       this.sig.update(buf, off, len);
/*      */   }
/*      */ 
/*      */   public byte[] getEncodedPKCS1()
/*      */   {
/*      */     try
/*      */     {
/*  721 */       if (this.externalDigest != null)
/*  722 */         this.digest = this.externalDigest;
/*      */       else
/*  724 */         this.digest = this.sig.sign();
/*  725 */       ByteArrayOutputStream bOut = new ByteArrayOutputStream();
/*      */ 
/*  727 */       ASN1OutputStream dout = new ASN1OutputStream(bOut);
/*  728 */       dout.writeObject(new DEROctetString(this.digest));
/*  729 */       dout.close();
/*      */ 
/*  731 */       return bOut.toByteArray();
/*      */     }
/*      */     catch (Exception e) {
/*  734 */       throw new ExceptionConverter(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public byte[] getEncodedPKCS7()
/*      */   {
/*  745 */     return getEncodedPKCS7(null, null, null, null, null, MakeSignature.CryptoStandard.CMS);
/*      */   }
/*      */ 
/*      */   public byte[] getEncodedPKCS7(byte[] secondDigest, Calendar signingTime)
/*      */   {
/*  756 */     return getEncodedPKCS7(secondDigest, signingTime, null, null, null, MakeSignature.CryptoStandard.CMS);
/*      */   }
/*      */ 
/*      */   public byte[] getEncodedPKCS7(byte[] secondDigest, Calendar signingTime, TSAClient tsaClient, byte[] ocsp, Collection<byte[]> crlBytes, MakeSignature.CryptoStandard sigtype)
/*      */   {
/*      */     try
/*      */     {
/*  771 */       if (this.externalDigest != null) {
/*  772 */         this.digest = this.externalDigest;
/*  773 */         if (this.RSAdata != null)
/*  774 */           this.RSAdata = this.externalRSAdata;
/*      */       }
/*  776 */       else if ((this.externalRSAdata != null) && (this.RSAdata != null)) {
/*  777 */         this.RSAdata = this.externalRSAdata;
/*  778 */         this.sig.update(this.RSAdata);
/*  779 */         this.digest = this.sig.sign();
/*      */       }
/*      */       else {
/*  782 */         if (this.RSAdata != null) {
/*  783 */           this.RSAdata = this.messageDigest.digest();
/*  784 */           this.sig.update(this.RSAdata);
/*      */         }
/*  786 */         this.digest = this.sig.sign();
/*      */       }
/*      */ 
/*  790 */       ASN1EncodableVector digestAlgorithms = new ASN1EncodableVector();
/*  791 */       for (Object element : this.digestalgos) {
/*  792 */         ASN1EncodableVector algos = new ASN1EncodableVector();
/*  793 */         algos.add(new ASN1ObjectIdentifier((String)element));
/*  794 */         algos.add(DERNull.INSTANCE);
/*  795 */         digestAlgorithms.add(new DERSequence(algos));
/*      */       }
/*      */ 
/*  799 */       ASN1EncodableVector v = new ASN1EncodableVector();
/*  800 */       v.add(new ASN1ObjectIdentifier("1.2.840.113549.1.7.1"));
/*  801 */       if (this.RSAdata != null)
/*  802 */         v.add(new DERTaggedObject(0, new DEROctetString(this.RSAdata)));
/*  803 */       DERSequence contentinfo = new DERSequence(v);
/*      */ 
/*  807 */       v = new ASN1EncodableVector();
/*  808 */       for (Object element : this.certs) {
/*  809 */         ASN1InputStream tempstream = new ASN1InputStream(new ByteArrayInputStream(((X509Certificate)element).getEncoded()));
/*  810 */         v.add(tempstream.readObject());
/*      */       }
/*      */ 
/*  813 */       DERSet dercertificates = new DERSet(v);
/*      */ 
/*  817 */       ASN1EncodableVector signerinfo = new ASN1EncodableVector();
/*      */ 
/*  821 */       signerinfo.add(new ASN1Integer(this.signerversion));
/*      */ 
/*  823 */       v = new ASN1EncodableVector();
/*  824 */       v.add(CertificateInfo.getIssuer(this.signCert.getTBSCertificate()));
/*  825 */       v.add(new ASN1Integer(this.signCert.getSerialNumber()));
/*  826 */       signerinfo.add(new DERSequence(v));
/*      */ 
/*  829 */       v = new ASN1EncodableVector();
/*  830 */       v.add(new ASN1ObjectIdentifier(this.digestAlgorithmOid));
/*  831 */       v.add(new DERNull());
/*  832 */       signerinfo.add(new DERSequence(v));
/*      */ 
/*  835 */       if ((secondDigest != null) && (signingTime != null)) {
/*  836 */         signerinfo.add(new DERTaggedObject(false, 0, getAuthenticatedAttributeSet(secondDigest, signingTime, ocsp, crlBytes, sigtype)));
/*      */       }
/*      */ 
/*  839 */       v = new ASN1EncodableVector();
/*  840 */       v.add(new ASN1ObjectIdentifier(this.digestEncryptionAlgorithmOid));
/*  841 */       v.add(new DERNull());
/*  842 */       signerinfo.add(new DERSequence(v));
/*      */ 
/*  845 */       signerinfo.add(new DEROctetString(this.digest));
/*      */ 
/*  850 */       if (tsaClient != null) {
/*  851 */         byte[] tsImprint = tsaClient.getMessageDigest().digest(this.digest);
/*  852 */         byte[] tsToken = tsaClient.getTimeStampToken(tsImprint);
/*  853 */         if (tsToken != null) {
/*  854 */           ASN1EncodableVector unauthAttributes = buildUnauthenticatedAttributes(tsToken);
/*  855 */           if (unauthAttributes != null) {
/*  856 */             signerinfo.add(new DERTaggedObject(false, 1, new DERSet(unauthAttributes)));
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  862 */       ASN1EncodableVector body = new ASN1EncodableVector();
/*  863 */       body.add(new ASN1Integer(this.version));
/*  864 */       body.add(new DERSet(digestAlgorithms));
/*  865 */       body.add(contentinfo);
/*  866 */       body.add(new DERTaggedObject(false, 0, dercertificates));
/*      */ 
/*  869 */       body.add(new DERSet(new DERSequence(signerinfo)));
/*      */ 
/*  874 */       ASN1EncodableVector whole = new ASN1EncodableVector();
/*  875 */       whole.add(new ASN1ObjectIdentifier("1.2.840.113549.1.7.2"));
/*  876 */       whole.add(new DERTaggedObject(0, new DERSequence(body)));
/*      */ 
/*  878 */       ByteArrayOutputStream bOut = new ByteArrayOutputStream();
/*      */ 
/*  880 */       ASN1OutputStream dout = new ASN1OutputStream(bOut);
/*  881 */       dout.writeObject(new DERSequence(whole));
/*  882 */       dout.close();
/*      */ 
/*  884 */       return bOut.toByteArray();
/*      */     }
/*      */     catch (Exception e) {
/*  887 */       throw new ExceptionConverter(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private ASN1EncodableVector buildUnauthenticatedAttributes(byte[] timeStampToken)
/*      */     throws IOException
/*      */   {
/*  901 */     if (timeStampToken == null) {
/*  902 */       return null;
/*      */     }
/*      */ 
/*  905 */     String ID_TIME_STAMP_TOKEN = "1.2.840.113549.1.9.16.2.14";
/*      */ 
/*  907 */     ASN1InputStream tempstream = new ASN1InputStream(new ByteArrayInputStream(timeStampToken));
/*  908 */     ASN1EncodableVector unauthAttributes = new ASN1EncodableVector();
/*      */ 
/*  910 */     ASN1EncodableVector v = new ASN1EncodableVector();
/*  911 */     v.add(new ASN1ObjectIdentifier(ID_TIME_STAMP_TOKEN));
/*  912 */     ASN1Sequence seq = (ASN1Sequence)tempstream.readObject();
/*  913 */     v.add(new DERSet(seq));
/*      */ 
/*  915 */     unauthAttributes.add(new DERSequence(v));
/*  916 */     return unauthAttributes;
/*      */   }
/*      */ 
/*      */   public byte[] getAuthenticatedAttributeBytes(byte[] secondDigest, Calendar signingTime, byte[] ocsp, Collection<byte[]> crlBytes, MakeSignature.CryptoStandard sigtype)
/*      */   {
/*      */     try
/*      */     {
/*  950 */       return getAuthenticatedAttributeSet(secondDigest, signingTime, ocsp, crlBytes, sigtype).getEncoded("DER");
/*      */     }
/*      */     catch (Exception e) {
/*  953 */       throw new ExceptionConverter(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private DERSet getAuthenticatedAttributeSet(byte[] secondDigest, Calendar signingTime, byte[] ocsp, Collection<byte[]> crlBytes, MakeSignature.CryptoStandard sigtype)
/*      */   {
/*      */     try
/*      */     {
/*  967 */       ASN1EncodableVector attribute = new ASN1EncodableVector();
/*  968 */       ASN1EncodableVector v = new ASN1EncodableVector();
/*  969 */       v.add(new ASN1ObjectIdentifier("1.2.840.113549.1.9.3"));
/*  970 */       v.add(new DERSet(new ASN1ObjectIdentifier("1.2.840.113549.1.7.1")));
/*  971 */       attribute.add(new DERSequence(v));
/*  972 */       v = new ASN1EncodableVector();
/*  973 */       v.add(new ASN1ObjectIdentifier("1.2.840.113549.1.9.5"));
/*  974 */       v.add(new DERSet(new DERUTCTime(signingTime.getTime())));
/*  975 */       attribute.add(new DERSequence(v));
/*  976 */       v = new ASN1EncodableVector();
/*  977 */       v.add(new ASN1ObjectIdentifier("1.2.840.113549.1.9.4"));
/*  978 */       v.add(new DERSet(new DEROctetString(secondDigest)));
/*  979 */       attribute.add(new DERSequence(v));
/*  980 */       boolean haveCrl = false;
/*  981 */       if (crlBytes != null) {
/*  982 */         for (byte[] bCrl : crlBytes) {
/*  983 */           if (bCrl != null) {
/*  984 */             haveCrl = true;
/*  985 */             break;
/*      */           }
/*      */         }
/*      */       }
/*  989 */       if ((ocsp != null) || (haveCrl)) {
/*  990 */         v = new ASN1EncodableVector();
/*  991 */         v.add(new ASN1ObjectIdentifier("1.2.840.113583.1.1.8"));
/*      */ 
/*  993 */         ASN1EncodableVector revocationV = new ASN1EncodableVector();
/*      */ 
/*  995 */         if (haveCrl) {
/*  996 */           ASN1EncodableVector v2 = new ASN1EncodableVector();
/*  997 */           for (byte[] bCrl : crlBytes)
/*  998 */             if (bCrl != null)
/*      */             {
/* 1000 */               ASN1InputStream t = new ASN1InputStream(new ByteArrayInputStream(bCrl));
/* 1001 */               v2.add(t.readObject());
/*      */             }
/* 1003 */           revocationV.add(new DERTaggedObject(true, 0, new DERSequence(v2)));
/*      */         }
/*      */ 
/* 1006 */         if (ocsp != null) {
/* 1007 */           DEROctetString doctet = new DEROctetString(ocsp);
/* 1008 */           ASN1EncodableVector vo1 = new ASN1EncodableVector();
/* 1009 */           ASN1EncodableVector v2 = new ASN1EncodableVector();
/* 1010 */           v2.add(OCSPObjectIdentifiers.id_pkix_ocsp_basic);
/* 1011 */           v2.add(doctet);
/* 1012 */           ASN1Enumerated den = new ASN1Enumerated(0);
/* 1013 */           ASN1EncodableVector v3 = new ASN1EncodableVector();
/* 1014 */           v3.add(den);
/* 1015 */           v3.add(new DERTaggedObject(true, 0, new DERSequence(v2)));
/* 1016 */           vo1.add(new DERSequence(v3));
/* 1017 */           revocationV.add(new DERTaggedObject(true, 1, new DERSequence(vo1)));
/*      */         }
/*      */ 
/* 1020 */         v.add(new DERSet(new DERSequence(revocationV)));
/* 1021 */         attribute.add(new DERSequence(v));
/*      */       }
/* 1023 */       if (sigtype == MakeSignature.CryptoStandard.CADES) {
/* 1024 */         v = new ASN1EncodableVector();
/* 1025 */         v.add(new ASN1ObjectIdentifier("1.2.840.113549.1.9.16.2.47"));
/*      */ 
/* 1027 */         ASN1EncodableVector aaV2 = new ASN1EncodableVector();
/* 1028 */         AlgorithmIdentifier algoId = new AlgorithmIdentifier(new ASN1ObjectIdentifier(this.digestAlgorithmOid), null);
/* 1029 */         aaV2.add(algoId);
/* 1030 */         MessageDigest md = this.interfaceDigest.getMessageDigest(getHashAlgorithm());
/* 1031 */         byte[] dig = md.digest(this.signCert.getEncoded());
/* 1032 */         aaV2.add(new DEROctetString(dig));
/*      */ 
/* 1034 */         v.add(new DERSet(new DERSequence(new DERSequence(new DERSequence(aaV2)))));
/* 1035 */         attribute.add(new DERSequence(v));
/*      */       }
/*      */ 
/* 1038 */       return new DERSet(attribute);
/*      */     }
/*      */     catch (Exception e) {
/* 1041 */       throw new ExceptionConverter(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean verify()
/*      */     throws GeneralSecurityException
/*      */   {
/* 1073 */     if (this.verified)
/* 1074 */       return this.verifyResult;
/* 1075 */     if (this.isTsp) {
/* 1076 */       TimeStampTokenInfo info = this.timeStampToken.getTimeStampInfo();
/* 1077 */       MessageImprint imprint = info.toASN1Structure().getMessageImprint();
/* 1078 */       byte[] md = this.messageDigest.digest();
/* 1079 */       byte[] imphashed = imprint.getHashedMessage();
/* 1080 */       this.verifyResult = Arrays.equals(md, imphashed);
/*      */     }
/* 1083 */     else if ((this.sigAttr != null) || (this.sigAttrDer != null)) {
/* 1084 */       byte[] msgDigestBytes = this.messageDigest.digest();
/* 1085 */       boolean verifyRSAdata = true;
/*      */ 
/* 1087 */       boolean encContDigestCompare = false;
/* 1088 */       if (this.RSAdata != null) {
/* 1089 */         verifyRSAdata = Arrays.equals(msgDigestBytes, this.RSAdata);
/* 1090 */         this.encContDigest.update(this.RSAdata);
/* 1091 */         encContDigestCompare = Arrays.equals(this.encContDigest.digest(), this.digestAttr);
/*      */       }
/* 1093 */       boolean absentEncContDigestCompare = Arrays.equals(msgDigestBytes, this.digestAttr);
/* 1094 */       boolean concludingDigestCompare = (absentEncContDigestCompare) || (encContDigestCompare);
/* 1095 */       boolean sigVerify = (verifySigAttributes(this.sigAttr)) || (verifySigAttributes(this.sigAttrDer));
/* 1096 */       this.verifyResult = ((concludingDigestCompare) && (sigVerify) && (verifyRSAdata));
/*      */     }
/*      */     else {
/* 1099 */       if (this.RSAdata != null)
/* 1100 */         this.sig.update(this.messageDigest.digest());
/* 1101 */       this.verifyResult = this.sig.verify(this.digest);
/*      */     }
/*      */ 
/* 1104 */     this.verified = true;
/* 1105 */     return this.verifyResult;
/*      */   }
/*      */ 
/*      */   private boolean verifySigAttributes(byte[] attr) throws GeneralSecurityException {
/* 1109 */     Signature signature = initSignature(this.signCert.getPublicKey());
/* 1110 */     signature.update(attr);
/* 1111 */     return signature.verify(this.digest);
/*      */   }
/*      */ 
/*      */   public boolean verifyTimestampImprint()
/*      */     throws GeneralSecurityException
/*      */   {
/* 1121 */     if (this.timeStampToken == null)
/* 1122 */       return false;
/* 1123 */     TimeStampTokenInfo info = this.timeStampToken.getTimeStampInfo();
/* 1124 */     MessageImprint imprint = info.toASN1Structure().getMessageImprint();
/* 1125 */     String algOID = info.getMessageImprintAlgOID().getId();
/* 1126 */     byte[] md = new BouncyCastleDigest().getMessageDigest(DigestAlgorithms.getDigest(algOID)).digest(this.digest);
/* 1127 */     byte[] imphashed = imprint.getHashedMessage();
/* 1128 */     boolean res = Arrays.equals(md, imphashed);
/* 1129 */     return res;
/*      */   }
/*      */ 
/*      */   public Certificate[] getCertificates()
/*      */   {
/* 1149 */     return (Certificate[])this.certs.toArray(new X509Certificate[this.certs.size()]);
/*      */   }
/*      */ 
/*      */   public Certificate[] getSignCertificateChain()
/*      */   {
/* 1160 */     return (Certificate[])this.signCerts.toArray(new X509Certificate[this.signCerts.size()]);
/*      */   }
/*      */ 
/*      */   public X509Certificate getSigningCertificate()
/*      */   {
/* 1168 */     return this.signCert;
/*      */   }
/*      */ 
/*      */   private void signCertificateChain()
/*      */   {
/* 1177 */     ArrayList cc = new ArrayList();
/* 1178 */     cc.add(this.signCert);
/* 1179 */     ArrayList oc = new ArrayList(this.certs);
/* 1180 */     for (int k = 0; k < oc.size(); k++) {
/* 1181 */       if (this.signCert.equals(oc.get(k))) {
/* 1182 */         oc.remove(k);
/* 1183 */         k--;
/*      */       }
/*      */     }
/*      */ 
/* 1187 */     boolean found = true;
/* 1188 */     while (found) {
/* 1189 */       X509Certificate v = (X509Certificate)cc.get(cc.size() - 1);
/* 1190 */       found = false;
/* 1191 */       for (int k = 0; k < oc.size(); k++) {
/* 1192 */         X509Certificate issuer = (X509Certificate)oc.get(k);
/*      */         try {
/* 1194 */           if (this.provider == null)
/* 1195 */             v.verify(issuer.getPublicKey());
/*      */           else
/* 1197 */             v.verify(issuer.getPublicKey(), this.provider);
/* 1198 */           found = true;
/* 1199 */           cc.add(oc.get(k));
/* 1200 */           oc.remove(k);
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*      */         }
/*      */       }
/*      */     }
/* 1207 */     this.signCerts = cc;
/*      */   }
/*      */ 
/*      */   public Collection<CRL> getCRLs()
/*      */   {
/* 1219 */     return this.crls;
/*      */   }
/*      */ 
/*      */   private void findCRL(ASN1Sequence seq)
/*      */   {
/*      */     try
/*      */     {
/* 1227 */       this.crls = new ArrayList();
/* 1228 */       for (int k = 0; k < seq.size(); k++) {
/* 1229 */         ByteArrayInputStream ar = new ByteArrayInputStream(seq.getObjectAt(k).toASN1Primitive().getEncoded("DER"));
/* 1230 */         CertificateFactory cf = CertificateFactory.getInstance("X.509");
/* 1231 */         X509CRL crl = (X509CRL)cf.generateCRL(ar);
/* 1232 */         this.crls.add(crl);
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public BasicOCSPResp getOcsp()
/*      */   {
/* 1251 */     return this.basicResp;
/*      */   }
/*      */ 
/*      */   public boolean isRevocationValid()
/*      */   {
/* 1260 */     if (this.basicResp == null)
/* 1261 */       return false;
/* 1262 */     if (this.signCerts.size() < 2)
/* 1263 */       return false;
/*      */     try {
/* 1265 */       X509Certificate[] cs = (X509Certificate[])getSignCertificateChain();
/* 1266 */       SingleResp sr = this.basicResp.getResponses()[0];
/* 1267 */       CertificateID cid = sr.getCertID();
/* 1268 */       DigestCalculator digestalg = new JcaDigestCalculatorProviderBuilder().build().get(new AlgorithmIdentifier(cid.getHashAlgOID(), DERNull.INSTANCE));
/* 1269 */       X509Certificate sigcer = getSigningCertificate();
/* 1270 */       X509Certificate isscer = cs[1];
/* 1271 */       CertificateID tis = new CertificateID(digestalg, new JcaX509CertificateHolder(isscer), sigcer.getSerialNumber());
/*      */ 
/* 1273 */       return tis.equals(cid);
/*      */     }
/*      */     catch (Exception ex) {
/*      */     }
/* 1277 */     return false;
/*      */   }
/*      */ 
/*      */   private void findOcsp(ASN1Sequence seq)
/*      */     throws IOException
/*      */   {
/* 1286 */     this.basicResp = null;
/* 1287 */     boolean ret = false;
/*      */ 
/* 1289 */     while ((!(seq.getObjectAt(0) instanceof ASN1ObjectIdentifier)) || (!((ASN1ObjectIdentifier)seq.getObjectAt(0)).getId().equals(OCSPObjectIdentifiers.id_pkix_ocsp_basic.getId())))
/*      */     {
/* 1293 */       ret = true;
/* 1294 */       for (int k = 0; k < seq.size(); k++) {
/* 1295 */         if ((seq.getObjectAt(k) instanceof ASN1Sequence)) {
/* 1296 */           seq = (ASN1Sequence)seq.getObjectAt(0);
/* 1297 */           ret = false;
/* 1298 */           break;
/*      */         }
/* 1300 */         if ((seq.getObjectAt(k) instanceof ASN1TaggedObject)) {
/* 1301 */           ASN1TaggedObject tag = (ASN1TaggedObject)seq.getObjectAt(k);
/* 1302 */           if ((tag.getObject() instanceof ASN1Sequence)) {
/* 1303 */             seq = (ASN1Sequence)tag.getObject();
/* 1304 */             ret = false;
/* 1305 */             break;
/*      */           }
/*      */ 
/* 1308 */           return;
/*      */         }
/*      */       }
/* 1311 */       if (ret)
/* 1312 */         return;
/*      */     }
/* 1314 */     ASN1OctetString os = (ASN1OctetString)seq.getObjectAt(1);
/* 1315 */     ASN1InputStream inp = new ASN1InputStream(os.getOctets());
/* 1316 */     BasicOCSPResponse resp = BasicOCSPResponse.getInstance(inp.readObject());
/* 1317 */     this.basicResp = new BasicOCSPResp(resp);
/*      */   }
/*      */ 
/*      */   public boolean isTsp()
/*      */   {
/* 1336 */     return this.isTsp;
/*      */   }
/*      */ 
/*      */   public TimeStampToken getTimeStampToken()
/*      */   {
/* 1345 */     return this.timeStampToken;
/*      */   }
/*      */ 
/*      */   public Calendar getTimeStampDate()
/*      */   {
/* 1354 */     if (this.timeStampToken == null)
/* 1355 */       return null;
/* 1356 */     Calendar cal = new GregorianCalendar();
/* 1357 */     Date date = this.timeStampToken.getTimeStampInfo().getGenTime();
/* 1358 */     cal.setTime(date);
/* 1359 */     return cal;
/*      */   }
/*      */ 
/*      */   public PdfName getFilterSubtype()
/*      */   {
/* 1366 */     return this.filterSubtype;
/*      */   }
/*      */ 
/*      */   public String getEncryptionAlgorithm()
/*      */   {
/* 1374 */     String encryptAlgo = EncryptionAlgorithms.getAlgorithm(this.digestEncryptionAlgorithmOid);
/* 1375 */     if (encryptAlgo == null)
/* 1376 */       encryptAlgo = this.digestEncryptionAlgorithmOid;
/* 1377 */     return encryptAlgo;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.PdfPKCS7
 * JD-Core Version:    0.6.2
 */