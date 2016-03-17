/*    */ package org.apache.pdfbox.preflight.annotation;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationTextMarkup;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*    */ 
/*    */ public class MarkupAnnotationValidator extends AnnotationValidator
/*    */ {
/* 41 */   protected PDAnnotationTextMarkup pdMarkup = null;
/*    */ 
/*    */   public MarkupAnnotationValidator(PreflightContext ctx, COSDictionary annotDictionary)
/*    */   {
/* 45 */     super(ctx, annotDictionary);
/* 46 */     this.pdMarkup = new PDAnnotationTextMarkup(annotDictionary);
/* 47 */     this.pdAnnot = this.pdMarkup;
/*    */   }
/*    */ 
/*    */   protected boolean checkMandatoryFields()
/*    */   {
/* 57 */     boolean subtype = this.annotDictionary.containsKey(COSName.SUBTYPE);
/* 58 */     boolean rect = this.annotDictionary.containsKey(COSName.RECT);
/* 59 */     boolean f = this.annotDictionary.containsKey(COSName.F);
/* 60 */     boolean contents = this.annotDictionary.containsKey(COSName.CONTENTS);
/* 61 */     boolean qp = this.annotDictionary.containsKey(COSName.getPDFName("QuadPoints"));
/*    */ 
/* 63 */     boolean result = (subtype) && (rect) && (f) && (contents) && (qp);
/* 64 */     if (!result)
/*    */     {
/* 66 */       this.ctx.addValidationError(new ValidationResult.ValidationError("5.1"));
/*    */     }
/* 68 */     return result;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.annotation.MarkupAnnotationValidator
 * JD-Core Version:    0.6.2
 */