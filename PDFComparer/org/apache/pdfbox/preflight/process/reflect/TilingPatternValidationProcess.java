/*    */ package org.apache.pdfbox.preflight.process.reflect;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.cos.COSStream;
/*    */ import org.apache.pdfbox.pdmodel.PDPage;
/*    */ import org.apache.pdfbox.pdmodel.PDResources;
/*    */ import org.apache.pdfbox.pdmodel.graphics.pattern.PDTilingPatternResources;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.PreflightPath;
/*    */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*    */ import org.apache.pdfbox.preflight.content.ContentStreamWrapper;
/*    */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*    */ import org.apache.pdfbox.preflight.process.AbstractProcess;
/*    */ import org.apache.pdfbox.preflight.utils.ContextHelper;
/*    */ 
/*    */ public class TilingPatternValidationProcess extends AbstractProcess
/*    */ {
/*    */   public void validate(PreflightContext context)
/*    */     throws ValidationException
/*    */   {
/* 47 */     PreflightPath vPath = context.getValidationPath();
/* 48 */     if (vPath.isEmpty()) {
/* 49 */       return;
/*    */     }
/* 51 */     if (!vPath.isExpectedType(PDTilingPatternResources.class))
/*    */     {
/* 53 */       context.addValidationError(new ValidationResult.ValidationError("2.1.9", "Tiling pattern validation required at least a PDPage"));
/*    */     }
/*    */     else
/*    */     {
/* 57 */       PDTilingPatternResources tilingPattern = (PDTilingPatternResources)vPath.peek();
/* 58 */       PDPage page = (PDPage)vPath.getClosestPathElement(PDPage.class);
/*    */ 
/* 60 */       checkMandatoryFields(context, page, tilingPattern);
/* 61 */       parseResources(context, page, tilingPattern);
/* 62 */       parsePatternContent(context, page, tilingPattern);
/*    */     }
/*    */   }
/*    */ 
/*    */   protected void parseResources(PreflightContext context, PDPage page, PDTilingPatternResources pattern)
/*    */     throws ValidationException
/*    */   {
/* 69 */     PDResources resources = pattern.getResources();
/* 70 */     if (resources != null)
/*    */     {
/* 72 */       ContextHelper.validateElement(context, resources, "resources-process");
/*    */     }
/*    */   }
/*    */ 
/*    */   protected void parsePatternContent(PreflightContext context, PDPage page, PDTilingPatternResources pattern)
/*    */     throws ValidationException
/*    */   {
/* 82 */     ContentStreamWrapper csWrapper = new ContentStreamWrapper(context, page);
/* 83 */     csWrapper.validPatternContentStream((COSStream)pattern.getCOSObject());
/*    */   }
/*    */ 
/*    */   protected void checkMandatoryFields(PreflightContext context, PDPage page, PDTilingPatternResources pattern)
/*    */   {
/* 91 */     COSDictionary dictionary = pattern.getCOSDictionary();
/* 92 */     boolean res = dictionary.getItem(COSName.RESOURCES) != null;
/* 93 */     res = (res) && (dictionary.getItem(COSName.BBOX) != null);
/* 94 */     res = (res) && (dictionary.getItem(COSName.PAINT_TYPE) != null);
/* 95 */     res = (res) && (dictionary.getItem(COSName.TILING_TYPE) != null);
/* 96 */     res = (res) && (dictionary.getItem(COSName.X_STEP) != null);
/* 97 */     res = (res) && (dictionary.getItem(COSName.Y_STEP) != null);
/* 98 */     if (!res)
/*    */     {
/* 100 */       context.addValidationError(new ValidationResult.ValidationError("2.4.6"));
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.process.reflect.TilingPatternValidationProcess
 * JD-Core Version:    0.6.2
 */