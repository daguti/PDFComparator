/*    */ package org.apache.pdfbox.preflight.annotation;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
/*    */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationText;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*    */ 
/*    */ public class TextAnnotationValidator extends AnnotationValidator
/*    */ {
/* 41 */   protected PDAnnotationText pdText = null;
/*    */ 
/*    */   public TextAnnotationValidator(PreflightContext ctx, COSDictionary annotDictionary)
/*    */   {
/* 45 */     super(ctx, annotDictionary);
/* 46 */     this.pdText = new PDAnnotationText(annotDictionary);
/* 47 */     this.pdAnnot = this.pdText;
/*    */   }
/*    */ 
/*    */   protected boolean checkFlags()
/*    */   {
/* 58 */     boolean result = super.checkFlags();
/*    */ 
/* 64 */     result = (result) && (this.pdAnnot.isNoRotate());
/* 65 */     result = (result) && (this.pdAnnot.isNoZoom());
/* 66 */     if (!result)
/*    */     {
/* 68 */       this.ctx.addValidationError(new ValidationResult.ValidationError("5.2.6"));
/*    */     }
/* 70 */     return result;
/*    */   }
/*    */ 
/*    */   protected boolean checkMandatoryFields()
/*    */   {
/* 80 */     boolean subtype = this.annotDictionary.containsKey(COSName.SUBTYPE);
/* 81 */     boolean rect = this.annotDictionary.containsKey(COSName.RECT);
/* 82 */     boolean f = this.annotDictionary.containsKey(COSName.F);
/* 83 */     boolean contents = this.annotDictionary.containsKey(COSName.CONTENTS);
/*    */ 
/* 88 */     boolean result = (subtype) && (rect) && (f) && (contents);
/* 89 */     if (!result)
/*    */     {
/* 91 */       this.ctx.addValidationError(new ValidationResult.ValidationError("5.1"));
/*    */     }
/* 93 */     return result;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.annotation.TextAnnotationValidator
 * JD-Core Version:    0.6.2
 */