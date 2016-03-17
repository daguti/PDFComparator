/*     */ package org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public class PDVisibleSigProperties
/*     */ {
/*     */   private String signerName;
/*     */   private String signerLocation;
/*     */   private String signatureReason;
/*     */   private boolean visualSignEnabled;
/*     */   private int page;
/*     */   private int preferredSize;
/*     */   private InputStream visibleSignature;
/*     */   private PDVisibleSignDesigner pdVisibleSignature;
/*     */ 
/*     */   public void buildSignature()
/*     */     throws IOException
/*     */   {
/*  47 */     PDFTemplateBuilder builder = new PDVisibleSigBuilder();
/*  48 */     PDFTemplateCreator creator = new PDFTemplateCreator(builder);
/*  49 */     setVisibleSignature(creator.buildPDF(getPdVisibleSignature()));
/*     */   }
/*     */ 
/*     */   public String getSignerName()
/*     */   {
/*  58 */     return this.signerName;
/*     */   }
/*     */ 
/*     */   public PDVisibleSigProperties signerName(String signerName)
/*     */   {
/*  68 */     this.signerName = signerName;
/*  69 */     return this;
/*     */   }
/*     */ 
/*     */   public String getSignerLocation()
/*     */   {
/*  78 */     return this.signerLocation;
/*     */   }
/*     */ 
/*     */   public PDVisibleSigProperties signerLocation(String signerLocation)
/*     */   {
/*  88 */     this.signerLocation = signerLocation;
/*  89 */     return this;
/*     */   }
/*     */ 
/*     */   public String getSignatureReason()
/*     */   {
/*  98 */     return this.signatureReason;
/*     */   }
/*     */ 
/*     */   public PDVisibleSigProperties signatureReason(String signatureReason)
/*     */   {
/* 108 */     this.signatureReason = signatureReason;
/* 109 */     return this;
/*     */   }
/*     */ 
/*     */   public int getPage()
/*     */   {
/* 118 */     return this.page;
/*     */   }
/*     */ 
/*     */   public PDVisibleSigProperties page(int page)
/*     */   {
/* 128 */     this.page = page;
/* 129 */     return this;
/*     */   }
/*     */ 
/*     */   public int getPreferredSize()
/*     */   {
/* 138 */     return this.preferredSize;
/*     */   }
/*     */ 
/*     */   public PDVisibleSigProperties preferredSize(int preferredSize)
/*     */   {
/* 148 */     this.preferredSize = preferredSize;
/* 149 */     return this;
/*     */   }
/*     */ 
/*     */   public boolean isVisualSignEnabled()
/*     */   {
/* 158 */     return this.visualSignEnabled;
/*     */   }
/*     */ 
/*     */   public PDVisibleSigProperties visualSignEnabled(boolean visualSignEnabled)
/*     */   {
/* 168 */     this.visualSignEnabled = visualSignEnabled;
/* 169 */     return this;
/*     */   }
/*     */ 
/*     */   public PDVisibleSignDesigner getPdVisibleSignature()
/*     */   {
/* 178 */     return this.pdVisibleSignature;
/*     */   }
/*     */ 
/*     */   public PDVisibleSigProperties setPdVisibleSignature(PDVisibleSignDesigner pdVisibleSignature)
/*     */   {
/* 188 */     this.pdVisibleSignature = pdVisibleSignature;
/* 189 */     return this;
/*     */   }
/*     */ 
/*     */   public InputStream getVisibleSignature()
/*     */   {
/* 198 */     return this.visibleSignature;
/*     */   }
/*     */ 
/*     */   public void setVisibleSignature(InputStream visibleSignature)
/*     */   {
/* 207 */     this.visibleSignature = visibleSignature;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible.PDVisibleSigProperties
 * JD-Core Version:    0.6.2
 */