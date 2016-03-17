/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import com.itextpdf.text.Rectangle;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.awt.Color;
/*     */ import java.awt.Image;
/*     */ 
/*     */ public class BarcodeEANSUPP extends Barcode
/*     */ {
/*     */   protected Barcode ean;
/*     */   protected Barcode supp;
/*     */ 
/*     */   public BarcodeEANSUPP(Barcode ean, Barcode supp)
/*     */   {
/*  77 */     this.n = 8.0F;
/*  78 */     this.ean = ean;
/*  79 */     this.supp = supp;
/*     */   }
/*     */ 
/*     */   public Rectangle getBarcodeSize()
/*     */   {
/*  87 */     Rectangle rect = this.ean.getBarcodeSize();
/*  88 */     rect.setRight(rect.getWidth() + this.supp.getBarcodeSize().getWidth() + this.n);
/*  89 */     return rect;
/*     */   }
/*     */ 
/*     */   public Rectangle placeBarcode(PdfContentByte cb, BaseColor barColor, BaseColor textColor)
/*     */   {
/* 129 */     if (this.supp.getFont() != null)
/* 130 */       this.supp.setBarHeight(this.ean.getBarHeight() + this.supp.getBaseline() - this.supp.getFont().getFontDescriptor(2, this.supp.getSize()));
/*     */     else
/* 132 */       this.supp.setBarHeight(this.ean.getBarHeight());
/* 133 */     Rectangle eanR = this.ean.getBarcodeSize();
/* 134 */     cb.saveState();
/* 135 */     this.ean.placeBarcode(cb, barColor, textColor);
/* 136 */     cb.restoreState();
/* 137 */     cb.saveState();
/* 138 */     cb.concatCTM(1.0F, 0.0F, 0.0F, 1.0F, eanR.getWidth() + this.n, eanR.getHeight() - this.ean.getBarHeight());
/* 139 */     this.supp.placeBarcode(cb, barColor, textColor);
/* 140 */     cb.restoreState();
/* 141 */     return getBarcodeSize();
/*     */   }
/*     */ 
/*     */   public Image createAwtImage(Color foreground, Color background)
/*     */   {
/* 153 */     throw new UnsupportedOperationException(MessageLocalization.getComposedMessage("the.two.barcodes.must.be.composed.externally", new Object[0]));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.BarcodeEANSUPP
 * JD-Core Version:    0.6.2
 */