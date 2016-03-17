/*     */ package com.itextpdf.text.pdf.parser;
/*     */ 
/*     */ import com.itextpdf.text.pdf.PRStream;
/*     */ import com.itextpdf.text.pdf.PdfDictionary;
/*     */ import com.itextpdf.text.pdf.PdfIndirectReference;
/*     */ import com.itextpdf.text.pdf.PdfReader;
/*     */ import java.io.IOException;
/*     */ 
/*     */ public class ImageRenderInfo
/*     */ {
/*     */   private final Matrix ctm;
/*     */   private final PdfIndirectReference ref;
/*     */   private final InlineImageInfo inlineImageInfo;
/*     */   private final PdfDictionary colorSpaceDictionary;
/*  68 */   private PdfImageObject imageObject = null;
/*     */ 
/*     */   private ImageRenderInfo(Matrix ctm, PdfIndirectReference ref, PdfDictionary colorSpaceDictionary) {
/*  71 */     this.ctm = ctm;
/*  72 */     this.ref = ref;
/*  73 */     this.inlineImageInfo = null;
/*  74 */     this.colorSpaceDictionary = colorSpaceDictionary;
/*     */   }
/*     */ 
/*     */   private ImageRenderInfo(Matrix ctm, InlineImageInfo inlineImageInfo, PdfDictionary colorSpaceDictionary) {
/*  78 */     this.ctm = ctm;
/*  79 */     this.ref = null;
/*  80 */     this.inlineImageInfo = inlineImageInfo;
/*  81 */     this.colorSpaceDictionary = colorSpaceDictionary;
/*     */   }
/*     */ 
/*     */   public static ImageRenderInfo createForXObject(Matrix ctm, PdfIndirectReference ref, PdfDictionary colorSpaceDictionary)
/*     */   {
/*  92 */     return new ImageRenderInfo(ctm, ref, colorSpaceDictionary);
/*     */   }
/*     */ 
/*     */   protected static ImageRenderInfo createForEmbeddedImage(Matrix ctm, InlineImageInfo inlineImageInfo, PdfDictionary colorSpaceDictionary)
/*     */   {
/* 104 */     ImageRenderInfo renderInfo = new ImageRenderInfo(ctm, inlineImageInfo, colorSpaceDictionary);
/* 105 */     return renderInfo;
/*     */   }
/*     */ 
/*     */   public PdfImageObject getImage()
/*     */     throws IOException
/*     */   {
/* 114 */     prepareImageObject();
/* 115 */     return this.imageObject;
/*     */   }
/*     */ 
/*     */   private void prepareImageObject() throws IOException {
/* 119 */     if (this.imageObject != null) {
/* 120 */       return;
/*     */     }
/* 122 */     if (this.ref != null) {
/* 123 */       PRStream stream = (PRStream)PdfReader.getPdfObject(this.ref);
/* 124 */       this.imageObject = new PdfImageObject(stream, this.colorSpaceDictionary);
/* 125 */     } else if (this.inlineImageInfo != null) {
/* 126 */       this.imageObject = new PdfImageObject(this.inlineImageInfo.getImageDictionary(), this.inlineImageInfo.getSamples(), this.colorSpaceDictionary);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Vector getStartPoint()
/*     */   {
/* 134 */     return new Vector(0.0F, 0.0F, 1.0F).cross(this.ctm);
/*     */   }
/*     */ 
/*     */   public Matrix getImageCTM()
/*     */   {
/* 142 */     return this.ctm;
/*     */   }
/*     */ 
/*     */   public float getArea()
/*     */   {
/* 151 */     return this.ctm.getDeterminant();
/*     */   }
/*     */ 
/*     */   public PdfIndirectReference getRef()
/*     */   {
/* 159 */     return this.ref;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.ImageRenderInfo
 * JD-Core Version:    0.6.2
 */