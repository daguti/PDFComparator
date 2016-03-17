/*    */ package org.apache.pdfbox.preflight.process.reflect;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.preflight.PreflightConfiguration;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.PreflightPath;
/*    */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*    */ import org.apache.pdfbox.preflight.annotation.AnnotationValidator;
/*    */ import org.apache.pdfbox.preflight.annotation.AnnotationValidatorFactory;
/*    */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*    */ import org.apache.pdfbox.preflight.process.AbstractProcess;
/*    */ 
/*    */ public class AnnotationValidationProcess extends AbstractProcess
/*    */ {
/*    */   public void validate(PreflightContext context)
/*    */     throws ValidationException
/*    */   {
/* 40 */     PreflightPath vPath = context.getValidationPath();
/* 41 */     if (vPath.isEmpty()) {
/* 42 */       return;
/*    */     }
/* 44 */     if (!vPath.isExpectedType(COSDictionary.class))
/*    */     {
/* 46 */       context.addValidationError(new ValidationResult.ValidationError("5.3", "Annotation validation process needs at least one COSDictionary object"));
/*    */     }
/*    */     else
/*    */     {
/* 50 */       COSDictionary annotDict = (COSDictionary)vPath.peek();
/*    */ 
/* 52 */       PreflightConfiguration config = context.getConfig();
/* 53 */       AnnotationValidatorFactory factory = config.getAnnotFact();
/* 54 */       AnnotationValidator annotValidator = factory.getAnnotationValidator(context, annotDict);
/* 55 */       if (annotValidator != null)
/*    */       {
/* 57 */         annotValidator.validate();
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.process.reflect.AnnotationValidationProcess
 * JD-Core Version:    0.6.2
 */