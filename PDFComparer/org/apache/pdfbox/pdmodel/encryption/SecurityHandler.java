/*     */ package org.apache.pdfbox.pdmodel.encryption;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import javax.crypto.BadPaddingException;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.IllegalBlockSizeException;
/*     */ import javax.crypto.NoSuchPaddingException;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.spec.IvParameterSpec;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSObject;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ import org.apache.pdfbox.encryption.ARCFour;
/*     */ import org.apache.pdfbox.exceptions.CryptographyException;
/*     */ import org.apache.pdfbox.exceptions.WrappedIOException;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ 
/*     */ public abstract class SecurityHandler
/*     */ {
/*     */   private static final int DEFAULT_KEY_LENGTH = 40;
/*  77 */   private static final byte[] AES_SALT = { 115, 65, 108, 84 };
/*     */   protected int version;
/*  87 */   protected int keyLength = 40;
/*     */   protected byte[] encryptionKey;
/*     */   protected PDDocument document;
/* 103 */   protected ARCFour rc4 = new ARCFour();
/*     */   protected boolean decryptMetadata;
/* 109 */   private Set<COSBase> objects = new HashSet();
/*     */ 
/* 111 */   private Set<COSDictionary> potentialSignatures = new HashSet();
/*     */   private boolean aes;
/* 123 */   protected AccessPermission currentAccessPermission = null;
/*     */ 
/*     */   public abstract void prepareDocumentForEncryption(PDDocument paramPDDocument)
/*     */     throws CryptographyException, IOException;
/*     */ 
/*     */   public abstract void prepareForDecryption(PDEncryptionDictionary paramPDEncryptionDictionary, COSArray paramCOSArray, DecryptionMaterial paramDecryptionMaterial)
/*     */     throws CryptographyException, IOException;
/*     */ 
/*     */   public abstract void decryptDocument(PDDocument paramPDDocument, DecryptionMaterial paramDecryptionMaterial)
/*     */     throws CryptographyException, IOException;
/*     */ 
/*     */   protected void proceedDecryption()
/*     */     throws IOException, CryptographyException
/*     */   {
/* 185 */     COSDictionary trailer = this.document.getDocument().getTrailer();
/* 186 */     COSArray fields = (COSArray)trailer.getObjectFromPath("Root/AcroForm/Fields");
/*     */ 
/* 190 */     if (fields != null)
/*     */     {
/* 192 */       for (int i = 0; i < fields.size(); i++)
/*     */       {
/* 194 */         COSDictionary field = (COSDictionary)fields.getObject(i);
/* 195 */         if (field != null)
/*     */         {
/* 197 */           addDictionaryAndSubDictionary(this.potentialSignatures, field);
/*     */         }
/*     */         else
/*     */         {
/* 201 */           throw new IOException("Could not decypt document, object not found.");
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 206 */     List allObjects = this.document.getDocument().getObjects();
/* 207 */     Iterator objectIter = allObjects.iterator();
/* 208 */     COSDictionary encryptionDict = this.document.getEncryptionDictionary().getCOSDictionary();
/* 209 */     while (objectIter.hasNext())
/*     */     {
/* 211 */       COSObject nextObj = (COSObject)objectIter.next();
/* 212 */       COSBase nextCOSBase = nextObj.getObject();
/* 213 */       boolean isSignatureDictionary = false;
/* 214 */       if ((nextCOSBase instanceof COSDictionary))
/*     */       {
/* 216 */         isSignatureDictionary = COSName.SIG.equals(((COSDictionary)nextCOSBase).getDictionaryObject(COSName.TYPE));
/*     */       }
/*     */ 
/* 219 */       if ((!isSignatureDictionary) && (nextCOSBase != encryptionDict))
/*     */       {
/* 221 */         decryptObject(nextObj);
/*     */       }
/*     */     }
/* 224 */     this.document.setEncryptionDictionary(null);
/*     */   }
/*     */ 
/*     */   private void addDictionaryAndSubDictionary(Set<COSDictionary> set, COSDictionary dic)
/*     */   {
/* 229 */     if (dic != null)
/*     */     {
/* 231 */       set.add(dic);
/* 232 */       COSArray kids = (COSArray)dic.getDictionaryObject(COSName.KIDS);
/* 233 */       for (int i = 0; (kids != null) && (i < kids.size()); i++)
/*     */       {
/* 235 */         addDictionaryAndSubDictionary(set, (COSDictionary)kids.getObject(i));
/*     */       }
/* 237 */       COSBase value = dic.getDictionaryObject(COSName.V);
/* 238 */       if ((value instanceof COSDictionary))
/*     */       {
/* 240 */         addDictionaryAndSubDictionary(set, (COSDictionary)value);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void encryptData(long objectNumber, long genNumber, InputStream data, OutputStream output)
/*     */     throws CryptographyException, IOException
/*     */   {
/* 268 */     encryptData(objectNumber, genNumber, data, output, false);
/*     */   }
/*     */ 
/*     */   public void encryptData(long objectNumber, long genNumber, InputStream data, OutputStream output, boolean decrypt)
/*     */     throws CryptographyException, IOException
/*     */   {
/* 293 */     if ((this.aes) && (!decrypt))
/*     */     {
/* 295 */       throw new IllegalArgumentException("AES encryption is not yet implemented.");
/*     */     }
/*     */ 
/* 298 */     byte[] newKey = new byte[this.encryptionKey.length + 5];
/* 299 */     System.arraycopy(this.encryptionKey, 0, newKey, 0, this.encryptionKey.length);
/*     */ 
/* 305 */     newKey[(newKey.length - 5)] = ((byte)(int)(objectNumber & 0xFF));
/* 306 */     newKey[(newKey.length - 4)] = ((byte)(int)(objectNumber >> 8 & 0xFF));
/* 307 */     newKey[(newKey.length - 3)] = ((byte)(int)(objectNumber >> 16 & 0xFF));
/* 308 */     newKey[(newKey.length - 2)] = ((byte)(int)(genNumber & 0xFF));
/* 309 */     newKey[(newKey.length - 1)] = ((byte)(int)(genNumber >> 8 & 0xFF));
/*     */ 
/* 312 */     byte[] digestedKey = null;
/*     */     try
/*     */     {
/* 315 */       MessageDigest md = MessageDigest.getInstance("MD5");
/* 316 */       md.update(newKey);
/* 317 */       if (this.aes)
/*     */       {
/* 319 */         md.update(AES_SALT);
/*     */       }
/* 321 */       digestedKey = md.digest();
/*     */     }
/*     */     catch (NoSuchAlgorithmException e)
/*     */     {
/* 325 */       throw new CryptographyException(e);
/*     */     }
/*     */ 
/* 329 */     int length = Math.min(newKey.length, 16);
/* 330 */     byte[] finalKey = new byte[length];
/* 331 */     System.arraycopy(digestedKey, 0, finalKey, 0, length);
/*     */ 
/* 333 */     if (this.aes)
/*     */     {
/* 335 */       byte[] iv = new byte[16];
/*     */ 
/* 337 */       data.read(iv);
/*     */       try
/*     */       {
/* 341 */         Cipher decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
/* 342 */         SecretKey aesKey = new SecretKeySpec(finalKey, "AES");
/* 343 */         IvParameterSpec ips = new IvParameterSpec(iv);
/* 344 */         decryptCipher.init(decrypt ? 2 : 1, aesKey, ips);
/*     */ 
/* 346 */         byte[] buffer = new byte[256];
/* 347 */         for (int n = 0; -1 != (n = data.read(buffer)); )
/*     */         {
/* 349 */           output.write(decryptCipher.update(buffer, 0, n));
/*     */         }
/* 351 */         output.write(decryptCipher.doFinal());
/*     */       }
/*     */       catch (InvalidKeyException e)
/*     */       {
/* 355 */         throw new WrappedIOException(e);
/*     */       }
/*     */       catch (InvalidAlgorithmParameterException e)
/*     */       {
/* 359 */         throw new WrappedIOException(e);
/*     */       }
/*     */       catch (NoSuchAlgorithmException e)
/*     */       {
/* 363 */         throw new WrappedIOException(e);
/*     */       }
/*     */       catch (NoSuchPaddingException e)
/*     */       {
/* 367 */         throw new WrappedIOException(e);
/*     */       }
/*     */       catch (IllegalBlockSizeException e)
/*     */       {
/* 371 */         throw new WrappedIOException(e);
/*     */       }
/*     */       catch (BadPaddingException e)
/*     */       {
/* 375 */         throw new WrappedIOException(e);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 380 */       this.rc4.setKey(finalKey);
/* 381 */       this.rc4.write(data, output);
/*     */     }
/*     */ 
/* 384 */     output.flush();
/*     */   }
/*     */ 
/*     */   private void decryptObject(COSObject object)
/*     */     throws CryptographyException, IOException
/*     */   {
/* 400 */     long objNum = object.getObjectNumber().intValue();
/* 401 */     long genNum = object.getGenerationNumber().intValue();
/* 402 */     COSBase base = object.getObject();
/* 403 */     decrypt(base, objNum, genNum);
/*     */   }
/*     */ 
/*     */   private void decrypt(COSBase obj, long objNum, long genNum)
/*     */     throws CryptographyException, IOException
/*     */   {
/* 423 */     if (!this.objects.contains(obj))
/*     */     {
/* 425 */       this.objects.add(obj);
/*     */ 
/* 427 */       if ((obj instanceof COSString))
/*     */       {
/* 429 */         decryptString((COSString)obj, objNum, genNum);
/*     */       }
/* 431 */       else if ((obj instanceof COSStream))
/*     */       {
/* 433 */         decryptStream((COSStream)obj, objNum, genNum);
/*     */       }
/* 435 */       else if ((obj instanceof COSDictionary))
/*     */       {
/* 437 */         decryptDictionary((COSDictionary)obj, objNum, genNum);
/*     */       }
/* 439 */       else if ((obj instanceof COSArray))
/*     */       {
/* 441 */         decryptArray((COSArray)obj, objNum, genNum);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void decryptStream(COSStream stream, long objNum, long genNum)
/*     */     throws CryptographyException, IOException
/*     */   {
/* 463 */     COSBase type = stream.getDictionaryObject(COSName.TYPE);
/* 464 */     if ((!this.decryptMetadata) && (COSName.METADATA.equals(type)))
/*     */     {
/* 466 */       return;
/*     */     }
/*     */ 
/* 469 */     if (COSName.XREF.equals(type))
/*     */     {
/* 471 */       return;
/*     */     }
/* 473 */     decryptDictionary(stream, objNum, genNum);
/* 474 */     InputStream encryptedStream = stream.getFilteredStream();
/* 475 */     encryptData(objNum, genNum, encryptedStream, stream.createFilteredStream(), true);
/*     */   }
/*     */ 
/*     */   public void encryptStream(COSStream stream, long objNum, long genNum)
/*     */     throws CryptographyException, IOException
/*     */   {
/* 496 */     InputStream encryptedStream = stream.getFilteredStream();
/* 497 */     encryptData(objNum, genNum, encryptedStream, stream.createFilteredStream(), false);
/*     */   }
/*     */ 
/*     */   private void decryptDictionary(COSDictionary dictionary, long objNum, long genNum)
/*     */     throws CryptographyException, IOException
/*     */   {
/* 518 */     for (Map.Entry entry : dictionary.entrySet())
/*     */     {
/* 520 */       COSBase value = (COSBase)entry.getValue();
/*     */ 
/* 522 */       if (((value instanceof COSString)) || ((value instanceof COSStream)) || ((value instanceof COSArray)) || ((value instanceof COSDictionary)))
/*     */       {
/* 527 */         if ((!((COSName)entry.getKey()).equals(COSName.CONTENTS)) || (!(value instanceof COSString)) || (!this.potentialSignatures.contains(dictionary)))
/*     */         {
/* 530 */           decrypt(value, objNum, genNum);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void encryptString(COSString string, long objNum, long genNum)
/*     */     throws CryptographyException, IOException
/*     */   {
/* 551 */     ByteArrayInputStream data = new ByteArrayInputStream(string.getBytes());
/* 552 */     ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/* 553 */     encryptData(objNum, genNum, data, buffer, false);
/* 554 */     string.reset();
/* 555 */     string.append(buffer.toByteArray());
/*     */   }
/*     */ 
/*     */   public void decryptString(COSString string, long objNum, long genNum)
/*     */     throws CryptographyException, IOException
/*     */   {
/* 575 */     ByteArrayInputStream data = new ByteArrayInputStream(string.getBytes());
/* 576 */     ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/* 577 */     encryptData(objNum, genNum, data, buffer, true);
/* 578 */     string.reset();
/* 579 */     string.append(buffer.toByteArray());
/*     */   }
/*     */ 
/*     */   public void decryptArray(COSArray array, long objNum, long genNum)
/*     */     throws CryptographyException, IOException
/*     */   {
/* 599 */     for (int i = 0; i < array.size(); i++)
/*     */     {
/* 601 */       decrypt(array.get(i), objNum, genNum);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getKeyLength()
/*     */   {
/* 612 */     return this.keyLength;
/*     */   }
/*     */ 
/*     */   public void setKeyLength(int keyLen)
/*     */   {
/* 623 */     this.keyLength = keyLen;
/*     */   }
/*     */ 
/*     */   public AccessPermission getCurrentAccessPermission()
/*     */   {
/* 634 */     return this.currentAccessPermission;
/*     */   }
/*     */ 
/*     */   public boolean isAES()
/*     */   {
/* 644 */     return this.aes;
/*     */   }
/*     */ 
/*     */   public void setAES(boolean aesValue)
/*     */   {
/* 656 */     this.aes = aesValue;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.encryption.SecurityHandler
 * JD-Core Version:    0.6.2
 */