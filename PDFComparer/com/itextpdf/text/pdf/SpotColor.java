/*    */ package com.itextpdf.text.pdf;
/*    */ 
/*    */ import com.itextpdf.text.BaseColor;
/*    */ 
/*    */ public class SpotColor extends ExtendedColor
/*    */ {
/*    */   private static final long serialVersionUID = -6257004582113248079L;
/*    */   PdfSpotColor spot;
/*    */   float tint;
/*    */ 
/*    */   public SpotColor(PdfSpotColor spot, float tint)
/*    */   {
/* 58 */     super(3, (spot.getAlternativeCS().getRed() / 255.0F - 1.0F) * tint + 1.0F, (spot.getAlternativeCS().getGreen() / 255.0F - 1.0F) * tint + 1.0F, (spot.getAlternativeCS().getBlue() / 255.0F - 1.0F) * tint + 1.0F);
/*    */ 
/* 62 */     this.spot = spot;
/* 63 */     this.tint = tint;
/*    */   }
/*    */ 
/*    */   public PdfSpotColor getPdfSpotColor() {
/* 67 */     return this.spot;
/*    */   }
/*    */ 
/*    */   public float getTint() {
/* 71 */     return this.tint;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object obj) {
/* 75 */     return ((obj instanceof SpotColor)) && (((SpotColor)obj).spot.equals(this.spot)) && (((SpotColor)obj).tint == this.tint);
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 79 */     return this.spot.hashCode() ^ Float.floatToIntBits(this.tint);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.SpotColor
 * JD-Core Version:    0.6.2
 */