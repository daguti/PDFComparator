/*     */ package org.apache.pdfbox.encryption;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import org.apache.pdfbox.exceptions.CryptographyException;
/*     */ 
/*     */ /** @deprecated */
/*     */ public final class PDFEncryption
/*     */ {
/*  42 */   private ARCFour rc4 = new ARCFour();
/*     */ 
/*  46 */   public static final byte[] ENCRYPT_PADDING = { 40, -65, 78, 94, 78, 117, -118, 65, 100, 0, 78, 86, -1, -6, 1, 8, 46, 46, 0, -74, -48, 104, 62, -128, 47, 12, -87, -2, 100, 83, 105, 122 };
/*     */ 
/*     */   public final void encryptData(long objectNumber, long genNumber, byte[] key, InputStream data, OutputStream output)
/*     */     throws CryptographyException, IOException
/*     */   {
/*  77 */     byte[] newKey = new byte[key.length + 5];
/*  78 */     System.arraycopy(key, 0, newKey, 0, key.length);
/*     */ 
/*  84 */     newKey[(newKey.length - 5)] = ((byte)(int)(objectNumber & 0xFF));
/*  85 */     newKey[(newKey.length - 4)] = ((byte)(int)(objectNumber >> 8 & 0xFF));
/*  86 */     newKey[(newKey.length - 3)] = ((byte)(int)(objectNumber >> 16 & 0xFF));
/*  87 */     newKey[(newKey.length - 2)] = ((byte)(int)(genNumber & 0xFF));
/*  88 */     newKey[(newKey.length - 1)] = ((byte)(int)(genNumber >> 8 & 0xFF));
/*     */ 
/*  92 */     byte[] digestedKey = null;
/*     */     try
/*     */     {
/*  95 */       MessageDigest md = MessageDigest.getInstance("MD5");
/*  96 */       digestedKey = md.digest(newKey);
/*     */     }
/*     */     catch (NoSuchAlgorithmException e)
/*     */     {
/* 100 */       throw new CryptographyException(e);
/*     */     }
/*     */ 
/* 104 */     int length = Math.min(newKey.length, 16);
/* 105 */     byte[] finalKey = new byte[length];
/* 106 */     System.arraycopy(digestedKey, 0, finalKey, 0, length);
/*     */ 
/* 108 */     this.rc4.setKey(finalKey);
/* 109 */     this.rc4.write(data, output);
/* 110 */     output.flush();
/*     */   }
/*     */ 
/*     */   public final byte[] getUserPassword(byte[] ownerPassword, byte[] o, int revision, long length)
/*     */     throws CryptographyException, IOException
/*     */   {
/*     */     try
/*     */     {
/* 135 */       ByteArrayOutputStream result = new ByteArrayOutputStream();
/*     */ 
/* 138 */       byte[] ownerPadded = truncateOrPad(ownerPassword);
/*     */ 
/* 141 */       MessageDigest md = MessageDigest.getInstance("MD5");
/* 142 */       md.update(ownerPadded);
/* 143 */       byte[] digest = md.digest();
/*     */ 
/* 146 */       if ((revision == 3) || (revision == 4))
/*     */       {
/* 148 */         for (int i = 0; i < 50; i++)
/*     */         {
/* 150 */           md.reset();
/* 151 */           md.update(digest);
/* 152 */           digest = md.digest();
/*     */         }
/*     */       }
/* 155 */       if ((revision == 2) && (length != 5L))
/*     */       {
/* 157 */         throw new CryptographyException("Error: Expected length=5 actual=" + length);
/*     */       }
/*     */ 
/* 162 */       byte[] rc4Key = new byte[(int)length];
/* 163 */       System.arraycopy(digest, 0, rc4Key, 0, (int)length);
/*     */ 
/* 166 */       if (revision == 2)
/*     */       {
/* 168 */         this.rc4.setKey(rc4Key);
/* 169 */         this.rc4.write(o, result);
/*     */       }
/* 171 */       else if ((revision == 3) || (revision == 4))
/*     */       {
/* 190 */         byte[] iterationKey = new byte[rc4Key.length];
/*     */ 
/* 193 */         byte[] otemp = new byte[o.length];
/* 194 */         System.arraycopy(o, 0, otemp, 0, o.length);
/* 195 */         this.rc4.write(o, result);
/*     */ 
/* 197 */         for (int i = 19; i >= 0; i--)
/*     */         {
/* 199 */           System.arraycopy(rc4Key, 0, iterationKey, 0, rc4Key.length);
/* 200 */           for (int j = 0; j < iterationKey.length; j++)
/*     */           {
/* 202 */             iterationKey[j] = ((byte)(iterationKey[j] ^ (byte)i));
/*     */           }
/* 204 */           this.rc4.setKey(iterationKey);
/* 205 */           result.reset();
/* 206 */           this.rc4.write(otemp, result);
/* 207 */           otemp = result.toByteArray();
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 212 */       return result.toByteArray();
/*     */     }
/*     */     catch (NoSuchAlgorithmException e)
/*     */     {
/* 217 */       throw new CryptographyException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final boolean isOwnerPassword(byte[] ownerPassword, byte[] u, byte[] o, int permissions, byte[] id, int revision, int length)
/*     */     throws CryptographyException, IOException
/*     */   {
/* 247 */     byte[] userPassword = getUserPassword(ownerPassword, o, revision, length);
/* 248 */     return isUserPassword(userPassword, u, o, permissions, id, revision, length);
/*     */   }
/*     */ 
/*     */   public final boolean isUserPassword(byte[] password, byte[] u, byte[] o, int permissions, byte[] id, int revision, int length)
/*     */     throws CryptographyException, IOException
/*     */   {
/* 279 */     boolean matches = false;
/*     */ 
/* 281 */     byte[] computedValue = computeUserPassword(password, o, permissions, id, revision, length);
/* 282 */     if (revision == 2)
/*     */     {
/* 285 */       matches = arraysEqual(u, computedValue);
/*     */     }
/* 287 */     else if ((revision == 3) || (revision == 4))
/*     */     {
/* 290 */       matches = arraysEqual(u, computedValue, 16);
/*     */     }
/* 292 */     return matches;
/*     */   }
/*     */ 
/*     */   private final boolean arraysEqual(byte[] first, byte[] second, int count)
/*     */   {
/* 306 */     boolean equal = (first.length >= count) && (second.length >= count);
/* 307 */     for (int i = 0; (i < count) && (equal); i++)
/*     */     {
/* 309 */       equal = first[i] == second[i];
/*     */     }
/* 311 */     return equal;
/*     */   }
/*     */ 
/*     */   private final boolean arraysEqual(byte[] first, byte[] second)
/*     */   {
/* 324 */     boolean equal = first.length == second.length;
/* 325 */     for (int i = 0; (i < first.length) && (equal); i++)
/*     */     {
/* 327 */       equal = first[i] == second[i];
/*     */     }
/* 329 */     return equal;
/*     */   }
/*     */ 
/*     */   public final byte[] computeUserPassword(byte[] password, byte[] o, int permissions, byte[] id, int revision, int length)
/*     */     throws CryptographyException, IOException
/*     */   {
/* 356 */     ByteArrayOutputStream result = new ByteArrayOutputStream();
/*     */ 
/* 358 */     byte[] encryptionKey = computeEncryptedKey(password, o, permissions, id, revision, length);
/*     */ 
/* 360 */     if (revision == 2)
/*     */     {
/* 363 */       this.rc4.setKey(encryptionKey);
/* 364 */       this.rc4.write(ENCRYPT_PADDING, result);
/*     */     }
/* 366 */     else if ((revision == 3) || (revision == 4))
/*     */     {
/*     */       try
/*     */       {
/* 371 */         MessageDigest md = MessageDigest.getInstance("MD5");
/*     */ 
/* 373 */         md.update(ENCRYPT_PADDING);
/*     */ 
/* 376 */         md.update(id);
/* 377 */         result.write(md.digest());
/*     */ 
/* 380 */         byte[] iterationKey = new byte[encryptionKey.length];
/* 381 */         for (int i = 0; i < 20; i++)
/*     */         {
/* 383 */           System.arraycopy(encryptionKey, 0, iterationKey, 0, iterationKey.length);
/* 384 */           for (int j = 0; j < iterationKey.length; j++)
/*     */           {
/* 386 */             iterationKey[j] = ((byte)(iterationKey[j] ^ i));
/*     */           }
/* 388 */           this.rc4.setKey(iterationKey);
/* 389 */           ByteArrayInputStream input = new ByteArrayInputStream(result.toByteArray());
/* 390 */           result.reset();
/* 391 */           this.rc4.write(input, result);
/*     */         }
/*     */ 
/* 395 */         byte[] finalResult = new byte[32];
/* 396 */         System.arraycopy(result.toByteArray(), 0, finalResult, 0, 16);
/* 397 */         System.arraycopy(ENCRYPT_PADDING, 0, finalResult, 16, 16);
/* 398 */         result.reset();
/* 399 */         result.write(finalResult);
/*     */       }
/*     */       catch (NoSuchAlgorithmException e)
/*     */       {
/* 403 */         throw new CryptographyException(e);
/*     */       }
/*     */     }
/* 406 */     return result.toByteArray();
/*     */   }
/*     */ 
/*     */   public final byte[] computeEncryptedKey(byte[] password, byte[] o, int permissions, byte[] id, int revision, int length)
/*     */     throws CryptographyException
/*     */   {
/* 432 */     byte[] result = new byte[length];
/*     */     try
/*     */     {
/* 437 */       byte[] padded = truncateOrPad(password);
/*     */ 
/* 440 */       MessageDigest md = MessageDigest.getInstance("MD5");
/* 441 */       md.update(padded);
/*     */ 
/* 444 */       md.update(o);
/*     */ 
/* 447 */       byte zero = (byte)(permissions >>> 0);
/* 448 */       byte one = (byte)(permissions >>> 8);
/* 449 */       byte two = (byte)(permissions >>> 16);
/* 450 */       byte three = (byte)(permissions >>> 24);
/*     */ 
/* 452 */       md.update(zero);
/* 453 */       md.update(one);
/* 454 */       md.update(two);
/* 455 */       md.update(three);
/*     */ 
/* 458 */       md.update(id);
/* 459 */       byte[] digest = md.digest();
/*     */ 
/* 462 */       if ((revision == 3) || (revision == 4))
/*     */       {
/* 464 */         for (int i = 0; i < 50; i++)
/*     */         {
/* 466 */           md.reset();
/* 467 */           md.update(digest, 0, length);
/* 468 */           digest = md.digest();
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 473 */       if ((revision == 2) && (length != 5))
/*     */       {
/* 475 */         throw new CryptographyException("Error: length should be 5 when revision is two actual=" + length);
/*     */       }
/*     */ 
/* 478 */       System.arraycopy(digest, 0, result, 0, length);
/*     */     }
/*     */     catch (NoSuchAlgorithmException e)
/*     */     {
/* 482 */       throw new CryptographyException(e);
/*     */     }
/* 484 */     return result;
/*     */   }
/*     */ 
/*     */   public final byte[] computeOwnerPassword(byte[] ownerPassword, byte[] userPassword, int revision, int length)
/*     */     throws CryptographyException, IOException
/*     */   {
/*     */     try
/*     */     {
/* 510 */       byte[] ownerPadded = truncateOrPad(ownerPassword);
/*     */ 
/* 513 */       MessageDigest md = MessageDigest.getInstance("MD5");
/* 514 */       md.update(ownerPadded);
/* 515 */       byte[] digest = md.digest();
/*     */ 
/* 518 */       if ((revision == 3) || (revision == 4))
/*     */       {
/* 520 */         for (int i = 0; i < 50; i++)
/*     */         {
/* 522 */           md.reset();
/* 523 */           md.update(digest, 0, length);
/* 524 */           digest = md.digest();
/*     */         }
/*     */       }
/* 527 */       if ((revision == 2) && (length != 5))
/*     */       {
/* 529 */         throw new CryptographyException("Error: Expected length=5 actual=" + length);
/*     */       }
/*     */ 
/* 534 */       byte[] rc4Key = new byte[length];
/* 535 */       System.arraycopy(digest, 0, rc4Key, 0, length);
/*     */ 
/* 538 */       byte[] paddedUser = truncateOrPad(userPassword);
/*     */ 
/* 542 */       this.rc4.setKey(rc4Key);
/* 543 */       ByteArrayOutputStream crypted = new ByteArrayOutputStream();
/* 544 */       this.rc4.write(new ByteArrayInputStream(paddedUser), crypted);
/*     */ 
/* 548 */       if ((revision == 3) || (revision == 4))
/*     */       {
/* 550 */         byte[] iterationKey = new byte[rc4Key.length];
/* 551 */         for (int i = 1; i < 20; i++)
/*     */         {
/* 553 */           System.arraycopy(rc4Key, 0, iterationKey, 0, rc4Key.length);
/* 554 */           for (int j = 0; j < iterationKey.length; j++)
/*     */           {
/* 556 */             iterationKey[j] = ((byte)(iterationKey[j] ^ (byte)i));
/*     */           }
/* 558 */           this.rc4.setKey(iterationKey);
/* 559 */           ByteArrayInputStream input = new ByteArrayInputStream(crypted.toByteArray());
/* 560 */           crypted.reset();
/* 561 */           this.rc4.write(input, crypted);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 566 */       return crypted.toByteArray();
/*     */     }
/*     */     catch (NoSuchAlgorithmException e)
/*     */     {
/* 570 */       throw new CryptographyException(e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   private final byte[] truncateOrPad(byte[] password)
/*     */   {
/* 583 */     byte[] padded = new byte[ENCRYPT_PADDING.length];
/* 584 */     int bytesBeforePad = Math.min(password.length, padded.length);
/* 585 */     System.arraycopy(password, 0, padded, 0, bytesBeforePad);
/* 586 */     System.arraycopy(ENCRYPT_PADDING, 0, padded, bytesBeforePad, ENCRYPT_PADDING.length - bytesBeforePad);
/* 587 */     return padded;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.encryption.PDFEncryption
 * JD-Core Version:    0.6.2
 */