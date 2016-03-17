/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ 
/*     */ public class TtfUnicodeWriter
/*     */ {
/*  56 */   protected PdfWriter writer = null;
/*     */ 
/*     */   public TtfUnicodeWriter(PdfWriter writer) {
/*  59 */     this.writer = writer;
/*     */   }
/*     */ 
/*     */   public void writeFont(TrueTypeFontUnicode font, PdfIndirectReference ref, Object[] params, byte[] rotbits) throws DocumentException, IOException {
/*  63 */     HashMap longTag = (HashMap)params[0];
/*  64 */     font.addRangeUni(longTag, true, font.subset);
/*  65 */     int[][] metrics = (int[][])longTag.values().toArray(new int[0][]);
/*  66 */     Arrays.sort(metrics, font);
/*  67 */     PdfIndirectReference ind_font = null;
/*  68 */     PdfObject pobj = null;
/*  69 */     PdfIndirectObject obj = null;
/*     */ 
/*  71 */     if (font.cff) {
/*  72 */       byte[] b = font.readCffFont();
/*  73 */       if ((font.subset) || (font.subsetRanges != null)) {
/*  74 */         CFFFontSubset cff = new CFFFontSubset(new RandomAccessFileOrArray(b), longTag);
/*  75 */         b = cff.Process(cff.getNames()[0]);
/*     */       }
/*  77 */       pobj = new BaseFont.StreamFont(b, "CIDFontType0C", font.compressionLevel);
/*  78 */       obj = this.writer.addToBody(pobj);
/*  79 */       ind_font = obj.getIndirectReference();
/*     */     }
/*     */     else
/*     */     {
/*     */       byte[] b;
/*     */       byte[] b;
/*  82 */       if ((font.subset) || (font.directoryOffset != 0)) {
/*  83 */         b = font.getSubSet(new HashSet(longTag.keySet()), true);
/*     */       }
/*     */       else {
/*  86 */         b = font.getFullFont();
/*     */       }
/*  88 */       int[] lengths = { b.length };
/*  89 */       pobj = new BaseFont.StreamFont(b, lengths, font.compressionLevel);
/*  90 */       obj = this.writer.addToBody(pobj);
/*  91 */       ind_font = obj.getIndirectReference();
/*     */     }
/*  93 */     String subsetPrefix = "";
/*  94 */     if (font.subset)
/*  95 */       subsetPrefix = TrueTypeFontUnicode.createSubsetPrefix();
/*  96 */     PdfDictionary dic = font.getFontDescriptor(ind_font, subsetPrefix, null);
/*  97 */     obj = this.writer.addToBody(dic);
/*  98 */     ind_font = obj.getIndirectReference();
/*     */ 
/* 100 */     pobj = font.getCIDFontType2(ind_font, subsetPrefix, metrics);
/* 101 */     obj = this.writer.addToBody(pobj);
/* 102 */     ind_font = obj.getIndirectReference();
/*     */ 
/* 104 */     pobj = font.getToUnicode(metrics);
/* 105 */     PdfIndirectReference toUnicodeRef = null;
/*     */ 
/* 107 */     if (pobj != null) {
/* 108 */       obj = this.writer.addToBody(pobj);
/* 109 */       toUnicodeRef = obj.getIndirectReference();
/*     */     }
/*     */ 
/* 112 */     pobj = font.getFontBaseType(ind_font, subsetPrefix, toUnicodeRef);
/* 113 */     this.writer.addToBody(pobj, ref);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.TtfUnicodeWriter
 * JD-Core Version:    0.6.2
 */