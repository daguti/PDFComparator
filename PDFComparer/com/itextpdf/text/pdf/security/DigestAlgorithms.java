/*     */ package com.itextpdf.text.pdf.security;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class DigestAlgorithms
/*     */ {
/*     */   public static final String SHA1 = "SHA-1";
/*     */   public static final String SHA256 = "SHA-256";
/*     */   public static final String SHA384 = "SHA-384";
/*     */   public static final String SHA512 = "SHA-512";
/*     */   public static final String RIPEMD160 = "RIPEMD160";
/*  76 */   private static final HashMap<String, String> digestNames = new HashMap();
/*     */ 
/*  79 */   private static final HashMap<String, String> fixNames = new HashMap();
/*     */ 
/*  82 */   private static final HashMap<String, String> allowedDigests = new HashMap();
/*     */ 
/*     */   public static MessageDigest getMessageDigestFromOid(String digestOid, String provider)
/*     */     throws NoSuchAlgorithmException, NoSuchProviderException
/*     */   {
/* 143 */     return getMessageDigest(getDigest(digestOid), provider);
/*     */   }
/*     */ 
/*     */   public static MessageDigest getMessageDigest(String hashAlgorithm, String provider)
/*     */     throws NoSuchAlgorithmException, NoSuchProviderException
/*     */   {
/* 157 */     if ((provider == null) || (provider.startsWith("SunPKCS11")) || (provider.startsWith("SunMSCAPI"))) {
/* 158 */       return MessageDigest.getInstance(normalizeDigestName(hashAlgorithm));
/*     */     }
/* 160 */     return MessageDigest.getInstance(hashAlgorithm, provider);
/*     */   }
/*     */ 
/*     */   public static byte[] digest(InputStream data, String hashAlgorithm, String provider)
/*     */     throws GeneralSecurityException, IOException
/*     */   {
/* 175 */     MessageDigest messageDigest = getMessageDigest(hashAlgorithm, provider);
/* 176 */     return digest(data, messageDigest);
/*     */   }
/*     */ 
/*     */   public static byte[] digest(InputStream data, MessageDigest messageDigest) throws GeneralSecurityException, IOException
/*     */   {
/* 181 */     byte[] buf = new byte[8192];
/*     */     int n;
/* 183 */     while ((n = data.read(buf)) > 0) {
/* 184 */       messageDigest.update(buf, 0, n);
/*     */     }
/* 186 */     return messageDigest.digest();
/*     */   }
/*     */ 
/*     */   public static String getDigest(String oid)
/*     */   {
/* 195 */     String ret = (String)digestNames.get(oid);
/* 196 */     if (ret == null) {
/* 197 */       return oid;
/*     */     }
/* 199 */     return ret;
/*     */   }
/*     */ 
/*     */   public static String normalizeDigestName(String algo) {
/* 203 */     if (fixNames.containsKey(algo))
/* 204 */       return (String)fixNames.get(algo);
/* 205 */     return algo;
/*     */   }
/*     */ 
/*     */   public static String getAllowedDigests(String name)
/*     */   {
/* 215 */     return (String)allowedDigests.get(name.toUpperCase());
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  85 */     digestNames.put("1.2.840.113549.2.5", "MD5");
/*  86 */     digestNames.put("1.2.840.113549.2.2", "MD2");
/*  87 */     digestNames.put("1.3.14.3.2.26", "SHA1");
/*  88 */     digestNames.put("2.16.840.1.101.3.4.2.4", "SHA224");
/*  89 */     digestNames.put("2.16.840.1.101.3.4.2.1", "SHA256");
/*  90 */     digestNames.put("2.16.840.1.101.3.4.2.2", "SHA384");
/*  91 */     digestNames.put("2.16.840.1.101.3.4.2.3", "SHA512");
/*  92 */     digestNames.put("1.3.36.3.2.2", "RIPEMD128");
/*  93 */     digestNames.put("1.3.36.3.2.1", "RIPEMD160");
/*  94 */     digestNames.put("1.3.36.3.2.3", "RIPEMD256");
/*  95 */     digestNames.put("1.2.840.113549.1.1.4", "MD5");
/*  96 */     digestNames.put("1.2.840.113549.1.1.2", "MD2");
/*  97 */     digestNames.put("1.2.840.113549.1.1.5", "SHA1");
/*  98 */     digestNames.put("1.2.840.113549.1.1.14", "SHA224");
/*  99 */     digestNames.put("1.2.840.113549.1.1.11", "SHA256");
/* 100 */     digestNames.put("1.2.840.113549.1.1.12", "SHA384");
/* 101 */     digestNames.put("1.2.840.113549.1.1.13", "SHA512");
/* 102 */     digestNames.put("1.2.840.113549.2.5", "MD5");
/* 103 */     digestNames.put("1.2.840.113549.2.2", "MD2");
/* 104 */     digestNames.put("1.2.840.10040.4.3", "SHA1");
/* 105 */     digestNames.put("2.16.840.1.101.3.4.3.1", "SHA224");
/* 106 */     digestNames.put("2.16.840.1.101.3.4.3.2", "SHA256");
/* 107 */     digestNames.put("2.16.840.1.101.3.4.3.3", "SHA384");
/* 108 */     digestNames.put("2.16.840.1.101.3.4.3.4", "SHA512");
/* 109 */     digestNames.put("1.3.36.3.3.1.3", "RIPEMD128");
/* 110 */     digestNames.put("1.3.36.3.3.1.2", "RIPEMD160");
/* 111 */     digestNames.put("1.3.36.3.3.1.4", "RIPEMD256");
/* 112 */     digestNames.put("1.2.643.2.2.9", "GOST3411");
/*     */ 
/* 114 */     fixNames.put("SHA256", "SHA-256");
/* 115 */     fixNames.put("SHA384", "SHA-384");
/* 116 */     fixNames.put("SHA512", "SHA-512");
/*     */ 
/* 118 */     allowedDigests.put("MD2", "1.2.840.113549.2.2");
/* 119 */     allowedDigests.put("MD-2", "1.2.840.113549.2.2");
/* 120 */     allowedDigests.put("MD5", "1.2.840.113549.2.5");
/* 121 */     allowedDigests.put("MD-5", "1.2.840.113549.2.5");
/* 122 */     allowedDigests.put("SHA1", "1.3.14.3.2.26");
/* 123 */     allowedDigests.put("SHA-1", "1.3.14.3.2.26");
/* 124 */     allowedDigests.put("SHA224", "2.16.840.1.101.3.4.2.4");
/* 125 */     allowedDigests.put("SHA-224", "2.16.840.1.101.3.4.2.4");
/* 126 */     allowedDigests.put("SHA256", "2.16.840.1.101.3.4.2.1");
/* 127 */     allowedDigests.put("SHA-256", "2.16.840.1.101.3.4.2.1");
/* 128 */     allowedDigests.put("SHA384", "2.16.840.1.101.3.4.2.2");
/* 129 */     allowedDigests.put("SHA-384", "2.16.840.1.101.3.4.2.2");
/* 130 */     allowedDigests.put("SHA512", "2.16.840.1.101.3.4.2.3");
/* 131 */     allowedDigests.put("SHA-512", "2.16.840.1.101.3.4.2.3");
/* 132 */     allowedDigests.put("RIPEMD128", "1.3.36.3.2.2");
/* 133 */     allowedDigests.put("RIPEMD-128", "1.3.36.3.2.2");
/* 134 */     allowedDigests.put("RIPEMD160", "1.3.36.3.2.1");
/* 135 */     allowedDigests.put("RIPEMD-160", "1.3.36.3.2.1");
/* 136 */     allowedDigests.put("RIPEMD256", "1.3.36.3.2.3");
/* 137 */     allowedDigests.put("RIPEMD-256", "1.3.36.3.2.3");
/* 138 */     allowedDigests.put("GOST3411", "1.2.643.2.2.9");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.DigestAlgorithms
 * JD-Core Version:    0.6.2
 */