/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.security.PrivateKey;
/*     */ import java.util.HashMap;
/*     */ import org.bouncycastle.cms.CMSException;
/*     */ import org.bouncycastle.cms.Recipient;
/*     */ import org.bouncycastle.cms.RecipientInformation;
/*     */ import org.bouncycastle.cms.jcajce.JceKeyTransEnvelopedRecipient;
/*     */ 
/*     */ public final class PdfEncryptor
/*     */ {
/*     */   public static void encrypt(PdfReader reader, OutputStream os, byte[] userPassword, byte[] ownerPassword, int permissions, boolean strength128Bits)
/*     */     throws DocumentException, IOException
/*     */   {
/*  84 */     PdfStamper stamper = new PdfStamper(reader, os);
/*  85 */     stamper.setEncryption(userPassword, ownerPassword, permissions, strength128Bits);
/*  86 */     stamper.close();
/*     */   }
/*     */ 
/*     */   public static void encrypt(PdfReader reader, OutputStream os, byte[] userPassword, byte[] ownerPassword, int permissions, boolean strength128Bits, HashMap<String, String> newInfo)
/*     */     throws DocumentException, IOException
/*     */   {
/* 110 */     PdfStamper stamper = new PdfStamper(reader, os);
/* 111 */     stamper.setEncryption(userPassword, ownerPassword, permissions, strength128Bits);
/* 112 */     stamper.setMoreInfo(newInfo);
/* 113 */     stamper.close();
/*     */   }
/*     */ 
/*     */   public static void encrypt(PdfReader reader, OutputStream os, boolean strength, String userPassword, String ownerPassword, int permissions)
/*     */     throws DocumentException, IOException
/*     */   {
/* 132 */     PdfStamper stamper = new PdfStamper(reader, os);
/* 133 */     stamper.setEncryption(strength, userPassword, ownerPassword, permissions);
/* 134 */     stamper.close();
/*     */   }
/*     */ 
/*     */   public static void encrypt(PdfReader reader, OutputStream os, boolean strength, String userPassword, String ownerPassword, int permissions, HashMap<String, String> newInfo)
/*     */     throws DocumentException, IOException
/*     */   {
/* 158 */     PdfStamper stamper = new PdfStamper(reader, os);
/* 159 */     stamper.setEncryption(strength, userPassword, ownerPassword, permissions);
/* 160 */     stamper.setMoreInfo(newInfo);
/* 161 */     stamper.close();
/*     */   }
/*     */ 
/*     */   public static void encrypt(PdfReader reader, OutputStream os, int type, String userPassword, String ownerPassword, int permissions, HashMap<String, String> newInfo)
/*     */     throws DocumentException, IOException
/*     */   {
/* 187 */     PdfStamper stamper = new PdfStamper(reader, os);
/* 188 */     stamper.setEncryption(type, userPassword, ownerPassword, permissions);
/* 189 */     stamper.setMoreInfo(newInfo);
/* 190 */     stamper.close();
/*     */   }
/*     */ 
/*     */   public static void encrypt(PdfReader reader, OutputStream os, int type, String userPassword, String ownerPassword, int permissions)
/*     */     throws DocumentException, IOException
/*     */   {
/* 212 */     PdfStamper stamper = new PdfStamper(reader, os);
/* 213 */     stamper.setEncryption(type, userPassword, ownerPassword, permissions);
/* 214 */     stamper.close();
/*     */   }
/*     */ 
/*     */   public static String getPermissionsVerbose(int permissions)
/*     */   {
/* 223 */     StringBuffer buf = new StringBuffer("Allowed:");
/* 224 */     if ((0x804 & permissions) == 2052) buf.append(" Printing");
/* 225 */     if ((0x8 & permissions) == 8) buf.append(" Modify contents");
/* 226 */     if ((0x10 & permissions) == 16) buf.append(" Copy");
/* 227 */     if ((0x20 & permissions) == 32) buf.append(" Modify annotations");
/* 228 */     if ((0x100 & permissions) == 256) buf.append(" Fill in");
/* 229 */     if ((0x200 & permissions) == 512) buf.append(" Screen readers");
/* 230 */     if ((0x400 & permissions) == 1024) buf.append(" Assembly");
/* 231 */     if ((0x4 & permissions) == 4) buf.append(" Degraded printing");
/* 232 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public static boolean isPrintingAllowed(int permissions)
/*     */   {
/* 243 */     return (0x804 & permissions) == 2052;
/*     */   }
/*     */ 
/*     */   public static boolean isModifyContentsAllowed(int permissions)
/*     */   {
/* 254 */     return (0x8 & permissions) == 8;
/*     */   }
/*     */ 
/*     */   public static boolean isCopyAllowed(int permissions)
/*     */   {
/* 265 */     return (0x10 & permissions) == 16;
/*     */   }
/*     */ 
/*     */   public static boolean isModifyAnnotationsAllowed(int permissions)
/*     */   {
/* 276 */     return (0x20 & permissions) == 32;
/*     */   }
/*     */ 
/*     */   public static boolean isFillInAllowed(int permissions)
/*     */   {
/* 287 */     return (0x100 & permissions) == 256;
/*     */   }
/*     */ 
/*     */   public static boolean isScreenReadersAllowed(int permissions)
/*     */   {
/* 298 */     return (0x200 & permissions) == 512;
/*     */   }
/*     */ 
/*     */   public static boolean isAssemblyAllowed(int permissions)
/*     */   {
/* 309 */     return (0x400 & permissions) == 1024;
/*     */   }
/*     */ 
/*     */   public static boolean isDegradedPrintingAllowed(int permissions)
/*     */   {
/* 320 */     return (0x4 & permissions) == 4;
/*     */   }
/*     */ 
/*     */   public static byte[] getContent(RecipientInformation recipientInfo, PrivateKey certificateKey, String certificateKeyProvider)
/*     */     throws CMSException
/*     */   {
/* 332 */     Recipient jceKeyTransRecipient = new JceKeyTransEnvelopedRecipient(certificateKey).setProvider(certificateKeyProvider);
/* 333 */     return recipientInfo.getContent(jceKeyTransRecipient);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfEncryptor
 * JD-Core Version:    0.6.2
 */