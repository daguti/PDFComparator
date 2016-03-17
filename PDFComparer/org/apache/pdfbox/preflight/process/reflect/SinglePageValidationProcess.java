/*     */ package org.apache.pdfbox.preflight.process.reflect;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSObject;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.PDResources;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*     */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObject;
/*     */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
/*     */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
/*     */ import org.apache.pdfbox.preflight.PreflightConfiguration;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.PreflightDocument;
/*     */ import org.apache.pdfbox.preflight.PreflightPath;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.content.ContentStreamWrapper;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ import org.apache.pdfbox.preflight.graphic.ColorSpaceHelper;
/*     */ import org.apache.pdfbox.preflight.graphic.ColorSpaceHelperFactory;
/*     */ import org.apache.pdfbox.preflight.graphic.ColorSpaceHelperFactory.ColorSpaceRestriction;
/*     */ import org.apache.pdfbox.preflight.process.AbstractProcess;
/*     */ import org.apache.pdfbox.preflight.utils.COSUtils;
/*     */ import org.apache.pdfbox.preflight.utils.ContextHelper;
/*     */ 
/*     */ public class SinglePageValidationProcess extends AbstractProcess
/*     */ {
/*     */   public void validate(PreflightContext context)
/*     */     throws ValidationException
/*     */   {
/*  68 */     PreflightPath vPath = context.getValidationPath();
/*  69 */     if (vPath.isEmpty()) {
/*  70 */       return;
/*     */     }
/*  72 */     if (!vPath.isExpectedType(PDPage.class))
/*     */     {
/*  74 */       addValidationError(context, new ValidationResult.ValidationError("8.1", "Page validation required at least a PDPage"));
/*     */     }
/*     */     else
/*     */     {
/*  78 */       PDPage page = (PDPage)vPath.peek();
/*  79 */       validateActions(context, page);
/*  80 */       validateAnnotation(context, page);
/*  81 */       validateColorSpaces(context, page);
/*  82 */       validateResources(context, page);
/*  83 */       validateGraphicObjects(context, page);
/*  84 */       validateGroupTransparency(context, page);
/*     */ 
/*  87 */       validateContent(context, page);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void validateActions(PreflightContext context, PDPage page)
/*     */     throws ValidationException
/*     */   {
/* 101 */     ContextHelper.validateElement(context, page.getCOSDictionary(), "actions-process");
/*     */   }
/*     */ 
/*     */   protected void validateColorSpaces(PreflightContext context, PDPage page)
/*     */     throws ValidationException
/*     */   {
/* 113 */     PDResources resources = page.getResources();
/*     */     ColorSpaceHelperFactory colorSpaceFactory;
/* 114 */     if (resources != null)
/*     */     {
/* 116 */       Map colorSpaces = resources.getColorSpaces();
/* 117 */       if (colorSpaces != null)
/*     */       {
/* 119 */         PreflightConfiguration config = context.getConfig();
/* 120 */         colorSpaceFactory = config.getColorSpaceHelperFact();
/* 121 */         for (PDColorSpace pdCS : colorSpaces.values())
/*     */         {
/* 123 */           ColorSpaceHelper csHelper = colorSpaceFactory.getColorSpaceHelper(context, pdCS, ColorSpaceHelperFactory.ColorSpaceRestriction.NO_RESTRICTION);
/*     */ 
/* 125 */           csHelper.validate();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void validateGraphicObjects(PreflightContext context, PDPage page)
/*     */     throws ValidationException
/*     */   {
/* 141 */     COSBase thumbBase = page.getCOSDictionary().getItem("Thumb");
/* 142 */     if (thumbBase != null)
/*     */     {
/*     */       try
/*     */       {
/* 146 */         if ((thumbBase instanceof COSObject))
/*     */         {
/* 148 */           thumbBase = ((COSObject)thumbBase).getObject();
/*     */         }
/* 150 */         PDXObject thumbImg = PDXObjectImage.createThumbnailXObject(thumbBase);
/* 151 */         ContextHelper.validateElement(context, thumbImg, "graphic-process");
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 155 */         context.addValidationError(new ValidationResult.ValidationError("2.1", "Unable to read Thumb image : " + e.getMessage()));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void validateResources(PreflightContext context, PDPage page)
/*     */     throws ValidationException
/*     */   {
/* 163 */     ContextHelper.validateElement(context, page.getResources(), "resources-process");
/*     */   }
/*     */ 
/*     */   protected void validateContent(PreflightContext context, PDPage page)
/*     */     throws ValidationException
/*     */   {
/*     */     try
/*     */     {
/* 178 */       ContentStreamWrapper csWrapper = new ContentStreamWrapper(context, page);
/* 179 */       csWrapper.validPageContentStream();
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 183 */       context.addValidationError(new ValidationResult.ValidationError("-1", e.getMessage()));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void validateAnnotation(PreflightContext context, PDPage page)
/*     */     throws ValidationException
/*     */   {
/*     */     try
/*     */     {
/* 197 */       List lAnnots = page.getAnnotations();
/* 198 */       for (i$ = lAnnots.iterator(); i$.hasNext(); ) { Object object = i$.next();
/*     */ 
/* 200 */         if ((object instanceof PDAnnotation))
/*     */         {
/* 202 */           COSDictionary cosAnnot = ((PDAnnotation)object).getDictionary();
/* 203 */           ContextHelper.validateElement(context, cosAnnot, "annotations-process");
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*     */       Iterator i$;
/* 209 */       if ((e instanceof ValidationException))
/*     */       {
/* 211 */         throw ((ValidationException)e);
/*     */       }
/*     */ 
/* 214 */       throw new ValidationException("Unable to access Annotation", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void validateGroupTransparency(PreflightContext context, PDPage page)
/*     */     throws ValidationException
/*     */   {
/* 227 */     COSBase baseGroup = page.getCOSDictionary().getItem("Group");
/* 228 */     COSDictionary groupDictionary = COSUtils.getAsDictionary(baseGroup, context.getDocument().getDocument());
/* 229 */     if (groupDictionary != null)
/*     */     {
/* 231 */       String sVal = groupDictionary.getNameAsString(COSName.S);
/* 232 */       if ("Transparency".equals(sVal))
/*     */       {
/* 234 */         context.addValidationError(new ValidationResult.ValidationError("2.2.1", "Group has a transparency S entry or the S entry is null."));
/*     */ 
/* 236 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.process.reflect.SinglePageValidationProcess
 * JD-Core Version:    0.6.2
 */