/*    */ package org.apache.pdfbox.preflight.font;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.font.container.CIDType0Container;
/*    */ import org.apache.pdfbox.preflight.font.descriptor.CIDType0DescriptorHelper;
/*    */ 
/*    */ public class CIDType0FontValidator extends DescendantFontValidator<CIDType0Container>
/*    */ {
/*    */   public CIDType0FontValidator(PreflightContext context, PDFont font)
/*    */   {
/* 35 */     super(context, font, new CIDType0Container(font));
/*    */   }
/*    */ 
/*    */   protected void checkCIDToGIDMap(COSBase ctog)
/*    */   {
/* 41 */     checkCIDToGIDMap(ctog, false);
/*    */   }
/*    */ 
/*    */   protected void createFontDescriptorHelper()
/*    */   {
/* 47 */     this.descriptorHelper = new CIDType0DescriptorHelper(this.context, this.font, (CIDType0Container)this.fontContainer);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.CIDType0FontValidator
 * JD-Core Version:    0.6.2
 */