/*     */ package org.apache.pdfbox.preflight.process;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
/*     */ import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
/*     */ import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.PreflightDocument;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ import org.apache.pdfbox.preflight.utils.COSUtils;
/*     */ import org.apache.pdfbox.preflight.utils.ContextHelper;
/*     */ 
/*     */ public class BookmarkValidationProcess extends AbstractProcess
/*     */ {
/*     */   public void validate(PreflightContext ctx)
/*     */     throws ValidationException
/*     */   {
/*  46 */     PDDocumentCatalog catalog = ctx.getDocument().getDocumentCatalog();
/*  47 */     if (catalog != null)
/*     */     {
/*  49 */       PDDocumentOutline outlineHierarchy = catalog.getDocumentOutline();
/*  50 */       if (outlineHierarchy != null)
/*     */       {
/*  53 */         if ((!isCountEntryPresent(outlineHierarchy.getCOSDictionary())) && ((outlineHierarchy.getFirstChild() != null) || (outlineHierarchy.getLastChild() != null)))
/*     */         {
/*  56 */           addValidationError(ctx, new ValidationResult.ValidationError("1.4.9", "Outline Hierarchy doesn't have Count entry"));
/*     */         }
/*  59 */         else if ((isCountEntryPositive(ctx, outlineHierarchy.getCOSDictionary())) && ((outlineHierarchy.getFirstChild() == null) || (outlineHierarchy.getLastChild() == null)))
/*     */         {
/*  62 */           addValidationError(ctx, new ValidationResult.ValidationError("1.4.9", "Outline Hierarchy doesn't have First and/or Last entry(ies)"));
/*     */         }
/*     */         else
/*     */         {
/*  67 */           exploreOutlineLevel(ctx, outlineHierarchy.getFirstChild());
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*  73 */       ctx.addValidationError(new ValidationResult.ValidationError("1.2.14", "There are no Catalog entry in the Document."));
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean isCountEntryPresent(COSDictionary outline)
/*     */   {
/*  85 */     return outline.getItem(COSName.getPDFName("Count")) != null;
/*     */   }
/*     */ 
/*     */   private boolean isCountEntryPositive(PreflightContext ctx, COSDictionary outline)
/*     */   {
/*  97 */     COSBase countBase = outline.getItem(COSName.getPDFName("Count"));
/*  98 */     COSDocument cosDocument = ctx.getDocument().getDocument();
/*  99 */     return (COSUtils.isInteger(countBase, cosDocument)) && (COSUtils.getAsInteger(countBase, cosDocument).intValue() > 0);
/*     */   }
/*     */ 
/*     */   protected boolean exploreOutlineLevel(PreflightContext ctx, PDOutlineItem inputItem)
/*     */     throws ValidationException
/*     */   {
/* 116 */     PDOutlineItem currentItem = inputItem;
/* 117 */     while (currentItem != null)
/*     */     {
/* 119 */       if (!validateItem(ctx, currentItem))
/*     */       {
/* 121 */         return false;
/*     */       }
/* 123 */       currentItem = currentItem.getNextSibling();
/*     */     }
/* 125 */     return true;
/*     */   }
/*     */ 
/*     */   protected boolean validateItem(PreflightContext ctx, PDOutlineItem inputItem)
/*     */     throws ValidationException
/*     */   {
/* 143 */     boolean isValid = true;
/*     */ 
/* 147 */     COSDictionary dictionary = inputItem.getCOSDictionary();
/* 148 */     COSBase dest = dictionary.getItem(COSName.DEST);
/* 149 */     COSBase action = dictionary.getItem(COSName.A);
/*     */ 
/* 151 */     if ((action != null) && (dest != null))
/*     */     {
/* 153 */       addValidationError(ctx, new ValidationResult.ValidationError("1.4.9", "Dest entry isn't permitted if the A entry is present"));
/*     */ 
/* 155 */       return false;
/*     */     }
/* 157 */     if (action != null)
/*     */     {
/* 159 */       ContextHelper.validateElement(ctx, dictionary, "actions-process");
/*     */     }
/*     */ 
/* 163 */     PDOutlineItem fChild = inputItem.getFirstChild();
/* 164 */     if (fChild != null)
/*     */     {
/* 166 */       if (!isCountEntryPresent(inputItem.getCOSDictionary()))
/*     */       {
/* 168 */         addValidationError(ctx, new ValidationResult.ValidationError("1.4.9", "Outline item doesn't have Count entry but has at least one descendant."));
/*     */ 
/* 170 */         isValid = false;
/*     */       }
/*     */       else
/*     */       {
/* 175 */         isValid = (isValid) && (exploreOutlineLevel(ctx, fChild));
/*     */       }
/*     */     }
/*     */ 
/* 179 */     return isValid;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.process.BookmarkValidationProcess
 * JD-Core Version:    0.6.2
 */