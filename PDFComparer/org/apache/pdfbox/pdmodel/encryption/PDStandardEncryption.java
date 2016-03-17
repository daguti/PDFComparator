/*     */ package org.apache.pdfbox.pdmodel.encryption;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ 
/*     */ /** @deprecated */
/*     */ public class PDStandardEncryption extends PDEncryptionDictionary
/*     */ {
/*     */   public static final String FILTER_NAME = "Standard";
/*     */   public static final int DEFAULT_REVISION = 3;
/*     */   public static final int REVISION2 = 2;
/*     */   public static final int REVISION3 = 3;
/*     */   public static final int REVISION4 = 4;
/*     */   public static final int DEFAULT_PERMISSIONS = -4;
/*     */   private static final int PRINT_BIT = 3;
/*     */   private static final int MODIFICATION_BIT = 4;
/*     */   private static final int EXTRACT_BIT = 5;
/*     */   private static final int MODIFY_ANNOTATIONS_BIT = 6;
/*     */   private static final int FILL_IN_FORM_BIT = 9;
/*     */   private static final int EXTRACT_FOR_ACCESSIBILITY_BIT = 10;
/*     */   private static final int ASSEMBLE_DOCUMENT_BIT = 11;
/*     */   private static final int DEGRADED_PRINT_BIT = 12;
/*     */ 
/*     */   public PDStandardEncryption()
/*     */   {
/*  82 */     this.encryptionDictionary.setItem(COSName.FILTER, COSName.getPDFName("Standard"));
/*  83 */     setVersion(1);
/*  84 */     setRevision(2);
/*  85 */     setPermissions(-4);
/*     */   }
/*     */ 
/*     */   public PDStandardEncryption(COSDictionary dict)
/*     */   {
/*  95 */     super(dict);
/*     */   }
/*     */ 
/*     */   public int getRevision()
/*     */   {
/* 106 */     int revision = 0;
/* 107 */     COSNumber cosRevision = (COSNumber)this.encryptionDictionary.getDictionaryObject(COSName.getPDFName("R"));
/* 108 */     if (cosRevision != null)
/*     */     {
/* 110 */       revision = cosRevision.intValue();
/*     */     }
/* 112 */     return revision;
/*     */   }
/*     */ 
/*     */   public void setRevision(int revision)
/*     */   {
/* 126 */     this.encryptionDictionary.setInt(COSName.getPDFName("R"), revision);
/*     */   }
/*     */ 
/*     */   public byte[] getOwnerKey()
/*     */   {
/* 136 */     byte[] o = null;
/* 137 */     COSString owner = (COSString)this.encryptionDictionary.getDictionaryObject(COSName.getPDFName("O"));
/* 138 */     if (owner != null)
/*     */     {
/* 140 */       o = owner.getBytes();
/*     */     }
/* 142 */     return o;
/*     */   }
/*     */ 
/*     */   public void setOwnerKey(byte[] o)
/*     */     throws IOException
/*     */   {
/* 154 */     COSString owner = new COSString();
/* 155 */     owner.append(o);
/* 156 */     this.encryptionDictionary.setItem(COSName.getPDFName("O"), owner);
/*     */   }
/*     */ 
/*     */   public byte[] getUserKey()
/*     */   {
/* 166 */     byte[] u = null;
/* 167 */     COSString user = (COSString)this.encryptionDictionary.getDictionaryObject(COSName.getPDFName("U"));
/* 168 */     if (user != null)
/*     */     {
/* 170 */       u = user.getBytes();
/*     */     }
/* 172 */     return u;
/*     */   }
/*     */ 
/*     */   public void setUserKey(byte[] u)
/*     */     throws IOException
/*     */   {
/* 184 */     COSString user = new COSString();
/* 185 */     user.append(u);
/* 186 */     this.encryptionDictionary.setItem(COSName.getPDFName("U"), user);
/*     */   }
/*     */ 
/*     */   public int getPermissions()
/*     */   {
/* 196 */     int permissions = 0;
/* 197 */     COSInteger p = (COSInteger)this.encryptionDictionary.getDictionaryObject(COSName.getPDFName("P"));
/* 198 */     if (p != null)
/*     */     {
/* 200 */       permissions = p.intValue();
/*     */     }
/* 202 */     return permissions;
/*     */   }
/*     */ 
/*     */   public void setPermissions(int p)
/*     */   {
/* 212 */     this.encryptionDictionary.setInt(COSName.getPDFName("P"), p);
/*     */   }
/*     */ 
/*     */   private boolean isPermissionBitOn(int bit)
/*     */   {
/* 217 */     return (getPermissions() & 1 << bit - 1) != 0;
/*     */   }
/*     */ 
/*     */   private boolean setPermissionBit(int bit, boolean value)
/*     */   {
/* 222 */     int permissions = getPermissions();
/* 223 */     if (value)
/*     */     {
/* 225 */       permissions |= 1 << bit - 1;
/*     */     }
/*     */     else
/*     */     {
/* 229 */       permissions &= (0xFFFFFFFF ^ 1 << bit - 1);
/*     */     }
/* 231 */     setPermissions(permissions);
/*     */ 
/* 233 */     return (getPermissions() & 1 << bit - 1) != 0;
/*     */   }
/*     */ 
/*     */   public boolean canPrint()
/*     */   {
/* 243 */     return isPermissionBitOn(3);
/*     */   }
/*     */ 
/*     */   public void setCanPrint(boolean allowPrinting)
/*     */   {
/* 253 */     setPermissionBit(3, allowPrinting);
/*     */   }
/*     */ 
/*     */   public boolean canModify()
/*     */   {
/* 263 */     return isPermissionBitOn(4);
/*     */   }
/*     */ 
/*     */   public void setCanModify(boolean allowModifications)
/*     */   {
/* 273 */     setPermissionBit(4, allowModifications);
/*     */   }
/*     */ 
/*     */   public boolean canExtractContent()
/*     */   {
/* 284 */     return isPermissionBitOn(5);
/*     */   }
/*     */ 
/*     */   public void setCanExtractContent(boolean allowExtraction)
/*     */   {
/* 295 */     setPermissionBit(5, allowExtraction);
/*     */   }
/*     */ 
/*     */   public boolean canModifyAnnotations()
/*     */   {
/* 305 */     return isPermissionBitOn(6);
/*     */   }
/*     */ 
/*     */   public void setCanModifyAnnotations(boolean allowAnnotationModification)
/*     */   {
/* 315 */     setPermissionBit(6, allowAnnotationModification);
/*     */   }
/*     */ 
/*     */   public boolean canFillInForm()
/*     */   {
/* 325 */     return isPermissionBitOn(9);
/*     */   }
/*     */ 
/*     */   public void setCanFillInForm(boolean allowFillingInForm)
/*     */   {
/* 335 */     setPermissionBit(9, allowFillingInForm);
/*     */   }
/*     */ 
/*     */   public boolean canExtractForAccessibility()
/*     */   {
/* 347 */     return isPermissionBitOn(10);
/*     */   }
/*     */ 
/*     */   public void setCanExtractForAccessibility(boolean allowExtraction)
/*     */   {
/* 358 */     setPermissionBit(10, allowExtraction);
/*     */   }
/*     */ 
/*     */   public boolean canAssembleDocument()
/*     */   {
/* 369 */     return isPermissionBitOn(11);
/*     */   }
/*     */ 
/*     */   public void setCanAssembleDocument(boolean allowAssembly)
/*     */   {
/* 379 */     setPermissionBit(11, allowAssembly);
/*     */   }
/*     */ 
/*     */   public boolean canPrintDegraded()
/*     */   {
/* 390 */     return isPermissionBitOn(12);
/*     */   }
/*     */ 
/*     */   public void setCanPrintDegraded(boolean allowAssembly)
/*     */   {
/* 401 */     setPermissionBit(12, allowAssembly);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.encryption.PDStandardEncryption
 * JD-Core Version:    0.6.2
 */