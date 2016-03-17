/*     */ package org.apache.pdfbox.preflight.process;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
/*     */ import org.apache.pdfbox.pdmodel.interactive.action.PDFormFieldAdditionalActions;
/*     */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
/*     */ import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
/*     */ import org.apache.pdfbox.pdmodel.interactive.form.PDField;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.PreflightDocument;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ import org.apache.pdfbox.preflight.utils.ContextHelper;
/*     */ 
/*     */ public class AcroFormValidationProcess extends AbstractProcess
/*     */ {
/*     */   public void validate(PreflightContext ctx)
/*     */     throws ValidationException
/*     */   {
/*  53 */     PDDocumentCatalog catalog = ctx.getDocument().getDocumentCatalog();
/*  54 */     if (catalog != null)
/*     */     {
/*  56 */       PDAcroForm acroForm = catalog.getAcroForm();
/*  57 */       if (acroForm != null)
/*     */       {
/*  59 */         checkNeedAppearences(ctx, acroForm);
/*     */         try
/*     */         {
/*  62 */           exploreFields(ctx, acroForm.getFields());
/*     */         }
/*     */         catch (IOException e)
/*     */         {
/*  66 */           throw new ValidationException("Unable to get the list of fields : " + e.getMessage(), e);
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*  72 */       ctx.addValidationError(new ValidationResult.ValidationError("1.2.14", "There are no Catalog entry in the Document."));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void checkNeedAppearences(PreflightContext ctx, PDAcroForm acroForm)
/*     */   {
/*  87 */     if (acroForm.getDictionary().getBoolean("NeedAppearances", false))
/*     */     {
/*  89 */       addValidationError(ctx, new ValidationResult.ValidationError("1.2.3", "NeedAppearance is present with the value \"true\""));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected boolean exploreFields(PreflightContext ctx, List<?> lFields)
/*     */     throws IOException
/*     */   {
/*     */     Iterator i$;
/* 104 */     if (lFields != null)
/*     */     {
/* 107 */       for (i$ = lFields.iterator(); i$.hasNext(); ) { Object obj = i$.next();
/*     */ 
/* 109 */         if ((obj instanceof PDField))
/*     */         {
/* 111 */           if (!valideField(ctx, (PDField)obj))
/*     */           {
/* 113 */             return false;
/*     */           }
/*     */         }
/* 116 */         else if ((obj instanceof PDAnnotationWidget))
/*     */         {
/* 119 */           ContextHelper.validateElement(ctx, ((PDAnnotationWidget)obj).getDictionary(), "annotations-process");
/*     */         }
/*     */         else
/*     */         {
/* 123 */           addValidationError(ctx, new ValidationResult.ValidationError("1.2", "Field can only have fields or widget annotations as KIDS"));
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 128 */     return true;
/*     */   }
/*     */ 
/*     */   protected boolean valideField(PreflightContext ctx, PDField aField)
/*     */     throws IOException
/*     */   {
/* 145 */     boolean res = true;
/* 146 */     PDFormFieldAdditionalActions aa = aField.getActions();
/* 147 */     if (aa != null)
/*     */     {
/* 149 */       addValidationError(ctx, new ValidationResult.ValidationError("6.2.3", "\"AA\" must not be used in a Field dictionary"));
/*     */ 
/* 151 */       res = false;
/*     */     }
/*     */ 
/* 157 */     PDAnnotationWidget widget = aField.getWidget();
/* 158 */     if ((res) && (widget != null))
/*     */     {
/* 160 */       ContextHelper.validateElement(ctx, widget.getDictionary(), "annotations-process");
/* 161 */       COSBase act = widget.getDictionary().getDictionaryObject(COSName.A);
/* 162 */       if (act != null)
/*     */       {
/* 164 */         addValidationError(ctx, new ValidationResult.ValidationError("6.2.4", "\"A\" must not be used in a Field dictionary"));
/*     */ 
/* 166 */         res = false;
/*     */       }
/*     */     }
/*     */ 
/* 170 */     res = (res) && (exploreFields(ctx, aField.getKids()));
/* 171 */     return res;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.process.AcroFormValidationProcess
 * JD-Core Version:    0.6.2
 */