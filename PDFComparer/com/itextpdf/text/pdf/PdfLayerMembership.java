/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ 
/*     */ public class PdfLayerMembership extends PdfDictionary
/*     */   implements PdfOCG
/*     */ {
/*  64 */   public static final PdfName ALLON = new PdfName("AllOn");
/*     */ 
/*  68 */   public static final PdfName ANYON = new PdfName("AnyOn");
/*     */ 
/*  72 */   public static final PdfName ANYOFF = new PdfName("AnyOff");
/*     */ 
/*  76 */   public static final PdfName ALLOFF = new PdfName("AllOff");
/*     */   PdfIndirectReference ref;
/*  79 */   PdfArray members = new PdfArray();
/*  80 */   HashSet<PdfLayer> layers = new HashSet();
/*     */ 
/*     */   public PdfLayerMembership(PdfWriter writer)
/*     */   {
/*  87 */     super(PdfName.OCMD);
/*  88 */     put(PdfName.OCGS, this.members);
/*  89 */     this.ref = writer.getPdfIndirectReference();
/*     */   }
/*     */ 
/*     */   public PdfIndirectReference getRef()
/*     */   {
/*  97 */     return this.ref;
/*     */   }
/*     */ 
/*     */   public void addMember(PdfLayer layer)
/*     */   {
/* 105 */     if (!this.layers.contains(layer)) {
/* 106 */       this.members.add(layer.getRef());
/* 107 */       this.layers.add(layer);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection<PdfLayer> getLayers()
/*     */   {
/* 116 */     return this.layers;
/*     */   }
/*     */ 
/*     */   public void setVisibilityPolicy(PdfName type)
/*     */   {
/* 126 */     put(PdfName.P, type);
/*     */   }
/*     */ 
/*     */   public void setVisibilityExpression(PdfVisibilityExpression ve)
/*     */   {
/* 138 */     put(PdfName.VE, ve);
/*     */   }
/*     */ 
/*     */   public PdfObject getPdfObject()
/*     */   {
/* 146 */     return this;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfLayerMembership
 * JD-Core Version:    0.6.2
 */