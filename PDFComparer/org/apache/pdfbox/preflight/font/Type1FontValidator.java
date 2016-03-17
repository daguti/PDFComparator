/*    */ package org.apache.pdfbox.preflight.font;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSDocument;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.PreflightDocument;
/*    */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*    */ import org.apache.pdfbox.preflight.font.container.Type1Container;
/*    */ import org.apache.pdfbox.preflight.font.descriptor.Type1DescriptorHelper;
/*    */ import org.apache.pdfbox.preflight.utils.COSUtils;
/*    */ 
/*    */ public class Type1FontValidator extends SimpleFontValidator<Type1Container>
/*    */ {
/*    */   public Type1FontValidator(PreflightContext context, PDFont font)
/*    */   {
/* 47 */     super(context, font, new Type1Container(font));
/*    */   }
/*    */ 
/*    */   protected void createFontDescriptorHelper()
/*    */   {
/* 53 */     this.descriptorHelper = new Type1DescriptorHelper(this.context, this.font, (Type1Container)this.fontContainer);
/*    */   }
/*    */ 
/*    */   protected void checkEncoding()
/*    */   {
/* 58 */     COSBase encoding = ((COSDictionary)this.font.getCOSObject()).getItem(COSName.ENCODING);
/* 59 */     if (encoding != null)
/*    */     {
/* 61 */       COSDocument cosDocument = this.context.getDocument().getDocument();
/* 62 */       if (COSUtils.isString(encoding, cosDocument))
/*    */       {
/* 64 */         String encodingName = COSUtils.getAsString(encoding, cosDocument);
/* 65 */         if ((!encodingName.equals("MacRomanEncoding")) && (!encodingName.equals("MacExpertEncoding")) && (!encodingName.equals("WinAnsiEncoding")) && (!encodingName.equals("PDFDocEncoding")) && (!encodingName.equals("StandardEncoding")))
/*    */         {
/* 71 */           ((Type1Container)this.fontContainer).push(new ValidationResult.ValidationError("3.1.5"));
/*    */         }
/*    */       }
/* 74 */       else if (!COSUtils.isDictionary(encoding, cosDocument))
/*    */       {
/* 76 */         ((Type1Container)this.fontContainer).push(new ValidationResult.ValidationError("3.1.5"));
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.Type1FontValidator
 * JD-Core Version:    0.6.2
 */