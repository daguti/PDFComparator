/*    */ package com.itextpdf.text.pdf;
/*    */ 
/*    */ public class PatternColor extends ExtendedColor
/*    */ {
/*    */   private static final long serialVersionUID = -1185448552860615964L;
/*    */   PdfPatternPainter painter;
/*    */ 
/*    */   public PatternColor(PdfPatternPainter painter)
/*    */   {
/* 60 */     super(4, 0.5F, 0.5F, 0.5F);
/* 61 */     this.painter = painter;
/*    */   }
/*    */ 
/*    */   public PdfPatternPainter getPainter()
/*    */   {
/* 68 */     return this.painter;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object obj) {
/* 72 */     return ((obj instanceof PatternColor)) && (((PatternColor)obj).painter.equals(this.painter));
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 76 */     return this.painter.hashCode();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PatternColor
 * JD-Core Version:    0.6.2
 */