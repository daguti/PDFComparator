/*     */ package org.apache.pdfbox.preflight.xobject;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSBoolean;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpaceFactory;
/*     */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
/*     */ import org.apache.pdfbox.preflight.PreflightConfiguration;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ import org.apache.pdfbox.preflight.graphic.ColorSpaceHelper;
/*     */ import org.apache.pdfbox.preflight.graphic.ColorSpaceHelperFactory;
/*     */ import org.apache.pdfbox.preflight.graphic.ColorSpaceHelperFactory.ColorSpaceRestriction;
/*     */ import org.apache.pdfbox.preflight.utils.COSUtils;
/*     */ import org.apache.pdfbox.preflight.utils.RenderingIntents;
/*     */ 
/*     */ public class XObjImageValidator extends AbstractXObjValidator
/*     */ {
/*  52 */   protected PDXObjectImage xImage = null;
/*     */ 
/*     */   public XObjImageValidator(PreflightContext context, PDXObjectImage xobj)
/*     */   {
/*  56 */     super(context, xobj.getCOSStream());
/*  57 */     this.xImage = xobj;
/*     */   }
/*     */ 
/*     */   protected void checkMandatoryFields()
/*     */   {
/*  63 */     boolean res = this.xobject.getItem(COSName.WIDTH) != null;
/*  64 */     res = (res) && (this.xobject.getItem(COSName.HEIGHT) != null);
/*     */ 
/*  66 */     if (!res)
/*     */     {
/*  68 */       this.context.addValidationError(new ValidationResult.ValidationError("2.1.7"));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void checkAlternates()
/*     */     throws ValidationException
/*     */   {
/*  77 */     if (this.xobject.getItem("Alternates") != null)
/*     */     {
/*  79 */       this.context.addValidationError(new ValidationResult.ValidationError("2.3", "Unexpected 'Alternates' Key"));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void checkInterpolate()
/*     */     throws ValidationException
/*     */   {
/*  88 */     if (this.xobject.getItem("Interpolate") != null)
/*     */     {
/*  90 */       if (this.xobject.getBoolean("Interpolate", true))
/*     */       {
/*  92 */         this.context.addValidationError(new ValidationResult.ValidationError("2.3.2", "Unexpected 'true' value for 'Interpolate' Key"));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void checkIntent()
/*     */     throws ValidationException
/*     */   {
/* 103 */     if (this.xobject.getItem("Intent") != null)
/*     */     {
/* 105 */       String s = this.xobject.getNameAsString("Intent");
/* 106 */       if (!RenderingIntents.contains(s))
/*     */       {
/* 108 */         this.context.addValidationError(new ValidationResult.ValidationError("2.3.2", "Unexpected value '" + s + "' for Intent key in image"));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void checkColorSpaceAndImageMask()
/*     */     throws ValidationException
/*     */   {
/* 120 */     COSBase csImg = this.xobject.getItem(COSName.COLORSPACE);
/* 121 */     COSBase bitsPerComp = this.xobject.getItem("BitsPerComponent");
/* 122 */     COSBase mask = this.xobject.getItem(COSName.MASK);
/*     */ 
/* 124 */     if (isImageMaskTrue())
/*     */     {
/* 126 */       if ((csImg != null) || (mask != null))
/*     */       {
/* 128 */         this.context.addValidationError(new ValidationResult.ValidationError("2.3", "ImageMask entry is true, ColorSpace and Mask are forbidden."));
/*     */       }
/*     */ 
/* 132 */       Integer bitsPerCompValue = COSUtils.getAsInteger(bitsPerComp, this.cosDocument);
/* 133 */       if ((bitsPerCompValue != null) && (bitsPerCompValue.intValue() != 1))
/*     */       {
/* 135 */         this.context.addValidationError(new ValidationResult.ValidationError("2.3.2", "ImageMask entry is true, BitsPerComponent must be absent or 1."));
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/*     */       try
/*     */       {
/* 144 */         PreflightConfiguration config = this.context.getConfig();
/* 145 */         ColorSpaceHelperFactory csFact = config.getColorSpaceHelperFact();
/* 146 */         PDColorSpace pdCS = PDColorSpaceFactory.createColorSpace(csImg);
/* 147 */         ColorSpaceHelper csh = csFact.getColorSpaceHelper(this.context, pdCS, ColorSpaceHelperFactory.ColorSpaceRestriction.NO_PATTERN);
/* 148 */         csh.validate();
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 152 */         this.context.addValidationError(new ValidationResult.ValidationError("2.4.4"));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean isImageMaskTrue()
/*     */   {
/* 159 */     COSBase imgMask = this.xobject.getItem("ImageMask");
/* 160 */     if ((imgMask != null) && ((imgMask instanceof COSBoolean)))
/*     */     {
/* 162 */       return ((COSBoolean)imgMask).getValue();
/*     */     }
/*     */ 
/* 166 */     return false;
/*     */   }
/*     */ 
/*     */   public void validate()
/*     */     throws ValidationException
/*     */   {
/* 178 */     super.validate();
/*     */ 
/* 180 */     checkAlternates();
/* 181 */     checkInterpolate();
/* 182 */     checkIntent();
/*     */ 
/* 184 */     checkColorSpaceAndImageMask();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.xobject.XObjImageValidator
 * JD-Core Version:    0.6.2
 */