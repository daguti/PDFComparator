/*    */ package org.apache.pdfbox.preflight.annotation;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationRubberStamp;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*    */ 
/*    */ public class RubberStampAnnotationValidator extends AnnotationValidator
/*    */ {
/* 40 */   protected PDAnnotationRubberStamp pdRStamp = null;
/*    */ 
/*    */   public RubberStampAnnotationValidator(PreflightContext ctx, COSDictionary annotDictionary)
/*    */   {
/* 44 */     super(ctx, annotDictionary);
/* 45 */     this.pdRStamp = new PDAnnotationRubberStamp(annotDictionary);
/* 46 */     this.pdAnnot = this.pdRStamp;
/*    */   }
/*    */ 
/*    */   protected boolean checkMandatoryFields()
/*    */   {
/* 56 */     boolean subtype = this.annotDictionary.containsKey(COSName.SUBTYPE);
/* 57 */     boolean rect = this.annotDictionary.containsKey(COSName.RECT);
/* 58 */     boolean f = this.annotDictionary.containsKey(COSName.F);
/* 59 */     boolean contents = this.annotDictionary.containsKey(COSName.CONTENTS);
/*    */ 
/* 61 */     boolean result = (subtype) && (rect) && (f) && (contents);
/* 62 */     if (!result)
/*    */     {
/* 64 */       this.ctx.addValidationError(new ValidationResult.ValidationError("5.1"));
/*    */     }
/* 66 */     return result;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.annotation.RubberStampAnnotationValidator
 * JD-Core Version:    0.6.2
 */