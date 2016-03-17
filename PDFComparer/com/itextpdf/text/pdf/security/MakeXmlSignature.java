/*     */ package com.itextpdf.text.pdf.security;
/*     */ 
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.pdf.XmlSignatureAppearance;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.StringWriter;
/*     */ import java.math.BigInteger;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.Key;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.PublicKey;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import javax.xml.crypto.XMLStructure;
/*     */ import javax.xml.crypto.dom.DOMStructure;
/*     */ import javax.xml.crypto.dsig.DigestMethod;
/*     */ import javax.xml.crypto.dsig.Reference;
/*     */ import javax.xml.crypto.dsig.XMLObject;
/*     */ import javax.xml.crypto.dsig.XMLSignatureFactory;
/*     */ import javax.xml.crypto.dsig.dom.DOMSignContext;
/*     */ import javax.xml.crypto.dsig.keyinfo.KeyInfo;
/*     */ import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
/*     */ import javax.xml.crypto.dsig.keyinfo.KeyValue;
/*     */ import javax.xml.crypto.dsig.keyinfo.X509Data;
/*     */ import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
/*     */ import javax.xml.crypto.dsig.spec.TransformParameterSpec;
/*     */ import javax.xml.crypto.dsig.spec.XPathFilter2ParameterSpec;
/*     */ import javax.xml.crypto.dsig.spec.XPathType;
/*     */ import javax.xml.crypto.dsig.spec.XPathType.Filter;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import org.apache.jcp.xml.dsig.internal.dom.DOMKeyInfoFactory;
/*     */ import org.apache.jcp.xml.dsig.internal.dom.DOMReference;
/*     */ import org.apache.jcp.xml.dsig.internal.dom.DOMSignedInfo;
/*     */ import org.apache.jcp.xml.dsig.internal.dom.DOMUtils;
/*     */ import org.apache.jcp.xml.dsig.internal.dom.DOMXMLSignature;
/*     */ import org.apache.jcp.xml.dsig.internal.dom.XMLDSigRI;
/*     */ import org.apache.xml.security.utils.Base64;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class MakeXmlSignature
/*     */ {
/*     */   public static void signXmlDSig(XmlSignatureAppearance sap, ExternalSignature externalSignature, KeyInfo keyInfo)
/*     */     throws GeneralSecurityException, IOException, DocumentException
/*     */   {
/* 127 */     verifyArguments(sap, externalSignature);
/* 128 */     XMLSignatureFactory fac = createSignatureFactory();
/* 129 */     Reference reference = generateContentReference(fac, sap, null);
/* 130 */     String signatureMethod = null;
/* 131 */     if (externalSignature.getEncryptionAlgorithm().equals("RSA"))
/* 132 */       signatureMethod = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
/* 133 */     else if (externalSignature.getEncryptionAlgorithm().equals("DSA")) {
/* 134 */       signatureMethod = "http://www.w3.org/2000/09/xmldsig#dsa-sha1";
/*     */     }
/*     */ 
/* 137 */     DOMSignedInfo signedInfo = (DOMSignedInfo)fac.newSignedInfo(fac.newCanonicalizationMethod("http://www.w3.org/TR/2001/REC-xml-c14n-20010315", (C14NMethodParameterSpec)null), fac.newSignatureMethod(signatureMethod, null), Collections.singletonList(reference));
/*     */ 
/* 141 */     sign(fac, externalSignature, sap.getXmlLocator(), signedInfo, null, keyInfo, null);
/*     */ 
/* 143 */     sap.close();
/*     */   }
/*     */ 
/*     */   public static void signXmlDSig(XmlSignatureAppearance sap, ExternalSignature externalSignature, Certificate[] chain)
/*     */     throws DocumentException, GeneralSecurityException, IOException
/*     */   {
/* 157 */     signXmlDSig(sap, externalSignature, generateKeyInfo(chain, sap));
/*     */   }
/*     */ 
/*     */   public static void signXmlDSig(XmlSignatureAppearance sap, ExternalSignature externalSignature, PublicKey publicKey)
/*     */     throws GeneralSecurityException, DocumentException, IOException
/*     */   {
/* 171 */     signXmlDSig(sap, externalSignature, generateKeyInfo(publicKey));
/*     */   }
/*     */ 
/*     */   public static void signXades(XmlSignatureAppearance sap, ExternalSignature externalSignature, Certificate[] chain, boolean includeSignaturePolicy)
/*     */     throws GeneralSecurityException, DocumentException, IOException
/*     */   {
/* 188 */     verifyArguments(sap, externalSignature);
/*     */ 
/* 190 */     String signatureMethod = null;
/* 191 */     if (externalSignature.getEncryptionAlgorithm().equals("RSA"))
/* 192 */       signatureMethod = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
/* 193 */     else if (externalSignature.getEncryptionAlgorithm().equals("DSA")) {
/* 194 */       signatureMethod = "http://www.w3.org/2000/09/xmldsig#dsa-sha1";
/*     */     }
/* 196 */     String contentReferenceId = "Reference-" + getRandomId();
/* 197 */     String signedPropertiesId = "SignedProperties-" + getRandomId();
/* 198 */     String signatureId = "Signature-" + getRandomId();
/*     */ 
/* 200 */     XMLSignatureFactory fac = createSignatureFactory();
/*     */ 
/* 202 */     KeyInfo keyInfo = generateKeyInfo(chain, sap);
/* 203 */     String[] signaturePolicy = null;
/* 204 */     if (includeSignaturePolicy) {
/* 205 */       signaturePolicy = new String[2];
/* 206 */       if (signatureMethod.equals("http://www.w3.org/2000/09/xmldsig#rsa-sha1")) {
/* 207 */         signaturePolicy[0] = "urn:oid:1.2.840.113549.1.1.5";
/* 208 */         signaturePolicy[1] = "RSA (PKCS #1 v1.5) with SHA-1 signature";
/*     */       } else {
/* 210 */         signaturePolicy[0] = "urn:oid:1.2.840.10040.4.3";
/* 211 */         signaturePolicy[1] = "ANSI X9.57 DSA signature generated with SHA-1 hash (DSA x9.30)";
/*     */       }
/*     */     }
/* 214 */     XMLObject xmlObject = generateXadesObject(fac, sap, signatureId, contentReferenceId, signedPropertiesId, signaturePolicy);
/* 215 */     Reference contentReference = generateContentReference(fac, sap, contentReferenceId);
/* 216 */     Reference signedPropertiesReference = generateCustomReference(fac, "#" + signedPropertiesId, "http://uri.etsi.org/01903#SignedProperties", null);
/*     */ 
/* 218 */     List references = Arrays.asList(new Reference[] { signedPropertiesReference, contentReference });
/*     */ 
/* 221 */     DOMSignedInfo signedInfo = (DOMSignedInfo)fac.newSignedInfo(fac.newCanonicalizationMethod("http://www.w3.org/TR/2001/REC-xml-c14n-20010315", (C14NMethodParameterSpec)null), fac.newSignatureMethod(signatureMethod, null), references, null);
/*     */ 
/* 225 */     sign(fac, externalSignature, sap.getXmlLocator(), signedInfo, xmlObject, keyInfo, signatureId);
/*     */ 
/* 227 */     sap.close();
/*     */   }
/*     */ 
/*     */   public static void signXadesBes(XmlSignatureAppearance sap, ExternalSignature externalSignature, Certificate[] chain)
/*     */     throws GeneralSecurityException, DocumentException, IOException
/*     */   {
/* 241 */     signXades(sap, externalSignature, chain, false);
/*     */   }
/*     */ 
/*     */   public static void signXadesEpes(XmlSignatureAppearance sap, ExternalSignature externalSignature, Certificate[] chain)
/*     */     throws GeneralSecurityException, DocumentException, IOException
/*     */   {
/* 255 */     signXades(sap, externalSignature, chain, true);
/*     */   }
/*     */ 
/*     */   private static void verifyArguments(XmlSignatureAppearance sap, ExternalSignature externalSignature) throws DocumentException
/*     */   {
/* 260 */     if (sap.getXmlLocator() == null)
/* 261 */       throw new DocumentException(MessageLocalization.getComposedMessage("xmllocator.cannot.be.null", new Object[0]));
/* 262 */     if (!externalSignature.getHashAlgorithm().equals("SHA1")) {
/* 263 */       throw new UnsupportedOperationException(MessageLocalization.getComposedMessage("support.only.sha1.hash.algorithm", new Object[0]));
/*     */     }
/* 265 */     if ((!externalSignature.getEncryptionAlgorithm().equals("RSA")) && (!externalSignature.getEncryptionAlgorithm().equals("DSA")))
/* 266 */       throw new UnsupportedOperationException(MessageLocalization.getComposedMessage("support.only.rsa.and.dsa.algorithms", new Object[0]));
/*     */   }
/*     */ 
/*     */   private static Element findElement(NodeList nodes, String localName)
/*     */   {
/* 273 */     for (int i = nodes.getLength() - 1; i >= 0; i--) {
/* 274 */       Node currNode = nodes.item(i);
/* 275 */       if ((currNode.getNodeType() == 1) && (currNode.getLocalName().equals(localName)))
/* 276 */         return (Element)currNode;
/*     */     }
/* 278 */     return null;
/*     */   }
/*     */ 
/*     */   private static KeyInfo generateKeyInfo(Certificate[] chain, XmlSignatureAppearance sap) {
/* 282 */     Certificate certificate = chain[0];
/* 283 */     sap.setCertificate(certificate);
/* 284 */     KeyInfoFactory kif = new DOMKeyInfoFactory();
/*     */ 
/* 286 */     X509Data x509d = kif.newX509Data(Collections.singletonList(certificate));
/*     */ 
/* 288 */     return kif.newKeyInfo(Collections.singletonList(x509d));
/*     */   }
/*     */ 
/*     */   private static KeyInfo generateKeyInfo(PublicKey publicKey) throws GeneralSecurityException {
/* 292 */     KeyInfoFactory kif = new DOMKeyInfoFactory();
/* 293 */     KeyValue kv = kif.newKeyValue(publicKey);
/* 294 */     return kif.newKeyInfo(Collections.singletonList(kv));
/*     */   }
/*     */ 
/*     */   private static String getRandomId() {
/* 298 */     return UUID.randomUUID().toString().substring(24);
/*     */   }
/*     */ 
/*     */   private static XMLSignatureFactory createSignatureFactory() {
/* 302 */     return XMLSignatureFactory.getInstance("DOM", new XMLDSigRI());
/*     */   }
/*     */ 
/*     */   private static XMLObject generateXadesObject(XMLSignatureFactory fac, XmlSignatureAppearance sap, String signatureId, String contentReferenceId, String signedPropertiesId, String[] signaturePolicy)
/*     */     throws GeneralSecurityException
/*     */   {
/* 309 */     MessageDigest md = MessageDigest.getInstance("SHA1");
/* 310 */     Certificate cert = sap.getCertificate();
/*     */ 
/* 312 */     Document doc = sap.getXmlLocator().getDocument();
/*     */ 
/* 314 */     Element QualifyingProperties = doc.createElementNS("http://uri.etsi.org/01903/v1.3.2#", "xades:QualifyingProperties");
/* 315 */     QualifyingProperties.setAttribute("Target", "#" + signatureId);
/* 316 */     Element SignedProperties = doc.createElementNS("http://uri.etsi.org/01903/v1.3.2#", "xades:SignedProperties");
/* 317 */     SignedProperties.setAttribute("Id", signedPropertiesId);
/* 318 */     SignedProperties.setIdAttribute("Id", true);
/* 319 */     Element SignedSignatureProperties = doc.createElementNS("http://uri.etsi.org/01903/v1.3.2#", "xades:SignedSignatureProperties");
/* 320 */     Element SigningTime = doc.createElementNS("http://uri.etsi.org/01903/v1.3.2#", "xades:SigningTime");
/* 321 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
/* 322 */     String result = sdf.format(sap.getSignDate().getTime());
/* 323 */     result = result.substring(0, result.length() - 2).concat(":").concat(result.substring(result.length() - 2));
/* 324 */     SigningTime.appendChild(doc.createTextNode(result));
/* 325 */     SignedSignatureProperties.appendChild(SigningTime);
/* 326 */     Element SigningCertificate = doc.createElementNS("http://uri.etsi.org/01903/v1.3.2#", "xades:SigningCertificate");
/* 327 */     Element Cert = doc.createElementNS("http://uri.etsi.org/01903/v1.3.2#", "xades:Cert");
/* 328 */     Element CertDigest = doc.createElementNS("http://uri.etsi.org/01903/v1.3.2#", "xades:CertDigest");
/* 329 */     Element DigestMethod = doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "DigestMethod");
/* 330 */     DigestMethod.setAttribute("Algorithm", "http://www.w3.org/2000/09/xmldsig#sha1");
/* 331 */     CertDigest.appendChild(DigestMethod);
/* 332 */     Element DigestValue = doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "DigestValue");
/* 333 */     DigestValue.appendChild(doc.createTextNode(Base64.encode(md.digest(cert.getEncoded()))));
/* 334 */     CertDigest.appendChild(DigestValue);
/* 335 */     Cert.appendChild(CertDigest);
/* 336 */     if ((cert instanceof X509Certificate)) {
/* 337 */       Element IssueSerial = doc.createElementNS("http://uri.etsi.org/01903/v1.3.2#", "xades:IssuerSerial");
/* 338 */       Element X509IssuerName = doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "X509IssuerName");
/* 339 */       X509IssuerName.appendChild(doc.createTextNode(getX509IssuerName((X509Certificate)cert)));
/* 340 */       IssueSerial.appendChild(X509IssuerName);
/* 341 */       Element X509SerialNumber = doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "X509SerialNumber");
/* 342 */       X509SerialNumber.appendChild(doc.createTextNode(getX509SerialNumber((X509Certificate)cert)));
/* 343 */       IssueSerial.appendChild(X509SerialNumber);
/* 344 */       Cert.appendChild(IssueSerial);
/*     */     }
/* 346 */     SigningCertificate.appendChild(Cert);
/* 347 */     SignedSignatureProperties.appendChild(SigningCertificate);
/* 348 */     if (signaturePolicy != null) {
/* 349 */       Element SignaturePolicyIdentifier = doc.createElementNS("http://uri.etsi.org/01903/v1.3.2#", "xades:SignaturePolicyIdentifier");
/* 350 */       Element SignaturePolicyId = doc.createElementNS("http://uri.etsi.org/01903/v1.3.2#", "xades:SignaturePolicyId");
/* 351 */       Element SigPolicyId = doc.createElementNS("http://uri.etsi.org/01903/v1.3.2#", "xades:SigPolicyId");
/* 352 */       Element Identifier = doc.createElementNS("http://uri.etsi.org/01903/v1.3.2#", "xades:Identifier");
/* 353 */       Identifier.appendChild(doc.createTextNode(signaturePolicy[0]));
/* 354 */       Identifier.setAttribute("Qualifier", "OIDAsURN");
/* 355 */       SigPolicyId.appendChild(Identifier);
/*     */ 
/* 357 */       Element Description = doc.createElementNS("http://uri.etsi.org/01903/v1.3.2#", "xades:Description");
/* 358 */       Description.appendChild(doc.createTextNode(signaturePolicy[1]));
/* 359 */       SigPolicyId.appendChild(Description);
/* 360 */       SignaturePolicyId.appendChild(SigPolicyId);
/* 361 */       Element SigPolicyHash = doc.createElementNS("http://uri.etsi.org/01903/v1.3.2#", "xades:SigPolicyHash");
/* 362 */       DigestMethod = doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "DigestMethod");
/* 363 */       DigestMethod.setAttribute("Algorithm", "http://www.w3.org/2000/09/xmldsig#sha1");
/* 364 */       SigPolicyHash.appendChild(DigestMethod);
/* 365 */       DigestValue = doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "DigestValue");
/* 366 */       byte[] policyIdContent = getByteArrayOfNode(SigPolicyId);
/* 367 */       DigestValue.appendChild(doc.createTextNode(Base64.encode(md.digest(policyIdContent))));
/* 368 */       SigPolicyHash.appendChild(DigestValue);
/* 369 */       SignaturePolicyId.appendChild(SigPolicyHash);
/* 370 */       SignaturePolicyIdentifier.appendChild(SignaturePolicyId);
/* 371 */       SignedSignatureProperties.appendChild(SignaturePolicyIdentifier);
/*     */     }
/* 373 */     SignedProperties.appendChild(SignedSignatureProperties);
/* 374 */     Element SignedDataObjectProperties = doc.createElement("xades:SignedDataObjectProperties");
/* 375 */     Element DataObjectFormat = doc.createElement("xades:DataObjectFormat");
/* 376 */     DataObjectFormat.setAttribute("ObjectReference", "#" + contentReferenceId);
/* 377 */     String descr = sap.getDescription();
/* 378 */     if (descr != null) {
/* 379 */       Element Description = doc.createElement("xades:Description");
/* 380 */       Description.appendChild(doc.createTextNode(descr));
/* 381 */       DataObjectFormat.appendChild(Description);
/*     */     }
/* 383 */     Element MimeType = doc.createElement("xades:MimeType");
/* 384 */     MimeType.appendChild(doc.createTextNode(sap.getMimeType()));
/* 385 */     DataObjectFormat.appendChild(MimeType);
/* 386 */     String enc = sap.getXmlLocator().getEncoding();
/* 387 */     if (enc != null) {
/* 388 */       Element Encoding = doc.createElement("xades:Encoding");
/* 389 */       Encoding.appendChild(doc.createTextNode(enc));
/* 390 */       DataObjectFormat.appendChild(Encoding);
/*     */     }
/* 392 */     SignedDataObjectProperties.appendChild(DataObjectFormat);
/* 393 */     SignedProperties.appendChild(SignedDataObjectProperties);
/* 394 */     QualifyingProperties.appendChild(SignedProperties);
/*     */ 
/* 396 */     XMLStructure content = new DOMStructure(QualifyingProperties);
/* 397 */     return fac.newXMLObject(Collections.singletonList(content), null, null, null);
/*     */   }
/*     */ 
/*     */   private static String getX509IssuerName(X509Certificate cert) {
/* 401 */     return cert.getIssuerX500Principal().toString();
/*     */   }
/*     */ 
/*     */   private static String getX509SerialNumber(X509Certificate cert) {
/* 405 */     return cert.getSerialNumber().toString();
/*     */   }
/*     */ 
/*     */   private static Reference generateContentReference(XMLSignatureFactory fac, XmlSignatureAppearance sap, String referenceId) throws GeneralSecurityException
/*     */   {
/* 410 */     DigestMethod digestMethodSHA1 = fac.newDigestMethod("http://www.w3.org/2000/09/xmldsig#sha1", null);
/*     */ 
/* 412 */     List transforms = new ArrayList();
/* 413 */     transforms.add(fac.newTransform("http://www.w3.org/2000/09/xmldsig#enveloped-signature", (TransformParameterSpec)null));
/*     */ 
/* 416 */     XpathConstructor xpathConstructor = sap.getXpathConstructor();
/* 417 */     if ((xpathConstructor != null) && (xpathConstructor.getXpathExpression().length() > 0)) {
/* 418 */       XPathFilter2ParameterSpec xpath2Spec = new XPathFilter2ParameterSpec(Collections.singletonList(new XPathType(xpathConstructor.getXpathExpression(), XPathType.Filter.INTERSECT)));
/* 419 */       transforms.add(fac.newTransform("http://www.w3.org/2002/06/xmldsig-filter2", xpath2Spec));
/*     */     }
/* 421 */     return fac.newReference("", digestMethodSHA1, transforms, null, referenceId);
/*     */   }
/*     */ 
/*     */   private static Reference generateCustomReference(XMLSignatureFactory fac, String uri, String type, String id) throws GeneralSecurityException {
/* 425 */     DigestMethod dsDigestMethod = fac.newDigestMethod("http://www.w3.org/2000/09/xmldsig#sha1", null);
/* 426 */     return fac.newReference(uri, dsDigestMethod, null, type, id);
/*     */   }
/*     */ 
/*     */   private static void sign(XMLSignatureFactory fac, ExternalSignature externalSignature, XmlLocator locator, DOMSignedInfo si, XMLObject xo, KeyInfo ki, String signatureId)
/*     */     throws DocumentException
/*     */   {
/* 432 */     Document doc = locator.getDocument();
/*     */ 
/* 434 */     DOMSignContext domSignContext = new DOMSignContext(EmptyKey.getInstance(), doc.getDocumentElement());
/*     */ 
/* 436 */     List objects = null;
/* 437 */     if (xo != null)
/* 438 */       objects = Collections.singletonList(xo);
/* 439 */     DOMXMLSignature signature = (DOMXMLSignature)fac.newXMLSignature(si, ki, objects, signatureId, null);
/*     */ 
/* 441 */     ByteArrayOutputStream byteRange = new ByteArrayOutputStream();
/*     */     try {
/* 443 */       signature.marshal(domSignContext.getParent(), domSignContext.getNextSibling(), DOMUtils.getSignaturePrefix(domSignContext), domSignContext);
/*     */ 
/* 445 */       Element signElement = findElement(doc.getDocumentElement().getChildNodes(), "Signature");
/* 446 */       if (signatureId != null) {
/* 447 */         signElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xades", "http://uri.etsi.org/01903/v1.3.2#");
/*     */       }
/* 449 */       List references = si.getReferences();
/* 450 */       for (int i = 0; i < references.size(); i++)
/* 451 */         ((DOMReference)references.get(i)).digest(domSignContext);
/* 452 */       si.canonicalize(domSignContext, byteRange);
/*     */ 
/* 454 */       Element signValue = findElement(signElement.getChildNodes(), "SignatureValue");
/*     */ 
/* 457 */       String valueBase64 = Base64.encode(externalSignature.sign(byteRange.toByteArray()));
/*     */ 
/* 459 */       signValue.appendChild(doc.createTextNode(valueBase64));
/* 460 */       locator.setDocument(doc);
/*     */     } catch (Exception e) {
/* 462 */       throw new DocumentException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static byte[] getByteArrayOfNode(Node node) {
/* 467 */     ByteArrayOutputStream stream = new ByteArrayOutputStream();
/*     */     try {
/* 469 */       StreamResult xmlOutput = new StreamResult(new StringWriter());
/* 470 */       Transformer transformer = TransformerFactory.newInstance().newTransformer();
/* 471 */       transformer.setOutputProperty("omit-xml-declaration", "yes");
/* 472 */       transformer.transform(new DOMSource(node), xmlOutput);
/* 473 */       return xmlOutput.getWriter().toString().getBytes();
/*     */     } catch (Exception e) {
/*     */     }
/* 476 */     return stream.toByteArray();
/*     */   }
/*     */ 
/*     */   private static class EmptyKey
/*     */     implements Key
/*     */   {
/*  96 */     private static EmptyKey instance = new EmptyKey();
/*     */ 
/*     */     public static EmptyKey getInstance() {
/*  99 */       return instance;
/*     */     }
/*     */ 
/*     */     public String getAlgorithm() {
/* 103 */       return null;
/*     */     }
/*     */ 
/*     */     public String getFormat() {
/* 107 */       return null;
/*     */     }
/*     */ 
/*     */     public byte[] getEncoded() {
/* 111 */       return new byte[0];
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.MakeXmlSignature
 * JD-Core Version:    0.6.2
 */