/*     */ package org.apache.pdfbox.preflight.xobject;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.PreflightDocument;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ import org.apache.pdfbox.preflight.utils.COSUtils;
/*     */ 
/*     */ public abstract class AbstractXObjValidator
/*     */   implements XObjectValidator
/*     */ {
/*  47 */   protected COSStream xobject = null;
/*     */ 
/*  51 */   protected PreflightContext context = null;
/*     */ 
/*  55 */   protected COSDocument cosDocument = null;
/*     */ 
/*     */   public AbstractXObjValidator(PreflightContext context, COSStream xobj)
/*     */   {
/*  59 */     this.xobject = xobj;
/*  60 */     this.context = context;
/*  61 */     this.cosDocument = context.getDocument().getDocument();
/*     */   }
/*     */ 
/*     */   protected void checkSMask()
/*     */   {
/*  73 */     COSBase smask = this.xobject.getItem(COSName.SMASK);
/*  74 */     if ((smask != null) && ((!COSUtils.isString(smask, this.cosDocument)) || (!"None".equals(COSUtils.getAsString(smask, this.cosDocument)))))
/*     */     {
/*  78 */       this.context.addValidationError(new ValidationResult.ValidationError("2.2.2", "Soft Mask must be null or None"));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void checkOPI()
/*     */   {
/*  94 */     if (this.xobject.getItem(COSName.getPDFName("OPI")) != null)
/*     */     {
/*  96 */       this.context.addValidationError(new ValidationResult.ValidationError("2.3", "Unexpected 'OPI' Key"));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void checkReferenceXObject()
/*     */   {
/* 108 */     if (this.xobject.getItem("Ref") != null)
/*     */     {
/* 110 */       this.context.addValidationError(new ValidationResult.ValidationError("2.3", "No reference Xobject allowed in PDF/A"));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void checkPostscriptXObject()
/*     */   {
/* 124 */     String subtype = this.xobject.getNameAsString(COSName.SUBTYPE);
/* 125 */     if ((subtype != null) && ("PS".equals(subtype)))
/*     */     {
/* 127 */       this.context.addValidationError(new ValidationResult.ValidationError("2.3.2", "No Postscript Xobject allowed in PDF/A"));
/*     */ 
/* 129 */       return;
/*     */     }
/* 131 */     if (this.xobject.getItem(COSName.getPDFName("Subtype2")) != null)
/*     */     {
/* 133 */       this.context.addValidationError(new ValidationResult.ValidationError("2.3.2", "No Postscript Xobject allowed in PDF/A (Subtype2)"));
/*     */ 
/* 135 */       return;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected abstract void checkMandatoryFields();
/*     */ 
/*     */   public void validate()
/*     */     throws ValidationException
/*     */   {
/* 152 */     checkMandatoryFields();
/* 153 */     checkOPI();
/* 154 */     checkSMask();
/* 155 */     checkReferenceXObject();
/* 156 */     checkPostscriptXObject();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.xobject.AbstractXObjValidator
 * JD-Core Version:    0.6.2
 */