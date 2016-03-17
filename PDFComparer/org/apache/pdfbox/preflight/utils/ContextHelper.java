/*     */ package org.apache.pdfbox.preflight.utils;
/*     */ 
/*     */ import org.apache.pdfbox.pdmodel.PDResources;
/*     */ import org.apache.pdfbox.preflight.PreflightConfiguration;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.PreflightPath;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ import org.apache.pdfbox.preflight.process.ValidationProcess;
/*     */ 
/*     */ public class ContextHelper
/*     */ {
/*     */   public static void validateElement(PreflightContext context, Object element, String processName)
/*     */     throws ValidationException
/*     */   {
/*  48 */     if (element == null)
/*     */     {
/*  50 */       context.addValidationError(new ValidationResult.ValidationError("8.1", "Unable to process an element if it is null."));
/*     */     }
/*     */     else
/*     */     {
/*  54 */       callValidation(context, element, processName);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void callValidation(PreflightContext context, Object element, String processName)
/*     */     throws ValidationException
/*     */   {
/*  71 */     PreflightPath validationPath = context.getValidationPath();
/*     */ 
/*  73 */     if (hasRecursion(context, element, validationPath))
/*     */     {
/*  75 */       return;
/*     */     }
/*     */ 
/*  78 */     boolean needPop = validationPath.pushObject(element);
/*  79 */     PreflightConfiguration config = context.getConfig();
/*  80 */     ValidationProcess process = config.getInstanceOfProcess(processName);
/*  81 */     process.validate(context);
/*  82 */     if (needPop)
/*  83 */       validationPath.pop();
/*     */   }
/*     */ 
/*     */   public static void validateElement(PreflightContext context, String processName)
/*     */     throws ValidationException
/*     */   {
/*  96 */     callValidation(context, null, processName);
/*     */   }
/*     */ 
/*     */   private static boolean hasRecursion(PreflightContext context, Object element, PreflightPath validationPath)
/*     */   {
/* 102 */     if ((element instanceof PDResources))
/*     */     {
/* 104 */       for (int i = 0; i < validationPath.size(); i++)
/*     */       {
/* 106 */         Object obj = validationPath.getPathElement(i, Object.class);
/* 107 */         if ((obj instanceof PDResources))
/*     */         {
/* 109 */           PDResources pdRes = (PDResources)obj;
/* 110 */           if (pdRes.getCOSObject() == ((PDResources)element).getCOSObject())
/*     */           {
/* 112 */             context.addValidationError(new ValidationResult.ValidationError("8", "Resources recursion"));
/* 113 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 118 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.utils.ContextHelper
 * JD-Core Version:    0.6.2
 */