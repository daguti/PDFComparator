/*     */ package com.itextpdf.text.pdf.security;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.log.Logger;
/*     */ import com.itextpdf.text.log.LoggerFactory;
/*     */ import com.itextpdf.text.pdf.codec.Base64;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.math.BigInteger;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.MessageDigest;
/*     */ import org.bouncycastle.asn1.ASN1ObjectIdentifier;
/*     */ import org.bouncycastle.asn1.cmp.PKIFailureInfo;
/*     */ import org.bouncycastle.tsp.TSPException;
/*     */ import org.bouncycastle.tsp.TimeStampRequest;
/*     */ import org.bouncycastle.tsp.TimeStampRequestGenerator;
/*     */ import org.bouncycastle.tsp.TimeStampResponse;
/*     */ import org.bouncycastle.tsp.TimeStampToken;
/*     */ import org.bouncycastle.tsp.TimeStampTokenInfo;
/*     */ 
/*     */ public class TSAClientBouncyCastle
/*     */   implements TSAClient
/*     */ {
/*  84 */   private static final Logger LOGGER = LoggerFactory.getLogger(TSAClientBouncyCastle.class);
/*     */   protected String tsaURL;
/*     */   protected String tsaUsername;
/*     */   protected String tsaPassword;
/*     */   protected TSAInfoBouncyCastle tsaInfo;
/*     */   public static final int DEFAULTTOKENSIZE = 4096;
/*     */   protected int tokenSizeEstimate;
/*     */   public static final String DEFAULTHASHALGORITHM = "SHA-256";
/*     */   protected String digestAlgorithm;
/*     */ 
/*     */   public TSAClientBouncyCastle(String url)
/*     */   {
/* 115 */     this(url, null, null, 4096, "SHA-256");
/*     */   }
/*     */ 
/*     */   public TSAClientBouncyCastle(String url, String username, String password)
/*     */   {
/* 125 */     this(url, username, password, 4096, "SHA-256");
/*     */   }
/*     */ 
/*     */   public TSAClientBouncyCastle(String url, String username, String password, int tokSzEstimate, String digestAlgorithm)
/*     */   {
/* 139 */     this.tsaURL = url;
/* 140 */     this.tsaUsername = username;
/* 141 */     this.tsaPassword = password;
/* 142 */     this.tokenSizeEstimate = tokSzEstimate;
/* 143 */     this.digestAlgorithm = digestAlgorithm;
/*     */   }
/*     */ 
/*     */   public void setTSAInfo(TSAInfoBouncyCastle tsaInfo)
/*     */   {
/* 150 */     this.tsaInfo = tsaInfo;
/*     */   }
/*     */ 
/*     */   public int getTokenSizeEstimate()
/*     */   {
/* 159 */     return this.tokenSizeEstimate;
/*     */   }
/*     */ 
/*     */   public MessageDigest getMessageDigest()
/*     */     throws GeneralSecurityException
/*     */   {
/* 167 */     return new BouncyCastleDigest().getMessageDigest(this.digestAlgorithm);
/*     */   }
/*     */ 
/*     */   public byte[] getTimeStampToken(byte[] imprint)
/*     */     throws IOException, TSPException
/*     */   {
/* 179 */     byte[] respBytes = null;
/*     */ 
/* 181 */     TimeStampRequestGenerator tsqGenerator = new TimeStampRequestGenerator();
/* 182 */     tsqGenerator.setCertReq(true);
/*     */ 
/* 184 */     BigInteger nonce = BigInteger.valueOf(System.currentTimeMillis());
/* 185 */     TimeStampRequest request = tsqGenerator.generate(new ASN1ObjectIdentifier(DigestAlgorithms.getAllowedDigests(this.digestAlgorithm)), imprint, nonce);
/* 186 */     byte[] requestBytes = request.getEncoded();
/*     */ 
/* 189 */     respBytes = getTSAResponse(requestBytes);
/*     */ 
/* 192 */     TimeStampResponse response = new TimeStampResponse(respBytes);
/*     */ 
/* 195 */     response.validate(request);
/* 196 */     PKIFailureInfo failure = response.getFailInfo();
/* 197 */     int value = failure == null ? 0 : failure.intValue();
/* 198 */     if (value != 0)
/*     */     {
/* 200 */       throw new IOException(MessageLocalization.getComposedMessage("invalid.tsa.1.response.code.2", new Object[] { this.tsaURL, String.valueOf(value) }));
/*     */     }
/*     */ 
/* 206 */     TimeStampToken tsToken = response.getTimeStampToken();
/* 207 */     if (tsToken == null) {
/* 208 */       throw new IOException(MessageLocalization.getComposedMessage("tsa.1.failed.to.return.time.stamp.token.2", new Object[] { this.tsaURL, response.getStatusString() }));
/*     */     }
/* 210 */     TimeStampTokenInfo tsTokenInfo = tsToken.getTimeStampInfo();
/* 211 */     byte[] encoded = tsToken.getEncoded();
/*     */ 
/* 213 */     LOGGER.info("Timestamp generated: " + tsTokenInfo.getGenTime());
/* 214 */     if (this.tsaInfo != null) {
/* 215 */       this.tsaInfo.inspectTimeStampTokenInfo(tsTokenInfo);
/*     */     }
/*     */ 
/* 218 */     this.tokenSizeEstimate = (encoded.length + 32);
/* 219 */     return encoded;
/*     */   }
/*     */ 
/*     */   protected byte[] getTSAResponse(byte[] requestBytes)
/*     */     throws IOException
/*     */   {
/* 229 */     URL url = new URL(this.tsaURL);
/*     */     URLConnection tsaConnection;
/*     */     try
/*     */     {
/* 232 */       tsaConnection = url.openConnection();
/*     */     }
/*     */     catch (IOException ioe) {
/* 235 */       throw new IOException(MessageLocalization.getComposedMessage("failed.to.get.tsa.response.from.1", new Object[] { this.tsaURL }));
/*     */     }
/* 237 */     tsaConnection.setDoInput(true);
/* 238 */     tsaConnection.setDoOutput(true);
/* 239 */     tsaConnection.setUseCaches(false);
/* 240 */     tsaConnection.setRequestProperty("Content-Type", "application/timestamp-query");
/*     */ 
/* 242 */     tsaConnection.setRequestProperty("Content-Transfer-Encoding", "binary");
/*     */ 
/* 244 */     if ((this.tsaUsername != null) && (!this.tsaUsername.equals(""))) {
/* 245 */       String userPassword = this.tsaUsername + ":" + this.tsaPassword;
/* 246 */       tsaConnection.setRequestProperty("Authorization", "Basic " + Base64.encodeBytes(userPassword.getBytes(), 8));
/*     */     }
/*     */ 
/* 249 */     OutputStream out = tsaConnection.getOutputStream();
/* 250 */     out.write(requestBytes);
/* 251 */     out.close();
/*     */ 
/* 254 */     InputStream inp = tsaConnection.getInputStream();
/* 255 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 256 */     byte[] buffer = new byte[1024];
/* 257 */     int bytesRead = 0;
/* 258 */     while ((bytesRead = inp.read(buffer, 0, buffer.length)) >= 0) {
/* 259 */       baos.write(buffer, 0, bytesRead);
/*     */     }
/* 261 */     byte[] respBytes = baos.toByteArray();
/*     */ 
/* 263 */     String encoding = tsaConnection.getContentEncoding();
/* 264 */     if ((encoding != null) && (encoding.equalsIgnoreCase("base64"))) {
/* 265 */       respBytes = Base64.decode(new String(respBytes));
/*     */     }
/* 267 */     return respBytes;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.TSAClientBouncyCastle
 * JD-Core Version:    0.6.2
 */