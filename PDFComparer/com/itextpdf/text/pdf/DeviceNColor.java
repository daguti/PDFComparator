/*    */ package com.itextpdf.text.pdf;
/*    */ 
/*    */ import com.itextpdf.text.error_messages.MessageLocalization;
/*    */ 
/*    */ public class DeviceNColor extends ExtendedColor
/*    */ {
/*    */   PdfDeviceNColor pdfDeviceNColor;
/*    */   float[] tints;
/*    */ 
/*    */   public DeviceNColor(PdfDeviceNColor pdfDeviceNColor, float[] tints)
/*    */   {
/*  9 */     super(6);
/* 10 */     if (pdfDeviceNColor.getSpotColors().length != tints.length)
/* 11 */       throw new RuntimeException(MessageLocalization.getComposedMessage("devicen.color.shall.have.the.same.number.of.colorants.as.the.destination.DeviceN.color.space", new Object[0]));
/* 12 */     this.pdfDeviceNColor = pdfDeviceNColor;
/* 13 */     this.tints = tints;
/*    */   }
/*    */ 
/*    */   public PdfDeviceNColor getPdfDeviceNColor() {
/* 17 */     return this.pdfDeviceNColor;
/*    */   }
/*    */ 
/*    */   public float[] getTints() {
/* 21 */     return this.tints;
/*    */   }
/*    */   public boolean equals(Object obj) {
/* 24 */     if (((obj instanceof DeviceNColor)) && (((DeviceNColor)obj).tints.length == this.tints.length)) {
/* 25 */       int i = 0;
/* 26 */       for (float tint : this.tints) {
/* 27 */         if (tint != ((DeviceNColor)obj).tints[i])
/* 28 */           return false;
/* 29 */         i++;
/*    */       }
/* 31 */       return true;
/*    */     }
/* 33 */     return false;
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 37 */     int hashCode = this.pdfDeviceNColor.hashCode();
/* 38 */     float[] arr$ = this.tints; int len$ = arr$.length; for (int i$ = 0; i$ < len$; i$++) { Float tint = Float.valueOf(arr$[i$]);
/* 39 */       hashCode ^= tint.hashCode();
/*    */     }
/* 41 */     return hashCode;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.DeviceNColor
 * JD-Core Version:    0.6.2
 */