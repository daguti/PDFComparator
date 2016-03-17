/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class PdfPage extends PdfDictionary
/*     */ {
/*  63 */   private static final String[] boxStrings = { "crop", "trim", "art", "bleed" };
/*  64 */   private static final PdfName[] boxNames = { PdfName.CROPBOX, PdfName.TRIMBOX, PdfName.ARTBOX, PdfName.BLEEDBOX };
/*     */ 
/*  68 */   public static final PdfNumber PORTRAIT = new PdfNumber(0);
/*     */ 
/*  71 */   public static final PdfNumber LANDSCAPE = new PdfNumber(90);
/*     */ 
/*  74 */   public static final PdfNumber INVERTEDPORTRAIT = new PdfNumber(180);
/*     */ 
/*  77 */   public static final PdfNumber SEASCAPE = new PdfNumber(270);
/*     */   PdfRectangle mediaBox;
/*     */ 
/*     */   PdfPage(PdfRectangle mediaBox, HashMap<String, PdfRectangle> boxSize, PdfDictionary resources, int rotate)
/*     */     throws DocumentException
/*     */   {
/*  94 */     super(PAGE);
/*  95 */     this.mediaBox = mediaBox;
/*  96 */     if ((mediaBox != null) && ((mediaBox.width() > 14400.0F) || (mediaBox.height() > 14400.0F))) {
/*  97 */       throw new DocumentException(MessageLocalization.getComposedMessage("the.page.size.must.be.smaller.than.14400.by.14400.its.1.by.2", new Object[] { Float.valueOf(mediaBox.width()), Float.valueOf(mediaBox.height()) }));
/*     */     }
/*  99 */     put(PdfName.MEDIABOX, mediaBox);
/* 100 */     put(PdfName.RESOURCES, resources);
/* 101 */     if (rotate != 0) {
/* 102 */       put(PdfName.ROTATE, new PdfNumber(rotate));
/*     */     }
/* 104 */     for (int k = 0; k < boxStrings.length; k++) {
/* 105 */       PdfObject rect = (PdfObject)boxSize.get(boxStrings[k]);
/* 106 */       if (rect != null)
/* 107 */         put(boxNames[k], rect);
/*     */     }
/*     */   }
/*     */ 
/*     */   PdfPage(PdfRectangle mediaBox, HashMap<String, PdfRectangle> boxSize, PdfDictionary resources)
/*     */     throws DocumentException
/*     */   {
/* 120 */     this(mediaBox, boxSize, resources, 0);
/*     */   }
/*     */ 
/*     */   public boolean isParent()
/*     */   {
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */   void add(PdfIndirectReference contents)
/*     */   {
/* 144 */     put(PdfName.CONTENTS, contents);
/*     */   }
/*     */ 
/*     */   PdfRectangle rotateMediaBox()
/*     */   {
/* 154 */     this.mediaBox = this.mediaBox.rotate();
/* 155 */     put(PdfName.MEDIABOX, this.mediaBox);
/* 156 */     return this.mediaBox;
/*     */   }
/*     */ 
/*     */   PdfRectangle getMediaBox()
/*     */   {
/* 166 */     return this.mediaBox;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfPage
 * JD-Core Version:    0.6.2
 */