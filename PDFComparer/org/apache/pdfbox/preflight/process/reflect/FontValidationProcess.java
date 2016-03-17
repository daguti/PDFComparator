/*     */ package org.apache.pdfbox.preflight.process.reflect;
/*     */ 
/*     */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.PreflightPath;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ import org.apache.pdfbox.preflight.font.FontValidator;
/*     */ import org.apache.pdfbox.preflight.font.TrueTypeFontValidator;
/*     */ import org.apache.pdfbox.preflight.font.Type0FontValidator;
/*     */ import org.apache.pdfbox.preflight.font.Type1FontValidator;
/*     */ import org.apache.pdfbox.preflight.font.Type3FontValidator;
/*     */ import org.apache.pdfbox.preflight.font.container.FontContainer;
/*     */ import org.apache.pdfbox.preflight.process.AbstractProcess;
/*     */ 
/*     */ public class FontValidationProcess extends AbstractProcess
/*     */ {
/*     */   public void validate(PreflightContext context)
/*     */     throws ValidationException
/*     */   {
/*  54 */     PreflightPath vPath = context.getValidationPath();
/*  55 */     if (vPath.isEmpty()) {
/*  56 */       return;
/*     */     }
/*  58 */     if (!vPath.isExpectedType(PDFont.class))
/*     */     {
/*  60 */       context.addValidationError(new ValidationResult.ValidationError("3.1", "Font validation process needs at least one PDFont object"));
/*     */     }
/*     */     else
/*     */     {
/*  64 */       PDFont font = (PDFont)vPath.peek();
/*  65 */       FontContainer fontContainer = context.getFontContainer(font.getCOSObject());
/*  66 */       if (fontContainer == null)
/*     */       {
/*  68 */         FontValidator validator = getFontValidator(context, font);
/*  69 */         if (validator != null) validator.validate();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected FontValidator<? extends FontContainer> getFontValidator(PreflightContext context, PDFont font)
/*     */   {
/*  82 */     String subtype = font.getSubType();
/*  83 */     if ("TrueType".equals(subtype))
/*     */     {
/*  85 */       return new TrueTypeFontValidator(context, font);
/*     */     }
/*  87 */     if (("MMType1".equals(subtype)) || ("Type1".equals(subtype)))
/*     */     {
/*  89 */       return new Type1FontValidator(context, font);
/*     */     }
/*  91 */     if ("Type3".equals(subtype))
/*     */     {
/*  93 */       return new Type3FontValidator(context, font);
/*     */     }
/*  95 */     if ("Type0".equals(subtype))
/*     */     {
/*  97 */       return new Type0FontValidator(context, font);
/*     */     }
/*  99 */     if (("CIDFontType2".equals(subtype)) || ("Type1C".equals(subtype)) || ("CIDFontType0C".equals(subtype)) || ("CIDFontType0".equals(subtype)))
/*     */     {
/* 104 */       return null;
/*     */     }
/*     */ 
/* 108 */     context.addValidationError(new ValidationResult.ValidationError("3.1.14", "Unknown font type : " + subtype));
/* 109 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.process.reflect.FontValidationProcess
 * JD-Core Version:    0.6.2
 */