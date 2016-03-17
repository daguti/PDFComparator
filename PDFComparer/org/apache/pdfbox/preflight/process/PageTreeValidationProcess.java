/*    */ package org.apache.pdfbox.preflight.process;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
/*    */ import org.apache.pdfbox.pdmodel.PDPage;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.PreflightDocument;
/*    */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*    */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*    */ import org.apache.pdfbox.preflight.utils.ContextHelper;
/*    */ 
/*    */ public class PageTreeValidationProcess extends AbstractProcess
/*    */ {
/*    */   public void validate(PreflightContext context)
/*    */     throws ValidationException
/*    */   {
/* 41 */     PDDocumentCatalog catalog = context.getDocument().getDocumentCatalog();
/* 42 */     if (catalog != null)
/*    */     {
/* 44 */       List pages = catalog.getAllPages();
/* 45 */       for (int i = 0; i < pages.size(); i++)
/*    */       {
/* 47 */         validatePage(context, (PDPage)pages.get(i));
/*    */       }
/*    */     }
/*    */     else
/*    */     {
/* 52 */       context.addValidationError(new ValidationResult.ValidationError("1.2.14", "There are no Catalog entry in the Document."));
/*    */     }
/*    */   }
/*    */ 
/*    */   protected void validatePage(PreflightContext context, PDPage page) throws ValidationException
/*    */   {
/* 58 */     ContextHelper.validateElement(context, page, "page-process");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.process.PageTreeValidationProcess
 * JD-Core Version:    0.6.2
 */