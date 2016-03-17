/*    */ package com.itextpdf.text.pdf;
/*    */ 
/*    */ public class GrayColor extends ExtendedColor
/*    */ {
/*    */   private static final long serialVersionUID = -6571835680819282746L;
/*    */   private float gray;
/* 57 */   public static final GrayColor GRAYBLACK = new GrayColor(0.0F);
/* 58 */   public static final GrayColor GRAYWHITE = new GrayColor(1.0F);
/*    */ 
/*    */   public GrayColor(int intGray) {
/* 61 */     this(intGray / 255.0F);
/*    */   }
/*    */ 
/*    */   public GrayColor(float floatGray) {
/* 65 */     super(1, floatGray, floatGray, floatGray);
/* 66 */     this.gray = normalize(floatGray);
/*    */   }
/*    */ 
/*    */   public float getGray() {
/* 70 */     return this.gray;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object obj) {
/* 74 */     return ((obj instanceof GrayColor)) && (((GrayColor)obj).gray == this.gray);
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 78 */     return Float.floatToIntBits(this.gray);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.GrayColor
 * JD-Core Version:    0.6.2
 */