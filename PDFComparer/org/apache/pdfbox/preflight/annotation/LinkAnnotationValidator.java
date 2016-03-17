/*     */ package org.apache.pdfbox.preflight.annotation;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
/*     */ import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDDestination;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ 
/*     */ public class LinkAnnotationValidator extends AnnotationValidator
/*     */ {
/*  46 */   protected PDAnnotationLink pdLink = null;
/*     */ 
/*     */   public LinkAnnotationValidator(PreflightContext ctx, COSDictionary annotDictionary)
/*     */   {
/*  50 */     super(ctx, annotDictionary);
/*  51 */     this.pdLink = new PDAnnotationLink(annotDictionary);
/*  52 */     this.pdAnnot = this.pdLink;
/*     */   }
/*     */ 
/*     */   public boolean validate()
/*     */     throws ValidationException
/*     */   {
/*  63 */     boolean isValide = super.validate();
/*  64 */     isValide = (isValide) && (checkDest());
/*  65 */     return isValide;
/*     */   }
/*     */ 
/*     */   protected boolean checkDest()
/*     */   {
/*     */     try
/*     */     {
/*  77 */       PDDestination dest = this.pdLink.getDestination();
/*  78 */       if (dest != null)
/*     */       {
/*  81 */         if (this.pdLink.getAction() != null)
/*     */         {
/*  83 */           this.ctx.addValidationError(new ValidationResult.ValidationError("5.2.4", "Dest can't be used due to A element"));
/*     */ 
/*  85 */           return false;
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*  91 */       this.ctx.addValidationError(new ValidationResult.ValidationError("5.3.3", "Dest can't be checked"));
/*  92 */       return false;
/*     */     }
/*  94 */     return true;
/*     */   }
/*     */ 
/*     */   protected boolean checkMandatoryFields()
/*     */   {
/* 104 */     boolean subtype = this.annotDictionary.containsKey(COSName.SUBTYPE);
/* 105 */     boolean rect = this.annotDictionary.containsKey(COSName.RECT);
/* 106 */     boolean f = this.annotDictionary.containsKey(COSName.F);
/*     */ 
/* 108 */     boolean result = (subtype) && (rect) && (f);
/* 109 */     if (!result)
/*     */     {
/* 111 */       this.ctx.addValidationError(new ValidationResult.ValidationError("5.1"));
/*     */     }
/* 113 */     return result;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.annotation.LinkAnnotationValidator
 * JD-Core Version:    0.6.2
 */