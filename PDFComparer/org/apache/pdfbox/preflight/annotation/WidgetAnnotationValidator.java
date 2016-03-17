/*    */ package org.apache.pdfbox.preflight.annotation;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*    */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*    */ 
/*    */ public class WidgetAnnotationValidator extends AnnotationValidator
/*    */ {
/* 42 */   protected PDAnnotationWidget pdWidget = null;
/*    */ 
/*    */   public WidgetAnnotationValidator(PreflightContext ctx, COSDictionary annotDictionary)
/*    */   {
/* 46 */     super(ctx, annotDictionary);
/* 47 */     this.pdWidget = new PDAnnotationWidget(annotDictionary);
/* 48 */     this.pdAnnot = this.pdWidget;
/*    */   }
/*    */ 
/*    */   public boolean validate()
/*    */     throws ValidationException
/*    */   {
/* 59 */     boolean isValide = super.validate();
/* 60 */     isValide = (isValide) && (checkAAField());
/* 61 */     return isValide;
/*    */   }
/*    */ 
/*    */   protected boolean checkAAField()
/*    */   {
/* 72 */     if (this.pdWidget.getActions() != null)
/*    */     {
/* 74 */       this.ctx.addValidationError(new ValidationResult.ValidationError("5.2.5"));
/* 75 */       return false;
/*    */     }
/* 77 */     return true;
/*    */   }
/*    */ 
/*    */   protected boolean checkMandatoryFields()
/*    */   {
/* 87 */     boolean subtype = this.annotDictionary.containsKey(COSName.SUBTYPE);
/* 88 */     boolean rect = this.annotDictionary.containsKey(COSName.RECT);
/* 89 */     boolean f = this.annotDictionary.containsKey(COSName.F);
/*    */ 
/* 91 */     boolean result = (subtype) && (rect) && (f);
/* 92 */     if (!result)
/*    */     {
/* 94 */       this.ctx.addValidationError(new ValidationResult.ValidationError("5.1"));
/*    */     }
/* 96 */     return result;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.annotation.WidgetAnnotationValidator
 * JD-Core Version:    0.6.2
 */