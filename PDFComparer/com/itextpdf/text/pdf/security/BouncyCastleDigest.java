/*    */ package com.itextpdf.text.pdf.security;
/*    */ 
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.security.MessageDigest;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ import org.bouncycastle.jcajce.provider.digest.GOST3411.Digest;
/*    */ import org.bouncycastle.jcajce.provider.digest.MD2.Digest;
/*    */ import org.bouncycastle.jcajce.provider.digest.MD5.Digest;
/*    */ import org.bouncycastle.jcajce.provider.digest.RIPEMD128.Digest;
/*    */ import org.bouncycastle.jcajce.provider.digest.RIPEMD160.Digest;
/*    */ import org.bouncycastle.jcajce.provider.digest.RIPEMD256.Digest;
/*    */ import org.bouncycastle.jcajce.provider.digest.SHA1.Digest;
/*    */ import org.bouncycastle.jcajce.provider.digest.SHA224.Digest;
/*    */ import org.bouncycastle.jcajce.provider.digest.SHA256.Digest;
/*    */ import org.bouncycastle.jcajce.provider.digest.SHA384.Digest;
/*    */ import org.bouncycastle.jcajce.provider.digest.SHA512.Digest;
/*    */ 
/*    */ public class BouncyCastleDigest
/*    */   implements ExternalDigest
/*    */ {
/*    */   public MessageDigest getMessageDigest(String hashAlgorithm)
/*    */     throws GeneralSecurityException
/*    */   {
/* 59 */     String oid = DigestAlgorithms.getAllowedDigests(hashAlgorithm);
/* 60 */     if (oid == null)
/* 61 */       throw new NoSuchAlgorithmException(hashAlgorithm);
/* 62 */     if (oid.equals("1.2.840.113549.2.2")) {
/* 63 */       return new MD2.Digest();
/*    */     }
/* 65 */     if (oid.equals("1.2.840.113549.2.5")) {
/* 66 */       return new MD5.Digest();
/*    */     }
/* 68 */     if (oid.equals("1.3.14.3.2.26")) {
/* 69 */       return new SHA1.Digest();
/*    */     }
/* 71 */     if (oid.equals("2.16.840.1.101.3.4.2.4")) {
/* 72 */       return new SHA224.Digest();
/*    */     }
/* 74 */     if (oid.equals("2.16.840.1.101.3.4.2.1")) {
/* 75 */       return new SHA256.Digest();
/*    */     }
/* 77 */     if (oid.equals("2.16.840.1.101.3.4.2.2")) {
/* 78 */       return new SHA384.Digest();
/*    */     }
/* 80 */     if (oid.equals("2.16.840.1.101.3.4.2.3")) {
/* 81 */       return new SHA512.Digest();
/*    */     }
/* 83 */     if (oid.equals("1.3.36.3.2.2")) {
/* 84 */       return new RIPEMD128.Digest();
/*    */     }
/* 86 */     if (oid.equals("1.3.36.3.2.1")) {
/* 87 */       return new RIPEMD160.Digest();
/*    */     }
/* 89 */     if (oid.equals("1.3.36.3.2.3")) {
/* 90 */       return new RIPEMD256.Digest();
/*    */     }
/* 92 */     if (oid.equals("1.2.643.2.2.9")) {
/* 93 */       return new GOST3411.Digest();
/*    */     }
/* 95 */     throw new NoSuchAlgorithmException(hashAlgorithm);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.BouncyCastleDigest
 * JD-Core Version:    0.6.2
 */