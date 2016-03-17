/*    */ package org.apache.pdfbox.preflight.process.reflect;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.cos.COSStream;
/*    */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectForm;
/*    */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.PreflightPath;
/*    */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*    */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*    */ import org.apache.pdfbox.preflight.process.AbstractProcess;
/*    */ import org.apache.pdfbox.preflight.xobject.XObjFormValidator;
/*    */ import org.apache.pdfbox.preflight.xobject.XObjImageValidator;
/*    */ import org.apache.pdfbox.preflight.xobject.XObjPostscriptValidator;
/*    */ import org.apache.pdfbox.preflight.xobject.XObjectValidator;
/*    */ 
/*    */ public class GraphicObjectPageValidationProcess extends AbstractProcess
/*    */ {
/*    */   public void validate(PreflightContext context)
/*    */     throws ValidationException
/*    */   {
/* 47 */     PreflightPath vPath = context.getValidationPath();
/*    */ 
/* 49 */     XObjectValidator validator = null;
/* 50 */     if ((!vPath.isEmpty()) && (vPath.isExpectedType(PDXObjectImage.class)))
/*    */     {
/* 52 */       validator = new XObjImageValidator(context, (PDXObjectImage)vPath.peek());
/*    */     }
/* 54 */     else if ((!vPath.isEmpty()) && (vPath.isExpectedType(PDXObjectForm.class)))
/*    */     {
/* 56 */       validator = new XObjFormValidator(context, (PDXObjectForm)vPath.peek());
/*    */     }
/* 58 */     else if ((!vPath.isEmpty()) && (vPath.isExpectedType(COSStream.class)))
/*    */     {
/* 60 */       COSStream stream = (COSStream)vPath.peek();
/* 61 */       String subType = stream.getNameAsString(COSName.SUBTYPE);
/* 62 */       if ("PS".equals(subType))
/*    */       {
/* 64 */         validator = new XObjPostscriptValidator(context, stream);
/*    */       }
/*    */       else
/*    */       {
/* 68 */         context.addValidationError(new ValidationResult.ValidationError("2.1.10", "Invalid XObject subtype"));
/*    */       }
/*    */     }
/*    */     else
/*    */     {
/* 73 */       context.addValidationError(new ValidationResult.ValidationError("2.1.9", "Graphic validation process needs at least one PDXObject"));
/*    */     }
/*    */ 
/* 76 */     if (validator != null)
/* 77 */       validator.validate();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.process.reflect.GraphicObjectPageValidationProcess
 * JD-Core Version:    0.6.2
 */