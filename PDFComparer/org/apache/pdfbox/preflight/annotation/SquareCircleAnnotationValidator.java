/*     */ package org.apache.pdfbox.preflight.annotation;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationSquareCircle;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ 
/*     */ public class SquareCircleAnnotationValidator extends AnnotationValidator
/*     */ {
/*  42 */   protected PDAnnotationSquareCircle pdSquareCircle = null;
/*     */ 
/*     */   public SquareCircleAnnotationValidator(PreflightContext ctx, COSDictionary annotDictionary)
/*     */   {
/*  46 */     super(ctx, annotDictionary);
/*  47 */     this.pdSquareCircle = new PDAnnotationSquareCircle(annotDictionary);
/*  48 */     this.pdAnnot = this.pdSquareCircle;
/*     */   }
/*     */ 
/*     */   public boolean validate()
/*     */     throws ValidationException
/*     */   {
/*  59 */     boolean isValide = super.validate();
/*  60 */     isValide = (isValide) && (checkIColors());
/*  61 */     return isValide;
/*     */   }
/*     */ 
/*     */   protected boolean checkIColors()
/*     */     throws ValidationException
/*     */   {
/*  74 */     if (this.pdSquareCircle.getInteriorColour() != null)
/*     */     {
/*  76 */       if (!searchRGBProfile())
/*     */       {
/*  78 */         this.ctx.addValidationError(new ValidationResult.ValidationError("5.2.3", "Annotation uses a Color profile which isn't the same than the profile contained by the OutputIntent"));
/*     */ 
/*  80 */         return false;
/*     */       }
/*     */     }
/*  83 */     return true;
/*     */   }
/*     */ 
/*     */   protected boolean checkMandatoryFields()
/*     */   {
/*  93 */     boolean subtype = this.annotDictionary.containsKey(COSName.SUBTYPE);
/*  94 */     boolean rect = this.annotDictionary.containsKey(COSName.RECT);
/*  95 */     boolean f = this.annotDictionary.containsKey(COSName.F);
/*     */ 
/* 101 */     boolean result = (subtype) && (rect) && (f);
/* 102 */     if (!result)
/*     */     {
/* 104 */       this.ctx.addValidationError(new ValidationResult.ValidationError("5.1"));
/*     */     }
/* 106 */     return result;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.annotation.SquareCircleAnnotationValidator
 * JD-Core Version:    0.6.2
 */