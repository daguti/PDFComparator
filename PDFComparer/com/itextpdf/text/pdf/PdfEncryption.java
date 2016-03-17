/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.DocWriter;
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.exceptions.BadPasswordException;
/*     */ import com.itextpdf.text.pdf.crypto.AESCipherCBCnoPad;
/*     */ import com.itextpdf.text.pdf.crypto.ARCFOUREncryption;
/*     */ import com.itextpdf.text.pdf.crypto.IVGenerator;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.cert.Certificate;
/*     */ 
/*     */ public class PdfEncryption
/*     */ {
/*     */   public static final int STANDARD_ENCRYPTION_40 = 2;
/*     */   public static final int STANDARD_ENCRYPTION_128 = 3;
/*     */   public static final int AES_128 = 4;
/*     */   public static final int AES_256 = 5;
/*  77 */   private static final byte[] pad = { 40, -65, 78, 94, 78, 117, -118, 65, 100, 0, 78, 86, -1, -6, 1, 8, 46, 46, 0, -74, -48, 104, 62, -128, 47, 12, -87, -2, 100, 83, 105, 122 };
/*     */ 
/*  85 */   private static final byte[] salt = { 115, 65, 108, 84 };
/*     */ 
/*  88 */   private static final byte[] metadataPad = { -1, -1, -1, -1 };
/*     */   byte[] key;
/*     */   int keySize;
/*  98 */   byte[] mkey = new byte[0];
/*     */ 
/* 101 */   byte[] extra = new byte[5];
/*     */   MessageDigest md5;
/* 107 */   byte[] ownerKey = new byte[32];
/*     */ 
/* 110 */   byte[] userKey = new byte[32];
/*     */   byte[] oeKey;
/*     */   byte[] ueKey;
/*     */   byte[] perms;
/* 117 */   protected PdfPublicKeySecurityHandler publicKeyHandler = null;
/*     */   int permissions;
/*     */   byte[] documentID;
/* 123 */   static long seq = System.currentTimeMillis();
/*     */   private int revision;
/* 128 */   private ARCFOUREncryption arcfour = new ARCFOUREncryption();
/*     */   private int keyLength;
/*     */   private boolean encryptMetadata;
/*     */   private boolean embeddedFilesOnly;
/*     */   private int cryptoMode;
/*     */   private static final int VALIDATION_SALT_OFFSET = 32;
/*     */   private static final int KEY_SALT_OFFSET = 40;
/*     */   private static final int SALT_LENGHT = 8;
/*     */   private static final int OU_LENGHT = 48;
/*     */ 
/*     */   public PdfEncryption()
/*     */   {
/*     */     try
/*     */     {
/* 145 */       this.md5 = MessageDigest.getInstance("MD5");
/*     */     } catch (Exception e) {
/* 147 */       throw new ExceptionConverter(e);
/*     */     }
/* 149 */     this.publicKeyHandler = new PdfPublicKeySecurityHandler();
/*     */   }
/*     */ 
/*     */   public PdfEncryption(PdfEncryption enc) {
/* 153 */     this();
/* 154 */     if (enc.key != null)
/* 155 */       this.key = ((byte[])enc.key.clone());
/* 156 */     this.keySize = enc.keySize;
/* 157 */     this.mkey = ((byte[])enc.mkey.clone());
/* 158 */     this.ownerKey = ((byte[])enc.ownerKey.clone());
/* 159 */     this.userKey = ((byte[])enc.userKey.clone());
/* 160 */     this.permissions = enc.permissions;
/* 161 */     if (enc.documentID != null)
/* 162 */       this.documentID = ((byte[])enc.documentID.clone());
/* 163 */     this.revision = enc.revision;
/* 164 */     this.keyLength = enc.keyLength;
/* 165 */     this.encryptMetadata = enc.encryptMetadata;
/* 166 */     this.embeddedFilesOnly = enc.embeddedFilesOnly;
/* 167 */     this.publicKeyHandler = enc.publicKeyHandler;
/*     */   }
/*     */ 
/*     */   public void setCryptoMode(int mode, int kl) {
/* 171 */     this.cryptoMode = mode;
/* 172 */     this.encryptMetadata = ((mode & 0x8) != 8);
/* 173 */     this.embeddedFilesOnly = ((mode & 0x18) == 24);
/* 174 */     mode &= 7;
/* 175 */     switch (mode) {
/*     */     case 0:
/* 177 */       this.encryptMetadata = true;
/* 178 */       this.embeddedFilesOnly = false;
/* 179 */       this.keyLength = 40;
/* 180 */       this.revision = 2;
/* 181 */       break;
/*     */     case 1:
/* 183 */       this.embeddedFilesOnly = false;
/* 184 */       if (kl > 0)
/* 185 */         this.keyLength = kl;
/*     */       else
/* 187 */         this.keyLength = 128;
/* 188 */       this.revision = 3;
/* 189 */       break;
/*     */     case 2:
/* 191 */       this.keyLength = 128;
/* 192 */       this.revision = 4;
/* 193 */       break;
/*     */     case 3:
/* 195 */       this.keyLength = 256;
/* 196 */       this.keySize = 32;
/* 197 */       this.revision = 5;
/* 198 */       break;
/*     */     default:
/* 200 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("no.valid.encryption.mode", new Object[0]));
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getCryptoMode() {
/* 205 */     return this.cryptoMode;
/*     */   }
/*     */ 
/*     */   public boolean isMetadataEncrypted() {
/* 209 */     return this.encryptMetadata;
/*     */   }
/*     */ 
/*     */   public int getPermissions() {
/* 213 */     return this.permissions;
/*     */   }
/*     */ 
/*     */   public boolean isEmbeddedFilesOnly()
/*     */   {
/* 222 */     return this.embeddedFilesOnly;
/*     */   }
/*     */ 
/*     */   private byte[] padPassword(byte[] userPassword)
/*     */   {
/* 228 */     byte[] userPad = new byte[32];
/* 229 */     if (userPassword == null) {
/* 230 */       System.arraycopy(pad, 0, userPad, 0, 32);
/*     */     } else {
/* 232 */       System.arraycopy(userPassword, 0, userPad, 0, Math.min(userPassword.length, 32));
/*     */ 
/* 234 */       if (userPassword.length < 32) {
/* 235 */         System.arraycopy(pad, 0, userPad, userPassword.length, 32 - userPassword.length);
/*     */       }
/*     */     }
/*     */ 
/* 239 */     return userPad;
/*     */   }
/*     */ 
/*     */   private byte[] computeOwnerKey(byte[] userPad, byte[] ownerPad)
/*     */   {
/* 245 */     byte[] ownerKey = new byte[32];
/* 246 */     byte[] digest = this.md5.digest(ownerPad);
/* 247 */     if ((this.revision == 3) || (this.revision == 4)) {
/* 248 */       byte[] mkey = new byte[this.keyLength / 8];
/*     */ 
/* 250 */       for (int k = 0; k < 50; k++) {
/* 251 */         this.md5.update(digest, 0, mkey.length);
/* 252 */         System.arraycopy(this.md5.digest(), 0, digest, 0, mkey.length);
/*     */       }
/* 254 */       System.arraycopy(userPad, 0, ownerKey, 0, 32);
/* 255 */       for (int i = 0; i < 20; i++) {
/* 256 */         for (int j = 0; j < mkey.length; j++)
/* 257 */           mkey[j] = ((byte)(digest[j] ^ i));
/* 258 */         this.arcfour.prepareARCFOURKey(mkey);
/* 259 */         this.arcfour.encryptARCFOUR(ownerKey);
/*     */       }
/*     */     } else {
/* 262 */       this.arcfour.prepareARCFOURKey(digest, 0, 5);
/* 263 */       this.arcfour.encryptARCFOUR(userPad, ownerKey);
/*     */     }
/* 265 */     return ownerKey;
/*     */   }
/*     */ 
/*     */   private void setupGlobalEncryptionKey(byte[] documentID, byte[] userPad, byte[] ownerKey, int permissions)
/*     */   {
/* 274 */     this.documentID = documentID;
/* 275 */     this.ownerKey = ownerKey;
/* 276 */     this.permissions = permissions;
/*     */ 
/* 278 */     this.mkey = new byte[this.keyLength / 8];
/*     */ 
/* 281 */     this.md5.reset();
/* 282 */     this.md5.update(userPad);
/* 283 */     this.md5.update(ownerKey);
/*     */ 
/* 285 */     byte[] ext = new byte[4];
/* 286 */     ext[0] = ((byte)permissions);
/* 287 */     ext[1] = ((byte)(permissions >> 8));
/* 288 */     ext[2] = ((byte)(permissions >> 16));
/* 289 */     ext[3] = ((byte)(permissions >> 24));
/* 290 */     this.md5.update(ext, 0, 4);
/* 291 */     if (documentID != null)
/* 292 */       this.md5.update(documentID);
/* 293 */     if (!this.encryptMetadata) {
/* 294 */       this.md5.update(metadataPad);
/*     */     }
/* 296 */     byte[] digest = new byte[this.mkey.length];
/* 297 */     System.arraycopy(this.md5.digest(), 0, digest, 0, this.mkey.length);
/*     */ 
/* 300 */     if ((this.revision == 3) || (this.revision == 4)) {
/* 301 */       for (int k = 0; k < 50; k++) {
/* 302 */         System.arraycopy(this.md5.digest(digest), 0, digest, 0, this.mkey.length);
/*     */       }
/*     */     }
/* 305 */     System.arraycopy(digest, 0, this.mkey, 0, this.mkey.length);
/*     */   }
/*     */ 
/*     */   private void setupUserKey()
/*     */   {
/* 314 */     if ((this.revision == 3) || (this.revision == 4)) {
/* 315 */       this.md5.update(pad);
/* 316 */       byte[] digest = this.md5.digest(this.documentID);
/* 317 */       System.arraycopy(digest, 0, this.userKey, 0, 16);
/* 318 */       for (int k = 16; k < 32; k++)
/* 319 */         this.userKey[k] = 0;
/* 320 */       for (int i = 0; i < 20; i++) {
/* 321 */         for (int j = 0; j < this.mkey.length; j++)
/* 322 */           digest[j] = ((byte)(this.mkey[j] ^ i));
/* 323 */         this.arcfour.prepareARCFOURKey(digest, 0, this.mkey.length);
/* 324 */         this.arcfour.encryptARCFOUR(this.userKey, 0, 16);
/*     */       }
/*     */     } else {
/* 327 */       this.arcfour.prepareARCFOURKey(this.mkey);
/* 328 */       this.arcfour.encryptARCFOUR(pad, this.userKey);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setupAllKeys(byte[] userPassword, byte[] ownerPassword, int permissions)
/*     */   {
/* 336 */     if ((ownerPassword == null) || (ownerPassword.length == 0))
/* 337 */       ownerPassword = this.md5.digest(createDocumentId());
/* 338 */     permissions |= ((this.revision == 3) || (this.revision == 4) || (this.revision == 5) ? -3904 : -64);
/*     */ 
/* 340 */     permissions &= -4;
/* 341 */     this.permissions = permissions;
/* 342 */     if (this.revision == 5) {
/*     */       try {
/* 344 */         if (userPassword == null)
/* 345 */           userPassword = new byte[0];
/* 346 */         this.documentID = createDocumentId();
/* 347 */         byte[] uvs = IVGenerator.getIV(8);
/* 348 */         byte[] uks = IVGenerator.getIV(8);
/* 349 */         this.key = IVGenerator.getIV(32);
/*     */ 
/* 351 */         MessageDigest md = MessageDigest.getInstance("SHA-256");
/* 352 */         md.update(userPassword, 0, Math.min(userPassword.length, 127));
/* 353 */         md.update(uvs);
/* 354 */         this.userKey = new byte[48];
/* 355 */         md.digest(this.userKey, 0, 32);
/* 356 */         System.arraycopy(uvs, 0, this.userKey, 32, 8);
/* 357 */         System.arraycopy(uks, 0, this.userKey, 40, 8);
/*     */ 
/* 359 */         md.update(userPassword, 0, Math.min(userPassword.length, 127));
/* 360 */         md.update(uks);
/* 361 */         AESCipherCBCnoPad ac = new AESCipherCBCnoPad(true, md.digest());
/* 362 */         this.ueKey = ac.processBlock(this.key, 0, this.key.length);
/*     */ 
/* 364 */         byte[] ovs = IVGenerator.getIV(8);
/* 365 */         byte[] oks = IVGenerator.getIV(8);
/* 366 */         md.update(ownerPassword, 0, Math.min(ownerPassword.length, 127));
/* 367 */         md.update(ovs);
/* 368 */         md.update(this.userKey);
/* 369 */         this.ownerKey = new byte[48];
/* 370 */         md.digest(this.ownerKey, 0, 32);
/* 371 */         System.arraycopy(ovs, 0, this.ownerKey, 32, 8);
/* 372 */         System.arraycopy(oks, 0, this.ownerKey, 40, 8);
/*     */ 
/* 374 */         md.update(ownerPassword, 0, Math.min(ownerPassword.length, 127));
/* 375 */         md.update(oks);
/* 376 */         md.update(this.userKey);
/* 377 */         ac = new AESCipherCBCnoPad(true, md.digest());
/* 378 */         this.oeKey = ac.processBlock(this.key, 0, this.key.length);
/*     */ 
/* 380 */         byte[] permsp = IVGenerator.getIV(16);
/* 381 */         permsp[0] = ((byte)permissions);
/* 382 */         permsp[1] = ((byte)(permissions >> 8));
/* 383 */         permsp[2] = ((byte)(permissions >> 16));
/* 384 */         permsp[3] = ((byte)(permissions >> 24));
/* 385 */         permsp[4] = -1;
/* 386 */         permsp[5] = -1;
/* 387 */         permsp[6] = -1;
/* 388 */         permsp[7] = -1;
/* 389 */         permsp[8] = (this.encryptMetadata ? 84 : 70);
/* 390 */         permsp[9] = 97;
/* 391 */         permsp[10] = 100;
/* 392 */         permsp[11] = 98;
/* 393 */         ac = new AESCipherCBCnoPad(true, this.key);
/* 394 */         this.perms = ac.processBlock(permsp, 0, permsp.length);
/*     */       }
/*     */       catch (Exception ex) {
/* 397 */         throw new ExceptionConverter(ex);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 403 */       byte[] userPad = padPassword(userPassword);
/* 404 */       byte[] ownerPad = padPassword(ownerPassword);
/*     */ 
/* 406 */       this.ownerKey = computeOwnerKey(userPad, ownerPad);
/* 407 */       this.documentID = createDocumentId();
/* 408 */       setupByUserPad(this.documentID, userPad, this.ownerKey, permissions);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean readKey(PdfDictionary enc, byte[] password)
/*     */     throws BadPasswordException
/*     */   {
/*     */     try
/*     */     {
/* 419 */       if (password == null)
/* 420 */         password = new byte[0];
/* 421 */       byte[] oValue = DocWriter.getISOBytes(enc.get(PdfName.O).toString());
/* 422 */       byte[] uValue = DocWriter.getISOBytes(enc.get(PdfName.U).toString());
/* 423 */       byte[] oeValue = DocWriter.getISOBytes(enc.get(PdfName.OE).toString());
/* 424 */       byte[] ueValue = DocWriter.getISOBytes(enc.get(PdfName.UE).toString());
/* 425 */       byte[] perms = DocWriter.getISOBytes(enc.get(PdfName.PERMS).toString());
/* 426 */       boolean isUserPass = false;
/* 427 */       MessageDigest md = MessageDigest.getInstance("SHA-256");
/* 428 */       md.update(password, 0, Math.min(password.length, 127));
/* 429 */       md.update(oValue, 32, 8);
/* 430 */       md.update(uValue, 0, 48);
/* 431 */       byte[] hash = md.digest();
/* 432 */       boolean isOwnerPass = compareArray(hash, oValue, 32);
/* 433 */       if (isOwnerPass) {
/* 434 */         md.update(password, 0, Math.min(password.length, 127));
/* 435 */         md.update(oValue, 40, 8);
/* 436 */         md.update(uValue, 0, 48);
/* 437 */         hash = md.digest();
/* 438 */         AESCipherCBCnoPad ac = new AESCipherCBCnoPad(false, hash);
/* 439 */         this.key = ac.processBlock(oeValue, 0, oeValue.length);
/*     */       }
/*     */       else {
/* 442 */         md.update(password, 0, Math.min(password.length, 127));
/* 443 */         md.update(uValue, 32, 8);
/* 444 */         hash = md.digest();
/* 445 */         isUserPass = compareArray(hash, uValue, 32);
/* 446 */         if (!isUserPass)
/* 447 */           throw new BadPasswordException(MessageLocalization.getComposedMessage("bad.user.password", new Object[0]));
/* 448 */         md.update(password, 0, Math.min(password.length, 127));
/* 449 */         md.update(uValue, 40, 8);
/* 450 */         hash = md.digest();
/* 451 */         AESCipherCBCnoPad ac = new AESCipherCBCnoPad(false, hash);
/* 452 */         this.key = ac.processBlock(ueValue, 0, ueValue.length);
/*     */       }
/* 454 */       AESCipherCBCnoPad ac = new AESCipherCBCnoPad(false, this.key);
/* 455 */       byte[] decPerms = ac.processBlock(perms, 0, perms.length);
/* 456 */       if ((decPerms[9] != 97) || (decPerms[10] != 100) || (decPerms[11] != 98))
/* 457 */         throw new BadPasswordException(MessageLocalization.getComposedMessage("bad.user.password", new Object[0]));
/* 458 */       this.permissions = (decPerms[0] & 0xFF | (decPerms[1] & 0xFF) << 8 | (decPerms[2] & 0xFF) << 16 | (decPerms[2] & 0xFF) << 24);
/*     */ 
/* 460 */       this.encryptMetadata = (decPerms[8] == 84);
/* 461 */       return isOwnerPass;
/*     */     }
/*     */     catch (BadPasswordException ex) {
/* 464 */       throw ex;
/*     */     }
/*     */     catch (Exception ex) {
/* 467 */       throw new ExceptionConverter(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static boolean compareArray(byte[] a, byte[] b, int len) {
/* 472 */     for (int k = 0; k < len; k++) {
/* 473 */       if (a[k] != b[k]) {
/* 474 */         return false;
/*     */       }
/*     */     }
/* 477 */     return true;
/*     */   }
/*     */ 
/*     */   public static byte[] createDocumentId() {
/*     */     MessageDigest md5;
/*     */     try {
/* 483 */       md5 = MessageDigest.getInstance("MD5");
/*     */     } catch (Exception e) {
/* 485 */       throw new ExceptionConverter(e);
/*     */     }
/* 487 */     long time = System.currentTimeMillis();
/* 488 */     long mem = Runtime.getRuntime().freeMemory();
/* 489 */     String s = time + "+" + mem + "+" + seq++;
/* 490 */     return md5.digest(s.getBytes());
/*     */   }
/*     */ 
/*     */   public void setupByUserPassword(byte[] documentID, byte[] userPassword, byte[] ownerKey, int permissions)
/*     */   {
/* 497 */     setupByUserPad(documentID, padPassword(userPassword), ownerKey, permissions);
/*     */   }
/*     */ 
/*     */   private void setupByUserPad(byte[] documentID, byte[] userPad, byte[] ownerKey, int permissions)
/*     */   {
/* 505 */     setupGlobalEncryptionKey(documentID, userPad, ownerKey, permissions);
/* 506 */     setupUserKey();
/*     */   }
/*     */ 
/*     */   public void setupByOwnerPassword(byte[] documentID, byte[] ownerPassword, byte[] userKey, byte[] ownerKey, int permissions)
/*     */   {
/* 513 */     setupByOwnerPad(documentID, padPassword(ownerPassword), userKey, ownerKey, permissions);
/*     */   }
/*     */ 
/*     */   private void setupByOwnerPad(byte[] documentID, byte[] ownerPad, byte[] userKey, byte[] ownerKey, int permissions)
/*     */   {
/* 519 */     byte[] userPad = computeOwnerKey(ownerKey, ownerPad);
/*     */ 
/* 522 */     setupGlobalEncryptionKey(documentID, userPad, ownerKey, permissions);
/*     */ 
/* 524 */     setupUserKey();
/*     */   }
/*     */ 
/*     */   public void setKey(byte[] key) {
/* 528 */     this.key = key;
/*     */   }
/*     */ 
/*     */   public void setupByEncryptionKey(byte[] key, int keylength) {
/* 532 */     this.mkey = new byte[keylength / 8];
/* 533 */     System.arraycopy(key, 0, this.mkey, 0, this.mkey.length);
/*     */   }
/*     */ 
/*     */   public void setHashKey(int number, int generation) {
/* 537 */     if (this.revision == 5)
/* 538 */       return;
/* 539 */     this.md5.reset();
/* 540 */     this.extra[0] = ((byte)number);
/* 541 */     this.extra[1] = ((byte)(number >> 8));
/* 542 */     this.extra[2] = ((byte)(number >> 16));
/* 543 */     this.extra[3] = ((byte)generation);
/* 544 */     this.extra[4] = ((byte)(generation >> 8));
/* 545 */     this.md5.update(this.mkey);
/* 546 */     this.md5.update(this.extra);
/* 547 */     if (this.revision == 4)
/* 548 */       this.md5.update(salt);
/* 549 */     this.key = this.md5.digest();
/* 550 */     this.keySize = (this.mkey.length + 5);
/* 551 */     if (this.keySize > 16)
/* 552 */       this.keySize = 16;
/*     */   }
/*     */ 
/*     */   public static PdfObject createInfoId(byte[] id, boolean modified) throws IOException {
/* 556 */     ByteBuffer buf = new ByteBuffer(90);
/* 557 */     buf.append('[').append('<');
/* 558 */     if (id.length != 16)
/* 559 */       id = createDocumentId();
/* 560 */     for (int k = 0; k < 16; k++)
/* 561 */       buf.appendHex(id[k]);
/* 562 */     buf.append('>').append('<');
/* 563 */     if (modified)
/* 564 */       id = createDocumentId();
/* 565 */     for (int k = 0; k < 16; k++)
/* 566 */       buf.appendHex(id[k]);
/* 567 */     buf.append('>').append(']');
/* 568 */     buf.close();
/* 569 */     return new PdfLiteral(buf.toByteArray());
/*     */   }
/*     */ 
/*     */   public PdfDictionary getEncryptionDictionary() {
/* 573 */     PdfDictionary dic = new PdfDictionary();
/*     */ 
/* 575 */     if (this.publicKeyHandler.getRecipientsSize() > 0) {
/* 576 */       PdfArray recipients = null;
/*     */ 
/* 578 */       dic.put(PdfName.FILTER, PdfName.PUBSEC);
/* 579 */       dic.put(PdfName.R, new PdfNumber(this.revision));
/*     */       try
/*     */       {
/* 582 */         recipients = this.publicKeyHandler.getEncodedRecipients();
/*     */       } catch (Exception f) {
/* 584 */         throw new ExceptionConverter(f);
/*     */       }
/*     */ 
/* 587 */       if (this.revision == 2) {
/* 588 */         dic.put(PdfName.V, new PdfNumber(1));
/* 589 */         dic.put(PdfName.SUBFILTER, PdfName.ADBE_PKCS7_S4);
/* 590 */         dic.put(PdfName.RECIPIENTS, recipients);
/* 591 */       } else if ((this.revision == 3) && (this.encryptMetadata)) {
/* 592 */         dic.put(PdfName.V, new PdfNumber(2));
/* 593 */         dic.put(PdfName.LENGTH, new PdfNumber(128));
/* 594 */         dic.put(PdfName.SUBFILTER, PdfName.ADBE_PKCS7_S4);
/* 595 */         dic.put(PdfName.RECIPIENTS, recipients);
/*     */       } else {
/* 597 */         if (this.revision == 5) {
/* 598 */           dic.put(PdfName.R, new PdfNumber(5));
/* 599 */           dic.put(PdfName.V, new PdfNumber(5));
/*     */         }
/*     */         else {
/* 602 */           dic.put(PdfName.R, new PdfNumber(4));
/* 603 */           dic.put(PdfName.V, new PdfNumber(4));
/*     */         }
/* 605 */         dic.put(PdfName.SUBFILTER, PdfName.ADBE_PKCS7_S5);
/*     */ 
/* 607 */         PdfDictionary stdcf = new PdfDictionary();
/* 608 */         stdcf.put(PdfName.RECIPIENTS, recipients);
/* 609 */         if (!this.encryptMetadata)
/* 610 */           stdcf.put(PdfName.ENCRYPTMETADATA, PdfBoolean.PDFFALSE);
/* 611 */         if (this.revision == 4) {
/* 612 */           stdcf.put(PdfName.CFM, PdfName.AESV2);
/* 613 */           stdcf.put(PdfName.LENGTH, new PdfNumber(128));
/*     */         }
/* 615 */         else if (this.revision == 5) {
/* 616 */           stdcf.put(PdfName.CFM, PdfName.AESV3);
/* 617 */           stdcf.put(PdfName.LENGTH, new PdfNumber(256));
/*     */         }
/*     */         else {
/* 620 */           stdcf.put(PdfName.CFM, PdfName.V2);
/* 621 */         }PdfDictionary cf = new PdfDictionary();
/* 622 */         cf.put(PdfName.DEFAULTCRYPTFILTER, stdcf);
/* 623 */         dic.put(PdfName.CF, cf);
/* 624 */         if (this.embeddedFilesOnly) {
/* 625 */           dic.put(PdfName.EFF, PdfName.DEFAULTCRYPTFILTER);
/* 626 */           dic.put(PdfName.STRF, PdfName.IDENTITY);
/* 627 */           dic.put(PdfName.STMF, PdfName.IDENTITY);
/*     */         }
/*     */         else {
/* 630 */           dic.put(PdfName.STRF, PdfName.DEFAULTCRYPTFILTER);
/* 631 */           dic.put(PdfName.STMF, PdfName.DEFAULTCRYPTFILTER);
/*     */         }
/*     */       }
/*     */ 
/* 635 */       MessageDigest md = null;
/* 636 */       byte[] encodedRecipient = null;
/*     */       try
/*     */       {
/* 639 */         if (this.revision == 5)
/* 640 */           md = MessageDigest.getInstance("SHA-256");
/*     */         else
/* 642 */           md = MessageDigest.getInstance("SHA-1");
/* 643 */         md.update(this.publicKeyHandler.getSeed());
/* 644 */         for (int i = 0; i < this.publicKeyHandler.getRecipientsSize(); i++) {
/* 645 */           encodedRecipient = this.publicKeyHandler.getEncodedRecipient(i);
/* 646 */           md.update(encodedRecipient);
/*     */         }
/* 648 */         if (!this.encryptMetadata)
/* 649 */           md.update(new byte[] { -1, -1, -1, -1 });
/*     */       }
/*     */       catch (Exception f) {
/* 652 */         throw new ExceptionConverter(f);
/*     */       }
/*     */ 
/* 655 */       byte[] mdResult = md.digest();
/*     */ 
/* 657 */       if (this.revision == 5)
/* 658 */         this.key = mdResult;
/*     */       else
/* 660 */         setupByEncryptionKey(mdResult, this.keyLength);
/*     */     } else {
/* 662 */       dic.put(PdfName.FILTER, PdfName.STANDARD);
/* 663 */       dic.put(PdfName.O, new PdfLiteral(PdfContentByte.escapeString(this.ownerKey)));
/*     */ 
/* 665 */       dic.put(PdfName.U, new PdfLiteral(PdfContentByte.escapeString(this.userKey)));
/*     */ 
/* 667 */       dic.put(PdfName.P, new PdfNumber(this.permissions));
/* 668 */       dic.put(PdfName.R, new PdfNumber(this.revision));
/*     */ 
/* 670 */       if (this.revision == 2) {
/* 671 */         dic.put(PdfName.V, new PdfNumber(1));
/* 672 */       } else if ((this.revision == 3) && (this.encryptMetadata)) {
/* 673 */         dic.put(PdfName.V, new PdfNumber(2));
/* 674 */         dic.put(PdfName.LENGTH, new PdfNumber(128));
/*     */       }
/* 677 */       else if (this.revision == 5) {
/* 678 */         if (!this.encryptMetadata)
/* 679 */           dic.put(PdfName.ENCRYPTMETADATA, PdfBoolean.PDFFALSE);
/* 680 */         dic.put(PdfName.OE, new PdfLiteral(PdfContentByte.escapeString(this.oeKey)));
/*     */ 
/* 682 */         dic.put(PdfName.UE, new PdfLiteral(PdfContentByte.escapeString(this.ueKey)));
/*     */ 
/* 684 */         dic.put(PdfName.PERMS, new PdfLiteral(PdfContentByte.escapeString(this.perms)));
/*     */ 
/* 686 */         dic.put(PdfName.V, new PdfNumber(this.revision));
/* 687 */         dic.put(PdfName.LENGTH, new PdfNumber(256));
/* 688 */         PdfDictionary stdcf = new PdfDictionary();
/* 689 */         stdcf.put(PdfName.LENGTH, new PdfNumber(32));
/* 690 */         if (this.embeddedFilesOnly) {
/* 691 */           stdcf.put(PdfName.AUTHEVENT, PdfName.EFOPEN);
/* 692 */           dic.put(PdfName.EFF, PdfName.STDCF);
/* 693 */           dic.put(PdfName.STRF, PdfName.IDENTITY);
/* 694 */           dic.put(PdfName.STMF, PdfName.IDENTITY);
/*     */         }
/*     */         else {
/* 697 */           stdcf.put(PdfName.AUTHEVENT, PdfName.DOCOPEN);
/* 698 */           dic.put(PdfName.STRF, PdfName.STDCF);
/* 699 */           dic.put(PdfName.STMF, PdfName.STDCF);
/*     */         }
/* 701 */         stdcf.put(PdfName.CFM, PdfName.AESV3);
/* 702 */         PdfDictionary cf = new PdfDictionary();
/* 703 */         cf.put(PdfName.STDCF, stdcf);
/* 704 */         dic.put(PdfName.CF, cf);
/*     */       }
/*     */       else {
/* 707 */         if (!this.encryptMetadata)
/* 708 */           dic.put(PdfName.ENCRYPTMETADATA, PdfBoolean.PDFFALSE);
/* 709 */         dic.put(PdfName.R, new PdfNumber(4));
/* 710 */         dic.put(PdfName.V, new PdfNumber(4));
/* 711 */         dic.put(PdfName.LENGTH, new PdfNumber(128));
/* 712 */         PdfDictionary stdcf = new PdfDictionary();
/* 713 */         stdcf.put(PdfName.LENGTH, new PdfNumber(16));
/* 714 */         if (this.embeddedFilesOnly) {
/* 715 */           stdcf.put(PdfName.AUTHEVENT, PdfName.EFOPEN);
/* 716 */           dic.put(PdfName.EFF, PdfName.STDCF);
/* 717 */           dic.put(PdfName.STRF, PdfName.IDENTITY);
/* 718 */           dic.put(PdfName.STMF, PdfName.IDENTITY);
/*     */         }
/*     */         else {
/* 721 */           stdcf.put(PdfName.AUTHEVENT, PdfName.DOCOPEN);
/* 722 */           dic.put(PdfName.STRF, PdfName.STDCF);
/* 723 */           dic.put(PdfName.STMF, PdfName.STDCF);
/*     */         }
/* 725 */         if (this.revision == 4)
/* 726 */           stdcf.put(PdfName.CFM, PdfName.AESV2);
/*     */         else
/* 728 */           stdcf.put(PdfName.CFM, PdfName.V2);
/* 729 */         PdfDictionary cf = new PdfDictionary();
/* 730 */         cf.put(PdfName.STDCF, stdcf);
/* 731 */         dic.put(PdfName.CF, cf);
/*     */       }
/*     */     }
/*     */ 
/* 735 */     return dic;
/*     */   }
/*     */ 
/*     */   public PdfObject getFileID(boolean modified) throws IOException {
/* 739 */     return createInfoId(this.documentID, modified);
/*     */   }
/*     */ 
/*     */   public OutputStreamEncryption getEncryptionStream(OutputStream os) {
/* 743 */     return new OutputStreamEncryption(os, this.key, 0, this.keySize, this.revision);
/*     */   }
/*     */ 
/*     */   public int calculateStreamSize(int n) {
/* 747 */     if ((this.revision == 4) || (this.revision == 5)) {
/* 748 */       return (n & 0x7FFFFFF0) + 32;
/*     */     }
/* 750 */     return n;
/*     */   }
/*     */ 
/*     */   public byte[] encryptByteArray(byte[] b) {
/*     */     try {
/* 755 */       ByteArrayOutputStream ba = new ByteArrayOutputStream();
/* 756 */       OutputStreamEncryption os2 = getEncryptionStream(ba);
/* 757 */       os2.write(b);
/* 758 */       os2.finish();
/* 759 */       return ba.toByteArray();
/*     */     } catch (IOException ex) {
/* 761 */       throw new ExceptionConverter(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public StandardDecryption getDecryptor() {
/* 766 */     return new StandardDecryption(this.key, 0, this.keySize, this.revision);
/*     */   }
/*     */ 
/*     */   public byte[] decryptByteArray(byte[] b) {
/*     */     try {
/* 771 */       ByteArrayOutputStream ba = new ByteArrayOutputStream();
/* 772 */       StandardDecryption dec = getDecryptor();
/* 773 */       byte[] b2 = dec.update(b, 0, b.length);
/* 774 */       if (b2 != null)
/* 775 */         ba.write(b2);
/* 776 */       b2 = dec.finish();
/* 777 */       if (b2 != null)
/* 778 */         ba.write(b2);
/* 779 */       return ba.toByteArray();
/*     */     } catch (IOException ex) {
/* 781 */       throw new ExceptionConverter(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addRecipient(Certificate cert, int permission) {
/* 786 */     this.documentID = createDocumentId();
/* 787 */     this.publicKeyHandler.addRecipient(new PdfPublicKeyRecipient(cert, permission));
/*     */   }
/*     */ 
/*     */   public byte[] computeUserPassword(byte[] ownerPassword)
/*     */   {
/* 792 */     byte[] userPad = computeOwnerKey(this.ownerKey, padPassword(ownerPassword));
/* 793 */     for (int i = 0; i < userPad.length; i++) {
/* 794 */       boolean match = true;
/* 795 */       for (int j = 0; j < userPad.length - i; j++) {
/* 796 */         if (userPad[(i + j)] != pad[j]) {
/* 797 */           match = false;
/* 798 */           break;
/*     */         }
/*     */       }
/* 801 */       if (match) {
/* 802 */         byte[] userPassword = new byte[i];
/* 803 */         System.arraycopy(userPad, 0, userPassword, 0, i);
/* 804 */         return userPassword;
/*     */       }
/*     */     }
/* 806 */     return userPad;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfEncryption
 * JD-Core Version:    0.6.2
 */