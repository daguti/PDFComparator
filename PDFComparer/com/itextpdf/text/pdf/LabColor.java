/*    */ package com.itextpdf.text.pdf;
/*    */ 
/*    */ import com.itextpdf.text.BaseColor;
/*    */ 
/*    */ public class LabColor extends ExtendedColor
/*    */ {
/*    */   PdfLabColor labColorSpace;
/*    */   private float l;
/*    */   private float a;
/*    */   private float b;
/*    */ 
/*    */   public LabColor(PdfLabColor labColorSpace, float l, float a, float b)
/*    */   {
/* 12 */     super(7);
/* 13 */     this.labColorSpace = labColorSpace;
/* 14 */     this.l = l;
/* 15 */     this.a = a;
/* 16 */     this.b = b;
/* 17 */     BaseColor altRgbColor = labColorSpace.lab2Rgb(l, a, b);
/* 18 */     setValue(altRgbColor.getRed(), altRgbColor.getGreen(), altRgbColor.getBlue(), 255);
/*    */   }
/*    */ 
/*    */   public PdfLabColor getLabColorSpace() {
/* 22 */     return this.labColorSpace;
/*    */   }
/*    */ 
/*    */   public float getL() {
/* 26 */     return this.l;
/*    */   }
/*    */ 
/*    */   public float getA() {
/* 30 */     return this.a;
/*    */   }
/*    */ 
/*    */   public float getB() {
/* 34 */     return this.b;
/*    */   }
/*    */ 
/*    */   public BaseColor toRgb() {
/* 38 */     return this.labColorSpace.lab2Rgb(this.l, this.a, this.b);
/*    */   }
/*    */ 
/*    */   CMYKColor toCmyk() {
/* 42 */     return this.labColorSpace.lab2Cmyk(this.l, this.a, this.b);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.LabColor
 * JD-Core Version:    0.6.2
 */