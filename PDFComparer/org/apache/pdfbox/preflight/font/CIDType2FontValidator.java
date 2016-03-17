/*    */ package org.apache.pdfbox.preflight.font;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.font.container.CIDType2Container;
/*    */ import org.apache.pdfbox.preflight.font.descriptor.CIDType2DescriptorHelper;
/*    */ import org.apache.pdfbox.preflight.font.util.CIDToGIDMap;
/*    */ 
/*    */ public class CIDType2FontValidator extends DescendantFontValidator<CIDType2Container>
/*    */ {
/*    */   public CIDType2FontValidator(PreflightContext context, PDFont font)
/*    */   {
/* 36 */     super(context, font, new CIDType2Container(font));
/*    */   }
/*    */ 
/*    */   protected void checkCIDToGIDMap(COSBase ctog)
/*    */   {
/* 42 */     CIDToGIDMap cidToGid = checkCIDToGIDMap(ctog, true);
/* 43 */     ((CIDType2Container)this.fontContainer).setCidToGid(cidToGid);
/*    */   }
/*    */ 
/*    */   protected void createFontDescriptorHelper()
/*    */   {
/* 49 */     this.descriptorHelper = new CIDType2DescriptorHelper(this.context, this.font, (CIDType2Container)this.fontContainer);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.CIDType2FontValidator
 * JD-Core Version:    0.6.2
 */