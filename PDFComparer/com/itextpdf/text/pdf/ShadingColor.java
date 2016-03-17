/*    */ package com.itextpdf.text.pdf;
/*    */ 
/*    */ public class ShadingColor extends ExtendedColor
/*    */ {
/*    */   private static final long serialVersionUID = 4817929454941328671L;
/*    */   PdfShadingPattern shadingPattern;
/*    */ 
/*    */   public ShadingColor(PdfShadingPattern shadingPattern)
/*    */   {
/* 61 */     super(5, 0.5F, 0.5F, 0.5F);
/* 62 */     this.shadingPattern = shadingPattern;
/*    */   }
/*    */ 
/*    */   public PdfShadingPattern getPdfShadingPattern()
/*    */   {
/* 70 */     return this.shadingPattern;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object obj) {
/* 74 */     return ((obj instanceof ShadingColor)) && (((ShadingColor)obj).shadingPattern.equals(this.shadingPattern));
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 78 */     return this.shadingPattern.hashCode();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.ShadingColor
 * JD-Core Version:    0.6.2
 */