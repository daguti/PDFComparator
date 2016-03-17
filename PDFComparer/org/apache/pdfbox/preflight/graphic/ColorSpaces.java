/*    */ package org.apache.pdfbox.preflight.graphic;
/*    */ 
/*    */ public enum ColorSpaces
/*    */ {
/* 32 */   Lab("Lab"), CalRGB("CalRGB"), CalGray("CalGray"), DeviceN("DeviceN"), Indexed("Indexed"), Indexed_SHORT("I"), Pattern("Pattern"), 
/* 33 */   ICCBased("ICCBased"), DeviceRGB("DeviceRGB"), DeviceRGB_SHORT("RGB"), DeviceGray("DeviceGray"), DeviceGray_SHORT("G"), 
/* 34 */   DeviceCMYK("DeviceCMYK"), DeviceCMYK_SHORT("CMYK"), Separation("Separation");
/*    */ 
/*    */   public String label;
/*    */ 
/*    */   private ColorSpaces(String _label)
/*    */   {
/* 43 */     this.label = _label;
/*    */   }
/*    */ 
/*    */   public String getLabel()
/*    */   {
/* 51 */     return this.label;
/*    */   }
/*    */ 
/*    */   public void setLabel(String label)
/*    */   {
/* 60 */     this.label = label;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.graphic.ColorSpaces
 * JD-Core Version:    0.6.2
 */