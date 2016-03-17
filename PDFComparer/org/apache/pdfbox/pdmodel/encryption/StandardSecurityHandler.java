/*     */ package org.apache.pdfbox.pdmodel.encryption;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.Arrays;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ import org.apache.pdfbox.encryption.ARCFour;
/*     */ import org.apache.pdfbox.exceptions.CryptographyException;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ 
/*     */ public class StandardSecurityHandler extends SecurityHandler
/*     */ {
/*     */   public static final String FILTER = "Standard";
/*     */   private static final int DEFAULT_VERSION = 1;
/*     */   private static final int DEFAULT_REVISION = 3;
/*  59 */   private int revision = 3;
/*     */   private StandardProtectionPolicy policy;
/*  63 */   private ARCFour rc4 = new ARCFour();
/*     */ 
/*  68 */   public static final Class<?> PROTECTION_POLICY_CLASS = StandardProtectionPolicy.class;
/*     */ 
/*  73 */   public static final byte[] ENCRYPT_PADDING = { 40, -65, 78, 94, 78, 117, -118, 65, 100, 0, 78, 86, -1, -6, 1, 8, 46, 46, 0, -74, -48, 104, 62, -128, 47, 12, -87, -2, 100, 83, 105, 122 };
/*     */ 
/*     */   public StandardSecurityHandler()
/*     */   {
/*     */   }
/*     */ 
/*     */   public StandardSecurityHandler(StandardProtectionPolicy p)
/*     */   {
/*  98 */     this.policy = p;
/*  99 */     this.keyLength = this.policy.getEncryptionKeyLength();
/*     */   }
/*     */ 
/*     */   private int computeVersionNumber()
/*     */   {
/* 112 */     if (this.keyLength == 40)
/*     */     {
/* 114 */       return 1;
/*     */     }
/* 116 */     return 2;
/*     */   }
/*     */ 
/*     */   private int computeRevisionNumber()
/*     */   {
/* 128 */     if ((this.version < 2) && (!this.policy.getPermissions().hasAnyRevision3PermissionSet()))
/*     */     {
/* 130 */       return 2;
/*     */     }
/* 132 */     if ((this.version == 2) || (this.version == 3) || (this.policy.getPermissions().hasAnyRevision3PermissionSet()))
/*     */     {
/* 134 */       return 3;
/*     */     }
/* 136 */     return 4;
/*     */   }
/*     */ 
/*     */   public void decryptDocument(PDDocument doc, DecryptionMaterial decryptionMaterial)
/*     */     throws CryptographyException, IOException
/*     */   {
/* 151 */     this.document = doc;
/*     */ 
/* 153 */     PDEncryptionDictionary dictionary = this.document.getEncryptionDictionary();
/* 154 */     COSArray documentIDArray = this.document.getDocument().getDocumentID();
/*     */ 
/* 156 */     prepareForDecryption(dictionary, documentIDArray, decryptionMaterial);
/*     */ 
/* 158 */     proceedDecryption();
/*     */   }
/*     */ 
/*     */   public void prepareForDecryption(PDEncryptionDictionary encDictionary, COSArray documentIDArray, DecryptionMaterial decryptionMaterial)
/*     */     throws CryptographyException, IOException
/*     */   {
/* 178 */     if (!(decryptionMaterial instanceof StandardDecryptionMaterial))
/*     */     {
/* 180 */       throw new CryptographyException("Provided decryption material is not compatible with the document");
/*     */     }
/* 182 */     this.decryptMetadata = encDictionary.isEncryptMetaData();
/*     */ 
/* 184 */     StandardDecryptionMaterial material = (StandardDecryptionMaterial)decryptionMaterial;
/*     */ 
/* 186 */     String password = material.getPassword();
/* 187 */     if (password == null)
/*     */     {
/* 189 */       password = "";
/*     */     }
/*     */ 
/* 192 */     int dicPermissions = encDictionary.getPermissions();
/* 193 */     int dicRevision = encDictionary.getRevision();
/* 194 */     int dicLength = encDictionary.getLength() / 8;
/*     */ 
/* 198 */     byte[] documentIDBytes = null;
/* 199 */     if ((documentIDArray != null) && (documentIDArray.size() >= 1))
/*     */     {
/* 201 */       COSString id = (COSString)documentIDArray.getObject(0);
/* 202 */       documentIDBytes = id.getBytes();
/*     */     }
/*     */     else
/*     */     {
/* 206 */       documentIDBytes = new byte[0];
/*     */     }
/*     */ 
/* 210 */     boolean encryptMetadata = encDictionary.isEncryptMetaData();
/*     */ 
/* 212 */     byte[] u = encDictionary.getUserKey();
/* 213 */     byte[] o = encDictionary.getOwnerKey();
/*     */ 
/* 215 */     boolean isUserPassword = isUserPassword(password.getBytes("ISO-8859-1"), u, o, dicPermissions, documentIDBytes, dicRevision, dicLength, encryptMetadata);
/*     */ 
/* 225 */     boolean isOwnerPassword = isOwnerPassword(password.getBytes("ISO-8859-1"), u, o, dicPermissions, documentIDBytes, dicRevision, dicLength, encryptMetadata);
/*     */ 
/* 236 */     if (isUserPassword)
/*     */     {
/* 238 */       this.currentAccessPermission = new AccessPermission(dicPermissions);
/* 239 */       this.encryptionKey = computeEncryptedKey(password.getBytes("ISO-8859-1"), o, dicPermissions, documentIDBytes, dicRevision, dicLength, encryptMetadata);
/*     */     }
/* 249 */     else if (isOwnerPassword)
/*     */     {
/* 251 */       this.currentAccessPermission = AccessPermission.getOwnerAccessPermission();
/* 252 */       byte[] computedUserPassword = getUserPassword(password.getBytes("ISO-8859-1"), o, dicRevision, dicLength);
/* 253 */       this.encryptionKey = computeEncryptedKey(computedUserPassword, o, dicPermissions, documentIDBytes, dicRevision, dicLength, encryptMetadata);
/*     */     }
/*     */     else
/*     */     {
/* 265 */       throw new CryptographyException("Error: The supplied password does not match either the owner or user password in the document.");
/*     */     }
/*     */ 
/* 271 */     PDCryptFilterDictionary stdCryptFilterDictionary = encDictionary.getStdCryptFilterDictionary();
/*     */ 
/* 273 */     if (stdCryptFilterDictionary != null)
/*     */     {
/* 275 */       COSName cryptFilterMethod = stdCryptFilterDictionary.getCryptFilterMethod();
/* 276 */       if (cryptFilterMethod != null)
/*     */       {
/* 278 */         setAES("AESV2".equalsIgnoreCase(cryptFilterMethod.getName()));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void prepareDocumentForEncryption(PDDocument doc)
/*     */     throws CryptographyException, IOException
/*     */   {
/* 293 */     this.document = doc;
/* 294 */     PDEncryptionDictionary encryptionDictionary = this.document.getEncryptionDictionary();
/* 295 */     if (encryptionDictionary == null)
/*     */     {
/* 297 */       encryptionDictionary = new PDEncryptionDictionary();
/*     */     }
/* 299 */     this.version = computeVersionNumber();
/* 300 */     this.revision = computeRevisionNumber();
/* 301 */     encryptionDictionary.setFilter("Standard");
/* 302 */     encryptionDictionary.setVersion(this.version);
/* 303 */     encryptionDictionary.setRevision(this.revision);
/* 304 */     encryptionDictionary.setLength(this.keyLength);
/*     */ 
/* 306 */     String ownerPassword = this.policy.getOwnerPassword();
/* 307 */     String userPassword = this.policy.getUserPassword();
/* 308 */     if (ownerPassword == null)
/*     */     {
/* 310 */       ownerPassword = "";
/*     */     }
/* 312 */     if (userPassword == null)
/*     */     {
/* 314 */       userPassword = "";
/*     */     }
/*     */ 
/* 317 */     int permissionInt = this.policy.getPermissions().getPermissionBytes();
/*     */ 
/* 319 */     encryptionDictionary.setPermissions(permissionInt);
/*     */ 
/* 321 */     int length = this.keyLength / 8;
/*     */ 
/* 323 */     COSArray idArray = this.document.getDocument().getDocumentID();
/*     */ 
/* 327 */     if ((idArray == null) || (idArray.size() < 2))
/*     */     {
/* 329 */       idArray = new COSArray();
/*     */       try
/*     */       {
/* 332 */         MessageDigest md = MessageDigest.getInstance("MD5");
/* 333 */         BigInteger time = BigInteger.valueOf(System.currentTimeMillis());
/* 334 */         md.update(time.toByteArray());
/* 335 */         md.update(ownerPassword.getBytes("ISO-8859-1"));
/* 336 */         md.update(userPassword.getBytes("ISO-8859-1"));
/* 337 */         md.update(this.document.getDocument().toString().getBytes());
/* 338 */         byte[] id = md.digest(toString().getBytes("ISO-8859-1"));
/* 339 */         COSString idString = new COSString();
/* 340 */         idString.append(id);
/* 341 */         idArray.add(idString);
/* 342 */         idArray.add(idString);
/* 343 */         this.document.getDocument().setDocumentID(idArray);
/*     */       }
/*     */       catch (NoSuchAlgorithmException e)
/*     */       {
/* 347 */         throw new CryptographyException(e);
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 351 */         throw new CryptographyException(e);
/*     */       }
/*     */     }
/*     */ 
/* 355 */     COSString id = (COSString)idArray.getObject(0);
/*     */ 
/* 357 */     byte[] o = computeOwnerPassword(ownerPassword.getBytes("ISO-8859-1"), userPassword.getBytes("ISO-8859-1"), this.revision, length);
/*     */ 
/* 361 */     byte[] u = computeUserPassword(userPassword.getBytes("ISO-8859-1"), o, permissionInt, id.getBytes(), this.revision, length, true);
/*     */ 
/* 365 */     this.encryptionKey = computeEncryptedKey(userPassword.getBytes("ISO-8859-1"), o, permissionInt, id.getBytes(), this.revision, length, true);
/*     */ 
/* 368 */     encryptionDictionary.setOwnerKey(o);
/* 369 */     encryptionDictionary.setUserKey(u);
/*     */ 
/* 371 */     this.document.setEncryptionDictionary(encryptionDictionary);
/* 372 */     this.document.getDocument().setEncryptionDictionary(encryptionDictionary.getCOSDictionary());
/*     */   }
/*     */ 
/*     */   public final boolean isOwnerPassword(byte[] ownerPassword, byte[] u, byte[] o, int permissions, byte[] id, int encRevision, int length, boolean encryptMetadata)
/*     */     throws CryptographyException, IOException
/*     */   {
/* 404 */     byte[] userPassword = getUserPassword(ownerPassword, o, encRevision, length);
/* 405 */     return isUserPassword(userPassword, u, o, permissions, id, encRevision, length, encryptMetadata);
/*     */   }
/*     */ 
/*     */   public final byte[] getUserPassword(byte[] ownerPassword, byte[] o, int encRevision, int length)
/*     */     throws CryptographyException, IOException
/*     */   {
/*     */     try
/*     */     {
/* 430 */       ByteArrayOutputStream result = new ByteArrayOutputStream();
/* 431 */       byte[] rc4Key = computeRC4key(ownerPassword, encRevision, length);
/*     */ 
/* 433 */       if ((encRevision == 2) && (length != 5))
/*     */       {
/* 435 */         throw new CryptographyException("Error: Expected length=5 actual=" + length);
/*     */       }
/*     */ 
/* 440 */       if (encRevision == 2)
/*     */       {
/* 442 */         this.rc4.setKey(rc4Key);
/* 443 */         this.rc4.write(o, result);
/*     */       }
/* 445 */       else if ((encRevision == 3) || (encRevision == 4))
/*     */       {
/* 447 */         byte[] iterationKey = new byte[rc4Key.length];
/* 448 */         byte[] otemp = new byte[o.length];
/* 449 */         System.arraycopy(o, 0, otemp, 0, o.length);
/* 450 */         this.rc4.write(o, result);
/*     */ 
/* 452 */         for (int i = 19; i >= 0; i--)
/*     */         {
/* 454 */           System.arraycopy(rc4Key, 0, iterationKey, 0, rc4Key.length);
/* 455 */           for (int j = 0; j < iterationKey.length; j++)
/*     */           {
/* 457 */             iterationKey[j] = ((byte)(iterationKey[j] ^ (byte)i));
/*     */           }
/* 459 */           this.rc4.setKey(iterationKey);
/* 460 */           result.reset();
/* 461 */           this.rc4.write(otemp, result);
/* 462 */           otemp = result.toByteArray();
/*     */         }
/*     */       }
/* 465 */       return result.toByteArray();
/*     */     }
/*     */     catch (NoSuchAlgorithmException e)
/*     */     {
/* 469 */       throw new CryptographyException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final byte[] computeEncryptedKey(byte[] password, byte[] o, int permissions, byte[] id, int encRevision, int length, boolean encryptMetadata)
/*     */     throws CryptographyException
/*     */   {
/* 498 */     byte[] result = new byte[length];
/*     */     try
/*     */     {
/* 503 */       byte[] padded = truncateOrPad(password);
/*     */ 
/* 506 */       MessageDigest md = MessageDigest.getInstance("MD5");
/* 507 */       md.update(padded);
/*     */ 
/* 510 */       md.update(o);
/*     */ 
/* 513 */       byte zero = (byte)(permissions >>> 0);
/* 514 */       byte one = (byte)(permissions >>> 8);
/* 515 */       byte two = (byte)(permissions >>> 16);
/* 516 */       byte three = (byte)(permissions >>> 24);
/*     */ 
/* 518 */       md.update(zero);
/* 519 */       md.update(one);
/* 520 */       md.update(two);
/* 521 */       md.update(three);
/*     */ 
/* 524 */       md.update(id);
/*     */ 
/* 529 */       if ((encRevision == 4) && (!encryptMetadata))
/*     */       {
/* 531 */         md.update(new byte[] { -1, -1, -1, -1 });
/*     */       }
/*     */ 
/* 534 */       byte[] digest = md.digest();
/*     */ 
/* 537 */       if ((encRevision == 3) || (encRevision == 4))
/*     */       {
/* 539 */         for (int i = 0; i < 50; i++)
/*     */         {
/* 541 */           md.reset();
/* 542 */           md.update(digest, 0, length);
/* 543 */           digest = md.digest();
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 548 */       if ((encRevision == 2) && (length != 5))
/*     */       {
/* 550 */         throw new CryptographyException("Error: length should be 5 when revision is two actual=" + length);
/*     */       }
/*     */ 
/* 553 */       System.arraycopy(digest, 0, result, 0, length);
/*     */     }
/*     */     catch (NoSuchAlgorithmException e)
/*     */     {
/* 557 */       throw new CryptographyException(e);
/*     */     }
/* 559 */     return result;
/*     */   }
/*     */ 
/*     */   public final byte[] computeUserPassword(byte[] password, byte[] o, int permissions, byte[] id, int encRevision, int length, boolean encryptMetadata)
/*     */     throws CryptographyException, IOException
/*     */   {
/* 589 */     ByteArrayOutputStream result = new ByteArrayOutputStream();
/*     */ 
/* 591 */     byte[] encryptionKey = computeEncryptedKey(password, o, permissions, id, encRevision, length, encryptMetadata);
/*     */ 
/* 593 */     if (encRevision == 2)
/*     */     {
/* 596 */       this.rc4.setKey(encryptionKey);
/* 597 */       this.rc4.write(ENCRYPT_PADDING, result);
/*     */     }
/* 599 */     else if ((encRevision == 3) || (encRevision == 4))
/*     */     {
/*     */       try
/*     */       {
/* 604 */         MessageDigest md = MessageDigest.getInstance("MD5");
/*     */ 
/* 606 */         md.update(ENCRYPT_PADDING);
/*     */ 
/* 609 */         md.update(id);
/* 610 */         result.write(md.digest());
/*     */ 
/* 613 */         byte[] iterationKey = new byte[encryptionKey.length];
/* 614 */         for (int i = 0; i < 20; i++)
/*     */         {
/* 616 */           System.arraycopy(encryptionKey, 0, iterationKey, 0, iterationKey.length);
/* 617 */           for (int j = 0; j < iterationKey.length; j++)
/*     */           {
/* 619 */             iterationKey[j] = ((byte)(iterationKey[j] ^ i));
/*     */           }
/* 621 */           this.rc4.setKey(iterationKey);
/* 622 */           ByteArrayInputStream input = new ByteArrayInputStream(result.toByteArray());
/* 623 */           result.reset();
/* 624 */           this.rc4.write(input, result);
/*     */         }
/*     */ 
/* 628 */         byte[] finalResult = new byte[32];
/* 629 */         System.arraycopy(result.toByteArray(), 0, finalResult, 0, 16);
/* 630 */         System.arraycopy(ENCRYPT_PADDING, 0, finalResult, 16, 16);
/* 631 */         result.reset();
/* 632 */         result.write(finalResult);
/*     */       }
/*     */       catch (NoSuchAlgorithmException e)
/*     */       {
/* 636 */         throw new CryptographyException(e);
/*     */       }
/*     */     }
/* 639 */     return result.toByteArray();
/*     */   }
/*     */ 
/*     */   public final byte[] computeOwnerPassword(byte[] ownerPassword, byte[] userPassword, int encRevision, int length)
/*     */     throws CryptographyException, IOException
/*     */   {
/*     */     try
/*     */     {
/* 664 */       byte[] rc4Key = computeRC4key(ownerPassword, encRevision, length);
/*     */ 
/* 667 */       byte[] paddedUser = truncateOrPad(userPassword);
/*     */ 
/* 671 */       this.rc4.setKey(rc4Key);
/* 672 */       ByteArrayOutputStream crypted = new ByteArrayOutputStream();
/* 673 */       this.rc4.write(new ByteArrayInputStream(paddedUser), crypted);
/*     */ 
/* 677 */       if ((encRevision == 3) || (encRevision == 4))
/*     */       {
/* 679 */         byte[] iterationKey = new byte[rc4Key.length];
/* 680 */         for (int i = 1; i < 20; i++)
/*     */         {
/* 682 */           System.arraycopy(rc4Key, 0, iterationKey, 0, rc4Key.length);
/* 683 */           for (int j = 0; j < iterationKey.length; j++)
/*     */           {
/* 685 */             iterationKey[j] = ((byte)(iterationKey[j] ^ (byte)i));
/*     */           }
/* 687 */           this.rc4.setKey(iterationKey);
/* 688 */           ByteArrayInputStream input = new ByteArrayInputStream(crypted.toByteArray());
/* 689 */           crypted.reset();
/* 690 */           this.rc4.write(input, crypted);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 695 */       return crypted.toByteArray();
/*     */     }
/*     */     catch (NoSuchAlgorithmException e)
/*     */     {
/* 699 */       throw new CryptographyException(e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   private byte[] computeRC4key(byte[] ownerPassword, int encRevision, int length)
/*     */     throws NoSuchAlgorithmException, CryptographyException
/*     */   {
/* 707 */     if ((encRevision == 2) && (length != 5))
/*     */     {
/* 709 */       throw new CryptographyException("Error: Expected length=5 actual=" + length);
/*     */     }
/*     */ 
/* 714 */     byte[] ownerPadded = truncateOrPad(ownerPassword);
/*     */ 
/* 717 */     MessageDigest md = MessageDigest.getInstance("MD5");
/* 718 */     md.update(ownerPadded);
/* 719 */     byte[] digest = md.digest();
/*     */ 
/* 722 */     if ((encRevision == 3) || (encRevision == 4))
/*     */     {
/* 724 */       for (int i = 0; i < 50; i++)
/*     */       {
/* 729 */         md.reset();
/* 730 */         md.update(digest, 0, length);
/* 731 */         digest = md.digest();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 736 */     byte[] rc4Key = new byte[length];
/* 737 */     System.arraycopy(digest, 0, rc4Key, 0, length);
/* 738 */     return rc4Key;
/*     */   }
/*     */ 
/*     */   private final byte[] truncateOrPad(byte[] password)
/*     */   {
/* 750 */     byte[] padded = new byte[ENCRYPT_PADDING.length];
/* 751 */     int bytesBeforePad = Math.min(password.length, padded.length);
/* 752 */     System.arraycopy(password, 0, padded, 0, bytesBeforePad);
/* 753 */     System.arraycopy(ENCRYPT_PADDING, 0, padded, bytesBeforePad, ENCRYPT_PADDING.length - bytesBeforePad);
/* 754 */     return padded;
/*     */   }
/*     */ 
/*     */   public final boolean isUserPassword(byte[] password, byte[] u, byte[] o, int permissions, byte[] id, int encRevision, int length, boolean encryptMetadata)
/*     */     throws CryptographyException, IOException
/*     */   {
/* 785 */     boolean matches = false;
/*     */ 
/* 787 */     byte[] computedValue = computeUserPassword(password, o, permissions, id, encRevision, length, encryptMetadata);
/*     */ 
/* 789 */     if (encRevision == 2)
/*     */     {
/* 792 */       matches = Arrays.equals(u, computedValue);
/*     */     }
/* 794 */     else if ((encRevision == 3) || (encRevision == 4))
/*     */     {
/* 797 */       matches = arraysEqual(u, computedValue, 16);
/*     */     }
/*     */     else
/*     */     {
/* 801 */       throw new IOException("Unknown Encryption Revision " + encRevision);
/*     */     }
/* 803 */     return matches;
/*     */   }
/*     */ 
/*     */   public final boolean isUserPassword(String password, byte[] u, byte[] o, int permissions, byte[] id, int encRevision, int length, boolean encryptMetadata)
/*     */     throws CryptographyException, IOException
/*     */   {
/* 834 */     return isUserPassword(password.getBytes("ISO-8859-1"), u, o, permissions, id, encRevision, length, encryptMetadata);
/*     */   }
/*     */ 
/*     */   public final boolean isOwnerPassword(String password, byte[] u, byte[] o, int permissions, byte[] id, int encRevision, int length, boolean encryptMetadata)
/*     */     throws CryptographyException, IOException
/*     */   {
/* 866 */     return isOwnerPassword(password.getBytes("ISO-8859-1"), u, o, permissions, id, encRevision, length, encryptMetadata);
/*     */   }
/*     */ 
/*     */   private static final boolean arraysEqual(byte[] first, byte[] second, int count)
/*     */   {
/* 873 */     if ((first.length < count) || (second.length < count))
/*     */     {
/* 875 */       return false;
/*     */     }
/* 877 */     for (int i = 0; i < count; i++)
/*     */     {
/* 879 */       if (first[i] != second[i])
/*     */       {
/* 881 */         return false;
/*     */       }
/*     */     }
/* 884 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.encryption.StandardSecurityHandler
 * JD-Core Version:    0.6.2
 */