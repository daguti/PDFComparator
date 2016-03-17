/*    */ package org.apache.pdfbox.preflight.process;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSDocument;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.PreflightDocument;
/*    */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*    */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*    */ 
/*    */ public class XRefValidationProcess extends AbstractProcess
/*    */ {
/*    */   public void validate(PreflightContext ctx)
/*    */     throws ValidationException
/*    */   {
/* 37 */     COSDocument document = ctx.getDocument().getDocument();
/* 38 */     if (document.getObjects().size() > 8388607)
/*    */     {
/* 40 */       addValidationError(ctx, new ValidationResult.ValidationError("1.0.9", "Too many indirect objects"));
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.process.XRefValidationProcess
 * JD-Core Version:    0.6.2
 */