/*     */ package org.apache.pdfbox.pdmodel.interactive.digitalsignature;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Calendar;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ import org.apache.pdfbox.pdfwriter.COSFilterInputStream;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ 
/*     */ public class PDSignature
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary dictionary;
/*  48 */   public static final COSName FILTER_ADOBE_PPKLITE = COSName.ADOBE_PPKLITE;
/*     */ 
/*  53 */   public static final COSName FILTER_ENTRUST_PPKEF = COSName.ENTRUST_PPKEF;
/*     */ 
/*  58 */   public static final COSName FILTER_CICI_SIGNIT = COSName.CICI_SIGNIT;
/*     */ 
/*  63 */   public static final COSName FILTER_VERISIGN_PPKVS = COSName.VERISIGN_PPKVS;
/*     */ 
/*  68 */   public static final COSName SUBFILTER_ADBE_X509_RSA_SHA1 = COSName.ADBE_X509_RSA_SHA1;
/*     */ 
/*  73 */   public static final COSName SUBFILTER_ADBE_PKCS7_DETACHED = COSName.ADBE_PKCS7_DETACHED;
/*     */ 
/*  78 */   public static final COSName SUBFILTER_ETSI_CADES_DETACHED = COSName.getPDFName("ETSI.CAdES.detached");
/*     */ 
/*  83 */   public static final COSName SUBFILTER_ADBE_PKCS7_SHA1 = COSName.ADBE_PKCS7_SHA1;
/*     */ 
/*     */   public PDSignature()
/*     */   {
/*  90 */     this.dictionary = new COSDictionary();
/*  91 */     this.dictionary.setItem(COSName.TYPE, COSName.SIG);
/*     */   }
/*     */ 
/*     */   public PDSignature(COSDictionary dict)
/*     */   {
/* 101 */     this.dictionary = dict;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 111 */     return getDictionary();
/*     */   }
/*     */ 
/*     */   public COSDictionary getDictionary()
/*     */   {
/* 121 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   public void setType(COSName type)
/*     */   {
/* 131 */     this.dictionary.setItem(COSName.TYPE, type);
/*     */   }
/*     */ 
/*     */   public void setFilter(COSName filter)
/*     */   {
/* 141 */     this.dictionary.setItem(COSName.FILTER, filter);
/*     */   }
/*     */ 
/*     */   public void setSubFilter(COSName subfilter)
/*     */   {
/* 151 */     this.dictionary.setItem(COSName.SUBFILTER, subfilter);
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 160 */     this.dictionary.setString(COSName.NAME, name);
/*     */   }
/*     */ 
/*     */   public void setLocation(String location)
/*     */   {
/* 169 */     this.dictionary.setString(COSName.LOCATION, location);
/*     */   }
/*     */ 
/*     */   public void setReason(String reason)
/*     */   {
/* 179 */     this.dictionary.setString(COSName.REASON, reason);
/*     */   }
/*     */ 
/*     */   public void setContactInfo(String contactInfo)
/*     */   {
/* 189 */     this.dictionary.setString(COSName.CONTACT_INFO, contactInfo);
/*     */   }
/*     */ 
/*     */   public void setSignDate(Calendar cal)
/*     */   {
/* 199 */     this.dictionary.setDate(COSName.M, cal);
/*     */   }
/*     */ 
/*     */   public String getFilter()
/*     */   {
/* 208 */     return this.dictionary.getNameAsString(COSName.FILTER);
/*     */   }
/*     */ 
/*     */   public String getSubFilter()
/*     */   {
/* 218 */     return this.dictionary.getNameAsString(COSName.SUBFILTER);
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 228 */     return this.dictionary.getString(COSName.NAME);
/*     */   }
/*     */ 
/*     */   public String getLocation()
/*     */   {
/* 238 */     return this.dictionary.getString(COSName.LOCATION);
/*     */   }
/*     */ 
/*     */   public String getReason()
/*     */   {
/* 248 */     return this.dictionary.getString(COSName.REASON);
/*     */   }
/*     */ 
/*     */   public String getContactInfo()
/*     */   {
/* 258 */     return this.dictionary.getString(COSName.CONTACT_INFO);
/*     */   }
/*     */ 
/*     */   public Calendar getSignDate()
/*     */   {
/*     */     try
/*     */     {
/* 270 */       return this.dictionary.getDate(COSName.M);
/*     */     }
/*     */     catch (IOException e) {
/*     */     }
/* 274 */     return null;
/*     */   }
/*     */ 
/*     */   public void setByteRange(int[] range)
/*     */   {
/* 285 */     if (range.length != 4)
/*     */     {
/* 287 */       return;
/*     */     }
/* 289 */     COSArray ary = new COSArray();
/* 290 */     for (int i : range)
/*     */     {
/* 292 */       ary.add(COSInteger.get(i));
/*     */     }
/*     */ 
/* 295 */     this.dictionary.setItem(COSName.BYTERANGE, ary);
/*     */   }
/*     */ 
/*     */   public int[] getByteRange()
/*     */   {
/* 305 */     COSArray byteRange = (COSArray)this.dictionary.getDictionaryObject(COSName.BYTERANGE);
/* 306 */     int[] ary = new int[byteRange.size()];
/* 307 */     for (int i = 0; i < ary.length; i++)
/*     */     {
/* 309 */       ary[i] = byteRange.getInt(i);
/*     */     }
/* 311 */     return ary;
/*     */   }
/*     */ 
/*     */   public byte[] getContents(InputStream pdfFile)
/*     */     throws IOException
/*     */   {
/* 323 */     int[] byteRange = getByteRange();
/* 324 */     int begin = byteRange[0] + byteRange[1] + 1;
/* 325 */     int end = byteRange[2] - begin;
/*     */ 
/* 327 */     return getContents(new COSFilterInputStream(pdfFile, new int[] { begin, end }));
/*     */   }
/*     */ 
/*     */   public byte[] getContents(byte[] pdfFile)
/*     */     throws IOException
/*     */   {
/* 339 */     int[] byteRange = getByteRange();
/* 340 */     int begin = byteRange[0] + byteRange[1] + 1;
/* 341 */     int end = byteRange[2] - begin;
/*     */ 
/* 343 */     return getContents(new COSFilterInputStream(pdfFile, new int[] { begin, end }));
/*     */   }
/*     */ 
/*     */   private byte[] getContents(COSFilterInputStream fis) throws IOException
/*     */   {
/* 348 */     ByteArrayOutputStream byteOS = new ByteArrayOutputStream(1024);
/* 349 */     byte[] buffer = new byte[1024];
/*     */     int c;
/* 351 */     while ((c = fis.read(buffer)) != -1)
/*     */     {
/* 354 */       if ((buffer[0] == 60) || (buffer[0] == 40))
/*     */       {
/* 356 */         byteOS.write(buffer, 1, c);
/*     */       }
/* 359 */       else if ((buffer[(c - 1)] == 62) || (buffer[(c - 1)] == 41))
/*     */       {
/* 361 */         byteOS.write(buffer, 0, c - 1);
/*     */       }
/*     */       else
/*     */       {
/* 365 */         byteOS.write(buffer, 0, c);
/*     */       }
/*     */     }
/* 368 */     fis.close();
/*     */ 
/* 370 */     return COSString.createFromHexString(byteOS.toString()).getBytes();
/*     */   }
/*     */ 
/*     */   public void setContents(byte[] bytes)
/*     */   {
/* 380 */     COSString string = new COSString(bytes);
/* 381 */     string.setForceHexForm(true);
/* 382 */     this.dictionary.setItem(COSName.CONTENTS, string);
/*     */   }
/*     */ 
/*     */   public byte[] getSignedContent(InputStream pdfFile)
/*     */     throws IOException
/*     */   {
/* 394 */     COSFilterInputStream fis = null;
/*     */     try
/*     */     {
/* 398 */       fis = new COSFilterInputStream(pdfFile, getByteRange());
/* 399 */       return fis.toByteArray();
/*     */     }
/*     */     finally
/*     */     {
/* 403 */       if (fis != null)
/*     */       {
/* 405 */         fis.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public byte[] getSignedContent(byte[] pdfFile)
/*     */     throws IOException
/*     */   {
/* 419 */     COSFilterInputStream fis = null;
/*     */     try
/*     */     {
/* 422 */       fis = new COSFilterInputStream(pdfFile, getByteRange());
/* 423 */       return fis.toByteArray();
/*     */     }
/*     */     finally
/*     */     {
/* 427 */       if (fis != null)
/*     */       {
/* 429 */         fis.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public PDPropBuild getPropBuild()
/*     */   {
/* 441 */     PDPropBuild propBuild = null;
/* 442 */     COSDictionary propBuildDic = (COSDictionary)this.dictionary.getDictionaryObject(COSName.PROP_BUILD);
/* 443 */     if (propBuildDic != null)
/*     */     {
/* 445 */       propBuild = new PDPropBuild(propBuildDic);
/*     */     }
/* 447 */     return propBuild;
/*     */   }
/*     */ 
/*     */   public void setPropBuild(PDPropBuild propBuild)
/*     */   {
/* 457 */     this.dictionary.setItem(COSName.PROP_BUILD, propBuild);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature
 * JD-Core Version:    0.6.2
 */