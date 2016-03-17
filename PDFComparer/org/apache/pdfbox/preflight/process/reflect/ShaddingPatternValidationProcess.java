/*     */ package org.apache.pdfbox.preflight.process.reflect;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.PDShadingResources;
/*     */ import org.apache.pdfbox.preflight.PreflightConfiguration;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.PreflightPath;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ import org.apache.pdfbox.preflight.graphic.ColorSpaceHelper;
/*     */ import org.apache.pdfbox.preflight.graphic.ColorSpaceHelperFactory;
/*     */ import org.apache.pdfbox.preflight.graphic.ColorSpaceHelperFactory.ColorSpaceRestriction;
/*     */ import org.apache.pdfbox.preflight.process.AbstractProcess;
/*     */ import org.apache.pdfbox.preflight.utils.ContextHelper;
/*     */ 
/*     */ public class ShaddingPatternValidationProcess extends AbstractProcess
/*     */ {
/*     */   public void validate(PreflightContext context)
/*     */     throws ValidationException
/*     */   {
/*  51 */     PreflightPath vPath = context.getValidationPath();
/*  52 */     if (vPath.isEmpty()) {
/*  53 */       return;
/*     */     }
/*  55 */     if (!vPath.isExpectedType(PDShadingResources.class))
/*     */     {
/*  57 */       context.addValidationError(new ValidationResult.ValidationError("2.1.9", "ShadingPattern validation required at least a PDResources"));
/*     */     }
/*     */     else
/*     */     {
/*  61 */       PDShadingResources shaddingResource = (PDShadingResources)vPath.peek();
/*  62 */       PDPage page = (PDPage)vPath.getClosestPathElement(PDPage.class);
/*  63 */       checkColorSpace(context, page, shaddingResource);
/*  64 */       checkGraphicState(context, page, shaddingResource);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void checkColorSpace(PreflightContext context, PDPage page, PDShadingResources shadingRes)
/*     */     throws ValidationException
/*     */   {
/*     */     try
/*     */     {
/*  84 */       PDColorSpace pColorSpace = shadingRes.getColorSpace();
/*  85 */       PreflightConfiguration config = context.getConfig();
/*  86 */       ColorSpaceHelperFactory csFact = config.getColorSpaceHelperFact();
/*  87 */       ColorSpaceHelper csh = csFact.getColorSpaceHelper(context, pColorSpace, ColorSpaceHelperFactory.ColorSpaceRestriction.NO_PATTERN);
/*  88 */       csh.validate();
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*  92 */       context.addValidationError(new ValidationResult.ValidationError("2.4.4", e.getMessage()));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void checkGraphicState(PreflightContext context, PDPage page, PDShadingResources shadingRes)
/*     */     throws ValidationException
/*     */   {
/* 106 */     COSDictionary resources = (COSDictionary)shadingRes.getCOSDictionary().getDictionaryObject("ExtGState");
/*     */ 
/* 108 */     if (resources != null)
/*     */     {
/* 110 */       ContextHelper.validateElement(context, resources, "extgstate-process");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.process.reflect.ShaddingPatternValidationProcess
 * JD-Core Version:    0.6.2
 */