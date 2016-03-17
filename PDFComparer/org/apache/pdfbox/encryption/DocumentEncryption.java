/*     */ package org.apache.pdfbox.encryption;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.math.BigInteger;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSObject;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ import org.apache.pdfbox.exceptions.CryptographyException;
/*     */ import org.apache.pdfbox.exceptions.InvalidPasswordException;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.encryption.PDStandardEncryption;
/*     */ 
/*     */ /** @deprecated */
/*     */ public class DocumentEncryption
/*     */ {
/*  57 */   private PDDocument pdDocument = null;
/*  58 */   private COSDocument document = null;
/*     */ 
/*  60 */   private byte[] encryptionKey = null;
/*  61 */   private PDFEncryption encryption = new PDFEncryption();
/*     */ 
/*  63 */   private Set objects = new HashSet();
/*     */ 
/*  69 */   private Set potentialSignatures = new HashSet();
/*     */ 
/*     */   public DocumentEncryption(PDDocument doc)
/*     */   {
/*  78 */     this.pdDocument = doc;
/*  79 */     this.document = doc.getDocument();
/*     */   }
/*     */ 
/*     */   public DocumentEncryption(COSDocument doc)
/*     */   {
/*  89 */     this.pdDocument = new PDDocument(doc);
/*  90 */     this.document = doc;
/*     */   }
/*     */ 
/*     */   public void initForEncryption()
/*     */     throws CryptographyException, IOException
/*     */   {
/* 103 */     String ownerPassword = this.pdDocument.getOwnerPasswordForEncryption();
/* 104 */     String userPassword = this.pdDocument.getUserPasswordForEncryption();
/* 105 */     if (ownerPassword == null)
/*     */     {
/* 107 */       ownerPassword = "";
/*     */     }
/* 109 */     if (userPassword == null)
/*     */     {
/* 111 */       userPassword = "";
/*     */     }
/* 113 */     PDStandardEncryption encParameters = (PDStandardEncryption)this.pdDocument.getEncryptionDictionary();
/* 114 */     int permissionInt = encParameters.getPermissions();
/* 115 */     int revision = encParameters.getRevision();
/* 116 */     int length = encParameters.getLength() / 8;
/* 117 */     COSArray idArray = this.document.getDocumentID();
/*     */ 
/* 121 */     if ((idArray == null) || (idArray.size() < 2))
/*     */     {
/* 123 */       idArray = new COSArray();
/*     */       try
/*     */       {
/* 126 */         MessageDigest md = MessageDigest.getInstance("MD5");
/* 127 */         BigInteger time = BigInteger.valueOf(System.currentTimeMillis());
/* 128 */         md.update(time.toByteArray());
/* 129 */         md.update(ownerPassword.getBytes("ISO-8859-1"));
/* 130 */         md.update(userPassword.getBytes("ISO-8859-1"));
/* 131 */         md.update(this.document.toString().getBytes());
/* 132 */         byte[] id = md.digest(toString().getBytes("ISO-8859-1"));
/* 133 */         COSString idString = new COSString();
/* 134 */         idString.append(id);
/* 135 */         idArray.add(idString);
/* 136 */         idArray.add(idString);
/* 137 */         this.document.setDocumentID(idArray);
/*     */       }
/*     */       catch (NoSuchAlgorithmException e)
/*     */       {
/* 141 */         throw new CryptographyException(e);
/*     */       }
/*     */     }
/*     */ 
/* 145 */     COSString id = (COSString)idArray.getObject(0);
/* 146 */     this.encryption = new PDFEncryption();
/*     */ 
/* 148 */     byte[] o = this.encryption.computeOwnerPassword(ownerPassword.getBytes("ISO-8859-1"), userPassword.getBytes("ISO-8859-1"), revision, length);
/*     */ 
/* 152 */     byte[] u = this.encryption.computeUserPassword(userPassword.getBytes("ISO-8859-1"), o, permissionInt, id.getBytes(), revision, length);
/*     */ 
/* 156 */     this.encryptionKey = this.encryption.computeEncryptedKey(userPassword.getBytes("ISO-8859-1"), o, permissionInt, id.getBytes(), revision, length);
/*     */ 
/* 159 */     encParameters.setOwnerKey(o);
/* 160 */     encParameters.setUserKey(u);
/*     */ 
/* 162 */     this.document.setEncryptionDictionary(encParameters.getCOSDictionary());
/*     */   }
/*     */ 
/*     */   public void decryptDocument(String password)
/*     */     throws CryptographyException, IOException, InvalidPasswordException
/*     */   {
/* 179 */     if (password == null)
/*     */     {
/* 181 */       password = "";
/*     */     }
/*     */ 
/* 184 */     PDStandardEncryption encParameters = (PDStandardEncryption)this.pdDocument.getEncryptionDictionary();
/*     */ 
/* 186 */     int permissions = encParameters.getPermissions();
/* 187 */     int revision = encParameters.getRevision();
/* 188 */     int length = encParameters.getLength() / 8;
/*     */ 
/* 190 */     COSString id = (COSString)this.document.getDocumentID().getObject(0);
/* 191 */     byte[] u = encParameters.getUserKey();
/* 192 */     byte[] o = encParameters.getOwnerKey();
/*     */ 
/* 194 */     boolean isUserPassword = this.encryption.isUserPassword(password.getBytes("ISO-8859-1"), u, o, permissions, id.getBytes(), revision, length);
/*     */ 
/* 197 */     boolean isOwnerPassword = this.encryption.isOwnerPassword(password.getBytes("ISO-8859-1"), u, o, permissions, id.getBytes(), revision, length);
/*     */ 
/* 201 */     if (isUserPassword)
/*     */     {
/* 203 */       this.encryptionKey = this.encryption.computeEncryptedKey(password.getBytes("ISO-8859-1"), o, permissions, id.getBytes(), revision, length);
/*     */     }
/* 208 */     else if (isOwnerPassword)
/*     */     {
/* 210 */       byte[] computedUserPassword = this.encryption.getUserPassword(password.getBytes("ISO-8859-1"), o, revision, length);
/*     */ 
/* 216 */       this.encryptionKey = this.encryption.computeEncryptedKey(computedUserPassword, o, permissions, id.getBytes(), revision, length);
/*     */     }
/*     */     else
/*     */     {
/* 223 */       throw new InvalidPasswordException("Error: The supplied password does not match either the owner or user password in the document.");
/*     */     }
/*     */ 
/* 227 */     COSDictionary trailer = this.document.getTrailer();
/* 228 */     COSArray fields = (COSArray)trailer.getObjectFromPath("Root/AcroForm/Fields");
/*     */ 
/* 232 */     if (fields != null)
/*     */     {
/* 234 */       for (int i = 0; i < fields.size(); i++)
/*     */       {
/* 236 */         COSDictionary field = (COSDictionary)fields.getObject(i);
/* 237 */         addDictionaryAndSubDictionary(this.potentialSignatures, field);
/*     */       }
/*     */     }
/*     */ 
/* 241 */     List allObjects = this.document.getObjects();
/* 242 */     Iterator objectIter = allObjects.iterator();
/* 243 */     while (objectIter.hasNext())
/*     */     {
/* 245 */       decryptObject((COSObject)objectIter.next());
/*     */     }
/* 247 */     this.document.setEncryptionDictionary(null);
/*     */   }
/*     */ 
/*     */   private void addDictionaryAndSubDictionary(Set set, COSDictionary dic)
/*     */   {
/* 252 */     set.add(dic);
/* 253 */     COSArray kids = (COSArray)dic.getDictionaryObject(COSName.KIDS);
/* 254 */     for (int i = 0; (kids != null) && (i < kids.size()); i++)
/*     */     {
/* 256 */       addDictionaryAndSubDictionary(set, (COSDictionary)kids.getObject(i));
/*     */     }
/* 258 */     COSBase value = dic.getDictionaryObject(COSName.V);
/* 259 */     if ((value instanceof COSDictionary))
/*     */     {
/* 261 */       addDictionaryAndSubDictionary(set, (COSDictionary)value);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void decryptObject(COSObject object)
/*     */     throws CryptographyException, IOException
/*     */   {
/* 276 */     long objNum = object.getObjectNumber().intValue();
/* 277 */     long genNum = object.getGenerationNumber().intValue();
/* 278 */     COSBase base = object.getObject();
/* 279 */     decrypt(base, objNum, genNum);
/*     */   }
/*     */ 
/*     */   public void decrypt(Object obj, long objNum, long genNum)
/*     */     throws CryptographyException, IOException
/*     */   {
/* 295 */     if (!this.objects.contains(obj))
/*     */     {
/* 297 */       this.objects.add(obj);
/*     */ 
/* 299 */       if ((obj instanceof COSString))
/*     */       {
/* 301 */         decryptString((COSString)obj, objNum, genNum);
/*     */       }
/* 303 */       else if ((obj instanceof COSStream))
/*     */       {
/* 305 */         decryptStream((COSStream)obj, objNum, genNum);
/*     */       }
/* 307 */       else if ((obj instanceof COSDictionary))
/*     */       {
/* 309 */         decryptDictionary((COSDictionary)obj, objNum, genNum);
/*     */       }
/* 311 */       else if ((obj instanceof COSArray))
/*     */       {
/* 313 */         decryptArray((COSArray)obj, objNum, genNum);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void decryptStream(COSStream stream, long objNum, long genNum)
/*     */     throws CryptographyException, IOException
/*     */   {
/* 331 */     decryptDictionary(stream, objNum, genNum);
/* 332 */     InputStream encryptedStream = stream.getFilteredStream();
/* 333 */     this.encryption.encryptData(objNum, genNum, this.encryptionKey, encryptedStream, stream.createFilteredStream());
/*     */   }
/*     */ 
/*     */   private void decryptDictionary(COSDictionary dictionary, long objNum, long genNum)
/*     */     throws CryptographyException, IOException
/*     */   {
/* 353 */     for (Map.Entry entry : dictionary.entrySet())
/*     */     {
/* 357 */       if ((!((COSName)entry.getKey()).getName().equals("Contents")) || (!(entry.getValue() instanceof COSString)) || (!this.potentialSignatures.contains(dictionary)))
/*     */       {
/* 361 */         decrypt(entry.getValue(), objNum, genNum);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void decryptString(COSString string, long objNum, long genNum)
/*     */     throws CryptographyException, IOException
/*     */   {
/* 379 */     ByteArrayInputStream data = new ByteArrayInputStream(string.getBytes());
/* 380 */     ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/* 381 */     this.encryption.encryptData(objNum, genNum, this.encryptionKey, data, buffer);
/*     */ 
/* 386 */     string.reset();
/* 387 */     string.append(buffer.toByteArray());
/*     */   }
/*     */ 
/*     */   private void decryptArray(COSArray array, long objNum, long genNum)
/*     */     throws CryptographyException, IOException
/*     */   {
/* 403 */     for (int i = 0; i < array.size(); i++)
/*     */     {
/* 405 */       decrypt(array.get(i), objNum, genNum);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.encryption.DocumentEncryption
 * JD-Core Version:    0.6.2
 */