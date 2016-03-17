/*    */ package com.itextpdf.text.pdf;
/*    */ 
/*    */ import com.itextpdf.text.DocumentException;
/*    */ import com.itextpdf.text.Image;
/*    */ import com.itextpdf.text.error_messages.MessageLocalization;
/*    */ 
/*    */ public final class Type3Glyph extends PdfContentByte
/*    */ {
/*    */   private PageResources pageResources;
/*    */   private boolean colorized;
/*    */ 
/*    */   private Type3Glyph()
/*    */   {
/* 58 */     super(null);
/*    */   }
/*    */ 
/*    */   Type3Glyph(PdfWriter writer, PageResources pageResources, float wx, float llx, float lly, float urx, float ury, boolean colorized) {
/* 62 */     super(writer);
/* 63 */     this.pageResources = pageResources;
/* 64 */     this.colorized = colorized;
/* 65 */     if (colorized) {
/* 66 */       this.content.append(wx).append(" 0 d0\n");
/*    */     }
/*    */     else
/* 69 */       this.content.append(wx).append(" 0 ").append(llx).append(' ').append(lly).append(' ').append(urx).append(' ').append(ury).append(" d1\n");
/*    */   }
/*    */ 
/*    */   PageResources getPageResources()
/*    */   {
/* 74 */     return this.pageResources;
/*    */   }
/*    */ 
/*    */   public void addImage(Image image, float a, float b, float c, float d, float e, float f, boolean inlineImage) throws DocumentException {
/* 78 */     if ((!this.colorized) && ((!image.isMask()) || ((image.getBpc() != 1) && (image.getBpc() <= 255))))
/* 79 */       throw new DocumentException(MessageLocalization.getComposedMessage("not.colorized.typed3.fonts.only.accept.mask.images", new Object[0]));
/* 80 */     super.addImage(image, a, b, c, d, e, f, inlineImage);
/*    */   }
/*    */ 
/*    */   public PdfContentByte getDuplicate() {
/* 84 */     Type3Glyph dup = new Type3Glyph();
/* 85 */     dup.writer = this.writer;
/* 86 */     dup.pdf = this.pdf;
/* 87 */     dup.pageResources = this.pageResources;
/* 88 */     dup.colorized = this.colorized;
/* 89 */     return dup;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.Type3Glyph
 * JD-Core Version:    0.6.2
 */