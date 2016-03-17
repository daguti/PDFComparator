/*    */ package org.apache.pdfbox.preflight.graphic;
/*    */ 
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*    */ 
/*    */ public class NoPatternColorSpaceHelper extends StandardColorSpaceHelper
/*    */ {
/*    */   public NoPatternColorSpaceHelper(PreflightContext _context, PDColorSpace _cs)
/*    */   {
/* 39 */     super(_context, _cs);
/*    */   }
/*    */ 
/*    */   protected void processPatternColorSpace(PDColorSpace pdcs)
/*    */   {
/* 48 */     this.context.addValidationError(new ValidationResult.ValidationError("2.4.5", "Pattern color space is forbidden"));
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.graphic.NoPatternColorSpaceHelper
 * JD-Core Version:    0.6.2
 */