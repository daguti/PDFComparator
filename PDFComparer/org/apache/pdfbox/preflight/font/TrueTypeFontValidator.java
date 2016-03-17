/*    */ package org.apache.pdfbox.preflight.font;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.encoding.Encoding;
/*    */ import org.apache.pdfbox.encoding.MacRomanEncoding;
/*    */ import org.apache.pdfbox.encoding.WinAnsiEncoding;
/*    */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*    */ import org.apache.pdfbox.pdmodel.font.PDFontDescriptor;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*    */ import org.apache.pdfbox.preflight.font.container.TrueTypeContainer;
/*    */ import org.apache.pdfbox.preflight.font.descriptor.TrueTypeDescriptorHelper;
/*    */ 
/*    */ public class TrueTypeFontValidator extends SimpleFontValidator<TrueTypeContainer>
/*    */ {
/*    */   public TrueTypeFontValidator(PreflightContext context, PDFont font)
/*    */   {
/* 43 */     super(context, font, new TrueTypeContainer(font));
/*    */   }
/*    */ 
/*    */   protected void createFontDescriptorHelper()
/*    */   {
/* 48 */     this.descriptorHelper = new TrueTypeDescriptorHelper(this.context, this.font, (TrueTypeContainer)this.fontContainer);
/*    */   }
/*    */ 
/*    */   protected void checkEncoding()
/*    */   {
/* 53 */     PDFontDescriptor fd = this.font.getFontDescriptor();
/* 54 */     if (fd != null)
/*    */     {
/* 59 */       if (fd.isNonSymbolic())
/*    */       {
/* 61 */         Encoding encodingValue = this.font.getFontEncoding();
/* 62 */         if ((encodingValue == null) || ((!(encodingValue instanceof MacRomanEncoding)) && (!(encodingValue instanceof WinAnsiEncoding))))
/*    */         {
/* 65 */           ((TrueTypeContainer)this.fontContainer).push(new ValidationResult.ValidationError("3.1.5", "The Encoding is invalid for the NonSymbolic TTF"));
/*    */         }
/*    */ 
/*    */       }
/*    */ 
/* 74 */       if ((fd.isSymbolic()) && (((COSDictionary)this.font.getCOSObject()).getItem(COSName.ENCODING) != null))
/*    */       {
/* 76 */         ((TrueTypeContainer)this.fontContainer).push(new ValidationResult.ValidationError("3.1.5", "The Encoding should be missing for the Symbolic TTF"));
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.TrueTypeFontValidator
 * JD-Core Version:    0.6.2
 */