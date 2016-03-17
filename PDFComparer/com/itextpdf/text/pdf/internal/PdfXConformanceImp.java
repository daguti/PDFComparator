/*     */ package com.itextpdf.text.pdf.internal;
/*     */ 
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.pdf.BaseFont;
/*     */ import com.itextpdf.text.pdf.ExtendedColor;
/*     */ import com.itextpdf.text.pdf.PatternColor;
/*     */ import com.itextpdf.text.pdf.PdfArray;
/*     */ import com.itextpdf.text.pdf.PdfDictionary;
/*     */ import com.itextpdf.text.pdf.PdfGState;
/*     */ import com.itextpdf.text.pdf.PdfImage;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfNumber;
/*     */ import com.itextpdf.text.pdf.PdfObject;
/*     */ import com.itextpdf.text.pdf.PdfPatternPainter;
/*     */ import com.itextpdf.text.pdf.PdfShading;
/*     */ import com.itextpdf.text.pdf.PdfShadingPattern;
/*     */ import com.itextpdf.text.pdf.PdfSpotColor;
/*     */ import com.itextpdf.text.pdf.PdfWriter;
/*     */ import com.itextpdf.text.pdf.PdfXConformanceException;
/*     */ import com.itextpdf.text.pdf.ShadingColor;
/*     */ import com.itextpdf.text.pdf.SpotColor;
/*     */ import com.itextpdf.text.pdf.interfaces.PdfXConformance;
/*     */ 
/*     */ public class PdfXConformanceImp
/*     */   implements PdfXConformance
/*     */ {
/*  71 */   protected int pdfxConformance = 0;
/*     */   protected PdfWriter writer;
/*     */ 
/*     */   public PdfXConformanceImp(PdfWriter writer)
/*     */   {
/*  75 */     this.writer = writer;
/*     */   }
/*     */ 
/*     */   public void setPDFXConformance(int pdfxConformance)
/*     */   {
/*  82 */     this.pdfxConformance = pdfxConformance;
/*     */   }
/*     */ 
/*     */   public int getPDFXConformance()
/*     */   {
/*  89 */     return this.pdfxConformance;
/*     */   }
/*     */ 
/*     */   public boolean isPdfIso()
/*     */   {
/*  96 */     return isPdfX();
/*     */   }
/*     */ 
/*     */   public boolean isPdfX()
/*     */   {
/* 104 */     return this.pdfxConformance != 0;
/*     */   }
/*     */ 
/*     */   public boolean isPdfX1A2001()
/*     */   {
/* 111 */     return this.pdfxConformance == 1;
/*     */   }
/*     */ 
/*     */   public boolean isPdfX32002()
/*     */   {
/* 118 */     return this.pdfxConformance == 2;
/*     */   }
/*     */ 
/*     */   public void checkPdfIsoConformance(int key, Object obj1)
/*     */   {
/* 127 */     if ((this.writer == null) || (!this.writer.isPdfX()))
/* 128 */       return;
/* 129 */     int conf = this.writer.getPDFXConformance();
/* 130 */     switch (key) {
/*     */     case 1:
/* 132 */       switch (conf) {
/*     */       case 1:
/* 134 */         if ((obj1 instanceof ExtendedColor)) {
/* 135 */           ExtendedColor ec = (ExtendedColor)obj1;
/* 136 */           switch (ec.getType()) {
/*     */           case 1:
/*     */           case 2:
/* 139 */             return;
/*     */           case 0:
/* 141 */             throw new PdfXConformanceException(MessageLocalization.getComposedMessage("colorspace.rgb.is.not.allowed", new Object[0]));
/*     */           case 3:
/* 143 */             SpotColor sc = (SpotColor)ec;
/* 144 */             checkPdfIsoConformance(1, sc.getPdfSpotColor().getAlternativeCS());
/* 145 */             break;
/*     */           case 5:
/* 147 */             ShadingColor xc = (ShadingColor)ec;
/* 148 */             checkPdfIsoConformance(1, xc.getPdfShadingPattern().getShading().getColorSpace());
/* 149 */             break;
/*     */           case 4:
/* 151 */             PatternColor pc = (PatternColor)ec;
/* 152 */             checkPdfIsoConformance(1, pc.getPainter().getDefaultColor());
/*     */           }
/*     */ 
/*     */         }
/* 156 */         else if ((obj1 instanceof BaseColor)) {
/* 157 */           throw new PdfXConformanceException(MessageLocalization.getComposedMessage("colorspace.rgb.is.not.allowed", new Object[0]));
/*     */         }break;
/*     */       }
/* 160 */       break;
/*     */     case 2:
/* 162 */       break;
/*     */     case 3:
/* 164 */       if (conf == 1)
/* 165 */         throw new PdfXConformanceException(MessageLocalization.getComposedMessage("colorspace.rgb.is.not.allowed", new Object[0]));
/*     */       break;
/*     */     case 4:
/* 168 */       if (!((BaseFont)obj1).isEmbedded())
/* 169 */         throw new PdfXConformanceException(MessageLocalization.getComposedMessage("all.the.fonts.must.be.embedded.this.one.isn.t.1", new Object[] { ((BaseFont)obj1).getPostscriptFontName() }));
/*     */       break;
/*     */     case 5:
/* 172 */       PdfImage image = (PdfImage)obj1;
/* 173 */       if (image.get(PdfName.SMASK) != null)
/* 174 */         throw new PdfXConformanceException(MessageLocalization.getComposedMessage("the.smask.key.is.not.allowed.in.images", new Object[0]));
/* 175 */       switch (conf) {
/*     */       case 1:
/* 177 */         PdfObject cs = image.get(PdfName.COLORSPACE);
/* 178 */         if (cs == null)
/* 179 */           return;
/* 180 */         if (cs.isName()) {
/* 181 */           if (PdfName.DEVICERGB.equals(cs))
/* 182 */             throw new PdfXConformanceException(MessageLocalization.getComposedMessage("colorspace.rgb.is.not.allowed", new Object[0]));
/*     */         }
/* 184 */         else if ((cs.isArray()) && 
/* 185 */           (PdfName.CALRGB.equals(((PdfArray)cs).getPdfObject(0)))) {
/* 186 */           throw new PdfXConformanceException(MessageLocalization.getComposedMessage("colorspace.calrgb.is.not.allowed", new Object[0]));
/*     */         }
/*     */         break;
/*     */       }
/* 190 */       break;
/*     */     case 6:
/* 192 */       PdfDictionary gs = (PdfDictionary)obj1;
/*     */ 
/* 194 */       if (gs != null)
/*     */       {
/* 196 */         PdfObject obj = gs.get(PdfName.BM);
/* 197 */         if ((obj != null) && (!PdfGState.BM_NORMAL.equals(obj)) && (!PdfGState.BM_COMPATIBLE.equals(obj)))
/* 198 */           throw new PdfXConformanceException(MessageLocalization.getComposedMessage("blend.mode.1.not.allowed", new Object[] { obj.toString() }));
/* 199 */         obj = gs.get(PdfName.CA);
/* 200 */         double v = 0.0D;
/* 201 */         if ((obj != null) && ((v = ((PdfNumber)obj).doubleValue()) != 1.0D))
/* 202 */           throw new PdfXConformanceException(MessageLocalization.getComposedMessage("transparency.is.not.allowed.ca.eq.1", new Object[] { String.valueOf(v) }));
/* 203 */         obj = gs.get(PdfName.ca);
/* 204 */         v = 0.0D;
/* 205 */         if ((obj != null) && ((v = ((PdfNumber)obj).doubleValue()) != 1.0D))
/* 206 */           throw new PdfXConformanceException(MessageLocalization.getComposedMessage("transparency.is.not.allowed.ca.eq.1", new Object[] { String.valueOf(v) })); 
/*     */       }
/*     */       break;
/*     */     case 7:
/* 209 */       throw new PdfXConformanceException(MessageLocalization.getComposedMessage("layers.are.not.allowed", new Object[0]));
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.internal.PdfXConformanceImp
 * JD-Core Version:    0.6.2
 */