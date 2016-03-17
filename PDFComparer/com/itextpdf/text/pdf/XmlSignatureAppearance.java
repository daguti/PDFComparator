/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.pdf.security.XmlLocator;
/*     */ import com.itextpdf.text.pdf.security.XpathConstructor;
/*     */ import java.io.IOException;
/*     */ import java.security.cert.Certificate;
/*     */ import java.util.Calendar;
/*     */ 
/*     */ public class XmlSignatureAppearance
/*     */ {
/*     */   private PdfStamperImp writer;
/*     */   private PdfStamper stamper;
/*     */   private Certificate signCertificate;
/*     */   private XmlLocator xmlLocator;
/*     */   private XpathConstructor xpathConstructor;
/*     */   private Calendar signDate;
/*     */   private String description;
/*  80 */   private String mimeType = "text/xml";
/*     */ 
/*     */   XmlSignatureAppearance(PdfStamperImp writer)
/*     */   {
/*  64 */     this.writer = writer;
/*     */   }
/*     */ 
/*     */   public PdfStamperImp getWriter()
/*     */   {
/*  83 */     return this.writer;
/*     */   }
/*     */ 
/*     */   public PdfStamper getStamper() {
/*  87 */     return this.stamper;
/*     */   }
/*     */ 
/*     */   public void setStamper(PdfStamper stamper) {
/*  91 */     this.stamper = stamper;
/*     */   }
/*     */ 
/*     */   public void setCertificate(Certificate signCertificate)
/*     */   {
/* 100 */     this.signCertificate = signCertificate;
/*     */   }
/*     */ 
/*     */   public Certificate getCertificate() {
/* 104 */     return this.signCertificate;
/*     */   }
/*     */ 
/*     */   public void setDescription(String description) {
/* 108 */     this.description = description;
/*     */   }
/*     */ 
/*     */   public String getDescription() {
/* 112 */     return this.description;
/*     */   }
/*     */ 
/*     */   public String getMimeType() {
/* 116 */     return this.mimeType;
/*     */   }
/*     */ 
/*     */   public void setMimeType(String mimeType) {
/* 120 */     this.mimeType = mimeType;
/*     */   }
/*     */ 
/*     */   public Calendar getSignDate()
/*     */   {
/* 128 */     if (this.signDate == null)
/* 129 */       this.signDate = Calendar.getInstance();
/* 130 */     return this.signDate;
/*     */   }
/*     */ 
/*     */   public void setSignDate(Calendar signDate)
/*     */   {
/* 138 */     this.signDate = signDate;
/*     */   }
/*     */ 
/*     */   public XmlLocator getXmlLocator()
/*     */   {
/* 146 */     return this.xmlLocator;
/*     */   }
/*     */ 
/*     */   public void setXmlLocator(XmlLocator xmlLocator)
/*     */   {
/* 151 */     this.xmlLocator = xmlLocator;
/*     */   }
/*     */ 
/*     */   public XpathConstructor getXpathConstructor()
/*     */   {
/* 159 */     return this.xpathConstructor;
/*     */   }
/*     */ 
/*     */   public void setXpathConstructor(XpathConstructor xpathConstructor) {
/* 163 */     this.xpathConstructor = xpathConstructor;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException, DocumentException
/*     */   {
/* 172 */     this.writer.close(this.stamper.getMoreInfo());
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.XmlSignatureAppearance
 * JD-Core Version:    0.6.2
 */