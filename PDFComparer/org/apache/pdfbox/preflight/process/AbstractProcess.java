/*    */ package org.apache.pdfbox.preflight.process;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*    */ 
/*    */ public abstract class AbstractProcess
/*    */   implements ValidationProcess
/*    */ {
/*    */   protected void addValidationError(PreflightContext ctx, ValidationResult.ValidationError error)
/*    */   {
/* 34 */     ctx.addValidationError(error);
/*    */   }
/*    */ 
/*    */   protected void addValidationErrors(PreflightContext ctx, List<ValidationResult.ValidationError> errors)
/*    */   {
/* 39 */     for (ValidationResult.ValidationError error : errors)
/*    */     {
/* 41 */       addValidationError(ctx, error);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.process.AbstractProcess
 * JD-Core Version:    0.6.2
 */