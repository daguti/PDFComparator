/*     */ package org.apache.pdfbox.preflight.annotation;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectForm;
/*     */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
/*     */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceDictionary;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.PreflightDocument;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ import org.apache.pdfbox.preflight.graphic.ICCProfileWrapper;
/*     */ import org.apache.pdfbox.preflight.utils.COSUtils;
/*     */ import org.apache.pdfbox.preflight.utils.ContextHelper;
/*     */ 
/*     */ public abstract class AnnotationValidator
/*     */ {
/*  51 */   protected AnnotationValidatorFactory annotFact = null;
/*     */ 
/*  53 */   protected PreflightContext ctx = null;
/*  54 */   protected COSDocument cosDocument = null;
/*     */ 
/*  58 */   protected COSDictionary annotDictionary = null;
/*     */ 
/*  62 */   protected PDAnnotation pdAnnot = null;
/*     */ 
/*     */   public AnnotationValidator(PreflightContext context, COSDictionary annotDictionary)
/*     */   {
/*  67 */     this.ctx = context;
/*  68 */     this.annotDictionary = annotDictionary;
/*  69 */     this.cosDocument = this.ctx.getDocument().getDocument();
/*     */   }
/*     */ 
/*     */   protected boolean checkFlags()
/*     */   {
/*  87 */     boolean result = this.pdAnnot.isPrinted();
/*  88 */     result = (result) && (!this.pdAnnot.isHidden());
/*  89 */     result = (result) && (!this.pdAnnot.isInvisible());
/*  90 */     result = (result) && (!this.pdAnnot.isNoView());
/*  91 */     if (!result)
/*     */     {
/*  93 */       this.ctx.addValidationError(new ValidationResult.ValidationError("5.2.2", "Flags of " + this.pdAnnot.getSubtype() + " annotation are invalid"));
/*     */     }
/*     */ 
/*  97 */     return result;
/*     */   }
/*     */ 
/*     */   protected boolean checkCA()
/*     */   {
/* 108 */     COSBase ca = this.pdAnnot.getDictionary().getItem(COSName.CA);
/* 109 */     if (ca != null)
/*     */     {
/* 111 */       float caf = COSUtils.getAsFloat(ca, this.cosDocument).floatValue();
/* 112 */       if (caf != 1.0F)
/*     */       {
/* 114 */         this.ctx.addValidationError(new ValidationResult.ValidationError("5.3.2", "CA entry is invalid. Expected 1.0 / Read " + caf));
/*     */ 
/* 116 */         return false;
/*     */       }
/*     */     }
/* 119 */     return true;
/*     */   }
/*     */ 
/*     */   protected boolean checkColors()
/*     */     throws ValidationException
/*     */   {
/* 130 */     if (this.pdAnnot.getColour() != null)
/*     */     {
/* 132 */       if (!searchRGBProfile())
/*     */       {
/* 134 */         this.ctx.addValidationError(new ValidationResult.ValidationError("5.2.3", "Annotation uses a Color profile which isn't the same than the profile contained by the OutputIntent"));
/*     */ 
/* 136 */         return false;
/*     */       }
/*     */     }
/* 139 */     return true;
/*     */   }
/*     */ 
/*     */   protected boolean searchRGBProfile()
/*     */     throws ValidationException
/*     */   {
/* 149 */     ICCProfileWrapper iccpw = ICCProfileWrapper.getOrSearchICCProfile(this.ctx);
/* 150 */     if (iccpw != null)
/*     */     {
/* 152 */       return iccpw.isRGBColorSpace();
/*     */     }
/* 154 */     return false;
/*     */   }
/*     */ 
/*     */   protected boolean checkAP()
/*     */     throws ValidationException
/*     */   {
/* 169 */     PDAppearanceDictionary ap = this.pdAnnot.getAppearance();
/* 170 */     if (ap != null)
/*     */     {
/* 172 */       COSDictionary apDict = ap.getDictionary();
/*     */ 
/* 174 */       if ((apDict.getItem(COSName.D) != null) || (apDict.getItem(COSName.R) != null))
/*     */       {
/* 176 */         this.ctx.addValidationError(new ValidationResult.ValidationError("5.3.1", "Only the N Appearance is authorized"));
/*     */ 
/* 178 */         return false;
/*     */       }
/* 180 */       if (apDict.getItem(COSName.N) == null)
/*     */       {
/* 183 */         this.ctx.addValidationError(new ValidationResult.ValidationError("5.1.2", "The N Appearance must be present"));
/*     */ 
/* 185 */         return false;
/*     */       }
/*     */ 
/* 190 */       COSBase apn = apDict.getItem(COSName.N);
/* 191 */       if (!COSUtils.isStream(apn, this.cosDocument))
/*     */       {
/* 193 */         this.ctx.addValidationError(new ValidationResult.ValidationError("5.3.1", "The N Appearance must be a Stream"));
/*     */ 
/* 195 */         return false;
/*     */       }
/*     */ 
/* 199 */       ContextHelper.validateElement(this.ctx, new PDXObjectForm(COSUtils.getAsStream(apn, this.cosDocument)), "graphic-process");
/*     */     }
/*     */ 
/* 203 */     return true;
/*     */   }
/*     */ 
/*     */   protected boolean checkActions()
/*     */     throws ValidationException
/*     */   {
/* 216 */     ContextHelper.validateElement(this.ctx, this.annotDictionary, "actions-process");
/* 217 */     return true;
/*     */   }
/*     */ 
/*     */   protected boolean checkPopup()
/*     */     throws ValidationException
/*     */   {
/* 230 */     COSBase cosPopup = this.annotDictionary.getItem("Popup");
/* 231 */     if (cosPopup != null)
/*     */     {
/* 233 */       COSDictionary popupDict = COSUtils.getAsDictionary(cosPopup, this.cosDocument);
/* 234 */       if (popupDict == null)
/*     */       {
/* 236 */         this.ctx.addValidationError(new ValidationResult.ValidationError("1.2.3", "An Annotation has a Popup entry, but the value is missing or isn't a dictionary"));
/*     */ 
/* 238 */         return false;
/*     */       }
/* 240 */       AnnotationValidator popupVal = this.annotFact.getAnnotationValidator(this.ctx, popupDict);
/* 241 */       return popupVal.validate();
/*     */     }
/* 243 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean validate()
/*     */     throws ValidationException
/*     */   {
/* 254 */     boolean isValide = checkMandatoryFields();
/* 255 */     isValide = (isValide) && (checkFlags());
/* 256 */     isValide = (isValide) && (checkColors());
/* 257 */     isValide = (isValide) && (checkAP());
/* 258 */     isValide = (isValide) && (checkCA());
/* 259 */     isValide = (isValide) && (checkActions());
/* 260 */     isValide = (isValide) && (checkPopup());
/* 261 */     return isValide;
/*     */   }
/*     */ 
/*     */   protected abstract boolean checkMandatoryFields();
/*     */ 
/*     */   public final void setFactory(AnnotationValidatorFactory fact)
/*     */   {
/* 282 */     this.annotFact = fact;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.annotation.AnnotationValidator
 * JD-Core Version:    0.6.2
 */