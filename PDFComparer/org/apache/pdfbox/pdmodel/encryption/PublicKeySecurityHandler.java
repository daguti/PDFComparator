/*     */ package org.apache.pdfbox.pdmodel.encryption;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.security.AlgorithmParameterGenerator;
/*     */ import java.security.AlgorithmParameters;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.Security;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.KeyGenerator;
/*     */ import javax.crypto.SecretKey;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ import org.apache.pdfbox.exceptions.CryptographyException;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.bouncycastle.asn1.ASN1InputStream;
/*     */ import org.bouncycastle.asn1.DERInteger;
/*     */ import org.bouncycastle.asn1.DERObject;
/*     */ import org.bouncycastle.asn1.DERObjectIdentifier;
/*     */ import org.bouncycastle.asn1.DEROctetString;
/*     */ import org.bouncycastle.asn1.DEROutputStream;
/*     */ import org.bouncycastle.asn1.DERSet;
/*     */ import org.bouncycastle.asn1.cms.ContentInfo;
/*     */ import org.bouncycastle.asn1.cms.EncryptedContentInfo;
/*     */ import org.bouncycastle.asn1.cms.EnvelopedData;
/*     */ import org.bouncycastle.asn1.cms.IssuerAndSerialNumber;
/*     */ import org.bouncycastle.asn1.cms.KeyTransRecipientInfo;
/*     */ import org.bouncycastle.asn1.cms.RecipientIdentifier;
/*     */ import org.bouncycastle.asn1.cms.RecipientInfo;
/*     */ import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
/*     */ import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
/*     */ import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
/*     */ import org.bouncycastle.asn1.x509.TBSCertificateStructure;
/*     */ import org.bouncycastle.cms.CMSEnvelopedData;
/*     */ import org.bouncycastle.cms.CMSException;
/*     */ import org.bouncycastle.cms.RecipientId;
/*     */ import org.bouncycastle.cms.RecipientInformation;
/*     */ import org.bouncycastle.cms.RecipientInformationStore;
/*     */ import org.bouncycastle.jce.provider.BouncyCastleProvider;
/*     */ 
/*     */ public class PublicKeySecurityHandler extends SecurityHandler
/*     */ {
/*  83 */   private static final Log LOG = LogFactory.getLog(PublicKeySecurityHandler.class);
/*     */   public static final String FILTER = "Adobe.PubSec";
/*     */   private static final String SUBFILTER = "adbe.pkcs7.s4";
/*  92 */   private PublicKeyProtectionPolicy policy = null;
/*     */ 
/*     */   public PublicKeySecurityHandler()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PublicKeySecurityHandler(PublicKeyProtectionPolicy p)
/*     */   {
/* 108 */     this.policy = p;
/* 109 */     this.keyLength = this.policy.getEncryptionKeyLength();
/*     */   }
/*     */ 
/*     */   public void decryptDocument(PDDocument doc, DecryptionMaterial decryptionMaterial)
/*     */     throws CryptographyException, IOException
/*     */   {
/* 124 */     this.document = doc;
/*     */ 
/* 126 */     PDEncryptionDictionary dictionary = doc.getEncryptionDictionary();
/*     */ 
/* 128 */     prepareForDecryption(dictionary, doc.getDocument().getDocumentID(), decryptionMaterial);
/*     */ 
/* 131 */     proceedDecryption();
/*     */   }
/*     */ 
/*     */   public void prepareForDecryption(PDEncryptionDictionary encDictionary, COSArray documentIDArray, DecryptionMaterial decryptionMaterial)
/*     */     throws CryptographyException, IOException
/*     */   {
/* 150 */     if (!(decryptionMaterial instanceof PublicKeyDecryptionMaterial))
/*     */     {
/* 152 */       throw new CryptographyException("Provided decryption material is not compatible with the document");
/*     */     }
/*     */ 
/* 155 */     this.decryptMetadata = encDictionary.isEncryptMetaData();
/* 156 */     if (encDictionary.getLength() != 0)
/*     */     {
/* 158 */       this.keyLength = encDictionary.getLength();
/*     */     }
/*     */ 
/* 161 */     PublicKeyDecryptionMaterial material = (PublicKeyDecryptionMaterial)decryptionMaterial;
/*     */     try
/*     */     {
/* 165 */       boolean foundRecipient = false;
/*     */ 
/* 169 */       byte[] envelopedData = null;
/*     */ 
/* 172 */       byte[][] recipientFieldsBytes = new byte[encDictionary.getRecipientsLength()][];
/*     */ 
/* 174 */       int recipientFieldsLength = 0;
/*     */ 
/* 176 */       for (int i = 0; i < encDictionary.getRecipientsLength(); i++)
/*     */       {
/* 178 */         COSString recipientFieldString = encDictionary.getRecipientStringAt(i);
/* 179 */         byte[] recipientBytes = recipientFieldString.getBytes();
/* 180 */         CMSEnvelopedData data = new CMSEnvelopedData(recipientBytes);
/* 181 */         Iterator recipCertificatesIt = data.getRecipientInfos().getRecipients().iterator();
/* 182 */         while (recipCertificatesIt.hasNext())
/*     */         {
/* 184 */           RecipientInformation ri = (RecipientInformation)recipCertificatesIt.next();
/*     */ 
/* 187 */           if ((ri.getRID().match(material.getCertificate())) && (!foundRecipient))
/*     */           {
/* 189 */             foundRecipient = true;
/* 190 */             envelopedData = ri.getContent(material.getPrivateKey(), "BC");
/* 191 */             break;
/*     */           }
/*     */         }
/* 194 */         recipientFieldsBytes[i] = recipientBytes;
/* 195 */         recipientFieldsLength += recipientBytes.length;
/*     */       }
/* 197 */       if ((!foundRecipient) || (envelopedData == null))
/*     */       {
/* 199 */         throw new CryptographyException("The certificate matches no recipient entry");
/*     */       }
/* 201 */       if (envelopedData.length != 24)
/*     */       {
/* 203 */         throw new CryptographyException("The enveloped data does not contain 24 bytes");
/*     */       }
/*     */ 
/* 209 */       byte[] accessBytes = new byte[4];
/* 210 */       System.arraycopy(envelopedData, 20, accessBytes, 0, 4);
/*     */ 
/* 212 */       this.currentAccessPermission = new AccessPermission(accessBytes);
/* 213 */       this.currentAccessPermission.setReadOnly();
/*     */ 
/* 216 */       byte[] sha1Input = new byte[recipientFieldsLength + 20];
/*     */ 
/* 219 */       System.arraycopy(envelopedData, 0, sha1Input, 0, 20);
/*     */ 
/* 222 */       int sha1InputOffset = 20;
/* 223 */       for (int i = 0; i < recipientFieldsBytes.length; i++)
/*     */       {
/* 225 */         System.arraycopy(recipientFieldsBytes[i], 0, sha1Input, sha1InputOffset, recipientFieldsBytes[i].length);
/* 226 */         sha1InputOffset += recipientFieldsBytes[i].length;
/*     */       }
/*     */ 
/* 229 */       MessageDigest md = MessageDigest.getInstance("SHA-1");
/* 230 */       byte[] mdResult = md.digest(sha1Input);
/*     */ 
/* 233 */       this.encryptionKey = new byte[this.keyLength / 8];
/* 234 */       System.arraycopy(mdResult, 0, this.encryptionKey, 0, this.keyLength / 8);
/*     */     }
/*     */     catch (CMSException e)
/*     */     {
/* 238 */       throw new CryptographyException(e);
/*     */     }
/*     */     catch (KeyStoreException e)
/*     */     {
/* 242 */       throw new CryptographyException(e);
/*     */     }
/*     */     catch (NoSuchProviderException e)
/*     */     {
/* 246 */       throw new CryptographyException(e);
/*     */     }
/*     */     catch (NoSuchAlgorithmException e)
/*     */     {
/* 250 */       throw new CryptographyException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void prepareDocumentForEncryption(PDDocument doc)
/*     */     throws CryptographyException
/*     */   {
/*     */     try
/*     */     {
/* 266 */       Security.addProvider(new BouncyCastleProvider());
/*     */ 
/* 268 */       PDEncryptionDictionary dictionary = doc.getEncryptionDictionary();
/* 269 */       if (dictionary == null)
/*     */       {
/* 271 */         dictionary = new PDEncryptionDictionary();
/*     */       }
/*     */ 
/* 274 */       dictionary.setFilter("Adobe.PubSec");
/* 275 */       dictionary.setLength(this.keyLength);
/* 276 */       dictionary.setVersion(2);
/* 277 */       dictionary.setSubFilter("adbe.pkcs7.s4");
/*     */ 
/* 279 */       byte[][] recipientsField = new byte[this.policy.getRecipientsNumber()][];
/*     */ 
/* 283 */       byte[] seed = new byte[20];
/*     */ 
/* 285 */       KeyGenerator key = KeyGenerator.getInstance("AES");
/* 286 */       key.init(192, new SecureRandom());
/* 287 */       SecretKey sk = key.generateKey();
/* 288 */       System.arraycopy(sk.getEncoded(), 0, seed, 0, 20);
/*     */ 
/* 291 */       Iterator it = this.policy.getRecipientsIterator();
/* 292 */       int i = 0;
/*     */ 
/* 295 */       while (it.hasNext())
/*     */       {
/* 297 */         PublicKeyRecipient recipient = (PublicKeyRecipient)it.next();
/* 298 */         X509Certificate certificate = recipient.getX509();
/* 299 */         int permission = recipient.getPermission().getPermissionBytesForPublicKey();
/*     */ 
/* 301 */         byte[] pkcs7input = new byte[24];
/* 302 */         byte one = (byte)permission;
/* 303 */         byte two = (byte)(permission >>> 8);
/* 304 */         byte three = (byte)(permission >>> 16);
/* 305 */         byte four = (byte)(permission >>> 24);
/*     */ 
/* 307 */         System.arraycopy(seed, 0, pkcs7input, 0, 20);
/*     */ 
/* 309 */         pkcs7input[20] = four;
/* 310 */         pkcs7input[21] = three;
/* 311 */         pkcs7input[22] = two;
/* 312 */         pkcs7input[23] = one;
/*     */ 
/* 314 */         DERObject obj = createDERForRecipient(pkcs7input, certificate);
/*     */ 
/* 316 */         ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*     */ 
/* 318 */         DEROutputStream k = new DEROutputStream(baos);
/*     */ 
/* 320 */         k.writeObject(obj);
/*     */ 
/* 322 */         recipientsField[i] = baos.toByteArray();
/*     */ 
/* 324 */         i++;
/*     */       }
/*     */ 
/* 327 */       dictionary.setRecipients(recipientsField);
/*     */ 
/* 329 */       int sha1InputLength = seed.length;
/*     */ 
/* 331 */       for (int j = 0; j < dictionary.getRecipientsLength(); j++)
/*     */       {
/* 333 */         COSString string = dictionary.getRecipientStringAt(j);
/* 334 */         sha1InputLength += string.getBytes().length;
/*     */       }
/*     */ 
/* 338 */       byte[] sha1Input = new byte[sha1InputLength];
/*     */ 
/* 340 */       System.arraycopy(seed, 0, sha1Input, 0, 20);
/*     */ 
/* 342 */       int sha1InputOffset = 20;
/*     */ 
/* 345 */       for (int j = 0; j < dictionary.getRecipientsLength(); j++)
/*     */       {
/* 347 */         COSString string = dictionary.getRecipientStringAt(j);
/* 348 */         System.arraycopy(string.getBytes(), 0, sha1Input, sha1InputOffset, string.getBytes().length);
/*     */ 
/* 351 */         sha1InputOffset += string.getBytes().length;
/*     */       }
/*     */ 
/* 354 */       MessageDigest md = MessageDigest.getInstance("SHA-1");
/*     */ 
/* 356 */       byte[] mdResult = md.digest(sha1Input);
/*     */ 
/* 358 */       this.encryptionKey = new byte[this.keyLength / 8];
/* 359 */       System.arraycopy(mdResult, 0, this.encryptionKey, 0, this.keyLength / 8);
/*     */ 
/* 361 */       doc.setEncryptionDictionary(dictionary);
/* 362 */       doc.getDocument().setEncryptionDictionary(dictionary.encryptionDictionary);
/*     */     }
/*     */     catch (NoSuchAlgorithmException ex)
/*     */     {
/* 367 */       throw new CryptographyException(ex);
/*     */     }
/*     */     catch (NoSuchProviderException ex)
/*     */     {
/* 371 */       throw new CryptographyException(ex);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 375 */       LOG.error(e, e);
/* 376 */       throw new CryptographyException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private DERObject createDERForRecipient(byte[] in, X509Certificate cert)
/*     */     throws IOException, GeneralSecurityException
/*     */   {
/* 386 */     String s = "1.2.840.113549.3.2";
/*     */ 
/* 388 */     AlgorithmParameterGenerator algorithmparametergenerator = AlgorithmParameterGenerator.getInstance(s);
/* 389 */     AlgorithmParameters algorithmparameters = algorithmparametergenerator.generateParameters();
/* 390 */     ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(algorithmparameters.getEncoded("ASN.1"));
/* 391 */     ASN1InputStream asn1inputstream = new ASN1InputStream(bytearrayinputstream);
/* 392 */     DERObject derobject = asn1inputstream.readObject();
/* 393 */     KeyGenerator keygenerator = KeyGenerator.getInstance(s);
/* 394 */     keygenerator.init(128);
/* 395 */     SecretKey secretkey = keygenerator.generateKey();
/* 396 */     Cipher cipher = Cipher.getInstance(s);
/* 397 */     cipher.init(1, secretkey, algorithmparameters);
/* 398 */     byte[] abyte1 = cipher.doFinal(in);
/* 399 */     DEROctetString deroctetstring = new DEROctetString(abyte1);
/* 400 */     KeyTransRecipientInfo keytransrecipientinfo = computeRecipientInfo(cert, secretkey.getEncoded());
/* 401 */     DERSet derset = new DERSet(new RecipientInfo(keytransrecipientinfo));
/* 402 */     AlgorithmIdentifier algorithmidentifier = new AlgorithmIdentifier(new DERObjectIdentifier(s), derobject);
/* 403 */     EncryptedContentInfo encryptedcontentinfo = new EncryptedContentInfo(PKCSObjectIdentifiers.data, algorithmidentifier, deroctetstring);
/*     */ 
/* 405 */     EnvelopedData env = new EnvelopedData(null, derset, encryptedcontentinfo, null);
/* 406 */     ContentInfo contentinfo = new ContentInfo(PKCSObjectIdentifiers.envelopedData, env);
/*     */ 
/* 408 */     return contentinfo.getDERObject();
/*     */   }
/*     */ 
/*     */   private KeyTransRecipientInfo computeRecipientInfo(X509Certificate x509certificate, byte[] abyte0)
/*     */     throws GeneralSecurityException, IOException
/*     */   {
/* 414 */     ASN1InputStream asn1inputstream = new ASN1InputStream(new ByteArrayInputStream(x509certificate.getTBSCertificate()));
/*     */ 
/* 416 */     TBSCertificateStructure tbscertificatestructure = TBSCertificateStructure.getInstance(asn1inputstream.readObject());
/*     */ 
/* 418 */     AlgorithmIdentifier algorithmidentifier = tbscertificatestructure.getSubjectPublicKeyInfo().getAlgorithmId();
/* 419 */     IssuerAndSerialNumber issuerandserialnumber = new IssuerAndSerialNumber(tbscertificatestructure.getIssuer(), tbscertificatestructure.getSerialNumber().getValue());
/*     */ 
/* 423 */     Cipher cipher = Cipher.getInstance(algorithmidentifier.getObjectId().getId());
/* 424 */     cipher.init(1, x509certificate.getPublicKey());
/* 425 */     DEROctetString deroctetstring = new DEROctetString(cipher.doFinal(abyte0));
/* 426 */     RecipientIdentifier recipId = new RecipientIdentifier(issuerandserialnumber);
/* 427 */     return new KeyTransRecipientInfo(recipId, algorithmidentifier, deroctetstring);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.encryption.PublicKeySecurityHandler
 * JD-Core Version:    0.6.2
 */