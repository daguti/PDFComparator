/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ 
/*     */ public class PdfSpotColor
/*     */   implements ICachedColorSpace, IPdfSpecialColorSpace
/*     */ {
/*     */   public PdfName name;
/*     */   public BaseColor altcs;
/*     */   public ColorDetails altColorDetails;
/*     */ 
/*     */   public PdfSpotColor(String name, BaseColor altcs)
/*     */   {
/*  75 */     this.name = new PdfName(name);
/*  76 */     this.altcs = altcs;
/*     */   }
/*     */ 
/*     */   public ColorDetails[] getColorantDetails(PdfWriter writer) {
/*  80 */     if ((this.altColorDetails == null) && ((this.altcs instanceof ExtendedColor)) && (((ExtendedColor)this.altcs).getType() == 7)) {
/*  81 */       this.altColorDetails = writer.addSimple(((LabColor)this.altcs).getLabColorSpace());
/*     */     }
/*  83 */     return new ColorDetails[] { this.altColorDetails };
/*     */   }
/*     */ 
/*     */   public BaseColor getAlternativeCS()
/*     */   {
/*  91 */     return this.altcs;
/*     */   }
/*     */ 
/*     */   public PdfName getName() {
/*  95 */     return this.name;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected PdfObject getSpotObject(PdfWriter writer)
/*     */   {
/* 101 */     return getPdfObject(writer);
/*     */   }
/*     */ 
/*     */   public PdfObject getPdfObject(PdfWriter writer) {
/* 105 */     PdfArray array = new PdfArray(PdfName.SEPARATION);
/* 106 */     array.add(this.name);
/* 107 */     PdfFunction func = null;
/* 108 */     if ((this.altcs instanceof ExtendedColor)) {
/* 109 */       int type = ((ExtendedColor)this.altcs).type;
/* 110 */       switch (type) {
/*     */       case 1:
/* 112 */         array.add(PdfName.DEVICEGRAY);
/* 113 */         func = PdfFunction.type2(writer, new float[] { 0.0F, 1.0F }, null, new float[] { 1.0F }, new float[] { ((GrayColor)this.altcs).getGray() }, 1.0F);
/* 114 */         break;
/*     */       case 2:
/* 116 */         array.add(PdfName.DEVICECMYK);
/* 117 */         CMYKColor cmyk = (CMYKColor)this.altcs;
/* 118 */         func = PdfFunction.type2(writer, new float[] { 0.0F, 1.0F }, null, new float[] { 0.0F, 0.0F, 0.0F, 0.0F }, new float[] { cmyk.getCyan(), cmyk.getMagenta(), cmyk.getYellow(), cmyk.getBlack() }, 1.0F);
/*     */ 
/* 120 */         break;
/*     */       case 7:
/* 122 */         LabColor lab = (LabColor)this.altcs;
/* 123 */         if (this.altColorDetails != null)
/* 124 */           array.add(this.altColorDetails.getIndirectReference());
/*     */         else
/* 126 */           array.add(lab.getLabColorSpace().getPdfObject(writer));
/* 127 */         func = PdfFunction.type2(writer, new float[] { 0.0F, 1.0F }, null, new float[] { 100.0F, 0.0F, 0.0F }, new float[] { lab.getL(), lab.getA(), lab.getB() }, 1.0F);
/*     */ 
/* 129 */         break;
/*     */       default:
/* 131 */         throw new RuntimeException(MessageLocalization.getComposedMessage("only.rgb.gray.and.cmyk.are.supported.as.alternative.color.spaces", new Object[0]));
/*     */       }
/*     */     }
/*     */     else {
/* 135 */       array.add(PdfName.DEVICERGB);
/* 136 */       func = PdfFunction.type2(writer, new float[] { 0.0F, 1.0F }, null, new float[] { 1.0F, 1.0F, 1.0F }, new float[] { this.altcs.getRed() / 255.0F, this.altcs.getGreen() / 255.0F, this.altcs.getBlue() / 255.0F }, 1.0F);
/*     */     }
/*     */ 
/* 139 */     array.add(func.getReference());
/* 140 */     return array;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object o)
/*     */   {
/* 145 */     if (this == o) return true;
/* 146 */     if (!(o instanceof PdfSpotColor)) return false;
/*     */ 
/* 148 */     PdfSpotColor spotColor = (PdfSpotColor)o;
/*     */ 
/* 150 */     if (!this.altcs.equals(spotColor.altcs)) return false;
/* 151 */     if (!this.name.equals(spotColor.name)) return false;
/*     */ 
/* 153 */     return true;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 158 */     int result = this.name.hashCode();
/* 159 */     result = 31 * result + this.altcs.hashCode();
/* 160 */     return result;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfSpotColor
 * JD-Core Version:    0.6.2
 */