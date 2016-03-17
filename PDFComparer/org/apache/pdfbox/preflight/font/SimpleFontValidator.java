/*    */ package org.apache.pdfbox.preflight.font;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*    */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*    */ import org.apache.pdfbox.preflight.font.container.FontContainer;
/*    */ import org.apache.pdfbox.preflight.font.descriptor.FontDescriptorHelper;
/*    */ 
/*    */ public abstract class SimpleFontValidator<T extends FontContainer> extends FontValidator<T>
/*    */ {
/*    */   public SimpleFontValidator(PreflightContext context, PDFont font, T fContainer)
/*    */   {
/* 39 */     super(context, font, fContainer);
/*    */   }
/*    */ 
/*    */   public void validate()
/*    */     throws ValidationException
/*    */   {
/* 52 */     checkMandatoryField();
/*    */ 
/* 54 */     createFontDescriptorHelper();
/* 55 */     processFontDescriptorValidation();
/*    */ 
/* 57 */     checkEncoding();
/* 58 */     checkToUnicode();
/*    */   }
/*    */ 
/*    */   protected void checkMandatoryField()
/*    */   {
/* 63 */     COSDictionary fontDictionary = (COSDictionary)this.font.getCOSObject();
/* 64 */     boolean areFieldsPResent = fontDictionary.containsKey(COSName.TYPE);
/* 65 */     areFieldsPResent &= fontDictionary.containsKey(COSName.SUBTYPE);
/* 66 */     areFieldsPResent &= fontDictionary.containsKey(COSName.BASE_FONT);
/* 67 */     areFieldsPResent &= fontDictionary.containsKey(COSName.FIRST_CHAR);
/* 68 */     areFieldsPResent &= fontDictionary.containsKey(COSName.LAST_CHAR);
/* 69 */     areFieldsPResent &= fontDictionary.containsKey(COSName.WIDTHS);
/*    */ 
/* 71 */     if (!areFieldsPResent)
/*    */     {
/* 73 */       this.fontContainer.push(new ValidationResult.ValidationError("3.1.1", "Some required fields are missing from the Font dictionary."));
/*    */     }
/*    */   }
/*    */ 
/*    */   protected abstract void createFontDescriptorHelper();
/*    */ 
/*    */   protected void processFontDescriptorValidation()
/*    */   {
/* 82 */     this.descriptorHelper.validate();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.SimpleFontValidator
 * JD-Core Version:    0.6.2
 */