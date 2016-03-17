/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ public class PdfGState extends PdfDictionary
/*     */ {
/*  58 */   public static final PdfName BM_NORMAL = new PdfName("Normal");
/*     */ 
/*  60 */   public static final PdfName BM_COMPATIBLE = new PdfName("Compatible");
/*     */ 
/*  62 */   public static final PdfName BM_MULTIPLY = new PdfName("Multiply");
/*     */ 
/*  64 */   public static final PdfName BM_SCREEN = new PdfName("Screen");
/*     */ 
/*  66 */   public static final PdfName BM_OVERLAY = new PdfName("Overlay");
/*     */ 
/*  68 */   public static final PdfName BM_DARKEN = new PdfName("Darken");
/*     */ 
/*  70 */   public static final PdfName BM_LIGHTEN = new PdfName("Lighten");
/*     */ 
/*  72 */   public static final PdfName BM_COLORDODGE = new PdfName("ColorDodge");
/*     */ 
/*  74 */   public static final PdfName BM_COLORBURN = new PdfName("ColorBurn");
/*     */ 
/*  76 */   public static final PdfName BM_HARDLIGHT = new PdfName("HardLight");
/*     */ 
/*  78 */   public static final PdfName BM_SOFTLIGHT = new PdfName("SoftLight");
/*     */ 
/*  80 */   public static final PdfName BM_DIFFERENCE = new PdfName("Difference");
/*     */ 
/*  82 */   public static final PdfName BM_EXCLUSION = new PdfName("Exclusion");
/*     */ 
/*     */   public void setOverPrintStroking(boolean op)
/*     */   {
/*  89 */     put(PdfName.OP, op ? PdfBoolean.PDFTRUE : PdfBoolean.PDFFALSE);
/*     */   }
/*     */ 
/*     */   public void setOverPrintNonStroking(boolean op)
/*     */   {
/*  97 */     put(PdfName.op, op ? PdfBoolean.PDFTRUE : PdfBoolean.PDFFALSE);
/*     */   }
/*     */ 
/*     */   public void setOverPrintMode(int opm)
/*     */   {
/* 105 */     put(PdfName.OPM, new PdfNumber(opm == 0 ? 0 : 1));
/*     */   }
/*     */ 
/*     */   public void setStrokeOpacity(float ca)
/*     */   {
/* 115 */     put(PdfName.CA, new PdfNumber(ca));
/*     */   }
/*     */ 
/*     */   public void setFillOpacity(float ca)
/*     */   {
/* 125 */     put(PdfName.ca, new PdfNumber(ca));
/*     */   }
/*     */ 
/*     */   public void setAlphaIsShape(boolean ais)
/*     */   {
/* 135 */     put(PdfName.AIS, ais ? PdfBoolean.PDFTRUE : PdfBoolean.PDFFALSE);
/*     */   }
/*     */ 
/*     */   public void setTextKnockout(boolean tk)
/*     */   {
/* 144 */     put(PdfName.TK, tk ? PdfBoolean.PDFTRUE : PdfBoolean.PDFFALSE);
/*     */   }
/*     */ 
/*     */   public void setBlendMode(PdfName bm)
/*     */   {
/* 152 */     put(PdfName.BM, bm);
/*     */   }
/*     */ 
/*     */   public void setRenderingIntent(PdfName ri)
/*     */   {
/* 162 */     put(PdfName.RI, ri);
/*     */   }
/*     */ 
/*     */   public void toPdf(PdfWriter writer, OutputStream os) throws IOException
/*     */   {
/* 167 */     PdfWriter.checkPdfIsoConformance(writer, 6, this);
/* 168 */     super.toPdf(writer, os);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfGState
 * JD-Core Version:    0.6.2
 */