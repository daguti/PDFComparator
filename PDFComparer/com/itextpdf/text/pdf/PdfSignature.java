/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.pdf.security.PdfSignatureBuildProperties;
/*     */ 
/*     */ public class PdfSignature extends PdfDictionary
/*     */ {
/*     */   public PdfSignature(PdfName filter, PdfName subFilter)
/*     */   {
/*  58 */     super(PdfName.SIG);
/*  59 */     put(PdfName.FILTER, filter);
/*  60 */     put(PdfName.SUBFILTER, subFilter);
/*     */   }
/*     */ 
/*     */   public void setByteRange(int[] range) {
/*  64 */     PdfArray array = new PdfArray();
/*  65 */     for (int k = 0; k < range.length; k++)
/*  66 */       array.add(new PdfNumber(range[k]));
/*  67 */     put(PdfName.BYTERANGE, array);
/*     */   }
/*     */ 
/*     */   public void setContents(byte[] contents) {
/*  71 */     put(PdfName.CONTENTS, new PdfString(contents).setHexWriting(true));
/*     */   }
/*     */ 
/*     */   public void setCert(byte[] cert) {
/*  75 */     put(PdfName.CERT, new PdfString(cert));
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/*  79 */     put(PdfName.NAME, new PdfString(name, "UnicodeBig"));
/*     */   }
/*     */ 
/*     */   public void setDate(PdfDate date) {
/*  83 */     put(PdfName.M, date);
/*     */   }
/*     */ 
/*     */   public void setLocation(String name) {
/*  87 */     put(PdfName.LOCATION, new PdfString(name, "UnicodeBig"));
/*     */   }
/*     */ 
/*     */   public void setReason(String name) {
/*  91 */     put(PdfName.REASON, new PdfString(name, "UnicodeBig"));
/*     */   }
/*     */ 
/*     */   public void setSignatureCreator(String name)
/*     */   {
/* 101 */     if (name != null)
/* 102 */       getPdfSignatureBuildProperties().setSignatureCreator(name);
/*     */   }
/*     */ 
/*     */   PdfSignatureBuildProperties getPdfSignatureBuildProperties()
/*     */   {
/* 113 */     PdfSignatureBuildProperties buildPropDic = (PdfSignatureBuildProperties)getAsDict(PdfName.PROP_BUILD);
/* 114 */     if (buildPropDic == null) {
/* 115 */       buildPropDic = new PdfSignatureBuildProperties();
/* 116 */       put(PdfName.PROP_BUILD, buildPropDic);
/*     */     }
/* 118 */     return buildPropDic;
/*     */   }
/*     */ 
/*     */   public void setContact(String name) {
/* 122 */     put(PdfName.CONTACTINFO, new PdfString(name, "UnicodeBig"));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfSignature
 * JD-Core Version:    0.6.2
 */