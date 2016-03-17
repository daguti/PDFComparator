/*    */ package org.apache.pdfbox.preflight.annotation;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
/*    */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationTextMarkup;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*    */ 
/*    */ public class FreeTextAnnotationValidator extends AnnotationValidator
/*    */ {
/* 40 */   protected PDAnnotationTextMarkup pdFreeText = null;
/*    */ 
/*    */   public FreeTextAnnotationValidator(PreflightContext ctx, COSDictionary annotDictionary)
/*    */   {
/* 44 */     super(ctx, annotDictionary);
/* 45 */     this.pdFreeText = new PDAnnotationTextMarkup(annotDictionary);
/* 46 */     this.pdAnnot = this.pdFreeText;
/*    */   }
/*    */ 
/*    */   protected boolean checkMandatoryFields()
/*    */   {
/* 56 */     boolean subtype = this.annotDictionary.containsKey(COSName.SUBTYPE);
/* 57 */     boolean rect = this.annotDictionary.containsKey(COSName.RECT);
/* 58 */     boolean f = this.annotDictionary.containsKey(COSName.F);
/* 59 */     boolean contents = this.annotDictionary.containsKey(COSName.CONTENTS);
/* 60 */     boolean da = this.annotDictionary.containsKey(COSName.DA);
/*    */ 
/* 66 */     boolean result = (subtype) && (rect) && (f) && (da) && (contents);
/* 67 */     if (!result)
/*    */     {
/* 69 */       this.ctx.addValidationError(new ValidationResult.ValidationError("5.1", "A mandatory field for the " + this.pdAnnot.getSubtype() + " annotation is missing"));
/*    */     }
/*    */ 
/* 72 */     return result;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.annotation.FreeTextAnnotationValidator
 * JD-Core Version:    0.6.2
 */