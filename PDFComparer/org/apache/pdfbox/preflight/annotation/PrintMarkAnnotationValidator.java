/*    */ package org.apache.pdfbox.preflight.annotation;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationUnknown;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*    */ 
/*    */ public class PrintMarkAnnotationValidator extends AnnotationValidator
/*    */ {
/* 40 */   protected PDAnnotationUnknown pdUnk = null;
/*    */ 
/*    */   public PrintMarkAnnotationValidator(PreflightContext ctx, COSDictionary annotDictionary)
/*    */   {
/* 44 */     super(ctx, annotDictionary);
/* 45 */     this.pdUnk = new PDAnnotationUnknown(annotDictionary);
/* 46 */     this.pdAnnot = this.pdUnk;
/*    */   }
/*    */ 
/*    */   protected boolean checkMandatoryFields()
/*    */   {
/* 56 */     boolean subtype = this.annotDictionary.containsKey(COSName.SUBTYPE);
/* 57 */     boolean rect = this.annotDictionary.containsKey(COSName.RECT);
/* 58 */     boolean f = this.annotDictionary.containsKey(COSName.F);
/*    */ 
/* 60 */     boolean result = (subtype) && (rect) && (f);
/* 61 */     if (!result)
/*    */     {
/* 63 */       this.ctx.addValidationError(new ValidationResult.ValidationError("5.1"));
/*    */     }
/* 65 */     return result;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.annotation.PrintMarkAnnotationValidator
 * JD-Core Version:    0.6.2
 */