/*     */ package org.apache.pdfbox.preflight.process.reflect;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdmodel.PDResources;
/*     */ import org.apache.pdfbox.pdmodel.graphics.pattern.PDTilingPatternResources;
/*     */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObject;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.PreflightDocument;
/*     */ import org.apache.pdfbox.preflight.PreflightPath;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ import org.apache.pdfbox.preflight.process.AbstractProcess;
/*     */ import org.apache.pdfbox.preflight.utils.COSUtils;
/*     */ import org.apache.pdfbox.preflight.utils.ContextHelper;
/*     */ 
/*     */ public class ResourcesValidationProcess extends AbstractProcess
/*     */ {
/*     */   public void validate(PreflightContext ctx)
/*     */     throws ValidationException
/*     */   {
/*  61 */     PreflightPath vPath = ctx.getValidationPath();
/*  62 */     if (vPath.isEmpty()) {
/*  63 */       return;
/*     */     }
/*  65 */     if (!vPath.isExpectedType(PDResources.class))
/*     */     {
/*  67 */       addValidationError(ctx, new ValidationResult.ValidationError("8.1", "Resources validation process needs at least one PDResources object"));
/*     */     }
/*     */     else
/*     */     {
/*  72 */       PDResources resources = (PDResources)vPath.peek();
/*     */ 
/*  74 */       validateFonts(ctx, resources);
/*  75 */       validateExtGStates(ctx, resources);
/*  76 */       validateShadingPattern(ctx, resources);
/*  77 */       validateTilingPattern(ctx, resources);
/*  78 */       validateXObjects(ctx, resources);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void validateFonts(PreflightContext context, PDResources resources)
/*     */     throws ValidationException
/*     */   {
/*  91 */     Map mapOfFonts = resources.getFonts();
/*  92 */     if (mapOfFonts != null)
/*     */     {
/*  94 */       for (Map.Entry entry : mapOfFonts.entrySet())
/*     */       {
/*  96 */         ContextHelper.validateElement(context, entry.getValue(), "font-process");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void validateExtGStates(PreflightContext context, PDResources resources)
/*     */     throws ValidationException
/*     */   {
/* 109 */     COSBase egsEntry = resources.getCOSDictionary().getItem("ExtGState");
/* 110 */     COSDocument cosDocument = context.getDocument().getDocument();
/* 111 */     COSDictionary extGState = COSUtils.getAsDictionary(egsEntry, cosDocument);
/* 112 */     if (egsEntry != null)
/*     */     {
/* 114 */       ContextHelper.validateElement(context, extGState, "extgstate-process");
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void validateShadingPattern(PreflightContext context, PDResources resources)
/*     */     throws ValidationException
/*     */   {
/*     */     try
/*     */     {
/* 129 */       Map shadingResources = resources.getShadings();
/* 130 */       if (shadingResources != null)
/*     */       {
/* 132 */         for (Map.Entry entry : shadingResources.entrySet())
/*     */         {
/* 134 */           ContextHelper.validateElement(context, entry.getValue(), "shadding-pattern-process");
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 140 */       context.addValidationError(new ValidationResult.ValidationError("2.4.6", e.getMessage()));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void validateTilingPattern(PreflightContext context, PDResources resources)
/*     */     throws ValidationException
/*     */   {
/*     */     try
/*     */     {
/* 155 */       Map patternResources = resources.getPatterns();
/* 156 */       if (patternResources != null)
/*     */       {
/* 158 */         for (Map.Entry entry : patternResources.entrySet())
/*     */         {
/* 160 */           if ((entry.getValue() instanceof PDTilingPatternResources))
/*     */           {
/* 162 */             ContextHelper.validateElement(context, entry.getValue(), "tiling-pattern-process");
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 169 */       context.addValidationError(new ValidationResult.ValidationError("2.4.6", e.getMessage()));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void validateXObjects(PreflightContext context, PDResources resources) throws ValidationException
/*     */   {
/* 175 */     COSDocument cosDocument = context.getDocument().getDocument();
/* 176 */     COSDictionary mapOfXObj = COSUtils.getAsDictionary(resources.getCOSDictionary().getItem(COSName.XOBJECT), cosDocument);
/*     */ 
/* 178 */     if (mapOfXObj != null)
/*     */     {
/* 180 */       for (Map.Entry entry : mapOfXObj.entrySet())
/*     */       {
/* 182 */         COSBase xobj = (COSBase)entry.getValue();
/* 183 */         if ((xobj != null) && (COSUtils.isStream(xobj, cosDocument)))
/*     */         {
/*     */           try
/*     */           {
/* 187 */             COSStream stream = COSUtils.getAsStream(xobj, cosDocument);
/* 188 */             PDXObject pdXObject = PDXObject.createXObject(stream);
/* 189 */             if (pdXObject != null)
/*     */             {
/* 191 */               ContextHelper.validateElement(context, pdXObject, "graphic-process");
/*     */             }
/*     */             else
/*     */             {
/* 195 */               ContextHelper.validateElement(context, stream, "graphic-process");
/*     */             }
/*     */           }
/*     */           catch (IOException e)
/*     */           {
/* 200 */             throw new ValidationException(e.getMessage(), e);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.process.reflect.ResourcesValidationProcess
 * JD-Core Version:    0.6.2
 */