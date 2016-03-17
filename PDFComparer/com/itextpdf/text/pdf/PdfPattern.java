/*    */ package com.itextpdf.text.pdf;
/*    */ 
/*    */ import com.itextpdf.text.ExceptionConverter;
/*    */ 
/*    */ public class PdfPattern extends PdfStream
/*    */ {
/*    */   PdfPattern(PdfPatternPainter painter)
/*    */   {
/* 62 */     this(painter, -1);
/*    */   }
/*    */ 
/*    */   PdfPattern(PdfPatternPainter painter, int compressionLevel)
/*    */   {
/* 73 */     PdfNumber one = new PdfNumber(1);
/* 74 */     PdfArray matrix = painter.getMatrix();
/* 75 */     if (matrix != null) {
/* 76 */       put(PdfName.MATRIX, matrix);
/*    */     }
/* 78 */     put(PdfName.TYPE, PdfName.PATTERN);
/* 79 */     put(PdfName.BBOX, new PdfRectangle(painter.getBoundingBox()));
/* 80 */     put(PdfName.RESOURCES, painter.getResources());
/* 81 */     put(PdfName.TILINGTYPE, one);
/* 82 */     put(PdfName.PATTERNTYPE, one);
/* 83 */     if (painter.isStencil())
/* 84 */       put(PdfName.PAINTTYPE, new PdfNumber(2));
/*    */     else
/* 86 */       put(PdfName.PAINTTYPE, one);
/* 87 */     put(PdfName.XSTEP, new PdfNumber(painter.getXStep()));
/* 88 */     put(PdfName.YSTEP, new PdfNumber(painter.getYStep()));
/* 89 */     this.bytes = painter.toPdf(null);
/* 90 */     put(PdfName.LENGTH, new PdfNumber(this.bytes.length));
/*    */     try {
/* 92 */       flateCompress(compressionLevel);
/*    */     } catch (Exception e) {
/* 94 */       throw new ExceptionConverter(e);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfPattern
 * JD-Core Version:    0.6.2
 */