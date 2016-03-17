/*    */ package org.apache.pdfbox.preflight.graphic;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDIndexed;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*    */ 
/*    */ public class DeviceColorSpaceHelper extends StandardColorSpaceHelper
/*    */ {
/*    */   public DeviceColorSpaceHelper(PreflightContext _context, PDColorSpace _cs)
/*    */   {
/* 44 */     super(_context, _cs);
/*    */   }
/*    */ 
/*    */   protected void processPatternColorSpace(PDColorSpace pdcs)
/*    */   {
/* 53 */     this.context.addValidationError(new ValidationResult.ValidationError("2.4.9", "Pattern ColorSpace is forbidden"));
/*    */   }
/*    */ 
/*    */   protected void processDeviceNColorSpace(PDColorSpace pdcs)
/*    */   {
/* 63 */     this.context.addValidationError(new ValidationResult.ValidationError("2.4.9", "DeviceN ColorSpace is forbidden"));
/*    */   }
/*    */ 
/*    */   protected void processIndexedColorSpace(PDColorSpace pdcs)
/*    */   {
/* 74 */     PDIndexed indexed = (PDIndexed)pdcs;
/*    */     try
/*    */     {
/* 77 */       PDColorSpace based = indexed.getBaseColorSpace();
/* 78 */       ColorSpaces colorSpace = ColorSpaces.valueOf(based.getName());
/* 79 */       switch (1.$SwitchMap$org$apache$pdfbox$preflight$graphic$ColorSpaces[colorSpace.ordinal()])
/*    */       {
/*    */       case 1:
/*    */       case 2:
/*    */       case 3:
/* 84 */         this.context.addValidationError(new ValidationResult.ValidationError("2.4.9", colorSpace.getLabel() + " ColorSpace is forbidden"));
/*    */ 
/* 86 */         break;
/*    */       default:
/* 88 */         processAllColorSpace(based);
/*    */       }
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 93 */       this.context.addValidationError(new ValidationResult.ValidationError("2.4", "Unable to read Indexed Color Space : " + e.getMessage()));
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.graphic.DeviceColorSpaceHelper
 * JD-Core Version:    0.6.2
 */