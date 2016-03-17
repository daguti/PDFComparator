/*     */ package com.itextpdf.text.pdf.security;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.security.cert.CRL;
/*     */ import java.security.cert.CRLException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.CertificateParsingException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import org.bouncycastle.asn1.ASN1Encodable;
/*     */ import org.bouncycastle.asn1.ASN1InputStream;
/*     */ import org.bouncycastle.asn1.ASN1ObjectIdentifier;
/*     */ import org.bouncycastle.asn1.ASN1OctetString;
/*     */ import org.bouncycastle.asn1.ASN1Primitive;
/*     */ import org.bouncycastle.asn1.ASN1Sequence;
/*     */ import org.bouncycastle.asn1.ASN1TaggedObject;
/*     */ import org.bouncycastle.asn1.DERIA5String;
/*     */ import org.bouncycastle.asn1.DEROctetString;
/*     */ import org.bouncycastle.asn1.x509.CRLDistPoint;
/*     */ import org.bouncycastle.asn1.x509.DistributionPoint;
/*     */ import org.bouncycastle.asn1.x509.DistributionPointName;
/*     */ import org.bouncycastle.asn1.x509.Extension;
/*     */ import org.bouncycastle.asn1.x509.GeneralName;
/*     */ import org.bouncycastle.asn1.x509.GeneralNames;
/*     */ 
/*     */ public class CertificateUtil
/*     */ {
/*     */   public static CRL getCRL(X509Certificate certificate)
/*     */     throws CertificateException, CRLException, IOException
/*     */   {
/*  91 */     return getCRL(getCRLURL(certificate));
/*     */   }
/*     */ 
/*     */   public static String getCRLURL(X509Certificate certificate)
/*     */     throws CertificateParsingException
/*     */   {
/*     */     ASN1Primitive obj;
/*     */     try
/*     */     {
/* 104 */       obj = getExtensionValue(certificate, Extension.cRLDistributionPoints.getId());
/*     */     } catch (IOException e) {
/* 106 */       obj = null;
/*     */     }
/* 108 */     if (obj == null) {
/* 109 */       return null;
/*     */     }
/* 111 */     CRLDistPoint dist = CRLDistPoint.getInstance(obj);
/* 112 */     DistributionPoint[] dists = dist.getDistributionPoints();
/* 113 */     for (DistributionPoint p : dists) {
/* 114 */       DistributionPointName distributionPointName = p.getDistributionPoint();
/* 115 */       if (0 == distributionPointName.getType())
/*     */       {
/* 118 */         GeneralNames generalNames = (GeneralNames)distributionPointName.getName();
/* 119 */         GeneralName[] names = generalNames.getNames();
/* 120 */         for (GeneralName name : names)
/* 121 */           if (name.getTagNo() == 6)
/*     */           {
/* 124 */             DERIA5String derStr = DERIA5String.getInstance((ASN1TaggedObject)name.toASN1Primitive(), false);
/* 125 */             return derStr.getString();
/*     */           }
/*     */       }
/*     */     }
/* 128 */     return null;
/*     */   }
/*     */ 
/*     */   public static CRL getCRL(String url)
/*     */     throws IOException, CertificateException, CRLException
/*     */   {
/* 140 */     if (url == null)
/* 141 */       return null;
/* 142 */     InputStream is = new URL(url).openStream();
/* 143 */     CertificateFactory cf = CertificateFactory.getInstance("X.509");
/* 144 */     return cf.generateCRL(is);
/*     */   }
/*     */ 
/*     */   public static String getOCSPURL(X509Certificate certificate)
/*     */   {
/*     */     try
/*     */     {
/* 158 */       ASN1Primitive obj = getExtensionValue(certificate, Extension.authorityInfoAccess.getId());
/* 159 */       if (obj == null) {
/* 160 */         return null;
/*     */       }
/* 162 */       ASN1Sequence AccessDescriptions = (ASN1Sequence)obj;
/* 163 */       for (int i = 0; i < AccessDescriptions.size(); i++) {
/* 164 */         ASN1Sequence AccessDescription = (ASN1Sequence)AccessDescriptions.getObjectAt(i);
/* 165 */         if (AccessDescription.size() == 2)
/*     */         {
/* 168 */           if ((AccessDescription.getObjectAt(0) instanceof ASN1ObjectIdentifier)) {
/* 169 */             ASN1ObjectIdentifier id = (ASN1ObjectIdentifier)AccessDescription.getObjectAt(0);
/* 170 */             if ("1.3.6.1.5.5.7.48.1".equals(id.getId())) {
/* 171 */               ASN1Primitive description = (ASN1Primitive)AccessDescription.getObjectAt(1);
/* 172 */               String AccessLocation = getStringFromGeneralName(description);
/* 173 */               if (AccessLocation == null) {
/* 174 */                 return "";
/*     */               }
/*     */ 
/* 177 */               return AccessLocation;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     } catch (IOException e) {
/* 183 */       return null;
/*     */     }
/* 185 */     return null;
/*     */   }
/*     */ 
/*     */   public static String getTSAURL(X509Certificate certificate)
/*     */   {
/* 197 */     byte[] der = certificate.getExtensionValue("1.2.840.113583.1.1.9.1");
/* 198 */     if (der == null)
/* 199 */       return null;
/*     */     try
/*     */     {
/* 202 */       ASN1Primitive asn1obj = ASN1Primitive.fromByteArray(der);
/* 203 */       DEROctetString octets = (DEROctetString)asn1obj;
/* 204 */       asn1obj = ASN1Primitive.fromByteArray(octets.getOctets());
/* 205 */       ASN1Sequence asn1seq = ASN1Sequence.getInstance(asn1obj);
/* 206 */       return getStringFromGeneralName(asn1seq.getObjectAt(1).toASN1Primitive()); } catch (IOException e) {
/*     */     }
/* 208 */     return null;
/*     */   }
/*     */ 
/*     */   private static ASN1Primitive getExtensionValue(X509Certificate certificate, String oid)
/*     */     throws IOException
/*     */   {
/* 221 */     byte[] bytes = certificate.getExtensionValue(oid);
/* 222 */     if (bytes == null) {
/* 223 */       return null;
/*     */     }
/* 225 */     ASN1InputStream aIn = new ASN1InputStream(new ByteArrayInputStream(bytes));
/* 226 */     ASN1OctetString octs = (ASN1OctetString)aIn.readObject();
/* 227 */     aIn = new ASN1InputStream(new ByteArrayInputStream(octs.getOctets()));
/* 228 */     return aIn.readObject();
/*     */   }
/*     */ 
/*     */   private static String getStringFromGeneralName(ASN1Primitive names)
/*     */     throws IOException
/*     */   {
/* 238 */     ASN1TaggedObject taggedObject = (ASN1TaggedObject)names;
/* 239 */     return new String(ASN1OctetString.getInstance(taggedObject, false).getOctets(), "ISO-8859-1");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.CertificateUtil
 * JD-Core Version:    0.6.2
 */