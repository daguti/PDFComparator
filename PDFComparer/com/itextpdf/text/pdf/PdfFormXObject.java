/*    */ package com.itextpdf.text.pdf;
/*    */ 
/*    */ public class PdfFormXObject extends PdfStream
/*    */ {
/* 56 */   public static final PdfNumber ZERO = new PdfNumber(0);
/*    */ 
/* 59 */   public static final PdfNumber ONE = new PdfNumber(1);
/*    */ 
/* 62 */   public static final PdfLiteral MATRIX = new PdfLiteral("[1 0 0 1 0 0]");
/*    */ 
/*    */   PdfFormXObject(PdfTemplate template, int compressionLevel)
/*    */   {
/* 75 */     put(PdfName.TYPE, PdfName.XOBJECT);
/* 76 */     put(PdfName.SUBTYPE, PdfName.FORM);
/* 77 */     put(PdfName.RESOURCES, template.getResources());
/* 78 */     put(PdfName.BBOX, new PdfRectangle(template.getBoundingBox()));
/* 79 */     put(PdfName.FORMTYPE, ONE);
/* 80 */     if (template.getLayer() != null)
/* 81 */       put(PdfName.OC, template.getLayer().getRef());
/* 82 */     if (template.getGroup() != null)
/* 83 */       put(PdfName.GROUP, template.getGroup());
/* 84 */     PdfArray matrix = template.getMatrix();
/* 85 */     if (matrix == null)
/* 86 */       put(PdfName.MATRIX, MATRIX);
/*    */     else
/* 88 */       put(PdfName.MATRIX, matrix);
/* 89 */     this.bytes = template.toPdf(null);
/* 90 */     put(PdfName.LENGTH, new PdfNumber(this.bytes.length));
/* 91 */     if (template.getAdditional() != null) {
/* 92 */       putAll(template.getAdditional());
/*    */     }
/* 94 */     flateCompress(compressionLevel);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfFormXObject
 * JD-Core Version:    0.6.2
 */