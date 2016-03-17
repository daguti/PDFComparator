/*    */ package org.apache.pdfbox.preflight.graphic;
/*    */ 
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ 
/*    */ public class ColorSpaceHelperFactory
/*    */ {
/*    */   public ColorSpaceHelper getColorSpaceHelper(PreflightContext context, PDColorSpace cs, ColorSpaceRestriction csr)
/*    */   {
/* 51 */     switch (1.$SwitchMap$org$apache$pdfbox$preflight$graphic$ColorSpaceHelperFactory$ColorSpaceRestriction[csr.ordinal()])
/*    */     {
/*    */     case 1:
/* 54 */       return new NoPatternColorSpaceHelper(context, cs);
/*    */     case 2:
/* 56 */       return new DeviceColorSpaceHelper(context, cs);
/*    */     }
/* 58 */     return new StandardColorSpaceHelper(context, cs);
/*    */   }
/*    */ 
/*    */   public static enum ColorSpaceRestriction
/*    */   {
/* 67 */     NO_RESTRICTION, NO_PATTERN, ONLY_DEVICE;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.graphic.ColorSpaceHelperFactory
 * JD-Core Version:    0.6.2
 */