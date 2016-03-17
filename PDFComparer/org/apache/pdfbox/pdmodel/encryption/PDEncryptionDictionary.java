/*     */ package org.apache.pdfbox.pdmodel.encryption;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSBoolean;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ 
/*     */ public class PDEncryptionDictionary
/*     */ {
/*     */   public static final int VERSION0_UNDOCUMENTED_UNSUPPORTED = 0;
/*     */   public static final int VERSION1_40_BIT_ALGORITHM = 1;
/*     */   public static final int VERSION2_VARIABLE_LENGTH_ALGORITHM = 2;
/*     */   public static final int VERSION3_UNPUBLISHED_ALGORITHM = 3;
/*     */   public static final int VERSION4_SECURITY_HANDLER = 4;
/*     */   public static final String DEFAULT_NAME = "Standard";
/*     */   public static final int DEFAULT_LENGTH = 40;
/*     */   public static final int DEFAULT_VERSION = 0;
/*  83 */   protected COSDictionary encryptionDictionary = null;
/*     */ 
/*     */   public PDEncryptionDictionary()
/*     */   {
/*  90 */     this.encryptionDictionary = new COSDictionary();
/*     */   }
/*     */ 
/*     */   public PDEncryptionDictionary(COSDictionary d)
/*     */   {
/*  99 */     this.encryptionDictionary = d;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/* 109 */     return this.encryptionDictionary;
/*     */   }
/*     */ 
/*     */   public void setFilter(String filter)
/*     */   {
/* 119 */     this.encryptionDictionary.setItem(COSName.FILTER, COSName.getPDFName(filter));
/*     */   }
/*     */ 
/*     */   public String getFilter()
/*     */   {
/* 129 */     return this.encryptionDictionary.getNameAsString(COSName.FILTER);
/*     */   }
/*     */ 
/*     */   public String getSubFilter()
/*     */   {
/* 139 */     return this.encryptionDictionary.getNameAsString(COSName.SUB_FILTER);
/*     */   }
/*     */ 
/*     */   public void setSubFilter(String subfilter)
/*     */   {
/* 149 */     this.encryptionDictionary.setName(COSName.SUB_FILTER, subfilter);
/*     */   }
/*     */ 
/*     */   public void setVersion(int version)
/*     */   {
/* 162 */     this.encryptionDictionary.setInt(COSName.V, version);
/*     */   }
/*     */ 
/*     */   public int getVersion()
/*     */   {
/* 173 */     return this.encryptionDictionary.getInt(COSName.V, 0);
/*     */   }
/*     */ 
/*     */   public void setLength(int length)
/*     */   {
/* 183 */     this.encryptionDictionary.setInt(COSName.LENGTH, length);
/*     */   }
/*     */ 
/*     */   public int getLength()
/*     */   {
/* 194 */     return this.encryptionDictionary.getInt(COSName.LENGTH, 40);
/*     */   }
/*     */ 
/*     */   public void setRevision(int revision)
/*     */   {
/* 208 */     this.encryptionDictionary.setInt(COSName.R, revision);
/*     */   }
/*     */ 
/*     */   public int getRevision()
/*     */   {
/* 219 */     return this.encryptionDictionary.getInt(COSName.R, 0);
/*     */   }
/*     */ 
/*     */   public void setOwnerKey(byte[] o)
/*     */     throws IOException
/*     */   {
/* 231 */     COSString owner = new COSString();
/* 232 */     owner.append(o);
/* 233 */     this.encryptionDictionary.setItem(COSName.O, owner);
/*     */   }
/*     */ 
/*     */   public byte[] getOwnerKey()
/*     */     throws IOException
/*     */   {
/* 245 */     byte[] o = null;
/* 246 */     COSString owner = (COSString)this.encryptionDictionary.getDictionaryObject(COSName.O);
/* 247 */     if (owner != null)
/*     */     {
/* 249 */       o = owner.getBytes();
/*     */     }
/* 251 */     return o;
/*     */   }
/*     */ 
/*     */   public void setUserKey(byte[] u)
/*     */     throws IOException
/*     */   {
/* 263 */     COSString user = new COSString();
/* 264 */     user.append(u);
/* 265 */     this.encryptionDictionary.setItem(COSName.U, user);
/*     */   }
/*     */ 
/*     */   public byte[] getUserKey()
/*     */     throws IOException
/*     */   {
/* 277 */     byte[] u = null;
/* 278 */     COSString user = (COSString)this.encryptionDictionary.getDictionaryObject(COSName.U);
/* 279 */     if (user != null)
/*     */     {
/* 281 */       u = user.getBytes();
/*     */     }
/* 283 */     return u;
/*     */   }
/*     */ 
/*     */   public void setPermissions(int permissions)
/*     */   {
/* 293 */     this.encryptionDictionary.setInt(COSName.P, permissions);
/*     */   }
/*     */ 
/*     */   public int getPermissions()
/*     */   {
/* 303 */     return this.encryptionDictionary.getInt(COSName.P, 0);
/*     */   }
/*     */ 
/*     */   public boolean isEncryptMetaData()
/*     */   {
/* 314 */     boolean encryptMetaData = true;
/*     */ 
/* 316 */     COSBase value = this.encryptionDictionary.getDictionaryObject(COSName.ENCRYPT_META_DATA);
/*     */ 
/* 318 */     if ((value instanceof COSBoolean)) {
/* 319 */       encryptMetaData = ((COSBoolean)value).getValue();
/*     */     }
/*     */ 
/* 322 */     return encryptMetaData;
/*     */   }
/*     */ 
/*     */   public void setRecipients(byte[][] recipients)
/*     */     throws IOException
/*     */   {
/* 333 */     COSArray array = new COSArray();
/* 334 */     for (int i = 0; i < recipients.length; i++)
/*     */     {
/* 336 */       COSString recip = new COSString();
/* 337 */       recip.append(recipients[i]);
/* 338 */       recip.setForceLiteralForm(true);
/* 339 */       array.add(recip);
/*     */     }
/* 341 */     this.encryptionDictionary.setItem(COSName.RECIPIENTS, array);
/*     */   }
/*     */ 
/*     */   public int getRecipientsLength()
/*     */   {
/* 351 */     COSArray array = (COSArray)this.encryptionDictionary.getItem(COSName.RECIPIENTS);
/* 352 */     return array.size();
/*     */   }
/*     */ 
/*     */   public COSString getRecipientStringAt(int i)
/*     */   {
/* 364 */     COSArray array = (COSArray)this.encryptionDictionary.getItem(COSName.RECIPIENTS);
/* 365 */     return (COSString)array.get(i);
/*     */   }
/*     */ 
/*     */   public PDCryptFilterDictionary getStdCryptFilterDictionary()
/*     */   {
/* 375 */     return getCryptFilterDictionary(COSName.STD_CF);
/*     */   }
/*     */ 
/*     */   public PDCryptFilterDictionary getCryptFilterDictionary(COSName cryptFilterName)
/*     */   {
/* 387 */     COSDictionary cryptFilterDictionary = (COSDictionary)this.encryptionDictionary.getDictionaryObject(COSName.CF);
/* 388 */     if (cryptFilterDictionary != null)
/*     */     {
/* 390 */       COSDictionary stdCryptFilterDictionary = (COSDictionary)cryptFilterDictionary.getDictionaryObject(cryptFilterName);
/* 391 */       if (stdCryptFilterDictionary != null)
/*     */       {
/* 393 */         return new PDCryptFilterDictionary(stdCryptFilterDictionary);
/*     */       }
/*     */     }
/* 396 */     return null;
/*     */   }
/*     */ 
/*     */   public COSName getStreamFilterName()
/*     */   {
/* 407 */     COSName stmF = (COSName)this.encryptionDictionary.getDictionaryObject(COSName.STM_F);
/* 408 */     if (stmF == null)
/*     */     {
/* 410 */       stmF = COSName.IDENTITY;
/*     */     }
/* 412 */     return stmF;
/*     */   }
/*     */ 
/*     */   public COSName getStringFilterName()
/*     */   {
/* 423 */     COSName strF = (COSName)this.encryptionDictionary.getDictionaryObject(COSName.STR_F);
/* 424 */     if (strF == null)
/*     */     {
/* 426 */       strF = COSName.IDENTITY;
/*     */     }
/* 428 */     return strF;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.encryption.PDEncryptionDictionary
 * JD-Core Version:    0.6.2
 */