/*     */ package com.itextpdf.text.pdf.security;
/*     */ 
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.io.RASInputStream;
/*     */ import com.itextpdf.text.io.RandomAccessSource;
/*     */ import com.itextpdf.text.io.RandomAccessSourceFactory;
/*     */ import com.itextpdf.text.io.StreamUtil;
/*     */ import com.itextpdf.text.log.Logger;
/*     */ import com.itextpdf.text.log.LoggerFactory;
/*     */ import com.itextpdf.text.pdf.AcroFields;
/*     */ import com.itextpdf.text.pdf.ByteBuffer;
/*     */ import com.itextpdf.text.pdf.PdfArray;
/*     */ import com.itextpdf.text.pdf.PdfDate;
/*     */ import com.itextpdf.text.pdf.PdfDeveloperExtension;
/*     */ import com.itextpdf.text.pdf.PdfDictionary;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfReader;
/*     */ import com.itextpdf.text.pdf.PdfSignature;
/*     */ import com.itextpdf.text.pdf.PdfSignatureAppearance;
/*     */ import com.itextpdf.text.pdf.PdfString;
/*     */ import com.itextpdf.text.pdf.RandomAccessFileOrArray;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class MakeSignature
/*     */ {
/*  85 */   private static final Logger LOGGER = LoggerFactory.getLogger(MakeSignature.class);
/*     */ 
/*     */   public static void signDetached(PdfSignatureAppearance sap, ExternalDigest externalDigest, ExternalSignature externalSignature, Certificate[] chain, Collection<CrlClient> crlList, OcspClient ocspClient, TSAClient tsaClient, int estimatedSize, CryptoStandard sigtype)
/*     */     throws IOException, DocumentException, GeneralSecurityException
/*     */   {
/* 110 */     Collection crlBytes = null;
/* 111 */     int i = 0;
/* 112 */     while ((crlBytes == null) && (i < chain.length))
/* 113 */       crlBytes = processCrl(chain[(i++)], crlList);
/* 114 */     if (estimatedSize == 0) {
/* 115 */       estimatedSize = 8192;
/* 116 */       if (crlBytes != null) {
/* 117 */         for (byte[] element : crlBytes) {
/* 118 */           estimatedSize += element.length + 10;
/*     */         }
/*     */       }
/* 121 */       if (ocspClient != null)
/* 122 */         estimatedSize += 4192;
/* 123 */       if (tsaClient != null)
/* 124 */         estimatedSize += 4192;
/*     */     }
/* 126 */     sap.setCertificate(chain[0]);
/* 127 */     if (sigtype == CryptoStandard.CADES) {
/* 128 */       sap.addDeveloperExtension(PdfDeveloperExtension.ESIC_1_7_EXTENSIONLEVEL2);
/*     */     }
/* 130 */     PdfSignature dic = new PdfSignature(PdfName.ADOBE_PPKLITE, sigtype == CryptoStandard.CADES ? PdfName.ETSI_CADES_DETACHED : PdfName.ADBE_PKCS7_DETACHED);
/* 131 */     dic.setReason(sap.getReason());
/* 132 */     dic.setLocation(sap.getLocation());
/* 133 */     dic.setSignatureCreator(sap.getSignatureCreator());
/* 134 */     dic.setContact(sap.getContact());
/* 135 */     dic.setDate(new PdfDate(sap.getSignDate()));
/* 136 */     sap.setCryptoDictionary(dic);
/*     */ 
/* 138 */     HashMap exc = new HashMap();
/* 139 */     exc.put(PdfName.CONTENTS, new Integer(estimatedSize * 2 + 2));
/* 140 */     sap.preClose(exc);
/*     */ 
/* 142 */     String hashAlgorithm = externalSignature.getHashAlgorithm();
/* 143 */     PdfPKCS7 sgn = new PdfPKCS7(null, chain, hashAlgorithm, null, externalDigest, false);
/* 144 */     InputStream data = sap.getRangeStream();
/* 145 */     byte[] hash = DigestAlgorithms.digest(data, externalDigest.getMessageDigest(hashAlgorithm));
/* 146 */     Calendar cal = Calendar.getInstance();
/* 147 */     byte[] ocsp = null;
/* 148 */     if ((chain.length >= 2) && (ocspClient != null)) {
/* 149 */       ocsp = ocspClient.getEncoded((X509Certificate)chain[0], (X509Certificate)chain[1], null);
/*     */     }
/* 151 */     byte[] sh = sgn.getAuthenticatedAttributeBytes(hash, cal, ocsp, crlBytes, sigtype);
/* 152 */     byte[] extSignature = externalSignature.sign(sh);
/* 153 */     sgn.setExternalDigest(extSignature, null, externalSignature.getEncryptionAlgorithm());
/*     */ 
/* 155 */     byte[] encodedSig = sgn.getEncodedPKCS7(hash, cal, tsaClient, ocsp, crlBytes, sigtype);
/*     */ 
/* 157 */     if (estimatedSize < encodedSig.length) {
/* 158 */       throw new IOException("Not enough space");
/*     */     }
/* 160 */     byte[] paddedSig = new byte[estimatedSize];
/* 161 */     System.arraycopy(encodedSig, 0, paddedSig, 0, encodedSig.length);
/*     */ 
/* 163 */     PdfDictionary dic2 = new PdfDictionary();
/* 164 */     dic2.put(PdfName.CONTENTS, new PdfString(paddedSig).setHexWriting(true));
/* 165 */     sap.close(dic2);
/*     */   }
/*     */ 
/*     */   public static Collection<byte[]> processCrl(Certificate cert, Collection<CrlClient> crlList)
/*     */   {
/* 175 */     if (crlList == null)
/* 176 */       return null;
/* 177 */     ArrayList crlBytes = new ArrayList();
/* 178 */     for (CrlClient cc : crlList)
/* 179 */       if (cc != null)
/*     */       {
/* 181 */         LOGGER.info("Processing " + cc.getClass().getName());
/* 182 */         Collection b = cc.getEncoded((X509Certificate)cert, null);
/* 183 */         if (b != null)
/*     */         {
/* 185 */           crlBytes.addAll(b);
/*     */         }
/*     */       }
/* 187 */     if (crlBytes.isEmpty()) {
/* 188 */       return null;
/*     */     }
/* 190 */     return crlBytes;
/*     */   }
/*     */ 
/*     */   public static void signExternalContainer(PdfSignatureAppearance sap, ExternalSignatureContainer externalSignatureContainer, int estimatedSize)
/*     */     throws GeneralSecurityException, IOException, DocumentException
/*     */   {
/* 204 */     PdfSignature dic = new PdfSignature(null, null);
/* 205 */     dic.setReason(sap.getReason());
/* 206 */     dic.setLocation(sap.getLocation());
/* 207 */     dic.setSignatureCreator(sap.getSignatureCreator());
/* 208 */     dic.setContact(sap.getContact());
/* 209 */     dic.setDate(new PdfDate(sap.getSignDate()));
/* 210 */     externalSignatureContainer.modifySigningDictionary(dic);
/* 211 */     sap.setCryptoDictionary(dic);
/*     */ 
/* 213 */     HashMap exc = new HashMap();
/* 214 */     exc.put(PdfName.CONTENTS, new Integer(estimatedSize * 2 + 2));
/* 215 */     sap.preClose(exc);
/*     */ 
/* 217 */     InputStream data = sap.getRangeStream();
/* 218 */     byte[] encodedSig = externalSignatureContainer.sign(data);
/*     */ 
/* 220 */     if (estimatedSize < encodedSig.length) {
/* 221 */       throw new IOException("Not enough space");
/*     */     }
/* 223 */     byte[] paddedSig = new byte[estimatedSize];
/* 224 */     System.arraycopy(encodedSig, 0, paddedSig, 0, encodedSig.length);
/*     */ 
/* 226 */     PdfDictionary dic2 = new PdfDictionary();
/* 227 */     dic2.put(PdfName.CONTENTS, new PdfString(paddedSig).setHexWriting(true));
/* 228 */     sap.close(dic2);
/*     */   }
/*     */ 
/*     */   public static void signDeferred(PdfReader reader, String fieldName, OutputStream outs, ExternalSignatureContainer externalSignatureContainer)
/*     */     throws DocumentException, IOException, GeneralSecurityException
/*     */   {
/* 243 */     AcroFields af = reader.getAcroFields();
/* 244 */     PdfDictionary v = af.getSignatureDictionary(fieldName);
/* 245 */     if (v == null)
/* 246 */       throw new DocumentException("No field");
/* 247 */     if (!af.signatureCoversWholeDocument(fieldName))
/* 248 */       throw new DocumentException("Not the last signature");
/* 249 */     PdfArray b = v.getAsArray(PdfName.BYTERANGE);
/* 250 */     long[] gaps = b.asLongArray();
/* 251 */     if ((b.size() != 4) || (gaps[0] != 0L))
/* 252 */       throw new DocumentException("Single exclusion space supported");
/* 253 */     RandomAccessSource readerSource = reader.getSafeFile().createSourceView();
/* 254 */     InputStream rg = new RASInputStream(new RandomAccessSourceFactory().createRanged(readerSource, gaps));
/* 255 */     byte[] signedContent = externalSignatureContainer.sign(rg);
/* 256 */     int spaceAvailable = (int)(gaps[2] - gaps[1]) - 2;
/* 257 */     if ((spaceAvailable & 0x1) != 0)
/* 258 */       throw new DocumentException("Gap is not a multiple of 2");
/* 259 */     spaceAvailable /= 2;
/* 260 */     if (spaceAvailable < signedContent.length)
/* 261 */       throw new DocumentException("Not enough space");
/* 262 */     StreamUtil.CopyBytes(readerSource, 0L, gaps[1] + 1L, outs);
/* 263 */     ByteBuffer bb = new ByteBuffer(spaceAvailable * 2);
/* 264 */     for (byte bi : signedContent) {
/* 265 */       bb.appendHex(bi);
/*     */     }
/* 267 */     int remain = (spaceAvailable - signedContent.length) * 2;
/* 268 */     for (int k = 0; k < remain; k++) {
/* 269 */       bb.append((byte)48);
/*     */     }
/* 271 */     bb.writeTo(outs);
/* 272 */     StreamUtil.CopyBytes(readerSource, gaps[2] - 1L, gaps[3] + 1L, outs);
/*     */   }
/*     */ 
/*     */   public static enum CryptoStandard
/*     */   {
/*  88 */     CMS, CADES;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.MakeSignature
 * JD-Core Version:    0.6.2
 */